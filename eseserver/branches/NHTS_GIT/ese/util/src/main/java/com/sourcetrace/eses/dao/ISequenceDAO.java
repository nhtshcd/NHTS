/*
 * ISequenceDAO.java
 * Copyright (c) 2015-2016, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.dao;

// TODO: Auto-generated Javadoc
public interface ISequenceDAO extends IESEDAO {

	public static final String AGENT = "AGENT_SEQ";
	public static final String CLIENT = "CLIENT_SEQ";
	public static final String USER = "USER_SEQ";
	public static final String PROVIDER = "PROV_SEQ";
	public static final String AFFILIATE = "AFF_SEQ";
	public static final String SWITCH_TXN = "TXN_CFG_SEQ";
	public static final String ENROLLMENT_STATION = "ES_SEQ";
	public static final String POS = "POS_SEQ";
	public static final String KIOSK = "KIOSK_SEQ";
	public static final String SERVICE_POINT = "SERV_POINT_SEQ";
	public static final String TELLER_POS = "TPOS_SEQ";
	public static final String AGENT_CARD = "AGENT_CARD_SEQ";
	public static final String TXN = "TXN_SEQ";
	public static final String STORE = "STORE_SEQ";
	public static final String RECEIPT = "RCPT_SEQ";

	public static final String AGENT_CARD_SEQ = "AGENT_CARD_SEQ";
	public static final String AGENT_ACCT_SEQ = "AGENT_ACCOUNT_SEQ";
	public static final String ORGANIZATION_ACCOUNT_SEQ = "ORGANIZATION_ACCOUNT_NO_SEQ";
	public static final String CLIENT_CARD_SEQ = "CLIENT_CARD_SEQ";
	public static final String CLIENT_ACCT_SEQ = "CLIENT_ACCOUNT_SEQ";
	public static final String BATCH_NO_SEQ = "BATCH_NO_SEQ";
	public static final String APR_CODE_SEQ = "APR_CODE_SEQ";

	public static final String AGENT_CARD_ID_SEQ = "AGENT_CARD_ID_SEQ";
	public static final String SHOP_DEALER_CARD_ID_SEQ = "SHOP_DEALER_CARD_ID_SEQ";
	public static final String FARMER_CARD_ID_SEQ = "FARMER_CARD_ID_SEQ";
	public static final String FARMER_ACCOUNT_NO_SEQ = "FARMER_ACCOUNT_NO_SEQ";
	public static final String TRUCK_UNIQUE_ID_SEQ = "TRUCK_UNIQUE_ID_SEQ";
	public static final String PAYMENT_UNIQUE_ID_SEQ = "PAYMENT_UNIQUE_ID_SEQ";
	public static final String PROCUREMENT_CHART_NO_SEQ = "PROCUREMENT_CHART_NO_SEQ";

	public static final String SHOP_DEALER_ID_SEQ = "SHOP_DEALER_ID_SEQ";
	public static final String DISTRIBUTION_SEQ = "DISTRIBUTION_SEQ";
	public static final String MTNT_RECEIPT_NO_SEQ = "MTNT_RECEIPT_NO_SEQ";
	public static final String MTNR_RECEIPT_NO_SEQ = "MTNR_RECEIPT_NO_SEQ";
	public static final String DISTRIBUTION_MTNT_RECEIPT_NO_SEQ = "DISTRIBUTION_MTNT_RECEIPT_NO_SEQ";
	public static final String DISTRIBUTION_MTNR_RECEIPT_NO_SEQ = "DISTRIBUTION_MTNR_RECEIPT_NO_SEQ";
	public static final String DISTRIBUTION_TO_FIELDSTAFF_SEQ = "DISTRIBUTION_TO_FIELDSTAFF_SEQ";
	public static final String CONTRACT_NO_SEQ = "CONTRACT_NO_SEQ";
	public static final String PRICE_PATTERN_CODE_SEQ = "PRICE_PATTERN_CODE_SEQ";
	public static final String PROCUREMENT_RECEIPT_NO_SEQ = "PROCUREMENT_RECEIPT_NO_SEQ";
	public static final String CUSTOMER_SEQ = "CUSTOMER_SEQ";
	public static final String CUSTOMER_PROJECT_SEQ = "CUSTOMER_PROJECT_SEQ";
	public static final String WEB_WAREHOUSE_STOCK_ENTRY_SEQ = "WEB_WAREHOUSE_STOCK_ENTRY_SEQ";
	public static final String VENDOR_ID_SEQ = "VENDOR_ID_SEQ";
	public static final String CUSTOMER_ID_SEQ = "CUSTOMER_ID_SEQ";
	public static final String VENDOR_ACCOUNT_SEQ = "VENDOR_ACCOUNT_SEQ";
	public static final String CUSTOMER_ACCOUNT_SEQ = "CUSTOMER_ACCOUNT_SEQ";

	public static final String COUNTRY_ID_SEQ = "COUNTRY_ID_SEQ";
	public static final String STATE_ID_SEQ = "STATE_ID_SEQ";
	public static final String DISTRICT_ID_SEQ = "DISTRICT_ID_SEQ";
	public static final String MANDAL_ID_SEQ = "MANDAL_ID_SEQ";
	public static final String VILLAGE_ID_SEQ = "VILLAGE_ID_SEQ";

	public static final String DEVICE_ID_SEQ = "DEVICE_ID_SEQ";
	public static final String GROUP_ID_SEQ = "GROUP_ID_SEQ";
	public static final String WAREHOUSE_ID_SEQ = "WAREHOUSE_ID_SEQ";

	public static final String SUBCATEGORY_ID_SEQ = "SUBCATEGORY_ID_SEQ";

	public static final String PRODUCT_ID_SEQ = "PRODUCT_ID_SEQ";
	public static final String PERMIT_APPLN_ID = "PERMIT_APPLN_ID";
	/** Farmer related id sequence constant */

	public static final int FARMER_TASK_LENGTH = 3;
	public static final int FARM_TASK_LENGTH = 3;
	public static final int FARMCROP_TASK_LENGTH = 3;
	public static final int FARMER_ID_LENGTH = 6;
	public static final int FARMER_SEQ_RANGE = 1000;
	public static final int FARMER_RESERVE_INDEX = 100;
	public static final int FARMER_ID_MAX_RANGE = 999999;

	/** ID Sequence Tables */
	public static final String AGENT_INTERNAL_ID_SEQ = "AGENT_INTERNAL_ID_SEQ";
	public static final String FARMER_ID_SEQ = "FARMER_ID_SEQ";
	public static final String TXN_ID_SEQ = "TXN_ID_SEQ";
	public static final String HAMLET_CODE_SEQ = "HAMLET_CODE_SEQ";
	public static final String VILLAGE_CODE_SEQ = "VILLAGE_CODE_SEQ";

	public static final String CLUSTER_CODE_SEQ = "CLUSTER_CODE_SEQ";
	public static final String TALUK_CODE_SEQ = "TALUK_CODE_SEQ";
	public static final String DISTRICT_CODE_SEQ = "DISTRICT_CODE_SEQ";
	public static final String GRAMPANCHAYAT_CODE_SEQ = "GRAMPANCHAYAT_CODE_SEQ";
	public static final String STATE_CODE_SEQ = "STATE_CODE_SEQ";
	public static final String COUNTRY_CODE_SEQ = "COUNTRY_CODE_SEQ";
	public static final String RANGE_CODE_SEQ = "RANGE_CODE_SEQ";
	public static final String MANDAL_CODE_SEQ = "MANDAL_CODE_SEQ";

	public static final String CATALOGUE_ID_SEQ = "CATALOGUE_ID_SEQ";
	public static final String PRODUCT_ENROLL_ID_SEQ = "PRODUCT_ENROLL_ID_SEQ";
	public static final String VARIETY_ID_SEQ = "VARIETY_ID_SEQ";
	public static final String GRADE_ID_SEQ = "GRADE_ID_SEQ";
	public static final String FARM_PRODUCT_ID_SEQ = "FARM_PRODUCT_ID_SEQ";
	public static final String PRODUCT_P_ID_SEQ = "PRODUCT_P_ID_SEQ";
	public static final String SEASON_ID_SEQ = "SEASON_ID_SEQ";
	public static final String TRAINING_TOPIC_ID_SEQ = "TRAINING_TOPIC_ID_SEQ";
	public static final String TRAINING_CRITERIA_CATEGORY_ID_SEQ = "TRAINING_CRITERIA_CATEGORY_ID_SEQ";
	public static final String CRITERIA_ID_SEQ = "CRITERIA_ID_SEQ";
	public static final String TARGET_GROUP_ID_SEQ = "TARGET_GROUP_ID_SEQ";
	public static final String TRAINING_METHOD_ID_SEQ = "TRAINING_METHOD_ID_SEQ";
	public static final String FARM_ID_SEQ = "FARM_ID_SEQ";
	public static final String FarmCrop_ID_SEQ = "FarmCrop_ID_SEQ";

	public static final String SEED_TRANSFER_SEQ = "SEED_TRANSFER_SEQ";
	public static final String DISTRIBUTION_STOCK_SEQ = "DISTRIBUTION_STOCK_SEQ";

	public static final String COLD_STORAGE_STOCK_TRANSFER_RECEIPT_NO_SEQ = "COLD_STORAGE_STOCK_TRANSFER_RECEIPT_NO_SEQ";
	public static final String DYNAMIC_MENU_CODE_SEQ = "DYNAMIC_MENU_CODE_SEQ";
	public static final String DYNAMIC_SECTION_CODE_SEQ = "DYNAMIC_SECTION_CODE_SEQ";
	public static final String DYNAMIC_FIELD_CODE_SEQ = "DYNAMIC_FIELD_CODE_SEQ";
	public static final String FARMER_CODE_SEQ = "FARMER_CODE_SEQ";

	/**
	 * Gets the agent internal id sequence.
	 * 
	 * @return the agent internal id sequence
	 */

	/**
	 * Gets the farmer web id seq.
	 * 
	 * @return the farmer web id seq
	 */

	/**
	 * Gets the farmer remote id seq.
	 * 
	 * @return the farmer remote id seq
	 */

	/**
	 * Gets the txn id seq.
	 * 
	 * @return the txn id seq
	 */
	public Long getTxnIdSeq();

	/**
	 * Gets the hamlet code seq.
	 * 
	 * @return the hamlet code seq
	 */
	public Long getHamletCodeSeq();

	/**
	 * Gets the village code seq.
	 * 
	 * @return the village code seq
	 */
	public Long getVillageCodeSeq();

	/**
	 * Gets the cluster code seq.
	 * 
	 * @return the cluster code seq
	 */
	public Long getClusterCodeSeq();

	/**
	 * Gets the taluk code seq.
	 * 
	 * @return the taluk code seq
	 */
	public Long getTalukCodeSeq();

	/**
	 * Gets the district code seq.
	 * 
	 * @return the district code seq
	 */
	public Long getDistrictCodeSeq();

	/**
	 * Gets the state code seq.
	 * 
	 * @return the state code seq
	 */
	public Long getStateCodeSeq();

	/**
	 * Gets the country code seq.
	 * 
	 * @return the country code seq
	 */
	public Long getCountryCodeSeq();

	/**
	 * Gets the range code seq.
	 * 
	 * @return the range code seq
	 */
	public Long getRangeCodeSeq();

	public abstract Long getDeviceIdSeq();

	public abstract Long getCustomerAccountNoSequence();

	public abstract Long getGroupIdSeq();

	public abstract long getAgentSequence();

	public abstract long getAgentCardSequence();

	public abstract long getEnrollmentStationSequence();

	/**
	 * Gets the pOS sequence.
	 * 
	 * @return the pOS sequence
	 */
	public abstract long getPOSSequence();

	/**
	 * Gets the teller pos sequence.
	 * 
	 * @return the teller pos sequence
	 */
	public abstract long getTellerPOSSequence();

	/**
	 * Gets the kiosk sequence.
	 * 
	 * @return the kiosk sequence
	 */
	public abstract long getKioskSequence();

	/**
	 * Gets the user sequence.
	 * 
	 * @return the user sequence
	 */
	public abstract long getUserSequence();

	/**
	 * Gets the txn sequence.
	 * 
	 * @return the txn sequence
	 */
	public long getTxnSequence();

	/**
	 * Gets the client sequence.
	 * 
	 * @return the client sequence
	 */
	public abstract long getClientSequence();

	/**
	 * Gets the provider sequence.
	 * 
	 * @return the provider sequence
	 */
	public long getProviderSequence();

	/**
	 * Gets the affiliate sequence.
	 * 
	 * @return the affiliate sequence
	 */
	public long getAffiliateSequence();

	/**
	 * Gets the service point sequence.
	 * 
	 * @return the service point sequence
	 */
	public long getServicePointSequence();

	/**
	 * Gets the store sequence.
	 * 
	 * @return the store sequence
	 */
	public long getStoreSequence();

	/**
	 * Gets the switch txn sequence.
	 * 
	 * @return the switch txn sequence
	 */
	public long getSwitchTxnSequence();

	/**
	 * Gets the receipt sequence.
	 * 
	 * @return the receipt sequence
	 */
	public long getReceiptSequence();

	/**
	 * Gets the eSE agent account sequence.
	 * 
	 * @return the eSE agent account sequence
	 */
	public abstract long getESEAgentAccountSequence();

	/**
	 * Gets the eSE agent card sequence.
	 * 
	 * @return the eSE agent card sequence
	 */
	public abstract long getESEAgentCardSequence();

	/**
	 * Gets the eSE client account sequence.
	 * 
	 * @return the eSE client account sequence
	 */
	public abstract long getESEClientAccountSequence();

	/**
	 * Gets the eSE client card sequence.
	 * 
	 * @return the eSE client card sequence
	 */
	public abstract long getESEClientCardSequence();

	/**
	 * Creates the batch number.
	 * 
	 * @return the long
	 */
	public abstract long createBatchNumber();

	/**
	 * Gets the client sequencefor device.
	 * 
	 * @param value
	 *            the value
	 * @return the client sequencefor device
	 */
	public abstract String getClientSequenceforDevice(String value);

	/**
	 * Gets the eSE account sequencefor device.
	 * 
	 * @param value
	 *            the value
	 * @return the eSE account sequencefor device
	 */
	public abstract String getESEAccountSequenceforDevice(String value);

	/**
	 * Gets the eSE card sequencefor device.
	 * 
	 * @param value
	 *            the value
	 * @return the eSE card sequencefor device
	 */
	public abstract String getESECardSequenceforDevice(String value);

	/**
	 * Creates the approval code.
	 * 
	 * @return the long
	 */
	public long createApprovalCode();

	/**
	 * Gets the agent internal id sequence.
	 * 
	 * @return the agent internal id sequence
	 */
	public abstract Long getAgentInternalIdSequence();

	/**
	 * Gets the agent card id sequence.
	 * 
	 * @return the agent card id sequence
	 */
	public abstract Long getAgentCardIdSequence();

	/**
	 * Gets the farmer card id sequence.
	 * 
	 * @return the farmer card id sequence
	 */
	public abstract Long getFarmerCardIdSequence();

	/**
	 * Gets the agent account no sequence.
	 * 
	 * @return the agent account no sequence
	 */
	public abstract Long getAgentAccountNoSequence();

	/**
	 * Gets the farmer account no sequence.
	 * 
	 * @return the farmer account no sequence
	 */
	public abstract Long getFarmerAccountNoSequence();

	/**
	 * Gets the truck unique id seq.
	 * 
	 * @return the truck unique id seq
	 */
	public abstract Long getTruckUniqueIdSeq();

	/**
	 * Gets the farmer remote id seq.
	 * 
	 * @return the farmer remote id seq
	 */
	public String getFarmerRemoteIdSeq();

	/**
	 * Gets the farmer web id seq.
	 * 
	 * @return the farmer web id seq
	 */
	public String getFarmerWebIdSeq();

	/**
	 * Gets the distribution seq.
	 * 
	 * @return the distribution seq
	 */
	public abstract Long getDistributionSeq();

	/**
	 * Gets the payment id seq.
	 * 
	 * @return the payment id seq
	 */
	public abstract Long getPaymentIdSeq();

	/**
	 * Gets the procurement chart no seq.
	 * 
	 * @return the procurement chart no seq
	 */
	public abstract Long getProcurementChartNoSeq();

	/**
	 * Gets the mTNT receipt no seq.
	 * 
	 * @return the mTNT receipt no seq
	 */
	public abstract Long getMTNTReceiptNoSeq();

	/**
	 * Gets the mTNR receipt no seq.
	 * 
	 * @return the mTNR receipt no seq
	 */
	public abstract Long getMTNRReceiptNoSeq();

	/**
	 * Gets the distribution mtnt id seq.
	 * 
	 * @return the distribution mtnt id seq
	 */
	public abstract Long getDistributionMTNTIdSeq();

	/**
	 * Gets the distribution mtnr id seq.
	 * 
	 * @return the distribution mtnr id seq
	 */
	public abstract Long getDistributionMTNRIdSeq();

	/**
	 * Gets the distribution to field staff seq.
	 * 
	 * @return the distribution to field staff seq
	 */
	public abstract Long getDistributionToFieldStaffSeq();

	/**
	 * Gets the contract no sequence.
	 * 
	 * @return the contract no sequence
	 */
	public abstract Long getContractNoSequence();

	/**
	 * Gets the price pattern code sequence.
	 * 
	 * @return the price pattern code sequence
	 */
	public abstract Long getPricePatternCodeSequence();

	/**
	 * Gets the procurement receipt no seq.
	 * 
	 * @return the procurement receipt no seq
	 */
	public abstract Long getProcurementReceiptNoSeq();

	/**
	 * Gets the customer sequence.
	 * 
	 * @return the customer sequence
	 */
	public abstract long getCustomerSequence();

	/**
	 * Gets the warehouse stock entry receipt number seq.
	 * 
	 * @return the warehouse stock entry receipt number seq
	 */
	public abstract Long getWarehouseStockEntryReceiptNumberSeq();

	/**
	 * Gets the vendor sequence.
	 * 
	 * @return the vendor sequence
	 */
	public abstract long getVendorSequence();

	/**
	 * Gets the customer account no sequence.
	 * 
	 * @return the customer account no sequence
	 */

	/**
	 * Gets the vendor account no sequence.
	 * 
	 * @return the vendor account no sequence
	 */
	public abstract Long getVendorAccountNoSequence();

	public abstract Long getCountryIdSeq();

	public abstract Long getStateIdSeq();

	public abstract Long getDistrictIdSeq();

	public abstract Long getVillageIdSeq();

	public abstract Long getMandalIdSeq();

	public abstract Long getCatalogueIdSeq();

	public Long getCropHarvestReceiptNumberSeq();

	public abstract Long getProductEnrollIdSeq();

	public abstract Long getSubCategoryIdSeq();

	public abstract Long getProductIdSeq();

	public abstract Long getSeasonIdSeq();

	public abstract Long getTrainingTopicIdSeq();

	public abstract Long getTrainingCriteriaCategoryIdSeq();

	public abstract Long getCriteriaIdSeq();

	public abstract Long getTargetGroupIdSeq();

	public abstract Long getTrainingMethodIdSeq();

	public abstract Long getOrganizationAccountNoSequence();

	public abstract Long getGramPanchayatIdSeq();

	public String getFarmerWebIdSeq(int farmerIdLength, long farmerMaxRange);

	public String getFarmerRemoteIdSeq(int farmerIdLength, long farmerMaxRange);

	public Long getCatalougeTypeSeq();

	public String getGroupHHIdSeq(String code);

	public String getFarmerHHIdSeq(String code);

	public abstract Long getDistrictHHIdSeq();

	public String getMandalHHId(String code);

	public String getGramPanchayathHHId(String code);

	public String getVillageHHIdSeq(String code);

	public String getFarmWebIdSeq();

	public String getFarmerWebCodeIdSeq(String city, String gp);

	public String getFarmWebCodeIdSeq(String city, String gp);

	public abstract long getResearchStationSequence();

	public abstract Long getProductReturnFromFieldStaffSeq();

	public abstract Long getSeedTransferReceiptNoSeq();

	public Long getObservationsSeqId();

	public Long getSurveySectionCodeSeq();

	public String getFarmerWebIdSeqWithBranch(int farmerIdLength, String branch);

	public String getFarmerRemoteIdSeqWithBranch(int farmerIdLength, String branch);

	public Long getProcurementTraceabilityReceiptNoSeq();

	public String getDynmaicTxnType();

	public abstract Long getDistributionStockSeq();

	public Long getGroupHHIdSeqFromBranchMasterWebSeq(String branch);

	public abstract Long getColdStorageStockTransferReceiptNoSeq();

	public Long getDynamicSectionCodeSeq();

	public Long getDynamicFieldCodeSeq();

	public Long getDynamicMenuCodeSeq();

	public Long getFarmerCodeSeq();

	Long getLoanDistributionReceiptNoSeq();

	public Long getLoanAccountNoSeq();

	Long getLoanRepaymentReceiptNoSeq();

	String getFarmerIdSeq();
	
	String getFarmIdSeq();
	
	String getFarmCropIdSeq();

	String getFarmerTaskIdSeq();

	public Long getShopDealerCardIdSequence();

	public Long getPermitIdSeq();

	Long getDealerIdSeq(String seqName);

	public Long getExproterIdSeq();

	public Long getTransSeedCertIdSeq();

	public Long getFarmerIdSequ(String string);

	Long getHarvestIdSeq();

	public Long getWarehouseIdSeq();

	public Long getProcurementVarietyIdSeq();

	public Long getProcurementGradeIdSeq();

}
