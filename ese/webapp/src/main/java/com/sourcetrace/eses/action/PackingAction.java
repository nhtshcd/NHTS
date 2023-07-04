package com.sourcetrace.eses.action;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.hibernate.boot.model.IdGeneratorStrategyInterpreter.GeneratorNameDeterminationContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.sourcetrace.eses.entity.CityWarehouse;
import com.sourcetrace.eses.entity.DocumentUpload;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.Packhouse;
import com.sourcetrace.eses.entity.PackhouseIncomingDetails;
import com.sourcetrace.eses.entity.Packing;
import com.sourcetrace.eses.entity.PackingDetail;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.PostRecallInspection;
import com.sourcetrace.eses.entity.ProcurementProduct;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.entity.ShipmentDetails;
import com.sourcetrace.eses.entity.SitePrepration;
import com.sourcetrace.eses.entity.SprayAndFieldManagement;
import com.sourcetrace.eses.service.FarmerService;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.FileUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;
import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class PackingAction extends SwitchAction {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SprayAndFieldManagementAction.class);
	protected static final String CREATE = "create";
	protected static final String DETAIL = "detail";
	protected static final String UPDATE = "update";
	protected static final String MAPPING = "mapping";
	protected static final String DELETE = "delete";
	protected static final String LIST = "list";
	protected static final String TITLE_PREFIX = "title.";
	protected static final String HEADING = "heading";

	@Getter
	@Setter
	private String id;
	@Getter
	@Setter
	private String redirectContent;
	@Autowired
	private IFarmerService farmerService;

	@Getter
	@Setter
	private String packingTotalString;

	@Getter
	@Setter
	private Packing packing;

	@Getter
	@Setter
	private String bestBefore;

	@Getter
	@Setter
	private String packingDate;

	@Getter
	@Setter
	private String selectedPackHouse;

	@Getter
	@Setter
	private String selectedBlock;

	@Getter
	@Setter
	private String selectedBatchNo;

	@Autowired
	private IUniqueIDGenerator idGenerator;

	@Getter
	@Setter
	private String farm;

	public String create() throws Exception {
		if (packing == null) {
			command = CREATE;
			request.setAttribute(HEADING, "packingcreate");
			return INPUT;
		} else {
			Set<PackingDetail> packingDetails = formPackingDetail(packing);
			if (!StringUtil.isEmpty(packingDate)) {
				packing.setPackingDate(DateUtil.convertStringToDate(packingDate, getGeneralDateFormat()));

			} else {
				packing.setPackingDate(null);
			}
			Packhouse p = (Packhouse) farmerService.findObjectById("FROM Packhouse where id=?",
					new Object[] { Long.valueOf(getSelectedPackHouse()) });
			packing.setPackHouse(p);
			packing.setPackerName(packing.getPackerName());
			packing.setBranchId(getBranchId());
			packing.setCreatedUser(getUsername());
			packing.setBranchId(getBranchId());
			packing.setStatus(1);
			packing.setPackingDetails(packingDetails);
			DateFormat dFormat = new SimpleDateFormat("HHmmss");
			String code = packing.getPackHouse().getCode() + "_"
					+ DateUtil.convertDateToString(packing.getPackingDate(), DateUtil.DATE_FORMAT_DDMMYY) + "_"
					+ dFormat.format(new Date());
			packing.setBatchNo(code);
			packing.setTotWt(packingDetails.stream().mapToDouble(u -> u.getQuantity()).sum());
			packing.setLatitude(getLatitude());
			packing.setLongitude(getLongitude());
			packing.setQrCodeId(DateUtil.getDateTimWithMinsec());
			utilService.savePacking(packing);
		}
		return REDIRECT;
	}

	public String update() throws Exception {
		if (id != null && !id.equals("") && packing == null) {
			packing = (Packing) farmerService.findObjectById("FROM Packing where id=?",
					new Object[] { Long.valueOf(id) });
			DateFormat df = new SimpleDateFormat(getGeneralDateFormat());
			if (packing.getPackingDetails() != null) {
				packing.getPackingDetails().stream().forEach(uu -> {
					CityWarehouse ctt = (CityWarehouse) farmerService.findObjectById(
							"FROM CityWarehouse where qrCodeId=? and planting.id=? and coOperative.id=? and isDelete=0",
							new Object[] { uu.getQrUnique(), uu.getPlanting().getId(), packing.getPackHouse().getId() });
					uu.setCtt(ctt);

				});
			}
			isEdit = "1";
			/*
			 * ShipmentDetails packde = (ShipmentDetails)
			 * farmerService.findObjectById(
			 * "FROM ShipmentDetails pd where pd.cityWarehouse.batchNo=? and pd.shipment.status=1"
			 * , new Object[] { packing.getBatchNo() }); if (packde == null) {
			 * isEdit = "1"; } else { isEdit = "0"; }
			 */
			setPackingDate(df.format(packing.getPackingDate()));
			command = UPDATE;
			request.setAttribute(HEADING, getText("packingupdate"));
		} else {
			if (packing != null) {
				Packing temp = (Packing) farmerService.findObjectById("FROM Packing where id=?",
						new Object[] { Long.valueOf(id) });

				if (temp == null) {
					addActionError(NO_RECORD);
					return REDIRECT;
				}
				Map<CityWarehouse, Double> existock = new HashMap<CityWarehouse, Double>();
				temp.getPackingDetails().stream().forEach(uu -> {
					CityWarehouse ctt = (CityWarehouse) farmerService.findObjectById(
							"FROM CityWarehouse where batchNo=? and planting.id=? and coOperative.id=? AND isDelete=0",
							new Object[] { uu.getBatchNo(), uu.getPlanting().getId(), temp.getPackHouse().getId() });
					existock.put(ctt, (uu.getQuantity() + uu.getRejectWt()) * -1);
				});
				Set<PackingDetail> packingDetails = formPackingDetail(temp);
				if (!StringUtil.isEmpty(packingDate)) {
					packing.setPackingDate(DateUtil.convertStringToDate(packingDate, getGeneralDateFormat()));

				} else {
					packing.setPackingDate(null);
				}

				if (!StringUtil.isEmpty(packingDate)) {
					temp.setPackingDate(DateUtil.convertStringToDate(packingDate, getGeneralDateFormat()));

				} else {
					temp.setPackingDate(null);
				}
				Packhouse p = (Packhouse) farmerService.findObjectById("FROM Packhouse where id=?",
						new Object[] { Long.valueOf(getSelectedPackHouse()) });
				temp.setPackHouse(p);
				temp.setPackerName(packing.getPackerName());
				temp.setBranchId(getBranchId());
				temp.setCreatedUser(getUsername());
				temp.setBranchId(getBranchId());
				temp.setStatus(1);

				temp.setBranchId(getBranchId());
				temp.setCreatedUser(getUsername());
				temp.setBranchId(getBranchId());
				temp.setStatus(1);
				setCurrentPage(getCurrentPage());
				temp.setCreatedUser(getUsername());
				temp.setCreatedDate(new Date());
				packingDetails.stream().forEach(uu -> {
					if (existock.keySet().stream().anyMatch(cta -> cta.getId() == uu.getCtt().getId())) {
						CityWarehouse kk = existock.keySet().stream().filter(cta -> cta.getId() == uu.getCtt().getId())
								.findFirst().get();
						//kk.setLossWeight(uu.getRejectWt());
						existock.put(kk, existock.get(kk) + uu.getQuantity() + uu.getRejectWt());
					} else {
						existock.put(uu.getCtt(), uu.getQuantity() + uu.getRejectWt());
					}

				});
				temp.getPackingDetails().clear();
				
				//utilService.updatePacking(temp, existock, packingDetails);
				/*packingDetails.stream().forEach(uu -> {
					utilService.saveOrUpdate(uu);
				});*/
				//temp.getShipmentDetails().clear();
				temp.getPackingDetails().addAll(packingDetails);
				temp.setTotWt(packingDetails.stream().mapToDouble(u -> u.getQuantity()).sum());
				
				 utilService.updatePacking(temp, existock, packingDetails);

			}
			request.setAttribute(HEADING, getText("packinglist"));
			return REDIRECT;
		}
		return super.execute();

	}

	 @Getter
		@Setter
		List<Object[]> ex;
	public String detail() throws Exception {
		String view = null;
		if (id != null && !id.equals("")) {
			packing = (Packing) farmerService.findObjectById("FROM Packing where id=?",
					new Object[] { Long.valueOf(id) });
			if (packing == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}

			if (packing.getPackingDetails() != null) {
				packing.getPackingDetails().stream().forEach(uu -> {
					CityWarehouse ctt = (CityWarehouse) farmerService.findObjectById(
							"FROM CityWarehouse where qrCodeId=? and planting.id=? and coOperative.id=? AND isDelete=0",
							new Object[] { uu.getQrUnique(), uu.getPlanting().getId(), packing.getPackHouse().getId() });
					uu.setCtt(ctt);

				});
			}
			ex = utilService.getAuditRecords("com.sourcetrace.eses.entity.Packing", packing.getId());
			
			// ex = utilService.getAuditRecords("com.sourcetrace.eses.entity.Packing", packing.getId());
			DateFormat df = new SimpleDateFormat(getGeneralDateFormat());
			setPackingDate(df.format(packing.getPackingDate()));
			setSelectedPackHouse(packing.getPackHouse().getName());
			setCurrentPage(getCurrentPage());
			command = UPDATE;
			view = DETAIL;
			request.setAttribute(HEADING, getText("packingDetail"));
		} else {
			request.setAttribute(HEADING, getText(LIST));
			return REDIRECT;
		}
		return view;
	}

	public String delete() throws Exception {
		String result = null;
		if (id != null && !id.equals("")) {
			Packing packing = (Packing) farmerService.findObjectById("FROM Packing where id=?",
					new Object[] { Long.valueOf(id) });
			if (packing == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(packing)) {
				packing.setStatus(0);
				packing.setTotWt(0d);
				Map<CityWarehouse, Double> existock = new HashMap<CityWarehouse, Double>();
				packing.getPackingDetails().stream().forEach(uu -> {
					CityWarehouse ctt = (CityWarehouse) farmerService.findObjectById(
							"FROM CityWarehouse where qrCodeId=? and planting.id=? and coOperative.id=? AND isDelete=0",
							new Object[] { uu.getQrUnique(), uu.getPlanting().getId(), packing.getPackHouse().getId() });
					//ctt.setLossWeight(0d);
					existock.put(ctt, (uu.getQuantity() + uu.getRejectWt()) * -1);
				});
				utilService.deletePacking(packing, existock, new HashSet<>());

				result = REDIRECT;
			}
		}

		return result;
	}

	public void populateFarmer() {
		JSONArray jsnArr = new JSONArray();
		if (selectedBlock != null && !StringUtil.isEmpty(selectedBlock) && StringUtil.isLong(selectedBlock)) {

			/*
			 * List<Object[]> cwList = (List<Object[]>)
			 * farmerService.listObjectById(
			 * "select DISTINCT f.id,f.firstName,f.lastName,f.farmerId from CityWarehouse cw join cw.farmcrops fc join fc.farmCrops.farm fm join fm.farmer f where cw.stockType=? and cw.coOperative.id=? and cw.sortedWeight>0 "
			 * , new Object[] {
			 * CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(),
			 * Long.valueOf(selectedBlock) });
			 */
			List<Object[]> cwList = (List<Object[]>) farmerService.listObjectById(
					"select DISTINCT f.id,f.firstName,f.lastName,f.farmerId from CityWarehouse cw join cw.farmcrops fc join fc.farm fm join fm.farmer f where cw.stockType=? and cw.coOperative.id=? and cw.sortedWeight>0  and cw.isDelete=0",
					new Object[] { CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(), Long.valueOf(selectedBlock) });
			cwList.stream().forEach(cw -> {
				jsnArr.add(getJSONObject(cw[0].toString(),
						cw[3].toString() + " - " + cw[1].toString() + " " + (cw[2] != null ? cw[2].toString() : "")));
			});

		}

		sendAjaxResponse(jsnArr);
	}

	public void populateFarm() {
		JSONArray jsnArr = new JSONArray();
		if (selectedBlock != null && !StringUtil.isEmpty(selectedBlock) && StringUtil.isLong(selectedBlock)
				&& selectedFarmer != null && !StringUtil.isEmpty(selectedFarmer) && StringUtil.isLong(selectedFarmer)) {

			/*
			 * List<Object[]> cwList = (List<Object[]>)
			 * farmerService.listObjectById(
			 * "select DISTINCT fm.id,fm.farmName,fm.farmCode from CityWarehouse cw join cw.farmcrops.farmCrops fc join fc.farm fm where cw.stockType=? and cw.coOperative.id=? and fm.farmer.id=?  and cw.sortedWeight>0 "
			 * , new Object[] {
			 * CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(),
			 * Long.valueOf(selectedBlock), Long.valueOf(selectedFarmer) });
			 */
			List<Object[]> cwList = (List<Object[]>) farmerService.listObjectById(
					"select DISTINCT fm.id,fm.farmName,fm.farmCode from CityWarehouse cw join cw.farmcrops fc join fc.farm fm where cw.stockType=? and cw.coOperative.id=? and fm.farmer.id=?  and cw.sortedWeight>0  and cw.isDelete=0",
					new Object[] { CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(), Long.valueOf(selectedBlock),
							Long.valueOf(selectedFarmer) });
			cwList.stream().forEach(cw -> {
				jsnArr.add(getJSONObject(cw[0].toString(), cw[2].toString() + "-" + cw[1].toString()));
			});

		}

		sendAjaxResponse(jsnArr);
	}

/*	public void populatePlanting() {
		JSONArray plantingarr = new JSONArray();
		if (selectedBlock != null && !ObjectUtil.isEmpty(selectedBlock) && !selectedBlock.equals("")) {
			
			LinkedList<Object> parame = new LinkedList();
			String qry = "SELECT DISTINCT f.id,f.plantingId from Sorting s join s.planting f where f.farmCrops.id=? and s.status=1 and f.status=1 ORDER BY f.id ASC";
			parame.add(Long.valueOf(selectedBlock));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry = "SELECT DISTINCT f.id,f.plantingId FROM Sorting s join s.planting f  where f.farmCrops.id=?  and s.status=1 and f.status=1 and f.farmCrops.exporter.id=?  ORDER BY f.id ASC";
				parame.add(Long.valueOf(getLoggedInDealer()));
			}

			List<Object[]> growerList = (List<Object[]> ) farmerService.listObjectById(qry, parame.toArray());
			//List<Planting> dataList = utilService.listPlantingByFarmCropsId(Long.valueOf(selectedBlock));
			growerList.stream().distinct().forEach(f -> {
				plantingarr.add(getJSONObject(f[0].toString(), f[1].toString()));
			});
		}
		sendAjaxResponse(plantingarr);
	}*/
	
	public void populatePlanting() {
		JSONArray plantingarr = new JSONArray();
		if (selectedBlock != null && !ObjectUtil.isEmpty(selectedBlock) && !selectedBlock.equals("") && selectedFarm != null && !ObjectUtil.isEmpty(selectedFarm) && !selectedFarm.equals("")) {

			LinkedList<Object> parame = new LinkedList();
			String qry = "SELECT DISTINCT f.id,f.plantingId from Sorting s join s.planting f where f.farmCrops.id=? and s.status=1 and f.status=1 ORDER BY f.id ASC";
			parame.add(Long.valueOf(selectedBlock));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry = "SELECT DISTINCT f.id,f.plantingId FROM Sorting s join s.planting f  where f.farmCrops.id=?  and s.status=1 and f.status=1 and f.farmCrops.exporter.id=?  ORDER BY f.id ASC";
				parame.add(Long.valueOf(getLoggedInDealer()));
			}

			List<Object[]> growerList = (List<Object[]>) farmerService.listObjectById(qry, parame.toArray());
			growerList.stream().distinct().forEach(f -> {
				Planting planting1 = utilService.findPlantingById(Long.valueOf(f[0].toString()));
				if (planting1 != null) {
					List<Object[]> cwList1 = (List<Object[]>) farmerService.listObjectById(
							"select cw.id,cw.batchNo from CityWarehouse cw join cw.planting fc  where cw.stockType=? and cw.coOperative.id=? and fc.id=?  and cw.sortedWeight>0 and cw.isDelete=0",
							new Object[] { CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(),
									Long.valueOf(selectedFarm), planting1.getId() });
					if (!StringUtil.isListEmpty(cwList1)) {
						plantingarr.add(getJSONObject(f[0].toString(), f[1].toString()));
					}
				}
			});
		}
		sendAjaxResponse(plantingarr);
	}

	public void populateBatches() {
		JSONArray jsnArr = new JSONArray();
		if (selectedBlock != null && !StringUtil.isEmpty(selectedBlock) && StringUtil.isLong(selectedBlock)
				&& selectedFarm != null && !StringUtil.isEmpty(selectedFarm) && StringUtil.isLong(selectedFarm)) {
			Planting planting = utilService.findPlantingById(Long.valueOf(selectedFarm));
			if (planting != null) {
				List<Object[]> cwList = (List<Object[]>) farmerService.listObjectById(
						"select cw.id,cw.batchNo from CityWarehouse cw join cw.planting fc  where cw.stockType=? and cw.coOperative.id=? and fc.id=?  and cw.sortedWeight>0 and cw.isDelete=0",
						new Object[] { CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(),
								Long.valueOf(selectedBlock), planting.getId() });
				/*
				 * List<Object[]> cwList = (List<Object[]>)
				 * farmerService.listObjectById(
				 * "select cw.id,cw.batchNo from CityWarehouse cw join cw.farmcrops fc  where cw.stockType=? and cw.coOperative.id=? and fc.id=?  and cw.sortedWeight>0"
				 * , new Object[] {
				 * CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(),
				 * Long.valueOf(selectedBlock), Long.valueOf(selectedFarm) });
				 */ 
				cwList.stream().forEach(cw -> {
					jsnArr.add(getJSONObject(cw[0].toString(), cw[1].toString()));
				});
			}

		}

		sendAjaxResponse(jsnArr);
	}

	@SuppressWarnings("unchecked")
	public void populateBlocks() {
		JSONArray jsnArr = new JSONArray();
		if (selectedBlock != null && !StringUtil.isEmpty(selectedBlock) && StringUtil.isLong(selectedBlock)
				&& selectedFarm != null && !StringUtil.isEmpty(selectedFarm) && StringUtil.isLong(selectedFarm)) {

			/*
			 * List<Object[]> cwList = (List<Object[]>)
			 * farmerService.listObjectById(
			 * "select DISTINCT fc.id,fc.blockName,fc.blockId from CityWarehouse cw join cw.farmcrops.farmCrops fc join fc.farm fm where cw.stockType=? and cw.coOperative.id=? and fm.id=?  and cw.sortedWeight>0 "
			 * , new Object[] {
			 * CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(),
			 * Long.valueOf(selectedBlock), Long.valueOf(selectedFarm) });
			 * 
			 */
			List<Object[]> cwList = new ArrayList<>();
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
			 cwList = (List<Object[]>) farmerService.listObjectById(
					"select DISTINCT fc.id,fc.blockName,fc.blockId from CityWarehouse cw join cw.farmcrops fc join fc.farm fm where cw.stockType=? and cw.coOperative.id=? and fm.id=?  and cw.sortedWeight>0 and cw.isDelete=0 and fc.exporter.id=?",
					new Object[] { CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(), Long.valueOf(selectedBlock),
							Long.valueOf(selectedFarm),Long.valueOf(getLoggedInDealer()) });
			}else{
				 cwList = (List<Object[]>) farmerService.listObjectById(
						"select DISTINCT fc.id,fc.blockName,fc.blockId from CityWarehouse cw join cw.farmcrops fc join fc.farm fm where cw.stockType=? and cw.coOperative.id=? and fm.id=?  and cw.sortedWeight>0 and cw.isDelete=0",
						new Object[] { CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(), Long.valueOf(selectedBlock),
								Long.valueOf(selectedFarm) });
			}
			if (cwList != null) {
			cwList.stream().forEach(cw -> {
				jsnArr.add(getJSONObject(cw[0].toString(), cw[2].toString() + " - " + cw[1].toString()));
			});
			}
		}

		sendAjaxResponse(jsnArr);
	}

	private Set<PackingDetail> formPackingDetail(Packing pack) {
		Set<PackingDetail> pcdetails = new LinkedHashSet<>();
		if (!StringUtil.isEmpty(getPackingTotalString())) {
			List<String> productsList = Arrays.asList(getPackingTotalString().split("@"));
			productsList.stream().filter(obj -> !StringUtil.isEmpty(obj)).forEach(products -> {
				PackingDetail pcDetail = new PackingDetail();
				List<String> list = Arrays.asList(products.split("#"));
				Planting farmCrop = utilService.findPlantingById(Long.valueOf(list.get(9)));
				CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse  where id=?",
						new Object[] { Long.valueOf(list.get(4)) });
				if(farmCrop.getPlantingId() == cw.getPlanting().getPlantingId()){
					cw.setSortedWeight(0.00);
				}
				cw.setFarmcrops(farmCrop.getFarmCrops());
				cw.setPlanting(farmCrop);
				pcDetail.setCtt(cw);
				pcDetail.setBlockId(farmCrop.getFarmCrops());
				pcDetail.setPlanting(farmCrop);
				pcDetail.setBatchNo(cw.getBatchNo());
				pcDetail.setQrUnique(cw.getQrCodeId());
				if(list.get(10).toString() != null && !StringUtil.isEmpty(list.get(10).toString())){
					pcDetail.setQrCodeId(String.valueOf(DateUtil.getRevisionNoDateTimeMilliSec()));
					//pcDetail.setQrCodeId(list.get(10).toString());
				}else{
					pcDetail.setQrCodeId(String.valueOf(DateUtil.getRevisionNoDateTimeMilliSec()));
				}
				pcDetail.setQuantity(Double.valueOf(list.get(5)));
				pcDetail.setPrice(Double.valueOf(list.get(6)));
				pcDetail.setTotalprice((list.get(11)));
				pcDetail.setRejectWt(Double.valueOf(list.get(7)));
				pcDetail.setBestBefore(DateUtil.convertStringToDate(list.get(8), getGeneralDateFormat()));

				if (list.get(9) != null) {
					Planting planing = utilService.findPlantingById(Long.valueOf(list.get(9)));
					if (planing != null) {
						pcDetail.setPlanting(planing);
					}
				}

				pcDetail.setPacking(pack);
				pcdetails.add(pcDetail);
			});
		}
		return pcdetails;
	}

	public void populateBlockData() {
		JSONObject jss = new JSONObject();
		if (selectedBlock != null && !StringUtil.isEmpty(selectedBlock) && StringUtil.isLong(selectedBlock)) {
			CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse  where id=?",
					new Object[] { Long.valueOf(selectedBlock) });
			if (cw != null) {
				PackhouseIncomingDetails sr = (PackhouseIncomingDetails) farmerService.findObjectById("FROM PackhouseIncomingDetails cw WHERE cw.packhouseIncoming.batchNo=? AND cw.planting.id=? AND cw.packhouseIncoming.status=1",
						new Object[] { cw.getBatchNo(),Long.valueOf(cw.getPlanting().getId())});
				jss.put("createdDate", getGeneralDateFormat(String.valueOf(cw.getCreatedDate())));
				jss.put("hquantity", cw.getSortedWeight());
				jss.put("product", cw.getPlanting()!=null ? cw.getPlanting().getVariety().getName() : "");
				jss.put("variety", cw.getPlanting()!=null ? cw.getPlanting().getGrade().getName() : "");
				jss.put("country", cw.getFarmcrops().getFarm().getVillage() != null
						? cw.getFarmcrops().getFarm().getVillage().getCity().getLocality().getState().getName() : "");
				jss.put("qrCode", sr !=null && sr.getQrCodeId() !=null && !StringUtil.isEmpty(sr.getQrCodeId()) ? String.valueOf(sr.getQrCodeId()) : " ");
			}
		}
		sendAjaxResponse(jss);
	}

	public void populateValidate() {
		Map<String, String> errorCodes = new LinkedHashMap<String, String>();
		if (packing != null) {
			if (StringUtil.isEmpty(packingDate) || getPackingDate() == null || StringUtil.isEmpty(getPackingDate())
					|| getPackingDate().equals("")) {
				errorCodes.put("empty.packingDate", "empty.packingDate");
			}
			if (StringUtil.isEmpty(selectedPackHouse)) {
				errorCodes.put("empty.packhouse", "empty.packhouse");
			}

			if (packing.getPackerName() == null || StringUtil.isEmpty(packing.getPackerName())
					|| packing.getPackerName().equals("")) {
				errorCodes.put("empty.packerName", "empty.packerName");
			}

			if (StringUtil.isEmpty(getPackingTotalString())) {
				errorCodes.put("empty.packerdetails", "empty.packerdetails");
			}

		} else {
			errorCodes.put("empty.fields", getLocaleProperty("empty.fields"));
		}
		printErrorCodes(errorCodes);
	}

	public void populateExpiryDate() {
		JSONObject jss = new JSONObject();
		if (selectedFarm != null && !StringUtil.isEmpty(selectedFarm) && StringUtil.isLong(selectedFarm)) {

			Planting farmCrops = utilService.findPlantingById(Long.valueOf(selectedFarm));
			if (farmCrops != null && !StringUtil.isEmpty(farmCrops.getPlantingDate())) {
				jss.put("expDate", DateUtil.convertDateToString(farmCrops.getPlantingDate(), DateUtil.DATE_FORMAT_2));
			}

		}

		sendAjaxResponse(jss);
	}
}