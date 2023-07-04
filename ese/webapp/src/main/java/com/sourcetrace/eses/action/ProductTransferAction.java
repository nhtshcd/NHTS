package com.sourcetrace.eses.action;

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

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.CityWarehouse;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.ExporterRegistration;
import com.sourcetrace.eses.entity.Packhouse;
import com.sourcetrace.eses.entity.PackingDetail;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.ProductTransfer;
import com.sourcetrace.eses.entity.ProductTransferDetail;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class ProductTransferAction extends SwitchAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ProductTransferAction.class);

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
	private ProductTransfer productTransfer;

	@Getter
	@Setter
	private String id;

	@Autowired
	private IUtilService utilService;

	@Getter
	@Setter
	private String redirectContent;
	
	@Getter
	@Setter
	private String selectedDate;
	
	@Getter
	@Setter
	private String selectedTransferFrom;
	
	@Getter
	@Setter
	private String selectedTransferTo;
	
	@Getter
	@Setter
	private String selectedExporter;
	
	@Getter
	@Setter
	private String selectedPackhouse;
	
	@Getter
	@Setter
	private String selectedReceptionBatchID;
	
	@Getter
	@Setter
	private String selectedBlockID;
	
	@Getter
	@Setter
	private String selectedPlantingID;
	
	@Getter
	@Setter
	private String productTransferListDetails;
	
	@Autowired
	private IUniqueIDGenerator idGenerator;
	
	/**
	 * Creates the.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String create() throws Exception {
		String defBranchId = utilService.findPrefernceByName(ESESystem.DEF_BRANCH);
		if (productTransfer == null) {
			command = "create";

			request.setAttribute(HEADING, getText("productTransfercreate"));

			if (request != null && request.getSession() != null) {
				request.getSession().setAttribute(ISecurityFilter.REPORT, "PRODUCTTRANSFER");
			}
			
			productTransfer = new ProductTransfer();
			if (getLoggedInDealer() > 0) {
				ExporterRegistration ex = utilService.findExportRegById(Long.valueOf(getLoggedInDealer()));
				if (ex != null) {
					productTransfer.setExporter(ex);
				}
			}
			
			return INPUT;
		} else {
			productTransfer.setCreatedDate(new Date());
			productTransfer.setBranchId(getBranchId() != null ? getBranchId() : defBranchId);
			productTransfer.setCreatedUser(getUsername());
			
			//productTransfer.setBatchNo(idGenerator.getProductTransferIdSeq());
			productTransfer.setBatchNo(DateUtil.getDateTimWithMinsec());
			
			if (productTransferListDetails != null && !StringUtil.isEmpty(productTransferListDetails)) {
				Set<ProductTransferDetail> phIncomingDetails = getProductTransferDetails(productTransfer);
				productTransfer.setProductTransferDetails(phIncomingDetails);
			}
			
			if (!StringUtil.isEmpty(selectedDate)) {
				productTransfer.setDate(DateUtil.convertStringToDate(selectedDate, getGeneralDateFormat()));
			} else {
				productTransfer.setDate(null);
			}
			
			if (!StringUtil.isEmpty(selectedTransferFrom)) {
				Packhouse pack=utilService.findWarehouseById(Long.valueOf(selectedTransferFrom));
				if(pack!=null)
					productTransfer.setTransferFrom(pack);
			}
			if (!StringUtil.isEmpty(selectedTransferTo)) {
				Packhouse pack=utilService.findWarehouseById(Long.valueOf(selectedTransferTo));
				if(pack!=null)
					productTransfer.setTransferTo(pack);
			}
			if (productTransfer.getExporter() != null && productTransfer.getExporter().getId() > 0) {
				ExporterRegistration ex = utilService.findExportRegById(Long.valueOf(productTransfer.getExporter().getId()));
				if (ex != null) {
					productTransfer.setExporter(ex);
				}
			}
			productTransfer.setTruckNo(productTransfer.getTruckNo());
			productTransfer.setDriverName(productTransfer.getDriverName());
			productTransfer.setDriverLicenseNumber(productTransfer.getDriverLicenseNumber());
			
			productTransfer.setStatus(1);
			productTransfer.setType(ProductTransfer.type.PRODUCT_TRANSFER.ordinal());
			productTransfer.setLatitude(getLatitude());
			productTransfer.setLongitude(getLongitude());
			productTransfer.setIpAddr(getIpAddress());

			//utilService.save(productTransfer);
			utilService.saveProductTransfer(productTransfer);

			return REDIRECT;
		}

	}

	/**
	 * Delete.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */

	public String delete() throws Exception {
		String result = null;
		if (this.getId() != null && !(this.getId().equals(EMPTY))) {
			productTransfer = utilService.findProductTransferById(Long.valueOf(id));
			if (productTransfer == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(productTransfer)) {
				productTransfer.setStatus(0);
				
				Map<CityWarehouse, Double> existock = new HashMap<CityWarehouse, Double>();
				productTransfer.getProductTransferDetails().stream().forEach(uu -> {
					CityWarehouse ctt = (CityWarehouse) farmerService.findObjectById(
							"FROM CityWarehouse where batchNo=? and planting.id=? and coOperative.id=? AND isDelete=0",
							new Object[] { uu.getBatchNo(), uu.getPlanting().getId(), productTransfer.getTransferFrom().getId() });
					existock.put(ctt, (uu.getTransferredWeight()) * -1);
				});
				utilService.deleteProductTransfer(productTransfer, existock, new HashSet<>());
				
				result = REDIRECT;
			}
		}

		request.setAttribute(HEADING, getText("pcbplist"));
		return result;

	}

	/**
	 * Detail.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@Getter
	@Setter
	List<Object[]> ex;
	public String detail() throws Exception {
		String view = "";
		if (id != null && !id.equals("")) {
			productTransfer = utilService.findProductTransferById(Long.valueOf(id));
			
			setCurrentPage(getCurrentPage());
			command = UPDATE;
			
			if (productTransfer.getProductTransferDetails() != null) {
				productTransfer.getProductTransferDetails().stream().forEach(uu -> {
					CityWarehouse ctt = (CityWarehouse) farmerService.findObjectById(
							"FROM CityWarehouse where batchNo=? and planting.id=? AND isDelete=0",
							new Object[] { uu.getBatchNo(), uu.getPlanting().getId() });
					uu.setCtt(ctt);
				});
			}
			
			 ex = utilService.getAuditRecords("com.sourcetrace.eses.entity.ProductTransfer", productTransfer.getId());
			
			view = DETAIL;
			request.setAttribute(HEADING, getText("ProductTransferdetail"));
		} else {
			request.setAttribute(HEADING, getText("ProductTransferdetail"));
			return REDIRECT;
		}
		return view;
	}

	/**
	 * Update.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String update() throws Exception {

		if (id != null && !id.equals("") && productTransfer == null) {
			productTransfer = utilService.findProductTransferById(Long.valueOf(id));
			setCurrentPage(getCurrentPage());

			if(productTransfer.getExporter()!=null){
				setSelectedExporter(productTransfer.getExporter().getId().toString());
			}
			if(productTransfer.getTransferFrom()!=null){
				setSelectedTransferFrom(productTransfer.getTransferFrom().getId().toString());
			}
			if(productTransfer.getTransferTo()!=null){
				setSelectedTransferTo(productTransfer.getTransferTo().getId().toString());
			}
			
			if (productTransfer.getDate() != null) {
				SimpleDateFormat sm = new SimpleDateFormat(getGeneralDateFormat());
				setSelectedDate(sm.format(productTransfer.getDate()));
			}
			
			if (productTransfer.getProductTransferDetails() != null) {
				productTransfer.getProductTransferDetails().stream().forEach(uu -> {
					CityWarehouse ctt = (CityWarehouse) farmerService.findObjectById(
							"FROM CityWarehouse where batchNo=? and planting.id=? and coOperative.id=? and isDelete=0",
							new Object[] { uu.getBatchNo(), uu.getPlanting().getId(), productTransfer.getTransferFrom().getId() });
					if(ctt!=null){
						ctt.setSortedWeight(uu.getTransferredWeight()+ctt.getSortedWeight());
						uu.setCtt(ctt);
					}
					

				});
			}
			
			command = UPDATE;
			request.setAttribute(HEADING, getText("pcbpupdate"));
		} else {

			ProductTransfer tempProductTransfer = utilService.findProductTransferById(Long.valueOf(id));
			setCurrentPage(getCurrentPage());
			Map<CityWarehouse, Double> existock = new HashMap<CityWarehouse, Double>();
			tempProductTransfer.getProductTransferDetails().stream().forEach(uu -> {
				CityWarehouse ctt = (CityWarehouse) farmerService.findObjectById(
						"FROM CityWarehouse where batchNo=? and planting.id=? and coOperative.id=? AND isDelete=0",
						new Object[] { uu.getBatchNo(), uu.getPlanting().getId(), tempProductTransfer.getTransferFrom().getId() });
				existock.put(ctt, (uu.getTransferredWeight()) * -1);
			});
			
			Set<ProductTransferDetail> lpDetails = getProductTransferDetails(tempProductTransfer);
			
			tempProductTransfer.setBranchId(getBranchId());
			tempProductTransfer.setUpdatedDate(new Date());
			tempProductTransfer.setUpdatedUser(getUsername());

			if (!StringUtil.isEmpty(selectedDate)) {
				tempProductTransfer.setDate(DateUtil.convertStringToDate(selectedDate, getGeneralDateFormat()));
			} else {
				tempProductTransfer.setDate(null);
			}
			
			if (!StringUtil.isEmpty(selectedTransferFrom)) {
				Packhouse pack=utilService.findWarehouseById(Long.valueOf(selectedTransferFrom));
				if(pack!=null)
					tempProductTransfer.setTransferFrom(pack);
			}
			if (!StringUtil.isEmpty(selectedTransferTo)) {
				Packhouse pack=utilService.findWarehouseById(Long.valueOf(selectedTransferTo));
				if(pack!=null)
					tempProductTransfer.setTransferTo(pack);
			}
			
			if (productTransfer.getExporter() != null && productTransfer.getExporter().getId() > 0) {
				ExporterRegistration ex = utilService.findExportRegById(Long.valueOf(productTransfer.getExporter().getId()));
				if (ex != null) {
					tempProductTransfer.setExporter(ex);
				}
			}
			
			tempProductTransfer.setTruckNo(productTransfer.getTruckNo());
			tempProductTransfer.setDriverName(productTransfer.getDriverName());
			tempProductTransfer.setDriverLicenseNumber(productTransfer.getDriverLicenseNumber());
			
			lpDetails.stream().forEach(uu -> {
				if (existock.keySet().stream().anyMatch(cta -> cta.getId() == uu.getCtt().getId())) {
					CityWarehouse kk = existock.keySet().stream().filter(cta -> cta.getId() == uu.getCtt().getId())
							.findFirst().get();
					existock.put(kk, existock.get(kk) + uu.getTransferredWeight());
				} else {
					existock.put(uu.getCtt(), uu.getTransferredWeight());
				}
			});
			
			tempProductTransfer.getProductTransferDetails().clear();
			tempProductTransfer.getProductTransferDetails().addAll(lpDetails);
			utilService.updateProductTransfer(tempProductTransfer, existock, lpDetails);

			request.setAttribute(HEADING, getText("pcbplist"));
			return REDIRECT;

		}

		return super.execute();
	}

	public void populateValidate() {

		Map<String, String> errorCodes = new LinkedHashMap<String, String>();

		if (productTransfer != null) {
			if (productTransfer == null || StringUtil.isEmpty(selectedDate)) {
				errorCodes.put("empty.date", "empty.date");
			}
			if (productTransfer.getExporter() == null || productTransfer.getExporter().getId()==null || productTransfer.getExporter().getId() <= 0 ) {
				errorCodes.put("empty.exporter", "empty.exporter");
			}
			if (productTransfer == null || StringUtil.isEmpty(selectedTransferFrom)) {
				errorCodes.put("empty.transferFrom", "empty.transferFrom");
			}
			if (productTransfer == null || StringUtil.isEmpty(selectedTransferTo)) {
				errorCodes.put("empty.transferTo", "empty.transferTo");
			}
			
			if (productTransfer == null || StringUtil.isEmpty(productTransfer.getTruckNo())) {
				errorCodes.put("empty.truckNo", "empty.truckNo");
			}
			if (productTransfer == null || StringUtil.isEmpty(productTransfer.getDriverName())) {
				errorCodes.put("empty.driverName", "empty.driverName");
			}
			if (productTransfer == null || StringUtil.isEmpty(productTransfer.getDriverLicenseNumber())) {
				errorCodes.put("empty.driverLicenseNumber", "empty.driverLicenseNumber");
			}
			if (!StringUtil.isEmpty(selectedTransferFrom) && !StringUtil.isEmpty(selectedTransferTo)) {
				if(selectedTransferFrom!=null && selectedTransferTo!=null && selectedTransferFrom.equalsIgnoreCase(selectedTransferTo)){
					errorCodes.put("invalid.transferFromAndTransferTo", "invalid.transferFromAndTransferTo");
				}
			}
			if (productTransfer == null || StringUtil.isEmpty(productTransferListDetails)) {
				errorCodes.put("empty.productTransferListDetails", "empty.productTransferListDetails");
			}
		
		} else {
			errorCodes.put("empty.fields", getLocaleProperty("empty.fields"));
		}
		printErrorCodes(errorCodes);
	}
	
	public void populatePackhouseByExporter() throws Exception {
		if (!StringUtil.isEmpty(selectedExporter)) {
			List<Packhouse> gradeList = utilService.listOfPackhouseByExporterId(Long.valueOf(selectedExporter));
			JSONArray gradeArr = new JSONArray();
			if(!StringUtil.isListEmpty(gradeList)){
				gradeList.stream().filter(obj -> !ObjectUtil.isEmpty(obj)).forEach(obj -> {
					gradeArr.add(getJSONObject(obj.getId(), obj.getName()));
				});
			}
			sendAjaxResponse(gradeArr);
		}
	}
	
	public void populateReceiptNoBasedOnPackhouse() {
		JSONArray jsnArr = new JSONArray();
		if (selectedPackhouse != null && !StringUtil.isEmpty(selectedPackhouse) && StringUtil.isLong(selectedPackhouse)) {
			Packhouse pack = utilService.findWarehouseById(Long.valueOf(selectedPackhouse));
			if (pack != null) {
				List<Object[]> cwList = (List<Object[]>) farmerService.listObjectById(
						"select DISTINCT cw.batchNo,cw.batchNo from CityWarehouse cw"
						+ "  where cw.stockType=? and cw.coOperative.id=?"
						+ "  and cw.sortedWeight>0 and cw.isDelete=0",
						new Object[] { CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(),pack.getId() });
				cwList.stream().forEach(cw -> {
					jsnArr.add(getJSONObject(cw[0].toString(), cw[1].toString()));
				});
			}
		}
		sendAjaxResponse(jsnArr);
	}
	
	public void populateBlockIDByReceiptNo() {
		JSONArray jsnArr = new JSONArray();
		if (selectedPackhouse != null && !StringUtil.isEmpty(selectedPackhouse) && StringUtil.isLong(selectedPackhouse)
				&& selectedReceptionBatchID != null && !StringUtil.isEmpty(selectedReceptionBatchID) && StringUtil.isLong(selectedReceptionBatchID)) {
			List<Object[]> cwList = new ArrayList<>();
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
			 cwList = (List<Object[]>) farmerService.listObjectById(
					"select DISTINCT fc.id,fc.blockName,fc.blockId from CityWarehouse cw"
					+ " join cw.farmcrops fc join fc.farm fm where cw.stockType=? "
					+ "and cw.coOperative.id=? and cw.batchNo=?  and cw.sortedWeight>0 "
					+ "and cw.isDelete=0 and fc.exporter.id=?",
					new Object[] { CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(), 
					Long.valueOf(selectedPackhouse),selectedReceptionBatchID,Long.valueOf(getLoggedInDealer()) });
			}else{
				 cwList = (List<Object[]>) farmerService.listObjectById(
						"select DISTINCT fc.id,fc.blockName,fc.blockId from CityWarehouse cw "
						+ "join cw.farmcrops fc join fc.farm fm where cw.stockType=? and "
						+ "cw.coOperative.id=? and cw.batchNo=?  and cw.sortedWeight>0 and cw.isDelete=0",
						new Object[] { CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(), Long.valueOf(selectedPackhouse),
								selectedReceptionBatchID });
			}
			if (cwList != null) {
			cwList.stream().forEach(cw -> {
				jsnArr.add(getJSONObject(cw[0].toString(), cw[2].toString() + " - " + cw[1].toString()));
			});
			}
		}
		sendAjaxResponse(jsnArr);
	}
	
	/*public void populatePlanting() {
		JSONArray plantingarr = new JSONArray();
		if (selectedPackhouse != null && !ObjectUtil.isEmpty(selectedPackhouse) && !selectedPackhouse.equals("") 
		 && selectedBlockID != null && !ObjectUtil.isEmpty(selectedBlockID) && !selectedBlockID.equals("")) {

			LinkedList<Object> parame = new LinkedList();
			String qry = "SELECT DISTINCT f.id,f.plantingId from Sorting s join s.planting f where f.farmCrops.id=? and s.status=1 and f.status=1 ORDER BY f.id ASC";
			parame.add(Long.valueOf(selectedBlockID));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry = "SELECT DISTINCT f.id,f.plantingId FROM Sorting s join s.planting f  where f.farmCrops.id=?  and s.status=1 and f.status=1 and f.farmCrops.exporter.id=?  ORDER BY f.id ASC";
				parame.add(Long.valueOf(getLoggedInDealer()));
			}

			List<Object[]> growerList = (List<Object[]>) farmerService.listObjectById(qry, parame.toArray());
			growerList.stream().distinct().forEach(f -> {
				Planting planting1 = utilService.findPlantingById(Long.valueOf(f[0].toString()));
				if (planting1 != null) {
					List<Object[]> cwList1 = (List<Object[]>) farmerService.listObjectById(
							"select pl.id,cw.batchNo from CityWarehouse cw join cw.planting pl"
									+ "  where cw.stockType=? and cw.coOperative.id=? and pl.id=?  "
									+ "and cw.sortedWeight>0 and cw.isDelete=0",
									new Object[] { CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(),
											Long.valueOf(selectedPackhouse), planting1.getId() });
					if (!StringUtil.isListEmpty(cwList1)) {
						plantingarr.add(getJSONObject(f[0].toString(), f[1].toString()));
					}
				}
			});
		}
		sendAjaxResponse(plantingarr);
	}*/
	
	public void populatePlanting() {
		JSONArray plantingarr = new JSONArray();
		if (selectedPackhouse != null && !ObjectUtil.isEmpty(selectedPackhouse) && !selectedPackhouse.equals("") 
		 && selectedBlockID != null && !ObjectUtil.isEmpty(selectedBlockID) && !selectedBlockID.equals("")
			&& selectedReceptionBatchID != null && !ObjectUtil.isEmpty(selectedReceptionBatchID) && !selectedReceptionBatchID.equals("")) {

			LinkedList<Object> parame = new LinkedList();
			String qry = "SELECT DISTINCT f.id,f.plantingId from Sorting s join s.planting f where f.farmCrops.id=? and s.status=1 and f.status=1 ORDER BY f.id ASC";
			parame.add(Long.valueOf(selectedBlockID));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry = "SELECT DISTINCT f.id,f.plantingId FROM Sorting s join s.planting f  where f.farmCrops.id=?  and s.status=1 and f.status=1 and f.farmCrops.exporter.id=?  ORDER BY f.id ASC";
				parame.add(Long.valueOf(getLoggedInDealer()));
			}

			List<Object[]> growerList = (List<Object[]>) farmerService.listObjectById(qry, parame.toArray());
			growerList.stream().distinct().forEach(f -> {
				Planting planting1 = utilService.findPlantingById(Long.valueOf(f[0].toString()));
				if (planting1 != null) {
					List<Object[]> cwList1 = (List<Object[]>) farmerService.listObjectById(
							"select pl.id,cw.batchNo from CityWarehouse cw join cw.planting pl"
									+ "  where cw.stockType=? and cw.coOperative.id=? and pl.id=? and cw.batchNo=?"
									+ "and cw.sortedWeight>0 and cw.isDelete=0",
									new Object[] { CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(),
											Long.valueOf(selectedPackhouse), planting1.getId(),selectedReceptionBatchID });
					if (!StringUtil.isListEmpty(cwList1)) {
						plantingarr.add(getJSONObject(f[0].toString(), f[1].toString()));
					}
				}
			});
		}
		sendAjaxResponse(plantingarr);
	}
	
	public void populateBlockData() {
		JSONObject jss = new JSONObject();
		if (selectedReceptionBatchID != null && !StringUtil.isEmpty(selectedReceptionBatchID) && StringUtil.isLong(selectedReceptionBatchID) && 
			selectedPlantingID != null && !StringUtil.isEmpty(selectedPlantingID) && StringUtil.isLong(selectedPlantingID) &&
			selectedPackhouse != null && !StringUtil.isEmpty(selectedPackhouse) && StringUtil.isLong(selectedPackhouse)) {
			CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse cw where cw.batchNo=? and cw.planting.id=? and cw.coOperative.id=? and cw.stockType=? and cw.sortedWeight>0 and cw.isDelete=0",
					new Object[] { selectedReceptionBatchID,Long.valueOf(selectedPlantingID),Long.valueOf(selectedPackhouse),CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal()});
			if (cw != null) {
				jss.put("availableWeight", cw.getSortedWeight());
				jss.put("product", cw.getPlanting()!=null ? cw.getPlanting().getVariety().getName() : "");
				jss.put("variety", cw.getPlanting()!=null ? cw.getPlanting().getGrade().getName() : "");
			}
		}
		sendAjaxResponse(jss);
	}
	
	public Set<ProductTransferDetail> getProductTransferDetails(ProductTransfer productTransfer) {
		Set<ProductTransferDetail> productTransferDetails = new LinkedHashSet<>();
		if (!StringUtil.isEmpty(getProductTransferListDetails())) {
			List<String> ichList = Arrays.asList(getProductTransferListDetails().split("@"));

			ichList.stream().filter(obj -> !StringUtil.isEmpty(obj)).forEach(lp -> {
				ProductTransferDetail ichDtl = new ProductTransferDetail();
				List<String> list = Arrays.asList(lp.split("#"));
				//CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse  where id=?",
				//new Object[] { Long.valueOf(list.get(0)) });

				Planting planting = utilService.findPlantingById(Long.valueOf(list.get(2)));
				
			//	CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse cw where cw.batchNo=? and cw.planting.id=? and cw.stockType=? and cw.sortedWeight>0 and cw.isDelete=0",
						CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse cw where cw.batchNo=? and cw.planting.id=? and cw.stockType=? and cw.isDelete=0",
				new Object[] { list.get(0),planting.getId(),CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal()});
				
				if(cw!=null && planting.getPlantingId() == cw.getPlanting().getPlantingId()){
					cw.setSortedWeight(0.00);
				}
				cw.setFarmcrops(planting.getFarmCrops());
				cw.setPlanting(planting);
				ichDtl.setCtt(cw);
				
				ichDtl.setBatchNo(list.get(0));
				ichDtl.setPlanting(planting);
				ichDtl.setBlockId(planting.getFarmCrops());
				ichDtl.setProduct(list.get(3));
				ichDtl.setVariety(list.get(4));
				ichDtl.setAvailableWeight(Double.valueOf(list.get(5).toString()));
				ichDtl.setTransferredWeight(Double.valueOf(list.get(6).toString()));
				
				ichDtl.setStatus(1);
				
				ichDtl.setProductTransfer(productTransfer);
				productTransferDetails.add(ichDtl);
			});
		}
		return productTransferDetails;
	}

	public String findCitywarehouseByBatchNoPlantingIdAndCoOperative(String batchNo, String plantingId, String transferFromId) {
		String val = "";
		if(batchNo!=null && !StringUtil.isEmpty(batchNo) && plantingId!=null && !StringUtil.isEmpty(plantingId) && transferFromId!=null && !StringUtil.isEmpty(transferFromId)){
		CityWarehouse cw = (CityWarehouse) farmerService.findObjectById(
				"FROM CityWarehouse where batchNo=? and planting.id=? and coOperative.id=? AND isDelete=0",
				new Object[] { batchNo, Long.valueOf(plantingId), Long.valueOf(transferFromId) });
		if (cw!=null) {
			val = cw.getBatchNo();
		}}
		return val;
	}
	
}
