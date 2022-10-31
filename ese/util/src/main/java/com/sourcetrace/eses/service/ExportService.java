/*
 * ExportService.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.NullValueInNestedPathException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.sourcetrace.eses.util.ExportUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

@Component
public class ExportService implements IExportService, ApplicationContextAware {

    private static final Logger LOGGER = Logger.getLogger(ExportService.class.getName());
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MMM-yyyy");
    private static Properties properties;
    private static final String RECORD_PROPERTIES = "record.properties";

    private String reportName = null;
    private String sortColumns = null;
    private String sortOrder = null;
    private Date startDate = null;
    private Date endDate = null;
    private Object filterObject = null;
    private String serviceName = null;
    private Map<String, Object> filterData = null;
    private List<String> filterList = null;
    private Map<String, Map<Object, Object>> recordProperties = null;
    private BeanWrapper firstBeanRecord = null;

    private IReportService reportService;
    private ApplicationContext applicationContext;
   static {
        properties = new Properties();
        try {
            properties.load(ExportService.class.getResourceAsStream("exportProperties.properties"));
        } catch (IOException e) {
            LOGGER.error("Error reading property, exportProperties.properties");
        }
    }

    /*
     * (non-Javadoc)
     * @see com.sourcetrace.eses.service.IExportService#excelExport(java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public InputStream excelExport(Map<String, Object> dataMap) {
        try{
        this.reportName = (String) dataMap.get(IExportService.REPORT_NAME);
        this.sortColumns = (String) dataMap.get(IExportService.SORTING_COLUMS);
        this.sortOrder = (String) dataMap.get(IExportService.SORTING_ORDER);
        // this.serviceName = (String) dataMap.get(IExportService.REPORT_SERVICE);
        this.filterObject = (Object) dataMap.get(IExportService.ENTITY_FILTER);

        this.startDate = null;
        this.endDate = null;

        loadRecordProperties(this.reportName);

        Map<String, Object> filterMap = (Map<String, Object>) dataMap
                .get(IExportService.FILTER_MAP);
        if (filterMap.containsKey(START_DATE) && !ObjectUtil.isEmpty(filterMap.get(START_DATE))
                && filterMap.get(START_DATE) instanceof Date)
            this.startDate = (Date) filterMap.get(START_DATE);
        if (filterMap.containsKey(END_DATE) && !ObjectUtil.isEmpty(filterMap.get(END_DATE))
                && filterMap.get(END_DATE) instanceof Date)
            this.endDate = (Date) filterMap.get(END_DATE);

        String entityProperties = properties.getProperty("entity" + this.reportName);
        this.serviceName = properties.getProperty("export.reportService." + this.reportName);

        List<Map<String, Object>> dataList = getExportDatas(entityProperties);

        loadFilterRecords(filterMap);

        // if(!ObjectUtil.isListEmpty(dataList)) {

        String reportName = properties.getProperty("reportName" + this.reportName);
        String title = properties.getProperty("title" + this.reportName);
        String subTitle = properties.getProperty("subTitle" + this.reportName);
        String heading = properties.getProperty("heading" + this.reportName);

        Map<String, Object> exportDataMap = new HashMap<String, Object>();
        exportDataMap.put(ExportUtil.REPORT_NAME, reportName);
        exportDataMap.put(ExportUtil.TITLE, title);
        exportDataMap.put(ExportUtil.SUB_TITLE, subTitle);
        exportDataMap.put(ExportUtil.HEADING, heading);
        exportDataMap.put(ExportUtil.ENTITY_PROPERTY, entityProperties);
        exportDataMap.put(ExportUtil.DATAS, dataList);
        exportDataMap.put(ExportUtil.FILTER_DATA, this.filterData);
        exportDataMap.put(ExportUtil.FILTER_LIST, this.filterList);
        exportDataMap.put(ExportUtil.RECORD_PROPERTIES, this.recordProperties);
//        byte[] logo = preferencesService.findLogo();
//        if (!ObjectUtil.isEmpty(logo)) {
//            exportDataMap.put(ExportUtil.LOGO, logo);
//        }
        exportDataMap.put(ExportUtil.FOOTER, properties.getProperty(ExportUtil.FOOTER));

        // return ExportUtil.excelExport(exportDataMap);
        // }

        return ExportUtil.excelExport(exportDataMap);
        }
        catch (Exception e) {
           return null;
        }
    }

    /**
     * Load record properties.
     * @param reportName the report name
     */
    public void loadRecordProperties(String reportName) {

        recordProperties = new LinkedHashMap<String, Map<Object, Object>>();
        if (!StringUtil.isEmpty(reportName)) {
            String recordPropertyKeys = properties
                    .getProperty(reportName + "." + RECORD_PROPERTIES);
            if (!StringUtil.isEmpty(recordPropertyKeys)) {
                String[] recordPropertyKeyArray = recordPropertyKeys.split("\\,");
                if (recordPropertyKeyArray != null && recordPropertyKeyArray.length > 0) {
                    for (String key : recordPropertyKeyArray) {

                        Map<Object, Object> recordProps = new LinkedHashMap<Object, Object>();
                        recordProperties.put(key, recordProps);

                        String key1 = reportName + "." + key;
                        String propertyKeys = (String) properties.get(key1);
                        if (!StringUtil.isEmpty(propertyKeys)) {
                            String[] propKeyArray = propertyKeys.split("\\,");
                            if (propKeyArray != null && propKeyArray.length > 0) {
                                for (String propKeys : propKeyArray) {
                                    recordProps.put(propKeys,
                                            (String) properties.get(key1 + "." + propKeys));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets the export datas.
     * @return the export datas
     */
    private List<Map<String, Object>> getExportDatas(String entityProperties) {

        List<Map<String, Object>> exportDataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> recordMap = null;

        String[] entityPropertyArray = entityProperties.split("\\,");
        List dataList = getRecords();
        try {
            this.firstBeanRecord = null;
            for (Object object : dataList) {
                recordMap = new LinkedHashMap<String, Object>();
                BeanWrapper beanWarp = new BeanWrapperImpl(object);
                if (ObjectUtil.isEmpty(firstBeanRecord)) {
                    firstBeanRecord = beanWarp;
                }
                for (String entityProperty : entityPropertyArray) {
                    entityProperty = ExportUtil.getPropertyName(entityProperty);
                    Object propertyObject = null;
                    try {
                        propertyObject = beanWarp.getPropertyValue(entityProperty);
                    } catch (NullValueInNestedPathException e) {
                        LOGGER.info("Null Value In NestedPath Exception");
                        propertyObject = null;
                    }
                    if (!ObjectUtil.isEmpty(propertyObject)) {
                        if (propertyObject instanceof Date) {
                            recordMap.put(entityProperty, SDF.format(propertyObject));
                        } else {
                            recordMap.put(entityProperty, propertyObject);
                        }
                    } else {
                        recordMap.put(entityProperty, "");
                    }
                }
                exportDataList.add(recordMap);
            }
        } catch (Exception e) {
            LOGGER.info("Record Process Exception");
            e.printStackTrace();
        }
        return exportDataList;
    }

    /**
     * Gets the records.
     * @return the records
     */
    private List getRecords() {

        try {
            reportService = (IReportService) applicationContext.getBean(this.serviceName);
            Map data = reportService.listWithMultipleFiltering(sortOrder, sortColumns, 0, 0,
                    startDate, endDate, filterObject, 0);
            return (List) data.get("rows");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the filter datas.
     * @return the filter datas
     */
    @SuppressWarnings("rawtypes")
    private void loadFilterRecords(Map<String, Object> filterObjects) {

        this.filterList = (List<String>) filterObjects.get(ExportUtil.FILTER_LIST);
        this.filterData = new LinkedHashMap<String, Object>();

        if (!ObjectUtil.isListEmpty(this.filterList)) {
            for (String filterString : this.filterList) {
                String methodName = String.valueOf(properties.get(this.reportName + ".filter."
                        + filterString + ".method"));
                String defaultValue = "";
                if (!StringUtil.isEmpty(methodName)) {
                    Object cellObject = null;
                    try {
                        cellObject = firstBeanRecord.getPropertyValue(ExportUtil
                                .getPropertyName(methodName));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!ObjectUtil.isEmpty(cellObject)) {
                        if (ExportUtil.isFieldProperty(methodName)) {
                            if (recordProperties
                                    .containsKey(ExportUtil.getPropertyName(methodName))
                                    && recordProperties.get(ExportUtil.getPropertyName(methodName))
                                            .containsKey(String.valueOf(cellObject))) {
                                defaultValue = String.valueOf(recordProperties.get(
                                        ExportUtil.getPropertyName(methodName)).get(
                                        String.valueOf(cellObject)));
                            } else {
                                defaultValue = String.valueOf(cellObject);
                            }
                        } else {
                            defaultValue = String.valueOf(cellObject);
                        }
                    }
                }
                this.filterData.put(
                        filterString,
                        getFilterDetailMap(
                                String.valueOf(properties.get(this.reportName + ".filter."
                                        + filterString)), defaultValue));
            }
        }
        if (!ObjectUtil.isEmpty(this.startDate)) {
            this.filterList.add(0, START_DATE);
            this.filterData.put(
                    START_DATE,
                    getFilterDetailMap(String.valueOf(properties.get(START_DATE)),
                            SDF.format(this.startDate)));
        }
        if (!ObjectUtil.isEmpty(this.endDate)) {
            this.filterList.add(1, END_DATE);
            this.filterData.put(
                    END_DATE,
                    getFilterDetailMap(String.valueOf(properties.get(END_DATE)),
                            SDF.format(this.endDate)));
        }
    }

    private Map<String, Object> getFilterDetailMap(String propertyValue, String defaultValue) {

        Map<String, Object> detailMap = new LinkedHashMap<String, Object>();
        detailMap.put(ExportUtil.FILTER_PROPERTY_VALUE, propertyValue);
        detailMap.put(ExportUtil.FILTER_DEFAULT_VALUE, defaultValue);
        return detailMap;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework
     * .context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }

}