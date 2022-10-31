package com.sourcetrace.eses.action;

/*
 * ESEAction.java
 * Copyright (c) 2008, Source Trace Systems
 * ALL RIGHTS RESERVED
 */

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.validator.IValidator;

/**
 * The Class ESEAction.
 * 
 * @author $Author: aravind $
 * @version $Rev: 19 $, $Date: 2008-10-16 17:29:43 +0530 (Thu, 16 Oct 2008) $
 */
public abstract class SwitchValidatorAction extends SwitchAction {

	private static final Logger logger = Logger.getLogger(SwitchValidatorAction.class);

	protected IValidator validator;
	

	public String startDate;

	public String endDate;

	public Object filter;

	/**
	 * Sets the validator.
	 * 
	 * @param validator
	 *            the new validator
	 */
	public void setValidator(IValidator validator) {
		this.validator = validator;
	}

	/**
	 * Gets the data.
	 * 
	 * @return the data
	 */
	public abstract Object getData();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	public void validate() {
		request.setAttribute("heading", getText(command));
		Object obj = getData();
		if (obj != null) {
			Map<String, String> fieldErrors = validator.validate(obj);
			Set<String> fields = fieldErrors.keySet();

			if (logger.isInfoEnabled()) {
				logger.info("validate() Errors " + fieldErrors);
			}

			for (String field : fields) {
				addFieldError(field, getLocaleProperty(fieldErrors.get(field)));
			}
		}	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getText(String prop) {
		return getLocaleProperty(prop);
	}

}
