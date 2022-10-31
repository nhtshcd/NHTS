package com.sourcetrace.eses.validators;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.IValidator;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.dao.IFarmerDAO;
import com.sourcetrace.eses.dao.IUtilDAO;
import com.sourcetrace.eses.entity.Country;
import com.sourcetrace.eses.entity.GramPanchayat;
import com.sourcetrace.eses.entity.Locality;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.entity.Village;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
public class VillageValidator implements IValidator {



    private static final Logger logger = Logger.getLogger(VillageValidator.class);
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
    private static final String CITY_NOT_FOUND = "cityNotFound";
    private static final String CITY_NOT_FOUND_PROPERTY = "city.notfound";
    private static final String CITY_EMPTY = "emptyCity";
    private static final String CITY_EMPTY_PROPERTY = "empty.city";

    private static final String GRAM_PANCHAYAT_NOT_FOUND = "gramPanchayatNotFound";
    private static final String GRAM_PANCHAYAT_NOT_FOUND_PROPERTY = "gramPanchayat.notfound";
    private static final String GRAM_PANCHAYAT_EMPTY = "emptygramPanchayat";
    private static final String GRAM_PANCHAYAT_EMPTY_PROPERTY = "empty.gramPanchayat";

    @Autowired
    private IUtilDAO utilDAO;
   

    /**
     * @see com.ese.view.validator.IValidator#validate(java.lang.Object)
     */
    @Override
    public Map<String, String> validate(Object object) {

        ClassValidator villageValidator = new ClassValidator(Village.class);
        // ClassValidator cityValidator = new ClassValidator(Municipality.class);
        ClassValidator gramPanchayatValidator = new ClassValidator(GramPanchayat.class);
        Village aVillage = (Village) object;

        Map<String, String> errorCodes = new LinkedHashMap<String, String>();
        if (logger.isInfoEnabled()) {
            logger.info("validate(Object) " + aVillage.toString());
        }

        InvalidValue[] values = null;

        if (StringUtil.isEmpty(aVillage.getCity().getLocality().getState()
                .getCountry().getName())) {
            errorCodes.put(COUNTRY_EMPTY, COUNTRY_EMPTY_PROPERTY);
        } else {
            // Check whether country name is exists
            Country country = utilDAO.findCountryByName(aVillage.getCity()
                    .getLocality().getState().getCountry().getName());
            if (ObjectUtil.isEmpty(country))
                errorCodes.put(COUNTRY_NOT_FOUND, COUNTRY_NOT_FOUND_PROPERTY);
        }

        // State Validation
        if (StringUtil.isEmpty(aVillage.getCity().getLocality().getState()
                .getCode()) || aVillage.getCity().getLocality().getState()
                .getCode().equals("0") ) {
            errorCodes.put(STATE_EMPTY, STATE_EMPTY_PROPERTY);
        } else {
            // Check whether state name is exists
            State state = utilDAO.findStateByCode(aVillage.getCity()
                    .getLocality().getState().getCode());
            if (ObjectUtil.isEmpty(state))
                errorCodes.put(STATE_NOT_FOUND, STATE_NOT_FOUND_PROPERTY);
        }

        // District Validation
        if (StringUtil.isEmpty(aVillage.getCity().getLocality().getCode())) {
            errorCodes.put(DISTRICT_EMPTY, DISTRICT_EMPTY_PROPERTY);
        } else {
            // Check whether district name is exists
            Locality locality = utilDAO.findLocalityByCode(aVillage
                    .getCity().getLocality().getCode());
            if (ObjectUtil.isEmpty(locality))
                errorCodes.put(DISTRICT_NOT_FOUND, DISTRICT_NOT_FOUND_PROPERTY);
        }
        Municipality city = null;
        // city Validation
        if (StringUtil.isEmpty(aVillage.getCity().getCode())) {
            errorCodes.put(CITY_EMPTY, CITY_EMPTY_PROPERTY);
        } else {
            // Check whether city name is exists
            city = utilDAO.findMunicipalityByCode(aVillage.getCity()
            		.getCode());
            if (ObjectUtil.isEmpty(city))
                errorCodes.put(CITY_NOT_FOUND, CITY_EMPTY_PROPERTY);
        }

        // Gram Panchayat Validation
        GramPanchayat gramPanchayat = null;
        if(aVillage.getGramPanchayat()!=null)
        {
        if (StringUtil.isEmpty(aVillage.getGramPanchayat().getCode())) {
            errorCodes.put(GRAM_PANCHAYAT_EMPTY, GRAM_PANCHAYAT_EMPTY_PROPERTY);
        } else {
            gramPanchayat = utilDAO.findGrampanchaythByCode(aVillage.getGramPanchayat()
                    .getCode());
            if (ObjectUtil.isEmpty(gramPanchayat)) {
            errorCodes.put(GRAM_PANCHAYAT_NOT_FOUND, GRAM_PANCHAYAT_NOT_FOUND_PROPERTY);
            }
        }
        }
        values = gramPanchayatValidator.getInvalidValues(aVillage.getGramPanchayat(),
                "gramPanchayat");
        for (InvalidValue value : values)
            errorCodes.put(value.getPropertyName(), value.getMessage());

        /*values = villageValidator.getInvalidValues(aVillage, "code");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }
        if (values == null || values.length == 0) {
            Village eVillage = locationDAO.findVillageByCode(aVillage.getCode());
            if (eVillage != null) {
                if (aVillage.getId() != eVillage.getId()) {
                    errorCodes.put("code", "unique.villageCode");
                } else {
                    if (!ObjectUtil.isEmpty(city)
                            && eVillage.getCity().getId() != city.getId()) {
                        List<Farmer> farmers = farmerDAO.listFarmerByCityAndVillageId(eVillage
                               .getCity().getId() ,eVillage.getId());
                        if (!ObjectUtil.isListEmpty(farmers)) {
                            errorCodes.put("farmerExists", "farmer.exists");
                        }
                    }
                }
            }
        }*/

        values = villageValidator.getInvalidValues(aVillage, "name");
        for (InvalidValue value : values) {
            errorCodes.put(value.getPropertyName(), value.getMessage());
        }
        
        
        if(StringUtil.isEmpty(aVillage.getName())){
        	errorCodes.put("name", "empty.name");
        }else if(aVillage.getName().contains("-"))
        {
        	errorCodes.put("name","pattern.name");
        }
        
        //code hidden as req changed to allow special characters & Duplicate names
        
      /*  if(!StringUtil.isEmpty(aVillage.getName())){
        	if(!ValidationUtil.isPatternMaches(aVillage.getName(), ValidationUtil.ALPHANUMERIC_PATTERN)){
        		errorCodes.put("name","pattern.name");
        	}
        }else{
        	errorCodes.put("name","empty.name");
        }     
        */
        if (values == null || values.length == 0) {
        	if (!ObjectUtil.isEmpty(aVillage.getGramPanchayat())&&!StringUtil.isEmpty(aVillage.getGramPanchayat().getCode())) {
        		GramPanchayat grampanchayat=utilDAO.findGrampanchaythByCode(aVillage.getGramPanchayat().getCode());
        		if(!ObjectUtil.isEmpty(grampanchayat)){
        		Village villageExist=utilDAO.findDuplicateVillageName(grampanchayat.getId(),aVillage.getName());
        	
           /* Village eVillage = locationDAO.findVillageByCode((aVillage.getCode()));*/
            if (villageExist != null && villageExist.getId() != aVillage.getId()) {
                errorCodes.put("name", "unique.villageName");
            }
        	}
        	}
        }
        return errorCodes;
    }

    /**
     * Gets the location dao.
     * @return the location dao
     */
    public IUtilDAO getUtilDAO() {

        return utilDAO;
    }

    /**
     * Sets the location dao.
     * @param locationDAO the new location dao
     */
    public void setUtilDAO(IUtilDAO utilDAO) {

        this.utilDAO = utilDAO;
    }

   
}
