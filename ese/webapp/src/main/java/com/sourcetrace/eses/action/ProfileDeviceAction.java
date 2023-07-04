/*
 * ProfileDeviceAction.java
 * Copyright (c) 2012-2013, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.Asset;
import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.Device;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.ExporterRegistration;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.PersonalInfo;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.FileUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ProfileDeviceAction.
 */
public class ProfileDeviceAction extends SwitchValidatorAction {

	private static final long serialVersionUID = 1L;
	private String xlsFileName;
	private InputStream fileInputStream;
	private static final SimpleDateFormat fileNameDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	private static final Logger logger = Logger.getLogger(ProfileDeviceAction.class);
	private static final int BOD_STATUS = 0;

	private static final String POS = "POS";
	private static final String MOBILE = "Mobile";
	private static final String LAPTOP = "Laptop";

	private static final String IS_HOLD = "1";

	private String id;
	private String agentId;
	private String agentName;
	private String requestFor;
	private String profileId;
	private String hold;

	private List<Device> deviceList;
	private List<Agent> agents;

	private Device device;

	private boolean unMapAgent;
	private boolean pendingMTNTExists = false;
	private boolean deleteDeviceExistInTxn = false;

	/** The list device status. */
	Map<Boolean, String> listDeviceStatus = new LinkedHashMap<Boolean, String>();

	/** The list device types. */
	Map<String, String> listDeviceTypes = new LinkedHashMap<String, String>();

	private IUniqueIDGenerator idGenerator;
	private String tabIndex = "#tab1";
	private Long devId;
	private String deviceName;
	private String deviceCode;
	@Autowired
	private IUtilService utilService;

	/**
	 * Sets the device service.
	 * 
	 * @param deviceService
	 *            the new device service
	 */

	/**
	 * Gets the device service.
	 * 
	 * @return the device service
	 */

	/**
	 * Gets the device.
	 * 
	 * @return the device
	 */
	public Device getDevice() {

		return device;
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
	 * Sets the device.
	 * 
	 * @param device
	 *            the new device
	 */
	public void setDevice(Device device) {

		this.device = device;
	}

	/**
	 * Gets the device list.
	 * 
	 * @return the device list
	 */
	public List<Device> getDeviceList() {

		return deviceList;
	}

	/**
	 * Sets the device list.
	 * 
	 * @param deviceList
	 *            the new device list
	 */
	public void setDeviceList(List<Device> deviceList) {

		this.deviceList = deviceList;
	}

	/**
	 * Creates the.
	 * 
	 * @return the string
	 */

	public String create() {

		if (device == null) {
			command = CREATE;
			request.setAttribute(HEADING, getText("Profilecreate.page"));
			return INPUT;
		} else {
			if (!StringUtil.isEmpty(device.getMacAddress())) {
				device.setSerialNumber(convertMacToSerial(device.getMacAddress()));
			}
			this.getDevice().setEnabled(false);
			this.getDevice().setDeleted(false);
			device.setCode(idGenerator.getDeviceIdSeq());
			device.setIsRegistered(Device.DEVICE_REGISTERED);
			device.setCreatedUsername(getUsername());
			device.setLastUpdatedUsername(getUsername());
			device.setCreatedDate(new Date());
			device.setModifiedTime(new Date());
			device.setBranchId(getBranchId());
			this.getUtilService().addDevice(this.getDevice());
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
			this.setDevice(this.getUtilService().findDeviceById(Long.valueOf(id)));
			if (this.device == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			if ((this.device.getAgent() != null) && (this.device.getAgent().getPersonalInfo() != null)) {
				agentName = this.device.getAgent().getPersonalInfo().getFirstName() + " "
						+ this.device.getAgent().getPersonalInfo().getLastName();
			}
			// populate device status list according to es and es properties
			getDeviceStatusList();
			setCurrentPage(getCurrentPage());
			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getText(UPDATE));
		} else {
			if (device != null) {
				Device existing = utilService.findDeviceById(device.getId());
				if (existing != null) {
					if (existing.getAgent() != null) {
						if (existing.getAgent().getBodStatus() != BOD_STATUS) {
							addActionError(getText("updateBodStatus"));
							setDevice(device);
							if ((device.getAgent() != null) && (device.getAgent().getPersonalInfo() != null))
								agentName = device.getAgent().getPersonalInfo().getFirstName() + " "
										+ device.getAgent().getPersonalInfo().getLastName();
							return INPUT;
						}
					}
					existing.setName(device.getName());
					if (agentId != null && !agentId.equalsIgnoreCase("-1")) {
						Agent agent = utilService.findAgent((Long.parseLong(agentId)));
						existing.setAgent(agent);
						if (existing.getExporter() == null || ObjectUtil.isEmpty(existing.getExporter())) {
							ExporterRegistration ex;
							if (agent.getPackhouse() != null && agent.getPackhouse().getExporter() != null
									&& !ObjectUtil.isEmpty(agent.getPackhouse().getExporter()))
								ex = utilService.findExportRegById(agent.getPackhouse().getExporter().getId());
							else
								ex = utilService.findExportRegById(agent.getExporter().getId());
							// ExporterRegistration ex =
							// utilService.findExportRegById(agent.getExporter().getId());
							existing.setExporter(ex);
						}
					}

					existing.setLastUpdatedUsername(getUsername());
					existing.setModifiedTime(new Date());
					existing.setEnabled(this.getDevice().getEnabled());
					existing.setIsRegistered(Device.DEVICE_REGISTERED);
					existing.setBranchId(getBranchId());
					existing.setUpdatedUser(getUsername());
					existing.setUpdatedDate(new Date());
					utilService.updateDevice(existing);

				}
				setCurrentPage(getCurrentPage());
				if (existing == null) {
					addActionError(NO_RECORD);
					return REDIRECT;
				}

			}
			request.setAttribute(HEADING, getText(LIST));
			return LIST;
		}

		return INPUT;
	}

	/**
	 * Delete.
	 * 
	 * @return the string
	 */
	public String delete() {

		if (id != null && !id.equals("")) {
			Device exist = utilService.findDeviceById(this.device.getId());
			if (!ObjectUtil.isEmpty(exist)) {
				if (!ObjectUtil.isEmpty(exist.getAgent())) {
					if (exist.getAgent().getBodStatus() != BOD_STATUS) {
						addActionError(getText("bodStatus"));
						setCommand(DETAIL);
						request.setAttribute(HEADING, getText(command));
						this.setDevice(exist);
						if (!ObjectUtil.isEmpty(exist.getAgent())
								&& !ObjectUtil.isEmpty(exist.getAgent().getPersonalInfo()))
							this.agentName = exist.getAgent().getPersonalInfo().getFirstName() + " "
									+ exist.getAgent().getPersonalInfo().getLastName();
						return DETAIL;
					} else {
						exist.setAgent(null);
					}
				}
				exist.setDeleted(true);
				utilService.removeDevice(exist);
			} else {
				// addActionError(NO_RECORD);
			}
		}
		request.setAttribute(HEADING, getText(LIST));
		return REDIRECT;
	}

	/**
	 * Data.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 * @see com.sourcetrace.esesw.view.SwitchAction#data()
	 */
	public String data() throws Exception {

		Map<String, String> searchRecord = getDataTableJQGridRequestParam();

		Device filter = new Device();

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

		if (!StringUtil.isEmpty(searchRecord.get("serialNumber"))) {
			filter.setSerialNumber(searchRecord.get("serialNumber").trim());
		}
		if (!StringUtil.isEmpty(searchRecord.get("exporter"))) {
			ExporterRegistration exp = new ExporterRegistration();
			exp.setName(searchRecord.get("exporter").trim());
			filter.setExporter(exp);
		}
		if (getLoggedInDealer() > 0) {
			ExporterRegistration ex = new ExporterRegistration();
			ex.setId(getLoggedInDealer());
			Agent ss = new Agent();
			ss.setExporter(ex);
			filter.setAgent(ss);
		}

		if (!StringUtil.isEmpty(searchRecord.get("enabled"))) {
			if ("1".equals(searchRecord.get("enabled"))) {
				filter.setFilterStatus("enabled");
				filter.setEnabled(true);
			} else {
				filter.setFilterStatus("disabled");
				filter.setEnabled(false);
			}
		}

		if (!StringUtil.isEmpty(searchRecord.get("agentName"))) {
			Agent agent = new Agent();
			agent.setPersonalInfo(new PersonalInfo());
			agent.getPersonalInfo().setFirstName(searchRecord.get("agentName").trim());
			filter.setAgent(agent);

		}

		filter.setIsRegistered(Device.DEVICE_REGISTERED);
		Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
				getPage());

		return sendDataTableJSONResponse(data);

	}

	@SuppressWarnings("rawtypes")
	public String dataUnReg() throws Exception {

		Map<String, String> searchRecord = getDataTableJQGridRequestParam();
		Device filter = new Device();

		if (!StringUtil.isEmpty(searchRecord.get("branchId"))) {
			filter.setBranchId(searchRecord.get("branchId").trim());
		}
		if (!StringUtil.isEmpty(searchRecord.get("modifiedTime")))
			filter.setCode(searchRecord.get("modifiedTime").trim());

		if (!StringUtil.isEmpty(searchRecord.get("serialNo")))
			filter.setSerialNumber(searchRecord.get("serialNo").trim());

		if (!StringUtil.isEmpty(searchRecord.get("exporter"))) {
			ExporterRegistration exp = new ExporterRegistration();
			exp.setName(searchRecord.get("exporter").trim());
			filter.setExporter(exp);
		}

		if (!StringUtil.isEmpty(searchRecord.get("agentName"))) {
			Agent agent = new Agent();
			agent.setPersonalInfo(new PersonalInfo());
			agent.getPersonalInfo().setFirstName(searchRecord.get("agentName").trim());
			filter.setAgent(agent);

		}
		if (!StringUtil.isEmpty(searchRecord.get("lastUpdatedUsername"))) {
			filter.setName(searchRecord.get("lastUpdatedUsername").trim());
		}
		filter.setIsRegistered(Device.DEVICE_NOT_REGISTERED);
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

		Device device = (Device) obj;
		JSONObject jsonObject = new JSONObject();
		// JSONArray rows = new JSONArray();
		JSONObject objDt = new JSONObject();

		if ((getIsMultiBranch().equalsIgnoreCase("1")
				&& (getIsParentBranch().equals("1") || StringUtil.isEmpty(branchIdValue)))) {

			if (StringUtil.isEmpty(branchIdValue)) {
				objDt.put("branch",
						!StringUtil.isEmpty(getBranchesMap().get(getParentBranchMap().get(device.getBranchId())))
								? getBranchesMap().get(getParentBranchMap().get(device.getBranchId()))
								: getBranchesMap().get(device.getBranchId()));

			}
			objDt.put("subbranch", getBranchesMap().get(device.getBranchId()));

		} else {
			if (StringUtil.isEmpty(branchIdValue)) {
				objDt.put("branch", getBranchesMap().get(device.getBranchId()));
			}
		}
		String linkField = "<a href=device_detail.action?id=" + device.getId() + " title='Go To Detail' target=_blank>"
				+ device.getName() + "</a>";
		objDt.put("code", "<font color=\"#0000FF\" style=\"cursor:pointer;\">" + device.getCode() + "</font>");
		objDt.put("name", linkField);
		objDt.put("serialNo", device.getSerialNumber());
		objDt.put("status", device.getEnabled() ? getText("enabled") : getText("disabled"));

		if (ObjectUtil.isEmpty(device.getAgent())) {
			objDt.put("agentName", getText("NA"));
		} else {
			objDt.put("agentName", !StringUtil.isEmpty(device.getAgent().getProfileId())
					? device.getAgent().getPersonalInfo().getFirstName() + " "
							+ device.getAgent().getPersonalInfo().getLastName() + "-" + device.getAgent().getProfileId()
					: device.getAgent().getProfileId());

		}
		if (ObjectUtil.isEmpty(device.getExporter()) || device.getExporter().equals("")) {
			objDt.put("exporter", getText("NA"));

		} else {
			objDt.put("exporter",
					!StringUtil.isEmpty(device.getExporter()) ? device.getExporter().getName() : getText(""));
		}
		objDt.put("version", !StringUtil.isEmpty(device.getAppVersion()) ? device.getAppVersion() : getText(""));
		objDt.put("lastLogin", !StringUtil.isEmpty(device.getLoginTime()) ? device.getLoginTime() : getText(""));
		objDt.put("modTime", DateUtil.convertDateToString(device.getModifiedTime(), DateUtil.DATE_FORMAT_2));
		if (device.getIsRegistered() == 0) {
			objDt.put("action", "<button type=\"button\"  title='" + getText("button.update")
					+ "' onclick=\"openModel('" + String.valueOf(device.getSerialNumber()) + "','" + device.getId()
					+ "')\"  class=\"btn btn-primary btn-unreg\" data-toggle='modal' data-target='#slide'>"
					+ getText("button.update") + "</button> <button type=\"button\" title='" + getText("button.delete")
					+ "'  class=\"btnDel btn btn-danger\" onclick=\"deleteUnregisteredDevice(" + device.getId()
					+ ")\" data-toggle=\"deleteButton\" data-target=\"deleteForm\" >" + getText("button.delete")
					+ "</button>");
		}

		jsonObject.put("DT_RowId", device.getId());
		jsonObject.put("cell", objDt);
		return jsonObject;

	}

	/**
	 * Gets the data.
	 * 
	 * @return the data
	 * @see com.sourcetrace.esesw.view.SwitchValidatorAction#getData()
	 */
	public Object getData() {

		// populate device type and status list according to es and es
		// properties
		getDeviceTypeList();
		getDeviceStatusList();
		if ((device != null) && (device.getAgent() != null) && (device.getAgent().getPersonalInfo() != null))
			agentName = device.getAgent().getPersonalInfo().getFirstName() + " "
					+ device.getAgent().getPersonalInfo().getLastName();

		if (!ObjectUtil.isEmpty(device) && device.getIsRegistered() != null
				&& device.getIsRegistered() == Device.DEVICE_REGISTERED) {
			return device;
		}

		return this.getDevice();
	}

	@Getter
	@Setter
	List<Object[]> ex;

	/**
	 * Detail.
	 * 
	 * @return the string
	 */
	public String detail() {

		if (id != null && !id.equals(EMPTY)) {

			setCommand("detail");
			request.setAttribute(HEADING, getText(command));
			setCurrentPage(getCurrentPage());
			this.setDevice(this.getUtilService().findDeviceById(Long.valueOf(id)));
			ex = utilService.getAuditRecords("com.sourcetrace.eses.entity.Device", this.device.getId());
			if ((this.device.getAgent() != null) && (device.getAgent().getPersonalInfo() != null)) {
				agentName = this.device.getAgent().getPersonalInfo().getFirstName() + " "
						+ this.device.getAgent().getPersonalInfo().getLastName();
			}
		} else {
			request.setAttribute(HEADING, getText(LIST));
			return LIST;
		}
		return DETAIL;
	}

	/*
	 * @SuppressWarnings("unchecked") public String validateUnReg() throws
	 * Exception {
	 * 
	 * JSONObject unRegObj = new JSONObject(); Map<String, String> fieldErrors =
	 * getValidator().getValidationErrors(this.device); if
	 * (LOGGER.isInfoEnabled()) { LOGGER.info("validate() Errors " +
	 * fieldErrors); } if (fieldErrors.size() == 0) { unRegObj.put("status",
	 * "1"); } else { unRegObj.put("status", "0"); } JSONArray array = new
	 * JSONArray(); if (!ObjectUtil.isEmpty(fieldErrors)) { for
	 * (Map.Entry<String, String> errorEntry : fieldErrors.entrySet()) {
	 * JSONObject errorObj = new JSONObject(); errorObj.put("key",
	 * errorEntry.getKey()); errorObj.put("value",
	 * getText(errorEntry.getValue())); array.add(errorObj); } }
	 * unRegObj.put("message", array); sendResponse(unRegObj); return null; }
	 */

	/**
	 * Gets the agent list not map with device.
	 * 
	 * @return the agent list not map with device
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getAgentListNotMapWithDevice() {
		List<Agent> agentList = new ArrayList<>();
		if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
			agentList = (List<Agent>) farmerService.listObjectById(
					"FROM Agent a WHERE a.id NOT IN(SELECT d.agent.id FROM Device d WHERE d.agent.id IS NOT NULL) and a.exporter.id=? and a.status <>2",
					new Object[] { getLoggedInDealer() });

		} else {
			agentList = utilService.listAgentByAgentTypeNotMappedwithDevice();
		}

		Map<String, String> agentDropDownList = new LinkedHashMap<String, String>();
		for (Agent agent : agentList) {
			if (device != null && device.getExporter() != null) {
				ExporterRegistration ex;
				if (agent.getPackhouse() != null && agent.getPackhouse().getExporter() != null
						&& !ObjectUtil.isEmpty(agent.getPackhouse().getExporter())) {
					ex = agent.getPackhouse().getExporter();
				} else
					ex = agent.getExporter();
				if (device.getExporter().getId() == ex.getId()) {
					agentDropDownList.put(String.valueOf(agent.getId()), (agent.getPersonalInfo().getFirstName() + " "
							+ agent.getPersonalInfo().getLastName() + "-" + agent.getProfileId()));
				}
			} else {
				agentDropDownList.put(String.valueOf(agent.getId()), (agent.getPersonalInfo().getFirstName() + " "
						+ agent.getPersonalInfo().getLastName() + "-" + agent.getProfileId()));
			}
		}
		return agentDropDownList;
	}

	/**
	 * Gets the agent service.
	 * 
	 * @return the agent service
	 */
	/**
	 * Sets the agent service.
	 * 
	 * @param agentService
	 *            the new agent service
	 */
	/**
	 * Sets the agent id.
	 * 
	 * @param agentId
	 *            the new agent id
	 */
	public void setAgentId(String agentId) {

		this.agentId = agentId;
	}

	/**
	 * Gets the agent id.
	 * 
	 * @return the agent id
	 */
	public String getAgentId() {

		return agentId;
	}

	/**
	 * Reset.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String reset() throws Exception {

		setCurrentPage(getCurrentPage());
		if (!ObjectUtil.isEmpty(device)) {
			Device existing = utilService.findDeviceById(device.getId());
			if (!ObjectUtil.isEmpty(existing) && !ObjectUtil.isEmpty(existing.getAgent())) {
				/*
				 * if (existing.getAgent().getBodStatus() != BOD_STATUS) {
				 * addActionError(getText("resetBodStatus")); this.agentName =
				 * existing.getAgent().getPersonalInfo().getFirstName() + " " +
				 * existing.getAgent().getPersonalInfo().getLastName();
				 * this.setDevice(existing); } else {
				 */
				unMapAgent = true;
				existing.setAgent(null);
				utilService.updateDevice(existing);
				/* } */
				return DETAIL;
			}
		}
		return LIST;
	}

	/**
	 * Sets the un map agent.
	 * 
	 * @param unMapAgent
	 *            the new un map agent
	 */
	public void setUnMapAgent(boolean unMapAgent) {

		this.unMapAgent = unMapAgent;
	}

	/**
	 * Checks if is un map agent.
	 * 
	 * @return true, if is un map agent
	 */
	public boolean isUnMapAgent() {

		return unMapAgent;
	}

	/**
	 * Sets the agent name.
	 * 
	 * @param agentName
	 *            the new agent name
	 */
	public void setAgentName(String agentName) {

		this.agentName = agentName;
	}

	/**
	 * Gets the agent name.
	 * 
	 * @return the agent name
	 */
	public String getAgentName() {

		return agentName;
	}

	/**
	 * Gets the list device status.
	 * 
	 * @return the list device status
	 */
	public Map<Boolean, String> getListDeviceStatus() {

		return listDeviceStatus;
	}

	/**
	 * Sets the list device status.
	 * 
	 * @param listDeviceStatus
	 *            the list device status
	 */
	public void setListDeviceStatus(Map<Boolean, String> listDeviceStatus) {

		this.listDeviceStatus = listDeviceStatus;
	}

	/**
	 * Gets the list device types.
	 * 
	 * @return the list device types
	 */
	public Map<String, String> getListDeviceTypes() {

		return listDeviceTypes;
	}

	/**
	 * Sets the list device types.
	 * 
	 * @param listDeviceTypes
	 *            the list device types
	 */
	public void setListDeviceTypes(Map<String, String> listDeviceTypes) {

		this.listDeviceTypes = listDeviceTypes;
	}

	/**
	 * Gets the device type list.
	 * 
	 * @return the device type list
	 */
	private void getDeviceTypeList() {

		listDeviceTypes.put(LAPTOP, getText("Laptop"));
		listDeviceTypes.put(MOBILE, getText("Mobile"));
		listDeviceTypes.put(POS, getText("POS"));
	}

	/**
	 * Gets the device status list.
	 * 
	 * @return the device status list
	 */
	private void getDeviceStatusList() {

		listDeviceStatus.put(true, getText("status.enabled"));
		listDeviceStatus.put(false, getText("status.disabled"));
	}

	/**
	 * Gets the pending mtnt exists.
	 * 
	 * @return the pending mtnt exists
	 */
	public boolean getPendingMTNTExists() {

		return pendingMTNTExists;
	}

	/**
	 * Sets the pending mtnt exists.
	 * 
	 * @param pendingMTNTExists
	 *            the new pending mtnt exists
	 */
	public void setPendingMTNTExists(boolean pendingMTNTExists) {

		this.pendingMTNTExists = pendingMTNTExists;
	}

	/*
	 * public void setProductService(IProductService productService) {
	 * this.productService = productService; } public void
	 * setProductDistributionService(IProductDistributionService
	 * productDistributionService) { this.productDistributionService =
	 * productDistributionService; }
	 */

	/**
	 * Sets the agents.
	 * 
	 * @param agents
	 *            the new agents
	 */
	public void setAgents(List<Agent> agents) {

		this.agents = agents;
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
	 * Gets the hold.
	 * 
	 * @return the hold
	 */
	public String getHold() {

		return hold;
	}

	/**
	 * Sets the hold.
	 * 
	 * @param hold
	 *            the new hold
	 */
	public void setHold(String hold) {

		this.hold = hold;
	}

	/**
	 * Sets the process stock transfer.
	 * 
	 * @param processStockTransfer
	 *            the new process stock transfer
	 */
	/**
	 * Sets the request for.
	 * 
	 * @param requestFor
	 *            the new request for
	 */
	public void setRequestFor(String requestFor) {

		this.requestFor = requestFor;
	}

	/**
	 * Sets the delete device exist in txn.
	 * 
	 * @param deleteDeviceExistInTxn
	 *            the new delete device exist in txn
	 */
	public void setDeleteDeviceExistInTxn(boolean deleteDeviceExistInTxn) {

		this.deleteDeviceExistInTxn = deleteDeviceExistInTxn;
	}

	/**
	 * Checks if is delete device exist in txn.
	 * 
	 * @return true, if is delete device exist in txn
	 */
	public boolean isDeleteDeviceExistInTxn() {

		return deleteDeviceExistInTxn;
	}

	public void setIdGenerator(IUniqueIDGenerator idGenerator) {

		this.idGenerator = idGenerator;
	}

	public String getTabClass(String tabId) {

		return (StringUtil.isEmpty(tabId)) ? "" : (tabId.equalsIgnoreCase(tabIndex) ? "active" : "");
	}

	public String getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
	}

	public String deleteUnRegDevice() {

		Device device = utilService.findDeviceById(devId);
		if (!ObjectUtil.isEmpty(device)) {
			utilService.removeDevice(device);
		}
		try {
			JSONObject js = new JSONObject();
			js.put("msg", getText("msg.removed"));
			sendAjaxResponse(js);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Long getDevId() {
		return devId;
	}

	public void setDevId(Long devId) {
		this.devId = devId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public void updateUnReg() throws Exception {

		Device existing = utilService.findUnRegisterDeviceById(devId);
		if (existing != null) {
			if (existing.getAgent() != null) {
				if (existing.getAgent().getBodStatus() != BOD_STATUS) {
					addActionError(getText("updateBodStatus"));
					setDevice(device);
					if (device != null) {
						if ((device.getAgent() != null) && (device.getAgent().getPersonalInfo() != null))
							agentName = device.getAgent().getPersonalInfo().getFirstName() + " "
									+ device.getAgent().getPersonalInfo().getLastName();
					}
				}
			}
			existing.setName(deviceName);
			existing.setCode(idGenerator.getDeviceIdSeq());
			existing.setBranchId(getBranchId());
			existing.setUpdatedUser(getUsername());
			existing.setUpdatedDate(new Date());
			if (agentId != null) {
				Agent agent = utilService.findAgent((Long.parseLong(agentId)));
				existing.setAgent(agent);

				if (existing.getExporter() == null || ObjectUtil.isEmpty(existing.getExporter())) {
					ExporterRegistration ex;
					if (agent.getPackhouse() != null && agent.getPackhouse().getExporter() != null)
						ex = utilService.findExportRegById(agent.getPackhouse().getExporter().getId());
					else
						ex = utilService.findExportRegById(agent.getExporter().getId());

					existing.setExporter(ex);
				}
			}
			// existing.setEnabled(this.getDevice().isEnabled());
			existing.setModifiedTime(new Date());
			existing.setIsRegistered(Device.DEVICE_REGISTERED);
			existing.setEnabled(true);
			utilService.updateDevice(existing);
		}
		setCurrentPage(getCurrentPage());
		if (existing == null) {
			addActionError(NO_RECORD);
		}

	}

	/**
	 * Gets the m d5 string.
	 * 
	 * @param macAddress
	 *            the mac address
	 * @return the m d5 string
	 */
	public String convertMacToSerial(String macAddress) {

		/*
		 * String md5Value = null; try { MessageDigest md =
		 * MessageDigest.getInstance("MD5"); byte[] dataBytes =
		 * macAddress.getBytes(); md.update(dataBytes); byte[] mdbytes =
		 * md.digest();
		 * 
		 * StringBuffer sb = new StringBuffer(); for (int i = 0; i <
		 * mdbytes.length; i++) { sb.append(Integer.toString((mdbytes[i] & 0xff)
		 * + 0x100, 16).substring(1)); } md5Value = sb.toString();
		 * LOGGER.info("Device MD5 Value->" + sb.toString());
		 * 
		 * } catch (Exception e) { e.printStackTrace(); } return md5Value;
		 */
		MessageDigest md;
		byte[] md5hash = new byte[32];
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(macAddress.getBytes(), 0, macAddress.length());
			md5hash = md.digest();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertToHex(md5hash);
	}

	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	public String getDeviceAddressDefaultValue() {

		return (ObjectUtil.isEmpty(this.device) ? "0" : device.getDeviceAddress());
	}

	public String populateXLS() throws Exception {
		response.setContentType("application/vnd.ms-excel");
		// response.setHeader("Content-Disposition",
		// "attachment;filename=" + getText("farmerList") +
		// fileNameDateFormat.format(new Date()) + ".xls");
		InputStream is = null;

		is = getFarmerInputStream();

		setXlsFileName(getText("deviceList") + fileNameDateFormat.format(new Date()));
		Map<String, InputStream> fileMap = new HashMap<String, InputStream>();
		fileMap.put(xlsFileName, is);
		setFileInputStream(FileUtil.createFileInputStreamToZipFile(getText("deviceList"), fileMap, ".xls"));

		return "xls";
	}

	StringBuffer headerCols = new StringBuffer();
	List<String> dynamicExportFieldList = new ArrayList<>();
	String builder = new String();
	HSSFRow row, titleRow;
	HSSFCell cell;

	int rowNum = 3;
	int colNum = 0;

	private InputStream getFarmerInputStream() throws IOException, ParseException {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(getLocaleProperty("exportDeviceTitle"));
		HSSFPatriarch drawing = sheet.createDrawingPatriarch();

		HSSFCellStyle style1 = wb.createCellStyle();
		HSSFCellStyle style2 = wb.createCellStyle();

		HSSFFont font1 = wb.createFont();
		font1.setFontHeightInPoints((short) 22);

		HSSFFont font2 = wb.createFont();
		font2.setFontHeightInPoints((short) 12);

		HSSFFont font3 = wb.createFont();
		font3.setFontHeightInPoints((short) 10);

		branchIdValue = getBranchId(); // set value for branch id.
		buildBranchMap();

		titleRow = sheet.createRow(rowNum++);
		cell = titleRow.createCell(3);
		cell.setCellValue(new HSSFRichTextString(getLocaleProperty("exportDeviceTitle")));
		cell.setCellStyle(style1);
		font1.setBoldweight((short) 22);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style1.setFont(font1);

		sheet.addMergedRegion(new CellRangeAddress(3, 3, 3, 5));
		int imgRow1 = 0;
		int imgRow2 = 2;
		int imgCol1 = 0;
		int imgCol2 = 1;

		sheet.addMergedRegion(new CellRangeAddress(imgRow1, imgRow2, imgCol1, imgCol2));

		++rowNum;

		rowNum += 2;
		HSSFRow rowHead1 = sheet.createRow(rowNum++);
		rowHead1.setHeight((short) 600);

		String columnHeaders1 = null;

		if (StringUtil.isEmpty(getBranchId())) {
			columnHeaders1 = getLocaleProperty("exportDeviceHeadingBranch");
		} else {
			columnHeaders1 = getLocaleProperty("exportDeviceHeading");
		}
		int iterator1 = 1;

		cell = rowHead1.createCell(0);
		cell.setCellValue(new HSSFRichTextString("SNO"));
		style2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cell.setCellStyle(style2);
		font2.setBoldweight((short) 12);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style2.setFont(font2);
		style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style2.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style2.setTopBorderColor(IndexedColors.BLACK.getIndex());

		for (String cellHeader : columnHeaders1.split("\\,")) {

			if (StringUtil.isEmpty(branchIdValue)) {
				cell = rowHead1.createCell(iterator1);
				cell.setCellValue(new HSSFRichTextString(cellHeader));
				style2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
				style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cell.setCellStyle(style2);
				font2.setBoldweight((short) 12);
				font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				style2.setFont(font2);
				if (iterator1 != 7) {
					sheet.setColumnWidth(iterator1, (15 * 450));
				}
				iterator1++;

			} else {
				if (!cellHeader.equalsIgnoreCase(getText("app.branch"))) {
					cell = rowHead1.createCell(iterator1);
					cell.setCellValue(new HSSFRichTextString(cellHeader));
					// style2.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
					// style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					cell.setCellStyle(style2);
					font2.setBoldweight((short) 12);
					font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					style2.setFont(font2);
					sheet.setColumnWidth(iterator1, (15 * 550));
					iterator1++;
				}
			}
		}

		Map<String, String> searchRecord = getJQGridRequestParam();

		Device filter = new Device();

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

		if (!StringUtil.isEmpty(searchRecord.get("serialNumber"))) {
			filter.setSerialNumber(searchRecord.get("serialNumber").trim());
		}

		/*
		 * if (!StringUtil.isEmpty(searchRecord.get("deviceType"))) {
		 * filter.setDeviceType(searchRecord.get("deviceType").trim()); }
		 */

		if (!StringUtil.isEmpty(searchRecord.get("enabled"))) {
			if ("1".equals(searchRecord.get("enabled"))) {
				filter.setFilterStatus("enabled");
				filter.setEnabled(true);
			} else {
				filter.setFilterStatus("disabled");
				filter.setEnabled(false);
			}
		}

		if (!StringUtil.isEmpty(searchRecord.get("agentName"))) {
			Agent agent = new Agent();
			agent.setPersonalInfo(new PersonalInfo());
			agent.getPersonalInfo().setFirstName(searchRecord.get("agentName").trim());
			filter.setAgent(agent);

		}
		filter.setIsRegistered(Device.DEVICE_REGISTERED);
		Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
				getPage());
		List<Device> deviceList = (List) data.get(ROWS);

		// 0=Id,1=Farmer Id,2=Farmer Code,3=First Name,4=Last
		// name,5=surName,6=village name,7=Group name,8=Is certified
		// Farmer,9=Status,10=BranchId
		AtomicInteger snoCount = new AtomicInteger(0);
		deviceList.stream().forEach(device -> {
			row = sheet.createRow(rowNum++);
			row.setHeight((short) 400);
			colNum = 0;
			cell = row.createCell(colNum++);
			cell.setCellValue(snoCount.incrementAndGet());
			/*
			 * if (StringUtil.isEmpty(branchIdValue)) { cell =
			 * row.createCell(colNum++); cell.setCellValue(new
			 * HSSFRichTextString(!StringUtil.isEmpty(branchesMap.get(device.
			 * getBranch())) ? (branchesMap.get(device.getBranch())) : "")); }
			 */

			cell = row.createCell(colNum++);
			cell.setCellValue(
					new HSSFRichTextString(StringUtil.isEmpty(device.getCode()) ? getText("NA") : device.getCode()));

			cell = row.createCell(colNum++);
			cell.setCellValue(
					new HSSFRichTextString(StringUtil.isEmpty(device.getName()) ? getText("NA") : device.getName()));

			cell = row.createCell(colNum++);
			cell.setCellValue(new HSSFRichTextString(
					StringUtil.isEmpty(device.getSerialNumber()) ? getText("NA") : device.getSerialNumber()));

			cell = row.createCell(colNum++);
			cell.setCellValue(new HSSFRichTextString(device.getEnabled() ? getText("enabled") : getText("disabled")));

			if (ObjectUtil.isEmpty(device.getAgent())) {
				cell = row.createCell(colNum++);
				cell.setCellValue(new HSSFRichTextString(getText("NA")));

			} else {
				cell = row.createCell(colNum++);
				cell.setCellValue(new HSSFRichTextString(!StringUtil.isEmpty(device.getAgent().getProfileId())
						? device.getAgent().getPersonalInfo().getFirstName() + " "
								+ device.getAgent().getPersonalInfo().getLastName() + "-"
								+ device.getAgent().getProfileId()
						: device.getAgent().getProfileId()));
			}

			cell = row.createCell(colNum++);
			cell.setCellValue(new HSSFRichTextString(
					!StringUtil.isEmpty(device.getAppVersion()) ? device.getAppVersion() : getText("")));

			cell = row.createCell(colNum++);
			cell.setCellValue(new HSSFRichTextString(
					!StringUtil.isEmpty(device.getLoginTime()) ? device.getLoginTime() : getText("")));

		});

		for (int i = 0; i <= colNum; i++) {
			sheet.autoSizeColumn(i);
		}

		// Add a picture
		int pictureIdx = getPicIndex(wb);
		HSSFClientAnchor anchor = new HSSFClientAnchor(100, 150, 900, 100, (short) 0, 0, (short) 0, 4);
		anchor.setAnchorType(1);
		HSSFPicture picture = drawing.createPicture(anchor, pictureIdx);
		picture.resize(0.50);

		String makeDir = FileUtil.storeXls(request.getSession().getId());
		String fileName = getLocaleProperty("deviceList") + fileNameDateFormat.format(new Date()) + ".xls";
		FileOutputStream fileOut = new FileOutputStream(makeDir + fileName);
		wb.write(fileOut);
		InputStream stream = new FileInputStream(new File(makeDir + fileName));
		fileOut.close();
		return stream;

	}

	public String getXlsFileName() {
		return xlsFileName;
	}

	public void setXlsFileName(String xlsFileName) {
		this.xlsFileName = xlsFileName;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public static SimpleDateFormat getFilenamedateformat() {
		return fileNameDateFormat;
	}

	public int getPicIndex(HSSFWorkbook wb) throws IOException {

		int index = -1;
		String roleName = (String) request.getSession().getAttribute("role");

		byte[] picData = null;

		picData = utilService.findLogoByCode(Asset.APP_LOGO);

		index = wb.addPicture(picData, HSSFWorkbook.PICTURE_TYPE_JPEG);

		return index;
	}

	@SuppressWarnings("unchecked")
	protected JSONObject getJSONObject(Object id, Object name) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("name", name);
		return jsonObject;
	}

	public void populateVersion() {

		String version = "";
		JSONArray jArray = new JSONArray();
		ESESystem preferencesOne = utilService.findPrefernceById("1");
		if (!StringUtil.isEmpty(preferencesOne))
			version = preferencesOne.getPreferences().get(ESESystem.REMOTE_APK_VERSION) + "."
					+ preferencesOne.getPreferences().get(ESESystem.REMOTE_CONFIG_VERSION) + "."
					+ preferencesOne.getPreferences().get(ESESystem.REMOTE_DB_VERSION);

		jArray.add(getJSONObject("version", version));
		sendAjaxResponse(jArray);

	}

	public IUtilService getUtilService() {
		return utilService;
	}

	public void setUtilService(IUtilService utilService) {
		this.utilService = utilService;
	}
}
