package com.sourcetrace.eses.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sourcetrace.eses.entity.AccessLog;
import com.sourcetrace.eses.entity.Action;
import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.AgentAccessLog;
import com.sourcetrace.eses.entity.AgentAccessLogDetail;
import com.sourcetrace.eses.entity.AgentType;
import com.sourcetrace.eses.entity.AgroTransaction;
import com.sourcetrace.eses.entity.Asset;
import com.sourcetrace.eses.entity.BankInfo;
import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.CityWarehouse;
import com.sourcetrace.eses.entity.CityWarehouseDetail;
import com.sourcetrace.eses.entity.Country;
import com.sourcetrace.eses.entity.Customer;
import com.sourcetrace.eses.entity.DeploymentLog;
import com.sourcetrace.eses.entity.Device;
import com.sourcetrace.eses.entity.Distribution;
import com.sourcetrace.eses.entity.DocumentUpload;
import com.sourcetrace.eses.entity.DynamicMenuFieldMap;
import com.sourcetrace.eses.entity.DynamicReportConfig;
import com.sourcetrace.eses.entity.ESEAccount;
import com.sourcetrace.eses.entity.ESECard;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.ESETxnHeader;
import com.sourcetrace.eses.entity.ESETxnStatus;
import com.sourcetrace.eses.entity.Entitlement;
import com.sourcetrace.eses.entity.ExporterRegistration;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.entity.FarmCatalogueMaster;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.FarmerDynamicFieldsValue;
import com.sourcetrace.eses.entity.GramPanchayat;
import com.sourcetrace.eses.entity.Harvest;
import com.sourcetrace.eses.entity.HarvestSeason;
import com.sourcetrace.eses.entity.IdentityType;
import com.sourcetrace.eses.entity.Language;
import com.sourcetrace.eses.entity.LanguagePreferences;
import com.sourcetrace.eses.entity.LocaleProperty;
import com.sourcetrace.eses.entity.Locality;
import com.sourcetrace.eses.entity.Menu;
import com.sourcetrace.eses.entity.Municipality;
import com.sourcetrace.eses.entity.Pmt;
import com.sourcetrace.eses.entity.PmtDetail;
import com.sourcetrace.eses.entity.Procurement;
import com.sourcetrace.eses.entity.ProcurementGrade;
import com.sourcetrace.eses.entity.ProcurementProduct;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.entity.Product;
import com.sourcetrace.eses.entity.Profile;
import com.sourcetrace.eses.entity.Role;
import com.sourcetrace.eses.entity.Scouting;
import com.sourcetrace.eses.entity.SeasonMaster;
import com.sourcetrace.eses.entity.ServicePoint;
import com.sourcetrace.eses.entity.Shipment;
import com.sourcetrace.eses.entity.ShipmentDetails;
import com.sourcetrace.eses.entity.SprayAndFieldManagement;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.entity.SubCategory;
import com.sourcetrace.eses.entity.Transaction;
import com.sourcetrace.eses.entity.TransactionLog;
import com.sourcetrace.eses.entity.TransactionType;
import com.sourcetrace.eses.entity.UptimeLog;
import com.sourcetrace.eses.entity.User;
import com.sourcetrace.eses.entity.Vendor;
import com.sourcetrace.eses.entity.Village;
import com.sourcetrace.eses.entity.VillageWarehouse;
import com.sourcetrace.eses.entity.Packhouse;
import com.sourcetrace.eses.entity.PackhouseIncoming;
import com.sourcetrace.eses.entity.Pcbp;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.WarehouseProductDetail;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

@Repository
@Transactional
public class UtilDAO extends ESEDAO implements IUtilDAO {
	@Autowired
	public UtilDAO(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	@Override
	public void save(Object user) {
		super.save(user);

	}

	@Override
	public void saveOrUpdate(Object user) {
		super.saveOrUpdate(user);

	}

	@Override
	public Device findDeviceBySerialNumber(String serialNumber) {
		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Query query = session.createQuery("From Device where serialNumber=:serialNo and deleted=false");
		query.setParameter("serialNo", serialNumber);
		List list = query.list();
		Device device = null;
		if (list.size() > 0) {
			device = (Device) query.list().get(0);
		}
		session.flush();
		session.close();
		return device;
	}

	@Override
	public Agent findAgentByProfileAndBranchId(String agentID, String branchID) {
		Session session = getSessionFactory().openSession();
		org.hibernate.Filter branchFilter = session.getEnabledFilter(ISecurityFilter.BRANCH_FILTER);
		if (!ObjectUtil.isEmpty(branchFilter)) {
			session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		}
		Query query = session.createQuery("From Agent agent where agent.profileId=:agentId");
		query.setParameter("agentId", agentID);
		List<Agent> aglis =query.list();
		Agent agent = aglis!=null && aglis.size()>0 ? (Agent) query.list().get(0) :null;
		session.flush();
		session.close();
		return agent;
	}

	@Override
	public BranchMaster findBranchMasterByBranchId(String branchId) {

		BranchMaster branchMaster = (BranchMaster) find("FROM BranchMaster bm WHERE bm.branchId = ?", branchId);
		return branchMaster;
	}

	@Override
	public ESESystem findPrefernceByOrganisationId(String id) {
		ESESystem sys = (ESESystem) find("FROM ESESystem es WHERE es.name = ?", id);
		return sys;
	}

	public ESEAccount findAccountByProfileIdAndProfileType(String profileId, int type) {

		Object[] values = { profileId, type };
		return (ESEAccount) find("FROM ESEAccount esea WHERE esea.profileId = ? AND esea.type = ? ", values);
	}

	@Override
	public String findCurrentSeasonCodeByBranchId(String branchId) {
		String currentBranch = ObjectUtil.isEmpty(branchId) ? ESESystem.SYSTEM_ESE_NAME : branchId.toString();
		ESESystem ese = findPrefernceByOrganisationId(currentBranch);
		String currentSeasonCode = "";
		if (!ObjectUtil.isEmpty(ese)) {
			ESESystem preference = findPrefernceById(String.valueOf(ese.getId()));
			currentSeasonCode = preference.getPreferences().get(ESESystem.CURRENT_SEASON_CODE);

			if (StringUtil.isEmpty(currentSeasonCode)) {
				currentSeasonCode = "";
			}
		}
		return currentSeasonCode;

	}

	@Override
	public ESESystem findPrefernceById(String id) {
		ESESystem sys = (ESESystem) find("FROM ESESystem es WHERE es.id = ?", Long.valueOf(id));
		return sys;
	}

	@Override
	public Long findCustomerLatestRevisionNo() {

		List list = list("Select Max(c.revisionNo) FROM Customer c");
		if (!ObjectUtil.isListEmpty(list) && !ObjectUtil.isEmpty(list.get(0)))
			return (Long) list.get(0);
		return 0l;
	}

	@Override
	public void updateAgentBODStatus(String profileId, int status) {

		String queryString = "UPDATE agent_prof Inner Join prof ON agent_prof.PROF_ID = prof.ID SET agent_prof.BOD_STATUS = '"
				+ status + "' WHERE prof.PROF_ID =  '" + profileId + "'";
		Session sessions = getSessionFactory().openSession();
		Query querys = sessions.createSQLQuery(queryString);
		int results = querys.executeUpdate();
		sessions.flush();
		sessions.close();
	}

	@Override
	public Long findTopicCategoryLatestRevisionNo() {

		List<Long> revList = list("SELECT MAX(tc.revisionNo) FROM TopicCategory tc");
		if (!ObjectUtil.isListEmpty(revList) && !ObjectUtil.isEmpty(revList.get(0))) {
			return revList.get(0);
		}
		return 0l;
	}

	@Override
	public Long findTopicLatestRevisionNo() {

		List<Long> revList = list("SELECT MAX(tp.revisionNo) FROM Topic tp");
		if (!ObjectUtil.isListEmpty(revList) && !ObjectUtil.isEmpty(revList.get(0))) {
			return revList.get(0);
		}
		return 0l;
	}

	@Override
	public Long findFarmerTrainingLatestRevisionNo() {

		List<Long> revList = list("SELECT MAX(ft.revisionNo) FROM FarmerTraining ft WHERE ft.status = 1");
		if (!ObjectUtil.isListEmpty(revList) && !ObjectUtil.isEmpty(revList.get(0))) {
			return revList.get(0);
		}
		return 0l;
	}

	@Override
	public List<Object[]> findAgroPrefernceDetailById(String id) {
		String queryString = "SELECT * FROM eses_agro.pref es WHERE es.ese_id ='" + id + "' ";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery(queryString);
		List<Object[]> list = query.list();
		sessions.flush();
		sessions.close();
		return list;
	}

	@Override
	public String findPrefernceByName(String enableMultiProduct) {

		String queryString = "SELECT VAL FROM pref WHERE pref.NAME ='" + enableMultiProduct + "' ";
		System.out.println("Inside utildao at 192 queryString==>" + queryString);
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery(queryString);
		List<Object> list = query.list();
		sessions.flush();
		sessions.close();
		if (!ObjectUtil.isListEmpty(list))
			return (String) list.get(0);
		return null;
	}

	@Override
	public List<FarmCatalogue> listCatalogueByRevisionNo(Long revNo) {
		Object[] values = { revNo, FarmCatalogue.ACTIVE };
		return list("FROM FarmCatalogue sn WHERE sn.revisionNo>? AND sn.status=? ORDER BY sn.revisionNo DESC", values);
	}

	@Override
	public List<LanguagePreferences> listLangPrefByCodes(List<String> codes) {

		Session session = getSessionFactory().openSession();
		Query query = session.createQuery("FROM LanguagePreferences lp WHERE lp.code in (:codes)");
		query.setParameterList("codes", codes);
		List<LanguagePreferences> questions = query.list();
		session.flush();
		session.close();
		return questions;

	}

	@Override
	public List<Customer> listCustomerByRevNo(Long revNo) {
		return list("FROM Customer cust WHERE cust.revisionNo > ? ORDER BY cust.revisionNo DESC", revNo);

	}

	@Override
	public List<DynamicMenuFieldMap> listDynamisMenubyscoreType() {
		return list(
				"select distinct dfm FROM DynamicMenuFieldMap dfm join fetch dfm.field ff  left join fetch ff.languagePreferences   left join fetch ff.dynamicSectionConfig ffs  where dfm.menu.isScore in (2,3) and ff.followUp in (3,4) order by dfm.menu.mTxnType,dfm.order ");

	}

	@Override
	public Agent findAgentByProfileId(String agentId) {
		Agent agent = (Agent) find("from Agent agent where agent.profileId = ?", agentId);
		return agent;
	}

	@Override
	public List<Object[]> listDistributionBalanceDownload(Long id, String strevisionNo) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(
				"select f.farmerId,db.product.subCategory.code,db.product.code,db.stock,db.revisionNo,f.proofNo from DistributionBalance db inner join db.farmer f WHERE f.samithi.id in (SELECT s.id FROM Agent a INNER JOIN a.wareHouses s WHERE a.id=:id) AND  db.revisionNo > :revisionNo   AND f.statusCode in  (:status1)  order by db.revisionNo DESC");
		query.setParameter("id", id);
		query.setParameter("revisionNo", Long.valueOf(strevisionNo));
		query.setParameterList("status1",
				new Object[] { ESETxnStatus.SUCCESS.ordinal(), ESETxnStatus.DELETED.ordinal() });
		List<Object[]> result = query.list();

		return result;
	}

	@Override
	public List<Country> listCountriesWithAll() {
		return list(
				"select distinct c FROM Country c left join fetch c.states s left join fetch s.localities ld left join fetch ld.municipalities m left join fetch m.villages left join fetch m.gramPanchayats gm  left join fetch gm.villages ORDER BY c.name ASC");

	}

	@Override
	public List<Country> listCountriesByRevisionNo(long countryRevisionNo) {
		return list("From Country c left join fetch c.states WHERE c.revisionNo>? ORDER BY c.revisionNo DESC",
				countryRevisionNo);

	}

	public List<State> listStatesByRevisionNo(long revisionNo) {

		return list(
				"From State s left join fetch s.localities join fetch s.country WHERE s.revisionNo>? ORDER BY s.revisionNo DESC",
				revisionNo);
	}

	@SuppressWarnings("unchecked")
	public List<Locality> listLocalitiesByRevisionNo(long revisionNo) {

		return list(
				"From Locality l left join fetch l.municipalities WHERE l.revisionNo>? GROUP BY l.id ORDER BY l.revisionNo DESC",
				revisionNo);
	}

	@SuppressWarnings("unchecked")
	public List<Municipality> listMunicipalitiesByRevisionNo(long revisionNo) {

		return list(
				"From Municipality m left join fetch m.gramPanchayats left join fetch m.villages  WHERE m.revisionNo>? ORDER BY m.revisionNo DESC",
				revisionNo);
	}

	@SuppressWarnings("unchecked")
	public List<GramPanchayat> listGramPanchayatsByRevisionNo(long revisionNo) {

		return list(
				"From GramPanchayat gp left join fetch gp.villages WHERE gp.revisionNo>? ORDER BY gp.revisionNo DESC",
				revisionNo);
	}

	@SuppressWarnings("unchecked")
	public List<Village> listVillagesByRevisionNo(long revisionNo) {

		return list("From Village v left join fetch v.city c WHERE v.revisionNo>? ORDER BY v.revisionNo DESC",
				revisionNo);
	}

	@Override
	public List<Product> listProductByRevisionNo(Long revisionNo, String branchId) {
		Object[] values = { revisionNo };
		return list("FROM Product p left join fetch p.subCategory WHERE p.revisionNo>? ORDER BY p.revisionNo DESC",
				values);

	}

	@Override
	public FarmCatalogue findCatalogueByCode(String code) {
		return (FarmCatalogue) find("FROM FarmCatalogue cg WHERE cg.code = ?", code);

	}

	@Override
	public List<Object[]> listSupplierProcurementDetailProperties(String ssDate, String eeDate) {
		String[] otherProperties = { "id", "agroTxn.txnTime", "supplierProcurement.invoiceNo",
				"supplierProcurement.seasonCode", "supplierProcurement.warehouseId",
				"supplierProcurement.procMasterType", "supplierProcurement.procMasterTypeId", "f.firstName", "s.name",
				"v.name", "c.name", "pp.name", "pg.name", "numberOfBags", "ratePerUnit", "grossWeight",
				"supplierProcurement.isRegSupplier", "farmerName", "f.id", "supplierProcurement.invoiceValue" };

		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("id"));
		for (String property : otherProperties) {
			pList.add(Projections.property(property));
		}
		Date fromT = DateUtil.convertStringToDate(ssDate, DateUtil.DATE_FORMAT_1);
		Date to = DateUtil.convertStringToDate(eeDate, DateUtil.DATE_FORMAT_1);

		Session session = getSessionFactory().openSession();
		// CriteriaBuilder builder = session.getCriteriaBuilder();

		// Create CriteriaQuery
		/*
		 * CriteriaQuery<SupplierProcurementDetail> criteria =
		 * builder.createQuery(SupplierProcurementDetail.class);
		 * Root<SupplierProcurementDetail> from =
		 * criteria.from(SupplierProcurementDetail.class);
		 * from.join("SupplierProcurementDetail_.supplierProcurement");
		 * from.join("SupplierProcurement_.agroTransaction");
		 * 
		 * Predicate pp =
		 * builder.and(builder.isNotNull(from.get("procurementGrade")),
		 * builder.isNotNull(from.get("supplierProcurement")),
		 * builder.isNotNull(from.get("supplierProcurement.agroTransaction")),
		 * builder.between(from.get("agroTxn.txnTime"), fromT, to));
		 * criteria.orderBy(builder.desc(from.get("id")));
		 * criteria.select(from).where(pp);
		 * TypedQuery<SupplierProcurementDetail> q =
		 * session.createQuery(criteria); List list = q.getResultList();
		 */
		List list = null;
		session.flush();
		session.close();
		return list;
	}

	@Override
	public List<Object[]> listOfGroup() {
		return list("SELECT DISTINCT g.code,g.name,g.id from Packhouse g where g.typez=? ORDER BY g.name ASC",
				Packhouse.SAMITHI);
	}

	@Override
	public List<HarvestSeason> listHarvestSeasons() {
		return list("FROM HarvestSeason");
	}

	@Override
	public List<Pmt> findTransferStockByGinner(Integer ginner, Long warhouseId, String season) {
		Object[] values = { ginner, warhouseId, season };
		return list(
				"select distinct pmt From PMT pmt inner join pmt.pmtDetails pd "
						+ "WHERE pd.coOperative.typez=? AND pd.coOperative.id=? AND pmt.mtntReceiptNumber IS NOT NULL "
						+ "and pd.mtntGrossWeight > 0 and pmt.statusCode in ('1','2') and pd.status in ('0','1','2') and pmt.seasonCode=?",
				values);

	}

	@Override
	public List<Object[]> listGinningProcessByHeapProductAndGinning(String heap, Long product, Long ginning,
			String startDate, String endDate, String season) {
		Session sessions = getSessionFactory().openSession();
		String queryString = "";
		List<Object[]> list = new ArrayList<>();
		if (!StringUtil.isEmpty(heap) && !StringUtil.isEmpty(product)) {
			queryString = "SELECT gp.processDate,sum(gp.processQty) FROM GinningProcess gp where gp.heapCode=:heap AND gp.product.id=:product and gp.ginning.id=:ginning AND gp.season=:season AND gp.processDate BETWEEN :sDate AND :eDate GROUP BY gp.processDate";
			Query query = sessions.createQuery(queryString);
			query.setParameter("heap", heap);
			query.setParameter("ginning", ginning);
			query.setParameter("product", product);
			query.setParameter("season", season);
			query.setParameter("sDate", startDate);
			query.setParameter("eDate", endDate);

			list = query.list();
		}
		sessions.flush();
		sessions.close();
		return list;
	}

	@Override
	public List<Object[]> listProcurementTraceabilityStockbyAgent(String agentId, Long revNo, String season) {
		List<Object[]> result = new ArrayList<Object[]>();
		Session session = getSessionFactory().getCurrentSession();
		String queryString = "select f.farmer_id,pp.CODE,ptsd.TOTAL_NO_OF_BAGS,ptsd.TOTAL_STOCK,pts.GRADE from procurement_traceability_stock pts "
				+ "INNER JOIN procurement_product pp on pp.ID=pts.PROCUREMENT_PRODUCT_ID "
				+ "INNER JOIN procurement_traceability_stock_details ptsd on ptsd.PROCUREMENT_TRACEABILITY_STOCK_ID=pts.ID "
				+ "INNER JOIN farmer f on f.ID=ptsd.FARMER_ID " + "INNER JOIN warehouse w on w.ID=pts.CO_OPERATIVE_ID "
				+ "INNER JOIN prof p on p.WAREHOUSE_ID=w.ID WHERE p.PROF_ID=:agentId AND pts.SEASON=:season AND pts.REVISION_NO >=:revNo "
				+ "GROUP BY ptsd.FARMER_ID, pts.PROCUREMENT_PRODUCT_ID,pts.GRADE";
		Query query = session.createSQLQuery(queryString).setParameter("agentId", agentId).setParameter("revNo", revNo)
				.setParameter("season", season);
		result = query.list();
		return result;
	}

	@Override
	public List<CityWarehouse> listProcurementStockByAgentIdRevisionNo(String agentId, Long revisionNo) {

		Session session = getSessionFactory().openSession();
		String queryString1 = "FROM CityWarehouse cw WHERE cw.agentId=:agentId AND cw.isDelete=0 AND cw.revisionNo > :revNo)";
		Query query = session.createQuery(queryString1);
		query.setParameter("agentId", agentId);
		query.setParameter("revNo", revisionNo);
		List<CityWarehouse> list = query.list();
		session.flush();
		session.close();
		return list;
	}

	@Override
	public List<CityWarehouse> listCityWarehouseByAgentIdRevisionNo(String agentId, Long revisionNo) {

		Session session = getSessionFactory().openSession();

		String queryString1 = "FROM CityWarehouse cw WHERE cw.agentId=:agentId and cw.coOperative IS NULL AND cw.isDelete=0 AND cw.revisionNo > :revNo";
		Query query = session.createQuery(queryString1);
		query.setParameter("agentId", agentId);
		query.setParameter("revNo", revisionNo);
		List<CityWarehouse> list = query.list();
		session.flush();
		session.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PackhouseIncoming> listWarehouseProductByAgentRevisionNoStockByBatch(Long agentId, Long revisionNo) {

		Object[] bindValues = { agentId, revisionNo };
		return list(
				"FROM PackhouseIncoming wp WHERE wp.agent.id = ? AND wp.revisionNo>? GROUP BY wp.batchNo,wp.warehouse.id,wp.product.id,wp.agent.id,wp.seasonCode ORDER BY wp.revisionNo DESC",
				bindValues);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PackhouseIncoming> listWarehouseProductByAgentRevisionNoStockAndSeasonCodeByBatch(Long id, Long valueOf,
			String seasonCode) {

		Object[] bindValues = { id, seasonCode, valueOf };
		return list(
				"FROM PackhouseIncoming wp WHERE wp.agent.id = ? AND wp.seasonCode = ? AND wp.revisionNo>? GROUP BY wp.batchNo,wp.warehouse.id,wp.product.id,wp.agent.id ORDER BY wp.revisionNo DESC",
				bindValues);
	}

	@Override
	public List<String> listSyncLogins() {
		Session sessions = getSessionFactory().openSession();
		String queryStrings = "select TXN_KEY from Login_Txns where STATUS=1";
		Query querys = sessions.createSQLQuery(queryStrings);

		List results = querys.list();
		sessions.flush();
		sessions.close();
		return results;
	}

	@Override
	public User findByUsername(String username) {
		User user = (User) find("from User u  where username=? and u.dataId <> 3", username);
		return user;
	}

	@Override
	public List<Entitlement> listEntitlements(String username) {
		List<Entitlement> ents = list("select user.entitlements from User user where user.username = ?", username);
		return ents;
	}

	@Override
	public Role loadRole(Long id) {
		Role role = (Role) find(
				"From Role r left JOIN FETCH r.entitlements " + "INNER JOIN FETCH r.menus WHERE r.id = ?", id);
		return role;
	}

	@Override
	public String findLocaleProperty(String code, String languageCode) {
		Session session = currentSession();
		Query query = session.createQuery(
				"SELECT l.langValue FROM LocaleProperty l WHERE l.code=:code AND l.langCode=:languageCode");
		query.setParameter("code", code);
		query.setParameter("languageCode", languageCode);
		List<Object> list = query.list();
		Object object = ObjectUtil.isListEmpty(list) ? null : list.get(0);
		return ObjectUtil.isEmpty(object) ? "" : object.toString();

	}

	@Override
	public List<Customer> listCustomerByRevisionNo(Long revNo) {
		return list("FROM Customer sn WHERE sn.revisionNo>? and sn.status=0 ORDER BY sn.revisionNo DESC", revNo);

	}

	@Override
	public List<FarmCatalogueMaster> listCatalogueMasters() {
		return list("FROM FarmCatalogueMaster sn");
	}

	@Override
	public FarmCatalogueMaster findCatalogueMasterByTypez(Integer id) {
		return (FarmCatalogueMaster) find("FROM FarmCatalogueMaster sn where sn.typez=?", id);
	}

	@Override
	public HarvestSeason findHarvestSeasonbyCode(String String) {
		return (HarvestSeason) find("FROM HarvestSeason sn where sn.code=?", String);
	}

	@Override
	public List<Product> listProduct() {
		return list("FROM Product sn left join fetch sn.subCategory");
	}

	@Override
	public Product findProductByCode(String code) {
		return (Product) find("FROM Product sn where sn.code=?", code);
	}

	@Override
	public List<SubCategory> listSubCategory() {
		return list("FROM SubCategory sn");
	}

	@Override
	public SubCategory findSubCategoryByid(Long id) {

		return (SubCategory) find("FROM SubCategory sn where sn.id=?", id);
	}

	@Override
	public SubCategory findCategoryByCode(String code) {

		return (SubCategory) find("FROM SubCategory sn where sn.code=?", code);
	}

	@Override
	public List<Vendor> listVendor() {

		return list("FROM Vendor sn ORDER BY sn.id DESC");
	}

	@Override
	public Vendor findVendorById(String vendorId) {
		return (Vendor) find("FROM Vendor sn where sn.vendorId=?", vendorId);
	}

	@Override
	public List<Customer> listCustomer() {

		return list("FROM Customer sn left join fetch sn.locationDetail and sn.status=0");
	}
/*	
	@Override
	public List<Pcbp> listPcbp() {
		
		return list("FROM Customer sn left join fetch sn.locationDetail");
	}*/
	
	@Override
	public List<Object[]> listPcbp() {
		return list("select  p.id,p.tradeName FROM Pcbp p");

	}
	

	@Override
	public Customer findCustomerBycustomerId(String customerId) {
		return (Customer) find("FROM Customer sn where sn.customerId=?", customerId);
	}

	@Override
	public List<State> listStatesByRevisionNoAndCountryCode(String countryCode, long revisionNo) {
		Object[] values = { revisionNo, countryCode };
		return list("From State s WHERE s.revisionNo>? AND s.country.code=? ORDER BY s.revisionNo DESC", values);
	}

	@Override
	public List<Locality> listDistrictsByRevisionNoAndStateCode(String stateCode, long revisionNo) {
		Object[] values = { revisionNo, stateCode };
		return list("From Locality ld WHERE ld.revisionNo>? AND ld.state.code=? ORDER BY ld.revisionNo DESC", values);
	}

	@Override
	public List<GramPanchayat> listGrampanchayatByRevisionNoAndCityCode(String cityCode, long revisionNo) {
		Object[] values = { revisionNo, cityCode };
		return list("From GramPanchayat gp WHERE gp.revisionNo>? AND gp.city.code=? ORDER BY gp.revisionNo DESC",
				values);
	}

	@Override
	public List<Municipality> listCityByRevisionNoAndDistrictCode(String districtCode, long revisionNo) {
		Object[] values = { revisionNo, districtCode };
		return list("From Municipality c WHERE c.revisionNo>? AND c.locality.code=? ORDER BY c.revisionNo DESC",
				values);
	}

	@Override
	public List<Village> listVillageByRevisionNoAndCityCode(String gpCode, long revisionNo) {
		Object[] values = { revisionNo, gpCode };
		return list("From Village v WHERE v.revisionNo>? AND v.city.code=? ORDER BY v.revisionNo DESC", values);
	}

	@Override
	public Village findVillageByCode(String villageCode) {
		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Query query = session.createQuery("From Village v where v.code=:code");
		query.setParameter("code", villageCode);
		List list = query.list();
		Village village = null;
		if (list.size() > 0) {
			village = (Village) query.list().get(0);
		}
		session.flush();
		session.close();
		return village;
	}

	@Override
	public List<FarmCatalogue> listCatalogueByCatalogueMasterType(Integer catalogueType) {
		Object[] values = { catalogueType, FarmCatalogue.ACTIVE };
		return list("FROM FarmCatalogue sn WHERE sn.typez.typez =? AND sn.status=? ORDER BY sn.revisionNo DESC",
				values);
	}

	public SeasonMaster findSeasonBySeasonCode(String seasonCode) {

		SeasonMaster season = (SeasonMaster) find("FROM Season sn WHERE sn.code = ? ", seasonCode);
		return season;
	}

	@Override
	public AgentAccessLog findAgentAccessLogByAgentId(String agentId, Date txnDate) {
		Object[] bind = { agentId, txnDate };
		AgentAccessLog agentAccessLog = (AgentAccessLog) find(
				"FROM AgentAccessLog a WHERE a.profileId = ? and a.login = ?", bind);
		return agentAccessLog;
	}

	public ESECard findESECardByCardId(String cardId) {
		return (ESECard) find("FROM ESECard c WHERE c.cardId = ?", cardId);
	}

	@Override
	public AgentAccessLogDetail findAgentAccessLogDetailByTxn(Long accessId, String txnType) {
		Object[] bind = { accessId, txnType };
		AgentAccessLogDetail agentAccessLogDetail = (AgentAccessLogDetail) find(
				"FROM AgentAccessLogDetail a WHERE a.agentAccessLog.id = ? AND a.txnType=?", bind);
		return agentAccessLogDetail;
	}

	@Override
	public Village findVillageById(Long id) {

		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Query query = session.createQuery("From Village v where v.id=:id");
		query.setParameter("id", id);
		List list = query.list();
		Village village = null;
		if (list.size() > 0) {
			village = (Village) query.list().get(0);
		}
		session.flush();
		session.close();
		return village;

	}

	public ESESystem findPrefernceById(String id, String tenantId) {

		Session session = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		Query query = session.createQuery("FROM ESESystem es WHERE es.id = :id");
		query.setParameter("id", Integer.valueOf(id));

		List<ESESystem> sysList = query.list();

		ESESystem eseSystem = null;
		if (sysList.size() > 0) {
			eseSystem = (ESESystem) sysList.get(0);
		}

		session.flush();
		session.close();
		return eseSystem;
	}

	@Override
	public String findAgentTimerValue() {
		String queryString = "SELECT VAL FROM pref WHERE pref.NAME = 'AGENT_EXPIRY_TIMER'";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery(queryString);
		List<Object> list = query.list();
		sessions.flush();
		sessions.close();
		if (!ObjectUtil.isListEmpty(list))
			return (String) list.get(0);
		return null;

	}

	@Override
	public Agent findAgentByAgentId(String agentId) {

		Agent agent = (Agent) find("FROM Agent a WHERE  a.profileId = ?", agentId);

		return agent;
	}

	public Procurement findProcurementByRecNo(String receiptNo) {

		Procurement procurement = (Procurement) find("FROM Procurement pt WHERE pt.agroTransaction.receiptNo = ?",
				receiptNo);

		return procurement;
	}

	public ESEAccount findAccountByProfileId(String profileId) {

		ESEAccount account = (ESEAccount) find("from ESEAccount account where account.profileId = ?", profileId);
		return account;
	}

	public SeasonMaster findCurrentSeason() {

		ESESystem preference = findPrefernceById(ESESystem.SYSTEM_SWITCH);
		String currentSeasonCode = preference.getPreferences().get(ESESystem.CURRENT_SEASON_CODE);
		if (!StringUtil.isEmpty(currentSeasonCode)) {
			SeasonMaster currentSeason = findSeasonBySeasonCode(currentSeasonCode);
			return currentSeason;
		}
		return null;
	}

	public void updateAgentReceiptNoSequence(String agentId, String offlineReceiptNo) {

		String queryString = "UPDATE agent_prof INNER JOIN prof ON prof.ID = agent_prof.PROF_ID SET agent_prof.RECEIPT_NUMBER = ' "
				+ offlineReceiptNo + "' WHERE prof.PROF_ID = '" + agentId + "'";
		Session sessions = getSessionFactory().openSession();
		Query querys = sessions.createSQLQuery(queryString);
		int results = querys.executeUpdate();
		sessions.flush();
		sessions.close();

	}

	public List<AgroTransaction> findAgroTxnByReceiptNo(String receiptNo) {

		return list("FROM AgroTransaction at WHERE at.receiptNo = ? AND at.operType = 1 ", receiptNo);
	}

	@Override
	public AgroTransaction findEseAccountByTransaction(long id) {
		AgroTransaction agroTransaction = (AgroTransaction) find("from AgroTransaction ag where ag.account.id=?", id);
		return agroTransaction;
	}

	public VillageWarehouse findVillageWarehouse(long villageId, String agentId) {

		Object[] values = { villageId, agentId };
		VillageWarehouse villageWarehouse = (VillageWarehouse) find(
				"FROM VillageWarehouse vw WHERE vw.village.id = ?  AND vw.agentId = ? AND vw.isDelete = 0", values);
		return villageWarehouse;
	}

	public ESEAccount findESEAccountByProfileId(String profileId, int type) {
		Object[] values = { profileId, type };
		ESEAccount account = (ESEAccount) find("from ESEAccount account where account.profileId = ? AND type=?",
				values);
		return account;
	}

	public CityWarehouse findCityWarehouseByCoOperative(long coOperativeId, String agentId) {

		Object[] values = { coOperativeId, agentId };
		CityWarehouse cityWarehouse = (CityWarehouse) find(
				"FROM CityWarehouse cw WHERE cw.coOperative.id = ? AND cw.agentId = ? AND cw.isDelete = 0", values);
		return cityWarehouse;
	}

	@Override
	public Long findVillageWarehouselatestRevisionNo() {

		List list = list("Select Max(w.revisionNo) FROM PackhouseIncoming w");
		if (!ObjectUtil.isListEmpty(list) && !ObjectUtil.isEmpty(list.get(0)))
			return (Long) list.get(0);
		return 0l;
	}

	public CityWarehouse findCityWarehouseByCoOperative(long coOperativeId, String agentId, String quality) {

		Object[] values = { coOperativeId, quality };
		CityWarehouse cityWarehouse = (CityWarehouse) find(
				"FROM CityWarehouse cw WHERE cw.coOperative.id = ?  AND cw.quality = ? AND cw.isDelete = 0", values);
		return cityWarehouse;
	}

	public CityWarehouse findCityWarehouseByVillage(long villageId, String agentId, String quality) {

		Object[] values = { villageId, agentId, quality };
		CityWarehouse cityWarehouse = (CityWarehouse) find(
				"FROM CityWarehouse cw WHERE cw.village.id = ?  AND cw.agentId = ? AND cw.quality = ? AND cw.isDelete = 0",
				values);
		return cityWarehouse;
	}

	public CityWarehouse findCityWareHouseByWarehouseIdProcrmentGradeCodeAndProcurementProductId(long warehouseId,
			String gradeCode) {

		Object[] values = { warehouseId, gradeCode };
		return (CityWarehouse) find("FROM CityWarehouse cw WHERE cw.coOperative.id=? AND cw.quality=? ", values);
	}

	public CityWarehouse findCityWarehouseByState(long stateId, long productId, String quality, String tenantId) {

		Session session = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		Query query = session.createQuery(
				"FROM CityWarehouse cw WHERE cw.state.id = :stateId AND cw.procurementProduct.id = :productId "
						+ " AND cw.quality = :quality AND cw.isDelete = 0");
		query.setParameter("stateId", stateId);
		query.setParameter("productId", productId);
		query.setParameter("quality", quality);

		List<CityWarehouse> cityWarehouseList = query.list();
		CityWarehouse cityWarehouse = null;
		if (cityWarehouseList.size() > 0) {
			cityWarehouse = (CityWarehouse) cityWarehouseList.get(0);
		}

		session.flush();
		session.close();
		return cityWarehouse;
	}

	public CityWarehouse findCityWarehouseByState(long stateId, long productId, String quality) {

		Object[] values = { stateId, productId, quality };
		CityWarehouse cityWarehouse = (CityWarehouse) find(
				"FROM CityWarehouse cw WHERE cw.state.id = ? AND cw.procurementProduct.id = ? AND cw.quality = ? AND cw.isDelete = 0",
				values);
		return cityWarehouse;
	}

	public ESEAccount findAccountBySeassonProcurmentProductFarmer(long seasonId, long farmerId) {
		return (ESEAccount) find("SELECT c.account FROM Contract c WHERE c.farmer.id=?", new Object[] { farmerId });
	}

	public CityWarehouse findSupplierWarehouseByCoOperative(long coOperativeId, String quality, String agentId) {
		Object[] values = { coOperativeId, quality, agentId };
		CityWarehouse cityWarehouse = (CityWarehouse) find(
				"FROM CityWarehouse cw WHERE cw.coOperative.id = ?  AND cw.quality = ? AND cw.agentId= ? AND cw.isDelete = 0",
				values);
		return cityWarehouse;
	}

	public ESEAccount findAccountByFarmerLoanProduct(long farmerId) {
		return (ESEAccount) find("SELECT c.account FROM Contract c WHERE c.farmer.id=?", new Object[] { farmerId });
	}

	@Override
	public void saveOrUpdateCityWarehouse(CityWarehouse cityWarehouse, String tenantId) {
		Session sessions = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		sessions.saveOrUpdate(cityWarehouse);
		sessions.flush();
		sessions.close();
	}

	@Override
	public void saveCityWarehouseDetail(CityWarehouseDetail cityWarehouseDetail, String tenantId) {
		Session sessions = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		sessions.save(cityWarehouseDetail);
		sessions.flush();
		sessions.close();
	}

	@Override
	public CityWarehouse findCityWarehouseByWarehouseProductBatchNoGradeFarmer(long warehouseId, long productId,
			String batchNo, String grade, String coldStorageName, String blockName, String floorName, String bayNum,
			long farmerId) {

		Session session = getSessionFactory().getCurrentSession();
		String queryString = "SELECT * from city_warehouse cw JOIN city_warehouse_detail cwd ON cwd.CITY_WAREHOUSE_ID=cw.ID WHERE cw.CO_OPERATIVE_ID='"
				+ warehouseId + "' AND cw.PROCUREMENT_PRODUCT_ID='" + productId + "' AND cwd.BATCH_NO='" + batchNo
				+ "' AND cw.quality ='" + grade + "' AND cw.COLD_STORAGE_NAME='" + coldStorageName
				+ "' AND cwd.BLOCK_NAME='" + blockName + "' AND cwd.FLOOR_NAME='" + floorName + "' AND cwd.BAY_NUMBER='"
				+ bayNum + "' AND cw.FARMER_ID='" + farmerId + "' AND cw.IS_DELETE='0';";
		Query query = session.createSQLQuery(queryString).addEntity(CityWarehouse.class);
		List<CityWarehouse> list = query.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public CityWarehouse findSupplierWarehouseByCoOperativeProductAndGrade(long coOperativeId, String quality) {
		Object[] values = { coOperativeId, quality };
		CityWarehouse cityWarehouse = (CityWarehouse) find(
				"FROM CityWarehouse cw WHERE cw.coOperative.id = ? AND cw.quality = ? AND cw.isDelete = 0", values);
		return cityWarehouse;
	}

	public VillageWarehouse findVillageWarehouse(long villageId, String agentId, String qualityCode) {

		Object[] values = { villageId, agentId, qualityCode };
		VillageWarehouse villageWarehouse = (VillageWarehouse) find(
				"FROM VillageWarehouse vw WHERE vw.village.id = ?  AND vw.agentId = ? AND vw.quality = ? AND vw.isDelete = 0",
				values);
		return villageWarehouse;
	}

	public Customer findCustomerById(String customerId) {

		Customer customer = (Customer) find("FROM Customer c WHERE c.customerId = ?", customerId);
		return customer;
	}
	
	public Customer findCustomerByName(String customerName) {
		
		Customer customer = (Customer) find("FROM Customer c WHERE c.customerName = ?", customerName);
		return customer;
	}

	public Pmt findPMTByReceiptNumber(String receiptNumber) {

		return (Pmt) find("From Pmt pmt WHERE pmt.mtntReceiptNumber=?", receiptNumber);
	}

	@Override
	public List<FarmCatalogue> listCatalogues() {
		Object[] values = { FarmCatalogue.ACTIVE };
		return list("FROM FarmCatalogue cg where cg.status=? ORDER BY cg.name ASC", values);
	}

	@Override
	public PackhouseIncoming findAgentAvailableStock(long agentId, long prodId) {
		Object[] values = { agentId, prodId };
		PackhouseIncoming packhouseIncoming = (PackhouseIncoming) find(
				"FROM PackhouseIncoming wp WHERE wp.agent.id=? AND wp.product.id=? AND wp.warehouse.id=null", values);
		return packhouseIncoming;
	}

	@Override
	public void SaveOrUpdateByTenant(Object obj, String tenantId) {
		Session sessions = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		sessions.saveOrUpdate(obj);
		sessions.flush();
		sessions.close();

	}

	@Override
	public void saveOrUpdate(Object warehouseProduct, String tenantId) {

		Session sessions = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		sessions.saveOrUpdate(warehouseProduct);
		sessions.flush();
		sessions.close();

	}

	@Override
	public void save(WarehouseProductDetail warehouseProductDetail, String tenantId) {

		Session sessions = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		sessions.save(warehouseProductDetail);
		sessions.flush();
		sessions.close();

	}

	public PackhouseIncoming findFieldStaffAvailableStock(String agentId, long productId, String tenantId) {

		Session session = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		Query query = session.createQuery(
				"From PackhouseIncoming wp WHERE wp.agent.profileId= :agentId AND wp.product.id= :productId");
		query.setParameter("agentId", agentId);
		query.setParameter("productId", productId);

		List<PackhouseIncoming> warehouseProductList = query.list();
		PackhouseIncoming packhouseIncoming = null;
		if (warehouseProductList.size() > 0) {
			packhouseIncoming = (PackhouseIncoming) warehouseProductList.get(0);
		}

		session.flush();
		session.close();
		return packhouseIncoming;

	}

	public void updateESEAccountCashBalById(long id, double cashBalance) {
		String queryString = "UPDATE ESE_ACCOUNT SET CASH_BALANCE ='" + cashBalance + "' WHERE ID='" + id + "'";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(queryString);
		int result = query.executeUpdate();
	}

	@Override
	public PackhouseIncoming findAvailableStockByAgentIdProductIdBatchNoSeason(String agentId, long productId,
			String selectedSeason, String batchNo) {
		Object[] values = { agentId, productId, selectedSeason, batchNo };

		PackhouseIncoming packhouseIncoming = (PackhouseIncoming) find(
				"FROM PackhouseIncoming wp WHERE wp.agent.profileId=? AND wp.product.id=? AND wp.seasonCode=? AND wp.warehouse.id=null AND wp.batchNo=?",
				values);
		return packhouseIncoming;
	}

	public PackhouseIncoming findAvailableStock(long warehouseId, long productId, String tenantId) {

		Session session = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		Query query = session.createQuery(
				"FROM PackhouseIncoming wp WHERE wp.warehouse.id = :warehouseId AND wp.product.id :productId AND wp.agent.id=null");
		query.setParameter("warehouseId", warehouseId);
		query.setParameter("productId", productId);

		List<PackhouseIncoming> warehouseProductList = query.list();
		PackhouseIncoming packhouseIncoming = null;
		if (warehouseProductList.size() > 0) {
			packhouseIncoming = (PackhouseIncoming) warehouseProductList.get(0);
		}
		session.flush();
		session.close();
		return packhouseIncoming;

	}

	@Override
	public PackhouseIncoming findAvailableStocksBySeasonAndBatch(String servicePointId, long productId, String seasonId,
			String batch, String tenantId) {

		Session session = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		Query query = session.createQuery(
				"FROM PackhouseIncoming wp WHERE wp.warehouse.code= :servicePointId AND wp.product.id= :id AND wp.seasonCode=:seasonCode AND wp.batchNo=:batchNo");
		query.setParameter("servicePointId", servicePointId);
		query.setParameter("id", productId);
		query.setParameter("seasonCode", seasonId);
		query.setParameter("batchNo", batch);

		List<PackhouseIncoming> warehouseProductList = query.list();
		PackhouseIncoming packhouseIncoming = null;
		if (warehouseProductList.size() > 0) {
			packhouseIncoming = (PackhouseIncoming) warehouseProductList.get(0);
		}
		session.flush();
		session.close();
		return packhouseIncoming;
	}

	@Override
	public PackhouseIncoming findFieldStaffAvailableStockBySeasonAndBatch(String agentId, long productId,
			String seasonId, String batch, String tenantId, String branchId) {
		Session session = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		Query query = session.createQuery(
				"From PackhouseIncoming wp WHERE wp.agent.profileId= :agentId AND wp.product.id= :productId AND wp.seasonCode=:seasonCode AND wp.batchNo=:batchNo AND wp.branchId=:branchId AND wp is not null");
		query.setParameter("agentId", agentId);
		query.setParameter("productId", productId);
		query.setParameter("seasonCode", seasonId);
		query.setParameter("batchNo", batch);
		query.setParameter("branchId", branchId);

		List<PackhouseIncoming> warehouseProductList = query.list();
		PackhouseIncoming packhouseIncoming = null;
		if (warehouseProductList.size() > 0) {
			packhouseIncoming = (PackhouseIncoming) warehouseProductList.get(0);
		}

		session.flush();
		session.close();
		return packhouseIncoming;
	}

	@Override
	public String findPrefernceByName(String enableApproved, String tenantId) {

		String queryString = "SELECT VAL FROM pref WHERE pref.NAME ='" + enableApproved + "' ";
		Session sessions = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		Query query = sessions.createSQLQuery(queryString);
		List<Object> list = query.list();
		sessions.flush();
		sessions.close();
		if (!ObjectUtil.isListEmpty(list))
			return (String) list.get(0);
		return null;

	}

	@Override
	public void saveByTenantId(Object object, String tenantId) {
		Session sessions = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		sessions.save(object);
		sessions.flush();
		sessions.close();

	}

	@Override
	public PackhouseIncoming findCooperativeAvailableStockByCooperative(long agentId, long productId, String tenantId) {

		Session session = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		Query query = session.createQuery(
				"From PackhouseIncoming wp WHERE wp.warehouse.id=(SELECT Distinct wh.id FROM Agent a INNER JOIN a.wareHouses wh WHERE  a.profileId=:agentId) AND wp.product.id= :productId AND wp.agent=null ");
		query.setParameter("agentId", agentId);
		query.setParameter("productId", productId);

		List<PackhouseIncoming> warehouseProductList = query.list();
		PackhouseIncoming packhouseIncoming = null;
		if (warehouseProductList.size() > 0) {
			packhouseIncoming = (PackhouseIncoming) warehouseProductList.get(0);
		}
		session.flush();
		session.close();
		return packhouseIncoming;

	}

	public PackhouseIncoming findAgentAvailableStock(String agentId, long productId, String tenantId) {

		Session session = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		Query query = session.createQuery(
				"From PackhouseIncoming wp WHERE wp.agent.profileId= :agentId and wp.product.id= :productId and wp.warehouse.id =(SELECT Distinct w.refCooperative.id FROM Agent a INNER JOIN a.wareHouses w WHERE a.profileId= :agentId)");
		query.setParameter("agentId", agentId);
		query.setParameter("productId", productId);

		List<PackhouseIncoming> warehouseProductList = query.list();
		PackhouseIncoming packhouseIncoming = null;
		if (warehouseProductList.size() > 0) {
			packhouseIncoming = (PackhouseIncoming) warehouseProductList.get(0);
		}
		session.flush();
		session.close();
		return packhouseIncoming;

	}

	public Agent findAgentByProfileId(String profileId, String tenantId) {

		Session session = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		Query query = session.createQuery("from Agent agent where agent.profileId = :profileId");
		query.setParameter("profileId", profileId);

		List<Agent> agentList = query.list();
		Agent agent = null;

		if (agentList.size() > 0) {
			agent = (Agent) agentList.get(0);
		}

		session.flush();
		session.close();
		return agent;
	}

	public PackhouseIncoming findAvailableStock(long warehouseId, long productId) {

		Object[] values = { warehouseId, productId };
		PackhouseIncoming packhouseIncoming = (PackhouseIncoming) find(
				"FROM PackhouseIncoming wp WHERE wp.warehouse.id=? AND wp.product.id=? AND wp.agent.id=null", values);
		return packhouseIncoming;
	}

	public ESEAccount findAccountBySeassonProcurmentProductFarmer(long seasonId, long farmerId, String tenantId) {

		Session session = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		Query query = session
				.createQuery("SELECT c.account FROM Contract c WHERE c.season.id=:seasonId AND c.farmer.id=:farmerId");
		query.setParameter("seasonId", seasonId);
		query.setParameter("farmerId", farmerId);

		List<ESEAccount> eseAccountList = query.list();

		ESEAccount eseAccount = null;
		if (eseAccountList.size() > 0) {
			eseAccount = (ESEAccount) eseAccountList.get(0);
		}

		session.flush();
		session.close();
		return eseAccount;
	}

	@Override
	public PackhouseIncoming findAvailableStockByWarehouseIdSelectedProductSeasonBatchNo(long warehouseId,
			long productId, String selectedSeason, String batchNo) {
		Object[] values = { warehouseId, productId, selectedSeason, batchNo };
		PackhouseIncoming packhouseIncoming = (PackhouseIncoming) find(
				"FROM PackhouseIncoming wpd WHERE wpd.warehouse.id=? AND wpd.product.id=? AND wpd.seasonCode=? AND wpd.agent.id=null AND wpd.batchNo=?",
				values);
		return packhouseIncoming;
	}

	@Override
	public PackhouseIncoming findAvailableStockByWarehouseIdProductIdBatchNoAndSeason(long warehouseId, long productId,
			String batchNo, String seasonCode) {
		Object[] values = { warehouseId, productId, batchNo, seasonCode };

		return (PackhouseIncoming) find(
				"FROM PackhouseIncoming wp WHERE wp.warehouse.id=? AND wp.product.id=? AND wp.agent.id=null AND wp.batchNo=? AND wp.seasonCode=?",
				values);
	}

	public PackhouseIncoming findAgentAvailableStock(String agentId, long productId) {

		Object[] bindValues = { agentId, productId, agentId };
		return (PackhouseIncoming) find(
				"From PackhouseIncoming wp WHERE wp.agent.profileId=? and wp.product.id=? and wp.warehouse.id =(SELECT Distinct w.refCooperative.id FROM Agent a INNER JOIN a.wareHouses w WHERE a.profileId=?)",
				bindValues);
	}

	@Override
	public PackhouseIncoming findWarehouseStockByWarehouseIdAndProductId(long warehouseId, long productId,
			String season) {
		Object[] values = { warehouseId, productId, season };
		return (PackhouseIncoming) find(
				"FROM PackhouseIncoming wp WHERE wp.warehouse.id=? AND wp.product.id=? AND wp.seasonCode=?", values);
	}

	public Product findProductById(long id) {

		Product product = (Product) find("FROM Product p WHERE p.id = ?", id);
		return product;
	}

	@Override
	public void saveAgroTransaction(AgroTransaction agroTransaction, String tenantId) {
		Session sessions = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		sessions.save(agroTransaction);
		sessions.flush();
		sessions.close();
	}

	public ESEAccount findAccountByProfileIdAndProfileType(String profileId, int type, String tenantId) {

		Session session = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		Query query = session
				.createQuery("FROM ESEAccount esea WHERE esea.profileId = :profileId AND esea.type = :type");
		query.setParameter("profileId", profileId);
		query.setParameter("type", type);

		List<ESEAccount> eseAccountList = query.list();

		ESEAccount eseAccount = null;
		if (eseAccountList.size() > 0) {
			eseAccount = (ESEAccount) eseAccountList.get(0);
		}

		session.flush();
		session.close();
		return eseAccount;
	}

	public ESEAccount findAccountByProfileId(String profileId, String tenantId) {

		Session session = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		Query query = session.createQuery("from ESEAccount account where account.profileId = :profileId");
		query.setParameter("profileId", profileId);

		List<ESEAccount> eseAccountList = query.list();

		ESEAccount eseAccount = null;
		if (eseAccountList.size() > 0) {
			eseAccount = (ESEAccount) eseAccountList.get(0);
		}

		session.flush();
		session.close();
		return eseAccount;
	}

	@Override
	public void updateESEAccount(ESEAccount farmerAccount, String tenantId) {

		Session sessions = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		sessions.update(farmerAccount);
		sessions.flush();
		sessions.close();

	}

	@Override
	public List<AgroTransaction> listFarmerTransactionHistory(String farmerId, String[] transactionArray, int limit) {

		Session session = getSessionFactory().openSession();
		String queryString = "FROM AgroTransaction agTran WHERE agTran.farmerId=:farmerId AND agTran.txnType IN (:transactionArray) AND agTran.profType=:profType ORDER BY agTran.id DESC ";
		Query query = session.createQuery(queryString).setParameter("farmerId", farmerId)
				.setParameterList("transactionArray", transactionArray).setParameter("profType", Profile.CLIENT)
				.setMaxResults(limit);
		List<AgroTransaction> list = query.list();
		session.flush();
		session.close();
		return list;
	}

	@Override
	public Customer findCustomer(long id) {

		Customer customer = (Customer) find("FROM Customer c WHERE c.id = ?", id);
		return customer;
	}

	@Override
	public void updateCashBal(long id, double cashBalance, double creditBalance) {

		String queryString = "UPDATE ESE_ACCOUNT SET CASH_BALANCE ='" + cashBalance + "',CREDIT_BALANCE ='"
				+ creditBalance + "' WHERE ID='" + id + "'";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(queryString);
		int result = query.executeUpdate();

	}

	@Override
	public ESEAccount findEseAccountByBuyerIdAndTypee(String profileId, int type) {

		Session session = getSessionFactory().getCurrentSession();
		String queryString = "SELECT * from ese_account ea WHERE ea.profile_id='" + profileId + "' AND ea.typee='"
				+ type + "';";
		Query query = session.createSQLQuery(queryString).addEntity(ESEAccount.class);
		List<ESEAccount> list = query.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public ESEAccount findEseAccountByProfileId(String profileId) {

		Session session = getSessionFactory().getCurrentSession();
		String queryString = "SELECT * from ese_account ea WHERE ea.profile_id='" + profileId + "';";
		Query query = session.createSQLQuery(queryString).addEntity(ESEAccount.class);
		List<ESEAccount> list = query.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void updateESEAccountOutStandingBalById(long id, double loanBalance) {
		String queryString = "UPDATE ESE_ACCOUNT SET OUTSTANDING_AMOUNT ='" + loanBalance + "' WHERE ID='" + id + "'";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(queryString);
		int result = query.executeUpdate();
	}

	@Override
	public void save(Object obj, String tenantId) {
		Session sessions = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		sessions.saveOrUpdate(obj);
		sessions.flush();
		sessions.close();
	}

	@Override
	public List<Object[]> listMaxTypeByFarmerId(Long farmerId) {
		Object[] values = { farmerId };
		return list(
				"SELECT fdfv.fieldName,fdfv.typez FROM FarmerDynamicFieldsValue fdfv WHERE fdfv.farmer.id=? AND fdfv.typez IS NOT NULL GROUP BY fdfv.fieldName",
				values);
	}

	@Override
	public FarmCatalogue findCatalogueById(Long id) {
		return (FarmCatalogue) find("FROM FarmCatalogue cg WHERE cg.id = ?", id);
	}

	@Override
	public FarmCatalogue findCatalogueByName(String name) {
		return (FarmCatalogue) find("FROM FarmCatalogue cg WHERE cg.name = ?", name);
	}

	@Override
	public FarmCatalogueMaster findFarmCatalogueMasterById(Long id) {
		return (FarmCatalogueMaster) find("FROM FarmCatalogueMaster fm WHERE fm.id=?", id);
	}

	@Override
	public String findCatalogueValueByCode(String code) {

		Session session = getSessionFactory().openSession();
		Query query = session.createQuery("SELECT fc.name FROM FarmCatalogue fc  WHERE fc.code=:code");
		query.setParameter("code", code);
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
	public List<Agent> listAgentByWarehouseAndRevisionNo(long warehouseId, String revisionNo) {
		return (List<Agent>) list("FROM Agent a WHERE a.procurementCenter.id=? and a.profRev>? ORDER BY a.id ASC",
				new Object[] { warehouseId, Long.parseLong(revisionNo) });
	}

	@Override
	public List<Agent> listAgentByRevisionNo(String branchId, Long revisionNo) {
		Session session = getSessionFactory().openSession();
		org.hibernate.Filter branchFilter = session.getEnabledFilter(ISecurityFilter.BRANCH_FILTER);
		if (!ObjectUtil.isEmpty(branchFilter)) {
			session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		}
		List<Agent> agents = new ArrayList<>();
		Query query = session.createQuery("FROM Agent a where a.procurementCenter is not null ORDER BY a.id ASC");
		agents = query.list();

		session.flush();
		session.close();
		return agents;

	}

	@Override
	public List<Object[]> listFarmsLastInspectionDate() {
		Session session = getSessionFactory().openSession();
		SQLQuery query = session.createSQLQuery(
				"SELECT max(DATE(pi.INSPECTION_DATE)),pi.FARM_ID from periodic_inspection pi group by pi.FARM_ID");
		List list = query.list();
		return list;
	}

	@Override
	public Vendor findVendorByDbId(String id) {
		Long idl = 0l;
		try {
			idl = Long.valueOf(id);
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
		return (Vendor) find("FROM Vendor sn where sn.id=?", idl);
	}

	@Override
	public List<Vendor> listVendorByFilter(String vendorId, String vendorName, String mobileNo) {

		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Object[] values = { vendorId, vendorName, mobileNo };
		List list;
		String queryStr = "FROM Vendor ven WHERE ven.vendorId like :vendorId OR ven.vendorName like :vendorName OR ven.mobileNo like :mobileNo ORDER BY ven.id DESC";
		Query query = session.createQuery(queryStr).setParameter("vendorId", "%" + vendorId + "%")
				.setParameter("vendorName", "%" + vendorName + "%").setParameter("mobileNo", mobileNo + "%");

		list = query.list();
		session.flush();
		session.close();
		return list;
	}

	@Override
	public List<Role> listRole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role findRoleById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCityWarehouseByWhId(String id) {
		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Object[] values = { id };

		String queryStr = "DELETE FROM CityWarehouse cw where cw.coOperative.id = :cooperativeId";
		Query query = session.createQuery(queryStr).setParameter("cooperativeId", Long.valueOf(id));

		query.executeUpdate();
		session.flush();
		session.close();
	}

	@Override
	public List<CityWarehouse> listCityWarehouseByCooperativeId(Long id) {

		return list("FROM CityWarehouse cw WHERE cw.coOperative.id=?", id);
	}

	@Override
	public void deletePmtWarehouseByWhId(long id) {
		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Object[] values = { id };

		String queryStr = "DELETE FROM Pmt p where p.coOperative.id = :cooperativeId";
		Query query = session.createQuery(queryStr).setParameter("cooperativeId", Long.valueOf(id));

		query.executeUpdate();
		session.flush();
		session.close();
	}

	@Override
	public List<Pmt> listPmtByCooperativeId(Long id) {

		return list("FROM Pmt pmt WHERE pmt.coOperative.id=?", id);
	}

	@Override
	public void deleteWarehouseAgroTrxnByWhId(long id) {
		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Object[] values = { id };

		String queryStr = "DELETE FROM AgroTransaction agrotrxn where agrotrxn.warehouse.id = :cooperativeId";
		Query query = session.createQuery(queryStr).setParameter("cooperativeId", Long.valueOf(id));

		query.executeUpdate();
		session.flush();
		session.close();
	}

	@Override
	public boolean findAgentMappedWithWarehouse(long id) {

		boolean isAgentMappedWithWarehouse = false;
		Session sessions = getSessionFactory().openSession();
		String queryStrings = "SELECT AGENT_ID FROM AGENT_WAREHOUSE_MAP WHERE WAREHOUSE_ID = " + id;
		Query querys = sessions.createSQLQuery(queryStrings);
		List results = querys.list();
		if (results.size() > 0) {
			isAgentMappedWithWarehouse = true;
		}
		sessions.flush();
		sessions.close();
		return isAgentMappedWithWarehouse;
	}

	@Override
	public List<FarmCatalogue> listCataloguesByUnit() {
		Object[] values = { Product.LIST_UNIT_BASED_ON_UOM, FarmCatalogue.ACTIVE };
		return list("FROM FarmCatalogue cg WHERE cg.typez.typez=? and cg.status=?", values);
	}

	@Override
	public PmtDetail findPMTDetailByGradeId(long gradeId) {
		return (PmtDetail) find("From PmtDetail pmtDet WHERE pmtDet.procurementGrade.id=?", gradeId);
	}

	@Override
	public TransactionType findTxnTypeByCode(String code) {

		return (TransactionType) find("From TransactionType txnType where txnType.code=?", code);
	}

	@Override
	public Transaction findDuplication(Date txnTime, String serialNo, String msgNo) {

		Object[] values = { txnTime, serialNo, msgNo };
		Transaction transaction = (Transaction) find(
				"FROM Transaction ts WHERE ts.txnTime = ? AND ts.header.serialNo = ? AND ts.msgNo = ?", values);
		return transaction;
	}

	@Override
	public void updateMsgNo(String serialNo, String msgNo) {
		Session session = getSessionFactory().openSession();
		Query query = session.createQuery("update Device set msgNo = :msgNo" + " where serialNumber = :serialNo");
		query.setParameter("msgNo", msgNo);
		query.setParameter("serialNo", serialNo);
		int result = query.executeUpdate();
		session.flush();
		session.close();

	}

	@Override
	public AgentAccessLogDetail listAgnetAccessLogDetailsByIdTxnType(long id, String type, String msgNo) {
		Object[] obj = { id, type, msgNo };
		return (AgentAccessLogDetail) find(
				"FROM AgentAccessLogDetail aad WHERE aad.agentAccessLog.id=? AND aad.txnType=? AND aad.messageNumber=? ORDER BY aad.id DESC",
				obj);
	}

	@Override
	public TransactionLog findTransactionLogById(long id) {

		return (TransactionLog) find("FROM TransactionLog WHERE id=?", id);
	}

	@Override
	public List<Language> listLanguages() {
		// TODO Auto-generated method stub
		return list("FROM Language l WHERE l.webStatus=1 ORDER by l.code ASC");
	}

	public User findByUserNameExcludeBranch(String userName) {

		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);

		User user = null;
		user = (User) find(
				"from User user LEFT JOIN FETCH user.entitlements ent LEFT JOIN FETCH user.role rl LEFT JOIN FETCH rl.entitlements rent where user.username = ? and user.dataId <> 3",
				userName);
		session.flush();
		session.clear();
		session.close();
		return user;
	}

	public User findByUserNameIncludeBranch(String userName, String branchIdParm) {

		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);

		User user = null;
		user = (User) find(
				"from User user LEFT JOIN FETCH user.entitlements ent LEFT JOIN FETCH user.role rl LEFT JOIN FETCH rl.entitlements rent where user.username = ? AND user.branchId = ?",
				new Object[] { userName, branchIdParm });
		session.flush();
		session.clear();
		session.close();
		return user;

	}

	@Override
	public List<BranchMaster> listChildBranchIds(String parentBranch) {
		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Query query = session.createQuery("FROM BranchMaster hs WHERE hs.parentBranch=:parentBranch");
		query.setParameter("parentBranch", parentBranch);
		List<BranchMaster> list = query.list();
		session.flush();
		session.clear();
		session.close();
		return list;
	}

	@Override
	public ESESystem findESESystem() {
		ESESystem sys = (ESESystem) find("FROM ESESystem es WHERE es.id = ?", 1L);
		return sys;
	}

	@Override
	public Object[] findWebUserAgentInfoByProfileId(String profileId) {
		return null;
	}

	@Override
	public Role loadRoleMenus(long id) {

		Role role = (Role) find("From Role r INNER JOIN FETCH r.menus WHERE r.id = ?", id);
		return role;
	}

	@Override
	public List<Menu> listMenus() {

		List<Menu> menus = (List<Menu>) list("FROM Menu m WHERE m.parentId IS NULL ORDER BY m.order");
		return menus;
	}

	@Override
	public AccessLog findLatestAccessLog(String module, String user) {

		Object[] values = new Object[] { module, user };
		AccessLog log = (AccessLog) find(
				"FROM AccessLog al WHERE al.id = (SELECT MAX(id) FROM AccessLog) AND al.module = ? AND al.user = ?",
				values);
		return log;
	}

	@Override
	public void updateAccessLog(AccessLog accessLog) {
		Session sessions = getSessionFactory().openSession();
		sessions.update(accessLog);
		sessions.flush();
		sessions.close();

	}

	@Override
	public UptimeLog findLatestUptimeLog(String module) {

		UptimeLog log = (UptimeLog) find(
				"FROM UptimeLog ul WHERE ul.id = (SELECT MAX(id) FROM UptimeLog) AND ul.module = ?", module);
		return log;
	}

	@Override
	public DeploymentLog findLatestDeploymentLog(String module) {

		DeploymentLog log = (DeploymentLog) find(
				"FROM DeploymentLog dl WHERE dl.id = (SELECT MAX(id) FROM DeploymentLog) AND dl.module = ?", module);
		return log;
	}

	@Override
	public void saveDeploymentLog(DeploymentLog deploymentLog) {
		Session sessions = getSessionFactory().openSession();
		sessions.save(deploymentLog);
		sessions.flush();
		sessions.close();

	}

	@Override
	public void saveUptimeLog(UptimeLog uptimeLog) {
		Session sessions = getSessionFactory().openSession();
		sessions.save(uptimeLog);
		sessions.flush();
		sessions.close();

	}

	@Override
	public void updateUptimeLog(UptimeLog uptimeLog) {
		Session sessions = getSessionFactory().openSession();
		sessions.update(uptimeLog);
		sessions.flush();
		sessions.close();

	}

	@Override
	public List<Object[]> findParentBranches() {
		Object[] bindValues = { "" };
		return list(
				"SELECT br.branchId,br.name FROM BranchMaster br WHERE br.parentBranch IS NULL OR br.parentBranch=? ORDER BY br.name ASC",
				bindValues);
	}

	@Override
	public List<Object[]> listBranchMastersInfo() {
		return list("SELECT br.branchId,br.name FROM BranchMaster br ORDER BY br.name ASC");
	}

	@Override
	public List<Object[]> findChildBranches() {
		Object[] bindValues = { "" };
		return list(
				"SELECT br.branchId,br.name FROM BranchMaster br WHERE br.parentBranch IS NOT NULL OR br.parentBranch!=? ORDER BY br.name ASC",
				bindValues);
	}

	@Override
	public Object[] findBranchInfo(String branchId) {

		// TODO Auto-generated method stub
		return (Object[]) find("SELECT br.branchId,br.name FROM BranchMaster br WHERE br.branchId=?", branchId);
	}

	@Override
	public List<Object[]> listParentBranchMastersInfo() {
		return list(
				"SELECT br.branchId,br.parentBranch FROM BranchMaster br WHERE br.parentBranch IS NOT NULL ORDER BY br.name ASC");
	}

	public byte[] findLogoByCode(String code) {

		byte[] logo = (byte[]) find("SELECT a.file FROM Asset a WHERE a.code = ?", code);
		return logo;
	}

	@Override
	public List<LocaleProperty> listLocalePropByLang(String lang) {
		return list("FROM LocaleProperty lp where lp.langCode=?", lang);
	}

	@Override
	public String findCurrentSeasonName(String seasonCode) {
		Session session = currentSession();
		Query query = session.createQuery("SELECT hs.name from HarvestSeason hs where code=:seasonValue");
		query.setParameter("seasonValue", seasonCode);
		List<Object> list = query.list();
		Object object = ObjectUtil.isListEmpty(list) ? null : list.get(0);
		return ObjectUtil.isEmpty(object) ? "" : object.toString();
	}

	@Override
	public Integer isParentBranch(String loggedUserBranch) {
		Integer returnVal = 0;
		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Query query = session.createQuery("FROM BranchMaster hs WHERE hs.parentBranch=:parentBranch");
		query.setParameter("parentBranch", loggedUserBranch);
		List<BranchMaster> list = query.list();
		if (list.size() > 0) {
			returnVal = 1;
		}
		session.flush();
		session.clear();
		session.close();
		return returnVal;
	}

	@Override
	public List<LanguagePreferences> listLanguagePreferences() {
		// TODO Auto-generated method stub
		return list("from LanguagePreferences");
	}

	@Override
	public List<Object[]> listStates() {
		System.out.println("At 1821 *************");
		return list("select DISTINCT s.code,s.name,s.id FROM State s ORDER BY s.name ASC ");

	}

	@Override
	public List<Object[]> listLocalities() {
		// TODO Auto-generated method stub
		return list("select DISTINCT l.code,l.name,l.id FROM Locality l ORDER BY l.name ASC ");
	}

	@Override
	public List<Municipality> listMunicipality() {
		// TODO Auto-generated method stub
		return list("FROM Municipality m ORDER BY m.name ASC");
	}

	@Override
	public List<Object[]> listGramPanchayatIdCodeName() {
		return list("SELECT g.id,g.code,g.name FROM GramPanchayat g order by g.name asc");
	}

	@Override
	public List<Object[]> listVillageIdAndName() {
		// TODO Auto-generated method stub
		return list("SELECT v.id,v.code,v.name FROM Village v order by v.name asc");
	}

	@Override
	public List<Object[]> listProducts() {

		String queryString = "select product.NAME, GROUP_CONCAT(PRODUCT.UNIT SEPARATOR '~' ),product.ID,product.code,product.manufacture from product group by product.name";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery(queryString);
		List<Object[]> list = query.list();
		sessions.flush();
		sessions.close();
		return list;
	}

	@Override
	public List<Object[]> listCustomerIdAndName() {
		return list("SELECT DISTINCT c.id,c.customerName from Customer c where c.status=0");
	}

	@Override
	public List<Customer> listOfCustomers() {
		return list("FROM Customer c ORDER BY c.customerName");
	}

	@Override
	public Object[] findMenuInfo(long id) {
		// TODO Auto-generated method stub
		return (Object[]) find("SELECT m.id,m.label,m.url FROM Menu m WHERE m.id=?", id);
	}

	@Override
	public List<Object[]> listProcurmentGradeByVarityCode(String code) {
		// TODO Auto-generated method stub
		List<Object[]> result = list(
				"SELECT pp.code, pp.name FROM ProcurementProduct pp where pp.ProcurementVariety.code=?", code);
		return result;
	}

	@Override
	public List<Country> listCountries() {

		return list("FROM Country c ORDER BY c.name ASC");
	}

	@Override
	public void saveFarmerDynmaicList(List<FarmerDynamicFieldsValue> farmerDynamicFieldValuesList) {
		Session session = getSessionFactory().openSession();
		Transaction tx = (Transaction) session.beginTransaction();

		AtomicInteger index = new AtomicInteger();

		// Batch Insert Principle used here

		farmerDynamicFieldValuesList.forEach(empDetail -> {
			session.saveOrUpdate(empDetail);
			if (index.incrementAndGet() % 20 == 0) { // 20, same as the JDBC
														// batch size
				// flush a batch of inserts and release memory:
				session.flush();
				session.clear();
			}
		});
		((org.hibernate.Transaction) tx).commit();
		session.close();
	}

	public void updateFarmerDynmaicList(List<FarmerDynamicFieldsValue> farmerDynamicFieldValuesList, Long id) {
		Session deleteSession = getSessionFactory().openSession();
		Query query = deleteSession
				.createSQLQuery("DELETE FROM farmer_dynamic_field_value where REFERENCE_ID=" + id + "");
		int result = query.executeUpdate();
		deleteSession.flush();
		deleteSession.close();

		saveFarmerDynmaicList(farmerDynamicFieldValuesList);

	}

	@Override
	public DynamicReportConfig findReportById(String id) {
		return (DynamicReportConfig) find(
				"FROM DynamicReportConfig drc LEFT JOIN FETCH drc.dynmaicReportConfigFilters drcf LEFT JOIN FETCH drc.dynmaicReportConfigDetails drcd WHERE drc.id=? ORDER BY drcd.order ASC",
				Long.valueOf(id));
	}

	@Override
	public List<Object[]> listFDVSForFolloUp(String agentId, String folloRev) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FarmCatalogueMaster findFarmCatalogueMasterByName(String name) {
		// TODO Auto-generated method stub
		Object[] values = { name, ESEAccount.ACTIVE };
		return (FarmCatalogueMaster) find("FROM FarmCatalogueMaster fcm WHERE fcm.name=? and fcm.status=?", values);
	}

	@Override
	public List<FarmCatalogue> findFarmCatalougeByType(int type) {
		return list("FROM FarmCatalogue fc where fc.typez=? and fc.status='1'", type);
	}

	@Override
	public Language findLanguageByCode(String code) {

		// TODO Auto-generated method stub
		return (Language) find("FROM Language l WHERE l.code=?", code);
	}

	@Override
	public void updateUserLanguage(String userName, String lang) {

		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		Query query = session.createQuery("UPDATE User SET language= :lang WHERE username=:userName");
		query.setString("lang", lang);
		query.setString("userName", userName);
		query.executeUpdate();
		session.flush();
		session.close();
	}

	@Override
	public List<BranchMaster> listBranchMasters() {

		// TODO Auto-generated method stub
		Object[] value = { FarmCatalogue.ACTIVE };
		return list("FROM BranchMaster br where br.status=? ORDER BY br.name ASC", value);
	}

	public User findUserByProfileId(String profileId) {

		// TODO Auto-generated method stub
		User user = (User) find("from User user where user.username = ? and user.dataId <> 3", profileId);
		return user;
	}

	public User findUserByEmailId(String emailId) {

		User user = (User) find("FROM User us WHERE us.contInfo.email = ? and us.dataId <> 3", emailId);
		return user;
	}

	public void update(Object obj) {
		getHibernateTemplate().update(obj);

	}

	@Override
	public void editUser(User user) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(user);
	}

	@Override
	public boolean isDashboardUserEntitlementAvailable(String userName) {

		// String queryString = "SELECT u.ID FROM ese_user AS u INNER JOIN
		// ese_role_ent AS re ON re.ROLE_ID = u.ESE_ROLE_ID INNER JOIN ese_ent
		// AS e ON e.ID = re.ENT_ID WHERE u.USER_NAME = '"
		// + userName + "' AND e.NAME = 'dashboard.dashboard.list'";

		String hqlString = "SELECT u.id FROM User u INNER JOIN u.role.entitlements ets WHERE u.username = :uname AND ets.authority = :aut ";
		Session sessions = getSessionFactory().getCurrentSession();
		Query query = sessions.createQuery(hqlString);
		query.setParameter("uname", userName);
		query.setParameter("aut", Entitlement.DASHBOARD_ENTITLEMENT);
		List results = query.list();

		return (results.size() > 0) ? true : false;
	}

	@Override
	public boolean isEntitlavailoableforuser(String userName, String auth) {

		// String queryString = "SELECT u.ID FROM ese_user AS u INNER JOIN
		// ese_role_ent AS re ON re.ROLE_ID = u.ESE_ROLE_ID INNER JOIN ese_ent
		// AS e ON e.ID = re.ENT_ID WHERE u.USER_NAME = '"
		// + userName + "' AND e.NAME = 'dashboard.dashboard.list'";

		String hqlString = "SELECT u.id FROM User u INNER JOIN u.role.entitlements ets WHERE u.username = :uname AND ets.authority = :aut ";
		Session sessions = getSessionFactory().getCurrentSession();
		Query query = sessions.createQuery(hqlString);
		query.setParameter("uname", userName);
		query.setParameter("aut", auth);
		List results = query.list();

		return (results.size() > 0) ? true : false;
	}

	@Override
	public Integer findUserCount(Date sDate, Date eDate,Long id) {
		
		Session session = getSessionFactory().openSession();			
		//String q  = "select count(*) from User where createdDate BETWEEN :startDate AND :endDate and dataId<>3";
		String q  = "select count(*) from User where dataId<>3";
		if(id>0){
			  q+=" AND agroChDealer= "+id;
		}
		Query query = session.createQuery(q);
		//query.setParameter("startDate", sDate).setParameter("endDate", eDate);
				
		Integer l = ((Long) query.uniqueResult()).intValue();
		session.flush();
		session.close();
		return l;
	}

	@Override
	public Integer findUserCountByMonth(Date sDate, Date eDate) {

		Session session = getSessionFactory().openSession();
		Query query = session
				.createQuery("select count(*) from User where createdDate BETWEEN :startDate AND :endDate and dataId<>3");
		query.setParameter("startDate", sDate).setParameter("endDate", eDate);

		Integer val = ((Long) query.uniqueResult()).intValue();
		session.flush();
		session.close();
		return val;
	}

	@Override
	public Integer findMobileUserCount(Date sDate, Date eDate,Long id) {
		Session session = getSessionFactory().openSession();
		//String q= "Select count(*) from Agent s where createdDate BETWEEN :startDate AND :endDate and s.status=1";		
		String q= "Select count(*) from Agent s where s.status=1";			
		if(id>0){
			  q+=" AND s.exporter.id= "+id;
		}
		Query query = session.createQuery(q);
		//query.setParameter("startDate", sDate).setParameter("endDate", eDate);
				
		Integer l = ((Long) query.uniqueResult()).intValue();
		session.flush();
		session.close();
		return l;
	}

	@Override
	public Integer findMobileUserCountByMonth(Date sDate, Date eDate) {
		Session session = getSessionFactory().openSession();
		Query query = session
				.createQuery("select count(*) from Agent where createdDate BETWEEN :startDate AND :endDate and s.status=1");
		query.setParameter("startDate", sDate).setParameter("endDate", eDate);

		Integer val = ((Long) query.uniqueResult()).intValue();
		session.flush();
		session.close();
		return val;
	}

	@Override
	public Integer findDeviceCount(Date sDate, Date eDate,Long id) {
		Session session = getSessionFactory().openSession();
		//String q="select count(*) from Device where createdDate BETWEEN :startDate AND :endDate";
		String q="select count(*) from Device";
		if(id>0){
			  q+=" where agent.exporter.id= "+id;
		}
		Query query = session.createQuery(q);
		//query.setParameter("startDate", sDate).setParameter("endDate", eDate);
		Integer val = ((Long) query.uniqueResult()).intValue();
		session.flush();
		session.close();
		return val;
	}

	@Override
	public Integer findDeviceCountByMonth(Date sDate, Date eDate) {
		Session session = getSessionFactory().openSession();
		Query query = session
				.createQuery("select count(*) from Device where createdDate BETWEEN :startDate AND :endDate");
		query.setParameter("startDate", sDate).setParameter("endDate", eDate);
		Integer val = ((Long) query.uniqueResult()).intValue();
		session.flush();
		session.close();
		return val;
	}

	@Override
	public Integer findFacilitiesCount() {
		Session session = getSessionFactory().getCurrentSession();
		return ((Long) session.createQuery("select count(*) from Packhouse WHERE typez in (:type) and status=1")
				.setParameterList("type", new Object[] { Packhouse.COOPERATIVE, Packhouse.GINNER, Packhouse.SPINNING,
						Packhouse.PROCUREMENT_PLACE })
				.uniqueResult()).intValue();
	}

	@Override
	public Integer findWarehouseCount(Date sDate, Date eDate,Long id) {
		Session session = getSessionFactory().openSession();
		//String q="select count(*) from Packhouse WHERE  status=1  AND createdDate BETWEEN :startDate AND :endDate";
		String q="select count(*) from Packhouse WHERE  status=1";
		if(id>0){
			  q+=" AND exporter= "+id;
		}
		Query query = session.createQuery(q);
		
		//query.setParameter("startDate", sDate).setParameter("endDate", eDate);

		Integer val = ((Long) query.uniqueResult()).intValue();
		session.flush();
		session.close();
		return val;
				
	}

	@Override
	public Integer findWarehouseCountByMonth(Date sDate, Date eDate) {
		Session session = getSessionFactory().openSession();
		Query query = session.createQuery("select count(*) from Packhouse WHERE  status=1 and typez='"
				+ Packhouse.WarehouseTypes.COOPERATIVE.ordinal() + "' AND createdDate BETWEEN :startDate AND :endDate");
		query.setParameter("startDate", sDate).setParameter("endDate", eDate);

		Integer val = ((Long) query.uniqueResult()).intValue();
		session.flush();
		session.close();
		return val;
	}

	@Override
	public Integer findGroupCount() {
		Session session = getSessionFactory().getCurrentSession();
		return ((Long) session.createQuery(
				"select count(*) from Packhouse WHERE  status=1 and typez='" + Packhouse.WarehouseTypes.SAMITHI.ordinal() + "'")
				.uniqueResult()).intValue();
	}

	@Override
	public Integer findGroupCountByMonth(Date sDate, Date eDate) {
		Session session = getSessionFactory().openSession();
		Query query = session.createQuery("select count(*) from Packhouse WHERE  status=1 and typez='"
				+ Packhouse.WarehouseTypes.SAMITHI.ordinal() + "' AND createdDate BETWEEN :startDate AND :endDate");
		query.setParameter("startDate", sDate).setParameter("endDate", eDate);

		Integer val = ((Long) query.uniqueResult()).intValue();
		session.flush();
		session.close();
		return val;
	}

	@Override
	public List<Object> listWarehouseProductAndStockByWarehouseId(Long warehouseId) {
		Object[] values = { warehouseId };
		return list(
				"SELECT SUM(wp.stock),wp.product.name from PackhouseIncoming wp  WHERE wp.warehouse.id=? and  wp.status=1  GROUP BY wp.product order by wp.stock DESC",
				values);
	}

	@Override
	public List<Object> listWarehouseProductAndStock() {
		return list(
				"SELECT SUM(wp.stock),wp.product.name from PackhouseIncoming wp where wp.warehouse.id is not  and  wp.status=1  NULL GROUP BY wp.product");
	}

	@Override
	public BranchMaster findBranchMasterById(Long id) {

		BranchMaster branchMaster = (BranchMaster) find("FROM BranchMaster bm WHERE bm.id = ?", id);
		return branchMaster;
	}

	public List<Device> listDevices() {
		return list("FROM Device d where d.isRegistered=1");
	}

	@Override
	public List<Object> listcowMilkByMonth(Date sDate, Date eDate) {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(
				"select year(ci.currentInspDate),month(ci.currentInspDate),SUM(ci.totalMilkPerDay) from CowInspection ci where ci.currentInspDate BETWEEN :sDate AND :eDate group by year(ci.currentInspDate),month(ci.currentInspDate)");
		query.setParameter("sDate", sDate).setParameter("eDate", eDate);
		List<Object> resultSet = query.list();
		return resultSet;
	}

	@Override
	public List<Object> listCropSaleQtyByMoth(Date sDate, Date eDate, String selectedBranch) {
		Session session = getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<String, Object>();
		String sqlString = "SELECT YEAR(cs.DATE_SALE ),MONTH (cs.DATE_SALE ),sum(csd.QTY ) FROM CROP_SUPPLY cs INNER JOIN CROP_SUPPLY_DETAILS csd "
				+ "ON cs.ID = csd.CROP_SUPPLY_ID INNER JOIN farm fa on fa.FARM_CODE=cs.FARM_CODE INNER JOIN farmer f on f.FARMER_ID=cs.FARMER_ID "
				+ "WHERE cs.DATE_SALE BETWEEN :sDate AND :eDate AND fa.status=1 AND f.status=1 AND f.STATUS_CODE=0 GROUP BY YEAR (cs.DATE_SALE),MONTH (cs.DATE_SALE)";

		if (!StringUtil.isEmpty(selectedBranch)) {
			sqlString += " AND f.branch_Id =:branch";
			params.put("branch", selectedBranch);
		}
		params.put("sDate", sDate);
		params.put("eDate", eDate);
		SQLQuery query = session.createSQLQuery(sqlString);
		for (String str : query.getNamedParameters()) {
			query.setParameter(str, params.get(str));
		}

		List<Object> result = query.list();
		session.flush();
		session.close();
		return result;
	}

	@Override
	public List<Object> listCropHarvestByMoth(Date sDate, Date eDate, String selectedBranch) {
		Session session = getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<String, Object>();
		String sqlString = "select year(ch.HARVEST_DATE),month(ch.HARVEST_DATE),sum(chd.QTY) from CROP_HARVEST ch inner join CROP_HARVEST_DETAILS chd "
				+ "on ch.ID=chd.CROP_HARVEST_ID INNER JOIN farm fa ON fa.FARM_CODE = ch.FARM_CODE INNER JOIN farmer f "
				+ "ON f.FARMER_ID = ch.FARMER_ID where ch.HARVEST_DATE between  :sDate AND :eDate AND fa.status=1 AND f.status=1 AND f.STATUS_CODE=0 "
				+ "group by year(ch.HARVEST_DATE),month(ch.HARVEST_DATE)";

		if (!StringUtil.isEmpty(selectedBranch)) {
			sqlString += " AND f.branch_Id =:branch";
			params.put("branch", selectedBranch);
		}
		params.put("sDate", sDate);
		params.put("eDate", eDate);
		SQLQuery query = session.createSQLQuery(sqlString);
		for (String str : query.getNamedParameters()) {
			query.setParameter(str, params.get(str));
		}

		List<Object> result = query.list();
		session.flush();
		session.close();
		return result;
	}

	@Override
	public List<Object> listDistributionQtyByMoth(Date sDate, Date eDate, String selectedBranch) {
		Session session = getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<String, Object>();
		String sqlString = "select year(d.TXN_TIME),month(d.TXN_TIME),sum(dd.QUANTITY) from DISTRIBUTION d inner join DISTRIBUTION_DETAIL dd "
				+ "on d.id=dd.DISTRIBUTION_ID INNER JOIN farmer f "
				+ "ON f.FARMER_ID = d.FARMER_ID where d.TXN_TIME between  :sDate AND :eDate AND f.status=1 AND f.STATUS_CODE=0 "
				+ "group by year(d.TXN_TIME),month(d.TXN_TIME)";

		if (!StringUtil.isEmpty(selectedBranch)) {
			sqlString += " AND f.branch_Id =:branch";
			params.put("branch", selectedBranch);
		}
		params.put("sDate", sDate);
		params.put("eDate", eDate);
		SQLQuery query = session.createSQLQuery(sqlString);
		for (String str : query.getNamedParameters()) {
			query.setParameter(str, params.get(str));
		}

		List<Object> result = query.list();
		session.flush();
		session.close();
		return result;
	}

	@Override
	public List<Object> listProcurementAmtByMoth(Date sDate, Date eDate, String selectedBranch) {

		Session session = getSessionFactory().openSession();
		Map<String, Object> params = new HashMap<String, Object>();
		String sqlString = "select year(proc.created_Date),month(proc.created_date),SUM(proc.TOTAL_AMOUNT),f.fpo as fpo FROM Procurement  proc inner join "
				+ "farmer f on f.id=proc.FARMER_ID where proc.created_date BETWEEN :sDate AND :eDate AND f.status=1 AND f.STATUS_CODE=0 "
				+ " group by year(proc.created_Date),month(proc.created_Date)";

		if (!StringUtil.isEmpty(selectedBranch)) {
			sqlString += " AND f.branch_Id =:branch";
			params.put("branch", selectedBranch);
		}
		params.put("sDate", sDate);
		params.put("eDate", eDate);
		SQLQuery query = session.createSQLQuery(sqlString);
		for (String str : query.getNamedParameters()) {
			query.setParameter(str, params.get(str));
		}

		List<Object> result = query.list();
		session.flush();
		session.close();
		return result;
	}

	@Override
	public List<Object> listEnrollmentByMoth(Date sDate, Date eDate) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(
				"select year(f.createdDate),month(f.createdDate),COUNT(f) FROM Farmer f WHERE f.statusCode=0 AND f.status=1 AND f.createdDate between :sDate AND :eDate group by year(f.createdDate),month(f.createdDate)");
		query.setParameter("sDate", sDate).setParameter("eDate", eDate);
		List<Object> resultSet = query.list();
		return resultSet;
	}

	public List<Village> listVillagesByCityId(long selectedCity) {

		return list("FROM Village v WHERE v.city.id = ? ORDER BY v.name ASC", selectedCity);

	}

	@Override
	public List<Village> listVillageByBranch(String selectedBranch, Long selectedState) {
		Object[] values = { selectedBranch, selectedState };
		if (StringUtil.isEmpty(selectedBranch)) {
			return list("FROM Village v where v.city.locality.state.id = ? group by v.name ORDER BY v.name ASC",
					selectedState);

		} else {
			return list("FROM Village v where v.branchId in ? AND v.city.locality.state.id = ? ORDER BY v.name ASC",
					values);

		}
	}

	@Override
	public List<String> findBranchList() {
		// TODO Auto-generated method stub
		return list("Select b.name from BranchMaster b");
	}

	@Override
	public HarvestSeason findSeasonByCode(String code) {
		HarvestSeason harvestSeason = (HarvestSeason) find("FROM HarvestSeason hs WHERE hs.code=?", code);
		return harvestSeason;
	}

	@Override
	public List<State> listOfStatesByBranch(String selectedBranch) {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(selectedBranch)) {
			return list("FROM State st group by st.name ORDER BY st.name ASC");

		} else {
			return list("FROM State st where st.branchId in ? ORDER BY st.name ASC", selectedBranch);

		}

	}

	@Override
	public List<Object[]> findCatalogueCodeAndDisNameByType(Integer type) {
		// TODO Auto-generated method stub
		return list(
				"SELECT fc.code,fc.name,fc.dispName FROM  FarmCatalogue fc WHERE fc.typez=? ORDER BY fc.dispName ASC",
				type);
	}

	@Override
	public List<Object[]> findProcurementDataByFilter(Long selectedProduct, String selectedDate) {
		Session sessions = getSessionFactory().openSession();
		String queryString = "";
		List<Object[]> list = new ArrayList<>();
		if (!StringUtil.isEmpty(selectedProduct) && selectedProduct > 0L && !StringUtil.isEmpty(selectedDate)) {
			String[] dateSplit = selectedDate.split("-");
			Date sDate = DateUtil.convertStringToDate(dateSplit[0], DateUtil.DATE_FORMAT);
			Date eDate = DateUtil.convertStringToDate(dateSplit[1], DateUtil.DATE_FORMAT);

			queryString = "SELECT SUM(pd.numberOfBags),SUM(pd.NetWeight),SUM(pd.subTotal) FROM Procurement p INNER JOIN p.procurementDetails pd WHERE pd.procurementProduct.id=:selectedProduct AND p.createdDate BETWEEN :sDate AND :eDate";
			Query query = sessions.createQuery(queryString);
			query.setParameter("selectedProduct", selectedProduct);
			query.setParameter("sDate", sDate);
			query.setParameter("eDate", eDate);

			list = query.list();

		} else if (!StringUtil.isEmpty(selectedProduct) && selectedProduct > 0L) {

			queryString = "SELECT SUM(pd.numberOfBags),SUM(pd.NetWeight),SUM(pd.subTotal) FROM Procurement p INNER JOIN p.procurementDetails pd WHERE pd.procurementProduct.id=:selectedProduct";
			Query query = sessions.createQuery(queryString);
			query.setParameter("selectedProduct", selectedProduct);
			list = query.list();

		} else if (!StringUtil.isEmpty(selectedDate)) {

			String[] dateSplit = selectedDate.split("-");
			Date sDate = DateUtil.convertStringToDate(dateSplit[0], DateUtil.DATE_FORMAT);
			Date eDate = DateUtil.convertStringToDate(dateSplit[1], DateUtil.DATE_FORMAT);
			queryString = "SELECT SUM(pd.numberOfBags),SUM(pd.NetWeight),SUM(pd.subTotal) FROM Procurement p INNER JOIN p.procurementDetails pd WHERE p.createdDate BETWEEN :sDate AND :eDate";
			Query query = sessions.createQuery(queryString);
			query.setParameter("sDate", sDate);
			query.setParameter("eDate", eDate);
			list = query.list();

		}
		sessions.flush();
		sessions.close();
		return list;
	}

	@Override
	public List<Object[]> findProcurementCummulativeData() {
		String queryString = "SELECT SUM(pd.numberOfBags),SUM(pd.NetWeight),SUM(pd.subTotal) FROM Procurement p INNER JOIN p.procurementDetails pd";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createQuery(queryString);
		List<Object[]> list = query.list();
		sessions.flush();
		sessions.close();
		return list;
	}

	public Role findRole(long id) {

		Role role = (Role) find("FROM Role r WHERE r.id = ?", id);
		return role;
	}

	public BranchMaster findBranchMasterByBranchIdAndDisableFilter(String branchId) {

		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Query query = session.createQuery("FROM BranchMaster bm WHERE bm.branchId = :branchId");
		query.setParameter("branchId", branchId);
		BranchMaster branchMaster = (BranchMaster) query.list().get(0);
		session.flush();
		session.close();
		return branchMaster;
	}

	public List<Role> listRoles() {

		return list("FROM Role ORDER BY name ASC");
	}

	public List<Menu> listTopParentMenus() {

		List<Menu> menus = (List<Menu>) list("FROM Menu m where m.parentId = null");
		return menus;
	}

	@Override
	public List<Role> listRolesByTypeAndBranchId(String branchId) {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(branchId)) {
			List<Role> availableRoles = list("FROM Role r WHERE  r.branchId IS NULL ORDER BY r.name ASC");
			return availableRoles;
		} else {
			List<Role> availableRoles = list("FROM Role r WHERE r.branchId = ? ORDER BY r.name ASC", branchId);
			return availableRoles;
		}

	}

	public List<Menu> listFlatSubMenusForTopParent(long id) {

		Object[] values = { id, id };
		List<Menu> menus = (List<Menu>) list("FROM Menu m WHERE m.parentId IN "
				+ "(SELECT cm.id FROM Menu cm WHERE cm.parentId = ?) OR m.parentId = ? "
				+ "AND m.url !='javascript:void(0)'", values);
		return menus;
	}

	@Override
	public Role findRoleExcludeBranch(long selectedRole) {
		Role returnVal = new Role();
		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Query query = session.createQuery("FROM Role where id = :selectedRole");
		query.setParameter("selectedRole", selectedRole);
		List<Role> list = query.list();
		if (list.size() > 0) {
			returnVal = list.get(0);
		}
		session.flush();
		session.clear();
		session.close();
		return returnVal;
	}

	@Override
	public Object[] findRoleInfo(long id) {
		// TODO Auto-generated method stub
		Object[] roleInfo = (Object[]) find("SELECT r.id,r.name,r.branchId FROM Role r WHERE r.id = ?", id);
		return roleInfo;
	}

	public Menu findMenu(long id) {

		Menu menu = (Menu) find("FROM Menu m WHERE m.id = ?", id);
		return menu;
	}

	public List<Action> listAction() {

		return (List<Action>) list("FROM Action a ORDER BY a.id ASC");
	}

	public Entitlement findEntitlement(String name) {

		Entitlement ent = (Entitlement) find("FROM Entitlement e WHERE e.authority = ?", name);
		return ent;
	}

	public BranchMaster findBranchMasterByName(String name) {

		BranchMaster branchMaster = (BranchMaster) find("FROM BranchMaster bm WHERE bm.name = ?", name);
		return branchMaster;
	}

	public Role findRoleByName(String name) {

		Role role = (Role) find("FROM Role r WHERE r.name = ?", name);
		return role;
	}

	public List<GramPanchayat> listGramPanchayatsByCityId(long selectedCity) {

		return list("FROM GramPanchayat gp WHERE gp.city.id = ? ORDER BY gp.name ASC", selectedCity);
	}

	public List<Municipality> listMunicipalitiesByLocalityId(long selectedLocality) {

		return list("FROM Municipality m WHERE m.locality.id = ? ORDER BY m.name ASC", selectedLocality);
	}

	public List<Locality> findLocalityByStateId(long selectedState) {

		return list("FROM Locality l WHERE l.state.id = ? ORDER BY l.name ASC", selectedState);
	}

	public List<State> listStates(String country) {

		return list("FROM State s WHERE s.country.name = ? ORDER BY s.name ASC", country);
	}

	public List<Object[]> findLocalityByBranch(String Branch) {

		// return list("FROM Locality l WHERE l.branchId = ?", Branch);
		return list("SELECT l.id,l.code,l.name FROM Locality l WHERE l.code = ? order by l.name asc", Branch);
	}

	@Override
	public List<Object[]> listLocalityIdCodeAndName() {
		return list("SELECT l.id,l.code,l.name FROM Locality l order by l.name asc");
	}

	@Override
	public List<Object[]> findFarmerCountBySamithiId() {
		// TODO Auto-generated method stub
		Session sessions = getSessionFactory().openSession();
		String queryString = "select f.SAMITHI_ID,count(distinct(f.id)),ROUND(sum(fdi.PROPOSED_PLANTING_AREA),2) from farmer f left join farm fa on fa.FARMER_ID =f.ID left join farm_detailed_info fdi on fdi.ID=fa.FARM_DETAILED_INFO_ID where  f.`STATUS`=1 and fa.`STATUS`=1  GROUP BY SAMITHI_ID";
		Query query = sessions.createSQLQuery(queryString);
		List<Object[]> array = query.list();
		sessions.flush();
		sessions.close();
		return array;
	}

	@Override
	public List<Object[]> listVillageByPanchayatId(Long id) {
		return list("SELECT v.id,v.code,v.name FROM Village v WHERE v.gramPanchayat.id=? ORDER BY v.name ASC", id);
	}

	@Override
	public List<Object[]> listGramPanchayatByCityId(Long id) {
		return list("SELECT g.id,g.code,g.name FROM GramPanchayat g WHERE g.city.id=? ORDER BY g.name ASC", id);
	}

	@Override
	public List<Object[]> listCityCodeAndNameByDistrictId(Long id) {
		return list("SELECT m.id,m.code,m.name FROM Municipality m WHERE m.locality.id=? ORDER BY m.name ASC", id);
	}

	public List listCity() {

		return list("FROM Municipality m ORDER BY m.name ASC");
	}

	public List<Village> listVillageByCity(long id) {

		return list("FROM Village v WHERE v.city.id = ?", id);
	}

	public boolean findFarmerMappedWithSamithi(long id) {

		boolean isFarmerMappedWithSamithi = false;
		Session sessions = getSessionFactory().openSession();
		String queryStrings = "SELECT SAMITHI_ID FROM FARMER WHERE STATUS<>2 AND SAMITHI_ID = " + id;
		Query querys = sessions.createSQLQuery(queryStrings);
		List results = querys.list();
		if (results.size() > 0) {
			isFarmerMappedWithSamithi = true;
		}
		sessions.flush();
		sessions.close();
		return isFarmerMappedWithSamithi;
	}

	public Village findVillage(long id) {

		return (Village) find(
				"From Village v LEFT JOIN FETCH v.city c LEFT JOIN FETCH c.locality l LEFT JOIN FETCH l.state s LEFT JOIN FETCH s.country c WHERE v.id=? ",
				id);
	}

	public Integer findFarmerCountBySamtihi(Long id) {
		Session session = getSessionFactory().getCurrentSession();
		return ((Long) session.createQuery("select count(f.id) from Farmer f where f.samithi.id=:id")
				.setParameter("id", id).uniqueResult()).intValue();
	}

	@Override
	public List<BankInfo> findWarehouseBankinfo(long id) {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		// Query query = session.createQuery("SELECT bi from Farmer fr INNER
		// JOIN fr.bankInfo bi WHERE fr.id=:ID");
		Query query = session.createQuery("SELECT bi from Packhouse w INNER JOIN w.bankInfo bi WHERE w.id=:ID");
		query.setParameter("ID", id);
		List list = query.list();
		session.flush();
		session.close();
		return list;
	}

	public List<IdentityType> listIdentityType() {

		return list("From IdentityType it ORDER BY it.name ASC");
	}

	public AgentType findAgentTypeById(long id) {

		return (AgentType) find("From AgentType WHERE id=?", id);
	}

	public Agent findAgent(long id) {

		// Agent agent = (Agent) find("FROM Agent a left join fetch a.surveys
		// WHERE a.id = ?", id);
		Agent agent = (Agent) find("FROM Agent a WHERE a.id = ?", id);

		return agent;
	}

	public List<AgentType> listAgentType() {

		return list("FROM AgentType");
	}

	public void updateFarmerIdSequence() {

		String qs1 = "select FARMER_ID from farmer";

		Session sessions = getSessionFactory().openSession();

		Query query = sessions.createSQLQuery(qs1);
		List list = query.list();
		for (int i = 0; i < list.size(); i++) {

			String oldID = list.get(i).toString();
			String newID = StringUtil.appendZeroPrefix(String.valueOf(i + 1), 6);

			String qs2 = "UPDATE trxn_agro SET farmer_id = '" + newID + "' where farmer_id = '" + oldID + "'";
			Query querys = sessions.createSQLQuery(qs2);
			int results = querys.executeUpdate();

			String qs3 = "UPDATE ese_account SET profile_id = '" + newID + "' where profile_id = '" + oldID + "'";
			querys = sessions.createSQLQuery(qs3);
			results = querys.executeUpdate();

			String qs4 = "UPDATE farmer SET farmer_id = '" + newID + "' where farmer_id = '" + oldID + "'";
			querys = sessions.createSQLQuery(qs4);
			results = querys.executeUpdate();

			String qs5 = "UPDATE farmer SET farmer_id = '" + newID + "' where farmer_id = '" + oldID + "'";
			querys = sessions.createSQLQuery(qs5);
			results = querys.executeUpdate();
		}

		sessions.flush();
		sessions.close();

	}

	public void updateShopDealerIdSequence() {

		String qs1 = "select SHOP_DEALER_ID from shop_dealer";

		Session sessions = getSessionFactory().openSession();

		Query query = sessions.createSQLQuery(qs1);
		List list = query.list();
		for (int i = 0; i < list.size(); i++) {

			String oldID = list.get(i).toString();
			String newID = StringUtil.appendZeroPrefix(String.valueOf(i + 1), 6);

			String qs2 = "update inventory set SHOP_DEALER_ID = 'S" + newID + "' where SHOP_DEALER_ID = '" + oldID
					+ "'";
			Query querys = sessions.createSQLQuery(qs2);
			int results = querys.executeUpdate();

			String qs3 = "update shop_dealer set SHOP_DEALER_ID = 'S" + newID + "' where SHOP_DEALER_ID = '" + oldID
					+ "'";
			querys = sessions.createSQLQuery(qs3);
			results = querys.executeUpdate();
		}

		sessions.flush();
		sessions.close();

	}

	public List<String> listWarehouseNameByServicePointId(long id) {

		Session session = getSessionFactory().openSession();
		String queryString = "SELECT" + " w.name " + " FROM" + " warehouse w" + " LEFT JOIN city c ON c.id = w.CITY_ID"
				+ " LEFT JOIN serv_point sp ON sp.CITY_ID = c.id"
				+ " WHERE w.id NOT IN (SELECT awm.warehouse_id FROM agent_warehouse_map awm)" + " AND sp.id=" + id;
		Query query = session.createSQLQuery(queryString);
		List list = query.list();
		session.flush();
		session.close();
		return list;
	}

	public List<String> listWarehouseNameByAgentIdAndServicePointId(long agentId, long servicePointId) {

		Session session = getSessionFactory().openSession();
		String queryString = "SELECT" + " w.name" + " FROM" + " warehouse w"
				+ " LEFT JOIN agent_warehouse_map awm ON awm.warehouse_id = w.id"
				+ " left join serv_point sp on sp.city_id=w.city_id" + " WHERE awm.agent_id = " + agentId
				+ " and sp.id=" + servicePointId;
		Query query = session.createSQLQuery(queryString);
		List list = query.list();
		session.flush();
		session.close();
		return list;
	}

	public List<String> listSelectedSamithiById(Long agentId) {

		Session session = getSessionFactory().openSession();
		String queryString = "SELECT w.name from warehouse w LEFT JOIN agent_warehouse_map awm ON awm.WAREHOUSE_ID = w.id WHERE w.typez=1 AND awm.AGENT_ID="
				+ agentId;
		Query query = session.createSQLQuery(queryString);
		List list = query.list();
		session.flush();
		session.close();
		return list;
	}

	public List<String> listAvailableSamithi() {

		Session session = getSessionFactory().openSession();
		String queryString = "SELECT NAME FROM Packhouse samithi  WHERE samithi.typez=1  ORDER BY samithi.name ASC";
		Query query = session.createSQLQuery(queryString);
		List list = query.list();
		session.flush();
		session.close();
		return list;
	}

	public List<String> listSelectedSamithiByAgentId(Long agentId, Long cooperativeId) {

		Session session = getSessionFactory().openSession();
		String queryString = "SELECT w.name from warehouse w LEFT JOIN agent_warehouse_map awm ON awm.WAREHOUSE_ID = w.id WHERE awm.AGENT_ID="
				+ agentId + " AND w.REF_WAREHOUSE_ID=" + cooperativeId;
		Query query = session.createSQLQuery(queryString);
		List list = query.list();
		session.flush();
		session.close();
		return list;
	}

	public List<String> listAvailableSamithiByAgentId(Long agentId, Long cooperativeId) {

		Session session = getSessionFactory().openSession();
		String queryString = "SELECT w.name from warehouse w WHERE w.REF_WAREHOUSE_ID is not NULL AND w.ID NOT IN (SELECT awm.WAREHOUSE_ID FROM agent_warehouse_map awm WHERE awm.AGENT_ID="
				+ agentId + ") AND w.REF_WAREHOUSE_ID=" + cooperativeId + " ";
		Query query = session.createSQLQuery(queryString);
		List list = query.list();
		session.flush();
		session.close();
		return list;
	}

	public List<String> listSamithiByCooperativeId(long id) {

		Session session = getSessionFactory().openSession();
		String queryString = "SELECT w.name FROM warehouse w where w.REF_WAREHOUSE_ID=" + id;
		Query query = session.createSQLQuery(queryString);
		List list = query.list();
		session.flush();
		session.close();
		return list;
	}

	public ServicePoint findServicePointByName(String selectedServicePoint) {

		ServicePoint servicePoint = (ServicePoint) find("FROM ServicePoint es WHERE es.name = ?", selectedServicePoint);
		return servicePoint;
	}

	public List<String> findServiceLocationBasedOnServicePointAndAgent(long id) {
		Session session = getSessionFactory().openSession();
		String queryString = "SELECT SERV_LOC.NAME from SERV_LOC WHERE SERV_LOC.ID NOT IN (SELECT AGENT_SERV_LOC_MAP.SERV_LOC_ID FROM AGENT_SERV_LOC_MAP)AND SERV_LOC.SERV_POINT_ID='"
				+ id + "'";
		Query query = session.createSQLQuery(queryString);
		List list = query.list();
		session.flush();
		session.close();
		return list;
	}

	public ServicePoint findServicePointByCode(String code) {

		return (ServicePoint) find("FROM ServicePoint sp WHERE sp.code = ?", code);
	}

	public List<Municipality> findMunicipalitiesByPostalCode(String postalCode) {

		return list("FROM Municipality m WHERE m.postalCode = ?", postalCode);
	}

	public ESECard findESECardByProfile(String profileId) {

		return (ESECard) find("FROM ESECard c WHERE c.profileId = ?", profileId);
	}

	public List<ServicePoint> list() {

		return (List<ServicePoint>) list("FROM ServicePoint sp ORDER BY sp.name ASC");
	}

	public List<Municipality> listMunicipalities(String locality) {

		return list("FROM Municipality m WHERE m.locality.code = ? ORDER BY m.name ASC", locality);
	}

	public List<Locality> listLocalities(String state) {

		return list("FROM Locality l WHERE l.state.code = ? ORDER BY l.name ASC", state);
	}

	public boolean findAgentIdFromEseTxn(String profileId) {

		List<ESETxnHeader> txnHeaderList = list("From ESETxnHeader eth where eth.agentId=?", profileId);
		boolean isTxnHeaderExist = false;
		if (txnHeaderList.size() > 0) {
			isTxnHeaderExist = true;
		}
		return isTxnHeaderExist;
	}

	public User findUserByUserNameExcludeBranch(String userName) {
		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Query query = session.createQuery("From User where dataId!=3 and username=:userName");
		query.setParameter("userName", userName);
		List list = query.list();
		User user = null;
		if (list.size() > 0) {
			user = (User) query.list().get(0);
		}
		session.flush();
		session.close();
		return user;
	}

	@Override
	public User findUserByNameAndBranchId(String username, String branchId) {
		// TODO Auto-generated method stub
		User user = null;
		if (StringUtil.isEmpty(branchId)) {
			user = (User) find("from User user where user.dataId!=3 and user.username = ? AND user.branchId IS NULL",
					username);
		} else {
			user = (User) find("from User user where user.dataId!=3 and user.username = ? AND user.branchId = ?",
					new Object[] { username, branchId });
		}
		return user;
	}

	public boolean findAgentIdFromDevice(long id) {

		List<Device> device = list("From Device dev where dev.agent.id=?", id);
		boolean isDeviceExist = false;
		if (device.size() > 0) {
			isDeviceExist = true;
		}
		return isDeviceExist;
	}

	public boolean isAgentWarehouseProductStockExist(String profileId) {

		Long warehoseProductCount = (Long) find(
				"SELECT Count(wp) From PackhouseIncoming wp WHERE wp.agent.profileId=? AND wp.stock>0", profileId);
		if (warehoseProductCount > 0) {
			return true;
		}
		return false;
	}

	public boolean isAgentDistributionExist(String profileId) {
		Long distributionCount = (Long) find("SELECT Count(d) From Distribution d WHERE d.agentId=? AND d.txnType=514",
				profileId);
		// Long distributionCount = (Long) find("SELECT Count(d) From
		// AgentAccessLogDetail d Where d.agentAccessLog.profileId=? AND
		// d.txnType=314",profileId);
		if (distributionCount > 0) {
			return true;
		}
		return false;
	}

	public List<CityWarehouse> listProcurementStocksForAgent(String agentId) {

		return (List<CityWarehouse>) (list(
				"FROM CityWarehouse cw WHERE cw.numberOfBags > 0 AND cw.grossWeight> 0  AND cw.agentId=?", agentId));
	}

	@SuppressWarnings("unchecked")
	public List<PackhouseIncoming> listWarehouseProductByAgentId(long agentId) {

		return list("From PackhouseIncoming wp WHERE wp.agent.id = ? ", agentId);
	}

	public void removeCardByProfileId(String profileId) {
		Session session = getSessionFactory().openSession();
		Query query = session.createQuery("delete ESECard where profileId = :profileId");
		query.setParameter("profileId", profileId);
		int result = query.executeUpdate();
		session.flush();
		session.close();
	}

	public void removeAccountByProfileIdAndProfileType(String profileId, int type) {

		Session session = getSessionFactory().openSession();
		Query query = session.createQuery("delete ESEAccount where profileId = :profileId AND type = :type");
		query.setParameter("profileId", profileId);
		query.setParameter("type", type);
		int result = query.executeUpdate();
		session.flush();
		session.close();

	}

	public void updateAccountStatus(String profileId, int status, int type) {

		Session session = getSessionFactory().openSession();
		Query query = session.createQuery(
				"update ESEAccount set status = :status" + " where profileId = :profileId AND type = :type");
		query.setParameter("status", status);
		query.setParameter("profileId", profileId);
		query.setParameter("type", type);
		int result = query.executeUpdate();
		session.flush();
		session.close();

	}

	public void removeAgentWarehouseMappingByAgentId(long id) {

		Session sessions = getSessionFactory().openSession();
		String queryStrings = "DELETE FROM AGENT_WAREHOUSE_MAP WHERE AGENT_ID = " + id;
		Query querys = sessions.createSQLQuery(queryStrings);

		int results = querys.executeUpdate();
		sessions.flush();
		sessions.close();
	}

	public Agent findAgent(String profileId) {

		Agent agent = (Agent) find("from Agent agent where agent.id = ?", profileId);
		return agent;
	}

	public void updateCardStatus(String profileId, int status, int cardRewritable) {

		Session session = getSessionFactory().openSession();
		Query query = session.createQuery("update ESECard set status = :status,cardRewritable = :cardRewritable"
				+ " where profileId = :profileId");
		query.setParameter("status", status);
		query.setParameter("profileId", profileId);
		query.setParameter("cardRewritable", cardRewritable);
		int result = query.executeUpdate();
		session.flush();
		session.close();
	}

	@Override
	public Agent findAgentByAgentIdd(String profileId) {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Query query = session.createQuery("From Agent agent where agent.profileId=:agentId");
		query.setParameter("agentId", profileId);
		Agent agent = null;
		if (query.list().size() > 0) {
			return (Agent) query.list().get(0);
		}
		session.flush();
		session.close();
		return agent;
	}

	@Override
	public List<Role> listRolesByTypeAndBranchIdExcludeBranch(String branchId) {
		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		String queryStr = "";
		List list;
		Query query;
		List<Role> user = new ArrayList<>();
		if (StringUtil.isEmpty(branchId)) {
			queryStr = "FROM Role r WHERE r.branchId IS NULL ORDER BY r.name ASC";
			query = session.createQuery(queryStr);

			user = query.list();
		} else {
			queryStr = "FROM Role r WHERE  r.branchId = :branch ORDER BY r.name ASC";
			query = session.createQuery(queryStr);

			query.setParameter("branch", branchId);
			user = query.list();

		}

		session.flush();
		session.close();
		return user;
	}

	public List<Role> listRolesByType(int type) {

		List<Role> availableRoles = list("FROM Role r WHERE r.filter.id = ?", type);
		return availableRoles;
	}

	public User findUser(long id) {

		User user = (User) find("from User user where user.id = ?", id);
		return user;
	}

	@Override
	public User findByUsernameAndBranchId(String username, String branchId) {

		// TODO Auto-generated method stub
		Session session = getSessionFactory().getCurrentSession();
		org.hibernate.Filter branchFilter = session.getEnabledFilter(ISecurityFilter.BRANCH_FILTER);
		User user = null;
		if (!ObjectUtil.isEmpty(branchFilter)) {
			session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		}
		if (StringUtil.isEmpty(branchId)) {
			user = (User) find(
					"from User user LEFT JOIN FETCH user.entitlements ent LEFT JOIN FETCH user.role rl LEFT JOIN FETCH rl.entitlements rent where user.username = ? AND user.branchId IS NULL",
					username);
		} else {

			user = (User) find(
					"from User user LEFT JOIN FETCH user.entitlements ent LEFT JOIN FETCH user.role rl LEFT JOIN FETCH rl.entitlements rent where user.username = ? AND user.branchId = ?",
					new Object[] { username, branchId });
		}
		return user;
	}

	@Override
	public byte[] findUserImage(long userId) {

		// TODO Auto-generated method stub
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT u.personalInfo.image FROM User u WHERE u.id=:userID");
		query.setLong("userID", userId);
		return (byte[]) query.uniqueResult();
	}

	@Override
	public Device findDeviceById(Long id) {
		Session session = getSessionFactory().getCurrentSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Query query = session.createQuery("FROM Device di WHERE di.id = :id AND di.deleted = '0'");
		query.setParameter("id", id);
		return (Device) query.uniqueResult();
	}

	public List<Agent> listAgentByAgentTypeNotMappedwithDevice() {

		List<Agent> agent = list(
				"FROM Agent a WHERE a.id NOT IN(SELECT d.agent.id FROM Device d WHERE d.agent.id IS NOT NULL) ");
		return agent;
	}

	@Override
	public Device findUnRegisterDeviceById(Long id) {
		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Device device = (Device) session.get(Device.class, id);
		session.flush();
		session.close();
		return device;
	}

	public Asset findAssetsByAssetCode(String code) {

		Asset asset = (Asset) find("FROM Asset ast WHERE ast.code = ?", code);
		return asset;
	}

	public BranchMaster findBranchMasterByIdAndDisableFilter(Long id) {

		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Query query = session.createQuery("FROM BranchMaster bm WHERE bm.id = :id");
		query.setParameter("id", id);
		BranchMaster branchMaster = (BranchMaster) query.list().get(0);
		session.flush();
		session.close();
		return branchMaster;
	}

	public List<BranchMaster> listBranchMastersAndDisableFilter() {

		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		Query query = session.createQuery("FROM BranchMaster br ORDER BY br.name ASC");
		List<BranchMaster> list = query.list();

		session.flush();
		session.close();

		return list;
	}

	@Override
	public List<Object> listFieldsByViewNameAndDBName(String dbName, String viewName) {
		Session session = getSessionFactory().openSession();
		String queryString = "select column_name from information_schema.columns where table_name='" + viewName
				+ "' and table_schema='" + dbName + "'";
		Query query = session.createSQLQuery(queryString);

		List<Object> list = query.list();
		session.flush();
		session.close();
		return list;
	}

	@Override
	public List<Object[]> listOfViewByDBName(String dbName) {
		Session session = getSessionFactory().openSession();
		String queryString = "SHOW FULL TABLES IN " + dbName + " WHERE TABLE_TYPE LIKE 'VIEW'";
		Query query = session.createSQLQuery(queryString);

		List<Object[]> list = query.list();
		session.flush();
		session.close();
		return list;
	}

	@Override
	public List<Language> findIsSurveyStatusLanguage() {
		// TODO Auto-generated method stub
		return list("FROM Language l where l.surveyStatus=1 ORDER BY l.code ASC");

	}

	@Override
	public FarmCatalogue findCatalogueByNameAndType(String name, int typez) {
		return (FarmCatalogue) find("FROM FarmCatalogue cg WHERE cg.name = ? and cg.typez=?",
				new Object[] { name, typez });
	}

	@Override
	public FarmCatalogueMaster findFarmCatalogueMasterByCatalogueTypez(Integer valueOf) {
		List<Integer> intList = new ArrayList<>();
		intList.add(ESEAccount.ACTIVE);
		intList.add(FarmCatalogueMaster.STATUS_SURVEY);

		Session session = getSessionFactory().openSession();
		Query query = session.createQuery("FROM FarmCatalogueMaster fm WHERE fm.typez=:type and fm.status in (:vals) ");
		query.setParameter("type", valueOf);
		query.setParameterList("vals", intList);
		try {
			FarmCatalogueMaster codeList = (FarmCatalogueMaster) query.list().get(0);
			session.flush();
			session.close();
			return codeList;
		} catch (Exception e) {
			session.flush();
			session.close();
			return null;
		}
	}

	@Override
	public List<FarmCatalogueMaster> listFarmCatalogueMatsters() {
		return list("FROM FarmCatalogueMaster fc where fc.status=? ORDER BY fc.name", ESEAccount.ACTIVE);
	}

	@Override
	public List<LanguagePreferences> listLangPrefByCode(String code) {
		// TODO Auto-generated method stub
		return list("FROM LanguagePreferences lp WHERE lp.code=?", code);
	}

	public String findVillageNameByCode(String code) {

		return (String) find("SELECT v.name FROM Village v WHERE v.code=?", code);
	}

	public String findCityNameByCode(String code) {

		return (String) find("SELECT m.name FROM Municipality m WHERE m.code=?", code);
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> listOfVillageCodeNameByCityCode(String cityCode) {

		return list("SELECT v.code, v.name FROM Village v WHERE v.gramPanchayat.city.code=?", cityCode);
	}

	@Override
	public FarmCatalogue findByNameAndType(String educationName, Integer typez) {
		Object[] values = { educationName, typez };
		return (FarmCatalogue) find("FROM FarmCatalogue cg WHERE cg.name = ? and typez=? ", values);
	}

	@Override
	public Village findVillageBySelectedVillageId(long selectedVillage) {
		return (Village) find("FROM Village v WHERE v.id= ? ", selectedVillage);

	}

	public Municipality findMunicipalityByCode(String code) {

		return (Municipality) find("FROM Municipality m WHERE m.code = ?", code);
	}

	@Override
	public GramPanchayat findGrampanchaythByCode(String code) {
		// TODO Auto-generated method stub
		return (GramPanchayat) find("FROM GramPanchayat gp WHERE gp.code=?", code);
	}

	@Override
	public List<GramPanchayat> listGramPanchayatsByCityCode(String selectedCity) {
		return list("FROM GramPanchayat gp WHERE gp.city.code = ? ORDER BY gp.name ASC", selectedCity);
	}

	public List<GramPanchayat> listGramPanchayatsByCode(String selectedCity) {

		return list("FROM GramPanchayat gp WHERE gp.city.code = ? ORDER BY gp.name ASC", selectedCity);
	}

	public List<Municipality> listMunicipalitiesByCode(String locality) {

		return list("FROM Municipality m WHERE m.locality.code = ? ORDER BY m.name ASC", locality);
	}

	public List<State> listStatesByCode(String country) {

		return list("FROM State s WHERE s.country.code = ? ORDER BY s.name ASC", country);
	}

	@Override
	public List<Object[]> listCityCodeAndName() {

		return list("SELECT m.code,m.name FROM Municipality m ORDER BY m.name ASC");
	}

	@Override
	public List<State> listOfStates() {
		// TODO Auto-generated method stub
		return list("FROM State st ORDER BY st.name ASC");
	}

	public boolean isVillageMappedWithCooperative(long id) {

		boolean isVillageMappedWithCooperative = false;
		Session sessions = getSessionFactory().openSession();
		String queryString = "SELECT COUNT(*) FROM WAREHOUSE_VILLAGE_MAP WHERE VILLAGE_ID = " + id;
		Query query = sessions.createSQLQuery(queryString);

		int count = Integer.valueOf(query.list().get(0).toString());

		if (count > 0) {
			isVillageMappedWithCooperative = true;
		}
		sessions.flush();
		sessions.close();
		return isVillageMappedWithCooperative;
	}

	public boolean isVillageMappingExist(long id) {

		Object[] values = { id, ESETxnStatus.SUCCESS.ordinal() };
		List<Farmer> farmerList = list("FROM Farmer farmer WHERE farmer.village.id = ? AND farmer.statusCode = ?",
				values);
		if (!ObjectUtil.isListEmpty(farmerList)) {
			return true;
		}
		return false;
	}

	public String isVillageMappindExistForDistributionAndProcurement(long id) {

		List<Procurement> procurementList = list("FROM Procurement p WHERE p.village.id = ?", id);
		if (!ObjectUtil.isListEmpty(procurementList)) {
			return "procurement.exist";

		}
		return null;
	}

	@Override
	public Village findVillageByNameAndCity(String village, String city) {
		Object[] values = { village, city };
		return (Village) find("FROM Village v WHERE v.name= ? AND v.city.name=?", values);
	}

	public Village findDuplicateVillageName(long id, String name) {
		Object[] values = { id, name };
		Village villageList = (Village) find("FROM Village v WHERE v.gramPanchayat.id = ? and v.name=?", values);
		return villageList;
	}

	public Country findCountryByName(String name) {

		Country country = (Country) find("FROM Country c LEFT JOIN FETCH c.states st WHERE c.name = ?", name);
		return country;
	}

	public State findStateByCode(String code) {

		State state = (State) find("FROM State s LEFT JOIN FETCH s.localities WHERE s.code = ?", code);
		return state;
	}

	public Locality findLocalityByCode(String code) {

		Locality locality = (Locality) find("FROM Locality l LEFT JOIN FETCH l.municipalities WHERE l.code = ?", code);
		return locality;
	}

	public GramPanchayat findGramPanchayatById(long id) {

		return (GramPanchayat) find("FROM GramPanchayat gp WHERE gp.id=?", id);
	}

	public Municipality findMunicipalityByName(String name) {

		Municipality municipality = (Municipality) find("FROM Municipality m WHERE m.name = ?", name);
		return municipality;
	}

	public Municipality findMunicipalityById(Long id) {

		Municipality municipality = (Municipality) find("FROM Municipality m WHERE m.id = ?", id);
		return municipality;
	}

	public List<Village> listVillagesByCityID(Long cityId) {
		return list("FROM Village v WHERE v.city.id = ? ORDER BY v.name ASC", cityId);
	}

	@SuppressWarnings("unchecked")
	public List<PackhouseIncoming> listWarehouseProductByCityCode(String code) {

		return list("From PackhouseIncoming wp WHERE wp.packhouse.code=?", code);
	}

	public Locality findLocalityByName(String name) {

		Locality locality = (Locality) find("FROM Locality l LEFT JOIN FETCH l.municipalities WHERE l.name = ?", name);
		return locality;
	}

	public Locality findLocalityById(Long id) {

		Locality locality = (Locality) find("FROM Locality l WHERE l.id = ?", id);
		return locality;
	}

	public Country findCountryById(Long id) {

		Country country = (Country) find("FROM Country c LEFT JOIN FETCH c.states WHERE c.id = ?", id);
		return country;
	}

	public State findStateById(Long id) {

		State state = (State) find("FROM State s WHERE s.id = ?", id);
		return state;
	}

	public List<Locality> listLocalitiesByStateID(Long stateId) {
		return list("FROM Locality l WHERE l.state.id = ? ORDER BY l.name ASC", stateId);
	}

	public State findStateByName(String name) {

		State state = (State) find("FROM State s LEFT JOIN FETCH s.localities WHERE s.name = ?", name);
		return state;
	}

	public Country findCountryByCode(String code) {

		Country country = (Country) find("FROM Country c LEFT JOIN FETCH c.states WHERE c.code = ?", code);
		return country;
	}

	public Village findVillageByName(String name) {

		return (Village) find("FROM Village v WHERE v.name= ? ", name);
	}

	public Vendor findVendor(Long id) {

		// TODO Auto-generated method stub
		Vendor vendor = (Vendor) find("FROM Vendor v WHERE v.id = ?", id);
		return vendor;

	}

	public List<String> findAgentNameByWareHouseId(long coOperativeId) {

		Session session = getSessionFactory().openSession();
		String queryString = "SELECT pi.FIRST_NAME from agent_warehouse_map awm LEFT JOIN  prof p on awm.agent_id = p.id LEFT JOIN pers_info pi on p.PERS_INFO_ID=pi.ID WHERE awm.warehouse_id ='"
				+ coOperativeId + "'";
		Query query = session.createSQLQuery(queryString);
		List list = query.list();
		session.flush();
		session.close();
		return list;
	}

	@Override
	public Village findVillageAndCityByVillName(String otherVi, Long cityId) {
		// TODO Auto-generated method stub
		Object[] values = { otherVi, cityId };
		return (Village) find("FROM Village v WHERE v.name= ? and v.city.id=?", values);
	}

	@Override
	public List<Village> listVillagesByPanchayatId(long selectedPanchayat) {

		return list("FROM Village v WHERE v.gramPanchayat.id = ? ORDER BY v.name ASC", selectedPanchayat);
	}

	@Override
	public List<Object[]> listHarvestSeasonFromToPeriod() {

		// TODO Auto-generated method stub
		return list("SELECT fromPeriod, toPeriod, id FROM HarvestSeason");
	}

	@Override
	public HarvestSeason findHarvestSeasonById(Long id) {

		return (HarvestSeason) find("From HarvestSeason hs where hs.id=?", id);
	}

	public HarvestSeason findHarvestSeasonByName(String name) {

		return (HarvestSeason) find("From HarvestSeason hs where hs.name=?", name);
	}

	@Override
	public List<Product> findProductBySubCategoryCode(Long trim) {
		// TODO Auto-generated method stub
		return list("FROM Product p WHERE p.subcategory.id=?", trim);
	}

	@Override
	public DocumentUpload findDocumentUploadById(Long valueOf) {
		// TODO Auto-generated method stub

		System.out.println("At 3475 *********valueOf===>" + valueOf);
		DocumentUpload documentUpload = (DocumentUpload) find("FROM DocumentUpload du WHERE du.id = ?", valueOf);

		return documentUpload;
	}

	@Override
	public List<Object[]> listOfProducts() {
		// TODO Auto-generated method stub
		return list("SELECT p.id,p.code,p.name,p.subcategory.name,p.subcategory.id FROM Product p ORDER BY p.id ASC");
	}

	@Override
	public List<Object[]> listAgentIdName() {
		return list(
				"Select a.profileId,CONCAT(a.personalInfo.firstName,' ',a.personalInfo.lastName)  FROM Agent a order by a.personalInfo.firstName");
	}

	@Override
	public List<FarmCatalogue> listCatelogueType(String type) {

		// TODO Auto-generated method stub
		Object[] values = { Integer.valueOf(type), FarmCatalogue.ACTIVE };
		return list("FROM FarmCatalogue where typez=? AND status=?", values);
	}

	@Override
	public DocumentUpload findDocumentUploadByReference(String id, Integer type) {
		// TODO Auto-generated method stub
		Object[] values = { id, type };
		String trim = id.trim();
		DocumentUpload documentUpload = (DocumentUpload) find(
				"FROM DocumentUpload du WHERE du.refCode = ? and du.type=?", values);

		return documentUpload;
	}

	@Override
	public List<Object[]> listAgroChemicalDealers() {
		String queryString = "select acd.id,acd.DelearName,acd.Company from agro_chemical_dealer acd left join ese_user u on u.DEALER_ID=acd.id where acd.is_Active=1 and u.ENABLE=1";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery(queryString);
		List<Object[]> list = query.list();
		sessions.flush();
		sessions.close();
		return list;
		// return list("select s.id,s.dealerName,s.companyName From
		// AgroChemicalDealer s left join fetch user u where s.status=1 and
		// u.isEnabled='true ORDER BY s.dealerName ASC");
	}

	@Override
	public User findUserByDealerAndRole(Long id, Long id2) {
		// TODO Auto-generated method stub
		Object[] values = { id, id2 };
		User user = (User) find("from User u where u.agroChDealer=? and u.role.id=?", values);
		return user;
	}

	@Override
	public List<User> listUsersByType(int type) {
		// TODO Auto-generated method stub
		List<User> availableUsers = list("FROM User r WHERE r.userType = ?", type);
		return availableUsers;
	}

	@Override
	public List<FarmCatalogue> listFarmCatalogueWithOther(int type, int otherValue) {
		return list("FROM FarmCatalogue fc where fc.typez in (?,?) and fc.status=?",
				new Object[] { type, FarmCatalogue.OTHER, FarmCatalogue.ACTIVE });
	}

	@Override
	public List<FarmCatalogue> listSeedTreatmentDetailsBasedOnType() {

		return list("FROM FarmCatalogue cat WHERE cat.typez='7'");
	}

	public List<HarvestSeason> listHarvestSeason() {

		return list("from HarvestSeason");
	}

	@Override
	public List<Object[]> listAgroChemical() {
		String queryString = "select cv.id,cv.code, cv.name from catalogue_value cv left join agro_chemical_registration acd on cv.code=acd.CHEMICAL_NAME where cv.`STATUS`='1' and acd.`is_Active`=1 and cv.typez=136 group by cv.id";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery(queryString);
		List<Object[]> list = query.list();
		sessions.flush();
		sessions.close();
		return list;
	}

	@Override
	public List<Object[]> listProcurementProductForSeedHarvest() {

		String queryString = "SELECT ppt.id,ppt.name FROM Procurement_Product ppt ORDER BY ppt.name";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery(queryString);
		List<Object[]> list = query.list();
		sessions.flush();
		sessions.close();
		return list;
	}

	@Override
	public List<Object[]> findPermittedAgroChemicalRegistrationByDealerId(Long dealerId) {
		// TODO Auto-generated method stub
		String queryString = "select ar.id,cv.code, cv.name,acd.REG_NO,acd.id as pid from catalogue_value cv inner join permit_application acd on cv.code=acd.AGRO_CHEM_NAME and cv.`STATUS`='1' and acd.`STATUS`=1 and cv.typez=136 and acd.IS_ACTIVE=1 and acd.DEALER_NAME='"
				+ dealerId
				+ "' join agro_chemical_registration ar on ar.id =acd.CHEMICAL_ID and ar.IS_ACTIVE=1 and ar.`STATUS`=1 group by acd.id";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery(queryString);
		List<Object[]> list = query.list();
		sessions.flush();
		sessions.close();
		return list;
	}

	@Override
	public List<Municipality> findMuniciByDistrictId(long selectedLocality) {
		// TODO Auto-generated method stub
		return list("FROM Municipality m WHERE m.locality.id = ? ORDER BY m.name ASC", selectedLocality);
	}

	@Override
	public List<GramPanchayat> findGramByMuniciId(long selectedMunicipality) {
		// TODO Auto-generated method stub
		return list("FROM GramPanchayat g WHERE g.city.id = ? ORDER BY g.name ASC", selectedMunicipality);
	}

	@Override
	public List<Village> findVillageByGramId(long selectedGram) {
		// TODO Auto-generated method stub
		System.out.println("At 6321  utilDao findVillageByGramId==>" + selectedGram);// selectedWard1

		return list("FROM Village v WHERE v.city.id = ?", selectedGram);//
	}

	@Override
	public User findInspectorById(Long id) {
		User user = (User) find("from User user where user.userType=3 and user.id = ?", id);
		return user;
	}

	@Override
	public List<User> listUsersByRoleName(String roleName) {
		return list("FROM User u join fetch u.role r WHERE r.name=?", roleName.trim());
	}

	@Override
	public List<Object> findAgroChemicalDealerByCompanyNameOrEmail(String email, String companyName, String nid) {
		String queryString = "select CASE WHEN a.E_mail='" + email + "' THEN 'duplicate.email' WHEN a.Company='"
				+ companyName + "' THEN 'duplicate.company' " + "when a.NATION_ID_NO='" + nid
				+ "' then 'duplicate.nid' END from agro_chemical_dealer a where a.E_mail='" + email + "' or "
				+ "a.Company='" + companyName + "' or a.NATION_ID_NO='" + nid + "'";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery(queryString);
		List<Object> list = query.list();
		sessions.flush();
		sessions.close();
		return list;
	}

	@Override
	public List<User> listUsersExceptAdmin() {
		Session session = getSessionFactory().openSession();
		Query query = session.createQuery(
				"FROM User u join fetch u.role r WHERE r.isAdminUser not in (:names) and  u.isEnabled=true");
		query.setParameterList("names", (Arrays.asList("true", "1")));
		List<User> users = query.list();
		session.flush();
		session.close();
		return users;
	}

	@Override
	public List<Object[]> listDealerDetailsbyRegNo(Long id) {
		// TODO Auto-generated method stub

		String queryString = "select ad.DelearName,ad.addr,p.ADDR_PARTNER,ad.id from agro_chemical_dealer ad left join premises_reg p on p.DEALER_ID="
				+ id + " where ad.id=" + id + " and ad.IS_ACTIVE=1";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery(queryString);
		List<Object[]> list = query.list();
		sessions.flush();
		sessions.close();
		return list;
	}

	@Override
	public List<DocumentUpload> listDocumentsByCode(String code) {
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(DocumentUpload.class);
		criteria.add(Restrictions.eq("refCode", code));
		criteria.addOrder(Order.asc("id"));
		List<DocumentUpload> list = criteria.list();
		return list;

	}

	@Override
	public DocumentUpload findDocumentById(Long id) {

		return (DocumentUpload) find("FROM DocumentUpload doc WHERE doc.id =?", id);
	}

	@Override
	public DocumentUpload findDocumentUploadByCode(String code) {

		DocumentUpload documentUpload = (DocumentUpload) find("FROM DocumentUpload du WHERE du.refCode = ?", code);

		return documentUpload;

	}

	@Override
	public List<Object[]> listProcurementProductByVariety(Long procurementProdId) {
		// TODO Auto-generated method stub
		return list("SELECT pv.id,pv.code,pv.name FROM ProcurementVariety pv WHERE pv.procurementProduct.id=?",
				procurementProdId);
	}

	@Override
	public List<Object[]> listChemical() {
		String queryString = "select cv.id,cv.code,cv.name from catalogue_value cv where cv.typez='136'and cv.status=1 and cv.code not in (select b.name from banned_products b) group by cv.ID";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery(queryString);
		List<Object[]> list = query.list();
		sessions.flush();
		sessions.close();
		return list;
		// return list("select cv.id,cv.code,cv.name from catalogue_value cv
		// where cv.typez='136' and cv.code not in (select b.name from
		// banned_products b) group by cv.ID");
	}

	@Override
	public String findDealerByRegNo(String regNo) {
		// TODO Auto-generated method stub
		String queryString = "select acd.id from agro_chemical_dealer acd where acd.Reg_No=:reg";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery(queryString);
		query.setParameter("reg", regNo);
		List ls = query.list();
		sessions.flush();
		sessions.close();
		if (ls != null && ls.size() > 0) {
			return String.valueOf(ls.get(0));
		} else {
			return "";
		}
	}

	@Override
	public Role findRoleByType(int type) {
		// TODO Auto-generated method stub
		Role role = (Role) find("FROM Role r WHERE r.type = ?", type);
		return role;
	}

	@Override
	public List<Object[]> listTestIdAndNumber() {
		// TODO Auto-generated method stub
		String queryString = "SELECT g.id,g.reg_no FROM seed_sample_request g where g.status=1 and g.is_active=1 ORDER BY g.test_no";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery(queryString);
		List<Object[]> list = query.list();
		sessions.flush();
		sessions.close();
		return list;
	}

	@Override
	public void saveDistribution(Distribution distribution, String tenantId) {
		Session sessions = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		sessions.save(distribution);
		sessions.flush();
		sessions.close();
	}

	@Override
	public Distribution findDistributionByReceiptNoTxnType(String receiptNo, String txnType) {
		Object[] bindValues = { receiptNo, txnType };
		Distribution distribution = (Distribution) find(
				"FROM Distribution dn WHERE dn.agroTransaction.receiptNo = ? and dn.agroTransaction.txnType=?",
				bindValues);
		return distribution;
	}

	@Override
	public List<Farmer> getFarmerList() {
		// TODO Auto-generated method stub
		return list("from Farmer f where f.status=1");
	}

	@Override
	public User findUserById(Long object) {
		// TODO Auto-generated method stub
		return (User) find("FROM User u WHERE  u.id = ?", object);
	}

	@Override
	public Farmer findFarmerByEmail(String email) {
		// TODO Auto-generated method stub
		return (Farmer) find("FROM Farmer f WHERE  f.isActive <>2 and f.emailId = ?", email);
	}

	@Override
	public List<Object[]> listExporter() {
		String queryString = "select acd.id,acd.COMPANY_NAME  from Exporter_registration acd WHERE  acd.Status <>2";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery(queryString);
		List<Object[]> list = query.list();
		sessions.flush();
		sessions.close();
		return list;
	}
	
	@Override
	public List<Object[]> listBuyer() {
		String queryString = "select acd.id,acd.CUSTOMER_NAME  from customer acd";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery(queryString);
		List<Object[]> list = query.list();
		sessions.flush();
		sessions.close();
		return list;
	}
	
	@Override
	public List<Object[]> listUOM() {
		String queryString = "select acd.id,acd.CUSTOMER_NAME  from customer acd";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery(queryString);
		List<Object[]> list = query.list();
		sessions.flush();
		sessions.close();
		return list;
	}

	@Override
	public List<Farmer> listFarmerName() {
		return list("FROM Farmer t where t.status=1");

	}

	@Override
	public AgentType findAgentTypeByName(String name) {
		// TODO Auto-generated method stub
		return (AgentType) find("From AgentType WHERE name=?", name);
	}

	@Override
	public Farmer findFarmerById(Long id) {
		// TODO Auto-generated method stub

		Farmer farmer = (Farmer) find("from Farmer f where f.id=?", id);
		return farmer;
	}

	@Override
	public List<FarmCatalogue> listCatalogueByCode(String code) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		String qry = null;
		if (code.split(",").length > 1) {
			Arrays.asList(code.split(",")).stream().forEach(c -> {
				// sb.append("'" + c.trim() + "',");
				sb.append("\"" + c.trim() + "\",");
			});
			qry = sb.toString().substring(0, sb.toString().lastIndexOf(","));
		}

		String queryString = (code.split(",").length > 1) ? "FROM FarmCatalogue cg WHERE cg.code in (" + qry + ")"
				: "FROM FarmCatalogue cg WHERE cg.code=?";
		Session sessions = getSessionFactory().openSession();
		List<FarmCatalogue> list = new LinkedList<FarmCatalogue>();
		Query query = sessions.createSQLQuery(queryString);
		if (code.split(",").length > 1) {
			list = query.list();
		} else {
			FarmCatalogue ft = (FarmCatalogue) find(queryString, code);
			list.add(ft);
		}

		sessions.flush();
		sessions.close();
		return list;
	}

	@Override
	public Farmer findFarmerByName(String farmerName) {
		// TODO Auto-generated method stub
		return (Farmer) find("From Farmer WHERE firstName=?", farmerName);
	}

	@Override
	public Farmer findFarmerByNIN(String nin) {
		return (Farmer) find("From Farmer WHERE nin=?", nin);
	}

	@Override
	public List<ProcurementProduct> listProcurementProductByRevisionNo(Long revisionNo) {
		// TODO Auto-generated method stub
		return list("FROM ProcurementProduct pp WHERE pp.revisionNo>? ORDER BY pp.revisionNo DESC", revisionNo);
	}

	@Override
	public List<ProcurementVariety> listProcurementProductVarietyByRevisionNo(Long revisionNo) {
		// TODO Auto-generated method stub
		return list("FROM ProcurementProduct pp WHERE pp.revisionNo>? ORDER BY pp.revisionNo DESC", revisionNo);
	}

	@Override
	public List<ProcurementGrade> listProcurementProductGradeByRevisionNo(Long revisionNo) {
		// TODO Auto-generated method stub
		return list("FROM ProcurementGrade pg WHERE pg.revisionNo>? ORDER BY pg.revisionNo DESC", revisionNo);
	}

	@Override
	public List<ProcurementProduct> listProcurementProduct() {
		// TODO Auto-generated method stub
		return list("FROM ProcurementProduct pp ORDER BY pp.name");
	}

	@Override
	public Packhouse findWarehouseById(Long id) {
		// TODO Auto-generated method stub
		Object[] values = { id, Packhouse.WarehouseTypes.COOPERATIVE.ordinal() };
		return (Packhouse) find("FROM Packhouse wh WHERE wh.id= ? AND wh.typez=?", values);
	}

	@Override
	public Packhouse findWarehouseByName(String name) {
		// TODO Auto-generated method stub
		return (Packhouse) find("FROM Packhouse sn where sn.name=?", name);
	}

	@Override
	public List<Packhouse> listAllWarehouses() {
		return list("FROM Packhouse pv ORDER BY pv.name");
	}

	@Override
	public ExporterRegistration findExproterByEmail(String emailId) {
		// TODO Auto-generated method stub
		return (ExporterRegistration) find("FROM ExporterRegistration er WHERE  er.isActive <>2 and er.email = ?",
				emailId);
	}

	@Override
	public ExporterRegistration findExproterByCompanyName(String name) {
		// TODO Auto-generated method stub
		return (ExporterRegistration) find("FROM ExporterRegistration er WHERE  er.isActive <>2 and er.status <>2 and er.name = ?", name);
	}

	@Override
	public List<ProcurementVariety> listProcurementVarietyByProcurementProductCode(String ppCode) {
		System.out.println("At 1821 *************" + ppCode);
		return list("FROM ProcurementVariety pv WHERE pv.procurementProduct.code=? ORDER BY pv.name ASC", ppCode);
	}

	@Override
	public ExporterRegistration findExportRegById(Long id) {
		ExporterRegistration expoReg = (ExporterRegistration) find(
				// "FROM ExporterRegistration e left join fetch e.inspDetails
				// idd left join fetch idd.user u WHERE e.id = ?",
				"FROM ExporterRegistration e  WHERE e.id = ?", id);
		return expoReg;

	}
	
	@Override
	public Pcbp findPcbpById(Long id) {
		Pcbp pcbp = (Pcbp) find("FROM Pcbp e  WHERE e.id = ?", id);
		return pcbp;
		
	}
	
	/*@Override
	public Pcbp findPcbpByvarietyAndChamical(String va, String ch) {
		Pcbp pcbp = (Pcbp) find("FROM Pcbp e  WHERE e.id = ?", va);
		return pcbp;
	}*/
	
	@Override
	public Pcbp findPcbpByvarietyAndChamical(Long va, Long ch) {
	//	return (Pcbp) find("FROM Pcbp p WHERE p.cropvariety.id = ? AND p.chemicalName= ?", new Object[] { va, ch });
		return (Pcbp) find("FROM Pcbp p WHERE p.cropvariety.id = ? AND p.id= ?", new Object[] { va, ch });
	}
	

	//
	@Override
	public State listStatesById(Long id) {
		System.out.println("At 1821 *************" + id);
		// return (State)find("FROM State st where st.country.id=?",id);
		return (State) find("FROM State  where  id=?", id);
		// return (Farmer) find("From Farmer WHERE firstName=?", farmerName);

	}

	@Override
	public List<ProcurementVariety> listProcurementVarietyByProcurementProductId(Long id) {

		return list("FROM ProcurementVariety pv WHERE pv.procurementProduct.id=? ORDER BY pv.name ASC", id);
	}

	@Override
	public ProcurementVariety findProcurementVariertyById(Long id) {

		ProcurementVariety procurementVariety = (ProcurementVariety) find(
				"FROM ProcurementVariety ppv WHERE ppv.id = ?", id);

		return procurementVariety;
	}
	
	@Override
	public List<Pcbp> findPcbpByProcurementGradeId(Long id) {
		
		return  list("FROM Pcbp pc WHERE pc.cropvariety.id = ?", id);
		
	}

	@Override
	public ProcurementProduct findProcurementProductById(Long id) {

		ProcurementProduct procurementProduct = (ProcurementProduct) find(
				"FROM ProcurementProduct ppv WHERE ppv.id = ?", id);

		return procurementProduct;
	}

	public Locality findVillageByStateId(long selectedState) {
		Locality locality = (Locality) find("FROM Locality l1 WHERE l1.id = ?", selectedState);

		return locality;
		// return list("FROM Locality l WHERE l.state.id = ? ORDER BY l.name
		// ASC", selectedState);
	}

	@Override
	public Municipality findCityByDistrictId(long selectedLocality) {
		// TODO Auto-generated method stub
		Municipality municipality = (Municipality) find("FROM Municipality m WHERE m.id = ? ORDER BY m.name ASC",
				selectedLocality);
		return municipality;
		// return list("FROM Municipality m WHERE m.locality.id = ? ORDER BY
		// m.name ASC", selectedLocality);
	}

	@Override
	public Farm findFarmId(Long id) {
		// TODO Auto-generated method stub
		return (Farm) find("FROM Farm f WHERE f.id=?", id);
	}

	@Override
	public ProcurementProduct findProcurementProductByName(String name) {
		ProcurementProduct product = (ProcurementProduct) find("FROM ProcurementProduct pp WHERE pp.name = ?", name);
		return product;
	}

	@Override
	public ProcurementVariety findProcurementVariertyByNameAndCropId(String varietyName, Long procurementProductId) {

		Object[] values = { varietyName, procurementProductId };
		ProcurementVariety procurementVariety = (ProcurementVariety) find(
				"FROM ProcurementVariety pv WHERE pv.name=? and pv.procurementProduct.id=?", values);
		return procurementVariety;
	}

	@Override
	public void addProcurementVariety(ProcurementVariety procurementVariety) {
		procurementVariety.setRevisionNo(DateUtil.getRevisionNumber());
		save(procurementVariety);
	}

	@Override
	public List<ProcurementGrade> listProcurementGradeByProcurementVarietyId(Long id) {

		return list("FROM ProcurementGrade pg WHERE pg.procurementVariety.id=? ORDER BY pg.name ASC", id);
	}
	
	
	@Override
	public ProcurementGrade findProcurementGradeByNameAndVarietyId(String name, Long id) {
		// TODO Auto-generated method stub
		Object[] values = { name, id };
		ProcurementGrade procurementGrade = (ProcurementGrade) find(
				"FROM ProcurementGrade pg WHERE pg.name=? and pg.procurementVariety.id=?", values);
		return procurementGrade;
	}

	@Override
	public ProcurementGrade findProcurementGradeById(Long id) {
		// TODO Auto-generated method stub
		return (ProcurementGrade) find("FROM ProcurementGrade pg WHERE pg.id=?", id);
	}

	@Override
	public List<ProcurementVariety> listProcurementVariety() {
		// TODO Auto-generated method stub
		return list("FROM ProcurementVariety pv ORDER BY pv.name");
	}
	
	@Override
	public List<ProcurementVariety> listProcurementVarietyBasedOnCropCat(String ugandaExport) {
		List<Long> ids = Arrays.asList(ugandaExport.split(",")).stream()
				.filter(u -> u != null && StringUtil.isLong(u.trim())).map(u -> Long.valueOf(u.trim()))
				.collect(Collectors.toList());
		Session session = getSessionFactory().openSession();
		Query query = session.createQuery("FROM ProcurementVariety pv where pv.id in (:codes)");
		query.setParameterList("codes", ids);
		List<ProcurementVariety> questions = query.list();
		session.flush();
		session.close();
		return questions;

	}

	@Override
	public ProcurementProduct findProcurementProductByNameAndBranch(String name, String branchId_F) {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().getCurrentSession();
		org.hibernate.Filter branchFilter = session.getEnabledFilter(ISecurityFilter.BRANCH_FILTER);
		ProcurementProduct prod = null;
		if (!ObjectUtil.isEmpty(branchFilter)) {
			session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		}
		if (StringUtil.isEmpty(branchId_F)) {
			prod = (ProcurementProduct) find("from ProcurementProduct pr where pr.name = ? AND pr.branchId IS NULL",
					name);
		} else {

			prod = (ProcurementProduct) find("from ProcurementProduct pr where pr.name = ? AND pr.branchId = ?",
					new Object[] { name, branchId_F });
		}
		return prod;
	}

	@Override
	public ProcurementProduct findProcurementProductByCode(String code) {
		// TODO Auto-generated method stub
		return (ProcurementProduct) find("FROM ProcurementProduct pp WHERE pp.code=?", code);
	}

	@Override
	public ProcurementVariety findProcurementVariertyByNameAndBranch(String name, String branchId_F) {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().getCurrentSession();
		org.hibernate.Filter branchFilter = session.getEnabledFilter(ISecurityFilter.BRANCH_FILTER);
		ProcurementVariety variety = null;
		if (!ObjectUtil.isEmpty(branchFilter)) {
			session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		}
		if (StringUtil.isEmpty(branchId_F)) {
			variety = (ProcurementVariety) find(
					"from ProcurementVariety var where var.name = ? AND var.procurementProduct.branchId IS NULL", name);
		} else {

			variety = (ProcurementVariety) find(
					"from ProcurementVariety var where var.name = ? AND var.procurementProduct.branchId = ?",
					new Object[] { name, branchId_F });
		}
		return variety;
	}

	@Override
	public ProcurementVariety findProcurementVariertyByCode(String code) {
		// TODO Auto-generated method stub

		ProcurementVariety procurementVariety = (ProcurementVariety) find(
				"FROM ProcurementVariety ppv WHERE ppv.code = ?", code);

		return procurementVariety;
	}

	@Override
	public ProcurementGrade findProcurementGradeByCode(String code) {
		// TODO Auto-generated method stub
		return (ProcurementGrade) find("FROM ProcurementGrade pg WHERE pg.code=?", code);
	}
	
	@Override
	public ProcurementGrade findProcurementGradeByName(String name) {
		// TODO Auto-generated method stub
		return (ProcurementGrade) find("FROM ProcurementGrade pg WHERE pg.name=?", name);
	}
	
	@Override
	public ProcurementVariety findProcurementVarietyByName(String name) {
		// TODO Auto-generated method stub
		return (ProcurementVariety) find("FROM ProcurementVariety pg WHERE pg.name=?", name);
	}

	@Override
	public ProcurementGrade findProcurementGradeByNameAndBranch(String name, String branchId_F) {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().getCurrentSession();
		org.hibernate.Filter branchFilter = session.getEnabledFilter(ISecurityFilter.BRANCH_FILTER);
		ProcurementGrade grade = null;
		if (!ObjectUtil.isEmpty(branchFilter)) {
			session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		}
		if (StringUtil.isEmpty(branchId_F)) {
			grade = (ProcurementGrade) find(
					"from ProcurementGrade gr where gr.name = ? AND gr.procurementVariety.procurementProduct.branchId IS NULL",
					name);
		} else {

			grade = (ProcurementGrade) find(
					"from ProcurementGrade gr where gr.name = ? AND gr.procurementVariety.procurementProduct.branchId = ?",
					new Object[] { name, branchId_F });
		}
		return grade;
	}

	@Override
	public Farmer findFarmerByNid(Long nid) {
		// TODO Auto-generated method stub
		return (Farmer) find("FROM Farmer fr WHERE  fr.nid = ?", nid);
	}

	/*
	 * @Override public Farmer findFarmerByPhone(Long nid) { // TODO
	 * Auto-generated method stub System.out.println("At 40005==nid==>"+nid);
	 * Farmer fr=null; try{ fr=(Farmer)
	 * find("FROM Farmer fr WHERE  fr.phoneNo = ?", nid);
	 * System.out.println("At 3998************"+fr.getPhoneNo());
	 * }catch(Exception e){
	 * 
	 * System.out.println("At 4001catch(Exception e) phone Not  exists");
	 * }return fr; }
	 * 
	 * @Override public User findByUsername(String username) { User user =
	 * (User) find("from User u  where username=?", username); return user; }
	 */

	@Override
	public Farmer findFarmerByPhone(Long phn) {
		return (Farmer) find("From Farmer WHERE phoneNo=?", phn);
	}

	// *******************
	@Override
	public ExporterRegistration findExportByLicense(String license) {

		return (ExporterRegistration) find("From ExporterRegistration WHERE license=?", license);
	}

	@Override
	public ExporterRegistration findExportByKrapin(String pin) {
		return (ExporterRegistration) find("From ExporterRegistration WHERE kraPin=?", pin);
	}

	@Override
	public ExporterRegistration findExportByNid(String nid) {
		return (ExporterRegistration) find("From ExporterRegistration WHERE nationalId=?", nid);
	}

	@Override
	public ExporterRegistration findExportByCompanyEmail(String cemail) {
		return (ExporterRegistration) find("From ExporterRegistration WHERE tin=?", cemail);
	}

	@Override
	public Object[] findIfFarmerExistForFarmer(String nid) {
		String queryString = "SELECT group_concat(distinct National_Id)  FROM farmer where status_code=0 and status=1 and (National_Id='" + nid + "') ";
		Session sessions = getSessionFactory().openSession();
		Query querys = sessions.createSQLQuery(queryString);
		Object[] results = (Object[]) querys.uniqueResult();
		sessions.flush();
		sessions.close();
		return results;
	}
	
	@Override
	public Object[] findIfFarmerExist(String phno, String nid) {
		String queryString = "SELECT group_concat(distinct MOBILE_NUMBER),group_concat(distinct National_Id)  FROM farmer where status_code=0 and status=1 and (MOBILE_NUMBER='"
				+ phno + "' or National_Id='" + nid + "') ";
		Session sessions = getSessionFactory().openSession();
		Query querys = sessions.createSQLQuery(queryString);
		Object[] results = (Object[]) querys.uniqueResult();
		sessions.flush();
		sessions.close();
		return results;
	}

	public FarmCrops findFarmCropsById(Long id) {

		return (FarmCrops) find("FROM FarmCrops fc WHERE fc.id = ?", id);
	}

	@Override
	public List<Farm> listFarmByFarmerId(Long id) {
		// TODO Auto-generated method stub
		return list("FROM Farm f  where f.status=1  and f.farmer.id=? ORDER BY f.farmName ASC", id);

	}

	@Override
	public List<FarmCrops> listFarmCropByFarmId(Long farmid) {
		return list("FROM FarmCrops f  where f.farm.id=?  and f.status=1 ORDER BY f.id ASC", farmid);
	}	
	
	@Override
	public List<Planting> listOfPlantingByBlockId(Long id) {
		return list("FROM Planting p  where p.id=?  and p.status=1 ORDER BY p.id ASC", id);
	}	

	@Override
	public List<ProcurementGrade> listProcurementGradeByProcurementVarietyIds(String procurementVariety) {
		List<Long> ids = Arrays.asList(procurementVariety.split(",")).stream()
				.filter(u -> u != null && StringUtil.isLong(u.trim())).map(u -> Long.valueOf(u.trim()))
				.collect(Collectors.toList());
		Session session = getSessionFactory().openSession();
		Query query = session.createQuery("FROM ProcurementGrade lp WHERE lp.procurementVariety.id in (:codes)");
		query.setParameterList("codes", ids);
		List<ProcurementGrade> questions = query.list();
		session.flush();
		session.close();
		return questions;

	}

	@Override
	public FarmCrops findFarmCropsById(long id) {
		// TODO Auto-generated method stub
		return (FarmCrops) find("FROM FarmCrops fc WHERE fc.id = ?", id);
	}

	@Override
	public SprayAndFieldManagement findSprayAndFieldManagementById(Long id) {
		return (SprayAndFieldManagement) find(
				"from SprayAndFieldManagement s left join fetch s.farmCrops fc where s.id=?", id);
	}

	@Override
	public Scouting findScoutingById(Long id) {
		// TODO Auto-generated method stub
		return (Scouting) find("from Scouting s left join fetch s.farmCrops fc where s.id=?", id);
	}
	@Override
	public FarmCrops findFarmCropByplantingfarmcode(String plantingId, String farmCode) {
		return (FarmCrops) find("FROM FarmCrops fc WHERE fc.farm.code = ? AND fc.plantingId= ?", new Object[] { farmCode, plantingId });
	}

	@Override
	public String findCropHierarchyNameById(String table,String id) {
		// TODO Auto-generated method stub
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery("SELECT group_concat(p.name) as name FROM "+table+" p WHERE p.id IN ("+id+")");
		List<Object> list = query.list();
		sessions.flush();
		sessions.close();
		if (!ObjectUtil.isListEmpty(list))
			return (String) list.get(0);
		return null;
	}
	
	@Override
	public String findCropHierarchyHSCodeById(String table,String id) {
		
		// TODO Auto-generated method stub
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery("SELECT group_concat(p.code) as code FROM "+table+" p WHERE p.id IN ("+id+")");
		List<Object> list = query.list();
		sessions.flush();
		sessions.close();
		if (!ObjectUtil.isListEmpty(list))
			return (String) list.get(0);
		return null;
	}

	@Override
	public String findCropHsCodeByProcurementProductId(String table, String id) {
		// TODO Auto-generated method stub
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery("SELECT group_concat(p.Crop_HS_code) as code FROM "+table+" p WHERE p.id IN ("+id+")");
		List<Object> list = query.list();
		sessions.flush();
		sessions.close();
		if (!ObjectUtil.isListEmpty(list))
			return (String) list.get(0);
		return null;
	}

	@Override
	public CityWarehouse findCityWarehouseByFarmCrops(Long id) {
		// TODO Auto-generated method stub
		return (CityWarehouse)find("FROM CityWarehouse cw WHERE cw.planting.id=? and cw.isDelete=0 and cw.coOperative is  null",id);
	}

	@Override
	public List<Packhouse> listPackhouse() {
		return list("FROM Packhouse f  WHERE f.status=1 ORDER BY f.name ASC");
	}
	@Override
	public List<CityWarehouse> listCityWareHouse() {
		// TODO Auto-generated method stub
		System.out.println("Inside utildao  listCityWareHouse");
		
		return list("FROM CityWarehouse cw ORDER BY cw.batchNo"); 
	}
	
	@Override
	public List<FarmCrops> listFarmCrops() {
		// TODO Auto-generated method stub
		return list("FROM FarmCrops fc ORDER BY fc.blockName");
	}
	
	public List<ShipmentDetails> findShipmentDetailById(Long id) {
	/*	Session session = getSessionFactory().openSession();
		Query query = session.createSQLQuery("Select * FROM importpermitApplication_detail pl where pl.id='"+id +"' ORDER BY pl.id");	
		//query.setParameter("id", id);
		List<ImportPermitApplicationDetail> result = query.list();// TODO Auto-generated method stub
		session.flush();
		session.close();*/
		return  list("FROM ShipmentDetails pl where pl.recallingstatus!='1' and pl.shipment.id='"+id +"' ORDER BY pl.id");
	}
	

	@Override
	public Packhouse findWarehouseByCode(String code) {
		// TODO Auto-generated method stub
		return (Packhouse) find("FROM Packhouse wh WHERE wh.code= ?", code);
	}
	
	public List<Shipment> listOfShipmentByCustomerId(long id) {
		Session session = getSessionFactory().openSession();
		String queryString1 = "select * from shipment cw where cw.CUSTOMER_ID='"+id+"'  and  cw.status=1  ";
		Query query = session.createSQLQuery(queryString1).addEntity(Shipment.class);
		List<Shipment> list = query.list();
		session.flush();
		session.close();
		return list;
	}
	
	public List<String> listPasswordsByTypeAndRefId(String type,long id) {
		Session session = getSessionFactory().openSession();
		String queryString1 = "select ph.password from password_history ph where ph.REF_ID='"+id+"' and ph.type='"+type+"' order by createdDate desc limit 3";
		Query query = session.createSQLQuery(queryString1);
		List<String> list = query.list();
		session.flush();
		session.close();
		return list;
	}
	@Override
	public List<LocaleProperty> listLocaleProp() {
		return list("FROM LocaleProperty lp");
	}
	
	@Override
	public Integer findCustomerCount(Date sDate, Date eDate, Long loggedInDealer) {
		Session session = getSessionFactory().getCurrentSession();
		if(loggedInDealer>0){
			return ((Long) session.createQuery("select count(*) from Customer c where  c.status=0 and c.exporter.id=:eid")
					.setParameter("eid", loggedInDealer).uniqueResult()).intValue();
		}else{					
			return ((Long) session.createQuery("select count(*) from Customer c where  c.status=0").uniqueResult()).intValue();			
		}
		
		
				
	}

	@Override
	public Integer findProductsCount(Date sDate, Date eDate, Long loggedInDealer) {
		Session session = getSessionFactory().getCurrentSession();
		return ((Long) session.createQuery("select count(*) from ProcurementVariety ").uniqueResult()).intValue();
	}

	@Override
	public Integer findShipmentsCount(Date sDate, Date eDate, Long loggedInDealer) {
		Session session = getSessionFactory().openSession();
		String QueryString = "Select count(*) from Shipment s";
		if(loggedInDealer>0){
			QueryString+= " where s.packhouse.exporter.id = " +  loggedInDealer;
			QueryString+= " AND s.shipmentDate between :startDate AND :endDate and  s.status=1   ";			
		}else{					
			QueryString+= " where s.shipmentDate BETWEEN :startDate AND :endDate and  s.status=1   ";			
		}
		
		Query query = session.createQuery(QueryString);		
		query.setParameter("startDate", sDate).setParameter("endDate", eDate);
				
		Integer l = ((Long) query.uniqueResult()).intValue();		
		session.flush();
		session.close();
		return l;
	}

	@Override
	public String findPlantingArea(Date sDate, Date eDate, Long loggedInDealer) {
		
		Session session = getSessionFactory().getCurrentSession();
		String q="select COALESCE(sum(p.cultiArea),'0') from Planting p join p.farmCrops fc join fc.farm fm where fc.status=1 and fm.status=1  and  fm.farmer.status=1 and p.plantingDate BETWEEN :startDate AND :endDate  ";
		if(loggedInDealer>0){
			q+="  and  fc.exporter.id="+loggedInDealer;
		}
		return ((String) session.createQuery(q).setParameter("startDate", sDate).setParameter("endDate", eDate).uniqueResult());
	}

	@Override
	public Integer findScoutingCount(Date sDate, Date eDate, Long loggedInDealer) {
		Session session = getSessionFactory().openSession();
		String QueryString = "Select count(*) from Scouting s";
		if(loggedInDealer>0){			
			QueryString+= " where s.farmCrops.exporter.id = " +  loggedInDealer;
			QueryString+= " AND s.receivedDate BETWEEN :startDate AND :endDate  and  s.status=0   ";			
		}else{					
			QueryString+= " where s.receivedDate BETWEEN :startDate AND :endDate and  s.status=0   ";			
		}
		
		Query query = session.createQuery(QueryString);		
		query.setParameter("startDate", sDate).setParameter("endDate", eDate);
				
		Integer l = ((Long) query.uniqueResult()).intValue();
		session.flush();
		session.close();
		return l;
	}

	@Override
	public Integer findSprayingCount(Date sDate, Date eDate, Long loggedInDealer) {
		Session session = getSessionFactory().openSession();
		String QueryString = "Select count(s.id) from SprayAndFieldManagement s";
		if(loggedInDealer>0){			
			QueryString+= " where s.farmCrops.exporter.id = " +  loggedInDealer;
			QueryString+= " AND s.dateOfSpraying BETWEEN :startDate AND :endDate  and  s.deleteStatus=0   ";			
		}else{					
			QueryString+= " where s.dateOfSpraying BETWEEN :startDate AND :endDate and  s.deleteStatus=0   ";			
		}
		
		Query query = session.createQuery(QueryString);		
		query.setParameter("startDate", sDate).setParameter("endDate", eDate);
				
		Integer l = ((Long) query.uniqueResult()).intValue();
		session.flush();
		session.close();
		return l;
	}

	@Override
	public Double findShipmentQuantity(Date sDate, Date eDate, Long loggedInDealer) {
			
		Session session = getSessionFactory().getCurrentSession();
		String q="select COALESCE(sum(s.totalShipmentQty),'0') from Shipment s where s.shipmentDate  BETWEEN :startDate AND :endDate and  s.status=1  ";
		if(loggedInDealer>0){
			q+=" and s.packhouse.exporter.id="+loggedInDealer;
		}
		return ((Double) session.createQuery(q).setParameter("startDate", sDate).setParameter("endDate", eDate).uniqueResult());
	}
	
	@Override
	public List<ProcurementGrade> listProcurementGradeByProcurementVarietyIdGradeid(Long id,String IdGrades) {
		List<Long> ids = Arrays.asList(IdGrades.split(",")).stream()
				.filter(u -> u != null && StringUtil.isLong(u.trim())).map(u -> Long.valueOf(u.trim()))
				.collect(Collectors.toList());
		Session session = getSessionFactory().openSession();
		Query query = session.createQuery("FROM ProcurementGrade lp WHERE  lp.procurementVariety.id='" + id + "'");
		if (ids != null && !ids.isEmpty()) {
			query = session.createQuery(
					"FROM ProcurementGrade lp WHERE lp.id in (:codes) and lp.procurementVariety.id='" + id + "'");
			query.setParameterList("codes", ids);
		}
		List<ProcurementGrade> questions = query.list();
		session.flush();
		session.close();
		return questions;

	}
	
	@Override
	public List<Object[]> listCustomerIdAndNameByExporter(Long id) {
		return list("SELECT DISTINCT c.id,c.customerName from Customer c where c.status=0 and c.exporter.id= ?",id);
	}
	
	@Override
	public List<Packhouse> listActivePackhouse() {
		return list("FROM Packhouse f  WHERE f.status=1 and f.exporter.status=1 and f.exporter.isActive=0 ORDER BY f.name ASC");
	}
	
	@Override
	public Agent findAgentByProfileAndBranchIdActive(String agentID, String branchID) {
		Session session = getSessionFactory().openSession();
		org.hibernate.Filter branchFilter = session.getEnabledFilter(ISecurityFilter.BRANCH_FILTER);
		if (!ObjectUtil.isEmpty(branchFilter)) {
			session.disableFilter(ISecurityFilter.BRANCH_FILTER);
		}
		Query query = session.createQuery("From Agent agent where agent.profileId=:agentId and agent.exporter.status=1 and agent.exporter.isActive=0");
		query.setParameter("agentId", agentID);
		List<Agent> aglis =query.list();
		Agent agent = aglis!=null && aglis.size()>0 ? (Agent) query.list().get(0) :null;
		session.flush();
		session.close();
		return agent;
	}
	
	public void processShipmentandharvest(ExporterRegistration expReg){
		// TODO Auto-generated method stub
				
				
				Session session = getSessionFactory().openSession();
				Query query = session.createQuery("update Shipment s set s.status=1 where s.packhouse.exporter.id ='" + expReg.getId() + "' and s.status != 2");
				Query query1 = session.createQuery("update Harvest h set h.status=1 where h.farmCrops.exporter.id ='" + expReg.getId() + "' and h.status != 2");
				
				session.flush();
				session.close();
						
				
	}
	
	public void processShipmentInactive(Integer long1,List<Shipment> sh){
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		sh.stream().forEach(uu -> {
		Query query = session.createQuery("update Shipment s set s.status=:status where s.id = :expId and s.status != 2");
		query.setParameter("status", long1 );
		query.setParameter("expId", uu.getId() );
		int result = query.executeUpdate();
		session.flush();
		});
		session.close();
				
	}
	
	public void processHarvestInactive(Integer long1,List<Harvest> long2){
		// TODO Auto-generated method stub
		Session session = getSessionFactory().openSession();
		long2.stream().forEach(uu -> {
		Query query = session.createQuery("update Harvest h set h.status=:status where h.id = :expId and h.status != 2");
		query.setParameter("status", long1 );
		query.setParameter("expId", uu.getId() );
		int result = query.executeUpdate();
		session.flush();
		});
		session.close();
				
	}
	
	public Planting findPlantingById(Long id) {

		return (Planting) find("FROM Planting fc WHERE fc.id = ?", id);
	}
	
	@Override
	public String findExporterNameById(String table,String id) {
		// TODO Auto-generated method stub
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery("SELECT group_concat(p.COMPANY_NAME) as name FROM "+table+" p WHERE p.id IN ("+id+")");
		List<Object> list = query.list();
		sessions.flush();
		sessions.close();
		if (!ObjectUtil.isListEmpty(list))
			return (String) list.get(0);
		return null;
	}
	
	@Override
	public List<Planting> listPlantingByFarmCropsId(Long farmid) {
		return list("FROM Planting f  where f.farmCrops.id=?  and f.status=1 ORDER BY f.id ASC", farmid);
	}	

}
