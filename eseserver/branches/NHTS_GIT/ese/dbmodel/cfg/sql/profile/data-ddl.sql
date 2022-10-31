-- -----------------------------------
-- Table structure for `DESIGNATION `
-- -----------------------------------
DROP TABLE IF EXISTS `DESIGNATION`;
CREATE TABLE `DESIGNATION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(90) COLLATE utf8_unicode_ci NOT NULL,
  `IS_ACTIVE` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Y',
  `CREATE_DT` datetime DEFAULT NULL,
  `CREATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAST_UPDATE_DT` datetime DEFAULT NULL,
  `UPDATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE_UNIQUE` (`CODE`),
  KEY `IS_ACTIVE` (`IS_ACTIVE`),
  KEY `NAME` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------
-- TABLE STRUCTURE FOR `PERS_INFO`
-- --------------------------------
DROP TABLE IF EXISTS `pers_info`;
CREATE TABLE `pers_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FIRST_NAME` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `MID_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAST_NAME` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `SECOND_LAST_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MIDDLE_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ID_TYPE` char(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ID_NUMBER` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SEX` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `GENDER` char(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FATHER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SALUT` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MOTHER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `MAR_STATUS` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `POB` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OCCUPATION` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NATIONALITY` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DESIGNATION_ID` bigint(20) DEFAULT NULL,
  `IMAGE` longblob,
  PRIMARY KEY (`ID`),
  KEY `FK_DESIGNATION_ID` (`DESIGNATION_ID`),
  CONSTRAINT `FK_DESIGNATION_ID` FOREIGN KEY (`DESIGNATION_ID`) REFERENCES `designation` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- -------------------------------
-- TABLE STRUCTURE FOR CONT_INFO
-- -------------------------------
DROP TABLE IF EXISTS `cont_info`;
CREATE TABLE `cont_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE` smallint(6) DEFAULT NULL,
  `ADDR` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ADDR2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ZIP` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PINCODE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PHONE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MOBILE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CITY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- -------------------------------
-- TABLE STRUCTURE FOR `ESE_USER`
-- -------------------------------
DROP TABLE IF EXISTS `ese_user`;
CREATE TABLE `ese_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PASSWORD` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PERS_INFO_ID` bigint(20) DEFAULT NULL,
  `CONT_INFO_ID` bigint(20) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `UPDATE_SEC` bigint(20) DEFAULT NULL,
  `LANG` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FILTER_ID` int(11) DEFAULT NULL,
  `DATA_ID` bigint(20) DEFAULT NULL,
  `ESE_ROLE_ID` bigint(20) DEFAULT NULL,
  `ENABLE` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `IS_RESET` tinyint(2) DEFAULT '0',
  `LOGIN_STATUS` tinyint(1) DEFAULT NULL,
  `IS_AGENT` tinyint(2) DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USER_NAME` (`USER_NAME`) USING BTREE,
  KEY `FK_ESE_USER_FILTER1` (`FILTER_ID`) USING BTREE,
  KEY `FK_ESE_USER_ESE_ROLE1` (`ESE_ROLE_ID`) USING BTREE,
  KEY `FK_ESE_USER_PERS_INFO1` (`PERS_INFO_ID`) USING BTREE,
  KEY `FK_ESE_USER_CONT_INFO1` (`CONT_INFO_ID`) USING BTREE,
  CONSTRAINT `ESE_USER_IBFK_1` FOREIGN KEY (`CONT_INFO_ID`) REFERENCES `cont_info` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ESE_USER_IBFK_2` FOREIGN KEY (`ESE_ROLE_ID`) REFERENCES `ese_role` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ESE_USER_IBFK_3` FOREIGN KEY (`FILTER_ID`) REFERENCES `filter` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ESE_USER_IBFK_4` FOREIGN KEY (`PERS_INFO_ID`) REFERENCES `pers_info` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ---------------------------------
-- TABLE STRUCTURE FOR ESE_USER_ENT
-- ---------------------------------
DROP TABLE IF EXISTS `ESE_USER_ENT`;
CREATE TABLE `ESE_USER_ENT` (
  `ESE_USER_ID` BIGINT(20) NOT NULL,
  `ESE_ENT_ID` BIGINT(20) NOT NULL,
  PRIMARY KEY (`ESE_USER_ID`,`ESE_ENT_ID`),
  KEY `FK_ESE_USER_ENT_ESE_USER1` (`ESE_USER_ID`),
  KEY `FK_ESE_USER_ENT_ESE_ENT1` (`ESE_ENT_ID`),
  CONSTRAINT `FK_ESE_USER_ENT_ESE_ENT1` FOREIGN KEY (`ESE_ENT_ID`) REFERENCES `ESE_ENT` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ESE_USER_ENT_ESE_USER1` FOREIGN KEY (`ESE_USER_ID`) REFERENCES `ESE_USER` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COLLATE=UTF8_UNICODE_CI;

-- ----------------------------
-- TABLE STRUCTURE FOR IMAGE
-- ----------------------------
DROP TABLE IF EXISTS `IMAGE`;
CREATE TABLE `IMAGE` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `IMG_ID` VARCHAR(45) COLLATE UTF8_UNICODE_CI NOT NULL,
  `IMG` LONGBLOB NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UNQ_IMG_ID` (`IMG_ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COLLATE=UTF8_UNICODE_CI;

-- --------------------------------
-- TABLE STRUCTURE FOR IMAGE_INFO
-- --------------------------------
DROP TABLE IF EXISTS `IMAGE_INFO`;
CREATE TABLE `IMAGE_INFO` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `PHOTO` BIGINT(20) DEFAULT NULL,
  `BIOMETRIC` BIGINT(20) DEFAULT NULL,
  `REV` BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_IMAGE_INFO_IMAGE1` (`PHOTO`),
  KEY `FK_IMAGE_INFO_IMAGE2` (`BIOMETRIC`),
  CONSTRAINT `FK_IMAGE_INFO_IMAGE1` FOREIGN KEY (`PHOTO`) REFERENCES `IMAGE` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_IMAGE_INFO_IMAGE2` FOREIGN KEY (`BIOMETRIC`) REFERENCES `IMAGE` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COLLATE=UTF8_UNICODE_CI;

-- ----------------------------
-- Table structure for prof
-- ----------------------------
DROP TABLE IF EXISTS `prof`;
CREATE TABLE `prof` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PROF_ID` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `PROF_TYPE` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `ENROLL_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ENROLL_AGENT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS` smallint(6) DEFAULT '1',
  `CRE_TIME` timestamp NULL DEFAULT NULL,
  `UPD_TIME` timestamp NULL DEFAULT NULL,
  `PERS_INFO_ID` bigint(20) NOT NULL,
  `CONT_INFO_ID` bigint(20) NOT NULL,
  `NOM_INFO_ID` bigint(20) DEFAULT NULL,
  `PROF_REV` bigint(20) NOT NULL DEFAULT '0',
  `IMAGE_INFO_ID` bigint(20) DEFAULT NULL,
  `SERVICE_POINT_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PROF_ID_UNIQUE` (`PROF_ID`) USING BTREE,
  KEY `FK_PROF_PERS_INFO1` (`PERS_INFO_ID`) USING BTREE,
  KEY `FK_PROF_CONT_INFO1` (`CONT_INFO_ID`) USING BTREE,
  KEY `FK_NOM_INFO` (`NOM_INFO_ID`) USING BTREE,
  KEY `FK_PROF_IMAGE_INFO1` (`IMAGE_INFO_ID`) USING BTREE,
  KEY `FK_SERVICE_POINT` (`SERVICE_POINT_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ---------------------------------
-- TABLE STRUCTURE FOR AGENT_TYPE
-- ---------------------------------
DROP TABLE IF EXISTS `agent_type`;
CREATE TABLE `agent_type` (
  `ID` bigint(20) NOT NULL,
  `CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
-- ----------------------------------
-- TABLE STRUCTURE FOR `AGENT_PROF`
-- ----------------------------------
DROP TABLE IF EXISTS `agent_prof`;
CREATE TABLE `agent_prof` (
  `PROF_ID` bigint(20) NOT NULL,
  `PASSWORD` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PASSWORD_UPT` datetime DEFAULT NULL,
  `AGENT_TYPE_ID` bigint(20) DEFAULT NULL,
  `MODE` tinyint(2) DEFAULT NULL,
  `BOD_STATUS` tinyint(2) DEFAULT NULL,
  `FARMER_CURRENT_ID_SEQ` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARMER_ALLOTED_ID_SEQ` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARMER_ALLOTED_RES_ID_SEQ` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SHOP_DEALER_CURRENT_ID_SEQ` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SHOP_DEALER_ALLOTED_ID_SEQ` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SHOP_DEALER_ALLOTED_RES_ID_SEQ` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARMER_CARD_ID_SEQ` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARMER_ACCOUNT_NO_SEQ` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SHOP_DEALER_CARD_ID_SEQ` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `INTERNAL_ID_SEQ` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `RECEIPT_NUMBER` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ORDER_NO_SEQ` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DELIVERY_NO_SEQ` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`PROF_ID`),
  KEY `fk_AGENT_PROF_PROF1` (`PROF_ID`) USING BTREE,
  KEY `fk_AGENT_PROF_AGENT_TYPE1` (`AGENT_TYPE_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for animal_husbandary
-- ----------------------------
DROP TABLE IF EXISTS `animal_husbandary`;
CREATE TABLE `animal_husbandary` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARM_ID` bigint(20) NOT NULL,
  `FARM_ANIMAL` bigint(4) NOT NULL,
  `OTHER_FARM_ANIMAL` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ANIMAL_COUNT` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FEEDING_USED` bigint(3) DEFAULT NULL,
  `ANIMSL_HOUSING` bigint(3) DEFAULT NULL,
  `OTHER_ANIMSL_HOUSING` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PRODUCTION` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FYM_ESTIMATED_OUTPUT` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FODDER` tinyint(2) DEFAULT NULL,
  `OTHER_FODDER` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REVENUE` tinyint(2) DEFAULT NULL,
  `OTHER_REVENUE` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for farmer
-- ----------------------------
DROP TABLE IF EXISTS `farmer`;
CREATE TABLE `farmer` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARMER_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARMER_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FIRST_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAST_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `GENDER` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `DOJ` date DEFAULT NULL,
  `NO_OF_FAMILY_MEMEBERS` int(11) DEFAULT NULL,
  `IMAGE_INFO_ID` bigint(20) DEFAULT NULL,
  `ADDRESS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CITY_ID` bigint(20) DEFAULT NULL,
  `VILLAGE_ID` bigint(20) DEFAULT NULL,
  `PIN_CODE` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `POST_OFFICE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PHONE_NUMBER` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MOBILE_NUMBER` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT NULL,
  `LATITUDE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LONGITUDE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PHOTO_CAPTURING_TIME` datetime DEFAULT NULL,
  `STATUS` tinyint(2) DEFAULT '0',
  `SAMITHI_ID` bigint(20) DEFAULT NULL,
  `CERTIFICATE_STANDARD_ID` bigint(20) DEFAULT NULL,
  `CERTIFICATE_STANDARD_LEVEL` int(11) DEFAULT '0',
  `CERTIFICATION_TYPE` bigint(2) DEFAULT '0',
  `MARITAL_STATUS` bigint(2) DEFAULT '0',
  `EDUCATION` bigint(3) DEFAULT NULL,
  `CHILD_CNT_ON_SITE` bigint(2) DEFAULT NULL,
  `CHILD_CNT_ON_SITE_PMRY_SCL` bigint(2) DEFAULT NULL,
  `CHILD_CNT_ON_SITE_SEC_SCL` bigint(2) DEFAULT NULL,
  `CUSTOMER_PROJECT_ID` bigint(20) DEFAULT NULL,
  `FARMER_ECONOMY_ID` bigint(20) DEFAULT NULL,
  `INSPECTION_TYPE` bigint(2) DEFAULT NULL,
  `ICS_STATUS` bigint(2) DEFAULT NULL,
  `STATUS_CODE` tinyint(2) DEFAULT '0',
  `ENROLL_PLACE` tinyint(2) DEFAULT '0',
  `ENROLL_PLACE_OTHER` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS_MSG` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TRACE_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'TRACE_ID',
  `FATHER_HUSBAND_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'FATHE_HUSBAND_NAME',
  `POLICE_STATION` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'POLICE_STATION',
  `NO_ADULT` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NO_CHILD` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ANNUAL_INCOME` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `IS_FARMER_CERTIFIED` bigint(2) DEFAULT NULL,
  `IS_VERIFIED` tinyint(2) DEFAULT NULL,
  `VERIFIED_DATE` datetime DEFAULT NULL,
  `VERIIED_AGENT_ID` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VERIIED_AGENT_NAME` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for bank_info
-- ----------------------------
DROP TABLE IF EXISTS `bank_info`;
CREATE TABLE `bank_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARMER_ID` bigint(20) DEFAULT NULL,
  `ACCOUNT_NO` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL,
  `BANK_NAME` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL,
  `BRANCH_NAME` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SORT_CODE` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SWIFT_CODE` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_FARMER` (`FARMER_ID`) USING BTREE,
  CONSTRAINT `bank_info_ibfk_1` FOREIGN KEY (`FARMER_ID`) REFERENCES `farmer` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



-- ----------------------------
-- Table structure for procurement_product
-- ----------------------------
DROP TABLE IF EXISTS `procurement_product`;
CREATE TABLE `procurement_product` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `CODE` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `TYPEE` tinyint(4) NOT NULL,
  `REVISION_NO` bigint(20) DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for warehouse
-- ----------------------------
DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `CODE` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `CITY_ID` bigint(20) DEFAULT NULL,
  `REF_WAREHOUSE_ID` bigint(20) DEFAULT NULL,
  `LOCATION` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ADDRESS` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PHONE_NO` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WAREHOUSE_INCHARGE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CAPACITY_TONNES` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `COMMODITY` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL,
  `COMMODITY_OTHERS` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WAREHOUSE_OWNERSHIP` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT '1',
  `TYPEZ` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CITY_ID` (`CITY_ID`) USING BTREE,
  KEY `REF_WAREHOUSE_ID` (`REF_WAREHOUSE_ID`) USING BTREE,
  CONSTRAINT `warehouse_ibfk_1` FOREIGN KEY (`REF_WAREHOUSE_ID`) REFERENCES `warehouse` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CUSTOMER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CUSTOMER_ADDRESS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LANDLINE` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PERSON_NAME` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MOBILE_NO` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMAIL_ID` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for customer_project
-- ----------------------------
DROP TABLE IF EXISTS `customer_project`;
CREATE TABLE `customer_project` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE_OF_PROJECT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME_OF_PROJECT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NUMBER_OF_PROJECTS` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `UNIT_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REPORT_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME_OF_UNIT` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `LOCATION_OF_UNIT` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `TYPE_OF_HOLDING` bigint(3) NOT NULL,
  `INSPECTION` bigint(3) DEFAULT NULL,
  `UNIT_COMPOSITION_FS` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NUMBER_OF_FARMERS` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AREA` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `CERTIFICATE_STANDARD_ID` bigint(20) DEFAULT NULL,
  `ICS_STATUS` bigint(3) DEFAULT '0',
  `REVISION_NO` bigint(20) DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for customer_project_seq_generator
-- ----------------------------
DROP TABLE IF EXISTS `customer_project_seq_generator`;
CREATE TABLE `customer_project_seq_generator` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PROJECT_CODE_SEQ` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for customer_seq
-- ----------------------------
DROP TABLE IF EXISTS `customer_seq`;
CREATE TABLE `customer_seq` (
  `ID` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for sub_category
-- ----------------------------
DROP TABLE IF EXISTS `sub_category`;
CREATE TABLE `sub_category` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `CATEGORY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CATEGORY_ID` (`CATEGORY_ID`) USING BTREE,
  CONSTRAINT `sub_category_ibfk_1` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `category` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `SUB_CATEGORY_ID` bigint(20) DEFAULT NULL,
  `UNIT` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `PRICE` double(20,2) NOT NULL DEFAULT '0.00',
  `REVISION_NO` bigint(20) DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `FK_SUB_CATEGORY_ID` (`SUB_CATEGORY_ID`) USING BTREE,
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`SUB_CATEGORY_ID`) REFERENCES `sub_category` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for error_log
-- ----------------------------
DROP TABLE IF EXISTS `error_log`;
CREATE TABLE `error_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LOG_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `MODULE` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MESG` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DETAIL` text COLLATE utf8_unicode_ci,
  `RESOURCE` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for ese_account
-- ----------------------------
DROP TABLE IF EXISTS `ese_account`;
CREATE TABLE `ese_account` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ACC_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ACC_TYPE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TYPEE` tinyint(2) DEFAULT NULL,
  `ACC_OPEN_DATE` date DEFAULT NULL,
  `STATUS` tinyint(2) DEFAULT NULL,
  `CRE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `MOD_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PROFILE_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CASH_BALANCE` double(20,2) DEFAULT '0.00',
  `CREDIT_BALANCE` double(20,2) DEFAULT '0.00',
  `BALANCE` double(20,2) DEFAULT '0.00',
  `DIST_BALANCE` double(20,2) DEFAULT '0.00',
  `SHARE_AMOUNT` double(20,2) DEFAULT '0.00',
  `SAVING_AMOUNT` double(20,2) DEFAULT '0.00',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ACC_NO` (`ACC_NO`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for ese_card
-- ----------------------------
DROP TABLE IF EXISTS `ese_card`;
CREATE TABLE `ese_card` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CARD_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CARD_TYPE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TYPEE` tinyint(2) DEFAULT NULL,
  `ISSUE_DATE` date DEFAULT NULL,
  `STATUS` tinyint(2) DEFAULT NULL,
  `MOD_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CRE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `PROFILE_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CARD_REWRITABLE` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CARD_ID` (`CARD_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for ese_logo
-- ----------------------------
DROP TABLE IF EXISTS `ese_logo`;
CREATE TABLE `ese_logo` (
  `ID` bigint(20) NOT NULL,
  `IMG` longblob,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for ese_pref
-- ----------------------------
DROP TABLE IF EXISTS `ese_pref`;
CREATE TABLE `ese_pref` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for ese_user_pref
-- ----------------------------
DROP TABLE IF EXISTS `ese_user_pref`;
CREATE TABLE `ese_user_pref` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `VAL` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `PREF_ID` bigint(20) NOT NULL,
  `USER_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ESE_USER_PREF_ESE_PREF1` (`PREF_ID`) USING BTREE,
  KEY `FK_ESE_USER_PREF_ESE_USER1` (`USER_ID`) USING BTREE,
  CONSTRAINT `ese_user_pref_ibfk_1` FOREIGN KEY (`PREF_ID`) REFERENCES `ese_pref` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ese_user_pref_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `ese_user` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for farm
-- ----------------------------
DROP TABLE IF EXISTS `farm`;
CREATE TABLE `farm` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARM_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARM_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `HECTARES` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAND_IN_PRODUCTION` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAND_NT_IN_PRODUCTION` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LATITUDE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LONGITUDE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PHOTO` longblob,
  `PHOTO_CAPTURING_TIME` datetime DEFAULT NULL,
  `FARMER_ID` bigint(20) DEFAULT NULL,
  `FARM_DETAILED_INFO_ID` bigint(20) DEFAULT NULL,
  `AUDIO` longblob,
  `IS_VERIFIED` tinyint(2) DEFAULT '0',
  `VERIFIED_DATE` datetime DEFAULT NULL,
  `VERIIED_AGENT_ID` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VERIIED_AGENT_NAME` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `IMAGE_NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_FARMER_ID` (`FARMER_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for procurement_variety
-- ----------------------------
DROP TABLE IF EXISTS `procurement_variety`;
CREATE TABLE `procurement_variety` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PROCUREMENT_PRODUCT_ID` bigint(20) DEFAULT NULL,
  `CODE` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `REVISION_NO` bigint(20) DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `fk_PROCUREMENT_PRODUCT_VARIETY_MAPPING` (`PROCUREMENT_PRODUCT_ID`) USING BTREE,
  CONSTRAINT `procurement_variety_ibfk_1` FOREIGN KEY (`PROCUREMENT_PRODUCT_ID`) REFERENCES `procurement_product` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for farm_crops
-- ----------------------------
DROP TABLE IF EXISTS `farm_crops`;
CREATE TABLE `farm_crops` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARM_CROP_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CROP_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CROP_AREA` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PRODUCTION_PER_YEAR` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARM_ID` bigint(20) DEFAULT NULL,
  `FARM_CROPS_MASTER_ID` bigint(20) DEFAULT NULL,
  `PROCUREMENT_CROPS_VARIETY_ID` bigint(20) DEFAULT NULL,
  `CROP_SEASON` bigint(3) DEFAULT NULL,
  `CROP_CATEGORY` bigint(3) DEFAULT NULL,
  `SEED_SOURCE` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_FARM_ID` (`FARM_ID`) USING BTREE,
  KEY `FK_FARM_CROPS_MASTER_ID` (`FARM_CROPS_MASTER_ID`) USING BTREE,
  KEY `fk_FARM_CROP_PROCUREMENT_CROP_VARIETY_MAPPING` (`PROCUREMENT_CROPS_VARIETY_ID`) USING BTREE,
  CONSTRAINT `farm_crops_ibfk_1` FOREIGN KEY (`PROCUREMENT_CROPS_VARIETY_ID`) REFERENCES `procurement_variety` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for farm_crops_master
-- ----------------------------
DROP TABLE IF EXISTS `farm_crops_master`;
CREATE TABLE `farm_crops_master` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CROP_CODE` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `CROP_NAME` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `REVISION_NO` bigint(20) DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for farm_detailed_info
-- ----------------------------
DROP TABLE IF EXISTS `farm_detailed_info`;
CREATE TABLE `farm_detailed_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `IS_FARM_ADDRESS_SAME_AS_FARMERS` tinyint(1) DEFAULT '0',
  `FARM_ADDRESS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAND_MEASUREMENT` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARM_AREA` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARM_PRODUCTIVE_AREA` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARM_CONSERVATION_AREA` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WATER_BODIES_COUNT` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FULL_TIME_WORKER_COUNT` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PART_TIME_WORKER_COUNT` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SEASONAL_WORKER_COUNT` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARM_OWNED` bigint(3) DEFAULT NULL,
  `AREA_UNDER_IRRIGATION` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARM_IRRIGATION` bigint(3) DEFAULT NULL,
  `IRRIGATION_SOURCE` bigint(3) DEFAULT NULL,
  `IRRIGATION_METHOD` bigint(3) DEFAULT NULL,
  `SOIL_TYPE` bigint(3) DEFAULT NULL,
  `SOIL_FERTILITY` bigint(3) DEFAULT NULL,
  `LAST_DATE_CHE_APP` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `BEGIN_CONV` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `INT_INSP_DATE` date DEFAULT NULL,
  `INT_INSP_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SURV_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ICS_STATUS` bigint(3) DEFAULT NULL,
  `FP_LAND` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CONV_LAND` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CONV_CROPS` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CONV_EST_YIELD` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NC` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TOTAL_LAND_HOLDING` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PROPOSED_PLANTING_AREA` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAND_GRADIENT` bigint(3) DEFAULT NULL,
  `APPROACH_ROAD` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REG_YEAR` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OTHER_IRRIGATION` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SOIL_TEXTURE` bigint(3) DEFAULT NULL,
  `RAINFED_VALUE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for farm_ics
-- ----------------------------
DROP TABLE IF EXISTS `farm_ics`;
CREATE TABLE `farm_ics` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARM_ID` bigint(20) DEFAULT NULL,
  `ICS_TYPE` tinyint(2) DEFAULT NULL,
  `LAND_ICS_DETAILS` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `BEGIN_OF_CONVERSION` date DEFAULT NULL,
  `SURVEY_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FARM_ID` (`FARM_ID`) USING BTREE,
  CONSTRAINT `farm_ics_ibfk_1` FOREIGN KEY (`FARM_ID`) REFERENCES `farm` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for farm_inventory
-- ----------------------------
DROP TABLE IF EXISTS `farm_inventory`;
CREATE TABLE `farm_inventory` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARM_ID` bigint(20) NOT NULL,
  `INVENTORY_ITEM` bigint(3) NOT NULL,
  `OTHER_INVENTORY_ITEM` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ITEM_COUNT` bigint(4) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for farmer_economy
-- ----------------------------
DROP TABLE IF EXISTS `farmer_economy`;
CREATE TABLE `farmer_economy` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARMER_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `HOUSING_OWNERSHIP` bigint(3) DEFAULT NULL,
  `HOUSING_TYPE` bigint(3) DEFAULT NULL,
  `OTHER_HOUSING_TYPE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ANNUAL_INCOME` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for farmer_family
-- ----------------------------
DROP TABLE IF EXISTS `farmer_family`;
CREATE TABLE `farmer_family` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARMER_ID` bigint(20) DEFAULT NULL,
  `NAME` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `RELATION` bigint(3) DEFAULT NULL,
  `AGE` bigint(3) NOT NULL,
  `GENDER` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EDUCATION` bigint(3) DEFAULT NULL,
  `HEAD_OF_FAMILY` tinyint(2) DEFAULT '0',
  `ACTIVITY` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `WAGE_EARNER` tinyint(2) DEFAULT '0',
  `PROFESSION` tinyint(2) DEFAULT NULL,
  `OTHER_PROFESSION` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for farmer_id_seq
-- ----------------------------
DROP TABLE IF EXISTS `farmer_id_seq`;
CREATE TABLE `farmer_id_seq` (
  `WEB_SEQ` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WEB_SEQ_LIMIT` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REMOTE_SEQ` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for identity_type
-- ----------------------------
DROP TABLE IF EXISTS `identity_type`;
CREATE TABLE `identity_type` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DESCR` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for inventory
-- ----------------------------
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory` (
  `ID` bigint(45) NOT NULL AUTO_INCREMENT,
  `SHOP_DEALER_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SHOP_DEALER_NAME` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TYPES` tinyint(1) DEFAULT NULL,
  `CREDIT_LIMIT` double DEFAULT NULL,
  `OUTSTANDING_BALANCE` double DEFAULT NULL,
  `ORDER_DATE` datetime DEFAULT NULL,
  `ORDER_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DELIVERY_DATE` datetime DEFAULT NULL,
  `DELIVERY_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CANCEL_DATE` datetime DEFAULT NULL,
  `TOTAL_QTY` double DEFAULT NULL,
  `GRAND_TOTAL` double DEFAULT NULL,
  `PAYMENT_MODE` tinyint(1) DEFAULT NULL,
  `PAYMENT_REFERENCE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PAYMENT_AMOUNT` double DEFAULT NULL,
  `DELIVERY_MODE` tinyint(1) DEFAULT NULL,
  `AGENT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AGENT_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEVICE_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEVICE_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERVICE_POINT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERVICE_POINT_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `APPROVAL_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OPER_TYPE` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for inventory_detail
-- ----------------------------
DROP TABLE IF EXISTS `inventory_detail`;
CREATE TABLE `inventory_detail` (
  `ID` bigint(45) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` bigint(45) DEFAULT NULL,
  `QUANTITY` double DEFAULT NULL,
  `PRICE_PER_UNIT` double DEFAULT NULL,
  `SUB_TOTAL` double DEFAULT NULL,
  `INVENTORY_ID` bigint(45) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `PRODUCT_ID` (`PRODUCT_ID`) USING BTREE,
  KEY `INVENTORY_ID` (`INVENTORY_ID`) USING BTREE,
  CONSTRAINT `inventory_detail_ibfk_1` FOREIGN KEY (`INVENTORY_ID`) REFERENCES `inventory` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `inventory_detail_ibfk_2` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for loc_history
-- ----------------------------
DROP TABLE IF EXISTS `loc_history`;
CREATE TABLE `loc_history` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TXN_TIME` timestamp NULL DEFAULT NULL,
  `SERIAL_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LONGITUDE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LATITUDE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AGENT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for months_table
-- ----------------------------
DROP TABLE IF EXISTS `months_table`;
CREATE TABLE `months_table` (
  `MONTH_NAME` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for nom_info
-- ----------------------------
DROP TABLE IF EXISTS `nom_info`;
CREATE TABLE `nom_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `REL_SHIP` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `ADDRESS` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for offline_farm_crops_enrollment
-- ----------------------------
DROP TABLE IF EXISTS `offline_farm_crops_enrollment`;
CREATE TABLE `offline_farm_crops_enrollment` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARM_CROP_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CROP_AREA` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PRODUCTION_PER_YEAR` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARM_CODE` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OFFLINE_FARMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for offline_farm_enrollment
-- ----------------------------
DROP TABLE IF EXISTS `offline_farm_enrollment`;
CREATE TABLE `offline_farm_enrollment` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARM_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARM_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `HECTARES` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAND_IN_PRODUCTION` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAND_NT_IN_PRODUCTION` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LATITUDE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LONGITUDE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PHOTO` longblob,
  `PHOTO_CAPTURING_TIME` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OFFLINE_FARMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for offline_farmer_enrollment
-- ----------------------------
DROP TABLE IF EXISTS `offline_farmer_enrollment`;
CREATE TABLE `offline_farmer_enrollment` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARMER_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARMER_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FIRST_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAST_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `GENDER` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DOB` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DOJ` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NO_OF_FAMILY_MEMEBERS` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ADDRESS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CITY_CODE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VILLAGE_CODE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SAMITHI_CODE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PIN_CODE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `POST_OFFICE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PHONE_NUMBER` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MOBILE_NUMBER` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LATITUDE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LONGITUDE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PHOTO_CAPTURING_TIME` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `IMG` longblob,
  `FP` longblob,
  `CARD_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ACCT_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ENROLL_DATE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ENROLLED_AGENT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TXN_TYPE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS_CODE` tinyint(2) DEFAULT NULL,
  `STATUS_MSG` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for perf_log
-- ----------------------------
DROP TABLE IF EXISTS `perf_log`;
CREATE TABLE `perf_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MODULE` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `RESOURCE` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `DURATION` bigint(20) NOT NULL,
  `STATUS` int(11) NOT NULL,
  `USER_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LOG_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for grade_master
-- ----------------------------
DROP TABLE IF EXISTS `grade_master`;
CREATE TABLE `grade_master` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `PROCUREMENT_PRODUCT_ID` bigint(20) DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for procurement_grade
-- ----------------------------
DROP TABLE IF EXISTS `procurement_grade`;
CREATE TABLE `procurement_grade` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PROCUREMENT_VARIETY_ID` bigint(20) DEFAULT NULL,
  `CODE` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `PRICE` double(20,2) DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `fk_PROCUREMENT_VARIETY_GRADE_MASTER_MAPPING` (`PROCUREMENT_VARIETY_ID`) USING BTREE,
  CONSTRAINT `procurement_grade_ibfk_1` FOREIGN KEY (`PROCUREMENT_VARIETY_ID`) REFERENCES `procurement_variety` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for trxn_agro
-- ----------------------------
DROP TABLE IF EXISTS `trxn_agro`;
CREATE TABLE `trxn_agro` (
  `ID` bigint(45) NOT NULL AUTO_INCREMENT,
  `RECEIPT_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AGENT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AGENT_NAME` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEVICE_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEVICE_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERVICE_POINT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERVICE_POINT_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARMER_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARMER_NAME` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VENDOR_ID` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VENDOR_NAME` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CUSTOMR_ID` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CUSTOMER_NAME` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PROF_TYPE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TXN_TYPE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MODE_OF_PAYMENT` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `INT_BAL` double(20,2) DEFAULT NULL,
  `TXN_AMT` double(20,2) DEFAULT NULL,
  `BAL_AMT` double(20,2) DEFAULT NULL,
  `OPER_TYPE` tinyint(2) DEFAULT NULL,
  `TXN_TIME` datetime DEFAULT NULL,
  `TXN_DESC` varchar(550) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ESE_ACCOUNT_ID` bigint(45) DEFAULT NULL,
  `SAMITHI_ID` bigint(20) DEFAULT NULL,
  `SERVER_UPDATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `REF_TRXN_AGRO_ID` bigint(45) DEFAULT NULL,
  `AUDIO_FILE` longblob,
  PRIMARY KEY (`ID`),
  KEY `fk_TRXN_AGRO_ESE_ACCOUNT` (`ESE_ACCOUNT_ID`) USING BTREE,
  KEY `fk_TRXN_AGRO_WAREHOUSE` (`SAMITHI_ID`) USING BTREE,
  KEY `fk_TRXN_AGRO_REF_TRXN_AGRO` (`REF_TRXN_AGRO_ID`) USING BTREE,
  CONSTRAINT `trxn_agro_ibfk_1` FOREIGN KEY (`SAMITHI_ID`) REFERENCES `warehouse` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `trxn_agro_ibfk_2` FOREIGN KEY (`ESE_ACCOUNT_ID`) REFERENCES `ese_account` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `trxn_agro_ibfk_3` FOREIGN KEY (`REF_TRXN_AGRO_ID`) REFERENCES `trxn_agro` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for season_master
-- ----------------------------
DROP TABLE IF EXISTS `season_master`;
CREATE TABLE `season_master` (
  `ID` bigint(10) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `YEAR` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for procurement_grade_pricing_history
-- ----------------------------
DROP TABLE IF EXISTS `procurement_grade_pricing_history`;
CREATE TABLE `procurement_grade_pricing_history` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PROCUREMENT_GRADE_ID` bigint(20) DEFAULT NULL,
  `PRICE` double(20,2) DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `fk_PROCUREMENT_GRADE_PRICING_HISTORY_MAPPING` (`PROCUREMENT_GRADE_ID`) USING BTREE,
  CONSTRAINT `procurement_grade_pricing_history_ibfk_1` FOREIGN KEY (`PROCUREMENT_GRADE_ID`) REFERENCES `procurement_grade` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for report_mail_config
-- ----------------------------
DROP TABLE IF EXISTS `report_mail_config`;
CREATE TABLE `report_mail_config` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS` tinyint(4) DEFAULT NULL,
  `MAIL_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for shop_dealer_id_seq
-- ----------------------------
DROP TABLE IF EXISTS `shop_dealer_id_seq`;
CREATE TABLE `shop_dealer_id_seq` (
  `WEB_SEQ` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WEB_SEQ_LIMIT` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REMOTE_SEQ` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for daily_report_mail_config
-- ----------------------------
DROP TABLE IF EXISTS `daily_report_mail_config`;
CREATE TABLE `daily_report_mail_config` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `REPORT_MAIL_CONFIG_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `REPORT_MAIL_CONFIG_ID` (`REPORT_MAIL_CONFIG_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for vendor
-- ----------------------------
DROP TABLE IF EXISTS `vendor`;
CREATE TABLE `vendor` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `VENDOR_ID` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VENDOR_NAME` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VENDOR_ADDRESS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LANDLINE` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PERSON_NAME` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MOBILE_NO` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMAIL_ID` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for warehouse_payment
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_payment`;
CREATE TABLE `warehouse_payment` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TRXN_DATE` datetime DEFAULT NULL,
  `RECEIPT_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WAREHOUSE_ID` bigint(20) DEFAULT NULL,
  `VENDOR_ID` bigint(20) DEFAULT NULL,
  `ORDER_NO` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TOTAL_GOOD_STOCK` bigint(12) DEFAULT '0',
  `TOTAL_DAMAGED_STOCK` bigint(12) DEFAULT '0',
  `TOTAL_QTY` bigint(12) DEFAULT '0',
  `TOTAL_AMOUNT` double(20,2) DEFAULT '0.00',
  `TAX` double(10,2) DEFAULT '0.00',
  `FINAL_AMOUNT` double(20,2) DEFAULT '0.00',
  `PAYMENT_MODE` varchar(3) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PAYMENT_AMOUNT` double(20,2) DEFAULT '0.00',
  `REMARKS` text COLLATE utf8_unicode_ci,
  `REVISION_NO` bigint(20) DEFAULT '1',
  `TRXN_AGRO_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_WAREHOUSE_PAYMENT_WAREHOUSE_MAPPING` (`WAREHOUSE_ID`) USING BTREE,
  KEY `fk_WAREHOUSE_PAYMENT_VENDOR_MAPPING` (`VENDOR_ID`) USING BTREE,
  KEY `fk_WAREHOUSE_PAYMENT_TRXN_AGRO_MAPPING` (`TRXN_AGRO_ID`) USING BTREE,
  CONSTRAINT `warehouse_payment_ibfk_1` FOREIGN KEY (`TRXN_AGRO_ID`) REFERENCES `trxn_agro` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `warehouse_payment_ibfk_2` FOREIGN KEY (`VENDOR_ID`) REFERENCES `vendor` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `warehouse_payment_ibfk_3` FOREIGN KEY (`WAREHOUSE_ID`) REFERENCES `warehouse` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for warehouse_payment_details
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_payment_details`;
CREATE TABLE `warehouse_payment_details` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `RECEIPT_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WAREHOUSE_PAYMENT_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_ID` bigint(20) DEFAULT NULL,
  `PRO_EXPDATE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `COST_PRICE` double(20,2) DEFAULT '0.00',
  `STOCK` bigint(20) DEFAULT '0',
  `DAMAGED_STOCK` bigint(20) DEFAULT '0',
  `TOTAL_AMT` double(20,2) DEFAULT '0.00',
  PRIMARY KEY (`ID`),
  KEY `fk_MAPPING_WAREHOUSE_PAYMENT_PAYMENT_DETAILS` (`WAREHOUSE_PAYMENT_ID`) USING BTREE,
  KEY `fk_MAPPING_WAREHOUSE_PAYMENT_PRODUCT` (`PRODUCT_ID`) USING BTREE,
  CONSTRAINT `warehouse_payment_details_ibfk_1` FOREIGN KEY (`WAREHOUSE_PAYMENT_ID`) REFERENCES `warehouse_payment` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `warehouse_payment_details_ibfk_2` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for warehouse_product
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_product`;
CREATE TABLE `warehouse_product` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `STOCK` double(20,3) DEFAULT '0.000',
  `COST_PRICE` double(20,3) DEFAULT '0.000',
  `WAREHOUSE_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_ID` bigint(20) NOT NULL,
  `AGENT_ID` bigint(20) DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `FK_WAREHOUSE_ID` (`WAREHOUSE_ID`) USING BTREE,
  KEY `FK_PRODUCT_ID` (`PRODUCT_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for warehouse_product_detail
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_product_detail`;
CREATE TABLE `warehouse_product_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `RECEIPT_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TXN_TIME` datetime DEFAULT NULL,
  `TXN_DESC` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VENDOR_ID` varchar(8) COLLATE utf8_unicode_ci DEFAULT NULL,
  `COST_PRICE` double(20,3) DEFAULT '0.000',
  `PREV_STOCK` double(20,3) DEFAULT NULL,
  `TXN_STOCK` double(20,3) DEFAULT NULL,
  `FINAL_STOCK` double(20,3) DEFAULT NULL,
  `DAMAGED_QTY` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ORDER_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS` tinyint(1) DEFAULT NULL,
  `USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WAREHOUSE_PRODUCT_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for warehouse_stock_return
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_stock_return`;
CREATE TABLE `warehouse_stock_return` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TRXN_DATE` datetime DEFAULT NULL,
  `RECEIPT_NO` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STOCK_RETURN_TYPE` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WAREHOUSE_ID` bigint(20) DEFAULT NULL,
  `VENDOR_ID` bigint(20) DEFAULT NULL,
  `ORDER_NO` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TOTAL_DAMAGED_STOCK` bigint(12) DEFAULT '0',
  `TOTAL_AMOUNT` double(20,2) DEFAULT '0.00',
  `PAYMENT_MODE` varchar(3) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PAYMENT_AMOUNT` double(20,2) DEFAULT '0.00',
  `REMARKS` text COLLATE utf8_unicode_ci,
  `REVISION_NO` bigint(20) DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `fk_WAREHOUSE_STOCK_RETURN_WAREHOUSE_MAPPING` (`WAREHOUSE_ID`) USING BTREE,
  KEY `fk_WAREHOUSE_STOCK_RETURN_VENDOR_MAPPING` (`VENDOR_ID`) USING BTREE,
  CONSTRAINT `warehouse_stock_return_ibfk_1` FOREIGN KEY (`VENDOR_ID`) REFERENCES `vendor` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `warehouse_stock_return_ibfk_2` FOREIGN KEY (`WAREHOUSE_ID`) REFERENCES `warehouse` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for warehouse_stock_return_details
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_stock_return_details`;
CREATE TABLE `warehouse_stock_return_details` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `WAREHOUSE_STOCK_RETURN_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_ID` bigint(20) DEFAULT NULL,
  `COST_PRICE` double(20,2) DEFAULT NULL,
  `DAMAGED_STOCK` bigint(12) DEFAULT NULL,
  `NO_STOCK_RETURED` bigint(12) DEFAULT NULL,
  `AMOUNT` double(20,2) DEFAULT '0.00',
  PRIMARY KEY (`ID`),
  KEY `fk_MAPPING_WAREHOUSE_STOCK_RETURN_DETAILS` (`WAREHOUSE_STOCK_RETURN_ID`) USING BTREE,
  KEY `fk_MAPPING_WAREHOUSE_STOCK_RETURN_PRODUCT` (`PRODUCT_ID`) USING BTREE,
  CONSTRAINT `warehouse_stock_return_details_ibfk_1` FOREIGN KEY (`WAREHOUSE_STOCK_RETURN_ID`) REFERENCES `warehouse_stock_return` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `warehouse_stock_return_details_ibfk_2` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for warehouse_village_map
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_village_map`;
CREATE TABLE `warehouse_village_map` (
  `WAREHOUSE_ID` bigint(20) NOT NULL,
  `VILLAGE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`WAREHOUSE_ID`,`VILLAGE_ID`),
  KEY `FK_WAREHOUSE_ID` (`WAREHOUSE_ID`) USING BTREE,
  KEY `FK_VILLAGE_ID` (`VILLAGE_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


