/*
 * TrimInterceptor.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

@SuppressWarnings("serial")
public class TrimInterceptor implements Interceptor {

    /*
     * (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
     */
    @Override
    public void destroy() {

    }

    /*
     * (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
     */
    @Override
    public void init() {

    }

    /*
     * (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.
     * ActionInvocation)
     */
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
    	String name=invocation.getInvocationContext().ACTION_NAME;
        Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
        for (String param : parameters.keySet()) {

            if (parameters.get(param) instanceof String[]) {
                String[] vals = (String[]) parameters.get(param);
                for (int i = 0; i < vals.length; i++) {
                    vals[i] = vals[i].trim();
                }
            }

        }
        return invocation.invoke();
    }
}