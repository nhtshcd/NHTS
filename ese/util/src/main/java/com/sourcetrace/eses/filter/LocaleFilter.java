/**
 * LocaleFilter.java
 * Copyright (c) 2008-2009, Source Trace Systems
 * ALL RIGHTS RESERVED
 */
package com.sourcetrace.eses.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sourcetrace.eses.util.StringUtil;


/**
 * The Class LocaleFilter.
 * @author $Author: aravind $
 * @version $Rev: 4183 $, $Date: 2009-05-08 13:02:31 +0530 (Fri, 08 May 2009) $
 */
public class LocaleFilter implements Filter {
	
	private static final Logger logger = Logger.getLogger(LocaleFilter.class);
    private String lang;
    
	
    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {}

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        if (lang != null && lang.trim().length() != 0) {
            String userLang = (String) request.getSession().getAttribute(ISecurityFilter.LANGUAGE);
            Locale clientlocale = null;

            if (!StringUtil.isEmpty(userLang)) {
                clientlocale = new Locale(userLang);
            } else {
                clientlocale = new Locale(lang);
            }

            request.getSession().setAttribute(ISecurityFilter.LOCALE,
                    clientlocale);
        }

        chain.doFilter(req, res);
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        lang = config.getServletContext().getInitParameter(ISecurityFilter.LANGUAGE);
    }
}

