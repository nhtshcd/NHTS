package com.sourcetrace.eses.txn.exception;

import org.springframework.security.core.AuthenticationException;

public class UndefinedEntitlementException extends AuthenticationException {

	private static final long serialVersionUID = 3758197279167428153L;

	/**
     * Instantiates a new undefined entitlement exception.
     * @param msg the msg
     */
    public UndefinedEntitlementException(String msg) {
        super(msg);
    }    
}
