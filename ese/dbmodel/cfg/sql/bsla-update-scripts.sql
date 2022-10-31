-- - BSLA UPDATE SCRIPT

-- - Location Label Name Change
-- - 17 AUG 2016
INSERT INTO locale_property VALUES ('10', 'info.state', 'en', 'Region Information');
INSERT INTO locale_property VALUES ('11', 'info.municipality', 'en', 'Sub County Information');
INSERT INTO locale_property VALUES ('12', 'info.grampanchayat', 'en', 'Parish Information');

INSERT INTO locale_property VALUES ('13', 'profile.location.state', 'en', 'Region');
INSERT INTO locale_property VALUES ('14', 'profile.location.municipality', 'en', 'Sub County');
INSERT INTO locale_property VALUES ('15', 'profile.location.gramPanchayat', 'en', 'Parish');

INSERT INTO locale_property VALUES ('16', 'StateAction.breadCrumb', 'en', 'Preferences~#,Location~#,Region~state_list.action');
INSERT INTO locale_property VALUES ('17', 'MunicipalityAction.breadCrumb', 'en', 'Preferences~#,Location~#,Sub County~municipality_list.action');
INSERT INTO locale_property VALUES ('18', 'GramPanchayatAction.breadCrumb', 'en', 'Preferences~#,Location~#,Parish~gramPanchayat_list.action');
INSERT INTO locale_property VALUES ('19', 'state.name', 'en', 'Region');
INSERT INTO locale_property VALUES ('20', 'city.name', 'en', 'Sub County');
INSERT INTO locale_property VALUES ('21', 'gramPanchayat.name', 'en', 'Parish');

INSERT INTO locale_property VALUES ('22', 'state.notfound', 'en', 'Selected Region not found (May removed by other user)');
INSERT INTO locale_property VALUES ('23', 'empty.state', 'en', 'Please select Region');
INSERT INTO locale_property VALUES ('24', 'empty.city', 'en', 'Please select Sub County');
INSERT INTO locale_property VALUES ('25', 'empty.gramPanchayat', 'en ', 'Please select Parish');

-- - 08-19-16 - --
-- - Bug Related Changes (982)- --
UPDATE `pref` SET `VAL`='1' WHERE (`ID`='39');

-- - Bug Related Changes (985)- --
INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('profile.samithi', 'en','Group');
INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('profile.samithi', 'fr', 'Groupe');

INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('samithi.municipality', 'en','Sub county');
INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('samithi.municipality', 'fr', 'Sub county');

-- - Bug Related Changes (954)- --
INSERT INTO `ese_account` (`ID`, `ACC_NO`, `ACC_TYPE`, `TYPEE`, `ACC_OPEN_DATE`, `STATUS`, `CRE_TIME`, `MOD_TIME`, `PROFILE_ID`, `CASH_BALANCE`, `CREDIT_BALANCE`, `BALANCE`, `DIST_BALANCE`, `SHARE_AMOUNT`, `SAVING_AMOUNT`) VALUES (NULL, '110000000001', 'ORG', '0', '2016-01-27', '1', '2016-01-27 09:42:14', '2016-08-10 11:19:25', 'BASIX', '12167.00', '-31599.03', '0.00', '0.00', '0.00', '0.00');

-- Bug:Id:989 changes --
UPDATE `locale_property` SET `code`='panchayat.name' WHERE (`id`='21')

-- - 05-10-2016 Start

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES ('47', 'Marital Status', '47', 'bsla', '1', '1');

-- - 05-10-2016 End

-- -07-10-2016 Start

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'gramPanchayat.name', 'en', 'Parish');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'gramPanchayat.name', 'fr', 'Paroisse');

-- - END