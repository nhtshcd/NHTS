/*
 * SwitchErrorCodes.java
 * Copyright (c) 2008-2009, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.txn.exception;

/**
 * SwitchErrorCodes defines the error codes for switch.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface SwitchErrorCodes {

    public static final String INVALID_LICENCE = "L1001";
    public static final String EXPIRED_LICENCE = "L1002";
    public static final String UNAUTHORIZED_CLIENT = "L1003";
    public static final String NO_LICENCE = "L1004";
    public static final String DATABASE_CONNECTION_ERROR = "L1005";

    // ERROR
    public static final String ERROR = "90000";
    public static final String DATA_CONVERSION_ERROR = "90001";
    public static final String EMPTY_LICENSE = "90002";
    public static final String LICENSE_EXPIRED = "90003";
    public static final String LICENSE_INVALID = "90004";
    public static final String DATE_CONVERSION_ERROR = "90005";

    public static final String INVALID_TOKEN = "1001";
    public static final String INVALID_USER = "1002";
    public static final String INVALID_USER_CREDENTIAL = "1003";
    public static final String INVALID_STORE = "1004";
    public static final String UNASSIGNED_STORE = "1005";
    public static final String UNMATCHED_STORE = "1006";
    public static final String SERVICE_POINT_DISABLED = "1008";
    public static final String INVALID_AFFILIATE = "1009";
    public static final String UNASSIGNED_AFFILIATE = "1010";
    public static final String UNMATCHED_AFFILIATE = "1011";
    public static final String AFFILIATE_DISABLED = "1012";
    public static final String INVALID_CATEGORY = "1013";
    public static final String INVALID_PROVIDER = "1014";
    public static final String INVALID_SWITCH_TXN = "1015";
    public static final String MISSING_TXN_PROP = "1016";
    public static final String RETRY_TXN = "1018";
    public static final String INVALID_CR_BALANCE = "1019";
    public static final String NO_CR_BALANCE = "1020";
    public static final String TXN_VER_MISMATCH = "1021";
    public static final String UNAUTHZ_WINCLIENT_USER = "1022";
    public static final String UNAUTHZ_WINCLIENT = "1023";
    public static final String DAILY_CLOSING_PROGRESS = "1024";
    public static final String INVALID_RCPT_NUM = "1025";
    public static final String INVALID_TXN = "1026";
    public static final String OLD_AND_NEW_PASSWORD_MISMATCH = "1027";
    public static final String INVALID_PREVIOUS_TXN_NUM = "1028";
    public static final String INVALID_SERVICE_POINT_PREF = "1029";
    public static final String INVALID_CALCULATED_COMMISSION = "1030";
    public static final String ALREADY_TXN_REVERSED = "1031";
    public static final String NOT_AVAILABLE_DEVICE = "1093";
    public static final String INVALID_DUPLICATION = "1097";
    public static final String INVALID_TRUCKTRACKING_LONGTITUDE = "E1061";
    public static final String INVALID_TRUCKTRACKING_LATITUDE = "E1062";
    public static final String INVALID_TRUCKTRACKING_TRUCKDATE = "E1063";
    public static final String INVALID_TRUCKTRACKING = "E1064";
    public static final String BODY_IS_EMPTY = "1099";
    public static final String INVALID_DEV_TIME = "1101";
    public static final String INVALID_TRUCKDATE_FORMAT = "E1065";

    public static final String INVALID_ENROLLMENT = "E1010";
    public static final String INVALID_CLIENTID = "E1011";
    public static final String INVALID_CLIENTIMAGE = "E1012";
    public static final String MISSING_ACCOUNTNO = "E1041";
    public static final String INVALID_ACCOUNTTYPE = "E1042";
    public static final String INVALID_CREDITTYPE = "E1043";
    public static final String INVALID_TXNAMOUNT = "E1044";
    public static final String NEWACCOUNT_CREDITTYPE_MISMATCH = "E1045";
    public static final String INVALID_ACCOUNT_TXN = "E1046";
    public static final String ACCOUNT_BALANCE_ZERO = "E1047";
    public static final String ACCOUNT_BALANCE_LOWER_THAN_TXN_AMOUNT = "E1048";
    public static final String MTA_NOTSAVED = "E1049";
    public static final String MISSING_ACCOUNT_TYPE = "E10410";
    public static final String MISSING_CREDIT_TYPE = "E10411";
    public static final String MISSING_TXN_AMOUNT = "E10412";
    public static final String LARGE_DESCRIPSION = "E10413";

    public static final String CLIENT_WITH_NO_TXN = "E1051";

    // Agent
    public static final String AGENT_ID_EMPTY = "11001";
    public static final String INVALID_AGENT = "11002";
    public static final String AGENT_DISABLED = "11003";
    public static final String AGENT_CARD_UNAVAILABLE = "11004";
    public static final String AGENT_CARD_INVALID = "11005";
    public static final String AGENT_CARD_INACTIVE = "11006";
    public static final String AGENT_CARD_BLOCKED = "11007";
    public static final String AGENT_BIOMETRICS_EXIST = "11008";
    public static final String AGENT_SERVICE_POINT_MAPPING_UNAVAILABLE = "11009";
    public static final String AGENT_DEVICE_MAPPING_UNAVAILABLE = "11010";
    public static final String AGENT_ACCOUNT_UNAVAILABLE = "11011";
    public static final String AGENT_ACCOUNT_INVALID = "11012";
    public static final String AGENT_ACCOUNT_INACTIVE = "11013";
    public static final String AGENT_ACCOUNT_BLOCKED = "11014";
    public static final String AGENT_ACCOUNT_TYPE_EMPTY = "11015";
    public static final String AGENT_ACCOUNT_TYPE_INVALID = "11016";
    public static final String AGENT_DEVICE_MAPPING_UNAUTHORIZED = "11017";
    public static final String EMPTY_AGENT_CARD_ID = "11018";
    public static final String AGENT_UNAUTHORIZED = "11019";
    public static final String AGENT_SAMITHI_UNAVAILABLE = "11020";
    public static final String EMPTY_REMOTE_NEW_PASSWORD = "11021";
    public static final String EMPTY_REMOTE_PASSWORD_DATE = "11022";

    // Device
    public static final String EMPTY_SERIAL_NO = "21001";
    public static final String INVALID_DEVICE = "21002";
    public static final String DEVICE_DISABLED = "21003";

    // Service point
    public static final String EMPTY_SERV_POINT_ID = "31001";
    public static final String INVALID_SERVICE_POINT = "31002";

    // Client
    public static final String EMPTY_CLIENT_ID = "41001";
    public static final String INVALID_CLIENT = "41002";
    public static final String CLIENT_EXIST = "41003";
    public static final String CLIENT_BIOMETRICS_EXIST = "41004";
    public static final String CLIENT_CARD_UNAVAILABLE = "41005";
    public static final String CLIENT_CARD_INVALID = "41006";
    public static final String CLIENT_CARD_INACTIVE = "41007";
    public static final String CLIENT_CARD_BLOCKED = "41008";
    public static final String CLIENT_ACCOUNT_UNAVAILABLE = "41009";
    public static final String CLIENT_ACCOUNT_INVALID = "41010";
    public static final String CLIENT_ACCOUNT_INACTIVE = "41011";
    public static final String CLIENT_ACCOUNT_BLOCKED = "41012";
    public static final String CLIENT_ACCOUNT_TYPE_EMPTY = "41013";
    public static final String CLIENT_ACCOUNT_TYPE_INVALID = "41014";
    public static final String CLIENT_ACCOUNT_NO_INVALID = "41015";
    public static final String CLIENT_INACTIVE = "41016";
    public static final String CLIENT_BLOCKED = "41017";

    // Txn
    public static final String TXN_TYPE_EMPTY = "51001";
    public static final String TXN_TYPE_UNAVAILABLE = "51002";
    public static final String INVALID_CREDENTIAL = "51003";
    public static final String INVALID_DATE_FORMAT = "51004";
    public static final String INVALID_OPERATION_TYPE = "51005";
    public static final String DUPLICATE_TXN = "51006";
    public static final String BOD_EXIST = "51007";
    public static final String EOD_EXIST = "51008";
    public static final String EMPTY_TXN_AMT = "51009";
    public static final String EMPTY_RECEIPT_ID = "51010";
    public static final String INSUFFICIENT_BAL = "51011";
    public static final String TXN_REJECTED = "51012";
    public static final String WEB_BOD_EXIST = "51013";

    // Customer
    public static final String INVALID_CUSTOMER = "61001";
    public static final String EMPTY_CUSTOMER_ID = "61002";
    public static final String CUSTOMER_EXIST = "61003";
    public static final String EMPTY_FIRST_NAME = "61004";

    // Product
    public static final String PRODUCT_DOES_NOT_EXIST = "61005";

    // Farm Crops Download
    public static final String FARM_CROPS_DOES_NOT_EXIST = "61006";

    // Shop Dealer
    public static final String EMPTY_SHOP_DEALER_ID = "61007";
    public static final String SHOP_DEALER_EXIST = "61008";
    public static final String SHOP_DEALER_CARD_EXIST = "61100";
    public static final String EMPTY_SHOP_DEALER_CARD_ID = "61101";
    public static final String SHOP_DEALER_CARD_UNAVAILABLE = "61102";
    public static final String SHOP_DEALER_BIOMETRICS_EXIST = "61103";
    public static final String SHOP_DEALER_CARD_INACTIVE = "61104";
    public static final String EMPTY_SHOP_DEALER_LAST_NAME = "61105";
    public static final String EMPTY_SHOP_DEALER_GENDER = "61106";
    public static final String INVALID_SHOP_DEALER_GENDER = "61107";
    public static final String EMPTY_SHOP_DEALER_CITY_CODE = "61108";
    public static final String INVALID_SHOP_DEALER_CITY_CODE = "61109";
    public static final String EMPTY_SHOP_DEALER_ADDRESS = "61110";
    public static final String EMPTY_SHOP_DEALER_MOBILE_NUMBER = "61111";
    public static final String EMPTY_SHOP_DEALER_PINCODE = "61112";
    public static final String EMPTY_SHOP_DEALER_SHOP_NAME = "61113";
    public static final String EMPTY_SHOP_DEALER_CONTACT_PERSON_FIRST_NAME = "61114";
    public static final String EMPTY_SHOP_DEALER_CONTACT_PERSON_LAST_NAME = "61115";
    public static final String EMPTY_SHOP_DEALER_FIRST_NAME = "61116";
    public static final String INVALID_SHOP_DEALER_DOB_DATE_FORMAT = "61117";

    // Farmer
    public static final String EMPTY_FARMER_ID = "61009";
    public static final String FARMER_EXIST = "61010";
    public static final String FARMER_CARD_EXIST = "61200";
    public static final String EMPTY_FARMER_CARD_ID = "61201";
    public static final String FARMER_CARD_UNAVAILABLE = "61202";
    public static final String FARMER_BIOMETRICS_EXIST = "61203";
    public static final String FARMER_CARD_INACTIVE = "61204";
    public static final String FARMER_ACCOUNT_EXIST = "61205";
    public static final String FARMER_ACCOUNT_UNAVAILABLE = "61206";
    public static final String FARMER_ACCOUNT_INACTIVE = "61207";
    public static final String FARMER_ACCOUNT_BLOCKED = "61208";

    public static final String SHOP_DEALER_CREDIT_NOT_EXIST = "61011";
    public static final String ERROR_SAVING_FARM = "61012";
    public static final String ERROR_SAVING_FARM_CROPS = "61013";
    public static final String CREDIT_LIMIT_EXCEEDS = "61014";
    public static final String SHOP_DEALER_NOT_EXIST = "61015";
    public static final String ORDER_NO_EMPTY = "61016";
    public static final String ORDER_NOT_EXIST = "61017";
    public static final String ORDER_CANT_CANCELLED = "61018";
    public static final String ORDER_ALREADY_CANCELLED = "61019";
    public static final String ORDER_EXIST = "61020";
    public static final String DELIVERY_NO_EMPTY = "61021";
    public static final String DELIVERY_EXIST = "61022";

    public static final String EMPTY_REVISION_NO = "61023";

    public static final String EMPTY_LAST_NAME = "61024";
    public static final String EMPTY_GENDER = "61025";
    public static final String EMPTY_ADDRESS = "61026";
    public static final String EMPTY_CITY_CODE = "61027";
    public static final String CITY_DOES_NOT_EXIST = "61028";
    public static final String VILLAGE_NOT_BELONGS_TO_CITY = "61029";
    public static final String EMPTY_POSTAL_CODE = "61030";
    public static final String EMPTY_FARM_CODE = "61031";
    public static final String EMPTY_FARM_NAME = "61032";
    public static final String EMPTY_FARM_CROP_CODE = "61033";
    public static final String EMPTY_FARM_CODE_REFERENCE = "61034";
    public static final String EMPTY_FARM_CROP_AREA = "61035";
    public static final String EMPTY_FARM_PRODUCTION_YEAR = "61036";
    public static final String INVALID_FARMER_DOB = "61037";

    public static final String EMPTY_LATITUDE = "61038";
    public static final String EMPTY_LONGITUDE = "61039";

    public static final String EMPTY_FARM_LATITUDE = "61040";
    public static final String EMPTY_FARM_LONGITUDE = "61041";

    public static final String FARMER_INACTIVE = "61042";
    public static final String VILLAGE_NOT_MAPPED_WITH_SAMITHI = "61043";
    public static final String INVALID_FARMER_DOJ = "61044";
    public static final String EMPTY_SAMITHI_CODE = "61045";
    public static final String INVALID_SAMITHI = "61046";
    public static final String SAMITHI_NOT_MAPPED_WITH_FIELD_STAFF = "61047";
    public static final String FARM_CODE_EXIST = "61048";
    public static final String FARM_CROPS_ALREADY_MAPPED_WITH_THIS_FARM = "61049";

    public static final String EMPTY_FARMER_REVISION_NO = "61050";

    public static final String EMPTY_FARMER_OUTSTANDING_BALANCE_REVISION_NO = "61051";
    public static final String EMPTY_PRODUCTS_REVISION_NO = "61052";
    public static final String EMPTY_FARMCROPS_REVISION_NO = "61053";
    public static final String EMPTY_PROCUREMENT_PRODUCT_REVISION_NO = "61054";
    public static final String EMPTY_VILLAGE_WAREHOUSE_STOCK_REVISION_NO = "61055";
    public static final String EMPTY_GRADE_REVISION_NO = "61056";
    public static final String EMPTY_WAREHOUSE_PRODUCT_STOCK_REVISION_NO = "61057";
    public static final String EMPTY_COOPERATIVE_REVISION_NO = "61058";
    public static final String EMPTY_SEASON_REVISION_NO = "61060";

    public static final String EMPTY_FARM_ID = "61059";

    public static final String EMPTY_TRAINING_CRITERIA_CATEGORY_REVISION_NO = "61061";
    public static final String EMPTY_ENROLLMENT_PLACE = "61062";
    public static final String INVALID_FARMER_ID = "61063";
    public static final String EMPTY_FARMER_MOBILE = "61064";
    public static final String EMPTY_FARMER_DATE_OF_JOINING = "61065";
    public static final String EMPTY_FARM_PHOTO = "61066";
    public static final String EMPTY_FARM_PHOTO_CAPTURE_DT = "61067";
    public static final String EMPTY_FARM_PHOTO_LATITUDE = "61068";
    public static final String EMPTY_FARM_PHOTO_LONGITUDE = "61069";
    public static final String EMPTY_FARM_REGISTRATION_YEAR = "61070";
    public static final String EMPTY_FARM_VARIETY_CODE = "61071";
    public static final String FARM_VARIETY_DOES_NOT_EXIST = "61072";
    
    // Product Distribution
    public static final String EMPTY_RECEIPT_NO = "62001";
    public static final String DISTRIBUTION_EXIST = "62002";
    public static final String FARMER_NOT_EXIST = "62003";
    public static final String EMPTY_PRODUCT_CODE = "62004";
    public static final String EMPTY_WAREHOUSE_CODE = "62005";
    public static final String WAREHOUSE_DOES_NOT_EXIST = "62006";
    public static final String WAREHOUSEPRODUCT_NOT_EXIST = "62007";
    public static final String PRODUCT_RETURN_FROM_FARMER_EXIST = "62008";
    public static final String PRODUCT_RETURN_FROM_FIELDSTAFF_EXIST = "62009";
    public static final String EMPTY_DISTRIBUTION_DATE = "62010";

    // Product Procurement
    public static final String PROCUREMENT_EXIST = "63001";
    public static final String EMPTY_VILLAGE_CODE = "63002";
    public static final String VILLAGE_NOT_EXIST = "63003";
    public static final String EMPTY_CHART_NO = "63004";
    public static final String EMPTY_VEHICLE_NO = "63005";
    public static final String EMPTY_DRIVER_NAME = "63006";
    public static final String EMPTY_PO_NUMBER = "63007";
    public static final String INVALID_TRIP_SHEET_DATE = "63008";
    public static final String INVALID_CITY = "63009";
    public static final String QUALITY_DOES_NOT_EXIST = "63010";
    public static final String SAMITHI_NOT_EXIST = "63012";
    public static final String INVALID_AGENT_TYPE_FOR_PROCUREMENT = "63013";
    public static final String PRODUCT_VARIETY_MISMATCH = "63014";

    // MTNT
    public static final String MTNT_EXIST = "64001";
    public static final String AGENT_WAREHOUSE_MAPPING = "64002";
    public static final String VILLAGE_WAREHOUSE_NOT_EXIST = "64003";
    public static final String MTNR_EXIST = "64004";
    public static final String EMPTY_TRUCK_ID = "64005";
    public static final String EMPTY_DRIVER_ID = "64006";

    // Agent Movement
    public static final String FARM_NOT_EXIST = "65001";
    public static final String EMPTY_PURPOSE_OF_VISIT = "65002";
    public static final String INVALID_IMAGE = "65003";
    public static final String INVALID_FARM = "65004";
    public static final String ERROR_WHILE_PROCESSING_FARMER_VOICE = "65005";

    // Payment Adapter
    public static final String PAYMENT_ALREADY_EXIST = "66001";
    public static final String EMPTY_SEASON_CODE = "66002";
    public static final String EMPTY_PAGE_NO = "66003";
    public static final String EMPTY_PAYMENT_TYPE = "66004";
    public static final String INVALID_PAYMENT_MODE = "66005";
    public static final String SEASON_NOT_EXIST = "66006";
    public static final String EMPTY_PAYMENT_DATE = "66007";

    // Distribution MTNT
    public static final String EMPTY_RECEIVER_AREA = "67001";
    public static final String INVALID_RECEIVER_AREA = "67002";
    public static final String DISTRIBUTION_MTNT_EXIST = "67003";
    public static final String INVALID_SENDER_WAREHOUSE = "67004";
    public static final String EMPTY_MTNT_RECEIPT_NO = "67005";
    public static final String EMPTY_PROCUREMENT_GRADE = "67006";

    // Distribution MTNR
    public static final String EMPTY_MTNR_RECEIPT_NO = "68001";
    public static final String DISTRIBUTION_MTNR_EXIST = "68002";
    public static final String MTNT_DOES_NOT_EXIST = "68003";

    // Procurement MTNT
    public static final String TRIP_SHEET_DOES_NOT_EXIST = "69001";
    public static final String NO_CHART_FOUND = "69002";
    public static final String EMPTY_CO_OPERATIVE_CODE = "69003";
    public static final String CO_OPERATIVE_NOT_EXIST = "69004";
    public static final String PROCUREMENT_MENT_PRODUCTS_NOT_FOUND = "69005";
    public static final String EMPTY_NO_OF_BAGS = "69006";
    public static final String INVALID_AGENT_TYPE_FOR_MTNT = "69007";
    public static final String INVALID_NO_OF_BAGS = "69008";
    public static final String INVALID_GROSS_WEIGHT = "69009";
    public static final String EMPTY_GRADE_CODE = "69010";
    public static final String GRADE_DOES_NOT_EXIST = "69011";

    // Procurement MTNR
    public static final String EMPTY_MTNT_RECEIPT_ID = "70001";
    public static final String MTNT_NOT_AVAILABLE = "70002";
    public static final String MTNT_ALREADY_PROCESSED = "70003";
    public static final String EMPTY_MTNR_RECEIPT_ID = "70004";
    public static final String MTNR_RECEIPT_ID_EXIST = "70005";
    public static final String INVALID_GRADE = "70006";
    public static final String INVALID_PRODUCT = "70007";

    // Crop Inspection Upload
    public static final String EMPTY_SECTIONS = "71001";
    public static final String EMPTY_QUESTIONS = "71002";
    public static final String EMPTY_ANSWERS = "71003";
    public static final String EMPTY_SUB_QUESTIONS = "71004";
    public static final String INVALID_QUESTION_CODE = "71005";
    public static final String INVALID_QUESTION_SUB_QUESTION_MAPPING = "71006";
    public static final String INVALID_SECTION_QUESTION_MAPPING = "71007";
    public static final String INVALID_SECTION_CODE = "71008";
    public static final String INVALID_CATEGORY_STANDARD_MAPPING = "71009";

    // Training Txn's
    public static final String TRAINING_STATUS_EXIST = "72001";
    public static final String EMPTY_LEARNING_GROUP_CODE = "72002";
    public static final String FARMERS_ATTENDED_INVALID = "72003";
    public static final String EMPTY_CRITERIA_CODE = "72004";
    public static final String INVALID_CRITERIA = "72005";
    public static final String EMPTY_TRAINING_CODE = "72006";
    public static final String INVALID_FARMER_TRAINING = "72007";
    public static final String LEARNING_GROUP_NOT_EXIST = "72008";

    public static final String EMPTY_PRICE_PATTERN_REVISION_NO = "73001";

    // Fields Related To Certification
    public static final String EMPTY_FARMER_CERTIFICATION_TYPE = "74001";
    public static final String EMPTY_CERTIFICATION_CATEGORY = "74002";
    public static final String EMPTY_CLIENT = "74003";
    public static final String EMPTY_CERTIFICATION_STANDARD = "74004";
    public static final String EMPTY_CLIENT_PROJECT = "74005";
    public static final String EMPTY_FAMILY_MEMBERS_NAME = "74006";
    public static final String EMPTY_FARMER_RELATION = "74007";
    public static final String EMPTY_FARMER_HOUSE_OWNERSHIP = "74008";
    public static final String EMPTY_FARMER_HOUSE_TYPE = "74009";
    public static final String EMPTY_FARMER_ANNUAL_INCOME = "74010";
    public static final String EMPTY_INVENTORY_ITEM_ID = "74011";
    public static final String EMPTY_INVENTORY_ITEM_COUNT = "74012";
    public static final String EMPTY_FARM_ANIMAL = "74013";
    public static final String EMPTY_FARM_ANIMAL_COUNT = "74014";
    public static final String EMPTY_FARM_ANIMAL_FEED_USED = "74015";
    public static final String EMPTY_FARM_ANIMAL_HOUSE = "74016";
    public static final String EMPTY_CROP_SEASON = "74017";
    public static final String EMPTY_CROP_CATEGORY = "74018";
    public static final String EMPTY_HARVEST_FARM_CROPS_CODE_REF = "74019";
    public static final String INVALID_HARVEST_FARM_CROPS_CODE = "74020";
    public static final String EMPTY_HARVEST_DATE = "74021";
    public static final String EMPTY_HARVEST_QUANTITY = "74022";
    public static final String EMPTY_HARVEST_AMOUNT = "74023";
    public static final String EMPTY_BUYER_NAME = "74024";
    public static final String INVALID_CERTIFICATION_CATEGORY = "74025";
    public static final String INVALID_CERTIFICATION_STANDARD = "74026";
    public static final String INVALID_CUSTOMER_PROJECT = "74027";
    public static final String EMPTY_INSPECTION_TYPE = "74028";
    public static final String EMPTY_ICS_STATUS = "74029";
    public static final String CERTIFICATE_STANDARD_NOT_MAPPED_WITH_CERTIFICATE_CATEGORY = "74030";
    public static final String CLIENT_NOT_MAPPED_WITH_CLIENT_PROJECT = "74031";
    

    /**
     * Load switch error codes.
     */
    public void loadSwitchErrorCodes();

    /** Periodic Inspection Error Codes **/

    public static final String INVALID_INSPECTION_TYPE = "74032";
    public static final String EMPTY_INSPECTION_DATE = "74033";
    public static final String INVALID_INSPECTION_DATE = "74034";
    public static final String INVALID_FARM_ID = "74035";
    public static final String EMPTY_CURRENT_STATUS = "74036";
    public static final String INVALID_STATUS_OF_GROWTH = "74037";
    public static final String ERR0R_WHILE_PROCESSING = "74038";
    public static final String EMPTY_PR_FARM_ID = "74039";

    // Fields Related To Bank

    public static final String EMPTY_BANK_NAME = "74040";
    public static final String EMPTY_ACCOUNT_NUMBER = "74041";
    public static final String EMPTY_BRANCH = "74042";
    public static final String EMPTY_SORT_CODE = "74043";
    public static final String EMPTY_SWIFT_CODE = "74044";

    public static final String EMPTY_FARMER_CERTIFIED = "74045";
    public static final String EMPTY_ADULT = "74046";
    public static final String EMPTY_CHILD = "74047";
    public static final String EMPTY_PROFESSION = "74048";
    public static final String EMPTY_OTHER_PROFESSION = "74049";
    
    public static final String EMPTY_ENROLLMENT_PLACE_OTHER = "74050";
    public static final String EMPTY_FARMER_HOUSE_TYPE_OTHER="74051";
    
    /** Periodic Inspection Error Codes **/

    public static final String INVALID_GAP_PLANTING_DATE = "74052";
    
    /** Cash Received & Settlement */
    public static final String EMPTY_CASH_RECEIVED_DATE = "75001";
    public static final String EMPTY_CASH_RECEIVED_MODE = "75002";
    public static final String EMPTY_CASH_AMOUNT = "75003";
    
    public static final String EMPTY_CASH_SETTLEMENT_DATE = "75004";
    public static final String EMPTY_CASH_SETTLEMENT_MODE = "75005";
    public static final String EMPTY_PHOTO_PROOF = "75006";
    public static final String EMPTY_COMPANY_NAME = "75007";
    public static final String EMPTY_PERSON_NAME = "75008";
    public static final String EMPTY_CATALOGUE_REVISION_NO = "75021";
    public static final String EMPTY_SUPPLIER_REVISION_NO = "75031";
 /** Crop Harvest **/
    
    public static final String EMPTY_CROP_TYPE = "75013";
    public static final String EMPTY_CROP_GRADE_CODE = "75014";
    public static final String EMPTY_CROP_ID = "75015";
    public static final String EMPTY_CROP_VARIETY_ID = "75016";
    public static final String EMPTY_FARMER = "75017";
    public static final String EMPTY_FARM = "75018";
    public static final String EMPTY_TOTAL_QTY = "75019"; 

    
    /** Buyer **/ 
    public static final String EMPTY_BUYER_REVISION_NO = "75020";
 
    
    
    /**Cost of Cultivation */
    public static final String EMPTY_EXPENSE_TYPE = "75025";
	public static final String EMPTY_COC_DATE = "75026";
    
    
    
	public static final String EMPTY_FARM_VARIETY_CODE_MAIN = "75011";
    public static final String FARM_VARIETY_DOES_NOT_EXIST_MAIN = "75012";
	public static final String EMPTY_POLY_HOUSE_ITEM = "75000";
	public static final String EMPTY_POLY_COUNT = "75010";
	public static final String EMPTY_MACHINARY_ITEM = "75111";
	public static final String EMPTY_MACHINARY_COUNT = "75222";
	
	
	
	
	public static final String EMPTY_SOWING_DATE = "75223";
	public static final String EMPTY_ESTIMATED_HARVEST_DATE = "75224";
    
	public static final String EMPTY_COW_ID = "75032";
	public static final String EMPTY_RESEARCH_STATION_REVISION_NO = "75032";
	
	public static final String EMPTY_TRAINING_TOPIC = "75034";
	public static final String CRITERIA_NOT_EXISTS = "75035";
	public static final String EMPTY_TRAINING_MATERIAL = "75036";
	public static final String TRAINING_MATERIAL_NOT_EXISTS = "75037";
	public static final String EMPTY_TRAINING_METHOD = "75038";
	public static final String TRAINING_METHOD_NOT_EXISTS = "75039";
	public static final String EMPTY_TRAINING_PHOTO="75040";
	public static final String EMPTY_TRAINING_TIME="75041";
	public static final String EMPTY_FARM_LAND_DETAILS = "75042";
	public static final String EMPTY_PREHARVEST_DATE = "75043";
	public static final String EMPTY_AREA_PRODUCTION = "75044";
	public static final String EMPTY_FARM_MAIN_CROP = "75045";
	public static final String EMPTY_YEAR_OF_ICS = "75046";
	public static final String EMPTY_AGE="75047";
	public static final String EMPTY_DISABILITY_DETAIL="75048";
	public static final String MTNR_NOT_EXIST = "75049";
	public static final String INVALID_ICS_CODE="75050";
	public static final String INVALID_HEAP_CODE="75051";
	public static final String EMPTY_HEAP_CODE="75052";
	public static final String EMPTY_ICS_CODE="75053";
	public static final String EMPTY_PROCESS_STOCK="75054";
	public static final String EMPTY_LINT_STOCK="75055";
	public static final String EMPTY_SCRAP_STOCK="75056";
	public static final String EMPTY_SEED_STOCK="75057";
	public static final String EMPTY_PROCESS_DATE="75058";
	public static final String EMPTY_TRANSFER_DATE = "75059";
	public static final String EMPTY_INVOICE_NO = "75060";
	public static final String EMPTY_SPINNER_CODE = "75061";
	public static final String EMPTY_LOT_NO = "75062";
	public static final String EMPTY_PR_NO = "75063";
	public static final String EMPTY_NO_OF_BALES = "75064";
	public static final String EMPTY_NET_WEIGHT = "75065";
	public static final String EMPTY_RATE = "75066";
	public static final String EMPTY_NET_RATE = "75067";
	public static final String EMPTY_TYPE = "75068";
	public static final String EMPTY_VILLAGE_NAME = "75069";
	

}
