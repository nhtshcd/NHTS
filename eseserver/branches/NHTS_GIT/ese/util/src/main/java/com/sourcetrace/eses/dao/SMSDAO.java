/*
 * SMSDAO.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */

package com.sourcetrace.eses.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.SMSHistory;
import com.sourcetrace.eses.entity.SmsGroupDetail;
import com.sourcetrace.eses.entity.SmsGroupHeader;
import com.sourcetrace.eses.entity.SmsTemplate;
import com.sourcetrace.eses.entity.User;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

/**
 * @author PANNEER
 */
@Repository
@Transactional
public class SMSDAO extends ESEDAO implements ISMSDAO {

    /**
     * Instantiates a new sMS service dao.
     * @param sessionFactory the session factory
     */
    @Autowired
    private SMSDAO(SessionFactory sessionFactory) {

        this.setSessionFactory(sessionFactory);
    }

    /*
     * (non-Javadoc)
     * @see com.sourcetrace.eses.util.sms.dao.ISMSDAO#listSMSHistoryByStatus(java.util.List)
     */
    @Override
    public List<SMSHistory> listSMSHistoryByStatus(List<String> status) {

        String queryString = "SELECT sH FROM SMSHistory sH INNER JOIN sH.smsHistoryDetails shd WHERE shd.status IN (:status) GROUP BY sH";
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(queryString);
        query.setParameterList("status", status);
        List list = query.list();

        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.sourcetrace.eses.util.sms.dao.ISMSDAO#findSmsHistoryById(long)
     */
    @Override
    public SMSHistory findSmsHistoryById(long id) {

        return (SMSHistory) find("from SMSHistory sh where sh.id=?", id);
    }

    @Override
    public SmsGroupHeader findGroupByName(String name) {

        return (SmsGroupHeader) find("from SmsGroupHeader sh where sh.name=?", name);
    }

    @Override
    public SmsGroupHeader findGroupHeaderById(long id) {

        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery("from SmsGroupHeader where id= :id");
        query.setLong("id", id);
        SmsGroupHeader groupHeader = (SmsGroupHeader) query.uniqueResult();

        return groupHeader;
    }

    @Override
    public List<Object[]> listSmsGroupHeader() {

        return list("select sgh.id,sgh.name from SmsGroupHeader sgh order by sgh.name asc");
    }

    @Override
    public SmsTemplate findSmsTemplateByName(String name) {

        return (SmsTemplate) find("from SmsTemplate st where st.name=?", name);
    }

    @Override
    public SmsTemplate findSmsTemplateById(long id) {

        return (SmsTemplate) find("from SmsTemplate s where s.id=?", id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SmsTemplate> listSmsTemplates() {

        return list("from SmsTemplate st order by st.name asc");
    }

    @Override
    public List<SmsGroupHeader> listGroupHeadersByIds(List<String> ids) {

        Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(SmsGroupHeader.class);
        criteria.add(Restrictions.in("id", ObjectUtil.convertStringList(ids)));
        List<SmsGroupHeader> groupHeaders = criteria.list();
        return groupHeaders;
    }

    @Override
    public long findGroupsMobileNumbersCount(List<String> groupIds) {

        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(
                "select count(*) from SmsGroupDetail gd where gd.smsGroup.id in (:ids)");
        query.setParameterList("ids", ObjectUtil.convertStringList(groupIds));
        List list = query.list();
        if (list.size() > 0) {
            return (Long) list.get(0);
        } else {
            return 0;
        }
    }

    @Override
    public List<Object> listFarmers(String sort, int startIndex, int limit, Farmer farmer) {

        Session session = getSessionFactory().getCurrentSession();
        Criteria cr = session.createCriteria(Farmer.class);
        cr.createAlias("village", "v");
        cr.createAlias("city", "c");
        cr.createAlias("c.locality", "l");
        cr.createAlias("l.state", "s");
        cr.createAlias("farms", "fa",cr.LEFT_JOIN);
        cr.createAlias("fa.farmCrops", "fc",cr.LEFT_JOIN);
        cr.createAlias("fc.procurementVariety", "pv",cr.LEFT_JOIN);
        cr.createAlias("pv.procurementProduct", "pp",cr.LEFT_JOIN);
        cr.add(Restrictions.isNotNull("mobileNumber"));
        cr.add(Restrictions.ne("mobileNumber", ""));

       /* if (!ObjectUtil.isEmpty(farmer)) {
            if (!StringUtil.isEmpty(farmer.getFirstName())) {
                cr.add(Restrictions.like("firstName", farmer.getFirstName(), MatchMode.ANYWHERE));
            }
            
            if(!StringUtil.isEmpty(farmer.getBranchId())){
                cr.add(Restrictions.like("branchId", farmer.getBranchId(), MatchMode.EXACT));
            }
            
            if (!StringUtil.isEmpty(farmer.getLastName())) {
                cr.add(Restrictions.like("lastName", farmer.getLastName(), MatchMode.ANYWHERE));
            }
            if (!StringUtil.isEmpty(farmer.getMobileNo())) {
                cr.add(Restrictions.like("mobileNumber", farmer.getMobileNo(),
                        MatchMode.ANYWHERE));
            }
            if (!StringUtil.isEmpty(farmer.getVillage())
                    && !StringUtil.isEmpty(farmer.getVillage().getName())) {
                cr.add(Restrictions.like("v.name", farmer.getVillage().getName(),
                        MatchMode.ANYWHERE));
            }
        }*/

        // cr.add(Restrictions.isNotEmpty("mobileNumber"));

        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.groupProperty("id"));
        //pl.add(Projections.property("id"));
        pl.add(Projections.property("firstName"));
        pl.add(Projections.property("lastName"));
        pl.add(Projections.property("mobileNumber"));
        pl.add(Projections.property("s.name"));
        pl.add(Projections.property("l.name"));
        pl.add(Projections.property("c.name"));
        pl.add(Projections.property("v.name"));
        pl.add(Projections.property("status"));
        pl.add(Projections.property("branchId"));
        pl.add(Projections.sqlProjection("ifnull(GROUP_CONCAT(pp8_.`NAME`),'NA') as cropNames", new String[] { "cropNames" },
				new Type[] { StringType.INSTANCE }));

        cr.setFirstResult(startIndex);
        cr.setMaxResults(limit);

        cr.setProjection(pl);

        List list = cr.list();
        return list;
    }

    @Override
    public Integer findFarmerMobileNumberCount(String sort, int startIndex, int limit, Farmer farmer) {

       /* Session session = getSessionFactory().getCurrentSession();
        return ((Long) session
                .createQuery(
                        "select count(*) from Farmer WHERE mobileNumber IS NOT NULL AND mobileNumber!=''")
                .uniqueResult()).intValue();*/
    	Session session = getSessionFactory().getCurrentSession();
        Criteria cr = session.createCriteria(Farmer.class);
        cr.createAlias("village", "v");
        cr.createAlias("city", "c");
        cr.createAlias("c.locality", "l");
        cr.createAlias("l.state", "s");
        cr.createAlias("farms", "fa",cr.LEFT_JOIN);
        cr.createAlias("fa.farmCrops", "fc",cr.LEFT_JOIN);
        cr.createAlias("fc.procurementVariety", "pv",cr.LEFT_JOIN);
        cr.createAlias("pv.procurementProduct", "pp",cr.LEFT_JOIN);
        cr.add(Restrictions.isNotNull("mobileNumber"));
        cr.add(Restrictions.ne("mobileNumber", ""));

        /*if (!ObjectUtil.isEmpty(farmer)) {
            if (!StringUtil.isEmpty(farmer.getFirstName())) {
                cr.add(Restrictions.like("firstName", farmer.getFirstName(), MatchMode.ANYWHERE));
            }
            
            if(!StringUtil.isEmpty(farmer.getBranchId())){
                cr.add(Restrictions.like("branchId", farmer.getBranchId(), MatchMode.EXACT));
            }
            
            if (!StringUtil.isEmpty(farmer.getLastName())) {
                cr.add(Restrictions.like("lastName", farmer.getLastName(), MatchMode.ANYWHERE));
            }
            if (!StringUtil.isEmpty(farmer.getMobileNo())) {
                cr.add(Restrictions.like("mobileNumber", farmer.getMobileNo(),
                        MatchMode.ANYWHERE));
            }
            if (!StringUtil.isEmpty(farmer.getVillage())
                    && !StringUtil.isEmpty(farmer.getVillage().getName())) {
                cr.add(Restrictions.like("v.name", farmer.getVillage().getName(),
                        MatchMode.ANYWHERE));
            }
        }*/

        // cr.add(Restrictions.isNotEmpty("mobileNumber"));

        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.groupProperty("id"));
        //pl.add(Projections.property("id"));
        pl.add(Projections.property("firstName"));
        pl.add(Projections.property("lastName"));
        pl.add(Projections.property("mobileNumber"));
        pl.add(Projections.property("s.name"));
        pl.add(Projections.property("l.name"));
        pl.add(Projections.property("c.name"));
        pl.add(Projections.property("v.name"));
        pl.add(Projections.property("status"));
        pl.add(Projections.property("branchId"));
        pl.add(Projections.sqlProjection("ifnull(GROUP_CONCAT(pp8_.`NAME`),'NA') as cropNames", new String[] { "cropNames" },
				new Type[] { StringType.INSTANCE }));

        cr.setFirstResult(startIndex);
        //cr.setMaxResults(limit);

        cr.setProjection(pl);

        List list = cr.list();
        return list.size();
    }
    @Override
    public Integer findAgentMobileNumberCount(String sort, int startIndex, int limit, Agent agent) {
        /*Session session = getSessionFactory().getCurrentSession();
        return ((Long) session
                .createQuery(
                        "select count(*) from Agent a INNER JOIN a.contactInfo cI WHERE cI.mobileNumber IS NOT NULL AND cI.mobileNumber!=''")
                .uniqueResult()).intValue();*/

        Session session = getSessionFactory().getCurrentSession();
        Criteria cr = session.createCriteria(Agent.class);
        cr.createAlias("contactInfo", "ci");
        cr.createAlias("personalInfo", "pi");
        
        cr.add(Restrictions.isNotNull("ci.mobileNumber"));
        cr.add(Restrictions.ne("ci.mobileNumber", ""));
        
        if(!ObjectUtil.isEmpty(agent)){
            if(!StringUtil.isEmpty(agent.getProfileId())){
                cr.add(Restrictions.like("profileId", agent
                        .getProfileId(), MatchMode.ANYWHERE));
            }
            
            if(!StringUtil.isEmpty(agent.getBranchId())){
                cr.add(Restrictions.like("branchId", agent.getBranchId(), MatchMode.EXACT));
            }
            
            if(!ObjectUtil.isEmpty(agent.getPersonalInfo())&&!StringUtil.isEmpty(agent.getPersonalInfo().getFirstName())){
                cr.add(Restrictions.like("pi.firstName", agent.getPersonalInfo().getFirstName(),
                        MatchMode.ANYWHERE));
            }
            
            if(!ObjectUtil.isEmpty(agent.getPersonalInfo())&&!StringUtil.isEmpty(agent.getPersonalInfo().getLastName())){
                cr.add(Restrictions.like("pi.lastName", agent.getPersonalInfo().getLastName(),
                        MatchMode.ANYWHERE));
            }
            if(!ObjectUtil.isEmpty(agent.getContInfo())&&!StringUtil.isEmpty(agent.getContInfo().getMobileNumbere())){
                cr.add(Restrictions.like("ci.mobileNumber", agent.getContInfo().getMobileNumbere(), MatchMode.ANYWHERE));
            }

            /*if (agent.getFilterStatus() != null && !"".equals(agent.getFilterStatus())) {
                  if (agent.getFilterStatus().equals("status")) {
                  cr.add(Restrictions.like("status", agent.getStatus()));
              }
           }*/
            
        }
        
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("id"));
        pl.add(Projections.property("profileId"));
        pl.add(Projections.property("pi.firstName"));
        pl.add(Projections.property("pi.lastName"));
        pl.add(Projections.property("ci.mobileNumber"));
        pl.add(Projections.property("status"));
        pl.add(Projections.property("branchId"));
        
        cr.setFirstResult(startIndex);
        //cr.setMaxResults(limit);

        cr.setProjection(pl);

        List list = cr.list();
        return list.size();
    
    }

    @Override
    public Integer findUserMobileNumberCount(String sort, int startIndex, int limit, User user) {
        /*Session session = getSessionFactory().getCurrentSession();
        return ((Long) session
                .createQuery(
                        "select count(*) from User u INNER JOIN u.contactInfo cI WHERE cI.mobileNumber IS NOT NULL AND cI.mobileNumber!=''")
                .uniqueResult()).intValue();*/


        Session session = getSessionFactory().getCurrentSession();
        Criteria cr = session.createCriteria(User.class);
        cr.createAlias("contactInfo", "ci");
        cr.createAlias("personalInfo", "pi");
        cr.add(Restrictions.isNotNull("ci.mobileNumber"));
        cr.add(Restrictions.ne("ci.mobileNumber", ""));
        /*cr.add(Restrictions.ne("ci.mobileNumber", ""));*/
        
        if(!ObjectUtil.isEmpty(user)){
            if(!StringUtil.isEmpty(user.getUsername())){
                cr.add(Restrictions.like("username", user.getUsername(), MatchMode.ANYWHERE));
            }
            
            if(!ObjectUtil.isEmpty(user.getPersInfo())&&!StringUtil.isEmpty(user.getPersInfo().getFirstName())){
                cr.add(Restrictions.like("pi.firstName", user.getPersInfo().getFirstName(),
                        MatchMode.ANYWHERE));
            }
            
            if(!ObjectUtil.isEmpty(user.getPersInfo())&&!StringUtil.isEmpty(user.getPersInfo().getLastName())){
                cr.add(Restrictions.like("pi.lastName", user.getPersInfo().getLastName(),
                        MatchMode.ANYWHERE));
            }
            
            if(!ObjectUtil.isEmpty(user.getContInfo())&&!StringUtil.isEmpty(user.getContInfo().getMobileNumbere())){
                cr.add(Restrictions.like("ci.mobileNumber", user.getContInfo().getMobileNumbere(), MatchMode.ANYWHERE));
            }
            
            if(!StringUtil.isEmpty(user.getBranchId())){
                cr.add(Restrictions.like("branchId", user.getBranchId(), MatchMode.EXACT));
            }
            
            /*if(!StringUtil.isEmpty(user.getStatus())){
                cr.add(Restrictions.eq("status", user.getStatus()));
            }*/
            /*if(!StringUtil.isEmpty(user.getTempStatus())){
            	if (user.getTempStatus().equals("1")) {
            		user.setEnabled(true); 
            		cr.add(Restrictions.eq("enabled", user.isEnabled()));
            		
    			} else {
    				user.setEnabled(false);
    				cr.add(Restrictions.eq("enabled", user.isEnabled()));
    			}
            }*/
        }
        
       /* if(!ObjectUtil.isEmpty(agent)){
            if(!StringUtil.isEmpty(agent.getProfileId())){
                cr.add(Restrictions.like("profileId", agent
                        .getProfileId(), MatchMode.ANYWHERE));
            }
            
            if(!ObjectUtil.isEmpty(agent.getPersonalInfo())&&!StringUtil.isEmpty(agent.getPersonalInfo().getFirstName())){
                cr.add(Restrictions.like("pi.firstName", agent.getPersonalInfo().getFirstName(),
                        MatchMode.ANYWHERE));
            }
            
            if(!ObjectUtil.isEmpty(agent.getPersonalInfo())&&!StringUtil.isEmpty(agent.getPersonalInfo().getLastName())){
                cr.add(Restrictions.like("pi.lastName", agent.getPersonalInfo().getLastName(),
                        MatchMode.ANYWHERE));
            }
            if(!ObjectUtil.isEmpty(agent.getContactInfo())&&!StringUtil.isEmpty(agent.getContactInfo().getMobileNumber())){
                cr.add(Restrictions.like("ci.mobileNumber", agent.getContactInfo().getMobileNumber(), MatchMode.ANYWHERE));
            }
        }*/
        
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("id"));
        pl.add(Projections.property("username"));
        pl.add(Projections.property("pi.firstName"));
        pl.add(Projections.property("pi.lastName"));
        pl.add(Projections.property("ci.mobileNumber"));
        pl.add(Projections.property("enabled"));
        pl.add(Projections.property("branchId"));
        
        cr.setFirstResult(startIndex);
       // cr.setMaxResults(limit);

        cr.setProjection(pl);

        List list = cr.list();
        return list.size();
    
    }
    @Override
    public List<Object> listAgents(String sort, int startIndex, int limit, Agent agent) {
        Session session = getSessionFactory().getCurrentSession();
        Criteria cr = session.createCriteria(Agent.class);
        cr.createAlias("contactInfo", "ci");
        cr.createAlias("personalInfo", "pi");
        
        cr.add(Restrictions.isNotNull("ci.mobileNumber"));
        cr.add(Restrictions.ne("ci.mobileNumber", ""));
        
        if(!ObjectUtil.isEmpty(agent)){
            if(!StringUtil.isEmpty(agent.getProfileId())){
                cr.add(Restrictions.like("profileId", agent
                        .getProfileId(), MatchMode.ANYWHERE));
            }
            
            if(!StringUtil.isEmpty(agent.getBranchId())){
                cr.add(Restrictions.like("branchId", agent.getBranchId(), MatchMode.EXACT));
            }
            
            if(!ObjectUtil.isEmpty(agent.getPersonalInfo())&&!StringUtil.isEmpty(agent.getPersonalInfo().getFirstName())){
                cr.add(Restrictions.like("pi.firstName", agent.getPersonalInfo().getFirstName(),
                        MatchMode.ANYWHERE));
            }
            
            if(!ObjectUtil.isEmpty(agent.getPersonalInfo())&&!StringUtil.isEmpty(agent.getPersonalInfo().getLastName())){
                cr.add(Restrictions.like("pi.lastName", agent.getPersonalInfo().getLastName(),
                        MatchMode.ANYWHERE));
            }
            if(!ObjectUtil.isEmpty(agent.getContInfo())&&!StringUtil.isEmpty(agent.getContInfo().getMobileNumbere())){
                cr.add(Restrictions.like("ci.mobileNumber", agent.getContInfo().getMobileNumbere(), MatchMode.ANYWHERE));
            }

            /*if (agent.getFilterStatus() != null && !"".equals(agent.getFilterStatus())) {
                  if (agent.getFilterStatus().equals("status")) {
                  cr.add(Restrictions.like("status", agent.getStatus()));
              }
           }*/
            
        }
        
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("id"));
        pl.add(Projections.property("profileId"));
        pl.add(Projections.property("pi.firstName"));
        pl.add(Projections.property("pi.lastName"));
        pl.add(Projections.property("ci.mobileNumber"));
        pl.add(Projections.property("status"));
        pl.add(Projections.property("branchId"));
        
        cr.setFirstResult(startIndex);
        cr.setMaxResults(limit);

        cr.setProjection(pl);

        List list = cr.list();
        return list;
    }

    @Override
    public List<Object> listUsers(String sort, int startIndex, int limit, User user) {

        Session session = getSessionFactory().getCurrentSession();
        Criteria cr = session.createCriteria(User.class);
        cr.createAlias("contactInfo", "ci");
        cr.createAlias("personalInfo", "pi");
        cr.add(Restrictions.isNotNull("ci.mobileNumber"));
        cr.add(Restrictions.ne("ci.mobileNumber", ""));
        /*cr.add(Restrictions.ne("ci.mobileNumber", ""));*/
        
        if(!ObjectUtil.isEmpty(user)){
            if(!StringUtil.isEmpty(user.getUsername())){
                cr.add(Restrictions.like("username", user.getUsername(), MatchMode.ANYWHERE));
            }
            
            if(!ObjectUtil.isEmpty(user.getPersInfo())&&!StringUtil.isEmpty(user.getPersInfo().getFirstName())){
                cr.add(Restrictions.like("pi.firstName", user.getPersInfo().getFirstName(),
                        MatchMode.ANYWHERE));
            }
            
            if(!ObjectUtil.isEmpty(user.getPersInfo())&&!StringUtil.isEmpty(user.getPersInfo().getLastName())){
                cr.add(Restrictions.like("pi.lastName", user.getPersInfo().getLastName(),
                        MatchMode.ANYWHERE));
            }
            
            if(!ObjectUtil.isEmpty(user.getContInfo())&&!StringUtil.isEmpty(user.getContInfo().getMobileNumbere())){
                cr.add(Restrictions.like("ci.mobileNumber", user.getContInfo().getMobileNumbere(), MatchMode.ANYWHERE));
            }
            
            if(!StringUtil.isEmpty(user.getBranchId())){
                cr.add(Restrictions.like("branchId", user.getBranchId(), MatchMode.EXACT));
            }
            
            /*if(!StringUtil.isEmpty(user.getStatus())){
                cr.add(Restrictions.eq("status", user.getStatus()));
            }*/
           /* if(!StringUtil.isEmpty(user.getTempStatus())){
            	if (user.getTempStatus().equals("1")) {
            		user.setEnabled(true); 
            		cr.add(Restrictions.eq("enabled", user.isEnabled()));
            		
    			} else {
    				user.setEnabled(false);
    				cr.add(Restrictions.eq("enabled", user.isEnabled()));
    			}
            }*/
        }
        
       /* if(!ObjectUtil.isEmpty(agent)){
            if(!StringUtil.isEmpty(agent.getProfileId())){
                cr.add(Restrictions.like("profileId", agent
                        .getProfileId(), MatchMode.ANYWHERE));
            }
            
            if(!ObjectUtil.isEmpty(agent.getPersonalInfo())&&!StringUtil.isEmpty(agent.getPersonalInfo().getFirstName())){
                cr.add(Restrictions.like("pi.firstName", agent.getPersonalInfo().getFirstName(),
                        MatchMode.ANYWHERE));
            }
            
            if(!ObjectUtil.isEmpty(agent.getPersonalInfo())&&!StringUtil.isEmpty(agent.getPersonalInfo().getLastName())){
                cr.add(Restrictions.like("pi.lastName", agent.getPersonalInfo().getLastName(),
                        MatchMode.ANYWHERE));
            }
            if(!ObjectUtil.isEmpty(agent.getContactInfo())&&!StringUtil.isEmpty(agent.getContactInfo().getMobileNumber())){
                cr.add(Restrictions.like("ci.mobileNumber", agent.getContactInfo().getMobileNumber(), MatchMode.ANYWHERE));
            }
        }*/
        
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.property("id"));
        pl.add(Projections.property("username"));
        pl.add(Projections.property("pi.firstName"));
        pl.add(Projections.property("pi.lastName"));
        pl.add(Projections.property("ci.mobileNumber"));
        pl.add(Projections.property("enabled"));
        pl.add(Projections.property("branchId"));
        
        cr.setFirstResult(startIndex);
        cr.setMaxResults(limit);

        cr.setProjection(pl);

        List list = cr.list();
        return list;
    }

	public List<SmsGroupDetail> listGroupDetailByGroupId(List<String> ids)
	{
		Session session = getSessionFactory().openSession();
		Query query = session.createQuery("FROM SmsGroupDetail s WHERE s.smsGroup.id in (:ids)");
		query.setParameterList("ids", ObjectUtil.stringListToLongList(ids));
		List<SmsGroupDetail> result = (List<SmsGroupDetail>) query.list();
		session.flush();
		session.close();
		return result;
	
	}
	
	public ESESystem findPrefernceById(String id) {
		ESESystem sys = (ESESystem) find("FROM ESESystem es WHERE es.id = ?",Long.valueOf(id));
		return sys;
	}


	
}
