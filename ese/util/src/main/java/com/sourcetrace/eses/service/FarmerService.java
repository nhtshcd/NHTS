package com.sourcetrace.eses.service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.sourcetrace.eses.dao.IFarmerDAO;
import com.sourcetrace.eses.dao.IUtilDAO;
import com.sourcetrace.eses.entity.BankInfo;
import com.sourcetrace.eses.entity.Coordinates;
import com.sourcetrace.eses.entity.CoordinatesMap;
import com.sourcetrace.eses.entity.Dashboard;
import com.sourcetrace.eses.entity.DynamicConstants;
import com.sourcetrace.eses.entity.DynamicFeildMenuConfig;
import com.sourcetrace.eses.entity.DynamicFieldConfig;
import com.sourcetrace.eses.entity.DynamicFieldConfig.LIST_METHOD;
import com.sourcetrace.eses.entity.DynamicImageData;
import com.sourcetrace.eses.entity.DynamicMenuFieldMap;
import com.sourcetrace.eses.entity.DynamicSectionConfig;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.FarmerDynamicData;
import com.sourcetrace.eses.entity.FarmerDynamicFieldsValue;
import com.sourcetrace.eses.entity.FarmerField;
import com.sourcetrace.eses.entity.HarvestSeason;
import com.sourcetrace.eses.entity.ImageInfo;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.entity.SeasonMaster;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

@Service
@Lazy
@Transactional
public class FarmerService implements IFarmerService {

	private static final Logger LOGGER = Logger.getLogger(FarmerService.class.getName());
	private static final DateFormat TXN_DATE_FORMAT = new SimpleDateFormat(DateUtil.TXN_DATE_TIME);
	private static final String NOT_APPLICABLE = "N/A";

	@Autowired
	private IFarmerDAO farmerDAO;

	@Autowired
	private IUtilService utilService;

	private IUtilDAO utilDAO;

	public Farmer findFarmerByFarmerId(String farmerId) {

		return farmerDAO.findFarmerByFarmerId(farmerId);
	}

	public Farmer findFarmerByFarmerId(String farmerId, String tenantId) {

		return farmerDAO.findFarmerByFarmerId(farmerId, tenantId);
	}

	@Override
	public void save(Object obj) {

		try {
			obj.getClass().getMethod("setCreatedUser", String.class).invoke(obj, ReflectUtil.getCurrentUser());
			obj.getClass().getMethod("setCreatedDate", Date.class).invoke(obj, new Date());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		farmerDAO.save(obj);
	}

	@Override
	public DynamicImageData findDynamicImageDataById(Long id) {

		return farmerDAO.findDynamicImageDataById(id);
	}

	@Override
	public List<HarvestSeason> listHarvestSeasons() {

		return farmerDAO.listHarvestSeasons();
	}

	@Override
	public List<DynamicSectionConfig> findDynamicFieldsBySectionId(String sectionId) {
		// TODO Auto-generated method stub
		return farmerDAO.findDynamicFieldsBySectionId(sectionId);
	}

	@Override
	public List<DynamicConstants> listDynamicConstants() {
		return farmerDAO.listDynamicConstants();
	}

	@Override
	public String getFieldValueByContant(String entityId, String referenceId, String group) {
		return farmerDAO.getFieldValueByContant(entityId, referenceId, group);
	}

	@Override
	public List<FarmerDynamicFieldsValue> listFarmerDynmaicFieldsByFarmerId(Long farmerId, String txnType) {
		return farmerDAO.listFarmerDynmaicFieldsByFarmerId(farmerId, txnType);
	}

	@Override
	public FarmerDynamicData findFarmerDynamicData(String id) {
		return farmerDAO.findFarmerDynamicData(id);
	}

	@Override
	public FarmerDynamicData findFarmerDynamicData(String txnType, String referenceId) {
		return farmerDAO.findFarmerDynamicData(txnType, referenceId);
	}

	@Override
	public List<Object[]> listValuesbyQuery(String methodName, Object[] parameter, String branchId) {
		// TODO Auto-generated method stub
		return farmerDAO.listValuesbyQuery(methodName, parameter, branchId);
	}

	@Override
	public String getValueByQuery(String methodName, Object[] parameter, String branchId) {
		// TODO Auto-generated method stub
		return farmerDAO.getValueByQuery(methodName, parameter, branchId);
	}

	@Override
	public List<Object[]> listfarmingseasonlist() {

		return farmerDAO.listfarmingseasonlist();
	}

	@Override
	public List<byte[]> getImageByQuery(String methodName, Object[] parameter, String branchId) {
		// TODO Auto-generated method stub
		return farmerDAO.getImageByQuery(methodName, parameter, branchId);
	}

	@Override
	public List<Object[]> listColdStorageNameDynamic() {
		// TODO Auto-generated method stub
		return farmerDAO.listColdStorageNameDynamic();
	}

	@Override
	public List<Object[]> listFarmerIDAndName() {
		// TODO Auto-generated method stub
		return farmerDAO.listFarmerIDAndName();
	}

	@Override
	public List<Object[]> listFarmIDAndName() {
		// TODO Auto-generated method stub
		return farmerDAO.listFarmIDAndName();
	}

	@Override
	public List<Object[]> listValueByFieldName(String field, String branchId) {
		// TODO Auto-generated method stub
		return farmerDAO.listValueByFieldName(field, branchId);
	}

	@Override
	public List<DynamicFeildMenuConfig> findDynamicMenusByType(String txnTypez, String branchId) {
		return farmerDAO.findDynamicMenusByType(txnTypez, branchId);
	}

	@Override
	public List<DynamicFeildMenuConfig> findDynamicMenusByType(String type) {
		return farmerDAO.findDynamicMenusByType(type);
	}

	@Override
	public List<DynamicFeildMenuConfig> findDynamicMenusByTypeForOCP(String txnTypez, String branchId) {
		return farmerDAO.findDynamicMenusByTypeForOCP(txnTypez, branchId);
	}

	public FarmerDynamicData processCustomisedFormula(FarmerDynamicData farmerDynamicData,
			Map<String, DynamicFieldConfig> fieldConfigMap, Map<String, List<FarmerDynamicFieldsValue>> fdMap) {
		return farmerDAO.processCustomisedFormula(farmerDynamicData, fieldConfigMap, fdMap);

	}

	@Override
	public void deleteChildObjects(String txnType) {
		farmerDAO.deleteChildObjects(txnType);

	}

	FarmerDynamicData fdData = null;

	public void saveOrUpdate(FarmerDynamicData farmerDynamicData, Map<String, List<FarmerDynamicFieldsValue>> fdMap,
			LinkedHashMap<String, DynamicFieldConfig> fieldConfigMap) {
		fdData = farmerDynamicData;
		if (fdMap.isEmpty()) {
			fdMap = fdData.getFarmerDynamicFieldsValues().stream()
					.collect(Collectors.groupingBy(FarmerDynamicFieldsValue::getFieldName));
		}
		if (fdData.getIsScore() != null
				&& (fdData.getIsScore() == 1 || fdData.getIsScore() == 2 || fdData.getIsScore() == 3)
				&& fdData.getScoreValue() != null && !fdData.getScoreValue().isEmpty()) {
			fdData = processScoreCalculation(fdData, fieldConfigMap);
		}
		List<FarmerDynamicFieldsValue> formulaLIst = farmerDAO.processFormula(fdData, fdMap, fieldConfigMap);
		formulaLIst.stream().filter(u -> (u.getIsUpdateProfile() != null && u.getIsUpdateProfile().equals("1")))
				.forEach(form -> {
					fdData.getProfileUpdateFields().put(form.getComponentType(), form.getFieldValue());
				});
		fdData.getFarmerDynamicFieldsValues().addAll(formulaLIst);

		fdData.setRevisionNo(DateUtil.getRevisionNumber());
		farmerDAO.saveOrUpdate(fdData);

		if (fdData.getProfileUpdateFields() != null && !ObjectUtil.isEmpty(fdData.getProfileUpdateFields())) {
			processProfileUpdates(fdData.getProfileUpdateFields(), fieldConfigMap, fdData);
		}

	}

	Map<String, Map<String, String>> scoreMap = new HashMap<>();
	Map<String, Integer> scoreTypeMap = new HashMap<>();
	Double totalPerce = 0.0;
	List<FarmerDynamicFieldsValue> fdv = new ArrayList<>();
	Map<String, FarmerDynamicFieldsValue> fdMap = new HashMap<>();
	boolean updatedS = false;

	private FarmerDynamicData processScoreCalculation(FarmerDynamicData farmerDynamicData,
			Map<String, DynamicFieldConfig> fieldConfigMap) {
		totalPerce = 0.0;
		scoreMap = farmerDynamicData.getScoreValue();
		fdv = new ArrayList<>();
		fdMap = new HashMap<>();
		farmerDynamicData.getFarmerDynamicFieldsValues().stream().filter(u -> scoreMap.containsKey(u.getFieldName()))
				.forEach(u -> {
					Map<String, String> smMap = scoreMap.get(u.getFieldName());
					updatedS = false;
					if (u.getPercentage() == null) {
						u.setPercentage(0.0);
					}
					if (!updatedS) {
						if (Arrays.asList("6", "9").contains(u.getComponentType())) {

							smMap.entrySet().forEach(score -> {
								if (!updatedS) {
									// List<String> an =
									// Arrays.asList(u.getFieldValue().split(","));
									List<String> an = new ArrayList(Arrays.asList(u.getFieldValue().split(",")));
									if (score.getKey().contains("!")) {
										List<String> excluse = Arrays.asList(score.getKey().split("!")[1].split(","));
										String noOf = score.getKey().split("!")[0].toString();

										an.removeAll(excluse);
										if (StringUtil.isInteger(noOf)) {
											Integer noOfEl = Integer.valueOf(noOf);
											if (noOfEl == an.size()) {
												u.setScore(Integer.valueOf(score.getValue().split("~")[0].toString()));
												u.setPercentage(
														Double.valueOf(score.getValue().split("~")[1].toString()));
												updatedS = true;
												return;
											} else if (noOfEl == -1
													&& (getCatCount(u.getListMethod(), u.getAccessType())
															- excluse.size()) == an.size()) {
												u.setScore(Integer.valueOf(score.getValue().split("~")[0].toString()));
												u.setPercentage(
														Double.valueOf(score.getValue().split("~")[1].toString()));
												updatedS = true;
											}
										} else if (noOf.contains(",")) {
											if (Arrays.asList(noOf.split(","))
													.containsAll(Arrays.asList(u.getFieldValue().split(",")))
													&& noOf.split(",").length == u.getFieldValue().split(",").length) {
												u.setScore(Integer.valueOf(score.getValue().split("~")[0].toString()));
												u.setPercentage(
														Double.valueOf(score.getValue().split("~")[1].toString()));
												updatedS = true;
											}
										} else {
											if (noOf.equals(u.getFieldValue())) {
												u.setScore(Integer.valueOf(score.getValue().split("~")[0].toString()));
												u.setPercentage(
														Double.valueOf(score.getValue().split("~")[1].toString()));
												updatedS = true;
											}
										}

									} else {
										if (StringUtil.isInteger(score.getKey())) {
											Integer noOfEl = Integer.valueOf(score.getKey());
											if (noOfEl == an.size()) {
												u.setScore(Integer.valueOf(score.getValue().split("~")[0].toString()));
												u.setPercentage(
														Double.valueOf(score.getValue().split("~")[1].toString()));
												updatedS = true;
											} else if (noOfEl == -1
													&& (getCatCount(u.getListMethod(), u.getAccessType())) == an
															.size()) {
												u.setScore(Integer.valueOf(score.getValue().split("~")[0].toString()));
												u.setPercentage(
														Double.valueOf(score.getValue().split("~")[1].toString()));
												updatedS = true;
											}
										} else {
											if (Arrays.asList(score.getKey().split(","))
													.containsAll(Arrays.asList(u.getFieldValue().split(",")))
													&& score.getKey().split(",").length == u.getFieldValue()
															.split(",").length) {
												u.setScore(Integer.valueOf(score.getValue().split("~")[0].toString()));
												u.setPercentage(
														Double.valueOf(score.getValue().split("~")[1].toString()));
												updatedS = true;
											} else if (score.getKey().equals("-1")
													&& getCatCount(u.getListMethod(), u.getAccessType()) == Arrays
															.asList(u.getFieldValue().split(",")).size()) {
												u.setScore(Integer.valueOf(score.getValue().split("~")[0].toString()));
												u.setPercentage(
														Double.valueOf(score.getValue().split("~")[1].toString()));
												updatedS = true;
											}
										}
									}
								}
							});
							if (u.getPercentage() != null) {
								totalPerce = totalPerce + u.getPercentage();
							}
						} else {
							smMap.entrySet().forEach(score -> {
								if (!updatedS) {
									if (score.getKey().contains("#")) {
										/*
										 * format of the score
										 * /*CT001#f1~3~2.0,f2~2~10,f3~3-2.0
										 */
										String[] cond = score.getKey().split("#");
										String catCode = cond[0].toString();
										String[] fields = cond[1].toString().split(",");
										if (u.getFieldValue().equals(catCode.trim())) {
											Arrays.asList(fields).stream().forEach(field -> {
												String ff = field.split("~")[0];
												Integer sc = Integer.valueOf(field.split("~")[1].toString());
												Double per = Double.valueOf(field.split("~")[2].toString());
												FarmerDynamicFieldsValue farmerDynamicFieldsValue = new FarmerDynamicFieldsValue();
												farmerDynamicFieldsValue.setTxnType(farmerDynamicData.getTxnType());
												farmerDynamicFieldsValue.setFieldName(ff.trim());
												farmerDynamicFieldsValue
														.setReferenceId(farmerDynamicData.getReferenceId());
												farmerDynamicFieldsValue.setCreatedDate(new Date());
												farmerDynamicFieldsValue
														.setCreatedUser(farmerDynamicData.getCreatedUser());
												farmerDynamicFieldsValue
														.setTxnUniqueId(farmerDynamicData.getTxnUniqueId());
												farmerDynamicFieldsValue.setScore(sc);
												farmerDynamicFieldsValue.setPercentage(per);
												farmerDynamicFieldsValue.setAccessType(fieldConfigMap
														.get(farmerDynamicFieldsValue.getFieldName()) != null
																? fieldConfigMap
																		.get(farmerDynamicFieldsValue.getFieldName())
																		.getAccessType()
																: 0);
												farmerDynamicFieldsValue.setComponentType(fieldConfigMap
														.get(farmerDynamicFieldsValue.getFieldName()) != null
																? fieldConfigMap
																		.get(farmerDynamicFieldsValue.getFieldName())
																		.getComponentType()
																: "0");

												farmerDynamicFieldsValue.setListMethod(fieldConfigMap
														.get(farmerDynamicFieldsValue.getFieldName()) != null
														&& fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName())
																.getCatalogueType() != null
																		? fieldConfigMap
																				.get(farmerDynamicFieldsValue
																						.getFieldName())
																				.getCatalogueType()
																		: "");
												farmerDynamicFieldsValue.setParentId(fieldConfigMap
														.get(farmerDynamicFieldsValue.getFieldName()) != null
														&& fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName())
																.getReferenceId() != null
																		? fieldConfigMap
																				.get(farmerDynamicFieldsValue
																						.getFieldName())
																				.getReferenceId()
																		: 0);

												farmerDynamicFieldsValue.setValidationType(fieldConfigMap
														.get(farmerDynamicFieldsValue.getFieldName()) != null
																? fieldConfigMap
																		.get(farmerDynamicFieldsValue.getFieldName())
																		.getValidation()
																: "0");
												farmerDynamicFieldsValue.setFarmerDynamicData(farmerDynamicData);
												farmerDynamicFieldsValue.setIsMobileAvail(fieldConfigMap
														.get(farmerDynamicFieldsValue.getFieldName()) != null
																? fieldConfigMap
																		.get(farmerDynamicFieldsValue.getFieldName())
																		.getIsMobileAvail()
																: "0");
												fdv.add(farmerDynamicFieldsValue);
												updatedS = true;
												if (u.getPercentage() != null) {
													totalPerce = totalPerce + per;
												}
											});
										}

									} /*
										 * else
										 * if(score.getKey().contains("\\|")){
										 * format of the score
										 * /*CT001|f1,f2,f3~-1~3-2.0 String[]
										 * cond =score.getKey().split("\\|");
										 * String catCode= cond[0].toString();
										 * if(u.getFieldValue().equals(catCode.
										 * trim())){ String[] fields =
										 * cond[1].toString().split("~")[0].
										 * split(","); String noOf =
										 * cond[1].toString().split("~")[1].
										 * toString(); String scPer =
										 * cond[1].toString().split("~")[2].
										 * toString(); List<Integer> ii = new
										 * ArrayList<>();
										 * Arrays.asList(fields).stream().
										 * forEach(field ->{
										 * if(fdMap.containsKey(field.trim())){
										 * ii.add(fdMap.get(field.trim()).
										 * getScore()); }else{ ii.add(0); } });
										 * 
										 * if(noOf!=null && noOf.equals("-1") &&
										 * ii.stream().filter(per ->
										 * per==3).count() ==fields.length ){
										 * Integer sc =
										 * Integer.valueOf(scPer.split("-")[0].
										 * toString()); Double per =
										 * Double.valueOf(scPer.split("-")[1].
										 * toString()); u.setScore(sc);
										 * u.setPercentage(per);
										 * 
										 * }else if(noOf!=null &&
										 * ii.stream().filter(per ->
										 * Arrays.asList(noOf.split(",")).
										 * contains(String.valueOf(per))).count(
										 * ) ==fields.length ){ Integer sc =
										 * Integer.valueOf(scPer.split("-")[0].
										 * toString()); Double per =
										 * Double.valueOf(scPer.split("-")[1].
										 * toString()); u.setScore(sc);
										 * u.setPercentage(per);
										 * 
										 * }else{ u.setScore(0);
										 * u.setPercentage(0.0);
										 * 
										 * } } if (u.getPercentage() != null) {
										 * totalPerce = totalPerce
										 * +u.getPercentage(); } }
										 */else if (score.getKey().equals(u.getFieldValue())) {
										u.setScore(Integer.valueOf(score.getValue().split("~")[0].toString()));
										u.setPercentage(Double.valueOf(score.getValue().split("~")[1].toString()));
										updatedS = true;
										if (u.getPercentage() != null) {
											totalPerce = totalPerce + u.getPercentage();
										}
									} else if (score.getKey().equals("-1") && getCatCount(u.getListMethod(),
											u.getAccessType()) == Arrays.asList(u.getFieldValue().split(",")).size()) {
										u.setScore(Integer.valueOf(score.getValue().split("~")[0].toString()));
										u.setPercentage(Double.valueOf(score.getValue().split("~")[1].toString()));
										if (u.getPercentage() != null) {
											totalPerce = totalPerce + u.getPercentage();
										}
										updatedS = true;
									}

								}
							});

						}
					} else {
						if (u.getPercentage() != null) {
							totalPerce = totalPerce + u.getPercentage();
						}
					}
					fdMap.put(u.getFieldName(), u);

				});
		farmerDynamicData.getFarmerDynamicFieldsValues().addAll(fdv);
		farmerDynamicData.setTotalScore(totalPerce);
		return farmerDynamicData;
	}

	public Integer getCatCount(String catType, Integer accessType) {
		if (accessType > 0 && accessType == 1) {
			return listCatelogueType(catType).size();
		} else if (accessType > 0 && accessType == 2) {
			return catType.split(",").length;
		} else if (accessType > 0 && accessType == 3) {
			LIST_METHOD[] methods = DynamicFieldConfig.LIST_METHOD.values();
			try {
				return getOptions(methods[Integer.valueOf(catType)].toString()).size();
			} catch (Exception e) {

			}
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getOptions(String methodName) {

		Map<String, String> returnMap = new LinkedHashMap<String, String>();
		try {
			Method method = this.getClass().getMethod(methodName);
			Object returnObj = method.invoke(this);
			if (!ObjectUtil.isEmpty(returnObj)) {
				returnMap = (Map<String, String>) returnObj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	public List<FarmCatalogue> listCatelogueType(String type) {

		// TODO Auto-generated method stub
		return farmerDAO.listCatelogueType(type);
	}

	boolean updated = false;
	Integer val = 0;
	Method setterMethod;

	public Farmer findFarmerById(Long id) {

		return farmerDAO.findFarmerById(id);
	}

	private Object getObjectWithEntity(String profileEnitiy, String entityId, String referenceId,
			Map<Class, Object> profileObject) {
		Object f = null;
		if (profileEnitiy.equals(entityId)) {

			if (entityId.equals(String.valueOf(DynamicFeildMenuConfig.EntityTypes.FARMER.ordinal()))) {
				if (profileObject.containsKey(Farmer.class)) {
					f = (Farmer) profileObject.get(Farmer.class);
				} else {
					f = findFarmerById(Long.valueOf(referenceId));
				}
			}
			return f;
		} else {
			if (entityId.equals(String.valueOf(DynamicFeildMenuConfig.EntityTypes.FARM_CROPS.ordinal()))) {
				if (profileEnitiy.equals(String.valueOf(DynamicFeildMenuConfig.EntityTypes.FARMER.ordinal()))) {
					if (profileObject.containsKey(Farmer.class)) {
						f = (Farmer) profileObject.get(Farmer.class);
					} else {

						// f = fm.getFarm().getFarmer();
					}

				}
			} else if (entityId.equals(String.valueOf(DynamicFeildMenuConfig.EntityTypes.FARM.ordinal()))
					|| entityId.equals(String.valueOf(DynamicFeildMenuConfig.EntityTypes.CERTIFICATION.ordinal()))) {
				if (profileEnitiy.equals(String.valueOf(DynamicFeildMenuConfig.EntityTypes.FARM.ordinal()))
						|| profileEnitiy
								.equals(String.valueOf(DynamicFeildMenuConfig.EntityTypes.CERTIFICATION.ordinal()))) {
					if (profileObject.containsKey(Farmer.class)) {
						f = (Farmer) profileObject.get(Farmer.class);
					}

				}
			}
		}

		return f;
	}

	private FarmerDynamicData processAuditCalculSym(FarmerDynamicData farmerDynamicData,
			Map<String, DynamicFieldConfig> fieldConfigMap, Map<String, List<FarmerDynamicFieldsValue>> fdMap,
			String auditedYear) {

		/*
		 * if (farmerDynamicData.getEntityId().equals("1")) { String prefs =
		 * utilService.findPrefernceByName("AUDIT_FIELD");
		 * List<CertificateStandard> ld=null; if (prefs != null) {
		 * if(farmerDynamicData.getActStatus()!=null &&
		 * farmerDynamicData.getActStatus()!=2){ ld =
		 * utilService.listCertificateStandardByCertificateCategoryId(7l);
		 * 
		 * 
		 * Farmer obj = (Farmer)
		 * getObjectWithEntity(prefs.split("#")[0].toString().trim(),
		 * farmerDynamicData.getEntityId(), farmerDynamicData.getReferenceId(),
		 * new HashMap<>());
		 * 
		 * try { setterMethod = setterMethod(obj.getClass(),
		 * prefs.split("#")[1].toString().trim()); } catch (Exception e1) {
		 * setterMethod = null; }
		 * 
		 * val = 0;
		 * 
		 * 
		 * updated = false; Map<String, CertificateStandard> ldMap =
		 * ld.stream().collect(Collectors.toMap(u -> u.getCode().split("_")[1],
		 * u -> u));
		 * 
		 * if (auditedYear!=null && !StringUtil.isEmpty(auditedYear)) { ldMap =
		 * ld.stream().filter(u ->
		 * u.getCode().contains(String.valueOf(auditedYear)))
		 * .collect(Collectors.toMap(u -> u.getCode().split("_")[1], u -> u)); }
		 * 
		 * ldMap.entrySet().stream().forEach(u -> { if (!updated &&
		 * u.getValue().getCriteria() != null &&
		 * !StringUtil.isEmpty(u.getValue().getCriteria())) { Integer result =
		 * validateAnswer(u.getValue().getCriteria(), fdMap, fieldConfigMap); if
		 * (result == 1) { try {
		 * 
		 * if (setterMethod!=null) { if
		 * (prefs.split("#")[2].toString().trim().equals("Integer")) {
		 * setterMethod.invoke(obj, Integer.valueOf(u.getKey())); } if
		 * (farmerDynamicData.getEntityId().equals("1")) { Method
		 * cersetterMethod = setterMethod(obj.getClass(),
		 * "certificateStandard"); cersetterMethod.invoke(obj, u.getValue()); }
		 * } } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } updated = true;
		 * farmerDynamicData.setTotalScore(Double.valueOf(u.getKey()));
		 * farmerDynamicData.setConversionStatus(String.valueOf(val));
		 * if(farmerDynamicData.getTxnType().equalsIgnoreCase("308")&&u.getKey()
		 * .equalsIgnoreCase("0")){ obj.setStatus(0); //obj.setStatusCode(2);
		 * 
		 * } this.update(obj); } } });
		 * 
		 * if (!updated && ldMap.entrySet().stream().anyMatch(u->
		 * StringUtil.isEmpty(u.getValue().getCriteria()))) { Entry<String,
		 * CertificateStandard> u = ldMap.entrySet().stream().filter(ss->
		 * StringUtil.isEmpty(ss.getValue().getCriteria())).findFirst().get();
		 * if (prefs.split("#")[2].toString().trim().equals("Integer")) { try {
		 * setterMethod.invoke(obj, Integer.valueOf(u.getKey())); if
		 * (farmerDynamicData.getEntityId().equals("1")) { Method
		 * cersetterMethod = setterMethod(obj.getClass(),
		 * "certificateStandard"); cersetterMethod.invoke(obj, u.getValue()); }
		 * } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } } updated = true;
		 * farmerDynamicData.setTotalScore(Double.valueOf(u.getKey()));
		 * farmerDynamicData.setConversionStatus(String.valueOf(val)); }
		 * 
		 * 
		 * 
		 * //} if(farmerDynamicData.getTotalScore()==2){
		 * farmerDynamicData.setActStatus(1); }
		 * 
		 * }} } else if (farmerDynamicData.getEntityId().equals("2")) { String
		 * prefs = utilService.findPrefernceByName("FARM_AUDIT_FIELD"); Farm f =
		 * findFarmByfarmId(Long.valueOf(farmerDynamicData.getReferenceId()));
		 * FarmerDynamicData fd =
		 * findFarmerDynamicDataByReferenceIdAndTxnType(f.getFarmer().getId());
		 * if(!ObjectUtil.isEmpty(fd)){ Map<String,
		 * List<FarmerDynamicFieldsValue>> fdFarmerMap =
		 * fd.getFarmerDynamicFieldsValues().stream()
		 * .collect(Collectors.groupingBy(FarmerDynamicFieldsValue::getFieldName
		 * )); fdMap.putAll(fdFarmerMap); } List<CertificateStandard> ld=null;
		 * if (prefs != null) { if(farmerDynamicData.getActStatus()!=null &&
		 * farmerDynamicData.getActStatus()!=2){ ld =
		 * utilService.listCertificateStandardByCertificateCategoryId(8l);
		 * }else{ ld =
		 * utilService.listCertificateStandardByCertificateCategoryId(10l); }
		 * Farm obj = (Farm)
		 * getObjectWithEntity(prefs.split("#")[0].toString().trim(),
		 * farmerDynamicData.getEntityId(), farmerDynamicData.getReferenceId(),
		 * new HashMap<>());
		 * 
		 * try { setterMethod = setterMethod(obj.getClass(),
		 * prefs.split("#")[1].toString().trim()); } catch (Exception e1) {
		 * setterMethod = null; }
		 * 
		 * val = 0;
		 * 
		 * 
		 * updated = false; Map<String, CertificateStandard> ldMap =
		 * ld.stream().collect(Collectors.toMap(u -> u.getCode().split("_")[1],
		 * u -> u));
		 * 
		 * if (auditedYear!=null && !StringUtil.isEmpty(auditedYear)) { ldMap =
		 * ld.stream().filter(u ->
		 * u.getCode().contains(String.valueOf(auditedYear)))
		 * .collect(Collectors.toMap(u -> u.getCode().split("_")[1], u -> u)); }
		 * 
		 * ldMap.entrySet().stream().forEach(u -> { if (!updated &&
		 * u.getValue().getCriteria() != null &&
		 * !StringUtil.isEmpty(u.getValue().getCriteria())) { Integer result =
		 * validateAnswer(u.getValue().getCriteria(), fdMap, fieldConfigMap); if
		 * (result == 1) { try {
		 * 
		 * if (setterMethod!=null) { if
		 * (prefs.split("#")[2].toString().trim().equals("Integer")) {
		 * setterMethod.invoke(obj, Integer.valueOf(u.getKey())); } if
		 * (farmerDynamicData.getEntityId().equals("1")) { Method
		 * cersetterMethod = setterMethod(obj.getClass(),
		 * "certificateStandard"); cersetterMethod.invoke(obj, u.getValue()); }
		 * } } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } updated = true;
		 * farmerDynamicData.setTotalScore(Double.valueOf(u.getKey()));
		 * farmerDynamicData.setConversionStatus(String.valueOf(val));
		 * 
		 * obj.setCertificateStandardLevel(Integer.valueOf(u.getKey()));
		 * if(u.getKey().equalsIgnoreCase("0")){ //obj.getFarmer().setStatus(0);
		 * //obj.getFarmer().setStatusCode(2); obj.setStatus(0);
		 * obj.getFarmer().setCertificateStandardLevel(0); }else
		 * if(u.getKey().equalsIgnoreCase("1")){ int
		 * size=obj.getFarmer().getFarms().stream().filter(fm->
		 * fm.getCertificateStandardLevel()==0).collect(Collectors.toList()).
		 * size(); //obj.setStatus(1); if(size>0){
		 * obj.getFarmer().setCertificateStandardLevel(0); }else{
		 * obj.getFarmer().setCertificateStandardLevel(1); } } else
		 * if(u.getKey().equalsIgnoreCase("2")){ int
		 * size=obj.getFarmer().getFarms().stream().filter(fm->
		 * fm.getCertificateStandardLevel()==0).collect(Collectors.toList()).
		 * size(); int
		 * sizeInPg=obj.getFarmer().getFarms().stream().filter(fm->fm.
		 * getCertificateStandardLevel()==1).collect(Collectors.toList()).size()
		 * ; if(sizeInPg>0){ obj.getFarmer().setCertificateStandardLevel(1);
		 * }else if(size>0){ obj.getFarmer().setCertificateStandardLevel(0);
		 * }else{ obj.getFarmer().setCertificateStandardLevel(2); } }
		 * obj.getFarmer().setRevisionNo(DateUtil.getRevisionNumber());
		 * obj.setRevisionNo(DateUtil.getRevisionNumber());
		 * this.update(obj.getFarmer()); this.update(obj);
		 * 
		 * } } });
		 * 
		 * if (!updated && ldMap.entrySet().stream().anyMatch(u->
		 * StringUtil.isEmpty(u.getValue().getCriteria()))) { Entry<String,
		 * CertificateStandard> u = ldMap.entrySet().stream().filter(ss->
		 * StringUtil.isEmpty(ss.getValue().getCriteria())).findFirst().get();
		 * if (prefs.split("#")[2].toString().trim().equals("Integer")) { try {
		 * setterMethod.invoke(obj, Integer.valueOf(u.getKey())); if
		 * (farmerDynamicData.getEntityId().equals("1")) { Method
		 * cersetterMethod = setterMethod(obj.getClass(),
		 * "certificateStandard"); cersetterMethod.invoke(obj, u.getValue()); }
		 * } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } } updated = true;
		 * farmerDynamicData.setTotalScore(Double.valueOf(u.getKey()));
		 * farmerDynamicData.setConversionStatus(String.valueOf(val)); }
		 * 
		 * 
		 * 
		 * //} if(farmerDynamicData.getTotalScore()==2){
		 * farmerDynamicData.setActStatus(1); }
		 * 
		 * } }
		 */
		return farmerDynamicData;
	}

	String formula;

	private Integer validateAnswer(String value, Map<String, List<FarmerDynamicFieldsValue>> fdMap,
			Map<String, DynamicFieldConfig> fieldConfigMap) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");

		List<String> fieldLiust = new ArrayList<>();
		Matcher p = Pattern.compile("\\{(.*?)\\}").matcher(value);
		Matcher p1 = Pattern.compile("\\[(.*?)\\]").matcher(value);
		formula = value;

		while (p.find())
			fieldLiust.add(p.group(1));

		while (p1.find())
			fieldLiust.add(p1.group(1));

		fieldLiust.stream().forEach(uu -> {

			if (uu.trim().equals("APLAN")) {
				Long cnt = fdMap.values().stream().flatMap(u -> u.stream()).collect(Collectors.toList()).stream()
						.filter(u -> u.getActionPlan() != null
								&& !StringUtil.isEmpty(u.getActionPlan().getFieldValue()))
						.collect(Collectors.counting());
				formula = formula.replaceAll("\\{" + uu + "\\}", String.valueOf(cnt));
			}
			if (uu.trim().contains("APLAN~")) {
				String grade = uu.split("~")[1].toString();
				Long cnt = fdMap.values().stream().flatMap(u -> u.stream()).collect(Collectors.toList()).stream()
						.filter(u -> u.getGrade() != null && u.getGrade().equals(grade)).collect(Collectors.counting());
				formula = formula.replaceAll("\\{" + uu + "\\}", String.valueOf(cnt));
			}

			if (uu.contains("#")) {
				String grade = uu.split("#")[0].toString();
				Integer score = Integer.valueOf(uu.split("#")[1].toString());
				String operat = "";
				if (uu.split("#").length >= 3) {
					operat = uu.split("#")[2].toString();
				}
				Long gradeCount = fieldConfigMap.values().stream()
						.filter(field -> field.getGrade() != null && field.getGrade().equals(grade))
						.collect(Collectors.counting());
				Long actulaVal = fdMap.values().stream().flatMap(ff -> ff.stream())
						.collect(Collectors.toList()).stream().filter(field -> field.getGrade() != null
								&& field.getGrade().equals(grade) && field.getScore() == score)
						.collect(Collectors.counting());
				if (operat != null && operat.equals("%")) {
					if (actulaVal == 0) {
						formula = formula.replaceAll("\\[" + uu + "\\]", "0");
					} else {
						formula = formula.replaceAll("\\[" + uu + "\\]",
								String.valueOf((gradeCount / actulaVal) * 100));
					}

				} else if (operat != null && operat.equals(">")) {
					{
						formula = formula.replaceAll("\\[" + uu + "\\]", String.valueOf(actulaVal));
					}
					// formula = formula.replaceAll("\\[" + uu +
					// "\\]",String.valueOf(actulaVal) );
				}
			} else {
				String valu = fdMap.containsKey(uu) ? fdMap.get(uu).get(0).getFieldValue() : "";
				if (StringUtil.isLong(valu) || StringUtil.isInteger(valu) || StringUtil.isDouble(valu)) {
					formula = formula.replaceAll("\\{" + uu + "\\}", valu);
				} else {
					formula = formula.replaceAll("\\{" + uu + "\\}", "\"" + valu + "\"");
				}
			}
		});

		try {
			if ((Boolean) engine.eval(formula)) {
				return 1;
			}
		} catch (ScriptException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public void addImageInfo(ImageInfo imageInfo) {
		farmerDAO.save(imageInfo);

	}

	@Override
	public void updateImageInfo(ImageInfo imageInfo) {
		farmerDAO.update(imageInfo);
	}

	private FarmerDynamicData findFarmerDynamicDataByReferenceIdAndTxnType(long id) {
		// TODO Auto-generated method stub
		return farmerDAO.findFarmerDynamicDataByReferenceIdAndTxnType(id);
	}

	Integer count = 1;

	public void processProfileUpdates(Map<String, Object> profileUpdateFields,
			Map<String, DynamicFieldConfig> fieldConfigMap, FarmerDynamicData farmerDynamicData) {
		Map<Class, Object> profileObject = new HashMap<>();
		profileUpdateFields.entrySet().stream().forEach(u -> {
			DynamicFieldConfig dm = fieldConfigMap.get(u.getKey());
			if (dm.getComponentType().equals("15")) {
				count = 1;
				String area = ((String) u.getValue()).split("~")[1].toString();
				String coord = ((String) u.getValue()).split("~")[0].toString();
				if (farmerDynamicData.getEntityId().equals("2") || farmerDynamicData.getEntityId().equals("4")) {

					Set<Coordinates> fcSet = new HashSet<>();
					Arrays.asList(coord.split("|")).stream().forEach(cp -> {
						Coordinates fcc = new Coordinates();
						fcc.setLatitude(cp.split(",")[0].trim().toString());
						fcc.setLongitude(cp.split(",")[1].trim().toString());
						// fcc.setOrderNo(count);
						count++;
						fcSet.add(fcc);
					});
					// fm.getFarmDetailedInfo().setTotalLandHolding(area);
					// fm.setCoordinates(fcSet);
					/*
					 * fm.getCoordinatesMap().stream().forEach(fc->{
					 * fc.setStatus(CoordinatesMap.Status.INACTIVE.ordinal());
					 * });
					 */
					CoordinatesMap co = new CoordinatesMap();
					co.setFarmCoordinates(fcSet);
					co.setAgentId(farmerDynamicData.getCreatedUser());
					// co.setArea(fm.getFarmDetailedInfo().getTotalLandHolding());
					// co.setDate(fm.getPhotoCapturingTime());

					// co.setMidLatitude(fm.getLatitude());
					// co.setMidLongitude(fm.getLongitude());
					co.setStatus(CoordinatesMap.Status.ACTIVE.ordinal());

					/*
					 * if (fm.getCoordinatesMap() != null &&
					 * !ObjectUtil.isListEmpty(fm.getCoordinatesMap())) {
					 * fm.getCoordinatesMap().stream().forEach(c -> {
					 * co.setStatus(CoordinatesMap.Status.INACTIVE.ordinal());
					 * }); fm.getCoordinatesMap().add(co); } else {
					 * Set<CoordinatesMap> coMap = new LinkedHashSet<>();
					 * coMap.add(co); fm.setCoordinatesMap(coMap);
					 * 
					 * } fm.setActiveCoordinates(co);
					 */

					// editFarm(fm);
				}
			} else if (dm.getComponentType().equals("12") && !StringUtil.isEmpty(dm.getProfileField())) {
				String profileField = dm.getProfileField();
				String entity = profileField.split("#")[0].trim().toString();
				String type = profileField.split("#")[1].trim().toString();
				String field = profileField.split("#")[2].trim().toString();
				if (type.equals("byte[]") && (field.contains("image") || field.contains("photo"))) {
					Object obj = getObjectWithEntity(entity, farmerDynamicData.getEntityId(),
							farmerDynamicData.getReferenceId(), profileObject);
					if (obj != null && field.contains("image") && obj instanceof Farmer) {
						byte[] photoContent = (byte[]) u.getValue();

						Farmer existingFarmer = (Farmer) obj;

						ImageInfo imageInfo = null;
						if (!StringUtil.isEmpty(photoContent) && photoContent.length > 0) {
						}
					}
				}

			} else {
				String profileField = dm.getProfileField();
				String entity = profileField.split("#")[0].trim().toString();
				String type = profileField.split("#")[1].trim().toString();
				String field = profileField.split("#")[2].trim().toString();
				if (!StringUtil.isEmpty(u.getValue())) {
					Object obj = getObjectWithEntity(entity, farmerDynamicData.getEntityId(),
							farmerDynamicData.getReferenceId(), profileObject);
					if (obj != null) {
						Method setterMethod;
						try {
							setterMethod = setterMethod(obj.getClass(), field);
							if (type.equals("Integer")) {
								setterMethod.invoke(obj, Integer.valueOf(u.getValue().toString()));
							} else if (type.equals("Decimal")) {
								setterMethod.invoke(obj, Double.valueOf(u.getValue().toString()));
							} else {
								setterMethod.invoke(obj, u.getValue().toString());
							}
							// String revisionNo = null;
							try {
								Field dField = obj.getClass().getDeclaredField("revisionNo");
								if (dField != null) {
									setterMethod = setterMethod(obj.getClass(), "revisionNo");
									setterMethod.invoke(obj, DateUtil.getRevisionNumber());
								}
							} catch (Exception e) {

							}
						} catch (Exception e) {
							System.out.println("error in ocne qun");
						}

					}

				}
			}

		});
		profileObject.entrySet().forEach(u -> {
			farmerDAO.saveOrUpdate(u.getValue());
		});

	}

	@SuppressWarnings("unchecked")
	private Method setterMethod(Class entityClass, String propertyName) throws Exception {

		BeanInfo info = Introspector.getBeanInfo(entityClass);
		PropertyDescriptor[] props = info.getPropertyDescriptors();
		for (PropertyDescriptor pd : props) {
			if (pd.getName().equals(propertyName))
				return pd.getWriteMethod();
		}
		return null;
	}

	@Override
	public List<FarmerDynamicFieldsValue> listFarmerDynmaicFieldsByRefId(String refId, String txnType) {
		return farmerDAO.listFarmerDynmaicFieldsByRefId(refId, txnType);
	}

	@Override
	public List<DynamicFeildMenuConfig> findDynamicMenusByMType(String txnType) {
		return farmerDAO.findDynamicMenusByMType(txnType);
	}

	@Override
	public List<FarmerDynamicFieldsValue> listFarmerDynamicFieldsValuePhotoByRefTxnType(String refId, String txnType) {
		// TODO Auto-generated method stub
		return farmerDAO.listFarmerDynamicFieldsValuePhotoByRefTxnType(refId, txnType);
	}

	@Override
	public void updateDynamicFarmerFieldComponentType() {
		farmerDAO.updateDynamicFarmerFieldComponentType();
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
		farmerDAO.update(obj);
	}

	@Override
	public Object[] findFarmerInfoById(Long id) {
		// TODO Auto-generated method stub
		return farmerDAO.findFarmerInfoById(id);
	}

	@Override
	public List<DynamicFeildMenuConfig> listDynamicMenus() {
		return farmerDAO.listDynamicMenus();
	}

	@Override
	public Long findActiveContractFarmersLatestRevisionNoByAgentAndSeason(long agentId, String seasonCode) {
		return farmerDAO.findActiveContractFarmersLatestRevisionNoByAgentAndSeason(agentId, seasonCode);
	}

	@Override
	public Long listFarmFieldsByFarmerIdByAgentIdAndSeasonRevisionNo(long id) {
		return farmerDAO.listFarmFieldsByFarmerIdByAgentIdAndSeasonRevisionNo(id);
	}

	@Override
	public List<DynamicFeildMenuConfig> listDynamicMenusByRevNo(String revisionNo, String branchId, String tenantId) {
		return farmerDAO.listDynamicMenusByRevNo(revisionNo, branchId, tenantId);
	}

	@Override
	public List<Object[]> listFDVSForFolloUp(String agentId, String revisionNo) {
		return farmerDAO.listFDVSForFolloUp(agentId, revisionNo);

	}

	@Override
	public List<DynamicMenuFieldMap> listDynamisMenubyscoreType() {
		// TODO Auto-generated method stub
		return farmerDAO.listDynamisMenubyscoreType();
	}

	@Override
	public List<Object[]> listActiveContractFarmersAccountByAgentAndSeason(long agentId, Date revisionDate) {

		List<Object[]> accountList = new ArrayList<Object[]>();
		SeasonMaster season = findCurrentSeason();
		if (!ObjectUtil.isEmpty(season))
			accountList = farmerDAO.listActiveContractFarmersAccountByAgentAndSeason(agentId, season.getCode(),
					revisionDate);
		return accountList;
	}

	private SeasonMaster findCurrentSeason() {

		ESESystem preference = utilDAO.findPrefernceById(ESESystem.SYSTEM_SWITCH);
		String currentSeasonCode = preference.getPreferences().get(ESESystem.CURRENT_SEASON_CODE);
		if (!StringUtil.isEmpty(currentSeasonCode)) {
			SeasonMaster currentSeason = utilDAO.findSeasonBySeasonCode(currentSeasonCode);
			return currentSeason;
		}
		return null;
	}

	@Override
	public List<Object[]> listActiveContractFarmersFieldsBySeasonRevNoAndSamithiWithGramp(long id,
			String currentSeasonCode, Long revisionNo, List<String> branch) {
		return farmerDAO.listActiveContractFarmersFieldsBySeasonRevNoAndSamithiWithGramp(id, currentSeasonCode,
				revisionNo, branch);
	}

	public List<Object[]> listfarmerDynamicData(List<Long> fidLi) {
		return farmerDAO.listfarmerDynamicData(fidLi);
	}

	public List<Object[]> findCountOfDynamicDataByFarmerId(List<Long> fidLi, String season) {
		return farmerDAO.findCountOfDynamicDataByFarmerId(fidLi, season);
	}

	public List<java.lang.Object[]> listFarmFieldsByFarmerIdByAgentIdAndSeason(long id, Long revisionNo) {
		return farmerDAO.listFarmFieldsByFarmerIdByAgentIdAndSeason(id, revisionNo);
	}

	public List<Object[]> listDynamicFieldsCodeAndName() {
		return farmerDAO.listDynamicFieldsCodeAndName();
	}

	public List<Object[]> listFarmerDynamicFieldsValuesByFarmIdList(List<String> farmIdList,
			List<String> selectedDynamicFieldCodeList) {
		return farmerDAO.listFarmerDynamicFieldsValuesByFarmIdList(farmIdList, selectedDynamicFieldCodeList);
	}

	@Override
	public List<Object[]> listFarmsLastInspectionDate() {
		return farmerDAO.listFarmsLastInspectionDate();
	}

	@Override
	public FarmerDynamicData findFarmerDynamicDataByTxnUniquId(String txnUniqueId) {
		return farmerDAO.findFarmerDynamicDataByTxnUniquId(txnUniqueId);
	}

	@Override
	public List<Farmer> listFarmerByFarmerIdByIdList(List<String> id) {
		return farmerDAO.listFarmerByFarmerIdByIdList(id);
	}

	@Override
	public FarmerDynamicData findFarmerDynamicDataBySeason(String txnType, String id, String season) {
		// TODO Auto-generated method stub
		return farmerDAO.findFarmerDynamicDataBySeason(txnType, id, season);

	}

	@Override
	public void deleteObject(Object object) {
		farmerDAO.delete(object);
	}

	@Override
	public Integer findFarmersCountByStatus(Date sDate, Date eDate, Long long1) {

		// TODO Auto-generated method stub
		return farmerDAO.findFarmersCountByStatus(sDate, eDate, long1);
	}

	@Override
	public Integer findFarmersCountByStatusAndSeason(String currentSeason) {
		// TODO Auto-generated method stub
		return farmerDAO.findFarmersCountByStatusAndSeason(currentSeason);
	}

	@Override
	public Integer findFarmerCountByMonth(Date sDate, Date eDate) {

		return farmerDAO.findFarmerCountByMonth(sDate, eDate);
	}

	@Override
	public String findFarmTotalProposedAreaCount() {
		// TODO Auto-generated method stub
		return farmerDAO.findFarmTotalProposedAreaCount();
	}

	@Override
	public String findFarmTotalLandAreaCount(Date sDate, Date eDate, Long long1) {

		// TODO Auto-generated method stub
		return farmerDAO.findFarmTotalLandAreaCount(sDate, eDate, long1);
	}

	@Override
	public Integer findFarmCountByMonth(Date sDate, Date eDate) {

		return farmerDAO.findFarmCountByMonth(sDate, eDate);
	}

	@Override
	public Integer findFarmsCount(Date sDate, Date eDate, Long long1) {

		return farmerDAO.findFarmsCount(sDate, eDate, long1);
	}

	@Override
	public Integer findCropCount() {

		return farmerDAO.findCropCount();
	}

	@Override
	public List<Object> listFarmerCountByGroup() {

		return farmerDAO.listFarmerCountByGroup();
	}

	@Override
	public List<Object> listFarmerCountByBranch() {

		return farmerDAO.listFarmerCountByBranch();
	}

	@Override
	public List<Object> listTotalFarmAcreByVillage() {
		// TODO Auto-generated method stub
		return farmerDAO.listTotalFarmAcreByVillage();
	}

	@Override
	public List<Object> listTotalFarmAcreByBranch() {
		return farmerDAO.listTotalFarmAcreByBranch();
	}

	@Override
	public List<String> findFarmerIdsByfarmCode(List<String> farmCodes) {
		// TODO Auto-generated method stub
		return farmerDAO.findFarmerIdsByfarmCode(farmCodes);
	}

	public HarvestSeason findHarvestSeasonByCode(String seasonCode) {

		return farmerDAO.findHarvestSeasonByCode(seasonCode);
	}

	@Override
	public List<Object[]> listSeasonCodeAndName() {

		return farmerDAO.listSeasonCodeAndName();
	}

	@Override
	public void addBankInformation(BankInfo bankInformation) {

		farmerDAO.save(bankInformation);

	}

	public long findFarmerCountByFPO(Long val) {

		return farmerDAO.findFarmerCountByFPO(val);
	}

	@Override
	public List<Object[]> farmersByBranch() {
		// TODO Auto-generated method stub
		return farmerDAO.farmersByBranch();
	}

	@Override
	public List<Object[]> farmersByCountry(String empty) {
		// TODO Auto-generated method stub
		return farmerDAO.farmersByCountry(empty);
	}

	@Override
	public List<Object[]> farmersByState(String empty) {
		// TODO Auto-generated method stub
		return farmerDAO.farmersByState(empty);
	}

	@Override
	public List<Object[]> farmersByLocality(String empty) {
		// TODO Auto-generated method stub
		return farmerDAO.farmersByLocality(empty);
	}

	@Override
	public List<Object[]> farmersByMunicipality(String empty) {
		// TODO Auto-generated method stub
		return farmerDAO.farmersByMunicipality(empty);
	}

	@Override
	public List<Object[]> farmersByGramPanchayat(String selectedBranch) {
		// TODO Auto-generated method stub
		return farmerDAO.farmersByGramPanchayat(selectedBranch);
	}

	@Override
	public List<Object[]> farmersByVillageWithGramPanchayat(String selectedBranch) {
		// TODO Auto-generated method stub
		return farmerDAO.farmersByVillageWithGramPanchayat(selectedBranch);
	}

	@Override
	public List<Object[]> farmersByVillageWithOutGramPanchayat(String empty) {
		// TODO Auto-generated method stub
		return farmerDAO.farmersByVillageWithOutGramPanchayat(empty);
	}

	@Override
	public List<Object[]> farmerDetailsByVillage(String empty) {
		// TODO Auto-generated method stub
		return farmerDAO.farmerDetailsByVillage(empty);
	}

	@Override
	public void updateFarmerStatusByFarmerId(String farmerId) {
		// TODO Auto-generated method stub
		farmerDAO.updateFarmerStatusByFarmerId(farmerId);
	}

	@Override
	public List<DynamicSectionConfig> listDynamicSections() {
		// TODO Auto-generated method stub
		return farmerDAO.listDynamicSections();
	}

	@Override
	public void editFarmer(Farmer existing) {
		// existing.setRevisionNo(DateUtil.getRevisionNumber());
		farmerDAO.update(existing);
	}

	@Override
	public List<Object[]> estimatedYield(String codeForCropChart) {
		// TODO Auto-generated method stub
		return farmerDAO.estimatedYield(codeForCropChart);
	}

	@Override
	public List<Object[]> actualYield(String codeForCropChart) {
		// TODO Auto-generated method stub
		return farmerDAO.actualYield(codeForCropChart);
	}

	@Override
	public List<Object[]> populateFarmerLocationCropChart(String codeForCropChart) {
		// TODO Auto-generated method stub
		return farmerDAO.populateFarmerLocationCropChart(codeForCropChart);
	}

	@Override
	public List<Object[]> getFarmDetailsAndProposedPlantingArea(String locationLevel, String selectedBranch,
			String gramPanchayatEnable) {
		// TODO Auto-generated method stub
		return farmerDAO.getFarmDetailsAndProposedPlantingArea(locationLevel, selectedBranch, gramPanchayatEnable);
	}

	@Override
	public List<Object[]> getFarmDetailsAndCultivationArea(String locationLevel, String selectedBranch,
			String gramPanchayatEnable) {
		// TODO Auto-generated method stub
		return farmerDAO.getFarmDetailsAndCultivationArea(locationLevel, selectedBranch, gramPanchayatEnable);
	}

	@Override
	public Double findTotalCottonAreaCount() {
		// TODO Auto-generated method stub
		return farmerDAO.findTotalCottonAreaCount();
	}

	@Override
	public Double findTotalCottonAreaCountByMonth(Date firstDateOfMonth, Date date) {
		// TODO Auto-generated method stub
		return farmerDAO.findTotalCottonAreaCountByMonth(firstDateOfMonth, date);
	}

	@Override
	public List<Object[]> populateFarmerCropCountChartByGroup(String codeForCropChart, String selectedBranch) {
		// TODO Auto-generated method stub
		return farmerDAO.populateFarmerCropCountChartByGroup(codeForCropChart, selectedBranch);
	}

	@Override
	public List<DynamicFieldConfig> listDynamicFields() {
		return farmerDAO.listDynamicFields();
	}

	@Override
	public void editFarmerStatus(long id, int statusCode, String statusMsg) {
		farmerDAO.editFarmerStatus(id, statusCode, statusMsg);

	}

	@Override
	public void processCustomisedFormula(FarmerDynamicData farmerDynamicData,
			Map<String, DynamicFieldConfig> fieldConfigMap) {
		farmerDAO.processCustomisedFormula(farmerDynamicData, fieldConfigMap);

	}

	@Override
	public Farmer findFarmerByMsgNo(String msgNo) {
		// TODO Auto-generated method stub
		return farmerDAO.findFarmerByMsgNo(msgNo);
	}

	@Override
	public void updateFarmerImageInfo(long id, long imageInfoId) {

		farmerDAO.updateFarmerImageInfo(id, imageInfoId);

	}

	public void removeUnmappedFarmCropObject() {

		farmerDAO.removeUnmappedFarmCropObject();

	}

	public List<FarmerField> listFarmerFields() {
		return farmerDAO.listFarmerFields();
	}

	public List<Farmer> listFarmer() {

		return farmerDAO.listFarmer();
	}

	@Override
	public List<Object[]> listFarmFieldsByFarmerId(long id) {
		return farmerDAO.listFarmFieldsByFarmerId(id);
	}

	@Override
	public List<Farmer> listFarmersByExporter(Long expId) {
		return farmerDAO.listFarmersByExporter(expId);
	}

	@Override
	public Farm findFarmById(Long id) {
		// TODO Auto-generated method stub
		return farmerDAO.findFarmById(id);
	}

	public void editFarm(Farm farm) {

		farmerDAO.update(farm);
	}

	@Override
	public Farm findFarmbyFarmCode(String farmCode) {
		// TODO Auto-generated method stub
		return farmerDAO.findFarmbyFarmCode(farmCode);
	}

	@Override
	public Object findObjectById(String qryString, Object[] value) {
		return farmerDAO.findObjectById(qryString, value);
	}

	@Override
	public Object listObjectById(String qryString, Object[] value) {
		return farmerDAO.listObjectById(qryString, value);
	}

	@Override
	public void runSQLQuery(String string, Object[] objects) {
		farmerDAO.runSQLQuery(string, objects);

	}

	public List<Object[]> getharvestdateandquantity(int i, List<Long> fcids) {
		return farmerDAO.getharvestdateandquantity(i, fcids);
	}

	public List<Object[]> getlistofkenyacode() {
		return farmerDAO.getlistofkenyacode();
	}

	// public List<Recalling> getlistofkenyacodeFromRecalling(){
	// return farmerDAO.getlistofkenyacodeFromRecalling();
	// }

	public List<Object[]> getlistofkenyacodeFromRecalling() {
		return farmerDAO.getlistofkenyacodeFromRecalling();
	}

	public void updateshipmentdetail(String receiptNumber) {
		farmerDAO.updateshipmentdetail(receiptNumber);
	}

	public void removerecallingdetail(Long id, Long id2) {
		farmerDAO.removerecallingdetail(id, id2);
	}

	@Override
	public List<Object[]> executeChartQuery(String chartQuery, int dateFilter, String dateField, String sDate,
			String eDate, Long id, String exporterField, String groupField, String orderField,int i) {
		// TODO Auto-generated method stub
		return farmerDAO.executeChartQuery(chartQuery, dateFilter, dateField, sDate, eDate, id, exporterField,
				groupField, orderField,i);
	}

	@Override
	public List<Dashboard> listDashboardDataByChartDivId() {
		// TODO Auto-generated method stub
		return farmerDAO.listDashboardDataByChartDivId();
	}

	@Override
	public List<ProcurementVariety> findProcurementVarietyByIds(List<Long> convertStringList) {
		return farmerDAO.findProcurementVarietyByIds(convertStringList);
	}
	
	@Override
	public Date findMaximunDateFromSprayingByFarmCropsId(Long farmcropsId) {
		// TODO Auto-generated method stub
		return farmerDAO.findMaximunDateFromSprayingByFarmCropsId(farmcropsId);
	}

	@Override
	public List<Object[]> findListOfScoutingByplantingIds(List<Long> fcids) {
		// TODO Auto-generated method stub
		return farmerDAO.findListOfScoutingByplantingIds(fcids);
	}

	@Override
	public List<Object[]> getValueListByQuery(String query, Object[] param, String branchId) {
		// TODO Auto-generated method stub
		return farmerDAO.getValueListByQuery(query,param,branchId);
	}
	
	@Override
	public List<Object[]> getCatalogueAuditValueByQuery(Long param) {
		// TODO Auto-generated method stub
		return farmerDAO.getCatalogueAuditValueByQuery(param);
	}
	
	@Override
	public List<Object[]> findProcurementProductDetailById(Long param,String crop) {
		// TODO Auto-generated method stub
		return farmerDAO.findProcurementProductDetailById(param,crop);
	}
	
	

}
