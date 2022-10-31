-- -20-10-2016

SET @ids = (SELECT id FROM ese_menu WHERE NAME='profile');

INSERT INTO `ese_menu` VALUES (null, 'profile.samithi.bci', 'Harvest Season', 'samithi_list.action', '5', @ids, '0', '0','0','0',null);

INSERT INTO `ese_ent` VALUES (null, 'profile.samithi.bci.list');
INSERT INTO `ese_ent` VALUES (null, 'profile.samithi.bci.create');
INSERT INTO `ese_ent` VALUES (null, 'profile.samithi.bci.update');
INSERT INTO `ese_ent` VALUES (null, 'profile.samithi.bci.delete');


INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='profile.samithi.bci.list'));
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='profile.samithi.bci.create'));
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='profile.samithi.bci.update'));
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='profile.samithi.bci.delete'));

INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='profile.samithi.bci'), '1');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='profile.samithi.bci'), '2');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='profile.samithi.bci'), '3');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='profile.samithi.bci'), '4');

INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='profile.samithi.bci'), '1');


INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.samithi.bci', 'en', 'LG');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.samithi.bci', 'fr', 'LG');

-- -26-10-2016
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (49, 'Pest Name', 49, 'pratibha', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (50, 'Pest Symptoms', 50, 'pratibha', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (51, 'Disease Name', 51, 'pratibha', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (52, 'Disease Symptom', 52, 'pratibha', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (53, 'Inspection Crop', 53, 'pratibha', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (54, 'Crop Growth', 54, 'pratibha', '1', '1');

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (55, 'Interplough Type', 55, 'pratibha', '1', '1');

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (56, 'Usage Level', 56, 'pratibha', '1', '1');

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (57, 'Weeding', 57, 'pratibha', '1', '1');


INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (58, 'Picking', 58, 'pratibha', '1', '1');

-- -9-11-2016

update farm_catalogue set `STATUS` = 0 where CATALOGUE_TYPEZ=56;
update catalogue_value set `IS_RESERVED` = 1 where TYPEZ=56;
update farm_catalogue set `STATUS` = 0 where CATALOGUE_TYPEZ=57;
update catalogue_value set `IS_RESERVED` = 1 where TYPEZ=57;

update farm_catalogue set `STATUS` = 0 where CATALOGUE_TYPEZ=58;
update catalogue_value set `IS_RESERVED` = 1 where TYPEZ=58;

delete from catalogue_value where typez=56;

delete from catalogue_value where typez=57;
delete from catalogue_value where typez=58;
set @max = (select max(revision_no)+1 from catalogue_value);
set @max_cat = (select CONCAT('CG',lpad((SUBSTR(max(CODE) FROM 3 FOR LENGTH(max(CODE)))+1),5,'0')) from catalogue_value);
set @tenant = select val from pref where name='TENANT_ID';
INSERT INTO `catalogue_value` (`ID`, `CODE`, `NAME`, `TYPEZ`, `REVISION_NO`, `BRANCH_ID`, `IS_RESERVED`, `PARENT_ID`) VALUES (null, 'CG00151', 'First time', '56', @max, @tenant, '1', NULL);
set @max_cat = (select CONCAT('CG',lpad((SUBSTR(max(CODE) FROM 3 FOR LENGTH(max(CODE)))+1),5,'0')) from catalogue_value);
INSERT INTO `catalogue_value` (`ID`, `CODE`, `NAME`, `TYPEZ`, `REVISION_NO`, `BRANCH_ID`, `IS_RESERVED`, `PARENT_ID`) VALUES (null, 'CG00152', 'Second Time', '56', @max, @tenant, '1', NULL);
set @max_cat = (select CONCAT('CG',lpad((SUBSTR(max(CODE) FROM 3 FOR LENGTH(max(CODE)))+1),5,'0')) from catalogue_value);
INSERT INTO `catalogue_value` (`ID`, `CODE`, `NAME`, `TYPEZ`, `REVISION_NO`, `BRANCH_ID`, `IS_RESERVED`, `PARENT_ID`) VALUES (null, 'CG00153', 'Third Time', '56', @max, @tenant, '1', NULL);
set @max_cat = (select CONCAT('CG',lpad((SUBSTR(max(CODE) FROM 3 FOR LENGTH(max(CODE)))+1),5,'0')) from catalogue_value);
INSERT INTO `catalogue_value` (`ID`, `CODE`, `NAME`, `TYPEZ`, `REVISION_NO`, `BRANCH_ID`, `IS_RESERVED`, `PARENT_ID`) VALUES (null, 'CG00154', 'Morethan three times', '56', @max, @tenant, '1', NULL);
set @max_cat = (select CONCAT('CG',lpad((SUBSTR(max(CODE) FROM 3 FOR LENGTH(max(CODE)))+1),5,'0')) from catalogue_value);



INSERT INTO `catalogue_value` (`ID`, `CODE`, `NAME`, `TYPEZ`, `REVISION_NO`, `BRANCH_ID`, `IS_RESERVED`, `PARENT_ID`) VALUES (null,  @max_cat, 'First time', '57', @max, @tenant, '1', NULL);
set @max_cat = (select CONCAT('CG',lpad((SUBSTR(max(CODE) FROM 3 FOR LENGTH(max(CODE)))+1),5,'0')) from catalogue_value);
INSERT INTO `catalogue_value` (`ID`, `CODE`, `NAME`, `TYPEZ`, `REVISION_NO`, `BRANCH_ID`, `IS_RESERVED`, `PARENT_ID`) VALUES (null,  @max_cat, 'Second Time', '57', @max, @tenant, '1', NULL);
set @max_cat = (select CONCAT('CG',lpad((SUBSTR(max(CODE) FROM 3 FOR LENGTH(max(CODE)))+1),5,'0')) from catalogue_value);
INSERT INTO `catalogue_value` (`ID`, `CODE`, `NAME`, `TYPEZ`, `REVISION_NO`, `BRANCH_ID`, `IS_RESERVED`, `PARENT_ID`) VALUES (null,  @max_cat, 'Third Time', '57', @max, @tenant, '1', NULL);


INSERT INTO `catalogue_value` (`ID`, `CODE`, `NAME`, `TYPEZ`, `REVISION_NO`, `BRANCH_ID`, `IS_RESERVED`, `PARENT_ID`) VALUES (null,  @max_cat, 'First time', '58', @max, @tenant, '1', NULL);
set @max_cat = (select CONCAT('CG',lpad((SUBSTR(max(CODE) FROM 3 FOR LENGTH(max(CODE)))+1),5,'0')) from catalogue_value);
INSERT INTO `catalogue_value` (`ID`, `CODE`, `NAME`, `TYPEZ`, `REVISION_NO`, `BRANCH_ID`, `IS_RESERVED`, `PARENT_ID`) VALUES (null,  @max_cat, 'Second Time', '58', @max, @tenant, '1', NULL);
set @max_cat = (select CONCAT('CG',lpad((SUBSTR(max(CODE) FROM 3 FOR LENGTH(max(CODE)))+1),5,'0')) from catalogue_value);
INSERT INTO `catalogue_value` (`ID`, `CODE`, `NAME`, `TYPEZ`, `REVISION_NO`, `BRANCH_ID`, `IS_RESERVED`, `PARENT_ID`) VALUES (null,  @max_cat, 'Third Time', '58', @max, @tenant, '1', NULL);
set @max_cat = (select SUBSTR(max(CODE) FROM 3 FOR LENGTH(max(CODE)))+1 from catalogue_value);
update ese_seq set SEQ_VAL = @max_cat where SEQ_KEY='CATALOGUE_ID_SEQ';

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'pesticideRecommended', 'en', 'Pesticide Recommended');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'fungicideRecommended', 'en', 'Fungicide Recommended');


--01-12-2016

INSERT INTO ese_menu values(NULL, 'report.pesticideUsageReport', 'Pesticide Usage Report', 'pesticideUsageReport_list.action','18',4,'0','0','0','0','');
INSERT INTO ese_ent VALUES(NULL,'report.pesticideUsageReport.list');
INSERT INTO ese_role_ent VALUES('1',(SELECT id FROM ese_ent WHERE NAME='report.pesticideUsageReport.list'));
INSERT INTO ese_role_ent VALUES('2',(SELECT id FROM ese_ent WHERE NAME='report.pesticideUsageReport.list'));
INSERT INTO ese_menu_action VALUES((SELECT id FROM ese_menu WHERE NAME='report.pesticideUsageReport'),'1');
INSERT INTO ese_role_menu VALUES(NULL,(SELECT id FROM ese_menu WHERE NAME='report.pesticideUsageReport'),'1');

INSERT INTO ese_menu values(NULL, 'report.bciFieldMonitoringReport', 'BCI Field Monitoring Report', 'bciFieldMonitoringReport_list.action','19',4,'0','0','0','0','');
INSERT INTO ese_ent VALUES(NULL,'report.bciFieldMonitoringReport.list');
INSERT INTO ese_role_ent VALUES('1',(SELECT id FROM ese_ent WHERE NAME='report.bciFieldMonitoringReport.list'));
INSERT INTO ese_role_ent VALUES('2',(SELECT id FROM ese_ent WHERE NAME='report.bciFieldMonitoringReport.list'));
INSERT INTO ese_menu_action VALUES((SELECT id FROM ese_menu WHERE NAME='report.bciFieldMonitoringReport'),'1');
INSERT INTO ese_role_menu VALUES(NULL,(SELECT id FROM ese_menu WHERE NAME='report.bciFieldMonitoringReport'),'1');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'SamithiAction.breadCrumb', 'en', 'Preferences~#,LG~samithi_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'SamithiAction.breadCrumb', 'fr', 'Préférences~#,LG~samithi_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'agent.samithi', 'en', 'LG');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'agent.samithi', 'fr', 'LG');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'selectedWarehouse', 'en', 'Selected LG');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'selectedWarehouse', 'fr', 'LG sélectionné');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'availableWarehouse', 'en', 'Disponible LG');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'availableWarehouse', 'en', 'Available LG');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'empty.samithi', 'en', 'Please select LG');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'empty.samithi', 'fr', 'Veuillez sélectionner LG');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.samithi', 'en', 'LG');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.samithi', 'fr', 'LG');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'learningGroupCode', 'en', 'LG Name');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'learningGroupCode', 'fr', 'LG Nom');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'learningGroupCodeOffline', 'en', 'LG Name');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'learningGroupCodeOffline', 'fr', 'LG Nom');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'city.name', 'en', 'Taluk');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'city.name', 'fr', 'Secteure');


INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('info.ics', 'en', 'Conversion Status');
INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('info.ics', 'fr', '\r\nÉtat de la conversion');

-- -03FEB2017 - --
set FOREIGN_KEY_CHECKS=0;
SET @ids = (SELECT id FROM ese_menu WHERE NAME='admin.masterData');
-- SET @idz = (SELECT id FROM ese_menu WHERE NAME='report.cropYield.crop');
DELETE FROM ese_role_menu WHERE menu_id=@ids;
-- DELETE FROM ese_role_menu WHERE menu_id=@idz;
 
DELETE FROM ese_role_ent WHERE ent_id=(select id from ese_ent WHERE NAME='admin.masterData.list');
DELETE FROM ese_role_ent WHERE ent_id=(select id from ese_ent WHERE NAME='admin.masterData.create');
DELETE FROM ese_role_ent WHERE ent_id=(select id from ese_ent WHERE NAME='admin.masterData.update');
DELETE FROM ese_role_ent WHERE ent_id=(select id from ese_ent WHERE NAME='admin.masterData.delete');


DELETE FROM ese_ent WHERE NAME='admin.masterData.list';
DELETE FROM ese_ent WHERE NAME='admin.masterData.create';
DELETE FROM ese_ent WHERE NAME='admin.masterData.update';
DELETE FROM ese_ent WHERE NAME='admin.masterData.delete';


DELETE FROM ese_menu_action WHERE menu_id=@ids; 
 -- DELETE FROM ese_menu_action WHERE menu_id=@idz; 
 DELETE FROM ese_menu WHERE id=@ids; 
-- DELETE FROM ese_menu WHERE id=@idz; 




set FOREIGN_KEY_CHECKS=0;
SET @ids = (SELECT id FROM ese_menu WHERE NAME='profile.branchMaster');
-- SET @idz = (SELECT id FROM ese_menu WHERE NAME='report.cropYield.crop');
DELETE FROM ese_role_menu WHERE menu_id=@ids;
-- DELETE FROM ese_role_menu WHERE menu_id=@idz;
 
DELETE FROM ese_role_ent WHERE ent_id=(select id from ese_ent WHERE NAME='profile.branchMaster.list');
DELETE FROM ese_role_ent WHERE ent_id=(select id from ese_ent WHERE NAME='profile.branchMaster.create');
DELETE FROM ese_role_ent WHERE ent_id=(select id from ese_ent WHERE NAME='profile.branchMaster.update');
DELETE FROM ese_role_ent WHERE ent_id=(select id from ese_ent WHERE NAME='profile.branchMaster.delete');


DELETE FROM ese_ent WHERE NAME='profile.branchMaster.list';
DELETE FROM ese_ent WHERE NAME='profile.branchMaster.create';
DELETE FROM ese_ent WHERE NAME='profile.branchMaster.update';
DELETE FROM ese_ent WHERE NAME='profile.branchMaster.delete';


DELETE FROM ese_menu_action WHERE menu_id=@ids; 
 -- DELETE FROM ese_menu_action WHERE menu_id=@idz; 
 DELETE FROM ese_menu WHERE id=@ids; 
-- DELETE FROM ese_menu WHERE id=@idz;
use this to delete menu
set FOREIGN_KEY_CHECKS=0;
SET @ids = (SELECT id FROM ese_menu WHERE NAME='report.cropYield');
SET @idz = (SELECT id FROM ese_menu WHERE NAME='report.cropYield.village');
DELETE FROM ese_role_menu WHERE menu_id=@ids;
-- DELETE FROM ese_role_menu WHERE menu_id=@idz;
DELETE FROM ese_role_ent WHERE ent_id=(select id from ese_ent WHERE NAME='report.cropYield.village.list');
DELETE FROM ese_ent WHERE NAME='report.cropYield.village.list';
DELETE FROM ese_menu_action WHERE menu_id=@ids; 
-- DELETE FROM ese_menu_action WHERE menu_id=@idz; 
DELETE FROM ese_menu WHERE id=@ids; 
-- DELETE FROM ese_menu WHERE id=@idz; 

set FOREIGN_KEY_CHECKS=0;
SET @ids = (SELECT id FROM ese_menu WHERE NAME='report.cropYield');
SET @idz = (SELECT id FROM ese_menu WHERE NAME='report.cropYield.crop');
DELETE FROM ese_role_menu WHERE menu_id=@ids;
-- DELETE FROM ese_role_menu WHERE menu_id=@idz;
DELETE FROM ese_role_ent WHERE ent_id=(select id from ese_ent WHERE NAME='report.cropYield.crop.list');
DELETE FROM ese_ent WHERE NAME='report.cropYield.crop.list';
DELETE FROM ese_menu_action WHERE menu_id=@ids; 
-- DELETE FROM ese_menu_action WHERE menu_id=@idz; 
DELETE FROM ese_menu WHERE id=@ids; 
-- DELETE FROM ese_menu WHERE id=@idz;
delete crop yield report
SET @ids = (SELECT id FROM ese_menu WHERE NAME='report.balanceSheet');
update ese_menu set PARENT_ID=@ids where (`NAME`='report.fieldStaffBalanceReport');

-- Enable Fair Trade
INSERT INTO `ese_menu`  VALUES (NULL, 'report.farmerInspectionReport.fairTrade', 'Farmer Inspection Report', 'farmerInspectionReport_list.action', '1', '45', '0', '0', '0', '0', NULL);
INSERT INTO `ese_ent`  VALUES (NULL, 'report.farmerInspectionReport.fairTrade.list');
INSERT INTO `ese_menu_action`  VALUES ((SELECT id FROM ese_menu WHERE NAME='report.farmerInspectionReport.fairTrade'), '1');
INSERT INTO `ese_role_ent`  VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='report.farmerInspectionReport.fairTrade.list'));
INSERT INTO `ese_role_menu`  VALUES (NULL,(SELECT MAX(id) FROM ese_menu), '1');

-- -26-07-2017 - --
INSERT INTO `locale_property` VALUES (NULL, 'paymentInfo', 'en', 'Payment Amount(INR)');
INSERT INTO `locale_property` VALUES (NULL, 'paymentInfo', 'fr', 'Montant du paiement (INR)');
INSERT INTO `farmer_field` VALUES (null, 'farmerHealthInfo', '4', 'farmerHealthInfo', NULL, '0', NULL, NULL);
INSERT INTO `farmer_field` VALUES (null, 'social_info', '4', 'social_info', NULL, '0', NULL, NULL);
INSERT INTO `farmer_field` VALUES (null, 'Self Asses', '4', 'self_Assesment', NULL, '0', NULL, NULL);

-- -31-07-2017 --
INSERT INTO `locale_property` VALUES (null, 'FarmerLISTExportHeading', 'en', 'Organization,Farmer Code,Farmer Name,Father Name,Village,LG,Is Farmer Certified,Status');
INSERT INTO `locale_property` VALUES (null, 'FarmerLISTExportHeading', 'fr', 'Organisation, Code du paysan, Nom du fermier, Nom du père, Village, LG, Certificat du fermier, Statut');
INSERT INTO `locale_property` VALUES (null, 'FarmerLISTExportHeadingNoBranch', 'en', 'Farmer Code,Farmer Name,Father Name,Village,LG,Is Farmer Certified,Status');
INSERT INTO `locale_property` VALUES (null, 'FarmerLISTExportHeadingNoBranch', 'fr', 'Code du paysan, Nom du fermier, Nom du père, Village, LG, Certificat du fermier, Statut');

-- -02-08-2017 --
INSERT INTO `locale_property` VALUES (null, 'farmingTotalLand', 'en', 'Total Farm Area');
INSERT INTO `locale_property` VALUES (null, 'farmingTotalLand', 'fr', 'Superficie totale de la ferme');
INSERT INTO `locale_property` VALUES (null, 'totalFarms', 'en', 'Total Farms');
INSERT INTO `locale_property` VALUES (null, 'totalFarms', 'fr', 'Total des fermes');
INSERT INTO `locale_property` VALUES (null, 'farm.totalLandHectare', 'en', 'Total Farm Area(Hectare)');
INSERT INTO `locale_property` VALUES (null, 'farm.totalLandHectare', 'fr', 'Superficie totale de la ferme (Hectare)');                              

UPDATE `ese_menu` SET `ORD`='1' WHERE (`NAME`='service.warehouseProduct' and `PARENT_ID`='3');
UPDATE `ese_menu` SET `ORD`='2' WHERE (`NAME`='service.distribution' and `PARENT_ID`='3');
UPDATE `ese_menu` SET `ORD`='3' WHERE (`NAME`='service.procurement' and `PARENT_ID`='3');

INSERT INTO `locale_property`  VALUES (null, 'warehouseStockEntryHeaderBranch', 'en', 'Organization,Date(DD-MM-YYYY),Order No,Warehouse,Receipt No,Client,Total Quantity,Season');
INSERT INTO `locale_property`  VALUES (null, 'warehouseStockEntryHeaderBranch', 'fr', 'Organisation, Date (JJ-MM-AAAA), Numéro de commande, Entrepôt, Numéro de réception, Client, Quantité totale, Saison');
INSERT INTO `locale_property`  VALUES (null, 'warehouseStockEntryBranch', 'en', 'Sub Organization,Organization,Date(DD-MM-YYYY),Order No,Warehouse,Receipt No,Client,Total Quantity,Season');
INSERT INTO `locale_property`  VALUES (null, 'warehouseStockEntryBranch', 'fr', 'Sous-organisation, organisation, date (DD-MM-AAAA), Numéro de commande, entrepôt, no de réception, client, quantité totale, saison');
INSERT INTO `locale_property`  VALUES (null, 'warehouseStockEntryBranchHeader', 'en', 'Organization,Date(DD-MM-YYYY),Order No,Warehouse,Receipt No,Client,Total Quantity,Season');
INSERT INTO `locale_property`  VALUES (null, 'warehouseStockEntryBranchHeader', 'fr', 'Organisation, Date (JJ-MM-AAAA), Numéro de commande, Entrepôt, Numéro de réception, Client, Quantité totale, Saison');
INSERT INTO `locale_property`  VALUES (null, 'warehouseStockEntryExport', 'en', 'Date(DD-MM-YYYY),Order No,Warehouse,Receipt No,Client,Total Quantity,Season');
INSERT INTO `locale_property`  VALUES (null, 'warehouseStockEntryExport', 'fr', 'Date (JJ-MM-AAAA), Numéro de commande, Entrepôt, Numéro de réception, Client, Quantité totale, saison');
INSERT INTO `locale_property`  VALUES (null, 'warehouseStockEntrySub', 'en', 'Crop,Variety,UOM,Parent Code,Packet Info,LotNo,Seed Type,Good Quantity,Total Quantity');
INSERT INTO `locale_property`  VALUES (null, 'warehouseStockEntrySub', 'fr', 'Culture, variété, UOM, code parent, information sur les paquets, lot n °, type de semence, bonne quantité, quantité totale');
INSERT INTO `locale_property` VALUES (null, 'exportColumnHeaderBranchfarmer', 'en', 'Organization,Date,Stock Type,Warehouse,MobileUser,Farmer Type,Farmer,Village,Free Distribution,Total Qty,Gross Amount,Final Amount,Payment Amount,Season');
INSERT INTO `locale_property` VALUES (null, 'exportColumnHeaderBranchfarmer', 'fr', 'Organisation, Date, Type de stock, Entrepôt, MobileUser, Type agriculteur, Agriculteur, Village, Distribution gratuite, Quantité totale, Montant brut, Montant final, Montant de paiement, Saison');
INSERT INTO `locale_property` VALUES (null, 'exportColumnHeaderfarmer', 'en', 'Date,Stock Type,Warehouse,MobileUser,Farmer Type,Farmer,Village,Mobile No,Free Distribution,Total Qty,Gross Amount,Final Amount,Payment Amount,Season');
INSERT INTO `locale_property` VALUES (null, 'exportColumnHeaderfarmer', 'fr', 'Date, Type de stock, Entrepôt, MobileUser, Type de fermier, Agriculteur, Village, Mobile Non, Distribution gratuite, Quantité totale, Montant brut, Montant final, Montant de paiement, Saison');

-- -07-08-2017 --

INSERT INTO `locale_property` VALUES (null, 'exportCultivationColumnHeader', 'en', 'Season,Farmer Code,Farmer,Farm Name,Crop,Village,Cotton Area in Acres,LandPreparation(INR),Sowing(INR),GapFilling(INR),Weeding(INR),InterCultural(INR),Irrigation(INR),Fertilizer(INR),Pesticide(INR),Farm Yard Manure(FYM),Harvesting(INR),OtherExpeness(INR),Labour Cost(INR),Total Coc(INR)');
INSERT INTO `locale_property` VALUES (null, 'exportCultivationColumnHeader', 'fr', 'Saison, Code du paysan, Agriculteur, Nom de la ferme, Culture, Village, Surface du coton dans les acres, LandPreparation (INR), Semis (INR), GapFilling (INR), Weeding (INR), InterCultural (INR), Irrigation (INR), Fertilisant (INR), pesticide (INR), fumier de ferme (FYM), récolte (INR), autre excès (INR), coût du travail (INR), Total Coc (INR)');
INSERT INTO `locale_property` VALUES (null, 'profile.agent', 'en', 'Mobile User Name');
INSERT INTO `locale_property` VALUES (null, 'profile.agent', 'fr', 'Nom d\utilisateur mobile');
INSERT INTO `locale_property` VALUES (null, 'fsCode', 'en', 'Mobile User ID');
INSERT INTO `locale_property` VALUES (null, 'fsCode', 'fr', 'ID utilisateur mobile');
INSERT INTO `locale_property` VALUES (null, 'exportFieldStaffStockColumnHeader', 'en', 'Mobile User ID,Mobile User Name,Farm Product,Product Name,Stock,Season');
INSERT INTO `locale_property` VALUES (null, 'exportFieldStaffStockColumnHeader', 'fr', 'Nom d\utilisateur mobile, Nom d\utilisateur mobile, Produit agricole, Nom du produit, Stock, Saison');
INSERT INTO `locale_property` VALUES (null, 'sowingExportWithCode', 'en', 'Farmer Code,Farmer Name,LG,Village,Crop,Total Area,Estimated Yield(Kg),Season');
INSERT INTO `locale_property` VALUES (null, 'sowingExportWithCode', 'fr', 'Code du paysan, Nom du fermier, LG, Village, Culture, Superficie totale, Rendement estimé (Kg), Saison');
INSERT INTO `locale_property` VALUES (null, 'exportHarvestSubgridHeadings', 'en', 'Crop Type,Crop,Variety,Grade,Unit,Price(INR),Quantity,Quantity(MT),Sale Value (INR)');
INSERT INTO `locale_property` VALUES (null, 'exportHarvestSubgridHeadings', 'fr', 'Type de culture, Culture, Variété, Grade, Unité, Prix (INR), Quantité, Quantité (MT), Valeur de vente (INR)');
INSERT INTO `locale_property` VALUES (null, 'FarmerInspectionReportAction.breadCrumb', 'en', 'Report~#,Organic Inspection Report~#,Fair Trade~farmerInspectionReport_list.action');
INSERT INTO `locale_property` VALUES (null, 'exportFarmerListColumnHeader', 'en', 'Farmer Code,Farmer,Name of the ICS,ICS Code,Gender,Age,Village,Farm,Tracenet Code,Total Land,Planting Area,Land Status,Crop Category,Crop');


-- - 08-16-2017

ALTER TABLE `farm`
MODIFY COLUMN `FARM_PLAT_NO`  varchar(35) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL AFTER `IRRI_LAND`;

INSERT INTO ese_seq VALUES (NULL, 'OBSERVATION_ID_SEQ', '0');

-- - 08-22-2017
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'profile.location.village', 'en', 'Village');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'profile.location.village', 'fr', 'Village');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'title.farmer', 'en', 'Farmer Details');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'title.farmer', 'fr', 'Details du fermier');

-- - 12-05-2017
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'distributiorIdfarmer', 'en', 'Mobile User');

-- - 12-11-2017
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'quantityMT', 'en', 'Quantity (MT)');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'donutChart.totalLandProduction', 'en', 'Total Cultivation Area');

-- - 12-12-2017
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'farmer.childCountSchollMale', 'en', 'Total No. of school going Children (<18 Yrs)');

-- - 03-26-2017 - --

INSERT INTO `locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'ExportMainGridHeadingProcurmentStock', 'en', 'S.No,Warehouse Name,Product Name,No Of Bags,Net Weight');
INSERT INTO `locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'ExportMainGridHeadingProcurmentStockBranch', 'en', 'Organization,S.No,Warehouse Name,Product Name,No Of Bags,Net Weight');


-- -03-05-2018
INSERT INTO `ese_menu`  VALUES (NULL, 'report.mobileUserSummaryReport', 'Mobile User Summary Report', 'mobileUserSummaryReport_create.action', '22', '3', '0', '0', '0', '0', NULL);
INSERT INTO `ese_ent`  VALUES (NULL, 'report.mobileUserSummaryReport.list');
INSERT INTO `ese_menu_action`  VALUES ((SELECT id FROM ese_menu WHERE NAME='report.mobileUserSummaryReport'), '1');
INSERT INTO `ese_role_ent`  VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='report.mobileUserSummaryReport.list'));
INSERT INTO `ese_role_menu`  VALUES (NULL,(SELECT MAX(id) FROM ese_menu), '1');

-- -15-05-2018
INSERT INTO `ese_menu`  VALUES (NULL, 'service.distributionStockTransfer', 'Distribution Stock Transfer', 'distributionStockTransfer_create.action', '23', '3', '0', '0', '0', '0', NULL);
INSERT INTO `ese_ent`  VALUES (NULL, 'service.distributionStockTransfer.list');
INSERT INTO `ese_ent`  VALUES (NULL, 'service.distributionStockTransfer.create');
INSERT INTO `ese_ent`  VALUES (NULL, 'service.distributionStockTransfer.delete');
INSERT INTO `ese_menu_action`  VALUES ((SELECT id FROM ese_menu WHERE NAME='service.distributionStockTransfer'), '1');
INSERT INTO `ese_role_ent`  VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.distributionStockTransfer.list'));
INSERT INTO `ese_role_ent`  VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.distributionStockTransfer.create'));
INSERT INTO `ese_role_ent`  VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.distributionStockTransfer.delete'));
INSERT INTO `ese_role_menu`  VALUES (NULL,(SELECT MAX(id) FROM ese_menu), '1');

INSERT INTO `ese_menu`  VALUES (NULL, 'service.distributionStockReception', 'Distribution Stock Reception', 'distributionStockReception_create.action', '24', '4', '0', '0', '0', '0', NULL);
INSERT INTO `ese_ent`  VALUES (NULL, 'service.distributionStockReception.list');
INSERT INTO `ese_ent`  VALUES (NULL, 'service.distributionStockReception.create');
INSERT INTO `ese_ent`  VALUES (NULL, 'service.distributionStockReception.delete');
INSERT INTO `ese_menu_action`  VALUES ((SELECT id FROM ese_menu WHERE NAME='service.distributionStockReception'), '1');
INSERT INTO `ese_role_ent`  VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.distributionStockReception.list'));
INSERT INTO `ese_role_ent`  VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.distributionStockReception.create'));
INSERT INTO `ese_role_ent`  VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.distributionStockReception.delete'));
INSERT INTO `ese_role_menu`  VALUES (NULL,(SELECT MAX(id) FROM ese_menu), '1');



INSERT INTO `ese_menu` VALUES (NULL , 'report.distributionstocktransfer', 'Distribution Stock Transfer Report', 'distributionStockTransferReport_list.action?type=transfer', '22','4','0','0','0','0',NULL);
INSERT INTO `ese_ent` VALUES (NULL, 'report.distributionstocktransfer.list');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='report.distributionstocktransfer'), '1');
INSERT INTO `ese_role_ent` VALUES ('1', (SELECT id FROM ese_ent WHERE NAME='report.distributionstocktransfer.list'));
INSERT INTO `ese_role_menu` VALUES (NULL, (SELECT MAX(id) FROM ese_menu), '1');


INSERT INTO `ese_menu` VALUES (NULL , 'report.distributionstockreception', 'Distribution Stock Reception Report', 'distributionStockTransferReport_list.action?type=reception', '23','4','0','0','0','0',NULL);
INSERT INTO `ese_ent` VALUES (NULL, 'report.distributionstockreception.list');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='report.distributionstockreception'), '1');
INSERT INTO `ese_role_ent` VALUES ('1', (SELECT id FROM ese_ent WHERE NAME='report.distributionstockreception.list'));
INSERT INTO `ese_role_menu` VALUES (NULL, (SELECT MAX(id) FROM ese_menu), '1');


-- -Mobile User Summary Report
DROP view if EXISTS vw_warehousestock;
CREATE  VIEW `vw_warehousestock` AS select cast(`wpd`.`TXN_TIME` as date) AS `WP_DATE`,`wp`.`AGENT_ID` AS `WP_AGENT_ID`,`wp`.`PRODUCT_ID` AS `WP_PRODUCT_ID`,`wp`.`BRANCH_ID` AS `WP_BRANCH_ID`,sum((case when (`wpd`.`TXN_DESC` like '%PRODUCT RETURN FROM FARMER%') then ifnull(`wpd`.`TXN_STOCK`,0) else 0 end)) AS `RETURN_FROM_FARMER_QTY`,sum((case when (`wpd`.`TXN_DESC` like '%PRODUCT RETURN FROM FIELDSTAFF%') then ifnull(`wpd`.`TXN_STOCK`,0) else 0 end)) AS `RETURN_FROM_AGENT_QTY`,min(`wpd`.`ID`) AS `MIN_id`,max(`wpd`.`ID`) AS `MAX_id` from (`warehouse_product` `wp` join `warehouse_product_detail` `wpd` on((`wp`.`ID` = `wpd`.`WAREHOUSE_PRODUCT_ID`))) group by cast(`wpd`.`TXN_TIME` as date),`wp`.`AGENT_ID`,`wp`.`PRODUCT_ID`,`wp`.`BRANCH_ID`;

DROP view if EXISTS vw_distribution;

CREATE  VIEW `vw_distribution` AS select `d`.`branch_id` AS `BRANCH_ID`,cast(`d`.`TXN_TIME` as date) AS `TXN_DATE`,`p`.`ID` AS `prof_id`,`w`.`CODE` AS `WAREHOUSE_CODE`,`w`.`NAME` AS `WAREHOUSE_NAME`,`d`.`AGENT_ID` AS `AGENT_ID`,`d`.`AGENT_NAME` AS `AGENT_NAME`,`sc`.`NAME` AS `CATEGORY`,`db`.`PRODUCT_ID` AS `PRODUCT_ID`,`pr`.`NAME` AS `PRODUCT`,`pr`.`UNIT` AS `UNIT`,`pr`.`PRICE` AS `UNIT_PRICE`,sum((case when (`d`.`TXN_TYPE` = '514') then ifnull(`db`.`QUANTITY`,0) else 0 end)) AS `RECEIVED_QTY`,sum((case when (`d`.`TXN_TYPE` = '314') then ifnull(`db`.`QUANTITY`,0) else 0 end)) AS `DIST_QTY`,sum((case when (`d`.`TXN_TYPE` = '314') then ifnull(`db`.`SUB_TOTAL`,0) else 0 end)) AS `DIST_AMT` from (((((`distribution_detail` `db` join `distribution` `d` on((`d`.`ID` = `db`.`DISTRIBUTION_ID`))) join `prof` `p` on((`p`.`PROF_ID` = `d`.`AGENT_ID`))) join `product` `pr` on((`pr`.`ID` = `db`.`PRODUCT_ID`))) join `sub_category` `sc` on((`sc`.`ID` = `pr`.`SUB_CATEGORY_ID`))) join `warehouse` `w` on((`w`.`ID` = `p`.`WAREHOUSE_ID`))) where ((`d`.`AGENT_ID` is not null) and (`d`.`STATUS` <> '2')) group by cast(`d`.`TXN_TIME` as date),`d`.`AGENT_ID`,`db`.`PRODUCT_ID`,`d`.`branch_id`;

DROP view if EXISTS view_mobile_user_summary;
CREATE  VIEW `view_mobile_user_summary` AS select uuid_short() AS `ID`,`dist`.`BRANCH_ID` AS `branch_id`,`dist`.`TXN_DATE` AS `txn_date`,`dist`.`WAREHOUSE_CODE` AS `WAREHOUSE_CODE`,`dist`.`WAREHOUSE_NAME` AS `warehouse_name`,`dist`.`AGENT_ID` AS `agent_id`,`dist`.`AGENT_NAME` AS `agent_name`,`dist`.`PRODUCT` AS `product`,`dist`.`PRODUCT_ID` AS `PRODUCT_ID`,`dist`.`CATEGORY` AS `CATEGORY`,`dist`.`UNIT` AS `UNIT`,`dist`.`UNIT_PRICE` AS `UNIT_PRICE`,ifnull((select `warehouse_product_detail`.`PREV_STOCK` from `warehouse_product_detail` where (`warehouse_product_detail`.`ID` = `t`.`MIN_id`)),0) AS `EXISTING_QUANTITY`,`dist`.`RECEIVED_QTY` AS `RECEIVED_QTY`,(ifnull((select `warehouse_product_detail`.`PREV_STOCK` from `warehouse_product_detail` where (`warehouse_product_detail`.`ID` = `t`.`MIN_id`)),0) + ifnull(`dist`.`RECEIVED_QTY`,0)) AS `TOTAL_QTY`,`dist`.`DIST_QTY` AS `DIST_QTY`,ifnull(`t`.`RETURN_FROM_FARMER_QTY`,0) AS `RETURN_FROM_FARMER_QTY`,ifnull(`t`.`RETURN_FROM_AGENT_QTY`,0) AS `RETURN_FROM_AGENT_QTY`,`dist`.`DIST_AMT` AS `DIST_AMT`,ifnull((select `warehouse_product_detail`.`FINAL_STOCK` from `warehouse_product_detail` where (`warehouse_product_detail`.`ID` = `t`.`MAX_id`)),0) AS `BAL_QTY`,(`dist`.`UNIT_PRICE` * ifnull((select `warehouse_product_detail`.`FINAL_STOCK` from `warehouse_product_detail` where (`warehouse_product_detail`.`ID` = `t`.`MAX_id`)),0)) AS `STOCK_AMT` from (`vw_distribution` `dist` left join `vw_warehousestock` `t` on(((`t`.`WP_DATE` = `dist`.`TXN_DATE`) and (`t`.`WP_AGENT_ID` = `dist`.`prof_id`) and (`t`.`WP_PRODUCT_ID` = `dist`.`PRODUCT_ID`)))) where (`dist`.`UNIT_PRICE` <> 0) order by `dist`.`TXN_DATE`;

DROP view if EXISTS view_noncommercial_product;
CREATE  VIEW `view_noncommercial_product` AS select uuid_short() AS `ID`,`dist`.`BRANCH_ID` AS `branch_id`,`dist`.`TXN_DATE` AS `txn_date`,`dist`.`WAREHOUSE_CODE` AS `WAREHOUSE_CODE`,`dist`.`WAREHOUSE_NAME` AS `warehouse_name`,`dist`.`AGENT_ID` AS `agent_id`,`dist`.`AGENT_NAME` AS `agent_name`,`dist`.`PRODUCT` AS `product`,`dist`.`PRODUCT_ID` AS `PRODUCT_ID`,`dist`.`CATEGORY` AS `CATEGORY`,`dist`.`UNIT` AS `UNIT`,`dist`.`UNIT_PRICE` AS `UNIT_PRICE`,ifnull((select `warehouse_product_detail`.`PREV_STOCK` from `warehouse_product_detail` where (`warehouse_product_detail`.`ID` = `t`.`MIN_id`)),0) AS `EXISTING_QUANTITY`,`dist`.`RECEIVED_QTY` AS `RECEIVED_QTY`,(ifnull((select `warehouse_product_detail`.`PREV_STOCK` from `warehouse_product_detail` where (`warehouse_product_detail`.`ID` = `t`.`MIN_id`)),0) + ifnull(`dist`.`RECEIVED_QTY`,0)) AS `TOTAL_QTY`,`dist`.`DIST_QTY` AS `DIST_QTY`,ifnull(`t`.`RETURN_FROM_FARMER_QTY`,0) AS `RETURN_FROM_FARMER_QTY`,ifnull(`t`.`RETURN_FROM_AGENT_QTY`,0) AS `RETURN_FROM_AGENT_QTY`,`dist`.`DIST_AMT` AS `DIST_AMT`,ifnull((select `warehouse_product_detail`.`FINAL_STOCK` from `warehouse_product_detail` where (`warehouse_product_detail`.`ID` = `t`.`MAX_id`)),0) AS `BAL_QTY`,(`dist`.`UNIT_PRICE` * ifnull((select `warehouse_product_detail`.`FINAL_STOCK` from `warehouse_product_detail` where (`warehouse_product_detail`.`ID` = `t`.`MAX_id`)),0)) AS `STOCK_AMT` from (`vw_distribution` `dist` left join `vw_warehousestock` `t` on(((`t`.`WP_DATE` = `dist`.`TXN_DATE`) and (`t`.`WP_AGENT_ID` = `dist`.`prof_id`) and (`t`.`WP_PRODUCT_ID` = `dist`.`PRODUCT_ID`)))) where (`dist`.`UNIT_PRICE` = 0) order by `dist`.`TXN_DATE`;

-- -12-07-2018
-- -Farmer Development Menu
INSERT INTO ese_menu VALUES (null, 'report.dynmaicCertification.6', 'Dynamic Cerrification Report', 'dynmaicCertificationReport_list.action?txnType=6', 16, 4, 0, 0, 0, 0, NULL);
INSERT INTO `ese_ent` VALUES (null, 'report.dynmaicCertification.list');
INSERT INTO ese_menu_action VALUES ((SELECT id FROM ese_menu WHERE NAME='report.dynmaicCertification.6'), '1');

INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='report.dynmaicCertification.6'), '1');
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='report.dynmaicCertification.list'));

----------------------08-03-2019-----------------
			
INSERT INTO `locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'seedCottonHarvested', 'en', 'Actual Harvested (Quintal)');
INSERT INTO `locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'yieldPerAcre', 'en', 'Per acre yield (Quintal)');
INSERT INTO `locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'estimatedCottonYield', 'en', 'Estimated yield (Quintal)');

UPDATE `locale_property` SET `code` = 'exportFarmerCropColumnHeaderBranchPratibha', `lang_code` = 'en', `lang_value` = 'S.no,Scheme,Season,Farmer,Village,Farm,Crop(MainCrop),Area in (%#),Type,InterCrop Details,Border Crop Details,Cover Crop Details,Plant on Bund Crop Details,Trap Crop Details,Estimated yield (Quintal),Actual Harvested (Quintal),Per acre yield (Quintal)' WHERE `code` = 'exportFarmerCropColumnHeaderBranchPratibha';

UPDATE `locale_property` SET `code` = 'OrganicFarmerCropExportHeader', `lang_code` = 'en', `lang_value` = 'S.no,Season,Farmer,Name Of ICS,Village,Farm,Crop(MainCrop),Area in (%#),Type,InterCrop Details,Border Crop Details,Cover Crop Details,Plant on Bund Crop Details,Trap Crop Details,Estimated yield (Quintal),Actual Harvested (Quintal),Per acre yield (Quintal)' WHERE `code` = 'OrganicFarmerCropExportHeader';

UPDATE `locale_property` SET `code` = 'BCIFarmerCropExportHeader', `lang_code` = 'en', `lang_value` = 'S.no,Season,Farmer,Village,Farm,Crop(MainCrop),Area in (%#),Type,InterCrop Details,Border Crop Details,Cover Crop Details,Plant on Bund Crop Details,Trap Crop Details,,Estimated yield (Quintal),Actual Harvested (Quintal),Per acre yield (Quintal)' WHERE `code` = 'BCIFarmerCropExportHeader';

