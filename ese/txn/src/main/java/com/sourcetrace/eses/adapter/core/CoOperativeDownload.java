package com.sourcetrace.eses.adapter.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.Packhouse;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.property.TxnEnrollmentProperties;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

@Component
public class CoOperativeDownload implements ITxnAdapter {

	private static final Logger LOGGER = Logger.getLogger(CoOperativeDownload.class.getName());
	@Autowired
	private IFarmerService farmerService;
	@Autowired
	private IUtilService utilService;

	private Map<String, List<String>> villageMap = new LinkedHashMap<String, List<String>>();

	@SuppressWarnings("unchecked")
	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData) {

		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		villageMap.clear();
		LOGGER.info("========== Co operativeDownload Begin ==========");
		String revisionNo = (String) reqData.get(TxnEnrollmentProperties.COOPERATIVE_DOWNLOAD_REVISION_NO);
		if (StringUtil.isEmpty(revisionNo) || !StringUtil.isLong(revisionNo)) {
			revisionNo = "0";
		}

		if (!StringUtil.isLong(revisionNo)) {
			revisionNo = "0";
		}

		List coOperativeList = new ArrayList();

		Agent ag = (Agent) reqData.get("agentObj");
		if (ag == null) {
			ag = utilService.findAgent(head.getAgentId());
		}
		List<Packhouse> cooperativeWarehouses;
		/*
		 * List<Packhouse> cooperativeWarehouses = (List<Packhouse>)
		 * farmerService
		 * .listObjectById("from Packhouse p where p.revisionNo>? and p.status=1 "
		 * , new Object[] { Long.valueOf(revisionNo) });
		 */
		if (ag.getPackhouse() != null && ag.getPackhouse().getExporter() != null
				&& !ObjectUtil.isEmpty(ag.getPackhouse().getExporter())) {
			cooperativeWarehouses = (List<Packhouse>) farmerService.listObjectById(
					"from Packhouse p where p.revisionNo>? and p.status=1 and p.exporter.id=?",
					new Object[] { Long.valueOf(revisionNo), ag.getPackhouse().getExporter().getId() });
		} else {
			cooperativeWarehouses = (List<Packhouse>) farmerService.listObjectById(
					"from Packhouse p where p.revisionNo>? and p.status=1 and p.exporter.id=?",
					new Object[] { Long.valueOf(revisionNo), ag.getExporter().getId() });
		}
		if (!ObjectUtil.isListEmpty(cooperativeWarehouses)) {
			String captyp = "";
			for (Packhouse warehouse : cooperativeWarehouses) {

				Map<String, String> AcoOperativeDataList = new HashMap<String, String>();
				AcoOperativeDataList.put(TxnEnrollmentProperties.COOPERATIVE_CODE, warehouse.getCode());
				AcoOperativeDataList.put(TxnEnrollmentProperties.COOPERATIVE_NAME, warehouse.getName());
				if (warehouse.getTypez() != null) {
					captyp = String.valueOf(warehouse.getTypez());
				}
				AcoOperativeDataList.put(TxnEnrollmentProperties.COOPERTAIVE_TYPE, String.valueOf(captyp));

				coOperativeList.add(AcoOperativeDataList);

			}

		}

		// Build response map object
		Map resp = new LinkedHashMap();
		resp.put(TxnEnrollmentProperties.COOPERATIVE_LIST, coOperativeList);
		resp.put(TxnEnrollmentProperties.COOPERATIVE_DOWNLOAD_REVISION_NO, revisionNo);
		LOGGER.info("========== CoOperativeDownload End ==========");

		return resp;

	}

}
