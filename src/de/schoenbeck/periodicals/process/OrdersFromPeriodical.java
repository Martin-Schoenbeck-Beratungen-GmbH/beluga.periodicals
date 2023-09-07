package de.schoenbeck.periodicals.process;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.MBPartner;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MProject;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import de.schoenbeck.periodicals.model.MPeriodical;
import de.schoenbeck.periodicals.model.MPeriodicalEdition;
import de.schoenbeck.periodicals.model.MPeriodicalSubscriber;

/**
 * 
 * @author Lukas Heidbreder, Martin Schönbeck Beratungen GmbH
 *
 */
@org.adempiere.base.annotation.Process
public class OrdersFromPeriodical extends SvrProcess {

	int periodical_id;
	int m_product_id;
	int editionno;
	BigDecimal qtyPlan;
	boolean createOrders = true;
	String p_description;
	
	MPeriodical periodical;
	
	List<Errors> errors;
	
	@Override
	protected void prepare() {
		for (ProcessInfoParameter pip : getParameter()) {
			switch (pip.getParameterName()) {
			case "M_Product_ID": m_product_id = pip.getParameterAsInt(); break;
			case "EditionNo": editionno = pip.getParameterAsInt(); break;
			case "QtyPlan": qtyPlan = pip.getParameterAsBigDecimal(); break;
			case "CreateOrders": createOrders = pip.getParameterAsBoolean();
			case "Description": p_description = pip.getParameterAsString();
			}
		}
		periodical_id = getRecord_ID();
		periodical = new MPeriodical(getCtx(), periodical_id, get_TrxName());
		if (editionno == 0) {
			String where = MPeriodicalEdition.COLUMNNAME_AD_Client_ID + " = " + getAD_Client_ID()
					+ " AND " + MPeriodicalEdition.COLUMNNAME_C_Periodical_ID + " = " + periodical_id;
			String expr = MPeriodicalEdition.COLUMNNAME_EditionNo;
			editionno = new Query(getCtx(), MPeriodicalEdition.Table_Name, where, get_TrxName()).aggregate(expr, Query.AGGREGATE_MAX, Integer.class) + 1;
		}
		
		errors = new LinkedList<>();
	}

	@Override
	protected String doIt() throws Exception {
		
		addPeriodicalEdition();
		
		LinkedList<MPeriodicalSubscriber> subscribers = periodical.getActiveSubscribers(get_TrxName());
		
		String description = periodical.getName() + " -> Edition #" + editionno 
				+ (p_description != null ? " (" + p_description + ")" : "");
		String billDescription = periodical.getName() + (this.p_description != null ? " " + this.p_description : "");
		int count = 0; //Count for rtn msg
		
		
		for (MPeriodicalSubscriber subscriber : subscribers) {
			
			Date today = new SimpleDateFormat("yyyy-MM-dd", Env.getLocale(getCtx()))
					.parse( ((String) Env.getCtx().get(Env.DATE)).split(" ")[0] );

			if (subscriber.getSubscribeDate().after(today)) continue;
			
			if (subscriber.isrenewAutomatically() &&
					(subscriber.getEditionsPaid() <= 0 
					|| subscriber.getSubscribedUntil().before(today))) {
				createRenewalOrder(subscriber, billDescription);
			}

			if (createOrders)
				createOrder(subscriber, description);
			
			subscriber.setEditionsPaid(subscriber.getEditionsPaid()-1); //Reduce the leftover editions for this subscriber by 1
			subscriber.saveEx(get_TrxName());
			
			count++;
		}
		
		if (errors.size() > 0) {
			processUI.download(writeErrorsToFile(errors));
		}
		
		return Msg.getMsg(getCtx(), "OrdersFromPeriodicalCreated", new Object[] {count});
	}

	/**
	 * Look for the last order to contain a periodical bought by this 
	 * @param subscriber
	 * @throws ParseException
	 * @throws SQLException
	 * @throws AdempiereException
	 */
	private void createRenewalOrder(MPeriodicalSubscriber subscriber, String description) throws ParseException, SQLException, AdempiereException {
		
		MOrder old;
		MOrder renew;
		
		if (subscriber.getC_Order_ID() > 0) {
			old = (MOrder) subscriber.getC_Order();
		} else {
			// find old
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = "WITH params AS (SELECT "
						+ "	? ad_client_id, "
						+ "	? c_periodical_id, "
						+ "	? c_bpartner_id, "
						+ "	? c_bpartner_location_id, "
						+ "	? bill_bpartner_id, "
						+ "	? bill_location_id) "
						+ "SELECT co.* FROM c_order co, params "
						+ "WHERE co.isactive = 'Y' "
						+ "AND co.c_order_id IN ( "
							+ "SELECT c_order_id FROM c_orderline co2 "
							+ "WHERE co2.m_product_id IN ( "
								+ "SELECT m_product_id FROM c_periodicalsubscription cps "
								+ "JOIN params "
								+ "ON cps.c_periodical_id = params.c_periodical_id)) "
						+ "AND co.c_bpartner_id = params.c_bpartner_id  "
						+ "AND co.c_bpartner_location_id = params.c_bpartner_location_id  "
						+ "AND co.bill_bpartner_id = params.bill_bpartner_id  "
						+ "AND co.bill_location_id = params.bill_location_id  "
						+ "ORDER BY updated DESC "
						+ "FETCH FIRST 1 ROW ONLY";
				
				ps = DB.prepareStatement(sql, get_TrxName());
				ps.setInt(1, getAD_Client_ID());
				ps.setInt(2, subscriber.getC_Periodical_ID());
				ps.setInt(3, subscriber.getC_BPartner_ID());
				ps.setInt(4, subscriber.getC_BPartner_Location_ID());
				ps.setInt(5, subscriber.getBill_BPartner_ID());
				ps.setInt(6, subscriber.getBill_Location_ID());
				
				rs = ps.executeQuery();
				if (!rs.next()) {
					log.warning("No old order for: bpartner " + subscriber.getBill_BPartner_ID() + "; bill_bpartner " + subscriber.getBill_BPartner_ID() + "; periodical " + subscriber.getC_Periodical_ID());
					errors.add(new Errors(
							ErrorAt.RENEWAL,
							subscriber.getC_BPartner(),
							subscriber.getC_BPartner_Location(),
							subscriber.getBill_BPartner(),
							subscriber.getBill_Location(),
							"No old order"
							));
					return;
				}
				old = new MOrder(getCtx(), rs, get_TrxName());
			} finally {
				DB.close(rs, ps);
			}
		}
		
		// copy values from old
		Timestamp today = new Timestamp(new SimpleDateFormat("yyyy-MM-dd", Env.getLocale(getCtx()))
				.parse( ((String) Env.getCtx().get(Env.DATE)).split(" ")[0] ).getTime());
		renew = MOrder.copyFrom(old, today, old.getC_DocTypeTarget_ID(), old.isSOTrx(), false, false, get_TrxName());
		
		renew.setDescription(description);
		
		renew.saveEx();
		
		subscriber.setC_Order_ID(renew.get_ID());
		subscriber.saveEx();
	}

	/**
	 * Add a periodical edition to the current periodical. Information is taken from process parameters.
	 */
	private void addPeriodicalEdition () {
		
		MPeriodicalEdition newEdition = new MPeriodicalEdition(periodical);
		
		newEdition.setEditionNo(editionno);
		newEdition.setName(new MProduct(getCtx(), m_product_id, get_TrxName()).getName());
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd", Env.getLocale(getCtx()))
					.parse( ((String) Env.getCtx().get(Env.DATE)).split(" ")[0] );
			newEdition.setIntroducedOn(new Timestamp(date.getTime()));
		} catch (ParseException e) {
			log.log(Level.WARNING, "Cannot parse login date", e);
			e.printStackTrace();
		}
		newEdition.setM_Product_ID(m_product_id);
		newEdition.setQtyPlan(qtyPlan);
		
		newEdition.saveEx(get_TrxName());
	}
	
	/**
	 * Create and return an order for a given subscriber.
	 * @param subscriber - the subscriber for whom to create the order
	 * @param description - the description to be given to the order
	 * @return the newly created order
	 */
	private MOrder createOrder (MPeriodicalSubscriber subscriber, String description) {
		
		final String periodicalReceiptsProject = "periodical shipping";
		int project_id = new Query(getCtx(), MProject.Table_Name, "value = '" + periodicalReceiptsProject + "'", null).firstId();
		
		MOrder order = new MOrder(getCtx(), 0, get_TrxName());
		order.setBPartner( (MBPartner) subscriber.getC_BPartner());
		order.setC_BPartner_Location_ID(subscriber.getC_BPartner_Location_ID());
		order.setBill_BPartner_ID(subscriber.getBill_BPartner_ID());
		order.setBill_Location_ID(subscriber.getBill_Location_ID());
		order.setDescription(description);
		order.setC_Project_ID(project_id);
		order.setDeliveryViaRule(MOrder.DELIVERYVIARULE_Delivery);
		order.setPOReference(subscriber.getPOReference());
		order.saveEx(get_TrxName());
		
		MOrderLine line = new MOrderLine(order);
		line.setM_Product_ID(m_product_id);
		
		BigDecimal qty = qtyPlan.multiply(subscriber.getQtyPlan());
		line.setQty(qty);
		line.setQtyInvoiced(qty);
		line.saveEx(get_TrxName());
		
		line.setPrice(Env.ZERO);		
		line.saveEx(get_TrxName());
		
		return order;
	}

	/**
	 * Creates a file called "belugaperiodicalerrors.txt" in the system's temporary directory and returns a reference.<br>
	 * If it already exists, it is deleted first.
	 * @param errors - A list of Objects representing errors
	 * @return reference to the new file
	 * @throws IOException
	 */
	private File writeErrorsToFile(List<?> errors) throws IOException {
		
		String path = System.getProperty("java.io.tmpdir") + "/belugaperiodicalerrors.txt";
		File rtn = new File(path);
		
		// Remove old file
		Files.deleteIfExists(rtn.toPath());
		
		// Write to file
		FileWriter writer = null;
		try {
			writer = new FileWriter(rtn);
			for (Object i : errors) {
				writer.write(i.toString() + "\n\n");
			}
		} finally {
			if (writer != null)
				writer.close();
		}
		
		return rtn;
	}
	
	/**
	 * This should be a record but records are not a part of Java 11.
	 * @author Lukas Heidbreder, Martin Schönbeck Beratungen GmbH
	 *
	 */
	private class Errors {
		
		@Override
		public String toString() {
			return "Error at: " + at 
					+ "\n\tRecipient: " + c_bpartner.getValue() + "_" + c_bpartner.getName() + " (ID=" + c_bpartner.getC_BPartner_ID() + ")"
					+ "\n\tLocation: " + c_bplocation.getName() + " (ID=" + c_bplocation.getC_BPartner_Location_ID() + ")"
					+ "\n\tBillpartner: " + bill_bpartner.getValue() + "_" + bill_bpartner.getName() + " (ID=" + bill_bpartner.getC_BPartner_ID() + ")"
					+ "\n\tLocation: " + c_billpartner_location.getName() + " (ID=" + c_billpartner_location.getC_BPartner_Location_ID() + ")"
					+ "\n\tWith Message: " + msg;
		}

		public final ErrorAt at;
		public final I_C_BPartner c_bpartner;
		public final I_C_BPartner bill_bpartner;
		public final I_C_BPartner_Location c_bplocation;
		public final I_C_BPartner_Location c_billpartner_location;
		public final String msg;
		
		public Errors (ErrorAt at, I_C_BPartner c_bpartner, I_C_BPartner_Location c_bplocation, 
				I_C_BPartner bill_bpartner, I_C_BPartner_Location c_billpartner_location, String msg) {
			this.at = at;
			this.c_bpartner = c_bpartner;
			this.bill_bpartner = bill_bpartner;
			this.c_bplocation = c_bplocation;
			this.c_billpartner_location = c_billpartner_location;
			this.msg = msg;
		}
	}
	
	/**
	 * Collection of every place this process may fail at
	 * @author Lukas Heidbreder, Martin Schönbeck Beratungen GmbH
	 *
	 */
	private static enum ErrorAt {
		RENEWAL{
			@Override
			public String toString() {
				return Msg.getMsg(Env.getCtx(), "Renewal");
			}
		},
		RECEIPT {
			@Override
			public String toString() {
				return Msg.getMsg(Env.getCtx(), "Receipt");
			}
		};
	}
 }
