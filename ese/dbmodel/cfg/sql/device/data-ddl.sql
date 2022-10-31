-- ---------------------------------
-- TABLE STRUCTURE FOR DEVICE_TYPE
-- ---------------------------------
DROP TABLE IF EXISTS `DEVICE_TYPE`;
CREATE TABLE `DEVICE_TYPE` (
  `ID` BIGINT(20) NOT NULL,
  `CODE` VARCHAR(45) COLLATE UTF8_UNICODE_CI DEFAULT NULL,
  `NAME` VARCHAR(45) COLLATE UTF8_UNICODE_CI DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE_UNIQUE` (`CODE`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COLLATE=UTF8_UNICODE_CI;


-- ----------------------------
-- TABLE STRUCTURE FOR DEVICE
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SERIAL_NUMBER` varchar(35) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DEVICE_TYPE` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AGENT_ID` bigint(20) DEFAULT NULL,
  `MSG_NUM` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `RECPT_NUM` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ENABLED` tinyint(1) DEFAULT NULL,
  `IS_DELETED` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FK_DEVICE_AGENT_ID1` (`AGENT_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- TABLE STRUCTURE FOR agent_warehouse_map
-- ----------------------------
DROP TABLE IF EXISTS `agent_warehouse_map`;
CREATE TABLE `agent_warehouse_map` (
  `AGENT_ID` bigint(20) NOT NULL,
  `WAREHOUSE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`AGENT_ID`,`WAREHOUSE_ID`),
  KEY `IDX_WAREHOUSE_MAP1` (`AGENT_ID`) USING BTREE,
  KEY `IDX_WAREHOUSE_MAP2` (`WAREHOUSE_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for audit_log
-- ----------------------------
DROP TABLE IF EXISTS `audit_log`;
CREATE TABLE `audit_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LOG_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `MODULE` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `USER_ID` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `ENTITY` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `ACTION` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `REF` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for audit_log_prop
-- ----------------------------
DROP TABLE IF EXISTS `audit_log_prop`;
CREATE TABLE `audit_log_prop` (
  `AUDIT_LOG_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `AUDIT_PROP_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`AUDIT_LOG_ID`,`AUDIT_PROP_ID`),
  KEY `FK_AUDIT_LOG_PROP_AUDIT_PROP1` (`AUDIT_PROP_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for audit_prop
-- ----------------------------
DROP TABLE IF EXISTS `audit_prop`;
CREATE TABLE `audit_prop` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PROP` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `OLD_VAL` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `NEW_VAL` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

