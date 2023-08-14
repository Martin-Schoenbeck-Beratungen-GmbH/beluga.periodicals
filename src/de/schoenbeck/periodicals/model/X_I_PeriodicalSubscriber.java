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

/** Generated Model for I_PeriodicalSubscriber
 *  @author iDempiere (generated) 
 *  @version Release 10 - $Id$ */
@org.adempiere.base.Model(table="I_PeriodicalSubscriber")
public class X_I_PeriodicalSubscriber extends PO implements I_I_PeriodicalSubscriber, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20230808L;

    /** Standard Constructor */
    public X_I_PeriodicalSubscriber (Properties ctx, int I_PeriodicalSubscriber_ID, String trxName)
    {
      super (ctx, I_PeriodicalSubscriber_ID, trxName);
      /** if (I_PeriodicalSubscriber_ID == 0)
        {
			setI_PeriodicalSubscriber_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_I_PeriodicalSubscriber (Properties ctx, int I_PeriodicalSubscriber_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, I_PeriodicalSubscriber_ID, trxName, virtualColumns);
      /** if (I_PeriodicalSubscriber_ID == 0)
        {
			setI_PeriodicalSubscriber_ID (0);
        } */
    }

    /** Load Constructor */
    public X_I_PeriodicalSubscriber (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuilder sb = new StringBuilder ("X_I_PeriodicalSubscriber[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Address 1.
		@param Address1 Address line 1 for this location
	*/
	public void setAddress1 (String Address1)
	{
		set_Value (COLUMNNAME_Address1, Address1);
	}

	/** Get Address 1.
		@return Address line 1 for this location
	  */
	public String getAddress1()
	{
		return (String)get_Value(COLUMNNAME_Address1);
	}

	/** Set Address 2.
		@param Address2 Address line 2 for this location
	*/
	public void setAddress2 (String Address2)
	{
		set_ValueNoCheck (COLUMNNAME_Address2, Address2);
	}

	/** Get Address 2.
		@return Address line 2 for this location
	  */
	public String getAddress2()
	{
		return (String)get_Value(COLUMNNAME_Address2);
	}

	/** Set Address 3.
		@param Address3 Address Line 3 for the location
	*/
	public void setAddress3 (String Address3)
	{
		set_ValueNoCheck (COLUMNNAME_Address3, Address3);
	}

	/** Get Address 3.
		@return Address Line 3 for the location
	  */
	public String getAddress3()
	{
		return (String)get_Value(COLUMNNAME_Address3);
	}

	/** Set Address 4.
		@param Address4 Address Line 4 for the location
	*/
	public void setAddress4 (String Address4)
	{
		set_ValueNoCheck (COLUMNNAME_Address4, Address4);
	}

	/** Get Address 4.
		@return Address Line 4 for the location
	  */
	public String getAddress4()
	{
		return (String)get_Value(COLUMNNAME_Address4);
	}

	/** Set Address 5.
		@param Address5 Address Line 5 for the location
	*/
	public void setAddress5 (String Address5)
	{
		set_Value (COLUMNNAME_Address5, Address5);
	}

	/** Get Address 5.
		@return Address Line 5 for the location
	  */
	public String getAddress5()
	{
		return (String)get_Value(COLUMNNAME_Address5);
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

	/** Set Invoice Partner Key.
		@param Bill_BPValue Invoice Partner Key
	*/
	public void setBill_BPValue (String Bill_BPValue)
	{
		set_Value (COLUMNNAME_Bill_BPValue, Bill_BPValue);
	}

	/** Get Invoice Partner Key.
		@return Invoice Partner Key	  */
	public String getBill_BPValue()
	{
		return (String)get_Value(COLUMNNAME_Bill_BPValue);
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

	/** Set bp_location_name.
		@param bp_location_name bp_location_name
	*/
	public void setbp_location_name (String bp_location_name)
	{
		set_Value (COLUMNNAME_bp_location_name, bp_location_name);
	}

	/** Get bp_location_name.
		@return bp_location_name	  */
	public String getbp_location_name()
	{
		return (String)get_Value(COLUMNNAME_bp_location_name);
	}

	/** Set BP Search Key.
		@param BPValue Business Partner Key Value
	*/
	public void setBPValue (String BPValue)
	{
		set_Value (COLUMNNAME_BPValue, BPValue);
	}

	/** Get BP Search Key.
		@return Business Partner Key Value
	  */
	public String getBPValue()
	{
		return (String)get_Value(COLUMNNAME_BPValue);
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

	/** Set City.
		@param City Identifies a City
	*/
	public void setCity (String City)
	{
		set_Value (COLUMNNAME_City, City);
	}

	/** Get City.
		@return Identifies a City
	  */
	public String getCity()
	{
		return (String)get_Value(COLUMNNAME_City);
	}

	/** Set Country.
		@param Country Country
	*/
	public void setCountry (String Country)
	{
		set_Value (COLUMNNAME_Country, Country);
	}

	/** Get Country.
		@return Country	  */
	public String getCountry()
	{
		return (String)get_Value(COLUMNNAME_Country);
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
			set_Value (COLUMNNAME_C_Periodical_ID, null);
		else
			set_Value (COLUMNNAME_C_Periodical_ID, Integer.valueOf(C_Periodical_ID));
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

	/** Set Import Error Message.
		@param I_ErrorMsg Messages generated from import process
	*/
	public void setI_ErrorMsg (String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import Error Message.
		@return Messages generated from import process
	  */
	public String getI_ErrorMsg()
	{
		return (String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** Set Imported.
		@param I_IsImported Has this import been processed
	*/
	public void setI_IsImported (boolean I_IsImported)
	{
		set_ValueNoCheck (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Imported.
		@return Has this import been processed
	  */
	public boolean isI_IsImported()
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Import Table.
		@param ImportTable Import Table Columns from Database
	*/
	public void setImportTable (String ImportTable)
	{
		set_Value (COLUMNNAME_ImportTable, ImportTable);
	}

	/** Get Import Table.
		@return Import Table Columns from Database
	  */
	public String getImportTable()
	{
		return (String)get_Value(COLUMNNAME_ImportTable);
	}

	/** Set Import Periodical Subscriber.
		@param I_PeriodicalSubscriber_ID Import Periodical Subscriber
	*/
	public void setI_PeriodicalSubscriber_ID (int I_PeriodicalSubscriber_ID)
	{
		if (I_PeriodicalSubscriber_ID < 1)
			set_ValueNoCheck (COLUMNNAME_I_PeriodicalSubscriber_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_I_PeriodicalSubscriber_ID, Integer.valueOf(I_PeriodicalSubscriber_ID));
	}

	/** Get Import Periodical Subscriber.
		@return Import Periodical Subscriber	  */
	public int getI_PeriodicalSubscriber_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_PeriodicalSubscriber_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set I_PeriodicalSubscriber_UU.
		@param I_PeriodicalSubscriber_UU I_PeriodicalSubscriber_UU
	*/
	public void setI_PeriodicalSubscriber_UU (String I_PeriodicalSubscriber_UU)
	{
		set_Value (COLUMNNAME_I_PeriodicalSubscriber_UU, I_PeriodicalSubscriber_UU);
	}

	/** Get I_PeriodicalSubscriber_UU.
		@return I_PeriodicalSubscriber_UU	  */
	public String getI_PeriodicalSubscriber_UU()
	{
		return (String)get_Value(COLUMNNAME_I_PeriodicalSubscriber_UU);
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

	/** Set Name 2.
		@param Name2 Additional Name
	*/
	public void setName2 (String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	/** Get Name 2.
		@return Additional Name
	  */
	public String getName2()
	{
		return (String)get_Value(COLUMNNAME_Name2);
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

	/** Set ZIP.
		@param Postal Postal code
	*/
	public void setPostal (String Postal)
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	/** Get ZIP.
		@return Postal code
	  */
	public String getPostal()
	{
		return (String)get_Value(COLUMNNAME_Postal);
	}

	/** Set Processed.
		@param Processed The document has been processed
	*/
	public void setProcessed (boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed()
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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