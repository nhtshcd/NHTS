/*
 * ChangePasswordAction.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.PasswordHistory;
import com.sourcetrace.eses.entity.User;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.ICryptoUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
@Getter
@Setter
public class ChangePasswordAction extends SwitchAction {
	private User user;
	@Autowired
	private IUtilService userService;
	@Autowired
	private ICryptoUtil cryptoUtil;
	private String pw;
	private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

	/**
	 * Edits the.
	 * 
	 * @return the string
	 */
	public String edit() {

		return INPUT;
	}

	public String editExp() {
		addActionError(getLocaleProperty("pw.exp"));

		user = utilService.findByUsername(getUsername());

		request.getSession().setAttribute("report", user.getPersInfo().getName());
		setCommand("update");
		return INPUT;
	}

	/**
	 * Update.
	 * 
	 * @return the string
	 */
	public String update() {

		if (!ObjectUtil.isEmpty(this.user)) {
			User eUser = userService.findUserByUserName(this.user.getUsername());
			if (!ObjectUtil.isEmpty(eUser)) {
				eUser.setChangePassword(true);
				eUser.setPassword(this.user.getPassword());
				eUser.setLoginStatus(2);
				userService.editUserCredential(eUser);
				
			}
		}
		return SUCCESS;
	}

	// @Override
	// protected Object getData() {
	//
	// if (!ObjectUtil.isEmpty(this.user)) {
	// this.user.setUsername(getLoggedUser().getUsername());
	// }
	// return this.user;
	// }

	/**
	 * Gets the user.
	 * 
	 * @return the user
	 */
	public User getUser() {

		return user;
	}

	/**
	 * Sets the user.
	 * 
	 * @param user
	 *            the new user
	 */
	public void setUser(User user) {

		this.user = user;
	}

	public void populateValidate() {

		Map<String, String> errors = new HashMap<String, String>();
		if (!ObjectUtil.isEmpty(user)) {
			User entity = user;
			User eUser = null;

			if (!StringUtil.isEmpty(entity.getUsername()))
				eUser = userService.findByUsername(entity.getUsername());

			ESESystem es = userService.findPrefernceByOrganisationId(eUser.getBranchId());
			Integer min = Integer.valueOf(es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString());
			String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{%%min%%,100000000}$";
			PASSWORD_PATTERN = PASSWORD_PATTERN.replace("%%min%%",
					es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString());

			Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

			if (ObjectUtil.isEmpty(eUser)) {
				errors.put("user.oldPassword", getLocaleProperty("changePassword.user.notfound"));
			}

			if (StringUtil.isEmpty(entity.getOldPassword())) {
				errors.put("user.oldPassword", getLocaleProperty("changePassword.empty.oldPassword"));

			}

			if (StringUtil.isEmpty(entity.getPassword())) {
				errors.put("user.password", getLocaleProperty("changePassword.empty.password"));

			}

			if (StringUtil.isEmpty(entity.getConfirmPassword())) {
				errors.put("user.confirmPassword", getLocaleProperty("changePassword.empty.confirmPassword"));

			}
			
			
			  if (eUser.getId() != null) {
            	  List<String> pwList=userService.listPasswordsByTypeAndRefId(String.valueOf(PasswordHistory.Type.WEB_USER.ordinal()),eUser.getId());	if (pwList != null) {
					pwList.stream().forEach(uu -> {
						String plainText = cryptoUtil.decrypt(uu);
						if (!StringUtil.isEmpty(plainText)) {
							plainText = plainText.trim();
							plainText = plainText.substring(user.getUsername().length(), plainText.length()).trim();
							if (plainText.equals(user.getPassword())) {
								errors.put("repeated.password", "repeated.password");
							}
						}
					});

				}
			}
				// password and confirmPassword should be same
			if (!StringUtil.isEmpty(entity.getPassword())
					&& !StringUtil.isEmpty(entity.getConfirmPassword())) {
				if (!entity.getPassword().equalsIgnoreCase(entity.getConfirmPassword())) {
					errors.put("user.password", getLocaleProperty("changePassword.password.mismatch"));

				}
			}

			if (!ObjectUtil.isEmpty(eUser)) {

				// Validating oldPassword should match with users current
				// password
				if (StringUtil.isEmpty(errors.get("user.oldPassword"))) {
					String oldPasswordToken = cryptoUtil
							.encrypt(StringUtil.getMulipleOfEight(eUser.getUsername() + entity.getOldPassword()));
					if (!oldPasswordToken.equalsIgnoreCase(eUser.getPassword())) {
						errors.put("user.oldPassword", getLocaleProperty("changePassword.oldPassword.mismatch"));
						
					}
				}

				// Validating password is should not same as users current
				// password
				if (StringUtil.isEmpty(errors.get("user.password"))) {
					String newPasswordToken = cryptoUtil
							.encrypt(StringUtil.getMulipleOfEight(eUser.getUsername() + entity.getPassword()));
					if (newPasswordToken.equalsIgnoreCase(eUser.getPassword())) {
						errors.put("user.password", getLocaleProperty("changePassword.password.exists"));

					}
				}
				Matcher matcher = pattern.matcher(entity.getPassword().trim());

				if (!matcher.matches()) {
					String mss = utilService.findLocaleProperty("agent.mishmatchbyregex", "en");
					mss = mss.replace("[min]",
							String.valueOf(es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString()));
					errors.put(mss, mss);
				} else if (entity.getPassword() != null && !StringUtil.isEmpty(entity.getPassword())) {

					if (eUser.getPersInfo() != null && eUser.getPersInfo().getFirstName() != null
							&& !StringUtil.isEmpty(eUser.getPersInfo().getFirstName()) && entity.getPassword()
									.toLowerCase().contains(eUser.getPersInfo().getFirstName().toLowerCase())) {
						errors.put("agent.namecontains", "agent.namecontains");
					}

					if (eUser.getPersInfo() != null && eUser.getPersInfo().getLastName() != null
							&& !StringUtil.isEmpty(eUser.getPersInfo().getLastName()) && entity.getPassword()
									.toLowerCase().contains(eUser.getPersInfo().getLastName().toLowerCase())) {
						errors.put("agent.namecontains", "agent.namecontains");
					}
					if (eUser.getUsername() != null && eUser.getUsername() != null
							&& entity.getPassword().toLowerCase().contains(eUser.getUsername().toLowerCase())) {
						errors.put("agent.namecontains", "agent.namecontains");
					}
				}

			}
		}

		printErrorCodes(errors);
	}
}
