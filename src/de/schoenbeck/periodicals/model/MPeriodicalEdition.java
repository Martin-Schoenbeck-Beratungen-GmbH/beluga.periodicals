package de.schoenbeck.periodicals.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MPeriodicalEdition extends X_C_PeriodicalEdition {

	private static final long serialVersionUID = 7223894018241394414L;

	public MPeriodicalEdition(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public MPeriodicalEdition(Properties ctx, int C_PeriodicalEdition_ID, String trxName, String[] virtualColumns) {
		super(ctx, C_PeriodicalEdition_ID, trxName, virtualColumns);
	}

	public MPeriodicalEdition(Properties ctx, int C_PeriodicalEdition_ID, String trxName) {
		super(ctx, C_PeriodicalEdition_ID, trxName);
	}

		public MPeriodicalEdition(MPeriodical periodical) {
		super(periodical.getCtx(), 0, periodical.get_TrxName());
		setC_Periodical_ID(periodical.get_ID());
	}

	
}
