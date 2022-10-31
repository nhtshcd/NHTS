package com.sourcetrace.eses.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.Country;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.GramPanchayat;
import com.sourcetrace.eses.entity.Locality;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

public class GramPanchayatAction extends SwitchValidatorAction {
	
	 private GramPanchayat gramPanchayat;
	    private String selectedCountry;
	    private String selectedState;
	    private String selectedDistrict;
	    private String selectedCity;
	    private String id;
	    List<Locality> localitiesId = new ArrayList<Locality>();
	    List<State> statesId = new ArrayList<State>();
	    List<Municipality> cities = new ArrayList<Municipality>();
	    private IUniqueIDGenerator idGenerator;
	    
	    @Autowired
		private IUtilService utilService;

	    
	 public GramPanchayat getGramPanchayat() {
			return gramPanchayat;
		}

		public void setGramPanchayat(GramPanchayat gramPanchayat) {
			this.gramPanchayat = gramPanchayat;
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
		}

		public String getSelectedDistrict() {
			return selectedDistrict;
		}

		public void setSelectedDistrict(String selectedDistrict) {
			this.selectedDistrict = selectedDistrict;
		}

		public String getSelectedCity() {
			return selectedCity;
		}

		public void setSelectedCity(String selectedCity) {
			this.selectedCity = selectedCity;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public List<Locality> getLocalitiesId() {
			return localitiesId;
		}

		public void setLocalitiesId(List<Locality> localities) {
			this.localitiesId = localities;
		}

		public List<State> getStatesId() {
			return statesId;
		}

		public void setStatesId(List<State> states) {
			this.statesId = states;
		}

		public List<Municipality> getCities() {
			return cities;
		}

		public void setCities(List<Municipality> cities) {
			this.cities = cities;
		}

		public IUniqueIDGenerator getIdGenerator() {
			return idGenerator;
		}

		public void setIdGenerator(IUniqueIDGenerator idGenerator) {
			this.idGenerator = idGenerator;
		}

	@SuppressWarnings("unchecked")
	    public String data() throws Exception {

	        Map<String, String> searchRecord = getJQGridRequestParam(); // get the search parameter with

	        GramPanchayat filter = new GramPanchayat();

	        if (!StringUtil.isEmpty(searchRecord.get("code"))) {
	            filter.setCode(searchRecord.get("code").trim());
	        }

	        if (!StringUtil.isEmpty(searchRecord.get("name"))) {
	            filter.setName(searchRecord.get("name").trim());
	        }

	        if (!StringUtil.isEmpty(searchRecord.get("selectedCity"))) {
	            Municipality city = new Municipality();
	            city.setName(searchRecord.get("selectedCity").trim());
	            filter.setCity(city);
	        }
	        
	        if (!StringUtil.isEmpty(searchRecord.get("selectedDistrict"))) {
				Municipality city = new Municipality();
				Locality locality = new Locality();
				locality.setName(searchRecord.get("selectedDistrict").trim());
				city.setLocality(locality);
				filter.setCity(city);
			}
	        
	        if (!StringUtil.isEmpty(searchRecord.get("selectedState"))) {
				Municipality city = new Municipality();
				Locality locality = new Locality();
				State state = new State();
				state.setName(searchRecord.get("selectedState").trim());
				locality.setState(state);
				city.setLocality(locality);
				filter.setCity(city);
			}
	        
	        if (!StringUtil.isEmpty(searchRecord.get("selectedCountry"))) {
				Municipality city = new Municipality();
				Locality locality = new Locality();
				State state = new State();
				Country country = new Country();
				country.setName(searchRecord.get("selectedCountry").trim());
				state.setCountry(country);
				locality.setState(state);
				city.setLocality(locality);
				filter.setCity(city);
			}
	        
	        
	        if (!StringUtil.isEmpty(searchRecord.get("branchId"))) {
				if (!getIsMultiBranch().equalsIgnoreCase("1")) {
					List<String> branchList = new ArrayList<>();
					branchList.add(searchRecord.get("branchId").trim());
					filter.setBranchesList(branchList);
				} else {
					List<String> branchList = new ArrayList<>();
					List<BranchMaster> branches = utilService.listChildBranchIds(searchRecord.get("branchId").trim());
					branchList.add(searchRecord.get("branchId").trim());
					branches.stream().filter(branch -> !StringUtil.isEmpty(branch)).forEach(branch -> {
						branchList.add(branch.getBranchId());
					});
					filter.setBranchesList(branchList);
				}
			}

	        Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(),
	                getResults(), filter, getPage());

	        return sendJQGridJSONResponse(data);
	    }
	 
	  @SuppressWarnings("unchecked")
	    public JSONObject toJSON(Object obj) {

	        GramPanchayat gramPanchayat = (GramPanchayat) obj;
	        JSONObject jsonObject = new JSONObject();
	        JSONArray rows = new JSONArray();
	        if ((getIsMultiBranch().equalsIgnoreCase("1")
					&& (getIsParentBranch().equals("1") || StringUtil.isEmpty(branchIdValue)))) {

				if (StringUtil.isEmpty(branchIdValue)) {
					rows.add(!StringUtil.isEmpty(getBranchesMap().get(getParentBranchMap().get(gramPanchayat.getBranchId())))
							? getBranchesMap().get(getParentBranchMap().get(gramPanchayat.getBranchId()))
							: getBranchesMap().get(gramPanchayat.getBranchId()));
				}
				rows.add(getBranchesMap().get(gramPanchayat.getBranchId()));

			} else {
				if (StringUtil.isEmpty(branchIdValue)) {
					rows.add(branchesMap.get(gramPanchayat.getBranchId()));
				}
			}
	        rows.add(gramPanchayat.getCode());
	        rows.add(gramPanchayat.getName());
	        rows.add(gramPanchayat.getCity() == null ? "  " :gramPanchayat.getCity().getName());
	        rows.add(gramPanchayat.getCity().getLocality().getName());
	        rows.add(gramPanchayat.getCity().getLocality().getState().getName());
	        rows.add(gramPanchayat.getCity().getLocality().getState().getCountry().getName());
	        jsonObject.put("id", gramPanchayat.getId());
	        jsonObject.put("cell", rows);
	        return jsonObject;
	    }

	    /**
	     * Creates the.
	     * @return the string
	     * @throws Exception the exception
	     */
	    public String create() throws Exception {
	    	
	    	ESESystem preferences = utilService.findPrefernceById("1");
			String codeGenType = preferences.getPreferences().get(ESESystem.CODE_TYPE);

	        if (gramPanchayat == null) {
	            command = CREATE;
	            request.setAttribute(HEADING, getText(CREATE));
	            return INPUT;
	        } else {
	            Municipality city = utilService.findMunicipalityByCode(gramPanchayat.getCity()
	                    .getCode());
	            gramPanchayat.setCity(city);
	            gramPanchayat.setBranchId(getBranchId());
			
				
				
					gramPanchayat.setCode(idGenerator.getGramPanchayatIdSeq());
				
				
	            
	            utilService.addGramPanchayat(gramPanchayat);
	            return REDIRECT;
	        }
	    }

	    /**
	     * Update.
	     * @return the string
	     * @throws Exception the exception
	     */
	    public String update() throws Exception {

	        if (id != null && !id.equals("")) {
	            gramPanchayat = utilService.findGramPanchayatById(Long.parseLong(id));
	            if (gramPanchayat == null) {
	                addActionError(NO_RECORD);
	                return REDIRECT;
	            }
	            setSelectedCountry(gramPanchayat.getCity().getLocality().getState().getCountry()
	                    .getName());
	            setSelectedState(gramPanchayat.getCity().getLocality().getState().getCode());
	            setSelectedDistrict(gramPanchayat.getCity().getLocality().getCode());
	            setCurrentPage(getCurrentPage());
	            id = null;
	            command = UPDATE;
	            request.setAttribute(HEADING, getText(UPDATE));
	        } else {
	        	ESESystem preferences = utilService.findPrefernceById("1");
				String codeGenType = preferences.getPreferences().get(ESESystem.CODE_TYPE);
	            if (gramPanchayat != null) {
	                GramPanchayat existing = utilService.findGramPanchayatById(gramPanchayat
	                        .getId());
	                if (existing == null) {
	                    addActionError(NO_RECORD);
	                    return REDIRECT;
	                }
	                setCurrentPage(getCurrentPage());
	                if (codeGenType.equals("1")) {
						if(!gramPanchayat.getCity().getName() .equalsIgnoreCase(existing.getCity().getName()))
						{
							String gramPanchayatCodeSeq = idGenerator.getGramPanchayatHHIdSeq(existing.getCity().getCode());
							existing.setCode(gramPanchayatCodeSeq);
						}
					}
	                
	                existing.setName(gramPanchayat.getName());
	                Municipality city = utilService.findMunicipalityByCode(gramPanchayat.getCity()
	                        .getCode());
					
	                existing.setCity(city);
	                existing.setBranchId(getBranchId());
	                utilService.editGramPanchayat(existing);
	            }
	            command = UPDATE;
	            request.setAttribute(HEADING, getText(LIST));
	            return LIST;
	        }
	        return super.execute();
	    }

	    /**
	     * Delete.
	     * @throws Exception the exception
	     */
	    @SuppressWarnings("unchecked")
	    public void delete() throws Exception {

	        if (id != null && !id.equals("")) {
	            gramPanchayat = utilService.findGramPanchayatById(Long.parseLong(id));
	            if (ObjectUtil.isEmpty(gramPanchayat)) {
	                addActionError(NO_RECORD);
	            }

	            String flag = null;
	            if (!ObjectUtil.isListEmpty(gramPanchayat.getVillages())) {
	                flag = "village.mapped.grampanchayat";
	                getJsonObject().put("msg", getText(flag));  
				    getJsonObject().put("title", getText("title.error"));
	            }
	           
	          /*  addActionError(getLocaleProperty(flag));
	            request.setAttribute(HEADING, getText(DETAIL));
	            return DETAIL;8*/
	        
	        else{
	        	 if (StringUtil.isEmpty(flag)) {
	                 utilService.removeGramPanchayat(gramPanchayat);
	                 getJsonObject().put("msg", getText("msg.deleted"));
	     			getJsonObject().put("title", getText("title.success"));
	     			
	             }
	        }
	        }
	        sendAjaxResponse(getJsonObject());
	    }

	    /**
	     * Detail.
	     * @return the string
	     * @throws Exception the exception
	     */
	    public String detail() throws Exception {

	        String view = "";
	        if (id != null && !id.equals("")) {
	            gramPanchayat = utilService.findGramPanchayatById(Long.parseLong(id));
	            if (gramPanchayat == null) {
	                addActionError(NO_RECORD);
	                return REDIRECT;
	            }
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
	     * @see com.sourcetrace.esesw.view.SwitchValidatorAction#getData()
	     */
	    @Override
	    public Object getData() {

	        if (ObjectUtil.isEmpty(gramPanchayat)) {
	            return null;
	        } else {
	            Country country = new Country();
	            country.setName(this.selectedCountry);

	            State state = new State();
	            state.setCode(this.selectedState);
	            state.setCountry(country);

	            Locality locality = new Locality();
	            locality.setCode(this.selectedDistrict);
	            locality.setState(state);

	            Municipality city = new Municipality();
	            city.setCode(gramPanchayat.getCity().getCode());
	            city.setLocality(locality);
	            gramPanchayat.setCity(city);

	            return gramPanchayat;
	        }

	    }

}
