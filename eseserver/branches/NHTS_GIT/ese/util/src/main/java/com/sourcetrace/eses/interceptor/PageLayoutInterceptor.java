/*
 * PageLayoutInterceptor.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sourcetrace.eses.util.ObjectUtil;

@SuppressWarnings("serial")
public class PageLayoutInterceptor extends AbstractInterceptor {

	private static final Logger LOGGER = Logger.getLogger(PageLayoutInterceptor.class);
	private static Map<String, String> methods = null;
	private boolean headingEnable;
	private boolean breadCrumbEnable;
	
	public static final String LIST = "list";
	public static final String CREATE = "create";
	public static final String SAVE = "save";
	public static final String EDIT = "edit";
	public static final String UPDATE = "update";
	public static final String HEADING = "heading";
	public static final String DETAIL = "detail";
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
	 */
	@Override
	public void destroy() {

		LOGGER.info("Destroying PageLayoutInterceptor...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
	 */
	@Override
	public void init() {

		LOGGER.info("Initializing PageLayoutInterceptor...");
		methods = new HashMap<String, String>();
		methods.put(LIST, LIST);
		methods.put(CREATE, CREATE);
		methods.put(SAVE, SAVE);
		methods.put(EDIT, EDIT);
		methods.put(UPDATE, UPDATE);
		methods.put(DETAIL, DETAIL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.
	 * opensymphony.xwork2. ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		try {
			if (methods.containsKey(invocation.getProxy().getMethod())) {
				if (isHeadingEnable()) {
					((HttpServletRequest) invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST))
							.setAttribute(HEADING,
									getText(invocation, methods.get(invocation.getProxy().getMethod()), ""));
				}
			}
			if (isBreadCrumbEnable()) {
				
//				((HttpServletRequest) invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST))
//						.setAttribute(BreadCrumb.BREADCRUMB, BreadCrumb.getBreadCrumb(
//								getText(invocation, BreadCrumb.BREADCRUMB, "")));
			}
			String actionName = invocation.getInvocationContext().getName();
			if (!actionName.equalsIgnoreCase("login_execute")) {
				Map attibutes = invocation.getInvocationContext().getSession();
				if (!ObjectUtil.isEmpty(attibutes) && (ObjectUtil.isEmpty(attibutes.get("menuLocalize"))
						|| !attibutes.get("menuLocalize").equals("done"))) {
					 invokeMenuLocalize(invocation);
				}
			}
		} catch (Exception e) {
			LOGGER.info("Could not set layout properties (heading & breadcrumb)");
			LOGGER.info("Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return invocation.invoke();
	}

	private String getText(ActionInvocation invocation, String propertyName, String defaultValue) {

		String value = "";
		try {
			String actionName = invocation.getInvocationContext().getName();

			value = (String) ((invocation.getAction().getClass()).getMethod("getText", String.class, String.class))
					.invoke(invocation.getAction(), propertyName, defaultValue);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * Checks if is heading enable.
	 * 
	 * @return true, if is heading enable
	 */
	public boolean isHeadingEnable() {

		return headingEnable;
	}

	/**
	 * Sets the heading enable.
	 * 
	 * @param headingEnable
	 *            the new heading enable
	 */
	public void setHeadingEnable(boolean headingEnable) {

		this.headingEnable = headingEnable;
	}

	/**
	 * Checks if is bread crumb enable.
	 * 
	 * @return true, if is bread crumb enable
	 */
	public boolean isBreadCrumbEnable() {

		return breadCrumbEnable;
	}

	/**
	 * Sets the bread crumb enable.
	 * 
	 * @param breadCrumbEnable
	 *            the new bread crumb enable
	 */
	public void setBreadCrumbEnable(boolean breadCrumbEnable) {

		this.breadCrumbEnable = breadCrumbEnable;
	}

	private void invokeMenuLocalize(ActionInvocation invocation) {
		try {
			Method method = ((invocation.getAction().getClass()).getMethod("localizeMenu", null));
			if (!ObjectUtil.isEmpty(method)) {
				method.invoke(invocation.getAction());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
