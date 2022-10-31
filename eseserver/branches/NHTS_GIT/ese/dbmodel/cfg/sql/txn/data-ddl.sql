-- ------------------------------
-- TABLE STRUCTURE FOR TXN_TYPE
-- ------------------------------
DROP TABLE IF EXISTS `TXN_TYPE`;
CREATE TABLE `TXN_TYPE` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `CODE` VARCHAR(45) NOT NULL,
  `NAME` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

-- --------------------------------
-- TABLE STRUCTURE FOR TXN_HEADER
-- --------------------------------
DROP TABLE IF EXISTS `TXN_HEADER`;
CREATE TABLE `TXN_HEADER` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `SERIAL_NO` VARCHAR(45) DEFAULT NULL,
  `AGENT_ID` VARCHAR(45) DEFAULT NULL,
  `SERVICE_POINT_ID` VARCHAR(45) DEFAULT NULL,
  `VERSION_NO` VARCHAR(10) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

-- -------------------------
-- TABLE STRUCTURE FOR TXN
-- -------------------------
DROP TABLE IF EXISTS `TXN`;
CREATE TABLE `TXN` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `TXN_TIME` DATETIME DEFAULT NULL,
  `TXN_TYPE` VARCHAR(45) DEFAULT NULL,
  `TXN_CODE` VARCHAR(45) DEFAULT NULL,
  `OPER_TYPE` TINYINT(4) NOT NULL,
  `MODE` TINYINT(4) NOT NULL,
  `RESENT_COUNT` BIGINT(20) NOT NULL,
  `MSG_NO` BIGINT(20) NOT NULL,
  `BATCH_NO` BIGINT(20) NOT NULL,
  `STATUS_CODE` VARCHAR(10) NOT NULL,
  `STATUS_MSG` VARCHAR(45) NOT NULL,
  `TXN_HEADER_ID` BIGINT(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

-- ----------------------------
-- Table structure for distribution
-- ----------------------------
DROP TABLE IF EXISTS `distribution`;
CREATE TABLE `distribution` (
  `ID` bigint(45) NOT NULL AUTO_INCREMENT,
  `RECEIPT_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REFERENCE_NO` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERIAL_NO` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VILLAGE_ID` bigint(20) DEFAULT NULL,
  `VILLAGE_NAME` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TRXN_AGRO_ID` bigint(20) DEFAULT NULL,
  `IS_FREE_DISTRIBUTION` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TOTAL_AMOUNT` double(20,2) DEFAULT '0.00',
  `TAX` double(20,2) DEFAULT '0.00',
  `FINAL_AMOUNT` double(20,2) DEFAULT '0.00',
  `PAYNMENT_MODE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SEASON_ID` bigint(10) DEFAULT NULL,
  `PAYMENT_AMT` double(20,2) DEFAULT '0.00',
  `MOBILE_NO` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `INT_BAL` double(20,2) DEFAULT '0.00',
  `TXN_AMT` double(20,2) DEFAULT '0.00',
  `BAL_AMT` double(20,2) DEFAULT '0.00',
  `AGENT_ID` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AGENT_NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARMER_ID` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARMER_NAME` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERVICE_POINT_ID` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERVICE_POINT_NAME` text COLLATE utf8_unicode_ci,
  `TXN_TIME` datetime DEFAULT NULL,
  `SAMITHI_NAME` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TXN_TYPE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for distribution_detail
-- ----------------------------
DROP TABLE IF EXISTS `distribution_detail`;
CREATE TABLE `distribution_detail` (
  `ID` bigint(45) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` bigint(45) DEFAULT NULL,
  `QUANTITY` double(20,3) DEFAULT NULL,
  `UNIT` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PRICE_PER_UNIT` double(20,2) DEFAULT NULL,
  `SUB_TOTAL` double(20,2) DEFAULT NULL,
  `DISTRIBUTION_ID` bigint(45) DEFAULT NULL,
  `SELLING_PRICE` double(20,2) DEFAULT '0.00',
  `EXISTING_QUANTITY` double(20,3) DEFAULT '0.000',
  `CURRENT_QUANTITY` double(20,3) DEFAULT '0.000',
  `COST_PRICE` double(20,3) DEFAULT '0.000',
  PRIMARY KEY (`ID`),
  KEY `PRODUCT_ID` (`PRODUCT_ID`) USING BTREE,
  KEY `DISTRIBUTION_ID` (`DISTRIBUTION_ID`) USING BTREE,
  CONSTRAINT `distribution_detail_ibfk_1` FOREIGN KEY (`DISTRIBUTION_ID`) REFERENCES `distribution` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `distribution_detail_ibfk_2` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for certificate_category
-- ----------------------------
DROP TABLE IF EXISTS `certificate_category`;
CREATE TABLE `certificate_category` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for certificate_standard
-- ----------------------------
DROP TABLE IF EXISTS `certificate_standard`;
CREATE TABLE `certificate_standard` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CERTIFICATE_CATEGORY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CERTIFICATE_CATEGORY_ID` (`CERTIFICATE_CATEGORY_ID`) USING BTREE,
  CONSTRAINT `certificate_standard_ibfk_1` FOREIGN KEY (`CERTIFICATE_CATEGORY_ID`) REFERENCES `certificate_category` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for city_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `city_warehouse`;
CREATE TABLE `city_warehouse` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CITY_ID` bigint(20) DEFAULT NULL,
  `VILLAGE_ID` bigint(20) DEFAULT NULL,
  `CO_OPERATIVE_ID` bigint(20) DEFAULT NULL,
  `PROCUREMENT_PRODUCT_ID` bigint(20) DEFAULT NULL,
  `NUMBER_OF_BAGS` bigint(20) DEFAULT NULL,
  `GROSS_WEIGHT` double(20,3) DEFAULT NULL,
  `AGENT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `QUALITY` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `IS_DELETE` tinyint(4) DEFAULT '0',
  `REVISION_NO` bigint(20) DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `FK_CITY_WAREHOUSE_VILLAGE` (`VILLAGE_ID`) USING BTREE,
  KEY `FK_CITY_WAREHOUSE_WAREHOUSE` (`CO_OPERATIVE_ID`) USING BTREE,
  KEY `FK_CITY_WAREHOUSE_PROCUREMENT_PRODUCT` (`PROCUREMENT_PRODUCT_ID`) USING BTREE,
  CONSTRAINT `city_warehouse_ibfk_1` FOREIGN KEY (`PROCUREMENT_PRODUCT_ID`) REFERENCES `procurement_product` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `city_warehouse_ibfk_2` FOREIGN KEY (`VILLAGE_ID`) REFERENCES `village` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `city_warehouse_ibfk_3` FOREIGN KEY (`CO_OPERATIVE_ID`) REFERENCES `warehouse` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for city_warehouse_detail
-- ----------------------------
DROP TABLE IF EXISTS `city_warehouse_detail`;
CREATE TABLE `city_warehouse_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TXN_DATE` datetime DEFAULT NULL,
  `TYPEE` tinyint(2) DEFAULT NULL,
  `TXN_REFERENCE_ID` bigint(20) DEFAULT NULL,
  `PREVIOUS_NO_OF_BAGS` bigint(20) DEFAULT NULL,
  `PREVIOUS_GROSS_WEIGHT` double(20,3) DEFAULT NULL,
  `TXN_NO_OF_BAGS` bigint(20) DEFAULT NULL,
  `TXN_GROSS_WEIGHT` double(20,3) DEFAULT NULL,
  `TOTAL_NO_OF_BAGS` bigint(20) DEFAULT NULL,
  `TOTAL_GROSS_WEIGHT` double(20,3) DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CITY_WAREHOUSE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CITY_WAREHOUSE_DETAIL_CITY_WAREHOUSE` (`CITY_WAREHOUSE_ID`) USING BTREE,
  CONSTRAINT `city_warehouse_detail_ibfk_1` FOREIGN KEY (`CITY_WAREHOUSE_ID`) REFERENCES `city_warehouse` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for contract
-- ----------------------------
DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CONTRACT_NUMBER` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARMER_ID` bigint(20) DEFAULT NULL,
  `PROCUREMENT_PRODUCT_ID` bigint(20) DEFAULT NULL,
  `SEASON_MASTER_ID` bigint(20) DEFAULT NULL,
  `PRICE_PATTERN_ID` bigint(20) DEFAULT NULL,
  `ESE_ACCOUNT_ID` bigint(20) DEFAULT NULL,
  `ACRE` double(20,3) DEFAULT NULL,
  `PRICE` double(20,2) DEFAULT NULL,
  `STATUS` tinyint(2) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for crop_yield
-- ----------------------------
DROP TABLE IF EXISTS `crop_yield`;
CREATE TABLE `crop_yield` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARM_ID` bigint(20) DEFAULT NULL,
  `SEASION_ID` bigint(20) DEFAULT NULL,
  `LAND_HOLDING` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LATITUDE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LONGITUDE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CROP_YIELD_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for crop_yield_detail
-- ----------------------------
DROP TABLE IF EXISTS `crop_yield_detail`;
CREATE TABLE `crop_yield_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARM_CROP_MASTER_ID` bigint(20) DEFAULT NULL,
  `YIELD` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CROP_YIELD_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for customer_registration
-- ----------------------------
DROP TABLE IF EXISTS `customer_registration`;
CREATE TABLE `customer_registration` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `FIRST_NAME` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `LAST_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STORE_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ADDRESS` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LOCATION` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PIN_CODE` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CONTACT_NO` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ENROLLED_DATE` date DEFAULT NULL,
  `ENROLLED_AGENT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ENROLLED_AGENT_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ENROLLED_DEVICE_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ENROLLED_DEVICE_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ENROLLED_BRANCH_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ENROLLED_BRANCH_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for farmer_crop_prod_answers
-- ----------------------------
DROP TABLE IF EXISTS `farmer_crop_prod_answers`;
CREATE TABLE `farmer_crop_prod_answers` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ANSWERED_DATE` timestamp NULL DEFAULT NULL,
  `FARMER_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARM_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CATEGORY_CODE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CATEGORY_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SEASON_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `CATEGORY_CODE` (`CATEGORY_CODE`) USING BTREE,
  KEY `ANSWERED_DATE` (`ANSWERED_DATE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for inspection_img_info
-- ----------------------------
DROP TABLE IF EXISTS `inspection_img_info`;
CREATE TABLE `inspection_img_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TXN_TYPE` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for inspection_img
-- ----------------------------
DROP TABLE IF EXISTS `inspection_img`;
CREATE TABLE `inspection_img` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PHOTO` longblob,
  `PHOTO_CAPTURE_TIME` datetime DEFAULT NULL,
  `LATITUDE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LONGITUDE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `INSPECTION_IMG_INFO_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `INSPECTION_IMG_FK` (`INSPECTION_IMG_INFO_ID`) USING BTREE,
  CONSTRAINT `inspection_img_ibfk_1` FOREIGN KEY (`INSPECTION_IMG_INFO_ID`) REFERENCES `inspection_img_info` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for interest_calc_consolidated
-- ----------------------------
DROP TABLE IF EXISTS `interest_calc_consolidated`;
CREATE TABLE `interest_calc_consolidated` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARMER_PROFILE_ID` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `FARMER_ACCOUNT_REF` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ACCUMULATED_PRINCIPAL_AMOUNT` double(20,2) NOT NULL DEFAULT '0.00',
  `CURRENT_RATE_OF_INTEREST` double(20,2) NOT NULL DEFAULT '0.00',
  `ACCUMULATED_INT_AMOUNT` double(20,2) NOT NULL DEFAULT '0.00',
  `ACCUMULATED_PRINCIPAL_AMOUNT2` double(20,2) NOT NULL DEFAULT '0.00',
  `CURRENT_RATE_OF_INTEREST2` double(20,2) NOT NULL DEFAULT '0.00',
  `ACCUMULATED_INT_AMOUNT2` double(20,2) NOT NULL DEFAULT '0.00',
  `LAST_CALC_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATE_DT` timestamp NULL DEFAULT NULL,
  `CREATE_USER_NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAST_UPDATE_DT` timestamp NULL DEFAULT NULL,
  `UPDATE_USER_NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for interest_calc_history
-- ----------------------------
DROP TABLE IF EXISTS `interest_calc_history`;
CREATE TABLE `interest_calc_history` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARMER_PROFILE_ID` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `FARMER_ACCOUNT_REF` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `PRINCIPAL_AMOUNT` double(20,2) NOT NULL DEFAULT '0.00',
  `RATE_OF_INTEREST` double(20,2) NOT NULL DEFAULT '0.00',
  `INTEREST_AMOUNT` double(20,2) NOT NULL DEFAULT '0.00',
  `CALC_DATE` timestamp NULL DEFAULT NULL,
  `CALC_STATUS` int(2) NOT NULL DEFAULT '1',
  `CALC_REMARKS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TRXN_REF_ID` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ACCUMULATED_INT_AMOUNT` double(20,2) DEFAULT NULL,
  `CREATE_DT` timestamp NULL DEFAULT NULL,
  `CREATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAST_UPDATE_DT` timestamp NULL DEFAULT NULL,
  `UPDATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for interest_rate_history
-- ----------------------------
DROP TABLE IF EXISTS `interest_rate_history`;
CREATE TABLE `interest_rate_history` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `RATE_OF_INTEREST` bigint(2) NOT NULL DEFAULT '0',
  `ROI_EFF_FROM` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `AFFECT_EXISTING_FARMER_BAL` int(11) NOT NULL DEFAULT '0',
  `IS_ACTIVE` int(11) NOT NULL,
  `CREATE_DT` timestamp NULL DEFAULT NULL,
  `CREATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAST_UPDATE_DT` timestamp NULL DEFAULT NULL,
  `UPDATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for offline_distribution
-- ----------------------------
DROP TABLE IF EXISTS `offline_distribution`;
CREATE TABLE `offline_distribution` (
  `ID` bigint(45) NOT NULL AUTO_INCREMENT,
  `RECEIPT_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DISTRIBUTION_DATE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARMER_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WAREHOUSE_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VILLAGE_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SEASON_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AGENT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEVICE_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERVICE_POINT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TXN_TYPE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `IS_FREE_DISTRIBUTION` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PAYMENT_MODE` varchar(3) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TAX` double(20,2) DEFAULT '0.00',
  `TOTAL_AMOUNT` double(20,2) DEFAULT '0.00',
  `PAYMENT_AMT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MOBILE_NO` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS_CODE` tinyint(2) DEFAULT NULL,
  `STATUS_MSG` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for offline_distribution_detail
-- ----------------------------
DROP TABLE IF EXISTS `offline_distribution_detail`;
CREATE TABLE `offline_distribution_detail` (
  `ID` bigint(45) NOT NULL AUTO_INCREMENT,
  `PRODUCT_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `QUANTITY` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PRICE_PER_UNIT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SUB_TOTAL` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SELLING_PRICE` double(20,2) DEFAULT NULL,
  `OFFLINE_DISTRIBUTION_ID` bigint(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for offline_inventory
-- ----------------------------
DROP TABLE IF EXISTS `offline_inventory`;
CREATE TABLE `offline_inventory` (
  `ID` bigint(45) NOT NULL AUTO_INCREMENT,
  `SHOP_DEALER_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SHOP_DEALER_NAME` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TXN_TYPE` bigint(3) DEFAULT NULL,
  `CREDIT_LIMIT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OUTSTANDING_BALANCE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `INVENTORY_DATE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `INVENTORY_ORDER_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `INVENTORY_DELIVERY_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TOTAL_QTY` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `GRAND_TOTAL` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PAYMENT_MODE` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PAYMENT_REFERENCE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PAYMENT_AMOUNT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DELIVERY_MODE` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AGENT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEVICE_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERVICE_POINT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS_CODE` tinyint(1) DEFAULT NULL,
  `STATUS_MSG` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for offline_inventory_detail
-- ----------------------------
DROP TABLE IF EXISTS `offline_inventory_detail`;
CREATE TABLE `offline_inventory_detail` (
  `ID` bigint(45) NOT NULL AUTO_INCREMENT,
  `PRODUCT_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `QUANTITY` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PRICE_PER_UNIT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SUB_TOTAL` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OFFLINE_INVENTORY_ID` bigint(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for offline_mtnt
-- ----------------------------
DROP TABLE IF EXISTS `offline_mtnt`;
CREATE TABLE `offline_mtnt` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `RECEIPT_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WAREHOUSE_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MTNT_DATE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AGENT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEVICE_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERVICE_POINT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TOTAL_NO_OF_BAGS` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TOTAL_GROSS_WEIGHT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TOTAL_TARE_WEIGHT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TOTAL_NET_WEIGHT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TRUCK_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DRIVER_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TYPEE` tinyint(1) DEFAULT NULL,
  `STATUS_CODE` tinyint(1) DEFAULT NULL,
  `STATUS_MSG` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for offline_mtnt_detail
-- ----------------------------
DROP TABLE IF EXISTS `offline_mtnt_detail`;
CREATE TABLE `offline_mtnt_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PRODUCT_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OFFLINE_MTNT_ID` bigint(20) DEFAULT NULL,
  `VILLAGE_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NO_OF_BAGS` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `GROSS_WEIGHT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TARE_WEIGHT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NET_WEIGHT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `QUALITY` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for offline_payment
-- ----------------------------
DROP TABLE IF EXISTS `offline_payment`;
CREATE TABLE `offline_payment` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `RECEIPT_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AGENT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEVICE_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERVICE_POINT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARMER_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PAYMENT_DATE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PAYMENT_AMT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PAYMENT_TYPE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SEASON_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PAGE_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REMARKS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS_CODE` tinyint(1) DEFAULT NULL,
  `STATUS_MSG` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for offline_procurement
-- ----------------------------
DROP TABLE IF EXISTS `offline_procurement`;
CREATE TABLE `offline_procurement` (
  `ID` bigint(45) NOT NULL AUTO_INCREMENT,
  `RECEIPT_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARMER_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TYPEE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PROCUREMENT_DATE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TOTAL_AMOUNT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PAYMENT_AMOUNT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AGENT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEVICE_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERVICE_POINT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VILLAGE_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SAMITHI_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CITY_CODE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CHART_NO` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DRIVER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VEHICLE_NO` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PO_NUMBER` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MOBILE_NO` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS_CODE` tinyint(1) DEFAULT NULL,
  `STATUS_MSG` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for offline_procurement_detail
-- ----------------------------
DROP TABLE IF EXISTS `offline_procurement_detail`;
CREATE TABLE `offline_procurement_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `QUALITY` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PRODUCT_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NUMBER_OF_BAGS` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `GROSS_WEIGHT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TARE_WEIGHT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NET_WEIGHT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `RATE_PER_UNIT` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SUB_TOTAL` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OFFLINE_PROCUREMENT_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for payment_mode
-- ----------------------------
DROP TABLE IF EXISTS `payment_mode`;
CREATE TABLE `payment_mode` (
  `ID` bigint(10) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for symptoms_master
-- ----------------------------
DROP TABLE IF EXISTS `symptoms_master`;
CREATE TABLE `symptoms_master` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPEZ` int(10) DEFAULT NULL,
  `SYMPTOM_CODE` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `SYMPTOM_NAME` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SYMPTOM_CODE` (`SYMPTOM_CODE`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for periodic_inspection
-- ----------------------------
DROP TABLE IF EXISTS `periodic_inspection`;
CREATE TABLE `periodic_inspection` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'INTERNAL ID - AUTOGEN',
  `INSPECTION_TYPE` varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT 'PERIODIC/NEED BASED',
  `INSPECTION_DATE` datetime DEFAULT NULL COMMENT 'INSPECTION DATE',
  `FARM_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'FARM ID',
  `PURPOSE` text COLLATE utf8_unicode_ci COMMENT 'NEED BASED - PURPOSE',
  `REMARKS` text COLLATE utf8_unicode_ci,
  `VOICE_RECORDING` longblob COMMENT 'FOR VOICE RECORDING',
  `SURVIVAL_PERCENTATGE` double(20,2) DEFAULT NULL COMMENT 'SURVIVAL PERCENTAGE',
  `AVG_HEIGHT` double(20,2) DEFAULT NULL COMMENT 'AVG. HEIGHT IN METRES',
  `AVG_GIRTH` double(20,2) DEFAULT NULL COMMENT 'AVG. GIRTH IN CMS',
  `CURRENT_STATUS_OF_GROWTH` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'CURRENT STATUS OF GROWTH',
  `ACTIVITIES_AFTER_PREV_VISIT` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'ACTIVITIES AFTER PREV. VISIT',
  `CHEMICAL_NAME` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'CHEMICAL NAME',
  `INTERPLOUGHING_WITH` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'INTERPLOUGHING WITH',
  `TYPE_OF_MANURE_APPLIED` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'TYPE OF MANURE APPLIED',
  `OTHER_TYPE_OF_MANURE_APPLIE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'OTHER TYPE OF MANURE APPLIED',
  `MANURE_QTY_APPLIED` double(20,2) DEFAULT NULL COMMENT 'GMS PER PLANT',
  `TYPE_OF_FERTILIZER_APPLIED` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'TYPE OF FERTILIZER APPLIED',
  `OTHER_TYPE_OF_FERTILIER` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'OTHER TYPE OF FERTILIER',
  `FERTILIZER_QTY_APPLIED` double(20,2) DEFAULT NULL COMMENT 'GMS PER PLANT',
  `MONTH_OF_FERTILIZER_APPLIED` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'MONTH OF FERTILIZER APPLIED',
  `PEST_PROBLEM_NOTICED` char(1) COLLATE utf8_unicode_ci DEFAULT 'N' COMMENT 'PEST PROBLEM NOTICED(Y/N)',
  `NAME_OF_THE_PEST` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'NAME OF THE PEST',
  `OTHER_PEST_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'OTHER PEST NAME',
  `PEST_SYMPTOM_CODE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'PEST SYMPTOM FROM LIST',
  `PEST_SYMPTOM_OTHER_VALUE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'OTHER PEST SYMPTOM VALUE',
  `PEST_INFESTATION_ABOVE_ETL` char(1) COLLATE utf8_unicode_ci DEFAULT 'N' COMMENT 'PEST INFESTATION ABOVE ETL',
  `RECOMMENDED_PESTICIDE_NAME` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'NAME OF THE PESTICIDE RECOMMENDED',
  `PESTICIDE_QTY_APPLIED` double(20,2) DEFAULT NULL COMMENT 'GMS PER PLANT',
  `MONTH_OF_PESTICIDE_APPLICATION` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'MONTH OF PESTICIDE APPLICATION',
  `DATE_OF_PESTICIDE_APPLICATION` date DEFAULT NULL COMMENT 'DATE OF PESTICIDE APPLICATION',
  `PEST_PROBLEM_SOLVED` char(1) COLLATE utf8_unicode_ci DEFAULT 'Y' COMMENT 'PEST PROBLEM SOLVED',
  `DISEASE_PROBLEM_NOTICED` char(1) COLLATE utf8_unicode_ci DEFAULT 'N' COMMENT 'DISEASE PROBLEM NOTICED(Y/N)',
  `NAME_OF_THE_DISEASE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'NAME OF THE DISEASE',
  `OTHER_DISEASE_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'OTHER DISEASE NAME',
  `DISEASE_SYMPTOM_CODE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'DISEASE SYMPTOM FROM LIST',
  `DISEASE_SYMPTOM_OTHER_VALUE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'OTHER DISEASE SYMPTOM VALUE',
  `DISEASE_INFESTATION_ABOVE_ETL` char(1) COLLATE utf8_unicode_ci DEFAULT 'N' COMMENT 'DISEASE INFESTATION ABOVE ETL',
  `RECOMMENDED_FUNGICIDE_NAME` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'NAME OF FUNGICIDE RECOMMENDED',
  `FUNGICIDE_QTY_APPLIED` double(20,2) DEFAULT NULL COMMENT 'GMS PER PLANT',
  `MONTH_OF_FUNGICIDE_APPLICATION` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'MONTH OF FUNGICIDE APPLICATION',
  `DATE_OF_FUNGICIDE_APPLICATION` date DEFAULT NULL COMMENT 'DATE OF FUNGICIDE APPLICATION',
  `DISEASE_PROBLEM_SOLVED` char(1) COLLATE utf8_unicode_ci DEFAULT 'Y' COMMENT 'DISEASE PROBLEM SOLVED',
  `FARMER_OPINION_ABOUT_SERVICE` text COLLATE utf8_unicode_ci COMMENT 'FARMERS OPINION ABOUT SERVICE',
  `IS_INTERCROP` char(1) COLLATE utf8_unicode_ci DEFAULT 'Y' COMMENT 'IS INTERCROP AVAILABLE',
  `NAME_OF_INTERCROP` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'NAME OF THE INTERCROP',
  `YIELD_OBTAINED_IN_MT` double(20,2) DEFAULT NULL COMMENT 'YIELD OBTAINED IN MT',
  `EXPENDITURE_INCURRED` double(20,2) DEFAULT NULL COMMENT 'EXPENDITURE INCURRED',
  `INCOME_GENERATED` double(20,2) DEFAULT NULL COMMENT 'INCOME GENERATED',
  `NET_PROFIT_LOSS` double(20,2) DEFAULT NULL COMMENT 'NET PROFIT OR LOSS',
  `FERTILIZER_APPLIED` char(1) COLLATE utf8_unicode_ci DEFAULT 'N' COMMENT 'IS FERTILIZER APPLIED',
  `IS_FIELD_SAFETY_PROPOSAL` char(1) COLLATE utf8_unicode_ci DEFAULT 'N' COMMENT 'IS FIELD SAFETY PROPOSAL',
  `NO_PLANTS_REPLANT` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL,
  `GAP_PLANT_DATE` date DEFAULT NULL,
  `CREATE_DT` datetime DEFAULT NULL,
  `CREATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAST_UPDATE_DT` datetime DEFAULT NULL,
  `UPDATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `INSPECTION_IMG_INFO_ID` bigint(20) DEFAULT NULL,
  `TXN_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FARM_ID` (`FARM_ID`) USING BTREE,
  KEY `PEST_SYMPTOM_CODE` (`PEST_SYMPTOM_CODE`) USING BTREE,
  KEY `DISEASE_SYMPTOM_CODE` (`DISEASE_SYMPTOM_CODE`) USING BTREE,
  KEY `PERIODIC_INSPECTION_IMG_FK1` (`INSPECTION_IMG_INFO_ID`) USING BTREE,
  KEY `TXN_ID` (`TXN_ID`) USING BTREE,
  CONSTRAINT `periodic_inspection_ibfk_1` FOREIGN KEY (`INSPECTION_IMG_INFO_ID`) REFERENCES `inspection_img_info` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `periodic_inspection_ibfk_2` FOREIGN KEY (`PEST_SYMPTOM_CODE`) REFERENCES `symptoms_master` (`SYMPTOM_CODE`) ON UPDATE CASCADE,
  CONSTRAINT `periodic_inspection_ibfk_3` FOREIGN KEY (`DISEASE_SYMPTOM_CODE`) REFERENCES `symptoms_master` (`SYMPTOM_CODE`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for periodic_inspection_data_list
-- ----------------------------
DROP TABLE IF EXISTS `periodic_inspection_data_list`;
CREATE TABLE `periodic_inspection_data_list` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CATALOUGE_VALUE` tinyint(20) DEFAULT NULL,
  `I_TYPE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OTHER_CATALOGUE_VALUE_NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `QUANTITY` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PERIODIC_INSPECTION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `periodic_inspection_data_list_ibfk1` (`PERIODIC_INSPECTION_ID`) USING BTREE,
  CONSTRAINT `periodic_inspection_data_list_ibfk_1` FOREIGN KEY (`PERIODIC_INSPECTION_ID`) REFERENCES `periodic_inspection` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for periodic_inspection_symptoms_data_list
-- ----------------------------
DROP TABLE IF EXISTS `periodic_inspection_symptoms_data_list`;
CREATE TABLE `periodic_inspection_symptoms_data_list` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SYMPTOM_CODE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `I_TYPE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PERIODIC_INSPECTION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `periodic_inspection_symptoms_data_list_ibfk1` (`PERIODIC_INSPECTION_ID`) USING BTREE,
  CONSTRAINT `periodic_inspection_symptoms_data_list_ibfk_1` FOREIGN KEY (`PERIODIC_INSPECTION_ID`) REFERENCES `periodic_inspection` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for pmt
-- ----------------------------
DROP TABLE IF EXISTS `pmt`;
CREATE TABLE `pmt` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CO_OPERATIVE_ID` bigint(20) DEFAULT NULL,
  `MTNT_TRANSFER_INFO_ID` bigint(20) DEFAULT NULL,
  `MTNR_TRANSFER_INFO_ID` bigint(20) DEFAULT NULL,
  `MTNT_DATE` datetime DEFAULT NULL,
  `MTNR_DATE` datetime DEFAULT NULL,
  `MTNT_RECEIPT_NUMBER` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MTNR_RECEIPT_NUMBER` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TRUCK_ID` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DRIVER_NAME` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS_CODE` tinyint(2) DEFAULT NULL,
  `STATUS_MSG` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TRN_TYPE` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PMT_WAREHOUSE` (`CO_OPERATIVE_ID`) USING BTREE,
  CONSTRAINT `pmt_ibfk_1` FOREIGN KEY (`CO_OPERATIVE_ID`) REFERENCES `warehouse` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for pmt_detail
-- ----------------------------
DROP TABLE IF EXISTS `pmt_detail`;
CREATE TABLE `pmt_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PMT_ID` bigint(20) DEFAULT NULL,
  `VILLAGE_ID` bigint(20) DEFAULT NULL,
  `PROCUREMENT_PRODUCT_ID` bigint(20) DEFAULT NULL,
  `GRADE_MASTER_ID` bigint(20) DEFAULT NULL,
  `PROCUREMENT_GRADE_ID` bigint(20) DEFAULT NULL,
  `MTNT_GROSS_WEIGHT` double(20,3) DEFAULT NULL,
  `MTNR_GROSS_WEIGHT` double(20,3) DEFAULT NULL,
  `MTNT_NO_OF_BAGS` bigint(20) DEFAULT NULL,
  `MTNR_NO_OF_BAGS` bigint(20) DEFAULT NULL,
  `COOPRATIVE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PMT_DETAIL_PMT` (`PMT_ID`) USING BTREE,
  KEY `FK_PMT_DETAIL_VILLAGE` (`VILLAGE_ID`) USING BTREE,
  KEY `FK_PMT_DETAIL_PROCUREMENT_PRODUCT` (`PROCUREMENT_PRODUCT_ID`) USING BTREE,
  KEY `FK_PMT_DETAIL_GRADE_MASTER` (`GRADE_MASTER_ID`) USING BTREE,
  KEY `pmt_detail_ibfk_5` (`PROCUREMENT_GRADE_ID`) USING BTREE,
  CONSTRAINT `pmt_detail_ibfk_1` FOREIGN KEY (`PMT_ID`) REFERENCES `pmt` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pmt_detail_ibfk_2` FOREIGN KEY (`VILLAGE_ID`) REFERENCES `village` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pmt_detail_ibfk_3` FOREIGN KEY (`PROCUREMENT_PRODUCT_ID`) REFERENCES `procurement_product` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pmt_detail_ibfk_4` FOREIGN KEY (`GRADE_MASTER_ID`) REFERENCES `grade_master` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pmt_detail_ibfk_5` FOREIGN KEY (`PROCUREMENT_GRADE_ID`) REFERENCES `procurement_grade` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for price_pattern_detail
-- ----------------------------
DROP TABLE IF EXISTS `price_pattern_detail`;
CREATE TABLE `price_pattern_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `GRADE_MASTER_ID` bigint(20) DEFAULT NULL,
  `PRICE_PATTERN_ID` bigint(20) DEFAULT NULL,
  `PRICE` double(20,2) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for procurement
-- ----------------------------
DROP TABLE IF EXISTS `procurement`;
CREATE TABLE `procurement` (
  `ID` bigint(45) NOT NULL AUTO_INCREMENT,
  `TYPEE` tinyint(4) NOT NULL,
  `VILLAGE_ID` bigint(20) DEFAULT NULL,
  `TRXN_AGRO_ID` bigint(20) DEFAULT NULL,
  `TRIP_SHEET_ID` bigint(45) DEFAULT NULL,
  `PO_NUMBER` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SEASON_ID` bigint(20) DEFAULT NULL,
  `PAYMENT_AMT` double(20,2) DEFAULT '0.00',
  `MOBILE_NO` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PROCUREMENT_VILLAGE` (`VILLAGE_ID`) USING BTREE,
  KEY `FK_PROCUREMENT_TRXN_AGRO` (`TRXN_AGRO_ID`) USING BTREE,
  KEY `FK_PROCUREMENT_SEASON_MASTER` (`SEASON_ID`) USING BTREE,
  CONSTRAINT `procurement_ibfk_1` FOREIGN KEY (`VILLAGE_ID`) REFERENCES `village` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `procurement_ibfk_2` FOREIGN KEY (`TRXN_AGRO_ID`) REFERENCES `trxn_agro` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `procurement_ibfk_3` FOREIGN KEY (`SEASON_ID`) REFERENCES `season_master` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for procurement_detail
-- ----------------------------
DROP TABLE IF EXISTS `procurement_detail`;
CREATE TABLE `procurement_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `QUALITY` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PROCUREMENT_PRODUCT_ID` bigint(20) DEFAULT NULL,
  `PROCUREMENT_GRADE_ID` bigint(20) DEFAULT NULL,
  `NUMBER_OF_BAGS` bigint(20) DEFAULT NULL,
  `GROSS_WEIGHT` double(20,3) DEFAULT NULL,
  `TARE_WEIGHT` double(20,3) DEFAULT NULL,
  `NET_WEIGHT` double(20,3) DEFAULT NULL,
  `RATE_PER_UNIT` double(20,2) DEFAULT NULL,
  `SUB_TOTAL` double(20,2) DEFAULT NULL,
  `PROCUREMENT_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PROCUREMENT_DETAIL_PROCUREMENT_PRODUCT` (`PROCUREMENT_PRODUCT_ID`) USING BTREE,
  KEY `FK_PROCUREMENT_DETAIL_PROCUREMENT` (`PROCUREMENT_ID`) USING BTREE,
  KEY `procurement_detail_ibfk_3` (`PROCUREMENT_GRADE_ID`) USING BTREE,
  CONSTRAINT `procurement_detail_ibfk_1` FOREIGN KEY (`PROCUREMENT_PRODUCT_ID`) REFERENCES `procurement_product` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `procurement_detail_ibfk_2` FOREIGN KEY (`PROCUREMENT_ID`) REFERENCES `procurement` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `procurement_detail_ibfk_3` FOREIGN KEY (`PROCUREMENT_GRADE_ID`) REFERENCES `procurement_grade` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for section
-- ----------------------------
DROP TABLE IF EXISTS `section`;
CREATE TABLE `section` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERIAL_NO` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME` text COLLATE utf8_unicode_ci,
  `SECTION_TYPE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CERTIFICATE_CATEGORY_ID` bigint(20) DEFAULT NULL,
  `REVISION_NUMBER` bigint(25) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SECTION_CERTIFICATE_CATEGORY_ID` (`CERTIFICATE_CATEGORY_ID`) USING BTREE,
  CONSTRAINT `section_ibfk_1` FOREIGN KEY (`CERTIFICATE_CATEGORY_ID`) REFERENCES `certificate_category` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERIAL_NO` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME` text COLLATE utf8_unicode_ci,
  `QUESTION_TYPE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SECTION_ID` bigint(20) DEFAULT NULL,
  `PARENT_QUESTION_ID` bigint(20) DEFAULT NULL,
  `REVISION_NUMBER` bigint(25) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SECTION_ID` (`SECTION_ID`) USING BTREE,
  KEY `FK_PARENT_QUESTION_ID` (`PARENT_QUESTION_ID`) USING BTREE,
  KEY `FK_SECTION_CODE` (`CODE`) USING BTREE,
  CONSTRAINT `question_ibfk_1` FOREIGN KEY (`PARENT_QUESTION_ID`) REFERENCES `question` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `question_ibfk_2` FOREIGN KEY (`SECTION_ID`) REFERENCES `section` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for transfer_info
-- ----------------------------
DROP TABLE IF EXISTS `transfer_info`;
CREATE TABLE `transfer_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TXN_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `AGENT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AGENT_NAME` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEVICE_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEVICE_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERVICE_POINT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERVICE_POINT_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OPER_TYPE` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_cfg_seq
-- ----------------------------
DROP TABLE IF EXISTS `txn_cfg_seq`;
CREATE TABLE `txn_cfg_seq` (
  `ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_req_res_log
-- ----------------------------
DROP TABLE IF EXISTS `txn_req_res_log`;
CREATE TABLE `txn_req_res_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `REQ` longblob,
  `RES` longblob,
  `CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MSG` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `TXN_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_seq
-- ----------------------------
DROP TABLE IF EXISTS `txn_seq`;
CREATE TABLE `txn_seq` (
  `ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for inspection_standard
-- ----------------------------
DROP TABLE IF EXISTS `inspection_standard`;
CREATE TABLE `inspection_standard` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `STANDARD_CODE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STANDARD_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARMER_CROP_PROD_ANSWERS_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_INS_CATG_FARMER_CROP_PROD_ANSWERS_ID_1` (`FARMER_CROP_PROD_ANSWERS_ID`) USING BTREE,
  CONSTRAINT `inspection_standard_ibfk_1` FOREIGN KEY (`FARMER_CROP_PROD_ANSWERS_ID`) REFERENCES `farmer_crop_prod_answers` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for price_pattern
-- ----------------------------
DROP TABLE IF EXISTS `price_pattern`;
CREATE TABLE `price_pattern` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SEASON_MASTER_ID` bigint(20) DEFAULT NULL,
  `PROCUREMENT_PRODUCT_ID` bigint(20) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `UPDATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `REVISION_NO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for contract_pricepattern_map
-- ----------------------------
DROP TABLE IF EXISTS `contract_pricepattern_map`;
CREATE TABLE `contract_pricepattern_map` (
  `CONTRACT_ID` bigint(20) NOT NULL,
  `PRICE_PATTERN_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`CONTRACT_ID`,`PRICE_PATTERN_ID`),
  KEY `CONTRACT_PP_MAP_PP_FK` (`PRICE_PATTERN_ID`) USING BTREE,
  CONSTRAINT `contract_pricepattern_map_ibfk_1` FOREIGN KEY (`CONTRACT_ID`) REFERENCES `contract` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `contract_pricepattern_map_ibfk_2` FOREIGN KEY (`PRICE_PATTERN_ID`) REFERENCES `price_pattern` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for usage_log
-- ----------------------------
DROP TABLE IF EXISTS `usage_log`;
CREATE TABLE `usage_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LOG_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `MODULE` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `RESOURCE` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `USER_ID` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for village_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `village_warehouse`;
CREATE TABLE `village_warehouse` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `VILLAGE_ID` bigint(20) DEFAULT NULL,
  `PROCUREMENT_PRODUCT_ID` bigint(20) DEFAULT NULL,
  `NUMBER_OF_BAGS` bigint(20) DEFAULT NULL,
  `GROSS_WEIGHT` double DEFAULT NULL,
  `AGENT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `QUALITY` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `IS_DELETE` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for ese_txn_type
-- ----------------------------
DROP TABLE IF EXISTS `ese_txn_type`;
CREATE TABLE `ese_txn_type` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for ese_txn_header
-- ----------------------------
DROP TABLE IF EXISTS `ese_txn_header`;
CREATE TABLE `ese_txn_header` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SERIAL_NO` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `DEVICE_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEVICE_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEVICE_TYPE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AGENT_ID` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `AGENT_NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERVICE_POINT_ID` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `SERVICE_POINT_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MSG_NO` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `BATCH_NO` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `MODE` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `ESE_TXN_TYPE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ESE_TXN_HEADER_ESE_TXN_TYPE1` (`ESE_TXN_TYPE_ID`) USING BTREE,
  CONSTRAINT `ese_txn_header_ibfk_1` FOREIGN KEY (`ESE_TXN_TYPE_ID`) REFERENCES `ese_txn_type` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for txn_log
-- ----------------------------
DROP TABLE IF EXISTS `txn_log`;
CREATE TABLE `txn_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TXN_TYPE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERIAL_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MSG_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `RESETN_CNT` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AGENT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OPER_TYPE` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MODE` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TXN_TIME` datetime DEFAULT NULL,
  `VERSION` varchar(90) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REQUEST_LOG` longtext COLLATE utf8_unicode_ci,
  `STATUS` tinyint(4) DEFAULT NULL,
  `STATUS_CODE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS_MSG` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CREATE_DT` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for mtnt
-- ----------------------------
DROP TABLE IF EXISTS `mtnt`;
CREATE TABLE `mtnt` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `RECEIPT_NO` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WAREHOUSE_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WAREHOUSE_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MTNT_DATE` datetime DEFAULT NULL,
  `AGENT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AGENT_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEVICE_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEVICE_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERVICE_POINT_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERVICE_POINT_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OPER_TYPE` tinyint(2) DEFAULT NULL,
  `TOTAL_NO_OF_BAGS` bigint(20) DEFAULT NULL,
  `TOTAL_GROSS_WEIGHT` double DEFAULT NULL,
  `TOTAL_TARE_WEIGHT` double DEFAULT NULL,
  `TOTAL_NET_WEIGHT` double DEFAULT NULL,
  `TYPEE` tinyint(1) DEFAULT NULL,
  `TRUCK_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DRIVER_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for mtnt_detail
-- ----------------------------
DROP TABLE IF EXISTS `mtnt_detail`;
CREATE TABLE `mtnt_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PROCUREMENT_PRODUCT_ID` bigint(20) DEFAULT NULL,
  `MTNT_ID` bigint(20) DEFAULT NULL,
  `VILLAGE_ID` bigint(20) DEFAULT NULL,
  `NO_OF_BAGS` bigint(20) DEFAULT NULL,
  `GROSS_WEIGHT` double(10,3) DEFAULT NULL,
  `TARE_WEIGHT` double DEFAULT NULL,
  `NET_WEIGHT` double DEFAULT NULL,
  `MODE` tinyint(4) DEFAULT NULL,
  `GRADE_MASTER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


