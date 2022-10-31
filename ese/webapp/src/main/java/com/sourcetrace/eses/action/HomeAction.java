/*
 * HomeAction.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.action;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.action.ESEAction;

import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.entity.User;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

public class HomeAction extends ESEAction {

	@Autowired
	private IUtilService utilService;
	@Setter
	@Getter
	private String pw;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1150435185457016568L;
	protected static final String HEADING = "heading";
	protected static final String LIST = "list";

	public String list() throws Exception {

		request.setAttribute(HEADING, getText(LIST));
		// Added to Disable HomeList when user is not logged in
		// Authentication Manual handling is needed since filters="none"
		// attribute is set in spring
		// security
		// Added for Bug Fix (ESE_AGRO 242 - Login Page Freeze)
		if (ObjectUtil.isEmpty(request.getSession()) || StringUtil.isEmpty(request.getSession().getAttribute("user"))) {
			return "login";
		}
		String userName = (String) request.getSession().getAttribute("user");
		// checked whether password has been reset
		User user = utilService.findUserByUserName(userName);
		// type attribute is set when its redirected from password reset page
		ESESystem es = utilService.findPrefernceByOrganisationId(user.getBranchId());
		Integer age = Integer.valueOf(es.getPreferences().get(ESESystem.AGE).toString());
		Integer remisays = Integer.valueOf(es.getPreferences().get(ESESystem.REMINDER_DAYS).toString());

		Date crtDate = user.getPasswordHistory() != null && !user.getPasswordHistory().isEmpty()
				? user.getPasswordHistory().iterator().next().getCreatedDate() : user.getCreatedDate();
		if (crtDate != null && !ObjectUtil.isEmpty(crtDate)) {
			DateTime today = new DateTime(DateUtil.getDateWithStartMinuteofDay(new Date()));
			DateTime crtDat = new DateTime(DateUtil.getDateWithStartMinuteofDay(crtDate));
			int days = Days.daysBetween(crtDat, today).getDays();
			if (days >= age) {
				pw = "pw=1&layoutType=publiclayout";
				return "change";
			}
		}

		if(user.getLoginStatus()==null || user.getLoginStatus()==0){
			pw = "pw=1&layoutType=publiclayout";
			return "change";
		}

		String type = request.getParameter("type");
		/*
		 * if (user.isReset() && StringUtil.isEmpty(type)) return
		 * "resetPassword";
		 */
		if (utilService.isDashboardUserEntitlementAvailable(userName)) {
			return "dashboard";
		} else {

			// sets ese applcation theme after the login.
			if (StringUtil.isEmpty(request.getSession().getAttribute(ESESystem.SESSION_THEME_ATTRIBUTE_NAME))) {
				// String theme =
				// this.preferncesService.findPrefernceById(ESESystem.SYSTEM_ESE)
				// .getPreferences().get(ESESystem.THEME);
				String theme = "agro-theme";
				request.getSession().setAttribute(ESESystem.SESSION_THEME_ATTRIBUTE_NAME,
						StringUtil.isEmpty(theme) ? getText("default.theme") : theme);
			}

			localizeMenu();
			return LIST;
		}
		// return userService.isDashboardUserEntitlementAvailable(userName) ?
		// "dashboard" : LIST ;
	}

}
