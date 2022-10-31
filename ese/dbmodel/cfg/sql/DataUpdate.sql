-- -Put all the data related queries in here with date.

-- -08-10-2018
INSERT INTO `coordinates_map` ( `DATE`, `STATUS`, `FARM_ID`, `FARM_CROPS_ID`, `AREA`, `MID_LATITUDE`, `MID_LONGITUDE` ) SELECT DISTINCT
f.PHOTO_CAPTURING_TIME,
'1',
f.id,
NULL,
fdd.TOTAL_LAND_HOLDING,
f.LATITUDE,
f.LONGITUDE 
FROM
	farm f
	INNER JOIN FARM_DETAILED_INFO fdd ON fdd.id = f.FARM_DETAILED_INFO_ID
	INNER JOIN coordinates c ON c.FARM_ID = f.id;
	
	
	
update coordinates_map cc join farm f on f.id =cc.FARM_ID join coordinates c on c.FARM_ID = f.id set c.coordinates_map_id = cc.id;


-- -Farm crops

INSERT INTO `coordinates_map` ( `DATE`, `STATUS`, `FARM_ID`, `FARM_CROPS_ID`, `AREA`, `MID_LATITUDE`, `MID_LONGITUDE` ) SELECT DISTINCT
f.PHOTO_CAPTURING_TIME,
'1',
NULL,
fc.ID,
fdd.TOTAL_LAND_HOLDING,
fc.LATITUDE,
fc.LONGITUDE 
FROM
	farm_crops fc inner join farm f on f.id=fc.FARM_ID
	INNER JOIN FARM_DETAILED_INFO fdd ON fdd.id = f.FARM_DETAILED_INFO_ID
	INNER JOIN farm_crops_coordinates c ON c.FARM_CROPS_ID = fc.id;
	
	
UPDATE coordinates_map cc
JOIN farm_crops f ON f.id = cc.FARM_CROPS_ID
JOIN farm_crops_coordinates c ON c.FARM_CROPS_ID= f.id 
SET c.coordinates_map_id = cc.id;


-- -09-10-2018
-- -09-10-2018
UPDATE farm f inner join coordinates_map cm on cm.FARM_ID=f.id set f.ACTIVE_COORDINATES=cm.id where cm.`STATUS`=1
UPDATE farm_crops f inner join coordinates_map cm on cm.FARM_CROPS_ID=f.id set f.ACTIVE_COORDINATES=cm.id where cm.`STATUS`=1



-- -25-06-2018
alter table dynamic_menu_section_map add column M_BEFORE_INSERT varchar(100),
add column M_AFTER_INSERT varchar(100);

alter table dynamic_menu_section_map add column BEFORE_INSERT varchar(100),
add column AFTER_INSERT varchar(100);

alter table dynamic_menu_field_map add column BEFORE_INSERT varchar(100),
add column AFTER_INSERT varchar(100);


alter table dynamic_menu_field_map add column M_BEFORE_INSERT varchar(100),
add column M_AFTER_INSERT varchar(100);


update  dynamic_section_config ds join dynamic_menu_section_map dsm on dsm.SECTION_ID = ds.id set dsm.M_AFTER_INSERT = ds.M_AFTER_INSERT,dsm.M_BEFORE_INSERT = ds.M_BEFORE_INSERT,dsm.AFTER_INSERT = ds.AFTER_INSERT;

update  dynamic_fields_config ds join dynamic_menu_field_map dsm on dsm.FIELD_ID = ds.id set dsm.M_AFTER_INSERT = ds.M_AFTER_INSERT,dsm.M_BEFORE_INSERT = ds.M_BEFORE_INSERT,dsm.AFTER_INSERT = ds.AFTER_ADD,dsm.BEFORE_INSERT = ds.BEFORE_ADD;



alter table dynamic_section_config drop column M_AFTER_INSERT,
drop column M_BEFORE_INSERT, drop column AFTER_INSERT;


alter table dynamic_fields_config drop column M_AFTER_INSERT,
drop column M_BEFORE_INSERT,drop column AFTER_ADD,drop column BEFORE_ADD;


