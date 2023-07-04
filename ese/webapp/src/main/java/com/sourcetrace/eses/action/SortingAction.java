package com.sourcetrace.eses.action;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.CityWarehouse;
import com.sourcetrace.eses.entity.CityWarehouseDetail;
import com.sourcetrace.eses.entity.Country;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.Locality;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.ParentEntity;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.Sorting;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.entity.Village;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class SortingAction extends SwitchAction {

	private static final long serialVersionUID = 1L;

	private String QUERY = "FROM Sorting s WHERE s.id=? AND s.status=?";
	private String FARMCROPS_QUERY = "FROM Planting fc WHERE fc.id=? AND fc.status=?";
	private String CITY_QUERY = "FROM CityWarehouse cw WHERE cw.planting.id=? and  cw.coOperative is  null";
	@Getter
	@Setter
	private String redirectContent;

	@Getter
	@Setter
	private String command;

	@Getter
	@Setter
	private String currentPage;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String qtyHarvested;

	@Getter
	@Setter
	private String qtyRejected;

	@Getter
	@Setter
	private String netQty;

	@Getter
	@Setter
	private Sorting sorting;

	@Getter
	@Setter
	private String selectedFarmCropsId;

	@Getter
	@Setter
	private String dateHarvested;

	@Getter
	@Setter
	private String cropPlanting;

	@Getter
	@Setter
	private String blockId;

	@Getter
	@Setter
	List<Object[]> ex;

	/**
	 * For detail page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		if (id != null && !StringUtil.isEmpty(id) && StringUtil.isLong(id)) {
			sorting = (Sorting) farmerService.findObjectById(QUERY, new Object[] { Long.valueOf(getId()), 1 });
			if (sorting == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			qtyRejected = sorting.getQtyRejected().toString();
			netQty = sorting.getQtyNet().toString();
			JSONObject harvest = getHarvestByFarmCropsId(sorting.getPlanting().getId().toString());
			dateHarvested = harvest.get("dateHarvested").toString();
			qtyHarvested = harvest.get("qtyHarvested").toString();
			ex = utilService.getAuditRecords("com.sourcetrace.eses.entity.Sorting", sorting.getId());

			setCommand(DETAIL);
			return DETAIL;
		} else {
			request.setAttribute(HEADING, getText("sortingDetail"));
			return REDIRECT;
		}
	}

	/**
	 * To get create page and insert record
	 * 
	 * @return
	 * @throws Exception
	 */
	public String create() throws Exception {
		if (sorting == null) {
			setCurrentPage(getCurrentPage());
			command = CREATE;
			request.setAttribute(HEADING, "sortingcreate");
			return INPUT;
		} else {
			Planting farmCrops = (Planting) farmerService.findObjectById(FARMCROPS_QUERY,
					new Object[] { sorting.getPlanting().getId(), 1 });
			sorting.setPlanting(farmCrops);
			// sorting.setFarmCrops(farmCrops.getFarmCrops());
			sorting.setQtyHarvested(Double.valueOf(qtyHarvested));
			sorting.setQtyRejected(Double.valueOf(qtyRejected));
			sorting.setQtyNet(Double.valueOf(netQty));
			sorting.setCreatedDate(new Date());
			sorting.setBranchId(getBranchId());
			sorting.setStatus(1);
			sorting.setQrCodeId(String.valueOf(DateUtil.getRevisionNumberMills()));
			sorting.setCreatedUser(getUsername());
			sorting.setLatitude(getLatitude());
			sorting.setLongitude(getLongitude());
			utilService.addSorting(sorting);
		}
		return REDIRECT;
	}

	/**
	 * To get edit page and update record
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		if (id != null && !StringUtil.isEmpty(id)) {
			sorting = (Sorting) farmerService.findObjectById(QUERY, new Object[] { Long.valueOf(getId()), 1 });
			if (sorting == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			qtyRejected = sorting.getQtyRejected().toString();
			netQty = sorting.getQtyNet().toString();

			setSelectedVillage(sorting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage() != null
					? String.valueOf(sorting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage().getId())
					: "");
			setSelectedCity(sorting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage() != null
					? String.valueOf(
							sorting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage().getCity().getId())
					: "");
			setSelectedLocality(sorting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage() != null
					? String.valueOf(sorting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage().getCity()
							.getLocality().getId())
					: "");
			setSelectedState(sorting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage() != null
					? String.valueOf(sorting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage().getCity()
							.getLocality().getState().getId())
					: "");
			setSelectedCountry(sorting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage() != null
					? sorting.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage().getCity().getLocality()
							.getState().getCountry().getName()
					: "");
			setCurrentPage(getCurrentPage());
			setSelectedFarm(sorting.getPlanting().getFarmCrops().getFarm() != null
					? String.valueOf(sorting.getPlanting().getFarmCrops().getFarm().getId()) : "");
			setSelectedFarmer(sorting.getPlanting().getFarmCrops().getFarm() != null
					? String.valueOf(sorting.getPlanting().getFarmCrops().getFarm().getFarmer().getId()) : "");
			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getText("sortingupdate"));
		} else {
			if (sorting != null) {
				Sorting s = (Sorting) farmerService.findObjectById(QUERY, new Object[] { sorting.getId(), 1 });
				Sorting oldSrot = new Sorting();
				BeanUtils.copyProperties(oldSrot, s);
				updateSorting(s);
				Planting farmCrops = (Planting) farmerService.findObjectById(FARMCROPS_QUERY,
						new Object[] { sorting.getPlanting().getId(), 1 });
				s.setPlanting(farmCrops);
				// s.setFarmCrops(farmCrops.getFarmCrops());
				s.setTruckType(sorting.getTruckType());
				s.setTruckNo(sorting.getTruckNo());
				s.setDriverName(sorting.getDriverName());
				s.setDriverCont(sorting.getDriverCont());

				s.setQtyHarvested(Double.valueOf(qtyHarvested));
				s.setQtyRejected(Double.valueOf(qtyRejected));
				s.setQtyNet(Double.valueOf(netQty));
				s.setUpdatedDate(new Date());
				s.setUpdatedUser(getUsername());
				utilService.updateSorting(s, oldSrot);
			}
			return REDIRECT;
		}
		return super.execute();
	}

	/**
	 * To delete record
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		String result = null;
		if (id != null && !StringUtil.isEmpty(id)) {
			Sorting s = (Sorting) farmerService.findObjectById(QUERY, new Object[] { Long.valueOf(getId()), 1 });
			if (s == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(s)) {
				s.setStatus(ParentEntity.Active.DELETED.ordinal());
				utilService.deleteSorting(s);
				result = REDIRECT;
			}
		}
		return result;
	}

	/**
	 * To populate harvest details
	 */
	public void populateHarvestDetails() {
		JSONObject jss = null;
		if (selectedFarmCropsId != null && !StringUtil.isEmpty(selectedFarmCropsId)
				&& StringUtil.isLong(selectedFarmCropsId)) {
			jss = getHarvestByFarmCropsId(selectedFarmCropsId);
		}
		sendAjaxResponse(jss);
	}

	public JSONObject getHarvestByFarmCropsId(String farmCropsId) {
		JSONObject jss = new JSONObject();

		CityWarehouse cw = (CityWarehouse) farmerService.findObjectById(
				"FROM CityWarehouse cw WHERE cw.planting.id=? and  cw.coOperative.id is null",
				new Object[] { Long.valueOf(farmCropsId) });

		if (cw != null) {
			jss.put("dateHarvested", DateUtil.convertDateToString(cw.getLastHarvestDate(), getGeneralDateFormat()));
			jss.put("qtyHarvested", cw.getHarvestedWeight().toString());
			jss.put("newblockid", Long.valueOf(farmCropsId));
		}
		return jss;
	}

	@Override
	public void populateFarmerByAuditRequest() throws Exception {
		JSONArray farmerArr = new JSONArray();
		LinkedList<Object> parame = new LinkedList();
		String qryString = "select distinct cw.farmcrops.farm.farmer FROM CityWarehouse cw where cw.coOperative.id is null  and cw.harvestedWeight>0";
		if (getLoggedInDealer() > 0) {
			qryString = "select distinct cw.farmcrops.farm.farmer FROM CityWarehouse cw where cw.coOperative.id is null  and cw.harvestedWeight>0 and cw.farmcrops.exporter.id=?";
			parame.add(Long.valueOf(getLoggedInDealer()));
		}

		if (getSelectedVillage() != null && StringUtil.isLong(getSelectedVillage())
				&& Long.valueOf(selectedVillage) > 0) {
			qryString += " and cw.farmcrops.farm.farmer.village.id=?";
			parame.add(Long.valueOf(selectedVillage));
		}

		List<Farmer> cityWareHouseList = (List<Farmer>) farmerService.listObjectById(qryString, parame.toArray());
		cityWareHouseList.stream().distinct().forEach(cw -> {
			// farmerArr.add(getJSONObject(cw.getId().toString(),
			// cw.getFirstName().toString()));
			farmerArr.add(
					getJSONObject(cw.getId().toString(), (cw.getFarmerId() + " - " + cw.getFirstName().toString())));
		});

		sendAjaxResponse(farmerArr);
	}

	public void populateValidate() throws Exception {
		Map<String, String> errorCodes = new LinkedHashMap<>();
		if (sorting != null) {
			if (sorting.getPlanting().getFarmCrops().getFarm().getFarmer().getId() == null
					|| StringUtil.isEmpty(sorting.getPlanting().getFarmCrops().getFarm().getFarmer().getId())) {
				errorCodes.put("empty.sorting.farmer", getLocaleProperty("empty.sorting.farmer"));
			}
			if (sorting.getPlanting().getFarmCrops().getFarm().getId() == null
					|| StringUtil.isEmpty(sorting.getPlanting().getFarmCrops().getFarm().getId())) {
				errorCodes.put("empty.sorting.farm", getLocaleProperty("empty.sorting.farm"));
			}
			if (sorting.getPlanting().getFarmCrops().getId() == null
					|| StringUtil.isEmpty(sorting.getPlanting().getFarmCrops().getId())) {
				errorCodes.put("empty.sorting.block", getLocaleProperty("empty.sorting.block"));
			}
			/*
			 * if (cropPlanting == null || StringUtil.isEmpty(cropPlanting)) {
			 * errorCodes.put("empty.sorting.cropPlanting",
			 * getLocaleProperty("empty.sorting.cropPlanting")); }
			 */

			if (sorting == null || sorting.getPlanting() == null || sorting.getPlanting().getId() == null) {

				errorCodes.put("empty.planting", "empty.planting");
			}

			/*
			 * if (blockId == null || StringUtil.isEmpty(blockId)) {
			 * errorCodes.put("empty.blockId",
			 * getLocaleProperty("empty.blockId")); }
			 */

			if (dateHarvested == null || StringUtil.isEmpty(dateHarvested)) {
				errorCodes.put("empty.sorting.dateHarvested", getLocaleProperty("empty.sorting.dateHarvested"));
			}
			if (qtyHarvested == null || StringUtil.isEmpty(qtyHarvested)) {
				errorCodes.put("empty.sorting.qtyHarvested", getLocaleProperty("empty.sorting.qtyHarvested"));
			}
			if (qtyRejected == null || StringUtil.isEmpty(qtyRejected)) {
				errorCodes.put("empty.sorting.qtyRejected", getLocaleProperty("empty.sorting.qtyRejected"));
			}
			if (netQty == null || StringUtil.isEmpty(netQty)) {
				errorCodes.put("empty.sorting.netQty", getLocaleProperty("empty.sorting.netQty"));
			} else if (netQty != null && StringUtil.isDouble(netQty) && Double.valueOf(netQty) <= 0) {
				errorCodes.put("sorting.zeronetQty", getLocaleProperty("sorting.zeronetQty"));
			}

			if (qtyRejected != null && netQty != null && qtyHarvested != null) {
				if (!qtyRejected.isEmpty() && !netQty.isEmpty() && !qtyHarvested.isEmpty()) {

					Double sum = Double.valueOf(qtyRejected) + Double.valueOf(netQty);
					if (sum > Double.valueOf(qtyHarvested)) {
						errorCodes.put("empty.sorting.sumOfQty", getLocaleProperty("empty.sorting.sumOfQty"));
					}
				}
			}
			if (qtyRejected != null && netQty != null && qtyHarvested != null) {
				if (!qtyRejected.isEmpty() && !netQty.isEmpty() && !qtyHarvested.isEmpty()) {

					if (Double.valueOf(netQty) > Double.valueOf(qtyHarvested)
							|| Double.valueOf(qtyRejected) > Double.valueOf(qtyHarvested)) {
						errorCodes.put("sorting.maxvalue", getLocaleProperty("sorting.maxvalue"));
					}
				}
			}
			if (sorting.getTruckType() == null || StringUtil.isEmpty(sorting.getTruckType())) {
				errorCodes.put("empty.sorting.truckType", getLocaleProperty("empty.sorting.truckType"));
			}
			if (sorting.getTruckNo() == null || StringUtil.isEmpty(sorting.getTruckNo())) {
				errorCodes.put("empty.sorting.truckNo", getLocaleProperty("empty.sorting.truckNo"));
			}
			if (sorting.getDriverName() == null || StringUtil.isEmpty(sorting.getDriverName())) {
				errorCodes.put("empty.sorting.driverName", getLocaleProperty("empty.sorting.driverName"));
			}
			if (sorting.getDriverCont() == null || StringUtil.isEmpty(sorting.getDriverCont())) {
				errorCodes.put("empty.sorting.driverCont", getLocaleProperty("empty.sorting.driverCont"));
			}

		} else {
			errorCodes.put("empty.fields", getLocaleProperty("empty.fields"));
		}
		printErrorCodes(errorCodes);
	}

	public void updateSorting(Sorting sorting) {
		// CityWarehouse cityWarehouse = (CityWarehouse)
		// farmerService.findCityWarehouseByFarmCrops(sorting.getFarmCrops().getId());
		CityWarehouse cityWarehouse = (CityWarehouse) farmerService.findObjectById(CITY_QUERY,
				new Object[] { sorting.getPlanting().getId() });
		if (cityWarehouse != null) {
			CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
					sorting.getCreatedDate(), 1, sorting.getId(), 0l, cityWarehouse.getLossWeight(),
					cityWarehouse.getHarvestedWeight(), cityWarehouse.getSortedWeight(), 0l, sorting.getQtyHarvested(),
					sorting.getQtyRejected(), sorting.getQtyNet(), 0l,
					cityWarehouse.getHarvestedWeight() + sorting.getQtyRejected() + sorting.getQtyNet(),
					cityWarehouse.getLossWeight() - sorting.getQtyRejected(),
					cityWarehouse.getSortedWeight() - sorting.getQtyNet(), "SORTING EDIT", null);
			cityWarehouseDetail.setCityWarehouse(cityWarehouse);
			cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);

			cityWarehouse.setHarvestedWeight(cityWarehouseDetail.getTotalGrossWeight());
			cityWarehouse.setLossWeight(cityWarehouseDetail.getTotalLoss());
			cityWarehouse.setSortedWeight(cityWarehouseDetail.getTotalSorted());
			cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
			utilService.update(cityWarehouse);
		}

	}

	public void populateState() throws Exception {

		JSONArray stateArr = new JSONArray();
		if (!selectedCountry.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedCountry))) {

			LinkedList<Object> parame = new LinkedList();
			String qry = "select distinct cw.farmcrops.farm.farmer.village.city.locality.state  FROM CityWarehouse cw where cw.coOperative.id is null  and cw.harvestedWeight>0 and cw.farmcrops.farm.farmer.village.city.locality.state.country.name =? ";
			parame.add(selectedCountry);
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry = "select distinct cw.farmcrops.farm.farmer.village.city.locality.state  FROM CityWarehouse cw where cw.coOperative.id is null  and cw.harvestedWeight>0 and cw.farmcrops.farm.farmer.village.city.locality.state.country.name =? and cw.farmcrops.exporter.id=? ";
				parame.add(getLoggedInDealer());
			}

			List<State> states = (List<State>) farmerService.listObjectById(qry, parame.toArray());
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
			String qry = "select distinct cw.farmcrops.farm.farmer.village.city.locality  FROM CityWarehouse cw where cw.coOperative.id is null  and cw.harvestedWeight>0 and cw.farmcrops.farm.farmer.village.city.locality.state.id =? ";
			parame.add(Long.valueOf(selectedState));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry = "select distinct cw.farmcrops.farm.farmer.village.city.locality  FROM CityWarehouse cw where cw.coOperative.id is null  and cw.harvestedWeight>0 and cw.farmcrops.farm.farmer.village.city.locality.state.id =? and cw.farmcrops.exporter.id=? ";
				parame.add(getLoggedInDealer());
			}

			List<Locality> listLocalities = (List<Locality>) farmerService.listObjectById(qry, parame.toArray());
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
			String qry = "select distinct cw.farmcrops.farm.farmer.village.city  FROM CityWarehouse cw where cw.coOperative.id is null  and cw.harvestedWeight>0 and cw.farmcrops.farm.farmer.village.city.locality.id =? ";
			parame.add(Long.valueOf(selectedLocality));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry = "select distinct cw.farmcrops.farm.farmer.village.city  FROM CityWarehouse cw where cw.coOperative.id is null  and cw.harvestedWeight>0 and cw.farmcrops.farm.farmer.village.city.locality.id =? and cw.farmcrops.exporter.id=? ";
				parame.add(getLoggedInDealer());
			}

			List<Municipality> listCity = (List<Municipality>) farmerService.listObjectById(qry, parame.toArray());
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
			String qry = "select distinct cw.farmcrops.farm.farmer.village  FROM CityWarehouse cw where cw.coOperative.id is null  and cw.harvestedWeight>0 and cw.farmcrops.farm.farmer.village.city.id =? ";
			parame.add(Long.valueOf(selectedCity));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry = "select distinct cw.farmcrops.farm.farmer.village  FROM CityWarehouse cw where cw.coOperative.id is null  and cw.harvestedWeight>0 and cw.farmcrops.farm.farmer.village.city.id =? and cw.farmcrops.exporter.id=? ";
				parame.add(getLoggedInDealer());
			}

			List<Village> listVillages = (List<Village>) farmerService.listObjectById(qry, parame.toArray());

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
		String qry = "select distinct cw.farmcrops.farm.farmer.village.city.locality.state.country  FROM CityWarehouse cw where cw.coOperative.id is null  and cw.harvestedWeight>0 and cw.farmcrops.farm.farmer.status =1 ";
		if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
			qry = "select distinct cw.farmcrops.farm.farmer.village.city.locality.state.country  FROM CityWarehouse cw where cw.coOperative.id is null  and cw.harvestedWeight>0 and cw.farmcrops.farm.farmer.status =1 and cw.farmcrops.exporter.id=? ";
			parame.add(getLoggedInDealer());
		}

		List<Country> countryList = (List<Country>) farmerService.listObjectById(qry, parame.toArray());
		for (Country obj : countryList) {
			countryMap.put(obj.getName(), obj.getCode() + "-" + obj.getName());
		}
		return countryMap;
	}// thisis loading country

	public void populateFarm() throws Exception {

		JSONArray farmerArr = new JSONArray();
		String qryString = "select distinct cw.farmcrops.farm FROM CityWarehouse cw where cw.coOperative.id is null  and cw.harvestedWeight>0 and cw.farmcrops.farm.farmer.id=? and cw.farmcrops.farm.status=1";

		List<Farm> cityWareHouseList = (List<Farm>) farmerService.listObjectById(qryString,
				new Object[] { Long.valueOf(selectedFarmer) });
		cityWareHouseList.stream().distinct().forEach(cw -> {
			farmerArr.add(getJSONObject(cw.getId().toString(), cw.getFarmCode() + "-" + cw.getFarmName().toString()));
		});

		sendAjaxResponse(farmerArr);

	}

	public void populatBlock() {

		JSONArray farmerArr = new JSONArray();

		LinkedList<Object> parame = new LinkedList();
		String qry = "select distinct cw.farmcrops FROM CityWarehouse cw where cw.coOperative.id is null  and cw.harvestedWeight>0 and cw.farmcrops.farm.id=? and cw.farmcrops.status=1";
		parame.add(Long.valueOf(selectedFarm));
		if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
			qry = "select distinct cw.farmcrops FROM CityWarehouse cw where cw.coOperative.id is null  and cw.harvestedWeight>0 and cw.farmcrops.farm.id=? and cw.farmcrops.status=1 and cw.farmcrops.exporter.id=?";
			parame.add(Long.valueOf(getLoggedInDealer()));
		}
		List<FarmCrops> growerList = (List<FarmCrops>) farmerService.listObjectById(qry, parame.toArray());
		/*
		 * String qryString =
		 * "select distinct cw.farmcrops FROM CityWarehouse cw where cw.coOperative.id is null  and cw.harvestedWeight>0 and cw.farmcrops.farm.id=? and cw.farmcrops.status=1"
		 * ;
		 * 
		 * List<FarmCrops> cityWareHouseList = (List<FarmCrops>)
		 * farmerService.listObjectById(qryString, new Object[] {
		 * Long.valueOf(selectedFarm) });
		 */
		growerList.stream().distinct().forEach(cw -> {
			farmerArr.add(getJSONObject(cw.getId().toString(), cw.getBlockId() + " - " + cw.getBlockName().toString()));
		});

		sendAjaxResponse(farmerArr);
	}

}
