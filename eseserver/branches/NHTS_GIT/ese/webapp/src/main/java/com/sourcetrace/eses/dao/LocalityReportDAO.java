/**
 * LocalityReportDAO.java
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
import com.sourcetrace.eses.entity.Locality;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;


/**
 * The Class LocalityReportDAO.
 */
public class LocalityReportDAO extends ReportDAO {

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
		Locality entity = (Locality) params.get(FILTER);

		if (entity != null) {

			criteria.createAlias("state", "s").createAlias("s.country", "c");
			
			if (!ObjectUtil.isListEmpty(entity.getBranchesList())) {
				criteria.add(Restrictions.in("branchId", entity.getBranchesList()));
			}
			
			if (!StringUtil.isEmpty(entity.getBranchId())){
			    criteria.add(Restrictions.eq("branchId", entity.getBranchId()));
			}
			
			if (entity.getCode() != null && !"".equals(entity.getCode()))
				criteria.add(Restrictions.like("code", entity.getCode(),
						MatchMode.ANYWHERE));


			if (entity.getName() != null && !"".equals(entity.getName()))
				criteria.add(Restrictions.like("name", entity.getName(),
						MatchMode.ANYWHERE));

			if (entity.getState() != null
					&& entity.getState().getName() != null
					&& !"".equals(entity.getState().getName()))
				criteria.add(Restrictions.like("s.name", entity.getState()
						.getName(), MatchMode.ANYWHERE));

			if (entity.getState() != null
					&& entity.getState().getCountry() != null
					&& entity.getState().getCountry().getName() != null
					&& !"".equals(entity.getState().getCountry().getName()))
				criteria.add(Restrictions.like("c.name", entity.getState()
						.getCountry().getName(), MatchMode.ANYWHERE));

			// sorting direction
		/*	String dir = (String) params.get(DIR);
			// sorting column
			String sort = (String) params.get(SORT_COLUMN);
			// sort a column in the given direction ascending/descending
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
