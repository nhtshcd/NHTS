/**
 * CountryAction.java
 * Copyright (c) 2008-2009, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */

package com.sourcetrace.eses.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.Country;
import com.sourcetrace.eses.service.IFarmerService;

import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;



/**
 * The Class CountryAction.
 */
public class CountryAction extends SwitchValidatorAction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CountryAction.class);

	@Autowired

	private IUniqueIDGenerator idGenerator;

	 @Autowired
	  private IUtilService utilService;


	@Autowired
	private IFarmerService farmerService;
	

	private Country country;
	private String id;
	 DecimalFormat formatter = new DecimalFormat("0.00");



	public void setIdGenerator(IUniqueIDGenerator idGenerator) {

		this.idGenerator = idGenerator;
	}

	/**
	 * Gets the country.
	 * 
	 * @return the country
	 */
	public Country getCountry() {

		return country;
	}

	/**
	 * Sets the country.
	 * 
	 * @param country
	 *            the new country
	 */
	public void setCountry(Country country) {

		this.country = country;
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
																	// //
																	// parameter
																	// with
		// value

		Country filter = new Country();

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

		Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
				getPage());

		return sendDataTableJSONResponse(data);
	}

	/**
	 * @see com.sourcetrace.esesw.view.SwitchAction#toJSON(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(Object obj) {

		Country country = (Country) obj;
		JSONObject jsonObject = new JSONObject();
		//JSONArray rows = new JSONArray();
		JSONObject objDt = new JSONObject();
		JSONObject actionOnj = new JSONObject();
		
		if ((getIsMultiBranch().equalsIgnoreCase("1")
				&& (getIsParentBranch().equals("1") || StringUtil.isEmpty(branchIdValue)))) {

			if (StringUtil.isEmpty(branchIdValue)) {
				objDt.put("branch",
						!StringUtil.isEmpty(getBranchesMap().get(getParentBranchMap().get(country.getBranchId())))
								? getBranchesMap().get(getParentBranchMap().get(country.getBranchId()))
								: getBranchesMap().get(country.getBranchId()));

			}
			objDt.put("subbranch", getBranchesMap().get(country.getBranchId()));

		} else {
			if (StringUtil.isEmpty(branchIdValue)) {
				objDt.put("branch", getBranchesMap().get(country.getBranchId()));
			}
		}
		
		
		objDt.put("code","<font color=\"#0000FF\" style=\"cursor:pointer;\">" + country.getCode() + "</font>");
		//rows.add(country.getName());
		
		String name = getLanguagePref(getLoggedInUserLanguage(), country.getCode().trim().toString());
		if(!StringUtil.isEmpty(name) && name != null){
			objDt.put("countryname1",name);
		}else{
			objDt.put("countryname1",country.getName());
		}
		actionOnj.put("code", country.getCode());
		actionOnj.put("name", country.getName());
		//actionOnj.put("city", municipality.getCity().getId());
		actionOnj.put("id", country.getId());
		//actionOnj.put("locality", locality.getLocality().getId());
		//actionOnj.put("state", state.getState().getId());
	//	actionOnj.put("country", state.getCountry().getId());
		objDt.put("edit","<a href='#' onclick='ediFunctionCountry(\"" + StringEscapeUtils.escapeJavaScript(actionOnj.toString())
		+ "\")' class='fa fa-edit' title='Edit' ></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='deleteCountry(\""
						+ country.getId() + "\")' class='fa fa-trash' title='Delete' ></a>");
		jsonObject.put("DT_RowId", country.getId());
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

		if (country == null) {
			command = "create";
			request.setAttribute(HEADING, getText(CREATE));
			return INPUT;
		} else {
			country.setCode(idGenerator.getCountryIdSeq());
			country.setBranchId(getBranchId());
			utilService.addCountry(country);
			return REDIRECT;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void populateCountryCreate(){
		country = new Country();
		country.setName(getName());
		country.setCode(idGenerator.getCountryIdSeq());
		country.setBranchId(getBranchId());
		Country c=utilService.findCountryByName(country.getName());
		if(country.getName()==null || StringUtil.isEmpty(country.getName())){
			getJsonObject().put("status", "0"); 
			getJsonObject().put("msg", getText("msg.nameEmpty"));  
		     getJsonObject().put("title", getText("title.error"));
		}
		else if(ObjectUtil.isEmpty(c)){
		utilService.addCountry(country);
		 getJsonObject().put("status", "1"); 
		 getJsonObject().put("msg", getText("msg.added"));  
	     getJsonObject().put("title", getText("title.success"));
		}
		else{
			getJsonObject().put("status", "0");
			getJsonObject().put("msg", getText("msg.countryDuplicateError"));  
		     getJsonObject().put("title", getText("title.error"));
		}
	     sendAjaxResponse(getJsonObject());
	}
	
	@SuppressWarnings("unchecked")
	public void populateCountryUpdate(){
		if (!StringUtil.isEmpty(getId())) {
			country = utilService.findCountryById(Long.valueOf(getId()));
			country.setName(getName());
			// state.setCode(idxGenerator.getStateIdSeq());
			Country c=utilService.findCountryByName(country.getName());
			if(country.getName()==null || StringUtil.isEmpty(country.getName())){
				getJsonObject().put("status", "0"); 
				getJsonObject().put("msg", getText("msg.nameEmpty"));  
			     getJsonObject().put("title", getText("title.error"));
			}
			else if(c != null && country.getId() != c.getId()){
				getJsonObject().put("status", "0");
				getJsonObject().put("msg", getText("msg.countryDuplicateError"));  
			     getJsonObject().put("title", getText("title.error"));
			     
			}
			
			else{
				utilService.editCountry(country);
				 getJsonObject().put("status", "1"); 
				getJsonObject().put("msg", getText("msg.updated"));  
			     getJsonObject().put("title", getText("title.success"));
			}
			
		     sendAjaxResponse(getJsonObject());
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
			country = utilService.findCountryById(Long.valueOf(id));
			if (country == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			setCurrentPage(getCurrentPage());
			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getText(UPDATE));
		} else {
			if (country != null) {
				Country temp = utilService.findCountryById(country.getId());
				if (temp == null) {
					addActionError(NO_RECORD);
					return REDIRECT;
				}
				setCurrentPage(getCurrentPage());
				temp.setName(country.getName());
				// temp.setCode(country.getCode());
				utilService.editCountry(temp);
			}
			request.setAttribute(HEADING, getText(LIST));
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
			country = utilService.findCountryById(Long.valueOf(id));
			if (country == null) {
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
	 * Delete.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public void delete() throws Exception {

		if (this.getId() != null && !(this.getId().equals(EMPTY))) {
			country = utilService.findCountryById(Long.valueOf(id));
			
			setCurrentPage(getCurrentPage());
			if (country != null && !ObjectUtil.isListEmpty(country.getStates())) {
				//addActionError(getLocaleProperty("countryDelete.warn"));
				getJsonObject().put("msg", getLocaleProperty("countryDelete.warn"));  
			    getJsonObject().put("title", getText("title.error"));
			} else {
				utilService.removeCountry(country.getName());
				getJsonObject().put("msg", getText("msg.deleted"));  
			    getJsonObject().put("title", getText("title.success"));
			}
			sendAjaxResponse(getJsonObject());
		}

		

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

		return country;

	}



}
