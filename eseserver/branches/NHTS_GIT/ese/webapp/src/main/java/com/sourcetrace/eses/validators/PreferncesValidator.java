/*
 * PreferncesValidator.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.validators;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.validator.IValidator;

import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.util.StringUtil;

/**
 * The Class PreferncesValidator.
 */
public class PreferncesValidator implements IValidator {

    private static final Logger logger = Logger.getLogger(PreferncesValidator.class);
    static final String ValidateDigits = "/[^0-9]/g";
    static final String ValidateSpChar = "/[a-zA-Z0-9]/g";
    static final String ValidateChar = "/[^a-zA-Z]/g";

    /*
     * (non-Javadoc)
     * @see com.ese.view.validator.IValidator#validate(java.lang.Object)
     */
    /**
     * @see com.ese.view.validator.IValidator#validate(java.lang.Object)
     */
    @Override
    public Map<String, String> validate(Object object) {

        Map<String, String> preferencesMap = (Map<String, String>) object;
        Map<String, String> errorCodes = new LinkedHashMap<String, String>();
        
        
            try {
                if (StringUtil.isEmpty(preferencesMap.get(ESESystem.INVALID_ATTEMPTS_COUNT))) {
                    errorCodes.put("INVALID_ATTEMPTS_COUNT", "empty." + "INVALID_ATTEMPTS_COUNT");
                } 
                else {
                    Long.parseLong(preferencesMap.get(ESESystem.INVALID_ATTEMPTS_COUNT));
                }
            } catch (Exception e) {
                errorCodes.put("INVALID_ATTEMPTS_COUNT", "invalid." + "INVALID_ATTEMPTS_COUNT");
            }
        
        
            try {
                if (StringUtil.isEmpty(preferencesMap.get(ESESystem.TIME_TO_AUTO_RELEASE))) {
                    errorCodes.put("TIME_TO_AUTO_RELEASE", "empty." + "TIME_TO_AUTO_RELEASE");
                } 
                else {
                    Long.parseLong(preferencesMap.get(ESESystem.TIME_TO_AUTO_RELEASE));
                }
            } catch (Exception e) {
                errorCodes.put("TIME_TO_AUTO_RELEASE", "invalid." + "TIME_TO_AUTO_RELEASE");
            }
        

      
        boolean flag = Boolean.parseBoolean(preferencesMap.get("GEO_FENCING_FLAG"));
        if (flag) {
            try {
                if (StringUtil.isEmpty(preferencesMap.get("GEO_FENCING_RADIUS_MT"))) {
                    errorCodes.put("GEO_FENCING_RADIUS_MT", "empty." + "GEO_FENCING_RADIUS_MT");
                } else {
                    Long.parseLong(preferencesMap.get("GEO_FENCING_RADIUS_MT"));
                }
            } catch (Exception e) {
                errorCodes.put("GEO_FENCING_RADIUS_MT", "invalid." + "GEO_FENCING_RADIUS_MT");
            }
        }
        
    	/*if (StringUtil.isEmpty(preferencesMap.get("CURRENT_SEASON_CODE"))) {
            errorCodes.put("CURRENT_SEASON_CODE", "empty." + "CURRENT_SEASON_CODE");
        }*/
    	
    	/*if (preferencesMap.get("CURRENT_SEASON_CODE").equals("-1")) {
            errorCodes.put("CURRENT_SEASON_CODE", "empty." + "CURRENT_SEASON_CODE");
        }*/

        return errorCodes;

    }

}
