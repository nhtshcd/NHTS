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
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.entity.Device;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.Procurement;


import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.entity.Village;

import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;


/**
 * @author Administrator
 */
public class DashboardAction extends ESEAction {

	private static final Logger logger = Logger.getLogger(DashboardAction.class);
	private static final long serialVersionUID = 43113144066190107L;

	private String branchIdValue;
	private String selectedTaluk;
	private String selectedVillage;
	private Integer userCount;
	private String selectedYear;



	List<String> farmCodes = new ArrayList<>();
	List<String> farmCodez = new ArrayList<>();
	private String selectedState;
	private String selectedGender;
	private String selectedVariety;
	private String selectedStatus;
	int count = 0;
	int keyCount = 0;
	int icsTyp = 0;

	// private String
	@Autowired
	private IFarmerService farmerService;

	@Autowired
	private IUtilService utilService;
	
	private String selectedBranch;
	List<Municipality> cities = new ArrayList<Municipality>();
	DecimalFormat formatter = new DecimalFormat("0.00");
	private String selectedCrop;
	Map<String, String> genderList = new LinkedHashMap<String, String>();
	
	private String currentYear;
private String gramPanchayatEnable="0";
private String codeForCropChart;
private String locationLevel;
public String getLocationLevel() {
	return locationLevel;
}

public void setLocationLevel(String locationLevel) {
	this.locationLevel = locationLevel;
}

public String getCodeForCropChart() {
	return codeForCropChart;
}

public void setCodeForCropChart(String codeForCropChart) {
	this.codeForCropChart = codeForCropChart;
}
	public String getGramPanchayatEnable() {
	return gramPanchayatEnable;
}

public void setGramPanchayatEnable(String gramPanchayatEnable) {
	this.gramPanchayatEnable = gramPanchayatEnable;
}

	public String getSelectedCrop() {
		return selectedCrop;
	}

	public void setSelectedCrop(String selectedCrop) {
		this.selectedCrop = selectedCrop;
	}

	
	

	public String list() throws Exception {
		Calendar currentDate = Calendar.getInstance();
        int iYear = currentDate.get(Calendar.YEAR);
        setCurrentYear(String.valueOf(iYear));
		return LIST;
	}

	@SuppressWarnings("unchecked")
	public void populateMethod() {
/*
		String descStatus = "fa fa-sort-desc text-danger";
		String ascStatus = "fa fa-sort-asc text-success";
		String textClassAsc = "text-success";
		String textClassDesc = "text-danger";
		Integer totalWarehouseCount = 0;

		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		JSONObject jsonObject = null;
		jsonObject = new JSONObject();
		localizeMenu();
		request.setAttribute("heading", getText("dashboardlist"));

		branchIdValue = getBranchId();
		if (StringUtil.isEmpty(branchIdValue)) {
			buildBranchMap();
		}
		Integer totalUserCount = utilService.findUserCount();
		Integer currentMonthUserCount = utilService.findUserCountByMonth(DateUtil.getFirstDateOfMonth(), new Date());
		Integer prevMonthUserCount = utilService.findUserCountByMonth(DateUtil.getLastMonthStartDate(),
				DateUtil.getLastMonthEndDate());

		Double currentMonthUserPercentage = (Double.parseDouble(String.valueOf(totalUserCount))
				* Double.parseDouble(String.valueOf(currentMonthUserCount))) / 100;

		Double prevMonthUserPercentage = (Double.parseDouble(String.valueOf(totalUserCount))
				* Double.parseDouble(String.valueOf(prevMonthUserCount))) / 100;

		jsonObject.put("userCount", String.valueOf(totalUserCount));
		if ((currentMonthUserPercentage) < (prevMonthUserPercentage)) {
			jsonObject.put("userCountPercentage", String.valueOf(currentMonthUserPercentage));
			jsonObject.put("userCountstauts", descStatus);
			jsonObject.put("userText", textClassDesc);
		} else {
			jsonObject.put("userCountPercentage", String.valueOf(currentMonthUserPercentage));
			jsonObject.put("userCountstauts", ascStatus);
			jsonObject.put("userText", textClassAsc);
		}

		Integer totalMobileUserCount = utilService.findMobileUserCount();
		Integer currentMonthMobileUsersCount = utilService.findMobileUserCountByMonth(DateUtil.getFirstDateOfMonth(),
				new Date());
		Integer prevMonthMobileUserCount = utilService.findMobileUserCountByMonth(DateUtil.getLastMonthStartDate(),
				DateUtil.getLastMonthEndDate());
		Double currentMonthMobileUserPercentage = (Double.parseDouble(String.valueOf(totalMobileUserCount))
				* Double.parseDouble(String.valueOf(currentMonthMobileUsersCount))) / 100;
		Double prevMonthMobileUserPercentage = (Double.parseDouble(String.valueOf(totalMobileUserCount))
				* Double.parseDouble(String.valueOf(prevMonthMobileUserCount))) / 100;

		jsonObject.put("mobileUsersCount", String.valueOf(totalMobileUserCount));
		if ((currentMonthMobileUserPercentage) < (prevMonthMobileUserPercentage)) {
			jsonObject.put("mobileuserCountPercentage", String.valueOf(prevMonthMobileUserPercentage));
			jsonObject.put("mobileuserCountstauts", descStatus);
			jsonObject.put("mobileText", textClassDesc);
		} else {
			jsonObject.put("mobileuserCountPercentage", String.valueOf(currentMonthMobileUserPercentage));
			jsonObject.put("mobileuserCountstauts", ascStatus);
			jsonObject.put("mobileText", textClassAsc);
		}

		Integer totalDeviceCount = utilService.findDeviceCount();
		Integer currentMonthDeviceCount = utilService.findDeviceCountByMonth(DateUtil.getFirstDateOfMonth(),
				new Date());
		Integer previousMonthDeviceCount = utilService.findDeviceCountByMonth(DateUtil.getLastMonthStartDate(),
				DateUtil.getLastMonthEndDate());
		Double currentMonthDevicePercentage = (Double.parseDouble(String.valueOf(totalDeviceCount))
				* Double.parseDouble(String.valueOf(currentMonthDeviceCount))) / 100;
		Double prevMonthDevicePercentage = (Double.parseDouble(String.valueOf(totalDeviceCount))
				* Double.parseDouble(String.valueOf(previousMonthDeviceCount))) / 100;

		jsonObject.put("deviceCount", String.valueOf(totalDeviceCount));
		if ((currentMonthDevicePercentage) < (prevMonthDevicePercentage)) {
			jsonObject.put("deviceCountPercentage", String.valueOf(currentMonthDevicePercentage));
			jsonObject.put("deviceCountstauts", descStatus);
			jsonObject.put("deviceText", textClassDesc);
		} else {
			jsonObject.put("deviceCountPercentage", String.valueOf(currentMonthDevicePercentage));
			jsonObject.put("deviceCountstauts", ascStatus);
			jsonObject.put("deviceText", textClassAsc);
		}

		Integer totalFarmersCount = farmerService.findFarmersCountByStatus();

		//Integer totalFarmersCountBySeason = farmerService.findFarmersCountByStatusAndSeason(getCurrentSeason());

		Integer currentMonthFarmerCount = farmerService.findFarmerCountByMonth(DateUtil.getFirstDateOfMonth(),
				new Date());
		Integer previousMonthFarmerCount = farmerService.findFarmerCountByMonth(DateUtil.getLastMonthStartDate(),
				DateUtil.getLastMonthEndDate());
		Double currentMonthFarmerPercentage = (Double.parseDouble(String.valueOf(totalFarmersCount))
				* Double.parseDouble(String.valueOf(currentMonthFarmerCount))) / 100;
		Double prevMonthFarmerPercentage = (Double.parseDouble(String.valueOf(totalFarmersCount))
				* Double.parseDouble(String.valueOf(previousMonthFarmerCount))) / 100;
		jsonObject.put("farmerCount", String.valueOf(totalFarmersCount));
		// jsonObject.put("farmerCount", String.valueOf(totalFarmersCount));
		if ((currentMonthFarmerPercentage) < (prevMonthFarmerPercentage)) {
			jsonObject.put("farmerCountPercentage", String.valueOf(currentMonthFarmerPercentage));
			jsonObject.put("farmerCountstauts", descStatus);
			jsonObject.put("farmerText", textClassDesc);
		} else {
			jsonObject.put("farmerCountPercentage", String.valueOf(currentMonthFarmerPercentage));
			jsonObject.put("farmerCountstauts", ascStatus);
			jsonObject.put("farmerText", textClassAsc);
		}
		
		*//** warehouse count **//*

			totalWarehouseCount = utilService.findWarehouseCount();
		Integer currentMonthWarehouseCount = utilService.findWarehouseCountByMonth(DateUtil.getFirstDateOfMonth(),
				new Date());
		Integer previousMonthWarehouseCount = utilService
				.findWarehouseCountByMonth(DateUtil.getLastMonthStartDate(), DateUtil.getLastMonthEndDate());
		Double currentMonthWarehousePercentage = (Double.parseDouble(String.valueOf(totalWarehouseCount))
				* Double.parseDouble(String.valueOf(currentMonthWarehouseCount))) / 100;
		Double prevMonthWarehousePercentage = (Double.parseDouble(String.valueOf(totalWarehouseCount))
				* Double.parseDouble(String.valueOf(previousMonthWarehouseCount))) / 100;
		jsonObject.put("warehouseCount", String.valueOf(totalWarehouseCount));

		if ((currentMonthWarehousePercentage) < (prevMonthWarehousePercentage)) {
			jsonObject.put("warehouseCountPercentage", String.valueOf(currentMonthWarehousePercentage));
			jsonObject.put("warehouseCountstauts", descStatus);
			jsonObject.put("warehouseText", textClassDesc);
		} else {
			jsonObject.put("warehouseCountPercentage", String.valueOf(currentMonthWarehousePercentage));
			jsonObject.put("warehouseCountstauts", ascStatus);
			jsonObject.put("warehouseText", textClassAsc);
		}

		DecimalFormat df = new DecimalFormat("0.00");
		String totalFarmLandCount = "0";
		
		
			totalFarmLandCount=farmerService.findFarmTotalLandAreaCount();
			totalFarmLandCount=!StringUtil.isEmpty(totalFarmLandCount)
			? totalFarmLandCount : "0";
		String count = (df.format(Double.valueOf(totalFarmLandCount)));
		Integer currentMonthTotalFarmLandCount = farmerService.findFarmCountByMonth(DateUtil.getFirstDateOfMonth(),
				new Date());
		Integer previousMonthTotalFarmLandCount = farmerService.findFarmCountByMonth(DateUtil.getLastMonthStartDate(),
				DateUtil.getLastMonthEndDate());
		Double currentMonthTotalFarmLandPercentage = (Double.parseDouble(totalFarmLandCount)
				* Double.parseDouble(String.valueOf(currentMonthTotalFarmLandCount))) / 100;
		Double prevMonthTotalFarmLandPercentage = (Double.parseDouble(totalFarmLandCount)
				* Double.parseDouble(String.valueOf(previousMonthTotalFarmLandCount))) / 100;
		jsonObject.put("farmLandCount", String.valueOf(count));

		if ((currentMonthTotalFarmLandPercentage) < (prevMonthTotalFarmLandPercentage)) {
			jsonObject.put("farmLandCountPercentage", String.valueOf(df.format(currentMonthTotalFarmLandPercentage)));
			jsonObject.put("farmLandCountstauts", descStatus);
			jsonObject.put("farmLandText", textClassDesc);
		} else {
			jsonObject.put("farmLandCountPercentage", String.valueOf(df.format(currentMonthTotalFarmLandPercentage)));
			jsonObject.put("farmLandCountstauts", ascStatus);
			jsonObject.put("farmLandText", textClassAsc);
		}

		Double totalCottonAreaCount = farmerService.findTotalCottonAreaCount();

		Double currentMonthCottonAreaCount = !StringUtil
				.isEmpty(farmerService.findTotalCottonAreaCountByMonth(DateUtil.getFirstDateOfMonth(), new Date()))
						? farmerService.findTotalCottonAreaCountByMonth(DateUtil.getFirstDateOfMonth(), new Date()) : 0;

		Double previousMonthCottonAreaCount = !StringUtil.isEmpty(farmerService
				.findTotalCottonAreaCountByMonth(DateUtil.getLastMonthStartDate(), DateUtil.getLastMonthEndDate()))
						? farmerService.findTotalCottonAreaCountByMonth(DateUtil.getLastMonthStartDate(),
								DateUtil.getLastMonthEndDate())
						: 0;

		Double currentMonthCottonAreaPercentage = !StringUtil
				.isEmpty((Double.parseDouble(String.valueOf(totalCottonAreaCount))
						* Double.parseDouble(String.valueOf(currentMonthCottonAreaCount))) / 100)
								? (Double.parseDouble(String.valueOf(totalCottonAreaCount))
										* Double.parseDouble(String.valueOf(currentMonthCottonAreaCount))) / 100
								: 0D;

		Double prevMonthFarmPercentage = !StringUtil.isEmpty((Double.parseDouble(String.valueOf(totalCottonAreaCount))
				* Double.parseDouble(String.valueOf(previousMonthCottonAreaCount))) / 100)
						? (Double.parseDouble(String.valueOf(totalCottonAreaCount))
								* Double.parseDouble(String.valueOf(previousMonthCottonAreaCount))) / 100
						: 0D;
		Integer fc = farmerService.findFarmsCount();

		jsonObject.put("farmCount", String.valueOf(fc));
		if (getCurrentTenantId().equalsIgnoreCase("chetna")) {
			jsonObject.put("farmCount", df.format(totalCottonAreaCount));
		} else {
			Integer totalFarmCount = farmerService.findFarmsCount();
			jsonObject.put("farmCount", String.valueOf(totalFarmCount));
		}

		if ((currentMonthCottonAreaPercentage) < (prevMonthFarmPercentage)) {
			jsonObject.put("farmCountPercentage", String.valueOf(currentMonthCottonAreaPercentage));
			jsonObject.put("farmCountstauts", descStatus);
			jsonObject.put("farmText", textClassDesc);
		} else {
			jsonObject.put("farmCountPercentage", String.valueOf(currentMonthCottonAreaPercentage));
			jsonObject.put("farmCountstauts", ascStatus);
			jsonObject.put("farmText", textClassAsc);
		}

		*//** Crop Count **//*

		Integer totalCropCount = farmerService.findCropCount();

		jsonObject.put("cropCount", String.valueOf(totalCropCount));

		*//** Group Count **//*

		Integer totalGroupCount = utilService.findGroupCount();
		Integer currentMonthGroupCount = utilService.findGroupCountByMonth(DateUtil.getFirstDateOfMonth(),
				new Date());
		Integer previousMonthGroupCount = utilService.findGroupCountByMonth(DateUtil.getLastMonthStartDate(),
				DateUtil.getLastMonthEndDate());
		Double currentMonthGroupPercentage = (Double.parseDouble(String.valueOf(totalGroupCount))
				* Double.parseDouble(String.valueOf(currentMonthGroupCount))) / 100;
		Double prevMonthGroupPercentage = (Double.parseDouble(String.valueOf(totalGroupCount))
				* Double.parseDouble(String.valueOf(previousMonthGroupCount))) / 100;
		jsonObject.put("groupCount", String.valueOf(totalGroupCount));

		if ((currentMonthGroupPercentage) < (prevMonthGroupPercentage)) {
			jsonObject.put("groupCountPercentage", String.valueOf(currentMonthGroupPercentage));
			jsonObject.put("groupCountstauts", descStatus);
			jsonObject.put("groupText", textClassDesc);
		} else {
			jsonObject.put("groupCountPercentage", String.valueOf(currentMonthGroupPercentage));
			jsonObject.put("groupCountstauts", ascStatus);
			jsonObject.put("groupText", textClassAsc);
		}

		// System.out.println(currentMonthUserCount + "-" + prevMonthUserCount);

		jsonObjects.add(jsonObject);*/
		printAjaxResponse(new JSONArray(), "text/html");

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

	@SuppressWarnings("unchecked")
	public void populateFarmerCountChartData() {

		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		List<Object> farmerData = farmerService.listFarmerCountByBranch();
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("Label", getLocaleProperty("donutChart.farmerBranchCount"));
		jsonObjects.add(jsonObject1);

		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("save", getLocaleProperty("save"));
		jsonObjects.add(jsonObject2);
		JSONObject jsonObject3 = new JSONObject();
		jsonObject3.put("refresh", getLocaleProperty("refresh"));
		jsonObjects.add(jsonObject3);

		farmerData.stream().forEach(obj -> {
			JSONObject jsonObject = new JSONObject();
			Object[] objArr = (Object[]) obj;
			jsonObject.put("count", objArr[0]);
			jsonObject.put("group", objArr[1] + "(" + objArr[0] + ")");
			jsonObjects.add(jsonObject);
		});

		printAjaxResponse(jsonObjects, "text/html");
	}

	
	public void populateTotalAcreByVillageChartData() {

		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		List<Object> farmerData = null;

		farmerData = farmerService.listTotalFarmAcreByVillage();
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("Label", getLocaleProperty("donutChart.totalLandAcreByVillage"));
		jsonObjects.add(jsonObject1);

		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("save", getLocaleProperty("save"));
		jsonObjects.add(jsonObject2);
		JSONObject jsonObject3 = new JSONObject();
		jsonObject3.put("refresh", getLocaleProperty("refresh"));
		jsonObjects.add(jsonObject3);

		farmerData.stream().forEach(obj -> {
			JSONObject jsonObject = new JSONObject();
			Object[] objArr = (Object[]) obj;
			jsonObject.put("count", objArr[0]);
			jsonObject.put("proposed", objArr[1]);
			jsonObject.put("group", objArr[2]);
			jsonObjects.add(jsonObject);
		});

		printAjaxResponse(jsonObjects, "text/html");
	}

	@SuppressWarnings("unchecked")
	public void populateTotalAcreChartData() {

		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		List<Object> farmerData = null;

		
		if (!getIsKpfBased().equals("1")) {
			farmerData = farmerService.listTotalFarmAcreByBranch();
			farmerData.stream().forEach(obj -> {
				JSONObject jsonObject = new JSONObject();
				Object[] objArr = (Object[]) obj;
				jsonObject.put("count", objArr[0]);
				jsonObject.put("proposed", objArr[1]);
				jsonObject.put("group", objArr[2]);
				jsonObjects.add(jsonObject);
			});
			JSONObject jsonObject1 = new JSONObject();
			if (!getCurrentTenantId().equalsIgnoreCase("fincocoa")) {
				jsonObject1.put("Label", getLocaleProperty("donutChart.totalLandAcre"));
			} else {
				jsonObject1.put("Label", getLocaleProperty("donutChart.totalLandHectare"));
			}
			jsonObjects.add(jsonObject1);

			JSONObject jsonObjectLabelProd = new JSONObject();
			// jsonObjectLabelProd.put("Label",
			// getLocaleProperty("donutChart.totalLandAcre"));
			if (!getCurrentTenantId().equalsIgnoreCase("fincocoa")) {
				jsonObjectLabelProd.put("Label", getLocaleProperty("donutChart.totalLandAcre"));
			} else {
				jsonObjectLabelProd.put("Label", getLocaleProperty("donutChart.totalLandHectare"));
			}
			jsonObjects.add(jsonObjectLabelProd);

			JSONObject jsonObjectProdLabel = new JSONObject();
			jsonObjectProdLabel.put("LabelProduction", getLocaleProperty("donutChart.totalLandProduction"));
			jsonObjects.add(jsonObjectProdLabel);

			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("save", getLocaleProperty("save"));
			jsonObjects.add(jsonObject2);
			JSONObject jsonObject3 = new JSONObject();
			jsonObject3.put("refresh", getLocaleProperty("refresh"));
			jsonObjects.add(jsonObject3);

			printAjaxResponse(jsonObjects, "text/html");
		} /*else {
			farmerData = farmerService.listTotalFarmAcreByFpo();

			farmerData.stream().forEach(obj -> {
				JSONObject jsonObject = new JSONObject();
				Object[] objArr = (Object[]) obj;
				jsonObject.put("landholding", objArr[0]);
				jsonObject.put("proposed", objArr[1]);
				jsonObject.put("group", objArr[2]);
				// jsonObject.put("group", objArr[2]+"("+objArr[0]+")");
				jsonObjects.add(jsonObject);
			});
			JSONObject jsonObject1 = new JSONObject();
			
				jsonObject1.put("Label", getLocaleProperty("donutChart.totalLandHectare"));
			jsonObjects.add(jsonObject1);

			JSONObject jsonObjectLabelProd = new JSONObject();
			// jsonObjectLabelProd.put("Label",
			// getLocaleProperty("donutChart.totalLandAcre"));
			if (!getCurrentTenantId().equalsIgnoreCase("fincocoa")) {
				jsonObjectLabelProd.put("Label", getLocaleProperty("donutChart.totalLandAcre"));
			} else {
				jsonObjectLabelProd.put("Label", getLocaleProperty("donutChart.totalLandHectare"));
			}
			jsonObjects.add(jsonObjectLabelProd);

			JSONObject jsonObjectProdLabel = new JSONObject();
			jsonObjectProdLabel.put("LabelProduction", getLocaleProperty("donutChart.totalLandProduction"));
			jsonObjects.add(jsonObjectProdLabel);

			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("save", getLocaleProperty("save"));
			jsonObjects.add(jsonObject2);
			JSONObject jsonObject3 = new JSONObject();
			jsonObject3.put("refresh", getLocaleProperty("refresh"));
			jsonObjects.add(jsonObject3);

			printAjaxResponse(jsonObjects, "text/html");
		}*/

	}

	@SuppressWarnings("unchecked")
	public void populateDeviceChartData() {

		List<Device> deviceList = utilService.listDevices();
		Long onlineDeviceCount = deviceList.stream().filter(device -> !ObjectUtil.isEmpty(device.getAgent())
				&& device.getEnabled() && device.getAgent().getBodStatus() == 1).count();
		Long offlineDeviceCount = deviceList.stream().filter(device -> !ObjectUtil.isEmpty(device.getAgent())
				&& device.getEnabled() && device.getAgent().getBodStatus() == 0).count();
		Long inActiveDeviceCount = deviceList.stream()
				.filter(device -> ObjectUtil.isEmpty(device.getAgent()) && device.getEnabled()).count();
		Long disabledDeviceCount = deviceList.stream().filter(device -> !device.getEnabled()).count();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("onlineDeviceCount", (onlineDeviceCount > 0L) ? onlineDeviceCount : "-");
		jsonObject.put("offlineDeviceCount", (offlineDeviceCount > 0L) ? offlineDeviceCount : "-");
		jsonObject.put("inActiveDeviceCount", (inActiveDeviceCount > 0L) ? inActiveDeviceCount : "-");
		jsonObject.put("disabledDeviceCount", (disabledDeviceCount > 0L) ? disabledDeviceCount : "-");
		jsonObject.put("Label", getLocaleProperty("txt.devices"));
		jsonObject.put("inactiveLabel", getLocaleProperty("inactive") + "(" + inActiveDeviceCount + ")");
		jsonObject.put("activeLabel", getLocaleProperty("active") + "(" + inActiveDeviceCount + ")");
		jsonObject.put("disabledLabel", getLocaleProperty("disabled") + "(" + disabledDeviceCount + ")");
		jsonObject.put("onlineLabel", getLocaleProperty("online") + "(" + onlineDeviceCount + ")");
		jsonObject.put("offlineLabel", getLocaleProperty("offline") + "(" + offlineDeviceCount + ")");

		printAjaxResponse(jsonObject, "text/html");
	}
	
	public String getBranchIdValue() {

		return branchIdValue;
	}

	public void setBranchIdValue(String branchIdValue) {

		this.branchIdValue = branchIdValue;
	}

	public String getSelectedTaluk() {

		return selectedTaluk;
	}

	public void setSelectedTaluk(String selectedTaluk) {

		this.selectedTaluk = selectedTaluk;
	}

	public String getSelectedVillage() {

		return selectedVillage;
	}

	public void setSelectedVillage(String selectedVillage) {

		this.selectedVillage = selectedVillage;
	}

	public Integer getUserCount() {

		return userCount;
	}

	public void setUserCount(Integer userCount) {

		this.userCount = userCount;
	}

	

	public Map<String, String> getBranchList() {

		List<Object[]> branchMasters = getBranchesInfo();
		Map<String, String> branchList = new HashMap<String, String>();

		branchList = branchMasters.stream()
				.collect(Collectors.toMap(obj -> String.valueOf(obj[0]), obj -> String.valueOf(obj[1])));
		return branchList;
	}

	public List<Integer> getYearList() {
		List<Integer> yearList = new LinkedList<>();
		for (int i = 2016; i < DateUtil.getCurrentYear(); i++) {
			yearList.add(i);
		}
		return yearList;
	}

	

	

	/*public void populateVillageByCity() throws Exception {

		List<Village> villages = new ArrayList<Village>();

		if (!selectedTaluk.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedTaluk))
				&& !selectedTaluk.equalsIgnoreCase("0")) {
			villages = utilService.listVillagesByCityId(Long.valueOf(selectedTaluk));
		}
		JSONArray villageArr = new JSONArray();
		if (!ObjectUtil.isEmpty(villages)) {
			for (Village village : villages) {
				villageArr.add(getJSONObject(village.getId(), village.getName()));
			}
		}
		sendAjaxResponse(villageArr);

	}
*/
	@SuppressWarnings("unchecked")
	protected JSONObject getJSONObject(Object id, Object name) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("name", name);
		return jsonObject;
	}

	public String getSelectedBranch() {

		if (!StringUtil.isEmpty(getBranchId())) {
			selectedBranch = getBranchId();
		}

		return selectedBranch;
	}

	public void setSelectedBranch(String selectedBranch) {

		this.selectedBranch = selectedBranch;
	}

	

	public String getPreferenceValue(String key) {

		String value = utilService.findPrefernceByName(key);
		return value;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(String selectedYear) {
		this.selectedYear = selectedYear;
	}

	/*public Map<String, String> getTimelineYear() {

		Map<String, String> yearList = new LinkedHashMap<String, String>();
		for (int i = DateUtil.getCurrentYear() + 1; i >= 2010; i--) {
			yearList.put(String.valueOf(i), String.valueOf(i - 1) + " - " + String.valueOf(i));
		}

		return yearList;
	}

	public Map<String, String> getListOfState() {

		Map<String, String> stateList = new HashMap<String, String>();

		return stateList;
	}*/



	Double totQty = 0.0;
	double tot;


	public List<Object[]> getBranchesInfo() {
		List<Object[]> branchMasters = new ArrayList<>();
		Object[] arry = new Object[2];
		arry[0] = "";
		arry[1] = getLocaleProperty("select");

		branchMasters.addAll(utilService.listBranchMastersInfo());
		return branchMasters;
	}

	public Map<Integer, String> getStateList() {

		Map<Integer, String> stateMap = new LinkedHashMap<Integer, String>();

		List<State> stateList = utilService.listOfStatesByBranch(selectedBranch);
		for (State obj : stateList) {
			stateMap.put((int) obj.getId(), obj.getCode() + " - " + obj.getName());

		}
		return stateMap;

	}
	



	/*@SuppressWarnings("unchecked")
	public void populateStapleLenList() {

		JSONArray stapleArr = new JSONArray();
		List<Object[]> stapleList = utilService
				.findCatalogueCodeAndDisNameByType(Integer.valueOf(getText("stapleLength")));

		if (!ObjectUtil.isEmpty(stapleList)) {
			stapleList.forEach(obj -> {
				stapleArr.add(getJSONObject(obj[0].toString(), getText("stapleLenEng" + obj[2].toString())));
			});
		}
		sendAjaxResponse(stapleArr);

	}*/

	
	
	
	

	/*public Map<String, String> getYearTimeList() {
		Map<String, String> yearList = new HashMap();
		for (int i = 2016; i <= DateUtil.getCurrentYear() + 1; i++) {

			yearList.put(String.valueOf(i - 1), String.valueOf(i - 1) + " - " + String.valueOf(i));

		}
		return yearList;
	}

	public Map<String, String> getCultiYearList() {
		Map<String, String> yearList = new HashMap();
		for (int i = 2014; i <= DateUtil.getCurrentYear(); i++) {
			yearList.put(String.valueOf(i), String.valueOf(i));
		}
		return yearList;
	}*/

	

	@SuppressWarnings("unchecked")
	public Map<String, String> getCooperativeList() {
		Map<String, String> warehouseMap = new LinkedHashMap<>();

		warehouseMap = getFarmCatalougeMap(Integer.valueOf(getText("cooperativeType")));
		return warehouseMap;

	}

	/*public Map<Long, String> getSamithiList() {

		samithi = utilService.listSamithiesBasedOnType();
		Map<Long, String> samithiMap = new LinkedHashMap<>();
		//samithiMap = samithi.stream().collect(Collectors.toMap(Warehouse::getId, Warehouse::getName));
		return samithiMap;
	}*/

	public String getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(String selectedState) {
		this.selectedState = selectedState;
	}

	public String getSelectedGender() {
		return selectedGender;
	}

	public void setSelectedGender(String selectedGender) {
		this.selectedGender = selectedGender;
	}

	public Map<String, String> getGenderList() {
		genderList.put(Farmer.SEX_MALE, getText("MALE"));
		genderList.put(Farmer.SEX_FEMALE, getText("FEMALE"));
		return genderList;
	}

	public void setGenderList(Map<String, String> genderList) {

		this.genderList = genderList;
	}

	

	public Map<String, String> getSeasonList() {

		Map<String, String> seasonMap = new LinkedHashMap<String, String>();
		List<Object[]> seasonList = farmerService.listSeasonCodeAndName();
		for (Object[] obj : seasonList) {

			seasonMap.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
		}
		return seasonMap;

	}

	

	

	

	public String getCurrencyType() {
		String result = null;
		ESESystem preferences = utilService.findPrefernceById("1");
		if (!StringUtil.isEmpty(preferences)) {
			result = preferences.getPreferences().get(ESESystem.CURRENCY_TYPE);
		}

		return !StringUtil.isEmpty(result) ? result : ESESystem.CURRENCY_TYPE;

	}
	
	public String getAreaType() {
	
			String result = null;
			ESESystem preferences = utilService.findPrefernceById("1");
			if (!StringUtil.isEmpty(preferences) && preferences.getPreferences().get(ESESystem.AREA_TYPE) != null) {
				result = preferences.getPreferences().get(ESESystem.AREA_TYPE);
				result = result.contains("-") ? result.split("-")[1] : result;

			}
			return !StringUtil.isEmpty(result) ? result : ESESystem.AREA_TYPE;

	
	}


	String catalougeValues = "";

	public String getCatalouge(String code) {

		if (!StringUtil.isEmpty(code)) {
			// String[] codes = code.split(",");
			FarmCatalogue catalogue = getCatlogueValueByCode(code);
			if (!ObjectUtil.isEmpty(catalogue)) {
				catalougeValues = catalogue.getName();
			}
		}

		return !StringUtil.isEmpty(catalougeValues) ? StringUtil.removeLastComma(catalougeValues) : "";
	}

	public String getCurrentSeason() {

		String val = "";
		return val = utilService.findCurrentSeasonCodeByBranchId(getBranchId());
	}
	/*public void populateFinancialYearList() {
		JSONArray fYearArr = new JSONArray();
		int startYear=Integer.valueOf(getLocaleProperty("financialStartYear"));
		int currentMonth=DateUtil.getCurrentMonth();
		int currentYear=currentMonth<=3?DateUtil.getCurrentYear()-1:DateUtil.getCurrentYear();
		for(int i=startYear;i<=currentYear;i++){
			//System.out.println(i+"-"+(i+1));
			fYearArr.add(getJSONObject(String.valueOf(i), String.valueOf(i+"-"+(i+1))));
		}
		sendAjaxResponse(fYearArr);
	}*/

		
	public void populateSeasonList() {

		JSONArray seasonArr = new JSONArray();
		List<Object[]> seasonList = farmerService.listSeasonCodeAndName();
		if (!ObjectUtil.isEmpty(seasonList)) {
			seasonList.forEach(obj -> {
				seasonArr.add(getJSONObject(obj[0].toString(), obj[1].toString()));
			});
		}
		sendAjaxResponse(seasonArr);
	}

	public void populateBranchList() {
		JSONArray branchArr = new JSONArray();
		List<Object[]> branchList = getBranchesInfo();
		if (!ObjectUtil.isEmpty(branchList)) {
			branchList.forEach(obj -> {
				branchArr.add(getJSONObject(obj[0].toString(), obj[1].toString()));
			});
		}
		sendAjaxResponse(branchArr);
	}

	public void populateStateList() {
		List<State> stateList = utilService.listOfStatesByBranch(selectedBranch);
		JSONArray stateArr = new JSONArray();
		if (!ObjectUtil.isListEmpty(stateList)) {
			stateList.forEach(obj -> {
				/*
				 * if (StringUtil.isEmpty(getBranchId())) {
				 * stateArr.add(getJSONObject(obj.getName(), obj.getName())); }
				 * else {
				 */
				stateArr.add(getJSONObject((int) obj.getId(), obj.getName()));
				// }

			});
		}
		sendAjaxResponse(stateArr);
	}



	

	

	public void populateCooperativeList() {
		JSONArray farmCatalougeArr = new JSONArray();
		if (!getCurrentTenantId().equals("chetna")) {
			List<FarmCatalogue> farmCatalougeList = utilService
					.findFarmCatalougeByType(Integer.valueOf(getText("cooperativeType")));
			Class clazz = FarmCatalogue.class;
			Field field = null;
			try {
				field = clazz.getDeclaredField(FarmCatalogue.NAME);
			} catch (NoSuchFieldException e1) {

			} catch (SecurityException e1) {

			}
			if (!ObjectUtil.isListEmpty(farmCatalougeList)) {
				farmCatalougeList.forEach(obj -> {
					farmCatalougeArr.add(getJSONObject(obj.getCode(), obj.getName()));
				});
			}
		} 
		sendAjaxResponse(farmCatalougeArr);

	}

	public void populateVillageList() {

		if (!StringUtil.isEmpty(selectedState) && selectedState != null) {
			List<Village> villList = utilService.listVillageByBranch(selectedBranch, Long.valueOf(selectedState));
			JSONArray villArr = new JSONArray();
			if (!ObjectUtil.isListEmpty(villList)) {
				villList.forEach(obj -> {
					
					villArr.add(getJSONObject((int) obj.getId(), obj.getName()));
					

				});
			}
			sendAjaxResponse(villArr);
		}

	}

	

	public String getSelectedVariety() {
		return selectedVariety;
	}

	public void setSelectedVariety(String selectedVariety) {
		this.selectedVariety = selectedVariety;
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
	public void populateStatusList() {
		JSONArray warehouseArr = new JSONArray();
		Map<Integer, String> statusList = new LinkedHashMap<Integer, String>();
		statusList.put(Farmer.Status.ACTIVE.ordinal(), getText("status" + Farmer.Status.ACTIVE.ordinal()));
		statusList.put(Farmer.Status.INACTIVE.ordinal(), getText("status" + Farmer.Status.INACTIVE.ordinal()));
		
		statusList.forEach((k,v)->{
			warehouseArr.add(getJSONObject(String.valueOf( k), v));
		});
	
		sendAjaxResponse(warehouseArr);
	}
	public void estimatedAndActualYield() {

		List<Object[]> estimatedYield = farmerService.estimatedYield(getCodeForCropChart());
		List<Object[]> actualYield = farmerService.actualYield(getCodeForCropChart());
		Map<String, String> estimated_map = new HashMap<String, String>();
		Map<String, String> actual_map = new HashMap<String, String>();
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		List<Object> actualYield1 = new ArrayList<Object>();
		for (Object[] objects : actualYield) {
			String val = (String) actual_map.get((String) objects[1]);
			if (StringUtil.isEmpty(val)) {
				actual_map.put((String) objects[1], objects[0] + "," + objects[2]);
			} else {
				String existingVal[] = val.split(",");
				double actual = Double.parseDouble(String.valueOf(existingVal[1] == null ? "0" : existingVal[1]))
						+ Double.parseDouble(objects[2] == null ? "0" : String.valueOf(objects[2]));
				actual_map.put((String) objects[1], objects[0] + "," + actual);
			}
			
		}for (Map.Entry<String, String> entry : actual_map.entrySet()) {
			actualYield1.add(entry.getKey());
		}
		System.out.println(actualYield1);
		
		for (Object objects : actualYield1) {
			for (Object[] objects2 : estimatedYield) {
				if ((objects.toString()).equalsIgnoreCase((String) objects2[1])) {

					String val = (String) estimated_map.get((String) objects2[1]);
					if (StringUtil.isEmpty(val)) {
						estimated_map.put((String) objects2[1], objects2[0] + "," + objects2[2]);
					} else {
						String existingVal[] = val.split(",");
						double estimated = Double
								.parseDouble(String.valueOf(existingVal[1] == null ? "0" : existingVal[1]))
								+ Double.parseDouble(objects2[2] == null ? "0" : String.valueOf(objects2[2]));
						estimated_map.put((String) objects2[1], objects2[0] + "," + estimated);
					}
				}
			}
		}

		for (Map.Entry<String, String> entry : actual_map.entrySet()) {
			// System.out.println("Key = " + entry.getKey() +", Value = " +
			// entry.getValue());
			String estimatedYield_val = (String) estimated_map.get((String) entry.getKey());
if(estimatedYield_val!=null){
			String actualValue[] = (entry.getValue()).split(",");
			String estimatedValue[] = estimatedYield_val.split(",");
			//String estimatedValue[] = estimated_map.get(entry.getKey().trim()).split(",");
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("productName", actualValue[0]);
			jsonObj.put("productCode", entry.getKey());
			jsonObj.put("estimated", estimatedValue[1]);
			jsonObj.put("actual", actualValue[1]);
			jsonList.add(jsonObj);
}
		}

		/*
		 * for (Object[] objects : actualYield) { String actualYield_val =
		 * (String)actual_map.get((String) objects[1]); String
		 * estimatedYield_val = (String)estimated_map.get((String) objects[1]);
		 * 
		 * String actualValue[] = actualYield_val.split(","); String
		 * estimatedValue[] = estimatedYield_val.split(",");
		 * 
		 * if(((String) objects[0]).equalsIgnoreCase(actualValue[0])){
		 * JSONObject jsonObj = new JSONObject(); jsonObj.put("productName",
		 * objects[0]); jsonObj.put("productCode", objects[1]);
		 * jsonObj.put("estimated", estimatedValue[1]); jsonObj.put("actual",
		 * actualValue[1]); jsonList.add(jsonObj); } }
		 */

		printAjaxResponse(jsonList, "text/html");
	}
	@SuppressWarnings("unchecked")
	public void populateFarmersByLocation() {
		List<JSONObject> farmersByLocation = new ArrayList<JSONObject>();
		List<JSONObject> branch = new ArrayList<JSONObject>();
		List<JSONObject> gramPanchayat = new ArrayList<JSONObject>();
		List<JSONObject> village = new ArrayList<JSONObject>();

		if (StringUtil.isEmpty(getSelectedBranch())) {
			List<Object[]> farmersByBranch = farmerService.farmersByBranch();

			farmersByBranch.stream().forEach(obj -> {
				Object[] objArr = (Object[]) obj;
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("count", objArr[0]);
				jsonObj.put("branchName", objArr[1]);
				jsonObj.put("branchId", objArr[2]);
				branch.add(jsonObj);
			});
		}

		List<Object[]> farmersByCountry;
		if (getIsMultiBranch().equalsIgnoreCase("1") && getIsParentBranch().equalsIgnoreCase("1")) {
			farmersByCountry = farmerService.farmersByCountry(EMPTY);
		} else {
			farmersByCountry = farmerService.farmersByCountry(getSelectedBranch());
		}
		List<JSONObject> country = new ArrayList<JSONObject>();
		farmersByCountry.stream().forEach(obj -> {
			Object[] objArr = (Object[]) obj;
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("count", objArr[0]);
			jsonObj.put("countryName", objArr[1]);
			jsonObj.put("countryCode", objArr[2]);
			jsonObj.put("branchId", objArr[3]);
			country.add(jsonObj);
		});

		List<Object[]> farmersByState;
		if (getIsMultiBranch().equalsIgnoreCase("1") && getIsParentBranch().equalsIgnoreCase("1"))
			farmersByState = farmerService.farmersByState(EMPTY);
		else
			farmersByState = farmerService.farmersByState(getSelectedBranch());
		List<JSONObject> state = new ArrayList<JSONObject>();
		farmersByState.stream().forEach(obj -> {
			Object[] objArr = (Object[]) obj;
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("count", objArr[0]);
			jsonObj.put("stateName", objArr[1]);
			jsonObj.put("stateCode", objArr[2]);
			jsonObj.put("countryCode", objArr[3]);
			state.add(jsonObj);
		});

		List<Object[]> farmersByLocality;
		if (getIsMultiBranch().equalsIgnoreCase("1") && getIsParentBranch().equalsIgnoreCase("1"))
			farmersByLocality = farmerService.farmersByLocality(EMPTY);
		else
			farmersByLocality = farmerService.farmersByLocality(getSelectedBranch());
		List<JSONObject> locality = new ArrayList<JSONObject>();
		farmersByLocality.stream().forEach(obj -> {
			Object[] objArr = (Object[]) obj;
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("count", objArr[0]);
			jsonObj.put("localityName", objArr[1]);
			jsonObj.put("localityCode", objArr[2]);
			jsonObj.put("stateCode", objArr[3]);
			locality.add(jsonObj);
		});

		List<Object[]> farmersByMunicipality;
		if (getIsMultiBranch().equalsIgnoreCase("1") && getIsParentBranch().equalsIgnoreCase("1"))
			farmersByMunicipality = farmerService.farmersByMunicipality(EMPTY);
		else
			farmersByMunicipality = farmerService.farmersByMunicipality(getSelectedBranch());
		List<JSONObject> municipality = new ArrayList<JSONObject>();
		farmersByMunicipality.stream().forEach(obj -> {
			Object[] objArr = (Object[]) obj;
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("count", objArr[0]);
			jsonObj.put("municipalityName", objArr[1]);
			jsonObj.put("municipalityCode", objArr[2]);
			jsonObj.put("localityCode", objArr[3]);
			municipality.add(jsonObj);
		});

		if (getGramPanchayatEnable().equalsIgnoreCase("1")) {
			List<Object[]> farmersByGramPanchayat = farmerService.farmersByGramPanchayat(getSelectedBranch());

			farmersByGramPanchayat.stream().forEach(obj -> {
				Object[] objArr = (Object[]) obj;
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("count", objArr[0]);
				jsonObj.put("gramPanchayatName", objArr[1]);
				jsonObj.put("gramPanchayatCode", objArr[2]);
				jsonObj.put("municipalityCode", objArr[3]);
				gramPanchayat.add(jsonObj);
			});

			List<Object[]> farmersByVillage = farmerService.farmersByVillageWithGramPanchayat(getSelectedBranch());

			farmersByVillage.stream().forEach(obj -> {
				Object[] objArr = (Object[]) obj;
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("count", objArr[0]);
				jsonObj.put("villageName", objArr[1]);
				jsonObj.put("villageCode", objArr[2]);
				jsonObj.put("gramPanchayatCode", objArr[3]);
				village.add(jsonObj);
			});
		} else {
			List<Object[]> farmersByVillage;
			if (getIsMultiBranch().equalsIgnoreCase("1") && getIsParentBranch().equalsIgnoreCase("1"))
				farmersByVillage = farmerService.farmersByVillageWithOutGramPanchayat(EMPTY);
			else
				farmersByVillage = farmerService.farmersByVillageWithOutGramPanchayat(getSelectedBranch());
			farmersByVillage.stream().forEach(obj -> {
				Object[] objArr = (Object[]) obj;
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("count", objArr[0]);
				jsonObj.put("villageName", objArr[1]);
				jsonObj.put("villageCode", objArr[2]);
				jsonObj.put("municipalityCode", objArr[3]);
				village.add(jsonObj);
			});
		}

		List<Object[]> farmerDetailsByVillage;
		if (getIsMultiBranch().equalsIgnoreCase("1") && getIsParentBranch().equalsIgnoreCase("1"))
			farmerDetailsByVillage = farmerService.farmerDetailsByVillage(EMPTY);
		else
			farmerDetailsByVillage = farmerService.farmerDetailsByVillage(getSelectedBranch());
		List<JSONObject> farmerDetails = new ArrayList<JSONObject>();
		farmerDetailsByVillage.stream().forEach(obj -> {
			Object[] objArr = (Object[]) obj;
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("totalCount", objArr[0]);
			jsonObj.put("active", objArr[1] == null ? 0 : objArr[1]);
			jsonObj.put("inActive", objArr[2] == null ? 0 : objArr[2]);
			jsonObj.put("certified", objArr[3] == null ? 0 : objArr[3]);
			jsonObj.put("nonCertified", objArr[4] == null ? 0 : objArr[4]);
			jsonObj.put("villageCode", objArr[5] == null ? 0 : objArr[5]);
			farmerDetails.add(jsonObj);
		});

		JSONObject final_jsonObject = new JSONObject();
		if (StringUtil.isEmpty(getSelectedBranch())) {
			final_jsonObject.put("branch", branch);
		}
		final_jsonObject.put("country", country);
		final_jsonObject.put("state", state);
		final_jsonObject.put("locality", locality);
		final_jsonObject.put("municipality", municipality);
		if (getGramPanchayatEnable().equalsIgnoreCase("1")) {
			final_jsonObject.put("gramPanchayat", gramPanchayat);
		}
		final_jsonObject.put("village", village);
		final_jsonObject.put("farmerDetails", farmerDetails);
		final_jsonObject.put("getGramPanchayatEnable", getGramPanchayatEnable());

		farmersByLocation.add(final_jsonObject);

		printAjaxResponse(farmersByLocation, "text/html");
	}

	public void populateFarmerLocationCropChart() {
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		List<Object[]> cropChartData = farmerService.populateFarmerLocationCropChart(getCodeForCropChart());

		cropChartData.stream().forEach(obj -> {
			Object[] objArr = (Object[]) obj;
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("farmCount", objArr[0]);

			String area = "0.00";
			DecimalFormat df = new DecimalFormat("0.00");
			if (objArr[1] != null && !StringUtil.isEmpty(objArr[1].toString())) {
				area = (df.format(Double.valueOf(objArr[1].toString())));
			}

			jsonObj.put("Area", area);
			jsonObj.put("productName", objArr[2]);
			jsonObj.put("productCode", objArr[3]);
			jsonList.add(jsonObj);
		});
		printAjaxResponse(jsonList, "text/html");
	}
	
	public void getFarmDetailsAndProposedPlantingArea() {
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		List<Object[]> farmDetailsAndProposedPlantingArea = null;
		if (!getCurrentTenantId().equalsIgnoreCase(ESESystem.PRATIBHA_TENANT_ID)) {
			farmDetailsAndProposedPlantingArea = farmerService.getFarmDetailsAndProposedPlantingArea(getLocationLevel(),
					getSelectedBranch(), getGramPanchayatEnable());
		} else {
			farmDetailsAndProposedPlantingArea = farmerService.getFarmDetailsAndCultivationArea(getLocationLevel(),
					getSelectedBranch(), getGramPanchayatEnable());
		}

		farmDetailsAndProposedPlantingArea.stream().forEach(obj -> {
			Object[] objArr = (Object[]) obj;
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("FarmCount", objArr[0] == null ? 0 : objArr[0]);

			DecimalFormat df = new DecimalFormat("0.00");
			String totalArea = (df.format(Double.valueOf(objArr[1].toString())));

			jsonObj.put("Area", objArr[1] == null ? 0 : totalArea);
			jsonObj.put("locationName", objArr[2]);
			// jsonObj.put("locationCode", objArr[3]);
			jsonList.add(jsonObj);
		});
		printAjaxResponse(jsonList, "text/html");
	}	}
