-- -14-03-2019
-- -Welspun Event Calnedar and Report No HBM connected only in DB updated by trigger

alter table event_calendar add column FARMER_NAME longtext;
alter table event_report add column FARMER_NAME longtext;
CREATE DEFINER = `root`@`localhost` TRIGGER `BEFOR_INSER_NAME` BEFORE INSERT ON `event_report` FOR EACH ROW begin
IF NEW.farmer_id is not null then 
SET NEW.FARMER_NAME = (select group_concat(concat(first_name,' ',IFNULL(last_name,''))) from farmer where FIND_IN_SET(farmer_id,NEW.farmer_id));
end if;
END;


CREATE DEFINER = `root`@`localhost` TRIGGER `BEFOR_INSER_NAMEE` BEFORE INSERT ON `event_calendar` FOR EACH ROW begin
IF NEW.farmer_id is not null then 
SET NEW.FARMER_NAME = (select group_concat(concat(first_name,' ',IFNULL(last_name,''))) from farmer where FIND_IN_SET(farmer_id,NEW.farmer_id));
end if;
END;
drop view if exists vw_event_calendar;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_event_calendar` AS select uuid_short() AS `ID`,`e`.`BRANCH_ID` AS `BRANCH_ID`,`e`.`CREATED_DATE` AS `CREATED_DATE`,`e`.`AGENT_ID` AS `AGENT_NAME`,`e`.`START_DATE` AS `START_DATE`,`e`.`END_DATE` AS `END_DATE`,`e`.`START_TIME` AS `START_TIME`,`e`.`END_TIME` AS `END_TIME`,`e`.`FARMER_ID` AS `FARMER_ID`,`e`.`PURPOUSE` AS `PURPOSE`,(select `w`.`NAME` from `warehouse` `w` where (`w`.`CODE` = `e`.`GROUP_CODE`)) AS `GROUP_NAME`,`e`.`FARMER_NAME` AS `FARMER_NAME`,(select `cv`.`NAME` from `catalogue_value` `cv` where (`cv`.`CODE` = `e`.`EVENT_TYPE`)) AS `EVENT_TYPE`,ifnull((select `cv`.`NAME` from `catalogue_value` `cv` where (`cv`.`CODE` = `e`.`STATUS`)),'Initiated') AS `STATUS` from `event_calendar` `e`;
drop view if exists vw_event_report;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_event_report` AS select uuid_short() AS `ID`,`e`.`BRANCH_ID` AS `BRANCH_ID`,`e`.`AGENT_ID` AS `AGENT_ID`,`e`.`AGENT_NAME` AS `AGENT_NAME`,`e`.`CREATED_DATE` AS `CREATED_DATE`,`e`.`FARMER_ID` AS `FARMER_ID`,ifnull((select `cv`.`NAME` from `catalogue_value` `cv` where (`cv`.`CODE` = `e`.`STATUS`)),'Initiated') AS `STATUS`,`e`.`OUTPUT` AS `OUTPUT`,`e`.`REMARK` AS `REMARK`,`e`.`PHOTO` AS `PHOTO`,concat(`e`.`LATITUDE`,',',`e`.`LONGITUDE`) AS `LATLON`,(select `w`.`NAME` from `warehouse` `w` where (`w`.`CODE` = `e`.`GROUP_CODE`)) AS `GROUP_NAME`,(select `w`.`NAME` from `warehouse` `w` where (`w`.`CODE` = `ec`.`GROUP_CODE`)) AS `EC_GROUP_NAME`,`e`.`FARMER_NAME` AS `FARMER_NAME`,`ec`.`START_TIME` AS `START_TIME`,`ec`.`END_TIME` AS `END_TIME`,`ec`.`START_DATE` AS `START_DATE`,`ec`.`END_DATE` AS `END_DATE`,`ec`.`FARMER_ID` AS `EC_FARMER_ID`,(select `cv`.`NAME` from `catalogue_value` `cv` where (`cv`.`CODE` = `ec`.`EVENT_TYPE`)) AS `EC_EVENT_TYPE`,`ec`.`PURPOUSE` AS `EC_PURPOSE`,`ec`.`FARMER_NAME` AS `EC_FARMER_NAME` from (`event_report` `e` join `event_calendar` `ec` on((`ec`.`MSG_NO` = `e`.`EVENT_ID`)));

-- -4-12-2019

0#Non Plotting Farm~<div id=tbDt><div class=imgWrap><table id=custom border=2><tr><td >Farmer Name :</td><td id=val1></td><td>Joining Year :</td><td id=val2></td><td>Crop :</td><td id=val3></td><td>Size Under Crops (Acre):</td><td id=val4></td> </tr><tr><td>Farmer Code:</td><td id=val6></td><td>Village :</td><td id=val8></td><td>Name Of Field :</td><td id=val10></td></tr></table><img src=img/welspun.png class=test></div><table id=detailDT border=1><thead><td>Date</td><td>Activity</td><td>Own</td><td class=sum>Hired</td><td>Name Of Item</td><td>UOM</td><td>Quantity</td><td>Cost   </td><td>Crop Harvest Quantity</td><td>Crop Harvest Value</td></thead><tr><td id=cal4></td><td id=cal5 class=total></td><td id=cal6 class=footerOwn></td><td id=cal7  class=footerHire></td><td id=cal8></td><td id=cal9></td><td id=cal10></td><td id=cal11 class=footer></td><td id=cal12></td><td id=cal13  class=footer></td></tr></table></div><table id=calc border=2><tr><td >Yield (Kg/Acre) :</td><td id=val11></td><td>Total Production Cost (Rupees/Acre) :</td><td id=val12></td><td>Net Profit (Rupees):</td><td id=val13></td><td> Profit (Rupees/Acre) :</td><td id=val14></td></table>


-- welspun profit loss function
CREATE DEFINER=`root`@`localhost` PROCEDURE `profit_loss`( p1 VARCHAR ( 10 ) )
BEGIN


(select distinct 

fd.id as fdddid,

	fd.REFERENCE_ID AS FCID,
	fc.id,
	mm.TYPEZ,
	-- DATE(`fd`.`CREATED_DATE`) AS `CREATED_DATE`,
	-- ac.AC_Date,
	DATE_FORMAT(ac.AC_Date, '%d/%m/%Y'),
	`dsd`.`SECTION_NAME` AS `SECTION_NAME`,
hh.own_sum AS own_sum,
	case when ds.SECTION_ID in (31,32,33) then mm.Machine_charges*mm.Quantity else hh.Hired_sum_cost end AS hired_sum,
	mm.Machine_name,
	mm.UOM,
	mm.Quantity1,
	case when ds.SECTION_ID in (31,32,33) then 0 when mm.Quantity1 is null then mm.Machine_charges*fc.CULTIVATION_AREA when mm.Quantity1 is not null then mm.Quantity1*mm.price end,
	mm.Quantity,
	mm.Cost,
	-- ac.AC_Date
	DATE(`fd`.`CREATED_DATE`) AS `CREATED_DATE`
from farm_crops fc join farm fm on fm.id = fc.FARM_ID 
join farmer_dynamic_data fd on  fd.REFERENCE_ID=fc.id join dynamic_menu_config dm on dm.TXN_TYPE
 = fd.TXN_TYPE join dynamic_menu_section_map ds on ds.MENU_ID = dm.ID
join dynamic_section_config dsd on dsd.id = ds.SECTION_ID

left join (select DT,REFID,SECID,FDID,SUM(Hired_sum_cost) as Hired_sum_cost,SUM(own_sum) as own_sum from (

SELECT
 DATE_FORMAT(fd.CREATED_DATE, '%d/%m/%Y')  AS DT,fd.REFERENCE_ID AS REFID,df.SECTION_ID AS SECID,fd.FARMER_DYNAMIC_DATA_ID AS FDID,
	group_concat( CASE WHEN ( `fd`.`FIELD_VALUE` = 'CG00163' ) THEN  case when  fdvv.FIELD_NAME in ('00C52',
	'00C63',
	'00C76', 
	'00C89',
	'0C109',
	'0C122',
	'0C137',
	'0C151',
	'0C165',
	'0C190',
	'0C203',
	'0C218',
	'0C238',
	'0C253',
	'0C272',
	'0C284',
	'0C296',
	'0C349',	
	'0C361',
	'0C420',
	'0C456' ) then  fdvv.FIELD_VALUE end END )  * 
		group_concat( CASE WHEN ( `fd`.`FIELD_VALUE` = 'CG00163' ) THEN  case when  fdvv.FIELD_NAME in ('00C53','00C64','00C77','00C90','0C110','0C123','0C138','0C152','0C166','0C191','0C204','0C219','0C239','0C254','0C273','0C285','0C297','0C350','0C362','0C421','0C457') then  fdvv.FIELD_VALUE end END ) AS `Hired_sum_cost`, 
	SUM( CASE WHEN ( `fd`.`FIELD_VALUE` = 'CG00161' ) THEN case when  fdvv.FIELD_NAME in ('00C52',
	'00C63',
	'00C76', 
	'00C89',
	'0C109',
	'0C122',
	'0C137',
	'0C151',
	'0C165',
	'0C190',
	'0C203',
	'0C218',
	'0C238',
	'0C253',
	'0C272',
	'0C284',
	'0C296',
	'0C349',	
	'0C361',
	'0C420',
	'0C456' ) then fdvv.FIELD_VALUE end  END ) AS `own_sum` 
FROM
	farmer_dynamic_field_value fd
	JOIN farmer_dynamic_field_value fdvv ON fd.FARMER_DYNAMIC_DATA_ID = fdvv.FARMER_DYNAMIC_DATA_ID   and fd.TYPEZ = fdvv.TYPEZ and fd.PARENT_ID = fdvv.PARENT_ID and  fd.REFERENCE_ID=p1
	AND `fd`.`FIELD_NAME` IN (
	'00C51',
	'00C62',
	'00C75',
	'00C88',
	'0C108',
	'0C121',
	'0C136',
	'0C150',
	'0C164',
	'0C189',
	'0C202',
	'0C217',
	'0C237',
	'0C252',
	'0C271',
	'0C283',
	'0C295',
	'0C348',
	'0C360',
	'0C419',
	'0C455' 
	) 
	AND 
	`fdvv`.`FIELD_NAME` IN (
	'00C52',
	'00C63',
	'00C76',
	'00C89',
	'0C109',
	'0C122',
	'0C137',
	'0C151',
	'0C165',
	'0C190',
	'0C203',
	'0C218',
	'0C238',
	'0C253',
	'0C272',
	'0C284',
	'0C296',
	'0C349',
	'0C361',
	'0C420',
	'0C456','00C53','00C64','00C77','00C90','0C110','0C123','0C138','0C152','0C166','0C191','0C204','0C219','0C239','0C254','0C273','0C285','0C297','0C350','0C362','0C421','0C457'
	)
	 JOIN dynamic_fields_config df ON fd.FIELD_NAME = df.COMPONENT_CODE 
	 group by fd.CREATED_DATE,fd.REFERENCE_ID,df.SECTION_ID,fd.TYPEZ) t
	 group by DT,REFID,SECID,FDID
)hh on hh.REFID = fc.id and hh.fdid = fd.id and hh.secid = dsd.SECTION_CODE



left join (SELECT
	fd.FARMER_DYNAMIC_DATA_ID AS FDDID,
	fd.REFERENCE_ID AS FCID,
	f.id AS FARMER_ID,
	fd.TYPEZ,
	DATE_FORMAT(fd.CREATED_DATE, '%d/%m/%Y')  AS `CREATED_DATE`,
	`ds`.`SECTION_NAME` AS `SECTION_NAME`,
	df.section_id AS MSECID,
								
								group_concat(
				DISTINCT (
				CASE
						WHEN (fd.field_name in("0C315","0C316","0C317","0C318","0C319","0C320","0C321","0C322","0C323","0C324","0C325","0C326","0C327","0C328","0C329","0C330","0C331","0C332","0C333","0C334","0C335","0C336","0C337","0C338","0C339","0C340","0C341",'0C424',"0C365")) THEN
							STR_TO_DATE(fd.FIELD_VALUE,'%d/%m/%Y')
						END 
						) SEPARATOR ',' 
					) AS `Ac_Date`
								
								FROM
									(
										(
											(
												(
													`farmer_dynamic_field_value` `fd`
													JOIN farm_crops fc ON fc.id = fd.REFERENCE_ID and fd.REFERENCE_ID=p1 and fd.field_name  in ("0C315","0C316","0C317","0C318","0C319","0C320","0C321","0C322","0C323","0C324","0C325","0C326","0C327","0C328","0C329","0C330","0C331","0C332","0C333","0C334","0C335","0C336","0C337","0C338","0C339","0C340","0C341","0C424","0C365")
													JOIN farm fa ON fa.id = fc.FARM_ID
													JOIN farmer f ON f.id = fa.FARMER_ID
							
												)
												JOIN `dynamic_fields_config` `df` ON ( ( `df`.`COMPONENT_CODE` = `fd`.`FIELD_NAME` ) ) 
											)
											JOIN `dynamic_section_config` `ds` ON (
												(
													( `ds`.`SECTION_CODE` = `df`.`SECTION_ID` ) 
													AND (
														`ds`.`ID` IN (
														7,8,9,10,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40 
														) 
													) 
												) 
											) 
										) 
										
									)
								
							group by fd.CREATED_DATE,ds.id,fd.TYPEZ	) ac on ac.FCID=fc.id  and ac.fddid = fd.id and ac.msecid = dsd.SECTION_CODE




left join (SELECT
	fd.FARMER_DYNAMIC_DATA_ID AS FDDID,
	fd.REFERENCE_ID AS FCID,
	f.id AS FARMER_ID,
	fd.TYPEZ,
	DATE_FORMAT(fd.CREATED_DATE, '%d/%m/%Y')  AS `CREATED_DATE`,
	`ds`.`SECTION_NAME` AS `SECTION_NAME`,
	df.section_id AS MSECID,
	group_concat(
	DISTINCT (
CASE
	
	WHEN (fd.field_name in('0C170','00C47','00C58','00C69','00C82','0C104','0C117','0C132','0C146','0C160','0C185','0C198','0C213','0C233','0C267','0C279','0C291','0C344','0C356','0C415','0C225','0C366','0C126','0C367','0C142','0C156','0C208','0C229','0C244')) THEN
		IFNULL(( SELECT group_concat( DISTINCT `cv`.`NAME` ) FROM `catalogue_value` `cv` WHERE ( `cv`.`CODE` = `fd`.`FIELD_VALUE` ) ),`fd`.`FIELD_VALUE`) 
	END 
	) SEPARATOR ',' 
	) AS `Machine_Name`,
	group_concat(
		DISTINCT (
		CASE
				
				WHEN (fd.field_name in('0C437','0C438','0C439','0C440','0C441','0C442','0C443','0C444','0C445','0C446','0C447','0C448','0C449')) THEN
					( SELECT group_concat( DISTINCT `cv`.`NAME` ) FROM `catalogue_value` `cv` WHERE ( `cv`.`CODE` = `fd`.`FIELD_VALUE` ) ) 
				END 
				) SEPARATOR ',' 
			) AS `UOM`,
			group_concat(
				DISTINCT (
				CASE
						WHEN (fd.field_name in('00C86','0C128','0C368','0C143','0C157','0C171','0C210','0C227','0C230','0C245')) THEN
							`fd`.`FIELD_VALUE` 
						END 
						) SEPARATOR ',' 
					) AS `Quantity1`,
					group_concat(
						DISTINCT (
						CASE
								
								WHEN (
									fd.field_name in ('00C48','00C59','00C70','00C83','0C105','0C118','0C133','0C147','0C161','0C186','0C199','0C214','0C234','0C268','0C280','0C292','0C345','0C357','0C416','00C74','00C87','0C129','0C144','0C158','0C172','0C211','0C228','0C231','0C246','0C302','0C305','0C308','0C309','0C310','0C369')
									) THEN
									`fd`.`FIELD_VALUE` 
								END 
								) SEPARATOR ',' 
							) AS `Machine_charges`,
							group_concat(
								DISTINCT (
								CASE
										
										WHEN ( ( `fd`.`FIELD_NAME` = '0C265' ) AND ( `ds`.`SECTION_CODE` = '00S31' ) ) THEN
										`fd`.`FIELD_VALUE` 
										WHEN ( ( `fd`.`FIELD_NAME` = '0C277' ) AND ( `ds`.`SECTION_CODE` = '00S32' ) ) THEN
										`fd`.`FIELD_VALUE` 
										WHEN ( ( `fd`.`FIELD_NAME` = '0C289' ) AND ( `ds`.`SECTION_CODE` = '00S33' ) ) THEN
										`fd`.`FIELD_VALUE` 
									END 
									) SEPARATOR ',' 
								) AS `Quantity`,
								0 as `Cost`,
								
								group_concat(
				DISTINCT (
				CASE
						WHEN (fd.field_name in('00C74','00C87','0C129','0C144','0C158','0C172','0C211','0C228','0C231','0C246','0C302','0C305','0C308','0C309','0C310','0C369')) THEN
							`fd`.`FIELD_VALUE` 
						END 
						) SEPARATOR ',' 
					) AS `Price`
								
								FROM
									(
										(
											(
												(
													`farmer_dynamic_field_value` `fd`
													JOIN farm_crops fc ON fc.id = fd.REFERENCE_ID and fd.REFERENCE_ID=p1 and fd.field_name  in ('0C170','00C47','00C58','00C69','00C82','0C104','0C117','0C132','0C146','0C160','0C185','0C198','0C213','0C233','0C267','0C279','0C291','0C344','0C356','0C415','0C225','0C366','0C126','0C367','0C142','0C156','0C170','0C208','0C229','0C244','00C48','00C59','00C70','00C83','0C105','0C118','0C133','0C147','0C161','0C186','0C199','0C214','0C234','0C268','0C280','0C292','0C345','0C357','0C416','0C437','0C438','0C439','0C440','0C441','0C442','0C443','0C444','0C445','0C446','0C447','0C448','0C449','00C73','00C86','0C128','0C143','0C157','0C171','0C210','0C227','0C230','0C245','0C265','0C277','0C289','0C368','00C74','00C87','0C129','0C144','0C158','0C172','0C211','0C228','0C231','0C246','0C302','0C305','0C308','0C309','0C310','00C47','00C58','00C69','00C82','0C104','0C117','0C132','0C146','0C160','0C185','0C198','0C213','0C233','0C267','0C279','0C291','0C344','0C356','0C415','00C74','00C87','0C129','0C144','0C158','0C172','0C211','0C228','0C231','0C246','0C302','0C305','0C308','0C309','0C310','0C369','00C74','00C87','0C129','0C144','0C158','0C172','0C211','0C228','0C231','0C246','0C302','0C305','0C308','0C309','0C310')
													JOIN farm fa ON fa.id = fc.FARM_ID
													JOIN farmer f ON f.id = fa.FARMER_ID
							
												)
												JOIN `dynamic_fields_config` `df` ON ( ( `df`.`COMPONENT_CODE` = `fd`.`FIELD_NAME` ) ) 
											)
											JOIN `dynamic_section_config` `ds` ON (
												(
													( `ds`.`SECTION_CODE` = `df`.`SECTION_ID` ) 
													AND (
														`ds`.`ID` IN (
														7,8,9,10,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40 
														) 
													) 
												) 
											) 
										) 
										
									)
								
							group by fd.CREATED_DATE,ds.id,fd.TYPEZ	) mm on mm.FCID=fc.id  and mm.fddid = fd.id and mm.msecid = dsd.SECTION_CODE where  (mm.Machine_Name!=null or mm.UOM is not null or mm.Quantity1 is not null or mm.quantity is not null or mm.cost is not null or mm.price is not null or hh.Hired_sum_cost is not null or hh.own_sum is not null ))  union (							
								SELECT
									"",
									cs.id,
									fc.id AS FCCID,
									"1",
								DATE_FORMAT(cs.DATE_SALE, '%d/%m/%Y') ,
									"Crop sale",
									"",
									"",
									c.customer_name,
									"",
									"",
									"",
									SUM( csd.QTY ),
									SUM( csd.SUB_TOTAL ),
								""	
								FROM
									farm_crops fc
									JOIN farm fa ON fa.id = fc.FARM_ID  and fc.id=p1
									
									JOIN farmer f ON f.id = fa.FARMER_ID
									LEFT JOIN crop_supply cs ON cs.FARM_CODE = fa.FARM_CODE
									LEFT JOIN crop_supply_details csd ON csd.CROP_SUPPLY_ID = cs.ID and csd.variety = fc.procurement_crops_variety_id
									join customer c on c.id=cs.buyer_info
								WHERE
									csd.SUB_TOTAL IS NOT NULL 
								GROUP BY
									fc.id,
								cs.DATE_SALE 
)
;
									END
									
-- welspun footer profit loss
									CREATE DEFINER=`root`@`localhost` PROCEDURE `details_footer_sum`( p1 VARCHAR ( 10 ) )
BEGIN
(select distinct 
fc.id,
f.FIRST_NAME,
	hs.name ,
	pp.name as pname,
	fc.CULTIVATION_AREA,
fc.TOTAL_CROP_HARVEST,
	f.farmeR_code AS FARMER_ID,

f.STATUS_CODE,
v.name as vName ,
fc.CROP_CATEGORY,
fm.FARM_NAME,
	ROUND(((mm.picking )/fc.CULTIVATION_AREA),2) AS picking_cost,
	ROUND(((hh.Hired_sum + mm.Machine_charges)/fc.CULTIVATION_AREA),2) as pcost,
	ROUND(((css.sub_total)-(hh.Hired_sum + mm.Machine_charges)),2) as profit,
	ROUND((((css.sub_total)-(hh.Hired_sum + mm.Machine_charges))/fc.CULTIVATION_AREA),2) as netprofit,
	ROUND((hh.own_sum),2) AS own_sum,
ROUND((hh.Hired_sum ),2) AS hired_sum
	
from farm_crops fc join farm fm on fm.id = fc.FARM_ID 

												
													join procurement_variety pv on pv.id = fc.procurement_crops_variety_id
													join procurement_product pp on pp.id = pv.procurement_product_id
													join farmer f on f.id = fm.FARMER_ID
															left join harvest_season hs on hs.code=f.season_code
														join village v on v.id=f.village_id
left join (select sum(Hired_sum_cost) as Hired_sum,sum(own_sum) as own_sum,REFID as fdid  from (
(SELECT
 fd.CREATED_DATE AS DT,fd.REFERENCE_ID AS REFID,df.SECTION_ID AS SECID,fd.FARMER_DYNAMIC_DATA_ID AS FDID,fd.TYPEZ,
	group_concat( CASE WHEN ( `fd`.`FIELD_VALUE` = 'CG00163' ) THEN  case when  fdvv.FIELD_NAME in ('00C52',
	'00C63',
	'00C76', 
	'00C89',
	'0C109',
	'0C122',
	'0C137',
	'0C151',
	'0C165',
	'0C190',
	'0C203',
	'0C218',
	'0C238',
	'0C253',
	'0C272',
	'0C284',
	'0C296',
	'0C349',	
	'0C361',
	'0C420',
	'0C456' ) then  fdvv.FIELD_VALUE end END )  * 
		group_concat( CASE WHEN ( `fd`.`FIELD_VALUE` = 'CG00163' ) THEN  case when  fdvv.FIELD_NAME in ('00C53','00C64','00C77','00C90','0C110','0C123','0C138','0C152','0C166','0C191','0C204','0C219','0C239','0C254','0C273','0C285','0C297','0C350','0C362','0C421','0C457') then  fdvv.FIELD_VALUE end END ) AS `Hired_sum_cost` ,
	
		
	SUm( CASE WHEN ( `fd`.`FIELD_VALUE` = 'CG00161' ) THEN case when  fdvv.FIELD_NAME in ('00C52',
	'00C63',
	'00C76', 
	'00C89',
	'0C109',
	'0C122',
	'0C137',
	'0C151',
	'0C165',
	'0C190',
	'0C203',
	'0C218',
	'0C238',
	'0C253',
	'0C272',
	'0C284',
	'0C296',
	'0C349',	
	'0C361',
	'0C420',
	'0C456' ) then  fdvv.FIELD_VALUE end END ) AS `own_sum` 
FROM
	farmer_dynamic_field_value fd
	JOIN farmer_dynamic_field_value fdvv ON fd.FARMER_DYNAMIC_DATA_ID = fdvv.FARMER_DYNAMIC_DATA_ID   and fd.TYPEZ = fdvv.TYPEZ and fd.PARENT_ID = fdvv.PARENT_ID and  fd.REFERENCE_ID=p1
	AND `fd`.`FIELD_NAME` IN (
	'00C51',
	'00C62',
	'00C75',
	'00C88',
	'0C108',
	'0C121',
	'0C136',
	'0C150',
	'0C164',
	'0C189',
	'0C202',
	'0C217',
	'0C237',
	'0C252',
	'0C271',
	'0C283',
	'0C295',
	'0C348',
	'0C360',
	'0C419',
	'0C455' 
	) 
	AND 
	`fdvv`.`FIELD_NAME` IN (
	'00C52',
	'00C63',
	'00C76',
	'00C89',
	'0C109',
	'0C122',
	'0C137',
	'0C151',
	'0C165',
	'0C190',
	'0C203',
	'0C218',
	'0C238',
	'0C253',
	'0C272',
	'0C284',
	'0C296',
	'0C349',
	'0C361',
	'0C420',
	'0C456','00C53','00C64','00C77','00C90','0C110','0C123','0C138','0C152','0C166','0C191','0C204','0C219','0C239','0C254','0C273','0C285','0C297','0C350','0C362','0C421','0C457'
	)
	 
	 JOIN dynamic_fields_config df ON fd.FIELD_NAME = df.COMPONENT_CODE 
	 group by fd.FARMER_DYNAMIC_DATA_ID,df.SECTION_ID,fd.TYPEZ) t
	 ))hh on hh.fdid = fc.id

left join (select FCID ,sum(picking) as picking, sum(case when MSECID in ('00S31','00S32','00S33') then Quantity*cost when Quantity1 is null then  Machine_charges*cutl_area when Quantity1 is not null then Quantity1*price  end )as Machine_charges


from (
SELECT
	fd.FARMER_DYNAMIC_DATA_ID AS FDDID,
	fd.REFERENCE_ID AS FCID,
	f.id AS FARMER_ID,
	fd.TYPEZ,
	DATE(`fd`.`CREATED_DATE`) AS `CREATED_DATE`,
	`ds`.`SECTION_NAME` AS `SECTION_NAME`,
	df.section_id AS MSECID,
	fc.CULTIVATION_AREA as cutl_area,
	group_concat(
	DISTINCT (
CASE
	
	WHEN (fd.field_name in('0C170','00C47','00C58','00C69','00C82','0C104','0C117','0C132','0C146','0C160','0C185','0C198','0C213','0C233','0C267','0C279','0C291','0C344','0C356','0C415','0C225','0C366','0C126','0C367','0C142','0C156','0C208','0C229','0C244')) THEN
		IFNULL(( SELECT group_concat( DISTINCT `cv`.`NAME` ) FROM `catalogue_value` `cv` WHERE ( `cv`.`CODE` = `fd`.`FIELD_VALUE` ) ),`fd`.`FIELD_VALUE`) 
	END 
	) SEPARATOR ',' 
	) AS `Machine_Name`,
	group_concat(
		DISTINCT (
		CASE
				
				WHEN (fd.field_name in('0C437','0C438','0C439','0C440','0C441','0C442','0C443','0C444','0C445','0C446','0C447','0C448','0C449')) THEN
					( SELECT group_concat( DISTINCT `cv`.`NAME` ) FROM `catalogue_value` `cv` WHERE ( `cv`.`CODE` = `fd`.`FIELD_VALUE` ) ) 
				END 
				) SEPARATOR ',' 
			) AS `UOM`,
			group_concat(
				DISTINCT (
				CASE
						WHEN (fd.field_name in('00C86','0C128','0C368','0C143','0C157','0C171','0C210','0C227','0C230','0C245')) THEN
							`fd`.`FIELD_VALUE` 
						END 
						) SEPARATOR ',' 
					) AS `Quantity1`,
					group_concat(
						DISTINCT (
						CASE
								
								WHEN (
									fd.field_name in ('00C48','00C59','00C70','00C83','0C105','0C118','0C133','0C147','0C161','0C186','0C199','0C214','0C234','0C268','0C280','0C292','0C345','0C357','0C416','00C74','00C87','0C129','0C144','0C158','0C172','0C211','0C228','0C231','0C246','0C302','0C305','0C308','0C309','0C310','0C369')
									) THEN
									`fd`.`FIELD_VALUE` 
								END 
								) SEPARATOR ',' 
							) AS `Machine_charges`,
							group_concat(
								DISTINCT (
								CASE
										
										WHEN ( ( `fd`.`FIELD_NAME` = '0C265' ) AND ( `ds`.`SECTION_CODE` = '00S31' ) ) THEN
										`fd`.`FIELD_VALUE` 
										WHEN ( ( `fd`.`FIELD_NAME` = '0C277' ) AND ( `ds`.`SECTION_CODE` = '00S32' ) ) THEN
										`fd`.`FIELD_VALUE` 
										WHEN ( ( `fd`.`FIELD_NAME` = '0C289' ) AND ( `ds`.`SECTION_CODE` = '00S33' ) ) THEN
										`fd`.`FIELD_VALUE` 
									END 
									) SEPARATOR ',' 
								) AS `Quantity`,
								group_concat(
									DISTINCT (
									CASE
											
											WHEN ( ( `fd`.`FIELD_NAME` = '0C308' ) AND ( `ds`.`SECTION_CODE` = '00S31' ) ) THEN
											`fd`.`FIELD_VALUE` 
											WHEN ( ( `fd`.`FIELD_NAME` = '0C309' ) AND ( `ds`.`SECTION_CODE` = '00S32' ) ) THEN
											`fd`.`FIELD_VALUE` 
											WHEN ( ( `fd`.`FIELD_NAME` = '0C310' ) AND ( `ds`.`SECTION_CODE` = '00S33' ) ) THEN
											`fd`.`FIELD_VALUE` 
										END 
										) SEPARATOR ',' 
									) AS `Cost` ,
									
									
									group_concat(
						DISTINCT (
						CASE
								
								WHEN (
									fd.field_name in ('0C265','0C277','0C289')
									) THEN
									`fd`.`FIELD_VALUE` 
								END 
								) SEPARATOR ',' 
							) AS `picking`,
							group_concat(
				DISTINCT (
				CASE
						WHEN (fd.field_name in('00C74','00C87','0C129','0C144','0C158','0C172','0C211','0C228','0C231','0C246','0C302','0C305','0C308','0C309','0C310')) THEN
							`fd`.`FIELD_VALUE` 
						END 
						) SEPARATOR ',' 
					) AS `Price`
									
								FROM
									(
										(
											(
												(
													`farmer_dynamic_field_value` `fd`
													JOIN farm_crops fc ON fc.id = fd.REFERENCE_ID and fd.REFERENCE_ID=p1 and fd.field_name  in ('0C170','00C47','00C58','00C69','00C82','0C104','0C117','0C132','0C146','0C160','0C185','0C198','0C213','0C233','0C267','0C279','0C291','0C344','0C356','0C415','0C225','0C366','0C126','0C367','0C142','0C156','0C170','0C208','0C229','0C244','00C48','00C59','00C70','00C83','0C105','0C118','0C133','0C147','0C161','0C186','0C199','0C214','0C234','0C268','0C280','0C292','0C345','0C357','0C416','0C437','0C438','0C439','0C440','0C441','0C442','0C443','0C444','0C445','0C446','0C447','0C448','0C449','00C73','00C86','0C128','0C143','0C157','0C171','0C210','0C227','0C230','0C245','0C265','0C277','0C289','0C368','00C74','00C87','0C129','0C144','0C158','0C172','0C211','0C228','0C231','0C246','0C302','0C305','0C308','0C309','0C310','00C47','00C58','00C69','00C82','0C104','0C117','0C132','0C146','0C160','0C185','0C198','0C213','0C233','0C267','0C279','0C291','0C344','0C356','0C415','00C74','00C87','0C129','0C144','0C158','0C172','0C211','0C228','0C231','0C246','0C302','0C305','0C308','0C309','0C310','0C369')
													JOIN farm fa ON fa.id = fc.FARM_ID
													JOIN farmer f ON f.id = fa.FARMER_ID
							
												)
												JOIN `dynamic_fields_config` `df` ON ( ( `df`.`COMPONENT_CODE` = `fd`.`FIELD_NAME` ) ) 
											)
											JOIN `dynamic_section_config` `ds` ON (
												(
													( `ds`.`SECTION_CODE` = `df`.`SECTION_ID` ) 
													AND (
														`ds`.`ID` IN (
														7,8,9,10,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40 
														) 
													) 
												) 
											) 
										) 
										
									)
								
							group by fd.CREATED_DATE,ds.id,fd.TYPEZ	) t group by FCID) mm on mm.FCID=fc.id
							left join (							
								SELECT
									fc.id AS FCCID,
	SUM(csd.SUB_TOTAL) AS SUB_TOTAL
								FROM
									farm_crops fc
									JOIN farm fm ON fm.id = fc.FARM_ID  and fc.id=p1
										LEFT JOIN crop_supply cs ON cs.FARM_CODE = fm.FARM_CODE
									LEFT JOIN crop_supply_details csd ON csd.CROP_SUPPLY_ID = cs.ID  and csd.variety = fc.procurement_crops_variety_id
										WHERE
									csd.SUB_TOTAL IS NOT NULL 
								GROUP BY
									fc.id
)css on css.FCCID = fc.id
							
where fc.id=p1 group by fc.id);   
									END