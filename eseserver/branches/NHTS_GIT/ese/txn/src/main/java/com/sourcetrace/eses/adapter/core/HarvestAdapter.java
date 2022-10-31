package com.sourcetrace.eses.adapter.core;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Harvest;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.interceptor.ITxnErrorCodes;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.exception.SwitchException;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.service.IFarmerService;

@Component
public class HarvestAdapter implements ITxnAdapter {

	@Autowired
	private IUniqueIDGenerator idGenerator;

	@Autowired
	private IUtilService utilService;

	@Autowired
	private IFarmerService farmerService;

	@Override
	public Map<?, ?> processJson(Map reqData) {
		Map insResponse = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		String farmerId = (String) reqData.get("farmerId");
		String farmCode = (String) reqData.get("farmCode");
		String blockId = (String) reqData.get("blockId");
		String plantingId = (String) reqData.get("plantingId");
		String date = (String) reqData.get("date");
		String noStems = (String) reqData.get("noStems");
		String qtyHvst = (String) reqData.get("qtyHvst");
		String yldHvst = (String) reqData.get("yldHvst");
		String expVol = (String) reqData.get("expVol");
		String nmeHvst = (String) reqData.get("nmeHvst");
		String eqpHvst = (String) reqData.get("eqpHvst");
		String noUnts = (String) reqData.get("noUnts");
		String pkUnt = (String) reqData.get("pkUnt");
		String dlyTyp = (String) reqData.get("dlyTyp");
		String obsPhi = (String) reqData.get("obsPhi");
		String weightType = (String) reqData.get("weightType");

		Long existId = (Long) farmerService.findObjectById(" select id from Harvest fc where fc.msgNo=?",
				new Object[] { head.getMsgNo() });
		if (existId != null && existId > 0) {
			return insResponse;
		}

		Harvest harvest = new Harvest();
		harvest.setBranchId(head.getBranchId());
		harvest.setDate(date != null && !StringUtil.isEmpty(date)
				? DateUtil.convertStringToDate(date, DateUtil.DATABASE_DATE_FORMAT) : null);
		if (!StringUtil.isEmpty(plantingId) && !StringUtil.isEmpty(farmCode)) {
			
			FarmCrops farmCrop = (FarmCrops) farmerService.findObjectById(
					" from FarmCrops fc where fc.blockId=? and fc.farm.farmCode=?  and fc.status=1",
					new Object[] { blockId,farmCode });

			if (farmCrop == null) {
				throw new SwitchException(ITxnErrorCodes.FARM_CROPS_DOES_NOT_EXIST);
			}
			
			
			Planting planting = (Planting) farmerService.findObjectById(
					" from Planting fc where fc.plantingId=? and fc.farmCrops.farm.farmCode=?  and fc.status=1",
					new Object[] { plantingId, farmCode });

			if (planting == null) {
				throw new SwitchException(ITxnErrorCodes.PLANTING_DOES_NOT_EXIST);
			}
			
			FarmCrops fc = (FarmCrops) farmerService.findObjectById(
					"FROM FarmCrops fc WHERE fc.blockId=? and fc.farm.farmCode=? and fc.exporter.status=1 and fc.exporter.isActive=1 and fc.status=1",
					new Object[] { blockId, farmCode });
			if(fc == null || StringUtil.isEmpty(fc)){
				harvest.setStatus(3);
			}else{
				harvest.setStatus(1);
			}
			harvest.setDeliveryType(dlyTyp);
			harvest.setExpctdYieldsVolume(expVol != null ? Double.valueOf(expVol) : 0);
			harvest.setHarvestEquipment(eqpHvst);
			harvest.setNameHarvester(nmeHvst);
			harvest.setNoStems(noStems != null && noStems != "" ? Double.valueOf(noStems):0);
			harvest.setNoUnits(noUnts != null && noUnts != "" ? Integer.valueOf(noUnts):0);
			harvest.setObservationPhi(obsPhi);
			harvest.setPackingUnit(pkUnt);
			harvest.setQtyHarvested(qtyHvst != null && qtyHvst != "" ? Double.valueOf(qtyHvst) : 0);
			harvest.setYieldsHarvested(yldHvst != null && qtyHvst != "" ? Double.valueOf(yldHvst) : 0);
			harvest.setPlanting(planting);
			harvest.setFarmCrops(farmCrop);
			harvest.setProduceId(blockId);
			
			harvest.setCreatedDate(new Date());
			harvest.setCreatedUser(head.getAgentId());
			harvest.setBranchId(head.getBranchId());
			harvest.setMsgNo(head.getMsgNo());
			harvest.setHarvestType(weightType);
			utilService.saveHarvest(harvest);
		}
		return insResponse;
	}
}
