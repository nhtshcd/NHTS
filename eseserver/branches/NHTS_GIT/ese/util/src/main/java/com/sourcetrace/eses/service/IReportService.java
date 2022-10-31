/*
 * IReportService.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.service;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.sourcetrace.eses.entity.DynamicReportConfigDetail;

public interface IReportService {

    /**
     * List.
     * @param dir the dir
     * @param sort the sort
     * @param start the start
     * @param limit the limit
     * @param page the page
     * @return the map
     */
    public Map list(String dir, String sort, int start, int limit, int page);

    /**
     * List with entity filtering.
     * @param dir the dir
     * @param sort the sort
     * @param start the start
     * @param limit the limit
     * @param exampleEntity the example entity
     * @param page the page
     * @return the map
     */
    public Map listWithEntityFiltering(String dir, String sort, int start, int limit,
            Object exampleEntity, int page);
    
    public Map listWithEntityFiltering(String dir, String sort, int start, int limit,
            Object exampleEntity, int page, boolean isReport);

    /**
     * List with entity projection filtering.
     * @param dir the dir
     * @param sort the sort
     * @param start the start
     * @param limit the limit
     * @param exampleEntity the example entity
     * @param page the page
     * @param projectionProperties the projection properties
     * @return the map
     */
    public Map listWithEntityProjectionFiltering(String dir, String sort, int start, int limit,
            Object exampleEntity, int page, Map projectionProperties);

    /**
     * List with date range filtering.
     * @param dir the dir
     * @param sort the sort
     * @param start the start
     * @param limit the limit
     * @param startDate the start date
     * @param endDate the end date
     * @param page the page
     * @return the map
     */
    public Map listWithDateRangeFiltering(String dir, String sort, int start, int limit,
            Date startDate, Date endDate, int page);

    /**
     * List with multiple filtering.
     * @param dir the dir
     * @param sort the sort
     * @param start the start
     * @param limit the limit
     * @param startDate the start date
     * @param endDate the end date
     * @param object the object
     * @param page the page
     * @return the map
     */
    public Map listWithMultipleFiltering(String dir, String sort, int start, int limit,
            Date startDate, Date endDate, Object object, int page);
    
    /**
     * List with multiple filtering.
     * @param sord the sord
     * @param sidx the sidx
     * @param startIndex the start index
     * @param limit the limit
     * @param sDate the s date
     * @param eDate the e date
     * @param filter the filter
     * @param page the page
     * @param projectionProperties the projection properties
     * @return the map
     */
    public Map listWithMultipleFiltering(String sord, String sidx, int startIndex, int limit,
            Date sDate, Date eDate, Object filter, int page, Map projectionProperties);

	public Map listWithProjectionFiltering(String sord, String sidx, int startIndex, int limit, Date getsDate,
			Date geteDate, Object filter, int page, Set<DynamicReportConfigDetail> dynamicReportConfigDetail);
	
	public Map listWithProjectionFilteringView(String sord, String sidx, int startIndex, int limit, Date getsDate,
			Date geteDate, Object filter, int page,Map<String, Map> map);
	
	public Map listWithTraceabilityFilteringData(String sord, String sidx, int startIndex, int limit, Date getsDate,
			Date geteDate, Object filter, int page,Map<String, Map> map, String branch);
	
	public Map listWithProjectionFilteringStatic(String sord, String sidx, int startIndex, int limit, Date getsDate,
			Date geteDate, Object filter, int page, Map<String, Map> map);
	public Map listWithMultipleFiltering(String sord, String sidx, int startIndex, int limit, Date sDate, Date eDate,
			Object filter, Map<String, String> filtermap, int page, Map projectionProperties);



	Map listWithMultipleFilteringExport(String sord, String sidx, int startIndex, int limit, Date startDate,
			Date endDate, Object exampleEntity, int page, Map projectionProperties);

}
