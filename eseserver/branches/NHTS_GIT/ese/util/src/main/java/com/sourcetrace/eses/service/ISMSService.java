/*
 * ISMSService.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */

package com.sourcetrace.eses.service;

import java.util.List;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.SMSHistory;
import com.sourcetrace.eses.entity.SmsGroupDetail;
import com.sourcetrace.eses.entity.SmsGroupHeader;
import com.sourcetrace.eses.entity.SmsTemplate;
import com.sourcetrace.eses.entity.User;

/**
 * @author PANNEER
 */
public interface ISMSService {

	 public static final String FORMAT = "application/json";

	    public final static String SMS_ALTERS_ON = "1";
	    public final static String SMS_ALTERS_OFF = "0";
	    public final static String SMS_PAR_TOKEN = "token";
	    public final static String SMS_PAR_MSISDN = "msisdn";
	    public final static String SMS_PAR_TEXT = "text";
	    public final static String SMS_PAR_SENDER_ID = "sender_id";
	    public final static String SMS_PAR_ROUTE = "route";
	    public final static String SMS_PAR_UNICODE = "unicode";
	    public final static String SMS_PAR_FLASH = "flash";

	    public final static String SMS_FASTALERTS = "fastalerts";
	    public final static String SMS_RES_STATUS = "status";
	    public final static String SMS_RES_DESCR = "description";
	    public final static String SMS_RES_UUID = "uuid";

	    public final static String SMS_CONTENT_TYPE = "application/json;charset=utf-8";

	    public final static String SMS_TOTAL = "total";
	    public final static String SMS_MESSAGES = "messages";

	    public static final String SMS_ERROR = "Error";
	    public static final String SINGLE_SMS_DND_DESCRIPTION = "in dnd";
	    public static final String BULK_SMS_DND_DESCRIPTION = "DNDFiltered";
	    public static final String SMS_JOB_ID = "job_id";

	    public static final String SMS_STATUS_SUBMITTED = "SUBMITTED";
	    public static final String SMS_STATUS_DELIVERED = "DELIVRD";
	    public static final String SMS_STATUS_DELIVERED_MSG = "DELIVERED";
	    public static final String SMS_STATUS_DND_FILTERED = "DND_FILTERED";
	    public static final String SMS_STATUS_DND_FILTERED_DESC = "DND FILTERED";
	    public static final String SMS_SENT_AT = "sent_at";
	    public static final String SMS_INVALID_JOB_ID = "INVALID_JOB_ID";
	    public static final String SMS_INVALID_JOB_ID_DESC = "Invalid Job Id";
	    public static final String SMS_SYSTEM_ERROR = "System Error";

		public static final String POST = "POST";
		public static final String CHARACTER_SET="UTF-8";

		public static final String STATUS = "status";
		
		 public static final String ERROR = "error";
		 
		 public static final String GET = "GET";
    
		 public static final String ERROR_CODE = "ErrorCode";
		 public static final String SMS_STATUS_FAIL = "Failed";

    /**
     * Send sms.
     * @param smsType the sms type
     * @param receiverMobNo the receiver mob no
     * @param message the message
     * @return the string
     */
    public String sendSMS(int smsType, String receiverMobNo, String message);

    /**
     * Gets the sMS status.
     * @param url the url
     * @return the sMS status
     */
    public String getSMSStatus(String url);

    /**
     * Add sms history.
     * @param smsHistory the sms history
     */
    public void addSMSHistory(SMSHistory smsHistory);

    /**
     * Edit sms history.
     * @param smsHistory the sms history
     */
    public void editSMSHistory(SMSHistory smsHistory);

    /**
     * List sms history by status.
     * @param status the status
     * @return the list
     */
    public List<SMSHistory> listSMSHistoryByStatus(List<String> status);

    /**
     * Update sms delivery status.
     * @param smsHistoryList the sms history list
     * @return the list
     */
    public List<SMSHistory> updateSMSDeliveryStatus(List<SMSHistory> smsHistoryList);

    /**
     * Find sms history by id.
     * @param id the id
     * @return the sMS history
     */
    public SMSHistory findSmsHistoryById(long id);

    public SmsGroupHeader findGroupByName(String name);

    public SmsGroupHeader addSmsGroup(SmsGroupHeader SmsGroupHeader);

    public void editSmsGroup(SmsGroupHeader SmsGroupHeader,String tenantId);

    public SmsGroupHeader findGroupHeaderById(long id);

    public List<Object[]> listSmsGroup();

    public void removeGroup(SmsGroupHeader SmsGroupHeader);

    public SmsTemplate findSmsTemplateByName(String name);

    public void addSmsTemplate(SmsTemplate smsTemplate);

    public SmsTemplate findSmsTemplateById(long id);

    public void editSmsTemplate(SmsTemplate smsTemplate);

    public List<SmsTemplate> listSmsTemplates();

    public void removeSmsTemplate(SmsTemplate smsTemplate);

    public List<SmsGroupHeader> listGroupHeadersByIds(List<String> ids);

    public long findGroupsMobileNumbersCount(List<String> groupIds);

    public List<Object> listFarmers(String sort, int startIndex, int limit, Farmer farmer);

    public Integer findFarmerMobileNumberCount(String sort, int startIndex, int limit, Farmer farmer);

    public Integer findAgentMobileNumberCount(String sort, int startIndex, int limit, Agent agent);

    public Integer findUserMobileNumberCount(String sort, int startIndex, int limit, User user);

    public List<Object> listAgents(String sort, int startIndex, int limit, Agent agent);

    public List<Object> listUsers(String sort, int startIndex, int limit, User user);
    
    public void removeBlindChilds(String table,String column,String tenantId);
    
    /*public Integer listMappedMobileNumberCountByGroup(long[] ids);*/
    
    public String getMessageHistory();

	
	public List<SmsGroupDetail> listGroupDetailByGroupId(List<String> ids);
}
