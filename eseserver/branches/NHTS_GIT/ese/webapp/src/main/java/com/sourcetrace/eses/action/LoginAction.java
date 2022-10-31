/*
 * LoginAction.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Asset;
import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.Language;
import com.sourcetrace.eses.entity.PersonalInfo;
import com.sourcetrace.eses.entity.PreferenceType;
import com.sourcetrace.eses.entity.User;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.umgmt.security.BranchDisabledException;
import com.sourcetrace.eses.umgmt.security.UndefinedEntitlementException;
import com.sourcetrace.eses.util.ICryptoUtil;
import com.sourcetrace.eses.util.MailUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

@Component
@Scope("prototype")
public class LoginAction extends SwitchAction {

	private static final long serialVersionUID = -8732527637531064784L;

	private String userValue;
	private String resetValue;

	@Autowired
	private IUtilService utilService;

	@Autowired
	private ICryptoUtil tripleDES;

	private String REDIRECT = "redirect";
	private Map<String, String> branches = new LinkedHashMap<String, String>();

	private int branchEnabled = 0;

	private int isMultibranchApp = 0;

	private String url;
	private String lang;
	private String logoType;
	private String vesion;

	@Resource(name = "datasources")
	private Map<String, DataSource> datasources;

	private String tenantId;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		String vesion = utilService.findPrefernceById(ESESystem.SYSTEM_ESE).getPreferences().get(ESESystem.VESION);
		setVesion(vesion);
		AuthenticationException authException = (AuthenticationException) request.getSession()
				.getAttribute(ISecurityFilter.EXCEPTION);

		if (authException != null) {
			if (authException instanceof LockedException) {
				addActionError(getText("userlogin.blocked"));
			} else if (authException instanceof DisabledException) {
				addActionError(getText("userlogin.notEnabled"));
			} else if (authException instanceof BadCredentialsException
					|| authException instanceof InternalAuthenticationServiceException) {

				if (authException.getCause() instanceof UndefinedEntitlementException) {
					addActionError(getText("undefined.entitlement"));
				} else if (authException.getCause() instanceof BranchDisabledException) {
					if (!StringUtil.isEmpty(authException.getCause().getMessage())) {
						if (authException.getCause().getMessage().equals("WL0007")) {
							addActionError(getText("Sub Organization Disabled"));
						} else {
							addActionError(getText("Organization Disabled"));
						}
					}
				} else {
					if (request.getSession().getAttribute(ISecurityFilter.CURRENT_BRANCH) != null
							&& request.getSession().getAttribute("counter") != null) {
						ESESystem es = utilService.findPrefernceByOrganisationId(
								request.getSession().getAttribute(ISecurityFilter.CURRENT_BRANCH).toString());
						String username = (String) request.getSession().getAttribute("user");
						if (es != null && es.getPreferences() != null && !es.getPreferences().isEmpty()) {
							Integer count = Integer.parseInt(
									es.getPreferences().get(PreferenceType.NO_OF_INVALID_LOGIN_ATTEMPTS.name()));
							Integer existlog = Integer
									.parseInt(request.getSession().getAttribute("counter").toString());
							if (count - existlog == 0) {
								User user = utilService.findByUsername(username);
								user.setMilliSecond(new Date().getTime());

								utilService.update(user);
								addActionError(getText("userlogin.blocked"));
							} else {
								String mss = getLocaleProperty("userpass.invalid.count");
								mss = mss.replace("[cnt]", String.valueOf(count - existlog));
								addActionError(mss);
							}
						}
					} else {
						addActionError(getText("userpass.invalid"));
					}

				}
			}

			/*
			 * else if (authException instanceof ConcurrentLoginException) {
			 * addActionError(getText("login.exists")); }
			 */ else {
				addActionError(getText("login.failed"));
			}
			request.getSession().removeAttribute(ISecurityFilter.EXCEPTION);
			request.getSession().setAttribute(ISecurityFilter.CURRENT_BRANCH, null);
			request.getSession().setAttribute(ISecurityFilter.CURRENT_BRANCH_NAME, null);
			tenantId = (String) request.getSession().getAttribute(ISecurityFilter.TENANT_ID);
		}
		if (!StringUtil.isEmpty(tenantId)) {
			request.getSession().setAttribute(ISecurityFilter.TENANT_ID, tenantId);
			String requestURL = request.getServletPath();
			request.getSession().setAttribute("FAV_URL", requestURL.toString());
		}

		if (StringUtil.isEmpty(tenantId)) {
			if (!StringUtil.isEmpty(request.getSession().getAttribute(ISecurityFilter.TENANT_ID))) {
				request.getSession().setAttribute(ISecurityFilter.TENANT_ID,
						request.getSession().getAttribute(ISecurityFilter.TENANT_ID));
				url = "/login_execute_" + request.getSession().getAttribute(ISecurityFilter.TENANT_ID);
				request.getSession().setAttribute("FAV_URL", url.toString());

			} else {
				String singleTenant = utilService.findPrefernceById(ESESystem.SYSTEM_ESE).getPreferences()
						.get(ESESystem.IS_SINGLE_TENANT);

				if (!StringUtil.isEmpty(singleTenant) && singleTenant.equals("1")) {
					// pref process
					tenantId = utilService.findPrefernceById(ESESystem.SYSTEM_ESE).getPreferences()
							.get(ESESystem.SINGLE_TENANT_NAME);
					request.getSession().setAttribute(ISecurityFilter.TENANT_ID, tenantId);
				} else {
					request.getSession().setAttribute(ISecurityFilter.TENANT_ID, ESESystem.AGRO_TENANT);
				}
				url = "/login_execute_" + request.getSession().getAttribute(ISecurityFilter.TENANT_ID);
				request.getSession().setAttribute("FAV_URL", url.toString());
			}
		} else {
			request.getSession().setAttribute(ISecurityFilter.TENANT_ID, tenantId);
			url = "/login_execute_" + tenantId;
			request.getSession().setAttribute("FAV_URL", url.toString());
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Object userfullnameObj = request.getSession().getAttribute(ISecurityFilter.USER_FULL_NAME);
		if (!ObjectUtil.isEmpty(userfullnameObj) && !StringUtil.isEmpty(userfullnameObj.toString())) {
			if (!ObjectUtil.isEmpty(authentication) && authentication.isAuthenticated()) {
				return "red_url";
			}
		}

		buildBranchMap();

		String branchVal = utilService.findPrefernceById(ESESystem.SYSTEM_ESE).getPreferences()
				.get(ESESystem.ENABLE_BRANCH);
		branchEnabled = Integer.parseInt(branchVal);

		String multiBranchVal = utilService.findPrefernceByName(ESESystem.IS_MULTI_BRANCH_APP);
		isMultibranchApp = StringUtil.isInteger(multiBranchVal) ? Integer.parseInt(multiBranchVal) : 0;
		return INPUT;
	}

	public String executeExpired() {
		try {
			url = (String) ReflectUtil.getCurrentHttpRequest().getSession().getAttribute("FAV_URL");
			tenantId = (String) ReflectUtil.getCurrentHttpRequest().getSession()
					.getAttribute(ISecurityFilter.TENANT_ID);
			request.getSession().invalidate();
			if (StringUtil.isEmpty(url)) {
				if (!StringUtil.isEmpty(tenantId)) {
					url = "login_execute_" + tenantId;
				} else {
					url = "login_execute";
				}
			}
		} catch (Exception e) {
			url = "login_execute";
		}
		return "red_url";
	}

	public String executeLang() {

		Language language = null;
		if (!StringUtil.isEmpty(lang))
			language = utilService.findLanguageByCode(lang);
		if (!StringUtil.isEmpty(lang) && !ObjectUtil.isEmpty(language)) {
			HttpSession httpSession = ReflectUtil.getCurrentHttpSession();
			if (!ObjectUtil.isEmpty(httpSession)) {
				httpSession.setAttribute(ISecurityFilter.LANGUAGE, lang);
				httpSession.setAttribute(ISecurityFilter.LOCALE, new Locale(lang));
				String loggedinUser = (String) httpSession.getAttribute(ISecurityFilter.USER);
				if (!StringUtil.isEmpty(loggedinUser)) {
					utilService.updateUserLanguage(loggedinUser, lang);
				}
				httpSession.setAttribute(ISecurityFilter.LANGUAGE_MENU, getLanguageMenu());
				reLocalizeMenu();
			}
		}
		url = url == null || StringUtil.isEmpty(url) ? request.getHeader("referer") : url;

		url = StringUtil.isEmpty(url) ? "home_list.action" : url.substring(url.lastIndexOf("/") + 1, url.length());
		return "red_url";
	}

	protected void buildBranchMap() {

		branches = new LinkedHashMap<String, String>();
		List<BranchMaster> branchMasters = utilService.listBranchMasters();
		branches = ReflectUtil.buildMap(branchMasters, new String[] { "branchId", "name" });
	}

	/**
	 * Gets the credential list.
	 * 
	 * @return the credential list
	 */
	public Map<String, String> getCredentialList() {

		Map<String, String> listCredential = new LinkedHashMap<String, String>();
		listCredential.put("1", getText("userName"));
		listCredential.put("2", getText("email"));
		return listCredential;
	}

	/**
	 * Validate user.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void validateUser() throws Exception {

		String result = "";
		User user = null;
		if (!ObjectUtil.isEmpty(userValue)) {
			if (userValue.equalsIgnoreCase("1")) {
				user = utilService.findUserByProfileId(resetValue);
			} else {
				user = utilService.findUserByEmailId(resetValue);
			}
		}
		try {
			if (!ObjectUtil.isEmpty(user)) {
				if (!ObjectUtil.isEmpty(user.getContInfo()) && !StringUtil.isEmpty(user.getContInfo().getEmail())) {
					StringBuffer msg = new StringBuffer();
					String password = StringUtil.generateStrPassword(8);

					msg.append("\n\tUser Name\t:\t");
					msg.append(user.getUsername());
					msg.append("\n");
					msg.append("\tPassword\t:\t");
					msg.append(password);

					user.setPassword(password);
					user.setReset(true);
					utilService.editUserCredential(user);

					sendExpireMail(user.getContInfo().getEmail(), user.getPersInfo().getName(), msg.toString(),
							"Your Temporary Credential");

					result = getText("successMsg") + "100";
				} else {
					result = getText("notExist.email");
				}
			} else {
				result = getText("notExist" + userValue);
			}
		} catch (Exception e) {
			result = "";
		}
		response.getWriter().print(result);
		response.setContentType("text/html");

	}

	public void populateLogo() {

		try {
			List<String> logoCodes = new ArrayList<String>() {
				{
					add(Asset.APP_LOGO);
					add(Asset.FAVICON);
				}
			};
			HttpServletResponse response = ServletActionContext.getResponse();
			response.reset();
			response.setContentType("multipart/form-data");
			OutputStream out = response.getOutputStream();
			byte[] imageData = new byte[] {};
			if (!StringUtil.isEmpty(logoType) && logoCodes.contains(logoType)) {
				imageData = utilService.findLogoByCode(logoType);
			}

			if (ObjectUtil.isEmpty(imageData) || imageData.length == 0) {
				String logoPath = request.getSession().getServletContext().getRealPath("/img/no-image.png");
				File pic = new File(logoPath);
				long length = pic.length();
				imageData = new byte[(int) length];
				FileInputStream picIn = new FileInputStream(pic);
				picIn.read(imageData);
			}
			out.write(imageData);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the user value.
	 * 
	 * @return the user value
	 */
	public String getUserValue() {

		return userValue;
	}

	/**
	 * Sets the user value.
	 * 
	 * @param userValue
	 *            the new user value
	 */
	public void setUserValue(String userValue) {

		this.userValue = userValue;
	}

	/**
	 * Gets the reset value.
	 * 
	 * @return the reset value
	 */
	public String getResetValue() {

		return resetValue;
	}

	/**
	 * Sets the reset value.
	 * 
	 * @param resetValue
	 *            the new reset value
	 */
	public void setResetValue(String resetValue) {

		this.resetValue = resetValue;
	}

	public Map<String, String> getBranches() {

		return branches;
	}

	public void setBranches(Map<String, String> branches) {

		this.branches = branches;
	}

	public int getBranchEnabled() {

		return branchEnabled;
	}

	public void setBranchEnabled(int branchEnabled) {

		this.branchEnabled = branchEnabled;
	}

	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		this.url = url;
	}

	public String getLang() {

		return lang;
	}

	public void setLang(String lang) {

		this.lang = lang;
	}

	public String getLogoType() {

		return logoType;
	}

	public void setLogoType(String logoType) {

		this.logoType = logoType;
	}

	public String getMainBranchName() {

		Map<String, String> preferences = utilService.findPrefernceById(ESESystem.SYSTEM_ESE).getPreferences();
		String mainBranchName = preferences.get(ESESystem.MAIN_BRANCH_NAME);
		return mainBranchName;
	}

	public String getTenantId() {

		return tenantId;
	}

	public void setTenantId(String tenantId) {

		this.tenantId = tenantId;
	}

	public String getLanguageMenu() {

		String menuText = "";
		List<Language> languages = utilService.listLanguages();
		if (languages.size() > 1) {
			String currentLang = getCurrentLanguage();
			menuText = "<button data-toggle='dropdown' class='btn btn-primary btn-sm dropdown-toggle'>"
					+ getDBProperty("language." + currentLang, currentLang) + " <span class='caret'></span>"
					+ "</button>" + "<ul class='dropdown-menu' role='menu'>";
			for (Language language : languages) {
				menuText = menuText + "<li><a class='lanMenu' href='login_executeLang?lang=" + language.getCode() + "'>"
						+ getDBProperty(language.getName(), currentLang) + "</a></li>";
			}
			menuText = menuText + "</ul>";
		}
		return menuText;
	}

	public int getIsMultibranchApp() {

		return isMultibranchApp;
	}

	public void setIsMultibranchApp(int isMultibranchApp) {

		this.isMultibranchApp = isMultibranchApp;
	}

	public String getVesion() {
		return vesion;
	}

	public void setVesion(String vesion) {
		this.vesion = vesion;
	}
	
	

}
