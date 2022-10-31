package com.sourcetrace.eses.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Country;
import com.sourcetrace.eses.entity.DocumentUpload;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Locality;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.entity.SitePrepration;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.entity.Village;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.FileUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class SitePreprationAction extends SwitchAction {
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
	private String FARMCROPS_BLOCK_QUERY="FROM FarmCrops fc WHERE fc.id=? AND fc.status=?";

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
	private String selectedProduct;
	@Getter
	@Setter
	private String selectedFarm;
	@Getter
	@Setter
	private SitePrepration sitePrepration;
	@Getter
	@Setter
	private File files;
	@Getter
	@Setter
	private String photoear;
	@Getter
	@Setter
	private File filesocr;
	@Getter
	@Setter
	private String photosocr;
	@Getter
	@Setter
	private File filesar;
	@Getter
	@Setter
	private String photosar;
	@Getter
	@Setter
	private File filewar;
	@Getter
	@Setter
	private String photowar;
	@Getter
	@Setter
	private String fileType;
	@Getter
	@Setter
	private String env;
	@Getter
	@Setter
	private String soc;
	@Getter
	@Setter
	private String soi;
	@Getter
	@Setter
	private String wat;
	@Getter
	@Setter
	private String envr;
	@Getter
	@Setter
	private String socr;
	@Getter
	@Setter
	private String soir;
	@Getter
	@Setter
	private String watr;
	@Getter
	@Setter
	private String farmCropfarmId;
	@Getter
	@Setter
	private String editFarmCropId;
	@Getter
	@Setter
	private String selectedFarms;
	@Getter
	@Setter
	private String selectedFarmCrops;

	public String create() throws Exception {
		if (selectedFarm == null) {
			command = CREATE;
			request.setAttribute(HEADING, "sitePreprationcreate");
			return INPUT;
		} else {
			SitePrepration sitePrepration = new SitePrepration();
			Long farmid = Long.valueOf(getSelectedFarm().split(",")[0].trim());
			Farm fm = (Farm) farmerService.findObjectById("FROM Farm where id=?", new Object[] { farmid });
			ProcurementVariety pv = (ProcurementVariety) farmerService.findObjectById(
					"FROM ProcurementVariety where id =?", new Object[] { Long.valueOf(getSelectedProduct()) });
			
			FarmCrops fc = (FarmCrops) farmerService.findObjectById("FROM FarmCrops where id=?", new Object[] { Long.valueOf(getSelectedFarmCrops()) });
		
			sitePrepration.setFarmCrops(fc);
			sitePrepration.setFarm(fm);
			sitePrepration.setPreviousCrop(pv);
			sitePrepration.setEnvironmentalAssessment(getEnv());
			sitePrepration.setSocialRiskAssessment(getSoc());
			sitePrepration.setSoilAnalysis(getSoi());
			sitePrepration.setWaterAnalysis(getWat());
			sitePrepration.setBranchId(getBranchId());
			if (files != null) {
				DocumentUpload du = new DocumentUpload();
				String[] tokens = photoear.split("\\.(?=[^\\.]+$)");
				String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
				if (!name.equals("") && name != null) {
					String filetype = tokens[1];
					if (tokens != null && filetype.equals("jpg") || filetype.equals("JPG") || filetype.equals("PNG")
							|| filetype.equals("jpeg") || filetype.equals("JPEG") || filetype.equals("png")
							|| filetype.equals("pdf") || filetype.equals("PDF") || filetype.equals("doc") || filetype.equals("DOC") || filetype.equals("docx") || filetype.equals("DOCX")
							|| filetype.equals("txt") || filetype.equals("TXT")) {
						du.setName(name);
						du.setContent(FileUtil.getBinaryFileContent(getFiles()));
						du.setDocFileContentType(tokens[1]);
						du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec()));
						du.setType(DocumentUpload.docType.farmer.ordinal());
						du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
						utilService.save(du);
						sitePrepration.setEnvironmentalAssessmentReport(du.getRefCode().toString());
					}
				}
			}
			if (filesocr != null) {
				DocumentUpload du = new DocumentUpload();
				String[] tokens = photosocr.split("\\.(?=[^\\.]+$)");
				String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
				if (!name.equals("") && name != null) {
					String filetype = tokens[1];
					if (tokens != null && filetype.equals("jpg") || filetype.equals("JPG") || filetype.equals("PNG")
							|| filetype.equals("jpeg") || filetype.equals("JPEG") || filetype.equals("png")
							|| filetype.equals("pdf") || filetype.equals("PDF") || filetype.equals("doc") || filetype.equals("DOC") || filetype.equals("docx") || filetype.equals("DOCX")
							|| filetype.equals("txt") || filetype.equals("TXT")) {
						du.setName(name);
						du.setContent(FileUtil.getBinaryFileContent(filesocr));
						du.setDocFileContentType(tokens[1]);
						du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec())+"1");
						du.setType(DocumentUpload.docType.farmer.ordinal());
						du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
						utilService.save(du);
						sitePrepration.setSocialRiskAssessmentReport(du.getRefCode().toString());
					}
				}
			}
			if (filesar != null) {
				DocumentUpload du = new DocumentUpload();
				String[] tokens = photosar.split("\\.(?=[^\\.]+$)");
				String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
				if (!name.equals("") && name != null) {
					String filetype = tokens[1];
					if (tokens != null && filetype.equals("jpg") || filetype.equals("JPG") || filetype.equals("PNG")
							|| filetype.equals("jpeg") || filetype.equals("JPEG") || filetype.equals("png")
							|| filetype.equals("pdf") || filetype.equals("PDF") || filetype.equals("doc") || filetype.equals("DOC") || filetype.equals("docx") || filetype.equals("DOCX")
							|| filetype.equals("txt") || filetype.equals("TXT")) {
						du.setName(name);
						du.setContent(FileUtil.getBinaryFileContent(filesar));
						du.setDocFileContentType(tokens[1]);
						du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec())+"2");
						du.setType(DocumentUpload.docType.farmer.ordinal());
						du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
						utilService.save(du);
						sitePrepration.setSoilAnalysisReport(du.getRefCode().toString());
					}
				}
			}
			if (filewar != null) {
				DocumentUpload du = new DocumentUpload();
				String[] tokens = photowar.split("\\.(?=[^\\.]+$)");
				String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
				if (!name.equals("") && name != null) {
					String filetype = tokens[1];
					if (tokens != null && filetype.equals("jpg") || filetype.equals("JPG") || filetype.equals("PNG")
							|| filetype.equals("jpeg") || filetype.equals("JPEG") || filetype.equals("png")
							|| filetype.equals("pdf") || filetype.equals("PDF") || filetype.equals("doc") || filetype.equals("DOC") || filetype.equals("docx") || filetype.equals("DOCX")
							|| filetype.equals("txt") || filetype.equals("TXT")) {
						du.setName(name);
						du.setContent(FileUtil.getBinaryFileContent(filewar));
						du.setDocFileContentType(tokens[1]);
						du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec())+"3");
						du.setType(DocumentUpload.docType.farmer.ordinal());
						du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
						utilService.save(du);
						sitePrepration.setWaterAnalysisReport(du.getRefCode().toString());
					}
				}
			}

			sitePrepration.setCreatedUser(getUsername());
			sitePrepration.setBranchId(getBranchId());
			sitePrepration.setStatus(1);
			sitePrepration.setStatusCode(0);
			sitePrepration.setLatitude(getLatitude());
			sitePrepration.setLongitude(getLongitude());
			utilService.save(sitePrepration);
		}
		return REDIRECT;
	}

	public String update() throws Exception {
		if (id != null && !id.equals("") && sitePrepration == null) {
			Long fid = Long.valueOf(id);
			sitePrepration = (SitePrepration) farmerService.findObjectById("FROM SitePrepration where id=?",
					new Object[] { Long.valueOf(id) });
				setSelectedProduct(sitePrepration.getPreviousCrop() != null
					? String.valueOf(sitePrepration.getPreviousCrop().getId()) : "");
			setSelectedFarm(sitePrepration.getFarm() != null ? String.valueOf(sitePrepration.getFarm().getId()) : "");
			setSelectedFarmer(sitePrepration.getFarm() != null
					? String.valueOf(sitePrepration.getFarm().getFarmer().getId()) : "");
			setSelectedFarmCrops(sitePrepration.getFarmCrops() != null ? String.valueOf(sitePrepration.getFarmCrops().getId()) : "");
			setEnv(sitePrepration.getEnvironmentalAssessment());
			setSoc(sitePrepration.getSocialRiskAssessment());
			setSoi(sitePrepration.getSoilAnalysis());
			setWat(sitePrepration.getWaterAnalysis());
			setEnvr(sitePrepration.getEnvironmentalAssessmentReport());
			setSocr(sitePrepration.getSocialRiskAssessmentReport());
			setSoir(sitePrepration.getSoilAnalysisReport());
			setWatr(sitePrepration.getWaterAnalysisReport());

			setSelectedVillage(sitePrepration.getFarm().getFarmer().getVillage() != null
					? String.valueOf(sitePrepration.getFarm().getFarmer().getVillage().getId()) : "");
			setSelectedCity(sitePrepration.getFarm().getFarmer().getVillage() != null
					? String.valueOf(sitePrepration.getFarm().getFarmer().getVillage().getCity().getId()) : "");
			setSelectedLocality(sitePrepration.getFarm().getFarmer().getVillage() != null
					? String.valueOf(sitePrepration.getFarm().getFarmer().getVillage().getCity().getLocality().getId())
					: "");
			setSelectedState(sitePrepration.getFarm().getFarmer().getVillage() != null ? String.valueOf(
					sitePrepration.getFarm().getFarmer().getVillage().getCity().getLocality().getState().getId()) : "");
			setSelectedCountry(sitePrepration.getFarm().getFarmer().getVillage() != null ? sitePrepration.getFarm()
					.getFarmer().getVillage().getCity().getLocality().getState().getCountry().getName() : "");
			setCurrentPage(getCurrentPage());
			
			if (sitePrepration.getFarmCrops() != null && !StringUtil.isEmpty(sitePrepration.getFarmCrops().getId())) {
				FarmCrops fc = (FarmCrops) farmerService.findObjectById(" from FarmCrops fc where fc.id=?  and fc.status=1",
						new Object[] { Long.valueOf(sitePrepration.getFarmCrops().getId()) });
				setEditFarmCropId(fc.getId() != null ? String.valueOf(fc.getId()) : "");
				setFarmCropfarmId(fc.getFarm().getId() != null ? String.valueOf(fc.getFarm().getId()) : "");
				
				}

			command = UPDATE;
			request.setAttribute(HEADING, getText("sitePreprationupdate"));
		} else {

			if (sitePrepration != null) {

				SitePrepration temp = (SitePrepration) farmerService.findObjectById("FROM SitePrepration where id=?",
						new Object[] { Long.valueOf(id) });

				if (temp == null) {
					addActionError(NO_RECORD);
					return REDIRECT;
				}
				
				if (sitePrepration.getFarmCrops() != null && !StringUtil.isEmpty(sitePrepration.getFarmCrops().getId())) {
					FarmCrops farmCrops=(FarmCrops) farmerService.findObjectById(FARMCROPS_BLOCK_QUERY, new Object[]{Long.valueOf(sitePrepration.getFarmCrops().getId()),1});
					
					temp.setFarmCrops(farmCrops);
					}
				temp.setBranchId(getBranchId());
				setCurrentPage(getCurrentPage());
				temp.setBranchId(getBranchId());
				Long farmid = Long.valueOf(getSelectedFarm().split(",")[0].trim());
				Farm fm = (Farm) farmerService.findObjectById("FROM Farm where id=?", new Object[] { farmid });
				FarmCrops fc = (FarmCrops) farmerService.findObjectById("FROM FarmCrops where id=?", new Object[] { Long.valueOf(getSelectedFarmCrops()) });
				
				temp.setFarmCrops(fc);
				ProcurementVariety pv = (ProcurementVariety) farmerService.findObjectById(
						"FROM ProcurementVariety where id =?", new Object[] { Long.valueOf(getSelectedProduct()) });
				temp.setPreviousCrop(pv);
				temp.setFarm(fm);
				temp.setEnvironmentalAssessment(getEnv());
				temp.setSocialRiskAssessment(getSoc());
				temp.setSoilAnalysis(getSoi());
				temp.setWaterAnalysis(getWat());
				if (files != null) {
					DocumentUpload du = new DocumentUpload();
					String[] tokens = photoear.split("\\.(?=[^\\.]+$)");
					String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
					if (!name.equals("") && name != null) {
						String filetype = tokens[1];

						if (tokens != null && filetype.equals("jpg") || filetype.equals("JPG") || filetype.equals("PNG")
								|| filetype.equals("jpeg") || filetype.equals("JPEG") || filetype.equals("png")
								|| filetype.equals("pdf") || filetype.equals("PDF") || filetype.equals("doc") || filetype.equals("DOC") || filetype.equals("docx") || filetype.equals("DOCX")
								|| filetype.equals("txt") || filetype.equals("TXT")) {
							du.setName(name);
							du.setContent(FileUtil.getBinaryFileContent(getFiles()));
							du.setDocFileContentType(tokens[1]);
							du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec()));
							du.setType(DocumentUpload.docType.farmer.ordinal());
							du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
							utilService.save(du);
							temp.setEnvironmentalAssessmentReport(du.getRefCode().toString());
						}
					}
				}

				if (filesocr != null) {
					DocumentUpload du = new DocumentUpload();
					String[] tokens = photosocr.split("\\.(?=[^\\.]+$)");
					String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
					if (!name.equals("") && name != null) {
						String filetype = tokens[1];
						if (tokens != null && filetype.equals("jpg") || filetype.equals("JPG") || filetype.equals("PNG")
								|| filetype.equals("jpeg") || filetype.equals("JPEG") || filetype.equals("png")
								|| filetype.equals("pdf") || filetype.equals("PDF") || filetype.equals("doc") || filetype.equals("DOC") || filetype.equals("docx") || filetype.equals("DOCX")
								|| filetype.equals("txt") || filetype.equals("TXT")) {
							du.setName(name);
							du.setContent(FileUtil.getBinaryFileContent(filesocr));
							du.setDocFileContentType(tokens[1]);
							du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec())+"1");
							du.setType(DocumentUpload.docType.farmer.ordinal());
							du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
							utilService.save(du);
							temp.setSocialRiskAssessmentReport(du.getRefCode().toString());
						}
					}
				}
				if (filesar != null) {
					DocumentUpload du = new DocumentUpload();
					String[] tokens = photosar.split("\\.(?=[^\\.]+$)");
					String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
					if (!name.equals("") && name != null) {
						String filetype = tokens[1];
						if (tokens != null && filetype.equals("jpg") || filetype.equals("JPG") || filetype.equals("PNG")
								|| filetype.equals("jpeg") || filetype.equals("JPEG") || filetype.equals("png")
								|| filetype.equals("pdf") || filetype.equals("PDF") || filetype.equals("doc") || filetype.equals("DOC") || filetype.equals("docx") || filetype.equals("DOCX")
								|| filetype.equals("txt") || filetype.equals("TXT")) {
							du.setName(name);
							du.setContent(FileUtil.getBinaryFileContent(filesar));
							du.setDocFileContentType(tokens[1]);
							du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec())+"2");
							du.setType(DocumentUpload.docType.farmer.ordinal());
							du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
							utilService.save(du);
							temp.setSoilAnalysisReport(du.getRefCode().toString());
						}
					}
				}
				if (filewar != null) {
					DocumentUpload du = new DocumentUpload();
					String[] tokens = photowar.split("\\.(?=[^\\.]+$)");
					String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
					if (!name.equals("") && name != null) {
						String filetype = tokens[1];
						if (tokens != null && filetype.equals("jpg") || filetype.equals("JPG") || filetype.equals("PNG")
								|| filetype.equals("jpeg") || filetype.equals("JPEG") || filetype.equals("png")
								|| filetype.equals("pdf") || filetype.equals("PDF") || filetype.equals("doc") || filetype.equals("DOC") || filetype.equals("docx") || filetype.equals("DOCX")
								|| filetype.equals("txt") || filetype.equals("TXT")) {
							du.setName(name);
							du.setContent(FileUtil.getBinaryFileContent(filewar));
							du.setDocFileContentType(tokens[1]);
							du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec())+"3");
							du.setType(DocumentUpload.docType.farmer.ordinal());
							du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
							utilService.save(du);
							temp.setWaterAnalysisReport(du.getRefCode().toString());
						}
					}
				}

				temp.setCreatedUser(getUsername());
				temp.setCreatedDate(new Date());
				temp.setIsActive(1l);
				temp.setRevisionNo(DateUtil.getRevisionNumber());
				utilService.update(temp);
			}
			request.setAttribute(HEADING, getText("farmerlist"));
			return REDIRECT;
		}
		return super.execute();

	}

	public String detail() throws Exception {
		String view = null;
		if (id != null && !id.equals("")) {
			 sitePrepration = (SitePrepration) farmerService
					.findObjectById("FROM SitePrepration where id=?", new Object[] { Long.valueOf(id) });
			if (sitePrepration == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			setSelectedProduct(sitePrepration.getPreviousCrop() != null
					? String.valueOf(sitePrepration.getPreviousCrop().getId()) : "");
			setSelectedFarm(sitePrepration.getFarm() != null ? String.valueOf(sitePrepration.getFarm().getId()) : "");
			setSelectedFarmer(sitePrepration.getFarm() != null
					? String.valueOf(sitePrepration.getFarm().getFarmer().getId()) : "");
			
			if (sitePrepration.getFarmCrops() != null && !StringUtil.isEmpty(sitePrepration.getFarmCrops())) {
				FarmCrops fc = (FarmCrops) farmerService.findObjectById(" from FarmCrops fc where fc.id=?  and fc.status=1",
						new Object[] { Long.valueOf(sitePrepration.getFarmCrops().getId()) });
				
				setFarmCropfarmId(fc.getBlockName() != null ? String.valueOf(fc.getBlockId()+"-"+fc.getBlockName()) : "");
				
				}
			
			setEnv(sitePrepration.getEnvironmentalAssessment());
			setSoc(sitePrepration.getSocialRiskAssessment());
			setSoi(sitePrepration.getSoilAnalysis());
			setWat(sitePrepration.getWaterAnalysis());
			setSelectedFarmer(sitePrepration.getFarm().getFarmer().getFirstName());
			setSelectedFarm(sitePrepration.getFarm().getFarmName());
			setSelectedProduct(sitePrepration.getPreviousCrop().getName());
			setEnvr(sitePrepration.getEnvironmentalAssessmentReport());
			setSocr(sitePrepration.getSocialRiskAssessmentReport());
			setSoir(sitePrepration.getSoilAnalysisReport());
			setWatr(sitePrepration.getWaterAnalysisReport());
			setCurrentPage(getCurrentPage());
			command = UPDATE;
			view = DETAIL;
			request.setAttribute(HEADING, getText("sitePreprationDetail"));
		} else {
			request.setAttribute(HEADING, getText(LIST));
			return REDIRECT;
		}
		return view;
	}

	public String delete() throws Exception {
		String result = null;
		if (id != null && !id.equals("")) {
			SitePrepration sitePrepration = (SitePrepration) farmerService
					.findObjectById("FROM SitePrepration where id=?", new Object[] { Long.valueOf(id) });
			if (sitePrepration == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(sitePrepration)) {
				sitePrepration.setStatus(0);
				utilService.update(sitePrepration);
				result = REDIRECT;
			}
		}

		return result;
	}
	
	public void populatBlocks() {
		JSONArray farmerArr = new JSONArray();
		if (selectedFarmer != null && !ObjectUtil.isEmpty(selectedFarmer) && !selectedFarmer.equals("")
				&& selectedFarms != null && !ObjectUtil.isEmpty(selectedFarms)) {
			
			LinkedList<Object> parame = new LinkedList();
			String qry = "FROM FarmCrops f  where f.farm.id=?  and f.status=1 ORDER BY f.id ASC";
			parame.add(Long.valueOf(selectedFarms));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry = "FROM FarmCrops f  where f.farm.id=?  and f.status=1 and f.exporter.id=?  ORDER BY f.id ASC";
				parame.add(Long.valueOf(getLoggedInDealer()));
			}
			List<FarmCrops> growerList = (List<FarmCrops>) farmerService.listObjectById(qry, parame.toArray());
			//List<FarmCrops> dataList = utilService.listFarmCropByFarmId(Long.valueOf(selectedFarms));
			growerList.stream().distinct().forEach(f -> {
				farmerArr.add(getJSONObject(f.getId(), f.getBlockId() + " - " + f.getBlockName().toString()));
			});
		}
		sendAjaxResponse(farmerArr);
	}

	public void populateValidate() {

		Map<String, String> errorCodes = new LinkedHashMap<String, String>();
		if (getSelectedFarmer() == null || StringUtil.isEmpty(getSelectedFarmer()) || getSelectedFarmer().equals("")) {
			errorCodes.put("empty.sitePrepration.farmer", "empty.sitePrepration.farmer");
		}
		if (getSelectedFarm() == null || StringUtil.isEmpty(getSelectedFarm()) || getSelectedFarm().equals("")) {
			errorCodes.put("empty.farm", "empty.farm");
		}
		
		if (getSelectedFarmCrops() == null || StringUtil.isEmpty(getSelectedFarmCrops()) || getSelectedFarmCrops().equals("")) {

			errorCodes.put("empty.block", "empty.block");
		}
		if (getSelectedProduct() == null || StringUtil.isEmpty(getSelectedProduct())
				|| getSelectedProduct().equals("")) {
			errorCodes.put("emptySite.crop", "emptySite.crop");
		}
		if (getEnv() == null || StringUtil.isEmpty(getEnv()) || getEnv().equals("")) {
			errorCodes.put("empty.envassesment", "empty.envassesment");
		} else if (getEnv() != null && getEnv().equals("1")
				&& (files == null && StringUtil.isEmpty(sitePrepration.getEnvironmentalAssessmentReport()))) {
			errorCodes.put("empty.sitePrepration.envFile", "empty.sitePrepration.envFile");
		}
		if (getSoc() == null || StringUtil.isEmpty(getSoc()) || getSoc().equals("")) {
			errorCodes.put("empty.socialrisk", "empty.socialrisk");
		} else if (getSoc() != null && getSoc().equals("1")
				&& (filesocr == null && StringUtil.isEmpty(sitePrepration.getSocialRiskAssessmentReport()))) {
			errorCodes.put("empty.sitePrepration.filesocr", "empty.sitePrepration.filesocr");
		}
		if (getSoi() == null || StringUtil.isEmpty(getSoi()) || getSoi().equals("")) {
			errorCodes.put(getLocaleProperty("empty.soil"), getLocaleProperty("empty.soil"));
		} else if (getSoi() != null && getSoi().equals("1")
				&& (filesar == null && StringUtil.isEmpty(sitePrepration.getSoilAnalysisReport()))) {
			errorCodes.put("empty.sitePrepration.filesar", "empty.sitePrepration.filesar");
		}
		if (getWat() == null || StringUtil.isEmpty(getWat()) || getWat().equals("")) {
			errorCodes.put(getLocaleProperty("empty.water"), getLocaleProperty("empty.water"));
		} else if (getWat() != null && getWat().equals("1")
				&& (filewar == null && StringUtil.isEmpty(sitePrepration.getWaterAnalysisReport()))) {
			errorCodes.put("empty.sitePrepration.filewar", "empty.sitePrepration.filewar");
		}
		printErrorCodes(errorCodes);
	}

	public void populateFarmerByAuditRequest() throws Exception {
		JSONArray farmerArr = new JSONArray();
		List<Object[]> dataList = new ArrayList();
		String qry = "SELECT DISTINCT f.id,f.farmerId,f.firstName,f.lastName from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1";
		LinkedList<Object> parame = new LinkedList();

		if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
			qry = "SELECT DISTINCT f.id,f.farmerId,f.firstName,f.lastName from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1 and fc.exporter.id=?";
			parame.add(getLoggedInDealer());

		}

		if (getSelectedVillage() != null && StringUtil.isLong(getSelectedVillage())
				&& Long.valueOf(selectedVillage) > 0) {
			qry += " and f.village.id=?";
			parame.add(Long.valueOf(selectedVillage));

		}
		dataList = (List<Object[]>) farmerService.listObjectById(qry, parame.toArray());
		if (dataList != null && !ObjectUtil.isEmpty(dataList)) {
			/*dataList.stream().distinct().forEach(f -> {
				farmerArr.add(getJSONObject(f[0].toString(),
						(f[1].toString() +" - "+f[2].toString() + " " + (f[3] != null && !ObjectUtil.isEmpty(f[3]) ? f[3].toString() : ""))));
			});*/
			
			for(Object f[]:dataList){
					 List<Farm> farm = utilService.listFarmByFarmerId(Long.valueOf(f[0].toString()));
				if(!StringUtil.isListEmpty(farm)){
					
					farmerArr.add(getJSONObject(f[0].toString(),(f[1].toString() +" - "+f[2].toString() + " " + (f[3] != null && !ObjectUtil.isEmpty(f[3]) ? f[3].toString() : ""))));
				}
			}
			
		}

		sendAjaxResponse(farmerArr);

	}
	public void populateFarm() throws Exception {
		
		JSONArray farmerArr = new JSONArray();
		if (selectedFarmer != null && !ObjectUtil.isEmpty(selectedFarmer) && !selectedFarmer.equals("")
				&& selectedFarmer != null && !ObjectUtil.isEmpty(selectedFarmer)) {
			LinkedList<Object> parame = new LinkedList();
			String qry = "SELECT DISTINCT f.id,f.farmCode,f.farmName from FarmCrops fc join fc.farm f where f.status=1 and f.farmer.id=? and fc.status=1";
			parame.add(Long.valueOf(selectedFarmer));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry = "SELECT DISTINCT f.id,f.farmCode,f.farmName from FarmCrops fc join fc.farm f where f.status=1 and f.farmer.id=? and fc.status=1 and fc.exporter.id=?";
				parame.add(Long.valueOf(getLoggedInDealer()));
			}
			List<Object[]> growerList = (List<Object[]>) farmerService.listObjectById(qry, parame.toArray());
			//List<FarmCrops> dataList = utilService.listFarmCropByFarmId(Long.valueOf(selectedFarm));
			growerList.stream().distinct().forEach(f -> {
				farmerArr.add(getJSONObject(f[0].toString(), f[1].toString()+"-"+f[2].toString()));
			});
		}
		sendAjaxResponse(farmerArr);
	}

	

	public void populateState() throws Exception {

		JSONArray stateArr = new JSONArray();
		if (!selectedCountry.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedCountry))) {
			
			LinkedList<Object> parame = new LinkedList();
			String qry="select distinct f.village.city.locality.state from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1 and f.village.city.locality.state.country.name =? ";
			parame.add(selectedCountry);
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry="select distinct f.village.city.locality.state from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1 and f.village.city.locality.state.country.name =?  and fc.exporter.id=?";
				parame.add(getLoggedInDealer());
			}
			
			
			List<State> states =(List<State>)farmerService.listObjectById(qry, parame.toArray());
			if (!ObjectUtil.isEmpty(states)) {
				for (State state : states) {

					String name = getLanguagePref(getLoggedInUserLanguage(), state.getCode().trim().toString());
					if (!StringUtil.isEmpty(name) && name != null) {
						stateArr.add(getJSONObject(String.valueOf(state.getId()), state.getCode().toString() + "-"
								+ getLanguagePref(getLoggedInUserLanguage(), state.getCode().toString())));
					} else {
						stateArr.add(
								getJSONObject(String.valueOf(state.getId()), state.getCode() + "-" + state.getName()));
					}
				}
			}
		}

		sendAjaxResponse(stateArr);
	}

	public void populateLocality() throws Exception {
		JSONArray localtiesArr = new JSONArray();
		if (!selectedState.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedState))) {
			
			LinkedList<Object> parame = new LinkedList();
			String qry="select distinct f.village.city.locality from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1 and f.village.city.locality.state.id =? ";
			parame.add(Long.valueOf(selectedState));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry="select distinct f.village.city.locality from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1 and f.village.city.locality.state.id =?   and fc.exporter.id=?";
				parame.add(getLoggedInDealer());
			}
			
				
			List<Locality> listLocalities =(List<Locality>)farmerService.listObjectById(qry, parame.toArray());
				if (!ObjectUtil.isEmpty(listLocalities)) {
				for (Locality locality : listLocalities) {

					String name = getLanguagePref(getLoggedInUserLanguage(), locality.getCode().trim().toString());
					if (!StringUtil.isEmpty(name) && name != null) {
						localtiesArr.add(getJSONObject(String.valueOf(locality.getId()),
								locality.getCode().trim().toString() + "-" + getLanguagePref(getLoggedInUserLanguage(),
										locality.getCode().trim().toString())));
					} else {
						localtiesArr.add(getJSONObject(String.valueOf(locality.getId()),
								locality.getCode() + "-" + locality.getName()));
					}
				}
			}
		}

		sendAjaxResponse(localtiesArr);

	}

	public void populateMunicipality() throws Exception {
		JSONArray citiesArr = new JSONArray();
		if (!selectedLocality.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedLocality))) {
			
			LinkedList<Object> parame = new LinkedList();
			String qry="select distinct f.village.city from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1 and f.village.city.locality.id =? ";
			parame.add(Long.valueOf(selectedLocality));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry="select distinct f.village.city from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1 and f.village.city.locality.id =?   and fc.exporter.id=?";
				parame.add(getLoggedInDealer());
			}
			
			
			List<Municipality> listCity =(List<Municipality>)farmerService.listObjectById(qry,parame.toArray());
			if (!ObjectUtil.isEmpty(listCity)) {
				for (Municipality city : listCity) {

					String name = getLanguagePref(getLoggedInUserLanguage(), city.getCode().trim().toString());
					if (!StringUtil.isEmpty(name) && name != null) {
						citiesArr.add(getJSONObject(String.valueOf(city.getId()), city.getCode().trim().toString() + "-"
								+ getLanguagePref(getLoggedInUserLanguage(), city.getCode().trim().toString())));
					} else {
						citiesArr.add(
								getJSONObject(String.valueOf(city.getId()), city.getCode() + "-" + city.getName()));
					}
				}
			}
		}

		sendAjaxResponse(citiesArr);

	}

	public void populateVillage() throws Exception {

		JSONArray villageArr = new JSONArray();
		if (!selectedCity.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedCity))) {
			
			LinkedList<Object> parame = new LinkedList();
			String qry="select distinct f.village from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1 and f.village.city.id =? ";
			parame.add(Long.valueOf(selectedCity));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry="select distinct f.village from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1 and f.village.city.id =?    and fc.exporter.id=?";
				parame.add(getLoggedInDealer());
			}
			
			
			List<Village> listVillages =(List<Village>)farmerService.listObjectById(qry, parame.toArray());
			
				if (!ObjectUtil.isEmpty(listVillages)) {
				for (Village village : listVillages) {

					String name = getLanguagePref(getLoggedInUserLanguage(), village.getCode().trim().toString());
					if (!StringUtil.isEmpty(name) && name != null) {
						villageArr.add(getJSONObject(String.valueOf(village.getId()),
								village.getCode().trim().toString() + "-" + getLanguagePref(getLoggedInUserLanguage(),
										village.getCode().trim().toString())));
					} else {
						villageArr.add(getJSONObject(String.valueOf(village.getId()),
								village.getCode() + "-" + village.getName()));
					}
				}
			}
		}

		sendAjaxResponse(villageArr);

	}
	

	public Map<String, String> getCountries() {

		Map<String, String> countryMap = new LinkedHashMap<String, String>();
		LinkedList<Object> parame = new LinkedList();
		String qry="select distinct f.village.city.locality.state.country from Farm fm join fm.farmer f where f.status=1 ";
		if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
			qry="select distinct f.village.city.locality.state.country from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1 and fc.exporter.id=?";
			parame.add(getLoggedInDealer());
		}
		List<Country> countryList =(List<Country>)farmerService.listObjectById(qry,parame.toArray());
		for (Country obj : countryList) {
			countryMap.put(obj.getName(), obj.getCode() + "-" + obj.getName());
		}
		return countryMap;
	}// thisis loading country

}
