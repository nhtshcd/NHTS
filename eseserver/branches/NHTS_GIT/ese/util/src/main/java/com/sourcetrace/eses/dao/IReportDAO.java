/*
 * IReportDAO.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.dao;

import java.util.Map;

public interface IReportDAO {

    public static final String ENTITY = "entity";
    public static final String DIR = "dir";
    public static final String SORT_COLUMN = "sort";
    public static final String START_INDEX = "start";
    public static final String LIMIT = "limit";
    public static final String DESCENDING = "desc";
    public static final String RECORD_COUNT = "records";
    public static final String RECORDS = "rows";
    public static final String DATE_COLUMN = "date";
    public static final String FROM_DATE = "from";
    public static final String TO_DATE = "to";
    public static final String FILTER = "example";
    public static final String PAGE_NUMBER = "pagenumber";
    public static final String PAGE = "page";
    public static final String EXCLUDE_PROP = "exclude";
    public static final String DELIMITER = ".";
    public static final String PROJ_GROUP = "groupProperty";
    public static final String PROJ_AVG = "avgProperty";
    public static final String PROJ_SUM = "sumProperty";
    public static final String PROJ_MAX = "maxProperty";
    public static final String PROJ_MIN = "minProperty";
    public static final String PROJ_OTHERS = "otherProperties";
    public static final String SEPARATOR = ",";
    public static final String IS_REPORT = "isReport";
    public static final String MAIN_GRID = "mainGrid";
    public static final String SUB_GRID = "subGrid";
    
    public static final String CONFIG_DETAIL = "configDetail";
    

    // specific Agent Txn Report only
    public static final String MULTIPLE_MATCH = "|";
    public static final String MULTIPLE_AGENTS = "|";
    public static final String FILTER_MAP = "filterMap";
	public static final String JOIN_MAP = "joinsMap";    

    /**
     * List.
     * @param params the params
     * @return the map
     */
    public Map list(Map params);

	public Map customList(Map params);

	public Map listExport(Map params);
	public Map listView(Map params,Map filterProperty);
	public Map customListStatic(Map params,Map filterProperty);
	public Map listTraceabilityData(Map params, Map filterProperty, String branch);
}
