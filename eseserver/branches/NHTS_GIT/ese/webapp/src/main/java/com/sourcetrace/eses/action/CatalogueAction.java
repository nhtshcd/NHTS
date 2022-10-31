package com.sourcetrace.eses.action;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.Language;
import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.entity.FarmCatalogueMaster;
import com.sourcetrace.eses.entity.LanguagePreferences;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.action.SwitchValidatorAction;

@SuppressWarnings("serial")
public class CatalogueAction extends SwitchValidatorAction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CatalogueAction.class);
	@Autowired
	private IUtilService utilService;
	@Autowired
	private IUniqueIDGenerator idGenerator;
	private Map<Integer, String> typezList = new LinkedHashMap<Integer, String>();
	static final int type = 13;
	private Map<String, String> catTypez = new LinkedHashMap<String, String>();
	private FarmCatalogue farmCatalogue;
	private String id;
	private String catalogueName;
	private Map<String, String> statusMap = new LinkedHashMap<>();
	private String status;
	private String typez;
	private String dispName;
	// transient Variable
	private String cataLogueMasterName;
	private String statusDeafaultVal;


	public void setIdGenerator(IUniqueIDGenerator idGenerator) {

		this.idGenerator = idGenerator;
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
	 * @see com.sourcetrace.esesw.view.SwitchAction#data()
	 */
	public String data() throws Exception {

		Map<String, String> searchRecord = getDataTableJQGridRequestParam();// get the
																	// search
																	// parameter
																	// with
		// value

		FarmCatalogue filter = new FarmCatalogue();

		ESESystem preferences = utilService.findPrefernceById("1");
		String isFpoEnabled = "";
		if (!StringUtil.isEmpty(preferences)) {
			isFpoEnabled = (preferences.getPreferences().get(ESESystem.ENABLE_FPOFG));
			if (!(isFpoEnabled.equals("1"))) {
				// filter.setTypeValue(Integer.parseInt(getText("FPO/FG")));
			}
		}

		if (!StringUtil.isEmpty(searchRecord.get("code"))) {
			filter.setCode(searchRecord.get("code").trim());
		}

		if (!StringUtil.isEmpty(searchRecord.get("name"))) {
			filter.setName(searchRecord.get("name").trim());
		}
		
		if (!StringUtil.isEmpty(searchRecord.get("typez"))) {
			filter.setTypez((Integer.valueOf(searchRecord.get("typez"))));
		} else {
			filter.setTypez(0);
		}
		
		if (!StringUtil.isEmpty(searchRecord.get("status"))) {
			filter.setStatus((searchRecord.get("status")));
		}
		
		if (!StringUtil.isEmpty(searchRecord.get("dispName"))) {
			filter.setDispName((searchRecord.get("dispName")));
		}
		
				//filter.setMasterStatus(0);
		
		filter.setIsReserved(0);

		Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
				getPage());

		return sendDataTableJSONResponse(data);

	}

	/**
	 * @see com.sourcetrace.esesw.view.SwitchAction#toJSON(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(Object obj) {

		FarmCatalogue catalogue = (FarmCatalogue) obj;
		JSONObject jsonObject = new JSONObject();
		//JSONArray rows = new JSONArray();
		JSONObject objDt = new JSONObject();
		JSONObject actionOnj = new JSONObject();
		objDt.put("code","<font color=\"#0000FF\" style=\"cursor:pointer;\">" + catalogue.getCode() + "</font>");
		FarmCatalogueMaster catalogueMaster = utilService
				.findFarmCatalogueMasterByCatalogueTypez(catalogue.getTypez());
		if (!ObjectUtil.isEmpty(catalogueMaster)) {
			objDt.put("typez",catalogueMaster.getName());
		} else {
			objDt.put("typez","");
		}
		//rows.add(catalogue.getName());
		
		String name = getLanguagePref(getLoggedInUserLanguage(), catalogue.getCode().trim().toString());
		if(!StringUtil.isEmpty(name) && name != null){
			objDt.put("name",name);
		}else{
			objDt.put("name",catalogue.getName());
		}
		
		if(!StringUtil.isEmpty(catalogue.getStatus())){
			objDt.put("status",catalogue.getStatus().equals("0")?getLocaleProperty("statuss0"):getLocaleProperty("statuss1"));
		}else{
			objDt.put("status",getLocaleProperty("statuss1"));
		}
		actionOnj.put("code", catalogue.getCode());
		actionOnj.put("name", catalogue.getName());
		actionOnj.put("typez", catalogue.getTypez());
		actionOnj.put("id", catalogue.getId());
		actionOnj.put("status", catalogue.getStatus());
		objDt.put("edit","<a href='#' onclick='ediFunction(\"" + StringEscapeUtils.escapeJavaScript(actionOnj.toString())
		+ "\")' class='fa fa-edit' title='Edit' ></a>");
		jsonObject.put("DT_RowId", catalogue.getId());
		jsonObject.put("cell", objDt);
		return jsonObject;
	}

	/**
	 * Creates the.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */

	public void populateCreate() throws Exception {
	
		if ((Integer.valueOf(typez) > 0) && (!StringUtil.isEmpty(catalogueName))) {

			FarmCatalogue eCatalogue = utilService.findCatalogueByNameAndType(catalogueName,
					Integer.valueOf(typez));
			if (eCatalogue != null) {
				getJsonObject().put("msg", getText("unique.CatalogueName"));
				getJsonObject().put("title", getText("title.error"));
				sendAjaxResponse(getJsonObject());
			} else {
				farmCatalogue=new FarmCatalogue();
				farmCatalogue.setCode(idGenerator.getCatalogueIdSeq());
				farmCatalogue.setRevisionNo(DateUtil.getRevisionNumber());
				farmCatalogue.setDispName(catalogueName);

				farmCatalogue.setStatus(status);
				farmCatalogue.setTypez(Integer.valueOf(typez));
				farmCatalogue.setName(catalogueName);
				farmCatalogue.setBranchId(getBranchId());
				farmCatalogue.setCreatedUser(getUsername());
				farmCatalogue.setCreatedDate(new Date());
				if (StringUtil.isEmpty(farmCatalogue.getDispName()) || farmCatalogue.getDispName() == null) {
					farmCatalogue.setDispName(farmCatalogue.getName());
				}

				

				utilService.addCatalogue(farmCatalogue);

				getJsonObject().put("msg", getText("msg.added"));
				getJsonObject().put("title", getText("title.success"));
				sendAjaxResponse(getJsonObject());
			}
		}
	}
	
	public void populateUpdate(){
		if (!StringUtil.isEmpty(getId())) {
			FarmCatalogue catalogue = utilService.findCatalogueById(Long.valueOf(getId()));
			if(!ObjectUtil.isEmpty(catalogue)){
				catalogue.setName(catalogueName);
				catalogue.setRevisionNo(DateUtil.getRevisionNumber());
				catalogue.setTypez(Integer.parseInt(typez));
				if(StringUtil.isEmpty(getDispName()) || getDispName()==null){
					catalogue.setDispName(catalogueName);
				}else{
					catalogue.setDispName(getDispName());
				}
				
					catalogue.setStatus(status);
					
					catalogue.setUpdatedUser(getUsername());
					catalogue.setUpdatedDate(new Date());
				
				utilService.editCatalogue(catalogue);
				
				getJsonObject().put("msg", getText("msg.updated"));
				getJsonObject().put("title", getText("title.success"));
				sendAjaxResponse(getJsonObject());
			}
		}
	}
	
	
	public void populateDelete(){
		
	}
	

	/**
	 * Update.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	/*public String update() throws Exception {

		if (id != null && !id.equals("")) {
			farmCatalogue = utilService.findCatalogueById(Long.valueOf(id));
			List<Language> languageMasters = utilService.findIsSurveyStatusLanguage();
			List<LanguagePreferences> languagePreferences = utilService.listLangPrefByCode(farmCatalogue.getCode());
			
	        
	        
			for(Language l : languageMasters){
			
				List<LanguagePreferences> lpList = languagePreferences.stream().filter(obj -> l.getCode().equals(obj.getLang())).collect(Collectors.toList());
				if(lpList==null || lpList.isEmpty()){
					LanguagePreferences preferences = new LanguagePreferences();
					preferences.setCode("");
					preferences.setName("");
					preferences.setLang(l.getCode());
					preferences.setLtype(LanguagePreferences.Type.CATALOGUE.ordinal());
					languagePreferences.add(preferences);
					
				}
			}
			Set<LanguagePreferences> languagePrefs = new HashSet<LanguagePreferences>();
	        for (LanguagePreferences x : languagePreferences)
	        	{
	        	languagePrefs.add(x);
	        	}
			
			farmCatalogue.setLanguagePref(languagePrefs);
			if (farmCatalogue == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			setCurrentPage(getCurrentPage());
			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getText("catalogueupdate"));
			setStatusDeafaultVal(farmCatalogue.getStatus());
		} else {
			if (farmCatalogue != null) {
				FarmCatalogue temp = utilService.findCatalogueById(farmCatalogue.getId());
				if (temp == null) {
					addActionError(NO_RECORD);
					return REDIRECT;
				}

				setCurrentPage(getCurrentPage());
				temp.setName(farmCatalogue.getName());
				temp.setTypez(farmCatalogue.getTypez());
				temp.setRevisionNo(DateUtil.getRevisionNumber());
				temp.setStatus(farmCatalogue.getStatus());
				
				if(farmCatalogue.getDispName()==null && StringUtil.isEmpty(farmCatalogue.getDispName())){
					temp.setDispName(farmCatalogue.getName());
				}else{
					temp.setDispName(farmCatalogue.getDispName());
				}
				temp.setLanguagePref(farmCatalogue.getLanguagePref());
				utilService.editCatalogue(temp);
			}
			request.setAttribute(HEADING, getText("cataloguelist"));
			return REDIRECT;
		}
		return super.execute();
	}*/

	/**
	 * Detail.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String detail() throws Exception {

		String view = LIST;
		request.setAttribute(HEADING, getText(LIST));
		if (id != null && !id.equals("")) {
			farmCatalogue = utilService.findCatalogueById(Long.valueOf(id));
			FarmCatalogueMaster catalogueMaster = utilService
					.findFarmCatalogueMasterByCatalogueTypez(farmCatalogue.getTypez());
			if (!ObjectUtil.isEmpty(catalogueMaster)) {
				setCataLogueMasterName(catalogueMaster.getName());
				setStatus(farmCatalogue.getStatus().equals("0")?getLocaleProperty("statuss0"):getLocaleProperty("statuss1"));
			}
			
			List<Language> languageMasters = utilService.findIsSurveyStatusLanguage();
			List<LanguagePreferences> languagePreferences = utilService.listLangPrefByCode(farmCatalogue.getCode());
			for(Language l : languageMasters){
			
				List<LanguagePreferences> lpList = languagePreferences.stream().filter(obj -> l.getCode().equals(obj.getLang())).collect(Collectors.toList());
				if(lpList==null || lpList.isEmpty()){
					LanguagePreferences preferences = new LanguagePreferences();
					preferences.setCode("");
					preferences.setName("");
					preferences.setLang(l.getCode());
					preferences.setLtype(LanguagePreferences.Type.CATALOGUE.ordinal());
					languagePreferences.add(preferences);
					
				}
			}
			
			Set<LanguagePreferences> languagePrefs = new HashSet<LanguagePreferences>();
	        for (LanguagePreferences x : languagePreferences)
	        	{
	        	languagePrefs.add(x);
	        	}
	        
			farmCatalogue.setLanguagePref(languagePrefs);
		
			if (farmCatalogue == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}

			command = UPDATE;
			view = DETAIL;
			request.setAttribute(HEADING, getText("cataloguedetail"));
		}
		return view;
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
			farmCatalogue = utilService.findCatalogueById(Long.valueOf(id));
			if (!ObjectUtil.isEmpty(farmCatalogue)) {
				FarmCatalogueMaster catalogueMaster = utilService
						.findFarmCatalogueMasterById(Long.valueOf(farmCatalogue.getTypez()));
				if (!ObjectUtil.isEmpty(catalogueMaster)) {
					setCataLogueMasterName(catalogueMaster.getName());
				}
			}
			if (farmCatalogue == null) {
				addActionError(NO_RECORD);
				return list();
			} else {
				setCurrentPage(getCurrentPage());

				utilService.removeCatalogue(farmCatalogue.getName());
			}

		}

		request.setAttribute(HEADING, getText(LIST));
		return LIST;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.view.ESEValidatorAction#getData()
	 */
	/**
	 * @see com.sourcetrace.esesw.view.SwitchValidatorAction#getData()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getData() {
		typezList = formCatalogueMap("cat", typezList);
		return farmCatalogue;

	}

	public void setTypezList(Map<Integer, String> typezList) {

		this.typezList = typezList;
	}

	public Map<Integer, String> getTypezList() {

		return typezList;
	}

	private Map<Integer, String> formCatalogueMap(String keyProperty, Map<Integer, String> typezList) {

		typezList = new LinkedHashMap();
		
			List<FarmCatalogueMaster> farmCatalougeList = utilService.listFarmCatalogueMatsters();
			for(FarmCatalogueMaster fc: farmCatalougeList){
			typezList.put(fc.getTypez(), fc.getName());}
		return typezList;
	}

	public void populateCatalogueType() {

		if (!catalogueName.equalsIgnoreCase("null") && (!StringUtil.isEmpty(catalogueName))) {

			FarmCatalogueMaster existingCatalogueMaster = utilService
					.findFarmCatalogueMasterByName(catalogueName.trim());
			if (existingCatalogueMaster == null) {
				FarmCatalogueMaster farmCatalogue = new FarmCatalogueMaster();
				farmCatalogue.setRevisionNo(Long.valueOf(DateUtil.getRevisionNumber()));
				farmCatalogue.setBranchId(getBranchId());
				farmCatalogue.setName(catalogueName);
				farmCatalogue.setTypez(Integer.valueOf(idGenerator.getCatalougeTypeSeq()));
				utilService.addFarmCatalogueMaster(farmCatalogue);
				JSONArray stateArr = new JSONArray();
				List<FarmCatalogueMaster> catalogueMasterLists = utilService.listFarmCatalogueMatsters();

				if (!ObjectUtil.isEmpty(catalogueMasterLists)) {

					for (FarmCatalogueMaster obj : catalogueMasterLists) {
						stateArr.add(getJSONObject(obj.getId(), obj.getName()));
					}
				}
				sendAjaxResponse(stateArr);
			} else {
				String result = "0";
				sendAjaxResponse(result);
			}
		}

	}

	@SuppressWarnings("unchecked")
	protected JSONObject getJSONObject(Object id, Object name) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("name", name);
		return jsonObject;
	}

	public String getTypezFilterText() {

		StringBuffer sb = new StringBuffer();
		/*
		 * String values = getText("cat"); String[] valuesArray =
		 * values.split(",");
		 * 
		 * ESESystem preferences = preferncesService.findPrefernceById("1");
		 * String isFpoEnabled=""; if(!StringUtil.isEmpty(preferences)){
		 * isFpoEnabled=(preferences.getPreferences().get(ESESystem.ENABLE_FPOFG
		 * )); } if(!isFpoEnabled.equals("1")){ valuesArray= (String[])
		 * ArrayUtils.remove(valuesArray, 10); }
		 * 
		 * sb.append("-1:").append(FILTER_ALL).append(";");
		 * 
		 * for (int count = 0; count < valuesArray.length; count++) {
		 * 
		 * sb.append(count).append(":").append(valuesArray[count]).append(";");
		 * }
		 */
		List<FarmCatalogueMaster> farmCatalougeList =  new ArrayList<>();
	
				 farmCatalougeList = utilService.listFarmCatalogueMatsters();
		
		sb.append("-1:").append(FILTER_ALL).append(";");

		for (FarmCatalogueMaster farmCatalougeMaster : farmCatalougeList) {
			sb.append(farmCatalougeMaster.getTypez()).append(":").append(farmCatalougeMaster.getName()).append(";");
		}

		String data = sb.toString();
		return data.substring(0, data.length() - 1);
	}
	
	public void populateCatalougeMaster(){
		JSONObject jsonObject = new JSONObject();
		utilService.listFarmCatalogueMatsters().stream().forEach(catalogue->{
			jsonObject.put(catalogue.getId(), catalogue.getName());
		});
		
		try {
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.print(jsonObject.toString().replace("\"", "").replace(",", ";"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public String getStatusFilter(){
		String value = getLocaleProperty("statuss1")+":"+getLocaleProperty("statuss0");
		return value;
	}
	
	public FarmCatalogue getFarmCatalogue() {
		return farmCatalogue;
	}

	public void setFarmCatalogue(FarmCatalogue farmCatalogue) {
		this.farmCatalogue = farmCatalogue;
	}

	public Map<Long, String> getCatTypez() {
		Map<Long, String> catalogeList = new HashMap<Long, String>();
		List<FarmCatalogueMaster> farmCatalougeList = utilService.listFarmCatalogueMatsters();
		catalogeList = farmCatalougeList.stream()
				.collect(Collectors.toMap(FarmCatalogueMaster::getId, FarmCatalogueMaster::getName));
		return catalogeList;
	}

	public void setCatTypez(Map<String, String> catTypez) {
		this.catTypez = catTypez;
	}

	public String getCatalogueName() {
		return catalogueName;
	}

	public void setCatalogueName(String catalogueName) {
		this.catalogueName = catalogueName;
	}

	public String getCataLogueMasterName() {
		return cataLogueMasterName;
	}

	public void setCataLogueMasterName(String cataLogueMasterName) {
		this.cataLogueMasterName = cataLogueMasterName;
	}

	public Map<String, String> getStatusMap() {
		statusMap.put("1", getLocaleProperty("statuss1"));
		statusMap.put("0", getLocaleProperty("statuss0"));
		return statusMap;
	}

	public void setStatusMap(Map<String, String> statusMap) {
		this.statusMap = statusMap;
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

	public String getTypez() {
		return typez;
	}

	public void setTypez(String typez) {
		this.typez = typez;
	}
	
	public String getDispName() {
		return dispName;
	}

	public void setDispName(String dispName) {
		this.dispName = dispName;
	}

	public IUtilService getUtilService() {
		return utilService;
	}

	public void setUtilService(IUtilService utilService) {
		this.utilService = utilService;
	}
	
	
}
