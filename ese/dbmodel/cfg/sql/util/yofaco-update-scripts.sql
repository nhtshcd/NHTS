-- 13-Dec-2016

INSERT INTO `pref` (`NAME`, `VAL`, `ESE_ID`) VALUES ('GEO_FENCING_FLAG', '1', '5');
INSERT INTO `pref` (`NAME`, `VAL`, `ESE_ID`) VALUES ('GEO_FENCING_RADIUS_MT', '10000', '5');
INSERT INTO `pref` (`ID`, `NAME`, `VAL`, `ESE_ID`, `DESCRIPTION`) VALUES (NULL, 'TIME_TO_AUTO_RELEASE', '180000', '5', NULL);
INSERT INTO `pref` (`ID`, `NAME`, `VAL`, `ESE_ID`, `DESCRIPTION`) VALUES (NULL, 'GEO_FENCING_FLAG', '0', '5', NULL);
INSERT INTO `pref` (`ID`, `NAME`, `VAL`, `ESE_ID`, `DESCRIPTION`) VALUES (NULL, 'INTEREST_MODULE', '1', '5', NULL);
INSERT INTO `pref` (`ID`, `NAME`, `VAL`, `ESE_ID`, `DESCRIPTION`) VALUES (NULL, 'RATE_OF_INTEREST', '12.00', '5', NULL);
INSERT INTO `pref` (`ID`, `NAME`, `VAL`, `ESE_ID`, `DESCRIPTION`) VALUES (NULL, 'ROI_EFFECTIVE_FROM', '05/26/2016', '5', NULL);
INSERT INTO `pref` (`ID`, `NAME`, `VAL`, `ESE_ID`, `DESCRIPTION`) VALUES (NULL, 'GEO_FENCING_RADIUS_MT', '', '5', NULL);
INSERT INTO `pref` (`ID`, `NAME`, `VAL`, `ESE_ID`, `DESCRIPTION`) VALUES (NULL, 'EXPORT_RECORD_LIMIT', '10000', '5', NULL);
INSERT INTO `pref` (`ID`, `NAME`, `VAL`, `ESE_ID`, `DESCRIPTION`) VALUES (NULL, 'NO_OF_INVALID_LOGIN_ATTEMPTS', '6', '5', NULL);
INSERT INTO `pref` (`ID`, `NAME`, `VAL`, `ESE_ID`, `DESCRIPTION`) VALUES (NULL, 'AREA_CAPTURE_MODE', '2', '5', NULL);
INSERT INTO `pref` (`ID`, `NAME`, `VAL`, `ESE_ID`, `DESCRIPTION`) VALUES (NULL, 'CURRENT_SEASON_CODE', 'HS00001', '5', NULL);
INSERT INTO `pref` (`ID`, `NAME`, `VAL`, `ESE_ID`, `DESCRIPTION`) VALUES (NULL, 'ROI_EFFECT_EXISTING_FARMER', 'true', '5', NULL);

INSERT INTO `ese_account` (`ID`, `ACC_NO`, `ACC_TYPE`, `TYPEE`, `ACC_OPEN_DATE`, `STATUS`, `CRE_TIME`, `MOD_TIME`, `PROFILE_ID`, `CASH_BALANCE`, `CREDIT_BALANCE`, `BALANCE`, `DIST_BALANCE`, `SHARE_AMOUNT`, `SAVING_AMOUNT`) VALUES (NULL, '110000000001', 'ORG', '0', '2016-01-27', '1', '2016-01-27 09:42:14', '2016-08-10 11:19:25', 'BASIX', '12167.00', '-31599.03', '0.00', '0.00', '0.00', '0.00');

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'countryDelete.warn', 'en', 'Country Cannot be deleted,since it is mapped with Region');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'delete.warn', 'en', 'Region Cannot be deleted,since it is mapped with District');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'delete.warn.locality', 'en', 'District Cannot be deleted,since it is mapped with Sub County');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'village.mapped.grampanchayat', 'en', 'Parish Cannot be deleted,since it is mapped with Village');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'village.mapped.municipality', 'en', 'Sub County Cannot be deleted,since it is mapped with Parish');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'cooperative.mapped', 'en', 'Village Cannot be deleted,since it is mapped with Group');



INSERT INTO `locale_property` (`code`, `lang_code`, `lang_value`) VALUES ('SamithiAction.breadCrumb ', 'en', ' profile~#,Group~samithi_list.action');