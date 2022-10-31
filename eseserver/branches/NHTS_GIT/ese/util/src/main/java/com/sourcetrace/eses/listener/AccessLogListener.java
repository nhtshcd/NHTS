package com.sourcetrace.eses.listener;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.dao.IUtilDAO;
import com.sourcetrace.eses.entity.AccessLog;
import com.sourcetrace.eses.entity.Deployment;

@SuppressWarnings("rawtypes")
@Component
public class AccessLogListener implements ApplicationListener {

    private static final Logger LOGGER = Logger.getLogger(AccessLogListener.class.getName());
   
    @Autowired
    private Deployment deployment;
    @Autowired
    private IUtilDAO utilDAO;
    
    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof AuthenticationSuccessEvent) {
            String username = ((AuthenticationSuccessEvent) event).getAuthentication().getName();
            AccessLog accessLog = new AccessLog(deployment.getModule(), username,((WebAuthenticationDetails)(WebAuthenticationDetails)((AuthenticationSuccessEvent) event).getAuthentication().getDetails()).getRemoteAddress());
            accessLog.setLogin(String.valueOf(new Date()));
            utilDAO.save(accessLog);
            LOGGER.info("User logged in " + username);
        } else if (event instanceof HttpSessionDestroyedEvent) {
            Object user = ((HttpSessionDestroyedEvent) event).getSession().getAttribute("user");
            if(user != null) {
                AccessLog accessLog = utilDAO.findLatestAccessLog(deployment.getModule(), user.toString());
                if(accessLog != null) {
                    accessLog.setLogout(String.valueOf(new Date()));
                    //accessLogDAO.update(accessLog);
                    utilDAO.updateAccessLog(accessLog);
                    LOGGER.info("User logged out " + user.toString());
                }
            }       
        }

    }

}
