/*
 * ExportUtil.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

public class ExportUtil {

    private static final Logger LOGGER = Logger.getLogger(ExportUtil.class.getName());
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmss");

    public static final String REPORT_NAME = "reportName";
    public static final String TITLE = "title";
    public static final String SUB_TITLE = "subTitle";
    public static final String FILTER_DATA = "filterData";
    public static final String FILTER_LIST = "filterList";
    public static final String HEADING = "heading";
    public static final String DATAS = "datas";
    public static final String ENTITY_PROPERTY = "entityProperty";
    public static final String RECORD_PROPERTIES = "recordProperties";
    public static final String HEADER = "header";
    public static final String FOOTER = "footer";
    public static final String LOGO = "logo";
    public static final String FILTER_METHOD_NAME = "methodName";
    public static final String FILTER_PROPERTY_VALUE = "propertyValue";
    public static final String FILTER_DEFAULT_VALUE = "defaultValue";

    /**
     * Excel export.
     * @param exportDataMap the export data map
     * @return the input stream
     * @throws IOException 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static InputStream excelExport(Map<String, Object> exportDataMap) throws IOException {

        OutputStream fileOut = null;
        InputStream fileIn = null;

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet((String) exportDataMap.get(REPORT_NAME));

        CellStyle titleStyle = wb.createCellStyle();
        Font titleFont = wb.createFont();
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleFont.setFontHeightInPoints((short) 12);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setWrapText(true);
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        CellStyle filterHeadingStyle = wb.createCellStyle();
        Font filterHeadingFont = wb.createFont();
        filterHeadingFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        filterHeadingStyle.setFont(filterHeadingFont);
        filterHeadingStyle.setWrapText(true);
        filterHeadingStyle.setAlignment(CellStyle.ALIGN_LEFT);
        filterHeadingStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);

        CellStyle filterRecordStyle = wb.createCellStyle();
        filterRecordStyle.setWrapText(true);
        filterRecordStyle.setAlignment(CellStyle.ALIGN_LEFT);
        filterRecordStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);

        CellStyle columnHeaderStyle = wb.createCellStyle();
        Font columnHeaderFont = wb.createFont();
        columnHeaderFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        columnHeaderStyle.setFont(columnHeaderFont);
        columnHeaderStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        columnHeaderStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        columnHeaderStyle.setWrapText(true);
        columnHeaderStyle.setAlignment(CellStyle.ALIGN_CENTER);
        columnHeaderStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        columnHeaderStyle.setBorderBottom(CellStyle.BORDER_THIN);
        columnHeaderStyle.setBorderTop(CellStyle.BORDER_THIN);
        columnHeaderStyle.setBorderRight(CellStyle.BORDER_THIN);
        columnHeaderStyle.setBorderLeft(CellStyle.BORDER_THIN);

        CellStyle columnBodyStyle = wb.createCellStyle();
        columnBodyStyle.setWrapText(true);
        columnBodyStyle.setAlignment(CellStyle.ALIGN_LEFT);
        columnBodyStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
        columnBodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
        columnBodyStyle.setBorderTop(CellStyle.BORDER_THIN);
        columnBodyStyle.setBorderRight(CellStyle.BORDER_THIN);
        columnBodyStyle.setBorderLeft(CellStyle.BORDER_THIN);

        CellStyle filterFooterStyle = wb.createCellStyle();
        filterFooterStyle.setWrapText(true);
        filterFooterStyle.setAlignment(CellStyle.ALIGN_CENTER);
        filterFooterStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        // Merging Logo Columns
        sheet.addMergedRegion(new CellRangeAddress(0, 4, 0, 1));
        // Merging title columns
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 3, 7));

        sheet.setDefaultColumnWidth(13);
        byte[] logo = (byte[]) exportDataMap.get(LOGO);
        int pictureIdx = getPicIndex(wb, logo);
        Drawing drawing = sheet.createDrawingPatriarch();
        HSSFClientAnchor anchor = new HSSFClientAnchor(100, 150, 900, 100, (short) 0, 0, (short) 1,
                4);
        anchor.setAnchorType(1);
        HSSFPicture picture = (HSSFPicture) drawing.createPicture(anchor, pictureIdx);

        // Report Title
        Row row = sheet.createRow(1);
        Cell cell = row.createCell(3);
        cell.setCellValue((StringUtil.isEmpty((String) exportDataMap.get(TITLE)) ? ""
                : (String) exportDataMap.get(TITLE)).toUpperCase());
        cell.setCellStyle(titleStyle);

        // Report Sub Title
        row = sheet.createRow(2);
        cell = row.createCell(2);
        cell.setCellValue(StringUtil.isEmpty((String) exportDataMap.get(SUB_TITLE)) ? ""
                : (String) exportDataMap.get(SUB_TITLE));

        Map<String, Object> filterMap = (Map<String, Object>) exportDataMap.get(FILTER_DATA);
        List<String> filterList = (List<String>) exportDataMap.get(FILTER_LIST);

        Map<String, Map<Object, Object>> recordProperties = (Map<String, Map<Object, Object>>) exportDataMap
                .get(RECORD_PROPERTIES);

        // Report Record Rows
        List<Map<String, Object>> datas = (List<Map<String, Object>>) exportDataMap.get(DATAS);

        int l = 0;

        if (!ObjectUtil.isListEmpty(filterList)) {
            l = 6;
            for (String filterString : filterList) {
                if (filterMap.containsKey(filterString)) {
                    Map<String, Object> filterDetailMap = (Map<String, Object>) filterMap
                            .get(filterString);
                    String propertyValue = (String) filterDetailMap.get(FILTER_PROPERTY_VALUE);
                    String defaultValue = (String) filterDetailMap.get(FILTER_DEFAULT_VALUE);

                    row = sheet.createRow(l);

                    // Filter Data Caption
                    cell = row.createCell(0);
                    cell.setCellValue((String) propertyValue);
                    cell.setCellStyle(filterHeadingStyle);

                    // Filter Data Caption's Value
                    cell = row.createCell(1);
                    cell.setCellValue(defaultValue);
                    cell.setCellStyle(filterRecordStyle);

                    l++;
                }
            }
        } else {
            l = 5;
        }

        // if (!ObjectUtil.isEmpty(filterMap)) {
        //
        // // Filter Data
        // row = sheet.createRow(3);
        // cell = row.createCell(0);
        // cell.setCellValue("Filter Condition");
        // l = 4;
        // for (Map.Entry entity : filterMap.entrySet()) {
        // row = sheet.createRow(l);
        //
        // // Filter Data Caption
        // cell = row.createCell(0);
        // cell.setCellValue((String) entity.getKey());
        //
        // // Filter Data Caption's Value
        // cell = row.createCell(1);
        // cell.setCellValue((String) entity.getValue());
        //
        // l++;
        // }
        // } else {
        // l = 3;
        // }

        // Report Record Heading
        String heading = (String) exportDataMap.get(HEADING);
        String recHeading[] = heading.split("\\,");

        row = sheet.createRow((l + 1));
        int i = 0;
        for (String title : recHeading) {
            cell = row.createCell(i);
            cell.setCellValue(title);
            cell.setCellStyle(columnHeaderStyle);
            i++;
        }

        String[] entityPropertyArray = (String[]) ((String) exportDataMap.get(ENTITY_PROPERTY))
                .split("\\,");
        int k = l + 2;
        for (Map recods : datas) {
            row = sheet.createRow(k);
            int j = 0;
            for (String entityProperty : entityPropertyArray) {
                cell = row.createCell(j);
                cell.setCellStyle(columnBodyStyle);
                Object cellObject = (Object) recods.get(getPropertyName(entityProperty));
                if (!ObjectUtil.isEmpty(cellObject)) {
                    if (isFieldProperty(entityProperty)) {
                        entityProperty = getPropertyName(entityProperty);
                        if (recordProperties.containsKey(entityProperty) && recordProperties
                                .get(entityProperty).containsKey(String.valueOf(cellObject))) {
                            cell.setCellValue(String.valueOf(recordProperties.get(entityProperty)
                                    .get(String.valueOf(cellObject))));
                        } else {
                            cell.setCellValue(String.valueOf(cellObject));
                        }
                    } else {
                        if (cellObject instanceof Double) {
                            cell.setCellValue(Double.valueOf((Double) cellObject));
                        } else {
                            cell.setCellValue(String.valueOf(cellObject));
                        }
                    }
                }
                j++;
            }
            k++;
        }

        // Report Footer
        row = sheet.createRow(k + 1);
        cell = row.createCell(3);
        cell.setCellStyle(filterFooterStyle);

        // Merging footer columns
        sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 3, 7));

        cell.setCellValue(StringUtil.isEmpty((String) exportDataMap.get(FOOTER)) ? ""
                : (String) exportDataMap.get(FOOTER));

        try {
            String makeDir = FileUtil
                    .storeXls(String.valueOf(DateUtil.getRevisionNoDateTimeMilliSec()));
            String fileName = (String) exportDataMap.get(REPORT_NAME) + SDF.format(new Date())
                    + ".xls";

            fileOut = new FileOutputStream(makeDir + fileName);
            wb.write(fileOut);

            fileIn = new FileInputStream(new File(makeDir + fileName));
            fileOut.close();
            return fileIn;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    /**
     * Gets the property name.
     * @param property the property
     * @return the property name
     */
    public static String getPropertyName(String property) {

        return (isFieldProperty(property))
                ? property.substring(property.lastIndexOf("(") + 1, property.lastIndexOf(")"))
                : property;
    }

    /**
     * Checks if is field property.
     * @param property the property
     * @return true, if is field property
     */
    public static boolean isFieldProperty(String property) {

        return property.startsWith("property(");
    }

    public static InputStream exportXLS(List<List<String>> data, List<String> headers,
            Map<String, String> filterMaps, String reportName, String filterName,
            String reportTitle, byte[] pic) throws IOException {

        Workbook wb = new SXSSFWorkbook();

        OutputStream fileOut = null;
        InputStream fileIn = null;
        SXSSFSheet surveySheet = null;

        CellStyle titleStyle = wb.createCellStyle();
        Font titleFont = wb.createFont();
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleFont.setFontHeightInPoints((short) 22);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setWrapText(true);
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        CellStyle filterHeadingStyle = wb.createCellStyle();
        Font filterHeadingFont = wb.createFont();
        filterHeadingFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        filterHeadingStyle.setFont(filterHeadingFont);
        filterHeadingStyle.setWrapText(true);
        filterHeadingStyle.setAlignment(CellStyle.ALIGN_LEFT);
        filterHeadingStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);

        CellStyle filterRecordStyle = wb.createCellStyle();
        filterRecordStyle.setWrapText(true);
        filterRecordStyle.setAlignment(CellStyle.ALIGN_LEFT);
        filterRecordStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);

        CellStyle columnHeaderStyle = wb.createCellStyle();
        Font columnHeaderFont = wb.createFont();
        columnHeaderFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        columnHeaderStyle.setFont(columnHeaderFont);
        columnHeaderStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        columnHeaderStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        columnHeaderStyle.setWrapText(true);
        columnHeaderStyle.setAlignment(CellStyle.ALIGN_CENTER);
        columnHeaderStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
       /* columnHeaderStyle.setBorderBottom(CellStyle.BORDER_THIN);
        columnHeaderStyle.setBorderTop(CellStyle.BORDER_THIN);
        columnHeaderStyle.setBorderRight(CellStyle.BORDER_THIN);
        columnHeaderStyle.setBorderLeft(CellStyle.BORDER_THIN);
*/
        CellStyle columnBodyStyle = wb.createCellStyle();
        columnBodyStyle.setWrapText(true);
        columnBodyStyle.setAlignment(CellStyle.ALIGN_LEFT);
        columnBodyStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
       /* columnBodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
        columnBodyStyle.setBorderTop(CellStyle.BORDER_THIN);
        columnBodyStyle.setBorderRight(CellStyle.BORDER_THIN);
        columnBodyStyle.setBorderLeft(CellStyle.BORDER_THIN);*/
        
        CellStyle columnSerialNo = wb.createCellStyle();
        columnSerialNo.setWrapText(true);
        columnSerialNo.setAlignment(CellStyle.ALIGN_CENTER);
        columnSerialNo.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
      /*columnSerialNo.setBorderBottom(CellStyle.BORDER_THIN);
        columnSerialNo.setBorderTop(CellStyle.BORDER_THIN);
        columnSerialNo.setBorderRight(CellStyle.BORDER_THIN);
        columnSerialNo.setBorderLeft(CellStyle.BORDER_THIN);*/

        CellStyle filterFooterStyle = wb.createCellStyle();
        filterFooterStyle.setWrapText(true);
        filterFooterStyle.setAlignment(CellStyle.ALIGN_CENTER);
        filterFooterStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Row filterRow;
        Row dataRow;
        Row headerRow;
        Row filterRowTitle;

        Cell cell;
        Cell subFormCell;
        CellStyle style1 = wb.createCellStyle();
        CellStyle style2 = wb.createCellStyle();
        
        int rowNum = 3;

        wb.createSheet(reportName);
        surveySheet = (SXSSFSheet) wb.getSheet(reportName);
        
        surveySheet.setDefaultColumnWidth(13);
        
        Drawing drawing = surveySheet.createDrawingPatriarch();
        // Merging Logo Columns
        surveySheet.addMergedRegion(new CellRangeAddress(0, 4, 0, 0));
        // Merging title columns
        surveySheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 5));
        
        Row row = surveySheet.createRow(2);
        Cell titleCell = row.createCell(2);
        titleCell.setCellValue(reportTitle);
        titleCell.setCellStyle(titleStyle);
        rowNum++;
        rowNum++;
        filterRowTitle = surveySheet.createRow(rowNum++);
        cell = filterRowTitle.createCell(1);
        cell.setCellValue(filterName);
        cell.setCellStyle(filterHeadingStyle);

        for (Entry<String, String> entyt : filterMaps.entrySet()) {
            filterRow = surveySheet.createRow(rowNum++);
            cell = filterRow.createCell(1);
            cell.setCellValue(entyt.getKey());
            cell.setCellStyle(filterRecordStyle);
            cell = filterRow.createCell(2);
            cell.setCellValue(new XSSFRichTextString(entyt.getValue()));
            cell.setCellStyle(filterRecordStyle);

        }
        int headCOunt = 0;
        rowNum++;
        headerRow = surveySheet.createRow(rowNum++);
        for (String entyt : headers) {

            cell = headerRow.createCell(headCOunt);
            cell.setCellValue(entyt);
            cell.setCellStyle(columnHeaderStyle);
            columnHeaderStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
            surveySheet.setColumnWidth(headCOunt, (15 * 550));
            headCOunt++;
        }

        for (List<String> listData : data) {
            dataRow = surveySheet.createRow(rowNum++);
            int i = 0;
          /*  for (String coldata : listData) {
                subFormCell = dataRow.createCell(i);
                subFormCell.setCellValue(coldata);
                if(i==0){
                	subFormCell.setCellStyle(columnSerialNo);
                }else{
                	subFormCell.setCellStyle(columnBodyStyle);
                }
                surveySheet.setColumnWidth(i,(15 * 550));
                i++;
            }*/
  for (String coldata : listData) {
                
                /*subFormCell.setCellValue(coldata);
                subFormCell.setCellStyle(columnBodyStyle);*/
               // surveySheet.setColumnWidth(i, (short) 5000);
               // surveySheet.setColumnWidth(i, (15 * 450));
              //  i++;
            	subFormCell = dataRow.createCell(i);
                try 
		        {
        			Float.parseFloat(coldata.toString());
    				if(i==0){
    					style2.setAlignment(CellStyle.ALIGN_CENTER);
        				subFormCell.setCellStyle(style2);
    				}else{
    					style1.setAlignment(CellStyle.ALIGN_RIGHT);
        				subFormCell.setCellStyle(style1);
    				}
    				subFormCell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);
    				subFormCell.setCellValue(coldata);
            		//subDataFormCell.setCellStyle(columnBodyStyle);
            		surveySheet.setColumnWidth(i, (short) 5000);
            		//surveySheet.setColumnWidth(j, (15 * 450));
                     headCOunt++;
            		i++;
        		
		        }
		 catch  (NumberFormatException e) {
			 		subFormCell.setCellValue(coldata);
			 		subFormCell.setCellStyle(columnBodyStyle);
            		surveySheet.setColumnWidth(i, (short) 5000);
            		//surveySheet.setColumnWidth(j, (15 * 450));
                     headCOunt++;
            		i++;
			 }
                
                
                
                
            }

        }

        surveySheet.setDefaultColumnWidth(13);

        int pictureIdx = getPicIndex(wb, pic);

        XSSFClientAnchor anchor = new XSSFClientAnchor(100, 150, 900, 100, (short) 0, 0, (short) 1, 4);
        anchor.setAnchorType(1);
        XSSFPicture picture = (XSSFPicture) drawing.createPicture(anchor, pictureIdx);

        String makeDir = FileUtil
                .storeXls(String.valueOf(DateUtil.getRevisionNoDateTimeMilliSec()));
        String fileName = reportName + SDF.format(new Date()) + ".xls";

        try {
            fileOut = new FileOutputStream(makeDir + fileName);
            wb.write(fileOut);

            fileIn = new FileInputStream(new File(makeDir + fileName));
            fileOut.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return fileIn;

    }

    public static int getPicIndex(Workbook wb, byte[] pic) throws IOException {

        int pictureIdx = wb.addPicture(pic, wb.PICTURE_TYPE_JPEG);
        return pictureIdx;
    }
    
    
    public static InputStream exportXLSWithSubGrid(List<List<String>> data, List<String> headers,
            Map<String, String> filterMaps, String reportName, String filterName,
            String reportTitle, byte[] pic,List<String>subHeaders,Map<Long,List<List<String>>> subGridData) throws IOException {

        Workbook wb = new SXSSFWorkbook();

        OutputStream fileOut = null;
        InputStream fileIn = null;
        SXSSFSheet surveySheet = null;

        CellStyle titleStyle = wb.createCellStyle();
        Font titleFont = wb.createFont();
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleFont.setFontHeightInPoints((short) 22);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setWrapText(true);
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        CellStyle filterHeadingStyle = wb.createCellStyle();
        Font filterHeadingFont = wb.createFont();
        filterHeadingFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        filterHeadingStyle.setFont(filterHeadingFont);
        filterHeadingStyle.setWrapText(true);
        filterHeadingStyle.setAlignment(CellStyle.ALIGN_LEFT);
        filterHeadingStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);

        CellStyle filterRecordStyle = wb.createCellStyle();
        filterRecordStyle.setWrapText(true);
        filterRecordStyle.setAlignment(CellStyle.ALIGN_LEFT);
        filterRecordStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);

        CellStyle columnHeaderStyle = wb.createCellStyle();
        Font columnHeaderFont = wb.createFont();
        columnHeaderFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        columnHeaderStyle.setFont(columnHeaderFont);
        columnHeaderStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
        columnHeaderStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        columnHeaderStyle.setWrapText(true);
        columnHeaderStyle.setAlignment(CellStyle.ALIGN_CENTER);
        columnHeaderStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
       /* columnHeaderStyle.setBorderBottom(CellStyle.BORDER_THIN);
        columnHeaderStyle.setBorderTop(CellStyle.BORDER_THIN);
        columnHeaderStyle.setBorderRight(CellStyle.BORDER_THIN);
        columnHeaderStyle.setBorderLeft(CellStyle.BORDER_THIN);*/
        
        
        CellStyle subColumnHeaderStyle = wb.createCellStyle();
        Font subColumnHeaderFont = wb.createFont();
        subColumnHeaderFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        subColumnHeaderStyle.setFont(subColumnHeaderFont);
        subColumnHeaderStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        subColumnHeaderStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        subColumnHeaderStyle.setWrapText(true);
        subColumnHeaderStyle.setAlignment(CellStyle.ALIGN_CENTER);
        subColumnHeaderStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
      /*  subColumnHeaderStyle.setBorderBottom(CellStyle.BORDER_THIN);
        subColumnHeaderStyle.setBorderTop(CellStyle.BORDER_THIN);
        subColumnHeaderStyle.setBorderRight(CellStyle.BORDER_THIN);
        subColumnHeaderStyle.setBorderLeft(CellStyle.BORDER_THIN);*/

        CellStyle columnBodyStyle = wb.createCellStyle();
        columnBodyStyle.setWrapText(true);
        columnBodyStyle.setAlignment(CellStyle.ALIGN_LEFT);
        columnBodyStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
        /*columnBodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
        columnBodyStyle.setBorderTop(CellStyle.BORDER_THIN);
        columnBodyStyle.setBorderRight(CellStyle.BORDER_THIN);
        columnBodyStyle.setBorderLeft(CellStyle.BORDER_THIN);*/

        CellStyle filterFooterStyle = wb.createCellStyle();
        filterFooterStyle.setWrapText(true);
        filterFooterStyle.setAlignment(CellStyle.ALIGN_CENTER);
        filterFooterStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Row filterRow;
        Row dataRow;
        Row headerRow;
        Row filterRowTitle;
        Row subHeaderRow;
        Row subDataRow;

        Cell cell;
        Cell subFormCell;
        Cell subCell;
        Cell subDataFormCell;
        int rowNum = 3;

        wb.createSheet(reportName);
        surveySheet = (SXSSFSheet) wb.getSheet(reportName);
        
        CellStyle style1 = wb.createCellStyle();
        CellStyle style2 = wb.createCellStyle();
        
        Drawing drawing = surveySheet.createDrawingPatriarch();
        // Merging Logo Columns
        surveySheet.addMergedRegion(new CellRangeAddress(0, 4, 0, 0));
        // Merging title columns
        surveySheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 5));

        Row row = surveySheet.createRow(2);
        Cell titleCell = row.createCell(2);
        titleCell.setCellValue(reportTitle);
        titleCell.setCellStyle(titleStyle);

        rowNum++;
        filterRowTitle = surveySheet.createRow(rowNum++);
        cell = filterRowTitle.createCell(3);
        cell.setCellValue(filterName);
        cell.setCellStyle(filterHeadingStyle);

        for (Entry<String, String> entyt : filterMaps.entrySet()) {
            filterRow = surveySheet.createRow(rowNum++);
            cell = filterRow.createCell(3);
            cell.setCellValue(entyt.getKey());
            cell.setCellStyle(filterRecordStyle);
            cell = filterRow.createCell(4);
            cell.setCellValue(new XSSFRichTextString(entyt.getValue()));
            cell.setCellStyle(filterRecordStyle);

        }
        int headCOunt = 0;
        rowNum++;
        headerRow = surveySheet.createRow(rowNum++);
        for (String entyt : headers) {
            cell = headerRow.createCell(headCOunt);
            cell.setCellValue(entyt);
            cell.setCellStyle(columnHeaderStyle);
            columnHeaderStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
            surveySheet.setColumnWidth(headCOunt, (15 * 550));
            headCOunt++;
        }
        for (List<String> listData : data) {
            dataRow = surveySheet.createRow(rowNum++);
            int i = 0;
            Long deletedId=Long.parseLong(listData.get(0));
            String removeEle=listData.remove(0);
            for (String coldata : listData) {
                
                /*subFormCell.setCellValue(coldata);
                subFormCell.setCellStyle(columnBodyStyle);*/
               // surveySheet.setColumnWidth(i, (short) 5000);
               // surveySheet.setColumnWidth(i, (15 * 450));
              //  i++;
            	subFormCell = dataRow.createCell(i);
                try 
		        {
        			Float.parseFloat(coldata.toString());
    				if(i==0){
    					style2.setAlignment(CellStyle.ALIGN_CENTER);
        				subFormCell.setCellStyle(style2);
    				}else{
    					style1.setAlignment(CellStyle.ALIGN_RIGHT);
        				subFormCell.setCellStyle(style1);
    				}
    				subFormCell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);
    				subFormCell.setCellValue(coldata);
            		//subDataFormCell.setCellStyle(columnBodyStyle);
            		surveySheet.setColumnWidth(i, (short) 5000);
            		//surveySheet.setColumnWidth(j, (15 * 450));
                     headCOunt++;
            		i++;
        		
		        }
		 catch  (NumberFormatException e) {
			 		subFormCell.setCellValue(coldata);
			 		subFormCell.setCellStyle(columnBodyStyle);
            		surveySheet.setColumnWidth(i, (short) 5000);
            		//surveySheet.setColumnWidth(j, (15 * 450));
                     headCOunt++;
            		i++;
			 }
                
                
                
                
            }
            List<List<String>> subData=subGridData.get(deletedId);
            int subHeadCount=1;
            //rowNum++; 
            subHeaderRow = surveySheet.createRow(rowNum++);
            for (String entyt : subHeaders) {
                cell = subHeaderRow.createCell(subHeadCount);
                cell.setCellValue(entyt);
                cell.setCellStyle(subColumnHeaderStyle);
                subHeadCount++;
            }
            if(!StringUtil.isListEmpty(subData)){
            	 for(List<String> subListData : subData){
                 	subDataRow = surveySheet.createRow(rowNum++);
                 	int j=1;
                 if(!StringUtil.isListEmpty(subListData)) {
                 	for(String subColData:subListData){
                 		subDataFormCell=subDataRow.createCell(j);
                 		try 
     			        {
                 			Float.parseFloat(subColData.toString());
             				style1.setAlignment(CellStyle.ALIGN_RIGHT);
             				subDataFormCell.setCellStyle(style1);
             				subDataFormCell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);
             				subDataFormCell.setCellValue(subColData);
                     		//subDataFormCell.setCellStyle(columnBodyStyle);
                     		surveySheet.setColumnWidth(j, (short) 5000);
                     		//surveySheet.setColumnWidth(j, (15 * 450));
                              headCOunt++;
                     		j++;
                 		
     			        }
     			 catch  (NumberFormatException e) {
             				subDataFormCell.setCellValue(subColData);
                     		subDataFormCell.setCellStyle(columnBodyStyle);
                     		surveySheet.setColumnWidth(j, (short) 5000);
                     		//surveySheet.setColumnWidth(j, (15 * 450));
                              headCOunt++;
                     		j++;
     				 }
                 		
                 		
                 	}
                 }
                 	
                 }
            }
            
           
            rowNum++;

        }

        //surveySheet.setDefaultColumnWidth(13);

        int pictureIdx = getPicIndex(wb, pic);

        XSSFClientAnchor anchor = new XSSFClientAnchor(100, 150, 900, 100, (short) 0, 0, (short) 1,
                4);
        anchor.setAnchorType(1);
        XSSFPicture picture = (XSSFPicture) drawing.createPicture(anchor, pictureIdx);

        String makeDir = FileUtil
                .storeXls(String.valueOf(DateUtil.getRevisionNoDateTimeMilliSec()));
        String fileName = reportName + SDF.format(new Date()) + ".xls";

        try {
            fileOut = new FileOutputStream(makeDir + fileName);
            wb.write(fileOut);

            fileIn = new FileInputStream(new File(makeDir + fileName));
            fileOut.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return fileIn;

    }
}
