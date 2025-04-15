package de.schoenbeck.periodicals.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MProject;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
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
		
		return Msg.getMsg(getCtx(), "OrdersFromPeriodicalCreated", new Object[] {count});
	}

	/**
	 * Create renewal order created from info stored in subscriber.
	 * @param subscriber - The subscriber to be renewed
	 * @param description - An optional order description
	 * @throws AdempiereException - If renewal info is incomplete
	 */
	private void createRenewalOrder(MPeriodicalSubscriber subscriber, String description) throws AdempiereException {
		
		if (subscriber.getC_PeriodicalSubscription_ID() == 0 || subscriber.getQtyOrdered() == null)
				throw new AdempiereException("Renewal is faulty at " 
					    + subscriber.getC_BPartner().getValue() + "_"
						+ subscriber.getC_BPartner().getName() + " / "
						+ subscriber.getBill_BPartner().getValue() + "_"
						+ subscriber.getBill_BPartner().getName());
			
		
		MOrder renew = new MOrder(getCtx(), 0, get_TrxName());
				
		renew.setPOReference(subscriber.getPOReference());
		renew.setBPartner((MBPartner) subscriber.getC_BPartner());
		renew.setC_BPartner_Location_ID(subscriber.getC_BPartner_Location_ID());
		renew.setBill_BPartner_ID(subscriber.getBill_BPartner_ID());
		renew.setBill_Location_ID(subscriber.getBill_Location_ID());
		renew.setDescription(description);
		renew.setDeliveryViaRule(MOrder.DELIVERYVIARULE_Delivery);
		renew.saveEx();
		MOrderLine line = new MOrderLine(renew);
		line.setM_Product_ID(subscriber.getC_PeriodicalSubscription().getM_Product_ID());
		line.setQty(subscriber.getQtyOrdered());
		line.setPrice();
		line.setPriceEntered(line.getPriceActual());
		line.setTax();
		line.saveEx();

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
	
}
