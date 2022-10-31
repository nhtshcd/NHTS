/*
 * ProductsDownload.java
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.LanguagePreferences;
import com.sourcetrace.eses.entity.Product;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.property.TxnEnrollmentProperties;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

@Component
public class ProductsDownload implements ITxnAdapter {
    private static final Logger LOGGER = Logger.getLogger(ProductsDownload.class.getName());
	@Autowired
	private IUtilService utilService;
   
	@SuppressWarnings("unchecked")
	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData){

		/** GET REQUEST DATA **/
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		String revisionNo = (String) reqData.get(TxnEnrollmentProperties.PRODUCTS_DOWNLOAD_REVISION_NO);
		
		if (StringUtil.isEmpty(revisionNo) || !StringUtil.isLong(revisionNo)) {
			revisionNo = "0";
		}

		/** FORM RESPONSE DATA **/
		Map resp = new HashMap();
		List<Product> products = utilService.listProductByRevisionNo(Long.valueOf(revisionNo), head.getBranchId());
		List collection = new ArrayList();
		Map<String, List<LanguagePreferences>> lpMap = null;

		if (products != null && !ObjectUtil.isEmpty(products)) {
			List<String> codes = products.stream().map(u -> u.getCode()).collect(Collectors.toList());
			codes.addAll(products.stream().map(u -> u.getSubCategory().getCode()).collect(Collectors.toList()));
			List<LanguagePreferences> lpList = new ArrayList<>();
			if (codes != null && !codes.isEmpty()) {
				lpList = utilService.listLangPrefByCodes(codes);
			}
			if (lpList != null && !ObjectUtil.isEmpty(lpList)) {
				lpMap = lpList.stream().collect(Collectors.groupingBy(LanguagePreferences::getCode));
			}
		}

		if (!ObjectUtil.isListEmpty(products)) {
			for (Product product : products) {
				if (!ObjectUtil.isEmpty(product.getSubCategory())) {
					Map categoryCodeData = new HashMap();
					categoryCodeData.put(TxnEnrollmentProperties.CATEGORY_CODE, product.getSubCategory().getCode());
					categoryCodeData.put(TxnEnrollmentProperties.PRODUCT_CODE, product.getCode());
					categoryCodeData.put(TxnEnrollmentProperties.PRODUCT_NAME, product.getName());
					categoryCodeData.put(TxnEnrollmentProperties.PRICE,
							!StringUtil.isEmpty(product.getPrice()) ? String.valueOf(product.getPrice()) : "");
					categoryCodeData.put(TxnEnrollmentProperties.UNIT,
							product.getUnit() == null || StringUtil.isEmpty(product.getUnit()) ? " "
									: product.getUnit());
					categoryCodeData.put(TxnEnrollmentProperties.MANUFACTURE,
							product.getManufacture() == null || StringUtil.isEmpty(product.getManufacture()) ? ""
									: utilService.findCatalogueByCode(product.getManufacture()).getName());
					categoryCodeData.put(TxnEnrollmentProperties.MANUFACTURE_CODE,
							product.getManufacture() == null || StringUtil.isEmpty(product.getManufacture()) ? ""
									: product.getManufacture());
					categoryCodeData.put(TxnEnrollmentProperties.INGREDIENT,
							product.getIngredient() == null || StringUtil.isEmpty(product.getIngredient()) ? ""
									: product.getIngredient());

					if (products != null && !ObjectUtil.isEmpty(products) && !ObjectUtil.isEmpty(lpMap)) {
						categoryCodeData.put(TransactionProperties.LANG_LIST,
								lpMap.get(product.getCode()));
					}
					if (products != null && !ObjectUtil.isEmpty(products) && !ObjectUtil.isEmpty(lpMap)) {
						categoryCodeData.put(TransactionProperties.LANG_LIST_CATEGORY,
								lpMap.get(product.getSubCategory().getCode()));
					}

					collection.add(categoryCodeData);
				}
			}
		}

		if (!ObjectUtil.isListEmpty(products)) {
			revisionNo = String.valueOf(products.get(0).getRevisionNo());
		}

		// response data
		resp.put(TxnEnrollmentProperties.PRODUCT_LIST, collection);
		resp.put(TxnEnrollmentProperties.PRODUCTS_DOWNLOAD_REVISION_NO, revisionNo);
		return resp;
	}

}
