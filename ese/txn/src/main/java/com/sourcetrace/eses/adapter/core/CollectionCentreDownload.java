package com.sourcetrace.eses.adapter.core;

	import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.interceptor.ITxnErrorCodes;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.property.TxnEnrollmentProperties;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.exception.SwitchException;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

	@Component
	public class CollectionCentreDownload  implements ITxnAdapter {

	    private static final Logger LOGGER = Logger.getLogger(CollectionCentreDownload.class.getName());
		@Autowired
		private IFarmerService farmerService;

		@Autowired
		private IUtilService utilService;
	    @SuppressWarnings("unchecked")
	    @Override
	    public Map<?, ?> processJson(Map<?, ?> reqData) {
	    	LOGGER.info("----------Collection Centre Download ----------");
	  
	    	Head head = (Head) reqData.get(TransactionProperties.HEAD);
			String agentId = head.getAgentId();
			if (StringUtil.isEmpty(agentId))
				throw new SwitchException(ITxnErrorCodes.AGENT_ID_EMPTY);

			String revisionNo = (String) reqData.get(TxnEnrollmentProperties.COLLECTION_CENTRE_DOWNLOAD);
			if (StringUtil.isEmpty(revisionNo))
				revisionNo = "0";

			LOGGER.info("REVISION NO" + revisionNo);
			List<java.lang.Object[]> centreList = new ArrayList<java.lang.Object[]>();

			Agent ag = utilService.findAgentByAgentId(agentId);
			List centreCollection = new ArrayList<>();
			if (ag != null && ag.getExporter() != null) {
				centreList = (List<Object[]>) farmerService.listObjectById(
						"select  f.name,f.code from city_warehouse c JOIN farm_crops f on f.id = c.FARM_CROP_ID  JOIN farmer ff on ff.id =f.FARMER_ID where ff.EXPORTER_ID= ? and c.REVISION_NO > 0 order by c.REVISION_NO desc",
						new Object[] {ag.getExporter().getId(),Long.valueOf(revisionNo) });
				if (!ObjectUtil.isListEmpty(centreList)) {

			centreList.stream().forEach(harvest -> {
				Map objectMap = new HashMap<>();
				objectMap.put(TxnEnrollmentProperties.COLLECTION_NAME,
						harvest[1] != null && !ObjectUtil.isEmpty(harvest[1]) ? harvest[1].toString() : "");
				objectMap.put(TxnEnrollmentProperties.COLLECTION_CODE,
						harvest[2] != null && !ObjectUtil.isEmpty(harvest[2]) ? harvest[2].toString() : "");
				centreCollection.add(objectMap);

			   });

		        }
			  
			}
				 
			Map resp = new LinkedHashMap();
			resp.put(TxnEnrollmentProperties.COLLECTION_LIST, centreCollection);
	     
			return resp;
	  }
  
	}
 