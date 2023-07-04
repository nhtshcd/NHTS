package com.sourcetrace.eses.adapter.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Coordinates;
import com.sourcetrace.eses.entity.CoordinatesMap;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.HarvestSeason;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.ProcurementGrade;
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
public class PlantingAdapter implements ITxnAdapter {
	@Autowired
	private IUtilService utilService;
	@Autowired
	private IFarmerService farmerService;

	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData) {
		Map response = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		String farmCode = (String) reqData.get("blockId");
		String cultiArea = (String) reqData.get("cultiArea");
		String variety = (String) reqData.get("variety");
		String grade = (String) reqData.get("grade");
		String seedSource = (String) reqData.get("seedSource");
		String plantingDate = (String) reqData.get("plantingDate");
		String season = (String) reqData.get("seasonCode");
		String lotNo = (String) reqData.get("lotNo");
		String cropCat = (String) reqData.get("cropCat");
		String chemUsed = (String) reqData.get("chemUsed");
		String chemQty = (String) reqData.get("chemQty");
		String seedQtyPlanted = (String) reqData.get("seedQtyPlanted");
		String plantingId = (String) reqData.get("plantingId");
		String seedWeek = (String) reqData.get("seedWeek");
		String fert = (String) reqData.get("fert");
		String tyFert = (String) reqData.get("tyFert");
		String fLotNo = (String) reqData.get("fLotNo");
		String fertQty = (String) reqData.get("fertQty");
		String modeApp = (String) reqData.get("modeApp");
		String unit = (String) reqData.get("unit");
		String expHarvestWeek = (String) reqData.get("expHarvestWeek");
		String expHarvestQty = (String) reqData.get("expHarvestQty");
		String lats = (String) reqData.get("lat");
		String lons = (String) reqData.get("lon");
		String fieldType = (String) reqData.get("fieldType");
		
		FarmCrops fm = (FarmCrops) farmerService.findObjectById("from FarmCrops fc where fc.blockId=?",
				new Object[] { farmCode });
		
		String farmSeq = (String) reqData.get("seq");
		
		Long existId = (Long) farmerService.findObjectById(" select id from Planting fc where fc.msgNo=?",
				new Object[] { head.getMsgNo() });
		if (existId != null && existId > 0) {
			return response;
		}

		Planting f = new Planting();
		if (fm == null) {
			throw new SwitchException(ITxnErrorCodes.FARM_CROPS_DOES_NOT_EXIST);
		}
		if (plantingId == null || StringUtil.isEmpty(plantingId)) {
			throw new SwitchException(ITxnErrorCodes.ORDER_NO_EMPTY);
		}

		f.setPlantingId(plantingId);
		f.setFarmCrops(fm);
		f.setBranchId(fm.getBranchId());
		f.setChemQty(chemQty);
		f.setChemUsed(chemUsed);
		f.setCropCategory(cropCat);
		f.setCropSeason(season);
		f.setCultiArea(cultiArea);
		f.setExpHarvestQty(expHarvestQty);
		f.setExpHarvestWeek(expHarvestWeek);
		f.setFertiliser(fert);
		f.setFertQty(fertQty);
		f.setFLotNo(fLotNo);
		f.setSeedSource(seedSource);
		f.setPlantingDate(DateUtil.convertStringToDate(plantingDate, DateUtil.DATABASE_DATE_FORMAT));
		f.setLotNo(lotNo);
		f.setSeedWeek(seedWeek);
		f.setSeedQtyPlanted(seedQtyPlanted);
		f.setTypeOfFert(tyFert);
		f.setUnit(unit);
		f.setModeApp(modeApp);
		f.setLongitude(lons);
		f.setLatitude(lats);
		f.setFieldType(fieldType);
		f.setStatus(1);
		f.setMsgNo(head.getMsgNo());
		ProcurementGrade pg = utilService.findProcurementGradeByCode(grade);
		if (pg != null) {
			f.setGrade(pg);
			f.setVariety(pg.getProcurementVariety());
		//	f.setSpecies(pg.getProcurementVariety().getProcurementProduct());
		}
		f.setRevisionNo(DateUtil.getRevisionNumber());

		/*String midLat = (String) reqData.get("fLon");
		String midLon = (String) reqData.get("fLat");*/
	    String midLat = (String) reqData.get("lon");
		String midLon = (String) reqData.get("lat");
		f.setRevisionNo(DateUtil.getRevisionNumber());
		f.setPlottingStatus("0");
		ArrayList jsonArr = (ArrayList) reqData.get("lAgps");
		if (jsonArr != null && jsonArr.size() > 0) {
			CoordinatesMap coordMa = new CoordinatesMap();
			coordMa.setFarmCoordinates(new HashSet<>());
			coordMa.setAgentId(head.getAgentId());
			coordMa.setArea(cultiArea);
			coordMa.setDate(DateUtil.convertStringToDate(head.getTxnTime(), DateUtil.TXN_DATE_TIME));
			jsonArr.stream().forEach(uu -> {
				try {
					LinkedHashMap jsonObj = (LinkedHashMap) uu;
					String lon = (String) jsonObj.get("laLon");
					String lat = (String) jsonObj.get("laLat");

					String orderNo = (String) jsonObj.get("orderNo");
					Coordinates cc = new Coordinates();
					cc.setLatitude(lat);
					cc.setLongitude(lon);
					cc.setOrderNo(orderNo != null && StringUtil.isLong(orderNo) ? Long.valueOf(orderNo) : 0);
					cc.setCoordinatesMap(coordMa);
					coordMa.getFarmCoordinates().add(cc);
				} catch (Exception e) {
					e.printStackTrace();
				}

			});
			f.setPlottingStatus("1");
			coordMa.setMidLatitude(midLat);
			coordMa.setMidLongitude(midLon);
			f.setPlotting(coordMa);
		}else{
			String midLat1 = (String) head.getLat();
			String midLon2 = (String) head.getLon();
			CoordinatesMap coordMa = new CoordinatesMap();
			coordMa.setMidLatitude(midLat1);
			coordMa.setMidLongitude(midLon2);
			f.setPlottingStatus("0");
			f.setLatitude(coordMa.getMidLatitude());
			f.setLongitude(coordMa.getMidLongitude());
			f.setPlotting(coordMa);
		}
		if (farmSeq == null || StringUtil.isEmpty(farmSeq)) {
			f.setFarmCropId(fm.getPlanting() == null || fm.getPlanting().size() == 0 ? "1"
					: String.valueOf(fm.getPlanting().stream().mapToInt(u -> Integer.valueOf(u.getFarmCropId()))
							.max().getAsInt() + 1));
		} else {
			f.setFarmCropId(farmSeq);
		}
		utilService.save(f);

		return response;

	}

}
