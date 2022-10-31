package com.sourcetrace.eses.validators;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.IValidator;
import org.hibernate.validator.InvalidValue;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.util.StringUtil;

@Component
@Scope("prototype")
public class FarmValidator implements IValidator {

	@Override
	public Map<String, String> validate(Object object) {

		ClassValidator farmValidator = new ClassValidator(Farm.class);
		Farm farm = (Farm) object;
		String STRING_PATTERN = "[a-zA-Z]+";
		InvalidValue[] values = null;
		Map<String, String> errorCodes = new LinkedHashMap<String, String>();
		
		if (StringUtil.isEmpty(farm.getFarmName())) {
			errorCodes.put("empty.farmName", "empty.farmName");
		}

		if (StringUtil.isEmpty(farm.getTotalLandHolding())) {
			errorCodes.put("empty.totalLandHolding", "empty.totalLandHolding");
		}

		/*if (StringUtil.isEmpty(farm.getProposedPlantingArea())) {
			errorCodes.put("empty.proposedPlantingArea", "empty.proposedPlantingArea");
		}*/	
		
		if(StringUtil.isEmpty(farm.getLandOwnership())|| farm.getLandOwnership().equals("-1")){
	        errorCodes.put("empty.landOwnership", "empty.landOwnership");
        }
		
		if(StringUtil.isEmpty(farm.getLandGradient())|| farm.getLandGradient().equals("-1")){
	        errorCodes.put("empty.landGradient", "empty.landGradient");
        }
		
		if(StringUtil.isEmpty(farm.getSoilType())|| farm.getSoilType().equals("-1")){
	        errorCodes.put("empty.soilType", "empty.soilType");
        }
		
		if(StringUtil.isEmpty(farm.getIrrigationType())|| farm.getIrrigationType().equals("-1")){
	        errorCodes.put("empty.irrigationType", "empty.irrigationType");
        }
		
		if(StringUtil.isEmpty(farm.getApproachRoad())|| farm.getApproachRoad().equals("-1")){
	        errorCodes.put("empty.approachRoad", "empty.approachRoad");
        }
		
		if(StringUtil.isEmpty(farm.getNoFarmLabours())){
	        errorCodes.put("empty.farmLabours", "empty.farmLabours");
        }

		return errorCodes;
	}

}
