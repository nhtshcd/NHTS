package com.sourcetrace.eses.dao;

import java.util.*;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

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
import com.sourcetrace.eses.entity.ProductTransfer;
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

@Component
public interface IUtilDAO extends IESEDAO {

	void save(Object tt);

	void saveOrUpdate(Object tt);

	void update(Object tt);

	void delete(Object tt);

	public Device findDeviceBySerialNumber(String serialNo);

	public Agent findAgentByProfileAndBranchId(String agentId, String branchId);

	public ESESystem findPrefernceById(String string);

	public BranchMaster findBranchMasterByBranchId(String branchId);

	public ESESystem findPrefernceByOrganisationId(String branchId);

	public ESEAccount findAccountByProfileIdAndProfileType(String profId, int agentAccount);

	public String findCurrentSeasonCodeByBranchId(String branchId);

	public Long findCustomerLatestRevisionNo();

	public Long findTopicCategoryLatestRevisionNo();

	public Long findTopicLatestRevisionNo();

	public Long findFarmerTrainingLatestRevisionNo();

	public List<java.lang.Object[]> findAgroPrefernceDetailById(String systemEse);

	public void updateAgentBODStatus(String profId, int bod);

	public String findPrefernceByName(String farmerIdLength);

	public List<FarmCatalogue> listCatalogueByRevisionNo(Long valueOf);

	public List<LanguagePreferences> listLangPrefByCodes(List<String> codes);

	public List<Customer> listCustomerByRevNo(Long valueOf);

	public List<java.lang.Object[]> listFDVSForFolloUp(String agentId, String folloRev);

	public List<DynamicMenuFieldMap> listDynamisMenubyscoreType();

	public Agent findAgentByProfileId(String agentId);

	public List<Object[]> listDistributionBalanceDownload(Long id, String strevisionNo);

	public List<Country> listCountriesWithAll();

	public List<Country> listCountriesByRevisionNo(long countryRevisionNo);

	public List<State> listStatesByRevisionNo(long stateRevisionNo);

	public List<Locality> listLocalitiesByRevisionNo(long districtRevisionNo);

	public List<Municipality> listMunicipalitiesByRevisionNo(long cityRevisionNo);

	public List<GramPanchayat> listGramPanchayatsByRevisionNo(long panchayatRevisionNo);

	public List<Village> listVillagesByRevisionNo(long villageRevisionNo);

	public List<Product> listProductByRevisionNo(Long valueOf, String branchId);

	public FarmCatalogue findCatalogueByCode(String manufacture);

	public List<Object[]> listSupplierProcurementDetailProperties(String ssDate, String eeDate);

	public List<Object[]> listOfGroup();

	public List<HarvestSeason> listHarvestSeasons();

	public List<Pmt> findTransferStockByGinner(Integer ginner, Long id, String seasonCode);

	public List<java.lang.Object[]> listGinningProcessByHeapProductAndGinning(String code, Long id, Long id2,
			String startDate, String endDate, String seasonCode);

	public List<java.lang.Object[]> listProcurementTraceabilityStockbyAgent(String agentId, Long valueOf,
			String season);

	public List<CityWarehouse> listProcurementStockByAgentIdRevisionNo(String agentId, Long valueOf);

	public List<CityWarehouse> listCityWarehouseByAgentIdRevisionNo(String agentId, Long valueOf);

	public List<PackhouseIncoming> listWarehouseProductByAgentRevisionNoStockByBatch(Long id, Long valueOf);

	public List<PackhouseIncoming> listWarehouseProductByAgentRevisionNoStockAndSeasonCodeByBatch(Long id, Long valueOf,
			String seasonCode);

	public List<String> listSyncLogins();

	User findByUsername(String username);

	List<Entitlement> listEntitlements(String username);

	Role loadRole(Long id);

	String findLocaleProperty(String prop, String string);

	List<Customer> listCustomerByRevisionNo(Long revNo);

	List<FarmCatalogueMaster> listCatalogueMasters();

	FarmCatalogueMaster findCatalogueMasterByTypez(Integer id);

	HarvestSeason findHarvestSeasonbyCode(String asString);

	public List<Product> listProduct();

	public Product findProductByCode(String asString);

	public List<SubCategory> listSubCategory();

	public SubCategory findSubCategoryByid(Long id);

	public SubCategory findCategoryByCode(String asString);

	public List<Vendor> listVendor();

	public Vendor findVendorById(String Id);

	public List<Customer> listCustomer();

	/* public List<Pcbp> listPcbp(); */
	public List<Object[]> listPcbp();

	public Customer findCustomerBycustomerId(String customerId);

	public List<State> listStatesByRevisionNoAndCountryCode(String countryCode, long revisionNo);

	public List<Locality> listDistrictsByRevisionNoAndStateCode(String stateCode, long revisionNo);

	public List<GramPanchayat> listGrampanchayatByRevisionNoAndCityCode(String cityCode, long revisionNo);

	public List<Municipality> listCityByRevisionNoAndDistrictCode(String districtCode, long revisionNo);

	public List<Village> listVillageByRevisionNoAndCityCode(String gpCode, long revisionNo);

	public List<FarmCatalogue> listCatalogueByCatalogueMasterType(Integer catalogueType);

	public SeasonMaster findSeasonBySeasonCode(String seasonCode);

	public Agent findAgentByAgentId(String agentId);

	public AgentAccessLog findAgentAccessLogByAgentId(String agentId, Date txnDate);

	public ESECard findESECardByCardId(String cardId);

	public Village findVillageByCode(String villageCode);

	public AgentAccessLogDetail findAgentAccessLogDetailByTxn(Long accessId, String txnType);

	public Village findVillageById(Long id);

	public ESESystem findPrefernceById(String id, String tenantId);

	public AgroTransaction findEseAccountByTransaction(long id);

	public Procurement findProcurementByRecNo(String receiptNo);

	public SeasonMaster findCurrentSeason();

	public VillageWarehouse findVillageWarehouse(long villageId, String agentId);

	public ESEAccount findESEAccountByProfileId(String profileId, int type);

	public CityWarehouse findCityWarehouseByCoOperative(long coOperativeId, String agentId);

	public Long findVillageWarehouselatestRevisionNo();

	public CityWarehouse findCityWarehouseByCoOperative(long coOperativeId, String agentId, String quality);

	public String findAgentTimerValue();

	public CityWarehouse findCityWarehouseByVillage(long villageId, String agentId, String quality);

	public CityWarehouse findCityWareHouseByWarehouseIdProcrmentGradeCodeAndProcurementProductId(long warehouseId,
			String gradeCode);

	public CityWarehouse findCityWarehouseByState(long stateId, long productId, String quality, String tenantId);

	public CityWarehouse findCityWarehouseByState(long stateId, long productId, String quality);

	public ESEAccount findAccountBySeassonProcurmentProductFarmer(long seasonId, long farmerId);

	public CityWarehouse findSupplierWarehouseByCoOperative(long coOperativeId, String quality, String agentId);

	public CityWarehouse findSupplierWarehouseByCoOperativeProductAndGrade(long coOperativeId, String quality);

	public ESEAccount findAccountByFarmerLoanProduct(long farmerId);

	public void saveOrUpdateCityWarehouse(CityWarehouse cityWarehouse, String tenantId);

	public void saveCityWarehouseDetail(CityWarehouseDetail cityWarehouseDetail, String tenantId);

	public CityWarehouse findCityWarehouseByWarehouseProductBatchNoGradeFarmer(long warehouseId, long productId,
			String batchNo, String grade, String coldStorageName, String blockName, String floorName, String bayNum,
			long farmerId);

	public VillageWarehouse findVillageWarehouse(long villageId, String agentId, String qualityCode);

	public Customer findCustomerById(String customerId);

	public Customer findCustomerByName(String customerName);

	public ESEAccount findAccountByProfileId(String profileId);

	public void updateAgentReceiptNoSequence(String agentId, String offlineReceiptNo);

	public List<AgroTransaction> findAgroTxnByReceiptNo(String receiptNo);

	public Pmt findPMTByReceiptNumber(String receiptNumber);

	public List<FarmCatalogue> listCatalogues();

	public PackhouseIncoming findAgentAvailableStock(long agentId, long prodId);

	public void SaveOrUpdateByTenant(Object obj, String tenantId);

	public void saveOrUpdate(Object warehouseProduct, String tenantId);

	public void save(WarehouseProductDetail warehouseProductDetail, String tenantId);

	public PackhouseIncoming findFieldStaffAvailableStock(String agentId, long productId, String tenantId);

	public void updateESEAccountCashBalById(long id, double cashBalance);

	public PackhouseIncoming findAvailableStockByAgentIdProductIdBatchNoSeason(String agentId, long productId,
			String selectedSeason, String batchNo);

	public PackhouseIncoming findAvailableStock(long warehouseId, long productId, String tenantId);

	public PackhouseIncoming findAvailableStocksBySeasonAndBatch(String servicePointId, long productId, String seasonId,
			String batch, String tenantId);

	public PackhouseIncoming findFieldStaffAvailableStockBySeasonAndBatch(String agentId, long productId,
			String seasonId, String batch, String tenantId, String branchId);

	public String findPrefernceByName(String enableApproved, String tenantId);

	public void saveByTenantId(Object object, String tenantId);

	public PackhouseIncoming findCooperativeAvailableStockByCooperative(long agentId, long productId, String tenantId);

	public Agent findAgentByProfileId(String profileId, String tenantId);

	public PackhouseIncoming findAvailableStock(long warehouseId, long productId);

	public PackhouseIncoming findAgentAvailableStock(String agentId, long productId, String tenantId);

	public ESEAccount findAccountBySeassonProcurmentProductFarmer(long seasonId, long farmerId, String tenantId);

	public PackhouseIncoming findAvailableStockByWarehouseIdSelectedProductSeasonBatchNo(long warehouseId,
			long productId, String selectedSeason, String batchNo);

	public PackhouseIncoming findAvailableStockByWarehouseIdProductIdBatchNoAndSeason(long warehouseId, long productId,
			String batchNo, String seasonCode);

	public PackhouseIncoming findAgentAvailableStock(String agentId, long productId);

	public PackhouseIncoming findWarehouseStockByWarehouseIdAndProductId(long warehouseId, long productId,
			String season);

	public Product findProductById(long id);

	public void saveAgroTransaction(AgroTransaction agroTransaction, String tenantId);

	public ESEAccount findAccountByProfileIdAndProfileType(String profileId, int type, String tenantId);

	public ESEAccount findAccountByProfileId(String profileId, String tenantId);

	public void updateESEAccount(ESEAccount farmerAccount, String tenantId);

	public List<AgroTransaction> listFarmerTransactionHistory(String farmerId, String[] transactionArray, int limit);

	public Customer findCustomer(long id);

	public void updateCashBal(long id, double cashBalance, double creditBalance);

	public ESEAccount findEseAccountByBuyerIdAndTypee(String profileId, int type);

	public ESEAccount findEseAccountByProfileId(String profileId);

	public void updateESEAccountOutStandingBalById(long id, double loanBalance);

	public void save(Object obj, String tenantId);

	public List<Object[]> listMaxTypeByFarmerId(Long farmerId);

	public FarmCatalogue findCatalogueById(Long id);

	public FarmCatalogue findCatalogueByName(String name);

	public FarmCatalogueMaster findFarmCatalogueMasterById(Long id);

	public String findCatalogueValueByCode(String code);

	public List<Agent> listAgentByWarehouseAndRevisionNo(long warehouseId, String revisionNo);

	public List<Agent> listAgentByRevisionNo(String branchId, Long revisionNo);

	public List<Object[]> listFarmsLastInspectionDate();

	public Vendor findVendorByDbId(String id);

	public List<Vendor> listVendorByFilter(String vendorId, String vendorName, String mobileNo);

	public List<Role> listRole();

	public Role findRoleById(Long id);

	public void deleteCityWarehouseByWhId(String id);

	public List<CityWarehouse> listCityWarehouseByCooperativeId(Long id);

	public void deletePmtWarehouseByWhId(long id);

	public List<Pmt> listPmtByCooperativeId(Long id);

	public void deleteWarehouseAgroTrxnByWhId(long id);

	public boolean findAgentMappedWithWarehouse(long id);

	public List<FarmCatalogue> listCataloguesByUnit();

	public PmtDetail findPMTDetailByGradeId(long gradeId);

	public TransactionType findTxnTypeByCode(String code);

	public Transaction findDuplication(Date txnTime, String serialNo, String msgNo);

	public void updateMsgNo(String serialNo, String msgNo);

	public AgentAccessLogDetail listAgnetAccessLogDetailsByIdTxnType(long id, String type, String msgNo);

	public TransactionLog findTransactionLogById(long id);

	public List<Language> listLanguages();

	public User findByUserNameExcludeBranch(String userName);

	public User findByUserNameIncludeBranch(String userName, String branchIdParm);

	public List<BranchMaster> listChildBranchIds(String parentBranch);

	public ESESystem findESESystem();

	public Object[] findWebUserAgentInfoByProfileId(String profileId);

	public Role loadRoleMenus(long id);

	public List<Menu> listMenus();

	public AccessLog findLatestAccessLog(String module, String user);

	public void updateAccessLog(AccessLog accessLog);

	public UptimeLog findLatestUptimeLog(String module);

	public DeploymentLog findLatestDeploymentLog(String module);

	public void saveDeploymentLog(DeploymentLog deploymentLog);

	public void saveUptimeLog(UptimeLog uptimeLog);

	public void updateUptimeLog(UptimeLog uptimeLog);

	public List<Object[]> findParentBranches();

	public List<Object[]> listBranchMastersInfo();

	public List<Object[]> findChildBranches();

	public Object[] findBranchInfo(String branchId);

	public List<Object[]> listParentBranchMastersInfo();

	public byte[] findLogoByCode(String code);

	public List<LocaleProperty> listLocalePropByLang(String lang);

	public String findCurrentSeasonName(String seasonCode);

	public Integer isParentBranch(String loggedUserBranch);

	public List<LanguagePreferences> listLanguagePreferences();

	public List<Object[]> listStates();

	public List<Object[]> listLocalities();

	public List<Municipality> listMunicipality();

	public List<Object[]> listGramPanchayatIdCodeName();

	public List<Object[]> listVillageIdAndName();

	// public List<Object[]> listWarehouseIdAndName();

	public List<Object[]> listProducts();

	public List<Object[]> listCustomerIdAndName();

	public List<Customer> listOfCustomers();

	public Object[] findMenuInfo(long id);

	public List<Object[]> listProcurmentGradeByVarityCode(String code);

	public List<Country> listCountries();

	public void saveFarmerDynmaicList(List<FarmerDynamicFieldsValue> farmerDynamicFieldValuesList);

	public void updateFarmerDynmaicList(List<FarmerDynamicFieldsValue> farmerDynamicFieldValuesList, Long id);

	public DynamicReportConfig findReportById(String id);

	public FarmCatalogueMaster findFarmCatalogueMasterByName(String name);

	public List<FarmCatalogue> findFarmCatalougeByType(int type);

	public Language findLanguageByCode(String code);

	public void updateUserLanguage(String userName, String lang);

	public List<BranchMaster> listBranchMasters();

	public User findUserByProfileId(String id);

	public User findUserByEmailId(String emailId);

	public void editUser(User user);

	public boolean isDashboardUserEntitlementAvailable(String userName);

	public Integer findUserCount(Date sDate, Date eDate, Long long1);

	public Integer findUserCountByMonth(Date sDate, Date eDate);

	public Integer findMobileUserCount(Date sDate, Date eDate, Long long1);

	public Integer findMobileUserCountByMonth(Date sDate, Date eDate);

	public Integer findDeviceCount(Date sDate, Date eDate, Long long1);

	public Integer findDeviceCountByMonth(Date sDate, Date eDate);

	public Integer findFacilitiesCount();

	public Integer findWarehouseCount(Date sDate, Date eDate, Long long1);

	public Integer findWarehouseCountByMonth(Date sDate, Date eDate);

	public Integer findGroupCount();

	public Integer findGroupCountByMonth(Date sDate, Date eDate);

	public List<Object> listWarehouseProductAndStockByWarehouseId(Long warehouseId);

	public List<Object> listWarehouseProductAndStock();

	public BranchMaster findBranchMasterById(Long id);

	public List<Device> listDevices();

	public List<Object> listcowMilkByMonth(Date sDate, Date eDate);

	public List<Object> listCropSaleQtyByMoth(Date sDate, Date eDate, String selectedBranch);

	public List<Object> listCropHarvestByMoth(Date sDate, Date eDate, String selectedBranch);

	public List<Object> listDistributionQtyByMoth(Date sDate, Date eDate, String selectedBranch);

	public List<Object> listProcurementAmtByMoth(Date sDate, Date eDate, String selectedBranch);

	public List<Object> listEnrollmentByMoth(Date sDate, Date eDate);

	public List<Village> listVillagesByCityId(long selectedCity);

	public List<Village> listVillageByBranch(String selectedBranch, Long selectedState);

	public List<String> findBranchList();

	public HarvestSeason findSeasonByCode(String code);

	public List<State> listOfStatesByBranch(String selectedBranch);

	public List<Object[]> findCatalogueCodeAndDisNameByType(Integer type);

	public List<Object[]> findProcurementDataByFilter(Long selectedProduct, String selectedDate);

	public List<Object[]> findProcurementCummulativeData();

	public Role findRole(long id);

	public BranchMaster findBranchMasterByBranchIdAndDisableFilter(String branchId);

	public List<Role> listRoles();

	public List<Menu> listTopParentMenus();

	public List<Role> listRolesByTypeAndBranchId(String branchId);

	public List<Menu> listFlatSubMenusForTopParent(long id);

	public Role findRoleExcludeBranch(long selectedRole);

	public Object[] findRoleInfo(long id);

	public Menu findMenu(long id);

	public List<Action> listAction();

	public Entitlement findEntitlement(String name);

	public BranchMaster findBranchMasterByName(String name);

	public Role findRoleByName(String name);

	public List<GramPanchayat> listGramPanchayatsByCityId(long selectedCity);

	public List<Municipality> listMunicipalitiesByLocalityId(long selectedLocality);

	public List<Locality> findLocalityByStateId(long selectedState);

	public List<State> listStates(String country);

	public List<Object[]> findLocalityByBranch(String Branch);

	public List<Object[]> listLocalityIdCodeAndName();

	public List<Object[]> findFarmerCountBySamithiId();

	public List<Object[]> listVillageByPanchayatId(Long id);

	public List<Object[]> listGramPanchayatByCityId(Long id);

	public List<Object[]> listCityCodeAndNameByDistrictId(Long id);

	public List listCity();

	public List<Village> listVillageByCity(long id);

	public boolean findFarmerMappedWithSamithi(long id);

	public Village findVillage(long id);

	public Integer findFarmerCountBySamtihi(Long id);

	public List<BankInfo> findWarehouseBankinfo(long id);

	public List<IdentityType> listIdentityType();

	public AgentType findAgentTypeById(long id);

	public Agent findAgent(long id);

	public List<AgentType> listAgentType();

	public void updateFarmerIdSequence();

	public void updateShopDealerIdSequence();

	public List<String> listWarehouseNameByServicePointId(long id);

	public List<Product> findProductBySubCategoryCode(Long trim);

	public List<String> listWarehouseNameByAgentIdAndServicePointId(long agentId, long servicePointId);

	public List<String> listSelectedSamithiById(Long agentId);

	public List<String> listAvailableSamithi();

	public List<String> listSelectedSamithiByAgentId(Long agentId, Long cooperativeId);

	public List<String> listAvailableSamithiByAgentId(Long agentId, Long cooperativeId);

	public List<String> listSamithiByCooperativeId(long id);

	public ServicePoint findServicePointByName(String selectedServicePoint);

	public List<String> findServiceLocationBasedOnServicePointAndAgent(long id);

	public ServicePoint findServicePointByCode(String code);

	public List<Municipality> findMunicipalitiesByPostalCode(String postalCode);

	public ESECard findESECardByProfile(String profileId);

	public List<ServicePoint> list();

	public List<Municipality> listMunicipalities(String locality);

	public List<Locality> listLocalities(String state);

	public boolean findAgentIdFromEseTxn(String profileId);

	public boolean findAgentIdFromDevice(long id);

	public boolean isAgentWarehouseProductStockExist(String profileId);

	public boolean isAgentDistributionExist(String profileId);

	public List<CityWarehouse> listProcurementStocksForAgent(String agentId);

	public List<PackhouseIncoming> listWarehouseProductByAgentId(long agentId);

	public void removeCardByProfileId(String profileId);

	public void removeAccountByProfileIdAndProfileType(String profileId, int type);

	public void updateAccountStatus(String profileId, int status, int type);

	public void removeAgentWarehouseMappingByAgentId(long id);

	public Agent findAgent(String profileId);

	public void updateCardStatus(String profileId, int status, int cardRewritable);

	public Agent findAgentByAgentIdd(String profileId);

	public List<Role> listRolesByTypeAndBranchIdExcludeBranch(String branchId);

	public User findUserByUserNameExcludeBranch(String userName);

	public User findUserByNameAndBranchId(String username, String branchId);

	public Device findDeviceById(Long id);

	public List<Agent> listAgentByAgentTypeNotMappedwithDevice();

	public List<Role> listRolesByType(int type);

	public User findUser(long id);

	public User findByUsernameAndBranchId(String username, String branchId);

	public byte[] findUserImage(long userId);

	public Device findUnRegisterDeviceById(Long id);

	public Asset findAssetsByAssetCode(String code);

	public BranchMaster findBranchMasterByIdAndDisableFilter(Long id);

	public List<BranchMaster> listBranchMastersAndDisableFilter();

	public List<Object> listFieldsByViewNameAndDBName(String dbName, String viewName);

	public List<Object[]> listOfViewByDBName(String dbName);

	public FarmCatalogueMaster findFarmCatalogueMasterByCatalogueTypez(Integer valueOf);

	public List<FarmCatalogueMaster> listFarmCatalogueMatsters();

	public List<LanguagePreferences> listLangPrefByCode(String code);

	public List<Language> findIsSurveyStatusLanguage();

	// public boolean isCatMappedWithFarmInventory(long id);
	public FarmCatalogue findCatalogueByNameAndType(String name, int typez);

	public String findVillageNameByCode(String code);

	public String findCityNameByCode(String code);

	public List<Object[]> listOfVillageCodeNameByCityCode(String cityCode);

	public FarmCatalogue findByNameAndType(String educationName, Integer typez);

	public Municipality findMunicipalityByCode(String code);

	public GramPanchayat findGrampanchaythByCode(String code);

	public List<GramPanchayat> listGramPanchayatsByCityCode(String selectedCity);

	public List<GramPanchayat> listGramPanchayatsByCode(String selectedCity);

	public List<Municipality> listMunicipalitiesByCode(String locality);

	public List<State> listStatesByCode(String country);

	public List<Object[]> listCityCodeAndName();

	public List<State> listOfStates();

	public boolean isVillageMappedWithCooperative(long id);

	public boolean isVillageMappingExist(long id);

	public String isVillageMappindExistForDistributionAndProcurement(long id);

	public Village findVillageByNameAndCity(String village, String city);

	public Village findDuplicateVillageName(long id, String name);

	public Country findCountryByName(String name);

	public State findStateByCode(String code);

	public Locality findLocalityByCode(String code);

	public GramPanchayat findGramPanchayatById(long id);

	public Municipality findMunicipalityByName(String name);

	public Municipality findMunicipalityById(Long id);

	public List<Village> listVillagesByCityID(Long cityId);

	public List<PackhouseIncoming> listWarehouseProductByCityCode(String code);

	public Locality findLocalityByName(String name);

	public Locality findLocalityById(Long id);

	public Country findCountryById(Long id);

	public State findStateById(Long id);

	public List<Locality> listLocalitiesByStateID(Long stateId);

	public State findStateByName(String name);

	public Country findCountryByCode(String code);

	public Village findVillageByName(String name);

	public Village findVillageBySelectedVillageId(long parseLong);

	public Vendor findVendor(Long id);

	Distribution findDistributionByReceiptNoTxnType(String receiptNo, String txnType);

	void saveDistribution(Distribution distribution, String tenantId);

	public List<String> findAgentNameByWareHouseId(long coOperativeId);

	public Village findVillageAndCityByVillName(String otherVi, Long cityId);

	public List<Village> listVillagesByPanchayatId(long selectedPanchayat);

	public List<Object[]> listHarvestSeasonFromToPeriod();

	public HarvestSeason findHarvestSeasonById(Long id);

	public HarvestSeason findHarvestSeasonByName(String name);

	DocumentUpload findDocumentUploadById(Long valueOf);

	List<Object[]> listOfProducts();

	List<Object[]> listAgentIdName();

	public boolean isEntitlavailoableforuser(String userName, String auth);

	List<FarmCatalogue> listCatelogueType(String text);

	DocumentUpload findDocumentUploadByReference(String id, Integer type);

	public List<Object[]> listAgroChemicalDealers();

	User findUserByDealerAndRole(Long id, Long id2);

	List<User> listUsersByType(int type);

	public List<FarmCatalogue> listFarmCatalogueWithOther(int type, int otherValue);

	public List<FarmCatalogue> listSeedTreatmentDetailsBasedOnType();

	public List<HarvestSeason> listHarvestSeason();

	List<Object[]> listAgroChemical();

	public List<Object[]> listProcurementProductForSeedHarvest();

	List<Object[]> findPermittedAgroChemicalRegistrationByDealerId(Long dealerId);

	public List<Municipality> findMuniciByDistrictId(long selectedLocality);

	public List<GramPanchayat> findGramByMuniciId(long selectedMunicipality);

	public List<Village> findVillageByGramId(long selectedGram);

	User findInspectorById(Long valueOf);

	public List<User> listUsersByRoleName(String roleName);

	public List<Object> findAgroChemicalDealerByCompanyNameOrEmail(String email, String companyName, String nid);

	public List<User> listUsersExceptAdmin();

	List<Object[]> listDealerDetailsbyRegNo(Long id);

	public List<DocumentUpload> listDocumentsByCode(String code);

	public DocumentUpload findDocumentById(Long id);

	public DocumentUpload findDocumentUploadByCode(String code);

	public List<Object[]> listProcurementProductByVariety(Long procurementProdId);

	List<Object[]> listChemical();

	String findDealerByRegNo(String regNo);

	Role findRoleByType(int type);

	List<Object[]> listTestIdAndNumber();

	Farmer findFarmerById(Long id);

	List<Farmer> getFarmerList();

	User findUserById(Long object);

	Farmer findFarmerByEmail(String email);

	List<Object[]> listExporter();

	List<Object[]> listBuyer();

	List<Object[]> listUOM();

	public List<Farmer> listFarmerName();

	AgentType findAgentTypeByName(String name);

	public Farmer findFarmerByName(String farmerName);

	public List<FarmCatalogue> listCatalogueByCode(String code);

	Farmer findFarmerByNIN(String nin);

	List<ProcurementProduct> listProcurementProductByRevisionNo(Long revisionNo);

	List<ProcurementVariety> listProcurementProductVarietyByRevisionNo(Long revisionNo);

	List<ProcurementGrade> listProcurementProductGradeByRevisionNo(Long revisionNo);

	List<ProcurementProduct> listProcurementProduct();

	Packhouse findWarehouseById(Long id);

	Packhouse findWarehouseByName(String name);

	public List<Packhouse> listAllWarehouses();

	public ExporterRegistration findExproterByEmail(String emailId);

	public ExporterRegistration findExproterByCompanyName(String name);

	public ExporterRegistration findExportRegById(Long id);

	public Pcbp findPcbpById(Long id);

	public Pcbp findPcbpByvarietyAndChamical(Long va, Long ch);

	public State listStatesById(Long id);

	public List<ProcurementVariety> listProcurementVarietyByProcurementProductCode(String ppCode);

	public List<ProcurementVariety> listProcurementVarietyByProcurementProductId(Long id);

	public ProcurementVariety findProcurementVariertyById(Long id);

	public List<Pcbp> findPcbpByProcurementGradeId(Long id);

	public ProcurementProduct findProcurementProductById(Long id);

	public Locality findVillageByStateId(long selectedState);

	public Municipality findCityByDistrictId(long selectedLocality);

	// public GramPanchayat findGramByMuniciIdById(long selectedWard1);
	public Farmer findFarmerByNid(Long nid);

	// public Farmer findFarmerByNid(Long nid) ;
	public Farmer findFarmerByPhone(Long nid);
	// public List<Object[]> listStatesById(Long id);

	Farm findFarmId(Long id);

	ProcurementProduct findProcurementProductByName(String cropName);

	ProcurementVariety findProcurementVariertyByNameAndCropId(String varietyName, Long procurementProductId);

	void addProcurementVariety(ProcurementVariety procurementVariety);

	List<ProcurementGrade> listProcurementGradeByProcurementVarietyId(Long id);

	List<ProcurementGrade> listProcurementGradeByProcurementVarietyIdGradeid(Long id, String IdGrades);

	ProcurementGrade findProcurementGradeByNameAndVarietyId(String name, Long id);

	ProcurementGrade findProcurementGradeById(Long id);

	List<ProcurementVariety> listProcurementVariety();

	List<ProcurementVariety> listProcurementVarietyBasedOnCropCat(String ugandaExport);

	ProcurementProduct findProcurementProductByNameAndBranch(String name, String branchId_F);

	ProcurementProduct findProcurementProductByCode(String code);

	ProcurementVariety findProcurementVariertyByNameAndBranch(String name, String branchId_F);

	ProcurementVariety findProcurementVariertyByCode(String code);

	ProcurementGrade findProcurementGradeByCode(String code);

	ProcurementGrade findProcurementGradeByName(String name);

	ProcurementVariety findProcurementVarietyByName(String name);

	ProcurementGrade findProcurementGradeByNameAndBranch(String name, String branchId_F);

	public ExporterRegistration findExportByLicense(String lid);

	public ExporterRegistration findExportByKrapin(String lid);

	public ExporterRegistration findExportByNid(String nid);

	public ExporterRegistration findExportByCompanyEmail(String cemail);

	FarmCrops findFarmCropsById(long id);

	List<Farm> listFarmByFarmerId(Long id);

	List<FarmCrops> listFarmCropByFarmId(Long farmid);

	List<Planting> listOfPlantingByBlockId(Long id);

	FarmCrops findFarmCropsById(Long id);

	Object[] findIfFarmerExist(String phno, String nid);

	Object[] findIfFarmerExistForFarmer(String nid);

	List<ProcurementGrade> listProcurementGradeByProcurementVarietyIds(String procurementVariety);

	SprayAndFieldManagement findSprayAndFieldManagementById(Long id);

	Scouting findScoutingById(Long id);

	public FarmCrops findFarmCropByplantingfarmcode(String plantingId, String farmCode);

	String findCropHierarchyNameById(String table, String id);

	public String findCropHierarchyHSCodeById(String table, String id);

	String findCropHsCodeByProcurementProductId(String table, String id);

	CityWarehouse findCityWarehouseByFarmCrops(Long id);

	public List<Packhouse> listPackhouse();

	public List<CityWarehouse> listCityWareHouse();

	public List<FarmCrops> listFarmCrops();

	List<ShipmentDetails> findShipmentDetailById(Long id);

	public Packhouse findWarehouseByCode(String code);

	public List<Shipment> listOfShipmentByCustomerId(long id);

	public List<String> listPasswordsByTypeAndRefId(String type, long id);

	List<LocaleProperty> listLocaleProp();

	public Integer findCustomerCount(Date sDate, Date eDate, Long loggedInDealer);

	public Integer findProductsCount(Date sDate, Date eDate, Long loggedInDealer);

	public Integer findShipmentsCount(Date sDate, Date eDate, Long loggedInDealer);

	public String findPlantingArea(Date sDate, Date eDate, Long loggedInDealer);

	public Integer findScoutingCount(Date sDate, Date eDate, Long loggedInDealer);

	public Integer findSprayingCount(Date sDate, Date eDate, Long loggedInDealer);

	public Double findShipmentQuantity(Date sDate, Date eDate, Long loggedInDealer);

	List<Object[]> listCustomerIdAndNameByExporter(Long id);

	List<Packhouse> listActivePackhouse();

	Agent findAgentByProfileAndBranchIdActive(String agentId, String branchId);

	void processShipmentandharvest(ExporterRegistration expReg);

	void processShipmentInactive(Integer long1, List<Shipment> sh);

	void processHarvestInactive(Integer long1, List<Harvest> long2);

	Planting findPlantingById(Long id);

	String findExporterNameById(String table, String id);

	List<Planting> listPlantingByFarmCropsId(Long farmid);

	public ProductTransfer findProductTransferById(Long id);

	public List<Packhouse> listOfPackhouseByExporterId(Long exporterId);

	public List<PackhouseIncoming> listOfIncomingShipmentbasedOnPackhouse(long packHouseId);

	List<ProductTransfer> listOfProductTransfer();

	public List<ProductTransfer> listOfProductTransferByCityWareHouseBatches(List<String> batches);

	List<Object[]> getBuyerCountry(String id);

	List<Object[]> getScoutingRecomm(String plantingId);

	<T> List<T> getAuditRecords(String expRegis, Long id);

	Municipality findCityByVillageId(Long valueOf);

	Locality findLocalityByVillageId(Long valueOf);

	State findStateByVillageId(Long valueOf);

	Country findCountryByVillageId(Long valueOf);

	Locality findLocalityByCityId(Long valueOf);

	State findStateByCityId(Long valueOf);

	Country findCountryByCityId(Long valueOf);

	String findDataByTableFieldById(String table, String code, String field);

	String findObjectIdFromTableByFieldIdAndRevId(String table, String fieldValue, String audId);

	<T> List<T> getAuditRecordsWithRelations(String string, List<String> s, Long id);

	void saveOrUpdatecitywarehouse(CityWarehouse obj);

}
