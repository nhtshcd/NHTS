/*
 * AnimalHusbandaryReportDAO.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sourcetrace.eses.multitenancy.OrderBySqlFormula;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

public class DynamicViewReportDAO extends ReportDAO {

	protected Criteria createCriteria(Map params) {

		String entity = (String) params.get(ENTITY);
		Object object = (Object) params.get(FILTER);

		Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(entity);

		return criteria;
	}

	protected void addExampleFiltering(Criteria criteria, Map params) {

		Object object = (Object) params.get(FILTER);
		if (!ObjectUtil.isEmpty(object)) {

			Map<String, String> filters = (Map<String, String>) object;
			filters.entrySet().stream().filter(uu -> !uu.getKey().equals("hcdFilter")).forEach(u -> {
				if (u.getValue().contains("~")) {
					String cond = u.getValue().split("~")[0].trim();
					String value = u.getValue().split("~")[1].trim();
					Object valuee = u.getValue().split("~")[1].trim();

					if (u.getKey().contains("|")) {
						List<Criterion> crlist = new ArrayList<>();
						// Arrays.asList(u.getKey().split("\\|")).stream().forEa
						Arrays.asList(u.getKey().split("\\|")).stream().forEach(uu -> {
							Criterion crr = getRestriction(cond, uu, value, valuee, u);
							if (crr != null)
								crlist.add(crr);
						});
						Criterion[] itemsArray = new Criterion[crlist.size()];
						itemsArray = crlist.toArray(itemsArray);
						criteria.add(Restrictions.or(itemsArray));

					} else if (u.getKey().contains("~")) {
						List<Criterion> crlist = new ArrayList<>();
						// Arrays.asList(u.getKey().split("\\|")).stream().forEa
						Arrays.asList(u.getKey().split("~")).stream().forEach(uu -> {
							Criterion crr = getRestriction(cond, uu, value, valuee, u);
							if (crr != null)
								crlist.add(crr);
						});
						Criterion[] itemsArray = new Criterion[crlist.size()];
						itemsArray = crlist.toArray(itemsArray);
						criteria.add(Restrictions.or(itemsArray));
					} else {
						Criterion crr = getRestriction(cond, u.getKey(), value, valuee, u);
						if (crr != null)
							criteria.add(crr);
					}

				}
			});

			filters.entrySet().stream().filter(uu -> uu.getKey().equals("hcdFilter")).forEach(ff -> {
				String[] vall = ff.getValue().split("##");
				List<Criterion> crlist = new ArrayList<>();
				final Map<String, String> filtersList = new HashMap<>();
				Arrays.asList(vall).stream().forEach(u -> {
					filtersList.put(u.split("~")[0], u.split("~")[1].toString() + "~" + u.split("~")[3].toString() + "~"
							+ u.split("~")[2].toString());

				});
				filtersList.entrySet().forEach(u -> {
					String cond = u.getValue().split("~")[0].trim();
					String value = u.getValue().split("~")[1].trim();
					Object valuee = u.getValue().split("~")[1].trim();

					crlist.add(getRestriction(cond, u.getKey(), value, valuee, u));
				});
				criteria.add(Restrictions.or(crlist.toArray(new Criterion[crlist.size()])));
			});

		}
		String alias = (String) params.get("alias");
		if (alias != null && !StringUtil.isEmpty(alias)) {
			String[] seperated = alias.split(",");
			for (String row : seperated) {
				String[] objectData = row.split("=");
				if (objectData != null && objectData.length == 3) {
					criteria.createAlias(objectData[1], objectData[0], Integer.valueOf(objectData[2]));
				} else if (objectData != null && objectData.length == 2) {
					criteria.createAlias(objectData[1], objectData[0]);
				}

			}

		}
	}

	private Criterion getRestriction(String cond, String key, String value, Object valuee, Entry<String, String> u) {

		if (cond.equals("1") && !StringUtil.isEmpty(u.getValue().split("~")[2])
				&& u.getValue().split("~")[2].trim().equalsIgnoreCase("2")) {
			return Restrictions.eq(key, value);
		} else if (cond.equals("1") && !StringUtil.isEmpty(u.getValue().split("~")[2])
				&& u.getValue().split("~")[2].trim().equalsIgnoreCase("3")) {
			List<Long> list = Arrays.asList(value.toString().split("\\|")).stream().map(s -> Long.parseLong(s.trim()))
					.collect(Collectors.toList());
			return Restrictions.in(key, list);
		} else if (cond.equals("1") && !StringUtil.isEmpty(u.getValue().split("~")[2])
				&& u.getValue().split("~")[2].trim().equalsIgnoreCase("1")) {
			return Restrictions.eq(key, Integer.parseInt(value));

		} else if (cond.equals("1") && !StringUtil.isEmpty(u.getValue().split("~")[2])
				&& u.getValue().split("~")[2].trim().equalsIgnoreCase("10")) {
			return Restrictions.eq(key, Boolean.parseBoolean(value));
		} else if (cond.equals("1") && !StringUtil.isEmpty(u.getValue().split("~")[2])
				&& u.getValue().split("~")[2].trim().equalsIgnoreCase("4")) {
			List<String> list = Arrays.asList(value.toString().split("\\|")).stream().map(s -> s.trim())
					.collect(Collectors.toList());
			return Restrictions.in(key, list);
		} else if (cond.equals("1") && !StringUtil.isEmpty(u.getValue().split("~")[2])
				&& u.getValue().split("~")[2].trim().equalsIgnoreCase("5")) {
			List<Integer> list = Arrays.asList(value.toString().split("\\|")).stream()
					.map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			return Restrictions.in(key, list);
		} else if (cond.equals("1") && !StringUtil.isEmpty(u.getValue().split("~")[2])
				&& u.getValue().split("~")[2].trim().equalsIgnoreCase("6")) {
			return Restrictions.not(Restrictions.eq(key, Integer.parseInt(value)));

		} else if (cond.equals("1")) {
			return Restrictions.eq(key, valuee);
		} else if (cond.equals("2")) {
			String[] dates = value.split("\\|");
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(dates[0] + " 00:00:01");
				endDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(dates[1] + " 23:59:59");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// =DateUtil.convertStringToDate(dates[0],"dd-MM-yyyy");
			return Restrictions.between(key, startDate, endDate);

		} else if (Arrays.asList("3", "4", "5", "6", "7").contains(cond)) {
			if (u.getValue().contains("~") && u.getValue().split("~").length > 2) {
				if (!StringUtil.isEmpty(u.getValue().split("~")[2])
						&& u.getValue().split("~")[2].trim().equalsIgnoreCase("1")) {
					if (cond.equals("4")) {
						return Restrictions.lt(key, Integer.parseInt(value));
					} else if (cond.equals("5")) {
						return Restrictions.le(key, Integer.parseInt(value));
					} else if (cond.equals("6")) {
						return Restrictions.ge(key, Integer.parseInt(value));
					} else if (cond.equals("3")) {
						return Restrictions.gt(key, Integer.parseInt(value));
					} else if (cond.equals("7")) {
						return Restrictions.eq(key, Integer.parseInt(value));
					}
				} else if (!StringUtil.isEmpty(u.getValue().split("~")[2])
						&& u.getValue().split("~")[2].trim().equalsIgnoreCase("12")) {
					if (cond.equals("4")) {
						return Restrictions.lt(key, Double.parseDouble(value));
					} else if (cond.equals("5")) {
						return Restrictions.le(key, Double.parseDouble(value));
					} else if (cond.equals("6")) {
						return Restrictions.ge(key, Double.parseDouble(value));
					} else if (cond.equals("3")) {
						return Restrictions.gt(key, Double.parseDouble(value));
					} else if (cond.equals("7")) {
						return Restrictions.eq(key, Double.parseDouble(value));
					}
				} else if (!StringUtil.isEmpty(u.getValue().split("~")[2])
						&& u.getValue().split("~")[2].trim().equalsIgnoreCase("11")) {
					if (cond.equals("4")) {
						return Restrictions.lt(key, value);
					} else if (cond.equals("5")) {
						return Restrictions.le(key, value);
					} else if (cond.equals("6")) {
						return Restrictions.ge(key, value);
					} else if (cond.equals("3")) {
						return Restrictions.gt(key, value);
					} else if (cond.equals("7")) {
						return Restrictions.eq(key, value);
					}
				}
			} else {
				if (cond.equals("7")) {
					if (key.equals("id") && valuee != null && !ObjectUtil.isEmpty(valuee)) {
						return Restrictions.eq(key, Long.valueOf(valuee.toString()));
					} else {
						return Restrictions.eq(key, valuee);
					}
				}
			}

		} else if (cond.equals("8")) {
			return Restrictions.ilike(key, "%" + value + "%", MatchMode.ANYWHERE);
		} else if (cond.equals("9")) {
			if (value == null || StringUtil.isEmpty(value) || value.equalsIgnoreCase("null")) {

			} else {
				return Restrictions.eq(key, value);
			}

		} else if (cond.equals("10")) {
			return Restrictions.in(key, Arrays.asList(value.toString().split(",")));
		} else if (cond.equals("11")) {
			String[] dates = value.split("\\|");
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[0] + " 00:00:01");
				endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates[1] + " 23:59:59");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// =DateUtil.convertStringToDate(dates[0],"dd-MM-yyyy");
			return Restrictions.between(key, startDate, endDate);
		} else if (cond.equals("12")) {
			return Restrictions.eq(key, Long.parseLong(value));
		} else if (cond.equals("13")) {
			return Restrictions.eq(key, Double.parseDouble(value));
		} else if (cond.equals("14")) {
			return Restrictions.eq(key, Integer.parseInt(value));
		}

		return null;
	}

	protected void addSorting(Criteria criteria, Map params) {

		// sorting direction
		String dir = (String) params.get(DIR);
		// sorting column
		String sort = (String) params.get(SORT_COLUMN);

		String sortQry = (String) params.get("sortQry");
		if (sortQry != null && !StringUtil.isEmpty(sortQry)) {
			criteria.addOrder(new OrderBySqlFormula(sortQry.replace("{cond}", dir)));
		} else {

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

	}

}
