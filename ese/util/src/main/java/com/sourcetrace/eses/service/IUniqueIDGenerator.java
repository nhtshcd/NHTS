/*
 * IUniqueIDGenerator.java
 * Copyright (c) 2015-2016, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.service;

// TODO: Auto-generated Javadoc
public interface IUniqueIDGenerator {

	public static final String AGENT = "10";
	public static final String CLIENT = "11";
	public static final String USER = "12";
	public static final String PROVIDER = "13";
	public static final String AFFILIATE = "14";
	public static final String SWITCH_TXN = "15";
	public static final String ENROLLMENT_STATION = "20";
	public static final String POS = "21";
	public static final String KIOSK = "22";
	public static final String TELLER_POS = "23";
	public static final String SERVICE_POINT = "24";
	public static final String AGENT_CARD = "30";
	public static final String CLIENT_CARD = "40";
	public static final String AGENT_DATA_CARD = "50";
	public static final String ENT_LOAN = "51";
	public static final String PERS_LOAN = "52";
	public static final String PINOLERA = "53";
	public static final String CBS_IMPORT = "9999";
	public static final String MDB_USER = "62p";
	public static final String MDB_USERID = "72";
	public static final String STORE = "80";
	public static final String ESE_AGENT_ID = "10";
	public static final String ESE_CLIENT_ID = "11";
	public static final String ESE_AGENT_CARD = "20";
	public static final String ESE_AGENT_ACCOUNT = "30";
	public static final String ESE_ORGANIZATION_ACCOUNT = "60";
	public static final String ESE_CLIENT_CARD = "21";
	public static final String ESE_WEB_REQUEST = "01";
	public static final String ESE_DEVICE_REQUEST = "02";
	public static final String ESE_AGENT_EMPTY_ID = "000000";
	public static final String ESE_EMPTY_ID = "000000";
	// properties for Card related sequence generation
	public static final String ESE_AGENT_CARD_ID = "10";
	public static final String ESE_SHOP_DALER_CARD_ID = "11";
	public static final String WEB_REQUEST = "00";
	public static final String ESE_FARMER_CARD_ID = "12";
	public static final String ESE_MOBILE_REQUEST = "01";
	public static final String ESE_POS_REQUEST = "02";
	public static final String ESE_AGENT_INTERNAL_ID = "0000";
	public static final String ESE_ORGANIZATION_INTERNAL_ID = "0000000";
	public static final String ESE_CLIENT_ACCOUNT = "31";

	public static final String ESE_COMPANY_ACCOUNT_NO = "11";
	public static final String ESE_AGENT_ACCOUNT_NO = "20";
	public static final String ESE_FARMER_ACCOUNT_NO = "22";
	public static final String ESE_ORGANIZATION_ACCOUNT_NO = "32";
	public static final String ESE_VENDOR_ACCOUNT_NO = "31";
	public static final String ESE_CUSTOMER_ACCOUNT = "41";
	public static final String ESE_RESEARCHSTATION_ACCOUNT_NO = "31";

	public static final String COUNTRY_PREFIX_CHAR = "C";
	public static final String STATE_PREFIX_CHAR = "S";
	public static final String DISTRICT_PREFIX_CHAR = "D";
	public static final String GRAMPANCHAYAT_PREFIX_CHAR = "G";
	public static final String MANDAL_PREFIX_CHAR = "M";
	public static final String VILLAGE_PREFIX_CHAR = "V";

	public static final String DEVICE_PREFIX_CHAR = "DV";
	public static final String GROUP_PREFIX_CHAR = "G";
	public static final String WAREHOUSE_PREFIX_CHAR = "W";
	public static final String CATALOGUE_PREFIX_CHAR = "CG";
	public static final String PRODUCT_ENROLL_CHAR = "CR";
	public static final String VARIETY_CHAR = "V";
	public static final String GRADE_CHAR = "G";
	public static final String SUB_CATEGORY_CHAR = "FP";
	public static final String PRODUCT_CHAR = "P";
	public static final String SEASON_CHAR = "HS";
	public static final String TRAINING_TOPIC_CHAR = "TT";
	public static final String TRAINING_CRITERIA_CATEGORY_CHAR = "TCC";
	public static final String CRITERIA_CHAR = "C";
	public static final String TARGET_GROUP_CHAR = "TG";
	public static final String TRAINING_METHOD_CHAR = "TM";
	public static final String OBSERVATION_CHAR = "OBS";
	public static final String DYNAMIC_MENU_CODE_PREFIX_CHAR = "M";
	public static final String DYNAMIC_SECTION_CODE_PREFIX_CHAR = "S";
	public static final String DYNAMIC_FIELD_CODE_PREFIX_CHAR = "C";
	public static final String PREMITAPPLN_PREFIX_CHAR = "AIT/";
	public static final String GROWER_PREFIX_CHAR = "GR";
	public static final String BREEDER_PREFIX_CHAR = "BS";
	public static final String DEALERREG_PREFIX_CHAR = "AIT/";
	public static final String seed_module_prefix = "NSCS/";
	public static final String HARVEST_PREFIX_CHAR = "HI";

	/**
	 * Creates the agent id.
	 * 
	 * @return the string
	 */
	public String createAgentId();

	/**
	 * Creates the agent card id.
	 * 
	 * @return the string
	 */
	public String createAgentCardId();

	/**
	 * Creates the agent data card id.
	 * 
	 * @return the string
	 */
	public String createAgentDataCardId();

	/**
	 * Creates the enrollment station id.
	 * 
	 * @return the string
	 */
	public String createEnrollmentStationId();

	/**
	 * Creates the pos id.
	 * 
	 * @return the string
	 */
	public String createPOSId();

	/**
	 * Creates the teller pos id.
	 * 
	 * @return the string
	 */
	public String createTellerPOSId();

	/**
	 * Creates the user id.
	 * 
	 * @return the string
	 */
	public String createUserId();

	/**
	 * Creates the client id.
	 * 
	 * @param agentId
	 *            the agent id
	 * @return the string
	 */
	public String createClientId(String agentId);

	/**
	 * Creates the ese agent account id.
	 * 
	 * @param agentId
	 *            the agent id
	 * @return the string
	 */
	public String createESEAgentAccountId(String agentId);

	/**
	 * Creates the ese client account id.
	 * 
	 * @param agentId
	 *            the agent id
	 * @return the string
	 */
	public String createESEClientAccountId(String agentId);

	/**
	 * Creates the ese agent card id.
	 * 
	 * @param agentId
	 *            the agent id
	 * @return the string
	 */
	public String createESEAgentCardId(String agentId);

	/**
	 * Creates the ese client card id.
	 * 
	 * @param agentId
	 *            the agent id
	 * @return the string
	 */
	public String createESEClientCardId(String agentId);

	/**
	 * Creates the kiosk id.
	 * 
	 * @return the string
	 */
	public String createKioskId();

	/**
	 * Creates the mdb user id.
	 * 
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @return the string
	 */
	public String createMDBUserId(String firstName, String lastName);

	/**
	 * Creates the mdb passwd.
	 * 
	 * @return the string
	 */
	public String createMDBPasswd();

	/**
	 * Creates the provider id.
	 * 
	 * @return the string
	 */
	public String createProviderId();

	/**
	 * Creates the affiliate id.
	 * 
	 * @return the string
	 */
	public String createAffiliateId();

	/**
	 * Creates the service point id.
	 * 
	 * @return the string
	 */
	public String createServicePointId();

	/**
	 * Creates the store id.
	 * 
	 * @return the string
	 */
	public String createStoreId();

	/**
	 * Creates the switch txn id.
	 * 
	 * @return the string
	 */
	public String createSwitchTxnId();

	/**
	 * Creates the batch number.
	 * 
	 * @return the string
	 */
	public String createBatchNumber();

	/**
	 * Creates the approval code.
	 * 
	 * @return the string
	 */
	public String createApprovalCode();

	/**
	 * Creates the txn sequence.
	 * 
	 * @return the string
	 */
	public String createTxnSequence();

	/**
	 * Creates the agent internal id sequence.
	 * 
	 * @return the string
	 */
	public String createAgentInternalIdSequence();

	/**
	 * Creates the agent card id sequence.
	 * 
	 * @return the string
	 */
	public String createAgentCardIdSequence();

	/**
	 * Creates the shop dealer card id sequence.
	 * 
	 * @param deviceType
	 *            the device type
	 * @param agentInternalId
	 *            the agent internal id
	 * @return the string
	 */
	public String createShopDealerCardIdSequence(String deviceType, String agentInternalId);

	/**
	 * Creates the farmer card id sequence.
	 * 
	 * @param deviceType
	 *            the device type
	 * @param agentInternalId
	 *            the agent internal id
	 * @return the string
	 */
	public String createFarmerCardIdSequence(String deviceType, String agentInternalId);

	/**
	 * Creates the agent account no sequence.
	 * 
	 * @return the string
	 */
	public String createAgentAccountNoSequence();

	/**
	 * Creates the farmer account no sequence.
	 * 
	 * @param deviceType
	 *            the device type
	 * @param agentInternalId
	 *            the agent internal id
	 * @return the string
	 */
	public String createFarmerAccountNoSequence(String deviceType, String agentInternalId);

	/**
	 * Creates the truck sequence.
	 * 
	 * @return the string
	 */
	public String createTruckSequence();

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
	 * Gets the shop dealer remote id seq.
	 * 
	 * @return the shop dealer remote id seq
	 */

	/**
	 * Gets the shop dealer web id seq.
	 * 
	 * @return the shop dealer web id seq
	 */

	/**
	 * Gets the distribution seq.
	 * 
	 * @return the distribution seq
	 */
	public String getDistributionSeq();

	/**
	 * Gets the payment seq id.
	 * 
	 * @return the payment seq id
	 */
	public String getPaymentSeqId();

	/**
	 * Gets the procurement chart no seq.
	 * 
	 * @return the procurement chart no seq
	 */
	public String getProcurementChartNoSeq();

	/**
	 * Gets the mTNT receipt no seq.
	 * 
	 * @return the mTNT receipt no seq
	 */
	public String getMTNTReceiptNoSeq();

	/**
	 * Gets the mTNR receipt no seq.
	 * 
	 * @return the mTNR receipt no seq
	 */
	public String getMTNRReceiptNoSeq();

	/**
	 * Gets the distribution mtnt id seq.
	 * 
	 * @return the distribution mtnt id seq
	 */
	public String getDistributionMTNTIdSeq();

	/**
	 * Gets the distribution mtnr id seq.
	 * 
	 * @return the distribution mtnr id seq
	 */
	public String getDistributionMTNRIdSeq();

	/**
	 * Gets the distribution to field staff seq.
	 * 
	 * @return the distribution to field staff seq
	 */
	public String getDistributionToFieldStaffSeq();

	/**
	 * Creates the contract no sequence.
	 * 
	 * @return the string
	 */
	public String createContractNoSequence();

	/**
	 * Creates the price pattern code sequence.
	 * 
	 * @return the string
	 */
	public String createPricePatternCodeSequence();

	/**
	 * Gets the procurement receipt no seq.
	 * 
	 * @return the procurement receipt no seq
	 */
	public String getProcurementReceiptNoSeq();

	/**
	 * Creates the customer id.
	 * 
	 * @return the string
	 */
	public String createCustomerId();

	/**
	 * Gets the warehouse stock entry receipt number seq.
	 * 
	 * @return the warehouse stock entry receipt number seq
	 */
	public String getWarehouseStockEntryReceiptNumberSeq();

	/**
	 * Create customer account no sequence.
	 * 
	 * @param customerId
	 * @return the string
	 */
	public String createCustomerAccountNoSequence(String customerId);

	/**
	 * Create vendor account no sequence.
	 * 
	 * @param vendorId
	 * @return the string
	 */
	public String createVendorAccountNoSequence(String vendorId);

	public String getCountryIdSeq();

	/**
	 * Create vendor Id.
	 * 
	 * @return the string
	 */

	public String createVendorId();

	public String getStateIdSeq();

	public String getDistrictIdSeq();

	public String getMandalIdSeq();

	public String getVillageIdSeq();

	public String getDeviceIdSeq();

	public String getGroupIdSeq();

	public String getTxnIdSeq();

	public String getCatalogueIdSeq();

	public String getCropHarvestReceiptNumberSeq();

	public String getProductEnrollIdSeq();

	public String getSubCategoryIdSeq();

	public String getProductIdSeq();

	public String getSeasonIdSeq();

	public String getTrainingTopicIdSeq();

	public String getTrainingCriteriaCategoryIdSeq();

	public String getCriteriaIdSeq();

	public String getTargetGroupIdSeq();

	public String getTrainingMethodIdSeq();

	public String createOrganizationAccountNoSequence();

	public String getGramPanchayatIdSeq();

	public String getFarmerWebIdSeq(int farmerIdLength, long farmerMaxRange);

	public String getFarmerRemoteIdSeq(int farmerIdLength, long farmerMaxRange);

	public String getCatalougeTypeSeq();

	/*
	 * public String createOrganizationAccountNoSequence(String deviceType,
	 * String agentInternalId); public String
	 * createOrganizationAccountNoSequence();
	 */
	public String getGroupHHIdSeq(String code);

	public String getFarmerHHIdSeq(String code);

	public String getLocalityHHIdSeq();

	public String getMunicipalityHHIdSeq(String code);

	public String getGramPanchayatHHIdSeq(String code);

	public String getVillageHHIdSeq(String code);

	public String getFarmWebIdSeq();

	public String getFarmerWebCodeIdSeq(String city, String gp);

	public String getFarmWebCodeIdSeq(String city, String gp);

	public String createResearchStationId();

	public String createResearchStationAccountNoSequence(String researchStationId);

	public String getProductReturnFromFieldStaffSeq();

	public String getSeedTransferReceiptNoSeq();

	public String getObservationsSeqId();

	public String getSurveySectionCodeSeq();

	String getFarmerWebIdSeqWithBranch(int farmerIdLength, String branch);

	String getFarmerRemoteIdSeqWithBranch(int farmerIdLength, String branch);

	public String getProcurementTraceabilityReceiptNoSeq();

	public String getDynmaicTxnType();

	public String getDistributionStockSeq();

	public String getBranchMasterWebSeq(String branch);

	public String getColdStorageStockTransferReceiptNoSeq();

	String getDynamicSectionCodeSeq();

	String getDynamicFieldCodeSeq();

	String getDynamicMenuCodeSeq();

	public String getFarmerCodeSeq();

	public String getLoanDistributionReceiptNoSeq();

	String getLoanAccountNoSeq();

	public String getLoanRepaymentReceiptNoSeq();

	public String getFarmerIdSeq();
	
	public String getFarmIdSeq();
	
	public String getFarmCropIdSeq();

	public String getFarmerTaskIdSeq();

	public String getPermitIdSeq();

	public String getDealerRegIdSeq(String prefix);

	public String getHarvestIdSeq();

	public String getFarmerIdSequ();

	public String getWarehouseIdSeq();

	public String getProcurementVarietyIdSeq();

	public String getProcurementGradeIdSeq();
}
