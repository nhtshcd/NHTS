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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.CityWarehouse;
import com.sourcetrace.eses.interceptor.ITxnErrorCodes;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.property.TxnEnrollmentProperties;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.exception.SwitchException;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

@Component
public class FarmCropsDownload implements ITxnAdapter {

	private static final String FARMER_DOB = "yyyyMMdd";
	@Autowired
	private IFarmerService farmerService;
	@Autowired
	private IUtilService utilService;

	SimpleDateFormat sdf = new SimpleDateFormat(FARMER_DOB);
	SimpleDateFormat sdf1 = new SimpleDateFormat(DateUtil.TXN_DATE_TIME);
	SimpleDateFormat sdfTxnDate = new SimpleDateFormat(DateUtil.DATABASE_DATE_FORMAT);

	List<java.lang.Object[]> farmICSConversionList = new ArrayList<>();
	Map<Long, List<java.lang.Object[]>> farmObject = new HashMap<>();
	Map<Long, List<java.lang.Object[]>> farmCropsObject = new HashMap<>();
	
	private static final DecimalFormat df = new DecimalFormat("0.00");
	@SuppressWarnings("unchecked")
	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData) {
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		String agentId = head.getAgentId();
		if (StringUtil.isEmpty(agentId))
			throw new SwitchException(ITxnErrorCodes.AGENT_ID_EMPTY);
		Agent agent=utilService.findAgentByAgentId(agentId);
		String revisionNo = (String) reqData.get(TxnEnrollmentProperties.FARM_CROPS_DOWNLOAD_REVISION_NO);
		if (StringUtil.isEmpty(revisionNo))
			revisionNo="0";
		List<java.lang.Object[]> farmCropsObjList = new ArrayList<java.lang.Object[]>();
		List<Object[]> farmCropsList= new ArrayList<>();
		

		Agent ag = utilService.findAgentByAgentId(agentId);
		List farmerCollection = new ArrayList<>();
		if (ag != null && ag.getExporter() != null) {
			farmCropsList = (List<Object[]>) farmerService.listObjectById(
					"select f.id,f.farmCrops.blockId,f.farmCrops.blockName,f.plantingDate,f.farmCrops.farm.farmCode,f.farmCrops.farm.farmer.farmerId,f.status,f.variety.code,f.variety.procurementProduct.code,f.grade.code,f.cultiArea,f.plantingId,f.farmCropId,f.grade.yield,f.revisionNo from Planting f where status in (1,0) and f.farmCrops.exporter.id=? and f.revisionNo > ? order by f.revisionNo desc",
					new Object[] { ag.getExporter().getId(),Long.valueOf(revisionNo) });
			List<Long> fcids =farmCropsList.stream().map(u -> Long.valueOf(u[0].toString().trim())).collect(Collectors.toList());
			List<Object[]> ibjli= new ArrayList<>();
			
			if(!StringUtil.isListEmpty(fcids)){
			ibjli = (List<Object[]>) farmerService.getharvestdateandquantity(CityWarehouse.Stock_type.HARVEST_STOCK.ordinal(),fcids);
			}
			
			
			Map<String,Object[]> fcHarData =ibjli.stream().filter(u -> u[0]!=null).collect(Collectors.toMap(u -> u[0].toString().trim(), u ->u));
			if (!ObjectUtil.isListEmpty(farmCropsList)) {
   
				farmCropsList.stream().forEach(farm -> {
					Map objectMap = new HashMap<>();
					String idd =farm[0].toString().trim();
					objectMap.put("blockId",
							farm[1] != null && !ObjectUtil.isEmpty(farm[1]) ? farm[1].toString().trim() : "");
					objectMap.put("blockName",
							farm[2] != null && !ObjectUtil.isEmpty(farm[2]) ? farm[2].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.SOWING_DATE,
							farm[3] != null && !ObjectUtil.isEmpty(farm[3]) ? farm[3].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.FARM_CODE,
							farm[4] != null && !ObjectUtil.isEmpty(farm[4]) ? farm[4].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.FARMER_ID,
							farm[5] != null && !ObjectUtil.isEmpty(farm[5]) ? farm[5].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.STATUS,
							farm[6] != null && !ObjectUtil.isEmpty(farm[6]) ? farm[6].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.VARIETY_CODE,
							farm[7] != null && !ObjectUtil.isEmpty(farm[7]) ? farm[7].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.CROP_CODE,
							farm[8] != null && !ObjectUtil.isEmpty(farm[8]) ? farm[8].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.GRADE_CODE,
							farm[9] != null && !ObjectUtil.isEmpty(farm[9]) ? farm[9].toString().trim() : "");
					objectMap.put(TxnEnrollmentProperties.CULTI_AREA,
							farm[10] != null && !ObjectUtil.isEmpty(farm[10]) ? farm[10].toString().trim() : "");
					objectMap.put("plantingId",
							farm[11] != null && !ObjectUtil.isEmpty(farm[11]) ? farm[11].toString().trim() : "");
					Double extyie=farm[10] != null && !ObjectUtil.isEmpty(farm[10]) ? Double.valueOf(farm[10].toString().trim()) : 0;
					Double yield=farm[13] != null && !ObjectUtil.isEmpty(farm[13]) ? Double.valueOf(farm[13].toString().trim()) : 0;
					objectMap.put(TxnEnrollmentProperties.GRADE_YIELD,df.format(extyie*yield));
					objectMap.put(TxnEnrollmentProperties.FARM_CROP_ID,
							farm[12] != null && !ObjectUtil.isEmpty(farm[12]) ? farm[12].toString().trim() : "");
					 
				
					if(fcHarData.containsKey(idd)){
						Object[] obj =fcHarData.get(idd);
						objectMap.put("lastHarvetDate",
								obj[1] != null && !ObjectUtil.isEmpty(obj[1]) ? obj[1].toString().trim() : "");
						objectMap.put("harvestedWeight",
								obj[2] != null && !ObjectUtil.isEmpty(obj[2]) ? obj[2].toString().trim() : "");
						objectMap.put("sortedWeight",
								obj[3] != null && !ObjectUtil.isEmpty(obj[3]) ? obj[3].toString().trim() : "");
						objectMap.put("lossWeight",
								obj[4] != null && !ObjectUtil.isEmpty(obj[4]) ? obj[4].toString().trim() : "");
					
						
					}else{
						objectMap.put("lastHarvetDate", "");
						objectMap.put("harvestedWeight","");
						objectMap.put("sortedWeight", "");
						objectMap.put("lossWeight", "");
					}

					farmerCollection.add(objectMap);
				});

			}

		}

		Map resp = new LinkedHashMap();
		resp.put(TxnEnrollmentProperties.FARM_CROPS_LIST, farmerCollection);
		if (!farmCropsObjList.isEmpty()) {
			resp.put(TxnEnrollmentProperties.FARM_CROPS_DOWNLOAD_REVISION_NO,
					farmCropsObjList.get(0)[farmCropsObjList.get(0).length - 1].toString().trim());
		} else {
			resp.put(TxnEnrollmentProperties.FARM_CROPS_DOWNLOAD_REVISION_NO, "0");
		}

		return resp;
		
	}
	

}
