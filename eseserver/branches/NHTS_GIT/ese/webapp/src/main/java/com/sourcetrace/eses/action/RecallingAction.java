package com.sourcetrace.eses.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.DocumentUpload;
import com.sourcetrace.eses.entity.ExporterRegistration;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.ParentEntity;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.entity.RecallDetails;
import com.sourcetrace.eses.entity.Recalling;
import com.sourcetrace.eses.entity.Shipment;
import com.sourcetrace.eses.entity.ShipmentDetails;
import com.sourcetrace.eses.entity.Village;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.FileUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class RecallingAction extends SwitchAction {

	private static final long serialVersionUID = 1L;
	private String QUERY = "FROM Recalling rec WHERE rec.id=? AND rec.status=? AND rec.statusCode=?";

	@Getter
	@Setter
	private Recalling recalling;

	@Getter
	@Setter
	private String command;

	@Getter
	@Setter
	private String currentPage;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String redirectContent;

	@Getter
	@Setter
	private String recDate;

	@Getter
	@Setter
	private String exporter;

	@Getter
	@Setter
	private String grower;

	@Getter
	@Setter
	private String selectedBatchno;

	@Getter
	@Setter
	private String selectedshipmentdetail;

	@Getter
	@Setter
	private Shipment shipment;

	@Getter
	@Setter
	private String selEntity;

	@Getter
	@Setter
	private String custname;

	@Getter
	@Setter
	private String exportername;

	@Getter
	@Setter
	private File files;

	@Getter
	@Setter
	private String fphoto;

	/**
	 * for detail page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {

		if (id != null && !StringUtil.isEmpty(id) && StringUtil.isLong(id)) {
			recalling = (Recalling) farmerService.findObjectById(QUERY, new Object[] { Long.valueOf(getId()), 1, 0 });
			if (recalling == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			recDate = DateUtil.convertDateToString(recalling.getRecDate(), getGeneralDateFormat());
			recalling.setRecEntity(getRecEntityList().get(recalling.getRecEntity()).toString());
			recalling.setRecNature(getNatureOfRecall().get(recalling.getRecNature()).toString());

			if (recalling.getShipment().getId() != null && !StringUtil.isEmpty(recalling.getShipment().getId())) {
				Shipment shipment = (Shipment) farmerService.findObjectById("FROM Shipment s where s.id=?",
						new Object[] { Long.valueOf(recalling.getShipment().getId()) });
				custname = shipment.getCustomer().getCustomerName();
				exportername = shipment.getPackhouse().getExporter().getName();
			}

			setCurrentPage(getCurrentPage());
			setCommand(DETAIL);
			return DETAIL;
		} else {
			request.setAttribute(HEADING, getText("recallingDetail"));
			return REDIRECT;
		}

	}

	/**
	 * for create page and insert record
	 * 
	 * @return
	 * @throws Exception
	 */
	public String create() throws Exception {
		if (recalling == null) {
			command = CREATE;
			request.setAttribute(HEADING, "recallingcreate");
			recalling = new Recalling();
			id = null;
			return INPUT;
		} else {
			// recalling = new Recalling();

			recalling.setRecDate(DateUtil.convertStringToDate(getRecDate(), getGeneralDateFormat()));

			if (recalling.getShipment().getId() != null && !StringUtil.isEmpty(recalling.getShipment().getId())) {
				Shipment shipment = (Shipment) farmerService.findObjectById("FROM Shipment s where s.id=?",
						new Object[] { Long.valueOf(recalling.getShipment().getId()) });
				recalling.setKenyaTraceCode(shipment.getPConsignmentNo());
				recalling.setShipment(shipment);
			}

			recalling.setCreatedDate(new Date());
			recalling.setCreatedUser(getUsername());
			recalling.setBranchId(getBranchId());
			recalling.setStatus(1);
			recalling.setStatusCode(0);
			recalling.setRecallDetails(new HashSet<>());
			recalling.setBatchNo(DateUtil.getDateTimWithMinsec());
			recalling.setLatitude(getLatitude());
			recalling.setLongitude(getLongitude());
			if (!StringUtil.isEmpty(selectedshipmentdetail)) {
				String batchIdsArray[] = null;
				selectedshipmentdetail = selectedshipmentdetail.replaceAll("\\s", "");
				batchIdsArray = selectedshipmentdetail.split(",");

				for (String tempBatchId : batchIdsArray) {
					if (!StringUtil.isEmpty(tempBatchId)) {
						farmerService.updateshipmentdetail(tempBatchId);
						ShipmentDetails sh = (ShipmentDetails) farmerService.findObjectById(
								"from ShipmentDetails where id=?", new Object[] { Long.valueOf(tempBatchId) });
						if (sh != null) {
							// recalling.getRecallDetails().add(RecallDetails.builder().farmcrops(sh.getCityWarehouse().getFarmcrops()).receivedWeight(Double.valueOf(sh.getPackingQty())).receivedUnits(sh.getPackingUnit()).recall(recalling).batchNo(sh.getCityWarehouse().getFarmcrops().getBlockId()).lotNo(sh.getCityWarehouse().getBatchNo()).shipmentdetail(sh).build());
							recalling.getRecallDetails()
									.add(RecallDetails.builder().farmcrops(sh.getCityWarehouse().getFarmcrops())
											.receivedWeight(Double.valueOf(sh.getPackingQty()))
											.receivedUnits(sh.getPackingUnit()).recall(recalling)
											.batchNo(sh.getCityWarehouse().getFarmcrops().getBlockId())
											.lotNo(sh.getCityWarehouse().getBatchNo()).shipmentdetail(sh).planting(sh.getCityWarehouse().getPlanting()).build());
						}
					}
				}
			}

			if (files != null) {
				DocumentUpload du = new DocumentUpload();
				System.out.println("fphoto==>" + fphoto);
				String[] tokens = fphoto.split("\\.(?=[^\\.]+$)");
				String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
				if (!name.equals("") && name != null) {
					String filetype = tokens[1];
					if (tokens != null && filetype.equals("jpg") || filetype.equals("JPG") || filetype.equals("PNG")
							|| filetype.equals("jpeg") || filetype.equals("JPEG") || filetype.equals("png")
							|| filetype.equals("pdf") || filetype.equals("PDF")) {

						System.out.println("At 210**********" + name);
						du.setName(name);
						du.setContent(FileUtil.getBinaryFileContent(getFiles()));
						du.setDocFileContentType(tokens[1]);
						du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec()) + "1");
						du.setType(DocumentUpload.docType.farmer.ordinal());
						du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
						utilService.save(du);
						recalling.setAttachment(du.getRefCode().toString());
					}
				}
			}

			if (recalling.getShipment().getId() != null && !StringUtil.isEmpty(recalling.getShipment().getId())) {
				ExporterRegistration expRegis = (ExporterRegistration) farmerService.findObjectById(
						"FROM ExporterRegistration s where s.id=?",
						new Object[] { Long.valueOf(recalling.getShipment().getPackhouse().getExporter().getId()) });

				if (expRegis.getTin() != null) {
					String msg = getLocaleProperty("mail_recalllingmsg");
					msg = msg.replace("[sh]", recalling.getKenyaTraceCode());
					sendExpireMail(expRegis.getTin().trim(), expRegis.getName(), msg, "Shipment Recalled");
					/*
					 * StringBuilder sb = new StringBuilder();
					 * sb.append(getLocaleProperty("hai") + " " +
					 * expRegis.getName() + "\n\n\n"); sb.append(msg); String
					 * message = sb.toString();
					 * 
					 * smsService.sendSMS(smsType, expRegis.getMobileNo(), msg);
					 */
				}
			}
			utilService.save(recalling);

		}
		return REDIRECT;
	}

	/**
	 * For update page and update record
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		if (id != null && !StringUtil.isEmpty(id)) {
			recalling = (Recalling) farmerService.findObjectById(QUERY, new Object[] { Long.valueOf(getId()), 1, 0 });
			if (recalling == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}

			recDate = DateUtil.convertDateToString(recalling.getRecDate(), getGeneralDateFormat());
			setCurrentPage(getCurrentPage());
			command = UPDATE;
			request.setAttribute(HEADING, getText("recallingupdate"));
		} else {
			if (recalling != null) {
				Recalling rec = (Recalling) farmerService.findObjectById(QUERY,
						new Object[] { Long.valueOf(recalling.getId()), 1, 0 });

				if (!StringUtil.isEmpty(rec.getRecallDetails())) {
					for (RecallDetails tempBatchId : rec.getRecallDetails()) {
						if (!StringUtil.isEmpty(tempBatchId)) {
							farmerService.removerecallingdetail(tempBatchId.getId(),
									tempBatchId.getShipmentdetail().getId());

						}
					}
				}
				rec.getRecallDetails().clear();
				rec.setRecDate(DateUtil.convertStringToDate(getRecDate(), getGeneralDateFormat()));

				if (recalling.getShipment().getId() != null && !StringUtil.isEmpty(recalling.getShipment().getId())) {
					Shipment shipment = (Shipment) farmerService.findObjectById("FROM Shipment s where s.id=?",
							new Object[] { Long.valueOf(recalling.getShipment().getId()) });
					rec.setKenyaTraceCode(shipment.getPConsignmentNo());
					rec.setShipment(shipment);
				}

				rec.setRecEntity(recalling.getRecEntity());
				rec.setRecCoordinatorName(recalling.getRecCoordinatorName());
				rec.setRecCoordinatorContact(recalling.getRecCoordinatorContact());
				rec.setRecNature(recalling.getRecNature());
				rec.setRecReason(recalling.getRecReason());
				rec.setOperatorName(recalling.getOperatorName());
				rec.setContactNo(recalling.getContactNo());
				rec.setPo(recalling.getPo());
				rec.setInvoiceNo(recalling.getInvoiceNo());
				rec.setCarrierNo(recalling.getCarrierNo());
				rec.setActionByRecaller(recalling.getActionByRecaller());
				rec.setActionByStakeholders(recalling.getActionByStakeholders());
				rec.setUpdatedDate(new Date());
				rec.setUpdatedUser(getUsername());

				if (files != null) {
					DocumentUpload du = new DocumentUpload();
					System.out.println("fphoto==>" + fphoto);
					String[] tokens = fphoto.split("\\.(?=[^\\.]+$)");
					String name = tokens[0].substring(tokens[0].lastIndexOf("\\") + 1).trim();
					if (!name.equals("") && name != null) {
						String filetype = tokens[1];
						if (tokens != null && filetype.equals("jpg") || filetype.equals("JPG") || filetype.equals("PNG")
								|| filetype.equals("jpeg") || filetype.equals("JPEG") || filetype.equals("png")
								|| filetype.equals("pdf") || filetype.equals("PDF")) {

							System.out.println("At 210**********" + name);
							du.setName(name);
							du.setContent(FileUtil.getBinaryFileContent(getFiles()));
							du.setDocFileContentType(tokens[1]);
							du.setRefCode(String.valueOf(DateUtil.getDateTimWithMinsec()) + "1");
							du.setType(DocumentUpload.docType.farmer.ordinal());
							du.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
							utilService.save(du);
							rec.setAttachment(du.getRefCode().toString());
						}
					}
				}

				if (!StringUtil.isEmpty(selectedshipmentdetail)) {
					String batchIdsArray[] = null;
					selectedshipmentdetail = selectedshipmentdetail.replaceAll("\\s", "");
					batchIdsArray = selectedshipmentdetail.split(",");

					for (String tempBatchId : batchIdsArray) {
						if (!StringUtil.isEmpty(tempBatchId)) {
							farmerService.updateshipmentdetail(tempBatchId);
							ShipmentDetails sh = (ShipmentDetails) farmerService.findObjectById(
									"from ShipmentDetails where id=?", new Object[] { Long.valueOf(tempBatchId) });
							if (sh != null) {
								// utilService.save(RecallDetails.builder().farmcrops(sh.getCityWarehouse().getFarmcrops()).receivedWeight(Double.valueOf(sh.getPackingQty())).receivedUnits(sh.getPackingUnit()).recall(rec).batchNo(sh.getCityWarehouse().getBatchNo()).build());
								utilService.save(RecallDetails.builder().farmcrops(sh.getCityWarehouse().getFarmcrops())
										.receivedWeight(Double.valueOf(sh.getPackingQty()))
										.receivedUnits(sh.getPackingUnit()).recall(rec)
										.batchNo(sh.getCityWarehouse().getFarmcrops().getBlockId())
										.lotNo(sh.getCityWarehouse().getBatchNo()).shipmentdetail(sh).planting(sh.getCityWarehouse().getPlanting()).build());

							}
						}
					}
				}

				utilService.update(rec);

			}
			return REDIRECT;
		}
		return super.execute();
	}

	/**
	 * for delete record
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		String result = null;
		if (id != null && !StringUtil.isEmpty(id)) {
			Recalling rec = (Recalling) farmerService.findObjectById(QUERY, new Object[] { Long.valueOf(id), 1, 0 });
			if (rec == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(rec)) {
				if (!StringUtil.isEmpty(rec.getRecallDetails())) {
					for (RecallDetails tempBatchId : rec.getRecallDetails()) {
						if (!StringUtil.isEmpty(tempBatchId)) {
							farmerService.removerecallingdetail(tempBatchId.getId(),
									tempBatchId.getShipmentdetail().getId());

						}
					}
				}
				rec.setStatus(0);
				// rec.setKenyaTraceCode("0");
				utilService.update(rec);
				result = REDIRECT;
			}
		}
		return result;
	}

	/**
	 * for to get recEntity field data
	 * 
	 * @return
	 */
	public Map<String, String> getRecEntityList() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("1", getText("grwer"));
		map.put("2", getText("exprtr"));
		map.put("3", getText("grow"));
		map.put("4", getText("hcd"));
		return map;
	}

	/**
	 * To get list of sub counties
	 * 
	 * @return
	 */
	public Map<String, String> getSubCounties() {
		return utilService.listLocalities().stream()
				.collect(Collectors.toMap(sc -> sc[2].toString(), sc -> sc[0].toString() + "-" + sc[1].toString()));
	}

	/**
	 * For validate input data
	 */
	public void populateValidate() {
		Map<String, String> errorCodes = new LinkedHashMap<>();
		if (recalling != null) {
			if (recalling.getShipment().getId() == null || StringUtil.isEmpty(recalling.getShipment().getId())) {
				errorCodes.put("empty.recalling.kenyaTraceCode", getLocaleProperty("empty.recalling.kenyaTraceCode"));
			}

			if (selectedshipmentdetail == null || StringUtil.isEmpty(selectedshipmentdetail)) {
				errorCodes.put("empty.selectedrecallingcop", getLocaleProperty("empty.selectedrecallingcop"));
			}

			if (recDate == null || StringUtil.isEmpty(recDate)) {
				errorCodes.put("empty.recalling.recDate", getLocaleProperty("empty.recalling.recDate"));
			}

			if (recalling.getRecEntity() == null || StringUtil.isEmpty(recalling.getRecEntity())) {
				errorCodes.put("empty.recalling.recEntity", getLocaleProperty("empty.recalling.recEntity"));
			}

			if (recalling.getRecCoordinatorName() != null && !StringUtil.isEmpty(recalling.getRecCoordinatorName())) {
				if (!ValidationUtil.isPatternMaches(recalling.getRecCoordinatorName(),
						ValidationUtil.ALPHANUMERIC_PATTERN)) {
					errorCodes.put("pattern.recalling.recCoordinatorName",
							getLocaleProperty("pattern.recalling.recCoordinatorName"));
				}
			} else {
				errorCodes.put("empty.recalling.recCoordinatorName",
						getLocaleProperty("empty.recalling.recCoordinatorName"));
			}

			if (recalling.getRecCoordinatorContact() != null
					&& !StringUtil.isEmpty(recalling.getRecCoordinatorContact())) {
				if (!ValidationUtil.isPatternMaches(recalling.getRecCoordinatorContact(),
						ValidationUtil.NUMBER_PATTERN)) {
					errorCodes.put("pattern.recalling.recCoordinatorContact",
							getLocaleProperty("pattern.recalling.recCoordinatorContact"));
				}
			} else {
				errorCodes.put("empty.recalling.recCoordinatorContact",
						getLocaleProperty("empty.recalling.recCoordinatorContact"));
			}

			if (recalling.getRecNature() == null || StringUtil.isEmpty(recalling.getRecNature())) {
				errorCodes.put("empty.recalling.recNature", getLocaleProperty("empty.recalling.recNature"));
			}

			if (recalling.getRecReason() != null && !StringUtil.isEmpty(recalling.getRecReason())) {
				if (!ValidationUtil.isPatternMaches(recalling.getRecReason(), ValidationUtil.ALPHANUMERIC_PATTERN)) {
					errorCodes.put("pattern.recalling.recReason", getLocaleProperty("pattern.recalling.recReason"));
				}
			} else {
				errorCodes.put("empty.recalling.recReason", getLocaleProperty("empty.recalling.recReason"));
			}

			if (recalling.getOperatorName() == null || StringUtil.isEmpty(recalling.getOperatorName())) {
				errorCodes.put("empty.recalling.operator", getLocaleProperty("empty.recalling.operator"));
			}
			/*
			 * if (selectedshipmentdetail == null ||
			 * StringUtil.isEmpty(selectedshipmentdetail)) {
			 * errorCodes.put("empty.blockId",
			 * getLocaleProperty("empty.blockId")); }
			 */
		} else {
			errorCodes.put("empty.fields", getLocaleProperty("empty.fields"));
		}
		printErrorCodes(errorCodes);
	}

	public Map<String, String> getTreacibilitycodeList() {

		String qryString = "FROM ShipmentDetails s where s.recallingstatus IS NULL and s.shipment.status=1";
		if (getLoggedInDealer() > 0) {
			qryString = "FROM ShipmentDetails s where s.recallingstatus IS NULL and s.shipment.status=1 and s.shipment.packhouse.exporter.id="
					+ getLoggedInDealer();
		}
		// Map<String,String> treacibilitycodeList =
		// farmerService.listObjectById(qryString, new Object[] {});

		Map<String, String> treacibilitycodeList = new HashMap<String, String>();

		List<ShipmentDetails> treacibilitycodeLists = (List<ShipmentDetails>) farmerService.listObjectById(qryString,
				new Object[] {});
		for (ShipmentDetails obj : treacibilitycodeLists) {
			treacibilitycodeList.put(String.valueOf(obj.getShipment().getId()),
					String.valueOf(obj.getShipment().getPConsignmentNo()));
		}

		return treacibilitycodeList;
	}

	public void populateShipmentdetail() throws Exception {

		if (selectedBatchno != null && !StringUtil.isEmpty(selectedBatchno)) {
			if (id != null && !StringUtil.isEmpty(id)) {

				JSONObject jsonObj = new JSONObject();
				Shipment shipment = (Shipment) farmerService.findObjectById("FROM Shipment s where s.id=?",
						new Object[] { Long.valueOf(selectedBatchno) });

				Recalling recall = (Recalling) farmerService.findObjectById("FROM Recalling s where s.id=?",
						new Object[] { Long.valueOf(id) });

				JSONArray procurementGradesJSON = new JSONArray();
				if (shipment != null && !StringUtil.isEmpty(shipment)) {
					for (ShipmentDetails detail : shipment.getShipmentDetails()) {
						JSONObject procurementGradeJSON = new JSONObject();
						Set<RecallDetails> shipmentdet = recall.getRecallDetails();
						if (detail.getRecallingstatus() == null) {
							procurementGradeJSON.put("prodCat",
									detail.getCityWarehouse().getPlanting().getVariety().getName());
							procurementGradeJSON.put("product",
									detail.getCityWarehouse().getPlanting().getGrade().getName());
							procurementGradeJSON.put("product1", detail.getCityWarehouse().getSortedWeight());
							procurementGradeJSON.put("createdDate",
									getGeneralDateFormat(String.valueOf(detail.getShipment().getShipmentDate())));
							procurementGradeJSON.put("scientific", getCatalgueNameByCode(detail.getPackingUnit()));
							procurementGradeJSON.put("quantity", detail.getPackingQty());
							procurementGradeJSON.put("unit", detail.getCityWarehouse().getFarmcrops().getBlockId());
							procurementGradeJSON.put("blockname",
									detail.getCityWarehouse().getFarmcrops().getBlockName());
							procurementGradeJSON.put("planting",
									detail.getCityWarehouse().getPlanting().getPlantingId());
							procurementGradeJSON.put("lotid", detail.getCityWarehouse().getBatchNo());
							procurementGradeJSON.put("farmername",
									detail.getCityWarehouse().getFarmcrops().getFarm().getFarmer().getFirstName()
											+ " - " + detail.getCityWarehouse().getFarmcrops().getFarm().getFarmer()
													.getFarmerId());
							procurementGradeJSON.put("id", detail.getId());
							if (detail.getRecallingstatus() != null) {
								procurementGradeJSON.put("status", "1");
							} else {
								procurementGradeJSON.put("status", "0");
							}

							procurementGradesJSON.add(procurementGradeJSON);
						}

					}
					for (RecallDetails recallDetail : recall.getRecallDetails()) {
						JSONObject procurementGradeJSON = new JSONObject();
						procurementGradeJSON.put("prodCat", recallDetail.getPlanting().getVariety().getName());
						procurementGradeJSON.put("product", recallDetail.getPlanting().getGrade().getName());
						procurementGradeJSON.put("product1",
								recallDetail.getShipmentdetail().getCityWarehouse().getSortedWeight());
						procurementGradeJSON.put("createdDate", getGeneralDateFormat(
								String.valueOf(recallDetail.getShipmentdetail().getShipment().getShipmentDate())));
						procurementGradeJSON.put("scientific",
								getCatalgueNameByCode(recallDetail.getShipmentdetail().getPackingUnit()));
						procurementGradeJSON.put("quantity", recallDetail.getShipmentdetail().getPackingQty());
						procurementGradeJSON.put("unit",
								recallDetail.getShipmentdetail().getCityWarehouse().getFarmcrops().getBlockId());
						procurementGradeJSON.put("blockname",
								recallDetail.getShipmentdetail().getCityWarehouse().getFarmcrops().getBlockName());
						procurementGradeJSON.put("planting", recallDetail.getPlanting().getPlantingId());
						
						procurementGradeJSON.put("lotid",
								recallDetail.getShipmentdetail().getCityWarehouse().getBatchNo());
						procurementGradeJSON.put("farmername",
								recallDetail.getShipmentdetail().getCityWarehouse().getFarmcrops().getFarm().getFarmer()
										.getFirstName() + " - "
										+ recallDetail.getShipmentdetail().getCityWarehouse().getFarmcrops().getFarm()
												.getFarmer().getFarmerId());
						procurementGradeJSON.put("id", recallDetail.getShipmentdetail().getId());
						if (recallDetail.getShipmentdetail().getRecallingstatus() != null) {
							procurementGradeJSON.put("status", "1");
						} else {
							procurementGradeJSON.put("status", "0");
						}

						procurementGradesJSON.add(procurementGradeJSON);

					}
				}
				// if (!command.equals("update"))
				jsonObj.put("procurementGrades", procurementGradesJSON);
				jsonObj.put("custname", shipment.getCustomer().getCustomerName());
				jsonObj.put("exporter", shipment.getPackhouse().getExporter().getName());
				sendAjaxResponse(jsonObj);
			}

			else {

				JSONObject jsonObj = new JSONObject();
				Shipment shipment = (Shipment) farmerService.findObjectById("FROM Shipment s where s.id=?",
						new Object[] { Long.valueOf(selectedBatchno) });

				JSONArray procurementGradesJSON = new JSONArray();
				if (shipment != null && !StringUtil.isEmpty(shipment)) {
					for (ShipmentDetails detail : shipment.getShipmentDetails()) {
						JSONObject procurementGradeJSON = new JSONObject();
						if (detail.getRecallingstatus() == null) {
							procurementGradeJSON.put("prodCat",
									detail.getCityWarehouse().getPlanting().getVariety().getName());
							procurementGradeJSON.put("product",
									detail.getCityWarehouse().getPlanting().getGrade().getName());
							procurementGradeJSON.put("product1", detail.getCityWarehouse().getSortedWeight());
							procurementGradeJSON.put("createdDate",
									getGeneralDateFormat(String.valueOf(detail.getShipment().getShipmentDate())));
							procurementGradeJSON.put("scientific", getCatalgueNameByCode(detail.getPackingUnit()));
							procurementGradeJSON.put("quantity", detail.getPackingQty());
							procurementGradeJSON.put("unit", detail.getCityWarehouse().getFarmcrops().getBlockId());
							procurementGradeJSON.put("blockname",
									detail.getCityWarehouse().getFarmcrops().getBlockName());
							procurementGradeJSON.put("planting",
									detail.getCityWarehouse().getPlanting().getPlantingId());
							
							procurementGradeJSON.put("lotid", detail.getCityWarehouse().getBatchNo());
							procurementGradeJSON.put("farmername",
									detail.getCityWarehouse().getFarmcrops().getFarm().getFarmer().getFirstName()
											+ " - " + detail.getCityWarehouse().getFarmcrops().getFarm().getFarmer()
													.getFarmerId());
							procurementGradeJSON.put("id", detail.getId());
							if (detail.getRecallingstatus() != null) {
								procurementGradeJSON.put("status", "1");
							} else {
								procurementGradeJSON.put("status", "0");
							}
							procurementGradeJSON.put("id", detail.getId());

							procurementGradesJSON.add(procurementGradeJSON);
						}

					}
				}
				// if (!command.equals("update"))
				jsonObj.put("procurementGrades", procurementGradesJSON);
				jsonObj.put("custname", shipment.getCustomer().getCustomerName());
				jsonObj.put("exporter", shipment.getPackhouse().getExporter().getName());
				sendAjaxResponse(jsonObj);
				// System.out.println(cwList);
			}
		}
	}

	public Map<String, String> getbuyerdetail() {
		return utilService.listLocalities().stream()
				.collect(Collectors.toMap(sc -> sc[2].toString(), sc -> sc[0].toString() + "-" + sc[1].toString()));
	}

	public Map<String, String> getBuyerList() {
		Map<String, String> buyerList = new LinkedHashMap<>();

		utilService.listBuyer().stream().forEach(u -> {
			buyerList.put(String.valueOf(u[0]), String.valueOf(u[1]));
		});
		return buyerList;
	}

	public Map<String, String> getGrowerList() {
		Map<String, String> growerList = new LinkedHashMap<>();

		utilService.listFarmerName().stream().forEach(u -> {
			growerList.put(String.valueOf(u.getId()), String.valueOf(u.getFirstName()));
		});
		return growerList;
	}

	public Map<String, String> getHcdList() {
		Map<String, String> hcdList = new LinkedHashMap<>();

		utilService.listBuyer().stream().forEach(u -> {
			hcdList.put(String.valueOf(u[0]), String.valueOf(u[1]));
		});
		return hcdList;
	}

	public void loadentitydata() {
		if (selEntity != null) {
			JSONArray varietyArr = new JSONArray();
			List<Object[]> dataList = new ArrayList();
			if (selEntity.equals("1")) {
				if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
					dataList = (List<Object[]>) farmerService.listObjectById(
							"SELECT s.customer.id,s.customer.customerName from Shipment s where s.status=1 and s.packhouse.exporter.id=?",
							new Object[] { getLoggedInDealer() });
				} else {
					dataList = (List<Object[]>) farmerService.listObjectById(
							"SELECT s.customer.id,s.customer.customerName from Shipment s where s.status=1",
							new Object[] { getLoggedInDealer() });
				}

				if (dataList != null && !ObjectUtil.isEmpty(dataList)) {
					dataList.stream().distinct().forEach(f -> {
						varietyArr.add(getJSONObject(f[0].toString(), f[1].toString()));
					});
				}
			} else if (selEntity.equals("2")) {
				utilService.listExporter().stream().forEach(u -> {
					varietyArr.add(getJSONObject(String.valueOf(u[0]), String.valueOf(u[1])));
				});
			} else if (selEntity.equals("3")) {
				if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
					dataList = (List<Object[]>) farmerService.listObjectById(
							"SELECT DISTINCT f.id,f.farmerId,f.firstName,f.lastName from Farmer f where f.status=1 and f.exporter.id=?",
							new Object[] { getLoggedInDealer() });
				} else {
					dataList = farmerService.listFarmerIDAndName();
				}

				if (dataList != null && !ObjectUtil.isEmpty(dataList)) {
					dataList.stream().distinct().forEach(f -> {
						varietyArr.add(getJSONObject(f[0].toString(), (f[2].toString() + " "
								+ (f[3] != null && !ObjectUtil.isEmpty(f[3]) ? f[3].toString() : ""))));
					});
				}

			}
			sendAjaxResponse(varietyArr);
		}
	}

}
