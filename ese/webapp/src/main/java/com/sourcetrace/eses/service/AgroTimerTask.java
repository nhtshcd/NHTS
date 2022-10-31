package com.sourcetrace.eses.service;

import java.util.TimerTask;

import com.sourcetrace.eses.entity.ESETxn;
import com.sourcetrace.eses.util.StringUtil;

public class AgroTimerTask extends TimerTask {

	
	 private int times = 0;
	    private boolean cancelled = false;
	    private String agentId = null;
	    private IUtilService utilService;
	    private IFarmerService farmerService;
	    
	    public AgroTimerTask(String agentId, 
	    		IFarmerService farmerService,IUtilService utilService) {

	        this.agentId = agentId;
	        this.utilService = utilService;
	        this.farmerService = farmerService;
	        String timerValue = utilService.findAgentTimerValue();
	        TIMER_COUNT = (!StringUtil.isEmpty(timerValue) ? Integer.parseInt(timerValue) : 0);
	        
	    }


	  
	    public int getTimes() {
			return times;
		}

		public void setTimes(int times) {
			this.times = times;
		}

		public boolean isCancelled() {
			return cancelled;
		}

		public void setCancelled(boolean cancelled) {
			this.cancelled = cancelled;
		}

		public String getAgentId() {
			return agentId;
		}

		public void setAgentId(String agentId) {
			this.agentId = agentId;
		}

		public int getTIMER_COUNT() {
			return TIMER_COUNT;
		}

		public void setTIMER_COUNT(int tIMER_COUNT) {
			TIMER_COUNT = tIMER_COUNT;
		}

		public int TIMER_COUNT = 0;

		 public void run() {

		        times++;
		        if (times > TIMER_COUNT) {
		            cancelTimer(true);
		        }

		    }

		    /**
		     * Cancel timer.
		     * @param isEod the is eod
		     */
		    public void cancelTimer(boolean isEod) {

		        if (isEod)
		            utilService.updateAgentBODStatus(agentId, ESETxn.EOD);
		        this.cancel();
		        cancelled = true;
		    }



}
