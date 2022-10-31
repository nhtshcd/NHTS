/*
 * UsageFilter.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sourcetrace.eses.txn.exception.WebConsoleError;
import com.sourcetrace.eses.txn.exception.WebConsoleException;
import com.sourcetrace.eses.util.DateUtil;

public class UsageFilter implements Filter {

	private static final Logger LOGGER = Logger.getLogger(UsageFilter.class
			.getName());
	private IUsageValidator usageValidator;
	private String date;
	private String ctx;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) {

		String reqDate = DateUtil.getDate();
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String requestURI = request.getRequestURI();
		int startIndex = requestURI.lastIndexOf("/");

		String uri = startIndex == -1 ? "" : requestURI
				.substring(startIndex + 1);
		try {
			if (!reqDate.equals(date)) {
				date = reqDate;
				if (!("license_list.action".equals(uri) || "license_save.action"
						.equals(uri))) {
					usageValidator.validate();
				}
			}
			chain.doFilter(req, res);
		} catch (Exception excep) {
			try {
				if (excep instanceof WebConsoleException) {
					WebConsoleException ee = (WebConsoleException) excep;
					req.setAttribute("error", ee.getMessage());
					if (ee.getCode().equals(WebConsoleError.INVALID_LICENCE)
							|| ee.getCode().equals(
									WebConsoleError.EXPIRED_LICENCE)
							|| ee.getCode().equals(
									WebConsoleError.UNAUTHORIZED_CLIENT)
							|| ee.getCode().equals(WebConsoleError.NO_LICENCE)) {
						response.sendRedirect("license_list.action");
					} else if (ee.getCode().equals(
							WebConsoleError.DATABASE_CONNECTION_ERROR)) {
						RequestDispatcher dispatcher = req
								.getRequestDispatcher("jsp/util/connectionError.jsp");
						dispatcher.forward(req, res);

					} else {
						RequestDispatcher dispatcher = req
								.getRequestDispatcher("jsp/util/serverError.jsp");
						dispatcher.forward(req, res);
					}

				} else {
					RequestDispatcher dispatcher = req
							.getRequestDispatcher("jsp/util/serverError.jsp");
					dispatcher.forward(req, res);
				}
			} catch (Exception e) {
				LOGGER.error("License Validation Error " + e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig cfg) throws ServletException {

		WebApplicationContext wctx = WebApplicationContextUtils
				.getWebApplicationContext(cfg.getServletContext());
		usageValidator = (UsageValidator) wctx.getBean("usageValidator");
		ctx = cfg.getServletContext().getContextPath();

	}

}
