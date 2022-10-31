/*
 * UniqueIDGenerator.java
 * Copyright (c) 2015-2016, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.service;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sourcetrace.eses.dao.ISequenceDAO;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

// TODO: Auto-generated Javadoc
@Service
@Transactional
public class UniqueIDGenerator implements IUniqueIDGenerator {

	private static final Logger LOGGER = Logger.getLogger(UniqueIDGenerator.class.getName());
	private static final int MAX_SEQUENCE = 999999;
	private static final int PREFIX_LENGTH = 6;
	private static final int CARD_PREFIX_LENGTH = 4;
	private static final int ACCOUNT_PREFIX_LENGTH = 4;
	private static final int PAYMENT_PREFIX_LENGTH = 4;
	private static final int CHART_NO_LENGTH = 4;
	private static final int WEB_DISTRIBUTION_LENGTH = 4;
	private static final int MTNT_RECEIPT_NO_PREFIX_LENGTH = 4;
	private static final int MTNR_RECEIPT_NO_PREFIX_LENGTH = 4;
	private static final int DISTRIBUTION_MTNT_PREFIX_LENGTH = 4;
	private static final int DISTRIBUTION_MTNR_PREFIX_LENGTH = 4;
	private static final int WEB_DISTRIBUTION_TO_FIELDSTAFF_LENGTH = 4;
	private static final int CONTRACT_NO_SEQ_LENGTH = 6;
	private static final int PRICE_PATTERN_CODE_SEQ_LENGTH = 3;
	private static final int PROCUREMENT_RECEIPT_NO_PREFIX_LENGTH = 7;
	private static final int CUSTOMER_ID_LENGTH = 5;
	private static final int RESEARCH_STATION_ID_SEQ = 5;
	private static final int CUSTOMER_PROJECT_CODE_LENGTH = 4;
	private static final int WEB_WAREHOUSE_STOCK_ENTRY = 6;
	private static final int VENDOR_ID_LENGTH = 5;
	private static final int ORGANIZATION_ID_LENGTH = 5;
	private static final int NEW_ACCOUNT_PREFIX_LENGTH = 5;

	private static final int LOCATION_PREFIX_LENGTH = 5;
	private static final int DEVICE_PREFIX_LENGTH = 4;

	private static final int GROUP_PREFIX_LENGTH = 5;
	private static final int WAREHOUSE_PREFIX_LENGTH = 5;

	private static final int PRODUCT_PREFIX_LENGTH = 4;

	private static final int CATALOGUE_PREFIX_LENGTH = 5;
	private static final int PRODUCT_ENROLL_PREFIX_LENGTH = 5;
	private static final int VARIETY_PREFIX_LENGTH = 5;
	private static final int GRADE_PREFIX_LENGTH = 5;
	private static final int FARM_PRODUCT_PREFIX_LENGTH = 5;
	private static final int P_PRODUCT_PREFIX_LENGTH = 5;
	private static final int SEASON_PREFIX_LENGTH = 5;
	private static final int CROP_HARVEST_RECEIPT_NO_PREFIX_LENGTH = 6;
	private static final int TRAINING_TOPIC_PREFIX_LENGTH = 5;
	private static final int TRAINING_CRITERIA_CATEGORY_PREFIX_LENGTH = 5;
	private static final int CRITERIA_PREFIX_LENGTH = 5;
	private static final int TARGET_GROUP_PREFIX_LENGTH = 5;
	private static final int TRAINING_METHOD_PREFIX_LENGTH = 5;
	private static final int WEB_PRODUCT_RETURN_FROM_FIELDSTAFF_LENGTH = 4;
	private static final int SEED_TRANSFER_RECEIPT_NO_PREFIX_LENGTH = 4;
	
	 private static final String SECTION_CODE_PREFIX_STRING = "S";	
	 private static final int SECTION_CODE_PREFIX_LENGTH = 3;
	private static final int PROCUREMENT_TRACEABILITY_RECEIPT_NO_PREFIX_LENGTH = 7;
	
	private static final int FARM_CROPS_MASTER_CODE_PREFIX_LENGTH = 4;
	private static final String FARM_CROPS_MASTER_CODE_PREFIX_STRING = "PA";
	private static final int WEB_DISTRIBUTION_STOCK_LENGTH=4;
	
	private static final int COLD_STORAGE_STOCK_TRANSFER_RECEIPT_NO = 6;
	private static final int DYNAMIC_MENU_CODE_PREFIX_LENGTH = 5;
	private static final int DYNAMIC_SECTION_CODE_PREFIX_LENGTH = 5;
	private static final int DYNAMIC_FIELD_CODE_PREFIX_LENGTH = 5;
	private static final int FARMER_CODE_PREFIX_LENGTH = 3;
	
	private static final int LOAN_DISTRIBUTION_RECEIPT_NO_PREFIX_LENGTH = 7;
	
	private static final int LOAN_ACCOUNT_NO =15;
	
	private static final int LOAN_REPAYMENT_RECEIPT_NO_PREFIX_LENGTH = 7;
	private static final int PERMIT_APPLN_NO =3;
	private static final int GROWER_PREFIX_LENGTH = 5;
	private static final int BREEDER_PREFIX_LENGTH = 5;
	public static final int FARM_TASK_LENGTH = 3;
	public static final int FARMCROP_TASK_LENGTH = 3;
	/** The sequence dao. */
	@Autowired
	private ISequenceDAO sequenceDAO;
	
//	protected HttpServletRequest request;

	/**
	 * Sets the sequence dao.
	 * 
	 * @param sequenceDAO
	 *            the new sequence dao
	 */
	public void setSequenceDAO(ISequenceDAO sequenceDAO) {

		this.sequenceDAO = sequenceDAO;
	}

	/**
	 * Gets the client dao.
	 * 
	 * @return the client dao
	 */
	/*
    /**
	 * Sets the client dao.
	 * 
	 * @param clientDAO
	 *            the new client dao
	 */
     /*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createAgentId()
	 */
	public String createAgentId() {

		String code = ESE_AGENT_ID;
		Long sequence = sequenceDAO.getAgentSequence();
		return createIdWithCodeMonthYearSequence(code, sequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createAgentCardId()
	 */
	public String createAgentCardId() {

		String code = AGENT_CARD;
		Long sequence = sequenceDAO.getAgentCardSequence();
		return createIdWithCodeMonthYearSequence(code, sequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createAgentDataCardId()
	 */
	public String createAgentDataCardId() {

		String code = AGENT_DATA_CARD;
		Long sequence = sequenceDAO.getAgentCardSequence();
		return createIdWithCodeMonthYearSequence(code, sequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createEnrollmentStationId()
	 */
	public String createEnrollmentStationId() {

		String code = ENROLLMENT_STATION;
		Long sequence = sequenceDAO.getEnrollmentStationSequence();
		return createIdWithCodeMonthYearSequence(code, sequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createPOSId()
	 */
	public String createPOSId() {

		String code = POS;
		Long sequence = sequenceDAO.getPOSSequence();
		return createIdWithCodeMonthYearSequence(code, sequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createTellerPOSId()
	 */
	public String createTellerPOSId() {

		String code = TELLER_POS;
		Long sequence = sequenceDAO.getTellerPOSSequence();
		return createIdWithCodeMonthYearSequence(code, sequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createKioskId()
	 */
	public String createKioskId() {

		String code = KIOSK;
		Long sequence = sequenceDAO.getKioskSequence();
		return createIdWithCodeMonthYearSequence(code, sequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createUserId()
	 */
	public String createUserId() {

		String code = USER;
		Long sequence = sequenceDAO.getUserSequence();
		return createIdWithCodeMonthYearSequence(code, sequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.util.IUniqueIDGenerator#createMDBUserId(java.lang.String,
	 * java.lang.String)
	 */
	public String createMDBUserId(String firstName, String lastName) {

		StringBuffer buffer = new StringBuffer();
		String firstNameFirst = firstName.substring(0, 1);
		String lastNameSix = lastName.substring(0, 6);
		buffer.append(firstNameFirst);
		buffer.append(lastNameSix);
		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createMDBPasswd()
	 */
	public String createMDBPasswd() {

		StringBuffer buffer = new StringBuffer();
		buffer.append(MDB_USER);
		int n = 6;
		char[] pw = new char[n];
		int c = 'A';
		int r1 = 0;
		for (int i = 0; i < n; i++) {
			r1 = (int) (Math.random() * 3);
			switch (r1) {
			case 0:
				c = '0' + (int) (Math.random() * 10);
				break;
			case 1:
				c = 'a' + (int) (Math.random() * 26);
				break;
			case 2:
				c = 'A' + (int) (Math.random() * 26);
				break;
			}
			pw[i] = (char) c;
		}
		buffer.append(pw);

		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createClientId()
	 */
	public String createClientId(String agentId) {

		String code = ESE_CLIENT_ID;
		String formate = null;

		if (StringUtil.isEmpty(agentId)) {
			formate = ESE_WEB_REQUEST + ESE_AGENT_EMPTY_ID;
			Long sequence = sequenceDAO.getClientSequence();
			String prefix = getPrefix(sequence.toString().length(), PREFIX_LENGTH);
			return code + formate + prefix + sequence;
		} else {
			formate = ESE_DEVICE_REQUEST + agentId.substring(agentId.length() - 6);
			String sequence = sequenceDAO.getClientSequenceforDevice(code + formate);
			if (StringUtil.isEmpty(sequence)) {
				return code + formate + ESE_EMPTY_ID;
			} else {
				return sequence;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createAffiliateId()
	 */
	public String createAffiliateId() {

		String code = AFFILIATE;
		Long sequence = sequenceDAO.getAffiliateSequence();
		return createIdWithCodeMonthYearSequence(code, sequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createProviderId()
	 */
	public String createProviderId() {

		String code = PROVIDER;
		Long sequence = sequenceDAO.getProviderSequence();
		return createIdWithCodeMonthYearSequence(code, sequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createServicePointId()
	 */
	public String createServicePointId() {

		String code = SERVICE_POINT;
		Long sequence = sequenceDAO.getServicePointSequence();
		return createIdWithCodeMonthYearSequence(code, sequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createStoreId()
	 */
	public String createStoreId() {

		String code = STORE;
		Long sequence = sequenceDAO.getStoreSequence();
		return createIdWithCodeMonthYearSequence(code, sequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createSwitchTxnId()
	 */
	public String createSwitchTxnId() {

		String code = SWITCH_TXN;
		Long sequence = sequenceDAO.getSwitchTxnSequence();
		return createIdWithCodeMonthYearSequence(code, sequence);
	}

	/**
	 * Creates the id with code month year sequence.
	 * 
	 * @param code
	 *            the code
	 * @param sequence
	 *            the sequence
	 * @return the string
	 */
	private String createIdWithCodeMonthYearSequence(String code, Long sequence) {

		StringBuffer buffer = new StringBuffer();
		buffer.append(code);

		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		month = month + 1;

		buffer.append(StringUtil.getExact(String.valueOf(month), 2));
		int year = calendar.get(Calendar.YEAR);
		buffer.append(Integer.toString(year).substring(2));

		sequence = (sequence != MAX_SEQUENCE) ? sequence % MAX_SEQUENCE : sequence;
		String prefix = getPrefix(sequence.toString().length(), PREFIX_LENGTH);
		buffer.append(prefix);
		buffer.append(sequence.toString());
		return buffer.toString();
	}

	/**
	 * Creates the id with code sub code sequence.
	 * 
	 * @param code
	 *            the code
	 * @param subCode
	 *            the sub code
	 * @param sequence
	 *            the sequence
	 * @return the string
	 */
	private String createIdWithCodeSubCodeSequence(String code, String subCode, Long sequence) {

		StringBuffer buffer = new StringBuffer();
		buffer.append(code);

		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		month = month + 1;

		buffer.append(StringUtil.getExact(String.valueOf(month), 2));
		int year = calendar.get(Calendar.YEAR);
		buffer.append(Integer.toString(year).substring(2));

		sequence = (sequence != MAX_SEQUENCE) ? sequence % MAX_SEQUENCE : sequence;
		String prefix = getPrefix(sequence.toString().length(), PREFIX_LENGTH);
		buffer.append(prefix);
		buffer.append(sequence.toString());
		return buffer.toString();
	}

	/**
	 * Gets the prefix.
	 * 
	 * @param length
	 *            the length
	 * @param max
	 *            the max
	 * @return the prefix
	 */
	private String getPrefix(int length, int max) {

		StringBuffer prefix = new StringBuffer("");
		for (int i = length; i < max; ++i) {
			prefix.append("0");
		}

		return prefix.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createESEAgentAccountId()
	 */
	public String createESEAgentAccountId(String agentId) {

		String code = ESE_AGENT_ACCOUNT;
		String formate = null;

		if (StringUtil.isEmpty(agentId)) {
			formate = ESE_WEB_REQUEST + ESE_AGENT_EMPTY_ID;
			Long sequence = sequenceDAO.getESEAgentAccountSequence();
			String prefix = getPrefix(sequence.toString().length(), PREFIX_LENGTH);
			return code + formate + prefix + sequence;
		} else {
			formate = ESE_DEVICE_REQUEST + agentId.substring(agentId.length() - 6);
			String sequence = sequenceDAO.getESEAccountSequenceforDevice(code + formate);
			if (StringUtil.isEmpty(sequence)) {
				return code + formate + ESE_EMPTY_ID;
			} else {
				return sequence;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.util.IUniqueIDGenerator#createESEClientAccountId(java
	 * .lang.String)
	 */
	public String createESEClientAccountId(String agentId) {

		String code = ESE_CLIENT_ACCOUNT;
		String formate = null;

		if (StringUtil.isEmpty(agentId)) {
			formate = ESE_WEB_REQUEST + ESE_AGENT_EMPTY_ID;
			Long sequence = sequenceDAO.getESEClientAccountSequence();
			String prefix = getPrefix(sequence.toString().length(), PREFIX_LENGTH);
			return code + formate + prefix + sequence;
		} else {
			formate = ESE_DEVICE_REQUEST + agentId.substring(agentId.length() - 6);
			String sequence = sequenceDAO.getESEAccountSequenceforDevice(code + formate);
			if (StringUtil.isEmpty(sequence)) {
				return code + formate + ESE_EMPTY_ID;
			} else {
				return sequence;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.util.IUniqueIDGenerator#createESEAgentCardId(java.lang
	 * .String)
	 */
	public String createESEAgentCardId(String agentId) {

		String code = ESE_AGENT_CARD;
		String formate = null;

		if (StringUtil.isEmpty(agentId)) {
			formate = ESE_WEB_REQUEST + ESE_AGENT_EMPTY_ID;
			Long sequence = sequenceDAO.getESEAgentCardSequence();
			String prefix = getPrefix(sequence.toString().length(), PREFIX_LENGTH);
			return code + formate + prefix + sequence;
		} else {
			formate = ESE_DEVICE_REQUEST + agentId.substring(agentId.length() - 6);
			String sequence = sequenceDAO.getESECardSequenceforDevice(code + formate);
			if (StringUtil.isEmpty(sequence)) {
				return code + formate + ESE_EMPTY_ID;
			} else {
				return sequence;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.util.IUniqueIDGenerator#createESEClientCardId(java.lang
	 * .String)
	 */
	public String createESEClientCardId(String agentId) {

		String code = ESE_CLIENT_CARD;
		String formate = null;

		if (StringUtil.isEmpty(agentId)) {
			formate = ESE_WEB_REQUEST + ESE_AGENT_EMPTY_ID;
			Long sequence = sequenceDAO.getESEClientCardSequence();
			String prefix = getPrefix(sequence.toString().length(), PREFIX_LENGTH);
			return code + formate + prefix + sequence;
		} else {
			formate = ESE_DEVICE_REQUEST + agentId.substring(agentId.length() - 6);
			String sequence = sequenceDAO.getESECardSequenceforDevice(code + formate);
			if (StringUtil.isEmpty(sequence)) {
				return code + formate + ESE_EMPTY_ID;
			} else {
				return sequence;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createBatchNumber()
	 */
	public String createBatchNumber() {

		Long sequence = sequenceDAO.createBatchNumber();
		return String.valueOf(sequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createApprovalCode()
	 */
	public String createApprovalCode() {

		Long sequence = sequenceDAO.createApprovalCode();
		String approvalSequence = getPrefix(sequence.toString().length(), PREFIX_LENGTH);
		return approvalSequence + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createTxnSequence()
	 */
	public String createTxnSequence() {

		Long sequence = sequenceDAO.getTxnSequence();
		return String.valueOf(sequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#agentInternalIdSequence()
	 */
	public String createAgentInternalIdSequence() {

		Long sequence = sequenceDAO.getAgentInternalIdSequence();
		String agentSequence = getPrefix(sequence.toString().length(), CARD_PREFIX_LENGTH);
		return agentSequence + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createAgentCardIdSequence()
	 */
	public String createAgentCardIdSequence() {

		String formate = null;
		formate = ESE_AGENT_CARD_ID + WEB_REQUEST + ESE_AGENT_INTERNAL_ID;
		Long sequence = sequenceDAO.getAgentCardIdSequence();
		String cardSequence = getPrefix(sequence.toString().length(), CARD_PREFIX_LENGTH);
		return formate + cardSequence + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.util.IUniqueIDGenerator#createShopDealerCardIdSequence
	 * (java.lang.String, java.lang.String)
	 */
	public String createShopDealerCardIdSequence(String deviceType, String agentInternalId) {

		Long sequence = sequenceDAO.getShopDealerCardIdSequence();
		return createCardSequence(ESE_SHOP_DALER_CARD_ID, deviceType, agentInternalId, sequence);
	}

	/**
	 * Creates the card sequence.
	 * 
	 * @param enrollType
	 *            the enroll type
	 * @param deviceType
	 *            the device type
	 * @param agentInternalId
	 *            the agent internal id
	 * @param sequence
	 *            the sequence
	 * @return the string
	 */
	private String createCardSequence(String enrollType, String deviceType, String agentInternalId, Long sequence) {

		String cardSequence = getPrefix(sequence.toString().length(), CARD_PREFIX_LENGTH);
		return enrollType + deviceType + agentInternalId + cardSequence + sequence;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.util.IUniqueIDGenerator#createFarmerCardIdSequence(java
	 * .lang.String, java.lang.String)
	 */
	public String createFarmerCardIdSequence(String deviceType, String agentInternalId) {

		Long sequence = sequenceDAO.getFarmerCardIdSequence();
		return createCardSequence(ESE_FARMER_CARD_ID, deviceType, agentInternalId, sequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.util.IUniqueIDGenerator#createAgentAccountNoSequence()
	 */
	public String createAgentAccountNoSequence() {

		String formate = null;
		formate = ESE_AGENT_ACCOUNT_NO + WEB_REQUEST + ESE_AGENT_INTERNAL_ID;
		Long sequence = sequenceDAO.getAgentAccountNoSequence();
		String accountSequence = getPrefix(sequence.toString().length(), ACCOUNT_PREFIX_LENGTH);
		return formate + accountSequence + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.util.IUniqueIDGenerator#createFarmerAccountNoSequence
	 * (java.lang.String, java.lang.String)
	 */
	public String createFarmerAccountNoSequence(String deviceType, String agentInternalId) {

		Long sequence = sequenceDAO.getFarmerAccountNoSequence();
		return createAccountSequence(ESE_FARMER_ACCOUNT_NO, deviceType, agentInternalId, sequence);
	}

	/**
	 * Creates the account sequence.
	 * 
	 * @param enrollType
	 *            the enroll type
	 * @param deviceType
	 *            the device type
	 * @param agentInternalId
	 *            the agent internal id
	 * @param sequence
	 *            the sequence
	 * @return the string
	 */
	private String createAccountSequence(String enrollType, String deviceType, String agentInternalId, Long sequence) {

		String accountSequence = getPrefix(sequence.toString().length(), ACCOUNT_PREFIX_LENGTH);
		return enrollType + deviceType + agentInternalId + accountSequence + sequence;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createTruckSequence()
	 */
	public String createTruckSequence() {

		Long sequence = sequenceDAO.getTruckUniqueIdSeq();
		return getPrefix(sequence.toString().length(), PREFIX_LENGTH) + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#getFarmerIdSeq()
	 */
	public String getFarmerRemoteIdSeq() {

		return sequenceDAO.getFarmerRemoteIdSeq();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#getFarmerWebIdSeq()
	 */
	public String getFarmerWebIdSeq() {

		return sequenceDAO.getFarmerWebIdSeq();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#getShopDealerRemoteIdSeq()
	 */
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#getShopDealerWebIdSeq()
	 */
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#getDistributionSeq()
	 */
	public String getDistributionSeq() {

		Long sequence = sequenceDAO.getDistributionSeq();
		return getPrefix(sequence.toString().length(), WEB_DISTRIBUTION_LENGTH) + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#getPaymentSeqId()
	 */
	public String getPaymentSeqId() {

		Long sequence = sequenceDAO.getPaymentIdSeq();
		return getPrefix(sequence.toString().length(), PAYMENT_PREFIX_LENGTH) + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#getProcurementChartNoSeq()
	 */
	public String getProcurementChartNoSeq() {

		Long sequence = sequenceDAO.getProcurementChartNoSeq();
		return getPrefix(sequence.toString().length(), CHART_NO_LENGTH) + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#getMTNTReceiptNoSeq()
	 */
	public String getMTNTReceiptNoSeq() {

		Long sequence = sequenceDAO.getMTNTReceiptNoSeq();
		return getPrefix(sequence.toString().length(), MTNT_RECEIPT_NO_PREFIX_LENGTH) + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#getMTNRReceiptNoSeq()
	 */
	public String getMTNRReceiptNoSeq() {

		Long sequence = sequenceDAO.getMTNRReceiptNoSeq();
		return getPrefix(sequence.toString().length(), MTNR_RECEIPT_NO_PREFIX_LENGTH) + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#getDistributionMTNTIdSeq()
	 */
	public String getDistributionMTNTIdSeq() {

		Long sequence = sequenceDAO.getDistributionMTNTIdSeq();
		return getPrefix(sequence.toString().length(), DISTRIBUTION_MTNT_PREFIX_LENGTH) + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#getDistributionMTNRIdSeq()
	 */
	public String getDistributionMTNRIdSeq() {

		Long sequence = sequenceDAO.getDistributionMTNRIdSeq();
		return getPrefix(sequence.toString().length(), DISTRIBUTION_MTNR_PREFIX_LENGTH) + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.util.IUniqueIDGenerator#getDistributionToFieldStaffSeq()
	 */
	public String getDistributionToFieldStaffSeq() {

		Long sequence = sequenceDAO.getDistributionToFieldStaffSeq();
		return getPrefix(sequence.toString().length(), WEB_DISTRIBUTION_TO_FIELDSTAFF_LENGTH) + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createContractNoSequence()
	 */
	public String createContractNoSequence() {

		Long sequence = sequenceDAO.getContractNoSequence();
		return getPrefix(sequence.toString().length(), CONTRACT_NO_SEQ_LENGTH) + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.util.IUniqueIDGenerator#createPricePatternCodeSequence()
	 */
	public String createPricePatternCodeSequence() {

		Long sequence = sequenceDAO.getPricePatternCodeSequence();
		return getPrefix(sequence.toString().length(), PRICE_PATTERN_CODE_SEQ_LENGTH) + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#getProcurementReceiptNoSeq()
	 */
	public String getProcurementReceiptNoSeq() {

		Long sequence = sequenceDAO.getProcurementReceiptNoSeq();
		return getPrefix(sequence.toString().length(), PROCUREMENT_RECEIPT_NO_PREFIX_LENGTH) + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.util.IUniqueIDGenerator#createCustomerId()
	 */
	public String createCustomerId() {

		StringBuffer buffer = new StringBuffer();
		try {
			Long sequence = sequenceDAO.getCustomerSequence();
			buffer.append(StringUtil.getExact(String.valueOf(sequence), CUSTOMER_ID_LENGTH));
		} catch (Exception e) {
			LOGGER.info("Error at Customer Unique id creation time");
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.util.IUniqueIDGenerator#createCustomerProjectCode(java
	 * .lang.String)
	 */
	

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.ese.service.util.IUniqueIDGenerator#
	 * getWarehouseStockEntryReceiptNumberSeq()
	 */
	public String getWarehouseStockEntryReceiptNumberSeq() {

		Long sequence = sequenceDAO.getWarehouseStockEntryReceiptNumberSeq();
		return getPrefix(sequence.toString().length(), WEB_WAREHOUSE_STOCK_ENTRY) + sequence;
	}

	/**
	 * Create vendor id.
	 * 
	 * @return the string
	 */
	public String createVendorId() {

		StringBuffer buffer = new StringBuffer();
		try {
			Long sequence = sequenceDAO.getVendorSequence();
			buffer.append(StringUtil.getExact(String.valueOf(sequence), VENDOR_ID_LENGTH));
		} catch (Exception e) {
			LOGGER.info("Error at Vendor Unique id creation time");
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.util.IUniqueIDGenerator#createCustomerAccountNoSequence
	 * (java.lang.String)
	 */
	public String createCustomerAccountNoSequence(String customerId) {

		String formate = null;
		formate = ESE_CUSTOMER_ACCOUNT + customerId;
		Long sequence = sequenceDAO.getCustomerAccountNoSequence();
		String accountSequence = getPrefix(sequence.toString().length(), NEW_ACCOUNT_PREFIX_LENGTH);
		return formate + accountSequence + sequence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.util.IUniqueIDGenerator#createVendorAccountNoSequence
	 * (java.lang.String)
	 */
	public String createVendorAccountNoSequence(String vendorId) {

		String formate = null;
		formate = ESE_VENDOR_ACCOUNT_NO + vendorId;
		Long sequence = sequenceDAO.getVendorAccountNoSequence();
		String accountSequence = getPrefix(sequence.toString().length(), NEW_ACCOUNT_PREFIX_LENGTH);
		return formate + accountSequence + sequence;
	}

	public String getCountryIdSeq() {

		Long sequence = sequenceDAO.getCountryIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), LOCATION_PREFIX_LENGTH);
		return COUNTRY_PREFIX_CHAR + remainingChar + sequence;
	}

	public String getStateIdSeq() {

		Long sequence = sequenceDAO.getStateIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), LOCATION_PREFIX_LENGTH);
		return STATE_PREFIX_CHAR + remainingChar + sequence;
	}

	public String getDistrictIdSeq() {

		Long sequence = sequenceDAO.getDistrictIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), LOCATION_PREFIX_LENGTH);
		return DISTRICT_PREFIX_CHAR + remainingChar + sequence;
	}

	public String getMandalIdSeq() {

		Long sequence = sequenceDAO.getMandalIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), LOCATION_PREFIX_LENGTH);
		return MANDAL_PREFIX_CHAR + remainingChar + sequence;
	}

	public String getVillageIdSeq() {
		String remainingChar ="";
		Long sequence = sequenceDAO.getVillageIdSeq();
		if(getBranchId()!=null&&getBranchId().equalsIgnoreCase("sticky")){
		 remainingChar = getPrefix(sequence.toString().length(), 6);
		}else{
			 remainingChar = getPrefix(sequence.toString().length(), LOCATION_PREFIX_LENGTH);
		}
		return VILLAGE_PREFIX_CHAR + remainingChar + sequence;

	}

	public String getDeviceIdSeq() {

		Long sequence = sequenceDAO.getDeviceIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), DEVICE_PREFIX_LENGTH);
		return DEVICE_PREFIX_CHAR + remainingChar + sequence;

	}

	public String getGroupIdSeq() {

		Long sequence = sequenceDAO.getGroupIdSeq();
		String groupSequence = getPrefix(sequence.toString().length(), GROUP_PREFIX_LENGTH);
		return GROUP_PREFIX_CHAR + groupSequence + sequence;
	}

	

	@Override
	public String getTxnIdSeq() {

		return String.valueOf(sequenceDAO.getTxnIdSeq());
	}

	@Override
	public String getCatalogueIdSeq() {

		Long sequence = sequenceDAO.getCatalogueIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), CATALOGUE_PREFIX_LENGTH);
		return CATALOGUE_PREFIX_CHAR + remainingChar + sequence;
	}

	@Override
	public String getCropHarvestReceiptNumberSeq() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getCropHarvestReceiptNumberSeq();
		return getPrefix(sequence.toString().length(), CROP_HARVEST_RECEIPT_NO_PREFIX_LENGTH) + sequence;
	}

	@Override
	public String getProductEnrollIdSeq() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getProductEnrollIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), PRODUCT_ENROLL_PREFIX_LENGTH);
		return PRODUCT_ENROLL_CHAR + remainingChar + sequence;
	}

	

	

	@Override
	public String getSubCategoryIdSeq() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getSubCategoryIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), FARM_PRODUCT_PREFIX_LENGTH);
		return SUB_CATEGORY_CHAR + remainingChar + sequence;
	}

	@Override
	public String getProductIdSeq() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getProductIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), P_PRODUCT_PREFIX_LENGTH);
		return PRODUCT_CHAR + remainingChar + sequence;
	}

	@Override
	public String getSeasonIdSeq() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getSeasonIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), SEASON_PREFIX_LENGTH);
		return SEASON_CHAR + remainingChar + sequence;
	}

	@Override
	public String getTrainingTopicIdSeq() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getTrainingTopicIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), TRAINING_TOPIC_PREFIX_LENGTH);
		return TRAINING_TOPIC_CHAR + remainingChar + sequence;
	}

	@Override
	public String getTrainingCriteriaCategoryIdSeq() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getTrainingCriteriaCategoryIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), TRAINING_CRITERIA_CATEGORY_PREFIX_LENGTH);
		return TRAINING_CRITERIA_CATEGORY_CHAR + remainingChar + sequence;
	}

	@Override
	public String getCriteriaIdSeq() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getCriteriaIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), CRITERIA_PREFIX_LENGTH);
		return CRITERIA_CHAR + remainingChar + sequence;
	}

	@Override
	public String getTargetGroupIdSeq() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getTargetGroupIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), TARGET_GROUP_PREFIX_LENGTH);
		return TARGET_GROUP_CHAR + remainingChar + sequence;
	}

	@Override
	public String getTrainingMethodIdSeq() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getTrainingMethodIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), TRAINING_METHOD_PREFIX_LENGTH);
		return TRAINING_METHOD_CHAR + remainingChar + sequence;
	}

	@Override
	public String createOrganizationAccountNoSequence() {
		// TODO Auto-generated method stub
		String formate = null;
		formate = ESE_ORGANIZATION_ACCOUNT_NO;
		Long sequence = sequenceDAO.getOrganizationAccountNoSequence();
		String accountSequence = getPrefix(sequence.toString().length(), ACCOUNT_PREFIX_LENGTH);
		return formate + accountSequence + sequence;
	}

	@Override
	public String getGramPanchayatIdSeq() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getGramPanchayatIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), LOCATION_PREFIX_LENGTH);
		return GRAMPANCHAYAT_PREFIX_CHAR + remainingChar + sequence;
	}

	@Override
	public String getFarmerWebIdSeq(int farmerIdLength, long farmerMaxRange) {
		return sequenceDAO.getFarmerWebIdSeq(farmerIdLength, farmerMaxRange);
	}

	@Override
	public String getFarmerRemoteIdSeq(int farmerIdLength, long farmerMaxRange) {
		return sequenceDAO.getFarmerRemoteIdSeq(farmerIdLength, farmerMaxRange);
	}

	@Override
	public String getCatalougeTypeSeq() {
		Long sequence = sequenceDAO.getCatalogueIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), CATALOGUE_PREFIX_LENGTH);
		return String.valueOf(sequence);
	}

	@Override
	public String getGroupHHIdSeq(String code) {
		String sequence = sequenceDAO.getGroupHHIdSeq(code);
		String seqVal = "";
		if (!StringUtil.isEmpty(sequence)) {
			BigInteger bigInt = new BigInteger(sequence.trim());
			seqVal = bigInt.add(BigInteger.ONE).toString();
		} else {
			seqVal = code + "01";
		}
		return seqVal;
	}

	@Override
	public String getFarmerHHIdSeq(String code) {
		String sequence = sequenceDAO.getFarmerHHIdSeq(code);
		String seqVal = "";
		if (!StringUtil.isEmpty(sequence)) {
			BigInteger bigInt = new BigInteger(sequence.trim());
			seqVal = bigInt.add(BigInteger.ONE).toString();
			//seqVal=StringUtil.getExact(seqVal, 2);
		} else {
			seqVal = code + "01";
		}
		return seqVal;
	}

	@Override
	public String getLocalityHHIdSeq() {
		Long sequence = sequenceDAO.getDistrictCodeSeq();
		String seqVal="";
		if(!StringUtil.isEmpty(sequence)){
			Double d = new Double(sequence);
			seqVal=String.valueOf(d.intValue());
		}else{
			seqVal="1";
		}
		return seqVal;
	}

	@Override
	public String getMunicipalityHHIdSeq(String code) {
		String sequence = sequenceDAO.getMandalHHId(code);
		String seqVal="";
		if(!StringUtil.isEmpty(sequence)){
			BigInteger bigInt = new BigInteger(sequence.trim());
			seqVal = bigInt.add(BigInteger.ONE).toString();
		}else{
			seqVal=code+"1";
		}
		return seqVal;
	}

	@Override
	public String getGramPanchayatHHIdSeq(String code) {
		// TODO Auto-generated method stub
		String sequence = sequenceDAO.getGramPanchayathHHId(code);
		String seqVal="";
		if(!StringUtil.isEmpty(sequence)){
			BigInteger bigInt = new BigInteger(sequence.trim());
			seqVal = bigInt.add(BigInteger.ONE).toString();
		}else{
			seqVal=code+"01";
		}
		return seqVal;
	}

	@Override
	public String getVillageHHIdSeq(String code) {
		// TODO Auto-generated method stub
		String sequence = sequenceDAO.getVillageHHIdSeq(code);
		String seqVal="";
		if(!StringUtil.isEmpty(sequence)){
			BigInteger bigInt = new BigInteger(sequence.trim());
			seqVal = bigInt.add(BigInteger.ONE).toString();
		}else{
			seqVal=code+"01";
		}
		return seqVal;
	}

    @Override
    public String getFarmWebIdSeq() {
        return sequenceDAO.getFarmWebIdSeq();
    }

    @Override
    public String getFarmerWebCodeIdSeq(String city,String gp) {
        String sequence =sequenceDAO.getFarmerWebCodeIdSeq(city,gp);
        String seqVal="";
        if(!StringUtil.isEmpty(sequence)){
            String seq=sequence.substring(2, 6);
            BigInteger bigInt = new BigInteger(seq.trim());
            seqVal = bigInt.add(BigInteger.ONE).toString();
            seqVal=(city+gp)+StringUtil.getExact(seqVal, 4);
        }else{
            seqVal=(city+gp)+StringUtil.getExact("1", 4);
        }
        return seqVal;
    }

    @Override
    public String getFarmWebCodeIdSeq(String city, String gp) {
        String sequence =sequenceDAO.getFarmWebCodeIdSeq(city,gp);
        String seqVal="";
        if(!StringUtil.isEmpty(sequence)){
            String seq=sequence.substring(2, 6);
            BigInteger bigInt = new BigInteger(seq.trim());
            seqVal = bigInt.add(BigInteger.ONE).toString();
            seqVal=(city+gp)+StringUtil.getExact(seqVal, 4);
        }else{
            seqVal=(city+gp)+StringUtil.getExact("1", 4);
        }
        return seqVal;
    }

    
    public String createResearchStationId() {

        StringBuffer buffer = new StringBuffer();
        try {
            Long sequence = sequenceDAO.getResearchStationSequence();
            buffer.append(StringUtil.getExact(String.valueOf(sequence), RESEARCH_STATION_ID_SEQ));
        } catch (Exception e) {
            LOGGER.info("Error at ResearchStation Unique id creation time");
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public String createResearchStationAccountNoSequence(String researchStationId) {

        String formate = null;
        formate = ESE_RESEARCHSTATION_ACCOUNT_NO + researchStationId;
        Long sequence = sequenceDAO.getVendorAccountNoSequence();
        String accountSequence = getPrefix(sequence.toString().length(), NEW_ACCOUNT_PREFIX_LENGTH);
        return formate + accountSequence + sequence;
    }
    
   
	public String getProductReturnFromFieldStaffSeq() {

		Long sequence = sequenceDAO.getProductReturnFromFieldStaffSeq();
		return getPrefix(sequence.toString().length(), WEB_PRODUCT_RETURN_FROM_FIELDSTAFF_LENGTH) + sequence;
	}

	@Override
	public String getSeedTransferReceiptNoSeq() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getSeedTransferReceiptNoSeq();
		return getPrefix(sequence.toString().length(), SEED_TRANSFER_RECEIPT_NO_PREFIX_LENGTH) + sequence;
	}
	@Override
	public String getObservationsSeqId() {
		Long sequence = sequenceDAO.getObservationsSeqId();
		String remainingChar = getPrefix(sequence.toString().length(), TRAINING_METHOD_PREFIX_LENGTH);
		return OBSERVATION_CHAR + remainingChar + sequence;
	}

	@Override
	public String getSurveySectionCodeSeq() {
		Long sequence = sequenceDAO.getSurveySectionCodeSeq();
		String surveySectionCodeSequencePrex = getPrefix(sequence.toString().length(),
		                SECTION_CODE_PREFIX_LENGTH);
		return SECTION_CODE_PREFIX_STRING + surveySectionCodeSequencePrex + sequence;
		   
	}
	
	@Override
	public String getFarmerWebIdSeqWithBranch(int farmerIdLength,String branch) {
		return sequenceDAO.getFarmerWebIdSeqWithBranch(farmerIdLength,branch);
	}

	@Override
	public String getFarmerRemoteIdSeqWithBranch(int farmerIdLength,String branch) {
		return sequenceDAO.getFarmerRemoteIdSeqWithBranch(farmerIdLength,branch);
	}

	@Override
	public String getProcurementTraceabilityReceiptNoSeq() {
		Long sequence = sequenceDAO.getProcurementTraceabilityReceiptNoSeq();
		return getPrefix(sequence.toString().length(), PROCUREMENT_TRACEABILITY_RECEIPT_NO_PREFIX_LENGTH) + sequence;
	}

	@Override
	public String getDynmaicTxnType() {
		return String.valueOf(sequenceDAO.getDynmaicTxnType());
	}
	
	

	@Override
	public String getDistributionStockSeq() {

		Long sequence = sequenceDAO.getDistributionStockSeq();
		return getPrefix(sequence.toString().length(), WEB_DISTRIBUTION_STOCK_LENGTH) + sequence;
	}
	
	public String getBranchMasterWebSeq(String branch){
		String sequence = String.valueOf(sequenceDAO.getGroupHHIdSeqFromBranchMasterWebSeq(branch));
		String seqVal = "";
		if (!StringUtil.isEmpty(sequence)) {
			BigInteger bigInt = new BigInteger(sequence.trim());
			seqVal = bigInt.add(BigInteger.ONE).toString();
			
		} else {
			seqVal = branch + "01";
		}
		String groupSequence = getPrefix(seqVal.toString().length(), PRICE_PATTERN_CODE_SEQ_LENGTH);
		return groupSequence + seqVal;
	}
	
	public String getColdStorageStockTransferReceiptNoSeq() {

		Long sequence = sequenceDAO.getColdStorageStockTransferReceiptNoSeq();
		return getPrefix(sequence.toString().length(), COLD_STORAGE_STOCK_TRANSFER_RECEIPT_NO) + sequence;
	}
	
	@Override
	public String getDynamicMenuCodeSeq() {

		Long sequence = sequenceDAO.getDynamicMenuCodeSeq();
		String remainingChar = getPrefix(sequence.toString().length(), DYNAMIC_MENU_CODE_PREFIX_LENGTH);
		return DYNAMIC_MENU_CODE_PREFIX_CHAR + remainingChar + sequence;
	}
	
	@Override
	public String getDynamicSectionCodeSeq() {

		Long sequence = sequenceDAO.getDynamicSectionCodeSeq();
		String remainingChar = getPrefix(sequence.toString().length(), DYNAMIC_SECTION_CODE_PREFIX_LENGTH);
		return DYNAMIC_SECTION_CODE_PREFIX_CHAR + remainingChar + sequence;
	}
	
	@Override
	public String getDynamicFieldCodeSeq() {

		Long sequence = sequenceDAO.getDynamicFieldCodeSeq();
		String remainingChar = getPrefix(sequence.toString().length(), DYNAMIC_FIELD_CODE_PREFIX_LENGTH);
		return DYNAMIC_FIELD_CODE_PREFIX_CHAR + remainingChar + sequence;
	}

	public static int getDynamicMenuCodePrefixLength() {
		return DYNAMIC_MENU_CODE_PREFIX_LENGTH;
	}
	
	public String getBranchId() {

			HttpServletRequest request = ReflectUtil.getCurrentHttpRequest();
			//request = ReflectUtil.getCurrentHttpRequest();
		Object biObj = ObjectUtil.isEmpty(request) ? null
				: request.getSession().getAttribute(ISecurityFilter.CURRENT_BRANCH);
		return ObjectUtil.isEmpty(biObj) ? null : biObj.toString();
	}
	@Override
	public String getFarmerCodeSeq() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getFarmerCodeSeq();
		String remainingChar = getPrefix(sequence.toString().length(), FARMER_CODE_PREFIX_LENGTH);
		return remainingChar + sequence;
	}
	
	public String getLoanDistributionReceiptNoSeq() {

		Long sequence = sequenceDAO.getLoanDistributionReceiptNoSeq();
		return getPrefix(sequence.toString().length(), LOAN_DISTRIBUTION_RECEIPT_NO_PREFIX_LENGTH) + sequence;
	}
	
	@Override
	public String getLoanAccountNoSeq() {
		Long sequence = sequenceDAO.getLoanAccountNoSeq();
		return getPrefix(sequence.toString().length(), LOAN_ACCOUNT_NO) + sequence;
	}
	
	public String getLoanRepaymentReceiptNoSeq() {

		Long sequence = sequenceDAO.getLoanRepaymentReceiptNoSeq();
		return getPrefix(sequence.toString().length(), LOAN_REPAYMENT_RECEIPT_NO_PREFIX_LENGTH) + sequence;
	}

	@Override
	public String getFarmerIdSeq() {
		// TODO Auto-generated method stub
		return sequenceDAO.getFarmerIdSeq();
	}
	
	@Override
	public String getFarmIdSeq() {
		// TODO Auto-generated method stub
		String sequence = sequenceDAO.getFarmIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), FARM_TASK_LENGTH);
		return remainingChar + sequence;
		//return sequenceDAO.getFarmIdSeq();
		
	}
	
	@Override
	public String getFarmCropIdSeq() {
		// TODO Auto-generated method stub
		String sequence = sequenceDAO.getFarmCropIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), FARMCROP_TASK_LENGTH);
		return remainingChar + sequence;
		//return sequenceDAO.getFarmIdSeq();
		
	}
	
	@Override
	public String getFarmerTaskIdSeq() {
		// TODO Auto-generated method stub
		return sequenceDAO.getFarmerTaskIdSeq();
	}

	@Override
	public String getPermitIdSeq() {
		Long sequence = sequenceDAO.getPermitIdSeq();
		Date d=new Date();
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(d);
		String remainingChar = String.valueOf(calendar1.get(Calendar.YEAR));
		String breederSequence = getPrefix(sequence.toString().length(), 4);
		return PREMITAPPLN_PREFIX_CHAR + remainingChar+"/"+  breederSequence+ sequence;
	}
	
	

	@Override
	public String getDealerRegIdSeq(String prefix) {
		Long sequence =0l;
		if(prefix.equals("DE")){
			sequence=sequenceDAO.getDealerIdSeq("DEALER_REG_ID_SEQ");
		}else if(prefix.equals("AC")){
			sequence=sequenceDAO.getDealerIdSeq("CHEMICAL_REG_ID_SEQ");
		}else if(prefix.equals("PR")){
			sequence=sequenceDAO.getDealerIdSeq("PREMISES_REG_ID_SEQ");
		}else if(prefix.equals("FU")){
			sequence=sequenceDAO.getDealerIdSeq("FUM_REG_ID_SEQ");
		}
		
		String breederSequence = getPrefix(sequence.toString().length(), 4);
		Date d=new Date();
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(d);
		String remainingChar = String.valueOf(calendar1.get(Calendar.YEAR));
		return DEALERREG_PREFIX_CHAR +prefix+"/"+ remainingChar+"/"+  breederSequence+sequence;
	}
	
	

	@Override
	public String getFarmerIdSequ() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getFarmerIdSequ("FARMER_CODE_SEQ");
		String prprefix=DEALERREG_PREFIX_CHAR+"FA/";
		Date d=new Date();
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(d);
		String remainingChar = String.valueOf(calendar1.get(Calendar.YEAR));
		String breederSequence = getPrefix(sequence.toString().length(), 4);
		return prprefix+remainingChar+"/"+breederSequence+sequence;
	}

	@Override
	public String getHarvestIdSeq() {
		Long sequence = sequenceDAO.getHarvestIdSeq();
		Date d=new Date();
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(d);
		String remainingChar = String.valueOf(calendar1.get(Calendar.YEAR));
		String breederSequence = getPrefix(sequence.toString().length(), 2);
		System.out.println("At 1567==>"+PREMITAPPLN_PREFIX_CHAR + HARVEST_PREFIX_CHAR+"/"+  remainingChar.substring(0,1)+"/"+  breederSequence+ sequence);
		return PREMITAPPLN_PREFIX_CHAR + HARVEST_PREFIX_CHAR+"/"+  remainingChar.substring(2,4)+"/"+  breederSequence+ sequence;
	}

	@Override
	public String getWarehouseIdSeq() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getWarehouseIdSeq();
		String warehouseSequence = getPrefix(sequence.toString().length(), WAREHOUSE_PREFIX_LENGTH);
		return WAREHOUSE_PREFIX_CHAR + warehouseSequence + sequence;
	}

	@Override
	public String getProcurementVarietyIdSeq() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getProcurementVarietyIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), VARIETY_PREFIX_LENGTH);
		return VARIETY_CHAR + remainingChar + sequence;
	}

	@Override
	public String getProcurementGradeIdSeq() {
		// TODO Auto-generated method stub
		Long sequence = sequenceDAO.getProcurementGradeIdSeq();
		String remainingChar = getPrefix(sequence.toString().length(), GRADE_PREFIX_LENGTH);
		return GRADE_CHAR + remainingChar + sequence;
	}
	
	

	
}