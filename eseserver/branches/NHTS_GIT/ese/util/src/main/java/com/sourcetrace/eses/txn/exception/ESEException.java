package com.sourcetrace.eses.txn.exception;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ESEException extends RuntimeException{

	private static final long serialVersionUID = -8131847583757980937L;
    public static final String SUCCESS = "00";
    public static final String PENDING = "01";
    public static final String INVALID_DATA = "10";

    private static final Logger LOGGER = Logger.getLogger(ESEException.class);
    protected static boolean initialized = false;
    protected static Properties errors;
    private String code;
    private String message;
    private Throwable cause;
    private boolean logged;

    static {
        errors = new Properties();
    }

    /**
     * Instantiates a new eSE exception.
     */
    public ESEException() {

        if (!initialized) {
            init();
        }
    }

    /**
     * Instantiates a new eSE exception.
     * @param code the code
     */
    public ESEException(String code) {

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
    public ESEException(String code, String message) {

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
     * @param cause the cause
     */
    public ESEException(String code, Throwable cause) {

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
    public ESEException(Throwable cause) {

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
            LOGGER.error(code + " - " + getMessage());
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
     */
    public void init() {

        try {
            errors.load(ESEException.class.getResourceAsStream("error.properties"));
        } catch (IOException e) {
            LOGGER.error("Error reading error codes and messages, error.properties");
        }

        initialized = true;
    }
    
}
