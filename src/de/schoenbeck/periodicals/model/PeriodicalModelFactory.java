package de.schoenbeck.periodicals.model;

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.PO;
import org.compiere.util.Env;

public class PeriodicalModelFactory implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {
		switch (tableName) {
		case MPeriodical.Table_Name: return MPeriodical.class;
		case MPeriodicalEdition.Table_Name: return MPeriodicalEdition.class;
		case MPeriodicalSubscriber.Table_Name: return MPeriodicalSubscriber.class;
		}
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		switch (tableName) {
		case MPeriodical.Table_Name: return new MPeriodical(Env.getCtx(), Record_ID, trxName);
		case MPeriodicalEdition.Table_Name: return new MPeriodicalEdition(Env.getCtx(), Record_ID, trxName);
		case MPeriodicalSubscriber.Table_Name: return new MPeriodicalSubscriber(Env.getCtx(), Record_ID, trxName);
		}
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		switch (tableName) {
		case MPeriodical.Table_Name: return new MPeriodical(Env.getCtx(), rs, trxName);
		case MPeriodicalEdition.Table_Name: return new MPeriodicalEdition(Env.getCtx(), rs, trxName);
		case MPeriodicalSubscriber.Table_Name: return new MPeriodicalSubscriber(Env.getCtx(), rs, trxName);
		}
		return null;
	}

}
