package com.sourcetrace.eses.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.CoordinatesMap;
import com.sourcetrace.eses.entity.Country;
import com.sourcetrace.eses.entity.DocumentUpload;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.Locality;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.ParentEntity;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.entity.Village;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.FileUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class FarmAction extends SwitchAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IUniqueIDGenerator idGenerator;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String redirectContent;

	@Getter
	@Setter
	protected String command;

	@Getter
	@Setter
	protected File photoFile;

	@Getter
	@Setter
	protected String photoFileName;

	@Getter
	@Setter
	protected String photoFileType;

	@Getter
	@Setter
	protected File landRegDocsFile;

	@Getter
	@Setter
	protected String landRegDocsFileName;

	@Getter
	@Setter
	protected String landRegDocsFileType;

	@Getter
	@Setter
	private String status;

	@Getter
	@Setter
	protected Farm farm;

	@Getter
	@Setter
	private String farmerId;

	@Getter
	@Setter
	private String farmerName;

	@Getter
	@Setter
	private String farmerUniqueId;

	@Getter
	@Setter
	private String selectedFarmerId;

	@Autowired
	private IFarmerService farmerService;

	public String detail() throws Exception {
		String view = null;
		if (id != null && !StringUtil.isEmpty(id)) {
			farm = utilService.findFarmById(Long.valueOf(id));
			if (farm == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			String isSAddr = getYesNoList().get(farm.getIsAddressSame());
			if (isSAddr != null && StringUtil.isEmpty(isSAddr))
				farm.setIsAddressSame(isSAddr);

			setCurrentPage(getCurrentPage());
			command = UPDATE;
			view = DETAIL;
			if (farm.getPlotting() != null && farm.getPlotting().getFarmCoordinates() != null
					&& !ObjectUtil.isListEmpty(farm.getPlotting().getFarmCoordinates())) {
				jsonObjectList = getFarmJSONObjects(farm.getPlotting().getFarmCoordinates());
			} else {
				jsonObjectList = new ArrayList();
			}
			request.setAttribute(HEADING, getText("farmdetail"));
		} else {
			request.setAttribute(HEADING, getText(LIST));
			return REDIRECT;
		}
		return view;
	}

	public String create() throws Exception {

		if (farm == null) {
			setCommand(CREATE);
			if (StringUtil.isEmpty(this.farmerId)) {
				return LIST;
			}

			Farmer farmer = new Farmer();
			 farm = new Farm();
			// farmer.setFarmerId(this.farmerId);
			farmer = farmerService.findFarmerByFarmerId(this.farmerId);
			farm.setFarmer(farmer);
			farm.setVillage(farmer.getVillage());
			farm.setIsAddressSame("1");
			setFarmerUniqueId(String.valueOf(farmer.getId()));
			if (getFarmerName() == null || getFarmerName().isEmpty()) {
				setFarmerName(farmer.getFirstName());
			}

			request.setAttribute(HEADING, getText("farmcreate.page"));

			return INPUT;
		} else {
			Farmer f = farmerService.findFarmerById(farm.getFarmer().getId());
			if (photoFile != null) {
				DocumentUpload du = new DocumentUpload();
				String[] tokens = photoFileName.split("\\.(?=[^\\.]+$)");
				String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
				du.setName(name);
				du.setContent(FileUtil.getBinaryFileContent(photoFile));
				du.setDocFileContentType(photoFileType);
				du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec()));
				du.setType(DocumentUpload.docType.IMPORT_APP.ordinal());
				du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
				utilService.save(du);
				farm.setPhoto(du.getRefCode().toString());
			}
			if (landRegDocsFile != null) {
				DocumentUpload du = new DocumentUpload();
				String[] tokens = landRegDocsFileName.split("\\.(?=[^\\.]+$)");
				String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
				du.setName(name);
				du.setContent(FileUtil.getBinaryFileContent(landRegDocsFile));
				du.setDocFileContentType(landRegDocsFileType);
				du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec())+"1");
				du.setType(DocumentUpload.docType.IMPORT_APP.ordinal());
				du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
				utilService.save(du);
				farm.setLandRegDocs(du.getRefCode().toString());
			}
			farm.setFarmer(f);
			farm.setFarmId(f.getFarms() != null ? String.valueOf(f.getFarms().size() + 1) : "1");
			farm.setFarmCode(farm.getFarmer().getVillage().getCity().getLocality().getState().getCode() + "_"
					+ farm.getFarmer().getVillage().getCity().getLocality().getCode() + "_"
					+ farm.getFarmer().getVillage().getCity().getCode() + "_" + farm.getFarmer().getFarmerId() + "_"
					+ StringUtil.appendZeroPrefix(String.valueOf(farm.getFarmId()), 3));
			farm.setBranchId(getBranchId());
			farm.setStatus(ParentEntity.Active.ACTIVE.ordinal());
			// farm.setFarmId(farm.getFarmer().getId()+"_"+(f.getFarms()==null
			// || f.getFarms().size()==0 ? "1" :
			// String.valueOf(f.getFarms().stream().mapToInt(u ->
			// Integer.valueOf(u.getFarmId())).max().getAsInt()+1)));

			farm.setCreatedDate(new Date());
			farm.setCreatedUser(getUsername());
			farm.setStatus(1);

			if (latLonJsonString != null && !StringUtil.isEmpty(latLonJsonString) && latLonJsonString.length()>2) {
				CoordinatesMap cc = formCoord(latLonJsonString);
				farm.setPlotting(cc);
			}
			if (selectedVillage != null && !selectedVillage.isEmpty() && selectedVillage != "") {
				Village l = utilService.findVillageById(Long.valueOf(selectedVillage));
				farm.setVillage(l);
			}
			farm.setRevisionNo(DateUtil.getRevisionNumber());
			farm.setPlottingStatus("0");
			farmerService.save(farm);
		}
		return REDIRECT;
	}

	public String update() throws Exception {
		if (id != null && !id.equals("")) {
			farm = farmerService.findFarmById(Long.valueOf(id));
			if (farm == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			if (farm.getVillage() != null) {
				setSelectedVillage(farm.getVillage() != null ? String.valueOf(farm.getVillage().getId()) : "");
				setSelectedCity(farm.getVillage() != null ? String.valueOf(farm.getVillage().getCity().getId()) : "");
				setSelectedLocality(farm.getVillage() != null
						? String.valueOf(farm.getVillage().getCity().getLocality().getId()) : "");
				setSelectedState(farm.getVillage() != null
						? String.valueOf(farm.getVillage().getCity().getLocality().getState().getId()) : "");
				setSelectedCountry(farm.getVillage() != null
						? farm.getVillage().getCity().getLocality().getState().getCountry().getName() : "");
			}
			setFarmerUniqueId(String.valueOf(farm.getFarmer().getId()));
			photoFileName = farm.getPhoto();
			landRegDocsFileName = farm.getLandRegDocs();
			setCurrentPage(getCurrentPage());
			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getText(UPDATE));
		} else {
			if (farm != null) {
				Farm fm = farmerService.findFarmById(farm.getId());
				if (fm == null) {
					addActionError(NO_RECORD);
					return REDIRECT;
				}
				setCurrentPage(getCurrentPage());

				if (photoFile != null) {
					DocumentUpload du = new DocumentUpload();
					String[] tokens = photoFileName.split("\\.(?=[^\\.]+$)");
					String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
					du.setName(name);
					du.setContent(FileUtil.getBinaryFileContent(photoFile));
					du.setDocFileContentType(photoFileType);
					du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec()));
					du.setType(DocumentUpload.docType.IMPORT_APP.ordinal());
					du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
					utilService.save(du);
					fm.setPhoto(du.getRefCode().toString());
				}
				if (landRegDocsFile != null) {
					DocumentUpload du = new DocumentUpload();
					String[] tokens = landRegDocsFileName.split("\\.(?=[^\\.]+$)");
					String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
					du.setName(name);
					du.setContent(FileUtil.getBinaryFileContent(landRegDocsFile));
					du.setDocFileContentType(landRegDocsFileType);
					du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec())+"1");
					du.setType(DocumentUpload.docType.IMPORT_APP.ordinal());
					du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
					utilService.save(du);
					fm.setLandRegDocs(du.getRefCode().toString());
				}

				if (StringUtil.isEmpty(fm.getFarmCode()))
					fm.setFarmCode(fm.getFarmer().getVillage().getCity().getLocality().getState().getCode() + "_"
							+ fm.getFarmer().getVillage().getCity().getLocality().getCode() + "_"
							+ fm.getFarmer().getVillage().getCity().getCode() + "_" + fm.getFarmer().getFarmerId() + "_"
							+ StringUtil.appendZeroPrefix(String.valueOf(fm.getFarmId()), 3));

				fm.setFarmName(farm.getFarmName());
				fm.setTotalLandHolding(farm.getTotalLandHolding());
				fm.setProposedPlanting(farm.getProposedPlanting());
				fm.setLandOwnership(farm.getLandOwnership());
				fm.setIsAddressSame(farm.getIsAddressSame());
				fm.setAddress(farm.getAddress());
				fm.setLandRegNo(farm.getLandRegNo());
				fm.setLandTopography(farm.getLandTopography());
				fm.setLandGradient(farm.getLandGradient());
				fm.setSoilType(farm.getSoilType());
				fm.setIrrigationType(farm.getIrrigationType());

				fm.setNoFarmLabours(farm.getNoFarmLabours());
				fm.setRevisionNo(farm.getRevisionNo());
				if (farm.getFarmer() != null && farm.getFarmer().getId() > 0) {
					Farmer f = farmerService.findFarmerById(farm.getFarmer().getId());
					fm.setFarmer(f);
				}
				if (latLonJsonString != null && !StringUtil.isEmpty(latLonJsonString)  && latLonJsonString.length()>2) {
					CoordinatesMap cc = formCoord(latLonJsonString);
					fm.setPlotting(cc);
					fm.setLatitude(cc.getMidLatitude());
					fm.setLongitude(cc.getMidLongitude());
				}
				if (selectedVillage != null && !selectedVillage.isEmpty() && selectedVillage != "") {
					Village l = utilService.findVillageById(Long.valueOf(selectedVillage));
					fm.setVillage(l);
				}
				fm.setIsAddressSame(farm.getIsAddressSame());
				fm.setRevisionNo(DateUtil.getRevisionNumber());
				farmerService.editFarm(fm);
			}
			return REDIRECT;
		}
		return super.execute();
	}

	public String delete() throws Exception {
		String result = null;
		if (id != null && !id.equals("")) {
			farm = farmerService.findFarmById(Long.valueOf(id));
			setCurrentPage(getCurrentPage());
			if (farm == null) {
				addActionError(NO_RECORD);
				return result;
			} else {
				farm.setStatus(ParentEntity.Active.DELETED.ordinal());
				farm.setRevisionNo(DateUtil.getRevisionNumber());
				utilService.update(farm);
				result = REDIRECT;
			}
		}
		request.setAttribute(HEADING, getText(LIST));
		return result;
	}

	public void populateValidate() {
		Map<String, String> errorCodes = new LinkedHashMap<>();
		if (farm != null) {

			/*if (!StringUtil.isEmpty(farm.getFarmName())) {
				if (!ValidationUtil.isPatternMaches(farm.getFarmName(), ValidationUtil.ALPHANUMERIC_PATTERN)) {
					errorCodes.put("pattern.farmName", getLocaleProperty("pattern.farmName"));
				} else {
					Long id = (Long) farmerService.findObjectById("select id from Farm fc where fc.farmName=? and fc.farmer.id=? and fc.status <>2",
							new Object[] { farm.getFarmName() ,Long.valueOf(farmerUniqueId)});
					if (id != null && (farm.getId() == null || !farm.getId().equals(id))) {
						errorCodes.put("unique.farmname", getLocaleProperty("unique.farmname"));
					}
				}
			} else*/
			if (StringUtil.isEmpty(farm.getFarmName())){
				errorCodes.put("empty.farmName", getLocaleProperty("empty.farmName"));
			}

			if (StringUtil.isEmpty(farm.getProposedPlanting())) {

				errorCodes.put("empty.proposedPlanting", getLocaleProperty("empty.proposedPlanting"));
			}
			if (StringUtil.isEmpty(farm.getTotalLandHolding())) {

				errorCodes.put("empty.totalLandHolding", getLocaleProperty("empty.totalLandHolding"));
			}
			
			/*if (latLonJsonString == null || StringUtil.isEmpty(latLonJsonString) || latLonJsonString.length() == 2) {
				errorCodes.put("empty.plotting", getLocaleProperty("empty.plotting"));
			}*/

			// if (StringUtil.isEmpty(farm.getIsAddressSame()) ||
			// farm.getIsAddressSame().equals(null)) {
			// errorCodes.put("empty.isAddressSame",
			// getLocaleProperty("empty.isAddressSame"));
			// }

			/*if (StringUtil.isEmpty(farm.getAddress())) {

				errorCodes.put("empty.address", getLocaleProperty("empty.address"));
			}*/

			if (StringUtil.isEmpty(farm.getLandRegNo())) {

				errorCodes.put("empty.landRegNo", getLocaleProperty("empty.landRegNo"));
			}else{
				Long id = (Long) farmerService.findObjectById("select id from Farm fc where fc.landRegNo=? and fc.status <>2",
						new Object[] { farm.getLandRegNo() });
				if (id != null && (farm.getId() == null || !farm.getId().equals(id))) {
					errorCodes.put("unique.landRegNo", getLocaleProperty("unique.landRegNo"));
				}
			}

			if (landRegDocsFileName == null || ObjectUtil.isEmpty(landRegDocsFileName)) {
				errorCodes.put("empty.landRegDocs", getLocaleProperty("empty.landRegDocs"));
			}
			System.out.println(getSelectedCountry());
			if (getSelectedCountry() == null || StringUtil.isEmpty(getSelectedCountry())
					|| getSelectedCountry().equals("")) {

				errorCodes.put("empty.country", "empty.country");
			}

			if (getSelectedState() == null || StringUtil.isEmpty(getSelectedState()) || getSelectedState().equals("")) {

				errorCodes.put("empty.county", "empty.county");
			}

			if (getSelectedLocality() == null || StringUtil.isEmpty(getSelectedLocality())
					|| getSelectedLocality().equals("")) {

				errorCodes.put("empty.locality", "empty.locality");
			}

			if (getSelectedCity() == null || StringUtil.isEmpty(getSelectedCity()) || getSelectedCity().equals("")) {

				errorCodes.put("empty.ward", "empty.ward");
			}

			if (getSelectedVillage() == null || StringUtil.isEmpty(getSelectedVillage())
					|| getSelectedVillage().equals("")) {

				errorCodes.put("empty.ward1", "empty.ward1");
			}

		} else {
			errorCodes.put("empty.fields", getLocaleProperty("empty.fields"));
		}
		printErrorCodes(errorCodes);
	}

	/**
	 * @see com.sourcetrace.esesw.view.SwitchAction#data()
	 */

	public String data() throws Exception {

		Map<String, String> searchRecord = getDataTableJQGridRequestParam(); // get
																				// the
		// //
		// search // parameter // with // value

		Farm filter = new Farm();

		if (!StringUtil.isEmpty(searchRecord.get("farmCode")))
			filter.setFarmCode(searchRecord.get("farmCode").trim());

		if (!StringUtil.isEmpty(searchRecord.get("farmName")))
			filter.setFarmName(searchRecord.get("farmName").trim());

		/*
		 * if (!StringUtil.isEmpty(searchRecord.get("fdi.regYear"))){
		 * FarmDetailedInfo farmDetailedInfo = new FarmDetailedInfo();
		 * farmDetailedInfo.setRegYear(searchRecord.get("fdi.regYear").trim());
		 * filter.setFarmDetailedInfo( farmDetailedInfo); }
		 */

		if (!StringUtil.isEmpty(searchRecord.get("f.firstName"))) {
			Farmer farmer = new Farmer();
			farmer.setFirstName(searchRecord.get("f.firstName").trim());
			filter.setFarmer(farmer);
		}

		if (!StringUtil.isEmpty(this.farmerId)) {
			if (!ObjectUtil.isEmpty(filter.getFarmer())) {
				filter.getFarmer().setId(Long.parseLong(this.farmerId));
			} else {
				Farmer farmer = new Farmer();
				farmer.setId(Long.parseLong(this.farmerId));
				farmer.setFirstName(null);
				filter.setFarmer(farmer);
			}

		}

		Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
				getPage());

		return sendDataTableJSONResponse(data);
	}

	/**
	 * @see com.sourcetrace.esesw.view.SwitchAction#toJSON(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(Object obj) {

		Farm farm = (Farm) obj;
		JSONObject jsonObject = new JSONObject();
		JSONObject objDt = new JSONObject();
		JSONObject actionOnj = new JSONObject();
		String detUrl = "farm_detail.action?id=" + farm.getId() + "&farmerId=" + farm.getFarmer().getFarmerId()
				+ "&farmerUniqueId=" + farm.getFarmer().getId();

		String code = "<a href=" + detUrl + "&redirectContent=" + getRedirectContent() + "&layoutType="
				+ request.getParameter("layoutType") + "&url=" + request.getParameter("url")
				+ " title='Go To Detail' target=_blank>"
				+ (!StringUtil.isEmpty(farm.getFarmCode()) ? farm.getFarmCode() : "NA") + "</a>";

		objDt.put("code", code);
		objDt.put("name", farm.getFarmName());
		objDt.put("area", farm.getTotalLandHolding());
		objDt.put("village", farm.getProposedPlanting()!=null ?  farm.getProposedPlanting() : "");
		objDt.put("city", farm.getVillage()!=null ? farm.getVillage().getName() : "");
		actionOnj.put("id", farm.getId());

		objDt.put("edit",
				"<a href='#' onclick='ediFunction(\"" + StringEscapeUtils.escapeJavaScript(actionOnj.toString())
						+ "\")' class='fa fa-edit' title='Edit' ></a>");
		objDt.put("del",
				"<a href='#' onclick='deleteFunction(\"" + StringEscapeUtils.escapeJavaScript(actionOnj.toString())
						+ "\")' class='fa fa-trash-o' title='del' ></a>");
		jsonObject.put("DT_RowId", farm.getId());
		jsonObject.put("cell", objDt);
		return jsonObject;
	}

	

	public void populateState() throws Exception {

		JSONArray stateArr = new JSONArray();
		if (!selectedCountry.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedCountry))) {
			List<State> states = utilService.listStates(selectedCountry);
			if (!ObjectUtil.isEmpty(states)) {
				for (State state : states) {

					String name = getLanguagePref(getLoggedInUserLanguage(), state.getCode().trim().toString());
					if (!StringUtil.isEmpty(name) && name != null) {
						stateArr.add(getJSONObject(String.valueOf(state.getId()), state.getCode().toString() + "-"
								+ getLanguagePref(getLoggedInUserLanguage(), state.getCode().toString())));
					} else {
						stateArr.add(
								getJSONObject(String.valueOf(state.getId()), state.getCode() + "-" + state.getName()));
					}
				}
			}
		}

		sendAjaxResponse(stateArr);
	}

	public void populateLocality() throws Exception {
		JSONArray localtiesArr = new JSONArray();
		if (!selectedState.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedState))) {
			List<Locality> listLocalities = utilService.findLocalityByStateId(Long.valueOf(selectedState));
			if (!ObjectUtil.isEmpty(listLocalities)) {
				for (Locality locality : listLocalities) {

					String name = getLanguagePref(getLoggedInUserLanguage(), locality.getCode().trim().toString());
					if (!StringUtil.isEmpty(name) && name != null) {
						localtiesArr.add(getJSONObject(String.valueOf(locality.getId()),
								locality.getCode().trim().toString() + "-" + getLanguagePref(getLoggedInUserLanguage(),
										locality.getCode().trim().toString())));
					} else {
						localtiesArr.add(getJSONObject(String.valueOf(locality.getId()),
								locality.getCode() + "-" + locality.getName()));
					}
				}
			}
		}

		sendAjaxResponse(localtiesArr);

	}

	public void populateMunicipality() throws Exception {
		JSONArray citiesArr = new JSONArray();
		if (!selectedLocality.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedLocality))) {
			List<Municipality> listCity = utilService.findMuniciByDistrictId(Long.valueOf(selectedLocality));
			if (!ObjectUtil.isEmpty(listCity)) {
				for (Municipality city : listCity) {

					String name = getLanguagePref(getLoggedInUserLanguage(), city.getCode().trim().toString());
					if (!StringUtil.isEmpty(name) && name != null) {
						citiesArr.add(getJSONObject(String.valueOf(city.getId()), city.getCode().trim().toString() + "-"
								+ getLanguagePref(getLoggedInUserLanguage(), city.getCode().trim().toString())));
					} else {
						citiesArr.add(
								getJSONObject(String.valueOf(city.getId()), city.getCode() + "-" + city.getName()));
					}
				}
			}
		}

		sendAjaxResponse(citiesArr);

	}

	public void populateVillage() throws Exception {

		JSONArray villageArr = new JSONArray();
		if (!selectedCity.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedCity))) {
			List<Village> listVillages = utilService.findVillageByGramId(Long.valueOf(selectedCity));
			if (!ObjectUtil.isEmpty(listVillages)) {
				for (Village village : listVillages) {

					String name = getLanguagePref(getLoggedInUserLanguage(), village.getCode().trim().toString());
					if (!StringUtil.isEmpty(name) && name != null) {
						villageArr.add(getJSONObject(String.valueOf(village.getId()),
								village.getCode().trim().toString() + "-" + getLanguagePref(getLoggedInUserLanguage(),
										village.getCode().trim().toString())));
					} else {
						villageArr.add(getJSONObject(String.valueOf(village.getId()),
								village.getCode() + "-" + village.getName()));
					}
				}
			}
		}

		sendAjaxResponse(villageArr);

	}
	

	public Map<String, String> getCountries() {

		Map<String, String> countryMap = new LinkedHashMap<String, String>();
		List<Country> countryList = utilService.listCountries();
		for (Country obj : countryList) {
			countryMap.put(obj.getName(), obj.getCode() + "-" + obj.getName());
		}
		return countryMap;
	}// thisis loading country

}
