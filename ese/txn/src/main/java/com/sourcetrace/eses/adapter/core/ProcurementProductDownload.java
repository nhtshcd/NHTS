/*
 * ProcurementProductDownload.java
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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.LanguagePreferences;
import com.sourcetrace.eses.entity.ProcurementGrade;
import com.sourcetrace.eses.entity.ProcurementProduct;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.property.TxnEnrollmentProperties;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
@Component
public class ProcurementProductDownload implements ITxnAdapter {

    private static Logger LOGGER = Logger.getLogger(ProcurementProductDownload.class);
    @Autowired
	private IUtilService utilService;
    private long procurementProductRevisionNo;
 
    private long varietyRevisionNo;

    private long gradeRevisionNo;

    private boolean initialDownload=true;
    public Map<?, ?> processJson(Map<?, ?> reqData) {
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		LOGGER.info("---------- Procurement Product Download Start ----------");
		String revisionNoString = (String) reqData
				.get(TxnEnrollmentProperties.PROCUREMENT_PRODUCT_DOWNLOAD_REVISION_NO);
		if (!StringUtil.isEmpty(revisionNoString) && revisionNoString.contains("|")) {
			String[] revisionNoArray = revisionNoString.split("\\|");
			if (revisionNoArray.length == 3) {
				try {
					procurementProductRevisionNo = Long.valueOf(revisionNoArray[0]);
					varietyRevisionNo = Long.valueOf(revisionNoArray[1]);
					gradeRevisionNo = Long.valueOf(revisionNoArray[2]);
				} catch (Exception e) {
					LOGGER.info("Revision No Conversion Error:" + e.getMessage());
				}
			}
			if (procurementProductRevisionNo + varietyRevisionNo + gradeRevisionNo > 0)
				initialDownload = false;
		}
		/** FORM RESPONSE DATA **/
		List procurementProductCollection = new ArrayList();
		List varietyCollection = new ArrayList();
		List gradeCollection = new ArrayList();

		if (initialDownload) {
				List<ProcurementProduct> procurementProductList = utilService.listProcurementProduct();
				if(procurementProductList!=null && !ObjectUtil.isEmpty(procurementProductList) && procurementProductList.size()>0){
					procurementProductCollection = buildProcurementProductCollectionJson(procurementProductList, head);
				}

		} else {
			
				List<ProcurementProduct> procurementProductList = utilService
						.listProcurementProductByRevisionNo(Long.valueOf(procurementProductRevisionNo));
				if(procurementProductList!=null && !ObjectUtil.isEmpty(procurementProductList) && procurementProductList.size()>0){
				procurementProductCollection = buildProcurementProductCollectionJson(procurementProductList, head);
				}
				List<ProcurementVariety> varietyList = utilService
						.listProcurementProductVarietyByRevisionNo(Long.valueOf(varietyRevisionNo));
				if(varietyCollection!=null && !ObjectUtil.isEmpty(varietyCollection) && varietyCollection.size()>0){
				varietyCollection = buildVarietyCollectionJson(varietyList, head);
				}
				
			List<ProcurementGrade> gradeList = utilService
					.listProcurementProductGradeByRevisionNo(Long.valueOf(gradeRevisionNo));
			if(gradeCollection!=null && !ObjectUtil.isEmpty(gradeCollection) && gradeCollection.size()>0){
			gradeCollection = buildGradeCollectionJson(gradeList);
			}

		}

		revisionNoString = procurementProductRevisionNo + "|" + varietyRevisionNo + "|" + gradeRevisionNo;

		Map resp = new LinkedHashMap();
		resp.put(TxnEnrollmentProperties.PROCUREMENT_MTNT_PRODUCTS, procurementProductCollection);
		if (!initialDownload) {
			resp.put(TxnEnrollmentProperties.PROCUREMENT_PRODUCT_VARIETY_LIST, varietyCollection);
			resp.put(TxnEnrollmentProperties.PROCUREMENT_PRODUCT_GRADE_LIST, gradeCollection);
		}
		resp.put(TxnEnrollmentProperties.PROCUREMENT_PRODUCT_DOWNLOAD_REVISION_NO, revisionNoString);

		LOGGER.info("----------Procurement Product Download End ----------");
		return resp;
	}

	/**
	 * Build procurement product collection.
	 * 
	 * @param procurementProductList
	 * @return the collection
	 * @throws JSONException
	 */
	private List buildProcurementProductCollectionJson(List<ProcurementProduct> procurementProductList, Head head){

		List procurementProductCollection = new ArrayList();
		Map<String, List<LanguagePreferences>> lpMap = null;

		if (procurementProductList != null && !ObjectUtil.isEmpty(procurementProductList)) {
			List<String> codes = procurementProductList.stream().map(u -> u.getCode()).collect(Collectors.toList());
			List<LanguagePreferences> lpList = new ArrayList<>();
			if (codes != null && !codes.isEmpty()) {
				lpList = utilService.listLangPrefByCodes(codes);
			}
			if (lpList != null && !ObjectUtil.isEmpty(lpList) && lpList.size()>0) {
				lpMap = lpList.stream().collect(Collectors.groupingBy(LanguagePreferences::getCode));
			}
		}

		if (!ObjectUtil.isListEmpty(procurementProductList)) {

			for (ProcurementProduct procurementProduct : procurementProductList) {

				Map procurementProductObj = new HashMap();
				procurementProductObj.put(TxnEnrollmentProperties.PROCUREMENT_PROD_CODE, procurementProduct.getCode());
				procurementProductObj.put(TxnEnrollmentProperties.PRICE_PATTERN_NAME, procurementProduct.getName());
				procurementProductObj.put(TxnEnrollmentProperties.PROCUREMENT_PROD_UNIT,
						!StringUtil.isEmpty(procurementProduct.getUnit()) ? procurementProduct.getUnit() : "");
				procurementProductObj.put(TxnEnrollmentProperties.CROP_TYPE,
						!StringUtil.isEmpty(procurementProduct.getSpeciesName()) ? procurementProduct.getSpeciesName() : "");
			
				if (procurementProductList != null && !ObjectUtil.isEmpty(procurementProductList)
						&& !ObjectUtil.isEmpty(lpMap)) {
					procurementProductObj.put(TransactionProperties.LANG_LIST,
							getCollectionJson(lpMap.get(procurementProduct.getCode())));
				}

				if (initialDownload) {

					procurementProductObj.put(TxnEnrollmentProperties.PROCUREMENT_PRODUCT_VARIETY_LIST,
							buildVarietyCollectionJson(!ObjectUtil.isListEmpty(procurementProduct.getProcurementVarieties())
									? new LinkedList<ProcurementVariety>(procurementProduct.getProcurementVarieties())
									: null, head));
				}
				procurementProductRevisionNo = Math.max(procurementProduct.getRevisionNo(),
						procurementProductRevisionNo);
				procurementProductCollection.add(procurementProductObj);
			}

		}
		if (!initialDownload)
			procurementProductRevisionNo = procurementProductList.get(0).getRevisionNo();

		return procurementProductCollection;
	}

	/**
	 * Build variety collection.
	 * 
	 * @param varietyList
	 * @return the collection
	 * @throws JSONException
	 */
	private List buildVarietyCollectionJson(List<ProcurementVariety> varietyList, Head head){

		Map<String, List<LanguagePreferences>> lpMap = null;

		if (varietyList != null && !ObjectUtil.isEmpty(varietyList)) {
			List<String> codes = varietyList.stream().map(u -> u.getCode()).collect(Collectors.toList());
			List<LanguagePreferences> lpList = new ArrayList<>();
			if (codes != null && !codes.isEmpty()) {
				lpList = utilService.listLangPrefByCodes(codes);
			}
			if (lpList != null && !ObjectUtil.isEmpty(lpList)) {
				lpMap = lpList.stream().collect(Collectors.groupingBy(LanguagePreferences::getCode));
			}
		}

		List varietyCollection = new ArrayList();
		
		if (!ObjectUtil.isListEmpty(varietyList)) {

			for (ProcurementVariety procurementVariety : varietyList) {
				Map varietyDataList = new HashMap();
				varietyDataList.put(TxnEnrollmentProperties.PROCUREMENT_PRODUCT_VARIETY_CODE,
						procurementVariety.getCode());
				varietyDataList.put(TxnEnrollmentProperties.PROCUREMENT_PRODUCT_VARIETY_NAME,
						procurementVariety.getName());
				varietyDataList.put("hsCode",
						procurementVariety.getCropHScode());

				if (varietyList != null && !ObjectUtil.isEmpty(varietyList) && !ObjectUtil.isEmpty(lpMap)) {
					varietyDataList.put(TransactionProperties.LANG_LIST,
							getCollectionJson(lpMap.get(procurementVariety.getCode())));
				}
				if (initialDownload) {

					varietyDataList.put(TxnEnrollmentProperties.PROCUREMENT_PRODUCT_GRADE_LIST,
							buildGradeCollectionJson(!ObjectUtil.isListEmpty(procurementVariety.getProcurementGrades())
									? new LinkedList<ProcurementGrade>(procurementVariety.getProcurementGrades())
									: null));
				}
				varietyCollection.add(varietyDataList);

			}
			if (!initialDownload)
				varietyRevisionNo = varietyList.get(0).getRevisionNo();
		}
		return varietyCollection;
	}

	/**
	 * Build grade collection.
	 * 
	 * @param gradeList
	 * @return the collection
	 * @throws JSONException
	 */
	private List buildGradeCollectionJson(List<ProcurementGrade> gradeList) {

		List gradeCollection = new ArrayList();

		Map<String, List<LanguagePreferences>> lpMap = null;

		if (gradeList != null && !ObjectUtil.isEmpty(gradeList)) {
			List<String> codes = gradeList.stream().map(u -> u.getCode()).collect(Collectors.toList());
			List<LanguagePreferences> lpList = new ArrayList<>();
			if (codes != null && !codes.isEmpty()) {
				lpList = utilService.listLangPrefByCodes(codes);
			}
			if (lpList != null && !ObjectUtil.isEmpty(lpList)) {
				lpMap = lpList.stream().collect(Collectors.groupingBy(LanguagePreferences::getCode));
			}
		}

		if (!ObjectUtil.isListEmpty(gradeList)) {

			for (ProcurementGrade procurementGrade : gradeList) {

				Map gardeObj = new HashMap();
				gardeObj.put(TxnEnrollmentProperties.PROCUREMENT_PRODUCT_GRADE_CODE, procurementGrade.getCode());
				gardeObj.put(TxnEnrollmentProperties.PROCUREMENT_PRODUCT_GRADE_NAME, procurementGrade.getName());
				gardeObj.put(TxnEnrollmentProperties.PROCUREMENT_PRODUCT_GRADE_PRICE,procurementGrade.getPrice()!=null ? String.valueOf(procurementGrade.getPrice()) : "0");
				gardeObj.put("cropCycle", procurementGrade.getCropCycle() == null  ? "" :procurementGrade.getCropCycle() );
				gardeObj.put(TxnEnrollmentProperties.ESTIMATION_HARVEST_DAYS, procurementGrade.getHarvestDays() == null  ? "" :procurementGrade.getHarvestDays() );

				gardeObj.put(TxnEnrollmentProperties.ESTIMATED_ACREAGE, procurementGrade.getYield() == null  ? "" :procurementGrade.getYield() );
				if (gradeList != null && !ObjectUtil.isEmpty(gradeList) && !ObjectUtil.isEmpty(lpMap)) {
					gardeObj.put(TransactionProperties.LANG_LIST, getCollectionJson(lpMap.get(procurementGrade.getCode())));

				}

				if (initialDownload)
					gradeRevisionNo = Math.max(procurementGrade.getRevisionNo(), gradeRevisionNo);
				else {
					gardeObj.put(TxnEnrollmentProperties.PROCUREMENT_PRODUCT_VARIETY_CODE,
							procurementGrade.getProcurementVariety().getCode());

				}
				gradeCollection.add(gardeObj);
			}
			if (!initialDownload)
				gradeRevisionNo = gradeList.get(0).getRevisionNo();
		}
		return gradeCollection;
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
