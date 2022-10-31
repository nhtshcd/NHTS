INSERT INTO `ESE` VALUES ('1', 'ESE');
INSERT INTO `ESE` VALUES ('2', 'CLIENT');
INSERT INTO `ESE` VALUES ('3', 'REMOTE');


-- ----------------------------
-- Records of pref 
-- ----------------------------
INSERT INTO `pref` VALUES (null, 'NO_OF_INVALID_LOGIN_ATTEMPTS', '3', '1');
INSERT INTO `pref` VALUES (null, 'TIME_TO_AUTO_RELEASE', '180000', '1');
INSERT INTO `pref` VALUES (null, 'DAILY_CLOSIGN_TIME', '12:58:00', '1');
INSERT INTO `pref` VALUES (null, 'USER_NAME', 'eseserver@sourcetrace.com', '1');
INSERT INTO `pref` VALUES (null, 'PASSWORD', 'eseserver123', '1');
INSERT INTO `pref` VALUES (null, 'MAIL_HOST', 'smtp.gmail.com', '1');
INSERT INTO `pref` VALUES (null, 'PORT', '587', '1');
INSERT INTO `pref` VALUES (null, 'AGENT_EXPIRY_TIMER', '303', '2');
INSERT INTO `pref` VALUES (null, 'CLIENT_NAME', 'gal', '2');
INSERT INTO `pref` VALUES (null, 'LOC_HISTORY_THRESHOLD_VALUE', '1000', '2');
INSERT INTO `pref` VALUES (null, 'TARE_WEIGHT', '0', '2');
INSERT INTO `pref` VALUES (null, 'CURRENT_SEASON_CODE', 'SE12016', '2');
INSERT INTO `pref` VALUES (null, 'INTEREST_MODULE', '1', '1');
INSERT INTO `pref` VALUES (null, 'RATE_OF_INTEREST', '11.00', '1');
INSERT INTO `pref` VALUES (null, 'ROI_EFFECTIVE_FROM', '01/01/2015', '1');
INSERT INTO `pref` VALUES (null, 'ROI_EFFECT_EXISTING_FARMER', 'false', '1');
INSERT INTO `pref` VALUES (null, 'DAILY_INTEREST_CALC_TYPE', '1', '1');
INSERT INTO `pref` VALUES (null, 'NO_OF_DAYS_PER_YEAR', '365', '1');
INSERT INTO `pref` VALUES (null, 'IS_QR_CODE_SEARCH_REQUIRED', '0', '1');
INSERT INTO `pref` VALUES (null, 'REMOTE_APK_VERSION', '0', '3');
INSERT INTO `pref` VALUES (null, 'REMOTE_CONFIG_VERSION', '0', '3');
INSERT INTO `pref` VALUES (null, 'REMOTE_DB_VERSION', '0', '3');
INSERT INTO `pref` VALUES (null, 'AREA_CAPTURE_MODE', '1', '2');
INSERT INTO `pref` VALUES (null, 'GEO_FENCING_FLAG', '1', '1');
INSERT INTO `pref` VALUES (null, 'GEO_FENCING_RADIUS_MT', '100', '1');
INSERT INTO `pref` VALUES (null, 'SMS_ALTERS', '0', '1');
INSERT INTO `pref` VALUES (null, 'SMS_GATEWAY_URL', 'http://fastalerts.in/api/v1/sms/single.json', '1');
INSERT INTO `pref` VALUES (null, 'SMS_BULK_GATEWAY_URL', 'http://fastalerts.in/api/v1/sms/bulk.json', '1');
INSERT INTO `pref` VALUES (null, 'SMS_STATUS_URL', 'http://fastalerts.in/api/v1/<!-token-!>/sms/<!-uuid-!>/status.json', '1');
INSERT INTO `pref` VALUES (null, 'SMS_TOKEN', 'eb0c7e52-747d-11e4-8b50-39f1a94d1f5a', '1');
INSERT INTO `pref` VALUES (null, 'SMS_SENDER_ID', 'TESTIN', '1');
INSERT INTO `pref` VALUES (null, 'SMS_ROUTE', 'PROMO', '1');
INSERT INTO `pref` VALUES (null, 'SMS_UNICODE', '0', '1');
INSERT INTO `pref` VALUES (null, 'SMS_FLASH', '0', '1');
INSERT INTO `pref` VALUES (null, 'COMPANY_NAMES', 'SOURCE TRACE SYSTEM INDIA PVT LTD', '2');

INSERT INTO `filter`(`ID`,`CODE`) VALUES (1,'Admin');

-- ----------------------------
-- Records of ESE_SEQ
-- ----------------------------
INSERT INTO `ese_seq` VALUES ('1', 'TXN_ID_SEQ', '0');
INSERT INTO `ese_seq` VALUES ('2', 'AGENT_INTERNAL_ID_SEQ', '0');
INSERT INTO `ese_seq` VALUES ('3', 'COUNTRY_CODE_SEQ', '0');
INSERT INTO `ese_seq` VALUES ('4', 'STATE_CODE_SEQ', '0');
INSERT INTO `ese_seq` VALUES ('5', 'DISTRICT_CODE_SEQ', '0');
INSERT INTO `ese_seq` VALUES ('6', 'TALUK_CODE_SEQ', '0');
INSERT INTO `ese_seq` VALUES ('7', 'CLUSTER_CODE_SEQ', '0');
INSERT INTO `ese_seq` VALUES ('8', 'VILLAGE_CODE_SEQ', '0');
INSERT INTO `ese_seq` VALUES ('9', 'HAMLET_CODE_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'CUSTOMER_ID_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'CUSTOMER_ACCOUNT_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'CONTRACT_NO_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'FARMER_ACCOUNT_NO_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'FARMER_CARD_ID_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'WAREHOUSE_ID_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'VENDOR_ID_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'VENDOR_ACCOUNT_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'DISTRIBUTION_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'MANDAL_ID_SEQ', '0');
INSERT INTO `ese_seq` VALUES (NULL, 'WEB_WAREHOUSE_STOCK_ENTRY_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'DISTRIBUTION_TO_FIELDSTAFF_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'PROCUREMENT_RECEIPT_NO_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'MTNR_RECEIPT_NO_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'MTNT_RECEIPT_NO_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'GROUP_ID_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'AGENT_ACCOUNT_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'AGENT_CARD_ID_SEQ', '0');
INSERT INTO `ese_seq` VALUES (null, 'DEVICE_ID_SEQ', '0');


-- ---------------------
-- Records of CATALOGUE
-- ---------------------
INSERT INTO `catalogue` VALUES ('1', 'EDU', 'EDU', 'Y', '2014-10-15 11:16:47', 'exec', '2014-10-15 11:16:47', 'exec', '1');

-- ----------------------------
-- Records of CATALOGUE_VALUES
-- ----------------------------
INSERT  INTO `catalogue_values` VALUES (1,'EDU-1', 'Nil', '1', '1', 'Y', '2014-10-15 12:43:03', 'exec', '2014-10-15 12:43:03', 'exec', '1');
insert  into `catalogue_values` values (2,'EDU-2','School',1,2,'Y','2014-10-15 12:43:03','exec','2014-10-15 12:43:03','exec',1);
insert  into `catalogue_values` values (3,'EDU-3','Graduate',1,3,'Y','2014-10-15 12:43:03','exec','2014-10-15 12:43:03','exec',1);
insert  into `catalogue_values` values (4,'EDU-4','Post Graduate',1,4,'Y','2014-10-15 12:43:03','exec','2014-10-15 12:43:03','exec',1);
insert  into `catalogue_values` values (5,'EDU-5','Doctorate',1,5,'Y','2014-10-15 12:43:03','exec','2014-10-15 12:43:03','exec',1);

-- ----------------------------
-- Records of SEASON_MASTER
-- ----------------------------
INSERT INTO `season_master` VALUES (null, 'SE12016', 'Winter', '2016', '1');