package com.sourcetrace.eses.adapter.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.LandPreparation;
import com.sourcetrace.eses.entity.LandPreparationDetails;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.entity.SitePrepration;
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
public class LandPreparationAdapter implements ITxnAdapter {
	@Autowired
	private IUtilService utilService;
	@Autowired
	private IFarmerService farmerService;

	@Override
	public Map<?, ?> processJson(Map reqData) {
		Map insResponse = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		String farmerId = (String) reqData.get("farmerId");
		String farmId = (String) reqData.get("farmId");
		String blockId = (String) reqData.get("blockId");
		String eDate = (String) reqData.get("eDate");

		Long existId = (Long) farmerService.findObjectById(" select id from LandPreparation fc where fc.msgNo=?",
				new Object[] { head.getMsgNo() });
		if (existId != null && existId > 0) {
			return insResponse;
		}

		LandPreparation landPreparation = new LandPreparation();
		if (!StringUtil.isEmpty(farmId)) {
			Farm fc = (Farm) farmerService.findObjectById(" from Farm fc where fc.farmCode=?  and fc.status=1",
					new Object[] { farmId });
			if (fc == null) {
				throw new SwitchException(ITxnErrorCodes.FARM_NOT_EXIST);
			}
			landPreparation.setFarm(fc);

			landPreparation.setDate(DateUtil.convertStringToDate(eDate, DateUtil.DATABASE_DATE_FORMAT));

		}
		if (!StringUtil.isEmpty(blockId)) {
			FarmCrops fc = (FarmCrops) farmerService.findObjectById(" from FarmCrops fc where fc.blockId=?  and fc.status=1",
					new Object[] { blockId });
			if (fc == null) {
				throw new SwitchException(ITxnErrorCodes.FARM_CROPS_DOES_NOT_EXIST);
			}
			landPreparation.setFarmCrops(fc);

			landPreparation.setDate(DateUtil.convertStringToDate(eDate, DateUtil.DATABASE_DATE_FORMAT));

		}else{
			throw new SwitchException(ITxnErrorCodes.EMPTY_FARM_CROP);
		}
		landPreparation.setLandPreparationDetails(new HashSet<>());
		ArrayList jsonArr = (ArrayList) reqData.get("activityList");
		if (jsonArr != null && jsonArr.size() > 0) {

			jsonArr.stream().forEach(uu -> {
				try {
					LandPreparationDetails landPreparationDetails = new LandPreparationDetails();

					LinkedHashMap jsonObj = (LinkedHashMap) uu;
					String activity = (String) jsonObj.get("activity");
					String mode = (String) jsonObj.get("mode");
					String noOfLab = (String) jsonObj.get("noOfLab");
					landPreparationDetails.setActivity(activity);
					landPreparationDetails.setActivityMode("");
					landPreparationDetails.setNoOfLabourers(noOfLab);
					landPreparationDetails.setLandPreparation(landPreparation);
					landPreparation.getLandPreparationDetails().add(landPreparationDetails);
				} catch (Exception e) {

				}
			});

		}

		landPreparation.setBranchId(head.getBranchId());
		landPreparation.setStatus(1);
		landPreparation.setStatus_code(0);
		landPreparation.setMsgNo(head.getMsgNo());
		utilService.save(landPreparation);
		return insResponse;
	}
}
