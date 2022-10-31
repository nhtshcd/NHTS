/*
 * DashboardAction.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.action;

import java.io.IOException;
import static java.util.stream.Collectors.joining;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.poi.util.SystemOutLogger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonObject;
import com.sourcetrace.eses.entity.Dashboard;
import com.sourcetrace.eses.entity.Device;
import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.entity.MasterData;
import com.sourcetrace.eses.entity.Packhouse;

import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.CurrencyUtil;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;


/**
 * @author Administrator
 */
public class AgroDashboardAction extends ESEAction {

	private static final Logger logger = Logger.getLogger(AgroDashboardAction.class);
	private static final long serialVersionUID = 43113144066190107L;

	private String branchIdValue;

	double totalHousingCost;
	double totalFeedCost;
	double totalTreatmentCost;
	double totalOtherCost;
	List<String> farmCodes = new ArrayList<>();
	List<String> farmCodez = new ArrayList<>();

	int count = 0;
	int keyCount = 0;
	int icsTyp = 0;
	
	@Autowired
	private IFarmerService farmerService;
	
	private String selectedBranch;
	private String dateRange;	
	DecimalFormat formatter = new DecimalFormat("0.00");
	private String selectedCooperative;
	Map<String, String> genderList = new LinkedHashMap<String, String>();
	List<Packhouse> samithi = new ArrayList<Packhouse>();
	private String selectedSeason;
	private String agentId;
	private String farmerId;
	private String currentYear;
	private String gramPanchayatEnable;
	private String selectedStatus;
	private String divId;
	private String exporterId;
	private String selectedExporter;
				
	public String getSelectedExporter() {
		return selectedExporter;
	}

	public void setSelectedExporter(String selectedExporter) {
		this.selectedExporter = selectedExporter;
	}

	public String getExporterId() {
		return String.valueOf(getLoggedInDealer());
	}

	public void setExporterId(String exporterId) {
		this.exporterId = exporterId;
	}

	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}
	
	public String getSelectedCooperative() {
		return selectedCooperative;
	}

	public void setSelectedCooperative(String selectedCooperative) {
		this.selectedCooperative = selectedCooperative;
	}

	public String list() throws Exception {
		Calendar currentDate = Calendar.getInstance();
        int iYear = currentDate.get(Calendar.YEAR);
        setCurrentYear(String.valueOf(iYear));
		return LIST;
	}

	Date stDate=null;
	Date edDate=null;
	@SuppressWarnings("unchecked")
	public void populateMethod() {

		String descStatus = "fa fa-sort-desc text-danger";
		String ascStatus = "fa fa-sort-asc text-success";
		String textClassAsc = "text-success";
		String textClassDesc = "text-danger";
		 
		if (!StringUtil.isEmpty(getDateRange())) {
			String[] dateSplit = getDateRange().split("-");
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);			
			 stDate = DateUtil.convertStringToDate(dateSplit[0], DateUtil.DATE_FORMAT);
			 edDate = DateUtil.convertStringToDate(dateSplit[1], DateUtil.DATE_FORMAT);
			 stDate=DateUtil.getDateWithStartMinuteofDay(stDate);
			 edDate=DateUtil.getDateWithLastMinuteofDay(edDate);
			//sDate=simpleDateFormat.format(DateUtil.convertStringToDate(dateSplit[0].toString(), DateUtil.DATE_FORMAT));
			//eDate=simpleDateFormat.format(DateUtil.convertStringToDate(dateSplit[1].toString(), DateUtil.DATE_FORMAT));						
		}
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		JSONObject jsonObject = null;
		jsonObject = new JSONObject();
		localizeMenu();
		request.setAttribute("heading", getText("dashboardlist"));

		branchIdValue = getBranchId();
		if (StringUtil.isEmpty(branchIdValue)) {
			buildBranchMap();
		}
		
		if(!StringUtil.isEmpty(selectedExporter)){
			exporterId=selectedExporter;
		}else{
			exporterId=getExporterId();
		}
		Integer totalUserCount = utilService.findUserCount(stDate,edDate,Long.valueOf(exporterId));
		Integer totalMobileUserCount = utilService.findMobileUserCount(stDate,edDate,Long.valueOf(exporterId));
		Integer totalDeviceCount = utilService.findDeviceCount(stDate,edDate,Long.valueOf(exporterId));
		Integer totalFarmersCount = farmerService.findFarmersCountByStatus(stDate,edDate,Long.valueOf(exporterId));
		Integer fc = farmerService.findFarmsCount(stDate,edDate,Long.valueOf(exporterId));
		Integer	totalWarehouseCount = utilService.findWarehouseCount(stDate,edDate,Long.valueOf(exporterId));
		String fa = farmerService.findFarmTotalLandAreaCount(stDate,edDate,Long.valueOf(exporterId));
		Integer	totalCustomerCount = utilService.findCustomerCount(stDate,edDate,Long.valueOf(exporterId));
		Integer	totalProductsCount = utilService.findProductsCount(stDate,edDate,Long.valueOf(exporterId));
		Integer	totalShipmentsCount = utilService.findShipmentsCount(stDate,edDate,Long.valueOf(exporterId));
		Double	totalShipmentQuantity = utilService.findShipmentQuantity(stDate,edDate,Long.valueOf(exporterId));
		String	totalPlantingArea= utilService.findPlantingArea(stDate,edDate,Long.valueOf(exporterId));
		Integer	totalScoutingCount = utilService.findScoutingCount(stDate,edDate,Long.valueOf(exporterId));
		Integer	totalSprayingCount = utilService.findSprayingCount(stDate,edDate,Long.valueOf(exporterId));
		
		
		
		jsonObject.put("userCount", String.valueOf(totalUserCount));		
		jsonObject.put("mobileUsersCount", String.valueOf(totalMobileUserCount));
		jsonObject.put("deviceCount", String.valueOf(totalDeviceCount));
		jsonObject.put("farmerCount", String.valueOf(totalFarmersCount));
		jsonObject.put("warehouseCount", String.valueOf(totalWarehouseCount));
		jsonObject.put("farmCount", String.valueOf(fc));
		jsonObject.put("farmArea", String.valueOf(fa));
		jsonObject.put("customerCount", String.valueOf(totalCustomerCount));
		
		jsonObject.put("totalProductsCount", String.valueOf(totalProductsCount));
		jsonObject.put("totalShipmentsCount", String.valueOf(totalShipmentsCount));
		jsonObject.put("totalShipmentQuantity", !StringUtil.isEmpty(totalShipmentQuantity) ? String.valueOf(totalShipmentQuantity) : "0");
		jsonObject.put("totalPlantingArea", String.valueOf(totalPlantingArea));
		jsonObject.put("totalScoutingCount", String.valueOf(totalScoutingCount));
		jsonObject.put("totalSprayingCount", String.valueOf(totalSprayingCount));
		jsonObject.put("NoDataAvailable", getLocaleProperty("NoDataAvailable"));
		
		jsonObjects.add(jsonObject);
		printAjaxResponse(jsonObjects, "text/html");

	}

	/*@SuppressWarnings("unchecked")
	public void populateTxnChartData() {
		JSONObject jsonObject = new JSONObject();
		List<List<JSONObject>> jsonObjects = new ArrayList<>();
		List<JSONObject> labelDataJsonObjects = new ArrayList<>();
		if (getCurrentTenantId().equalsIgnoreCase(ESESystem.BLRI_TENANT_ID)) {
			List<Object> cowMilkData;
			if (!StringUtil.isEmpty(selectedYear)) {
				cowMilkData = productService.listcowMilkByMonth(
						DateUtil.getFirstDateOfMonth(Integer.valueOf(selectedYear), 0),
						DateUtil.getFirstDateOfMonth(Integer.valueOf(selectedYear), 12));
			} else {
				cowMilkData = productService.listcowMilkByMonth(
						DateUtil.getFirstDateOfMonth(DateUtil.getCurrentYear(), 0),
						DateUtil.getFirstDateOfMonth(DateUtil.getCurrentYear(), 12));
			}

			List<JSONObject> milkDataJsonObjects = new ArrayList<>();

			for (int i = 1; i <= 12; i++) {
				JSONObject supplyDataJson = new JSONObject();
				supplyDataJson.put("id", i);
				supplyDataJson.put("year", "");
				supplyDataJson.put("month", i);
				supplyDataJson.put("Qty", 0);
				milkDataJsonObjects.add(supplyDataJson);
			}

			cowMilkData.stream().forEach(obj -> {
				JSONObject supplyDataJson = new JSONObject();
				Object[] objArr = (Object[]) obj;
				supplyDataJson = milkDataJsonObjects.get(Integer.parseInt(String.valueOf(objArr[1])) - 1);
				supplyDataJson.put("id", objArr[1]);
				supplyDataJson.put("year", objArr[0]);
				supplyDataJson.put("month", objArr[1]);
				supplyDataJson.put("Qty", objArr[2]);
				milkDataJsonObjects.add(supplyDataJson);
			});

			jsonObject.put("Label", getLocaleProperty("groupchart.title"));
			jsonObject.put("disLabel", "");
			jsonObject.put("harvestLabel", getLocaleProperty("cowMilk"));
			jsonObject.put("saleLabel", "");

			labelDataJsonObjects.add(jsonObject);
			jsonObjects.add(new ArrayList<JSONObject>());
			jsonObjects.add(milkDataJsonObjects);
			jsonObjects.add(new ArrayList<JSONObject>());
			jsonObjects.add(labelDataJsonObjects);

		} else {
			if (!StringUtil.isEmpty(getDateRange())) {
				String[] dateSplit = getDateRange().split("-");
				Date sDate = DateUtil.convertStringToDate(dateSplit[0] + " 00:00:00", DateUtil.DATE_TIME_FORMAT);
				Date eDate = DateUtil.convertStringToDate(dateSplit[1] + " 23:59:59", DateUtil.DATE_TIME_FORMAT);

				List<Object> supplyData = productService.listCropSaleQtyByMoth(sDate, eDate,selectedBranch);
				List<Object> harvestData = productService.listCropHarvestByMoth(sDate, eDate,selectedBranch);
				List<Object> distributionData = productService.listDistributionQtyByMoth(sDate, eDate,selectedBranch);

				List<Object> procurementData = productService.listProcurementAmtByMoth(sDate, eDate,selectedBranch);

				List<Object> enrollmentData = productService.listEnrollmentByMoth(sDate, eDate);

				List<JSONObject> supplyDataJsonObjects = new ArrayList<>();
				List<JSONObject> harvestDataJsonObjects = new ArrayList<>();
				List<JSONObject> distributionDataJsonObjects = new ArrayList<>();
				List<JSONObject> procurementDataJsonObjects = new ArrayList<>();
				List<JSONObject> enrollmentDataJsonObjects = new ArrayList<>();
				List<JSONObject> procurementQtyDataJsonObjects = new ArrayList<>();
				for (int i = 1; i <= 12; i++) {
					JSONObject supplyDataJson = new JSONObject();
					supplyDataJson.put("id", i);
					supplyDataJson.put("year", "");
					supplyDataJson.put("month", i);
					supplyDataJson.put("Qty", 0);
					supplyDataJsonObjects.add(supplyDataJson);
				}

				supplyData.stream().forEach(obj -> {
					JSONObject supplyDataJson = new JSONObject();
					Object[] objArr = (Object[]) obj;
					supplyDataJson = supplyDataJsonObjects.get(Integer.parseInt(String.valueOf(objArr[1])) - 1);
					supplyDataJson.put("id", objArr[1]);
					supplyDataJson.put("year", objArr[0]);
					supplyDataJson.put("month", objArr[1]);
					supplyDataJson.put("Qty", objArr[2]);
					// supplyDataJsonObjects.add(supplyDataJson);
					supplyDataJsonObjects.set(Integer.parseInt(String.valueOf(objArr[1])) - 1, supplyDataJson);
				});

				for (int i = 1; i <= 12; i++) {
					JSONObject harvestDataJson = new JSONObject();
					harvestDataJson.put("id", i);
					harvestDataJson.put("year", "");
					harvestDataJson.put("month", i);
					harvestDataJson.put("Qty", 0);
					harvestDataJsonObjects.add(harvestDataJson);
				}

				harvestData.stream().forEach(obj -> {
					JSONObject harvestDataJson = new JSONObject();
					Object[] objArr = (Object[]) obj;
					harvestDataJson = harvestDataJsonObjects.get(Integer.parseInt(String.valueOf(objArr[1])) - 1);
					harvestDataJson.put("year", objArr[0]);
					harvestDataJson.put("month", objArr[1]);
					harvestDataJson.put("Qty", objArr[2]);
					harvestDataJsonObjects.set(Integer.parseInt(String.valueOf(objArr[1])) - 1, harvestDataJson);
				});

				for (int i = 1; i <= 12; i++) {
					JSONObject distributionDataJson = new JSONObject();
					distributionDataJson.put("id", i);
					distributionDataJson.put("year", "");
					distributionDataJson.put("month", i);
					distributionDataJson.put("Qty", 0);
					distributionDataJsonObjects.add(distributionDataJson);
				}

				distributionData.stream().forEach(obj -> {
					JSONObject distributionDataJson = new JSONObject();
					Object[] objArr = (Object[]) obj;
					distributionDataJson = distributionDataJsonObjects
							.get(Integer.parseInt(String.valueOf(objArr[1])) - 1);
					distributionDataJson.put("year", objArr[0]);
					distributionDataJson.put("month", objArr[1]);
					distributionDataJson.put("Qty", objArr[2]);
					distributionDataJsonObjects.set(Integer.parseInt(String.valueOf(objArr[1])) - 1,
							distributionDataJson);
				});

				for (int i = 1; i <= 12; i++) {
					JSONObject procurementDataJson = new JSONObject();
					procurementDataJson.put("year", "");
					procurementDataJson.put("month", i);
					procurementDataJson.put("amt", 0);
					procurementDataJsonObjects.add(procurementDataJson);
				}

				procurementData.stream().forEach(obj -> {
					JSONObject procurementDataJson = new JSONObject();
					Object[] objArr = (Object[]) obj;
					procurementDataJson = procurementDataJsonObjects
							.get(Integer.parseInt(String.valueOf(objArr[1])) - 1);
					procurementDataJson.put("year", objArr[0]);
					procurementDataJson.put("month", objArr[1]);
					procurementDataJson.put("amt", objArr[2]);
					procurementDataJsonObjects.set(Integer.parseInt(String.valueOf(objArr[1])) - 1,
							procurementDataJson);
				});

				for (int i = 1; i <= 12; i++) {
					JSONObject enrollmentDataJson = new JSONObject();
					enrollmentDataJson.put("year", "");
					enrollmentDataJson.put("month", i);
					enrollmentDataJson.put("nos", 0);
					enrollmentDataJsonObjects.add(enrollmentDataJson);
				}

				enrollmentData.stream().forEach(obj -> {
					JSONObject enrollmentDataJson = new JSONObject();
					Object[] objArr = (Object[]) obj;
					enrollmentDataJson = enrollmentDataJsonObjects.get(Integer.parseInt(String.valueOf(objArr[1])) - 1);
					enrollmentDataJson.put("year", objArr[0]);
					enrollmentDataJson.put("month", objArr[1]);
					enrollmentDataJson.put("nos", objArr[2]);
					enrollmentDataJsonObjects.set(Integer.parseInt(String.valueOf(objArr[1])) - 1, enrollmentDataJson);
				});

				for (int i = 1; i <= 12; i++) {
					JSONObject procurementQtyDataJson = new JSONObject();
					procurementQtyDataJson.put("year", "");
					procurementQtyDataJson.put("month", i);
					procurementQtyDataJson.put("qty", 0);
					procurementQtyDataJsonObjects.add(procurementQtyDataJson);
				}

				procurementData.stream().forEach(obj -> {
					JSONObject procurementQtyDataJson = new JSONObject();
					Object[] objArr = (Object[]) obj;
					procurementQtyDataJson = procurementQtyDataJsonObjects
							.get(Integer.parseInt(String.valueOf(objArr[1])) - 1);
					procurementQtyDataJson.put("year", objArr[0]);
					procurementQtyDataJson.put("month", objArr[1]);
					procurementQtyDataJson.put("qty", objArr[3]);
					procurementQtyDataJson.put("unit", "Kgs");
					procurementQtyDataJsonObjects.set(Integer.parseInt(String.valueOf(objArr[1])) - 1,
							procurementQtyDataJson);
				});

				jsonObject.put("Label", getLocaleProperty("groupchart.title"));
				jsonObject.put("disLabel", getLocaleProperty("distribution"));
				jsonObject.put("harvestLabel", getLocaleProperty("cropharvest"));
				jsonObject.put("saleLabel", getLocaleProperty("cropSale"));
				jsonObject.put("procurementLabel", getLocaleProperty("procurement"));
				jsonObject.put("enrollmentLabel", getLocaleProperty("enrollment"));
				jsonObject.put("procurementQtyLabel", getLocaleProperty("procurementQtyLabel"));
				jsonObject.put("unit", "Kg");
				labelDataJsonObjects.add(jsonObject);

				jsonObjects.add(supplyDataJsonObjects);
				jsonObjects.add(harvestDataJsonObjects);
				jsonObjects.add(distributionDataJsonObjects);
				jsonObjects.add(procurementDataJsonObjects);
				jsonObjects.add(enrollmentDataJsonObjects);
				jsonObjects.add(labelDataJsonObjects);
			}
		}

		printAjaxResponse(jsonObjects, "text/html");
	}*/
	
	@SuppressWarnings("unchecked")
	public void populateFarmersByLocationBranch() {
		JSONArray jsonObjArray = new JSONArray();	
		List<JSONObject> villageCollectorByLocation = new ArrayList<JSONObject>();
		JSONObject final_jsonObject = new JSONObject();
		if (StringUtil.isEmpty(getSelectedBranch())) {
			List<Object[]> farmersByBranch = farmerService.farmersByBranch();
			farmersByBranch.stream().forEach(obj -> {
				Object[] objArr = (Object[]) obj;
				JSONArray jsonArr = new JSONArray();				
				jsonArr.add(objArr[1]);
				jsonArr.add(objArr[0]);				
				jsonObjArray.add(jsonArr);
			});
		}
		final_jsonObject.put("brArray",jsonObjArray);
		villageCollectorByLocation.add(final_jsonObject);
		printAjaxResponse(villageCollectorByLocation, "text/html");
	}
	
	String sDate=null;
	String eDate=null;
	@SuppressWarnings("unchecked")
	public void populateCharts() {
		
		List<JSONObject> chartCollection = new ArrayList<JSONObject>();				
		List<Dashboard> dashboardList= farmerService.listDashboardDataByChartDivId();		
		
		if (!StringUtil.isEmpty(getDateRange())) {
			String[] dateSplit = getDateRange().split("-");
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);			
			// sDate = DateUtil.convertStringToDate(dateSplit[0], DateUtil.DATE_FORMAT);
			// eDate = DateUtil.convertStringToDate(dateSplit[1], DateUtil.DATE_FORMAT);
			sDate=simpleDateFormat.format(DateUtil.convertStringToDate(dateSplit[0].toString(), DateUtil.DATE_FORMAT));
			eDate=simpleDateFormat.format(DateUtil.convertStringToDate(dateSplit[1].toString(), DateUtil.DATE_FORMAT));						
		}
		if(!StringUtil.isEmpty(selectedExporter)){
			exporterId=selectedExporter;
		}else{
			exporterId=getExporterId();
		}
		dashboardList.stream().forEach(d -> {
			JSONObject final_jsonObject = new JSONObject();
			JSONArray jsonObjArray = new JSONArray();	
			JSONArray nameArr = new JSONArray();
		if(!ObjectUtil.isEmpty(d) && !StringUtil.isEmpty(d.getChartQuery())){
			List<Object[]> result = farmerService.executeChartQuery(d.getChartQuery(),d.getDateFilter(),d.getDateFilterField(),sDate,eDate,Long.valueOf(exporterId),
					d.getExporterFilterField(),d.getGroupByField(),d.getOrderByField());
			
			if(d.getChartType().equalsIgnoreCase("bar") || d.getChartType().equalsIgnoreCase("column")){
			result.stream().forEach(obj -> {
				Object[] objArr = (Object[]) obj;
				JSONArray jsonArr = new JSONArray();
				JSONArray jsonArr1 = new JSONArray();
				JSONArray jsonArr2 = new JSONArray();
				JSONArray jsonArr3 = new JSONArray();
				JSONObject jo = new JSONObject();
				JSONObject jo1 = new JSONObject();
				JSONObject jo2 = new JSONObject();
				JSONObject jo3 = new JSONObject();
				jo.put("name",getLocaleProperty("Total"));
				jsonArr.add(objArr[0]);
				jo.put("data", jsonArr);
				jsonObjArray.add(jo);
				
				jo1.put("name",getLocaleProperty("Active"));
				jsonArr1.add(objArr[1]);	
				jo1.put("data", jsonArr1);
				jsonObjArray.add(jo1);
				
				jo2.put("name",getLocaleProperty("InActive"));
				jsonArr2.add(objArr[2]);	
				jo2.put("data", jsonArr2);
				jsonObjArray.add(jo2);
								
			});
						
			nameArr.add(d.getXaxisCategory());						
			}
			else if(d.getChartType().equalsIgnoreCase("line")){	
				JSONArray jsonArr = new JSONArray();					
				JSONObject jo = new JSONObject();	
				result.stream().forEach(obj -> {
					Object[] objArr = (Object[]) obj;
													
					jsonArr.add(objArr[2]);
					jo.put("data", jsonArr);
					jo.put("name",objArr[0].toString());
					
			});
				jsonObjArray.add(jo);
			}
			else if(d.getChartType().equalsIgnoreCase("pie")){						
				result.stream().forEach(obj -> {
					Object[] objArr = (Object[]) obj;	
					JSONObject jo = new JSONObject();
					JSONObject jo1 = new JSONObject();
					JSONObject jo2 = new JSONObject();
					JSONObject jo3 = new JSONObject();
					jo.put("name",getLocaleProperty("Total"));
					jo.put("y", objArr[0]);
					jsonObjArray.add(jo);
					
					jo1.put("name",getLocaleProperty("Active"));	
					jo1.put("y", objArr[1]);
					jsonObjArray.add(jo1);
					
					jo2.put("name",getLocaleProperty("InActive"));						
					jo2.put("y", objArr[2]);
					jsonObjArray.add(jo2);
										
			});				
			}			
			else if(d.getChartType().equalsIgnoreCase("donutActive")){						
				result.stream().forEach(obj -> {
					Object[] objArr = (Object[]) obj;	
					JSONArray jsonArr = new JSONArray();
					JSONArray jsonArr1 = new JSONArray();
					JSONArray jsonArr2 = new JSONArray();
					JSONArray jsonArr3 = new JSONArray();
					jsonArr.add(getLocaleProperty("Total"));
					jsonArr.add(objArr[0]);
					jsonObjArray.add(jsonArr);
					
					jsonArr1.add(getLocaleProperty("Active"));	
					jsonArr1.add(objArr[1]);
					jsonObjArray.add(jsonArr1);
					
					jsonArr2.add(getLocaleProperty("InActive"));						
					jsonArr2.add(objArr[2]);
					jsonObjArray.add(jsonArr2);
					
			});				
			}
			else if(d.getChartType().equalsIgnoreCase("semiCircle") || d.getChartType().equalsIgnoreCase("donut")){	
													
				result.stream().forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					JSONArray jsonArr = new JSONArray();
					jsonArr.add(objArr[0]);								
					jsonArr.add(objArr[1]);	
					jsonObjArray.add(jsonArr);
			});				
			}
			else if(d.getChartType().equalsIgnoreCase("stackedBar")){	
				JSONArray jsonArr = new JSONArray();
				JSONObject jsonObj1 = new JSONObject();
				JSONObject jsonObj2 = new JSONObject();
				JSONArray jsonArr1 = new JSONArray();
				JSONArray jsonArr2 = new JSONArray();
				result.stream().forEach(obj -> {
					Object[] objArr = (Object[]) obj;															
					nameArr.add(objArr[0]);	
					jsonArr1.add(objArr[2]);	
					jsonArr2.add(objArr[3]);									
			});		
				String a[]=d.getBarLabels().split(",");
				jsonObj1.put("name", getLocaleProperty(a[0].toString()));
				jsonObj1.put("data", jsonArr1);
				jsonObj2.put("name", getLocaleProperty(a[1].toString()));
				jsonObj2.put("data", jsonArr2);
				
				jsonObjArray.add(jsonObj2);
				jsonObjArray.add(jsonObj1);
								
			}
			else if(d.getChartType().equalsIgnoreCase("comparisonBar")){	
				JSONArray jsonArr = new JSONArray();
				JSONObject jsonObj1 = new JSONObject();
				JSONObject jsonObj2 = new JSONObject();
				JSONArray jsonArr1 = new JSONArray();
				JSONArray jsonArr2 = new JSONArray();
				result.stream().forEach(obj -> {
					Object[] objArr = (Object[]) obj;															
					nameArr.add(objArr[0]);	
					jsonArr1.add(objArr[2]);	
					jsonArr2.add(objArr[3]);									
			});	
				String a[]=d.getBarLabels().split(",");
				jsonObj1.put("name", getLocaleProperty(a[0].toString()));
				jsonObj1.put("data", jsonArr1);
				jsonObj2.put("name", getLocaleProperty(a[1].toString()));
				jsonObj2.put("data", jsonArr2);
				
				jsonObjArray.add(jsonObj1);
				jsonObjArray.add(jsonObj2);
								
			}
			
			final_jsonObject.put("chartDivId",d.getChartDivId());
			final_jsonObject.put("chartType",d.getChartType());
			final_jsonObject.put("chartTitle",d.getChartTitle());
			final_jsonObject.put("chartSubTittle",d.getChartSubTittle());
			final_jsonObject.put("xaxisCategory",nameArr);
			final_jsonObject.put("yaxisTitle",d.getYaxisTitle());
			final_jsonObject.put("xaxisTitle",d.getXaxisTitle());
			final_jsonObject.put("tooltipSuffix",d.getTooltipSuffix());
			final_jsonObject.put("legendEnable",d.getLegendEnable());
			final_jsonObject.put("labelEnable",d.getLegendEnable());
			final_jsonObject.put("stackLabelEnable",d.getStackLabelEnable());
			final_jsonObject.put("color",d.getColor());
			final_jsonObject.put("chartType",d.getChartType());
			final_jsonObject.put("seriesName",d.getSeriesName());
			final_jsonObject.put("datalabelFormat",d.getDatalabelFormat());
			final_jsonObject.put("tooltipPointFormat",d.getTooltipPointFormat());	
			final_jsonObject.put("tabs",d.getTabs());	
			final_jsonObject.put("seriesdata",jsonObjArray);
		}		
		chartCollection.add(final_jsonObject);
		});
		
		printAjaxResponse(chartCollection, "text/html");
	}
	
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}
	
	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public String getSelectedStatus() {
		return selectedStatus;
	}

	public void setSelectedStatus(String selectedStatus) {
		this.selectedStatus = selectedStatus;
	}

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}
	
	public String getCurrentSeason() {

		String val = "";
		return val = utilService.findCurrentSeasonCodeByBranchId(getBranchId());
	}
	
	protected void printAjaxResponse(Object value, String contentType) {

		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			if (!StringUtil.isEmpty(contentType)) {
				response.setContentType(contentType);
			}
			out = response.getWriter();
			out.print(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getSelectedBranch() {

		if (!StringUtil.isEmpty(getBranchId())) {
			selectedBranch = getBranchId();
		}
		return selectedBranch;
	}
	
	public String getGramPanchayatEnable() {

		return gramPanchayatEnable;
	}

	public void setGramPanchayatEnable(String gramPanchayatEnable) {

		this.gramPanchayatEnable = gramPanchayatEnable;
	}
}
