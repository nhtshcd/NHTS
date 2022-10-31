/**
 * UserAction.java
 * Copyright (c) 2012-2013, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.dao.UtilDAO;
import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.ContactInfo;
import com.sourcetrace.eses.entity.DocumentUpload;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.ExporterRegistration;
import com.sourcetrace.eses.entity.Language;
import com.sourcetrace.eses.entity.PasswordHistory;
import com.sourcetrace.eses.entity.PersonalInfo;
import com.sourcetrace.eses.entity.Role;
import com.sourcetrace.eses.entity.User;
import com.sourcetrace.eses.entity.UserActiveAndInActiveHostory;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.Base64Util;
import com.sourcetrace.eses.util.FileUtil;
import com.sourcetrace.eses.util.ICryptoUtil;
import com.sourcetrace.eses.util.JsonUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class UserAction.
 * 
 * @author $Author: boopalan $
 * @version $Rev: 5050 $
 */
public class UserAction extends SwitchValidatorAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UserAction.class);

	@Autowired
	private IUtilService utilService;
	private User user;
	private PersonalInfo persInfo;
	private ESESystem preferences;
	private String id;
	private String dateOfBirth;

	DateFormat df = new SimpleDateFormat(getESEDateFormat());
	Map<String, String> languageMap = new LinkedHashMap<String, String>();

	List<Role> roles;

	private String seletedType;
	@Getter
	@Setter
	private String seletedRole;
	@Autowired
	private ICryptoUtil cryptoUtil;
	private String roleName;
	private String status;
	private String hiddenRole;
	private String branchId_F;
	private File userImage;
	private String subBranchId_F;

	private String getImageFile;
	private String userImageString;
	private String userImageExist;

	@Getter
	@Setter
	private String redirectContent;

	@Getter
	@Setter
	private String signature;

	@Getter
	@Setter
	private String signatureStr;

	@Getter
	@Setter
	private File file1;

	@Getter
	@Setter
	private String UserDealerId;
	private UtilDAO utilDAO;

	@Getter
	@Setter
	private UserActiveAndInActiveHostory userEditHistory;

	/**
	 * Gets the logger.
	 * 
	 * @return the logger
	 */
	public static Logger getLogger() {

		return logger;
	}

	/**
	 * Gets the language.
	 * 
	 * @return the language
	 */

	public Map<String, String> getLanguageMap() {

		List<Language> languages = utilService.listLanguages();

		for (Language lm : languages) {
			languageMap.put(lm.getCode(), getLocaleProperty(lm.getName()));
		}
		return languageMap;
	}

	/**
	 * Gets the date of birth.
	 * 
	 * @return the date of birth
	 */
	public String getDateOfBirth() {

		return dateOfBirth;
	}

	/**
	 * Sets the date of birth.
	 * 
	 * @param dateOfBirth
	 *            the new date of birth
	 */
	public void setDateOfBirth(String dateOfBirth) {

		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Gets the user.
	 * 
	 * @return the user
	 */
	public User getUser() {

		return user;
	}

	/**
	 * Sets the user.
	 * 
	 * @param user
	 *            the new user
	 */
	public void setUser(User user) {

		this.user = user;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {

		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {

		this.id = id;
	}

	/**
	 * Sets the preferences.
	 * 
	 * @param preferences
	 *            the new preferences
	 */
	public void setPreferences(ESESystem preferences) {

		this.preferences = preferences;
	}

	/**
	 * Gets the preferences.
	 * 
	 * @return the preferences
	 */
	public ESESystem getPreferences() {

		return preferences;
	}

	/**
	 * Gets the prefernces service.
	 * 
	 * @return the prefernces service
	 */
	/**
	 * Sets the prefernces service.
	 * 
	 * @param preferncesService
	 *            the new prefernces service
	 */
	/**
	 * Data.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 * @see com.sourcetrace.esesw.view.SwitchAction#data()
	 */

	public String data() throws Exception {
		Map<String, String> searchRecord = getDataTableJQGridRequestParam(); // get
																				// the
		// search
		// parameter
		// with
		// value

		User filter = new User();

		/*
		 * if (!StringUtil.isEmpty(searchRecord.get("branchId"))) {
		 * filter.setBranchId(searchRecord.get("branchId").trim()); }
		 */

		if (!StringUtil.isEmpty(searchRecord.get("branchId"))) {
			if (!getIsMultiBranch().equalsIgnoreCase("1")) {
				List<String> branchList = new ArrayList<>();
				branchList.add(searchRecord.get("branchId").trim());
				filter.setBranchesList(branchList);
				filter.setBranchFiletr("1");
				filter.setBranchId(searchRecord.get("branchId").trim());
			} else {
				List<String> branchList = new ArrayList<>();
				List<BranchMaster> branches = utilService.listChildBranchIds(searchRecord.get("branchId").trim());
				branchList.add(searchRecord.get("branchId").trim());
				branches.stream().filter(branch -> !StringUtil.isEmpty(branch)).forEach(branch -> {
					branchList.add(branch.getBranchId());
				});
				filter.setBranchesList(branchList);
				filter.setBranchFiletr("1");
			}
		}

		if (!StringUtil.isEmpty(searchRecord.get("subBranchId"))) {
			filter.setBranchId(searchRecord.get("subBranchId").trim());
		}

		String loggedUsername = (String) request.getSession().getAttribute("user");
		if (!StringUtil.isEmpty(loggedUsername) && User.ADMIN_USER_NAME.equals(loggedUsername)) {
			filter.setAdmin(true);
		}

		if (!StringUtil.isEmpty(searchRecord.get("username"))) {
			filter.setUsername(searchRecord.get("username").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("pI.firstName"))
				|| !StringUtil.isEmpty(searchRecord.get("pI.lastName"))) {
			PersonalInfo personalInfo = new PersonalInfo();
			if (!StringUtil.isEmpty(searchRecord.get("pI.firstName")))
				personalInfo.setFirstName(searchRecord.get("pI.firstName").trim());
			if (!StringUtil.isEmpty(searchRecord.get("pI.lastName")))
				personalInfo.setLastName(searchRecord.get("pI.lastName").trim());
			filter.setPersInfo(personalInfo);
		}

		if (!StringUtil.isEmpty(searchRecord.get("cI.mobileNumber"))) {
			ContactInfo contactInfo = new ContactInfo();
			contactInfo.setMobileNumbere(searchRecord.get("cI.mobileNumber").trim());
			filter.setContInfo(contactInfo);
		}

		if (!StringUtil.isEmpty(searchRecord.get("r.name"))) {
			Role role = new Role();
			role.setName(searchRecord.get("r.name").trim());
			filter.setRole(role);
		}

		if (!StringUtil.isEmpty(searchRecord.get("enabled"))) {
			if ("1".equals(searchRecord.get("enabled"))) {
				filter.setFilterStatus("enabled");
				filter.setEnabled(true);
			} else {
				filter.setFilterStatus("disabled");
				filter.setEnabled(false);
			}

		}

		filter.setIsMultiBranch(getIsMultiBranch());
		Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
				getPage());

		return sendDataTableJSONResponse(data);

	}

	/**
	 * Creates the.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String create() throws Exception {
		String result = "";
		String cc = "";
		String webUrl = "";
		if (user == null) {
			command = CREATE;
			/*
			 * user= new User(); user.setPersInfo(new PersonalInfo());
			 * user.setContInfo(new ContactInfo());
			 */
			request.setAttribute(HEADING, getText("Usercreate.page"));
			if (request != null && request.getSession() != null) {
				request.getSession().setAttribute(ISecurityFilter.REPORT, "User Profile");
			}

			return INPUT;
		} else {
			user.setUsername(user.getUsername().toLowerCase());

			String userPass = cryptoUtil.encrypt(StringUtil.getMulipleOfEight(user.getUsername() + user.getPassword()));
			user.setPassword(userPass);
			// user.getContInfo().setCity(null);
			user.setLoginStatus(0);
			if (!StringUtil.isEmpty(seletedRole)) {
				String rileid = seletedRole.split("\\|")[0].toString();
				Role r = utilService.findRole(Long.valueOf(rileid));
				user.setRole(r);
				user.setUserType(r.getType());
			}

			user.setEnabled(user.isEnabled());

			if (getUserImage() != null) {

				user.getPersInfo().setImage(FileUtil.getBinaryFileContent(getUserImage()));

			}

			String bId = StringUtil.isEmpty(getBranchId())
					? ((StringUtil.isEmpty(getBranchId_F()) || getBranchId_F().equals("-1")) ? null : getBranchId_F())
					: getBranchId();
			user.setBranchId(bId);
			user.setStatus(0);
			user.setCreatedDate(new Date());
			user.setCreatedUser(getUsername());
			user.setDataId(0l);
			if (getIsMultiBranch().equals("1") && !StringUtil.isEmpty(getSubBranchId_F())) {
				user.setBranchId(subBranchId_F);
			}
			if (!StringUtil.isEmpty(signature)) {
				DocumentUpload du = new DocumentUpload();

				String[] tokens = signature.split("\\.(?=[^\\.]+$)");

				String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
				if (!name.equals("") && name != null) {
					String filetype = tokens[1];
					if (tokens != null && filetype.equals("jpg") || filetype.equals("JPG") || filetype.equals("PNG")
							|| filetype.equals("jpeg") || filetype.equals("JPEG") || filetype.equals("png")
							|| filetype.equals("pdf") || filetype.equals("PDF")) {

						du.setName(name);
						du.setContent(FileUtil.getBinaryFileContent(getFile1()));
						du.setDocFileContentType(tokens[1]);
						du.setRefCode(String.valueOf(user.getId()));
						du.setType(DocumentUpload.docType.Signature.ordinal());
						du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
						utilService.save(du);
						user.setSignature(du.getId());
					}
				}
			}

			if (getLoggedInDealer() > 0) {
				user.setAgroChDealer(getLoggedInDealer());
			}
			utilService.addUser(user);
			if (user.isChangePassword()) {
				User aUser = utilService.findByUsernameAndBranchId(user.getUsername(), user.getBranchId());
				PasswordHistory ph = new PasswordHistory();
				ph.setBranchId(aUser.getBranchId());
				ph.setCreatedDate(new Date());
				ph.setType(String.valueOf(PasswordHistory.Type.WEB_USER.ordinal()));
				ph.setPassword(aUser.getPassword());
				ph.setReferenceId(aUser.getId());
				utilService.save(ph);
			}
			
			if (userEditHistory == null) {
				userEditHistory = new UserActiveAndInActiveHostory();
				userEditHistory.setBranchId(getBranchId());
				userEditHistory.setCreatedDate(new Date());
				userEditHistory.setCreatedUser(getUsername());
				if(user.getAgroChDealer()!=null && !StringUtil.isEmpty(user.getAgroChDealer())){
				userEditHistory.setAgroChDealer(user.getAgroChDealer());
				}
				userEditHistory.setDate(new Date());
				DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
		    	String dateString = dateFormat.format(new Date()).toString();
				userEditHistory.setTime(dateString);
				userEditHistory.setUserName(user.getUsername());
				userEditHistory.setLoggedUser(getUsername());
				userEditHistory.setActivity(5);
				userEditHistory.setType(1);
				utilService.save(userEditHistory);
			}

			return REDIRECT;
		}
	}

	/**
	 * Detail.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String detail() throws Exception {

		if (id != null && !id.equals(EMPTY)) {
			user = utilService.findUser(Long.valueOf(id));
			if (!ObjectUtil.isEmpty(user)) {
				if (user.getBranchId() != null && !StringUtil.isEmpty(user.getBranchId())) {
					BranchMaster fm = utilService.findBranchMasterByBranchId(user.getBranchId());
					if (fm.getParentBranch() == null || StringUtil.isEmpty(fm.getParentBranch())) {
						branchId_F = user.getBranchId();
						user.setBranchId(null);
						user.setParentBranchId(branchId_F);
					} else {
						BranchMaster parent = utilService
								.findBranchMasterByBranchIdAndDisableFilter(fm.getParentBranch());
						branchId_F = parent.getBranchId();
						subBranchId_F = user.getBranchId();
						user.setParentBranchId(branchId_F);
					}
				}

				setCurrentPage(getCurrentPage());

				setStatus(getLocaleProperty("status" + user.isEnabled()));
				if (user.getPersInfo().getImage() != null) {

					userImageString = Base64Util.encoder(user.getPersInfo().getImage());

				}

				if (user.getRole() != null)
					roleName = user.getRole().getName();
				else
					roleName = "-";

				if (user == null) {
					addActionError(NO_RECORD);
					return REDIRECT;
				}
				id = null;
				command = DETAIL;
				request.setAttribute(HEADING, getText(command));
			} else {
				request.setAttribute(HEADING, getText(LIST));
				return REDIRECT;
			}
		} else {
			request.setAttribute(HEADING, getText(LIST));
			return REDIRECT;

		}
		return command;
	}

	/**
	 * Update.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String update() throws Exception {

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		if (id != null && !id.equals("")) {
			user = utilService.findUser(Long.valueOf(id));
			setCurrentPage(getCurrentPage());
			if (user == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			if (!ObjectUtil.isEmpty(user.getAgroChDealer())) {
				setUserDealerId(String.valueOf(user.getAgroChDealer()));
			}
			String tempPass = user.getPassword();
			if (tempPass != null && !StringUtil.isEmpty(tempPass)) {
				user.setPassword(cryptoUtil.decrypt(tempPass));
			}
			if (!ObjectUtil.isEmpty(user.getRole())) {
				setSeletedRole(user.getRole().getId() + "|" + user.getRole().getType());
			}
			if (StringUtil.isEmpty(user.getPassword()) || user.getPassword() == null) {
				user.setChangePassword(true);
			}
			if (user.getPersInfo().getImage() != null) {

				setUserImageString(Base64Util.encoder(user.getPersInfo().getImage()));
			}
			if ((getIsMultiBranch().equalsIgnoreCase("1")
					&& (getIsParentBranch().equals("1") || StringUtil.isEmpty(branchIdValue)))) {

				if (user.getBranchId() != null && !StringUtil.isEmpty(user.getBranchId())) {
					BranchMaster fm = utilService.findBranchMasterByBranchId(user.getBranchId());
					if (fm.getParentBranch() == null || StringUtil.isEmpty(fm.getParentBranch())) {
						branchId_F = user.getBranchId();
						user.setBranchId(null);
						user.setParentBranchId(branchId_F);
					} else {
						BranchMaster parent = utilService
								.findBranchMasterByBranchIdAndDisableFilter(fm.getParentBranch());
						branchId_F = parent.getBranchId();
						subBranchId_F = user.getBranchId();
						user.setParentBranchId(branchId_F);
					}
				}

			}
			setSignatureStr(String.valueOf(user.getSignature()));
			System.out.println(getSignature());
			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getText("Userupdate.page"));
		} else {
			if (user != null) {
				User temp = utilService.findUser(user.getId());

				if (!ObjectUtil.isEmpty(temp)) {
					temp.setUsername(user.getUsername());
					temp.setUpdatedDate(new Date());
					temp.setUpdatedUser(getUsername());
					temp.getContInfo().setType(user.getContInfo().getType());
					temp.getContInfo().setAddress1(user.getContInfo().getAddress1());
					temp.getContInfo().setAddress2(user.getContInfo().getAddress2());
					temp.getContInfo().setZipCode(user.getContInfo().getZipCode());
					temp.getContInfo().setPhoneNumber(user.getContInfo().getPhoneNumber());
					temp.getContInfo().setEmail(user.getContInfo().getEmail());
					temp.getContInfo().setMobileNumbere(user.getContInfo().getMobileNumbere());

					temp.getPersInfo().setFirstName(user.getPersInfo().getFirstName());
					temp.getPersInfo().setMiddleName(user.getPersInfo().getMiddleName());
					temp.getPersInfo().setLastName(user.getPersInfo().getLastName());
					temp.getPersInfo().setIdentityType(user.getPersInfo().getIdentityType());
					temp.getPersInfo().setIdentityNumber(user.getPersInfo().getIdentityNumber());
					temp.getPersInfo().setSex(user.getPersInfo().getSex());
					temp.setEnabled(user.isEnabled());
					if (!ObjectUtil.isEmpty(user.getRole())) {
						Role r = utilService.findRole(user.getRole().getId());
						temp.setUserType(r.getType());
						temp.setRole(r);
					}
					temp.setLanguage(user.getLanguage());
					if (getUserImage() != null) {
						temp.getPersInfo().setImage(FileUtil.getBinaryFileContent(getUserImage()));

					}
					if (getUserImageExist() != null && getUserImageExist().equals("1")) {
						temp.getPersInfo().setImage(null);
					}
					User userCred = utilService.findUser(user.getId());
					setCurrentPage(getCurrentPage());
					if (temp == null) {
						addActionError(NO_RECORD);
						return REDIRECT;
					}
					if (!StringUtil.isEmpty(signature)) {
						DocumentUpload du = new DocumentUpload();

						String[] tokens = signature.split("\\.(?=[^\\.]+$)");

						String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
						if (!name.equals("") && name != null) {
							String filetype = tokens[1];
							if (tokens != null && filetype.equals("jpg") || filetype.equals("JPG")
									|| filetype.equals("PNG") || filetype.equals("jpeg") || filetype.equals("JPEG")
									|| filetype.equals("png") || filetype.equals("pdf") || filetype.equals("PDF")) {

								du.setName(name);
								du.setContent(FileUtil.getBinaryFileContent(getFile1()));
								du.setDocFileContentType(tokens[1]);
								du.setRefCode(String.valueOf(user.getId()));
								du.setType(DocumentUpload.docType.FumigatorProof.ordinal());
								du.setFileType(DocumentUpload.fileType.TEXT.ordinal());
								utilService.save(du);
								temp.setSignature(du.getId());
							}
						}
					}
					temp.setDataId(0l);
					utilService.editUser(temp);

					if (user.isChangePassword()) {
						temp.setPassword(user.getPassword());
						utilService.editUserCredential(temp);
					}

					if (userEditHistory == null) {
						userEditHistory = new UserActiveAndInActiveHostory();
						userEditHistory.setBranchId(getBranchId());
						userEditHistory.setCreatedDate(new Date());
						userEditHistory.setCreatedUser(getUsername());
						if(temp.getAgroChDealer()!=null && !StringUtil.isEmpty(user.getAgroChDealer())){
						userEditHistory.setAgroChDealer(temp.getAgroChDealer());
						}
						userEditHistory.setDate(new Date());
						DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
				    	String dateString = dateFormat.format(new Date()).toString();
						userEditHistory.setTime(dateString);
						userEditHistory.setUserName(temp.getUsername());
						userEditHistory.setLoggedUser(getUsername());
						userEditHistory.setActivity(temp.isEnabled() ? 1 : 0);
						userEditHistory.setType(1);
						utilService.save(userEditHistory);
					}

				} else {
					addActionError(getText("user.deleted"));
					return INPUT;
				}
			}
			request.setAttribute(HEADING, getText(LIST));
			return REDIRECT;

		}
		return super.execute();
	}

	public void populateImage() {

		try {

			HttpServletResponse response = ServletActionContext.getResponse();
			response.reset();
			response.setContentType("multipart/form-data");
			OutputStream out = response.getOutputStream();
			byte[] imageData = new byte[] {};
			if (StringUtil.isLong(id)) {
				imageData = utilService.findUserImage(Long.parseLong(id));
			}

			if (ObjectUtil.isEmpty(imageData) || imageData.length == 0) {
				String logoPath = request.getSession().getServletContext().getRealPath("/img/avatar-small.jpg");
				File pic = new File(logoPath);
				long length = pic.length();
				imageData = new byte[(int) length];
				FileInputStream picIn = new FileInputStream(pic);
				picIn.read(imageData);
			}
			out.write(imageData);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
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

		user = utilService.findUser(Long.valueOf(id));
		String loggedUsername = (String) request.getSession().getAttribute("user");
		setCurrentPage(getCurrentPage());
		if (user == null) {
			addActionError(NO_RECORD);
			return list();
		}
		if (user != null) {
			if (StringUtil.isEqual(user.getUsername(), loggedUsername)) {
				addActionError(getText("cannotDeleteLoggedUser"));
				request.setAttribute(HEADING, getText(DETAIL));
				if (!ObjectUtil.isEmpty(user.getPersInfo())
						&& !StringUtil.isEmpty(user.getPersInfo().getDateOfBirth())) {
					dateOfBirth = df.format(user.getPersInfo().getDateOfBirth());
				}
				return DETAIL;
			} else {
				if (user != null) {
					user.setDataId(3L);
					user.setEnabled(false);
					utilService.update(user);
				}

			}
		}

		return REDIRECT;

	}

	/**
	 * To json.
	 * 
	 * @param obj
	 *            the obj
	 * @return the JSON object
	 * @see com.sourcetrace.esesw.view.SwitchAction#toJSON(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(Object obj) {

		User user = (User) obj;
		JSONObject jsonObject = new JSONObject();
		// JSONArray rows = new JSONArray();
		JSONObject objDt = new JSONObject();

		if ((getIsMultiBranch().equalsIgnoreCase("1")
				&& (getIsParentBranch().equals("1") || StringUtil.isEmpty(branchIdValue)))) {

			if (StringUtil.isEmpty(branchIdValue)) {
				objDt.put("branch",
						!StringUtil.isEmpty(getBranchesMap().get(getParentBranchMap().get(user.getBranchId())))
								? getBranchesMap().get(getParentBranchMap().get(user.getBranchId()))
								: getBranchesMap().get(user.getBranchId()));
			}
			objDt.put("branch", getBranchesMap().get(user.getBranchId()));

		} else {
			if (StringUtil.isEmpty(branchIdValue)) {
				objDt.put("branch", branchesMap.get(user.getBranchId()));
			}
		}

		objDt.put("username", "<font color=\"#0000FF\" style=\"cursor:pointer;\">" + user.getUsername() + "</font>");
		objDt.put("firstName", user.getPersInfo().getFirstName());
		objDt.put("lastName", user.getPersInfo().getLastName());

		objDt.put("mobileNumber",
				user.getContInfo().getMobileNumbere() == null ? "" : user.getContInfo().getMobileNumbere());
		objDt.put("name", !ObjectUtil.isEmpty(user.getRole())
				? (!StringUtil.isEmpty(user.getRole().getName()) ? user.getRole().getName() : "") : "");
		// rows.add(user.isEnabled() ? getText("enabled") :
		// getText("disabled"));
		objDt.put("enabled", getLocaleProperty("status" + user.isEnabled()));
		// rows.add("<font color=\"#0000FF\">delete</font>");
		jsonObject.put("DT_RowId", user.getId());
		jsonObject.put("cell", objDt);
		return jsonObject;
	}

	/**
	 * Gets the data.
	 * 
	 * @return the data
	 * @see com.sourcetrace.esesw.view.SwitchValidatorAction#getData()
	 */
	public Object getData() {

		// To get Language List
		List<Language> languages = utilService.listLanguages();
		for (Language language : languages) {
			languageMap.put(language.getCode(), getLocaleProperty(language.getName()));
		}

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		if (user != null) {
			try {
				/*
				 * if (!StringUtil.isEmpty(dateOfBirth)) {
				 * user.getPersInfo().setDateOfBirth(df.parse(dateOfBirth)); }
				 * else { user.getPersInfo().setDateOfBirth(null); }
				 */
				user.setIsMultiBranch(getIsMultiBranch());
				if (getBranchId() != null) {
					user.setParentBranchId(getBranchId());
				} else {
					user.setParentBranchId(getBranchId_F());
				}
				if (user.getBranchId() != null && !StringUtil.isEmpty(user.getBranchId())) {
					BranchMaster fm = utilService.findBranchMasterByBranchId(user.getBranchId());
					if (fm.getParentBranch() == null || StringUtil.isEmpty(fm.getParentBranch())) {
						branchId_F = user.getBranchId();
					} else {
						BranchMaster parent = utilService
								.findBranchMasterByBranchIdAndDisableFilter(fm.getParentBranch());
						branchId_F = parent.getBranchId();
						subBranchId_F = user.getBranchId();
						user.setParentBranchId(branchId_F);
					}
				}
				if (!StringUtil.isEmpty(seletedRole) && seletedRole.contains("|")) {
					user.setRole(new Role());
					user.getRole().setId(Long.valueOf(seletedRole.split("\\|")[0].toString()));
					user.getRole().setType(Integer.valueOf(seletedRole.split("\\|")[1].toString()));
				}

				if (user.getRole() != null) {
					setSeletedRole(user.getRole().getId() + "|" + user.getRole().getType());
				}
				if (!StringUtil.isEmpty(user.getSignature()))
					user.setSignature(Long.valueOf(user.getSignature()));
			} catch (Exception e) {
				e.printStackTrace();

			}
			if (getLoggedInDealer() > 0) {
				user.setAgroChDealer(getLoggedInDealer());
			}
			user.setUsername(user.getUsername() != null ? user.getUsername().toLowerCase() : "");

		}

		return user;
	}

	/**
	 * Gets the roles.
	 * 
	 * @return the roles
	 */
	public Map<String, String> getRoles() {

		Map<String, String> roleMa = new HashMap<>();
		roles = utilService.listRoles();
		if (getLoggedInDealer() > 0 && getLoggedInRoleType() != null
				&& getLoggedInRoleType() == Role.Type.EXPORTER_ADMIN.ordinal()) {
			roleMa = roles.stream()
					.filter(u -> Arrays.asList(Role.Type.EXPORTER_USER.ordinal(), Role.Type.EXPORTER_ADMIN.ordinal())
							.contains(u.getType()))
					.collect(Collectors.toMap(u -> u.getId() + "|" + u.getType(), u -> u.getName()));
		} else if (getLoggedInDealer() > 0 && getLoggedInRoleType() != null
				&& getLoggedInRoleType() == Role.Type.EXPORTER_USER.ordinal()) {
			roleMa = roles.stream().filter(u -> u.getType() == Role.Type.EXPORTER_USER.ordinal())
					.collect(Collectors.toMap(u -> u.getId() + "|" + u.getType(), u -> u.getName()));
		} else {
			roleMa = roles.stream().collect(Collectors.toMap(u -> u.getId() + "|" + u.getType(), u -> u.getName()));
		}
		return roleMa;
	}

	public void populateRoles() {

		roles = new ArrayList<Role>();

		if (!branchId_F.equals("-1")) {
			roles = utilService.listRolesByTypeAndBranchIdExcludeBranch(getBranchId_F());
		}
		Map<String, String> map = ReflectUtil.buildMap(roles, new String[] { "id", "name" });
		sendAjaxResponse(JsonUtil.maptoJSONArrayMap(map));
	}

	public Map<String, String> getParentBranches() {
		return utilService.findParentBranches().stream().collect(
				Collectors.toMap(branchId -> String.valueOf(branchId[0]), branchName -> String.valueOf(branchName[1])));
	}

	public Map<String, String> getSubBranchesMap() {
		String branchId = getBranchId() == null ? getBranchId_F()
				: StringUtil.isEmpty(getBranchId()) ? getBranchId_F() : getBranchId();
		return utilService.listChildBranchIds(branchId).stream().filter(branch -> !ObjectUtil.isEmpty(branch))
				.collect(Collectors.toMap(BranchMaster::getBranchId, BranchMaster::getName));
	}

	public void populateSubBranches() {
		JSONArray branchArr = new JSONArray();
		if (!StringUtil.isEmpty(getBranchId_F())) {
			utilService.listChildBranchIds(branchId_F).stream().filter(branch -> !ObjectUtil.isEmpty(branch))
					.forEach(branch -> {
						branchArr.add(getJSONObject(branch.getBranchId(), branch.getName()));
					});
		} else {

		}
		sendAjaxResponse(branchArr);
	}

	@SuppressWarnings("unchecked")
	protected JSONObject getJSONObject(Object id, Object name) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("name", name);
		return jsonObject;
	}

	/**
	 * Sets the roles.
	 * 
	 * @param roles
	 *            the new roles
	 */
	public void setRoles(List<Role> roles) {

		this.roles = roles;
	}

	/**
	 * Gets the role service.
	 * 
	 * @return the role service
	 */
	/**
	 * Sets the role service.
	 * 
	 * @param roleService
	 *            the new role service
	 */
	/**
	 * Sets the seleted type.
	 * 
	 * @param seletedType
	 *            the new seleted type
	 */
	public void setSeletedType(String seletedType) {

		this.seletedType = seletedType;
	}

	/**
	 * Gets the seleted type.
	 * 
	 * @return the seleted type
	 */
	public String getSeletedType() {

		return seletedType;
	}

	/**
	 * Gets the crypto util.
	 * 
	 * @return the crypto util
	 */
	public ICryptoUtil getCryptoUtil() {

		return cryptoUtil;
	}

	/**
	 * Sets the crypto util.
	 * 
	 * @param cryptoUtil
	 *            the new crypto util
	 */
	public void setCryptoUtil(ICryptoUtil cryptoUtil) {

		this.cryptoUtil = cryptoUtil;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {

		this.status = status;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public String getStatus() {

		return status;
	}

	/**
	 * Sets the role name.
	 * 
	 * @param roleName
	 *            the new role name
	 */
	public void setRoleName(String roleName) {

		this.roleName = roleName;
	}

	/**
	 * Gets the role name.
	 * 
	 * @return the role name
	 */
	public String getRoleName() {

		return roleName;
	}

	public String getBranchId_F() {

		return branchId_F;
	}

	public void setBranchId_F(String branchId_F) {

		this.branchId_F = branchId_F;
	}

	public String getHiddenRole() {

		return hiddenRole;
	}

	public void setHiddenRole(String hiddenRole) {

		this.hiddenRole = hiddenRole;
	}

	public File getUserImage() {

		return userImage;
	}

	public void setUserImage(File userImage) {

		this.userImage = userImage;
	}

	public PersonalInfo getPersInfo() {
		return persInfo;
	}

	public void setPersInfo(PersonalInfo persInfo) {
		this.persInfo = persInfo;
	}

	public String getGetImageFile() {

		return getImageFile;
	}

	public void setGetImageFile(String getImageFile) {

		this.getImageFile = getImageFile;
	}

	public String getUserImageString() {

		return userImageString;
	}

	public void setUserImageString(String userImageString) {

		this.userImageString = userImageString;
	}

	public String getSubBranchId_F() {
		return subBranchId_F;
	}

	public void setSubBranchId_F(String subBranchId_F) {
		this.subBranchId_F = subBranchId_F;
	}

	public String getUserImageExist() {
		return userImageExist;
	}

	public void setUserImageExist(String userImageExist) {
		this.userImageExist = userImageExist;
	}

	public IUtilService getUtilService() {
		return utilService;
	}

	public void setUtilService(IUtilService utilService) {
		this.utilService = utilService;
	}

	public Map<Integer, String> getUserTypeList() {
		Map<Integer, String> DealerStatus = new LinkedHashMap<Integer, String>();
		DealerStatus.put(User.userType1.Admin.ordinal(), getText("dealerRole" + User.userType1.Admin.ordinal()));
		DealerStatus.put(User.userType1.AgroChemical.ordinal(),
				getText("dealerRole" + User.userType1.AgroChemical.ordinal()));

		DealerStatus.put(User.userType1.Inspector.ordinal(),
				getText("dealerRole" + User.userType1.Inspector.ordinal()));
		DealerStatus.put(User.userType1.SeedInspector.ordinal(),
				getText("dealerRole" + User.userType1.SeedInspector.ordinal()));

		return DealerStatus;
	}

}
