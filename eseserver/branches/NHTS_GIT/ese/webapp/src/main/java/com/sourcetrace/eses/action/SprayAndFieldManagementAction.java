package com.sourcetrace.eses.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.text.DecimalFormat;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.DocumentUpload;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.Pcbp;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.ProcurementGrade;
import com.sourcetrace.eses.entity.ProcurementProduct;
import com.sourcetrace.eses.entity.SprayAndFieldManagement;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.entity.Village;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.FileUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class SprayAndFieldManagementAction extends SwitchAction {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(SprayAndFieldManagementAction.class);

	protected static final String CREATE = "create";
	protected static final String DETAIL = "detail";
	protected static final String UPDATE = "update";
	protected static final String MAPPING = "mapping";
	protected static final String DELETE = "delete";
	protected static final String LIST = "list";
	protected static final String TITLE_PREFIX = "title.";
	protected static final String HEADING = "heading";

	@Getter
	@Setter
	protected String command;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String redirectContent;

	@Getter
	@Setter
	private String crop;

	@Getter
	@Setter
	private SprayAndFieldManagement sprayAndFieldManagement;

	@Getter
	@Setter
	private File files;

	@Getter
	@Setter
	private String fileNamePhoto;

	@Getter
	@Setter
	private String fileType;

	@Getter
	@Setter
	private String dateOfSpraying;
	
	@Getter
	@Setter
	private String endDateOfSpraying;

	@Getter
	@Setter
	private String trainingStatusOfSprayOperator;

	@Getter
	@Setter
	private String lastDateOfCalibration;
	@Getter
	@Setter
	private String harvestStr;

	@Getter
	@Setter
	private String inspectorFileName;

	@Getter
	@Setter
	private String exporterFileName;

	@Getter
	@Setter
	private String selectedChamical;

	@Getter
	@Setter
	private String selectedVariety;

	@Getter
	@Setter
	private String selectedTradeNameId;

	/**
	 * for insertion
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getChemicalList() {
		Map<String, String> chemicalList = new LinkedHashMap<>();
		// chemicalList=getFarmCatalougeMap(Integer.valueOf(getText("agroCheList")));

		List<Object[]> pcbpList = utilService.listPcbp();
		for (Object[] objects : pcbpList) {
			chemicalList.put(String.valueOf(objects[0]), String.valueOf(objects[1]));
		}

		return chemicalList;
	}

	public Map<String, String> getUmoList() {
		Map<String, String> umoList = new LinkedHashMap<>();
		umoList = getFarmCatalougeMap(Integer.valueOf(getText("umoListType")));
		return umoList;
	}

	public Map<String, String> getApplicationEquipment() {
		Map<String, String> applicationEquipment = new LinkedHashMap<>();
		applicationEquipment = getFarmCatalougeMap(Integer.valueOf(getText("applicationEquipmentType")));
		return applicationEquipment;
	}

	public Map<String, String> getMethodApplication() {
		Map<String, String> methodApplication = new LinkedHashMap<>();
		methodApplication = getFarmCatalougeMap(Integer.valueOf(getText("methodApplicationType")));
		return methodApplication;
	}

	public String create() throws Exception {
		if (sprayAndFieldManagement == null) {
			command = CREATE;
			request.setAttribute(HEADING, "sprayAndFieldManagementcreate");
			return INPUT;
		} else {

			if (!StringUtil.isEmpty(dateOfSpraying)) {
				sprayAndFieldManagement
						.setDateOfSpraying(DateUtil.convertStringToDate(dateOfSpraying, getGeneralDateFormat()));
			}
			if (!StringUtil.isEmpty(endDateOfSpraying)) {
				sprayAndFieldManagement.setEndDateSpray(DateUtil.convertStringToDate(endDateOfSpraying, getGeneralDateFormat()));
			}
			if (!StringUtil.isEmpty(trainingStatusOfSprayOperator)) {
				sprayAndFieldManagement.setTrainingStatusOfSprayOperator(getTrainingStatusOfSprayOperator());
			}
			if (!StringUtil.isEmpty(lastDateOfCalibration)) {
				sprayAndFieldManagement.setLastDateOfCalibration(
						DateUtil.convertStringToDate(lastDateOfCalibration, getGeneralDateFormat()));
			}

			if (selectedProduct != null && !StringUtil.isEmpty(selectedProduct)) {
				FarmCrops fc = utilService.findFarmCropsById(Long.valueOf(selectedProduct.split("~")[0]));
				sprayAndFieldManagement.setFarmCrops(fc);
			}

			Pcbp pcbp = utilService.findPcbpById(Long.valueOf(selectedTradeNameId));
			if (pcbp != null) {
				sprayAndFieldManagement.setPcbp(pcbp);
			}

			sprayAndFieldManagement.setCreatedUser(getUsername());
			sprayAndFieldManagement.setBranchId(getBranchId());
			sprayAndFieldManagement.setLatitude(getLatitude());
			sprayAndFieldManagement.setLongitude(getLongitude());
			sprayAndFieldManagement.setDeleteStatus(sprayAndFieldManagement.getDeleteStatus());

			utilService.save(sprayAndFieldManagement);
		}
		return REDIRECT;
	}

	/**
	 * for updation
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {

		if (id != null && !StringUtil.isEmpty(id) && StringUtil.isLong(id) && sprayAndFieldManagement == null) {

			sprayAndFieldManagement = utilService.findSprayAndFieldManagementById(Long.valueOf(id));

			if (sprayAndFieldManagement.getDateOfSpraying() != null && !ObjectUtil.isEmpty(sprayAndFieldManagement.getDateOfSpraying())) {
				setDateOfSpraying(DateUtil.convertDateToString(sprayAndFieldManagement.getDateOfSpraying(),getGeneralDateFormat()));
			}
			
			if (sprayAndFieldManagement.getEndDateSpray() != null && !ObjectUtil.isEmpty(sprayAndFieldManagement.getEndDateSpray())) {
				setEndDateOfSpraying(DateUtil.convertDateToString(sprayAndFieldManagement.getEndDateSpray(),getGeneralDateFormat()));
			}
			
			if (sprayAndFieldManagement.getTrainingStatusOfSprayOperator() != null
					&& !ObjectUtil.isEmpty(sprayAndFieldManagement.getTrainingStatusOfSprayOperator())) {
				setTrainingStatusOfSprayOperator(sprayAndFieldManagement.getTrainingStatusOfSprayOperator());
			}

			if (sprayAndFieldManagement.getLastDateOfCalibration() != null
					&& !ObjectUtil.isEmpty(sprayAndFieldManagement.getLastDateOfCalibration())) {
				setLastDateOfCalibration(DateUtil.convertDateToString(
						sprayAndFieldManagement.getLastDateOfCalibration(), getGeneralDateFormat()));
			}

			sprayAndFieldManagement.setVariety(sprayAndFieldManagement.getPlanting().getVariety().getId().toString());
			setSelectedProduct(sprayAndFieldManagement.getFarmCrops().getId() + "~"
					+ sprayAndFieldManagement.getPlanting().getVariety().getProcurementProduct().getId());

			setSelectedVillage(sprayAndFieldManagement.getFarmCrops().getFarm().getFarmer().getVillage() != null
					? String.valueOf(sprayAndFieldManagement.getFarmCrops().getFarm().getFarmer().getVillage().getId())
					: "");
			setSelectedCity(sprayAndFieldManagement.getFarmCrops().getFarm().getFarmer().getVillage() != null
					? String.valueOf(
							sprayAndFieldManagement.getFarmCrops().getFarm().getFarmer().getVillage().getCity().getId())
					: "");
			setSelectedLocality(sprayAndFieldManagement.getFarmCrops().getFarm().getFarmer().getVillage() != null
					? String.valueOf(sprayAndFieldManagement.getFarmCrops().getFarm().getFarmer().getVillage().getCity()
							.getLocality().getId())
					: "");
			setSelectedState(sprayAndFieldManagement.getFarmCrops().getFarm().getFarmer().getVillage() != null
					? String.valueOf(sprayAndFieldManagement.getFarmCrops().getFarm().getFarmer().getVillage().getCity()
							.getLocality().getState().getId())
					: "");
			setSelectedCountry(sprayAndFieldManagement.getFarmCrops().getFarm().getFarmer().getVillage() != null
					? sprayAndFieldManagement.getFarmCrops().getFarm().getFarmer().getVillage().getCity().getLocality()
							.getState().getCountry().getName()
					: "");
			
			setSelectedFarm(sprayAndFieldManagement.getFarmCrops().getFarm() != null ? String.valueOf(sprayAndFieldManagement.getFarmCrops().getFarm().getId()) : "");
			setSelectedFarmer(sprayAndFieldManagement.getFarmCrops().getFarm() != null
					? String.valueOf(sprayAndFieldManagement.getFarmCrops().getFarm().getFarmer().getId()) : "");
			sprayAndFieldManagement.setPhi(sprayAndFieldManagement.getPhi());
			
			setSelectedTradeNameId(sprayAndFieldManagement.getPcbp()!=null ? sprayAndFieldManagement.getPcbp().getId().toString() : " " );

			command = "update";
			return INPUT;
		} else {

			SprayAndFieldManagement sfManagement = utilService
					.findSprayAndFieldManagementById(Long.valueOf(sprayAndFieldManagement.getId()));
			if (selectedProduct != null && !StringUtil.isEmpty(selectedProduct)) {
				FarmCrops fc = utilService.findFarmCropsById(Long.valueOf(selectedProduct.split("~")[0]));

			}
			sfManagement.setFarmCrops(sprayAndFieldManagement.getFarmCrops());

			if (!StringUtil.isEmpty(dateOfSpraying)) {
				sfManagement.setDateOfSpraying(DateUtil.convertStringToDate(dateOfSpraying, getGeneralDateFormat()));
			} else {
				sfManagement.setDateOfSpraying(null);
			}
			
			if (!StringUtil.isEmpty(endDateOfSpraying)) {
				sfManagement.setEndDateSpray(DateUtil.convertStringToDate(endDateOfSpraying, getGeneralDateFormat()));
			} else {
				sfManagement.setEndDateSpray(null);
			}
			if (!StringUtil.isEmpty(trainingStatusOfSprayOperator)) {
				// sfManagement.setTrainingStatusOfSprayOperator(DateUtil.convertStringToDate(trainingStatusOfSprayOperator,
				// getGeneralDateFormat()));

				sfManagement.setTrainingStatusOfSprayOperator(getTrainingStatusOfSprayOperator());

				// getTrainingStatusOfSprayOperator());

			} else {
				sfManagement.setDateOfSpraying(null);
			}
			if (!StringUtil.isEmpty(lastDateOfCalibration)) {
				sfManagement.setLastDateOfCalibration(
						DateUtil.convertStringToDate(lastDateOfCalibration, getGeneralDateFormat()));
			} else {
				sfManagement.setDateOfSpraying(null);
			}

			/*
			 * sfManagement.setAgroChemicalName(sprayAndFieldManagement.
			 * getAgroChemicalName());
			 */

			sfManagement.setDosage(sprayAndFieldManagement.getDosage());
			sfManagement.setOperatorMedicalReport(sprayAndFieldManagement.getOperatorMedicalReport());
			sfManagement.setUom(sprayAndFieldManagement.getUom());
			sfManagement.setNameOfOperator(sprayAndFieldManagement.getNameOfOperator());
			sfManagement.setSprayMobileNumber(sprayAndFieldManagement.getSprayMobileNumber());
			sfManagement.setTypeApplicationEquipment(sprayAndFieldManagement.getTypeApplicationEquipment());
			sfManagement.setMethodOfApplication(sprayAndFieldManagement.getMethodOfApplication());
			sfManagement.setPhi(sprayAndFieldManagement.getPhi());
			sfManagement.setAgrovetOrSupplierOfTheChemical(sprayAndFieldManagement.getAgrovetOrSupplierOfTheChemical());
			sfManagement.setInsectTargeted(sprayAndFieldManagement.getInsectTargeted());
			sfManagement.setUpdatedUser(getUsername());

			if (selectedTradeNameId != null) {
				Pcbp pcbp = utilService.findPcbpById(Long.valueOf(selectedTradeNameId));
				if (pcbp != null) {
					sfManagement.setPcbp(pcbp);
				}
			}

			if (files != null) {
				DocumentUpload du = new DocumentUpload();
				String[] tokens = fileNamePhoto.split("\\.(?=[^\\.]+$)");
				String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
				du.setName(name);
				du.setContent(FileUtil.getBinaryFileContent(files));
				du.setDocFileContentType(fileType);
				du.setRefCode(String.valueOf(sprayAndFieldManagement.getId()));
				du.setType(DocumentUpload.docType.IMPORT_APP.ordinal());
				du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
				utilService.save(du);
			}
			sfManagement.setDeleteStatus(sprayAndFieldManagement.getDeleteStatus());
			utilService.update(sfManagement);
		}
		return REDIRECT;

	}

	/**
	 * for list page,detail page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		String view = null;
		if (id != null && !id.equals("")) {
			sprayAndFieldManagement = utilService.findSprayAndFieldManagementById(Long.valueOf(id));
			if (sprayAndFieldManagement == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}

			setCurrentPage(getCurrentPage());
			command = UPDATE;
			view = DETAIL;
			request.setAttribute(HEADING, getText("sprayAndFieldManagementdetail"));
		} else {
			request.setAttribute(HEADING, getText(LIST));
			return REDIRECT;
		}
		return view;
	}

	/**
	 * For deletion
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		String result = null;
		if (id != null && !id.equals("")) {
			SprayAndFieldManagement sfManagement = utilService.findSprayAndFieldManagementById(Long.valueOf(id));
			if (sfManagement == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(sfManagement)) {
				sfManagement.setDeleteStatus(1);
				utilService.remove(sfManagement);
				result = REDIRECT;
			}
		}
		return result;
	}

	/**
	 * For validation
	 */
	public void populateValidate() {

		Map<String, String> errorCodes = new LinkedHashMap<String, String>();

		if (sprayAndFieldManagement != null) {

			if (sprayAndFieldManagement.getFarmCrops() == null
					|| sprayAndFieldManagement.getFarmCrops().getFarm() == null
					|| sprayAndFieldManagement.getFarmCrops().getFarm().getFarmer() == null
					|| sprayAndFieldManagement.getFarmCrops().getFarm().getFarmer().getId() == null
					|| sprayAndFieldManagement.getFarmCrops().getFarm().getFarmer().getId() <= 0) {

				errorCodes.put("empty.farmer", "empty.farmer");
			}

			if (sprayAndFieldManagement.getFarmCrops() == null
					|| sprayAndFieldManagement.getFarmCrops().getFarm() == null
					|| sprayAndFieldManagement.getFarmCrops().getFarm().getId() == null
					|| sprayAndFieldManagement.getFarmCrops().getFarm().getId() <= 0) {

				errorCodes.put("emptySpray.farm", "emptySpray.farm");
			}

			if (sprayAndFieldManagement.getFarmCrops() == null
					|| sprayAndFieldManagement.getFarmCrops().getId() == null) {

				errorCodes.put("empty.block", "empty.block");
			}
			if (sprayAndFieldManagement.getPlanting() == null
					|| sprayAndFieldManagement.getPlanting().getId() == null) {
				
				errorCodes.put("empty.planting", "empty.planting");
			}
			Date scoutingDate =null;
			if (dateOfSpraying == null || StringUtil.isEmpty(dateOfSpraying)) {
				errorCodes.put("empty.DateOfSpraying", "empty.DateOfSpraying");
			} else {

				 scoutingDate = DateUtil.convertStringToDate(dateOfSpraying, getGeneralDateFormat());

				/*FarmCrops fc = utilService.findFarmCropsById(sprayAndFieldManagement.getFarmCrops().getId());
				if (fc != null && fc.getPlantingDate().compareTo(scoutingDate) > 0) {
					errorCodes.put("spraying.invlid.date", "spraying.invlid.date");
				}*/
				Planting fc = utilService.findPlantingById(sprayAndFieldManagement.getPlanting().getId());
				if (fc != null && fc.getPlantingDate().compareTo(scoutingDate) > 0) {
					errorCodes.put("spraying.invlid.date", "spraying.invlid.date");
				}
				
			}
			
			
			if (scoutingDate!=null && endDateOfSpraying != null && !StringUtil.isEmpty(endDateOfSpraying)) {
				Date endDate = DateUtil.convertStringToDate(endDateOfSpraying, getGeneralDateFormat());
				if (scoutingDate.compareTo(endDate) > 0) {
					errorCodes.put("spraying.invlid.enddate", "spraying.invlid.enddate");
				}
			}

			if (StringUtil.isEmpty(selectedTradeNameId) || selectedTradeNameId == null) {

				errorCodes.put("empty.pcbpTradNameCha", "empty.pcbpTradNameCha");
			}

			
			 if (sprayAndFieldManagement.getDosage() == null ||
			 StringUtil.isEmpty(sprayAndFieldManagement.getDosage())) {
			 errorCodes.put("empty.dosage", "empty.dosage"); } 
			 if(sprayAndFieldManagement.getUom() == null ||
			 StringUtil.isEmpty(sprayAndFieldManagement.getUom())) {
			 errorCodes.put("empty.uom", "empty.uom"); }
			
			if (sprayAndFieldManagement.getNameOfOperator() == null
					|| StringUtil.isEmpty(sprayAndFieldManagement.getNameOfOperator())) {
				errorCodes.put("empty.NameOfOperator", "empty.NameOfOperator");
			}
			if (sprayAndFieldManagement.getSprayMobileNumber() == null
					|| StringUtil.isEmpty(sprayAndFieldManagement.getSprayMobileNumber())) {
				errorCodes.put("empty.SprayMobileNumber", "empty.SprayMobileNumber");
			}
			if (sprayAndFieldManagement.getTypeApplicationEquipment() == null
					|| StringUtil.isEmpty(sprayAndFieldManagement.getTypeApplicationEquipment())) {
				errorCodes.put("empty.ApplicationEquipment", "empty.ApplicationEquipment");
			}
			if (sprayAndFieldManagement.getMethodOfApplication() == null
					|| StringUtil.isEmpty(sprayAndFieldManagement.getMethodOfApplication())) {
				errorCodes.put("empty.MethodOfApplication", "empty.MethodOfApplication");
			}
			/*
			 * if (sprayAndFieldManagement.getPhi() == null ||
			 * StringUtil.isEmpty(sprayAndFieldManagement.getPhi())) {
			 * errorCodes.put("empty.phi", "empty.phi"); }
			 */
			if (trainingStatusOfSprayOperator == null || StringUtil.isEmpty(trainingStatusOfSprayOperator)) {
				errorCodes.put("empty.trainingStatus", "empty.trainingStatus");
			}
			if (sprayAndFieldManagement.getAgrovetOrSupplierOfTheChemical() == null
					|| StringUtil.isEmpty(sprayAndFieldManagement.getAgrovetOrSupplierOfTheChemical())) {
				errorCodes.put("empty.AgrovetOrSupplierOfTheChemical", "empty.AgrovetOrSupplierOfTheChemical");
			}
			if (lastDateOfCalibration == null || StringUtil.isEmpty(lastDateOfCalibration)) {
				errorCodes.put("empty.lastDateOfCalibration", "empty.lastDateOfCalibration");
			}
			if (sprayAndFieldManagement.getInsectTargeted() == null
					|| StringUtil.isEmpty(sprayAndFieldManagement.getInsectTargeted())) {
				errorCodes.put("empty.InsectTargeted", "empty.getInsectTargeted");
			}

		}
		printErrorCodes(errorCodes);
	}

	public void populatePhiAndDosageDetails() {

		JSONObject jss = new JSONObject();
		if (selectedChamical != null && !StringUtil.isEmpty(selectedChamical) && selectedVariety != null
				&& !StringUtil.isEmpty(selectedVariety)) {
			ProcurementGrade pg = utilService.findProcurementGradeByName(selectedVariety);
			Pcbp pcbpVaCh = utilService.findPcbpByvarietyAndChamical(Long.valueOf(pg.getId()),
					Long.valueOf(selectedChamical));
			if (pcbpVaCh != null) {
				jss.put("sMdosage", pcbpVaCh.getDosage());
				jss.put("sMphi", pcbpVaCh.getPhiIn());
				jss.put("sMuom", pcbpVaCh.getUom());
			}
		}
		sendAjaxResponse(jss);
	}

	public void populateBlockDetails() {

		JSONObject jss = new JSONObject();
		if (selectedFarmer != null && !StringUtil.isEmpty(selectedFarmer) && StringUtil.isLong(selectedFarmer)) {
			Planting fc = utilService.findPlantingById(Long.valueOf(selectedFarmer));

			DecimalFormat f = new DecimalFormat("##.00");
			if (fc != null) {
				Double exyy = fc.getGrade().getYield() != null ? fc.getGrade().getYield() : 0d;
				Double Area = fc.getCultiArea() != null && !StringUtil.isEmpty(fc.getCultiArea())
						? Double.valueOf(fc.getCultiArea()) : 0d;

				jss.put("plantingId", fc.getPlantingId());
				jss.put("blockId", fc.getFarmCrops().getBlockId());
				jss.put("variety", fc.getVariety().getName());
				jss.put("grade", fc.getGrade().getName());

			}

		}
		sendAjaxResponse(jss);
	}

	public void populateChamicals() throws Exception {

		JSONArray stateArr = new JSONArray();
		if (!selectedFarmer.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedFarmer))) {
			Planting plantings = utilService.findPlantingById(Long.valueOf(selectedFarmer));

			if (plantings != null) {
				ProcurementGrade pg = utilService.findProcurementGradeByName(plantings.getGrade().getName());
				if (pg != null) {
					List<Pcbp> pcbp = (List<Pcbp>) utilService.findPcbpByProcurementGradeId(pg.getId());
					if (!ObjectUtil.isListEmpty(pcbp)) {
						for (Pcbp pc : pcbp) {
							stateArr.add(getJSONObject(String.valueOf(pc.getId()), pc.getTradeName()));
						}
					}
				}
			}
		}

		sendAjaxResponse(stateArr);
	}
/*	public void populateChamicals() throws Exception {
		
		JSONArray stateArr = new JSONArray();
		if (!selectedFarmer.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedFarmer))) {
			FarmCrops fc = utilService.findFarmCropsById(Long.valueOf(selectedFarmer));
			
			if (fc != null) {
				ProcurementGrade pg = utilService.findProcurementGradeByName(fc.getGrade().getName());
				if (pg != null) {
					List<Pcbp> pcbp = (List<Pcbp>) utilService.findPcbpByProcurementGradeId(pg.getId());
					if (!ObjectUtil.isListEmpty(pcbp)) {
						for (Pcbp pc : pcbp) {
							stateArr.add(getJSONObject(String.valueOf(pc.getId()), pc.getTradeName()));
						}
					}
				}
			}
		}
		
		sendAjaxResponse(stateArr);
	}
*/
}
