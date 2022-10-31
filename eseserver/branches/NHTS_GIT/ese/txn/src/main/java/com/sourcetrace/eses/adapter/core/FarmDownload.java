/*
 * FarmerDownload.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.adapter.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.LanguagePreferences;
import com.sourcetrace.eses.entity.ProcurementGrade;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.interceptor.ITxnErrorCodes;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.property.TxnEnrollmentProperties;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.exception.SwitchException;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

@Component
public class FarmDownload implements ITxnAdapter {

	private static final Logger LOGGER = Logger.getLogger(FarmDownload.class.getName());
	private static final String FARMER_DOB = "yyyyMMdd";
	@Autowired
	private IFarmerService farmerService;

	@Autowired
	private IUtilService utilService;

	@SuppressWarnings("unchecked")
	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData) {

		/** REQUEST DATA **/
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		String agentId = head.getAgentId();
		if (StringUtil.isEmpty(agentId))
			throw new SwitchException(ITxnErrorCodes.AGENT_ID_EMPTY);

		String revisionNo = (String) reqData.get(TxnEnrollmentProperties.FARM_DOWNLOAD_REVISION_NO);
		if (StringUtil.isEmpty(revisionNo))
			revisionNo = "0";

		LOGGER.info("REVISION NO" + revisionNo);
		List<java.lang.Object[]> farmerList = new ArrayList<java.lang.Object[]>();

		Agent ag = utilService.findAgentByAgentId(agentId);
		List farmerCollection = new ArrayList<>();
		if (ag != null && ag.getExporter() != null) {
			farmerList = (List<Object[]>) farmerService.listObjectById(
					"select f.id,f.farmCode,f.farmName,f.farmer.farmerId,f.status,f.latitude,f.longitude,f.totalLandHolding,f.proposedPlanting ,f.farmId,f.farmer.village.city.locality.state.name,f.farmer.village.city.locality.state.code,f.farmer.village.city.locality.state.country.name,f.farmer.village.city.locality.state.country.code,f.farmer.village.city.code,f.farmer.village.city.name,f.farmer.village.city.locality.code,f.farmer.village.city.locality.name,f.farmer.village.code,f.farmer.village.name,f.revisionNo from Farm f where status in (1,0) and f.revisionNo > ? order by f.revisionNo desc",
					new Object[] { Long.valueOf(revisionNo) });
			if (!ObjectUtil.isListEmpty(farmerList)) {

				farmerList.stream().forEach(farm -> {
					Map objectMap = new HashMap<>();
					objectMap.put(TxnEnrollmentProperties.FARM_CODE,
							farm[1] != null && !ObjectUtil.isEmpty(farm[1]) ? farm[1].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.FARM_NAME,
							farm[2] != null && !ObjectUtil.isEmpty(farm[2]) ? farm[2].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.FARMER_ID,
							farm[3] != null && !ObjectUtil.isEmpty(farm[3]) ? farm[3].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.FARM_STATUS,
							farm[4] != null && !ObjectUtil.isEmpty(farm[4]) ? farm[4].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.FARM_LATITUDE,
							farm[5] != null && !ObjectUtil.isEmpty(farm[5]) ? farm[5].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.FARM_LONGITUDE,
							farm[6] != null && !ObjectUtil.isEmpty(farm[6]) ? farm[6].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.TOTAL_LAND_HOLDING_AREA,
							farm[7] != null && !ObjectUtil.isEmpty(farm[7]) ? farm[7].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.PROPOSED_PLANT_AREA,
							farm[8] != null && !ObjectUtil.isEmpty(farm[8]) ? farm[8].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.FARM_ID,
							farm[9] != null && !ObjectUtil.isEmpty(farm[9]) ? farm[9].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.STATE_NAME,
							farm[10] != null && !ObjectUtil.isEmpty(farm[10]) ? farm[10].toString().trim() : "");
					
					objectMap.put(TxnEnrollmentProperties.STATE_CODE,
							farm[11] != null && !ObjectUtil.isEmpty(farm[11]) ? farm[11].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.COUNTRY_NAME,
							farm[12] != null && !ObjectUtil.isEmpty(farm[12]) ? farm[12].toString().trim() : "");
					
					objectMap.put("country_code",
							farm[13] != null && !ObjectUtil.isEmpty(farm[13]) ? farm[13].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.CITY_CODE,
							farm[14] != null && !ObjectUtil.isEmpty(farm[14]) ? farm[14].toString().trim() : "");
					
					objectMap.put(TxnEnrollmentProperties.CITY_NAME,
							farm[15] != null && !ObjectUtil.isEmpty(farm[15]) ? farm[15].toString().trim() : "");
					
					objectMap.put(TxnEnrollmentProperties.DISTRICT_CODE,
							farm[16] != null && !ObjectUtil.isEmpty(farm[16]) ? farm[16].toString().trim() : "");
					
					objectMap.put("districtName",
							farm[17] != null && !ObjectUtil.isEmpty(farm[17]) ? farm[17].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.VILLAGE_CODE,
							farm[18] != null && !ObjectUtil.isEmpty(farm[18]) ? farm[18].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.VILLAGE_COOPEARATIVE_NAME,
							farm[19] != null && !ObjectUtil.isEmpty(farm[19]) ? farm[19].toString().trim() : "");
					Farm frm = (Farm) farmerService.findFarmById(Long.valueOf(farm[0].toString()));
					
					if(frm.getFarmCrops() != null && !ObjectUtil.isEmpty(frm.getFarmCrops())){
						long count = frm.getFarmCrops().stream().filter(ff -> ff.getId() != null && !StringUtil.isEmpty(ff.getId()) && ff.getStatus() != 3 ).count();
						objectMap.put(TxnEnrollmentProperties.BLOCK_COUNT, String.valueOf(count));
					}else{
						objectMap.put(TxnEnrollmentProperties.BLOCK_COUNT, "");
					}
					objectMap.put(TxnEnrollmentProperties.BLOCKING_LIST,
							buildBlockingCollectionJson(!ObjectUtil.isListEmpty(frm.getFarmCrops()) ? new LinkedList<FarmCrops>(frm.getFarmCrops()) : null, head));
				
				
					farmerCollection.add(objectMap);

				});

			}

		}

		Map resp = new LinkedHashMap();
		resp.put(TxnEnrollmentProperties.FARM_LIST, farmerCollection);
		if (!farmerList.isEmpty()) {
			resp.put(TxnEnrollmentProperties.FARM_DOWNLOAD_REVISION_NO,
					farmerList.get(0)[farmerList.get(0).length - 1].toString().trim());
		} else {
			resp.put(TxnEnrollmentProperties.FARM_DOWNLOAD_REVISION_NO, "0");
		}

		return resp;

	}
	
	private List buildBlockingCollectionJson(List<FarmCrops> varietyList, Head head){

		Map<String, List<LanguagePreferences>> lpMap = null;

		List varietyCollection = new ArrayList();
		String agentId = head.getAgentId();
		if (!ObjectUtil.isListEmpty(varietyList)) {
			Agent ag = utilService.findAgentByAgentId(agentId);
			varietyList.stream().filter(ff -> ff.getExporter() != null && !StringUtil.isEmpty(ff.getExporter().getId()) && ff.getExporter().getId().equals(ag.getExporter().getId()) && ff.getStatus() != 3).forEach(uu -> {
				Map varietyDataList = new HashMap();
				
				
				varietyDataList.put(TxnEnrollmentProperties.BLOCK_ID,
						uu.getBlockId());
				varietyDataList.put(TxnEnrollmentProperties.BLOCK_NAME,
						uu.getBlockName());
				varietyDataList.put(TxnEnrollmentProperties.CULTI_AREA,
						uu.getCultiArea() != null && !StringUtil.isEmpty(uu.getCultiArea()) ? uu.getCultiArea().toString().trim() : "");
				/*else{
					varietyDataList.put(TxnEnrollmentProperties.BLOCK_ID,
							"");
					varietyDataList.put(TxnEnrollmentProperties.BLOCK_NAME,
							"");
					varietyDataList.put(TxnEnrollmentProperties.CULTI_AREA,"");
				}*/
				varietyCollection.add(varietyDataList);			
		});
		
		}
		return varietyCollection;
	}

}
