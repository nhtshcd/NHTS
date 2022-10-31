/*
 * ProcurementProductValidator.java
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

import javax.servlet.http.HttpSession;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.IValidator;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.dao.IUtilDAO;
import com.sourcetrace.eses.entity.ProcurementProduct;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;


public class ProcurementProductValidator implements IValidator {

	@Autowired
	protected IUtilService utilService;
	@Autowired
	private IUtilDAO utilDAO;

    /**
     * @see com.ese.view.validator.IValidator#validate(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> validate(Object object) {

        ClassValidator productValidator = new ClassValidator(ProcurementProduct.class);
        ProcurementProduct aProcurementProduct = (ProcurementProduct) object;
        Map<String, String> errorCodes = new LinkedHashMap<String, String>();

        HttpSession httpSession=ReflectUtil.getCurrentHttpSession();
    	String branchId_F=(String)httpSession.getAttribute(ISecurityFilter.CURRENT_BRANCH);
    	
        InvalidValue[] values = null;

        values = productValidator.getInvalidValues(aProcurementProduct, "code");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }

        if (values == null || values.length == 0) {
            ProcurementProduct eProcurementProduct = utilService
                    .findProcurementProductByCode(aProcurementProduct.getCode());
            if (eProcurementProduct != null
                    && aProcurementProduct.getId() != eProcurementProduct.getId()) {
                errorCodes.put("code", "unique.ProcurementProductCode");
            }
        }

        values = productValidator.getInvalidValues(aProcurementProduct, "name");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }

        if (values == null || values.length == 0) {
            ProcurementProduct eProcurementProduct = utilDAO
                    .findProcurementProductByNameAndBranch(aProcurementProduct.getName(),branchId_F);
            if (eProcurementProduct != null
                    && aProcurementProduct.getId() != eProcurementProduct.getId()) {
                errorCodes.put("name", "unique.ProcurementProductName");
            }
        }
        
        
        if (!StringUtil.isEmpty(aProcurementProduct.getName())) {
            if (!ValidationUtil.isPatternMaches(aProcurementProduct.getName(),ValidationUtil.ALPHANUMERIC_PATTERN)) {
                errorCodes.put("pattern.name", "pattern.name");
            }
        }else{
            errorCodes.put("empty.name","empty.name");
        }
        
        return errorCodes;
    }



}
