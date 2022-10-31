-- ----------------------------
-- Table structure for country
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `IS_ACTIVE` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Y',
  `CREATE_DT` datetime DEFAULT NULL,
  `CREATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAST_UPDATE_DT` datetime DEFAULT NULL,
  `UPDATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NAME_UNIQUE` (`NAME`) USING BTREE,
  UNIQUE KEY `CODE_UNIQUE` (`CODE`) USING BTREE,
  KEY `IS_ACTIVE` (`IS_ACTIVE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for state
-- ----------------------------
DROP TABLE IF EXISTS `state`;
CREATE TABLE `state` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `COUNTRY_ID` bigint(20) NOT NULL,
  `IS_ACTIVE` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Y',
  `CREATE_DT` datetime DEFAULT NULL,
  `CREATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAST_UPDATE_DT` datetime DEFAULT NULL,
  `UPDATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE_UNIQUE` (`CODE`) USING BTREE,
  UNIQUE KEY `COUNTRY_ID` (`COUNTRY_ID`,`NAME`) USING BTREE,
  KEY `fk_STATE_COUNTRY` (`COUNTRY_ID`) USING BTREE,
  KEY `IS_ACTIVE` (`IS_ACTIVE`) USING BTREE,
  KEY `NAME` (`NAME`) USING BTREE,
  CONSTRAINT `state_ibfk_1` FOREIGN KEY (`COUNTRY_ID`) REFERENCES `country` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for district
-- ----------------------------
DROP TABLE IF EXISTS `district`;
CREATE TABLE `district` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `STATE_ID` bigint(20) NOT NULL,
  `IS_ACTIVE` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Y',
  `CREATE_DT` datetime DEFAULT NULL,
  `CREATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAST_UPDATE_DT` datetime DEFAULT NULL,
  `UPDATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE_UNIQUE` (`CODE`) USING BTREE,
  KEY `fk_DISTRICT_STATE1` (`STATE_ID`) USING BTREE,
  KEY `IS_ACTIVE` (`IS_ACTIVE`) USING BTREE,
  KEY `STATE_ID` (`STATE_ID`,`NAME`) USING BTREE,
  KEY `NAME` (`NAME`) USING BTREE,
  CONSTRAINT `district_ibfk_1` FOREIGN KEY (`STATE_ID`) REFERENCES `state` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for location
-- ----------------------------
DROP TABLE IF EXISTS `location`;
CREATE TABLE `location` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `CODE` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `STATE_ID` bigint(20) NOT NULL,
  `REVISION_NO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_LOCATION_STATE1` (`STATE_ID`) USING BTREE,
  CONSTRAINT `location_ibfk_1` FOREIGN KEY (`STATE_ID`) REFERENCES `state` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;






-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `CODE` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `LATITUDE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LONGITUDE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LOCATION_ID` bigint(20) NOT NULL,
  `POSTAL_CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CITY_LOCATION1` (`LOCATION_ID`) USING BTREE,
  CONSTRAINT `city_ibfk_1` FOREIGN KEY (`LOCATION_ID`) REFERENCES `location` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for gram_panchayat
-- ----------------------------
DROP TABLE IF EXISTS `gram_panchayat`;
CREATE TABLE `gram_panchayat` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `CODE` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `CITY_ID` bigint(20) NOT NULL,
  `REVISION_NO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CITY_ID` (`CITY_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for village
-- ----------------------------
DROP TABLE IF EXISTS `village`;
CREATE TABLE `village` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `CODE` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `CITY_ID` bigint(20) DEFAULT NULL,
  `GRAM_PANCHAYAT_ID` bigint(20) DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_GRAM_PANCHAYAT_ID` (`GRAM_PANCHAYAT_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for serv_place_type
-- ----------------------------
DROP TABLE IF EXISTS `serv_place_type`;
CREATE TABLE `serv_place_type` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE_UNIQUE` (`CODE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



-- ----------------------------
-- Table structure for serv_place
-- ----------------------------
DROP TABLE IF EXISTS `serv_place`;
CREATE TABLE `serv_place` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CITY_ID` bigint(20) NOT NULL,
  `SERV_PLACE_TYPE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE_UNIQUE` (`CODE`) USING BTREE,
  KEY `FK_SERV_PLACE_SERV_PLACE_TYPE1` (`SERV_PLACE_TYPE_ID`) USING BTREE,
  CONSTRAINT `serv_place_ibfk_2` FOREIGN KEY (`SERV_PLACE_TYPE_ID`) REFERENCES `serv_place_type` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for coordinates
-- ----------------------------
DROP TABLE IF EXISTS `coordinates`;
CREATE TABLE `coordinates` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FARM_ID` bigint(20) DEFAULT NULL,
  `LATITUDE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LONGITUDE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ORDER_NO` bigint(20) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for serv_point
-- ----------------------------
DROP TABLE IF EXISTS `serv_point`;
CREATE TABLE `serv_point` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CITY_ID` bigint(20) DEFAULT NULL,
  `SERV_PLACE_TYPE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE_UNIQUE` (`CODE`) USING BTREE,
  KEY `fk_SERVICE_POINT_CITY1` (`CITY_ID`) USING BTREE,
  KEY `fk_SERV_PLACE_SERV_PLACE_TYPE1` (`SERV_PLACE_TYPE_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for serv_point_seq
-- ----------------------------
DROP TABLE IF EXISTS `serv_point_seq`;
CREATE TABLE `serv_point_seq` (
  `ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for serv_point_type
-- ----------------------------
DROP TABLE IF EXISTS `serv_point_type`;
CREATE TABLE `serv_point_type` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE_UNIQUE` (`CODE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for agent_serv_loc_map
-- ----------------------------
DROP TABLE IF EXISTS `agent_serv_loc_map`;
CREATE TABLE `agent_serv_loc_map` (
  `AGENT_ID` bigint(20) NOT NULL,
  `SERV_LOC_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`AGENT_ID`,`SERV_LOC_ID`),
  KEY `IDX_LOC_MAP1` (`AGENT_ID`) USING BTREE,
  KEY `IDX_LOC_MAP2` (`SERV_LOC_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for serv_loc
-- ----------------------------
DROP TABLE IF EXISTS `serv_loc`;
CREATE TABLE `serv_loc` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERV_POINT_ID` bigint(20) NOT NULL,
  `LATITUDE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LONGITUDE` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE_UNIQUE` (`CODE`) USING BTREE,
  KEY `FK_SERV_LOC_SERV_POINT1` (`SERV_POINT_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

