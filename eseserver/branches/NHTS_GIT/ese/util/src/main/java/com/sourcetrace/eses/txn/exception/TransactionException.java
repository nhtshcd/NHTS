package com.sourcetrace.eses.txn.exception;

import java.io.IOException;
import java.util.Properties;

import javax.xml.ws.WebFault;

import org.apache.log4j.Logger;

@WebFault(name = "Response", targetNamespace = "http://service.eses.sourcetrace.com/")
public class TransactionException extends RuntimeException{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(TransactionException.class);
    private String code;
    private String message;
    private com.sourcetrace.eses.txn.schema.Response errorResponse;

    private static Properties errors;
    
    static {
        errors = new Properties();
        try {
            errors.load(TransactionException.class.getResourceAsStream("transactionError.properties"));
        } catch (IOException e) {
            logger.error("Error reading error codes and messages, transactionError.properties");
        }
    }
    /**
     * Instantiates a new txn exception.
     * @param code the code
     */

    public TransactionException(String code) {

        init();
        this.code = code;
        message = (String) errors.getProperty(code);
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
     * Inits the.
     */
    public void init() {

      

    }
    
    public com.sourcetrace.eses.txn.schema.Response getFaultInfo() {
        loadDataErrorCodes();
        if(errorResponse == null) {
            errorResponse = new com.sourcetrace.eses.txn.schema.Response();
            com.sourcetrace.eses.txn.schema.Status status=new com.sourcetrace.eses.txn.schema.Status();
            status.setCode(code);
            status.setMessage(message);
            errorResponse.setStatus(status);
        }
        
        return this.errorResponse;
    }

    /**
     * Load data error codes.
     */
    public void loadDataErrorCodes() {

        try {

            errors.load(TransactionException.class.getResourceAsStream("transactionError.properties"));

        } catch (IOException e) {

            logger.error("Error reading error codes and messages,transactionError.properties "
                    + e.getStackTrace());

        }
    }

}
