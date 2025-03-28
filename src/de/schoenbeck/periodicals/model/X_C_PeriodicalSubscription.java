/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package de.schoenbeck.periodicals.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for C_PeriodicalSubscription
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="C_PeriodicalSubscription")
public class X_C_PeriodicalSubscription extends PO implements I_C_PeriodicalSubscription, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250327L;

    /** Standard Constructor */
    public X_C_PeriodicalSubscription (Properties ctx, int C_PeriodicalSubscription_ID, String trxName)
    {
      super (ctx, C_PeriodicalSubscription_ID, trxName);
      /** if (C_PeriodicalSubscription_ID == 0)
        {
			setC_PeriodicalSubscription_ID (0);
			setEditionsPaid (0);
			setFrequency (0);
			setFrequencyType (null);
			setM_Product_ID (0);
			setName (null);
			setQtyPlan (Env.ZERO);
			setisRenewal (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_C_PeriodicalSubscription (Properties ctx, int C_PeriodicalSubscription_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, C_PeriodicalSubscription_ID, trxName, virtualColumns);
      /** if (C_PeriodicalSubscription_ID == 0)
        {
			setC_PeriodicalSubscription_ID (0);
			setEditionsPaid (0);
			setFrequency (0);
			setFrequencyType (null);
			setM_Product_ID (0);
			setName (null);
			setQtyPlan (Env.ZERO);
			setisRenewal (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_C_PeriodicalSubscription (Properties ctx, String C_PeriodicalSubscription_UU, String trxName)
    {
      super (ctx, C_PeriodicalSubscription_UU, trxName);
      /** if (C_PeriodicalSubscription_UU == null)
        {
			setC_PeriodicalSubscription_ID (0);
			setEditionsPaid (0);
			setFrequency (0);
			setFrequencyType (null);
			setM_Product_ID (0);
			setName (null);
			setQtyPlan (Env.ZERO);
			setisRenewal (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_C_PeriodicalSubscription (Properties ctx, String C_PeriodicalSubscription_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, C_PeriodicalSubscription_UU, trxName, virtualColumns);
      /** if (C_PeriodicalSubscription_UU == null)
        {
			setC_PeriodicalSubscription_ID (0);
			setEditionsPaid (0);
			setFrequency (0);
			setFrequencyType (null);
			setM_Product_ID (0);
			setName (null);
			setQtyPlan (Env.ZERO);
			setisRenewal (false);
// N
        } */
    }

    /** Load Constructor */
    public X_C_PeriodicalSubscription (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_C_PeriodicalSubscription[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Periodical Subscription.
		@param C_PeriodicalSubscription_ID Periodical Subscription
	*/
	public void setC_PeriodicalSubscription_ID (int C_PeriodicalSubscription_ID)
	{
		if (C_PeriodicalSubscription_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_PeriodicalSubscription_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_PeriodicalSubscription_ID, Integer.valueOf(C_PeriodicalSubscription_ID));
	}

	/** Get Periodical Subscription.
		@return Periodical Subscription	  */
	public int getC_PeriodicalSubscription_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PeriodicalSubscription_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_PeriodicalSubscription_UU.
		@param C_PeriodicalSubscription_UU C_PeriodicalSubscription_UU
	*/
	public void setC_PeriodicalSubscription_UU (String C_PeriodicalSubscription_UU)
	{
		set_Value (COLUMNNAME_C_PeriodicalSubscription_UU, C_PeriodicalSubscription_UU);
	}

	/** Get C_PeriodicalSubscription_UU.
		@return C_PeriodicalSubscription_UU	  */
	public String getC_PeriodicalSubscription_UU()
	{
		return (String)get_Value(COLUMNNAME_C_PeriodicalSubscription_UU);
	}

	public I_C_Periodical getC_Periodical() throws RuntimeException
	{
		return (I_C_Periodical)MTable.get(getCtx(), I_C_Periodical.Table_ID)
			.getPO(getC_Periodical_ID(), get_TrxName());
	}

	/** Set Periodical.
		@param C_Periodical_ID Periodical
	*/
	public void setC_Periodical_ID (int C_Periodical_ID)
	{
		if (C_Periodical_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Periodical_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Periodical_ID, Integer.valueOf(C_Periodical_ID));
	}

	/** Get Periodical.
		@return Periodical	  */
	public int getC_Periodical_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Periodical_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description Optional short description of the record
	*/
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription()
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Editions paid.
		@param EditionsPaid The number of editions the customer paid for that are open to be delivered
	*/
	public void setEditionsPaid (int EditionsPaid)
	{
		set_Value (COLUMNNAME_EditionsPaid, Integer.valueOf(EditionsPaid));
	}

	/** Get Editions paid.
		@return The number of editions the customer paid for that are open to be delivered
	  */
	public int getEditionsPaid()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EditionsPaid);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Frequency.
		@param Frequency Frequency of events
	*/
	public void setFrequency (int Frequency)
	{
		set_Value (COLUMNNAME_Frequency, Integer.valueOf(Frequency));
	}

	/** Get Frequency.
		@return Frequency of events
	  */
	public int getFrequency()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Frequency);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Day = D */
	public static final String FREQUENCYTYPE_Day = "D";
	/** Month = M */
	public static final String FREQUENCYTYPE_Month = "M";
	/** Year = Y */
	public static final String FREQUENCYTYPE_Year = "Y";
	/** Set Frequency Type.
		@param FrequencyType Frequency of event
	*/
	public void setFrequencyType (String FrequencyType)
	{

		set_Value (COLUMNNAME_FrequencyType, FrequencyType);
	}

	/** Get Frequency Type.
		@return Frequency of event
	  */
	public String getFrequencyType()
	{
		return (String)get_Value(COLUMNNAME_FrequencyType);
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_ID)
			.getPO(getM_Product_ID(), get_TrxName());
	}

	/** Set Product.
		@param M_Product_ID Product, Service, Item
	*/
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1)
			set_Value (COLUMNNAME_M_Product_ID, null);
		else
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name Alphanumeric identifier of the entity
	*/
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName()
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Quantity Plan.
		@param QtyPlan Planned Quantity
	*/
	public void setQtyPlan (BigDecimal QtyPlan)
	{
		set_Value (COLUMNNAME_QtyPlan, QtyPlan);
	}

	/** Get Quantity Plan.
		@return Planned Quantity
	  */
	public BigDecimal getQtyPlan()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPlan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set is renewal.
		@param isRenewal Whether this represents a new subscription (false) or a renewal of an old one (true)
	*/
	public void setisRenewal (boolean isRenewal)
	{
		set_Value (COLUMNNAME_isRenewal, Boolean.valueOf(isRenewal));
	}

	/** Get is renewal.
		@return Whether this represents a new subscription (false) or a renewal of an old one (true)
	  */
	public boolean isRenewal()
	{
		Object oo = get_Value(COLUMNNAME_isRenewal);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}
}