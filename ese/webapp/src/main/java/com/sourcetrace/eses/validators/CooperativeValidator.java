package com.sourcetrace.eses.validators;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.validator.IValidator;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.dao.IUtilDAO;

public class CooperativeValidator  implements IValidator{

    private static final Logger logger = Logger.getLogger(CooperativeValidator.class);

    @Autowired
	private IUtilDAO utilDAO;

    /**
     * Sets the location dao.
     * @param locationDAO the new location dao
     */
 

    /**
     * @see com.ese.view.validator.IValidator#validate(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> validate(Object object) {
    	Map<String, String> errorCodes = new LinkedHashMap<String, String>();
        /*ClassValidator cooperativeValidator = new ClassValidator(Warehouse.class);
        Warehouse cooperativeObject = (Warehouse) object;
        
        if (logger.isInfoEnabled()) {
            logger.info("validate(Object) " + cooperativeObject.toString());
        }
        InvalidValue[] values = null;

        values = cooperativeValidator.getInvalidValues(cooperativeObject, "name");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }
        if (values == null || values.length == 0) {
            Warehouse cooperative = utilDAO.findWarehouseByName(cooperativeObject.getName());
            if (cooperative != null && cooperativeObject.getId() != cooperative.getId()) {
                errorCodes.put("name", "unique.cooperativeName");
            }
        }

        values = cooperativeValidator.getInvalidValues(cooperativeObject, "address");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }

        values = cooperativeValidator.getInvalidValues(cooperativeObject, "phoneNo");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }

        values = cooperativeValidator.getInvalidValues(cooperativeObject, "capacityInTonnes");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }
        
        if(!StringUtil.isEmpty(cooperativeObject.getName())){
            if (!ValidationUtil.isPatternMaches(cooperativeObject.getName(),ValidationUtil.ALPHANUMERIC_PATTERN)) {
                errorCodes.put("pattern.name","pattern.name");
            }
        }else{
            errorCodes.put("empty.name","empty.name");
        }
        
        if(!StringUtil.isEmpty(cooperativeObject.getLocation())){
            if (!ValidationUtil.isPatternMaches(cooperativeObject.getLocation(),ValidationUtil.NAME_PATTERN)) {
                errorCodes.put("pattern.location","pattern.location");
            }
        }
        
        if(!StringUtil.isEmpty(cooperativeObject.getWarehouseIncharge())){
            if (!ValidationUtil.isPatternMaches(cooperativeObject.getWarehouseIncharge(),ValidationUtil.ALPHANUMERIC_PATTERN)) {
                errorCodes.put("pattern.warehouseInCharge","pattern.warehouseInCharge");
            }
        }
        */
    
        
        return errorCodes;
    }

}
