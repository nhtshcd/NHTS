package com.sourcetrace.eses.action;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.entity.ESESystem;
import com.opensymphony.xwork2.ActionContext;
import com.sourcetrace.eses.BreadCrumb;
import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.entity.FarmCatalogueMaster;
import com.sourcetrace.eses.entity.ProcurementDetail;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.service.UtilService;
import com.sourcetrace.eses.util.CurrencyUtil;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.entity.Village;
import com.sourcetrace.eses.action.SwitchValidatorAction;

public class CooperativeAction extends SwitchValidatorAction implements SessionAware {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CooperativeAction.class);
	public static final String SELECT_MULTI = "-1";
	public static final String OPTION_CONSTANT = "<option value=\"-1\">--Select--</option>";
	public static final String OPTION_KEY = "<!--optionKey--!>";
	public static final String OPTION_VALUE = "<rep!--optionValue--!>";
	public static final String PATTERN_OPTION = "<option value='<!--optionKey--!>'><!--optionValue--!></option>";

	private String id;
	private String selectedBlock;

	private List<Municipality> blockes = new ArrayList<Municipality>();
	private List<String> availableVillages = new ArrayList<String>();
	private List<String> selectedVillages = new ArrayList<String>();
	private Map<String, Object> selectedVillageMap = new LinkedHashMap<String, Object>();
	private Map<String, Object> sessionScopMap = new HashMap<String, Object>();
	private Map<Integer, String> commodityList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> ownershipList = new LinkedHashMap<Integer, String>();
	private List<Object[]> availableVillageObjs = new ArrayList<Object[]>();

	// private ILocationService locationService;
	// private IAgentService agentService;
	// private ICatalogueService catalogueService;
	private IFarmerService farmerService;
	@Autowired
	private IUtilService utilService;
	// private IPreferencesService preferncesService;
	private IUniqueIDGenerator idGenerator;
	private String commodityName;
	private String ownershipName;
	private String type;
	private String selectedCommodity;
	private String storageArrayToString;
	private String enableStorage;
	Map<Integer, String> warehouseType = new LinkedHashMap<Integer, String>();
	private String selectedColdStorageName;
	private Double maxBayWeight;

	/**
	 * Gets the data.
	 * 
	 * @return the data
	 * @see com.sourcetrace.esesw.view.SwitchValidatorAction#getData()
	 */
	public Object getData() {
		return null;
	}

	public String list() throws Exception {
		setType(request.getParameter("type"));
		if (getType() == null) {
			setType(getType());
		}
		if (getCurrentPage() != null) {
			setCurrentPage(getCurrentPage());
		}
		request.setAttribute(HEADING, getText(LIST));
		return LIST;
	}

	/**
	 * @see com.sourcetrace.esesw.view.SwitchAction#toJSON(java.lang.Object)
	 */

	/**
	 * Creates the.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public String create() throws Exception {
		setType(getType());
		setEnableStorage(utilService.findPrefernceByName(ESESystem.ENABLE_STORAGE));
		return null;

		/*
		 * if (warehouse == null) { command = "create";
		 * sessionScopMap.put("selVillageMap", null);
		 * request.setAttribute(HEADING, getText("cooperativecreate"));
		 * setEnableStorage(utilService.findPrefernceByName(ESESystem.
		 * ENABLE_BUYER));
		 * setEnableStorage(utilService.findPrefernceByName(ESESystem.
		 * ENABLE_STORAGE)); return INPUT; } else {
		 * 
		 * 
		 * warehouse.setCode(idGenerator.getWarehouseIdSeq());
		 * warehouse.setBranchId(getBranchId());
		 * warehouse.setCreatedUsername(getUsername());
		 * warehouse.setUpdatedUsername(getUsername());
		 * warehouse.setCreatedDate(new Date()); warehouse.setUpdatedDate(new
		 * Date());
		 * 
		 * warehouse.setTypez(Warehouse.COOPERATIVE);
		 * 
		 * 
		 * 
		 * utilService.addWarehouse(warehouse);
		 * sessionScopMap.put("selVillageMap", null); return list(); return
		 * REDIRECT; }
		 */
	}

	/**
	 * Update.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public String update() throws Exception {
		setEnableStorage(utilService.findPrefernceByName(ESESystem.ENABLE_STORAGE));
		setType(getType());
		/*
		 * if (id != null && !id.equals("")) { warehouse =
		 * utilService.findWarehouseById(Long.valueOf(id)); if (warehouse ==
		 * null) { addActionError(NO_RECORD); return list(); }
		 * sessionScopMap.put("selVillageMap", null); selectedVillageMap = new
		 * HashMap<String, Object>();
		 * 
		 * if(!StringUtil.isEmpty(getEnableStorage()) &&
		 * getEnableStorage().equalsIgnoreCase("1")){ warehouseStorageMapList =
		 * new ArrayList<>(); List<WarehouseStorageMap> calfListTemp =
		 * farmerService.listOfwarehouseStorageMap(warehouse.getId());
		 * calfListTemp.stream().filter(warehouseStorageMap ->
		 * !ObjectUtil.isEmpty(warehouseStorageMap)).forEach(warehouseStorageMap
		 * -> {
		 * 
		 * warehouseStorageMap.setColdStorageName(String.valueOf(
		 * warehouseStorageMap.getColdStorageName()));
		 * warehouseStorageMap.setMaxBayHold(warehouseStorageMap.getMaxBayHold()
		 * ); warehouseStorageMapList.add(warehouseStorageMap);
		 * 
		 * }); } setCurrentPage(getCurrentPage());
		 * 
		 * id = null; command = UPDATE; request.setAttribute(HEADING,
		 * getText("cooperativeupdate")); }
		 *//*
			 * else { setType(getType()); if (warehouse != null) { Warehouse
			 * temp;
			 * 
			 * temp = utilService.findWarehouseById(warehouse.getId()); if (temp
			 * == null) { addActionError(NO_RECORD); return list(); // return
			 * REDIRECT; } setCurrentPage(getCurrentPage());
			 * temp.setName(warehouse.getName());
			 * 
			 * temp.setLocation(warehouse.getLocation());
			 * temp.setAddress(warehouse.getAddress());
			 * temp.setPhoneNo(warehouse.getPhoneNo());
			 * temp.setUpdatedUsername(getUsername());
			 * temp.setUpdatedDate(warehouse.getUpdatedDate());
			 * temp.setWarehouseIncharge(warehouse.getWarehouseIncharge());
			 * 
			 * 
			 * Map map = (Map) sessionScopMap.get("selVillageMap");
			 * 
			 * utilService.editWarehouse(temp);
			 * sessionScopMap.put("selVillageMap", null); }
			 * request.setAttribute(HEADING, getText("cooperativelist")); return
			 * LIST; }
			 */
		return super.execute();
	}

	/**
	 * Detail.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String detail() throws Exception {
		setType(getType());
		setEnableStorage(utilService.findPrefernceByName(ESESystem.ENABLE_STORAGE));
		String view = "";
		/*
		 * if (id != null && !id.equals("")) {
		 * 
		 * warehouse = utilService.findWarehouseById(Long.valueOf(id));
		 * 
		 * 
		 * if (warehouse == null) { addActionError(NO_RECORD); return list(); //
		 * return REDIRECT; }
		 * 
		 * if(!StringUtil.isEmpty(getEnableStorage()) &&
		 * getEnableStorage().equalsIgnoreCase("1")){ warehouseStorageMapList =
		 * new ArrayList<>(); List<WarehouseStorageMap> calfListTemp =
		 * farmerService.listOfwarehouseStorageMap(warehouse.getId());
		 * calfListTemp.stream().filter(warehouseStorageMap ->
		 * !ObjectUtil.isEmpty(warehouseStorageMap)).forEach(warehouseStorageMap
		 * -> {
		 * 
		 * warehouseStorageMap.setColdStorageName(String.valueOf(
		 * getCatlogueValueByCode(warehouseStorageMap.getColdStorageName()).
		 * getName()));
		 * 
		 * warehouseStorageMap.setMaxBayHold(warehouseStorageMap.getMaxBayHold()
		 * ); warehouseStorageMapList.add(warehouseStorageMap);
		 * 
		 * }); }
		 * 
		 * setCurrentPage(getCurrentPage()); command = UPDATE; view = DETAIL;
		 * request.setAttribute(HEADING, getText("cooperativedetail")); }
		 */ /*
			 * else { request.setAttribute(HEADING, getText("cooperativelist"));
			 * return LIST; }
			 */
		return view;
	}

	/**
	 * Delete.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	/*
	 * public String delete() throws Exception { setType(getType()); if
	 * (this.getId() != null && !(this.getId().equals(EMPTY))) { warehouse =
	 * utilService.findWarehouseById(Long.valueOf(id)); if (warehouse == null) {
	 * addActionError(NO_RECORD); return list(); }
	 * setCurrentPage(getCurrentPage());
	 * 
	 * } request.setAttribute(HEADING, getText("cooperativelist")); return LIST;
	 * 
	 * }
	 */

	/**
	 * Sets the cooperative in session.
	 * 
	 * @param warehouse
	 *            the new cooperative in session
	 */

	/**
	 * Grid check.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String gridCheck() throws Exception {

		List<String> mappedVillages = new ArrayList<String>();
		if (!ObjectUtil.isEmpty(getAvailableVillages()) && !StringUtil.isEmpty(getId())) {
			for (String avlVillage : getAvailableVillages()) {
				/*
				 * String villageName = locationService
				 * .findCooperativeVillageMappedWtihS
				 * amithiVillage(Long.parseLong(getId()), avlVillage); if
				 * (!StringUtil.isEmpty(villageName)) {
				 * mappedVillages.add(villageName); }
				 */
			}
		}
		if (!ObjectUtil.isListEmpty(mappedVillages)) {
			sendResponse(getText("delete.samithi.village.warn"));
			return null;
		}
		return null;
	}

	/**
	 * Grid process.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public String gridProcess() throws Exception {

		Map selVillageMap = (Map) sessionScopMap.get("selVillageMap");
		if (!ObjectUtil.isEmpty(selVillageMap)) {
			selectedVillageMap.putAll(selVillageMap);
		}
		if (!ObjectUtil.isEmpty(getAvailableVillages())) {
			for (String avlVillage : getAvailableVillages()) {
				if (selectedVillageMap.containsKey(avlVillage)) {
					selectedVillageMap.remove(avlVillage);
				}
			}
		}
		if (!ObjectUtil.isEmpty(getSelectedVillages())) {
			String blockName = null;
			if (!StringUtil.isEmpty(selectedBlock))
				blockName = utilService.findCityNameByCode(selectedBlock);
			for (String selVillage : getSelectedVillages()) {
				selectedVillageMap.put(selVillage, utilService.findVillageNameByCode(selVillage) + " / " + blockName);
			}
		}
		sessionScopMap.put("selVillageMap", selectedVillageMap);
		sendResponse(selectedVillageMap);
		return null;
	}

	/**
	 * Gets the blockes.
	 * 
	 * @return the blockes
	 */
	@SuppressWarnings("unchecked")
	public List<Municipality> getBlockes() {

		blockes = utilService.listCity();
		return blockes;
	}

	/**
	 * Populate villages.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String populateVillages() throws Exception {

		// Load Villages details
		if (!ObjectUtil.isEmpty(selectedBlock)) {
			availableVillageObjs = utilService.listOfVillageCodeNameByCityCode(selectedBlock);
			sendResponse(buildOptionByObjArray(availableVillageObjs));
		}
		return null;
	}

	/**
	 * Builds the option by obj array.
	 * 
	 * @param villageList
	 *            the village list
	 * @return the string
	 */
	@SuppressWarnings("unchecked")
	public String buildOptionByObjArray(List<Object[]> villageList) {

		Map selVillageMap = (Map) sessionScopMap.get("selVillageMap");
		StringBuffer villagePickList = new StringBuffer();
		StringBuffer avlVillageOptions = new StringBuffer();
		StringBuffer selVillageOptions = new StringBuffer();
		String listString = null;
		for (Object[] objArray : villageList) {
			listString = null;
			if (ObjectUtil.isEmpty(selVillageMap) || !selVillageMap.containsKey((String) objArray[0])) {
				listString = PATTERN_OPTION.replaceAll(OPTION_KEY, (String) objArray[0]).replaceAll(OPTION_VALUE,
						(String) objArray[1]);
				avlVillageOptions.append(listString);
			} else {
				listString = PATTERN_OPTION.replaceAll(OPTION_KEY, (String) objArray[0]).replaceAll(OPTION_VALUE,
						(String) objArray[1]);
				selVillageOptions.append(listString);

			}
		}
		villagePickList.append(avlVillageOptions.toString());
		villagePickList.append("~");
		villagePickList.append(selVillageOptions.toString());

		return villagePickList.toString();
	}

	public void prepare() {
		String actionClassName = ActionContext.getContext().getActionInvocation().getAction().getClass()
				.getSimpleName();
		String content = getLocaleProperty(actionClassName + "." + BreadCrumb.BREADCRUMB);
		if (StringUtil.isEmpty(content) || (content.equalsIgnoreCase(actionClassName + "." + BreadCrumb.BREADCRUMB))) {
			content = getText(BreadCrumb.BREADCRUMB, "");
		}

		if (!StringUtil.isEmpty(content)) {
			request.setAttribute(BreadCrumb.BREADCRUMB, BreadCrumb.getBreadCrumb(content));
		}
	}

	@Override
	public String getCurrentMenu() {
		String type = request.getParameter("type");

		return getText("menu.select");

	}

	/**
	 * Gets the warehouse.
	 * 
	 * @return the warehouse
	 */

	/**
	 * Sets the warehouse.
	 * 
	 * @param warehouse
	 *            the new warehouse
	 */

	/**
	 * Sets the location service.
	 * 
	 * @param locationService
	 *            the new location service
	 */
	/*
	 * public void setLocationService(ILocationService locationService) {
	 * 
	 * this.locationService = locationService; }
	 */
	/**
	 * Gets the location service.
	 * 
	 * @return the location service
	 */

	/*
	 * public ILocationService getLocationService() {
	 * 
	 * return locationService; }
	 */
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
	 * Gets the selected block.
	 * 
	 * @return the selected block
	 */
	public String getSelectedBlock() {

		return selectedBlock;
	}

	/**
	 * Sets the selected block.
	 * 
	 * @param selectedBlock
	 *            the new selected block
	 */
	public void setSelectedBlock(String selectedBlock) {

		this.selectedBlock = selectedBlock;
	}

	/**
	 * Gets the available villages.
	 * 
	 * @return the available villages
	 */
	public List<String> getAvailableVillages() {

		return availableVillages;
	}

	/**
	 * Sets the available villages.
	 * 
	 * @param availableVillages
	 *            the new available villages
	 */
	public void setAvailableVillages(List<String> availableVillages) {

		this.availableVillages = availableVillages;
	}

	/**
	 * Gets the selected villages.
	 * 
	 * @return the selected villages
	 */
	public List<String> getSelectedVillages() {

		return selectedVillages;
	}

	/**
	 * Sets the selected villages.
	 * 
	 * @param selectedVillages
	 *            the new selected villages
	 */
	public void setSelectedVillages(List<String> selectedVillages) {

		this.selectedVillages = selectedVillages;
	}

	/**
	 * Gets the session scop map.
	 * 
	 * @return the session scop map
	 */
	public Map<String, Object> getSessionScopMap() {

		return sessionScopMap;
	}

	/**
	 * Sets the session scop map.
	 * 
	 * @param sessionScopMap
	 *            the session scop map
	 */
	public void setSessionScopMap(Map<String, Object> sessionScopMap) {

		this.sessionScopMap = sessionScopMap;
	}

	/**
	 * Gets the selected village map.
	 * 
	 * @return the selected village map
	 */
	public Map<String, Object> getSelectedVillageMap() {

		return selectedVillageMap;
	}

	/**
	 * Sets the selected village map.
	 * 
	 * @param selectedVillageMap
	 *            the selected village map
	 */
	public void setSelectedVillageMap(Map<String, Object> selectedVillageMap) {

		this.selectedVillageMap = selectedVillageMap;
	}

	/**
	 * Gets the agent service.
	 * 
	 * @return the agent service
	 */
	/*
	 * public IAgentService getAgentService() {
	 * 
	 * return agentService; }
	 */
	/**
	 * Sets the agent service.
	 * 
	 * @param agentService
	 *            the new agent service
	 */
	/*
	 * public void setAgentService(IAgentService agentService) {
	 * 
	 * this.agentService = agentService; }
	 */

	/**
	 * Sets the session.
	 * 
	 * @param session
	 *            the session
	 * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	@Override
	public void setSession(Map<String, Object> session) {

		this.sessionScopMap = session;

	}

	/*
	 * public Map<String, String> getCommodityList() {
	 * 
	 * Map<String, String> commodityMap = new LinkedHashMap<String, String>();
	 * 
	 * List<FarmCatalogue> educationList =
	 * farmerService.listCatelogueType(getText("commodityType")); for
	 * (FarmCatalogue obj : educationList) { commodityMap.put(obj.getCode(),
	 * obj.getName()); } return commodityMap; }
	 */

	public Map<String, String> getCommodityList() {
		Map<String, String> commodityList = new LinkedHashMap<>();
		FarmCatalogueMaster farmCatalougeMaster = utilService
				.findFarmCatalogueMasterByName(getText("Storage Commodity"));
		if (!ObjectUtil.isEmpty(farmCatalougeMaster)) {
			Double d = new Double(farmCatalougeMaster.getId());
			List<FarmCatalogue> farmCatalougeList = utilService.findFarmCatalougeByType(d.intValue());
			commodityList = farmCatalougeList.stream()
					.collect(Collectors.toMap(obj -> (String.valueOf(obj.getCode())), FarmCatalogue::getName));
		}
		return commodityList;
	}

	/*
	 * public Map<String, String> getOwnershipList() {
	 * 
	 * Map<String, String> commodityMap = new LinkedHashMap<String, String>();
	 * 
	 * List<FarmCatalogue> educationList =
	 * farmerService.listCatelogueType(getText("ownershipType")); for
	 * (FarmCatalogue obj : educationList) { commodityMap.put(obj.getCode(),
	 * obj.getName()); } return commodityMap; }
	 */

	public Map<String, String> getOwnershipList() {
		Map<String, String> ownwershipList = new LinkedHashMap<>();
		FarmCatalogueMaster farmCatalougeMaster = utilService
				.findFarmCatalogueMasterByName(getText("Warehouse Ownership"));
		if (!ObjectUtil.isEmpty(farmCatalougeMaster)) {
			Double d = new Double(farmCatalougeMaster.getId());
			List<FarmCatalogue> farmCatalougeList = utilService.findFarmCatalougeByType(d.intValue());
			ownwershipList = farmCatalougeList.stream()
					.collect(Collectors.toMap(obj -> (String.valueOf(obj.getCode())), FarmCatalogue::getName));
		}
		return ownwershipList;
	}

	public void setIdGenerator(IUniqueIDGenerator idGenerator) {

		this.idGenerator = idGenerator;
	}

	public void populateCommodity() {

		if (!commodityName.equalsIgnoreCase("null") && (!StringUtil.isEmpty(commodityName))) {

			FarmCatalogueMaster farmCatalougeMaster = utilService
					.findFarmCatalogueMasterByName(getText("Storage Commodity"));
			if (!ObjectUtil.isEmpty(farmCatalougeMaster)) {
				int catalogueTypez = farmCatalougeMaster.getTypez();

				FarmCatalogue catalogue = utilService.findByNameAndType(commodityName, catalogueTypez);
				if (catalogue == null) {
					FarmCatalogue farmCatalogue = new FarmCatalogue();
					farmCatalogue.setCode(idGenerator.getCatalogueIdSeq());
					farmCatalogue.setRevisionNo(DateUtil.getRevisionNumber());
					farmCatalogue.setBranchId(getBranchId());
					farmCatalogue.setName(commodityName);
					farmCatalogue.setTypez(catalogueTypez);
					farmCatalogue.setStatus(FarmCatalogue.ACTIVE);
					utilService.addCatalogue(farmCatalogue);
					JSONArray stateArr = new JSONArray();
					List<FarmCatalogue> educationList = farmerService.listCatelogueType(String.valueOf(catalogueTypez));
					if (!ObjectUtil.isEmpty(educationList)) {

						for (FarmCatalogue obj : educationList) {
							stateArr.add(getJSONObject(obj.getCode(), obj.getName()));
						}
					}
					sendAjaxResponse(stateArr);
				} else {
					String result = "0";
					sendAjaxResponse(result);
				}
			}
		} else {
			String result = "0";
			sendAjaxResponse(result);
		}

	}

	public void populateOwnership() {

		if (!ownershipName.equalsIgnoreCase("null") && (!StringUtil.isEmpty(ownershipName))) {

			FarmCatalogueMaster farmCatalougeMaster = utilService
					.findFarmCatalogueMasterByName(getText("Warehouse Ownership"));
			if (!ObjectUtil.isEmpty(farmCatalougeMaster)) {
				int catalogueTypez = farmCatalougeMaster.getTypez();

				FarmCatalogue catalogue = utilService.findByNameAndType(ownershipName, catalogueTypez);
				if (catalogue == null) {
					FarmCatalogue farmCatalogue = new FarmCatalogue();
					farmCatalogue.setCode(idGenerator.getCatalogueIdSeq());
					farmCatalogue.setRevisionNo(DateUtil.getRevisionNumber());
					farmCatalogue.setBranchId(getBranchId());
					farmCatalogue.setName(ownershipName);
					farmCatalogue.setTypez(catalogueTypez);
					farmCatalogue.setStatus(FarmCatalogue.ACTIVE);
					utilService.addCatalogue(farmCatalogue);
					JSONArray stateArr = new JSONArray();
					List<FarmCatalogue> educationList = farmerService.listCatelogueType(String.valueOf(catalogueTypez));
					if (!ObjectUtil.isEmpty(educationList)) {

						for (FarmCatalogue obj : educationList) {
							stateArr.add(getJSONObject(obj.getCode(), obj.getName()));
						}
					}
					sendAjaxResponse(stateArr);
				} else {
					String result = "0";
					sendAjaxResponse(result);
				}
			}
		} else {
			String result = "0";
			sendAjaxResponse(result);
		}

	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	@SuppressWarnings("unchecked")
	protected JSONObject getJSONObject(Object id, Object name) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("name", name);
		return jsonObject;
	}

	public void setCommodityList(Map<Integer, String> commodityList) {
		this.commodityList = commodityList;
	}

	public void setOwnershipList(Map<Integer, String> ownershipList) {
		this.ownershipList = ownershipList;
	}

	/*
	 * public ICatalogueService getCatalogueService() { return catalogueService;
	 * }
	 * 
	 * public void setCatalogueService(ICatalogueService catalogueService) {
	 * this.catalogueService = catalogueService; }
	 */

	public IFarmerService getFarmerService() {
		return farmerService;
	}

	public void setFarmerService(IFarmerService farmerService) {
		this.farmerService = farmerService;
	}

	public String getOwnershipName() {
		return ownershipName;
	}

	public void setOwnershipName(String ownershipName) {
		this.ownershipName = ownershipName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSelectedCommodity() {
		return selectedCommodity;
	}

	public void setSelectedCommodity(String selectedCommodity) {
		this.selectedCommodity = selectedCommodity;
	}

	public Map<Integer, String> getWarehouseType() {
		return warehouseType;
	}

	public void setWarehouseType(Map<Integer, String> warehouseType) {
		this.warehouseType = warehouseType;
	}

	public Map<String, String> getColdStorageNameList() {

		Map<String, String> farmCatalougeList = new LinkedHashMap<>();
		farmCatalougeList = getFarmCatalougeMap(Integer.valueOf(getLocaleProperty("coldStorageCatalog").trim()));
		return farmCatalougeList;

	}

	public Map<String, String> getBlockNameList() {

		Map<String, String> farmCatalougeList = new LinkedHashMap<>();
		farmCatalougeList = getFarmCatalougeMap(Integer.valueOf(getLocaleProperty("blockNameCatalog").trim()));
		return farmCatalougeList;

	}

	public Map<String, String> getFloorNameList() {

		Map<String, String> farmCatalougeList = new LinkedHashMap<>();
		farmCatalougeList = getFarmCatalougeMap(Integer.valueOf(getLocaleProperty("floorNameCatalog").trim()));
		return farmCatalougeList;

	}

	public Map<String, String> getBayNumList() {

		Map<String, String> farmCatalougeList = new LinkedHashMap<>();
		farmCatalougeList = getFarmCatalougeMap(Integer.valueOf(getLocaleProperty("bayNumCatalog").trim()));
		return farmCatalougeList;

	}

	public String getStorageArrayToString() {
		return storageArrayToString;
	}

	public void setStorageArrayToString(String storageArrayToString) {
		this.storageArrayToString = storageArrayToString;
	}

	public String getEnableStorage() {
		return enableStorage;
	}

	public void setEnableStorage(String enableStorage) {
		this.enableStorage = enableStorage;
	}

	public String getSelectedColdStorageName() {
		return selectedColdStorageName;
	}

	public void setSelectedColdStorageName(String selectedColdStorageName) {
		this.selectedColdStorageName = selectedColdStorageName;
	}

	public Double getMaxBayWeight() {
		return maxBayWeight;
	}

	public void setMaxBayWeight(Double maxBayWeight) {
		this.maxBayWeight = maxBayWeight;
	}

	public IUtilService getUtilService() {
		return utilService;
	}

	public void setUtilService(IUtilService utilService) {
		this.utilService = utilService;
	}

}
