package com.sourcetrace.eses.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

public class ESELogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	
	@Override
	public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {
		String loginUrl = "/login_execute";
		if (authentication != null && authentication.getDetails() != null) {
			try {
				HttpSession httpSession = ReflectUtil.getCurrentHttpSession();
				
				loginUrl = (String) ReflectUtil.getCurrentHttpRequest().getSession().getAttribute("FAV_URL");
				if(loginUrl==null || StringUtil.isEmpty(loginUrl) ){
					Object tenantIdObj = httpSession.getAttribute(ISecurityFilter.TENANT_ID);
					String tenantId = ObjectUtil.isEmpty(tenantIdObj) ? "" : tenantIdObj.toString();

					if(!StringUtil.isEmpty(tenantId)){
						loginUrl = "/login_execute_"+tenantId;
					}else{
						loginUrl = "/login_execute";
					}
					
				}
				httpSession.invalidate();

			} catch (Exception e) {
				loginUrl = "/login_execute";
				
			}
		}
		
		httpServletRequest.getSession().invalidate();
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		httpServletResponse
				.sendRedirect(httpServletResponse.encodeRedirectURL(httpServletRequest.getContextPath() + loginUrl));
	}
}