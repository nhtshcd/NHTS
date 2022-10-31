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

import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.entity.LanguagePreferences;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;;




// TODO: Auto-generated Javadoc
/**
 * The Class SeasonDownload.
 */
@Component
public class CatalogueDownload implements ITxnAdapter {

    private static final Logger LOGGER = Logger.getLogger(CatalogueDownload.class.getName());
    @Autowired
	private IUtilService utilService;
	


	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData) {

        /** HEADER VALUES **/
        Head head = (Head) reqData.get(TransactionProperties.HEAD);

        String revisionNo = (String) reqData
                .get(TransactionProperties.CATALOGUE_DOWNLOAD_REVISION_NO);
        
        if(StringUtil.isEmpty(revisionNo) || !StringUtil.isLong(revisionNo)){
        	revisionNo = "0";
        }
        
        /** REQUEST VALUES **/
        Map resp = new HashedMap();
        List<FarmCatalogue> catalogueList = utilService.listCatalogueByRevisionNo(Long
                .valueOf(revisionNo));// utilService.listSeasons();
       List<String> codes =  catalogueList.stream().map(u -> u.getCode())
				.collect(Collectors.toList());
       List<LanguagePreferences> lpList =  new ArrayList<>();
       if(codes!=null && !codes.isEmpty()){
    	 lpList = utilService.listLangPrefByCodes(codes);
       }
      
       Map<String, List<LanguagePreferences>> lpMap = lpList.stream().collect(Collectors.groupingBy(LanguagePreferences::getCode));
        List catalogues = new ArrayList();
        if (!ObjectUtil.isEmpty(catalogueList)) {
            for (FarmCatalogue cat : catalogueList) {
                Map catalogue = new HashMap();
                catalogue.put(TransactionProperties.CATLOGUE_CODE,cat.getCode());
                catalogue.put(TransactionProperties.CATLOGUE_NAME,cat.getName());
                catalogue.put(TransactionProperties.CATLOGUE_TYPE,cat.getTypez());
                catalogue.put(TransactionProperties.SEQ_NO,cat.getIsReserved());
                catalogue.put(TransactionProperties.PARENT_ID,cat.getParentId()!=null && !StringUtil.isEmpty(cat.getParentId())?cat.getParentId():"");
                catalogue.put(TransactionProperties.LANG_LIST,getCollectionJson(lpMap.get(cat.getCode())));
            
                catalogues.add(catalogue);

            }


        }

        if (!ObjectUtil.isListEmpty(catalogueList)) {
            revisionNo = String.valueOf(catalogueList.get(0).getRevisionNo());
        }

        /** RESPONSE DATA **/
        resp.put(TransactionProperties.CATALOGUES, catalogues);
        resp.put(TransactionProperties.CATALOGUE_DOWNLOAD_REVISION_NO, revisionNo);
        return resp;
    }
	
	 public List getCollectionJson(List<LanguagePreferences> lpListObj) {
	    	List langColl = new ArrayList();
			if (lpListObj!=null && !lpListObj.isEmpty()) {
		for (LanguagePreferences lp : lpListObj) {
					Map langCode = new HashMap();
					langCode.put(TransactionProperties.LANGUAGE_CODE,lp.getLang());
					langCode.put(TransactionProperties.LANGUAGE_NAME,lp.getName());
					langColl.add(langCode);

				}
			} 
			
			return langColl;
		}

    /**
     * Gets the product distribution service.
     * @return the product distribution service
     */
   

}
