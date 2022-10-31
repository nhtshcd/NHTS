package com.sourcetrace.eses.validators;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.IValidator;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.dao.IUtilDAO;
import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;

public class BranchMasterValidator implements IValidator {
	
	private static final Logger logger = Logger.getLogger(BranchMasterValidator.class);	
	@Autowired
	private IUtilDAO utilDAO;
	  @SuppressWarnings("unchecked")
	public Map<String, String> validate(Object object) {
		
		BranchMaster aBranchMaster = (BranchMaster) object;
		
		  ClassValidator branchMasterValidator = new ClassValidator(BranchMasterValidator.class);
		  
		  BranchMaster branchMaster = (BranchMaster) object;
		  
		  
		BranchMaster tempBranchMaster = null;
		 InvalidValue[] values = null;
        Map<String, String> errorCodes = new LinkedHashMap<String, String>();
        
        if (logger.isInfoEnabled()) {
            logger.info("validate(Object) " + aBranchMaster.toString());
        }
        
        if (!StringUtil.isEmpty(aBranchMaster.getBranchId())) {       
        	tempBranchMaster = utilDAO.findBranchMasterByBranchId(aBranchMaster.getBranchId());
        	if(!ObjectUtil.isEmpty(tempBranchMaster)&& aBranchMaster.getId()!=tempBranchMaster.getId()){
        		errorCodes.put("exists.branchId", "exists.branchId");
        	}
            if (!ValidationUtil.isPatternMaches(aBranchMaster.getBranchId(),ValidationUtil.ALPHANUMERIC_PATTERN_WITHOUT_SPACE)) {
                errorCodes.put("pattern.branchId", "pattern.branchId");
            }
        }else{
            errorCodes.put("empty.branchId","empty.branchId");
        }
        
        if (!StringUtil.isEmpty(aBranchMaster.getName())) {       
        	tempBranchMaster = utilDAO.findBranchMasterByName(aBranchMaster.getName());
        	if(!ObjectUtil.isEmpty(tempBranchMaster)&& aBranchMaster.getId()!=tempBranchMaster.getId()){
        		errorCodes.put("exists.name", "exists.name");
        	}
            if (!ValidationUtil.isPatternMaches(aBranchMaster.getName(),ValidationUtil.ALPHANUMERIC_PATTERN)) {
                errorCodes.put("pattern.name", "pattern.name");
            }
        }else{
            errorCodes.put("empty.name","empty.name");
        }
        
        values = branchMasterValidator.getInvalidValues(branchMaster, "mobileNumber");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }
        
        if (!StringUtil.isEmpty(aBranchMaster.getPhoneNo())) {
            if (!ValidationUtil.isPatternMaches(aBranchMaster.getPhoneNo(),ValidationUtil.NUMBER_PATTERN)) {
                errorCodes.put("pattern.phoneNo", "pattern.phoneNo");
            }
        }
        
        
        
		return errorCodes;
	}

	public void setUtilDAO(IUtilDAO utilDAO) {
		this.utilDAO = utilDAO;
	}
	
	
	
	
}
