package com.sourcetrace.eses.action;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Country;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.LandPreparation;
import com.sourcetrace.eses.entity.LandPreparationDetails;
import com.sourcetrace.eses.entity.Locality;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.ParentEntity;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.entity.Village;
import com.sourcetrace.eses.interceptor.ITxnErrorCodes;
import com.sourcetrace.eses.txn.exception.SwitchException;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class LandPreparationAction extends SwitchAction {

	private static final long serialVersionUID = 1L;

	private String QUERY = "FROM LandPreparation lp LEFT JOIN FETCH lp.landPreparationDetails lpd WHERE lp.id=? AND lp.status=? AND lp.status_code=?";
	private String FARMCROPS_QUERY="FROM Farm fc WHERE fc.id=? AND fc.status=?";
	private String FARMCROPS_BLOCK_QUERY="FROM FarmCrops fc WHERE fc.id=? AND fc.status=?";
	
	@Getter
	@Setter
	protected String command;

	@Getter
	@Setter
	protected String currentPage;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String date;

	@Getter
	@Setter
	private String redirectContent;

	@Getter
	@Setter
	private String landPreparationDtl;

	@Getter
	@Setter
	private LandPreparation landPreparation;

	@Getter
	@Setter
	private String farmCropfarmId;
	
	@Getter
	@Setter
	private String editFarmCropId;
	/**
	 * for detail page
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		if (id != null && !StringUtil.isEmpty(id) && StringUtil.isLong(id)) {
			landPreparation = (LandPreparation) farmerService.findObjectById(QUERY, new Object[] { Long.valueOf(id), 1, 0 });
			if (landPreparation == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			date=DateUtil.convertDateToString(landPreparation.getDate(), getGeneralDateFormat());
			setCurrentPage(getCurrentPage());
			if (landPreparation.getFarmCrops() != null && !StringUtil.isEmpty(landPreparation.getFarmCrops())) {
				FarmCrops fc = (FarmCrops) farmerService.findObjectById(" from FarmCrops fc where fc.id=?  and fc.status=1",
						new Object[] { Long.valueOf(landPreparation.getFarmCrops().getId()) });
				
				setFarmCropfarmId(fc.getBlockName() != null ? String.valueOf(fc.getBlockId()+"-"+fc.getBlockName()) : "");
				
				}
			setCommand(DETAIL);
			return DETAIL;
		} else {
			request.setAttribute(HEADING, getText("landPreparationDetail"));
			return REDIRECT;
		}
	}

	/**
	 * For create page and insert record
	 * @return
	 * @throws Exception
	 */
	public String create() throws Exception {
		if (landPreparation == null) {
			command = CREATE;
			request.setAttribute(HEADING, "landpreparationcreate");
			return INPUT;
		} else {
			Farm farm=(Farm) farmerService.findObjectById(FARMCROPS_QUERY, new Object[]{landPreparation.getFarm().getId(),1});
			
			landPreparation.setFarm(farm);	
			if (landPreparation.getFarmCrops() != null && !StringUtil.isEmpty(landPreparation.getFarmCrops().getId())) {
			FarmCrops farmCrops=(FarmCrops) farmerService.findObjectById(FARMCROPS_BLOCK_QUERY, new Object[]{Long.valueOf(landPreparation.getFarmCrops().getId()),1});
			
			landPreparation.setFarmCrops(farmCrops);
			}
			landPreparation.setDate(DateUtil.convertStringToDate(getDate(), getGeneralDateFormat()));
			landPreparation.setCreatedDate(new Date());
			landPreparation.setCreatedUser(getUsername());
			landPreparation.setBranchId(getBranchId());
			landPreparation.setStatus(1);
			landPreparation.setStatus_code(0);
			landPreparation.setLatitude(getLatitude());
			landPreparation.setLongitude(getLongitude());
			if (landPreparationDtl != null && !StringUtil.isEmpty(landPreparationDtl)) {
				Set<LandPreparationDetails> lpDetails = getLandPreparationDetails(landPreparation);
				landPreparation.setLandPreparationDetails(lpDetails);
			}
			
			utilService.save(landPreparation);

			
		}
		return REDIRECT;
	}

	/**
	 * To get list data of activity,activity mode and no. of labourers
	 * @param landPreparation
	 * @return
	 */
	private Set<LandPreparationDetails> getLandPreparationDetails(LandPreparation landPreparation) {
		Set<LandPreparationDetails> lpDetails = new LinkedHashSet<>();

		if (!StringUtil.isEmpty(getLandPreparationDtl())) {
			List<String> lpList = Arrays.asList(getLandPreparationDtl().split("@"));

			lpList.stream().filter(obj -> !StringUtil.isEmpty(obj)).forEach(lp -> {
				LandPreparationDetails lpDtl = new LandPreparationDetails();
				List<String> list = Arrays.asList(lp.split("#"));
				lpDtl.setActivity(list.get(0));
				lpDtl.setActivityMode(list.get(1));
				lpDtl.setNoOfLabourers(list.get(2));
				lpDtl.setLandPreparation(landPreparation);
				lpDetails.add(lpDtl);
			});
		}

		return lpDetails;
	}

	/**
	 * For edit page and update record
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		if (id != null && !StringUtil.isEmpty(id) && landPreparation == null) {
			landPreparation = (LandPreparation) farmerService.findObjectById(QUERY,
					new Object[] { Long.valueOf(getId()), 1, 0 });
			if (landPreparation == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			date=DateUtil.convertDateToString(landPreparation.getDate(), getGeneralDateFormat());
			
			setSelectedVillage(landPreparation.getFarm().getFarmer().getVillage() != null ? String.valueOf(landPreparation.getFarm().getFarmer().getVillage().getId()) : "");
			setSelectedCity(
					landPreparation.getFarm().getFarmer().getVillage() != null ? String.valueOf(landPreparation.getFarm().getFarmer().getVillage().getCity().getId()) : "");
			setSelectedLocality(landPreparation.getFarm().getFarmer().getVillage() != null
					? String.valueOf(landPreparation.getFarm().getFarmer().getVillage().getCity().getLocality().getId()) : "");
			setSelectedState(landPreparation.getFarm().getFarmer().getVillage() != null
					? String.valueOf(landPreparation.getFarm().getFarmer().getVillage().getCity().getLocality().getState().getId()) : "");
			setSelectedCountry(landPreparation.getFarm().getFarmer().getVillage() != null
					? landPreparation.getFarm().getFarmer().getVillage().getCity().getLocality().getState().getCountry().getName() : "");
			setCurrentPage(getCurrentPage());
			setSelectedFarm(landPreparation.getFarm() != null ? String.valueOf(landPreparation.getFarm().getId()) : "");
			setSelectedFarmer(landPreparation.getFarm() != null
					? String.valueOf(landPreparation.getFarm().getFarmer().getId()) : "");
			
			if (landPreparation.getFarmCrops() != null && !StringUtil.isEmpty(landPreparation.getFarmCrops().getId())) {
			FarmCrops fc = (FarmCrops) farmerService.findObjectById(" from FarmCrops fc where fc.id=?  and fc.status=1",
					new Object[] { Long.valueOf(landPreparation.getFarmCrops().getId()) });
			setEditFarmCropId(fc.getId() != null ? String.valueOf(fc.getId()) : "");
			setFarmCropfarmId(fc.getFarm().getId() != null ? String.valueOf(fc.getFarm().getId()) : "");
			
			}
			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getText("landPreparationupdate"));
		} else {
			if (landPreparation != null) {
				LandPreparation preparation = (LandPreparation) farmerService.findObjectById(QUERY,
						new Object[] { landPreparation.getId(), 1, 0 });

				
				preparation.setDate(DateUtil.convertStringToDate(date, getGeneralDateFormat()));				
				
				Farm farm=(Farm) farmerService.findObjectById(FARMCROPS_QUERY, new Object[]{landPreparation.getFarm().getId(),1});				

				preparation.setFarm(farm);
				if (landPreparation.getFarmCrops() != null && !StringUtil.isEmpty(landPreparation.getFarmCrops().getId())) {
				FarmCrops farmCrops=(FarmCrops) farmerService.findObjectById(FARMCROPS_BLOCK_QUERY, new Object[]{Long.valueOf(landPreparation.getFarmCrops().getId()),1});
				
				preparation.setFarmCrops(farmCrops);
				}
				preparation.getLandPreparationDetails().clear();
				preparation.setUpdatedDate(new Date());
				preparation.setUpdatedUser(getUsername());
				
				
				utilService.update(preparation);
				Set<LandPreparationDetails> lpDetails = getLandPreparationDetails(landPreparation);
				preparation.setLandPreparationDetails(lpDetails);
				if (preparation.getLandPreparationDetails() != null
						&& preparation.getLandPreparationDetails().size() > 0) {
					preparation.getLandPreparationDetails().stream()
							.filter(lpd -> lpd != null && !ObjectUtil.isEmpty(lpd)).forEach(lpd -> {
								lpd.setLandPreparation(preparation);
								utilService.save(lpd);
							});
				}
			}

			setCurrentPage(getCurrentPage());
			request.setAttribute(HEADING, getText("landPreparationlist"));
			return REDIRECT;
		}
		return super.execute();
	}

	/**
	 * For deletion for record
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		String result = null;
		if (id != null && !StringUtil.isEmpty(id)) {
			LandPreparation preparation = (LandPreparation) farmerService.findObjectById(QUERY,
					new Object[] { Long.valueOf(getId()), 1, 0 });
			if (preparation == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(preparation)) {
				preparation.setStatus(ParentEntity.Active.DELETED.ordinal());
				utilService.update(preparation);
				result = REDIRECT;
			}
		}
		return result;
	}

	/**
	 * for input data validation
	 */
	public void populateValidate() {
		Map<String, String> errorCodes = new LinkedHashMap<String, String>();

		if (landPreparation != null) {

			
			if (landPreparation.getFarm().getId() == null || StringUtil.isEmpty(landPreparation.getFarm().getId())) {
				errorCodes.put("empty.landPreparation.farm", getLocaleProperty("empty.landPreparation.farm"));
			}
			if (date == null || StringUtil.isEmpty(date)) {
				errorCodes.put("empty.landPreparation.date", getLocaleProperty("empty.landPreparation.date"));
			}
			
			if (landPreparationDtl == null || StringUtil.isEmpty(landPreparationDtl)) {
				errorCodes.put("empty.landPreparation.activity", getLocaleProperty("empty.landPreparation.activity"));
			}
			
			if (landPreparation == null || landPreparation.getFarmCrops() == null || landPreparation.getFarmCrops().getId() == null) {

				errorCodes.put("empty.block", "empty.block");
			}
			

		}else{
			errorCodes.put("empty.fields", getLocaleProperty("empty.fields"));
		}
		printErrorCodes(errorCodes);
	}
	
	public void populateFarmerByAuditRequest() throws Exception {
		JSONArray farmerArr = new JSONArray();
		List<Object[]> dataList = new ArrayList();
		String qry = "SELECT DISTINCT f.id,f.farmerId,f.firstName,f.lastName from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1";
		LinkedList<Object> parame = new LinkedList();

		if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
			qry = "SELECT DISTINCT f.id,f.farmerId,f.firstName,f.lastName from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1 and fc.exporter.id = ? ";
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
	
	public void populatBlocks() {
		JSONArray farmerArr = new JSONArray();
		if (selectedFarmer != null && !ObjectUtil.isEmpty(selectedFarmer) && !selectedFarmer.equals("")
				&& selectedFarm != null && !ObjectUtil.isEmpty(selectedFarm)) {
			
			LinkedList<Object> parame = new LinkedList();
			String qry = "FROM FarmCrops f  where f.farm.id=?  and f.status=1 ORDER BY f.id ASC";
			parame.add(Long.valueOf(selectedFarm));
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
			String qry="select distinct f.village.city.locality.state from Farm fm join fm.farmer f where f.status=1 and f.village.city.locality.state.country.name =? ";
			parame.add(selectedCountry);
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry="select distinct f.village.city.locality.state from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1 and f.village.city.locality.state.country.name =?  and fc.exporter.id = ? ";
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
			String qry="select distinct f.village.city.locality from Farm fm join fm.farmer f where f.status=1 and f.village.city.locality.state.id =? ";
			parame.add(Long.valueOf(selectedState));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry="select distinct f.village.city.locality from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1 and f.village.city.locality.state.id =? and fc.exporter.id = ?";
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
			String qry="select distinct f.village.city from Farm fm join fm.farmer f where f.status=1 and f.village.city.locality.id =? ";
			parame.add(Long.valueOf(selectedLocality));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry="select distinct f.village.city from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1 and f.village.city.locality.id =? and fc.exporter.id = ?";
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
			String qry="select distinct f.village from Farm fm join fm.farmer f where f.status=1 and f.village.city.id =? ";
			parame.add(Long.valueOf(selectedCity));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry="select distinct f.village from FarmCrops fc join fc.farm fm join fm.farmer f where f.status=1 and f.village.city.id =? and fc.exporter.id=?";
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
