package com.sourcetrace.eses.multitenancy;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;

import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

public class StatusOrder extends Order {
	public StatusOrder() {
        super("", true);
    }

    @Override
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
    	if(ReflectUtil.getCurrentHttpSession().getAttribute(ISecurityFilter.IS_ADMIN)!=null && !StringUtil.isEmpty(ReflectUtil.getCurrentHttpSession().getAttribute(ISecurityFilter.IS_ADMIN)) && ReflectUtil.getCurrentHttpSession().getAttribute(ISecurityFilter.IS_ADMIN).equals("true")){
    		return " case when this_.status = 0 then 1 "
              		+ "  when this_.status = 4 then 2"
                   	+ " else 3 end ";
    	}else if(ReflectUtil.getCurrentHttpSession().getAttribute(ISecurityFilter.INSP_ID)!=null && !StringUtil.isEmpty(ReflectUtil.getCurrentHttpSession().getAttribute(ISecurityFilter.INSP_ID))){
        	
    		return " case when this_.status = 3 then 1 "
              	   	+ " else 3 end";
    	}else{
    		return "this_.id";
    	}
    	  
    }
}
