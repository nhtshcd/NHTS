package com.sourcetrace.eses.adapter.core;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.DocumentUpload;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.HarvestSeason;
import com.sourcetrace.eses.entity.ProcurementGrade;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.entity.Scouting;
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
public class SitePreperationAdapter implements ITxnAdapter {
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
		String pCrop = (String) reqData.get("pCrop");
		String envAss = (String) reqData.get("envAss");
		String riskAss = (String) reqData.get("riskAss");
		String soilAnal = (String) reqData.get("soilAnal");
		String waterAnal = (String) reqData.get("waterAnal");
		String envPhoto = (String) reqData.get("envPhoto");
		String riskPhoto = (String) reqData.get("riskPhoto");
		String soilPhoto = (String) reqData.get("soilPhoto");
		String waterPhoto = (String) reqData.get("waterPhoto");
		ESESystem preferences = utilService.findPrefernceById("1");
		String blockId = (String) reqData.get("blockId");
		
		Long existId = (Long) farmerService.findObjectById(" select id from SitePrepration fc where fc.msgNo=?",
				new Object[] { head.getMsgNo() });
		if (existId != null && existId > 0) {
			return insResponse;
		}
		
		SitePrepration sitePrepration = new SitePrepration();
		if (!StringUtil.isEmpty(farmId) && !StringUtil.isEmpty(pCrop)) {
			Farm farm = farmerService.findFarmbyFarmCode(farmId);
			if (farm == null) {
				throw new SwitchException(ITxnErrorCodes.FARM_NOT_EXIST);
			}
			
			FarmCrops farmCrop = (FarmCrops) farmerService.findObjectById("from FarmCrops fc where fc.blockId=?",
					new Object[] { blockId });
			if (farmCrop == null) {
				throw new SwitchException(ITxnErrorCodes.FARM_CROPS_DOES_NOT_EXIST);
			}
			
			ProcurementVariety pCropcode = utilService.findProcurementVariertyByCode(pCrop);
			if (pCropcode == null) {
				throw new SwitchException(ITxnErrorCodes.FARM_VARIETY_DOES_NOT_EXIST);
			}
			
			if (farm != null && !ObjectUtil.isEmpty(farm) && pCropcode != null && !ObjectUtil.isEmpty(pCropcode)) {
				sitePrepration.setFarm(farm);
				sitePrepration.setFarmCrops(farmCrop);
				sitePrepration.setPreviousCrop(pCropcode);
				sitePrepration.setEnvironmentalAssessment(envAss);
				sitePrepration.setSocialRiskAssessment(riskAss);
				sitePrepration.setSoilAnalysis(soilAnal);
				sitePrepration.setWaterAnalysis(waterAnal);
			}
			
			if (envPhoto != null && !StringUtil.isEmpty(envPhoto)) {
				/*DocumentUpload d = new DocumentUpload();
				d.setName("SitePrepEnvironment");
				byte[] decodedString;
				try {
					decodedString = Base64.getDecoder().decode(new String(envPhoto).getBytes("UTF-8"));
					d.setContent(decodedString);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				d.setType(DocumentUpload.docType.Farmer.ordinal());
				d.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
				d.setDocFileContentType("jpg");
				d.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec()));
				utilService.save(d);
				sitePrepration.setEnvironmentalAssessmentReport(String.valueOf(d.getRefCode()));*/
				sitePrepration.setEnvironmentalAssessmentReport(envPhoto);
			}
			
			if (riskPhoto != null && !StringUtil.isEmpty(riskPhoto)) {
				/*DocumentUpload d = new DocumentUpload();
				d.setName("SitePrepRisk");
				byte[] decodedString;
				try {
					decodedString = Base64.getDecoder().decode(new String(riskPhoto).getBytes("UTF-8"));
					d.setContent(decodedString);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				d.setType(DocumentUpload.docType.Farmer.ordinal());
				d.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
				d.setDocFileContentType("jpg");
				d.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec()));
				utilService.save(d);
				sitePrepration.setSocialRiskAssessmentReport(String.valueOf(d.getRefCode()));*/
				sitePrepration.setSocialRiskAssessmentReport(riskPhoto);
			}
			
			
			if (soilPhoto != null && !StringUtil.isEmpty(soilPhoto)) {
				/*DocumentUpload d = new DocumentUpload();
				d.setName("SitePrepSoil");
				byte[] decodedString;
				try {
					decodedString = Base64.getDecoder().decode(new String(soilPhoto).getBytes("UTF-8"));
					d.setContent(decodedString);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				d.setType(DocumentUpload.docType.Farmer.ordinal());
				d.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
				d.setDocFileContentType("jpg");
				d.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec()));
				utilService.save(d);
				sitePrepration.setSoilAnalysisReport(String.valueOf(d.getRefCode()));*/
				sitePrepration.setSoilAnalysisReport(soilPhoto);
			}
			
			
			if (waterPhoto != null && !StringUtil.isEmpty(waterPhoto)) {
				/*DocumentUpload d = new DocumentUpload();
				d.setName("SitePrepWater");
				byte[] decodedString;
				try {
					decodedString = Base64.getDecoder().decode(new String(waterPhoto).getBytes("UTF-8"));
					d.setContent(decodedString);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				d.setType(DocumentUpload.docType.Farmer.ordinal());
				d.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
				d.setDocFileContentType("jpg");
				d.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec()));
				utilService.save(d);
				sitePrepration.setWaterAnalysisReport(String.valueOf(d.getRefCode()));*/
				sitePrepration.setWaterAnalysisReport(waterPhoto);
			}

			
			
			sitePrepration.setStatus(1);
			sitePrepration.setBranchId(head.getBranchId());
			sitePrepration.setMsgNo(head.getMsgNo());
			utilService.save(sitePrepration);
		}
		return insResponse;
	}
}
