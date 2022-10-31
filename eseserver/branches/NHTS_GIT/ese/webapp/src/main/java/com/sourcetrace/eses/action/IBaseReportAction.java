/*
 * IBaseReportAction.java
 * Copyright (c) 2008-2009, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.action;

/**
 * The Interface IBaseReportAction.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface IBaseReportAction {

    //
//    public static final String LIST = "list";
//    public static final String HEADING = "heading";
//    public static final String REDIRECT = "redirect";
//    public static final String DETAIL = "detail";
    //        Grid related static keys

   // public static final String RECORDS = "records";
   // public static final String TOTAL = "total";
    // get the requested page
   // public static final String PAGE = "page";
    // get how many rows we want to have into the grid
   // public static final String ROWS = "rows";
    // get index row - i.e. user click to sort
    public static final String SORT_INDEX = "sidx";
    // get the direction
    public static final String SORT_DIR= "sord";


    
    //Global data and time format
    public static String DATE_FORMAT="";
    public static String DATE_TIME_FORMAT="";

    //available export options.
    public final static String EXPORT_CSV = "csv";
    public final static String EXPORT_PDF = "pdf";
    public static final String CSV_SEPERATOR = ",";
    public static String CSV_FILE_DATE_FORMAT = "yyyyMMddHHmmss";

  
}
