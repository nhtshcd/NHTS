package com.sourcetrace.eses.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

public class CustomSessionControlAuthenticationStrategy extends ConcurrentSessionControlAuthenticationStrategy {

	@Autowired
	private SessionRegistry sessionRegistry;
	
	
    public CustomSessionControlAuthenticationStrategy(SessionRegistry sessionRegistry) {
		super(sessionRegistry);
		// TODO Auto-generated constructor stub
	}

	protected int getMaximumSessionsForThisUser(org.springframework.security.core.Authentication authentication) {
    	int maximumSession = 1;
       if( authentication.getAuthorities().stream().anyMatch(uu -> uu.getAuthority().equals("SuperAdmin"))){
    	   maximumSession  = -1;
       }
        return maximumSession;
    }
	
	 protected void allowableSessionsExceeded(List<SessionInformation> sessions, int allowableSessions,
	            SessionRegistry registry) throws SessionAuthenticationException {
	        if ((sessions == null)) {
	            throw new SessionAuthenticationException(messages.getMessage("ConcurrentSessionControlAuthenticationStrategy.exceededAllowed",
	                    new Object[] {Integer.valueOf(allowableSessions)},
	                    "Maximum sessions of {0} for this principal exceeded"));
	        }

	        // Determine least recently used session, and mark it for invalidation
	        SessionInformation leastRecentlyUsed = null;

	        for (SessionInformation session : sessions) {
	            if ((leastRecentlyUsed == null)
	                    || session.getLastRequest().before(leastRecentlyUsed.getLastRequest())) {
	                leastRecentlyUsed = session;
	            }
	        }

	        leastRecentlyUsed.expireNow();
	    }
	
	
}