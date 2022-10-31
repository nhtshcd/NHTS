/*
 * HarvestSeasonAction.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */

package com.sourcetrace.eses.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;


import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.ESESystem;

import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.entity.HarvestSeason;

import com.sourcetrace.eses.action.SwitchValidatorAction;

// TODO: Auto-generated Javadoc
/**
 * @author admin
 */
public class HarvestSeasonAction extends SwitchValidatorAction {

    private static final long serialVersionUID = -6336132150151746159L;
    DateFormat df = new SimpleDateFormat(getESEProfileDateFormat());
   DateFormat formatSeasonDate=new SimpleDateFormat(getESESeasonDateFormat());
    Map<Integer, String> farmerStatus = new LinkedHashMap<Integer, String>();

    private IFarmerService farmerService;
    private HarvestSeason harvestSeason;
    private String fromPeriod;
    private String toPeriod;
    private String id;
    
    @Autowired
    private IUniqueIDGenerator idGenerator;
    @Autowired
	private IUtilService utilService;

    /**
     * @see com.sourcetrace.esesw.view.SwitchAction#list()
     */
    public String list() throws Exception {

        if (getCurrentPage() != null) {
            setCurrentPage(getCurrentPage());
        }
        request.setAttribute(HEADING, getText("harvestSeasonlist"));
       
        return LIST;
    }

    /**
     * @see com.sourcetrace.esesw.view.SwitchAction#data()
     */
    @SuppressWarnings("unchecked")
    public String data() throws Exception {
    	return sendDataTableJSONResponse(buildFilterDataMap());
    }
    
    @SuppressWarnings("unchecked")
	private Map buildFilterDataMap() throws ParseException {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Map<String, String> searchRecord =getDataTableJQGridRequestParam();// the
																	// search
																	// parameter
																	// with
		                                                            // value

		HarvestSeason filter = new HarvestSeason();

		if (!StringUtil.isEmpty(searchRecord.get("code"))) {
            filter.setCode(searchRecord.get("code").trim());
        }

        if (!StringUtil.isEmpty(searchRecord.get("name"))) {
            filter.setName(searchRecord.get("name").trim());
        }

        if (!StringUtil.isEmpty(searchRecord.get("fromPeriod"))) {
            filter.setFromPeriod(df.parse(searchRecord.get("fromPeriod")));
        }

        if (!StringUtil.isEmpty(searchRecord.get("toPeriod"))) {
            filter.setToPeriod(df.parse(searchRecord.get("toPeriod")));
        }
		Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
				getPage());
		return data;

	}

    /**
     * @see com.sourcetrace.esesw.view.SwitchAction#toJSON(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public JSONObject toJSON(Object obj) {

        HarvestSeason harvestSeason = (HarvestSeason) obj;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        JSONObject jsonObject = new JSONObject();
        JSONObject objDt = new JSONObject();
        JSONArray rows = new JSONArray();
        //objDt.put("code","<font color=\"#0000FF\" style=\"cursor:pointer;\">" + harvestSeason.getCode() + "</font>");
        String linkField = "<a href=harvestSeason_detail.action?id="+harvestSeason.getId()+" title='Go To Detail' target=_blank>"+harvestSeason.getName()+"</a>";
        objDt.put("code", harvestSeason.getCode());
        objDt.put("name",linkField);     
        objDt.put("fromPeriod",String.valueOf(harvestSeason.getFromPeriod()));
        objDt.put("toPeriod",String.valueOf(harvestSeason.getToPeriod()));
      
        jsonObject.put("DT_RowId", harvestSeason.getId());
        jsonObject.put("cell", objDt);
        return jsonObject;
    }

    /**
     * Creates the.
     * @return the string
     * @throws Exception the exception
     */
    public String create() throws Exception {

        if (harvestSeason == null) {
            command = "create";
            request.setAttribute(HEADING, getText("harvestSeasoncreate"));
            return INPUT;
        } else {
        	
        	List<Object[]> harvestSeasons = utilService.listHarvestSeasonFromToPeriod();
        	if(!getCurrentTenantId().equalsIgnoreCase(ESESystem.LALTEER_TENANT_ID)){
        	Date enteredFromPeriod = harvestSeason.getFromPeriod(); 
        	Date enteredToPeriod = harvestSeason.getToPeriod(); 
        	
        	for (Object[] seasonDate : harvestSeasons)
        	{
				String seasonStartDate = formatSeasonDate.format(seasonDate[0]);
				String seasonEndDate = formatSeasonDate.format(seasonDate[1]);
				
				if(isWithinRange(enteredFromPeriod, seasonStartDate, seasonEndDate))
				{
					addActionError(getText("seasonExistsForDate"));
	                return INPUT;
				} 
				
				if(isWithinRange(enteredToPeriod, seasonStartDate, seasonEndDate))
				{
					addActionError(getText("seasonExistsForDate"));
	                return INPUT;
				}
				
				if(enteredFromPeriod.compareTo(DateUtil.convertStringToNewDate(seasonStartDate, "MM/yyyy"))<0 
				&& enteredToPeriod.compareTo(DateUtil.convertStringToNewDate(seasonEndDate, "MM/yyyy"))>0)
				{
					addActionError(getText("seasonExistsForDate"));
                	return INPUT;
				}
			}
        	}
        	else{
        		harvestSeason.setFromPeriod(new Date());
        		harvestSeason.setToPeriod(new Date());
        	}
        	harvestSeason.setCode(idGenerator.getSeasonIdSeq());
        	harvestSeason.setBranchId(getBranchId());
            harvestSeason.setRevisionNo(DateUtil.getRevisionNumber());
            utilService.addHarvestSeason(harvestSeason);
            return REDIRECT;
        }
    }
    
    public boolean isWithinRange(Date enteredFromOrToPeriod, String seasonStartDate, String seasonEndDate) 
    {
    	Calendar calSeasonStartDate = Calendar.getInstance();
    	calSeasonStartDate.setTime(DateUtil.convertStringToNewDate(seasonStartDate, "MM/yyyy"));
		
    	Calendar calSeasonEndDate = Calendar.getInstance(); 
    	calSeasonEndDate.setTime(DateUtil.convertStringToNewDate(seasonEndDate, "MM/yyyy"));
    	
    	if(enteredFromOrToPeriod.compareTo(calSeasonStartDate.getTime())==0)
    		return true;
    	else if(enteredFromOrToPeriod.compareTo(calSeasonEndDate.getTime())==0)
    		return true;
		
    	return enteredFromOrToPeriod.after(calSeasonStartDate.getTime()) && enteredFromOrToPeriod.before(calSeasonEndDate.getTime());
    }
    
    //public boolean 

    /**
     * Detail.
     * @return the string
     * @throws Exception the exception
     */
    public String detail() throws Exception {

        String view = "";
        ESESystem preferences = utilService.findPrefernceById("1");
        DateFormat genDate=new SimpleDateFormat(preferences.getPreferences().get(ESESystem.GENERAL_DATE_FORMAT));
        DateFormat formatSeasonDate=new SimpleDateFormat(getESESeasonDateFormat());
        if (id != null && !id.equals("")) {
            harvestSeason = utilService.findHarvestSeasonById(Long.valueOf(id));
            if (harvestSeason == null) {
                addActionError(NO_RECORD);
                return REDIRECT;
            }
            
            if(getCurrentTenantId().equals("pratibha")){
            	 if(!ObjectUtil.isEmpty(harvestSeason.getFromPeriod()) && harvestSeason.getFromPeriod()!=null){
            		 fromPeriod=formatSeasonDate.format(harvestSeason.getFromPeriod());
            	 }
            	  if(!ObjectUtil.isEmpty(harvestSeason.getToPeriod()) && harvestSeason.getToPeriod()!=null){
            		  toPeriod=formatSeasonDate.format(harvestSeason.getFromPeriod());
            	  }
            	 
            }else{
            	  if(!ObjectUtil.isEmpty(preferences)){
                  	
                      if(!ObjectUtil.isEmpty(harvestSeason.getFromPeriod()) && harvestSeason.getFromPeriod()!=null){
                      	fromPeriod = genDate.format(harvestSeason.getFromPeriod());
                      	//fromPeriod = df.format(harvestSeason.getFromPeriod());	
                      
                      }
                      if(!ObjectUtil.isEmpty(harvestSeason.getToPeriod()) && harvestSeason.getToPeriod()!=null){
                      	toPeriod = genDate.format(harvestSeason.getToPeriod());
                      	//toPeriod = df.format(harvestSeason.getToPeriod());
                      }
                      }
            }
            
            setCurrentPage(getCurrentPage());
            command = UPDATE;
            view = DETAIL;
            request.setAttribute(HEADING, getText("harvestSeasondetail"));
        } else {
            request.setAttribute(HEADING, getText("harvestSeasonlist"));
            return LIST;
        }
        return view;
    }

    /**
     * Update.
     * @return the string
     * @throws Exception the exception
     */
    public String update() throws Exception {

        if (id != null && !id.equals("")) {
            harvestSeason = utilService.findHarvestSeasonById(Long.valueOf(id));
            if (harvestSeason == null) {
                addActionError(NO_RECORD);
                return REDIRECT;
            }
            setCurrentPage(getCurrentPage());
            if(getCurrentTenantId().equals("pratibha")){
            	  fromPeriod = formatSeasonDate.format(harvestSeason.getFromPeriod());
                  toPeriod = formatSeasonDate.format(harvestSeason.getToPeriod());
            }else{
            	 fromPeriod = df.format(harvestSeason.getFromPeriod());
                 toPeriod = df.format(harvestSeason.getToPeriod());
            }
           
            id = null;
            command = UPDATE;
            request.setAttribute(HEADING, getText("harvestSeasonupdate"));
        } else {
            if (harvestSeason != null) {
                HarvestSeason existing = utilService.findHarvestSeasonById(harvestSeason.getId());
                if (existing == null) {
                    addActionError(NO_RECORD);
                    return REDIRECT;
                }
                
                List<Object[]> harvestSeasons = utilService.listHarvestSeasonFromToPeriod();
            	
            	Date enteredFromPeriod = harvestSeason.getFromPeriod(); 
            	Date enteredToPeriod = harvestSeason.getToPeriod(); 
            	if(getCurrentTenantId().equals("pratibha")){
            		for (Object[] seasonDate : harvestSeasons)
                	{
        				String seasonStartDate = formatSeasonDate.format(seasonDate[0]);
        				String seasonEndDate = formatSeasonDate.format(seasonDate[1]);
        				long harvestSeasonId = Long.valueOf(String.valueOf(seasonDate[2]));
        				
        				if(harvestSeasonId!=harvestSeason.getId())
        				{
    	    				if(isWithinRange(enteredFromPeriod, seasonStartDate, seasonEndDate))
    	    				{
    	    					addActionError(getText("seasonExistsForDate"));
    	    	                return INPUT;
    	    				} 
    	    				
    	    				if(isWithinRange(enteredToPeriod, seasonStartDate, seasonEndDate))
    	    				{
    	    					addActionError(getText("seasonExistsForDate"));
    	    	                return INPUT;
    	    				}
    	    				
    	    				if(enteredFromPeriod.compareTo(DateUtil.convertStringToNewDate(seasonStartDate, "MM/yyyy"))<0 
    	    				&& enteredToPeriod.compareTo(DateUtil.convertStringToNewDate(seasonEndDate, "MM/yyyy"))>0)
    	    				{
    	    					addActionError(getText("seasonExistsForDate"));
    	                    	return INPUT;
    	    				}
        				}
        			}
            	}else{
            		for (Object[] seasonDate : harvestSeasons)
                	{
        				String seasonStartDate = df.format(seasonDate[0]);
        				String seasonEndDate = df.format(seasonDate[1]);
        				long harvestSeasonId = Long.valueOf(String.valueOf(seasonDate[2]));
        				
        				if(harvestSeasonId!=harvestSeason.getId())
        				{
    	    			/*	if(isWithinRange(enteredFromPeriod, seasonStartDate, seasonEndDate))
    	    				{
    	    					addActionError(getText("seasonExistsForDate"));
    	    	                return INPUT;
    	    				} 
    	    				
    	    				if(isWithinRange(enteredToPeriod, seasonStartDate, seasonEndDate))
    	    				{
    	    					addActionError(getText("seasonExistsForDate"));
    	    	                return INPUT;
    	    				}*/
    	    				
    	    				if(enteredFromPeriod.compareTo(DateUtil.convertStringToDate(seasonStartDate, "dd/MM/yyyy"))<0 
    	    				&& enteredToPeriod.compareTo(DateUtil.convertStringToDate(seasonEndDate, "dd/MM/yyyy"))>0)
    	    				{
    	    					addActionError(getText("seasonExistsForDate"));
    	                    	return INPUT;
    	    				}
        				}
        			}
            	}
            
                //existing.setCurrentSeason(harvestSeason.getCurrentSeason());
                setCurrentPage(getCurrentPage());
                existing.setName(harvestSeason.getName());
                existing.setFromPeriod(harvestSeason.getFromPeriod());
                existing.setToPeriod(harvestSeason.getToPeriod());
               
               
                existing.setRevisionNo(DateUtil.getRevisionNumber());
                utilService.editHarvestSeason(existing);
            }
            request.setAttribute(HEADING, getText("harvestSeasonlist"));
            return LIST;
        }
        return super.execute();
    }

    /**
     * Delete.
     * @return the string
     * @throws Exception the exception
     */
    public String delete() throws Exception {

        if (this.getId() != null && !(this.getId().equals(EMPTY))) {
            harvestSeason = utilService.findHarvestSeasonById(Long.valueOf(id));
            setCurrentPage(getCurrentPage());
            
            
            
            if (harvestSeason == null) {
                addActionError(NO_RECORD);
                return null;
            } else {
            	utilService.removeHarvestSeason(harvestSeason);
            }

        }

        request.setAttribute(HEADING, getText("harvestSeasonlist"));
        return LIST;

    }

    /**
     * @see com.sourcetrace.esesw.view.SwitchValidatorAction#getData()
     */
    @Override
    public Object getData() {

        if (harvestSeason != null) {
        	if(getCurrentTenantId().equals("pratibha")){
        		
        		 if (fromPeriod != null && (!StringUtil.isEmpty(fromPeriod))) {
                     try {
                     	Date frmDate = DateUtil.convertStringToDate(fromPeriod, DateUtil.HARVEST_SEASON_DATE_FORMAT);
                     	harvestSeason.setFromPeriod(frmDate);
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
                 if (toPeriod != null && (!StringUtil.isEmpty(toPeriod))) {
                     try {
                     	Date toDate = DateUtil.convertStringToDate(toPeriod, DateUtil.HARVEST_SEASON_DATE_FORMAT);
                     	harvestSeason.setToPeriod(toDate);
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
        			
        	}else{
        		 if (fromPeriod != null && (!StringUtil.isEmpty(fromPeriod))) {
                     try {
                     	Date frmDate = DateUtil.convertStringToDate(fromPeriod, DateUtil.PROFILE_DATE_FORMAT);
                     	harvestSeason.setFromPeriod(frmDate);
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
                 if (toPeriod != null && (!StringUtil.isEmpty(toPeriod))) {
                     try {
                     	Date toDate = DateUtil.convertStringToDate(toPeriod, DateUtil.PROFILE_DATE_FORMAT);
                     	harvestSeason.setToPeriod(toDate);
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
        	}
           
        
        }
        return harvestSeason;
    }

    /**
     * Sets the harvest season.
     * @param harvestSeason the new harvest season
     */
    public void setHarvestSeason(HarvestSeason harvestSeason) {

        this.harvestSeason = harvestSeason;
    }

    /**
     * Gets the harvest season.
     * @return the harvest season
     */
    public HarvestSeason getHarvestSeason() {

        return harvestSeason;
    }

    /**
     * Sets the from period.
     * @param fromPeriod the new from period
     */
    public void setFromPeriod(String fromPeriod) {

        this.fromPeriod = fromPeriod;
    }

    /**
     * Gets the from period.
     * @return the from period
     */
    public String getFromPeriod() {

        return fromPeriod;
    }

    /**
     * Sets the to period.
     * @param toPeriod the new to period
     */
    public void setToPeriod(String toPeriod) {

        this.toPeriod = toPeriod;
    }

    /**
     * Gets the to period.
     * @return the to period
     */
    public String getToPeriod() {

        return toPeriod;
    }

    /**
     * Gets the id.
     * @return the id
     */
    public String getId() {

        return id;
    }

    /**
     * Sets the id.
     * @param id the new id
     */
    public void setId(String id) {

        this.id = id;
    }

    /**
     * Gets the farmer service.
     * @return the farmer service
     */
    public IFarmerService getFarmerService() {

        return farmerService;
    }

    /**
     * Sets the farmer service.
     * @param farmerService the new farmer service
     */
    public void setFarmerService(IFarmerService farmerService) {

        this.farmerService = farmerService;
    }

    /**
     * Gets the eSE profile date format.
     * @return the eSE profile date format
     */
    public String getESEProfileDateFormat() {

        return StringUtil.getString(DateUtil.WEB_DATE_FORMAT, DateUtil.PROFILE_DATE_FORMAT);
    }
    
    
    public String getESESeasonDateFormat(){
    	return StringUtil.getString(DateUtil.HARVEST_SEASON_DATE_FORMAT, DateUtil.HARVEST_SEASON_DATE_FORMAT);
    }

    /**
     * Gets the farmer status.
     * @return the farmer status
     */
    public Map<Integer, String> getFarmerStatus() {
        
        farmerStatus = formMap("status");

        return farmerStatus;
    }

    /**
     * Sets the farmer status.
     * @param farmerStatus the farmer status
     */
    public void setFarmerStatus(Map<Integer, String> farmerStatus) {

        this.farmerStatus = farmerStatus;
    }
    
    @SuppressWarnings("unchecked")
    private Map formMap(String keyProperty) {

        Map dataMap = new LinkedHashMap();
        String values = getText(keyProperty);
        if (!StringUtil.isEmpty(values)) {
            String[] valuesArray = values.split(",");
            int i = 0;
            for (String value : valuesArray) {
                dataMap.put(i++, value);
            }
        }
        return dataMap;
    }

	public IUniqueIDGenerator getIdGenerator() {
		return idGenerator;
	}

	public void setIdGenerator(IUniqueIDGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

    public IUtilService getUtilService() {
		return utilService;
	}

	public void setUtilService(IUtilService utilService) {
		this.utilService = utilService;
	}

public Map<String,String> getSeasonNameList(){
	Map<String,String> seasonMap=new LinkedHashMap<String,String>();
	int curYear = DateUtil.getCurrentYear();
	  int startYear = 2010;
	  int endYear = curYear+1;
	 
	  for (int i = startYear; i <endYear; i++) {
		  seasonMap.put(i+"-"+(i+1),i+"-"+(i+1));
	  }
	  
	return seasonMap;
}    
    

}
