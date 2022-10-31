/*
 * AgentLogout.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.adapter.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.Transaction;
import com.sourcetrace.eses.interceptor.ITxnErrorCodes;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.exception.ESEException;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

// TODO: Auto-generated Javadoc
@Component
public class AgentLogout implements ITxnAdapter {

    private static final Logger LOGGER = Logger.getLogger(AgentLogout.class.getName());

    @Autowired
    private IUtilService utilService;

    

	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData){

        /** GET HEAD DATA **/
        Head head = (Head) reqData.get(TransactionProperties.HEAD);

        /** VALIDATING DATA **/
        String agentId = head.getAgentId();
        LOGGER.info("AGENT ID :" + agentId);
        if (StringUtil.isEmpty(agentId)) {
            throw new ESEException(ITxnErrorCodes.AGENT_ID_EMPTY);
        }
        Agent agent = utilService.findAgentByAgentId(agentId);
        if (ObjectUtil.isEmpty(agent)) {
            throw new ESEException(ITxnErrorCodes.INVALID_AGENT);
        }

        /*
         * if(ESETxn.EOD == agent.getBodStatus()){ throw new
         * SwitchException(SwitchErrorCodes.EOD_EXIST); }
         */
        utilService.updateAgentBODStatus(agent.getProfileId(), Transaction.EOD);

        Map resp = new HashMap();
        return resp;
    }

}
