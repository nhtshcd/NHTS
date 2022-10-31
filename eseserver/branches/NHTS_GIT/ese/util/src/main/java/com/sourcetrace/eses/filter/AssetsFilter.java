/*
 * AssetsFilter.java
 * Copyright (c) 2008, Source Trace Systems
 * ALL RIGHTS RESERVED
 */
package com.sourcetrace.eses.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * The Class AssetsFilter.
 * 
 * @author $Author: aravind $
 * @version $Rev: 19 $, $Date: 2008-10-16 17:29:43 +0530 (Thu, 16 Oct 2008) $
 */
public class AssetsFilter implements Filter {

	private String client;

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
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		//HttpServletRequest request = (HttpServletRequest) req;
		//HttpServletResponse response = (HttpServletResponse) res;
		//Locale locale = (Locale) request.getSession().getAttribute(ISecurityFilter.LOCALE);

		// String path = request.getServletPath();
		// String newPath = null;

		// if (path.startsWith("/assets/") && path.indexOf("assets/common") ==
		// -1
		// && path.indexOf("/" + client) == -1
		// && path.indexOf("/" + locale.getLanguage()) == -1) {
		//
		// if (path.startsWith("/assets/client/")) {
		// newPath = request.getContextPath() + "/assets/client/" + client
		// + "/" + path.substring(15);
		// response.sendRedirect(newPath);
		// } else if (!locale.getLanguage().equals("en")) {
		// newPath = request.getContextPath() + "/assets/"
		// + locale.getLanguage() + "/" + path.substring(8);
		// response.sendRedirect(newPath);
		// } else {
		// chain.doFilter(req, res);
		// }
		// } else {
		chain.doFilter(req, res);
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		client = config.getServletContext().getInitParameter("client");
	}
}
