/*
 * AgentReportDAO.java
 * Copyright (c) 20013-2014, SourceTrace Systems, All Rights Reserved.
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
import org.hibernate.sql.JoinType;

import com.sourcetrace.eses.dao.ReportDAO;
import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentReportDAO.
 */
public class AgentReportDAO extends ReportDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.dao.ReportDAO#addExampleFiltering(org.hibernate.Criteria,
	 * java.util.Map)
	 */
	protected void addExampleFiltering(Criteria criteria, Map params) {

		// check for filter entity
		Agent entity = (Agent) params.get(FILTER);
		if (entity != null) {
			criteria.createAlias("personalInfo", "pI").createAlias("agentType", "aT", JoinType.LEFT_OUTER_JOIN)
					.createAlias("contInfo", "cI");

			if (!ObjectUtil.isListEmpty(entity.getBranchesList())) {
				criteria.add(Restrictions.in("branchId", entity.getBranchesList()));
			}

			if (!StringUtil.isEmpty(entity.getBranchId())) {
				criteria.add(Restrictions.eq("branchId", entity.getBranchId()));
			}
			if (entity.getProfileId() != null)
				criteria.add(Restrictions.like("profileId", entity.getProfileId(), MatchMode.ANYWHERE));

			if (entity.getPersonalInfo() != null) {

				String firstName = entity.getPersonalInfo().getFirstName();
				if (firstName != null && !"".equals(firstName)) {
					criteria.add(Restrictions.like("pI.firstName", firstName, MatchMode.ANYWHERE));
				}

				String lastName = entity.getPersonalInfo().getLastName();
				if (lastName != null && !"".equals(lastName)) {
					criteria.add(Restrictions.like("pI.lastName", lastName, MatchMode.ANYWHERE));
				}
			}

			if (!ObjectUtil.isEmpty(entity.getAgentType()) && entity.getAgentType().getId() > 0) {
				criteria.add(Restrictions.eq("aT.id", entity.getAgentType().getId()));
			}
			if (entity.getAgentType() != null && !ObjectUtil.isEmpty(entity.getAgentType())
					&& entity.getAgentType().getCode() != null && !StringUtil.isEmpty(entity.getAgentType().getCode())
					&& !entity.getAgentType().getCode().equals("-1")) {
				criteria.add(Restrictions.eq("aT.code", entity.getAgentType().getCode()));
			}
			if (entity.getBodStatus() != -1) {
				criteria.add(Restrictions.eq("bodStatus", entity.getBodStatus()));
			}

			if (entity.getBodStatus() != -1) {
				criteria.add(Restrictions.eq("bodStatus", entity.getBodStatus()));
			}

			if (entity.getStatus() != -1) {
				criteria.add(Restrictions.eq("status", entity.getStatus()));
			}

			if (entity.getExporter() != null && entity.getExporter().getId() != null
					&& entity.getExporter().getId() > 0) {
				criteria.createAlias("exporter", "ex");
				criteria.add(Restrictions.eq("ex.id", entity.getExporter().getId()));
			}
			if (entity.getContInfo() != null) {

				String mobileNo = entity.getContInfo().getMobileNumbere().trim();
				if (mobileNo != null && !"".equals(mobileNo)) {
					criteria.add(Restrictions.like("cI.mobileNumber", mobileNo, MatchMode.ANYWHERE));
				}
			}

			// sorting direction
			/*
			 * String dir = (String) params.get(DIR); // sorting column String
			 * sort = (String) params.get(SORT_COLUMN); if
			 * (dir.equals(DESCENDING)) { // sort descending
			 * criteria.addOrder(Order.desc(sort)); } else { // sort ascending
			 * criteria.addOrder(Order.asc(sort)); }
			 */
		}
	}

	private Criteria createAlias(String string, String string2) {
		// TODO Auto-generated method stub
		return null;
	}
}
