/*
 * SeasonDownload.java
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
import java.util.stream.Collectors;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.HarvestSeason;
import com.sourcetrace.eses.entity.LanguagePreferences;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.property.TxnEnrollmentProperties;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

/**
 * The Class SeasonDownload.
 */
@Component
public class SeasonDownload implements ITxnAdapter {

	private static final Logger LOGGER = Logger.getLogger(SeasonDownload.class.getName());

	@Autowired
	private IUtilService utilService;

	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData) {

		/** HEADER VALUES **/
		Head head = (Head) reqData.get(TransactionProperties.HEAD);

		String revisionNo = (String) reqData.get(TxnEnrollmentProperties.SEASON_DOWNLOAD_REVISION_NO);
		/*
		 * if (StringUtil.isEmpty(revisionNo)) throw new
		 * SwitchException(ITxnErrorCodes.EMPTY_SEASON_REVISION_NO);
		 * LOGGER.info("REVISION NO" + revisionNo);
		 */

		if (StringUtil.isEmpty(revisionNo) || !StringUtil.isLong(revisionNo)) {
			revisionNo = "0";
		}

		/** REQUEST VALUES **/
		Map resp = new HashedMap();
		/*List<HarvestSeason> seasonList = utilService
				.listHarvestSeasonByRevisionNo(Long.valueOf(revisionNo));*/// utilService.listSeasons();
		List collection = new ArrayList();
		Map<String, List<LanguagePreferences>> lpMap = null;

		/*if (seasonList != null && !ObjectUtil.isEmpty(seasonList)) {
			List<String> codes = seasonList.stream().map(u -> u.getCode()).collect(Collectors.toList());
			List<LanguagePreferences> lpList = new ArrayList<>();
			if (codes != null && !codes.isEmpty()) {
				lpList = utilService.listLangPrefByCodes(codes);
			}
			if (lpList != null && !ObjectUtil.isEmpty(lpList)) {
				lpMap = lpList.stream().collect(Collectors.groupingBy(LanguagePreferences::getCode));
			}
		}*/

		/*if (!ObjectUtil.isEmpty(seasonList)) {
			for (HarvestSeason season : seasonList) {
				Map seasonObj = new HashMap();
				seasonObj.put(TxnEnrollmentProperties.SEASON_CODE_DOWNLOAD, season.getCode());
				seasonObj.put(TxnEnrollmentProperties.SEASON_NAME, season.getName());
				seasonObj.put(TransactionProperties.LANG_LIST, getCollectionJson(lpMap.get(season.getCode())));

				collection.add(seasonObj);

			}

		}*/

		/*if (!ObjectUtil.isListEmpty(seasonList)) {
			revisionNo = String.valueOf(seasonList.get(0).getRevisionNo());
		}*/

		/** RESPONSE DATA **/
		resp.put(TxnEnrollmentProperties.SEASONS, collection);
		resp.put(TxnEnrollmentProperties.SEASON_DOWNLOAD_REVISION_NO, revisionNo);
		return resp;
	}

	public List getCollectionJson(List<LanguagePreferences> lpListObj) {
		List langColl = new ArrayList();
		if (lpListObj != null && !lpListObj.isEmpty()) {
			for (LanguagePreferences lp : lpListObj) {
				Map langCode = new HashMap();
				langCode.put(TransactionProperties.LANGUAGE_CODE, lp.getLang());
				langCode.put(TransactionProperties.LANGUAGE_NAME, lp.getName());
				langColl.add(langCode);

			}
		}

		return langColl;
	}
}
