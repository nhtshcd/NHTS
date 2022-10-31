package com.sourcetrace.eses.action;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Coordinates;
import com.sourcetrace.eses.entity.CoordinatesMap;
import com.sourcetrace.eses.entity.ExporterRegistration;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.ProcurementGrade;
import com.sourcetrace.eses.entity.ProcurementProduct;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope(value = "prototype")

public class FarmCropsAction extends SwitchAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(FarmCropsAction.class);

	protected static final String CREATE = "create";
	protected static final String DETAIL = "detail";
	protected static final String UPDATE = "update";
	protected static final String MAPPING = "mapping";
	protected static final String DELETE = "delete";
	protected static final String LIST = "list";
	protected static final String TITLE_PREFIX = "title.";
	protected static final String HEADING = "heading";

	public static final String FARMER_DETAIL = "farmerDetail";
	@Getter
	@Setter
	protected String command;
	@Getter
	@Setter
	private FarmCrops farmCrops;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String redirectContent;

	@Getter
	@Setter
	private String selectedFarm;

	@Autowired
	private IUtilService utilService;

	@Getter
	@Setter
	public String latLonJsonString;

	@Getter
	@Setter
	private String selectedFarmName;

	@Getter
	@Setter
	private String calendar1;

	@Getter
	@Setter
	private String selGrower;

	@Getter
	@Setter
	private String selectedCropName;
	@Getter
	@Setter
	private String tabValue;
	@Getter
	@Setter
	private String farmerId;
	@Getter
	@Setter
	public String scientificName;
	@Getter
	@Setter
	private String plantingDate;

	@Getter
	@Setter
	private String selectedGradename;

	@Getter
	@Setter
	private String selGrade;

	@Getter
	@Setter
	private String seedWeek;

	@Getter
	@Setter
	private String expHarvestWeek;
	
	@Getter
	@Setter
	private String cropva;

	@Getter
	@Setter
	private String cropgra;
	
	@Getter
	@Setter
	private String exporterGradeId;

	@Autowired
	private IUniqueIDGenerator idGenerator;

	public String create() throws Exception {

		if (farmCrops == null) {
			setCommand(CREATE);
			farmCrops = new FarmCrops();
			Farmer farmer=utilService.findFarmerById(Long.valueOf(farmerId));
			if (getLoggedInDealer() > 0) {
			ExporterRegistration exporter=utilService.findExportRegById(Long.valueOf(getLoggedInDealer()));
			farmCrops.setExporter(exporter);
			}
			
			
			request.setAttribute(HEADING, getText("farmCrops.page"));
			return INPUT;
		} else {

			farmCrops.setBranchId(getBranchId());
			
			if (getLoggedInDealer() > 0) {
				ExporterRegistration ex = utilService.findExportRegById(Long.valueOf(getLoggedInDealer()));
				farmCrops.setExporter(ex);
			} else if (farmCrops.getExporter() != null && farmCrops.getExporter().getId() > 0) {
				ExporterRegistration ex = utilService.findExportRegById(farmCrops.getExporter().getId());
				farmCrops.setExporter(ex);

			}
			
			if (farmCrops.getFarm() != null && farmCrops.getFarm().getId() != null
					&& farmCrops.getFarm().getId() != null && farmCrops.getFarm().getId() > 0) {
				Farm farm = utilService.findFarmById(Long.valueOf(farmCrops.getFarm().getId()));
				if (farm != null && !ObjectUtil.isEmpty(farm)) {
					farmCrops.setFarm(farm);
				}
			}


			farmCrops.setCultiArea(farmCrops.getCultiArea() != null && !StringUtil.isEmpty(farmCrops.getCultiArea())
					? farmCrops.getCultiArea() : null);


			Farm farm = utilService.findFarmById(Long.valueOf(farmCrops.getFarm().getId()));
			farmCrops.setFarmCropId(farm.getFarmCrops() == null || farm.getFarmCrops().size() == 0 ? "1"
					: String.valueOf(farm.getFarmCrops().stream().mapToInt(u -> Integer.valueOf(u.getFarmCropId()))
							.max().getAsInt() + 1));
			farmCrops.setBlockId(farm.getFarmer().getVillage().getCity().getLocality().getState().getCode() + "_"
					+ farm.getFarmer().getVillage().getCity().getLocality().getCode() + "_"
					+ farm.getFarmer().getVillage().getCity().getCode() + "_" + farm.getFarmer().getFarmerId() + "_"
					+ StringUtil.appendZeroPrefix(String.valueOf(farm.getFarmId()), 3) + "_"
					+ StringUtil.appendZeroPrefix(String.valueOf(farmCrops.getFarmCropId()), 3));
			
			
			// farmCrops.setFarmCropId(idGenerator.getFarmCropIdSeq());
			farmCrops.setRevisionNo(DateUtil.getRevisionNumber());
			if (latLonJsonString != null && !StringUtil.isEmpty(latLonJsonString) && latLonJsonString.length()>2) {
				CoordinatesMap cc = formCoord(latLonJsonString);
				farmCrops.setPlotting(cc);
			}
			farmCrops.setStatus(1);
			// farmCrops.setBlockId("BLK"+String.valueOf(DateUtil.getRevisionNumber()));

			farmCrops.setRevisionNo(DateUtil.getRevisionNumber());
			farmCrops.setPlottingStatus("0");
			utilService.save(farmCrops);
			return "farmerDetail";
		}

	}

	/**
	 * Delete.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */

	@SuppressWarnings("unchecked")
	public String delete() throws Exception {
		String result = null;
		if (this.getId() != null && !(this.getId().equals(EMPTY))) {
			farmCrops = utilService.findFarmCropsById(Long.valueOf(id));
			if (farmCrops == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(farmCrops)) {
				farmCrops.setStatus(3);
				farmCrops.setRevisionNo(DateUtil.getRevisionNumber());
				utilService.update(farmCrops);
				/*
				 * getJsonObject().put("msg", getText("farmcrop.deleted"));
				 * getJsonObject().put("title", getText("title.success"));
				 * sendAjaxResponse(getJsonObject());
				 */
				return "farmerDetail";
			}
		}

		request.setAttribute(HEADING, getText(LIST));
		return REDIRECT;
	}

	/**
	 * Detail the.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */

	public String detail() throws Exception {
		if (id != null && !StringUtil.isEmpty(id) && StringUtil.isLong(id)) {
			farmCrops = utilService.findFarmCropsById(Long.valueOf(id));
			if (farmCrops != null && !ObjectUtil.isEmpty(farmCrops)) {
				
					setCommand(DETAIL);

				
			}
			return DETAIL;
		} else {
			request.setAttribute(HEADING, getText(LIST));
			return LIST;
		}
	}

	/**
	 * Update the.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */

	public String update() throws Exception {
		if (id != null && !StringUtil.isEmpty(id) && StringUtil.isLong(id) && farmCrops == null) {
			farmCrops = utilService.findFarmCropsById(Long.valueOf(id));

			setFarmerId(farmCrops.getFarm().getFarmer().getId().toString());
		
			Farmer farmer=utilService.findFarmerById(Long.valueOf(farmCrops.getFarm().getFarmer().getId()));
		/*	ExporterRegistration exporter=utilService.findExportRegById(farmer.getExporter().getId());
			setCropva(exporter.getFarmerHaveFarms());
			setCropgra(exporter.getScattered());*/
			
			farmCrops.setCultiArea(farmCrops.getCultiArea() != null && !StringUtil.isEmpty(farmCrops.getCultiArea())
					? farmCrops.getCultiArea() : null);
			
			command = "update";
			return INPUT;
		} else {
			FarmCrops temp = utilService.findFarmCropsById(Long.valueOf(id));

			if (farmCrops.getFarm() != null && farmCrops.getFarm().getId() != null
					&& farmCrops.getFarm().getId() != null && farmCrops.getFarm().getId() > 0) {
				Farm farm = utilService.findFarmById(Long.valueOf(farmCrops.getFarm().getId()));
				if (farm != null && !ObjectUtil.isEmpty(farm)) {
					temp.setFarm(farm);
				}
			}

			temp.setCultiArea(farmCrops.getCultiArea() != null && !StringUtil.isEmpty(farmCrops.getCultiArea())
					? farmCrops.getCultiArea() : null);

			temp.setRevisionNo(DateUtil.getRevisionNumber());
			// temp.setStatus(farmCrops.getStatus());
			temp.setRevisionNo(DateUtil.getRevisionNumber());
			Farm farm = utilService.findFarmById(Long.valueOf(farmCrops.getFarm().getId()));
			
			
			if (farmCrops.getExporter() != null && farmCrops.getExporter().getId() > 0) {
				ExporterRegistration ex = utilService.findExportRegById(farmCrops.getExporter().getId());
				temp.setExporter(ex);

			}
			
			temp.setBlockId(farm.getFarmer().getVillage().getCity().getLocality().getState().getCode() + "_"
					+ farm.getFarmer().getVillage().getCity().getLocality().getCode() + "_"
					+ farm.getFarmer().getVillage().getCity().getCode() + "_" + farm.getFarmer().getFarmerId() + "_"
					+ StringUtil.appendZeroPrefix(String.valueOf(farm.getFarmId()), 3) + "_"
					+ StringUtil.appendZeroPrefix(String.valueOf(temp.getFarmCropId()), 3));
			
			
			
			temp.setBlockName(farmCrops.getBlockName());
			if (latLonJsonString != null && !StringUtil.isEmpty(latLonJsonString)) {
				CoordinatesMap cc = formCoord(latLonJsonString);
				temp.setPlotting(cc);
				temp.setLatitude(cc.getMidLatitude());
				temp.setLongitude(cc.getMidLongitude());
			}
			utilService.update(temp);

		}
		return "farmerDetail";

	}

	public void populateFarm() throws Exception {
		if (!StringUtil.isEmpty(selectedFarm)) {
			Map<String, String> farmList = new LinkedHashMap<>();
			List<Farm> growerList = utilService.listFarmByFarmerId(Long.valueOf(selectedFarm));
			JSONArray varietyArr = new JSONArray();
			if (!ObjectUtil.isEmpty(growerList)) {
				for (Farm pv : growerList) {
					farmList.put(String.valueOf(pv.getId()), pv.getFarmName());
					varietyArr.add(getJSONObject(pv.getId(), pv.getFarmCode()+"-"+pv.getFarmName()));
				}
			}

			sendAjaxResponse(varietyArr);
		}
	}

/*	public void populateGrade() throws Exception {
		if (!StringUtil.isEmpty(selectedVariety)) {
			List<ProcurementGrade> growerList = utilService
					.listProcurementGradeByProcurementVarietyId(Long.valueOf(selectedVariety));
			JSONArray varietyArr = new JSONArray();
			if (!ObjectUtil.isEmpty(growerList)) {
				for (ProcurementGrade pv : growerList) {
					varietyArr.add(getJSONObject(pv.getId(), pv.getName()));
				}
			}

			sendAjaxResponse(varietyArr);
		}
	}*/
/*	public void populateGrade() throws Exception {
		if (!StringUtil.isEmpty(selectedVariety)) {
			List<ProcurementGrade> growerList = utilService
					.listProcurementGradeByProcurementVarietyId(Long.valueOf(selectedVariety));
			JSONArray varietyArr = new JSONArray();
			if (!ObjectUtil.isEmpty(growerList)) {
				
				String expGradeid[]=exporterGradeId.split(",");
				
				for (ProcurementGrade pv : growerList) {
					
					for(int i=0;i<expGradeid.length;i++){
						if(expGradeid[i].trim().equalsIgnoreCase(String.valueOf(pv.getId()))){
							varietyArr.add(getJSONObject(pv.getId(), pv.getName()));
						}
					}
				}
			}
			sendAjaxResponse(varietyArr);
		}
	}*/
	public void populateGrade() throws Exception {
		if (!StringUtil.isEmpty(selectedVariety)) {
			List<ProcurementGrade> growerList = utilService
					.listProcurementGradeByProcurementVarietyIdGradeid(Long.valueOf(selectedVariety),exporterGradeId);
			JSONArray varietyArr = new JSONArray();
			if (!ObjectUtil.isEmpty(growerList)) {
				
				for (ProcurementGrade pv : growerList) {
							varietyArr.add(getJSONObject(pv.getId(), pv.getName()));
				}
			}
			sendAjaxResponse(varietyArr);
		}
	}

	public void populateFarmsCrop() throws Exception {
		if (selectedFarm != null && !StringUtil.isEmpty(selectedFarm)) {
			Map<String, String> cropList = new LinkedHashMap<>();
			// List<>
		}
	}

	public Map<String, String> getFarmerNameList() {
		Map<String, String> sedMerList = new LinkedHashMap<>();

		List<Farmer> seedMer = utilService.getFarmerList();
		if (!ObjectUtil.isEmpty(seedMer)) {
			seedMer.stream().filter(uu -> uu.getStatus() == 1).forEach(acd -> {

				sedMerList.put(String.valueOf(acd.getId()), acd.getFirstName() + "-" + acd.getFarmerCode());
			});
		}

		return sedMerList;
	}

	public void populateValidate() {
		Map<String, String> errorCodes = new LinkedHashMap<String, String>();
		if (farmCrops != null) {

			if (farmCrops.getExporter() == null || farmCrops.getExporter().getId() == null
					|| farmCrops.getExporter().getId() <= 0) {
				errorCodes.put("empty.exporter", "empty.exporter");
			}
			
			if (StringUtil.isEmpty(farmCrops.getFarm()) || farmCrops.getFarm().equals("")
					|| farmCrops.getFarm().equals(null) || farmCrops.getFarm().getId() == null) {

				errorCodes.put("empty.farms", "empty.farms");
			}

			if (farmCrops.getBlockName() == null || StringUtil.isEmpty(farmCrops.getBlockName())
					|| farmCrops.getBlockName().equals("")) {
				errorCodes.put("empty.blockName", "empty.blockName");
			} else {

				FarmCrops fr = (FarmCrops) farmerService.findObjectById(
						" from FarmCrops fc where fc.blockName=?  and fc.status=1 and fc.farm.id=?",
						new Object[] { farmCrops.getBlockName(),farmCrops.getFarm().getId() });
				if (fr != null && (farmCrops.getId() == null || !farmCrops.getId().equals(fr.getId()))) {

					errorCodes.put("unique.blockName", "unique.blockName");
					//

				} else {

				}

			}
			
			/*if (StringUtil.isEmpty(selectedVariety) || selectedVariety == null) {
				errorCodes.put("empty.selectedVariety", "empty.selectedVariety");
			}
			if (StringUtil.isEmpty(selectedGradename) || selectedGradename == null) {
				errorCodes.put("empty.selectedGradename", "empty.selectedGradename");
			}
			
			
			if (StringUtil.isEmpty(farmCrops.getSeedSource()) || farmCrops.getSeedSource() == null) {
				errorCodes.put("empty.seedsource", "empty.seedsource");
			}
			if (StringUtil.isEmpty(plantingDate) || plantingDate == null) {
				errorCodes.put("empty.pltDate", "empty.pltDate");
			}*/

			if (StringUtil.isEmpty(farmCrops.getCultiArea()) || farmCrops.getCultiArea().equals(null)) {
				errorCodes.put("empty.cultiArea", "empty.cultiArea");
			}else if(farmCrops.getCultiArea()!=null && StringUtil.isDouble(farmCrops.getCultiArea())  && Double.valueOf(farmCrops.getCultiArea())<=0){
				errorCodes.put("invalid.cultiArea", "invalid.cultiArea");
			}

			/*if (StringUtil.isEmpty(farmCrops.getSeedSource()) || farmCrops.getSeedSource() == null) {
				errorCodes.put("empty.seedsource", "empty.seedsource");
			}*/

			if (farmCrops.getFarm() != null && farmCrops.getFarm().getId() != null) {
				Farm farm = utilService.findFarmById(Long.valueOf(farmCrops.getFarm().getId()));
				farmCrops.setFarm(farm);
			
				if (farm != null && !StringUtil.isEmpty(farmCrops.getCultiArea())) {
					if (farmCrops.getId() == null || StringUtil.isEmpty(farmCrops.getId())) {
					Double exiscrop =farm.getFarmCrops().stream().filter(u -> u.getCultiArea()!=null).collect(Collectors.summingDouble(u -> Double.valueOf(u.getCultiArea())));
					exiscrop = exiscrop==null ? Double.valueOf(farm.getProposedPlanting()) : Double.valueOf(farm.getProposedPlanting())- exiscrop;
					if (exiscrop < Double.valueOf(farmCrops.getCultiArea())) {
						errorCodes.put("empty.PPC", "empty.PPC");
					}
					}else{
						Double exiscrop =farm.getFarmCrops().stream().filter(u -> u.getCultiArea()!=null && !u.getId().equals(farmCrops.getId())).collect(Collectors.summingDouble(u -> Double.valueOf(u.getCultiArea())));
						exiscrop = exiscrop==null ? Double.valueOf(farm.getProposedPlanting()) : Double.valueOf(farm.getProposedPlanting())- exiscrop;
						
						if (exiscrop < Double.valueOf(farmCrops.getCultiArea())) {
							errorCodes.put("empty.PPC", "empty.PPC");
						}
					}
				}
			}


			/*	if (StringUtil.isEmpty(farmCrops.getCropCategory()) || farmCrops.getCropCategory().equals(null)) {
				errorCodes.put("empty.cropcategory", "empty.cropcategory");
			}
			
			if (latLonJsonString == null || StringUtil.isEmpty(latLonJsonString) || latLonJsonString.length() == 2) {
				errorCodes.put("empty.plotting", getLocaleProperty("empty.plotting"));
			}*/
			

		} else {
			errorCodes.put("empty.fields", getLocaleProperty("empty.fields"));
		}
		printErrorCodes(errorCodes);
	}

	public void populateScientificName() throws Exception {
		JSONObject jsonObj = new JSONObject();
		if (selGrower != null && !ObjectUtil.isEmpty(selGrower)) {
			ProcurementProduct proList = utilService.findProcurementProductById(Long.valueOf(selGrower));

			if (proList != null && !ObjectUtil.isEmpty(proList)) {
				jsonObj.put("sciName",
						(proList.getSpeciesName() != null && !StringUtil.isEmpty(proList.getSpeciesName())
								? proList.getSpeciesName() : ""));
			}
		}
		sendAjaxResponse(jsonObj);

	}

	public String data() throws Exception {

		Map<String, String> searchRecord = getDataTableJQGridRequestParam(); // get
																				// the

		FarmCrops filter = new FarmCrops();

		/*if (!StringUtil.isEmpty(searchRecord.get("pc.name"))) {
			ProcurementProduct procurementProduct = new ProcurementProduct();
			ProcurementVariety procurementVariety = new ProcurementVariety();
			procurementProduct.setName(searchRecord.get("pc.name").trim());
			procurementVariety.setProcurementProduct(procurementProduct);
			filter.setVariety(procurementVariety);
		}

		if (!StringUtil.isEmpty(searchRecord.get("pv.name"))) {
			ProcurementVariety procurementVariety = new ProcurementVariety();
			procurementVariety.setName(searchRecord.get("pv.name").trim());
			filter.setVariety(procurementVariety);
		}*/

		if (!StringUtil.isEmpty(searchRecord.get("farmName"))) {
			Farm tempFarm = new Farm();
			tempFarm.setFarmName(searchRecord.get("farmName").trim());
			filter.setFarm(tempFarm);
		}
		
		if (!StringUtil.isEmpty(searchRecord.get("exporterName"))) {
			ExporterRegistration tempFarm = new ExporterRegistration();
			tempFarm.setName(searchRecord.get("exporterName").trim());
			filter.setExporter(tempFarm);
		}

		if (!StringUtil.isEmpty(this.farmerId) && StringUtil.isLong(this.farmerId)) {
			filter.setFarmerId(Long.valueOf(this.farmerId));
		}

		Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
				getPage());

		return sendDataTableJSONResponse(data);
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJSON(Object obj) {

		FarmCrops farmCrops = (FarmCrops) obj;
		JSONObject jsonObject = new JSONObject();
		JSONObject objDt = new JSONObject();
		JSONObject actionOnj = new JSONObject();
		String detUrl = "farmCrops_detail.action?id=" + farmCrops.getId() + "&farmId=" + farmCrops.getFarm().getId()
				+ "&farmerId=" + farmCrops.getFarm().getFarmer().getFarmerId();
		String code = "<a href=" + detUrl + "&redirectContent="
				+ StringUtil.removeLastComma(getRedirectContent()).trim() + "&layoutType="
				+ request.getParameter("layoutType") + "&url=" + request.getParameter("url")
				+ " title='Go To Detail' target=_blank>" + (farmCrops.getBlockId() == null ? ""
						: farmCrops.getBlockId())
				+ "</a>";
		objDt.put("exporter", ObjectUtil.isEmpty(farmCrops.getExporter()) || farmCrops.getExporter() == null ? "" : farmCrops.getExporter().getName());
		objDt.put("farm", farmCrops.getFarm() == null ? "" : farmCrops.getFarm().getFarmName());
		objDt.put("blockId",code);
		objDt.put("blName", farmCrops.getBlockName() == null ? "" : farmCrops.getBlockName());

		objDt.put("area", farmCrops.getCultiArea() == null ? "" : farmCrops.getCultiArea());

		actionOnj.put("id", farmCrops.getId());

		objDt.put("edit",
				"<a href='#' onclick='ediFunction1(\"" + StringEscapeUtils.escapeJavaScript(actionOnj.toString())
						+ "\")' class='fa fa-edit' title='Edit' ></a>");
		objDt.put("del",
				"<a href='#' onclick='deleteFunction1(\"" + StringEscapeUtils.escapeJavaScript(actionOnj.toString())
						+ "\")' class='fa fa-trash-o' title='del' ></a>");
		jsonObject.put("DT_RowId", farmCrops.getId());
		jsonObject.put("cell", objDt);
		return jsonObject;
	}

	public void populateGradeData() {
		JSONObject jsonObj = new JSONObject();
		if (selGrade != null && !ObjectUtil.isEmpty(selGrade)) {
			ProcurementGrade proList = utilService.findProcurementGradeById(Long.valueOf(selGrade));

			if (proList != null && !ObjectUtil.isEmpty(proList)) {
				jsonObj.put("yield", proList.getYield() == null ? 0 : proList.getYield());
				jsonObj.put("cc", proList.getCropCycle() == null ? 0 : proList.getCropCycle());
				jsonObj.put("hDays", proList.getHarvestDays() == null ? 0 : proList.getHarvestDays());
			}
		}
		sendAjaxResponse(jsonObj);

	}
	
	public List<ProcurementVariety> getProductVarityList() {
		String idValue = ServletActionContext.getRequest().getParameter("id");
		String paramValue = ServletActionContext.getRequest().getParameter("farmerId");
		Farmer farmer = null;
		if(idValue == null || idValue.isEmpty()){
			farmer=utilService.findFarmerById(Long.valueOf(paramValue));
		}else{
			farmer= (Farmer) farmerService.findObjectById("from Farmer fc where fc.farmerId=?",
					new Object[] { paramValue });
		}
		
		
		//List<ProcurementVariety> listProcurementvarity= utilService.listProcurementVarietyBasedOnCropCat(farmer.getExporter().getFarmerHaveFarms());
		List<ProcurementVariety> listProcurementvarity = utilService.listProcurementVariety();

		return listProcurementvarity;
	}
	
	public void populateFarmBound(){
		JSONArray jss =new JSONArray();
		if(selectedFarm!=null && !StringUtil.isEmpty(selectedFarm) && StringUtil.isLong(selectedFarm)){
			List<Coordinates> cr = (List<Coordinates>) farmerService.listObjectById("select pc.farmCoordinates from Farm f join f.plotting pc where f.id=?", new Object[]{Long.valueOf(selectedFarm)});
					if(cr!=null && !cr.isEmpty()){
						cr.stream().sorted(Comparator.comparingLong(Coordinates::getOrderNo)).forEach(uu ->{
							JSONObject jsobj =new JSONObject();
							jsobj.put("latitude", uu.getLatitude());
							jsobj.put("longitude", uu.getLongitude());
							jsobj.put("orderNo", uu.getOrderNo());
							jss.add(jsobj);
						});
					}
		}
		
		sendAjaxResponse(jss);
	}

}
