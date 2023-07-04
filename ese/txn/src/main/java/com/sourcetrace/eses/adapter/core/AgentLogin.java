/*
 * AgentLogin.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.adapter.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.Device;
import com.sourcetrace.eses.entity.ESEAccount;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.ESETxn;
import com.sourcetrace.eses.entity.ExporterRegistration;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.ProcurementGrade;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.entity.Profile;
import com.sourcetrace.eses.interceptor.ITxnErrorCodes;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.property.TxnEnrollmentProperties;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.exception.SwitchException;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class AgentLogin.
 */
@Component
@Getter
@Setter
public class AgentLogin implements ITxnAdapter {

	SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.SYNC_DATE_TIME);
	private static final Logger LOGGER = Logger.getLogger(AgentLogin.class.getName());

	@Autowired
	private IUtilService utilService;
	@Autowired
	private IUniqueIDGenerator idGenerator;
	@Autowired
	private IFarmerService farmerService;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected ServletContext context;
	private Map<String, ITxnAdapter> txnMap;
	private String MOBILE_VERSION_DETAILS;
	private String tenantId;
	private String branch;
	public static final String REMOTE_APK_VERSION = "REMOTE_APK_VERSION";
	public static final String REMOTE_CONFIG_VERSION = "REMOTE_CONFIG_VERSION";
	public static final String REMOTE_DB_VERSION = "REMOTE_DB_VERSION";

	@SuppressWarnings("unchecked")
	private void groupDownloadTransactionsJson(Map req, Map res) {

		if (!ObjectUtil.isEmpty(txnMap)) {
			for (Map.Entry txnAdapterEntry : txnMap.entrySet()) {
				ITxnAdapter txnAdapter = (ITxnAdapter) txnAdapterEntry.getValue();
				if (!ObjectUtil.isEmpty(txnAdapter)) {
					Map txnAdapterResp = txnAdapter.processJson(req);
					res.put(txnAdapterEntry.getKey(), txnAdapterResp);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Map respMap(Map reqData, Agent agent, Device device, boolean isJson) {
		MOBILE_VERSION_DETAILS = "1";
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		String agentId = head.getAgentId();
		String serialNo = head.getSerialNo();
		DecimalFormat formatter = new DecimalFormat("0.00");
		tenantId = head.getTenantId();
		branch = head.getBranchId();
		LOGGER.info("AGENT ID : " + agentId);
		LOGGER.info("SERIAL NO : " + serialNo);
		agent.setCreTime(DateUtil.convertStringToDate(head.getTxnTime(), DateUtil.TXN_TIME_FORMAT));
		agent.setPasswordUpt(null);
		agent.setBodStatus(0);
		if (agent.getStatus() == Profile.LOCKED) {
			agent.setStatus(Profile.ACTIVE);
		}
		agent.setIsTrainingAvailable(0);

		utilService.update(agent);
		ESEAccount account = utilService.findAccountByProfileIdAndProfileType(agent.getProfileId(),
				ESEAccount.AGENT_ACCOUNT);
		ESESystem eseSystem = utilService.findPrefernceById("1");
		ESESystem preferences = utilService.findPrefernceById("2");
		String branchId = device.getBranchId();
		BranchMaster branchMaster = utilService.findBranchMasterByBranchId(branchId);
		ESESystem eseSystemUser = utilService.findPrefernceByOrganisationId(branchId);
		eseSystemUser = eseSystemUser == null || eseSystemUser.getPreferences() == null
				|| eseSystemUser.getPreferences().isEmpty() ? eseSystem : eseSystemUser;
		if (agent.getBodStatus() == ESETxn.WEB_BOD) {
			throw new SwitchException(ITxnErrorCodes.WEB_BOD_EXIST,
					(Long) reqData.get(TransactionProperties.TXN_LOG_ID));
		}
		long aRevNo = 0;
		String agentRevisionNo = (String) reqData.get(TxnEnrollmentProperties.AGENT_REVISION_NO);
		if (!StringUtil.isEmpty(agentRevisionNo)) {
			aRevNo = Long.valueOf(agentRevisionNo);
		}
		Map resp = new LinkedHashMap<>();
		resp.put(TxnEnrollmentProperties.AGENT_NAME,
				(ObjectUtil.isEmpty(agent) && ObjectUtil.isEmpty(agent.getPersonalInfo()) ? ""
						: agent.getPersonalInfo().getAgentName()));
		resp.put(TransactionProperties.IS_FIRST_LOGIN,
				agent.getEnrollId() == null || StringUtil.isEmpty(agent.getEnrollId()) ? "1" : agent.getEnrollId());
		resp.put(TransactionProperties.DEVICE_ID, device.getCode());
		resp.put(TransactionProperties.SYNC_TIME_STAMP, sdf.format(new Date()));
		resp.put(TransactionProperties.DISPLAY_TS_FORMAT, DateUtil.TXN_DATE_TIME);
		resp.put(TransactionProperties.SERVICE_POINT_ID, "");
		resp.put(TransactionProperties.SERVICE_POINT_NAME, "");
		resp.put(TransactionProperties.BALANCE,
				(ObjectUtil.isEmpty(account)) ? "" : formatter.format(account.getCashBalance()));
		resp.put(TxnEnrollmentProperties.CLIENT_ID_SEQ, getFarmerIdSeq(agent));

		resp.put(TransactionProperties.AGENT_TYPE,
				(ObjectUtil.isEmpty(agent) && ObjectUtil.isEmpty(agent.getAgentType()) ? ""
						: agent.getAgentType().getCode()));
		List<ProcurementVariety> cropCodes;
		List<Long> gradeIds;

		if (agent.getPackhouse() != null) {
			resp.put(TransactionProperties.PACKHOUSE_ID,
					agent.getPackhouse().getId() == null ? "" : agent.getPackhouse().getId());
			resp.put(TransactionProperties.PACKHOUSE_NAME,
					agent.getPackhouse().getName() == null ? "" : agent.getPackhouse().getName());
			resp.put(TransactionProperties.PACKHOUSE_CODE,
					agent.getPackhouse().getCode() == null ? "" : agent.getPackhouse().getCode());
			resp.put(TransactionProperties.EXPORTER_LICENSE, agent.getPackhouse().getExporter().getRefLetterNo() == null
					? "" : agent.getPackhouse().getExporter().getRefLetterNo());
			resp.put(TransactionProperties.EXPORTER_EXP,
					agent.getPackhouse().getExporter().getExpireDate() == null ? ""
							: DateUtil.convertDateToString(agent.getPackhouse().getExporter().getExpireDate(),
									DateUtil.TXN_DATE_TIME));
			resp.put(TransactionProperties.EXPORTER_NAME, agent.getPackhouse().getExporter().getName() == null ? ""
					: agent.getPackhouse().getExporter().getName());
			resp.put(TransactionProperties.EXPORTER_CODE, agent.getPackhouse().getExporter().getId() == null ? ""
					: agent.getPackhouse().getExporter().getId());
			cropCodes = farmerService.findProcurementVarietyByIds(
					StringUtil.convertStringList(agent.getPackhouse().getExporter().getFarmerHaveFarms()));
			gradeIds = StringUtil.convertStringList(agent.getPackhouse().getExporter().getScattered());
			if (agent.getPackhouse().getExporter().getStatus() != null
					&& !StringUtil.isEmpty(agent.getPackhouse().getExporter().getStatus())
					&& agent.getPackhouse().getExporter().getIsActive() != null
					&& !StringUtil.isEmpty(agent.getPackhouse().getExporter().getIsActive())) {
				if (agent.getPackhouse().getExporter().getStatus().equals(1)
						&& agent.getPackhouse().getExporter().getIsActive().equals(1l)) {
					resp.put(TransactionProperties.EXPORTER_STATUS, "1");
				} else {
					resp.put(TransactionProperties.EXPORTER_STATUS, "0");
				}
			} else {
				resp.put(TransactionProperties.EXPORTER_STATUS, "0");
			}

		} else {
			resp.put(TransactionProperties.PACKHOUSE_ID, "");
			resp.put(TransactionProperties.PACKHOUSE_NAME, "");
			resp.put(TransactionProperties.PACKHOUSE_CODE, "");
			resp.put(TransactionProperties.EXPORTER_LICENSE, "");
			resp.put(TransactionProperties.EXPORTER_EXP, "");
			resp.put(TransactionProperties.EXPORTER_STATUS, "");
			resp.put(TransactionProperties.EXPORTER_NAME,
					agent.getExporter().getName() == null ? "" : agent.getExporter().getName());
			resp.put(TransactionProperties.EXPORTER_CODE,
					agent.getExporter().getId() == null ? "" : agent.getExporter().getId());
			cropCodes = farmerService.findProcurementVarietyByIds(
					StringUtil.convertStringList(agent.getExporter().getFarmerHaveFarms()));
			gradeIds = StringUtil.convertStringList(agent.getExporter().getScattered());
		}
		resp.put(TxnEnrollmentProperties.VARIETY_CODE,
				cropCodes != null ? cropCodes.stream().map(u -> u.getCode()).collect(Collectors.joining(",")) : "");

		List<ProcurementGrade> gradeLis = cropCodes.stream().flatMap(u -> u.getProcurementGrades().stream())
				.filter(u -> gradeIds.contains(u.getId())).collect(Collectors.toList());
		resp.put(TxnEnrollmentProperties.GRADE_CODE,
				gradeLis != null ? gradeLis.stream().map(u -> u.getCode()).collect(Collectors.joining(",")) : "");

		resp.put(TransactionProperties.TARE_WEIGHT, preferences.getPreferences().get(ESESystem.TARE_WEIGHT));
		String seasonCode = utilService.findCurrentSeasonCodeByBranchId(branchId);
		resp.put(TxnEnrollmentProperties.CURRENT_SEASON_CODE_LOGIN, seasonCode);
		resp.put(TransactionProperties.DISPLAY_TXN_DATE_FORMAT, DateUtil.PROFILE_DATE_FORMAT);
		resp.put(TransactionProperties.IS_GENERIC, eseSystem.getPreferences().get(ESESystem.ENABLE_MULTI_PRODUCT));
		resp.put(TransactionProperties.GENERAL_DATE_FORMAT,
				eseSystem.getPreferences().get(ESESystem.GENERAL_DATE_FORMAT));
		resp.put(TransactionProperties.IS_BATCH_AVAIL,
				eseSystem.getPreferences().get(ESESystem.ENABLE_BATCH_NO) != null
						&& !StringUtil.isEmpty(eseSystem.getPreferences().get(ESESystem.ENABLE_BATCH_NO))
								? eseSystem.getPreferences().get(ESESystem.ENABLE_BATCH_NO) : "");
		resp.put(TxnEnrollmentProperties.IS_GRAMPANCHAYAT,
				StringUtil.isEmpty(eseSystem.getPreferences().get(ESESystem.ENABLE_GRAMPANJAYAT)) ? "0"
						: eseSystem.getPreferences().get(ESESystem.ENABLE_GRAMPANJAYAT));
		resp.put(TxnEnrollmentProperties.IS_FIELDSTAFF_TRACKING,
				!eseSystem.getPreferences().containsKey(ESESystem.IS_FIELDSTAFF_TRACKING)
						|| StringUtil.isEmpty(eseSystem.getPreferences().get(ESESystem.IS_FIELDSTAFF_TRACKING)) ? "0"
								: eseSystem.getPreferences().get(ESESystem.IS_FIELDSTAFF_TRACKING));
		resp.put(TransactionProperties.CURRENCY,
				eseSystem.getPreferences().get(ESESystem.CURRENCY_TYPE) != null
						&& !StringUtil.isEmpty(eseSystem.getPreferences().get(ESESystem.CURRENCY_TYPE))
								? eseSystem.getPreferences().get(ESESystem.CURRENCY_TYPE) : "");
		resp.put(TransactionProperties.AREA_TYPE,
				eseSystem.getPreferences().get(ESESystem.AREA_TYPE) != null
						&& !StringUtil.isEmpty(eseSystem.getPreferences().get(ESESystem.AREA_TYPE))
								? eseSystem.getPreferences().get(ESESystem.AREA_TYPE) : "");

		HttpServletRequest httserreq = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String url = httserreq.getRequestURL().toString();
		try {
			URL aURL;
			aURL = new URL(url);
			String path = aURL.getPath();
			String fullPath[] = path.split("/", 0);
			String urll = aURL.getProtocol() + "://" + aURL.getAuthority() + "/"
					+ "nhts/traceabilityViewReport_list.action?layoutType=publiclayout&batchNo=";
			resp.put(TxnEnrollmentProperties.SHIPMENT_URL, urll);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		/*
		 * try { String url = request.getRequestURL().toString(); URL aURL; aURL
		 * = new URL(url); String path = aURL.getPath(); String fullPath[] =
		 * path.split("/", 0); String urll = aURL.getProtocol() + "://" +
		 * aURL.getAuthority() + "/" + fullPath[1]; System.out.println(urll); }
		 * catch (MalformedURLException e) { e.printStackTrace(); }
		 */

		/*
		 * resp.put(TransactionProperties.WAREHOUSE_ID,
		 * (ObjectUtil.isEmpty(agent) ||
		 * ObjectUtil.isEmpty(agent.getProcurementCenter()) ||
		 * StringUtil.isEmpty(agent.getProcurementCenter().getCode()) ? "" :
		 * agent.getProcurementCenter().getCode()));
		 */
		/*
		 * List warehousecol = new ArrayList(); if
		 * (!ObjectUtil.isListEmpty(agent.getWareHouses())) { for (Warehouse
		 * samithi : agent.getWareHouses()) { Map samithiCodeData = new
		 * HashMap(); samithiCodeData.put(TxnEnrollmentProperties.SAMITHI_CODE,
		 * samithi.getCode()); warehousecol.add(samithiCodeData); } }
		 */
		resp.put(TxnEnrollmentProperties.SAMITHI_LIST, "");
		Long farmerRevisionNo = 0l;
		Long farmRevisionNo = 0l;
		Long farmCropsRevisionNo = 0l;
		farmerRevisionNo = farmerService.findActiveContractFarmersLatestRevisionNoByAgentAndSeason(agent.getId(),
				seasonCode);
		farmRevisionNo = farmerService.listFarmFieldsByFarmerIdByAgentIdAndSeasonRevisionNo(agent.getId());

		resp.put(TxnEnrollmentProperties.FARMER_DOWNLOAD_REVISION_NO, farmerRevisionNo);
		resp.put(TxnEnrollmentProperties.FARM_DOWNLOAD_REVISION_NO, farmRevisionNo);
		resp.put(TxnEnrollmentProperties.FARM_CROPS_DOWNLOAD_REVISION_NO, farmCropsRevisionNo);
		resp.put(TxnEnrollmentProperties.CUSTOMER_PROJECT_REVISION_NO, utilService.findCustomerLatestRevisionNo());
		resp.put(TxnEnrollmentProperties.PRODUCTS_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.SEASON_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.LOCATION_DOWNLOAD_REVISION_NO, "0");
		resp.put(TransactionProperties.WAREHOUSE_PRODUCT_STOCK_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.VILLAGE_WAREHOUSE_STOCK_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.FARMER_OUTSTANDING_BALANCE_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.PROCUREMENT_PRODUCT_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.BUYER_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.CATALOGUE_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.COOPERATIVE_DOWNLOAD_REVISION_NO, "0");
		resp.put(TransactionProperties.SUPPLIER_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.DYNAMIC_COMPONENT_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.TRAINING_CRITERIA_CATEGORY_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.TRAINING_CRITERIA_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.PLANNER_DOWNLOAD_REVISION_NO, "0");
		if (agent.getRevisionNumber() > aRevNo) {
			reqData.put(TxnEnrollmentProperties.FARMER_OUTSTANDING_BALANCE_DOWNLOAD_REVISION_NO, "0");
			reqData.put(TxnEnrollmentProperties.VILLAGE_WAREHOUSE_STOCK_DOWNLOAD_REVISION_NO, "0");
		}
		resp.put(TxnEnrollmentProperties.AGENT_REVISION_NO, agent.getRevisionNumber());
		resp.put(TxnEnrollmentProperties.INTERESRT_RATE_APPLICAPLE,
				eseSystem.getPreferences().get(ESESystem.INTEREST_MODULE));
		if (eseSystem.getPreferences().get(ESESystem.INTEREST_MODULE).equals("1")) {
			resp.put(TxnEnrollmentProperties.RATE_OF_INTEREST,
					eseSystem.getPreferences().get(ESESystem.RATE_OF_INTEREST));
			resp.put(TxnEnrollmentProperties.EFFECTIVE_FROM,
					DateUtil.convertDateFormat(eseSystem.getPreferences().get(ESESystem.ROI_EFFECTIVE_FROM),
							DateUtil.DATE_FORMAT, DateUtil.DATABASE_DATE_FORMAT));
			resp.put(TxnEnrollmentProperties.IS_APPLICAPLE_EX_FARMER,
					eseSystem.getPreferences().get(ESESystem.ROI_EFFECT_EXISTING_FARMER));
		} else {
			resp.put(TxnEnrollmentProperties.RATE_OF_INTEREST, "");
			resp.put(TxnEnrollmentProperties.EFFECTIVE_FROM, "");
			resp.put(TxnEnrollmentProperties.IS_APPLICAPLE_EX_FARMER, "");
			resp.put(TxnEnrollmentProperties.PREVIOUS_INTEREST_RATE, "");
		}
		resp.put(TxnEnrollmentProperties.IS_QR_CODE_SEARCH_REQUIRED,
				eseSystem.getPreferences().get(ESESystem.IS_QR_CODE_SEARCH_REQUIRED));
		resp.put(TransactionProperties.BRANCH_ID, branchId);
		resp.put(TransactionProperties.PARENT_BRANCH_ID,
				branchMaster.getParentBranch() == null ? "" : branchMaster.getParentBranch());
		resp.put(TxnEnrollmentProperties.REMOTE_APK_VERSION,
				eseSystem.getPreferences().get(ESESystem.REMOTE_APK_VERSION));
		resp.put(TxnEnrollmentProperties.REMOTE_CONFIG_VERSION,
				eseSystem.getPreferences().get(ESESystem.REMOTE_CONFIG_VERSION));
		resp.put(TxnEnrollmentProperties.REMOTE_DB_VERSION,
				eseSystem.getPreferences().get(ESESystem.REMOTE_DB_VERSION));

		Date crtDate = agent.getPasswordHistory() != null && !agent.getPasswordHistory().isEmpty()
				? agent.getPasswordHistory().iterator().next().getCreatedDate() : agent.getCreatedDate();
		resp.put("ag_days", DateUtil.convertDateToString(crtDate,
				eseSystem.getPreferences().get(ESESystem.GENERAL_DATE_FORMAT).toString()));

		resp.put("p_age", String.valueOf(eseSystemUser.getPreferences().get(ESESystem.AGE).toString()));

		resp.put("p_rem", String.valueOf(eseSystemUser.getPreferences().get(ESESystem.REMINDER_DAYS).toString()));

		resp.put(TxnEnrollmentProperties.AREA_CAPTURE_MODE,
				StringUtil.isEmpty(eseSystemUser.getPreferences().get(ESESystem.AREA_CAPTURE_MODE)) ? ""
						: eseSystemUser.getPreferences().get(ESESystem.AREA_CAPTURE_MODE));
		resp.put(TxnEnrollmentProperties.GEO_FENCING_FLAG,
				StringUtil.isEmpty(eseSystemUser.getPreferences().get(ESESystem.GEO_FENCING_FLAG)) ? ""
						: eseSystemUser.getPreferences().get(ESESystem.GEO_FENCING_FLAG));
		resp.put(TxnEnrollmentProperties.GEO_FENCING_RADIUS_MT,
				StringUtil.isEmpty(eseSystemUser.getPreferences().get(ESESystem.GEO_FENCING_RADIUS_MT)) ? ""
						: eseSystemUser.getPreferences().get(ESESystem.GEO_FENCING_RADIUS_MT));
		resp.put(TransactionProperties.FTP_PASSWORD,
				eseSystem.getPreferences().get(ESESystem.FTP_PASSWORD) != null
						&& !StringUtil.isEmpty(eseSystem.getPreferences().get(ESESystem.FTP_PASSWORD))
								? eseSystem.getPreferences().get(ESESystem.FTP_PASSWORD) : "");
		resp.put(TransactionProperties.FTP_URL,
				eseSystem.getPreferences().get(ESESystem.FTP_URL) != null
						&& !StringUtil.isEmpty(eseSystem.getPreferences().get(ESESystem.FTP_URL))
								? eseSystem.getPreferences().get(ESESystem.FTP_URL) : "");
		resp.put(TransactionProperties.FTP_USERNAME,
				eseSystem.getPreferences().get(ESESystem.FTP_USERNAME) != null
						&& !StringUtil.isEmpty(eseSystem.getPreferences().get(ESESystem.FTP_USERNAME))
								? eseSystem.getPreferences().get(ESESystem.FTP_USERNAME) : "");
		resp.put(TransactionProperties.FTP_VIDEO_PATH,
				eseSystem.getPreferences().get(ESESystem.FTP_VIDEO_PATH) != null
						&& !StringUtil.isEmpty(eseSystem.getPreferences().get(ESESystem.FTP_VIDEO_PATH))
								? eseSystem.getPreferences().get(ESESystem.FTP_VIDEO_PATH) : "");
		resp.put(TransactionProperties.IS_BUYER,
				eseSystem.getPreferences().get(ESESystem.ENABLE_BUYER) != null
						&& !StringUtil.isEmpty(eseSystem.getPreferences().get(ESESystem.ENABLE_BUYER))
								? eseSystem.getPreferences().get(ESESystem.ENABLE_BUYER) : "");
		resp.put(TransactionProperties.DISTRIBUTION_IMAGE_AVILABLE,
				eseSystem.getPreferences().get(ESESystem.ENABLE_DISTRIBUTION_IMAGE) != null
						&& !StringUtil.isEmpty(eseSystem.getPreferences().get(ESESystem.ENABLE_DISTRIBUTION_IMAGE))
								? eseSystem.getPreferences().get(ESESystem.ENABLE_DISTRIBUTION_IMAGE) : "");
		resp.put(TransactionProperties.DIGITAL_SIGNATURE,
				eseSystem.getPreferences().get(ESESystem.ENABLE_DIGITAL_SIGNATURE) != null
						&& !StringUtil.isEmpty(eseSystem.getPreferences().get(ESESystem.ENABLE_DIGITAL_SIGNATURE))
								? eseSystem.getPreferences().get(ESESystem.ENABLE_DIGITAL_SIGNATURE) : "");
		resp.put(TransactionProperties.CROP_CALENDAR,
				eseSystem.getPreferences().get(ESESystem.ENABLE_CROP_CALENDAR) != null
						&& !StringUtil.isEmpty(eseSystem.getPreferences().get(ESESystem.ENABLE_CROP_CALENDAR))
								? eseSystem.getPreferences().get(ESESystem.ENABLE_CROP_CALENDAR) : "");
		resp.put(TransactionProperties.WAREHOUSEDOWNLOAD_SEASON,
				eseSystem.getPreferences().get(ESESystem.ENABLE_WAREHOUSEDOWNLOAD_SEASON) != null && !StringUtil
						.isEmpty(eseSystem.getPreferences().get(ESESystem.ENABLE_WAREHOUSEDOWNLOAD_SEASON))
								? eseSystem.getPreferences().get(ESESystem.ENABLE_WAREHOUSEDOWNLOAD_SEASON) : "");
		return resp;
	}

	@Override
	public Map<?, ?> processJson(Map reqData) {
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		String agentId = head.getAgentId();
		String serialNo = head.getSerialNo();

		if (head.getAgentId().equals("nhtstest123")) {

			return formEmptyResp();
		}

		Device device = utilService.findDeviceBySerialNumber(serialNo);
		Agent agent = utilService.findAgentByProfileAndBranchId(agentId, device.getBranchId());
		Map resp = respMap(reqData, agent, device, true);
		reqData.put("agentObj", agent);
		reqData.put("seasonObj", resp.get(TxnEnrollmentProperties.CURRENT_SEASON_CODE_LOGIN));
		Map groupResponse = new LinkedHashMap<>();
		Gson gss = new Gson();
		groupResponse.put(TxnEnrollmentProperties.AGENT_LOGIN_RESP_KEY, resp);
		if (branch != null) {
			head.setBranchId(branch);
			reqData.put(TransactionProperties.HEAD, head);
		}
		groupDownloadTransactionsJson(reqData, groupResponse);
		/** UPDATE AGENT EOD STATUS **/
		utilService.updateAgentBODStatus(agent.getProfileId(), ESETxn.BOD);
		String androidVer = (String) reqData.get(TxnEnrollmentProperties.ANDROID_VERSION);
		String mobileModel = (String) reqData.get(TxnEnrollmentProperties.MOBILE_MODEL);
		device.setAndroidVer(androidVer);
		device.setMobileModel(mobileModel);
		if (device.getExporter() == null || ObjectUtil.isEmpty(device.getExporter())) {
			ExporterRegistration ex;
			if (agent.getPackhouse() != null && agent.getPackhouse().getExporter() != null
					&& !ObjectUtil.isEmpty(agent.getPackhouse().getExporter()))
				ex = utilService.findExportRegById(agent.getPackhouse().getExporter().getId());
			else
				ex = utilService.findExportRegById(agent.getExporter().getId());
			device.setExporter(ex);
		}
		utilService.updateDevice(device);
		return groupResponse;
	}

	private String getFarmerIdSeq(Agent agent) {

		String farmerIdCurrIdSeq = "";
		String farmerAllotResIdSeq = "";
		String farmerAllotIdLimit = "";
		long farmerMaxRange = Farmer.FARMER_ID_MAX_RANGE;
		String farmerSeqLength = utilService.findPrefernceByName(ESESystem.FARMER_ID_LENGTH);
		String farmerMaxRanges = utilService.findPrefernceByName(ESESystem.FARMER_MAX_RANGE);
		if (farmerMaxRanges != null && !farmerMaxRanges.isEmpty()) {

			farmerMaxRange = Long.valueOf(farmerMaxRanges);
		}

		if (!ObjectUtil.isEmpty(agent)) {

			long famerCurrSeqId = StringUtil.isEmpty(agent.getFarmerCurrentIdSeq()) ? 0
					: Long.parseLong(agent.getFarmerCurrentIdSeq());
			long famerAllotSeqId = StringUtil.isEmpty(agent.getFarmerAllotedIdSeq()) ? 0
					: Long.parseLong(agent.getFarmerAllotedIdSeq());
			long maxIndex = famerAllotSeqId + Farmer.FARMER_SEQ_RANGE;

			if ((famerCurrSeqId == 0)
					|| (famerCurrSeqId == maxIndex && StringUtil.isEmpty(agent.getFarmerAllotedResIdSeq()))) {

				if (farmerSeqLength == null || farmerSeqLength.isEmpty()) {
					farmerIdCurrIdSeq = idGenerator.getFarmerRemoteIdSeq();

				} else {
					farmerIdCurrIdSeq = idGenerator.getFarmerRemoteIdSeq(Integer.valueOf(farmerSeqLength),
							farmerMaxRange);
				}

				agent.setFarmerCurrentIdSeq(farmerIdCurrIdSeq);
				agent.setFarmerAllotedIdSeq(farmerIdCurrIdSeq);
				agent.setFarmerAllotedResIdSeq(null);
			} else {
				long reserveIndex = maxIndex - Farmer.FARMER_RESERVE_INDEX;

				if (famerCurrSeqId > reserveIndex && StringUtil.isEmpty(agent.getFarmerAllotedResIdSeq())) {
					if (farmerSeqLength == null || farmerSeqLength.isEmpty()) {
						String newIdSeq = idGenerator.getFarmerRemoteIdSeq();
						agent.setFarmerAllotedResIdSeq(newIdSeq);

					} else {
						String newIdSeq = idGenerator.getFarmerRemoteIdSeq(Integer.valueOf(farmerSeqLength),
								farmerMaxRange);
						agent.setFarmerAllotedResIdSeq(newIdSeq);

					}

				} else {

					if (!StringUtil.isEmpty(agent.getFarmerAllotedResIdSeq())) {
						long reserveMaxIndex = Long.parseLong(agent.getFarmerAllotedResIdSeq())
								+ Farmer.FARMER_SEQ_RANGE;
						if (famerCurrSeqId == reserveMaxIndex) {
							if (farmerSeqLength == null || farmerSeqLength.isEmpty()) {
								farmerIdCurrIdSeq = idGenerator.getFarmerRemoteIdSeq();

							} else {
								farmerIdCurrIdSeq = idGenerator.getFarmerRemoteIdSeq(Integer.valueOf(farmerSeqLength),
										farmerMaxRange);
							}
							agent.setFarmerCurrentIdSeq(farmerIdCurrIdSeq);
							agent.setFarmerAllotedIdSeq(farmerIdCurrIdSeq);
							agent.setFarmerAllotedResIdSeq(null);
						} else if (famerCurrSeqId > maxIndex) {
							agent.setFarmerCurrentIdSeq(String.valueOf(famerCurrSeqId));
							agent.setFarmerAllotedIdSeq(agent.getFarmerAllotedResIdSeq());
							agent.setFarmerAllotedResIdSeq(null);
						}
					}
				}
			}
			farmerIdCurrIdSeq = StringUtil.isEmpty(agent.getFarmerCurrentIdSeq()) ? "0" : agent.getFarmerCurrentIdSeq();
			farmerAllotResIdSeq = StringUtil.isEmpty(agent.getFarmerAllotedResIdSeq()) ? "0"
					: agent.getFarmerAllotedResIdSeq();

			long allotSeqId = StringUtil.isEmpty(agent.getFarmerAllotedIdSeq()) ? 0
					: Long.parseLong(agent.getFarmerAllotedIdSeq());
			if (allotSeqId > 0) {
				long allotIdSeqLimit = allotSeqId + Farmer.FARMER_SEQ_RANGE;
				if (allotIdSeqLimit >= farmerMaxRange) {
					allotIdSeqLimit = farmerMaxRange;
				}
				farmerAllotIdLimit = String.valueOf(allotIdSeqLimit);
			}

		}
		utilService.editAgent(agent);
		return farmerIdCurrIdSeq + "|" + farmerAllotResIdSeq + "|" + farmerAllotIdLimit;
	}

	private Map formEmptyResp() {
		Map resp = new LinkedHashMap<>();
		Map groupResponse = new LinkedHashMap<>();
		resp.put(TxnEnrollmentProperties.AGENT_NAME, "nhtstest123");
		resp.put(TransactionProperties.DEVICE_ID, "");
		resp.put(TransactionProperties.SYNC_TIME_STAMP, "");
		resp.put(TransactionProperties.DISPLAY_TS_FORMAT, "");
		resp.put(TransactionProperties.SERVICE_POINT_ID, "");
		resp.put(TransactionProperties.SERVICE_POINT_NAME, "");
		resp.put(TransactionProperties.BALANCE, "");
		resp.put(TxnEnrollmentProperties.CLIENT_ID_SEQ, "1000|0|1000");

		resp.put(TransactionProperties.AGENT_TYPE, "02");
		resp.put(TransactionProperties.EXPORTER_NAME, "");
		resp.put(TransactionProperties.EXPORTER_CODE, "");

		resp.put(TransactionProperties.PACKHOUSE_ID, "");
		resp.put(TransactionProperties.PACKHOUSE_NAME, "");
		resp.put(TransactionProperties.PACKHOUSE_CODE, "");
		resp.put(TransactionProperties.EXPORTER_LICENSE, "");

		resp.put(TransactionProperties.TARE_WEIGHT, "");
		resp.put(TxnEnrollmentProperties.CURRENT_SEASON_CODE_LOGIN, "");
		resp.put(TransactionProperties.DISPLAY_TXN_DATE_FORMAT, DateUtil.PROFILE_DATE_FORMAT);
		resp.put(TransactionProperties.IS_GENERIC, "");
		resp.put(TransactionProperties.GENERAL_DATE_FORMAT, "");
		resp.put(TransactionProperties.IS_BATCH_AVAIL, "");
		resp.put(TxnEnrollmentProperties.IS_GRAMPANCHAYAT, "");
		resp.put(TxnEnrollmentProperties.IS_FIELDSTAFF_TRACKING, "");
		resp.put(TransactionProperties.CURRENCY, "");
		resp.put(TransactionProperties.AREA_TYPE, "");
		resp.put(TxnEnrollmentProperties.SAMITHI_LIST, "");
		resp.put(TxnEnrollmentProperties.FARMER_DOWNLOAD_REVISION_NO, 0);
		resp.put(TxnEnrollmentProperties.FARM_DOWNLOAD_REVISION_NO, 0);
		resp.put(TxnEnrollmentProperties.FARM_CROPS_DOWNLOAD_REVISION_NO, 0);
		resp.put(TxnEnrollmentProperties.CUSTOMER_PROJECT_REVISION_NO, 0);
		resp.put(TxnEnrollmentProperties.PRODUCTS_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.SEASON_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.LOCATION_DOWNLOAD_REVISION_NO, "0");
		resp.put(TransactionProperties.WAREHOUSE_PRODUCT_STOCK_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.VILLAGE_WAREHOUSE_STOCK_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.FARMER_OUTSTANDING_BALANCE_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.PROCUREMENT_PRODUCT_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.BUYER_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.CATALOGUE_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.COOPERATIVE_DOWNLOAD_REVISION_NO, "0");
		resp.put(TransactionProperties.SUPPLIER_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.DYNAMIC_COMPONENT_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.TRAINING_CRITERIA_CATEGORY_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.TRAINING_CRITERIA_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.PLANNER_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.FARMER_OUTSTANDING_BALANCE_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.VILLAGE_WAREHOUSE_STOCK_DOWNLOAD_REVISION_NO, "0");
		resp.put(TxnEnrollmentProperties.AGENT_REVISION_NO, 0);
		resp.put(TxnEnrollmentProperties.INTERESRT_RATE_APPLICAPLE, "");
		resp.put(TxnEnrollmentProperties.RATE_OF_INTEREST, "");
		resp.put(TxnEnrollmentProperties.EFFECTIVE_FROM, "");
		resp.put(TxnEnrollmentProperties.IS_APPLICAPLE_EX_FARMER, "");
		resp.put(TxnEnrollmentProperties.PREVIOUS_INTEREST_RATE, "");
		resp.put(TxnEnrollmentProperties.IS_QR_CODE_SEARCH_REQUIRED, "");
		resp.put(TransactionProperties.BRANCH_ID, "");
		resp.put(TransactionProperties.PARENT_BRANCH_ID, "");
		resp.put(TxnEnrollmentProperties.REMOTE_APK_VERSION, "");
		resp.put(TxnEnrollmentProperties.REMOTE_CONFIG_VERSION, "");
		resp.put(TxnEnrollmentProperties.REMOTE_DB_VERSION, "");
		HttpServletRequest httserreq = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String url = httserreq.getRequestURL().toString();
		try {
			URL aURL;
			aURL = new URL(url);
			String path = aURL.getPath();
			String fullPath[] = path.split("/", 0);
			String urll = aURL.getProtocol() + "://" + aURL.getAuthority() + "/"
					+ "nhts/traceabilityViewReport_list.action?layoutType=publiclayout&batchNo=";
			resp.put(TxnEnrollmentProperties.SHIPMENT_URL, urll);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		resp.put("ag_days", "");

		resp.put("p_age", "");
		resp.put("p_rem", "");

		resp.put(TxnEnrollmentProperties.AREA_CAPTURE_MODE, "");
		resp.put(TxnEnrollmentProperties.GEO_FENCING_FLAG, "");
		resp.put(TxnEnrollmentProperties.GEO_FENCING_RADIUS_MT, "");
		resp.put(TransactionProperties.FTP_PASSWORD, "");
		resp.put(TransactionProperties.FTP_URL, "");
		resp.put(TransactionProperties.FTP_USERNAME, "");
		resp.put(TransactionProperties.FTP_VIDEO_PATH, "");
		resp.put(TransactionProperties.IS_BUYER, "");
		resp.put(TransactionProperties.DISTRIBUTION_IMAGE_AVILABLE, "");
		resp.put(TransactionProperties.DIGITAL_SIGNATURE, "");
		resp.put(TransactionProperties.CROP_CALENDAR, "");
		resp.put(TransactionProperties.WAREHOUSEDOWNLOAD_SEASON, "");
		groupResponse.put(TxnEnrollmentProperties.AGENT_LOGIN_RESP_KEY, resp);
		groupEmptyDownloadTransactionsJson(groupResponse);

		return groupResponse;
	}

	private void groupEmptyDownloadTransactionsJson(Map res) {

		if (!ObjectUtil.isEmpty(txnMap)) {
			for (Map.Entry txnAdapterEntry : txnMap.entrySet()) {
				ITxnAdapter txnAdapter = (ITxnAdapter) txnAdapterEntry.getValue();
				if (!ObjectUtil.isEmpty(txnAdapter)) {
					Map txnAdapterResp = new HashMap<>();
					res.put(txnAdapterEntry.getKey(), txnAdapterResp);
				}
			}
		}
	}
}
