package com.sourcetrace.eses.action;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.Country;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.Locality;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.ServicePoint;
import com.sourcetrace.eses.entity.State;


import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.util.DateUtil;
public class MunicipalityAction extends SwitchValidatorAction{
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(MunicipalityAction.class);
	
	
	private String id;
	private String selectedCountry;
	private String selectedState;
	private String selectedDistrict;
	private String locality;
	
	private Municipality municipality;
	List<Locality> localities = new ArrayList<Locality>();
	List<State> stats = new ArrayList<State>();
	private String code;

	@Autowired
	private IUniqueIDGenerator idGenerator;
	
	 @Autowired
	  private IUtilService utilService;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSelectedCountry() {
		return selectedCountry;
	}

	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}

	public String getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(String selectedState) {
		this.selectedState = selectedState;
	}

	public String getSelectedDistrict() {
		return selectedDistrict;
	}

	public void setSelectedDistrict(String selectedDistrict) {
		this.selectedDistrict = selectedDistrict;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	/**
	 * @see com.sourcetrace.esesw.view.SwitchAction#data()
	 */
	public String data() throws Exception {

		Map<String, String> searchRecord = getDataTableJQGridRequestParam(); // get the
																	// search
																	// parameter
																	// with
		// value

		Municipality filter = new Municipality();

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

		/*
		 * if (!StringUtil.isEmpty(searchRecord.get("latitude"))) {
		 * filter.setLatitude(searchRecord.get("latitude").trim()); }
		 * 
		 * if (!StringUtil.isEmpty(searchRecord.get("longitude"))) {
		 * filter.setLongitude(searchRecord.get("longitude").trim()); }
		 */

		if (!StringUtil.isEmpty(searchRecord.get("selectedDistrict"))) {
			Locality locality = new Locality();
			locality.setName(searchRecord.get("selectedDistrict").trim());
			filter.setLocality(locality);
		}
		
		if (!StringUtil.isEmpty(searchRecord.get("selectedState"))) {
			filter.setStateName(searchRecord.get("selectedState").trim());;
		}
		
		if (!StringUtil.isEmpty(searchRecord.get("selectedCountry"))) {
			filter.setCountryName(searchRecord.get("selectedCountry").trim());;
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

		Municipality municipality = (Municipality) obj;
		JSONObject jsonObject = new JSONObject();
		//JSONArray rows = new JSONArray();
		JSONObject objDt = new JSONObject();
		JSONObject actionOnj = new JSONObject();
		if ((getIsMultiBranch().equalsIgnoreCase("1")
				&& (getIsParentBranch().equals("1") || StringUtil.isEmpty(branchIdValue)))) {

			if (StringUtil.isEmpty(branchIdValue)) {
				objDt.put("branch",
						!StringUtil.isEmpty(getBranchesMap().get(getParentBranchMap().get(municipality.getBranchId())))
								? getBranchesMap().get(getParentBranchMap().get(municipality.getBranchId()))
										: getBranchesMap().get(municipality.getBranchId()));

			}
			objDt.put("subbranch", getBranchesMap().get(municipality.getBranchId()));

		} else {
			if (StringUtil.isEmpty(branchIdValue)) {
				objDt.put("branch", getBranchesMap().get(municipality.getBranchId()));
			}
		}
		
		
		//if(!getCurrentTenantId().equalsIgnoreCase("livelihood")){
			objDt.put("code",municipality.getCode());
		//}
		//rows.add(municipality.getName());
		
		String name = getLanguagePref(getLoggedInUserLanguage(), municipality.getCode().trim().toString());
		if(!StringUtil.isEmpty(name) && name != null){
			objDt.put("villname1",name);
		}else{
			objDt.put("villname1",municipality.getName());
		}
		
		
		String locatlityName = getLanguagePref(getLoggedInUserLanguage(), municipality.getLocality().getCode().trim().toString());
		if(!StringUtil.isEmpty(locatlityName) && locatlityName != null){
			objDt.put("locaname1",locatlityName);
		}else{
			objDt.put("locaname1",municipality.getLocality().getName());
		}
		
		String stateName = getLanguagePref(getLoggedInUserLanguage(), municipality.getLocality().getState().getCode().trim().toString());
		if(!StringUtil.isEmpty(stateName) && stateName != null){
			objDt.put("staname1",stateName);
		}else{
			objDt.put("staname1",municipality.getLocality().getState().getName());
		}
		
		String countryName = getLanguagePref(getLoggedInUserLanguage(), municipality.getLocality().getState().getCountry().getCode().trim().toString());
		if(!StringUtil.isEmpty(countryName) && countryName != null){
			objDt.put("counname1",countryName);
		}else{
			objDt.put("counname1",municipality.getLocality().getState().getCountry().getName());
		}
		actionOnj.put("code", municipality.getCode());
		actionOnj.put("name", municipality.getName());
		//actionOnj.put("city", municipality.getCity().getId());
		actionOnj.put("id", municipality.getId());
		actionOnj.put("locality", municipality.getLocality().getCode());
		actionOnj.put("state", municipality.getLocality().getState().getCode());
		actionOnj.put("country", municipality.getLocality().getState().getCountry().getCode());
		objDt.put("edit","<a href='#' onclick='ediFunctionCity(\"" + StringEscapeUtils.escapeJavaScript(actionOnj.toString())
		+ "\")' class='fa fa-edit' title='Edit' ></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='deletMunicipality(\""
				+ municipality.getId() + "\")' class='fa fa-trash' title='Delete' ></a>");
		
		
		
		
		
		jsonObject.put("DT_RowId", municipality.getId());
		jsonObject.put("cell", objDt);
		return jsonObject;
	}
	
	@SuppressWarnings("unchecked")
	public void populateSaveCity() {
		getJsonObject().clear();
		Municipality city = new Municipality();
		String codeGenType = null;
		ESESystem preferences = utilService.findPrefernceById("1");
		if (!StringUtil.isEmpty(preferences)) {
			/*
			 * setGramPanchayatEnable(preferences.getPreferences().get(ESESystem
			 * .ENABLE_GRAMPANJAYAT));
			 */
			codeGenType = preferences.getPreferences().get(ESESystem.CODE_TYPE);
		}
		if (!StringUtil.isEmpty(selectedDistrict)) {
			Locality locality = utilService.findLocalityByCode(selectedDistrict);
			city.setName(getName());
			city.setLocality(locality);
			city.setBranchId(getBranchId());
			city.setRevisionNo(DateUtil.getRevisionNumber());
			if (codeGenType.equals("1") ) {
				String municipalityCodeSeq = idGenerator.getMunicipalityHHIdSeq(locality.getCode());
				BigInteger codeSeq = new BigInteger(municipalityCodeSeq);
				String maxCode = codeSeq.subtract(new BigInteger("1")).toString();
				if (Integer.valueOf(maxCode.substring(1, 2)) >= 9) {
					// addActionError(getLocaleProperty("error.municipalityExceed"));
					getJsonObject().put("msg", getLocaleProperty("error.municipalityExceed"));
					getJsonObject().put("title", getText("title.error"));
				} else {
					//city.setCode(municipalityCodeSeq);
					city.setCode(getCode());

				}
			}
				//city.setCode(idGenerator.getMandalIdSeq());
			city.setCode(getCode());
			
				Municipality c = utilService.findMunicipalityByName(city.getName());
				Municipality mcode = utilService.findMunicipalityByCode(getCode());
				
				
//				if(city.getName()==null || StringUtil.isEmpty(city.getName())){
//						getJsonObject().put("status", "0"); 
//						getJsonObject().put("msg", getText("msg.nameEmpty"));  
//					    getJsonObject().put("title", getText("title.error"));
//					}
//				 else if(ObjectUtil.isEmpty(c)){
//					    utilService.addMunicipality(city);
//						getJsonObject().put("status", "1"); 
//						getJsonObject().put("msg", getText("msg.added"));  
//					    getJsonObject().put("title", getText("title.success"));
//					}
//				else{
//						getJsonObject().put("status", "0");
//						getJsonObject().put("msg", getLocaleProperty("msg.cityDuplicateError"));  
//					    getJsonObject().put("title", getText("title.error"));
//					}
				
				if (c != null) {
					getJsonObject().put("status", "0");
					getJsonObject().put("msg", getLocaleProperty("msg.cityDuplicateError"));  
				    getJsonObject().put("title", getText("title.error"));
				}
				if (mcode != null) {
					if (getJsonObject().containsKey("msg")) {

						getJsonObject().put("msg", getJsonObject().get("msg") + "<br>" + getText("cropcatCode.exist"));
						getJsonObject().put("title", getText("title.error"));
					} else {
						getJsonObject().put("msg", getText("cropcatCode.exist"));
						getJsonObject().put("title", getText("title.error"));
					}
				}
				
				
				if (!getJsonObject().containsKey("msg")) {
					utilService.addMunicipality(city);
					getJsonObject().put("status", "1"); 
					getJsonObject().put("msg", getText("msg.added"));  
				    getJsonObject().put("title", getText("title.success"));
				}
			
			sendAjaxResponse(getJsonObject());
		}
	}


	
	
	@SuppressWarnings("unchecked")
	public void populateCityUpdate() {
		if (!StringUtil.isEmpty(getId())) {
			// Village village =
			// locationService.findVillageById(Long.valueOf(getId()));
			Municipality city = utilService.findMunicipalityById(Long.valueOf(getId()));
			
		/*		if(code!=null && !StringUtil.isEmpty(code)){
					Municipality m=locationService.findMunicipalityByCode(code);	
					if(m!=null && !ObjectUtil.isEmpty(m)){
						getJsonObject().put("msg", getText("msg.codeDuplicate"));  
					     getJsonObject().put("title", getText("title.error"));
					     sendAjaxResponse(getJsonObject());
					}
					else{*/
						/*if (!StringUtil.isEmpty(city)) {
						city.setCode(code);
						city.setName(getName());
						Locality locality = utilService.findLocalityByCode(getSelectedDistrict());
						city.setLocality(locality);
						city.setRevisionNo(DateUtil.getRevisionNumber());
						utilService.editMunicipality(city);
						getJsonObject().put("msg", getText("msg.updated"));
						getJsonObject().put("title", getText("title.success"));
						sendAjaxResponse(getJsonObject());
						}*/
					/*}
				}*/
			

			if (!StringUtil.isEmpty(city)) {
				city.setName(getName());
				Locality locality = utilService.findLocalityByCode(getSelectedDistrict());
				city.setLocality(locality);
				city.setRevisionNo(DateUtil.getRevisionNumber());
				utilService.editMunicipality(city);
				getJsonObject().put("msg", getText("msg.updated"));
				getJsonObject().put("title", getText("title.success"));
				sendAjaxResponse(getJsonObject());
			}
		
		}
	}

		
	public String create() throws Exception {

		if (municipality == null) {

			command = CREATE;
			request.setAttribute(HEADING, getLocaleProperty(CREATE));
			return INPUT;
		} else {

			Locality locality = utilService.findLocalityByCode(municipality.getLocality().getCode());

			ESESystem preferences = utilService.findPrefernceById("1");
			String codeGenType = preferences.getPreferences().get(ESESystem.CODE_TYPE);
			if (codeGenType.equals("1")) {
				String municipalityCodeSeq = idGenerator.getMunicipalityHHIdSeq(locality.getCode());
				BigInteger codeSeq = new BigInteger(municipalityCodeSeq);
				String maxCode = codeSeq.subtract(new BigInteger("1")).toString();
				if (Integer.valueOf(maxCode.substring(1, 2)) >= 9) {
					addActionError(getLocaleProperty("error.municipalityExceed"));
					return INPUT;
				} else {
					municipality.setCode(municipalityCodeSeq);

				}
			} else if (!getCurrentTenantId().equalsIgnoreCase("awi")) {
				municipality.setCode(idGenerator.getMandalIdSeq());
			}

			municipality.setLocality(locality);
			municipality.setBranchId(getBranchId());
			utilService.addMunicipality(municipality);
			// Added for Adding Service Point when creating Municipatity
			// addServicePoint(municipality);
			return REDIRECT;
		}
	}

	/**
	 * Update.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String update() throws Exception {

		if (id != null && !id.equals("")) {
			municipality = utilService.findMunicipalityById(Long.valueOf(id));
			if (municipality == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			setSelectedCountry(municipality.getLocality().getState().getCountry().getName());
			setSelectedState(municipality.getLocality().getState().getCode());
			localities = utilService.listLocalities(municipality.getLocality().getState().getName());
			municipality.getLocality().setName(municipality.getLocality().getName());
			setCurrentPage(getCurrentPage());
			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getLocaleProperty(UPDATE));
		} else {
			ESESystem preferences = utilService.findPrefernceById("1");
			String codeGenType = preferences.getPreferences().get(ESESystem.CODE_TYPE);
			if (municipality != null) {
				Municipality temp1 = utilService.findMunicipalityById(municipality.getId());
				if (temp1 == null) {
					addActionError(NO_RECORD);
					return REDIRECT;
				}
				setCurrentPage(getCurrentPage());

				if (codeGenType.equals("1") ) {
					if (!municipality.getLocality().getName().equalsIgnoreCase(temp1.getLocality().getName())) {
						String municipalityCodeSeq = idGenerator.getMunicipalityHHIdSeq(temp1.getLocality().getCode());
						temp1.setCode(municipalityCodeSeq);
					}
				}

				// temp1.setCode(municipality.getCode());
				temp1.setName(municipality.getName());
				Locality locality = utilService.findLocalityByCode(municipality.getLocality().getCode());

				temp1.setLocality(locality);
				temp1.setPostalCode(municipality.getPostalCode());
				// temp1.setLatitude(municipality.getLatitude());
				// temp1.setLongitude(municipality.getLongitude());
				temp1.setBranchId(getBranchId());

				utilService.editMunicipality(temp1);
				// Updating warehouse when editing Municipality
				editWarehouse(temp1);
				// Updating ServicePoint when editing Municipality
				//editServicePoint(temp1);

			}
			command = UPDATE;
			request.setAttribute(HEADING, getLocaleProperty(LIST));
			return LIST;
		}
		return super.execute();
	}


	
	/**
	 * Edits the warehouse.
	 * 
	 * @param municipality
	 *            the municipality
	 */
	private void editWarehouse(Municipality municipality) {

		/*Warehouse warehouse = utilService.findWarehouseByCode(municipality.getCode());
		if (!ObjectUtil.isEmpty(warehouse)) {
			warehouse.setName(municipality.getName());
			utilService.editWarehouse(warehouse);
		}*/
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
			municipality = utilService.findMunicipalityById(Long.valueOf(id));
			if (municipality == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			setCurrentPage(getCurrentPage());
			command = UPDATE;
			view = DETAIL;
			request.setAttribute(HEADING, getLocaleProperty(DETAIL));
		} else {
			request.setAttribute(HEADING, getLocaleProperty(LIST));
			return LIST;
		}
		return view;
	}

	/**
	 * Delete.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */

	@SuppressWarnings("unchecked")
	public void delete() throws Exception {

		if (id != null && !id.equals("")) {
			municipality = utilService.findMunicipalityById(Long.valueOf(id));

			String flag = null;
			if (!ObjectUtil.isListEmpty(utilService.listVillagesByCityID(municipality.getId()))) {
				flag = "village.mapped.municipality";
				
			}

			/*
			 * if(!ObjectUtil.isListEmpty(municipality.getGramPanchayats())){
			 * flag="gramPanchayat.mapped"; }else{ flag =
			 * locationService.isCityMappingexist(municipality); }
			 */

			if (StringUtil.isEmpty(flag)) {

				utilService.removeCityWarehouseProduct(municipality.getCode());
				if (municipality.getLocality() != null && !ObjectUtil.isEmpty(municipality.getLocality())) {
					if (municipality.getLocality().getMunicipalities().contains(municipality)) {
						municipality.getLocality().getMunicipalities().remove(municipality);

						utilService.editLocality(municipality.getLocality());
					}
				} 
				utilService.removeCity(municipality);
				getJsonObject().put("msg", getText("msg.deleted"));  
			    getJsonObject().put("title", getText("title.success"));
			}
			
			else {
				getJsonObject().put("msg", getLocaleProperty(flag));  
			    getJsonObject().put("title", getText("title.error"));
			}

			//addActionError(getLocaleProperty(flag));
			 sendAjaxResponse(getJsonObject());
		}

	}

	
	@SuppressWarnings("unchecked")
	protected JSONObject getJSONObject(Object id, Object name) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("name", name);
		return jsonObject;
	}
	
	@Override
	public Object getData() {

		if (ObjectUtil.isEmpty(municipality)) {
			return null;
		} else {
			Country country = new Country();
			country.setName(this.selectedCountry);

			State state = new State();
			state.setCode(this.selectedState);
			state.setCountry(country);

			Locality locality = new Locality();
			locality.setCode(municipality.getLocality().getCode());
			locality.setState(state);
			municipality.setLocality(locality);

			return municipality;
		}

	}


}
