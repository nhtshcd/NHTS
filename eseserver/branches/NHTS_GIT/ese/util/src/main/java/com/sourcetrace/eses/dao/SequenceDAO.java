/*
 * SequenceDAO.java
 * Copyright (c) 2015-2016, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.dao;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.util.StringUtil;


// TODO: Auto-generated Javadoc
@Repository
@Transactional
public class SequenceDAO extends ESEDAO implements ISequenceDAO {

   
	/**
     * Instantiates a new sequence dao.
     * @param sessionFactory the session factory
     */
    @Autowired
    public SequenceDAO(SessionFactory sessionFactory) {

        this.setSessionFactory(sessionFactory);
    }

    public static final String DEVICE_ID_SEQ = "DEVICE_ID_SEQ";
    public static final String DISTRIBUTION_MTNT_RECEIPT_NO_SEQ = "DISTRIBUTION_MTNT_RECEIPT_NO_SEQ";
    public static final String DISTRIBUTION_MTNR_RECEIPT_NO_SEQ = "DISTRIBUTION_MTNR_RECEIPT_NO_SEQ";
    public static final String DISTRIBUTION_TO_FIELDSTAFF_SEQ = "DISTRIBUTION_TO_FIELDSTAFF_SEQ";
    public static final String CONTRACT_NO_SEQ = "CONTRACT_NO_SEQ";
    public static final String PRICE_PATTERN_CODE_SEQ = "PRICE_PATTERN_CODE_SEQ";
    public static final String PROCUREMENT_RECEIPT_NO_SEQ = "PROCUREMENT_RECEIPT_NO_SEQ";
    public static final String CUSTOMER_SEQ = "CUSTOMER_SEQ";
    public static final String CUSTOMER_PROJECT_SEQ = "CUSTOMER_PROJECT_SEQ";
    public static final String WEB_WAREHOUSE_STOCK_ENTRY_SEQ = "WEB_WAREHOUSE_STOCK_ENTRY_SEQ";
    public static final String VENDOR_ID_SEQ = "VENDOR_ID_SEQ";
    public static final String RESEARCH_STATION_ID_SEQ = "RESEARCH_STATION_ID_SEQ";
    public static final String CUSTOMER_ID_SEQ = "CUSTOMER_ID_SEQ";
    public static final String VENDOR_ACCOUNT_SEQ = "VENDOR_ACCOUNT_SEQ";
    public static final String CUSTOMER_ACCOUNT_SEQ = "CUSTOMER_ACCOUNT_SEQ";
    public static final String GROUP_ID_SEQ = "GROUP_ID_SEQ";
    public static final String CROP_HARVEST_SEQ = "CROP_HARVEST_SEQ";
    private static final String CATALOGUE_TYPE_ID_SEQ = "CATALOGUE_TYPE_SEQ";
    private static final int FARM_ID_LENGTH = 4;
    public static final String PRODUCT_RETURN_FROM_FIELDSTAFF_SEQ = "PRODUCT_RETURN_FROM_FIELDSTAFF_SEQ";
    public static final String OBSERVATION_ID_SEQ="OBSERVATION_ID_SEQ";
    public static final String SURVEY_SECTION_CODE = "SURVEY_SECTION_CODE";
    public static final String PROCUREMENT_TRACEABILITY_RECEIPT_NO_SEQ = "PROCUREMENT_TRACEABILITY_RECEIPT_NO_SEQ";
    private static final String DYN_TXN_ID_SEQ="DYN_TXN_ID_SEQ";
    public static final String FARM_CROPS_MASTER_CODE_SEQ = "FARM_CROPS_MASTER_CODE_SEQ";
    public static final String LOAN_REPAYMENT_RECEIPT_NO_SEQ = "LOAN_REPAYMENT_RECEIPT_NO_SEQ";
    public static final String LOAN_DISTRIBUTION_RECEIPT_NO_SEQ = "LOAN_DISTRIBUTION_RECEIPT_NO_SEQ";
    public static final String LOAN_ACCOUNT_NO_SEQ = "LOAN_ACCOUNT_NO_SEQ";
    public static final String GROWER_ID_SEQ = "GROWER_ID_SEQ";
   
    public static final String DEALER_REG_ID_SEQ="DEALER_REG_ID_SEQ";
    public static final String VARIETY_INCLUSION_SEQ="VARIETY_INCLUSION_SEQ";
    public static final String EXPORTER_SEQ="EXPORTER_SEQ";
    public static final String TRANSEEDCERT_SEQ ="TRANSEEDCERT_SEQ";
    /**
     * Gets the eSE sequence.
     * @param key the key
     * @return the eSE sequence
     */
    private synchronized long getESESequence(String key) {

        long sequence = 0;
        Session session = getSessionFactory().openSession();
        SQLQuery seqQuery = session
                .createSQLQuery("SELECT SEQ_VAL FROM ESE_SEQ WHERE SEQ_KEY = '" + key + "'");
        sequence = ((BigInteger) seqQuery.list().get(0)).longValue();
        long incrementId = sequence + 1;
        session.createSQLQuery(
                "UPDATE ESE_SEQ SET SEQ_VAL = " + (incrementId) + " WHERE  SEQ_KEY = '" + key + "'")
                .executeUpdate();
        session.flush();
        session.close();
        return incrementId;
    }

    /*
     * (non-Javadoc)
     * @see com.sourcetrace.eses.dao.ISequenceDAO#getFarmerWebIdSeq()
     */
    @SuppressWarnings("unchecked")
    @Override
    public String getFarmerWebIdSeq() {

        long selectedSeq = 0;
        long selectedSeqLimit = 0;
        long updatedSeq = 0;
        Session session = getSessionFactory().openSession();
        SQLQuery query = null;

        query = session.createSQLQuery("SELECT WEB_SEQ, WEB_SEQ_LIMIT FROM " + FARMER_ID_SEQ);
        List<Object[]> objectList = query.list();
        selectedSeq = Long.parseLong((String) objectList.get(0)[0]);
        selectedSeqLimit = Long.parseLong((String) (String) objectList.get(0)[1]);
        updatedSeq = selectedSeq + 1;

        if (updatedSeq <= selectedSeqLimit) {
            query = session
                    .createSQLQuery("UPDATE " + FARMER_ID_SEQ + " SET WEB_SEQ = " + updatedSeq);
            int i = query.executeUpdate();
        } else {
            String newFarmerSeqId = getFarmerRemoteIdSeq();
            int newFarmerId = Integer.parseInt(newFarmerSeqId);
            updatedSeq = newFarmerId + 1;
            int newFarmerIdLimit = newFarmerId + FARMER_SEQ_RANGE;
            if (newFarmerIdLimit >= FARMER_ID_MAX_RANGE)
                newFarmerIdLimit = FARMER_ID_MAX_RANGE;

            query = session.createSQLQuery("UPDATE " + FARMER_ID_SEQ + " SET WEB_SEQ = "
                    + updatedSeq + ", WEB_SEQ_LIMIT = " + newFarmerIdLimit);
            int i = query.executeUpdate();
        }
        session.flush();
        session.close();

        String webFarmerId = StringUtil.appendZeroPrefix(String.valueOf(updatedSeq),
                FARMER_ID_LENGTH);
        return webFarmerId;
    }

    /*
     * (non-Javadoc)
     * @see com.sourcetrace.eses.dao.ISequenceDAO#getTxnIdSeq()
     */
    @Override
    public Long getTxnIdSeq() {

        return getESESequence(TXN_ID_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.sourcetrace.eses.dao.ISequenceDAO#getHamletCodeSeq()
     */
    @Override
    public Long getHamletCodeSeq() {

        return getESESequence(HAMLET_CODE_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.sourcetrace.eses.dao.ISequenceDAO#getVillageCodeSeq()
     */
    @Override
    public Long getVillageCodeSeq() {

        return getESESequence(VILLAGE_CODE_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.sourcetrace.eses.dao.ISequenceDAO#getClusterCodeSeq()
     */
    @Override
    public Long getClusterCodeSeq() {

        return getESESequence(CLUSTER_CODE_SEQ);
    }

    @Override
    public Long getTalukCodeSeq() {

        return getESESequence(TALUK_CODE_SEQ);
    }

    @Override
    public Long getDistrictCodeSeq() {

        return getESESequence(DISTRICT_CODE_SEQ);
    }

    @Override
    public Long getStateCodeSeq() {

        return getESESequence(STATE_CODE_SEQ);
    }

    @Override
    public Long getCountryCodeSeq() {

        return getESESequence(COUNTRY_CODE_SEQ);
    }

    @Override
    public Long getRangeCodeSeq() {

        return getESESequence(RANGE_CODE_SEQ);
    }

    @Override
    public long getAgentSequence() {

        return getESESequence(AGENT);
    }

    public long getAgentCardSequence() {

        return getESESequence(AGENT_CARD);
    }

    public long getEnrollmentStationSequence() {

        return getSequence(ENROLLMENT_STATION);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getKioskSequence()
     */
    public long getKioskSequence() {

        return getSequence(KIOSK);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getPOSSequence()
     */
    public long getPOSSequence() {

        return getSequence(POS);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getTellerPOSSequence()
     */
    public long getTellerPOSSequence() {

        return getSequence(TELLER_POS);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getUserSequence()
     */
    public long getUserSequence() {

        return getSequence(USER);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getTxnSequence()
     */
    public long getTxnSequence() {

        return getSequence(TXN);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getClientSequence()
     */
    public long getClientSequence() {

        return getESESequence(CLIENT);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getAffiliateSequence()
     */
    public long getAffiliateSequence() {

        return getSequence(AFFILIATE);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getProviderSequence()
     */
    public long getProviderSequence() {

        return getSequence(PROVIDER);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getServicePointSequence()
     */
    public long getServicePointSequence() {

        return getSequence(SERVICE_POINT);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getStoreSequence()
     */
    public long getStoreSequence() {

        return getSequence(STORE);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getSwitchTxnSequence()
     */
    public long getSwitchTxnSequence() {

        return getSequence(SWITCH_TXN);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getReceiptSequence()
     */
    public long getReceiptSequence() {

        return getSequence(RECEIPT);
    }

    /**
     * Gets the sequence.
     * @param name the name
     * @return the sequence
     */
    private long getSequence(String name) {

        long sequence = 0;
        Session session = getSessionFactory().openSession();

        // getHibernateTemplate().getSessionFactory().getCurrentSession()
        SQLQuery query = session
                .createSQLQuery("UPDATE " + name + " SET ID=LAST_INSERT_ID(ID+1) WHERE ID = ID");
        int i = query.executeUpdate();

        // query =
        // getHibernateTemplate().getSessionFactory().getCurrentSession()
        query = session.createSQLQuery("SELECT LAST_INSERT_ID()");
        sequence = ((BigInteger) query.list().get(0)).longValue();
        session.flush();
        session.close();
        return sequence;
    }

    /**
     * Gets the eSE sequence.
     * @param key the key
     * @return the eSE sequence
     */
    /*
     * private synchronized long getESESequence(String key) { long sequence = 0; // Session session
     * = // getHibernateTemplate().getSessionFactory().getCurrentSession(); Session session =
     * getSessionFactory().openSession(); SQLQuery seqQuery = session
     * .createSQLQuery("SELECT SEQ_VAL FROM ESE_SEQ WHERE SEQ_KEY = '" + key + "'"); sequence =
     * ((BigInteger) seqQuery.list().get(0)).longValue(); long incrementId = sequence + 1;
     * session.createSQLQuery( "UPDATE ESE_SEQ SET SEQ_VAL = " + (incrementId) +
     * " WHERE  SEQ_KEY = '" + key + "'").executeUpdate(); session.flush(); session.close(); return
     * incrementId; }
     */

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getESEAgentAccountSequence()
     */
    public long getESEAgentAccountSequence() {

        return getESESequence(AGENT_ACCT_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getESEAgentCardSequence()
     */
    public long getESEAgentCardSequence() {

        return getESESequence(AGENT_CARD_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getESEClientAccountSequence()
     */
    public long getESEClientAccountSequence() {

        return getESESequence(CLIENT_ACCT_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getESEClientCardSequence()
     */
    public long getESEClientCardSequence() {

        return getESESequence(CLIENT_CARD_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#createBatchNumber()
     */
    public long createBatchNumber() {

        return getESESequence(BATCH_NO_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getClientSequenceforDevice(java.lang .String)
     */
    public String getClientSequenceforDevice(String value) {

        // Session session =
        // getHibernateTemplate().getSessionFactory().getCurrentSession();
        Session session = getSessionFactory().openSession();
        SQLQuery query = session.createSQLQuery(
                "SELECT MAX(PROF_ID) FROM PROF WHERE PROF_ID LIKE '" + value + "%'");
        List list = query.list();
        session.flush();
        session.close();
        if (list.isEmpty() || list.size() <= 0) {
            return null;
        } else {
            return (String) list.get(0);
        }

    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getESEAccountSequenceforDevice(java. lang.String)
     */
    public String getESEAccountSequenceforDevice(String value) {

        // Session session =
        // getHibernateTemplate().getSessionFactory().getCurrentSession();
        Session session = getSessionFactory().openSession();
        SQLQuery query = session.createSQLQuery(
                "SELECT MAX(ACC_NO) FROM ESE_ACCOUNT WHERE ACC_NO LIKE '" + value + "%'");
        List list = query.list();
        session.flush();
        session.close();
        if (list.isEmpty() || list.size() <= 0) {
            return null;
        } else {
            return (String) list.get(0);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getESECardSequenceforDevice(java.lang .String)
     */
    public String getESECardSequenceforDevice(String value) {

        // Session session =
        // getHibernateTemplate().getSessionFactory().getCurrentSession();
        Session session = getSessionFactory().openSession();
        SQLQuery query = session.createSQLQuery(
                "SELECT MAX(CARD_ID) FROM ESE_CARD WHERE CARD_ID LIKE '" + value + "%'");
        List list = query.list();
        session.flush();
        session.close();
        if (list.isEmpty() || list.size() <= 0) {
            return null;
        } else {
            return (String) list.get(0);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#createApprovalCode()
     */
    public long createApprovalCode() {

        return getESESequence(APR_CODE_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getAgentInternalIdSequence()
     */
    public Long getAgentInternalIdSequence() {

        return getESESequence(AGENT_INTERNAL_ID_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getAgentCardIdSequence()
     */
    public Long getAgentCardIdSequence() {

        return getESESequence(AGENT_CARD_ID_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getFarmerCardIdSequence()
     */
    public Long getFarmerCardIdSequence() {

        return getESESequence(FARMER_CARD_ID_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getShopDealerCardIdSequence()
     */
    public Long getShopDealerCardIdSequence() {

        return getESESequence(SHOP_DEALER_CARD_ID_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getAgentAccountNoSequence()
     */
    public Long getAgentAccountNoSequence() {

        return getESESequence(AGENT_ACCT_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getFarmerAccountNoSequence()
     */
    public Long getFarmerAccountNoSequence() {

        return getESESequence(FARMER_ACCOUNT_NO_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getTruckUniqueIdSeq()
     */
    public Long getTruckUniqueIdSeq() {

        return getESESequence(TRUCK_UNIQUE_ID_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getFarmerIdSeq()
     */
    public synchronized String getFarmerRemoteIdSeq() {

        long selectedSeq = 0;
        long updatedSeq = 0;
        Session session = getSessionFactory().openSession();
        SQLQuery query = null;

        query = session.createSQLQuery("SELECT REMOTE_SEQ FROM " + FARMER_ID_SEQ);
        selectedSeq = Long.parseLong((String) query.list().get(0));
        updatedSeq = selectedSeq + Farmer.FARMER_SEQ_RANGE;
        if (updatedSeq >= Farmer.FARMER_ID_MAX_RANGE) {
            updatedSeq = Farmer.FARMER_ID_MAX_RANGE;
        }
        query = session
                .createSQLQuery("UPDATE " + FARMER_ID_SEQ + " SET REMOTE_SEQ = " + updatedSeq);
        int i = query.executeUpdate();

        session.flush();
        session.close();
        String remoteFarmerId = StringUtil.appendZeroPrefix(String.valueOf(selectedSeq),
                Farmer.FARMER_ID_LENGTH);
        return String.valueOf(remoteFarmerId);
    }

   

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getDistributionSeq()
     */
    public Long getDistributionSeq() {

        return getESESequence(DISTRIBUTION_SEQ);
    }

    /**
     * Gets the eSE sequence for distribution.
     * @param key the key
     * @return the eSE sequence for distribution
     */
    private String getESESequenceForDistribution(String key) {

        Session session = getSessionFactory().openSession();
        SQLQuery seqQuery = session.createSQLQuery(
                "SELECT LPAD( SEQ_VAL+1, 4, '0') FROM ESE_SEQ WHERE SEQ_KEY = '" + key + "'");
        String sequence = String.valueOf(seqQuery.list().get(0));
        long incrementId = Long.valueOf(sequence);
        if (incrementId > 9999) {
            incrementId = 0;
        }
        session.createSQLQuery(
                "UPDATE ESE_SEQ SET SEQ_VAL = " + (incrementId) + " WHERE  SEQ_KEY = '" + key + "'")
                .executeUpdate();
        session.flush();
        session.close();
        return sequence;
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getPaymentIdSeq()
     */
    public Long getPaymentIdSeq() {

        return getESESequence(PAYMENT_UNIQUE_ID_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getProcurementChartNoSeq()
     */
    public Long getProcurementChartNoSeq() {

        return getESESequence(PROCUREMENT_CHART_NO_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getMTNTReceiptNoSeq()
     */
    public Long getMTNTReceiptNoSeq() {

        return getESESequence(MTNT_RECEIPT_NO_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getMTNRReceiptNoSeq()
     */
    public Long getMTNRReceiptNoSeq() {

        return getESESequence(MTNR_RECEIPT_NO_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getDistributionMTNTIdSeq()
     */
    public Long getDistributionMTNTIdSeq() {

        return getESESequence(DISTRIBUTION_MTNT_RECEIPT_NO_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getDistributionMTNRIdSeq()
     */
    public Long getDistributionMTNRIdSeq() {

        return getESESequence(DISTRIBUTION_MTNR_RECEIPT_NO_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getDistributionToFieldStaffSeq()
     */
    public Long getDistributionToFieldStaffSeq() {

        return getESESequence(DISTRIBUTION_TO_FIELDSTAFF_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getContractNoSequence()
     */
    public Long getContractNoSequence() {

        return getESESequence(CONTRACT_NO_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getPricePatternCodeSequence()
     */
    public Long getPricePatternCodeSequence() {

        return getESESequence(PRICE_PATTERN_CODE_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getProcurementReceiptNoSeq()
     */
    public Long getProcurementReceiptNoSeq() {

        return getESESequence(PROCUREMENT_RECEIPT_NO_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getCustomerSequence()
     */
    public long getCustomerSequence() {

        return getESESequence(CUSTOMER_ID_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#findProjectCodeSequenceByCustomerId( java.lang.String)
     */
    /*
     * public CustomerProjectSequence findProjectCodeSequenceByCustomerId( String customerId) {
     * return (CustomerProjectSequence) find(
     * "FROM CustomerProjectSequence cps WHERE cps.customerId=?", customerId); }
     */

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getWarehouseStockEntryReceiptNumberSeq()
     */
    public Long getWarehouseStockEntryReceiptNumberSeq() {

        return getESESequence(WEB_WAREHOUSE_STOCK_ENTRY_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getVendorSequence()
     */
    public long getVendorSequence() {

        return getESESequence(VENDOR_ID_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getCustomerAccountNoSequence()
     */
    public Long getCustomerAccountNoSequence() {

        return getESESequence(CUSTOMER_ACCOUNT_SEQ);
    }

    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getVendorAccountNoSequence()
     */
    public Long getVendorAccountNoSequence() {

        return getESESequence(VENDOR_ACCOUNT_SEQ);
    }

    public Long getCountryIdSeq() {

        return getESESequence(COUNTRY_CODE_SEQ);
    }

    public Long getStateIdSeq() {

        return getESESequence(STATE_CODE_SEQ);
    }

    public Long getDistrictIdSeq() {

        return getESESequence(DISTRICT_CODE_SEQ);
    }

    public Long getMandalIdSeq() {

        return getESESequence(MANDAL_ID_SEQ);
    }

    public Long getVillageIdSeq() {

        return getESESequence(VILLAGE_CODE_SEQ);
    }

    public Long getDeviceIdSeq() {

        return getESESequence(DEVICE_ID_SEQ);

    }

    

    public Long getGroupIdSeq() {

        return getESESequence(GROUP_ID_SEQ);
    }


    @Override
    public Long getCatalogueIdSeq() {

        return getESESequence(CATALOGUE_ID_SEQ);
    }

    @Override
    public Long getCropHarvestReceiptNumberSeq() {

        // TODO Auto-generated method stub
        return getESESequence(CROP_HARVEST_SEQ);
    }

    @Override
    public Long getProductEnrollIdSeq() {

        // TODO Auto-generated method stub
        return getESESequence(PRODUCT_ENROLL_ID_SEQ);
    }

   

   

    @Override
    public Long getSubCategoryIdSeq() {

        // TODO Auto-generated method stub
        return getESESequence(FARM_PRODUCT_ID_SEQ);
    }

    @Override
    public Long getProductIdSeq() {

        // TODO Auto-generated method stub
        return getESESequence(PRODUCT_P_ID_SEQ);
    }

    @Override
    public Long getSeasonIdSeq() {

        // TODO Auto-generated method stub
        return getESESequence(SEASON_ID_SEQ);
    }

    @Override
    public Long getTrainingTopicIdSeq() {

        // TODO Auto-generated method stub
        return getESESequence(TRAINING_TOPIC_ID_SEQ);
    }

    @Override
    public Long getTrainingCriteriaCategoryIdSeq() {

        // TODO Auto-generated method stub
        return getESESequence(TRAINING_CRITERIA_CATEGORY_ID_SEQ);
    }

    @Override
    public Long getCriteriaIdSeq() {

        // TODO Auto-generated method stub
        return getESESequence(CRITERIA_ID_SEQ);
    }

    @Override
    public Long getTargetGroupIdSeq() {

        // TODO Auto-generated method stub
        return getESESequence(TARGET_GROUP_ID_SEQ);
    }

    @Override
    public Long getTrainingMethodIdSeq() {

        // TODO Auto-generated method stub
        return getESESequence(TRAINING_METHOD_ID_SEQ);
    }

    @Override
    public Long getOrganizationAccountNoSequence() {

        // TODO Auto-generated method stub
        return getESESequence(ORGANIZATION_ACCOUNT_SEQ);
    }

    @Override
    public Long getGramPanchayatIdSeq() {

        // TODO Auto-generated method stub
        return getESESequence(GRAMPANCHAYAT_CODE_SEQ);
    }

    @Override
    public String getFarmerWebIdSeq(int farmerIdLength, long farmerMaxRange) {

        long selectedSeq = 0;
        long selectedSeqLimit = 0;
        long updatedSeq = 0;
        Session session = getSessionFactory().openSession();
        SQLQuery query = null;

        query = session.createSQLQuery("SELECT WEB_SEQ, WEB_SEQ_LIMIT FROM " + FARMER_ID_SEQ);
        List<Object[]> objectList = query.list();
        selectedSeq = Long.parseLong((String) objectList.get(0)[0]);
        selectedSeqLimit = Long.parseLong((String) (String) objectList.get(0)[1]);
        updatedSeq = selectedSeq + 1;

        if (updatedSeq <= selectedSeqLimit) {
            query = session
                    .createSQLQuery("UPDATE " + FARMER_ID_SEQ + " SET WEB_SEQ = " + updatedSeq);
            int i = query.executeUpdate();
        } else {
            String newFarmerSeqId = getFarmerRemoteIdSeq();
            long newFarmerId = Long.parseLong(newFarmerSeqId);
            updatedSeq = newFarmerId + 1;
            long newFarmerIdLimit = newFarmerId + FARMER_SEQ_RANGE;
            if (newFarmerIdLimit >= farmerMaxRange)
                newFarmerIdLimit = farmerMaxRange;

            query = session.createSQLQuery("UPDATE " + FARMER_ID_SEQ + " SET WEB_SEQ = "
                    + updatedSeq + ", WEB_SEQ_LIMIT = " + newFarmerIdLimit);
            int i = query.executeUpdate();
        }
        session.flush();
        session.close();

        String webFarmerId = StringUtil.appendZeroPrefix(String.valueOf(updatedSeq),
                farmerIdLength);
        return webFarmerId;
    }

    public synchronized String getFarmerRemoteIdSeq(int farmerIdLentth, long farmerMaxRange) {

        long selectedSeq = 0;
        long updatedSeq = 0;
        Session session = getSessionFactory().openSession();
        SQLQuery query = null;

        query = session.createSQLQuery("SELECT REMOTE_SEQ FROM " + FARMER_ID_SEQ);
        selectedSeq = Long.parseLong((String) query.list().get(0));
        updatedSeq = selectedSeq + Farmer.FARMER_SEQ_RANGE;
        if (updatedSeq >= farmerMaxRange) {
            updatedSeq = farmerMaxRange;
        }
        query = session
                .createSQLQuery("UPDATE " + FARMER_ID_SEQ + " SET REMOTE_SEQ = " + updatedSeq);
        int i = query.executeUpdate();

        session.flush();
        session.close();
        String remoteFarmerId = StringUtil.appendZeroPrefix(String.valueOf(selectedSeq),
                farmerIdLentth);
        return String.valueOf(remoteFarmerId);
    }

    @Override
    public Long getCatalougeTypeSeq() {

        return getESESequence(CATALOGUE_TYPE_ID_SEQ);
    }

    @Override
    public String getGroupHHIdSeq(String code) {

        Session session = getSessionFactory().openSession();
        Query query = session.createQuery("SELECT MAX(code) FROM Packhouse WHERE code LIKE :code");
        query.setParameter("code", code + "%");
        List<String> codeList = query.list();
        String codeVal = null;
        if (codeList.size() > 0) {
            codeVal = codeList.get(0);
        }
        session.flush();
        session.close();
        return codeVal;
    }

    @Override
    public String getFarmerHHIdSeq(String code) {

        Session session = getSessionFactory().openSession();
        Query query = session.createQuery(
                "SELECT MAX(farmerCode) FROM Farmer WHERE farmerCode LIKE :farmerCode");
        query.setParameter("farmerCode", code + "%");
        List<String> codeList = query.list();
        String codeVal = null;
        if (codeList.size() > 0) {
            codeVal = codeList.get(0);
        }
        session.flush();
        session.close();
        return codeVal;
    }

    @Override
    public Long getDistrictHHIdSeq() {

        Session session = getSessionFactory().openSession();
        Query query = session.createQuery("SELECT MAX(code) FROM Locality");
        List<String> codeList = query.list();
        Long codeVal = null;
        if (codeList.size() > 0) {
            codeVal = Long.parseLong(codeList.get(0));
        }
        session.flush();
        session.close();
        return codeVal;
    }

    @Override
    public String getMandalHHId(String code) {

        Session session = getSessionFactory().openSession();
        Query query = session
                .createQuery("SELECT MAX(code) FROM Municipality WHERE code LIKE :distCode");
        query.setParameter("distCode", code + "%");
        List<String> codeList = query.list();
        String codeVal = null;
        if (codeList.size() > 0) {
            codeVal = codeList.get(0);
        }
        session.flush();
        session.close();
        return codeVal;
    }

    @Override
    public String getGramPanchayathHHId(String cityCode) {

        Session session = getSessionFactory().openSession();
        Query query = session
                .createQuery("SELECT MAX(code) FROM GramPanchayat WHERE code LIKE :cityCode");
        query.setParameter("cityCode", cityCode + "%");
        List<String> codeList = query.list();
        String codeVal = null;
        if (codeList.size() > 0) {
            codeVal = codeList.get(0);
        }
        session.flush();
        session.close();
        return codeVal;
    }

    @Override
    public String getVillageHHIdSeq(String code) {

        // TODO Auto-generated method stub
        Session session = getSessionFactory().openSession();
        Query query = session
                .createQuery("SELECT MAX(code) FROM Village WHERE code LIKE :pancyatCode");
        query.setParameter("pancyatCode", code + "%");
        List<String> codeList = query.list();
        String codeVal = null;
        if (codeList.size() > 0) {
            codeVal = codeList.get(0);
        }
        session.flush();
        session.close();
        return codeVal;
    }

    @Override
    public String getFarmWebIdSeq() {

        long selectedSeq = 0;
        long selectedSeqLimit = 0;
        long updatedSeq = 0;
        Session session = getSessionFactory().openSession();
        SQLQuery query = null;

        query = session.createSQLQuery("SELECT WEB_SEQ, WEB_SEQ_LIMIT FROM " + FARM_ID_SEQ);
        List<Object[]> objectList = query.list();
        selectedSeq = Long.parseLong((String) objectList.get(0)[0]);
        selectedSeqLimit = Long.parseLong((String) (String) objectList.get(0)[1]);
        updatedSeq = selectedSeq + 1;

        if (updatedSeq <= selectedSeqLimit) {
            query = session
                    .createSQLQuery("UPDATE " + FARM_ID_SEQ + " SET WEB_SEQ = " + updatedSeq);
            int i = query.executeUpdate();
        } else {
            String newFarmerSeqId = getFarmerRemoteIdSeq();
            int newFarmerId = Integer.parseInt(newFarmerSeqId);
            updatedSeq = newFarmerId + 1;
            int newFarmerIdLimit = newFarmerId + FARMER_SEQ_RANGE;
            if (newFarmerIdLimit >= FARMER_ID_MAX_RANGE)
                newFarmerIdLimit = FARMER_ID_MAX_RANGE;

            query = session.createSQLQuery("UPDATE " + FARM_ID_SEQ + " SET WEB_SEQ = " + updatedSeq
                    + ", WEB_SEQ_LIMIT = " + newFarmerIdLimit);
            int i = query.executeUpdate();
        }
        session.flush();
        session.close();

        String webFarmId = String.valueOf(updatedSeq);// StringUtil.appendZeroPrefix(String.valueOf(updatedSeq),
                                                      // FARM_ID_LENGTH);
        return webFarmId;
    }

    @Override
    public String getFarmerWebCodeIdSeq(String city,String gp) {

        Session session = getSessionFactory().openSession();
        Query query = session.createQuery(
                "SELECT MAX(farmerCode) FROM Farmer WHERE farmerCode LIKE :farmerCode");
        query.setParameter("farmerCode", (city.trim()+gp.trim()) + "%");
        List<String> codeList = query.list();
        String codeVal = null;
        if (codeList.size() > 0) {
            codeVal = codeList.get(0);
        }
        session.flush();
        session.close();
        return codeVal;
    }

    @Override
    public String getFarmWebCodeIdSeq(String city, String gp) {
        Session session = getSessionFactory().openSession();
        Query query = session.createQuery(
                "SELECT MAX(farmId) FROM Farm WHERE farmId LIKE :farmId");
        query.setParameter("farmId", (city.trim()+gp.trim()) + "%");
        List<String> codeList = query.list();
        String codeVal = null;
        if (codeList.size() > 0) {
            codeVal = codeList.get(0);
        }
        session.flush();
        session.close();
        return codeVal;
    }
    
    public long getResearchStationSequence() {

        return getESESequence(RESEARCH_STATION_ID_SEQ);
    }
    /*
     * (non-Javadoc)
     * @see com.ese.dao.profile.ISequenceDAO#getProductReturnFromFieldStaffSeq()
     */
    public Long getProductReturnFromFieldStaffSeq() {

        return getESESequence(DISTRIBUTION_TO_FIELDSTAFF_SEQ);
    }

	@Override
	public Long getSeedTransferReceiptNoSeq() {
		// TODO Auto-generated method stub
		return getESESequence(SEED_TRANSFER_SEQ);
	}
	
	@Override
	public Long getObservationsSeqId() {
		// TODO Auto-generated method stub
		 return getESESequence(OBSERVATION_ID_SEQ);
	}

	@Override
	public Long getSurveySectionCodeSeq() {
		return getESESequence(SURVEY_SECTION_CODE);	
		}

	@Override
	public synchronized String getFarmerWebIdSeqWithBranch(int farmerIdLength,String branch) {
		   long selectedSeq = 0;
	        long updatedSeq = 0;
	       long selectedSeqLimit=0;
	       long remoteSeq = 0;
	       long remoteSeqLimit=0;
	       Session session = getSessionFactory().openSession();
	       try
	       {
	       
	        SQLQuery query = null;

	        query = session.createSQLQuery("SELECT WEB_SEQ,WEB_ID_LIMIT,REMOTE_SEQ,SEQ_LIMIT FROM BRANCH_MASTER WHERE BRANCH_ID = '" + branch+"'");
	        List<Object[]> objectList = query.list();
	        selectedSeq = Long.parseLong((String) objectList.get(0)[0]);
	        selectedSeqLimit = Long.parseLong((String) (String) objectList.get(0)[1]);
	        remoteSeq = Long.parseLong((String) objectList.get(0)[2]);
	        remoteSeqLimit = Long.parseLong((String) (String) objectList.get(0)[3]);

	        updatedSeq = selectedSeq + 1;
	        if (updatedSeq >= selectedSeqLimit) {
	            updatedSeq =remoteSeq+1;
	            selectedSeqLimit =remoteSeq+1000; 
	            remoteSeq = selectedSeqLimit;
	            query = session
		                .createSQLQuery("UPDATE  BRANCH_MASTER SET REMOTE_SEQ = " + remoteSeq+", WEB_SEQ =  "+updatedSeq+", WEB_ID_LIMIT = "+selectedSeqLimit+" where branch_id='"+branch+"'");
		        int i = query.executeUpdate();
	        }else{
	        query = session
	                .createSQLQuery("UPDATE  BRANCH_MASTER SET WEB_SEQ = " + updatedSeq +" where branch_id='"+branch+"'");
	        int i = query.executeUpdate();
	        } 
	        return String.valueOf(updatedSeq);
	        
	       }
	       catch (Exception e) {
			e.printStackTrace();
		}
	       finally
	       {
	        session.flush();
	        session.close();
	       }
	     /*   String remoteFarmerId = StringUtil.appendZeroPrefix(String.valueOf(selectedSeq),
	                Farmer.FARMER_ID_LENGTH);*/
		return null;
	       
	}

	@Override
	public synchronized String  getFarmerRemoteIdSeqWithBranch(int farmerIdLength,String branch) {
	    long selectedSeq = 0;
        long updatedSeq = 0;
       long selectedSeqLimit=0;
        Session session = getSessionFactory().openSession();
        SQLQuery query = null;

        query = session.createSQLQuery("SELECT REMOTE_SEQ,SEQ_LIMIT FROM BRANCH_MASTER WHERE BRANCH_ID = '" + branch+"'");
        List<Object[]> objectList = query.list();
        selectedSeq = Long.parseLong((String) objectList.get(0)[0]);
        selectedSeqLimit = Long.parseLong((String) (String) objectList.get(0)[1]);

        updatedSeq = selectedSeq + Farmer.FARMER_SEQ_RANGE;
        if (updatedSeq >= selectedSeqLimit) {
            updatedSeq =selectedSeqLimit;
        }
        query = session
                .createSQLQuery("UPDATE  BRANCH_MASTER SET REMOTE_SEQ = " + updatedSeq+" where branch_id='"+branch+"'");
        int i = query.executeUpdate();

        session.flush();
        session.close();
     /*   String remoteFarmerId = StringUtil.appendZeroPrefix(String.valueOf(selectedSeq),
                Farmer.FARMER_ID_LENGTH);*/
        return String.valueOf(selectedSeq);
	}

	@Override
	public Long getProcurementTraceabilityReceiptNoSeq() {
		return getESESequence(PROCUREMENT_TRACEABILITY_RECEIPT_NO_SEQ);
	}

	@Override
	public String getDynmaicTxnType() {
		return String.valueOf(getESESequence(DYN_TXN_ID_SEQ) );
	}
	
	

	@Override
	public Long getDistributionStockSeq() {
		return getESESequence(DISTRIBUTION_STOCK_SEQ);
	}
	
	
	public Long getGroupHHIdSeqFromBranchMasterWebSeq(String branch){
		/*return (Long) find(
                "select b.webSeq FROM BranchMaster b WHERE b.branchId=?", branch);*/
		
		  long sequence = 0;
	        Session session = getSessionFactory().openSession();
	        SQLQuery seqQuery = session
	                .createSQLQuery("SELECT WEB_SEQ FROM BRANCH_MASTER WHERE BRANCH_ID = '" + branch + "'");
	        sequence = (Long.valueOf((String) seqQuery.list().get(0)));
	        long incrementId = sequence + 1;
	        session.createSQLQuery(
	                "UPDATE BRANCH_MASTER SET WEB_SEQ = " + (incrementId) + " WHERE  BRANCH_ID = '" + branch + "'")
	                .executeUpdate();
	        session.flush();
	        session.close();
	        return sequence;
	}

	@Override
	public Long getColdStorageStockTransferReceiptNoSeq() {
		// TODO Auto-generated method stub
		  return getESESequence(COLD_STORAGE_STOCK_TRANSFER_RECEIPT_NO_SEQ);
	}
	
	public Long getDynamicMenuCodeSeq() {
        return getESESequence(DYNAMIC_MENU_CODE_SEQ);
    }
	
	public Long getDynamicSectionCodeSeq() {
        return getESESequence(DYNAMIC_SECTION_CODE_SEQ);
    }
	
	public Long getDynamicFieldCodeSeq() {
        return getESESequence(DYNAMIC_FIELD_CODE_SEQ);
    }
	
	@Override
	public Long getFarmerCodeSeq() {
		// TODO Auto-generated method stub
        return getESESequence(FARMER_CODE_SEQ);
	}
	
	@Override
	public Long getLoanDistributionReceiptNoSeq() {

        return getESESequence(LOAN_DISTRIBUTION_RECEIPT_NO_SEQ);
    }

	@Override
	public Long getLoanAccountNoSeq() {
		return getESESequence(LOAN_ACCOUNT_NO_SEQ);
	}
	
	@Override
	public Long getLoanRepaymentReceiptNoSeq() {

        return getESESequence(LOAN_REPAYMENT_RECEIPT_NO_SEQ);
    }
	
	
	
	@SuppressWarnings("unchecked")
    @Override
    public String getFarmerIdSeq() {

        long selectedSeq = 0;
        selectedSeq = getESESequence(FARMER_ID_SEQ);
        String webFarmerId = StringUtil.appendZeroPrefix(String.valueOf(selectedSeq),
        		FARMER_ID_LENGTH);
        return webFarmerId;
    }
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String getFarmIdSeq() {
		
		long selectedSeq = 0;
		selectedSeq = getESESequence(FARM_ID_SEQ);
		String webFarmerId = StringUtil.appendZeroPrefix(String.valueOf(selectedSeq),
				FARM_TASK_LENGTH);
		return webFarmerId;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String getFarmCropIdSeq() {
		
		long selectedSeq = 0;
		selectedSeq = getESESequence(FarmCrop_ID_SEQ);
		String webFarmerId = StringUtil.appendZeroPrefix(String.valueOf(selectedSeq),
				FARMCROP_TASK_LENGTH);
		return webFarmerId;
	}
	
	
	
	@SuppressWarnings("unchecked")
    @Override
    public String getFarmerTaskIdSeq() {

        long selectedSeq = 0;
        long selectedSeqLimit = 0;
        long updatedSeq = 0;
        Session session = getSessionFactory().openSession();
        SQLQuery query = null;

        query = session.createSQLQuery("SELECT WEB_SEQ, WEB_SEQ_LIMIT FROM " + FARMER_ID_SEQ);
        List<Object[]> objectList = query.list();
        selectedSeq = Long.parseLong((String) objectList.get(0)[0]);
        selectedSeqLimit = Long.parseLong((String) (String) objectList.get(0)[1]);
        updatedSeq = selectedSeq + 1;

        if (updatedSeq <= selectedSeqLimit) {
            query = session
                    .createSQLQuery("UPDATE " + FARMER_ID_SEQ + " SET WEB_SEQ = " + updatedSeq);
            int i = query.executeUpdate();
        } else {
            String newFarmerSeqId = getFarmerRemoteIdSeq();
            int newFarmerId = Integer.parseInt(newFarmerSeqId);
            updatedSeq = newFarmerId + 1;
            int newFarmerIdLimit = newFarmerId + FARMER_SEQ_RANGE;
            if (newFarmerIdLimit >= FARMER_ID_MAX_RANGE)
                newFarmerIdLimit = FARMER_ID_MAX_RANGE;

            query = session.createSQLQuery("UPDATE " + FARMER_ID_SEQ + " SET WEB_SEQ = "
                    + updatedSeq + ", WEB_SEQ_LIMIT = " + newFarmerIdLimit);
            int i = query.executeUpdate();
        }
        session.flush();
        session.close();

        String webFarmerId = StringUtil.appendZeroPrefix(String.valueOf(updatedSeq),
        		FARMER_TASK_LENGTH);
        return webFarmerId;
    }

	@Override
	public Long getPermitIdSeq() {
		// TODO Auto-generated method stub
		return getESESequence(PERMIT_APPLN_ID);
	}
	
	
	

	@Override
	public Long getDealerIdSeq(String seqName) {
		// TODO Auto-generated method stub
		return getESESequence(seqName);  
	}

	

	

	@Override
	public Long getExproterIdSeq() {
		// TODO Auto-generated method stub
		return getESESequence(EXPORTER_SEQ); 
	}@Override
	public Long getTransSeedCertIdSeq() {
		// TODO Auto-generated method stub
		return getESESequence(TRANSEEDCERT_SEQ); 
	}
	

	@Override
	public Long getFarmerIdSequ(String string) {
		// TODO Auto-generated method stub
		return getESESequence(string); 
	}

	
	@Override
	public Long getHarvestIdSeq() {
		// TODO Auto-generated method stub
		return getESESequence(PERMIT_APPLN_ID);
	}

	@Override
	public Long getWarehouseIdSeq() {
		// TODO Auto-generated method stub
        return getESESequence(WAREHOUSE_ID_SEQ);
	}

	@Override
	public Long getProcurementVarietyIdSeq() {
		// TODO Auto-generated method stub
		 return getESESequence(GRADE_ID_SEQ);
	}

	@Override
	public Long getProcurementGradeIdSeq() {
		// TODO Auto-generated method stub
		return getESESequence(GRADE_ID_SEQ);
	}

	
}