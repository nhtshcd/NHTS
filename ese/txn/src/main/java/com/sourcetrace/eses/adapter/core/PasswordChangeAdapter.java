package com.sourcetrace.eses.adapter.core;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.PasswordHistory;
import com.sourcetrace.eses.interceptor.ITxnErrorCodes;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.property.TxnEnrollmentProperties;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.exception.SwitchException;
import com.sourcetrace.eses.txn.exception.TxnFault;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ICryptoUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class PasswordChangeAdapter implements ITxnAdapter {
	@Autowired
	private IUtilService utilService;
	@Autowired
	private ICryptoUtil cryptoUtil;

	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData) {
		Map insResponse = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		String agentId = head.getAgentId();
		if (StringUtil.isEmpty(agentId))
			throw new SwitchException(ITxnErrorCodes.AGENT_ID_EMPTY);

		String pass = (String) reqData.get("nPwd");
		String date = (String) reqData.get(TransactionProperties.REMOTE_PASSWORD_CHANGE_DATE);

		Agent agent = utilService.findAgentByAgentId(agentId);
		if (!ObjectUtil.isEmpty(agent)) {

			agent.setPassword(pass);
			
			try {
				String nowPw = cryptoUtil.decryptJWT(pass);
				agent.setPassword(nowPw);
				
			} catch (Exception e) {
				String mss = utilService.findLocaleProperty("invalid.password", "en");
				throw new TxnFault("550", mss);

			}
			
			//String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{%%min%%,10000000000}$";
			String PASSWORD_PATTERN = "^(?=.*).{%%min%%,10000000000}$";
			ESESystem es = utilService.findPrefernceByOrganisationId(agent.getBranchId());
			PASSWORD_PATTERN = PASSWORD_PATTERN.replace("%%min%%",
					es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString());

			Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

			if (agent.getId() != null) {
				List<String> pwList = utilService.listPasswordsByTypeAndRefId(
						String.valueOf(PasswordHistory.Type.MOBILE_USER.ordinal()), agent.getId());
				if (pwList != null) {
					pwList.stream().forEach(uu -> {
						String plainText = cryptoUtil.decrypt(uu);
						if (!StringUtil.isEmpty(plainText)) {
							plainText = plainText.trim();
							plainText = plainText.substring(agent.getProfileId().length(), plainText.length()).trim();
							
								if (plainText.equals(agent.getPassword())) {
									String mss = utilService.findLocaleProperty("repeated.password", "en");
									throw new TxnFault("550", mss);
								}
							
						}
					});

				}
			}
			Matcher matcher = pattern.matcher(agent.getPassword());
			if (!matcher.matches()) {
				String mss = utilService.findLocaleProperty("agent.mishmatchbyregex", "en");
				mss = mss.replace("[min]",
						String.valueOf(es.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH).toString()));

				throw new TxnFault("551", mss);
			} else if (agent.getPassword() != null && !StringUtil.isEmpty(agent.getPassword())) {
				if (agent.getPersonalInfo() != null && agent.getPersonalInfo().getFirstName() != null
						&& !StringUtil.isEmpty(agent.getPersonalInfo().getFirstName()) && agent.getPassword()
								.toLowerCase().contains(agent.getPersonalInfo().getFirstName().toLowerCase())) {
					throw new TxnFault("552", utilService.findLocaleProperty("agent.namecontains", "en"));
				}

				if (agent.getPersonalInfo() != null && agent.getPersonalInfo().getLastName() != null
						&& !StringUtil.isEmpty(agent.getPersonalInfo().getLastName()) && agent.getPassword()
								.toLowerCase().contains(agent.getPersonalInfo().getLastName().toLowerCase())) {
					throw new TxnFault("552", utilService.findLocaleProperty("agent.namecontains", "en"));
				}

				if (agent.getProfileId() != null && agent.getProfileId() != null
						&& agent.getPassword().toLowerCase().contains(agent.getProfileId().toLowerCase())) {
					throw new TxnFault("552", utilService.findLocaleProperty("agent.namecontains", "en"));
				}
			}

			if (insResponse == null || insResponse.isEmpty()) {
				String nepp =cryptoUtil
				.encrypt(StringUtil.getMulipleOfEight(agent.getProfileId() + agent.getPassword()));
				agent.setPassword(nepp);
				agent.setUpdTime(new Date());
				agent.setEnrollId("1");
				utilService.editAgent(agent);
				PasswordHistory ph = new PasswordHistory();
				ph.setBranchId(agent.getBranchId());
				ph.setCreatedDate(DateUtil.convertStringToDate(date, DateUtil.DATE_FORMAT_4));
				ph.setType(String.valueOf(PasswordHistory.Type.MOBILE_USER.ordinal()));
				ph.setPassword(agent.getPassword());
				ph.setReferenceId(agent.getId());
				utilService.save(ph);
			}

		}
		return insResponse;
	}

}
