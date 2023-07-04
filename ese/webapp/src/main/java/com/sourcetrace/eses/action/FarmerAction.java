package com.sourcetrace.eses.action;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Country;
import com.sourcetrace.eses.entity.DocumentUpload;
import com.sourcetrace.eses.entity.ExporterRegistration;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.Locality;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.ProcurementGrade;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.entity.Village;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.FileUtil;
import com.sourcetrace.eses.util.ICryptoUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class FarmerAction extends SwitchAction {

	private static final long serialVersionUID = 1L;
	public static final String SELECT_MULTI = "-1";
	@Getter
	@Setter
	private String id;
	@Getter
	@Setter
	private Farmer farmer;
	@Getter
	@Setter
	private String productId;
	@Getter
	@Setter
	private String cropHsCode;
	@Getter
	@Setter
	private String natiId;
	@Getter
	@Setter
	private String phn;
	@Getter
	@Setter
	private String redirectContent;
	@Autowired
	private IUtilService utilService;
	@Autowired
	private IFarmerService farmerService;
	@Autowired
	private ICryptoUtil cryptoUtil;
	@Autowired
	private IUniqueIDGenerator idGenerator;
	@Getter
	@Setter
	private String fphoto;
	@Getter
	@Setter
	private String refPhoto;
	@Getter
	@Setter
	private File files;
	/*
	 * @Getter
	 * 
	 * @Setter private File regCrt;
	 */
	@Getter
	@Setter
	private File fphoto1;
	@Getter
	@Setter
	private String nidPhoto;
	@Getter
	@Setter
	private File nidPhoto1;

	@Getter
	@Setter
	private String selProd;

	@Getter
	@Setter
	private String variety;

	@Getter
	@Setter
	private String selectedProduct;

	@Getter
	@Setter
	private String selectedVarity;

	@Getter
	@Setter
	private String selectedProductData;

	@Getter
	@Setter
	private String dateOfBirth;

	@Getter
	@Setter
	private String varietyId;

	@Getter
	@Setter
	private String[] columnNames;
	@Getter
	@Setter
	private String[] columnModels;

	public String create() throws Exception {
		if (farmer == null) {
			setCommand(CREATE);
			request.setAttribute(HEADING, getText("farmercreate.page"));
			if (request != null && request.getSession() != null) {
				request.getSession().setAttribute(ISecurityFilter.REPORT, "Farmer Registration");
			}
			farmer = new Farmer();
			/*
			 * if (getLoggedInDealer() > 0) { ExporterRegistration ex =
			 * utilService.findExportRegById(Long.valueOf(getLoggedInDealer()));
			 * farmer.setExporter(ex); }
			 */
			return INPUT;
		} else {
			farmer.setBranchId(getBranchId());
			farmer.setFirstName(farmer.getFirstName());
			farmer.setMobileNo(farmer.getMobileNo());
			farmer.setCropCategory(farmer.getCropCategory());
			farmer.setCropName(getProductId());
			farmer.setCropVariety(getVarietyId());
			if (farmer.getGender() == null || StringUtil.isEmpty(farmer.getGender())) {
				farmer.setGender("");
			}

			farmer.setFarmerId(idGenerator.getFarmerIdSeq());
			if (!StringUtil.isEmpty(selectedVillage) && selectedVillage != null) {
				Village village = utilService.findVillageById(Long.valueOf(selectedVillage));
				farmer.setVillage(village);
			}

			if (files != null) {
				DocumentUpload du = new DocumentUpload();
				System.out.println("fphoto==>" + fphoto);
				String[] tokens = fphoto.split("\\.(?=[^\\.]+$)");
				String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
				if (!name.equals("") && name != null) {
					String filetype = tokens[1];
					if (tokens != null && filetype.equals("jpg") || filetype.equals("JPG") || filetype.equals("PNG")
							|| filetype.equals("jpeg") || filetype.equals("JPEG") || filetype.equals("png")
							|| filetype.equals("pdf") || filetype.equals("PDF")) {

						System.out.println("At 210**********" + name);
						du.setName(name);
						du.setContent(FileUtil.getBinaryFileContent(getFiles()));
						du.setDocFileContentType(tokens[1]);
						du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec()) + "1");
						du.setType(DocumentUpload.docType.farmer.ordinal());
						du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
						utilService.save(du);
						farmer.setFarmerPhoto(du.getRefCode().toString());
					}
				}
			}
			/*
			 * if (regCrt != null) { DocumentUpload du = new DocumentUpload();
			 * String[] tokens = refPhoto.split("\\.(?=[^\\.]+$)"); String name
			 * = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim(); if
			 * (!name.equals("") && name != null) { String filetype = tokens[1];
			 * if (tokens != null && filetype.equals("jpg") ||
			 * filetype.equals("JPG") || filetype.equals("PNG") ||
			 * filetype.equals("jpeg") || filetype.equals("JPEG") ||
			 * filetype.equals("png") || filetype.equals("pdf") ||
			 * filetype.equals("PDF")) {
			 * 
			 * du.setName(name);
			 * du.setContent(FileUtil.getBinaryFileContent(getFiles()));
			 * du.setDocFileContentType(tokens[1]);
			 * du.setRefCode(String.valueOf(farmer.getId()));
			 * du.setType(DocumentUpload.docType.farmer.ordinal());
			 * du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
			 * utilService.save(du);
			 * farmer.setRegistrationCertificate(du.getId().toString()); } } }
			 */

			if (fphoto1 != null) {
				DocumentUpload du = new DocumentUpload();
				String[] tokens = nidPhoto.split("\\.(?=[^\\.]+$)");

				String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();

				if (!name.equals("") && name != null) {
					String filetype = tokens[1];
					if (tokens != null && filetype.equals("jpg") || filetype.equals("JPG") || filetype.equals("PNG")
							|| filetype.equals("jpeg") || filetype.equals("JPEG") || filetype.equals("png")
							|| filetype.equals("pdf") || filetype.equals("PDF")) {

						du.setName(name);
						du.setContent(FileUtil.getBinaryFileContent(getFphoto1()));
						du.setDocFileContentType(tokens[1]);
						du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec()));
						du.setType(DocumentUpload.docType.farmer.ordinal());
						du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
						utilService.save(du);
						farmer.setPhotoNid(du.getRefCode().toString());
					}
				}
			}

			farmer.setLatitude(farmer.getLatitude());
			farmer.setLongitude(farmer.getLongitude());
			farmer.setEmailId(farmer.getEmailId());
			farmer.setNoOfFamilyMember(farmer.getNoOfFamilyMember());
			farmer.setAdultAbove(farmer.getAdultAbove());
			farmer.setSchoolGoingChild(farmer.getSchoolGoingChild());
			farmer.setChildBelow(farmer.getChildBelow());
			farmer.setHedu(farmer.getHedu());
			farmer.setHouse(farmer.getHouse());
			farmer.setOwnership(farmer.getOwnership());
			farmer.setCreatedUser(getUsername());
			farmer.setCreatedDate(new Date());
			farmer.setStatus(Farmer.Status.ACTIVE.ordinal());
			farmer.setIsActive(1l);
			farmer.setRevisionNo(DateUtil.getRevisionNumber());
			String code = idGenerator.getCropHarvestReceiptNumberSeq();
			farmer.setFarmerCode("FA" + idGenerator.getFarmerIdSeq());
			farmer.setStatus(1);
			farmer.setStatusCode(0);
			farmer.setFarmerCat(farmer.getFarmerCat());
			farmer.setFCat(farmer.getFCat());

			/*
			 * farmer.setLatitude(getLatitude());
			 * farmer.setLongitude(getLongitude());
			 */

			farmer.setCompanyName(farmer.getCompanyName());
			farmer.setKraPin(farmer.getKraPin());
			farmer.setFarmerRegType(farmer.getFarmerRegType());
			farmer.setRegistrationCertificate(farmer.getRegistrationCertificate());
			if (!StringUtil.isEmpty(dateOfBirth)) {
				farmer.setDateOfBirth(DateUtil.convertStringToDate(dateOfBirth, getGeneralDateFormat()));
			} else {
				farmer.setDateOfBirth(null);
			}

			utilService.save(farmer);
		}
		return REDIRECT;
	}

	@Getter
	@Setter
	List<Object[]> ex;
	

	public String detail() throws Exception {
		String view = "";
		if (id != null && !id.equals("")) {
			farmer = utilService.findFarmerById(Long.valueOf(id));
			if (farmer == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			String hsCode = farmer.getCropVariety();
			String cropCat = utilService.findCropHierarchyNameById("procurement_product", farmer.getCropCategory());
			farmer.setCropCategory(cropCat);

			String cropName = utilService.findCropHierarchyNameById("procurement_variety", farmer.getCropName());
			farmer.setCropName(cropName);

			String varietyName = utilService.findCropHierarchyNameById("procurement_grade", farmer.getCropVariety());
			farmer.setCropVariety(varietyName);

			/*
			 * String expName =
			 * utilService.findExporterNameById("exporter_registration",
			 * farmer.getExporters()); farmer.setExporters(expName);
			 */

			// String hsCode1 =
			// utilService.findCropHierarchyHSCodeById("procurement_product",
			// hsCode);
			String hsCode1 = utilService.findCropHsCodeByProcurementProductId("procurement_grade", hsCode);
			setCropHsCode(hsCode1);

			DateFormat genDate = new SimpleDateFormat(getGeneralDateFormat());
			if (!ObjectUtil.isEmpty(farmer.getDateOfBirth()) && farmer.getDateOfBirth() != null) {
				dateOfBirth = genDate.format(farmer.getDateOfBirth());
			}
			ex = utilService.getAuditRecords("com.sourcetrace.eses.entity.Farmer", farmer.getId());
			
			
			setCurrentPage(getCurrentPage());
			command = UPDATE;
			view = DETAIL;
			request.setAttribute(HEADING, getText("farmerdetail"));
		} else {

			request.setAttribute(HEADING, getText(LIST));
			return REDIRECT;
		}
		return view;
	}

	public String update() throws Exception {
		if (id != null && !id.equals("") && farmer == null) {
			farmer = utilService.findFarmerById(Long.valueOf(id));
			setProductId(farmer.getCropName());
			setVarietyId(farmer.getCropVariety());
			// ProcurementGrade pg =
			// utilService.findProcurementGradeById(Long.valueOf(farmer.getCropName()));
			// setProductId(String.valueOf(pg.getProcurementVariety().getId()));
			// String hsCode1 =
			// utilService.findCropHierarchyHSCodeById("procurement_product",
			// farmer.getCropCategory());
			String hsCode1 = utilService.findCropHsCodeByProcurementProductId("procurement_grade",
					farmer.getCropName());
			setCropHsCode(hsCode1);
			if (selectedVillage != null && !selectedVillage.isEmpty() && selectedVillage != "") {
				Village l = utilService.findVillageById(Long.valueOf(selectedVillage));
				farmer.setVillage(l);
			}
			setNidPhoto(farmer.getPhotoNid());
			setSelectedVillage(farmer.getVillage() != null ? String.valueOf(farmer.getVillage().getId()) : "");
			setSelectedCity(farmer.getVillage() != null ? String.valueOf(farmer.getVillage().getCity().getId()) : "");
			setSelectedLocality(farmer.getVillage() != null
					? String.valueOf(farmer.getVillage().getCity().getLocality().getId()) : "");
			setSelectedState(farmer.getVillage() != null
					? String.valueOf(farmer.getVillage().getCity().getLocality().getState().getId()) : "");
			setSelectedCountry(farmer.getVillage() != null
					? farmer.getVillage().getCity().getLocality().getState().getCountry().getName() : "");

			if (farmer.getDateOfBirth() != null) {
				SimpleDateFormat sm = new SimpleDateFormat(getGeneralDateFormat());
				dateOfBirth = sm.format(farmer.getDateOfBirth());
			}

			command = UPDATE;
			request.setAttribute(HEADING, getText("farmerupdate"));
			farmer.setOwnership(farmer.getOwnership());

		} else {

			if (farmer != null) {
				Farmer temp = utilService.findFarmerById(Long.valueOf(farmer.getId()));
				if (temp == null) {
					addActionError(NO_RECORD);
					return REDIRECT;
				}
				temp.setBranchId(getBranchId());
				setCurrentPage(getCurrentPage());

				temp.setBranchId(getBranchId());
				temp.setFirstName(farmer.getFirstName());
				temp.setFarmerCode(farmer.getFarmerCode());

				temp.setGender(farmer.getGender());
				temp.setAge(farmer.getAge());
				temp.setNid(farmer.getNid());
				temp.setMobileNo(farmer.getMobileNo());
				/* temp.setExporters(farmer.getExporters()); */
				temp.setCropCategory(farmer.getCropCategory());
				temp.setCropName(getProductId());
				temp.setCropVariety(getVarietyId());
				// setSelectedVillage(farmer.getVillage() != null ?
				// String.valueOf(farmer.getVillage().getId()) : "");
				setSelectedCity(
						farmer.getVillage() != null ? String.valueOf(farmer.getVillage().getCity().getId()) : "");
				setSelectedLocality(farmer.getVillage() != null
						? String.valueOf(farmer.getVillage().getCity().getLocality().getId()) : "");
				setSelectedState(farmer.getVillage() != null
						? String.valueOf(farmer.getVillage().getCity().getLocality().getState().getId()) : "");
				setSelectedCountry(farmer.getVillage() != null
						? farmer.getVillage().getCity().getLocality().getState().getCountry().getName() : "");

				if (files != null) {
					DocumentUpload du = new DocumentUpload();
					System.out.println("fphoto==>" + fphoto);
					String[] tokens = fphoto.split("\\.(?=[^\\.]+$)");
					String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
					if (!name.equals("") && name != null) {
						String filetype = tokens[1];
						if (tokens != null && filetype.equals("jpg") || filetype.equals("JPG") || filetype.equals("PNG")
								|| filetype.equals("jpeg") || filetype.equals("JPEG") || filetype.equals("png")
								|| filetype.equals("pdf") || filetype.equals("PDF")) {

							System.out.println("At 210**********" + name);
							du.setName(name);
							du.setContent(FileUtil.getBinaryFileContent(getFiles()));
							du.setDocFileContentType(tokens[1]);
							du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec()) + "1");
							du.setType(DocumentUpload.docType.farmer.ordinal());
							du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
							utilService.save(du);
							temp.setFarmerPhoto(du.getRefCode().toString());
						}
					}
				}

				/*
				 * if (regCrt != null) { DocumentUpload du = new
				 * DocumentUpload(); String[] tokens =
				 * refPhoto.split("\\.(?=[^\\.]+$)"); String name =
				 * tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
				 * if (!name.equals("") && name != null) { String filetype =
				 * tokens[1]; if (tokens != null && filetype.equals("jpg") ||
				 * filetype.equals("JPG") || filetype.equals("PNG") ||
				 * filetype.equals("jpeg") || filetype.equals("JPEG") ||
				 * filetype.equals("png") || filetype.equals("pdf") ||
				 * filetype.equals("PDF")) { du.setName(name);
				 * du.setContent(FileUtil.getBinaryFileContent(getFiles()));
				 * du.setDocFileContentType(tokens[1]);
				 * du.setRefCode(String.valueOf(farmer.getId()));
				 * du.setType(DocumentUpload.docType.farmer.ordinal());
				 * du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
				 * utilService.save(du);
				 * temp.setRegistrationCertificate(du.getId().toString()); } } }
				 */

				if (fphoto1 != null) {
					DocumentUpload du = new DocumentUpload();
					String[] tokens = nidPhoto.split("\\.(?=[^\\.]+$)");

					String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();

					if (!name.equals("") && name != null) {
						String filetype = tokens[1];
						if (tokens != null && filetype.equals("jpg") || filetype.equals("JPG") || filetype.equals("PNG")
								|| filetype.equals("jpeg") || filetype.equals("JPEG") || filetype.equals("png")
								|| filetype.equals("pdf") || filetype.equals("PDF")) {

							du.setName(name);
							du.setContent(FileUtil.getBinaryFileContent(getFphoto1()));
							du.setDocFileContentType(tokens[1]);
							du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec()));
							du.setType(DocumentUpload.docType.farmer.ordinal());
							du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
							utilService.save(du);
							temp.setPhotoNid(du.getRefCode().toString());
						}
					}
				}

				if (!StringUtil.isEmpty(selectedVillage) && selectedVillage != null) {
					Village village = utilService.findVillageById(Long.valueOf(selectedVillage));
					temp.setVillage(village);
				}

				temp.setLatitude(farmer.getLatitude());
				temp.setLongitude(farmer.getLongitude());
				temp.setEmailId(farmer.getEmailId());
				temp.setNoOfFamilyMember(farmer.getNoOfFamilyMember());
				temp.setAdultAbove(farmer.getAdultAbove());
				temp.setSchoolGoingChild(farmer.getSchoolGoingChild());
				temp.setChildBelow(farmer.getChildBelow());
				temp.setHedu(farmer.getHedu());
				temp.setHouse(farmer.getHouse());
				temp.setOwnership(farmer.getOwnership());
				// temp.setCreatedUser(getUsername());
				// temp.setCreatedDate(new Date());
				temp.setStatus(Farmer.Status.ACTIVE.ordinal());
				temp.setIsActive(1l);
				temp.setStatusCode(0);
				temp.setRevisionNo(DateUtil.getRevisionNumber());
				temp.setAsset(farmer.getAsset());
				temp.setFarmerCat(farmer.getFarmerCat());
				temp.setFCat(farmer.getFCat());

				temp.setCompanyName(farmer.getCompanyName());
				temp.setKraPin(farmer.getKraPin());
				temp.setFarmerRegType(farmer.getFarmerRegType());
				temp.setRegistrationCertificate(farmer.getRegistrationCertificate());

				if (!StringUtil.isEmpty(dateOfBirth)) {
					temp.setDateOfBirth(DateUtil.convertStringToDate(dateOfBirth, getGeneralDateFormat()));
				} else {
					temp.setDateOfBirth(null);
				}

				utilService.update(temp);
			}
			request.setAttribute(HEADING, getText("farmerlist"));
			return REDIRECT;
		}
		return super.execute();
	}

	public String delete() throws Exception {

		String result = null;
		if (id != null && !id.equals("")) {
			farmer = utilService.findFarmerById(Long.valueOf(id));
			if (farmer == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(farmer)) {
				farmer.setStatus(0);
				farmer.setRevisionNo(DateUtil.getRevisionNumber());
				utilService.update(farmer);
				result = REDIRECT;
			}
		}
		return result;
	}

	public void populateValidate() {
		Map<String, String> errorCodes = new LinkedHashMap<String, String>();
		if (farmer != null) {

			/*
			 * if (farmer == null || StringUtil.isEmpty(farmer.getExporters()))
			 * { errorCodes.put("empty.exporter", "empty.exporter"); }
			 */

			if (farmer.getFCat() == null || StringUtil.isEmpty(farmer.getFCat()) || farmer.getFCat().equals("")) {
				errorCodes.put("empty.FarmerCat", "empty.FarmerCat");
			}
			if (farmer.getFarmerCat() == null || StringUtil.isEmpty(farmer.getFarmerCat())
					|| farmer.getFarmerCat().equals("")) {
				errorCodes.put("empty.farmOwnership", "empty.farmOwnership");
			}

			if (farmer.getFirstName() == null || StringUtil.isEmpty(farmer.getFirstName())
					|| farmer.getFirstName().equals("")) {

				errorCodes.put("empty.farmerName", "empty.farmerName");

			}
			if (farmer.getFarmerRegType().equalsIgnoreCase("0")) {
				if (farmer.getGender() == null || StringUtil.isEmpty(farmer.getGender())
						|| farmer.getGender().equals("")) {
					errorCodes.put("empty.gender", "empty.gender");
				}
			}

			if (farmer.getFarmerRegType().equalsIgnoreCase("0")) {
				if (dateOfBirth == null || StringUtil.isEmpty(dateOfBirth) || dateOfBirth.equals("")) {
					errorCodes.put("empty.dob", "empty.dob");
				}
			}

			if (farmer.getFarmerRegType().equalsIgnoreCase("0")
					&& (farmer.getAge() == null || StringUtil.isEmpty(farmer.getAge()) || farmer.getAge().equals(""))) {

				errorCodes.put("empty.age", "empty.age");
			} else {
				if (farmer.getAge() != null && !StringUtil.isEmpty(farmer.getAge())
						&& Integer.valueOf(farmer.getAge()) <= 17) {
					errorCodes.put("validate.age", "validate.age");
				}
			}

			/*
			 * if (farmer.getFarmerRegType().equalsIgnoreCase("0") &&
			 * (farmer.getNid() == null || StringUtil.isEmpty(farmer.getNid())
			 * || farmer.getNid().equals(""))) { errorCodes.put("pattern.nid",
			 * "pattern.nid"); } else
			 */

			if (farmer.getNid() != null && !StringUtil.isEmpty(farmer.getNid())) {
				try {
					if (farmer.getNid() != null && !StringUtil.isEmpty(farmer.getNid())) {
						Farmer fr = (Farmer) farmerService.findObjectById("from Farmer fc where fc.nid=?",
								new Object[] { farmer.getNid() });
						if (fr != null
								&& (farmer.getId() == null || fr != null && !farmer.getId().equals(fr.getId()))) {
							errorCodes.put("duplicate.nid", "duplicate.nid");

						} else {

						}
					}
				} catch (Exception e) {

				}
			}

			if (farmer.getMobileNo() == null || StringUtil.isEmpty(farmer.getMobileNo())
					|| farmer.getMobileNo().equals("")) {
				errorCodes.put("empty.phoneNo", "empty.phoneNo");
			} else {
				try {
					Farmer fr = (Farmer) farmerService.findObjectById("from Farmer fc where fc.mobileNo=?",
							new Object[] { farmer.getMobileNo() });
					if (fr != null && (farmer.getId() == null || !farmer.getId().equals(fr.getId()))) {

						errorCodes.put("unique.farmerPhone", "unique.farmerPhone");
						//

					} else {

					}
				} catch (Exception e) {

				}
			}

			if (farmer.getCropCategory() == null || StringUtil.isEmpty(farmer.getCropCategory())
					|| farmer.getCropCategory().equals("")) {

				errorCodes.put("empty.CropCategory", "empty.CropCategory");
			}

			if (farmer.getFarmerRegType() == null || StringUtil.isEmpty(farmer.getFarmerRegType())
					|| farmer.getFarmerRegType().equals("")) {

				errorCodes.put("empty.FarmerRegType", "empty.FarmerRegType");
			}
			if (farmer.getFarmerRegType().equalsIgnoreCase("1") || farmer.getFarmerRegType().equalsIgnoreCase("0")) {
				if (farmer.getFarmerRegType().equalsIgnoreCase("1")) {
					if (farmer.getCompanyName() == null || StringUtil.isEmpty(farmer.getCompanyName())
							|| farmer.getCompanyName().equals("")) {
						errorCodes.put("empty.CompanyName", "empty.CompanyName");
					}
				}
				/*
				 * if (farmer.getKraPin() == null ||
				 * StringUtil.isEmpty(farmer.getKraPin()) ||
				 * farmer.getKraPin().equals("")) {
				 * errorCodes.put("empty.KraPin", "empty.KraPin"); }
				 */

				if (!StringUtil.isEmpty(farmer.getKraPin())) {

					if (farmer.getKraPin().length() != 11) {
						errorCodes.put("krapinlength.valid", "krapinlength.valid");
					} else {
						char firstChar = farmer.getKraPin().charAt(0);
						String first = String.valueOf(firstChar);
						String last = farmer.getKraPin().substring(farmer.getKraPin().length() - 1);
						String num = removeFirstandLast(farmer.getKraPin());
						boolean resultfirst = first.matches("[a-zA-Z]+");
						boolean resultLast = last.matches("[a-zA-Z]+");

						/*
						 * if (!String.valueOf(first).equals("P") &&
						 * !String.valueOf(first).equals("A")) {
						 * errorCodes.put("krapinfirst.valid",
						 * "krapinfirst.valid"); } else
						 */
						if (farmer.getFarmerRegType() != null && farmer.getFarmerRegType().equalsIgnoreCase("1")
								&& !String.valueOf(first).equals("P")) {
							errorCodes.put("krapinfirstLetterForC.valid", "krapinfirstLetterForC.valid");
						} else if (farmer.getFarmerRegType() != null && farmer.getFarmerRegType().equalsIgnoreCase("0")
								&& !String.valueOf(first).equals("A")) {
							errorCodes.put("krapinfirstLetterForI.valid", "krapinfirstLetterForI.valid");
						} else if (resultLast == false) {
							errorCodes.put("krapinlast.valid", "krapinlast.valid");
						} else if (!StringUtil.isLong(num)) {
							errorCodes.put("krapinmiddle.valid", "krapinmiddle.valid");
						} else if (num.length() != 9) {
							errorCodes.put("krapinlength.valid", "krapinlength.valid");
						}
					}
				}
				if (farmer.getFarmerRegType().equalsIgnoreCase("1")) {
					if (farmer.getRegistrationCertificate() == null
							|| StringUtil.isEmpty(farmer.getRegistrationCertificate())
							|| farmer.getRegistrationCertificate().equals("")) {
						errorCodes.put("empty.RegistrationCertificate", "empty.RegistrationCertificate");
					}
				}
			}
			/*
			 * if (farmer.getFarmerRegType().equalsIgnoreCase("1")) { if
			 * (farmer.getCompanyName() == null ||
			 * StringUtil.isEmpty(farmer.getCompanyName()) ||
			 * farmer.getCompanyName().equals("")) {
			 * errorCodes.put("empty.CompanyName", "empty.CompanyName"); }
			 * 
			 * if (farmer.getKraPin() == null ||
			 * StringUtil.isEmpty(farmer.getKraPin()) ||
			 * farmer.getKraPin().equals("")) { errorCodes.put("empty.KraPin",
			 * "empty.KraPin"); }
			 * 
			 * if (!StringUtil.isEmpty(farmer.getKraPin())) {
			 * 
			 * if (farmer.getKraPin().length() != 11) {
			 * errorCodes.put("krapinlength.valid", "krapinlength.valid"); }
			 * else { char firstChar = farmer.getKraPin().charAt(0); String
			 * first = String.valueOf(firstChar); String last =
			 * farmer.getKraPin().substring(farmer.getKraPin().length() - 1);
			 * String num = removeFirstandLast(farmer.getKraPin()); boolean
			 * resultfirst = first.matches("[a-zA-Z]+"); boolean resultLast =
			 * last.matches("[a-zA-Z]+");
			 * 
			 * if (!String.valueOf(first).equals("P") &&
			 * !String.valueOf(first).equals("A")) {
			 * errorCodes.put("krapinfirst.valid", "krapinfirst.valid"); } else
			 * if (resultLast == false) { errorCodes.put("krapinlast.valid",
			 * "krapinlast.valid"); } else if (!StringUtil.isLong(num)) {
			 * errorCodes.put("krapinmiddle.valid", "krapinmiddle.valid"); }
			 * else if (num.length() != 9) {
			 * errorCodes.put("krapinlength.valid", "krapinlength.valid"); } } }
			 * if (farmer.getRegistrationCertificate() == null ||
			 * StringUtil.isEmpty(farmer.getRegistrationCertificate()) ||
			 * farmer.getRegistrationCertificate().equals("")) {
			 * errorCodes.put("empty.RegistrationCertificate",
			 * "empty.RegistrationCertificate"); } }
			 */
			if (getProductId() == null || StringUtil.isEmpty(getProductId()) || getProductId().equals("")) {

				errorCodes.put("empty.productId", "empty.productId");
			}

			if (getVarietyId() == null || StringUtil.isEmpty(getVarietyId()) || getVarietyId().equals("")) {

				errorCodes.put("empty.variety", "empty.variety");
			}

			if (getSelectedCountry() == null || StringUtil.isEmpty(getSelectedCountry())
					|| getSelectedCountry().equals("")) {

				errorCodes.put("empty.country", "empty.country");
			}

			if (getSelectedState() == null || StringUtil.isEmpty(getSelectedState()) || getSelectedState().equals("")) {

				errorCodes.put("empty.county", "empty.county");
			}

			if (getSelectedLocality() == null || StringUtil.isEmpty(getSelectedLocality())
					|| getSelectedLocality().equals("")) {

				errorCodes.put("empty.locality", "empty.locality");
			}

			if (getSelectedCity() == null || StringUtil.isEmpty(getSelectedCity()) || getSelectedCity().equals("")) {

				errorCodes.put("empty.ward", "empty.ward");
			}

			if (getSelectedVillage() == null || StringUtil.isEmpty(getSelectedVillage())
					|| getSelectedVillage().equals("")) {

				errorCodes.put("empty.ward1", "empty.ward1");
			}

			if (!StringUtil.isEmpty(farmer.getEmailId())) {
				if (!ValidationUtil.isPatternMaches(farmer.getEmailId(), ValidationUtil.EMAIL_PATTERN)) {
					errorCodes.put("pattern.email", "pattern.email");
				}

			}

			/*
			 * if (getNidPhoto() == null && StringUtil.isEmpty(getNidPhoto())) {
			 * errorCodes.put("empty.nidPhoto", "empty.nidPhoto"); }
			 */
			/**/

			/*
			 * if (command.equalsIgnoreCase("update")) { if
			 * (farmer.getPhotoNid() == null ||
			 * StringUtil.isEmpty(farmer.getPhotoNid()) ||
			 * farmer.getFarmerCat().equals("")) {
			 * 
			 * errorCodes.put("empty.nidPhoto", "empty.nidPhoto"); } } else { if
			 * (getFphoto1() == null && StringUtil.isEmpty(getFphoto1())) {
			 * errorCodes.put("empty.nidPhoto", "empty.nidPhoto"); } }
			 */

			/*
			 * if (farmer.getLatitude() == null ||
			 * StringUtil.isEmpty(farmer.getLatitude()) ||
			 * farmer.getLatitude().equals("")) {
			 * 
			 * errorCodes.put("empty.lat", "empty.lat"); }
			 * 
			 * if (farmer.getLongitude() == null ||
			 * StringUtil.isEmpty(farmer.getLongitude()) ||
			 * farmer.getLongitude().equals("")) {
			 * 
			 * errorCodes.put("empty.Flong", "empty.Flong"); }
			 */
		} else {
			errorCodes.put("empty.fields", getLocaleProperty("empty.fields"));
		}
		printErrorCodes(errorCodes);
	}

	public void populatevarity() {
		if (selProd != null) {
			variety = "";
			JSONArray varietyArr = new JSONArray();
			Arrays.asList(selProd.split(", ")).stream().forEach(u -> {
				List<ProcurementVariety> varietiesList = utilService
						.listProcurementVarietyByProcurementProductId(Long.valueOf(u));
				varietiesList.stream().filter(obj -> !ObjectUtil.isEmpty(obj)).forEach(obj -> {
					varietyArr.add(getJSONObject(obj.getId(), obj.getName()));
				});
			});
			sendAjaxResponse(varietyArr);

		}
	}

	public void populateHsCode() throws Exception {

		JSONObject jsonObj = new JSONObject();
		String msg = null;
		if (selectedProduct != null && !StringUtil.isEmpty(selectedProduct)) {
			String cropid = selectedProduct.replaceAll(",$", "");
			// String hsCode1 =
			// utilService.findCropHierarchyHSCodeById("procurement_product",
			// selectedProduct);
			String hsCode1 = utilService.findCropHsCodeByProcurementProductId("procurement_grade", cropid);
			setCropHsCode(hsCode1);
			jsonObj.put("hscode", hsCode1);
		}

		sendAjaxResponse(jsonObj);
	}

	public void populateGrade() {
		if (!StringUtil.isEmpty(procurementVariety)) {
			List<ProcurementGrade> gradeList = utilService
					.listProcurementGradeByProcurementVarietyIds(procurementVariety);
			JSONArray gradeArr = new JSONArray();
			gradeList.stream().filter(obj -> !ObjectUtil.isEmpty(obj)).forEach(obj -> {
				gradeArr.add(getJSONObject(obj.getId(), obj.getName()));
			});
			sendAjaxResponse(gradeArr);
		}

	}

	public Map<String, String> getFarmerCatList() {
		Map<String, String> farmerCatList = new LinkedHashMap<>();
		farmerCatList.put("0", getLocaleProperty("OWN"));
		farmerCatList.put("1", getLocaleProperty("CONTRACTED"));
		// farmerCatList.put("0", getLocaleProperty("Individual"));
		// farmerCatList.put("1", getLocaleProperty("Company"));
		return farmerCatList;
	}

	public Map<String, String> getFarmerCategoryList() {
		Map<String, String> farmerCategoryList = new LinkedHashMap<>();
		farmerCategoryList.put("0", getLocaleProperty("Organic"));
		farmerCategoryList.put("1", getLocaleProperty("Transition"));
		farmerCategoryList.put("2", getLocaleProperty("Conventional"));
		return farmerCategoryList;
	}

	public void populateState() throws Exception {

		JSONArray stateArr = new JSONArray();
		if (!selectedCountry.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedCountry))) {
			List<State> states = utilService.listStates(selectedCountry);
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
			List<Locality> listLocalities = utilService.findLocalityByStateId(Long.valueOf(selectedState));
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
			List<Municipality> listCity = utilService.findMuniciByDistrictId(Long.valueOf(selectedLocality));
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

	public Map<String, String> getCountries() {

		Map<String, String> countryMap = new LinkedHashMap<String, String>();
		List<Country> countryList = utilService.listCountries();
		for (Country obj : countryList) {
			countryMap.put(obj.getName(), obj.getCode() + "-" + obj.getName());
		}
		return countryMap;
	}// thisis loading country

	public void populateVillage() throws Exception {

		JSONArray villageArr = new JSONArray();
		if (!selectedCity.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedCity))) {
			List<Village> listVillages = utilService.findVillageByGramId(Long.valueOf(selectedCity));
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
}
