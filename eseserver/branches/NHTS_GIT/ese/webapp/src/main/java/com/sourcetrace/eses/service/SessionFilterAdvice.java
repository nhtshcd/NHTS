package com.sourcetrace.eses.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

@Service
@Aspect
public class SessionFilterAdvice {

	@AfterReturning(pointcut = "execution(* org.hibernate.SessionFactory.openSession(..)) || execution(* org.hibernate.SessionFactory.getCurrentSession(..))", returning = "session")
	public void setupFilter(Session session) {
		HttpSession httpSession = ReflectUtil.getCurrentHttpSession();
		if (!ObjectUtil.isEmpty(httpSession)) {
		    
		    Object isMultiBranch = httpSession.getAttribute(ISecurityFilter.IS_MULTI_BRANCH_APP);
		    String multiBranch = ObjectUtil.isEmpty(isMultiBranch) ? "" : isMultiBranch.toString();
		    if(multiBranch.equals("1")){
		        Object object = httpSession.getAttribute(ISecurityFilter.MAPPED_BRANCHES);
		        String currentBranch = ObjectUtil.isEmpty(object) ? "" : object.toString();
		        if (!StringUtil.isEmpty(currentBranch)) {
		            org.hibernate.Filter branchFilter = session.getEnabledFilter(ISecurityFilter.BRANCH_FILTER);
		            if (ObjectUtil.isEmpty(branchFilter)) {
		                List<String> branches = new ArrayList<String>();
		                Arrays.asList(currentBranch.split(",")).stream().filter(branch->!StringUtil.isEmpty(branch)).forEach(branch->{
		                    branches.add(branch);
		                });
		                if (branches.size() != 0) {
                            session.enableFilter(ISecurityFilter.BRANCH_FILTER)
                                    .setParameterList(ISecurityFilter.BRANCH_FILTER_PARM, branches);
                        }
		            }
		        }
		    }else{
		        Object object = httpSession.getAttribute(ISecurityFilter.CURRENT_BRANCH);
	            String currentBranch = ObjectUtil.isEmpty(object) ? "" : object.toString();
	            if (!StringUtil.isEmpty(currentBranch)) {
	                org.hibernate.Filter branchFilter = session.getEnabledFilter(ISecurityFilter.BRANCH_FILTER);
	                if (ObjectUtil.isEmpty(branchFilter)) {
	                    List<String> branches = new ArrayList<String>();
	                    branches.add(currentBranch);
	                    if (branches.size() != 0) {
	                        session.enableFilter(ISecurityFilter.BRANCH_FILTER)
	                                .setParameterList(ISecurityFilter.BRANCH_FILTER_PARM, branches);
	                    }
	                }
	            }
		    }
		    
			
		}
	}
}