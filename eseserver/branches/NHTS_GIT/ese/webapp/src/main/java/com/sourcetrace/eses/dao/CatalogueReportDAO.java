package com.sourcetrace.eses.dao;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.sourcetrace.eses.dao.ReportDAO;
import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.entity.FarmCatalogueMaster;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

public class CatalogueReportDAO extends ReportDAO {

	protected void addExampleFiltering(Criteria criteria, Map params) {

		// check for filter entity
		FarmCatalogue entity = (FarmCatalogue) params.get(FILTER);
		if (entity != null) {


			if (!StringUtil.isEmpty(entity.getBranchId())) {
				criteria.add(Restrictions.eq("branchId", entity.getBranchId()));
			}

			if (entity.getCode() != null && !"".equals(entity.getCode()))
				criteria.add(Restrictions.like("code", entity.getCode(), MatchMode.ANYWHERE));
			if (entity.getName() != null && !"".equals(entity.getName()))
				criteria.add(Restrictions.like("name", entity.getName(), MatchMode.ANYWHERE));
			if (entity.getDispName() != null && !"".equals(entity.getDispName()))
				criteria.add(Restrictions.like("dispName", entity.getDispName(), MatchMode.ANYWHERE));
			if (entity.getTypez() > 0) {
				criteria.add(Restrictions.eq("typez", entity.getTypez()));
			}
			if (!StringUtil.isEmpty(entity.getTypez())) {
				criteria.add(Restrictions.ne("typez", entity.getTypez()));
			}
			if (!StringUtil.isEmpty(entity.getStatus())) {
				criteria.add(Restrictions.eq("status", entity.getStatus()));
			}
			if (entity.getIsReserved() == 0) {
				criteria.add(Restrictions.eq("isReserved", entity.getIsReserved()));
			}
			/*if (entity.getMasterStatus() !=null && entity.getMasterStatus() > 0) {
				DetachedCriteria exampleSubquery = DetachedCriteria.forClass(FarmCatalogueMaster.class);
				exampleSubquery.add(Restrictions.eq("status",entity.getMasterStatus()));
				exampleSubquery.setProjection(Property.forName("id"));
				
				criteria.add(Subqueries.propertyIn("typez", exampleSubquery));
					 
			}else{
				DetachedCriteria exampleSubquery = DetachedCriteria.forClass(FarmCatalogueMaster.class);
				exampleSubquery.add(Restrictions.eq("status",entity.getMasterStatus()));
				exampleSubquery.setProjection(Property.forName("id"));
				
				criteria.add(Subqueries.propertyNotIn("typez", exampleSubquery));
			}*/

			String dir = (String) params.get(DIR);
			String sort = (String) params.get(SORT_COLUMN);

			if (dir != null && sort != null) {
				if (dir.equals(DESCENDING)) {
					criteria.addOrder(Order.desc(sort));
				} else {
					criteria.addOrder(Order.asc(sort));
				}
			}
		}
	}

}
