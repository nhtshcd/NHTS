package com.sourcetrace.eses.multitenancy;

import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;

import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.util.StringUtil;

public class MultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

	private static final long serialVersionUID = 1L;

	private Map<String, DataSource> dataSourceMap;

	@Override
	protected DataSource selectAnyDataSource() {
		return (DataSource) dataSourceMap.values().toArray()[0];
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {

		DataSource dataSource = selectAnyDataSource();

		if (!StringUtil.isEmpty(tenantIdentifier) && !tenantIdentifier.equals(ISecurityFilter.DEFAULT_TENANT_ID)
				&& dataSourceMap.containsKey(tenantIdentifier)) {
			dataSource = dataSourceMap.get(tenantIdentifier);
		}
		return dataSource;
	}

	public Map<String, DataSource> getDataSourceMap() {
		return dataSourceMap;
	}

	public void setDataSourceMap(Map<String, DataSource> dataSourceMap) {
		this.dataSourceMap = dataSourceMap;
	}

}
