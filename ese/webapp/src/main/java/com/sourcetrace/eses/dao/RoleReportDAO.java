/**
 * RoleReportDAO.java
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
import com.sourcetrace.eses.entity.Role;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

/**
 * The Class RoleReportDAO.
 */
public class RoleReportDAO extends ReportDAO {

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
		Role entity = (Role) params.get(FILTER);

		if (entity != null) {

			if (!ObjectUtil.isListEmpty(entity.getBranchesList())) {
				criteria.add(Restrictions.in("branchId", entity.getBranchesList()));
			}
			
			if (!StringUtil.isEmpty(entity.getBranchId())){
			    criteria.add(Restrictions.eq("branchId", entity.getBranchId()));
			}
			
			if (entity.getName() != null && !"".equals(entity.getName()))
				criteria.add(Restrictions.like("name", entity.getName(),
						MatchMode.ANYWHERE));
			// sorting direction
			String dir = (String) params.get(DIR);
			// sorting column
			String sort = (String) params.get(SORT_COLUMN);
			// sort a column in the given direction ascending/descending
			if (dir != null && sort != null) {
				Criteria sortCriteria = null;
				if (sort.contains(DELIMITER)) {
					sort = sort.substring(sort.lastIndexOf(DELIMITER) + 1);
					if (dir.equals(DESCENDING)) {
						// sort descending
						sortCriteria.addOrder(Order.desc(sort));
					} else {
						// sort ascending
						sortCriteria.addOrder(Order.asc(sort));
					}
				}
			}
		}
	}
}
