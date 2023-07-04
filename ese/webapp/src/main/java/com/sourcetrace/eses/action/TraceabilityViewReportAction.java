package com.sourcetrace.eses.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sourcetrace.eses.entity.AgroTransaction;
import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.Harvest;
import com.sourcetrace.eses.entity.Scouting;
import com.sourcetrace.eses.entity.Shipment;
import com.sourcetrace.eses.entity.ShipmentDetails;
import com.sourcetrace.eses.entity.SprayAndFieldManagement;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ICryptoUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;
import net.glxn.qrgen.QRCode;

@Component
@Scope("prototype")
public class TraceabilityViewReportAction extends BaseReportAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TraceabilityViewReportAction.class);
	protected static final String CREATE = "create";
	protected static final String DETAIL = "detail";
	protected static final String UPDATE = "update";
	protected static final String MAPPING = "mapping";
	protected static final String DELETE = "delete";
	protected static final String LIST = "list";
	protected static final String TITLE_PREFIX = "title.";
	protected static final String HEADING = "heading";

	protected List<String> fields = new ArrayList<String>();
	int serialNo = 1;
	@Autowired
	private IFarmerService farmerService;

	public String selectedDistillationBatchNo;

	public String list() {
		Calendar currentDate = Calendar.getInstance();
		Calendar cal = (Calendar) currentDate.clone();
		cal.set(Calendar.MONTH, currentDate.get(Calendar.MONTH) - 1);
		DateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT);
		super.startDate = df.format(cal.getTime());
		super.endDate = df.format(currentDate.getTime());
		// daterange = super.startDate + " - " + super.endDate;
		request.setAttribute(HEADING, getText(LIST));
		return LIST;

	}

	public void processBatchNo() throws Exception {
		DateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
		if (!StringUtil.isEmpty(selectedDistillationBatchNo)) {
			Shipment shipment = (Shipment) farmerService.findObjectById(
					"FROM Shipment s WHERE s.pConsignmentNo=? AND (s.status=? OR s.status=?)",
					new Object[] { selectedDistillationBatchNo, 1,3 });
			JSONArray finalProductArray = new JSONArray();

			if (!ObjectUtil.isEmpty(shipment)) {
				JSONObject finalProductObj = new JSONObject();
				JSONArray finalProductChild = new JSONArray();

				finalProductObj.put("name", getLocaleProperty("service.shipment") + " | Shipment Date : "
						+ df.format(shipment.getShipmentDate()) + " | Packhouse : " + shipment.getPackhouse().getName()
						+ " | Export License No: " + shipment.getPackhouse().getExporter().getRefLetterNo()
						+ " | Buyer: " + shipment.getCustomer().getCustomerName() + " | UCR Kentrade: "
						+ shipment.getPConsignmentNo() + " | Shipment User: " + shipment.getCreatedUser());
				finalProductObj.put("iconOpen", "img/warehouse.png");

				finalProductObj.put("open", true);

				shipment.getShipmentDetails().forEach(pms -> {
					if (pms.getCityWarehouse() != null) {
						finalProductChild.add(formPmtst(pms));
					}

				});

				finalProductObj.put("children", finalProductChild);
				finalProductArray.add(finalProductObj);
				sendAjaxResponse(finalProductArray);
			}

		}

	}

	@SuppressWarnings("unchecked")
	public JSONObject formPmtst(ShipmentDetails pms) {
		DateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
		// DateFormat dft = new SimpleDateFormat(DateUtil.DATE_FORMAT_4);
		JSONObject blendingObj = new JSONObject();

		JSONArray blendingCHild = new JSONArray();
		String txmyyt = getLocaleProperty("service.traceability.shipmentdetail");
		String batrch = " | Batch No : " + pms.getCityWarehouse().getBatchNo();
		String imgname = "processImg1";
		String lastharvestDate = "";

		if (pms.getCityWarehouse().getLastHarvestDate() != null
				&& !pms.getCityWarehouse().getLastHarvestDate().equals("")) {
			lastharvestDate = " | Last Harvested Date: " + df.format(pms.getCityWarehouse().getLastHarvestDate());
		}

		blendingObj.put("name", txmyyt + " | Lot Number : " + pms.getCityWarehouse().getBatchNo() + " | Block Id : "
				+ pms.getCityWarehouse().getFarmcrops().getBlockId() + " | Block Name : "
				+ pms.getCityWarehouse().getFarmcrops().getBlockName() + " | Product : "
				+ pms.getCityWarehouse().getPlanting().getVariety().getName() + " | Variety : "
				+ pms.getCityWarehouse().getPlanting().getGrade().getName() + " | Packing Unit : "
				+ getCatalgueNameByCode(pms.getPackingUnit()) + " | Packing Qty(KG) : " + pms.getPackingQty()
				+ " | Farm : " + pms.getCityWarehouse().getFarmcrops().getFarm().getFarmName()
				+ " | Farmer Trace Code : " + pms.getCityWarehouse().getFarmcrops().getFarm().getFarmer().getFarmerId()
				+ " | Farmer Name : " + pms.getCityWarehouse().getFarmcrops().getFarm().getFarmer().getFirstName()
				+ " | Planting Date : " + df.format(pms.getCityWarehouse().getPlanting().getPlantingDate())
				+ lastharvestDate + " | Farm County : " + pms.getCityWarehouse().getFarmcrops().getFarm().getVillage()
						.getCity().getLocality().getState().getName());
		blendingObj.put("iconOpen", "img/farmers-ico.png");
		blendingObj.put("iconClose", "img/farmers-ico.png");

		List<Scouting> scouting = (List<Scouting>) farmerService.listObjectById(
				"FROM Scouting s WHERE s.planting.id=? and s.status=0 order by s.receivedDate desc ",
				new Object[] { pms.getCityWarehouse().getPlanting().getId() });
		if (scouting != null && !scouting.isEmpty()) {
			scouting.stream().forEach(uu -> {
				blendingCHild.add(formPmtsts(uu));
			});

		} else {
			blendingCHild.add(new JSONObject());

		}

		List<SprayAndFieldManagement> SprayAndFieldManagement = (List<SprayAndFieldManagement>) farmerService
				.listObjectById(
						"FROM SprayAndFieldManagement s WHERE s.planting.id=? and s.deleteStatus=0 order by s.date desc",
						new Object[] { pms.getCityWarehouse().getPlanting().getId() });
		if (SprayAndFieldManagement != null && !SprayAndFieldManagement.isEmpty()) {
			SprayAndFieldManagement.stream().forEach(uu -> {
				blendingCHild.add(formPmtstss(uu));
			});

		} else {
			blendingCHild.add(new JSONObject());

		}

		/*
		 * Harvest = (Harvest)
		 * farmerService.findObjectById("FROM Harvest s WHERE s.farmCrops.id=?",
		 * new Object[] { pms.getCityWarehouse().getFarmcrops().getId() });
		 * if(Harvest!=null){ blendingCHild.add(formPmtharvest(Harvest)); }
		 */

		// blendingCHild.add(formPmtsts(pms));
		blendingObj.put("open", true);
		blendingObj.put("children", blendingCHild);

		return blendingObj;

	}

	public JSONObject formPmtsts(Scouting scouting2) {
		DateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
		DateFormat dft = new SimpleDateFormat(DateUtil.DATE_FORMAT_4);
		JSONObject blendingObj = new JSONObject();
		JSONArray blendingCHild = new JSONArray();
		// scouting = (Scouting) farmerService.findObjectById("FROM Scouting s
		// WHERE s.farmCrops.id=?", new Object[] { scouting2 });
		String txmyyt = getLocaleProperty("service.traceability.scouting");
		String batrch = " | Batch No : " + scouting2.getFarmerFullName();
		String imgname = "processImg";
		String batrchs = "";
		String recommandation = "";
		String blockid = "";

		if (scouting2.getReceivedDate() != null) {
			batrch = " | Date of Scouting: " + df.format(scouting2.getReceivedDate());
		}

		if (scouting2.getRecommendations() != null && !scouting2.getRecommendations().equals("")) {
			recommandation = " | Recommendations: " + scouting2.getRecommendations();
		}

		if (scouting2.getPlanting().getFarmCrops().getBlockId() != null) {
			blockid = " | Block Id: " + scouting2.getPlanting().getFarmCrops().getBlockId();
		}
		String testdata = txmyyt + batrch + " | Block : " + scouting2.getPlanting().getFarmCrops().getBlockName()
				+ " | Crop Planting Id : " + scouting2.getPlanting().getPlantingId() + recommandation;
		if (scouting2.getNameOfInsectsObserved() != null && !StringUtil.isEmpty(scouting2.getNameOfInsectsObserved())) {
			testdata += " | Insects Observed : " + getCatlogueValueByCodeArray(scouting2.getNameOfInsectsObserved());
		}

		if (scouting2.getNameOfWeeds() != null && !StringUtil.isEmpty(scouting2.getNameOfWeeds())) {
			testdata += " | Weeds Observed : " + getCatlogueValueByCodeArray(scouting2.getNameOfWeeds());
		}

		if (scouting2.getNameOfDisease() != null && !StringUtil.isEmpty(scouting2.getNameOfDisease())) {
			testdata += " | Disease Observed : " + getCatlogueValueByCodeArray(scouting2.getNameOfDisease());
		}
		testdata += " | Area Irrigated : " + scouting2.getAreaIrrrigated();
		blendingObj.put("name", testdata);
		blendingObj.put("iconOpen", "img/activity-details-bg.png");
		blendingObj.put("iconClose", "img/activity-details-bg.png");

		/*
		 * pms2.getFarmerDetails().forEach(pms -> { if(pms.getFarmerId()!=null
		 * && !pms.getFarmerId().equals("")){
		 * blendingCHild.add(formPmtstsss(pms)); }
		 * 
		 * });
		 */

		// blendingCHild.add(formPmtst(bl));
		blendingObj.put("open", true);
		blendingObj.put("children", blendingCHild);

		return blendingObj;

	}

	public JSONObject formPmtstss(SprayAndFieldManagement scouting2) {
		DateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
		DateFormat dft = new SimpleDateFormat(DateUtil.DATE_FORMAT_4);
		JSONObject blendingObj = new JSONObject();
		JSONArray blendingCHild = new JSONArray();
		// scouting = (Scouting) farmerService.findObjectById("FROM Scouting s
		// WHERE s.farmCrops.id=?", new Object[] { scouting2 });
		String txmyyt = getLocaleProperty("service.traceability.spraying");
		String batrch = " | Batch No : " + scouting2.getAgroChemicalName();
		String imgname = "processImg";
		String batrchs = "";
		String chemical = "";
		String Dosage = "";
		String uom = "";
		String phi = "";

		if (scouting2.getDateOfSpraying() != null) {
			batrch = " | Date of Spraying: " + df.format(scouting2.getDateOfSpraying());
		}

		if (scouting2.getPcbp() != null) {
			chemical = " | Chemical Name : " + scouting2.getPcbp().getTradeName();
		}

		if (scouting2.getDosage() != null && !scouting2.getDosage().equals("")) {
			Dosage = " | Dosage : " + scouting2.getDosage();
		}

		if (scouting2.getUom() != null && !scouting2.getUom().equals("")) {
			uom = " | UOM : " + getCatalgueNameByCode(scouting2.getUom());
		}

		if (scouting2.getPhi() != null && !scouting2.getPhi().equals("")) {
			phi = " | PHI : " + scouting2.getPhi();
		}

		blendingObj.put("name", txmyyt + batrch + chemical + Dosage + uom + phi);
		blendingObj.put("iconOpen", "img/activity-details-bg.png");
		blendingObj.put("iconClose", "img/activity-details-bg.png");

		/*
		 * pms2.getFarmerDetails().forEach(pms -> { if(pms.getFarmerId()!=null
		 * && !pms.getFarmerId().equals("")){
		 * blendingCHild.add(formPmtstsss(pms)); }
		 * 
		 * });
		 */

		// blendingCHild.add(formPmtst(bl));
		blendingObj.put("open", true);
		blendingObj.put("children", blendingCHild);

		return blendingObj;

	}

	/*
	 * public JSONObject formPmtharvest(Harvest harv) { DateFormat df = new
	 * SimpleDateFormat(DateUtil.DATE_FORMAT_2); JSONObject blendingObj = new
	 * JSONObject(); JSONArray blendingCHild = new JSONArray(); //scouting =
	 * (Scouting)
	 * farmerService.findObjectById("FROM Scouting s WHERE s.farmCrops.id=?",
	 * new Object[] { scouting2 }); String txmyyt
	 * =getLocaleProperty("service.traceability.harvest"); String batrch ="";
	 * String imgname ="processImg"; String blockname = ""; String qty = "";
	 * String uom = ""; String phi = "";
	 * 
	 * if(harv.getDate()!=null){ batrch =" | Date Harvested: "+
	 * df.format(harv.getDate()); }
	 * 
	 * if(harv.getDate()!=null){ blockname =" | Block ID : "+
	 * harv.getProduceId(); }
	 * 
	 * if(harv.getQtyHarvested()!=null && !harv.getQtyHarvested().equals("")){
	 * qty =" | Quantity Harvested : "+ harv.getQtyHarvested(); }
	 * 
	 * if(harv.getExpctdYieldsVolume()!=null &&
	 * !harv.getExpctdYieldsVolume().equals("")){ uom
	 * =" | Expected Yields in Volume per variety : "+
	 * harv.getExpctdYieldsVolume(); }
	 * 
	 * 
	 * 
	 * blendingObj.put("name", txmyyt+ batrch+qty+uom);
	 * blendingObj.put("iconOpen",
	 * "plugins/zTree/css/zTreeStyle/img/diy/farmer.png");
	 * blendingObj.put("iconClose",
	 * "plugins/zTree/css/zTreeStyle/img/diy/1_close.png");
	 * 
	 * 
	 * pms2.getFarmerDetails().forEach(pms -> { if(pms.getFarmerId()!=null &&
	 * !pms.getFarmerId().equals("")){ blendingCHild.add(formPmtstsss(pms)); }
	 * 
	 * });
	 * 
	 * //blendingCHild.add(formPmtst(bl)); blendingObj.put("open", true);
	 * blendingObj.put("children", blendingCHild);
	 * 
	 * 
	 * return blendingObj;
	 * 
	 * }
	 */

}