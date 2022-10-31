package com.sourcetrace.eses.action;

import com.sourcetrace.eses.entity.*;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Transient;
import java.io.File;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
@Component
@Scope("prototype")
public class ExporterRegistrationAction extends SwitchAction {

    protected static final String CREATE = "create";
    protected static final String DETAIL = "detail";
    protected static final String UPDATE = "update";
    protected static final String MAPPING = "mapping";
    protected static final String DELETE = "delete";
    protected static final String LIST = "list";
    protected static final String TITLE_PREFIX = "title.";
    protected static final String HEADING = "heading";
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ExporterRegistrationAction.class);
    /*
     * @Getter
     *
     * @Setter private String addr;
     */
    @Getter
    @Setter
    public SortedSet<InspectionDetails> inspDetails;
    @Getter
    @Setter
    private ExporterRegistration expRegis;
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String redirectContent;
    @Getter
    @Setter
    private String layoutType;
    @Getter
    @Setter
    private String proofFile1;
    @Getter
    @Setter
    private File file3;
    @Getter
    @Setter
    private String companyTin1;
    @Getter
    @Setter
    private File file2;
    @Autowired
    private IUtilService utilService;
    @Autowired
    private IUniqueIDGenerator idGenerator;
    @Autowired
    private ICryptoUtil cryptoUtil;
    @Getter
    @Setter
    private String selectedWarehouse;
    @Getter
    @Setter
    private String expireDateStr;
    @Getter
    @Setter
    private DocumentUpload docUpload;
    @Getter
    @Setter
    private String cropCategory;
    @Getter
    @Setter
    private String cropVariety;
    @Getter
    @Setter
    private String cropName;
    @Getter
    @Setter
    private String conformValue;
    @Getter
    @Setter
    @Transient
    private String otp;
	@Getter
	@Setter
	private String cropHsCode;

    public ICryptoUtil getCryptoUtil() {
        return cryptoUtil;
    }

    public void setCryptoUtil(ICryptoUtil cryptoUtil) {
        this.cryptoUtil = cryptoUtil;
    }

    /**
     * Creates the.
     *
     * @return the string
     */
    public String create() {
        String defBranchId = utilService.findPrefernceByName(ESESystem.DEF_BRANCH);
        if (expRegis == null) {
            command = "create";

            request.setAttribute(HEADING, getText("exportRegistercreate"));

            if (request != null && request.getSession() != null) {
                request.getSession().setAttribute(ISecurityFilter.REPORT, "Exporter Registration");
            }
            return INPUT;
        } else {
            expRegis.setCreatedDate(new Date());
            expRegis.setBranchId(getBranchId() != null ? getBranchId() : defBranchId);

            expRegis.setName(expRegis.getName());

            // associationMemb
            expRegis.setCmpyOrientation(
                    expRegis.getCmpyOrientation() != null && !StringUtil.isEmpty(expRegis.getCmpyOrientation())
                            ? expRegis.getCmpyOrientation() : null);
            // krapin
            expRegis.setRegNumber(expRegis.getRegNumber());
            // crop category
            expRegis.setUgandaExport(
                    expRegis.getUgandaExport() != null && !StringUtil.isEmpty(expRegis.getUgandaExport())
                            ? expRegis.getUgandaExport() : null);
            // license
            expRegis.setRefLetterNo(expRegis.getRefLetterNo());
            // fcropName
            expRegis.setFarmerHaveFarms(
                    expRegis.getFarmerHaveFarms() != null && !StringUtil.isEmpty(expRegis.getFarmerHaveFarms())
                            ? expRegis.getFarmerHaveFarms() : null);
            // cropvariety
            expRegis.setScattered(expRegis.getScattered() != null && !StringUtil.isEmpty(expRegis.getScattered())
                    ? expRegis.getScattered() : null);

            if (!StringUtil.isEmpty(selectedVillage) && selectedVillage != null) {
                Village lt = utilService.findVillageById(Long.valueOf(selectedVillage));
                expRegis.setVillage(lt);
            }
            expRegis.setPackGpsLoc(expRegis.getPackGpsLoc());
            // legal status
            expRegis.setPackhouse(expRegis.getPackhouse() != null && !StringUtil.isEmpty(expRegis.getPackhouse())
                    ? expRegis.getPackhouse() : null);
            // company email
            expRegis.setTin(expRegis.getTin());
            // national id
            expRegis.setRexNo(expRegis.getRexNo());
            // applicantName
            expRegis.setFarmToPackhouse(expRegis.getFarmToPackhouse());
            // gender
            expRegis.setPackToExitPoint(expRegis.getPackToExitPoint());
            // phone number
            expRegis.setMobileNo(expRegis.getMobileNo());
            // applicant email
            expRegis.setEmail(expRegis.getEmail());
            // postal address
            expRegis.setExpTinNumber(expRegis.getExpTinNumber());
            expRegis.setIsActive(0l);
            expRegis.setStatus(1);
            expRegis.setLatitude(getLatitude());
            expRegis.setLongitude(getLongitude());
            utilService.save(expRegis);
            ESESystem es = utilService.findPrefernceById("1");
            utilService.processExporterLic(expRegis,es.getPreferences());
           
            String pw = StringUtil.generateStrPassword(8);

            if (expRegis.getTin() != null) {
                String msg = getLocaleProperty("mail_exproterRegister");
                msg = msg.replace("[pw]", pw);
                sendExpireMail(expRegis.getTin().trim(), expRegis.getName(), msg, "Exporter registered");
                StringBuilder sb = new StringBuilder();
                sb.append(getLocaleProperty("hai") + " " + expRegis.getName() + "\n\n\n");
                sb.append(msg);
                String message = sb.toString();

                smsService.sendSMS(smsType, expRegis.getMobileNo(), msg);
            }

            Role r1 = utilService.findRoleByType(Role.Type.EXPORTER_ADMIN.ordinal());

            User u = new User();

            u.setUsername(expRegis.getTin());
            u.setStatus(1);
            String userPass = cryptoUtil.encrypt(StringUtil.getMulipleOfEight(u.getUsername() + pw));
            u.setPassword(userPass);
            u.setPersInfo(new PersonalInfo());
            u.setContInfo(new ContactInfo());
            u.setLoginStatus(0);
            u.setAgroChDealer(expRegis.getId());
            u.setRole(r1);
            u.getContInfo().setAddress1(expRegis.getAddr());
            u.getContInfo().setEmail(expRegis.getTin());
            u.getContInfo().setMobileNumbere(expRegis.getMobileNo());
            u.getPersInfo().setGender(expRegis.getPackToExitPoint());
            u.getPersInfo().setFirstName(expRegis.getName());
            u.setEnabled(true);
            u.setBranchId(getBranchId());
            u.setLanguage("en");
            u.setStatus(1);
            u.setCreatedDate(new Date());
            u.setDataId(0l);
            u.setUserType(r1.getType());
            u.setEnabled(true);

            utilService.addUser(u);
            setConformValue("success");
            return "successPopup";
            //return REDIRECT;
        }

    }

    /**
     * Delete.
     *
     * @return the string
     */

    public String delete() {
        String result = null;
        if (this.getId() != null && !(this.getId().equals(EMPTY))) {
            expRegis = utilService.findExportRegById(Long.valueOf(id));
            if (expRegis == null) {
                addActionError(NO_RECORD);
                return null;
            } else if (!ObjectUtil.isEmpty(expRegis)) {
                expRegis.setStatus(2);
                User user = utilService.findUserByUserName(expRegis.getTin());
                if (user != null) {
                    user.setDataId(3L);
                    user.setEnabled(false);
                    utilService.update(user);
                }
                utilService.update(expRegis);
                result = REDIRECT;
            }
        }

        request.setAttribute(HEADING, getText("exporRegislist"));
        return result;

    }

    /**
     * Detail.
     *
     * @return the string
     */
    public String detail() {
        String view = "";
        if (id != null && !id.equals("")) {
            expRegis = utilService.findExportRegById(Long.valueOf(id));
            setApprovalStatus(expRegis.getStatus());

            setApprovalDate(expRegis.getApprovalDate());
            setExpireDate(expRegis.getExpireDate());

            if (expRegis.getInspId() != null && expRegis.getInspId() > 0) {
                User us = utilService.findUser(expRegis.getInspId());
                setInspName(us.getPersInfo().getName());
            }
            expRegis.setCmpyOrientation(getAssociationMemberList().get(expRegis.getCmpyOrientation()));
            expRegis.setPackhouse(getLegalStatusList().get(expRegis.getPackhouse()));

            setExpireDateStr(expRegis.getExpireDate() != null && !ObjectUtil.isEmpty(expRegis.getExpireDate())
                    ? DateUtil.convertDateToString(expRegis.getExpireDate(), getGeneralDateFormat()) : "");

            expRegis.setPackGpsLoc(expRegis.getPackGpsLoc());

            String cropCat = utilService.findCropHierarchyNameById("procurement_product", expRegis.getUgandaExport());
            expRegis.setUgandaExport(cropCat);

            String cropName = utilService.findCropHierarchyNameById("procurement_variety",
                    expRegis.getFarmerHaveFarms());
            expRegis.setFarmerHaveFarms(cropName);

            String cropVariety = utilService.findCropHierarchyNameById("procurement_grade", expRegis.getScattered());
            expRegis.setScattered(cropVariety);

            if (expRegis.getPackToExitPoint() != null && !StringUtil.isEmpty(expRegis.getPackToExitPoint())) {
                expRegis.setPackToExitPoint(getGenderStatusMap().get(expRegis.getPackToExitPoint()));
            }

            setCurrentPage(getCurrentPage());
            command = UPDATE;
            view = DETAIL;
            request.setAttribute(HEADING, getText("exportRegisterdetail"));
        } else {
            request.setAttribute(HEADING, getText("exportRegisterdetail"));
            return REDIRECT;
        }
        return view;
    }

    /**
     * Update.
     *
     * @return the string
     * @throws Exception the exception
     */
    public String update() throws Exception {

        if (id != null && !id.equals("") && expRegis == null) {

            expRegis = utilService.findExportRegById(Long.valueOf(id));
            InspectionDetails id = new InspectionDetails();
            Role r1 = utilService.findRoleByType(Role.Type.EXPORTER_USER.ordinal());
            User u = utilService.findUserByDealerAndRole(expRegis.getId(), r1.getId());
            if (u == null) {
                u = new User();
            }
            setInspType(String.valueOf(Role.Type.PHYTO_INSPECTOR.ordinal()));
            setApprovalStatus(expRegis.getStatus());

            if (u != null) {
                expRegis.setRegNo(u.getUsername());
                String tempPass = u.getPassword();

                if (tempPass != null && !StringUtil.isEmpty(tempPass)) {
                    expRegis.setPassword(cryptoUtil.decrypt(tempPass));
                    expRegis.setConfrimPassword(cryptoUtil.decrypt(tempPass));
                }
            }
            if (selectedVillage != null && !selectedVillage.isEmpty() && selectedVillage != "") {
                Village l = utilService.findVillageById(Long.valueOf(selectedVillage));
                expRegis.setVillage(l);
            }

            setSelectedVillage(expRegis.getVillage() != null ? String.valueOf(expRegis.getVillage().getId()) : "");
            setSelectedCity(
                    expRegis.getVillage() != null ? String.valueOf(expRegis.getVillage().getCity().getId()) : "");
            setSelectedLocality(expRegis.getVillage() != null
                    ? String.valueOf(expRegis.getVillage().getCity().getLocality().getId()) : "");
            setSelectedState(expRegis.getVillage() != null
                    ? String.valueOf(expRegis.getVillage().getCity().getLocality().getState().getId()) : "");
            setSelectedCountry(expRegis.getVillage() != null
                    ? expRegis.getVillage().getCity().getLocality().getState().getCountry().getName() : "");
            setCurrentPage(getCurrentPage());
            command = UPDATE;
            request.setAttribute(HEADING, getText("exportRegisterupdate"));
        } else {

            ExporterRegistration tempexpReg = utilService.findExportRegById(Long.valueOf(id));
            setCurrentPage(getCurrentPage());
            tempexpReg.setBranchId(getBranchId());

            tempexpReg.setName(expRegis.getName());

            String[] tokens = proofFile1.split("\\.(?=[^\\.]+$)");
            String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
            if (!name.equals("") && name != null) {
                DocumentUpload du = new DocumentUpload();
                du.setName(name);
                du.setContent(FileUtil.getBinaryFileContent(getFile3()));
                du.setDocFileContentType(tokens[1]);
                du.setRefCode(String.valueOf(expRegis.getId()));
                // du.setType(DocumentUpload.docType.PermitApplication.ordinal());
                du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
                utilService.save(du);
                tempexpReg.setRegProof(String.valueOf(du.getId()));
            }

            tempexpReg.setRegNo(expRegis.getRegNo());

            tempexpReg.setCmpyOrientation(
                    expRegis.getCmpyOrientation() != null && !StringUtil.isEmpty(expRegis.getCmpyOrientation())
                            ? expRegis.getCmpyOrientation() : null);
            tempexpReg.setRegNumber(expRegis.getRegNumber());

            tempexpReg.setUgandaExport(
                    expRegis.getUgandaExport() != null && !StringUtil.isEmpty(expRegis.getUgandaExport())
                            ? expRegis.getUgandaExport() : null);

            tempexpReg.setRefLetterNo(expRegis.getRefLetterNo());
            tempexpReg.setFarmerHaveFarms(
                    expRegis.getFarmerHaveFarms() != null && !StringUtil.isEmpty(expRegis.getFarmerHaveFarms())
                            ? expRegis.getFarmerHaveFarms() : null);
            tempexpReg.setScattered(expRegis.getScattered() != null && !StringUtil.isEmpty(expRegis.getScattered())
                    ? expRegis.getScattered() : null);

            if (expRegis.getScattered() != null && !StringUtil.isEmpty(expRegis.getScattered())) {
                if (getCatalgueNameByCode(expRegis.getScattered()).equals("Others")) {
                    tempexpReg.setOtherScattered(expRegis.getOtherScattered());
                }
            }

            if (!StringUtil.isEmpty(selectedVillage) && selectedVillage != null) {
                Village lt = utilService.findVillageById(Long.valueOf(selectedVillage));
                tempexpReg.setVillage(lt);
            }
            tempexpReg.setPackGpsLoc(expRegis.getPackGpsLoc());
            tempexpReg.setPackhouse(expRegis.getPackhouse() != null && !StringUtil.isEmpty(expRegis.getPackhouse())
                    ? expRegis.getPackhouse() : null);
            tempexpReg.setTin(expRegis.getTin());
            tempexpReg.setExpTinNumber(expRegis.getExpTinNumber());

            tempexpReg.setRexNo(expRegis.getRexNo());
            tempexpReg.setFarmToPackhouse(expRegis.getFarmToPackhouse());
            tempexpReg.setPackToExitPoint(expRegis.getPackToExitPoint());

            tempexpReg.setMobileNo(expRegis.getMobileNo());
            tempexpReg.setEmail(expRegis.getEmail());

            
             tempexpReg.setStatus( StringUtil.isEmpty(expRegis.getStatus()) ?  0 : expRegis.getStatus());
             

            utilService.update(tempexpReg);
            ESESystem es = utilService.findPrefernceById("1");
            utilService.processExporterLic(tempexpReg,es.getPreferences());
            request.setAttribute(HEADING, getText("exportRegisterlist"));
            return REDIRECT;

        }

        return super.execute();
    }

    public void populateValidate() {
        Map<String, String> errorCodes = new LinkedHashMap<String, String>();

        if (!StringUtil.isEmpty(expRegis.getName())) {
            if (!ValidationUtil.isPatternMaches(expRegis.getName(), ValidationUtil.ALPHANUMERIC_PATTERN)) {
                errorCodes.put("pattern.companyName", "pattern.companyName");
            }
            ExporterRegistration acd2 = utilService.findExproterByCompanyName(expRegis.getName());
            if (acd2 != null && !acd2.getId().equals(expRegis.getId())) {
                errorCodes.put("error.company", "error.company");
            }
        } else {
            errorCodes.put("empty.incorpName", "empty.incorpName");
        }

        if (StringUtil.isEmpty(expRegis.getPackhouse()) || expRegis.getPackhouse().equals(null)) {
            errorCodes.put("empty.packhouseLS", "empty.packhouseLS");
        }
        
        if (expRegis.getRegNumber() == null || StringUtil.isEmpty(expRegis.getRegNumber())
                || expRegis.getRegNumber().equals("")) {

            errorCodes.put("pattern.krapin", "pattern.krapin");
        } else {
        	if (!StringUtil.isEmpty(expRegis.getPackhouse()) || !expRegis.getPackhouse().equals(null)) {
        		if(expRegis.getRegNumber().length()!=11) {
    				errorCodes.put("krapinlength.valid", "krapinlength.valid");
    			}else{
        		char firstChar = expRegis.getRegNumber().charAt(0);
        		String first = String.valueOf(firstChar);
            	String last = expRegis.getRegNumber().substring(expRegis.getRegNumber().length() - 1);
            	String num = removeFirstandLast(expRegis.getRegNumber());
            	boolean resultfirst = first.matches("[a-zA-Z]+");
            	boolean resultLast = last.matches("[a-zA-Z]+");
        		
        			if(!String.valueOf(first).equals("P") && !String.valueOf(first).equals("A")){
       				 	errorCodes.put("krapinfirst.valid", "krapinfirst.valid");
        			}else if(resultLast == false){
        				errorCodes.put("krapinlast.valid", "krapinlast.valid");
        			}else if(!StringUtil.isLong(num)) { 
        				errorCodes.put("krapinmiddleforexp.valid", "krapinmiddleforexp.valid");
        			}else if(num.length()!=9) {
        				errorCodes.put("krapinlength.valid", "krapinlength.valid");
        			}
    			}
       
            try {
                ExporterRegistration er = (ExporterRegistration) farmerService.findObjectById(
                        " from ExporterRegistration fc where  fc.isActive <>2 and fc.status <>2 and fc.regNumber=? ",
                        new Object[]{String.valueOf(expRegis.getRegNumber())});

                if (er != null) {
                    if (!er.getId().equals(expRegis.getId())) {

                        errorCodes.put("duplicate.krapin", "duplicate.krapin");
                        //
                    } else {

                    }
                } else {

                }
            } catch (Exception ignored) {

            }
        	}else{
        		errorCodes.put("pattern.krapins", "pattern.krapins");
        		}
        	}
        if (StringUtil.isEmpty(expRegis.getCmpyOrientation()) || expRegis.getCmpyOrientation().equals(null)) {
            errorCodes.put("empty.cmpOreien", "empty.cmpOreien");
        }
        if (StringUtil.isEmpty(expRegis.getPackGpsLoc()) || expRegis.getPackGpsLoc().equals(null)) {
            errorCodes.put("empty.cropHscode", "empty.cropHscode");
        }
       
        if (StringUtil.isEmpty(expRegis.getExpTinNumber()) || expRegis.getExpTinNumber().equals(null)) {
            errorCodes.put("empty.expTinNumber", "empty.expTinNumber");
        }
        if (StringUtil.isEmpty(getSelectedCountry()) || getSelectedCountry().equals(null)) {
            errorCodes.put("empty.selectedCountry", "empty.selectedCountry");
        }
        if (!StringUtil.isEmpty(expRegis.getTin())) {

            if (!ValidationUtil.isPatternMaches(expRegis.getTin(), ValidationUtil.EMAIL_PATTERN)) {
                errorCodes.put("pattern.cvemail", "pattern.cvemail");
            }

            ExporterRegistration er = (ExporterRegistration) farmerService.findObjectById(
                    " from ExporterRegistration fc where  fc.isActive <>2 and fc.status <>2 and fc.tin=? ",
                    new Object[]{String.valueOf(expRegis.getTin())});
            if (er != null) {
                if (!er.getId().equals(expRegis.getId())) {

                    errorCodes.put("duplicate.cvemail", "duplicate.cvemail");
                    //
                } else {

                }
            } else {

            }
        } else {
            errorCodes.put("empty.tin", "empty.tin");

        }

        if (!StringUtil.isEmpty(expRegis.getEmail())) {
            if (!ValidationUtil.isPatternMaches(expRegis.getEmail(), ValidationUtil.EMAIL_PATTERN)) {
                errorCodes.put("pattern.email", "pattern.email");
            }
            ExporterRegistration acd2 = utilService.findExproterByEmail(expRegis.getEmail());
            if (acd2 != null && !acd2.getId().equals(expRegis.getId())) {
                errorCodes.put("error.emailExceed", "error.emailExceed");
            }
        }

        // else{ errorCodes.put("empty.email", "empty.email");}

        if (StringUtil.isEmpty(expRegis.getUgandaExport()) || expRegis.getUgandaExport().equals(null)) {
            errorCodes.put("empty.ugandaExp", "empty.ugandaExp");
        }
        
        if (StringUtil.isEmpty(getOtp())) {
			errorCodes.put("empty.otp", "empty.otp");
		} else {
			List<SMSHistory> sm = (List<SMSHistory>) farmerService.listObjectById(
					"from SMSHistory   s where s.smsRoute=? order by s.id desc", new Object[] { sessionId });
			if (sm != null && !sm.isEmpty()) {
				SMSHistory ssm = sm.get(0);

				if (!sm.get(0).getCreationInfo().equals(getOtp())) {
					errorCodes.put("invalid.otp", "invalid.otp");
				} else {
					long diff =  new Date().getTime()-ssm.getCreateDt().getTime();
					long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
					if (minutes > 5) {
						errorCodes.put("expired.otp", "expired.otp");
					}
				}

			} else {
				errorCodes.put("invalid.otp", "invalid.otp");
			}
		}

        if (StringUtil.isEmpty(expRegis.getRexNo()) || expRegis.getRexNo().equals(null)) {
            // errorCodes.put("empty.farmerNid", "empty.farmerNid");
        } else {
            try {
                ExporterRegistration er = (ExporterRegistration) farmerService.findObjectById(
                        " from ExporterRegistration fc where   fc.isActive <>2 and fc.status <>2 and fc.rexNo=? ",
                        new Object[]{String.valueOf(expRegis.getRexNo())});
                if (er != null) {
                    if (!er.getId().equals(expRegis.getId())) {

                        errorCodes.put("duplicate.nidNo", "duplicate.nidNo");
                        //
                    } else {

                    }
                } else {

                }
            } catch (Exception ignored) {

            }

        }

        if (StringUtil.isEmpty(expRegis.getFarmerHaveFarms()) || expRegis.getFarmerHaveFarms().equals(null)) {
            errorCodes.put("empty.farmerFarms", "empty.farmerFarms");
        }


        if (StringUtil.isEmpty(expRegis.getScattered()) || expRegis.getScattered().equals(null)) {
            errorCodes.put("empty.scatter", "empty.scatter");
        }

        if (selectedState == null || StringUtil.isEmpty(selectedState)) {

            errorCodes.put("empty.state", "empty.state");
        }

        if (selectedLocality == null || StringUtil.isEmpty(selectedLocality)) {

            errorCodes.put("empty.district", "empty.district");
        }

        if (selectedCity == null || StringUtil.isEmpty(selectedCity)) {

            errorCodes.put("empty.city", "empty.city");
        }

        if (selectedVillage == null || StringUtil.isEmpty(selectedVillage)) {

            errorCodes.put("empty.village", "empty.village");
        }

        if (StringUtil.isEmpty(expRegis.getPackToExitPoint()) || expRegis.getPackToExitPoint().equals(null)) {
            errorCodes.put("empty.packToExitPoint", "empty.packToExitPoint");
        }

        if (!StringUtil.isEmpty(expRegis.getMobileNo())) {
            if (!ValidationUtil.isPatternMaches(expRegis.getMobileNo(), ValidationUtil.NUMBER_PATTERN)) {
                errorCodes.put("pattern.phn", "pattern.phn");
            } else {
                try {
                    ExporterRegistration er = (ExporterRegistration) farmerService.findObjectById(
                            " from ExporterRegistration fc where   fc.isActive <>2 and fc.status <>2 and fc.mobileNo=? ",
                            new Object[]{String.valueOf(expRegis.getMobileNo())});
                    if (er != null) {
                        if (!er.getId().equals(expRegis.getId())) {

                            errorCodes.put("duplicate.phno", "duplicate.phno");
                            //
                        } else {

                        }
                    } else {

                    }
                } catch (Exception ignored) {

                }

            }

        } else {
            errorCodes.put("pattern.phn", "pattern.phn");
        }


        printErrorCodes(errorCodes);
    }
    
   

    public String downloadImage1() {

        try {

            if (!StringUtil.isEmpty(id) && StringUtil.isLong(id)) {

                docUpload = utilService.findDocumentUploadById(Long.valueOf(id));
                if (docUpload != null) {
                    response.setContentType("application/octet-stream");
                    if (docUpload.getFileType() == 1) {
                        response.setContentType("application/pdf");
                    } else if (docUpload.getFileType() == 2) {
                        response.setContentType("application/msword");
                    } else if (docUpload.getFileType() == 3) {
                        response.setContentType("application/vnd.ms-excel");
                    }
                    String documentName;
                    documentName = docUpload.getName() != null ? docUpload.getName() : "";
                    /*
                     * response.setHeader("Content-Disposition",
                     * "attachment;filename=" + docUpload.getName() + "." +
                     * docUpload.getContent()); response.setHeader("fileName",
                     * docUpload.getFileName() + "." +
                     * docUpload.getContentType());
                     */
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Disposition",
                            "attachment;filename=" + documentName + "." + docUpload.getDocFileContentType());
                    OutputStream out = response.getOutputStream();
                    out.write(docUpload.getContent());
                    out.flush();
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public Map<String, String> getAvailableWarehouse() {
        Map<String, String> warehouseList = new LinkedHashMap<>();
        List<Packhouse> listVariety = utilService.listAllWarehouses();
        if (!ObjectUtil.isEmpty(listVariety)) {
            for (Packhouse pv : listVariety) {
                warehouseList.put(String.valueOf(pv.getId()), pv.getName());
            }
        }
        return warehouseList;
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


    public void populateState() {

        JSONArray stateArr = new JSONArray();
        if (!selectedCountry.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedCountry))) {
            List<State> states = utilService.listStates(selectedCountry);
            if (!ObjectUtil.isEmpty(states)) {
                for (State state : states) {

                    String name = getLanguagePref(getLoggedInUserLanguage(), state.getCode().trim());
                    if (!StringUtil.isEmpty(name) && name != null) {
                        stateArr.add(getJSONObject(String.valueOf(state.getId()), state.getCode() + "-"
                                + getLanguagePref(getLoggedInUserLanguage(), state.getCode())));
                    } else {
                        stateArr.add(
                                getJSONObject(String.valueOf(state.getId()), state.getCode() + "-" + state.getName()));
                    }
                }
            }
        }

        sendAjaxResponse(stateArr);
    }

    public void populateLocality() {
        JSONArray localtiesArr = new JSONArray();
        if (!selectedState.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedState))) {
            List<Locality> listLocalities = utilService.findLocalityByStateId(Long.parseLong(selectedState));
            if (!ObjectUtil.isEmpty(listLocalities)) {
                for (Locality locality : listLocalities) {

                    String name = getLanguagePref(getLoggedInUserLanguage(), locality.getCode().trim());
                    if (!StringUtil.isEmpty(name) && name != null) {
                        localtiesArr.add(getJSONObject(String.valueOf(locality.getId()),
                                locality.getCode().trim() + "-" + getLanguagePref(getLoggedInUserLanguage(),
                                        locality.getCode().trim())));
                    } else {
                        localtiesArr.add(getJSONObject(String.valueOf(locality.getId()),
                                locality.getCode() + "-" + locality.getName()));
                    }
                }
            }
        }

        sendAjaxResponse(localtiesArr);

    }

    public void populateMunicipality() {
        JSONArray citiesArr = new JSONArray();
        if (!selectedLocality.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedLocality))) {
            List<Municipality> listCity = utilService.findMuniciByDistrictId(Long.parseLong(selectedLocality));
            if (!ObjectUtil.isEmpty(listCity)) {
                for (Municipality city : listCity) {

                    String name = getLanguagePref(getLoggedInUserLanguage(), city.getCode().trim());
                    if (!StringUtil.isEmpty(name) && name != null) {
                        citiesArr.add(getJSONObject(String.valueOf(city.getId()), city.getCode().trim() + "-"
                                + getLanguagePref(getLoggedInUserLanguage(), city.getCode().trim())));
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

    public void populateVillage() {

        JSONArray villageArr = new JSONArray();
        if (!selectedCity.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedCity))) {
            List<Village> listVillages = utilService.findVillageByGramId(Long.parseLong(selectedCity));
            if (!ObjectUtil.isEmpty(listVillages)) {
                for (Village village : listVillages) {

                    String name = getLanguagePref(getLoggedInUserLanguage(), village.getCode().trim());
                    if (!StringUtil.isEmpty(name) && name != null) {
                        villageArr.add(getJSONObject(String.valueOf(village.getId()),
                                village.getCode().trim() + "-" + getLanguagePref(getLoggedInUserLanguage(),
                                        village.getCode().trim())));
                    } else {
                        villageArr.add(getJSONObject(String.valueOf(village.getId()),
                                village.getCode() + "-" + village.getName()));
                    }
                }
            }
        }

        sendAjaxResponse(villageArr);

    }

    @SuppressWarnings("unchecked")
    public void populateVillageSave() {
        Village village = new Village();
        Village vill = utilService.findVillageAndCityByVillName(getSelectedVillage(), Long.valueOf(selectedCity));
        if (!ObjectUtil.isEmpty(vill)) {
            getJsonObject().put("msg", "0");
            getJsonObject().put("title", getText("unique.ProcurementProductName"));
        } else {
            village.setName(getSelectedVillage());
            village.setCity(utilService.findMunicipalityById(Long.valueOf(selectedCity)));

            village.setBranchId(getBranchId());
            village.setCode(idGenerator.getVillageIdSeq());
            utilService.addVillage(village);

            getJsonObject().put("msg", getText("msg.added"));
            getJsonObject().put("title", getText("title.success"));
        }

        sendAjaxResponse(getJsonObject());
    }
    
	public void populateHsCode() throws Exception {

		JSONObject jsonObj = new JSONObject();
		String msg = null;
		if (selectedProduct != null && !StringUtil.isEmpty(selectedProduct)) {
			String cropid= selectedProduct.replaceAll(",$", "");
			//String hsCode1 = utilService.findCropHierarchyHSCodeById("procurement_product", selectedProduct);
			String hsCode1 = utilService.findCropHsCodeByProcurementProductId("procurement_variety", cropid);
			setCropHsCode(hsCode1);
			jsonObj.put("hscode", hsCode1);
		}

		sendAjaxResponse(jsonObj);
	}
}
