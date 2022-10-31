package com.sourcetrace.eses.validators;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.IValidator;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.dao.IUtilDAO;
import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

public class CatalogueValidator implements IValidator {

    private static final Logger logger = Logger.getLogger(CatalogueValidator.class);
    @Autowired
    private IUtilDAO utilDAO;

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> validate (Object object) {

        ClassValidator catalogueValidator = new ClassValidator(FarmCatalogue.class);
        FarmCatalogue aCatalogue = (FarmCatalogue) object;
        Map<String, String> errorCodes = new LinkedHashMap<String, String>();
        if (logger.isInfoEnabled()) {
            logger.info("validate(Object) " + aCatalogue.toString());
        }
        InvalidValue[] values = null;
        
        /*values = countryValidator.getInvalidValues(aCountry, "code");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }
        
        if (values == null || values.length == 0) {
            Country eCountry = locationDAO.findCountryByCode(aCountry.getCode());
              if (eCountry != null && aCountry.getId() != eCountry.getId()) {
                  errorCodes.put("code", "unique.CountryCode");
              }
          }*/
     /*  if((aCatalogue.getTypez()<0)^(StringUtil.isEmpty(aCatalogue.getName())))
                {
                    if (aCatalogue.getTypez() < 0){
                    errorCodes.put("typez", "typez.invalid");
                }
        if(StringUtil.isEmpty(aCatalogue.getName())){
            errorCodes.put("name", "empty.name");
        }
                }*/
        
        if (!ObjectUtil.isEmpty(aCatalogue.getTypez()) &&aCatalogue.getTypez() <= 0) {
            errorCodes.put("emptyType", "empty.type");
        }
        
        
       /* if((aCatalogue.getTypez()<0)&&(!StringUtil.isEmpty(aCatalogue.getName()))){
            errorCodes.put("typez", "typez.invalid");
        }
        else if(!(aCatalogue.getTypez()<0)&&(StringUtil.isEmpty(aCatalogue.getName())))
        {
            errorCodes.put("name", "empty.name");
        }
        */
       /* if (ObjectUtil.isEmpty(aCatalogue.getName())
                || StringUtil.isEmpty(aCatalogue.getName())) {
            errorCodes.put("emptyName", "empty.name");
        } */
        
        if(StringUtil.isEmpty(aCatalogue.getName())){
        	/*if(!ValidationUtil.isPatternMaches(aCatalogue.getName(), ValidationUtil.NAME_PATTERN) && !ValidationUtil.isPatternMaches(aCatalogue.getName(), ValidationUtil.ALPHANUMERIC_PATTERN)){
        		errorCodes.put("name","pattern.name");
        	}
        }else{*/
        	errorCodes.put("name","empty.name");
        }
        
        
        if(!StringUtil.isEmpty(aCatalogue.getName()))
        {
        values = catalogueValidator.getInvalidValues(aCatalogue, "name");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }
        
        if (values == null || values.length == 0 ) {
        	FarmCatalogue eCatalogue = utilDAO.findCatalogueByNameAndType(aCatalogue.getName(),aCatalogue.getTypez());
              if (eCatalogue != null && aCatalogue.getId() != eCatalogue.getId()) {
                  errorCodes.put("name", "unique.CatalogueName");
              }
          }
        }     
            
        
        return errorCodes;
    }

    public void setUtilDAO(IUtilDAO utilDAO) {
		this.utilDAO = utilDAO;
	}
	

}
