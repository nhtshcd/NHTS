/*
 * ILicenseDAO.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.dao;

import java.util.List;

import com.sourcetrace.eses.entity.License;

public interface ILicenseDAO extends IESEDAO {

    /**
     * Find.
     * @return the license
     */
    public License find();

    /**
     * List.
     * @return the list< license>
     */
    public List<License> list();
}
