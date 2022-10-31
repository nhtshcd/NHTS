/*
 * IUsageValidator.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.filter;

/**
 * The Interface IUsageValidator.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface IUsageValidator {

    /**
     * Validate.
     */
    public void validate();

    /**
     * Validate.
     * @param key the key
     */
    public void validate(String key);

    /**
     * Validate license for txn.
     */
    public void validateLicenseForTxn();
}
