package com.sourcetrace.eses.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.CityWarehouse;
import com.sourcetrace.eses.entity.CityWarehouseDetail;
import com.sourcetrace.eses.entity.Customer;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Packhouse;
import com.sourcetrace.eses.entity.PackhouseIncoming;
import com.sourcetrace.eses.entity.PackhouseIncomingDetails;
import com.sourcetrace.eses.entity.Packing;
import com.sourcetrace.eses.entity.PackingDetail;
import com.sourcetrace.eses.entity.ParentEntity;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.Recalling;
import com.sourcetrace.eses.entity.Shipment;
import com.sourcetrace.eses.entity.ShipmentDetails;
import com.sourcetrace.eses.entity.Sorting;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class ShipmentAction extends SwitchAction {

	private static final long serialVersionUID = 1L;
	private String QUERY = "FROM Shipment s WHERE s.id=? AND (s.status=? OR s.status=? OR s.status=?)";
	private String PACKHOUSE_QUERY = "FROM Packhouse w WHERE w.id=? AND w.status=?";
	private String BUYER_QUERY = "FROM Customer c WHERE c.id=?";
	private String LOT_NO_QUERY = "FROM CityWarehouse cw WHERE cw.coOperative.id=? AND cw.stockType=? and cw.sortedWeight>0 and cw.isDelete=0";
	private String CITYWAREHOUSE_QUERY = "FROM Packing cw WHERE cw.batchNo=? AND cw.status=?";
	//private String PACKHOUSE_BATCH_QUERY = "FROM CityWarehouse cw WHERE cw.id=? AND cw.stockType=?";
	private String PACKHOUSE_BATCH_QUERY = "FROM CityWarehouse cw WHERE cw.planting.id=? AND cw.stockType=? and cw.isDelete=0";
	private String CITYWAREHOUSE_ID_QUERY = "FROM CityWarehouse cw WHERE cw.id=? AND cw.stockType=? and cw.isDelete=0";

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String redirectContent;

	@Getter
	@Setter
	private String command;

	@Getter
	@Setter
	private String shipmentDate;

	@Getter
	@Setter
	private String selectedPackhouse;

	@Getter
	@Setter
	private String selectedExpLicNo;

	@Getter
	@Setter
	private String selectedBuyer;

	@Getter
	@Setter
	private String selectedPConsignmentNo;

	@Getter
	@Setter
	private String expLicNo;

	@Getter
	@Setter
	private String shipmentDtl;

	@Getter
	@Setter
	private Shipment shipment;

	@Getter
	@Setter
	private String selectedLotNo;

	@Getter
	@Setter
	private Object totalShipmentQty;

	@Getter
	@Setter
	private String pid;
	
	@Getter
	@Setter
	private String isEdit;
	
	@Getter
	@Setter
	private String qrCode;
	
	@Getter
	@Setter
	private String lotNos;

	public String detail() throws Exception {
		if (id != null && !StringUtil.isEmpty(id) && StringUtil.isLong(id)) {
			shipment = (Shipment) farmerService.findObjectById(QUERY, new Object[] { Long.valueOf(getId()), 1,3,4 });
			if (shipment == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			shipmentDate = DateUtil.convertDateToString(shipment.getShipmentDate(), getGeneralDateFormat());
			selectedPackhouse = shipment.getPackhouse().getId().toString();
			selectedBuyer = shipment.getCustomer().getId().toString();
			selectedExpLicNo = shipment.getPackhouse().getExporter().getRegNumber();
			selectedPConsignmentNo = shipment.getPConsignmentNo();
			shipment.getShipmentDetails().stream().forEach(uu -> {
				CityWarehouse cw = (CityWarehouse) farmerService.findObjectById(PACKHOUSE_BATCH_QUERY,
						new Object[] { uu.getCityWarehouse().getPlanting().getId(), CityWarehouse.Stock_type.PACKING_STOCK.ordinal() });
				
				Packing sr = (Packing) farmerService.findObjectById(CITYWAREHOUSE_QUERY,
						new Object[] { cw.getBatchNo(),1 });
				uu.setSr(sr);
			});
			
			setCurrentPage(getCurrentPage());
			setCommand(DETAIL);
			return DETAIL;
		} else {
			request.setAttribute(HEADING, getText("shipmentDetail"));
			return REDIRECT;
		}
	}

	public String create() throws Exception {
		if (getShipmentDate() == null && shipment == null) {
			command = CREATE;
			request.setAttribute(HEADING, "shipmentcreate");
			shipment = new Shipment();
			id = null;
			return INPUT;
		} else {
			Packhouse packhouse = (Packhouse) farmerService.findObjectById(PACKHOUSE_QUERY,
					new Object[] { Long.valueOf(getSelectedPackhouse()), 1 });
			Customer customer = (Customer) farmerService.findObjectById(BUYER_QUERY,
					new Object[] { Long.valueOf(getSelectedBuyer()) });

			shipment = new Shipment();

			shipment.setPackhouse(packhouse);
			shipment.setCustomer(customer);
			shipment.setShipmentDate(DateUtil.convertStringToDate(getShipmentDate(), getGeneralDateFormat()));
			shipment.setCreatedDate(new Date());
			shipment.setCreatedUser(getUsername());
			shipment.setBranchId(getBranchId());
			shipment.setStatus(1);
			shipment.setLatitude(getLatitude());
			shipment.setLongitude(getLongitude());
			shipment.setPConsignmentNo(getSelectedPConsignmentNo());
			shipment.setKenyaTraceCode(shipment.getPackhouse().getExporter().getName()+"_"+selectedExpLicNo+"_"+getSelectedPConsignmentNo()+"_"+DateUtil.getDateTimWithMinNew());

			if (shipmentDtl != null && !StringUtil.isEmpty(shipmentDtl)) {
				Set<ShipmentDetails> shipmentDetails = getShipmentDetails(shipment);
				shipment.setShipmentDetails(shipmentDetails);
			}
			DecimalFormat df = new DecimalFormat(".00");
			  Double sty;
			  sty=shipment.getShipmentDetails().stream().mapToDouble(u -> u.getPackingQty() == null ? 0 : Double.valueOf(u.getPackingQty())).sum();
			shipment.setTotalShipmentQty(Double.valueOf(df.format(sty)));

			utilService.save(shipment);
			utilService.saveShipment(shipment);
		}
		return REDIRECT;
	}

	public Set<ShipmentDetails> getShipmentDetails(Shipment shipment) {
		Set<ShipmentDetails> shipmentDetailsSet = new LinkedHashSet<>();
		if (!StringUtil.isEmpty(getShipmentDtl())) {
			List<String> sList = Arrays.asList(getShipmentDtl().split("@"));
			sList.stream().filter(obj -> !StringUtil.isEmpty(obj)).forEach(sd -> {
				ShipmentDetails sDetails = new ShipmentDetails();
				List<String> list = Arrays.asList(sd.split("#"));

				CityWarehouse cityWarehouse = (CityWarehouse) farmerService.findObjectById(CITYWAREHOUSE_ID_QUERY,
						new Object[] { Long.valueOf(list.get(0).toString()),
								CityWarehouse.Stock_type.PACKING_STOCK.ordinal() });
				sDetails.setCityWarehouse(cityWarehouse);
				sDetails.setPackingUnit(list.get(1).toString());
				sDetails.setPackingQty(list.get(2).toString());
				if(list.get(4).toString() != null && !StringUtil.isEmpty(list.get(4).toString())){
					sDetails.setQrCodeId(list.get(4).toString());
				}else{
					sDetails.setQrCodeId(String.valueOf(DateUtil.getRevisionNumber()));
				}
				sDetails.setStatus(1);
				if (list.get(3) != null) {
					Planting planing = utilService.findPlantingById(Long.valueOf(list.get(3)));
					if (planing != null) {
						sDetails.setPlanting(planing);
					}
				}
				
				sDetails.setShipment(shipment);
				shipmentDetailsSet.add(sDetails);
			});
		}
		return shipmentDetailsSet;
	}

	public String update() throws Exception {
		if (id != null && !StringUtil.isEmpty(id) && shipment == null) {
			shipment = (Shipment) farmerService.findObjectById(QUERY, new Object[] { Long.valueOf(getId()), 1,3,4 });
			if (shipment == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			shipmentDate = DateUtil.convertDateToString(shipment.getShipmentDate(), getGeneralDateFormat());
			selectedPackhouse = shipment.getPackhouse().getId().toString();
			selectedBuyer = shipment.getCustomer().getId().toString();
			selectedExpLicNo = shipment.getPackhouse().getExporter().getRefLetterNo();
			selectedPConsignmentNo = shipment.getPConsignmentNo();
			setCurrentPage(getCurrentPage());
			isEdit = "1";
			/*Recalling packde = (Recalling) farmerService.findObjectById(
					"FROM Recalling pd where pd.kenyaTraceCode=?  and pd.status=1", new Object[] { shipment.getPConsignmentNo() });
			if (packde == null) {
				isEdit = "1";
			} else {
				isEdit = "0";
			}*/
			/*shipment.getShipmentDetails().stream().forEach(uu -> {
				CityWarehouse cw = (CityWarehouse) farmerService.findObjectById(PACKHOUSE_BATCH_QUERY,
						new Object[] { uu.getPlanting().getId(), CityWarehouse.Stock_type.PACKING_STOCK.ordinal() });
				Packing sr = (Packing) farmerService.findObjectById(CITYWAREHOUSE_QUERY,
						new Object[] { cw.getBatchNo(),1 });
				uu.setSr(sr);
			});*/
			
			shipment.getShipmentDetails().stream().forEach(uu -> {
				CityWarehouse cw = (CityWarehouse) farmerService.findObjectById(CITYWAREHOUSE_ID_QUERY,
						new Object[] { uu.getCityWarehouse().getId(), CityWarehouse.Stock_type.PACKING_STOCK.ordinal() });
				Packing sr = (Packing) farmerService.findObjectById(CITYWAREHOUSE_QUERY,
						new Object[] { cw.getBatchNo(),1 });
				uu.setSr(sr);
			});
			
			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getText("shipmentupdate"));
		} else {
			if (shipment != null) {
				Shipment s = (Shipment) farmerService.findObjectById(QUERY,
						new Object[] { Long.valueOf(shipment.getId()), 1,3,4 });

				Packhouse pHouse = (Packhouse) farmerService.findObjectById(PACKHOUSE_QUERY,
						new Object[] { Long.valueOf(getSelectedPackhouse()), 1 });

				Customer c = (Customer) farmerService.findObjectById(BUYER_QUERY,
						new Object[] { Long.valueOf(getSelectedBuyer()) });
				Map<CityWarehouse, Double> existock = new HashMap<CityWarehouse, Double>();
				s.getShipmentDetails().stream().filter(uu-> uu.getShipment().getStatus() != null && !uu.getShipment().getStatus().equals(4)).forEach(uu -> {
					existock.put(uu.getCityWarehouse(), Double.valueOf(uu.getPackingQty()));
				});
				
				existock.entrySet().stream().forEach(uu ->{
					CityWarehouse cityWarehouse = uu.getKey();
					if (cityWarehouse != null) {

						CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
								shipment.getCreatedDate(), 2, shipment.getId(), 0l, cityWarehouse.getLossWeight(),
								cityWarehouse.getHarvestedWeight(), cityWarehouse.getSortedWeight(), 0l, 0d, 0d,
								Double.valueOf(uu.getValue()), 0l, cityWarehouse.getHarvestedWeight(),
								cityWarehouse.getLossWeight(),
								cityWarehouse.getSortedWeight() + Double.valueOf(uu.getValue()), "SHIPMENT", null);
						cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
						cityWarehouse.setSortedWeight(cityWarehouse.getSortedWeight() + Double.valueOf(uu.getValue()));
						cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
						utilService.update(cityWarehouse);
					}
				});
				
				s.setShipmentDate(DateUtil.convertStringToDate(getShipmentDate(), getGeneralDateFormat()));
				s.setPackhouse(pHouse);
				s.setCustomer(c);
				

				s.setPConsignmentNo(getSelectedPConsignmentNo());
				s.setUpdatedDate(new Date());
				s.setUpdatedUser(getUsername());
				if(s.getStatus() != null && !StringUtil.isEmpty(s.getStatus()) && s.getStatus().equals(4)){
					Packhouse packExp = (Packhouse) farmerService.findObjectById(
							"FROM Packhouse f where f.id=? and f.exporter.status=1 and f.exporter.isActive=1 and f.status=1  ORDER BY f.name ASC",
							new Object[] { Long.valueOf(pHouse.getId()) });
					if(packExp == null || StringUtil.isEmpty(packExp)){
						s.setStatus(3);
					}else{
						s.setStatus(1);
					}
					s.setKenyaTraceCode(s.getPackhouse().getExporter().getName()+"_"+selectedExpLicNo+"_"+getSelectedPConsignmentNo()+"_"+DateUtil.getDateTimWithMinNew());
				}
				Set<ShipmentDetails> sDtl = getShipmentDetails(s);
				DecimalFormat df = new DecimalFormat(".00");
			    Double sty=sDtl.stream()
						.mapToDouble(u -> u.getPackingQty() == null ? 0 : Double.valueOf(u.getPackingQty())).sum();
				s.setTotalShipmentQty(Double.valueOf(df.format(sty)));
				s.getShipmentDetails().clear();
				utilService.saveOrUpdate(s);

				s.setShipmentDetails(sDtl);

				if (s.getShipmentDetails() != null && s.getShipmentDetails().size() > 0) {
					s.getShipmentDetails().stream().filter(sd -> sd != null && !ObjectUtil.isEmpty(sd)).forEach(sd -> {
						sd.setShipment(s);
						utilService.save(sd);
					});
				}
				updateextingShipment(existock,s);
				//utilService.updateShipment(existock,s);
			}
			setCurrentPage(getCurrentPage());
			request.setAttribute(HEADING, "shipmentlist");
			return REDIRECT;
		}
		return super.execute();
	}

	public String delete() {
		String result = null;
		if (id != null && !StringUtil.isEmpty(id)) {
			Shipment s = (Shipment) farmerService.findObjectById(QUERY, new Object[] { Long.valueOf(getId()), 1,3,4 });
			if (s == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(s)) {
				s.setStatus(ParentEntity.Active.DELETED.ordinal());
				utilService.update(s);
				s.getShipmentDetails().stream().filter(uu -> uu.getCityWarehouse()!=null && uu.getShipment().getStatus() != null && !uu.getShipment().getStatus().equals(4)).forEach(uu -> {
					uu.getCityWarehouse().setSortedWeight(uu.getCityWarehouse().getSortedWeight()+Double.valueOf(uu.getPackingQty()));
					utilService.update(uu.getCityWarehouse());
				});
				result = REDIRECT;
			}
		}
		return result;
	}

	public void populateLotNumbers() {
		JSONArray jsnArr = new JSONArray();
		if(!pid.isEmpty()){
			
			List<CityWarehouse> cwList = (List<CityWarehouse>) farmerService.listObjectById(LOT_NO_QUERY,
					new Object[] { Long.valueOf(pid), CityWarehouse.Stock_type.PACKING_STOCK.ordinal() });
			cwList.stream().filter(u -> u.getBatchNo()!=null).map( u -> u.getBatchNo()).distinct().forEach(cw -> {
				jsnArr.add(getJSONObject(cw,cw));
			});
		}

		sendAjaxResponse(jsnArr);
	}

/*	public void populateBlock() {
		JSONArray jsnArr = new JSONArray();
		if (selectedLotNo != null && !StringUtil.isEmpty(selectedLotNo)) {
			List<Object[]> cwList = (List<Object[]>) farmerService.listObjectById(
					"select  ct.id,ct.farmcrops.blockId,ct.farmcrops.blockName from  CityWarehouse ct where ct.stockType=? and ct.batchNo=? and ct.sortedWeight>0",
					new Object[] { CityWarehouse.Stock_type.PACKING_STOCK.ordinal(), selectedLotNo });

			cwList.stream().forEach(cw -> {
				jsnArr.add(getJSONObject(cw[0].toString(),cw[1].toString()+"-"+cw[2].toString()));
			});
		}
		sendAjaxResponse(jsnArr);
	}*/
	public void populateBlock() {
		JSONArray jsnArr = new JSONArray();
		if (selectedLotNo != null && !StringUtil.isEmpty(selectedLotNo)) {
			/*List<Object[]> cwList = (List<Object[]>) farmerService.listObjectById(
					"select  ct.farmcrops.id,ct.farmcrops.blockId,ct.farmcrops.blockName from  CityWarehouse ct where ct.stockType=? and ct.batchNo=? and ct.sortedWeight>0",
					new Object[] { CityWarehouse.Stock_type.PACKING_STOCK.ordinal(), selectedLotNo });*/
			
			List<Object[]> cwList = new ArrayList<>();
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				cwList = (List<Object[]>) farmerService.listObjectById(
						"select  ct.id,ct.farmcrops.blockId,ct.farmcrops.blockName from  CityWarehouse ct where ct.stockType=? and ct.batchNo=? and ct.sortedWeight>0 and ct.isDelete=0 and ct.farmcrops.exporter.id=?",
						new Object[] { CityWarehouse.Stock_type.PACKING_STOCK.ordinal(), selectedLotNo,Long.valueOf(getLoggedInDealer()) });
			}else{
				cwList = (List<Object[]>) farmerService.listObjectById(
						"select  ct.id,ct.farmcrops.blockId,ct.farmcrops.blockName from  CityWarehouse ct where ct.stockType=? and ct.batchNo=? and ct.sortedWeight>0 and ct.isDelete=0",
						new Object[] { CityWarehouse.Stock_type.PACKING_STOCK.ordinal(), selectedLotNo });
			}
			
			cwList.stream().forEach(cw -> {
				jsnArr.add(getJSONObject(cw[0].toString(),cw[1].toString()+"-"+cw[2].toString()));
			});
		}
		sendAjaxResponse(jsnArr);
	}

	
/*	public void populatePlanting() {
		JSONArray plantingarr = new JSONArray();
		if (selectedBlock != null && !ObjectUtil.isEmpty(selectedBlock) && !selectedBlock.equals("")) {
			CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse cw WHERE cw.id=? AND cw.stockType=?",
					new Object[] { Long.valueOf(selectedBlock), CityWarehouse.Stock_type.PACKING_STOCK.ordinal() });
			if(cw!=null){
				
				LinkedList<Object> parame = new LinkedList();
				String qry = "select distinct p.id,p.plantingId from CityWarehouse ct join ct.planting p join p.farmCrops fc where fc.id=?  and p.status=1 ORDER BY p.id ASC";
				parame.add(Long.valueOf(cw.getFarmcrops().getId()));
				if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
					qry = "select distinct p.id,p.plantingId from CityWarehouse ct join ct.planting p join p.farmCrops fc where fc.id=?  and p.status=1 and fc.exporter.id=? ORDER BY p.id ASC";
					parame.add(Long.valueOf(getLoggedInDealer()));
				}

				List<Object[]> growerList = (List<Object[]>) farmerService.listObjectById(qry, parame.toArray());
				//List<Planting> dataList = utilService.listPlantingByFarmCropsId(cw.getFarmcrops().getId());
				growerList.stream().distinct().forEach(f -> {
					plantingarr.add(getJSONObject(f[0].toString(),f[1].toString()));
				});
			}
		}
		sendAjaxResponse(plantingarr);
	}*/
	
	public void populatePlanting() {
		JSONArray plantingarr = new JSONArray();
		if (selectedBlock != null && !ObjectUtil.isEmpty(selectedBlock) && !selectedBlock.equals("") && lotNos != null && !ObjectUtil.isEmpty(lotNos) && !lotNos.equals("")) {
			CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse cw WHERE cw.id=? AND cw.stockType=? and cw.isDelete=0",
					new Object[] { Long.valueOf(selectedBlock), CityWarehouse.Stock_type.PACKING_STOCK.ordinal() });
			if(cw!=null){
				
				LinkedList<Object> parame = new LinkedList();
				String qry = "select distinct p.id,p.plantingId from CityWarehouse ct join ct.planting p join p.farmCrops fc where fc.id=? and ct.isDelete=0  and p.status=1 ORDER BY p.id ASC";
				parame.add(Long.valueOf(cw.getFarmcrops().getId()));
				if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
					qry = "select distinct p.id,p.plantingId from CityWarehouse ct join ct.planting p join p.farmCrops fc where fc.id=? and ct.isDelete=0 and p.status=1 and fc.exporter.id=? ORDER BY p.id ASC";
					parame.add(Long.valueOf(getLoggedInDealer()));
				}

				List<Object[]> growerList = (List<Object[]>) farmerService.listObjectById(qry, parame.toArray());
				growerList.stream().distinct().forEach(f -> {
					if(f[0]!=null){
						CityWarehouse cw1 = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse cw WHERE cw.planting.id=? AND cw.batchNo=? AND cw.stockType=? and cw.isDelete=0",
								new Object[] { Long.valueOf(f[0].toString()),lotNos, CityWarehouse.Stock_type.PACKING_STOCK.ordinal() });
						if(cw1!=null){
							PackingDetail sr = (PackingDetail) farmerService.findObjectById("FROM PackingDetail cw WHERE cw.packing.batchNo=? AND cw.planting.id=? AND cw.packing.status=1",
									new Object[] { cw1.getBatchNo(),Long.valueOf(cw1.getPlanting().getId())});
							if(sr!=null && cw1.getPlanting()!=null && cw1.getPlanting().getVariety()!=null && sr.getPacking()!=null){
								plantingarr.add(getJSONObject(f[0].toString(),f[1].toString()));
								
							}
						}
					}
					
				});
			}
		}
		sendAjaxResponse(plantingarr);
	}
	
	public void populateProductVariety() {
		JSONObject jss = new JSONObject();
		if (selectedLotNo != null && !StringUtil.isEmpty(selectedLotNo)) {
			CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse cw WHERE cw.planting.id=? AND cw.batchNo=? AND cw.stockType=? and cw.isDelete=0",
					new Object[] { Long.valueOf(selectedLotNo),lotNos, CityWarehouse.Stock_type.PACKING_STOCK.ordinal() });
			PackingDetail sr = (PackingDetail) farmerService.findObjectById("FROM PackingDetail cw WHERE cw.packing.batchNo=? AND cw.planting.id=? AND cw.packing.status=1",
					new Object[] { cw.getBatchNo(),Long.valueOf(cw.getPlanting().getId())});
			jss.put("product", cw.getPlanting().getVariety().getName());
			jss.put("variety", cw.getPlanting().getGrade().getName());
			jss.put("lotQty", cw.getSortedWeight());
			jss.put("createdDate", getGeneralDateFormat(String.valueOf(sr.getPacking().getPackingDate())));
			jss.put("qrCode", sr !=null && sr.getQrCodeId() !=null && !StringUtil.isEmpty(sr.getQrCodeId()) ? String.valueOf(sr.getQrCodeId()) : " ");
			//jss.put("detailId", sr !=null && sr.getId() !=null && !StringUtil.isEmpty(sr.getId()) ? String.valueOf(sr.getId()) : " ");
		}
		sendAjaxResponse(jss);
	}
	
	public void populateqrCodecheck(){
		JSONObject jss = new JSONObject();
		if (qrCode != null && !StringUtil.isEmpty(qrCode)) {
			Shipment sr = (Shipment) farmerService.findObjectById("FROM Shipment pi LEFT JOIN FETCH pi.shipmentDetails pid WHERE pid.status=? and pid.qrCodeId=?",
					new Object[] { String.valueOf("1"),String.valueOf(qrCode) });
			if(sr != null || !StringUtil.isEmpty(sr)){
				jss.put("QRCheck", "1");
			}else{
				jss.put("QRCheck", "0");
			}
		}
		sendAjaxResponse(jss);
	}
/*	public void populateProductVariety() {
		JSONObject jss = new JSONObject();
		if (selectedLotNo != null && !StringUtil.isEmpty(selectedLotNo)) {
			CityWarehouse cw = (CityWarehouse) farmerService.findObjectById(PACKHOUSE_BATCH_QUERY,
					new Object[] { Long.valueOf(selectedLotNo), CityWarehouse.Stock_type.PACKING_STOCK.ordinal() });
			Packing sr = (Packing) farmerService.findObjectById(CITYWAREHOUSE_QUERY,
					new Object[] { cw.getBatchNo(),1 });
			jss.put("product", cw.getPlanting().getVariety().getName());
			jss.put("variety", cw.getPlanting().getGrade().getName());
			jss.put("lotQty", cw.getSortedWeight());
			jss.put("createdDate", getGeneralDateFormat(String.valueOf(sr.getPackingDate())));
		}
		sendAjaxResponse(jss);
	}
*/
	public void populateExportLicenseNo() {
		JSONObject jss = new JSONObject();
		if (expLicNo != null && !StringUtil.isEmpty(expLicNo)) {
			Packhouse packhouse = (Packhouse) farmerService.findObjectById(PACKHOUSE_QUERY,
					new Object[] { Long.valueOf(expLicNo), Integer.valueOf(1) });
			//jss.put("expLicNo", packhouse.getExporter().getRegNumber());4
			if(packhouse!=null){
			jss.put("expLicNo", packhouse.getExporter().getRefLetterNo());}
		}
		sendAjaxResponse(jss);
	}

	public void populateValidate() {
		Map<String, String> errorCodes = new LinkedHashMap<String, String>();

		if (shipmentDate == null || StringUtil.isEmpty(shipmentDate)) {
			errorCodes.put("empty.shipment.shipmentDate", getLocaleProperty("empty.shipment.shipmentDate"));
		}
		if (getSelectedPackhouse() == null || StringUtil.isEmpty(getSelectedPackhouse())) {
			errorCodes.put("empty.shipment.packhouse", getLocaleProperty("empty.shipment.packhouse"));
		}else{
			Packhouse packhouse = (Packhouse) farmerService.findObjectById(
					"FROM Packhouse f where f.id=? and f.exporter.status=1 and f.exporter.isActive=1 and f.status=1  ORDER BY f.name ASC",
					new Object[] { Long.valueOf(getSelectedPackhouse()) });
			if(packhouse == null || StringUtil.isEmpty(packhouse)){
				errorCodes.put("inactive.packhouse.export", getLocaleProperty("inactive.packhouse.export"));
			}
		}
		if (getSelectedExpLicNo() == null || StringUtil.isEmpty(getSelectedExpLicNo())) {
			errorCodes.put("empty.shipment.expLicNo", getLocaleProperty("empty.shipment.expLicNo"));
		}
		if (getSelectedBuyer() == null || StringUtil.isEmpty(getSelectedBuyer())) {
			errorCodes.put("empty.shipment.buyer", getLocaleProperty("empty.shipment.buyer"));
		}

		if (getSelectedPConsignmentNo() != null && !StringUtil.isEmpty(getSelectedPConsignmentNo())) {
			

				Shipment ship = (Shipment) farmerService.findObjectById("FROM Shipment s WHERE s.pConsignmentNo=? and s.status!=2",new Object[] { getSelectedPConsignmentNo() });
				
				if (ship != null  && (shipment.getId() == null ||  !shipment.getId().equals(ship.getId()))) {

						errorCodes.put("duplicate.consignmentno", "duplicate.consignmentno");
						
				}else{
					
				}

			
		} else {
			errorCodes.put("empty.shipment.pConsignmentNo", getLocaleProperty("empty.shipment.pConsignmentNo"));
		}
		if (getShipmentDtl() == null || StringUtil.isEmpty(getShipmentDtl())) {
			errorCodes.put("empty.packerdetails", getLocaleProperty("empty.packerdetails"));
		}else{
			List<String> sList = Arrays.asList(getShipmentDtl().split("@"));
			int[] index = { 0 };
			sList.stream().filter(obj -> !StringUtil.isEmpty(obj)).forEach(sd -> {
				List<String> list = Arrays.asList(sd.split("#"));
				sList.get(index[0]++);
				int currentIndex = index[0];
				if(list.get(4).toString() != null && !StringUtil.isEmpty(list.get(4).toString())){
					CityWarehouse cityWarehouse = (CityWarehouse) farmerService.findObjectById(CITYWAREHOUSE_ID_QUERY,
							new Object[] { Long.valueOf(list.get(0).toString()),CityWarehouse.Stock_type.PACKING_STOCK.ordinal() });
					
					ShipmentDetails sr = (ShipmentDetails) farmerService.findObjectById("FROM ShipmentDetails pi WHERE pi.shipment.status!=2 and pi.cityWarehouse.id=?",
						new Object[] { Long.valueOf(cityWarehouse.getId()) });
					
				
				if (sr != null && !sr.getId().equals(Long.valueOf(list.get(5).toString()))) {
					errorCodes.put("empty.shipment.sortQr"+currentIndex, "Row" + currentIndex + ": "+
							getLocaleProperty("empty.shipment.sortQr"));
				}
				}
			});
		}
		printErrorCodes(errorCodes);
	}
	public void updateextingShipment( Map<CityWarehouse, Double> existingstock,Shipment shipment) {
	
		shipment.getShipmentDetails().stream().forEach(uu -> {
			CityWarehouse cityWarehouse = uu.getCityWarehouse();
			if (cityWarehouse != null) {

				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
						shipment.getCreatedDate(), 2, shipment.getId(), 0l, cityWarehouse.getLossWeight(),
						cityWarehouse.getHarvestedWeight(), cityWarehouse.getSortedWeight(), 0l, 0d, 0d,
						Double.valueOf(uu.getPackingQty()), 0l, cityWarehouse.getHarvestedWeight(),
						cityWarehouse.getLossWeight(),
						cityWarehouse.getSortedWeight() - Double.valueOf(uu.getPackingQty()), "SHIPMENT", null);
				cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
				cityWarehouse.setSortedWeight(cityWarehouse.getSortedWeight() - Double.valueOf(uu.getPackingQty()));
				cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
				utilService.update(cityWarehouse);
			}
		});

	}

}
