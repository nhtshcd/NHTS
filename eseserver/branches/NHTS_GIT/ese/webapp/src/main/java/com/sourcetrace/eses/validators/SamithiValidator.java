/*
 * SamithiValidator.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
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
import com.sourcetrace.eses.entity.ESESystem;

import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;

public class SamithiValidator implements IValidator {

    private static final Logger logger = Logger.getLogger(SamithiValidator.class);

    
    @Autowired
	private IUtilDAO utilDAO;

    /**
     * Sets the location dao.
     * @param locationDAO the new location dao
     */
    /**
     * Validate.
     * @param object the object
     * @return the map< string, string>
     * @see com.ese.view.validator.IValidator#validate(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> validate(Object object) {
    	Map<String, String> errorCodes = new LinkedHashMap<String, String>();
        /*ClassValidator samithiValidator = new ClassValidator(Warehouse.class);

        Warehouse aSamithi = (Warehouse) object;
        HttpServletRequest httpRequest=ReflectUtil.getCurrentHttpRequest();
        
        
        if (logger.isInfoEnabled()) {
            logger.info("validate(Object) " + aSamithi.toString());
        }
        InvalidValue[] values = null;

        
        values = samithiValidator.getInvalidValues(aSamithi, "name");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }
        
        
        
        if(!StringUtil.isEmpty(aSamithi.getName())){
        	Warehouse eSamithi = utilDAO.findWarehouseByNameAndType(aSamithi.getName(),Warehouse.WarehouseTypes.SAMITHI.ordinal());
        	if(eSamithi!=null && aSamithi.getId() != eSamithi.getId()){
        		errorCodes.put("name", "unique.samithiName");
        	}
        }else{
        	if(StringUtil.isEmpty(aSamithi.getName())){
            	errorCodes.put("name","empty.name");
            }
        }*/
 return errorCodes;
    }

}
