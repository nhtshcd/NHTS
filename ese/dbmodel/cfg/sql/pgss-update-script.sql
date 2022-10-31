-- - Delete Farmer Inspection Report(fairtrade,organic)
UPDATE `ese_menu` SET `ORD`='24' WHERE (`ID`='49');
UPDATE `ese_menu` SET `PARENT_ID`='4' WHERE (`NAME`='report.farmerInspectionReport.ics');

DELETE FROM ESE_MENU_ACTION WHERE MENU_ID IN(46,47,48,118);
DELETE FROM ESE_ROLE_MENU WHERE MENU_ID IN(46,47,45,48,118);
DELETE FROM ESE_MENU WHERE ID IN(46,47,45,48,118);
DELETE FROM ESE_ROLE_ENT WHERE ENT_ID IN (179,124,125,265);
DELETE FROM ESE_ENT WHERE ID IN(179,124,125,265);



INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'report.farmerInspectionReport.organic', 'en', 'Farmer FeedBack');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'report.farmerInspectionReport.organic', 'fr', 'FeedBack de l\agriculteur');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'report.farmerInspectionReport.ics', 'en', 'Farmer ICS Report');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'report.farmerInspectionReport.ics', 'fr', 'Rapport ICS du paysan');


INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'exportXLSFarmerFeedbackHeadingSubOrg', 'en', 'Feedback Date,Village,Group,किसान का नाम, फैसससऱटेटर द्वारा का गई ववजिट की सॊख्या,किसान का सर्स्या का वववर, किसान के सुझाव,किसान के हस्ताक्षर');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'ques2', 'en', 'किसान का सर्स्या का वववर');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'ques1', 'en', 'फैसससऱटेटर द्वारा का गई ववजिट की सॊख्या');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'ques3', 'en', 'किसान के सुझाव');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'ques4', 'en', 'किसान के हस्ताक्षर');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'ques2', 'fr', 'किसान का सर्स्या का वववर');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'ques1', 'fr', 'फैसससऱटेटर द्वारा का गई ववजिट की सॊख्या');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'ques3', 'fr', 'किसान के सुझाव');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'ques4', 'fr', 'किसान के हस्ताक्षर');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'exportXLSFarmerFeedbackHeadingSubOrg', 'fr', 'Feedback Date,Village,Group,किसान का नाम, फैसससऱटेटर द्वारा का गई ववजिट की सॊख्या,किसान का सर्स्या का वववर, किसान के सुझाव,किसान के हस्ताक्षर');


-- - Farmer FeedBack Report Menu

INSERT INTO `ese_menu` VALUES (NULL , 'report.farmerFeedBack', 'Farmer FeedBack Report', 'farmerFeedBackReport_list.action', '25','4','0','0','0','0',NULL);
INSERT INTO `ese_ent` VALUES (NULL, 'report.farmerFeedBack.list');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='report.farmerFeedBack'), '1');
INSERT INTO `ese_role_ent` VALUES ('1', (SELECT id FROM ese_ent WHERE NAME='report.farmerFeedBack.list'));
INSERT INTO `ese_role_menu` VALUES (NULL, (SELECT MAX(id) FROM ese_menu), '1');


INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'answerDate', 'en', 'FeedBack Date');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'answerDate', 'fr', 'Date de retour');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'farmerName', 'en', 'किसान का नाम');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'farmerName', 'fr', 'किसान का नाम');




INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'selectFarmer', 'en', 'कृपया चुने किसान');

-- - Farmer FeedBack Service Menu
INSERT INTO `ese_menu` VALUES (null, 'service.farmerFeedBackService', 'Farmer FeedBackService ', 'farmerFeedBackService_create.action',  '16','3','0','0','0','0','NULL');
INSERT INTO `ese_ent` VALUES (NULL, 'service.farmerFeedBackService.list');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.farmerFeedBackService'), '1');
INSERT INTO `ese_role_ent` VALUES ('1', (SELECT id FROM ese_ent WHERE NAME='service.farmerFeedBackService.list'));
INSERT INTO `ese_role_menu` (`ID`, `MENU_ID`, `ROLE_ID`) VALUES (null, (SELECT MAX(ID) FROM ese_menu), '1');


INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'exportXLSFarmerFeedbackTitle', 'en', 'Farmer FeedBack Report');


INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES ('114', 'emptyQues1', 'en', 'कृपया दर्ज करें फैसससऱटेटर द्वारा का गई ववजिट की सॊख्या');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES ('115', 'emptyQues2', 'en', 'कृपया दर्ज करें किसान का सर्स्या का वववर');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES ('116', 'emptyQues3', 'en', 'कृपया दर्ज करें किसान के सुझाव');
INSERT INTO `eses_pgss`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES ('117', 'emptyQues4', 'en', 'कृपया दर्ज करें किसान के हस्ताक्षर');

INSERT INTO `ese_menu` VALUES (NULL , 'profile.irp', 'IRP', 'farmer_list.action?type=2', '11','2','0','0','0','0',NULL);

INSERT INTO `ese_ent` VALUES (NULL, 'profile.irp.list');
INSERT INTO `ese_ent` VALUES (NULL, 'profile.irp.create');
INSERT INTO `ese_ent` VALUES (NULL, 'profile.irp.update');
INSERT INTO `ese_ent` VALUES (NULL, 'profile.irp.delete');

INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='profile.irp'), '1');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='profile.irp'), '2');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='profile.irp'), '3');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='profile.irp'), '4');

INSERT INTO `ese_role_ent` VALUES ('1', (SELECT id FROM ese_ent WHERE NAME='profile.irp.list'));
INSERT INTO `ese_role_ent` VALUES ('1', (SELECT id FROM ese_ent WHERE NAME='profile.irp.create'));
INSERT INTO `ese_role_ent` VALUES ('1', (SELECT id FROM ese_ent WHERE NAME='profile.irp.update'));
INSERT INTO `ese_role_ent` VALUES ('1', (SELECT id FROM ese_ent WHERE NAME='profile.irp.delete'));

INSERT INTO `ese_role_menu` VALUES (NULL, (SELECT MAX(id) FROM ese_menu), '1');

-- - 09-19-2017 - --
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'animalHusbandary.animrev', 'en', 'Revenue(Year)');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'animalHusbandary.animrev', 'fr', 'Revenus (année)');


INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'farm.conventionalLands', 'en', 'Conventional Lands (Acre)');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'farm.fallowLand', 'en', 'Fallow/Pasture Land (Acre)');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'farm.conventionalCrops', 'en', 'Conventional Crops (Kg)');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'farm.conventionalEstimatedYields', 'en', 'Estimated Yield(Kg)');
