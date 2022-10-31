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
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.property.TxnEnrollmentProperties;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Component
public class FarmerDownload implements ITxnAdapter {

	private static final Logger LOGGER = Logger.getLogger(FarmerDownload.class.getName());
	private static final String FARMER_DOB = "yyyyMMdd";
	@Getter
	@Setter
	@Autowired
	private IFarmerService farmerService;
	@Autowired
	private IUtilService utilService;


	
	@SuppressWarnings("unchecked")
	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData)  {

		Map resp = new LinkedHashMap();
		/** REQUEST DATA **/
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
			String revisionNo = (String) reqData.get(TxnEnrollmentProperties.FARMER_DOWNLOAD_REVISION_NO);
		if (StringUtil.isEmpty(revisionNo))
			revisionNo="0";

		LOGGER.info("REVISION NO" + revisionNo);
		List<java.lang.Object[]> farmerList = new ArrayList<java.lang.Object[]>();
		List<String> branch = new ArrayList<>();
		branch.add(head.getBranchId());
		Agent ag = utilService.findAgentByAgentId(head.getAgentId());
		List farmerCollection = new ArrayList<>();
		if (ag != null && ag.getExporter() != null) {
			/*farmerList = (List<Object[]>) farmerService.listObjectById(
					"select f.id,f.farmerId,f.farmerCode,f.firstName,f.lastName,f.village.code,f.village.name,f.status,f.exporter.regNo,f.exporter.name,f.mobileNo,f.nid,f.village.city.locality.state.name,f.village.city.locality.state.code,f.revisionNo  from Farmer f where status in (1) and f.revisionNo > ? order by f.revisionNo desc",
					new Object[] { Long.valueOf(revisionNo) });*/
			farmerList = (List<Object[]>) farmerService.listObjectById(
					"select f.id,f.farmerId,f.farmerCode,f.firstName,f.lastName,f.village.code,f.village.name,f.status,f.exporters,f.mobileNo,f.nid,f.village.city.locality.state.name,f.village.city.locality.state.code,f.revisionNo,f.kraPin  from Farmer f where status in (1) and f.revisionNo > ? order by f.revisionNo desc",
					new Object[] { Long.valueOf(revisionNo) });
			
			if (!ObjectUtil.isListEmpty(farmerList)) {
	
			farmerList.forEach(farmer ->{
				Map objectMap = new HashMap<>();
				objectMap.put(TxnEnrollmentProperties.FARMER_CODE,
						(farmer[2] != null && !StringUtil.isEmpty(farmer[2]) ? farmer[2].toString() : ""));
				// }
				objectMap.put(TxnEnrollmentProperties.FARMER_ID,
						(farmer[1] != null && !StringUtil.isEmpty(farmer[1]) ? farmer[1].toString() : ""));
			
				objectMap.put(TxnEnrollmentProperties.FIRST_NAME,
						(farmer[3] != null && !StringUtil.isEmpty(farmer[3]) ? farmer[3].toString() : ""));
				objectMap.put(TxnEnrollmentProperties.LAST_NAME,
						(farmer[4] != null && !StringUtil.isEmpty(farmer[4]) ? farmer[4].toString() : ""));
				objectMap.put(TxnEnrollmentProperties.VILLAGE_CODE,
						(farmer[5] != null && !StringUtil.isEmpty(farmer[5]) ? farmer[5].toString() : ""));
				objectMap.put(TxnEnrollmentProperties.ADDRESS,
						farmer[6] != null && !ObjectUtil.isEmpty(farmer[6]) ? farmer[6].toString() : "");
				objectMap.put(TxnEnrollmentProperties.STATUS,
						(farmer[7] != null && !StringUtil.isEmpty(farmer[7]) ? farmer[7].toString() : ""));
				
				objectMap.put("exporter",
						farmer[8] != null && !StringUtil.isEmpty(farmer[8]) ? farmer[8].toString() : "");
				objectMap.put("exporterCode","");
				
				objectMap.put(TxnEnrollmentProperties.MOBILE_NUMBER,
						farmer[9] != null && !StringUtil.isEmpty(farmer[9]) ? farmer[9].toString() : "");
				objectMap.put(TxnEnrollmentProperties.NATIONALID,
						farmer[10] != null && !StringUtil.isEmpty(farmer[10]) ? farmer[10].toString() : "");
				
				objectMap.put(TxnEnrollmentProperties.STATE_NAME,
						farmer[11] != null && !StringUtil.isEmpty(farmer[11]) ? farmer[11].toString() : "");
				
				objectMap.put(TxnEnrollmentProperties.STATE_CODE,
						farmer[12] != null && !StringUtil.isEmpty(farmer[12]) ? farmer[12].toString() : "");
				objectMap.put(TxnEnrollmentProperties.FARMER_KRA,
						farmer[14] != null && !StringUtil.isEmpty(farmer[14]) ? farmer[14].toString() : "");
				
				/*objectMap.put("exporterCode",
						farmer[8] != null && !StringUtil.isEmpty(farmer[8]) ? farmer[8].toString() : "");
				objectMap.put("exporter",
						farmer[9] != null && !StringUtil.isEmpty(farmer[9]) ? farmer[9].toString() : "");

				
				objectMap.put(TxnEnrollmentProperties.MOBILE_NUMBER,
						farmer[10] != null && !StringUtil.isEmpty(farmer[10]) ? farmer[10].toString() : "");
				objectMap.put(TxnEnrollmentProperties.NATIONALID,
						farmer[11] != null && !StringUtil.isEmpty(farmer[11]) ? farmer[11].toString() : "");
				
				objectMap.put(TxnEnrollmentProperties.STATE_NAME,
						farmer[12] != null && !StringUtil.isEmpty(farmer[12]) ? farmer[12].toString() : "");
				
				objectMap.put(TxnEnrollmentProperties.STATE_CODE,
						farmer[13] != null && !StringUtil.isEmpty(farmer[13]) ? farmer[13].toString() : "");*/
			
			
				farmerCollection.add(objectMap);
				
			});
			}
			if (!farmerList.isEmpty()) {
			resp.put(TxnEnrollmentProperties.REVISION_NO, farmerList.get(0)[farmerList.get(0).length - 1].toString());
			}else{
				resp.put(TxnEnrollmentProperties.REVISION_NO, "0");
			}

		}
		resp.put(TxnEnrollmentProperties.FARMER_LIST, farmerCollection);
	
		return resp;

	}

}
