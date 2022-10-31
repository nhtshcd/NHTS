-- -20-06-17 - --
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES ('null', 'donutChart.totalLandProduction', 'en', 'Crop Area Production');
-- -22-06-17 - --
INSERT INTO `locale_property`  VALUES (null, 'exportColumnHeadercultivationcrs', 'en', 'Season,Farmer Name,Farm Name,Crop Name,Village,Area in Acres,LandPreparation,Sowing,GapFilling & Thinning,Weeding,InterCultural,Irrigation,Fertilizer/Bio Fertilizer,Pesticide/Bio Pesticide,Farm Yard Manure(FYM),Harvesting,OtherExpeness,Total Coc(INR)');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'farmerInspectionICSFarmerCode', 'en', 'Farmer Code, Farmer Name,Farm Code,Farm Name,Answered Date');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'farmerInspectionICS', 'en', 'Farmer Name,Farm Code,Farm Name,Answered Date');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'farmerInspectionICSFarmerCode', 'fr', 'Code du paysan, Nom du fermier, Code de la ferme, Nom de la ferme, Date de réponse');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'farmerInspectionICS', 'fr', 'Nom de l\agriculteur, Code de la ferme, Nom de la ferme, Date de réponse');

INSERT INTO `locale_property` VALUES ('null', 'exportColumnHeaderCrsdemoCropSale', 'en', 'Date of Sale,Season Name,Farmer Name,Farm Name,Village Name,Buyer,Invoice/Receipt Details,Sale Price/Kg,Total Sale Quantity (Kg),Total Sale Value (INR),Transporter Name ,Vehicle Number,Payment Amount(INR)');
INSERT INTO `locale_property`  VALUES ('null', 'exportColumnHeadercrscropharvest', 'en', 'Harvest Date, Season , Farmer,Farm Name, Village Name,Total Yield Quantity (Kg), Total Yield Price (INR)');

UPDATE `dynamic_report_config_detail` SET `FIELD`='agroTxn.farmerName' WHERE (`ID`='4')


-- - 26-06-2017 - --
INSERT INTO `locale_property` VALUES (null, 'ExportHeadingNoBranch', 'en', 'Season,Farmer Name,Farm Name,Group,Village,Area in Acres,Crop Area Income,Main Crop Income,Gross Income($),Cultivation Expenses($),Total Profit($),Profit Per Acre($)');

UPDATE `locale_property` SET `id`='133', `code`='locality.name', `lang_code`='fr', `lang_value`='Departement' WHERE (`id`='133');
UPDATE `locale_property` SET `id`='53', `code`='totalNoOfBags', `lang_code`='en', `lang_value`='Total No of Bags' WHERE (`id`='53');

-- - 28-06-2017 - --
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.trainingMaster.trainingTopic', 'en', 'Training');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'categorycode', 'en', 'Training Topic Code');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'categoryprinciple', 'en', 'Training Topic Name');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'remark', 'en', 'Remarks');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'samithi.name', 'en', 'Group');

-- -05-07-17 - --
-- - Table structure for farmer_dynamic_field_value
 
DROP TABLE IF EXISTS `farmer_dynamic_field_value`;
CREATE TABLE `farmer_dynamic_field_value` (
  `ID` bigint(15) NOT NULL AUTO_INCREMENT,
  `FIELD_NAME` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FIELD_VALUE` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FARMER_ID` bigint(15) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FARMER_ID_FK` (`FARMER_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
