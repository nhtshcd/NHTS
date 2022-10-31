package com.sourcetrace.eses.dao;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sourcetrace.eses.dao.ReportDAO;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

public class ProcurementVarietyReportDAO extends ReportDAO {
	protected void addExampleFiltering(Criteria criteria, Map params) {
		ProcurementVariety entity = (ProcurementVariety) params.get(FILTER);

		if (entity != null) {

            criteria.createAlias("procurementProduct", "pp");
            if (!ObjectUtil.isEmpty(entity.getCode()))
                criteria.add(Restrictions.like("code", entity.getCode(), MatchMode.ANYWHERE));
            if (!ObjectUtil.isEmpty(entity.getName()))
                criteria.add(Restrictions.like("name", entity.getName(), MatchMode.ANYWHERE));
            
             
            if (!ObjectUtil.isEmpty(entity.getProcurementProduct()) && entity.getProcurementProduct().getName()!=null)
                criteria.add(Restrictions.like("pp.name", entity.getProcurementProduct().getName(), MatchMode.ANYWHERE));
            
            if (!ObjectUtil.isEmpty(entity.getProcurementProduct()) && entity.getProcurementProduct().getId()!=0)
                criteria.add(Restrictions.eq("pp.id", entity.getProcurementProduct().getId()));
            
         
            String dir = (String) params.get(DIR);
            // sorting column
            String sort = (String) params.get(SORT_COLUMN);
            if (dir != null && sort != null) {
            if ( dir.equals(DESCENDING)) {
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
