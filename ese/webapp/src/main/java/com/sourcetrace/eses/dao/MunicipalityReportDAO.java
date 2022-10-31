/**
 * MunicipalityReportDAO.java
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
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;


/**
 * The Class MunicipalityReportDAO.
 */
public class MunicipalityReportDAO extends ReportDAO {

    /**
     * Adds the example filtering.
     * @param criteria the criteria
     * @param params the params
     */
    protected void addExampleFiltering(Criteria criteria, Map params) {

        // check for filter entity
        Municipality entity = (Municipality) params.get(FILTER);
        criteria.createAlias("locality", "l").
        createAlias("l.state", "state").
        createAlias("state.country", "country");
        

        if (entity != null) {
        	
        	if (!ObjectUtil.isListEmpty(entity.getBranchesList())) {
				criteria.add(Restrictions.in("branchId", entity.getBranchesList()));
			}
			
			if (!StringUtil.isEmpty(entity.getBranchId())){
			    criteria.add(Restrictions.eq("branchId", entity.getBranchId()));
			}
        	
           if (entity.getCode() != null && !"".equals(entity.getCode()))
                criteria.add(Restrictions.like("code", entity.getCode(), MatchMode.ANYWHERE));

            if (entity.getName() != null && !"".equals(entity.getName()))
                criteria.add(Restrictions.like("name", entity.getName(), MatchMode.ANYWHERE));

            if (entity.getLocality() != null && entity.getLocality().getName() != null
                    && !"".equals(entity.getLocality().getName()))
                criteria.add(Restrictions.like("l.name", entity.getLocality().getName(),
                        MatchMode.ANYWHERE));
            
            
            if (entity.getStateName()!=null)
                criteria.add(Restrictions.like("state.name", entity.getStateName(),
                        MatchMode.ANYWHERE));
            
            
            if (entity.getCountryName()!=null)
                criteria.add(Restrictions.like("country.name", entity.getCountryName(),
                        MatchMode.ANYWHERE));

            
            // sorting direction
           /* String dir = (String) params.get(DIR);
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
