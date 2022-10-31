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
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;

/**
 * The Class AgentLogin.
 */
public class AppInitialiser implements ITxnAdapter {

	SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.SYNC_DATE_TIME);
	private static final Logger LOGGER = Logger.getLogger(AppInitialiser.class.getName());
	@Autowired
	private IFarmerService farmerService;
	
	private Map<String, ITxnAdapter> txnMap;

	

	/**
	 * Gets the txn map.
	 * 
	 * @return the txn map
	 */
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
	public Map<?, ?> processJson(Map<?, ?> reqData){
		Map groupResponse = new LinkedHashMap<>();
		Map resp = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		groupDownloadTransactionsJson(reqData, groupResponse);
		
		return groupResponse;
	}
	
	@SuppressWarnings("unchecked")
	private void groupDownloadTransactionsJson(Map req, Map res) {

		if (!ObjectUtil.isEmpty(txnMap)) {
			for (Map.Entry txnAdapterEntry : txnMap.entrySet()) {
				ITxnAdapter txnAdapter = (ITxnAdapter) txnAdapterEntry.getValue();
				if (!ObjectUtil.isEmpty(txnAdapter)) {
					Map txnAdapterResp = txnAdapter.processJson(req);
					res.put(txnAdapterEntry.getKey(), buildCollectionJson(txnAdapterResp));
				}
			}
		}
	}
	

	@SuppressWarnings("unchecked")
	private org.codehaus.jettison.json.JSONObject buildCollectionJson(Map res) {
		return new org.codehaus.jettison.json.JSONObject(res);
	}
}
