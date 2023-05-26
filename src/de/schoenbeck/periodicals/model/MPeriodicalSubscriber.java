package de.schoenbeck.periodicals.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MPeriodicalSubscriber extends X_C_PeriodicalSubscriber {

	private static final long serialVersionUID = 836209662632767394L;

	public MPeriodicalSubscriber(Properties ctx, int C_PeriodicalSubscriber_ID, String trxName,
			String[] virtualColumns) {
		super(ctx, C_PeriodicalSubscriber_ID, trxName, virtualColumns);
	}

	public MPeriodicalSubscriber(Properties ctx, int C_PeriodicalSubscriber_ID, String trxName) {
		super(ctx, C_PeriodicalSubscriber_ID, trxName);
	}

	public MPeriodicalSubscriber(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
