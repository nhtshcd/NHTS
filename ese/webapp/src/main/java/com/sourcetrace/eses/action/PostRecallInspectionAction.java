package com.sourcetrace.eses.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.PostRecallInspection;
import com.sourcetrace.eses.entity.RecallDetails;
import com.sourcetrace.eses.entity.Recalling;
import com.sourcetrace.eses.entity.Shipment;
import com.sourcetrace.eses.entity.ShipmentDetails;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class PostRecallInspectionAction extends SwitchAction {
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
	private String LOT_NO_QUERY = "FROM Recalling rc WHERE kenyaTraceCode is not NULL";
	private String RECALLING_QUERY = "FROM Recalling cw WHERE cw.id=?";

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
	private PostRecallInspection postRecallInspection;
	@Getter
	@Setter
	private String inspectionDate;

	@Getter
	@Setter
	private String pid;

	@Getter
	@Setter
	private String selectedLotNo;

	@Getter
	@Setter
	private Recalling recalling;
	
	@Getter
	@Setter
	private String roleID;

	public String create() throws Exception {
		if (postRecallInspection == null) {
			command = CREATE;
			request.setAttribute(HEADING, "postRecallInspectioncreate");
			return INPUT;
		} else {
			if (!StringUtil.isEmpty(inspectionDate)) {
				postRecallInspection
						.setInspectionDate(DateUtil.convertStringToDate(inspectionDate, getGeneralDateFormat()));
			} else {
				postRecallInspection.setInspectionDate(null);
			}
			if (selectedLotNo != null && StringUtil.isLong(selectedLotNo)) {
				Recalling rec = (Recalling) farmerService.findObjectById(
						"FROM Recalling rec WHERE rec.id=? AND rec.status=?",
						new Object[] { Long.valueOf(selectedLotNo), 1 });

				postRecallInspection.setRecall(rec);
				rec.setRecallingStatus("1");
				utilService.update(rec);
			}
			postRecallInspection.setNameOfAgency(postRecallInspection.getNameOfAgency());
			postRecallInspection.setNameOfInspector(postRecallInspection.getNameOfInspector());
			postRecallInspection.setMobileNumber(postRecallInspection.getMobileNumber());
			postRecallInspection.setOperatorBeingInspected(postRecallInspection.getOperatorBeingInspected());
			postRecallInspection.setNatureOfRecall(postRecallInspection.getNatureOfRecall());
			postRecallInspection.setManagementOfRecalled(postRecallInspection.getManagementOfRecalled());
			postRecallInspection.setRecallReport(postRecallInspection.getRecallReport());
			postRecallInspection.setCorrectiveAction(postRecallInspection.getCorrectiveAction());
			postRecallInspection.setRecommendation(postRecallInspection.getRecommendation());
			postRecallInspection.setCreatedUser(getUsername());
			postRecallInspection.setCreatedDate(new Date());
			postRecallInspection.setBranchId(getBranchId());
			postRecallInspection.setStatus(1);
			postRecallInspection.setStatusCode(0);
			postRecallInspection.setLatitude(getLatitude());
			postRecallInspection.setLongitude(getLongitude());
			utilService.save(postRecallInspection);
		}
		return REDIRECT;
	}

	public String update() throws Exception {
		if (id != null && !id.equals("") && postRecallInspection == null) {
			postRecallInspection = (PostRecallInspection) farmerService
					.findObjectById("FROM PostRecallInspection where id=?", new Object[] { Long.valueOf(id) });
			DateFormat df = new SimpleDateFormat(getGeneralDateFormat());
			setInspectionDate(df.format(postRecallInspection.getInspectionDate()));
			setSelectedLotNo(String.valueOf(postRecallInspection.getRecall().getId()));
			recalling = (Recalling) farmerService.findObjectById("FROM Recalling where id=?",
					new Object[] { postRecallInspection.getRecall().getId() });
			command = UPDATE;
			request.setAttribute(HEADING, getText("postRecallInspectionupdate"));
		} else {
			if (postRecallInspection != null) {
				PostRecallInspection temp = (PostRecallInspection) farmerService
						.findObjectById("FROM PostRecallInspection where id=?", new Object[] { Long.valueOf(id) });
				if (temp != null && !StringUtil.isEmpty(temp)) {
					Recalling rec = (Recalling) farmerService.findObjectById(
							"FROM Recalling rec WHERE rec.id=? AND rec.status=?",
							new Object[] { temp.getRecall().getId(), 1 });

					rec.setRecallingStatus(null);
					utilService.update(rec);
				}

				if (temp == null) {
					addActionError(NO_RECORD);
					return REDIRECT;
				}
				if (!StringUtil.isEmpty(inspectionDate)) {
					temp.setInspectionDate(DateUtil.convertStringToDate(inspectionDate, getGeneralDateFormat()));
				} else {
					temp.setInspectionDate(null);
				}
				if (selectedLotNo != null && StringUtil.isLong(selectedLotNo)) {
					Recalling rec = (Recalling) farmerService.findObjectById(
							"FROM Recalling rec WHERE rec.id=? AND rec.status=?",
							new Object[] { Long.valueOf(selectedLotNo), 1 });

					temp.setRecall(rec);
					rec.setRecallingStatus("1");
					utilService.update(rec);
				}

				temp.setNameOfAgency(postRecallInspection.getNameOfAgency());
				temp.setNameOfInspector(postRecallInspection.getNameOfInspector());
				temp.setMobileNumber(postRecallInspection.getMobileNumber());
				temp.setOperatorBeingInspected(postRecallInspection.getOperatorBeingInspected());
				temp.setNatureOfRecall(postRecallInspection.getNatureOfRecall());
				temp.setManagementOfRecalled(postRecallInspection.getManagementOfRecalled());
				temp.setRecallReport(postRecallInspection.getRecallReport());
				temp.setCorrectiveAction(postRecallInspection.getCorrectiveAction());
				temp.setRecommendation(postRecallInspection.getRecommendation());
				temp.setBranchId(getBranchId());
				temp.setStatus(1);
				temp.setStatusCode(0);
				setCurrentPage(getCurrentPage());
				temp.setCreatedUser(getUsername());
				temp.setCreatedDate(new Date());
				temp.setIsActive(1l);
				temp.setRevisionNo(DateUtil.getRevisionNumber());
				utilService.update(temp);
			}
			request.setAttribute(HEADING, getText("postRecallInspectionlist"));
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
			postRecallInspection = (PostRecallInspection) farmerService
					.findObjectById("FROM PostRecallInspection where id=?", new Object[] { Long.valueOf(id) });

			if (postRecallInspection == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			setSelectedLotNo(String.valueOf(postRecallInspection.getRecall().getKenyaTraceCode()));
			DateFormat df = new SimpleDateFormat(getGeneralDateFormat());
			setInspectionDate(df.format(postRecallInspection.getInspectionDate()));
			setCurrentPage(getCurrentPage());
			postRecallInspection.setNatureOfRecall(getNatureOfRecall().get(postRecallInspection.getNatureOfRecall()));
			recalling = (Recalling) farmerService.findObjectById("FROM Recalling where id=?",
					new Object[] { postRecallInspection.getRecall().getId() });
			roleID = getLoggedInRoleID();
			 ex = utilService.getAuditRecords("com.sourcetrace.eses.entity.PostRecallInspection", postRecallInspection.getId());
			
			command = UPDATE;
			view = DETAIL;
			request.setAttribute(HEADING, getText("postRecallInspectionDetail"));
		} else {
			request.setAttribute(HEADING, getText(LIST));
			return REDIRECT;
		}
		return view;
	}

	public String delete() throws Exception {
		String result = null;
		if (id != null && !id.equals("")) {
			PostRecallInspection postRecallInspection = (PostRecallInspection) farmerService
					.findObjectById("FROM PostRecallInspection where id=?", new Object[] { Long.valueOf(id) });
			if (postRecallInspection == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(postRecallInspection)) {

				Recalling rec = (Recalling) farmerService.findObjectById(
						"FROM Recalling rec WHERE rec.id=? AND rec.status=?",
						new Object[] { postRecallInspection.getRecall().getId(), 1 });
				rec.setRecallingStatus(null);
				utilService.update(rec);

				postRecallInspection.setStatus(0);
				postRecallInspection.setRecall(null);
				utilService.update(postRecallInspection);
				result = REDIRECT;
			}
		}

		return result;
	}

	public void populateProductVariety() {
		JSONObject jsonObj = new JSONObject();
		if (selectedLotNo != null && !StringUtil.isEmpty(selectedLotNo)) {
			Recalling recalling = (Recalling) farmerService.findObjectById(RECALLING_QUERY,
					new Object[] { Long.valueOf(selectedLotNo) });

			JSONArray procurementGradesJSON = new JSONArray();
			for (RecallDetails detail : recalling.getRecallDetails()) {
				JSONObject procurementGradeJSON = new JSONObject();
				procurementGradeJSON.put("prodCat", detail.getPlanting().getVariety().getName());
				procurementGradeJSON.put("product", detail.getPlanting().getGrade().getName());
				procurementGradeJSON.put("lotQty", detail.getReceivedWeight());
				procurementGradeJSON.put("blockid", detail.getBatchNo());
				procurementGradeJSON.put("blockname", detail.getFarmcrops().getBlockName());
				procurementGradeJSON.put("plantingId", detail.getPlanting()!=null ? detail.getPlanting().getPlantingId() : "");
				procurementGradeJSON.put("lotid", detail.getLotNo());
				procurementGradeJSON.put("createdDate", getGeneralDateFormat(String.valueOf(detail.getRecall().getRecDate())));
				// procurementGradeJSON.put("createdDate",detail.getCreatedDate());
				procurementGradeJSON.put("unit", getCatalgueNameByCode(detail.getReceivedUnits()));
				procurementGradeJSON.put("farmername", detail.getFarmcrops().getFarm().getFarmer().getFirstName()
						+ " - " + detail.getFarmcrops().getFarm().getFarmer().getFarmerId());
				procurementGradesJSON.add(procurementGradeJSON);

			}
			jsonObj.put("nature", recalling.getRecNature());
			jsonObj.put("recNatureLab", getNatureOfRecall().get(recalling.getRecNature()));
			jsonObj.put("procurementGrades", procurementGradesJSON);
			sendAjaxResponse(jsonObj);
		}
	}

	public Map<String, String> getLotNoList() {

		String qryString = "select rc.id,rc.kenyaTraceCode,rc.batchNo FROM Recalling rc WHERE kenyaTraceCode is not NULL AND rc.status=1 and rc.recallingStatus is null";
		if (getLoggedInDealer() > 0) {
			qryString = "select rc.id,rc.kenyaTraceCode,rc.batchNo FROM Recalling rc WHERE kenyaTraceCode is not NULL AND rc.status=1 and rc.recallingStatus is null and rc.shipment.packhouse.exporter.id="
					+ getLoggedInDealer();
		}

		Map<String, String> lotNoList = new HashMap<String, String>();

		List<Object[]> treacibilitycodeLists = (List<Object[]>) farmerService.listObjectById(qryString,
				new Object[] {});
		for (Object[] obj : treacibilitycodeLists) {
			lotNoList.put(String.valueOf(obj[0]), String.valueOf(obj[1]) + "-" + String.valueOf(obj[2]));
		}
		return lotNoList;
	}

	public void populateValidate() throws ParseException {
		Map<String, String> errorCodes = new LinkedHashMap<String, String>();
		if (postRecallInspection != null) {
			if (StringUtil.isEmpty(selectedLotNo)) {
				errorCodes.put("empty.consignmentNo", "empty.consignmentNo");
			}
			if (StringUtil.isEmpty(inspectionDate)) {
				errorCodes.put("empty.inspDate", "empty.inspDate");

			}

			if (getInspectionDate() == null || StringUtil.isEmpty(getInspectionDate())
					|| getInspectionDate().equals("")) {
				errorCodes.put("empty.inspDate", "empty.inspDate");
			}
			
			if (selectedLotNo != null && !StringUtil.isEmpty(selectedLotNo)) {
				Recalling recalling = (Recalling) farmerService.findObjectById(RECALLING_QUERY, new Object[] { Long.valueOf(selectedLotNo) });
				Date date1 = DateUtil.convertStringToDate(getInspectionDate(), getGeneralDateFormat());
				for (RecallDetails detail : recalling.getRecallDetails()) {
					int result = date1.compareTo(detail.getRecall().getRecDate());
					System.out.println(date1);
					System.out.println(detail.getRecall().getRecDate());
					if (!(result >= 0) && !date1.equals(detail.getRecall().getRecDate())) {
						errorCodes.put("invalid.createdNpackingdatePOrecalling", "invalid.createdNpackingdatePOrecalling");
					}
				}
			}

			if (postRecallInspection.getNameOfAgency() == null
					|| StringUtil.isEmpty(postRecallInspection.getNameOfAgency())
					|| postRecallInspection.getNameOfAgency().equals("")) {
				errorCodes.put("empty.nameofAgency", "empty.nameofAgency");
			}
			if (postRecallInspection.getNameOfInspector() == null
					|| StringUtil.isEmpty(postRecallInspection.getNameOfInspector())
					|| postRecallInspection.getNameOfInspector().equals("")) {
				errorCodes.put("empty.nameOfInspector", "empty.nameOfInspector");
			}
			if (postRecallInspection.getMobileNumber() == null
					|| StringUtil.isEmpty(postRecallInspection.getMobileNumber())
					|| postRecallInspection.getMobileNumber().equals("")) {
				errorCodes.put("empty.mobNum", "empty.mobNum");
			}
			if (postRecallInspection.getOperatorBeingInspected() == null
					|| StringUtil.isEmpty(postRecallInspection.getOperatorBeingInspected())
					|| postRecallInspection.getOperatorBeingInspected().equals("")) {
				errorCodes.put("empty.operatorBeingInspected", "empty.operatorBeingInspected");
			}
			if (postRecallInspection.getNatureOfRecall() == null
					|| StringUtil.isEmpty(postRecallInspection.getNatureOfRecall())
					|| postRecallInspection.getNatureOfRecall().equals("")) {
				errorCodes.put("empty.natureOfRecall", "empty.natureOfRecall");
			}
			if (postRecallInspection.getManagementOfRecalled() == null
					|| StringUtil.isEmpty(postRecallInspection.getManagementOfRecalled())
					|| postRecallInspection.getManagementOfRecalled().equals("")) {
				errorCodes.put("empty.managementOfRecalled", "empty.managementOfRecalled");
			}
			if (postRecallInspection.getRecallReport() == null
					|| StringUtil.isEmpty(postRecallInspection.getRecallReport())
					|| postRecallInspection.getRecallReport().equals("")) {
				errorCodes.put("empty.recallReport", "empty.recallReport");
			}
			if (postRecallInspection.getCorrectiveAction() == null
					|| StringUtil.isEmpty(postRecallInspection.getCorrectiveAction())
					|| postRecallInspection.getCorrectiveAction().equals("")) {
				errorCodes.put("empty.correctiveAction", "empty.correctiveAction");
			}
			if (postRecallInspection.getRecommendation() == null
					|| StringUtil.isEmpty(postRecallInspection.getRecommendation())
					|| postRecallInspection.getRecommendation().equals("")) {
				errorCodes.put("empty.recommendation", "empty.recommendation");
			}

		} else {
			errorCodes.put("empty.fields", getLocaleProperty("empty.fields"));
		}
		printErrorCodes(errorCodes);
	}

	// public Map<String,String> postRecommendationList;

	public Map<String, String> getPostRecommendationList() {
		Map<String, String> postRecommendationList = new LinkedHashMap<>();
		postRecommendationList = getFarmCatalougeMap(Integer.valueOf(getText("postRecommendationListType")));
		return postRecommendationList;
	}

	// public void setLotNoList(Map<String, String> lotNoList) {
	// this.lotNoList = lotNoList;
	// }
}
