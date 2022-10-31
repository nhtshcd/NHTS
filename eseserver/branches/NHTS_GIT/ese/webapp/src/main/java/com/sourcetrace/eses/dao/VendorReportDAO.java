package com.sourcetrace.eses.dao;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sourcetrace.eses.dao.ReportDAO;
import com.sourcetrace.eses.entity.Vendor;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;


public class VendorReportDAO  extends ReportDAO
{
	 /**
     * Adds the example filtering.
     * @param criteria the criteria
     * @param params the params
     */
    @SuppressWarnings("unchecked")
    protected void addExampleFiltering(Criteria criteria, Map params) {

        // check for filter entity
        Vendor entity = (Vendor) params.get(FILTER);
        if (entity != null) {
        	

        	if (!ObjectUtil.isListEmpty(entity.getBranchesList())) {
				criteria.add(Restrictions.in("branchId", entity.getBranchesList()));
			}
			
			if (!StringUtil.isEmpty(entity.getBranchId())){
			    criteria.add(Restrictions.eq("branchId", entity.getBranchId()));
			}

            if (entity.getVendorId() != null && !"".equals(entity.getVendorId()))
                criteria.add(Restrictions.like("vendorId", entity.getVendorId(),
                        MatchMode.ANYWHERE));
            if (entity.getVendorName() != null && !"".equals(entity.getVendorName()))
                criteria.add(Restrictions.like("vendorName", entity.getVendorName(),
                        MatchMode.ANYWHERE));
            
            if (entity.getPersonName() != null && !"".equals(entity.getPersonName()))
                criteria.add(Restrictions.like("personName", entity.getPersonName(),
                        MatchMode.ANYWHERE));
            
            if (entity.getEmailId() != null && !"".equals(entity.getEmailId()))
                criteria.add(Restrictions.like("emailId", entity.getEmailId(),
                        MatchMode.ANYWHERE));
            
            if (entity.getVendorAddress() != null && !"".equals(entity.getVendorAddress()))
                criteria.add(Restrictions.like("vendorAddress", entity.getVendorAddress(),
                        MatchMode.ANYWHERE));
            
            if (entity.getMobileNo() != null && !"".equals(entity.getMobileNo()))
                criteria.add(Restrictions.like("mobileNo", entity.getMobileNo(),
                        MatchMode.ANYWHERE));



           
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
