INSERT INTO `cont_info`(`ID`,`TYPE`,`ADDR`,`ADDR2`,`PINCODE`,`PHONE`,`MOBILE`,`EMAIL`,`CITY_ID`) VALUES (1,1,'New Street','Big bazar','642001','042256785678','9988112345','demo@sourcetrace.com',1);
INSERT INTO `pers_info`(`ID`,`FIRST_NAME`,`LAST_NAME`,`MIDDLE_NAME`,`ID_TYPE`,`ID_NUMBER`,`GENDER`,`FATHER_NAME`,`SALUT`,`MOTHER_NAME`,`DOB`,`MAR_STATUS`,`POB`,`OCCUPATION`,`NATIONALITY`,`DESIGNATION_ID`,`IMAGE`) VALUES (1,'userFirstName','userLastName','userMiddleName','BANK_PASSBOOK','PP12345','MALE','sts','1','sts','2001-01-02','1','cbe town','vo','Indian',null,null);

INSERT INTO `ese_user` VALUES ('1', 'exec', 'A91607171504D708', '1', '1', '0', '0', 'en', '1', '0', '1', '1', '0', '0', '0');

-- - agent_type(ID,CODE,NAME)
-- -INSERT INTO `agent_type` VALUES ('1', '01', 'Agent');

-- ------------------------
-- Records of DESIGNATION
-- ------------------------
INSERT INTO `DESIGNATION` VALUES ('1', '1', 'Principal Chief Conservator of Forest', 'Y', '2015-05-28 18:29:12', 'exec', '2015-05-28 18:29:18', 'exec', '1');
INSERT INTO `DESIGNATION` VALUES ('2', '2', 'Additional Principal Chief Conservator of Forest', 'Y', '2015-05-28 18:29:12', 'exec', '2015-05-28 18:29:18', 'exec', '1');
INSERT INTO `DESIGNATION` VALUES ('3', '3', 'Chief Conservator of Forest', 'Y', '2015-05-28 18:29:12', 'exec', '2015-05-28 18:29:18', 'exec', '1');
INSERT INTO `DESIGNATION` VALUES ('4', '4', 'Conservator of Forest', 'Y', '2015-05-28 18:29:12', 'exec', '2015-05-28 18:29:18', 'exec', '1');
INSERT INTO `DESIGNATION` VALUES ('5', '5', 'Deputy Conservator of Forests', 'Y', '2015-05-28 18:29:12', 'exec', '2015-05-28 18:29:18', 'exec', '1');
INSERT INTO `DESIGNATION` VALUES ('6', '6', 'Divisional Forest Officer', 'Y', '2015-05-28 18:29:12', 'exec', '2015-05-28 18:29:18', 'exec', '1');
INSERT INTO `DESIGNATION` VALUES ('7', '7', 'Ranger', 'Y', '2015-05-28 18:29:12', 'exec', '2015-05-28 18:29:18', 'exec', '1');
INSERT INTO `DESIGNATION` VALUES ('8', '8', 'Forester', 'Y', '2015-05-28 18:29:12', 'exec', '2015-05-28 18:29:18', 'exec', '1');

-- ------------------------
-- Records of AGENT_TYPE
-- ------------------------
INSERT INTO agent_type VALUES ('1', '01', 'COOPERATIVE MANAGER');
INSERT INTO agent_type VALUES ('2', '02', 'FIELD STAFF');

-- ------------------------
-- Records of FARMER_ID_SEQ
-- ------------------------
INSERT INTO `farmer_id_seq` VALUES ('0', '100000', '119000');

-- ------------------------
-- Records of ESE_ACCOUNT
-- ------------------------
INSERT INTO ese_account VALUES (null, '110000000001', 'ORG', '0', '2016-01-27', '1', '2016-01-27 10:12:14', '2016-01-27 10:12:14', 'BASIX', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00');


INSERT INTO `identity_type` VALUES ('1', '04', 'PASSPORT WITH SAME ADDRESS', 'PASSPORT WITH SAME ADDRESS');
INSERT INTO `identity_type` VALUES ('2', '05', 'PASSPORT (DIFFERENT ADDRESS)', 'PASSPORT(DIFFERENT ADDRESS)');
INSERT INTO `identity_type` VALUES ('3', '06', 'ELECTION ID CARD', 'ELECTION ID CARD');
INSERT INTO `identity_type` VALUES ('4', '07', 'PAN CARD', 'PAN CARD');
INSERT INTO `identity_type` VALUES ('5', '08', 'GOVT./DEFENCE ID CARD', 'GOVT./DEFENCE ID CARD');
INSERT INTO `identity_type` VALUES ('6', '09', 'ID CARD OF REPUTED EMPLOYERS', 'ID CARD OF REPUTED EMPLOYERS');
INSERT INTO `identity_type` VALUES ('7', '10', 'DRIVING LICENCE', 'DRIVING LICENCE');
INSERT INTO `identity_type` VALUES ('8', '16', 'CREDIT CARD STATEMENT', 'CREDIT CARD STATEMENT');
INSERT INTO `identity_type` VALUES ('9', '17', 'SALARY SLIP', 'SALARY SLIP');
INSERT INTO `identity_type` VALUES ('10', '18', 'INCOME/WEALTH TAX ASSESMENT', 'INCOME/WEALTH TAX ASSESMENT');
INSERT INTO `identity_type` VALUES ('11', '19', 'ELECTRICITY BILL', 'ELECTRICITY BILL');
INSERT INTO `identity_type` VALUES ('12', '20', 'TELEPHONE BILL', 'TELEPHONE BILL');
INSERT INTO `identity_type` VALUES ('13', '31', 'CERTIFICATE OF INCORPORATION', 'CERTIFICATE OF INCORPORATION');
INSERT INTO `identity_type` VALUES ('14', '32', 'ARTICLE OF ASSOCIATION', 'ARTICLE OF ASSOCIATION');
INSERT INTO `identity_type` VALUES ('15', '33', 'MEMORANDUM OF UNDERSTANDING', 'MEMORANDUM OF UNDERSTANDING');
INSERT INTO `identity_type` VALUES ('16', '34', 'CERTIFICATE TO COMMENCE BUSINESS', 'CERTIFICATE TO COMMENCE BUSINESS');
INSERT INTO `identity_type` VALUES ('17', '35', 'PARTNERSHIP DEED', 'PARTNERSHIP DEED');
INSERT INTO `identity_type` VALUES ('18', '36', 'TRUST DEED', 'TRUST DEED');
INSERT INTO `identity_type` VALUES ('19', '37', 'JT.HINDU FAIMILY LETTER(COS-38)', 'JT.HINDU FAIMILY LETTER(COS-38)');
INSERT INTO `identity_type` VALUES ('20', '38', 'BOARD RESOLUTION', 'BOARD RESOLUTION');
INSERT INTO `identity_type` VALUES ('21', '39', 'NOMINATION FORM(FOR NOMINEE)', 'NOMINATION FORM(FOR NOMINEE)');
INSERT INTO `identity_type` VALUES ('22', '40', 'TRADE LICENCE', 'TRADE LICENCE');
INSERT INTO `identity_type` VALUES ('23', '41', 'ID ACCEPTABLE TO  BR.MANAGER', 'ID ACCEPTABLE TO  BR.MANAGER');
INSERT INTO `identity_type` VALUES ('24', '42', 'MIGRATED PPF CUSTOMER ID', 'MIGRATED PPF CUSTOMER ID');
INSERT INTO `identity_type` VALUES ('25', '43', 'RATION CARD', 'RATION CARD');
INSERT INTO `identity_type` VALUES ('26', '99', 'DUMMY MIGRATION ID', 'DUMMY MIGRATION ID');



INSERT INTO `symptoms_master` VALUES ('1', '0', 'SY0001', 'Thick, ribbon like, silken webs consisting of wooden particles and excreta hanging on the bark of the main stems, especially near forks. Holes on trunk.');
INSERT INTO `symptoms_master` VALUES ('2', '0', 'SY0002', 'Small Termite mounts nearby trees, in young seedling and sapling sudden drying of plants ');
INSERT INTO `symptoms_master` VALUES ('3', '0', 'SY0003', 'Slightly raised swellings develop on either side of leaves, leaves become malformed and bushy');
INSERT INTO `symptoms_master` VALUES ('4', '0', 'SY0004', 'Leaves of river red gum covered with psyllids');
INSERT INTO `symptoms_master` VALUES ('5', '0', 'SY0005', 'Fluffy white wax produced in the leaf axils or other sheltered places on the plant.Heavy infestations may result in an accumulation of honeydew.Severe infestations will reduce plant vigour and stunt growth');
INSERT INTO `symptoms_master` VALUES ('6', '1', 'SY0006', 'Vein clearing on the younger leaves and drooping of the older lower leaves, followed by stunting of the plant, yellowing of the lower leaves, defoliation');
INSERT INTO `symptoms_master` VALUES ('7', '1', 'SY0007', 'Stunted shoots with chlorotic leaves, often cupped and with tattered margins.');
INSERT INTO `symptoms_master` VALUES ('8', '1', 'SY0008', 'Gum-impregnated cankers and malformed stems');
INSERT INTO `symptoms_master` VALUES ('9', '1', 'SY0009', 'Pink coloured scattered split and bark and  stem cracks');
INSERT INTO `symptoms_master` VALUES ('10', '1', 'SY0010', 'Leaves will change into red or purple in clour and Mushroom like ball at root portions');
INSERT INTO `symptoms_master` VALUES ('11', '1', 'SY0011', 'Leaf spots range from small, discrete lesions on the upper side of the leaf.');
INSERT INTO `symptoms_master` VALUES ('12', '1', 'SY0012', 'Small, yellow-brown spots appear on the leaf blades and sheaths. Heavily spotted leaves turn yellow or brown prematurely. ');
INSERT INTO `symptoms_master` VALUES ('13', '1', 'SY0013', 'Red discolouration of the young host tissue and blistering of the young bark leading to rapid shoot death');