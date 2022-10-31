/*
 * LicenseKeyGenerator.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.util;

public class LicenseKeyGenerator implements ILicenseKeyGenerator {

    private ICryptoUtil tripleDES;

    /*
     * (non-Javadoc)
     * @see com.sourcetrace.util.ILicenseKeyGenerator#generate(java.lang.String)
     */
    public String generate(String text) {

        String key = tripleDES.encrypt(StringUtil.getMulipleOfEight(text));
        return key;
    }

    /**
     * Sets the triple des.
     * @param tripleDES the new triple des
     */
    public void setTripleDES(ICryptoUtil tripleDES) {

        this.tripleDES = tripleDES;
    }

    /**
     * Gets the triple des.
     * @return the triple des
     */
    public ICryptoUtil getTripleDES() {

        return tripleDES;
    }

}
