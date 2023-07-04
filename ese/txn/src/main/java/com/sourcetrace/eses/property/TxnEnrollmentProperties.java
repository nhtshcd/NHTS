/*
 * TransactionEnrollmentProperties.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.property;

public class TxnEnrollmentProperties {

	public static final String HEAD = "head";

	/** AGENT LOGIN DATA **/
	public static final String CLIENT_ID_SEQ = "clientIdSeq";
	public static final String AGENT_LOGIN_RESP_KEY = "agentLogin";

	/** AGENT ENROLLMENT DATA **/
	public static final String AGENT_ID = "agentId";
	public static final String FIRST_NAME = "firstName";
	public static final String MIDDLE_NAME = "middleName";
	public static final String LAST_NAME = "lastName";
	public static final String ADDRESS1 = "address1";
	public static final String ADDRESS2 = "address2";
	public static final String CITY_CODE = "city";
	public static final String DISTRICT_CODE = "district";
	public static final String STATE_CODE = "state";
	public static final String value = "country";
	public static final String ZIPCODE = "zipCode";
	public static final String DOB = "dob";
	public static final String VILLAGE_CODE = "village";
	public static final String AGENT_NAME = "agentName";
	public static final String AGENT_CITY_CODE = "cityCode";

	/** AGENT ENROLLMENT BIO METRIC UPLOAD DATA **/
	public static final String PHOTO = "photo";
	public static final String FINGER_PRINT = "fp";
	public static final String AGENT_CARD_ID = "agCardId";

	/** CLIENT ENROLLMENT DATA **/
	public static final String CLIENT_ID = "clientId";
	public static final String PHONE_NO = "phoneNo";
	public static final String MOBILE_NO = "mobileNo";
	public static final String OCCUPATION = "occupation";
	public static final String ID_TYPE = "idType";
	public static final String ID_NO = "idNo";
	public static final String PLACE_OF_BIRTH = "pob";
	public static final String SEX = "sex";
	public static final String CARD_ID = "cardId";
	public static final String ACCOUNT_NO = "acctNo";
	public static final String NOMINEE_RELATIONSHIP = "nRelation";
	public static final String NOMINEE_NAME = "nName";
	public static final String NOMINEE_ADDRESS = "nAddress";
	public static final String NOMINEE_DOB = "nDOB";
	public static final String EMAIL = "email";
	public static final String ENROLL_DATE_TIME = "enrollDateTime";

	/** CUSTOMER REGISTRSTION **/
	public static final String CUSTOMER_ID = "customerId";
	public static final String STORE_NAME = "storeName";
	public static final String ADDRESS = "address";
	public static final String LOCATION = "location";
	public static final String PINCODE = "pinCode";
	public static final String CONTACT_NO = "contactNo";

	/** PLACE ORDER **/
	public static final String ORDER_STATUS = "orderStatus";
	public static final String ORDER_DATE = "orderDate";
	public static final String TOTAL_ORDER = "totalOrder";
	public static final String TOTAL_ORDER_PRICE = "totalOrderPrice";
	public static final String ORDER_DETAIL = "orderDetail";
	public static final String SUB_TOTAL = "subTotal";
	public static final String CATEGORY_CODE = "categoryCode";
	public static final String CATEGORY_NAME = "categoryName";
	public static final String SUB_CATEGORY_CODE = "subCategoryCode";
	public static final String SUB_CATEGORY_NAME = "subCategoryName";
	public static final String PRODUCT_CODE = "productCode";
	public static final String PRODUCT_NAME = "productName";
	public static final String QUANTITY = "quantity";
	public static final String PRODUCT_LIST = "products";
	public static final String PRICE = "price";

	/** PRODUCT DOWNLOAD **/
	public static final String UNIT = "unit";
	public static final String MANUFACTURE="manufacture";
	public static final String INGREDIENT="ingredient";
	public static final String MANUFACTURE_CODE="manufactureId";

	/** FARM CROPS DOWNLOAD **/
	public static final String FARM_CROPS_CODE = "fcode";
	public static final String FARM_CROPS_NAME = "fname";
	public static final String FARM_CROPS_LIST = "cropList";
	public static final String CROP_AREA = "carea";
	public static final String PRODUCTION_YEAR = "pYear";
	public static final String FARM_LIST = "farmList";
	public static final String FARM_CODE_REFERENCE = "farmCodeRef";
	public static final String FARM_CROP_TYPE_OTHER = "typeOth";
	public static final String FARM_CULTI_AREA = "culArea";

	public static final String PROCUREMENT_PROD_UNIT = "ppUnit";
	public static final String PRODUCT_CATEGORY = "crpType";

	/** SHOP DEALER ENROLLMENT **/
	public static final String SHOP_DEALER_ID = "sdCode";
	public static final String SHOP_DEALER_NAME = "sdName";
	public static final String SHOP_NAME = "shopName";
	public static final String CONTACT_PERSON = "cp";
	public static final String CONTACT_PERSON_FIRST_NAME = "cpFName";
	public static final String CONTACT_PERSON_LAST_NAME = "cpLName";
	public static final String OUTSTANDING_BALANCE = "osBal";
	public static final String CREDIT_LIMIT = "crLmt";
	public static final String SHOP_DEALER_CREDIT_LIST = "crList";
	public static final String REVISION_NO = "revNo";
	public static final String SHOP_DEALER_LIST = "sdList";
	public static final String SHOP_DEALER_CARD_ID = "sdCardId";
	public static final String RATE_OF_INTEREST_FOB = "roi";
	public static final String PRINCIPAL_AMOUNT = "pAmt";
	public static final String INTEREST_AMOUNT_ACCUMULATED = "cumInt";

	/** FARMER ENROLLMENT **/
	public static final String FARMER_ID = "farmerId";
	public static final String FARMER_NAME = "farmerName";
	public static final String NO_OF_FAMILY_MEMBERS = "noOfMembers";
	public static final String FARM_CODE = "farmCode";
	public static final String FARM_NAME = "farmName";
	public static final String HECTARES = "hec";
	public static final String LAND_IN_PRODUCTION = "landProd";
	public static final String LAND_NOT_IN_PRODUCTION = "landNProd";
	public static final String FARMER_LIST = "farmerList";
	public static final String FARMER_CODE_REFERENCE_IN_FARM = "farmerCodeRef1";
	public static final String FARMER_CODE_REFERENCE_IN_FARM_CROPS = "farmerCodeRef2";
	public static final String FARMER_CARD_ID = "fCardId";
	public static final String FARMER_ACCOUNT_NO = "fAcctNo";
	public static final String FARMER_BALANCE_LIST = "frBalList";
	public static final String FINGER_PRINT_EXIST = "fpExist";
	public static final String FARMER_PHOTO_CAPTURE_TIME = "pcTime";
	public static final String FARM_LATITUDE = "fLat";
	public static final String FARM_LONGITUDE = "fLon";
	public static final String FARM_PHOTO = "fPhoto";
	public static final String FARM_PHOTO_CAPTURING_TIME = "fpcTime";
	public static final String POST_OFFICE = "postCode";
	public static final String DOJ = "doj";
	public static final String PROPOSED_PLANT_AREA = "frmProd";
	public static final String PLOTTING_STATUS = "pltStatus";
	public static final String GEO_STATUS ="geoStatus";

	public static final String FARMER_CODE = "farmerCode";
	public static final String ENROLLMENT_PLACE = "eP";
	public static final String ENROLLMENT_PLACE_OTHER = "ePO";
	public static final String FARM_LAND_AREA_GPS = "lAgps";
	public static final String FARM_LAND_AREA_LATITUDE = "laLat";
	public static final String FARM_LAND_AREA_LONGITUDE = "laLon";
	public static final String FARM_LAND_AREA_ORDER_NO = "orderNo";
	public static final String TRACE_ID = "trcId";
	public static final String FATHER_HUSBAND_NAME = "fhNme";
	public static final String POLICE_STATION = "plStn";
	public static final String FARMER_AUDIO = "fAud";
	public static final String ADULT_COUNT_MALE = "adCnt";
	public static final String CHILD_COUNT_MALE = "chCnt";

	public static final String TOTAL_LAND_HOLDING_AREA = "totLaAra";
	public static final String PROPOSED_PLANTING_AREA = "prPlAra";
	public static final String LAND_GRADIENT = "ldGrd";
	public static final String APPROACH_ROAD = "appRod";
	public static final String REGISTRATION_YEAR = "regYear";
	public static final String SOIL_TEXTURE = "solTxt";
	public static final String FERTILITY_STATUS = "frtStat";
	public static final String IRRIGATION_SOURCEZ = "isr";
	public static final String IRRIGATION_SOURCE_TYPES = "isTyp";
	public static final String IRRIGATION_SOURCE_TYPES_OTHER = "isTypOth";
	public static final String FARM_INVENTORY_ITEM_OTHER = "othFii";
	public static final String FARM_ANIMAL_OTHER = "faOth";
	public static final String FODDER = "fod";
	public static final String FODDER_OTHER = "fodOth";
	public static final String FARM_ANIMAL_HOUSE_OTHER = "ahOth";
	public static final String REVENUE = "rev";
	public static final String REVENUE_OTHER = "othRev";
	public static final String BREED = "breed";
	public static final String ICS_LIST = "icsLst";
	public static final String ICS_TYPE = "icsTyp";
	public static final String ICS_LAND_DETAILS = "icsDet";
	public static final String ICS_SURVEY_NO = "surNo";
	public static final String ICS_BEGIN_CONV = "begOfCon";
	public static final String SEED_SOURCE = "sdSur";
	public static final String IS_FARMER_VERIFIED = "isVrf";

	public static final String SOWING_DATE = "sowDt";
	public static final String ESTIMATED_HARVEST_DATE = "estHarvDt";

	public static final String OTHER_HOWSING_OWNER = "othrOwnship";
	public static final String ENROLL_DATE = "enrolDt";
	public static final String ICS_NAME = "icsNme";
	public static final String ICS_CODE = "icsCde";
	public static final String TOTAL_FAMILY_COUNT = "famCnt";
	public static final String SCHOOL_COUNT_MALE = "schChCnt";
	public static final String TOTAL_HOUSEHOLD_MEMBERS = "houhCnt";
	public static final String MALE_COUNT = "malCnt";
	public static final String FEMALE_COUNT = "femalCnt";
	public static final String OTHER_INCOME = "othInc";
	public static final String CONSUMER_ELECTRONICS = "electro";
	public static final String VEHICLE = "vehicle";
	public static final String CELL_PHONE = "cell";

	public static final String DRINKING_WATER_SOURCE = "wtSrc";
	public static final String DRINKING_WATER_SOURCE_OTHER = "otrwtSrc";
	public static final String LIFE_INSURANCE = "lifeIns";
	public static final String CROP_INSURANCE = "cropIns";
	public static final String ACCOUNT_TYPE = "bAcType";
	public static final String IS_ELECTRIFIED = "isElHo";
	public static final String LOAN_TAKEN = "lonTkn";
	public static final String IS_LOAN_TAKEN_SCHEME = "farmLonScheme";
	public static final String LOAN_TAKEN_SCHEME = "loanGovscheme";
	public static final String LOAN_TAKEN_FROM = "lonDet";
	public static final String ANIMAL_HOUSING_OTHERS = "aniHouOth";
	public static final String FARM_EQUIPMENTS_OTHERS = "frEqipOth";

	public static final String MACHINARY_LIST = "machLst";
	public static final String MACHINARY_ITEM = "machId";
	public static final String MACHINARY_COUNT = "machCnt";

	public static final String POLY_HOUSE_LIST = "phLst";
	public static final String POLY_HOUSE_ITEM = "phId";
	public static final String POLY_HOUSE_COUNT = "phCnt";

	public static final String SUR_NAME = "surName";

	public static final String CULTIVATION_TYPE = "cultTyp";
	public static final String CULTI_AREA = "cultArea";

	public static final String CONVERSION_STS = "convSts";
	public static final String DATE_INSPECTION = "inspDt";
	public static final String INSPECTOR_NAME = "inspNme";
	public static final String IS_QUALIFIED = "isQul";
	public static final String SANCTION_REASON = "sanRea";
	public static final String SANCTION_DURATION = "sanDur";
	public static final String REG_NO = "regNo";
	public static final String METHOD_OF_IRRIGATION = "mthOfIrri";
	public static final String ATTEND_FFS = "attFFS";
	public static final String IS_FFS_BENIFITED = "benif";
	public static final String BOREWELL_STRUCTURE = "bore";
	public static final String MILLET_CULTIVATED = "millet";
	public static final String MILLET_CROP_TYPE = "whichCrp";
	public static final String MILLET_CROP_COUNT = "howMany";
	public static final String GRS_MEMEBER = "grs";
	public static final String PAID_SHARE_CAPITAL = "share";
	public static final String FARM_SAMITHI = "samCode";
	public static final String FARM_FPO_GROUP = "share";
	public static final String FARMER_FINGER_PRINT = "fingerPrint";
	public static final String FARMER_DIGITAL_SIGNATURE = "digitalSign";

	public static final String FARM_LAND = "frmLndDetails";
	public static final String FARM_VARIETY_LAND = "farmvarty";

	public static final String FARM_PLOT_NO = "fpltNo";
	public static final String OTHER_VILLAGE = "otherVill";
	
	public static final String LANDMARK = "landmark";
	public static final String TOTAL_AREA = "totlarea";

	/** ORDER INVENTORY **/
	public static final String ORDER_NO = "ordNo";
	public static final String DELIVERY_NO = "dlryNo";
	public static final String GRAND_TOTAL = "grdTotal";
	public static final String PAYMENT_MODE = "pmtMode";
	public static final String PAYMENT_REFERENCE = "pmtRef";
	public static final String PAYMENT_AMOUNT = "pmtAmt";
	public static final String DELIVERY_MODE = "dlryMode";
	public static final String INVENTORY_DETAIL = "inventoryDetail";
	public static final String PRICE_PER_UNIT = "pricePerUnit";
	public static final String TOTAL_QUANTITY = "totalQty";
	public static final String DELIVERY_DATE = "deliveryDate";
	public static final String APPROVAL_CODE = "aprCode";

	/** PRODUCT DISTRIBUTION **/
	public static final String PRODUCT_DISTRIBUTION_DATE = "pdDate";
	public static final String RECEIPT_NO = "recNo";
	public static final String STOCK = "stock";
	public static final String WAREHOUSE_CATEGORY_CODE = "cCode";
	public static final String WAREHOUSE_SUB_CATEGORY_CODE = "sCode";
	public static final String WAREHOUSE_PRODUCT_CODE = "pCode";
	public static final String WAREHOUSE_STOCK_LIST = "stocks";
	public static final String PAYMENT = "amt";
	public static final String TAX = "tax";
	public static final String MODE_OF_PAYMENT = "mode";
	public static final String IS_FREE_DISTRIBUTION = "pdTyp";

	/** PROCUREMENT PRODUCT ENROLLMENT **/
	public static final String PROCUREMENT_PRODUCT_DETAIL = "procProducts";
	public static final String QUALITY = "quality";
	public static final String NO_OF_BAGS = "bags";
	public static final String NO_OF_FRUIT_BAGS="nofrutbgs";
	public static final String GROSS_WEIGHT = "grossWt";
	public static final String TARE_WEIGHT = "tareWt";
	public static final String NET_WEIGHT = "netWt";
	public static final String PROCUREMENT_DATE = "procDate";
	public static final String TOTAL_AMOUNT = "totalAmt";
	public static final String CHART_NO = "chartNo";
	public static final String AREA_CODE = "cityCode";
	public static final String VEHICLE_NO = "vehicleNo";
	public static final String DRIVER_NAME = "driverName";
	public static final String PO_NUMBER = "poNo";
	public static final String PROCUREMENT_VILLAGE_CODE = "villageCode";
	public static final String CURRENT_SEASON_CODE = "cSeasonCode";
	public static final String ROAD_MAP_CODE = "roadMapCode";
	public static final String SUBSTITUTE_NAME = "substituteName";
	public static final String FARMER_ATTENDANCE = "farmerAttnce";
	public static final String VEHICLE_NUM = "vehicle";
	public static final String PROCURMENT_UNIT = "procUnit";
	public static final String LABOUR_COST = "labourCost";
	public static final String TRANS_COST = "transCost";
	public static final String INVOICE_VALUE = "invoiceVal";
	public static final String TAX_AMT = "tax";
	public static final String PROC_OTHER_COST = "otherCost";

	/** MTN ADAPTER **/
	public static final String TOTAL_NO_OF_BAGS = "tbags";
	public static final String TOTAL_GROSS_WEIGHT = "tgrossWt";
	// public static final String TOTAL_TARE_WEIGHT = "ttareWt";
	public static final String TOTAL_NET_WEIGHT = "tnetWt";
	public static final String MODE = "mode";
	public static final String MTNT_DATE = "mtntDate";
	public static final String MTNR_DATE = "mtnrDate";
	public static final String TRUCK_ID = "truckId";
	public static final String DRIVER_ID = "driverId";
	public static final String TRANSPORTER_NAMES = "transporter";
	/** AGENT MOVEMENT **/
	public static final String PURPOSE = "purpose";
	public static final String REMARKS = "remarks";
	public static final String AGENT_MOVEMENT_LOCATIONS = "locations";
	public static final String UPDATE_TIME = "timeStamp";
	public static final String LATITUDE = "lat";
	public static final String LONGITUDE = "lon";
	public static final String MOBILE_NUMBER="fMobNo";
	public static final String NATIONALID="NatId";

	/** GRADE DOWNLOAD **/
	public static final String GRADE_CODE = "gCode";
	public static final String GRADE_NAME = "gName";
	public static final String GRADE_PRICE = "price";
	public static final String PROCUREMENT_PROD_CODE = "ppCode";
	public static final String GRADES = "grades";
	public static final String GRADE_YIELD = "expHarvestQty";

	/** SEASON DOWNLOAD **/
	public static final String SEASON_CODE_DOWNLOAD = "sCode";
	public static final String SEASON_NAME = "sName";
	public static final String SEASON_YEAR = "year";
	public static final String SEASONS = "seasons";

	/** PAYMENT ADAPTER **/
	public static final String PAYMENT_DATE = "pDate";
	public static final String PAGE_NO = "pageNo";
	public static final String PAYMENT_TYPE = "pType";
	public static final String PAYMENT_AMT = "pAmount";
	public static final String SEASON_CODE = "seasonCode";

	/** DISTRIBUTION MTNT **/
	public static final String MTNT_RECEIPT_NO = "mtntRptNo";
	public static final String RECEIVER_AREA = "revArea";

	/** DISTRIBUTION MTNR **/
	public static final String MTNR_RECEIPT_NO = "mtnrRptNo";
	public static final String MTNT_RECEIPTS = "mtntRpts";
	public static final String SENDER_AREA = "sAreaCode";

	/** PROCUREMENT MTNT **/
	public static final String CHARTS = "charts";
	public static final String PROCUREMENT_MTNT_PRODUCTS = "products";
	public static final String CO_OPERATIVE_CODE = "coCode";

	/** PROCUREMENT MTNR **/
	public static final String MTNT_RECEIPT_NOS = "mtntRptNos";
	public static final String MTNT_PRODUCTS = "mtntProducts";

	/** PRICE PATTERN DOWNLOAD **/
	public static final String PRICE_PATTERN_LIST = "ppList";
	public static final String PRICE_PATTERN_NAME = "ppName";
	public static final String GRADE_LIST = "gradeList";
	public static final String CLIENT = "receiver";
	public static final String INVOICE_NO = "invoiceNo";

	/** CROP INSPECTION UPLOAD **/
	public static final String ANSWER_DATE = "ansDate";
	public static final String CERTIFICATE_CATEGORY = "certCategory";
	public static final String STANDARDS = "standards";
	public static final String STANDARD_CODE = "stCode";
	public static final String SECTIONS = "sections";
	public static final String MENU_LIST = "menulist";
	public static final String SECTION_CODE = "secCode";
	public static final String QUESTIONS = "questions";
	public static final String QUESTION_CODE = "queCode";
	public static final String NEED_FOLLOW_UP = "followup";
	public static final String QUESTION_LATITUDE = "latitude";
	public static final String QUESTION_LONGITUDE = "longitude";
	public static final String GPS_CAPTURE_DATE_TIME = "GPSCptreDateTime";
	public static final String ANSWERS = "answers";
	public static final String ANSWER_TYPE = "ansType";
	public static final String ANSWER = "answer";
	public static final String ANSWER_1 = "answer1";
	public static final String ANSWER_2 = "answer2";
	public static final String ANSWER_3 = "answer3";
	public static final String ANSWER_4 = "answer4";
	public static final String ANSWER_5 = "answer5";
	public static final String FOLLOW_UP = "followup";
	public static final String IS_REGISTERED_QUESTION = "isRegQuestion";
	public static final String QUESTION_NAME = "queName";
	public static final String SUB_QUESTIONS = "subQuestions";
	public static final String FARM_ID = "farmId";
	public static final String CROP_INSPECTION_FARM_LATITUDE = "lat";
	public static final String CROP_INSPECTION_FARM_LONGITUDE = "lng";
	public static final String LAND_HOLDING = "landHolding";
	public static final String CROP_YIELD_LIST = "cropYieldList";
	public static final String YIELD = "yield";
	public static final String CERTIFICATION_STATUS = "certStatus";

	/** CO-OPERATIVE DOWNLOAD **/
	public static final String COOPERATIVE_LIST = "coOperatives";
	public static final String COOPERATIVE_CODE = "coCode";
	public static final String COOPERATIVE_NAME = "coName";
	public static final String SAMITHI_LIST = "samithis";
	public static final String SAMITHI_CODE = "samCode";
	public static final String SAMITHI_NAME = "samName";
	public static final String SAMITHI_TYPE = "samTyp";
	public static final String UTZ_STATS = "utzStatus";
	public static final String COOPEARATIVE_SAMITHI_REF = "coOpCode";
	public static final String SAMITHI_VILLAGE_LIST = "samVillages";
	public static final String VILLAGE_COOPERATIVE_CODE = "vCode";
	public static final String VILLAGE_COOPEARATIVE_NAME = "vName";
	public static final String COOPEARATIVE_VILLAGE_REF = "refCoCode";
	public static final String SAMITHI_VILLAGE_REF = "refSamCode";

	public static final String VILLAGE_LIST = "villages";

	/** TRAINING TXN'S **/
	public static final String CRITERIA_CATEGORY_LIST = "criterias";
	public static final String CRITERIA_CATEGORY_CODE = "ccCode";
	public static final String CRITERIA_CATEGORY_NAME = "ccName";
	public static final String PRINCIPLE = "prin";
	public static final String DESCRIPTION = "desc";
	public static final String TRAINING_CODE_LIST = "trCodes";
	public static final String TRAINING_CODE = "trCode";
	public static final String CRITERIA_TRAINING_CODE_REF = "trCodeRef";
	public static final String PLANNER_TRAINING_CODE_REF = "trCodePlanRef";
	public static final String PLANNER_LIST = "planners";
	public static final String PLANNER = "planner";
	public static final String TRAINING_STATUS_DATE = "tsDate";
	public static final String LEARNING_GROUP_CODE = "lgCode";
	public static final String ADDITIONAL_PHOTO = "photo1";

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

	/** FIELDS RELATED TO CERTIFICATION **/
	public static final String FARMER_CERTIFICATION_TYPE = "fct";
	public static final String MARITAL_STATUS = "ms";
	public static final String EDUCATION = "edu";
	public static final String NO_OF_CHILD = "noOfChild";
	public static final String NO_OF_CHILD_IN_PRIMARY_SCHOOL = "noOfChildPS";
	public static final String NO_OF_CHILD_IN_SECONDARY_SCHOOL = "noOfChildSS";
	public static final String FAMILY_NAME = "fmlyName";
	public static final String FAMILY_REALTION = "rel";
	public static final String FAMILY_AGE = "fmlyAge";
	public static final String FAMILY_EDUCATION = "ffedu";
	public static final String FAMILY_HEAD = "hof";
	public static final String FAMILY_ACTIVITY = "fmlyActivity";
	public static final String FAMILY_GENDER = "fmlyGender";
	public static final String FAMILY_WAGE_EARNER = "we";
	public static final String FAMILY_MEMBERS = "fmlyMembers";
	public static final String FARMER_ECONOMIC_DETAILS = "ecnmyDetails";
	public static final String FARMER_HOUSE_OWNERSHIP = "hop";
	public static final String FARMER_HOUSE_TYPE = "ht";
	public static final String FARMER_HOUSE_TYPE_OTHER = "oht";
	public static final String FARMER_ANNUAL_INCOME = "annualIncome";
	public static final String IS_SAME_AS_FARMER_ADDRESS = "isSameAddress";
	public static final String LAND_MEASUREMENT = "lndMeasurement";
	public static final String FARM_AREA = "farmArea";
	public static final String FARM_PRODUCTIVE_AREA = "productiveArea";
	public static final String FARM_CONSERVATION_AREA = "conservationArea";
	public static final String WATER_BODIES_COUNT = "waterCount";
	public static final String FULL_TIME_WORKERS_COUNT = "ftEmpCt";
	public static final String PART_TIME_WORKERS_COUNT = "ptEmpCt";
	public static final String SEASONAL_WORKERS_COUNT = "ssEmpCt";
	public static final String FARM_OWNED = "fo";
	public static final String AREA_UNDER_IRRIGATION = "areaUndrIrgn";
	public static final String FARM_IRRIGATION = "fi";
	public static final String IRRIGATION_SOURCE = "ir";
	public static final String IRRIGATION_METHOD = "im";
	public static final String SOIL_TYPE = "st";
	public static final String SOIL_FERTILITY = "sf";
	public static final String FARM_INVENTORIES = "frmInvnts";
	public static final String FARM_INVENTORY_ITEM = "fii";
	public static final String FARM_INVENTORY_ITEM_COUNT = "invntItmCnt";
	public static final String FARM_ANIMALS = "frmAnmls";
	public static final String FARM_ANIMAL = "fa";
	public static final String FARM_ANIMAL_COUNT = "frmAnmlCnt";
	public static final String FARM_ANIMAL_FEED_USED = "fu";
	public static final String FARM_ANIMAL_HOUSE = "ah";
	public static final String FARM_ANIMAL_PRODUCTION = "frmAnmlProd";
	public static final String FARM_ANIMAL_ESTIMATED_OUTPUT = "frmAnmlEstmtdOp";
	public static final String CLIENT_OR_CERTIFICATION_CATEGORY = "key1";
	public static final String CLIENT_PROJECT_OR_CERTIFICATION_STANDARD = "key2";
	public static final String CHEMICAL_APPLICATION_LAST_DATE = "chemLasDate";
	public static final String BEGIN_OF_CONVERSION = "begOfCon";
	public static final String INTERNAL_INSPECTION_DATE = "IntInsDate";
	public static final String INTERNAL_INSPECTOR_NAME = "IntInsName";
	public static final String SURVEY_NUMBER = "surNo";
	public static final String LAND_UNDER_ICS_STATUS = "ics";
	public static final String PASTURE_LAND = "PasLand";
	public static final String CONVENTIONAL_LAND = "conLand";
	public static final String CONVENTIONAL_CROP = "conCrop";
	public static final String CONVENTIONAL_ESTIMATED_YIELD = "conEstYield";
	public static final String NON_CONFIRMTY = "nc";
	public static final String CROP_SEASON = "cs";
	public static final String CROP_CATEGORY = "cc";
	public static final String HARVEST_LIST = "harvestList";
	public static final String HARVEST_FARM_CROP_CODE_REF = "htfcCode";
	public static final String HARVEST_DATE = "harvestDate";
	public static final String HARVEST_QUANTITY = "harvestQty";
	public static final String HARVEST_AMOUNT = "harvestAmt";
	public static final String BUYER_NAME = "buyerName";
	public static final String CROP_PHOTO = "crpPhoto";
	public static final String FARM_ADDRESS = "farmAddress";
	public static final String INSPECTION_TYPE = "key3";
	public static final String ICS_STATUS = "key4";
	public static final String CLIENT_NAME = "cName";
	public static final String PROJECTS = "projects";
	public static final String CLIENTS = "clients";
	public static final String CLIENT_INSPECTION_TYPE = "it";
	public static final String GENDER = "gender";
	public static final String CUSTOMER_PROJECT_REVISION_NO = "clientRevNo";
	public static final String AGENT_REVISION_NO = "fsRevNo";
	public static final String INTERESRT_RATE_APPLICAPLE = "iRateA";
	public static final String RATE_OF_INTEREST = "roi";
	public static final String EFFECTIVE_FROM = "eFrom";
	public static final String IS_APPLICAPLE_EX_FARMER = "isAExF";
	public static final String PREVIOUS_INTEREST_RATE = "preIR";
	public static final String IS_QR_CODE_SEARCH_REQUIRED = "isQrs";
	public static final String REMOTE_APK_VERSION = "rApkV";
	public static final String REMOTE_CONFIG_VERSION = "rConfV";
	public static final String REMOTE_DB_VERSION = "rDbV";
	public static final String AREA_CAPTURE_MODE = "acMod";
	public static final String GEO_FENCING_FLAG = "gFReq";
	public static final String GEO_FENCING_RADIUS_MT = "gRad";
	public static final String FARMER_CERTIFIED = "isCertFarmer";
	public static final String FARMER_VERIFIED = "isVrfamer";
	public static final String FARM_VERIFIED = "isVrf";
	public static final String UTZ_STATUS = "utzStatus";

	/** Peridic Inspection **/

	public static final String PERIODIC_INSPECTION_TYPE = "insTyp";
	public static final String PERIODIC_PURPOSE = "purpos";
	public static final String PERIODIC_REMARKS = "rmks";
	public static final String FARMER_OR_FS_VOICE = "fmrVce";
	public static final String TIME_OF_INSPECTION = "tmeIns";
	public static final String SURVIVAL = "surv";
	public static final String AVERAGE_HEIGHT = "avgHt";
	public static final String AVERAGE_GIRTH = "avgGr";
	public static final String CURRENT_STATUS_OF_GROWTH = "csg";
	public static final String ACTIVITIES_CARRIED_OUT = "acpv";
	public static final String NO_OF_PLANTS_REPLANNED = "nopr";
	public static final String GAP_PLANTING_DATE = "dtGp";
	public static final String INTERPLOUGHING_WITH = "intpw";
	public static final String TYPE_OF_MANURE_APPLIED = "typMnr";
	public static final String OTHER_TYPE_OF_MANURE_APPLIED = "ottypMnr";
	public static final String MANURE_QUANTITY_APPLIED = "mnrQty";
	public static final String TYPE_OF_FERTILIZER_APPLIED = "typFer";
	public static final String OTHER_TYPE_OF_FERTILIZER_APPLIED = "ottypFer";
	public static final String FERTILIZER_QUANTITY_APPLIED = "ferQty";
	public static final String PEST_PROBLEM_NOTICED = "pstPbm";
	public static final String NAME_OF_PEST = "nmePst";
	public static final String OTHER_NAME_OF_PEST = "otnmePst";
	public static final String PEST_SYMPTOM = "pstSym";
	public static final String OTHER_PEST_SYMPTOM = "otpstSym";
	public static final String PEST_INFESTATION_ABOUT_ETL = "wpETL";
	public static final String NAME_OF_PESTICIDE_RECOMMENDED = "recPst";
	public static final String PESTICIDE_QUALITY_APPLIED = "pstQty";
	public static final String OTHER_NAME_OF_PESTICIDE_RECOMMENTED = "otRecPst";
	public static final String DATE_OF_PESTICIDE_APPLICATION = "dtPstApl";
	public static final String PEST_PROBLEM_SOLVED = "wpps";
	public static final String DISEASE_PROBLEM_NOTICED = "dpn";
	public static final String NAME_OF_DISEASE = "nmeDis";
	public static final String OTHER_NAME_OF_DISEASE = "otnmeDis";
	public static final String DISEASE_SYMPTOM = "disSym";
	public static final String OTHER_DISEASE_SYMPTOM = "otdisSym";
	public static final String DISEASE_INFESTATION_ABOUT_ETL = "wdETL";
	public static final String NAME_OF_FUNGISIDE_RECOMMENDED = "nmeFng";
	public static final String FUNGICIDE_QUANTITY_APPLIED = "fngQty";
	public static final String OTHER_NAME_OF_FUNGICIDE_RECOMMENTED = "otNmeFng";
	public static final String DATE_OF_FUNGICIDE_APPLICATION = "dtFntApl";
	public static final String DISEASE_PROBLEM_SOLVED = "wdps";
	public static final String FARMER_OPINION_ABOUT_SERVICE = "fmrOpn";
	public static final String IS_INTER_CROP = "isiCrp";
	public static final String NAME_OF_CROP = "nmeCrp";
	public static final String YIELD_OBTAINED_MT = "yldObt";
	public static final String EXPENDITURE_INCURED = "expInc";
	public static final String INCOME_GENERATED = "incGen";
	public static final String NET_PROFIT_OR_LOSS = "netPL";
	public static final String FSC_FM_STANDARDS = "isFSCFM";
	public static final String FSC_COC_STANDARDS = "isFSCCOC";
	public static final String FSC_GROUP_CERTIFICATE_STANDARD = "isFSCGCS";
	public static final String TRAINING_ATTENDED = "trnATn";
	public static final String ACCIDENT_WITH_FMU = "isAcFMU";
	public static final String AGE = "age";
	public static final String PERIODIC_NO_OF_ROOT_TRAINERS_RETURNED = "noRtrs";
	public static final String IS_FERTILIZER_APPLIED = "isFetAp";
	public static final String IS_SAFETY_DISPOSAL = "isFsfty";
	public static final String IS_SOLD = "isSld";
	public static final String MONTH_OF_FERTILIZER_APPLIED = "mntFer";
	public static final String MONTH_OF_PESTICIDE_APPLIECTION = "dtPstApl";
	public static final String MONTH_OF_FUNGICIDE_APPLICATION = "dtFntApl";
	public static final String CHEMICAL_NAME = "cheNme";
	public static final String INSPECTION_DATE = "insDt";

	public static final String PHOTO_LIST = "phLst";
	public static final String PHOTO_CAPTURE_TIME = "pcTme";
	public static final String PHOTO_LATITUDE = "lat";
	public static final String PHOTO_LONGITUDE = "lon";
	public static final String PR_FARM_ID = "frmId";
	public static final String CROP_ROTATION = "cropRot";
	public static final String TEMP = "temperature";
	public static final String RAIN = "rain";
	public static final String WIND_SPEED = "windSpeed";
	public static final String HUMIDITY = "humidity";

	public static final String PR_CROP_CODE = "crpID";

	/** Bank Information **/
	public static final String BANK_INFORMATION = "bnkLst";
	public static final String BANK_NAME = "bnkNam";
	public static final String ACCOUNT_NUMBER = "bAcNo";
	public static final String BRANCH_DETAILS = "brchDets";
	public static final String SORT_CODE = "srtCod";
	public static final String SWIFT_CODE = "swftCod";
	public static final String PROFESSION = "prof";
	public static final String OTHER_PROFESSION = "othProf";
	public static final String OTHER_BANK_INFO = "otrbAcType";

	public static final String PROCUREMENT_PRODUCT_VARIETY_CODE = "ppVarCode";
	public static final String PROCUREMENT_PRODUCT_VARIETY_NAME = "ppVarName";

	public static final String PROCUREMENT_PRODUCT_GRADE_CODE = "ppGraCode";
	public static final String PROCUREMENT_PRODUCT_GRADE_NAME = "ppGraName";
	public static final String PROCUREMENT_PRODUCT_GRADE_PRICE = "ppGraPrice";

	public static final String PROCUREMENT_PRODUCT_GRADE_LIST = "grdLst";
	public static final String PROCUREMENT_PRODUCT_VARIETY_LIST = "vrtLst";

	/** Cash Received & Settlement Trxn */
	public static final String CASH_RECEIVED_DATE = "crDate";
	public static final String CASH_RECEIVED_MODE = "crmod";
	public static final String AMOUNT = "amt";
	public static final String CASH_SETTLEMENT_DATE = "csDate";
	public static final String CASH_SETTLEMENT_MODE = "csmod";
	public static final String PHOTO_PROOF = "proof";
	public static final String COMPANY_NAME = "comNam";
	public static final String PERSON_NAME = "prsNam";

	/* Farm Crop Harvest */

	public static final String CROP_HARVEST_DATE = "harvDt";
	public static final String CROP_FARMER_ID = "farmerId";
	public static final String CROP_FARM_ID = "farmId";
	public static final String TOTAL = "tot";
	public static final String CROP_RECEIPT_NO = "recNo";
	public static final String CROP_LIST = "cropLst";
	public static final String CROP_TYPE = "cropType";
	public static final String CROP_GRADE_CODE = "gCode";
	public static final String CROP_QUANTITY = "qty";
	public static final String CROP_PRICE = "price";
	public static final String CROP_SUB_TOTAL = "subTotal";
	public static final String CROP_DETAIL = "crops";
	public static final String CROP_ID = "cropId";
	public static final String FARM_CROP_ID = "fcropId";
	public static final String MAX_PHIDATES = "maxPhiDate";
	public static final String CROP_VARIETY_ID = "varId";

	public static final String BUYER_DOWNLOAD_REVISION_NO = "byrRevNo";

	public static final String BUYER = "byrList";
	public static final String BUYER_ID = "byrId";
	public static final String CUSTOMER_NAME = "byrName";

	public static final String MANURE_APP = "manr";
	public static final String MANURE_APP_QTY = "manrQty";

	/** Product Sale transaction */
	public static final String SALES_DATE = "saleDt";
	public static final String SALES_BUYER = "buyer";
	public static final String TRANSPORTER_NAME = "transp";
	public static final String VEHICLE_NUMBER = "vehNum";
	public static final String INVOICE_DETAIL = "InvDet";
	public static final String BATCH_NO = "batchNo";
	public static final String FERTILIZER_APP = "fertApp";
	public static final String PEST_APP = "pestApp";
	public static final String FERT = "fertType";
	public static final String FERT_QTY = "fertQty";
	public static final String PEST_FUNGISIDE = "pestType";
	public static final String PEST_FUNGISIDE_QTY = "pestQty";
	public static final String PAYEMENT = "payMnt";
	public static final String MANUR_APP = "manureApp";
	public static final String MANURE_FUNGISIDE = "manureType";
	public static final String MANURE_FUNGISIDE_QTY = "manureQty";
	public static final String COMPLETED = "completed";

	/** Cost of Cultivation */

	public static final String EXPENSE_TYPE = "expType";
	public static final String PLOUGHING = "plou";
	public static final String RIDGE_FURROW = "ridg";
	public static final String LAND_TOT_COST = "lndTot";
	public static final String SEED_COST = "seedCost";
	public static final String TREAT = "treat";
	public static final String MEN_COST_SOWING = "menCostsowing";

	public static final String WOMEN_COST_SOWING = "wmnCostsowing";
	public static final String TOTAL_SOWING = "totSowing";
	public static final String MEN_COST_GAP = "menCostGap";
	public static final String WOMEN_COST_GAP = "wmncostGap";
	public static final String TOTAL_GAP = "totGap";
	public static final String MEN_COST_WEED = "menCostWeed";
	public static final String WOMEN_COST_WEED = "wmncostWeed";

	public static final String TOTAL_WEED = "totweed";
	public static final String MEN_COST_CULTURE = "menCostOtrCulture";
	public static final String WOMEN_COST_CULTURE = "wmnCostOtrculture";
	public static final String TOTAL_CULTURE = "tototrCulture";
	public static final String MEN_COST_IRRIG = "menCostIrrig";
	public static final String WOMEN_COST_IRRIG = "wmncostIrrig";
	public static final String TOTAL_IRRIG = "totIrrig";

	public static final String MEN_COST_FERTI = "menCostFerti";
	public static final String WOMEN_COST_FERTI = "wmscostFerti";
	public static final String TOTAL_FERTI = "totFerti";
	public static final String MEN_COST_PESTI = "menCostPesti";
	public static final String WOMEN_COST_PESTI = "wmncostPesti";
	public static final String TOTAL_PESTI = "totPesti";
	public static final String MEN_COST_HARV = "menCostHarv";

	public static final String WOMEN_COST_HARV = "wmncostHarv";
	public static final String TOTAL_HARV = "totHarv";
	public static final String TOTAL_OTR_EXP = "totOtrExp";

	public static final String PACKING_MATERIAL = "pckMat";
	public static final String TRANSPORT = "transp";
	public static final String MISCELLANEOUS = "miscel";
	public static final String COST_OF_FERTILIZER = "fertCost";

	public static final String TOTAL_EXP_AMT = "totExpAmt";
	public static final String PESTICIDE_COST = "pestCost";

	public static final String ANIMAL_HOUSE = "ah";
	public static final String COC_DATE = "cocDt";
	public static final String TXN_TYPE = "txnType";
	public static final String INTERCROP_INCOME = "intrcrpInc";
	public static final String OTHERSOURCES_INCOME = "othInc";
	public static final String AGRI_INCOME = "agriInc";
	public static final String STAPLE_LENGTH_MAIN = "staLen";
	public static final String SEED_USED_MAIN = "sdUsd";
	public static final String SEED_COST_MAIN = "sdCost";
	public static final String EST_YIELD = "estYld";
	public static final String SEASON = "season";

	public static final String CATALOGUE_DOWNLOAD_REVISION_NO = "catRevNo";
	public static final String CATALOGUES = "catList";
	public static final String CATLOGUE_CODE = "catId";
	public static final String CATLOGUE_NAME = "catName";

	public static final String CATLOGUE_TYPE = "catType";
	public static final String FARM_CROP_TYPE = "type";
	public static final String FARM_CROP_CATEGORY = "cropType";
	public static final String FARM_CROP_CROP_ID = "cropId";
	public static final String CULTIVATION_RECEIPT_NO = "recNo";
	public static final String CULTIVATION_FARM_ID = "farmId";
	public static final String OTHER_VEHICLE = "othrVecle";
	public static final String CONSUMER_ELECTRONICS_OTHER = "othrElectro";

	public static final String COST_OF_SEED = "seedCost";
	public static final String SOWING_TREAT = "treat";
	public static final String LABOURCOST_MEN = "menCost";
	public static final String LABOURCOST_WOMEN = "wmnCost";

	public static final String ESTIMATION_HARVEST_DAYS = "estDays";

	public static final String SUPPLIER_TYPE = "supTypId";

	public static final String FPOGROUP = "fpo";

	public static final String CURR_SEASON_CODE = "cSeasonCode";

	public static final String MEN_COST_MANURE = "menCostFym";
	public static final String WOMEN_COST_MANURE = "wmncostFym";
	public static final String TOTAL_MANURE = "totFym";
	public static final String MANURE_COST = "fymCost";

	public static final String LABOUR_HIRE = "totLbrHire";
	public static final String SOIL_PREPARE = "soilPrepare";
	public static final String LAND_LABOUR_COST= "soilLab";
	public static final String SOWING_LABOUR_COST= "seedLab";
	public static final String GAP_LABOUR_COST= "gapLab";
	public static final String WEED_LABOUR_COST= "weedLab";
	public static final String CULTURE_LABOUR_COST= "inputLab";
	public static final String IRRI_LABOUR_COST= "irriLab";
	public static final String FERTI_LABOUR_COST= "bioFertLab";
	public static final String PEST_LABOUR_COST= "bioPestLab";
	public static final String HARVEST_LABOUR_COST= "harvLab";
	public static final String MANURE_LABOUR_COST= "totManLab";
	public static final String TRANSPORST_COST= "othGin";
	public static final String FUEL_COST= "othFuel";


	// chetna variables
	public static final String ICS_UNIT_NO = "icsUnit";
	public static final String ICS_TRACENET_REG_NO = "icsTrac";
	public static final String FARMER_CODE_BYICS = "icsFrCde";
	public static final String FARMER_CODE_BYTRACENET = "icsFrTrac";
	public static final String CATEGORY = "cate";
	public static final String STATUS = "status";
	public static final String IS_BENEFICIARY_INANY_GOV_SCHEME = "isBnef";
	public static final String NAME_OF_THE_STRING = "schNme";
	public static final String OTHER_LOAN_TAKEN_FROM = "othLoanFrm";
	public static final String LOAN_AMOUNT = "loanAmt";
	public static final String LOAN_PURPOSE = "loanPurp";
	public static final String LOAN_INTREST_RATE = "loanInt";
	public static final String LOAN_SECURITY = "loanSecu";

	public static final String SEED_TREATMENT_DETAILS = "seedTreat";
	public static final String OTHER_SEED_TREATMENT_DETAILS = "othSeedTreat";
	public static final String RISK_ASSESMENT = "riskAsses";
	public static final String RISK_BUFFER_ZONE_DISTANCE = "riskBuf";
	public static final String LOANS_TAKEN_FROM = "loanFrm";

	public static final String STORAGE_IN = "stor";
	public static final String PACKED_IN = "pack";
	public static final String STORAGE_IN_OTHER = "storOther";
	public static final String PACKED_IN_OTHER = "packOther";
	public static final int FARM_CATALOGUE_TYPE_SEED = 7;

	public static final String PAY_MODE = "pMode";
	public static final String REMARK = "remrks";
	public static final String PLAT = "pLat";
	public static final String PLON = "pLon";

	public static final String IS_TOILET_AVAILABLE = "toiAvl";
	public static final String TOILET_AVAILABLE = "toiUse";
	public static final String COOKING_FUEL = "cookFul";
	public static final String CO_OPERATIVE = "cop";
	public static final String HEAD_OF_FAMILY = "hof";
	public static final String GOVT_DEP = "govDept";
	public static final String HEALTH_INSURANCE = "hIns";
	public static final String LIFE_INSURANCE_AMT = "lInsAmt";
	public static final String HEALTH_INSURANCE_AMT = "hInsAmt";
	public static final String CROP_INSURANCE_VALUE = "cropId";
	public static final String AREA_INSURED = "insAcr";
	public static final String LOAN_PURPOSE_OTHER = "loanPurpOth";
	public static final String LOAN_TYPE = "loanIntTyp";
	public static final String LOAN_SEC_OTHER = "loanSecuOth";
	public static final String LAND_TOPOGRAPHY = "ldTopo";
	public static final String SOIL_TESTED = "solTest";
	public static final String SOIL_TEST_REPORT = "srPhoto";
	public static final String FARMER_LIFE_INSURANCE = "lIns";

	public static final String ID_PROOF = "idp";
	public static final String ID_PROOF_VALUE = "idpValue";
	public static final String ID_PROOF_OTHER = "idpOth";
	public static final String CASTE_TRIBE_NAME = "casteNm";
	public static final String OTHER_COOKING_FUEL_USED = "cookFulOth";
	public static final String LOAN_REPAYMENT_AMOUNT = "loanRepAmt";
	public static final String LOAN_REPAYMENT_DATE = "loanRepDt";
	public static final String TRADER_CODE = "trd";
	/* APMAS FARMER ENROLLMENT */
	public static final String HHID = "hhid";
	public static final String ADHAAR = "adhar";
	public static final String CASTE = "caste";
	public static final String RELIGION = "religion";
	public static final String RELIGION_OTHER = "othReligion";
	public static final String TYPE_OF_FAMILY = "tof";
	// public static final String HOUSEHOLD_LANDHOLDING="hhlh";
	public static final String HOUSEHOLD_LANDHOLDING_WET = "hhlhWet";
	public static final String HOUSEHOLD_LANDHOLDING_DRY = "hhlhDry";
	public static final String PRIMARY_HOUSEHOLD = "primaryhhoc";
	public static final String PRIMARY_HOUSEHOLD_OTHER = "othPrimary";
	public static final String SECONDARY_HOUSEHOLD = "secondary";
	public static final String SECONDARY_HOUSEHOLD_OTHER = "othSecondary";
	public static final String FAMILY_MEMBER_CBO = "flyMemCBO";
	public static final String FAMILY_MEMBER_CBO_OTHER = "OthFlyMemCBO";

	public static final String AGRICULTURAL_ACTIVITIES_LIST = "siAgr";
	public static final String SOURCE_OF_INCOME_AGRI_ACTIVITIES = "siAgrNme";
	public static final String INCOME_PER_ANNUM_AGRI_ACTIVITIES = "siAgrAmt";

	public static final String HORTICULTURE_LIST = "siHot";
	public static final String SOURCE_OF_INCOME_HORICULTURE = "siHotNme";
	public static final String INCOME_PER_ANNUM_HORICULTURE = "siHotAmt";

	public static final String ALLIED_SECTOR_LIST = "siAli";
	public static final String SOURCE_OF_INCOME_ALLIED_SECTOR = "siAliId";
	public static final String INCOME_PER_ANNUM_ALLIED_SECTOR = "siAliAmt";

	public static final String EMPLOYMENT_LIST = "siEmp";
	public static final String SOURCE_OF_INCOME_EMPLOYMENT = "siEmpId";
	public static final String INCOME_PER_ANNUM_EMPLOYMENT = "siEmpAmt";

	public static final String OTHERS_LIST = "siOth";
	public static final String SOURCE_OF_INCOME_OTHERS = "siOthId";
	public static final String INCOME_PER_ANNUM_OTHERS = "siOthAmt";

	public static final String TOTAL_INCOME_PER_ANNUM = "totPerAnn";
	public static final String OPINION_OF_INVESTIGATOR = "opnInvest";
	public static final String POSITION_IN_THE_GROUP = "posGrp";
	public static final String IFS_PRACTICES = "IFSPrct";
	public static final String IFS_PRACTICES_OTHERS = "OthIFSPrct";
	public static final String KITCHEN_GARDEN_VEGETABLES = "kitchGardVeg";
	public static final String KITCHEN_GARDEN_USES = "kitchGardUse";
	public static final String BACK_YARD_POULTRY = "bckYrdPoul";
	public static final String PROGRAMS_OR_SCHEMES_AVAILED_LIST = "prgSchLst";
	public static final String BENEFIT_DETAILS = "benefit";
	public static final String NO_OR_KGS = "noKg";
	public static final String SCHEMES_OR_DEPARTMENT_NAMES = "deptNme";
	public static final String RECEIVED_ON_DATE = "receiveDt"; // format
																// yyyyMMdd
	public static final String AMT_RECEIVED_RS = "amtReceive";
	public static final String OWN_CONTRIBUTION_RS = "ownContri";
	public static final String ADOPTED_IFS_PRACTICES_LIST = "adopIFSLst";
	public static final String SEASON_CODE_ADP_IFS_PRAC = "SesCode";
	public static final String IRRIGATED_TOTAL_LAND_IN_ACRES = "irrTotLnd";
	public static final String IRRIGATED_LAND_IFS_PRACTICES_ADOPTING = "irrIFSAdb";
	public static final String RAINFED_TOTAL_LAND_IN_ACRES = "rainTotLnd";
	public static final String RAINFED_LAND_IFS_PRACTICES_ADOPTING = "rainIFSAdb";
	public static final String SOIL_CONSERVATION = "soilCons";
	public static final String SOIL_CONSERVATION_OTHERS = "OthSoilCons";
	public static final String WATER_CONSERVATION = "waterCons";
	public static final String WATER_CONSERVATION_OTHERS = "OthWaterCons";
	public static final String PLANTS_OR_TREES_LIST = "plantTreeLst";
	public static final String PLANTS_OR_TREES_NAME = "ptName";
	public static final String NO_OF_PLANTS_PLANTED = "NoOfPT";
	public static final String NO_OF_LIVE_PLANTS = "NoOfLivPT";
	public static final String SERVICE_CENTRES = "service";
	public static final String SERVICE_CENTRES_OTHERS = "OthService";
	public static final String SOIL_TESTING = "soilTest";
	public static final String SOIL_TESTING_OFFICERS_SUGGESTIONS_LIST = "soilTstSuggLst";
	public static final String SUGGESTIONS_FROM_OFFICERS = "sugg";
	public static final String ACTION_TAKEN = "actTkn";
	public static final String TRAININGS_AND_PROGRAMS = "trngPrg";

	public static final String GROUP_TYPE_ID = "gpTypId";
	public static final String GROUP_TYPE_NAME = "gpTypNme";
	public static final String SANGHAM_LIST = "grpLst";

	/* SIERRA NOP CERTIFICATION */

	public static final String ACRES_OWNED = "acreOwned";
	public static final String ACRES_FARMED = "acreFarmed";
	public static final String ACRES_ORGANIC = "acreOrganic";
	public static final String ACRES_TRANSITION = "acreTransit";
	public static final String ELIGIBLE_ACRES_NEXT_CERTIFICATION = "acreEligble";
	public static final String ACRES_REQ_ORGANIC_INSPECTION = "acreRequested";
	public static final String CURRENT_SEASON_CODE_LOGIN = "currentSeasonCode";

	/** Farm Crops Download */

	public static final String CULTI_TYPE = "cultTyp";
	public static final String CULTIVATION_AREA = "cultArea";
	public static final String SOW_DATE = "sowDt";
	public static final String ESTIMATE_HARVEST_DATE = "estHarvDt";

	/* FARM */
	public static final String FARM_OWNED_OTHER = "othFo";
	public static final String COW_NO = "cowNo";
	public static final String HOUSING_TYPE = "hous";
	public static final String COW_SPACE = "space";
	public static final String NO_OF_COFFEE_PLANTS = "numCofTrees";
	public static final String FARM_PHOTO_ID = "docIdNo";
	public static final String VERMI_UNIT = "vermiUnit";

	/* Farm Inspection */
	public static final String LAND_PREP_COMPLETED = "ldPreCom";
	public static final String CHEMICAL_SPRAY = "noCheSpry";
	public static final String MONO_IMIDA = "noMono";
	public static final String SINGLE_SPRAY_COCKTAIL = "sgleSpry";
	public static final String REPETITION_PEST = "noRepet";
	public static final String NITROGENOUS_FERT = "noNitro";
	public static final String CROP_SPACE_LAST_YEAR = "crpSpLast";
	public static final String CROP_SPACE_CURRENT_YEAR = "crpSpCurr";
	public static final String WEEDING = "weed";
	public static final String PICKING = "pick";
	public static final String CROP_PROTECTION_PRACTICE = "cropProt";

	/** Farmer */
	public static final String ADULT_COUNT_FEMALE = "adCntFe";
	public static final String CHILD_COUNT_FEMALE = "chCntFe";
	public static final String SCHOOL_COUNT_FEMALE = "schChCntFe";

	/** COW ENROLLMENT */
	public static final String ELITE_TYPE = "elitTyp";
	public static final String RESEARCH_STATION_ID = "resStatId";
	public static final String COW_ID = "cowId";
	public static final String TAG_NUM = "tagNum";
	public static final String SIRE_ID = "sirId";
	public static final String DAM_ID = "damId";
	public static final String COW_GENO_TYPE = "geno";
	public static final String HISTOY = "hist";
	public static final String LACTATION_NUMBER = "lactNum";
	public static final String CALF_LIST = "calfLst";
	public static final String SERVICE_DATE = "servDt";
	public static final String CALVING_DATE = "calvDt";
	public static final String BULL_ID = "bullId";
	public static final String CALF_ID = "calfId";
	public static final String WEIGTH = "weig";
	public static final String INTERVAL = "inter";
	public static final String DELIVERY = "deliv";

	/** COW INSPECTION */

	public static final String INSPECTION_NO = "inspNum";
	public static final String CURRENT_DATE = "currInsDt";
	public static final String INTERVAL_DAYS = "insInter";
	public static final String MILK_MORNG_DAY = "morMilk";
	public static final String MILK_EVNG_DAY = "eveMilk";
	public static final String TOT_MILK_DAY = "totMilk";
	public static final String TOT_MILK_PERIOD = "totMilkRes";
	public static final String AUDIO = "aud";
	public static final String FEED_TYPE = "feed";
	public static final String AMT_FEED_PERIOD = "amtfeed";
	public static final String INFES_PARASITES = "parasi";
	public static final String DEWORNMING_PLACE = "dewarm";
	public static final String MEDICINE_NAME = "mediDewarm";
	public static final String DISEASE_NOTICE = "dise";
	public static final String DISEASE_NAME = "nmeDise";
	public static final String DISEASE_SERVICES = "disServ";
	public static final String DISEASE_MEDICINE = "disMedi";
	public static final String HEALTH_PROBLEM = "health";
	public static final String CORRECTIVE_MEASURE = "conMes";
	public static final String VACCINATION_PLACE = "vacci";
	public static final String VACCINATION_DATE = "dtVacci";
	public static final String VACCINATION_NAME = "nmeVacci";
	public static final String COMMENTS = "comme";
	public static final String HEALTH_SERVICES = "serv";
	public static final String HEALTH_MEDICINES = "mediuse";
	public static final String IS_MILKING_COW = "isMilkCow";

	/** COST OF FARMING **/

	public static final String COLLECTION_DATE = "ddDate";
	public static final String HOUSING_COST = "hous";
	public static final String FEED_COST = "feed";
	public static final String TREATEMENT_COST = "treat";
	public static final String OTHER_COST = "othCost";
	public static final String TOTAL_EXPENCE = "totExp";
	public static final String INCOME_MILK = "milk";
	public static final String INCOME_OTHER = "incOth";
	public static final String TOTAL_INCOME = "totInc";
	public static final String CROP_CODE = "cropId";

	public static final String CROP_GPS = "crpGps";
	public static final String CROP_LONGITUDE = "crpLon";
	public static final String CROP_LATITUDE = "crpLat";
	public static final String CROP_GRS_SNO = "crpGpsSno";

	public static final String AGRO_VERSION_DETAILS = "agroVersion";

	public static final String TRAINING_LIST = "trLists";
	public static final String TRAINING_NAME = "trName";
	public static final String TOPIC_LIST = "topicLists";
	public static final String CRITERIA_CATEHORY_CODE = "cccode";
	public static final String CRITERIA_CATEHORY_NAME = "ccname";
	public static final String CRITERIA_LIST = "criteria";
	public static final String CRITERIA_CODE = "ccode";
	public static final String CRITERIA_NAME = "cname";
	public static final String TRAINING_MATERIAL_LIST = "tmaterial";
	public static final String TRAINING_MATERIAL_CODE = "tmatcode";
	public static final String TRAINING_MATERIAL_NAME = "tmatname";
	public static final String TRAINING_METHOD_LIST = "tmethod";
	public static final String TRAINING_METHOD_CODE = "tmcode";
	public static final String TRAINING_METHOD_NAME = "tmname";
	public static final String TRAINING_OBSERVATION_LIST = "trobs";
	public static final String TRAINING_OBSERVATION_CODE = "obscode";
	public static final String TRAINING_OBSERVATION_NAME = "obsname";
	public static final String TRAINING_ASSISTANT_NAME = "trAssist";
	public static final String TIMETAKEN_FOR_TRAINING = "trTime";
	public static final String FFC = "ffc";

	public static final String CP_TYPE = "cpType";
	public static final String FIELD_ID = "fieldId";
	public static final String FIELD_NAME = "fieldName";
	public static final String FIELD_TYPE = "fieldType";
	public static final String IS_MANDATORY = "isRequired";

	public static final String MAX_LENGTH = "fieldMaxLen";
	public static final String DYNAMIC_FIELDS = "dyFields";
	public static final String DEPENDANCY_KEY = "depKey";
	public static final String ORDER = "order";

	public static final String SECTION = "secId";

	public static final String HEALTHASSES = "healthAsses";
	public static final String DISABILITYTYPE = "disabilityType";
	public static final String ORIGIN = "origin";
	public static final String REMARKZ = "remark";
	public static final String PFSNALCONSULTATION = "pfsnalConsultation";
	public static final String DETCONSULTATION = "detConsultation";
	public static final String CAREASSES = "careAsses";
	public static final String ACTIVITY = "activity";
	public static final String VALUE = "Value";
	public static final String HOMEDIFF = "homeDiff";
	public static final String WORKDIFF = "workDiff";
	public static final String COMDIFF = "comDiff";
	public static final String ASSISTIVEDEV = "assistiveDev";
	public static final String ASSISTIVEDEVNAME = "assistiveDevName";
	public static final String REQASSISTIVEDEV = "reqassistiveDev";
	public static final String HEALTHISSUE = "healthIssue";
	public static final String HEALTHISSUEDES = "healthIssueDes";

	public static final String SECTION_NAME = "secName";
	public static final String HAR_DATE = "preDt";

	public static final String PREFERENCE_OF_WORK = "prefWrk";

	public static final String IS_GRAMPANCHAYAT = "isGrampnchayat";
	public static final String ASSESS = "assemnt";
	public static final String PLACE_ASSES = "placeAssmnt";

	public static final String OBJECTIVE = "objective";

	public static final String FORM_FILLED_BY = "formFiled";

	/** PreHarvest **/
	public static final String PREHARVEST_DATE = "preDt";
	public static final String PREHARVEST_FARMER = "farmerId";
	public static final String PREHARVEST_FARM = "farmId";
	public static final String PREHARVEST_CROP = "crpId";
	public static final String PREHARVEST_AREA = "areaProd";

	public static final String EST_COTTON = "estCotton";
	public static final String EST_COTTON_YIELD = "estcottonYld";
	public static final String PRE_SEASON_CODE = "cSeasonCode";
	public static final String PRE_VILLAGE = "villId";
	public static final String PRE_ICS = "ics";

	/** Canda FarmerVerification **/

	public static final String FARM_MAIN_CROP = "fmainCrop";
	public static final String LH_INSURANCE = "insure";
	public static final String CRP_INSU = "cropInsure";
	public static final String BANK_ACC = "bankAcc";
	public static final String LOAN_TAKEN_LENDER = "loanTak";

	public static final String POST_HARVEST_INTER = "interType";
	public static final String INTER_ACRES = "interAcres";
	public static final String INTER_CROP = "interCropharvest";
	public static final String GROSS_INCOME = "interGrossIncm";

	/** COC Canda **/

	public static final String COTTON_QTY = "ctQty";
	public static final String COTTON_SALE_PR = "salePz";
	public static final String INCOME_CT_SALE = "ctSale";

	public static final Object AGRI_IMPLEMENT = "agriImplen";

	// Survey Master Download Properties
	public static final String SURVEY_MASTER_DOWNLOAD_REVISION_NO = "SurMasRevNo";
	public static final String SURVEY_MASTER_ID = "smCode";
	public static final String SURVEY_MASTER_NAME = "smName";
	public static final String SURVEY_MASTER_VALID_FROM = "smVaF";
	public static final String SURVEY_MASTER_VALID_TO = "smVaT";
	public static final String SURVEY_MASTERLIST = "smLst";
	public static final String SURVEY_MASTER_QUESTON_LIST = "smQLst";
	public static final String SURVEY_MASTER_QUESTION_ORDER = "smqOrd";
	public static final String SURVEY_MASTER_QUESTION_ID = "smqCode";
	public static final String SURVEY_MASTER_TYPE = "srvTyp";
	public static final String SURVEY_MASTER_DESCRIPTION = "smDesc";
	public static final String SURVEY_MASTER_DOWNLOAD = "smDwnd";
	public static final String SURVEY_MASTER_REVISION_NO = "smRvNo";
	public static final String SURVEY_MASTER_REVISION = "surveyRevNo";
	public static final String SURVEY_SUBFORM = "subFrmQus";

	public static final String DATALEVEL_CODE = "dtlvCde";
	public static final String DATALEVEL_NAME = "dtlvName";
	public static final String DATALEVEL_LANG = "dlvLang";
	// survey master language list
	public static final String LANGUAGE_PREF_LIST = "lngPrefLst";
	public static final String LANGUAGE_CODE = "lCde";
	public static final String LANGUAGE_NAME = "lName";
	public static final String LANGUAGE_TYPE = "lType";
	public static final String LANGUAGE = "lang";
	public static final String LANGUAGE_INFOTEXT = "linfoTxt";
	public static final String SURVEY_TYPE = "surTyp";
	public static final String SURVEY_SECTION_CODE = "entCode";
	public static final String SURVEY_SECTION_NAME = "entName";
	public static final String SURVEY_TYPE_ID = "surType";
	public static final String ACTION_PLAN_ANSWERS = "actPlanCat";

	// chetna farm changes
	public static final String FARM_CERTIFICATION_YEAR = "fcertYear";
	public static final String SECTION_TYPE = "sectype";
	public static final String SECTION_ORDER = "secOrder";

	public static final String FARMER_TYPE = "irpType";
	public static final String IS_REGISTERED_FARMER = "isReg";
	public static final String IS_REGISTERED_SUPPLIER = "isRegSupplier";
	public static final String SUPPLIER_ID = "supplierId";
	public static final String FARMER_MOBILE_NUMBER = "farmerMobileNo";
	public static final String FARMER_VILLAGE_CODE = "frVillageCode";

	public static final String FIELDS_LIST = "fieldList";

	public static final String TXN_TYPE_ID = "txnTypeId";
	public static final String LISTS = "lists";

	public static final String LIST_ID = "listId";
	public static final String LIST_NAME = "listName";

	public static final String BEFORE_INSERT = "beforeInsert";
	public static final String AFTER_INSERT = "afterInsert";
	
	public static final String FIELD_AFTER_INSERT="afterFld";
	public static final String FIELD_BEFORE_INSERT="beforeFld";
	

	public static final String COMPONENT_TYPE = "compoType";
	public static final String COMPONENT_LABEL = "compoLabel";
	public static final String COMPONENT_ID = "compoId";
	public static final String TEXTBOX_TYPE = "textType";
	public static final String DATA_FORMAT = "dataFormat";
	public static final String VALID_TYPE = "validType";
	public static final String MAXLENGTH = "maxLength";
	public static final String MIN_LENGTH = "minLength";
	public static final String IS_DEPENDENCY = "isDependency";
	public static final String DEPENDENT_FIELD = "dependentField";
	public static final String PARENT_DEPEND = "parentDepend";
	public static final String LIST_METHOD_NAME = "listMethodName";
	public static final String BLOCK_ID = "blockId";

	public static final String FARMER_FAMILY = "fmlyLst";
	public static final String FAMILY_MEMBER_NAME = "nmeFmly";
	public static final String FAMILY_MEMBER_GENDER = "gendFmly";
	public static final String FAMILY_MEMBER_AGE = "ageFmly";
	public static final String FAMILY_MEMBER_RELATION = "relFmly";
	public static final String FAMILY_MEMBER_EDUCATION = "eduFmly";
	public static final String FAMILY_MEMBER_DISABILITY = "childFmly";
	public static final String FAMILY_MEMBER_DISABILITY_DETAIL = "childDis";
	public static final String FARMILY_MEMBER_MARITAL = "marFmly";
	public static final String FARMILY_MEMBER_EDU_STATUS = "eduBelowFmly";
	public static final String FARMILY_IS_HEAD = "hofmly";
	public static final String FORMULA_DEPENDENCY = "formulaDependency";

	public static final String MANURE_COLLECT = "estManure";
	public static final String URINE_COLLECT = "estUrine";

	public static final String WATER_HARVEST = "waterHarvest";
	public static final String AVG_STORAGE = "avgStorage";
	public static final String TREE_NAME = "treeName";
	public static final String SMARTPHONE = "smartCell";
	public static final String FIELD_VAL = "fieldVal";
	public static final String LIST_ITRATION = "listItration";
	public static final String QTY_COOKING_FUEL_PERMONTH = "qtyCookFuel";
	public static final String COST_COOKING_FUEL_PERMONTH = "costCookFuel";
	public static final String TXN_UNIQUE_ID = "txnUniqueId";
	public static final String PROCUREMENT_LIST = "procList";
	public static final String TXN_DATE = "txnDate";

	public static final String PREF_LIST = "prefList";

	/**
	 * Procurement Traceability
	 */
	public static final String EDITED_MSP = "pricePerUnit";
	public static final String MSP_RATE = "msp";
	public static final String MSP_PERCENTAGE = "pmsp";
	public static final String CPROCUREMENT_CENTER = "pfc";

	public static final String CPROCUREMENT_STRIP_TEST = "sTest";
	public static final String CPROCUREMENT_STRIP_PHOTO = "photo";
	public static final String CPROCUREMENT_TRASH = "trash";
	public static final String CPROCUREMENT_MOISTURE = "moisture";
	public static final String CPROCUREMENT_KOWDIKAPAS = "kowdiKapas";
	public static final String CPROCUREMENT_STAPLE_LENGTH = "stapleLength";
	public static final String CPROCUREMENT_YELLOW_COTTON = "yellowCotton";
	public static final String COOPERTAIVE_TYPE = "copTyp";

	public static final String FARM_DOWNLOAD_REVISION_NO = "farmRevNo";
	public static final String FARM_CROPS_DOWNLOAD_REVISION_NO = "fCropRevNo";
	

	// Wilmar
	public static final String TRADER = "trader";
	public static final String DISTANCE_PROCESS_UNIT = "distanceUnit";
	public static final String NO_OF_TREES = "noOfTrees";
	public static final String PROCESSING_ACTIVITY = "processAct";
	public static final String PRODUCTIVE_TREES = "prodTrees";
	public static final String F_PHOTO = "fPhoto";
	public static final String AFFECTED_TREES = "affTrees";

	public static final String ID = "ID";
	public static final String FARM_STATUS = "farmStatus";
	public static final String FARM_CROP_STATUS = "cropStatus";

	public static final String MANURE_USE = "manureUse";
	public static final String FERT_USE = "fertUse";
	public static final String PEST_USE = "pestUse";

	public static final String TXN_TYPE_ID_MASTER = "txnTypeIdMaster";
	// Farmer Enrollment
	public static final String FARMER_ICS_CODE = "icsCode";

	public static final String PMT_PHOTO_1 = "prPhoto1";
	public static final String PMT_PHOTO_2 = "prPhoto2";

	public static final String PMT_LAT_1 = "prLat1";
	public static final String PMT_LAT_2 = "prLat2";

	public static final String PMT_LON_1 = "prLon1";
	public static final String PMT_LON_2 = "prLon2";

	public static final String TRIP_PHOTO_1 = "tPhoto1";
	public static final String TRIP_PHOTO_2 = "tPhoto2";

	public static final String MENU_ID = "menuId";
	public static final String MENU_NAME = "menuLabel";
	public static final String ICON_CLASS = "iconClass";
	public static final String ENTITY = "entity";
	public static final String MENU_ORDER = "menuOrder";
	public static final String IS_SEASON = "seasonFlag";

	public static final String MENU_ICON_CLASS = "iconClass";
	 public static final String ICS_CD="icsCd";
	public static final String HEAP_CODE="heapCode";
	 
	 public static final String INSPECTION_STATUS = "inspStatus";
	 public static final String CONVERSION_STATUS = "convStatus";
	 public static final String CORECTIVE_PLAN = "corActPlan";
	 public static final String DYNAMIC_TXN_ID = "dynamicTxnId";
	 
	 public static final String AGENT_TYPE = "agentType";
	 public static final String BALE_COLLECTION="baleList";

	public static final String SP_PHOTO_1 = "spPhoto1";
	public static final String SP_PHOTO_2 = "spPhoto2";
	public static final String SP_LAT_1 = "spLat1";
	public static final String SP_LAT_2 = "spLat2";
	public static final String SP_LON_1 = "spLon1";
	public static final String SP_LON_2 = "spLon2";
    public static final String SP_CAT_TIME1="sppcTime1";
    public static final String SP_CAT_TIME2="sppcTime2";
    
    public static final String START_DATE="sDate";
	public static final String END_DATE="eDate";
	
	
	/*
	 * weather forecast
	 */
	
	 public static final String CITY_NAME="city_name";
     public static final String LOCAL_TIME="localtime";
     public static final String STATE_NAME="state_name";
     public static final String COUNTRY_NAME="country_name";
     public static final String WEATHER_LONG="longitude";
     public static final String WEATHER_LAT="latitude";
     public static final String FORE_CAST_LIST="forecast";
     public static final String WEATHER_LOCATION="location";
     public static final String LOCAL_UPDATE_TIME="localupdatetime";

     /*
 	 * weather forecast Deatils
 	 */
 	
     
     public static final String SNOW_FALL = "snowfall";
 	public static final String DAY_SEQ = "day_sequence";
 	public static final String HIGH_TEMP = "high_temp";
 	public static final String WEATHER_DATE = "date";
 	public static final String TEMP_DESC = "temp_desc";
 	public static final String SKY_DESC = "sky_desc";
 	public static final String DAY_WEEK = "day_of_week";
 	public static final String WEEK_DAY = "weekday";
 	public static final String ICON_NAME = "icon_name";
 	public static final String WEATHER_DESC = "description";
 	public static final String WEATHER_TEMP = "temp";
 	public static final String RAIN_FALL = "rainfall";
 	public static final String BARO_PRESSURE = "baro_pressure";
 	public static final String WEATHER_DATE_KEY = "iso8601";

 	public static final String DAY_LIGHT = "daylight";
 	public static final String LOW_TEMP = "low_temp";
 	public static final String SKY = "sky";
 	public static final String PRECIP_DESC = "precip_desc";
 	public static final String PRECIP = "precip";
 	public static final String AIR_DESC = "air_desc";
 	public static final String AIR = "air";
 	public static final String UV_INDEX = "uv_index";
 	public static final String UV = "uv";
 	public static final String WEATHER_WIND_SPEED = "wind_speed";
 	public static final String WIND_DIR = "wind_dir";
 	public static final String WIND_SHORT = "wind_short";
 	public static final String WIND_LONG = "wind_long";

 	public static final String WEATHER_HUMIDITY = "humidity";
 	public static final String DEW_POINT = "dew_point";
 	public static final String COMFORT = "comfort";
 	public static final String PRECIP_PROB = "precip_prob";
 	public static final String ICON = "icon";
 	public static final String BEAUFORT = "beaufort";

 	public static final String BEAUFORT_DESC = "beaufort_desc";

 	public static final String DAY_SEGMENT = "segment";
 	public static final String LOW_SOIL_MOISTURE = "lower_soil_moisture";

 	public static final String HIGH_SOIL_MOISTURE = "upper_soil_moisture";
   
     public static final String DYN_FILE_LOCATION = "dynFileLoc";
     
     public static final String VIDEO = "videoName";
     public static final String DIST_STOCK_REV_NO = "stRevNo";
     public static final String DIST_STOCK_LIST= "frStockList";
     public static final String SCOPE = "scopeOpr";
  	public static final String ORGANIC_LAND = "certlandOrganic";
  	public static final String TOTAL_SITE = "certTotalsite";
  	public static final String INSPECTIONS_TYPE = "inspectionType";
  	public static final String CERTIFICATION_TYPE="certType";
  	public static final String RIFAN_ID = "rifanId";
  	public static final String DYNAMIC_COMPONENT_DOWNLOAD_REVISION_NO="dynLatestRevNo";

	public static final String ANDROID_VERSION = "androidVersion";
	public static final String MOBILE_MODEL = "mobileModel";
	
  	public static final String PARENT_FIELD = "parentField";
  	public static final String CAT_DEP_KEY = "catDepKey";
  	
  	public static final String ADDITIONAL_PHOTO2 = "photo2";
  	
	public static final String WATER_SRC = "waterSrc";
  	public static final String NME_CROTREE = "nmeCroTree";
  	public static final String NO_CROTREE = "noCroTree";
  	
  	public static final String TRANSPORTATION_VEHICLE = "modeTrans";
  	
	public static final String IDPROOF_PHOTO = "idProofImg";
	public static final String AUDIO_URL="audioURL";
	
	public static final String CROP_CALENDAR_DOWNLOAD_REVISION_NO = "cropCalRevNo";
	
	public static final String YEAR_OF_PLANTING = "yrPlant";
	public static final String FARMER_PHOTO="frPhoto";
	public static final String FARMER_ID_PROOF="idProof";
	//symrise
	public static final String MARITAL = "maritalStatus";
	public static final String CASTE_NAME = "ctName";
	public static final String PHONE_NUMBER = "phoneNo";
	public static final String FARMER_CERT_STATUS = "farmerCertStatus";
	public static final String INSPECTION = "Inspection ";
	public static final String DEAD = "dead";
	//welspun
	public static final String IRRI_ACRE = "irriAcre";
	public static final String RAIN_FED = "rainAcre";
	public static final String FALLOW_ACRE = "fallAcre";
	public static final String DYNAMIC_TRANSACTION_COUNT = "dynamicCount";
	
	//wilmar
	public static final String  LAST_DATE_CHEMICAL = "lstDtCheApp";
	public static final String  FIEDL_NAME= "fieldName";
	public static final String  FIEDL_CROP= "fieldCrop";
	public static final String  FIELD_AREA= "fieldArea";
	public static final String  QUANTITY_APPLIED= "qtyApply";
	public static final String  INPUT_APP= "inputsApp";
	public static final String  INPUT_SOURCE= "inpSource";
	public static final String  COCONUT_FARMING= "actCocFarm";
	public static final String IS_FIELDSTAFF_TRACKING = "isTracker";
	
	/**
	 * Olivado
	 */
	
	public static final String PRE_BANANA_TREE = "preBanana";
	public static final String PARALLEL_PROD = "parallelProd";
	public static final String INPUT_ORG_UNIT = "organicUnit";
	public static final String PRE_HIRED_LABOUR = "hireLabour";
	public static final String RISK_CATE = "riskCategory";
	public static final String CULTIVAR = "cc";
	public static final String PROD_STATUS = "cropSeason";
	public static final String YEARS = "fcode";
	public static final String TOTAL_TREES = "type";
	public static final String IS_OTHER = "isOther";
	
	
	public static final String DIGITAL_SIGNATURE="digitalSign";
	public static final String CROP_EDIT_STATUS = "crpEdtSt";
	
	/**Crop Calandar**/
	public static final String CROP_CALENDAR_LIST = "calandarLst";
	public static final String CALENDAR_NAME = "calName";
	public static final String CALENDAR_ACTIVITY_TYPE="calActiveType";
	public static final String CALENDAR_ACTIVITY_METHOD="calActiveMethod";
	public static final String NO_OF_DAYS="noOfDays";
	
	
	public static final String EVENT_TYPE="eventType";
	public static final String START_DATEE="startDt";
	public static final String END_DATEE="endDt";
	public static final String START_TIME="startTm";
	public static final String END_TIME="endTm";
	public static final String FARMERID="farmerId";
	public static final String PURPOUSE="purpos";
	public static final String GRP="grp";
	
	public static final String EVENT_ID="eventId";
	public static final String ER_OUTPUT="output";
	
	public static final String EVENT_DOWNLOAD_REVISION_NO="eventRevNo";
	public static final String EVENT_NAME="eventNme";
	public static final String EVENT_START_DATE="eventStDt";
	public static final String EVENT_END_DATE="eventEnDt";
	public static final String EVENT_START_TIME="eventStTim";
	public static final String EVENT_END_TIME="eventEnTim";
	public static final String EVENTS = "eventLst";

	
	public static final String ACTIVITY_LIST="activityList";
	public static final String ACTIVITY_ID="activityId";
	//public static final String DEFAULT_VALUE = "";
    
	//Farm History
	public static final String FARM_HISTORY = "crpHistory";
	public static final String YEAR="year";
	public static final String CROP="crp";
	public static final String SEEDLING="seedling";
	public static final String ESTIMATED_ACREAGE="estAcre";
	public static final String NO_TREES="noTrees";
	public static final String PEST_DISEASES="pestDisease";
	public static final String PEST_DISEASES_CONTROL="pestDiseaseCntrl";
	public static final String FERTILIZATION_METHOD="fertMthd";
	public static final String TWO_METER_DIST="twomDist";
	public static final String TEN_METER_DIST="tenmDist";
	public static final String THIRTY_METER_DIST="thirtymDist";
	public static final String DATE_LAST_APP="dateLstApp";
	
	public static final String ACT_STATUS="actStatus";
	public static final String RECOM="recom";
	public static final String VAL_ID="valId";
	
	public static final String  Inspection_Date="insDate";
	public static final String Inspector_Name="inspName";
	public static final String Inspection_Type="insType";
	public static final String Total_Land_Holding="totLand";
	public static final String Land_under_organic_Programme="prodLand";
	public static final String List_collection="listCollection";
	public static final String dynfield="dynfield";
	public static final String agentSign="agentSign";
	
	public static final String lf_ins_cmp="Lfinsr";
	public static final String hlth_ins_cmp="Hlinsc";
	public static final String crp_ins_cmp="CrInsc";
	
	public static final String ACC_NAME="accNm";	
	
	//Loan Module
	public static final String LOAN_INTEREST_PERCENTAGE ="loanInterest";
	public static final String LOAN_REPAYMENT_AMT="loandue";	
	public static final String OUTSTANDING_LOAN_AMOUNT = "osFarmerLoAm";
	public static final String INTEREST_LIST ="intList";
	public static final String MIN_RANGE ="minRange";
	public static final String MAX_RANGE ="maxRange";
	public static final String LOAN_PERCENTAGE ="loanPer";
	public static final String VALUE_DEPENDANCY = "valueDependency";
	
	public static final String CROP_NAME="cropName";
	public static final String VARIETY_CODE="variety";
	public static final String OBSERVATION="observation";
	public static final String RECOMMEND="recommendation";

	public static final String REG_PROOF = "regProof";
	public static final String CMPY_ORIENTATION = "cmpOrient";
	public static final String REG_NUMBER = "regNo";
	public static final String UGANDA_EXPORT = "ugendaExp";
	public static final String REF_LETTERNO = "refLet";
	public static final String Farmer_HaveFarms = "frmHavefrms";
	public static final String SCATTERED ="scattered";
	public static final String Pack_GpsLoc = "gpsLoc";
	public static final String PACKHOUSE = "packHouse";
	public static final String Exp_TinNumber = "expTinNo";
	public static final String TIN = "tin";
	public static final String REX_No = "rexNo";
	public static final String Pack_ToExitPoint = "toexitPt";
	public static final String farm_ToPackhouse="frmPackHouse";

	public static final String EXPORTER_LIST = "exporterList";

	public static final String GREEN_HOUSE = "greenHouse";
	public static final String PREVIOUS = "previous";
	public static final String TRACEABILITY = "traceability";
	public static final String PEST_PARTICULAR = "pestParticulars";
	public static final String MODE_TRANSPORT = "modeTransportation";
	public static final String HARVEST_INS = "harvestInspection";
	public static final String SPECIFICATION = "specification";
	public static final String HYGIENE_STATUS = "hygieneStatus";
	public static final String HYGIENE_DES = "hygieneDescription";
	public static final String GRADING_HALL = "gradingHall";
	public static final String GRADING_DES = "gradingDescription";
	public static final String GRADING_STAFF = "gradinghallStaff";
	public static final String FARM_EQUIPMENT = "farmEquipment";
	public static final String EVIDENCE = "evidence";
	public static final String SUPPLY_STATUS = "supplyStatus";
	public static final String REJECT_REASON = "rejectReason";
	public static final String STATUS_GREEN = "statusGreen";
	public static final String PEST_STATUS = "pestStatus";
	public static final String PEST_LOCATION = "pestLocation";
	public static final String CROP_PRODUCTION= "cropProtection";
	public static final String NO_SCOUTERS = "noScouters";
	public static final String ADDTIONAL_INFO = "additional";
	public static final String IS_SYNCED = "isSynched";
	public static final String REC_NO = "recNo";
	public static final String INSPECTOR_SIGN="inspectorSign";
	public static final String OWNER_SIGN="ownerSign";
	public static final String INS_AUDIO="audio";
	public static final String EVIDENCE_PHOTO="evidencePhoto";
	
	public static final String COLLECTION_CENTRE_DOWNLOAD = "collCenRevNo";
	public static final String COLLECTION_NAME = "collName";
	public static final String COLLECTION_CODE = "CollCode";
	public static final String COLLECTION_LIST = "CollLst";
	
	public static final String BLOCKING_LIST = "blockingLst";
	public static final String BLOCK_NAME = "blockName";
	public static final String BLOCK_COUNT = "blockCount";
	
	public static final String SHIPMENT_URL = "shipmentUrl";
	
	public static final String FARMER_KRA = "farmerKRA";
	
	public static final String QR_BATCH_NO = "QRBatchNo";
}

