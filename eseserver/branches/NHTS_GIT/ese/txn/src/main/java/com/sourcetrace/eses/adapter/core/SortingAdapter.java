package com.sourcetrace.eses.adapter.core;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.Scouting;
import com.sourcetrace.eses.entity.Sorting;
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
public class SortingAdapter  implements ITxnAdapter {

	@Autowired
	private IFarmerService farmerService;

	@Autowired
	private IUtilService utilService;

	@SuppressWarnings("unused")
	@Override
	public Map<?, ?> processJson(Map reqData) {
		Map insResponse = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);

		String blockId = (String) reqData.get("blockId");
		String qtyHarvested = (String) reqData.get("harQty");
		String qtyRejected = (String) reqData.get("rejQty");
		String qtyNet = (String) reqData.get("netQty");
		String status = (String) reqData.get("status");
		String farmcode = (String) reqData.get("farmcode");
		
		String plantingId = (String) reqData.get("plantingId");
		
		String trType = (String) reqData.get("trType");
		String trNo = (String) reqData.get("trNo");
		String drName = (String) reqData.get("drName");
		String drCt = (String) reqData.get("drCt");
		String QRCodeUnq = (String) reqData.get("QRCodeUnq");
		
		Sorting sorting=new Sorting();
		Long existId = (Long) farmerService.findObjectById(" select id from Sorting fc where fc.msgNo=?",
				new Object[] { head.getMsgNo() });
		if (existId != null && existId > 0) {
			return insResponse;
		}
		if (!StringUtil.isEmpty(blockId) ) {
			Planting farmCrops = (Planting) farmerService.findObjectById(
					" from Planting fc where fc.plantingId=? and fc.status=1",
					new Object[] { plantingId });
			if (farmCrops == null) {
				throw new SwitchException(ITxnErrorCodes.PLANTING_DOES_NOT_EXIST);
			}
			if (farmCrops != null) {
				sorting.setPlanting(farmCrops);
				sorting.setFarmCrops(farmCrops.getFarmCrops());
				sorting.setQtyHarvested(Double.valueOf(qtyHarvested));
				sorting.setQtyRejected(Double.valueOf(qtyRejected));
				sorting.setQtyNet(Double.valueOf(qtyNet));
				sorting.setStatus(1);
			}
			if (QRCodeUnq !=null && !StringUtil.isEmpty(QRCodeUnq)) {
				sorting.setQrCodeId(QRCodeUnq);
			}else{
				sorting.setQrCodeId(String.valueOf(DateUtil.getRevisionNumberMills()));
			}
			sorting.setStatus(1);
			sorting.setBranchId(head.getBranchId());
			sorting.setMsgNo(head.getMsgNo());
			sorting.setTruckNo(trNo);
			sorting.setTruckType(trType);
			sorting.setDriverCont(drCt);
			sorting.setDriverName(drName);
			sorting.setCreatedDate(new Date());
			sorting.setCreatedUser(head.getAgentId());
			utilService.addSorting(sorting);
		}
		return insResponse;
	}
}
