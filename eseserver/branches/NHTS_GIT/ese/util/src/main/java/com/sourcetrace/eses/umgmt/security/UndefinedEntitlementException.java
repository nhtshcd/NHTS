/*
 * UndefinedEntitlementException.java
 * Copyright (c) 2008-2009, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.umgmt.security;

import org.springframework.security.core.AuthenticationException;

/**
 * The Class UndefinedEntitlementException.
 * @author $Author: aravind $
 * @version $Rev: 1268 $ $Date: 2010-11-24 10:52:01 +0530 (Wed, 24 Nov 2010) $
 */
public class UndefinedEntitlementException extends AuthenticationException {

    /**
     * Instantiates a new undefined entitlement exception.
     * @param msg the msg
     */
    public UndefinedEntitlementException(String msg) {
        super(msg);
    }    
}
