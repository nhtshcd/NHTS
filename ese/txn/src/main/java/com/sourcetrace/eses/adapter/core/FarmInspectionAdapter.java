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

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sourcetrace.eses.entity.DocumentUpload;
import com.sourcetrace.eses.entity.ESESystem;




import com.sourcetrace.eses.entity.Farmer;
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
public class FarmInspectionAdapter implements ITxnAdapter {

    @Autowired
    private IFarmerService farmerService;
    @Autowired
    private IUtilService utilService;
   
    @Override
	public Map<?, ?> processJson(Map reqData) {
    	Map insResponse = new LinkedHashMap<>();
    	 Head head = (Head) reqData.get(TransactionProperties.HEAD);
         
    	 String insDate=(String) reqData.get(TransactionProperties.TRANS_DATE);
    	 String contactPerson=(String) reqData.get(TxnEnrollmentProperties.CONTACT_PERSON);
    	 String greenHouse=(String) reqData.get(TxnEnrollmentProperties.GREEN_HOUSE);
    	 String farmCropId=(String) reqData.get(TxnEnrollmentProperties.CROP_NAME);
    	 String observation=(String) reqData.get(TxnEnrollmentProperties.OBSERVATION);
    	 String recommend=(String) reqData.get(TxnEnrollmentProperties.RECOMMEND);
    	 String latitude=(String) reqData.get(TxnEnrollmentProperties.LATITUDE);
    	 String longitude=(String) reqData.get(TxnEnrollmentProperties.LONGITUDE);
    	 String photoStr=(String) reqData.get(TxnEnrollmentProperties.PHOTO);
    	 String insSignStr=(String) reqData.get(TxnEnrollmentProperties.INSPECTOR_SIGN);
    	 String ownerSignStr=(String) reqData.get(TxnEnrollmentProperties.OWNER_SIGN);
    	 String audioStr=(String) reqData.get(TxnEnrollmentProperties.INS_AUDIO);
    	 String previous=(String) reqData.get(TxnEnrollmentProperties.PREVIOUS);
    	 String statusGreen=(String) reqData.get(TxnEnrollmentProperties.STATUS_GREEN);
    	 String traceability=(String) reqData.get(TxnEnrollmentProperties.TRACEABILITY);
    	 String pestStatus=(String) reqData.get(TxnEnrollmentProperties.PEST_STATUS);
    	 String pestParticulars=(String) reqData.get(TxnEnrollmentProperties.PEST_PARTICULAR);
    	 String pestLocation=(String) reqData.get(TxnEnrollmentProperties.PEST_LOCATION);
    	 String cropProtection=(String) reqData.get(TxnEnrollmentProperties.CROP_PRODUCTION);
    	 String  modeTransportation	=(String) reqData.get(TxnEnrollmentProperties.MODE_TRANSPORT);
    	 String harvestInspection=(String) reqData.get(TxnEnrollmentProperties.HARVEST_INS);
    	 String specification=(String) reqData.get(TxnEnrollmentProperties.SPECIFICATION);
    	 String hygieneStatus=(String) reqData.get(TxnEnrollmentProperties.HYGIENE_STATUS);
    	 String hygieneDescription=(String) reqData.get(TxnEnrollmentProperties.HYGIENE_DES);
    	 String gradingHall=(String) reqData.get(TxnEnrollmentProperties.GRADING_HALL);
    	 String gradingDescription=(String) reqData.get(TxnEnrollmentProperties.GRADING_DES);
    	 String gradinghallStaff=(String) reqData.get(TxnEnrollmentProperties.GRADING_STAFF);
    	 String farmEquipment=(String) reqData.get(TxnEnrollmentProperties.FARM_EQUIPMENT);
    	 String  evidence=(String) reqData.get(TxnEnrollmentProperties.EVIDENCE);
    	 String supplyStatus=(String) reqData.get(TxnEnrollmentProperties.SUPPLY_STATUS);
    	 String  rejectReason=(String) reqData.get(TxnEnrollmentProperties.REJECT_REASON);
    	 String noScouters=(String) reqData.get(TxnEnrollmentProperties.NO_SCOUTERS);
    	 String additional=(String) reqData.get(TxnEnrollmentProperties.ADDTIONAL_INFO);
    	 String evidencePhotoStr=(String) reqData.get(TxnEnrollmentProperties.EVIDENCE_PHOTO);
    	 ESESystem preferences = utilService.findPrefernceById("1");
    	 String genDateFormat=preferences.getPreferences().get(ESESystem.GENERAL_DATE_FORMAT);
    	
    	 if(farmCropId!=null && !StringUtil.isEmpty(farmCropId)){
    		
    	 }
    
    	return insResponse;
    }
}
