package com.sourcetrace.eses.adapter.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.CityWarehouse;
import com.sourcetrace.eses.entity.ExporterRegistration;
import com.sourcetrace.eses.entity.Packhouse;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.ProductTransfer;
import com.sourcetrace.eses.entity.ProductTransferDetail;
import com.sourcetrace.eses.interceptor.ITxnErrorCodes;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.exception.TxnFault;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.StringUtil;

@Component
public class ProductTransferAdapter implements ITxnAdapter {

	@Autowired
	private IFarmerService farmerService;

	@Autowired
	private IUtilService utilService;

 
	@SuppressWarnings("unchecked")
	@Override
	public Map<?, ?> processJson(Map reqData) {
		Map insResponse = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);

		String tDate = (String) reqData.get("tDate");
		String exporter = (String) reqData.get("exporter");
		String from = (String) reqData.get("from");
		String to = (String) reqData.get("To");
		String truckNo = (String) reqData.get("truckNo");
		String drName = (String) reqData.get("drName");
		String license = (String) reqData.get("license");
		String transferId = (String) reqData.get("transferId");

		ProductTransfer ptExist = (ProductTransfer) farmerService.findObjectById(
				" from ProductTransfer pt where pt.msgNo=?  and pt.status<>0 and pt.type=0", new Object[] { head.getMsgNo() });
		if (ptExist != null) {
			return insResponse;
		}
		
		ProductTransfer productTransfer = new ProductTransfer();
		ArrayList jsonArr = (ArrayList) reqData.get("transferList");
		if (jsonArr != null && jsonArr.size() > 0) {
			if (productTransfer.getProductTransferDetails() == null) {
				productTransfer.setProductTransferDetails(new HashSet<>());
			}
			jsonArr.stream().forEach(uu -> {
				try {
					LinkedHashMap jsonObj = (LinkedHashMap) uu;
					
					ProductTransferDetail ptd = new ProductTransferDetail();
					
					 String batchNo = (String) jsonObj.get("batchNo");
						String blockId = (String) jsonObj.get("blockId");
						String product = (String) jsonObj.get("product");
						String variety = (String) jsonObj.get("variety");
						String transWt = (String) jsonObj.get("transWt");
						String avlWT = (String) jsonObj.get("avlWT");
						String plantingId = (String) jsonObj.get("plantingId");
						
					Planting planting = (Planting) farmerService.findObjectById(
								" from Planting fc where fc.plantingId=?  and fc.status=1", new Object[] { plantingId });
					if (planting == null) {
							throw new TxnFault(ITxnErrorCodes.PLANTING_DOES_NOT_EXIST);
					}
					
					CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse cw where cw.batchNo=? and cw.planting.id=? and cw.stockType=? and cw.isDelete=0",
							new Object[] { batchNo,planting.getId(),CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal()});
							
							
					if(planting.getPlantingId() == cw.getPlanting().getPlantingId()){
						cw.setSortedWeight(0.00);
					}
					cw.setFarmcrops(planting.getFarmCrops());
					cw.setPlanting(planting);
					ptd.setCtt(cw);
					
					ptd.setPlanting(planting);
					ptd.setBlockId(planting.getFarmCrops());
					ptd.setBatchNo(batchNo);
					ptd.setProduct(product);
					ptd.setVariety(variety);
					ptd.setAvailableWeight(Double.valueOf(avlWT));
					ptd.setTransferredWeight(Double.valueOf(transWt));
					ptd.setStatus(1);
					ptd.setProductTransfer(productTransfer);
					productTransfer.getProductTransferDetails().add(ptd);
				} catch (Exception e) {
					throw new TxnFault(ITxnErrorCodes.PLANTING_DOES_NOT_EXIST);
				}

			});

		}

		productTransfer.setDate(DateUtil.convertStringToDate(tDate, DateUtil.DATABASE_DATE_FORMAT));
		if(exporter!=null && !StringUtil.isEmpty(exporter) && StringUtil.isLong(exporter)){
			ExporterRegistration ex=utilService.findExportRegById(Long.valueOf(exporter));
			if(ex!=null){
				productTransfer.setExporter(ex);
			}
		}
		
		if(from!=null && !StringUtil.isEmpty(from) && exporter!=null && !StringUtil.isEmpty(exporter)){
			//Packhouse fromP=utilService.findWarehouseByCode(from);
			Packhouse fromP = (Packhouse) farmerService
					.findObjectById("from Packhouse ph where ph.code=? and ph.status=1 and ph.exporter.id=?", new Object[] { from,Long.valueOf(exporter) });
			if(fromP!=null){
				productTransfer.setTransferFrom(fromP);
			}else{
				throw new TxnFault(ITxnErrorCodes.EMPTY_TRANSFER_FROM);
			}
		}
		if(to!=null && !StringUtil.isEmpty(to) && exporter!=null && !StringUtil.isEmpty(exporter)){
			//Packhouse toP=utilService.findWarehouseByCode(to);
			Packhouse toP = (Packhouse) farmerService
					.findObjectById("from Packhouse ph where ph.code=? and ph.status=1 and ph.exporter.id=?", new Object[] { to,Long.valueOf(exporter) });
			if(toP!=null){
				productTransfer.setTransferTo(toP);
			}else{
				throw new TxnFault(ITxnErrorCodes.EMPTY_TRANSFER_TO);
			}
		}
		productTransfer.setTruckNo(truckNo);
		productTransfer.setDriverName(drName);
		productTransfer.setDriverLicenseNumber(license);
		productTransfer.setBatchNo(transferId);
		
		productTransfer.setBranchId(head.getBranchId());
		productTransfer.setStatus(1);
		productTransfer.setType(ProductTransfer.type.PRODUCT_TRANSFER.ordinal());
		productTransfer.setCreatedDate(new Date());
		productTransfer.setLatitude(head.getLat());
		productTransfer.setLongitude(head.getLon());
		productTransfer.setCreatedUser(head.getAgentId());
		productTransfer.setMsgNo(head.getMsgNo());
		productTransfer.setIpAddr(head.getIpAddress());
		
		utilService.saveProductTransfer(productTransfer);

		return insResponse;
	}
}