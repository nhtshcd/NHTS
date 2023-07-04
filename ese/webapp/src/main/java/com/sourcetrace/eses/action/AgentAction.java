/*
 * AgentAction.java
 * Copyright (c) 2015-2016, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.action;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.sourcetrace.eses.BreadCrumb;
import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.AgentType;
import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.ContactInfo;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.ESETxn;
import com.sourcetrace.eses.entity.ExporterRegistration;
import com.sourcetrace.eses.entity.Packhouse;
import com.sourcetrace.eses.entity.PasswordHistory;
import com.sourcetrace.eses.entity.PersonalInfo;
import com.sourcetrace.eses.entity.Profile;
import com.sourcetrace.eses.entity.Role;
import com.sourcetrace.eses.entity.User;
import com.sourcetrace.eses.entity.UserActiveAndInActiveHostory;
import com.sourcetrace.eses.service.IReportService;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ICryptoUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class AgentAction.
 */
@Component
@Scope("prototype")
public class AgentAction extends SwitchAction {

	private static final Logger logger = Logger.getLogger(AgentAction.class);
	protected static final String WAREHOUSE_MAPPING = "warehouseMapping";

	private String selectedStatus;
	private String id;
	private String profileId;
	private String dateOfBirth;
	private String selectedIdentity;
	private String selectedSex;
	private String warehouseNames = "";
	private String qrid;
	private String loginUserName;
	private String type;
	private String selectedCooperative;
	private String cooperativeName;
	private String warehouseCooperative;
	private Agent agent;
	private InputStream fileInputStream;
	DateFormat df = new SimpleDateFormat(getESEDateFormat());
	private String selectedAgentType;
	private List<Agent> agents;
	List<String> selectedName = new ArrayList<String>();
	private List<String> availableName = new ArrayList<String>();

	Map<String, String> genderType = new LinkedHashMap<String, String>();
	Map<Integer, String> txnMode = new LinkedHashMap<Integer, String>();
	Map<Integer, String> statuses = new LinkedHashMap<Integer, String>();
	Map<Integer, String> bodStatus = new LinkedHashMap<Integer, String>();
	Map<String, String> identityTypeList = new LinkedHashMap<String, String>();
	@Getter
	@Setter
	private String redirectContent;

	@Autowired
	private IUniqueIDGenerator idGenerator;

	private IReportService reportService;
	@Autowired
	private IUtilService utilService;

	@Autowired
	private ICryptoUtil cryptoUtil;
	private String warehouseName;
	private String selectedRole;

	@Getter
	@Setter
	private UserActiveAndInActiveHostory userEditHistory;

	/**
	 * Gets the gender type.
	 * 
	 * @return the gender type
	 */
	public Map<String, String> getGenderType() {

		return genderType;
	}

	/**
	 * Sets the gender type.
	 * 
	 * @param genderType
	 *            the gender type
	 */
	public void setGenderType(Map<String, String> genderType) {

		this.genderType = genderType;
	}

	/**
	 * Sets the identity type list.
	 * 
	 * @param identityTypeList
	 *            the identity type list
	 */
	public void setIdentityTypeList(Map<String, String> identityTypeList) {

		this.identityTypeList = identityTypeList;
	}

	/**
	 * Gets the txn mode.
	 * 
	 * @return the txn mode
	 */
	public Map<Integer, String> getTxnMode() {
		txnMode.put(ESETxn.ONLINE_MODE, getText("mode1"));
		txnMode.put(ESETxn.OFFLINE_MODE, getText("mode2"));
		txnMode.put(ESETxn.BOTH, getText("mode3"));

		return txnMode;
	}

	/**
	 * Sets the statuses.
	 * 
	 * @param statuses
	 *            the statuses
	 */
	public void setStatuses(Map<Integer, String> statuses) {

		this.statuses = statuses;
	}

	/**
	 * Sets the txn mode.
	 * 
	 * @param txnMode
	 *            the txn mode
	 */
	public void setTxnMode(Map<Integer, String> txnMode) {

		this.txnMode = txnMode;
	}

	/**
	 * Gets the service location service.
	 * 
	 * @return the service location service
	 */
	/**
	 * Sets the service location service.
	 * 
	 * @param serviceLocationService
	 *            the new service location service
	 */

	/**
	 * Gets the selected name.
	 * 
	 * @return the selected name
	 */
	public List<String> getSelectedName() {

		return selectedName;
	}

	/**
	 * Sets the selected name.
	 * 
	 * @param selectedName
	 *            the new selected name
	 */
	public void setSelectedName(List<String> selectedName) {

		this.selectedName = selectedName;
	}

	/**
	 * Gets the agent.
	 * 
	 * @return the agent
	 */

	public Agent getAgent() {

		return agent;
	}

	/**
	 * Sets the agent.
	 * 
	 * @param agent
	 *            the new agent
	 */
	public void setAgent(Agent agent) {

		this.agent = agent;
	}

	/**
	 * Gets the profile id.
	 * 
	 * @return the profile id
	 */
	public String getProfileId() {

		return profileId;
	}

	/**
	 * Sets the profile id.
	 * 
	 * @param profileId
	 *            the new profile id
	 */
	public void setProfileId(String profileId) {

		this.profileId = profileId;
	}

	/**
	 * Gets the date of birth.
	 * 
	 * @return the date of birth
	 */
	public String getDateOfBirth() {

		return dateOfBirth;
	}

	/**
	 * Sets the date of birth.
	 * 
	 * @param dateOfBirth
	 *            the new date of birth
	 */
	public void setDateOfBirth(String dateOfBirth) {

		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Gets the df.
	 * 
	 * @return the df
	 */
	public DateFormat getDf() {

		return df;
	}

	/**
	 * Sets the df.
	 * 
	 * @param df
	 *            the new df
	 */
	public void setDf(DateFormat df) {

		this.df = df;
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
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {

		return id;
	}

	/**
	 * Gets the report service.
	 * 
	 * @return the report service
	 */
	public IReportService getReportService() {

		return reportService;
	}

	/**
	 * Sets the report service.
	 * 
	 * @param reportService
	 *            the report service
	 * @see com.sourcetrace.esesw.view.SwitchAction#setReportService(com.ese.service.report.IReportService)
	 */
	@Override
	public void setReportService(IReportService reportService) {

		this.reportService = reportService;
	}

	/**
	 * Gets the service point service.
	 * 
	 * @return the service point service
	 */

	/**
	 * Gets the service point service.
	 * 
	 * @return the service point service
	 */
	/*
	 * public IServicePointService getServicePointService() {
	 * 
	 * return servicePointService; }
	 */
	/**
	 * Sets the service point service.
	 * 
	 * @param servicePointService
	 *            the new service point service
	 */
	/*
	 * public void setServicePointService(IServicePointService
	 * servicePointService) {
	 * 
	 * this.servicePointService = servicePointService; }
	 */

	/**
	 * Gets the selected sex.
	 * 
	 * @return the selected sex
	 */
	public String getSelectedSex() {

		return selectedSex;
	}

	/**
	 * Sets the selected sex.
	 * 
	 * @param selectedSex
	 *            the new selected sex
	 */
	public void setSelectedSex(String selectedSex) {

		this.selectedSex = selectedSex;
	}

	/**
	 * Sets the selected status.
	 * 
	 * @param selectedStatus
	 *            the new selected status
	 */
	public void setSelectedStatus(String selectedStatus) {

		this.selectedStatus = selectedStatus;
	}

	/**
	 * Gets the selected status.
	 * 
	 * @return the selected status
	 */
	public String getSelectedStatus() {

		return selectedStatus;
	}

	/**
	 * Sets the id generator.
	 * 
	 * @param idGenerator
	 *            the new id generator
	 */
	public void setIdGenerator(IUniqueIDGenerator idGenerator) {

		this.idGenerator = idGenerator;
	}

	/**
	 * Gets the id generator.
	 * 
	 * @return the id generator
	 */
	public IUniqueIDGenerator getIdGenerator() {

		return idGenerator;
	}

	/**
	 * Gets the identity type list.
	 * 
	 * @return the identity type list
	 */
	public Map<String, String> getIdentityTypeList() {

		return identityTypeList;
	}

	/**
	 * Gets the selected identity.
	 * 
	 * @return the selected identity
	 */
	public String getSelectedIdentity() {

		return selectedIdentity;
	}

	/**
	 * Sets the selected identity.
	 * 
	 * @param selectedIdentity
	 *            the new selected identity
	 */
	public void setSelectedIdentity(String selectedIdentity) {

		this.selectedIdentity = selectedIdentity;
	}

	/**
	 * Gets the data.
	 * 
	 * @return the data
	 * @see com.sourcetrace.esesw.view.SwitchValidatorAction#getData()
	 */

	/**
	 * Gets the statuses.
	 * 
	 * @return the statuses
	 */
	public Map<Integer, String> getStatuses() {
		// To get Txn Mode

		// To get Agent Status
		statuses.put(Profile.ACTIVE, getText("agent1"));
		statuses.put(Profile.INACTIVE, getText("agent0"));
		statuses.put(Profile.LOCKED, getText("agent4"));

		return statuses;
	}

	/**
	 * Data.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 * @see com.sourcetrace.esesw.view.SwitchAction#data()
	 */
	@Override
	public String data() throws Exception {

		Map<String, String> searchRecord = getDataTableJQGridRequestParam(); // get
																				// the
		// search
		// parameter
		// with
		// value

		Agent filter = new Agent();

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

		if (!StringUtil.isEmpty(searchRecord.get("profileId"))) {
			filter.setProfileId(searchRecord.get("profileId").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("pI.firstName"))
				|| !StringUtil.isEmpty(searchRecord.get("pI.lastName"))) {
			PersonalInfo personalInfo = new PersonalInfo();
			if (!StringUtil.isEmpty(searchRecord.get("pI.firstName")))
				personalInfo.setFirstName(searchRecord.get("pI.firstName").trim());
			if (!StringUtil.isEmpty(searchRecord.get("pI.lastName")))
				personalInfo.setLastName(searchRecord.get("pI.lastName").trim());
			filter.setPersonalInfo(personalInfo);
		}

		filter.setBodStatus(-1);
		filter.setStatus(-1);
		if (!StringUtil.isEmpty(searchRecord.get("bodStatus"))) {
			filter.setBodStatus(Integer.parseInt(searchRecord.get("bodStatus")));
		}
		if (!StringUtil.isEmpty(searchRecord.get("status"))) {
			filter.setStatus(Integer.parseInt(searchRecord.get("status")));
		}

		if (!StringUtil.isEmpty(searchRecord.get("cI.mobileNumber"))) {
			ContactInfo contactInfo = new ContactInfo();
			contactInfo.setMobileNumbere(searchRecord.get("cI.mobileNumbere").trim());
			filter.setContInfo(contactInfo);
		}
		if (getLoggedInDealer() > 0) {
			ExporterRegistration ex = new ExporterRegistration();
			ex.setId(getLoggedInDealer());

			filter.setExporter(ex);
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
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(Object obj) {

		Agent agent = (Agent) obj;
		JSONObject jsonObject = new JSONObject();
		// JSONArray rows = new JSONArray();
		JSONObject objDt = new JSONObject();

		if ((getIsMultiBranch().equalsIgnoreCase("1")
				&& (getIsParentBranch().equals("1") || StringUtil.isEmpty(branchIdValue)))) {

			if (StringUtil.isEmpty(branchIdValue)) {
				objDt.put("branch",
						!StringUtil.isEmpty(getBranchesMap().get(getParentBranchMap().get(agent.getBranchId())))
								? getBranchesMap().get(getParentBranchMap().get(agent.getBranchId()))
								: getBranchesMap().get(agent.getBranchId()));
			}
			objDt.put("branch", getBranchesMap().get(agent.getBranchId()));

		} else {
			if (StringUtil.isEmpty(branchIdValue)) {
				objDt.put("branch", branchesMap.get(agent.getBranchId()));
			}
		}

		objDt.put("profileId", "<font color=\"#0000FF\" style=\"cursor:pointer;\">" + agent.getProfileId() + "</font>");
		objDt.put("firstName", agent.getPersonalInfo().getFirstName());
		objDt.put("lastName", agent.getPersonalInfo().getLastName());
		objDt.put("mobileNumber",
				agent.getContInfo().getMobileNumbere() == null ? "" : agent.getContInfo().getMobileNumbere());

		objDt.put("bodStatus", getText("bodStatus" + agent.getBodStatus()));
		objDt.put("status", getText("status" + agent.getStatus()));
		jsonObject.put("DT_RowId", agent.getId());
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
		String result = "";
		String cc = "";
		String apkUrl = "";
		if (agent == null) {
			command = CREATE;
			request.setAttribute(HEADING, getText("Agentcreate.page" + type));
			agent = new Agent();
			agent.setStatus(Profile.ACTIVE);
			if (getLoggedInDealer() > 0) {
				ExporterRegistration ex = utilService.findExportRegById(Long.valueOf(getLoggedInDealer()));
				agent.setExporter(ex);
			}
			return INPUT;
		} else {
			agent.setProfileId(agent.getProfileId() == null ? "" : agent.getProfileId().toLowerCase());
			agent.getPersonalInfo().setIdentityType(agent.getPersonalInfo().getIdentityType());
			if (Profile.ACTIVE == agent.getStatus()) {
				if (StringUtil.isEmpty(agent.getPassword())) {
					addActionError(getText("empty.password"));
					request.setAttribute(HEADING, getText("Agentcreate.page" + type));
					return INPUT;
				} else {
					String oldPasswordToken = cryptoUtil
							.encrypt(StringUtil.getMulipleOfEight(agent.getProfileId() + agent.getPassword()));
					agent.setPassword(oldPasswordToken);
				}
			}

			if (!ObjectUtil.isEmpty(agent.getAgentType().getId()) && agent.getAgentType().getId() > 0) {
				AgentType agentType = utilService.findAgentTypeById(agent.getAgentType().getId());
				agent.setAgentType(agentType);
			}
			agent.getPersonalInfo().setDateOfBirth(dateOfBirth != null && !StringUtil.isEmpty(dateOfBirth)
					? DateUtil.convertStringToDate(dateOfBirth, getGeneralDateFormat()) : null);
			agent.getPersonalInfo().setSex(agent.getPersonalInfo().getSex());
			agent.getContInfo().setMobileNumbere(agent.getContInfo().getMobileNumbere());
			agent.getContInfo().setEmail(agent.getContInfo().getEmail());
			// agent.setStatus(Profile.INACTIVE);
			agent.setBodStatus(ESETxn.EOD);
			agent.setInternalIdSequence(idGenerator.createAgentInternalIdSequence());
			agent.setBranchId(getBranchId());
			agent.setCreatedDate(new Date());
			agent.setCreatedUser(getUsername());
			// agent.setUpdatedDate(new Date());
			// agent.setUpdatedUser(getUsername());
			// saving exporter only when packhouse is null for Audit issues.
			if (agent.getPackhouse() != null && agent.getPackhouse().getId() != null
					&& agent.getPackhouse().getId() > 0) {
				Packhouse ware = utilService.findWarehouseById(Long.valueOf(agent.getPackhouse().getId()));
				agent.setPackhouse(ware);
				agent.setExporter(null);
			} else if (agent.getExporter() != null && agent.getExporter().getId() > 0) {
				ExporterRegistration ex = utilService.findExportRegById(Long.valueOf(agent.getExporter().getId()));
				agent.setExporter(ex);
				agent.setPackhouse(null);
			}
			/*
			 * Role
			 * r=utilService.findRole(Long.valueOf(agent.getRole().getId()));
			 * User u=new User(); u.setUsername(agent.getProfileId());
			 * u.setPassword(agent.getPassword()); u.setLoginStatus(0); if
			 * (!ObjectUtil.isEmpty(agent.getAgentType())) { Role
			 * r1=utilService.findRoleByName(agent.getAgentType().getName());
			 * u.setRole(r1); u.setUserType(r1.getType()); } u.setEnabled(true);
			 * u.setLanguage(getLoggedInUserLanguage());
			 * u.setBranchId(getBranchId()); u.setStatus(0);
			 * u.setCreatedDate(new Date()); u.setDataId(0l);
			 * u.setPersInfo(agent.getPersonalInfo());
			 * u.getPersInfo().setLastName(agent.getPersonalInfo().getLastName()
			 * ); u.getPersInfo().setDateOfBirth(dateOfBirth!=null &&
			 * !StringUtil.isEmpty(dateOfBirth)?DateUtil.convertStringToDate(
			 * dateOfBirth,getGeneralDateFormat()):null);
			 * u.getPersInfo().setSex(agent.getPersonalInfo().getSex());
			 * u.setContInfo(agent.getContInfo());
			 * u.getContInfo().setPhoneNumber(agent.getContInfo().getPhoneNumber
			 * ()); u.getContInfo().setEmail(agent.getContInfo().getEmail());
			 * u.getContInfo().setAddress1(agent.getContInfo().getAddress1());
			 * utilService.addUser(u);
			 */
			agent.setIsActive(1l);
			agent.setEnrollId("0");
			utilService.createAgent(agent);

			Agent aUser = utilService.findAgentByProfileAndBranchId(agent.getProfileId(), agent.getBranchId());
			PasswordHistory ph = new PasswordHistory();
			ph.setBranchId(aUser.getBranchId());
			ph.setCreatedDate(new Date());
			ph.setType(String.valueOf(PasswordHistory.Type.MOBILE_USER.ordinal()));
			ph.setPassword(aUser.getPassword());
			ph.setReferenceId(aUser.getId());
			utilService.save(ph);

			if (userEditHistory == null) {
				userEditHistory = new UserActiveAndInActiveHostory();
				userEditHistory.setBranchId(getBranchId());
				userEditHistory.setCreatedDate(new Date());
				userEditHistory.setCreatedUser(getUsername());
				userEditHistory.setAgroChDealer(agent.getPackhouse() != null
						? agent.getPackhouse().getExporter().getId() : agent.getExporter().getId());
				userEditHistory.setDate(new Date());
				DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
				String dateString = dateFormat.format(new Date()).toString();
				userEditHistory.setTime(dateString);
				userEditHistory.setUserName(agent.getProfileId());
				userEditHistory.setLoggedUser(getUsername());
				userEditHistory.setActivity(5);
				userEditHistory.setType(2);
				utilService.save(userEditHistory);
			}

			return REDIRECT;
		}
	}

	@Getter
	@Setter
	List<Object[]> ex;

	/**
	 * Detail.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String detail() throws Exception {

		if (id != null && !id.equals(EMPTY)) {
			agent = utilService.findAgent(Long.valueOf(id));

			if (!StringUtil.isEmpty(agent.getPersonalInfo().getDateOfBirth())) {
				setDateOfBirth(df.format(agent.getPersonalInfo().getDateOfBirth()));
			}
			setCurrentPage(getCurrentPage());
			if (agent == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}

			User user = utilService.findUserByUserName(getUsername());

			setLoginUserName(request.getSession().getAttribute("user").toString());
			// ex =
			// utilService.getAuditRecords("com.sourcetrace.eses.entity.Agent",
			// agent.getId());

			command = DETAIL;
			request.setAttribute(HEADING, getText(command + type));

		} else {
			request.setAttribute(HEADING, getText(LIST + type));
			return REDIRECT;
		}
		return command;
	}

	/**
	 * Update.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String update() throws Exception {

		int tmpAccStat = -1; // temp storage of account status. -1 is initial
								// value.
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		if (id != null && !id.equals("")) {
			agent = utilService.findAgent(Long.valueOf(id));
			setCurrentPage(getCurrentPage());
			if (agent == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}

			if (agent.getStatus() == 0) {
				setSelectedStatus("0");
			} else {
				setSelectedStatus("1");
			}
			if (!StringUtil.isEmpty(agent.getPersonalInfo().getDateOfBirth())) {
				// dateOfBirth =
				// df.format(agent.getPersonalInfo().getDateOfBirth());
				dateOfBirth = DateUtil.convertDateToString(agent.getPersonalInfo().getDateOfBirth(),
						getGeneralDateFormat());
			}
			if (!ObjectUtil.isEmpty(agent.getAgentType())) {
				setSelectedRole(agent.getAgentType().getId().toString());
			}

			if (!StringUtil.isEmpty(agent.getPassword())) {
				String plainText = cryptoUtil.decrypt(agent.getPassword());
				if (!StringUtil.isEmpty(plainText)) {
					plainText = plainText.trim();
					plainText = plainText.substring(agent.getProfileId().length(), plainText.length());
					agent.setPassword(plainText.trim());
					agent.setConfirmPassword(plainText.trim());
				} else {
					agent.setPassword(null);
				}
			} else {
				agent.setPassword(null);
			}
			if (agent.getPackhouse() != null && agent.getPackhouse().getExporter() != null) {
				agent.setExporter(agent.getPackhouse().getExporter());
			}
			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getText("Agentupdate.page" + type));
		} else {

			if (agent != null) {
				if (agent.getBodStatus() == ESETxn.BOD) {
					addActionError(getText("cannotUpdateAgent"));
					request.setAttribute(HEADING, getText("Agentupdate.page" + type));
					return INPUT;
				}

				Agent temp = utilService.findAgent(agent.getId());
				if (temp == null) {
					addActionError(NO_RECORD);
					return REDIRECT;
				}

				temp.setProfileId(agent.getProfileId());
				temp.getContInfo().setAddress1(agent.getContInfo().getAddress1());
				temp.getContInfo().setAddress2(agent.getContInfo().getAddress2());
				temp.getContInfo().setZipCode(agent.getContInfo().getZipCode());
				temp.getContInfo().setPhoneNumber(agent.getContInfo().getPhoneNumber());
				temp.getContInfo().setMobileNumbere(agent.getContInfo().getMobileNumbere());
				temp.getContInfo().setEmail(agent.getContInfo().getEmail());
				temp.setAccountBalance(agent.getAccountBalance());
				temp.setRevisionNumber(DateUtil.getRevisionNumber());

				temp.getPersonalInfo().setFirstName(agent.getPersonalInfo().getFirstName());
				temp.getPersonalInfo().setMiddleName(agent.getPersonalInfo().getMiddleName());
				temp.getPersonalInfo().setLastName(agent.getPersonalInfo().getLastName());
				temp.getPersonalInfo().setStatus(agent.getBodStatus());

				temp.getPersonalInfo().setIdentityType(agent.getPersonalInfo().getIdentityType());
				temp.getPersonalInfo().setIdentityNumber(agent.getPersonalInfo().getIdentityNumber());
				temp.getPersonalInfo().setSex(agent.getPersonalInfo().getSex());
				temp.getPersonalInfo()
						.setDateOfBirth(dateOfBirth != null && !StringUtil.isEmpty(dateOfBirth)
								? DateUtil.convertStringToDate(dateOfBirth, getGeneralDateFormat())
								: temp.getPersonalInfo().getDateOfBirth());
				temp.setStatus(agent.getStatus());
				temp.setUpdatedDate(new Date());
				temp.setUpdatedUser(getUsername());

				if (Profile.ACTIVE == agent.getStatus()) {
					if (agent.isChangePassword()) {
						if (StringUtil.isEmpty(agent.getPassword())) {
							addActionError(getText("empty.password"));
							request.setAttribute(HEADING, getText("Agentupdate.page" + type));
							return INPUT;
						} else {
							String oldPasswordToken = cryptoUtil
									.encrypt(StringUtil.getMulipleOfEight(agent.getProfileId() + agent.getPassword()));
							temp.setPassword(oldPasswordToken);
							PasswordHistory ph = new PasswordHistory();
							ph.setBranchId(temp.getBranchId());
							ph.setCreatedDate(new Date());
							ph.setType(String.valueOf(PasswordHistory.Type.MOBILE_USER.ordinal()));
							ph.setPassword(oldPasswordToken);
							ph.setReferenceId(temp.getId());
							utilService.save(ph);
						}
					}
				}

				if (agent.getPackhouse() != null && agent.getPackhouse().getId() != null) {
					Packhouse ware = utilService.findWarehouseById(Long.valueOf(agent.getPackhouse().getId()));
					temp.setPackhouse(ware);
					temp.setExporter(null);
				} else if (agent.getExporter() != null && agent.getExporter().getId() > 0) {
					ExporterRegistration ex = utilService.findExportRegById(Long.valueOf(agent.getExporter().getId()));
					temp.setExporter(ex);
					temp.setPackhouse(null);
				}
				if (!ObjectUtil.isEmpty(agent.getAgentType()) && agent.getAgentType().getId() > 0) {
					AgentType agentType = utilService.findAgentTypeById(agent.getAgentType().getId());
					temp.setAgentType(agentType);
				}

				setCurrentPage(getCurrentPage());
				utilService.editAgentProfile(temp);

				if (userEditHistory == null) {
					userEditHistory = new UserActiveAndInActiveHostory();
					userEditHistory.setBranchId(getBranchId());
					userEditHistory.setCreatedDate(new Date());
					userEditHistory.setCreatedUser(getUsername());
					userEditHistory.setAgroChDealer(temp.getPackhouse() != null
							? temp.getPackhouse().getExporter().getId() : temp.getExporter().getId());

					userEditHistory.setDate(new Date());
					DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
					String dateString = dateFormat.format(new Date()).toString();
					userEditHistory.setTime(dateString);
					userEditHistory.setUserName(temp.getProfileId());
					userEditHistory.setLoggedUser(getUsername());
					userEditHistory.setActivity(temp.getStatus());
					userEditHistory.setType(2);
					utilService.save(userEditHistory);
				}

			}

			request.setAttribute(HEADING, getText(LIST + type));
			return REDIRECT;
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
	public String delete() throws Exception {

		setCurrentPage(getCurrentPage());
		if (this.getId() != null && !(this.getId().equals(EMPTY))) {
			agent = utilService.findAgent(Long.valueOf(id));
			setCurrentPage(getCurrentPage());
			if (agent == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			if (agent != null) {
				boolean agentId = utilService.findAgentIdFromEseTxn(agent.getProfileId());
				if (agentId) {
					addActionError(getText("cannotDeleteAgentHasTxn"));
					request.setAttribute(HEADING, getText(DETAIL));
					return REDIRECT;
				} else {
					String errorMsg = null;
					if ("fieldStaff".equalsIgnoreCase(type)) {
						boolean agentDeviceMap = utilService.findAgentIdFromDevice(agent.getId());
						if (agentDeviceMap) {
							errorMsg = "cannotDeleteAgentHasDeviceMapping";
							detail();
						}
					}

					if (StringUtil.isEmpty(errorMsg)) {
						agent.setStatus(2);
						utilService.update(agent);
					}

					if (!StringUtil.isEmpty(errorMsg)) {
						addActionError(getText(errorMsg));
						request.setAttribute(HEADING, getText(DETAIL));
						return REDIRECT;
					}
				}
			}
		}
		return REDIRECT;
	}

	/**
	 * Process stock transfer.
	 * 
	 * @return the string
	 */
	/**
	 * Execute.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {

		if (id != null && !id.equals("")) {
			agent = utilService.findAgent(id);

			id = null;
			command = "update";
			request.setAttribute("heading", getText("update" + type));
		}
		return super.execute();
	}

	/**
	 * Sets the file input stream.
	 * 
	 * @param fileInputStream
	 *            the new file input stream
	 */
	public void setFileInputStream(InputStream fileInputStream) {

		this.fileInputStream = fileInputStream;
	}

	/**
	 * Gets the file input stream.
	 * 
	 * @return the file input stream
	 */
	public InputStream getFileInputStream() {

		return fileInputStream;
	}

	/**
	 * Gets the code name.
	 * 
	 * @return the code name
	 */
	public String getCodeName() {

		return getQrid();
	}

	/**
	 * Sets the qrid.
	 * 
	 * @param qrid
	 *            the new qrid
	 */
	public void setQrid(String qrid) {

		this.qrid = qrid;
	}

	/**
	 * Gets the qrid.
	 * 
	 * @return the qrid
	 */
	public String getQrid() {

		return qrid;
	}

	/**
	 * Gets the warehouse names.
	 * 
	 * @return the warehouse names
	 */
	public String getWarehouseNames() {

		return warehouseNames;
	}

	/**
	 * Sets the warehouse names.
	 * 
	 * @param warehouseNames
	 *            the new warehouse names
	 */
	public void setWarehouseNames(String warehouseNames) {

		this.warehouseNames = warehouseNames;
	}

	public void setAgents(List<Agent> agents) {

		this.agents = agents;
	}

	/**
	 * Sets the login user name.
	 * 
	 * @param loginUserName
	 *            the new login user name
	 */
	public void setLoginUserName(String loginUserName) {

		this.loginUserName = loginUserName;
	}

	/**
	 * Gets the login user name.
	 * 
	 * @return the login user name
	 */
	public String getLoginUserName() {

		return loginUserName;
	}

	/**
	 * Gets the agents.
	 * 
	 * @return the agents
	 */
	public List<Agent> getAgents() {

		return agents;
	}

	/**
	 * Sets the process stock transfer.
	 * 
	 * @param processStockTransfer
	 *            the new process stock transfer
	 */

	/**
	 * Gets the available name.
	 * 
	 * @return the available name
	 */
	public List<String> getAvailableName() {

		return availableName;
	}

	/**
	 * Sets the available name.
	 * 
	 * @param availableName
	 *            the new available name
	 */
	public void setAvailableName(List<String> availableName) {

		this.availableName = availableName;
	}

	/**
	 * Reset.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String reset() throws Exception {

		if (this.getId() != null && !(this.getId().equals(EMPTY))) {
			agent = utilService.findAgent(Long.valueOf(id));
			setCurrentPage(getCurrentPage());
			if (agent == null) {
				addActionError(NO_RECORD);
				return null;
			}
			int txnStatus = 0;
			String text = "success.reset.BOD";
			/*
			 * if (agent.getBodStatus() == ESETxn.EOD) { txnStatus = 1; text =
			 * "success.reset.EOD"; }
			 */
			utilService.updateAgentBODStatus(agent.getProfileId(), txnStatus);
			loginUserName = request.getSession().getAttribute("user").toString();
			agent.setBodStatus(txnStatus);
			addActionMessage(getText(text));
			request.setAttribute(HEADING, getText(DETAIL + type));
			detail();
		}
		return DETAIL;
	}

	/**
	 * Update id seq.
	 * 
	 * @return the string
	 */
	public String updateIdSeq() {

		utilService.updateIdSeq();
		return DETAIL;
	}

	/**
	 * Gets the agent types.
	 * 
	 * @return the agent types
	 */
	public List<AgentType> getAgentTypes() {

		return utilService.listAgentType();
	}

	public Map<String, String> getAgentType() {
		Map<String, String> agentType = new LinkedHashMap<String, String>();
		List<AgentType> agent = utilService.listAgentType();

		if (agent != null) {
			for (AgentType agentT : agent) {
				agentType.put(String.valueOf(agentT.getId()), String.valueOf(agentT.getName()));

			}
		}
		return agentType;
	}

	public Map<String, String> getWarehouseMap() {
		Map<String, String> warehouseMap = new LinkedHashMap<String, String>();
		List<Packhouse> wareh = new ArrayList<>();
		if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
			wareh = (List<Packhouse>) farmerService.listObjectById(
					"FROM Packhouse pv where pv.exporter.id=?  ORDER BY pv.name", new Object[] { getLoggedInDealer() });
		}
		if (wareh != null) {
			for (Packhouse ware : wareh) {
				warehouseMap.put(String.valueOf(ware.getId()), String.valueOf(ware.getName()));

			}
		}
		return warehouseMap;
	}

	public void populatePackhouse() throws Exception {

		JSONArray farmerArr = new JSONArray();
		List<Packhouse> wareh = new ArrayList<>();
		if (selectedAgentType != null && !StringUtil.isEmpty(selectedAgentType)) {
			/*
			 * wareh = (List<Packhouse>) farmerService.listObjectById(
			 * "FROM Packhouse pv where pv.exporter.id=?  ORDER BY pv.name", new
			 * Object[] { Long.valueOf(selectedAgentType) });
			 */
			wareh = (List<Packhouse>) farmerService.listObjectById(
					"FROM Packhouse pv where pv.exporter.id=? and pv.status!=2 ORDER BY pv.name",
					new Object[] { Long.valueOf(selectedAgentType) });
		}
		if (wareh != null && !ObjectUtil.isEmpty(wareh)) {
			wareh.stream().distinct().forEach(f -> {
				farmerArr.add(getJSONObject(String.valueOf(f.getId()), String.valueOf(f.getName())));
			});
		}

		sendAjaxResponse(farmerArr);
	}

	/**
	 * Gets the filter agent types.
	 * 
	 * @return the filter agent types
	 */
	public String getFilterAgentTypes() {

		List<AgentType> agentTypes = getAgentTypes();
		String filterAgentTypes = "0:All;";
		if (!ObjectUtil.isEmpty(agentTypes)) {
			for (AgentType agentType : agentTypes) {
				filterAgentTypes += agentType.getId() + ":" + agentType.getName() + ";";
			}
		}
		return filterAgentTypes.substring(0, filterAgentTypes.length() - 1);
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {

		this.type = type;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public String getType() {

		return type;
	}

	/**
	 * Sets the selected cooperative.
	 * 
	 * @param selectedCooperative
	 *            the new selected cooperative
	 */
	public void setSelectedCooperative(String selectedCooperative) {

		this.selectedCooperative = selectedCooperative;
	}

	/**
	 * Gets the selected cooperative.
	 * 
	 * @return the selected cooperative
	 */
	public String getSelectedCooperative() {

		return selectedCooperative;
	}

	/**
	 * Gets the cooperative list.
	 * 
	 * @return the cooperative list
	 */

	/**
	 * Prepare.
	 * 
	 * @throws Exception
	 *             the exception
	 * @see com.sourcetrace.esesw.view.SwitchAction#prepare()
	 */
	public void prepare() throws Exception {

		type = request.getParameter("type");
		if (!StringUtil.isEmpty(type)) {
			String actionClassName = ActionContext.getContext().getActionInvocation().getAction().getClass()
					.getSimpleName();
			String content = getLocaleProperty(actionClassName + "." + BreadCrumb.BREADCRUMB + type);
			if (StringUtil.isEmpty(content)
					|| (content.equalsIgnoreCase(actionClassName + "." + BreadCrumb.BREADCRUMB + type))) {
				content = super.getText(BreadCrumb.BREADCRUMB + type, "");
			}
			request.setAttribute(BreadCrumb.BREADCRUMB, BreadCrumb.getBreadCrumb(content));

		} else {
			String actionClassName = ActionContext.getContext().getActionInvocation().getAction().getClass()
					.getSimpleName();
			String content = getLocaleProperty(actionClassName + "." + BreadCrumb.BREADCRUMB);
			if (StringUtil.isEmpty(content)
					|| (content.equalsIgnoreCase(actionClassName + "." + BreadCrumb.BREADCRUMB))) {
				content = super.getText(BreadCrumb.BREADCRUMB, "");
			} else {

			}
			request.setAttribute(BreadCrumb.BREADCRUMB, BreadCrumb.getBreadCrumb(content));
		}
	}

	/**
	 * Gets the cooperative name.
	 * 
	 * @return the cooperative name
	 */
	public String getCooperativeName() {

		return cooperativeName;
	}

	/**
	 * Sets the cooperative name.
	 * 
	 * @param cooperativeName
	 *            the new cooperative name
	 */
	public void setCooperativeName(String cooperativeName) {

		this.cooperativeName = cooperativeName;
	}

	/**
	 * Sets the warehouse cooperative.
	 * 
	 * @param warehouseCooperative
	 *            the new warehouse cooperative
	 */
	public void setWarehouseCooperative(String warehouseCooperative) {

		this.warehouseCooperative = warehouseCooperative;
	}

	/**
	 * Gets the warehouse cooperative.
	 * 
	 * @return the warehouse cooperative
	 */
	public String getWarehouseCooperative() {

		return warehouseCooperative;
	}

	public Map<Integer, String> getBodStatus() {
		// To get BOD Status
		bodStatus.put(Profile.ONLINE, getText("bodStatus1"));
		bodStatus.put(Profile.OFFLINE, getText("bodStatus0"));
		return bodStatus;
	}

	public void setBodStatus(Map<Integer, String> bodStatus) {

		this.bodStatus = bodStatus;
	}

	/**
	 * List.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 * @see com.sourcetrace.esesw.view.SwitchAction#list()
	 */
	public String list() throws Exception {

		if (getCurrentPage() != null) {
			setCurrentPage(getCurrentPage());
		}
		request.setAttribute(HEADING, getText(LIST + type));
		return LIST;
	}

	public IUtilService getUtilService() {
		return utilService;
	}

	public void setUtilService(IUtilService utilService) {
		this.utilService = utilService;
	}

	public ICryptoUtil getCryptoUtil() {
		return cryptoUtil;
	}

	public void setCryptoUtil(ICryptoUtil cryptoUtil) {
		this.cryptoUtil = cryptoUtil;
	}

	public int getStatusDefaultValue() {

		return (ObjectUtil.isEmpty(this.agent) ? Profile.INACTIVE : this.agent.getStatus());
	}

	public String getSelectedAgentType() {
		return selectedAgentType;
	}

	public void setSelectedAgentType(String selectedAgentType) {
		this.selectedAgentType = selectedAgentType;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	List<Role> roles;

	public List<Role> getRoles() {

		roles = new ArrayList<Role>();

		/*
		 * if(subBranchId_F==null || StringUtil.isEmpty(subBranchId_F)){ if
		 * ((ObjectUtil.isEmpty(branchId_F) || branchId_F.equals("-1")) &&
		 * StringUtil.isEmpty(getBranchId())) { roles = new ArrayList<Role>();
		 * roles = utilService.listRoles(); } else if
		 * ((!ObjectUtil.isEmpty(branchId_F) && !branchId_F.equals("-1"))) { if
		 * (!ObjectUtil.isEmpty(agent)) { roles =
		 * utilService.listRolesByTypeAndBranchId( branchId_F); } } else { roles
		 * = utilService.listRolesByTypeAndBranchId(getBranchId()); } }else{
		 */

		if (!ObjectUtil.isEmpty(agent)) {
			roles = utilService.listRolesByTypeAndBranchId(getBranchId());
		}

		for (Role role : roles) {
			String name = role.getName();
			role.setName(getText(name));
		}
		return roles;
	}

	public String getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(String selectedRole) {
		this.selectedRole = selectedRole;
	}

	public void populateValidate() {
		Map<String, String> errorCodes = new LinkedHashMap<String, String>();
		agent.setProfileId(agent.getProfileId() == null ? "" : agent.getProfileId().toLowerCase());
		if (!StringUtil.isEmpty(agent.getProfileId())) {
			Agent eAgent = utilService.findAgentByAgentId(agent.getProfileId());
			if (eAgent != null && !eAgent.getId().equals(agent.getId())
					&& eAgent.getProfileId().equalsIgnoreCase(agent.getProfileId()) && eAgent.getStatus() != 2) {
				errorCodes.put("profileId", "unique.agentId");
			}
		}

		if (ObjectUtil.isEmpty(agent.getAgentType()) || agent.getAgentType().getId() == null
				|| agent.getAgentType().getId() <= 0) {
			errorCodes.put("agentType", "empty.mobileUserType");
		} else if (agent.getAgentType().getId() != 1 && (agent.getPackhouse() == null
				|| agent.getPackhouse().getId() == null || agent.getPackhouse().getId() <= 0)) {
			errorCodes.put("agentType", "empty.packhouse");
		}

		if (agent.getPersonalInfo() != null && agent.getPersonalInfo().getDateOfBirth() != null) {
			Date toDay = new Date();
			Date SelectedDate = agent.getPersonalInfo().getDateOfBirth();
			if (SelectedDate != null && toDay.before(SelectedDate)) {
				errorCodes.put("dateofbirth", "invalid.dateofbirth");
			}
		}

		if (agent.getStatus() == null) {
			errorCodes.put("agent.status.empty", "agent.status.empty");
		}

		if (agent.isChangePassword()) {

			// String PASSWORD_PATTERN =
			// "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{%%min%%,10000000}$";
			String PASSWORD_PATTERN = "^(?=.*).{%%min%%,10000000}$";
			ESESystem es = utilService.findPrefernceByOrganisationId(getBranchId());
			PASSWORD_PATTERN = PASSWORD_PATTERN.replace("%%min%%",
					es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString());

			Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

			if (StringUtil.isEmpty(agent.getPassword()) || StringUtil.isEmpty(agent.getConfirmPassword())) {
				errorCodes.put("agent.password", "password.missing");
			} else if (!agent.getPassword().equals(agent.getConfirmPassword())) {
				errorCodes.put("agent.password", "password.missmatch");
			}
			if (agent.getId() != null) {
				List<String> pwList = utilService.listPasswordsByTypeAndRefId(
						String.valueOf(PasswordHistory.Type.MOBILE_USER.ordinal()), agent.getId());
				if (pwList != null) {
					pwList.stream().forEach(uu -> {
						String plainText = cryptoUtil.decrypt(uu);
						if (!StringUtil.isEmpty(plainText)) {
							plainText = plainText.trim();
							plainText = plainText.substring(agent.getProfileId().length(), plainText.length()).trim();
							if (plainText.equals(agent.getPassword())) {
								errorCodes.put("repeated.password", "repeated.password");
							}
						}
					});

				}
			}

			Matcher matcher = pattern.matcher(agent.getPassword());
			if (!matcher.matches()) {
				String mss = utilService.findLocaleProperty("agent.mishmatchbyregex", "en");
				mss = mss.replace("[min]",
						String.valueOf(es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString()));
				errorCodes.put(mss, mss);
			} else if (agent.getPassword() != null && !StringUtil.isEmpty(agent.getPassword())) {
				if (agent.getPersonalInfo() != null && agent.getPersonalInfo().getFirstName() != null
						&& !StringUtil.isEmpty(agent.getPersonalInfo().getFirstName()) && agent.getPassword()
								.toLowerCase().contains(agent.getPersonalInfo().getFirstName().toLowerCase())) {
					errorCodes.put("agent.namecontains", "agent.namecontains");
				}

				if (agent.getPersonalInfo() != null && agent.getPersonalInfo().getLastName() != null
						&& !StringUtil.isEmpty(agent.getPersonalInfo().getLastName()) && agent.getPassword()
								.toLowerCase().contains(agent.getPersonalInfo().getLastName().toLowerCase())) {
					errorCodes.put("agent.namecontains", "agent.namecontains");
				}

				if (agent.getProfileId() != null && agent.getProfileId() != null
						&& agent.getPassword().toLowerCase().contains(agent.getProfileId().toLowerCase())) {
					errorCodes.put("agent.namecontains", "agent.namecontains");
				}
			}

		}

		if (ObjectUtil.isEmpty(agent.getExporter()) || StringUtil.isEmpty(agent.getExporter().getId())) {
			errorCodes.put("empty.exporter", "empty.exporter");
		}

		if (!StringUtil.isEmpty(agent.getProfileId())) {
			if (!ValidationUtil.isPatternMaches(agent.getProfileId(), ValidationUtil.ALPHANUMERIC_PATTERN)) {
				errorCodes.put("pattern.profileId", "pattern.profileId");
			}
		} else {
			errorCodes.put("empty.profileId", "empty.profileId");
		}

		if (!StringUtil.isEmpty(agent.getPersonalInfo().getFirstName())) {
			if (!ValidationUtil.isPatternMaches(agent.getPersonalInfo().getFirstName(),
					ValidationUtil.ALPHANUMERIC_PATTERN)) {
				errorCodes.put("pattern.firstName", "pattern.firstName");
			}
		} else {
			errorCodes.put("empty.firstName", "empty.firstName");
		}
		if (!StringUtil.isEmpty(agent.getPersonalInfo().getFirstName())) {
			if (!ValidationUtil.isPatternMaches(agent.getPersonalInfo().getFirstName(),
					ValidationUtil.ALPHANUMERIC_PATTERN)) {
				errorCodes.put("pattern.lastName", "pattern.lastName");
			}
		}
		if (!StringUtil.isEmpty(agent.getPersonalInfo().getIdentityNumber())) {
			if (!ValidationUtil.isPatternMaches(agent.getPersonalInfo().getIdentityNumber(),
					ValidationUtil.ALPHANUMERIC_PATTERN)) {
				errorCodes.put("pattern.identityNumber", "pattern.identityNumber");
			}
		}
		if (agent.getContInfo() != null && !ObjectUtil.isEmpty(agent.getContInfo())
				&& !StringUtil.isEmpty(agent.getContInfo().getEmail())) {
			if (!ValidationUtil.isPatternMaches(agent.getContInfo().getEmail(), ValidationUtil.EMAIL_PATTERN)) {
				errorCodes.put("pattern.email", "pattern.email");
			}
		}
		printErrorCodes(errorCodes);
	}

}
