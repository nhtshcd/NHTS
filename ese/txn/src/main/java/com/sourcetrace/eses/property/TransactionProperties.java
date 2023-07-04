/*
 * TransactionProperties.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.property;

/**
 * The Class TransactionProperties.
 */
public class TransactionProperties {


    /** HEAD DATA **/
    public static final String HEAD = "head";
    public static final String SERVICE_POINT_ID = "servPointId";
    public static final String SERIAL_NO = "serialNo";
    public static final String AGENT_ID = "agentId";
    public static final String AGENT_TOKEN = "agentToken";
    public static final String TXN_TYPE = "txnType";
    public static final String TXN_TIME = "txnTime";
    public static final String OPERATION_TYPE = "operType";
    public static final String MODE = "mode";
    public static final String MSG_NO = "msgNo";
    public static final String RESENT_COUNT = "resentCount";
    public static final String BATCH_NO = "batchNum";
    public static final String BRANCH_ID = "branchId";
    public static final String PARENT_BRANCH_ID = "parentId";

    public static final String TXN_ID = "txnId";
    public static final String TXN_LOG_ID = "txnLogId";

    public static final String ONLINE_MODE = "01";
    public static final String OFFLINE_MODE = "02";
    public static final String REGULAR_TXN = "01";
    public static final String VOID_TXN = "02";
    
    public static final String SERVER_ERROR="Server Error";

    /** PARAM SYNC DATA **/
    public static final String SERVICE_POINT_NAME = "servPointName";
    public static final String MSG_NO_SEQ = "msgNoSeq";
    public static final String RECEIPT_NO_SEQ = "receiptNoSeq";
    public static final String DEVICE_ID = "deviceId";
    public static final String SYNC_TIME_STAMP = "syncTS";
    public static final String DISPLAY_TS_FORMAT = "displayTSFormat";
    public static final String CLIENT_ID_SEQ = "idSeq";
    public static final String CLIENT_CARD_ID_SEQ = "cardIdSeq";
    public static final String CLIENT_ACCT_ID_SEQ = "acctIdSeq";
    public static final String FARMER_CARD_ID_SEQ = "fCardSeq";
    public static final String SHOP_DEALER_CARD_ID_SEQ = "sdCardSeq";
    public static final String RECEIPT_NUMBER_SEQ = "rptNoSeq";
    public static final String AGENT_INTERNAL_ID = "internalId";
    public static final String ORDER_NUMBER_SEQ = "orderNoSeq";
    public static final String DELIVERY_NUMBER_SEQ = "deliveryNoSeq";
    public static final String FARMER_ACCOUNT_NO_SEQ = "fAcctSeq";
    public static final String AGENT_ACCOUNT_NO = "agAcctNo";
    public static final String SHOP_DEALER_ID_SEQ = "spIdSeq";
    public static final String AGENT_TYPE = "agentType";
    public static final String TARE_WEIGHT = "tare";
    public static final String CURRENT_SEASON_CODE = "currentSeasonCode";
    public static final String DISPLAY_TXN_DATE_FORMAT = "dispDtFormat";
    public static final String AREA_TYPE = "areaType";
    public static final String WAREHOUSE_ID = "warehouseId";
    public static final String PACKHOUSE_ID = "packHouseId";
    public static final String PACKHOUSE_NAME = "packHouseName";
    public static final String PACKHOUSE_CODE = "packHouseCode";
    public static final String EXPORTER_LICENSE = "expLic";
    public static final String EXPORTER_NAME = "expName";
    public static final String EXPORTER_CODE = "expCode";
    public static final String EXPORTER_EXP = "expDate";
    public static final String EXPORTER_STATUS = "expStatus";
    /**
     * CRED DOWNLOAD
     **/
    public static final String TXN_MODE = "txnMode";
    public static final String FP_OVERRIDE_PER_CUSTOMER = "fpOvrdCustomer";
    public static final String FP_OVERRIDE_PER_OPERATOR = "fpOvrdOperator";
    public static final String CASH_IN_HAND = "cashInHand";
    public static final String CASH_IN_HAND_PERMITTED = "cashInHandPmt";
    public static final String ONLINE_DEPOSIT_LIMIT_MIN = "onlineDepLmtMin";
    public static final String ONLINE_DEPOSIT_LIMIT_MAX = "onlineDepLmtMax";
    public static final String ONLINE_WITHDRAW_LIMIT_MIN = "onlineWthLmtMin";
    public static final String ONLINE_WITHDRAW_LIMIT_MAX = "onlineWthLmtMax";
    public static final String OFFLINE_DEPOSIT_LIMIT_MIN = "offlineDepLmtMin";
    public static final String OFFLINE_DEPOSIT_LIMIT_MAX = "offlineDepLmtMax";
    public static final String OFFLINE_WITHDRAW_LIMIT_MIN = "offlineWthLmtMin";
    public static final String OFFLINE_WITHDRAW_LIMIT_MAX = "offlineWthLmtMax";
    public static final String HOUR_BEFORE_SYNC = "hourB4Sync";
    public static final String TXN_BETWEEN_SYNC = "txnBwnSync";
    public static final String TURNOVER_BETWEEN_SYNC = "tnorBwnSync";
    public static final String ONLINE_DEPOSIT_LIMIT_PER_SYNC = "onlineDepLmtSync";
    public static final String ONLINE_WITHDRAW_LIMIT_PER_SYNC = "onlineWthLmtSync";
    public static final String OFFLINE_DEPOSIT_LIMIT_PER_SYNC = "offlineDepLmtSync";
    public static final String OFFLINE_WITHDRAW_LIMIT_PER_SYNC = "offlineWthLmtSync";
    public static final String CUSTOMER_TXN_PER_DAY_LIMIT = "customerTxnPerDay";

    /** CLIENT DATA DOWNLOAD **/
    public static final String CLIENT_REV_NO = "clientRevNo";
    public static final String CLIENT_CARD_ID = "cardId";
    public static final String CLIENT_ACCT_NO = "acctNo";
    public static final String BALANCE = "bal";
    public static final String STATUS = "status";

    public static final String CLIENT_CARD_LIST = "clientCardList";
    public static final String CLIENT_ACCT_LIST = "clientAcctList";
    
    /** MFI TXNs **/
    public static final String CLIENT_CARDID = "clientCardId";
    public static final String CLIENT_ACCOUNT_NO = "clientAcctNo";
    public static final String CLIENT_ACCOUNT_TYPE = "clientAcctType";
    public static final String TXN_AMOUNT = "txnAmt";
    public static final String RECEIPT_ID = "receiptId";
    public static final String FINAL_BALANCE = "balance";
    public static final String APPROVAL_CODE = "aprCode";
    public static final String TXN_CODE = "txnCode";
    public static final String CURRENCY = "currency";    
    public static final String TXN_NARATION = "txnNrtn";
    public static final String TXN_LIST = "txnList";
    public static final String TYPE = "type";
    
    /** LOCATION DOWNLOAD **/
    public static final String COUNTRY_CODE="countryCode";
    public static final String COUNTRY_NAME="countryName";
    public static final String COUNTRY_LIST ="countryList";
    public static final String STATE_CODE="stateCode";
    public static final String STATE_NAME="stateName";  
    public static final String STATE_LIST ="stateList";
    public static final String DISTRICT_CODE = "districtCode";
    public static final String DISTRICT_NAME = "districtName";
    public static final String DISTRICT_LIST ="districtList";
    public static final String CITY_CODE = "cityCode";
    public static final String CITY_NAME = "cityName";
    public static final String CITY_LIST ="cityList";
    public static final String VILLAGE_CODE = "villageCode";
    public static final String VILLAGE_NAME = "villageName";
    public static final String VILLAGE_LIST ="villageList";
    public static final String WAREHOUSE_CODE = "wCode";
    public static final String WAREHOUSE_NAME = "wName";
    public static final String WAREHOUSE_CITY_REF ="wCityRef";
    public static final String WAREHOUSE_LIST ="whList";
    public static final String AREA_CODE = "aCode";
    public static final String AREA_NAME = "aName";
    public static final String GRAM_PANCHAYAT_LIST ="panchayatList";
    public static final String PANCHAYAT_CODE ="panCode";
    public static final String PANCHAYAT_NAME ="panName";
    public static final String PANCHAYAT_REF_CODE ="panRefCode";
    
    /** FARMER TRANSACTION HISTORY **/
    public static final String TRANSACTION_HISTORY ="txnHistory";
    public static final String COMPANY_NAMES = "cmpNam";
    
    
    /** DOWNLOAD TXN REVISION NO KEYS **/
    public static final String FARMER_DOWNLOAD_REVISION_NO = "farmerRevNo";
    public static final String PRICE_PATTERN_DOWNLOAD_REVISION_NO = "ppRevNo";
    public static final String LOCATION_DOWNLOAD_REVISION_NO = "lRevNo";
    public static final String FARMER_OUTSTANDING_BALANCE_DOWNLOAD_REVISION_NO = "fobRevNo";
    public static final String PRODUCTS_DOWNLOAD_REVISION_NO = "prodRevNo";
    public static final String FARMCROPS_DOWNLOAD_REVISION_NO = "fcmRevNo";
    public static final String PROCUREMENT_PRODUCT_DOWNLOAD_REVISION_NO = "procProdRevNo";
    public static final String VILLAGE_WAREHOUSE_STOCK_DOWNLOAD_REVISION_NO = "vwsRevNo";
    public static final String GRADE_DOWNLOAD_REVISION_NO = "gRevNo";
    public static final String WAREHOUSE_PRODUCT_STOCK_DOWNLOAD_REVISION_NO = "wsRevNo";
    public static final String COOPERATIVE_DOWNLOAD_REVISION_NO = "coRevNo";
    public static final String SEASON_DOWNLOAD_REVISION_NO = "seasonRevNo";

    public static final String TRAINING_CRITERIA_CATEGORY_DOWNLOAD_REVISION_NO = "tccRevNo";
    public static final String TRAINING_CRITERIA_DOWNLOAD_REVISION_NO = "tcRevNo";
    public static final String PLANNER_DOWNLOAD_REVISION_NO = "plannerRevNo";

    public static final String REMOTE_NEW_PASSWORD = "nPwd";
    public static final String REMOTE_PASSWORD_CHANGE_DATE = "nPwdCDt";
    public static final String CATALOGUE_DOWNLOAD_REVISION_NO = "catRevNo";
    public static final String CATLOGUE_CODE = "catId";
    public static final String CATLOGUE_NAME = "catName";

    public static final String CATLOGUE_TYPE = "catType";
    public static final String CATALOGUES = "catList";
    public static final String SEQ_NO = "seqNo";
    public static final String PARENT_ID = "pCatId";
    
    public static final String BUYER_REV_NO ="byrRevNo";
    
    public static final String BUYER = "byrList";
    public static final String CUSTOMER_NAME = "byrName";
	public static final String BUYER_ID = "byrId";
    public static final String BUYER_DOWNLOAD_REVISION_NO = "byrRevNo";
    public static final String BUYER_COUNTRY = "buyrCountry";
    public static final String BUYER_COUNTRY_CODE = "buyrCountryCode";
    public static final String BUYER_COUNTY= "buyrCounty";
    public static final String BUYER_SUB_COUNTY= "buyrSubCounty";
    public static final String BUYER_WARD= "buyrWard";
    
    public static final String IS_GENERIC="isGeneric";
    public static final String IS_BUYER="isBuyer";
    
    public static final String SUPPLIER_DOWNLOAD_REVISION_NO = "supRevNo";
    public static final String SUPPLIER_TYPE_lIST = "supTypLst";
    
    public static final String SUPPLIER_TYPE_ID = "supTypId";
    public static final String SUPPLIER_TYPE_NAME = "supTypNme";
    public static final String SUPPLIER_lIST = "supLst";
    
    public static final String SUPPLIER_ID = "supId";
    public static final String SUPPLIER_NAME = "supNme";

    public static final String RESEARCH_STATION_REV_NO="resStatRevNo";
    public static final String RESEARCH_STATION_ID = "resStatId";
    public static final String RESEARCH_STATION_NAME = "resStatNme";
    public static final String RESEARCH_STATION_POINT_PERSON = "pointPers";
    public static final String COW_LIST = "cowLst";
    public static final String CALF_LIST = "calfLst";
    public static final String RESEARCH_STATION_LIST = "resStatLst";
    public static final java.lang.Object IS_BATCH_AVAIL = "isBatchAvail";
    
    public static final String GROUP_ID="groupId";
    public static final String VCODE="vCode";
    public static final String FARMER_CNT="farmerCnt";
    public static final String REMARKS="rmrk";
    public static final String DYNAMIC_FIELDS = "fieldList";
    public static final String DYNAMIC_REV_NO = "dynaFieldRevNo";
    
    public static final String SECTIONS = "sections";
	public static final String  SECTION_NAME = "secName";
	
	 /** Questions Download **/
	 public static final String SURVEY_MASTER_REV_NO = "sRevNo";
	 
		public static final String SECTIONSDWN_CODE = "secId";
		public static final String SECTIONSDWN_NAME = "secName";
		public static final String DATA_LEVEL_CODE = "datCde";
		public static final String DATA_LEVEL_NAME = "datNme";
		public static final String SECTION_LIST = "sections";
		public static final String SECTION_REVISION_NO = "secRvNo";
		public static final String QUESTION_REVISION_NO = "quesRvNo";
		
		
	 public static final String QUESTIONS_CODE = "queCode";
		public static final String QUESTIONS_NAME = "queName";
		public static final String QUESTION_TYPE = "qusTyp";
		public static final String SECTIONS_CODE = "secId";
		public static final String QUESTION_LANGUAGE_CODE = "lngCde";
		// public static final String QUESTION_SERIALNO="slNo";
		public static final String QUESTION_COMPONENT_TYPE = "compoType";
		public static final String QUESTION_FORMULA = "frmQus";
		public static final String QUESTION_DATAFORMAT = "dataFormat";
			public static final String QUESTION_MANDATORY = "isMandatory";
			public static final String QUESTION_REVISIONNO = "questionRevNo";
		public static final String QUESTION_LIST = "questionListS";
			public static final String DEPENDANCY_KEY_QUESTION = "dependentField";
		public static final String IS_DEPENDANCY = "isDependency";
		public static final String QUESTION_REVISION = "dcSurRevNo";
		public static final String QUESTION_MAXIMUM_LENGTH = "maxLength";
		public static final String QUESTION_VALIDATION_TYPE = "validType";
		public static final String QUESTION_CAT_TYPE = "catalogType";
		public static final String PARENT_DEP = "parentDepend";
		public static final String PARENT_QN = "parentQuestion";
		public static final String LANGUAGE_CODE = "langCode";
		public static final String QUESTION_SECTION = "sectionId";
		public static final String LANGUAGE_NAME = "langValue";
		public static final String FIELD_ORDER = "fieldOrder";
		public static final String LIST_METHOD_NAME = "listMethodName";
		public static final String LANGUAGE_INFOTEXT = "infoTxt";
		public static final String FORMULA_DEP_KEY = "formulaDependencyKey";
		public static final String FORMULA = "formula";
	    public static final String QUESTION_MINIMUM_RANGE = "minLength";
	    public static final String LANG_LIST = "lang";
	    public static final String LANG_LIST_CATEGORY = "langCat";
	    
	    public static final String SURVEYS_LIST = "surveyList";
	    public static final String SURVEY_CODE = "surveyCode";
	    
	    public static final String SURVEYS_NAME= "surveyName";
	    public static final String SURVEY_TYPE = "surveyType";
	    public static final String DATA_LEVEL = "dataLevel";
	
	    public static final String DYNAMIC_FIELD="dyFields";
	    public static final String DYNAMIC_FIELD_LIST="dyFieldsLst";
	    public static final String IMAGE_TIME="imageTime";
	    public static final String DYNAMIC_IMAGE_LIST="dynImageLst";
	    public static final String FIELD_ID="fieldId";
	    public static final String F_PHOTO="fPhoto";
	    
	    public static final String SENDER_QWH="senWh";
	    public static final String RECEIV_QWH="recWh";
	    public static final String TRANSFER_RECEIPT_NO="trRecNo";
	    public static final String PRODUCT_LIST="products";
	    public static final String PRODUCT_CODE="productCode";
	    public static final String BAGS="bags";
	    public static final String WEIGHT="grossWt";
	    public static final String STOCK_LIST="stckList";
	    public static final String STOCK_REV="stckRev";
	   
	    public static final String PMT_CAT_TIME1="prpcTime1";
	    public static final String PMT_CAT_TIME2="prpcTime2";
	    
	    public static final String TRACE_STKDOWNLOAD_TYPE="ty";
	    public static final String ICS_CODE="icsCd";
	    public static final String HEAP_STOCK="heapStock";
	    public static final String HEAP_REV="heapRev";
		public static final String GINNER_CODE = "ginCode";
		public static final String PRD_CODE="prdCode";
		public static final String ICS="ics";
		public static final String HEAP_CODE="heapCode";
		public static final String TOTAL_STOCK="totStk";
		public static final String HEAP_LIST="heapList";
		
		public static final String PROCESS_QTY="procQty";
		public static final String LINT_QTY="lintQty";
		public static final String SEED_QTY="seedQty";
		public static final String SCRAP_QTY="scrapQty";
		public static final String PROCESS_DATE="procDate";
		
		public static final String LINT_STOCK="lintStk";
		public static final String BALE_DATE="baleDate";
		public static final String LOT_NO="lotNo";
		public static final String PR_NO="prNo";
		public static final String BALE_WEIGHT="baleWt";
		public static final String TRANS_DATE="trnsDate";
		public static final String TAX_INVOICE_NO="taxInvoice";
		public static final String TRUCK_NO="truckNo"; 
		public static final String SPINNING_CODE="spinnerCode";
		public static final String BALE_COUNT="noOfBale";
		public static final String NET_WEIGHT="netWt";
		public static final String RATE="rate";
		public static final String NET_AMT="netAmt";
		public static final String GINNING_DATE="ginDate";
		public static final String GINNING_STOCK="ginStock";
		public static final String GINNING_LIST="ginList";
		public static final String GIN_DATE="ginDate";
		
		
		public static final String FTP_URL="ftpUrl";
		public static final String FTP_PASSWORD="ftpPw";
		public static final String FTP_USERNAME="ftpUs";
		public static final String FTP_VIDEO_PATH="ftpPath";
		public static final String DISTRIBUTION_IMAGE_AVILABLE="distImgAvil";
		public static final String DIGITAL_SIGNATURE="digitalSign";
		
		
		public static final String CROP_CALENDAR="cropCalandar";
		public static final String WAREHOUSEDOWNLOAD_SEASON="warehouseSeason";
		  public static final String FOLLOW_UPS="followUps";
		public static final String COMPANY_ID = "companyId";
		public static final String GENERAL_DATE_FORMAT = "genDateFormat";
		public static final String IS_FIRST_LOGIN = "fLogin";
	
}
