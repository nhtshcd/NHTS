package com.sourcetrace.eses.service;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sourcetrace.eses.dao.IUtilDAO;
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
import com.sourcetrace.eses.entity.Device;
import com.sourcetrace.eses.entity.Distribution;
import com.sourcetrace.eses.entity.DocumentUpload;
import com.sourcetrace.eses.entity.DynamicMenuFieldMap;
import com.sourcetrace.eses.entity.DynamicReportConfig;
import com.sourcetrace.eses.entity.ESEAccount;
import com.sourcetrace.eses.entity.ESECard;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.ESETxn;
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
import com.sourcetrace.eses.entity.Packhouse;
import com.sourcetrace.eses.entity.PackhouseIncoming;
import com.sourcetrace.eses.entity.Packing;
import com.sourcetrace.eses.entity.PackingDetail;
import com.sourcetrace.eses.entity.PasswordHistory;
import com.sourcetrace.eses.entity.Pcbp;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.Pmt;
import com.sourcetrace.eses.entity.PmtDetail;
import com.sourcetrace.eses.entity.Procurement;
import com.sourcetrace.eses.entity.ProcurementDetail;
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
import com.sourcetrace.eses.entity.Sorting;
import com.sourcetrace.eses.entity.SprayAndFieldManagement;
import com.sourcetrace.eses.entity.State;
import com.sourcetrace.eses.entity.SubCategory;
import com.sourcetrace.eses.entity.Transaction;
import com.sourcetrace.eses.entity.TransactionLog;
import com.sourcetrace.eses.entity.User;
import com.sourcetrace.eses.entity.Vendor;
import com.sourcetrace.eses.entity.Village;
import com.sourcetrace.eses.entity.VillageWarehouse;
import com.sourcetrace.eses.entity.WarehouseProductDetail;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ICryptoUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@Service
@org.springframework.transaction.annotation.Transactional(value = "transactionManager")
public class UtilService implements IUtilService {

	private static Logger LOGGER = Logger.getLogger(UtilService.class);

	@Autowired
	private IUtilDAO utilDAO;

	@Autowired
	private IFarmerService farmerService;

	@Autowired
	private IUniqueIDGenerator idGenerator;

	@Autowired
	private ICryptoUtil cryptoUtil;

	@Override
	public void save(Object obj) {
		obj = setDefaultValues(obj);

		utilDAO.save(obj);

	}

	private Object setDefaultValues(Object obj) {
		try {
			Long id = (Long) ReflectUtil.getObjectFieldValue(obj, "id");
			if (id == null || id == 0) {
				obj.getClass().getMethod("setCreatedUser", String.class).invoke(obj, ReflectUtil.getCurrentUser());
				Date createdeDate = (Date) ReflectUtil.getObjectFieldValue(obj, "createdDate");
				if (createdeDate == null) {
					obj.getClass().getMethod("setCreatedDate", Date.class).invoke(obj, new Date());
				}
			} else {
				obj.getClass().getMethod("setUpdatedUser", String.class).invoke(obj, ReflectUtil.getCurrentUser());
				Date createdeDate = (Date) ReflectUtil.getObjectFieldValue(obj, "createdDate");
				if (createdeDate == null) {
					obj.getClass().getMethod("setUpdatedDate", Date.class).invoke(obj, new Date());
				}
			}
			obj.getClass().getMethod("setIpAddr", String.class).invoke(obj, ReflectUtil.getCurrentIPaddress());

			String latitude = (String) ReflectUtil.getObjectFieldValue(obj, "latitude");
			String longitude = (String) ReflectUtil.getObjectFieldValue(obj, "longitude");
			if (latitude == null) {
				obj.getClass().getMethod("setLatitude", String.class).invoke(obj, ReflectUtil.getCurrentLatitude());
			}

			if (longitude == null) {
				obj.getClass().getMethod("setLongitude", String.class).invoke(obj, ReflectUtil.getCurrentLongitude());
			}

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			
		}

		return obj;
	}

	@Override
	public void saveOrUpdate(Object obj) {

		obj = setDefaultValues(obj);

		utilDAO.saveOrUpdate(obj);
	}

	public void updateDevice(Device device) {

		utilDAO.update(device);
	}

	public void editAgent(Agent agent) {

		utilDAO.update(agent);
	}

	@Override
	public void delete(Object tt) {
		utilDAO.delete(tt);
	}

	@Override
	public Device findDeviceBySerialNumber(String serialNo) {
		return utilDAO.findDeviceBySerialNumber(serialNo);
	}

	@Override
	public Agent findAgentByProfileAndBranchId(String agentId, String branchId) {

		return utilDAO.findAgentByProfileAndBranchId(agentId, branchId);
	}

	@Override
	public void update(Object obj) {
		try {
			obj.getClass().getMethod("setUpdatedUser", String.class).invoke(obj, ReflectUtil.getCurrentUser());
			obj.getClass().getMethod("setUpdatedDate", Date.class).invoke(obj, new Date());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		utilDAO.update(obj);

	}

	@Override
	public ESESystem findPrefernceById(String string) {
		return utilDAO.findPrefernceById(string);
	}

	@Override
	public BranchMaster findBranchMasterByBranchId(String branchId) {
		return utilDAO.findBranchMasterByBranchId(branchId);
	}

	@Override
	public ESESystem findPrefernceByOrganisationId(String branchId) {
		return utilDAO.findPrefernceByOrganisationId(branchId);
	}

	@Override
	public ESEAccount findAccountByProfileIdAndProfileType(String profId, int agentAccount) {
		return utilDAO.findAccountByProfileIdAndProfileType(profId, agentAccount);
	}

	@Override
	public String findCurrentSeasonCodeByBranchId(String branchId) {
		return utilDAO.findCurrentSeasonCodeByBranchId(branchId);
	}

	@Override
	public Long findCustomerLatestRevisionNo() {
		return utilDAO.findCustomerLatestRevisionNo();
	}

	@Override
	public Long findTopicCategoryLatestRevisionNo() {
		return utilDAO.findTopicCategoryLatestRevisionNo();
	}

	@Override
	public Long findTopicLatestRevisionNo() {
		return utilDAO.findTopicLatestRevisionNo();
	}

	@Override
	public Long findFarmerTrainingLatestRevisionNo() {
		return utilDAO.findFarmerTrainingLatestRevisionNo();
	}

	@Override
	public List<Object[]> findAgroPrefernceDetailById(String systemEse) {

		return utilDAO.findAgroPrefernceDetailById(systemEse);
	}

	@Override
	public void updateAgentBODStatus(String profId, int bod) {
		utilDAO.updateAgentBODStatus(profId, bod);

	}

	@Override
	public String findPrefernceByName(String farmerIdLength) {

		return utilDAO.findPrefernceByName(farmerIdLength);
	}

	public List<FarmCatalogue> listCatalogueByRevisionNo(Long revNo) {
		return utilDAO.listCatalogueByRevisionNo(revNo);
	}

	public List<LanguagePreferences> listLangPrefByCodes(List<String> codes) {
		return utilDAO.listLangPrefByCodes(codes);
	}

	@Override
	public List<Customer> listCustomerByRevNo(Long valueOf) {

		return utilDAO.listCustomerByRevNo(valueOf);
	}

	@Override
	public List<Object[]> listFDVSForFolloUp(String agentId, String folloRev) {

		return utilDAO.listFDVSForFolloUp(agentId, folloRev);
	}

	@Override
	public List<DynamicMenuFieldMap> listDynamisMenubyscoreType() {

		return utilDAO.listDynamisMenubyscoreType();
	}

	@Override
	public Agent findAgentByProfileId(String agentId) {

		return utilDAO.findAgentByProfileId(agentId);
	}

	@Override
	public List<Object[]> listDistributionBalanceDownload(Long id, String strevisionNo) {

		return utilDAO.listDistributionBalanceDownload(id, strevisionNo);
	}

	@Override
	public List<Country> listCountriesWithAll() {

		return utilDAO.listCountriesWithAll();
	}

	@Override
	public List<Country> listCountriesByRevisionNo(long countryRevisionNo) {

		return utilDAO.listCountriesByRevisionNo(countryRevisionNo);
	}

	@Override
	public List<State> listStatesByRevisionNo(long stateRevisionNo) {

		return utilDAO.listStatesByRevisionNo(stateRevisionNo);
	}

	@Override
	public List<Locality> listLocalitiesByRevisionNo(long districtRevisionNo) {

		return utilDAO.listLocalitiesByRevisionNo(districtRevisionNo);
	}

	@Override
	public List<Municipality> listMunicipalitiesByRevisionNo(long cityRevisionNo) {

		return utilDAO.listMunicipalitiesByRevisionNo(cityRevisionNo);
	}

	@Override
	public List<GramPanchayat> listGramPanchayatsByRevisionNo(long panchayatRevisionNo) {

		return utilDAO.listGramPanchayatsByRevisionNo(panchayatRevisionNo);
	}

	@Override
	public List<Village> listVillagesByRevisionNo(long villageRevisionNo) {

		return utilDAO.listVillagesByRevisionNo(villageRevisionNo);
	}

	@Override
	public List<Product> listProductByRevisionNo(Long valueOf, String branchId) {

		return utilDAO.listProductByRevisionNo(valueOf, branchId);
	}

	@Override
	public FarmCatalogue findCatalogueByCode(String code) {

		return utilDAO.findCatalogueByCode(code);
	}

	@Override
	public List<Object[]> listSupplierProcurementDetailProperties(String ssDate, String eeDate) {

		return utilDAO.listSupplierProcurementDetailProperties(ssDate, eeDate);
	}

	@Override
	public List<Object[]> listOfGroup() {

		return utilDAO.listOfGroup();
	}

	/*
	 * @Override public List<Object[]> listWarehouses() {
	 * 
	 * return utilDAO.listWarehouses(); }
	 */

	@Override
	public List<HarvestSeason> listHarvestSeasons() {

		return utilDAO.listHarvestSeasons();
	}

	@Override
	public List<Pmt> findTransferStockByGinner(Integer ginner, Long id, String seasonCode) {

		return utilDAO.findTransferStockByGinner(ginner, id, seasonCode);
	}

	@Override
	public List<Object[]> listGinningProcessByHeapProductAndGinning(String code, Long id, Long id2, String startDate,
			String endDate, String seasonCode) {

		return utilDAO.listGinningProcessByHeapProductAndGinning(code, id, id2, startDate, endDate, seasonCode);
	}

	@Override
	public List<Object[]> listProcurementTraceabilityStockbyAgent(String agentId, Long valueOf, String season) {

		return utilDAO.listProcurementTraceabilityStockbyAgent(agentId, valueOf, season);
	}

	@Override
	public List<CityWarehouse> listProcurementStockByAgentIdRevisionNo(String agentId, Long valueOf) {

		return utilDAO.listProcurementStockByAgentIdRevisionNo(agentId, valueOf);
	}

	@Override
	public List<CityWarehouse> listCityWarehouseByAgentIdRevisionNo(String agentId, Long valueOf) {

		return utilDAO.listCityWarehouseByAgentIdRevisionNo(agentId, valueOf);
	}

	@Override
	public List<PackhouseIncoming> listWarehouseProductByAgentRevisionNoStockByBatch(Long id, Long valueOf) {

		return utilDAO.listWarehouseProductByAgentRevisionNoStockByBatch(id, valueOf);
	}

	@Override
	public List<PackhouseIncoming> listWarehouseProductByAgentRevisionNoStockAndSeasonCodeByBatch(Long id, Long valueOf,
			String seasonCode) {

		return utilDAO.listWarehouseProductByAgentRevisionNoStockAndSeasonCodeByBatch(id, valueOf, seasonCode);
	}

	@Override
	public List<String> listSyncLogins() {
		return utilDAO.listSyncLogins();
	}

	@Override
	public Role loadRole(Long id) {
		return utilDAO.loadRole(id);
	}

	@Override
	public List<Entitlement> listEntitlements(String username) {
		return utilDAO.listEntitlements(username);
	}

	@Override
	public User findByUsername(String username) {
		return utilDAO.findByUsername(username);
	}

	@Override
	public String findLocaleProperty(String prop, String string) {

		return utilDAO.findLocaleProperty(prop, string);
	}

	@Override
	public List<Customer> listCustomerByRevisionNo(Long revNo) {
		return utilDAO.listCustomerByRevisionNo(revNo);
	}

	@Override
	public List<FarmCatalogueMaster> listCatalogueMasters() {
		return utilDAO.listCatalogueMasters();
	}

	@Override
	public FarmCatalogueMaster findCatalogueMasterByTypez(Integer id) {
		return utilDAO.findCatalogueMasterByTypez(id);
	}

	@Override
	public HarvestSeason findHarvestSeasonbyCode(String asString) {

		return utilDAO.findHarvestSeasonbyCode(asString);
	}

	@Override
	public List<Product> listProduct() {

		return utilDAO.listProduct();
	}

	@Override
	public Product findProductByCode(String asString) {

		return utilDAO.findProductByCode(asString);
	}

	@Override
	public List<SubCategory> listSubCategory() {

		return utilDAO.listSubCategory();
	}

	@Override
	public SubCategory findSubCategoryByid(Long id) {

		return utilDAO.findSubCategoryByid(id);
	}

	@Override
	public SubCategory findCategoryByCode(String asString) {

		return utilDAO.findCategoryByCode(asString);
	}

	@Override
	public List<Vendor> listVendor() {

		return utilDAO.listVendor();
	}

	@Override
	public Vendor findVendorById(String Id) {

		return utilDAO.findVendorById(Id);
	}

	@Override
	public List<Customer> listCustomer() {

		return utilDAO.listCustomer();
	}
	/*
	 * @Override public List<Pcbp> listPcbp() {
	 * 
	 * return utilDAO.listPcbp(); }
	 */

	@Override
	public List<Object[]> listPcbp() {

		return utilDAO.listPcbp();
	}

	@Override
	public Customer findCustomerBycustomerId(String customerId) {
		return utilDAO.findCustomerBycustomerId(customerId);
	}

	@Override
	public List<State> listStatesByRevisionNoAndCountryCode(String countryCode, long stateRevisionNo) {

		return utilDAO.listStatesByRevisionNo(stateRevisionNo);
	}

	@Override
	public List<Locality> listDistrictsByRevisionNoAndStateCode(String stateCode, long revisionNo) {
		return utilDAO.listDistrictsByRevisionNoAndStateCode(stateCode, revisionNo);
	}

	@Override
	public List<GramPanchayat> listGrampanchayatByRevisionNoAndCityCode(String cityCode, long revisionNo) {
		return utilDAO.listGrampanchayatByRevisionNoAndCityCode(cityCode, revisionNo);
	}

	@Override
	public List<Municipality> listCityByRevisionNoAndDistrictCode(String districtCode, long revisionNo) {
		return utilDAO.listCityByRevisionNoAndDistrictCode(districtCode, revisionNo);
	}

	@Override
	public List<Village> listVillageByRevisionNoAndCityCode(String gpCode, long revisionNo) {
		return utilDAO.listVillageByRevisionNoAndCityCode(gpCode, revisionNo);
	}

	@Override
	public Village findVillageByCode(String villageCode) {
		return utilDAO.findVillageByCode(villageCode);
	}

	@Override
	public List<FarmCatalogue> listCatalogueByCatalogueMasterType(Integer catalogueType) {
		return utilDAO.listCatalogueByCatalogueMasterType(catalogueType);
	}

	@Override
	public SeasonMaster findSeasonBySeasonCode(String seasonCode) {
		return utilDAO.findSeasonBySeasonCode(seasonCode);
	}

	public void createESECard(String profileId, String cardId, Date txnTime, int type) {

		ESECard card = new ESECard();
		card.setCardId(cardId);
		card.setType(type);
		card.setCreateTime(txnTime);
		card.setIssueDate(txnTime);
		card.setProfileId(profileId);
		card.setStatus(ESECard.INACTIVE);
		card.setCardRewritable(ESECard.IS_REWRITABLE_NO);
		utilDAO.save(card);
	}

	public void addCatalogue(FarmCatalogue farmcatalogue) {
		utilDAO.save(farmcatalogue);
		if (farmcatalogue.getLanguagePref() != null && !ObjectUtil.isListEmpty(farmcatalogue.getLanguagePref())) {
			for (LanguagePreferences preferences : farmcatalogue.getLanguagePref()) {
				preferences.setCode(farmcatalogue.getCode());
				if (preferences.getName().isEmpty()) {
					preferences.setName(farmcatalogue.getName());
				}

				utilDAO.save(preferences);
			}
		}

	}

	@Override
	public Agent findAgentByAgentId(String agentId) {
		return utilDAO.findAgentByAgentId(agentId);
	}

	@Override
	public AgentAccessLog findAgentAccessLogByAgentId(String agentId, Date txnDate) {
		return utilDAO.findAgentAccessLogByAgentId(agentId, txnDate);
	}

	public ESECard findESECardByCardId(String cardId) {
		return utilDAO.findESECardByCardId(cardId);
	}

	public AgentAccessLogDetail findAgentAccessLogDetailByTxn(Long accessId, String txnType) {
		return utilDAO.findAgentAccessLogDetailByTxn(accessId, txnType);
	}

	public Village findVillageById(Long id) {
		return utilDAO.findVillageById(id);
	}

	public ESESystem findPrefernceById(String id, String tenantId) {
		return utilDAO.findPrefernceById(id, tenantId);
	}

	public void updateAgroTxnVillageWarehouseAndAccount(List<AgroTransaction> existingList) {

		if (!ObjectUtil.isListEmpty(existingList)) {
			Procurement procurement = utilDAO.findProcurementByRecNo(existingList.get(0).getReceiptNo());
			ESEAccount agentAccount = null;
			ESEAccount farmerAccount = utilDAO.findAccountByProfileIdAndProfileType(existingList.get(0).getFarmerId(),
					ESEAccount.FARMER_ACCOUNT);
			for (AgroTransaction agroTxn : existingList) {
				agroTxn.setOperType(ESETxn.VOID);
				utilDAO.update(agroTxn);
				if (Profile.AGENT.equalsIgnoreCase(agroTxn.getProfType())) {
					agentAccount = utilDAO.findAccountByProfileIdAndProfileType(agroTxn.getAgentId(),
							ESEAccount.AGENT_ACCOUNT);
					agentAccount.setBalance(agentAccount.getBalance() + agroTxn.getTxnAmt());

				} else {
					if (Procurement.PROCUREMENT_AMOUNT.equalsIgnoreCase(agroTxn.getTxnDesc()))
						farmerAccount.setBalance(farmerAccount.getBalance() + agroTxn.getTxnAmt());
					else if (Procurement.PROCUREMENT_PAYMENT.equalsIgnoreCase(agroTxn.getTxnDesc()))
						farmerAccount.setBalance(farmerAccount.getBalance() - agroTxn.getTxnAmt());
				}

			}
			if (!ObjectUtil.isEmpty(agentAccount))
				utilDAO.update(agentAccount);
			utilDAO.update(farmerAccount);
			updateVillageWarehouse(procurement, ESETxn.VOID);
		}

	}

	private void updateVillageWarehouse(Procurement procurement, int operationType) {

		AgroTransaction tmpTransaction;
		for (ProcurementDetail proDetail : procurement.getProcurementDetails()) {
			tmpTransaction = utilDAO.findEseAccountByTransaction(procurement.getTrxnAgroId());
			VillageWarehouse existingVillageWarehouse = utilDAO.findVillageWarehouse((long) procurement.getVillageId(),
					tmpTransaction.getAgentId(), proDetail.getQuality());

			if (ObjectUtil.isEmpty(existingVillageWarehouse)) {
				if (ESETxn.VOID != operationType) {
					VillageWarehouse villageWarehouse = new VillageWarehouse();
					villageWarehouse.setVillageId(procurement.getVillageId());

					villageWarehouse.setGrossWeight(proDetail.getGrossWeight());
					villageWarehouse.setNumberOfBags(proDetail.getNumberOfBags());
					villageWarehouse.setAgentId(tmpTransaction.getAgentId());
					villageWarehouse.setIsDelete(VillageWarehouse.NOT_DELETED);
					villageWarehouse.setQuality(proDetail.getQuality());
					utilDAO.save(villageWarehouse);
				}
			} else {
				long totalNumberOfBags;
				double totalGrossWeight;
				if (ESETxn.VOID == operationType) {
					totalNumberOfBags = existingVillageWarehouse.getNumberOfBags() - proDetail.getNumberOfBags();
					totalGrossWeight = existingVillageWarehouse.getGrossWeight() - proDetail.getGrossWeight();
				} else {
					totalNumberOfBags = existingVillageWarehouse.getNumberOfBags() + proDetail.getNumberOfBags();
					totalGrossWeight = existingVillageWarehouse.getGrossWeight() + proDetail.getGrossWeight();
				}
				existingVillageWarehouse.setNumberOfBags(totalNumberOfBags);
				existingVillageWarehouse.setGrossWeight(totalGrossWeight);
				utilDAO.update(existingVillageWarehouse);
			}
		}
	}

	public AgroTransaction findEseAccountByTransaction(long id) {
		return utilDAO.findEseAccountByTransaction(id);
	}

	public SeasonMaster findCurrentSeason() {
		return utilDAO.findCurrentSeason();
	}

	public void addProcurement(Procurement procurement) {
		AgroTransaction tmpAgroTxn = utilDAO.findEseAccountByTransaction(procurement.getTrxnAgroId());
		Farmer tmpFarmer;
		ESEAccount intialBal = utilDAO.findESEAccountByProfileId(tmpAgroTxn.getFarmerId(), ESEAccount.CONTRACT_ACCOUNT);
		Double initBal = 0.0;
		if (!ObjectUtil.isEmpty(intialBal)) {
			initBal = intialBal.getCashBalance();
		}
		ESESystem eseSystem = utilDAO.findPrefernceById("1");

		AgroTransaction existingAgroTxn = tmpAgroTxn;
		processTransaction(procurement);
		procurement.setTrxnAgroId(tmpAgroTxn.getId());
		tmpAgroTxn.setTxnDesc(Procurement.PROCUREMENT_AMOUNT);
		Double bal;

		if (!ObjectUtil.isEmpty(intialBal)) {
			bal = intialBal.getCashBalance();
			if (eseSystem.getPreferences().get(ESESystem.ENABLE_LOAN_MODULE).equals("1")) {

				tmpAgroTxn.setTxnDesc(Procurement.REPAYMENT_AMOUNT);
				tmpAgroTxn.setInitialBalance(bal + procurement.getTotalAmount());
				tmpAgroTxn.setTxnAmt(procurement.getLoanAmt() + procurement.getPaymentAmt());
				tmpAgroTxn.setBalAmt(bal + procurement.getFinalPayAmount() - procurement.getPaymentAmt());
				tmpAgroTxn.setEseAccount(intialBal);
				tmpAgroTxn.setCreditAmount(procurement.getFinalPayAmount() - procurement.getPaymentAmt());
				tmpAgroTxn.setProcurement(procurement);
				tmpAgroTxn.setInitialBalance(initBal);
				tmpAgroTxn.setSeasonCode(procurement.getSeasonCode());
				intialBal.setCashBalance(bal + procurement.getFinalPayAmount() - procurement.getPaymentAmt());
				if (intialBal.getOutstandingLoanAmount() >= procurement.getLoanAmt()) {
					intialBal.setOutstandingLoanAmount(intialBal.getOutstandingLoanAmount() - procurement.getLoanAmt());
				}

			} else {

				tmpAgroTxn.setBalAmt(bal + procurement.getTotalAmount() - procurement.getPaymentAmt());
				tmpAgroTxn.setInitialBalance(bal + procurement.getTotalAmount());
				tmpAgroTxn.setTxnAmt(procurement.getPaymentAmt());
				tmpAgroTxn.setEseAccount(intialBal);
				tmpAgroTxn.setCreditAmount(procurement.getTotalAmount() - procurement.getPaymentAmt());
				tmpAgroTxn.setProcurement(procurement);
				tmpAgroTxn.setInitialBalance(initBal);
				tmpAgroTxn.setSeasonCode(procurement.getSeasonCode());
				intialBal.setCashBalance(bal + procurement.getTotalAmount() - procurement.getPaymentAmt());
			}

			utilDAO.update(intialBal);
		}

		utilDAO.save(procurement);

	}

	public VillageWarehouse findVillageWarehouse(long villageId, String agentId) {
		return utilDAO.findVillageWarehouse(villageId, agentId);
	}

	public ESEAccount findESEAccountByProfileId(String profileId, int type) {
		return utilDAO.findESEAccountByProfileId(profileId, type);
	}

	private void processTransaction(Object object) {

		Farmer farmer = null;
		ESEAccount farmerAccount = null, agentAccount = null;

		SeasonMaster season = findCurrentSeason();
		boolean isRegisteredFarmer = false;
		PackhouseIncoming packhouseIncoming;
		ESESystem eseSystem = utilDAO.findPrefernceById("1");
		AgroTransaction tmpTransaction;

		if (!ObjectUtil.isEmpty(object)) {
			if (object instanceof Procurement) {
				Procurement procurement = (Procurement) object;
				tmpTransaction = utilDAO.findEseAccountByTransaction(procurement.getTrxnAgroId());
				if (tmpTransaction.getFarmerId() != null) {
					isRegisteredFarmer = true;
				}
				// Finds registered farmer
				if (isRegisteredFarmer)
					farmer = farmerService.findFarmerByFarmerId(tmpTransaction.getFarmerId());
				// Finds the Procurement product

				// Finds Farmer Account
				if (!ObjectUtil.isEmpty(season) && isRegisteredFarmer && !ObjectUtil.isEmpty(farmer)) {
					farmerAccount = utilDAO.findAccountBySeassonProcurmentProductFarmer(season.getId(), farmer.getId());
				}
				// Checks whether payment available
				tmpTransaction = utilDAO.findEseAccountByTransaction(procurement.getTrxnAgroId());
				Agent agent = utilDAO.findAgentByProfileId(tmpTransaction.getAgentId());
				if (!ObjectUtil.isEmpty(agent)) {

					agentAccount = utilDAO.findAccountByProfileIdAndProfileType(agent.getProfileId(),
							ESEAccount.AGENT_ACCOUNT);
					if (!ObjectUtil.isEmpty(agentAccount)) {
						tmpTransaction = utilDAO.findEseAccountByTransaction(procurement.getTrxnAgroId());
						tmpTransaction.setTxnDesc(Procurement.PROCUREMENT_PAYMENT_AMOUNT);
						AgroTransaction agentPaymentTransaction = buildAgroTransaction(tmpTransaction);
						agentPaymentTransaction.setProfType(Profile.AGENT);
						if (!ObjectUtil.isEmpty(agent) && agent.isCoOperativeManager()) {
							agentPaymentTransaction.setProfType(Profile.CO_OPEARATIVE_MANAGER);
						}
						if (!ObjectUtil.isEmpty(agentAccount)) {
							agentPaymentTransaction.setTxnDesc(Procurement.PROCUREMENT_AMOUNT);
							agentPaymentTransaction.setInitialBalance(agentAccount.getCashBalance());

							if (eseSystem.getPreferences().get(ESESystem.ENABLE_LOAN_MODULE).equals("1")) {
								agentPaymentTransaction.setTxnAmt(procurement.getFinalPayAmount());
								agentPaymentTransaction
										.setBalAmt(agentAccount.getCashBalance() - procurement.getFinalPayAmount());

								agentAccount.setCashBalance(
										agentAccount.getCashBalance() - procurement.getFinalPayAmount());
							} else {
								agentPaymentTransaction.setTxnAmt(procurement.getPaymentAmt());
								agentPaymentTransaction
										.setBalAmt(agentAccount.getCashBalance() - procurement.getPaymentAmt());

								agentAccount
										.setCashBalance(agentAccount.getCashBalance() - procurement.getPaymentAmt());
							}

							agentPaymentTransaction.setEseAccount(agentAccount);
							utilDAO.update(agentAccount);
						}
						utilDAO.save(agentPaymentTransaction);
						tmpTransaction = utilDAO.findEseAccountByTransaction(procurement.getTrxnAgroId());
						AgroTransaction farmerPaymentTransaction = buildAgroTransaction(tmpTransaction);
						farmerPaymentTransaction.setSeasonCode(procurement.getSeasonCode());
						if (!ObjectUtil.isEmpty(farmerAccount)) {
							farmerPaymentTransaction.setTxnDesc(Procurement.PROCURMEMENT);
							farmerPaymentTransaction.setInitialBalance(farmerAccount.getCashBalance());
							farmerPaymentTransaction.setTxnAmt(procurement.getTotalAmount());
							farmerPaymentTransaction
									.setBalAmt(farmerAccount.getCashBalance() + procurement.getTotalAmount());
							farmerAccount.setCashBalance(farmerAccount.getCashBalance() + procurement.getTotalAmount());
							farmerPaymentTransaction.setEseAccount(farmerAccount);
							utilDAO.update(farmerAccount);
						}
						utilDAO.save(farmerPaymentTransaction);

					}
				}

			} else if (object instanceof Distribution) {
				Distribution distribution = (Distribution) object;

				// Distribution may happen with Un registered farmer also
				if (!"N/A".equalsIgnoreCase(distribution.getAgroTransaction().getFarmerId())) {
					isRegisteredFarmer = true;
				}
				// Finds registered farmer
				if (isRegisteredFarmer)
					farmer = farmerService.findFarmerByFarmerId(distribution.getAgroTransaction().getFarmerId());
				// Finds Farmer Account
				if (!ObjectUtil.isEmpty(season) && isRegisteredFarmer && !ObjectUtil.isEmpty(farmer)) {
					farmerAccount = utilDAO.findAccountBySeassonProcurmentProductFarmer(season.getId(), farmer.getId());
				}

				// Checks whether is payment available
				if (distribution.isPaymentAvailable()) {

					if (distribution.getAgroTransaction().getProductStock()
							.equalsIgnoreCase(PackhouseIncoming.StockType.AGENT_STOCK.name())) {

						Agent agent = utilDAO.findAgentByProfileId(distribution.getAgroTransaction().getAgentId());
						if (!ObjectUtil.isEmpty(agent)) {
							agentAccount = utilDAO.findAccountByProfileIdAndProfileType(agent.getProfileId(),
									ESEAccount.AGENT_ACCOUNT);
							if (!ObjectUtil.isEmpty(agentAccount)) {
								distribution.getAgroTransaction().setTxnDesc(Distribution.DISTRIBUTION_PAYMENT_AMOUNT);
								// Agent account will be added with payment
								// amount
								AgroTransaction agentPaymentTransaction = buildAgroTransaction(
										distribution.getAgroTransaction());
								agentPaymentTransaction.setProfType(Profile.AGENT);
								if (!ObjectUtil.isEmpty(agent) && agent.isCoOperativeManager()) {
									agentPaymentTransaction.setProfType(Profile.CO_OPEARATIVE_MANAGER);
								}
								agentPaymentTransaction.calculateBalance(agentAccount, distribution.getPaymentAmount(),
										false, true);
								utilDAO.save(agentPaymentTransaction);
								// Farmer account will be detected with
								// transaction
								// amount
								distribution.getAgroTransaction().calculateBalance(farmerAccount,
										distribution.getAgroTransaction().getTxnAmt(), false, false);

								utilDAO.save(distribution.getAgroTransaction());

								// Farmer account will be added with payment
								// amount
								AgroTransaction farmerPaymentTransaction = buildAgroTransaction(
										distribution.getAgroTransaction());
								farmerPaymentTransaction.calculateBalance(farmerAccount,
										distribution.getPaymentAmount(), false, true);
								utilDAO.save(farmerPaymentTransaction);

								ESESystem preferences = utilDAO.findPrefernceById("1");
								String isInterestModule = preferences.getPreferences().get(ESESystem.INTEREST_MODULE);

							}
						}
					} else if (distribution.getAgroTransaction().getProductStock().equalsIgnoreCase(
							PackhouseIncoming.StockType.WAREHOUSE_STOCK.name()) && !ObjectUtil.isEmpty(farmerAccount)) {
						// Farmer account will be detected with transaction
						// amount
						distribution.getAgroTransaction().calculateBalance(farmerAccount,
								distribution.getAgroTransaction().getTxnAmt(), false, false);
						utilDAO.save(distribution.getAgroTransaction());
						distribution.getAgroTransaction().setTxnDesc(Distribution.DISTRIBUTION_PAYMENT_AMOUNT);
						// Farmer account will be added with payment amount
						AgroTransaction farmerPaymentTransaction = buildAgroTransaction(
								distribution.getAgroTransaction());
						farmerPaymentTransaction.calculateBalance(farmerAccount, distribution.getPaymentAmount(), false,
								true);
						utilDAO.save(farmerPaymentTransaction);

						ESESystem preferences = utilDAO.findPrefernceById("1");
						String isInterestModule = preferences.getPreferences().get(ESESystem.INTEREST_MODULE);

					} else if (distribution.getAgroTransaction().getProductStock().equalsIgnoreCase(
							PackhouseIncoming.StockType.WAREHOUSE_STOCK.name()) && ObjectUtil.isEmpty(farmerAccount)) {
						utilDAO.save(distribution.getAgroTransaction());
					} else if (distribution.getAgroTransaction().getProductStock().equalsIgnoreCase(
							PackhouseIncoming.StockType.AGENT_STOCK.name()) && ObjectUtil.isEmpty(farmerAccount)) {

						Agent agent = utilDAO.findAgentByProfileId(distribution.getAgroTransaction().getAgentId());
						if (!ObjectUtil.isEmpty(agent)) {
							agentAccount = utilDAO.findAccountByProfileIdAndProfileType(agent.getProfileId(),
									ESEAccount.AGENT_ACCOUNT);
							if (!ObjectUtil.isEmpty(agentAccount)) {
								// Agent account will be added with payment
								// amount
								AgroTransaction agentPaymentTransaction = buildAgroTransaction(
										distribution.getAgroTransaction());
								agentPaymentTransaction.setTxnDesc(Distribution.DISTRIBUTION_PAYMENT_AMOUNT);

								agentPaymentTransaction.setProfType(Profile.AGENT);
								if (!ObjectUtil.isEmpty(agent) && agent.isCoOperativeManager()) {
									agentPaymentTransaction.setProfType(Profile.CO_OPEARATIVE_MANAGER);
								}
								agentPaymentTransaction.calculateBalance(agentAccount, distribution.getPaymentAmount(),
										false, true);
								utilDAO.save(agentPaymentTransaction);
							}
						}
						utilDAO.save(distribution.getAgroTransaction());
					}
				} else {
					// Farmer account will be detected with transaction amount
					distribution.getAgroTransaction().calculateBalance(farmerAccount,
							distribution.getAgroTransaction().getTxnAmt(), false, false);
					utilDAO.save(distribution.getAgroTransaction());
				}
			}

		}
	}

	private void processCityWarehouseDetail(CityWarehouse cityWarehouse, Map<String, Object> map, boolean flag) {
		HttpSession httpSession = ReflectUtil.getCurrentHttpSession();
		HttpServletRequest request = ReflectUtil.getCurrentHttpRequest();
		String tenant = !StringUtil.isEmpty(ISecurityFilter.DEFAULT_TENANT_ID) ? ISecurityFilter.DEFAULT_TENANT_ID : "";
		if (!ObjectUtil.isEmpty(request)) {
			tenant = !StringUtil.isEmpty((String) request.getSession().getAttribute(ISecurityFilter.TENANT_ID))
					? (String) request.getSession().getAttribute(ISecurityFilter.TENANT_ID) : "";
		}
		CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail();
		cityWarehouseDetail.setCityWarehouse(cityWarehouse);
		cityWarehouseDetail.setDate((Date) map.get("DATE"));
		cityWarehouseDetail.setType((Integer) map.get("TYPE"));
		cityWarehouseDetail.setReferenceId((Long) map.get("REFERENCE_ID"));
		cityWarehouseDetail.setPreviousGrossWeight(cityWarehouse.getHarvestedWeight());
		cityWarehouseDetail.setTxnNoOfBags((Long) map.get("TXN_NO_OF_BAGS"));
		cityWarehouseDetail.setTxnGrossWeight((Double) map.get("TXN_GROSS_WEIGHT"));
		cityWarehouseDetail.setDescription((String) map.get("DESCRIPTION"));
		if (!tenant.equalsIgnoreCase("griffith")) {
			cityWarehouseDetail
					.setBatchNo(!ObjectUtil.isEmpty(cityWarehouse.getBatchNo()) ? cityWarehouse.getBatchNo() : "");
		} else {
			cityWarehouseDetail.setBatchNo((String) map.get("BATCH_NO"));
		}

		if (flag) { // Adding of procurement stock
			cityWarehouseDetail.setTotalGrossWeight(
					cityWarehouseDetail.getPreviousGrossWeight() + cityWarehouseDetail.getTxnGrossWeight());
		} else { // Detecting of procurement stock
			cityWarehouseDetail.setTotalGrossWeight(
					cityWarehouseDetail.getPreviousGrossWeight() - cityWarehouseDetail.getTxnGrossWeight());
		}

		utilDAO.save(cityWarehouseDetail);
		cityWarehouse.setHarvestedWeight(cityWarehouseDetail.getTotalGrossWeight());
		cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.saveOrUpdate(cityWarehouse);
	}

	private void processCityWarehouseDetail(CityWarehouse cityWarehouse, Map<String, Object> map, boolean flag,
			String tenantId) {

		CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail();
		cityWarehouseDetail.setCityWarehouse(cityWarehouse);
		cityWarehouseDetail.setDate((Date) map.get("DATE"));
		cityWarehouseDetail.setType((Integer) map.get("TYPE"));
		cityWarehouseDetail.setReferenceId((Long) map.get("REFERENCE_ID"));
		cityWarehouseDetail.setPreviousGrossWeight(cityWarehouse.getHarvestedWeight());
		cityWarehouseDetail.setTxnNoOfBags((Long) map.get("TXN_NO_OF_BAGS"));
		cityWarehouseDetail.setTxnGrossWeight((Double) map.get("TXN_GROSS_WEIGHT"));
		cityWarehouseDetail.setDescription((String) map.get("DESCRIPTION"));
		if (flag) { // Adding of procurement stock
			cityWarehouseDetail.setTotalGrossWeight(
					cityWarehouseDetail.getPreviousGrossWeight() + cityWarehouseDetail.getTxnGrossWeight());
		} else { // Detecting of procurement stock
			cityWarehouseDetail.setTotalGrossWeight(
					cityWarehouseDetail.getPreviousGrossWeight() - cityWarehouseDetail.getTxnGrossWeight());
		}
		cityWarehouse.setHarvestedWeight(cityWarehouseDetail.getTotalGrossWeight());
		cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());

		utilDAO.saveOrUpdateCityWarehouse(cityWarehouse, tenantId);
		utilDAO.saveCityWarehouseDetail(cityWarehouseDetail, tenantId);
	}

	private AgroTransaction buildAgroTransaction(AgroTransaction eAgroTransaction) {

		AgroTransaction nAgroTransaction = new AgroTransaction();
		nAgroTransaction.setReceiptNo(eAgroTransaction.getReceiptNo());
		nAgroTransaction.setAgentId(eAgroTransaction.getAgentId());
		nAgroTransaction.setAgentName(eAgroTransaction.getAgentName());
		nAgroTransaction.setDeviceId(eAgroTransaction.getDeviceId());
		nAgroTransaction.setDeviceName(eAgroTransaction.getDeviceName());
		nAgroTransaction.setServicePointId(eAgroTransaction.getServicePointId());
		nAgroTransaction.setServicePointName(eAgroTransaction.getServicePointName());
		nAgroTransaction.setFarmerId(eAgroTransaction.getFarmerId());
		nAgroTransaction.setFarmerName(eAgroTransaction.getFarmerName());
		nAgroTransaction.setProfType(eAgroTransaction.getProfType());
		nAgroTransaction.setTxnType(eAgroTransaction.getTxnType());
		nAgroTransaction.setOperType(eAgroTransaction.getOperType());
		nAgroTransaction.setTxnTime(eAgroTransaction.getTxnTime());
		nAgroTransaction.setTxnDesc(eAgroTransaction.getTxnDesc());
		nAgroTransaction.setSamithi(eAgroTransaction.getSamithi());
		return nAgroTransaction;
	}

	public CityWarehouse findCityWarehouseByCoOperative(long coOperativeId, String agentId) {
		return utilDAO.findCityWarehouseByCoOperative(coOperativeId, agentId);
	}

	@Override
	public Long findVillageWarehouselatestRevisionNo() {
		return utilDAO.findVillageWarehouselatestRevisionNo();
	}

	public CityWarehouse findCityWarehouseByCoOperative(long coOperativeId, String agentId, String quality) {
		return utilDAO.findCityWarehouseByCoOperative(coOperativeId, agentId, quality);
	}

	public CityWarehouse findCityWarehouseByVillage(long villageId, String agentId, String quality) {
		return utilDAO.findCityWarehouseByVillage(villageId, agentId, quality);
	}

	private void processCityWarehouseDetailByState(CityWarehouse cityWarehouseByState, Map<String, Object> map,
			boolean flag) {
		HttpSession httpSession = ReflectUtil.getCurrentHttpSession();
		HttpServletRequest request = ReflectUtil.getCurrentHttpRequest();
		String tenant = !StringUtil.isEmpty(ISecurityFilter.DEFAULT_TENANT_ID) ? ISecurityFilter.DEFAULT_TENANT_ID : "";
		if (!ObjectUtil.isEmpty(request)) {
			tenant = !StringUtil.isEmpty((String) request.getSession().getAttribute(ISecurityFilter.TENANT_ID))
					? (String) request.getSession().getAttribute(ISecurityFilter.TENANT_ID) : "";
		}
		CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail();
		cityWarehouseDetail.setCityWarehouse(cityWarehouseByState);
		cityWarehouseDetail.setDate((Date) map.get("DATE"));
		cityWarehouseDetail.setType((Integer) map.get("TYPE"));
		cityWarehouseDetail.setReferenceId((Long) map.get("REFERENCE_ID"));
		cityWarehouseDetail.setPreviousGrossWeight(cityWarehouseByState.getHarvestedWeight());
		cityWarehouseDetail.setTxnNoOfBags((Long) map.get("TXN_NO_OF_BAGS"));
		cityWarehouseDetail.setTxnGrossWeight((Double) map.get("TXN_GROSS_WEIGHT"));
		cityWarehouseDetail.setDescription((String) map.get("DESCRIPTION"));
		if (!tenant.equalsIgnoreCase("griffith")) {
			cityWarehouseDetail.setBatchNo(
					!ObjectUtil.isEmpty(cityWarehouseByState.getBatchNo()) ? cityWarehouseByState.getBatchNo() : "");
		} else {
			cityWarehouseDetail.setBatchNo((String) map.get("BATCH_NO"));
		}

		if (flag) { // Adding of procurement stock
			cityWarehouseDetail.setTotalGrossWeight(
					cityWarehouseDetail.getPreviousGrossWeight() + cityWarehouseDetail.getTxnGrossWeight());
		} else { // Detecting of procurement stock
			cityWarehouseDetail.setTotalGrossWeight(
					cityWarehouseDetail.getPreviousGrossWeight() - cityWarehouseDetail.getTxnGrossWeight());
		}

		utilDAO.save(cityWarehouseDetail);
		cityWarehouseByState.setHarvestedWeight(cityWarehouseDetail.getTotalGrossWeight());
		cityWarehouseByState.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.saveOrUpdate(cityWarehouseByState);
	}

	public CityWarehouse findCityWareHouseByWarehouseIdProcrmentGradeCodeAndProcurementProductId(long warehouseId,
			String gradeCode) {
		return utilDAO.findCityWareHouseByWarehouseIdProcrmentGradeCodeAndProcurementProductId(warehouseId, gradeCode);
	}

	public CityWarehouse findCityWarehouseByState(long stateId, long productId, String quality, String tenantId) {
		return utilDAO.findCityWarehouseByState(stateId, productId, quality, tenantId);
	}

	public CityWarehouse findCityWarehouseByState(long stateId, long productId, String quality) {
		return utilDAO.findCityWarehouseByState(stateId, productId, quality);
	}

	public ESEAccount findAccountBySeassonProcurmentProductFarmer(long seasonId, long farmerId) {
		return utilDAO.findAccountBySeassonProcurmentProductFarmer(seasonId, farmerId);
	}

	public CityWarehouse findSupplierWarehouseByCoOperative(long coOperativeId, String quality, String agentId) {
		return utilDAO.findSupplierWarehouseByCoOperative(coOperativeId, quality, agentId);
	}

	public CityWarehouse findSupplierWarehouseByCoOperativeProductAndGrade(long coOperativeId, String quality) {
		return utilDAO.findSupplierWarehouseByCoOperativeProductAndGrade(coOperativeId, quality);
	}

	public ESEAccount findAccountByFarmerLoanProduct(long farmerId) {
		return utilDAO.findAccountByFarmerLoanProduct(farmerId);
	}

	public void saveOrUpdateCityWarehouse(CityWarehouse cityWarehouse, String tenantId) {
		utilDAO.saveOrUpdateCityWarehouse(cityWarehouse, tenantId);
	}

	public void saveCityWarehouseDetail(CityWarehouseDetail cityWarehouseDetail, String tenantId) {
		utilDAO.saveCityWarehouseDetail(cityWarehouseDetail, tenantId);
	}

	public CityWarehouse findCityWarehouseByWarehouseProductBatchNoGradeFarmer(long warehouseId, long productId,
			String batchNo, String grade, String coldStorageName, String blockName, String floorName, String bayNum,
			long farmerId) {
		return utilDAO.findCityWarehouseByWarehouseProductBatchNoGradeFarmer(warehouseId, productId, batchNo, grade,
				coldStorageName, blockName, floorName, bayNum, farmerId);
	}

	public VillageWarehouse findVillageWarehouse(long villageId, String agentId, String qualityCode) {
		return utilDAO.findVillageWarehouse(villageId, agentId, qualityCode);
	}

	@Override
	public Procurement findProcurementByRecNo(String receiptNo) {
		return utilDAO.findProcurementByRecNo(receiptNo);
	}

	@Override
	public ESEAccount findAccountByProfileId(String profileId) {
		return utilDAO.findAccountByProfileId(profileId);
	}

	public Customer findCustomerById(String customerId) {
		return utilDAO.findCustomerById(customerId);
	}

	public Customer findCustomerByName(String customerName) {
		return utilDAO.findCustomerByName(customerName);
	}

	public void updateAgentReceiptNoSequence(String agentId, String offlineReceiptNo) {
		utilDAO.updateAgentReceiptNoSequence(agentId, offlineReceiptNo);
	}

	public List<AgroTransaction> findAgroTxnByReceiptNo(String receiptNo) {
		return utilDAO.findAgroTxnByReceiptNo(receiptNo);
	}

	public Pmt findPMTByReceiptNumber(String receiptNumber) {
		return utilDAO.findPMTByReceiptNumber(receiptNumber);
	}

	public String addProcurementMTNT(Pmt pmt) {

		if (!ObjectUtil.isEmpty(pmt) && !ObjectUtil.isListEmpty(pmt.getPmtDetails())) {
			if (StringUtil.isEmpty(pmt.getMtntReceiptNumber())) {
				pmt.setMtntReceiptNumber(idGenerator.getMTNTReceiptNoSeq());
				utilDAO.save(pmt);
				return pmt.getMtntReceiptNumber();
			}
			utilDAO.saveOrUpdate(pmt);

		}
		return null;
	}

	public List<FarmCatalogue> listCatalogues() {
		return utilDAO.listCatalogues();
	}

	@Override
	public Distribution findDistributionByReceiptNoTxnType(String receiptNo, String txnType) {
		return utilDAO.findDistributionByReceiptNoTxnType(receiptNo, txnType);
	}

	@Override
	public PackhouseIncoming findAgentAvailableStock(long agentId, long prodId) {
		return utilDAO.findAgentAvailableStock(agentId, prodId);
	}

	public void SaveOrUpdateByTenant(Object obj, String tenantId) {
		utilDAO.SaveOrUpdateByTenant(obj, tenantId);
	}

	public void saveOrUpdate(Object warehouseProduct, String tenantId) {
		utilDAO.saveOrUpdate(warehouseProduct, tenantId);
	}

	public void save(WarehouseProductDetail warehouseProductDetail, String tenantId) {
		utilDAO.save(warehouseProductDetail, tenantId);
	}

	public PackhouseIncoming findFieldStaffAvailableStock(String agentId, long productId, String tenantId) {
		return utilDAO.findFieldStaffAvailableStock(agentId, productId, tenantId);
	}

	private AgroTransaction processAgroTransaction(AgroTransaction agroTransaction) {

		AgroTransaction refAgroTransaction = new AgroTransaction();
		refAgroTransaction.setReceiptNo(agroTransaction.getReceiptNo());
		refAgroTransaction.setTxnTime(agroTransaction.getTxnTime());
		refAgroTransaction.setFarmerId(agroTransaction.getFarmerId());
		refAgroTransaction.setFarmerName(agroTransaction.getFarmerName());
		refAgroTransaction.setSamithi(agroTransaction.getSamithi());
		refAgroTransaction.setAgentId(agroTransaction.getAgentId());
		refAgroTransaction.setAgentName(agroTransaction.getAgentName());
		refAgroTransaction.setServicePointId(agroTransaction.getServicePointId());
		refAgroTransaction.setServicePointName(agroTransaction.getServicePointName());
		refAgroTransaction.setDeviceId(agroTransaction.getDeviceId());
		refAgroTransaction.setDeviceName(agroTransaction.getDeviceName());
		refAgroTransaction.setOperType(agroTransaction.getOperType());
		refAgroTransaction.setProfType(Profile.CLIENT);
		refAgroTransaction.setTxnType(Distribution.PRODUCT_DISTRIBUTION_TO_FARMER);
		refAgroTransaction.setTxnDesc(Distribution.PRODUCT_DISTRIBUTION_TO_FARMER_DESCRIPTION);
		Farmer farmer = farmerService.findFarmerByFarmerId(agroTransaction.getFarmerId());
		SeasonMaster season = findCurrentSeason();
		ESEAccount farmerAccount = null;
		if (!ObjectUtil.isEmpty(farmer) && !ObjectUtil.isEmpty(season)) {
			farmerAccount = utilDAO.findAccountBySeassonProcurmentProductFarmer(season.getId(), farmer.getId());
			if (!ObjectUtil.isEmpty(farmerAccount)) {
				refAgroTransaction.setInitialBalance(farmerAccount.getDistributionBalance());
				refAgroTransaction.setTxnAmt(agroTransaction.getTxnAmt());
				refAgroTransaction.setBalAmt(refAgroTransaction.getInitialBalance() - refAgroTransaction.getTxnAmt());
				refAgroTransaction.setEseAccount(farmerAccount);
				farmerAccount.setDistributionBalance(refAgroTransaction.getBalAmt());
			}
		}
		return refAgroTransaction;
	}

	public void updateESEAccountCashBalById(long id, double cashBalance) {
		utilDAO.updateESEAccountCashBalById(id, cashBalance);
	}

	public String findPrefernceByName(String enableApproved, String tenantId) {
		return utilDAO.findPrefernceByName(enableApproved, tenantId);
	}

	public void saveByTenantId(Object object, String tenantId) {
		utilDAO.saveByTenantId(object, tenantId);
	}

	public PackhouseIncoming findCooperativeAvailableStockByCooperative(long agentId, long productId, String tenantId) {
		return utilDAO.findCooperativeAvailableStockByCooperative(agentId, productId, tenantId);
	}

	public PackhouseIncoming findAgentAvailableStock(String agentId, long productId, String tenantId) {
		return utilDAO.findAgentAvailableStock(agentId, productId, tenantId);
	}

	public Agent findAgentByProfileId(String profileId, String tenantId) {
		return utilDAO.findAgentByProfileId(profileId, tenantId);
	}

	public PackhouseIncoming findAvailableStock(long warehouseId, long productId) {
		return utilDAO.findAvailableStock(warehouseId, productId);
	}

	public ESEAccount findAccountBySeassonProcurmentProductFarmer(long seasonId, long farmerId, String tenantId) {
		return utilDAO.findAccountBySeassonProcurmentProductFarmer(seasonId, farmerId, tenantId);
	}

	public PackhouseIncoming findAvailableStockByWarehouseIdSelectedProductSeasonBatchNo(long warehouseId,
			long productId, String selectedSeason, String batchNo) {
		return utilDAO.findAvailableStockByWarehouseIdSelectedProductSeasonBatchNo(warehouseId, productId,
				selectedSeason, batchNo);
	}

	public PackhouseIncoming findAvailableStockByWarehouseIdProductIdBatchNoAndSeason(long warehouseId, long productId,
			String batchNo, String seasonCode) {
		return utilDAO.findAvailableStockByWarehouseIdProductIdBatchNoAndSeason(warehouseId, productId, batchNo,
				seasonCode);
	}

	public PackhouseIncoming findAgentAvailableStock(String agentId, long productId) {
		return utilDAO.findAgentAvailableStock(agentId, productId);
	}

	public PackhouseIncoming findWarehouseStockByWarehouseIdAndProductId(long warehouseId, long productId,
			String season) {
		return utilDAO.findWarehouseStockByWarehouseIdAndProductId(warehouseId, productId, season);
	}

	public Product findProductById(long id) {
		return utilDAO.findProductById(id);
	}

	public void saveAgroTransaction(AgroTransaction agroTransaction, String tenantId) {
		utilDAO.saveAgroTransaction(agroTransaction, tenantId);
	}

	public void saveDistribution(Distribution distribution, String tenantId) {
		utilDAO.saveDistribution(distribution, tenantId);
	}

	public ESEAccount findAccountByProfileIdAndProfileType(String profileId, int type, String tenantId) {
		return utilDAO.findAccountByProfileIdAndProfileType(profileId, type, tenantId);
	}

	public ESEAccount findAccountByProfileId(String profileId, String tenantId) {
		return utilDAO.findAccountByProfileId(profileId, tenantId);
	}

	public void updateESEAccount(ESEAccount farmerAccount, String tenantId) {
		utilDAO.updateESEAccount(farmerAccount, tenantId);
	}

	private AgroTransaction processAgroTransactionTenant(AgroTransaction agroTransaction, String tenantId) {

		AgroTransaction refAgroTransaction = new AgroTransaction();
		refAgroTransaction.setReceiptNo(agroTransaction.getReceiptNo());
		refAgroTransaction.setTxnTime(agroTransaction.getTxnTime());
		refAgroTransaction.setFarmerId(agroTransaction.getFarmerId());
		refAgroTransaction.setFarmerName(agroTransaction.getFarmerName());
		refAgroTransaction.setSamithi(agroTransaction.getSamithi());
		refAgroTransaction.setAgentId(agroTransaction.getAgentId());
		refAgroTransaction.setAgentName(agroTransaction.getAgentName());
		refAgroTransaction.setServicePointId(agroTransaction.getServicePointId());
		refAgroTransaction.setServicePointName(agroTransaction.getServicePointName());
		refAgroTransaction.setDeviceId(agroTransaction.getDeviceId());
		refAgroTransaction.setDeviceName(agroTransaction.getDeviceName());
		refAgroTransaction.setOperType(agroTransaction.getOperType());
		refAgroTransaction.setProfType(Profile.CLIENT);
		refAgroTransaction.setTxnType(Distribution.PRODUCT_DISTRIBUTION_TO_FARMER);
		refAgroTransaction.setTxnDesc(Distribution.PRODUCT_DISTRIBUTION_TO_FARMER_DESCRIPTION);
		Farmer farmer = farmerService.findFarmerByFarmerId(agroTransaction.getFarmerId(), tenantId);
		SeasonMaster season = findCurrentSeason();
		ESEAccount farmerAccount = null;
		if (!ObjectUtil.isEmpty(farmer) && !ObjectUtil.isEmpty(season)) {
			farmerAccount = utilDAO.findAccountBySeassonProcurmentProductFarmer(season.getId(), farmer.getId(),
					tenantId);
			if (!ObjectUtil.isEmpty(farmerAccount)) {
				refAgroTransaction.setInitialBalance(farmerAccount.getDistributionBalance());
				refAgroTransaction.setTxnAmt(agroTransaction.getTxnAmt());
				refAgroTransaction.setBalAmt(refAgroTransaction.getInitialBalance() - refAgroTransaction.getTxnAmt());
				refAgroTransaction.setEseAccount(farmerAccount);
				farmerAccount.setDistributionBalance(refAgroTransaction.getBalAmt());
			}
		}
		return refAgroTransaction;
	}

	public List<AgroTransaction> listFarmerTransactionHistory(String farmerId, String[] transactionArray, int limit) {

		return utilDAO.listFarmerTransactionHistory(farmerId, transactionArray, limit);
	}

	@Override
	public Customer findCustomer(long id) {
		return utilDAO.findCustomer(id);

	}

	@Override
	public void updateCashBal(long id, double cashBalance, double creditBalance) {
		utilDAO.updateCashBal(id, cashBalance, creditBalance);
	}

	@Override
	public ESEAccount findEseAccountByBuyerIdAndTypee(String profileId, int type) {
		return utilDAO.findEseAccountByBuyerIdAndTypee(profileId, type);
	}

	@Override
	public ESEAccount findEseAccountByProfileId(String profileId) {
		return utilDAO.findEseAccountByProfileId(profileId);
	}

	@Override
	public void saveAgroTransactionForPayment(AgroTransaction farmerPaymentTxn, AgroTransaction agentPaymentTxn) {

		String description = farmerPaymentTxn.getTxnDesc();
		if (!StringUtil.isEmpty(description)) {
			/*
			 * String[] descriptionDetail = description.split("\\|"); if
			 * (PaymentMode.DISTRIBUTION_ADVANCE_PAYMENT_MODE_NAME.
			 * equalsIgnoreCase(descriptionDetail[0]) ||
			 * PaymentMode.DISTRIBUTION_PAYMENT_MODE_NAME.equalsIgnoreCase(
			 * descriptionDetail[0])) { // DISTRIBUTION ADVANCE PAYMENT
			 * farmerPaymentTxn.calculateBalance(farmerPaymentTxn.getEseAccount(
			 * ), farmerPaymentTxn.getTxnAmt(), false, true);
			 * agentPaymentTxn.calculateBalance(agentPaymentTxn.getEseAccount(),
			 * agentPaymentTxn.getTxnAmt(), false, true);
			 * 
			 * ESESystem eseSystem = utilDAO.findPrefernceById("1"); } else if
			 * (PaymentMode.DISTRIBUTION_ADVANCE_PAYMENT_MODE_NAME.
			 * equalsIgnoreCase(description) ||
			 * PaymentMode.DISTRIBUTION_PAYMENT_MODE_NAME.equalsIgnoreCase(
			 * description)) { // DISTRIBUTION ADVANCE PAYMENT
			 * farmerPaymentTxn.calculateBalance(farmerPaymentTxn.getEseAccount(
			 * ), farmerPaymentTxn.getTxnAmt(), false, true);
			 * agentPaymentTxn.calculateBalance(agentPaymentTxn.getEseAccount(),
			 * agentPaymentTxn.getTxnAmt(), false, true);
			 * 
			 * ESESystem eseSystem = utilDAO.findPrefernceById("1"); } else {//
			 * OTHER PAYMENTS
			 * farmerPaymentTxn.calculateBalance(farmerPaymentTxn.getEseAccount(
			 * ), farmerPaymentTxn.getTxnAmt(), true, false);
			 * agentPaymentTxn.calculateBalance(agentPaymentTxn.getEseAccount(),
			 * agentPaymentTxn.getTxnAmt(), true, false); }
			 * 
			 * utilDAO.save(farmerPaymentTxn); if
			 * (!PaymentMode.LOAN_REPAYMENT_MODE_NAME.equalsIgnoreCase(
			 * descriptionDetail[0])) { utilDAO.save(agentPaymentTxn); }
			 * 
			 * if (!StringUtil.isEmpty(farmerPaymentTxn.getBalAmt())) {
			 * utilDAO.updateESEAccountCashBalById(farmerPaymentTxn.
			 * getEseAccount().getId(), farmerPaymentTxn.getBalAmt()); }
			 * 
			 * if (!PaymentMode.LOAN_REPAYMENT_MODE_NAME.equalsIgnoreCase(
			 * descriptionDetail[0])) { if
			 * (!StringUtil.isEmpty(agentPaymentTxn.getBalAmt())) {
			 * utilDAO.updateESEAccountCashBalById(agentPaymentTxn.getEseAccount
			 * ().getId(), agentPaymentTxn.getBalAmt()); }
			 * 
			 * }
			 */}
	}

	public void updateESEAccountOutStandingBalById(long id, double loanBalance) {
		utilDAO.updateESEAccountOutStandingBalById(id, loanBalance);
	}

	@Override
	public List<Object[]> listMaxTypeByFarmerId(Long farmerId) {
		return utilDAO.listMaxTypeByFarmerId(farmerId);
	}

	@Override
	public FarmCatalogue findCatalogueById(Long id) {
		return utilDAO.findCatalogueById(id);
	}

	@Override
	public FarmCatalogue findCatalogueByName(String name) {
		return utilDAO.findCatalogueByName(name);
	}

	@Override
	public FarmCatalogueMaster findFarmCatalogueMasterById(Long id) {
		return utilDAO.findFarmCatalogueMasterById(id);
	}

	@Override
	public String findCatalogueValueByCode(String code) {
		return utilDAO.findCatalogueValueByCode(code);
	}

	@Override
	public List<Agent> listAgentByWarehouseAndRevisionNo(long warehouseId, String revisionNo) {
		return utilDAO.listAgentByWarehouseAndRevisionNo(warehouseId, revisionNo);
	}

	@Override
	public List<Agent> listAgentByRevisionNo(String branchId, Long revisionNo) {
		return utilDAO.listAgentByRevisionNo(branchId, revisionNo);
	}

	@Override
	public List<Object[]> listFarmsLastInspectionDate() {
		return utilDAO.listFarmsLastInspectionDate();
	}

	/*
	 * @Override public List<HarvestSeason> listHarvestSeasonByRevisionNo(Long
	 * revNo) { return utilDAO.listHarvestSeasonByRevisionNo(revNo); }
	 */

	@Override
	public Vendor findVendorByDbId(String id) {
		return utilDAO.findVendorByDbId(id);
	}

	@Override
	public List<Vendor> listVendorByFilter(String vendorId, String vendorName, String mobileNo) {
		return utilDAO.listVendorByFilter(vendorId, vendorName, mobileNo);
	}

	@Override
	public void deleteCityWarehouseByWhId(String id) {
		utilDAO.deleteCityWarehouseByWhId(id);
	}

	@Override
	public List<CityWarehouse> listCityWarehouseByCooperativeId(Long id) {
		return utilDAO.listCityWarehouseByCooperativeId(id);
	}

	@Override
	public void deletePmtWarehouseByWhId(long id) {
		utilDAO.deletePmtWarehouseByWhId(id);
	}

	@Override
	public List<Pmt> listPmtByCooperativeId(Long id) {
		return utilDAO.listPmtByCooperativeId(id);
	}

	@Override
	public void deleteWarehouseAgroTrxnByWhId(long id) {
		utilDAO.deleteWarehouseAgroTrxnByWhId(id);
	}

	public boolean findCooperativeMappedWithWarhousePayment(long warhouseId) {

		// WarehousePayment warehouse =
		// utilDAO.findCooperativeMappedWithWarhousePayment(warhouseId);
		boolean flag = false;
		/*
		 * if (!ObjectUtil.isEmpty(warehouse)) flag = true;
		 */

		return flag;
	}

	@Override
	public boolean findCooperativeMappedWithSamithi(String code) {

		// Warehouse warehouse = utilDAO.findCooperativeMappedWithSamithi(code);
		boolean flag = false;
		/*
		 * if (!ObjectUtil.isEmpty(warehouse)) flag = true;
		 */

		return flag;
	}

	@Override
	public boolean findAgentMappedWithWarehouse(long id) {
		return utilDAO.findAgentMappedWithWarehouse(id);
	}

	@Override
	public List<FarmCatalogue> listCataloguesByUnit() {
		return utilDAO.listCataloguesByUnit();
	}

	@Override
	public void remove(Object object) {
		utilDAO.delete(object);
	}

	@Override
	public PmtDetail findPMTDetailByGradeId(long gradeId) {
		return utilDAO.findPMTDetailByGradeId(gradeId);
	}

	@Override
	public void addTransactionLog(TransactionLog transactionLog) {

		utilDAO.save(transactionLog);
	}

	@Override
	public AgentAccessLogDetail listAgnetAccessLogDetailsByIdTxnType(long id, String type, String msgNo) {
		return utilDAO.listAgnetAccessLogDetailsByIdTxnType(id, type, msgNo);
	}

	@Override
	public void addDevice(Device newDevice) {
		utilDAO.save(newDevice);

	}

	@Override
	public TransactionLog findTransactionLogById(long id) {

		return utilDAO.findTransactionLogById(id);
	}

	@Override
	public void updateTransactionLog(TransactionLog transactionLog) {

		utilDAO.update(transactionLog);
	}

	@Override
	public void createTransaction(Transaction transaction) {

		utilDAO.save(transaction);
	}

	@Override
	public void editTransaction(Transaction transaction) {

		utilDAO.update(transaction);
	}

	@Override
	public List<Language> listLanguages() {
		// TODO Auto-generated method stub
		return utilDAO.listLanguages();
	}

	@Override
	public String getESEDateTimeFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> findParentBranches() {
		return utilDAO.findParentBranches();
	}

	@Override
	public List<BranchMaster> listChildBranchIds(String parentBranch) {
		return utilDAO.listChildBranchIds(parentBranch);
	}

	@Override
	public User findUserByUserName(String username) {
		return utilDAO.findByUsername(username);
	}

	@Override
	public List<Object[]> listBranchMastersInfo() {
		// TODO Auto-generated method stub
		return utilDAO.listBranchMastersInfo();
	}

	@Override
	public List<Object[]> findChildBranches() {
		return utilDAO.findChildBranches();
	}

	@Override
	public Object[] findBranchInfo(String branchId) {
		// TODO Auto-generated method stub
		return utilDAO.findBranchInfo(branchId);
	}

	@Override
	public List<Object[]> listParentBranchMastersInfo() {
		return utilDAO.listParentBranchMastersInfo();
	}

	@Override
	public byte[] findLogoByCode(String code) {
		return utilDAO.findLogoByCode(code);
	}

	@Override
	public List<LocaleProperty> listLocalePropByLang(String lang) {
		// TODO Auto-generated method stub
		return utilDAO.listLocalePropByLang(lang);
	}

	@Override
	public String findCurrentSeasonCode() {
		HttpSession httpSession = ReflectUtil.getCurrentHttpSession();
		Object branchObject = httpSession.getAttribute(ISecurityFilter.CURRENT_BRANCH);
		String currentBranch = ObjectUtil.isEmpty(branchObject) ? ESESystem.SYSTEM_ESE_NAME : branchObject.toString();
		ESESystem ese = findPrefernceByOrganisationId(currentBranch);
		String currentSeasonCode = "";
		if (!ObjectUtil.isEmpty(ese)) {
			ESESystem preference = utilDAO.findPrefernceById(String.valueOf(ese.getId()));
			currentSeasonCode = preference.getPreferences().get(ESESystem.CURRENT_SEASON_CODE);

			if (StringUtil.isEmpty(currentSeasonCode)) {
				currentSeasonCode = "";
			}
		}
		return currentSeasonCode;
	}

	@Override
	public String findCurrentSeasonName() {

		HttpSession httpSession = ReflectUtil.getCurrentHttpSession();
		Object branchObject = httpSession.getAttribute(ISecurityFilter.CURRENT_BRANCH);
		String currentBranch = ObjectUtil.isEmpty(branchObject) ? ESESystem.SYSTEM_ESE_NAME : branchObject.toString();
		ESESystem ese = findPrefernceByOrganisationId(currentBranch);
		if (!ObjectUtil.isEmpty(ese)) {
			ESESystem preference = utilDAO.findPrefernceById(String.valueOf(ese.getId()));
			String currentSeasonCode = preference.getPreferences().get(ESESystem.CURRENT_SEASON_CODE);
			if (!StringUtil.isEmpty(currentSeasonCode)) {
				String currentSeason = utilDAO.findCurrentSeasonName(currentSeasonCode);
				return currentSeason;
			}
		}
		return "";

	}

	@Override
	public Integer isParentBranch(String loggedUserBranch) {
		return utilDAO.isParentBranch(loggedUserBranch);
	}

	@Override
	public String findAgentTimerValue() {
		return utilDAO.findAgentTimerValue();

	}

	@Override
	public List<LanguagePreferences> listLanguagePreferences() {
		return utilDAO.listLanguagePreferences();

	}

	@Override
	public List<Object[]> listStates() {
		// TODO Auto-generated method stub
		return utilDAO.listStates();
	}

	@Override
	public List<Object[]> listLocalities() {
		// TODO Auto-generated method stub
		return utilDAO.listLocalities();
	}

	@Override
	public List<Municipality> listMunicipality() {
		// TODO Auto-generated method stub
		return utilDAO.listMunicipality();
	}

	@Override
	public List<Object[]> listGramPanchayatIdCodeName() {
		return utilDAO.listGramPanchayatIdCodeName();
	}

	@Override
	public List<Object[]> listVillageIdAndName() {
		// TODO Auto-generated method stub
		return utilDAO.listVillageIdAndName();
	}

	/*
	 * @Override public List<Object[]> listWarehouseIdAndName() { // TODO
	 * Auto-generated method stub return utilDAO.listWarehouseIdAndName(); }
	 */

	public List<Object[]> listProducts() {

		return utilDAO.listProducts();
	}

	@Override
	public List<Object[]> listCustomerIdAndName() {
		// TODO Auto-generated method stub
		return utilDAO.listCustomerIdAndName();
	}

	public List<Customer> listOfCustomers() {

		return utilDAO.listOfCustomers();
	}

	@Override
	public Object[] findMenuInfo(long id) {
		// TODO Auto-generated method stub
		return utilDAO.findMenuInfo(id);
	}

	@Override
	public List<Object[]> listProcurmentGradeByVarityCode(String code) {
		return utilDAO.listProcurmentGradeByVarityCode(code);
	}

	@Override
	public List<Country> listCountries() {

		return utilDAO.listCountries();
	}

	@Override
	public String getESEDateFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveFarmerDynmaicList(List<FarmerDynamicFieldsValue> farmerDynamicFieldValuesList) {
		utilDAO.saveFarmerDynmaicList(farmerDynamicFieldValuesList);
	}

	@Override
	public void updateFarmerDynmaicList(List<FarmerDynamicFieldsValue> farmerDynamicFieldValuesList, Long id) {
		utilDAO.updateFarmerDynmaicList(farmerDynamicFieldValuesList, id);
	}

	@Override
	public DynamicReportConfig findReportById(String name) {
		return utilDAO.findReportById(name);
	}

	@Override
	public FarmCatalogueMaster findFarmCatalogueMasterByName(String name) {
		// TODO Auto-generated method stub
		return utilDAO.findFarmCatalogueMasterByName(name);
	}

	@Override
	public List<FarmCatalogue> findFarmCatalougeByType(int type) {
		return utilDAO.findFarmCatalougeByType(type);
	}

	@Override
	public Language findLanguageByCode(String code) {

		// TODO Auto-generated method stub
		return utilDAO.findLanguageByCode(code);
	}

	@Override
	public void updateUserLanguage(String userName, String lang) {
		// TODO Auto-generated method stub
		utilDAO.updateUserLanguage(userName, lang);
	}

	@Override
	public List<BranchMaster> listBranchMasters() {
		// TODO Auto-generated method stub
		return utilDAO.listBranchMasters();
	}

	public User findUserByProfileId(String id) {

		return utilDAO.findUserByProfileId(id);
	}

	public User findUserByEmailId(String emailId) {

		return utilDAO.findUserByEmailId(emailId);
	}

	public void editUser(User user) {

		utilDAO.update(user);
	}

	public boolean isDashboardUserEntitlementAvailable(String userName) {

		return utilDAO.isDashboardUserEntitlementAvailable(userName);
	}

	public BranchMaster findBranchMasterById(Long id) {
		return utilDAO.findBranchMasterById(id);
	}

	public void addBranchMaster(BranchMaster bm) {
		utilDAO.save(bm);
		ESEAccount account = new ESEAccount();
		account.setAccountNo(idGenerator.createOrganizationAccountNoSequence());
		account.setType(ESEAccount.ORGANIZATION_ACCOUNT);
		account.setAccountType(ESEAccount.COMPANY_ACCOUNT);
		account.setStatus(ESEAccount.ACTIVE);
		account.setBranchId(bm.getBranchId());
		account.setBalance(0.0);
		account.setDistributionBalance(0.0);
		account.setSavingAmount(0.0);
		account.setShareAmount(0.0);
		account.setAccountOpenDate(new Date());
		account.setCreateTime(new Date());
		account.setProfileId(bm.getBranchId());
		account.setCashBalance(bm.getAccountBalance());
		utilDAO.save(account);
	}

	@Override
	public Integer findUserCountByMonth(Date sDate, Date eDate) {

		return utilDAO.findUserCountByMonth(sDate, eDate);
	}

	@Override
	public Integer findMobileUserCountByMonth(Date sDate, Date eDate) {

		return utilDAO.findMobileUserCountByMonth(sDate, eDate);
	}

	public void editBranchMaster(BranchMaster bm) {
		utilDAO.update(bm);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addOrganisationESE(ESESystem ese) {
		utilDAO.save(ese);
	}

	@Override
	public Integer findDeviceCountByMonth(Date sDate, Date eDate) {
		return utilDAO.findDeviceCountByMonth(sDate, eDate);
	}

	@Override
	public Integer findFacilitiesCount() {
		return utilDAO.findFacilitiesCount();
	}

	@Override
	public Integer findWarehouseCountByMonth(Date sDate, Date eDate) {
		return utilDAO.findWarehouseCountByMonth(sDate, eDate);
	}

	@Override
	public Integer findGroupCount() {

		// TODO Auto-generated method stub
		return utilDAO.findGroupCount();
	}

	@Override
	public Integer findGroupCountByMonth(Date sDate, Date eDate) {

		// TODO Auto-generated method stub
		return utilDAO.findGroupCountByMonth(sDate, eDate);
	}

	@Override
	public List<Object> listWarehouseProductAndStockByWarehouseId(Long warehouseId) {
		return utilDAO.listWarehouseProductAndStockByWarehouseId(warehouseId);
	}

	@Override
	public List<Object> listWarehouseProductAndStock() {
		return utilDAO.listWarehouseProductAndStock();
	}

	public List<Device> listDevices() {

		return utilDAO.listDevices();
	}

	@Override
	public List<Object> listcowMilkByMonth(Date sDate, Date eDate) {
		// TODO Auto-generated method stub

		return utilDAO.listcowMilkByMonth(sDate, eDate);
	}

	@Override
	public List<Object> listCropSaleQtyByMoth(Date sDate, Date eDate, String selectedBranch) {
		return utilDAO.listCropSaleQtyByMoth(sDate, eDate, selectedBranch);
	}

	@Override
	public List<Object> listCropHarvestByMoth(Date sDate, Date eDate, String selectedBranch) {
		return utilDAO.listCropHarvestByMoth(sDate, eDate, selectedBranch);
	}

	@Override
	public List<Object> listDistributionQtyByMoth(Date sDate, Date eDate, String selectedBranch) {
		return utilDAO.listDistributionQtyByMoth(sDate, eDate, selectedBranch);
	}

	@Override
	public List<Object> listProcurementAmtByMoth(Date sDate, Date eDate, String selectedBranch) {
		return utilDAO.listProcurementAmtByMoth(sDate, eDate, selectedBranch);
	}

	@Override
	public List<Object> listEnrollmentByMoth(Date sDate, Date eDate) {
		return utilDAO.listEnrollmentByMoth(sDate, eDate);
	}

	@Override
	public List<Village> listVillagesByCityId(long selectedCity) {

		return utilDAO.listVillagesByCityId(selectedCity);
	}

	@Override
	public List<Village> listVillageByBranch(String selectedBranch, Long selectedState) {
		return utilDAO.listVillageByBranch(selectedBranch, selectedState);
	}

	@Override
	public List<String> findBranchList() {
		// TODO Auto-generated method stub
		return utilDAO.findBranchList();
	}

	@Override
	public HarvestSeason findSeasonByCode(String code) {
		return utilDAO.findSeasonByCode(code);
	}

	@Override
	public List<State> listOfStatesByBranch(String selectedBranch) {
		// TODO Auto-generated method stub
		return utilDAO.listOfStatesByBranch(selectedBranch);
	}

	@Override
	public List<Object[]> findCatalogueCodeAndDisNameByType(Integer type) {
		// TODO Auto-generated method stub
		return utilDAO.findCatalogueCodeAndDisNameByType(type);
	}

	@Override
	public List<Object[]> findProcurementDataByFilter(Long selectedProduct, String selectedDate) {
		return utilDAO.findProcurementDataByFilter(selectedProduct, selectedDate);
	}

	@Override
	public List<Object[]> findProcurementCummulativeData() {
		return utilDAO.findProcurementCummulativeData();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addRole(Role aRole) {

		utilDAO.save(aRole);
	}

	public Role findRole(long id) {

		return utilDAO.findRole(id);
	}

	public BranchMaster findBranchMasterByBranchIdAndDisableFilter(String branchId) {
		return utilDAO.findBranchMasterByBranchIdAndDisableFilter(branchId);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void editRole(Role role) {

		utilDAO.update(role);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void removeRole(Role aRole) {

		utilDAO.delete(aRole);
	}

	public List<Role> listRoles() {

		return utilDAO.listRoles();
	}

	public List<Menu> listParentMenus() {

		return utilDAO.listTopParentMenus();
	}

	@Override
	public List<Role> listRolesByTypeAndBranchId(String branchId) {
		// TODO Auto-generated method stub
		return utilDAO.listRolesByTypeAndBranchId(branchId);
	}

	public List<Menu> getSelectedSubMenus(long roleId, long parentMenuId) {

		// find the role for the given id
		Role role = utilDAO.loadRoleMenus(roleId);

		// list to hold selected menus for the given role and top most parent
		List<Menu> selected = new ArrayList<Menu>();

		if (role != null) {
			LOGGER.debug("Menus " + role.getMenus());
			// get menus for the given role
			Set<Menu> menus = role.getMenus();
			boolean dataFiltered = role.isDataFiltered();

			// list flat all sub menus for the given top most parent menu
			List<Menu> subMenus = utilDAO.listFlatSubMenusForTopParent(parentMenuId);

			// loop all sub menus
			for (Menu subMenu : subMenus) {
				// given role has the sub menu, then add to list
				if (menus.contains(subMenu)) {
					if (dataFiltered) {
						if (subMenu.isDataFiltered()) {
							selected.add(subMenu);
						}
					} else {
						selected.add(subMenu);
					}
				}
			}
		}

		return selected;
	}

	@Override
	public Role findRoleExcludeBranch(long selectedRole) {

		return utilDAO.findRoleExcludeBranch(selectedRole);
	}

	public Map<String, List<Menu>> getAvailableSelectedSubMenus(long roleId, long parentMenuId) {

		// load role menus
		Role role = utilDAO.loadRoleMenus(roleId);

		// list flat all sub menus for the given top most parent menu
		List<Menu> subMenus = utilDAO.listFlatSubMenusForTopParent(parentMenuId);

		// create the map and list to hold the selected and available menus
		Map<String, List<Menu>> menusAvailableSelected = new LinkedHashMap<String, List<Menu>>();
		List<Menu> selected = new ArrayList<Menu>();
		menusAvailableSelected.put(SELECTED, selected);
		List<Menu> available = new ArrayList<Menu>();
		menusAvailableSelected.put(AVAILABLE, available);

		if (role != null) {
			// get menus for the given role
			Set<Menu> menus = role.getMenus();
			boolean dataFiltered = role.isDataFiltered();

			// loop all submenus for the given parent menu id
			for (Menu subMenu : subMenus) {
				if (menus.contains(subMenu)) {
					// add the menu to selected, if the set of menu for the role
					// has it
					if (dataFiltered) {
						if (subMenu.isDataFiltered()) {
							selected.add(subMenu);
						}
					} else {
						selected.add(subMenu);
					}
				} else {
					// add the menu to available, if the set of menu for the
					// role does not have it
					if (dataFiltered) {
						if (subMenu.isDataFiltered()) {
							available.add(subMenu);
						}
					} else {
						available.add(subMenu);
					}
				}
			}
		} else {
			available.addAll(subMenus);
		}

		return menusAvailableSelected;
	}

	@Override
	public Object[] findRoleInfo(long id) {
		// TODO Auto-generated method stub
		return utilDAO.findRoleInfo(id);
	}

	public void editSubMenusForRole(long roleId, long parentMenuId, List<Long> selectedSubMenus) {

		// find the role for the given id
		Role role = utilDAO.loadRoleMenus(roleId);
		Set<Menu> menus = null;

		if (role != null) {
			// get menus for the given role
			menus = role.getMenus();
			// remove menus which were not selected
			removeUnselectedSubMenu(parentMenuId, menus, selectedSubMenus, role);
			// remove parent menus which don't have any child
			removeParentsWithoutChild(menus);
		} else {
			role = utilDAO.findRole(roleId);
			menus = new LinkedHashSet<Menu>();
			role.setMenus(menus);
		}

		// add newly selected menus and any of its missing parent
		addSelectedSubMenu(menus, selectedSubMenus, role);

		// update the menus for the given role and top most parent menu
		utilDAO.update(role);
	}

	private void removeUnselectedSubMenu(Long parentMenuId, Set<Menu> menus, List<Long> selectedSubMenus, Role role) {

		// list to hold the sub menu ids, which have to be removed
		List<Long> removeIds = new ArrayList<Long>();
		// list flat all sub menus for the given top most parent menu
		List<Menu> subMenus = utilDAO.listFlatSubMenusForTopParent(parentMenuId);
		// loop all sub menus
		for (Menu subMenu : subMenus) {
			// submenu is not present in the selected sub menu list
			// add it to removal list
			if (!selectedSubMenus.contains(subMenu.getId())) {
				removeIds.add(subMenu.getId());
			}
		}

		// loop the list of sub menus to be removed
		for (Long id : removeIds) {
			// loop the set of menus for the given role
			for (Menu menu : menus) {
				// menu id equals the id to be removed
				if (menu.getId() == id) {

					// // loading entitlements for Menu name
					// List<Object[]> entitlements =
					// roleDAO.loadEntitlements(menu.getName());
					// if (!entitlements.isEmpty()) {
					// for (Object[] menuEntitlement : entitlements) {
					// long entId =
					// Long.parseLong(menuEntitlement[0].toString());
					// // remove entitlement for the unselected menu
					// roleDAO.removeEntitlement(role.getId(), entId);
					// }
					// }

					// remove the menu from the set of menus
					menus.remove(menu);
					break;
				}
			}
		}
	}

	private void removeParentsWithoutChild(Set<Menu> menus) {

		List<Menu> parentsToRemove = new ArrayList<Menu>();
		for (Menu menu : menus) {
			if (menu.getUrl().equals("javascript:void(0)") && menu.getParentId() != null) {
				boolean childExists = false;
				for (Menu aMenu : menus) {
					if (aMenu.getParentId() != null && aMenu.getParentId().equals(menu.getId())) {
						childExists = true;
					}
				}

				if (!childExists) {
					parentsToRemove.add(menu);
				}
			}
		}

		for (Menu parentMenu : parentsToRemove) {
			LOGGER.debug("removing parent menu as it has no child " + parentMenu);
			menus.remove(parentMenu);
		}
	}

	public void addSelectedSubMenu(Set<Menu> menus, List<Long> selectedSubMenus, Role role) {

		if (ObjectUtil.isListEmpty(role.getEntitlements())) {
			role.setEntitlements(new LinkedHashSet<Entitlement>());
		}

		for (Long id : selectedSubMenus) {
			Menu menu = utilDAO.findMenu(id);
			if (!menus.contains(menu)) {
				LOGGER.debug("adding menu " + menu);
				menus.add(menu);
				// When adding a menu for a role,Add the List Entitlement also
				Entitlement ent = utilDAO.findEntitlement(menu.getName() + ".list");
				if (!ObjectUtil.isEmpty(ent)) {
					role.getEntitlements().add(ent);
				}
			}

			Menu parent = utilDAO.findMenu(menu.getParentId());
			if (!menus.contains(parent)) {
				LOGGER.debug("adding parent " + parent);
				menus.add(parent);
			}

			if (parent.getParentId() != null) {
				parent = utilDAO.findMenu(parent.getParentId());
				if (!menus.contains(parent)) {
					LOGGER.debug("adding parent of parent " + parent);
					menus.add(parent);
				}
			}
		}
	}

	public List<Action> listAction() {

		return utilDAO.listAction();
	}

	public Map<String, Map<String, String>> getSelectedSubMenusActions(long roleId, long parentMenuId) {

		List<Menu> selectedSubMenus = getSelectedSubMenus(roleId, parentMenuId);
		Map<String, Map<String, String>> subMenusActions = buildMapForSelectedMenusActions(selectedSubMenus);
		return subMenusActions;
	}

	private Map<String, Map<String, String>> buildMapForSelectedMenusActions(List<Menu> selectedSubMenus) {

		Map<String, Map<String, String>> subMenusActions = new LinkedHashMap<String, Map<String, String>>();

		for (Menu selectedSubMenu : selectedSubMenus) {

			String subMenuName = selectedSubMenu.getName();
			Map<String, String> selectedActions = new LinkedHashMap<String, String>();

			for (Action action : selectedSubMenu.getActions()) {
				String actionName = action.getName();
				String entitlementName = subMenuName + "." + actionName;
				selectedActions.put(actionName, entitlementName);
			}
			subMenusActions.put(subMenuName, selectedActions);
		}

		return subMenusActions;
	}

	public List<String> getEntitlements(long roleId, long parentMenuId) {

		Role role = utilDAO.loadRole(roleId);
		List<String> entitlements = new ArrayList<String>();

		if (role != null) {
			Set<Entitlement> ents = role.getEntitlements();
			List<Menu> selectedSubMenus = getSelectedSubMenus(roleId, parentMenuId);
			for (Menu selected : selectedSubMenus) {
				Set<Action> actions = selected.getActions();
				for (Action action : actions) {
					String entitlementName = selected.getName() + "." + action.getName();
					boolean entitled = false;
					for (Entitlement ent : ents) {
						if (entitlementName.equals(ent.getAuthority())) {
							entitled = true;
							break;
						}
					}

					if (entitled) {
						entitlements.add(entitlementName);
					}
				}
			}
		}

		return entitlements;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Role editEntitlements(long roleId, long parentMenuId, List<String> entitlements) {

		Role role = utilDAO.loadRole(roleId);
		Set<Entitlement> ents = null;
		Set<Menu> menus = null;

		if (role == null) {
			role = utilDAO.loadRoleMenus(roleId);
			ents = new LinkedHashSet<Entitlement>();
			if (!ObjectUtil.isEmpty(role)) {
				role.setEntitlements(ents);
			}
		} else {
			ents = role.getEntitlements();
		}

		List<Menu> subMenus = utilDAO.listFlatSubMenusForTopParent(parentMenuId);
		List<Menu> selectedSubMenus = new ArrayList<Menu>();

		if (role != null) {
			menus = role.getMenus();

			for (Menu subMenu : subMenus) {
				if (menus.contains(subMenu)) {
					selectedSubMenus.add(subMenu);
				}
			}
		}

		LOGGER.info("Existing entitlements " + ents);
		LOGGER.info("Selected entitlements " + entitlements);

		List<Entitlement> selectedEnts = new ArrayList<Entitlement>();
		for (String entitlement : entitlements) {
			Entitlement selectedEnt = new Entitlement();
			selectedEnt.setAuthority(entitlement);
			selectedEnts.add(selectedEnt);
		}

		removeUnselectedActions(ents, selectedSubMenus, selectedEnts);

		for (Entitlement selected : selectedEnts) {
			if (!ents.contains(selected)) {
				LOGGER.info("finding entitlement " + selected.getAuthority());
				Entitlement newEntitlement = utilDAO.findEntitlement(selected.getAuthority());
				LOGGER.debug("adding entitlement " + newEntitlement.getId());
				ents.add(newEntitlement);
			}
		}
		if (!ObjectUtil.isEmpty(role)) {
			utilDAO.update(role);
		}
		return role;
	}

	private void removeUnselectedActions(Set<Entitlement> ents, List<Menu> selectedSubMenus,
			List<Entitlement> selectedEnts) {

		for (Menu selectedSubMenu : selectedSubMenus) {

			String subMenuName = selectedSubMenu.getName();

			for (Action action : selectedSubMenu.getActions()) {
				String actionName = action.getName();
				String entitlementName = subMenuName + "." + actionName;
				Entitlement oldEnt = new Entitlement();
				oldEnt.setAuthority(entitlementName);
				if (!selectedEnts.contains(oldEnt)) {
					LOGGER.debug("removing entitlement " + oldEnt);
					ents.remove(oldEnt);
				}
			}
		}
	}

	public BranchMaster findBranchMasterByName(String name) {
		return utilDAO.findBranchMasterByName(name);
	}

	public Role findRoleByName(String name) {

		return utilDAO.findRoleByName(name);
	}

	public List<GramPanchayat> listGramPanchayatsByCityId(long selectedCity) {

		return utilDAO.listGramPanchayatsByCityId(selectedCity);
	}

	public List<Municipality> listMunicipalitiesByLocalityId(long selectedLocality) {

		return utilDAO.listMunicipalitiesByLocalityId(selectedLocality);
	}

	public List<Locality> findLocalityByStateId(long selectedState) {
		return utilDAO.findLocalityByStateId(selectedState);
	}

	public List<State> listStates(String country) {

		return utilDAO.listStates(country);
	}

	public List<Object[]> findLocalityByBranch(String Branch) {
		// TODO Auto-generated method stub
		return utilDAO.findLocalityByBranch(Branch);
	}

	@Override
	public List<Object[]> listLocalityIdCodeAndName() {
		return utilDAO.listLocalityIdCodeAndName();
	}

	@Override
	public List<Object[]> findFarmerCountBySamithiId() {
		// TODO Auto-generated method stub
		return utilDAO.findFarmerCountBySamithiId();
	}

	@Override
	public List<Object[]> listVillageByPanchayatId(Long id) {

		return utilDAO.listVillageByPanchayatId(id);
	}

	@Override
	public List<Object[]> listGramPanchayatByCityId(Long id) {
		return utilDAO.listGramPanchayatByCityId(id);
	}

	@Override
	public List<Object[]> listCityCodeAndNameByDistrictId(Long id) {
		return utilDAO.listCityCodeAndNameByDistrictId(id);
	}

	public List listCity() {

		return utilDAO.listCity();
	}

	public List<Village> listVillageByCity(long id) {

		return utilDAO.listVillageByCity(id);
	}

	public boolean findFarmerMappedWithSamithi(long id) {

		return utilDAO.findFarmerMappedWithSamithi(id);
	}

	public Village findVillage(long id) {

		return utilDAO.findVillage(id);
	}

	@Override
	public Integer findFarmerCountBySamtihi(Long id) {
		return utilDAO.findFarmerCountBySamtihi(id);
	}

	@Override
	public List<BankInfo> findWarehouseBankinfo(long id) {
		// TODO Auto-generated method stub
		return utilDAO.findWarehouseBankinfo(id);
	}

	public List<IdentityType> listIdentityType() {

		return utilDAO.listIdentityType();
	}

	public AgentType findAgentTypeById(long id) {

		return utilDAO.findAgentTypeById(id);
	}

	public Agent findAgent(long id) {

		return utilDAO.findAgent(id);
	}

	public List<AgentType> listAgentType() {

		return utilDAO.listAgentType();
	}

	public void updateIdSeq() {

		utilDAO.updateFarmerIdSequence();
		utilDAO.updateShopDealerIdSequence();

	}

	public List<String> listWarehouseNameByServicePointId(long id) {

		return utilDAO.listWarehouseNameByServicePointId(id);
	}

	public List<String> listWarehouseNameByAgentIdAndServicePointId(long agentId, long servicePointId) {

		return utilDAO.listWarehouseNameByAgentIdAndServicePointId(agentId, servicePointId);
	}

	public List<String> listSelectedSamithiById(Long id) {

		return utilDAO.listSelectedSamithiById(id);
	}

	public List<String> listAvailableSamithi() {

		return utilDAO.listAvailableSamithi();
	}

	public List<String> listSelectedSamithiByAgentId(Long agentId, Long cooperativeId) {

		return utilDAO.listSelectedSamithiByAgentId(agentId, cooperativeId);
	}

	public List<String> listAvailableSamithiByAgentId(Long agentId, Long cooperativeId) {

		return utilDAO.listAvailableSamithiByAgentId(agentId, cooperativeId);
	}

	public List<String> listSamithiByCooperativeId(Long id) {

		return utilDAO.listSamithiByCooperativeId(id);
	}

	public ServicePoint findServicePointByName(String selectedServicePoint) {

		return utilDAO.findServicePointByName(selectedServicePoint);
	}

	public List<String> findServiceLocationBasedOnServicePointAndAgent(long id) {

		return utilDAO.findServiceLocationBasedOnServicePointAndAgent(id);
	}

	public ServicePoint findServicePointByCode(String code) {

		return utilDAO.findServicePointByCode(code);
	}

	public void createAgentESEAccount(String profileId, String accountNo, Date txnTime, int type, Agent agent,
			String branchId) {

		ESEAccount account = new ESEAccount();
		account.setAccountNo(accountNo);
		account.setType(type);
		if (ESEAccount.AGENT_ACCOUNT == type) {
			account.setAccountType(ESEAccount.OPERATOR_ACCOUNT);
			account.setStatus(ESEAccount.ACTIVE);
		} else if (ESEAccount.FARMER_ACCOUNT == type) {
			account.setAccountType(ESEAccount.SAVING_BANK_ACCOUNT);
			account.setStatus(ESEAccount.ACTIVE);
		}
		account.setBranchId(branchId);
		account.setBalance(0.0);
		account.setAccountOpenDate(txnTime);
		account.setCreateTime(txnTime);
		account.setProfileId(profileId);
		account.setCashBalance(0.00);
		utilDAO.save(account);
	}

	public List<Municipality> findMunicipalitiesByPostalCode(String postalCode) {

		return utilDAO.findMunicipalitiesByPostalCode(postalCode);
	}

	public ESECard findCardByProfileId(String profileId) {

		return utilDAO.findESECardByProfile(profileId);
	}

	public List<ServicePoint> listServicePoints() {

		return utilDAO.list();
	}

	public List<Municipality> listMunicipalities(String locality) {

		return utilDAO.listMunicipalities(locality);
	}

	public List<Locality> listLocalities(String state) {

		return utilDAO.listLocalities(state);
	}

	public boolean findAgentIdFromEseTxn(String profileId) {

		return utilDAO.findAgentIdFromEseTxn(profileId);
	}

	public boolean findAgentIdFromDevice(long id) {

		return utilDAO.findAgentIdFromDevice(id);
	}

	public boolean isAgentWarehouseProductStockExist(String profileId) {

		return utilDAO.isAgentWarehouseProductStockExist(profileId);
	}

	public boolean isAgentDistributionExist(String profileId) {

		return utilDAO.isAgentDistributionExist(profileId);
	}

	public List<CityWarehouse> listProcurementStocksForAgent(String agentId) {

		return utilDAO.listProcurementStocksForAgent(agentId);
	}

	public void removeAgentWarehouseProduct(long agentId) {

		List<PackhouseIncoming> products = utilDAO.listWarehouseProductByAgentId(agentId);
		for (PackhouseIncoming product : products) {
			utilDAO.delete(product);
		}
	}

	public void removeCardByProfileId(String profileId) {
		utilDAO.removeCardByProfileId(profileId);
	}

	public void removeAccountByProfileIdAndProfileType(String profileId, int type) {

		utilDAO.removeAccountByProfileIdAndProfileType(profileId, type);

	}

	public void removeAgent(Agent agent) {

		utilDAO.delete(agent);

	}

	public void createAgent(Agent agent) {
		agent.setRevisionNumber(DateUtil.getRevisionNumber());
		utilDAO.save(agent);

	}

	public void updateAccountStatus(String profileId, int status, int type) {

		utilDAO.updateAccountStatus(profileId, status, type);

	}

	public void removeAgentWarehouseMappingByAgentId(long id) {

		utilDAO.removeAgentWarehouseMappingByAgentId(id);
	}

	public Agent findAgent(String profileId) {

		return utilDAO.findAgent(profileId);
	}

	public void editAgentProfile(Agent agent) {
		agent.setRevisionNumber(DateUtil.getRevisionNumber());
		utilDAO.update(agent);

	}

	public void updateCardStatus(String profileId, int status, int cardRewritable) {
		utilDAO.updateCardStatus(profileId, status, cardRewritable);

	}

	@Override
	public List<Role> listRolesByTypeAndBranchIdExcludeBranch(String branchId_F) {

		return utilDAO.listRolesByTypeAndBranchIdExcludeBranch(branchId_F);
	}

	public List<Role> listRolesByType(int type) {

		return utilDAO.listRolesByType(type);
	}

	public void addUser(User user) {

		utilDAO.save(user);
	}

	public User findUser(long id) {

		return utilDAO.findUser(id);
	}

	public void editUserCredential(User user) {

		if (!StringUtil.isEmpty(user.getPassword())) {
			try {
				String clearText = user.getUsername() + user.getPassword();
				String password = cryptoUtil.encrypt(StringUtil.getMulipleOfEight(clearText));
				user.setPassword(password);
			} catch (Exception e) {
				LOGGER.error("Error Encrypting password", e);
			}
		}

		PasswordHistory ph = new PasswordHistory();
		ph.setBranchId(user.getBranchId());
		ph.setCreatedDate(new Date());
		ph.setType(String.valueOf(PasswordHistory.Type.WEB_USER.ordinal()));
		ph.setPassword(user.getPassword());
		ph.setReferenceId(user.getId());
		utilDAO.save(ph);

		user.setRole(user.getRole());

		user.setEnabled(user.isEnabled());
		utilDAO.update(user);

	}

	@Override
	public byte[] findUserImage(long userId) {
		// TODO Auto-generated method stub
		return utilDAO.findUserImage(userId);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void removeUser(User user) {

		utilDAO.delete(user);
	}

	public Device findDeviceById(Long id) {

		return utilDAO.findDeviceById(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void removeDevice(Device device) {

		utilDAO.delete(device);
	}

	public List<Agent> listAgentByAgentTypeNotMappedwithDevice() {
		return utilDAO.listAgentByAgentTypeNotMappedwithDevice();
	}

	@Override
	public Device findUnRegisterDeviceById(Long id) {
		return utilDAO.findUnRegisterDeviceById(id);
	}

	@Override
	public void editOrganisationPreference(Map<String, String> preferences, ESESystem ese) {

		ese.getPreferences().putAll(preferences);
		utilDAO.update(ese);
	}

	public Asset findAssetsByAssetCode(String code) {
		return utilDAO.findAssetsByAssetCode(code);
	}

	public BranchMaster findBranchMasterByIdAndDisableFilter(Long id) {
		return utilDAO.findBranchMasterByIdAndDisableFilter(id);
	}

	public void updateAsset(Asset existing) {
		utilDAO.update(existing);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void editPreference(Map<String, String> preferences) {
		ESESystem existingSystem = findPrefernceById("1");
		existingSystem.getPreferences().putAll(preferences);
		utilDAO.update(existingSystem);

	}

	public List<BranchMaster> listBranchMastersAndDisableFilter() {
		// TODO Auto-generated method stub
		return utilDAO.listBranchMastersAndDisableFilter();
	}

	@Override
	public List<Object> listFieldsByViewNameAndDBName(String dbName, String viewName) {
		return utilDAO.listFieldsByViewNameAndDBName(dbName, viewName);
	}

	@Override
	public void addDynamicReportConfig(DynamicReportConfig dynamicReportConfig) {
		utilDAO.save(dynamicReportConfig);

	}

	@Override
	public List<Object[]> listOfViewByDBName(String dbName) {
		return utilDAO.listOfViewByDBName(dbName);
	}

	@Override
	public FarmCatalogueMaster findFarmCatalogueMasterByCatalogueTypez(Integer valueOf) {
		return utilDAO.findFarmCatalogueMasterByCatalogueTypez(valueOf);
	}

	@Override
	public List<FarmCatalogueMaster> listFarmCatalogueMatsters() {
		return utilDAO.listFarmCatalogueMatsters();
	}

	@Override
	public List<LanguagePreferences> listLangPrefByCode(String code) {
		return utilDAO.listLangPrefByCode(code);
	}

	public void editCatalogue(FarmCatalogue farmcatalogue) {
		utilDAO.update(farmcatalogue);

	}

	@Override
	public List<Language> findIsSurveyStatusLanguage() {
		return utilDAO.findIsSurveyStatusLanguage();
	}

	public void removeCatalogue(String name) {
		utilDAO.delete(findCatalogueByName(name));
	}

	@Override
	public void addFarmCatalogueMaster(FarmCatalogueMaster catalogueMaster) {
		utilDAO.save(catalogueMaster);
	}

	public String findVillageNameByCode(String code) {

		return utilDAO.findVillageNameByCode(code);
	}

	public String findCityNameByCode(String code) {

		return utilDAO.findCityNameByCode(code);
	}

	public List<Object[]> listOfVillageCodeNameByCityCode(String cityCode) {

		return utilDAO.listOfVillageCodeNameByCityCode(cityCode);
	}

	@Override
	public FarmCatalogue findByNameAndType(String educationName, Integer typez) {
		return utilDAO.findByNameAndType(educationName, typez);

	}

	@Override
	public Village findVillageBySelectedVillageId(long parseLong) {
		// TODO Auto-generated method stub
		return utilDAO.findVillageBySelectedVillageId(parseLong);
	}

	public Municipality findMunicipalityByCode(String code) {

		return utilDAO.findMunicipalityByCode(code);
	}

	@Override
	public GramPanchayat findGrampanchaythByCode(String code) {
		// TODO Auto-generated method stub
		return utilDAO.findGrampanchaythByCode(code);
	}

	public void addVillage(Village village) {

		// As City is not mapped directly with villages,these changes made
		// village.setCity(village.getCity());
		village.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.save(village);
	}

	public void editVillage(Village existing) {

		// As City is not mapped directly with villages,these changes made
		// existing.setCity(existing.getCity());
		existing.setRevisionNo(DateUtil.getRevisionNumber());
		// editWarehouseRevNo(existing);
		utilDAO.update(existing);

	}

	@Override
	public List<GramPanchayat> listGramPanchayatsByCityCode(String selectedCity) {
		return utilDAO.listGramPanchayatsByCityCode(selectedCity);
	}

	@Override
	public List<GramPanchayat> listGramPanchayatsByCode(String selectedCity) {
		return utilDAO.listGramPanchayatsByCode(selectedCity);
	}

	@Override
	public List<Municipality> listMunicipalitiesByCode(String locality) {
		return utilDAO.listMunicipalitiesByCode(locality);
	}

	@Override
	public List<State> listStatesByCode(String country) {

		return utilDAO.listStatesByCode(country);
	}

	@Override
	public List<Object[]> listCityCodeAndName() {
		return utilDAO.listCityCodeAndName();
	}

	@Override
	public List<State> listOfStates() {
		// TODO Auto-generated method stub
		return utilDAO.listOfStates();
	}

	@Override
	public void removeVillage(Village village) {

		if (!ObjectUtil.isEmpty(village)) {
			editWarehouseRevNo(village);
			utilDAO.delete(village);
		}

	}

	private void editWarehouseRevNo(Village village) {

		if (!ObjectUtil.isEmpty(village)) {
			/*
			 * List<Warehouse> warehouses =
			 * listWarehouseByVillageId(village.getId()); if
			 * (!ObjectUtil.isListEmpty(warehouses)) { for (Warehouse warehouse
			 * : warehouses) { editWarehouse(warehouse); } }
			 */
		}
	}

	public void editMunicipality(Municipality municipality) {

		municipality.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.update(municipality);
	}

	public void editGramPanchayat(GramPanchayat existing) {

		existing.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.update(existing);

	}

	public boolean isVillageMappedWithCooperative(long id) {

		return utilDAO.isVillageMappedWithCooperative(id);
	}

	public String isVillageMappingExist(long id) {

		boolean farmerMapped = utilDAO.isVillageMappingExist(id);
		if (farmerMapped) {
			return "farmer.mapped";
		} else {
			return utilDAO.isVillageMappindExistForDistributionAndProcurement(id);
		}
	}

	@Override
	public Village findVillageByNameAndCity(String village, String city) {
		return utilDAO.findVillageByNameAndCity(village, city);
	}

	public void addGramPanchayat(GramPanchayat gramPanchayat) {

		gramPanchayat.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.save(gramPanchayat);

	}

	public GramPanchayat findGramPanchayatById(long id) {

		return utilDAO.findGramPanchayatById(id);
	}

	@Override
	public void removeGramPanchayat(GramPanchayat gm) {
		utilDAO.delete(gm);

	}

	public Locality findLocalityByCode(String code) {

		return utilDAO.findLocalityByCode(code);
	}

	public Municipality findMunicipalityByName(String name) {

		return utilDAO.findMunicipalityByName(name);
	}

	public void addMunicipality(Municipality municipality) {

		municipality.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.save(municipality);
	}

	public Municipality findMunicipalityById(Long id) {

		return utilDAO.findMunicipalityById(id);
	}

	@Override
	public List<Village> listVillagesByCityID(Long cityId) {
		return utilDAO.listVillagesByCityID(cityId);
	}

	public void editLocality(Locality locality) {

		locality.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.update(locality);
	}

	@Override
	public void removeCity(Municipality mun) {
		utilDAO.delete(mun);

	}

	public void removeCityWarehouseProduct(String cityCode) {

		List<PackhouseIncoming> products = utilDAO.listWarehouseProductByCityCode(cityCode);
		if (products != null) {
		for (PackhouseIncoming product : products) {
			utilDAO.delete(product);
		}
		}
	}

	public void editServicePoint(ServicePoint servicePoint) {

		utilDAO.update(servicePoint);
	}

	@Override
	public State findStateByCode(String code) {
		return utilDAO.findStateByCode(code);
	}

	public void addLocality(Locality locality) {

		locality.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.save(locality);
	}

	public Locality findLocalityByName(String name) {

		return utilDAO.findLocalityByName(name);
	}

	public Locality findLocalityById(Long id) {

		return utilDAO.findLocalityById(id);
	}

	public Country findCountryByName(String name) {

		return utilDAO.findCountryByName(name);
	}

	public void removeLocality(String name) {

		utilDAO.delete(findLocalityByName(name));
	}

	public void addState(State state) {

		state.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.save(state);
	}

	public void addCountry(Country country) {

		country.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.save(country);
	}

	public Country findCountryById(Long id) {

		return utilDAO.findCountryById(id);
	}

	public void editCountry(Country country) {

		country.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.update(country);
	}

	public void removeCountry(String name) {

		utilDAO.delete(findCountryByName(name));
	}

	public State findStateById(Long id) {

		return utilDAO.findStateById(id);
	}

	@Override
	public List<Locality> listLocalitiesByStateID(Long stateId) {
		return utilDAO.listLocalitiesByStateID(stateId);
	}

	public void removeState(String name) {

		utilDAO.delete(findStateByName(name));
	}

	public State findStateByName(String name) {

		return utilDAO.findStateByName(name);
	}

	public void editState(State state) {

		state.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.update(state);
	}

	public Country findCountryByCode(String code) {
		return utilDAO.findCountryByCode(code);
	}

	public Village findVillageByName(String name) {

		return utilDAO.findVillageByName(name);
	}

	public Vendor findVendor(Long id) {

		// TODO Auto-generated method stub
		return this.utilDAO.findVendor(id);
	}

	public void addVendor(Vendor vendor) {

		// TODO Auto-generated method stub
		this.utilDAO.save(vendor);
	}

	public void createAccount(String profileId, String accountNo, Date txnTime, int type, String branchId) {

		ESEAccount account = new ESEAccount();
		account.setProfileId(profileId);
		account.setAccountNo(accountNo);
		account.setType(type);
		account.setAccountOpenDate(txnTime);
		if (ESEAccount.AGENT_ACCOUNT == type) {
			account.setAccountType(ESEAccount.OPERATOR_ACCOUNT);
		} else if (ESEAccount.FARMER_ACCOUNT == type) {
			account.setAccountType(ESEAccount.SAVING_BANK_ACCOUNT);
		} else if ((ESEAccount.CLIENT_ACCOUNT == type) || (ESEAccount.VENDOR_ACCOUNT == type)) {
			account.setAccountType(ESEAccount.SAVING_BANK_ACCOUNT);
		}
		account.setCashBalance(0.00);
		account.setCreditBalance(0.00);
		account.setBranchId(branchId);
		account.setBalance(0.00);
		account.setDistributionBalance(0.00);
		account.setSavingAmount(0.00);
		account.setShareAmount(0.00);
		account.setStatus(ESEAccount.ACTIVE);
		account.setCreateTime(txnTime);
		createAccount(account);
	}

	public void createAccount(ESEAccount account) {
		utilDAO.save(account);

	}

	public void editVendor(Vendor tempVendor) {

		// TODO Auto-generated method stub
		this.utilDAO.update(tempVendor);
	}

	public void removeVendor(Vendor vendor) {

		// TODO Auto-generated method stub
		this.utilDAO.delete(vendor);
	}

	@Override
	public List<String> findAgentNameByWareHouseId(long coOperativeId) {

		// TODO Auto-generated method stub
		return utilDAO.findAgentNameByWareHouseId(coOperativeId);
	}

	@Override
	public Village findVillageAndCityByVillName(String otherVi, Long cityId) {
		// TODO Auto-generated method stub
		return utilDAO.findVillageAndCityByVillName(otherVi, cityId);
	}

	@Override
	public List<Village> listVillagesByPanchayatId(Long selectedPanchayat) {

		return utilDAO.listVillagesByPanchayatId(selectedPanchayat);
	}

	public List<Object[]> listHarvestSeasonFromToPeriod() {

		return utilDAO.listHarvestSeasonFromToPeriod();
	}

	public void addHarvestSeason(HarvestSeason harvestSeason) {

		utilDAO.save(harvestSeason);
	}

	public void addCustomer(Customer customer) {

		utilDAO.save(customer);
	}

	public void editHarvestSeason(HarvestSeason harvestSeason) {

		utilDAO.update(harvestSeason);
	}

	public void editCustomer(Customer customer) {

		utilDAO.update(customer);
	}

	public HarvestSeason findHarvestSeasonById(Long id) {

		return utilDAO.findHarvestSeasonById(id);
	}

	public void removeHarvestSeason(HarvestSeason harvestSeason) {

		utilDAO.delete(harvestSeason);
	}

	@Override
	public List<Object[]> listAgentIdName() {
		return utilDAO.listAgentIdName();
	}

	@Override
	public List<Object[]> listOfProducts() {
		// TODO Auto-generated method stub
		return utilDAO.listOfProducts();
	}

	@Override
	public DocumentUpload findDocumentUploadById(Long valueOf) {
		return utilDAO.findDocumentUploadById(valueOf);
	}

	@Override
	public boolean isEntitlavailoableforuser(String userName, String auth) {
		return utilDAO.isEntitlavailoableforuser(userName, auth);
	}

	@Override
	public List<Product> findProductBySubCategoryCode(Long trim) {
		// TODO Auto-generated method stub
		return utilDAO.findProductBySubCategoryCode(trim);
	}

	@Override
	public List<FarmCatalogue> listCatelogueType(String text) {
		// TODO Auto-generated method stub
		return utilDAO.listCatelogueType(text);
	}

	@Override
	public DocumentUpload findDocumentUploadByReference(String id, Integer type) {
		// TODO Auto-generated method stub
		return utilDAO.findDocumentUploadByReference(id, type);
	}

	@Override
	public List<Object[]> listAgroChemicalDealers() {
		// TODO Auto-generated method stub
		return utilDAO.listAgroChemicalDealers();
	}

	@Override
	public User findUserByDealerAndRole(Long id, Long id2) {
		// TODO Auto-generated method stub
		return utilDAO.findUserByDealerAndRole(id, id2);
	}

	@Override
	public List<User> listUsersByType(int type) {
		// TODO Auto-generated method stub
		return utilDAO.listUsersByType(type);
	}

	@Override
	public List<FarmCatalogue> listFarmCatalogueWithOther(int type, int otherValue) {
		return utilDAO.listFarmCatalogueWithOther(type, otherValue);
	}

	@Override
	public List<FarmCatalogue> listSeedTreatmentDetailsBasedOnType() {

		return utilDAO.listSeedTreatmentDetailsBasedOnType();
	}

	public List<HarvestSeason> listHarvestSeason() {

		return utilDAO.listHarvestSeason();
	}

	@Override
	public List<Object[]> listAgroChemical() {
		// TODO Auto-generated method stub

		return utilDAO.listAgroChemical();
	}

	@Override
	public FarmCatalogue findCatalogueByNameAndType(String catalogueName, Integer valueOf) {
		// TODO Auto-generated method stub
		return utilDAO.findCatalogueByNameAndType(catalogueName, valueOf);
	}

	@Override
	public List<Object[]> listProcurementProductForSeedHarvest() {
		// TODO Auto-generated method stub
		return utilDAO.listProcurementProductForSeedHarvest();
	}

	@Override
	public List<Object[]> findPermittedAgroChemicalRegistrationByDealerId(Long dealerId) {
		// TODO Auto-generated method stub
		return utilDAO.findPermittedAgroChemicalRegistrationByDealerId(dealerId);
	}

	@Override
	public List<Municipality> findMuniciByDistrictId(long selectedLocality) {
		// TODO Auto-generated method stub
		return utilDAO.findMuniciByDistrictId(selectedLocality);
	}

	@Override
	public List<GramPanchayat> findGramByMuniciId(long selectedMunicipality) {
		// TODO Auto-generated method stub
		return utilDAO.findGramByMuniciId(selectedMunicipality);
	}

	@Override
	public List<Village> findVillageByGramId(long selectedGram) {
		System.out.println("At 6321  utilservice findVillageByGramId==>" + selectedGram);// selectedWard1

		return utilDAO.findVillageByGramId(selectedGram);
	}

	@Override
	public User findInspectorById(Long valueOf) {
		// TODO Auto-generated method stub
		return utilDAO.findInspectorById(valueOf);
	}

	@Override
	public List<User> listUsersByRoleName(String roleName) {
		return utilDAO.listUsersByRoleName(roleName);
	}

	@Override
	public List<Object> findAgroChemicalDealerByCompanyNameOrEmail(String email, String companyName, String nid) {
		return utilDAO.findAgroChemicalDealerByCompanyNameOrEmail(email, companyName, nid);
	}

	@Override
	public List<User> listUsersExceptAdmin() {
		return utilDAO.listUsersExceptAdmin();
	}

	@Override
	public List<Object[]> listDealerDetailsbyRegNo(Long id) {
		// TODO Auto-generated method stub
		return utilDAO.listDealerDetailsbyRegNo(id);

	}

	@Override
	public List<DocumentUpload> listDocumentsByCode(String code) {
		return utilDAO.listDocumentsByCode(code);
	}

	@Override
	public DocumentUpload findDocumentById(Long id) {

		return utilDAO.findDocumentById(id);
	}

	public DocumentUpload findDocumentUploadByCode(String code) {

		return utilDAO.findDocumentUploadByCode(code);
	}

	@Override
	public List<Object[]> listProcurementProductByVariety(Long procurementProdId) {
		// TODO Auto-generated method stub
		return utilDAO.listProcurementProductByVariety(procurementProdId);
	}

	@Override
	public List<Object[]> listChemical() {
		// TODO Auto-generated method stub
		return utilDAO.listChemical();
	}

	@Override
	public String findDealerByRegNo(String regNo) {
		// TODO Auto-generated method stub
		return utilDAO.findDealerByRegNo(regNo);
	}

	@Override
	public Role findRoleByType(int type) {
		// TODO Auto-generated method stub
		return utilDAO.findRoleByType(type);
	}

	@Override
	public List<Farmer> getFarmerList() {

		return utilDAO.getFarmerList();
	}

	@Override
	public Farmer findFarmerById(Long id) {
		// TODO Auto-generated method stub
		return utilDAO.findFarmerById(id);
	}

	/*
	 * @Override public List<Farmer> getFarmerList() { // TODO Auto-generated
	 * method stub return utilDAO.getFarmerList(); }
	 */

	/*
	 * @Override public Farmer findFarmerById(Long id) { // TODO Auto-generated
	 * method stub return utilDAO.findFarmerById(id); }
	 */

	@Override
	public List<Object[]> listTestIdAndNumber() {
		// TODO Auto-generated method stub
		return utilDAO.listTestIdAndNumber();
	}

	@Override
	public User findUserById(Long object) {
		// TODO Auto-generated method stub
		return utilDAO.findUserById(object);
	}

	@Override
	public Farmer findFarmerByEmail(String email) {
		// TODO Auto-generated method stub
		return utilDAO.findFarmerByEmail(email);
	}

	@Override
	public List<Object[]> listExporter() {
		// TODO Auto-generated method stub
		return utilDAO.listExporter();
	}

	@Override
	public List<Object[]> listBuyer() {
		// TODO Auto-generated method stub
		return utilDAO.listBuyer();
	}

	@Override
	public List<Object[]> listUOM() {
		// TODO Auto-generated method stub
		return utilDAO.listUOM();
	}

	@Override
	public AgentType findAgentTypeByName(String name) {
		// TODO Auto-generated method stub
		return utilDAO.findAgentTypeByName(name);
	}

	public List<Farmer> listFarmerName() {

		return utilDAO.listFarmerName();

	}

	@Override
	public List<FarmCatalogue> listCatalogueByCode(String code) {
		// TODO Auto-generated method stub
		return utilDAO.listCatalogueByCode(code);
	}

	@Override
	public Farmer findFarmerByName(String farmerName) {
		// TODO Auto-generated method stub
		return utilDAO.findFarmerByName(farmerName);
	}

	@Override
	public Farmer findFarmerByNIN(String nin) {
		// TODO Auto-generated method stub
		return utilDAO.findFarmerByNIN(nin);
	}

	@Override
	public List<ProcurementProduct> listProcurementProductByRevisionNo(Long revisionNo) {
		// TODO Auto-generated method stub
		return utilDAO.listProcurementProductByRevisionNo(revisionNo);
	}

	@Override
	public List<ProcurementProduct> listProcurementProduct() {
		// TODO Auto-generated method stub
		return utilDAO.listProcurementProduct();
	}

	@Override
	public List<ProcurementVariety> listProcurementProductVarietyByRevisionNo(Long revisionNo) {
		// TODO Auto-generated method stub
		return utilDAO.listProcurementProductVarietyByRevisionNo(revisionNo);
	}

	@Override
	public List<ProcurementGrade> listProcurementProductGradeByRevisionNo(Long revisionNo) {
		// TODO Auto-generated method stub
		return utilDAO.listProcurementProductGradeByRevisionNo(revisionNo);
	}

	@Override
	public Packhouse findWarehouseById(Long id) {
		// TODO Auto-generated method stub
		return utilDAO.findWarehouseById(id);
	}

	@Override
	public Packhouse findWarehouseByName(String name) {
		// TODO Auto-generated method stub
		return utilDAO.findWarehouseByName(name);
	}

	@Override
	public List<Packhouse> listAllWarehouses() {
		// TODO Auto-generated method stub
		return utilDAO.listAllWarehouses();
	}

	@Override
	public ExporterRegistration findExproterByEmail(String emailId) {
		// TODO Auto-generated method stub
		return utilDAO.findExproterByEmail(emailId);
	}

	@Override
	public ExporterRegistration findExproterByCompanyName(String name) {
		// TODO Auto-generated method stub
		return utilDAO.findExproterByCompanyName(name);
	}

	@Override
	public ExporterRegistration findExportRegById(Long id) {
		return utilDAO.findExportRegById(id);
	}

	@Override
	public Pcbp findPcbpById(Long id) {
		return utilDAO.findPcbpById(id);
	}

	@Override
	public Pcbp findPcbpByvarietyAndChamical(Long va, Long ch) {
		return utilDAO.findPcbpByvarietyAndChamical(va, ch);
	}

	@Override
	public State listStatesById(Long id) {
		// public List<Object[]> listStatesById(Long id){
		System.out.println("At 4626 inside utilservice Method*************" + id);

		return utilDAO.listStatesById(id);
	}

	@Override
	public List<ProcurementVariety> listProcurementVarietyByProcurementProductCode(String ppCode) {

		return utilDAO.listProcurementVarietyByProcurementProductCode(ppCode);
	}

	@Override
	public List<ProcurementVariety> listProcurementVarietyByProcurementProductId(Long id) {
		return utilDAO.listProcurementVarietyByProcurementProductId(id);
	}

	@Override
	public ProcurementVariety findProcurementVarietyById(Long id) {
		return utilDAO.findProcurementVariertyById(id);
	}

	@Override
	public List<Pcbp> findPcbpByProcurementGradeId(Long id) {
		return utilDAO.findPcbpByProcurementGradeId(id);
	}

	@Override
	public ProcurementProduct findProcurementProductById(Long id) {
		return utilDAO.findProcurementProductById(id);
	}

	public Locality findVillageByStateId(long selectedState) {

		return utilDAO.findVillageByStateId(selectedState);
	}

	@Override
	public Municipality findCityByDistrictId(long selectedLocality) {
		// TODO Auto-generated method stub
		return utilDAO.findCityByDistrictId(selectedLocality);
	}

	/*
	 * @Override public Village findVillageByVId(long selectedWard1) { // TODO
	 * Auto-generated method stub
	 * System.out.println("At 340 exp regis utilservice*  getWard1************"
	 * +selectedWard1);
	 * 
	 * return utilDAO.findVillageByVId(selectedWard1); }
	 */
	@Override
	public Farmer findFarmerByNid(Long nid) {
		// TODO Auto-generated method stub
		return utilDAO.findFarmerByNid(nid);
	}

	@Override
	public Farmer findFarmerByPhone(Long nid) {
		// TODO Auto-generated method stub
		return utilDAO.findFarmerByPhone(nid);
	}

	@Override
	public Farm findFarmById(Long id) {
		// TODO Auto-generated method stub
		return utilDAO.findFarmId(id);
	}

	@Override
	public ProcurementProduct findProcurementProductByName(String cropName) {
		// TODO Auto-generated method stub
		return utilDAO.findProcurementProductByName(cropName);
	}

	@Override
	public void addProcurementProduct(ProcurementProduct procurementProduct) {
		procurementProduct.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.save(procurementProduct);
	}

	@Override
	public void editProcurementProduct(ProcurementProduct procurementProduct) {
		procurementProduct.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.update(procurementProduct);
	}

	@Override
	public ProcurementVariety findProcurementVariertyByNameAndCropId(String varietyName, Long procurementProductId) {
		return utilDAO.findProcurementVariertyByNameAndCropId(varietyName, procurementProductId);
	}

	@Override
	public void addProcurementVariety(ProcurementVariety procurementVariety) {
		utilDAO.addProcurementVariety(procurementVariety);
	}

	@Override
	public void editProcurementVariety(ProcurementVariety procurementVariety) {
		procurementVariety.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.update(procurementVariety);
	}

	@Override
	public List<ProcurementGrade> listProcurementGradeByProcurementVarietyId(Long id) {
		return utilDAO.listProcurementGradeByProcurementVarietyId(id);
	}

	@Override
	public List<ProcurementGrade> listProcurementGradeByProcurementVarietyIdGradeid(Long id, String IdGrades) {
		return utilDAO.listProcurementGradeByProcurementVarietyIdGradeid(id, IdGrades);
	}

	@Override
	public ProcurementGrade findProcurementGradeByNameAndVarietyId(String name, Long id) {
		// TODO Auto-generated method stub
		return utilDAO.findProcurementGradeByNameAndVarietyId(name, id);
	}

	@Override
	public void addProcurementGrade(ProcurementGrade procurementGrade) {
		procurementGrade.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.save(procurementGrade);
	}

	@Override
	public ProcurementGrade findProcurementGradeById(Long id) {
		return utilDAO.findProcurementGradeById(id);
	}

	@Override
	public void editProcurementGrade(ProcurementGrade procurementGrade) {

		procurementGrade.setRevisionNo(DateUtil.getRevisionNumber());
		utilDAO.update(procurementGrade);

	}

	@Override
	public List<ProcurementVariety> listProcurementVariety() {
		// TODO Auto-generated method stub
		return utilDAO.listProcurementVariety();
	}

	@Override
	public List<ProcurementVariety> listProcurementVarietyBasedOnCropCat(String ugandaExport) {
		// TODO Auto-generated method stub
		return utilDAO.listProcurementVarietyBasedOnCropCat(ugandaExport);
	}

	@Override
	public ProcurementProduct findProcurementProductByCode(String code) {
		// TODO Auto-generated method stub
		return utilDAO.findProcurementProductByCode(code);
	}

	@Override
	public ProcurementVariety findProcurementVariertyByCode(String code) {
		// TODO Auto-generated method stub
		return utilDAO.findProcurementVariertyByCode(code);
	}

	@Override
	public ExporterRegistration findExportByLicense(String refLetterNo) {
		// TODO Auto-generated method stub
		return utilDAO.findExportByLicense(refLetterNo);
	}

	@Override
	public ExporterRegistration findExportByKrapin(String regno) {
		// TODO Auto-generated method stub
		return utilDAO.findExportByKrapin(regno);
	}

	@Override
	public ExporterRegistration findExportByNid(String rex) {
		return utilDAO.findExportByNid(rex);
	}

	@Override
	public ExporterRegistration findExportByCompanyEmail(String tin) {
		return utilDAO.findExportByCompanyEmail(tin);

	}

	@Override
	public Object[] findIfFarmerExist(String phno, String nid) {
		return utilDAO.findIfFarmerExist(phno, nid);
	}
	
	@Override
	public Object[] findIfFarmerExistForFarmer(String nid) {
		return utilDAO.findIfFarmerExistForFarmer(nid);
	}

	public FarmCrops findFarmCropsById(long id) {

		return utilDAO.findFarmCropsById(id);
	}

	@Override
	public List<Farm> listFarmByFarmerId(Long id) {
		// TODO Auto-generated method stub
		return utilDAO.listFarmByFarmerId(id);
	}

	@Override
	public List<FarmCrops> listFarmCropByFarmId(Long farmid) {
		return utilDAO.listFarmCropByFarmId(farmid);
	}
	
	@Override
	public List<Planting> listOfPlantingByBlockId(Long blockId) {
		return utilDAO.listOfPlantingByBlockId(blockId);
	}

	@Override
	public Scouting findScoutingById(Long id) {
		// TODO Auto-generated method stub
		return utilDAO.findScoutingById(id);
	}

	@Override
	public SprayAndFieldManagement findSprayAndFieldManagementById(Long id) {
		// TODO Auto-generated method stub
		return utilDAO.findSprayAndFieldManagementById(id);
	}

	@Override
	public ProcurementGrade findProcurementGradeByCode(String code) {
		return utilDAO.findProcurementGradeByCode(code);
	}

	@Override
	public ProcurementGrade findProcurementGradeByName(String name) {
		return utilDAO.findProcurementGradeByName(name);
	}

	@Override
	public ProcurementVariety findProcurementVarietyByName(String name) {
		return utilDAO.findProcurementVarietyByName(name);
	}

	@Override
	public FarmCrops findFarmCropsById(Long id) {
		// TODO Auto-generated method stub
		return utilDAO.findFarmCropsById(id);
	}

	@Override
	public List<ProcurementGrade> listProcurementGradeByProcurementVarietyIds(String procurementVariety) {
		return utilDAO.listProcurementGradeByProcurementVarietyIds(procurementVariety);
	}

	@Override
	public FarmCrops findFarmCropByplantingfarmcode(String plantingId, String farmCode) {
		return utilDAO.findFarmCropByplantingfarmcode(plantingId, farmCode);
	}

	@Override
	public String findCropHierarchyNameById(String table, String id) {
		// TODO Auto-generated method stub
		return utilDAO.findCropHierarchyNameById(table, id);
	}

	@Override
	public String findCropHierarchyHSCodeById(String table, String id) {
		// TODO Auto-generated method stub
		return utilDAO.findCropHierarchyHSCodeById(table, id);
	}

	@Override
	public String findCropHsCodeByProcurementProductId(String table, String id) {
		// TODO Auto-generated method stub
		return utilDAO.findCropHsCodeByProcurementProductId(table, id);
	}

	@Override
	public void addSorting(Sorting sorting) {
		CityWarehouse cityWarehouse = (CityWarehouse) findCityWarehouseByFarmCrops(sorting.getPlanting().getId());

		if (sorting.getQtyNet() > sorting.getQtyHarvested() || sorting.getQtyRejected() > sorting.getQtyHarvested()) {
			System.out.print("****** HARVEST QTY MUST BE LESS THAN THE SORTED & REJECTED QTY**** ");
		} else {
			if (cityWarehouse != null) {
				sorting.setQtyHarvested(cityWarehouse.getHarvestedWeight());
				cityWarehouse.setLossWeight(cityWarehouse.getLossWeight() == null ? 0 : cityWarehouse.getLossWeight());
				cityWarehouse
						.setSortedWeight(cityWarehouse.getSortedWeight() == null ? 0 : cityWarehouse.getSortedWeight());
				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
						sorting.getCreatedDate(), 1, sorting.getId(), 0l, cityWarehouse.getLossWeight(),
						cityWarehouse.getHarvestedWeight(), cityWarehouse.getSortedWeight(), 0l,
						sorting.getQtyHarvested(), sorting.getQtyRejected(), sorting.getQtyNet(), 0l,
						cityWarehouse.getHarvestedWeight() - sorting.getQtyRejected() - sorting.getQtyNet(),
						cityWarehouse.getLossWeight() + sorting.getQtyRejected(),
						cityWarehouse.getSortedWeight() + sorting.getQtyNet(), "SORTING", null);
				cityWarehouseDetail.setCityWarehouse(cityWarehouse);
				cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);

				cityWarehouse.setHarvestedWeight(
						cityWarehouse.getHarvestedWeight() - sorting.getQtyRejected() - sorting.getQtyNet());
				cityWarehouse.setLossWeight(cityWarehouse.getLossWeight() + sorting.getQtyRejected());
				cityWarehouse.setSortedWeight(cityWarehouse.getSortedWeight() + sorting.getQtyNet());
				cityWarehouse.setBranchId(sorting.getBranchId());
				cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
				//cityWarehouse.setQrCodeId(sorting.getQrCodeId());
				saveOrUpdate(cityWarehouse);
			}
			
			if (sorting != null)
				save(sorting);
			
			CityWarehouse incominCW = new CityWarehouse();

			incominCW = new CityWarehouse();
			incominCW.setPlanting(sorting.getPlanting());
			incominCW.setFarmcrops(sorting.getFarmCrops());
			incominCW.setSortedWeight(sorting.getQtyNet());
			incominCW.setLossWeight(sorting.getQtyRejected());
			incominCW.setHarvestedWeight(sorting.getQtyHarvested());
			incominCW.setCreatedDate(sorting.getCreatedDate());
			incominCW.setCityWarehouseDetails(new HashSet<>());
			incominCW.setCreatedUser(sorting.getCreatedUser());
			incominCW.setStockType(CityWarehouse.Stock_type.HARVEST_STOCK.ordinal());
			incominCW.setQrCodeId(sorting.getQrCodeId());
			incominCW.setBranchId(sorting.getBranchId());
			incominCW.setIsDelete(0);

			CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, incominCW,
					sorting.getCreatedDate(), 1, sorting.getId(), 0l, incominCW.getLossWeight(),
					incominCW.getHarvestedWeight(), incominCW.getSortedWeight(), 0l,
					sorting.getQtyHarvested(), sorting.getQtyRejected(), sorting.getQtyNet(), 0l,
					incominCW.getHarvestedWeight() - sorting.getQtyRejected() - sorting.getQtyNet(),
					incominCW.getLossWeight() + sorting.getQtyRejected(),
					incominCW.getSortedWeight() + sorting.getQtyNet(), "SORTING", null);
			incominCW.getCityWarehouseDetails().add(cityWarehouseDetail);
			incominCW.setRevisionNo(DateUtil.getRevisionNumber());
			
			saveOrUpdate(incominCW);
			
			
		}

	}

	@Override
	public CityWarehouse findCityWarehouseByFarmCrops(Long id) {
		// TODO Auto-generated method stub
		return utilDAO.findCityWarehouseByFarmCrops(id);
	}

	@Override
	public void updateSorting(Sorting sorting, Sorting oldSort) {

		CityWarehouse cityWarehouse = (CityWarehouse) findCityWarehouseByFarmCrops(sorting.getPlanting().getId());
		
		/*
		 * oldSort.setQtyNet(sorting.getQtyNet() - oldSort.getQtyNet());
		 * oldSort.setQtyRejected(sorting.getQtyRejected() -
		 * oldSort.getQtyRejected());
		 * oldSort.setQtyHarvested(sorting.getQtyHarvested() -
		 * oldSort.getQtyNet() - oldSort.getQtyRejected());
		 */
		sorting.setQtyHarvested(cityWarehouse.getHarvestedWeight());
		if (cityWarehouse != null) {
			CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
					sorting.getCreatedDate(), 1, sorting.getId(), 0l, cityWarehouse.getLossWeight(),
					cityWarehouse.getHarvestedWeight(), cityWarehouse.getSortedWeight(), 0l, sorting.getQtyHarvested(),
					oldSort.getQtyRejected(), sorting.getQtyNet(), 0l,
					cityWarehouse.getHarvestedWeight() - sorting.getQtyRejected() - sorting.getQtyNet(),
					cityWarehouse.getLossWeight() + sorting.getQtyRejected(),
					cityWarehouse.getSortedWeight() + sorting.getQtyNet(), "SORTING EDIT", null);
			cityWarehouseDetail.setCityWarehouse(cityWarehouse);
			cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);

			cityWarehouse.setHarvestedWeight(cityWarehouseDetail.getTotalGrossWeight());
			cityWarehouse.setLossWeight(cityWarehouseDetail.getTotalLoss());
			cityWarehouse.setSortedWeight(cityWarehouseDetail.getTotalSorted());
			cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
			update(cityWarehouse);
		}
		update(sorting);
		
		CityWarehouse incominCW = new CityWarehouse();
		incominCW = (CityWarehouse) farmerService.findObjectById(
				"from CityWarehouse ct where ct.qrCodeId=? and ct.stockType=? and ct.planting.id=? AND ct.isDelete=0",
				new Object[] { sorting.getQrCodeId(),
						CityWarehouse.Stock_type.HARVEST_STOCK.ordinal(), sorting.getPlanting().getId() });
		if (incominCW != null) {
			
			CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, incominCW,
					sorting.getCreatedDate(), 1, sorting.getId(), 0l, incominCW.getLossWeight(),
					incominCW.getHarvestedWeight(), incominCW.getSortedWeight(), 0l, sorting.getQtyHarvested(),
					oldSort.getQtyRejected(), sorting.getQtyNet(), 0l,
					incominCW.getHarvestedWeight() - sorting.getQtyRejected() - sorting.getQtyNet(),
					incominCW.getLossWeight() + sorting.getQtyRejected(),
					incominCW.getSortedWeight() + sorting.getQtyNet(), "SORTING EDIT", null);
			incominCW.getCityWarehouseDetails().add(cityWarehouseDetail);
			incominCW.setSortedWeight(sorting.getQtyNet());
			incominCW.setLossWeight(sorting.getQtyRejected());
			incominCW.setHarvestedWeight(sorting.getQtyHarvested());
			incominCW.setRevisionNo(DateUtil.getRevisionNumber());
			incominCW.setCreatedDate(sorting.getCreatedDate());

		} else {
			
			incominCW = new CityWarehouse();
			incominCW.setPlanting(sorting.getPlanting());
			incominCW.setFarmcrops(sorting.getFarmCrops());
			incominCW.setSortedWeight(sorting.getQtyNet());
			incominCW.setLossWeight(sorting.getQtyRejected());
			incominCW.setHarvestedWeight(cityWarehouse.getHarvestedWeight() - sorting.getQtyRejected() - sorting.getQtyNet());
			incominCW.setCreatedDate(sorting.getCreatedDate());
			incominCW.setCityWarehouseDetails(new HashSet<>());
			incominCW.setCreatedUser(sorting.getCreatedUser());
			incominCW.setStockType(CityWarehouse.Stock_type.HARVEST_STOCK.ordinal());
			incominCW.setQrCodeId(sorting.getQrCodeId());
			incominCW.setBranchId(sorting.getBranchId());
			incominCW.setIsDelete(0);

			CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, incominCW,
					sorting.getCreatedDate(), 1, sorting.getId(), 0l, incominCW.getLossWeight(),
					incominCW.getHarvestedWeight(), incominCW.getSortedWeight(), 0l,
					sorting.getQtyHarvested(), sorting.getQtyRejected(), sorting.getQtyNet(), 0l,
					incominCW.getHarvestedWeight() - sorting.getQtyRejected() - sorting.getQtyNet(),
					incominCW.getLossWeight() + sorting.getQtyRejected(),
					incominCW.getSortedWeight() + sorting.getQtyNet(), "SORTING", null);
			incominCW.getCityWarehouseDetails().add(cityWarehouseDetail);
			incominCW.setRevisionNo(DateUtil.getRevisionNumber());

		}
		saveOrUpdate(incominCW);

	}

	@Override
	public void saveHarvest(Harvest harvest) {

		CityWarehouse cityWarehouse = (CityWarehouse) findCityWarehouseByFarmCrops(harvest.getPlanting().getId());
		if (cityWarehouse != null) {
			CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse, harvest.getDate(), 2,
					harvest.getId(), 0l, cityWarehouse.getLossWeight(), cityWarehouse.getHarvestedWeight(),
					cityWarehouse.getSortedWeight(), 0l, harvest.getQtyHarvested(), 0d, 0d, 0l,
					cityWarehouse.getHarvestedWeight() + harvest.getQtyHarvested(), cityWarehouse.getLossWeight(),
					cityWarehouse.getSortedWeight(), "HARVEST", null);
			cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
			cityWarehouse.setHarvestedWeight(cityWarehouseDetail.getTotalGrossWeight());
			if (cityWarehouse.getLastHarvestDate() == null
					|| cityWarehouse.getLastHarvestDate().compareTo(harvest.getDate()) < 0) {
				cityWarehouse.setLastHarvestDate(harvest.getDate());
			}
			cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
			update(cityWarehouse);
		} else {
			cityWarehouse = new CityWarehouse();
			cityWarehouse.setLossWeight(0d);
			cityWarehouse.setSortedWeight(0d);

			cityWarehouse.setFarmcrops(harvest.getPlanting().getFarmCrops());
			cityWarehouse.setPlanting(harvest.getPlanting());
			cityWarehouse.setHarvestedWeight(harvest.getQtyHarvested());
			cityWarehouse.setCreatedDate(harvest.getDate());
			cityWarehouse.setCityWarehouseDetails(new HashSet<>());
			cityWarehouse.setCreatedUser(harvest.getCreatedUser());
			cityWarehouse.setCoOperative(null);
			cityWarehouse.setBranchId(harvest.getBranchId());
			cityWarehouse.setIsDelete(0);
			CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse, harvest.getDate(), 2,
					harvest.getId(), 0l, 0d, 0d, 0d, 0l, harvest.getQtyHarvested(), 0d, 0d, 0l,
					harvest.getQtyHarvested(), 0d, 0d, "HARVEST", null);
			cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
			if (cityWarehouse.getLastHarvestDate() == null
					|| cityWarehouse.getLastHarvestDate().compareTo(harvest.getDate()) < 0) {
				cityWarehouse.setLastHarvestDate(harvest.getDate());
			}
			cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
			cityWarehouse.setStockType(0);
			cityWarehouse.setBranchId(harvest.getBranchId());
			save(cityWarehouse);
		}
		save(harvest);
	}

	@Override
	public void updateHarvest(Harvest harvest) {

		update(harvest);

	}

	@Override
	public void updateStocks(Harvest harvest, Map<CityWarehouse, Double> existock) {
		Date dt = (Date) farmerService.findObjectById("select max(h.date) from Harvest h where h.planting.id=?",
				new Object[] { harvest.getPlanting().getId() });
		existock.entrySet().forEach(uu -> {
			CityWarehouse cityWarehouse = uu.getKey();
			if (cityWarehouse == null) {
				cityWarehouse = new CityWarehouse();
				cityWarehouse.setLossWeight(0d);
				cityWarehouse.setSortedWeight(0d);

				cityWarehouse.setFarmcrops(harvest.getPlanting().getFarmCrops());
				cityWarehouse.setPlanting(harvest.getPlanting());
				cityWarehouse.setHarvestedWeight(uu.getValue());
				cityWarehouse.setCreatedDate(harvest.getDate());
				cityWarehouse.setCityWarehouseDetails(new HashSet<>());
				cityWarehouse.setCreatedUser(harvest.getCreatedUser());
				cityWarehouse.setCoOperative(null);
				cityWarehouse.setBranchId(harvest.getBranchId());
				cityWarehouse.setIsDelete(0);
				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
						harvest.getDate(), 2, harvest.getId(), 0l, 0d, 0d, 0d, 0l, harvest.getQtyHarvested(), 0d, 0d,
						0l, harvest.getQtyHarvested(), 0d, 0d, "HARVEST", null);
				cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
				if (dt != null) {
					cityWarehouse.setLastHarvestDate(dt);
				} else if (cityWarehouse.getLastHarvestDate() == null
						|| cityWarehouse.getLastHarvestDate().compareTo(harvest.getDate()) < 0) {
					cityWarehouse.setLastHarvestDate(harvest.getDate());
				}
				cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
				cityWarehouse.setStockType(0);
				cityWarehouse.setBranchId(harvest.getBranchId());
				save(cityWarehouse);
			} else {
				cityWarehouse.setHarvestedWeight(cityWarehouse.getHarvestedWeight() + uu.getValue());
				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
						harvest.getDate(), 2, harvest.getId(), 0l, 0d, 0d, 0d, 0l, harvest.getQtyHarvested(), 0d, 0d,
						0l, harvest.getQtyHarvested(), 0d, 0d, "HARVEST", null);
				cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
				if (dt != null) {
					cityWarehouse.setLastHarvestDate(dt);
				} else if (cityWarehouse.getLastHarvestDate() == null
						|| cityWarehouse.getLastHarvestDate().compareTo(harvest.getDate()) < 0) {
					cityWarehouse.setLastHarvestDate(harvest.getDate());
				}
				saveOrUpdate(cityWarehouse);
			}
		});
	}

	@Override
	public List<Packhouse> listPackhouse() {
		// TODO Auto-generated method stub

		return utilDAO.listPackhouse();
	}

	@Override
	public List<CityWarehouse> listCityWareHouse() {
		// TODO Auto-generated method stub

		return utilDAO.listCityWareHouse();
	}

	public List<FarmCrops> listFarmCrops() {
		return utilDAO.listFarmCrops();
	}

	@Override
	public void saveIncoming(PackhouseIncoming packhouseIncoming) {
		saveOrUpdate(packhouseIncoming);
		packhouseIncoming.getPackhouseIncomingDetails().stream().filter(uu -> uu.getPackhouseIncoming().getStatus() != null && !uu.getPackhouseIncoming().equals(4)).forEach(uu -> {
			CityWarehouse cityWarehouse = (CityWarehouse) farmerService.findObjectById(
						"from CityWarehouse ct where ct.qrCodeId=? and ct.stockType=? and ct.planting.id=?",
						new Object[] { uu.getQrCodeId(),
								CityWarehouse.Stock_type.HARVEST_STOCK.ordinal(), uu.getPlanting().getId() });
			if (cityWarehouse != null) {
				
				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
						packhouseIncoming.getCreatedDate(), 2, packhouseIncoming.getId(), 0l,
						cityWarehouse.getLossWeight(), cityWarehouse.getHarvestedWeight(),
						cityWarehouse.getSortedWeight(), 0l, 0d, 0d, uu.getReceivedWeight(), 0l,
						cityWarehouse.getHarvestedWeight(), cityWarehouse.getLossWeight(),
						cityWarehouse.getSortedWeight() - uu.getTransferWeight(), "INCOMING", null);
				cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
				cityWarehouse.setSortedWeight(cityWarehouse.getSortedWeight() - uu.getTransferWeight());
				//cityWarehouse.setLossWeight(cityWarehouse.getLossWeight());
				cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
				update(cityWarehouse);
			}

			CityWarehouse incominCW = new CityWarehouse();
			
			incominCW = new CityWarehouse();
			incominCW.setPlanting(uu.getPlanting());
			incominCW.setFarmcrops(uu.getPlanting().getFarmCrops());
			incominCW.setSortedWeight(uu.getReceivedWeight());
			incominCW.setLossWeight(uu.getTotalWeight());
			incominCW.setHarvestedWeight(uu.getTransferWeight());
			incominCW.setCreatedDate(packhouseIncoming.getOffLoadingDate());
			incominCW.setCityWarehouseDetails(new HashSet<>());
			incominCW.setCreatedUser(packhouseIncoming.getCreatedUser());
			incominCW.setCoOperative(packhouseIncoming.getPackhouse());
			incominCW.setBranchId(packhouseIncoming.getBranchId());
			incominCW.setBranchId(packhouseIncoming.getBranchId());
			incominCW.setStockType(CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal());
			incominCW.setCoOperative(packhouseIncoming.getPackhouse());
			incominCW.setBatchNo(packhouseIncoming.getBatchNo());
			incominCW.setQrCodeId(uu.getQrCodeId());
			incominCW.setBranchId(packhouseIncoming.getBranchId());
			incominCW.setIsDelete(0);

			CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, incominCW,
					packhouseIncoming.getCreatedDate(), 2, packhouseIncoming.getId(), 0l, incominCW.getLossWeight(),
					incominCW.getHarvestedWeight(), incominCW.getSortedWeight(), 0l, 0d, 0d, uu.getReceivedWeight(), 0l,
					incominCW.getHarvestedWeight(), incominCW.getLossWeight(),
					incominCW.getSortedWeight() + uu.getReceivedWeight(), "INCOMING", null);
			incominCW.getCityWarehouseDetails().add(cityWarehouseDetail);
			incominCW.setRevisionNo(DateUtil.getRevisionNumber());
			saveOrUpdate(incominCW);
		});

	}

	@Override
	public void updateIncoming(PackhouseIncoming packhouseIncoming) {
		
		packhouseIncoming.getPackhouseIncomingDetails().stream().filter(uu -> uu.getPackhouseIncoming().getStatus() != null && !uu.getPackhouseIncoming().equals(4)).forEach(uu -> {
			CityWarehouse cityWarehouse = (CityWarehouse) farmerService.findObjectById(
						"from CityWarehouse ct where ct.qrCodeId=? and ct.stockType=? and ct.planting.id=?",
						new Object[] { uu.getQrCodeId(),
								CityWarehouse.Stock_type.HARVEST_STOCK.ordinal(), uu.getPlanting().getId() });
			if (cityWarehouse != null) {
				
				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
						packhouseIncoming.getCreatedDate(), 2, packhouseIncoming.getId(), 0l,
						cityWarehouse.getLossWeight(), cityWarehouse.getHarvestedWeight(),
						cityWarehouse.getSortedWeight(), 0l, 0d, 0d, uu.getReceivedWeight(), 0l,
						cityWarehouse.getHarvestedWeight(), cityWarehouse.getLossWeight(),
						cityWarehouse.getSortedWeight() - uu.getTransferWeight(), "INCOMING", null);
				cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
				cityWarehouse.setSortedWeight(cityWarehouse.getSortedWeight() - uu.getTransferWeight());
				//cityWarehouse.setLossWeight(cityWarehouse.getLossWeight());
				cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
				update(cityWarehouse);
			}

			CityWarehouse incominCW = new CityWarehouse();
			incominCW = (CityWarehouse) farmerService.findObjectById(
					"from CityWarehouse ct where ct.coOperative.id=? and ct.batchNo=? and ct.stockType=? and ct.planting.id=? and ct.qrCodeId=?",
					new Object[] { uu.getPackhouseIncoming().getPackhouse().getId(), uu.getPackhouseIncoming().getBatchNo(),
							CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(), uu.getPlanting().getId(),uu.getQrCodeId() });
			
			if (incominCW != null) {
				incominCW.setSortedWeight(0d);
				incominCW.setLossWeight(0d);
				incominCW.setHarvestedWeight(0d);
				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
						packhouseIncoming.getCreatedDate(), 2, packhouseIncoming.getId(), 0l,
						cityWarehouse.getLossWeight(), cityWarehouse.getHarvestedWeight(),
						cityWarehouse.getSortedWeight(), 0l, 0d, 0d, uu.getReceivedWeight(), 0l,
						cityWarehouse.getHarvestedWeight(), cityWarehouse.getLossWeight(),
						cityWarehouse.getSortedWeight() - uu.getTransferWeight(), "INCOMING", null);
				cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
				incominCW.setSortedWeight(uu.getReceivedWeight());
				incominCW.setLossWeight(uu.getTotalWeight());
				incominCW.setHarvestedWeight(uu.getTransferWeight());
				incominCW.getCityWarehouseDetails().add(cityWarehouseDetail);
				incominCW.setRevisionNo(DateUtil.getRevisionNumber());
				incominCW.setCreatedDate(packhouseIncoming.getOffLoadingDate());
				if (incominCW.getSortedWeight() <= 0) {
					incominCW.setIsDelete(1);
				}else{
					incominCW.setIsDelete(0);
				}

			}else{
			
			incominCW = new CityWarehouse();
			incominCW.setPlanting(uu.getPlanting());
			incominCW.setFarmcrops(uu.getPlanting().getFarmCrops());
			incominCW.setSortedWeight(uu.getReceivedWeight());
			incominCW.setLossWeight(uu.getTotalWeight());
			incominCW.setHarvestedWeight(uu.getTransferWeight());
			incominCW.setCreatedDate(packhouseIncoming.getOffLoadingDate());
			incominCW.setCityWarehouseDetails(new HashSet<>());
			incominCW.setCreatedUser(packhouseIncoming.getCreatedUser());
			incominCW.setCoOperative(packhouseIncoming.getPackhouse());
			incominCW.setBranchId(packhouseIncoming.getBranchId());
			incominCW.setBranchId(packhouseIncoming.getBranchId());
			incominCW.setStockType(CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal());
			incominCW.setCoOperative(packhouseIncoming.getPackhouse());
			incominCW.setBatchNo(packhouseIncoming.getBatchNo());
			incominCW.setQrCodeId(uu.getQrCodeId());
			incominCW.setBranchId(packhouseIncoming.getBranchId());
			incominCW.setIsDelete(0);

			CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, incominCW,
					packhouseIncoming.getCreatedDate(), 2, packhouseIncoming.getId(), 0l, incominCW.getLossWeight(),
					incominCW.getHarvestedWeight(), incominCW.getSortedWeight(), 0l, 0d, 0d, uu.getReceivedWeight(), 0l,
					incominCW.getHarvestedWeight(), incominCW.getLossWeight(),
					incominCW.getSortedWeight() + uu.getReceivedWeight(), "INCOMING", null);
			incominCW.getCityWarehouseDetails().add(cityWarehouseDetail);
			incominCW.setRevisionNo(DateUtil.getRevisionNumber());
			}
			saveOrUpdate(incominCW);
		});

		/*existingstock.entrySet().stream().forEach(uu -> {
			CityWarehouse cityWarehouse = uu.getKey();
			if (cityWarehouse != null) {
				//cityWarehouse.setSortedWeight(cityWarehouse.getSortedWeight() + uu.getValue());
				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
						packhouseIncoming.getCreatedDate(), 2, packhouseIncoming.getId(), 0l,
						cityWarehouse.getLossWeight(), cityWarehouse.getHarvestedWeight(),
						cityWarehouse.getSortedWeight(), 0l, 0d, 0d, uu.getValue(), 0l,
						cityWarehouse.getHarvestedWeight(), cityWarehouse.getLossWeight(),
						cityWarehouse.getSortedWeight() - uu.getValue(), "INCOMING REDUCTION", null);
				cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
				cityWarehouse.setSortedWeight(cityWarehouse.getSortedWeight() - uu.getValue());
				//cityWarehouse.setLossWeight(0d);
				cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
				update(cityWarehouse);
			}

			CityWarehouse incominCW = new CityWarehouse();
			incominCW = (CityWarehouse) farmerService.findObjectById(
					"from CityWarehouse ct where ct.coOperative.id=? and ct.batchNo=? and ct.stockType=? and ct.planting.id=?",
					new Object[] { packhouseIncoming.getPackhouse().getId(), packhouseIncoming.getBatchNo(),
							CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(), cityWarehouse.getPlanting().getId() });
			if (incominCW != null) {
				incominCW.setSortedWeight(incominCW.getSortedWeight() + uu.getValue());
				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, incominCW,
						packhouseIncoming.getCreatedDate(), 2, packhouseIncoming.getId(), 0l, incominCW.getLossWeight(),
						incominCW.getHarvestedWeight(), incominCW.getSortedWeight(), 0l, 0d, 0d, uu.getValue(), 0l,
						incominCW.getHarvestedWeight(), incominCW.getLossWeight(),
						incominCW.getSortedWeight() + uu.getValue(), "INCOMING", null);
				//cityWarehouse.setLossWeight(0d);
				incominCW.setLossWeight(incominCW.getHarvestedWeight() - incominCW.getSortedWeight());
				incominCW.getCityWarehouseDetails().add(cityWarehouseDetail);
				incominCW.setRevisionNo(DateUtil.getRevisionNumber());
				incominCW.setCreatedDate(packhouseIncoming.getOffLoadingDate());
				if (incominCW.getSortedWeight() <= 0) {
					incominCW.setIsDelete(1);
				}

			} else {
				incominCW = new CityWarehouse();
				incominCW.setLossWeight(0d);
				incominCW.setSortedWeight(0d);
				incominCW.setPlanting(cityWarehouse.getPlanting());
				incominCW.setFarmcrops(cityWarehouse.getPlanting().getFarmCrops());
				incominCW.setLossWeight(cityWarehouse.getSortedWeight() - uu.getValue());
				incominCW.setHarvestedWeight(cityWarehouse.getSortedWeight());
				incominCW.setSortedWeight(uu.getValue());
				incominCW.setCreatedDate(packhouseIncoming.getOffLoadingDate());
				incominCW.setCityWarehouseDetails(new HashSet<>());
				incominCW.setCreatedUser(packhouseIncoming.getCreatedUser());
				incominCW.setCoOperative(packhouseIncoming.getPackhouse());
				incominCW.setBranchId(packhouseIncoming.getBranchId());
				incominCW.setBranchId(packhouseIncoming.getBranchId());
				incominCW.setStockType(CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal());
				incominCW.setCoOperative(packhouseIncoming.getPackhouse());
				incominCW.setBatchNo(packhouseIncoming.getBatchNo());
				incominCW.setBranchId(packhouseIncoming.getBranchId());
				incominCW.setIsDelete(0);

				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, incominCW,
						packhouseIncoming.getCreatedDate(), 2, packhouseIncoming.getId(), 0l, incominCW.getLossWeight(),
						incominCW.getHarvestedWeight(), incominCW.getSortedWeight(), 0l, 0d, 0d, uu.getValue(), 0l,
						incominCW.getHarvestedWeight(), incominCW.getLossWeight(),
						incominCW.getSortedWeight() + uu.getValue(), "INCOMING", null);
				incominCW.getCityWarehouseDetails().add(cityWarehouseDetail);
				incominCW.setRevisionNo(DateUtil.getRevisionNumber());

			}
			saveOrUpdate(incominCW);

		});*/
	}

	@Override
	public void savePacking(Packing packing) {
		saveOrUpdate(packing);
		Map<Long, CityWarehouse> ctmap = new HashMap<Long, CityWarehouse>();

		packing.getPackingDetails().stream().forEach(uu -> {
			CityWarehouse cityWarehouse = uu.getCtt();

			if (cityWarehouse != null) {
				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
						packing.getCreatedDate(), 2, packing.getId(), 0l, cityWarehouse.getLossWeight(),
						cityWarehouse.getHarvestedWeight(), cityWarehouse.getSortedWeight(), 0l, 0d, 0d,
						uu.getQuantity(), 0l, cityWarehouse.getHarvestedWeight(), cityWarehouse.getLossWeight(),
						cityWarehouse.getSortedWeight() - uu.getQuantity(), "PACKING", cityWarehouse.getBatchNo());
				cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
				//cityWarehouse.setSortedWeight(cityWarehouse.getSortedWeight() - uu.getQuantity());
				Double sortedwt = uu.getQuantity() + uu.getRejectWt();
				cityWarehouse.setSortedWeight(cityWarehouse.getSortedWeight() - sortedwt);
				
				cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
				update(cityWarehouse);

			}
			CityWarehouse incominCW = new CityWarehouse();
			/*if (ctmap.containsKey(uu.getCtt().getId())) {
				incominCW = ctmap.get(uu.getCtt().getId());
				//incominCW.setSortedWeight(incominCW.getSortedWeight() + uu.getQuantity());
				incominCW.setSortedWeight(incominCW.getSortedWeight() + uu.getQuantity());
				incominCW.setLossWeight(incominCW.getLossWeight() + uu.getRejectWt());

			} else {*/

				incominCW.setLossWeight(0d);
				incominCW.setSortedWeight(0d);
				incominCW.setSortedWeight(uu.getQuantity());
				incominCW.setLossWeight(uu.getRejectWt());
				incominCW.setHarvestedWeight(0d);
				incominCW.setQrCodeId(uu.getQrCodeId());
				incominCW.setPackBatch(uu.getBatchNo());
				incominCW.setCreatedDate(packing.getPackingDate());
				incominCW.setCityWarehouseDetails(new HashSet<>());
				incominCW.setFarmcrops(uu.getPlanting().getFarmCrops());
				incominCW.setPlanting(uu.getPlanting());
				incominCW.setCreatedUser(packing.getCreatedUser());
				incominCW.setCoOperative(packing.getPackHouse());
				incominCW.setBranchId(packing.getBranchId());
				incominCW.setStockType(CityWarehouse.Stock_type.PACKING_STOCK.ordinal());
				incominCW.setCoOperative(packing.getPackHouse());
				incominCW.setBatchNo(packing.getBatchNo());
				incominCW.setBranchId(packing.getBranchId());
				incominCW.setIsDelete(0);
			

			CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, incominCW, packing.getCreatedDate(),
					2, packing.getId(), 0l, incominCW.getLossWeight(), incominCW.getHarvestedWeight(),
					incominCW.getSortedWeight(), 0l, 0d, 0d, uu.getQuantity(), 0l, incominCW.getHarvestedWeight(),
					incominCW.getLossWeight(), incominCW.getSortedWeight() + uu.getQuantity(), "INCOMING", null);
			incominCW.getCityWarehouseDetails().add(cityWarehouseDetail);
			incominCW.setRevisionNo(DateUtil.getRevisionNumber());
			ctmap.put(uu.getCtt().getId(), incominCW);
			
			saveOrUpdate(incominCW);
		});

	}

	@Override
	public void updatePacking(Packing packing, Map<CityWarehouse, Double> existingstock,Set<PackingDetail> pdSet) {
		
		saveOrUpdate(packing);
		Map<Long, CityWarehouse> ctmap = new HashMap<Long, CityWarehouse>();

		existingstock.entrySet().stream().forEach(uu -> {
			CityWarehouse cityWarehouse = uu.getKey();
			if (cityWarehouse != null) {
				//cityWarehouse.setSortedWeight(cityWarehouse.getSortedWeight() + uu.getValue());
				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
						packing.getCreatedDate(), 2, packing.getId(), 0l, cityWarehouse.getLossWeight(),
						cityWarehouse.getHarvestedWeight(), cityWarehouse.getSortedWeight(), 0l, 0d, 0d, uu.getValue(),
						0l, cityWarehouse.getHarvestedWeight(), cityWarehouse.getLossWeight(),
						cityWarehouse.getSortedWeight() - (uu.getValue()+cityWarehouse.getLossWeight()), "INCOMING REDUCTION", null);
				cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
				cityWarehouse.setSortedWeight(cityWarehouse.getSortedWeight() - (uu.getValue()));
				//cityWarehouse.setLossWeight(cityWarehouse.getLossWeight());
				cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
				update(cityWarehouse);
			}

			CityWarehouse incominCW = (CityWarehouse) farmerService.findObjectById(
					"from CityWarehouse ct where ct.coOperative.id=? and ct.batchNo=? and ct.stockType=? and ct.planting.id=? AND ct.isDelete=0",
					new Object[] { packing.getPackHouse().getId(), packing.getBatchNo(),
							CityWarehouse.Stock_type.PACKING_STOCK.ordinal(),
							uu.getKey().getPlanting().getId() });
			if (incominCW != null) {
				incominCW.setSortedWeight(0d);
				incominCW.setLossWeight(0d);
				//ctmap.put(incominCW.getId(), incominCW);
				incominCW.setIsDelete(2);
				update(incominCW);
			}
		
		});
		Map<CityWarehouse, Double> stockwe =pdSet.stream().collect(Collectors.groupingBy(PackingDetail::getCtt,Collectors.summingDouble(PackingDetail::getQuantity)));
		
		Map<CityWarehouse, Double> rejQt =pdSet.stream().collect(Collectors.groupingBy(PackingDetail::getCtt,Collectors.summingDouble(PackingDetail::getRejectWt)));
		stockwe.entrySet().stream().forEach(str ->{
			Double rejwt =rejQt.containsKey(str.getKey()) ? rejQt.get(str.getKey()) :0d;
			CityWarehouse incominCW = new CityWarehouse();
			if (ctmap.containsKey(str.getKey().getId())) {
				incominCW = ctmap.get(str.getKey().getId());
			} else {
				incominCW = (CityWarehouse) farmerService.findObjectById(
						"from CityWarehouse ct where ct.coOperative.id=? and ct.batchNo=? and ct.stockType=? and ct.planting.id=? AND ct.isDelete=2",
						new Object[] { packing.getPackHouse().getId(), packing.getBatchNo(),
								CityWarehouse.Stock_type.PACKING_STOCK.ordinal(),
								str.getKey().getId() });
			}
				
				if (incominCW != null) {
					incominCW.setSortedWeight(incominCW.getSortedWeight()+str.getValue());
					incominCW.setLossWeight(incominCW.getLossWeight()+rejwt);

					CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, incominCW,
							packing.getCreatedDate(), 2, packing.getId(), 0l, incominCW.getLossWeight(),
							incominCW.getHarvestedWeight(), incominCW.getSortedWeight(), 0l, 0d, 0d, str.getValue(), 0l,
							incominCW.getHarvestedWeight(), incominCW.getLossWeight()+rejwt,
							incominCW.getSortedWeight() +str.getValue(), "INCOMING", null);
					incominCW.getCityWarehouseDetails().add(cityWarehouseDetail);
					incominCW.setRevisionNo(DateUtil.getRevisionNumber());
					if (incominCW.getSortedWeight() <= 0) {
						incominCW.setIsDelete(1);
					}else{
						incominCW.setIsDelete(0);
					}
					
				}else{
					incominCW = new CityWarehouse();
					incominCW.setLossWeight(0d);
					incominCW.setSortedWeight(0d);

					incominCW.setFarmcrops(str.getKey().getFarmcrops());
					incominCW.setPlanting(str.getKey().getPlanting());
					incominCW.setSortedWeight(incominCW.getSortedWeight() +str.getValue());
				//	incominCW.setLossWeight(0d);
					incominCW.setLossWeight(rejwt);
					incominCW.setHarvestedWeight(0d);
					incominCW.setCreatedDate(packing.getPackingDate());
					incominCW.setCityWarehouseDetails(new HashSet<>());
					incominCW.setCreatedUser(packing.getCreatedUser());
					incominCW.setCoOperative(packing.getPackHouse());
					incominCW.setBranchId(packing.getBranchId());
					incominCW.setBranchId(packing.getBranchId());
					incominCW.setStockType(CityWarehouse.Stock_type.PACKING_STOCK.ordinal());
					incominCW.setCoOperative(packing.getPackHouse());
					incominCW.setBatchNo(packing.getBatchNo());
					incominCW.setBranchId(packing.getBranchId());
					incominCW.setIsDelete(0);

					CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, incominCW,
							packing.getCreatedDate(), 2, packing.getId(), 0l, incominCW.getLossWeight(),
							incominCW.getHarvestedWeight(), incominCW.getSortedWeight(), 0l, 0d, 0d,str.getValue(), 0l,
							incominCW.getHarvestedWeight(), incominCW.getLossWeight()+rejwt,
							incominCW.getSortedWeight() + str.getValue(), "INCOMING", null);
					incominCW.getCityWarehouseDetails().add(cityWarehouseDetail);
					incominCW.setRevisionNo(DateUtil.getRevisionNumber());
				}
			
			
				saveOrUpdate(incominCW);
		});
		
		/*ctmap.entrySet().forEach(uu -> {
					
			
		});*/
	
	}
	
	@Override
	public void deletePacking(Packing packing, Map<CityWarehouse, Double> existingstock,Set<PackingDetail> pdSet) {
		
		saveOrUpdate(packing);
		Map<Long, CityWarehouse> ctmap = new HashMap<Long, CityWarehouse>();

		existingstock.entrySet().stream().forEach(uu -> {
			CityWarehouse cityWarehouse = uu.getKey();
			if (cityWarehouse != null) {
				//cityWarehouse.setSortedWeight(cityWarehouse.getSortedWeight() + uu.getValue());
				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
						packing.getCreatedDate(), 2, packing.getId(), 0l, cityWarehouse.getLossWeight(),
						cityWarehouse.getHarvestedWeight(), cityWarehouse.getSortedWeight(), 0l, 0d, 0d, uu.getValue(),
						0l, cityWarehouse.getHarvestedWeight(), cityWarehouse.getLossWeight(),
						cityWarehouse.getSortedWeight() - (uu.getValue()+cityWarehouse.getLossWeight()), "INCOMING REDUCTION", null);
				cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
				cityWarehouse.setSortedWeight(cityWarehouse.getSortedWeight() - (uu.getValue()));
				//cityWarehouse.setLossWeight(cityWarehouse.getLossWeight());
				cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
				update(cityWarehouse);
			}

			CityWarehouse incominCW = (CityWarehouse) farmerService.findObjectById(
					"from CityWarehouse ct where ct.coOperative.id=? and ct.batchNo=? and ct.stockType=? and ct.planting.id=? AND ct.isDelete=0",
					new Object[] { packing.getPackHouse().getId(), packing.getBatchNo(),
							CityWarehouse.Stock_type.PACKING_STOCK.ordinal(),
							uu.getKey().getPlanting().getId() });
			if (incominCW != null) {
				incominCW.setSortedWeight(0d);
				incominCW.setLossWeight(0d);
				ctmap.put(incominCW.getPlanting().getId(), incominCW);
			}
		
		});
		Map<Planting, Double> stockwe =pdSet.stream().collect(Collectors.groupingBy(PackingDetail::getPlanting,Collectors.summingDouble(PackingDetail::getQuantity)));
		
		Map<Planting, Double> rejQt =pdSet.stream().collect(Collectors.groupingBy(PackingDetail::getPlanting,Collectors.summingDouble(PackingDetail::getRejectWt)));
		stockwe.entrySet().stream().forEach(str ->{
			Double rejwt =rejQt.containsKey(str.getKey()) ? rejQt.get(str.getKey()) :0d;
			CityWarehouse incominCW = new CityWarehouse();
			if (ctmap.containsKey(str.getKey().getId())) {
				incominCW = ctmap.get(str.getKey().getId());
			} else {
				incominCW = (CityWarehouse) farmerService.findObjectById(
						"from CityWarehouse ct where ct.coOperative.id=? and ct.batchNo=? and ct.stockType=? and ct.planting.id=? AND ct.isDelete=0",
						new Object[] { packing.getPackHouse().getId(), packing.getBatchNo(),
								CityWarehouse.Stock_type.PACKING_STOCK.ordinal(),
								str.getKey().getId() });
			}
				
				if (incominCW != null) {
					incominCW.setSortedWeight(incominCW.getSortedWeight()+str.getValue());
					incominCW.setLossWeight(incominCW.getLossWeight()+rejwt);

					CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, incominCW,
							packing.getCreatedDate(), 2, packing.getId(), 0l, incominCW.getLossWeight(),
							incominCW.getHarvestedWeight(), incominCW.getSortedWeight(), 0l, 0d, 0d, str.getValue(), 0l,
							incominCW.getHarvestedWeight(), incominCW.getLossWeight()+rejwt,
							incominCW.getSortedWeight() +str.getValue(), "INCOMING", null);
					incominCW.getCityWarehouseDetails().add(cityWarehouseDetail);
					incominCW.setRevisionNo(DateUtil.getRevisionNumber());
					if (incominCW.getSortedWeight() <= 0) {
						incominCW.setIsDelete(1);
					}
					
				}else{
					incominCW = new CityWarehouse();
					incominCW.setLossWeight(0d);
					incominCW.setSortedWeight(0d);

					incominCW.setFarmcrops(str.getKey().getFarmCrops());
					incominCW.setPlanting(str.getKey());
					incominCW.setSortedWeight(incominCW.getSortedWeight() +str.getValue());
				//	incominCW.setLossWeight(0d);
					incominCW.setLossWeight(rejwt);
					incominCW.setHarvestedWeight(0d);
					incominCW.setCreatedDate(packing.getPackingDate());
					incominCW.setCityWarehouseDetails(new HashSet<>());
					incominCW.setCreatedUser(packing.getCreatedUser());
					incominCW.setCoOperative(packing.getPackHouse());
					incominCW.setBranchId(packing.getBranchId());
					incominCW.setBranchId(packing.getBranchId());
					incominCW.setStockType(CityWarehouse.Stock_type.PACKING_STOCK.ordinal());
					incominCW.setCoOperative(packing.getPackHouse());
					incominCW.setBatchNo(packing.getBatchNo());
					incominCW.setBranchId(packing.getBranchId());
					incominCW.setIsDelete(0);

					CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, incominCW,
							packing.getCreatedDate(), 2, packing.getId(), 0l, incominCW.getLossWeight(),
							incominCW.getHarvestedWeight(), incominCW.getSortedWeight(), 0l, 0d, 0d,str.getValue(), 0l,
							incominCW.getHarvestedWeight(), incominCW.getLossWeight()+rejwt,
							incominCW.getSortedWeight() + str.getValue(), "INCOMING", null);
					incominCW.getCityWarehouseDetails().add(cityWarehouseDetail);
					incominCW.setRevisionNo(DateUtil.getRevisionNumber());
				}
			
			
			
		});

		ctmap.entrySet().forEach(uu -> {
			
			if (uu.getValue().getSortedWeight() <= 0) {
				uu.getValue().setIsDelete(1);
			}
			
			saveOrUpdate(uu.getValue());
		});
	
	}

	@Override
	public void deleteSorting(Sorting sorting) {

		CityWarehouse cityWarehouse = (CityWarehouse) findCityWarehouseByFarmCrops(sorting.getPlanting().getId());
		if (cityWarehouse != null) {
			CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
					sorting.getCreatedDate(), 1, sorting.getId(), 0l, cityWarehouse.getLossWeight(),
					cityWarehouse.getHarvestedWeight(), cityWarehouse.getSortedWeight(), 0l, sorting.getQtyHarvested(),
					sorting.getQtyRejected(), sorting.getQtyNet(), 0l,
					cityWarehouse.getHarvestedWeight() + sorting.getQtyRejected() + sorting.getQtyNet(),
					cityWarehouse.getLossWeight() - sorting.getQtyRejected(),
					cityWarehouse.getSortedWeight() - sorting.getQtyNet(), "SORTING EDIT", null);
			cityWarehouseDetail.setCityWarehouse(cityWarehouse);
			cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);

			cityWarehouse.setHarvestedWeight(cityWarehouseDetail.getTotalGrossWeight());
			cityWarehouse.setLossWeight(cityWarehouseDetail.getTotalLoss());
			cityWarehouse.setSortedWeight(cityWarehouseDetail.getTotalSorted());
			cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
			update(cityWarehouse);
		}
		update(sorting);
		
		CityWarehouse incominCW = new CityWarehouse();
		incominCW = (CityWarehouse) farmerService.findObjectById(
				"from CityWarehouse ct where ct.qrCodeId=? and ct.stockType=? and ct.planting.id=? AND ct.isDelete=0",
				new Object[] { sorting.getQrCodeId(),
						CityWarehouse.Stock_type.HARVEST_STOCK.ordinal(), sorting.getPlanting().getId() });
		if (incominCW != null) {
			
			CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, incominCW,
					sorting.getCreatedDate(), 1, sorting.getId(), 0l, incominCW.getLossWeight(),
					incominCW.getHarvestedWeight(), incominCW.getSortedWeight(), 0l, sorting.getQtyHarvested(),
					sorting.getQtyRejected(), sorting.getQtyNet(), 0l,
					incominCW.getHarvestedWeight() + sorting.getQtyRejected() + sorting.getQtyNet(),
					incominCW.getLossWeight() - sorting.getQtyRejected(),
					incominCW.getSortedWeight() - sorting.getQtyNet(), "SORTING EDIT", null);
			incominCW.getCityWarehouseDetails().add(cityWarehouseDetail);
			incominCW.setHarvestedWeight(cityWarehouseDetail.getTotalGrossWeight());
			incominCW.setLossWeight(0d);
			incominCW.setSortedWeight(0d);
			incominCW.setRevisionNo(DateUtil.getRevisionNumber());
			incominCW.setCreatedDate(sorting.getCreatedDate());
			if (incominCW.getSortedWeight() <= 0) {
				incominCW.setIsDelete(1);
			}
			saveOrUpdate(incominCW);
		}

	}

	public void removeCustomer(Customer customer) {

		utilDAO.delete(customer);

	}

	public List<ShipmentDetails> findShipmentDetailById(Long id) {
		// TODO Auto-generated method stub
		return utilDAO.findShipmentDetailById(id);
	}

	@Override
	public void saveShipment(Shipment shipment) {
		saveOrUpdate(shipment);
		shipment.getShipmentDetails().stream().filter(uu-> uu.getShipment().getStatus() != null && !uu.getShipment().getStatus().equals(4)).forEach(uu -> {
			CityWarehouse cityWarehouse = uu.getCityWarehouse();
			if (cityWarehouse != null) {

				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
						shipment.getCreatedDate(), 2, shipment.getId(), 0l, cityWarehouse.getLossWeight(),
						cityWarehouse.getHarvestedWeight(), cityWarehouse.getSortedWeight(), 0l, 0d, 0d,
						Double.valueOf(uu.getPackingQty()), 0l, cityWarehouse.getHarvestedWeight(),
						cityWarehouse.getLossWeight(),
						cityWarehouse.getSortedWeight() - Double.valueOf(uu.getPackingQty()), "SHIPMENT", null);
				cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
				cityWarehouse.setSortedWeight(cityWarehouse.getSortedWeight() - Double.valueOf(uu.getPackingQty()));
				cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
				update(cityWarehouse);
			}
		});

	}

	@Override
	public Packhouse findWarehouseByCode(String code) {
		// TODO Auto-generated method stub
		return utilDAO.findWarehouseByCode(code);
	}

	public List<Shipment> listOfShipmentByCustomerId(long id) {

		return utilDAO.listOfShipmentByCustomerId(id);
	}

	@Override
	public void updateShipment(Map<CityWarehouse, Double> existingstock, Shipment shipment) {
		shipment.getShipmentDetails().stream().forEach(uu -> {
			CityWarehouse cityWarehouse = uu.getCityWarehouse();
			if (cityWarehouse != null) {

				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
						shipment.getCreatedDate(), 2, shipment.getId(), 0l, cityWarehouse.getLossWeight(),
						cityWarehouse.getHarvestedWeight(), cityWarehouse.getSortedWeight(), 0l, 0d, 0d,
						Double.valueOf(uu.getPackingQty()), 0l, cityWarehouse.getHarvestedWeight(),
						cityWarehouse.getLossWeight(),
						cityWarehouse.getSortedWeight() - Double.valueOf(uu.getPackingQty()), "SHIPMENT", null);
				cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
				cityWarehouse.setSortedWeight(cityWarehouse.getSortedWeight() - Double.valueOf(uu.getPackingQty()));
				cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
				update(cityWarehouse);
			}
		});

	}

	@Override
	public void deleteIncoming(PackhouseIncoming packhouseIncoming) {

		packhouseIncoming.getPackhouseIncomingDetails().stream().filter(uu -> uu.getPackhouseIncoming().getStatus() != null && !uu.getPackhouseIncoming().equals(4)).forEach(uu -> {
			//CityWarehouse cityWarehouse = uu.getKey();
			CityWarehouse cityWarehouse = (CityWarehouse) farmerService.findObjectById(
					"from CityWarehouse ct where ct.qrCodeId=? and ct.stockType=? and ct.planting.id=?",
					new Object[] { uu.getQrCodeId(),
							CityWarehouse.Stock_type.HARVEST_STOCK.ordinal(), uu.getPlanting().getId() });

			CityWarehouse incominCW = new CityWarehouse();
			incominCW = (CityWarehouse) farmerService.findObjectById(
					"from CityWarehouse ct where ct.coOperative.id=? and ct.batchNo=? and ct.stockType=? and ct.planting.id=? and ct.qrCodeId=?",
					new Object[] { uu.getPackhouseIncoming().getPackhouse().getId(), uu.getPackhouseIncoming().getBatchNo(),
							CityWarehouse.Stock_type.RECEPTION_STOCK.ordinal(), uu.getPlanting().getId(),uu.getQrCodeId() });
			if (incominCW != null) {
				incominCW.setSortedWeight(0d);
				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, incominCW,
						packhouseIncoming.getCreatedDate(), 2, packhouseIncoming.getId(), 0l,
						cityWarehouse.getLossWeight(), cityWarehouse.getHarvestedWeight(),
						cityWarehouse.getSortedWeight(), 0l, 0d, 0d, uu.getReceivedWeight(), 0l,
						cityWarehouse.getHarvestedWeight(), cityWarehouse.getLossWeight(),
						cityWarehouse.getSortedWeight() - uu.getTransferWeight(), "INCOMING", null);
				//cityWarehouse.setLossWeight(0d);
				incominCW.setLossWeight(0d);
				incominCW.getCityWarehouseDetails().add(cityWarehouseDetail);
				incominCW.setRevisionNo(DateUtil.getRevisionNumber());
				incominCW.setCreatedDate(packhouseIncoming.getOffLoadingDate());
				if (incominCW.getSortedWeight() <= 0) {
					incominCW.setIsDelete(1);
				}

			} 
			saveOrUpdate(incominCW);

		});
	}

	@Override
	public void deletePacking(Packing packing, Map<CityWarehouse, Double> existingstock) {

		saveOrUpdate(packing);
		existingstock.entrySet().stream().forEach(uu -> {
			CityWarehouse cityWarehouse = uu.getKey();

			if (cityWarehouse != null) {
				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse,
						packing.getCreatedDate(), 2, packing.getId(), 0l, cityWarehouse.getLossWeight(),
						cityWarehouse.getHarvestedWeight(), cityWarehouse.getSortedWeight(), 0l, 0d, 0d, uu.getValue(),
						0l, cityWarehouse.getHarvestedWeight(), cityWarehouse.getLossWeight(),
						cityWarehouse.getSortedWeight() - uu.getValue(), "INCOMING", null);
				cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
				cityWarehouse.setSortedWeight(cityWarehouse.getSortedWeight() - uu.getValue());
				cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
				update(cityWarehouse);

			}

			CityWarehouse incominCW = new CityWarehouse();

			incominCW = (CityWarehouse) farmerService.findObjectById(
					"from CityWarehouse ct where ct.coOperative.id=? and ct.batchNo=? and ct.stockType=? and ct.planting.id=?",
					new Object[] { packing.getPackHouse().getId(), packing.getBatchNo(),
							CityWarehouse.Stock_type.PACKING_STOCK.ordinal(), cityWarehouse.getPlanting().getId() });
			if (incominCW != null) {

				incominCW.setLossWeight(0d);
				incominCW.setSortedWeight(0d);
				incominCW.setSortedWeight(incominCW.getSortedWeight());
				incominCW.setLossWeight(0d);
				incominCW.setHarvestedWeight(0d);
				incominCW.setCreatedDate(packing.getPackingDate());
				incominCW.setCityWarehouseDetails(new HashSet<>());
				incominCW.setFarmcrops(cityWarehouse.getPlanting().getFarmCrops());
				incominCW.setPlanting(cityWarehouse.getPlanting());
				incominCW.setCreatedUser(packing.getCreatedUser());
				incominCW.setCoOperative(packing.getPackHouse());
				incominCW.setBranchId(packing.getBranchId());
				incominCW.setStockType(CityWarehouse.Stock_type.PACKING_STOCK.ordinal());
				incominCW.setCoOperative(packing.getPackHouse());
				incominCW.setBatchNo(packing.getBatchNo());
				incominCW.setBranchId(packing.getBranchId());
				incominCW.setIsDelete(0);

				CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, incominCW,
						packing.getCreatedDate(), 2, packing.getId(), 0l, incominCW.getLossWeight(),
						incominCW.getHarvestedWeight(), incominCW.getSortedWeight(), 0l, 0d, 0d, packing.getTotWt(), 0l,
						incominCW.getHarvestedWeight(), incominCW.getLossWeight(),
						incominCW.getSortedWeight() + packing.getTotWt(), "INCOMING", null);
				incominCW.getCityWarehouseDetails().add(cityWarehouseDetail);
				incominCW.setRevisionNo(DateUtil.getRevisionNumber());
				saveOrUpdate(incominCW);
			}

		});

	}

	@Override
	public void deleteHarvest(Harvest harvest, Harvest oldHarvest) {

		CityWarehouse cityWarehouse = (CityWarehouse) findCityWarehouseByFarmCrops(harvest.getPlanting().getId());
		oldHarvest.setQtyHarvested(harvest.getQtyHarvested() - oldHarvest.getQtyHarvested());

		if (cityWarehouse != null) {

			CityWarehouseDetail cityWarehouseDetail = new CityWarehouseDetail(null, cityWarehouse, harvest.getDate(), 2,
					harvest.getId(), 0l, cityWarehouse.getLossWeight(), cityWarehouse.getHarvestedWeight(),
					cityWarehouse.getSortedWeight(), 0l, oldHarvest.getQtyHarvested(), 0d, 0d, 0l,
					cityWarehouse.getHarvestedWeight(), cityWarehouse.getLossWeight(), cityWarehouse.getSortedWeight(),
					"HARVEST", null);
			cityWarehouse.getCityWarehouseDetails().add(cityWarehouseDetail);
			if (cityWarehouse.getLastHarvestDate().compareTo(harvest.getDate()) < 0) {
				cityWarehouse.setLastHarvestDate(harvest.getDate());
			}
			cityWarehouse.setHarvestedWeight(cityWarehouseDetail.getTotalGrossWeight() - harvest.getQtyHarvested());
			cityWarehouse.setRevisionNo(DateUtil.getRevisionNumber());
			update(cityWarehouse);
		}
		update(harvest);
	}

	public User findByUsernameAndBranchId(String username, String branchId) {
		return utilDAO.findByUsernameAndBranchId(username, branchId);
	}

	@Override
	public List<String> listPasswordsByTypeAndRefId(String valueOf, Long id) {
		return utilDAO.listPasswordsByTypeAndRefId(valueOf, id);
	}

	@Override
	public List<LocaleProperty> listLocaleProp() {
		return utilDAO.listLocaleProp();
	}

	@Override
	public Integer findUserCount(Date sDate, Date eDate, Long long1) {
		return utilDAO.findUserCount(sDate, eDate, long1);
	}

	@Override
	public Integer findMobileUserCount(Date sDate, Date eDate, Long long1) {
		return utilDAO.findMobileUserCount(sDate, eDate, long1);
	}

	@Override
	public Integer findDeviceCount(Date sDate, Date eDate, Long long1) {
		return utilDAO.findDeviceCount(sDate, eDate, long1);
	}

	@Override
	public Integer findWarehouseCount(Date sDate, Date eDate, Long long1) {
		return utilDAO.findWarehouseCount(sDate, eDate, long1);
	}

	@Override
	public Integer findCustomerCount(Date sDate, Date eDate, Long loggedInDealer) {
		return utilDAO.findCustomerCount(sDate, eDate, loggedInDealer);
	}

	@Override
	public Integer findProductsCount(Date sDate, Date eDate, Long loggedInDealer) {
		return utilDAO.findProductsCount(sDate, eDate, loggedInDealer);
	}

	@Override
	public Integer findShipmentsCount(Date sDate, Date eDate, Long loggedInDealer) {
		return utilDAO.findShipmentsCount(sDate, eDate, loggedInDealer);
	}

	@Override
	public String findPlantingArea(Date sDate, Date eDate, Long loggedInDealer) {
		return utilDAO.findPlantingArea(sDate, eDate, loggedInDealer);
	}

	@Override
	public Integer findScoutingCount(Date sDate, Date eDate, Long loggedInDealer) {
		return utilDAO.findScoutingCount(sDate, eDate, loggedInDealer);
	}

	@Override
	public Integer findSprayingCount(Date sDate, Date eDate, Long loggedInDealer) {
		return utilDAO.findSprayingCount(sDate, eDate, loggedInDealer);
	}

	@Override
	public Double findShipmentQuantity(Date sDate, Date eDate, Long loggedInDealer) {
		return utilDAO.findShipmentQuantity(sDate, eDate, loggedInDealer);
	}

	@Override
	public List<Object[]> listCustomerIdAndNameByExporter(Long id) {
		// TODO Auto-generated method stub
		return utilDAO.listCustomerIdAndNameByExporter(id);
	}
	
	@Override
	public void processExporterLic(ExporterRegistration expReg, Map<String, String> pref) {
		
		ESESystem preference = findPrefernceById("1");
		//String user = "username=" + preference.getPreferences().get(ESESystem.SMS_USER_NAME);

		String auth_url =  pref.get(ESESystem.AUTH_URL);
		String auth_username =  pref.get(ESESystem.AUTH_USERNAME);
		String auth_password = pref.get(ESESystem.AUTH_PASSWORD);
		String auth_req =  pref.get(ESESystem.AUTH_REQ);
		String EXP_URL =  pref.get(ESESystem.EXP_URL);
		String bytesEncoded = Base64.encode((auth_username + ":" + auth_password).getBytes());
		String accss_token = null;
		String Msg;
		try {
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, auth_req);
				Request request = new Request.Builder()
				  .url(auth_url)
				  .method("POST", body)
				  .addHeader("Authorization", "Basic " + bytesEncoded)
				  .addHeader("Content-Type", "application/json")
				  .build();
				Response response = client.newCall(request).execute();
				
				String respTk = response.body().string().replace("\\/", "/");
				
				JSONObject Jobject1 = new JSONObject(respTk);
				if (Jobject1.has("token")) {
					accss_token = String.valueOf(Jobject1.get("token"));
				}
		}catch (Exception e) {
			LOGGER.error("Error....", e);
		}
		
		//System.out.println(accss_token);
		if (accss_token != null && !StringUtil.isEmpty(accss_token)) {
			try {
				OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("text/plain");
				RequestBody body = RequestBody.create(mediaType, auth_req);
				Request request = new Request.Builder()
				  .url(EXP_URL+expReg.getRegNumber())
				  .addHeader("Authorization", "Bearer "+accss_token)
				  .build();
				Response response = client.newCall(request).execute();
				String respTk = response.body().string().replace("\\/", "/");
				
				JSONObject Jobject = new JSONObject(respTk);
				JSONObject Jobject1 = new JSONObject();
				if (Jobject.has("success")) {
					if(Jobject.get("success").equals(true)){
						Msg = String.valueOf(Jobject.get("message"));
						
						if (Jobject.has("data")) {
							Jobject1 = (JSONObject) Jobject.get("data");
							boolean vad = (boolean) Jobject1.get("valid");
							boolean app = (boolean) Jobject1.get("approved");
							boolean exp = (boolean) Jobject1.get("expired");
							String expDate = (String) Jobject1.get("expiryDate");
							String licenseNo = (String) Jobject1.get("licenseNo");
							if(vad == true && app == true && exp == false){
								expReg.setStatusMsg(Msg);
								expReg.setIsActive(1l);
							}else if(vad == false){
								Msg = "KRA Pin is Invalid";
								expReg.setStatusMsg(Msg);
								expReg.setIsActive(0l);
							}else if(app == false){
								Msg = "KRA Pin is Not Approved";
								expReg.setStatusMsg(Msg);
								expReg.setIsActive(0l);
							}else if(exp == true){
								Msg = "KRA Pin is Expired";
								expReg.setStatusMsg(Msg);
								expReg.setIsActive(0l);
							}
							Date expDateFor = DateUtil.convertStringToDate(expDate, DateUtil.DATABASE_DATE_TIME);
							expReg.setExpireDate(expDateFor);
							expReg.setRefLetterNo(licenseNo != null && !StringUtil.isEmpty(licenseNo) ? licenseNo : "");
						} 
					}else{
						Msg = String.valueOf(Jobject.get("message"));
						expReg.setStatusMsg(Msg);
						expReg.setIsActive(0l);
						
					}
					update(expReg);
				}else{
					Msg = "Something Went Wrong, Please Try Again";
					expReg.setStatusMsg(Msg);
					expReg.setIsActive(0l);
					update(expReg);
				}
			}catch (Exception e) {
				LOGGER.error("Error while Validate licence....", e);
			}
			
		}else{
			Msg = "Something Went Wrong, Please Try Again";
			expReg.setStatusMsg(Msg);
			expReg.setIsActive(0l);
			update(expReg);
		}
		
		if(expReg.getStatus()==1 && expReg.getIsActive()==1){
			List<Shipment> sp = (List<Shipment>) farmerService.listObjectById(
					"FROM Shipment f where f.status = 3 and f.packhouse.exporter.id=?",
					new Object[] { Long.valueOf(expReg.getId()) });
			List<Harvest> hs = (List<Harvest>) farmerService.listObjectById(
					"FROM Harvest f where f.status = 3 and f.farmCrops.exporter.id=?",
					new Object[] { Long.valueOf(expReg.getId()) });
			if(sp != null){
				processShipmentInactive(1,sp);
			}
			if(hs != null){
				processHarvestInactive(1,hs);
			}
		}
	}
	
	@Override
	public List<Packhouse> listActivePackhouse() {
		// TODO Auto-generated method stub

		return utilDAO.listActivePackhouse();
	}
	
	@Override
	public Agent findAgentByProfileAndBranchIdActive(String agentId, String branchId) {

		return utilDAO.findAgentByProfileAndBranchIdActive(agentId, branchId);
	}
	
	@Override
	public void processShipmentandharvest(ExporterRegistration expReg) {

		utilDAO.processShipmentandharvest(expReg);
	}
	@Override
	public void processShipmentInactive(Integer long1,List<Shipment> expReg) {

		utilDAO.processShipmentInactive(long1,expReg);
	}
	
	@Override
	public void processHarvestInactive(Integer long1,List<Harvest> long2) {

		utilDAO.processHarvestInactive(long1,long2);
	}
	
	@Override
	public Planting findPlantingById(Long id) {
		// TODO Auto-generated method stub
		return utilDAO.findPlantingById(id);
	}
	
	@Override
	public String findExporterNameById(String table, String id) {
		// TODO Auto-generated method stub
		return utilDAO.findExporterNameById(table, id);
	}
	
	@Override
	public List<Planting> listPlantingByFarmCropsId(Long farmid) {
		return utilDAO.listPlantingByFarmCropsId(farmid);
	}
}
