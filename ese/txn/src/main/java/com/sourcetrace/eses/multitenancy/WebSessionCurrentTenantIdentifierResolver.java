package com.sourcetrace.eses.multitenancy;

import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.util.StringUtil;

public class WebSessionCurrentTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

	@Resource(name = "datasources")
	private Map<String, DataSource> datasources;

	@Override
	public String resolveCurrentTenantIdentifier() {
		String tenantId = ISecurityFilter.DEFAULT_TENANT_ID;
		
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
