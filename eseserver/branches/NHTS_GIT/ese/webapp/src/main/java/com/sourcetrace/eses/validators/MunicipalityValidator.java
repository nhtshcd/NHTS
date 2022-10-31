/**
 * MunicipalityValidator.java
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

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.IValidator;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.dao.IUtilDAO;
import com.sourcetrace.eses.entity.Country;
import com.sourcetrace.eses.entity.Locality;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;


/**
 * The Class MunicipalityValidator.
 */
public class MunicipalityValidator implements IValidator {

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(MunicipalityValidator.class);
    private static final String COUNTRY_EMPTY = "emptyCountry";
    private static final String COUNTRY_EMPTY_PROPERTY = "empty.country";
    private static final String STATE_EMPTY = "emptyState";
    private static final String STATE_EMPTY_PROPERTY = "empty.state";
    private static final String DISTRICT_EMPTY = "emptyDistrict";
    private static final String DISTRICT_EMPTY_PROPERTY = "empty.district";
    private static final String COUNTRY_NOT_FOUND = "countryNotFound";
    private static final String COUNTRY_NOT_FOUND_PROPERTY = "country.notfound";
    private static final String STATE_NOT_FOUND = "stateNotFound";
    private static final String STATE_NOT_FOUND_PROPERTY = "state.notfound";
    private static final String DISTRICT_NOT_FOUND = "districtNotFound";
    private static final String DISTRICT_NOT_FOUND_PROPERTY = "district.notfound";

    @Autowired
	private IUtilDAO utilDAO;


   
    /**
     * @see com.ese.view.validator.IValidator#validate(java.lang.Object)
     */
    @Override
    public Map<String, String> validate(Object object) {
    	
    	HttpServletRequest request = ReflectUtil.getCurrentHttpRequest();
        String tenantId = !StringUtil.isEmpty(ISecurityFilter.DEFAULT_TENANT_ID)
                ? ISecurityFilter.DEFAULT_TENANT_ID : "";
    	
        if (!ObjectUtil.isEmpty(request)) {
            tenantId = !StringUtil
                    .isEmpty((String) request.getSession().getAttribute(ISecurityFilter.TENANT_ID))
                            ? (String) request.getSession().getAttribute(ISecurityFilter.TENANT_ID)
                            : "";
        }

        ClassValidator municipalityValidator = new ClassValidator(Municipality.class);
        ClassValidator localityValidator = new ClassValidator(Locality.class);
        Municipality aMunicipality = (Municipality) object;

        Map<String, String> errorCodes = new LinkedHashMap<String, String>();
        if (logger.isInfoEnabled()) {
            logger.info("validate(Object) " + aMunicipality.toString());
        }

        InvalidValue[] values = null;

        if (StringUtil.isEmpty(aMunicipality.getLocality().getState().getCountry().getName())) {
            errorCodes.put(COUNTRY_EMPTY, COUNTRY_EMPTY_PROPERTY);
        } else {
            // Check whether country name is exists
            Country country = utilDAO.findCountryByName(aMunicipality.getLocality().getState()
                    .getCountry().getName());
            if (ObjectUtil.isEmpty(country))
                errorCodes.put(COUNTRY_NOT_FOUND, COUNTRY_NOT_FOUND_PROPERTY);
        }

        // State Validation
        if (StringUtil.isEmpty(aMunicipality.getLocality().getState().getCode())) {
            errorCodes.put(STATE_EMPTY, STATE_EMPTY_PROPERTY);
        } else {
            // Check whether state name is exists
            State state = utilDAO.findStateByCode(aMunicipality.getLocality().getState()
                    .getCode());
            if (ObjectUtil.isEmpty(state))
                errorCodes.put(STATE_NOT_FOUND, STATE_NOT_FOUND_PROPERTY);
        }

        // District Validation
        if (StringUtil.isEmpty(aMunicipality.getLocality().getCode())) {
            errorCodes.put(DISTRICT_EMPTY, DISTRICT_EMPTY_PROPERTY);
        } else {
            // Check whether district name is exists
            Locality locality = utilDAO.findLocalityByCode(aMunicipality.getLocality()
                    .getCode());
            if (ObjectUtil.isEmpty(locality))
                errorCodes.put(DISTRICT_NOT_FOUND, DISTRICT_NOT_FOUND_PROPERTY);
        }

        values = localityValidator.getInvalidValues(aMunicipality.getLocality(), "locality");
        for (InvalidValue value : values)
            errorCodes.put(value.getPropertyName(), value.getMessage());

        

        values = municipalityValidator.getInvalidValues(aMunicipality, "name");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }
        //code hidden as req changed to allow duplicate names
       
        	
      
        values = municipalityValidator.getInvalidValues(aMunicipality, "postalCode");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }

        if (!StringUtil.isEmpty(aMunicipality.getLatitude())) {
            values = municipalityValidator.getInvalidValues(aMunicipality, "latitude");
            for (InvalidValue value : values) {
                errorCodes.put(value.getPropertyName(), value.getMessage());
            }
        }

        if (!StringUtil.isEmpty(aMunicipality.getLongitude())) {
            values = municipalityValidator.getInvalidValues(aMunicipality, "longitude");
            for (InvalidValue value : values) {
                errorCodes.put(value.getPropertyName(), value.getMessage());
            }
        }

        
        if(!StringUtil.isEmpty(aMunicipality.getName())){
        	if(!ValidationUtil.isPatternMaches(aMunicipality.getName(), ValidationUtil.NAME_PATTERN)){
        		errorCodes.put("name","pattern.name");
        	}
        }else{
        	errorCodes.put("name","empty.name");
        }
        
        if(!StringUtil.isEmpty(aMunicipality.getPostalCode())){
        	if(!ValidationUtil.isPatternMaches(aMunicipality.getPostalCode(), ValidationUtil.NUMBER_PATTERN)){
        		errorCodes.put("name","pattern.postalcode");
        	}
        }else
        /*{
        	errorCodes.put("postalCode","empty.postalcode");
        }*/
        
        if(!StringUtil.isEmpty(aMunicipality.getLatitude())){
        	if(!ValidationUtil.isPatternMaches(aMunicipality.getLatitude(), ValidationUtil.LATITUDE_PATTERN)){
        		errorCodes.put("latitude","pattern.latitude");
        	}
        }
        
        if(!StringUtil.isEmpty(aMunicipality.getLongitude())){
        	if(!ValidationUtil.isPatternMaches(aMunicipality.getLongitude(), ValidationUtil.LONGITUDE_PATTERN)){
        		errorCodes.put("longitude","pattern.longitude");
        	}
        }
        
        return errorCodes;
    }

}
