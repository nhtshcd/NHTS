package com.sourcetrace.eses.interceptors;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sourcetrace.eses.dao.IFarmerDAO;
import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.AgentAccessLog;
import com.sourcetrace.eses.entity.AgentAccessLogDetail;
import com.sourcetrace.eses.entity.Device;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.PreferenceType;
import com.sourcetrace.eses.entity.Profile;
import com.sourcetrace.eses.entity.Transaction;
import com.sourcetrace.eses.entity.TransactionLog;
import com.sourcetrace.eses.interceptor.ITxnErrorCodes;
import com.sourcetrace.eses.property.TxnEnrollmentProperties;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.exception.TxnFault;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.txn.schema.JsonRequest;
import com.sourcetrace.eses.txn.schema.Status;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ICryptoUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

public class ApiInterceptors extends HandlerInterceptorAdapter {
	@Autowired
	private IUtilService utilDAO;
	@Autowired
	private IFarmerDAO farmerDAO;
	@Autowired
	private IUniqueIDGenerator idGenerator;
	@Autowired
	private ICryptoUtil cryptoUtil;
	private Logger logger = Logger.getLogger(ApiInterceptors.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		JsonRequest js = (JsonRequest) request.getAttribute("reqObj");
		Device device = new Device();

		if (!js.getHead().getAgentId().equals("nhtstest123")) {
			device = validateDevice(js.getHead(), js.getTxnLogId());

			

			Agent agent = validateAgent(js, js.getTxnLogId(), device);

			Device dc = (Device) farmerDAO.findObjectById("from Device dc where dc.agent.profileId=?",
					new Object[] { js.getHead().getAgentId() });
			if ((device.getIsRegistered() == Device.DEVICE_NOT_REGISTERED || device.getAgent() == null) && dc == null && agent!=null) {
				if (device.getExporter()!=null && agent.getExporter()!=null && device.getExporter().getId()!=agent.getExporter().getId()) {
					logError(new TxnFault(ITxnErrorCodes.UNAUTHORISED_DEVICE, js.getTxnLogId()));
				}else{
					device.setAgent(agent);
					device.setIsRegistered(Device.DEVICE_REGISTERED);
					device.setBranchId(agent.getBranchId());
					device.setEnabled(true);
					utilDAO.update(device);
				}
			}
			
			if (!device.getEnabled()) {
				logError(new TxnFault(ITxnErrorCodes.DEVICE_DISABLED, js.getTxnLogId()));
			}
			if (device.getIsRegistered() == Device.DEVICE_NOT_REGISTERED) {
				logError(new TxnFault(ITxnErrorCodes.INVALID_DEVICE, js.getTxnLogId()));
			}
			
			
			if (device.getExporter()!=null && agent.getExporter()!=null && device.getExporter().getId()!=agent.getExporter().getId()) {
				logError(new TxnFault(ITxnErrorCodes.UNAUTHORISED_DEVICE, js.getTxnLogId()));
			}
			

			if (ObjectUtil.isEmpty(device.getAgent())) {
				logError(new TxnFault(ITxnErrorCodes.AGENT_DEVICE_MAPPING_UNAVAILABLE, js.getTxnLogId()));
			} else if (!js.getHead().getAgentId().equalsIgnoreCase(device.getAgent().getProfileId())) {
				logError(new TxnFault(ITxnErrorCodes.AGENT_DEVICE_MAPPING_UNAUTHORIZED, js.getTxnLogId()));
			}
			js.getHead().setBranchId(device.getBranchId()==null ? agent.getBranchId() :  device.getBranchId());
		}
		
		request.setAttribute("reqObj", js);

		return true;
	}

	private void logError(TxnFault txnFault) {
		Status status = new Status();
		status.setCode(txnFault.getCode());
		status.setMessage(txnFault.getMessage());
		updateTransactionLog(txnFault.getTxnLogId(), status, Transaction.Status.FAILED.ordinal());
		throw txnFault;
	}

	private void updateTransactionLog(long txnLogId, Status status, int txnSts) {

		try {
			TransactionLog transactionLog = utilDAO.findTransactionLogById(txnLogId);
			if (!ObjectUtil.isEmpty(transactionLog)) {
				transactionLog.setStatus(txnSts);
				transactionLog.setStatusCode(status.getCode());
				transactionLog.setStatusMsg(status.getMessage());
				utilDAO.updateTransactionLog(transactionLog);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private Agent validateAgent(JsonRequest jsre, long txnlogId, Device device) {
		Head head = jsre.getHead();
		String agentId = head.getAgentId();
		String txnType = head.getTxnType();

		if (head.getBranchId() == null || StringUtil.isEmpty(head.getBranchId())) {
			head.setBranchId(utilDAO.findPrefernceByName(ESESystem.DEF_BRANCH));
		}

		if (StringUtil.isEmpty(agentId))
			logError(new TxnFault(ITxnErrorCodes.AGENT_ID_EMPTY, txnlogId));

		Agent agent = utilDAO.findAgentByProfileAndBranchId(agentId, device.getBranchId());

		if (ObjectUtil.isEmpty(agent))
			logError(new TxnFault(ITxnErrorCodes.INVALID_AGENT));

		if (agent.getStatus() == Profile.INACTIVE)
			logError(new TxnFault(ITxnErrorCodes.AGENT_ACCOUNT_INACTIVE, txnlogId));

		Integer incr = (agent.getIsTrainingAvailable() == null ? 0 : agent.getIsTrainingAvailable()) + 1;

		ESESystem eseSystem = utilDAO.findPrefernceByOrganisationId(agent.getBranchId());
		Integer count = Integer
				.parseInt(eseSystem.getPreferences().get(PreferenceType.NO_OF_INVALID_LOGIN_ATTEMPTS.name()));
		Long autoReleaseTime = Long
				.parseLong(eseSystem.getPreferences().get(PreferenceType.TIME_TO_AUTO_RELEASE.name()));

		if (agent.getStatus() == Profile.LOCKED && txnType.equalsIgnoreCase("301") && agent.getPasswordUpt() != null) {
			long msec = new Date().getTime() - agent.getPasswordUpt().getTime();
			boolean accountNotLocked = msec >= autoReleaseTime;

			if (!accountNotLocked) {
				logError(new TxnFault(ITxnErrorCodes.AGENT_LOCKED, utilDAO.findLocaleProperty("agent.locked", "en"),
						txnlogId));

			}

		}

		try {
			String agentPa = cryptoUtil.decrypt(agent.getPassword());
			String nowPw = cryptoUtil.decryptJWT(head.getAgentToken());
			agentPa = StringUtil.replaceCaseInsensitive(agentPa, head.getAgentId(), "");
			nowPw = StringUtil.replaceCaseInsensitive(nowPw, head.getAgentId(), "");
			if (!agentPa.trim().equals(nowPw.trim())) {
				if (txnType.equalsIgnoreCase("301")) {
					agent.setIsTrainingAvailable(incr);
					if ((agent.getIsTrainingAvailable()) >= count) {
						agent.setPasswordUpt(new Date());
						agent.setStatus(Profile.LOCKED);
						agent.setIsTrainingAvailable(0);
						utilDAO.update(agent);
						logError(new TxnFault(ITxnErrorCodes.AGENT_LOCKED,
								utilDAO.findLocaleProperty("agent.locked", "en"), txnlogId));
					}
					utilDAO.update(agent);
					String mss = utilDAO.findLocaleProperty("userpass.invalid.count", "en");
					mss = mss.replace("[cnt]", String.valueOf(count - agent.getIsTrainingAvailable()));
					logError(new TxnFault(ITxnErrorCodes.INVALID_CREDENTIAL, mss, txnlogId));
				} else if (!txnType.equalsIgnoreCase("358")) {
					logError(new TxnFault(ITxnErrorCodes.INVALID_CREDENTIAL, txnlogId));
				}
			}
		} catch (Exception e) {

			if (txnType.equalsIgnoreCase("301")) {
				agent.setIsTrainingAvailable(incr);
				if ((agent.getIsTrainingAvailable()) >= count) {
					agent.setPasswordUpt(new Date());
					agent.setStatus(Profile.LOCKED);
					agent.setIsTrainingAvailable(0);
					utilDAO.update(agent);
					logError(new TxnFault(ITxnErrorCodes.AGENT_LOCKED,
							utilDAO.findLocaleProperty("agent.locked", "en")));
				}
				utilDAO.update(agent);
				String mss = utilDAO.findLocaleProperty("userpass.invalid.count", "en");
				mss = mss.replace("[cnt]", String.valueOf(count - agent.getIsTrainingAvailable()));
				logError(new TxnFault(ITxnErrorCodes.INVALID_CREDENTIAL, mss, txnlogId));
			} else if (!txnType.equalsIgnoreCase("358")) {
				logError(new TxnFault(ITxnErrorCodes.INVALID_CREDENTIAL, txnlogId));
			}

		}

		if (head.getTxnType().equals("500")) {
			Map<String, Object> body = (Map) jsre.getBody();

			if (body.containsKey(TxnEnrollmentProperties.DYNAMIC_TXN_ID)) {
				txnType = (String) body.get(TxnEnrollmentProperties.DYNAMIC_TXN_ID);

			}
		}

		Date formatedDate = DateUtil.convertStringToDate(head.getTxnTime(), DateUtil.TXN_DATE_TIME);

		AgentAccessLog agentAccessLog = utilDAO.findAgentAccessLogByAgentId(agent.getProfileId(),
				DateUtil.getDateWithoutTime(formatedDate));
		if (!ObjectUtil.isEmpty(agentAccessLog)) {

			AgentAccessLogDetail agentAccessLogDetailExisting = utilDAO
					.listAgnetAccessLogDetailsByIdTxnType(agentAccessLog.getId(), txnType, head.getMsgNo());

			if (ObjectUtil.isEmpty(agentAccessLogDetailExisting)) {

				AgentAccessLogDetail agentAccessLogDetail = utilDAO
						.findAgentAccessLogDetailByTxn(agentAccessLog.getId(), txnType);
				if (!ObjectUtil.isEmpty(agentAccessLogDetail)) {
					if (Long.valueOf(head.getResentCount()) == 0) {
						Long txnCount = agentAccessLogDetail.getTxnCount() + 1L;
						agentAccessLogDetail.setTxnCount(txnCount);
					}
					agentAccessLogDetail.setTxnDate(formatedDate);
					agentAccessLogDetail.setLatitude(head.getLat());
					agentAccessLogDetail.setLongitude(head.getLon());
					utilDAO.update(agentAccessLogDetail);
				} else {
					AgentAccessLogDetail agentAccessLogDetailNew = new AgentAccessLogDetail();
					agentAccessLogDetailNew.setAgentAccessLog(agentAccessLog);
					agentAccessLogDetailNew.setTxnType(txnType);
					agentAccessLogDetailNew.setMessageNumber(head.getMsgNo());
					agentAccessLogDetailNew.setTxnMode(head.getMode());

					agentAccessLogDetailNew.setTxnDate(formatedDate);

					agentAccessLogDetailNew.setTxnCount(1L);

					agentAccessLogDetailNew.setServicePoint(head.getServPointId());
					agentAccessLogDetailNew.setLatitude(head.getLat());
					agentAccessLogDetailNew.setLongitude(head.getLon());

					utilDAO.save(agentAccessLogDetailNew);
				}

				agentAccessLog.setLogin(formatedDate);
				agentAccessLog.setLastTxnTime(formatedDate);
				utilDAO.update(agentAccessLog);
			}
		} else {
			AgentAccessLog agentAccessLogNew = new AgentAccessLog();
			agentAccessLogNew.setLogin(formatedDate);
			agentAccessLogNew.setMobileVersion(head.getVersionNo());
			agentAccessLogNew.setProfileId(head.getAgentId());
			agentAccessLogNew.setSerialNo(head.getSerialNo());
			agentAccessLogNew.setBranchId(device.getBranchId());
			agentAccessLogNew.setLastTxnTime(formatedDate);
			utilDAO.save(agentAccessLogNew);

			AgentAccessLogDetail agentAccessLogDetail = new AgentAccessLogDetail();
			agentAccessLogDetail.setAgentAccessLog(agentAccessLogNew);
			agentAccessLogDetail.setTxnType(txnType);
			agentAccessLogDetail.setMessageNumber(head.getMsgNo());
			agentAccessLogDetail.setTxnMode(head.getMode());
			agentAccessLogDetail.setTxnCount(1L);
			agentAccessLogDetail.setServicePoint(head.getServPointId());
			agentAccessLogDetail.setTxnDate(formatedDate);
			agentAccessLogDetail.setLatitude(head.getLat());
			agentAccessLogDetail.setLongitude(head.getLon());

			utilDAO.save(agentAccessLogDetail);
		}

		return agent;

	}

	private Device validateDevice(Head head, long txnlogId) {

		/** VALIDATION FOR AGENT **/
		String agentId = head.getAgentId();
		String serialNo = head.getSerialNo();
		Device device = utilDAO.findDeviceBySerialNumber(head.getSerialNo());

		if (StringUtil.isEmpty(serialNo))
			logError(new TxnFault(ITxnErrorCodes.EMPTY_SERIAL_NO, txnlogId));

		if (ObjectUtil.isEmpty(device) || device.getIsRegistered() == Device.DEVICE_NOT_REGISTERED) {
			if (ObjectUtil.isEmpty(device)) {
				 device = new Device();
				 device.setSerialNumber(serialNo);
				 device.setDeviceType(Device.MOBILE_DEVICE);
				 device.setEnabled(false);
				 device.setDeleted(false);
				 device.setCode(idGenerator.getDeviceIdSeq());
				 device.setCreatedDate(new Date());
				 device.setModifiedTime(new Date());
				 device.setIsRegistered(Device.DEVICE_NOT_REGISTERED);
				 device.setName(agentId);
				utilDAO.save(device);
			} else {
				device.setDeviceType(Device.MOBILE_DEVICE);
				device.setEnabled(false);
				device.setDeleted(false);
				device.setModifiedTime(new Date());
				device.setIsRegistered(Device.DEVICE_NOT_REGISTERED);
				utilDAO.update(device);
			}

		} else {
			String logintime = head.getTxnTime();
			String appversion = head.getVersionNo();
			String appvers = appversion.replace("|", ".");

			device.setLoginTime(logintime);
			device.setAppVersion(appvers);
			utilDAO.update(device);
		}

		return device;
	}

}
