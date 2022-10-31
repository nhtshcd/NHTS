package com.sourcetrace.eses.adapter.core;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.DocumentUpload;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.Village;
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
public class FarmerEnrollment implements ITxnAdapter {
	@Autowired
	private IUtilService utilService;
	@Autowired
	private IFarmerService farmerService;

	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData) {
		Map response = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		Agent ag = utilService.findAgentByAgentId(head.getAgentId());

		String fName = (String) reqData.get("fName");
		String farmerId = (String) reqData.get("farmerId");
		String gender = (String) reqData.get("gender");
		String Age = (String) reqData.get("Age");
		String dob = (String) reqData.get("dob");
		String phoneNo = (String) reqData.get("phoneNo");
		String village = (String) reqData.get("village");
		String photo = (String) reqData.get("photo");
		String idProofImg = (String) reqData.get("idProofImg");

		String email = (String) reqData.get("email");
		String noOfMembers = (String) reqData.get("noOfMembers");

		String adCntFe = (String) reqData.get("adCntFe");
		String schChCntFe = (String) reqData.get("schChCntFe");
		String chCntFe = (String) reqData.get("chCntFe");
		String education = (String) reqData.get("education");
		String assemnt = (String) reqData.get("assemnt");
		String ht = (String) reqData.get("ht");
		String hop = (String) reqData.get("hop");

		String pcTime = (String) reqData.get("pcTime");
		String lat = (String) reqData.get("lat");
		String lon = (String) reqData.get("lon");
		String cropCategory = (String) reqData.get("cropCategory");
		String cropCode = (String) reqData.get("cropname");
		String idpValue = (String) reqData.get("idpValue");
		String vName = (String) reqData.get("vName");
		String city = (String) reqData.get("city");
		String farmerCat = (String) reqData.get("fcategory");
		
		String krapin = (String) reqData.get("krapin");
		String compName = (String) reqData.get("compName");
		String regCert = (String) reqData.get("regCert");
		String farmerType = (String) reqData.get("fType");
		
		Long existId = (Long) farmerService.findObjectById(" select id from Farmer fc where fc.msgNo=?",
				new Object[] { head.getMsgNo() });
		if (existId != null && existId > 0) {
			return response;
		}

		Farmer f = new Farmer();
		Farmer fm = farmerService.findFarmerByFarmerId(farmerId);
		if (fm != null) {
			throw new SwitchException(ITxnErrorCodes.FARMER_EXIST);
		}
		
		if (cropCategory == null || StringUtil.isEmpty(cropCategory)) {
			throw new SwitchException(ITxnErrorCodes.EMPTY_CROP_CATEGORY);
		}
		
		if (cropCode == null || StringUtil.isEmpty(cropCode)) {
			throw new SwitchException(ITxnErrorCodes.EMPTY_CROP_NAME);
		}

		cropCategory = cropCategory != null && !cropCategory.contains(",") ? cropCategory + "," : cropCategory;
		cropCode = cropCode != null && !cropCode.contains(",") ? cropCode + "," : cropCode;
		String ids = farmerService.getValueByQuery(
				"select group_concat(id) from Procurement_Product pp where pp.code in (:param1)",
				new Object[] { cropCategory }, ag.getBranchId());
		String crops = farmerService.getValueByQuery(
				"select group_concat(id) from Procurement_variety pp where pp.code in (:param1)",
				new Object[] { cropCode }, ag.getBranchId());
		f.setBranchId(ag.getBranchId());
		f.setStatus(1);
		f.setIsActive(1l);
		f.setStatusCode(0);
		f.setFirstName(fName);
		f.setFarmerId(farmerId);
		f.setFarmerCode(farmerId);
		f.setAge(Age!=null && !StringUtil.isEmpty(Age)  ? Age : null);
		f.setDateOfBirth(dob!=null && !StringUtil.isEmpty(dob)  ? DateUtil.convertStringToDate(dob, DateUtil.DATE_FORMAT_2) :null);
		f.setGender(gender);
		f.setAsset(assemnt);
		f.setChildBelow(chCntFe);
		f.setCropCategory(ids);
		f.setCropName(crops);
		f.setEmailId(email);
		f.setLongitude(lon);
		f.setLatitude(lat);
		f.setNid(idpValue);
		f.setNoOfFamilyMember(noOfMembers);
		f.setAdultAbove(adCntFe);
		f.setSchoolGoingChild(schChCntFe);
		f.setHedu(education);
		f.setOwnership(hop);
		f.setHouse(ht);
		f.setFarmerCat(farmerCat);
		
		f.setCompanyName(compName);
		f.setKraPin(krapin);
		f.setFarmerRegType(farmerType);
		f.setRegistrationCertificate(regCert);
		
		f.setMsgNo(head.getMsgNo());

		Village v = utilService.findVillageByCode(village);

		if (v == null && vName != null && !StringUtil.isEmpty(vName)) {
			Municipality c = utilService.findMunicipalityByCode(city);
			if (c != null && c.getVillages().stream().anyMatch(uu -> uu.getName().equals(vName))) {
				v = c.getVillages().stream().filter(uu -> uu.getName().equals(vName)).findFirst().get();
			} else if (c != null) {
				v = new Village();
				v.setCode(village);
				v.setName(vName);
				v.setCity(c);
				v.setRevisionNo(DateUtil.getRevisionNumber());
				v.setBranchId(c.getBranchId());
				utilService.save(v);
			}
		}

		if (v != null)
			f.setVillage(v);

	/*	if (ag != null && ag.getExporter() != null) {
			f.setExporters(String.valueOf(ag.getExporter().getId()));
		}*/
		f.setMobileNo(phoneNo);
		f.setCreatedDate(DateUtil.convertStringToDate(head.getTxnTime(), DateUtil.TXN_DATE_TIME));
		f.setCreatedUser(head.getAgentId());
		
		if (photo != null && !StringUtil.isEmpty(photo)) {
			DocumentUpload d = new DocumentUpload();
			d.setName(f.getFirstName());
			byte[] decodedString;
			try {
				decodedString = Base64.getDecoder().decode(new String(photo).getBytes("UTF-8"));
				d.setContent(decodedString);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			d.setType(DocumentUpload.docType.Farmer.ordinal());
			d.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
			d.setDocFileContentType("jpg");
			d.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec())+"1");
			utilService.save(d);
			f.setFarmerPhoto(String.valueOf(d.getRefCode()));
		}
		
	/*	if (regCert != null && !StringUtil.isEmpty(regCert)) {
			DocumentUpload d = new DocumentUpload();
			d.setName(f.getFirstName());
			byte[] decodedString;
			try {
				decodedString = Base64.getDecoder().decode(new String(regCert).getBytes("UTF-8"));
				d.setContent(decodedString);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			d.setType(DocumentUpload.docType.Farmer.ordinal());
			d.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
			d.setDocFileContentType("jpg");
			d.setRefCode(String.valueOf(f.getFarmerId()));
			utilService.save(d);
			f.setRegistrationCertificate(String.valueOf(d.getId()));
		}*/

		if (idProofImg != null && !StringUtil.isEmpty(idProofImg)) {
			DocumentUpload d = new DocumentUpload();
			d.setName(f.getFirstName() + "_IdProof");
			byte[] decodedString;
			try {
				decodedString = Base64.getDecoder().decode(new String(idProofImg).getBytes("UTF-8"));
				d.setContent(decodedString);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			d.setType(DocumentUpload.docType.Farmer.ordinal());
			d.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
			d.setDocFileContentType("jpg");
			d.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec()));
			utilService.save(d);
			f.setPhotoNid(String.valueOf(d.getRefCode()));
			/*f.setPhotoNid(String.valueOf(idProofImg));*/
		}

		/*if (f.getNid() != null && !StringUtil.isEmpty(f.getNid())) {
		Object[] isExist = utilService.findIfFarmerExistForFarmer(f.getNid());
		StringBuffer st = new StringBuffer();
		if (isExist != null && isExist.length == 1) {
		
			if (isExist[0] != null && Arrays.asList(isExist[0].toString().split(",")).contains(f.getNid())) {

				st.append(", Duplicate Nid");

			}
			if (!st.toString().isEmpty()) {
				f.setStatusMsg(st.toString());
				f.setStatusCode(1);
				f.setStatus(0);
			}

		}
		}*/
		
		if (f.getNid() != null && !StringUtil.isEmpty(f.getNid()) && f.getMobileNo() != null && !StringUtil.isEmpty(f.getMobileNo())){
		Object[] isExist = utilService.findIfFarmerExist(f.getMobileNo(), f.getNid());
		StringBuffer st = new StringBuffer();
		if (isExist != null && isExist.length == 2) {
			if (isExist[0] != null && Arrays.asList(isExist[0].toString().split(",")).contains(f.getMobileNo())) {
				
				st.append("Duplicate Phone");
				
			}
			
			if (isExist[1] != null && Arrays.asList(isExist[1].toString().split(",")).contains(f.getNid())) {
				
				st.append(", Duplicate Nid");
				
			}
			if (!st.toString().isEmpty()) {
				f.setStatusMsg(st.toString());
				f.setStatusCode(1);
				f.setStatus(0);
			}
			
		}
		}
		f.setRevisionNo(DateUtil.getRevisionNumber());
		utilService.save(f);

		if (!StringUtil.isEmpty(farmerId)) {
			if (Long.parseLong(ag.getFarmerCurrentIdSeq()) < Long.parseLong(farmerId)) {
				ag.setFarmerCurrentIdSeq(farmerId);
				utilService.editAgent(ag);
			}
		}

		return response;

	}

}
