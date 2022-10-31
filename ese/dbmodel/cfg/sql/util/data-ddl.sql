-- ----------------------------
-- TABLE STRUCTURE FOR ESE
-- ----------------------------
DROP TABLE IF EXISTS `ESE`;
CREATE TABLE `ESE` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) COLLATE UTF8_UNICODE_CI DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COLLATE=UTF8_UNICODE_CI;


-- ----------------------------
-- TABLE STRUCTURE FOR PREF
-- ----------------------------
DROP TABLE IF EXISTS `pref`;
CREATE TABLE `pref` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VAL` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ESE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PREF_ESE1` (`ESE_ID`) USING BTREE,
  CONSTRAINT `pref_ibfk_1` FOREIGN KEY (`ESE_ID`) REFERENCES `ese` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- -----------------------------
-- TABLE STRUCTURE FOR ESE_SEQ
-- -----------------------------
DROP TABLE IF EXISTS `ESE_SEQ`;
CREATE TABLE `ESE_SEQ` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `SEQ_KEY` VARCHAR(45) COLLATE UTF8_UNICODE_CI DEFAULT NULL,
  `SEQ_VAL` BIGINT(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SEQ_KEY` (`SEQ_KEY`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COLLATE=UTF8_UNICODE_CI;

-- --------------------------------
-- TABLE STRUCTURE FOR DEPLOY_LOG
-- --------------------------------
DROP TABLE IF EXISTS `DEPLOY_LOG`;
CREATE TABLE `DEPLOY_LOG` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `MODULE` VARCHAR(45) NOT NULL,
  `LOG_DATE` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `VERSION` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;


-- --------------------------------
-- TABLE STRUCTURE FOR UPTIME_LOG
-- --------------------------------
DROP TABLE IF EXISTS `UPTIME_LOG`;
CREATE TABLE `UPTIME_LOG` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `STARTUP` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `SHUTDOWN` TIMESTAMP NULL DEFAULT NULL,
  `MODULE` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

-- --------------------------------
-- TABLE STRUCTURE FOR ACCESS_LOG
-- --------------------------------
DROP TABLE IF EXISTS `access_log`;
CREATE TABLE `access_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LOGIN` varchar(45) NOT NULL,
  `LOGOUT` varchar(45) DEFAULT NULL,
  `USER_ID` varchar(45) NOT NULL,
  `IP_ADDRESS` varchar(45) DEFAULT NULL,
  `MODULE` varchar(45) NOT NULL,
  `STATUS` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `MODULE_IDX_1` (`MODULE`),
  KEY `USER_ID_IDX` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- TABLE STRUCTURE FOR LICENSE
-- ----------------------------
DROP TABLE IF EXISTS `LICENSE`;
CREATE TABLE `LICENSE` (
  `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `LIC_KEY` TEXT NOT NULL,
  `OWNER` VARCHAR(255) NOT NULL,
  `CLIENT` VARCHAR(255) NOT NULL,
  `LIC_TYPE` INT(11) NOT NULL,
  `VER` VARCHAR(25) NOT NULL,
  `VALID_FROM` DATE NOT NULL,
  `VALID_TILL` DATE NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

-- --------------------------------
-- Table structure for `CATALOGUE`
-- --------------------------------
DROP TABLE IF EXISTS `CATALOGUE`;
CREATE TABLE `CATALOGUE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Internal ID',
  `CODE` varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Unique Code for the Catalogue',
  `NAME` varchar(45) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Catalogue Name',
  `IS_ACTIVE` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Y',
  `CREATE_DT` datetime DEFAULT NULL,
  `CREATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAST_UPDATE_DT` datetime DEFAULT NULL,
  `UPDATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  UNIQUE KEY `NAME` (`NAME`),
  KEY `IS_ACTIVE` (`IS_ACTIVE`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ---------------------------------------
-- Table structure for `CATALOGUE_VALUES`
-- ---------------------------------------
DROP TABLE IF EXISTS `CATALOGUE_VALUES`;
CREATE TABLE `CATALOGUE_VALUES` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `CATALOGUE_ID` bigint(20) NOT NULL,
  `ITEM_POS` bigint(10) DEFAULT NULL,
  `IS_ACTIVE` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Y',
  `CREATE_DT` datetime DEFAULT NULL,
  `CREATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAST_UPDATE_DT` datetime DEFAULT NULL,
  `UPDATE_USER_NAME` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  UNIQUE KEY `CATALOGUE_ID` (`CATALOGUE_ID`,`NAME`),
  KEY `IS_ACTIVE` (`IS_ACTIVE`),
  KEY `NAME` (`NAME`),
  CONSTRAINT `catalogue_values_ibfk_1` FOREIGN KEY (`CATALOGUE_ID`) REFERENCES `catalogue` (`ID`) ON UPDATE CASCADE 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- TABLE STRUCTURE FOR TXN_LOG
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
-- Table structure for uptime_log
-- ----------------------------
DROP TABLE IF EXISTS `uptime_log`;
CREATE TABLE `uptime_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `STARTUP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `SHUTDOWN` timestamp NULL DEFAULT NULL,
  `MODULE` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

