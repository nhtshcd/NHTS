-- Farm Report

-- -28-03-2019

-- - vw_tree_details - 

SELECT
	`td`.`FARM_ID` AS `FFM_ID`,
	sum(
	(
CASE
	
	WHEN ( ( `td`.`VARIETY` = 'V00001' ) AND ( `td`.`PRODUCT_STATUS` = 1 ) ) THEN
	`td`.`NO_OF_TREES` ELSE 0 
END 
	) 
	) AS `V00001`,
	sum(
		(
		CASE
				
				WHEN ( ( `td`.`VARIETY` = 'V00002' ) AND ( `td`.`PRODUCT_STATUS` = 1 ) ) THEN
				`td`.`NO_OF_TREES` ELSE 0 
			END 
			) 
		) AS `V00002`,
		sum(
			(
			CASE
					
					WHEN ( ( `td`.`VARIETY` = 'V00001' ) AND ( `td`.`PRODUCT_STATUS` = 2 ) ) THEN
					`td`.`NO_OF_TREES` ELSE 0 
				END 
				) 
			) AS `V00001_2`,
			sum(
				(
				CASE
						
						WHEN ( ( `td`.`VARIETY` = 'V00002' ) AND ( `td`.`PRODUCT_STATUS` = 2 ) ) THEN
						`td`.`NO_OF_TREES` ELSE 0 
					END 
					) 
				) AS `V00002_2` 
			FROM
				`tree_detail` `td` 
			WHERE
				( `td`.`FARM_ID` IS NOT NULL ) 
		GROUP BY
`td`.`FARM_ID`

-- -- -- vw_tree_insp -- -- 

SELECT
	`fdd`.`REFERENCE_ID` AS `FARM_ID`,
	max( `fdd`.`ID` ) AS `max( ``fdd``.``ID`` )`,
	`getTreeCount` ( `fdd`.`ID`, '0C115', '0C116', '0C119', 'V00001', 'CG00008' ) AS `HASS_OR_NO_OF_TREES`,
	`getTreeCount` ( `fdd`.`ID`, '0C115', '0C116', '0C119', 'V00002', 'CG00008' ) AS `FUERTRE_OR_NO_OF_TREES`,
	`getTreeCount` ( `fdd`.`ID`, '0C115', '0C116', '0C119', 'V00001', 'CG00009' ) AS `HASS_CV_NO_OF_TREES`,
	`getTreeCount` ( `fdd`.`ID`, '0C115', '0C116', '0C119', 'V00002', 'CG00009' ) AS `FUERTRE_CV_NO_OF_TREES`,
	`getTreeCount` ( `fdd`.`ID`, '0C123', '0C124', '0C125', 'V00001', 'CG00008' ) AS `HASS_OR_ESTIMATED_YEILD`,
	`getTreeCount` ( `fdd`.`ID`, '0C123', '0C124', '0C125', 'V00002', 'CG00008' ) AS `FUERTRE_OR_ESTIMATED_YEILD`,
	`getTreeCount` ( `fdd`.`ID`, '0C123', '0C124', '0C125', 'V00001', 'CG00009' ) AS `HASS_CV_ESTIMATED_YEILD`,
	`getTreeCount` ( `fdd`.`ID`, '0C123', '0C124', '0C125', 'V00002', 'CG00009' ) AS `FUERTRE_CV_ESTIMATED_YEILD` 
FROM
	`farmer_dynamic_data` `fdd` 
WHERE
	( `fdd`.`TXN_TYPE` = '2' ) 
GROUP BY
	`fdd`.`REFERENCE_ID`
	
-- -- -- vw_farm_report -- -- 

	SELECT
	`fa`.`ID` AS `id`,
	concat( `f`.`FIRST_NAME`, '  -  ', `f`.`LAST_NAME` ) AS `FARMER_NAME`,
	concat( `fa`.`FARM_CODE`, '  - ', `fa`.`FARM_NAME` ) AS `FARM_NAME`,
	`s`.`NAME` AS `ZONE`,
	`v`.`NAME` AS `VILLAGE`,
	`f`.`BRANCH_ID` AS `BRANCH_ID`,
	`f`.`FARMER_ID` AS `FARMER_ID`,
	`fdi`.`TOTAL_LAND_HOLDING` AS `ORGANIC_ACREAGE`,
	`fdi`.`PROPOSED_PLANTING_AREA` AS `CONVENTIONAL_ACREAGE`,
	ifnull( `fft`.`HASS_OR_NO_OF_TREES`, `ttd`.`V00001` ) AS `HASS_OR_NO_OF_TREES`,
	ifnull( `fft`.`FUERTRE_OR_NO_OF_TREES`, `ttd`.`V00002` ) AS `FUERTRE_OR_NO_OF_TREES`,
	ifnull( `fft`.`HASS_CV_NO_OF_TREES`, `ttd`.`V00001_2` ) AS `HASS_CV_NO_OF_TREES`,
	ifnull( `fft`.`FUERTRE_CV_NO_OF_TREES`, `ttd`.`V00002_2` ) AS `FUERTRE_CV_NO_OF_TREES`,
	ifnull( `fft`.`HASS_OR_ESTIMATED_YEILD`, 0 ) AS `HASS_OR_ESTIMATED_YEILD`,
	ifnull( `fft`.`FUERTRE_OR_ESTIMATED_YEILD`, 0 ) AS `FUERTRE_OR_ESTIMATED_YEILD`,
	ifnull( `fft`.`HASS_CV_ESTIMATED_YEILD`, 0 ) AS `HASS_CV_ESTIMATED_YEILD`,
	ifnull( `fft`.`FUERTRE_CV_ESTIMATED_YEILD`, 0 ) AS `FUERTRE_CV_ESTIMATED_YEILD` 
FROM
	(
	(
	(
	(
	(
	(
	(
	( `farm` `fa` JOIN `farm_detailed_info` `fdi` ON ( ( `fdi`.`ID` = `fa`.`FARM_DETAILED_INFO_ID` ) ) )
	JOIN `farmer` `f` ON ( ( `f`.`ID` = `fa`.`FARMER_ID` ) ) 
	)
	JOIN `village` `v` ON ( ( `v`.`ID` = `f`.`VILLAGE_ID` ) ) 
	)
	JOIN `city` `c` ON ( ( `c`.`ID` = `v`.`CITY_ID` ) ) 
	)
	JOIN `location_detail` `ld` ON ( ( `ld`.`ID` = `c`.`LOCATION_ID` ) ) 
	)
	JOIN `state` `s` ON ( ( `s`.`ID` = `ld`.`STATE_ID` ) ) 
	)
	LEFT JOIN `vw_tree_insp` `fft` ON ( ( `fft`.`FARM_ID` = `fa`.`ID` ) ) 
	)
	LEFT JOIN `vw_tree_details` `ttd` ON ( ( `ttd`.`FFM_ID` = `fa`.`ID` ) ) 
	) 
GROUP BY
	`fa`.`ID`