/*
 * LicenseDAO.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sourcetrace.eses.entity.License;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
@Repository
@Transactional
public class LicenseDAO extends ESEDAO implements ILicenseDAO {
    
    @Autowired
    public LicenseDAO(SessionFactory sessionFactory) {
        this.setSessionFactory(sessionFactory);
    }


    /*
     * (non-Javadoc)
     * @see com.ese.dao.util.ILicenseDAO#find()
     */
    @SuppressWarnings("unchecked")
    public License find() {

        List<License> license = (List<License>) list(
                "FROM License li WHERE ? BETWEEN li.start AND li.end", DateUtil
                        .getDateWithoutTime(new Date()));
        if (!ObjectUtil.isListEmpty(license)) {
            return license.get(0);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.util.ILicenseDAO#list()
     */
    @SuppressWarnings("unchecked")
    public List<License> list() {

        return list("FROM License");
    }
}
