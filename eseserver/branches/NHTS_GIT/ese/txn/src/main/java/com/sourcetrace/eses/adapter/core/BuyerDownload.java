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

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.service.IFarmerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Customer;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

/**
 * The Class customerDownload.
 */

@Component

public class BuyerDownload implements ITxnAdapter {

    private static final Logger LOGGER = Logger.getLogger(BuyerDownload.class.getName());

    @Autowired
    private IFarmerService farmerService;
    @Autowired
    private IUtilService utilService;


    @Override
    public Map<?, ?> processJson(Map<?, ?> reqData) {

        LOGGER.info("---------- BUYER Download Start ----------");
        /** HEADER VALUES **/
        Head head = (Head) reqData.get(TransactionProperties.HEAD);

        String revisionNo = (String) reqData
                .get(TransactionProperties.BUYER_REV_NO);

        if (StringUtil.isEmpty(revisionNo) || !StringUtil.isLong(revisionNo)) {
            revisionNo = "0";
        }
        Agent ag = (Agent) reqData.get("agentObj");
        if (ag == null) {
            ag = utilService.findAgent(head.getAgentId());
        }
        /** REQUEST VALUES **/
        Map resp = new HashMap();
        List<Customer> customerList = (List<Customer>) farmerService.listObjectById("from  Customer c where c.revisionNo > ? and c.exporter.id =?", new Object[]{Long.valueOf(revisionNo), (ag.getExporter() != null ? ag.getExporter().getId() : 0l)});// utilService.listcustomers();
        List collection = new ArrayList();
        if (!ObjectUtil.isEmpty(customerList)) {
            for (Customer customer : customerList) {
                Map customerCode = new HashMap();
                customerCode.put(TransactionProperties.BUYER_ID, customer.getCustomerId());
                customerCode.put(TransactionProperties.CUSTOMER_NAME, customer.getCustomerName());
              
                collection.add(customerCode);

            }

        }

        if (!ObjectUtil.isListEmpty(customerList)) {
            revisionNo = String.valueOf(customerList.get(0).getRevisionNo());
        }

        /** RESPONSE DATA **/
        resp.put(TransactionProperties.BUYER, collection);
        resp.put(TransactionProperties.BUYER_DOWNLOAD_REVISION_NO, revisionNo);
        LOGGER.info("---------- BUYER Download END ----------");
        return resp;
    }


}
