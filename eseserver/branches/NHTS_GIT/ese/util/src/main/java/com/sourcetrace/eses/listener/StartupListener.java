/*
 * StartupListener.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.listener;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sourcetrace.eses.dao.IUtilDAO;
import com.sourcetrace.eses.entity.Deployment;
import com.sourcetrace.eses.entity.DeploymentLog;
import com.sourcetrace.eses.entity.UptimeLog;



public class StartupListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(StartupListener.class); 
    
    /*
     * (non-Javadoc)
     * @see
     * javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent evt) {
    	try {
	        LOGGER.info("Context Initializing...");
	        ServletContext ctx = evt.getServletContext();
	        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(ctx);
	        Deployment deployment = (Deployment) wac.getBean("deployment");
	        IUtilDAO deploymentLogDAO = (IUtilDAO) wac.getBean("utilDAO");
	        DeploymentLog deploymentLog = deploymentLogDAO.findLatestDeploymentLog(deployment
	                .getModule());
	        if (deploymentLog == null || !deployment.getVersion().equals(deploymentLog.getVersion())) {
	            deploymentLog = new DeploymentLog(deployment.getModule(), deployment.getVersion());
	            //deploymentLogDAO.save(deploymentLog);
	            deploymentLogDAO.saveDeploymentLog(deploymentLog);
	           
	        }
	        UptimeLog uptimeLog = new UptimeLog(deployment.getModule());
	        //deploymentLogDAO.save(uptimeLog);
	        deploymentLogDAO.saveUptimeLog(uptimeLog);
	        
	        ctx.setAttribute("version", deployment.getVersion());
	        ctx.setAttribute("module", deployment.getModule());
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent evt) {

        LOGGER.info("Context Destroying...");
        ServletContext ctx = evt.getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(ctx);
        Deployment deployment = (Deployment) wac.getBean("deployment");
        IUtilDAO uptimeLogDAO = (IUtilDAO) wac.getBean("utilDAO");
       
        UptimeLog uptimeLog = uptimeLogDAO.findLatestUptimeLog(deployment.getModule());
        uptimeLog.setShutdown(new Date());
        //uptimeLogDAO.update(uptimeLog);
        uptimeLogDAO.updateUptimeLog(uptimeLog);
    }

}
