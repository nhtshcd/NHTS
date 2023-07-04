package com.sourcetrace.eses.action;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.AgentType;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.ExporterRegistration;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.Village;
import com.sourcetrace.eses.entity.Packhouse;
import com.sourcetrace.eses.entity.Packhouse.WarehouseTypes;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class PackhouseAction extends SwitchAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	private Packhouse ware;
	@Getter
	@Setter
	private String id;
	@Getter
	@Setter
	private String redirectContent;

	@Autowired
	private IUniqueIDGenerator idGenerator;

	public String create() throws Exception {

		String defBranchId = utilService.findPrefernceByName(ESESystem.DEF_BRANCH);
		if (ware == null) {
			command = "create";
			request.setAttribute(HEADING, getText("warehousecreate"));
			ware = new Packhouse();
			if (getLoggedInDealer() > 0) {
				ExporterRegistration ex = utilService.findExportRegById(Long.valueOf(getLoggedInDealer()));
				if (ex != null) {

					ware.setExporter(ex);
				}
			}
			return INPUT;
		} else {
			ware.setCreatedDate(new Date());
			ware.setCreatedUser(getUsername());
			ware.setBranchId(getBranchId() != null ? getBranchId() : defBranchId);

			ware.setName(ware.getName());
			ware.setLocation(ware.getLocation());
			ware.setAddress(ware.getAddress());
			ware.setLatitude(ware.getLatitude());
			ware.setLongitude(ware.getLongitude());
			ware.setPhoneNo(ware.getPhoneNo());
			ware.setWarehouseIncharge(ware.getWarehouseIncharge());
			ware.setStatus(0);
			ware.setTypez(Packhouse.COOPERATIVE);
			//String code = idGenerator.getWarehouseIdSeq();
			ware.setCode(ware.getCode());
			ware.setTypez(WarehouseTypes.COOPERATIVE.ordinal());
			if (ware.getExporter() != null && ware.getExporter().getId() > 0) {
				ExporterRegistration ex = utilService.findExportRegById(Long.valueOf(ware.getExporter().getId()));
				if (ex != null) {

					ware.setExporter(ex);
				}
			}
			ware.setRevisionNo(DateUtil.getRevisionNumber());
			ware.setStatus(1);
			utilService.save(ware);
			return REDIRECT;
		}

	}

	public String detail() throws Exception {

		String view = "";
		if (id != null && !id.equals("")) {
			ware = utilService.findWarehouseById(Long.valueOf(id));
			ESESystem preferences = utilService.findPrefernceById("1");

			if (ware == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			if (!ObjectUtil.isEmpty(preferences)) {
			}

			setCurrentPage(getCurrentPage());

			command = UPDATE;
			view = DETAIL;
			request.setAttribute(HEADING, getText("Warehousedetail"));
		} else {
			request.setAttribute(HEADING, getText(LIST));
			return LIST;
		}
		return view;
	}

	public String delete() throws Exception {
		String result = null;
		if (this.getId() != null && !(this.getId().equals(EMPTY))) {
			ware = utilService.findWarehouseById(Long.valueOf(getId()));
			if (ware == null) {
				addActionError(NO_RECORD);
				return null;
			} else if (!ObjectUtil.isEmpty(ware)) {
				ware.setStatus(Integer.valueOf(2));

				utilService.update(ware);
				result = REDIRECT;
			}
		}

		request.setAttribute(HEADING, getText("agroChemicalDealerlist"));
		return result;

	}

	public String update() throws Exception {

		if (id != null && !id.equals("") && ware == null) {
			ware = utilService.findWarehouseById(Long.valueOf(id));
			if (ware == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}

			setCurrentPage(getCurrentPage());

			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getText("warehouseUpdate"));
		} else {
			if (ware != null) {
				Packhouse tempAgroCh = utilService.findWarehouseById(Long.valueOf(ware.getId()));
				tempAgroCh.setName(ware.getName());
				tempAgroCh.setLocation(ware.getLocation());
				tempAgroCh.setAddress(ware.getAddress());
				tempAgroCh.setPhoneNo(ware.getPhoneNo());
				tempAgroCh.setWarehouseIncharge(ware.getWarehouseIncharge());
				tempAgroCh.setUpdatedUser(getUsername());
				tempAgroCh.setUpdatedDate(new Date());
				tempAgroCh.setLatitude(ware.getLatitude());
				tempAgroCh.setLongitude(ware.getLongitude());
				tempAgroCh.setRevisionNo(DateUtil.getRevisionNumber());
				//tempAgroCh.setStatus(StringUtil.isEmpty(ware.getStatus()) ? 0 : ware.getStatus());

				utilService.update(tempAgroCh);
			}
			request.setAttribute(HEADING, getText("agroChemicalDealerlist"));
			return REDIRECT;
		}

		return super.execute();
	}

	public void populateValidate() {
		Map<String, String> errorCodes = new LinkedHashMap<String, String>();
		if (ware == null) {
			errorCodes.put("empty.fields", "empty.fields");
		} else {
			if(ware.getCode() == null || StringUtil.isEmpty(ware.getCode())){
				errorCodes.put("empty.code", "empty.code");
			}
			if(ware.getName()==null || StringUtil.isEmpty(ware.getName())){
				errorCodes.put("empty.name", "empty.name");
			}else {
				Packhouse cooperative = (Packhouse) farmerService.findObjectById("FROM Packhouse sn where sn.name=? and sn.exporter.id = ? and sn.status=1",new Object[] { ware.getName(),ware.getExporter().getId() });
				//Packhouse cooperative = utilService.findWarehouseByName(ware.getName());
				if (cooperative!=null && (ware.getId()==null || cooperative!=null  && !ware.getId().equals(cooperative.getId()))) {
					errorCodes.put("unique.cooperativeName", "unique.cooperativeName");
				}
			}

			
		
				
				/*if (ware.getCode() != null && !StringUtil.isEmpty(ware.getCode())) {
					//Packhouse packhouseCode=utilService.findWarehouseByCode(ware.getCode());
					Packhouse packhouseCode = (Packhouse) farmerService.findObjectById("FROM Packhouse sn where sn.code=? and sn.exporter.id = ? and sn.status=1",new Object[] { ware.getCode(),ware.getExporter().getId() });
					if (packhouseCode != null && !packhouseCode.getId().equals(ware.getId())) {
						errorCodes.put("unique.cooperativeCode", "unique.cooperativeCode");
					}
				}*/
				if (ware.getCode() != null && !StringUtil.isEmpty(ware.getCode())) {
					Packhouse packhouseCode=utilService.findWarehouseByCode(ware.getCode());
					if (packhouseCode != null && !packhouseCode.getId().equals(ware.getId())) {
						errorCodes.put("unique.cooperativeCode", "unique.cooperativeCode");
					}
				}
			
			
			if(ware.getLocation() == null || StringUtil.isEmpty(ware.getLocation())){
				errorCodes.put("empty.location", "empty.location");
			}
			if(ware.getAddress() == null || StringUtil.isEmpty(ware.getAddress())){
				errorCodes.put("empty.address", "empty.address");
			}
			if (ware.getExporter() == null || ware.getExporter().getId()==null || ware.getExporter().getId() <= 0 ) {
				errorCodes.put("empty.exporter", "empty.exporter");
			}
			if(ware.getLatitude() == null || StringUtil.isEmpty(ware.getLatitude())){
				errorCodes.put("empty.latitude", "empty.latitude");
			}
			if(ware.getLongitude() == null || StringUtil.isEmpty(ware.getLongitude())){
				errorCodes.put("empty.longitude", "empty.longitude");
			}
			
		}
		printErrorCodes(errorCodes);
	}

	public Map<String, String> getAgentTypes() {
		Map<String, String> agentTypes = new LinkedHashMap<String, String>();
		List<AgentType> agent = utilService.listAgentType();

		if (agent != null) {
			for (AgentType agentType : agent) {
				agentTypes.put(String.valueOf(agentType.getId()), String.valueOf(agentType.getName()));

			}
		}
		return agentTypes;
	}
}
