package com.sourcetrace.eses.action;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.ProcurementGrade;
import com.sourcetrace.eses.entity.ProcurementProduct;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope(value = "prototype")

public class PlantingAction extends SwitchAction {


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
	private Planting planting;

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
	
	@Getter
	@Setter
	private String selectedFarmCrops;

	public String create() throws Exception {

		if (planting == null) {
			setCommand(CREATE);
			planting = new Planting();
			Farmer farmer=utilService.findFarmerById(Long.valueOf(farmerId));
			/*ExporterRegistration exporter=utilService.findExportRegById(farmer.getExporter().getId());
			setCropva(exporter.getFarmerHaveFarms());
			setCropgra(exporter.getScattered());*/
			
			request.setAttribute(HEADING, getText("farmCrops.page"));
			return INPUT;
		} else {

			planting.setBranchId(getBranchId());

			if (planting.getFarmCrops() != null && planting.getFarmCrops().getId() != null
					&& planting.getFarmCrops().getId() != null && planting.getFarmCrops().getId() > 0) {
				FarmCrops farm = utilService.findFarmCropsById(Long.valueOf(planting.getFarmCrops().getId()));
				if (farm != null && !ObjectUtil.isEmpty(farm)) {
					planting.setFarmCrops(farm);
				}
			}

			planting.setCropCategory(
					planting.getCropCategory() != null && !StringUtil.isEmpty(planting.getCropCategory())
							? planting.getCropCategory() : null);

			if (selectedProduct != null && !selectedProduct.isEmpty() && selectedProduct != "") {

				ProcurementProduct gro = utilService.findProcurementProductById(Long.valueOf(selectedProduct));
				if (gro != null && !ObjectUtil.isEmpty(gro)) {
					planting.setSpecies(gro);

				}
			}
			if (!StringUtil.isEmpty(plantingDate)) {
				planting.setPlantingDate(DateUtil.convertStringToDate(plantingDate, getGeneralDateFormat()));
			} else {
				planting.setPlantingDate(null);
			}

			if (selectedVariety != null && !selectedVariety.isEmpty() && selectedVariety != "") {

				ProcurementVariety var = utilService.findProcurementVarietyById(Long.valueOf(selectedVariety));
				if (var != null && !ObjectUtil.isEmpty(var)) {
					planting.setVariety(var);
				}
			}

			if (selectedGradename != null && !selectedGradename.isEmpty() && selectedGradename != "") {
				ProcurementGrade var = utilService.findProcurementGradeById(Long.valueOf(selectedGradename));
				if (var != null && !ObjectUtil.isEmpty(var)) {
					planting.setGrade(var);
				}
			}

			planting.setCultiArea(planting.getCultiArea() != null && !StringUtil.isEmpty(planting.getCultiArea())
					? planting.getCultiArea() : null);

			planting.setSeedSource(planting.getSeedSource() != null && !StringUtil.isEmpty(planting.getSeedSource())
					? planting.getSeedSource() : null);

			FarmCrops farm = utilService.findFarmCropsById(Long.valueOf(planting.getFarmCrops().getId()));
			planting.setFarmCropId(farm.getPlanting() == null || farm.getPlanting().size() == 0 ? "1"
					: String.valueOf(farm.getPlanting().stream().mapToInt(u -> Integer.valueOf(u.getFarmCropId()))
							.max().getAsInt() + 1));
			/*planting.setBlockId(farm.getFarmer().getVillage().getCity().getLocality().getState().getCode() + "_"
					+ farm.getFarmer().getVillage().getCity().getLocality().getCode() + "_"
					+ farm.getFarmer().getVillage().getCity().getCode() + "_" + farm.getFarmer().getFarmerId() + "_"
					+ StringUtil.appendZeroPrefix(String.valueOf(farm.getFarmId()), 3) + "_"
					+ StringUtil.appendZeroPrefix(String.valueOf(planting.getFarmCropId()), 3));*/
			//planting.setFarmCropId(idGenerator.getFarmCropIdSeq());
			planting.setPlantingId(planting.getFarmCrops().getBlockId() + "_"
					+ StringUtil.appendZeroPrefix(String.valueOf(planting.getFarmCropId()), 3));
			planting.setUnit(planting.getUnit());
			planting.setRevisionNo(DateUtil.getRevisionNumber());
			if (latLonJsonString != null && !StringUtil.isEmpty(latLonJsonString) && latLonJsonString.length()>2) {
				CoordinatesMap cc = formCoord(latLonJsonString);
				planting.setPlotting(cc);
			}
			planting.setStatus(1);
			// farmCrops.setBlockId("BLK"+String.valueOf(DateUtil.getRevisionNumber()));

			planting.setRevisionNo(DateUtil.getRevisionNumber());
			planting.setPlottingStatus("0");
			utilService.save(planting);
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
			planting = utilService.findPlantingById(Long.valueOf(id));
			if (planting == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(planting)) {
				planting.setStatus(3);
				planting.setRevisionNo(DateUtil.getRevisionNumber());
				utilService.update(planting);
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
			planting = utilService.findPlantingById(Long.valueOf(id));
			if (planting != null && !ObjectUtil.isEmpty(planting)) {
				if (planting.getVariety() != null && !ObjectUtil.isEmpty(planting.getVariety())) {
					setSelectedProduct(planting.getVariety().getProcurementProduct().getName());
					setSelectedVariety(planting.getVariety().getName());
					setSelectedGradename(planting.getGrade().getName());
					setScientificName(planting.getVariety().getProcurementProduct().getSpeciesName());
					setCommand(DETAIL);

				}
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
		if (id != null && !StringUtil.isEmpty(id) && StringUtil.isLong(id) && planting == null) {
			planting = utilService.findPlantingById(Long.valueOf(id));

			setFarmerId(planting.getFarmCrops().getFarm().getFarmer().getId().toString());
			selectedProduct = String.valueOf(planting.getVariety().getProcurementProduct().getId());
			selectedVariety = String.valueOf(planting.getVariety().getId());
			selectedGradename = String.valueOf(planting.getGrade().getId());
			if (planting.getPlantingDate() != null && !ObjectUtil.isEmpty(planting.getPlantingDate())) {
				setPlantingDate(DateUtil.convertDateToString(planting.getPlantingDate(), getGeneralDateFormat()));
			}
			Farmer farmer=utilService.findFarmerById(Long.valueOf(planting.getFarmCrops().getFarm().getFarmer().getId()));
			/*ExporterRegistration exporter=utilService.findExportRegById(farmer.getExporter().getId());
			setCropva(exporter.getFarmerHaveFarms());
			setCropgra(exporter.getScattered());*/
			
			planting.setCultiArea(planting.getCultiArea() != null && !StringUtil.isEmpty(planting.getCultiArea())
					? planting.getCultiArea() : null);
			
			command = "update";
			return INPUT;
		} else {
			Planting temp = utilService.findPlantingById(Long.valueOf(id));

			if (planting.getFarmCrops() != null && planting.getFarmCrops().getId() != null
					&& planting.getFarmCrops().getId() != null && planting.getFarmCrops().getId() > 0) {
				FarmCrops farm = utilService.findFarmCropsById(Long.valueOf(planting.getFarmCrops().getId()));
				if (farm != null && !ObjectUtil.isEmpty(farm)) {
					temp.setFarmCrops(farm);
				}
			}

			if (selectedProduct != null && !selectedProduct.isEmpty() && selectedProduct != "") {

				ProcurementProduct gro = utilService.findProcurementProductById(Long.valueOf(selectedProduct));
				if (gro != null && !ObjectUtil.isEmpty(gro)) {
					temp.setSpecies(gro);

				}
			}

			if (selectedVariety != null && !selectedVariety.isEmpty() && selectedVariety != "") {

				ProcurementVariety var = utilService.findProcurementVarietyById(Long.valueOf(selectedVariety));
				if (var != null && !ObjectUtil.isEmpty(var)) {
					temp.setVariety(var);

				}
			}
			if (selectedGradename != null && !selectedGradename.isEmpty() && selectedGradename != "") {

				ProcurementGrade var = utilService.findProcurementGradeById(Long.valueOf(selectedGradename));
				if (var != null && !ObjectUtil.isEmpty(var)) {
					temp.setGrade(var);

				}
			}
			if (!StringUtil.isEmpty(plantingDate)) {
				temp.setPlantingDate(DateUtil.convertStringToDate(plantingDate, getGeneralDateFormat()));
			} else {
				temp.setPlantingDate(null);
			}
			temp.setCropCategory(planting.getCropCategory() != null && !StringUtil.isEmpty(planting.getCropCategory())
					? planting.getCropCategory() : null);
			temp.setCultiArea(planting.getCultiArea() != null && !StringUtil.isEmpty(planting.getCultiArea())
					? planting.getCultiArea() : null);

			temp.setSeedSource(planting.getSeedSource() != null && !StringUtil.isEmpty(planting.getSeedSource())
					? planting.getSeedSource() : null);
			temp.setUnit(planting.getUnit() != null && !StringUtil.isEmpty(planting.getUnit()) ? planting.getUnit()
					: null);
			temp.setRevisionNo(DateUtil.getRevisionNumber());
			// temp.setStatus(farmCrops.getStatus());
			temp.setRevisionNo(DateUtil.getRevisionNumber());
			/*Farm farm = utilService.findFarmById(Long.valueOf(planting.getFarm().getId()));*/
			
			/*temp.setBlockId(farm.getFarmer().getVillage().getCity().getLocality().getState().getCode() + "_"
					+ farm.getFarmer().getVillage().getCity().getLocality().getCode() + "_"
					+ farm.getFarmer().getVillage().getCity().getCode() + "_00" + farm.getFarmId() + "_" + "00"
					+ temp.getFarmCropId());*/
			
			
			/*temp.setBlockId(farm.getFarmer().getVillage().getCity().getLocality().getState().getCode() + "_"
					+ farm.getFarmer().getVillage().getCity().getLocality().getCode() + "_"
					+ farm.getFarmer().getVillage().getCity().getCode() + "_" + farm.getFarmer().getFarmerId() + "_"
					+ StringUtil.appendZeroPrefix(String.valueOf(farm.getFarmId()), 3) + "_"
					+ StringUtil.appendZeroPrefix(String.valueOf(temp.getFarmCropId()), 3));*/
			
			
			
			//temp.setBlockName(planting.getBlockName());
			temp.setLotNo(planting.getLotNo());
			temp.setChemQty(planting.getChemQty());
			temp.setChemUsed(planting.getChemUsed());
			temp.setSeedQtyPlanted(planting.getSeedQtyPlanted());
			temp.setSeedWeek(planting.getSeedWeek());
			temp.setFertiliser(planting.getFertiliser());
			temp.setTypeOfFert(planting.getTypeOfFert());
			temp.setFLotNo(planting.getFLotNo());
			temp.setFertQty(planting.getFertQty());
			temp.setModeApp(planting.getModeApp());
			temp.setExpHarvestWeek(planting.getExpHarvestWeek());
			temp.setExpHarvestQty(planting.getExpHarvestQty());

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
			JSONArray farmerArr = new JSONArray();
			LinkedList<Object> param = new LinkedList();
			String qry = "SELECT DISTINCT f.id,f.farmCode,f.farmName from FarmCrops fc join fc.farm f where f.status=1 and f.farmer.id=? and fc.status=1";
			param.add(Long.valueOf(selectedFarm));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry = "SELECT DISTINCT f.id,f.farmCode,f.farmName from FarmCrops fc join fc.farm f where f.status=1 and f.farmer.id=? and fc.status=1 and fc.exporter.id=?";
				param.add(Long.valueOf(getLoggedInDealer()));
			}
			
			List<Object[]> dataList = (List<Object[]>) farmerService.listObjectById(qry,param.toArray());
			
			
			if (dataList != null && !ObjectUtil.isEmpty(dataList)) {
				dataList.stream().distinct().forEach(f -> {
					farmerArr.add(getJSONObject(f[0].toString(), f[1].toString() + "-" + f[2].toString()));
				});
			}

			sendAjaxResponse(farmerArr);
		}
	}
	
	public void populateFarmCropsByFarm() throws Exception {
		if (!StringUtil.isEmpty(selectedFarmCrops)) {
			LinkedList<Object> parame = new LinkedList();
			String qry = "FROM FarmCrops f  where f.farm.id=?  and f.status=1 ORDER BY f.id ASC";
			parame.add(Long.valueOf(selectedFarmCrops));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry = "FROM FarmCrops f  where f.farm.id=?  and f.status=1 and f.exporter.id=?  ORDER BY f.id ASC";
				parame.add(Long.valueOf(getLoggedInDealer()));
			}

			List<FarmCrops> growerList = (List<FarmCrops>) farmerService.listObjectById(qry, parame.toArray());
			
			Map<String, String> farmList = new LinkedHashMap<>();
			JSONArray varietyArr = new JSONArray();
			if (!ObjectUtil.isEmpty(growerList)) {
				for (FarmCrops pv : growerList) {
					farmList.put(String.valueOf(pv.getId()), pv.getBlockName());
					varietyArr.add(getJSONObject(pv.getId(), pv.getBlockName()));
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
			
			FarmCrops fc = (FarmCrops) farmerService.findObjectById("from FarmCrops fc where fc.id=?",
					new Object[] { Long.valueOf(exporterGradeId) });
			
			ExporterRegistration exporter=utilService.findExportRegById(Long.valueOf(fc.getExporter().getId()));
			
			List<ProcurementGrade> growerList = utilService
					.listProcurementGradeByProcurementVarietyIdGradeid(Long.valueOf(selectedVariety),exporter.getScattered());
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
		if (planting != null) {

			/*if (StringUtil.isEmpty(planting.getFarm()) || planting.getFarm().equals("")
					|| planting.getFarm().equals(null) || planting.getFarm().getId() == null) {

				errorCodes.put("empty.farms", "empty.farms");
			}

			if (planting.getBlockName() == null || StringUtil.isEmpty(planting.getBlockName())
					|| planting.getBlockName().equals("")) {
				errorCodes.put("empty.blockName", "empty.blockName");
			} else {

				FarmCrops fr = (FarmCrops) farmerService.findObjectById(
						" from FarmCrops fc where fc.blockName=?  and fc.status=1 and fc.farm.id=?",
						new Object[] { planting.getBlockName(),planting.getFarm().getId() });
				if (fr != null && (planting.getId() == null || !planting.getId().equals(fr.getId()))) {

					errorCodes.put("unique.blockName", "unique.blockName");
					//

				} else {

				}

			}*/
			
			if (StringUtil.isEmpty(selectedVariety) || selectedVariety == null) {
				errorCodes.put("empty.selectedVariety", "empty.selectedVariety");
			}
			if (StringUtil.isEmpty(selectedGradename) || selectedGradename == null) {
				errorCodes.put("empty.selectedGradename", "empty.selectedGradename");
			}
			
			if (StringUtil.isEmpty(planting.getSeedSource()) || planting.getSeedSource() == null) {
				errorCodes.put("empty.plantingmaterial", "empty.plantingmaterial");
			}
			if (StringUtil.isEmpty(plantingDate) || plantingDate == null) {
				errorCodes.put("empty.pltDate", "empty.pltDate");
			}

			if (StringUtil.isEmpty(planting.getCultiArea()) || planting.getCultiArea().equals(null)) {
				errorCodes.put("empty.PlantingArea", "empty.PlantingArea");
			}else if(planting.getCultiArea()!=null && StringUtil.isDouble(planting.getCultiArea())  && Double.valueOf(planting.getCultiArea())<=0){
				errorCodes.put("invalid.PlantingArea", "invalid.PlantingArea");
			}

			if (StringUtil.isEmpty(planting.getSeedSource()) || planting.getSeedSource() == null) {
				errorCodes.put("empty.seedsource", "empty.seedsource");
			}

			if (planting.getFarmCrops() != null && planting.getFarmCrops().getId() != null) {
				FarmCrops farm = utilService.findFarmCropsById(Long.valueOf(planting.getFarmCrops().getId()));
				planting.setFarmCrops(farm);
			
				if (farm != null && !StringUtil.isEmpty(planting.getCultiArea())) {
					if (planting.getId() == null || StringUtil.isEmpty(planting.getId())) {
					Double exiscrop =farm.getPlanting().stream().filter(u -> u.getCultiArea()!=null).collect(Collectors.summingDouble(u -> Double.valueOf(u.getCultiArea())));
					exiscrop = exiscrop==null ? Double.valueOf(farm.getCultiArea()) : Double.valueOf(farm.getCultiArea())- exiscrop;
					if (exiscrop < Double.valueOf(planting.getCultiArea())) {
						errorCodes.put("empty.PPCplanting", "empty.PPCplanting");
					}
					}else{
						Double exiscrop =farm.getPlanting().stream().filter(u -> u.getCultiArea()!=null && !u.getId().equals(planting.getId())).collect(Collectors.summingDouble(u -> Double.valueOf(u.getCultiArea())));
						exiscrop = exiscrop==null ? Double.valueOf(farm.getCultiArea()) : Double.valueOf(farm.getCultiArea())- exiscrop;
						
						if (exiscrop < Double.valueOf(planting.getCultiArea())) {
							errorCodes.put("empty.PPCplanting", "empty.PPCplanting");
						}
					}
				}
			}


			if (StringUtil.isEmpty(planting.getCropCategory()) || planting.getCropCategory().equals(null)) {
				errorCodes.put("empty.cropcategory", "empty.cropcategory");
			}
			
		/*	if (latLonJsonString == null || StringUtil.isEmpty(latLonJsonString) || latLonJsonString.length() == 2) {
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

		Planting filter = new Planting();

		if (!StringUtil.isEmpty(searchRecord.get("pc.name"))) {
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
		}
		

		if (!StringUtil.isEmpty(searchRecord.get("farmCropName"))) {
			FarmCrops tempFarm = new FarmCrops();
			tempFarm.setBlockName(searchRecord.get("farmCropName").trim());
			filter.setFarmCrops(tempFarm);
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

		Planting farmCrops = (Planting) obj;
		JSONObject jsonObject = new JSONObject();
		JSONObject objDt = new JSONObject();
		JSONObject actionOnj = new JSONObject();
		String detUrl = "planting_detail.action?id=" + farmCrops.getId() + "&farmId=" + farmCrops.getFarmCrops().getId()
				+ "&farmerId=" + farmCrops.getFarmCrops().getFarm().getFarmer().getFarmerId();
		String code = "<a href=" + detUrl + "&redirectContent="
				+ StringUtil.removeLastComma(getRedirectContent()).trim() + "&layoutType="
				+ request.getParameter("layoutType") + "&url=" + request.getParameter("url")
				+ " title='Go To Detail' target=_blank>" + (farmCrops.getPlantingDate() == null ? ""
						: DateUtil.convertDateToString(farmCrops.getPlantingDate(), getGeneralDateFormat()))
				+ "</a>";
		objDt.put("farm", farmCrops.getFarmCrops() == null ? "" : farmCrops.getFarmCrops().getFarm().getFarmName());
		objDt.put("variety", farmCrops.getVariety() == null ? "" : farmCrops.getVariety().getName());
		objDt.put("farmCrops", farmCrops.getFarmCrops() == null ? "" : farmCrops.getFarmCrops().getBlockName());
		objDt.put("grade", farmCrops.getGrade() == null ? "" : farmCrops.getGrade().getName());
		objDt.put("blockId", farmCrops.getFarmCrops() == null ? "" : farmCrops.getFarmCrops().getBlockId());
		objDt.put("plId", farmCrops.getPlantingId() == null ? "" : farmCrops.getPlantingId());
		objDt.put("date", code);
		objDt.put("blName", farmCrops.getFarmCrops().getBlockName() == null ? "" : farmCrops.getFarmCrops().getBlockName());
		objDt.put("season", farmCrops.getCropSeason() == null ? "" : farmCrops.getCropSeason());

		//objDt.put("area", "");
		objDt.put("area", farmCrops.getCultiArea());

		actionOnj.put("id", farmCrops.getId());

		objDt.put("edit",
				"<a href='#' onclick='ediFunction2(\"" + StringEscapeUtils.escapeJavaScript(actionOnj.toString())
						+ "\")' class='fa fa-edit' title='Edit' ></a>");
		objDt.put("del",
				"<a href='#' onclick='deleteFunction2(\"" + StringEscapeUtils.escapeJavaScript(actionOnj.toString())
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
	
	public void populateCropNames() throws Exception {
		JSONArray farmerArr = new JSONArray();
		
		if (selectedFarmCrops != null && !ObjectUtil.isEmpty(selectedFarmCrops)) {
			
			FarmCrops fc = (FarmCrops) farmerService.findObjectById("from FarmCrops fc where fc.id=?",
					new Object[] { Long.valueOf(selectedFarmCrops) });
			
			List<ProcurementVariety> dataList= utilService.listProcurementVarietyBasedOnCropCat(fc.getExporter().getFarmerHaveFarms());

			if (dataList != null && !ObjectUtil.isEmpty(dataList)) {
				dataList.stream().distinct().forEach(f -> {
					farmerArr.add(getJSONObject(f.getId().toString(), f.getName().toString()));
				});
			}
		}
		sendAjaxResponse(farmerArr);

	}
	
	public void populateFarmBound(){
		JSONArray jss =new JSONArray();
		if(selectedFarm!=null && !StringUtil.isEmpty(selectedFarm) && StringUtil.isLong(selectedFarm)){
			List<Coordinates> cr = (List<Coordinates>) farmerService.listObjectById("select pc.farmCoordinates from FarmCrops f join f.plotting pc where f.id=?", new Object[]{Long.valueOf(selectedFarm)});
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
