/*
 * IUserDetailsAdapter.java
 * Copyright (c) 2015-2016, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.umgmt.security;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sourcetrace.eses.entity.Menu;
import com.sourcetrace.eses.entity.User;

public interface IUserDetailsAdapter extends UserDetailsService {

    /**
     * Checks if is account not locked.
     * @param user the user
     * @return true, if is account not locked
     */
    public boolean isAccountNotLocked(User user);

    /**
     * Sets the invalid login counter.
     * @param username the new invalid login counter
     */
    public void setInvalidLoginCounter(String username);

    /**
     * Reset invalid login counter.
     * @param username the username
     */
    public void resetInvalidLoginCounter(String username);

    /**
     * Gets the user language.
     * @param username the username
     * @return the user language
     */
    public String getUserLanguage(String username);

    /**
     * Gets the user.
     * @param username the username
     * @return the user
     */
    public User getUser(String username);

    /**
     * Gets the user menus.
     * @param username the username
     * @return the user menus
     */
    public List<Menu> getUserMenus(String username);

    /**
     * Gets the agent info.
     * @param username the username
     * @return the agent info
     */
    public Object[] getAgentInfo(String profileId);

}
