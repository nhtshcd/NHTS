package com.sourcetrace.eses.dao;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sourcetrace.eses.dao.ReportDAO;
import com.sourcetrace.eses.entity.ProcurementGrade;
import com.sourcetrace.eses.util.ObjectUtil;

public class ProcurementGradeReportDAO extends ReportDAO{
	protected void addExampleFiltering(Criteria criteria, Map params) {
		ProcurementGrade entity = (ProcurementGrade) params.get(FILTER);

		if (entity != null) {

            criteria.createAlias("procurementVariety", "pv").createAlias("pv.procurementProduct", "pp");
            if (!ObjectUtil.isEmpty(entity.getCode()))
                criteria.add(Restrictions.like("code", entity.getCode(), MatchMode.ANYWHERE));
            if (!ObjectUtil.isEmpty(entity.getName()))
                criteria.add(Restrictions.like("name", entity.getName(), MatchMode.ANYWHERE));
            
            if (!ObjectUtil.isEmpty(entity.getPrice()))
                criteria.add(Restrictions.eq("price", entity.getPrice()));
            
            /*if (!ObjectUtil.isEmpty(entity.getId()))
                criteria.add(Restrictions.eq("procurementVarietyId", entity.getId()));*/
            
            if (!ObjectUtil.isEmpty(entity.getProcurementVariety()) && entity.getProcurementVariety().getName()!=null )
                criteria.add(Restrictions.like("pv.name", entity.getProcurementVariety().getName(), MatchMode.ANYWHERE));
                      
            if (!ObjectUtil.isEmpty(entity.getProcurementVariety()) && entity.getProcurementVariety().getProcurementProduct() !=null && entity.getProcurementVariety().getProcurementProduct().getName()!=null )
                criteria.add(Restrictions.like("pp.name", entity.getProcurementVariety().getProcurementProduct().getName(), MatchMode.ANYWHERE));
            
            if (!ObjectUtil.isEmpty(entity.getProcurementVariety()) && entity.getProcurementVariety().getId()!=0 )
                criteria.add(Restrictions.eq("pv.id", entity.getProcurementVariety().getId()));
            
            // sorting direction
            String dir = (String) params.get(DIR);
            // sorting column
            String sort = (String) params.get(SORT_COLUMN);
            if (dir != null && sort != null) {
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
