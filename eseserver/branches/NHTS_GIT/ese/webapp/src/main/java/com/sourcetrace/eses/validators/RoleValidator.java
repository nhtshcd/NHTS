package com.sourcetrace.eses.validators;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.IValidator;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.dao.IUtilDAO;
import com.sourcetrace.eses.entity.Role;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;

public class RoleValidator implements IValidator {
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(RoleValidator.class);
@Autowired
	private IUtilDAO utilDAO;
	public Map<String, String> validate(Object object) {

		ClassValidator roleValidator = new ClassValidator(Role.class);

		Role role = (Role) object;
		Map<String, String> errorCodes = new LinkedHashMap<String, String>();

		if (logger.isInfoEnabled()) {
			logger.info("validate(Object) " + object.toString());
		}

		if (!StringUtil.isEmpty(role.getBranchId()) && "-1".equalsIgnoreCase(role.getBranchId())) {
			errorCodes.put("empty.organization", "empty.organization");
		}

		InvalidValue[] values = roleValidator.getInvalidValues(role, "name");
		for (InvalidValue value : values) {
			errorCodes.put(value.getPropertyName(), value.getMessage());
		}

		if (values == null || values.length == 0) {
			Role eRole = utilDAO.findRoleByName(role.getName());
			if (eRole != null && role.getId() != eRole.getId()) {
				errorCodes.put("name", "unique.rolename");
			}
		}
		if (!StringUtil.isEmpty(role.getName())) {
			if (!ValidationUtil.isPatternMaches(role.getName(), ValidationUtil.ALPHANUMERIC_PATTERN)) {
				errorCodes.put("pattern.name", "pattern.name");
			}
		} else {
			errorCodes.put("empty.name", "empty.name");
		}

		return errorCodes;
	}

}
