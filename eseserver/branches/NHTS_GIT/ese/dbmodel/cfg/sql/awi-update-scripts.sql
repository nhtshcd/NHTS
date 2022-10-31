-- - 23Nov2016

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'dateOfFormation', 'en', 'Date of Circuit formation');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'dateOfFormation', 'fr', 'Date de la formation du circuit');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'availableWarehouse', 'en ', 'Available Circuit');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'availableWarehouse', 'fr', 'Circuit disponible');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'selectedWarehouse', 'en', 'Selected Circuit');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'selectedWarehouse', 'fr', 'Circuit sélectionné');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'district.notfound', 'en', 'Selected City not found (May removed by other user)');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'gramPanchayat.notfound', 'en', 'Please Select Area');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'empty.gramPanchayat', 'en', 'Please Select Area');


INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'info.locality', 'en', 'City Informations');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'info.locality', 'fr', 'Informations sur la ville');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'LocalityAction.breadCrumb', 'en', 'Preferences~#,Location~#,City~locality_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'LocalityAction.breadCrumb', 'fr', 'Préférences~#,Emplacement~#,Ville~locality_list.action');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.location.locality', 'en', 'City');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.location.locality', 'fr', 'Ville');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'info.municipality', 'en', 'Zone Informations');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'info.municipality', 'fr', 'Informations de zone');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'city.name', 'en', 'Zone');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'locality.name', 'en', 'City');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'locality.name', 'fr', 'Ville');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'MunicipalityAction.breadCrumb', 'en', 'Preferences~#,Location~#,Zone~municipality_list.action');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'MunicipalityAction.breadCrumb', 'fr', 'Préférences~#,Emplacement~#,Zone~municipality_list.action');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.location.municipality', 'en', 'Zone');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.location.municipality', 'fr', 'Zone');


UPDATE `locale_property` SET `lang_value`='Circuit' WHERE (`id`='17');

UPDATE `locale_property` SET `lang_value`='Preferences~#,Circuit~samithi_list.action' WHERE (`id`='18');

UPDATE `locale_property` SET `lang_value`='Circuit Information' WHERE (`id`='19');


UPDATE `locale_property` SET `lang_value`='Area' WHERE (`id`='10');

UPDATE `locale_property` SET `lang_value`='Preferences~#,Location~#,Area~gramPanchayat_list.action' WHERE (`id`='12');

UPDATE `locale_property` SET `lang_value`='Area Informations' WHERE (`id`='13');

UPDATE `locale_property` SET `lang_value`='Area Code' WHERE (`id`='14');

UPDATE `locale_property` SET `lang_value`='Area Name' WHERE (`id`='15');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'panchayat.name', 'en', 'Area');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'panchayat.name', 'fr', 'Région');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'taluk', 'en', 'Zone');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'district', 'en', 'City');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'district', 'fr', 'Ville');


-- -09 DEC 2016
INSERT INTO locale_property (id, code, lang_code, lang_value) VALUES (NULL, 'fpoGroup', 'en', 'Mentoring Group');
INSERT INTO locale_property (id, code, lang_code, lang_value) VALUES (NULL, 'empty.fpoGroup', 'en', 'Please Select Mentoring Group');
INSERT INTO locale_property (id, code, lang_code, lang_value) VALUES (NULL, 'agent.samithi', 'en', 'Circuit');

UPDATE locale_property SET lang_value='Circuit Code' WHERE (id='20'); 
UPDATE locale_property SET lang_value='Circuit Name' WHERE (id='21');
UPDATE locale_property SET lang_value='Please select the Circuit' WHERE (id='23');
UPDATE farm_catalogue SET NAME='Mentoring Group' WHERE (ID='11');

-- -26-12-2006
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'delete.samithiFarmer.warn', 'en', 'Cant Delete : Circuit has been Mapped with Farmer');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'delete.samithiFarmer.warn', 'fr', 'Impossible de supprimer: Le circuit a été mappé avec Farmer');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'delete.samithiAgent.warn', 'en', 'Cant Delete : Circuit has been Mapped with Mobile User');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'delete.samithiAgent.warn', 'fr', 'Impossible de supprimer: le circuit a été mappé avec l\utilisateur mobile');

-- -02 JAN 2017
INSERT INTO locale_property (id, code, lang_code, lang_value) VALUES (NULL, 'farmingTotalLand', 'en', 'Total Area(Hectare)');

-- -04Jan 2017
INSERT INTO locale_property VALUES (NULL, 'totalLandHolding', 'en', 'Total Land holding in (Hectare)');
UPDATE locale_property SET lang_value='Proposed area of planting in(Hectare)' WHERE (code='proposedPlantingAreas');
-- -05 Jan 2017
INSERT INTO locale_property VALUES (NULL, 'cottonArea', 'en', 'Area in Hectare');

-- 13 March 2019
-- - AWI
-- - Procurement Payment Import Report Menu

SET @ids = (SELECT id FROM ese_menu WHERE NAME='service');
SET @order=(SELECT MAX(ORD) from ese_menu where PARENT_ID=@ids);

INSERT INTO `ese_menu` VALUES (null, 'service.procurementPaymentImport', 'Procurement Payment Import', 'procurementPaymentImport_fileUpload.action', @order, @ids, '0', '0','0','0',null);

INSERT INTO `ese_ent` VALUES (null, 'service.procurementPaymentImport.list');

INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='service.procurementPaymentImport.list'));

INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.procurementPaymentImport'), '1');

INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='service.procurementPaymentImport'), '1');
