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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sourcetrace.eses.entity.Agent;


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
public class ExporterDownload implements ITxnAdapter {

	@Autowired
	private IFarmerService farmerService;
	@Autowired
	private IUtilService utilService;

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
		List exportersList= new ArrayList<>();
		
		List<java.lang.Object[]> exporterObjList = utilService.listExporter();
		if(exporterObjList!=null && !ObjectUtil.isListEmpty(exporterObjList)){
			exporterObjList.stream().forEach(exporter ->{
				Map objectMap=new HashMap<>();
				objectMap.put(TxnEnrollmentProperties.COMPANY_NAME,exporter[1]!=null && !ObjectUtil.isEmpty(exporter[1])?exporter[1]:"");
				objectMap.put(TxnEnrollmentProperties.REG_PROOF, exporter[2]!=null && !ObjectUtil.isEmpty(exporter[2])?exporter[2]:"");
				objectMap.put(TxnEnrollmentProperties.CMPY_ORIENTATION, exporter[3]!=null && !ObjectUtil.isEmpty(exporter[3])?exporter[3]:"");
				objectMap.put(TxnEnrollmentProperties.REG_NUMBER, exporter[4]!=null && !ObjectUtil.isEmpty(exporter[4])?exporter[4]:"");
				objectMap.put(TxnEnrollmentProperties.UGANDA_EXPORT, exporter[5]!=null && !ObjectUtil.isEmpty(exporter[5])?exporter[5]:"");
				objectMap.put(TxnEnrollmentProperties.REF_LETTERNO, exporter[6]!=null && !ObjectUtil.isEmpty(exporter[6])?exporter[6]:"");
				objectMap.put(TxnEnrollmentProperties.Farmer_HaveFarms, exporter[7]!=null && !ObjectUtil.isEmpty(exporter[7])?exporter[7]:"");
				objectMap.put(TxnEnrollmentProperties.SCATTERED, exporter[8]!=null && !ObjectUtil.isEmpty(exporter[8])?exporter[8]:"");
				objectMap.put(TxnEnrollmentProperties.Pack_GpsLoc, exporter[9]!=null && !ObjectUtil.isEmpty(exporter[9])?exporter[9]:"");
				objectMap.put(TxnEnrollmentProperties.PACKHOUSE, exporter[10]!=null && !ObjectUtil.isEmpty(exporter[10])?exporter[10]:"");
				objectMap.put(TxnEnrollmentProperties.Exp_TinNumber, exporter[11]!=null && !ObjectUtil.isEmpty(exporter[11])?exporter[11]:"");
				objectMap.put(TxnEnrollmentProperties.TIN, exporter[12]!=null && !ObjectUtil.isEmpty(exporter[12])?exporter[12]:"");
				objectMap.put(TxnEnrollmentProperties.REX_No, exporter[13]!=null && !ObjectUtil.isEmpty(exporter[13])?exporter[13]:"");
				objectMap.put(TxnEnrollmentProperties.farm_ToPackhouse, exporter[14]!=null && !ObjectUtil.isEmpty(exporter[14])?exporter[14]:"");
				objectMap.put(TxnEnrollmentProperties.Pack_ToExitPoint, exporter[15]!=null && !ObjectUtil.isEmpty(exporter[15])?exporter[15]:"");
				objectMap.put(TxnEnrollmentProperties.MOBILE_NO, exporter[16]!=null && !ObjectUtil.isEmpty(exporter[16])?exporter[16]:"");
				objectMap.put(TxnEnrollmentProperties.EMAIL, exporter[17]!=null && !ObjectUtil.isEmpty(exporter[17])?exporter[17]:"");
				exportersList.add(objectMap);
			
			});
		}
		Map resp = new LinkedHashMap();
		resp.put(TxnEnrollmentProperties.EXPORTER_LIST, exportersList);
		return resp;
		
	}
	

}
