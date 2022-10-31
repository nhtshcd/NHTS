package com.sourcetrace.eses.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

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
import com.sourcetrace.eses.entity.LandPreparation;
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
import com.sourcetrace.eses.entity.Packhouse;
import com.sourcetrace.eses.entity.PackhouseIncoming;
import com.sourcetrace.eses.entity.Packing;
import com.sourcetrace.eses.entity.PackingDetail;
import com.sourcetrace.eses.entity.Pcbp;
import com.sourcetrace.eses.entity.Planting;
import com.sourcetrace.eses.entity.WarehouseProductDetail;

@Component
public interface IUtilService {

	public static final String SELECTED = "selected";
	public static final String AVAILABLE = "available";

	public static final String DATE = "date";
	public static final String RECEIPT_NO = "receiptNo";
	public static final String DESC = "desc";
	public static final String QTY = "qty";
	public static final String AVAILABLE_QTY = "availQty";
	public static final String CURRENT_QTY = "currentQty";
	public static final String COSTPRICE = "costPrice";
	public static final String SEASON_CODE = "seasoncode";
	public static final String ADD_HEAP = "Heap Stock Added";

	public static final String REGISTERED_QUESTION = "1";
	public static final String UN_REGISTERED_QUESTION = "0";
	public static final String NORMAL_SECTION = "00";
	public static final String SECTION_THREE = "03";
	public static final String SECTION_SIX = "06";
	public static final String SECTION_THREE_FIRST_TABLE = "3.1";
	public static final String SECTION_SIX_FIRST_TABLE = "6.1";

	public void save(Object i);

	void saveOrUpdate(Object tt);

	void delete(Object tt);

	public Device findDeviceBySerialNumber(String serialNo);

	public Agent findAgentByProfileAndBranchId(String agentId, String branchId);

	public void update(Object agent);

	public ESESystem findPrefernceById(String string);

	public BranchMaster findBranchMasterByBranchId(String branchId);

	public ESESystem findPrefernceByOrganisationId(String branchId);

	public ESEAccount findAccountByProfileIdAndProfileType(String profId, int agentAccount);

	public String findCurrentSeasonCodeByBranchId(String branchId);

	public java.lang.Object findCustomerLatestRevisionNo();

	public Long findTopicCategoryLatestRevisionNo();

	public Long findTopicLatestRevisionNo();

	public Long findFarmerTrainingLatestRevisionNo();

	public List<java.lang.Object[]> findAgroPrefernceDetailById(String systemEse);

	public void updateAgentBODStatus(String profId, int bod);

	public void updateDevice(Device device);

	public String findPrefernceByName(String farmerIdLength);

	public void editAgent(Agent agent);

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

	public Distribution findDistributionByReceiptNoTxnType(String receiptNo, String txnType);

	public List<Product> listProductByRevisionNo(Long valueOf, String branchId);

	public FarmCatalogue findCatalogueByCode(String manufacture);

	public List<Object[]> listSupplierProcurementDetailProperties(String ssDate, String eeDate);

	public List<Object[]> listOfGroup();

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

	List<String> listSyncLogins();

	public Role loadRole(Long id);

	public List<Entitlement> listEntitlements(String username);

	public User findByUsername(String username);

	public String findLocaleProperty(String prop, String string);

	public List<Customer> listCustomerByRevisionNo(Long revNo);

	public List<FarmCatalogueMaster> listCatalogueMasters();

	public FarmCatalogueMaster findCatalogueMasterByTypez(Integer typez);

	public HarvestSeason findHarvestSeasonbyCode(String asString);

	public List<Product> listProduct();

	public Product findProductByCode(String code);

	public List<SubCategory> listSubCategory();

	public SubCategory findSubCategoryByid(Long long1);

	public SubCategory findCategoryByCode(String asString);

	public List<Vendor> listVendor();

	public Vendor findVendorById(String Id);

	public List<Customer> listCustomer();
	
	/*public List<Pcbp> listPcbp();*/
	public List<Object[]> listPcbp();

	public Customer findCustomerBycustomerId(String customerId);

	public List<State> listStatesByRevisionNoAndCountryCode(String countryCode, long revisionNo);

	public List<Locality> listDistrictsByRevisionNoAndStateCode(String stateCode, long revisionNo);

	public List<GramPanchayat> listGrampanchayatByRevisionNoAndCityCode(String cityCode, long revisionNo);

	public List<Municipality> listCityByRevisionNoAndDistrictCode(String districtCode, long revisionNo);

	public List<Village> listVillageByRevisionNoAndCityCode(String gpCode, long revisionNo);

	public Village findVillageByCode(String villageCode);

	public List<FarmCatalogue> listCatalogueByCatalogueMasterType(Integer catalogueType);

	public SeasonMaster findSeasonBySeasonCode(String seasonCode);

	public void createESECard(String profileId, String cardId, Date txnTime, int type);

	public void addCatalogue(FarmCatalogue farmcatalogue);

	public Agent findAgentByAgentId(String agentId);

	public AgentAccessLog findAgentAccessLogByAgentId(String agentId, Date txnDate);

	public ESECard findESECardByCardId(String cardId);

	public AgentAccessLogDetail findAgentAccessLogDetailByTxn(Long accessId, String txnType);

	public Village findVillageById(Long id);

	public void createAgentESEAccount(String profileId, String accountNo, Date txnTime, int type, Agent agent,
			String branchId);

	public List<Municipality> findMunicipalitiesByPostalCode(String postalCode);

	public ESECard findCardByProfileId(String profileId);

	public List<ServicePoint> listServicePoints();

	public List<Municipality> listMunicipalities(String locality);

	public List<Locality> listLocalities(String state);

	public boolean findAgentIdFromEseTxn(String profileId);

	public boolean findAgentIdFromDevice(long id);

	public boolean isAgentWarehouseProductStockExist(String profileId);

	public boolean isAgentDistributionExist(String profileId);

	public List<CityWarehouse> listProcurementStocksForAgent(String agentId);

	public void removeAgentWarehouseProduct(long agentId);

	public void removeCardByProfileId(String profileId);

	public void removeAccountByProfileIdAndProfileType(String profileId, int type);

	public void removeAgent(Agent agent);

	public void createAgent(Agent agent);

	public void updateAccountStatus(String profileId, int status, int type);

	public void removeAgentWarehouseMappingByAgentId(long id);

	public Agent findAgent(String profileId);

	public void editAgentProfile(Agent agent);

	public void updateCardStatus(String profileId, int status, int cardRewritable);

	public ESESystem findPrefernceById(String id, String tenantId);

	public AgroTransaction findEseAccountByTransaction(long id);

	public SeasonMaster findCurrentSeason();

	public VillageWarehouse findVillageWarehouse(long villageId, String agentId);

	public ESEAccount findESEAccountByProfileId(String profileId, int type);

	public CityWarehouse findCityWarehouseByCoOperative(long coOperativeId, String agentId);

	public Long findVillageWarehouselatestRevisionNo();

	public CityWarehouse findCityWarehouseByCoOperative(long coOperativeId, String agentId, String quality);

	public CityWarehouse findCityWarehouseByVillage(long villageId, String agentId, String quality);

	public CityWarehouse findCityWareHouseByWarehouseIdProcrmentGradeCodeAndProcurementProductId(long warehouseId,
			String gradeCode);

	public CityWarehouse findCityWarehouseByState(long stateId, long productId, String quality, String tenantId);

	public CityWarehouse findCityWarehouseByState(long stateId, long productId, String quality);

	public ESEAccount findAccountBySeassonProcurmentProductFarmer(long seasonId, long farmerId);

	public CityWarehouse findSupplierWarehouseByCoOperative(long coOperativeId, String quality, String agentId);

	public CityWarehouse findSupplierWarehouseByCoOperativeProductAndGrade(long coOperativeId, String quality);

	public ESEAccount findAccountByFarmerLoanProduct(long farmerId);

	public void saveCityWarehouseDetail(CityWarehouseDetail cityWarehouseDetail, String tenantId);

	public VillageWarehouse findVillageWarehouse(long villageId, String agentId, String qualityCode);

	public Procurement findProcurementByRecNo(String receiptNo);

	public ESEAccount findAccountByProfileId(String profileId);

	public Customer findCustomerById(String customerId);
	
	public Customer findCustomerByName(String customerName);

	public void addProcurement(Procurement procurement);

	public void updateAgentReceiptNoSequence(String agentId, String offlineReceiptNo);

	public void updateAgroTxnVillageWarehouseAndAccount(List<AgroTransaction> existingList);

	public List<AgroTransaction> findAgroTxnByReceiptNo(String receiptNo);

	public Pmt findPMTByReceiptNumber(String receiptNumber);

	public String addProcurementMTNT(Pmt pmt);

	public List<FarmCatalogue> listCatalogues();

	public PackhouseIncoming findAgentAvailableStock(long agentId, long prodId);

	public void SaveOrUpdateByTenant(Object obj, String tenantId);

	public void saveOrUpdate(Object warehouseProduct, String tenantId);

	public void save(WarehouseProductDetail warehouseProductDetail, String tenantId);

	public PackhouseIncoming findFieldStaffAvailableStock(String agentId, long productId, String tenantId);

	public void updateESEAccountCashBalById(long id, double cashBalance);
	public String findPrefernceByName(String enableApproved, String tenantId);

	public void saveByTenantId(Object object, String tenantId);

	public PackhouseIncoming findCooperativeAvailableStockByCooperative(long agentId, long productId, String tenantId);

	public PackhouseIncoming findAgentAvailableStock(String agentId, long productId, String tenantId);

	public Agent findAgentByProfileId(String profileId, String tenantId);

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

	public void saveAgroTransactionForPayment(AgroTransaction farmerPaymentTxn, AgroTransaction agentPaymentTxn);

	public void updateESEAccountOutStandingBalById(long id, double loanBalance);

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

	public void deleteCityWarehouseByWhId(String id);

	public List<CityWarehouse> listCityWarehouseByCooperativeId(Long id);

	public void deletePmtWarehouseByWhId(long id);

	public List<Pmt> listPmtByCooperativeId(Long id);

	public void deleteWarehouseAgroTrxnByWhId(long id);

	public boolean findCooperativeMappedWithWarhousePayment(long warhouseId);

	public boolean findCooperativeMappedWithSamithi(String code);

	public boolean findAgentMappedWithWarehouse(long id);

	public List<FarmCatalogue> listCataloguesByUnit();

	public void remove(Object object);

	public PmtDetail findPMTDetailByGradeId(long gradeId);

	public void addDevice(Device newDevice);

	public void addTransactionLog(TransactionLog transactionLog);

	public AgentAccessLogDetail listAgnetAccessLogDetailsByIdTxnType(long id, String type, String msgNo);

	public TransactionLog findTransactionLogById(long id);

	public void updateTransactionLog(TransactionLog transactionLog);

	public void createTransaction(Transaction transaction);

	public void editTransaction(Transaction transaction);

	public List<Language> listLanguages();

	List<HarvestSeason> listHarvestSeasons();

	public String getESEDateFormat();

	public String getESEDateTimeFormat();

	public List<Object[]> findParentBranches();

	public List<BranchMaster> listChildBranchIds(String parentBranch);

	public User findUserByUserName(String username);

	public List<Object[]> listBranchMastersInfo();

	public List<Object[]> findChildBranches();

	public Object[] findBranchInfo(String branchId);

	public List<Object[]> listParentBranchMastersInfo();

	public byte[] findLogoByCode(String code);

	public List<LocaleProperty> listLocalePropByLang(String lang);

	public String findCurrentSeasonCode();

	public String findCurrentSeasonName();

	public Integer isParentBranch(String loggedUserBranch);

	public String findAgentTimerValue();

	public List<LanguagePreferences> listLanguagePreferences();

	public List<Object[]> listStates();

	public List<Object[]> listLocalities();

	public List<Municipality> listMunicipality();

	public List<Object[]> listGramPanchayatIdCodeName();

	public List<Object[]> listVillageIdAndName();

	public List<Object[]> listProducts();

	public List<Object[]> listCustomerIdAndName();

	public List<Customer> listOfCustomers();

	public Object[] findMenuInfo(long id);

	public List<Object[]> listProcurmentGradeByVarityCode(String code);

	public List<Country> listCountries();

	public void saveFarmerDynmaicList(List<FarmerDynamicFieldsValue> farmerDynamicFieldValuesList);

	public void updateFarmerDynmaicList(List<FarmerDynamicFieldsValue> farmerDynamicFieldValuesList, Long id);

	public DynamicReportConfig findReportById(String name);

	public FarmCatalogueMaster findFarmCatalogueMasterByName(String name);

	public List<FarmCatalogue> findFarmCatalougeByType(int type);

	public Language findLanguageByCode(String code);

	public void updateUserLanguage(String userName, String lang);

	public List<BranchMaster> listBranchMasters();

	public User findUserByProfileId(String id);

	public User findUserByEmailId(String emailId);

	public void editUser(User user);

	public boolean isDashboardUserEntitlementAvailable(String userName);

	public Integer findUserCount(Date stDate, Date edDate, Long long1);

	public BranchMaster findBranchMasterById(Long id);

	public Integer findUserCountByMonth(Date sDate, Date eDate);

	public Integer findMobileUserCount(Date stDate, Date edDate, Long long1);

	public Integer findMobileUserCountByMonth(Date sDate, Date eDate);

	public Integer findDeviceCount(Date stDate, Date eDate, Long long1);

	public Integer findDeviceCountByMonth(Date sDate, Date eDate);

	public Integer findFacilitiesCount();

	public Integer findWarehouseCount(Date stDate, Date edDate, Long long1);

	public Integer findWarehouseCountByMonth(Date sDate, Date eDate);

	public Integer findGroupCount();

	public Integer findGroupCountByMonth(Date sDate, Date eDate);

	public List<Object> listWarehouseProductAndStockByWarehouseId(Long warehouseId);

	public List<Object> listWarehouseProductAndStock();

	public List<Device> listDevices();

	public List<Object> listcowMilkByMonth(Date sDate, Date eDate);

	public List<Object> listCropSaleQtyByMoth(Date sDate, Date eDate, String selectedBranch);

	public List<Object> listCropHarvestByMoth(Date sDate, Date eDate, String selectedBranch);

	public List<Object> listDistributionQtyByMoth(Date sDate, Date eDate, String selectedBranch);

	public List<Object> listProcurementAmtByMoth(Date sDate, Date eDate, String selectedBranch);

	public List<Object> listEnrollmentByMoth(Date sDate, Date eDate);

	public void addOrganisationESE(ESESystem ese);

	public List<Village> listVillagesByCityId(long selectedCity);

	public List<Village> listVillageByBranch(String selectedBranch, Long selectedState);

	public List<String> findBranchList();

	public HarvestSeason findSeasonByCode(String code);

	public List<State> listOfStatesByBranch(String selectedBranch);

	public List<Object[]> findCatalogueCodeAndDisNameByType(Integer type);

	public List<Object[]> findProcurementDataByFilter(Long selectedProduct, String selectedDate);

	public List<Object[]> findProcurementCummulativeData();

	public void addRole(Role aRole);

	public Role findRole(long id);

	public BranchMaster findBranchMasterByBranchIdAndDisableFilter(String branchId);

	public void editRole(Role role);

	public void removeRole(Role aRole);

	public List<Role> listRoles();

	public List<Menu> listParentMenus();

	public List<Role> listRolesByTypeAndBranchId(String branchId);

	public List<Menu> getSelectedSubMenus(long roleId, long parentMenuId);

	public Role findRoleExcludeBranch(long selectedRole);

	public Map<String, List<Menu>> getAvailableSelectedSubMenus(long roleId, long parentMenuId);

	public Object[] findRoleInfo(long id);

	public void editSubMenusForRole(long roleId, long parentMenuId, List<Long> selectedSubMenus);

	public List<Action> listAction();

	public Map<String, Map<String, String>> getSelectedSubMenusActions(long roleId, long parentMenuId);

	public List<String> getEntitlements(long roleId, long parentMenuId);

	public Role editEntitlements(long roleId, long parentMenuId, List<String> entitlements);

	public void addBranchMaster(BranchMaster bm);

	public void editBranchMaster(BranchMaster bm);

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

	public void updateIdSeq();

	public List<String> listWarehouseNameByServicePointId(long id);

	public List<String> listWarehouseNameByAgentIdAndServicePointId(long agentId, long servicePointId);

	public List<String> listSelectedSamithiById(Long id);

	public List<String> listAvailableSamithi();

	public List<String> listSelectedSamithiByAgentId(Long agentId, Long cooperativeId);

	public List<String> listAvailableSamithiByAgentId(Long agentId, Long cooperativeId);

	public List<String> listSamithiByCooperativeId(Long id);

	public ServicePoint findServicePointByName(String selectedServicePoint);

	public List<String> findServiceLocationBasedOnServicePointAndAgent(long id);

	public ServicePoint findServicePointByCode(String code);

	public List<Role> listRolesByTypeAndBranchIdExcludeBranch(String branchId_F);

	public List<Role> listRolesByType(int type);

	public void addUser(User user);

	public User findUser(long id);

	public void editUserCredential(User user);

	public byte[] findUserImage(long userId);

	public void removeUser(User user);

	public Device findDeviceById(Long id);

	public void removeDevice(Device device);

	public List<Agent> listAgentByAgentTypeNotMappedwithDevice();

	public Device findUnRegisterDeviceById(Long id);

	public void editOrganisationPreference(Map<String, String> preferences, ESESystem ese);

	public Asset findAssetsByAssetCode(String code);

	public BranchMaster findBranchMasterByIdAndDisableFilter(Long id);

	public void updateAsset(Asset existing);

	public void editPreference(Map<String, String> preferences);

	public List<BranchMaster> listBranchMastersAndDisableFilter();

	public List<Object> listFieldsByViewNameAndDBName(String dbName, String viewName);

	public void addDynamicReportConfig(DynamicReportConfig dynamicReportConfig);

	public List<Object[]> listOfViewByDBName(String dbName);

	public FarmCatalogueMaster findFarmCatalogueMasterByCatalogueTypez(Integer valueOf);

	public List<FarmCatalogueMaster> listFarmCatalogueMatsters();

	public List<LanguagePreferences> listLangPrefByCode(String code);

	public void editCatalogue(FarmCatalogue farmcatalogue);

	public List<Language> findIsSurveyStatusLanguage();

	public void removeCatalogue(String name);

	public void addFarmCatalogueMaster(FarmCatalogueMaster catalogueMaster);

	public String findVillageNameByCode(String code);

	public String findCityNameByCode(String code);

	public List<Object[]> listOfVillageCodeNameByCityCode(String cityCode);

	public FarmCatalogue findByNameAndType(String educationName, Integer typez);

	public Municipality findMunicipalityByCode(String code);

	public GramPanchayat findGrampanchaythByCode(String code);

	public void addVillage(Village village);

	public void editVillage(Village existing);

	public List<GramPanchayat> listGramPanchayatsByCityCode(String selectedCity);

	public List<GramPanchayat> listGramPanchayatsByCode(String selectedCity);

	public List<Municipality> listMunicipalitiesByCode(String locality);

	public List<State> listStatesByCode(String country);

	public List<Object[]> listCityCodeAndName();

	public List<State> listOfStates();

	public void removeVillage(Village village);

	public void editMunicipality(Municipality municipality);

	public void editGramPanchayat(GramPanchayat existing);

	public boolean isVillageMappedWithCooperative(long id);

	public String isVillageMappingExist(long id);

	public Village findVillageByNameAndCity(String village, String city);

	public void addGramPanchayat(GramPanchayat gramPanchayat);

	public GramPanchayat findGramPanchayatById(long id);

	public void removeGramPanchayat(GramPanchayat gm);

	public Locality findLocalityByCode(String code);

	public Municipality findMunicipalityByName(String name);

	public void addMunicipality(Municipality municipality);

	public Municipality findMunicipalityById(Long id);

	public List<Village> listVillagesByCityID(Long cityId);

	public void editLocality(Locality locality);

	public void removeCity(Municipality mun);

	public void removeCityWarehouseProduct(String cityCode);

	public void editServicePoint(ServicePoint servicePoint);

	public State findStateByCode(String code);

	public void addLocality(Locality locality);

	public Locality findLocalityByName(String name);

	public Locality findLocalityById(Long id);

	public Country findCountryByName(String name);

	public void removeLocality(String name);

	public void addState(State state);

	public void addCountry(Country country);

	public Country findCountryById(Long id);

	public void editCountry(Country country);

	public void removeCountry(String name);

	public State findStateById(Long id);

	public List<Locality> listLocalitiesByStateID(Long stateId);

	public void removeState(String name);

	public void editState(State state);

	public Country findCountryByCode(String code);

	public State findStateByName(String name);

	public Village findVillageByName(String name);

	public Village findVillageBySelectedVillageId(long parseLong);

	public Vendor findVendor(Long id);

	public void addVendor(Vendor vendor);

	public void createAccount(String profileId, String accountNo, Date txnTime, int type, String branchId);

	public void createAccount(ESEAccount account);

	public void editVendor(Vendor tempVendor);

	public void removeVendor(Vendor vendor);

	public List<String> findAgentNameByWareHouseId(long coOperativeId);

	public Village findVillageAndCityByVillName(String selectedVillage, Long valueOf);

	public List<Village> listVillagesByPanchayatId(Long valueOf);

	public List<Object[]> listHarvestSeasonFromToPeriod();

	public void addHarvestSeason(HarvestSeason harvestSeason);
	
	public void addCustomer(Customer customer);

	public void editHarvestSeason(HarvestSeason existing);
	
	public void editCustomer(Customer customer);

	public HarvestSeason findHarvestSeasonById(Long id);

	public void removeHarvestSeason(HarvestSeason harvestSeason);

	public List<Object[]> listAgentIdName();

	public List<Object[]> listOfProducts();

	public DocumentUpload findDocumentUploadById(Long valueOf);

	boolean isEntitlavailoableforuser(String userName, String auth);

	public List<Product> findProductBySubCategoryCode(Long trim);

	public List<FarmCatalogue> listCatelogueType(String text);

	public DocumentUpload findDocumentUploadByReference(String id, Integer type);

	public List<Object[]> listAgroChemicalDealers();

	public User findUserByDealerAndRole(Long dealeId, Long roleId);

	public List<User> listUsersByType(int type);

	public List<FarmCatalogue> listFarmCatalogueWithOther(int type, int otherValue);

	public List<FarmCatalogue> listSeedTreatmentDetailsBasedOnType();

	public List<HarvestSeason> listHarvestSeason();

	public List<Object[]> listAgroChemical();

	public FarmCatalogue findCatalogueByNameAndType(String catalogueName, Integer valueOf);

	public List<Object[]> listProcurementProductForSeedHarvest();

	public List<Object[]> findPermittedAgroChemicalRegistrationByDealerId(Long dealerId);

	public List<Municipality> findMuniciByDistrictId(long selectedLocality);

	public List<GramPanchayat> findGramByMuniciId(long selectedMunicipality);

	public List<Village> findVillageByGramId(long selectedGram);

	public User findInspectorById(Long valueOf);

	public List<User> listUsersByRoleName(String roleName);

	public List<Object> findAgroChemicalDealerByCompanyNameOrEmail(String email, String companyName, String nid);

	public List<User> listUsersExceptAdmin();

	public List<Object[]> listDealerDetailsbyRegNo(Long valueOf);

	public List<DocumentUpload> listDocumentsByCode(String code);

	public DocumentUpload findDocumentById(Long id);

	public DocumentUpload findDocumentUploadByCode(String code);

	public List<Object[]> listProcurementProductByVariety(Long procurementProdId);

	public List<Object[]> listChemical();

	public String findDealerByRegNo(String regNo);

	public Role findRoleByType(int i);

	List<Object[]> listTestIdAndNumber();

	public Farmer findFarmerById(Long valueOf);

	public List<Farmer> getFarmerList();

	public User findUserById(Long object);

	public Farmer findFarmerByEmail(String email);

	public List<Object[]> listExporter();
	
	public List<Object[]> listBuyer();
	
	public List<Object[]> listUOM();

	List<Farmer> listFarmerName();

	public AgentType findAgentTypeByName(String name);

	public List<FarmCatalogue> listCatalogueByCode(String code);

	public Farmer findFarmerByName(String farmerName);

	public Farmer findFarmerByNIN(String nin);

	public List<ProcurementProduct> listProcurementProductByRevisionNo(Long id);

	public List<ProcurementProduct> listProcurementProduct();

	public List<ProcurementVariety> listProcurementProductVarietyByRevisionNo(Long id);

	public List<ProcurementGrade> listProcurementProductGradeByRevisionNo(Long id);

	public Packhouse findWarehouseById(Long id);

	public Packhouse findWarehouseByName(String name);

	public List<Packhouse> listAllWarehouses();

	public ExporterRegistration findExproterByEmail(String emailId);

	public ExporterRegistration findExproterByCompanyName(String name);

	public ExporterRegistration findExportRegById(Long id);
	
	public Pcbp findPcbpById(Long id);
	
	public Pcbp findPcbpByvarietyAndChamical(Long va, Long ch);

	public State listStatesById(Long id);
	// public List<Object[]> listStatesById(Long id);

	public List<ProcurementVariety> listProcurementVarietyByProcurementProductCode(String ppCode);

	public List<ProcurementVariety> listProcurementVarietyByProcurementProductId(Long id);

	public ProcurementVariety findProcurementVarietyById(Long id);
	
	public List<Pcbp> findPcbpByProcurementGradeId(Long id);

	public ProcurementProduct findProcurementProductById(Long id);

	public Locality findVillageByStateId(long selectedState);

	public Municipality findCityByDistrictId(long selectedLocality);

	// public Village findVillageByVId(long selectedWard);

	public Farmer findFarmerByNid(Long nid);

	public Farm findFarmById(Long id);

	public ProcurementProduct findProcurementProductByName(String cropName);

	public void addProcurementProduct(ProcurementProduct procurementProduct);

	public void editProcurementProduct(ProcurementProduct temp);

	public ProcurementVariety findProcurementVariertyByNameAndCropId(String varietyName, Long id);

	public Farmer findFarmerByPhone(Long nid);

	public void addProcurementVariety(ProcurementVariety procurementVariety);

	public void editProcurementVariety(ProcurementVariety temp);

	public List<ProcurementGrade> listProcurementGradeByProcurementVarietyId(Long valueOf);
	
	public List<ProcurementGrade> listProcurementGradeByProcurementVarietyIdGradeid(Long valueOf,String IdGrades);

	public ProcurementGrade findProcurementGradeByNameAndVarietyId(String gradeName, Long id);

	public void addProcurementGrade(ProcurementGrade procurementGrade);

	public ProcurementGrade findProcurementGradeById(Long valueOf);

	public void editProcurementGrade(ProcurementGrade temp);

	public List<ProcurementVariety> listProcurementVariety();
	
	public List<ProcurementVariety> listProcurementVarietyBasedOnCropCat(String ugandaExport);

	public ProcurementProduct findProcurementProductByCode(String code);

	public ProcurementVariety findProcurementVariertyByCode(String code);

	public ExporterRegistration findExportByLicense(String lid);

	public ExporterRegistration findExportByKrapin(String lid);

	public ExporterRegistration findExportByNid(String nid);

	public ExporterRegistration findExportByCompanyEmail(String cemail);

	public Object[] findIfFarmerExist(String phno, String nid);
	
	public Object[] findIfFarmerExistForFarmer(String nid);

	public FarmCrops findFarmCropsById(Long id);

	public FarmCrops findFarmCropsById(long id);

	List<Farm> listFarmByFarmerId(Long id);

	SprayAndFieldManagement findSprayAndFieldManagementById(Long id);

	Scouting findScoutingById(Long id);

	ProcurementGrade findProcurementGradeByCode(String code);
	
	ProcurementGrade findProcurementGradeByName(String name);
	
	ProcurementVariety findProcurementVarietyByName(String name);

	public List<ProcurementGrade> listProcurementGradeByProcurementVarietyIds(String procurementVariety);

	public List<FarmCrops> listFarmCropByFarmId(Long id);
	
	public List<Planting> listOfPlantingByBlockId(Long id);

	public FarmCrops findFarmCropByplantingfarmcode(String plantingId, String farmCode);

	public String findCropHierarchyNameById(String string, String ugandaExport);

	public String findCropHierarchyHSCodeById(String string, String ugandaExport);

	public String findCropHsCodeByProcurementProductId(String string, String ugandaExport);

	public void addSorting(Sorting sorting);

	public void updateSorting(Sorting sorting, Sorting oldSrot);

	public void saveHarvest(Harvest harvest);

	void updateHarvest(Harvest harvest);

	public List<Packhouse> listPackhouse() ;
	public List<FarmCrops> listFarmCrops();
	public List<CityWarehouse> listCityWareHouse();

	public void saveIncoming(PackhouseIncoming packhouseIncoming);
	
	void savePacking(Packing packing);

	void updatePacking(Packing packing, Map<CityWarehouse, Double> existock, Set<PackingDetail> packingDetails);

	void deleteSorting(Sorting sorting);
	
	
	void updateIncoming(PackhouseIncoming packhouseIncoming);
	
	public void removeCustomer(Customer customer);
	
	public List<ShipmentDetails> findShipmentDetailById(Long id);

	void saveShipment(Shipment shipment);
	
	public Packhouse findWarehouseByCode(String code);
	
	public List<Shipment> listOfShipmentByCustomerId(long id);

	public void updateShipment(Map<CityWarehouse, Double> existingstock, Shipment shipment);
	
	void deletePacking(Packing packing, Map<CityWarehouse, Double> existock);

	void deleteIncoming(PackhouseIncoming incoming);

	void deleteHarvest(Harvest harvest, Harvest oldHarvest);

	CityWarehouse findCityWarehouseByFarmCrops(Long id);
	
	public User findByUsernameAndBranchId(String username, String branchId);

	void updateStocks(Harvest harvest, Map<CityWarehouse, Double> existock);

	public List<String> listPasswordsByTypeAndRefId(String valueOf, Long id);

	public List<LocaleProperty> listLocaleProp();

	public Integer findCustomerCount(Date stDate, Date edDate, Long loggedInDealer);

	public Integer findProductsCount(Date stDate, Date edDate, Long loggedInDealer);

	public Integer findShipmentsCount(Date stDate, Date edDate, Long loggedInDealer);

	public String findPlantingArea(Date stDate, Date edDate, Long loggedInDealer);

	public Integer findScoutingCount(Date stDate, Date edDate, Long loggedInDealer);

	public Integer findSprayingCount(Date stDate, Date edDate, Long loggedInDealer);

	public Double findShipmentQuantity(Date stDate, Date edDate, Long loggedInDealer);
	
	public List<Object[]> listCustomerIdAndNameByExporter(Long id);
	
	void processExporterLic(ExporterRegistration expReg,Map<String, String> preferences);
	
	public List<Packhouse> listActivePackhouse();
	
	public Agent findAgentByProfileAndBranchIdActive(String agentId, String branchId);
	
	void processShipmentandharvest(ExporterRegistration expReg);
	
	void processShipmentInactive(Integer long1,List<Shipment> sh);
	
	void processHarvestInactive(Integer long1,List<Harvest> long2);

	public Planting findPlantingById(Long id);
	
	public String findExporterNameById(String string, String ugandaExport);
	
	public List<Planting> listPlantingByFarmCropsId(Long id);

	void deletePacking(Packing packing, Map<CityWarehouse, Double> existingstock, Set<PackingDetail> pdSet);
	
}
