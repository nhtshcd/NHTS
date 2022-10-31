/**
 * UserReportDAO.java
 * Copyright (c) 2008-2009, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.dao;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sourcetrace.eses.dao.ReportDAO;
import com.sourcetrace.eses.entity.User;
import com.sourcetrace.eses.util.StringUtil;

/**
 * The Class UserReportDAO.
 */
public class UserReportDAO extends ReportDAO {

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
		User entity = (User) params.get(FILTER);

		if (entity != null) {

			if (!StringUtil.isEmpty(entity.getBranchId())) {
				criteria.add(Restrictions.like("branchId", entity.getBranchId(), MatchMode.EXACT));
			}

			if (!entity.isAdmin()) {
				criteria.add(Restrictions.ne("username", "exec"));
			}

			if (entity.getUsername() != null
					&& !"".equals(entity.getUsername()))
				criteria.add(Restrictions.like("username",
						entity.getUsername(), MatchMode.ANYWHERE));

			criteria.createAlias("persInfo", "pI").createAlias(
					"contInfo", "cI").createAlias("role", "r",
					Criteria.LEFT_JOIN);

			if (entity.getFilterStatus() != null
					&& !"".equals(entity.getFilterStatus())) {
				if (entity.getFilterStatus().equals("enabled")) {
					criteria.add(Restrictions.like("enabled", entity
							.isEnabled()));
				} else {
					criteria.add(Restrictions.like("enabled", entity
							.isEnabled()));
				}
			}

			if (entity.getPersInfo() != null) {

				String firstName = entity.getPersInfo().getFirstName();
				if (firstName != null && !"".equals(firstName)) {
					criteria.add(Restrictions.like("pI.firstName", firstName,
							MatchMode.ANYWHERE));
				}

				String lastName = entity.getPersInfo().getLastName();
				if (lastName != null && !"".equals(lastName)) {
					criteria.add(Restrictions.like("pI.lastName", lastName,
							MatchMode.ANYWHERE));
				}
			}

			if (entity.getContInfo() != null) {
				String mobileNumber = entity.getContInfo().getMobileNumbere()
						.trim();
				if (mobileNumber != null && !"".equals(mobileNumber)) {
					criteria.add(Restrictions.like("cI.mobileNumber",
							mobileNumber, MatchMode.ANYWHERE));
				}
			}

			if (entity.getRole() != null && !"".equals(entity.getRole())) {
				// Criteria pCriteria = criteria.createCriteria("role");
				String name = entity.getRole().getName();
				if (name != null && !"".equals(name)) {
					criteria.add(Restrictions.like("r.name", name,
							MatchMode.ANYWHERE));
				}
			}

			if (!StringUtil.isEmpty(entity.getSearchPage())
					&& entity.getSearchPage().equalsIgnoreCase("smsList")) {
				criteria.add(Restrictions.and(Restrictions
						.ne("cI.mobileNumber", ""), Restrictions
						.isNotNull("cI.mobileNumber")));
			}
		/*	// sorting direction
			String dir = (String) params.get(DIR);
			// sorting column
			String sort = (String) params.get(SORT_COLUMN);
			if (dir.equals(DESCENDING)) {
				// sort descending
				criteria.addOrder(Order.desc(sort));
			} else {
				// sort ascending
				criteria.addOrder(Order.asc(sort));
			}*/
		}
	}
}
