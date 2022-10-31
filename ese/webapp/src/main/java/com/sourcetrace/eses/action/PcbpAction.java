package com.sourcetrace.eses.action;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.entity.Pcbp;
import com.sourcetrace.eses.entity.ProcurementGrade;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class PcbpAction extends SwitchAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(PcbpAction.class);

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
	private Pcbp pcbp;

	@Getter
	@Setter
	private String id;

	@Autowired
	private IUtilService utilService;

	@Getter
	@Setter
	private String redirectContent;
	
	@Getter
	@Setter
	private String selectedVariety;
	
	@Getter
	@Setter
	private String selectedCropName;
	
	@Getter
	@Setter
	private String selectedCropCat;

	/**
	 * Creates the.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String create() throws Exception {
		String defBranchId = utilService.findPrefernceByName(ESESystem.DEF_BRANCH);
		if (pcbp == null) {
			command = "create";

			request.setAttribute(HEADING, getText("pcbpcreate"));

			if (request != null && request.getSession() != null) {
				request.getSession().setAttribute(ISecurityFilter.REPORT, "PCBP");
			}
			return INPUT;
		} else {
			pcbp.setCreatedDate(new Date());
			pcbp.setBranchId(getBranchId() != null ? getBranchId() : defBranchId);
			
			
			ProcurementGrade pv = (ProcurementGrade) farmerService.findObjectById(
					"FROM ProcurementGrade where id =?", new Object[] { Long.valueOf(getSelectedVariety()) });
/*
			// crop category
			pcbp.setCropcat(
					pcbp.getCropcat() != null && !StringUtil.isEmpty(pcbp.getCropcat()) ? pcbp.getCropcat() : null);
			// crop Name
			pcbp.setCropName(
					pcbp.getCropName() != null && !StringUtil.isEmpty(pcbp.getCropName()) ? pcbp.getCropName() : null);
			// crop variety
*/			pcbp.setCropvariety(pv);

			pcbp.setTradeName(pcbp.getTradeName());

			pcbp.setRegistrationNo(pcbp.getRegistrationNo());

			pcbp.setActiveing(pcbp.getActiveing());

			pcbp.setManufacturerReg(pcbp.getManufacturerReg());

			pcbp.setAgent(pcbp.getAgent());

			pcbp.setPhiIn(pcbp.getPhiIn());

			pcbp.setDosage(pcbp.getDosage());

			pcbp.setUom(pcbp.getUom());

		/*	pcbp.setChemicalName(pcbp.getChemicalName());*/

			pcbp.setStatus(1);
			
			pcbp.setLatitude(getLatitude());
			pcbp.setLongitude(getLongitude());

			utilService.save(pcbp);

			return REDIRECT;
		}

	}

	/**
	 * Delete.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */

	public String delete() throws Exception {
		String result = null;
		if (this.getId() != null && !(this.getId().equals(EMPTY))) {
			pcbp = utilService.findPcbpById(Long.valueOf(id));
			if (pcbp == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(pcbp)) {
				//pcbp.setStatus(Integer.valueOf(0));

				utilService.delete(pcbp);
				result = REDIRECT;
			}
		}

		request.setAttribute(HEADING, getText("pcbplist"));
		return result;

	}

	/**
	 * Detail.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String detail() throws Exception {
		String view = "";
		if (id != null && !id.equals("")) {
			// expRegis = utilService.findExportRegById(Long.valueOf(id));
			pcbp = utilService.findPcbpById(Long.valueOf(id));
			setApprovalStatus(pcbp.getStatus());
			
			ProcurementGrade pg = (ProcurementGrade) farmerService.findObjectById(
					"FROM ProcurementGrade where id =?", new Object[] { Long.valueOf(pcbp.getCropvariety().getId()) });
			pcbp.setCropvariety(pg);
			setSelectedVariety(String.valueOf(pg.getId()));
			ProcurementVariety pv=pg.getProcurementVariety();
			setSelectedCropName(String.valueOf(pv.getId()));
			
			
			pcbp.setTradeName(pcbp.getTradeName());

			pcbp.setRegistrationNo(pcbp.getRegistrationNo());

			pcbp.setActiveing(pcbp.getActiveing());

			pcbp.setManufacturerReg(pcbp.getManufacturerReg());

			pcbp.setAgent(pcbp.getAgent());

			pcbp.setPhiIn(pcbp.getPhiIn());

			pcbp.setDosage(pcbp.getDosage());

			pcbp.setUom(pcbp.getUom());
			/*pcbp.setChemicalName(pcbp.getChemicalName());*/

			setCurrentPage(getCurrentPage());
			command = UPDATE;
			view = DETAIL;
			request.setAttribute(HEADING, getText("pcbpdetail"));
		} else {
			request.setAttribute(HEADING, getText("pcbpdetail"));
			return REDIRECT;
		}
		return view;
	}

	/**
	 * Update.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String update() throws Exception {

		if (id != null && !id.equals("") && pcbp == null) {
			pcbp = utilService.findPcbpById(Long.valueOf(id));
			setCurrentPage(getCurrentPage());
			ProcurementGrade pg = (ProcurementGrade) farmerService.findObjectById(
					"FROM ProcurementGrade where id =?", new Object[] { Long.valueOf(pcbp.getCropvariety().getId()) });
			pcbp.setCropvariety(pg);
			setSelectedVariety(String.valueOf(pg.getId()));
			ProcurementVariety pv=pg.getProcurementVariety();
			setSelectedCropName(String.valueOf(pv.getId()));
			setSelectedCropCat(String.valueOf(pv.getProcurementProduct().getId()));
			
			
			command = UPDATE;
			request.setAttribute(HEADING, getText("pcbpupdate"));
		} else {

			Pcbp tempPcbp = utilService.findPcbpById(Long.valueOf(id));
			setCurrentPage(getCurrentPage());
			tempPcbp.setBranchId(getBranchId());

			ProcurementGrade pv = (ProcurementGrade) farmerService.findObjectById(
					"FROM ProcurementGrade where id =?", new Object[] { Long.valueOf(getSelectedVariety()) });
			tempPcbp.setCropvariety(pv);

			tempPcbp.setTradeName(pcbp.getTradeName());

			tempPcbp.setRegistrationNo(pcbp.getRegistrationNo());

			tempPcbp.setActiveing(pcbp.getActiveing());

			tempPcbp.setManufacturerReg(pcbp.getManufacturerReg());

			tempPcbp.setAgent(pcbp.getAgent());

			tempPcbp.setPhiIn(pcbp.getPhiIn());

			tempPcbp.setDosage(pcbp.getDosage());

			tempPcbp.setUom(pcbp.getUom());
			/*tempPcbp.setChemicalName(pcbp.getChemicalName());*/

			//tempPcbp.setStatus(StringUtil.isEmpty(pcbp.getStatus()) ? 0 : tempPcbp.getStatus());

			utilService.update(tempPcbp);

			request.setAttribute(HEADING, getText("pcbplist"));
			return REDIRECT;

		}

		return super.execute();
	}

	public void populateValidate() {

		Map<String, String> errorCodes = new LinkedHashMap<String, String>();

		if (pcbp != null) {
			if (pcbp == null || StringUtil.isEmpty(selectedCropCat)) {
				errorCodes.put("empty.selectedCropCat", "empty.selectedCropCat");
			}
			if (pcbp == null || StringUtil.isEmpty(getSelectedVariety())) {
				errorCodes.put("empty.cropvariety", "empty.cropvariety");
			}
			if (pcbp == null || StringUtil.isEmpty(getSelectedCropName())) {
				errorCodes.put("empty.cropName", "empty.cropName");
			}
			
			if (pcbp == null || StringUtil.isEmpty(pcbp.getTradeName())) {
				errorCodes.put("empty.tradeName", "empty.tradeName");
			}
			if (pcbp == null || StringUtil.isEmpty(pcbp.getRegistrationNo())) {
				errorCodes.put("empty.registrationNo", "empty.registrationNo");
			}
			if (pcbp == null || StringUtil.isEmpty(pcbp.getActiveing())) {
				errorCodes.put("empty.activeing", "empty.activeing");
			}
			if (pcbp == null || StringUtil.isEmpty(pcbp.getManufacturerReg())) {
				errorCodes.put("empty.manufacturerReg", "empty.manufacturerReg");
			}
			if (pcbp == null || StringUtil.isEmpty(pcbp.getAgent())) {
				errorCodes.put("empty.agent", "empty.agent");
			}
			/*if (pcbp == null || StringUtil.isEmpty(pcbp.getPhiIn())) {
				errorCodes.put("empty.phiIn", "empty.phiIn");
			}*/
			if (pcbp == null || StringUtil.isEmpty(pcbp.getDosage())) {
				errorCodes.put("empty.dosage", "empty.dosage");
			}
			if (pcbp == null || StringUtil.isEmpty(pcbp.getUom())) {
				errorCodes.put("empty.uom", "empty.uom");
			}
			/*if (pcbp == null || StringUtil.isEmpty(pcbp.getChemicalName())) {
				errorCodes.put("empty.ChemicalName", "empty.ChemicalName");
			}*/
		/*	if(command.equalsIgnoreCase("create")){
				if (!StringUtil.isEmpty(getSelectedVariety()) && !StringUtil.isEmpty(pcbp.getChemicalName())) {
					ProcurementGrade pv = (ProcurementGrade) farmerService.findObjectById(
								"FROM ProcurementGrade where id =?", new Object[] { Long.valueOf(getSelectedVariety()) });
						
						//Pcbp pcbpVaCh = utilService.findPcbpByvarietyAndChamical(String.valueOf(pv.getId()), pcbp.getChemicalName());
						Pcbp pcbpVaCh = utilService.findPcbpByvarietyAndChamical(Long.valueOf(getSelectedVariety()), pcbp.getChemicalName());
						if (pcbpVaCh != null) {
							errorCodes.put("unique.ChemicalVariety", "unique.ChemicalVariety");
						}
					}
			}
			if(command.equalsIgnoreCase("update")){
				if (!StringUtil.isEmpty(getSelectedVariety()) && !StringUtil.isEmpty(pcbp.getChemicalName())) {
					ProcurementGrade pv = (ProcurementGrade) farmerService.findObjectById(
							"FROM ProcurementGrade where id =?", new Object[] { Long.valueOf(getSelectedVariety()) });
					
					Pcbp pcbpVaCh = utilService.findPcbpByvarietyAndChamical(Long.valueOf(getSelectedVariety()), pcbp.getChemicalName());
					if(pcbpVaCh != null){
						if (pcbpVaCh.getId()!=pcbp.getId()) {
							errorCodes.put("unique.ChemicalVariety", "unique.ChemicalVariety");
						}
					}
				}
			}*/
			


		} else {
			errorCodes.put("empty.fields", getLocaleProperty("empty.fields"));
		}
		printErrorCodes(errorCodes);
	}

	public void populateVarietyPcbp() throws Exception {
		if (selectedProduct != null) {

			JSONArray varietyArr = new JSONArray();
			varietyArr.add(getJSONObject("", "Select"));
			Arrays.asList(selectedProduct.split(",")).stream().forEach(u -> {
				List<ProcurementVariety> varietiesList = utilService.listProcurementVarietyByProcurementProductId(Long.valueOf(u));
				varietiesList.stream().filter(obj -> !ObjectUtil.isEmpty(obj)).forEach(obj -> {
					varietyArr.add(getJSONObject(obj.getId(), obj.getName()));
				});
			});
			sendAjaxResponse(varietyArr);

		}
	}

	public void populateGradePcbp() throws Exception {
		if (!StringUtil.isEmpty(procurementVariety)) {
			List<ProcurementGrade> gradeList = utilService
					.listProcurementGradeByProcurementVarietyId(Long.valueOf(procurementVariety));
			JSONArray gradeArr = new JSONArray();
			gradeArr.add(getJSONObject("", "Select"));
			gradeList.stream().filter(obj -> !ObjectUtil.isEmpty(obj)).forEach(obj -> {
				gradeArr.add(getJSONObject(obj.getId(), obj.getName()));
			});
			sendAjaxResponse(gradeArr);
		}

	}

	public Map<String, String> getChemicalList() {
		Map<String, String> chemicalList = new LinkedHashMap<>();
		chemicalList = getFarmCatalougeMap(Integer.valueOf(getText("agroCheList")));
		return chemicalList;
	}

/*	public List<FarmCatalogue> getListUom() {
		List<FarmCatalogue> subUomList = utilService.listCataloguesByUnit();

		return subUomList;
	}*/
	public Map<String, String> getListUom() {
		Map<String, String> subUomList = new LinkedHashMap<>();
		subUomList = getFarmCatalougeMap(Integer.valueOf(getText("umoListType")));
		return subUomList;
	}
	
	
	public List<ProcurementVariety> getProcurementVarietyList(){
		List<ProcurementVariety> pv = utilService.listProcurementVariety();
		
		return pv;
	}

}
