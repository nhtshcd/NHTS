/*
 * customerDownload.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.adapter.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.ObjectUtil;

/**
 * The Class customerDownload.
 */

@Component
public class PcbbDownload implements ITxnAdapter {

	private static final Logger LOGGER = Logger.getLogger(PcbbDownload.class.getName());

	@Autowired
	private IFarmerService farmerService;

	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData) {

		LOGGER.info("---------- PCPB Download Start ----------");
		/** HEADER VALUES **/
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		/** REQUEST VALUES **/
		Map resp = new HashMap();
		List<Object[]> customerList = (List<Object[]>) farmerService.listObjectById(
				"select p.cropvariety.code, p.cropvariety.procurementVariety.code,p.cropvariety.procurementVariety.procurementProduct.code,p.phiIn,p.dosage,p.tradeName,p.id,p.uom from Pcbp p",
				new Object[] {});

		List collection = new ArrayList();
		if (!ObjectUtil.isEmpty(customerList)) {
			for (Object[] customer : customerList) {
				Map customerCode = new HashMap();
				customerCode.put("cropVariety", customer[0]!=null ?  customer[0].toString() : "" );
				customerCode.put("crop", customer[1]!=null ?  customer[1].toString() : "" );
				customerCode.put("cropCat", customer[2]!=null ?  customer[2].toString() : "" );
				customerCode.put("phiIn", customer[3]!=null ?  customer[3].toString() : "" );
				customerCode.put("dosage", customer[4]!=null ?  customer[4].toString() : "" );
				customerCode.put("chemicalName", customer[5]!=null ?  customer[5].toString() : "" );
				customerCode.put("pid", customer[6]!=null ?  customer[6].toString() : "" );
				customerCode.put("uom", customer[7]!=null ?  customer[7].toString() : "" );
				collection.add(customerCode);

			}

		}

		/** RESPONSE DATA **/
		resp.put("pcbpList", collection);
		LOGGER.info("---------- PCPB Download END ----------");
		return resp;
	}

}
