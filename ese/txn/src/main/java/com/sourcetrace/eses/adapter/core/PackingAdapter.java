package com.sourcetrace.eses.adapter.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.CityWarehouse;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Packhouse;
import com.sourcetrace.eses.entity.Packing;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.PackingDetail;
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
public class PackingAdapter  implements ITxnAdapter {

	@Autowired
	private IFarmerService farmerService;

	@Autowired
	private IUtilService utilService;

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public Map<?, ?> processJson(Map reqData) {
		Map insResponse = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);

		String packDate = (String) reqData.get("packDate");
		String packhouse = (String) reqData.get("packhouse");
		String packerName = (String) reqData.get("packerName");
		String lotNo = (String) reqData.get("lotNo");
		Packing packing=new Packing();
		

		Packing pi = (Packing) farmerService.findObjectById(
				" from Packing fc where fc.msgNo=?  and fc.status<>0", new Object[] { head.getMsgNo() });
		if (pi != null) {
			return insResponse;
		}
		
		
		
		ArrayList jsonArr = (ArrayList) reqData.get("packingList");
		packing.setPackingDetails(new HashSet<>());
		if (jsonArr != null && jsonArr.size() > 0) {

			jsonArr.stream().forEach(uu -> {
				try {
					

					LinkedHashMap jsonObj = (LinkedHashMap) uu;
					
					String farmerId = (String) jsonObj.get("farmerId");
					
					String farmId = (String) jsonObj.get("farmId");
					String blockId = (String) jsonObj.get("blockId");
					String resBatchNo = (String) jsonObj.get("resBatchNo");
					String pCode = (String) jsonObj.get("pCode");
					String vCode = (String) jsonObj.get("vCode");
					String avlWt = (String) jsonObj.get("avlWt");
					String packWt = (String) jsonObj.get("packWt");
					String farmbbDateId = (String) jsonObj.get("bbDate");
					String price = (String) jsonObj.get("price");
					String productVal = (String) jsonObj.get("productValue");
					String rejectWts = (String) jsonObj.get("rejectWt");
					String plantingId = (String) jsonObj.get("plantingId");
					String QRCodeUnq = (String) jsonObj.get("QRCodeUnq");
					PackingDetail packingdetail = new PackingDetail();
					System.out.println(blockId+" "+farmId);
					Planting fc = (Planting) farmerService.findObjectById(
							" from Planting fc where fc.plantingId=?  and fc.status=1",
							new Object[] { plantingId });

					if (fc == null) {
						throw new TxnFault(ITxnErrorCodes.FARM_CROPS_DOES_NOT_EXIST);
						//throw new SwitchException(ITxnErrorCodes.FARM_CROPS_DOES_NOT_EXIST);
					}
					
					if (QRCodeUnq !=null && !StringUtil.isEmpty(QRCodeUnq)) {
						packingdetail.setQrCodeId(QRCodeUnq);
					}else{
						packingdetail.setQrCodeId(String.valueOf(DateUtil.getRevisionNumber()));
					}
					
					CityWarehouse cw = (CityWarehouse) farmerService.findObjectById(
							"FROM CityWarehouse ct  where ct.planting.id=? and ct.batchNo=? and ct.stockType=?  and ct.isDelete=0",
							new Object[] { fc.getId(), resBatchNo,
									CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal() });
					packingdetail.setQrUnique(cw.getQrCodeId());
					packingdetail.setCtt(cw);
					packingdetail.setPrice(Double.valueOf(price));
					packingdetail.setTotalprice(productVal);
					packingdetail.setBlockId(fc.getFarmCrops());
					packingdetail.setPlanting(fc);
					packingdetail.setBatchNo(resBatchNo);
					packingdetail.setBestBefore(DateUtil.convertStringToDate(farmbbDateId, DateUtil.DATABASE_DATE_FORMAT));
					packingdetail.setQuantity(Double.valueOf(packWt));
					packingdetail.setPacking(packing);
					packingdetail.setRejectWt(Double.valueOf(rejectWts));
					packing.getPackingDetails().add(packingdetail); 
					
				} catch (Exception e) {
					throw new TxnFault(ITxnErrorCodes.FARM_CROPS_DOES_NOT_EXIST);
				}
			});

		}
		
		Packhouse ph = (Packhouse) farmerService.findObjectById(
				" from Packhouse fc where fc.id=?",
				new Object[] { Long.valueOf(packhouse) });
		packing.setQrCodeId(String.valueOf(DateUtil.getRevisionNumber()));
		packing.setBranchId(head.getBranchId());
		packing.setStatus(1);
		packing.setBatchNo(lotNo);
		packing.setPackingDate(DateUtil.convertStringToDate(packDate, DateUtil.DATABASE_DATE_FORMAT));
		packing.setPackHouse(ph);
		packing.setTotWt(packing.getPackingDetails().stream().mapToDouble(u -> u.getQuantity()).sum());
		packing.setPackerName(packerName);
		packing.setCreatedDate(new Date());
		packing.setCreatedUser(head.getAgentId());
		packing.setPackingDetails(packing.getPackingDetails());
		packing.setMsgNo(head.getMsgNo());
		utilService.savePacking(packing);
		return insResponse;
	}
}
