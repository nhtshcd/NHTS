-- Excel Import tool menu script
SET @ids = (SELECT id FROM ese_menu WHERE NAME='service');
SET @order=(SELECT MAX(ORD) from ese_menu where PARENT_ID=@ids);

INSERT INTO `ese_menu` VALUES (null, 'service.excelImport', 'Excel Import', 'excelImport_fileUpload.action', @order, @ids, '0', '0','0','0',null);

INSERT INTO `ese_ent` VALUES (null, 'service.excelImport.list');


INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.excelImport.list'));

INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.excelImport'), '1');

INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='service.excelImport'), '1');

-- Excel Immport tool Delete menu
set FOREIGN_KEY_CHECKS=0;
SET @ids = (SELECT id FROM ese_menu WHERE NAME='service.excelImport');
DELETE FROM ese_role_menu WHERE menu_id=@ids;
DELETE FROM ese_role_ent WHERE ent_id=(select id from ese_ent WHERE NAME='service.excelImport.list');
DELETE FROM ese_ent WHERE NAME='service.excelImport.list';
DELETE FROM ese_menu_action WHERE menu_id=@ids;  
DELETE FROM ese_menu WHERE id=@ids; 

-- -26-10-2016

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (49, 'Pest Name', 49, 'agro', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (50, 'Pest Symptoms', 50, 'agro', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (51, 'Disease Name', 51, 'agro', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (52, 'Disease Symptom', 52, 'agro', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (53, 'Inspection Crop', 53, 'agro', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (54, 'Crop Growth', 54, 'agro', '1', '1');

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (55, 'Interplough Type', 55, 'agro', '1', '1');

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (56, 'Usage Level', 56, 'agro', '1', '1');

-- - 11-14-2016
INSERT INTO `eses_agro`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.location.municipality', 'en', 'Taluk');
INSERT INTO `eses_agro`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.location.municipality', 'fr', 'Taluk');


INSERT INTO `eses_agro`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'pesticideRecommended', 'en', 'Pest Remedies');
INSERT INTO `eses_agro`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'fungicideRecommended', 'en', 'Disease Remedies');


SET @ids = (SELECT id FROM ese_menu WHERE NAME='service');
SET @order=(SELECT MAX(ORD) from ese_menu where PARENT_ID=@ids);
INSERT INTO `ese_menu` VALUES (null, 'service.smsAlert', 'SMS Alert', 'sMSAlert_list.action', @order, @ids, '0', '0','0','0',NULL);

INSERT INTO `ese_ent` VALUES (null, 'service.smsAlert.list');
INSERT INTO `ese_ent` VALUES (null, 'service.smsAlert.create');


INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.smsAlert.list'));
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.smsAlert.create'));

INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.smsAlert'), '1');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.smsAlert'), '2');

INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='service.smsAlert'), '1');

UPDATE `pref` SET `VAL`='1' WHERE (`NAME`='SMS_ALTERS');
--12-Jan-2017
UPDATE `ese_menu` SET `URL`='procurementProduct_create.action' WHERE (`NAME`='service.procurementService');
-- -03 FEB 2017
-- - Group Activity Report
SET @ids = (SELECT id FROM ese_menu WHERE NAME='report');

INSERT INTO ese_menu VALUES (NULL, 'report.samithiAccessReport', 'Group Access Report', 'samithiAccessReport_list.action', '18',  @ids, '0', '0', '0', '0', NULL);

INSERT INTO ese_ent VALUES (NULL, 'report.samithiAccessReport.list');

INSERT INTO ese_role_ent VALUES ('1', (SELECT id FROM ese_ent WHERE NAME='report.samithiAccessReport.list'));
INSERT INTO ese_role_ent VALUES ('2', (SELECT id FROM ese_ent WHERE NAME='report.samithiAccessReport.list'));
INSERT INTO ese_role_ent VALUES ('3', (SELECT id FROM ese_ent WHERE NAME='report.samithiAccessReport.list'));
INSERT INTO ese_role_ent VALUES ('5', (SELECT id FROM ese_ent WHERE NAME='report.samithiAccessReport.list'));
INSERT INTO ese_role_ent VALUES ('6', (SELECT id FROM ese_ent WHERE NAME='report.samithiAccessReport.list'));
INSERT INTO ese_role_ent VALUES ('7', (SELECT id FROM ese_ent WHERE NAME='report.samithiAccessReport.list'));
INSERT INTO ese_role_ent VALUES ('9', (SELECT id FROM ese_ent WHERE NAME='report.samithiAccessReport.list'));
INSERT INTO ese_role_ent VALUES ('11', (SELECT id FROM ese_ent WHERE NAME='report.samithiAccessReport.list'));
INSERT INTO ese_role_ent VALUES ('13', (SELECT id FROM ese_ent WHERE NAME='report.samithiAccessReport.list'));

INSERT INTO ese_role_menu VALUES (NULL, (SELECT id FROM ese_menu WHERE NAME='report.samithiAccessReport'), '1');
INSERT INTO ese_role_menu VALUES (NULL, (SELECT id FROM ese_menu WHERE NAME='report.samithiAccessReport'), '2');
INSERT INTO ese_role_menu VALUES (NULL, (SELECT id FROM ese_menu WHERE NAME='report.samithiAccessReport'), '3');
INSERT INTO ese_role_menu VALUES (NULL, (SELECT id FROM ese_menu WHERE NAME='report.samithiAccessReport'), '5');
INSERT INTO ese_role_menu VALUES (NULL, (SELECT id FROM ese_menu WHERE NAME='report.samithiAccessReport'), '6');
INSERT INTO ese_role_menu VALUES (NULL, (SELECT id FROM ese_menu WHERE NAME='report.samithiAccessReport'), '7');
INSERT INTO ese_role_menu VALUES (NULL, (SELECT id FROM ese_menu WHERE NAME='report.samithiAccessReport'), '9');
INSERT INTO ese_role_menu VALUES (NULL, (SELECT id FROM ese_menu WHERE NAME='report.samithiAccessReport'), '11');
INSERT INTO ese_role_menu VALUES (NULL, (SELECT id FROM ese_menu WHERE NAME='report.samithiAccessReport'), '13');

INSERT INTO ese_menu_action VALUES ((SELECT id FROM ese_menu WHERE NAME='report.samithiAccessReport'), '1');

-- -06-02-2017
INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('info.ics', 'en', 'Conversion Status');
INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('info.ics', 'fr', '\r\nÉtat de la conversion');

-- -16 MAR 2017
-- -Report Menu Order Changes
-- -Run for All Tenant

UPDATE ese_menu SET ORD='1' WHERE (NAME='report.warehouse');
UPDATE ese_menu SET ORD='2' WHERE (NAME='report.distribution');
UPDATE ese_menu SET ORD='3' WHERE (NAME='report.procurementReport');
UPDATE ese_menu SET ORD='4' WHERE (NAME='report.mtnt.procurement');
UPDATE ese_menu SET ORD='5' WHERE (NAME='report.mtnr.procurement');
UPDATE ese_menu SET ORD='6' WHERE (NAME='report.periodicInspectionReport');
UPDATE ese_menu SET ORD='7' WHERE (NAME='report.farmer');
INSERT INTO ese_menu VALUES (NULL, 'report.fieldStaff', 'FieldStaff', 'javascript:void(0)', '8', '4', '0', '0', '0', '0', NULL);

UPDATE ese_menu SET ORD='9' WHERE (NAME='report.training');
UPDATE ese_menu SET ORD='10' WHERE (NAME='report.sensitizingReport');
UPDATE ese_menu SET ORD='11' WHERE (NAME='report.samithiAccessReport');
UPDATE ese_menu SET ORD='12' WHERE (NAME='report.farmerInspectionReport');
UPDATE ese_menu SET ORD='13' WHERE (NAME='report.failedInspectionReport');
UPDATE ese_menu SET ORD='14' WHERE (NAME='report.offlineReport');

DELETE FROM ese_role_menu WHERE (MENU_ID=(SELECT ID FROM ese_menu WHERE NAME='report.balanceSheet'));

SET @ids = (SELECT id FROM ese_menu WHERE NAME='report.fieldStaff');
UPDATE ese_menu  SET ORD='1',PARENT_ID=@ids WHERE NAME='report.fieldStaffStockReport' ;
UPDATE ese_menu  SET ORD='2',PARENT_ID=@ids WHERE NAME='report.fieldStaffBalanceReport' ;
UPDATE ese_menu  SET ORD='3',PARENT_ID=@ids WHERE NAME='report.fieldStaffAccessReport' ;
UPDATE ese_menu  SET ORD='4',PARENT_ID=@ids WHERE NAME='report.fieldStaffManagement' ;

SET @ids = (SELECT id FROM ese_menu WHERE NAME='report.farmer');
UPDATE ese_menu SET ORD='1' WHERE (NAME='report.farmerList');
UPDATE ese_menu SET ORD='2',PARENT_ID=@ids WHERE NAME='report.sowingReport' ;
UPDATE ese_menu SET ORD='3' WHERE (NAME='report.farmerCropReport');
UPDATE ese_menu SET ORD='4' WHERE (NAME='report.cultivationReport');
UPDATE ese_menu SET ORD='5' WHERE (NAME='report.cBRReport');
UPDATE ese_menu SET ORD='6' WHERE (NAME='report.farmerIncomeReport');
UPDATE ese_menu SET ORD='7',PARENT_ID=@ids WHERE NAME='report.farmerBalanceReport' ;

UPDATE ese_menu SET ORD='1' WHERE (NAME='report.warehouseStockEntryReport');
UPDATE ese_menu SET ORD='2' WHERE (NAME='report.warehouseStockReport');

SET @ids = (SELECT id FROM ese_menu WHERE NAME='report.procurementReport');
UPDATE ese_menu SET ORD='4',PARENT_ID=@ids WHERE NAME='report.procurementStock' ;
UPDATE ese_menu SET ORD='2' WHERE (NAME='report.cropHarvestReport');
UPDATE ese_menu SET ORD='3' WHERE (NAME='report.cropSaleReport');
UPDATE ese_menu SET ORD='1',PARENT_ID=@ids WHERE NAME='report.procurement' ;


INSERT INTO ese_role_menu VALUES (NULL, (SELECT MAX(id) FROM ese_menu), '1');
INSERT INTO ese_role_menu VALUES (NULL, (SELECT MAX(id) FROM ese_menu), '4');
INSERT INTO ese_role_menu VALUES (NULL, (SELECT MAX(id) FROM ese_menu), '3');

DELETE FROM ese_role_menu WHERE (MENU_ID=(SELECT ID FROM ese_menu WHERE NAME='report.balanceSheet'));


-- -07 APR 2017
INSERT INTO locale_property VALUES (NULL, 'farmer.fpo', 'en', 'FPO/FG Group ');

-- -20 June 2017 - --
INSERT INTO `dynamic_report_config_detail` VALUES (null, '1', 'farmerName', 'agroTxn.farmerName', '1', NULL, NULL, '0', '0', '200', '13', '0', '1', '1');
UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE`='3', `PARAMTERS`='4,13', `METHOD`='getFarmerRegUnReg' WHERE (`FIELD`='f.firstName');


INSERT INTO `ese_txn_type` (`CODE`, `NAME`) VALUES ( '377', 'farmerFeedback');
INSERT INTO `txn_type` (`CODE`, `NAME`) VALUES ( '377', 'farmerFeedback');


ALTER TABLE `farmer`
ADD COLUMN `FORM_FILLED_BY`  varchar(250) NULL AFTER `HEALTH_ISSUE_DESCRIBE`,
ADD COLUMN `ASSESSMENT`  varchar(250) NULL AFTER `FORM_FILLED_BY`,
ADD COLUMN `PLACE_OF_ASSESSMENT`  varchar(10) NULL AFTER `ASSESSMENT`,
ADD COLUMN `OBJECTIVE`  varchar(250) NULL AFTER `PLACE_OF_ASSESSMENT`;

-- -13 July 2017 - --
INSERT INTO `farm_field` (`ID`, `NAME`, `TYPE`, `TYPE_NAME`, `PARENT`, `STATUS`) VALUES ('null', 'ownLand', '1', 'farm.ownLand', '1', '0');


INSERT INTO `farm_field` (`ID`, `NAME`, `TYPE`, `TYPE_NAME`, `PARENT`, `STATUS`) VALUES ('null', 'leasedland', '1', 'farm.leasedLand', '1', '0');


INSERT INTO `farm_field` (`ID`, `NAME`, `TYPE`, `TYPE_NAME`, `PARENT`, `STATUS`) VALUES ('null', '\r\nirriLand', '1', 'farm.irrigationLand', '1', '0');


UPDATE `farm_field` SET `ID`='2', `NAME`='Contact Info', `TYPE`='4', `TYPE_NAME`='contactInfo', `PARENT`=NULL, `STATUS`='0' WHERE (`ID`='2');


UPDATE `farm_field` SET `ID`='8', `NAME`='Integrated Farming System', `TYPE`='4', `TYPE_NAME`='farmSysInfo', `PARENT`=NULL, `STATUS`='0' WHERE (`ID`='8');

-- - 31-07-2017 - --
INSERT INTO `locale_property` VALUES (null, 'FarmerLISTExportHeading', 'en', 'Organization,Farmer Code,Farmer Name,Father Name,Village,LG,Is Farmer Certified,Status');
INSERT INTO `locale_property` VALUES (null, 'FarmerLISTExportHeading', 'fr', 'Organisation, Code du paysan, Nom du fermier, Nom du père, Village, LG, Certificat du fermier, Statut');
INSERT INTO `locale_property` VALUES (null, 'FarmerLISTExportHeadingNoBranch', 'en', 'Farmer Code,Farmer Name,Father Name,Village,LG,Is Farmer Certified,Status');
INSERT INTO `locale_property` VALUES (null, 'FarmerLISTExportHeadingNoBranch', 'fr', 'Code du paysan, Nom du fermier, Nom du père, Village, LG, Certificat du fermier, Statut');
INSERT INTO `locale_property` VALUES (null, 'ExportHeadingNoBranch', 'en', 'Farmer,Gender,Age,Village,Farm,Total Land(Acres),Planting Area(Acres),Land Status,Crop Category,Crop');
INSERT INTO `locale_property` VALUES (null, 'ExportHeading', 'en', 'Organization,Farmer,Gender,Age,Village,Farm,Total Land(Acres),Planting Area(Acres),Land Status,Crop Category,Crop');
-- -1-08-2017 - --
INSERT INTO `locale_property` VALUES (null, 'exportFarmerListColumnHeader', 'en', 'Farmer,Gender,Age,Village,Farm,Total Land(Acres),Planting Area(Acres),Land Status,Crop Category,Crop');


-- 10Aug20147
SET @ids = (SELECT id FROM ese_menu WHERE NAME='service');

INSERT INTO `ese_menu` VALUES (null, 'service.farmerIncome', 'Farmer Income', 'farmerIncomeReport_list.action?type=service', '11', @ids, '0', '0','0','0',null);

INSERT INTO `ese_ent` VALUES (null, 'service.farmerIncome.list');
INSERT INTO `ese_ent` VALUES (null, 'service.farmerIncome.update');


INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.farmerIncome.list'));
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.farmerIncome.update'));

INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.farmerIncome'), '1');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.farmerIncome'), '3');

INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='service.farmerIncome'), '1');

-- - 07SEPT2017 - --alterUPDATE `locale_property` SET `lang_value`='S.no,Date,Farmer Training Code,Mobile User,Group,Remarks' WHERE (`code`='trainingcompletionStatusReportColumnHeaders' and `lang_code`='en');
UPDATE `locale_property` SET `lang_value`='S.no,Date,Farmer Training Code,Mobile User,Group,Remarks' WHERE (`code`='trainingcompletionStatusReportColumnHeaders' and `lang_code`='en');
UPDATE `locale_property` SET `lang_value`='S.no,Organization,Date,Farmer Training Code,Mobile User,Group,Remarks' WHERE (`code`='trainingcompletionStatusReportColumnHeadersBranch' and `lang_code`='en');

INSERT INTO `locale_property`  VALUES (null, 'print.sellingPrice', 'en', 'Cost Price');

-- 29NOV2017
INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `DIMENSION`, `PRIORITY`, `ICON_CLASS`) VALUES (NULL, 'CreationToolGrid', 'creation Tool Grid', 'creationTool_grid.action', '12', '145', '0', '0', '0', '0', NULL);
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='CreationToolGrid'), '1');
INSERT INTO `eses_agro`.`ese_ent` (`ID`, `NAME`) VALUES (NULL, 'CreationToolGrid');
INSERT INTO `ese_role_ent` VALUES ('1', (SELECT id FROM ese_ent WHERE NAME='CreationToolGrid'));
INSERT INTO `ese_role_menu` VALUES (NULL, (SELECT MAX(id) FROM ese_menu), '1');

-- -05-01-2018

INSERT INTO ese_menu VALUES (NULL, 'admin.forecast', 'ForeCast', 'forecast_list.action', '8', '5', '0', '0', '0', '0', NULL);
INSERT INTO ese_menu_action VALUES ((SELECT id FROM ese_menu WHERE NAME='admin.forecast'), '1');
INSERT INTO `ese_ent` VALUES (null, 'admin.forecast.list');
INSERT INTO ese_role_ent (ROLE_ID, ENT_ID) VALUES ('1', (SELECT id FROM ese_ent WHERE NAME='admin.forecast.list'));
INSERT INTO ese_role_menu VALUES (NULL, (SELECT id FROM ese_menu WHERE NAME='admin.forecast'), '1');

-- -18-02-2019

ALTER TABLE `dynamic_report_config_detail` 
ADD COLUMN `IS_GROUP_HEADER` varchar(255) NULL DEFAULT NULL AFTER `IS_FOOTER_SUM`;


set FOREIGN_KEY_CHECKS=0;
SET @ids = (SELECT id FROM ese_menu WHERE NAME='service.coldStorageEntryService');
DELETE FROM ese_role_menu WHERE menu_id=@ids;
DELETE FROM ese_role_ent WHERE ent_id=(select id from ese_ent WHERE NAME='service.coldStorageEntryService.list');
DELETE FROM ese_role_ent WHERE ent_id=(select id from ese_ent WHERE NAME='service.coldStorageEntryService.create');
DELETE FROM ese_role_ent WHERE ent_id=(select id from ese_ent WHERE NAME='service.coldStorageEntryService.update');

DELETE FROM ese_ent WHERE NAME='service.coldStorageEntryService.list';
DELETE FROM ese_ent WHERE NAME='service.coldStorageEntryService.create';
DELETE FROM ese_ent WHERE NAME='service.coldStorageEntryService.update';

DELETE FROM ese_menu_action WHERE menu_id=@ids;  
DELETE FROM ese_menu WHERE id=@ids; 
-- -30-10-2019




set @parent_id = 3;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`) VALUES (NULL, 'service.ndviInfo', 'NDVI INFO', 'ndvi_list.action', @menu_order, @parent_id, '0', '0');

SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`(`ID`, `MENU_ID`, `ROLE_ID`) VALUES (NULL, @max_menu_id, 1);

INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');


INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.ndvi.list');

INSERT INTO `ese_role_ent`(`ROLE_ID`, `ENT_ID`) VALUES (1, (SELECT ID FROM ese_ent WHERE ese_ent.NAME = 'service.ndvi.list'));

