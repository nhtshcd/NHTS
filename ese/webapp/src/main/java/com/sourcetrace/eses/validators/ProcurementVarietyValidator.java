package com.sourcetrace.eses.validators;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.IValidator;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.dao.IUtilDAO;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;

public class ProcurementVarietyValidator implements IValidator {

	@Autowired
	protected IUtilService utilService;
	@Autowired
	private IUtilDAO utilDAO;

    @Override
    public Map<String, String> validate(Object object) {

        ClassValidator productValidator = new ClassValidator(ProcurementVariety.class);
        ProcurementVariety aProcurementVariety = (ProcurementVariety) object;
        Map<String, String> errorCodes = new LinkedHashMap<String, String>();
        
        HttpServletRequest httpRequest=ReflectUtil.getCurrentHttpRequest();
        String branchId_F=(String) httpRequest.getSession().getAttribute(ISecurityFilter.CURRENT_BRANCH);
        String tenantId = (String) httpRequest.getSession().getAttribute(ISecurityFilter.TENANT_ID);
        tenantId = StringUtil.isEmpty(tenantId) ? ISecurityFilter.DEFAULT_TENANT_ID : tenantId;
        
        InvalidValue[] values = null;

        values = productValidator.getInvalidValues(aProcurementVariety, "code");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }        
     
        if (values == null || values.length == 0) {
            ProcurementVariety eProcurementVariety = utilService.findProcurementVariertyByCode(aProcurementVariety.getCode());
            if (eProcurementVariety != null && !aProcurementVariety.getId().equals(eProcurementVariety.getId())) {
                errorCodes.put("code", "unique.ProcurementVarietyCode");
            }
        }      
        
        if (values == null || values.length == 0) {
            ProcurementVariety eProcurementVariety = utilDAO.findProcurementVariertyByNameAndBranch(aProcurementVariety.getName(),branchId_F); 
            if (eProcurementVariety != null && aProcurementVariety.getId() != eProcurementVariety.getId()) {
                errorCodes.put("name", "unique.ProcurementVarietyName");
            }
        }
        
       
        if (!StringUtil.isEmpty(aProcurementVariety.getName())) {
            if (!ValidationUtil.isPatternMaches(aProcurementVariety.getName(),ValidationUtil.ALPHANUMERIC_PATTERN)) {
                errorCodes.put("pattern.name", "pattern.name");
            }
        }else{
            errorCodes.put("empty.name","empty.name");
        }
        
       
        
        return errorCodes;
    }



}
