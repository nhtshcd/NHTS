/**
 * The Class RoleAction.
 */
package com.sourcetrace.eses.action;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * The Class RoleAction.
 */

public class SampleAction extends ESEAction {
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(SampleAction.class);

	public String create() throws Exception {

		request.setAttribute(HEADING, getText(CREATE));
		return INPUT;
	}

	public String detail() throws Exception {

	
		request.setAttribute(HEADING, getText(DETAIL));
		return DETAIL;
	}

	

}
