/*
 * ProcurementGradeAction.java
 * Copyright (c) 2015-2016, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.ProcurementGrade;
import com.sourcetrace.eses.entity.ProcurementProduct;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class ProcurementGradeAction extends SwitchValidatorAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IFarmerService farmerService;

	@Autowired
	protected IUtilService utilService;

	private ProcurementGrade procurementGrade;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String tabIndexVariety = "#tabs-2";

	@Getter
	@Setter
	private String procurementVarietyId;

	@Getter
	@Setter
	private String procurementVarietyCodeAndName;

	@Getter
	@Setter
	private String procurementProductCodeAndName;

	@Getter
	@Setter
	private String gradePrice;

	@Getter
	@Setter
	private String tabIndex = "#tabs-1";

	@Getter
	@Setter
	private String selectedUom = "mm";

	@Getter
	@Setter
	private String gradeName;

	@Getter
	@Setter
	private String gradeCode;

	@Getter
	@Setter
	private List<ProcurementVariety> procurementVarietyList = new ArrayList<>();

	@Getter
	@Setter
	private String selectedUnit;

	@Getter
	@Setter
	private String procurementProductId;

	private List<FarmCatalogue> subUomList;

	@Autowired
	private IUniqueIDGenerator idGenerator;

	@Getter
	@Setter
	private String ccycle;

	@Getter
	@Setter
	private Double yieldkg;

	@Getter
	@Setter
	private String harvestday;
	
	@Getter
	@Setter
	private String cropHScode;

	@Getter
	@Setter
	private String crop1;
	
	/**
	 * @see com.sourcetrace.esesw.view.SwitchAction#data()
	 */
	public String data() throws Exception {

		Map<String, String> searchRecord = getDataTableJQGridRequestParam(); // value
																				// //
																				// get
																				// the
		// search
		// parameter
		// with
		// value

		ProcurementGrade filter = new ProcurementGrade();
		ProcurementVariety procurementvarietyObject = null;
		ProcurementVariety procurementVariety = new ProcurementVariety();
		ProcurementProduct procurementProduct = new ProcurementProduct();

		/*if (!StringUtil.isEmpty(searchRecord.get("code"))) {
			filter.setCode(searchRecord.get("code").trim());
		}*/

		if (!StringUtil.isEmpty(searchRecord.get("gradeName"))) {
			filter.setName(searchRecord.get("gradeName").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("procurementVarietyId"))) {
			procurementVariety.setName(searchRecord.get("procurementVarietyId"));
			filter.setProcurementVariety(procurementVariety);

		}
		
		if (!StringUtil.isEmpty(searchRecord.get("cropHScode"))) {
			filter.setCropHScode(searchRecord.get("cropHScode").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("gradePrice"))) {
			filter.setPrice(Double.valueOf(searchRecord.get("gradePrice").trim()));
		}

		if (!StringUtil.isEmpty(searchRecord.get("procurementProductId"))) {

			procurementProduct.setName(searchRecord.get("procurementProductId"));

			procurementVariety.setProcurementProduct(procurementProduct);
			filter.setProcurementVariety(procurementVariety);
		}

		if (!StringUtil.isEmpty(searchRecord.get("cropCycle"))) {
			filter.setCropCycle(searchRecord.get("cropCycle").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("yield"))) {
			filter.setYield(Double.valueOf(searchRecord.get("yield").trim()));
		}

		if (!StringUtil.isEmpty(searchRecord.get("harvestDays"))) {
			filter.setHarvestDays(searchRecord.get("harvestDays").trim());
		}

		if (!StringUtil.isEmpty(this.getProcurementVarietyId())) {
			procurementvarietyObject = utilService
					.findProcurementVarietyById(Long.valueOf(this.getProcurementVarietyId()));
			filter.setProcurementVariety(procurementvarietyObject);
			if (!ObjectUtil.isEmpty(filter.getProcurementVariety())) {
				filter.getProcurementVariety().setId(Long.valueOf(this.getProcurementVarietyId()));
			}

		}

		Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
				getPage());

		return sendDataTableJSONResponse(data);
	}

	/**
	 * @see com.sourcetrace.esesw.view.SwitchAction#toJSON(java.lang.Object)
	 */
	public JSONObject toJSON(Object obj) {

		ProcurementGrade variety = (ProcurementGrade) obj;
		JSONObject jsonObject = new JSONObject();
		JSONObject actionOnj = new JSONObject();
		JSONObject rows = new JSONObject();
		/*jsonObject.put("code", "<font color=\"#0000FF\" style=\"cursor:pointer;\">" + variety.getCode() + "</font>");*/

		if (!ObjectUtil.isEmpty(variety.getProcurementVariety().getProcurementProduct())) {
			jsonObject.put("prodName", !ObjectUtil.isEmpty(variety.getProcurementVariety().getProcurementProduct())
					? variety.getProcurementVariety().getProcurementProduct().getName() : "");
		} else {
			jsonObject.put("prodName", !ObjectUtil.isEmpty(variety.getProcurementVariety().getProcurementProduct())
					? variety.getProcurementVariety().getProcurementProduct().getName() : "");
		}
		
		if (!ObjectUtil.isEmpty(variety.getProcurementVariety())) {
			jsonObject.put("variety", !ObjectUtil.isEmpty(variety.getProcurementVariety())
					? variety.getProcurementVariety().getName() : "");
		} else {
			jsonObject.put("variety", !ObjectUtil.isEmpty(variety.getProcurementVariety())
					? variety.getProcurementVariety().getName() : "");
		}
		jsonObject.put("name", variety.getName());
		jsonObject.put("cropHScode", variety.getCropHScode());
		actionOnj.put("cropHScode", variety.getCropHScode());
		/*actionOnj.put("code", variety.getCode());*/
		actionOnj.put("name", variety.getName());
		actionOnj.put("variety", variety.getProcurementVariety().getId());
		actionOnj.put("crop", variety.getProcurementVariety().getProcurementProduct().getId());
		actionOnj.put("cropCycle", variety.getCropCycle());
		actionOnj.put("yield", variety.getYield());
		actionOnj.put("harvestDays", variety.getHarvestDays());
		jsonObject.put("cropCycle", variety.getCropCycle());
		jsonObject.put("yield", variety.getYield());
		jsonObject.put("harvestDays", variety.getHarvestDays());

		actionOnj.put("id", variety.getId());
		if (request.getSession().getAttribute("roleId") != null
				&& request.getSession().getAttribute("roleId").equals(2L)) {
		jsonObject.put("audit","<button class='fa fa-info' aria-hidden='true' onclick='detailPopupG(\""
				 + variety.getId() + "\")'></button>");
		}else{
			jsonObject.put("audit","No Access");
		}
		jsonObject.put("edit",
				"<a href='#' onclick='ediFunctionG(\"" + StringEscapeUtils.escapeJavaScript(actionOnj.toString())
						+ "\")' class='fa fa-edit' title='Edit' ></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='deleteGrade(\""
						+ variety.getId() + "\")' class='fa fa-trash' title='Delete' ></a>");
		rows.put("DT_RowId", variety.getId());
		rows.put("cell", jsonObject);
		return rows;
	}

	/**
	 * @see com.sourcetrace.esesw.view.SwitchValidatorAction#getData()
	 */
	@Override
	public Object getData() {

		return procurementGrade;
	}

	/**
	 * Creates the.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public void create() throws Exception {
		getJsonObject().clear();
		if (!StringUtil.isEmpty(gradeName)) {
			procurementGrade = new ProcurementGrade();
			procurementGrade.setCode(idGenerator.getProcurementGradeIdSeq());
			procurementGrade.setName(gradeName);
			procurementGrade.setCropCycle(ccycle);
			procurementGrade.setCropHScode(cropHScode);
			procurementGrade.setYield(yieldkg);
			procurementGrade.setHarvestDays(harvestday);
			procurementGrade.setCreatedUser(getUsername());
			procurementGrade.setCreatedDate(new Date());
			// procurementGrade.processPrice();
			if (!StringUtil.isEmpty(this.getProcurementVarietyId())) {
				procurementGrade.setProcurementVariety(
						utilService.findProcurementVarietyById(Long.valueOf(this.getProcurementVarietyId())));
			}
			ProcurementGrade pg = utilService.findProcurementGradeByNameAndVarietyId(gradeName,
					Long.valueOf(procurementVarietyId));
			ProcurementGrade pgCode = utilService.findProcurementGradeByCode(gradeCode);
			if (!ObjectUtil.isEmpty(pg) && !ObjectUtil.isEmpty(pg.getProcurementVariety())) {
				if (pg.getProcurementVariety().getId() == Long.valueOf(this.getProcurementVarietyId())) {
					getJsonObject().put("msg", getText("cropVariety.exist"));
					getJsonObject().put("title", getText("title.error"));
				}
			}
			if (pgCode != null) {
				if (getJsonObject().containsKey("msg")) {
					getJsonObject().put("msg", getJsonObject().get("msg") + "<br>" + getText("cropcatCode.exist"));
					getJsonObject().put("title", getText("title.error"));
				} else {
					getJsonObject().put("msg", getText("cropcatCode.exist"));
					getJsonObject().put("title", getText("title.error"));
				}
			}
			if (!getJsonObject().containsKey("msg")) {
				utilService.addProcurementGrade(procurementGrade);
				getJsonObject().put("msg", getText("msg.gradeAdded"));
				getJsonObject().put("title", getText("title.success"));
			}
			// procurementGrade.setCode(idGenerator.getProcurementGradeIdSeq());
			sendAjaxResponse(getJsonObject());
		}
	}

	/**
	 * Update.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public void update() throws Exception {

		if (id != null) {
			ProcurementGrade temp = utilService.findProcurementGradeById(Long.valueOf(id));

			if (!StringUtil.isEmpty(gradeName) && !StringUtil.isEmpty(cropHScode)) {

				/*
				 * ProcurementGradePricingHistory procurementGradePricingHistory
				 * = new ProcurementGradePricingHistory();
				 * procurementGradePricingHistory.setProcurementGrade(temp);
				 * procurementGradePricingHistory.setPrice(Double.valueOf(
				 * gradePrice)); utilService.addprocurementGradePricingHistory(
				 * procurementGradePricingHistory);
				 */
				temp.setProcurementVariety(
						utilService.findProcurementVarietyById(Long.valueOf(this.getProcurementVarietyId())));
				temp.setName(gradeName);
				temp.setCropCycle(ccycle);
				temp.setCropHScode(cropHScode);
				temp.setYield(yieldkg);
				temp.setHarvestDays(harvestday);
				temp.setUpdatedUser(getUsername());
				temp.setUpdatedDate(new Date());
				utilService.editProcurementGrade(temp);
				getJsonObject().put("msg", getText("msg.gradeUpdated"));
				getJsonObject().put("title", getText("title.success"));
			} else {
				getJsonObject().put("msg", getText("Please Enter Grade Name"));
				getJsonObject().put("title", getText("Error"));
			}

		}

		sendAjaxResponse(getJsonObject());
	}

	/**
	 * Detail.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 *//*
		 * public String detail() throws Exception {
		 * 
		 * String view = ""; if (id != null && !id.equals("")) {
		 * procurementGrade =
		 * productDistributionService.findProcurementGradeById(Long.valueOf(id))
		 * ; if (procurementGrade == null) { addActionError(NO_RECORD); return
		 * REDIRECT; } this.gradePrice =
		 * DoubleUtil.getString(procurementGrade.getPrice());
		 * setCurrentPage(getCurrentPage()); command = UPDATE; view = DETAIL;
		 * request.setAttribute(HEADING, getText(DETAIL)); } else {
		 * request.setAttribute(HEADING, getText(LIST)); return REDIRECT; }
		 * return view; }
		 */

	@SuppressWarnings("unchecked")
	public void populateDelete() throws Exception {

		if (id != null) {
			procurementGrade = utilService.findProcurementGradeById(Long.valueOf(id));

			if (!ObjectUtil.isEmpty(procurementGrade)) {

				//FarmCrops fc = (FarmCrops) farmerService.findObjectById("from FarmCrops fc where fc.grade.id=? and fc.status=1" , new Object[]{procurementGrade.getId()});
				Planting fc = (Planting) farmerService.findObjectById("from Planting fc where fc.grade.id=? and fc.status=1" , new Object[]{procurementGrade.getId()});
				if(fc==null){
					 utilService.remove(procurementGrade);
					 getJsonObject().put("msg", getText("grademsg.deleted"));
						getJsonObject().put("title", getText("title.success"));
						
				}else{
					getJsonObject().put("msg", getText("procurementdeletegrad.warn"));
					getJsonObject().put("title", getText("title.error"));
				}
				
				// getJsonObject().put("msg", getText("grademsg.deleted"));
				// getJsonObject().put("title", getText("title.success"));
				// }

			}
			sendAjaxResponse(getJsonObject());
		}

	}

	/**
	 * Gets the procurement detail params.
	 * 
	 * @return the procurement detail params
	 */
	public String getProcurementDetailParams() {

		return "tabIndex=" + URLEncoder.encode(tabIndexVariety) + "&id=" + getProcurementVarietyId() + "&"
				+ tabIndexVariety;
	}

	public List<FarmCatalogue> getListUom() {
		List<FarmCatalogue> subUomList = utilService.listCataloguesByUnit();

		return subUomList;
	}

	public void populateProcurementVariety() {
		List<ProcurementVariety> varietyList = utilService.listProcurementVariety();

		JSONObject jsonObject = new JSONObject();
		varietyList.forEach(procurementVariety -> jsonObject.put(String.valueOf(procurementVariety.getId()),
				procurementVariety.getName()));
		try {
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.print(jsonObject.toString().replace("\"", "").replace(",", ";"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void populateUnit() {
		subUomList = utilService.listCataloguesByUnit();

		JSONObject jsonObject = new JSONObject();
		subUomList.forEach(unit -> jsonObject.put(String.valueOf(unit.getCode()), unit.getName()));
		try {
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.print(jsonObject.toString().replace("\"", "").replace(",", ";"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void populateProcurementProduct() {
		List<ProcurementProduct> procurementProdList = utilService.listProcurementProduct();

		JSONObject jsonObject = new JSONObject();
		procurementProdList.forEach(procurementProduct -> jsonObject.put(String.valueOf(procurementProduct.getId()),
				procurementProduct.getName()));
		try {
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.print(jsonObject.toString().replace("\"", "").replace(",", ";"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void populateVariety() {
		JSONArray varietyArr = new JSONArray();
		if (!StringUtil.isEmpty(procurementProductId)) {
			List<Object[]> varietyList = utilService
					.listProcurementProductByVariety(Long.valueOf(procurementProductId));

			if (!ObjectUtil.isEmpty(varietyList)) {
				for (Object[] variety : varietyList) {
					varietyArr.add(getJSONObject(String.valueOf(variety[0]),String.valueOf(variety[2])));
				}
			}
			sendAjaxResponse(varietyArr);
		}
	}

	@SuppressWarnings("unchecked")
	protected JSONObject getJSONObject(Object id, Object name) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("name", name);
		return jsonObject;
	}
	
	public String populateDetail() {
		if(id!=null) {
			crop1="grade";
			List<Object[]> Datas = farmerService.findProcurementProductDetailById(Long.valueOf(id),crop1);
			List<JSONObject> jsonObjects = new ArrayList<JSONObject>();

			JSONObject jsonObj = new JSONObject();
			previousObje = null;
			Datas.stream().forEach(da -> {
				JSONArray jsonArr = new JSONArray();

				if (previousObje == null || !ObjectUtil.isEquals(da, previousObje)) {

					for (i = 0; i < da.length; i++) {
						if (da[i] != null) {
							jsonArr.add(i, da[i].toString());
						} else {
							jsonArr.add(i, "");
						}
					}
					jsonObj.put(j, jsonArr);
					j++;
					previousObje = da;
				}

			});
			printAjaxResponse(jsonObj, "text/html");

		} 
		return null;
	}
}
