package de.schoenbeck.periodicals.process;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.adempiere.base.IProcessFactory;
import org.compiere.process.ProcessCall;
import org.compiere.util.Env;

public final class Util implements IProcessFactory {

	public ProcessCall newProcessInstance(String className) {
		if (className.equals("de.schoenbeck.periodicals.process.OrdersFromPeriodical"))
			return new OrdersFromPeriodical();
		if (className.equals("de.schoenbeck.periodicals.process.ImportPeriodicalSubscriber"))
			return new ImportPeriodicalSubscriber();
		return null;
	}
	
	/**
	 * Utility function taking a {@code java.util.Date} and adding some time to it.
	 * @param ctx - Context for locale
	 * @param date - the original date
	 * @param timeAmount - the amount to be added
	 * @param timeUnit - the unit to be added; allows for 'Y'ear, 'M'onth or 'D'ay
	 * @return a java.sql.Timestamp after addition; null if parameter timeUnit is non-conclusive
	 */
	public static Timestamp addTimeToTimestamp (Properties ctx, Date date, int timeAmount, char timeUnit) {
		Calendar cal = Calendar.getInstance(Env.getLocale(ctx));
		cal.setTime(date);
		
		int unit;
		switch (timeUnit) {
		case 'Y': unit = Calendar.YEAR; break;
		case 'M': unit = Calendar.MONTH; break;
		case 'D': unit = Calendar.DAY_OF_YEAR; break;
		default: return null;
		}
		
		cal.add(unit, timeAmount);
		return new Timestamp(cal.getTime().getTime());
	}
	
	public static String nvl (String s) {
		return s == null ? "" : s;
	}
}
