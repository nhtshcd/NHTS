-- 19-08-2016

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'product.name', 'en', 'Crop');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'empty.samithi', 'en', 'Please select the Samithi');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'agent', 'en', 'User Name');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'SubCategoryServiceAction.breadCrumb', 'en', 'Profile~#,Farm Product~subCategoryService_list.action');



-- - Bug Related Changes (971)- --
ALTER TABLE `periodic_inspection`
MODIFY COLUMN `DISEASE_SYMPTOMS_NAME`  varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL AFTER `PEST_SYMPTOMS_NAME`;


-- - Bug Related Changes (954)- --

INSERT INTO `ese_account` (`ID`, `ACC_NO`, `ACC_TYPE`, `TYPEE`, `ACC_OPEN_DATE`, `STATUS`, `CRE_TIME`, `MOD_TIME`, `PROFILE_ID`, `CASH_BALANCE`, `CREDIT_BALANCE`, `BALANCE`, `DIST_BALANCE`, `SHARE_AMOUNT`, `SAVING_AMOUNT`) VALUES (NULL, '110000000001', 'ORG', '0', '2016-01-27', '1', '2016-01-27 09:42:14', '2016-08-10 11:19:25', 'BASIX', '12167.00', '-31599.03', '0.00', '0.00', '0.00', '0.00');



-----Training in profile------


INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `DIMENSION`, `PRIORITY`, `ICON_CLASS`) VALUES (null, 'trainingMaster', 'Training Master', 'javascript:void(0)', '10', '2', '0', '0', '0', '0', NULL);
SET @ids = (SELECT id FROM ese_menu WHERE NAME='trainingMaster');

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `DIMENSION`, `PRIORITY`, `ICON_CLASS`) VALUES (null, 'profile.trainingMaster.trainingTopic', 'Training Topic', 'trainingTopic_list.action', '1', @ids, '0', '0', '0', '0', NULL);
INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `DIMENSION`, `PRIORITY`, `ICON_CLASS`) VALUES (null, 'profile.trainingMaster.topicCriteria', 'Topic Criteria', 'topicCriteria_list.action', '3', @ids, '0', '0', '0', '0', NULL);
INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `DIMENSION`, `PRIORITY`, `ICON_CLASS`) VALUES (null, 'profile.trainingMaster.topicCriteriaCategory', 'Topic Criteria Category', 'trainingCriteriaCategory_list.action', '2', @ids, '0', '0', '0', '0', NULL);
INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `DIMENSION`, `PRIORITY`, `ICON_CLASS`) VALUES (null, 'profile.trainingMaster.targetGroup', 'Target Group', 'targetGroup_list.action', '4', @ids, '0', '0', '0', '0', NULL);
INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `DIMENSION`, `PRIORITY`, `ICON_CLASS`) VALUES (null, 'profile.trainingMaster.trainingMethod', 'Training Method', 'trainingMethod_list.action', '5', @ids, '0', '0', '0', '0', NULL);
SET @idTopic = (SELECT id FROM ese_menu WHERE NAME='profile.trainingMaster.trainingTopic');
SET @idCri = (SELECT id FROM ese_menu WHERE NAME='profile.trainingMaster.topicCriteria');
SET @idcc = (SELECT id FROM ese_menu WHERE NAME='profile.trainingMaster.topicCriteriaCategory');
SET @idtg = (SELECT id FROM ese_menu WHERE NAME='profile.trainingMaster.targetGroup');
SET @idtm = (SELECT id FROM ese_menu WHERE NAME='profile.trainingMaster.trainingMethod');
INSERT INTO `ese_menu_action` VALUES (@idTopic, '1');
INSERT INTO `ese_menu_action` VALUES (@idTopic, '2');
INSERT INTO `ese_menu_action` VALUES (@idTopic, '3');
INSERT INTO `ese_menu_action` VALUES (@idTopic, '4');

INSERT INTO `ese_menu_action` VALUES (@idCri, '1');
INSERT INTO `ese_menu_action` VALUES (@idCri, '2');
INSERT INTO `ese_menu_action` VALUES (@idCri, '3');
INSERT INTO `ese_menu_action` VALUES (@idCri, '4');

INSERT INTO `ese_menu_action` VALUES (@idcc, '1');
INSERT INTO `ese_menu_action` VALUES (@idcc, '2');
INSERT INTO `ese_menu_action` VALUES (@idcc, '3');
INSERT INTO `ese_menu_action` VALUES (@idcc, '4');

INSERT INTO `ese_menu_action` VALUES (@idtg, '1');
INSERT INTO `ese_menu_action` VALUES (@idtg, '2');
INSERT INTO `ese_menu_action` VALUES (@idtg, '3');
INSERT INTO `ese_menu_action` VALUES (@idtg, '4');

INSERT INTO `ese_menu_action` VALUES (@idtm, '1');
INSERT INTO `ese_menu_action` VALUES (@idtm, '2');
INSERT INTO `ese_menu_action` VALUES (@idtm, '3');
INSERT INTO `ese_menu_action` VALUES (@idtm, '4');

INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='trainingMaster'), '1');
INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='trainingMaster'), '2');
INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='profile.trainingMaster.trainingTopic'), '1');
INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='profile.trainingMaster.topicCriteria'), '1');
INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='profile.trainingMaster.topicCriteriaCategory'), '1');
INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='profile.trainingMaster.targetGroup'), '1');
INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='profile.trainingMaster.trainingMethod'), '1');

INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='profile.trainingMaster.trainingTopic'), '2');
INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='profile.trainingMaster.topicCriteria'), '2');
INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='profile.trainingMaster.topicCriteriaCategory'), '2');
INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='profile.trainingMaster.targetGroup'), '2');
INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='profile.trainingMaster.trainingMethod'), '2');


INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.trainingTopic.list');
INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.trainingTopic.create');
INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.trainingTopic.update');
INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.trainingTopic.delete');


INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.topicCriteria.list');
INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.topicCriteria.create');
INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.topicCriteria.update');
INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.topicCriteria.delete');

INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.topicCriteriaCategory.list');
INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.topicCriteriaCategory.create');
INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.topicCriteriaCategory.update');
INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.topicCriteriaCategory.delete');

INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.targetGroup.list');
INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.targetGroup.create');
INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.targetGroup.update');
INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.targetGroup.delete');

INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.trainingMethod.list');
INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.trainingMethod.create');
INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.trainingMethod.update');
INSERT INTO `ese_ent` VALUES (null, 'profile.trainingMaster.trainingMethod.delete');

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.trainingTopic.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.trainingTopic.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.trainingTopic.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.trainingTopic.delete'));



INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id from ese_ent where name='profile.trainingMaster.topicCriteria.list'));

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',(SELECT id from ese_ent where name='profile.trainingMaster.topicCriteria.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',(SELECT id from ese_ent where name='profile.trainingMaster.topicCriteria.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.topicCriteria.delete'));

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.topicCriteriaCategory.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.topicCriteriaCategory.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.topicCriteriaCategory.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.topicCriteriaCategory.delete'));


INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.targetGroup.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.targetGroup.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.targetGroup.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.targetGroup.delete'));



INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.trainingMethod.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.trainingMethod.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.trainingMethod.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',
 (SELECT id from ese_ent where name='profile.trainingMaster.trainingMethod.delete'));
 
 
  
 INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.trainingTopic.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.trainingTopic.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.trainingTopic.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.trainingTopic.delete'));



INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.topicCriteria.list'));

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.topicCriteria.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.topicCriteria.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.topicCriteria.delete'));

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.topicCriteriaCategory.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.topicCriteriaCategory.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.topicCriteriaCategory.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.topicCriteriaCategory.delete'));


INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.targetGroup.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.targetGroup.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.targetGroup.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.targetGroup.delete'));



INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.trainingMethod.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.trainingMethod.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.trainingMethod.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2',
 (SELECT id from ese_ent where name='profile.trainingMaster.trainingMethod.delete'));

 
 -- kavipriya 19-08-2016 - --

ALTER TABLE `product`
MODIFY COLUMN `UNIT`  varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL AFTER `SUB_CATEGORY_ID`;


-- - Scripts for Traing under Service - --


SET @ids = (SELECT id FROM ese_menu WHERE NAME='service');

INSERT INTO `ese_menu` VALUES (null, 'training', 'Training Service', 'javascript:void(0)', '9', @ids, '0', '0','0','0',null);

SET @ser = (SELECT id FROM ese_menu WHERE NAME='training');
INSERT INTO `ese_menu` VALUES (null, 'service.farmerTrainingSelection', 'Farmer Training Selection', 'farmerTrainingSelection_list.action', '1', @ser, '0', '0','0','0',null);

INSERT INTO `ese_ent` VALUES (null, 'service.farmerTrainingSelection.list');
INSERT INTO `ese_ent` VALUES (null, 'service.farmerTrainingSelection.create');
INSERT INTO `ese_ent` VALUES (null, 'service.farmerTrainingSelection.update');
INSERT INTO `ese_ent` VALUES (null, 'service.farmerTrainingSelection.delete');

INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.farmerTrainingSelection.list'));
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.farmerTrainingSelection.create'));
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.farmerTrainingSelection.update'));
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.farmerTrainingSelection.delete'));

INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.farmerTrainingSelection'), '1');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.farmerTrainingSelection'), '2');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.farmerTrainingSelection'), '3');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.farmerTrainingSelection'), '4');

INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='service.farmerTrainingSelection'), '1');

INSERT INTO `ese_menu` VALUES (null, 'service.trainingPlanner', 'Farmer Training Planner', 'trainingPlanner_list.action', '2', @ser, '0', '0','0','0',null);

INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.trainingPlanner.list'));
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.trainingPlanner.update'));

INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.trainingPlanner'), '1');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.trainingPlanner'), '3');

INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='service.trainingPlanner'), '1');


-- --Report mail Under Service --


SET @ids = (SELECT id FROM ese_menu WHERE NAME='service');

INSERT INTO `ese_menu` VALUES (null, 'service.reportMailConfig', 'Report Mail Configuration', 'reportMailConfiguration_list.action', '15', @ids, '0', '0','0','0',null);

INSERT INTO `ese_ent` VALUES (null, 'service.reportMailConfig.list');
INSERT INTO `ese_ent` VALUES (null, 'service.reportMailConfig.create');
INSERT INTO `ese_ent` VALUES (null, 'service.reportMailConfig.update');
INSERT INTO `ese_ent` VALUES (null, 'service.reportMailConfig.delete');


INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.reportMailConfig.list'));
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.reportMailConfig.create'));
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.reportMailConfig.update'));
INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.reportMailConfig.delete'));

INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.reportMailConfig'), '1');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.reportMailConfig'), '2');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.reportMailConfig'), '3');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.reportMailConfig'), '4');

INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='service.reportMailConfig'), '1');

-- 08/Aug/2016
-- (Indong) Bug 996 related script.

ALTER TABLE `warehouse_payment_details`
MODIFY COLUMN `STOCK`  double(20,3) NULL DEFAULT 0 AFTER `COST_PRICE`;


-- -06-09-2016
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'StateAction.breadCrumb', 'fr', 'Admin~#,Localisation~#,Province~state_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'LocalityAction.breadCrumb', 'fr', 'Admin~#,Localisation~#,Territoire~locality_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'MunicipalityAction.breadCrumb', 'fr', 'Admin~#,Localisation~#,Secteur~municipality_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'GramPanchayatAction.breadCrumb', 'fr', 'Admin~#,Localisation~#,Groupements~gramPanchayat_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'VillageAction.breadCrumb', 'fr', 'Admin~#,Localisation~#,Village~village_list.action');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'info.state', 'fr', 'Informations sur la Province');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'info.locality', 'fr', 'Informations sur le Territoire');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'info.municipality', 'fr', 'Informations sur le Secteur');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'info.grampanchayat', 'fr', 'Informations sur le Groupement');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'info.village', 'fr', 'Informations sur le Village');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.location.state', 'fr', 'Province');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.location.locality', 'fr', 'Territoire');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.location.municipality', 'fr', 'Secteur');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.location.village', 'fr', 'Kebele');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'state.name', 'fr', 'Province');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'locality.name', 'fr', 'Territoire');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'city.name', 'fr', 'Secteur');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'gramPanchayat.name', 'fr', 'Groupements');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'village.name', 'fr', 'Kebele');

-- -07-09-2016
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'CountryAction.breadCrumb', 'fr', 'Admin~#,Localisation~#,Pays~country_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'empty.state', 'fr', 'Sil vous plaît Sélectionnez Province');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'empty.district', 'fr', 'Sil vous plaît Sélectionnez Territoire');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'empty.city', 'fr', 'Sil vous plaît Sélectionnez  Secteur');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'empty.gramPanchayat', 'fr', 'Sil vous plaît Sélectionnez Groupement');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'panchayat.name', 'fr', 'Groupements');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'SamithiAction.breadCrumb', 'fr', 'Admin~#,Groupe~samithi_list.action');

UPDATE `locale_property` SET `lang_value`='Groupements' WHERE (`code`='profile.location.gramPanchayat' and `lang_code`='fr');

-- - 16-09-2016
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'preference.manual', 'en', 'MANUAL');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'preference.gps', 'en', 'GPS');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'preference.both', 'en', 'BOTH');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'preference.manual', 'fr', 'MANUEL');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'preference.gps', 'fr', 'GPS');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'preference.both', 'fr', 'TOUS LES DEUX');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'currentSeasonLbl', 'fr', 'Saison en cours');


-- - 30-09-2016
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'report.distribution', 'fr', 'Rapport de distribution');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'report.distribution.agent', 'fr', 'Utilisateur mobile');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'report.procurement', 'fr', 'Marchés Rapport');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'ProcurementReportAction.breadCrumb', 'fr', 'Rapport~#,Marchés Rapport~procurementReport_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'DistributionReportAction.breadCrumbagent', 'fr', 'Rapport~#,Rapport de distribution~#,Utilisateur mobile~agentDistributionReport_list.action?type=agent');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'admin', 'fr', 'Préférences');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'ProfileDeviceAction.breadCrumb', 'fr', 'Préférences~#,Dispositif~device_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'AgentAction.breadCrumbfieldStaff', 'fr', 'Préférences~#,utilisateur mobile~fieldStaff_list.action?type=fieldStaff');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'RoleAction.breadCrumb', 'fr', 'Préférences~#,Rôle~role_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'RoleMenuAction.breadCrumb', 'fr', 'Préférences~#,Rôle~#,Rôle Menu~roleMenu_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'RoleEntitlementAction.breadCrumb', 'fr', 'Préférences~#,Rôle~#,Rôle Droit~roleEntitlement_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'UserAction.breadCrumb', 'fr', 'Préférences~#,Utilisateur~user_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'StateAction.breadCrumb', 'fr', 'Préférences~#,Localisation~#,Province~state_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'MunicipalityAction.breadCrumb', 'fr', 'Préférences~#,Localisation~#,Secteur~municipality_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'GramPanchayatAction.breadCrumb', 'fr', 'Préférences~#,Localisation~#,Groupements~gramPanchayat_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'VillageAction.breadCrumb', 'fr', 'Préférences~#,Localisation~#,Kebele~village_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'LocalityAction.breadCrumb', 'fr', 'Préférences~#,Localisation~#,Territoire~locality_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'CountryAction.breadCrumb', 'fr', 'Préférences~#,Localisation~#,Pays~country_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'SamithiAction.breadCrumb', 'fr', 'Préférences~#,Groupe~samithi_list.action');


- -- 10-04-2016 - --
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'availableWarehouse.yield', 'en', 'Available Group');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'availableWarehouse.yield', 'fr', 'Disponible Groupe');

-- - 05-10-2016 Start

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES ('47', 'Marital Status', '47', 'indong', '1', '1');

-- - 05-10-2016 End
-- - 07-10-2016 Start

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.customer', 'en', 'Client');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.customer', 'fr', 'Client');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'CustomerAction.breadCrumb', 'en', 'Profile~#,Client~customer_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'CustomerAction.breadCrumb', 'fr', 'Profile~#,Client~customer_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'info.customer', 'en', 'Client Details ');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'info.customer', 'fr', 'Détails du client');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'customer.customerType', 'en', 'Client Type');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'customer.customerSegment', 'en', 'Client Segment');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'customer.customerSegment', 'fr', 'Segment client');

-- - END


-- - 10-12-2016 - --

UPDATE `locale_property`
SET lang_value='Groupment'
WHERE lang_code='fr' AND `code`='profile.location.gramPanchayat';

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'GramPanchayatAction.breadCrumb', 'fr', ' Préférences ~#,Localisation~#,Groupment~gramPanchayat_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'city.name', 'fr', 'Secteure');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'panchayat.name', 'fr', 'Groupment');

-- -26-10-2016
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (49, 'Pest Name', 49, 'indong', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (50, 'Pest Symptoms', 50, 'indong', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (51, 'Disease Name', 51, 'indong', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (52, 'Disease Symptom', 52, 'indong', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (53, 'Inspection Crop', 53, 'indong', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (54, 'Crop Growth', 54, 'indong', '1', '1');

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (55, 'Interplough Type', 55, 'indong', '1', '1');

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (56, 'Usage Level', 56, 'indong', '1', '1');
-- -17-11-2016
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.location.municipality', 'en', 'Taluk');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.location.municipality', 'fr', 'Taluk');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'farm.icType', 'fr', 'Type ICS');
