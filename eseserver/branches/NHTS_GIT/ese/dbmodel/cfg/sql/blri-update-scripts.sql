INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'profile.location.state', 'en', 'Division');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'profile.location.municipality', 'en', 'Upazilla');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'profile.location.state', 'fr', 'Division');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'profile.location.municipality', 'fr', 'Upazilla');


SET @ids = (SELECT id FROM ese_menu WHERE NAME='profile');

INSERT INTO `ese_menu` VALUES (null, 'profile.researchStation', 'ResearchStation', 'researchStation_list.action', '11', @ids, '0', '0','0','0',null);

INSERT INTO `ese_ent` VALUES (null, 'profile.researchStation.list');
INSERT INTO `ese_ent` VALUES (null, 'profile.researchStation.create');
INSERT INTO `ese_ent` VALUES (null, 'profile.researchStation.update');
INSERT INTO `ese_ent` VALUES (null, 'profile.researchStation.delete');

INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='profile.researchStation.list'));
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='profile.researchStation.create'));
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='profile.researchStation.update'));
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='profile.researchStation.delete'));

INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='profile.researchStation'), '1');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='profile.researchStation'), '2');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='profile.researchStation'), '3');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='profile.researchStation'), '4');
INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='profile.researchStation'), '1');

-- -Cow Inspection Report
SET @ids = (SELECT id FROM ese_menu WHERE NAME='report');
SET @order=(SELECT MAX(ORD) from ese_menu where PARENT_ID=@ids)+1;
INSERT INTO `ese_menu` VALUES (null, 'report.cowInspection', 'ResearchStation', 'cowInspectionReport_list.action', @order, @ids, '0', '0','0','0',null);

INSERT INTO `ese_ent` VALUES (null, 'report.cowInspection.list');
INSERT INTO `ese_ent` VALUES (null, 'profile.researchStation.create');
INSERT INTO `ese_ent` VALUES (null, 'profile.researchStation.update');
INSERT INTO `ese_ent` VALUES (null, 'profile.researchStation.delete');

INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='report.cowInspection.list'));
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='report.cowInspection'), '1');
INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='report.cowInspection'), '1');


-- - Cost Of Farming Report Script 
SET @ids = (SELECT id FROM ese_menu WHERE NAME='report');
SET @order=(SELECT MAX(ORD) from ese_menu where PARENT_ID=@ids)+1;

INSERT INTO `ese_menu` VALUES (null, 'report.costFarmingReport', 'Cost Farming', 'costFarmingReport_list.action', @order, @ids, '0', '0','0','0',null);
INSERT INTO `ese_ent` VALUES (null, 'report.costFarmingReport.list');
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='report.costFarmingReport.list'));
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='report.costFarmingReport'), '1');
INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='report.costFarmingReport'), '1');


INSERT INTO `eses_blri`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'farmerCode', 'en', 'NID Number');
INSERT INTO `eses_blri`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'farmerCode', 'fr', 'Nombre PAS');

-- -Milk Production Report
SET @ids = (SELECT id FROM ese_menu WHERE NAME='report');
SET @order=(SELECT MAX(ORD) from ese_menu where PARENT_ID=@ids)+1;

INSERT INTO `ese_menu` VALUES (null, 'report.milkProduction', 'Cost Farming', 'milkProductionReport_list.action', @order, @ids, '0', '0','0','0',null);
INSERT INTO `ese_ent` VALUES (null, 'report.milkProduction.list');
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='report.milkProduction.list'));
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='report.milkProduction'), '1');
INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='report.milkProduction'), '1');