package com.sourcetrace.eses.adapter.core;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Coordinates;
import com.sourcetrace.eses.entity.CoordinatesMap;
import com.sourcetrace.eses.entity.DocumentUpload;
import com.sourcetrace.eses.entity.Farm;
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
public class FarmEnrollment implements ITxnAdapter {
	@Autowired
	private IUtilService utilService;
	@Autowired
	private IFarmerService farmerService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData) {
		Map response = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		String farmName = (String) reqData.get("farmName");
		String farmerId = (String) reqData.get("farmerId");
		String farmCode = (String) reqData.get("farmCode");
		String totLaAra = (String) reqData.get("totLaAra");
		String farmSeq = (String) reqData.get("seq");
		String prPlAra = (String) reqData.get("prPlAra");
		String fo = (String) reqData.get("fo");
		String photo = (String) reqData.get("fPhoto");
		String isAddressSame = (String) reqData.get("isSameAddress");

		String address = (String) reqData.get("farmAddress");
		String regNo = (String) reqData.get("regNo");
		String ldTopo = (String) reqData.get("ldTopo");
		String ldGrd = (String) reqData.get("ldGrd");
		String regPhoto = (String) reqData.get("regPhoto");
		String village = (String) reqData.get("village");
		String vName = (String) reqData.get("vName");
		String city = (String) reqData.get("city");
	
		Long existId = (Long) farmerService.findObjectById(" select id from Farm fc where fc.msgNo=?",
				new Object[] { head.getMsgNo() });
		if (existId != null && existId > 0) {
			return response;
		}
		
		Farm f = new Farm();
		Farmer fm = farmerService.findFarmerByFarmerId(farmerId);
		if (fm == null) {
			throw new SwitchException(ITxnErrorCodes.FARMER_NOT_EXIST);
		}
		
		Farm existfa =(Farm) farmerService.findObjectById(" from Farm f where f.farmCode = ? and status=1",new Object[]{farmCode});
		if(existfa!=null){
			throw new SwitchException(ITxnErrorCodes.FARM_CODE_EXIST);
		}
		

		f.setBranchId(fm.getBranchId());
		f.setStatus(1);
		f.setFarmer(fm);
		f.setFarmName(farmName);
		f.setAddress(address);
		f.setIsAddressSame(isAddressSame);
		f.setFarmCode(farmCode);
		f.setTotalLandHolding(totLaAra);
		f.setProposedPlanting(prPlAra);
		f.setLandRegNo(regNo);
		f.setLandTopography(ldTopo);
		f.setLandOwnership(fo);
		f.setLandGradient(ldGrd);
		f.setCreatedDate(DateUtil.convertStringToDate(head.getTxnTime(), DateUtil.TXN_DATE_TIME));
		f.setCreatedUser(head.getAgentId());
		f.setMsgNo(head.getMsgNo());
		if (photo != null && !StringUtil.isEmpty(photo)) {
			DocumentUpload d = new DocumentUpload();
			d.setName(f.getFarmName());
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
			d.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec()));
			utilService.save(d);
			f.setPhoto(String.valueOf(d.getRefCode()));
		}

		if (regPhoto != null && !StringUtil.isEmpty(regPhoto)) {
			DocumentUpload d = new DocumentUpload();
			d.setName(f.getFarmName() + "_Reg");
			byte[] decodedString;
			try {
				decodedString = Base64.getDecoder().decode(new String(regPhoto).getBytes("UTF-8"));
				d.setContent(decodedString);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			d.setType(DocumentUpload.docType.Farmer.ordinal());
			d.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
			d.setDocFileContentType("jpg");
			d.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec())+"1");
			utilService.save(d);
			f.setLandRegDocs(String.valueOf(d.getRefCode()));
		}
		/*	String midLat = (String) reqData.get("fLon");
		String midLon = (String) reqData.get("fLat");*/
		String midLat = (String) reqData.get("lon");
		String midLon = (String) reqData.get("lat");
		f.setRevisionNo(DateUtil.getRevisionNumber());
		ArrayList jsonArr = (ArrayList) reqData.get("lAgps");
		f.setPlottingStatus("0");
		if (jsonArr != null && jsonArr.size() > 0) {
			CoordinatesMap coordMa = new CoordinatesMap();
			coordMa.setFarmCoordinates(new HashSet<>());
			coordMa.setAgentId(head.getAgentId());
			coordMa.setArea(totLaAra);
			coordMa.setDate(f.getCreatedDate());
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
			coordMa.setMidLatitude(midLat);
			coordMa.setMidLongitude(midLon);
			f.setPlottingStatus("1");
			f.setLatitude(coordMa.getMidLatitude());
			f.setLongitude(coordMa.getMidLongitude());
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
		if(farmSeq==null || StringUtil.isEmpty(farmSeq)){
			f.setFarmId(fm.getFarms()==null || fm.getFarms().size()==0 ? "1" :  String.valueOf(fm.getFarms().stream().mapToInt(u -> Integer.valueOf(u.getFarmId())).max().getAsInt()+1));
				
		}else{
			f.setFarmId(farmSeq);
		}
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
		f.setRevisionNo(DateUtil.getRevisionNumber());
		utilService.save(f);
		return response;

	}

}
