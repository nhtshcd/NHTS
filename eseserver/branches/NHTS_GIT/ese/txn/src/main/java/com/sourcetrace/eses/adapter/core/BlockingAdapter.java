package com.sourcetrace.eses.adapter.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.Coordinates;
import com.sourcetrace.eses.entity.CoordinatesMap;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.HarvestSeason;
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
public class BlockingAdapter implements ITxnAdapter{
	@Autowired
	private IUtilService utilService;
	@Autowired
	private IFarmerService farmerService;

	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData) {
		Map response = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		Agent ag = utilService.findAgentByAgentId(head.getAgentId());
		
		String farmCode = (String) reqData.get("farmCode");
		String blockname = (String) reqData.get("blockname");
		String cultiArea = (String) reqData.get("cultiArea");
		String blockId = (String) reqData.get("blockId");
		String season = (String) reqData.get("seasonCode");
		String lats = (String) head.getLat();
		String lons = (String) head.getLon();
		Farm fm = farmerService.findFarmbyFarmCode(farmCode);
		String farmSeq = (String) reqData.get("seq");
		
		Long existId = (Long) farmerService.findObjectById(" select id from FarmCrops fc where fc.msgNo=?",
				new Object[] { head.getMsgNo() });
		if (existId != null && existId > 0) {
			return response;
		}

		FarmCrops f = new FarmCrops();
		if (fm == null) {
			throw new SwitchException(ITxnErrorCodes.FARM_NOT_EXIST);
		}

		f.setFarm(fm);
		f.setBlockId(blockId);
		f.setBlockName(blockname);
		f.setBranchId(fm.getBranchId());
		f.setCropSeason(season);
		f.setCultiArea(cultiArea);
		f.setLongitude(lons);
		f.setLatitude(lats);
		f.setStatus(1);
		f.setMsgNo(head.getMsgNo());
		f.setRevisionNo(DateUtil.getRevisionNumber());
		
		if (ag != null && ag.getExporter() != null) {
			f.setExporter(ag.getExporter());
		}

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
			f.setFarmCropId(fm.getFarmCrops() == null || fm.getFarmCrops().size() == 0 ? "1"
					: String.valueOf(fm.getFarmCrops().stream().mapToInt(u -> Integer.valueOf(u.getFarmCropId())).max()
							.getAsInt() + 1));

		} else {
			f.setFarmCropId(farmSeq);
		}
		
		utilService.save(f);

		return response;

	}

}
