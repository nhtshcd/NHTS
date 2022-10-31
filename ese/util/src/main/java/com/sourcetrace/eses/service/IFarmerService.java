package com.sourcetrace.eses.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sourcetrace.eses.entity.BankInfo;
import com.sourcetrace.eses.entity.Dashboard;
import com.sourcetrace.eses.entity.DynamicConstants;
import com.sourcetrace.eses.entity.DynamicFeildMenuConfig;
import com.sourcetrace.eses.entity.DynamicFieldConfig;
import com.sourcetrace.eses.entity.DynamicImageData;
import com.sourcetrace.eses.entity.DynamicMenuFieldMap;
import com.sourcetrace.eses.entity.DynamicSectionConfig;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.FarmerDynamicData;
import com.sourcetrace.eses.entity.FarmerDynamicFieldsValue;
import com.sourcetrace.eses.entity.FarmerField;
import com.sourcetrace.eses.entity.HarvestSeason;
import com.sourcetrace.eses.entity.ImageInfo;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.entity.Recalling;

public interface IFarmerService {
	public Farmer findFarmerByFarmerId(String farmerId);

	public Farmer findFarmerByFarmerId(String farmerId, String tenantId);

	void save(Object obj);

	public DynamicImageData findDynamicImageDataById(Long id);

	public List<HarvestSeason> listHarvestSeasons();

	public List<DynamicSectionConfig> findDynamicFieldsBySectionId(String sectionId);

	public List<DynamicConstants> listDynamicConstants();

	public String getFieldValueByContant(String entityId, String referenceId, String group);

	public List<FarmerDynamicFieldsValue> listFarmerDynmaicFieldsByFarmerId(Long farmerId, String txnType);

	public FarmerDynamicData findFarmerDynamicData(String id);

	public FarmerDynamicData findFarmerDynamicData(String txnType, String referenceId);

	public List<Object[]> listValuesbyQuery(String methodName, Object[] parameter, String branchId);

	public String getValueByQuery(String methodName, Object[] parameter, String branchId);

	public List<Object[]> listfarmingseasonlist();

	public List<byte[]> getImageByQuery(String methodName, Object[] parameter, String branchId);

	public List<Object[]> listColdStorageNameDynamic();

	public List<Object[]> listFarmerIDAndName();

	public List<Object[]> listFarmIDAndName();

	public List<Object[]> listValueByFieldName(String field, String branchId);

	public List<DynamicFeildMenuConfig> findDynamicMenusByType(String txnTypez, String branchId);

	public List<DynamicFeildMenuConfig> findDynamicMenusByType(String type);

	public List<DynamicFeildMenuConfig> findDynamicMenusByTypeForOCP(String txnTypez, String branchId);

	public FarmerDynamicData processCustomisedFormula(FarmerDynamicData farmerDynamicData,
			Map<String, DynamicFieldConfig> fieldConfigMap, Map<String, List<FarmerDynamicFieldsValue>> fdMap);

	public void deleteChildObjects(String txnType);

	public void saveOrUpdate(FarmerDynamicData farmerDynamicData, Map<String, List<FarmerDynamicFieldsValue>> fdMap,
			LinkedHashMap<String, DynamicFieldConfig> fieldConfigMap);

	public List<FarmerDynamicFieldsValue> listFarmerDynmaicFieldsByRefId(String refId, String txnType);

	public List<DynamicFeildMenuConfig> findDynamicMenusByMType(String txnType);

	public List<FarmerDynamicFieldsValue> listFarmerDynamicFieldsValuePhotoByRefTxnType(String refId, String txnType);

	public void updateDynamicFarmerFieldComponentType();

	public void update(Object obj);

	public Object[] findFarmerInfoById(Long id);

	public List<DynamicFeildMenuConfig> listDynamicMenus();

	public Farmer findFarmerById(Long id);

	public void addImageInfo(ImageInfo imageInfo);

	public void updateImageInfo(ImageInfo imageInfo);

	public Long findActiveContractFarmersLatestRevisionNoByAgentAndSeason(long agentId, String seasonCode);

	public Long listFarmFieldsByFarmerIdByAgentIdAndSeasonRevisionNo(long id);

	public List<DynamicFeildMenuConfig> listDynamicMenusByRevNo(String revisionNo, String branchId, String tenantId);

	public List<Object[]> listFDVSForFolloUp(String agentId, String revisionNo);

	public List<DynamicMenuFieldMap> listDynamisMenubyscoreType();

	public List<Object[]> listActiveContractFarmersAccountByAgentAndSeason(long agentId, Date revisionDate);

	public List<Object[]> listActiveContractFarmersFieldsBySeasonRevNoAndSamithiWithGramp(long id,
			String currentSeasonCode, Long revisionNo, List<String> branch);

	public List<Object[]> listfarmerDynamicData(List<Long> fidLi);

	public List<Object[]> findCountOfDynamicDataByFarmerId(List<Long> fidLi, String season);

	public List<java.lang.Object[]> listFarmFieldsByFarmerIdByAgentIdAndSeason(long id, Long revisionNo);

	public List<Object[]> listDynamicFieldsCodeAndName();

	public List<Object[]> listFarmerDynamicFieldsValuesByFarmIdList(List<String> farmIdList,
			List<String> selectedDynamicFieldCodeList);

	public List<Object[]> listFarmsLastInspectionDate();

	public FarmerDynamicData findFarmerDynamicDataByTxnUniquId(String txnUniqueId);

	public List<Farmer> listFarmerByFarmerIdByIdList(List<String> id);

	public FarmerDynamicData findFarmerDynamicDataBySeason(String txnType, String id, String season);

	public void deleteObject(Object object);

	public Integer findFarmersCountByStatus(Date stDate, Date edDate, Long long1);

	public Integer findFarmersCountByStatusAndSeason(String currentSeason);

	public Integer findFarmerCountByMonth(Date sDate, Date eDate);

	public String findFarmTotalProposedAreaCount();

	public String findFarmTotalLandAreaCount(Date stDate, Date edDate, Long long1);

	public Integer findFarmCountByMonth(Date sDate, Date eDate);

	public Integer findFarmsCount(Date stDate, Date edDate, Long long1);

	public Integer findCropCount();

	public List<Object> listFarmerCountByGroup();

	public List<Object> listFarmerCountByBranch();

	public List<Object> listTotalFarmAcreByVillage();

	public List<Object> listTotalFarmAcreByBranch();

	public List<String> findFarmerIdsByfarmCode(List<String> farmCodes);

	public HarvestSeason findHarvestSeasonByCode(String seasonCode);

	public List<Object[]> listSeasonCodeAndName();

	public void addBankInformation(BankInfo bankInformation);

	public long findFarmerCountByFPO(Long val);

	public List<Object[]> farmersByBranch();

	public List<Object[]> farmersByCountry(String empty);

	public List<Object[]> farmersByState(String empty);

	public List<Object[]> farmersByLocality(String empty);

	public List<Object[]> farmersByMunicipality(String empty);

	public List<Object[]> farmersByGramPanchayat(String selectedBranch);

	public List<Object[]> farmersByVillageWithGramPanchayat(String selectedBranch);

	public List<Object[]> farmersByVillageWithOutGramPanchayat(String empty);

	public List<Object[]> farmerDetailsByVillage(String empty);

	public List<FarmCatalogue> listCatelogueType(String Type);

	public void updateFarmerStatusByFarmerId(String farmerId);

	public List<DynamicSectionConfig> listDynamicSections();

	public void editFarmer(Farmer existing);

	public List<Object[]> estimatedYield(String codeForCropChart);

	public List<Object[]> actualYield(String codeForCropChart);

	public List<Object[]> populateFarmerLocationCropChart(String codeForCropChart);

	public List<Object[]> getFarmDetailsAndProposedPlantingArea(String locationLevel, String selectedBranch,
			String gramPanchayatEnable);

	public List<Object[]> getFarmDetailsAndCultivationArea(String locationLevel, String selectedBranch,
			String gramPanchayatEnable);

	public Double findTotalCottonAreaCount();

	public Double findTotalCottonAreaCountByMonth(Date firstDateOfMonth, Date date);

	public List<Object[]> populateFarmerCropCountChartByGroup(String codeForCropChart, String selectedBranch);

	public List<DynamicFieldConfig> listDynamicFields();

	public void editFarmerStatus(long id, int statusCode, String statusMsg);

	public void processCustomisedFormula(FarmerDynamicData farmerDynamicData,
			Map<String, DynamicFieldConfig> fieldConfigMap);

	public Farmer findFarmerByMsgNo(String msgNo);

	public void updateFarmerImageInfo(long id, long imageInfoId);

	public void removeUnmappedFarmCropObject();

	public List<FarmerField> listFarmerFields();

	public List<Farmer> listFarmer();

	List<Object[]> listFarmFieldsByFarmerId(long id);

	public List<Farmer> listFarmersByExporter(Long expId);

	public Farm findFarmById(Long id);

	public void editFarm(Farm fm);


	public Farm findFarmbyFarmCode(String farmCode);

	public Object findObjectById(String qryString, Object[] value);

	public Object listObjectById(String qryString, Object[] value);

	public void runSQLQuery(String string, Object[] objects);
	
	public List<Object[]> getharvestdateandquantity(int i, List<Long> fcids);
	
	public List<Object[]> getlistofkenyacode();
	
	//public List<Recalling> getlistofkenyacodeFromRecalling();
	public List<Object[]> getlistofkenyacodeFromRecalling();
	
	public void updateshipmentdetail(String receiptNoId);

	public void removerecallingdetail(Long id, Long id2);
	
	public List<Object[]> executeChartQuery(String chartQuery, int dateFilter, String dateField, String sDate, String eDate, Long id, String exporterField, String groupField, String orderField);

	public List<Dashboard> listDashboardDataByChartDivId();

	public List<ProcurementVariety> findProcurementVarietyByIds(List<Long> convertStringList);
 
}
