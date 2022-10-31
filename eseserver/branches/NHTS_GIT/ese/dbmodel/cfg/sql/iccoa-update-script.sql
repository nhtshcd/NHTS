ALTER TABLE dynamic_menu_field_map 
CHANGE COLUMN `ORDER` `ORDERZ` bigint(20) NULL DEFAULT NULL AFTER `FIELD_ID`;


ALTER TABLE dynamic_menu_section_map 
CHANGE COLUMN `ORDER` `ORDERZ` bigint(20) NULL DEFAULT NULL AFTER `FIELD_ID`;


ALTER TABLE dynamic_menu_config
CHANGE COLUMN `ORDER` `ORDERZ` varchar(3) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL AFTER `ENTITY`;


INSERT INTO dynamic_menu_config VALUES (1, 'M001', 'Test', 'farmerLogo', '391', '4', '1');

INSERT INTO dynamic_menu_field_map VALUES (1, 1, 1);
INSERT INTO dynamic_menu_field_map VALUES (1, 2, 2);
INSERT INTO dynamic_menu_field_map VALUES (1, 3, 3);
INSERT INTO dynamic_menu_field_map VALUES (1, 4, 4);
INSERT INTO dynamic_menu_field_map VALUES (1, 5, 5);
INSERT INTO dynamic_menu_field_map VALUES (1, 6, 6);
INSERT INTO dynamic_menu_field_map VALUES (1, 7, 7);
INSERT INTO dynamic_menu_field_map VALUES (1, 8, 8);
INSERT INTO dynamic_menu_field_map VALUES (1, 9, 9);
INSERT INTO dynamic_menu_field_map VALUES (1, 10, 10);
INSERT INTO dynamic_menu_field_map VALUES (1, 11, 11);
INSERT INTO dynamic_menu_field_map VALUES (1, 12, 12);
INSERT INTO dynamic_menu_field_map VALUES (1, 13, 13);
INSERT INTO dynamic_menu_field_map VALUES (1, 14, 14);
INSERT INTO dynamic_menu_field_map VALUES (1, 15, 15);
INSERT INTO dynamic_menu_field_map VALUES (1, 16, 16);
INSERT INTO dynamic_menu_field_map VALUES (1, 17, 17);
INSERT INTO dynamic_menu_field_map VALUES (1, 18, 18);
INSERT INTO dynamic_menu_field_map VALUES (1, 19, 19);
INSERT INTO dynamic_menu_field_map VALUES (1, 20, 20);
INSERT INTO dynamic_menu_field_map VALUES (1, 21, 21);
INSERT INTO dynamic_menu_field_map VALUES (1, 22, 22);
INSERT INTO dynamic_menu_field_map VALUES (1, 23, 23);
INSERT INTO dynamic_menu_field_map VALUES (1, 24, 24);
INSERT INTO dynamic_menu_field_map VALUES (1, 25, 25);
INSERT INTO dynamic_menu_field_map VALUES (1, 26, 26);
INSERT INTO dynamic_menu_field_map VALUES (1, 27, 27);
INSERT INTO dynamic_menu_field_map VALUES (1, 28, 28);
INSERT INTO dynamic_menu_field_map VALUES (1, 29, 29);
INSERT INTO dynamic_menu_field_map VALUES (1, 30, 30);
INSERT INTO dynamic_menu_field_map VALUES (1, 31, 31);
INSERT INTO dynamic_menu_field_map VALUES (1, 32, 32);
INSERT INTO dynamic_menu_field_map VALUES (1, 33, 33);
INSERT INTO dynamic_menu_field_map VALUES (1, 34, 34);
INSERT INTO dynamic_menu_field_map VALUES (1, 35, 35);
INSERT INTO dynamic_menu_field_map VALUES (1, 36, 36);
INSERT INTO dynamic_menu_field_map VALUES (1, 37, 37);
INSERT INTO dynamic_menu_field_map VALUES (1, 38, 38);
INSERT INTO dynamic_menu_field_map VALUES (1, 39, 39);
INSERT INTO dynamic_menu_field_map VALUES (1, 40, 40);
INSERT INTO dynamic_menu_field_map VALUES (1, 41, 41);
INSERT INTO dynamic_menu_field_map VALUES (1, 42, 42);
INSERT INTO dynamic_menu_field_map VALUES (1, 43, 43);
INSERT INTO dynamic_menu_field_map VALUES (1, 44, 44);
INSERT INTO dynamic_menu_field_map VALUES (1, 45, 45);
INSERT INTO dynamic_menu_field_map VALUES (1, 46, 46);
INSERT INTO dynamic_menu_field_map VALUES (1, 47, 47);
INSERT INTO dynamic_menu_field_map VALUES (1, 48, 48);
INSERT INTO dynamic_menu_field_map VALUES (1, 49, 49);
INSERT INTO dynamic_menu_field_map VALUES (1, 50, 50);
INSERT INTO dynamic_menu_field_map VALUES (1, 51, 51);
INSERT INTO dynamic_menu_field_map VALUES (1, 52, 52);
INSERT INTO dynamic_menu_field_map VALUES (1, 53, 53);
INSERT INTO dynamic_menu_field_map VALUES (1, 54, 54);
INSERT INTO dynamic_menu_field_map VALUES (1, 55, 55);
INSERT INTO dynamic_menu_field_map VALUES (1, 56, 56);
INSERT INTO dynamic_menu_field_map VALUES (1, 57, 57);
INSERT INTO dynamic_menu_field_map VALUES (1, 58, 58);
INSERT INTO dynamic_menu_field_map VALUES (1, 59, 59);
INSERT INTO dynamic_menu_field_map VALUES (1, 60, 60);
INSERT INTO dynamic_menu_field_map VALUES (1, 61, 61);
INSERT INTO dynamic_menu_field_map VALUES (1, 62, 62);
INSERT INTO dynamic_menu_field_map VALUES (1, 63, 63);
INSERT INTO dynamic_menu_field_map VALUES (1, 64, 64);
INSERT INTO dynamic_menu_field_map VALUES (1, 65, 65);
INSERT INTO dynamic_menu_field_map VALUES (1, 66, 66);
INSERT INTO dynamic_menu_field_map VALUES (1, 67, 67);
INSERT INTO dynamic_menu_field_map VALUES (1, 68, 68);
INSERT INTO dynamic_menu_field_map VALUES (1, 69, 69);
INSERT INTO dynamic_menu_field_map VALUES (1, 70, 70);
INSERT INTO dynamic_menu_field_map VALUES (1, 71, 71);
INSERT INTO dynamic_menu_field_map VALUES (1, 72, 72);
INSERT INTO dynamic_menu_field_map VALUES (1, 73, 73);
INSERT INTO dynamic_menu_field_map VALUES (1, 74, 74);
INSERT INTO dynamic_menu_field_map VALUES (1, 75, 75);
INSERT INTO dynamic_menu_field_map VALUES (1, 76, 76);
INSERT INTO dynamic_menu_field_map VALUES (1, 77, 77);
INSERT INTO dynamic_menu_field_map VALUES (1, 78, 78);
INSERT INTO dynamic_menu_field_map VALUES (1, 79, 79);
INSERT INTO dynamic_menu_field_map VALUES (1, 80, 80);


INSERT INTO dynamic_menu_section_map VALUES (1, 1, 1);
INSERT INTO dynamic_menu_section_map VALUES (1, 2, 2);
INSERT INTO dynamic_menu_section_map VALUES (1, 3, 3);
INSERT INTO dynamic_menu_section_map VALUES (1, 4, 4);
INSERT INTO dynamic_menu_section_map VALUES (1, 5, 5);
INSERT INTO dynamic_menu_section_map VALUES (1, 6, 6);
INSERT INTO dynamic_menu_section_map VALUES (1, 7, 7);

INSERT INTO `locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'agentagentName', 'en', 'Mobile User');
