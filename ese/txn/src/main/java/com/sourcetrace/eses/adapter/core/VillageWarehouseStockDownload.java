package com.sourcetrace.eses.adapter.core;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.CityWarehouse;
import com.sourcetrace.eses.entity.ProductTransfer;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.property.TxnEnrollmentProperties;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

@Component
public class VillageWarehouseStockDownload implements ITxnAdapter {

	private static final Logger LOGGER = Logger.getLogger(VillageWarehouseStockDownload.class.getName());
	@Autowired
	private IFarmerService farmerService;
	@Autowired
	private IUtilService utilService;

	private Map<String, List<String>> villageMap = new LinkedHashMap<String, List<String>>();

	@SuppressWarnings("unchecked")
	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData) {

		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		villageMap.clear();
		LOGGER.info("========== Village Warehouse Stock download Begin ==========");
		String revisionNo = (String) reqData.get(TxnEnrollmentProperties.VILLAGE_WAREHOUSE_STOCK_DOWNLOAD_REVISION_NO);
		if (StringUtil.isEmpty(revisionNo) || !StringUtil.isLong(revisionNo)) {
			revisionNo = "0";
		}
		DecimalFormat formatter = new DecimalFormat("0.00");
		List coOperativeList = new ArrayList();
		List<CityWarehouse> cooperativeWarehouses = new ArrayList<>();
		List<ProductTransfer> ptlist = new ArrayList<>();
		Map<String, ProductTransfer> ptListMap = new HashMap<>();
		Agent ag = (Agent) reqData.get("agentObj");
		if (ag == null) {
			ag = utilService.findAgent(head.getAgentId());
		}
		if (ag.getPackhouse() != null && ag.getPackhouse().getExporter() != null
				&& !ObjectUtil.isEmpty(ag.getPackhouse().getExporter())) {
			cooperativeWarehouses = (List<CityWarehouse>) farmerService.listObjectById(
					"from CityWarehouse p where p.coOperative is null and p.revisionNo>? and  p.planting.farmCrops.exporter.id=? and (p.sortedWeight>0 or p.harvestedWeight>0)",
					new Object[] { Long.valueOf(revisionNo), ag.getPackhouse().getExporter().getId() });
		} else {
			cooperativeWarehouses = (List<CityWarehouse>) farmerService.listObjectById(
					"from CityWarehouse p where p.coOperative is null and p.revisionNo>? and  p.planting.farmCrops.exporter.id=? and (p.sortedWeight>0 or p.harvestedWeight>0)",
					new Object[] { Long.valueOf(revisionNo), ag.getExporter().getId() });
		}

		if (cooperativeWarehouses == null) {
			cooperativeWarehouses = new ArrayList<>();
		}

		if (ag != null && (ag.getAgentType().getCode().equals("02") || ag.getAgentType().getCode().equals("03"))
				&& ag.getPackhouse() != null && ag.getExporter() != null) {

			cooperativeWarehouses.addAll((List<CityWarehouse>) farmerService.listObjectById(
					"from CityWarehouse p where p.revisionNo>? and p.coOperative.id =? and p.sortedWeight>0",
					new Object[] { Long.valueOf(revisionNo), ag.getPackhouse().getId() }));
		}
		if (!ObjectUtil.isListEmpty(cooperativeWarehouses)) {
			List<String> list = cooperativeWarehouses.stream().filter(a -> a.getStockType() == 4)
					.map(a -> a.getBatchNo()).collect(Collectors.toList());
			if (!ObjectUtil.isListEmpty(list)) {
				ptlist = utilService.listOfProductTransferByCityWareHouseBatches(list);
				if (!ObjectUtil.isListEmpty(ptlist)) {
					ptListMap = ptlist.stream().collect(Collectors.toMap(p -> p.getBatchNo(), p -> p));
				}
			}

			for (CityWarehouse cityWarehouse : cooperativeWarehouses) {
				Map<String, String> villageData = new HashMap<>();
				villageData.put(TxnEnrollmentProperties.BATCH_NO,
						cityWarehouse.getBatchNo() != null ? cityWarehouse.getBatchNo() : "");
				villageData.put(TransactionProperties.WAREHOUSE_CODE,
						cityWarehouse.getCoOperative() != null ? cityWarehouse.getCoOperative().getCode() : "");
				villageData.put(TransactionProperties.WAREHOUSE_NAME,
						cityWarehouse.getCoOperative() != null ? cityWarehouse.getCoOperative().getName() : "");

				/*
				 * villageData.put(TxnEnrollmentProperties.
				 * WAREHOUSE_PRODUCT_CODE, cityWarehouse.getFarmcrops() != null
				 * &&
				 * !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getVariety())
				 * ? cityWarehouse.getPlanting().getVariety().getCode() : "");
				 */
				villageData.put(TxnEnrollmentProperties.WAREHOUSE_PRODUCT_CODE,
						cityWarehouse.getPlanting() != null && cityWarehouse.getPlanting().getFarmCrops() != null
								&& !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getVariety())
										? cityWarehouse.getPlanting().getVariety().getCode() : "");

				/*
				 * villageData.put(TxnEnrollmentProperties.PRODUCT_NAME,
				 * cityWarehouse.getFarmcrops() != null &&
				 * !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getVariety())
				 * ? cityWarehouse.getPlanting().getVariety().getName() : "");
				 */
				/*
				 * villageData.put(TxnEnrollmentProperties.BLOCK_ID,
				 * cityWarehouse.getFarmcrops() != null &&
				 * !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getVariety())
				 * ? cityWarehouse.getFarmcrops().getBlockId() : "");
				 */
				villageData.put(TxnEnrollmentProperties.PRODUCT_NAME,
						cityWarehouse.getPlanting() != null && cityWarehouse.getPlanting().getFarmCrops() != null
								&& !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getVariety())
										? cityWarehouse.getPlanting().getVariety().getName() : "");
				villageData.put(TxnEnrollmentProperties.BLOCK_ID,
						cityWarehouse.getPlanting() != null && cityWarehouse.getPlanting().getFarmCrops() != null
								&& !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getVariety())
										? cityWarehouse.getPlanting().getFarmCrops().getBlockId() : "");

				villageData.put(TxnEnrollmentProperties.FARMER_ID,
						cityWarehouse.getPlanting() != null && cityWarehouse.getPlanting().getFarmCrops() != null
								&& !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getFarmCrops().getFarm())
										? cityWarehouse.getPlanting().getFarmCrops().getFarm().getFarmer().getFarmerId()
										: "");

				villageData.put(TxnEnrollmentProperties.FARMER_NAME, cityWarehouse.getPlanting() != null
						&& cityWarehouse.getPlanting().getFarmCrops() != null
						&& !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getFarmCrops().getFarm())
								? cityWarehouse.getPlanting().getFarmCrops().getFarm().getFarmer().getFirstName() + ""
										+ (cityWarehouse.getPlanting().getFarmCrops().getFarm().getFarmer()
												.getLastName() != null
														? cityWarehouse.getPlanting().getFarmCrops().getFarm()
																.getFarmer().getLastName()
														: "")
								: "");
				/*
				 * villageData.put(TxnEnrollmentProperties.NATIONALID,
				 * cityWarehouse.getFarmcrops() != null &&
				 * !ObjectUtil.isEmpty(cityWarehouse.getFarmcrops().getFarm()) ?
				 * cityWarehouse.getFarmcrops().getFarm().getFarmer().getNid() :
				 * "");
				 * 
				 * villageData.put(TxnEnrollmentProperties.FARMER_KRA,
				 * cityWarehouse.getFarmcrops() != null &&
				 * !ObjectUtil.isEmpty(cityWarehouse.getFarmcrops().getFarm()) ?
				 * cityWarehouse.getFarmcrops().getFarm().getFarmer().getKraPin(
				 * ) : "");
				 */

				villageData.put(TxnEnrollmentProperties.STATE_NAME,
						cityWarehouse.getPlanting() != null && cityWarehouse.getPlanting().getFarmCrops() != null
								&& !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getFarmCrops().getFarm())
								&& !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getFarmCrops().getFarm().getFarmer())
										? cityWarehouse.getPlanting().getFarmCrops().getFarm().getVillage().getCity()
												.getLocality().getState().getName()
										: "");
				villageData.put(TxnEnrollmentProperties.STATE_CODE,
						cityWarehouse.getPlanting() != null && cityWarehouse.getPlanting().getFarmCrops() != null
								&& !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getFarmCrops().getFarm())
								&& !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getFarmCrops().getFarm().getFarmer())
										? cityWarehouse.getPlanting().getFarmCrops().getFarm().getVillage().getCity()
												.getLocality().getState().getCode()
										: "");

				villageData.put("stType",
						cityWarehouse.getStockType() != null ? String.valueOf(cityWarehouse.getStockType()) : "0");

				villageData.put(TxnEnrollmentProperties.FARM_NAME,
						cityWarehouse.getPlanting() != null && cityWarehouse.getPlanting().getFarmCrops() != null
								&& !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getFarmCrops().getFarm())
										? cityWarehouse.getPlanting().getFarmCrops().getFarm().getFarmName() : "");

				villageData.put(TxnEnrollmentProperties.FARM_CODE,
						cityWarehouse.getPlanting() != null && cityWarehouse.getPlanting().getFarmCrops() != null
								&& !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getFarmCrops().getFarm())
										? cityWarehouse.getPlanting().getFarmCrops().getFarm().getFarmCode() : "");

				/*
				 * villageData.put("blockname", cityWarehouse.getFarmcrops() !=
				 * null &&
				 * !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getVariety())
				 * ? cityWarehouse.getFarmcrops().getBlockName() : "");
				 * villageData.put("plantingId", cityWarehouse.getFarmcrops() !=
				 * null &&
				 * !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getVariety())
				 * ? cityWarehouse.getPlanting().getPlantingId() : "");
				 */
				villageData.put("blockname",
						cityWarehouse.getPlanting() != null && cityWarehouse.getPlanting().getFarmCrops() != null
								&& !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getVariety())
										? cityWarehouse.getPlanting().getFarmCrops().getBlockName() : "");
				villageData.put("plantingId",
						cityWarehouse.getPlanting() != null && cityWarehouse.getPlanting().getFarmCrops() != null
								&& !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getVariety())
										? cityWarehouse.getPlanting().getPlantingId() : "");
				villageData.put(TxnEnrollmentProperties.QR_BATCH_NO,
						cityWarehouse.getQrCodeId() != null && !StringUtil.isEmpty(cityWarehouse.getQrCodeId())
								? cityWarehouse.getQrCodeId() : "");
				villageData.put("pkBatchNo",
						cityWarehouse.getPackBatch() != null && !StringUtil.isEmpty(cityWarehouse.getPackBatch())
								? cityWarehouse.getPackBatch() : "");
				villageData.put("lastHarDate",
						cityWarehouse.getPlanting() != null && cityWarehouse.getPlanting().getFarmCrops() != null
								&& !ObjectUtil.isEmpty(cityWarehouse.getLastHarvestDate())
										? DateUtil.convertDateToString(cityWarehouse.getLastHarvestDate(),
												DateUtil.DATABASE_DATE_FORMAT)
										: cityWarehouse.getCreatedDate() != null
												&& !ObjectUtil.isEmpty(cityWarehouse.getCreatedDate())
														? DateUtil.convertDateToString(cityWarehouse.getCreatedDate(),
																DateUtil.DATABASE_DATE_FORMAT)
														: "");

				/*
				 * villageData.put(TxnEnrollmentProperties.VARIETY_CODE,
				 * cityWarehouse.getFarmcrops() != null &&
				 * !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getVariety())
				 * ? cityWarehouse.getPlanting().getGrade().getCode() : "");
				 * 
				 * villageData.put("varietyName", cityWarehouse.getFarmcrops()
				 * != null &&
				 * !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getVariety())
				 * ? cityWarehouse.getPlanting().getGrade().getName() : "");
				 */
				villageData.put(TxnEnrollmentProperties.VARIETY_CODE,
						cityWarehouse.getPlanting() != null && cityWarehouse.getPlanting().getFarmCrops() != null
								&& !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getVariety())
										? cityWarehouse.getPlanting().getGrade().getCode() : "");

				villageData.put("varietyName",
						cityWarehouse.getPlanting() != null && cityWarehouse.getPlanting().getFarmCrops() != null
								&& !ObjectUtil.isEmpty(cityWarehouse.getPlanting().getVariety())
										? cityWarehouse.getPlanting().getGrade().getName() : "");

				villageData.put("createdDate",
						cityWarehouse.getCreatedDate() != null && !ObjectUtil.isEmpty(cityWarehouse.getCreatedDate())
								? DateUtil.convertDateToString(cityWarehouse.getCreatedDate(),
										DateUtil.DATABASE_DATE_FORMAT)
								: "");

				villageData.put("sortedWt",
						cityWarehouse.getSortedWeight() != null && cityWarehouse.getSortedWeight() > 0
								? formatter.format(cityWarehouse.getSortedWeight()) : "0");
				villageData.put("lossWt", cityWarehouse.getLossWeight() != null && cityWarehouse.getLossWeight() > 0
						? formatter.format(cityWarehouse.getLossWeight()) : "0");
				villageData.put(TxnEnrollmentProperties.GROSS_WEIGHT,
						formatter.format(cityWarehouse.getSortedWeight()));
				villageData.put("harvestWt", formatter.format(cityWarehouse.getHarvestedWeight()));

				if (cityWarehouse.getBatchNo() != null && cityWarehouse.getStockType().equals(4)) {
					if (ptListMap.containsKey(cityWarehouse.getBatchNo())) {
						ProductTransfer temppt = ptListMap.get(cityWarehouse.getBatchNo());
						if (temppt != null) {
							villageData.put("ptDate", temppt.getDate() != null && !ObjectUtil.isEmpty(temppt.getDate())
									? DateUtil.convertDateToString(temppt.getDate(), DateUtil.DATABASE_DATE_FORMAT)
									: "");
							villageData.put("ptTransferFromCode",
									temppt.getTransferFrom() != null && !StringUtil.isEmpty(temppt.getTransferFrom())
											? temppt.getTransferFrom().getCode() : "");
							villageData.put("ptTransferFromName",
									temppt.getTransferFrom() != null && !StringUtil.isEmpty(temppt.getTransferFrom())
											? temppt.getTransferFrom().getName() : "");
							villageData.put("ptTransferToCode",
									temppt.getTransferTo() != null && !StringUtil.isEmpty(temppt.getTransferTo())
											? temppt.getTransferTo().getCode() : "");
							villageData.put("ptTransferToName",
									temppt.getTransferTo() != null && !StringUtil.isEmpty(temppt.getTransferTo())
											? temppt.getTransferTo().getName() : "");
							villageData.put("ptTruckNo",
									temppt.getTruckNo() != null && !StringUtil.isEmpty(temppt.getTruckNo())
											? temppt.getTruckNo() : "");
							villageData.put("ptDriverName",
									temppt.getDriverName() != null && !StringUtil.isEmpty(temppt.getDriverName())
											? temppt.getDriverName() : "");
							villageData.put("ptDriverLicenseNo",
									temppt.getDriverLicenseNumber() != null
											&& !StringUtil.isEmpty(temppt.getDriverLicenseNumber())
													? temppt.getDriverLicenseNumber() : "");
						}
					}
				} else {
					villageData.put("ptDate", "");
					villageData.put("ptTransferFromCode", "");
					villageData.put("ptTransferFromName", "");
					villageData.put("ptTransferToCode", "");
					villageData.put("ptTransferToName", "");
					villageData.put("ptTruckNo", "");
					villageData.put("ptDriverName", "");
					villageData.put("ptDriverLicenseNo", "");
				}

				coOperativeList.add(villageData);
				revisionNo = String.valueOf(cityWarehouse.getRevisionNo());

			}

		}

		// Build response map object
		Map resp = new LinkedHashMap();
		resp.put(TxnEnrollmentProperties.WAREHOUSE_STOCK_LIST, coOperativeList);
		resp.put(TxnEnrollmentProperties.VILLAGE_WAREHOUSE_STOCK_DOWNLOAD_REVISION_NO, revisionNo);
		LOGGER.info("========== Village Warehouse Stock download End ==========");

		return resp;

	}

}
