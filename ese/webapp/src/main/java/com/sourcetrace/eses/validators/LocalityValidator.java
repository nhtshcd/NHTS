/**
 * LocalityValidator.java
 * Copyright (c) 2008-2009, SourceTrace Systems, All Rights Reserved.
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
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.IValidator;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.dao.IUtilDAO;
import com.sourcetrace.eses.dao.UtilDAO;
import com.sourcetrace.eses.entity.Country;
import com.sourcetrace.eses.entity.Locality;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;



/**
 * The Class LocalityValidator.
 */
public class LocalityValidator implements IValidator {

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(LocalityValidator.class);
    private static final String COUNTRY_EMPTY ="emptyCountry";
    private static final String COUNTRY_EMPTY_PROPERTY ="empty.country";
    private static final String COUNTRY_NOT_FOUND="countryNotFound";
    private static final String COUNTRY_NOT_FOUND_PROPERTY="country.notfound";
    private static final String STATE_NOT_FOUND="stateNotFound";
    private static final String STATE_NOT_FOUND_PROPERTY="state.notfound";

    @Autowired
  	private IUtilDAO utilDAO;

     /**
     * Validate.
     * @param object the object
     * @return the map< string, string>
     * @see com.ese.view.validator.IValidator#validate(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> validate(Object object) {

        ClassValidator localityValidator = new ClassValidator(Locality.class);
        ClassValidator stateValidator = new ClassValidator(State.class);
        Locality aLocality = (Locality) object;

        Map<String, String> errorCodes = new LinkedHashMap<String, String>();
        if (logger.isInfoEnabled()) {
            logger.info("validate(Object) " + aLocality.toString());
        }
        InvalidValue[] values = null;
      //To Check Country empty or not
        if(StringUtil.isEmpty(aLocality.getState().getCountry().getName())){
            errorCodes.put(COUNTRY_EMPTY,COUNTRY_EMPTY_PROPERTY);
        }else{
            // Check whether country name is exists
            Country country = utilDAO.findCountryByName(aLocality.getState().getCountry().getName());
            if(ObjectUtil.isEmpty(country))
                errorCodes.put(COUNTRY_NOT_FOUND, COUNTRY_NOT_FOUND_PROPERTY);
        }
        
       /* if (ObjectUtil.isEmpty(aLocality.getState())) {
            errorCodes.put("state", "empty.state");
        }*/
        
        values = localityValidator.getInvalidValues(aLocality, "state");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }
       
        
        if(StringUtil.isEmpty(aLocality.getState().getCode())){
        	 errorCodes.put("state", "empty.state");
        }else{
            // Check whether state name is exists
            State state = utilDAO.findStateByCode(aLocality.getState().getCode());
            if(ObjectUtil.isEmpty(state))
                errorCodes.put(STATE_NOT_FOUND, STATE_NOT_FOUND_PROPERTY);
        }
        
        
        
       /* if (aLocality.getState() != null) {
            values = stateValidator.getInvalidValues(aLocality.getState(), "name");
            for (InvalidValue value : values) {
                errorCodes.put("state", "empty.state");
            }
            
            if(ObjectUtil.isEmpty(values)|| (!ObjectUtil.isEmpty(values) && values.length==0)){
                State state = locationDAO.findStateByName(aLocality.getState().getName());
                if(ObjectUtil.isEmpty(state))
                    errorCodes.put(STATE_NOT_FOUND, STATE_NOT_FOUND_PROPERTY);
            }
        }*/

        values = localityValidator.getInvalidValues(aLocality, "name");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }
        
        if(!StringUtil.isEmpty(aLocality.getName())){
        	if(!ValidationUtil.isPatternMaches(aLocality.getName(), ValidationUtil.NAME_PATTERN)){
        		errorCodes.put("name","pattern.name");
        	}
        }else{
        	errorCodes.put("name","empty.name");
        }
        
        if (values == null || values.length == 0) {
            Locality eLocality = utilDAO.findLocalityByName((aLocality.getName()));
            if (eLocality != null && aLocality.getId() != eLocality.getId()) {
                errorCodes.put("name", "unique.LocalityName");
            }

        }
       
      
        return errorCodes;
    }

}
