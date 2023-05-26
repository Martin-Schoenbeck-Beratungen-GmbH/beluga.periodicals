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
package de.schoenbeck.periodicals.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for C_PeriodicalSubscriber
 *  @author iDempiere (generated) 
 *  @version Release 9
 */
@SuppressWarnings("all")
public interface I_C_PeriodicalSubscriber 
{

    /** TableName=C_PeriodicalSubscriber */
    public static final String Table_Name = "C_PeriodicalSubscriber";

    /** AD_Table_ID=200334 */
    public static final int Table_ID = 200334;

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name Bill_BPartner_ID */
    public static final String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/** Set Invoice Partner.
	  * Business Partner to be invoiced
	  */
	public void setBill_BPartner_ID (int Bill_BPartner_ID);

	/** Get Invoice Partner.
	  * Business Partner to be invoiced
	  */
	public int getBill_BPartner_ID();

	public org.compiere.model.I_C_BPartner getBill_BPartner() throws RuntimeException;

    /** Column name Bill_Location_ID */
    public static final String COLUMNNAME_Bill_Location_ID = "Bill_Location_ID";

	/** Set Invoice Location.
	  * Business Partner Location for invoicing
	  */
	public void setBill_Location_ID (int Bill_Location_ID);

	/** Get Invoice Location.
	  * Business Partner Location for invoicing
	  */
	public int getBill_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getBill_Location() throws RuntimeException;

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner.
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner.
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException;

    /** Column name C_PeriodicalSubscriber_ID */
    public static final String COLUMNNAME_C_PeriodicalSubscriber_ID = "C_PeriodicalSubscriber_ID";

	/** Set Periodical Subscriber	  */
	public void setC_PeriodicalSubscriber_ID (int C_PeriodicalSubscriber_ID);

	/** Get Periodical Subscriber	  */
	public int getC_PeriodicalSubscriber_ID();

    /** Column name C_PeriodicalSubscriber_UU */
    public static final String COLUMNNAME_C_PeriodicalSubscriber_UU = "C_PeriodicalSubscriber_UU";

	/** Set C_PeriodicalSubscriber_UU	  */
	public void setC_PeriodicalSubscriber_UU (String C_PeriodicalSubscriber_UU);

	/** Get C_PeriodicalSubscriber_UU	  */
	public String getC_PeriodicalSubscriber_UU();

    /** Column name C_Periodical_ID */
    public static final String COLUMNNAME_C_Periodical_ID = "C_Periodical_ID";

	/** Set Periodical	  */
	public void setC_Periodical_ID (int C_Periodical_ID);

	/** Get Periodical	  */
	public int getC_Periodical_ID();

	public I_C_Periodical getC_Periodical() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name EditionsPaid */
    public static final String COLUMNNAME_EditionsPaid = "EditionsPaid";

	/** Set Editions paid.
	  * The number of editions the customer paid for that are open to be delivered
	  */
	public void setEditionsPaid (int EditionsPaid);

	/** Get Editions paid.
	  * The number of editions the customer paid for that are open to be delivered
	  */
	public int getEditionsPaid();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

	/** Set Order Reference.
	  * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public void setPOReference (String POReference);

	/** Get Order Reference.
	  * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public String getPOReference();

    /** Column name QtyPlan */
    public static final String COLUMNNAME_QtyPlan = "QtyPlan";

	/** Set Quantity Plan.
	  * Planned Quantity
	  */
	public void setQtyPlan (BigDecimal QtyPlan);

	/** Get Quantity Plan.
	  * Planned Quantity
	  */
	public BigDecimal getQtyPlan();

    /** Column name SubscribeDate */
    public static final String COLUMNNAME_SubscribeDate = "SubscribeDate";

	/** Set Subscribe Date.
	  * Date the contact actively subscribed
	  */
	public void setSubscribeDate (Timestamp SubscribeDate);

	/** Get Subscribe Date.
	  * Date the contact actively subscribed
	  */
	public Timestamp getSubscribeDate();

    /** Column name SubscribedUntil */
    public static final String COLUMNNAME_SubscribedUntil = "SubscribedUntil";

	/** Set Subscribed until.
	  * The date to which the subscription lasts
	  */
	public void setSubscribedUntil (Timestamp SubscribedUntil);

	/** Get Subscribed until.
	  * The date to which the subscription lasts
	  */
	public Timestamp getSubscribedUntil();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name renewAutomatically */
    public static final String COLUMNNAME_renewAutomatically = "renewAutomatically";

	/** Set renew automatically.
	  * Whether to renew the subscription automatically
	  */
	public void setrenewAutomatically (boolean renewAutomatically);

	/** Get renew automatically.
	  * Whether to renew the subscription automatically
	  */
	public boolean isrenewAutomatically();
}
