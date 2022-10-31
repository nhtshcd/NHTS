INSERT INTO `eses_mcash`.`ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES ('51', NULL);
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'report.farmerInspectionReport.npop.list');
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='report.farmerInspectionReport.npop.list'));


INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'report.farmerInspectionReport.fairTrade.list');
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='report.farmerInspectionReport.fairTrade.list'));


INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'report.farmerInspectionReport.organic');
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='report.farmerInspectionReport.organic'));


INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'report.farmerInspectionReport.ics');
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='report.farmerInspectionReport.ics'));