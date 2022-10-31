/*
 * SMSService.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.SMSHistory;
import com.sourcetrace.eses.entity.SMSHistoryDetail;
import com.sourcetrace.eses.entity.SmsGroupDetail;
import com.sourcetrace.eses.entity.SmsGroupHeader;
import com.sourcetrace.eses.entity.SmsTemplate;
import com.sourcetrace.eses.entity.User;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.entity.Farmer;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sourcetrace.eses.dao.ISMSDAO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
/**
 * @author PANNEER
 */
@Service
@Transactional
public class SMSService implements ISMSService {
	private static final Logger LOGGER = Logger.getLogger(SMSService.class.getName());
	@Autowired
	private ISMSDAO smsDAO;
	private SMSHistory smsHistory;
	@Autowired
    private IUtilService utilService;

	@Override
	public String sendSMS(int smsType, String receiverMobNo, String message) {
		final StringBuffer stringBuffer = new StringBuffer();
		List<String> usNos = new ArrayList<String>();
		String urlString;
		String userName;
		String apiSecretKey;
		String senderUrl;
		Integer otp;
		URL usURL = null;
		int responseCode;
		try {
			ESESystem preference = utilService.findPrefernceById("1");
			String params = "username=" + preference.getPreferences().get(ESESystem.SMS_USER_NAME);
			String template = preference.getPreferences().get(ESESystem.SMS_TEMPLATE);
			//String sender = "&sender=" + preference.getPreferences().get(ESESystem.SMS_SENDER_ID);
			String sender = preference.getPreferences().get(ESESystem.SMS_SENDER_ID);
			params+="&to="+receiverMobNo;
			params+="&message="+template + message;
			params+="&from="+sender;
			String hash =  preference.getPreferences().get(ESESystem.SMS_TOKEN);
			urlString = preference.getPreferences().get(ESESystem.SMS_BULK_GATEWAY_URL);
			userName = preference.getPreferences().get(ESESystem.SMS_USER_NAME);
			
			Unirest.setTimeouts(0, 0);
		    HttpResponse<String> response = Unirest.post(urlString)
		      .header("apiKey", hash)
		      .header("Content-Type", "application/x-www-form-urlencoded")
		      .field("username", userName)
		      .field("to", receiverMobNo)
		      .field("message",message)
		      .field("from", sender)
		      .asString();
		    String req;
		    JSONObject json = new JSONObject();
		    json.put("url", urlString);
		    json.put("apiKey", hash);
		    json.put("username", userName);
		    json.put("to", receiverMobNo);
		    json.put("message", message);
		    json.put("from", sender);
		    req = json.toString();
		    
					  org.json.JSONObject xmlJSONObj = XML.toJSONObject(response.getBody().toString());
					  org.json.JSONObject  res =  xmlJSONObj.getJSONObject("AfricasTalkingResponse").getJSONObject("SMSMessageData");
					  stringBuffer.append(res.toString());
					  smsHistory = new SMSHistory();
						smsHistory.setSenderMobNo(receiverMobNo);
						smsHistory.setMessage(message);	 
						smsHistory.setSmsType(smsType);
						smsHistory.setStatusZ("0");
						smsHistory.setUuid(req.toString());
						smsHistory.setCreateUser(receiverMobNo);
						smsHistory.setStatusMsg(stringBuffer.toString());
						addSMSHistory(smsHistory);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}

	@Override
	public String getSMSStatus(String url) {

		try {
			URL urlObj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", SMS_CONTENT_TYPE);
			conn.setDoOutput(false);

			if (conn.getResponseCode() != 200) {
				LOGGER.info("Failed : HTTP error code : " + conn.getResponseCode() + "--" + conn.getResponseMessage());
				throw new RuntimeException(
						"Failed : HTTP error code : " + conn.getResponseCode() + "--" + conn.getResponseMessage());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output = null;
			LOGGER.info("Output from Server");
			StringBuilder statusRespData = new StringBuilder();
			while ((output = br.readLine()) != null) {
				statusRespData.append(output);
			}
			conn.disconnect();

			return statusRespData.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"fastalerts\":{\"status\":\"Error\",\"description\":\"System Error\",\"uuid\":\"9999\"}}";
		}
	}

	@Override
	public void addSMSHistory(SMSHistory smsHistory) {

		smsDAO.save(smsHistory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sourcetrace.eses.util.sms.service.ISMSService#editSMSHistory(com.
	 * sourcetrace.eses.util .entity.SMSHistory)
	 */
	@Override
	public void editSMSHistory(SMSHistory smsHistory) {

		smsDAO.update(smsHistory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sourcetrace.eses.util.sms.service.ISMSService#listSMSHistoryByStatus(
	 * java.util.List)
	 */
	@Override
	public List<SMSHistory> listSMSHistoryByStatus(List<String> status) {

		return smsDAO.listSMSHistoryByStatus(status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sourcetrace.eses.util.sms.service.ISMSService#findSmsHistoryById(
	 * long)
	 */
	@Override
	public SMSHistory findSmsHistoryById(long id) {

		return smsDAO.findSmsHistoryById(id);
	}

	private String buildJSONObject(String smsToken, String senderId, String smsRoute, String receiverMobNo,
			String message) {

		JSONObject cred = new JSONObject();
		try {
			cred.put(SMS_PAR_TOKEN, smsToken);
			cred.put(SMS_PAR_MSISDN, receiverMobNo);
			cred.put(SMS_PAR_TEXT, message);
			cred.put(SMS_PAR_SENDER_ID, senderId);
			cred.put(SMS_PAR_ROUTE, smsRoute);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return cred.toString();
	}

	@Override
	public List<SMSHistory> updateSMSDeliveryStatus(List<SMSHistory> smsHistoryList) {

		if (!ObjectUtil.isListEmpty(smsHistoryList)) {
			try {
				ESESystem preference = smsDAO.findPrefernceById(ESESystem.SYSTEM_ESE);
				if (preference.getPreferences().get(ESESystem.SMS_ALTERS).equals(SMS_ALTERS_ON)) {
					for (SMSHistory smsHistory : smsHistoryList) {
						String reqUrl = preference.getPreferences().get(ESESystem.SMS_STATUS_URL);
						/*reqUrl = reqUrl.replace("<!-token-!>", preference.getPreferences().get(ESESystem.SMS_TOKEN))
								.replace("<!-uuid-!>", smsHistory.getUuid());*/
						reqUrl = reqUrl.replace("<!-token-!>", preference.getPreferences().get(ESESystem.SMS_SENDER_ID));
						String statusResponse = getSMSStatus(reqUrl);
						String smsStatus = "", smsDescription = "";
						LOGGER.info("------------------------------------------------");
						LOGGER.info("Request URL :" + reqUrl);
						LOGGER.info("Status Resp :" + statusResponse);
						LOGGER.info("------------------------------------------------");
						if (!ObjectUtil.isEmpty(statusResponse)) {
							try {
								JSONObject respObj = new JSONObject(statusResponse);
								JSONObject fastAlerts = respObj.getJSONObject(ISMSService.SMS_FASTALERTS);
								Object statusObj = fastAlerts.has(ISMSService.SMS_RES_STATUS)
										? fastAlerts.get(ISMSService.SMS_RES_STATUS) : null;
								Object descriptionObj = fastAlerts.has(ISMSService.SMS_RES_DESCR)
										? fastAlerts.get(ISMSService.SMS_RES_DESCR) : null;
								Object smsMessagesObj = fastAlerts.has(ISMSService.SMS_MESSAGES)
										? fastAlerts.get(ISMSService.SMS_MESSAGES) : null;

								String status = (!ObjectUtil.isEmpty(statusObj)) ? String.valueOf(statusObj) : null;
								String descrption = (!ObjectUtil.isEmpty(descriptionObj))
										? String.valueOf(descriptionObj) : null;
								JSONArray messages = (!ObjectUtil.isEmpty(smsMessagesObj)
										&& smsMessagesObj instanceof JSONArray) ? (JSONArray) smsMessagesObj : null;
								if (!ObjectUtil.isEmpty(messages)) {
									for (int i = 0; i < messages.length(); i++) {
										JSONObject msg = (JSONObject) messages.get(i);
										if (!ObjectUtil.isListEmpty(smsHistory.getSmsHistoryDetails())) {
											for (SMSHistoryDetail smsHistoryDetail : smsHistory
													.getSmsHistoryDetails()) {
												if (msg.getString(ISMSService.SMS_PAR_MSISDN)
														.equalsIgnoreCase(smsHistoryDetail.getReceiverNo())) {

													if (!StringUtil.isEmpty(msg.getString(ISMSService.SMS_RES_STATUS))
															&& ISMSService.SMS_STATUS_DELIVERED.equalsIgnoreCase(
																	msg.getString(ISMSService.SMS_RES_STATUS))) {
														smsHistoryDetail
																.setStatus(ISMSService.SMS_STATUS_DELIVERED_MSG);
													} else {
														smsHistoryDetail
																.setStatus(msg.getString(ISMSService.SMS_RES_STATUS));
													}
													if (!StringUtil.isEmpty(smsHistoryDetail.getStatus())
															&& smsHistoryDetail.getStatus().length() > 100) {
														smsHistoryDetail.setStatus(
																smsHistoryDetail.getStatus().substring(0, 100));
													}
													smsHistoryDetail.setLastUpdateDt(new Date());
													/*
													 * Do not break in case same
													 * mobile number present
													 * again
													 */
													// break;
												}
											}
										}
									}
								} else {
									if (ISMSService.SMS_ERROR.equalsIgnoreCase(status)
											&& (ISMSService.SINGLE_SMS_DND_DESCRIPTION.equalsIgnoreCase(descrption)
													|| ISMSService.BULK_SMS_DND_DESCRIPTION
															.equalsIgnoreCase(descrption))) {
										smsStatus = ISMSService.SMS_STATUS_DND_FILTERED;
										smsDescription = ISMSService.SMS_STATUS_DND_FILTERED_DESC;
									} else if (ISMSService.SMS_ERROR.equalsIgnoreCase(status)
											&& (ISMSService.SMS_INVALID_JOB_ID_DESC.equalsIgnoreCase(descrption))) {
										// Considered as Submited for invalid
										// job id
										smsStatus = ISMSService.SMS_INVALID_JOB_ID;
										smsDescription = ISMSService.SMS_INVALID_JOB_ID_DESC;
									} else {
										// Considered as un expected error
										smsDescription = descrption;
										smsStatus = descrption;
										smsHistory.setStatus(status);
										smsHistory.setStatusMsg(smsDescription);
									}
									if (!StringUtil.isEmpty(smsStatus) && !StringUtil.isEmpty(smsDescription)) {
										if (!ObjectUtil.isListEmpty(smsHistory.getSmsHistoryDetails())) {
											for (SMSHistoryDetail smsHistoryDetail : smsHistory
													.getSmsHistoryDetails()) {
												smsHistoryDetail.setStatus(smsStatus.length() > 100
														? smsStatus.substring(0, 100) : smsStatus);
												smsHistoryDetail.setLastUpdateDt(new Date());
											}
										}
									}
								}
							} catch (Exception e) {
								LOGGER.info("Exception : " + e.getMessage());
								e.printStackTrace();
							}
						}
						editSMSHistory(smsHistory);
					}
				}
			} catch (Exception e) {
				LOGGER.info("Exception : " + e.getMessage());
				e.printStackTrace();
			}
		}
		return smsHistoryList;
	}

	@Override
	public SmsGroupHeader findGroupByName(String name) {

		return smsDAO.findGroupByName(name);
	}

	@Override
	public SmsGroupHeader addSmsGroup(SmsGroupHeader smsGroupHeader) {

		smsDAO.save(smsGroupHeader);
		return smsGroupHeader;
	}

	@Override
	public void editSmsGroup(SmsGroupHeader smsGroupHeader, String tenantId) {
		smsDAO.update(smsGroupHeader);
	}

	@Override
	public SmsGroupHeader findGroupHeaderById(long id) {

		return smsDAO.findGroupHeaderById(id);
	}

	@Override
	public List<Object[]> listSmsGroup() {

		return smsDAO.listSmsGroupHeader();
	}

	@Override
	public void removeGroup(SmsGroupHeader smsGroupHeader) {

		smsDAO.delete(smsGroupHeader);
	}

	@Override
	public SmsTemplate findSmsTemplateByName(String name) {

		return smsDAO.findSmsTemplateByName(name);
	}

	@Override
	public void addSmsTemplate(SmsTemplate smsTemplate) {

		smsDAO.save(smsTemplate);
	}

	@Override
	public SmsTemplate findSmsTemplateById(long id) {

		return smsDAO.findSmsTemplateById(id);
	}

	@Override
	public void editSmsTemplate(SmsTemplate smsTemplate) {

		smsDAO.update(smsTemplate);
	}

	@Override
	public List<SmsTemplate> listSmsTemplates() {

		return smsDAO.listSmsTemplates();
	}

	@Override
	public void removeSmsTemplate(SmsTemplate smsTemplate) {

		smsDAO.delete(smsTemplate);

	}

	@Override
	public List<SmsGroupHeader> listGroupHeadersByIds(List<String> ids) {

		return smsDAO.listGroupHeadersByIds(ids);
	}

	@Override
	public long findGroupsMobileNumbersCount(List<String> groupIds) {

		return smsDAO.findGroupsMobileNumbersCount(groupIds);
	}

	@Override
	public List<Object> listFarmers(String sort, int startIndex, int limit, Farmer farmer) {
		return smsDAO.listFarmers(sort, startIndex, limit, farmer);
	}

	public Integer findFarmerMobileNumberCount(String sort, int startIndex, int limit, Farmer farmer) {
		return smsDAO.findFarmerMobileNumberCount(sort, startIndex, limit, farmer);
	}

	@Override
	public List<Object> listAgents(String sort, int startIndex, int limit, Agent agent) {
		return smsDAO.listAgents(sort, startIndex, limit, agent);
	}

	@Override
	public List<Object> listUsers(String sort, int startIndex, int limit, User user) {
		return smsDAO.listUsers(sort, startIndex, limit, user);
	}

	@Override
	public Integer findAgentMobileNumberCount(String sort, int startIndex, int limit, Agent agent) {
		return smsDAO.findAgentMobileNumberCount(sort, startIndex, limit, agent);
	}

	@Override
	public Integer findUserMobileNumberCount(String sort, int startIndex, int limit, User user) {
		return smsDAO.findUserMobileNumberCount(sort, startIndex, limit, user);
	}

	@Override
	public void removeBlindChilds(String table, String column, String tenantId) {
		smsDAO.removeBlindChilds(table, column, tenantId);
	}

	public String getMessageHistory() {
		try {
			ESESystem preference = smsDAO.findPrefernceById("1");
			String user = "username=" + preference.getPreferences().get(ESESystem.SMS_USER_NAME);

			String hash = "&hash=" + preference.getPreferences().get(ESESystem.SMS_TOKEN);
			
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/get_history_api/?").openConnection();
			
			String data = user + hash;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
			stringBuffer.append(line);
			}
			rd.close();
			
			return stringBuffer.toString();
		} catch (Exception e) {
			return "Error "+e;
		}
	}
	
	public String getCurrentTenantId() {

		HttpServletRequest request = ReflectUtil.getCurrentHttpRequest();
		String tenantId = ISecurityFilter.DEFAULT_TENANT_ID;
		if (!ObjectUtil.isEmpty(request)) {
			tenantId = (String) request.getSession().getAttribute(ISecurityFilter.TENANT_ID);
		}
		if (StringUtil.isEmpty(tenantId)) {
			tenantId = ISecurityFilter.DEFAULT_TENANT_ID;
		}
		return tenantId;
	}

	

	/*
	 * @Override public Integer listMappedMobileNumberCountByGroup(long[] ids) {
	 * return smsDAO.listMappedMobileNumberCountByGroup(ids); }
	 */
	
	public boolean isUSNo(String receiverMobNo) {
		boolean retVal = false;
		String[] receiverNos;
		char[] receiverNoChar;
		char firstCharNo;
		char secondCharNo;
		if (receiverMobNo.contains(",")) {
			receiverNos = receiverMobNo.split(",");
		} else {
			receiverNos = new String[1];
			receiverNos[0] = receiverMobNo;
		}
		for (String receiverNo : receiverNos) {
			receiverNoChar = receiverNo.toCharArray();
			if (receiverNoChar.length >= 2) {
				firstCharNo = receiverNoChar[0];
				secondCharNo = receiverNoChar[1];
				if (firstCharNo == '1' || firstCharNo == '+') {
					if (firstCharNo == '+') {
						if (secondCharNo == '1') {
							retVal = true;
							return retVal;
						}
					} else {
						retVal = true;
					}
				}
			}
		}
		return retVal;
	}
	
	public List<String> getUSNos(String receiverMobNo) {

		List<String> retVal = new ArrayList<String>();
		String[] receiverNos;
		char[] receiverNoChar;
		char firstCharNo;
		char secondCharNo;
		if (receiverMobNo.contains(",")) {
			receiverNos = receiverMobNo.split(",");
		} else {
			receiverNos = new String[1];
			receiverNos[0] = receiverMobNo;
		}
		for (String receiverNo : receiverNos) {
			receiverNoChar = receiverNo.toCharArray();
			if (receiverNoChar.length >= 2) {
				firstCharNo = receiverNoChar[0];
				secondCharNo = receiverNoChar[1];
				if (firstCharNo == '1' || firstCharNo == '+') {
					if (firstCharNo == '+') {
						if (secondCharNo == '1') {
							retVal.add(receiverNo.trim());
						}
					} else {
						retVal.add(receiverNo.trim());
					}
				}
			}
		}
		return retVal;

	}
	
	public String getNonUSNos(String receiverMobNo) {

		String retVal = "";
		String[] receiverNos;
		char[] receiverNoChar;
		char firstCharNo;
		if (receiverMobNo.contains(",")) {
			receiverNos = receiverMobNo.split(",");
		} else {
			receiverNos = new String[1];
			receiverNos[0] = receiverMobNo;
		}
		for (String receiverNo : receiverNos) {
			receiverNoChar = receiverNo.toCharArray();
			if (receiverNoChar.length >= 2) {
				firstCharNo = receiverNoChar[0];
				if (firstCharNo != '1') {
					retVal += receiverNo.trim() + ",";

				}
			}
		}
		retVal = StringUtil.removeLastComma(retVal);
		return retVal;

	}
	
	public String processUrlStringForTextingApi(String urlString, String userName, String apiSecretKey, String receiverMobNo,
			String senderUrl, String message) {
		String urlExt = "/Message/Send?";
		String retStr = "";
		String apiKeySecretUrl = "&api_secret=";
		String userNameUrl = "api_key=";
		String from = "&from=";
		String to = "&to=";
		String text = "&text=";
		retStr = urlString + urlExt;
		retStr = retStr + userNameUrl + userName;
		retStr = retStr + apiKeySecretUrl + apiSecretKey;
		retStr = retStr + from + senderUrl;
		retStr = retStr + to + receiverMobNo;
		retStr = retStr + text + message;
		return retStr;
	}
	
	public int doUsSMSRequest(String urlString) throws IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url(urlString).get().addHeader("Accept", "*/*")
				.addHeader("Cache-Control", "no-cache").addHeader("Host", "www.thetexting.com")
				.addHeader("accept-encoding", "gzip, deflate").addHeader("Connection", "keep-alive")
				.addHeader("cache-control", "no-cache").build();

		Response response = client.newCall(request).execute();
		LOGGER.info("US SMS Response:" + response.body().string());
		return response.code();
	}
	
	public List<SmsGroupDetail> listGroupDetailByGroupId(List<String> ids){
		return smsDAO.listGroupDetailByGroupId(ids);
		
	}
	
	


}
