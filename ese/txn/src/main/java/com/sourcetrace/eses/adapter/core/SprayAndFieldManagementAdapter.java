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

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Pcbp;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.Scouting;
import com.sourcetrace.eses.entity.SprayAndFieldManagement;
import com.sourcetrace.eses.interceptor.ITxnErrorCodes;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.exception.SwitchException;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.StringUtil;

@Component
public class SprayAndFieldManagementAdapter implements ITxnAdapter {

	@Autowired
	private IFarmerService farmerService;

	@Autowired
	private IUtilService utilService;

	@Override
	public Map<?, ?> processJson(Map reqData) {
		Map insResponse = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		String farmCode = (String) reqData.get("farmcode");
		String plantingId = (String) reqData.get("plantingId");
		String blockId = (String) reqData.get("blockId");
		String date = (String) reqData.get("date");
		String chem = (String) reqData.get("chem");
		String dose = (String) reqData.get("dose");
		String uom = (String) reqData.get("uom");
		String operator = (String) reqData.get("operator");
		String sprayMob = (String) reqData.get("sprayMob");
		String appEq = (String) reqData.get("appEq");
		String moa = (String) reqData.get("moa");
		String ph = (String) reqData.get("ph");
		String trOpe = (String) reqData.get("trOpe");
		String agrovet = (String) reqData.get("agrovet");
		String dateOfCal = (String) reqData.get("dateOfCal");
		String dis = (String) reqData.get("dis");
		String endSprayDate  = (String) reqData.get("endSprayDate");
		String oprMedRpt  = (String) reqData.get("oprMedRpt");
		String activeIngredient  = (String) reqData.get("activeIngredient");
		String recommendation  = (String) reqData.get("recommendation");
		
		Long existId = (Long) farmerService.findObjectById(
				" select id from SprayAndFieldManagement fc where fc.msgNo=?", new Object[] { head.getMsgNo() });
		if (existId != null && existId > 0) {
			return insResponse;
		}

		FarmCrops fc = (FarmCrops) farmerService.findObjectById(
				" from FarmCrops fc where fc.blockId=? and fc.farm.farmCode=?  and fc.status=1",
				new Object[] { blockId, farmCode });

		if (fc == null) {
			throw new SwitchException(ITxnErrorCodes.FARM_CROPS_DOES_NOT_EXIST);
		}
		
		Planting pl = (Planting) farmerService.findObjectById(
				" from Planting fc where fc.plantingId=? and fc.status=1",
				new Object[] { plantingId});

		if (pl == null) {
			throw new SwitchException(ITxnErrorCodes.PLANTING_DOES_NOT_EXIST);
		}

		SprayAndFieldManagement sp = new SprayAndFieldManagement();
		sp.setDate(DateUtil.convertStringToDate(date, DateUtil.DATABASE_DATE_FORMAT));
		if (chem != null && StringUtil.isLong(chem)) {
			Pcbp pcpb = (Pcbp) farmerService.findObjectById(" from Pcbp fc where fc.id=?",
					new Object[] { Long.valueOf(chem) });
			sp.setPcbp(pcpb);
		} else {
			sp.setAgroChemicalName(chem);
		}
		sp.setPlanting(pl);
		sp.setDosage(dose);
		sp.setBranchId(fc.getBranchId());
		sp.setUom(uom);
		sp.setNameOfOperator(operator);
		sp.setSprayMobileNumber(sprayMob);
		sp.setOperatorMedicalReport(oprMedRpt);
		sp.setTypeApplicationEquipment(appEq);
		sp.setMethodOfApplication(moa);
		sp.setPhi(ph);
		sp.setTrainingStatusOfSprayOperator(trOpe);
		sp.setAgrovetOrSupplierOfTheChemical(agrovet);
		sp.setLastDateOfCalibration(DateUtil.convertStringToDate(dateOfCal, DateUtil.DATABASE_DATE_FORMAT));
		sp.setInsectTargeted(dis);
		sp.setCreatedDate(DateUtil.convertStringToDate(head.getTxnTime(), DateUtil.TXN_DATE_TIME));
		sp.setCreatedUser(head.getAgentId());
		sp.setMsgNo(head.getMsgNo());
		/*sp.setFarmCrops(fc);*/
		sp.setDeleteStatus(0);
		sp.setActiveIngredient(activeIngredient);
		sp.setRecommen(recommendation);
		sp.setDateOfSpraying(DateUtil.convertStringToDate(date, DateUtil.DATABASE_DATE_FORMAT));
		sp.setEndDateSpray(endSprayDate!=null && !StringUtil.isEmpty(endSprayDate) ? DateUtil.convertStringToDate(endSprayDate, DateUtil.DATABASE_DATE_FORMAT) :null);
		
		if (!StringUtil.isEmpty(sp.getDateOfSpraying()) && !StringUtil.isEmpty(sp.getPhi()) && sp.getPhi()!=null && sp.getDateOfSpraying()!=null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(sp.getDateOfSpraying());
			cal.add(Calendar.DATE, Integer.valueOf(sp.getPhi())); 
			sp.setDayOfPHIandSprayingDate(cal.getTime());
			}
		
		farmerService.save(sp);
		return insResponse;
	}
}
