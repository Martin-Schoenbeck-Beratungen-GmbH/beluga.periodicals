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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for C_Periodical
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="C_Periodical")
public class X_C_Periodical extends PO implements I_C_Periodical, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250327L;

    /** Standard Constructor */
    public X_C_Periodical (Properties ctx, int C_Periodical_ID, String trxName)
    {
      super (ctx, C_Periodical_ID, trxName);
      /** if (C_Periodical_ID == 0)
        {
			setC_Periodical_ID (0);
			setName (null);
        } */
    }

    /** Standard Constructor */
    public X_C_Periodical (Properties ctx, int C_Periodical_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, C_Periodical_ID, trxName, virtualColumns);
      /** if (C_Periodical_ID == 0)
        {
			setC_Periodical_ID (0);
			setName (null);
        } */
    }

    /** Standard Constructor */
    public X_C_Periodical (Properties ctx, String C_Periodical_UU, String trxName)
    {
      super (ctx, C_Periodical_UU, trxName);
      /** if (C_Periodical_UU == null)
        {
			setC_Periodical_ID (0);
			setName (null);
        } */
    }

    /** Standard Constructor */
    public X_C_Periodical (Properties ctx, String C_Periodical_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, C_Periodical_UU, trxName, virtualColumns);
      /** if (C_Periodical_UU == null)
        {
			setC_Periodical_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_Periodical (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_C_Periodical[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
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

	/** Set C_Periodical_UU.
		@param C_Periodical_UU C_Periodical_UU
	*/
	public void setC_Periodical_UU (String C_Periodical_UU)
	{
		set_Value (COLUMNNAME_C_Periodical_UU, C_Periodical_UU);
	}

	/** Get C_Periodical_UU.
		@return C_Periodical_UU	  */
	public String getC_Periodical_UU()
	{
		return (String)get_Value(COLUMNNAME_C_Periodical_UU);
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

	/** Set Introduced on.
		@param IntroducedOn The date this periodical was first introduced
	*/
	public void setIntroducedOn (Timestamp IntroducedOn)
	{
		set_Value (COLUMNNAME_IntroducedOn, IntroducedOn);
	}

	/** Get Introduced on.
		@return The date this periodical was first introduced
	  */
	public Timestamp getIntroducedOn()
	{
		return (Timestamp)get_Value(COLUMNNAME_IntroducedOn);
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

	/** Set Create orders from periodical.
		@param OrdersFromPeriodical Introduce a new edition to a periodical and create orders for every currently subscribed user
	*/
	public void setOrdersFromPeriodical (String OrdersFromPeriodical)
	{
		set_Value (COLUMNNAME_OrdersFromPeriodical, OrdersFromPeriodical);
	}

	/** Get Create orders from periodical.
		@return Introduce a new edition to a periodical and create orders for every currently subscribed user
	  */
	public String getOrdersFromPeriodical()
	{
		return (String)get_Value(COLUMNNAME_OrdersFromPeriodical);
	}

	/** Set Search Key.
		@param Value Search key for the record in the format required - must be unique
	*/
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue()
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}