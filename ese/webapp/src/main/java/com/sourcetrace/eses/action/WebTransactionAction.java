/*
 * WebTransactionAction.java
 * Copyright (c) 2008-2010, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.action;

import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.service.AgroTimerTask;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

@SuppressWarnings("serial")
public class WebTransactionAction extends SwitchValidatorAction {

    public static final String NOT_APPLICABLE = "N/A";
    public static final String IS_FORM_RESUBMIT = "isFormResubmit";
    protected String agentId;
    protected String agentTimer;
    protected Timer timer = new Timer("");
    protected boolean reInitialize = false;
    protected boolean reStartTxn = false;
    @Autowired
    protected IFarmerService farmerService;
    @Autowired
    private IUtilService utilService;
    /**
     * Redirect.
     * @return the string
     */
    public String redirect() {

        AgroTimerTask myTask = (AgroTimerTask) request.getSession()
                .getAttribute(agentId + "_timer");
        agentTimer = (String) request.getAttribute("agentTimer");
        myTask.setTimes(!StringUtil.isEmpty(agentTimer) ? Integer.parseInt(agentTimer) : 0);
        myTask.cancelTimer(true);
        return "redirect";
    }

    /**
     * Gets the timer count.
     * @return the timer count
     */
    public int getTimerCount() {

        String timerValue = utilService.findAgentTimerValue();
        return (!StringUtil.isEmpty(timerValue) ? Integer.parseInt(timerValue) : 0);
    }

    /**
     * Populate timer count.
     */
    public void populateTimerCount() {

        try {
            AgroTimerTask myTask = (AgroTimerTask) request.getSession().getAttribute(
                    agentId + "_timer");
            agentTimer = (String) request.getAttribute("agentTimer");
            if (ObjectUtil.isEmpty(myTask) || myTask.isCancelled()) {
                myTask = new AgroTimerTask(agentId, farmerService, utilService);
                request.getSession().setAttribute(agentId + "_timer", myTask);
                timer.schedule(myTask, 0, 1000);

            } else if (!ObjectUtil.isEmpty(myTask) && !myTask.isCancelled() && reInitialize) {
                myTask.setTimes(!StringUtil.isEmpty(agentTimer) ? Integer.parseInt(agentTimer) : 0);
            }
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html");
            String result = "failure";
            if (!ObjectUtil.isEmpty(myTask))
                if (!myTask.isCancelled())
                    result = String.valueOf(myTask.getTimes());
                else
                    result = "expired";

            response.getWriter().write(result);
        } catch (Exception e) {

        }
    }

    /**
     * @return
     * @see com.sourcetrace.esesw.view.SwitchValidatorAction#getData()
     */
    @Override
    public Object getData() {

        return null;
    }

    /**
     * Sets the prefernces service.
     * @param preferncesService the new prefernces service
     */
    /**
     * Sets the agent service.
     * @param agentService the new agent service
     */
     /**
     * Gets the agent id.
     * @return the agent id
     */
    public String getAgentId() {

        return agentId;
    }

    /**
     * Sets the agent id.
     * @param agentId the new agent id
     */
    public void setAgentId(String agentId) {

        this.agentId = agentId;
    }

    /**
     * Gets the agent timer.
     * @return the agent timer
     */
    public String getAgentTimer() {

        return agentTimer;
    }

    /**
     * Sets the agent timer.
     * @param agentTimer the new agent timer
     */
    public void setAgentTimer(String agentTimer) {

        this.agentTimer = agentTimer;
    }

    /**
     * Gets the timer.
     * @return the timer
     */
    public Timer getTimer() {

        return timer;
    }

    /**
     * Sets the timer.
     * @param timer the new timer
     */
    public void setTimer(Timer timer) {

        this.timer = timer;
    }

    /**
     * Checks if is re initialize.
     * @return true, if is re initialize
     */
    public boolean isReInitialize() {

        return reInitialize;
    }

    /**
     * Sets the re initialize.
     * @param reInitialize the new re initialize
     */
    public void setReInitialize(boolean reInitialize) {

        this.reInitialize = reInitialize;
    }

    /**
     * Checks if is re start txn.
     * @return true, if is re start txn
     */
    public boolean isReStartTxn() {

        return reStartTxn;
    }

    /**
     * Sets the re start txn.
     * @param reStartTxn the new re start txn
     */
    public void setReStartTxn(boolean reStartTxn) {

        this.reStartTxn = reStartTxn;
    }
}
