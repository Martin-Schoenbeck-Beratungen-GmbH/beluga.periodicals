package de.schoenbeck.periodicals.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import org.adempiere.process.ImportProcess;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MLocation;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import de.schoenbeck.periodicals.model.MPeriodicalSubscriber;
import de.schoenbeck.periodicals.model.X_C_PeriodicalSubscription;

public class ImportPeriodicalSubscriber extends SvrProcess implements ImportProcess {

	//Parameters
	boolean p_deleteOldImports;
	int p_PeriodicalSubscription_ID;
	
	@Override
	protected void prepare() {
		for (ProcessInfoParameter pip : getParameter()) {
			switch (pip.getParameterName()) {
			case "DeleteOldImported": p_deleteOldImports = pip.getParameterAsString().equals("Y"); break;
			case "C_PeriodicalSubscription_ID": p_PeriodicalSubscription_ID = pip.getParameterAsInt(); break;
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		if (p_deleteOldImports)
			deleteOldImports();
		
		resetTable();
		
		findMissingValues();
		createMissingValues();
		
		checkForErrors();
		
		insertSubscribers();
		return Msg.getMsg(getCtx(), "NewSubscriber") + " (" + markImported() + ")";
	}

	@Override
	public String getImportTableName() {
		return null;
	}

	@Override
	public String getWhereClause() {
		return null;
	}

	
	
	private void deleteOldImports() {
		String sql = "DELETE FROM I_PeriodicalSubscriber "
				+ "WHERE I_IsImported = 'Y' ";
		int deleted = DB.executeUpdate(sql, get_TrxName());
		log.info("Deleted from I_PeriodicalSubscriber: " + deleted);
	}
	
	private void resetTable() throws SQLException {
		String sql = "UPDATE I_PeriodicalSubscriber SET "
						+ "AD_Client_ID = COALESCE(AD_Client_ID, ?), "
						+ "AD_Org_ID = COALESCE(AD_Org_ID, ?), "
						+ "IsActive = COALESCE(IsActive, 'Y'), "
						+ "I_ErrorMsg = ' ', "
						+ "I_IsImported = 'N' "
					+ "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL";
		PreparedStatement ps = DB.prepareStatement(sql, get_TrxName());
		int updated = 0;
		try {
			ps.setInt(1, getAD_Client_ID());
			ps.setInt(2, 0);
			updated = ps.executeUpdate(); 
		} finally {
			DB.close(ps);
		}
		log.info("Reset I_PeriodicalSubscriber: " + updated);
	}
	
	private void findMissingValues() throws SQLException {
		
		String sql;
		PreparedStatement ps = null;
		
		try {
			//Periodical ID -> fail if null or inactive
			sql = "UPDATE I_PeriodicalSubscriber SET C_Periodical_ID = ("
					+ "SELECT C_Periodical_ID FROM C_PeriodicalSubscription WHERE C_PeriodicalSubscription_ID = ?) "
					+ "WHERE C_Periodical_ID = 0 OR C_Periodical_ID IS NULL";
			ps = DB.prepareStatement(sql, get_TrxName());
			ps.setInt(1, p_PeriodicalSubscription_ID);
			ps.executeUpdate();
			DB.close(ps);
			
			//BPartner ID -> create new if null
			sql = "UPDATE I_PeriodicalSubscriber ps SET C_BPartner_ID = ("
					+ "SELECT C_BPartner_ID FROM C_BPartner bp WHERE ps.bpvalue = bp.value) "
					+ "WHERE C_BPartner_ID = 0 OR C_BPartner_ID IS NULL";
			ps = DB.prepareStatement(sql, get_TrxName());
			ps.executeUpdate();
			DB.close(ps);
			
			//Name -> use Name2 if empty
			sql = "UPDATE I_PeriodicalSubscriber SET Name=Name2, Name2=null "
					+ "WHERE trim(Name) = '' "
					+ "OR trim(Name) IS NULL";
			DB.executeUpdate(sql, get_TrxName());
			
			//BPLocation ID -> create new if null (??)
			sql = "UPDATE I_PeriodicalSubscriber ps SET C_BPartner_Location_ID = ("
					+ "SELECT C_BPartner_Location_ID FROM C_BPartner_Location loc "
					+ "JOIN c_location cl ON loc.c_location_id = cl.c_location_id "
					+ "WHERE loc.c_bpartner_id = ps.c_bpartner_id "
					+ "AND cl.address1 = ps.address1 "
					+ "AND cl.city = ps.city "
					+ "AND cl.postal = ps.postal) "
					+ "WHERE C_BPartner_Location_ID = 0 OR C_BPartner_Location_ID IS NULL";
			ps = DB.prepareStatement(sql, get_TrxName());
			ps.executeUpdate();
			DB.close(ps);
			
			//BillPartner ID -> value required in import; fail if null
			sql = "UPDATE I_PeriodicalSubscriber ps SET Bill_BPartner_ID = ("
					+ "SELECT C_BPartner_ID FROM C_BPartner bp "
					+ "WHERE ps.Bill_BPValue = bp.Value) "
					+ "WHERE Bill_BPartner_ID = 0 OR Bill_BPartner_ID IS NULL";
			ps = DB.prepareStatement(sql, get_TrxName());
			ps.executeUpdate();
			DB.close(ps);
			
			//BillLocation ID -> fail if null (??)
			sql = "UPDATE I_PeriodicalSubscriber ps SET Bill_Location_ID = ("
					+ "SELECT C_BPartner_Location_ID FROM C_BPartner_Location cbl "
					+ "WHERE cbl.name = ps.bp_location_name "
					+ "AND ps.Bill_BPartner_ID = cbl.C_BPartner_ID) "
					+ "WHERE Bill_Location_ID = 0 OR Bill_Location_ID IS NULL";
			ps = DB.prepareStatement(sql, get_TrxName());
			ps.executeUpdate();
			DB.close(ps);
			
		} finally {
			DB.close(ps);
		}
	}

	private void createMissingValues() throws SQLException {
		String sql = "SELECT * FROM I_PeriodicalSubscriber WHERE C_BPartner_ID = 0 OR C_BPartner_ID IS NULL";
		PreparedStatement ps = DB.prepareStatement(sql, get_TrxName());
		ResultSet rs = null;
		try {
			
			rs = ps.executeQuery();
			while (rs.next()) {
				MBPartner partner = new MBPartner(getCtx(), 0, get_TrxName());
				String name = rs.getString("Name");
				String name2 = rs.getString("Name2");
				if (Util.nvl(name).equals("")) {
					name = name2;
					name2 = null;
				}
				partner.setName(name);
				partner.setName2(name2);
				if (!partner.save()) continue;
				
//				X_C_BP_Relation relation = new X_C_BP_Relation(getCtx(), 0, get_TrxName());
//				relation.setC_BPartner_ID(rs.getInt("Bill_BPartner_ID"));
//				relation.setC_BPartnerRelation_ID(partner.get_ID());
//				relation.saveEx();
				
				sql = "UPDATE I_PeriodicalSubscriber SET C_BPartner_ID = " + partner.get_ID() 
					+ " WHERE I_PeriodicalSubscriber_ID = " + rs.getInt("I_PeriodicalSubscriber_ID");
				DB.executeUpdate(sql, get_TrxName());
			}
			
		} finally {
			DB.close(rs, ps);
		}
		sql = "UPDATE i_periodicalsubscriber ip "
				+ "SET bpvalue = (SELECT value FROM c_bpartner bp WHERE ip.c_bpartner_id = bp.c_bpartner_id)";
		DB.executeUpdate(sql, get_TrxName());
		
		sql = "SELECT * FROM I_PeriodicalSubscriber WHERE C_BPartner_Location_ID = 0 OR C_BPartner_Location_ID IS NULL";
		ps = DB.prepareStatement(sql, get_TrxName());
		try {
			
			rs = ps.executeQuery();
			while (rs.next()) {
				MLocation loc = new MLocation(getCtx(), 0, get_TrxName());
				loc.setAddress1(rs.getString("address1"));
				loc.setPostal(rs.getString("postal"));
				loc.setCity(rs.getString("city"));
				loc.setC_Country_ID(findCountryID(rs.getString("country")));
				if (!loc.save()) continue;
				
				MBPartnerLocation ploc = new MBPartnerLocation(getCtx(), 0, get_TrxName());
				ploc.setC_Location_ID(loc.get_ID());
				ploc.setName(loc.getCity());
				ploc.setC_BPartner_ID(rs.getInt("C_BPartner_ID"));
				if (!ploc.save()) continue;
				
				sql = "UPDATE I_PeriodicalSubscriber SET C_BPartner_Location_ID = " + ploc.get_ID() 
					+ " WHERE I_PeriodicalSubscriber_ID = " + rs.getInt("I_PeriodicalSubscriber_ID");
				DB.executeUpdate(sql, get_TrxName());
			}
			
		} finally {
			DB.close(rs, ps);
		}
	}
	
	private int findCountryID (String name) throws SQLException {
		String sql = "SELECT C_Country_ID FROM C_Country_Trl "
				+ "WHERE AD_Language=? "
				+ "AND name=?";
		PreparedStatement ps = DB.prepareStatement(sql, get_TrxName());
		ResultSet rs = null;
		
		try {
			ps.setString(1, Env.getAD_Language(getCtx()));
			ps.setString(2, name);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} finally {
			DB.close(rs, ps);
		}
		
		return 0;
	}
	
	private void checkForErrors() throws SQLException {
		String sql;
		PreparedStatement ps = null;
		try {
			//Periodical with Periodical ID exists and is active
			sql = "UPDATE I_PeriodicalSubscriber ip SET I_IsImported='E', I_ErrorMsg=ip.I_ErrorMsg||'No such periodical exists or is active; ' "
					+ "WHERE NOT EXISTS (SELECT * FROM C_Periodical cp WHERE cp.C_Periodical_ID = ip.C_Periodical_ID AND cp.isactive = 'Y')";
			ps = DB.prepareStatement(sql, get_TrxName());
			ps.executeUpdate();
			DB.close(ps);
			
			//BillPartner is >0 and exists with BillPartner ID
			sql = "UPDATE I_PeriodicalSubscriber ip SET I_IsImported='E', I_ErrorMsg=ip.I_ErrorMsg||'No billing partner found; ' "
					+ "WHERE ip.Bill_BPartner_ID = 0 "
					+ "OR NOT EXISTS (SELECT * FROM C_BPartner cb WHERE cb.C_BPartner_ID=ip.C_BPartner_ID)";
			ps = DB.prepareStatement(sql, get_TrxName());
			ps.executeUpdate();
			DB.close(ps);
			
			//BillLocation is >0 and exists with BillPartner ID
			sql = "UPDATE I_PeriodicalSubscriber ip SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'No billing location found; ' "
					+ "WHERE ip.Bill_Location_ID = 0 "
					+ "OR NOT EXISTS (SELECT * FROM C_BPartner_Location cb WHERE cb.C_BPartner_Location_ID=ip.Bill_Location_ID AND cb.C_BPartner_ID=ip.Bill_BPartner_ID)";
			ps = DB.prepareStatement(sql, get_TrxName());
			ps.executeUpdate();
			DB.close(ps);
			
		} finally {
			DB.close(ps);
		}
	}

	private void insertSubscribers() throws SQLException {
		String sql = "SELECT * FROM I_PeriodicalSubscriber WHERE I_IsImported='N'";
		PreparedStatement ps = DB.prepareStatement(sql, get_TrxName());
		ResultSet rs = null;
		X_C_PeriodicalSubscription subscription = new X_C_PeriodicalSubscription(getCtx(), p_PeriodicalSubscription_ID, get_TrxName());
		
		try {
			
			rs = ps.executeQuery();
			while (rs.next()) {
				MPeriodicalSubscriber sub = new MPeriodicalSubscriber(getCtx(), 0, get_TrxName());
				sub.setAD_Org_ID(rs.getInt("AD_Org_ID"));
				sub.setBill_BPartner_ID(rs.getInt("Bill_BPartner_ID"));
				sub.setBill_Location_ID(rs.getInt("Bill_Location_ID"));
				sub.setC_BPartner_ID(rs.getInt("C_BPartner_ID"));
				sub.setC_BPartner_Location_ID(rs.getInt("C_BPartner_Location_ID"));
				sub.setC_Periodical_ID(rs.getInt("C_Periodical_ID"));
				sub.setEditionsPaid(subscription.getEditionsPaid());
				sub.setPOReference(rs.getString("POReference"));
				sub.setQtyPlan(subscription.getQtyPlan().multiply(rs.getBigDecimal("QtyPlan")));
				sub.setrenewAutomatically(false);
				
				try {
					Date date = new SimpleDateFormat("yyyy-MM-dd", Env.getLocale(getCtx()))
							.parse( ((String) Env.getCtx().get(Env.DATE)).split(" ")[0] );
					Timestamp endTime = Util.addTimeToTimestamp(getCtx(), date, subscription.getFrequency(), subscription.getFrequencyType().charAt(0));
					
					sub.setSubscribeDate(new Timestamp(date.getTime()));
					sub.setSubscribedUntil(endTime);
				} catch (ParseException e) {
					log.log(Level.WARNING, "Cannot parse login date", e);
					e.printStackTrace();
				}
				
				
				sub.saveEx();
			}
			
		} finally {
			DB.close(rs, ps);
		}
	}

	private int markImported() throws SQLException {
		String sql = "UPDATE I_PeriodicalSubscriber SET I_IsImported='Y' WHERE I_IsImported='N'";
		PreparedStatement ps = DB.prepareStatement(sql, get_TrxName());
		try {
			return ps.executeUpdate();
		} finally {
			DB.close(ps);
		}
	}
}
