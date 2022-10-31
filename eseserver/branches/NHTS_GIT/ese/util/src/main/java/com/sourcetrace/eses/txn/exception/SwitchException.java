/*
\ * SwitchException.java
 * Copyright (c) 2008-2009, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.txn.exception;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sourcetrace.eses.interceptor.ITxnErrorCodes;

/**
 * SwitchException is a system exception that defines error codes and sets message for the given
 * code. It is also used to log the exception at source and once.
 * @author $Author: moorthy $
 * @version $Rev: 175 $, $Date: 2010-06-01 17:29:07 +0530 (Tue, 01 Jun 2010) $
 */
public class SwitchException extends RuntimeException implements ITxnErrorCodes {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(SwitchException.class);
    protected static boolean initialized = false;
    protected static Properties errors;
    private String code;
    private String message;
    private Throwable cause;
    private boolean logged;
    private long txnLogId;
    private String txnCode;
    
    public String getTxnCode() {
		return txnCode;
	}

	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}

	public long getTxnLogId() {
		return txnLogId;
	}

	public void setTxnLogId(long txnLogId) {
		this.txnLogId = txnLogId;
	}
    static {
        errors = new Properties();
    }

    /**
     * Instantiates a new eSE exception.
     */
    public SwitchException() {

        if (!initialized) {
            init();
        }
    }

    /**
     * Instantiates a new eSE exception.
     * @param code the code
     */
    public SwitchException(String code) {

        if (!initialized) {
            init();
        }

        this.code = code;
        message = (String) errors.get(code);
        log();
    }
    
    public SwitchException(String code,long txnLogId) {
    	this.setTxnLogId(txnLogId);
        if (!initialized) {
            init();
        }

        this.code = code;
        message = (String) errors.get(code);
        log();
    }
    
    public SwitchException(String code,long txnLogId,String txnCode) {
    	this.setTxnLogId(txnLogId);
    	this.setTxnCode(txnCode);
        if (!initialized) {
            init();
        }

        this.code = code;
        message = (String) errors.get(code);
        log();
    }

    /**
     * Instantiates a new eSE exception.
     * @param code the code
     * @param message the message
     */
    public SwitchException(String code, String message) {

        if (!initialized) {
            init();
        }

        this.code = code;
        this.message = message;
        log();
    }
    
    public SwitchException(String code, String message,long txnLogId) {
    	this.setTxnLogId(txnLogId);
        if (!initialized) {
            init();
        }

        this.code = code;
        this.message = message;
        log();
    }
    
    public SwitchException(String code, String message,long txnLogId,String txnCode) {
    	this.setTxnLogId(txnLogId);
    	this.setTxnCode(txnCode);
        if (!initialized) {
            init();
        }

        this.code = code;
        this.message = message;
        log();
    }
    
    /**
     * Instantiates a new eSE exception.
     * @param code the code
     * @param message the message
     */
    public SwitchException(String code, String message,String desc) {

        if (!initialized) {
            init();
        }

        this.code = code;
        this.message = (String) errors.get(code);
        this.message=this.message+"-"+desc;
        log();
    }


    /**
     * Instantiates a new eSE exception.
     * @param code the code
     * @param cause the cause
     */
    public SwitchException(String code, Throwable cause) {

        if (!initialized) {
            init();
        }

        this.cause = cause;
        this.code = code;
        message = (String) errors.get(code);
        log();
    }

    /**
     * Instantiates a new eSE exception.
     * @param cause the cause
     */
    public SwitchException(Throwable cause) {

        if (!initialized) {
            init();
        }

        this.cause = cause;
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
     * Gets the code.
     * @return the code
     */
    public String getCode() {

        return code;
    }

    /**
     * Sets the code.
     * @param code the new code
     */
    public void setCode(String code) {

        this.code = code;
    }

    /**
     * Gets the message.
     * @return the message
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage() {

        return message;
    }

    /**
     * Sets the message.
     * @param message the new message
     */
    public void setMessage(String message) {

        this.message = message;
    }

    /**
     * Gets the cause.
     * @return the cause
     * @see java.lang.Throwable#getCause()
     */
    public Throwable getCause() {

        return cause;
    }

    /**
     * Sets the cause.
     * @param cause the new cause
     */
    public void setCause(Throwable cause) {

        this.cause = cause;
    }

    /**
     * Inits the.
     * @see com.ese.excep.SwitchException#init()
     */
    public void init() {

        loadSwitchErrorCodes();
        initialized = true;
    }

    /**
     * Load switch error codes.
     * @see com.sourcetrace.eses.txn.exception.SwitchErrorCodes#loadSwitchErrorCodes()
     */
    public void loadSwitchErrorCodes() {

        try {
            errors.load(SwitchException.class.getResourceAsStream("transactionError.properties"));
        } catch (IOException e) {
            LOGGER.error("Error reading error codes and messages, transactionError.properties");
        }
    }
}
