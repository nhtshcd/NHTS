/*
 * TxnProcessServiceImpl.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.appengine.repackaged.com.google.gson.Gson;
import com.sourcetrace.eses.entity.Transaction;
import com.sourcetrace.eses.entity.TransactionLog;
import com.sourcetrace.eses.interceptor.ITxnMessageUtil;
import com.sourcetrace.eses.property.TxnEnrollmentProperties;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.exception.TxnFault;
import com.sourcetrace.eses.txn.schema.JsonRequest;
import com.sourcetrace.eses.txn.schema.Response;
import com.sourcetrace.eses.txn.schema.Status;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

// TODO: Auto-generated Javadoc

@RestController
@RequestMapping("/api")
public class TxnProcessServiceImpl {

	private static final Logger LOGGER = Logger.getLogger(TxnProcessServiceImpl.class.getName());
	public static final String TXN_ID = "txnId";
	public static final String SERVER_ERROR = "SERVER_ERROR";

	private Map<String, ITxnAdapter> txnAdapterMap;

	private Transaction transaction;

	@Autowired
	private IUtilService utilService;

	/**
	 * Set txn adapter map.
	 * 
	 * @param txnAdapterMap
	 *            the txn adapter map
	 */
	public void setTxnAdapterMap(Map<String, ITxnAdapter> txnAdapterMap) {

		this.txnAdapterMap = txnAdapterMap;
	}

	/**
	 * Gets the txn adapter.
	 * 
	 * @param txnType
	 *            the txn type
	 * @return the txn adapter
	 */
	public ITxnAdapter getTxnAdapter(String txnType) {

		LOGGER.info("Txn Type " + txnType);
		return txnAdapterMap.get(txnType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sourcetrace.eses.service.ITxnProcessService#processRequest(com.
	 * sourcetrace.eses.txn. schema .Request)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/processTxnRequest", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody Response processJson(@RequestBody JsonRequest request) {
		HttpServletRequest httserreq = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		request.setTxnLogId(
				httserreq.getAttribute("txnLogId") != null && StringUtil.isLong(httserreq.getAttribute("txnLogId"))
						? Long.valueOf(httserreq.getAttribute("txnLogId").toString()) : 0);
		Map reqData = new HashMap();
		Map respData = new HashMap();
		Status status = new Status();
		status.setCode("00");
		status.setMessage("SUCCESS");
		Response res = new Response();
		ITxnAdapter txnAdapter = getTxnAdapter(request.getHead().getTxnType());
		if (!ObjectUtil.isEmpty(txnAdapter)) {
			try {
				if (!ObjectUtil.isEmpty(request.getBody()))
					reqData = request.getBody();

				reqData.put(ITxnMessageUtil.HEAD, request.getHead());

				if (request.getHead().getTxnType().equals("500")) {
					respData = processDynamicTxn(reqData, request.getHead().getTxnType());
				} else {
					respData = txnAdapter.processJson(reqData);
				}
				res.setBody(respData != null && !ObjectUtil.isEmpty(respData) ? respData : new HashMap());

				updateTransactionLog(request.getTxnLogId(), status, Transaction.Status.SUCCESS.ordinal());
			} catch (Exception exception) {
				String exceptionDetailMsg = exception.getMessage();
				if (exception instanceof TxnFault) {
					TxnFault txnFault = (TxnFault) exception;
					status.setCode(txnFault.getCode());
					status.setMessage(txnFault.getMessage());
				} else {
					status.setCode(SERVER_ERROR);
					String exceptionDetailLog = exception.getMessage();
					if (StringUtil.isEmpty(exceptionDetailLog) || exceptionDetailLog.equalsIgnoreCase("null")) {
						exceptionDetailMsg = org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(exception);
						exceptionDetailLog = exception.toString();
					}
					status.setMessage(exceptionDetailLog);
				}
				updateTransactionLog(request.getTxnLogId(), status, Transaction.Status.FAILED.ordinal());
				exception.printStackTrace();
				throw new TxnFault(status.getCode(), status.getMessage(), request.getTxnLogId());

			}
		}else{
			status.setCode("411");
			status.setMessage("TXN TYPE UNVAIALBLE");
			updateTransactionLog(request.getTxnLogId(), status, Transaction.Status.FAILED.ordinal());
			throw new TxnFault(status.getCode(), status.getMessage(), request.getTxnLogId());
		}
		res.setStatus(status);
		return res;

	}

	private Map processDynamicTxn(Map reqData, String txnTypeId) {

		String txnType = (reqData.containsKey(TxnEnrollmentProperties.DYNAMIC_TXN_ID))
				? (String) reqData.get(TxnEnrollmentProperties.DYNAMIC_TXN_ID) : "";
		ITxnAdapter txnAdapter = getTxnAdapter(txnTypeId);
		if (txnType.contains("-")) {
			txnAdapter = getTxnAdapter("501");
		}
		return (txnAdapter.processJson(reqData));
	}

	private void updateTransactionLog(long txnLogId, Status status, int txnSts) {

		try {
			TransactionLog transactionLog = utilService.findTransactionLogById(txnLogId);
			if (!ObjectUtil.isEmpty(transactionLog)) {
				transactionLog.setStatus(txnSts);
				transactionLog.setStatusCode(status.getCode());
				transactionLog.setStatusMsg(status.getMessage());
				utilService.updateTransactionLog(transactionLog);
			}
		} catch (Exception e) {
			LOGGER.info("Exception : " + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/validatePhMob", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody Map<String, String> otherService(@RequestBody String request) {
		JSONObject jss = new Gson().fromJson(request, JSONObject.class);
		String phno = (String) jss.get("phone");
		String nid = (String) jss.get("nid");
		Object[] isExist = utilService.findIfFarmerExist(phno, nid);
		Map res = new HashMap<>();
		if (isExist == null || isExist.length == 0) {
			res.put("status", "00");
			res.put("msg", "success");

		} else if (isExist != null && isExist.length == 2) {
			if (isExist[0] != null && Arrays.asList(isExist[0].toString().split(",")).contains(phno)) {
				res.put("status", "01");
				res.put("msg", "phone");
			}

			if (isExist[1] != null && Arrays.asList(isExist[1].toString().split(",")).contains(nid)) {
				if (res.containsKey("status")) {
					res.put("status", "03");
					res.put("msg", "all");
				} else {
					res.put("status", "02");
					res.put("msg", "nid");
				}

			}

		}
		return res;
	}
}
