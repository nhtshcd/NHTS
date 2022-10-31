/*
 * LocationDownload.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.adapter.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.Country;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.GramPanchayat;
import com.sourcetrace.eses.entity.LanguagePreferences;
import com.sourcetrace.eses.entity.Locality;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.entity.Village;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.property.TxnEnrollmentProperties;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;


@Component
public class LocationDownloadAdapter implements ITxnAdapter {

	private static Logger LOGGER = Logger.getLogger(LocationDownloadAdapter.class);
	@Autowired
	private IUtilService utilService;
	private long countryRevisionNo;
	private long stateRevisionNo;
	private long districtRevisionNo;
	private long cityRevisionNo;
	private long panchayatRevisionNo;
	private long villageRevisionNo;
	private boolean initialDownload;
	private Integer gmEnable = 0;

		/**
	 * Initialize revision numbers.
	 */
	private void initializeRevisionNumbers() {

		countryRevisionNo = 0;
		stateRevisionNo = 0;
		districtRevisionNo = 0;
		cityRevisionNo = 0;
		panchayatRevisionNo = 0;
		villageRevisionNo = 0;
		initialDownload = true;
	}


	@SuppressWarnings("unchecked")
	private List buildCountriesCollectionJson(List<Country> countriesList, String tenantId,
			Map<String, List<LanguagePreferences>> lpMap) {

		List countryCollection = new ArrayList();
		if (!ObjectUtil.isListEmpty(countriesList)) {
			for (Country country : countriesList) {
				Map countryDataList = new HashMap();
				countryDataList.put(TransactionProperties.COUNTRY_CODE, country.getCode());
				countryDataList.put(TransactionProperties.COUNTRY_NAME, country.getName());
				countryDataList.put(TransactionProperties.LANG_LIST, getCollectionJson(lpMap.get(country.getCode())));

				if (initialDownload) {
					countryDataList
							.put(TransactionProperties.STATE_LIST,
									buildSatatesCollectionJson(
											!ObjectUtil.isListEmpty(country.getStates())
													? new LinkedList<State>(country.getStates()) : null,
											tenantId, lpMap));

					countryRevisionNo = Math.max(country.getRevisionNo(), countryRevisionNo);
				}

				countryCollection.add(countryDataList);
			}
			if (!initialDownload)
				countryRevisionNo = countriesList.get(0).getRevisionNo();
		}

		return countryCollection;
	}

	/**
	 * Builds the satates JSONArray.
	 * 
	 * @param statesList
	 *            the states list
	 * @param tenantId
	 * @return the JSONArray
	 * @throws JSONException 
	 */
	@SuppressWarnings("unchecked")
	private List buildSatatesCollectionJson(List<State> statesList, String tenantId,
			Map<String, List<LanguagePreferences>> lpMap){

		List stateCollection = new ArrayList();
		if (!ObjectUtil.isListEmpty(statesList)) {
			for (State state : statesList) {
				Map stateDataList = new HashMap();
				stateDataList.put(TransactionProperties.STATE_CODE, state.getCode());
				stateDataList.put(TransactionProperties.STATE_NAME, state.getName());
				stateDataList.put(TransactionProperties.LANG_LIST, getCollectionJson(lpMap.get(state.getCode())));

				if (initialDownload) {

					stateDataList
							.put(TransactionProperties.DISTRICT_LIST,
									buildDistrictsCollectionJson(
											!ObjectUtil.isListEmpty(state.getLocalities())
													? new LinkedList<Locality>(state.getLocalities()) : null,
											tenantId, lpMap));
					stateRevisionNo = Math.max(state.getRevisionNo(), stateRevisionNo);
				} else {

					stateDataList.put(TransactionProperties.COUNTRY_CODE, state.getCountry().getCode());

				}
				stateCollection.add(stateDataList);
			}
			if (!initialDownload)
				stateRevisionNo = statesList.get(0).getRevisionNo();
		}
		return stateCollection;

	}

	/**
	 * Builds the districts JSONArray.
	 * 
	 * @param districtsList
	 *            the districts list
	 * @param tenantId
	 * @return the JSONArray
	 * @throws JSONException 
	 */
	@SuppressWarnings("unchecked")
	private List buildDistrictsCollectionJson(List<Locality> districtsList, String tenantId,
			Map<String, List<LanguagePreferences>> lpMap) {

		List districtCollection = new ArrayList();
		if (!ObjectUtil.isListEmpty(districtsList)) {
			for (Locality district : districtsList) {
				Map stateDataList = new HashMap();
				stateDataList.put(TransactionProperties.DISTRICT_CODE, district.getCode());
				stateDataList.put(TransactionProperties.DISTRICT_NAME, district.getName());
				stateDataList.put(TransactionProperties.LANG_LIST, getCollectionJson(lpMap.get(district.getCode())));

				if (initialDownload) {

					stateDataList.put(TransactionProperties.CITY_LIST,
							buildCitiesCollectionJson(
									!ObjectUtil.isListEmpty(district.getMunicipalities())
									? new LinkedList<Municipality>(district.getMunicipalities()) : null,
									tenantId, lpMap));
					districtRevisionNo = Math.max(district.getRevisionNo(), stateRevisionNo);
				} else {

					stateDataList.put(TransactionProperties.STATE_CODE, district.getState().getCode());

				}
				districtCollection.add(stateDataList);
			}
			if (!initialDownload)
				districtRevisionNo = districtsList.get(0).getRevisionNo();
		}
		return districtCollection;

	}

	/**
	 * Builds the cities JSONArray.
	 * 
	 * @param citiesList
	 *            the cities list
	 * @param tenantId
	 * @return the JSONArray
	 * @throws JSONException 
	 */
	@SuppressWarnings("unchecked")
	private List buildCitiesCollectionJson(List<Municipality> citiesList, String tenantId,
			Map<String, List<LanguagePreferences>> lpMap) {
		List cityCollection = new ArrayList();
		if (!ObjectUtil.isListEmpty(citiesList)) {
			for (Municipality city : citiesList) {
				Map cityDataList = new HashMap();
				cityDataList.put(TransactionProperties.CITY_CODE, city.getCode());
				cityDataList.put(TransactionProperties.CITY_NAME, city.getName());
				cityDataList.put(TransactionProperties.LANG_LIST, getCollectionJson(lpMap.get(city.getCode())));

				if (initialDownload) {

					if (gmEnable == 1) {
						cityDataList.put(TransactionProperties.GRAM_PANCHAYAT_LIST,
								buildPanchayatsCollectionJson(
										!ObjectUtil.isListEmpty(city.getGramPanchayats())
												? new LinkedList<GramPanchayat>(city.getGramPanchayats()) : null,
										tenantId, lpMap));

					} else {

						cityDataList.put(TransactionProperties.VILLAGE_LIST,
								buildVillagesCollectionJson(!ObjectUtil.isListEmpty(city.getVillages())
										? new LinkedList<Village>(city.getVillages()) : null, lpMap));

					}

					cityRevisionNo = Math.max(city.getRevisionNo(), cityRevisionNo);
				} else {

					cityDataList.put(TransactionProperties.DISTRICT_CODE, city.getLocality().getCode());

				}
				cityCollection.add(cityDataList);
			}
			if (!initialDownload)
				cityRevisionNo = citiesList.get(0).getRevisionNo();
		}
		return cityCollection;
	}

	/**
	 * Builds the panchayats JSONArray.
	 * 
	 * @param panchayatsList
	 *            the panchayats list
	 * @return the JSONArray
	 * @throws JSONException 
	 */
	private List buildPanchayatsCollectionJson(List<GramPanchayat> panchayatsList, String tenantId,
			Map<String, List<LanguagePreferences>> lpMap) {

		List panchayatCollection = new ArrayList();
		if (!ObjectUtil.isListEmpty(panchayatsList)) {
			for (GramPanchayat panchayat : panchayatsList) {

				Map panchayatDataList = new HashMap();
				panchayatDataList.put(TransactionProperties.PANCHAYAT_CODE, panchayat.getCode());
				panchayatDataList.put(TransactionProperties.PANCHAYAT_NAME, panchayat.getName());
				panchayatDataList.put(TransactionProperties.LANG_LIST, getCollectionJson(lpMap.get(panchayat.getCode())));

				if (initialDownload) {
					panchayatDataList.put(TransactionProperties.VILLAGE_LIST,
							buildVillagesCollectionJson(!ObjectUtil.isListEmpty(panchayat.getVillages())
									? new LinkedList<Village>(panchayat.getVillages()) : null, lpMap));

					panchayatRevisionNo = Math.max(panchayat.getRevisionNo(), panchayatRevisionNo);
				} else {
					panchayatDataList.put(TransactionProperties.CITY_CODE, panchayat.getCity().getCode());

				}
				panchayatCollection.add(panchayatDataList);
			}
			if (!initialDownload)
				panchayatRevisionNo = panchayatsList.get(0).getRevisionNo();
		}
		return panchayatCollection;
	}

	/**
	 * Builds the villages JSONArray.
	 * 
	 * @param villagesList
	 *            the villages list
	 * @param lpMap
	 * @return the JSONArray
	 * @throws JSONException 
	 */
	private List buildVillagesCollectionJson(List<Village> villagesList,
			Map<String, List<LanguagePreferences>> lpMap) {
		List villageCollection = new ArrayList();

		if (!ObjectUtil.isListEmpty(villagesList)) {
			for (Village village : villagesList) {
				Map villageDataList = new HashMap();
				villageDataList.put(TransactionProperties.VILLAGE_CODE, village.getCode());
				villageDataList.put(TransactionProperties.VILLAGE_NAME, village.getName());
				villageDataList.put(TransactionProperties.LANG_LIST, getCollectionJson(lpMap.get(village.getCode())));

				if (initialDownload)
					villageRevisionNo = Math.max(village.getRevisionNo(), villageRevisionNo);
				else {
					if (gmEnable == 1) {
						villageDataList.put(TransactionProperties.PANCHAYAT_CODE, village.getGramPanchayat().getCode());

					} else {

						villageDataList.put(TransactionProperties.CITY_CODE, village.getCity().getCode());

					}

				}
				villageCollection.add(villageDataList);
			}
			if (!initialDownload)
				villageRevisionNo = villagesList.get(0).getRevisionNo();
		}
		return villageCollection;
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
	
	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData) {

	


		Head head = (Head) reqData.get(TransactionProperties.HEAD);

		/** VALIDATING REQUEST DATA **/
		String tenantId = head.getTenantId();

		/** GET REQUEST DATA **/
		LOGGER.info("---------- LocationDownload Start ----------");

		initializeRevisionNumbers();
		ESESystem preferences = utilService.findPrefernceById("1");
		gmEnable = 0;
		if (!StringUtil.isEmpty(preferences)) {
			gmEnable = Integer.valueOf(preferences.getPreferences().get(ESESystem.ENABLE_GRAMPANJAYAT));
		}

		String revisionNoString = (String) reqData.get(TxnEnrollmentProperties.LOCATION_DOWNLOAD_REVISION_NO);
		if (!StringUtil.isEmpty(revisionNoString) && revisionNoString.contains("|")) {
			String[] revisionNoArray = revisionNoString.split("\\|");
			if (revisionNoArray.length == 6) {
				try {
					countryRevisionNo = Long.valueOf(revisionNoArray[0]);
					stateRevisionNo = Long.valueOf(revisionNoArray[1]);
					districtRevisionNo = Long.valueOf(revisionNoArray[2]);
					cityRevisionNo = Long.valueOf(revisionNoArray[3]);
					panchayatRevisionNo = Long.valueOf(revisionNoArray[4]);
					villageRevisionNo = Long.valueOf(revisionNoArray[5]);
				} catch (Exception e) {
					LOGGER.info("Revision No Conversion Error:" + e.getMessage());
					initializeRevisionNumbers();
				}
			}
			if (countryRevisionNo + stateRevisionNo + districtRevisionNo + cityRevisionNo + panchayatRevisionNo
					+ villageRevisionNo > 0)
				initialDownload = false;
		}

		List countryCollection = new ArrayList();
		List stateCollection = new ArrayList();
		List districtCollection = new ArrayList();
		List cityCollection = new ArrayList();
		List panchayatCollection = new ArrayList();
		List villageCollection = new ArrayList();
		List<String> codes = new ArrayList<>();
		Map<String, List<LanguagePreferences>> lpMap = new HashMap<>();
		if (initialDownload) {
			List<Country> countries = utilService.listCountriesWithAll();
			 codes.addAll(countries.stream().map(u -> u.getCode()).collect(Collectors.toList()));
	            codes.addAll(countries.stream().flatMap(u -> u.getStates().stream()).collect(Collectors.toList()).stream().map(pp -> pp.getCode()).collect(Collectors.toList()));
	            codes.addAll(countries.stream().flatMap(u -> u.getStates().stream()).collect(Collectors.toList()).stream().flatMap(u -> u.getLocalities().stream()).collect(Collectors.toList()).stream().map(pp -> pp.getCode()).collect(Collectors.toList()));
	            codes.addAll(countries.stream().flatMap(u -> u.getStates().stream()).collect(Collectors.toList()).stream().flatMap(u -> u.getLocalities().stream()).collect(Collectors.toList()).stream().flatMap(uu -> uu.getMunicipalities().stream()).map(pp -> pp.getCode()).collect(Collectors.toList()));
	            codes.addAll(countries.stream().flatMap(u -> u.getStates().stream()).collect(Collectors.toList()).stream().flatMap(u -> u.getLocalities().stream()).collect(Collectors.toList()).stream().flatMap(uu -> uu.getMunicipalities().stream()).collect(Collectors.toList()).stream().flatMap(uu -> uu.getGramPanchayats().stream()).map(pp -> pp.getCode()).collect(Collectors.toList()));
	            codes.addAll(countries.stream().flatMap(u -> u.getStates().stream()).collect(Collectors.toList()).stream().flatMap(u -> u.getLocalities().stream()).collect(Collectors.toList()).stream().flatMap(uu -> uu.getMunicipalities().stream()).collect(Collectors.toList()).stream().flatMap(uu -> uu.getVillages().stream()).map(pp -> pp.getCode()).collect(Collectors.toList()));
	            codes.addAll(countries.stream().flatMap(u -> u.getStates().stream()).collect(Collectors.toList()).stream().flatMap(u -> u.getLocalities().stream()).collect(Collectors.toList()).stream().flatMap(uu -> uu.getMunicipalities().stream()).collect(Collectors.toList()).stream().flatMap(uu -> uu.getGramPanchayats().stream()).collect(Collectors.toList()).stream().flatMap(uu -> uu.getVillages().stream()).map(pp -> pp.getCode()).collect(Collectors.toList()));
	         
			if (codes != null && !codes.isEmpty()) {
				List<LanguagePreferences> lpLis = utilService.listLangPrefByCodes(codes);
				lpMap = lpLis.stream().collect(Collectors.groupingBy(u -> u.getCode()));
			}
			countryCollection = buildCountriesCollectionJson(countries, tenantId, lpMap);
		} else {
			List<Country> countries = utilService.listCountriesByRevisionNo(countryRevisionNo);

			codes.addAll(countries.stream().map(u -> u.getCode()).collect(Collectors.toList()));
			if (codes != null && !codes.isEmpty()) {
				List<LanguagePreferences> lpLis = utilService.listLangPrefByCodes(codes);

				lpMap = lpLis.stream().collect(Collectors.groupingBy(u -> u.getCode()));
			}
			countryCollection = buildCountriesCollectionJson(countries, tenantId, lpMap);

			List<State> states = utilService.listStatesByRevisionNo(stateRevisionNo);
			codes = new ArrayList<>();
			codes.addAll(states.stream().map(u -> u.getCode()).collect(Collectors.toList()));
			if (codes != null && !codes.isEmpty()) {
				List<LanguagePreferences> lpLis = utilService.listLangPrefByCodes(codes);

				lpMap = lpLis.stream().collect(Collectors.groupingBy(u -> u.getCode()));
			}
			stateCollection = buildSatatesCollectionJson(states, tenantId, lpMap);

			List<Locality> districts = utilService.listLocalitiesByRevisionNo(districtRevisionNo);

			codes = new ArrayList<>();
			codes.addAll(districts.stream().map(u -> u.getCode()).collect(Collectors.toList()));
			if (codes != null && !codes.isEmpty()) {
				List<LanguagePreferences> lpLis = utilService.listLangPrefByCodes(codes);

				lpMap = lpLis.stream().collect(Collectors.groupingBy(u -> u.getCode()));
			}

			districtCollection = buildDistrictsCollectionJson(districts, tenantId, lpMap);

			List<Municipality> cities = utilService.listMunicipalitiesByRevisionNo(cityRevisionNo);

			codes = new ArrayList<>();
			codes.addAll(cities.stream().map(u -> u.getCode()).collect(Collectors.toList()));
			if (codes != null && !codes.isEmpty()) {
				List<LanguagePreferences> lpLis = utilService.listLangPrefByCodes(codes);

				lpMap = lpLis.stream().collect(Collectors.groupingBy(u -> u.getCode()));
			}

			cityCollection = buildCitiesCollectionJson(cities, tenantId, lpMap);

			List<GramPanchayat> panchayats = utilService.listGramPanchayatsByRevisionNo(panchayatRevisionNo);

			codes = new ArrayList<>();
			codes.addAll(panchayats.stream().map(u -> u.getCode()).collect(Collectors.toList()));
			if (codes != null && !codes.isEmpty()) {
				List<LanguagePreferences> lpLis = utilService.listLangPrefByCodes(codes);

				lpMap = lpLis.stream().collect(Collectors.groupingBy(u -> u.getCode()));
			}
			panchayatCollection = buildPanchayatsCollectionJson(panchayats, tenantId, lpMap);

			List<Village> villages = utilService.listVillagesByRevisionNo(villageRevisionNo);
			codes = new ArrayList<>();
			codes.addAll(villages.stream().map(u -> u.getCode()).collect(Collectors.toList()));
			if (codes != null && !codes.isEmpty()) {
				List<LanguagePreferences> lpLis = utilService.listLangPrefByCodes(codes);

				lpMap = lpLis.stream().collect(Collectors.groupingBy(u -> u.getCode()));
			}
			villageCollection = buildVillagesCollectionJson(villages, lpMap);
		}

		revisionNoString = countryRevisionNo + "|" + stateRevisionNo + "|" + districtRevisionNo + "|" + cityRevisionNo
				+ "|" + panchayatRevisionNo + "|" + villageRevisionNo;

		Map resp = new LinkedHashMap();
		resp.put(TransactionProperties.COUNTRY_LIST, countryCollection);
		if (!initialDownload) {
			resp.put(TransactionProperties.STATE_LIST, stateCollection);
			resp.put(TransactionProperties.DISTRICT_LIST, districtCollection);
			resp.put(TransactionProperties.CITY_LIST, cityCollection);
			resp.put(TransactionProperties.GRAM_PANCHAYAT_LIST, panchayatCollection);
			resp.put(TransactionProperties.VILLAGE_LIST, villageCollection);
		}
		resp.put(TxnEnrollmentProperties.LOCATION_DOWNLOAD_REVISION_NO, revisionNoString);

		LOGGER.info("----------LocationDownload End ----------");
		return resp;
	}

}
