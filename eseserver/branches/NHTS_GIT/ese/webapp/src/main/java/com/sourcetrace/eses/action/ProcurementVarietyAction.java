package com.sourcetrace.eses.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.sourcetrace.eses.BreadCrumb;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.ProcurementGrade;
import com.sourcetrace.eses.entity.ProcurementProduct;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.CurrencyUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class ProcurementVarietyAction extends SwitchValidatorAction {

	private static final long serialVersionUID = 1L;

	private ProcurementVariety procurementVariety;

	private ProcurementGrade procurementGrade;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String tabIndexVariety = "#tabs-2";

	@Getter
	@Setter
	private String tabIndex = "#tabs-1";

	@Getter
	@Setter
	private String procurementProductId;

	@Getter
	@Setter
	private String procurementProductCodeAndName;

	@Getter
	@Setter
	private String addingGradeEnabled;

	@Getter
	@Setter
	private String varietyYield;

	@Getter
	@Setter
	private String noDaysToGrow;

	@Getter
	@Setter
	private String harvestDays;

	@Getter
	@Setter
	private String varietyName;

	@Getter
	@Setter
	private String varietyCode;

	@Getter
	@Setter
	private String cropHScode;

	@Getter
	@Setter
	private String crop;

	@Autowired
	private IUniqueIDGenerator idGenerator;

	@Autowired
	protected IUtilService utilService;

	public String data() throws Exception {

		Map<String, String> searchRecord = getDataTableJQGridRequestParam(); // value

		ProcurementVariety filter = new ProcurementVariety();
		ProcurementProduct procurementProduct = new ProcurementProduct();
		ProcurementProduct ppObj = null;

		if (!StringUtil.isEmpty(searchRecord.get("code"))) {
			filter.setCode(searchRecord.get("code").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("varietyName"))) {
			filter.setName(searchRecord.get("varietyName").trim());
		}
		if (!StringUtil.isEmpty(searchRecord.get("cropHScode"))) {
			filter.setCropHScode(searchRecord.get("cropHScode").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("procurementProductId"))) {
			procurementProduct.setName(searchRecord.get("procurementProductId"));
			filter.setProcurementProduct(procurementProduct);
		}

		if (!StringUtil.isEmpty(this.getProcurementProductId())) {
			ppObj = utilService.findProcurementProductById(Long.valueOf(this.getProcurementProductId()));
			filter.setProcurementProduct(ppObj);
			if (!ObjectUtil.isEmpty(filter.getProcurementProduct())) {
				filter.getProcurementProduct().setId(Long.valueOf(this.getProcurementProductId()));
			}

		}
		Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
				getPage());

		return sendDataTableJSONResponse(data);
	}

	/**
	 * @see com.sourcetrace.esesw.view.SwitchAction#toJSON(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(Object obj) {

		ProcurementVariety variety = (ProcurementVariety) obj;
		JSONObject jsonObject = new JSONObject();
		JSONObject actionOnj = new JSONObject();
		JSONObject rows = new JSONObject();
		jsonObject.put("code", "<font color=\"#0000FF\" style=\"cursor:pointer;\">" + variety.getCode() + "</font>");

		if (!ObjectUtil.isEmpty(variety.getProcurementProduct())) {
			jsonObject.put("prodName", !ObjectUtil.isEmpty(variety.getProcurementProduct())
					? variety.getProcurementProduct().getName() : "");
		} else {
			jsonObject.put("prodName", !ObjectUtil.isEmpty(variety.getProcurementProduct())
					? variety.getProcurementProduct().getName() : "");
		}
		jsonObject.put("name", variety.getName());
		actionOnj.put("code", variety.getCode());
		actionOnj.put("name", variety.getName());
		actionOnj.put("cropHScode", variety.getCropHScode());
		// actionOnj.put("cropHScode", variety.getCropHScode());
		actionOnj.put("crop", variety.getProcurementProduct().getId());
		actionOnj.put("id", variety.getId());
		jsonObject.put("cropHScode", variety.getCropHScode());
		jsonObject.put("edit",
				"<a href='#' onclick='ediFunctionV(\"" + StringEscapeUtils.escapeJavaScript(actionOnj.toString())
						+ "\")' class='fa fa-edit' title='Edit' ></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='deletVariety(\""
						+ variety.getId() + "\")' class='fa fa-trash' title='Delete' ></a>");
		rows.put("DT_RowId", variety.getId());
		rows.put("cell", jsonObject);
		return rows;
	}

	@SuppressWarnings("unchecked")
	public void create() throws Exception {
		getJsonObject().clear();
		if (!StringUtil.isEmpty(varietyName)) {
			procurementVariety = new ProcurementVariety();
			if (!StringUtil.isEmpty(this.getProcurementProductId())) {
				procurementVariety.setProcurementProduct(
						utilService.findProcurementProductById(Long.valueOf(this.getProcurementProductId())));
			}
			procurementVariety.setName(varietyName);
			procurementVariety.setCropHScode(cropHScode);
			procurementVariety.setBranchId(getBranchId());
			
			procurementVariety.setCreatedUser(getUsername());
			procurementVariety.setCreatedDate(new Date());
			
			// procurementVariety.setCode(idGenerator.getProcurementVarietyIdSeq());
			procurementVariety.setCode(varietyCode);
			ProcurementVariety pv = utilService.findProcurementVariertyByNameAndCropId(varietyName,
					Long.valueOf(procurementProductId));
			if (!ObjectUtil.isEmpty(pv) && !ObjectUtil.isEmpty(procurementVariety.getProcurementProduct())) {
				if (pv.getProcurementProduct().getId() == procurementVariety.getProcurementProduct().getId()) {
					getJsonObject().put("msg", getText("cropName.alreadyExists"));
					getJsonObject().put("title", getText("title.error"));
				}
			}
			ProcurementVariety pvCode = utilService.findProcurementVariertyByCode(varietyCode);
			if (pvCode != null) {
				if (getJsonObject().containsKey("msg")) {

					getJsonObject().put("msg", getJsonObject().get("msg") + "<br>" + getText("cropcatCode.exist"));
					getJsonObject().put("title", getText("title.error"));
				} else {
					getJsonObject().put("msg", getText("cropcatCode.exist"));
					getJsonObject().put("title", getText("title.error"));
				}
			}
			if (!getJsonObject().containsKey("msg")) {
				utilService.addProcurementVariety(procurementVariety);

				getJsonObject().put("msg", getText("msg.varietyAdded"));
				getJsonObject().put("title", getText("title.success"));
			}

			sendAjaxResponse(getJsonObject());

		}
	}

	@SuppressWarnings("unchecked")
	public void update() throws Exception {

		if (!StringUtil.isEmpty(id)) {
			ProcurementVariety temp = utilService.findProcurementVarietyById(Long.valueOf(id));
			temp.setName(varietyName);
			temp.setCropHScode(cropHScode);
			temp.setUpdatedUser(getUsername());
			temp.setUpdatedDate(new Date());

			if (!StringUtil.isEmpty(varietyName) && !StringUtil.isEmpty(cropHScode)) {

				utilService.editProcurementVariety(temp);

				getJsonObject().put("msg", getText("msg.varityUpdated"));
				getJsonObject().put("title", getText("title.success"));

			} else {
				getJsonObject().put("msg", getText("Please Enter Variety Name"));
				getJsonObject().put("title", getText("Error"));

			}

			sendAjaxResponse(getJsonObject());
		}
	}

	@SuppressWarnings("unchecked")
	public void populateDelete() throws Exception {

		if (id != null) {
			procurementVariety = utilService.findProcurementVarietyById(Long.valueOf(id));
			if (!ObjectUtil.isEmpty(procurementVariety)) {

				List<ProcurementGrade> procurementGradeList = utilService
						.listProcurementGradeByProcurementVarietyId(Long.valueOf(id));

				/*
				 * if (!ObjectUtil.isEmpty(procurementVariety)) {
				 * 
				 * 
				 * 
				 * utilService.remove(procurementVariety);
				 * getJsonObject().put("msg", getText("varietymsg.deleted"));
				 * getJsonObject().put("title", getText("title.success"));
				 * 
				 * 
				 * }
				 */

				if (!ObjectUtil.isListEmpty(procurementGradeList)) {
					getJsonObject().put("msg", getText("procurementdeletevar.warn"));
					getJsonObject().put("title", getText("title.error"));
				} else {

					utilService.remove(procurementVariety);
					getJsonObject().put("msg", getText("varietymsg.deleted"));
					getJsonObject().put("title", getText("title.success"));
				}

			}
			sendAjaxResponse(getJsonObject());
		}
	}

	public void populateCrop() throws Exception {

		List<ProcurementProduct> procurementProdList = utilService.listProcurementProduct();
		JSONArray crop = new JSONArray();
		if (!ObjectUtil.isEmpty(procurementProdList)) {
			// JSONObject crop=new JSONObject();
			// if (!ObjectUtil.isEmpty(procurementProdList)) {
			for (ProcurementProduct ProcurementProduct : procurementProdList) {
				crop.add(getJSONObject(ProcurementProduct.getId(), ProcurementProduct.getName()));
			}
		}
		sendAjaxResponse(crop);
	}

	@SuppressWarnings("unchecked")
	protected JSONObject getJSONObject(Object id, Object name) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("name", name);
		return jsonObject;
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

	public String getProcurementDetailParams() {

		return "tabIndex=" + URLEncoder.encode(tabIndexVariety) + "&id=" + getProcurementProductId() + "&"
				+ tabIndexVariety;
	}

	@Override
	public Object getData() {

		return procurementVariety;
	}

	public void prepare() throws Exception {

		String procurementProductId = (String) request.getParameter("procurementProductEnrollId");

		if (StringUtil.isEmpty(procurementProductId)
				&& !StringUtil.isEmpty(request.getSession().getAttribute("uniqueProcurementProductId"))) {
			procurementProductId = request.getSession().getAttribute("uniqueProcurementProductId").toString();
		}
		String actionClassName = ActionContext.getContext().getActionInvocation().getAction().getClass()
				.getSimpleName();
		String content = getLocaleProperty(actionClassName + "." + BreadCrumb.BREADCRUMB);
		if (StringUtil.isEmpty(content) || (content.equalsIgnoreCase(actionClassName + "." + BreadCrumb.BREADCRUMB))) {
			content = super.getText(BreadCrumb.BREADCRUMB, "");
		}
		request.setAttribute(BreadCrumb.BREADCRUMB,
				BreadCrumb.getBreadCrumb(content + procurementProductId + "&tabValue=tabs-2"));
		/*
		 * request.setAttribute(BreadCrumb.BREADCRUMB, BreadCrumb.getBreadCrumb(
		 * getText(BreadCrumb.BREADCRUMB, "") + procurementProductId +
		 * "&tabValue=tabs-2"));
		 */
	}

	private double getDoubleValueFromString(String str1, String str2) {

		double toDoubleValue = 0d;
		try {

			StringBuffer sb = new StringBuffer();
			if (!StringUtil.isEmpty(str1)) {
				sb.append(str1);
			}
			if (!StringUtil.isEmpty(str2)) {
				sb.append(".");
				sb.append(str2);
			}
			if (!StringUtil.isEmpty(sb.toString())) {
				toDoubleValue = Double.valueOf(sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toDoubleValue;
	}
}
