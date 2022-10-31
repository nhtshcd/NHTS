/**
 * StateAction.java
 * Copyright (c) 2008-2009, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.action;

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
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;



/**
 * The Class StateAction.
 */
public class StateAction extends SwitchValidatorAction {

	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(StateAction.class);

	

	/** The state. */
	private State state;

	/** The id. */
	private String id;

	private IUniqueIDGenerator idGenerator;

	private String selectedCountry;
	private String code;
	private String email;
	private String phoneNo;
	private String inChargeName;

	 @Autowired
	  private IUtilService utilService;

	public void setIdGenerator(IUniqueIDGenerator idGenerator) {

		this.idGenerator = idGenerator;
	}

	/**
	 * Gets the state.
	 * 
	 * @return the state
	 */
	public State getState() {

		return state;
	}

	/**
	 * Sets the state.
	 * 
	 * @param state
	 *            the new state
	 */
	public void setState(State state) {

		this.state = state;
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
	 * @see com.sourcetrace.esesw.view.SwitchAction#data()
	 */
	public String data() throws Exception {

		Map<String, String> searchRecord = getDataTableJQGridRequestParam(); // get the
																	// search
																	// parameter
																	// with
		// value

		State filter = new State();

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
		if (!StringUtil.isEmpty(searchRecord.get("selectedCountry"))) {
			Country country = new Country();
			country.setName(searchRecord.get("selectedCountry").trim());
			filter.setCountry(country);
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

		State state = (State) obj;
		JSONObject jsonObject = new JSONObject();
		//JSONArray rows = new JSONArray();
		JSONObject objDt = new JSONObject();
		JSONObject actionOnj = new JSONObject();
		if ((getIsMultiBranch().equalsIgnoreCase("1")
				&& (getIsParentBranch().equals("1") || StringUtil.isEmpty(branchIdValue)))) {

			if (StringUtil.isEmpty(branchIdValue)) {
				objDt.put("branch",
						!StringUtil.isEmpty(getBranchesMap().get(getParentBranchMap().get(state.getBranchId())))
								? getBranchesMap().get(getParentBranchMap().get(state.getBranchId()))
								: getBranchesMap().get(state.getBranchId()));

			}
			objDt.put("subbranch", getBranchesMap().get(state.getBranchId()));

		} else {
			if (StringUtil.isEmpty(branchIdValue)) {
				objDt.put("branch", getBranchesMap().get(state.getBranchId()));
			}
		}
		
		
		objDt.put("code",state.getCode());
		
		//rows.add(state.getName());
		String name = getLanguagePref(getLoggedInUserLanguage(), state.getCode().trim().toString());
		if(!StringUtil.isEmpty(name) && name != null){
			objDt.put("countryname1",name);
		}else{
			objDt.put("countryname1",state.getName());
		}
		
		String countryName = getLanguagePref(getLoggedInUserLanguage(), state.getCountry().getCode().trim().toString());
		if(!StringUtil.isEmpty(countryName) && countryName != null){
			objDt.put("countnam1",countryName);
		}else{
			objDt.put("countnam1",state.getCountry().getName());
		}
	
		actionOnj.put("code", state.getCode());
		actionOnj.put("name", state.getName());
		actionOnj.put("id", state.getId());
		actionOnj.put("country", state.getCountry().getCode());
		objDt.put("edit","<a href='#' onclick='ediFunctionState(\"" + StringEscapeUtils.escapeJavaScript(actionOnj.toString())
		+ "\")' class='fa fa-edit' title='Edit' ></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='deleteState(\""
						+ state.getId() + "\")' class='fa fa-trash' title='Delete' ></a>");
		
		jsonObject.put("DT_RowId", state.getId());
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

		if (state == null) {
			command = CREATE;
			request.setAttribute(HEADING, getLocaleProperty(CREATE));
			return INPUT;
		} else {
			state.setCode(idGenerator.getStateIdSeq());
			state.setBranchId(getBranchId());
			utilService.addState(state);
			return REDIRECT;
		}
	}

	@SuppressWarnings("unchecked")
	public void populateStateCreate() {
		state = new State();
		state.setName(getName());
		state.setBranchId(getBranchId());
		
		state.setCountry(utilService.findCountryByCode(getSelectedCountry()));
		state.setCode(idGenerator.getStateIdSeq());
		State s=utilService.findStateByName(state.getName());
		if(state.getName()==null || StringUtil.isEmpty(state.getName())){
			getJsonObject().put("status", "0"); 
			getJsonObject().put("msg", getText("msg.nameEmpty"));  
		     getJsonObject().put("title", getText("title.error"));
		}
		else if(ObjectUtil.isEmpty(s)){
			utilService.addState(state);
			getJsonObject().put("status", "1"); 
			getJsonObject().put("msg", getText("msg.added"));  
		    getJsonObject().put("title", getText("title.success"));
		}
		else{
			getJsonObject().put("status", "0");
			getJsonObject().put("msg", getLocaleProperty("msg.stateDuplicateError"));  
		     getJsonObject().put("title", getText("title.error"));
		}
		
	    sendAjaxResponse(getJsonObject());
	}

	@SuppressWarnings("unchecked")
	public void populateStateUpdate() {
		if (!StringUtil.isEmpty(getId())) {
			state = utilService.findStateById(Long.valueOf(getId()));
			
				
			state.setName(getName());
			/*state.setEmail(getEmail());
			state.setPhoneNo(getPhoneNo());
			state.setInChargeName(getInChargeName());*/
			//state.setCountry(locationService.findCountryByName(getSelectedCountry()));
			// state.setCode(idGenerator.getStateIdSeq());
			State s=utilService.findStateByName(state.getName());
			if(state.getName()==null || StringUtil.isEmpty(state.getName())){
				getJsonObject().put("msg", getText("msg.nameEmpty"));  
			     getJsonObject().put("title", getText("title.error"));
			}
			else if(s != null && state.getId() != s.getId()){
				getJsonObject().put("msg", getText("msg.stateDuplicateError"));  
			     getJsonObject().put("title", getText("title.error"));
			}
			else{
				utilService.editState(state);
				 getJsonObject().put("msg", getText("msg.updated"));  
			     getJsonObject().put("title", getText("title.success"));
			}
			
			
			
		     sendAjaxResponse(getJsonObject());
			
		}
	}
	
	
	public void countryList() throws Exception{
		
		List<Country>CountryProductList=utilService.listCountries();
		JSONArray countryList = new JSONArray();
		if (!ObjectUtil.isEmpty(CountryProductList)) {
			for (Country Country: CountryProductList) {
				countryList.add(getJSONObject(Country.getCode(),Country.getName()));
		}
	}
		sendAjaxResponse(countryList);
	 }
		 @SuppressWarnings("unchecked")
			protected JSONObject getJSONObject( Object id, Object name) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", id);
				jsonObject.put("name", name);
				return jsonObject;
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
			state = utilService.findStateById(Long.valueOf(id));
			if (state == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			setCurrentPage(getCurrentPage());
			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getLocaleProperty(UPDATE));
		} else {
			if (state != null) {
				State temp = utilService.findStateById(state.getId());
				if (temp == null) {
					addActionError(NO_RECORD);
					return REDIRECT;
				}
				setCurrentPage(getCurrentPage());
				temp.setName(state.getName());
				// temp.setCode(state.getCode());
				temp.setCountry(state.getCountry());
				utilService.editState(temp);
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
			state = utilService.findStateById(Long.valueOf(id));
			if (state == null) {
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

		if (this.getId() != null && !(this.getId().equals(EMPTY))) {
			state = utilService.findStateById(Long.valueOf(id));
			
			setCurrentPage(getCurrentPage());
			if (state != null && !ObjectUtil.isListEmpty(utilService.listLocalitiesByStateID(state.getId()))) {
			
				getJsonObject().put("msg", getLocaleProperty("delete.warn"));
				//getJsonObject().put("msg", getLocaleProperty("statedelete.warn"));  
			    getJsonObject().put("title", getText("title.error"));
				
			} else {
				utilService.removeState(state.getName());
				
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.view.ESEValidatorAction#getData()
	 */
	/**
	 * @see com.sourcetrace.esesw.view.SwitchValidatorAction#getData()
	 */
	@Override
	public Object getData() {

		if (state != null) {
			state.setCountry(utilService.findCountryByName(state.getCountry().getName()));
		}
		return state;

	}

	public String getSelectedCountry() {
		return selectedCountry;
	}

	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getInChargeName() {
		return inChargeName;
	}

	public void setInChargeName(String inChargeName) {
		this.inChargeName = inChargeName;
	}

	
}
