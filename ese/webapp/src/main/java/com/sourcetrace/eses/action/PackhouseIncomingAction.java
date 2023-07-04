package com.sourcetrace.eses.action;

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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.CityWarehouse;
import com.sourcetrace.eses.entity.CityWarehouseDetail;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Packhouse;
import com.sourcetrace.eses.entity.PackhouseIncoming;
import com.sourcetrace.eses.entity.PackhouseIncomingDetails;
import com.sourcetrace.eses.entity.PackingDetail;
import com.sourcetrace.eses.entity.ParentEntity;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.entity.Sorting;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.ValidationUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class PackhouseIncomingAction extends SwitchAction {

	private static final long serialVersionUID = 1L;

	private String QUERY = "FROM PackhouseIncoming pi LEFT JOIN FETCH pi.packhouseIncomingDetails pid WHERE pi.id=? AND (pi.status=? or pi.status=?)";
	private String PACKHOUSE_QUERY = "FROM Packhouse w WHERE w.id=? AND w.status=?";
	// private String BLOCK_QUERY = "FROM CityWarehouse cw WHERE
	// cw.farmcrops.id=? and cw.stockType=? ";
	private String BLOCK_QUERY = "FROM CityWarehouse cw WHERE cw.planting.id=? and cw.stockType=?  and cw.isDelete=0 ";
	private String BLOCK_LIST_QUERY = "FROM CityWarehouse cw where cw.stockType=? and cw.sortedWeight>0 and cw.planting.farmCrops.exporter.id=?  and cw.isDelete=0 GROUP BY cw.planting.farmCrops.id";
	// private String FARMCROPS_QUERY = "FROM Planting fc WHERE fc.id=? AND
	// fc.status=?";
	private String FARMCROPS_QUERY = "FROM Planting fc WHERE fc.id=? AND fc.status=?";
	private String SORTING_QUERY = "FROM Sorting w WHERE w.qrCodeId=? Order By createdDate Desc";

	@Getter
	@Setter
	protected String command;

	@Getter
	@Setter
	protected String currentPage;

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String redirectContent;

	@Getter
	@Setter
	private String packhouseIncomingDtl;

	@Getter
	@Setter
	private String offLoadingDate;

	@Getter
	@Setter
	private String blockNo;// selectedBlockNo this value is not coming from form
							// whether its loading

	@Getter
	@Setter
	private String variety;

	@Getter
	@Setter
	private PackhouseIncoming packhouseIncoming;

	@Getter
	@Setter
	private String selectedBlock;

	@Getter
	@Setter
	private String selectedPackhouse;

	@Getter
	@Setter
	private String qrCode;

	@Getter
	@Setter
	private String selectedPlanting;

	/**
	 * for detail page
	 * 
	 * @return
	 * @throws Exception
	 */
	@Getter
	@Setter
	List<Object[]> ex;

	public String detail() throws Exception {
		if (id != null && !StringUtil.isEmpty(id) && StringUtil.isLong(id)) {
			packhouseIncoming = (PackhouseIncoming) farmerService.findObjectById(QUERY,
					new Object[] { Long.valueOf(getId()), 1, 4 });
			if (packhouseIncoming == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}

			packhouseIncoming.getPackhouseIncomingDetails().stream().forEach(uu -> {
				CityWarehouse cw = (CityWarehouse) farmerService.findObjectById(
						"FROM CityWarehouse where qrCodeId=? and planting.id=? and stockType=? and isDelete=0 ",
						new Object[] { uu.getQrCodeId(), uu.getPlanting().getId(),
								CityWarehouse.Stock_type.HARVEST_STOCK.ordinal() });
				uu.setCw(cw);
				Sorting sr = (Sorting) farmerService.findObjectById(
						"FROM Sorting w WHERE w.qrCodeId=? Order By createdDate Desc",
						new Object[] { uu.getQrCodeId() });
				uu.setSr(sr);
			});

			offLoadingDate = DateUtil.convertDateToString(packhouseIncoming.getOffLoadingDate(),
					getGeneralDateFormat());

			ex = utilService.getAuditRecords("com.sourcetrace.eses.entity.PackhouseIncoming",
					packhouseIncoming.getId());

			setCurrentPage(getCurrentPage());
			setCommand(DETAIL);
			return DETAIL;
		} else {
			request.setAttribute(HEADING, getText("incomingShipmentDetail"));
			return REDIRECT;
		}
	}

	/**
	 * For create page and insert record
	 * 
	 * @return
	 * @throws Exception
	 */
	public String create() throws Exception {
		if (packhouseIncoming == null) {
			command = CREATE;
			request.setAttribute(HEADING, "incomingShipmentcreate");
			return INPUT;
		} else {

			// String blockid= getPackhouseIncomingDtl());
			Packhouse packhouse = (Packhouse) farmerService.findObjectById(PACKHOUSE_QUERY,
					new Object[] { packhouseIncoming.getPackhouse().getId(), 1 });

			packhouseIncoming.setPackhouse(packhouse);
			packhouseIncoming
					.setOffLoadingDate(DateUtil.convertStringToDate(getOffLoadingDate(), getGeneralDateFormat()));
			// packhouseIncoming.setBlockNo(packhouseIncoming.getBlockNo());

			packhouseIncoming.setCreatedDate(DateUtil.convertStringToDate(getOffLoadingDate(), getGeneralDateFormat()));
			// packhouseIncoming.setCreatedDate(new Date());
			packhouseIncoming.setCreatedUser(getUsername());
			packhouseIncoming.setBranchId(getBranchId());
			packhouseIncoming.setStatus(1);

			String delNote = "Truck Type:" + packhouseIncoming.getTruckType();
			delNote += ",Truck No:" + packhouseIncoming.getTruckNo();
			delNote += ",Driver Name:" + packhouseIncoming.getDriverName();
			delNote += ",Driver Contact:" + packhouseIncoming.getDriverCont();
			delNote += ",Total Weight:" + packhouseIncoming.getTotalWeight();

			packhouseIncoming.setDeliveryNote(delNote);

			if (packhouseIncomingDtl != null && !StringUtil.isEmpty(packhouseIncomingDtl)) {
				Set<PackhouseIncomingDetails> phIncomingDetails = getincomingShipmentDetails(packhouseIncoming);
				packhouseIncoming.setPackhouseIncomingDetails(phIncomingDetails);
				packhouseIncoming.setTotalWeight(packhouseIncoming.getPackhouseIncomingDetails().stream()
						.mapToDouble(u -> u.getReceivedWeight()).sum());

			}

			packhouseIncoming.setBatchNo(DateUtil.getDateTimWithMinsec());
			packhouseIncoming.setLatitude(getLatitude());
			packhouseIncoming.setLongitude(getLongitude());

			utilService.saveIncoming(packhouseIncoming);

		}
		return REDIRECT;
	}

	/**
	 * To get details of packhouse incoming shipment
	 * 
	 * @param incoming
	 * @return
	 */
	public Set<PackhouseIncomingDetails> getincomingShipmentDetails(PackhouseIncoming incoming) {
		Set<PackhouseIncomingDetails> incomingDetails = new LinkedHashSet<>();
		if (!StringUtil.isEmpty(getPackhouseIncomingDtl())) {
			List<String> ichList = Arrays.asList(getPackhouseIncomingDtl().split("@"));

			ichList.stream().filter(obj -> !StringUtil.isEmpty(obj)).forEach(lp -> {
				PackhouseIncomingDetails ichDtl = new PackhouseIncomingDetails();
				List<String> list = Arrays.asList(lp.split("#"));

				Planting farmCrops = (Planting) farmerService.findObjectById(FARMCROPS_QUERY,
						new Object[] { Long.valueOf(list.get(8).toString()), 1 });
				CityWarehouse ctt = (CityWarehouse) farmerService.findObjectById(
						"FROM CityWarehouse where qrCodeId=? and planting.id=? and stockType=? AND isDelete=0",
						new Object[] { list.get(9).toString(), farmCrops.getId(),
								CityWarehouse.Stock_type.HARVEST_STOCK.ordinal() });
				ichDtl.setCw(ctt);
				ichDtl.setFarmcrops(farmCrops.getFarmCrops());
				ichDtl.setPlanting(farmCrops);
				ichDtl.setReceivedWeight(Double.valueOf(list.get(5).toString()));
				ichDtl.setReceivedUnits(list.get(1).toString());
				ichDtl.setTransferWeight(Double.valueOf(list.get(4).toString()));
				ichDtl.setTotalWeight(Double.valueOf(list.get(6).toString()));
				ichDtl.setNoUnits(Integer.valueOf(list.get(7).toString()));

				if (list.get(9).toString() != null && !StringUtil.isEmpty(list.get(9).toString())) {
					ichDtl.setQrCodeId(String.valueOf(list.get(9).toString()));
				} else {
					ichDtl.setQrCodeId(String.valueOf(DateUtil.getRevisionNumber()));
				}
				ichDtl.setStatus(1);
				if (list.get(8) != null) {
					Planting planing = utilService.findPlantingById(Long.valueOf(list.get(8)));
					if (planing != null) {
						ichDtl.setPlanting(planing);
					}
				}
				ichDtl.setPackhouseIncoming(incoming);
				incomingDetails.add(ichDtl);
			});
		}
		return incomingDetails;
	}

	/**
	 * For edit page and update record
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		if (id != null && !StringUtil.isEmpty(id) && packhouseIncoming == null) {
			packhouseIncoming = (PackhouseIncoming) farmerService.findObjectById(QUERY,
					new Object[] { Long.valueOf(getId()), 1, 4 });
			if (packhouseIncoming == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			offLoadingDate = DateUtil.convertDateToString(packhouseIncoming.getOffLoadingDate(),
					getGeneralDateFormat());

			packhouseIncoming.getPackhouseIncomingDetails().stream().forEach(uu -> {
				CityWarehouse cw = (CityWarehouse) farmerService.findObjectById(
						"FROM CityWarehouse where qrCodeId=? and planting.id=? and stockType=? AND isDelete=0",
						new Object[] { uu.getQrCodeId(), uu.getPlanting().getId(),
								CityWarehouse.Stock_type.HARVEST_STOCK.ordinal() });
				uu.setCw(cw);
				Sorting sr = (Sorting) farmerService.findObjectById(
						"FROM Sorting w WHERE w.qrCodeId=? Order By createdDate Desc",
						new Object[] { uu.getQrCodeId() });
				uu.setSr(sr);
			});
			isEdit = "1";

			setCurrentPage(getCurrentPage());
			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getText("incomingshipmentupdate"));
		} else {
			if (packhouseIncoming != null) {
				PackhouseIncoming incoming = (PackhouseIncoming) farmerService.findObjectById(QUERY,
						new Object[] { packhouseIncoming.getId(), 1, 4 });
				Map<CityWarehouse, Double> existock = new HashMap<CityWarehouse, Double>();
				incoming.getPackhouseIncomingDetails().stream()
						.filter(uu -> uu.getPackhouseIncoming().getStatus() != null
								&& !uu.getPackhouseIncoming().getStatus().equals(4))
						.forEach(uu -> {
							CityWarehouse ctt = (CityWarehouse) farmerService.findObjectById(
									"FROM CityWarehouse where qrCodeId=? and  planting.id=? and stockType=? AND isDelete=0",
									new Object[] { uu.getQrCodeId(), uu.getPlanting().getId(),
											CityWarehouse.Stock_type.HARVEST_STOCK.ordinal() });
							ctt.setSortedWeight(ctt.getSortedWeight() + uu.getTransferWeight());

							utilService.update(ctt);
							CityWarehouse incominCW = (CityWarehouse) farmerService.findObjectById(
									"from CityWarehouse ct where ct.coOperative.id=? and ct.batchNo=? and ct.stockType=? and ct.planting.id=? AND ct.isDelete=0",
									new Object[] { uu.getPackhouseIncoming().getPackhouse().getId(),
											uu.getPackhouseIncoming().getBatchNo(),
											CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(),
											uu.getPlanting().getId() });

							existock.put(ctt, 0d);
							if (incominCW != null && !ObjectUtil.isEmpty(incominCW)) {
								incominCW.setIsDelete(2);
								utilService.update(incominCW);
							}
						});
				Long phid = packhouseIncoming.getPackhouse().getId();
				Packhouse packhouse = (Packhouse) farmerService.findObjectById(PACKHOUSE_QUERY,
						new Object[] { phid, 1 });

				incoming.setPackhouse(packhouse);

				incoming.setOffLoadingDate(DateUtil.convertStringToDate(getOffLoadingDate(), getGeneralDateFormat()));
				incoming.setCreatedDate(DateUtil.convertStringToDate(getOffLoadingDate(), getGeneralDateFormat()));
				// incoming.setUpdatedDate(new Date());
				incoming.setUpdatedDate(DateUtil.convertStringToDate(getOffLoadingDate(), getGeneralDateFormat()));
				incoming.setUpdatedUser(getUsername());
				incoming.setTotalWeight(Double.valueOf(packhouseIncoming.getTotalWeight()));
				incoming.setTruckType(packhouseIncoming.getTruckType());
				incoming.setTruckNo(packhouseIncoming.getTruckNo());
				incoming.setDriverName(packhouseIncoming.getDriverName());
				incoming.setDriverCont(packhouseIncoming.getDriverCont());
				incoming.setStatus(1);
				Set<PackhouseIncomingDetails> pidtl = getincomingShipmentDetails(incoming);

				String delNote = "Truck Type:" + packhouseIncoming.getTruckType();
				delNote += ",Truck No:" + packhouseIncoming.getTruckNo();
				delNote += ",Driver Name:" + packhouseIncoming.getDriverName();
				delNote += ",Driver Contact:" + packhouseIncoming.getDriverCont();
				delNote += ",Total Weight:" + packhouseIncoming.getTotalWeight();

				incoming.setDeliveryNote(delNote);
				pidtl.stream().forEach(uu -> {
					if (existock.keySet().stream().anyMatch(cta -> cta.getId() == uu.getCw().getId())) {
						/*
						 * CityWarehouse kk =
						 * existock.keySet().stream().filter(cta -> cta.getId()
						 * == uu.getCw().getId()) .findFirst().get();
						 */
						CityWarehouse incominCW = (CityWarehouse) farmerService.findObjectById(
								"from CityWarehouse ct where ct.coOperative.id=? and ct.batchNo=? and ct.stockType=? and ct.planting.id=? AND ct.isDelete=2",
								new Object[] { uu.getPackhouseIncoming().getPackhouse().getId(),
										uu.getPackhouseIncoming().getBatchNo(),
										CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(), uu.getPlanting().getId() });
						if (incominCW != null && !ObjectUtil.isEmpty(incominCW)) {
							incominCW.setIsDelete(0);
							utilService.update(incominCW);
						}
					} else {
						/*
						 * //uu.getCw().setSortedWeight(uu.getCw().
						 * getSortedWeight()-uu.getTotalWeight());
						 * existock.put(uu.getCw(), uu.getReceivedWeight());
						 */
					}

				});
				incoming.setTotalWeight(pidtl.stream().mapToDouble(u -> u.getReceivedWeight()).sum());
				incoming.getPackhouseIncomingDetails().clear();
				// utilService.saveOrUpdate(incoming);

				// incoming.setPackhouseIncomingDetails(pidtl);

				/*
				 * if (incoming.getPackhouseIncomingDetails() != null &&
				 * incoming.getPackhouseIncomingDetails().size() > 0) {
				 * incoming.getPackhouseIncomingDetails().stream().filter(sd ->
				 * sd != null && !ObjectUtil.isEmpty(sd)) .forEach(sd -> {
				 * sd.setPackhouseIncoming(incoming); utilService.save(sd); });
				 * }
				 */
				incoming.getPackhouseIncomingDetails().addAll(pidtl);

				utilService.saveOrUpdate(incoming);
				utilService.updateIncoming(incoming);

			}

			setCurrentPage(getCurrentPage());
			request.setAttribute(HEADING, getText("incomingshipmentlist"));
			return REDIRECT;
		}
		return super.execute();
	}

	/**
	 * For deletion for record
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		String result = null;
		if (id != null && !StringUtil.isEmpty(id)) {
			PackhouseIncoming incoming = (PackhouseIncoming) farmerService.findObjectById(QUERY,
					new Object[] { Long.valueOf(getId()), 1, 4 });
			if (incoming == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(incoming)) {
				incoming.setStatus(ParentEntity.Active.DELETED.ordinal());
				Map<CityWarehouse, Double> existock = new HashMap<CityWarehouse, Double>();
				incoming.getPackhouseIncomingDetails().stream()
						.filter(uu -> uu.getPackhouseIncoming().getStatus() != null
								&& !uu.getPackhouseIncoming().getStatus().equals(4))
						.forEach(uu -> {
							CityWarehouse ctt = (CityWarehouse) farmerService.findObjectById(
									"FROM CityWarehouse where  planting.id=? and stockType=? and qrCodeId=? AND isDelete=0",
									new Object[] { uu.getPlanting().getId(),
											CityWarehouse.Stock_type.HARVEST_STOCK.ordinal(), uu.getQrCodeId() });
							ctt.setSortedWeight(ctt.getSortedWeight() + uu.getTransferWeight());
							// existock.put(ctt, uu.getReceivedWeight() * -1);

							utilService.update(ctt);
						});
				utilService.saveOrUpdate(incoming);
				utilService.deleteIncoming(incoming);
				result = REDIRECT;
			}
		}
		return result;
	}

	public void populateBlock() {
		JSONArray jsnArr = new JSONArray();
		if (selectedPackhouse != null && !StringUtil.isEmpty(selectedPackhouse)
				&& StringUtil.isLong(selectedPackhouse)) {
			Packhouse pk = (Packhouse) farmerService.findObjectById(PACKHOUSE_QUERY,
					new Object[] { Long.valueOf(selectedPackhouse), 1 });
			if (pk != null) {
				List<CityWarehouse> cwList = (List<CityWarehouse>) farmerService.listObjectById(BLOCK_LIST_QUERY,
						new Object[] { CityWarehouse.Stock_type.HARVEST_STOCK.ordinal(), pk.getExporter().getId() });
				List<CityWarehouse> distinctInts = cwList.stream().distinct().collect(Collectors.toList());
				distinctInts.stream().forEach(cw -> {
					jsnArr.add(getJSONObject(cw.getPlanting().getFarmCrops().getId(),
							cw.getPlanting().getFarmCrops().getBlockName()));
				});
			}

		}

		sendAjaxResponse(jsnArr);
	}

	public void populateSorting() {
		JSONArray jsnArr = new JSONArray();
		if (selectedPlanting != null && !StringUtil.isEmpty(selectedPlanting) && StringUtil.isLong(selectedPlanting)) {
			LinkedList<Object> parame = new LinkedList();
			String qry = "SELECT DISTINCT cw.qrCodeId,cw.qrCodeId FROM CityWarehouse cw join cw.planting f WHERE f.id=? and cw.stockType=? and f.status=1 and cw.sortedWeight>0  and cw.isDelete=0 ORDER BY f.id ASC";
			/*
			 * CityWarehouse cw = (CityWarehouse) farmerService.
			 * findObjectById("SELECT DISTINCT s.qrCodeId,s.qrCodeId FROM CityWarehouse cw join cw.planting f WHERE f.id=? and cw.stockType=? and f.status=1 and cw.sortedWeight>0"
			 * , new Object[] { selectedBlock,
			 * CityWarehouse.Stock_type.HARVEST_STOCK.ordinal() });
			 */
			parame.add(Long.valueOf(selectedPlanting));
			parame.add(CityWarehouse.Stock_type.HARVEST_STOCK.ordinal());
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry = "SELECT DISTINCT cw.qrCodeId,cw.qrCodeId FROM CityWarehouse cw join cw.planting f WHERE f.id=? and cw.stockType=? and f.status=1 and cw.sortedWeight>0  and cw.isDelete=0 and f.farmCrops.exporter.id=?  ORDER BY f.id ASC";
				parame.add(Long.valueOf(getLoggedInDealer()));
			}

			List<Object[]> growerList = (List<Object[]>) farmerService.listObjectById(qry, parame.toArray());
			// List<Planting> dataList =
			// utilService.listPlantingByFarmCropsId(Long.valueOf(selectedBlock));
			growerList.stream().distinct().forEach(f -> {
				jsnArr.add(getJSONObject(f[0].toString(), f[1].toString()));
			});

		}

		sendAjaxResponse(jsnArr);
	}

	public void populateBlockIdByFarmCropsId() {
		JSONObject jss = new JSONObject();
		if (selectedBlock != null && !StringUtil.isEmpty(selectedBlock) && StringUtil.isLong(selectedBlock)) {
			FarmCrops fc = utilService.findFarmCropsById(Long.valueOf(selectedBlock));
			if (fc != null) {
				jss.put("blockId", fc.getBlockId());
			}
		}
		sendAjaxResponse(jss);
	}

	@Override
	public void populateBlockDetails() {
		JSONObject jss = new JSONObject();
		if (selectedBlock != null && !StringUtil.isEmpty(selectedBlock) && StringUtil.isLong(selectedBlock)) {
			CityWarehouse cw = (CityWarehouse) farmerService.findObjectById(
					"FROM CityWarehouse cw WHERE cw.qrCodeId=? and cw.stockType=?  and cw.isDelete=0",
					new Object[] { selectedBlock, CityWarehouse.Stock_type.HARVEST_STOCK.ordinal() });
			if (cw != null) {
				jss.put("blockId", cw.getPlanting().getFarmCrops().getBlockId());
				jss.put("product", cw.getPlanting().getVariety().getName());
				jss.put("variety", cw.getPlanting().getGrade().getName());
				jss.put("transferedWeight", cw.getSortedWeight());
				jss.put("createdDate", getGeneralDateFormat(String.valueOf(cw.getCreatedDate())));
			}

		}
		sendAjaxResponse(jss);
	}

	public void populateqrCodecheck() {
		JSONObject jss = new JSONObject();
		if (qrCode != null && !StringUtil.isEmpty(qrCode)) {
			PackhouseIncoming sr = (PackhouseIncoming) farmerService.findObjectById(
					"FROM PackhouseIncoming pi LEFT JOIN FETCH pi.packhouseIncomingDetails pid WHERE pid.status=? and pid.qrCodeId=?",
					new Object[] { String.valueOf("1"), String.valueOf(qrCode) });
			if (sr != null || !StringUtil.isEmpty(sr)) {
				jss.put("QRCheck", "1");
			} else {
				jss.put("QRCheck", "0");
			}
		}
		sendAjaxResponse(jss);
	}

	/*
	 * public void populatePlanting() { JSONArray plantingarr = new JSONArray();
	 * if (selectedBlock != null && !ObjectUtil.isEmpty(selectedBlock) &&
	 * !selectedBlock.equals("")) {
	 * 
	 * LinkedList<Object> parame = new LinkedList(); String qry =
	 * "SELECT DISTINCT f.id,f.plantingId from Sorting s join s.planting f where f.farmCrops.id=? and s.status=1 and f.status=1 ORDER BY f.id ASC"
	 * ; parame.add(Long.valueOf(selectedBlock)); if (getLoggedInDealer() !=
	 * null && getLoggedInDealer() > 0) { qry =
	 * "SELECT DISTINCT f.id,f.plantingId FROM Sorting s join s.planting f  where f.farmCrops.id=?  and s.status=1 and f.status=1 and f.farmCrops.exporter.id=?  ORDER BY f.id ASC"
	 * ; parame.add(Long.valueOf(getLoggedInDealer())); }
	 * 
	 * List<Object[]> growerList = (List<Object[]> )
	 * farmerService.listObjectById(qry, parame.toArray()); //List<Planting>
	 * dataList =
	 * utilService.listPlantingByFarmCropsId(Long.valueOf(selectedBlock));
	 * growerList.stream().distinct().forEach(f -> {
	 * plantingarr.add(getJSONObject(f[0].toString(), f[1].toString())); }); }
	 * sendAjaxResponse(plantingarr); }
	 */

	public void populatePlanting() {
		JSONArray plantingarr = new JSONArray();
		if (selectedBlock != null && !ObjectUtil.isEmpty(selectedBlock) && !selectedBlock.equals("")) {

			LinkedList<Object> parame = new LinkedList();
			String qry = "SELECT DISTINCT f.id,f.plantingId from Sorting s join s.planting f where f.farmCrops.id=? and s.status=1 and f.status=1 ORDER BY f.id ASC";
			parame.add(Long.valueOf(selectedBlock));
			if (getLoggedInDealer() != null && getLoggedInDealer() > 0) {
				qry = "SELECT DISTINCT f.id,f.plantingId FROM Sorting s join s.planting f  where f.farmCrops.id=?  and s.status=1 and f.status=1 and f.farmCrops.exporter.id=?  ORDER BY f.id ASC";
				parame.add(Long.valueOf(getLoggedInDealer()));
			}

			List<Object[]> growerList = (List<Object[]>) farmerService.listObjectById(qry, parame.toArray());
			growerList.stream().distinct().forEach(f -> {
				if (f[0] != null) {
					LinkedList<Object> parame1 = new LinkedList();
					String qry1 = "SELECT DISTINCT cw.qrCodeId,cw.qrCodeId FROM CityWarehouse cw join cw.planting f WHERE f.id=? and cw.stockType=? and f.status=1 and cw.sortedWeight>0 ORDER BY f.id ASC";
					parame1.add(Long.valueOf(f[0].toString()));
					parame1.add(CityWarehouse.Stock_type.HARVEST_STOCK.ordinal());
					List<Object[]> growerList1 = (List<Object[]>) farmerService.listObjectById(qry1, parame1.toArray());
					if (!StringUtil.isListEmpty(growerList1)) {
						plantingarr.add(getJSONObject(f[0].toString(), f[1].toString()));
					}
				}
			});
		}
		sendAjaxResponse(plantingarr);
	}

	/*
	 * @Override public void populateBlockDetails() { JSONObject jss = new
	 * JSONObject(); if (selectedBlock != null &&
	 * !StringUtil.isEmpty(selectedBlock) && StringUtil.isLong(selectedBlock)) {
	 * CityWarehouse cw = (CityWarehouse)
	 * farmerService.findObjectById(BLOCK_QUERY, new Object[] {
	 * Long.valueOf(selectedBlock),
	 * CityWarehouse.Stock_type.HARVEST_STOCK.ordinal() }); Sorting sr =
	 * (Sorting) farmerService.findObjectById(SORTING_QUERY, new Object[] {
	 * Long.valueOf(cw.getFarmcrops().getId()) }); jss.put("blockId",
	 * cw.getFarmcrops().getBlockId()); jss.put("product",
	 * cw.getPlanting().getVariety().getName()); jss.put("variety",
	 * cw.getPlanting().getGrade().getName()); jss.put("transferedWeight",
	 * cw.getSortedWeight()); jss.put("createdDate",
	 * getGeneralDateFormat(String.valueOf(sr.getCreatedDate()))); }
	 * sendAjaxResponse(jss); }
	 */
	/**
	 * for input data validation
	 */
	public void populateValidate() {
		Map<String, String> errorCodes = new LinkedHashMap<String, String>();
		if (packhouseIncoming != null) {

			if (offLoadingDate == null || StringUtil.isEmpty(offLoadingDate)) {
				errorCodes.put("empty.packhouseIncoming.offLoadingDate",
						getLocaleProperty("empty.packhouseIncoming.offLoadingDate"));
			}
			if (packhouseIncoming.getPackhouse().getId() == null
					|| StringUtil.isEmpty(packhouseIncoming.getPackhouse().getId())) {
				errorCodes.put("empty.packhouseIncoming.packhouse",
						getLocaleProperty("empty.packhouseIncoming.packhouse"));
			}

			if (String.valueOf(packhouseIncoming.getTruckType()) != null
					|| !StringUtil.isEmpty(packhouseIncoming.getTruckType())) {
				if (!ValidationUtil.isPatternMaches(packhouseIncoming.getTruckType(),
						ValidationUtil.ALPHANUMERIC_PATTERN)) {
					errorCodes.put("pattern.packhouseIncoming.truckType",
							getLocaleProperty("pattern.packhouseIncoming.truckType"));
				}
			} else {
				errorCodes.put("empty.packhouseIncoming.truckType",
						getLocaleProperty("empty.packhouseIncoming.truckType"));
			}
			if (StringUtil.isEmpty(packhouseIncoming.getTruckNo())) {
				errorCodes.put("empty.packhouseIncoming.truckNo", getLocaleProperty("empty.packhouseIncoming.truckNo"));
			} else {
				if (String.valueOf(packhouseIncoming.getTruckNo()) != null
						|| !StringUtil.isEmpty(packhouseIncoming.getTruckNo())) {
					if (!ValidationUtil.isPatternMaches(packhouseIncoming.getTruckType(),
							ValidationUtil.ALPHANUMERIC_PATTERN)) {
						errorCodes.put("pattern.packhouseIncoming.truckNo",
								getLocaleProperty("pattern.packhouseIncoming.truckNo"));
					}
				}
			}

			if (String.valueOf(packhouseIncoming.getDriverName()) != null
					|| !StringUtil.isEmpty(packhouseIncoming.getDriverName())) {
				if (!ValidationUtil.isPatternMaches(packhouseIncoming.getDriverName(),
						ValidationUtil.ALPHANUMERIC_PATTERN)) {
					errorCodes.put("pattern.packhouseIncoming.driverName",
							getLocaleProperty("pattern.packhouseIncoming.driverName"));
				}
			} else {
				errorCodes.put("empty.packhouseIncoming.driverName",
						getLocaleProperty("empty.packhouseIncoming.driverName"));
			}
			if (String.valueOf(packhouseIncoming.getDriverCont()) != null
					|| !StringUtil.isEmpty(packhouseIncoming.getDriverCont())) {
				if (!ValidationUtil.isPatternMaches(packhouseIncoming.getDriverCont(), ValidationUtil.NUMBER_PATTERN)) {
					errorCodes.put("pattern.packhouseIncoming.driverContact",
							getLocaleProperty("pattern.packhouseIncoming.driverContact"));
				}
			} else {
				errorCodes.put("empty.packhouseIncoming.driverContact",
						getLocaleProperty("empty.packhouseIncoming.driverContact"));
			}

			if (packhouseIncomingDtl == null || StringUtil.isEmpty(packhouseIncomingDtl)) {
				errorCodes.put("empty.packhouseIncoming.packhouseIncomingDtl",
						getLocaleProperty("empty.packhouseIncoming.packhouseIncomingDtl"));
			} else {
				List<String> ichList = Arrays.asList(getPackhouseIncomingDtl().split("@"));
				int[] index = { 0 };
				ichList.stream().filter(obj -> !StringUtil.isEmpty(obj)).forEach(lp -> {
					PackhouseIncomingDetails ichDtl = new PackhouseIncomingDetails();
					List<String> list = Arrays.asList(lp.split("#"));
					ichList.get(index[0]++);
					int currentIndex = index[0];
					if (list.get(9).toString() != null && !StringUtil.isEmpty(list.get(9).toString())) {
						PackhouseIncomingDetails sr = (PackhouseIncomingDetails) farmerService.findObjectById(
								"FROM PackhouseIncomingDetails pi WHERE pi.packhouseIncoming.status!=2 and pi.qrCodeId=?",
								new Object[] { String.valueOf(list.get(9).toString()) });

						if (sr != null && !sr.getId().equals(Long.valueOf(list.get(10).toString()))) {
							errorCodes.put("empty.packhouseIncoming.qrCode" + currentIndex,
									"Row" + currentIndex + ": " + getLocaleProperty("empty.packhouseIncoming.qrCode"));
						}

					}
				});
			}

		} else {
			errorCodes.put("empty.fields", getLocaleProperty("empty.fields"));
		}

		printErrorCodes(errorCodes);
	}
}
