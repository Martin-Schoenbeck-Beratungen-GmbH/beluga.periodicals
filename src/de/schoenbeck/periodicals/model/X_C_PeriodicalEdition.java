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

/** Generated Model for C_PeriodicalEdition
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="C_PeriodicalEdition")
public class X_C_PeriodicalEdition extends PO implements I_C_PeriodicalEdition, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250327L;

    /** Standard Constructor */
    public X_C_PeriodicalEdition (Properties ctx, int C_PeriodicalEdition_ID, String trxName)
    {
      super (ctx, C_PeriodicalEdition_ID, trxName);
      /** if (C_PeriodicalEdition_ID == 0)
        {
			setC_PeriodicalEdition_ID (0);
			setC_Periodical_ID (0);
			setEditionNo (0);
			setIntroducedOn (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setM_Product_ID (0);
			setName (null);
        } */
    }

    /** Standard Constructor */
    public X_C_PeriodicalEdition (Properties ctx, int C_PeriodicalEdition_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, C_PeriodicalEdition_ID, trxName, virtualColumns);
      /** if (C_PeriodicalEdition_ID == 0)
        {
			setC_PeriodicalEdition_ID (0);
			setC_Periodical_ID (0);
			setEditionNo (0);
			setIntroducedOn (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setM_Product_ID (0);
			setName (null);
        } */
    }

    /** Standard Constructor */
    public X_C_PeriodicalEdition (Properties ctx, String C_PeriodicalEdition_UU, String trxName)
    {
      super (ctx, C_PeriodicalEdition_UU, trxName);
      /** if (C_PeriodicalEdition_UU == null)
        {
			setC_PeriodicalEdition_ID (0);
			setC_Periodical_ID (0);
			setEditionNo (0);
			setIntroducedOn (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setM_Product_ID (0);
			setName (null);
        } */
    }

    /** Standard Constructor */
    public X_C_PeriodicalEdition (Properties ctx, String C_PeriodicalEdition_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, C_PeriodicalEdition_UU, trxName, virtualColumns);
      /** if (C_PeriodicalEdition_UU == null)
        {
			setC_PeriodicalEdition_ID (0);
			setC_Periodical_ID (0);
			setEditionNo (0);
			setIntroducedOn (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setM_Product_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_PeriodicalEdition (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_C_PeriodicalEdition[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Periodical Edition.
		@param C_PeriodicalEdition_ID Periodical Edition
	*/
	public void setC_PeriodicalEdition_ID (int C_PeriodicalEdition_ID)
	{
		if (C_PeriodicalEdition_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_PeriodicalEdition_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_PeriodicalEdition_ID, Integer.valueOf(C_PeriodicalEdition_ID));
	}

	/** Get Periodical Edition.
		@return Periodical Edition	  */
	public int getC_PeriodicalEdition_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PeriodicalEdition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_PeriodicalEdition_UU.
		@param C_PeriodicalEdition_UU C_PeriodicalEdition_UU
	*/
	public void setC_PeriodicalEdition_UU (String C_PeriodicalEdition_UU)
	{
		set_Value (COLUMNNAME_C_PeriodicalEdition_UU, C_PeriodicalEdition_UU);
	}

	/** Get C_PeriodicalEdition_UU.
		@return C_PeriodicalEdition_UU	  */
	public String getC_PeriodicalEdition_UU()
	{
		return (String)get_Value(COLUMNNAME_C_PeriodicalEdition_UU);
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

	/** Set Edition number.
		@param EditionNo Edition number
	*/
	public void setEditionNo (int EditionNo)
	{
		set_Value (COLUMNNAME_EditionNo, Integer.valueOf(EditionNo));
	}

	/** Get Edition number.
		@return Edition number	  */
	public int getEditionNo()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EditionNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}