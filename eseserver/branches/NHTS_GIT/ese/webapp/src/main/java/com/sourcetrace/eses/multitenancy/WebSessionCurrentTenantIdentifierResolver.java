package com.sourcetrace.eses.multitenancy;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

public class WebSessionCurrentTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

	@Override
	public String resolveCurrentTenantIdentifier() {
		HttpServletRequest request = ReflectUtil.getCurrentHttpRequest();
		String tenantId = ISecurityFilter.DEFAULT_TENANT_ID;
		if (!ObjectUtil.isEmpty(request)) {
			tenantId = (String) request.getSession().getAttribute(ISecurityFilter.TENANT_ID);
		}
		if (StringUtil.isEmpty(tenantId)) {
			tenantId = ISecurityFilter.DEFAULT_TENANT_ID;
		}
		return tenantId;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return false;
	}
}
