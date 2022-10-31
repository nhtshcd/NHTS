-- - 08-09-16 - --
INSERT INTO `eses_agro`.`ese_seq` (`ID`, `SEQ_KEY`, `SEQ_VAL`) VALUES (NULL, 'GRAMPANCHAYAT_CODE_SEQ', '0');

-- - 08-16-16 - --
UPDATE ese_menu set PARENT_ID='5' WHERE name='profile.samithi';

-- -19-08-2016 - --
ALTER TABLE `periodic_inspection`
MODIFY COLUMN `SURVIVAL_PERCENTATGE`  text NULL COMMENT 'SURVIVAL PERCENTAGE' AFTER `VOICE_RECORDING`;

-- 22-08-2016
ALTER TABLE `periodic_inspection`
ADD COLUMN `CROP_ROTATION`  varchar(4) NULL AFTER `DISEASE_SYMPTOMS_NAME`;

INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('profile.location.state', 'en ', 'Division');
INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('profile.location.municipality', 'en', 'Upozilla');
INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('info.state', 'en', 'Division Informations');
INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('StateAction.breadCrumb', 'en', 'Preferences~#,Location~#,Division~state_list.action');
INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('state.name', 'en', 'Division');
INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('MunicipalityAction.breadCumb', 'en', 'Preferences~#,Location~#,Upozilla~municipality_list.action');
INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('info.municipality', 'en', 'Upozilla Informations');
INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('city.name', 'en', 'Upozilla');

-- -23-08-2016
ALTER TABLE `periodic_inspection`
ADD COLUMN `TEMP`  varchar(25) NULL AFTER `DISEASE_SYMPTOMS_NAME`,
ADD COLUMN `HUMIDITY`  varchar(25) NULL AFTER `TEMP`,
ADD COLUMN `RAIN`  varchar(25) NULL AFTER `HUMIDITY`,
ADD COLUMN `WIND_SPEED`  varchar(25) NULL AFTER `RAIN`;

INSERT INTO `locale_property VALUES (null, 'availableWarehouse', 'en', 'Available Service Point');
INSERT INTO `locale_property VALUES (null, 'selectedWarehouse', 'en', 'Selected Service Point');
INSERT INTO `locale_property VALUES (null, 'availableWarehouse', 'fr', 'Disponible Service Point');
INSERT INTO `locale_property VALUES (null, 'selectedWarehouse', 'fr', 'Sélectionné Service Point');


-- -26-08-2016
ALTER TABLE `procurement`
ADD COLUMN `SEASON_CODE`  varchar(25) NULL AFTER `MASTER_TYPE_ID`;


ALTER TABLE `pmt`
ADD COLUMN `SEASON_CODE`  varchar(25) NULL AFTER `TRN_TYPE`;

ALTER TABLE `warehouse_stock_return`
ADD COLUMN `SEASON_CODE`  varchar(25) NULL AFTER `REVISION_NO`;

ALTER TABLE `offline_procurement`
ADD COLUMN `SEASON_CODE`  varchar(25) NULL AFTER `PAYMENT_MODE`;

ALTER TABLE `mtnt`
ADD COLUMN `SEASON_CODE`  varchar(25) NULL AFTER `DRIVER_ID`;

ALTER TABLE `offline_mtnt`
ADD COLUMN `SEASON_CODE`  varchar(25) NULL AFTER `STATUS_MSG`;

-- -29-08-2016 Srinivas
INSERT INTO `locale_property VALUES (null, 'empty.city', 'en', 'Please Select Union');

-- -29-08-2016 Season Changes 
ALTER TABLE `farmer`
ADD COLUMN `SEASON_CODE`  varchar(25) NULL AFTER `ICS_UNIT_NO`;

-- -29-08-2016 Srinivas
SET @ids = (SELECT `id` FROM `locale_property` WHERE `code`='empty.city');
UPDATE `locale_property` SET `id`=@ids, `code`='empty.city', `lang_code`='en', `lang_value`='Please Select Upozilla' WHERE (`id`=@ids);

-- -30-08-2016
ALTER TABLE `periodic_inspection`
ADD COLUMN `SEASON_CODE`  varchar(25) NULL AFTER `BRANCH_ID`;

-- -30-08-2016 Srinivas
INSERT INTO `locale_property VALUES (null, 'gramPanchayat.notfound', 'en', 'Please Select Union Name');
INSERT INTO `locale_property VALUES (null, 'empty.gramPanchayat', 'en', 'Please Select Union Name');

-- -31-08-2016

ALTER TABLE locale_property MODIFY COLUMN lang_value text;
INSERT INTO `locale_property VALUES (null, 'taluk', 'en', 'Upozilla');
INSERT INTO `locale_property VALUES (null, 'taluk', 'fr', 'Upozilla');
INSERT INTO `locale_property VALUES (null, 'state', 'en', 'Division');
INSERT INTO `locale_property VALUES (null, 'state', 'fr', 'Division');
INSERT INTO `locale_property VALUES (null, 'exportColumnHeader', 'en', 'Organization,Farmer Code, Name Of Farmer,Father Name, Name of ICS, Year of Joining ICS, Village,Upozilla , District,Division ,Crop, Area in Acres, Crop Type, InterCrop Detail, Estimated Crop Yield (Kg), Actual Harvested (Kg),  Per Acre Yield (Kg)\r\nOrganization,Farmer Code, Name Of Farmer,Father Name, Name of ICS, Year of Joining ICS, Village,Upozilla , District,Division ,Crop, Area in Acres, Crop Type, InterCrop Detail, Estimated Crop Yield (Kg), Actual Harvested (Kg),  Per Acre Yield (Kg)\r\n');
INSERT INTO `locale_property VALUES (null, 'exportColumnHeader', 'fr', 'Organisation , Code Farmer, Name Of Farmer, Nom de l\ ICS , ICS Année de ralliement , Village , Upozilla , district , Division , des cultures, superficie en acres , des types de cultures , détail INTERCROP , estimée Crop Yield ( Kg ) , Actual Récolté ( Kg ) , Per Acre Rendement ( Kg )\r\n');

-- -31-08-2016- --
INSERT INTO `locale_property VALUES (null, 'panchayat.name', 'en', 'Union');
INSERT INTO `locale_property VALUES (null, 'panchayat.name', 'fr', 'syndicat');

-- -02-09-2016
INSERT INTO `locale_property VALUES (null, 'empty.state', 'en', 'Please select Division');
INSERT INTO `locale_property VALUES (null, 'empty.state', 'fr', 'Sil vous plaît sélectionner Division');

-- -08-09-2016
UPDATE ese_seq SET SEQ_VAL = (SELECT MAX(ID) FROM sub_category) WHERE SEQ_KEY = 'FARM_PRODUCT_ID_SEQ';

-- -09-09-2016
INSERT INTO `locale_property VALUES (null, 'taluk.name', 'en', 'Upozilla');
INSERT INTO `locale_property VALUES (null, 'taluk.name', 'fr', 'Upozilla');

-- -15-09-2016
INSERT INTO `locale_property VALUES (null, 'empty.samithi', 'en', 'Please Select Service Point');
INSERT INTO `locale_property VALUES (null, 'empty.samithi', 'fr', 'S\il vous plaît Sélectionnez Service Point');

-- -20-09-2016
INSERT INTO `locale_property VALUES (null, 'farmerCode', 'en', 'Farmer Code')

-- - 21-09-2016

ALTER TABLE `farmer_soil_testing`
CHANGE COLUMN `TAKE_ACTION` `TAKEN_ACTION`  varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `OFFICERS_SUGGESTION`;

ALTER TABLE `farm_crops`
MODIFY COLUMN `SEED_QTY_USED`  double(12,2) NULL DEFAULT 0.0 AFTER `STAPLE_LENGTH`;

-- -01-10-2016
ALTER TABLE `procurement_detail`
ADD COLUMN `DRY_LOSS`  double(20,4) NULL AFTER `PROCUREMENT_ID`,
ADD COLUMN `GRADING_LOSS`  double(20,4) NULL AFTER `DRY_LOSS`;

ALTER TABLE `city_warehouse`
ADD COLUMN `FARMER_ID`  bigint(20) NULL AFTER `BRANCH_ID`;

ALTER TABLE `city_warehouse`
MODIFY COLUMN `FARMER_ID`  bigint(20) NULL DEFAULT NULL AFTER `BRANCH_ID`;

ALTER TABLE `procurement_grade`
MODIFY COLUMN `PRICE`  double(20,2) NULL DEFAULT 0 AFTER `CODE`;

ALTER TABLE `procurement`
ADD COLUMN `FARMER_ID`  bigint(20) NULL AFTER `SEASON_CODE`;

ALTER TABLE `procurement_detail`
MODIFY COLUMN `SUB_TOTAL`  double(20,3) NULL DEFAULT NULL AFTER `RATE_PER_UNIT`;


-- - 05-10-2016 Start

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES ('47', 'Marital Status', '47', 'lalteer', '1', '1');

-- - 05-10-2016 End
-- -14-10-2016 Product transfer Menu

UPDATE ese_menu SET ID='102', NAME='service.farmerProductTransfer', DES='ProcurementTransfer', URL='farmerProductTransfer_create.action', ORD='12', PARENT_ID='3', FILTER='0', EXPORT_AVILABILITY='0', DIMENSION='0', PRIORITY='0', ICON_CLASS='' WHERE (ID='102');


UPDATE ese_ent SET ID='248', NAME='service.farmerProductTransfer.list' WHERE (ID='248');
UPDATE ese_ent SET ID='249', NAME='service.farmerProductTransfer.create' WHERE (ID='249');
UPDATE ese_ent SET ID='250', NAME='service.farmerProductTransfer.update' WHERE (ID='250');

-- -20-10-2016

INSERT INTO `eses_lalteer`.`locale_property VALUES (null, 'empty.grossWeight', 'en', 'Please Enter Net Weight');
INSERT INTO `eses_lalteer`.`locale_property VALUES (null, 'empty.grossWeight', 'fr', 'S`il vous plaît Entrez Poids net');
INSERT INTO `eses_lalteer`.`locale_property VALUES (null, 'invalid.grossWeight', 'en', 'Please enter valid Net Weight (Numbers only)');
INSERT INTO `eses_lalteer`.`locale_property VALUES (null, 'invalid.grossWeight', 'fr', 'S`il vous plaît entrer valides Poids net (chiffres uniquement)');
INSERT INTO `eses_lalteer`.`locale_property VALUES (null, 'invalidNetWeightCalculations', 'en', 'Net Gross Weight Should be greater than Loss');
INSERT INTO `eses_lalteer`.`locale_property VALUES (null, 'invalidNetWeightCalculations', 'fr', 'Net Poids brut devrait être supérieure à la perte');


-- - 25-10-2016 - --

INSERT INTO `locale_property VALUES (null, 'taluk', 'en', 'Upozilla');
INSERT INTO `locale_property VALUES (null, 'taluk', 'fr', 'Upozilla');

INSERT INTO `locale_property VALUES (null, 'invalidNetWeightCalculation', 'en', 'Loss Should be less than Gross weight');
INSERT INTO `locale_property VALUES (null, 'invalidNetWeightCalculation', 'fr', 'Perte doit être inférieure Poids brut');
-- -26-10-2016

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (49, 'Pest Name', 49, 'lalteerqa', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (50, 'Pest Symptoms', 50, 'lalteerqa', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (51, 'Disease Name', 51, 'lalteerqa', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (52, 'Disease Symptom', 52, 'lalteerqa', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (53, 'Inspection Crop', 53, 'lalteerqa', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (54, 'Crop Growth', 54, 'lalteerqa', '1', '1');

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (55, 'Interplough Type', 55, 'lalteerqa', '1', '1');

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (56, 'Usage Level', 56, 'lalteerqa', '1', '1');

INSERT INTO `locale_property VALUES (null, 'pesticideRecommended', 'en', 'Pesticide Recommended');
INSERT INTO `locale_property VALUES (null, 'fungicideRecommended', 'en', 'Fungicide Recommended');

-- - 05 JAN 2017

INSERT INTO locale_property VALUES (NULL, 'agent.samithi', 'en', 'Service Point');

-- - 09 FEB 2017
-- - Service Point Activity Report
SET @ids = (SELECT id FROM ese_menu WHERE NAME='report');

INSERT INTO ese_menu VALUES (NULL, 'report.samithiAccessReport', 'Service Point Access Report', 'samithiAccessReport_list.action', '18',  @ids, '0', '0', '0', '0', NULL);

INSERT INTO ese_ent VALUES (NULL, 'report.samithiAccessReport.list');

INSERT INTO ese_role_ent VALUES ('1', (SELECT id FROM ese_ent WHERE NAME='report.samithiAccessReport.list'));
INSERT INTO ese_role_ent VALUES ('24', (SELECT id FROM ese_ent WHERE NAME='report.samithiAccessReport.list'));

INSERT INTO ese_role_menu VALUES (NULL, (SELECT id FROM ese_menu WHERE NAME='report.samithiAccessReport'), '1');
INSERT INTO ese_role_menu VALUES (NULL, (SELECT id FROM ese_menu WHERE NAME='report.samithiAccessReport'), '24');


INSERT INTO ese_menu_action VALUES ((SELECT id FROM ese_menu WHERE NAME='report.samithiAccessReport'), '1');


INSERT INTO locale_property VALUES (NULL, 'samithiName', 'en', 'Service Point');
INSERT INTO locale_property VALUES (NULL, 'report.samithiAccessReport', 'en', 'Service Point Access Report');
INSERT INTO locale_property VALUES (NULL, 'SamithiAccessReportAction.breadCrumb', 'en', 'Report~#,Service Point Activity Report~samithiAccessReport_list.action');
