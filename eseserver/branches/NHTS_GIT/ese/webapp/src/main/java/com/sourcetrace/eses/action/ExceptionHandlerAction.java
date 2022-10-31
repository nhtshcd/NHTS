/*
 * ExceptionHandlerAction.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.action;

import org.apache.log4j.Logger;

import com.sourcetrace.eses.util.ObjectUtil;


@SuppressWarnings("serial")
public class ExceptionHandlerAction extends SwitchAction {
    private static final Logger LOGGER = Logger.getLogger(ExceptionHandlerAction.class);
    private Exception exception;

    /*
     * (non-Javadoc)
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Override
    public String execute() throws Exception {

        if (!ObjectUtil.isEmpty(exception)) {
            addActionError(exception.toString());
            exception.printStackTrace();
            LOGGER.error(exception.getMessage());
        } else {
            addActionError(getText("exception.could.not.log"));
            LOGGER.error(getText("exception.could.not.log"));
        }
        return ERROR;
    }

    /**
     * Sets the exception.
     * @param exception the new exception
     */
    public void setException(Exception exception) {

        this.exception = exception;
    }
}
