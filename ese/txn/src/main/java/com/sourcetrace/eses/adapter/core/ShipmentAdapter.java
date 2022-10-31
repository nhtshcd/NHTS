package com.sourcetrace.eses.adapter.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.CityWarehouse;
import com.sourcetrace.eses.entity.Customer;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Packhouse;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.PackhouseIncoming;
import com.sourcetrace.eses.entity.PackhouseIncomingDetails;
import com.sourcetrace.eses.entity.PackingDetail;
import com.sourcetrace.eses.txn.exception.TxnFault;
import com.sourcetrace.eses.entity.Shipment;
import com.sourcetrace.eses.entity.ShipmentDetails;
import com.sourcetrace.eses.interceptor.ITxnErrorCodes;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.exception.SwitchException;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.StringUtil;

@Component
public class ShipmentAdapter implements ITxnAdapter {

	@Autowired
	private IFarmerService farmerService;

	@Autowired
	private IUtilService utilService;

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public Map<?, ?> processJson(Map reqData) {
		Map insResponse = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);

		String packDate = (String) reqData.get("shipDate");
		String packhouse = (String) reqData.get("packhouse");
		String consignNo = (String) reqData.get("consignNo");
		String totalQty = (String) reqData.get("totalQty");
		String traceCode = (String) reqData.get("traceCode");

		String buyer = (String) reqData.get("buyer");

		Shipment shipment = new Shipment();
		
		Shipment pi = (Shipment) farmerService.findObjectById(
				" from Shipment fc where fc.msgNo=?  and fc.status<>2", new Object[] { head.getMsgNo() });
		if (pi != null) {
			return insResponse;
		}
		
		
		shipment.setShipmentDetails(new HashSet<>());
		ArrayList jsonArr = (ArrayList) reqData.get("shipmentList");
		if (jsonArr != null && jsonArr.size() > 0) {

			jsonArr.stream().forEach(uu -> {
				try {
					ShipmentDetails shipmentDetails = new ShipmentDetails();

					LinkedHashMap jsonObj = (LinkedHashMap) uu;

					String blockId = (String) jsonObj.get("blockId");
					String resBatchNo = (String) jsonObj.get("lotNo");
					String packUnit = (String) jsonObj.get("packUnit");
					String lotQty = (String) jsonObj.get("lotQty");
					String packQty = (String) jsonObj.get("packQty");
					String plantingId = (String) jsonObj.get("plantingId");
					String QRCodeUnq = (String) jsonObj.get("QRCodeUnq");

					Planting fc = (Planting) farmerService.findObjectById(
							" from Planting fc where fc.plantingId=?   and fc.status=1", new Object[] { plantingId });

					if (fc == null) {
						throw new TxnFault(ITxnErrorCodes.FARM_CROPS_DOES_NOT_EXIST);
					}
					/*if (QRCodeUnq !=null && !StringUtil.isEmpty(QRCodeUnq)) {
						shipmentDetails.setQrCodeId(QRCodeUnq);
					}else{
						shipmentDetails.setQrCodeId(head.getMsgNo());
					}*/
					
					

					
					CityWarehouse cw = (CityWarehouse) farmerService.findObjectById(
							"FROM CityWarehouse ct  where ct.planting.id=? and ct.batchNo=? and ct.stockType=? and ct.isDelete=0",
							new Object[] { fc.getId(), resBatchNo.trim(), CityWarehouse.Stock_type.PACKING_STOCK.ordinal() });
					shipmentDetails.setCityWarehouse(cw);
					
					if (cw !=null && !StringUtil.isEmpty(cw.getBatchNo())) {
						shipmentDetails.setQrCodeId(String.valueOf(DateUtil.getRevisionNumber()));
						ShipmentDetails pckinc = (ShipmentDetails) farmerService.findObjectById(
								"FROM ShipmentDetails where shipment.status!=2 and cityWarehouse.id=?",
								new Object[] { Long.valueOf(cw.getId())});
						if(pckinc != null && !StringUtil.isEmpty(pckinc)){
							shipmentDetails.setStatus(0);
						}else{
							shipmentDetails.setStatus(1);
						}
						
					}else{
						shipmentDetails.setQrCodeId(String.valueOf(DateUtil.getRevisionNumber()));
						shipmentDetails.setStatus(1);
					}
					
					shipmentDetails.setPackingUnit(packUnit);
					shipmentDetails.setPackingQty(packQty);
					shipmentDetails.setShipment(shipment);
					shipmentDetails.setPlanting(fc);
					shipment.getShipmentDetails().add(shipmentDetails);

				} catch (Exception e) {
					throw new TxnFault(ITxnErrorCodes.FARM_CROPS_DOES_NOT_EXIST);
				}
			});

		}

		Packhouse ph = (Packhouse) farmerService.findObjectById(" from Packhouse fc where fc.id=? and fc.status=1",
				new Object[] { Long.valueOf(packhouse) });

		Customer customer = (Customer) farmerService.findObjectById("FROM Customer c WHERE c.customerId=?",
				new Object[] { buyer });
		
		

		shipment.setCustomer(customer);

		shipment.setBranchId(head.getBranchId());
		
		shipment.setShipmentDate(DateUtil.convertStringToDate(packDate, DateUtil.DATABASE_DATE_FORMAT));
		shipment.setPackhouse(ph);
		shipment.setStatus(1);
		shipment.getShipmentDetails().stream().filter(uu -> uu != null && uu.getStatus() !=null && uu.getStatus().equals(0)).forEach(uu -> {
			shipment.setStatus(4);
		});
		
		if (shipment.getStatus() != null  && !StringUtil.isEmpty(shipment.getStatus()) && !shipment.getStatus().equals(4)) {
			Shipment ship = (Shipment) farmerService.findObjectById("FROM Shipment s WHERE s.pConsignmentNo=? and s.status!=2",new Object[] { consignNo });
			
			if (ship != null  && !StringUtil.isEmpty(ship.getId())) {
				shipment.setStatus(4);
			}else{
				Packhouse packExp = (Packhouse) farmerService.findObjectById(
						"FROM Packhouse f where f.id=? and f.exporter.status=1 and f.exporter.isActive=1 and f.status=1  ORDER BY f.name ASC",
						new Object[] { Long.valueOf(packhouse) });
				if(packExp == null || StringUtil.isEmpty(packExp)){
					shipment.setStatus(3);
				}else{
					shipment.setStatus(1);
				}
			}
			}
		
		shipment.setPConsignmentNo(consignNo);
		shipment.setTotalShipmentQty(totalQty != null && !StringUtil.isEmpty(totalQty) ? Double.valueOf(totalQty) : 0d);
		shipment.setCreatedDate(new Date());
		shipment.setCreatedUser(head.getAgentId());
		shipment.setMsgNo(head.getMsgNo());
		shipment.setKenyaTraceCode(traceCode);
		utilService.saveShipment(shipment);
		return insResponse;
	}
}
