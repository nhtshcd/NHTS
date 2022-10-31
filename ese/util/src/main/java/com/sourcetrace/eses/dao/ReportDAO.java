/*
' * ReportDAO.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.dao;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.type.Type;
import org.json.simple.JSONObject;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.sourcetrace.eses.entity.DynamicReportConfigDetail;
import com.sourcetrace.eses.multitenancy.CoalesceAggregateProjection;
import com.sourcetrace.eses.multitenancy.ConcatProjection;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Transactional

public class ReportDAO extends HibernateDaoSupport implements IReportDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.dao.IReportDAO#list(java.util.Map)
	 */
	public Map list(Map params) {

		// create the query criteria
		Criteria criteria = createCriteria(params);

		// add date range filtering
		addDateRangeFiltering(criteria, params);

		// add sorting criteria
		addSorting(criteria, params);

		// add pagination criteria
		addPagination(criteria, params);

		// add example filtering
		addExampleFiltering(criteria, params);

		// add projections
		addPropertyProjections(criteria, params);

		// build the response map
		// total number of rows
		// list of rows

		Map data = new HashMap();
		List list = criteria.list();

		data.put(RECORDS, list);
		Object res = getRowCount(params);
		if (res instanceof Long) {
			data.put(RECORD_COUNT, (((Long) res)).intValue());
		} else if (res instanceof Object[]) {
			data.put(RECORD_COUNT, (Object[]) res);
		} else if (res instanceof Integer) {
			data.put(RECORD_COUNT, (Integer) res);
		} else {
			data.put(RECORD_COUNT, res);
		}
		data.put(PAGE_NUMBER, params.get(IReportDAO.PAGE));

		if (logger.isDebugEnabled()) {
			logger.debug("Criteria " + criteria);
			logger.debug("Response " + data);
		}

		return data;
	}

	public Map listExport(Map params) {

		// create the query criteria
		Criteria criteria = createCriteria(params);

		// add date range filtering
		addDateRangeFiltering(criteria, params);

		// add sorting criteria
		addSorting(criteria, params);

		// add pagination criteria
		addPagination(criteria, params);

		// add example filtering
		addExampleFiltering(criteria, params);

		// add projections
		addPropertyProjections(criteria, params);

		// build the response map
		// total number of rows
		// list of rows

		Map data = new HashMap();
		Stream list = criteria.list().stream();

		data.put(RECORDS, list);
		Object res = getRowCount(params);
		if (res instanceof Long) {
			data.put(RECORD_COUNT, (((Long) res)).intValue());
		} else if (res instanceof Object[]) {
			data.put(RECORD_COUNT, (Object[]) res);
		} else if (res instanceof Integer) {
			data.put(RECORD_COUNT, (Integer) res);
		} else {
			data.put(RECORD_COUNT, res);
		}
		data.put(PAGE_NUMBER, params.get(IReportDAO.PAGE));

		if (logger.isDebugEnabled()) {
			logger.debug("Criteria " + criteria);
			logger.debug("Response " + data);
		}

		return data;
	}

	protected Map addPropertyProjectionView(String criteria, Map params) {

		// otherProperties
		StringBuffer otherProperty = new StringBuffer();
		StringBuffer sumProperty = new StringBuffer();
		StringBuffer groupProperty = new StringBuffer();
		StringBuffer sumProp = new StringBuffer();
		StringBuffer sumPropLabel = new StringBuffer();
		StringBuffer innerSum = new StringBuffer();
		Set<DynamicReportConfigDetail> reportConfigDetailsSet = (Set<DynamicReportConfigDetail>) params
				.get(IReportDAO.CONFIG_DETAIL);
		reportConfigDetailsSet.stream().filter(config -> config.getIsGridAvailability())
				.sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder())).forEach(configDetailsSet -> {
					if (configDetailsSet.getSumProp() == null || configDetailsSet.getSumProp() == 0) {
						otherProperty.append((Projections.property(configDetailsSet.getField())) + ",");
					} else if (configDetailsSet.getSumProp() != 0) {
						otherProperty
								.append("FORMAT(SUM(" + (Projections.property(configDetailsSet.getField())) + "),2),");
					}
					if (configDetailsSet.getGroupProp() != 0) {

						groupProperty.append((Projections.property(configDetailsSet.getField())) + ",");
					} /*
						 * else if (configDetailsSet.getSumProp()!=null &&
						 * configDetailsSet.getSumProp() != 0) {
						 * sumProperty.append((Projections.property(
						 * configDetailsSet.getField())) + ","); }
						 */

					if (configDetailsSet.getIsFooterSum() != null && configDetailsSet.getIsFooterSum().equals("1")) {
						if (configDetailsSet.getAcessType() == 7) {
							String method = configDetailsSet.getMethod();
							if (method.contains("param")) {
								method = configDetailsSet.getMethod().replaceAll(":param1",
										configDetailsSet.getField());
							}
							innerSum.append("FORMAT(SUM(`" + (Projections.property(configDetailsSet.getField()))
									+ "`),2) as `" + (Projections.property(configDetailsSet.getField())) + "`,");
							sumProp.append(
									"(" + method + ") as `" + (Projections.property(configDetailsSet.getField())) + "`,");
							sumPropLabel.append((Projections.property(configDetailsSet.getLabelName())) + ",");
						} else {
							if (configDetailsSet.getSumProp() == null || configDetailsSet.getSumProp() == 0) {
								sumProp.append((Projections.property(configDetailsSet.getField())) + " as "
										+ (Projections.property(configDetailsSet.getField())) + ",");
							} else if (configDetailsSet.getSumProp() != 0) {
								sumProp.append("SUM(" + (Projections.property(configDetailsSet.getField())) + ") as "
										+ (Projections.property(configDetailsSet.getField())) + ",");
							}
							innerSum.append("FORMAT(SUM(" + (Projections.property(configDetailsSet.getField()))
									+ "),2) as " + (Projections.property(configDetailsSet.getField())) + ",");
							sumPropLabel.append((Projections.property(configDetailsSet.getLabelName())) + ",");
						}
					}
				});

		params.put(PROJ_SUM, sumProperty.toString());
		params.put(PROJ_GROUP, StringUtil.removeLastComma(groupProperty.toString()));
		params.put(PROJ_OTHERS, otherProperty.toString());
		if (sumProp != null && !StringUtil.isEmpty(sumProp) && StringUtil.removeLastComma(sumProp.toString()) != null
				&& !StringUtil.isEmpty(StringUtil.removeLastComma(sumProp.toString()))
				&& StringUtil.removeLastComma(sumPropLabel.toString()) != null
				&& !StringUtil.isEmpty(StringUtil.removeLastComma(sumPropLabel.toString()))) {
			params.put("foorterSum", StringUtil.removeLastComma(sumProp.toString()) + "~"
					+ StringUtil.removeLastComma(sumPropLabel.toString()));
		}
		if (innerSum != null && !StringUtil.isEmpty(innerSum) && StringUtil.removeLastComma(innerSum.toString()) != null
				&& !StringUtil.isEmpty(StringUtil.removeLastComma(innerSum.toString()))) {
			params.put("foorterInnerSum", StringUtil.removeLastComma(innerSum.toString()) + "~"
					+ StringUtil.removeLastComma(sumPropLabel.toString()));
		}
		return params;

	}

	int i = 0;

	public Map listView(Map params, Map filterProperty) {

		String query = "";
		params = addPropertyProjectionView(query, params);
		query = addProjectionsView(query, params);
		query = addDateRangeFilteringView(query, params);
		// query = addExampleFilteringView(query, params);
		query = addOtherFilterView(query, params, filterProperty);
		String countQye = query.split("from")[1];
		String mainCountQry = "";
		String finalFooterVal = query.split("from")[1];
		finalFooterVal = "SELECT COUNT(*) from " + finalFooterVal;
		String groupPropertyString = (String) params.get(PROJ_GROUP);
		if (groupPropertyString != null && !StringUtil.isEmpty(groupPropertyString)) {
			query = query + " group by " + groupPropertyString + " ";
			finalFooterVal = finalFooterVal + " group by " + groupPropertyString + " ";
			finalFooterVal = "SELECT COUNT(*) from (" + finalFooterVal+") t";
		}

		query = addSortingView(query, params);

		if (params.containsKey("foorterSum")) {
			// finalFooterVal = "SELECT COUNT(*) from " + finalFooterVal;
			countQye = "SELECT " + params.get("foorterSum").toString().split("~")[0] + " from " + countQye;

			countQye = addDateRangeFilteringView(countQye, params);
			countQye = addOtherFilterView(countQye, params, filterProperty);
			if (groupPropertyString != null && !StringUtil.isEmpty(groupPropertyString)) {
				countQye = countQye + " group by " + groupPropertyString + " ";
			}
			countQye = addSortingView(countQye, params);
			countQye = addPaginationView(countQye, params);
		} /*
			 * else { finalFooterVal = "SELECT COUNT(*) from " + finalFooterVal;
			 * }
			 */
		if (params.containsKey("foorterInnerSum")) {
			mainCountQry = "SELECT " + params.get("foorterInnerSum").toString().split("~")[0] + ",COUNT(*) from (";
		}

		query = addPaginationView(query, params);

		Map data = new HashMap();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<Object[]> list = session.createSQLQuery(query).list();
		JSONObject count = new JSONObject();
		if (params.containsKey("foorterSum")) {
			mainCountQry = mainCountQry + countQye + ")t";
			Object[] obj = (Object[]) session.createSQLQuery(mainCountQry).list().get(0);
			i = 0;
			BigInteger cObj = (BigInteger) session.createSQLQuery(finalFooterVal).list().get(0);
			count.put("count", (cObj).intValue());
			count.put("footers", new JSONObject());
			Arrays.asList(params.get("foorterSum").toString().split("~")[1].toString().split(",")).stream()
					.forEach(u -> {
						if (obj[i] != null) {
							((JSONObject) count.get("footers")).put(u, obj[i]);
						}

						i++;
					});

		} else {
			BigInteger obj = (BigInteger) session.createSQLQuery(finalFooterVal).list().get(0);
			count.put("count", (obj).intValue());
		}
		data.put(RECORDS, list);
		data.put(RECORD_COUNT, count);
		data.put(PAGE_NUMBER, params.get(IReportDAO.PAGE));

		if (logger.isDebugEnabled()) {
			logger.debug("Criteria " + query);
			logger.debug("Response " + data);
		}

		return data;
	}

	@Override
	public Map customListStatic(Map params, Map filterProperty) {
		// create the query criteria
		Criteria criteria = createCriteria(params);

		// add date range filtering
		addDateRangeFiltering(criteria, params);

		// add sorting criteria
		addSorting(criteria, params);

		// add pagination criteria
		addPagination(criteria, params);

		// add example filtering
		addExampleFiltering(criteria, params);

		// add projections
		addPropertyProjectionz(criteria, params);

		// build the response map
		// total number of rows
		// list of rows
		Map data = new HashMap();
		List list = criteria.list();

		data.put(RECORDS, list);
		data.put(RECORD_COUNT, getRowCountz(params));
		data.put(PAGE_NUMBER, params.get(IReportDAO.PAGE));

		if (logger.isDebugEnabled()) {
			logger.debug("Criteria " + criteria);
			logger.debug("Response " + data);
		}

		return data;
	}

	@Override
	public Map customList(Map params) {
		// create the query criteria
		Criteria criteria = createCriteria(params);

		// add date range filtering
		addDateRangeFiltering(criteria, params);

		// add sorting criteria
		addSorting(criteria, params);

		// add pagination criteria
		addPagination(criteria, params);

		// add example filtering
		addExampleFiltering(criteria, params);

		// add projections
		addPropertyProjectionz(criteria, params);

		// build the response map
		// total number of rows
		// list of rows
		Map data = new HashMap();
		List list = criteria.list();

		data.put(RECORDS, list);
		data.put(RECORD_COUNT, getRowCount(params));
		data.put(PAGE_NUMBER, params.get(IReportDAO.PAGE));

		if (logger.isDebugEnabled()) {
			logger.debug("Criteria " + criteria);
			logger.debug("Response " + data);
		}

		return data;
	}

	/**
	 * Creates the criteria.
	 * 
	 * @param params
	 *            the params
	 * @return the criteria
	 */
	protected Criteria createCriteria(Map params) {

		// the entity type
		String entity = (String) params.get(ENTITY);
		// create the criteria for the given entity type
		Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(entity);
		return criteria;
	}

	/**
	 * Adds the sorting.
	 * 
	 * @param criteria
	 *            the criteria
	 * @param params
	 *            the params
	 */
	protected void addSorting(Criteria criteria, Map params) {

		// sorting direction
		String dir = (String) params.get(DIR);
		// sorting column
		String sort = (String) params.get(SORT_COLUMN);

		// sort a column in the given direction ascending/descending
		if (dir != null && sort != null /* && !sort.contains(DELIMITER) */) {
			if (dir.equals(DESCENDING)) {
				// sort descending
				criteria.addOrder(Order.desc(sort));
			} else {
				// sort ascending
				criteria.addOrder(Order.asc(sort));
			}
		}
	}

	/**
	 * Adds the pagination.
	 * 
	 * @param criteria
	 *            the criteria
	 * @param params
	 *            the params
	 */
	protected void addPagination(Criteria criteria, Map params) {

		// starting record index
		int start = (Integer) params.get(START_INDEX);
		// page records limit
		int limit = (Integer) params.get(LIMIT);

		// pagination to be done
		if (limit != 0) {
			// set starting record index
			criteria.setFirstResult(start);
			// set the page records limit
			criteria.setMaxResults(limit);
		}
	}

	/**
	 * Adds the date range filtering.
	 * 
	 * @param criteria
	 *            the criteria
	 * @param params
	 *            the params
	 */
	protected void addDateRangeFiltering(Criteria criteria, Map params) {

		// from date
		Date from = (Date) params.get(FROM_DATE);
		// to date
		Date to = (Date) params.get(TO_DATE);
		// date property
		String dateProperty = (String) params.get(DATE_COLUMN);

		// date filtering to be done
		if (dateProperty != null && dateProperty.trim().length() > 0) {

			if (from != null && to != null) {
				// date range filter
				// txns whose device time between given from/to dates
				criteria.add(Expression.between(dateProperty, from, to));
			} else if (from != null) {
				// txns whose device time greater than/equal to given from date
				criteria.add(Expression.ge(dateProperty, from));
			} else if (to != null) {
				// txns whose device time less than/equal to given to date
				criteria.add(Expression.le(dateProperty, to));
			}
		}
	}

	/**
	 * Adds the example filtering.
	 * 
	 * @param criteria
	 *            the criteria
	 * @param params
	 *            the params
	 */
	protected void addExampleFiltering(Criteria criteria, Map params) {

		// check for filter entity
		Object entity = params.get(FILTER);
		if (entity != null) {
			// get the list of properties to be excluded
			List excludes = (List) params.get(EXCLUDE_PROP);
			// add entity based filtering
			addExampleFiltering(criteria, entity, excludes);
		}
	}

	/**
	 * Adds the example filtering.
	 * 
	 * @param criteria
	 *            the criteria
	 * @param entity
	 *            the entity
	 * @param excludes
	 *            the excludes
	 */
	protected void addExampleFiltering(Criteria criteria, Object entity, List excludes) {

		// custom property selector
		PropertySelector propertySelector = new PropertySelector() {

			public boolean include(Object value, String name, Type type) {

				boolean include = false;
				include = value != null;
				if (logger.isDebugEnabled()) {
					logger.debug("Filter " + name + ": [" + value + "] - " + include);
				}
				return include;
			}
		};

		// filtering of records to be done

		Example exampleEntity = Example.create(entity).setPropertySelector(propertySelector).excludeZeroes()
				.ignoreCase().enableLike(MatchMode.ANYWHERE);

		if (excludes != null) {
			// loop all exclude properties
			for (Object exclude : excludes) {
				// set the property to be excluded
				exampleEntity.excludeProperty(exclude.toString());
			}
		}

		// add the filters
		criteria.add(exampleEntity);

	}

	/**
	 * Adds the projections.
	 * 
	 * @param criteria
	 *            the criteria
	 * @param params
	 *            the params
	 */
	protected void addProjections(Criteria criteria, Map params) {

		String groupPropertyString = (String) params.get(PROJ_GROUP);
		ProjectionList list = Projections.projectionList();
		addGrouping(params, list);
		// to get row count
		list.add(Projections.rowCount());
		String sumPropertyString = (String) params.get(PROJ_SUM);

		String avgPropertyString = (String) params.get(PROJ_AVG);

		String otherPropertyString = (String) params.get(PROJ_OTHERS);
		if (!StringUtil.isEmpty(otherPropertyString)) {
			String[] otherProperties = otherPropertyString.split(SEPARATOR);
			for (String property : otherProperties) {
				list.add(Projections.property(property));
			}
		}
		if (!StringUtil.isEmpty(sumPropertyString)) {
			String[] sumProperties = sumPropertyString.split(SEPARATOR);
			for (String property : sumProperties) {
				list.add(Projections.sum(property));
			}
		}

		if (!StringUtil.isEmpty(avgPropertyString)) {
			String[] avgProperties = avgPropertyString.split(SEPARATOR);
			for (String property : avgProperties) {
				list.add(Projections.avg(property));
			}
		}

		if (!StringUtil.isEmpty(groupPropertyString) || !StringUtil.isEmpty(sumPropertyString)
				|| !StringUtil.isEmpty(avgPropertyString)) {
			criteria.setProjection(list);
		}

	}

	/**
	 * Adds the projections.
	 * 
	 * @param criteria
	 *            the criteria
	 * @param params
	 *            the params
	 */
	protected String addProjectionsView(String criteria, Map params) {

		String groupPropertyString = (String) params.get(PROJ_GROUP);
		ProjectionList list = Projections.projectionList();
		addGrouping(params, list);
		// to get row count
		list.add(Projections.rowCount());
		/*
		 * String sumPropertyString = (String) params.get(PROJ_SUM);
		 * 
		 * String avgPropertyString = (String) params.get(PROJ_AVG);
		 */
		String otherPropertyString = (String) params.get(PROJ_OTHERS);

		/*
		 * if (!StringUtil.isEmpty(sumPropertyString)) { String[] sumProperties
		 * = sumPropertyString.split(SEPARATOR); for (String property :
		 * sumProperties) { otherPropertyString = otherPropertyString + "SUM(" +
		 * property + "),"; } otherPropertyString =
		 * StringUtil.removeLastComma(otherPropertyString); }
		 */

		/*
		 * if (!StringUtil.isEmpty(avgPropertyString)) { String[] avgProperties
		 * = avgPropertyString.split(SEPARATOR); for (String property :
		 * avgProperties) { otherPropertyString = otherPropertyString +
		 * "AVERAGE(" + property + "),"; } otherPropertyString =
		 * StringUtil.removeLastComma(otherPropertyString); }
		 */

		otherPropertyString = StringUtil.removeLastComma(otherPropertyString);
		criteria = criteria + "select " + otherPropertyString + " from " + (String) params.get(ENTITY);

		return criteria;
	}

	/**
	 * Adds the date range filtering.
	 * 
	 * @param criteria
	 *            the criteria
	 * @param params
	 *            the params
	 */
	protected String addDateRangeFilteringView(String criteria, Map params) {

		// from date
		Date from = (Date) params.get(FROM_DATE);
		// to date
		Date to = (Date) params.get(TO_DATE);
		// date property
		String dateProperty = (String) params.get(DATE_COLUMN);
		// date filtering to be done
		if (dateProperty != null && dateProperty.trim().length() > 0) {

			if (from != null && to != null) {
				// date range filter
				// txns whose device time between given from/to dates
				criteria = criteria + "where " + dateProperty + " BETWEEN " + from + " AND " + to;
			} else if (from != null) {
				// txns whose device time greater than/equal to given from date
				criteria = criteria + "where " + dateProperty + " >= " + from;
				// criteria.add(Expression.ge(dateProperty, from));
			} else if (to != null) {
				// txns whose device time less than/equal to given to date
				criteria = criteria + "where " + dateProperty + " <= " + to;
				// criteria.add(Expression.le(dateProperty, to));
			}
		}
		return criteria;
	}

	protected String addOtherFilterView(String criteria, Map params, Map filters) {

		if (filters != null && filters.size() > 0) {
			Iterator it = filters.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				if (criteria.contains("where")) {
					criteria = criteria + " and " + pair.getKey() + " " + pair.getValue();
				} else {
					criteria = criteria + " where " + pair.getKey() + " " + pair.getValue();
				}

				it.remove();
			}

		}
		return criteria;
	}

	/**
	 * Adds the sorting.
	 * 
	 * @param criteria
	 *            the criteria
	 * @param params
	 *            the params
	 */
	protected String addSortingView(String criteria, Map params) {
		// sorting direction
		String dir = (String) params.get(DIR);
		// sorting column
		String sort = (String) params.get(SORT_COLUMN);

		// sort a column in the given direction ascending/descending
		if (dir != null && sort != null /* && !sort.contains(DELIMITER) */) {
			if (dir.equals(DESCENDING)) {
				// sort descending
				// criteria.addOrder(Order.desc(sort));
				criteria = criteria + " ORDER BY " + sort + " DESC";
			} else {
				// sort ascending
				// criteria.addOrder(Order.asc(sort));
				criteria = criteria + " ORDER BY " + sort + " ASC";
			}
		}
		return criteria;
	}

	/**
	 * Adds the pagination.
	 * 
	 * @param criteria
	 *            the criteria
	 * @param params
	 *            the params
	 */
	protected String addPaginationView(String criteria, Map params) {
		// starting record index
		int start = (Integer) params.get(START_INDEX);
		// page records limit
		int limit = (Integer) params.get(LIMIT);

		// pagination to be done
		if (limit != 0) {
			// set starting record index
			// criteria.setFirstResult(start);
			// set the page records limit
			// criteria.setMaxResults(limit);
			criteria = criteria + " LIMIT " + start + ", " + limit;
		}
		return criteria;
	}

	protected void addPropertyProjectionz(Criteria criteria, Map params) {
		ProjectionList list = Projections.projectionList();
		Set<DynamicReportConfigDetail> reportConfigDetailsSet = (Set<DynamicReportConfigDetail>) params
				.get(IReportDAO.CONFIG_DETAIL);
		reportConfigDetailsSet.stream().sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder()))
				.forEach(configDetailsSet -> {
					if (configDetailsSet.getGroupProp() == 0 && configDetailsSet.getSumProp() == 0) {
						if(configDetailsSet.getField().contains("##")){
						Arrays.asList(configDetailsSet.getField().split("##")).stream().forEach(uu ->{
						list.add(Projections.property(uu));
						});

						}else if(configDetailsSet.getField().contains("|")){
							if(configDetailsSet.getAlignment()!=null && !StringUtil.isEmpty(configDetailsSet.getAlignment())){
								list.add( new ConcatProjection(" ",configDetailsSet.getField().split("\\|")));
								
							}else{
								list.add( new ConcatProjection(configDetailsSet.getAlignment(),configDetailsSet.getField().split("\\|")));
								
							}
						}else{
						list.add(Projections.property(configDetailsSet.getField()));
						}
						} 
					 else if (configDetailsSet.getGroupProp() != 0) {
						list.add(Projections.groupProperty(configDetailsSet.getField()));
					} else if (!StringUtil.isEmpty(configDetailsSet.getSumProp() != 0)) {
						list.add(CoalesceAggregateProjection.sum(configDetailsSet.getField(), 0));
					}
				});
		criteria.setProjection(list);
	}

	protected void addPropertyProjections(Criteria criteria, Map params) {

		String groupPropertyString = (String) params.get(PROJ_GROUP);
		ProjectionList list = Projections.projectionList();
		addGrouping(params, list);
		// to get row count
		// list.add(Projections.rowCount());
		String sumPropertyString = (String) params.get(PROJ_SUM);
		String avgPropertyString = (String) params.get(PROJ_AVG);

		String otherPropertyString = (String) params.get(PROJ_OTHERS);
		if (!StringUtil.isEmpty(otherPropertyString)) {
			String[] otherProperties = otherPropertyString.split(SEPARATOR);
			for (String property : otherProperties) {
				list.add(Projections.property(property));
			}
		}
		if (!StringUtil.isEmpty(sumPropertyString)) {
			String[] sumProperties = sumPropertyString.split(SEPARATOR);
			for (String property : sumProperties) {
				list.add(Projections.sum(property));
			}
		}

		if (!StringUtil.isEmpty(avgPropertyString)) {
			String[] avgProperties = avgPropertyString.split(SEPARATOR);
			for (String property : avgProperties) {
				list.add(Projections.avg(property));
			}
		}
		if (!StringUtil.isEmpty(groupPropertyString) || !StringUtil.isEmpty(sumPropertyString)
				|| !StringUtil.isEmpty(avgPropertyString) || !StringUtil.isEmpty(otherPropertyString)) {
			criteria.setProjection(list);
		}

	}

	/**
	 * Adds the grouping.
	 * 
	 * @param params
	 *            the params
	 * @param list
	 *            the list
	 */
	protected void addGrouping(Map params, ProjectionList list) {

		String groupPropertyString = (String) params.get(PROJ_GROUP);
		if (!StringUtil.isEmpty(groupPropertyString)) {
			String[] groupProperties = groupPropertyString.split(SEPARATOR);
			for (String property : groupProperties) {
				list.add(Projections.groupProperty(property));
			}
		}

	}

	/**
	 * Gets the row count.
	 * 
	 * @param params
	 *            the params
	 * @return the row count
	 */
	protected Object getRowCount(Map params) {

		// create the criteria for the given entity type
		Criteria criteria = createCriteria(params);
		ProjectionList list = Projections.projectionList();
		// to get row count
		list.add(Projections.rowCount());
		addGrouping(params, list);
		criteria.setProjection(list);
		// apply the filters, if any
		// add example filtering
		addExampleFiltering(criteria, params);
		// add date range filtering
		addDateRangeFiltering(criteria, params);
		// total row count
		List results = criteria.list();
		String groupPropertyString = (String) params.get(PROJ_GROUP);
		if (!StringUtil.isEmpty(groupPropertyString)) {
			return ObjectUtil.isListEmpty(results) ? 0 : (results.size());
		} else {
			if (results.size() > 0) {
				if (results.get(0) instanceof Long) {
					return ObjectUtil.isListEmpty(results) ? 0 : (((Long) results.get(0))).intValue();
				} else if (results.get(0) instanceof Integer) {
					return ObjectUtil.isListEmpty(results) ? 0 : ((Integer) results.get(0));
				}
			}

		}
		return ObjectUtil.isListEmpty(results) ? 0 : (results.size());
	}

	protected Object getRowCountz(Map params) {

		// create the criteria for the given entity type
		Criteria criteria = createCriteria(params);
		ProjectionList list = Projections.projectionList();
		// to get row count
		list.add(Projections.rowCount());
		StringBuffer sumPropLabel = new StringBuffer();
		Set<DynamicReportConfigDetail> reportConfigDetailsSet = (Set<DynamicReportConfigDetail>) params
				.get(IReportDAO.CONFIG_DETAIL);
		reportConfigDetailsSet.stream()
				.filter(configDetailsSet -> configDetailsSet.getIsGridAvailability()
						&& configDetailsSet.getIsFooterSum() != null && configDetailsSet.getIsFooterSum().equals("1"))
				.sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder())).forEach(configDetailsSet -> {
					list.add(Projections.sum(configDetailsSet.getField()));
					sumPropLabel.append((Projections.property(configDetailsSet.getLabelName())) + ",");
				});
		if (sumPropLabel != null && sumPropLabel.length() > 0) {
			params.put("foorterSum", StringUtil.removeLastComma(sumPropLabel.toString()));
		}
		// addGrouping(params, list);
		criteria.setProjection(list);
		// apply the filters, if any
		// add example filtering
		addExampleFiltering(criteria, params);
		// add date range filtering
		addDateRangeFiltering(criteria, params);
		// total row count
		if (params.containsKey("foorterSum")) {
			Object[] obj = (Object[]) criteria.list().get(0);
			i = 1;
			JSONObject count = new JSONObject();
			count.put("count", ((Long) obj[0]).intValue());
			count.put("footers", new JSONObject());
			if (params.containsKey("foorterSum")) {
				Arrays.asList(params.get("foorterSum").toString().split(",")).stream().forEach(u -> {
					if (obj[i] != null) {
						((JSONObject) count.get("footers")).put(u, obj[i]);
					}

					i++;
				});

			}
			return count;
		} else {
			List results = criteria.list();
			String groupPropertyString = (String) params.get(PROJ_GROUP);
			if (!StringUtil.isEmpty(groupPropertyString)) {
				return ObjectUtil.isListEmpty(results) ? 0 : (results.size());
			} else {
				if (results.size() > 0) {
					if (results.get(0) instanceof Long) {
						return ObjectUtil.isListEmpty(results) ? 0 : (((Long) results.get(0))).intValue();
					} else if (results.get(0) instanceof Integer) {
						return ObjectUtil.isListEmpty(results) ? 0 : ((Integer) results.get(0));
					}
				}

			}
			return 0;
		}
	}

	@Override
	public Map listTraceabilityData(Map params, Map filterProperty, String branch) {
		// System.out.println("filterProperty: "+filterProperty);
		String qryString = "";
		String lNo = "";
		if (filterProperty != null && filterProperty.size() > 0) {
			Iterator it = filterProperty.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				if (pair.getValue() != null && !StringUtil.isEmpty(pair.getValue()) && !pair.getValue().equals(
						" = ''")) {/*
									 * if(pair.getKey().toString().
									 * equalsIgnoreCase("flp.LOT_NO") ||
									 * pair.getKey().toString().equalsIgnoreCase
									 * ("flp.PR_NO")){
									 * if(!StringUtil.isEmpty(pair.getValue().
									 * toString().trim()) &&
									 * pair.getValue().toString().trim()!="")
									 * qryString=qryString+" and"
									 * +" FIND_IN_SET("+pair.getValue().toString
									 * ().trim()+","+pair.getKey().toString().
									 * trim()+")"; } else qryString = qryString
									 * +" and "+ pair.getKey().toString().trim()
									 * + " "+ pair.getValue().toString();
									 * it.remove();
									 */
					if (pair.getKey().toString().equalsIgnoreCase("flp.LOT_NO")) {
						if (!StringUtil.isEmpty(pair.getValue().toString().trim())
								&& pair.getValue().toString().trim() != "")
							// AND CONCAT(",", flp.LOT_NO, ",") REGEXP
							// ",(l77|8|678|7899|ty52),"

							lNo = pair.getValue().toString().trim().replace(",", "|").replace("'", "");

						qryString = qryString + " and CONCAT(\",\"," + pair.getKey().toString().trim()
								+ ", \",\") REGEXP \",(" + lNo.trim() + "),\"";
					} else if (pair.getKey().toString().equalsIgnoreCase("flp.PR_NO")) {
						if (!StringUtil.isEmpty(pair.getValue().toString().trim())
								&& pair.getValue().toString().trim() != "")
							qryString = qryString + " and" + " FIND_IN_SET(" + pair.getValue().toString().trim() + ","
									+ pair.getKey().toString().trim() + ")";
					}
					/*
					 * if(pair.getKey().toString().equalsIgnoreCase(
					 * "flp.LOT_NO") ||
					 * pair.getKey().toString().equalsIgnoreCase("flp.PR_NO")){
					 * if(!StringUtil.isEmpty(pair.getValue().toString().trim())
					 * && pair.getValue().toString().trim()!="")
					 * qryString=qryString+" and"+" FIND_IN_SET("+pair.getValue(
					 * ).toString().trim()+","+pair.getKey().toString().trim()+
					 * ")"; }
					 */
					else
						qryString = qryString + " and " + pair.getKey().toString().trim() + " "
								+ pair.getValue().toString();
					it.remove();
				}
			}

		}
		String query = "";
		if (qryString.contains("LOT_NO") || qryString.contains("PR_NO")) {
			if (branch != null && !StringUtil.isEmpty(branch))
				query = "SELECT count(f.id) AS 'Total_Farmers',sum(CASE WHEN (f.AGE <= 30) THEN 1 ELSE 0 END) AS 'Young_Age', sum(CASE WHEN (f.AGE > 30 AND f.age <= 50) THEN 1 ELSE 0 END) AS 'Avg_Age', sum(CASE WHEN (f.AGE > 50) THEN 1 ELSE 0 END) AS 'Old_Age', sum(CASE WHEN (f.MARITAL_STATUS = 'CG00269') THEN 1 ELSE 0 END) AS 'Married', sum(CASE WHEN (f.MARITAL_STATUS = 'CG00270') THEN 1 ELSE 0 END) AS 'Unmarried', sum(CASE WHEN (f.MARITAL_STATUS = 'CG00292') THEN 1 ELSE 0 END) AS 'Widow', sum(CASE WHEN (f.MARITAL_STATUS = 'CG00520') THEN 1 ELSE 0 END) AS 'Divorce', sum(CASE WHEN (f.CATEGORY = 'CG00116') THEN 1 ELSE 0 END) AS 'General', sum(CASE WHEN (f.CATEGORY = 'CG00154') THEN 1 ELSE 0 END) AS 'SC', sum(CASE WHEN (f.CATEGORY = 'CG00155') THEN 1 ELSE 0 END) AS 'ST', sum(CASE WHEN (f.CATEGORY = 'CG00156') THEN 1 ELSE 0 END) AS 'BC', sum(CASE WHEN (f.CATEGORY = 'CG00157') THEN 1 ELSE 0 END) AS 'Minority', sum(CASE WHEN (f.CATEGORY = 'CG00158') THEN 1 ELSE 0 END) AS 'OC', sum(CASE WHEN (f.CATEGORY = 'CG00461') THEN 1 ELSE 0 END) AS 'OBC', sum(CASE WHEN (f.GENDER = 'FEMALE') THEN 1 ELSE 0 END) AS 'FEMALE', sum(CASE WHEN (f.GENDER = 'MALE') THEN 1 ELSE 0 END) AS 'MALE', sum(CASE WHEN (fc.HOUSING_TYPE = 'CG00141') THEN 1 ELSE 0 END) AS 'Kacha', sum(CASE WHEN (fc.HOUSING_TYPE = 'CG00142') THEN 1 ELSE 0 END) AS 'Semi Pakka', sum(CASE WHEN (fc.HOUSING_TYPE = 'CG00143') THEN 1 ELSE 0 END) AS 'Pakka', sum(CASE WHEN (fc.HOUSING_TYPE = '99') THEN 1 ELSE 0 END) AS 'Other', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('1',REPLACE(fc.DRINKING_WATER_SOURCE,', ',','))THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('1',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Borewell', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('2',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('2',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'River/Pond', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('3',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('3',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Tap', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('4',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('4',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Tubewell', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('5',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('5',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Well', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('6',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('6',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Drinking Water Other', CONCAT(CAST(sum(CASE WHEN (fc.ELECTRIFIED_HOUSE = '1') THEN 1 ELSE 0 END) AS CHAR), '(', (CONCAT(Round(((sum(CASE WHEN (fc.ELECTRIFIED_HOUSE = '1') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Electrified House', CONCAT(CAST(sum(CASE WHEN (fc.ELECTRIFIED_HOUSE = '2') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (fc.ELECTRIFIED_HOUSE = '2') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Not Electrified House', CONCAT(CAST(sum(CASE WHEN (fc.TOILET_AVAILABLE = '1') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (fc.TOILET_AVAILABLE = '1') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Toilet Avilable', CONCAT(CAST(sum(CASE WHEN (fc.TOILET_AVAILABLE = '2') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (fc.TOILET_AVAILABLE = '2') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Toilet Not Avilable', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00025') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00025') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'School', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00011') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00011') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Graduate and above', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00026') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00026') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Illiterate', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00027') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00027') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Primary school', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00150') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00150') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Secondary school', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00151') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00151') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Higher secondary', CONCAT(CAST(sum(CASE WHEN (f.IS_BENEFICIARY_IN_GOV_SCHEME = '1') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.IS_BENEFICIARY_IN_GOV_SCHEME = '1') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Gov Schemes', CONCAT(CAST(sum(CASE WHEN (f.IS_BENEFICIARY_IN_GOV_SCHEME = '2') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.IS_BENEFICIARY_IN_GOV_SCHEME = '2') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Not in Gov Schemes', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00098',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00098',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Firewood', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00099',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00099',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Kerosene', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00100',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00100',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'LPG', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00101',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00101',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Biogas', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00102',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00102',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'dry dung', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00505',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00505',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Crop Waste material', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('99',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('99',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Other Cooking Fuel', CONCAT(CAST(sum(CASE WHEN (f.CELL_PHONE = '1') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.CELL_PHONE = '1') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Cellphone', CONCAT(CAST(sum(CASE WHEN (f.CELL_PHONE = '2') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.CELL_PHONE = '2') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Not Cellphone', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00474',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00474',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Car', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00475',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00475',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Bike', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00476',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00476',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Cycle', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00477',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00477',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Tractor', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00544',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00544',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Other Vehicle', f.BRANCH_ID AS 'Branch' FROM farmer f LEFT JOIN farmer_economy fc ON fc.FARMER_ID = f.ID INNER JOIN city c ON c.id = f.CITY_ID INNER JOIN location_detail l ON l.id = c.LOCATION_ID INNER JOIN state s ON s.id = l.STATE_ID INNER JOIN  farmer_lot_pr flp on flp.farmerId=f.ID WHERE f.BRANCH_ID='"
						+ branch + "' and f.STATUS=1" + qryString;
			else {
				query = "SELECT count(f.id) AS 'Total_Farmers',sum(CASE WHEN (f.AGE <= 30) THEN 1 ELSE 0 END) AS 'Young_Age', sum(CASE WHEN (f.AGE > 30 AND f.age <= 50) THEN 1 ELSE 0 END) AS 'Avg_Age', sum(CASE WHEN (f.AGE > 50) THEN 1 ELSE 0 END) AS 'Old_Age', sum(CASE WHEN (f.MARITAL_STATUS = 'CG00269') THEN 1 ELSE 0 END) AS 'Married', sum(CASE WHEN (f.MARITAL_STATUS = 'CG00270') THEN 1 ELSE 0 END) AS 'Unmarried', sum(CASE WHEN (f.MARITAL_STATUS = 'CG00292') THEN 1 ELSE 0 END) AS 'Widow', sum(CASE WHEN (f.MARITAL_STATUS = 'CG00520') THEN 1 ELSE 0 END) AS 'Divorce', sum(CASE WHEN (f.CATEGORY = 'CG00116') THEN 1 ELSE 0 END) AS 'General', sum(CASE WHEN (f.CATEGORY = 'CG00154') THEN 1 ELSE 0 END) AS 'SC', sum(CASE WHEN (f.CATEGORY = 'CG00155') THEN 1 ELSE 0 END) AS 'ST', sum(CASE WHEN (f.CATEGORY = 'CG00156') THEN 1 ELSE 0 END) AS 'BC', sum(CASE WHEN (f.CATEGORY = 'CG00157') THEN 1 ELSE 0 END) AS 'Minority', sum(CASE WHEN (f.CATEGORY = 'CG00158') THEN 1 ELSE 0 END) AS 'OC', sum(CASE WHEN (f.CATEGORY = 'CG00461') THEN 1 ELSE 0 END) AS 'OBC', sum(CASE WHEN (f.GENDER = 'FEMALE') THEN 1 ELSE 0 END) AS 'FEMALE', sum(CASE WHEN (f.GENDER = 'MALE') THEN 1 ELSE 0 END) AS 'MALE', sum(CASE WHEN (fc.HOUSING_TYPE = 'CG00141') THEN 1 ELSE 0 END) AS 'Kacha', sum(CASE WHEN (fc.HOUSING_TYPE = 'CG00142') THEN 1 ELSE 0 END) AS 'Semi Pakka', sum(CASE WHEN (fc.HOUSING_TYPE = 'CG00143') THEN 1 ELSE 0 END) AS 'Pakka', sum(CASE WHEN (fc.HOUSING_TYPE = '99') THEN 1 ELSE 0 END) AS 'Other', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('1',REPLACE(fc.DRINKING_WATER_SOURCE,', ',','))THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('1',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Borewell', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('2',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('2',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'River/Pond', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('3',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('3',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Tap', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('4',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('4',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Tubewell', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('5',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('5',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Well', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('6',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('6',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Drinking Water Other', CONCAT(CAST(sum(CASE WHEN (fc.ELECTRIFIED_HOUSE = '1') THEN 1 ELSE 0 END) AS CHAR), '(', (CONCAT(Round(((sum(CASE WHEN (fc.ELECTRIFIED_HOUSE = '1') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Electrified House', CONCAT(CAST(sum(CASE WHEN (fc.ELECTRIFIED_HOUSE = '2') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (fc.ELECTRIFIED_HOUSE = '2') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Not Electrified House', CONCAT(CAST(sum(CASE WHEN (fc.TOILET_AVAILABLE = '1') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (fc.TOILET_AVAILABLE = '1') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Toilet Avilable', CONCAT(CAST(sum(CASE WHEN (fc.TOILET_AVAILABLE = '2') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (fc.TOILET_AVAILABLE = '2') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Toilet Not Avilable', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00025') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00025') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'School', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00011') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00011') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Graduate and above', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00026') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00026') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Illiterate', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00027') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00027') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Primary school', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00150') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00150') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Secondary school', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00151') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00151') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Higher secondary', CONCAT(CAST(sum(CASE WHEN (f.IS_BENEFICIARY_IN_GOV_SCHEME = '1') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.IS_BENEFICIARY_IN_GOV_SCHEME = '1') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Gov Schemes', CONCAT(CAST(sum(CASE WHEN (f.IS_BENEFICIARY_IN_GOV_SCHEME = '2') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.IS_BENEFICIARY_IN_GOV_SCHEME = '2') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Not in Gov Schemes', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00098',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00098',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Firewood', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00099',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00099',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Kerosene', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00100',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00100',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'LPG', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00101',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00101',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Biogas', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00102',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00102',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'dry dung', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00505',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00505',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Crop Waste material', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('99',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('99',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Other Cooking Fuel', CONCAT(CAST(sum(CASE WHEN (f.CELL_PHONE = '1') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.CELL_PHONE = '1') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Cellphone', CONCAT(CAST(sum(CASE WHEN (f.CELL_PHONE = '2') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.CELL_PHONE = '2') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Not Cellphone', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00474',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00474',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Car', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00475',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00475',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Bike', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00476',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00476',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Cycle', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00477',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00477',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Tractor', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00544',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00544',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Other Vehicle', f.BRANCH_ID AS 'Branch' FROM farmer f LEFT JOIN farmer_economy fc ON fc.FARMER_ID = f.ID INNER JOIN city c ON c.id = f.CITY_ID INNER JOIN location_detail l ON l.id = c.LOCATION_ID INNER JOIN state s ON s.id = l.STATE_ID INNER JOIN  farmer_lot_pr flp on flp.farmerId=f.ID WHERE f.STATUS=1"
						+ qryString;
				query = query + " GROUP BY f.BRANCH_ID";
			}
		} else {
			if (branch != null && !StringUtil.isEmpty(branch))
				query = "SELECT count(f.id) AS 'Total_Farmers',sum(CASE WHEN (f.AGE <= 30) THEN 1 ELSE 0 END) AS 'Young_Age', sum(CASE WHEN (f.AGE > 30 AND f.age <= 50) THEN 1 ELSE 0 END) AS 'Avg_Age', sum(CASE WHEN (f.AGE > 50) THEN 1 ELSE 0 END) AS 'Old_Age', sum(CASE WHEN (f.MARITAL_STATUS = 'CG00269') THEN 1 ELSE 0 END) AS 'Married', sum(CASE WHEN (f.MARITAL_STATUS = 'CG00270') THEN 1 ELSE 0 END) AS 'Unmarried', sum(CASE WHEN (f.MARITAL_STATUS = 'CG00292') THEN 1 ELSE 0 END) AS 'Widow', sum(CASE WHEN (f.MARITAL_STATUS = 'CG00520') THEN 1 ELSE 0 END) AS 'Divorce', sum(CASE WHEN (f.CATEGORY = 'CG00116') THEN 1 ELSE 0 END) AS 'General', sum(CASE WHEN (f.CATEGORY = 'CG00154') THEN 1 ELSE 0 END) AS 'SC', sum(CASE WHEN (f.CATEGORY = 'CG00155') THEN 1 ELSE 0 END) AS 'ST', sum(CASE WHEN (f.CATEGORY = 'CG00156') THEN 1 ELSE 0 END) AS 'BC', sum(CASE WHEN (f.CATEGORY = 'CG00157') THEN 1 ELSE 0 END) AS 'Minority', sum(CASE WHEN (f.CATEGORY = 'CG00158') THEN 1 ELSE 0 END) AS 'OC', sum(CASE WHEN (f.CATEGORY = 'CG00461') THEN 1 ELSE 0 END) AS 'OBC', sum(CASE WHEN (f.GENDER = 'FEMALE') THEN 1 ELSE 0 END) AS 'FEMALE', sum(CASE WHEN (f.GENDER = 'MALE') THEN 1 ELSE 0 END) AS 'MALE', sum(CASE WHEN (fc.HOUSING_TYPE = 'CG00141') THEN 1 ELSE 0 END) AS 'Kacha', sum(CASE WHEN (fc.HOUSING_TYPE = 'CG00142') THEN 1 ELSE 0 END) AS 'Semi Pakka', sum(CASE WHEN (fc.HOUSING_TYPE = 'CG00143') THEN 1 ELSE 0 END) AS 'Pakka', sum(CASE WHEN (fc.HOUSING_TYPE = '99') THEN 1 ELSE 0 END) AS 'Other', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('1',REPLACE(fc.DRINKING_WATER_SOURCE,', ',','))THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('1',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Borewell', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('2',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('2',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'River/Pond', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('3',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('3',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Tap', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('4',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('4',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Tubewell', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('5',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('5',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Well', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('6',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('6',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Drinking Water Other', CONCAT(CAST(sum(CASE WHEN (fc.ELECTRIFIED_HOUSE = '1') THEN 1 ELSE 0 END) AS CHAR), '(', (CONCAT(Round(((sum(CASE WHEN (fc.ELECTRIFIED_HOUSE = '1') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Electrified House', CONCAT(CAST(sum(CASE WHEN (fc.ELECTRIFIED_HOUSE = '2') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (fc.ELECTRIFIED_HOUSE = '2') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Not Electrified House', CONCAT(CAST(sum(CASE WHEN (fc.TOILET_AVAILABLE = '1') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (fc.TOILET_AVAILABLE = '1') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Toilet Avilable', CONCAT(CAST(sum(CASE WHEN (fc.TOILET_AVAILABLE = '2') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (fc.TOILET_AVAILABLE = '2') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Toilet Not Avilable', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00025') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00025') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'School', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00011') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00011') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Graduate and above', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00026') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00026') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Illiterate', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00027') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00027') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Primary school', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00150') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00150') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Secondary school', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00151') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00151') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Higher secondary', CONCAT(CAST(sum(CASE WHEN (f.IS_BENEFICIARY_IN_GOV_SCHEME = '1') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.IS_BENEFICIARY_IN_GOV_SCHEME = '1') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Gov Schemes', CONCAT(CAST(sum(CASE WHEN (f.IS_BENEFICIARY_IN_GOV_SCHEME = '2') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.IS_BENEFICIARY_IN_GOV_SCHEME = '2') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Not in Gov Schemes', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00098',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00098',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Firewood', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00099',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00099',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Kerosene', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00100',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00100',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'LPG', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00101',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00101',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Biogas', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00102',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00102',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'dry dung', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00505',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00505',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Crop Waste material', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('99',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('99',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Other Cooking Fuel', CONCAT(CAST(sum(CASE WHEN (f.CELL_PHONE = '1') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.CELL_PHONE = '1') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Cellphone', CONCAT(CAST(sum(CASE WHEN (f.CELL_PHONE = '2') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.CELL_PHONE = '2') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Not Cellphone', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00474',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00474',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Car', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00475',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00475',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Bike', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00476',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00476',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Cycle', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00477',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00477',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Tractor', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00544',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00544',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Other Vehicle', f.BRANCH_ID AS 'Branch' FROM farmer f LEFT JOIN farmer_economy fc ON fc.FARMER_ID = f.ID INNER JOIN city c ON c.id = f.CITY_ID INNER JOIN location_detail l ON l.id = c.LOCATION_ID INNER JOIN state s ON s.id = l.STATE_ID WHERE f.BRANCH_ID='"
						+ branch + "' and f.STATUS=1" + qryString;
			else {
				query = "SELECT count(f.id) AS 'Total_Farmers',sum(CASE WHEN (f.AGE <= 30) THEN 1 ELSE 0 END) AS 'Young_Age', sum(CASE WHEN (f.AGE > 30 AND f.age <= 50) THEN 1 ELSE 0 END) AS 'Avg_Age', sum(CASE WHEN (f.AGE > 50) THEN 1 ELSE 0 END) AS 'Old_Age', sum(CASE WHEN (f.MARITAL_STATUS = 'CG00269') THEN 1 ELSE 0 END) AS 'Married', sum(CASE WHEN (f.MARITAL_STATUS = 'CG00270') THEN 1 ELSE 0 END) AS 'Unmarried', sum(CASE WHEN (f.MARITAL_STATUS = 'CG00292') THEN 1 ELSE 0 END) AS 'Widow', sum(CASE WHEN (f.MARITAL_STATUS = 'CG00520') THEN 1 ELSE 0 END) AS 'Divorce', sum(CASE WHEN (f.CATEGORY = 'CG00116') THEN 1 ELSE 0 END) AS 'General', sum(CASE WHEN (f.CATEGORY = 'CG00154') THEN 1 ELSE 0 END) AS 'SC', sum(CASE WHEN (f.CATEGORY = 'CG00155') THEN 1 ELSE 0 END) AS 'ST', sum(CASE WHEN (f.CATEGORY = 'CG00156') THEN 1 ELSE 0 END) AS 'BC', sum(CASE WHEN (f.CATEGORY = 'CG00157') THEN 1 ELSE 0 END) AS 'Minority', sum(CASE WHEN (f.CATEGORY = 'CG00158') THEN 1 ELSE 0 END) AS 'OC', sum(CASE WHEN (f.CATEGORY = 'CG00461') THEN 1 ELSE 0 END) AS 'OBC', sum(CASE WHEN (f.GENDER = 'FEMALE') THEN 1 ELSE 0 END) AS 'FEMALE', sum(CASE WHEN (f.GENDER = 'MALE') THEN 1 ELSE 0 END) AS 'MALE', sum(CASE WHEN (fc.HOUSING_TYPE = 'CG00141') THEN 1 ELSE 0 END) AS 'Kacha', sum(CASE WHEN (fc.HOUSING_TYPE = 'CG00142') THEN 1 ELSE 0 END) AS 'Semi Pakka', sum(CASE WHEN (fc.HOUSING_TYPE = 'CG00143') THEN 1 ELSE 0 END) AS 'Pakka', sum(CASE WHEN (fc.HOUSING_TYPE = '99') THEN 1 ELSE 0 END) AS 'Other', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('1',REPLACE(fc.DRINKING_WATER_SOURCE,', ',','))THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('1',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Borewell', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('2',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('2',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'River/Pond', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('3',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('3',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Tap', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('4',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('4',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Tubewell', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('5',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('5',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Well', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('6',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('6',REPLACE(fc.DRINKING_WATER_SOURCE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Drinking Water Other', CONCAT(CAST(sum(CASE WHEN (fc.ELECTRIFIED_HOUSE = '1') THEN 1 ELSE 0 END) AS CHAR), '(', (CONCAT(Round(((sum(CASE WHEN (fc.ELECTRIFIED_HOUSE = '1') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Electrified House', CONCAT(CAST(sum(CASE WHEN (fc.ELECTRIFIED_HOUSE = '2') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (fc.ELECTRIFIED_HOUSE = '2') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Not Electrified House', CONCAT(CAST(sum(CASE WHEN (fc.TOILET_AVAILABLE = '1') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (fc.TOILET_AVAILABLE = '1') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Toilet Avilable', CONCAT(CAST(sum(CASE WHEN (fc.TOILET_AVAILABLE = '2') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (fc.TOILET_AVAILABLE = '2') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Toilet Not Avilable', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00025') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00025') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'School', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00011') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00011') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Graduate and above', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00026') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00026') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Illiterate', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00027') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00027') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Primary school', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00150') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00150') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Secondary school', CONCAT(CAST(sum(CASE WHEN (f.EDUCATION = 'CG00151') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.EDUCATION = 'CG00151') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Higher secondary', CONCAT(CAST(sum(CASE WHEN (f.IS_BENEFICIARY_IN_GOV_SCHEME = '1') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.IS_BENEFICIARY_IN_GOV_SCHEME = '1') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Gov Schemes', CONCAT(CAST(sum(CASE WHEN (f.IS_BENEFICIARY_IN_GOV_SCHEME = '2') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.IS_BENEFICIARY_IN_GOV_SCHEME = '2') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Not in Gov Schemes', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00098',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00098',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Firewood', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00099',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00099',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Kerosene', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00100',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00100',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'LPG', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00101',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00101',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Biogas', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00102',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00102',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'dry dung', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00505',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00505',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Crop Waste material', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('99',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('99',REPLACE(fc.COOKING_FUEL,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Other Cooking Fuel', CONCAT(CAST(sum(CASE WHEN (f.CELL_PHONE = '1') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.CELL_PHONE = '1') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Cellphone', CONCAT(CAST(sum(CASE WHEN (f.CELL_PHONE = '2') THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN (f.CELL_PHONE = '2') THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Not Cellphone', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00474',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00474',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Car', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00475',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00475',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Bike', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00476',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00476',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Cycle', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00477',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00477',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Tractor', CONCAT(CAST(sum(CASE WHEN FIND_IN_SET('CG00544',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END) AS CHAR), ' (', (CONCAT(Round(((sum(CASE WHEN FIND_IN_SET('CG00544',REPLACE(f.VEHICLE,', ',',')) THEN 1 ELSE 0 END)) / (count(f.id)) * 100), 2), '%')), ')') AS 'Other Vehicle', f.BRANCH_ID AS 'Branch' FROM farmer f LEFT JOIN farmer_economy fc ON fc.FARMER_ID = f.ID INNER JOIN city c ON c.id = f.CITY_ID INNER JOIN location_detail l ON l.id = c.LOCATION_ID INNER JOIN state s ON s.id = l.STATE_ID WHERE f.STATUS=1"
						+ qryString;
				query = query + " GROUP BY f.BRANCH_ID";
			}
		}
		System.out.println("query: " + query);
		Map data = new HashMap();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<Object[]> list = session.createSQLQuery(query).list();
		i = 1;
		/*
		 * JSONObject count = new JSONObject(); count.put("count", list.size());
		 * count.put("footers",new JSONObject());
		 */
		data.put(RECORDS, list);
		data.put(RECORD_COUNT, list.size());
		data.put(PAGE_NUMBER, 1);

		if (logger.isDebugEnabled()) {
			logger.debug("Criteria " + query);
			logger.debug("Response " + data);
		}

		return data;
	}
}
