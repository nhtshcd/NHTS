/*
 * LicenseAction.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */

package com.sourcetrace.eses.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.filter.IUsageValidator;
import com.sourcetrace.eses.txn.exception.WebConsoleError;
import com.sourcetrace.eses.txn.exception.WebConsoleException;
import com.sourcetrace.eses.util.StringUtil;


// TODO: Auto-generated Javadoc
@Component
@Scope("prototype")
public class LicenseAction extends SwitchAction {

    private static final Logger LOGGER = Logger.getLogger(LicenseAction.class);
    private static final long serialVersionUID = 1L;

    @Autowired
    private IUsageValidator usageValidator;
    private String key;

    /**
     * Save.
     * @return the string
     */
    public String save() {

        try {
            if (StringUtil.isEmpty(key)) {
                addActionError(getText("empty.license"));
            } else {
                usageValidator.validate(key);
                String userName = (String) request.getSession().getAttribute("user");
                if (!StringUtil.isEmpty(userName)) {
                    request.getSession().invalidate();
                }
                LOGGER.debug("License is valid, saved");
                return REDIRECT;
            }
        } catch (WebConsoleException e) {
            if (e.getCode().equals(WebConsoleError.INVALID_LICENCE)
                    || e.getCode().equals(WebConsoleError.EXPIRED_LICENCE)
                    || e.getCode().equals(WebConsoleError.UNAUTHORIZED_CLIENT)) {
                addActionError(e.getMessage());
            }
        }

        return LIST;
    }

    /**
     * List.
     * @return the string
     */
    public String list() {

        return LIST;
    }

    /**
     * @return
     */
    public IUsageValidator getUsageValidator() {

        return usageValidator;
    }

    /**
     * @param usageValidator
     */
    public void setUsageValidator(IUsageValidator usageValidator) {

        this.usageValidator = usageValidator;
    }

    /**
     * @param key
     */
    public void setKey(String key) {

        this.key = key;
    }

    /**
     * @return
     */
    public String getKey() {

        return key;
    }
}