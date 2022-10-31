-- Crop->Variety->Label changes
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'product.code', 'en', 'Name');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'product.name', 'en', 'Crop');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'procurementVariety.name', 'en', 'Name');

-- 14-09-2016-- kpf-
INSERT INTO  `locale_property` VALUES ('11', 'profile.location.municipality', 'en', 'Taluk');
INSERT INTO  `locale_property` VALUES ('12', 'village.name', 'en', 'Village');
INSERT INTO  `locale_property` VALUES ('13', 'city.name', 'en', 'Taluk');

-- - 05-10-2016 Start

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES ('47', 'Marital Status', '47', 'kpf', '1', '1');

-- - 05-10-2016 End

-- -06-10-2016

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'learningGroupCode', 'en', 'Group');
-- - END
-- - 07-10-2016 Start

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'learningGroupCode', 'en', 'Group Code');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'learningGroupCode', 'fr', 'Code de groupe');

-- - END

-- -26-20-2016
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (49, 'Pest Name', 49, 'kpf', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (50, 'Pest Symptoms', 50, 'kpf', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (51, 'Disease Name', 51, 'kpf', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (52, 'Disease Symptom', 52, 'kpf', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (53, 'Inspection Crop', 53, 'kpf', '1', '1');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (54, 'Crop Growth', 54, 'kpf', '1', '1');

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (55, 'Interplough Type', 55, 'kpf', '1', '1');

INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES (56, 'Usage Level', 56, 'kpf', '1', '1');

-- -9-Nov-2016 - --
INSERT INTO `ese_menu` VALUES (null, 'profile.fieldCollectionCentre', 'Field Collection Centre', 'cooperative_list.action?type=collectionCenter',  '10', '2','0','0','0','0',NULL);

	INSERT INTO `ese_role_menu` VALUES (null, (select id from ese_menu where name="profile.fieldCollectionCentre"), '1');

	INSERT INTO `ese_menu_action` VALUES ((select id from ese_menu where name="profile.fieldCollectionCentre"), '1');
	INSERT INTO `ese_menu_action` VALUES ((select id from ese_menu where name="profile.fieldCollectionCentre"), '2');
	INSERT INTO `ese_menu_action` VALUES ((select id from ese_menu where name="profile.fieldCollectionCentre"), '3');
	INSERT INTO `ese_menu_action` VALUES ((select id from ese_menu where name="profile.fieldCollectionCentre"), '4');
	
	
INSERT INTO `ese_ent` VALUES (null, 'profile.fieldCollectionCentre.list');
INSERT INTO `ese_ent` VALUES (null, 'profile.fieldCollectionCentre.create');
INSERT INTO `ese_ent` VALUES (null, 'profile.fieldCollectionCentre.update');
INSERT INTO `ese_ent` VALUES (null, 'profile.fieldCollectionCentre.delete');

INSERT INTO `ese_role_ent` VALUES ('1', (select id from ese_ent where name="profile.fieldCollectionCentre.list"));
INSERT INTO `ese_role_ent` VALUES ('1', (select id from ese_ent where name="profile.fieldCollectionCentre.create"));
INSERT INTO `ese_role_ent` VALUES ('1', (select id from ese_ent where name="profile.fieldCollectionCentre.update"));
INSERT INTO `ese_role_ent` VALUES ('1', (select id from ese_ent where name="profile.fieldCollectionCentre.delete"));

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES ('31', 'profile.cooperative', 'en', ' Production Distribution Center ');

-- -11-14-2016
INSERT INTO `eses_kpf`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.location.municipality', 'en', 'Taluk');
INSERT INTO `eses_kpf`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'profile.location.municipality', 'fr', 'Taluk');


INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'pesticideRecommended', 'en', 'Pesticide Recommended');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'fungicideRecommended', 'en', 'Fungicide Recommended');

--29-Nov-2016
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES ('47', 'SamithiAction.breadCrumb', 'en', 'Preferences~#,Group~samithi_list.action');

--29-Dec-2016

ALTER TABLE `farm_detailed_info`
MODIFY COLUMN `FARM_OWNED`  varchar(45) NULL DEFAULT NULL AFTER `SEASONAL_WORKER_COUNT`;

-- -17-01-2017
UPDATE `locale_property` SET `lang_value`='Total No of Bags' WHERE (`id`='29')
UPDATE `locale_property` SET `lang_value`='Total No of Bags' WHERE (`id`='39')

-- -02-02-2017

SET @ids = (SELECT id FROM ese_menu WHERE NAME='report');
SET @order=(SELECT MAX(ORD) from ese_menu where PARENT_ID=@ids);

INSERT INTO `ese_menu` VALUES (null, 'report.yieldEstimationReport', 'Yield Estimation Report', 'yieldEstimationReport_list.action', @order, @ids, '0', '0','0','0',null);
INSERT INTO `ese_ent` VALUES (null, 'report.yieldEstimationReport.list');

INSERT INTO `ese_role_ent` VALUES ('1',(SELECT id FROM ese_ent WHERE NAME='report.yieldEstimationReport.list'));
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='report.yieldEstimationReport'), '1');
INSERT INTO `ese_role_menu` VALUES (null,(SELECT id FROM ese_menu WHERE NAME='report.yieldEstimationReport'), '1');


INSERT INTO `pref` (`NAME`, `VAL`, `ESE_ID`, `DESCRIPTION`) VALUES (  'QR_CODE_TEXT', '     {productKey} in {varietyKey} Variety is grown & harvested as per {cultTypeKey} from {villageKey} Village , {districtKey} District, {stateKey} State   by {samithiKey} Group with Lot No {lotNoKey}.\r\n	  \r\n      Enjoy the taste of organic produce and help the local community.', '1', '');
INSERT INTO `farm_catalogue` (`ID`, `NAME`, `CATALOGUE_TYPEZ`, `BRANCH_ID`, `REVIOSION_NO`, `STATUS`) VALUES ('70', 'Cultivation Type', '70', NULL, '1', '1');


-- -18-08-2017
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'procurementQtyLabel', 'en', 'Procurement Quantity');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (null, 'procurementQtyLabel', 'fr', 'Quantité d\'approvisionnement');

-- - 23 AUG 2017
UPDATE  locale_property  SET  lang_value ='1~MANDI TRADER,2~MANDI AGGREGATOR,3~FARM AGGREGATOR,11~FPO/FG,7~PRODUCE IMPORTER,8~FPC,10~Agriculture Company,99~FARMER,12~Customer,13~Cash Purchase\'' WHERE (code ='masterTypeList' AND lang_code ='en', );
UPDATE  locale_property  SET  lang_value ='1~MANDI TRADER,2~MANDI AGGREGATOR,3~AGREGATEUR AGRICOLE,11~FPO/FG,7~PRODUIT IMPORTATEUR,8~FPC,10~Entreprise agricole,99~AGRICULTEUR,,12~Cliente,13~Compra en efectivo' WHERE ( code ='masterTypeList' AND lang_code ='fr', );
UPDATE  locale_property  SET  lang_value ='1~MANDI TRADER,2~MANDI AGGREGATOR,3~FARM AGGREGATOR,7~PRODUCE IMPORTER,8~FPC,10~Agriculture Company,11~FPO/FG,12~Customer,13~Cash Purchase' WHERE (code ='mTypeList' AND lang_code ='en', );
UPDATE  locale_property  SET  lang_value ='1~MANDI TRADER,2~MANDI AGGREGATOR,3~AGREGATEUR AGRICOLE,7~PRODUIT IMPORTATEUR,8~FPC,10~Entreprise agricole,11~FPO/FG,12~Cliente,13~Compra en efectivo' WHERE ( code ='mTypeList' AND lang_code ='en', );


INSERT INTO  locale_property  VALUES (NULL, 'labourCost', 'en', 'Labour Cost (INR)');
INSERT INTO  locale_property  VALUES (NULL, 'transportCost', 'en', 'Transport Cost(INR)');


-- - 24AUG 2017
INSERT INTO  locale_property  VALUES (NULL, 'groupMasterTypeList', 'en ', '1~MANDI TRADER,2~MANDI AGGREGATOR,3~FARM AGGREGATOR,4~FPO,7~PRODUCE IMPORTER,8~FPC,10~Agriculture Company,11~FG,12~Customer,13~Cash Purchase');
INSERT INTO  locale_property  VALUES (NULL, 'groupMasterTypeList', 'fr ', '1~MANDI TRADER,2~MANDI AGGREGATOR,3~AGREGATEUR AGRICOLE,4~FPO,7~PRODUIT IMPORTATEUR,8~FPC,10~Entreprise agricole,11~FG,12~Cliente,13~Compra en efectivo');

INSERT INTO locale_property VALUES (NULL, 'group.type', 'en', 'Master Type');

INSERT INTO  locale_property  VALUES (NULL, 'masterDataTypeList', 'en ', '1~MANDI TRADER,2~MANDI AGGREGATOR,3~FARM AGGREGATOR,7~PRODUCE IMPORTER,8~FPC,10~Agriculture Company,12~Customer,13~Cash Purchase');
INSERT INTO  locale_property  VALUES (NULL, 'masterDataTypeList', 'fr ', '1~MANDI TRADER,2~MANDI AGGREGATOR,3~AGREGATEUR AGRICOLE,7~PRODUIT IMPORTATEUR,8~FPC,10~Entreprise agricole,12~Cliente,13~Compra en efectivo');

-- -30 AUG 2017
INSERT INTO `eses_kpf`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'exportColumnHeaderSupplierProcurement', 'en', 'Date,Month,Year,Invoice No,PFC,PFC Incharge, Produce Type, Supplier Type,Supplier Name,Farmer Name, Village,Weight in (KG),Transaction Amount(INR)');
INSERT INTO `eses_kpf`.`locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'exportColumnSubHeaderSupplierProcurement', 'en', 'Product,Grade,Unit No,Purchase Price(INR), Net Weight(Kg), Total Amount(INR)');

-- -07SEPT2017 - --
UPDATE `locale_property` SET `lang_value`='Report~#,Produce Fulfilment Centre~#,Produce Fulfilment Centre Stock Entry Report~warehouseStockEntryReport_list.action' WHERE (`code`='WarehouseStockEntryReportAction.breadcrumb' and `lang_code`='en');
INSERT INTO `locale_property` VALUES (null, 'warehouseStockEntryReportHeader', 'en', 'S.No,Date,Season,Order No,Produce Fulfilment Centre,Receipt No,Vendor Name,Total quantity');
INSERT INTO `locale_property` VALUES (null, 'warehouseStockReportExport', 'en', ' S.No,Produce Fulfilment Centre,Category,Product,Unit,Stock,Season');


-- -11-07-2017 - --
INSERT INTO `locale_property`  VALUES (null, 'fieldStaffMobileUserManagementHeader', 'en', 'Date,PFC Incharge Id,PFC Incharge,Strating Place,End Point,Total Distance Covered');
--INSERT INTO `locale_property` VALUES (null, 'exportColumnHeaderpmtnt', 'en', 'Organization,Date,Season,Sender Village/PFC*,Receipt Number,Truck Id,Driver Name,Total No Of Bags,Total Net Weight,Transaction Type');
INSERT INTO `locale_property` VALUES (null, 'exportColumnHeaderpmtnr', 'en', 'Organization,Date,Season,Receiver PFC,Receipt Number,Truck Id,Driver Name,Total No Of Bags,Total Net Weight');
--INSERT INTO `locale_property` VALUES (null, 'exportSubColumnHeaderpmtnt', 'en', 'Product,Variety,Grade,Unit,Reciever Village/PFC*,No Of Bags,Net Weight');


-- -04 Oct 2017
INSERT INTO txn_type VALUES (NULL, '378', 'supplierProcurementAdapter');

INSERT INTO `ese_menu` VALUES (NULL , 'report.supplierProcurement', 'Supplier Procurement Report', 'supplierProcurementReport_list.action', '25','4','0','0','0','0',NULL);
INSERT INTO `ese_ent` VALUES (NULL, 'report.supplierProcurement.list');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='report.supplierProcurement'), '1');
INSERT INTO `ese_role_ent` VALUES ('1', (SELECT id FROM ese_ent WHERE NAME='report.supplierProcurement.list'));
INSERT INTO `ese_role_menu` VALUES (NULL, (SELECT MAX(id) FROM ese_menu), '1');

INSERT INTO locale_property VALUES (NULL, 'exportColumnSupplierProcurementHeader', 'en', 'Date,Month,Year,Invoice No,Season,PFC,PFC Incharge, Supplier Type,Supplier Name,Weight in (KG),Transaction Amount(INR)');
INSERT INTO locale_property VALUES (NULL, 'exportColumnSupplierProcurementSubHeader', 'en', 'Farmer Id,Farmer Name,Product,Grade,Unit,Purchase Price(INR), Net Weight(Kg), Total Amount(INR),Produce Type');
INSERT INTO locale_property VALUES (NULL, 'print', 'en', 'Print');

-- -10 Oct 2017
INSERT INTO locale_property VALUES (NULL, 'supplierMobileNumber', 'en', 'Supplier Mobile No');
INSERT INTO locale_property VALUES (NULL, 'empty.supplier', 'en', 'Please Select Supplier');
INSERT INTO locale_property VALUES (NULL, 'empty.supplierName', 'en', 'Empty Supplier Name');
INSERT INTO locale_property VALUES (NULL, 'invalid.mobileNo', 'en', 'Invalid Mobile Number');
INSERT INTO locale_property VALUES (NULL, 'empty.product', 'en', 'Please Select Product');

-- -Supplier Procurement Menu
INSERT INTO ese_menu VALUES (NULL, 'service.supplierProcurementService', 'Supplier Procurement', 'supplierProcurement_create.action', '13', '3', '0', '0', '0', '0', '');

INSERT INTO ese_ent VALUES (NULL, 'service.supplierProcurementService.list');
INSERT INTO ese_ent VALUES (NULL, 'service.supplierProcurementService.create');
INSERT INTO ese_ent VALUES (NULL, 'service.supplierProcurementService.update');

INSERT INTO ese_role_ent (ROLE_ID, ENT_ID) VALUES ('1', (SELECT id FROM ese_ent WHERE NAME='service.supplierProcurementService.list'));
INSERT INTO ese_role_ent (ROLE_ID, ENT_ID) VALUES ('21', (SELECT id FROM ese_ent WHERE NAME='service.supplierProcurementService.list'));
INSERT INTO ese_role_ent (ROLE_ID, ENT_ID) VALUES ('1', (SELECT id FROM ese_ent WHERE NAME='service.supplierProcurementService.create'));
INSERT INTO ese_role_ent (ROLE_ID, ENT_ID) VALUES ('21', (SELECT id FROM ese_ent WHERE NAME='service.supplierProcurementService.create'));
INSERT INTO ese_role_ent (ROLE_ID, ENT_ID) VALUES ('1', (SELECT id FROM ese_ent WHERE NAME='service.supplierProcurementService.update'));
INSERT INTO ese_role_ent (ROLE_ID, ENT_ID) VALUES ('21', (SELECT id FROM ese_ent WHERE NAME='service.supplierProcurementService.update'));

INSERT INTO ese_role_menu VALUES (null,(SELECT id FROM ese_menu WHERE NAME='service.supplierProcurementService'), '1');


INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.supplierProcurementService'), '1');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.supplierProcurementService'), '2');
INSERT INTO `ese_menu_action` VALUES ((SELECT id FROM ese_menu WHERE NAME='service.supplierProcurementService'), '3');

-- - 10-11-2017 - --
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES ((NULL, 'exportColumnHeaderpmtnt', 'en', 'S.No,Date,Month,Year,Invoice No,PFC,Vehicle No,Driver,Customer,Total No of Bags,Weight in(KG),Total Amount(INR),Labour Cost (INR),Transport Cost(INR),Final Amount');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'exportSubColumnHeaderpmtnt', 'en', 'S.No,Product,type,Grade,Unit of Measurement,Village,No of Bags,Net Weight(Kg),Selling price (INR),Amount (INR)');

-- -28-11-2017 - --

INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'print.commDetails', 'en', 'Sender  Produce fulfilment centre : ');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'print.recieverComDetails', 'en', 'Receiver Produce fulfilment centre : ');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'truckId', 'en', 'Truck Id');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'warehouse.name', 'en', 'Produce Fulfillment Centre');
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'info.warehouseStockInfo', 'en', 'Produce Fulfillment Centre Stock Information');


-- -22-01-2018
INSERT INTO `locale_property` VALUES (null, 'taxAmt', 'en', 'Tax Amount(INR)');
INSERT INTO `locale_property` VALUES (null, 'otherCost', 'en', 'Other Cost(INR)');

-- -13-02-2018
UPDATE `locale_property` SET `lang_value` = 'S.no,Produce Fulfilment Centre Name,Product Name,No of Bags,Net Weight' WHERE `code` = 'ExportMainGridHeadingProcurmentStock';

