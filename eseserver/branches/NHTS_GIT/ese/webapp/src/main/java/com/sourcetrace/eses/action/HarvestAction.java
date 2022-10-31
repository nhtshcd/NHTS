package com.sourcetrace.eses.action;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.CityWarehouse;
import com.sourcetrace.eses.entity.CityWarehouseDetail;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Harvest;
import com.sourcetrace.eses.entity.Packhouse;
import com.sourcetrace.eses.entity.ParentEntity;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.Shipment;
import com.sourcetrace.eses.entity.Village;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class HarvestAction extends SwitchAction {

	private static final long serialVersionUID = 1L;

	private String QUERY = "FROM Harvest h WHERE h.id=? AND (h.status=? OR h.status=?)";
	private String FARMCROPS_QUERY = "FROM Planting fc WHERE fc.id=? AND fc.status=?";
	private String CITY_QUERY = "FROM CityWarehouse cw WHERE cw.farmcrops.id=? and  cw.coOperative is  null";

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
	private String date;

	@Getter
	@Setter
	private String redirectContent;

	@Getter
	@Setter
	private Harvest harvest;

	@Getter
	@Setter
	private String qtyHarvested;

	/**
	 * for detail page and list data
	 * 
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		if (id != null && !StringUtil.isEmpty(id) && StringUtil.isLong(id)) {
			harvest = (Harvest) farmerService.findObjectById(QUERY, new Object[] { Long.valueOf(getId()), 1,3 });

			if (harvest == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			date = DateUtil.convertDateToString(harvest.getDate(), getGeneralDateFormat());
			setCurrentPage(getCurrentPage());
			if (harvest.getPackingUnit() != null && !StringUtil.isEmpty(harvest.getPackingUnit())) {
				harvest.setPackingUnit(getCatalgueNameByCode(harvest.getPackingUnit()));

			}
			// if (harvest.getDeliveryType() != null &&
			// !StringUtil.isEmpty(harvest.getDeliveryType())) {
			// harvest.setDeliveryType(getDeliveryTypeList().get(harvest.getDeliveryType()).toString());
			// }

			setCommand(DETAIL);
			return DETAIL;
		} else {
			request.setAttribute(HEADING, getText("harvestDetail"));
			return REDIRECT;
		}
	}

	/**
	 * for create page and insert record
	 * 
	 * @return
	 * @throws Exception
	 */
	public String create() throws Exception {
		if (harvest == null) {
			setCurrentPage(getCurrentPage());
			command = CREATE;
			request.setAttribute(HEADING, "harvestcreate");
			return INPUT;
		} else {
			Planting farmCrops = (Planting) farmerService.findObjectById(FARMCROPS_QUERY,
					new Object[] { harvest.getPlanting().getId(), 1 });
			harvest.setPlanting(farmCrops);
			
			
			harvest.setDate(DateUtil.convertStringToDate(date, getGeneralDateFormat()));
			harvest.setCreatedDate(new Date());
			harvest.setCreatedUser(getUsername());
			harvest.setBranchId(getBranchId());
			harvest.setQtyHarvested(Double.valueOf(qtyHarvested));
			harvest.setStatus(1);
			harvest.setLatitude(getLatitude());
			harvest.setLongitude(getLongitude());
			utilService.saveHarvest(harvest);
		}
		return REDIRECT;
	}

	/**
	 * For update page and update record
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		if (id != null && !StringUtil.isEmpty(id)) {
			harvest = (Harvest) farmerService.findObjectById(QUERY, new Object[] { Long.valueOf(getId()), 1,3 });
			if (harvest == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			date = DateUtil.convertDateToString(harvest.getDate(), getGeneralDateFormat());
			
		
			setSelectedVillage(harvest.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage() != null ? String.valueOf(harvest.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage().getId()) : "");
			setSelectedCity(
					harvest.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage() != null ? String.valueOf(harvest.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage().getCity().getId()) : "");
			setSelectedLocality(harvest.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage() != null
					? String.valueOf(harvest.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage().getCity().getLocality().getId()) : "");
			setSelectedState(harvest.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage() != null
					? String.valueOf(harvest.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage().getCity().getLocality().getState().getId()) : "");
			setSelectedCountry(harvest.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage() != null
					? harvest.getPlanting().getFarmCrops().getFarm().getFarmer().getVillage().getCity().getLocality().getState().getCountry().getName() : "");
			setCurrentPage(getCurrentPage());
			
			setSelectedFarm(harvest.getPlanting().getFarmCrops().getFarm() != null ? String.valueOf(harvest.getPlanting().getFarmCrops().getFarm().getId()) : "");
			setSelectedFarmer(harvest.getPlanting().getFarmCrops().getFarm() != null
					? String.valueOf(harvest.getPlanting().getFarmCrops().getFarm().getFarmer().getId()) : "");
			setCurrentPage(getCurrentPage());
			id = null;
			qtyHarvested = harvest.getQtyHarvested().toString();
			command = UPDATE;
			request.setAttribute(HEADING, getText("harvestupdate"));
		} else {
			if (harvest != null) {
				Harvest harv = (Harvest) farmerService.findObjectById(QUERY,
						new Object[] { Long.valueOf(harvest.getId()), 1,3 });
				Map<CityWarehouse, Double> existock = new HashMap<CityWarehouse, Double>();
				CityWarehouse cityWarehouse = (CityWarehouse) utilService
						.findCityWarehouseByFarmCrops(harv.getPlanting().getId());
				existock.put(cityWarehouse, Double.valueOf(harv.getQtyHarvested()) * -1);

				harv.setQtyHarvested(Double.valueOf(qtyHarvested));

				harv.setDate(DateUtil.convertStringToDate(date, getGeneralDateFormat()));
				Planting farmCrops = (Planting) farmerService.findObjectById(FARMCROPS_QUERY,
						new Object[] { harvest.getPlanting().getId(), 1 });

				harv.setPlanting(farmCrops);

				CityWarehouse currentCt = (CityWarehouse) utilService
						.findCityWarehouseByFarmCrops(harvest.getPlanting().getId());
				if (currentCt == null) {
					existock.put(null, Double.valueOf(harv.getQtyHarvested()));
				} else if (existock.keySet().stream().anyMatch(cta -> cta.getId() == currentCt.getId())) {
					CityWarehouse kk = existock.keySet().stream().filter(cta -> cta.getId() == currentCt.getId())
							.findFirst().get();
					existock.put(cityWarehouse, existock.get(kk) + Double.valueOf(harv.getQtyHarvested()));

				} else {
					existock.put(cityWarehouse, Double.valueOf(harv.getQtyHarvested()));
				}

				harv.setNoStems(harvest.getNoStems());
				// harv.setQtyHarvested(harvest.getQtyHarvested());
				harv.setYieldsHarvested(harvest.getYieldsHarvested());
				harv.setExpctdYieldsVolume(harvest.getExpctdYieldsVolume());
				harv.setNoUnits(harvest.getNoUnits());
				harv.setHarvestType(harvest.getHarvestType());
				harv.setNameHarvester(harvest.getNameHarvester());
				harv.setHarvestEquipment(harvest.getHarvestEquipment());
				harv.setPackingUnit(harvest.getPackingUnit());
				harv.setDeliveryType(harvest.getDeliveryType());
				harv.setProduceId(harvest.getProduceId());
				harv.setObservationPhi(harvest.getObservationPhi());
				harv.setUpdatedDate(new Date());
				harv.setUpdatedUser(getUsername());

				utilService.updateHarvest(harv);
				utilService.updateStocks(harv, existock);
			}
			return REDIRECT;
		}
		return super.execute();
	}

	/**
	 * for delete record
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		String result = null;
		if (id != null && !StringUtil.isEmpty(id)) {
			Harvest harvest = (Harvest) farmerService.findObjectById(QUERY, new Object[] { Long.valueOf(getId()), 1,3 });
			if (harvest == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(harvest)) {
				harvest.setStatus(ParentEntity.Active.DELETED.ordinal());
				Map<CityWarehouse, Double> existock = new HashMap<CityWarehouse, Double>();
				CityWarehouse cityWarehouse = (CityWarehouse) utilService
						.findCityWarehouseByFarmCrops(harvest.getPlanting().getId());
				existock.put(cityWarehouse, Double.valueOf(harvest.getQtyHarvested()) * -1);
				utilService.updateHarvest(harvest);
				utilService.updateStocks(harvest, existock);
				result = REDIRECT;
			}
		}
		return result;
	}

	/**
	 * field value for field PackingUnit
	 * 
	 * @return
	 */
	public Map<String, String> getPackingUnitList() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("1", getText("Crates"));
		map.put("2", getText("Boxes"));
		map.put("3", getText("Nets"));
		return map;
	}
	
	public Map<String, String> getHarvestType() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("1", getText("Count"));
		map.put("0", getText("Weight"));
		return map;
	}

	/**
	 * field value for field Delivery Type
	 * 
	 * @return
	 */
	public Map<String, String> getDeliveryTypeList() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("1", getText("Collection Center"));
		map.put("2", getText("Marketing Agent"));
		return map;
	}

	/**
	 * For validate input data
	 */
	public void populateValidate() {
		Map<String, String> errorCodes = new LinkedHashMap<>();
		if (harvest != null) {
			if (harvest.getPlanting().getFarmCrops().getFarm().getFarmer().getId() == null
					|| StringUtil.isEmpty(harvest.getPlanting().getFarmCrops().getFarm().getFarmer().getId())) {
				errorCodes.put("empty.farmer", getLocaleProperty("empty.farmer"));
			}
			if (harvest.getPlanting().getFarmCrops().getFarm().getId() == null
					|| StringUtil.isEmpty(harvest.getPlanting().getFarmCrops().getFarm().getId())) {
				errorCodes.put("empty.harvest.farm", getLocaleProperty("empty.harvest.farm"));
			}
			
			if (harvest.getPlanting().getId() == null
					|| StringUtil.isEmpty(harvest.getPlanting().getId())) {
				errorCodes.put("empty.planting", getLocaleProperty("empty.planting"));
			}

			if (harvest.getPlanting().getFarmCrops().getId() == null || StringUtil.isEmpty(harvest.getPlanting().getFarmCrops().getId())) {
				errorCodes.put("empty.harvest.block", getLocaleProperty("empty.harvest.block"));
			}else{
				FarmCrops fc = (FarmCrops) farmerService.findObjectById(
						"FROM FarmCrops fc WHERE fc.id=? and fc.exporter.status=1 and fc.exporter.isActive=1 and fc.status=1",
						new Object[] { Long.valueOf(harvest.getPlanting().getFarmCrops().getId()) });
				if(fc == null || StringUtil.isEmpty(fc)){
					errorCodes.put("inactive.packhouse.export", getLocaleProperty("inactive.packhouse.export"));
				}
			}
			

			/*if (harvest.getProduceId() == null || StringUtil.isEmpty(harvest.getProduceId())) {
				errorCodes.put("empty.produceId", getLocaleProperty("empty.produceId"));
			}*/
			
			if (date == null || StringUtil.isEmpty(date)) {
				errorCodes.put("empty.harvest.date", getLocaleProperty("empty.harvest.date"));
			} else {
				if(harvest.getPlanting().getId() != null && !StringUtil.isEmpty(harvest.getPlanting().getId())){
				Date scoutingDate = DateUtil.convertStringToDate(date, getGeneralDateFormat());
				System.out.println(harvest.getPlanting().getId());
				Planting fc = (Planting) farmerService.findObjectById(FARMCROPS_QUERY,
						new Object[] { Long.valueOf(harvest.getPlanting().getId()),1 });
				if (fc != null && fc.getPlantingDate().compareTo(scoutingDate) > 0) {
					errorCodes.put("harvest.invlid.date", "harvest.invlid.date");
				}
				}else{
					errorCodes.put("empty.planting", getLocaleProperty("empty.planting"));
				}
			}
			if (harvest.getHarvestType() == null || StringUtil.isEmpty(harvest.getHarvestType())) {
				errorCodes.put("empty.HarvestType", getLocaleProperty("empty.HarvestType"));
			}else if (harvest.getNoStems() == null || harvest.getNoStems() <=0) {
				errorCodes.put("empty.HarvestType", getLocaleProperty("empty.stems"+harvest.getHarvestType()));
			}
			if (qtyHarvested == null || StringUtil.isEmpty(qtyHarvested)) {
				errorCodes.put("empty.qtyHarvested", getLocaleProperty("empty.qtyHarvested"));
			}else if (qtyHarvested != null && StringUtil.isDouble(qtyHarvested) && Double.valueOf(qtyHarvested) <= 0) {
				errorCodes.put("qtyHarvested.zeronetQty", getLocaleProperty("qtyHarvested.zeronetQty"));
			}
			if (harvest.getYieldsHarvested() == null || StringUtil.isEmpty(harvest.getYieldsHarvested())) {
				errorCodes.put("empty.yieldsHarvested", getLocaleProperty("empty.yieldsHarvested"));
			}
			if (harvest.getExpctdYieldsVolume() == null || StringUtil.isEmpty(harvest.getExpctdYieldsVolume())) {
				errorCodes.put("empty.expctdYieldsVolume", getLocaleProperty("empty.expctdYieldsVolume"));
			}
			if (harvest.getNameHarvester() == null || StringUtil.isEmpty(harvest.getNameHarvester())) {
				errorCodes.put("empty.nameHarvester", getLocaleProperty("empty.nameHarvester"));
			}
			if (harvest.getHarvestEquipment() == null || StringUtil.isEmpty(harvest.getHarvestEquipment())) {
				errorCodes.put("empty.harvestEquipment", getLocaleProperty("empty.harvestEquipment"));
			}
			
			if (harvest.getNoUnits() == null || StringUtil.isEmpty(harvest.getNoUnits())) {
				errorCodes.put("empty.noUnits", getLocaleProperty("empty.noUnits"));
			}
			
			if (harvest.getPackingUnit() == null || StringUtil.isEmpty(harvest.getPackingUnit())) {
				errorCodes.put("empty.packingUnit", getLocaleProperty("empty.packingUnit"));
			}
			
			// if (harvest.getDeliveryType() == null ||
			// StringUtil.isEmpty(harvest.getDeliveryType())) {
			// errorCodes.put("empty.deliveryType",
			// getLocaleProperty("empty.deliveryType"));
			// }
			

		} else {
			errorCodes.put("empty.fields", getLocaleProperty("empty.fields"));
		}
		printErrorCodes(errorCodes);
	}

}
