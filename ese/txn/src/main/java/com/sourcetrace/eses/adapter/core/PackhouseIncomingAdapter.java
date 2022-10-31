package com.sourcetrace.eses.adapter.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.CityWarehouse;
import com.sourcetrace.eses.entity.CityWarehouseDetail;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.Packhouse;
import com.sourcetrace.eses.entity.PackhouseIncoming;
import com.sourcetrace.eses.entity.PackhouseIncomingDetails;
import com.sourcetrace.eses.interceptor.ITxnErrorCodes;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.exception.SwitchException;
import com.sourcetrace.eses.txn.exception.TxnFault;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.StringUtil;

@Component
public class PackhouseIncomingAdapter implements ITxnAdapter {

	@Autowired
	private IFarmerService farmerService;

	@Autowired
	private IUtilService utilService;

 
	@SuppressWarnings("unchecked")
	@Override
	public Map<?, ?> processJson(Map reqData) {
		Map insResponse = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);

		String date = (String) reqData.get("date");
		String packhouse = (String) reqData.get("packhouse");
		String totWt = (String) reqData.get("totWt");
		String trType = (String) reqData.get("trType");
		String trNo = (String) reqData.get("trNo");
		String drName = (String) reqData.get("drName");
		String batchNo = (String) reqData.get("batchNo");
		String drCt = (String) reqData.get("drCt");
		
		PackhouseIncoming piExist = (PackhouseIncoming) farmerService.findObjectById(
				" from PackhouseIncoming fc where fc.msgNo=?  and fc.status<>2", new Object[] { head.getMsgNo() });
		if (piExist != null) {
			return insResponse;
		}
		
		
	 

		
		PackhouseIncoming packhouseIncoming = new PackhouseIncoming();
		
		ArrayList jsonArr = (ArrayList) reqData.get("productList");
		if (jsonArr != null && jsonArr.size() > 0) {
			if (packhouseIncoming.getPackhouseIncomingDetails() == null) {
				packhouseIncoming.setPackhouseIncomingDetails(new HashSet<>());
			}
			jsonArr.stream().forEach(uu -> {
				try {
					LinkedHashMap jsonObj = (LinkedHashMap) uu;
					String plantingId = (String) jsonObj.get("plantingId");
					
					Planting planting = (Planting) farmerService.findObjectById(
							" from Planting fc where fc.plantingId=?  and fc.status=1", new Object[] { plantingId });
					if (planting == null) {
						throw new TxnFault(ITxnErrorCodes.PLANTING_DOES_NOT_EXIST);
					}

					String prodCode = (String) jsonObj.get("prodCode");
					String VarietyCode = (String) jsonObj.get("VarietyCode");
					String trWt = (String) jsonObj.get("trWt");
					String recWt = (String) jsonObj.get("recWt");
					String uom = (String) jsonObj.get("uom");
					String noOfBag = (String) jsonObj.get("noOfBag");
					String remingWt = (String) jsonObj.get("remingWt");
					String QRCodeUnq = (String) jsonObj.get("QRCodeUnq");
					String lossWt = (String) jsonObj.get("lossWt");
					String sortId = (String) jsonObj.get("sortingId");
					PackhouseIncomingDetails pi = new PackhouseIncomingDetails();
					
					if (sortId !=null && !StringUtil.isEmpty(sortId)) {
						pi.setQrCodeId(sortId);
						PackhouseIncomingDetails pckinc = (PackhouseIncomingDetails) farmerService.findObjectById(
								"FROM PackhouseIncomingDetails where packhouseIncoming.status!=2 and qrCodeId=?",
								new Object[] { sortId });
						if(pckinc != null && !StringUtil.isEmpty(pckinc)){
							pi.setStatus(0);
						}else{
							pi.setStatus(1);
						}
					}else{
						pi.setQrCodeId(String.valueOf(DateUtil.getRevisionNumber()));
						pi.setStatus(1);
					}

					pi.setFarmcrops(planting.getFarmCrops());
					pi.setPlanting(planting);
					pi.setNoUnits(Integer.valueOf(noOfBag));
					pi.setPackhouseIncoming(packhouseIncoming);
					pi.setReceivedUnits(uom);
					pi.setReceivedWeight(Double.valueOf(recWt));
					pi.setTransferWeight(Double.valueOf(trWt));
					pi.setTotalWeight(Double.valueOf(lossWt));
					pi.setSortingId(QRCodeUnq);
					CityWarehouse cityWarehouse = (CityWarehouse) farmerService.findObjectById(
							"FROM CityWarehouse where  qrCodeId=? and planting.id=? and stockType=? and isDelete=0",
							new Object[] { sortId, planting.getId(), CityWarehouse.Stock_type.HARVEST_STOCK.ordinal() });
					if (cityWarehouse == null) {
						cityWarehouse = new CityWarehouse();
						cityWarehouse.setLossWeight(0d);
						cityWarehouse.setSortedWeight(0d);

						cityWarehouse.setFarmcrops(planting.getFarmCrops());
						cityWarehouse.setPlanting(planting);
						cityWarehouse.setHarvestedWeight(0d);
						cityWarehouse.setSortedWeight(0d);
						cityWarehouse.setLossWeight(0d);
						cityWarehouse.setCreatedDate(packhouseIncoming.getCreatedDate());
						cityWarehouse.setCityWarehouseDetails(new HashSet<>());
						cityWarehouse.setCreatedUser(packhouseIncoming.getCreatedUser());
						cityWarehouse.setCoOperative(null);
						cityWarehouse.setBranchId(packhouseIncoming.getBranchId());
						cityWarehouse.setIsDelete(0);
						cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
						cityWarehouse.setStockType(1);
						cityWarehouse.setQrCodeId(pi.getQrCodeId());
						utilService.save(cityWarehouse);
					}
					pi.setCw(cityWarehouse);
					packhouseIncoming.getPackhouseIncomingDetails().add(pi);
				} catch (Exception e) {
					throw new TxnFault(ITxnErrorCodes.PLANTING_DOES_NOT_EXIST);
				}

			});

		}

		Packhouse packhouses = (Packhouse) farmerService
				.findObjectById(" from Packhouse ph where ph.id=?  and ph.status=1", new Object[] { Long.valueOf(packhouse) });

		packhouseIncoming.setPackhouse(packhouses);
		packhouseIncoming.setStatus(1);
		packhouseIncoming.getPackhouseIncomingDetails().stream().filter(uu -> uu != null && uu.getStatus() !=null && uu.getStatus().equals(0)).forEach(uu -> {
			packhouseIncoming.setStatus(4);
		});
		packhouseIncoming.setTotalWeight(Double.valueOf(totWt));
		packhouseIncoming.setTruckType(trType);
		packhouseIncoming.setBatchNo(batchNo);
		packhouseIncoming.setTruckNo(trNo);
		packhouseIncoming.setDriverName(drName);
		packhouseIncoming.setDriverCont(drCt);
		packhouseIncoming.setOffLoadingDate(DateUtil.convertStringToDate(date, DateUtil.DATABASE_DATE_FORMAT));
		packhouseIncoming.setBranchId(head.getBranchId());
		
		packhouseIncoming.setMsgNo(head.getMsgNo());
		utilService.saveIncoming(packhouseIncoming);

		return insResponse;
	}
}