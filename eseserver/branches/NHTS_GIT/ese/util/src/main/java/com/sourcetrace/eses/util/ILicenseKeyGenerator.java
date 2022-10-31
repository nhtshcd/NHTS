/*
 * ILicenseKeyGenerator.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.util;

public interface ILicenseKeyGenerator {

    /**
     * Generate.
     * @param text
     * @return the string
     */
    public String generate(String text);

}
