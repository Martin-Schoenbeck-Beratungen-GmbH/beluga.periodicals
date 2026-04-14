package de.schoenbeck.periodicals.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import de.schoenbeck.periodicals.model.MPeriodicalSubscriber;
import de.schoenbeck.periodicals.model.X_C_PeriodicalSubscription;

public class SubscriptionReservationProcess extends SvrProcess {

	// given
	int periodical_id = 0;
	
	// required
	int c_bpartner_id = 0;
	int c_bplocation_id = 0;
	X_C_PeriodicalSubscription subscription = null;
	BigDecimal qtyordered = null;
	
	// can be set implicitly
	int bill_bpartner_id = 0;
	int bill_location_id = 0;
	int bill_user_id = 0;
	int ad_user_id = 0;
	Timestamp subscribedate = null;
	
	// optional
	String bp_ref = null;
	
	@Override
	protected void prepare() {
		periodical_id = getRecord_ID();
		
		for (var p : getParameter()) {
			switch (p.getParameterName().trim()) {
			case "C_BPartner_ID": c_bpartner_id = p.getParameterAsInt(); break;
			case "C_BPartner_Location_ID": c_bplocation_id = p.getParameterAsInt(); break;
			case "AD_User_ID": ad_user_id = p.getParameterAsInt(); break;
			case "Bill_BPartner_ID": bill_bpartner_id = p.getParameterAsInt(); break;
			case "Bill_Location_ID": bill_location_id = p.getParameterAsInt(); break;
			case "Bill_User_ID": bill_user_id = p.getParameterAsInt(); break;
			case "C_PeriodicalSubscription_ID": subscription = new X_C_PeriodicalSubscription(getCtx(), p.getParameterAsInt(), get_TrxName()); break;
			case "QtyOrdered": qtyordered = p.getParameterAsBigDecimal(); break;
			
			case "SubscribeDate": subscribedate = p.getParameterAsTimestamp(); break;
			case "POReference": bp_ref = p.getParameterAsString(); break;
			
			//Override periodical
			case "Record_ID":
			case "C_Periodical_ID": periodical_id = p.getParameterAsInt(); break;
			
			default:
				throw new IllegalArgumentException("Unexpected value: " + p.getParameterName());
			}
		}
		
		if (bill_user_id == 0 && bill_bpartner_id == 0)
			bill_user_id = ad_user_id;
		if (bill_location_id == 0 && bill_bpartner_id == 0)
			bill_location_id = c_bplocation_id;
		if (bill_bpartner_id == 0)
			bill_bpartner_id = c_bpartner_id;
		if (subscribedate == null)
			subscribedate = getDefaultSubscribeDate();
	}

	@Override
	protected String doIt() throws Exception {
		validateRequired();
		
		checkForDuplicate();
		createSubscriber();
		
		return "@Success@";
	}
	
	private Timestamp getDefaultSubscribeDate() {
		Calendar rtn = Calendar.getInstance();
		rtn.set(rtn.get(Calendar.YEAR)+1, 0, 1);
		
		return new Timestamp(rtn.getTimeInMillis());
	}
	
	/**
	 * Validate required parameters (existence, ranges, etc)
	 * @throws AdempiereException
	 */
	private void validateRequired() throws AdempiereException {
		if (c_bpartner_id == 0) throw new AdempiereException(MSG_InvalidArguments + " - C_BPartner_ID");
		if (c_bplocation_id == 0) throw new AdempiereException(MSG_InvalidArguments + " - C_BPartner_Location_ID");
		if (ad_user_id == 0) throw new AdempiereException(MSG_InvalidArguments + " - AD_User_ID");
		if (bill_bpartner_id == 0) throw new AdempiereException(MSG_InvalidArguments + " - Bill_BPartner_ID");
		if (bill_location_id == 0) throw new AdempiereException(MSG_InvalidArguments + " - Bill_Location_ID");
		if (bill_user_id == 0) throw new AdempiereException(MSG_InvalidArguments + " - Bill_User_ID");
		if (subscription == null) throw new AdempiereException(MSG_InvalidArguments + " - C_PeriodicalSubscription_ID");
		if (qtyordered == null) throw new AdempiereException(MSG_InvalidArguments + " - QtyOrdered");
	}
	
	/**
	 * In case of duplicates:
	 * Warn the user, then (if requested) set the existing subscriber's end date to the new subscriber's start date.
	 * @throws AdempiereException On cancel
	 * @throws InterruptedException On dialog timeout
	 */
	private void checkForDuplicate() throws AdempiereException, InterruptedException {
		String where = """
				C_Periodical_ID=?
				AND C_BPartner_ID=?
				AND Bill_BPartner_ID=?
				AND isactive='Y'
				""";
		MPeriodicalSubscriber existing =
				new Query(getCtx(), MPeriodicalSubscriber.Table_Name, where, get_TrxName())
					.setClient_ID()
					.setParameters(periodical_id, c_bpartner_id, bill_bpartner_id)
					.first();
		
		// No duplicate, we're good to go
		if (existing == null)
			return;
		
		// Ask whether to modify
		final String msg = "Es existiert schon ein Eintrag für:"
				+ "\n- " + MBPartner.get(getCtx(), c_bpartner_id).getValue()
					+ " (" + MBPartner.get(getCtx(), c_bpartner_id).getName() + ")"
				+ "\n- " + MBPartner.get(getCtx(), bill_bpartner_id)
					+ " (" + MBPartner.get(getCtx(), bill_bpartner_id).getName() + ")"
				+ "\nSoll das Abonnement zum Datum [" + new SimpleDateFormat("yyyy-MM-dd").format(subscribedate)+ "] angepasst werden?"
				;
		boolean[] modify = {false};
		Object lock = new Object();
		synchronized (lock) {
			processUI.ask(msg,
						(answer) -> {
							synchronized (lock) {
								modify[0]=answer;
								lock.notifyAll();
							}
						}
					);
			lock.wait(60000);
		}
		
		if (!modify[0])
			throw new AdempiereException("@ProcessCancelled@");
		
		// Do the modification
		existing.setSubscribedUntil(subscribedate);
		existing.setrenewAutomatically(false);
		existing.saveEx();
	}
	
	private void createSubscriber() {
		MPeriodicalSubscriber rtn = new MPeriodicalSubscriber(getCtx(), 0, get_TrxName());
		rtn.setC_Periodical_ID(periodical_id);
		rtn.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		
		rtn.setC_PeriodicalSubscription_ID(subscription.get_ID());
		rtn.setrenewAutomatically(subscription.isRenewal());
		rtn.setSubscribeDate(subscribedate);
		Timestamp endTime = Util.addTimeToTimestamp(getCtx(), subscribedate, subscription.getFrequency(), subscription.getFrequencyType().charAt(0));
		rtn.setSubscribedUntil(endTime);
		rtn.setQtyPlan(subscription.getQtyPlan());
		
		rtn.setQtyOrdered(qtyordered);
		
		rtn.setC_BPartner_ID(c_bpartner_id);
		rtn.setC_BPartner_Location_ID(c_bplocation_id);
		rtn.setBill_BPartner_ID(bill_bpartner_id);
		rtn.setBill_Location_ID(bill_location_id);
		rtn.setPOReference(bp_ref);
		
		rtn.setEditionsPaid(0);
		
		rtn.saveEx();
	}
}
