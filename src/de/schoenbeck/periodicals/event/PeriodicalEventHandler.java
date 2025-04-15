package de.schoenbeck.periodicals.event;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.base.event.AbstractEventHandler;
import org.adempiere.base.event.IEventTopics;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.Query;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.osgi.service.event.Event;

import de.schoenbeck.periodicals.model.MPeriodical;
import de.schoenbeck.periodicals.model.MPeriodicalSubscriber;
import de.schoenbeck.periodicals.process.Util;

public class PeriodicalEventHandler extends AbstractEventHandler {

	@Override
	protected void initialize() {
		registerEvent(IEventTopics.DOC_AFTER_COMPLETE);
		CLogger.get().log(Level.SEVERE, "registered event handler: " + this.getClass().getCanonicalName());
	}

	@Override
	protected void doHandleEvent(Event event) {
		
		if (!event.getProperty("tableName").equals(MOrder.Table_Name)) return;
		
		Properties ctx = Env.getCtx();
		MOrder order = (MOrder) getPO(event);
		
		// skip orders that have been used to create a subscriber before
		if (isOrderUsedAlready(ctx, order.get_ID(), order.get_TrxName())) return;
		
		StringBuilder errs = new StringBuilder();
		
		for (SubInfo info : getSubscriptionInfo(ctx, order.get_ID(), order.get_TrxName())) {
			try {
				createSubscriberFromSubInfo(ctx, info.line, info.periodical_id, info.frequency, info.frequencyType, info.editionspaid, info.qtyplan, info.isRenew, info.periodicalSubscription_id, info.line.getQtyEntered(), order.get_TrxName());
			} catch (RuntimeException e) {
				String msg = Msg.getMsg(Env.getCtx(), "AddingSubscriberFailed", new Object[] {info.line.getProduct().getName()});
				errs.append(msg).append("\n")
					.append(e).append(";\n");
				CLogger.get().log(Level.SEVERE, "", e);
			}
		}
		
		if (errs.length() > 0) {
			addErrorMessage(event, errs.toString());
		}
		
	}
	
	private boolean isOrderUsedAlready (Properties ctx, int order_id, String trxname) {
		return new Query(
				ctx,
				MPeriodicalSubscriber.Table_Name,
				MPeriodicalSubscriber.COLUMNNAME_C_Order_ID + " = " + order_id,
				trxname).match();
	}

	/**
	 * Collect a {@link LinkedList} of SubInfo objects.<br>
	 * The objects contain all info for order lines that contain a product representing a periodical subscription,
	 * as per configuration in the Periodical Subscription tab.
	 * @param ctx - execution context
	 * @param order_id - The given order
	 * @return the list as explained above
	 */
	private List<SubInfo> getSubscriptionInfo(Properties ctx, int order_id, String trxname) {
		
		LinkedList<SubInfo> rtn = new LinkedList<>();
		
		String sql = "SELECT co.c_orderline_id, cp.c_periodical_id, cps.frequency, cps.frequencytype, cps.editionspaid, cps.qtyplan, cps.isrenewal, cps.c_periodicalsubscription_id "
				+ "FROM c_orderline co "
				+ "JOIN c_periodicalsubscription cps "
				+ "	ON cps.m_product_id = co.m_product_id "
				+ "	AND cps.ad_client_id = co.ad_client_id "
				+ "JOIN c_periodical cp "
				+ "	ON cp.c_periodical_id = cps.c_periodical_id "
				+ "	AND cp.ad_client_id = co.ad_client_id "
				+ "WHERE cps.isactive = 'Y' "
				+ "AND cp.isactive = 'Y' "
				+ "AND co.isactive = 'Y' "
				+ "AND co.ad_client_id = ? "
				+ "AND co.c_order_id = ?";
		PreparedStatement ps = DB.prepareStatement(sql, trxname);
		ResultSet rs = null;
		try {
			ps.setInt(1, Env.getAD_Client_ID(ctx));
			ps.setInt(2, order_id);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				rtn.add(new SubInfo(
						new MOrderLine(ctx, rs.getInt("c_orderline_id"), trxname),
						rs.getInt("c_periodical_id"),
						rs.getInt("frequency"),
						rs.getString("frequencytype").charAt(0),
						rs.getInt("editionspaid"),
						rs.getBigDecimal("qtyplan"),
						rs.getBoolean("isrenewal"),
						rs.getInt("c_periodicalsubscription_id")
						));
			}
		} catch (SQLException e) {
			CLogger.get().log(Level.SEVERE, "", e);
		} finally {
			DB.close(rs, ps);
			rs = null;
			ps = null;
		}
		
		return rtn;
	}
	
	/**
	 * Create an entry for {@link MPeriodicalSubscriber} with data taken from this order and configuration in the Periodical Subscription tab.
	 * @param ctx - execution context
	 * @param line - the order line containing a product representing a periodical subscription
	 * @param periodical_id - the periodical represented by the former order line
	 * @param frequency - the frequency in which the subscription needs to be renewed
	 * @param frequencyType - the unit the {@code frequency} is given in
	 * @param editionspaid - the amount of editions the subscriber will receive
	 * @param isRenew - whether the subscription bought is a renewal of an existing subscription
	 */
	private void createSubscriberFromSubInfo (
			Properties ctx, MOrderLine line, int periodical_id, int frequency, char frequencyType, int editionspaid, 
			BigDecimal qtyPlan, boolean isRenew, int periodicalSubscription_id, BigDecimal qtyOrdered, String trxname) {
		
		String where = MPeriodical.COLUMNNAME_C_Periodical_ID + " = " + periodical_id +
				" AND " + MPeriodicalSubscriber.COLUMNNAME_C_BPartner_ID + " = " + line.getC_BPartner_ID() +
				" AND " + MPeriodicalSubscriber.COLUMNNAME_Bill_BPartner_ID + " = " + line.getC_Order().getBill_BPartner_ID() +
				" AND " + MPeriodicalSubscriber.COLUMNNAME_SubscribeDate + " < '" + Env.getCtx().get(Env.DATE) + "'" +
				" AND " + MPeriodicalSubscriber.COLUMNNAME_IsActive + " = 'Y'";
		int subscriber_id = Math.max(new Query(ctx, MPeriodicalSubscriber.Table_Name, where, trxname).firstIdOnly(), 0);
		
		if (!isRenew && subscriber_id > 0) {
			throw new AdempiereException("Periodical does not allow renewal");
		}
		
		MPeriodicalSubscriber subscriber = new MPeriodicalSubscriber(ctx, subscriber_id, trxname);
		if (!subscriber.isrenewAutomatically() && subscriber_id > 0)
			throw new AdempiereException();
		
		I_C_Order currentOrder = line.getC_Order();
		
		subscriber.setQtyOrdered(qtyOrdered);
		subscriber.setC_PeriodicalSubscription_ID(periodicalSubscription_id);
		
		subscriber.setAD_Org_ID(line.getAD_Org_ID());
		subscriber.setC_Periodical_ID(periodical_id);
		subscriber.setrenewAutomatically(isRenew);
		
		subscriber.setC_BPartner_ID(line.getC_BPartner_ID());
		subscriber.setC_BPartner_Location_ID(line.getC_BPartner_Location_ID());
		subscriber.setBill_BPartner_ID(currentOrder.getBill_BPartner_ID());
		subscriber.setBill_Location_ID(currentOrder.getBill_Location_ID());
		subscriber.setPOReference(currentOrder.getPOReference());
		subscriber.setC_Order_ID(currentOrder.getC_Order_ID());
		
		Timestamp date = line.getDatePromised();
		Timestamp endTime = Util.addTimeToTimestamp(ctx, date, frequency, frequencyType);
		
		if (!isRenew || subscriber_id == 0) // Only update the "subscribed since" date when it is no renewal nor new
			subscriber.setSubscribeDate(date);
		subscriber.setSubscribedUntil(endTime);
		
		subscriber.setEditionsPaid(editionspaid
				+ subscriber.getEditionsPaid());
		subscriber.setQtyPlan(line.getQtyEntered().multiply(qtyPlan));
		
		subscriber.saveEx();
	}
	
	
	private record SubInfo (
		MOrderLine line,
		int periodical_id,
		int frequency,
		char frequencyType,
		int editionspaid,
		BigDecimal qtyplan,
		boolean isRenew,
		int periodicalSubscription_id){}
		

}
