package com.sourcetrace.eses.dao;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sourcetrace.eses.dao.ReportDAO;
import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.util.StringUtil;

public class BranchMasterReportDAO extends ReportDAO {

	protected void addExampleFiltering(Criteria criteria, Map params) {
		BranchMaster entity = (BranchMaster) params.get(FILTER);
		if (entity != null) {
			if (entity.getBranchId() != null && !"".equals(entity.getBranchId())) {
				criteria.add(Restrictions.like("branchId", entity.getBranchId(), MatchMode.EXACT));
			}
			if (entity.getName() != null && !"".equals(entity.getName())) {
				criteria.add(Restrictions.like("name", entity.getName(), MatchMode.ANYWHERE));
			}
			if (entity.getContactPerson() != null && !"".equals(entity.getContactPerson())) {
				criteria.add(Restrictions.like("contactPerson", entity.getContactPerson(), MatchMode.ANYWHERE));
			}
			if (entity.getPhoneNo() != null && !"".equals(entity.getPhoneNo())) {
				criteria.add(Restrictions.like("phoneNo", entity.getPhoneNo(), MatchMode.ANYWHERE));
			}
			if (entity.getAddress() != null && !"".equals(entity.getAddress())) {
				criteria.add(Restrictions.like("address", entity.getAddress(), MatchMode.ANYWHERE));
			}
			if (!StringUtil.isEmpty(entity.getStatus())) {
				criteria.add(Restrictions.eq("status", entity.getStatus()));
			}
		}
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
