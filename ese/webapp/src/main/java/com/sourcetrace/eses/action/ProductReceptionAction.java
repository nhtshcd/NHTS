package com.sourcetrace.eses.action;

import java.text.SimpleDateFormat;
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
import com.sourcetrace.eses.entity.PackhouseIncoming;
import com.sourcetrace.eses.entity.PackhouseIncomingDetails;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.entity.ProductTransfer;
import com.sourcetrace.eses.entity.ProductTransferDetail;
import com.sourcetrace.eses.entity.RecallDetails;
import com.sourcetrace.eses.entity.ShipmentDetails;
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
public class ProductReceptionAction extends SwitchAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ProductReceptionAction.class);

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
	private ProductTransfer productReception;

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
	private String productTransferListDetails;
	
	@Getter
	@Setter
	private String selectedTransferReceiptID;
	
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
		if (productReception == null) {
			command = "create";

			request.setAttribute(HEADING, getText("productTransfercreate"));

			if (request != null && request.getSession() != null) {
				request.getSession().setAttribute(ISecurityFilter.REPORT, "PRODUCTTRANSFER");
			}
			
			return INPUT;
		} else {
			productReception.setCreatedDate(new Date());
			productReception.setBranchId(getBranchId() != null ? getBranchId() : defBranchId);
			productReception.setCreatedUser(getUsername());
			
			productReception.setBatchNo(DateUtil.getDateTimWithMinsec());
			//productReception.setBatchNo(selectedTransferReceiptID);
			productReception.setTransferReceiptID(selectedTransferReceiptID);
			
			if (productTransferListDetails != null && !StringUtil.isEmpty(productTransferListDetails)) {
				Set<ProductTransferDetail> phIncomingDetails = getProductTransferDetails(productReception);
				productReception.setProductTransferDetails(phIncomingDetails);
			}
			
			if (!StringUtil.isEmpty(selectedDate)) {
				productReception.setDate(DateUtil.convertStringToDate(selectedDate, getGeneralDateFormat()));
			} else {
				productReception.setDate(null);
			}
			
			if (!StringUtil.isEmpty(selectedTransferFrom)) {
				Packhouse pack=utilService.findWarehouseById(Long.valueOf(selectedTransferFrom));
				if(pack!=null)
					productReception.setTransferFrom(pack);
			}
			if (!StringUtil.isEmpty(selectedTransferTo)) {
				Packhouse pack=utilService.findWarehouseById(Long.valueOf(selectedTransferTo));
				if(pack!=null)
					productReception.setTransferTo(pack);
			}
			productReception.setTruckNo(productReception.getTruckNo());
			productReception.setDriverName(productReception.getDriverName());
			productReception.setDriverLicenseNumber(productReception.getDriverLicenseNumber());
			
			productReception.setStatus(1);
			productReception.setType(ProductTransfer.type.PRODUCT_RECEPTION.ordinal());
			productReception.setLatitude(getLatitude());
			productReception.setLongitude(getLongitude());
			
			utilService.saveProductReception(productReception);

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
			productReception = utilService.findProductTransferById(Long.valueOf(id));
			if (productReception == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(productReception)) {
				productReception.setStatus(0);
				
				Map<CityWarehouse, Double> existock = new HashMap<CityWarehouse, Double>();
				productReception.getProductTransferDetails().stream().forEach(uu -> {
					CityWarehouse ctt = (CityWarehouse) farmerService.findObjectById(
							"FROM CityWarehouse where batchNo=? and planting.id=? and coOperative.id=? AND isDelete=0",
							new Object[] { uu.getBatchNo(), uu.getPlanting().getId(), productReception.getTransferFrom().getId() });
					existock.put(ctt, (uu.getTransferredWeight()) * -1);
				});
				utilService.deleteProductReception(productReception, existock, new HashSet<>());
				
				result = REDIRECT;
			}
		}
		request.setAttribute(HEADING, getText("productReceptionlist"));
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
			productReception = utilService.findProductTransferById(Long.valueOf(id));
			
			 ex = utilService.getAuditRecords("com.sourcetrace.eses.entity.ProductTransfer", productReception.getId());
			
			setCurrentPage(getCurrentPage());
			command = UPDATE;
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

		if (id != null && !id.equals("") && productReception == null) {
			productReception = utilService.findProductTransferById(Long.valueOf(id));
			setCurrentPage(getCurrentPage());
			
			if(productReception.getTransferFrom()!=null){
				setSelectedTransferFrom(productReception.getTransferFrom().getId().toString());
			}
			if(productReception.getTransferTo()!=null){
				setSelectedTransferTo(productReception.getTransferTo().getId().toString());
			}
			
			if (productReception.getDate() != null) {
				SimpleDateFormat sm = new SimpleDateFormat(getGeneralDateFormat());
				setSelectedDate(sm.format(productReception.getDate()));
			}
			
			if(productReception.getTransferReceiptID()!=null){
				setSelectedTransferReceiptID(productReception.getTransferReceiptID());
			}
			
			command = UPDATE;
			request.setAttribute(HEADING, getText("pcbpupdate"));
		} else {

			ProductTransfer tempProductReception = utilService.findProductTransferById(Long.valueOf(id));
			setCurrentPage(getCurrentPage());
			Map<CityWarehouse, Double> existock = new HashMap<CityWarehouse, Double>();
			tempProductReception.getProductTransferDetails().stream().forEach(uu -> {
				CityWarehouse ctt = (CityWarehouse) farmerService.findObjectById(
						"FROM CityWarehouse where batchNo=? and planting.id=? and coOperative.id=? AND isDelete=0",
						new Object[] { uu.getBatchNo(), uu.getPlanting().getId(), tempProductReception.getTransferFrom().getId() });
				existock.put(ctt, (uu.getReceivedWeight()) * -1);
			});
			
			Set<ProductTransferDetail> lpDetails = getProductTransferDetails(tempProductReception);
			
			tempProductReception.setBranchId(getBranchId());
			tempProductReception.setUpdatedDate(new Date());
			tempProductReception.setUpdatedUser(getUsername());

			if (!StringUtil.isEmpty(selectedDate)) {
				tempProductReception.setDate(DateUtil.convertStringToDate(selectedDate, getGeneralDateFormat()));
			} else {
				tempProductReception.setDate(null);
			}
			
			if (!StringUtil.isEmpty(selectedTransferFrom)) {
				Packhouse pack=utilService.findWarehouseById(Long.valueOf(selectedTransferFrom));
				if(pack!=null)
					tempProductReception.setTransferFrom(pack);
			}
			if (!StringUtil.isEmpty(selectedTransferTo)) {
				Packhouse pack=utilService.findWarehouseById(Long.valueOf(selectedTransferTo));
				if(pack!=null)
					tempProductReception.setTransferTo(pack);
			}
			tempProductReception.setTruckNo(productReception.getTruckNo());
			tempProductReception.setDriverName(productReception.getDriverName());
			tempProductReception.setDriverLicenseNumber(productReception.getDriverLicenseNumber());
			
			lpDetails.stream().forEach(uu -> {
				if (existock.keySet().stream().anyMatch(cta -> cta.getId() == uu.getCtt().getId())) {
					CityWarehouse kk = existock.keySet().stream().filter(cta -> cta.getId() == uu.getCtt().getId())
							.findFirst().get();
					existock.put(kk, existock.get(kk) + uu.getReceivedWeight());
				} else {
					existock.put(uu.getCtt(), uu.getReceivedWeight());
				}
			});
			
			tempProductReception.getProductTransferDetails().clear();
			
			utilService.updateProductReception(tempProductReception, existock, lpDetails);
			
			lpDetails.stream().forEach(uu -> {
				utilService.saveOrUpdate(uu);
			});

			request.setAttribute(HEADING, getText("pcbplist"));
			return REDIRECT;
		}
		return super.execute();
	}

	public void populateValidate() {

		Map<String, String> errorCodes = new LinkedHashMap<String, String>();

		if (productReception != null) {
			if (productReception == null || StringUtil.isEmpty(selectedTransferReceiptID)) {
				errorCodes.put("empty.selectedTransferReceiptID", "empty.selectedTransferReceiptID");
			}
			if (productReception == null || StringUtil.isEmpty(selectedDate)) {
				errorCodes.put("empty.date", "empty.date");
			}
			if (productReception == null || StringUtil.isEmpty(selectedTransferFrom)) {
				errorCodes.put("empty.transferFrom", "empty.transferFrom");
			}
			if (productReception == null || StringUtil.isEmpty(selectedTransferTo)) {
				errorCodes.put("empty.transferTo", "empty.transferTo");
			}
			
			if (productReception == null || StringUtil.isEmpty(productReception.getTruckNo())) {
				errorCodes.put("empty.truckNo", "empty.truckNo");
			}
			if (productReception == null || StringUtil.isEmpty(productReception.getDriverName())) {
				errorCodes.put("empty.driverName", "empty.driverName");
			}
			if (productReception == null || StringUtil.isEmpty(productReception.getDriverLicenseNumber())) {
				errorCodes.put("empty.driverLicenseNumber", "empty.driverLicenseNumber");
			}
			if (productReception == null || StringUtil.isEmpty(productTransferListDetails)) {
				errorCodes.put("empty.productTransferListDetails", "empty.productTransferListDetails");
			}else{
				List<String> productlistd = Arrays.asList(getProductTransferListDetails().split("@"));
				int[] index = { 0 };
				productlistd.stream().filter(obj -> !StringUtil.isEmpty(obj)).forEach(lp -> {
					List<String> list = Arrays.asList(lp.split("#"));
					productlistd.get(index[0]++);
					int currentIndex = index[0];
					if(list.get(7).equalsIgnoreCase("0") || list.get(7).toString() == null || StringUtil.isEmpty(list.get(7).toString())){
						errorCodes.put("invalid.receivedWt.zero"+currentIndex, "Row" + currentIndex + ": "+getLocaleProperty("invalid.receivedWt.zero"));
					}else if(Double.valueOf(list.get(6)) < Double.valueOf(list.get(7))){
						errorCodes.put("invalid.receivedWt.transferWt"+currentIndex, "Row" + currentIndex + ": "+getLocaleProperty("invalid.receivedWt.transferWt"));
					}
				});
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

	public void populateProductTransferData() {
		SimpleDateFormat sdf=new SimpleDateFormat(getGeneralDateFormat());
		JSONObject jss = new JSONObject();
		JSONArray productTransferDetailsJSON = new JSONArray();

		if (selectedTransferReceiptID != null && !StringUtil.isEmpty(selectedTransferReceiptID) && selectedTransferReceiptID != null && !StringUtil.isEmpty(selectedTransferReceiptID)) {
			
			ProductTransfer pt = (ProductTransfer) farmerService.findObjectById("FROM ProductTransfer pt WHERE pt.batchNo=? AND pt.status=1 and pt.type=0",
						new Object[] {selectedTransferReceiptID});
				
			if (pt != null) {
				jss.put("date", sdf.format(pt.getDate()));
				jss.put("transferFrom", pt.getTransferFrom()!=null ? pt.getTransferFrom().getName() : "");
				jss.put("transferFromId", pt.getTransferFrom()!=null ? pt.getTransferFrom().getId() : "");
				jss.put("transferTo", pt.getTransferTo()!=null ? pt.getTransferTo().getName() : "");
				jss.put("transferToId", pt.getTransferTo()!=null ? pt.getTransferTo().getId() : "");
				jss.put("truckNo", pt.getTruckNo());
				jss.put("driverName", pt.getDriverName());
				jss.put("driverLicenseNumber", pt.getDriverLicenseNumber());
				
				if (pt != null) {
					if(command.equalsIgnoreCase("create")){
					List<Object[]> cwList = (List<Object[]>) farmerService.listObjectById(
							"select cw.id,cw.batchNo,pl.farmCrops.blockId,pl.plantingId,produst.name,vari.name,cw.sortedWeight,pl.id,cw.lossWeight from CityWarehouse cw join cw.planting pl join pl.variety produst join pl.grade vari"
									+ "  where cw.stockType=? and cw.batchNo=? "
									+ "and cw.sortedWeight>0 and cw.isDelete=0 AND cw.packBatch!=null",
									new Object[] { CityWarehouse.Stock_type.PRODUCT_TRANSFER.ordinal(),
											selectedTransferReceiptID});
					if (cwList != null) {
						cwList.stream().forEach(cw -> {
							JSONObject productTransferDetail = new JSONObject();
							productTransferDetail.put("receptionBatchID",cw[1]);
							productTransferDetail.put("blockID", cw[2]);
							productTransferDetail.put("plantingID", cw[3]);
							productTransferDetail.put("product", cw[4]);
							productTransferDetail.put("variety", cw[5]);
							productTransferDetail.put("transferredWeight",cw[6]);
							productTransferDetail.put("cid", cw[0]);
							productTransferDetail.put("plantId", cw[7]);
							productTransferDetail.put("receivedWeight", cw[8]!=null ? cw[8] : "");
							productTransferDetailsJSON.add(productTransferDetail);
						   });
						}
				}else if(command.equalsIgnoreCase("update")){
					List<Object[]> cwList = (List<Object[]>) farmerService.listObjectById(
							"select cw.id,cw.batchNo,pl.farmCrops.blockId,pl.plantingId,produst.name,vari.name,cw.sortedWeight,pl.id,cw.lossWeight,cw.harvestedWeight from CityWarehouse cw join cw.planting pl join pl.variety produst join pl.grade vari"
							+ "  where cw.stockType=? and cw.batchNo=? and cw.isDelete=0",
									new Object[] { CityWarehouse.Stock_type.PRODUCT_TRANSFER.ordinal(),
											selectedTransferReceiptID});
					if (cwList != null) {
						cwList.stream().forEach(cw -> {
							JSONObject productTransferDetail = new JSONObject();
							productTransferDetail.put("receptionBatchID",cw[1]);
							productTransferDetail.put("blockID", cw[2]);
							productTransferDetail.put("plantingID", cw[3]);
							productTransferDetail.put("product", cw[4]);
							productTransferDetail.put("variety", cw[5]);
							productTransferDetail.put("cid", cw[0]);
							productTransferDetail.put("plantId", cw[7]);
							//productTransferDetail.put("transferredWeight",cw[6]);
							productTransferDetail.put("transferredWeight",(Double.valueOf(cw[8].toString())+Double.valueOf(cw[9].toString())));
							productTransferDetail.put("receivedWeight", cw[8]!=null ? cw[8] : "");
							productTransferDetailsJSON.add(productTransferDetail);
						   });
						}
				}
				}
			}
		}
		jss.put("productTransferDetails", productTransferDetailsJSON);
		sendAjaxResponse(jss);
	}
	
	public Set<ProductTransferDetail> getProductTransferDetails(ProductTransfer productTransfer) {
		Set<ProductTransferDetail> productTransferDetails = new LinkedHashSet<>();
		if (!StringUtil.isEmpty(getProductTransferListDetails())) {
			List<String> ichList = Arrays.asList(getProductTransferListDetails().split("@"));
			
			ichList.stream().filter(obj -> !StringUtil.isEmpty(obj)).forEach(lp -> {
				ProductTransferDetail ichDtl = new ProductTransferDetail();
				List<String> list = Arrays.asList(lp.split("#"));
				Planting planting = utilService.findPlantingById(Long.valueOf(list.get(3)));
				CityWarehouse cw = (CityWarehouse) farmerService.findObjectById("FROM CityWarehouse cw where cw.id=?",
						new Object[] { Long.valueOf(list.get(1))});
				
				if(planting.getPlantingId() == cw.getPlanting().getPlantingId()){
					cw.setSortedWeight(0.00);
				}
				cw.setFarmcrops(planting.getFarmCrops());
				cw.setPlanting(planting);
				ichDtl.setCtt(cw);
				
				ichDtl.setBatchNo(cw.getBatchNo());
				ichDtl.setPlanting(planting);
				ichDtl.setBlockId(planting.getFarmCrops());
				ichDtl.setProduct(list.get(4));
				ichDtl.setVariety(list.get(5));
				ichDtl.setTransferredWeight(Double.valueOf(list.get(6).toString()));
				ichDtl.setReceivedWeight(Double.valueOf(list.get(7).toString()));
				ichDtl.setStatus(1);
				ichDtl.setProductTransfer(productTransfer);
				productTransferDetails.add(ichDtl);
			});
		}
		return productTransferDetails;
	}

	
	public Map<String, String> getTransferReceiptIDList() {
		Map<String, String> transferReceiptIDList = new LinkedHashMap<>();
		if("create".equalsIgnoreCase(command)){
			/*List<Object[]> cwList = (List<Object[]>) farmerService.listObjectById(
					"select cw.batchNo,cw.batchNo from CityWarehouse cw where cw.stockType=? and cw.sortedWeight>0 and cw.isDelete=0",
					new Object[] { CityWarehouse.Stock_type.PRODUCT_TRANSFER.ordinal()});
			 cwList.stream().forEach(cw -> {
					transferReceiptIDList.put(cw[0].toString(), cw[1].toString());
				});*/
			LinkedList<Object> parame = new LinkedList();
			String qry = "select cw.batchNo,cw.batchNo from CityWarehouse cw where cw.stockType=? and cw.sortedWeight>0 and cw.isDelete=0";
			parame.add(CityWarehouse.Stock_type.PRODUCT_TRANSFER.ordinal());
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				   qry = "select cw.batchNo,cw.batchNo from CityWarehouse cw where cw.stockType=? and cw.sortedWeight>0 and cw.isDelete=0 and cw.farmcrops.exporter.id=?";
				parame.add(Long.valueOf(getLoggedInDealer()));
			}
			List<Object[]> cwList = (List<Object[]> ) farmerService.listObjectById(qry, parame.toArray());
			cwList.stream().forEach(cw -> {
				transferReceiptIDList.put(cw[0].toString(), cw[1].toString());
			});
		}else if("update".equalsIgnoreCase(command)){
			List<Object[]> cwList = (List<Object[]>) farmerService.listObjectById(
					"select cw.batchNo,cw.batchNo from CityWarehouse cw where cw.stockType=? and cw.isDelete=0",
					new Object[] { CityWarehouse.Stock_type.PRODUCT_TRANSFER.ordinal()});
		 cwList.stream().forEach(cw -> {
				transferReceiptIDList.put(cw[0].toString(), cw[1].toString());
			});
		}
		
		return transferReceiptIDList;
	}
	
}
