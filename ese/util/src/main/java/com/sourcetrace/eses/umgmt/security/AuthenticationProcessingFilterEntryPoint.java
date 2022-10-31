/*
 * AuthenticationProcessingFilterEntryPoint.java
 * Copyright (c) 2008-2010, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.umgmt.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * The Class AuthenticationProcessingFilterEntryPoint.
 * @author $Author: moorthy $
 * @version $Rev: 7114 $ $Date: 2011-03-23 16:35:35 +0530 (Wed, 23 Mar 2011) $
 */
public class AuthenticationProcessingFilterEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public AuthenticationProcessingFilterEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	public static final Logger LOGGER = Logger.getLogger(AuthenticationProcessingFilterEntryPoint.class.getName());
    public static final String ATTRIBUTE_LOGIN_PAGE = "web.secfilter.authenticator.showLogin";
    
    /**
     * Commence.
     * @param request the request
     * @param response the response
     * @param authException the auth exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     * @see org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint#commence(javax.servlet.ServletRequest,
     *      javax.servlet.ServletResponse, org.springframework.security.AuthenticationException)
     */
    public void commence(ServletRequest request, ServletResponse response, AuthenticationException authException) throws IOException, ServletException {
 
        boolean ajax = Boolean.parseBoolean(request.getParameter("javax.faces.partial.ajax"));
        LOGGER.error("AJAX Request Parameter " + ajax);
        
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        String context[]=uri.split("/");       
        
        if(ajax) {
            String loginForm = determineUrlToUseForThisRequest((HttpServletRequest) request, (HttpServletResponse) response, authException);
            StringBuffer buffer = new StringBuffer();
            buffer.append("<?xml version='1.0' encoding='UTF-8'?>");
            buffer.append("<partial-response>");
            buffer.append("<redirect url=\"" + context[1] + loginForm + "\"></redirect>");
            buffer.append("</partial-response>");
            response.getWriter().write(buffer.toString());
        } else {
            super.commence((HttpServletRequest)request, (HttpServletResponse)response, authException);
        }
    }
}
