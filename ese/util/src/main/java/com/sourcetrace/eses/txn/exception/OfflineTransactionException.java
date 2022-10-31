/*
 * OfflineTransactionException.java
 * Copyright (c) 2012-2013, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.txn.exception;

import java.io.IOException;
import java.util.Properties;


public class OfflineTransactionException extends Exception {

	private static final long serialVersionUID = -3531038877501472686L;

	private String error;

	/**
	 * Instantiates a new offline transaction exception.
	 * 
	 * @param errorCode
	 *            the error code
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public OfflineTransactionException(String errorCode) throws IOException {
		SwitchException switchException =new SwitchException();
		Properties errors = new Properties();
		errors.load(switchException.getClass().getResourceAsStream(
				"transactionError.properties"));
		setError(errors.getProperty(errorCode));
	}

	/**
	 * Sets the error.
	 * 
	 * @param error
	 *            the new error
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * Gets the error.
	 * 
	 * @return the error
	 */
	public String getError() {
		return error;
	}

}
