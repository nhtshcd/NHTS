/*
 * DeviceReportDAO.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.dao;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sourcetrace.eses.entity.Device;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

/**
 * The Class DeviceReportDAO.
 */
public class DeviceReportDAO extends com.sourcetrace.eses.dao.ReportDAO {

	@Override
	protected Criteria createCriteria(Map params) {
		// TODO Auto-generated method stub
		return super.createCriteria(params);
	}

	/**
	 * Adds the example filtering.
	 * 
	 * @param criteria
	 *            the criteria
	 * @param params
	 *            the params
	 */
	protected void addExampleFiltering(Criteria criteria, Map params) {

		// check for filter entity
		Device entity = (Device) params.get(FILTER);
		if (entity != null) {

			criteria.createAlias("agent", "a", Criteria.LEFT_JOIN).createAlias("a.personalInfo", "p",
					Criteria.LEFT_JOIN);

			criteria.add(Restrictions.eq("deleted", false));

			if (!ObjectUtil.isListEmpty(entity.getBranchesList())) {
				criteria.add(Restrictions.in("branchId", entity.getBranchesList()));
			}

			if (!StringUtil.isEmpty(entity.getBranchId())) {
				criteria.add(Restrictions.eq("branchId", entity.getBranchId()));
			}

			if (entity.getCode() != null && !"".equals(entity.getCode()))
				criteria.add(Restrictions.like("code", entity.getCode(), MatchMode.ANYWHERE));

			if (entity.getName() != null && !"".equals(entity.getName()))
				criteria.add(Restrictions.like("name", entity.getName(), MatchMode.ANYWHERE));

			if (entity.getSerialNumber() != null && !"".equals(entity.getSerialNumber()))
				criteria.add(Restrictions.like("serialNumber", entity.getSerialNumber(), MatchMode.ANYWHERE));

			/*
			 * if(entity.getDeviceType()!=null &&
			 * !"".equals(entity.getDeviceType()))
			 * criteria.add(Restrictions.like("deviceType",
			 * entity.getDeviceType(), MatchMode.ANYWHERE));
			 */

			if (entity.getFilterStatus() != null && !"".equals(entity.getFilterStatus())) {
				if (entity.getFilterStatus().equals("enabled")) {
					criteria.add(Restrictions.like("enabled", entity.getEnabled()));
				} else {
					criteria.add(Restrictions.like("enabled", entity.getEnabled()));
				}
			}

			if (!ObjectUtil.isEmpty(entity.getAgent()) && !ObjectUtil.isEmpty(entity.getAgent().getPersonalInfo())) {

				if (!StringUtil.isEmpty(entity.getAgent().getPersonalInfo().getFirstName())) {

					criteria.add(Restrictions.or(
							Restrictions.like("p.firstName", entity.getAgent().getPersonalInfo().getFirstName(),
									MatchMode.ANYWHERE),
							Restrictions.like("p.lastName", entity.getAgent().getPersonalInfo().getFirstName(),
									MatchMode.ANYWHERE),
							Restrictions.like("a.profileId", entity.getAgent().getPersonalInfo().getFirstName(),
									MatchMode.ANYWHERE)));

				}

			}
			if(entity.getName()!=null && !"".equals(entity.getName())){
				criteria.add(Restrictions.like("name", entity.getName(),MatchMode.ANYWHERE));
			}
			
			if(entity.getExporter() !=null && !"".equals(entity.getExporter().getId())){
				criteria.add(Restrictions.eq("exporter", entity.getExporter().getId()));
			}

			
			 HttpSession httpSession = ReflectUtil.getCurrentHttpSession();
			 Long role = (Long) httpSession.getAttribute("dealerId");
			 if (role != null && role > 0) {
				 criteria.createAlias("exporter", "exp",Criteria.LEFT_JOIN);
				 criteria.add(Restrictions.eq("exporter.id", role));
			 }
			 
			criteria.add(Restrictions.eq("isRegistered", entity.getIsRegistered()));
			if (entity.getIsRegistered() == Device.DEVICE_NOT_REGISTERED) {
				getHibernateTemplate().getSessionFactory().getCurrentSession()
						.disableFilter(ISecurityFilter.BRANCH_FILTER);
			}
			if (entity.getAgent()!=null && entity.getAgent().getExporter() != null && entity.getAgent().getExporter().getId() != null
					&& entity.getAgent().getExporter().getId() > 0) {
				criteria.createAlias("a.exporter", "ex",Criteria.LEFT_JOIN);
				criteria.add(Restrictions.or(Restrictions.eq("ex.id", entity.getAgent().getExporter().getId()), Restrictions.isNull("a.id")));
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
}
