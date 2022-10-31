package com.sourcetrace.eses.dao;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sourcetrace.eses.dao.ReportDAO;
import com.sourcetrace.eses.entity.Customer;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerReportDAO.
 */
public class CustomerReportDAO extends ReportDAO {

	@SuppressWarnings("unchecked")
	protected void addExampleFiltering(Criteria criteria, Map params) {

		// check for filter entity
		Customer entity = (Customer) params.get(FILTER);
		if (entity != null) {

			// if (!ObjectUtil.isListEmpty(entity.getBranchesList())) {
			// criteria.add(Restrictions.in("branchId",
			// entity.getBranchesList()));
			// }

			if (!StringUtil.isEmpty(entity.getBranchId())) {
				criteria.add(Restrictions.eq("branchId", entity.getBranchId()));
			}

			if (entity.getCustomerId() != null && !"".equals(entity.getCustomerId()))
				criteria.add(Restrictions.like("customerId", entity.getCustomerId(), MatchMode.ANYWHERE));
			if (entity.getCustomerName() != null && !"".equals(entity.getCustomerName()))
				criteria.add(Restrictions.like("customerName", entity.getCustomerName(), MatchMode.ANYWHERE));
			if (entity.getPersonName() != null && !"".equals(entity.getPersonName()))
				criteria.add(Restrictions.like("personName", entity.getPersonName(), MatchMode.ANYWHERE));
			if (entity.getEmailId() != null && !"".equals(entity.getEmailId()))
				criteria.add(Restrictions.like("emailId", entity.getEmailId(), MatchMode.ANYWHERE));

			if (entity.getCustomerAddress() != null && !"".equals(entity.getCustomerAddress()))
				criteria.add(Restrictions.like("customerAddress", entity.getCustomerAddress(), MatchMode.ANYWHERE));
			if (entity.getMobileNo() != null && !"".equals(entity.getMobileNo()))
				criteria.add(Restrictions.like("mobileNo", entity.getMobileNo(), MatchMode.ANYWHERE));

			criteria.add(Restrictions.eq("status", 0));

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
