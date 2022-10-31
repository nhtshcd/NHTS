package com.sourcetrace.eses.txn.exception;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class WebConsoleException extends ESEException implements
		WebConsoleError {

	private static final long serialVersionUID = 2459120633739915222L;


    private static final Logger LOGGER = Logger.getLogger(WebConsoleException.class);

    static {
        errors = new Properties();
        try {
            errors.load(WebConsoleException.class.getResourceAsStream("webError.properties"));
        } catch (IOException e) {
            LOGGER.error("Error reading properties for error codes and their messages");
        }
    }

    /**
     * Instantiates a new web console exception.
     */
    public WebConsoleException() {

        super();
    }

    /**
     * Instantiates a new web console exception.
     * @param code the code
     * @param cause the cause
     */
    public WebConsoleException(String code, Throwable cause) {

        super(code, cause);
    }

    /**
     * Instantiates a new web console exception.
     * @param code the code
     */
    public WebConsoleException(String code) {

        super(code);
    }

    /**
     * Instantiates a new web console exception.
     * @param cause the cause
     */
    public WebConsoleException(Throwable cause) {

        super(cause);
    }
	

}
