/**
 * StateReportDAO.java
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
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.util.StringUtil;


// TODO: Auto-generated Javadoc
/**
 * The Class CountryReportDAO.
 */
public class StateReportDAO extends ReportDAO {

    /**
     * Adds the example filtering.
     * @param criteria the criteria
     * @param params the params
     */
    protected void addExampleFiltering(Criteria criteria, Map params) {

        // check for filter entity
        State entity = (State) params.get(FILTER);
        criteria.createAlias("country", "c");

        if (entity != null) {
        	
        	if (!StringUtil.isEmpty(entity.getBranchId())) {
				criteria.add(Restrictions.like("branchId", entity.getBranchId(), MatchMode.EXACT));
			}
        	
            if (entity.getName() != null && !"".equals(entity.getName()))
                criteria.add(Restrictions.like("name", entity.getName(), MatchMode.ANYWHERE));
            if (entity.getCode() != null && !"".equals(entity.getCode()))
                criteria.add(Restrictions.like("code", entity.getCode(), MatchMode.ANYWHERE));
           
            if (entity.getCountry() != null && entity.getCountry().getName() != null
                    && !"".equals(entity.getCountry().getName()))
                criteria.add(Restrictions.like("c.name", entity.getCountry().getName(),
                        MatchMode.ANYWHERE));

            // sorting direction
          /*  String dir = (String) params.get(DIR);
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
