/*
 * ReportService.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sourcetrace.eses.dao.IReportDAO;
import com.sourcetrace.eses.entity.DynamicReportConfig;
import com.sourcetrace.eses.entity.DynamicReportConfigDetail;


@Service
@Transactional
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ReportService implements IReportService {

	private IReportDAO reportDAO;
	private String reportEntity;
	private String filterDateProperty;
	private List<String> filterExcludeProperties;

	/**
	 * Sets the report entity.
	 * 
	 * @param reportEntity
	 *            the new report entity
	 */
	public void setReportEntity(String reportEntity) {

		this.reportEntity = reportEntity;
	}

	/**
	 * Sets the filter date property.
	 * 
	 * @param filterDateProperty
	 *            the new filter date property
	 */
	public void setFilterDateProperty(String filterDateProperty) {

		this.filterDateProperty = filterDateProperty;
	}

	/**
	 * Sets the filter exclude properties.
	 * 
	 * @param filterExcludeProperties
	 *            the new filter exclude properties
	 */
	public void setFilterExcludeProperties(List<String> filterExcludeProperties) {

		this.filterExcludeProperties = filterExcludeProperties;
	}

	/**
	 * Sets the report dao.
	 * 
	 * @param reportDAO
	 *            the new report dao
	 */
	public void setReportDAO(IReportDAO reportDAO) {

		this.reportDAO = reportDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.service.report.IReportService#list(java.lang.String,
	 * java.lang.String, int, int, int)
	 */
	public Map list(String dir, String sort, int start, int limit, int page) {

		Map params = new HashMap();
		addSortingPagination(params, reportEntity, dir, sort, start, limit, page);
		return reportDAO.list(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.report.IReportService#listWithDateRangeFiltering(java.
	 * lang.String, java.lang.String, int, int, java.util.Date, java.util.Date,
	 * int)
	 */
	public Map listWithDateRangeFiltering(String dir, String sort, int start, int limit, Date startDate, Date endDate,
			int page) {

		Map params = new HashMap();
		addSortingPagination(params, reportEntity, dir, sort, start, limit, page);
		addDateRangeFiltering(params, filterDateProperty, startDate, endDate);
		return reportDAO.list(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.report.IReportService#listWithEntityFiltering(java.lang.
	 * String, java.lang.String, int, int, java.lang.Object, int)
	 */
	public Map listWithEntityFiltering(String dir, String sort, int start, int limit, Object exampleEntity, int page) {

		return listWithEntityFiltering(dir, sort, start, limit, exampleEntity, page, false);
	}

	public Map listWithEntityFiltering(String dir, String sort, int start, int limit, Object exampleEntity, int page,
			boolean isReport) {

		Map params = new HashMap();
		addIsReportParam(params, isReport);
		addSortingPagination(params, reportEntity, dir, sort, start, limit, page);
		addEntityFiltering(params, filterExcludeProperties, exampleEntity, page);
		return reportDAO.list(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.report.IReportService#listWithEntityProjectionFiltering(
	 * java.lang.String, java.lang.String, int, int, java.lang.Object, int,
	 * java.util.Map)
	 */
	public Map listWithEntityProjectionFiltering(String dir, String sort, int start, int limit, Object exampleEntity,
			int page, Map projectionProperties) {

		Map params = new HashMap();

		if (projectionProperties != null && !projectionProperties.isEmpty()) {
			params.putAll(projectionProperties);
		}
		addSortingPagination(params, reportEntity, dir, sort, start, limit, page);
		addEntityFiltering(params, filterExcludeProperties, exampleEntity, page);
		return reportDAO.list(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.report.IReportService#listWithMultipleFiltering(java.lang
	 * .String, java.lang.String, int, int, java.util.Date, java.util.Date,
	 * java.lang.Object, int)
	 */
	public Map listWithMultipleFiltering(String dir, String sort, int start, int limit, Date startDate, Date endDate,
			Object exampleEntity, int page) {

		Map params = new HashMap();
		addSortingPagination(params, reportEntity, dir, sort, start, limit, page);
		addDateRangeFiltering(params, filterDateProperty, startDate, endDate);
		addEntityFiltering(params, filterExcludeProperties, exampleEntity, page);
		return reportDAO.list(params);
	}

	/**
	 * Adds the sorting pagination.
	 * 
	 * @param params
	 *            the params
	 * @param entity
	 *            the entity
	 * @param dir
	 *            the dir
	 * @param sort
	 *            the sort
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @param page
	 *            the page
	 */
	private void addSortingPagination(Map params, String entity, String dir, String sort, int start, int limit,
			int page) {

		params.put(IReportDAO.ENTITY, entity);
		params.put(IReportDAO.DIR, dir);
		params.put(IReportDAO.SORT_COLUMN, sort);
		params.put(IReportDAO.START_INDEX, start);
		params.put(IReportDAO.LIMIT, limit);
		params.put(IReportDAO.PAGE, page);
	}

	private void addIsReportParam(Map params, boolean isReport) {

		params.put(IReportDAO.IS_REPORT, isReport);
	}

	/**
	 * Adds the date range filtering.
	 * 
	 * @param params
	 *            the params
	 * @param dateProperty
	 *            the date property
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 */
	private void addDateRangeFiltering(Map params, String dateProperty, Date startDate, Date endDate) {

		// date range filter is enabled
		if (dateProperty != null) {

			params.put(IReportDAO.DATE_COLUMN, dateProperty);
			String check = (String) params.get(IReportDAO.DATE_COLUMN);
			if (check.equalsIgnoreCase("lastModifiedTime")) {
				// from date
				params.put(IReportDAO.FROM_DATE, null);
				// to date
				params.put(IReportDAO.TO_DATE, null);
			} else {
				// from date
				params.put(IReportDAO.FROM_DATE, startDate);
				// to date
				params.put(IReportDAO.TO_DATE, endDate);
			}

		}
	}

	/**
	 * Adds the entity multiple match.
	 * 
	 * @param params
	 *            the params
	 * @param shopMap
	 *            the shop map
	 */
	private void addEntityMultipleMatch(Map params, String[] shopMap) {

		params.put(IReportDAO.MULTIPLE_MATCH, shopMap);

	}

	/**
	 * Adds the entity filtering.
	 * 
	 * @param params
	 *            the params
	 * @param excludeProps
	 *            the exclude props
	 * @param exampleEntity
	 *            the example entity
	 * @param page
	 *            the page
	 */
	private void addEntityFiltering(Map params, List excludeProps, Object exampleEntity, int page) {

		params.put(IReportDAO.FILTER, exampleEntity);
		params.put(IReportDAO.EXCLUDE_PROP, excludeProps);
		params.put(IReportDAO.PAGE, page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ese.service.report.IReportService#listWithMultipleFiltering(java.lang
	 * .String, java.lang.String, int, int, java.util.Date, java.util.Date,
	 * com.sourcetrace.esesw.entity.txn.Txn, int, java.util.Map)
	 */
	public Map listWithMultipleFiltering(String sord, String sidx, int startIndex, int limit, Date startDate,
			Date endDate, Object exampleEntity, int page, Map projectionProperties) {

		Map params = new HashMap();
		if (projectionProperties != null && !projectionProperties.isEmpty()) {
			params.putAll(projectionProperties);
		}
		addSortingPagination(params, reportEntity, sord, sidx, startIndex, limit, page);
		addDateRangeFiltering(params, filterDateProperty, startDate, endDate);
		addEntityFiltering(params, filterExcludeProperties, exampleEntity, page);
		return reportDAO.list(params);
	}
	@Override
	public Map listWithMultipleFilteringExport(String sord, String sidx, int startIndex, int limit, Date startDate,
			Date endDate, Object exampleEntity, int page, Map projectionProperties) {

		Map params = new HashMap();
		if (projectionProperties != null && !projectionProperties.isEmpty()) {
			params.putAll(projectionProperties);
		}
		addSortingPagination(params, reportEntity, sord, sidx, startIndex, limit, page);
		addDateRangeFiltering(params, filterDateProperty, startDate, endDate);
		addEntityFiltering(params, filterExcludeProperties, exampleEntity, page);
		return reportDAO.listExport(params);
	}

	@Override
	public Map listWithProjectionFiltering(String sord, String sidx, int startIndex, int limit, Date startDate,
			Date endDate, Object exampleEntity, int page, Set<DynamicReportConfigDetail> dynamicReportConfigDetail) {
		Map params = new HashMap();
		params.put(IReportDAO.CONFIG_DETAIL, dynamicReportConfigDetail);
		addSortingPagination(params, reportEntity, sord, sidx, startIndex, limit, page);
		addDateRangeFiltering(params, filterDateProperty, startDate, endDate);
		addEntityFiltering(params, filterExcludeProperties, exampleEntity, page);
		return reportDAO.customList(params);
	}
	
	@Override
	public Map listWithProjectionFilteringView(String sord, String sidx, int startIndex, int limit, Date startDate,
			Date endDate, Object exampleEntity, int page,Map<String, Map> map) {
		Map params = new HashMap();
		setFilterDateProperty("TXN_DATE");
		params.put(IReportDAO.CONFIG_DETAIL, map.get(DynamicReportConfig.OTHER_FIELD).get(DynamicReportConfig.DYNAMIC_CONFIG_DETAIL));
		addSortingPagination(params, map.get(DynamicReportConfig.OTHER_FIELD).get(DynamicReportConfig.ENTITY).toString(), sord, sidx, startIndex, limit, page);
		addDateRangeFiltering(params, filterDateProperty, startDate, endDate);
		addEntityFiltering(params, filterExcludeProperties, exampleEntity, page);
		return reportDAO.listView(params,map.get(DynamicReportConfig.FILTER_FIELDS));
	}
	

	@Override
	public Map listWithProjectionFilteringStatic(String sord, String sidx, int startIndex, int limit, Date startDate,
			Date endDate, Object exampleEntity, int page,Map<String, Map> map) {
		Map params = new HashMap();
		//setFilterDateProperty("TXN_DATE");
		params.put(IReportDAO.CONFIG_DETAIL, map.get(DynamicReportConfig.OTHER_FIELD).get(DynamicReportConfig.DYNAMIC_CONFIG_DETAIL));
		params.put("alias", map.get(DynamicReportConfig.OTHER_FIELD).get(DynamicReportConfig.ALIAS));
		params.put("sortQry", map.get(DynamicReportConfig.OTHER_FIELD).get("sortQry"));
		params.put(IReportDAO.FILTER, map.get(DynamicReportConfig.FILTER_FIELDS));	
		params.put("groupProperty", map.get(DynamicReportConfig.OTHER_FIELD).get(DynamicReportConfig.GROUP_PROPERTY));
		addSortingPagination(params, map.get(DynamicReportConfig.OTHER_FIELD).get(DynamicReportConfig.ENTITY).toString(), sord, sidx, startIndex, limit, page);
		addDateRangeFiltering(params, filterDateProperty, startDate, endDate);
		addEntityFiltering(params, filterExcludeProperties, map.get(DynamicReportConfig.FILTER_FIELDS), page);
		return reportDAO.customListStatic(params,map.get(DynamicReportConfig.FILTER_FIELDS));
	}
	
	@SuppressWarnings("unchecked")
	public Map listWithMultipleFiltering(String sord, String sidx, int startIndex, int limit, Date startDate,
			Date endDate, Object exampleEntity, Map<String, String> filterMap, int page, Map projectionProperties) {
		Map params = new HashMap();
		if (projectionProperties != null && !projectionProperties.isEmpty()) {
			params.putAll(projectionProperties);
		}
		addSortingPagination(params, reportEntity, sord, sidx, startIndex, limit, page);
		addDateRangeFiltering(params, filterDateProperty, startDate, endDate);
		addEntityFiltering(params, filterExcludeProperties, exampleEntity, page, filterMap);
		return reportDAO.list(params);
	}

	private void addEntityFiltering(Map params, List excludeProps, Object exampleEntity, int page,
			Map<String, String> filterMap) {

		params.put(IReportDAO.FILTER, exampleEntity);
		params.put(IReportDAO.EXCLUDE_PROP, excludeProps);
		params.put(IReportDAO.PAGE, page);
		params.put(IReportDAO.FILTER_MAP, filterMap);
	}

	@Override
	public Map listWithTraceabilityFilteringData(String sord, String sidx, int startIndex, int limit, Date getsDate,
			Date geteDate, Object filter, int page, Map<String, Map> map, String branch) {
			Map params = new HashMap();
			return reportDAO.listTraceabilityData(params,map.get(DynamicReportConfig.FILTER_FIELDS), branch);
	}

	

}
