/**
] * LocalityAction.java
 * Copyright (c) 2008-2009, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.action;

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
import com.sourcetrace.eses.entity.Locality;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;


/**
 * The Class LocalityAction.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class LocalityAction extends SwitchValidatorAction {

	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(LocalityAction.class);

	

	/** The locality. */
	private Locality locality;

	/** The id. */
	private String id;

	/** The country name. */
	private String countryName;
	private String countryCode;

	/** The selected country. */
	private String selectedCountry;

	/** The selected state. */
	private String selectedState;

	/** The selected location. */
	private String selectedLocation;

	private IUniqueIDGenerator idGenerator;
	
	 @Autowired
	  private IUtilService utilService;


	List<State> statesList = new ArrayList<State>();
	
	List<Locality> localities = new ArrayList<Locality>();

	private String code;
	/**
	 * Gets the states list.
	 * 
	 * @return the states list
	 */
	public List<State> getStatesList() {

		return statesList;
	}

	/**
	 * Sets the states list.
	 * 
	 * @param statesList
	 *            the new states list
	 */
	public void setStatesList(List<State> statesList) {

		this.statesList = statesList;
	}

	/**
	 * Gets the selected location.
	 * 
	 * @return the selected location
	 */
	public String getSelectedLocation() {

		return selectedLocation;
	}

	/**
	 * Sets the selected location.
	 * 
	 * @param selectedLocation
	 *            the new selected location
	 */
	public void setSelectedLocation(String selectedLocation) {

		this.selectedLocation = selectedLocation;
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
	 * Gets the locality.
	 * 
	 * @return the locality
	 */
	public Locality getLocality() {

		return locality;
	}

	/**
	 * Sets the locality.
	 * 
	 * @param locality
	 *            the new locality
	 */
	public void setLocality(Locality locality) {

		this.locality = locality;
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
	 * Gets the country name.
	 * 
	 * @return the country name
	 */
	public String getCountryName() {

		return countryName;
	}

	/**
	 * Sets the country name.
	 * 
	 * @param countryName
	 *            the new country name
	 */
	public void setCountryName(String countryName) {

		this.countryName = countryName;
	}

	public void setIdGenerator(IUniqueIDGenerator idGenerator) {

		this.idGenerator = idGenerator;
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

		Locality filter = new Locality();

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

		if (!StringUtil.isEmpty(searchRecord.get("selectedCountry")) || !StringUtil.isEmpty(searchRecord.get("selectedState"))) {
			State state = new State();

			if (!StringUtil.isEmpty(searchRecord.get("selectedCountry"))) {
				Country country = new Country();
				country.setName(searchRecord.get("selectedCountry").trim());
				state.setCountry(country);
				
				
			}

			if (!StringUtil.isEmpty(searchRecord.get("selectedState")))
				state.setName(searchRecord.get("selectedState").trim());

			filter.setState(state);
		}

		Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
				getPage());

		return sendDataTableJSONResponse(data);
	}

	/**
	 * @see com.sourcetrace.esesw.view.SwitchAction#toJSON(java.lang.Object)
	 */
	public JSONObject toJSON(Object obj) {

		Locality locality = (Locality) obj;
		JSONObject jsonObject = new JSONObject();
		//JSONArray rows = new JSONArray();
		JSONObject objDt = new JSONObject();
		JSONObject actionOnj = new JSONObject();
		if ((getIsMultiBranch().equalsIgnoreCase("1")
				&& (getIsParentBranch().equals("1") || StringUtil.isEmpty(branchIdValue)))) {

			if (StringUtil.isEmpty(branchIdValue)) {
				objDt.put("branch",
						!StringUtil.isEmpty(getBranchesMap().get(getParentBranchMap().get(locality.getBranchId())))
								? getBranchesMap().get(getParentBranchMap().get(locality.getBranchId()))
								: getBranchesMap().get(locality.getBranchId()));

			}
			objDt.put("subbranch", getBranchesMap().get(locality.getBranchId()));

		} else {
			if (StringUtil.isEmpty(branchIdValue)) {
				objDt.put("branch", getBranchesMap().get(locality.getBranchId()));
			}
		}
		objDt.put("code",locality.getCode());
		
		//rows.add(locality.getName());
		
		String name = getLanguagePref(getLoggedInUserLanguage(), locality.getCode().trim().toString());
		if(!StringUtil.isEmpty(name) && name != null){
			objDt.put("villname",name);
		}else{
			objDt.put("villname",locality.getName());
		}
		
		
		String stateName = getLanguagePref(getLoggedInUserLanguage(), locality.getState().getCode().trim().toString());
		if(!StringUtil.isEmpty(stateName) && stateName != null){
			objDt.put("staname",stateName);
		}else{
			objDt.put("staname",locality.getState().getName());
		}
		
		String countryName = getLanguagePref(getLoggedInUserLanguage(), locality.getState().getCountry().getCode().trim().toString());
		if(!StringUtil.isEmpty(countryName) && countryName != null){
			objDt.put("couname",countryName);
		}else{
			objDt.put("couname",locality.getState().getCountry().getName());
		}
		actionOnj.put("code", locality.getCode());
		actionOnj.put("name", locality.getName());
		actionOnj.put("id", locality.getId());
		actionOnj.put("state", locality.getState().getCode());
		actionOnj.put("country", locality.getState().getCountry().getCode());
		objDt.put("edit","<a href='#' onclick='ediFunctionLoc(\"" + StringEscapeUtils.escapeJavaScript(actionOnj.toString())
		+ "\")' class='fa fa-edit' title='Edit' ></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='deleteLocality(\""
						+ locality.getId() + "\")' class='fa fa-trash' title='Delete' ></a>");
		
		jsonObject.put("DT_RowId", locality.getId());
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

		if (locality == null) {
			command = CREATE;
			request.setAttribute(HEADING, getLocaleProperty(CREATE));
			return INPUT;
		} else {
			ESESystem preferences = utilService.findPrefernceById("1");
			String codeGenType = preferences.getPreferences().get(ESESystem.CODE_TYPE);
			if (codeGenType.equals("1") ) {
				String districtCodeSeq = idGenerator.getLocalityHHIdSeq();
				BigInteger codeSeq = new BigInteger(districtCodeSeq);
				String maxCode = codeSeq.subtract(new BigInteger("1")).toString();
				if (Integer.valueOf(maxCode) >= 9) {
					addActionError(getLocaleProperty("error.localityExceed"));
					return INPUT;
				} else {
					locality.setCode(districtCodeSeq);
				}
			} else {
				locality.setCode(idGenerator.getDistrictIdSeq());
			}
			locality.setBranchId(getBranchId());
			locality.setState(utilService.findStateByCode(locality.getState().getCode()));
			utilService.addLocality(locality);
			command = CREATE;
			return REDIRECT;
		}
	}
	
/*	@SuppressWarnings("unchecked")
	public void populateSaveLocality(){
		locality = new Locality();
		ESESystem preferences = preferncesService.findPrefernceById("1");
		String codeGenType = preferences.getPreferences().get(ESESystem.CODE_TYPE);
		if (codeGenType.equals("1") ) {
			String districtCodeSeq = idGenerator.getLocalityHHIdSeq();
			BigInteger codeSeq = new BigInteger(districtCodeSeq);
			String maxCode = codeSeq.subtract(new BigInteger("1")).toString();
			if (Integer.valueOf(maxCode) >= 9) {
				//addActionError(getLocaleProperty("error.localityExceed"));
				 getJsonObject().put("msg", getLocaleProperty("error.localityExceed"));  
			     getJsonObject().put("title", getText("title.error"));
				
			} else {
				locality.setCode(districtCodeSeq);
			}
		} else {
			locality.setCode(idGenerator.getDistrictIdSeq());
		}
		locality.setName(getName());
		locality.setBranchId(getBranchId());
		locality.setState(locationService.findStateByCode(getSelectedState()));
		locationService.addLocality(locality);
		
		 getJsonObject().put("msg", getText("msg.added"));  
	     getJsonObject().put("title", getText("title.success"));
	     sendAjaxResponse(getJsonObject());
	}*/
	
	@SuppressWarnings("unchecked")
	public void populateSaveLocality(){
		getJsonObject().clear();
		locality = new Locality();
		ESESystem preferences = utilService.findPrefernceById("1");
		String codeGenType = preferences.getPreferences().get(ESESystem.CODE_TYPE);
		if (codeGenType.equals("1") ) {
			String districtCodeSeq = idGenerator.getLocalityHHIdSeq();
			BigInteger codeSeq = new BigInteger(districtCodeSeq);
			String maxCode = codeSeq.subtract(new BigInteger("1")).toString();
			if (Integer.valueOf(maxCode) >= 9) {
				//addActionError(getLocaleProperty("error.localityExceed"));
				 getJsonObject().put("msg", getLocaleProperty("error.localityExceed"));  
			     getJsonObject().put("title", getText("title.error"));
				
			} else {
				//locality.setCode(districtCodeSeq);
				locality.setCode(getCode());
			}
		} else {
			      // locality.setCode(idGenerator.getDistrictIdSeq());
			         locality.setCode(getCode());
	          	}
			    locality.setName(getName());
		        locality.setBranchId(getBranchId());
		        locality.setState(utilService.findStateByCode(getSelectedState()));
		        
			    Locality l=utilService.findLocalityByName(locality.getName());
			    Locality lCode=utilService.findLocalityByCode(getCode());
			    
				    if (l != null) {
				    	getJsonObject().put("status", "0");
						getJsonObject().put("msg", getLocaleProperty("msg.localityDuplicateError"));  
					    getJsonObject().put("title", getText("title.error"));
					}
					if (lCode != null) {
						if (getJsonObject().containsKey("msg")) {

							getJsonObject().put("msg", getJsonObject().get("msg") + "<br>" + getText("cropcatCode.exist"));
							getJsonObject().put("title", getText("title.error"));
						} else {
							getJsonObject().put("msg", getText("cropcatCode.exist"));
							getJsonObject().put("title", getText("title.error"));
						}
					}
				    
			    if (!getJsonObject().containsKey("msg")) {
			    	utilService.addLocality(locality);
					getJsonObject().put("status", "1"); 
					getJsonObject().put("msg", getText("msg.added"));  
				    getJsonObject().put("title", getText("title.success"));
				}
	     sendAjaxResponse(getJsonObject());
	}
	
	@SuppressWarnings("unchecked")
	public void populateLocalityUpdate() {
		if (!StringUtil.isEmpty(getId())) {
			Locality locality = utilService.findLocalityById(Long.valueOf(getId()));
			if (!ObjectUtil.isEmpty(locality)) {
				
				/*if(code!=null && !StringUtil.isEmpty(code)){
					Locality loc=locationService.findLocalityByCode(code);
					if(loc!=null && !ObjectUtil.isEmpty(loc)){
						getJsonObject().put("msg", getText("msg.codeDuplicate"));  
					     getJsonObject().put("title", getText("title.error"));
					     sendAjaxResponse(getJsonObject());
					}
					else{*/
						/*locality.setCode(code);
						locality.setName(getName());
						State state = locationService.findStateByCode(getSelectedState());
						locality.setState(state);
						locality.setRevisionNo(DateUtil.getRevisionNumber());
						locationService.editLocality(locality);
						
						getJsonObject().put("msg", getText("msg.updated"));  
					     getJsonObject().put("title", getText("title.success"));
					     sendAjaxResponse(getJsonObject());*/
					/*}
				}*/
				
			
				locality.setName(getName());
				State state = utilService.findStateByCode(getSelectedState());
				locality.setState(state);
				locality.setRevisionNo(DateUtil.getRevisionNumber());
				utilService.editLocality(locality);
				
				getJsonObject().put("msg", getText("msg.updated"));  
			     getJsonObject().put("title", getText("title.success"));
			     sendAjaxResponse(getJsonObject());
				
			}
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
			locality = utilService.findLocalityById(Long.valueOf(id));
			if (locality == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			setSelectedCountry(locality.getState().getCountry().getName());
			setSelectedState(locality.getState().getCode());
			setCurrentPage(getCurrentPage());
			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getLocaleProperty(UPDATE));
		} else {
			if (locality != null) {

				Locality temp1 = utilService.findLocalityById(locality.getId());
				if (temp1 == null) {
					addActionError(NO_RECORD);
					return REDIRECT;
				}
				setCurrentPage(getCurrentPage());
				temp1.setName(locality.getName());

				if (locality.getState() == null) {
					addActionError(getLocaleProperty("empty.state"));
					command = UPDATE;
					request.setAttribute(HEADING, getLocaleProperty(UPDATE));
					return INPUT;
				}
				temp1.setState(utilService.findStateByCode(locality.getState().getCode()));
				// temp1.setCode(locality.getCode());
				temp1.getState().setCountry(utilService.findCountryByName(selectedCountry));
				utilService.editLocality(temp1);
				command = UPDATE;
				request.setAttribute(HEADING, getLocaleProperty(LIST));
				return LIST;

			}
			request.setAttribute(HEADING, getLocaleProperty(LIST));
			return LIST;
		}
		return super.execute();
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
			locality = utilService.findLocalityById(Long.valueOf(id));
			if (locality == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			setCountryName(locality.getState().getCountry().getName());
			setCountryCode(locality.getState().getCountry().getCode());
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
			setCurrentPage(getCurrentPage());
			locality = utilService.findLocalityById(Long.valueOf(id));
			
			if (locality != null
					&& !ObjectUtil.isListEmpty(utilService.listMunicipalitiesByLocalityId(locality.getId()))) {
				setCountryName(locality.getState().getCountry().getName());
//				addActionError(getLocaleProperty("delete.warn.locality"));
				getJsonObject().put("msg", getLocaleProperty("delete.warn.locality"));  
			    getJsonObject().put("title", getText("title.error"));
			} else {
				utilService.removeLocality(locality.getName());
				getJsonObject().put("msg", getText("msg.deleted"));  
			    getJsonObject().put("title", getText("title.success"));
			    
			}
			 sendAjaxResponse(getJsonObject());
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

	public Map<String, String> getCountries() {
		Map<String, String> countryMap = new LinkedHashMap<String, String>();
		List<Country> returnValue = utilService.listCountries();
		for (Country c : returnValue) {
			countryMap.put(c.getName(), c.getCode() + " - " + c.getName());
		}
		return countryMap;
	}

	/**
	 * Gets the states.
	 * 
	 * @return the states
	 */
	/*
	 * public List<State> getStates() {
	 * 
	 * if (!StringUtil.isEmpty(selectedCountry)) { statesList =
	 * locationService.listStates(selectedCountry); } return statesList;
	 * 
	 * }
	 */

	public Map<String, String> getStatesId() {
		Map<String, String> state = new LinkedHashMap<String, String>();
		if (!StringUtil.isEmpty(selectedCountry)) {
			statesList = utilService.listStates(selectedCountry);
			for (State s : statesList) {
				state.put(s.getCode(), s.getCode() + " - " + s.getName());
			}
		}
		return state;
	}

	/**
	 * Populate location.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	/*
	 * public String populateLocation() throws Exception {
	 * 
	 * if (!selectedCountry.equalsIgnoreCase("null")) { statesList =
	 * locationService.listStates(selectedCountry); } sendResponse(statesList);
	 * return null; }
	 */

	@SuppressWarnings("unchecked")
	protected JSONObject getJSONObject(Object id, Object name) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("name", name);
		return jsonObject;
	}

	public void populateLocality() throws Exception {
		if (!selectedState.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedState))) {
			localities = utilService.listLocalities(selectedState);
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
	 * @see com.sourcetrace.esesw.view.SwitchValidatorAction#getData()
	 */
	@Override
	public Object getData() {

		if (locality != null) {

			Country country = new Country();
			country.setName(this.selectedCountry);

			State state = new State();
			state.setCode(this.selectedState);
			state.setCountry(country);

			locality.setState(state);

		}

		return locality;

	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public List<Locality> getLocalitiesId() {
		return localities;
	}

	public void setLocalities(List<Locality> localities) {
		this.localities = localities;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
