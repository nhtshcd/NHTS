/*
 * SecurityFilter.java
 * Copyright (c) 2015-2016, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.Language;
import com.sourcetrace.eses.entity.Menu;
import com.sourcetrace.eses.entity.User;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.exception.WebConsoleError;
import com.sourcetrace.eses.umgmt.security.IUserDetailsAdapter;
import com.sourcetrace.eses.util.ICryptoUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

public class SecurityFilter extends UsernamePasswordAuthenticationFilter
        implements ISecurityFilter {

    private static final Logger LOGGER = Logger.getLogger(SecurityFilter.class.getName());
    private IUserDetailsAdapter userDetailsAdapter;
    @Autowired
    private ICryptoUtil cryptoUtil;
    @Autowired
    private IUtilService utilService;
    
  

    private String defaultLanguage;

    /**
     * Sets the crypto util.
     * @param cryptoUtil the new crypto util
     */
    public void setCryptoUtil(ICryptoUtil cryptoUtil) {

        this.cryptoUtil = cryptoUtil;
    }

    /**
     * Gets the default language.
     * @return the default language
     */
    public String getDefaultLanguage() {

        return defaultLanguage;
    }

    /**
     * Sets the default language.
     * @param defaultLanguage the new default language
     */
    public void setDefaultLanguage(String defaultLanguage) {

        this.defaultLanguage = defaultLanguage;
    }

    /**
     * Sets the user details adapter.
     * @param userDetailsAdapter the new user details adapter
     */
    public void setUserDetailsAdapter(IUserDetailsAdapter userDetailsAdapter) {

        this.userDetailsAdapter = userDetailsAdapter;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.ui.webapp.AuthenticationProcessingFilter#
     * obtainPassword(javax .servlet.http.HttpServletRequest)
     */
    @Override
    protected String obtainPassword(HttpServletRequest request) {

        String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
        String password = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);

        if (StringUtil.isEmpty(username)) {
            throw new BadCredentialsException(WebConsoleError.EMPTY_USER);
        } else if (StringUtil.isEmpty(password)) {
            throw new BadCredentialsException(WebConsoleError.EMPTY_PASSWORD);
        }

        try {
            String clearText = username + password;
            password = cryptoUtil.encrypt(StringUtil.getMulipleOfEight(clearText));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return password;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.ui.AbstractProcessingFilter#
     * successfulAuthentication(javax.servlet .http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, org.springframework.security.Authentication)
     */
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, javax.servlet.FilterChain filterChain,
            Authentication authResult) throws IOException {
    	 request.getSession().setAttribute("dealerId", null);
    		request.getSession().setAttribute(INSP_ID, null);
        try {
            super.successfulAuthentication(request, response, filterChain, authResult);
        } catch (ServletException e) {
            throw new IOException(e);
        }
        String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
        String language = userDetailsAdapter.getUserLanguage(username);
        language = (language == null) ? defaultLanguage : language;
        if (language != null) {
            request.getSession().setAttribute(LOCALE, new Locale(language));
        }
        request.getSession().removeAttribute(EXCEPTION);
        List<Menu> userMenus = userDetailsAdapter.getUserMenus(username);
        LOGGER.debug("Logged in User Menu " + userMenus);

        for (GrantedAuthority authority : authResult.getAuthorities()) {
            LOGGER.debug(" " + authority.getAuthority());
        }
        request.getSession().setAttribute(ISecurityFilter.CURRENT_BRANCH, ISecurityFilter.DEFAULT_TENANT_ID);
        request.getSession().setAttribute(MENU, userMenus);
        request.getSession().setAttribute(USER, username);
        request.getSession().setAttribute(LANGUAGE, language);

        User user = userDetailsAdapter.getUser(username);
        if (user == null) {
            throw new BadCredentialsException(WebConsoleError.INVALID_LOGIN);
        }
        request.getSession().setAttribute(USER_EMAIL, user.getContInfo().getEmail());
        request.getSession().setAttribute(USER_INFO, getUserInfo(user));
        request.getSession().setAttribute(USER_FULL_NAME,
                !ObjectUtil.isEmpty(user.getPersInfo()) ? user.getPersInfo().getName()
                        : "");
        
        request.getSession().setAttribute(BRANCH_ID,
                !ObjectUtil.isEmpty(user.getBranchId()) ? user.getBranchId()
                        : "");
        
        request.getSession().setAttribute(PREF_DATAS, getPrefDatas());

        request.getSession().setAttribute(LANGUAGE_MENU, getLanguageMenu());

        String currentTheme = utilService.findPrefernceByName(ESESystem.CURRENT_THEME);
        if(!StringUtil.isEmpty(currentTheme)){
        	request.getSession().setAttribute(CURRENT_THEME, currentTheme.trim());
        }else{
        	request.getSession().setAttribute(CURRENT_THEME, "bluewood.css");
        }
        if (user.getRole() != null) {
            request.getSession().setAttribute(ROLE, user.getRole().getName());
            request.getSession().setAttribute(IS_ADMIN, user.getRole().getIsAdminUser());
            request.getSession().setAttribute(ROLE_ID, user.getRole().getId());
            request.getSession().setAttribute(ROLE_TYPE, user.getRole().getType());
            if(user.getRole().getName().contains("Inspector")){
            	request.getSession().setAttribute(INSP_ID, user.getId());
            }
        }
        request.getSession().setAttribute(LOCALIZE, TRUE);
        request.getSession().setAttribute("menuLocalize", "");
        request.getSession().setAttribute(dealerId, user.getAgroChDealer());
        user.setLastLoginDate(new Date());
        utilService.update(user);
        userDetailsAdapter.resetInvalidLoginCounter(username);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException failed) throws IOException {

        String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
        request.getSession().setAttribute(USER, username);
        request.getSession().setAttribute(EXCEPTION, failed);

        userDetailsAdapter.setInvalidLoginCounter(username);

        try {
            super.unsuccessfulAuthentication(request, response, failed);
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Gets the user info.
     * @param user the user
     * @return the user info
     */
    private Map<String, Object> getUserInfo(User user) {

        Map<String, Object> userInfo = new HashMap<String, Object>();
        if (!ObjectUtil.isEmpty(user)) {
            userInfo.put(USER_REC_ID, user.getId());
            // userInfo.put(IS_AGENT_USER, new Boolean(user.isAgentUser()));
            // if (user.isAgentUser()) {
            // Object[] agentInfo =
            // userDetailsAdapter.getAgentInfo(user.getUsername());
            // if (!ObjectUtil.isEmpty(agentInfo) && agentInfo.length >= 5) {
            // userInfo.put(AGENT_PRIMARY_KEY_ID, agentInfo[0]);
            // userInfo.put(AGENT_PROFILE_ID, agentInfo[1]);
            //
            // String agentName = "";
            // if (!ObjectUtil.isEmpty(agentInfo[2])) {
            // agentName += (!StringUtil.isEmpty(agentName)) ? " " +
            // String.valueOf(agentInfo[2]) : String.valueOf(agentInfo[2]);
            // }
            // if (!ObjectUtil.isEmpty(agentInfo[3])) {
            // agentName += (!StringUtil.isEmpty(agentName)) ? " " +
            // String.valueOf(agentInfo[3]) : String.valueOf(agentInfo[3]);
            // }
            // userInfo.put(AGENT_NAME, agentName);
            // userInfo.put(AGENT_DESIGNATION_CODE, agentInfo[4]);
            // }
            // }
        }
        return userInfo;
    }

    /**
     * Gets the pref datas.
     * @return the pref datas
     */
    private Map<String, String> getPrefDatas() {

        // Map<String, String> preferenceDatas =
        // preferencesService.getPreferenceDatas();
        Map<String, String> prefDatas = null;
        // if(!ObjectUtil.isEmpty(preferenceDatas)) {
        // prefDatas = new HashMap<String, String>();
        // prefDatas.put(com.sourcetrace.eses.util.entity.System.DONT_ACCESS_DATA_FILTER,
        // (String)preferenceDatas.get(com.sourcetrace.eses.util.entity.System.DONT_ACCESS_DATA_FILTER));
        // }
        return prefDatas;
    }

    public String getLanguageMenu() {

        String menuText = "";
        List<Language> languages = utilService.listLanguages();
        if (languages.size() > 1) {
            String currentLang = getCurrentLanguage();
            menuText = "<button data-toggle='dropdown' class='btn btn-primary btn-sm dropdown-toggle'>"
                    + getDBProperty("language." + currentLang, currentLang)
                    + " <span class='caret'></span>" + "</button>"
                    + "<ul class='dropdown-menu' role='menu'>";
            for (Language language : languages) {
                menuText = menuText + "<li><a class='lanMenu' href='login_executeLang?lang=" + language.getCode()
                        + "'>" + getDBProperty(language.getName(), currentLang) + "</a></li>";
            }
            menuText = menuText + "</ul>";
        }
        return menuText;
    }

    public String getCurrentLanguage() {

        return (String) ReflectUtil.getCurrentHttpSession().getAttribute(ISecurityFilter.LANGUAGE);
    }

    public String getDBProperty(String prop, String language) {

        String locProp = utilService.findLocaleProperty(prop, language);
        return locProp;
    }

}