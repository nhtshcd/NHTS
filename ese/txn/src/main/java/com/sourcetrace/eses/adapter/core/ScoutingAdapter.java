/*
 * PeriodicInspectionAdapter.java
 * Copyright (c) 2015-2016, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.adapter.core;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.Scouting;
import com.sourcetrace.eses.interceptor.ITxnErrorCodes;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.exception.SwitchException;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

@Component
public class ScoutingAdapter implements ITxnAdapter {

	@Autowired
	private IFarmerService farmerService;

	@Autowired
	private IUtilService utilService;

	@Override
	public Map<?, ?> processJson(Map reqData) {
		Map insResponse = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		//Agent ag = utilService.findAgentByAgentId(head.getAgentId());

		String farmerId = (String) reqData.get("farmerId");
		String farmCode = (String) reqData.get("farmId");
		String blockid = (String) reqData.get("blockid");
		String planting = (String) reqData.get("plantingId");
		String date = (String) reqData.get("date");
		String insects = (String) reqData.get("insects");
		String nameIns = (String) reqData.get("nameIns");
		String perIns = (String) reqData.get("perIns");
		String disease = (String) reqData.get("disease");
		String nameDis = (String) reqData.get("nameDis");
		String perDis = (String) reqData.get("perDis");
		String weed = (String) reqData.get("weed");
		String perWeed = (String) reqData.get("perWeed");
		String recom = (String) reqData.get("recom");
		String weedObs  = (String) reqData.get("weedObs");
		String irrType = (String) reqData.get("irrType");
		String irrMet = (String) reqData.get("irrMet");
		String area = (String) reqData.get("area");
		String source=(String) reqData.get("source");
		ESESystem preferences = utilService.findPrefernceById("1");
		String genDateFormat = preferences.getPreferences().get(ESESystem.GENERAL_DATE_FORMAT);
		
		Long existId = (Long) farmerService.findObjectById(" select id from Scouting fc where fc.msgNo=?",
				new Object[] { head.getMsgNo() });
		if (existId != null && existId > 0) {
			return insResponse;
		}

		Scouting scouting = new Scouting();

		if (!StringUtil.isEmpty(blockid) && !StringUtil.isEmpty(farmCode)) {
			FarmCrops farmCrops = (FarmCrops) farmerService.findObjectById(
					" from FarmCrops fc where fc.blockId=? and fc.farm.farmCode=?  and fc.status=1",
					new Object[] { blockid,farmCode });

			if (farmCrops == null) {
				throw new SwitchException(ITxnErrorCodes.FARM_CROPS_DOES_NOT_EXIST);
			}
			
			if (!StringUtil.isEmpty(planting) && !StringUtil.isEmpty(planting)) {
				Planting ex = (Planting) farmerService.findObjectById(
						" from Planting fc where fc.plantingId=? and fc.status=1",
						new Object[] { planting });
				scouting.setPlanting(ex);
				
				if (ex == null) {
					throw new SwitchException(ITxnErrorCodes.PLANTING_DOES_NOT_EXIST);
				}

			}else{
				throw new SwitchException(ITxnErrorCodes.PLANTING_IS_EMPTY);
			}

			if (farmCrops != null && !ObjectUtil.isEmpty(farmCrops)) {
				scouting.setFarmCrops(farmCrops);
				scouting.setReceivedDate(DateUtil.convertStringToDate(date, DateUtil.DATABASE_DATE_FORMAT));
				scouting.setRecommendations(recom);
				scouting.setAreaIrrrigated(area);
				scouting.setInsectsObserved(insects);
				scouting.setWeedsObserveds(weedObs);
				scouting.setDiseaseObserved(disease);
				scouting.setIrrigationType(irrType);
				scouting.setIrrigationMethod(irrMet);
				scouting.setWeedsPresence(perWeed);
				scouting.setPerInfection(perDis);
				scouting.setNameOfDisease(nameDis);
				scouting.setNameOfInsectsObserved(nameIns);
				scouting.setNameOfWeeds(weed);
				scouting.setPerOrNumberInsects(perIns);
				scouting.setSourceOfWater(source);
				scouting.setCreatedDate(DateUtil.convertStringToDate(head.getTxnTime(), DateUtil.TXN_DATE_TIME));
				scouting.setCreatedUser(head.getAgentId());
				scouting.setMsgNo(head.getMsgNo());
				scouting.setBranchId(head.getBranchId());
				scouting.setStatus(0);
			}

			utilService.save(scouting);

		}

		return insResponse;
	}
}
