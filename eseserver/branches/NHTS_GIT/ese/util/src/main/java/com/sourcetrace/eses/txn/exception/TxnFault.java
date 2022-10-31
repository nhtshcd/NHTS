/*
 * TxnFault.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.txn.exception;

import java.io.IOException;
import java.util.Properties;

import javax.xml.ws.WebFault;

import org.apache.log4j.Logger;

import lombok.Getter;
import lombok.Setter;

@WebFault(name = "Response", targetNamespace = "http://service.eses.sourcetrace.com/")
@Getter
@Setter
public class TxnFault extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(TxnFault.class);
	protected static boolean initialized = false;
	protected static Properties errors;
	private String code;
	private String message;
	private String txnCode;
	private boolean logged = false;
	 private long txnLogId;
	public String getTxnCode() {
		return txnCode;
	}

	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}

	static {
		errors = new Properties();
	}

	/**
	 * Instantiates a new eSE exception.
	 */
	public TxnFault() {

		if (!initialized) {
			init();
		}
	}

	/**
	 * Instantiates a new eSE exception.
	 * 
	 * @param code
	 *            the code
	 */
	public TxnFault(String code) {

		if (!initialized) {
			init();
		}

		this.code = code;
		message = (String) errors.get(code);
		log();
	}

	

	/**
	 * Instantiates a new eSE exception.
	 * 
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 */
	public TxnFault(String code, String message) {

		if (!initialized) {
			init();
		}

		this.code = code;
		this.message = message;
		log();
	}
	public TxnFault(String code, String message,Long txnlogId) {

		if (!initialized) {
			init();
		}

		this.code = code;
		this.message = message;
		this.txnLogId=txnlogId;
		log();
	}
	
	public TxnFault(String code,Long txnlogId) {

		if (!initialized) {
			init();
		}

		this.code = code;
		this.message = (String) errors.get(code);;
		this.txnLogId=txnlogId;
		log();
	}
	
	
	


	/**
	 * Log.
	 */
	public void log() {

		if (!logged) {
			LOGGER.error(getMessage());
			logged = true;
		}
	}

	/**
	 * Inits the.
	 * 
	 * @see com.ese.excep.TxnFault#init()
	 */
	public void init() {

		loadSwitchErrorCodes();
		initialized = true;
	}

	/**
	 * Load switch error codes.
	 * 
	 * @see com.sourcetrace.esesw.excep.SwitchErrorCodes#loadSwitchErrorCodes()
	 */
	public void loadSwitchErrorCodes() {

		try {
			errors.load(TxnFault.class.getResourceAsStream("transactionError.properties"));
		} catch (IOException e) {
			LOGGER.error("Error reading error codes and messages, transactionError.properties");
		}
	}

}
