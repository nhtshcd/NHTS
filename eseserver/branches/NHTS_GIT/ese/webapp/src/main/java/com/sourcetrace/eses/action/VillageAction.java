package com.sourcetrace.eses.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.Country;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.GramPanchayat;
import com.sourcetrace.eses.entity.Locality;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.entity.Village;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

public class VillageAction extends SwitchValidatorAction {



	private static final long serialVersionUID = -3703638399195601859L;

	private static final Logger LOGGER = Logger.getLogger(VillageAction.class);

	
	@Autowired
	private IUtilService utilService;

	private Village village;
	private String selectedCountry;
	private String selectedState;
	private String selectedDistrict;
	private String selectedCity;
	private String selectedGramPanchayat;
	private String id;
	private String gramPanchayatEnable;
	private String locality;
	List<Locality> localities = new ArrayList<Locality>();
	List<State> states = new ArrayList<State>();
	List<Municipality> cities = new ArrayList<Municipality>();
	List<GramPanchayat> gramPanchayats = new ArrayList<GramPanchayat>();
	private String name;
	private String code;

	private IUniqueIDGenerator idGenerator;
	

	/**
	 * Data.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 * @see com.sourcetrace.esesw.view.SwitchAction#data()
	 */
	public String data() throws Exception {

		Map<String, String> searchRecord = getDataTableJQGridRequestParam(); // get the
																	// search
																	// parameter
																	// with
		// value

		Village filter = new Village();

		if (!StringUtil.isEmpty(searchRecord.get("branchId"))) {
			if (!getIsMultiBranch().equalsIgnoreCase("1")) {
				List<String> branchList = new ArrayList<>();
				branchList.add(searchRecord.get("branchId").trim());
				filter.setBranchesList(branchList);
			} else {
				List<String> branchList = new ArrayList<>();
				List<BranchMaster> branches = utilService.listChildBranchIds(searchRecord.get("branchId").trim());
				branchList.add(searchRecord.get("branchId").trim());
				branches.stream().filter(branch -> !StringUtil.isEmpty(branch)).forEach(branch -> {
					branchList.add(branch.getBranchId());
				});
				filter.setBranchesList(branchList);
			}
		}

		if (!StringUtil.isEmpty(searchRecord.get("subBranchId"))) {
			filter.setBranchId(searchRecord.get("subBranchId").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("code"))) {
			filter.setCode(searchRecord.get("code").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("name"))) {
			filter.setName(searchRecord.get("name").trim());
		}

		/*if (!StringUtil.isEmpty(searchRecord.get("selectedGramPanchayat"))) {
			GramPanchayat gramPanchayat = new GramPanchayat();
			gramPanchayat.setName(searchRecord.get("selectedGramPanchayat").trim());
			filter.setGramPanchayat(gramPanchayat);
		}*/

		if (!StringUtil.isEmpty(searchRecord.get("selectedCity"))) {
			Municipality city = new Municipality();
			city.setName(searchRecord.get("selectedCity").trim());
			filter.setCity(city);
		}

		if (!StringUtil.isEmpty(searchRecord.get("selectedDistrict"))) {
			Municipality city = new Municipality();
			Locality locality = new Locality();
			locality.setName(searchRecord.get("selectedDistrict").trim());
			city.setLocality(locality);
			filter.setCity(city);
		}

		if (!StringUtil.isEmpty(searchRecord.get("selectedState"))) {
			Municipality city = new Municipality();
			Locality locality = new Locality();
			State state = new State();
			state.setName(searchRecord.get("selectedState").trim());
			locality.setState(state);
			city.setLocality(locality);
			filter.setCity(city);
		}
		
		if (!StringUtil.isEmpty(searchRecord.get("selectedCountry"))) {
			Municipality city = new Municipality();
			Locality locality = new Locality();
			State state = new State();
			Country country = new Country();
			country.setName(searchRecord.get("selectedCountry").trim());
			state.setCountry(country);
			locality.setState(state);
			city.setLocality(locality);
			filter.setCity(city);
		}

		Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
				getPage());

		return sendDataTableJSONResponse(data);
	}

	/**
	 * To json.
	 * 
	 * @param obj
	 *            the obj
	 * @return the JSON object
	 * @see com.sourcetrace.esesw.view.SwitchAction#toJSON(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(Object obj) {
		ESESystem preferences = utilService.findPrefernceById("1");
		if (!StringUtil.isEmpty(preferences)) {
			setGramPanchayatEnable(preferences.getPreferences().get(ESESystem.ENABLE_GRAMPANJAYAT));
		}
		Village village = (Village) obj;
		JSONObject jsonObject = new JSONObject();
		//JSONArray rows = new JSONArray();
		JSONObject objDt = new JSONObject();
		JSONObject actionOnj = new JSONObject();
		if ((getIsMultiBranch().equalsIgnoreCase("1")
				&& (getIsParentBranch().equals("1") || StringUtil.isEmpty(branchIdValue)))) {

			if (StringUtil.isEmpty(branchIdValue)) {
				objDt.put("branch",
						!StringUtil.isEmpty(getBranchesMap().get(getParentBranchMap().get(village.getBranchId())))
								? getBranchesMap().get(getParentBranchMap().get(village.getBranchId()))
								: getBranchesMap().get(village.getBranchId()));

			}
			objDt.put("subbranch", getBranchesMap().get(village.getBranchId()));

		} else {
			if (StringUtil.isEmpty(branchIdValue)) {
				objDt.put("branch", getBranchesMap().get(village.getBranchId()));
			}
		}
		
		objDt.put("code",village.getCode());
			
		String name = getLanguagePref(getLoggedInUserLanguage(), village.getCode().trim().toString());
		if(!StringUtil.isEmpty(name) && name != null){
			objDt.put("villagename1",name);
		}else{
			objDt.put("villagename1",village.getName());
		}
		

		/*if (getGramPanchayatEnable().equalsIgnoreCase("1")) {
			String gramName = getLanguagePref(getLoggedInUserLanguage(), village.getGramPanchayat().getCode().trim().toString());
			if(!StringUtil.isEmpty(gramName) && gramName != null){
				objDt.put("gramname",gramName);
			}else{
				objDt.put("gramname",village.getGramPanchayat() == null ? "  " :  village.getGramPanchayat().getName());
			}
		}*/
		
		
		String cityName = getLanguagePref(getLoggedInUserLanguage(), village.getCity().getCode().trim().toString());
		if(!StringUtil.isEmpty(cityName) && cityName != null){
			objDt.put("cityname1",cityName);
		}else{
			objDt.put("cityname1",village.getCity() == null ? "  " : village.getCity().getName());
		}
		
		
		String localityName = getLanguagePref(getLoggedInUserLanguage(), village.getCity().getLocality().getCode().trim().toString());
		if(!StringUtil.isEmpty(localityName) && localityName != null){
			objDt.put("localityname1",localityName);
		}else{
			objDt.put("localityname1",village.getCity().getLocality() == null ? " " : village.getCity().getLocality().getName());
		}
		
		String stateName = getLanguagePref(getLoggedInUserLanguage(), village.getCity().getLocality().getState().getCode().trim().toString());
		if(!StringUtil.isEmpty(stateName) && stateName != null){
			objDt.put("statename1",stateName);
		}else{
			objDt.put("statename1",village.getCity().getLocality().getState() == null ? " "
					: village.getCity().getLocality().getState().getName());
		}
		
		
		String countryName = getLanguagePref(getLoggedInUserLanguage(), village.getCity().getLocality().getState().getCountry().getCode().trim().toString());
		if(!StringUtil.isEmpty(countryName) && countryName != null){
			objDt.put("countryname1",countryName);
		}else{
			objDt.put("countryname1",village.getCity().getLocality().getState().getCountry() == null ? " "
					: village.getCity().getLocality().getState().getCountry().getName());
		}
		actionOnj.put("code", village.getCode());
		actionOnj.put("name", village.getName());
		actionOnj.put("city", village.getCity().getCode());
		actionOnj.put("id", village.getId());
		actionOnj.put("locality", village.getCity().getLocality().getCode());
		actionOnj.put("state", village.getCity().getLocality().getState().getCode());
		actionOnj.put("country", village.getCity().getLocality().getState().getCountry().getCode());
//		objDt.put("edit","<a href='#' onclick='ediFunction(\"" + StringEscapeUtils.escapeJavaScript(actionOnj.toString())
//		+ "\")' class='fa fa-edit' title='Edit' ></a>");
		objDt.put("edit","<a href='#' onclick='ediFunction(\"" + StringEscapeUtils.escapeJavaScript(actionOnj.toString())
		+ "\")' class='fa fa-edit' title='Edit' ></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='deletProd(\""
						+ village.getId() + "\")' class='fa fa-trash' title='Delete' ></a>");
		jsonObject.put("DT_RowId", village.getId());
		jsonObject.put("cell", objDt);
		return jsonObject;
	}

	/**
	 * Creates the.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String create() throws Exception {
		String codeGenType = null;
		ESESystem preferences = utilService.findPrefernceById("1");
		if (!StringUtil.isEmpty(preferences)) {
			setGramPanchayatEnable(preferences.getPreferences().get(ESESystem.ENABLE_GRAMPANJAYAT));
			codeGenType = preferences.getPreferences().get(ESESystem.CODE_TYPE);

		}
		if (village == null) {

			command = CREATE;
			request.setAttribute(HEADING, getText(CREATE));
			return LIST;
		} else {

			Municipality city = utilService.findMunicipalityByCode(village.getCity().getCode());
			village.setCity(city);
			village.setBranchId(getBranchId());
			if (gramPanchayatEnable != null) {
				if (gramPanchayatEnable.equalsIgnoreCase("1")) {
					GramPanchayat gramPanchayat =utilService
							.findGrampanchaythByCode(village.getGramPanchayat().getCode());
					village.setGramPanchayat(gramPanchayat);

					if (codeGenType.equals("1") ) {
						String villageCode = idGenerator.getVillageHHIdSeq(gramPanchayat.getCode());
						BigInteger codeSeq = new BigInteger(villageCode);
						String maxCode = codeSeq.subtract(new BigInteger("1")).toString();
						if (Integer.valueOf(maxCode.substring(4, 6)) >= 99) {
							addActionError(getText("error.villageExceed"));
							return INPUT;
						} else {
							village.setCode(idGenerator.getVillageHHIdSeq(gramPanchayat.getCode()));
						}
					} else {
						village.setCode(idGenerator.getVillageIdSeq());
					}

				} else {
					village.setCode(idGenerator.getVillageIdSeq());
				}

			} else {
				village.setGramPanchayat(null);
			}
			utilService.addVillage(village);
			return REDIRECT;
		}
	}

/*	@SuppressWarnings("unchecked")
	public String populateSaveVillage() {
		village = new Village();
		String codeGenType = null;
		ESESystem preferences = preferncesService.findPrefernceById("1");
		if (!StringUtil.isEmpty(preferences)) {
			setGramPanchayatEnable(preferences.getPreferences().get(ESESystem.ENABLE_GRAMPANJAYAT));
			codeGenType = preferences.getPreferences().get(ESESystem.CODE_TYPE);
		}

		if (!StringUtil.isEmpty(selectedCity)) {
			village.setName(getName());
			Village tempVillage=locationService.findVillageByName(getName());
			if(ObjectUtil.isEmpty(tempVillage) || !ObjectUtil.isEmpty(tempVillage) && !ObjectUtil.isEmpty(tempVillage.getCity()) )
			{
				
		
			Municipality city = locationService.findMunicipalityByCode(selectedCity);
			village.setCity(city);
			village.setBranchId(getBranchId());
			if (gramPanchayatEnable != null) {
				if (gramPanchayatEnable.equalsIgnoreCase("1")) {
					GramPanchayat gramPanchayat = locationService.findGrampanchaythByCode(getSelectedGramPanchayat());
					village.setGramPanchayat(gramPanchayat);

					village.setCode(idGenerator.getVillageIdSeq());

				} else {
					village.setCode(idGenerator.getVillageIdSeq());
				}

			}
			locationService.addVillage(village);
			getJsonObject().put("msg", getText("msg.added"));
			getJsonObject().put("title", getText("title.success"));
			
			}
			else
			{
				
				getJsonObject().put("status", "0");
				getJsonObject().put("msg", getLocaleProperty("msg.stateDuplicateError"));  
			     getJsonObject().put("title", getText("title.error"));
			}
		} else {
			addActionError(getText("empty.city"));

		}

		
		sendAjaxResponse(getJsonObject());
		return null;
	}*/
	
	@SuppressWarnings("unchecked")
	public String populateSaveVillage() {
		getJsonObject().clear();
		village = new Village();
		String codeGenType = null;
		ESESystem preferences = utilService.findPrefernceById("1");
		if (!StringUtil.isEmpty(preferences)) {
			setGramPanchayatEnable(preferences.getPreferences().get(ESESystem.ENABLE_GRAMPANJAYAT));
			codeGenType = preferences.getPreferences().get(ESESystem.CODE_TYPE);
		}

		if (!StringUtil.isEmpty(selectedCity)) {
			village.setName(getName());
			Municipality city = utilService.findMunicipalityByCode(selectedCity);
			village.setCity(city);
			village.setBranchId(getBranchId());
			if (gramPanchayatEnable != null) {
				if (gramPanchayatEnable.equalsIgnoreCase("1")) {
					GramPanchayat gramPanchayat = utilService.findGrampanchaythByCode(getSelectedGramPanchayat());
					village.setGramPanchayat(gramPanchayat);

					//village.setCode(idGenerator.getVillageIdSeq());
					village.setCode(getCode());

				} else {
					if(getCurrentTenantId().equalsIgnoreCase("symrise") || getCurrentTenantId().equalsIgnoreCase(ESESystem.AVT) ){
						village.setCode(getCode());
						if(getCurrentTenantId().equalsIgnoreCase(ESESystem.AVT)){
							village.setSeq("1");
						}
						else{
						village.setSeq("100");
						}
					}else{
					//village.setCode(idGenerator.getVillageIdSeq());
				     village.setCode(getCode());
					if(getCurrentTenantId().equalsIgnoreCase("griffith")){
						village.setSeq("1");
					}
					}
				}

			}
			
		}
		Village villageCode=utilService.findVillageByCode(getCode());
		Village vname = utilService.findVillageByName(getName());
		if (vname != null) {
			getJsonObject().put("status", "0");
			getJsonObject().put("msg", getLocaleProperty("msg.villageDuplicateError"));  
		    getJsonObject().put("title", getText("title.error"));
		}
		if (villageCode != null) {
			if (getJsonObject().containsKey("msg")) {
				
				getJsonObject().put("msg", getJsonObject().get("msg") + "<br>" + getText("cropcatCode.exist"));
				getJsonObject().put("title", getText("title.error"));
			} else {
				getJsonObject().put("msg", getText("cropcatCode.exist"));
				getJsonObject().put("title", getText("title.error"));
			}
		}
		if (!getJsonObject().containsKey("msg")) {
			utilService.addVillage(village);
			getJsonObject().put("status", "1"); 
			getJsonObject().put("msg", getText("msg.added"));  
		    getJsonObject().put("title", getText("title.success"));
		}
		sendAjaxResponse(getJsonObject());
		return null;
	}

	/**
	 * Update.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public String update() throws Exception {

		if (id != null && !id.equals("")) {
			village = utilService.findVillageById(Long.parseLong(id));
			if (village == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}

			ESESystem preferences = utilService.findPrefernceById("1");
			if (!StringUtil.isEmpty(preferences)) {
				setGramPanchayatEnable(preferences.getPreferences().get(ESESystem.ENABLE_GRAMPANJAYAT));
			}

			/*
			 * setSelectedCountry(village.getCity().getLocality().getState().
			 * getCountry().getName());
			 * setSelectedState(village.getCity().getLocality().getState().
			 * getName());
			 * setSelectedDistrict(village.getCity().getLocality().getName());
			 * setSelectedCity(village.getCity().getName());
			 */
			if (getGramPanchayatEnable().equalsIgnoreCase("1") && !StringUtil.isEmpty(village.getGramPanchayat())) {
				setSelectedCountry(
						village.getGramPanchayat().getCity().getLocality().getState().getCountry().getName());
				setSelectedState(village.getGramPanchayat().getCity().getLocality().getState().getCode());
				setSelectedDistrict(village.getGramPanchayat().getCity().getLocality().getCode());
				setSelectedCity(village.getGramPanchayat().getCity().getCode());
				setSelectedGramPanchayat(village.getGramPanchayat().getCode());
			} else {
				setSelectedCountry(village.getCity().getLocality().getState().getCountry().getName());
				setSelectedState(village.getCity().getLocality().getState().getCode());
				setSelectedDistrict(village.getCity().getLocality().getCode());
				setSelectedCity(village.getCity().getCode());
			}

			setCurrentPage(getCurrentPage());
			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getText(UPDATE));
		} else {
			ESESystem preferences = utilService.findPrefernceById("1");
			String codeGenType = preferences.getPreferences().get(ESESystem.CODE_TYPE);
			if (village != null) {
				Village existing = utilService.findVillageById(village.getId());

				if (existing == null) {
					addActionError(NO_RECORD);
					return REDIRECT;
				}
				setCurrentPage(getCurrentPage());
				existing.setName(village.getName());

				Municipality city = utilService.findMunicipalityByCode(village.getCity().getCode());
				existing.setCity(city);
				if (gramPanchayatEnable != null) {
					if (gramPanchayatEnable.equalsIgnoreCase("1")) {

						if (codeGenType.equals("1")) {
							if (!village.getGramPanchayat().getName()
									.equalsIgnoreCase(existing.getGramPanchayat().getName())) {
								String villageCodeSeq = idGenerator
										.getVillageHHIdSeq(existing.getGramPanchayat().getCode());
								existing.setCode(villageCodeSeq);
							}
						}

						GramPanchayat gramPanchayat = utilService
								.findGrampanchaythByCode(village.getGramPanchayat().getCode());
						existing.setGramPanchayat(gramPanchayat);

					}
				}
				utilService.editVillage(existing);

				getJsonObject().put("msg", getText("msg.updated"));
				getJsonObject().put("title", getText("title.success"));

				sendAjaxResponse(getJsonObject());
			}
			command = UPDATE;
			request.setAttribute(HEADING, getText(LIST));
			return LIST;
		}
		return super.execute();
	}

	/**
	 * Delete.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public String delete() throws Exception {

		if (id != null && !id.equals("")) {
			village = utilService.findVillageById(Long.parseLong(id));
			if (ObjectUtil.isEmpty(village)) {
				addActionError(NO_RECORD);
				
			}

			boolean isCooperativeMappingExist = utilService.isVillageMappedWithCooperative(village.getId());

			if (isCooperativeMappingExist) {
				// addActionError(getLocaleProperty("cooperative.mapped"));
				getJsonObject().put("msg", getLocaleProperty("cooperative.mapped"));
				getJsonObject().put("title", getText("title.success"));
				sendAjaxResponse(getJsonObject());
				return null;
			}

			ESESystem preferences = utilService.findPrefernceById("1");
			if (!StringUtil.isEmpty(preferences)) {
				setGramPanchayatEnable(preferences.getPreferences().get(ESESystem.ENABLE_GRAMPANJAYAT));
			}

			if (getGramPanchayatEnable().equalsIgnoreCase("1") && !StringUtil.isEmpty(village.getGramPanchayat())) {

				if (village.getGramPanchayat().getVillages().contains(village)) {
					village.getGramPanchayat().getVillages().remove(village);
				}
				utilService.editGramPanchayat(village.getGramPanchayat());

				if (village.getCity() != null && !ObjectUtil.isEmpty(village.getCity())) {
					if (village.getCity().getVillages().contains(village)) {
						village.getCity().getVillages().remove(village);
					}
				}
				utilService.editMunicipality(village.getCity());

			} else {
				if (village.getCity() != null && !ObjectUtil.isEmpty(village.getCity())) {
					if (village.getCity().getVillages().contains(village)) {
						village.getCity().getVillages().remove(village);
					}
				}
				utilService.editMunicipality(village.getCity());
			}

			utilService.removeVillage(village);
			if (getJsonObject() != null && !StringUtil.isEmpty(getJsonObject().get("msg"))) {
				getJsonObject().put("msg", getText("msg.deleted"));
				getJsonObject().put("title", getText("title.success"));
			}

			sendAjaxResponse(getJsonObject());
		}
		return null;
	}

	/**
	 * Detail.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String detail() throws Exception {

		String view = "";
		if (id != null && !id.equals("")) {
			village = utilService.findVillageById(Long.parseLong(id));
			ESESystem preferences = utilService.findPrefernceById("1");
			if (!StringUtil.isEmpty(preferences)) {
				setGramPanchayatEnable(preferences.getPreferences().get(ESESystem.ENABLE_GRAMPANJAYAT));
			}
			if (village == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			setCurrentPage(getCurrentPage());
			command = UPDATE;
			view = DETAIL;
			request.setAttribute(HEADING, getText(DETAIL));
		} else {
			request.setAttribute(HEADING, getText(LIST));
			return LIST;
		}
		return view;
	}

	/**
	 * Gets the data.
	 * 
	 * @return the data
	 * @see com.sourcetrace.esesw.view.SwitchValidatorAction#getData()
	 */
	@Override
	public Object getData() {

		ESESystem preferences = utilService.findPrefernceById("1");
		if (!StringUtil.isEmpty(preferences)) {
			setGramPanchayatEnable(preferences.getPreferences().get(ESESystem.ENABLE_GRAMPANJAYAT));
		}

		if (ObjectUtil.isEmpty(village)) {
			return null;
		} else {
			Country country = new Country();
			country.setName(this.selectedCountry);

			State state = new State();
			state.setCode(this.selectedState);
			state.setCountry(country);

			Locality locality = new Locality();
			locality.setCode(this.selectedDistrict);
			locality.setState(state);

			Municipality city = new Municipality();
			city.setCode(this.selectedCity);
			city.setLocality(locality);

			GramPanchayat gramPanchayat = new GramPanchayat();
			if (gramPanchayatEnable != null) {
				if (getGramPanchayatEnable().equalsIgnoreCase("1")) {
					gramPanchayat.setCode(village.getGramPanchayat().getCode());
					gramPanchayat.setCity(city);
					village.setGramPanchayat(gramPanchayat);
				}

			}
			// city.setName(village.getCity().getName());
			// city.setLocality(locality);
			village.setCity(city);
			return village;
		}

	}

	@SuppressWarnings("unchecked")
	public void populateVillageUpdate() {
		if (!StringUtil.isEmpty(getId())) {
			Village village = utilService.findVillageById(Long.valueOf(getId()));
			
				/*if(code!=null && !StringUtil.isEmpty(code)){
					Village vil=locationService.findVillageByCode(code);
					if(vil!=null && !ObjectUtil.isEmpty(vil)){
						getJsonObject().put("msg", getText("msg.codeDuplicate"));  
					     getJsonObject().put("title", getText("title.error"));
					     sendAjaxResponse(getJsonObject());
					}
					else{*/
						/*if (!ObjectUtil.isEmpty(village)) {
							village.setCode(code);
							village.setName(getName());
							Municipality city = utilService.findMunicipalityByCode(getSelectedCity());
							GramPanchayat gp = utilService.findGrampanchaythByCode(getSelectedGramPanchayat());
							village.setCity(city);
							village.setGramPanchayat(gp);
							village.setRevisionNo(DateUtil.getRevisionNumber());
							utilService.editVillage(village);

							getJsonObject().put("msg", getText("msg.updated"));
							getJsonObject().put("title", getText("title.success"));
							sendAjaxResponse(getJsonObject());
						/*}
					}*/
				
			
			
			if (!StringUtil.isEmpty(village)) {
				village.setName(getName());
				Municipality city = utilService.findMunicipalityByCode(getSelectedCity());
				GramPanchayat gp = utilService.findGrampanchaythByCode(getSelectedGramPanchayat());
				village.setCity(city);
				village.setGramPanchayat(gp);
				village.setRevisionNo(DateUtil.getRevisionNumber());
				utilService.editVillage(village);

				getJsonObject().put("msg", getText("msg.updated"));
				getJsonObject().put("title", getText("title.success"));
				sendAjaxResponse(getJsonObject());
			}
			}
		
	}

	/**
	 * Gets the countries.
	 * 
	 * @return the countries
	 */
	/*
	 * public List<Country> getCountries() {
	 * 
	 * List<Country> returnValue = locationService.listCountries(); return
	 * returnValue; }
	 */
	public Map<String, String> getCountriesList() {
		Map<String, String> countryMap = new LinkedHashMap<String, String>();
		List<Country> returnValue = utilService.listCountries();
		for (Country c : returnValue) {
			countryMap.put(c.getCode(), c.getName());
		}
		return countryMap;
	}

	/**
	 * Gets the location service.
	 * 
	 * @return the location service
	 */
	
	/**
	 * Sets the location service.
	 * 
	 * @param locationService
	 *            the new location service
	 */
	

	/**
	 * Gets the village.
	 * 
	 * @return the village
	 */
	public Village getVillage() {

		return village;
	}

	/**
	 * Sets the village.
	 * 
	 * @param village
	 *            the new village
	 */
	public void setVillage(Village village) {

		this.village = village;
	}

	/**
	 * Gets the selected country.
	 * 
	 * @return the selected country
	 */
	public String getSelectedCountry() {

		return selectedCountry;
	}

	/**
	 * Sets the selected country.
	 * 
	 * @param selectedCountry
	 *            the new selected country
	 */
	public void setSelectedCountry(String selectedCountry) {

		this.selectedCountry = selectedCountry;
	}

	/**
	 * Gets the selected state.
	 * 
	 * @return the selected state
	 */
	public String getSelectedState() {

		return selectedState;
	}

	/**
	 * Sets the selected state.
	 * 
	 * @param selectedState
	 *            the new selected state
	 */
	public void setSelectedState(String selectedState) {

		this.selectedState = selectedState;
	}

	/**
	 * Gets the selected district.
	 * 
	 * @return the selected district
	 */
	public String getSelectedDistrict() {

		return selectedDistrict;
	}

	/**
	 * Sets the selected district.
	 * 
	 * @param selectedDistrict
	 *            the new selected district
	 */
	public void setSelectedDistrict(String selectedDistrict) {

		this.selectedDistrict = selectedDistrict;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {

		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {

		this.id = id;
	}

	/**
	 * Gets the localities.
	 * 
	 * @return the localities
	 */
	/*
	 * public List<Locality> getLocalities() {
	 * 
	 * if (!StringUtil.isEmpty(selectedState)) { localities =
	 * locationService.listLocalities(selectedState); } return localities; }
	 */

	public Map<String, String> getLocalitiesId() {
		Map<String, String> locality = new LinkedHashMap<String, String>();
		if (!StringUtil.isEmpty(selectedState)) {
			localities = utilService.listLocalities(selectedState);
			for (Locality loc : localities) {
				locality.put(loc.getCode(), loc.getCode() + " - " + loc.getName());
			}
		}
		return locality;
	}

	/**
	 * Sets the localities.
	 * 
	 * @param localities
	 *            the new localities
	 */
	public void setLocalities(List<Locality> localities) {

		this.localities = localities;
	}

	/**
	 * Gets the states.
	 * 
	 * @return the states
	 */
	/*
	 * public List<State> getStates() {
	 * 
	 * if (!StringUtil.isEmpty(selectedCountry)) { states =
	 * locationService.listStates(selectedCountry); }
	 * 
	 * return states; }
	 */

	public Map<String, String> getStatesId() {
		Map<String, String> state = new LinkedHashMap<String, String>();
		if (!StringUtil.isEmpty(selectedCountry)) {
			states = utilService.listStates(selectedCountry);
			for (State s : states) {
				state.put(s.getCode(), s.getCode() + " - " + s.getName());
			}
		}
		return state;
	}

	/**
	 * Sets the states.
	 * 
	 * @param states
	 *            the new states
	 */
	public void setStates(List<State> states) {

		this.states = states;
	}

	/**
	 * Gets the cities.
	 * 
	 * @return the cities
	 */
	/*
	 * public List<Municipality> getCities() {
	 * 
	 * if (!StringUtil.isEmpty(selectedDistrict)) { cities =
	 * locationService.listMunicipalitiesByCode(selectedDistrict); } return
	 * cities; }
	 */

	public Map<String, String> getCities() {
		Map<String, String> city = new LinkedHashMap<String, String>();
		if (!StringUtil.isEmpty(selectedDistrict)) {
			cities = utilService.listMunicipalitiesByCode(selectedDistrict);
			for (Municipality muncipality : cities) {
				city.put(muncipality.getCode(), muncipality.getCode() + " - " + muncipality.getName());
			}
		}
		return city;
	}

	/**
	 * Sets the cities.
	 * 
	 * @param cities
	 *            the new cities
	 */
	public void setCities(List<Municipality> cities) {

		this.cities = cities;
	}

	@SuppressWarnings("unchecked")
	protected JSONObject getJSONObject(Object id, Object name) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("name", name);
		return jsonObject;
	}

	@SuppressWarnings("unchecked")
	public void populateCountryList() {
		JSONObject jsonObject = new JSONObject();

		utilService.listCountries().stream().forEach(country -> {
			jsonObject.put(country.getCode(), country.getName());
		});

		try {
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.print(jsonObject.toString().replace("\"", "").replace(",", ";"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void populateStateList() {
		JSONObject jsonObject = new JSONObject();

		utilService.listOfStates().stream().forEach(state -> {
			jsonObject.put(state.getCode(), state.getName());
		});

		try {
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.print(jsonObject.toString().replace("\"", "").replace(",", ";"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void populateLocalityList() {
		JSONObject jsonObject = new JSONObject();
		utilService.listLocalityIdCodeAndName().stream().forEach(cities -> {
			Object[] cityArr = (Object[]) cities;
			jsonObject.put(cityArr[1], cityArr[2]);
		});

		try {
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.print(jsonObject.toString().replace("\"", "").replace(",", ";"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void populateCityList() {
		JSONObject jsonObject = new JSONObject();
		utilService.listCityCodeAndName().stream().forEach(cities -> {
			Object[] cityArr = (Object[]) cities;
			jsonObject.put(cityArr[0], cityArr[1]);
		});

		try {
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.print(jsonObject.toString().replace("\"", "").replace(",", ";"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void populateGrampanchayatList() {
		JSONObject jsonObject = new JSONObject();
		utilService.listGramPanchayatIdCodeName().stream().forEach(cities -> {
			Object[] cityArr = (Object[]) cities;
			jsonObject.put(cityArr[1], cityArr[2]);
		});

		try {
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.print(jsonObject.toString().replace("\"", "").replace(",", ";"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Populate state.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public void populateState() throws Exception {

		if (!StringUtil.isEmpty(selectedCountry)) {
			states = utilService.listStatesByCode(selectedCountry.trim());
		}
		JSONArray stateArr = new JSONArray();
		if (!ObjectUtil.isEmpty(states)) {
			for (State state : states) {
				stateArr.add(getJSONObject(state.getCode(), state.getCode() + " - " + state.getName()));
			}
		}
		sendAjaxResponse(stateArr);
	}

	/**
	 * Populate locality.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public void populateLocality() throws Exception {

		if (!selectedState.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedState))) {
			localities = utilService.listLocalities(selectedState.trim());
		}
		JSONArray localtiesArr = new JSONArray();
		if (!ObjectUtil.isEmpty(localities)) {
			for (Locality locality : localities) {
				localtiesArr.add(getJSONObject(locality.getCode(), locality.getCode() + " - " + locality.getName()));
			}
		}
		sendAjaxResponse(localtiesArr);
	}

	/**
	 * Populate city.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public void populateCity() throws Exception {

		if ((!StringUtil.isEmpty(selectedDistrict) && !selectedDistrict.equalsIgnoreCase("null"))) {
			cities = utilService.listMunicipalitiesByCode(selectedDistrict);
		}
		JSONArray cityArray = new JSONArray();
		if (!ObjectUtil.isEmpty(cities)) {
			for (Municipality municipality : cities) {
				cityArray.add(
						getJSONObject(municipality.getCode(), municipality.getCode() + " - " + municipality.getName()));
			}
		}
		sendAjaxResponse(cityArray);
	}

	/**
	 * Gets the selected city.
	 * 
	 * @return the selected city
	 */
	public String getSelectedCity() {

		return selectedCity;
	}

	/**
	 * Sets the selected city.
	 * 
	 * @param selectedCity
	 *            the new selected city
	 */
	public void setSelectedCity(String selectedCity) {

		this.selectedCity = selectedCity;
	}

	public Map<String, String> getGramPanchayats() {
		Map<String, String> gramPanchayat = new LinkedHashMap<String, String>();
		if (!StringUtil.isEmpty(selectedCity)) {
			gramPanchayats = utilService.listGramPanchayatsByCode(selectedCity);
			for (GramPanchayat gram : gramPanchayats) {
				gramPanchayat.put(gram.getCode(), gram.getCode() + " - " + gram.getName());
			}
		}
		return gramPanchayat;
	}

	/**
	 * Sets the gram panchayats.
	 * 
	 * @param gramPanchayats
	 *            the new gram panchayats
	 */
	public void setGramPanchayats(List<GramPanchayat> gramPanchayats) {

		this.gramPanchayats = gramPanchayats;
	}

	/**
	 * Populate gram panchayats.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public void populateGramPanchayat() throws Exception {

		if ((!StringUtil.isEmpty(selectedCity) && !selectedCity.equalsIgnoreCase("null"))) {
			gramPanchayats = utilService.listGramPanchayatsByCityCode(selectedCity);
		}
		JSONArray gramPanchayatArray = new JSONArray();
		if (!ObjectUtil.isEmpty(gramPanchayats)) {
			for (GramPanchayat gramPanchayat : gramPanchayats) {
				gramPanchayatArray.add(getJSONObject(gramPanchayat.getCode(),
						gramPanchayat.getCode() + " - " + gramPanchayat.getName()));
			}
		}
		sendAjaxResponse(gramPanchayatArray);
	}

	/**
	 * Gets the selected gram panchayat.
	 * 
	 * @return the selected gram panchayat
	 */
	public String getSelectedGramPanchayat() {

		return selectedGramPanchayat;
	}

	/**
	 * Sets the selected gram panchayat.
	 * 
	 * @param selectedGramPanchayat
	 *            the new selected gram panchayat
	 */
	public void setSelectedGramPanchayat(String selectedGramPanchayat) {

		this.selectedGramPanchayat = selectedGramPanchayat;
	}

	public void setIdGenerator(IUniqueIDGenerator idGenerator) {

		this.idGenerator = idGenerator;
	}

	public String getGramPanchayatEnable() {
		return gramPanchayatEnable!=null ? gramPanchayatEnable : "0";
	}

	public void setGramPanchayatEnable(String gramPanchayatEnable) {
		this.gramPanchayatEnable = gramPanchayatEnable;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}



}
