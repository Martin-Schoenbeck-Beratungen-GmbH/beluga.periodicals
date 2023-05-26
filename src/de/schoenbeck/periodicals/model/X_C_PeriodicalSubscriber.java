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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for C_PeriodicalSubscriber
 *  @author iDempiere (generated) 
 *  @version Release 9 - $Id$ */
@org.adempiere.base.Model(table="C_PeriodicalSubscriber")
public class X_C_PeriodicalSubscriber extends PO implements I_C_PeriodicalSubscriber, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20230330L;

    /** Standard Constructor */
    public X_C_PeriodicalSubscriber (Properties ctx, int C_PeriodicalSubscriber_ID, String trxName)
    {
      super (ctx, C_PeriodicalSubscriber_ID, trxName);
      /** if (C_PeriodicalSubscriber_ID == 0)
        {
			setC_PeriodicalSubscriber_ID (0);
			setC_Periodical_ID (0);
			setEditionsPaid (0);
			setSubscribedUntil (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Standard Constructor */
    public X_C_PeriodicalSubscriber (Properties ctx, int C_PeriodicalSubscriber_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, C_PeriodicalSubscriber_ID, trxName, virtualColumns);
      /** if (C_PeriodicalSubscriber_ID == 0)
        {
			setC_PeriodicalSubscriber_ID (0);
			setC_Periodical_ID (0);
			setEditionsPaid (0);
			setSubscribedUntil (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_C_PeriodicalSubscriber (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuilder sb = new StringBuilder ("X_C_PeriodicalSubscriber[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getBill_BPartner() throws RuntimeException
	{
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_ID)
			.getPO(getBill_BPartner_ID(), get_TrxName());
	}

	/** Set Invoice Partner.
		@param Bill_BPartner_ID Business Partner to be invoiced
	*/
	public void setBill_BPartner_ID (int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1)
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else
			set_Value (COLUMNNAME_Bill_BPartner_ID, Integer.valueOf(Bill_BPartner_ID));
	}

	/** Get Invoice Partner.
		@return Business Partner to be invoiced
	  */
	public int getBill_BPartner_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner_Location getBill_Location() throws RuntimeException
	{
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_ID)
			.getPO(getBill_Location_ID(), get_TrxName());
	}

	/** Set Invoice Location.
		@param Bill_Location_ID Business Partner Location for invoicing
	*/
	public void setBill_Location_ID (int Bill_Location_ID)
	{
		if (Bill_Location_ID < 1)
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else
			set_Value (COLUMNNAME_Bill_Location_ID, Integer.valueOf(Bill_Location_ID));
	}

	/** Get Invoice Location.
		@return Business Partner Location for invoicing
	  */
	public int getBill_Location_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_ID)
			.getPO(getC_BPartner_ID(), get_TrxName());
	}

	/** Set Business Partner.
		@param C_BPartner_ID Identifies a Business Partner
	*/
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1)
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner.
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_ID)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());
	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID Identifies the (ship to) address for this Business Partner
	*/
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1)
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
	{
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_ID)
			.getPO(getC_Order_ID(), get_TrxName());
	}

	/** Set Order.
		@param C_Order_ID Order
	*/
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1)
			set_Value (COLUMNNAME_C_Order_ID, null);
		else
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Periodical Subscriber.
		@param C_PeriodicalSubscriber_ID Periodical Subscriber
	*/
	public void setC_PeriodicalSubscriber_ID (int C_PeriodicalSubscriber_ID)
	{
		if (C_PeriodicalSubscriber_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_PeriodicalSubscriber_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_PeriodicalSubscriber_ID, Integer.valueOf(C_PeriodicalSubscriber_ID));
	}

	/** Get Periodical Subscriber.
		@return Periodical Subscriber	  */
	public int getC_PeriodicalSubscriber_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PeriodicalSubscriber_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_PeriodicalSubscriber_UU.
		@param C_PeriodicalSubscriber_UU C_PeriodicalSubscriber_UU
	*/
	public void setC_PeriodicalSubscriber_UU (String C_PeriodicalSubscriber_UU)
	{
		set_Value (COLUMNNAME_C_PeriodicalSubscriber_UU, C_PeriodicalSubscriber_UU);
	}

	/** Get C_PeriodicalSubscriber_UU.
		@return C_PeriodicalSubscriber_UU	  */
	public String getC_PeriodicalSubscriber_UU()
	{
		return (String)get_Value(COLUMNNAME_C_PeriodicalSubscriber_UU);
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

	/** Set Order Reference.
		@param POReference Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	*/
	public void setPOReference (String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	/** Get Order Reference.
		@return Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public String getPOReference()
	{
		return (String)get_Value(COLUMNNAME_POReference);
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

	/** Set Subscribe Date.
		@param SubscribeDate Date the contact actively subscribed
	*/
	public void setSubscribeDate (Timestamp SubscribeDate)
	{
		set_Value (COLUMNNAME_SubscribeDate, SubscribeDate);
	}

	/** Get Subscribe Date.
		@return Date the contact actively subscribed
	  */
	public Timestamp getSubscribeDate()
	{
		return (Timestamp)get_Value(COLUMNNAME_SubscribeDate);
	}

	/** Set Subscribed until.
		@param SubscribedUntil The date to which the subscription lasts
	*/
	public void setSubscribedUntil (Timestamp SubscribedUntil)
	{
		set_Value (COLUMNNAME_SubscribedUntil, SubscribedUntil);
	}

	/** Get Subscribed until.
		@return The date to which the subscription lasts
	  */
	public Timestamp getSubscribedUntil()
	{
		return (Timestamp)get_Value(COLUMNNAME_SubscribedUntil);
	}

	/** Set renew automatically.
		@param renewAutomatically Whether to renew the subscription automatically
	*/
	public void setrenewAutomatically (boolean renewAutomatically)
	{
		set_Value (COLUMNNAME_renewAutomatically, Boolean.valueOf(renewAutomatically));
	}

	/** Get renew automatically.
		@return Whether to renew the subscription automatically
	  */
	public boolean isrenewAutomatically()
	{
		Object oo = get_Value(COLUMNNAME_renewAutomatically);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}