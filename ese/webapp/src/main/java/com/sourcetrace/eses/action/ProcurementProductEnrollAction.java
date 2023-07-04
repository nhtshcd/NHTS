package com.sourcetrace.eses.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.BlockDetails;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCatalogue;
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
public class ProcurementProductEnrollAction extends SwitchValidatorAction {

	private static final long serialVersionUID = 1L;

	private ProcurementProduct procurementProduct;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private File crop;

	@Getter
	@Setter
	private String cropName;

	@Getter
	@Setter
	private String cropCode;

	@Getter
	@Setter
	private String speciesName;

	@Getter
	@Setter
	private String unit;

	@Getter
	@Setter
	private String cropCategory;

	@Getter
	@Setter
	private String mspRate;

	@Getter
	@Setter
	private String mspPercentage;

	@Getter
	@Setter
	private String selectedVariety;

	@Getter
	@Setter
	private List<File> files;

	@Getter
	@Setter
	private List<String> filesContentType;

	@Getter
	@Setter
	private List<String> filesFileName;

	@Getter
	@Setter
	private List<File> cropDocuments;

	@Getter
	@Setter
	private List<String> cropDocumentsContentType;

	@Getter
	@Setter
	private List<String> cropDocumentsFileName;

	@Getter
	@Setter
	private String cropDocumentsProcurementProductCode;

	@Getter
	@Setter
	private String productCode;

	@Getter
	@Setter
	private String documentUploadId;

	@Getter
	@Setter
	private String advisory;

	@Autowired
	private IUniqueIDGenerator idGenerator;

	@Autowired
	protected IUtilService utilService;

	private List<FarmCatalogue> subUomList = new ArrayList<FarmCatalogue>();
	private Map<String, String> cropCatList = new HashMap<String, String>();

	@Getter
	@Setter
	private String crop1;

	/**
	 * @see com.sourcetrace.esesw.view.SwitchAction#data()
	 */
	@SuppressWarnings("unchecked")
	public String data() throws Exception {

		Map<String, String> searchRecord = getDataTableJQGridRequestParam();

		ProcurementProduct filter = new ProcurementProduct();

		Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
				getPage());

		return sendDataTableJSONResponse(data);
	}

	/**
	 * @see com.sourcetrace.esesw.view.SwitchAction#toJSON(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(Object obj) {

		ProcurementProduct product = (ProcurementProduct) obj;
		JSONObject jsonObject = new JSONObject();
		JSONObject actionOnj = new JSONObject();
		JSONObject rows = new JSONObject();
		if ((getIsMultiBranch().equalsIgnoreCase("1")
				&& (getIsParentBranch().equals("1") || StringUtil.isEmpty(branchIdValue)))) {

			if (StringUtil.isEmpty(branchIdValue)) {
				jsonObject.put("branchId",
						!StringUtil.isEmpty(getBranchesMap().get(getParentBranchMap().get(product.getBranchId())))
								? getBranchesMap().get(getParentBranchMap().get(product.getBranchId()))
								: getBranchesMap().get(product.getBranchId()));
			}
			jsonObject.put("branchId", getBranchesMap().get(product.getBranchId()));

		} else {
			if (StringUtil.isEmpty(branchIdValue)) {
				jsonObject.put("branchId", branchesMap.get(product.getBranchId()));
			}
		}

		jsonObject.put("code", "<font color=\"#0000FF\" style=\"cursor:pointer;\">" + product.getCode() + "</font>");
		jsonObject.put("name", product.getName());
		if (product.getSpeciesName() != null && !StringUtil.isEmpty(product.getSpeciesName())) {
			jsonObject.put("speciesName", getCatlogueValueByCode(product.getSpeciesName()).getName());
			jsonObject.put("speciesCode", product.getSpeciesName());
		} else {
			jsonObject.put("speciesName", "");
		}
		// jsonObject.put("unit", getCatalgueNameByCode(product.getUnit()));
		actionOnj.put("code", product.getCode());
		actionOnj.put("name", product.getName());
		if (product.getSpeciesName() != null && !StringUtil.isEmpty(product.getSpeciesName())) {
			actionOnj.put("speciesName", getCatlogueValueByCode(product.getSpeciesName()).getName());
			actionOnj.put("speciesCode", product.getSpeciesName());
		} else {
			actionOnj.put("speciesName", "");
			actionOnj.put("speciesCode", "");
		}
		// actionOnj.put("speciesName", product.getSpeciesName());
		actionOnj.put("id", product.getId());
		// actionOnj.put("unit", product.getUnit());
		if (request.getSession().getAttribute("roleId") != null
				&& request.getSession().getAttribute("roleId").equals(2L)) {
			jsonObject.put("audit", "<button class='fa fa-info' aria-hidden='true' onclick='detailPopup(\""
					+ product.getId() + "\")'></button>");
		} else {
			jsonObject.put("audit", "No Access");
		}
		jsonObject.put("edit",
				"<a href='#' onclick='ediFunction(\"" + StringEscapeUtils.escapeJavaScript(actionOnj.toString())
						+ "\")' class='fa fa-edit' title='Edit' ></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='deletProd(\""
						+ product.getId() + "\")' class='fa fa-trash' title='Delete' ></a>");
		rows.put("DT_RowId", product.getId());
		rows.put("cell", jsonObject);
		return rows;
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
		// if (!StringUtil.isEmpty(cropName) && !StringUtil.isEmpty(unit))
		if (!StringUtil.isEmpty(cropName)) {
			procurementProduct = new ProcurementProduct();
			if (!getCurrentTenantId().equalsIgnoreCase("indong")) {
				// procurementProduct.setCode(idGenerator.getProductEnrollIdSeq());
				procurementProduct.setCode(cropCode);
			}
			procurementProduct.setName(cropName);
			procurementProduct.setSpeciesName(speciesName);
			// procurementProduct.setUnit(unit);
			procurementProduct.setBranchId(getBranchId());
			procurementProduct.setCreatedUser(getUsername());
			procurementProduct.setCreatedDate(new Date());
			ProcurementProduct eProcurementProduct = utilService.findProcurementProductByName(cropName);
			ProcurementProduct eProcurementCode = utilService.findProcurementProductByCode(cropCode);
			if (eProcurementProduct != null) {
				getJsonObject().put("msg", getText("cropCat.exist"));
				getJsonObject().put("title", getText("title.error"));
			}
			if (eProcurementCode != null) {
				if (getJsonObject().containsKey("msg")) {

					getJsonObject().put("msg", getJsonObject().get("msg") + "<br>" + getText("cropcatCode.exist"));
					getJsonObject().put("title", getText("title.error"));
				} else {
					getJsonObject().put("msg", getText("cropcatCode.exist"));
					getJsonObject().put("title", getText("title.error"));
				}
			}
			if (!getJsonObject().containsKey("msg")) {
				utilService.addProcurementProduct(procurementProduct);
				getJsonObject().put("msg", getText("msg.cropAdded"));
				getJsonObject().put("title", getText("title.success"));
			}
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
		if (!StringUtil.isEmpty(id)) {
			ProcurementProduct temp = utilService.findProcurementProductById(Long.valueOf(id));

			if (!StringUtil.isEmpty(cropName)) {
				ProcurementProduct epp = utilService.findProcurementProductByName(cropName);
				if (epp != null && temp != null && temp.getId() != epp.getId()

				) {
					getJsonObject().put("msg", getText("cropCat.exist"));
					getJsonObject().put("title", getText("title.error"));
				} else {
					temp.setName(cropName);
					temp.setSpeciesName(speciesName);
					// temp.setUnit(unit);
					temp.setUpdatedUser(getUsername());
					temp.setUpdatedDate(new Date());
					utilService.editProcurementProduct(temp);

					getJsonObject().put("msg", getText("msg.cropUpdated"));
					getJsonObject().put("title", getText("title.success"));
				}

			} else {
				getJsonObject().put("msg", getText("Please Enter Crop Name"));
				getJsonObject().put("title", getText("Error"));
			}

			sendAjaxResponse(getJsonObject());

		}

	}

	/*
	 * @SuppressWarnings("unchecked") public void update() throws Exception { if
	 * (!StringUtil.isEmpty(id)) { ProcurementProduct temp =
	 * utilService.findProcurementProductById(Long.valueOf(id));
	 * 
	 * if (!StringUtil.isEmpty(cropName)) {
	 * 
	 * temp.setName(cropName); temp.setSpeciesName(speciesName); //
	 * temp.setUnit(unit); temp.setUpdatedUser(getUsername());
	 * temp.setUpdatedDate(new Date());
	 * utilService.editProcurementProduct(temp);
	 * 
	 * getJsonObject().put("msg", getText("msg.cropUpdated"));
	 * getJsonObject().put("title", getText("title.success")); } else {
	 * getJsonObject().put("msg", getText("Please Enter Crop Name"));
	 * getJsonObject().put("title", getText("Error")); }
	 * 
	 * sendAjaxResponse(getJsonObject());
	 * 
	 * }
	 * 
	 * }
	 */
	@SuppressWarnings("unchecked")
	public void populateDelete() throws Exception {
		if (id != null) {
			procurementProduct = utilService.findProcurementProductById(Long.parseLong(id));

			if (!ObjectUtil.isEmpty(procurementProduct)) {

				List<ProcurementVariety> procurementVarietyList = utilService
						.listProcurementVarietyByProcurementProductId(Long.valueOf(id));

				if (!ObjectUtil.isListEmpty(procurementVarietyList)) {
					getJsonObject().put("msg", getText("procurementdeleteCrop.warn"));
					getJsonObject().put("title", getText("title.error"));
				} else {

					utilService.remove(procurementProduct);
					getJsonObject().put("msg", getText("cropmsg.deleted"));
					getJsonObject().put("title", getText("title.success"));
				}
			}

			sendAjaxResponse(getJsonObject());
		}
	}

	/**
	 * @see com.sourcetrace.esesw.view.SwitchValidatorAction#getData()
	 */
	public Object getData() {

		return procurementProduct;
	}

	/**
	 * Sets the procurement product.
	 * 
	 * @param procurementProduct
	 *            the new procurement product
	 */
	public void setProcurementProduct(ProcurementProduct procurementProduct) {

		this.procurementProduct = procurementProduct;
	}

	/**
	 * Gets the procurement product.
	 * 
	 * @return the procurement product
	 */
	public ProcurementProduct getProcurementProduct() {

		return procurementProduct;
	}

	public IUniqueIDGenerator getIdGenerator() {
		return idGenerator;
	}

	public void setIdGenerator(IUniqueIDGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	public Map<Integer, String> getCropCategoryList() {
		Map<Integer, String> cropCategories = new LinkedHashMap<Integer, String>();
		String categories = getText("cropCategories");
		String[] cropCategory = categories.split(",");
		Arrays.sort(cropCategory);
		int i = 0;
		for (String cropCat : cropCategory) {
			cropCategories.put(Integer.valueOf(i++), cropCat);
		}
		return cropCategories;
	}

	public Map<String, String> getCropCatList() {
		cropCatList.put("0", getText("main"));
		cropCatList.put("1", getText("inter"));

		return cropCatList;
	}

	public Map<Integer, String> getCropTypeList() {
		Map<Integer, String> cropTypeMap = new LinkedHashMap<Integer, String>();
		String cropType = getText("cropTypes");
		String[] crops = cropType.split(",");
		Arrays.sort(crops);
		int i = 0;
		for (String cropTypes : crops) {
			cropTypeMap.put(Integer.valueOf(i++), cropTypes);
		}
		return cropTypeMap;
	}

	public List<ProcurementProduct> getProcurementProductList() {

		return utilService.listProcurementProduct();
	}

	public List<FarmCatalogue> getListUom() {
		List<FarmCatalogue> subUomList = utilService.listCataloguesByUnit();

		return subUomList;
	}

	public void populateUnit() throws Exception {

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

	public void populateCropCategory() throws Exception {
		JSONObject jsonObject = new JSONObject();
		if (cropCatList != null) {
			/*
			 * cropCatList.stream().filter(procurementProduct ->
			 * !StringUtil.isEmpty(procurementProduct.getCropCategory()))
			 * .forEach(procurementProduct -> jsonObject.p
			 * ut(String.valueOf(procurementProduct.getId()),
			 * procurementProduct.getCropCategory()));
			 */
			getCropCatList().forEach((key, value) -> {
				jsonObject.put(key, value);
			});
			try {
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				out.print(jsonObject.toString().replace("\"", "").replace(",", ";"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
		if (id != null) {
			crop1 = "cropcategory";
			List<Object[]> Datas = farmerService.findProcurementProductDetailById(Long.valueOf(id), crop1);
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
