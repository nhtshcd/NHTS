package com.sourcetrace.eses.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sourcetrace.eses.entity.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ICryptoUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CustomerAction.
 */
@SuppressWarnings("serial")
public class CustomerAction extends SwitchAction {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CustomerAction.class);

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

	private Customer customer;
	private Customer filter;
	private String id;
	private String selectedHoldingType;

	private Map<String, String> holdingTypeList = new LinkedHashMap<String, String>();
	private String tabIndex = "#tabs-1";

	private Map<String, String> listOfCustomerTypes;
	private Map<String, String> listOfCustomerSegment;
	/*List<Locality> localities = new ArrayList<Locality>();
	List<Municipality> municipalities = new ArrayList<Municipality>();
	List<State> states = new ArrayList<State>();
	List<Country> countries = new ArrayList<Country>();*/
	/*private String selectedCountry;
	private String selectedState;
	private Municipality municipality;
	private String selectedDistrict;
	private String selectedCountryCode;
	private String selectedBranch;
	private String selectedStateCode;*/

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
	private String redirectContent;

	/**
	 * Data.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 * @see com.sourcetrace.esesw.view.SwitchAction#data()
	 */
	@SuppressWarnings("unchecked")
	public Map buildFilterDataMap() throws Exception {

		Map<String, String> searchRecord = getDataTableJQGridRequestParam(); // get
																				// the
		// search
		// parameter
		// with

		Customer filter = new Customer();

		if (!StringUtil.isEmpty(searchRecord.get("customerId"))) {
			filter.setCustomerId(searchRecord.get("customerId").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("customerName"))) {
			filter.setCustomerName(searchRecord.get("customerName").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("personName"))) {
			filter.setPersonName(searchRecord.get("personName").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("emailId"))) {
			filter.setEmailId(searchRecord.get("emailId").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("mobileNo"))) {
			filter.setMobileNo(searchRecord.get("mobileNo").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("customerAddress"))) {
			filter.setCustomerAddress(searchRecord.get("customerAddress").trim());
		}

		Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
				getPage());

		return data;
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
			customer = utilService.findCustomer(Long.valueOf(id));
			if (customer == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			setCurrentPage(getCurrentPage());
			// String customerTypes=getText("listOfCustomerTypes");
			if (!StringUtil.isEmpty(customer.getCustomerType()) && Integer.valueOf(customer.getCustomerType()) > -1) {
				// String[] customers=customerTypes.split(",");
				customer.setCustomerType((getListOfCustomerTypes().get(customer.getCustomerType())));
			} else {
				customer.setCustomerType("");
			}
			if (!StringUtil.isEmpty(customer.getCustomerSegment())
					&& Integer.valueOf(customer.getCustomerSegment()) > -1) {
				String customerSegments = getText("customerSegments");

				String[] customerSegment = customerSegments.split(",");
				customer.setCustomerSegment((getListOfCustomerSegment().get(customer.getCustomerSegment())));
			} else {
				customer.setCustomerSegment("");
			}
			if (customer.getCity() != null) {
				customer.getCity().setName(customer.getCity().getName());
			}
			command = UPDATE;
			if (customer.getCity() != null) {
				setSelectedCity(
						customer.getCity() != null ? String.valueOf(customer.getCity().getId()) : "");
				setSelectedLocality(customer.getCity() != null
						? String.valueOf(customer.getCity().getLocality().getId()) : "");
				setSelectedState(customer.getCity() != null
						? String.valueOf(customer.getCity().getLocality().getState().getId()) : "");
				setSelectedCountry(customer.getCity() != null
						? customer.getCity().getLocality().getState().getCountry().getName() : "");
			}
			view = DETAIL;
			request.setAttribute(HEADING, getText("buyerdetail"));
		} else {
			request.setAttribute(HEADING, getText("buyerlist"));
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

		if (customer == null) {
			command = "create";
			request.setAttribute(HEADING, getText("buyercreate"));
			customer = new Customer();
			if (getLoggedInDealer() > 0) {
				ExporterRegistration ex = utilService.findExportRegById(Long.valueOf(getLoggedInDealer()));
				customer.setExporter(ex);
			}
			return INPUT;
		} else {
			/*if (customer.getLocationDetail() != null) {
				Locality locality = utilService.findLocalityByCode(customer.getLocationDetail().getName());
				customer.setLocationDetail(locality);
			}
			if (customer.getCity() != null) {
				Municipality municipality = utilService.findMunicipalityByCode(customer.getCity().getName());
				customer.setCity(municipality);
			}*/
			
			if (!StringUtil.isEmpty(selectedCity) && selectedCity != null) {
				Municipality lt = utilService.findMunicipalityById(Long.valueOf(selectedCity));
				customer.setCity(lt);
			}

			String customerIdSeq = idGenerator.createCustomerId();
			customer.setCustomerId(customerIdSeq);
			customer.setBranchId(getBranchId());
			customer.setRevisionNo(DateUtil.getRevisionNumber());
			customer.setStatus(0);

			customer.setCreatedUser(getUsername());
			customer.setCreatedDate(new Date());
			if (customer.getExporter() != null && customer.getExporter().getId() > 0) {
				ExporterRegistration ex = utilService.findExportRegById(Long.valueOf(customer.getExporter().getId()));
				customer.setExporter(ex);
			}
			utilService.addCustomer(customer);
			// String accountNo =
			// idGenerator.createCustomerAccountNoSequence(customerIdSeq);
			// utilService.createAccount(customerIdSeq, accountNo, new Date(),
			// ESEAccount.CLIENT_ACCOUNT,customer.getBranchId());
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
			customer = utilService.findCustomer(Long.valueOf(id));
			if (customer == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			setCurrentPage(getCurrentPage());
			/*if (customer.getLocationDetail() != null) {
				setSelectedCountry(customer.getLocationDetail().getState().getCountry().getCode());
				setSelectedState(customer.getLocationDetail().getState().getCode());
			}
			if (customer.getLocationDetail() != null) {
				Locality locality = utilService.findLocalityByCode(customer.getLocationDetail().getName());
				customer.setLocationDetail(locality);
			}
			if (customer.getCity() != null) {
				Municipality municipality = utilService.findMunicipalityByCode(customer.getCity().getName());
				customer.setCity(municipality);
			}*/
			
			setSelectedCity(
					customer.getCity() != null ? String.valueOf(customer.getCity().getId()) : "");
			setSelectedLocality(customer.getCity() != null
					? String.valueOf(customer.getCity().getLocality().getId()) : "");
			setSelectedState(customer.getCity() != null
					? String.valueOf(customer.getCity().getLocality().getState().getId()) : "");
			setSelectedCountry(customer.getCity() != null
					? customer.getCity().getLocality().getState().getCountry().getName() : "");
			setCurrentPage(getCurrentPage());
			
			
			
			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getText("buyerupdate"));
		} else {
			if (customer != null) {
				Customer tempCustomer = utilService.findCustomer(Long.valueOf(customer.getId()));
				if (tempCustomer == null) {
					addActionError(NO_RECORD);
					return REDIRECT;
				}
				setCurrentPage(getCurrentPage());
				/*if (customer.getLocationDetail() != null) {
					Locality locality = utilService.findLocalityByCode(customer.getLocationDetail().getName());
					tempCustomer.setLocationDetail(locality);
				}
				if (customer.getCity() != null) {
					Municipality municipality = utilService.findMunicipalityByCode(customer.getCity().getName());
					tempCustomer.setCity(municipality);
				}*/
				
				if (!StringUtil.isEmpty(selectedCity) && selectedCity != null) {
					Municipality city = utilService.findMunicipalityById(Long.valueOf(selectedCity));
					tempCustomer.setCity(city);
				}
				
				tempCustomer.setCustomerName(customer.getCustomerName());
				tempCustomer.setCustomerAddress(customer.getCustomerAddress());
				tempCustomer.setEmailId(customer.getEmailId());
				tempCustomer.setPersonName(customer.getPersonName());
				tempCustomer.setLandline(customer.getLandline());
				tempCustomer.setMobileNo(customer.getMobileNo());
				tempCustomer.setCustomerType(customer.getCustomerType());
				tempCustomer.setCustomerSegment(customer.getCustomerSegment());
				tempCustomer.setRevisionNo(DateUtil.getRevisionNumber());
				tempCustomer.setUpdatedUser(getUsername());
				tempCustomer.setUpdatedDate(new Date());
				if (customer.getExporter() != null && customer.getExporter().getId() > 0) {
					ExporterRegistration ex = utilService.findExportRegById(Long.valueOf(customer.getExporter().getId()));
					tempCustomer.setExporter(ex);
				}
				utilService.editCustomer(tempCustomer);
			}
			request.setAttribute(HEADING, getText(LIST));
			return REDIRECT;
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
		String result = null;
		if (this.getId() != null && !(this.getId().equals(EMPTY))) {
			customer = utilService.findCustomer(Long.valueOf(getId()));

			if (!ObjectUtil.isEmpty(customer)) {
				List<Shipment> shipcustomer = utilService.listOfShipmentByCustomerId(Long.valueOf(id));
				if (!ObjectUtil.isListEmpty(shipcustomer)) {
					addActionError(getText("msg.buyercont"));

				} else {
					customer.setStatus(1);
					utilService.update(customer);
					addActionError(getText("msg.deleted"));
					result = REDIRECT;
				}
			}
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	public void populateDelete() throws Exception {
		getJsonObject().clear();
		if (id != null) {
			customer = utilService.findCustomer(Long.valueOf(getId()));
			List<Shipment> shipcustomer = utilService.listOfShipmentByCustomerId(Long.valueOf(id));

			if (!ObjectUtil.isEmpty(customer)) {

				if (!ObjectUtil.isListEmpty(shipcustomer)) {
					getJsonObject().put("msg", getText("msg.buyercont"));
					getJsonObject().put("title", getText("title.error"));
				} else {
					customer.setStatus(1);
					utilService.update(customer);
					getJsonObject().put("msg", getText("msg.deleted"));
					getJsonObject().put("title", getText("title.success"));
				}
			}

			sendAjaxResponse(getJsonObject());
		}

	}

	/**
	 * To json.
	 * 
	 * @param record
	 *            the record
	 * @return the JSON object
	 * @see com.sourcetrace.esesw.view.SwitchAction#toJSON(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(Object record) {
		Customer customer = (Customer) record;
		JSONObject jsonObject = new JSONObject();
		JSONObject objDt = new JSONObject();
		JSONArray rows = new JSONArray();
		objDt.put("customerId",
				"<font color=\"#0000FF\" style=\"cursor:pointer;\">" + customer.getCustomerId() + "</font>");
		objDt.put("customerName", customer.getCustomerName());
		objDt.put("personName", customer.getPersonName());
		objDt.put("mobileNo", customer.getMobileNo());
		objDt.put("emailId", customer.getEmailId());
		objDt.put("edit",
				"<a href='#' onclick='ediFunction(\"" + customer.getId()
						+ "\")' class='fa fa-edit' title='Edit' ></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='deletProd(\""
						+ customer.getId() + "\")' class='fa fa-trash' title='Delete' ></a>");
		jsonObject.put("id", customer.getId());
		jsonObject.put("cell", objDt);
		return jsonObject;
	}

	public void populateValidate() {

		Map<String, String> errorCodes = new LinkedHashMap<String, String>();
		if (customer.getCustomerName() == null || StringUtil.isEmpty(customer.getCustomerName())) {
			errorCodes.put("empty.customerName", "empty.customerName");
		} else {
			Customer customerName = utilService.findCustomerByName(customer.getCustomerName());
			if (customerName != null && (customer.getId() == null
					|| customerName != null && !customer.getId().equals(customerName.getId()))) {
				errorCodes.put("unique.ProcurementProductName", "unique.ProcurementProductName");
			}
		}

		if (ObjectUtil.isEmpty(customer.getExporter()) || StringUtil.isEmpty(customer.getExporter().getId())) {
			errorCodes.put("empty.exporter", "empty.exporter");
		}

		if (getSelectedCountry() == null || StringUtil.isEmpty(getSelectedCountry()) || getSelectedCountry().equals("")) {
			errorCodes.put("empty.country", "empty.country");
		}
		if (getSelectedState() == null || StringUtil.isEmpty(getSelectedState()) || getSelectedState().equals("")) {
			errorCodes.put("empty.state", "empty.state");
		}
		if (getSelectedLocality() == null || StringUtil.isEmpty(getSelectedLocality()) || getSelectedLocality().equals("")) {
			errorCodes.put("empty.locality", "empty.locality");
		}
			if (getSelectedCity() == null || StringUtil.isEmpty(getSelectedCity()) || getSelectedCity().equals("")) {
				errorCodes.put("empty.ward", "empty.ward");
			}


		printErrorCodes(errorCodes);
	}

	@SuppressWarnings("unchecked")
	protected JSONObject getJSONObject(Object id, Object name) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("name", name);
		return jsonObject;
	}

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

	/**
	 * Gets the data.
	 * 
	 * @return the data
	 * @see com.sourcetrace.esesw.view.SwitchValidatorAction#getData()
	 */
	public Object getData() {
		if (ObjectUtil.isEmpty(customer)) {
			return null;
		} else {
			/*Country country = new Country();
			country.setCode(this.selectedCountry);

			State state = new State();
			state.setCode(this.selectedState);
			state.setCountry(country);

			Locality locality = new Locality();
			locality.setCode(this.selectedDistrict);
			locality.setState(state);*/

			/*
			 * Municipality city = new Municipality();
			 * city.setCode(this.selectedCity); city.setLocality(locality);
			 */

			return customer;
		}
	}

	/**
	 * @see com.sourcetrace.esesw.view.SwitchAction#data()
	 */
	@SuppressWarnings("unchecked")
	public String data() throws Exception {
		return sendDataTableJSONResponse(buildFilterDataMap());
	}

	/**
	 * Gets the client service.
	 * 
	 * @return the client service
	 */

	/**
	 * Sets the client service.
	 * 
	 * @param clientService
	 *            the new client service
	 */

	/**
	 * Gets the id generator.
	 * 
	 * @return the id generator
	 */
	public IUniqueIDGenerator getIdGenerator() {

		return idGenerator;
	}

	/**
	 * Sets the id generator.
	 * 
	 * @param idGenerator
	 *            the new id generator
	 */
	public void setIdGenerator(IUniqueIDGenerator idGenerator) {

		this.idGenerator = idGenerator;
	}

	/**
	 * Sets the filter.
	 * 
	 * @param filter
	 *            the new filter
	 */
	public void setFilter(Customer filter) {

		this.filter = filter;
	}

	/**
	 * Gets the customer.
	 * 
	 * @return the customer
	 */
	public Customer getCustomer() {

		return customer;
	}

	/**
	 * Sets the customer.
	 * 
	 * @param customer
	 *            the new customer
	 */
	public void setCustomer(Customer customer) {

		this.customer = customer;
	}

	/**
	 * Gets the filter.
	 * 
	 * @return the filter
	 */
	public Customer getFilter() {

		return filter;
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
	 * Gets the holding type list.
	 * 
	 * @return the holding type list
	 */
	public Map<String, String> getHoldingTypeList() {

		return holdingTypeList;
	}

	/**
	 * Sets the holding type list.
	 * 
	 * @param holdingTypeList
	 *            the holding type list
	 */
	public void setHoldingTypeList(Map<String, String> holdingTypeList) {

		this.holdingTypeList = holdingTypeList;
	}

	/**
	 * Gets the selected holding type.
	 * 
	 * @return the selected holding type
	 */
	public String getSelectedHoldingType() {

		return selectedHoldingType;
	}

	/**
	 * Sets the selected holding type.
	 * 
	 * @param selectedHoldingType
	 *            the new selected holding type
	 */
	public void setSelectedHoldingType(String selectedHoldingType) {

		this.selectedHoldingType = selectedHoldingType;
	}

	public String getTabIndex() {

		return URLDecoder.decode(tabIndex);
	}

	public void setTabIndex(String tabIndex) {

		this.tabIndex = tabIndex;
	}

	public Map<String, String> getListOfCustomerTypes() {
		String customerTypes = getText("listOfCustomerTypes");
		Map<String, String> customersMap = new LinkedHashMap<String, String>();
		String[] customers = customerTypes.split(",");
		Arrays.sort(customers);
		int i = 0;
		for (String customer : customers) {
			customersMap.put(String.valueOf(i++), customer);
		}
		return customersMap;
	}

	public void setListOfCustomerTypes(Map<String, String> listOfCustomerTypes) {
		this.listOfCustomerTypes = listOfCustomerTypes;
	}

	public Map<String, String> getListOfCustomerSegment() {
		String customerSegments = getText("customerSegments");
		Map<String, String> customerSegmentMap = new LinkedHashMap<String, String>();
		String[] customerSegment = customerSegments.split(",");
		Arrays.sort(customerSegment);
		int i = 0;
		for (String customer : customerSegment) {
			customerSegmentMap.put(String.valueOf(i++), customer);
		}
		return customerSegmentMap;
	}

	public void setListOfCustomerSegment(Map<String, String> listOfCustomerSegment) {
		this.listOfCustomerSegment = listOfCustomerSegment;
	}

	// public Map<String,String> getLocalities() {
	// Map<String, String> locality=new LinkedHashMap<String, String>();
	// if (!StringUtil.isEmpty(selectedState)) {
	// localities = utilService.listLocalities(selectedState);
	// for(Locality loc:localities){
	// locality.put(loc.getCode(), loc.getCode()+" - "+loc.getName());
	// }
	// }
	// return locality;
	// }

/*	public void setLocalities(List<Locality> localities) {
		this.localities = localities;
	}*/

	// public Map<String,String> getStates() {
	// Map<String, String> state=new LinkedHashMap<String, String>();
	// if (!StringUtil.isEmpty(selectedCountry)) {
	// states = utilService.listStatesByCode(selectedCountry);
	// for(State s:states){
	// state.put(s.getCode(), s.getCode()+" - "+s.getName());
	// }
	// }
	// return state;
	// }

/*	public void setStates(List<State> states) {
		this.states = states;
	}

	public String getSelectedCountry() {
		return selectedCountry;
	}

	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}

	public String getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(String selectedState) {
		this.selectedState = selectedState;
	}*/

/*	public Map<String, String> getCountries() {
		Map<String, String> countryMap = new LinkedHashMap<String, String>();
		List<Country> returnValue = utilService.listCountries();
		for (Country c : returnValue) {
			countryMap.put(c.getCode(), c.getCode() + " - " + c.getName());
		}
		return countryMap;
	}*/

	/*public Municipality getMunicipality() {
		return municipality;
	}

	public void setMunicipality(Municipality municipality) {
		this.municipality = municipality;
	}*/

/*	public Map<String, String> getMunicipalities() {
		Map<String, String> city = new LinkedHashMap<String, String>();
		if (!ObjectUtil.isEmpty(customer) && !ObjectUtil.isEmpty(customer.getCity())
				&& !StringUtil.isEmpty(customer.getCity().getName())) {
			List<Municipality> cities = utilService.listMunicipalitiesByCode(customer.getCity().getName());
			for (Municipality muncipality : cities) {
				city.put(muncipality.getCode(), muncipality.getCode() + " - " + muncipality.getName());
			}
		}
		return city;
	}*/

	/*public void setMunicipalities(List<Municipality> municipalities) {
		this.municipalities = municipalities;
	}

	public String getSelectedDistrict() {
		return selectedDistrict;
	}

	public void setSelectedDistrict(String selectedDistrict) {
		this.selectedDistrict = selectedDistrict;
	}*/

	// @SuppressWarnings("unchecked")
	// public void populateChildBranch() {
	//
	// JSONArray branchArr = new JSONArray();
	//
	// List<BranchMaster> branchMaster = new ArrayList<>();
	// if (!StringUtil.isEmpty(selectedBranch)) {
	// branchMaster = utilService.listChildBranchIds(selectedBranch);
	//
	// branchMaster.stream().filter(branch ->
	// !ObjectUtil.isEmpty(branch)).forEach(branch -> {
	// branchArr.add(getJSONObject(branch.getBranchId(), branch.getName()));
	// });
	// } else {
	// List<Object[]> branches = utilService.findChildBranches();
	// branches.forEach(obj -> {
	// branchArr.add(getJSONObject(obj[0].toString(), obj[1].toString()));
	// });
	// }
	//
	// sendAjaxResponse(branchArr);
	// }

	/*public String getSelectedCountryCode() {
		Country country = utilService.findCountryByName(selectedCountry);
		String countryCode = country.getCode();
		return countryCode;
	}

	public void setSelectedCountryCode(String selectedCountryCode) {
		this.selectedCountryCode = selectedCountryCode;
	}

	public String getSelectedStateCode() {
		State state = utilService.findStateByName(selectedState);
		String stateCode = state.getCode();
		return stateCode;
	}

	public void setSelectedStateCode(String selectedStateCode) {
		this.selectedStateCode = selectedStateCode;
	}*/

/*	public String getSelectedBranch() {
		return selectedBranch;
	}

	public void setSelectedBranch(String selectedBranch) {
		this.selectedBranch = selectedBranch;
	}*/

	public IUtilService getUtilService() {
		return utilService;
	}

	public void setUtilService(IUtilService utilService) {
		this.utilService = utilService;
	}

	public IFarmerService getFarmerService() {
		return farmerService;
	}

	public void setFarmerService(IFarmerService farmerService) {
		this.farmerService = farmerService;
	}

	public ICryptoUtil getCryptoUtil() {
		return cryptoUtil;
	}

	public void setCryptoUtil(ICryptoUtil cryptoUtil) {
		this.cryptoUtil = cryptoUtil;
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

	public Map<String, String> getCountries() {

		Map<String, String> countryMap = new LinkedHashMap<String, String>();
		List<Country> countryList = utilService.listCountries();
		for (Country obj : countryList) {
			countryMap.put(obj.getName(), obj.getCode() + "-" + obj.getName());
		}
		return countryMap;
	}// thisis loading country

}
