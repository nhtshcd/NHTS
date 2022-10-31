/*
 * AgentValidator.java
 * Copyright (c) 2012-2013, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.validators;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.IValidator;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.dao.IUtilDAO;
import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.ContactInfo;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.PasswordHistory;
import com.sourcetrace.eses.entity.PersonalInfo;
import com.sourcetrace.eses.entity.ServicePoint;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.util.ICryptoUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;

/**
 * The Class AgentValidator.
 */
public class AgentValidator implements IValidator {

	private static final Logger logger = Logger.getLogger(AgentValidator.class);

	@Autowired
	private IUtilDAO utilDAO;

	private static final String CITY_NOT_FOUND = "cityNotFound";
	private static final String CITY_NOT_FOUND_PROPERTY = "city.notfound";
	private static final String COUNTRY_EMPTY = "emptyCountry";
	private static final String COUNTRY_EMPTY_PROPERTY = "empty.country";
	private static final String COUNTRY_NOT_FOUND = "countryNotFound";
	private static final String COUNTRY_NOT_FOUND_PROPERTY = "country.notfound";
	private static final String STATE_EMPTY = "emptyState";
	private static final String STATE_EMPTY_PROPERTY = "empty.state";
	private static final String STATE_NOT_FOUND = "stateNotFound";
	private static final String STATE_NOT_FOUND_PROPERTY = "state.notfound";
	private static final String DISTRICT_EMPTY = "emptyDistrict";
	private static final String DISTRICT_EMPTY_PROPERTY = "empty.district";
	private static final String DISTRICT_NOT_FOUND = "districtNotFound";
	private static final String DISTRICT_NOT_FOUND_PROPERTY = "district.notfound";
	private static final String SERVICEPOINT_NOT_FOUND = "servicePointNotFound";
	private static final String SERVICEPOINT_NOT_FOUND_PROPERTY = "servicepoint.notfound";
	private static final String SELECT = "-1";

	@Autowired
	private ICryptoUtil cryptoUtil;

	/**
	 * Gets the location dao.
	 * 
	 * @return the location dao
	 */

	/**
	 * Sets the location dao.
	 * 
	 * @param locationDAO
	 *            the new location dao
	 */

	/**
	 * Gets the agent dao.
	 * 
	 * @return the agent dao
	 */
	/**
	 * Gets the service point dao.
	 * 
	 * @return the service point dao
	 */
	/**
	 * Sets the service point dao.
	 * 
	 * @param servicePointDAO
	 *            the new service point dao
	 */

	/**
	 * Sets the agent dao.
	 * 
	 * @param agentDAO
	 *            the new agent dao
	 */

	/**
	 * @see com.ese.view.validator.IValidator#validate(java.lang.Object)
	 */
	@Override
	public Map<String, String> validate(Object agent) {

		ClassValidator<Agent> agentValidator = new ClassValidator<Agent>(Agent.class);
		ClassValidator contactValidator = new ClassValidator(ContactInfo.class);
		ClassValidator cityValidator = new ClassValidator(Municipality.class);
		ClassValidator personalValidator = new ClassValidator(PersonalInfo.class);
		ClassValidator servicePointValidator = new ClassValidator(ServicePoint.class);
		Agent aAgent = (Agent) agent;
		HttpServletRequest httpRequest = ReflectUtil.getCurrentHttpRequest();
		String branchId_F = (String) httpRequest.getSession().getAttribute(ISecurityFilter.CURRENT_BRANCH);

		if (logger.isInfoEnabled()) {
			logger.info("validate(Object) " + agent.toString());
		}
		Map<String, String> errorCodes = new LinkedHashMap<String, String>();
		InvalidValue[] values = null;

		values = agentValidator.getInvalidValues(aAgent, "profileId");
		for (InvalidValue value : values) {
			errorCodes.put(value.getPropertyName(), value.getMessage());
		}

		if (!StringUtil.isEmpty(aAgent.getProfileId())) {
			Agent eAgent = utilDAO.findAgentByAgentIdd(aAgent.getProfileId());
			if (eAgent != null && !eAgent.getId().equals(aAgent.getId())
					&& eAgent.getProfileId().equalsIgnoreCase(aAgent.getProfileId())) {
				errorCodes.put("profileId", "unique.agentId");
			}
		}

		values = personalValidator.getInvalidValues(aAgent.getPersonalInfo(), "firstName");
		for (InvalidValue value : values) {
			errorCodes.put(value.getPropertyName(), value.getMessage());
		}

		if (aAgent.getPersonalInfo().getMiddleName() != null && aAgent.getPersonalInfo().getMiddleName().length() > 0) {
			values = personalValidator.getInvalidValues(aAgent.getPersonalInfo(), "middleName");
			for (InvalidValue value : values) {
				errorCodes.put(value.getPropertyName(), value.getMessage());
			}
		}

		if (aAgent.getPersonalInfo().getSecondLastName() != null
				&& aAgent.getPersonalInfo().getSecondLastName().length() > 0) {
			values = personalValidator.getInvalidValues(aAgent.getPersonalInfo(), "secondLastName");
			for (InvalidValue value : values) {
				errorCodes.put(value.getPropertyName(), value.getMessage());
			}
		}

		if (!StringUtil.isEmpty(aAgent.getPersonalInfo().getIdentityNumber())) {
			values = personalValidator.getInvalidValues(aAgent.getPersonalInfo(), "identityNumber");
			for (InvalidValue value : values) {
				errorCodes.put(value.getPropertyName(), value.getMessage());
			}
		}

		if (ObjectUtil.isEmpty(aAgent.getAgentType()) || aAgent.getAgentType().getId() == null
				|| aAgent.getAgentType().getId() <= 0) {
			errorCodes.put("agentType", "empty.mobileUserType");
		} else if (aAgent.getAgentType().getId() !=1 && (aAgent.getPackhouse() == null
				|| aAgent.getPackhouse().getId() == null || aAgent.getPackhouse().getId() <= 0)) {
			errorCodes.put("agentType", "empty.packhouse");
		}

		values = agentValidator.getInvalidValues(aAgent, "status");
		for (InvalidValue value : values) {
			errorCodes.put(value.getPropertyName(), value.getMessage());
		}
		/*
		 * if (aAgent.getPassword() != null && aAgent.getPassword().length() >
		 * 0) { values = agentValidator.getInvalidValues(aAgent, "password");
		 * 
		 * if (aAgent.getPassword().length() != 6) {
		 * errorCodes.put("agent.password", "length.passwords"); }
		 * 
		 * }
		 */
		if (values == null || values.length == 0) {
			Date toDay = new Date();
			Date SelectedDate = aAgent.getPersonalInfo().getDateOfBirth();
			if (SelectedDate != null && toDay.before(SelectedDate)) {
				errorCodes.put("dateofbirth", "invalid.dateofbirth");
			}
		}

		values = contactValidator.getInvalidValues(aAgent.getContInfo(), "phoneNumber");
		for (InvalidValue value : values) {
			errorCodes.put(value.getPropertyName(), value.getMessage());
		}

		if ((!(ObjectUtil.isEmpty(aAgent.getContInfo())))
				&& (!(StringUtil.isEmpty(aAgent.getContInfo().getMobileNumbere())))) {
			values = contactValidator.getInvalidValues(aAgent.getContInfo(), "mobileNumbere");
			for (InvalidValue value : values) {
				errorCodes.put(value.getPropertyName(), value.getMessage());
			}
		}

		if ((!(ObjectUtil.isEmpty(aAgent.getContInfo()))) && (!(StringUtil.isEmpty(aAgent.getContInfo().getEmail())))) {
			values = contactValidator.getInvalidValues(aAgent.getContInfo(), "email");
			for (InvalidValue value : values) {
				errorCodes.put(value.getPropertyName(), value.getMessage());
			}
		}
		if(aAgent.getStatus()==null){
			errorCodes.put("agent.status.empty", "agent.status.empty");
		}
		

		if (aAgent.isChangePassword()) {

			String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{%%min%%,10000000}$";
			ESESystem es = utilDAO.findPrefernceByOrganisationId(branchId_F);
			PASSWORD_PATTERN = PASSWORD_PATTERN.replace("%%min%%",
					es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString());

			Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

			if (StringUtil.isEmpty(aAgent.getPassword()) || StringUtil.isEmpty(aAgent.getConfirmPassword())) {
				errorCodes.put("agent.password", "password.missing");
			} else if (!aAgent.getPassword().equals(aAgent.getConfirmPassword())) {
				errorCodes.put("agent.password", "password.missmatch");
			} /*else if (aAgent.getPassword().length() < Integer
					.valueOf(es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString())) {
				String pww = utilDAO.findLocaleProperty("length.password", aAgent.getLanguage());
				pww = pww.replace("[min]", es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString());
				errorCodes.put("user.password", pww);
			}*/

			/*
			 * else if (aAgent.getPassword().length() >
			 * Integer.valueOf(es.getPreferences().get(ESESystem.
			 * PASSWORD_MAX_LENGTH).toString())) { String pww
			 * =utilDAO.findLocaleProperty("length.password",
			 * aAgent.getLanguage()); pww=pww.replace("[min]",
			 * es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString()
			 * ); pww=pww.replace("[max]",
			 * es.getPreferences().get(ESESystem.PASSWORD_MAX_LENGTH).toString()
			 * ); errorCodes.put("user.password", pww); }
			 */
			if (aAgent.getId() != null) {
				List<String> pwList = utilDAO.listPasswordsByTypeAndRefId(
						String.valueOf(PasswordHistory.Type.MOBILE_USER.ordinal()), aAgent.getId());
				if (pwList != null) {
					pwList.stream().forEach(uu -> {
						String plainText = cryptoUtil.decrypt(uu);
						if (!StringUtil.isEmpty(plainText)) {
							plainText = plainText.trim();
							plainText = plainText.substring(aAgent.getProfileId().length(), plainText.length()).trim();
							if (plainText.equals(aAgent.getPassword())) {
								errorCodes.put("repeated.password", "repeated.password");
							}
						}
					});

				}
			}

			Matcher matcher = pattern.matcher(aAgent.getPassword());
			if (!matcher.matches()) {
				String mss = utilDAO.findLocaleProperty("agent.mishmatchbyregex", "en");
				mss = mss.replace("[min]",
						String.valueOf(es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString()));
				errorCodes.put(mss, mss);
			} else if (aAgent.getPassword() != null && !StringUtil.isEmpty(aAgent.getPassword())) {
				if (aAgent.getPersonalInfo() != null && aAgent.getPersonalInfo().getFirstName() != null
						&& !StringUtil.isEmpty(aAgent.getPersonalInfo().getFirstName()) && aAgent.getPassword()
								.toLowerCase().contains(aAgent.getPersonalInfo().getFirstName().toLowerCase())) {
					errorCodes.put("agent.namecontains", "agent.namecontains");
				}

				if (aAgent.getPersonalInfo() != null && aAgent.getPersonalInfo().getLastName() != null
						&& !StringUtil.isEmpty(aAgent.getPersonalInfo().getLastName()) && aAgent.getPassword()
								.toLowerCase().contains(aAgent.getPersonalInfo().getLastName().toLowerCase())) {
					errorCodes.put("agent.namecontains", "agent.namecontains");
				}

				if (aAgent.getProfileId() != null && aAgent.getProfileId() != null
						&& aAgent.getPassword().toLowerCase().contains(aAgent.getProfileId().toLowerCase())) {
					errorCodes.put("agent.namecontains", "agent.namecontains");
				}
			}

		}

		if (ObjectUtil.isEmpty(aAgent.getExporter()) || StringUtil.isEmpty(aAgent.getExporter().getId())) {
			errorCodes.put("empty.exporter", "empty.exporter");
		}

		if (!StringUtil.isEmpty(aAgent.getProfileId())) {
			if (!ValidationUtil.isPatternMaches(aAgent.getProfileId(), ValidationUtil.ALPHANUMERIC_PATTERN)) {
				errorCodes.put("pattern.profileId", "pattern.profileId");
			}
		} else {
			errorCodes.put("empty.profileId", "empty.profileId");
		}

		if (!StringUtil.isEmpty(aAgent.getPersonalInfo().getFirstName())) {
			if (!ValidationUtil.isPatternMaches(aAgent.getPersonalInfo().getFirstName(),
					ValidationUtil.ALPHANUMERIC_PATTERN)) {
				errorCodes.put("pattern.firstName", "pattern.firstName");
			}
		} else {
			errorCodes.put("empty.firstName", "empty.firstName");
		}
		if (!StringUtil.isEmpty(aAgent.getPersonalInfo().getFirstName())) {
			if (!ValidationUtil.isPatternMaches(aAgent.getPersonalInfo().getFirstName(),
					ValidationUtil.ALPHANUMERIC_PATTERN)) {
				errorCodes.put("pattern.lastName", "pattern.lastName");
			}
		}
		if (!StringUtil.isEmpty(aAgent.getPersonalInfo().getIdentityNumber())) {
			if (!ValidationUtil.isPatternMaches(aAgent.getPersonalInfo().getIdentityNumber(),
					ValidationUtil.ALPHANUMERIC_PATTERN)) {
				errorCodes.put("pattern.identityNumber", "pattern.identityNumber");
			}
		}
		if (aAgent.getContInfo() != null && !ObjectUtil.isEmpty(aAgent.getContInfo())
				&& !StringUtil.isEmpty(aAgent.getContInfo().getEmail())) {
			if (!ValidationUtil.isPatternMaches(aAgent.getContInfo().getEmail(), ValidationUtil.EMAIL_PATTERN)) {
				errorCodes.put("pattern.email", "pattern.email");
			}
		}

		return errorCodes;
	}

	/**
	 * Sets the product distribution dao.
	 * 
	 * @param productDistributionDAO
	 *            the new product distribution dao
	 */

	/**
	 * Gets the product distribution dao.
	 * 
	 * @return the product distribution dao
	 */

}
