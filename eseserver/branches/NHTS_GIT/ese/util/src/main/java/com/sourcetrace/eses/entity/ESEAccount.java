/*
 * ESEAccount.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

// TODO: Auto-generated Javadoc

/**
 * The Class ESEAccount.
 */
@Entity
@FilterDef(name = "branchFilter", parameters = @ParamDef(name = "branchIdParam", type = "string"))@Filters(@org.hibernate.annotations.Filter(name="branchFilter", condition="branch_id in ( :branchIdParam )"))
@Table(name = "ESE_ACCOUNT")
public class ESEAccount implements Comparable<ESEAccount> {

	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int BLOCKED = 2;

	public static final int OFFLINE = 0;
	public static final int ONLINE = 1;

	public static final int COMPANY_ACCOUNT_TYPE = 0;
	public static final int AGENT_ACCOUNT = 1;
	public static final int CLIENT_ACCOUNT = 2;
	public static final int CONTRACT_ACCOUNT = 3;
	public static final int VENDOR_ACCOUNT = 4;
	public static final int FARMER_ACCOUNT = 5;
	public static final int BUYER_ACCOUNT = 6;
	public static final int ORGANIZATION_ACCOUNT = 7;
	public static final String COMPANY_ACCOUNT = "ORG";
	public static final String MASTER_ACCOUNT = "MA";
	public static final String CURRENT_ACCOUNT = "CA";
	public static final String OPERATOR_ACCOUNT = "OA";
	public static final String SAVING_BANK_ACCOUNT = "SB";
	public static final String FARMER_ACC = "02";
	public static final String AGENT_ACC = "01";
	public static final String PAYMENT_MODE_CASH = "CS";
	public static final String PAYMENT_MODE_CREDIT = "CR";

	public static final String BASIX_ACCOUNT = "BASIX";

	private long id;
	private String accountNo;
	private String accountType;
	private int type;
	private Date accountOpenDate;
	private int status;
	private Date createTime;
	private Date updateTime;
	private String profileId;
	private double cashBalance;
	private double creditBalance;
	private double balance;
	private double distributionBalance;
	private double shareAmount;
	private double savingAmount;
	private double loanAmount;
	private double outstandingLoanAmount;
	private String loanAccountNo;
	private String branchId;
	private Set<AgroTransaction> agroTransactions;

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {

		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(long id) {

		this.id = id;
	}

	/**
	 * Gets the account no.
	 * 
	 * @return the account no
	 */
	@Column(name = "ACC_NO", length = 45)
	public String getAccountNo() {

		return accountNo;
	}

	/**
	 * Sets the account no.
	 * 
	 * @param accountNo
	 *            the new account no
	 */
	public void setAccountNo(String accountNo) {

		this.accountNo = accountNo;
	}

	/**
	 * Gets the account type.
	 * 
	 * @return the account type
	 */
	@Column(name = "ACC_TYPE", length = 45)
	public String getAccountType() {

		return accountType;
	}

	/**
	 * Sets the account type.
	 * 
	 * @param accountType
	 *            the new account type
	 */
	public void setAccountType(String accountType) {

		this.accountType = accountType;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the new type
	 */

	public void setType(int type) {

		this.type = type;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	@Column(name = "TYPEE", length = 2)
	public int getType() {

		return type;
	}

	/**
	 * Gets the account open date.
	 * 
	 * @return the account open date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACC_OPEN_DATE")
	public Date getAccountOpenDate() {

		return accountOpenDate;
	}

	/**
	 * Sets the account open date.
	 * 
	 * @param accountOpenDate
	 *            the new account open date
	 */
	public void setAccountOpenDate(Date accountOpenDate) {

		this.accountOpenDate = accountOpenDate;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	@Column(name = "STATUS", length = 2)
	public int getStatus() {

		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the new status
	 */
	public void setStatus(int status) {

		this.status = status;
	}

	/**
	 * Gets the create time.
	 * 
	 * @return the create time
	 */
	@Column(name = "CRE_TIME")
	public Date getCreateTime() {

		return createTime;
	}

	/**
	 * Sets the create time.
	 * 
	 * @param createTime
	 *            the new create time
	 */
	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	/**
	 * Gets the update time.
	 * 
	 * @return the update time
	 */
	@Column(name = "MOD_TIME")
	public Date getUpdateTime() {

		return updateTime;
	}

	/**
	 * Sets the update time.
	 * 
	 * @param updateTime
	 *            the new update time
	 */
	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}

	/**
	 * Gets the profile id.
	 * 
	 * @return the profile id
	 */
	@Column(name = "PROFILE_ID", length = 150)
	public String getProfileId() {

		return profileId;
	}

	/**
	 * Sets the profile id.
	 * 
	 * @param profileId
	 *            the new profile id
	 */
	public void setProfileId(String profileId) {

		this.profileId = profileId;
	}

	/**
	 * Gets the cash balance.
	 * 
	 * @return the cash balance
	 */
	@Column(name = "CASH_BALANCE", precision = 20, scale = 3)
	public double getCashBalance() {

		return cashBalance;
	}

	/**
	 * Sets the cash balance.
	 * 
	 * @param cashBalance
	 *            the new cash balance
	 */
	public void setCashBalance(double cashBalance) {

		this.cashBalance = cashBalance;
	}

	/**
	 * Gets the credit balance.
	 * 
	 * @return the credit balance
	 */
	@Column(name = "CREDIT_BALANCE", precision = 20, scale = 3)

	public double getCreditBalance() {

		return creditBalance;
	}

	/**
	 * Sets the credit balance.
	 * 
	 * @param creditBalance
	 *            the new credit balance
	 */
	public void setCreditBalance(double creditBalance) {

		this.creditBalance = creditBalance;
	}

	/**
	 * Sets the balance.
	 * 
	 * @param balance
	 *            the new balance
	 */
	public void setBalance(double balance) {

		this.balance = balance;
	}

	/**
	 * Gets the balance.
	 * 
	 * @return the balance
	 */
	@Column(name = "BALANCE", precision = 20, scale = 3)

	public double getBalance() {

		return balance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(ESEAccount account) {

		int value = -1;
		if (account instanceof ESEAccount) {
			ESEAccount temp = (ESEAccount) account;
			value = this.getAccountNo().compareTo(temp.getAccountNo());
		}
		return value;
	}

	/**
	 * Gets the agro transactions.
	 * 
	 * @return the agro transactions
	 */
	@OneToMany(mappedBy = "eseAccount")
	public Set<AgroTransaction> getAgroTransactions() {

		return agroTransactions;
	}

	/**
	 * Sets the agro transactions.
	 * 
	 * @param agroTransactions
	 *            the new agro transactions
	 */
	public void setAgroTransactions(Set<AgroTransaction> agroTransactions) {

		this.agroTransactions = agroTransactions;
	}

	/**
	 * Sets the distribution balance.
	 * 
	 * @param distributionBalance
	 *            the new distribution balance
	 */
	public void setDistributionBalance(double distributionBalance) {

		this.distributionBalance = distributionBalance;
	}

	/**
	 * Gets the distribution balance.
	 * 
	 * @return the distribution balance
	 */
	@Column(name = "DIST_BALANCE", precision = 20, scale = 3)

	public double getDistributionBalance() {

		return distributionBalance;
	}

	/**
	 * Gets the share amount.
	 * 
	 * @return the share amount
	 */
	@Column(name = "SHARE_AMOUNT", precision = 20, scale = 3)
	public double getShareAmount() {

		return shareAmount;
	}

	/**
	 * Sets the share amount.
	 * 
	 * @param shareAmount
	 *            the new share amount
	 */
	public void setShareAmount(double shareAmount) {

		this.shareAmount = shareAmount;
	}

	/**
	 * Gets the saving amount.
	 * 
	 * @return the saving amount
	 */
	@Column(name = "SAVING_AMOUNT", precision = 20, scale = 3)
	public double getSavingAmount() {

		return savingAmount;
	}

	/**
	 * Sets the saving amount.
	 * 
	 * @param savingAmount
	 *            the new saving amount
	 */
	public void setSavingAmount(double savingAmount) {

		this.savingAmount = savingAmount;
	}

	@Column(name = "BRANCH_ID", length = 150)
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Column(name = "LOAN_AMOUNT", precision = 20, scale = 3)
	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	@Column(name = "OUTSTANDING_AMOUNT", precision = 20, scale = 3)

	public double getOutstandingLoanAmount() {
		return outstandingLoanAmount;
	}

	public void setOutstandingLoanAmount(double outstandingLoanAmount) {
		this.outstandingLoanAmount = outstandingLoanAmount;
	}

	@Column(name = "LOAN_ACC_NO", length = 150)

	public String getLoanAccountNo() {
		return loanAccountNo;
	}

	public void setLoanAccountNo(String loanAccountNo) {
		this.loanAccountNo = loanAccountNo;
	}

}