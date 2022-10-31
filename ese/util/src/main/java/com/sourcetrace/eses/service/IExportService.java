/*
 * IExportService.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.service;

import java.io.InputStream;
import java.util.Map;

public interface IExportService {

    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String REPORT_NAME = "reportName";
    public static final String SORTING_COLUMS = "sortColumn";
    public static final String SORTING_ORDER = "sortOrder";
    public static final String ENTITY_FILTER = "entityFilter";
    public static final String REPORT_SERVICE = "reportService";
    public static final String FILTER_MAP = "filterMap";

    /**
     * Excel export.
     * @param dataMap the data map
     * @return the input stream
     */
    public InputStream excelExport(Map<String, Object> dataMap);

}
