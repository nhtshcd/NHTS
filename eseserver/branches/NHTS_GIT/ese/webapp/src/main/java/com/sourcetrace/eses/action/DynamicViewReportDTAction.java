package com.sourcetrace.eses.action;

import com.google.common.reflect.*;
import com.google.gson.*;
import com.google.zxing.*;
import com.google.zxing.qrcode.decoder.*;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.html.simpleparser.*;
import com.itextpdf.text.pdf.*;
import com.opencsv.*;
import com.sourcetrace.eses.*;
import com.sourcetrace.eses.dao.*;
import com.sourcetrace.eses.entity.*;
import com.sourcetrace.eses.filter.*;
import com.sourcetrace.eses.service.*;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.*;
import lombok.*;
import net.glxn.qrgen.*;
import org.apache.commons.lang3.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.json.simple.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import javax.script.*;
import javax.servlet.http.*;
import javax.xml.bind.*;
import javax.xml.parsers.*;
import java.awt.Color;
import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.text.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;
import java.util.regex.*;

@SuppressWarnings("serial")
@Component
@Scope(value = "prototype")
public class DynamicViewReportDTAction extends BaseReportAction {
	private String daterange;
	private String mainGridCols;
	private String mainGridColNames;
	private String filterList;
	private String gridIdentity;
	private Object fValue;
	private Object mValue;
	private String expression_result;
	private String createService;
	private DynamicReportConfig dynamicReportConfig;
	private Object filter;
	Map<Integer, Long> dynamic_report_config_detail_ID = new HashMap<Integer, Long>();
	private List<DynamicReportConfigFilter> reportConfigFilters = new LinkedList<>();
	private String reportConfigFiltersJs;
	Map entityMap = new HashMap<>();
	Map otherMap = new HashMap<>();
	Map<String, Map> mainMap = new HashMap<>();
	private String footerSumCols;
	private String footerTotCol;
	private String id;
	private String pdfData;
	private String pdfFileName;
	private InputStream fileInputStream;
	private String defFilters;
	String defFilter = "";
	boolean hit = true;
	String tothead;
	@Autowired
	private IUtilService utilService;

	@Autowired
	private IFarmerService farmerService;
	private List<Object[]> agentList;
	private String fetchType;
	private DynamicReportConfig subDynamicReportConfig;
	private String subGridCols;
	private String subGridColNames;
	private List<Object[]> productInfoList;
	private Map<String, String> seasonsList;
	DecimalFormat df = new DecimalFormat("0.00");
	private static int created = 0;

	private String villagecode;
	private String gpcode;
	private String citycode;
	private String localitycode;
	private String statecode;
	private String batchid;
	private String SORTING_QUERY = "FROM Sorting w WHERE w.farmCrops.id=? Order By createdDate Desc";
	private String txnId;
	public DynamicViewReportDTAction() {
		System.out.println("*************** Session Bean Created " + (++created) + " Times ***************");
	}

	public String getSubGridColNames() {
		return subGridColNames;
	}

	public void setSubGridColNames(String subGridColNames) {
		this.subGridColNames = subGridColNames;
	}

	public String list() {

		if (request != null && request.getSession() != null) {
			request.getSession().setAttribute(ISecurityFilter.REPORT, dynamicReportConfig.getReport());
		}

		Calendar currentDate = Calendar.getInstance();
		Calendar cal = (Calendar) currentDate.clone();
		cal.set(Calendar.MONTH, currentDate.get(Calendar.MONTH) - 1);
		DateFormat df = new SimpleDateFormat(DateUtil.DATABASE_DATE_FORMAT);
		super.startDate = df.format(cal.getTime());
		super.endDate = df.format(currentDate.getTime());
		setDaterange(super.startDate + " - " + super.endDate);
		request.setAttribute(HEADING, getText(LIST));
		ESESystem preferences = utilService.findPrefernceById("1");

		/*
		 * if (getCurrentTenantId().equalsIgnoreCase(ESESystem.KPF_TENANT_ID))
		 */
		if (!StringUtil.isEmpty(id)) {
			formMainGridCols();
			setFetchType(String.valueOf(dynamicReportConfig.getFetchType()));
			if (dynamicReportConfig.getDetailMethod() != null) {
				if (dynamicReportConfig.getDetailMethod().contains("$$")) {
					String ent = dynamicReportConfig.getDetailMethod().split("\\$\\$")[1].toString();
					boolean ans = utilService.isEntitlavailoableforuser(getUsername(), ent);
					if (!ans) {
						dynamicReportConfig.setDetailMethod("");
					} else {
						dynamicReportConfig
								.setDetailMethod(dynamicReportConfig.getDetailMethod().split("\\$\\$")[0].toString());
					}

				}
				setCreateService(dynamicReportConfig.getDetailMethod());
			}

			if (!ObjectUtil.isEmpty(dynamicReportConfig)
					&& !ObjectUtil.isListEmpty(dynamicReportConfig.getDynmaicReportConfigFilters())) {
				setFilterSize("");
				Gson gs = new Gson();
				JSONObject filters = new JSONObject();
				dynamicReportConfig.getDynmaicReportConfigFilters().stream()
						.filter(reportConfigFilter -> reportConfigFilter.getDetailId() != null
								&& !StringUtil.isEmpty(reportConfigFilter.getDetailId()))
						.forEach(reportConfigFilter -> {
							if (reportConfigFilter.getMethod() != null
									&& !StringUtil.isEmpty(reportConfigFilter.getMethod())
									&& reportConfigFilter.getType() != null && reportConfigFilter.getType() == 3) {
								Map<String, String> optionMap = (Map<String, String>) getQueryForFilters(
										reportConfigFilter.getMethod(), null);
								reportConfigFilter.setOptions(optionMap);
							} else if (reportConfigFilter.getMethod() != null
									&& !StringUtil.isEmpty(reportConfigFilter.getMethod())
									&& reportConfigFilter.getType() != null && reportConfigFilter.getType() == 5) {
								Map<String, String> optionMap = (Map<String, String>) getQueryForFilters(
										reportConfigFilter.getMethod(), null);
								reportConfigFilter.setOptions(optionMap);
							} else if (reportConfigFilter.getMethod() != null
									&& !StringUtil.isEmpty(reportConfigFilter.getMethod())
									&& reportConfigFilter.getType() != null && reportConfigFilter.getType() == 8) {
								Map<String, String> optionMap = (Map<String, String>) getQueryWithExpression(
										reportConfigFilter.getMethod());
								reportConfigFilter.setOptions(optionMap);
							}
							setFilterSize(getFilterSize() + "," + reportConfigFilter.getField());
							reportConfigFilters.add(reportConfigFilter);
							if (reportConfigFilter.getDetailId() != null
									&& !ObjectUtil.isEmpty(reportConfigFilter.getDetailId())) {
								JSONObject jss = new JSONObject();
								jss.put("options", reportConfigFilter.getOptions());
								jss.put("type", reportConfigFilter.getType());
								jss.put("dtype", reportConfigFilter.getDetailId().getDataType());
								jss.put("isDate", reportConfigFilter.getIsDateFilter());
								jss.put("field", reportConfigFilter.getField());
								filters.put(reportConfigFilter.getDetailId().getId(), jss);
							}

						});
				if (filters != null && !filters.isEmpty()) {
					setReportConfigFiltersJs(gs.toJson(filters));
					setFilterSize(getFilterSize().trim().substring(1));
				}

			}
			
			if (getLoggedInDealer() > 0) {
				dynamicReportConfig.setCsvFile(dynamicReportConfig.getCsvFile());
			}else{
				dynamicReportConfig.setCsvFile(null);
			}

		}
		return LIST;
	}

	int i = 0;

	private void formMainGridCols() {
		dynamicReportConfig = utilService.findReportById(id);
		if (!ObjectUtil.isEmpty(dynamicReportConfig)) {
			mainGridCols = "";
			mainGridColNames = "";
			footerSumCols = "";
			footerTotCol = "";
			dynamicReportConfig.getDynmaicReportConfigDetails()
					.remove(dynamicReportConfig.getDynmaicReportConfigDetails().iterator().next());
			if (!StringUtil.isEmpty(getBranchId())) {
				dynamicReportConfig.getDynmaicReportConfigDetails()
						.remove(dynamicReportConfig.getDynmaicReportConfigDetails().iterator().next());

			} else if (request.getSession() != null) {
				dynamicReportConfig.getDynmaicReportConfigDetails()
						.remove(dynamicReportConfig.getDynmaicReportConfigDetails().iterator().next());
			}

			dynamicReportConfig.getDynmaicReportConfigDetails().stream()
					.filter(config -> config.getIsGridAvailability())
					.sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder()))
					.forEach(dynamicReportConfigDetail -> {
						dynamicReportConfigDetail.setDataType(dynamicReportConfigDetail.getDataType() != null
								? dynamicReportConfigDetail.getDataType() : "");
						if((getDealerId()==null || StringUtil.isEmpty(getDealerId())) &&  dynamicReportConfigDetail.getAlignment()!=null && dynamicReportConfigDetail.getAlignment().equalsIgnoreCase("dealer")  ){
							dynamicReportConfigDetail.setAlignment(null);
							
						}
						if (dynamicReportConfigDetail.getLabelName().contains("@")) {
							String label = dynamicReportConfigDetail.getLabelName();
							mainGridCols += getLocaleProperty(label) + " (" + getCurrencyType() + ")" + "#"
									+ dynamicReportConfigDetail.getWidth() + "#"
									+ dynamicReportConfigDetail.getAlignment() + "#"
									+ dynamicReportConfigDetail.getLabelName() + "#" + dynamicReportConfigDetail.getId()
									+ "#" + dynamicReportConfigDetail.getAcessType() + "#"
									+ dynamicReportConfigDetail.getField() + "~"
									+ dynamicReportConfigDetail.getDataType() + "%";
							mainGridColNames += dynamicReportConfigDetail.getField() + " (" + getCurrencyType() + ")"
									+ "#" + dynamicReportConfigDetail.getWidth() + "#"
									+ dynamicReportConfigDetail.getAlignment() + "%";
						} else {
							mainGridCols += getLocaleProperty(dynamicReportConfigDetail.getLabelName()) + "#"
									+ dynamicReportConfigDetail.getWidth() + "#"
									+ dynamicReportConfigDetail.getAlignment() + "#"
									+ dynamicReportConfigDetail.getLabelName() + "#" + dynamicReportConfigDetail.getId()
									+ "#" + dynamicReportConfigDetail.getAcessType() + "#"
									+ dynamicReportConfigDetail.getField() + "~"
									+ dynamicReportConfigDetail.getDataType() + "%";
							mainGridColNames += dynamicReportConfigDetail.getField() + "#"
									+ dynamicReportConfigDetail.getWidth() + "#"
									+ dynamicReportConfigDetail.getAlignment() + "%";
						}

					});
			i = 0;
			dynamicReportConfig.getDynmaicReportConfigDetails().stream()
					.sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder())).forEach(dy -> {
						if (dy.getIsGridAvailability() && dy.getIsFooterSum() != null
								&& (dy.getIsFooterSum().equals("1") || dy.getIsFooterSum().equals("3"))) {
							footerSumCols += i + "#";

						}
						i++;
					});

			if (!footerSumCols.isEmpty())
				footerSumCols = StringUtil.removeLastChar(footerSumCols.trim(), '#');

			dynamicReportConfig.getDynmaicReportConfigDetails().stream().filter(
					f -> f.getIsGridAvailability() && f.getIsFooterSum() != null && f.getIsFooterSum().equals("2"))
					.forEach(dy -> {
						footerTotCol = dy.getLabelName();
					});
		}
		if (dynamicReportConfig.getSubGrid() != null && dynamicReportConfig.getSubGrid().size() > 0) {
			subDynamicReportConfig = dynamicReportConfig.getSubGrid().iterator().next();

			if (!ObjectUtil.isEmpty(subDynamicReportConfig)) {
				subGridCols = "";
				subDynamicReportConfig.getDynmaicReportConfigDetails().stream()
						.filter(config -> config.getIsGridAvailability())
						.sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder()))
						.forEach(dynamicReportConfigDetail -> {
							if (dynamicReportConfigDetail.getLabelName().contains("@")) {
								String label = dynamicReportConfigDetail.getLabelName();
								subGridCols += getLocaleProperty(label) + " (" + getCurrencyType() + ")" + "#"
										+ dynamicReportConfigDetail.getWidth() + "#"
										+ dynamicReportConfigDetail.getAlignment() + "%";
							} else {
								subGridCols += getLocaleProperty(dynamicReportConfigDetail.getLabelName()) + "#"
										+ dynamicReportConfigDetail.getWidth() + "#"
										+ dynamicReportConfigDetail.getAlignment() + "%";
								subGridColNames += dynamicReportConfigDetail.getField() + "#"
										+ dynamicReportConfigDetail.getWidth() + "#"
										+ dynamicReportConfigDetail.getAlignment() + "%";
							}
						});
			}
		}

	}

	public Map formValMap(DynamicReportConfig dynamicReportConfig, String branchIdd) {
		Map<Long, Object> valMap = new HashMap<>();
		dynamicReportConfig.getDynmaicReportConfigDetails().stream()
				.filter(u -> Arrays.asList(7, 22, 17, 18, 19, 16, 28, 23).contains(u.getAcessType())
						&& (u.getMethod() != null || u.getParamters() != null))
				.forEach(dynamicReportConfigDetail -> {
					if (Arrays.asList(28, 23).contains(dynamicReportConfigDetail.getAcessType())) {

						if (dynamicReportConfigDetail.getMethod() != null) {
							Map<String, String> optionMap = (Map<String, String>) getQueryForFilters(
									dynamicReportConfigDetail.getMethod(), new Object[] { branchIdd });

							if (dynamicReportConfigDetail.getMethod().contains(":lanParam")) {
								String langVal = dynamicReportConfigDetail.getMethod().replace(":lanParam",
										"'" + getLoggedInUserLanguage() + "'");
								dynamicReportConfigDetail.setMethod(langVal);
							}
							if (dynamicReportConfigDetail.getMethod().contains(":roleId")) {
								String langVal = dynamicReportConfigDetail.getMethod().replace(":roleId",
										"'" + getLoggedInRoleID() + "'");
								dynamicReportConfigDetail.setMethod(langVal);
							}
							valMap.put(dynamicReportConfigDetail.getId(), optionMap);
						}

					} else if (dynamicReportConfigDetail.getMethod() != null) {
						Map<String, String> optionMap = (Map<String, String>) getQueryForFilters(
								dynamicReportConfigDetail.getMethod(), new Object[] { branchIdd });
						if (dynamicReportConfigDetail.getMethod().contains(":lanParam")) {
							String langVal = dynamicReportConfigDetail.getMethod().replace(":lanParam",
									"'" + getLoggedInUserLanguage() + "'");
							dynamicReportConfigDetail.setMethod(langVal);
						}
						if (dynamicReportConfigDetail.getMethod().contains(":roleId")) {
							String langVal = dynamicReportConfigDetail.getMethod().replace(":roleId",
									"'" + getLoggedInRoleID() + "'");
							dynamicReportConfigDetail.setMethod(langVal);
						}
						valMap.put(dynamicReportConfigDetail.getId(), optionMap);
					}

				});
		return valMap;
	}

	String footersum = "";

	@SuppressWarnings("unchecked")
	protected String sendJQGridJSONResponse(Map data, boolean isSibGrid) throws Exception {
		LinkedList js = new LinkedList();
		JSONObject gridData = new JSONObject();
		List list = (List) data.get(ROWS);
		Object objj = data.get(RECORDS);
		Map<Long, Map<String, String>> valMap = formValMap(dynamicReportConfig, getLoggedInUserLanguage());
		footersum = "";
		if (objj instanceof JSONObject) {
			JSONObject countart = (JSONObject) objj;
			totalRecords = (Integer) countart.get("count");
			if (countart.containsKey("footers")) {
				JSONObject footers = (JSONObject) countart.get("footers");
				if (!footers.isEmpty()) {
					footers.keySet().stream().forEach(u -> {
						footersum = footersum + getLocaleProperty(u.toString()) + " : " + footers.get(u).toString()
								+ "       ";
					});

					gridData.put("footersum", footersum);
				}
			}
		} else {
			totalRecords = (Integer) data.get(RECORDS);
		}
		JSONArray rows = new JSONArray();
		if (list != null) {
			branchIdValue = getBranchId();
			if (StringUtil.isEmpty(branchIdValue)) {
				buildBranchMap();
			}
			for (Object record : list) {

				if (getGridIdentity().equalsIgnoreCase(IReportDAO.SUB_GRID)) {
					JSONArray newjS = new JSONArray();
					newjS.addAll((JSONArray) toJSON(record, valMap).get("cell"));
					js.add(newjS);
				} else {
					initializeMap(record);
					js.add(toJSON(record, valMap));
				}

			}

		}
		Gson gson = new GsonBuilder().create();
		JsonArray myCustomArray = gson.toJsonTree(js).getAsJsonArray();
		gridData.put("data", js);
		gridData.put("recordsTotal", totalRecords);
		gridData.put("recordsFiltered", totalRecords);
		gridData.put("draw", request.getParameter("draw"));

		PrintWriter out = response.getWriter();
		out.println(gridData.toString());

		return null;
	}

	@SuppressWarnings("unused")
	private Object getMethodValue(String methodName, Object param) {
		Object field = null;
		try {
			@SuppressWarnings("rawtypes")
			Class cls = this.getClass();

			if (param != null) {
				if (param instanceof Object[]) {
					Method setNameMethod = this.getClass().getMethod(methodName, Object[].class);
					field = setNameMethod.invoke(this, param);
				} else {
					Method setNameMethod = this.getClass().getMethod(methodName, String.class);
					field = setNameMethod.invoke(this, param);
				}
			} else {
				Method setNameMethod = this.getClass().getMethod(methodName);
				field = setNameMethod.invoke(this);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return field;
	}

	public String detail() throws Exception {
		

		Map data = null;
		if (!ObjectUtil.isEmpty(dynamicReportConfig) && dynamicReportConfig.getFetchType() == 2L) {
			
			data = getExportData(false);
		} else if (!ObjectUtil.isEmpty(dynamicReportConfig) && dynamicReportConfig.getFetchType() == 3L) {

			data = readProjectionDataView(mainMap);
		} else {
			data = readData();
		}
		setGridIdentity(IReportDAO.MAIN_GRID);
		if (dynamicReportConfig.getSubGrid() != null && dynamicReportConfig.getSubGrid().size() > 0) {
			return sendJQGridJSONResponse(data, true);
		} else {
			return sendJQGridJSONResponse(data, false);
		}
	}

	public String subGridDetail() throws Exception {

		/*
		 * if (getCurrentTenantId().equalsIgnoreCase(ESESystem.KPF_TENANT_ID))
		 */
		Map data = null;
		otherMap.put(DynamicReportConfig.DYNAMIC_CONFIG_DETAIL, subDynamicReportConfig.getDynmaicReportConfigDetails());
		otherMap.put(DynamicReportConfig.ENTITY, subDynamicReportConfig.getEntityName().split("~")[0].trim());
		otherMap.put(DynamicReportConfig.ALIAS, subDynamicReportConfig.getAlias());

		// mainMap.put(DynamicReportConfig.FILTER_FIELDS, filtersList);
		if (!ObjectUtil.isEmpty(subDynamicReportConfig) && subDynamicReportConfig.getFetchType() == 2L) {
			Map<String, String> filtersList = new HashMap<>();
			filtersList.put(subDynamicReportConfig.getEntityName().split("~")[1].trim(),
					"7~" + Long.valueOf(getId()) + "");
			mainMap.put(DynamicReportConfig.FILTER_FIELDS, filtersList);
			mainMap.put(DynamicReportConfig.OTHER_FIELD, otherMap);
			data = readProjectionDataStatic(mainMap);
		} else if (!ObjectUtil.isEmpty(subDynamicReportConfig) && subDynamicReportConfig.getFetchType() == 3L) {
			Map<String, String> filtersList = new HashMap<>();
			filtersList.put(subDynamicReportConfig.getEntityName().split("~")[1].trim(),
					getId().contains(",") ? " in (" + getId() + ") " : " = " + getId());
			mainMap.put(DynamicReportConfig.FILTER_FIELDS, filtersList);
			mainMap.put(DynamicReportConfig.OTHER_FIELD, otherMap);

			data = readProjectionDataView(mainMap);
		} else {
			data = readData();
		}
		setGridIdentity(IReportDAO.SUB_GRID);
		return sendJQGridJSONResponse(data, false);

	}

	private int flag = 0;
	String imgs = "";

	private String getAnswer(DynamicReportConfigDetail dynamicReportConfigDetail, Object[] arr, AtomicInteger at,
			Map<Long, Object> valMap, String language, StringBuilder exportParams) {
		String ansVal = "";

		if (dynamicReportConfigDetail.getAcessType() == 1L) {
			fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));

			if (!ObjectUtil.isEmpty(dynamicReportConfigDetail.getExpression())
					&& !StringUtil.isEmpty(dynamicReportConfigDetail.getExpression())) {
				String expression = dynamicReportConfigDetail.getExpression();

				try {
					fValue = ReflectUtil.getObjectFieldValueByExpression(arr, expression,
							dynamic_report_config_detail_ID);
				} catch (ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (!ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue)) {
				ansVal = fValue.toString();

			}
		} else if (dynamicReportConfigDetail.getAcessType() == 2L) {
			fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));
			if (!ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue)) {
				if (dynamicReportConfigDetail.getMethod().contains("##lan##")) {
					String[] stra = dynamicReportConfigDetail.getMethod().split("##lan##");
					String method = stra[0].toString();

					mValue = getMethodValue(method, new Object[] { fValue.toString(), language });
				} else {
					mValue = getMethodValue(dynamicReportConfigDetail.getMethod(), fValue.toString());
				}

				if (!ObjectUtil.isEmpty(mValue) && !StringUtil.isEmpty(mValue)) {
					ansVal = mValue.toString();
				}
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 14L) {

			if (arr[at.get()] != null) {
				List<byte[]> img = getQueryForImagesJSON(dynamicReportConfigDetail.getMethod(),
						new Object[] { arr[at.get()], branchId });
				if (!ObjectUtil.isEmpty(img) && img.size() > 0) {
					img.stream().forEach(u -> {
						if (u instanceof byte[]) {
							if (!ObjectUtil.isEmpty(u) && !StringUtil.isEmpty(u)) {
								String image = (Base64Util.encoder((byte[]) u));
								imgs = imgs + ",~#" + image;
							}
						}
					});
					ansVal = "<button class=\"fa fa-picture-o\"\"aria-hidden=\"true\"\" onclick='event.preventDefault();popupImages(\""
							+ imgs + "\")'></button>";
				} else {

					// No Latlon
					ansVal = "<button class='fa fa-ban'></button>";
				}

			} else {
				at.get();
			}

		} else if (dynamicReportConfigDetail.getAcessType() == 15L) {
			fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));
			if (!ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue)) {
				ansVal = fValue.toString();
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 16L) {
			ansVal = (String) ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));

			if (dynamicReportConfigDetail.getMethod() != null) {
				if (valMap.containsKey(dynamicReportConfigDetail.getId())) {
					Map<String, String> valuess = (Map<String, String>) valMap.get(dynamicReportConfigDetail.getId());
					String ke = String.valueOf(arr[at.get()]);
					if (valuess.containsKey(ke)) {
						ansVal = valuess.get(ke);
					} else {
						ansVal = "";
					}
				}

			}

			if (!ObjectUtil.isEmpty(ansVal) && !StringUtil.isEmpty(ansVal)) {

				ansVal = "<button class=\"fa fa-picture-o\"\"aria-hidden=\"true\"\" onclick='event.preventDefault();popupDocUploads(\""
						+ ansVal + "\")'></button>";
			} else {

				// No Latlon
				ansVal = "<button class='fa fa-ban'></button>";
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 17L) {
			ansVal = (String) ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));

			if (dynamicReportConfigDetail.getMethod() != null) {
				if (valMap.containsKey(dynamicReportConfigDetail.getId())) {
					Map<String, String> valuess = (Map<String, String>) valMap.get(dynamicReportConfigDetail.getId());
					String ke = String.valueOf(arr[at.get()]);
					if (valuess.containsKey(ke)) {
						ansVal = valuess.get(ke);
					} else {
						ansVal = "";
					}
				}

			}

			if (!ObjectUtil.isEmpty(ansVal) && !StringUtil.isEmpty(ansVal)) {

				ansVal = "<button class=\"fa fa-play\"\"aria-hidden=\"true\"\" onclick='event.preventDefault();playVideo(\""
						+ ansVal
						+ "\")'>&nbsp; &nbsp;</button><button class=\"fa fa-download\"\"aria-hidden=\"true\"\" onclick='event.preventDefault();downloadFile(\""
						+ ansVal + "\")'></button>";
			} else {

				// No Latlon
				ansVal = "<button class='fa fa-ban'></button>";
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 18L) {
			ansVal = (String) ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));

			if (dynamicReportConfigDetail.getMethod() != null) {
				if (valMap.containsKey(dynamicReportConfigDetail.getId())) {
					Map<String, String> valuess = (Map<String, String>) valMap.get(dynamicReportConfigDetail.getId());
					String ke = String.valueOf(arr[at.get()]);
					if (valuess.containsKey(ke)) {
						ansVal = valuess.get(ke);
					} else {
						ansVal = "";
					}
				}

			}

			if (!ObjectUtil.isEmpty(ansVal) && !StringUtil.isEmpty(ansVal)) {

				ansVal = "<a class='fa fa-download' href='#' onclick='event.preventDefault();downloadFile(\"" + ansVal
						+ "\")' title='" + dynamicReportConfigDetail.getLabelName() + "'></a>";

			} else {

				// No Latlon
				ansVal = "<button class='fa fa-ban'></button>";
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 19L) {
			ansVal = (String) ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));

			if (dynamicReportConfigDetail.getMethod() != null) {
				if (valMap.containsKey(dynamicReportConfigDetail.getId())) {
					Map<String, String> valuess = (Map<String, String>) valMap.get(dynamicReportConfigDetail.getId());
					String ke = String.valueOf(arr[at.get()]);
					if (valuess.containsKey(ke)) {
						ansVal = valuess.get(ke);
					} else {
						ansVal = "";
					}
				}

			}

			if (!ObjectUtil.isEmpty(ansVal) && !StringUtil.isEmpty(ansVal)) {

				ansVal = "<button class=\"fa fa-play\"\"aria-hidden=\"true\"\" onclick='event.preventDefault();playAudio(\""
						+ ansVal
						+ "\")'>&nbsp; &nbsp;</button><button class=\"fa fa-download\"\"aria-hidden=\"true\"\" onclick='event.preventDefault();downloadFile(\""
						+ ansVal + "\")'></button>";

			} else {

				// No Latlon
				ansVal = "<button class='fa fa-ban'></button>";
			}
		}

		else if (dynamicReportConfigDetail.getAcessType() == 3L) {

			fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));

			if (!ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue)) {
				mValue = getMethodValue(dynamicReportConfigDetail.getMethod(), fValue.toString());
			}

			if (!ObjectUtil.isEmpty(dynamicReportConfigDetail.getExpression())
					&& !StringUtil.isEmpty(dynamicReportConfigDetail.getExpression())) {
				String expression = dynamicReportConfigDetail.getExpression();

				try {
					expression_result = ReflectUtil.getObjectFieldValueByExpression(arr, expression,
							dynamic_report_config_detail_ID);
				} catch (ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (!ObjectUtil.isEmpty(dynamicReportConfigDetail.getParamters())
					&& !StringUtil.isEmpty(dynamicReportConfigDetail.getParamters())) {
				String parameters = dynamicReportConfigDetail.getParamters();
				String[] parametersArray = parameters.split(",");
				List<String> fvalueByParameters = ReflectUtil.getObjectFieldValueByParameters(arr, parametersArray,
						dynamic_report_config_detail_ID);

				Object temp = getMethodValue(dynamicReportConfigDetail.getMethod(), fvalueByParameters);

			} else if (!ObjectUtil.isEmpty(expression_result) && !StringUtil.isEmpty(expression_result)
					&& !ObjectUtil.isEmpty(dynamicReportConfigDetail.getExpression())
					&& !StringUtil.isEmpty(dynamicReportConfigDetail.getExpression())) {
				ansVal = expression_result.toString();
			} else if (!ObjectUtil.isEmpty(mValue) && !StringUtil.isEmpty(mValue)) {
				ansVal = mValue.toString();

			}

		} else if (dynamicReportConfigDetail.getAcessType() == 4L) {
			fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));

			if (!ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue)) {
				ansVal = "<button class='faMap' title='" + getText("farm.map.available.title")
						+ "' onclick='event.preventDefault();showFarmMap(\"" + fValue + "\")'></button>";
			} else {
				// No Latlon
				ansVal = "<button class='no-latLonIcn' title='" + getText("farm.map.unavailable.title") + "'></button>";
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 5L) {
			fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));

			if (!ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue)) {
				String[] a = String.valueOf(fValue).split(",");
				ansVal = "<button class='faMap' title='" + getText("farm.map.available.title")
						+ "' onclick='event.preventDefault();showMap(\"" + a[0] + "\",\"" + a[1] + "\")'></button>";
			} else {
				// No Latlon
				ansVal = "<button class='no-latLonIcn' title='" + getText("farm.map.unavailable.title") + "'></button>";
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 6L) {
			fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));

			if (!ObjectUtil.isEmpty(fValue) && fValue != null && fValue instanceof byte[]
					&& !StringUtil.isEmpty(fValue)) {
				if (((byte[]) fValue).length > 0) {
					String image = (Base64Util.encoder((byte[]) fValue));
					ansVal = "<button class=\"fa fa-picture-o\"\"aria-hidden=\"true\"\" onclick='event.preventDefault();popupWindow(\""
							+ image + "\")'></button>";
				} else {
					ansVal = "<button class='fa fa-ban'></button>";
				}
			} else {
				// No Latlon
				ansVal = "<button class='fa fa-ban'></button>";
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 20L) {
			fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));

			if (!ObjectUtil.isEmpty(fValue) && fValue != null && fValue instanceof byte[]
					&& !StringUtil.isEmpty(fValue)) {
				if (((byte[]) fValue).length > 0) {
					String image = (Base64Util.encoder((byte[]) fValue));
					ansVal = "<button class=\"fa file-sign\"\"aria-hidden=\"true\"\" onclick='event.preventDefault();popupWindow(\""
							+ image + "\")'></button>";
				} else {
					ansVal = "<button class='fa-ban'></button>";
				}
			} else {
				// No Latlon
				ansVal = "<button class='fa-ban'></button>";
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 7L) {
			if (arr[at.get()] != null) {
				if (valMap.containsKey(dynamicReportConfigDetail.getId())) {
					Map<String, String> valuess = (Map<String, String>) valMap.get(dynamicReportConfigDetail.getId());
					String ke = String.valueOf(arr[at.get()]);
					if (valuess.containsKey(ke)) {
						ansVal = valuess.get(ke);
					}
				}
				/*
				 * fValue =
				 * getQueryForFiltersJSON(dynamicReportConfigDetail.getMethod(),
				 * new Object[] { arr[at.get()], branchId }); if
				 * (!ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue))
				 * { ansVal = fValue.toString();
				 * 
				 * }
				 */
			}
		}

		else if (dynamicReportConfigDetail.getAcessType() == 8L) {
			if (arr[at.get()] != null) {
				fValue = convertToJson(dynamicReportConfigDetail.getMethod(), arr[at.get()]);
				if (!ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue)) {
					ansVal = fValue.toString();
				}
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 9L) {
			if (arr[at.get()] != null) {
				String par = (getLocaleProperty(dynamicReportConfigDetail.getParamters().split("~")[0].trim()));
				/*
				 * mValue = fValue = convertToJson(
				 * dynamicReportConfigDetail.getMethod() , arr[at.get()]);
				 */
				String title = par.split("#")[1].trim();
				String param = par.split("#")[0].trim();
				mValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));
				String par1 = getLocaleProperty(dynamicReportConfigDetail.getParamters().split("~")[1]);
				ScriptEngineManager mgr = new ScriptEngineManager();
				ScriptEngine engine = mgr.getEngineByName("JavaScript");

				List<String> fieldLiust = new ArrayList<>();
				Matcher p = Pattern.compile("\\{(.*?)\\}").matcher(par1);

				while (p.find())
					fieldLiust.add(p.group(1));

				if (!fieldLiust.isEmpty()) {
					par1 = par1.replaceAll("branchId", branchId);
					par1 = par1.replaceAll("\\{", "");
					par1 = par1.replaceAll("\\}", "");
					String parry = par1;
					try {
						par1 = (String) engine.eval(par1);
					} catch (Exception e) {
						par1 = parry;
						// TODO: handle exception
					}
				}

				fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(param)) + "~" + par1 + "~"
						+ dynamicReportConfigDetail.getMethod() + "~" + title;
				// System.out.println("fValue:
				// "+fValue);
				// if (!ObjectUtil.isEmpty(fValue) &&
				// !StringUtil.isEmpty(fValue)) {
				/*
				 * rows.add(mValue
				 * +"<button class=\"fa fa-pencil-square-o\"\"aria-hidden=\"true\"\" onclick=\"detailPopup('"
				 * +fValue + "')\"></button>");
				 */
				if (fValue != null && !StringUtil.isEmpty(fValue)) {
					/*
					 * rows. add("<a href onclick=\"detailPopup('" +fValue +
					 * "');return false\"> "+mValue+ "</a>");
					 */
					ansVal = "<button class='fa fa-info' aria-hidden='true' onclick='event.preventDefault();detailPopup(\""
							+ fValue + "\")'></button>";

				} else {
					ansVal = "<button class='fa fa-ban'></button>";

				}
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 10L) {

			fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));
			if (!ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue)) {

				ansVal = "<button class='fa fa-file-pdf-o' style='font-size:18px;color:red' title='"
						+ getText("dynExport") + "' onclick='event.preventDefault();exportRecPDF(\"" + exportParams
						+ "\")'></button>";

			} else {
				ansVal = "<button class='fa fa-ban'></button>";
			}

		} else if (dynamicReportConfigDetail.getAcessType() == 12L) {
			if (arr[at.get()] != null) {
				String par = dynamicReportConfigDetail.getParamters().split("~")[0].trim();

				String title = par.split("#")[1].trim();
				String param = par.split("#")[0].trim();
				mValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));
				String par1 = dynamicReportConfigDetail.getParamters().split("~")[1].trim();
				fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(param)) + "~" + par1 + "~"
						+ dynamicReportConfigDetail.getMethod() + "~" + title;
				// System.out.println("fValue:
				// "+fValue);

				if (!ObjectUtil.isEmpty(mValue) && !StringUtil.isEmpty(mValue)) {
					ansVal = "<a href onclick=\"detailPopupDt('" + fValue + "');return false\"target=\"_blank\"> "
							+ mValue + "</a>";

				} else {
					ansVal = "<button class='fa fa-ban'></button>";

				}
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 13L) {
			fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));

			if (!ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue)) {
				String image = (Base64Util.encoder((byte[]) fValue));
				ansVal = "<button class='btn btn-info btn-sm' onclick='event.preventDefault();downloadFile(\""
						+ arr[0].toString() + "\")'><span class='glyphicon glyphicon-cloud-download'></span></button>";
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 21L) {
			DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(Locale.FRANCE);
			DecimalFormatSymbols dfsEn = DecimalFormatSymbols.getInstance(Locale.ENGLISH);
			NumberFormat goodNumberFormat = new DecimalFormat("#,##0.000#", dfs);
			NumberFormat goodNumberFormatEn = new DecimalFormat("#,##0.000#", dfsEn);
			fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));
			if (!ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue)) {
				Double dd = Double.valueOf(fValue.toString());
				if (language != null && language.equals("fr")) {
					fValue = goodNumberFormat.format(dd);

				} else {
					fValue = goodNumberFormatEn.format(dd);
				}

				ansVal = fValue.toString();
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 22L) {
			DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(Locale.FRANCE);
			DecimalFormatSymbols dfsEn = DecimalFormatSymbols.getInstance(Locale.ENGLISH);
			NumberFormat goodNumberFormat = new DecimalFormat("#,##0.000#", dfs);
			NumberFormat goodNumberFormatEn = new DecimalFormat("#,##0.000#", dfsEn);
			if (arr[at.get()] != null) {
				if (valMap.containsKey(dynamicReportConfigDetail.getId())) {
					Map<String, String> valuess = (Map<String, String>) valMap.get(dynamicReportConfigDetail.getId());
					String ke = String.valueOf(arr[at.get()]);
					if (valuess.containsKey(ke)) {
						fValue = valuess.get(ke);
					}
				}
				if (fValue != "" && fValue != null && !ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue)) {

					// Double dd = Double.valueOf(fValue.toString());
					if (fValue instanceof Double) {
						Double dd = Double.parseDouble(fValue.toString().replaceAll(",", ""));
						if (language != null && language.equals("fr")) {
							fValue = goodNumberFormat.format(dd);

						} else {
							fValue = goodNumberFormatEn.format(dd);
						}
					}

					ansVal = fValue.toString();
				}
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 28L || dynamicReportConfigDetail.getAcessType() == 23L) {
			String ke = String.valueOf(arr[at.get()]);
			boolean ans = true;
			fValue = "true";
			String entt = "";
			if (dynamicReportConfigDetail.getMethod() != null) {

				if (valMap.containsKey(dynamicReportConfigDetail.getId())) {
					Map<String, String> valuess = (Map<String, String>) valMap.get(dynamicReportConfigDetail.getId());

					if (valuess.containsKey(ke)) {
						fValue = valuess.get(ke);
					}
				}
			}
			if (dynamicReportConfigDetail.getParamters() != null
					&& dynamicReportConfigDetail.getParamters().contains("##")) {
				ans = isEntitlavailoableforuser(dynamicReportConfigDetail.getParamters().split("##")[1]);
				entt = dynamicReportConfigDetail.getParamters().split("##")[1];

			}

			if (ans && fValue != null && fValue.toString().equals("true")) {
				if (dynamicReportConfigDetail.getAcessType() == 23L) {
					ansVal = "<a class='fa fa-trash' href='#' onclick='event.preventDefault();redirectToDelete(\""
							+ dynamicReportConfigDetail.getParamters().split("##")[0] + ke + "\")' title='"
							+ dynamicReportConfigDetail.getLabelName() + "'></a>";
				} else {
					ansVal = "<a class='fa fa-edit' href='#' onclick='event.preventDefault();redirectTo(\""
							+ dynamicReportConfigDetail.getParamters().split("##")[0] + ke + "\")' title='"
							+ dynamicReportConfigDetail.getLabelName() + "'></a>";
				}

			} else if (!ans) {
				ansVal = "No Access";
			} else if (!fValue.toString().equals("true") && dynamicReportConfigDetail.getAcessType() == 23L) {
				ansVal = getLocaleProperty("mssg.delete." + entt);
			} else if (!fValue.toString().equals("true")) {
				ansVal = getLocaleProperty("mssg.edit." + entt);
			}

		} else if (dynamicReportConfigDetail.getAcessType() == 29L) {

			if (arr[at.get()] != null) {
				if (!StringUtil.isEmpty(dynamicReportConfigDetail.getMethod())
						&& dynamicReportConfigDetail.getMethod().contains(":lanParam")) {
					String langVal = dynamicReportConfigDetail.getMethod().replace(":lanParam", "'" + language + "'");
					dynamicReportConfigDetail.setMethod(langVal);
				}
				Object ieId = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));
				if (!StringUtil.isEmpty(dynamicReportConfigDetail.getMethod()))
					fValue = getQueryForFiltersJSON(dynamicReportConfigDetail.getMethod(),
							new Object[] { arr[at.get()], branchId });

				String url = "";
				if (dynamicReportConfigDetail.getParamters().contains("##")) {
					url = dynamicReportConfigDetail.getParamters().split("##")[0];
				} else {
					url = dynamicReportConfigDetail.getParamters();
				}
				if (!ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue)) {
					if (dynamicReportConfig.getId() == 50 || dynamicReportConfig.getId() == 54
							|| dynamicReportConfig.getId() == 47) {
						if (fValue.toString().equalsIgnoreCase("0")) {
							String tooltip = "";
							if (!StringUtil.isEmpty(dynamicReportConfigDetail.getParamters())
									&& dynamicReportConfigDetail.getParamters().contains("##"))
								tooltip = dynamicReportConfigDetail.getParamters().split("##").length > 2
										&& !StringUtil.isEmpty(dynamicReportConfigDetail.getParamters().split("##")[2])
												? dynamicReportConfigDetail.getParamters().split("##")[2] : "";
							ansVal = "<button class='fa fa-edit' style='font-size:18px;color:red' title='"
									+ getLocaleProperty(tooltip) + "' onclick='event.preventDefault();redirectTo(\""
									+ url + ieId + "\")'></button>";
						} else {
							ansVal = "NA";
						}
					} else {
						String tooltip = "";
						if (!StringUtil.isEmpty(dynamicReportConfigDetail.getParamters())
								&& dynamicReportConfigDetail.getParamters().contains("##"))
							tooltip = dynamicReportConfigDetail.getParamters().split("##").length > 2
									&& !StringUtil.isEmpty(dynamicReportConfigDetail.getParamters().split("##")[2])
											? dynamicReportConfigDetail.getParamters().split("##")[2] : "";
						ansVal = "<button class='fa fa-edit' style='font-size:18px;color:red' title='"
								+ getLocaleProperty(tooltip) + "' onclick='event.preventDefault();redirectTo(\"" + url
								+ ieId + "\")'></button>";
					}

				} else {
					ansVal = "NA";
				}

			} else {
				ansVal = "NA";

			}
		} else if (dynamicReportConfigDetail.getAcessType() == 30L) {

			if (arr[at.get()] != null) {
				List<byte[]> img = getQueryForImagesJSON(dynamicReportConfigDetail.getMethod(),
						new Object[] { arr[at.get()], branchId });
				if (!ObjectUtil.isEmpty(img) && img.size() > 0) {
					img.stream().forEach(u -> {
						if (u instanceof byte[]) {
							if (!ObjectUtil.isEmpty(u) && !StringUtil.isEmpty(u)) {
								String image = (Base64Util.encoder((byte[]) u));
								imgs = imgs + ",~#" + image;
							}
						}
					});
					ansVal = "<button class=\"fa fa-picture-o\"\"aria-hidden=\"true\"\" onclick='event.preventDefault();popupImages(\""
							+ imgs + "\")'></button>";
				} else {

					// No Latlon
					ansVal = "<button class='fa fa-ban'></button>";
				}

			} else {
				at.get();
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 38L) {
			fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));

			if (!ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue)) {
				// System.out.println("fValue: " + fValue);
				ansVal = ("<button class='btn btn-info btn-sm' onclick='QRCodeDownloadProcess(\"" + fValue
						+ "\")'><i class='fa fa-print' aria-hidden='true'></i></button>");
			} else {
				// No Latlon
				ansVal = ("");
				runCount.getAndIncrement();
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 42L) {
			fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));

			if (!ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue)) {
				// System.out.println("fValue: " + fValue);
				ansVal = ("<button class='btn btn-info btn-sm' onclick='SortandPackQRCodeDownloadProcess(\"" + fValue
						+ "\")'><i class='fa fa-print' aria-hidden='true'></i></button>");
			} else {
				// No Latlon
				ansVal = ("");
				runCount.getAndIncrement();
			}
		}else if (dynamicReportConfigDetail.getAcessType() == 39L) {
			fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));

			if (!ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue)) {
				// System.out.println("fValue: " + fValue);
				ansVal = ("<a href=\"javascript:printReceipt(\\'" + fValue + "\\')\" class ='btn btn-default btnBorderRadius' "
						+ "onclick='printReceipt(\"" + fValue + "\",\""+dynamicReportConfigDetail.getMethod()+"\")'>"
						+ getText("printReceipt") + "</button>");

			} else {
				// No Latlon
				ansVal = ("");
				runCount.getAndIncrement();
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 40) {
			fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(at.get()));

			if (!ObjectUtil.isEmpty(fValue) && !StringUtil.isEmpty(fValue)) {
				// System.out.println("fValue: " + fValue);
				ansVal = ("<a href=\"javascript:printReceipt(\\'" + fValue
						+ "\\')\" class ='btn btn-default btnBorderRadius' onclick='printoutcomeReceipt(\"" + fValue
						+ "\")'>" + getText("printReceipt") + "</button>");

			} else {
				// No Latlon
				ansVal = ("");
				runCount.getAndIncrement();
			}
		} else if (dynamicReportConfigDetail.getAcessType() == 31L) {
			expression_result = "1";
			if (!ObjectUtil.isEmpty(dynamicReportConfigDetail.getExpression())
					&& !StringUtil.isEmpty(dynamicReportConfigDetail.getExpression())) {
				String expression = dynamicReportConfigDetail.getExpression();

				try {
					expression_result = ReflectUtil.getObjectFieldValueByExpression(arr, expression,
							dynamic_report_config_detail_ID);
				} catch (ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (expression_result.equalsIgnoreCase("1")) {
				ansVal = "<button class=\"fa fa-print\"\"aria-hidden=\"true\"\" onclick='event.preventDefault();printContract(\""
						+ StringEscapeUtils.escapeEcmaScript(dynamicReportConfigDetail.getMethod()) + ",#"+ dynamicReportConfigDetail.getParamters() + ",#" + arr[0].toString() + "\")'></button>";
			} else {
				ansVal = "No Access";
			}
		}
		
			if (dynamicReportConfigDetail.getDataType() != null
					&& dynamicReportConfigDetail.getDataType().equals("5")) {

				try {
					ansVal = "<a href=" + dynamicReportConfigDetail.getParamters() + arr[0].toString() + "&breadcrumb="
							+ URLEncoder.encode(dynamicReportConfigDetail.getDynamicReportConfig().getReport(),
									StandardCharsets.UTF_8.toString())
							+ "&redirectContent=" + getRedirectContent() + "&layoutType="
							+ request.getParameter("layoutType") + "&url=" + request.getParameter("url")
							+ " title='Go To Detail' target=_blank>" + ansVal + "</a>";
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (dynamicReportConfigDetail.getDataType() != null
					&& dynamicReportConfigDetail.getDataType().equals("6")) {

				ansVal = "<a class='fa fa-download' href=" + dynamicReportConfigDetail.getParamters()
						+ arr[0].toString() + " title='" + dynamicReportConfigDetail.getLabelName() + "'></a>";

			} else if (dynamicReportConfigDetail.getDataType() != null
					&& dynamicReportConfigDetail.getDataType().equals("2")) {
				ansVal = !StringUtil.isEmpty(ansVal.toString()) ? ansVal.toString() : "";
			}
		
		return ansVal;

	}

	@Getter
	@Setter
	String redirectContent;
	String branchId = "";
	AtomicInteger runCount = new AtomicInteger(1);
	String expr;

	@SuppressWarnings("unchecked")
	public JSONObject toJSON(Object obj, Map valMap) {
		JSONObject jsonObject = new JSONObject();
		AtomicInteger runCount = new AtomicInteger(1);
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");

		JSONArray js = new JSONArray();
		StringBuilder exportParams = new StringBuilder("");
		if (obj instanceof Object[]) {
			Object[] arr = (Object[]) obj;
			id = String.valueOf(arr[0]);
			if (getGridIdentity().equalsIgnoreCase(IReportDAO.MAIN_GRID)) {
				if (!StringUtil.isEmpty(dynamicReportConfig)) {

					dynamicReportConfig.getDynmaicReportConfigDetails().stream()
							.filter(config -> config.getIsGridAvailability())
							.sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder()))
							.forEach(dynamicReportConfigDetail -> {
								// flag=0;

								String ansVal = getAnswer(dynamicReportConfigDetail, arr, runCount, valMap,
										getLoggedInUserLanguage(), exportParams);
								runCount.incrementAndGet();
								exportParams.append("~" + ansVal);
								jsonObject.put(dynamicReportConfigDetail.getId(), ansVal);

							});
				}
			} else if (getGridIdentity().equalsIgnoreCase(IReportDAO.SUB_GRID)) {
				subDynamicReportConfig.getDynmaicReportConfigDetails().stream()
						.filter(config -> config.getIsGridAvailability())
						.sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder()))
						.forEach(dynamicReportConfigDetail -> {
							String ansVal = getAnswer(dynamicReportConfigDetail, arr, runCount, valMap,
									getLoggedInUserLanguage(), exportParams);
							runCount.incrementAndGet();
							exportParams.append("~" + ansVal);
							js.add(ansVal);

						});

				jsonObject.put("DT_RowId", id);

			}

		}
		return jsonObject;

	}

	public String getDaterange() {
		return daterange;
	}

	public void setDaterange(String daterange) {
		this.daterange = daterange;
	}

	public DynamicReportConfig getDynamicReportConfig() {
		return dynamicReportConfig;
	}

	public void setDynamicReportConfig(DynamicReportConfig dynamicReportConfig) {
		this.dynamicReportConfig = dynamicReportConfig;
	}

	public List<DynamicReportConfigFilter> getReportConfigFilters() {
		return reportConfigFilters;
	}

	public void setReportConfigFilters(List<DynamicReportConfigFilter> reportConfigFilters) {
		this.reportConfigFilters = reportConfigFilters;
	}

	public String getMainGridCols() {
		return mainGridCols;
	}

	public void setMainGridCols(String mainGridCols) {
		this.mainGridCols = mainGridCols;
	}

	public String getFilterList() {
		return filterList;
	}

	public void setFilterList(String filterList) {
		this.filterList = filterList;
	}

	public String getGridIdentity() {
		return gridIdentity;
	}

	public void setGridIdentity(String gridIdentity) {
		this.gridIdentity = gridIdentity;
	}

	public Map<String, String> getAgentList() {
		Map<String, String> agentMap = new LinkedHashMap<>();
		List<Object[]> agentList = utilService.listAgentIdName();
		agentList.stream().forEach(obj -> {
			Object[] objArr = (Object[]) obj;
			agentMap.put(String.valueOf(objArr[0]), String.valueOf(objArr[1]));
		});
		return agentMap;
	}

	public Map<String, String> getCategoryList() {
		Map<String, String> cateMap = new HashMap<String, String>();
		List<Object[]> cat = utilService.listOfProducts();
		cat.stream().forEach(a -> {
			cateMap.put(a[4].toString(), a[3].toString());
		});
		return cateMap;
	}

	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	public Object getFilter() {
		return filter;
	}

	public void setFilter(Object filter) {
		this.filter = filter;
	}

	public Map getEntityMap() {
		return entityMap;
	}

	public void setEntityMap(Map entityMap) {
		this.entityMap = entityMap;
	}

	public Map getOtherMap() {
		return otherMap;
	}

	public void setOtherMap(Map otherMap) {
		this.otherMap = otherMap;
	}

	public Map<String, Map> getMainMap() {
		return mainMap;
	}

	public void setMainMap(Map<String, Map> mainMap) {
		this.mainMap = mainMap;
	}

	@Override
	public void prepare() throws Exception {
		dynamicReportConfig = utilService.findReportById(request.getParameter("id"));
		String url = "";
		if (dynamicReportConfig != null) {
			if (dynamicReportConfig.getDetailMethod() != null
					&& !StringUtil.isEmpty(dynamicReportConfig.getDetailMethod())) {
				if (dynamicReportConfig.getReport().trim().equals("User")) {
					url = getLocaleProperty("preferences") + "~#," + dynamicReportConfig.getReport()
							+ "~dynamicViewReportDT_list.action?id=" + dynamicReportConfig.getId();
				} else if (dynamicReportConfig.getEntityName().trim().endsWith("Customer")
						|| dynamicReportConfig.getEntityName().trim().endsWith("Packhouse")) {
					url = getLocaleProperty("profile") + "~#," + dynamicReportConfig.getReport()
							+ "~dynamicViewReportDT_list.action?id=" + dynamicReportConfig.getId();
				} else if (dynamicReportConfig.getEntityName().trim().endsWith(".Agent")
						) {
					url = getLocaleProperty("admin") + "~#," + dynamicReportConfig.getReport()
							+ "~dynamicViewReportDT_list.action?id=" + dynamicReportConfig.getId();
				} else {
					url = getLocaleProperty("service") + "~#," + dynamicReportConfig.getReport()
							+ "~dynamicViewReportDT_list.action?id=" + dynamicReportConfig.getId();
				}
			} else {
				url = getLocaleProperty("report") + "~#," + dynamicReportConfig.getReport()
						+ "~dynamicViewReportDT_list.action?id=" + dynamicReportConfig.getId();
			}

		}
		request.setAttribute(BreadCrumb.BREADCRUMB, BreadCrumb.getBreadCrumb(url));
	}

	public String getFooterSumCols() {
		return footerSumCols;
	}

	public void setFooterSumCols(String footerSumCols) {
		this.footerSumCols = footerSumCols;
	}

	public String getMainGridColNames() {
		return mainGridColNames;
	}

	public void setMainGridColNames(String mainGridColNames) {
		this.mainGridColNames = mainGridColNames;
	}

	public String getFooterTotCol() {
		return footerTotCol;
	}

	public void setFooterTotCol(String footerTotCol) {
		this.footerTotCol = footerTotCol;
	}

	public Map<String, String> getSeasonsList() {
		if (seasonsList == null || seasonsList.isEmpty()) {
			seasonsList = new HashMap<>();
			List<Object[]> seasonListt = farmerService.listSeasonCodeAndName();

			for (Object[] obj : seasonListt) {
				seasonsList.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
			}
		}
		return seasonsList;

	}

	public String getAgentByProfile(String profileId) {
		String agent = getAgentsList().get(profileId);
		return agent;
	}

	public Map<String, String> getAgentsList() {
		Map<String, String> agentMap = new LinkedHashMap<String, String>();
		if (ObjectUtil.isListEmpty(agentList)) {
			agentList = utilService.listAgentIdName();
		}
		agentList.stream().forEach(obj -> {
			Object[] objArr = (Object[]) obj;
			agentMap.put(String.valueOf(objArr[0]), String.valueOf(objArr[1]));
		});
		return agentMap;
	}

	public String getSeasonByCode(String code) {
		return getSeasonsList().get(code);
	}

	public String getFarmerType(String id) {
		String type = getRegType().get(id);
		return type;
	}

	public Map<String, String> getRegType() {
		Map<String, String> regType = new LinkedHashMap<>();
		regType.put("0", getText("REG"));
		regType.put("1", getText("UNREG"));

		return regType;
	}

	public String getFetchType() {
		return fetchType;
	}

	public void setFetchType(String fetchType) {
		this.fetchType = fetchType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*
	 * public String getProcurementProductById(String id) { String product =
	 * getProcurementProductList().get(id); return product; }
	 */

	/*
	 * public String getProcurementProductUnitById(String id) { String product =
	 * getProcurementProductUnitList().get(id); return product; }
	 */

	public String getSubGridCols() {
		return subGridCols;
	}

	public void setSubGridCols(String subGridCols) {
		this.subGridCols = subGridCols;
	}

	public String processPDF() throws IOException, ParserConfigurationException, DocumentException {
		SimpleDateFormat fileNameDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Document document = new Document();
		String makeDir = FileUtil.storeXls(request.getSession().getId());
		String fileName = makeDir + pdfFileName + fileNameDateFormat.format(new Date());
		FileOutputStream fileOut = new FileOutputStream(fileName);
		PdfWriter.getInstance(document, fileOut);
		document.open();

		HTMLWorker htmlWorker = new HTMLWorker(document);
		htmlWorker.parse(new StringReader(pdfData));

		document.close();

		InputStream in = new FileInputStream(fileName);
		setFileInputStream(in);
		return "pdf";
	}

	public String getPdfData() {
		return pdfData;
	}

	public void setPdfData(String pdfData) {
		this.pdfData = pdfData;
	}

	public String getPdfFileName() {
		return pdfFileName;
	}

	public void setPdfFileName(String pdfFileName) {
		this.pdfFileName = pdfFileName;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public String getVillagecode() {
		return villagecode;
	}

	public void setVillagecode(String villagecode) {
		this.villagecode = villagecode;
	}

	public String getGpcode() {
		return gpcode;
	}

	public void setGpcode(String gpcode) {
		this.gpcode = gpcode;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getLocalitycode() {
		return localitycode;
	}

	public void setLocalitycode(String localitycode) {
		this.localitycode = localitycode;
	}

	public String getStatecode() {
		return statecode;
	}

	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}

	public String getDefFilters() {
		return defFilters;
	}

	public void setDefFilters(String defFilters) {
		this.defFilters = defFilters;
	}

	public String getDefFilter() {
		return defFilter;
	}

	public void setDefFilter(String defFilter) {
		this.defFilter = defFilter;
	}

	/*
	 * public String getDefaultFilter() {
	 * 
	 * dynamicReportConfig.getDynmaicReportConfigFilters().stream()
	 * .filter(reportConfigFilter ->
	 * !StringUtil.isEmpty(reportConfigFilter.getDefaultFilter()))
	 * .forEach(reportConfigFilter -> {
	 * 
	 * if (reportConfigFilter.getStatus() == 1) { defFilter = defFilter.concat(
	 * reportConfigFilter.getField() + ":" +
	 * reportConfigFilter.getDefaultFilter() + ":1,"); } else { defFilter =
	 * defFilter.concat( reportConfigFilter.getField() + ":" +
	 * reportConfigFilter.getDefaultFilter() + ":0,"); } });
	 * 
	 * if (hit) { defFilter = defFilter.substring(0, defFilter.length() - 1);
	 * setDefFilters(defFilter); hit = false; }
	 * 
	 * return defFilter; }
	 */

	public String getCreateService() {
		return createService;
	}

	public void setCreateService(String createService) {
		this.createService = createService;
	}

	public String getReportConfigFiltersJs() {
		return reportConfigFiltersJs;
	}

	public void setReportConfigFiltersJs(String reportConfigFiltersJs) {
		this.reportConfigFiltersJs = reportConfigFiltersJs;
	}

	public String downloadImageKnow() {

		try {
			// setImgId(imgId);
			if (!StringUtil.isEmpty(id) && StringUtil.isLong(id)) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public String getInspectorById(String id) {
		User grower;
		grower = utilService.findInspectorById(Long.valueOf(id));
		return grower.getUsername();
	}

	public void initializeMap(Object obj) {

		JSONObject jsonObject = new JSONObject();
		JSONArray rows = new JSONArray();
		AtomicInteger runCount = new AtomicInteger(0);

		if (obj instanceof Object[]) {
			Object[] arr = (Object[]) obj;

			dynamicReportConfig.getDynmaicReportConfigDetails().stream()
					.sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder()))
					.forEach(dynamicReportConfigDetail -> {
						dynamic_report_config_detail_ID.put(runCount.getAndIncrement(),
								dynamicReportConfigDetail.getId());
					});
		}
	}

	public String processQRBatchData() {
		try {
			String documentName = null;
			if (!StringUtil.isEmpty(batchid)) {
				documentName = getText("qrCode") + batchid;
				String url = request.getRequestURL().toString();
				URL aURL = new URL(url);
				String path = aURL.getPath();
				String fullPath[] = path.split("/", 0);
				String urll = aURL.getProtocol() + "://" + aURL.getAuthority() + "/" + fullPath[1];
				String tenant = getCurrentTenantId();
				String timestamp = DateUtil.getRevisionNoWithMillSec();
				String message = urll + "/traceabilityViewReport_list.action" + "?batchNo=" + batchid
						+ "&layoutType=publiclayout";

				ByteArrayOutputStream stream = QRCode.from(message).withErrorCorrection(ErrorCorrectionLevel.L)
						.withHint(EncodeHintType.MARGIN, 2).withSize(250, 250).stream();
				setFileInputStream(new ByteArrayInputStream(stream.toByteArray()));
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment;filename=" + documentName + "." + "png");

				response.getOutputStream().write(stream.toByteArray());
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}
	
	public String processSortPackQRBatchData(){
		try {
		String documentName = null;
		documentName = getText("qrCode") + txnId;
		String message =null;
		Sorting sorting = (Sorting) farmerService.findObjectById(
				"FROM Sorting so WHERE so.id=? AND so.status=?", new Object[] { Long.valueOf(txnId), 1 });
		if(sorting!=null){
			CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse cw WHERE cw.farmcrops.id=? and cw.qrCodeId=? and cw.coOperative.id is null",
					new Object[] {sorting.getFarmCrops().getId(),sorting.getQrCodeId()});
			
	     message =sorting.getFarmCrops().getFarm().getFarmer().getVillage().getCity().getLocality().getState().getCode()+"~"
		+sorting.getFarmCrops().getFarm().getFarmer().getVillage().getCity().getLocality().getState().getName()+"~"+sorting.getFarmCrops().getBlockId()+"~"
		+sorting.getFarmCrops().getBlockName()+"~"+sorting.getPlanting().getVariety().getCode()+"~"+sorting.getPlanting().getVariety().getName()+"~"
		+sorting.getPlanting().getGrade().getCode()+"~"+sorting.getPlanting().getGrade().getName()+"~"+sorting.getQtyNet()+"~"
		+sorting.getFarmCrops().getFarm().getFarmer().getFarmerId()+"~"+sorting.getFarmCrops().getFarm().getFarmer().getFirstName()+"~"
		+sorting.getFarmCrops().getFarm().getFarmCode()+"~"+sorting.getFarmCrops().getFarm().getFarmName()+"~"
		+cw.getStockType() +"~"+sorting.getFarmCrops().getExporter().getId()+"~"
		+DateUtil.convertDateToString(cw.getCreatedDate(), "yyyy-MM-dd")+"~"+sorting.getPlanting().getPlantingId()+"~"+sorting.getQrCodeId();
		}
		ByteArrayOutputStream stream = QRCode.from(message).withErrorCorrection(ErrorCorrectionLevel.L)
				.withHint(EncodeHintType.MARGIN, 2).withSize(250, 250).stream();
		setFileInputStream(new ByteArrayInputStream(stream.toByteArray()));
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + documentName + "." + "png");

		response.getOutputStream().write(stream.toByteArray());
		
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	private String receiptNumber;
	private String methodValuerp;
	private HashMap<String, Object> shipmentPrintMap;
	private HashMap<String, Object> sortingPrintMap;

	public String processPrintHTML() throws IOException, ParserConfigurationException, DocumentException {
		if (!StringUtil.isEmpty(receiptNumber)) {
			if(methodValuerp.equalsIgnoreCase("IncomingShipment")){
				initializePrintMap();
				PackhouseIncoming procurementMTNR = (PackhouseIncoming) farmerService.findObjectById(
						"FROM PackhouseIncoming pi WHERE pi.batchNo=? AND pi.status=?", new Object[] { receiptNumber, 1 });
				buildTransactionPrintMap(procurementMTNR);
			}
			else if(methodValuerp.equalsIgnoreCase("packingOperations")){
				initializePrintMapForPacking();
				Packing packing = (Packing) farmerService.findObjectById( "FROM Packing so WHERE so.batchNo=? AND so.status=?", new Object[] { String.valueOf(receiptNumber), 1 });
				buildTransactionPrintMapForPacking(packing);
			}
			else{
				initializePrintMapForSorting();
				Sorting sorting = (Sorting) farmerService.findObjectById(
						"FROM Sorting so WHERE so.id=? AND so.status=?", new Object[] { Long.valueOf(receiptNumber), 1 });
				buildTransactionPrintMapForSorting(sorting);
			}
		
		}
		return "html";
	}


private void initializePrintMapForPacking() {
	this.shipmentPrintMap = new HashMap<String, Object>();
	List<Map<String, Object>> productMapList = new ArrayList<Map<String, Object>>();
	Map<String, Object> totalMap = new LinkedHashMap<String, Object>();
	this.shipmentPrintMap.put("checkIncomingOrSorting", "pack2");
	this.shipmentPrintMap.put("recNo", "");
	this.shipmentPrintMap.put("date", "");
	this.shipmentPrintMap.put("agentName", "");
	this.shipmentPrintMap.put("packerName", "");
	this.shipmentPrintMap.put("product", "");
	this.shipmentPrintMap.put("productMapList", productMapList);
	this.shipmentPrintMap.put("totalInfo", totalMap);
	this.shipmentPrintMap.put("packingFarmer", "");
	this.shipmentPrintMap.put("packingFarm", "");
	this.shipmentPrintMap.put("packingBlockidandName", "");
	this.shipmentPrintMap.put("packingplanting", "");
	this.shipmentPrintMap.put("packingReceptbatchNo", "");
	this.shipmentPrintMap.put("packingCrop", "");
	this.shipmentPrintMap.put("packingVariety", "");
	this.shipmentPrintMap.put("packingincomingshipmentdate", "");
	this.shipmentPrintMap.put("packingPackedquantity", "");
	this.shipmentPrintMap.put("packingPrice", "");
	this.shipmentPrintMap.put("rejectWt", "");
	this.shipmentPrintMap.put("packingbestBeforeDate", "");
	this.shipmentPrintMap.put("packingCountryOfOrigin", "");
	}
	
private void buildTransactionPrintMapForPacking(Packing packing) throws MalformedURLException {
		
	List<Map<String, Object>> productMapList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> qrMapList = new ArrayList<Map<String, Object>>();
	if (!ObjectUtil.isEmpty(packing)) {
		long noOfBagsSum = 0l;
		double netWeightSum = 0d;
		double rejectedWTSum = 0d;
		DateFormat df = new SimpleDateFormat(getGeneralDateFormat());
		if (!ObjectUtil.isEmpty(packing.getPackHouse().getName())) {
			if (!StringUtil.isEmpty(packing.getPackHouse().getName())) {
				this.shipmentPrintMap.put("recNo", packing.getPackHouse().getName());
			}
			if (!ObjectUtil.isEmpty(packing.getPackingDate())) {
				this.shipmentPrintMap.put("date", df.format(packing.getPackingDate()));
			}
			if (!ObjectUtil.isEmpty(packing.getPackerName()) && !StringUtil.isEmpty(packing.getPackerName())) {
				this.shipmentPrintMap.put("packerName", packing.getPackerName());
			}
			if (!ObjectUtil.isEmpty(packing.getBatchNo()) && !StringUtil.isEmpty(packing.getBatchNo())) {
				this.shipmentPrintMap.put("agentName", packing.getBatchNo());
			}

		}
		this.shipmentPrintMap.put("checkIncomingOrSorting", "pack2");
		this.shipmentPrintMap.put("productMapList", productMapList);
		this.shipmentPrintMap.put("qrMapList", qrMapList);
		if (!ObjectUtil.isListEmpty(packing.getPackingDetails())) {
			for (PackingDetail packingDetail : packing.getPackingDetails()) {
				Map<String, Object> productMap = new LinkedHashMap<String, Object>();
				Map<String, Object> qrMap = new LinkedHashMap<String, Object>();
				String pacQr = null;
				productMap.put("packingFarmer", packingDetail.getBlockId().getFarm().getFarmer().getFarmerId() +" - "+packingDetail.getBlockId().getFarm().getFarmer().getFirstName());
				productMap.put("packingFarm", packingDetail.getBlockId().getFarm().getFarmCode()+" - "+packingDetail.getBlockId().getFarm().getFarmName());
				productMap.put("packingBlockidandName", packingDetail.getBlockId().getBlockId() +" - "+packingDetail.getBlockId().getBlockName());
				productMap.put("packingplanting", packingDetail.getPlanting().getPlantingId());
/*				productMap.put("packingFarmer", packingDetail.getBlockId().getFarm().getFarmer().getFirstName() +" - "+packingDetail.getBlockId().getFarm().getFarmer().getFarmerId());
				productMap.put("packingFarm", packingDetail.getBlockId().getFarm().getFarmName()+" - "+packingDetail.getBlockId().getFarm().getFarmCode());
				productMap.put("packingBlockidandName", packingDetail.getBlockId().getBlockId() +" - "+packingDetail.getBlockId().getBlockName());*/	
		    	productMap.put("packingReceptbatchNo", packingDetail.getBatchNo());
				productMap.put("packingCrop", packingDetail.getPlanting().getVariety().getName());
				productMap.put("packingVariety", packingDetail.getPlanting().getGrade().getName());
				CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse  where batchNo=?", new Object[] { String.valueOf(packingDetail.getBatchNo()) });
				if (cw != null) {
					productMap.put("packingincomingshipmentdate", getGeneralDateFormat(String.valueOf(cw.getCreatedDate())));
				}
				productMap.put("packingPackedquantity", CurrencyUtil.getDecimalFormat(packingDetail.getQuantity(), "##.00"));
				productMap.put("packingPrice", CurrencyUtil.getDecimalFormat(packingDetail.getPrice(), "##.00"));
				productMap.put("rejectWt", CurrencyUtil.getDecimalFormat(packingDetail.getRejectWt(), "##.00"));
				productMap.put("packingbestBeforeDate", df.format(packingDetail.getBestBefore()));
				productMap.put("packingCountryOfOrigin", packingDetail.getBlockId().getFarm().getVillage()!=null ? packingDetail.getBlockId().getFarm().getVillage().getCity().getLocality().getState().getName() :"");
				
				netWeightSum += packingDetail.getQuantity();
				rejectedWTSum += packingDetail.getRejectWt();
				pacQr = gendrateQRcodeForPacking(packingDetail);
				qrMap.put("QrCode",pacQr);
				qrMapList.add(qrMap);
				productMapList.add(productMap);
			}
		}
		Map<String, Object> totalMap = new LinkedHashMap<String, Object>();
		totalMap.put("netWeight", CurrencyUtil.getDecimalFormat(netWeightSum, "##.00"));
		totalMap.put("rejectedWTSum", CurrencyUtil.getDecimalFormat(rejectedWTSum, "##.00"));
		this.shipmentPrintMap.put("qr", gendrateQRcode(packing.getBatchNo()));
		this.shipmentPrintMap.put("batchNo", packing.getBatchNo());
		this.shipmentPrintMap.put("totalInfo", totalMap);
	}
	}

private void initializePrintMapForSorting() {
	
	this.shipmentPrintMap = new HashMap<String, Object>();
	List<Map<String, Object>> productMapList = new ArrayList<Map<String, Object>>();
	Map<String, Object> totalMap = new LinkedHashMap<String, Object>();
	this.shipmentPrintMap.put("checkIncomingOrSorting", "sort0");
	this.shipmentPrintMap.put("product", "");
	this.shipmentPrintMap.put("productMapList", productMapList);
	this.shipmentPrintMap.put("totalInfo", totalMap);
	this.shipmentPrintMap.put("truckId", "");
	this.shipmentPrintMap.put("driverName", "");
	this.shipmentPrintMap.put("vehicleno", "");
	this.shipmentPrintMap.put("driverNo", "");
}


private void buildTransactionPrintMapForSorting(Sorting sorting) throws MalformedURLException {
	
	List<Map<String, Object>> productMapList = new ArrayList<Map<String, Object>>();
	if (!ObjectUtil.isEmpty(sorting)) {
		long noOfBagsSum = 0l;
		double netWeightSum = 0d;
		
		this.shipmentPrintMap.put("checkIncomingOrSorting", "sort0");
		this.shipmentPrintMap.put("tructId", sorting.getTruckType());
		this.shipmentPrintMap.put("driverName", sorting.getDriverName());
		this.shipmentPrintMap.put("vehicleno", sorting.getTruckNo());
		this.shipmentPrintMap.put("driverNo", sorting.getDriverCont());
		this.shipmentPrintMap.put("productMapList", productMapList);
		if (!ObjectUtil.isEmpty(sorting.getFarmCrops())) {
			Map<String, Object> productMap = new LinkedHashMap<String, Object>();
			productMap.put("blockname", sorting.getFarmCrops().getBlockName());
			productMap.put("blockid", sorting.getFarmCrops().getBlockId());
			productMap.put("plantingid", sorting.getPlanting().getPlantingId());
			productMap.put("product", sorting.getPlanting().getVariety().getName());
			productMap.put("variety", sorting.getPlanting().getGrade().getName());
			productMap.put("qty", CurrencyUtil.getDecimalFormat(sorting.getQtyNet(), "##.00"));
			productMap.put("transferredweight", "50");
			netWeightSum += sorting.getQtyNet();
			productMapList.add(productMap);
		}
		Map<String, Object> totalMap = new LinkedHashMap<String, Object>();
		totalMap.put("netWeight", CurrencyUtil.getDecimalFormat(netWeightSum, "##.00"));
		//this.shipmentPrintMap.put("qr", gendrateQRcode(String.valueOf(sorting.getId())));
		this.shipmentPrintMap.put("qr", gendrateQRcodeForShorting(sorting));
		
		this.shipmentPrintMap.put("batchNo", sorting.getId());
		this.shipmentPrintMap.put("totalInfo", totalMap);
	}
}

public String gendrateQRcodeForShorting(Sorting sorting) throws MalformedURLException {
	String url = request.getRequestURL().toString();
	URL aURL;
	aURL = new URL(url);
	String path = aURL.getPath();
	String fullPath[] = path.split("/", 0);
	String urll = aURL.getProtocol() + "://" + aURL.getAuthority() + "/" + fullPath[1];
	String tenant = getCurrentTenantId();
	String timestamp = DateUtil.getRevisionNoWithMillSec();
	String message =null;
	if(sorting!=null){
		CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse cw WHERE cw.farmcrops.id=? and cw.qrCodeId=? and cw.coOperative.id is null",
				new Object[] {sorting.getFarmCrops().getId(),sorting.getQrCodeId()});
		
     message =sorting.getFarmCrops().getFarm().getFarmer().getVillage().getCity().getLocality().getState().getCode()+"~"
	+sorting.getFarmCrops().getFarm().getFarmer().getVillage().getCity().getLocality().getState().getName()+"~"+sorting.getFarmCrops().getBlockId()+"~"
	+sorting.getFarmCrops().getBlockName()+"~"+sorting.getPlanting().getVariety().getCode()+"~"+sorting.getPlanting().getVariety().getName()+"~"
	+sorting.getPlanting().getGrade().getCode()+"~"+sorting.getPlanting().getGrade().getName()+"~"+sorting.getQtyNet()+"~"
	+sorting.getFarmCrops().getFarm().getFarmer().getFarmerId()+"~"+sorting.getFarmCrops().getFarm().getFarmer().getFirstName()+"~"
	+sorting.getFarmCrops().getFarm().getFarmCode()+"~"+sorting.getFarmCrops().getFarm().getFarmName()+"~"
	+cw.getStockType() +"~"+sorting.getFarmCrops().getExporter().getId()+"~"
	+DateUtil.convertDateToString(cw.getCreatedDate(), "yyyy-MM-dd")+"~"+sorting.getPlanting().getPlantingId()+"~"+sorting.getQrCodeId();
	}
	ByteArrayOutputStream stream = QRCode.from(message).withErrorCorrection(ErrorCorrectionLevel.L)
			.withHint(EncodeHintType.MARGIN, 2).withSize(250, 250).stream();
	return "data:image/png;base64," + DatatypeConverter.printBase64Binary(stream.toByteArray());
}
/*stateCode +"~" +stateName +"~" +block Id +"~" +block Name +"~" +productCode +"~" +productName +"~" +varietyCode +"~" +varietyName +"~" +sorted Qty +
"~" +farmerId+"~" +farmerName +"~" +farmID+"~" +farm Name+"~" +Stock Type +"~" +exporterId+"~" +harvestDate;*/

public String gendrateQRcodeForPacking(PackingDetail pk) throws MalformedURLException {
	String url = request.getRequestURL().toString();
	URL aURL;
	aURL = new URL(url);
	String path = aURL.getPath();
	String fullPath[] = path.split("/", 0);
	String urll = aURL.getProtocol() + "://" + aURL.getAuthority() + "/" + fullPath[1];
	String tenant = getCurrentTenantId();
	String timestamp = DateUtil.getRevisionNoWithMillSec();
	String message =null;
	if(pk!=null){
		CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse cw WHERE cw.farmcrops.id=? and  cw.coOperative.id is null",
				new Object[] {pk.getBlockId().getId()});
		
     message =pk.getPacking().getBatchNo()+"~"+pk.getBlockId().getFarm().getFarmer().getVillage().getCity().getLocality().getState().getCode()+"~"
	+pk.getBlockId().getFarm().getFarmer().getVillage().getCity().getLocality().getState().getName()+"~"+pk.getBlockId().getBlockId()+"~"
	+pk.getBlockId().getBlockName()+"~"+pk.getPlanting().getVariety().getCode()+"~"+pk.getPlanting().getVariety().getName()+"~"
	+pk.getPlanting().getGrade().getCode()+"~"+pk.getPlanting().getGrade().getName()+"~"+pk.getQuantity()+"~"
	+pk.getBlockId().getFarm().getFarmer().getFarmerId()+"~"+pk.getBlockId().getFarm().getFarmer().getFirstName()+"~"
	+pk.getBlockId().getFarm().getFarmCode()+"~"+pk.getBlockId().getFarm().getFarmName()+"~3~"+pk.getBlockId().getExporter().getId()+"~"
	+DateUtil.convertDateToString(pk.getPacking().getPackingDate(), "yyyy-MM-dd")+"~"+pk.getPacking().getPackHouse().getId()+"~"+pk.getPlanting().getPlantingId()+"~"+pk.getQrCodeId();
	}
	ByteArrayOutputStream stream = QRCode.from(message).withErrorCorrection(ErrorCorrectionLevel.L)
			.withHint(EncodeHintType.MARGIN, 2).withSize(250, 250).stream();
	return "data:image/png;base64," + DatatypeConverter.printBase64Binary(stream.toByteArray());
}

public String gendrateQRcodeForIncomingShipment(PackhouseIncomingDetails pk) throws MalformedURLException {
	String url = request.getRequestURL().toString();
	URL aURL;
	aURL = new URL(url);
	String path = aURL.getPath();
	String fullPath[] = path.split("/", 0);
	String urll = aURL.getProtocol() + "://" + aURL.getAuthority() + "/" + fullPath[1];
	String tenant = getCurrentTenantId();
	String timestamp = DateUtil.getRevisionNoWithMillSec();
	String message =null;
	if(pk!=null){
		/*CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse cw WHERE cw.farmcrops.id=? and  cw.coOperative.id is null",
				new Object[] {pk.getFarmcrops().getId()});*/
		
     message =pk.getPackhouseIncoming().getBatchNo()+"~"+pk.getFarmcrops().getFarm().getFarmer().getVillage().getCity().getLocality().getState().getCode()+"~"
	+pk.getFarmcrops().getFarm().getFarmer().getVillage().getCity().getLocality().getState().getName()+"~"+pk.getFarmcrops().getBlockId()+"~"
	+pk.getFarmcrops().getBlockName()+"~"+pk.getPlanting().getVariety().getCode()+"~"+pk.getPlanting().getVariety().getName()+"~"
	+pk.getPlanting().getGrade().getCode()+"~"+pk.getPlanting().getGrade().getName()+"~"+pk.getReceivedWeight()+"~"
	+pk.getFarmcrops().getFarm().getFarmer().getFarmerId()+"~"+pk.getFarmcrops().getFarm().getFarmer().getFirstName()+"~"
	+pk.getFarmcrops().getFarm().getFarmCode()+"~"+pk.getFarmcrops().getFarm().getFarmName()+"~2~"+pk.getFarmcrops().getExporter().getId()+"~"
	+DateUtil.convertDateToString(pk.getPackhouseIncoming().getOffLoadingDate(), "yyyy-MM-dd")+"~"+pk.getPackhouseIncoming().getPackhouse().getId()+"~"+pk.getPlanting().getPlantingId()+"~"+pk.getQrCodeId();
	}
	ByteArrayOutputStream stream = QRCode.from(message).withErrorCorrection(ErrorCorrectionLevel.L)
			.withHint(EncodeHintType.MARGIN, 2).withSize(250, 250).stream();
	return "data:image/png;base64," + DatatypeConverter.printBase64Binary(stream.toByteArray());
}

private void initializePrintMap() {

	this.shipmentPrintMap = new HashMap<String, Object>();
	List<Map<String, Object>> productMapList = new ArrayList<Map<String, Object>>();
	Map<String, Object> totalMap = new LinkedHashMap<String, Object>();
	this.shipmentPrintMap.put("checkIncomingOrSorting", "ship1");
	this.shipmentPrintMap.put("recNo", "");
	this.shipmentPrintMap.put("date", "");
	this.shipmentPrintMap.put("agentId", "");
	this.shipmentPrintMap.put("agentName", "");
	this.shipmentPrintMap.put("product", "");
	this.shipmentPrintMap.put("productMapList", productMapList);
	this.shipmentPrintMap.put("totalInfo", totalMap);

	this.shipmentPrintMap.put("truckId", "");
	this.shipmentPrintMap.put("driverName", "");
	this.shipmentPrintMap.put("vehicleno", "");
	this.shipmentPrintMap.put("driverNo", "");
}
	private void buildTransactionPrintMap(PackhouseIncoming procurementMTNR) throws MalformedURLException {

		List<Map<String, Object>> productMapList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> qrMapList = new ArrayList<Map<String, Object>>();
		if (!ObjectUtil.isEmpty(procurementMTNR)) {
			long noOfBagsSum = 0l;
			double netWeightSum = 0d;
			DateFormat df = new SimpleDateFormat(getGeneralDateFormat());
			if (!ObjectUtil.isEmpty(procurementMTNR.getPackhouse().getName())) {
				if (!StringUtil.isEmpty(procurementMTNR.getPackhouse().getName())) {
					this.shipmentPrintMap.put("recNo", procurementMTNR.getPackhouse().getName());
				}
				if (!ObjectUtil.isEmpty(procurementMTNR.getOffLoadingDate())) {
					this.shipmentPrintMap.put("date", df.format(procurementMTNR.getOffLoadingDate()));
				}
				if (!ObjectUtil.isEmpty(procurementMTNR.getBatchNo())
						&& !StringUtil.isEmpty(procurementMTNR.getBatchNo())) {
					this.shipmentPrintMap.put("agentName", procurementMTNR.getBatchNo());
				}

			}
			this.shipmentPrintMap.put("checkIncomingOrSorting", "ship1");
			this.shipmentPrintMap.put("tructId", procurementMTNR.getTruckType());
			this.shipmentPrintMap.put("driverName", procurementMTNR.getDriverName());
			this.shipmentPrintMap.put("vehicleno", procurementMTNR.getTruckNo());
			this.shipmentPrintMap.put("driverNo", procurementMTNR.getDriverCont());
			// this.mtnrPrintMap.put("warehouseName",
			// procurementMTNR.getCoOperative().getWarehouseName());
			this.shipmentPrintMap.put("productMapList", productMapList);
			this.shipmentPrintMap.put("qrMapList", qrMapList);
			if (!ObjectUtil.isListEmpty(procurementMTNR.getPackhouseIncomingDetails())) {
				for (PackhouseIncomingDetails pmtDetail : procurementMTNR.getPackhouseIncomingDetails()) {
					/*
					 * if (StringUtil.isEmpty(String.valueOf(mtnrPrintMap.get(
					 * "product"))) &&
					 * !ObjectUtil.isEmpty(pmtDetail.getProcurementProduct())) {
					 * this.mtnrPrintMap.put("product",
					 * !StringUtil.isEmpty(pmtDetail
					 * .getProcurementProduct().getName()) ? pmtDetail
					 * .getProcurementProduct().getName() : ""); }
					 */
					Map<String, Object> productMap = new LinkedHashMap<String, Object>();
					Map<String, Object> qrMap = new LinkedHashMap<String, Object>();
					String pacQr = null;
					productMap.put("blockname", pmtDetail.getFarmcrops().getBlockName());
					productMap.put("blockid", pmtDetail.getFarmcrops().getBlockId());
					productMap.put("plantingid", pmtDetail.getPlanting().getPlantingId());
					productMap.put("product", pmtDetail.getPlanting().getVariety().getName());
					productMap.put("variety", pmtDetail.getPlanting().getGrade().getName());
					Sorting sr = (Sorting) farmerService.findObjectById(SORTING_QUERY, new Object[] { Long.valueOf(pmtDetail.getFarmcrops().getId()) });
					if(sr!=null){
						productMap.put("sortingDate", getGeneralDateFormat(String.valueOf(sr.getCreatedDate())));
					}
					productMap.put("transferredweight", "50");
					productMap.put("receivedweight", CurrencyUtil.getDecimalFormat(pmtDetail.getReceivedWeight(), "##.00"));
					productMap.put("receivedunit", getCatalgueNameByCode(pmtDetail.getReceivedUnits()));
					productMap.put("netWeight", pmtDetail.getNoUnits());
					netWeightSum += pmtDetail.getReceivedWeight();
					
					pacQr = gendrateQRcodeForIncomingShipment(pmtDetail);
					qrMap.put("QrCode",pacQr);
					qrMapList.add(qrMap);

					productMapList.add(productMap);
				}
			}
			Map<String, Object> totalMap = new LinkedHashMap<String, Object>();
			totalMap.put("netWeight", CurrencyUtil.getDecimalFormat(netWeightSum, "##.00"));
			this.shipmentPrintMap.put("qr", gendrateQRcode(procurementMTNR.getBatchNo()));
			//this.shipmentPrintMap.put("qr", gendrateQRcodeForIncomingShipment(procurementMTNR));
			this.shipmentPrintMap.put("batchNo", procurementMTNR.getBatchNo());
			this.shipmentPrintMap.put("totalInfo", totalMap);
		}
	}
	
/*public String gendrateQRcodeForIncomingShipment(PackhouseIncoming procurementMTNR) throws MalformedURLException {
		String url = request.getRequestURL().toString();
		URL aURL;
		aURL = new URL(url);
		String path = aURL.getPath();
		String fullPath[] = path.split("/", 0);
		String urll = aURL.getProtocol() + "://" + aURL.getAuthority() + "/" + fullPath[1];
		String tenant = getCurrentTenantId();
		String timestamp = DateUtil.getRevisionNoWithMillSec();
		String blockId="",receivedWaigth="",crop="",variety="",blockName="",cropCode="",varietyCode="",farmerId="",farmerName="",farmId="",farmName="",harvestDate="",stockType="";
		if(procurementMTNR!=null){
		for(PackhouseIncomingDetails pd:procurementMTNR.getPackhouseIncomingDetails()){
			blockId+=pd.getFarmcrops().getBlockId()+ ", ";
			blockName+=pd.getFarmcrops().getBlockName()+ ", ";
			receivedWaigth+=pd.getReceivedWeight()+ ", ";
			crop+=pd.getFarmcrops().getVariety().getName()+ ", ";
			cropCode+=pd.getFarmcrops().getVariety().getCode()+ ", ";
			variety+=pd.getFarmcrops().getGrade().getName()+ ", ";
			varietyCode+=pd.getFarmcrops().getGrade().getCode()+ ", ";
			farmerId+=pd.getFarmcrops().getFarm().getFarmer().getFarmerId()+ ", ";
			farmerName+=pd.getFarmcrops().getFarm().getFarmer().getFirstName()+ ", ";
			farmId+=pd.getFarmcrops().getFarm().getFarmer().getFarmerId()+ ", ";
			farmName+=pd.getFarmcrops().getFarm().getFarmer().getFirstName()+ ", ";
			CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse cw WHERE cw.farmcrops.id=? and  cw.coOperative.id is null",new Object[] {pd.getFarmcrops().getId()});
			if(cw!=null){
				harvestDate+=DateUtil.convertDateToString(cw.getLastHarvestDate(), getGeneralDateFormat())+", ";
				stockType+=cw.getStockType()+", ";
				
			}
		}
		blockId=blockId.replaceAll(", $", "");receivedWaigth=receivedWaigth.replaceAll(", $", "");crop=crop.replaceAll(", $", "");variety=variety.replaceAll(", $", "");
		blockName=blockName.replaceAll(", $", "");cropCode=cropCode.replaceAll(", $", "");varietyCode=varietyCode.replaceAll(", $", "");farmerId=farmerId.replaceAll(", $", "");
		farmerName=farmerName.replaceAll(", $", "");farmId=farmId.replaceAll(", $", "");farmName=farmName.replaceAll(", $", "");
		harvestDate=harvestDate.replaceAll(", $", "");stockType=stockType.replaceAll(", $", "");
		}
String message =procurementMTNR.getBatchNo()+"~" + "stateCode" + "~" + "stateName" +"~"+blockId+"~"+blockName+"~"+cropCode+"~"+crop+"~"+varietyCode+"~"+variety+"~"+receivedWaigth+"~"+farmerId+"~"
		+farmerName+"~"+farmId+"~"+farmName+"~"+stockType+"~"+procurementMTNR.getPackhouse().getExporter().getId()+"~"+harvestDate;

procurementMTNR.getPackhouse().getExporter().getVillage().getCity().getLocality().getState().getCode();
procurementMTNR.getPackhouse().getExporter().getVillage().getCity().getLocality().getState().getName();

		ByteArrayOutputStream stream = QRCode.from(message).withErrorCorrection(ErrorCorrectionLevel.L)
				.withHint(EncodeHintType.MARGIN, 2).withSize(250, 250).stream();
		return "data:image/png;base64," + DatatypeConverter.printBase64Binary(stream.toByteArray());
	}*/

	

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public String getReceiptNumberd() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public HashMap<String, Object> getShipmentPrintMap() {

		return shipmentPrintMap;
	}

	public void setShipmentPrintMap(HashMap<String, Object> shipmentPrintMap) {

		this.shipmentPrintMap = shipmentPrintMap;
	}

	private String TraceNumber;

	private HashMap<String, Object> outcomeShipmentPrintMap;

	public String processPrintOutHTML() throws IOException, ParserConfigurationException, DocumentException {
		initializeoutcomePrintMap();
		if (!StringUtil.isEmpty(TraceNumber)) {
			Shipment procurementOut = (Shipment) farmerService.findObjectById(
					"FROM Shipment s WHERE s.pConsignmentNo=? AND (s.status=? OR s.status=?)", new Object[] { TraceNumber, 1, 3 });
			buildoutcomeTransactionPrintMap(procurementOut);
		}
		return "htmlOutcome";
	}

	private void initializeoutcomePrintMap() {

		this.outcomeShipmentPrintMap = new HashMap<String, Object>();
		List<Map<String, Object>> outproductMapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> totalMap = new LinkedHashMap<String, Object>();
		this.outcomeShipmentPrintMap.put("recNo", "");
		this.outcomeShipmentPrintMap.put("date", "");
		this.outcomeShipmentPrintMap.put("agentId", "");
		this.outcomeShipmentPrintMap.put("agentName", "");
		this.outcomeShipmentPrintMap.put("product", "");
		this.outcomeShipmentPrintMap.put("productMapList", outproductMapList);
		this.outcomeShipmentPrintMap.put("totalInfo", totalMap);

		this.outcomeShipmentPrintMap.put("truckId", "");
		this.outcomeShipmentPrintMap.put("driverName", "");
		this.outcomeShipmentPrintMap.put("vehicleno", "");
		this.outcomeShipmentPrintMap.put("driverNo", "");
		this.outcomeShipmentPrintMap.put("buyeraddress", "");
		this.outcomeShipmentPrintMap.put("buyermobile", "");
		this.outcomeShipmentPrintMap.put("traceCode", "");
		this.outcomeShipmentPrintMap.put("consigno", "");
	}

	private void buildoutcomeTransactionPrintMap(Shipment procurementOut) throws MalformedURLException {

		List<Map<String, Object>> productMapLists = new ArrayList<Map<String, Object>>();
		if (!ObjectUtil.isEmpty(procurementOut)) {
			long noOfBagsSum = 0l;
			double netWeightSum = 0d;
			DateFormat df = new SimpleDateFormat(getGeneralDateFormat());

			if (!StringUtil.isEmpty(procurementOut.getPackhouse().getName())) {
				this.outcomeShipmentPrintMap.put("recNo", procurementOut.getPackhouse().getName());
			}
			if (!ObjectUtil.isEmpty(procurementOut.getShipmentDate())) {
				this.outcomeShipmentPrintMap.put("date", df.format(procurementOut.getShipmentDate()));
			}
			if (!ObjectUtil.isEmpty(procurementOut.getPackhouse().getExporter().getRefLetterNo())
					&& !StringUtil.isEmpty(procurementOut.getPackhouse().getExporter().getRefLetterNo())) {
				this.outcomeShipmentPrintMap.put("agentId",
						procurementOut.getPackhouse().getExporter().getRefLetterNo());
			}
			if (!ObjectUtil.isEmpty(procurementOut.getCustomer().getCustomerName())
					&& !StringUtil.isEmpty(procurementOut.getCustomer().getCustomerName())) {
				this.outcomeShipmentPrintMap.put("agentName", procurementOut.getCustomer().getCustomerName());
			}

			if (!ObjectUtil.isEmpty(procurementOut.getPackhouse().getExporter().getExpTinNumber())
					&& !StringUtil.isEmpty(procurementOut.getPackhouse().getExporter().getExpTinNumber())) {
				this.outcomeShipmentPrintMap.put("truckId",
						procurementOut.getPackhouse().getExporter().getExpTinNumber());
			}

			if (!ObjectUtil.isEmpty(procurementOut.getPackhouse().getExporter().getMobileNo())
					&& !StringUtil.isEmpty(procurementOut.getPackhouse().getExporter().getMobileNo())) {
				this.outcomeShipmentPrintMap.put("driverName",
						procurementOut.getPackhouse().getExporter().getMobileNo());
			}

			if (!ObjectUtil.isEmpty(procurementOut.getCustomer().getCustomerAddress())
					&& !StringUtil.isEmpty(procurementOut.getCustomer().getCustomerAddress())) {
				this.outcomeShipmentPrintMap.put("buyeraddress", procurementOut.getCustomer().getCustomerAddress());
			}

			if (!ObjectUtil.isEmpty(procurementOut.getCustomer().getMobileNo())
					&& !StringUtil.isEmpty(procurementOut.getCustomer().getMobileNo())) {
				this.outcomeShipmentPrintMap.put("buyermobile", procurementOut.getCustomer().getMobileNo());
			}

			if (!ObjectUtil.isEmpty(procurementOut.getPConsignmentNo())
					&& !StringUtil.isEmpty(procurementOut.getPConsignmentNo())) {
				this.outcomeShipmentPrintMap.put("consigno", procurementOut.getPConsignmentNo());
			}
			
			if (!ObjectUtil.isEmpty(procurementOut.getKenyaTraceCode())
					&& !StringUtil.isEmpty(procurementOut.getKenyaTraceCode())) {
				this.outcomeShipmentPrintMap.put("traceCode", procurementOut.getKenyaTraceCode());
			}

			// this.outcomeshipmentPrintMap.put("tructId",
			// procurementMTNR.getQrCode());
			// this.outcomeshipmentPrintMap.put("driverName",
			// procurementMTNR.getQrCode());
			// this.mtnrPrintMap.put("warehouseName",
			// procurementMTNR.getCoOperative().getWarehouseName());
			this.outcomeShipmentPrintMap.put("productMapList", productMapLists);
			if (!ObjectUtil.isListEmpty(procurementOut.getShipmentDetails())) {
				for (ShipmentDetails pmtDetail : procurementOut.getShipmentDetails()) {
					Map<String, Object> productMap = new LinkedHashMap<String, Object>();
					productMap.put("batchno", pmtDetail.getCityWarehouse().getBatchNo());
					productMap.put("block", pmtDetail.getCityWarehouse().getFarmcrops().getBlockId());
					productMap.put("planting", pmtDetail.getCityWarehouse().getPlanting().getPlantingId());
					productMap.put("product", pmtDetail.getCityWarehouse().getPlanting().getVariety().getName());
					productMap.put("grade", pmtDetail.getCityWarehouse().getPlanting().getGrade().getName());
					productMap.put("packunit", getCatalgueNameByCode(pmtDetail.getPackingUnit()));
					productMap.put("netWeight", pmtDetail.getPackingQty());
					double price = Double.parseDouble(pmtDetail.getPackingQty());
					netWeightSum += price;
					productMapLists.add(productMap);
				}
			}
			Map<String, Object> totalMap = new LinkedHashMap<String, Object>();
			totalMap.put("netWeight", CurrencyUtil.getDecimalFormat(netWeightSum, "##.00"));
			this.outcomeShipmentPrintMap.put("qr", gendrateQRcode(procurementOut.getPConsignmentNo()));
			this.outcomeShipmentPrintMap.put("totalInfo", totalMap);
		}

	}

	public String getTraceNumber() {
		return TraceNumber;
	}

	public void setTraceNumber(String TraceNumber) {
		this.TraceNumber = TraceNumber;
	}

	public HashMap<String, Object> getOutcomeShipmentPrintMap() {

		return outcomeShipmentPrintMap;
	}

	public void setOutcomeShipmentPrintMap(HashMap<String, Object> outcomeShipmentPrintMap) {

		this.outcomeShipmentPrintMap = outcomeShipmentPrintMap;
	}

	public String gendrateQRcode(String string) throws MalformedURLException {

		String url = request.getRequestURL().toString();
		URL aURL;
		aURL = new URL(url);
		String path = aURL.getPath();
		String fullPath[] = path.split("/", 0);
		String urll = aURL.getProtocol() + "://" + aURL.getAuthority() + "/" + fullPath[1];
		String tenant = getCurrentTenantId();
		String timestamp = DateUtil.getRevisionNoWithMillSec();
		String message = urll + "/traceabilityViewReport_list.action" + "?batchNo=" + string
				+ "&layoutType=publiclayout";

		ByteArrayOutputStream stream = QRCode.from(message).withErrorCorrection(ErrorCorrectionLevel.L)
				.withHint(EncodeHintType.MARGIN, 2).withSize(250, 250).stream();
		return "data:image/png;base64," + DatatypeConverter.printBase64Binary(stream.toByteArray());

	}

    private static final SimpleDateFormat fileNameDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat csvfileNameDateFormat = new SimpleDateFormat("ddMMyyHHmm");
    @Getter
    @Setter
    private String xlsFileName;
	private int headerRow = 0;

	public String populateXLS() throws Exception {
		dynamicReportConfig = utilService.findReportById(id);
		String fileName = dynamicReportConfig.getXlsFile() != null
				&& !StringUtil.isEmpty(dynamicReportConfig.getXlsFile()) ? dynamicReportConfig.getXlsFile()
						: getText("xlsFile");
		InputStream is = getExportDataStream(IExporter.EXPORT_MANUAL);
		setXlsFileName((fileName).replace(" ", "_").replace("null", "") + fileNameDateFormat.format(new Date()));
		Map<String, InputStream> fileMap = new HashMap<String, InputStream>();
		fileMap.put(xlsFileName, is);

		setFileInputStream(FileUtil.createFileInputStreamToZipFile((fileName), fileMap, ".xls"));

		return "xls";
	}

	public String populatePDF() throws Exception {
		dynamicReportConfig = utilService.findReportById(id);
		String fileName = dynamicReportConfig.getXlsFile() != null
				&& !StringUtil.isEmpty(dynamicReportConfig.getXlsFile()) ? dynamicReportConfig.getXlsFile()
						: getText("pdfFile");

		// ESESystem preferences = preferncesService.findPrefernceById("1");
		InputStream is = getPDFExportStream(IExporter.EXPORT_MANUAL);
		setPdfFileName((fileName).replace(" ", "_").replace("null", "") + fileNameDateFormat.format(new Date()));
		Map<String, InputStream> fileMap = new HashMap<String, InputStream>();
		fileMap.put(pdfFileName, is);
		setFileInputStream(FileUtil.createFileInputStreamToZipFile((fileName), fileMap, ".pdf"));
		return "pdf";
	}
	
	PdfPCell pdfCell = null;
	PdfPTable table = null;
	Font cellFont = null; // font for cells.
	Font titleFont = null; // font for title text.
	Paragraph title = null; // to add title for report
	String columnHeaders = null; // to hold column headers from property
	Document document = null;
	private int iddCol;

	public InputStream getPDFExportStream(String exportType)
			throws IOException, DocumentException, NoSuchFieldException, SecurityException {

		InputStream fileInputStream;
		List<Object[]> mainGridRows = new ArrayList<>();
		JsonArray header = new JsonArray();
		JsonArray dataAr = new JsonArray();
		// dynamicReportConfig =
		// clientService.findReportByName(DynamicReportProperties.MOBILE_USER_SUMMARY_REPORT);
		if (exportDataXls != null && !StringUtil.isEmpty(exportDataXls)) {
			Gson gs = new Gson();
			JsonObject json = gs.fromJson(exportDataXls, JsonObject.class);
			header = json.get("header").getAsJsonArray();
			dataAr = json.get("body").getAsJsonArray();
		} else {
			Map data = getExportData(true);
			mainGridRows = (List<Object[]>) data.get(ROWS);
			Object objj = data.get(RECORDS);
			jss = new JSONObject();
			if (objj instanceof JSONObject) {
				JSONObject countart = (JSONObject) objj;
				totalRecords = (Integer) countart.get("count");
				if (countart.containsKey("footers")) {
					jss = (JSONObject) countart.get("footers");
				}
			}

			if (ObjectUtil.isListEmpty(mainGridRows))
				return null;
		}
		String fName = dynamicReportConfig.getXlsFile() != null && !StringUtil.isEmpty(dynamicReportConfig.getXlsFile())
				? dynamicReportConfig.getXlsFile() : getText("pdfFile");
		Map<Long, Object> valMap = formValMap(dynamicReportConfig, getLoggedInUserLanguage());
		// file.
		SimpleDateFormat fileNameDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		document = new Document();

		HttpServletRequest request = ReflectUtil.getCurrentHttpRequest();

		String makeDir = FileUtil.storeXls(request.getSession().getId());
		String fileName = fName + fileNameDateFormat.format(new Date()) + ".pdf";
		FileOutputStream fileOut = new FileOutputStream(makeDir + fileName);
		PdfWriter.getInstance(document, fileOut);

		document.open();

		branchIdValue = getBranchId(); // set value for branch id.
		buildBranchMap();

		String serverFilePath = request.getSession().getServletContext().getRealPath("/");
		serverFilePath = serverFilePath.replace('\\', '/');
		String arialFontFileLocation = serverFilePath + "/fonts/ARIAL.TTF";
		arialFontFileLocation = arialFontFileLocation.replace("//", "/");
		BaseFont bf = BaseFont.createFont(arialFontFileLocation, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance(exportLogo());
		logo.scaleAbsolute(100, 50);
		// resizing logo image size.

		document.add(logo); // Adding logo in PDF file.

		// below of code for title text
		titleFont = new Font(FontFamily.HELVETICA, 14, Font.BOLD, GrayColor.GRAYBLACK);
		title = new Paragraph(new Phrase(dynamicReportConfig.getReport(), titleFont));
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);
		document.add(
				new Paragraph(new Phrase(" ", new Font(FontFamily.HELVETICA, 15, Font.NORMAL, GrayColor.GRAYBLACK)))); // Add
		cellFont = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, GrayColor.GRAYBLACK); // a
		// blank
		// line

		Map<String, DynamicReportConfigFilter> filterFieldMap = new LinkedHashMap<>();
		dynamicReportConfig.getDynmaicReportConfigFilters().stream().forEach(df -> {
			filterFieldMap.put(df.getField(), df);
		});

		/*
		 * Map<String, Double> totMap = new LinkedHashMap<>(); // title.
		 * dynamicReportConfig.getDynmaicReportConfigDetails().stream()
		 * .filter(f -> f.getIsExportAvailability() && f.getIsFooterSum() !=
		 * null && f.getIsFooterSum().equals("1")) .sorted((f1, f2) ->
		 * Long.compare(f1.getOrder(), f2.getOrder())).forEach(dy -> {
		 * totMap.put(dy.getLabelName() + "_" + dy.getOrder(), 0.0); });
		 * dynamicReportConfig.getDynmaicReportConfigDetails().stream()
		 * .filter(f -> f.getIsExportAvailability() && f.getIsFooterSum() !=
		 * null && f.getIsFooterSum().equals("2")) .forEach(dy -> { tothead =
		 * String.valueOf(dy.getOrder()); });
		 */Map<String, Double> totMap = new LinkedHashMap<>();
		dynamicReportConfig.getDynmaicReportConfigDetails()
				.remove(dynamicReportConfig.getDynmaicReportConfigDetails().iterator().next());
		if (!StringUtil.isEmpty(getBranchId())) {
			dynamicReportConfig.getDynmaicReportConfigDetails()
					.remove(dynamicReportConfig.getDynmaicReportConfigDetails().iterator().next());

		} else if (request.getSession() != null) {
			dynamicReportConfig.getDynmaicReportConfigDetails()
					.remove(dynamicReportConfig.getDynmaicReportConfigDetails().iterator().next());
		}
		DynamicReportConfigDetail dtt = dynamicReportConfig.getDynmaicReportConfigDetails().iterator().next();
		if (dtt.getField().contains("##")) {
			iddCol = dtt.getField().split("##").length;
		} else {
			iddCol = 0;
		}

		dynamicReportConfig.getDynmaicReportConfigDetails().stream().filter(config -> config.getIsExportAvailability())
				.forEach(dynamicReportConfigDetail -> {
					headerRow++;
				});

		if (StringUtil.isEmpty(getBranchId())) {

			table = new PdfPTable(headerRow + 1);
		} else {
			table = new PdfPTable(headerRow);
		}

		if (StringUtil.isEmpty(getBranchId())) {

			table.setWidthPercentage(100); // Set Table Width.
			table.getDefaultCell().setUseAscender(true);
			table.getDefaultCell().setUseDescender(true);
			pdfCell = new PdfPCell(new Phrase(getLocaleProperty("branch"), cellFont));
			pdfCell.setBackgroundColor(new BaseColor(255, 255, 224));
			pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pdfCell.setNoWrap(false); // To set wrapping of text in
			table.addCell(pdfCell);

		}
		if (header != null && header.size() > 0) {

			header.spliterator().forEachRemaining(uu -> {
				table.setWidthPercentage(100);
				table.setTotalWidth(100);// Set Table Width.
				table.getDefaultCell().setUseAscender(true);
				table.getDefaultCell().setUseDescender(true);

				pdfCell = new PdfPCell(new Phrase(uu.getAsString(), cellFont));
				pdfCell.setBackgroundColor(new BaseColor(255, 255, 224));
				pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pdfCell.setNoWrap(false); // To set wrapping of text in
											// cell.
				// cell.setColspan(3); // To add column span.
				table.addCell(pdfCell);

			});

		} else {
			/*
			 * dynamicReportConfig.getDynmaicReportConfigDetails().stream()
			 * .filter(config -> config.getIsExportAvailability()) .sorted((f1,
			 * f2) -> Long.compare(f1.getOrder(), f2.getOrder()))
			 * .forEach(dynamicReportConfigDetail -> {
			 */
			dynamicReportConfig.getDynmaicReportConfigDetails().stream()
					.filter(config -> config.getIsExportAvailability())
					.sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder()))
					.forEach(dynamicReportConfigDetail -> {
						// cell for table.
						table.setWidthPercentage(100);
						table.setTotalWidth(100);// Set Table Width.
						table.getDefaultCell().setUseAscender(true);
						table.getDefaultCell().setUseDescender(true);

						pdfCell = new PdfPCell(
								new Phrase(getLocaleProperty(dynamicReportConfigDetail.getLabelName()), cellFont));
						pdfCell.setBackgroundColor(new BaseColor(255, 255, 224));
						pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
						pdfCell.setNoWrap(false); // To set wrapping of text in
													// cell.
						// cell.setColspan(3); // To add column span.
						table.addCell(pdfCell);

					});
		}

		// columnHeaders = columnHeaders();

		// setting
		// font
		// for
		// cells.
		if (dataAr != null && dataAr.size() > 0) {

			dataAr.spliterator().forEachRemaining(uu -> {
				AtomicInteger colCount = new AtomicInteger(1);

				JsonArray data1 = uu.getAsJsonArray();
				data1.spliterator().forEachRemaining(dat -> {

					String ansVal = parseHTMLContent(dat.getAsString());
					colCount.incrementAndGet();

					if (!ObjectUtil.isEmpty(ansVal) && !StringUtil.isEmpty(ansVal)) {
						pdfCell = new PdfPCell(new Phrase(String.valueOf(ansVal), cellFont));
					} else {
						pdfCell = new PdfPCell(new Phrase("NA", cellFont));
					}

					table.addCell(pdfCell);

				});
			});

		} else {

			mainGridRows.stream().forEach(arr -> {
				AtomicInteger colCount = new AtomicInteger(1);

				initializeMap(arr);
				String parentId = "";

				if (iddCol == 0) {
					parentId = String.valueOf(arr[0]);
				} else {
					for (i = 0; i < iddCol; i++) {
						parentId = parentId + String.valueOf(arr[i]) + "-";
						if (i != 0) {
							colCount.getAndIncrement();
						}
					}
					parentId = StringUtil.removeLastChar(parentId, '-');

				}

				if (StringUtil.isEmpty(getBranchId())) {
					fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(colCount.getAndIncrement()));
					pdfCell = new PdfPCell(new Phrase(String.valueOf(getBranchesMap().get(fValue)), cellFont));
					table.addCell(pdfCell);
				} else {
					colCount.getAndIncrement();
				}
				StringBuilder exportParams = new StringBuilder("");

				dynamicReportConfig.getDynmaicReportConfigDetails().stream()
						.sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder()))
						.forEach(dynamicReportConfigDetail -> {
							if (dynamicReportConfigDetail.getIsExportAvailability()) {
								String ansVal = parseHTMLContent(getAnswer(dynamicReportConfigDetail, arr, colCount,
										valMap, getLoggedInUserLanguage(), exportParams));
								colCount.incrementAndGet();

								if (!ObjectUtil.isEmpty(ansVal) && !StringUtil.isEmpty(ansVal)) {
									pdfCell = new PdfPCell(new Phrase(String.valueOf(ansVal), cellFont));
								} else {
									pdfCell = new PdfPCell(new Phrase("NA", cellFont));
								}

								if (dynamicReportConfigDetail.getAlignment() != null
										&& !StringUtil.isEmpty(dynamicReportConfigDetail.getAlignment())) {
									if (dynamicReportConfigDetail.getAlignment().equalsIgnoreCase("center")) {
										pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
									} else if (dynamicReportConfigDetail.getAlignment().equalsIgnoreCase("left")) {
										pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
									} else if (dynamicReportConfigDetail.getAlignment().equalsIgnoreCase("right")) {
										pdfCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									}
								}

								table.addCell(pdfCell);
							} else {
								colCount.incrementAndGet();
							}

						});

			});

		}
		document.add(table);

		InputStream stream = new FileInputStream(new File(makeDir + fileName));
		document.close();
		fileOut.close();
		return stream;
	}
	
	XSSFRow row, filterRowTitle, filterRow1, filterRow2, filterRow3, filterRow4;
	XSSFRow titleRow;
	int colCount, rowCount, titleRow1 = 4, titleRow2 = 6;
	Cell cell;
	Integer cellIndex;
	Integer heade = null;
	JSONObject jss = new JSONObject();
	@Setter
	private String csvFileName;
	@Getter
	@Setter
	private String exportDataXls;
	int mainGridIterator = 0;
	int serialNo = 1;

	public InputStream getExportDataStream(String exportType) throws IOException {
		InputStream fileInputStream;
		List<Object[]> mainGridRows = new ArrayList<>();
		JsonArray header = new JsonArray();
		JsonArray dataAr = new JsonArray();
		// dynamicReportConfig =
		// clientService.findReportByName(DynamicReportProperties.MOBILE_USER_SUMMARY_REPORT);
		if (exportDataXls != null && !StringUtil.isEmpty(exportDataXls)) {
			Gson gs = new Gson();
			JsonObject json = gs.fromJson(exportDataXls, JsonObject.class);
			header = json.get("header").getAsJsonArray();
			dataAr = json.get("body").getAsJsonArray();
		} else {
			Map data = getExportData(true);
			mainGridRows = (List<Object[]>) data.get(ROWS);
			Object objj = data.get(RECORDS);
			jss = new JSONObject();
			if (objj instanceof JSONObject) {
				JSONObject countart = (JSONObject) objj;
				totalRecords = (Integer) countart.get("count");
				if (countart.containsKey("footers")) {
					jss = (JSONObject) countart.get("footers");
				}
			}

			if (ObjectUtil.isListEmpty(mainGridRows))
				return null;
		}

		String fName = dynamicReportConfig.getXlsFile() != null && !StringUtil.isEmpty(dynamicReportConfig.getXlsFile())
				? dynamicReportConfig.getXlsFile() : getText("xlsFile");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(fName);

		/** Defining Styles */
		XSSFCellStyle headerStyle = workbook.createCellStyle();

		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		XSSFCellStyle filterStyle = workbook.createCellStyle();

		filterStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		filterStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

		XSSFCellStyle headerLabelStyle = workbook.createCellStyle();
		headerLabelStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		headerLabelStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		headerLabelStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		headerLabelStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headerLabelStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		headerLabelStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerLabelStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		headerLabelStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headerLabelStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerLabelStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);

		XSSFColor subGridColor = new XSSFColor(new Color(204, 255, 204));
		filterStyle.setFillForegroundColor(subGridColor);
		filterStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		XSSFCellStyle subGridHeader = workbook.createCellStyle();
		subGridHeader.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		subGridHeader.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		subGridHeader.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		subGridHeader.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		subGridHeader.setBorderRight(XSSFCellStyle.BORDER_THIN);
		subGridHeader.setRightBorderColor(IndexedColors.BLACK.getIndex());
		subGridHeader.setBorderTop(XSSFCellStyle.BORDER_THIN);
		subGridHeader.setTopBorderColor(IndexedColors.BLACK.getIndex());
		subGridHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		XSSFColor myColor = new XSSFColor(new Color(237, 237, 237));
		subGridHeader.setFillForegroundColor(myColor);

		XSSFCellStyle rows = workbook.createCellStyle();
		rows.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		rows.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		rows.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		rows.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		rows.setBorderRight(XSSFCellStyle.BORDER_THIN);
		rows.setRightBorderColor(IndexedColors.BLACK.getIndex());
		rows.setBorderTop(XSSFCellStyle.BORDER_THIN);
		rows.setTopBorderColor(IndexedColors.BLACK.getIndex());

		/** Defining Fonts */
		XSSFFont font1 = workbook.createFont();
		font1.setFontHeightInPoints((short) 22);

		XSSFFont font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 16);

		XSSFFont font3 = workbook.createFont();
		font3.setFontHeightInPoints((short) 14);

		XSSFFont font4 = workbook.createFont();
		font3.setFontHeightInPoints((short) 10);

		XSSFCellStyle style1 = (XSSFCellStyle) workbook.createCellStyle();

		XSSFCellStyle style2 = (XSSFCellStyle) workbook.createCellStyle();

		DecimalFormat df2 = new DecimalFormat("0.00");

		int imgRow1 = 0;
		int imgRow2 = 4;
		int imgCol1 = 0;
		int imgCol2 = 0;

		int titleRow1 = 2;
		int titleRow2 = 5;
		int count = 0;
		rowCount = 2;
		colCount = 0;

		sheet.addMergedRegion(new CellRangeAddress(imgRow1, imgRow2, imgCol1, imgCol2));
		sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, titleRow1, titleRow2));

		titleRow = sheet.createRow(rowCount++);
		titleRow.setHeight((short) 500);

		cell = titleRow.createCell(2);
		cell.setCellValue(dynamicReportConfig.getReport());
		font1.setBoldweight((short) 22);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerStyle.setFont(font1);
		cell.setCellStyle(headerStyle);

		rowCount = 8;
		Map<String, DynamicReportConfigFilter> filterFieldMap = new LinkedHashMap<>();
		dynamicReportConfig.getDynmaicReportConfigFilters().stream().forEach(df -> {
			filterFieldMap.put(df.getField(), df);
		});

		row = sheet.createRow(rowCount++);
		row.setHeight((short) 400);

		colCount = 0;
		Map<String, Double> totMap = new LinkedHashMap<>();
		dynamicReportConfig.getDynmaicReportConfigDetails()
				.remove(dynamicReportConfig.getDynmaicReportConfigDetails().iterator().next());
		if (!StringUtil.isEmpty(getBranchId())) {
			dynamicReportConfig.getDynmaicReportConfigDetails()
					.remove(dynamicReportConfig.getDynmaicReportConfigDetails().iterator().next());

		} else if (request.getSession() != null) {
			dynamicReportConfig.getDynmaicReportConfigDetails()
					.remove(dynamicReportConfig.getDynmaicReportConfigDetails().iterator().next());
		}
		cell = row.createCell(colCount++);
		cell.setCellValue("S.No");
		font2.setBoldweight((short) 5);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		filterStyle.setFont(font2);
		/* cell.setCellStyle(filterStyle); */
		cell.setCellStyle(filterStyle);

		if (StringUtil.isEmpty(getBranchId())) {
			cell = row.createCell(colCount++);
			cell.setCellValue(getLocaleProperty("branch"));
			font2.setBoldweight((short) 5);
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			filterStyle.setFont(font2);
			/* cell.setCellStyle(filterStyle); */
			cell.setCellStyle(filterStyle);

		}
		if (header != null && header.size() > 0) {

			header.spliterator().forEachRemaining(uu -> {
				cell = row.createCell(colCount++);
				String[] groupHeader;

				cell.setCellValue(uu.getAsString());
				font2.setBoldweight((short) 5);
				font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				filterStyle.setFont(font2);
				sheet.setColumnWidth(mainGridIterator, (15 * 550));
				mainGridIterator++;

				/* cell.setCellStyle(filterStyle); */
				cell.setCellStyle(filterStyle);

			});

		} else {
			dynamicReportConfig.getDynmaicReportConfigDetails().stream()
					.filter(config -> config.getIsExportAvailability())
					.sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder()))
					.forEach(dynamicReportConfigDetail -> {
						cell = row.createCell(colCount++);
						String[] groupHeader;
						String heading = "";
						if (!StringUtil.isEmpty(dynamicReportConfigDetail.getIsGroupHeader())) {
							groupHeader = dynamicReportConfigDetail.getIsGroupHeader().split("~");
							heading = groupHeader[2] + " "
									+ getLocaleProperty(dynamicReportConfigDetail.getLabelName());
						} else {
							heading = getLocaleProperty(dynamicReportConfigDetail.getLabelName());
						}
						cell.setCellValue(heading);
						font2.setBoldweight((short) 5);
						font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
						filterStyle.setFont(font2);
						sheet.setColumnWidth(mainGridIterator, (15 * 550));
						mainGridIterator++;

						if (!StringUtil.isEmpty(dynamicReportConfigDetail.getIsFooterSum())
								&& dynamicReportConfigDetail.getIsFooterSum().equals("1")) {
							heade = colCount;
						}
						/* cell.setCellStyle(filterStyle); */
						cell.setCellStyle(filterStyle);

					});
		}

		sheet.setColumnWidth(mainGridIterator++, (15 * 550));
		sheet.setColumnWidth(mainGridIterator, (15 * 550));
		if (dataAr != null && dataAr.size() > 0) {

			dataAr.spliterator().forEachRemaining(uu -> {
				row = sheet.createRow(rowCount++);
				AtomicInteger col = new AtomicInteger(0);
				row.setHeight((short) 400);
				cell = row.createCell(col.getAndIncrement());
				style1.setAlignment(CellStyle.ALIGN_CENTER);
				cell.setCellStyle(style1);
				cell.setCellValue(serialNo++);

				JsonArray data1 = uu.getAsJsonArray();
				data1.spliterator().forEachRemaining(dat -> {

					cell = row.createCell(col.getAndIncrement());

					cell.setCellValue(parseHTMLContent(dat.getAsString()));
					runCount.incrementAndGet();

				});
			});

		} else {

			Map<Long, Object> valMap = formValMap(dynamicReportConfig, getLoggedInUserLanguage());
			mainGridRows.stream().forEach(arr -> {
				row = sheet.createRow(rowCount++);
				AtomicInteger col = new AtomicInteger(0);
				row.setHeight((short) 400);
				AtomicInteger colCountRow = new AtomicInteger(1);

				initializeMap(arr);

				cell = row.createCell(col.getAndIncrement());
				style1.setAlignment(CellStyle.ALIGN_CENTER);
				cell.setCellStyle(style1);
				cell.setCellValue(serialNo++);

				String parentId = "";

				if (StringUtil.isEmpty(getBranchId())) {
					cell = row.createCell(col.getAndIncrement());
					fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(colCountRow.getAndIncrement()));
					cell.setCellValue(getBranchesMap().get(fValue));
				} else {
					colCountRow.getAndIncrement();
				}

				StringBuilder exportParams = new StringBuilder();
				AtomicInteger runCount = new AtomicInteger(1);
				if (StringUtil.isEmpty(getBranchId())) {
					fValue = ReflectUtil.getObjectFieldValue(arr, String.valueOf(runCount.getAndIncrement()));
					cell = row.createCell(col.getAndIncrement());
					cell.setCellValue(fValue.toString());
				} else {
					runCount.getAndIncrement();
				}
				dynamicReportConfig.getDynmaicReportConfigDetails().stream()
						.sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder()))
						.forEach(dynamicReportConfigDetail -> {
							if (!dynamicReportConfigDetail.getIsExportAvailability()) {
								runCount.getAndIncrement();
							} else {
								cell = row.createCell(col.getAndIncrement());
								System.err.println(dynamicReportConfigDetail.getId() + ","
										+ dynamicReportConfigDetail.getAcessType());
								String ansVal = parseHTMLContent(getAnswer(dynamicReportConfigDetail, arr, runCount,
										valMap, getLoggedInUserLanguage(), exportParams));
								System.err.println("ansVal" + ansVal);
								runCount.incrementAndGet();

								if (dynamicReportConfigDetail.getAlignment() != null
										&& !StringUtil.isEmpty(dynamicReportConfigDetail.getAlignment())) {
									if (dynamicReportConfigDetail.getAlignment().equalsIgnoreCase("center")) {
										style2.setAlignment(CellStyle.ALIGN_CENTER);
										cell.setCellStyle(style2);
									} else if (dynamicReportConfigDetail.getAlignment().equalsIgnoreCase("left")) {
										style2.setAlignment(CellStyle.ALIGN_LEFT);
										cell.setCellStyle(style2);
									} else if (dynamicReportConfigDetail.getAlignment().equalsIgnoreCase("right")) {
										style2.setAlignment(CellStyle.ALIGN_RIGHT);
										cell.setCellStyle(style2);
									}
								}

								if (!ObjectUtil.isEmpty(ansVal) && !StringUtil.isEmpty(ansVal)) {

									if (dynamicReportConfigDetail.getDataType() != null) {
										if (dynamicReportConfigDetail.getDataType().equalsIgnoreCase("2")) {
											cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
											cell.setCellValue(Double.parseDouble(
													CurrencyUtil.getDecimalFormat(Double.valueOf(ansVal), "##.00")));
										} else if (StringUtil.isLong(ansVal)) {
											cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
											cell.setCellValue(Long.parseLong(ansVal));
										} else {
											cell.setCellValue(ansVal);
										}
									} else {
										cell.setCellValue(ansVal);
									}
								} else {
									cell.setCellValue("");
								}
							}

						});

			});
			if (jss != null && !jss.isEmpty() && heade != null) {
				row = sheet.createRow(rowCount++);
				cell = row.createCell(heade - 2);
				cell.setCellValue("Total");
				font4.setBoldweight((short) 5);
				font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				filterStyle.setFont(font4);
				cell.setCellStyle(filterStyle);
				jss.keySet().stream().forEach(u -> {
					cell = row.createCell(heade - 1);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					// cell.setCellValue(u.getValue().toString());
					cell.setCellValue(jss.get(u).toString());
					font4.setBoldweight((short) 5);
					font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					filterStyle.setFont(font4);
					cell.setCellStyle(filterStyle);

				});

			}
		}

		/* for (int i = 0; i <= colCount; i++) { sheet.autoSizeColumn(i); } */

		sheet.autoSizeColumn(20000000);
		Drawing drawing = sheet.createDrawingPatriarch();
		int pictureIdx = getPicIndex(workbook);
		XSSFClientAnchor anchor = new XSSFClientAnchor(100, 150, 900, 100, (short) 0, 0, (short) 0, 10);
		anchor.setAnchorType(1);
		Picture picture = drawing.createPicture(anchor, pictureIdx);
		picture.resize();

		String makeDir = FileUtil.storeXls(id);
		String fileName = fName + fileNameDateFormat.format(new Date()) + ".xls";
		FileOutputStream fileOut = new FileOutputStream(makeDir + fileName);
		workbook.write(fileOut);
		/*
		 * InputStream stream = new FileInputStream(new File(makeDir +
		 * fileName)); fileOut.close();
		 */
		File file = new File(makeDir + fileName);
		fileInputStream = new FileInputStream(file);
		fileOut.close();

		return fileInputStream;
	}
	
	public Map getExportData(boolean isExport) {
		getDataTableJQGridRequestParam();
		otherMap.put(DynamicReportConfig.DYNAMIC_CONFIG_DETAIL, dynamicReportConfig.getDynmaicReportConfigDetails());
		otherMap.put(DynamicReportConfig.ENTITY, dynamicReportConfig.getEntityName());
		otherMap.put(DynamicReportConfig.ALIAS, dynamicReportConfig.getAlias());
		otherMap.put(DynamicReportConfig.GROUP_PROPERTY, dynamicReportConfig.getGroupProperty());

		Map<String, String> locationFilter = new HashMap<>();
		locationFilter.put("villagecode", villagecode);
		locationFilter.put("gpcode", gpcode);
		locationFilter.put("citycode", citycode);
		locationFilter.put("localitycode", localitycode);
		locationFilter.put("statecode", statecode);
		otherMap.put("IsExport", isExport);
		otherMap.put(DynamicReportConfig.LOCATION_FILTER_PROPERTY, locationFilter);
		mainMap.put(DynamicReportConfig.OTHER_FIELD, otherMap);

		Map data = null;
		if (!ObjectUtil.isEmpty(dynamicReportConfig) && dynamicReportConfig.getFetchType() == 2L) {
			final Map<String, String> filtersList = new HashMap<>();
			if (!StringUtil.isEmpty(filterList)) {
				Type listType1 = new TypeToken<Map<String, String>>() {
				}.getType();
				filtersList.putAll(new Gson().fromJson(filterList, listType1));
				filtersList.put("branchId", "9~null");

			}

			if (dynamicReportConfig.getDynmaicReportConfigFilters().size() > 0) {

				dynamicReportConfig.getDynmaicReportConfigFilters().stream()
						.filter(u -> u.getDefaultFilter() != null && !u.getDefaultFilter().equalsIgnoreCase("insp")
								&& !u.getDefaultFilter().equalsIgnoreCase("inspwr") && !u.getDefaultFilter().equalsIgnoreCase("hcdFilter")
								&& !u.getDefaultFilter().equalsIgnoreCase("dealer"))
						.forEach(u -> {
							filtersList.put(u.getField().split("~")[0], u.getField().split("~")[1] + "~"
									+ u.getDefaultFilter() + "~" + u.getField().split("~")[2]);

						});
				if (request.getSession().getAttribute("dealerId") != null) {
					dynamicReportConfig.getDynmaicReportConfigFilters().stream().filter(
							u -> u.getDefaultFilter() != null && u.getDefaultFilter().equalsIgnoreCase("dealer"))
							.forEach(u -> {
								filtersList.put(u.getField().split("~")[0], u.getField().split("~")[1] + "~"
										+ request.getSession().getAttribute("dealerId") + "~3");

							});
				}
				
			
					dynamicReportConfig.getDynmaicReportConfigFilters().stream().filter(
							u -> u.getDefaultFilter() != null && u.getDefaultFilter().equalsIgnoreCase("hcdFilter"))
							.forEach(u -> {
								filtersList.put(u.getDefaultFilter(), u.getField());

							});
			
				if (request.getSession().getAttribute("inspId") != null
						&& request.getSession().getAttribute("inspId") != "false") {
					dynamicReportConfig.getDynmaicReportConfigFilters().stream()
							.filter(u -> u.getDefaultFilter() != null && u.getDefaultFilter().equalsIgnoreCase("insp"))
							.forEach(u -> {
								filtersList.put(u.getField().split("~")[0], u.getField().split("~")[1] + "~"
										+ request.getSession().getAttribute("inspId") + "~3");
								// filtersList.put("status", "1~3~1");

							});

					dynamicReportConfig.getDynmaicReportConfigFilters().stream().filter(
							u -> u.getDefaultFilter() != null && u.getDefaultFilter().equalsIgnoreCase("inspwr"))
							.forEach(u -> {
								filtersList.put(u.getField().split("~")[0], u.getField().split("~")[1] + "~"
										+ request.getSession().getAttribute("inspId") + "~3");

							});
				}

			}

			mainMap.put(DynamicReportConfig.FILTER_FIELDS, filtersList);
			if (getSidx() != null && !StringUtil.isEmpty(getSidx())) {
				DynamicReportConfigDetail ds = dynamicReportConfig.getDynmaicReportConfigDetails().stream()
						.anyMatch(u -> u.getId().equals(Long.valueOf(getSidx())))
								? dynamicReportConfig.getDynmaicReportConfigDetails().stream()
										.filter(u -> u.getId().equals(Long.valueOf(getSidx()))).findFirst().get()
								: null;
				setSidx(ds.getField());
				if (ds.getSortField()!=null && !StringUtil.isEmpty(ds.getSortField())) {
					setSidx(ds.getField());
					mainMap.get(DynamicReportConfig.OTHER_FIELD).put("sortQry", ds.getSortField());
				}

			} else {
				setSidx("id");
				setSord("desc");
			}
			data = readProjectionDataStatic(mainMap);
		} else if (!ObjectUtil.isEmpty(dynamicReportConfig) && dynamicReportConfig.getFetchType() == 3L) {

			data = readProjectionDataView(mainMap);
		} else {
			data = readData();
		}
		return data;
	}

/*	XSSFRow row, filterRowTitle, filterRow1, filterRow2, filterRow3, filterRow4;
	XSSFRow titleRow;
	int colCount, rowCount, titleRow1 = 4, titleRow2 = 6;
	Cell cell;
	Integer cellIndex;
	Integer heade = null;
	JSONObject jss = new JSONObject();
	int flagxls = 0;
	int mainGridIterator = 0;
	int serialNo = 1;
	String heading = "";

	public Object[] getExportDataStream(String exportType, String configId, String lang, String branchIdd,
			String profId) throws IOException {
		InputStream fileInputStream;
		serialNo = 1;
		Object[] recData = new Object[2];

		String breanchidPa = "BRANCH_ID";
		Map<String, String> defMapMailExport = new HashMap<String, String>();
		if (!IExporter.EXPORT_MANUAL.equalsIgnoreCase(exportType)) {
			setSord("desc");
			setSidx("id");
			dynamicReportConfig = utilService.findReportById(configId);

			if (StringUtil.isEmpty(filterList) && dynamicReportConfig.getDynmaicReportConfigFilters().size() > 0) {
				Map<String, String> filtersList = new HashMap<String, String>();
				dynamicReportConfig.getDynmaicReportConfigFilters().stream()
						.filter(u -> !u.getDefaultFilter().equalsIgnoreCase("insp")
								&& !u.getDefaultFilter().equalsIgnoreCase("inspwr")
								&& !u.getDefaultFilter().equalsIgnoreCase("dealer"))
						.forEach(u -> {
							filtersList.put(u.getField().split("~")[0], u.getField().split("~")[1] + "~"
									+ u.getDefaultFilter() + "~" + u.getField().split("~")[2]);
							mainMap.put(DynamicReportConfig.FILTER_FIELDS, filtersList);
						});
				if (request != null && request.getSession() != null
						&& request.getSession().getAttribute("dealerId") != null) {
					dynamicReportConfig.getDynmaicReportConfigFilters().stream()
							.filter(u -> u.getDefaultFilter().equalsIgnoreCase("dealer")).forEach(u -> {
								filtersList.put(u.getField().split("~")[0], u.getField().split("~")[1] + "~"
										+ request.getSession().getAttribute("dealerId") + "~3");
								mainMap.put(DynamicReportConfig.FILTER_FIELDS, filtersList);
							});
				}
				if (request != null && request.getSession() != null
						&& request.getSession().getAttribute("inspId") != null
						&& request.getSession().getAttribute("inspId") != "false") {
					dynamicReportConfig.getDynmaicReportConfigFilters().stream()
							.filter(u -> u.getDefaultFilter().equalsIgnoreCase("insp")).forEach(u -> {
								filtersList.put(u.getField().split("~")[0], u.getField().split("~")[1] + "~"
										+ request.getSession().getAttribute("inspId") + "~3");
								filtersList.put("status", "1~3~1");
								mainMap.put(DynamicReportConfig.FILTER_FIELDS, filtersList);
							});

					dynamicReportConfig.getDynmaicReportConfigFilters().stream()
							.filter(u -> u.getDefaultFilter().equalsIgnoreCase("inspwr")).forEach(u -> {
								filtersList.put(u.getField().split("~")[0], u.getField().split("~")[1] + "~"
										+ request.getSession().getAttribute("inspId") + "~3");
								// filtersList.put("status", "1~3~1");
								mainMap.put(DynamicReportConfig.FILTER_FIELDS, filtersList);
							});
				}
			}

		}

		otherMap.put(DynamicReportConfig.DYNAMIC_CONFIG_DETAIL, dynamicReportConfig.getDynmaicReportConfigDetails());
		otherMap.put(DynamicReportConfig.ENTITY, dynamicReportConfig.getEntityName());
		otherMap.put(DynamicReportConfig.ALIAS, dynamicReportConfig.getAlias());
		otherMap.put(DynamicReportConfig.GROUP_PROPERTY, dynamicReportConfig.getGroupProperty());
		mainMap.put(DynamicReportConfig.OTHER_FIELD, otherMap);

		Map data = null;
		if (!ObjectUtil.isEmpty(dynamicReportConfig) && dynamicReportConfig.getFetchType() == 2L) {
			data = readProjectionDataStatic(mainMap);
		} else if (!ObjectUtil.isEmpty(dynamicReportConfig) && dynamicReportConfig.getFetchType() == 3L) {

			data = readProjectionDataView(mainMap);
		} else {
			data = readData();
		}

		List<Object[]> mainGridRows = (List<Object[]>) data.get(ROWS);
		Object objj = data.get(RECORDS);
		jss = new JSONObject();
		if (objj instanceof JSONObject) {
			JSONObject countart = (JSONObject) objj;
			totalRecords = (Integer) countart.get("count");
			if (countart.containsKey("footers")) {
				jss = (JSONObject) countart.get("footers");
			}
		}
		recData[0] = mainGridRows.size();
		String fName = dynamicReportConfig.getXlsFile() != null && !StringUtil.isEmpty(dynamicReportConfig.getXlsFile())
				? getLocalePropertyForMailExport(dynamicReportConfig.getXlsFile(), lang).trim() : getText("xlsFile");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(fName);

		*//** Defining Styles *//*
		XSSFCellStyle headerStyle = workbook.createCellStyle();
		
		 * headerStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
		 * headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		 * headerStyle.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
		 * headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		 * headerStyle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
		 * headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		 * headerStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
		 * headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		 
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		XSSFCellStyle filterStyle = workbook.createCellStyle();
		
		 * filterStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		 * filterStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		 * filterStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		 * filterStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		 * filterStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		 * filterStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		 * filterStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		 * filterStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		 
		filterStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		filterStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

		XSSFCellStyle headerLabelStyle = workbook.createCellStyle();
		headerLabelStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		headerLabelStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		headerLabelStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		headerLabelStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headerLabelStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		headerLabelStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerLabelStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		headerLabelStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headerLabelStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerLabelStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);

		XSSFColor subGridColor = new XSSFColor(new Color(204, 255, 204));
		filterStyle.setFillForegroundColor(subGridColor);
		filterStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		XSSFCellStyle subGridHeader = workbook.createCellStyle();
		subGridHeader.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		subGridHeader.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		subGridHeader.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		subGridHeader.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		subGridHeader.setBorderRight(XSSFCellStyle.BORDER_THIN);
		subGridHeader.setRightBorderColor(IndexedColors.BLACK.getIndex());
		subGridHeader.setBorderTop(XSSFCellStyle.BORDER_THIN);
		subGridHeader.setTopBorderColor(IndexedColors.BLACK.getIndex());
		subGridHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		XSSFColor myColor = new XSSFColor(new Color(237, 237, 237));
		subGridHeader.setFillForegroundColor(myColor);

		XSSFCellStyle rows = workbook.createCellStyle();
		rows.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		rows.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		rows.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		rows.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		rows.setBorderRight(XSSFCellStyle.BORDER_THIN);
		rows.setRightBorderColor(IndexedColors.BLACK.getIndex());
		rows.setBorderTop(XSSFCellStyle.BORDER_THIN);
		rows.setTopBorderColor(IndexedColors.BLACK.getIndex());

		*//** Defining Fonts *//*
		XSSFFont font1 = workbook.createFont();
		font1.setFontHeightInPoints((short) 22);

		XSSFFont font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 16);

		XSSFFont font3 = workbook.createFont();
		font3.setFontHeightInPoints((short) 14);

		XSSFFont font4 = workbook.createFont();
		font3.setFontHeightInPoints((short) 10);

		XSSFCellStyle style1 = (XSSFCellStyle) workbook.createCellStyle();

		XSSFCellStyle style2 = (XSSFCellStyle) workbook.createCellStyle();

		int imgRow1 = 0;
		int imgRow2 = 4;
		int imgCol1 = 0;
		int imgCol2 = 0;

		int titleRow1 = 2;
		int titleRow2 = 5;
		int count = 0;
		rowCount = 2;
		colCount = 0;

		sheet.addMergedRegion(new CellRangeAddress(imgRow1, imgRow2, imgCol1, imgCol2));
		sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, titleRow1, titleRow2));

		titleRow = sheet.createRow(rowCount++);
		titleRow.setHeight((short) 500);

		cell = titleRow.createCell(2);
		// if(dynamicReportConfig.getId()==1){
		
		 * if (!StringUtil.isEmpty(dynamicReportConfig.getAreaWise()) &&
		 * dynamicReportConfig.getAreaWise().equalsIgnoreCase("1") &&
		 * !StringUtil.isEmpty(getBranchId()) &&
		 * !StringUtil.isEmpty(getUserAreaCode()) &&
		 * !getUserAreaCode().contains(",")) {
		 * cell.setCellValue(getLocalePropertyForMailExport(getLocaleProperty(
		 * dynamicReportConfig.getReport()+getUserAreaCode()), lang)); }else{
		 
		cell.setCellValue(getLocalePropertyForMailExport(dynamicReportConfig.getReport(), lang));
		 } 
		
		 * }else if(dynamicReportConfig.getId()==11){
		 * cell.setCellValue(dynamicReportConfig.getReport()); }else{
		 * cell.setCellValue(dynamicReportConfig.getReport()); }
		 
		font1.setBoldweight((short) 22);
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerStyle.setFont(font1);
		cell.setCellStyle(headerStyle);

		rowCount = 8;
		Map<String, DynamicReportConfigFilter> filterFieldMap = new LinkedHashMap<>();
		dynamicReportConfig.getDynmaicReportConfigFilters().stream().filter(p -> p.getStatus() != 0).forEach(df -> {
			if (df.getField().contains("~")) {
				filterFieldMap.put(df.getField().split("~")[0], df);
			} else {
				filterFieldMap.put(df.getField(), df);
			}
		});

		row = sheet.createRow(rowCount++);
		cell = row.createCell(4);
		cell.setCellValue(getLocalePropertyForMailExport("filter", lang));
		cell.setCellStyle(filterStyle);

		if (branchIdParma != null && !StringUtil.isEmpty(branchIdParma)) {
			row = sheet.createRow(rowCount++);
			cell = row.createCell(4);
			cell.setCellValue(getLocalePropertyForMailExport("app.branch", lang));
			cell.setCellStyle(rows);

			cell = row.createCell(5);
			cell.setCellValue(getBranchesMap().get(branchIdParma));
			cell.setCellStyle(rows);
		}

		if (!StringUtil.isEmpty(filterList)) {
			try {
				Type listType1 = new TypeToken<Map<String, String>>() {
				}.getType();
				Map<String, String> filtersList = new Gson().fromJson(filterList, listType1);
				if (filtersList.entrySet().stream()
						.anyMatch(filterFieldData -> (filterFieldMap.containsKey(filterFieldData.getKey())))) {

					filtersList.entrySet().stream()
							.filter(filterFieldData -> (filterFieldMap.containsKey(filterFieldData.getKey())))
							.forEach(filterFieldData -> {
								row = sheet.createRow(rowCount++);

								cell = row.createCell(4);
								cell.setCellValue(getLocalePropertyForMailExport(
										filterFieldMap.get(filterFieldData.getKey()).getLabel(), lang));
								cell.setCellStyle(rows);

								cell = row.createCell(5);
								if (filterFieldMap.get(filterFieldData.getKey()).getType() == 3) {
									Map<String, String> optMap = getOptions(
											filterFieldMap.get(filterFieldData.getKey()).getMethod());
									String optVal = filterFieldData.getValue();
									optVal = optVal.replaceAll("=", "").replaceAll("'", "");
									cell.setCellValue(optMap.get(Integer.valueOf(optVal.split("~")[1].trim())));
									cell.setCellStyle(rows);
								} else if (filterFieldMap.get(filterFieldData.getKey()).getType() == 4) {
									String dateOrg = filterFieldData.getValue().split("~")[1];
									dateOrg = dateOrg.replaceAll("between", "From");
									dateOrg = dateOrg.replace("and", "To");
									dateOrg.replaceAll("'", "");
									String datStr = DateUtil.convertDateFormat(dateOrg.split("\\|")[0].split(" ")[0],
											DateUtil.DATE, DateUtil.DATE_FORMAT_2) + "|"
											+ DateUtil.convertDateFormat(dateOrg.split("\\|")[1].split(" ")[0],
													DateUtil.DATE, DateUtil.DATE_FORMAT_2);
									cell.setCellValue(datStr);
									cell.setCellStyle(rows);

								} else if (filterFieldMap.get(filterFieldData.getKey()).getType() == 5) {
									Object[] parameter = null;
									Map<String, String> optMap = getQueryForFilters(
											filterFieldMap.get(filterFieldData.getKey()).getMethod(), parameter);
									String optVal = filterFieldData.getValue();
									if (optVal.contains("~")) {
										cell.setCellValue(optMap.get(optVal.split("~")[1].trim()));
									} else {
										optVal = optVal.replaceAll("=", "").replaceAll("'", "");
										cell.setCellValue(optMap.get(optVal.trim()));
									}
									cell.setCellStyle(rows);
								} else if (filterFieldMap.get(filterFieldData.getKey()).getType() == 10) {
									Object[] parameter = null;
									Map<String, String> optMap = getQueryForMultiSelectFilters(
											filterFieldMap.get(filterFieldData.getKey()).getMethod(), parameter);
									String optVal = filterFieldData.getValue();
									optVal = optVal.replaceAll("=", "").replaceAll("'", "");
									String aOtpVal = optVal.split("~")[1].trim();
									if (aOtpVal.contains(",")) {
										StringBuilder fVal = new StringBuilder();
										Arrays.asList(aOtpVal.split(",")).stream().forEach(x -> {
											fVal.append(optMap.get(x) + ",");
										});
										cell.setCellValue(StringUtil.removeLastComma(String.valueOf(fVal)));

									} else {
										cell.setCellValue(optMap.get(aOtpVal));
									}
									cell.setCellStyle(rows);
								}

							});
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 400);

		colCount = 0;
		Map<String, Double> totMap = new LinkedHashMap<>();

		dynamicReportConfig.getDynmaicReportConfigDetails().stream()
				.filter(f -> f.getIsGridAvailability() && f.getIsFooterSum() != null && f.getIsFooterSum().equals("1"))
				.sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder())).forEach(dy -> {
					totMap.put(dy.getLabelName() + "_" + dy.getOrder(), 0.0);
				});
		dynamicReportConfig.getDynmaicReportConfigDetails().stream()
				.filter(f -> f.getIsGridAvailability() && f.getIsFooterSum() != null && f.getIsFooterSum().equals("2"))
				.forEach(dy -> {
					tothead = String.valueOf(dy.getOrder());
				});

		 removing ID column config detail fro iterating 
		dynamicReportConfig.getDynmaicReportConfigDetails()
				.remove(dynamicReportConfig.getDynmaicReportConfigDetails().iterator().next());
		
		 * removing Branch Dynamic Report COnfig Detail From Iterating, s its
		 * value already added to rows
		 
		
		 * dynamicReportConfig.getDynmaicReportConfigDetails()
		 * .remove(dynamicReportConfig.getDynmaicReportConfigDetails().iterator(
		 * ).next());
		 
		cell = row.createCell(colCount++);
		cell.setCellValue(getLocalePropertyForMailExport("SNO", lang));
		font2.setBoldweight((short) 5);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		filterStyle.setFont(font2);
		 cell.setCellStyle(filterStyle); 
		cell.setCellStyle(filterStyle);
		
		 * if (IExporter.EXPORT_MANUAL.equalsIgnoreCase(exportType)) { if
		 * (StringUtil.isEmpty(getBranchId())) { cell =
		 * row.createCell(colCount++);
		 * cell.setCellValue(getLocalePropertyForMailExport("branchId", lang));
		 * font2.setBoldweight((short) 5);
		 * font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		 * filterStyle.setFont(font2); // cell.setCellStyle(filterStyle);
		 * cell.setCellStyle(filterStyle);
		 * 
		 * } } else { if (StringUtil.isEmpty(branchIdd)) { cell =
		 * row.createCell(colCount++);
		 * cell.setCellValue(getLocalePropertyForMailExport("branchId", lang));
		 * font2.setBoldweight((short) 5);
		 * font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		 * filterStyle.setFont(font2); //cell.setCellStyle(filterStyle);
		 * cell.setCellStyle(filterStyle); } }
		 

		if (StringUtil.isEmpty(getBranchId())) {
			cell = row.createCell(colCount++);
			cell.setCellValue(getLocalePropertyForMailExport("masterDT.Branch", lang));
			font2.setBoldweight((short) 5);
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			filterStyle.setFont(font2);
			cell.setCellStyle(filterStyle);
			cell = row.createCell(colCount++);
			cell.setCellValue(getLocalePropertyForMailExport("masterDT.area", lang));
			font2.setBoldweight((short) 5);
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			filterStyle.setFont(font2);
			cell.setCellStyle(filterStyle);
		}
		dynamicReportConfig.getDynmaicReportConfigDetails().stream().filter(config -> config.getIsExportAvailability())
				.sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder())).forEach(dynamicReportConfigDetail -> {
					cell = row.createCell(colCount++);
					String[] groupHeader;
					heading = "";
					if (!StringUtil.isEmpty(dynamicReportConfigDetail.getIsGroupHeader())) {
						groupHeader = dynamicReportConfigDetail.getIsGroupHeader().split("~");
						heading = groupHeader[2] + " "
								+ getLocalePropertyForMailExport(dynamicReportConfigDetail.getLabelName(), lang);
					} else {

						heading = getLocalePropertyForMailExport(dynamicReportConfigDetail.getLabelName(), lang);
					}
					cell.setCellValue(heading);
					font2.setBoldweight((short) 5);
					font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					filterStyle.setFont(font2);
					sheet.setColumnWidth(mainGridIterator, (15 * 550));
					mainGridIterator++;

					if (!StringUtil.isEmpty(dynamicReportConfigDetail.getIsFooterSum())
							&& dynamicReportConfigDetail.getIsFooterSum().equals("2")) {
						heade = colCount;
					}
					 cell.setCellStyle(filterStyle); 
					cell.setCellStyle(filterStyle);

				});
		sheet.setColumnWidth(mainGridIterator++, (15 * 550));
		sheet.setColumnWidth(mainGridIterator, (15 * 550));
		Map valMap = formValMap(dynamicReportConfig, branchIdd);
		mainGridRows.stream().forEach(arr -> {
			row = sheet.createRow(rowCount++);
			AtomicInteger col = new AtomicInteger(0);
			row.setHeight((short) 400);
			AtomicInteger colCount = new AtomicInteger(1);

			initializeMap(arr);
			cell = row.createCell(col.getAndIncrement());
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			cell.setCellStyle(style1);
			cell.setCellValue(serialNo++);
			branchId = getBranchId();
			StringBuilder exportParams = new StringBuilder("");

			dynamicReportConfig.getDynmaicReportConfigDetails().stream()
					.filter(config -> config.getIsExportAvailability())
					.sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder()))
					.forEach(dynamicReportConfigDetail -> {
						cell = row.createCell(col.getAndIncrement());

						String ansVal = getAnswer(dynamicReportConfigDetail, arr, colCount, valMap, lang, exportParams,
								true);
						colCount.incrementAndGet();
						exportParams.append("~" + ansVal);
						if (dynamicReportConfigDetail.getAlignment() != null
								&& !StringUtil.isEmpty(dynamicReportConfigDetail.getAlignment())) {
							if (dynamicReportConfigDetail.getAlignment().equalsIgnoreCase("center")) {
								style2.setAlignment(CellStyle.ALIGN_CENTER);
								cell.setCellStyle(style2);
							} else if (dynamicReportConfigDetail.getAlignment().equalsIgnoreCase("left")) {
								style2.setAlignment(CellStyle.ALIGN_LEFT);
								cell.setCellStyle(style2);
							} else if (dynamicReportConfigDetail.getAlignment().equalsIgnoreCase("right")) {
								style2.setAlignment(CellStyle.ALIGN_RIGHT);
								cell.setCellStyle(style2);
							}
						}

						if (!ObjectUtil.isEmpty(ansVal) && !StringUtil.isEmpty(ansVal)) {

							if (dynamicReportConfigDetail.getDataType() != null) {
								if (dynamicReportConfigDetail.getDataType() != null
										&& dynamicReportConfigDetail.getDataType().equalsIgnoreCase("2")) {
									ansVal = !StringUtil.isEmpty(ansVal.toString())
											? CurrencyUtil.getDecimalFormat(Double.valueOf(ansVal.toString()), "##.00")
											: "0.00";
									cell.setCellValue(ansVal);

								} else if (dynamicReportConfigDetail.getDataType() != null
										&& dynamicReportConfigDetail.getDataType().equalsIgnoreCase("3")) {
									ansVal = !StringUtil.isEmpty(ansVal.toString())
											? CurrencyUtil.getDecimalFormat(Double.valueOf(ansVal.toString()), "##.000")
											: "0.000";
									cell.setCellValue(ansVal);

								}

						else if (dynamicReportConfigDetail.getDataType() != null
								&& dynamicReportConfigDetail.getDataType().equalsIgnoreCase("0")) {
									ansVal = !StringUtil.isEmpty(ansVal.toString())
											? CurrencyUtil.getDecimalFormat(Double.valueOf(ansVal.toString()), "##")
											: "0";
									cell.setCellValue(ansVal);

								}

						else {
									cell.setCellValue(ansVal);
								}
							} else {
								cell.setCellValue(ansVal);
							}
						} else {
							cell.setCellValue("");
						}

					});

		});
		if (jss != null && !jss.isEmpty() && heade != null) {
			row = sheet.createRow(rowCount++);
			cell = row.createCell(heade - 1);
			cell.setCellValue("Total");
			font4.setBoldweight((short) 5);
			font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			filterStyle.setFont(font4);
			cell.setCellStyle(filterStyle);
			jss.keySet().stream().forEach(u -> {
				cell = row.createCell(heade++);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				// cell.setCellValue(u.getValue().toString());
				cell.setCellValue(jss.get(u).toString());
				font4.setBoldweight((short) 5);
				font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				filterStyle.setFont(font4);
				cell.setCellStyle(filterStyle);

			});

		}
		
		 * for (int i = 0; i <= colCount; i++) { sheet.autoSizeColumn(i); }
		 

		// alternateGreenAndWhiteRows(sheet);

		Drawing drawing = sheet.createDrawingPatriarch();
		int pictureIdx = getPicIndex(workbook);
		XSSFClientAnchor anchor = new XSSFClientAnchor(100, 150, 900, 100, (short) 0, 0, (short) 0, 4);
		anchor.setAnchorType(1);
		Picture picture = drawing.createPicture(anchor, pictureIdx);
		picture.resize();
		String makeDir = FileUtil.storeXls(id);
		if (IExporter.EXPORT_MANUAL.equalsIgnoreCase(exportType)) {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=" + fName);
			ServletOutputStream outStream = response.getOutputStream();
			workbook.write(outStream); // Write workbook to response.
			outStream.close();
		} else {

			FileOutputStream fileOut = new FileOutputStream(makeDir + fName);
			workbook.write(fileOut);
			InputStream stream = new FileInputStream(new File(makeDir + fName));
			fileOut.close();
			recData[1] = stream;
			// stream.close();
		}
		return recData;

	}*/

	public int getPicIndex(XSSFWorkbook workbook) throws IOException {

		int index = -1;

		byte[] picData = null;
		picData = utilService.findLogoByCode(Asset.APP_LOGO);

		if (picData != null)
			index = workbook.addPicture(picData, HSSFWorkbook.PICTURE_TYPE_JPEG);

		return index;
	}

		public String getMethodValuerp() {
			return methodValuerp;
		}

		public void setMethodValuerp(String methodValuerp) {
			this.methodValuerp = methodValuerp;
		}

		public HashMap<String, Object> getSortingPrintMap() {
			return sortingPrintMap;
		}

		public void setSortingPrintMap(HashMap<String, Object> sortingPrintMap) {
            this.sortingPrintMap = sortingPrintMap;
        }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }


    public void populateCSV() throws Exception {

        JSONObject jss = new JSONObject();
        jss.put("status", "1");
        if (getLoggedInDealer() > 0) {
            ExporterRegistration ex = utilService.findExportRegById(Long.valueOf(getLoggedInDealer()));
            setCsvFileName(ex.getRegNumber() + "_" + dynamicReportConfig.getCsvFile() + "_" + DateUtil.getCurrentYear() + "_" + csvfileNameDateFormat.format(new Date()) + ".csv");

        }
        String dirName = utilService.findPrefernceByName("FTP_DIRECTOTY");
        File directory = new File(dirName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File file = new File(dirName + csvFileName);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
			CSVWriter writer = new CSVWriter(outputfile, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);

            Map data = getExportData(true);
            dynamicReportConfig.getDynmaicReportConfigDetails()
                    .remove(dynamicReportConfig.getDynmaicReportConfigDetails().iterator().next());
            if (!StringUtil.isEmpty(getBranchId())) {
                dynamicReportConfig.getDynmaicReportConfigDetails()
                        .remove(dynamicReportConfig.getDynmaicReportConfigDetails().iterator().next());

            } else if (request.getSession() != null) {
                dynamicReportConfig.getDynmaicReportConfigDetails()
                        .remove(dynamicReportConfig.getDynmaicReportConfigDetails().iterator().next());
            }
            List<Object[]> mainGridRows = (List<Object[]>) data.get(ROWS);
            List<String> header = new ArrayList<>();
            dynamicReportConfig.getDynmaicReportConfigDetails().stream()
                    .filter(config -> config.getIsExportAvailability())
                    .sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder()))
                    .forEach(dynamicReportConfigDetail -> {
                        header.add(getLocaleProperty(dynamicReportConfigDetail.getLabelName()));

                    });
            writer.writeNext(header.stream().map(u -> String.valueOf(u)).toArray(String[]::new));


            Map<Long, Object> valMap = formValMap(dynamicReportConfig, getLoggedInUserLanguage());
            mainGridRows.stream().forEach(arr -> {
                List<String> valuue = new ArrayList<>();
				runCount = new AtomicInteger(2);
				dynamicReportConfig.getDynmaicReportConfigDetails().stream()
						.sorted((f1, f2) -> Long.compare(f1.getOrder(), f2.getOrder()))
						.forEach(dynamicReportConfigDetail -> {
							if (!dynamicReportConfigDetail.getIsExportAvailability()) {
								runCount.incrementAndGet();
							} else {
								valuue.add(String.valueOf(parseHTMLContent(getAnswer(dynamicReportConfigDetail, arr, runCount,
										valMap, getLoggedInUserLanguage(), new StringBuilder()))));
								runCount.incrementAndGet();
							}


						});
				writer.writeNext(valuue.stream().map(u -> String.valueOf(u)).toArray(String[]::new));

			});

			writer.close();
			Set<PosixFilePermission> perms = new HashSet<>();
			perms.add(PosixFilePermission.OTHERS_READ);
			file.setReadable(true);
			Files.setPosixFilePermissions(file.toPath(), perms);

		} catch (IOException e) {
            jss.put("status", "0");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sendAjaxResponse(jss);
/*

			setFileInputStream(new FileInputStream(file));

		return "csv";
*/

    }
    
    public String getExporterNames(String grpCodes) {
		String groupName = "";
		if (grpCodes != null) {
			String[] array = grpCodes.split(",");
			ArrayList<String> nameArr = new ArrayList<String>();
			for (int i = 0; i <= array.length - 1; i++) {
				if (array[i].trim() != null && !StringUtil.isEmpty(array[i].trim())) {
					ExporterRegistration wh = utilService.findExportRegById(Long.valueOf(array[i].trim()));
					nameArr.add(wh.getName());
				}
			}
			groupName = String.join(",", nameArr);
		}
		return groupName;
	}


}