package com.sourcetrace.eses.adapter.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.CityWarehouse;
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
public class ProductReceptionAdapter implements ITxnAdapter {

	@Autowired
	private IFarmerService farmerService;

	@Autowired
	private IUtilService utilService;

 
	@SuppressWarnings("unchecked")
	@Override
	public Map<?, ?> processJson(Map reqData) {
		Map insResponse = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);

		String receiptId = (String) reqData.get("receiptId");
		String tDate = (String) reqData.get("tDate");
		String from = (String) reqData.get("from");
		String to = (String) reqData.get("To");
		String truckNo = (String) reqData.get("truckNo");
		String drName = (String) reqData.get("drName");
		String license = (String) reqData.get("license");
		String transferId = (String) reqData.get("transferId");
		String batchNoTr= (String) reqData.get("batNo");

		ProductTransfer ptExist = (ProductTransfer) farmerService.findObjectById(
				" from ProductTransfer pt where pt.msgNo=?  and pt.status<>0 and pt.type=1", new Object[] { head.getMsgNo() });
		if (ptExist != null) {
			return insResponse;
		}
		
		ProductTransfer productReception = new ProductTransfer();
		
		ArrayList jsonArr = (ArrayList) reqData.get("receptionList");
		if (jsonArr != null && jsonArr.size() > 0) {
			if (productReception.getProductTransferDetails() == null) {
				productReception.setProductTransferDetails(new HashSet<>());
			}
			jsonArr.stream().forEach(uu -> {
				try {
					LinkedHashMap jsonObj = (LinkedHashMap) uu;
					
					ProductTransferDetail ptd = new ProductTransferDetail();
					
					    //String batchNo = (String) jsonObj.get("batchNo");
					    String batchNo = (String) receiptId;
					    String plantingId = (String) jsonObj.get("plantingId");
						String blockId = (String) jsonObj.get("blockId");
						String product = (String) jsonObj.get("product");
						String variety = (String) jsonObj.get("variety");
						String transWt = (String) jsonObj.get("transWt");
						String recWt = (String) jsonObj.get("recWt");
						
					Planting planting = (Planting) farmerService.findObjectById(
								" from Planting fc where fc.plantingId=?  and fc.status=1", new Object[] { plantingId });
					if (planting == null) {
							throw new TxnFault(ITxnErrorCodes.PLANTING_DOES_NOT_EXIST);
					}
					
					CityWarehouse cw = (CityWarehouse) farmerService.findObjectById(""
							+ "FROM CityWarehouse cw where cw.batchNo=? and cw.planting.id=?"
							+ " and cw.farmcrops.id=? and cw.stockType=? and cw.sortedWeight>0 and cw.isDelete=0",
							new Object[] {batchNo,planting.getId(),planting.getFarmCrops().getId(),
									CityWarehouse.Stock_type.PRODUCT_TRANSFER.ordinal() });
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
					ptd.setTransferredWeight(Double.valueOf(transWt));
					ptd.setReceivedWeight(Double.valueOf(recWt));
					ptd.setStatus(1);
					ptd.setProductTransfer(productReception);
					productReception.getProductTransferDetails().add(ptd);
				} catch (Exception e) {
					throw new TxnFault(ITxnErrorCodes.PLANTING_DOES_NOT_EXIST);
				}

			});

		}

		if(receiptId!=null && !StringUtil.isEmpty(receiptId)){
		ProductTransfer temppt = (ProductTransfer) farmerService
					.findObjectById(" from ProductTransfer pt where pt.batchNo=?  and pt.status=1 and pt.type=0", new Object[] { receiptId });			
					
		if(temppt!=null){
		productReception.setTransferReceiptID(receiptId);
		
		productReception.setDate(temppt.getDate());
		
		if(temppt!=null && !StringUtil.isEmpty(temppt.getTransferFrom())){
				productReception.setTransferFrom(temppt.getTransferFrom());
		}
		if(temppt!=null && !StringUtil.isEmpty(temppt.getTransferTo())){
				productReception.setTransferTo(temppt.getTransferTo());
		}
		productReception.setTruckNo(temppt.getTruckNo());
		productReception.setDriverName(temppt.getDriverName());
		productReception.setDriverLicenseNumber(temppt.getDriverLicenseNumber());
		}}
		
		//productReception.setBatchNo(DateUtil.getDateTimWithMinsec());
		productReception.setBatchNo(batchNoTr);
		
		productReception.setBranchId(head.getBranchId());
		productReception.setStatus(1);
		productReception.setType(ProductTransfer.type.PRODUCT_RECEPTION.ordinal());
		productReception.setCreatedDate(new Date());
		productReception.setLatitude(head.getLat());
		productReception.setLongitude(head.getLon());
		productReception.setCreatedUser(head.getAgentId());
		productReception.setMsgNo(head.getMsgNo());
		productReception.setIpAddr(head.getIpAddress());
		
		utilService.saveProductReception(productReception);
		return insResponse;
	}
}