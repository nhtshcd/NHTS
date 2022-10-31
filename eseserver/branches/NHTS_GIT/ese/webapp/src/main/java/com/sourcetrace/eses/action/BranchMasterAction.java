package com.sourcetrace.eses.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.ESEAccount;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

@SuppressWarnings("serial")
public class BranchMasterAction extends SwitchValidatorAction {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(BranchMasterAction.class);

	protected static final String CREATE = "create";
	protected static final String DETAIL = "detail";
	protected static final String UPDATE = "update";
	protected static final String MAPPING = "mapping";
	protected static final String DELETE = "delete";
	protected static final String LIST = "list";
	protected static final String TITLE_PREFIX = "title.";
	protected static final String HEADING = "heading";

	public static final String COUNTRY_ID_SEQ = "COUNTRY_ID_SEQ";
	public static final String STATE_ID_SEQ = "STATE_ID_SEQ";
	public static final String DISTRICT_ID_SEQ = "DISTRICT_ID_SEQ";
	public static final String MANDAL_ID_SEQ = "MANDAL_ID_SEQ";
	public static final String VILLAGE_ID_SEQ = "VILLAGE_ID_SEQ";

	public static final String DEVICE_ID_SEQ = "DEVICE_ID_SEQ";
	public static final String GROUP_ID_SEQ = "GROUP_ID_SEQ";
	public static final String WAREHOUSE_ID_SEQ = "WAREHOUSE_ID_SEQ";

	public static final String SUBCATEGORY_ID_SEQ = "SUBCATEGORY_ID_SEQ";

	public static final String PRODUCT_ID_SEQ = "PRODUCT_ID_SEQ";
	private ESEAccount account;

	private String id;
	private String branchId;
	private String name;
	private String contactPerson;
	private String phoneNo;
	private String address;
	private String profileId;
	private BranchMaster branchMaster;
	private BranchMaster filter;
	private Map<String, String> statusMap = new LinkedHashMap<>();
	private String status;
	private String statusDeafaultVal;

	@Autowired
	private IUtilService utilService;
	@Autowired
	private IUniqueIDGenerator idGenerator;
	
	

	@Override
	public Object getData() {
		if (ObjectUtil.isEmpty(branchMaster)) {
			return null;
		} else {
			return branchMaster;
		}
	}

	@SuppressWarnings("unchecked")
	public String data() throws Exception {


		Map<String, String> searchRecord = getDataTableJQGridRequestParam(); // get
																				// the
		// search
		// parameter
		// with

		BranchMaster filter = new BranchMaster();

		if (!StringUtil.isEmpty(searchRecord.get("branchId"))) {
			filter.setBranchId(searchRecord.get("branchId").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("name"))) {
			filter.setName(searchRecord.get("name").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("contactPerson"))) {
			filter.setContactPerson(searchRecord.get("contactPerson").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("phoneNo"))) {
			filter.setPhoneNo(searchRecord.get("phoneNo").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("address"))) {
			filter.setAddress(searchRecord.get("address").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("status"))) {
			filter.setStatus(searchRecord.get("status").trim());
		}

		Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
				getPage());

		 return sendDataTableJSONResponse(data);

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
			branchMaster = utilService.findBranchMasterById(Long.valueOf(id));
			account = utilService.findAccountByProfileIdAndProfileType(branchMaster.getBranchId(),
					ESEAccount.ORGANIZATION_ACCOUNT);
			if (branchMaster == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			account = utilService.findAccountByProfileIdAndProfileType(branchMaster.getBranchId(),
					ESEAccount.ORGANIZATION_ACCOUNT);
			
			if (!ObjectUtil.isEmpty(account)) {
				String[] agentAcc = String.valueOf(account.getCashBalance()).split("\\.");
				branchMaster.setAccountRupee(agentAcc[0]);
				branchMaster.setAccountPaise(agentAcc[1]);
			}
			setStatus(!ObjectUtil.isEmpty(branchMaster.getStatus()) && branchMaster.getStatus().equals("0")?getText("status0"):getText("status1"));
			setCurrentPage(getCurrentPage());
			command = UPDATE;
			view = DETAIL;
			request.setAttribute(HEADING, getText(DETAIL));
		} else {
			request.setAttribute(HEADING, getText(LIST));
			return LIST;
		}
		return view;
	}

	/**
	 * Creates the.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String create() throws Exception {

		if (branchMaster == null) {
			command = "create";
			request.setAttribute(HEADING, getText(CREATE));
			setStatusDeafaultVal("1");
			return INPUT;
		} else {
			if (!StringUtil.isEmpty(branchMaster.getAccountBalance())) {
				branchMaster.setAccountBalance(branchMaster.getAccountBalance());
			} else
				branchMaster.setAccountBalance(0.00);

			if (!StringUtil.isEmpty(super.getBranchId())) {
				BranchMaster branch = utilService.findBranchMasterByBranchIdAndDisableFilter(super.getBranchId());
				branchMaster.setparentBranch(branch.getBranchId());
			}
			branchMaster.setTenant(getCurrentTenantId());
			utilService.addBranchMaster(branchMaster);
			ESESystem ese = new ESESystem();
			Map<String, String> preferences = new HashMap<String, String>();
			ESESystem mainese = utilService.findPrefernceById("1");
			preferences.put(ESESystem.INVALID_ATTEMPTS_COUNT,
					mainese.getPreferences().get(ESESystem.INVALID_ATTEMPTS_COUNT));
			preferences.put(ESESystem.TIME_TO_AUTO_RELEASE,
					mainese.getPreferences().get(ESESystem.TIME_TO_AUTO_RELEASE));
			preferences.put(ESESystem.AREA_CAPTURE_MODE, mainese.getPreferences().get(ESESystem.AREA_CAPTURE_MODE));
			preferences.put(ESESystem.GEO_FENCING_FLAG, mainese.getPreferences().get(ESESystem.GEO_FENCING_FLAG));
			preferences.put(ESESystem.GEO_FENCING_RADIUS_MT,
					mainese.getPreferences().get(ESESystem.GEO_FENCING_RADIUS_MT));
			preferences.put(ESESystem.CURRENT_SEASON_CODE, "");
			ese.setName(branchMaster.getBranchId());
			ese.setPreferences(preferences);
			utilService.addOrganisationESE(ese);
			request = ReflectUtil.getCurrentHttpRequest();
			Object isMultiBranch = request.getSession().getAttribute(ISecurityFilter.IS_MULTI_BRANCH_APP);
			String multiBranch = ObjectUtil.isEmpty(isMultiBranch) ? "" : isMultiBranch.toString();
			if (multiBranch.equals("1")) {
				Object object = request.getSession().getAttribute(ISecurityFilter.MAPPED_BRANCHES);
				String currentBranch = ObjectUtil.isEmpty(object) ? "" : object.toString();
				currentBranch = currentBranch + "," + branchMaster.getBranchId();
				request.getSession().setAttribute(ISecurityFilter.MAPPED_BRANCHES, currentBranch);
			}
			return REDIRECT;
		}
	}

	/**
	 * Update.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String update() throws Exception {

		if (id != null && !id.equals("")) {
			branchMaster = utilService.findBranchMasterById(Long.valueOf(id));
			if (branchMaster == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			setStatusDeafaultVal(branchMaster.getStatus());
			account = utilService.findAccountByProfileIdAndProfileType(branchMaster.getBranchId(),
					ESEAccount.ORGANIZATION_ACCOUNT);
			if (!StringUtil.isEmpty(account)) {
				String[] accBalance = String.valueOf(account.getCashBalance()).split("\\.");
				branchMaster.setAccountRupee(String.valueOf(accBalance[0]));
				branchMaster.setAccountPaise(String.valueOf(accBalance[1]));
			} else {
				branchMaster.setAccountRupee("0");
				branchMaster.setAccountPaise("00");
			}

			setCurrentPage(getCurrentPage());

			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getText(UPDATE));
		} else {
			if (branchMaster != null) {
				BranchMaster tempBranchMaster = utilService.findBranchMasterById(Long.valueOf(branchMaster.getId()));
				if (tempBranchMaster == null) {
					addActionError(NO_RECORD);
					return REDIRECT;
				}
				setCurrentPage(getCurrentPage());
				// tempBranchMaster.setBranchId(branchMaster.getBranchId());
				tempBranchMaster.setName(branchMaster.getName());
				tempBranchMaster.setContactPerson(branchMaster.getContactPerson());
				tempBranchMaster.setPhoneNo(branchMaster.getPhoneNo());
				tempBranchMaster.setAddress(branchMaster.getAddress());
				tempBranchMaster.setAccountBalance(branchMaster.getAccountBalance());
				tempBranchMaster.setStatus(branchMaster.getStatus());
				utilService.editBranchMaster(tempBranchMaster);

				account = utilService.findAccountByProfileIdAndProfileType(tempBranchMaster.getBranchId(),
						ESEAccount.ORGANIZATION_ACCOUNT);
				if (account != null) {
					account.setCashBalance(branchMaster.getAccountBalance());
					utilService.update(account);
				}
			}
			request.setAttribute(HEADING, getText(LIST));
			return LIST;
		}
		return super.execute();
	}

	/**
	 * Delete.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String delete() throws Exception {

		if (this.getId() != null && !(this.getId().equals(EMPTY))) {
			branchMaster = utilService.findBranchMasterById(Long.valueOf(getId()));
			if (branchMaster == null) {
				addActionError(NO_RECORD);
				return null;
			}
			if (!ObjectUtil.isEmpty(branchMaster)) {
				// clientService.removeBranchMaster(branchMaster); // this line
				// has been commented to disable deleting branch master.

			}

		}

		request.setAttribute(HEADING, getText(LIST));
		return LIST;

	}

	@SuppressWarnings("unchecked")
	public JSONObject toJSON(Object record) {

		JSONObject jsonObject = null;
		jsonObject = new JSONObject();
		if (record instanceof BranchMaster) {
			BranchMaster branchMaster = (BranchMaster) record;
			//JSONArray rows = new JSONArray();
			JSONObject objDt = new JSONObject();
			if ((getIsMultiBranch().equalsIgnoreCase("1")
					&& (getIsParentBranch().equals("1") || StringUtil.isEmpty(branchIdValue)))) {

				if (StringUtil.isEmpty(branchIdValue)) {
					objDt.put("branch",
							!StringUtil.isEmpty(getBranchesMap().get(getParentBranchMap().get(branchMaster.getBranchId())))
									? getBranchesMap().get(getParentBranchMap().get(branchMaster.getBranchId()))
									: getBranchesMap().get(branchMaster.getBranchId()));
				}
				objDt.put("branch", getBranchesMap().get(branchMaster.getBranchId()));

			} else {
				if (StringUtil.isEmpty(branchIdValue)) {
					objDt.put("branch", branchesMap.get(branchMaster.getBranchId()));
				}
			}

			objDt.put("name", branchMaster.getName());
			objDt.put("contactPerson", branchMaster.getContactPerson());
			objDt.put("phoneNo", branchMaster.getPhoneNo());
			objDt.put("address", branchMaster.getAddress());
			
			if(!StringUtil.isEmpty(branchMaster.getStatus())){
				objDt.put("status",branchMaster.getStatus().equals("0")?getText("status0"):getText("status1"));
			}else{
				//rows.add(getText("status1"));
				objDt.put("status", getText("status1"));
			}
			jsonObject.put("DT_RowId", branchMaster.getId());
			jsonObject.put("cell", objDt);
			return jsonObject;
		}
		return jsonObject;
	}

	/**
	 * Populate state.
	 * 
	 * @param populateResponce
	 *            the populate responce
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */

	protected String sendResponse(List<?> populateResponce) throws Exception {

		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.print(populateResponce);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BranchMaster getBranchMaster() {
		return branchMaster;
	}

	public void setBranchMaster(BranchMaster branchMaster) {
		this.branchMaster = branchMaster;
	}

	public BranchMaster getFilter() {
		return filter;
	}

	public void setFilter(BranchMaster filter) {
		this.filter = filter;
	}

	public ESEAccount getAccount() {
		return account;
	}

	public void setAccount(ESEAccount account) {
		this.account = account;
	}

	public IUniqueIDGenerator getIdGenerator() {
		return idGenerator;
	}

	public void setIdGenerator(IUniqueIDGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public Map<String, String> getStatusMap() {
		statusMap.put("1", getText("status1"));
		statusMap.put("0", getText("status0"));
		return statusMap;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDeafaultVal() {
		return statusDeafaultVal;
	}

	public void setStatusDeafaultVal(String statusDeafaultVal) {
		this.statusDeafaultVal = statusDeafaultVal;
	}
	
}
