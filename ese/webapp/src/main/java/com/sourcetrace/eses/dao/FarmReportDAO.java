package com.sourcetrace.eses.dao;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.Farmer;

public class FarmReportDAO extends ReportDAO {
	protected void addExampleFiltering(Criteria criteria, Map params) {
		Farm entity = (Farm) params.get(FILTER);

		criteria.add(Restrictions.in("status",
				new Integer[] { Farmer.Status.ACTIVE.ordinal(), Farmer.Status.INACTIVE.ordinal() }));
		if (entity.getFarmer() != null && entity.getFarmer().getId() != null && entity.getFarmer().getId() > 0) {
			criteria.createAlias("farmer", "f");
			criteria.add(Restrictions.eq("f.id", entity.getFarmer().getId()));
		}
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