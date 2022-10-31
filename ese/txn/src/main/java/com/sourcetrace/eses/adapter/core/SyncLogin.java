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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.ESETxn;

import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.property.TxnEnrollmentProperties;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;

/**
 * The Class AgentLogin.
 */
public class SyncLogin implements ITxnAdapter {

	SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.SYNC_DATE_TIME);
	private static final Logger LOGGER = Logger.getLogger(SyncLogin.class.getName());
	@Autowired
	private IUtilService utilService;
	@Autowired
	private IFarmerService farmerService;
	private Map<String, ITxnAdapter> txnMap;

	@SuppressWarnings("unchecked")

	public Map<String, ITxnAdapter> getTxnMap() {

		return txnMap;
	}

	/**
	 * Sets the txn map.
	 * 
	 * @param txnMap
	 *            the txn map
	 */
	public void setTxnMap(Map<String, ITxnAdapter> txnMap) {

		this.txnMap = txnMap;
	}

	@Override
	public Map<?, ?> processJson(Map reqData) {

		Map groupResponse = new LinkedHashMap<>();
		Map resp = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		String agentId = head.getAgentId();
		String serialNo = head.getSerialNo();
		List<String> lognTxns = new ArrayList<>();
		Long farmerRevisionNo = 0l;
		Long farmRevisionNo = 0l;
		Long farmCropsRevisionNo = 0l;
		ESESystem eseSystem = utilService.findPrefernceById("1");
		Agent agent = utilService.findAgentByProfileAndBranchId(agentId, head.getBranchId());
		String seasonCode = utilService.findCurrentSeasonCodeByBranchId(head.getBranchId());
		farmerRevisionNo = farmerService.findActiveContractFarmersLatestRevisionNoByAgentAndSeason(agent.getId(),
				seasonCode);

		farmRevisionNo = farmerService.listFarmFieldsByFarmerIdByAgentIdAndSeasonRevisionNo(agent.getId());

		

		resp.put(TxnEnrollmentProperties.FARMER_DOWNLOAD_REVISION_NO, farmerRevisionNo);
		resp.put(TxnEnrollmentProperties.FARM_DOWNLOAD_REVISION_NO, farmRevisionNo);
		resp.put(TxnEnrollmentProperties.FARM_CROPS_DOWNLOAD_REVISION_NO, farmCropsRevisionNo);
		resp.put(TxnEnrollmentProperties.AGENT_REVISION_NO, agent.getRevisionNumber());
		resp.put(TxnEnrollmentProperties.CURRENT_SEASON_CODE_LOGIN, seasonCode);

		groupResponse.put(TxnEnrollmentProperties.AGENT_LOGIN_RESP_KEY, resp);
		reqData.put("agentObj", agent);
		reqData.put("seasonObj", seasonCode);
		groupDownloadTransactionsJson(reqData, groupResponse);

		utilService.updateAgentBODStatus(agent.getProfileId(), ESETxn.BOD);
		return groupResponse;

	}

	@SuppressWarnings("unchecked")
	private void groupDownloadTransactionsJson(Map req, Map res){

		if (!ObjectUtil.isEmpty(txnMap)) {
			for (Map.Entry txnAdapterEntry : txnMap.entrySet()) {
				ITxnAdapter txnAdapter = (ITxnAdapter) txnAdapterEntry.getValue();
				if (!ObjectUtil.isEmpty(txnAdapter)) {
					LOGGER.info(txnAdapterEntry.getKey());
					Map txnAdapterResp = txnAdapter.processJson(req);
					res.put(txnAdapterEntry.getKey(), txnAdapterResp);
				}
			}
		}
	}
}
