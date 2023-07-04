
package com.sourcetrace.eses.action;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.sourcetrace.eses.entity.DocumentUpload;
import com.sourcetrace.eses.entity.ExporterRegistration;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.Scouting;
import com.sourcetrace.eses.entity.ScoutingDetails;
import com.sourcetrace.eses.entity.Village;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.FileUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import java.sql.Blob;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope(value = "prototype")
public class ScoutingAction extends SwitchAction {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ScoutingAction.class);

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
	private Scouting scouting;

	@Getter
	@Setter
	private String farm;

	@Getter
	@Setter
	private File files;

	@Getter
	@Setter
	private File inspecSign;

	@Getter
	@Setter
	private File istaFile;

	@Getter
	@Setter
	private String istaFileType;

	@Getter
	@Setter
	private String istaFileName;

	@Getter
	@Setter
	private String fileNamePhoto;

	@Getter
	@Setter
	private String inspecSignFileName;

	@Getter
	@Setter
	private String fileType;

	@Getter
	@Setter
	private String inspecSignFileType;

	@Getter
	@Setter
	private String ownerSign;

	@Getter
	@Setter
	private String ownerFileName;

	@Getter
	@Setter
	private String ownerFileType;

	@Getter
	@Setter
	private String scoutingTotalString;

	@Getter
	@Setter
	private String scoutingStr;

	@Getter
	@Setter
	private String scoutDateStrr;

	@Getter
	@Setter
	private String dateStr;

	@Getter
	@Setter
	private String roleID;

	@Getter
	@Setter
	private Blob p1;

	/*
	 * @Getter
	 * 
	 * @Setter protected String selectedCountry;
	 * 
	 * @Getter
	 * 
	 * @Setter protected String selectedState;
	 * 
	 * @Getter
	 * 
	 * @Setter protected String selectedLocality;
	 * 
	 * @Getter
	 * 
	 * @Setter protected String selectedCity;
	 * 
	 * @Getter
	 * 
	 * @Setter protected String selectedVillage;
	 */

	/**
	 * for insertion
	 * 
	 * @return
	 * @throws Exception
	 */

	public String create() throws Exception {
		if (scouting == null) {
			command = CREATE;
			request.setAttribute(HEADING, "scoutingcreate");
			return INPUT;
		} else {

			if (!StringUtil.isEmpty(scoutingStr)) {
				scouting.setReceivedDate(DateUtil.convertStringToDate(scoutingStr, getGeneralDateFormat()));
			} else {
				scouting.setReceivedDate(null);
			}

			/*
			 * if (selectedProduct != null &&
			 * !StringUtil.isEmpty(selectedProduct)) { FarmCrops fc =
			 * utilService.findFarmCropsById(Long.valueOf(selectedProduct.split(
			 * "~")[0])); scouting.setFarmCrops(fc); }
			 */
			if (scouting.getPlanting().getId() != null && !StringUtil.isEmpty(scouting.getPlanting().getId())) {
				Planting fc = utilService.findPlantingById(Long.valueOf(scouting.getPlanting().getId()));
				scouting.setPlanting(fc);
			}

			scouting.setStatus(Scouting.DeleteStatus.NOT_DELETED.ordinal());
			scouting.setCreatedDate(new Date());
			scouting.setCreatedUser(getUsername());
			scouting.setBranchId(getBranchId());
			scouting.setLatitude(getLatitude());
			scouting.setLongitude(getLongitude());

			if (scouting.getInsectsObserved() != null && scouting.getInsectsObserved().equals("1")) {
				scouting.setNameOfInsectsObserved(scouting.getNameOfInsectsObserved() != null
						&& !StringUtil.isEmpty(scouting.getNameOfInsectsObserved())
								? scouting.getNameOfInsectsObserved() : "");
				scouting.setPerOrNumberInsects(scouting.getPerOrNumberInsects() != null
						&& !StringUtil.isEmpty(scouting.getPerOrNumberInsects()) ? scouting.getPerOrNumberInsects()
								: "");
			} else {
				scouting.setNameOfInsectsObserved("");
				scouting.setPerOrNumberInsects("");
			}
			if (scouting.getDiseaseObserved() != null && scouting.getDiseaseObserved().equals("1")) {
				scouting.setNameOfDisease(
						scouting.getNameOfDisease() != null && !StringUtil.isEmpty(scouting.getNameOfDisease())
								? scouting.getNameOfDisease() : "");
				scouting.setPerInfection(
						scouting.getPerInfection() != null && !StringUtil.isEmpty(scouting.getPerInfection())
								? scouting.getPerInfection() : "");
			} else {
				scouting.setNameOfDisease("");
				scouting.setPerInfection("");
			}
			if (scouting.getWeedsObserveds() != null && scouting.getWeedsObserveds().equals("1")) {
				scouting.setNameOfWeeds(
						scouting.getNameOfWeeds() != null && !StringUtil.isEmpty(scouting.getNameOfWeeds())
								? scouting.getNameOfWeeds() : "");
				scouting.setWeedsPresence(
						scouting.getWeedsPresence() != null && !StringUtil.isEmpty(scouting.getWeedsPresence())
								? scouting.getWeedsPresence() : "");
				scouting.setRecommendations(
						scouting.getRecommendations() != null && !StringUtil.isEmpty(scouting.getRecommendations())
								? scouting.getRecommendations() : "");
			} else {
				scouting.setNameOfWeeds("");
				scouting.setWeedsPresence("");
				scouting.setRecommendations("");
			}

			utilService.save(scouting);
		}
		return REDIRECT;
	}

	public String update() throws Exception {

		if (id != null && !StringUtil.isEmpty(id) && StringUtil.isLong(id) && scouting == null) {
			scouting = utilService.findScoutingById(Long.valueOf(id));
			if (scouting.getReceivedDate() != null && !ObjectUtil.isEmpty(scouting.getReceivedDate())) {
				setScoutingStr(DateUtil.convertDateToString(scouting.getReceivedDate(), getGeneralDateFormat()));
			}

			setSelectedProduct(scouting.getPlanting().getFarmCrops().getId() + "~"
					+ scouting.getPlanting().getVariety().getProcurementProduct().getId());
			scouting.setVariety(scouting.getPlanting().getVariety().getId().toString());

			setSelectedVillage(scouting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage() != null
					? String.valueOf(scouting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage().getId())
					: "");
			setSelectedCity(scouting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage() != null
					? String.valueOf(
							scouting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage().getCity().getId())
					: "");
			setSelectedLocality(scouting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage() != null
					? String.valueOf(scouting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage().getCity()
							.getLocality().getId())
					: "");
			setSelectedState(scouting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage() != null
					? String.valueOf(scouting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage().getCity()
							.getLocality().getState().getId())
					: "");
			setSelectedCountry(scouting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage() != null
					? scouting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage().getCity().getLocality()
							.getState().getCountry().getName()
					: "");
			setCurrentPage(getCurrentPage());
			setSelectedFarm(scouting.getPlanting().getFarmCrops().getFarm() != null
					? String.valueOf(scouting.getPlanting().getFarmCrops().getFarm().getId()) : "");
			setSelectedFarmer(scouting.getPlanting().getFarmCrops().getFarm() != null
					? String.valueOf(scouting.getPlanting().getFarmCrops().getFarm().getFarmer().getId()) : "");

			command = "update";
			return INPUT;
		} else {

			Scouting scoutingDetail = utilService.findScoutingById(Long.valueOf(scouting.getId()));

			if (!StringUtil.isEmpty(scoutingStr)) {
				scoutingDetail.setReceivedDate(DateUtil.convertStringToDate(scoutingStr, getGeneralDateFormat()));
			} else {
				scoutingDetail.setReceivedDate(null);
			}

			if (scouting.getPlanting() != null && scouting.getPlanting().getId() > 0) {
				Planting ex = utilService.findPlantingById(scouting.getPlanting().getId());
				scoutingDetail.setPlanting(ex);

			}

			// scoutingDetail.setRecommendations(scouting.getRecommendations());
			scoutingDetail.setAreaIrrrigated(scouting.getAreaIrrrigated());
			scoutingDetail.setInsectsObserved(scouting.getInsectsObserved());
			scoutingDetail.setWeedsObserveds(scouting.getWeedsObserveds());
			scoutingDetail.setDiseaseObserved(scouting.getDiseaseObserved());
			scoutingDetail.setIrrigationType(scouting.getIrrigationType());
			scoutingDetail.setIrrigationMethod(scouting.getIrrigationMethod());
			// scoutingDetail.setWeedsPresence(scouting.getWeedsPresence());
			// scoutingDetail.setPerInfection(scouting.getPerInfection());
			// scoutingDetail.setNameOfDisease(scouting.getNameOfDisease());
			// scoutingDetail.setNameOfInsectsObserved(scouting.getNameOfInsectsObserved());
			// scoutingDetail.setPerOrNumberInsects(scouting.getPerOrNumberInsects());
			// scoutingDetail.setNameOfWeeds(scouting.getNameOfWeeds());
			scoutingDetail.setUpdatedDate(new Date());
			scoutingDetail.setUpdatedUser(getUsername());
			scoutingDetail.setSourceOfWater(scouting.getSourceOfWater());
			// scoutingDetail.setFarmCrops(scouting.getFarmCrops());
			scoutingDetail.setSprayingRequired(scouting.getSprayingRequired());
			scoutingDetail.setSctRecommendation(scouting.getSctRecommendation());

			if (scouting.getInsectsObserved() != null && scouting.getInsectsObserved().equals("1")) {
				scoutingDetail.setNameOfInsectsObserved(scouting.getNameOfInsectsObserved() != null
						&& !StringUtil.isEmpty(scouting.getNameOfInsectsObserved())
								? scouting.getNameOfInsectsObserved() : "");
				scoutingDetail.setPerOrNumberInsects(scouting.getPerOrNumberInsects() != null
						&& !StringUtil.isEmpty(scouting.getPerOrNumberInsects()) ? scouting.getPerOrNumberInsects()
								: "");
			} else {
				scoutingDetail.setNameOfInsectsObserved("");
				scoutingDetail.setPerOrNumberInsects("");
			}
			if (scouting.getDiseaseObserved() != null && scouting.getDiseaseObserved().equals("1")) {
				scoutingDetail.setNameOfDisease(
						scouting.getNameOfDisease() != null && !StringUtil.isEmpty(scouting.getNameOfDisease())
								? scouting.getNameOfDisease() : "");
				scoutingDetail.setPerInfection(
						scouting.getPerInfection() != null && !StringUtil.isEmpty(scouting.getPerInfection())
								? scouting.getPerInfection() : "");
			} else {
				scoutingDetail.setNameOfDisease("");
				scoutingDetail.setPerInfection("");
			}
			if (scouting.getWeedsObserveds() != null && scouting.getWeedsObserveds().equals("1")) {
				scoutingDetail.setNameOfWeeds(
						scouting.getNameOfWeeds() != null && !StringUtil.isEmpty(scouting.getNameOfWeeds())
								? scouting.getNameOfWeeds() : "");
				scoutingDetail.setWeedsPresence(
						scouting.getWeedsPresence() != null && !StringUtil.isEmpty(scouting.getWeedsPresence())
								? scouting.getWeedsPresence() : "");
				scoutingDetail.setRecommendations(
						scouting.getRecommendations() != null && !StringUtil.isEmpty(scouting.getRecommendations())
								? scouting.getRecommendations() : "");
			} else {
				scoutingDetail.setNameOfWeeds("");
				scoutingDetail.setWeedsPresence("");
				scoutingDetail.setRecommendations("");
			}
			utilService.update(scoutingDetail);
		}
		return REDIRECT;
	}

	/**
	 * for list page
	 * 
	 * @return
	 * @throws Exception
	 */
	@Getter
	@Setter
	List<Object[]> ex;

	public String detail() throws Exception {

		if (id != null && !StringUtil.isEmpty(id) && StringUtil.isLong(id)) {
			scouting = utilService.findScoutingById(Long.valueOf(id));

			if (scouting.getReceivedDate() != null && !ObjectUtil.isEmpty(scouting.getReceivedDate())) {
				setScoutingStr(DateUtil.convertDateToString(scouting.getReceivedDate(), getGeneralDateFormat()));
			}
			scouting.setFarmerFullName(scouting.getPlanting().getFarmCrops().getFarm().getFarmer().getFirstName() + " "
					+ (scouting.getPlanting().getFarmCrops().getFarm().getFarmer().getLastName() != null && !StringUtil
							.isEmpty(scouting.getPlanting().getFarmCrops().getFarm().getFarmer().getLastName())
									? scouting.getPlanting().getFarmCrops().getFarm().getFarmer().getLastName() : ""));
			ex = utilService.getAuditRecords("com.sourcetrace.eses.entity.Scouting", scouting.getId());
			roleID = getLoggedInRoleID();
			setCommand(DETAIL);
			return DETAIL;

		} else {

			return REDIRECT;
		}

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
			Scouting scouting = utilService.findScoutingById(Long.valueOf(id));
			if (scouting == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(scouting)) {
				scouting.setStatus(Scouting.DeleteStatus.DELETED.ordinal());
				utilService.update(scouting);
				result = REDIRECT;
			}
		}
		return result;
	}

	/*
	 * public void populateGrade() throws Exception {
	 * 
	 * if (!StringUtil.isEmpty(selectedVariety)) { FarmCrops varierty =
	 * utilService.findFarmCropsById(Long.valueOf(selectedVariety.trim()));
	 * 
	 * if (!ObjectUtil.isEmpty(varierty) && varierty.getVariety() != null &&
	 * varierty.getVariety().getProcurementGrades() != null) { JSONArray
	 * gradeArr = new JSONArray();
	 * 
	 * varierty.getVariety().getProcurementGrades().stream().forEach(obj -> {
	 * gradeArr.add(getJSONObject(obj.getId(), obj.getName())); });
	 * sendAjaxResponse(gradeArr); } } }
	 */

	private Set<ScoutingDetails> formScoutDetails(Scouting scouting) {
		Set<ScoutingDetails> ssoDetails = new LinkedHashSet<>();

		if (!StringUtil.isEmpty(getScoutingTotalString())) {
			List<String> productsList = Arrays.asList(getScoutingTotalString().split("@"));

			productsList.stream().filter(obj -> !StringUtil.isEmpty(obj)).forEach(products -> {
				ScoutingDetails ssoDetails1 = new ScoutingDetails();
				List<String> list = Arrays.asList(products.split("#"));
				ssoDetails1.setScoutDate(list.get(0) != null && !StringUtil.isEmpty(list.get(0))
						? DateUtil.convertStringToDate(list.get(0), getGeneralDateFormat()) : null);
				ssoDetails1.setAreaScouted(list.get(1));
				ssoDetails1.setNoOfPlants(Integer.valueOf(list.get(2)));
				ssoDetails1.setPbmObserved(list.get(3));
				ssoDetails1.setOtherPbmObserved(list.get(4));// here
				ssoDetails1.setNoOfPbmPlants(Integer.valueOf(list.get(5)));
				ssoDetails1.setSolution(list.get(6));
				ssoDetails1.setScoutInitials(list.get(7));

				ssoDetails1.setScouting(scouting);
				ssoDetails.add(ssoDetails1);
			});
		}

		return ssoDetails;
	}

	/**
	 * For validation
	 */
	public void populateValidate() {

		Map<String, String> errorCodes = new LinkedHashMap<String, String>();

		if (scouting != null) {
			if (scouting == null || StringUtil.isEmpty(getScoutingStr())) {
				errorCodes.put("empty.scoutingdate", "empty.scoutingdate");
			}
			if (scoutingStr == null || StringUtil.isEmpty(scoutingStr)) {
				errorCodes.put("empty.scoutingdate", "empty.scoutingdate");
			} else {

				Date scoutingDate = DateUtil.convertStringToDate(getScoutingStr(), getGeneralDateFormat());

				// FarmCrops fc =
				// utilService.findFarmCropsById(scouting.getFarmCrops().getId());
				// scouting.setFarmCrops(fc);
				Planting fc = utilService.findPlantingById(scouting.getPlanting().getId());
				scouting.setPlanting(fc);
				if (fc != null && fc.getPlantingDate().compareTo(scoutingDate) > 0) {
					errorCodes.put("scouting.invlid.date", "scouting.invlid.date");
				}

			}

			if (scouting == null || scouting.getPlanting() == null || scouting.getPlanting().getFarmCrops() == null
					|| scouting.getPlanting().getFarmCrops().getFarm() == null
					|| scouting.getPlanting().getFarmCrops().getFarm().getFarmer() == null
					|| scouting.getPlanting().getFarmCrops().getFarm().getFarmer().getId() == null) {

				errorCodes.put("empty.farmer", "empty.farmer");
			}

			if (scouting == null || scouting.getPlanting() == null || scouting.getPlanting().getFarmCrops() == null
					|| scouting.getPlanting().getFarmCrops().getFarm() == null
					|| scouting.getPlanting().getFarmCrops().getFarm().getId() == null) {

				errorCodes.put("empty.farms", "empty.farms");
			}

			if (scouting == null || scouting.getPlanting() == null || scouting.getPlanting().getFarmCrops() == null
					|| scouting.getPlanting().getFarmCrops().getId() == null) {

				errorCodes.put("empty.block", "empty.block");
			}

			if (scouting == null || scouting.getPlanting() == null || scouting.getPlanting().getId() == null) {

				errorCodes.put("empty.planting", "empty.planting");
			}
			if (scouting == null || StringUtil.isEmpty(scouting.getSprayingRequired())
					|| scouting.getSprayingRequired() == null) {

				errorCodes.put("empty.sprayingRequired", "empty.sprayingRequired");
			}
			if (scouting == null || StringUtil.isEmpty(scouting.getSctRecommendation())
					|| scouting.getSctRecommendation() == null) {
				errorCodes.put("empty.sctRecommendation", "empty.sctRecommendation");
			}

			if (scouting == null || StringUtil.isEmpty(scouting.getInsectsObserved())) {
				errorCodes.put("empty.InsectsObserved", "empty.InsectsObserved");
			}
			if (scouting.getInsectsObserved() != null && scouting.getInsectsObserved().equalsIgnoreCase("1")) {

				// if (scouting == null ||
				// StringUtil.isEmpty(scouting.getNameOfInsectsObserved())) {
				// errorCodes.put("empty.NameOfInsectsObserved",
				// "empty.NameOfInsectsObserved");
				// }
				if (scouting == null || StringUtil.isEmpty(scouting.getPerOrNumberInsects())) {
					errorCodes.put("empty.PerOrNumberInsects", "empty.PerOrNumberInsects");
				}
			}

			if (scouting == null || StringUtil.isEmpty(scouting.getDiseaseObserved())) {
				errorCodes.put("empty.DiseaseObserved", "empty.DiseaseObserved");
			}

			if (scouting.getDiseaseObserved() != null && scouting.getDiseaseObserved().equalsIgnoreCase("1")) {
				if (scouting == null || StringUtil.isEmpty(scouting.getNameOfDisease())) {
					errorCodes.put("empty.NameOfDisease", "empty.NameOfDisease");
				}
				if (scouting == null || StringUtil.isEmpty(scouting.getPerInfection())) {
					errorCodes.put("empty.PerInfection", "empty.PerInfection");
				}
			}

			if (scouting == null || StringUtil.isEmpty(scouting.getWeedsObserveds())) {
				errorCodes.put("empty.WeedsObserveds", "empty.WeedsObserveds");
			}

			if (scouting.getWeedsObserveds() != null && scouting.getWeedsObserveds().equalsIgnoreCase("1")) {
				// if (scouting == null ||
				// StringUtil.isEmpty(scouting.getWeedsPresence())) {
				// errorCodes.put("empty.WeedsPresence", "empty.WeedsPresence");
				// }
				// if (scouting == null ||
				// StringUtil.isEmpty(scouting.getRecommendations())) {
				// errorCodes.put("empty.Recommendations",
				// "empty.Recommendations");
				// }
				if (scouting == null || StringUtil.isEmpty(scouting.getNameOfWeeds())) {
					errorCodes.put("empty.nameOfWeeds", "empty.nameOfWeeds");
				}
			}
			if (scouting == null || StringUtil.isEmpty(scouting.getSourceOfWater())) {
				errorCodes.put("empty.SourceOfWater", "empty.SourceOfWater");
			}
			if (scouting == null || StringUtil.isEmpty(scouting.getIrrigationType())) {
				errorCodes.put("empty.IrrigationType", "empty.IrrigationType");
			}
			if (scouting == null || StringUtil.isEmpty(scouting.getIrrigationMethod())) {
				errorCodes.put("empty.IrrigationMethod", "empty.IrrigationMethod");
			}
			if (scouting == null || StringUtil.isEmpty(scouting.getAreaIrrrigated())) {
				errorCodes.put("empty.AreaIrrrigated", "empty.AreaIrrrigated");
			}

		} else {
			errorCodes.put("empty.fields", getLocaleProperty("empty.fields"));
		}
		printErrorCodes(errorCodes);
	}

	private Map<String, String> insectsObservedMap = new LinkedHashMap<>();

	public Map<String, String> getInsectsObservedMap() {
		insectsObservedMap.put("1", getText("Yes"));
		insectsObservedMap.put("0", getText("No"));

		return insectsObservedMap;
	}

	public void setInsectsObservedMap(Map<String, String> insectsObservedMap) {
		this.insectsObservedMap = insectsObservedMap;
	}

	private Map<String, String> diseaseObservedMap = new LinkedHashMap<>();

	public Map<String, String> getDiseaseObservedMap() {
		diseaseObservedMap.put("1", getText("Yes"));
		diseaseObservedMap.put("0", getText("No"));

		return diseaseObservedMap;
	}

	public void setDiseaseObservedMap(Map<String, String> diseaseObservedMap) {
		this.diseaseObservedMap = diseaseObservedMap;
	}

	private Map<String, String> weedsPresenceMapMap = new LinkedHashMap<>();

	public Map<String, String> getWeedsPresenceMapMap() {
		weedsPresenceMapMap.put("1", getText("Yes"));
		weedsPresenceMapMap.put("0", getText("No"));

		return weedsPresenceMapMap;
	}

	public void setWeedsPresenceMapMap(Map<String, String> weedsPresenceMapMap) {
		this.weedsPresenceMapMap = weedsPresenceMapMap;
	}

	private Map<String, String> weedsObservedsMap = new LinkedHashMap<>();

	public Map<String, String> getWeedsObservedsMap() {

		weedsObservedsMap.put("1", getText("Yes"));
		weedsObservedsMap.put("0", getText("No"));
		return weedsObservedsMap;
	}

	public void setWeedsObservedsMap(Map<String, String> weedsObservedsMap) {
		this.weedsObservedsMap = weedsObservedsMap;
	}

	public Map<String, String> getIrrigationMethod() {
		Map<String, String> irrigationMethod = new LinkedHashMap<>();

		irrigationMethod = getFarmCatalougeMap(Integer.valueOf(getText("irrigationMethod")));
		return irrigationMethod;
	}

	public Map<String, String> getIrrigationtype() {
		Map<String, String> irrigationtype = new LinkedHashMap<>();

		irrigationtype = getFarmCatalougeMap(Integer.valueOf(getText("irrigationtype")));
		return irrigationtype;
	}

	public Map<String, String> getNameOfInsectsObserved() {
		Map<String, String> nameOfInsectsObserved = new LinkedHashMap<>();

		nameOfInsectsObserved = getFarmCatalougeMap(Integer.valueOf(getText("nameOfInsectsObserved")));
		return nameOfInsectsObserved;
	}

	public Map<String, String> getNameOfDisease() {
		Map<String, String> nameOfDisease = new LinkedHashMap<>();

		nameOfDisease = getFarmCatalougeMap(Integer.valueOf(getText("nameOfDisease")));
		return nameOfDisease;
	}

	public Map<String, String> getNameOfWeeds() {
		Map<String, String> nameOfWeeds = new LinkedHashMap<>();
		nameOfWeeds = getFarmCatalougeMap(Integer.valueOf(getText("nameOfWeeds")));
		return nameOfWeeds;
	}

	public Map<String, String> getSourceOfWater() {
		Map<String, String> sourceOfWater = new LinkedHashMap<>();
		sourceOfWater = getFarmCatalougeMap(Integer.valueOf(getText("sourceOfWaterC")));
		return sourceOfWater;
	}

}