-- --------------------
-- Records of COUNTRY
-- --------------------
INSERT INTO `COUNTRY` VALUES ('1', 'IND', 'India', 'Y', '2014-10-12 18:20:20', 'exec', '2014-10-12 18:20:27', 'exec', '1');

-- ------------------
-- Records of STATE
-- ------------------
INSERT INTO `STATE` VALUES ('1', 'TN', 'Tamil Nadu', '1', 'Y', '2014-10-12 18:20:52', 'exec', '2014-10-12 18:21:25', 'exec', '1');

-- ----------------------
-- Records of DISTRICT
-- ----------------------
INSERT INTO `DISTRICT` VALUES ('1', 'CBE', 'Coimbatore', '1', 'Y', '2014-10-12 18:22:25', 'exec', '2014-10-12 18:22:32', 'exec', '1');

-- -------------------------
-- Records of SERVICE_PLACE
-- -------------------------
INSERT INTO `serv_place_type` VALUES ('1', 'SP', 'SERVICE PLACE');
INSERT INTO `serv_place` VALUES ('1', 'SL1', 'GANDHI PURAM', '1', '1');

-- -------------------------
-- Records of SERVICE_POINT
-- -------------------------
INSERT INTO serv_point VALUES ('1', 'SP001', 'Service Point', NULL, '1');

-- -------------------------
-- Records of SHOP_DEALER_ID_SEQ
-- -------------------------
INSERT INTO shop_dealer_id_seq VALUES ('0', '100000', '110000');

-- -------------------------
-- Records of SERV_POINT_TYPE
-- -------------------------
INSERT INTO `serv_point_type` VALUES ('1', '100', 'ServicePlace');