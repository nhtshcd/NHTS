package com.sourcetrace.eses.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.sourcetrace.eses.entity.Dashboard;
import com.sourcetrace.eses.entity.DynamicConstants;
import com.sourcetrace.eses.entity.DynamicFeildMenuConfig;
import com.sourcetrace.eses.entity.DynamicFieldConfig;
import com.sourcetrace.eses.entity.DynamicImageData;
import com.sourcetrace.eses.entity.DynamicMenuFieldMap;
import com.sourcetrace.eses.entity.DynamicSectionConfig;
import com.sourcetrace.eses.entity.ESEAccount;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.ESETxnStatus;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.FarmerDynamicData;
import com.sourcetrace.eses.entity.FarmerDynamicFieldsValue;
import com.sourcetrace.eses.entity.FarmerField;
import com.sourcetrace.eses.entity.HarvestSeason;
import com.sourcetrace.eses.entity.ProcurementVariety;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

@Repository
@Lazy
@Transactional
public class FarmerDAO extends ESEDAO implements IFarmerDAO {

	protected static String SELECT = "-1";
	protected static String formula = "";
	@Autowired
	IUtilDAO utilDAO;

	@Autowired
	public FarmerDAO(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	@Override
	public Farmer findFarmerByFarmerId(String farmerId) {

		Object[] values = { farmerId, com.sourcetrace.eses.entity.ESETxnStatus.SUCCESS.ordinal() };
		Farmer farmer = (Farmer) find(
				"FROM Farmer fr left join fetch fr.farms WHERE  fr.farmerId = ? AND fr.statusCode = ? ", values);
		return farmer;
	}

	@Override
	public Farmer findFarmerByFarmerId(String farmerId, String tenantId) {

		Session session = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		Query query = session.createQuery(
				"FROM Farmer fr left join fetch fr.village v left join fetch fr.samithi s WHERE  fr.farmerId = :farmerId AND fr.statusCode = :statusCode");
		query.setParameter("farmerId", farmerId);
		query.setParameter("statusCode", com.sourcetrace.eses.entity.ESETxnStatus.SUCCESS.ordinal());

		List<Farmer> farmerList = query.list();

		Farmer farmer = null;
		if (farmerList.size() > 0) {
			farmer = (Farmer) farmerList.get(0);
		}

		session.flush();
		session.close();
		return farmer;
	}

	@Override
	public DynamicImageData findDynamicImageDataById(Long id) {
		// return (DynamicImageData) find("FROM DynamicImageData dd WHERE
		// dd.id=?",id);
		Session session = getSessionFactory().openSession();
		Query query = session
				.createQuery("FROM DynamicImageData dd JOIN FETCH dd.farmerDynamicFieldsValue WHERE dd.id=:id");
		query.setParameter("id", id);
		List<DynamicImageData> list = query.list();
		session.flush();
		session.close();
		if (list.size() > 0) {
			DynamicImageData dynamicImageData = list.get(0);
			return dynamicImageData;
		} else {
			return null;
		}
	}

	@Override
	public List<HarvestSeason> listHarvestSeasons() {

		return list("FROM HarvestSeason");
	}

	@Override
	public List<DynamicSectionConfig> findDynamicFieldsBySectionId(String sectionId) {
		// TODO Auto-generated method stub
		return list("FROM DynamicSectionConfig dsc where dsc.tableId=? order by dsc.id asc", sectionId);
	}

	@Override
	public List<DynamicConstants> listDynamicConstants() {
		return list("FROM DynamicConstants dsc ORDER BY dsc.code ASC");
	}

	public Farmer findFarmerById(Long id) {

		return (Farmer) find("FROM Farmer f left join fetch f.farms fr  WHERE f.id = ?", id);
	}

	@Override
	public List<FarmerDynamicFieldsValue> listFarmerDynmaicFieldsByFarmerId(Long farmerId, String txnType) {
		Object[] values = { farmerId, txnType };
		return list(
				"select fdfv FROM FarmerDynamicFieldsValue fdfv LEFT JOIN FETCH fdfv.dymamicImageData di where fdfv.farmer.id=? AND fdfv.txnType=?",
				values);
	}

	@Override
	public FarmerDynamicData findFarmerDynamicData(String id) {

		return (FarmerDynamicData) find("FROM FarmerDynamicData fdfv where fdfv.id=?", Long.valueOf(id));
	}

	@Override
	public FarmerDynamicData findFarmerDynamicData(String txnType, String referenceId) {

		return (FarmerDynamicData) find(
				"FROM FarmerDynamicData fdfv  join fetch  fdfv.farmerDynamicFieldsValues  where fdfv.txnType=? and fdfv.referenceId=?",
				new Object[] { txnType, referenceId });
	}

	@Override
	public List<Object[]> listValuesbyQuery(String methodName, Object[] parameter, String branchId) {
		// TODO Auto-generated method stub
		String queryString = null;
		methodNameQ = methodName;
		Session session = getSessionFactory().openSession();

		SQLQuery query = session.createSQLQuery(methodNameQ);
		if (parameter != null && parameter.length > 0) {
			i = 1;
			Arrays.asList(parameter).stream().forEach(u -> {
				if (methodNameQ.contains("param" + i)) {
					if (u != null && u.toString().contains(",")) {
						query.setParameterList("param" + i, Arrays.asList(u.toString().split(",")));
					} else {
						query.setParameter("param" + i, u);
					}
					i++;
				}
			});
		}
		List<Object[]> result = query.list();
		session.flush();
		session.close();
		return result;

	}

	int i = 1;
	String methodNameQ = "";

	@Override
	public String getValueByQuery(String methodName, Object[] parameter, String branchId) {
		methodNameQ = methodName;
		Session session = getSessionFactory().openSession();
		if (branchId == null) {
			methodNameQ = methodName.replaceAll("<.*?>", "");

		} else {
			methodNameQ = methodNameQ.replaceAll("<", "").replaceAll(">", "").replaceAll("branchId",
					"'" + branchId + "'");
		}
		Query query = session.createSQLQuery(methodNameQ);
		if (parameter != null && parameter.length > 0) {
			i = 1;
			Arrays.asList(parameter).stream().forEach(u -> {
				if (methodNameQ.contains("param" + i)) {
					if (u != null && u.toString().contains(",")) {
						query.setParameterList("param" + i, Arrays.asList(u.toString().split(",")));
					} else {
						query.setParameter("param" + i, u);
					}
					i++;
				}
			});
		}
		List ls = query.list();
		session.flush();
		session.close();
		if (ls != null && ls.size() > 0) {
			return String.valueOf(ls.get(0));
		} else {
			return "";
		}

	}

	@Override
	public List<Object[]> listfarmingseasonlist() {

		return list("SELECT hs.code,hs.name FROM HarvestSeason hs");
	}

	@Override
	public List<byte[]> getImageByQuery(String methodName, Object[] parameter, String branchId) {
		List<byte[]> a = null;
		methodNameQ = methodName;
		Session session = getSessionFactory().openSession();
		if (branchId == null) {
			methodNameQ = methodName.replaceAll("<.*?>", "");

		} else {
			methodNameQ = methodNameQ.replaceAll("<", "").replaceAll(">", "").replaceAll("branchId",
					"'" + branchId + "'");
		}
		Query query = session.createSQLQuery(methodNameQ);
		if (parameter != null && parameter.length > 0) {
			i = 1;
			Arrays.asList(parameter).stream().forEach(u -> {
				if (methodNameQ.contains("param" + i)) {
					if (u != null && u.toString().contains(",")) {
						query.setParameterList("param" + i, Arrays.asList(u.toString().split(",")));
					} else {
						query.setParameter("param" + i, u);
					}
					i++;
				}
			});
		}
		List ls = query.list();
		session.flush();
		session.close();
		if (ls != null && ls.size() > 0) {
			return (List<byte[]>) (ls);
		} else {
			return a;
		}
	}

	@Override
	public List<Object[]> listColdStorageNameDynamic() {

		// TODO Auto-generated method stub
		Object[] values = { FarmCatalogue.ACTIVE, Integer.valueOf("126") };
		return list(
				"Select fc.code,fc.name FROM FarmCatalogue fc where fc.status=? and fc.typez=? order by fc.name asc",
				values);
	}

	@Override
	public List<Object[]> listFarmerIDAndName() {
		return list("SELECT DISTINCT f.id,f.farmerId,f.firstName,f.lastName from Farmer f where f.status=1");
	}

	@Override
	public List<Object[]> listFarmIDAndName() {
		return list("SELECT DISTINCT f.id,f.farmCode,f.farmName from Farm f");
	}

	public List<Object[]> listValueByFieldName(String field, String branchId) {
		Session session = getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(FarmerDynamicFieldsValue.class);
		criteria.createAlias("farmerDynamicData", "fd", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("fd.branch", branchId));
		criteria.add(Restrictions.eq("fieldName", field));
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("id"));
		pList.add(Projections.property("fieldValue"));
		pList.add(Projections.property("farmerDynamicData.id"));
		criteria.setProjection(pList);
		List<Object[]> list = criteria.list();
		session.flush();
		session.close();
		return list;
	}

	@Override
	public List<DynamicFeildMenuConfig> findDynamicMenusByType(String txnTypez, String branchId) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(
				"select distinct dsc FROM DynamicFeildMenuConfig dsc join fetch dsc.dynamicSectionConfigs dss left join fetch dss.section ds left join fetch ds.languagePreferences join fetch dsc.dynamicFieldConfigs  dc  join fetch dc.field ff   left join fetch ff.languagePreferences join fetch ff.dynamicSectionConfig sec join fetch dc.field WHERE dsc.txnType = :txnType and (dsc.branchId is null or dsc.branchId =''  or dsc.branchId like :branchId) and  (dc.branchId is null or dc.branchId ='' or dc.branchId like :branchId) and  (dss.branchId is null or dss.branchId ='' or dss.branchId like :branchId) ORDER BY dsc.order ASC");
		query.setParameter("branchId", "%" + branchId + "%");
		query.setParameter("txnType", txnTypez);

		List<DynamicFeildMenuConfig> result = query.list();
		session.flush();
		session.close();
		return result;
	}

	@Override
	public List<DynamicFeildMenuConfig> findDynamicMenusByType(String type) {

		// return list("FROM DynamicFeildMenuConfig dsc WHERE dsc.txnType = ?
		// ORDER BY dsc.order ASC", type);
		return list(
				"select distinct dsc FROM DynamicFeildMenuConfig dsc join fetch dsc.dynamicSectionConfigs dss left join fetch dss.section ds left join fetch ds.languagePreferences join fetch dsc.dynamicFieldConfigs  dc  join fetch dc.field ff   left join fetch ff.languagePreferences join fetch ff.dynamicSectionConfig sec join fetch dc.field WHERE dsc.txnType = ? ORDER BY dsc.order ASC",
				type);
	}

	@Override
	public List<DynamicFeildMenuConfig> findDynamicMenusByTypeForOCP(String txnTypez, String branchId) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		String queryString1 = "select distinct dsc FROM DynamicFeildMenuConfig dsc join fetch dsc.dynamicSectionConfigs dss left join fetch dss.section ds left join fetch ds.languagePreferences join fetch dsc.dynamicFieldConfigs  dc  join fetch dc.field ff   left join fetch ff.languagePreferences join fetch ff.dynamicSectionConfig sec join fetch dc.field WHERE dsc.txnType = :txnType and (dsc.branchId is null or dsc.branchId =''  or dsc.branchId like :branchId) and  (dc.branchId is null or dc.branchId ='' or dc.branchId like :branchId) and  (dss.branchId is null or dss.branchId ='' or dss.branchId like :branchId) ORDER BY dsc.order ASC";
		String queryString2 = "select distinct dsc FROM DynamicFeildMenuConfig dsc join fetch dsc.dynamicSectionConfigs dss left join fetch dss.section ds left join fetch ds.languagePreferences join fetch dsc.dynamicFieldConfigs  dc  join fetch dc.field ff   left join fetch ff.languagePreferences join fetch ff.dynamicSectionConfig sec join fetch dc.field WHERE dsc.txnType = :txnType ORDER BY dsc.order ASC";
		Query query = null;

		if (!StringUtil.isEmpty(branchId)) {
			query = session.createQuery(queryString1);
			query.setParameter("branchId", "%" + branchId + "%");
		} else {
			query = session.createQuery(queryString2);
		}
		query.setParameter("txnType", txnTypez);

		List<DynamicFeildMenuConfig> result = query.list();
		session.flush();
		session.close();
		return result;
	}

	@Override
	public FarmerDynamicData processCustomisedFormula(FarmerDynamicData farmerDynamicData,
			Map<String, DynamicFieldConfig> fieldConfigMap, Map<String, List<FarmerDynamicFieldsValue>> fdMap) {

		List<FarmerDynamicFieldsValue> resultSet = new ArrayList<>();
		List<DynamicFieldConfig> fmap = fieldConfigMap.values().stream()
				.filter(p -> (Arrays.asList("4").contains(p.getIsMobileAvail()) && p.getFormula() != null
						&& !StringUtil.isEmpty(p.getFormula())))
				.collect(Collectors.toList());
		if (fmap != null && !ObjectUtil.isListEmpty(fmap)) {
			Session session = this.getSessionFactory().openSession();
			fmap.sort((p1, p2) -> p1.getCode().compareTo(p2.getCode()));
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");

			fmap.stream().forEach(u -> {
				if (u.getReferenceId() != null && u.getReferenceId() > 0) {
					u.setFormula(u.getFormula().replace("##REFID##", String.valueOf(farmerDynamicData.getId())));
					u.setFormula(
							u.getFormula().replace("##REFERID##", String.valueOf(farmerDynamicData.getReferenceId())));
					u.setFormula(u.getFormula().replace("##SEASON##", String.valueOf(farmerDynamicData.getSeason())));

					List<String> fieldLiust = new ArrayList<>();
					Pattern p = Pattern.compile("\\{([^}]*)\\}");
					Matcher m = p.matcher(u.getFormula());
					while (m.find())
						fieldLiust.add(m.group(1));

					fieldLiust.stream().filter(ff -> fieldConfigMap.containsKey(ff)).forEach(g -> {
						if (fieldConfigMap.get(g).getValidation() != null
								&& fieldConfigMap.get(g).getValidation().equals("4")) {
							Double String = fdMap.get(g) != null ? fdMap.get(g).stream()
									.map(e -> Double.valueOf(e.getFieldValue())).reduce(0.00, (a, b) -> a + b) : 0.0;
							u.setFormula(u.getFormula().replaceAll("\\{" + g + "\\}",
									fdMap.containsKey(g) ? String.toString() : "0"));
						} else if (fieldConfigMap.get(g).getValidation() != null
								&& fieldConfigMap.get(g).getValidation().equals("2")) {
							Integer String = fdMap.get(g) != null ? fdMap.get(g).stream()
									.map(e -> Integer.valueOf(e.getFieldValue())).reduce(0, (a, b) -> a + b) : 0;
							u.setFormula(u.getFormula().replaceAll("\\{" + g + "\\}",
									fdMap.containsKey(g) ? String.toString() : "0"));
						} else {
							u.setFormula(u.getFormula().replaceAll("\\{" + g + "\\}",
									fdMap.containsKey(g) ? '"' + fdMap.get(g).get(0).getFieldValue() + '"' : "0"));
						}

					});

					Query query = session.createSQLQuery(u.getFormula());
					List<Object[]> result = (List<Object[]>) query.list();
					if (result != null) {
						result.stream().forEach(ff -> {
							FarmerDynamicFieldsValue farmerDynamicFieldsValue = new FarmerDynamicFieldsValue();
							farmerDynamicFieldsValue.setFieldName(u.getCode());
							farmerDynamicFieldsValue
									.setFieldValue(!ObjectUtil.isEmpty(ff[0].toString()) ? ff[0].toString() : "");
							farmerDynamicFieldsValue.setTypez(
									!ObjectUtil.isEmpty(ff[1].toString()) ? Integer.parseInt(ff[1].toString()) : 0);
							farmerDynamicFieldsValue.setComponentType(u.getComponentType());
							farmerDynamicFieldsValue.setTxnType(farmerDynamicData.getTxnType());
							farmerDynamicFieldsValue.setReferenceId(farmerDynamicData.getReferenceId());
							farmerDynamicFieldsValue.setCreatedDate(farmerDynamicData.getCreatedDate());
							farmerDynamicFieldsValue.setCreatedUser(farmerDynamicData.getCreatedUser());
							farmerDynamicFieldsValue.setTxnUniqueId(farmerDynamicData.getTxnUniqueId());
							farmerDynamicFieldsValue.setIsMobileAvail(u.getIsMobileAvail());
							farmerDynamicFieldsValue.setValidationType(u.getValidation());
							farmerDynamicFieldsValue.setIsMobileAvail(
									fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null ? fieldConfigMap
											.get(farmerDynamicFieldsValue.getFieldName()).getIsMobileAvail() : "0");

							farmerDynamicFieldsValue.setAccessType(
									fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null ? fieldConfigMap
											.get(farmerDynamicFieldsValue.getFieldName()).getAccessType() : 0);

							farmerDynamicFieldsValue
									.setListMethod(fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null
											&& fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName())
													.getCatalogueType() != null
															? fieldConfigMap
																	.get(farmerDynamicFieldsValue.getFieldName())
																	.getCatalogueType()
															: "");
							farmerDynamicFieldsValue
									.setParentId(fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null
											&& fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName())
													.getReferenceId() != null
															? fieldConfigMap
																	.get(farmerDynamicFieldsValue.getFieldName())
																	.getReferenceId()
															: 0);
							farmerDynamicFieldsValue.setFarmerDynamicData(farmerDynamicData);
							session.saveOrUpdate(farmerDynamicFieldsValue);
						});
					}

				} else {
					FarmerDynamicFieldsValue farmerDynamicFieldsValue = new FarmerDynamicFieldsValue();
					u.setFormula(u.getFormula().replace("##REFID##", String.valueOf(farmerDynamicData.getId())));
					u.setFormula(
							u.getFormula().replace("##REFERID##", String.valueOf(farmerDynamicData.getReferenceId())));
					u.setFormula(u.getFormula().replace("##SEASON##", String.valueOf(farmerDynamicData.getSeason())));
					u.setFormula(u.getFormula().replace("##BRANCH##", String.valueOf(farmerDynamicData.getBranch())));
					List<String> fieldLiust = new ArrayList<>();
					Pattern p = Pattern.compile("\\{([^}]*)\\}");
					Matcher m = p.matcher(u.getFormula());
					while (m.find())
						fieldLiust.add(m.group(1));

					fieldLiust.stream().filter(ff -> fieldConfigMap.containsKey(ff)).forEach(g -> {
						if (fieldConfigMap.get(g).getValidation() != null
								&& fieldConfigMap.get(g).getValidation().equals("4")) {
							Double String = fdMap.get(g) != null ? fdMap.get(g).stream()
									.map(e -> Double.valueOf(e.getFieldValue())).reduce(0.00, (a, b) -> a + b) : 0.0;
							u.setFormula(u.getFormula().replaceAll("\\{" + g + "\\}",
									fdMap.containsKey(g) ? String.toString() : "0"));
						} else if (fieldConfigMap.get(g).getValidation() != null
								&& fieldConfigMap.get(g).getValidation().equals("2")) {
							Integer String = fdMap.get(g) != null ? fdMap.get(g).stream()
									.map(e -> Integer.valueOf(e.getFieldValue())).reduce(0, (a, b) -> a + b) : 0;
							u.setFormula(u.getFormula().replaceAll("\\{" + g + "\\}",
									fdMap.containsKey(g) ? String.toString() : "0"));
						} else {
							u.setFormula(u.getFormula().replaceAll("\\{" + g + "\\}",
									fdMap.containsKey(g) ? '"' + fdMap.get(g).get(0).getFieldValue() + '"' : "0"));
						}

					});

					Query query = session.createSQLQuery(u.getFormula());
					String result = (String) query.uniqueResult();
					farmerDynamicFieldsValue.setFieldName(u.getCode());
					farmerDynamicFieldsValue.setFieldValue(result);
					farmerDynamicFieldsValue.setComponentType(u.getComponentType());
					farmerDynamicFieldsValue.setTxnType(farmerDynamicData.getTxnType());
					farmerDynamicFieldsValue.setReferenceId(farmerDynamicData.getReferenceId());
					farmerDynamicFieldsValue.setCreatedDate(farmerDynamicData.getCreatedDate());
					farmerDynamicFieldsValue.setCreatedUser(farmerDynamicData.getCreatedUser());
					farmerDynamicFieldsValue.setTxnUniqueId(farmerDynamicData.getTxnUniqueId());
					farmerDynamicFieldsValue.setIsMobileAvail(u.getIsMobileAvail());
					farmerDynamicFieldsValue.setValidationType(u.getValidation());
					farmerDynamicFieldsValue
							.setIsMobileAvail(fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null
									? fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getIsMobileAvail()
									: "0");

					farmerDynamicFieldsValue
							.setAccessType(fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null
									? fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getAccessType() : 0);

					farmerDynamicFieldsValue.setListMethod(fieldConfigMap
							.get(farmerDynamicFieldsValue.getFieldName()) != null
							&& fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getCatalogueType() != null
									? fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getCatalogueType()
									: "");
					farmerDynamicFieldsValue.setParentId(fieldConfigMap
							.get(farmerDynamicFieldsValue.getFieldName()) != null
							&& fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getReferenceId() != null
									? fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getReferenceId() : 0);
					farmerDynamicFieldsValue.setFarmerDynamicData(farmerDynamicData);
					farmerDynamicFieldsValue
							.setIsUpdateProfile(fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null
									? fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getIsUpdateProfile()
									: "0");

					LinkedHashMap<String, DynamicFieldConfig> newMap = new LinkedHashMap<>(fieldConfigMap);
					Map<String, Object> profileUpdateFields = new HashMap<>();
					profileUpdateFields = new HashMap<>();
					profileUpdateFields.put(farmerDynamicFieldsValue.getFieldName(),
							farmerDynamicFieldsValue.getFieldValue());
					farmerDynamicFieldsValue.getFarmerDynamicData().setProfileUpdateFields(profileUpdateFields);
					/*
					 * if (farmerDynamicData.getProfileUpdateFields() != null &&
					 * !ObjectUtil.isEmpty(farmerDynamicData.
					 * getProfileUpdateFields())) {
					 * processProfileUpdates(farmerDynamicData.
					 * getProfileUpdateFields(), fieldConfigMap,
					 * farmerDynamicData); }
					 */
					session.saveOrUpdate(farmerDynamicFieldsValue);
					/*
					 * farmerService.processProfileUpdates(
					 * farmerDynamicFieldsValue.getFarmerDynamicData().
					 * getProfileUpdateFields(), fieldConfigMap,
					 * farmerDynamicData);
					 */
					farmerDynamicData.getFarmerDynamicFieldsValues().add(farmerDynamicFieldsValue);

					// farmerService.saveOrUpdate(farmerDynamicData,fdMap,newMap);
				}

			});
			session.flush();
			session.clear();
			session.close();

		}
		return farmerDynamicData;

	}

	@Override
	public void deleteChildObjects(String txnType) {
		Session session = getSessionFactory().openSession();
		SQLQuery query = session.createSQLQuery(
				"delete from farmer_dynamic_field_value where FARMER_DYNAMIC_DATA_ID IS NULL and txn_type='" + txnType
						+ "';");
		query.executeUpdate();
		session.flush();
		session.close();

	}

	@Override
	public List<FarmCatalogue> listCatelogueType(String type) {

		// TODO Auto-generated method stub
		Object[] values = { Integer.valueOf(type), FarmCatalogue.ACTIVE };
		return list("FROM FarmCatalogue where typez=? AND status=?", values);
	}

	int iCount = 0;

	@Override
	public List<FarmerDynamicFieldsValue> processFormula(FarmerDynamicData farmerDynamicData,
			Map<String, List<FarmerDynamicFieldsValue>> fdMap,
			LinkedHashMap<String, DynamicFieldConfig> fieldConfigMap) {

		List<FarmerDynamicFieldsValue> resultSet = new ArrayList<>();

		LinkedList<DynamicFieldConfig> fmap = fieldConfigMap.values().stream()
				.filter(p -> (Arrays.asList("0", "2").contains(p.getIsMobileAvail()) && p.getFormula() != null
						&& !StringUtil.isEmpty(p.getFormula())) || (p.getIsMobileAvail().equals("5")))
				.sorted(Comparator.comparing(DynamicFieldConfig::getfOrder))
				.collect(Collectors.toCollection(LinkedList::new));

		List<DynamicConstants> consts = listDynamicConstants();
		Map<String, String> cts = consts.stream().collect(Collectors.toMap(u -> u.getCode(), u -> u.getFieldName()));
		if (fmap != null && !ObjectUtil.isListEmpty(fmap)) {
			// fmap.sort((p1, p2) -> p1.getCode().compareTo(p2.getCode()));
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			fmap.stream().forEach(u -> {
				FarmerDynamicFieldsValue farmerDynamicFieldsValue = new FarmerDynamicFieldsValue();
				if (u.getIsMobileAvail().equals("5")) {
					if (farmerDynamicData.getScoreValue() != null && !farmerDynamicData.getScoreValue().isEmpty()
							&& farmerDynamicData.getScoreValue().containsKey(u.getCode())) {
						Map<String, String> smMap = farmerDynamicData.getScoreValue().get(u.getCode());

						smMap.entrySet().forEach(score -> {
							if (score.getKey().contains("|")) {
								/*
								 * format of the score /*CT001|f1,f2,f3~-1~3-2.0
								 */
								if (farmerDynamicFieldsValue.getScore() == null) {
									String[] cond = score.getKey().split("\\|");
									String catCode = cond[0].toString();
									String[] fields = cond[1].toString().split("~")[0].split(",");
									String noOf = cond[1].toString().split("~")[1].toString();
									String scPer = cond[1].toString().split("~")[2].toString();
									List<Integer> ii = new ArrayList<>();

									Arrays.asList(fields).stream().forEach(field -> {
										if (field.contains("$")) {
											iCount = 0;
											Arrays.asList(field.split("\\$")).stream().forEach(ff -> {
												if (fdMap.containsKey(ff.trim())) {
													if (fdMap.get(ff.trim()).get(0).getScore() != null
															&& fdMap.get(ff.trim()).get(0).getScore() > iCount) {
														iCount = fdMap.get(ff.trim()).get(0).getScore();
													}
												}
											});
											ii.add(iCount);
										} else {
											if (fdMap.containsKey(field.trim())) {

												if (fdMap.get(field.trim()).get(0).getScore() != null) {
													ii.add(fdMap.get(field.trim()).get(0).getScore());
												}

											} else {
												ii.add(0);
											}
										}
									});

									if (noOf != null && noOf.equals("-1")) {
										if (ii.stream().filter(per -> per == 3).count() == fields.length) {
											Integer sc = Integer.valueOf(scPer.split("-")[0].toString());
											Double per = Double.valueOf(scPer.split("-")[1].toString());
											farmerDynamicFieldsValue.setScore(sc);
											farmerDynamicFieldsValue.setPercentage(per);
											farmerDynamicFieldsValue.setFieldValue(catCode);
										}

									} else if (noOf != null && ii.stream()
											.filter(per -> Arrays.asList(noOf.split(",")).contains(String.valueOf(per)))
											.count() == fields.length) {
										Integer sc = Integer.valueOf(scPer.split("-")[0].toString());
										Double per = Double.valueOf(scPer.split("-")[1].toString());
										farmerDynamicFieldsValue.setScore(sc);
										farmerDynamicFieldsValue.setPercentage(per);
										farmerDynamicFieldsValue.setFieldValue(catCode);

									}

								}
							}

						});
						farmerDynamicData.setTotalScore(
								farmerDynamicData.getTotalScore() + (farmerDynamicFieldsValue.getPercentage() == null
										? 0 : farmerDynamicFieldsValue.getPercentage()));
					}
				} else {
					String ans = "";
					List<String> fieldLiust = new ArrayList<>();
					formula = u.getFormula();
					Pattern p = Pattern.compile("\\{([^}]*)\\}");
					Matcher m = p.matcher(formula);
					while (m.find())
						fieldLiust.add(m.group(1));

					fieldLiust.stream().filter(ff -> fieldConfigMap.containsKey(ff)).forEach(g -> {
						if (fieldConfigMap.get(g).getValidation() != null
								&& fieldConfigMap.get(g).getValidation().equals("4")) {
							Double String = fdMap.get(g) != null ? fdMap.get(g).stream()
									.map(e -> Double.valueOf(e.getFieldValue())).reduce(0.00, (a, b) -> a + b) : 0.0;
							formula = formula.replaceAll("\\{" + g + "\\}",
									fdMap.containsKey(g) ? String.toString() : "0");
						} else if (fieldConfigMap.get(g).getValidation() != null
								&& fieldConfigMap.get(g).getValidation().equals("2")) {
							Integer String = fdMap.get(g) != null ? fdMap.get(g).stream()
									.map(e -> Integer.valueOf(e.getFieldValue())).reduce(0, (a, b) -> a + b) : 0;
							formula = formula.replaceAll("\\{" + g + "\\}",
									fdMap.containsKey(g) ? String.toString() : "0");
						} else {
							formula = formula.replaceAll("\\{" + g + "\\}",
									fdMap.containsKey(g) ? '"' + fdMap.get(g).get(0).getFieldValue() + '"' : "0");
						}

					});
					cts.entrySet().forEach(consta -> {
						if (formula.contains(consta.getKey())) {
							Pattern cp = Pattern.compile("\\##([^##]*)\\##");
							Matcher cm = cp.matcher(formula);
							while (cm.find()) {

								String vlue = getFieldValueByContant(farmerDynamicData.getEntityId(),
										farmerDynamicData.getReferenceId(), cts.get(cm.group(1)));

								if (ObjectUtil.isEmpty(vlue) || StringUtil.isEmpty(vlue)) {
									vlue = "0";
								}

								formula = formula.replaceAll(DynamicFieldConfig.FORMULA_CONSTANT_VALUE + consta.getKey()
										+ DynamicFieldConfig.FORMULA_CONSTANT_VALUE, vlue);

							}
						}
					});
					try {
						ans = String.valueOf(engine.eval(formula));
						if (ans.equals("NaN") || ans.contains("Infinity")) {
							ans = "0";
						}
					} catch (ScriptException e) {
						ans = "0";
					}
					farmerDynamicFieldsValue.setFieldValue(ans);
				}
				farmerDynamicFieldsValue.setFieldName(u.getCode());

				farmerDynamicFieldsValue.setComponentType(u.getComponentType());
				farmerDynamicFieldsValue.setTxnType(farmerDynamicData.getTxnType());
				farmerDynamicFieldsValue.setReferenceId(farmerDynamicData.getReferenceId());
				farmerDynamicFieldsValue.setCreatedDate(farmerDynamicData.getCreatedDate());
				farmerDynamicFieldsValue.setCreatedUser(farmerDynamicData.getCreatedUser());
				farmerDynamicFieldsValue.setTxnUniqueId(farmerDynamicData.getTxnUniqueId());
				farmerDynamicFieldsValue.setIsMobileAvail(u.getIsMobileAvail());
				farmerDynamicFieldsValue.setValidationType(u.getValidation());
				farmerDynamicFieldsValue.setFarmerDynamicData(farmerDynamicData);
				farmerDynamicFieldsValue
						.setIsMobileAvail(fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null
								? fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getIsMobileAvail() : "0");

				farmerDynamicFieldsValue
						.setAccessType(fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null
								? fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getAccessType() : 0);

				farmerDynamicFieldsValue.setListMethod(
						fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null && fieldConfigMap
								.get(farmerDynamicFieldsValue.getFieldName()).getCatalogueType() != null
										? fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getCatalogueType()
										: "");
				farmerDynamicFieldsValue.setParentId(fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null
						&& fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getReferenceId() != null
								? fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getReferenceId() : 0);
				fdMap.put(farmerDynamicFieldsValue.getFieldName(), new ArrayList<FarmerDynamicFieldsValue>() {
					{
						add(farmerDynamicFieldsValue);

					}
				});
				resultSet.add(farmerDynamicFieldsValue);

			});
		}
		return resultSet;

	}

	@Override
	public List<FarmerDynamicFieldsValue> listFarmerDynmaicFieldsByRefId(String refId, String txnType) {
		Object[] values = { refId, txnType };
		return list(
				"select fdfv FROM FarmerDynamicFieldsValue fdfv LEFT JOIN FETCH fdfv.dymamicImageData di LEFT JOIN FETCH  fdfv.followUps where fdfv.referenceId=? AND fdfv.txnType=?",
				values);

	}

	@Override
	public List<DynamicFeildMenuConfig> findDynamicMenusByMType(String txnType) {
		// return list("FROM DynamicFeildMenuConfig dsc WHERE dsc.mTxnType = ?
		// ORDER BY dsc.order ASC", txnType);
		return list(
				"select distinct dsc FROM DynamicFeildMenuConfig dsc join fetch dsc.dynamicSectionConfigs ds  join fetch dsc.dynamicFieldConfigs  dc join fetch dc.field ff join fetch ff.dynamicSectionConfig sec join fetch dc.field left join fetch dc.dynamicFieldScoreMap WHERE dsc.mTxnType = ? ORDER BY dsc.order ASC",
				txnType);
	}

	@Override
	public List<FarmerDynamicFieldsValue> listFarmerDynamicFieldsValuePhotoByRefTxnType(String refId, String txnType) {
		Object[] values = { refId, txnType };
		return (List<FarmerDynamicFieldsValue>) list(
				"FROM FarmerDynamicFieldsValue fdfv INNER JOIN FETCH fdfv.dymamicImageData dImage LEFT JOIN FETCH  fdfv.followUps  WHERE fdfv.referenceId=? AND fdfv.txnType=?",
				values);
	}

	@Override
	public void updateDynamicFarmerFieldComponentType() {
		Session session = getSessionFactory().openSession();
		Query query = session.createSQLQuery(
				"update farmer_dynamic_field_value set COMPONENT_TYPE=(SELECT dfc.COMPONENT_TYPE from dynamic_fields_config dfc where dfc.component_code=FIELD_NAME);");
		int result = query.executeUpdate();

		query = session.createSQLQuery(
				"DELETE FROM farmer_dynamic_field_value where farmer_id is null AND REFERENCE_ID IS NULL");
		result = query.executeUpdate();

		session.flush();
		session.close();
	}

	@Override
	public Object[] findFarmerInfoById(Long id) {
		// TODO Auto-generated method stub
		return (Object[]) find("select fr.farmerId,fr.firstName,fr.lastName FROM Farmer fr WHERE fr.id = ?", id);
	}

	@Override
	public List<DynamicFeildMenuConfig> listDynamicMenus() {
		return list("FROM DynamicFeildMenuConfig dsc order by dsc.order asc ");
	}

	public FarmerDynamicData findFarmerDynamicDataByReferenceIdAndTxnType(long referenceId) {

		return (FarmerDynamicData) find(
				"FROM FarmerDynamicData fdfv where fdfv.referenceId=? and fdfv.txnType=2001 order by fdfv.id desc",
				String.valueOf(referenceId));
	}

	@Override
	public String getFieldValueByContant(String entityId, String referenceId, String group) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long findActiveContractFarmersLatestRevisionNoByAgentAndSeason(long agentId, String seasonCode) {

		Session session = getSessionFactory().getCurrentSession();
		Query query = session
				.createQuery("SELECT Max(COALESCE(f.revisionNo,0)) FROM Farmer f WHERE f.statusCode in (:status1) ");
		// query.setParameter("id", agentId);
		query.setParameterList("status1",
				new Object[] { ESETxnStatus.SUCCESS.ordinal(), ESETxnStatus.DELETED.ordinal() });
		List list = query.list();
		if (!ObjectUtil.isListEmpty(list) && !ObjectUtil.isEmpty(list.get(0))) {
			return (Long) list.get(0);
		}
		return 0l;

	}

	@Override
	public Long listFarmFieldsByFarmerIdByAgentIdAndSeasonRevisionNo(long id) {
		// TODO Auto-generated method stub

		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(
				"SELECT  MAX(COALESCE(fm.revisionNo,0)) FROM Farm fm WHERE fm.status in (0,1)  AND fm.farmer.statusCode in  (:status1)  order by fm.revisionNo DESC");
		// query.setParameter("id", id);
		// query.setParameter("revisionNo", revisionNo);
		query.setParameterList("status1",
				new Object[] { ESETxnStatus.SUCCESS.ordinal(), ESETxnStatus.DELETED.ordinal() });
		List list = query.list();
		if (!ObjectUtil.isListEmpty(list) && !ObjectUtil.isEmpty(list.get(0))) {
			return (Long) list.get(0);
		}
		return 0l;
	}

	@Override
	public List<DynamicFeildMenuConfig> listDynamicMenusByRevNo(String revisionNo, String branchId, String tenantId) {

		// Session session =
		// getHibernateTemplate().getSessionFactory().openSession();
		Session session = getSessionFactory().withOptions().tenantIdentifier(tenantId).openSession();
		// Session session =
		// getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(
				"select distinct dsc FROM DynamicFeildMenuConfig dsc  join fetch dsc.dynamicSectionConfigs dsm  join fetch dsc.dynamicFieldConfigs dfm  join fetch dsm.section ss  join fetch dfm.field ff  left join fetch ff.languagePreferences left join fetch ss.languagePreferences  left join fetch ff.dynamicSectionConfig ffs left join fetch dfm.dynamicFieldScoreMap  where dsc.revisionNo > :revNo  and (dsc.branchId is null or dsc.branchId =''  or dsc.branchId like :branchId) and  (dfm.branchId is null or dfm.branchId ='' or dfm.branchId like :branchId) and  (dsm.branchId is null or dsm.branchId ='' or dsm.branchId like :branchId)  order by dsc.revisionNo desc ");
		query.setParameter("branchId", "%" + branchId + "%");
		query.setParameter("revNo", Long.valueOf(revisionNo));

		List<DynamicFeildMenuConfig> result = query.list();
		session.flush();
		session.close();
		return result;

	}

	List<Object[]> result = new ArrayList<>();

	@Override
	public List<Object[]> listFDVSForFolloUp(String agentId, String revsionNo) {
		result = new ArrayList<>();
		Session session = getSessionFactory().openSession();
		SQLQuery query = session
				.createSQLQuery("select txn_type,entity from dynamic_menu_config dm where dm.is_score in (2,3)");
		List<Object[]> list = query.list();
		if (list != null && !ObjectUtil.isEmpty(list)) {
			list.stream().forEach(u -> {
				if (u[1].toString().equals("1")) {
					SQLQuery sql = session.createSQLQuery(
							"SELECT distinct fd.txn_Type,fd.id,fd.DATE,fd.CREATED_USER,fdv.FIELD_NAME,fdv.FIELD_VALUE,fdv.ACCESS_TYPE,fdv.CATALOGUE_TYPE,concat(f.first_name,' ',f.last_name),f.farmer_id,act.field_value  as act_value ,dead.field_value as dead,fdv.id AS DEALINE,fd.REVISION_NO,f.farmer_code AS code,w.name AS grp,v.name AS village    FROM `prof` p join agent_warehouse_map amp on p.id = amp.AGENT_ID  and p.PROF_ID='"
									+ agentId
									+ "' join farmer f on f.SAMITHI_ID = amp.WAREHOUSE_ID join warehouse w on w.id=f.samithi_id join village v on v.id = f.village_id join farmer_dynamic_data fd on fd.REFERENCE_ID = f.id AND (fd.ACT_STATUS=1) and fd.REVISION_NO >'"
									+ revsionNo + "' and TXN_TYPE ='" + u[0].toString().trim()
									+ "' join farmer_dynamic_field_value fdv on fdv.FARMER_DYNAMIC_DATA_ID = fd.id   join farmer_dynamic_field_value act on fdv.action_plan = act.id  join farmer_dynamic_field_value dead on fdv.deadline = dead.id  order by fd.TXN_UNIQUE_ID DESC ");
					result.addAll(sql.list());
				} else if (u[1].toString().equals("2") || u[1].toString().equals("4")) {
					SQLQuery sql = session.createSQLQuery(
							"SELECT distinct fd.txn_Type,fd.id,fd.DATE,fd.CREATED_USER,fdv.FIELD_NAME,fdv.FIELD_VALUE,fdv.ACCESS_TYPE,fdv.CATALOGUE_TYPE,concat(f.first_name,'-',fm.farm_Name),fm.farm_Code,act.field_value  as act_value ,dead.field_value as dead,fdv.id AS DEALINE,fd.REVISION_NO,'',w.name AS grp,v.name AS village    FROM `prof` p join agent_warehouse_map amp on p.id = amp.AGENT_ID  and p.PROF_ID='"
									+ agentId
									+ "' join farmer f on f.SAMITHI_ID = amp.WAREHOUSE_ID join warehouse w on w.id=f.samithi_id join village v on v.id = f.village_id join farm fm on fm.FARMER_ID = f.id  join farmer_dynamic_data fd on fd.REFERENCE_ID = fm.id AND (fd.ACT_STATUS=1) and fd.REVISION_NO >'"
									+ revsionNo + "'  and TXN_TYPE ='" + u[0].toString().trim()
									+ "' join farmer_dynamic_field_value fdv on fdv.FARMER_DYNAMIC_DATA_ID = fd.id   join farmer_dynamic_field_value act on fdv.action_plan = act.id  join farmer_dynamic_field_value dead on fdv.deadline = dead.id order by fd.TXN_UNIQUE_ID DESC ");
					result.addAll(sql.list());
				}
			});
		}
		session.flush();
		session.close();
		return result;
	}

	@Override
	public List<DynamicMenuFieldMap> listDynamisMenubyscoreType() {
		return list(
				"select distinct dfm FROM DynamicMenuFieldMap dfm join fetch dfm.field ff  left join fetch ff.languagePreferences   left join fetch ff.dynamicSectionConfig ffs  where dfm.menu.isScore in (2,3) and ff.followUp in (3,4) order by dfm.menu.mTxnType,dfm.order ");
	}

	@Override
	public List<Object[]> listActiveContractFarmersAccountByAgentAndSeason(long agentId, String seasonCode,
			Date revisionDate) {

		Object[] values = new Object[] { agentId, seasonCode, ESEAccount.CONTRACT_ACCOUNT, ESEAccount.ACTIVE,
				Farmer.Status.ACTIVE.ordinal(), ESETxnStatus.SUCCESS.ordinal() };

		String revisionQuery = " ";

		if (!ObjectUtil.isEmpty(revisionDate)) {
			values = new Object[] { agentId, seasonCode, ESEAccount.CONTRACT_ACCOUNT, ESEAccount.ACTIVE,
					Farmer.Status.ACTIVE.ordinal(), ESETxnStatus.SUCCESS.ordinal(), revisionDate };
			revisionQuery = "AND c.account.updateTime>? ";
		}
		List<Object[]> list = list(
				"SELECT c.farmer.farmerId,c.farmer.firstName,c.farmer.lastName,c.account.creditBalance,c.account.updateTime,c.account.cashBalance FROM Contract c WHERE c.farmer.samithi.id in (SELECT s.id FROM Agent a INNER JOIN a.wareHouses s WHERE a.id=?) AND c.season.code=?  AND c.account.type=? AND c.account.status=? AND c.farmer.status=?  AND c.farmer.statusCode = ?  "
						// "SELECT
						// c.farmer.farmerId,c.farmer.firstName,c.farmer.lastName,c.account.balance,c.account.distributionBalance,c.account.updateTime
						// FROM Contract c WHERE c.farmer.samithi.id in (SELECT
						// s.id FROM Agent a
						// INNER JOIN a.wareHouses s WHERE a.id=?) AND
						// c.season.code=? AND
						// c.account.type=? AND c.account.status=? AND
						// c.farmer.status=? AND
						// c.farmer.statusCode = ? "
						+ revisionQuery + " order by c.account.updateTime DESC ",
				values);
		return list;

	}

	@Override
	public List<Object[]> listActiveContractFarmersFieldsBySeasonRevNoAndSamithiWithGramp(long id,
			String currentSeasonCode, Long revisionNo, List<String> branch) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(
				"select f.id,COALESCE(f.farmerId,''), COALESCE(f.farmerCode,''),COALESCE(f.firstName,''),COALESCE(f.firstName,''),COALESCE(f.village.code,''),f.farms.size,f.status,f.mobileNo,f.revisionNo,f.revisionNo,f.exporter.name,f.exporter.regNo FROM Farmer f LEFT JOIN f.village v LEFT JOIN v.gramPanchayat gm  WHERE   f.revisionNo > :revisionNo  AND f.statusCode in  (:status1)  order by f.revisionNo DESC");
		query.setParameter("revisionNo", revisionNo);
		query.setParameterList("status1",
				new Object[] { ESETxnStatus.SUCCESS.ordinal(), ESETxnStatus.DELETED.ordinal() });
		List result = query.list();

		return result;

	}

	public List<Object[]> listfarmerDynamicData(List<Long> fidLi) {
		Session session = getSessionFactory().openSession();
		Query query = session.createSQLQuery(
				"SELECT fd.REFERENCE_ID,DATE(max( fd.DATE )),(SELECT fddv.field_value FROM farmer_dynamic_field_value fdv JOIN farmer_dynamic_field_value fddv ON fdv.deadline = fddv.id  where fdv.FARMER_DYNAMIC_DATA_ID = max( fd.id ) LIMIT 1) FROM farmer_dynamic_data fd  WHERE fd.REFERENCE_ID IN ( :ids)  AND fd.TXN_TYPE = 2001 GROUP BY fd.REFERENCE_ID");

		// SQLQuery sqlQuery = session.createSQLQuery(query);
		query.setParameterList("ids", fidLi);
		List<Object[]> list = query.list();
		session.flush();
		session.close();
		return list;
	}

	public List<Object[]> findCountOfDynamicDataByFarmerId(List<Long> fidLi, String season) {
		Session session = getSessionFactory().openSession();
		Query query = session.createSQLQuery(
				"SELECT FARMER_ID,group_concat( concat( `name`, '-', `value` ) SEPARATOR ',' ) AS `ColumnName` FROM(SELECT fm.FARMER_ID,(CASE WHEN fdd.TXN_TYPE = 2001 THEN 'Land Preparation ' WHEN fdd.TXN_TYPE = 2002 THEN 'After Sowing ' WHEN fdd.TXN_TYPE = 2003 THEN 'Vegetative Growth phase I ' WHEN fdd.TXN_TYPE = 2004 THEN 'Vegetative Growth phase II ' WHEN fdd.TXN_TYPE = 2005 THEN 'Flowering and boll formation ' WHEN fdd.TXN_TYPE = 2006 THEN 'Harvesting ' ELSE '' END ) AS NAME,count( fdd.ID ) AS VALUE FROM farm_crops fc INNER JOIN farm fm ON fc.FARM_ID = fm.ID INNER JOIN farmer_dynamic_data fdd ON fdd.REFERENCE_ID = fc.ID  WHERE fm.FARMER_ID IN ( :ids ) AND fdd.TXN_TYPE in (2001,2002,2003,2004,2005,2006) AND fdd.SEASON =:season GROUP BY fm.FARMER_ID,fdd.TXN_TYPE ) tbl GROUP BY FARMER_ID");
		query.setParameter("season", season);
		query.setParameterList("ids", fidLi);
		List<Object[]> list = query.list();
		session.flush();
		session.close();
		return list;
	}

	@Override
	public List<Object[]> listFarmFieldsByFarmerIdByAgentIdAndSeason(long id, Long revisionNo) {
		// TODO Auto-generated method stub

		Session session = getSessionFactory().getCurrentSession();
		/*
		 * Query query = session.createQuery(
		 * "SELECT fm.id,COALESCE(fm.farmCode,''),COALESCE(fm.farmName,''),COALESCE(fm.farmId,''),COALESCE(fm.farmDetailedInfo.lastDateOfChemicalApplication,''),COALESCE(fm.isVerified,'0'),fm.farmer.farmerId,fm.cows.size,fm.farmICSConversion.size,fm.farmer.id,fm.status,fm.revisionNo FROM Farm fm WHERE fm.farmer.samithi.id in (SELECT s.id FROM Agent a INNER JOIN a.wareHouses s WHERE a.id=:id) AND  fm.revisionNo > :revisionNo    and  fm.status=1  AND fm.farmer.statusCode in  (:status1)  order by fm.revisionNo DESC"
		 * );
		 */
		Query query = session.createQuery(
				"SELECT fm.id,COALESCE(fm.farmCode,''),COALESCE(fm.farmName,''),fm.farmer.farmerId,fm.status,fm.latitude,fm.longitude,fm.revisionNo FROM Farm fm WHERE  fm.revisionNo > :revisionNo    and  fm.status in (0,1)  AND fm.farmer.statusCode in  (:status1)  order by fm.revisionNo DESC");
		query.setParameter("revisionNo", revisionNo);
		query.setParameterList("status1",
				new Object[] { ESETxnStatus.SUCCESS.ordinal(), ESETxnStatus.DELETED.ordinal() });
		List result = query.list();

		return result;

	}

	public List<Object[]> listDynamicFieldsCodeAndName() {

		String query = " select dfc.COMPONENT_CODE,dfc.COMPONENT_NAME from dynamic_fields_config dfc ";
		Session session = getSessionFactory().openSession();
		SQLQuery sqlQuery = session.createSQLQuery(query);
		List<Object[]> list = sqlQuery.list();
		session.flush();
		session.close();
		return list;
	}

	public List<Object[]> listFarmerDynamicFieldsValuesByFarmIdList(List<String> farmIdList,
			List<String> selectedDynamicFieldCodeList) {

		// 0 - fdfv.FIELD_NAME
		// 1 - fdfv.FIELD_VALUE
		// 2 - fdfv.COMPONENT_TYPE
		// 3 - fdfv.TYPEZ
		// 4 - fdfv.REFERENCE_ID
		// 5 - fdfv.TXN_TYPE
		// 6 - fdfv.FARMER_DYNAMIC_DATA_ID
		// 7 - fdfv.ACCESS_TYPE
		// 8 - fdfv.PARENT_ID
		// 9 - fic.INSPECTION_DATE
		// 10 - fic.INSPECTOR_NAME
		// 11 - fic.INSPECTION_TYPE
		// 12 - fic.TOTAL_LANDHOLD
		// 13 - fic.LAND_ORGANIC

		/*
		 * String str = farmIdList.toString(); String csv = str.substring(1,
		 * str.length() - 1).replace(", ", ",");
		 * 
		 * String query = "SELECT "; query +=
		 * " fdfv.FIELD_NAME,fdfv.FIELD_VALUE,fdfv.COMPONENT_TYPE,fdfv.TYPEZ,fdfv.REFERENCE_ID,fdfv.TXN_TYPE,fdfv.FARMER_DYNAMIC_DATA_ID,fdfv.ACCESS_TYPE,fdfv.PARENT_ID,fic.INSPECTION_DATE,fic.INSPECTOR_NAME,fic.INSPECTION_TYPE,fic.TOTAL_LANDHOLD,fic.LAND_ORGANIC "
		 * ; query += " FROM farmer_dynamic_field_value fdfv "; query +=
		 * " inner join farmer_dynamic_data fdd on fdd.ID = fdfv.FARMER_DYNAMIC_DATA_ID "
		 * ; query +=
		 * " inner join farm_ics_conversion fic on fic.ID = fdd.FARM_ICS_CONV_ID "
		 * ; query += " WHERE "; if(selectedDynamicFieldCodeList != null &&
		 * !selectedDynamicFieldCodeList.isEmpty()){ query +=
		 * "  fdfv.FIELD_NAME in ( :selectedDynFields )  and "; } query +=
		 * " fdfv.REFERENCE_ID IN ( :referenceId ) "; query +=
		 * " AND fdfv.FARMER_DYNAMIC_DATA_ID IN ( SELECT Max( fdfv2.FARMER_DYNAMIC_DATA_ID ) FROM farmer_dynamic_field_value fdfv2 WHERE fdfv2.REFERENCE_ID IN ( :referenceId ) GROUP BY fdfv2.REFERENCE_ID ORDER BY instr( :referenceIdStr ,fdfv2.REFERENCE_ID) ) "
		 * ;
		 * 
		 * query += " ORDER BY instr( :referenceIdStr ,fdfv.REFERENCE_ID) ";
		 * 
		 * if(selectedDynamicFieldCodeList != null &&
		 * !selectedDynamicFieldCodeList.isEmpty()){ query +=
		 * " , instr( :selectedDynFieldsStr ,fdfv.FIELD_NAME) "; }
		 * sqlQuery.setParameterList("referenceId", farmIdList);
		 * sqlQuery.setParameter("referenceIdStr", csv);
		 * 
		 */

		String query = "SELECT ";
		query += " fdfv.FIELD_NAME,fdfv.FIELD_VALUE,fdfv.COMPONENT_TYPE,fdfv.TYPEZ,fdfv.REFERENCE_ID,fdfv.TXN_TYPE,fdfv.FARMER_DYNAMIC_DATA_ID,fdfv.ACCESS_TYPE,fdfv.PARENT_ID,fic.INSPECTION_DATE,fic.INSPECTOR_NAME,fic.INSPECTION_TYPE,fic.TOTAL_LANDHOLD,fic.LAND_ORGANIC  ";
		query += " FROM farmer_dynamic_field_value fdfv ";
		query += " INNER JOIN farmer_dynamic_data fdd ON fdd.ID = fdfv.FARMER_DYNAMIC_DATA_ID ";
		query += " AND fdfv.FIELD_NAME IN ( :selectedDynFields ) ";
		query += " AND fdd.ENTITY_ID = 4 ";
		query += " INNER JOIN farm_ics_conversion fic ON fic.ID = fdd.FARM_ICS_CONV_ID ";
		query += " JOIN ( SELECT df.REFERENCE_ID,max( FARMER_DYNAMIC_DATA_ID ) AS ffdis  ";
		query += " FROM farmer_dynamic_field_value df";
		query += " JOIN farmer_dynamic_data fdd ON fdd.id = df.FARMER_DYNAMIC_DATA_ID ";
		query += " AND fdd.ENTITY_ID = 4";
		query += " GROUP BY df.REFERENCE_ID";
		query += " ) x ON x.REFERENCE_ID = fdfv.REFERENCE_ID";
		query += " AND fdd.id = ffdis";
		query += " ORDER BY instr( :selectedDynFieldsStr , fdfv.FIELD_NAME )";

		Session session = getSessionFactory().openSession();
		SQLQuery sqlQuery = session.createSQLQuery(query);

		if (selectedDynamicFieldCodeList != null && !selectedDynamicFieldCodeList.isEmpty()) {
			sqlQuery.setParameterList("selectedDynFields", selectedDynamicFieldCodeList);
			String selectedFields_str = selectedDynamicFieldCodeList.toString();
			String selectedFields_csv = selectedFields_str.substring(1, selectedFields_str.length() - 1).replace(", ",
					",");
			sqlQuery.setParameter("selectedDynFieldsStr", selectedFields_csv);
		}

		List<Object[]> list = sqlQuery.list();
		session.flush();
		session.close();
		return list;
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
	public FarmerDynamicData findFarmerDynamicDataByTxnUniquId(String txnUniquId) {
		// TODO Auto-generated method stub
		return (FarmerDynamicData) find("from FarmerDynamicData where txnUniqueId = ? ", Long.valueOf(txnUniquId));
	}

	@Override
	public List<Farmer> listFarmerByFarmerIdByIdList(List<String> id) {

		Session sessions = getSessionFactory().openSession();
		String queryString = "FROM Farmer f WHERE f.farmerId IN (:idList) AND f.statusCode = :status";
		Query query = sessions.createQuery(queryString);
		query.setParameterList("idList", id);
		query.setParameter("status", ESETxnStatus.SUCCESS.ordinal());
		List<Farmer> result = query.list();
		sessions.flush();
		sessions.close();
		return result;
	}

	@Override
	public FarmerDynamicData findFarmerDynamicDataBySeason(String txnType, String id, String season) {
		return (FarmerDynamicData) find(
				"FROM FarmerDynamicData fdfv join fetch  fdfv.farmerDynamicFieldsValues where fdfv.txnType=? and fdfv.referenceId=? and fdfv.season=?",
				new Object[] { txnType, id, season });
	}

	@Override
	public Integer findFarmersCountByStatus(Date sDate, Date eDate, Long id) {

		Session session = getSessionFactory().openSession();
		String q = "select count(*) from Farmer where status in (1) and status_code=:statusCode and createdDate BETWEEN :startDate AND :endDate";
		if (id > 0) {
			/*q += " and exporters in (" + id +")";*/
		}
		Query query = session.createQuery(q);
		query.setParameter("startDate", sDate).setParameter("endDate", eDate);
		query.setParameter("statusCode", Integer.valueOf(ESETxnStatus.SUCCESS.ordinal()));

		Integer val = Integer.valueOf(((Long) query.uniqueResult()).intValue());
		session.flush();
		session.close();
		return val;
	}

	@Override
	public Integer findFarmersCountByStatusAndSeason(
			String currentSeason) {/*
									 * // TODO Auto-generated method stub
									 * 
									 * Session session =
									 * getSessionFactory().openSession(); Query
									 * query = session
									 * .createQuery("select count(*) from Farmer where status=1 and seasonCode=:seasonCode and statusCode=0"
									 * );
									 * 
									 * query.setParameter("status",
									 * ESETxnStatus.SUCCESS.ordinal());
									 * query.setParameter("seasonCode",
									 * currentSeason);
									 * 
									 * Integer val = ((Long)
									 * query.uniqueResult()).intValue();
									 * session.flush(); session.close(); return
									 * val;
									 */

		Session session = getSessionFactory().openSession();
		Query query = session
				.createQuery("select count(*) from Farmer where status in (0,1) and statusCode=:statusCode ");

		// query.setParameter("seasonCode", currentSeason);
		query.setParameter("statusCode", Integer.valueOf(ESETxnStatus.SUCCESS.ordinal()));

		Integer val = Integer.valueOf(((Long) query.uniqueResult()).intValue());
		session.flush();
		session.close();
		return val;
	}

	@Override
	public Integer findFarmerCountByMonth(Date sDate, Date eDate) {

		Session session = getSessionFactory().openSession();
		Query query = session
				.createQuery("select count(*) from Farmer where createdDate BETWEEN :startDate AND :endDate");
		query.setParameter("startDate", sDate).setParameter("endDate", eDate);

		Integer val = ((Long) query.uniqueResult()).intValue();
		session.flush();
		session.close();
		return val;
	}

	@Override
	public String findFarmTotalProposedAreaCount() {
		// TODO Auto-generated method stub
		Session session = getSessionFactory().getCurrentSession();
		return ((String) session
				.createQuery(
						"select COALESCE(sum(fm.proposedPlantingArea),'0') from Farmer f join f.farms fm where fm.status=1 and f.status =1")
				.uniqueResult());
	}

	@Override
	public String findFarmTotalLandAreaCount(Date sDate, Date eDate, Long id) {

		Session session = getSessionFactory().getCurrentSession();
		String q = "select COALESCE(sum(fm.totalLandHolding),'0') from Farmer f join f.farms fm where fm.status=1 and f.statusCode = 0 and f.status=1 and f.createdDate BETWEEN :startDate AND :endDate  ";
		if (id > 0) {
			/*q += " and f.farmer.exporters in (" + id +")";*/
		}
		return ((String) session.createQuery(q).setParameter("startDate", sDate).setParameter("endDate", eDate)
				.uniqueResult());
	}

	@Override
	public Integer findFarmCountByMonth(Date sDate, Date eDate) {

		Session session = getSessionFactory().openSession();
		Query query = session.createQuery(
				"select count(*) from Farm f where f.farmer.createdDate BETWEEN :startDate AND :endDate and    f.status=1  ");
		query.setParameter("startDate", sDate).setParameter("endDate", eDate);

		Integer val = ((Long) query.uniqueResult()).intValue();
		session.flush();
		session.close();
		return val;
	}

	@Override
	public Integer findFarmsCount(Date sDate, Date eDate, Long id) {

		Session session = getSessionFactory().getCurrentSession();
		String q = "select count(*) from Farm f WHERE f.status=1 and f.farmer.status=1 and f.createdDate BETWEEN :startDate AND :endDate";
		if (id > 0) {
			/*q += " and f.farmer.exporters in (" + id +")";*/
		}
		Long count = ((Long) session.createQuery(q).setParameter("startDate", sDate).setParameter("endDate", eDate)
				.uniqueResult());
		return count == null ? 0 : count.intValue();
	}

	@Override
	public Integer findCropCount() {

		Session session = getSessionFactory().getCurrentSession();
		return ((Long) session.createQuery("select count(*) from ProcurementProduct").uniqueResult()).intValue();
	}

	@Override
	public List<Object> listFarmerCountByGroup() {

		return list("select count(*) as count,f.samithi.name as group from Farmer f GROUP BY samithi");
	}

	@Override
	public List<Object> listFarmerCountByBranch() {

		return list("select count(f.id) as count,f.branchId as name from Farmer f GROUP BY f.branchId");
	}

	@Override
	public List<Object> listTotalFarmAcreByVillage() {

		return list(
				"SELECT SUM(fa.totalLandHolding) as Total,SUM(fa.proposedPlantingArea) as Proposed,f.village.name FROM Farm fa  INNER JOIN fa.farmer f  where  fa.status=1   GROUP BY f.village.name");
	}

	@Override
	public List<Object> listTotalFarmAcreByBranch() {

		return list(
				"SELECT SUM(fa.totalLandHolding) as Total,SUM(fa.proposedPlantingArea) as Proposed,f.branchId FROM Farm fa  INNER JOIN fa.farmer f  where  fa.status=1  GROUP BY f.branchId");
	}

	@Override
	public List<String> findFarmerIdsByfarmCode(List<String> farmCodes) {
		List<String> list = null;
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(
				"SELECT f.farmer.farmerId from Farm f where f.farmCode in :fCode and f.farmer.status=1 and f.farmer.statusCode=0");
		query.setParameterList("fCode", farmCodes);
		if (!ObjectUtil.isEmpty(query) && query.list().size() > 0) {
			list = query.list();
		}
		return list;

	}

	public HarvestSeason findHarvestSeasonByCode(String code) {

		return (HarvestSeason) find("From HarvestSeason hs where hs.code=?", code);
	}

	@Override
	public List<Object[]> listSeasonCodeAndName() {

		return list("SELECT s.code,s.name FROM HarvestSeason s ORDER BY s.name ASC");
	}

	@Override
	public long findFarmerCountByFPO(Long val) {

		long totalFarmers = 0;
		Session sessions = getSessionFactory().openSession();
		String queryString = "SELECT COUNT( f.id ) FROM farmer f WHERE f.SAMITHI_ID =:codeVal and f.STATUS=1 ";
		Query query = sessions.createSQLQuery(queryString);
		query.setParameter("codeVal", val);
		int count = Integer.valueOf(query.list().get(0).toString());
		if (count > 0) {
			totalFarmers = count;
		}
		sessions.flush();
		sessions.close();
		return totalFarmers;
	}

	@Override
	public List<Object[]> farmersByBranch() {
		Session session = getSessionFactory().openSession();
		String queryString = "select count(f.ID),bm.`NAME`,bm.BRANCH_ID from farmer f inner join branch_master bm on bm.BRANCH_ID = f.BRANCH_ID where f.STATUS_CODE = 0 and f.status=1 group by bm.BRANCH_ID ORDER BY COUNT(f.ID) DESC";
		SQLQuery query = session.createSQLQuery(queryString);
		List<Object[]> result = query.list();
		session.flush();
		session.close();
		return result;
	}

	@Override
	public List<Object[]> farmersByCountry(String selectedBranch) {

		HttpSession httpSession = ReflectUtil.getCurrentHttpSession();
		HttpServletRequest request = ReflectUtil.getCurrentHttpRequest();
		String tenantId = !StringUtil.isEmpty(ISecurityFilter.DEFAULT_TENANT_ID) ? ISecurityFilter.DEFAULT_TENANT_ID
				: "";
		if (!ObjectUtil.isEmpty(request)) {
			tenantId = !StringUtil.isEmpty((String) request.getSession().getAttribute(ISecurityFilter.TENANT_ID))
					? (String) request.getSession().getAttribute(ISecurityFilter.TENANT_ID) : "";
		}
		Session session = getSessionFactory().getCurrentSession();
		Query query;
		if (!StringUtil.isEmpty(selectedBranch)) {
			String hqlString = "SELECT count(f.id),ctry.name,ctry.code,ctry.branchId from Farmer f inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s inner join s.country ctry where f.branchId = :branch and f.statusCode = 0 and f.status=1";
			if (!StringUtil.isEmpty(tenantId) && tenantId.equalsIgnoreCase(ESESystem.WELSPUN_TENANT_ID))
				hqlString += " AND f.status=1";
			hqlString += " group by ctry.code";
			hqlString += " ORDER BY COUNT(f.id) DESC";
			query = session.createQuery(hqlString);
			query.setParameter("branch", selectedBranch);

		} else {
			String hqlString = "SELECT count(f.id),ctry.name,ctry.code,ctry.branchId from Farmer f inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s inner join s.country ctry where f.statusCode = 0 and f.status=1 ";
			if (!StringUtil.isEmpty(tenantId) && tenantId.equalsIgnoreCase(ESESystem.WELSPUN_TENANT_ID))
				hqlString += " AND f.status=1";
			hqlString += " group by ctry.code";
			hqlString += " ORDER BY COUNT(f.id) DESC";
			query = session.createQuery(hqlString);
		}
		List<Object[]> result = query.list();
		return result;

	}

	@Override
	public List<Object[]> farmersByState(String selectedBranch) {

		Session session = getSessionFactory().getCurrentSession();
		Query query;
		if (!StringUtil.isEmpty(selectedBranch)) {
			String hqlString = "SELECT count(f.id),s.name,s.code,ctry.code,s.id from Farmer f inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s inner join s.country ctry where f.branchId = :branch and f.statusCode = 0 and f.status=1   group by s.code";
			hqlString += " ORDER BY COUNT(f.id) DESC";
			query = session.createQuery(hqlString);
			query.setParameter("branch", selectedBranch);
		} else {
			String hqlString = "SELECT count(f.id),s.name,s.code,ctry.code,s.id from Farmer f inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s inner join s.country ctry where  f.statusCode = 0 and f.status=1 group by s.code";
			hqlString += " ORDER BY COUNT(f.id) DESC";
			query = session.createQuery(hqlString);
		}
		List<Object[]> result = query.list();
		return result;
	}

	@Override
	public List<Object[]> farmersByLocality(String selectedBranch) {
		Session session = getSessionFactory().getCurrentSession();
		Query query;
		if (!StringUtil.isEmpty(selectedBranch)) {
			String hqlString = "SELECT count(f.id),ld.name,ld.code,s.code from Farmer f inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s  where f.branchId = :branch and f.statusCode = 0 and f.status=1  group by ld.code";
			hqlString += " ORDER BY COUNT(f.id) DESC";
			query = session.createQuery(hqlString);
			query.setParameter("branch", selectedBranch);
		} else {
			String hqlString = "SELECT count(f.id),ld.name,ld.code,s.code from Farmer f inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s where f.statusCode = 0 and f.status=1 group by ld.code";
			hqlString += " ORDER BY COUNT(f.id) DESC";
			query = session.createQuery(hqlString);
		}
		List<Object[]> result = query.list();
		return result;
	}

	@Override
	public List<Object[]> farmersByMunicipality(String selectedBranch) {

		Session session = getSessionFactory().getCurrentSession();
		Query query;
		if (!StringUtil.isEmpty(selectedBranch)) {
			String hqlString = "SELECT count(f.id),c.name,c.code,ld.code from Farmer f inner join f.village v  inner join v.city c inner join c.locality ld  where f.branchId = :branch and f.statusCode = 0 and f.status=1  group by c.code";
			hqlString += " ORDER BY COUNT(f.id) DESC";
			query = session.createQuery(hqlString);
			query.setParameter("branch", selectedBranch);
		} else {
			String hqlString = "SELECT count(f.id),c.name,c.code,ld.code from Farmer f inner join f.village v  inner join v.city c inner join c.locality ld where f.statusCode = 0 and f.status=1 group by c.code";
			hqlString += " ORDER BY COUNT(f.id) DESC";
			query = session.createQuery(hqlString);
		}
		List<Object[]> result = query.list();
		return result;
	}

	@Override
	public List<Object[]> farmersByGramPanchayat(String selectedBranch) {
		Session session = getSessionFactory().getCurrentSession();
		Query query;
		if (!StringUtil.isEmpty(selectedBranch)) {
			String hqlString = "SELECT count(f.id),gp.name,gp.code,c.code from Farmer f inner join f.village v  inner join v.gramPanchayat gp inner join gp.city c  where f.branchId = :branch and f.statusCode = 0 and f.status=1  group by gp.code";
			hqlString += " ORDER BY COUNT(f.id) DESC";
			query = session.createQuery(hqlString);
			query.setParameter("branch", selectedBranch);
		} else {
			String hqlString = "SELECT count(f.id),gp.name,gp.code,c.code from Farmer f inner join f.village v  inner join v.gramPanchayat gp inner join gp.city c where f.statusCode = 0 and f.status=1  group by gp.code";
			hqlString += " ORDER BY COUNT(f.id) DESC";
			query = session.createQuery(hqlString);
		}
		List<Object[]> result = query.list();
		return result;
	}

	@Override
	public List<Object[]> farmersByVillageWithGramPanchayat(String selectedBranch) {
		Session session = getSessionFactory().getCurrentSession();
		Query query;
		if (!StringUtil.isEmpty(selectedBranch)) {
			String hqlString = "SELECT count(f.id),v.name,v.code,gp.code from Farmer f inner join f.village v  inner join v.gramPanchayat gp where f.branchId = :branch and f.statusCode = 0 and f.status=1  group by v.code";
			hqlString += " ORDER BY COUNT(f.id) DESC";
			query = session.createQuery(hqlString);
			query.setParameter("branch", selectedBranch);
		} else {
			String hqlString = "SELECT count(f.id),v.name,v.code,gp.code from Farmer f inner join f.village v  inner join v.gramPanchayat gp where f.statusCode = 0 and f.status=1 group by v.code";
			hqlString += " ORDER BY COUNT(f.id) DESC";
			query = session.createQuery(hqlString);
		}
		List<Object[]> result = query.list();
		return result;
	}

	@Override
	public List<Object[]> farmersByVillageWithOutGramPanchayat(String empty) {
		Session session = getSessionFactory().getCurrentSession();
		Query query;
		if (!StringUtil.isEmpty(empty)) {
			String hqlString = "SELECT count(f.id) ,v.name,v.code,c.code from Farmer f inner join f.village v  inner join v.city c where f.branchId = :branch and f.statusCode = 0 and f.status=1  group by v.code ";
			hqlString += " ORDER BY COUNT(f.id) DESC";
			query = session.createQuery(hqlString);
			query.setParameter("branch", empty);
		} else {
			String hqlString = "SELECT count(f.id) ,v.name,v.code,c.code from Farmer f inner join f.village v  inner join v.city c where f.statusCode = 0 and f.status=1 group by v.code ";
			hqlString += " ORDER BY COUNT(f.id) DESC";
			query = session.createQuery(hqlString);
		}
		List<Object[]> result = query.list();
		return result;
	}

	@Override
	public List<Object[]> farmerDetailsByVillage(String empty) {
		Session session = getSessionFactory().getCurrentSession();
		Query query;
		if (!StringUtil.isEmpty(empty)) {
			String hqlString = "SELECT count(f.id) as totalFarmers, (select count(f.id) from Farmer f where f.status = 1 and f.village.id = v.id) as active,(select count(f.id) from Farmer f where f.status = 0 and f.village.id = v.id) as inActive,(select count(f.id) from Farmer f where  f.status = 1 and f.village.id = v.id) as certified,(select count(f.id) from Farmer f where  f.status = 1 and f.village.id = v.id) as nonCertified,(select v1.code from Village v1 where f.village.id = v1.id) as villageCode  from Farmer f inner join f.village v where f.branchId = :branch and f.statusCode = 0 and f.status=1  group by f.village.id";
			hqlString += " ORDER BY COUNT(f.id) DESC";
			query = session.createQuery(hqlString);
			query.setParameter("branch", empty);
		} else {
			String hqlString = "SELECT count(f.id) as totalFarmers, (select count(f.id) from Farmer f where f.status = 1 and f.village.id = v.id) as active,(select count(f.id) from Farmer f where f.status = 0 and f.village.id = v.id) as inActive,(select count(f.id) from Farmer f where f.status = 1 and f.village.id = v.id) as certified,(select count(f.id) from Farmer f where f.status = 1 and f.village.id = v.id) as nonCertified,(select v1.code from Village v1 where f.village.id = v1.id) as villageCode  from Farmer f inner join f.village v where f.statusCode = 0 and f.status=1 group by f.village.id";
			hqlString += " ORDER BY COUNT(f.id) DESC";
			query = session.createQuery(hqlString);
		}
		List<Object[]> result = query.list();
		return result;
	}

	@Override
	public void updateFarmerStatusByFarmerId(String farmerId) {

		Session session = getSessionFactory().openSession();

		Query query = session.createSQLQuery(
				"UPDATE farmer fm  LEFT JOIN farm f on f.FARMER_ID = fm.ID LEFT JOIN farm_crops fc on fc.FARM_ID = f.ID  SET  fm.STATUS=:status,fm.STATUS_MSG='FARMER DELETED',fm.REVISION_NO=:revNo,fm.STATUS_CODE=:statCode,f.STATUS=:status,f.REVISION_NO=:revNo,fc.REVISION_NO=:revNo,fc.STATUS=:status WHERE fm.FARMER_ID = :farmerId");
		query.setParameter("farmerId", farmerId);
		query.setParameter("status", Farmer.Status.DELETED.ordinal());
		query.setParameter("statCode", ESETxnStatus.DELETED.ordinal());
		query.setParameter("revNo", DateUtil.getRevisionNumber());
		int result = query.executeUpdate();
		session.flush();
		session.close();

	}

	@Override
	public List<DynamicSectionConfig> listDynamicSections() {
		return list("FROM DynamicSectionConfig dsc order by dsc.id asc ");
	}

	@Override
	public List<Object[]> estimatedYield(String locationCode) {
		char code = 0;
		String queryString;
		if (!StringUtil.isEmpty(locationCode)) {
			code = locationCode.charAt(0);
		}

		switch (code) {
		case 'C':
			queryString = "SELECT pp.name,pp.code,fc.EST_YIELD AS estimated FROM farm fa INNER JOIN farm_crops fc ON fc.FARM_ID = fa.ID inner join procurement_variety pv on pv.ID = fc.PROCUREMENT_CROPS_VARIETY_ID inner join procurement_product pp on pp.ID = pv.PROCUREMENT_PRODUCT_ID inner join farmer f on f.ID = fa.FARMER_ID inner join village v on v.ID = f.VILLAGE_ID inner join city c on c.id = v.CITY_ID inner join location_detail ld on ld.ID = c.LOCATION_ID inner join state s on s.ID = ld.STATE_ID inner join country ctry on ctry.ID = s.COUNTRY_ID where ctry.`CODE` = '"
					+ locationCode + "'  and  f.STATUS_CODE = 0 and fa.status=1 and f.status=1 ";
			break;
		case 'S':
			queryString = "SELECT pp.name,pp.code,fc.EST_YIELD AS estimated FROM farm fa INNER JOIN farm_crops fc ON fc.FARM_ID = fa.ID inner join procurement_variety pv on pv.ID = fc.PROCUREMENT_CROPS_VARIETY_ID inner join procurement_product pp on pp.ID = pv.PROCUREMENT_PRODUCT_ID inner join farmer f on f.ID = fa.FARMER_ID inner join village v on v.ID = f.VILLAGE_ID inner join city c on c.id = v.CITY_ID inner join location_detail ld on ld.ID = c.LOCATION_ID inner join state s on s.ID = ld.STATE_ID where s.`CODE` = '"
					+ locationCode + "' and  f.STATUS_CODE = 0 and fa.status=1 and f.status=1";
			break;
		case 'D':
			queryString = "SELECT pp.name,pp.code,fc.EST_YIELD AS estimated FROM farm fa INNER JOIN farm_crops fc ON fc.FARM_ID = fa.ID inner join procurement_variety pv on pv.ID = fc.PROCUREMENT_CROPS_VARIETY_ID inner join procurement_product pp on pp.ID = pv.PROCUREMENT_PRODUCT_ID inner join farmer f on f.ID = fa.FARMER_ID inner join village v on v.ID = f.VILLAGE_ID inner join city c on c.id = v.CITY_ID inner join location_detail ld on ld.ID = c.LOCATION_ID where ld.`CODE` = '"
					+ locationCode + "'  and  f.STATUS_CODE = 0 and fa.status=1 and f.status=1 ";
			break;
		case 'M':
			queryString = "SELECT pp.name,pp.code,fc.EST_YIELD AS estimated FROM farm fa INNER JOIN farm_crops fc ON fc.FARM_ID = fa.ID inner join procurement_variety pv on pv.ID = fc.PROCUREMENT_CROPS_VARIETY_ID inner join procurement_product pp on pp.ID = pv.PROCUREMENT_PRODUCT_ID inner join farmer f on f.ID = fa.FARMER_ID inner join village v on v.ID = f.VILLAGE_ID inner join city c on c.id = v.CITY_ID where c.`CODE` = '"
					+ locationCode + "'  and  f.STATUS_CODE = 0 and fa.status=1 and f.status=1 ";
			break;
		case 'V':
			queryString = "SELECT pp.name,pp.code,fc.EST_YIELD AS estimated FROM farm fa INNER JOIN farm_crops fc ON fc.FARM_ID = fa.ID inner join procurement_variety pv on pv.ID = fc.PROCUREMENT_CROPS_VARIETY_ID inner join procurement_product pp on pp.ID = pv.PROCUREMENT_PRODUCT_ID inner join farmer f on f.ID = fa.FARMER_ID inner join village v on v.ID = f.VILLAGE_ID where v.`CODE` = '"
					+ locationCode + "'  and  f.STATUS_CODE = 0 and fa.status=1 and f.status=1 ";
			break;
		default:
			if (!StringUtil.isEmpty(locationCode)) {
				queryString = "SELECT pp.name,pp.code,fc.EST_YIELD AS estimated FROM farm fa INNER JOIN farm_crops fc ON fc.FARM_ID = fa.ID inner join procurement_variety pv on pv.ID = fc.PROCUREMENT_CROPS_VARIETY_ID inner join procurement_product pp on pp.ID = pv.PROCUREMENT_PRODUCT_ID where fc.BRANCH_ID = '"
						+ locationCode + "'and fc.status=1";

			} else {
				queryString = "SELECT pp.name,pp.code,fc.EST_YIELD AS estimated FROM farm fa INNER JOIN farm_crops fc ON fc.FARM_ID = fa.ID INNER JOIN farmer f on f.id=fa.FARMER_ID inner join procurement_variety pv on pv.ID = fc.PROCUREMENT_CROPS_VARIETY_ID inner join procurement_product pp on pp.ID = pv.PROCUREMENT_PRODUCT_ID where f.status = 1 AND fa.STATUS = 1 AND fc.STATUS = 1 AND f.status_Code = 0";
			}
			break;
		}

		Session session = getSessionFactory().openSession();
		SQLQuery query = session.createSQLQuery(queryString);
		List<Object[]> result = query.list();
		session.flush();
		session.close();
		return result;
	}

	@Override
	public List<Object[]> actualYield(String locationCode) {
		char code = 0;
		String queryString;
		if (!StringUtil.isEmpty(locationCode)) {
			code = locationCode.charAt(0);
		}

		switch (code) {
		case 'C':
			queryString = "SELECT pp.NAME,pp.CODE,chd.QTY  AS actual FROM farm fa INNER JOIN crop_harvest ch ON ch.FARM_CODE = fa.FARM_CODE INNER JOIN crop_harvest_details chd ON chd.CROP_HARVEST_ID = ch.ID INNER JOIN procurement_product pp ON pp.ID = chd.CROP inner join farmer f on f.ID = fa.FARMER_ID inner join village v on v.ID = f.VILLAGE_ID inner join city c on c.id = v.CITY_ID inner join location_detail ld on ld.ID = c.LOCATION_ID inner join state s on s.ID = ld.STATE_ID inner join country ctry on ctry.ID = s.COUNTRY_ID where ctry.`CODE` = '"
					+ locationCode + "'  and  f.STATUS_CODE = 0 and fa.status=1 and f.status=1 ";
			break;
		case 'S':
			queryString = "SELECT pp.NAME,pp.CODE,chd.QTY  AS actual FROM farm fa INNER JOIN crop_harvest ch ON ch.FARM_CODE = fa.FARM_CODE INNER JOIN crop_harvest_details chd ON chd.CROP_HARVEST_ID = ch.ID INNER JOIN procurement_product pp ON pp.ID = chd.CROP inner join farmer f on f.ID = fa.FARMER_ID inner join village v on v.ID = f.VILLAGE_ID inner join city c on c.id = v.CITY_ID inner join location_detail ld on ld.ID = c.LOCATION_ID inner join state s on s.ID = ld.STATE_ID where s.`CODE` = '"
					+ locationCode + "' and  f.STATUS_CODE = 0 and fa.status=1 and f.status=1";
			break;
		case 'D':
			queryString = "SELECT pp.NAME,pp.CODE,chd.QTY  AS actual FROM farm fa INNER JOIN crop_harvest ch ON ch.FARM_CODE = fa.FARM_CODE INNER JOIN crop_harvest_details chd ON chd.CROP_HARVEST_ID = ch.ID INNER JOIN procurement_product pp ON pp.ID = chd.CROP inner join farmer f on f.ID = fa.FARMER_ID inner join village v on v.ID = f.VILLAGE_ID inner join city c on c.id = v.CITY_ID inner join location_detail ld on ld.ID = c.LOCATION_ID where ld.`CODE` = '"
					+ locationCode + "'  and  f.STATUS_CODE = 0 and fa.status=1 and f.status=1 ";
			break;
		case 'M':
			queryString = "SELECT pp.NAME,pp.CODE,chd.QTY  AS actual FROM farm fa INNER JOIN crop_harvest ch ON ch.FARM_CODE = fa.FARM_CODE INNER JOIN crop_harvest_details chd ON chd.CROP_HARVEST_ID = ch.ID INNER JOIN procurement_product pp ON pp.ID = chd.CROP inner join farmer f on f.ID = fa.FARMER_ID inner join village v on v.ID = f.VILLAGE_ID inner join city c on c.id = v.CITY_ID where c.`CODE` = '"
					+ locationCode + "'  and  f.STATUS_CODE = 0 and fa.status=1 and f.status=1 ";
			break;
		case 'G':
			queryString = "SELECT pp.NAME,pp.CODE,chd.QTY AS actual FROM farm  fa INNER JOIN crop_harvest ch ON ch.FARM_CODE = fa.FARM_CODE INNER JOIN crop_harvest_details chd ON chd.CROP_HARVEST_ID = ch.ID INNER JOIN procurement_product pp ON pp.ID = chd.CROP INNER JOIN farmer f ON f.ID = fa.FARMER_ID INNER JOIN village v ON v.ID = f.VILLAGE_ID INNER JOIN gram_panchayat gp ON gp.ID = v.GRAM_PANCHAYAT_ID INNER JOIN city c ON c.id = gp.CITY_ID WHERE gp.`CODE` = '"
					+ locationCode + "' AND f.STATUS_CODE = 0 and fa.status=1 and f.status=1";
			break;
		case 'V':
			queryString = "SELECT pp.NAME,pp.CODE,chd.QTY  AS actual FROM farm fa INNER JOIN crop_harvest ch ON ch.FARM_CODE = fa.FARM_CODE INNER JOIN crop_harvest_details chd ON chd.CROP_HARVEST_ID = ch.ID INNER JOIN procurement_product pp ON pp.ID = chd.CROP inner join farmer f on f.ID = fa.FARMER_ID inner join village v on v.ID = f.VILLAGE_ID where v.`CODE` = '"
					+ locationCode + "'  and  f.STATUS_CODE = 0 and fa.status=1 and f.status=1 ";
			break;
		default:
			if (!StringUtil.isEmpty(locationCode)) {
				queryString = "SELECT pp.NAME,pp.CODE,chd.QTY  AS actual FROM farmer f INNER JOIN crop_harvest ch ON ch.FARMER_ID = f.FARMER_ID INNER JOIN crop_harvest_details chd ON chd.CROP_HARVEST_ID = ch.ID INNER JOIN procurement_product pp ON pp.ID = chd.CROP where ch.BRANCH_ID = '"
						+ locationCode + "'";

			} else {
				queryString = "SELECT pp.NAME,pp.CODE,chd.QTY  AS actual FROM farm fa INNER JOIN crop_harvest ch ON ch.FARM_CODE = fa.FARM_CODE INNER JOIN crop_harvest_details chd ON chd.CROP_HARVEST_ID = ch.ID INNER JOIN procurement_product pp ON pp.ID = chd.CROP";
			}
			break;
		}

		Session session = getSessionFactory().openSession();
		SQLQuery query = session.createSQLQuery(queryString);
		List<Object[]> result = query.list();
		session.flush();
		session.close();
		return result;

	}

	@Override
	public List<Object[]> populateFarmerLocationCropChart(String codeForCropChart) {
		HttpSession httpSession = ReflectUtil.getCurrentHttpSession();
		HttpServletRequest request = ReflectUtil.getCurrentHttpRequest();
		String tenantId = !StringUtil.isEmpty(ISecurityFilter.DEFAULT_TENANT_ID) ? ISecurityFilter.DEFAULT_TENANT_ID
				: "";
		if (!ObjectUtil.isEmpty(request)) {
			tenantId = !StringUtil.isEmpty((String) request.getSession().getAttribute(ISecurityFilter.TENANT_ID))
					? (String) request.getSession().getAttribute(ISecurityFilter.TENANT_ID) : "";
		}
		char code = 0;
		if (!StringUtil.isEmpty(codeForCropChart)) {
			code = codeForCropChart.charAt(0);
		}
		String hqlString = null;
		Session session = getSessionFactory().getCurrentSession();
		Query query;

		/*
		 * switch (code) { case 'C': hqlString =
		 * "SELECT count(fa.id),sum(famcrps.cultivationArea) as CULTIVATION_AREA,pp.name,pp.code  from Farmer f inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s inner join s.country ctry inner join f.farms fa inner join fa.farmCrops famcrps inner join famcrps.procurementVariety pv inner join pv.procurementProduct pp where ctry.code = :branchId and f.statusCode = 0 and fa.status=1 and f.status=1 group by pp.code ORDER BY CULTIVATION_AREA desc"
		 * ; break; case 'S': hqlString =
		 * "SELECT count(fa.id),sum(famcrps.cultivationArea) as CULTIVATION_AREA,pp.name,pp.code  from Farmer f inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s inner join s.country ctry inner join f.farms fa inner join fa.farmCrops famcrps inner join famcrps.procurementVariety pv inner join pv.procurementProduct pp where s.code = :branchId and f.statusCode = 0 and fa.status=1 and f.status=1 group by pp.code ORDER BY CULTIVATION_AREA desc"
		 * ; break; case 'D': hqlString =
		 * "SELECT count(fa.id),sum(famcrps.cultivationArea) as CULTIVATION_AREA,pp.name,pp.code  from Farmer f inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s inner join s.country ctry inner join f.farms fa  inner join fa.farmCrops famcrps inner join famcrps.procurementVariety pv inner join pv.procurementProduct pp where ld.code = :branchId and f.statusCode = 0 and fa.status=1 and f.status=1 group by pp.code ORDER BY CULTIVATION_AREA desc"
		 * ; break; case 'M': hqlString =
		 * "SELECT count(fa.id),sum(famcrps.cultivationArea) as CULTIVATION_AREA,pp.name,pp.code  from Farmer f inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s inner join s.country ctry inner join f.farms fa  inner join fa.farmCrops famcrps inner join famcrps.procurementVariety pv inner join pv.procurementProduct pp where c.code = :branchId and f.statusCode = 0 and fa.status=1 and f.status=1 group by pp.code ORDER BY CULTIVATION_AREA desc"
		 * ; break; case 'G': hqlString =
		 * "SELECT count(fa.id),sum(famcrps.cultivationArea) as CULTIVATION_AREA,pp.name,pp.code  from Farmer f inner join f.village v  inner join v.gramPanchayat gp inner join gp.city c inner join c.locality ld inner join ld.state s inner join s.country ctry inner join f.farms fa  inner join fa.farmCrops famcrps inner join famcrps.procurementVariety pv inner join pv.procurementProduct pp where gp.code = :branchId and f.statusCode = 0 and fa.status=1 and f.status=1 group by pp.code ORDER BY CULTIVATION_AREA desc"
		 * ; break; case 'V': hqlString =
		 * "SELECT count(fa.id),sum(famcrps.cultivationArea) as CULTIVATION_AREA,pp.name,pp.code  from Farmer f inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s inner join s.country ctry inner join f.farms fa  inner join fa.farmCrops famcrps inner join famcrps.procurementVariety pv inner join pv.procurementProduct pp where v.code = :branchId and f.statusCode = 0 and fa.status=1 and f.status=1 group by pp.code ORDER BY CULTIVATION_AREA desc"
		 * ; break; default: if (!StringUtil.isEmpty(codeForCropChart)) {
		 * hqlString =
		 * "SELECT count(fa.id),sum(famcrps.cultivationArea) as CULTIVATION_AREA,pp.name,pp.code  from Farmer f inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s inner join s.country ctry inner join f.farms fa  inner join fa.farmCrops famcrps inner join famcrps.procurementVariety pv inner join pv.procurementProduct pp where f.branchId = :branchId and f.statusCode = 0 and fa.status=1 and f.status=1 group by pp.code ORDER BY CULTIVATION_AREA desc"
		 * ; } else { hqlString =
		 * "SELECT count(fa.id),sum(famcrps.cultivationArea) as CULTIVATION_AREA,pp.name,pp.code  from Farmer f inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s inner join s.country ctry inner join f.farms fa  inner join fa.farmCrops famcrps inner join famcrps.procurementVariety pv inner join pv.procurementProduct pp where f.statusCode = 0 and fa.status=1 and f.status=1"
		 * ; if (!StringUtil.isEmpty(tenantId) &&
		 * tenantId.equalsIgnoreCase(ESESystem.WELSPUN_TENANT_ID)) hqlString +=
		 * " AND f.status=1"; hqlString +=
		 * " group by pp.code ORDER BY CULTIVATION_AREA desc"; } break; }
		 */

		query = session.createQuery(hqlString);
		if (!StringUtil.isEmpty(codeForCropChart)) {
			query.setParameter("branchId", codeForCropChart);
		}

		List<Object[]> result = query.list();
		return result;
	}

	@Override
	public List<Object[]> getFarmDetailsAndProposedPlantingArea(String locationLevel1, String selectedBranch,
			String gramPanchayatEnable) {
		HttpSession httpSession = ReflectUtil.getCurrentHttpSession();
		HttpServletRequest request = ReflectUtil.getCurrentHttpRequest();
		String tenantId = !StringUtil.isEmpty(ISecurityFilter.DEFAULT_TENANT_ID) ? ISecurityFilter.DEFAULT_TENANT_ID
				: "";
		if (!ObjectUtil.isEmpty(request)) {
			tenantId = !StringUtil.isEmpty((String) request.getSession().getAttribute(ISecurityFilter.TENANT_ID))
					? (String) request.getSession().getAttribute(ISecurityFilter.TENANT_ID) : "";
		}
		String locationLevel = String.valueOf(locationLevel1.charAt(0));
		String flag = "enable";
		if (!locationLevel.equals("B")) {
			Session session = getSessionFactory().getCurrentSession();
			Query query;
			String hqlString = null;
			// if (!StringUtil.isEmpty(selectedBranch)) {

			switch (locationLevel) {
			case "C":
				if (gramPanchayatEnable.equalsIgnoreCase("1")) {
					hqlString = "select count(fa.id),sum(fa.totalLandHolding) as TOTAL_LAND_HOLDING,s.name from Farm fa inner join fa.farmer f  inner join f.village v inner join v.gramPanchayat gp inner join gp.city c inner join c.locality ld inner join ld.state s inner join s.country ctry where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fa.status=1 and ctry.code = :locationCode GROUP BY s.code ORDER BY TOTAL_LAND_HOLDING desc";
				} else {
					hqlString = "select count(fa.id),sum(fa.totalLandHolding) as TOTAL_LAND_HOLDING,s.name from Farm fa inner join fa.farmer f  inner join f.village v inner join v.city c inner join c.locality ld inner join ld.state s inner join s.country ctry where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fa.status=1 and ctry.code = :locationCode GROUP BY s.code ORDER BY TOTAL_LAND_HOLDING desc";
				}
				break;
			case "S":
				if (gramPanchayatEnable.equalsIgnoreCase("1")) {
					hqlString = "select count(fa.id),sum(fa.totalLandHolding) as TOTAL_LAND_HOLDING,ld.name from Farm fa inner join fa.farmer f  inner join f.village v inner join v.gramPanchayat gp inner join gp.city c inner join c.locality ld inner join ld.state s where f.branchId = :branch and f.statusCode = 0 and f.status=1  and fa.status=1 and s.code = :locationCode GROUP BY ld.code ORDER BY TOTAL_LAND_HOLDING desc";
				} else {
					hqlString = "select count(fa.id),sum(fa.totalLandHolding) as TOTAL_LAND_HOLDING,ld.name from Farm fa inner join fa.farmer f  inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s where f.branchId = :branch and f.statusCode = 0 and f.status=1  and fa.status=1 and s.code = :locationCode GROUP BY ld.code ORDER BY TOTAL_LAND_HOLDING desc";
				}
				break;
			case "D":
				if (gramPanchayatEnable.equalsIgnoreCase("1")) {
					hqlString = "select count(fa.id),sum(fa.totalLandHolding) as TOTAL_LAND_HOLDING,c.name from Farm fa inner join fa.farmer f  inner join f.village v inner join v.gramPanchayat gp inner join gp.city c inner join c.locality ld where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fa.status=1 and ld.code = :locationCode GROUP BY c.code ORDER BY TOTAL_LAND_HOLDING desc";
				} else {
					hqlString = "select count(fa.id),sum(fa.totalLandHolding) as TOTAL_LAND_HOLDING,c.name from Farm fa inner join fa.farmer f  inner join f.village v inner join v.city c inner join c.locality ld where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fa.status=1 and ld.code = :locationCode GROUP BY c.code ORDER BY TOTAL_LAND_HOLDING desc";
				}
				break;
			case "M":
				if (gramPanchayatEnable.equalsIgnoreCase("1")) {
					hqlString = "select count(fa.id),sum(fa.totalLandHolding) as TOTAL_LAND_HOLDING,gp.name from Farm fa inner join fa.farmer f  inner join f.village v inner join v.gramPanchayat gp inner join gp.city c where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fa.status=1 and c.code = :locationCode GROUP BY gp.code ORDER BY TOTAL_LAND_HOLDING desc";
				} else {
					hqlString = "select count(fa.id),sum(fa.totalLandHolding) as TOTAL_LAND_HOLDING,v.name from Farm fa inner join fa.farmer f  inner join f.village v inner join v.city c where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fa.status=1 and c.code = :locationCode GROUP BY v.code ORDER BY TOTAL_LAND_HOLDING desc";
				}
				break;
			case "G":
				if (gramPanchayatEnable.equalsIgnoreCase("1")) {
					hqlString = "select count(fa.id),sum(fa.totalLandHolding) as TOTAL_LAND_HOLDING,v.name from Farm fa inner join fa.farmer f  inner join f.village v inner join v.gramPanchayat gp where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fa.status=1 and gp.code = :locationCode GROUP BY v.code ORDER BY TOTAL_LAND_HOLDING desc";
				}
				break;
			case "V":
				hqlString = "select count(fa.id),sum(fa.totalLandHolding) as TOTAL_LAND_HOLDING,f.firstName from Farm fa inner join fa.farmer f  inner join f.village v  where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fa.status=1 and v.code = :locationCode GROUP BY f.id ORDER BY TOTAL_LAND_HOLDING desc";
				break;
			default:
				if (gramPanchayatEnable.equalsIgnoreCase("1")) {
					hqlString = "select count(fa.id),sum(fa.totalLandHolding) as TOTAL_LAND_HOLDING,ctry.name from Farm fa inner join fa.farmer f  inner join f.village v inner join v.gramPanchayat gp inner join gp.city c inner join c.locality ld inner join ld.state s inner join s.country ctry where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fa.status=1 GROUP BY ctry.name ORDER BY TOTAL_LAND_HOLDING desc";
				} else {
					hqlString = "select count(fa.id),sum(fa.totalLandHolding) as TOTAL_LAND_HOLDING,ctry.name from Farm fa inner join fa.farmer f  inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s inner join s.country ctry where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fa.status=1";
					if (!StringUtil.isEmpty(tenantId) && tenantId.equalsIgnoreCase(ESESystem.WELSPUN_TENANT_ID))
						hqlString += " AND f.status=1";
					hqlString += " GROUP BY ctry.name ORDER BY TOTAL_LAND_HOLDING desc";

				}
				flag = "disable";
				break;
			}

			query = session.createQuery(hqlString);
			query.setParameter("branch", selectedBranch);
			if (!locationLevel1.equalsIgnoreCase("first_level_Branch_Login") && flag.equalsIgnoreCase("enable")) {
				query.setParameter("locationCode", locationLevel1);
			}

			List<Object[]> result = query.list();
			return result;
		} else {
			Session session = getSessionFactory().openSession();
			String queryString = "select count(fa.id),sum(fa.TOTAL_LAND_HOLDING) as TOTAL_LAND_HOLDING,bm.name,bm.BRANCH_ID from farm fa inner join farmer f on f.ID = fa.FARMER_ID  inner join branch_master bm on bm.BRANCH_ID = f.BRANCH_ID where f.STATUS_CODE = 0 and fa.status=1 and f.status=1 GROUP BY bm.`NAME` ORDER BY TOTAL_LAND_HOLDING desc";
			SQLQuery query = session.createSQLQuery(queryString);
			List<Object[]> result = query.list();
			session.flush();
			session.close();
			return result;
		}

	}

	@Override
	public List<Object[]> getFarmDetailsAndCultivationArea(String locationLevel1, String selectedBranch,
			String gramPanchayatEnable) {
		String locationLevel = String.valueOf(locationLevel1.charAt(0));
		String flag = "enable";
		if (!locationLevel.equals("B")) {
			Session session = getSessionFactory().getCurrentSession();
			Query query;
			String hqlString = null;
			// if (!StringUtil.isEmpty(selectedBranch)) {

			switch (locationLevel) {
			case "C":
				if (gramPanchayatEnable.equalsIgnoreCase("1")) {
					hqlString = "select count(fa.id),sum(fc.cultivationArea) as TOTAL_LAND_HOLDING,s.name from FarmCrops fc inner join fc.farm fa inner join fa.farmer f  inner join f.village v inner join v.gramPanchayat gp inner join gp.city c inner join c.locality ld inner join ld.state s inner join s.country ctry where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fc.status=1 and fa.status=1 and ctry.code = :locationCode GROUP BY s.code ORDER BY TOTAL_LAND_HOLDING desc";
				} else {
					hqlString = "select count(fa.id),sum(fc.cultivationArea) as TOTAL_LAND_HOLDING,s.name from FarmCrops fc inner join fc.farm fa inner join fa.farmer f  inner join f.village v inner join v.city c inner join c.locality ld inner join ld.state s inner join s.country ctry where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fc.status=1 and fa.status=1 and ctry.code = :locationCode GROUP BY s.code ORDER BY TOTAL_LAND_HOLDING desc";
				}
				break;
			case "S":
				if (gramPanchayatEnable.equalsIgnoreCase("1")) {
					hqlString = "select count(fa.id),sum(fc.cultivationArea) as TOTAL_LAND_HOLDING,ld.name from FarmCrops fc inner join fc.farm fa inner join fa.farmer f inner join f.village v inner join v.gramPanchayat gp inner join gp.city c inner join c.locality ld inner join ld.state s where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fc.status=1 and fa.status=1 and s.code = :locationCode GROUP BY ld.code ORDER BY TOTAL_LAND_HOLDING desc";
				} else {
					hqlString = "select count(fa.id),sum(fc.cultivationArea) as TOTAL_LAND_HOLDING,ld.name from FarmCrops fc inner join fc.farm fa inner join fa.farmer f  inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fc.status=1 and fa.status=1 and s.code = :locationCode GROUP BY ld.code ORDER BY TOTAL_LAND_HOLDING desc";
				}
				break;
			case "D":
				if (gramPanchayatEnable.equalsIgnoreCase("1")) {
					hqlString = "select count(fa.id),sum(fc.cultivationArea) as TOTAL_LAND_HOLDING,c.name from FarmCrops fc inner join fc.farm fa inner join fa.farmer f inner join f.village v inner join v.gramPanchayat gp inner join gp.city c inner join c.locality ld where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fc.status=1 and fa.status=1 and ld.code = :locationCode GROUP BY c.code ORDER BY TOTAL_LAND_HOLDING desc";
				} else {
					hqlString = "select count(fa.id),sum(fc.cultivationArea) as TOTAL_LAND_HOLDING,c.name from FarmCrops fc inner join fc.farm fa inner join fa.farmer f inner join f.village v inner join v.city c inner join c.locality ld where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fc.status=1 and fa.status=1 and ld.code = :locationCode GROUP BY c.code ORDER BY TOTAL_LAND_HOLDING desc";
				}
				break;
			case "M":
				if (gramPanchayatEnable.equalsIgnoreCase("1")) {
					hqlString = "select count(fa.id),sum(fc.cultivationArea) as TOTAL_LAND_HOLDING,gp.name from FarmCrops fc inner join fc.farm fa inner join fa.farmer f inner join f.village v inner join v.gramPanchayat gp inner join gp.city c where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fc.status=1 and fa.status=1 and c.code = :locationCode GROUP BY gp.code ORDER BY TOTAL_LAND_HOLDING desc";
				} else {
					hqlString = "select count(fa.id),sum(fc.cultivationArea) as TOTAL_LAND_HOLDING,v.name AS NAME from FarmCrops fc inner join fc.farm fa inner join fa.farmer f  inner join f.village v inner join v.city c where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fc.status=1 and fa.status=1 and c.code = :locationCode GROUP BY v.code ORDER BY NAME";
				}
				break;
			case "G":
				if (gramPanchayatEnable.equalsIgnoreCase("1")) {
					hqlString = "select count(fa.id),sum(fc.cultivationArea) as TOTAL_LAND_HOLDING,v.name from FarmCrops fc inner join fc.farm fa inner join fa.farmer f  inner join f.village v inner join v.gramPanchayat gp where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fc.status=1 and fa.status=1 and gp.code = :locationCode GROUP BY v.code ORDER BY TOTAL_LAND_HOLDING desc";
				}
				break;
			case "V":
				hqlString = "select count(fa.id),sum(fc.cultivationArea) as TOTAL_LAND_HOLDING,f.firstName from FarmCrops fc inner join fc.farm fa inner join fa.farmer f  inner join f.village v  where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fc.status=1 and fa.status=1 and v.code = :locationCode GROUP BY f.id ORDER BY TOTAL_LAND_HOLDING desc";
				break;
			default:
				if (gramPanchayatEnable.equalsIgnoreCase("1")) {
					hqlString = "select count(fa.id),sum(fc.cultivationArea) as TOTAL_LAND_HOLDING,ctry.name from FarmCrops fc inner join fc.farm fa inner join fa.farmer f  inner join f.village v inner join v.gramPanchayat gp inner join gp.city c inner join c.locality ld inner join ld.state s inner join s.country ctry where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fc.status=1 and fa.status=1 GROUP BY ctry.name ORDER BY TOTAL_LAND_HOLDING desc";
				} else {
					hqlString = "select count(fa.id),sum(fc.cultivationArea) as TOTAL_LAND_HOLDING,ctry.name from FarmCrops fc inner join fc.farm fa inner join fa.farmer f  inner join f.village v  inner join v.city c inner join c.locality ld inner join ld.state s inner join s.country ctry where f.branchId = :branch and f.statusCode = 0 and f.status=1 and fc.status=1 and fa.status=1 GROUP BY ctry.name ORDER BY TOTAL_LAND_HOLDING desc";
				}
				flag = "disable";
				break;
			}

			query = session.createQuery(hqlString);
			query.setParameter("branch", selectedBranch);
			if (!locationLevel1.equalsIgnoreCase("first_level_Branch_Login") && flag.equalsIgnoreCase("enable")) {
				query.setParameter("locationCode", locationLevel1);
			}

			List<Object[]> result = query.list();
			return result;
		} else {
			Session session = getSessionFactory().openSession();
			String queryString = "select count(fa.id),sum(fc.CULTIVATION_AREA) as TOTAL_LAND_HOLDING,bm.name,bm.BRANCH_ID from farm_crops fc inner join farm fa  on fa.ID = fc.farm_id  inner join farmer f on f.ID = fa.FARMER_ID  inner join branch_master bm on bm.BRANCH_ID = f.BRANCH_ID where f.STATUS_CODE = 0 and f.status=1 and fa.status=1 and fc.status=1 GROUP BY bm.`NAME` ORDER BY TOTAL_LAND_HOLDING desc";
			SQLQuery query = session.createSQLQuery(queryString);
			List<Object[]> result = query.list();
			session.flush();
			session.close();
			return result;
		}
	}

	@Override
	public Double findTotalCottonAreaCount() {

		double totalCottonArea = 0;
		String qry = "";
		Session sessions = getSessionFactory().openSession();
		String queryString = "SELECT IFNULL(SUM(fc.CULTIVATION_AREA),0) FROM FARM_CROPS fc "
				+ "LEFT OUTER JOIN FARM f ON fc.FARM_ID = f.id " + "LEFT OUTER JOIN FARMER fm ON f.FARMER_ID = fm.id "
				+ "INNER JOIN VILLAGE v ON fm.VILLAGE_ID = v.id " + "INNER JOIN WAREHOUSE w ON fm.SAMITHI_ID = w.id "
				+ "LEFT OUTER JOIN PROCUREMENT_VARIETY pv ON fc.PROCUREMENT_CROPS_VARIETY_ID = pv.id "
				+ "INNER JOIN PROCUREMENT_PRODUCT pp ON pv.PROCUREMENT_PRODUCT_ID = pp.id "
				+ "INNER JOIN HARVEST_SEASON hs ON fc.CROP_SEASON = hs.id WHERE pp.NAME LIKE '%cotton%'  AND fm.STATUS_CODE = '"
				+ ESETxnStatus.SUCCESS.ordinal() + "'";

		Query query = sessions.createSQLQuery(queryString);
		qry = String.valueOf(query.list().get(0));
		double count = Double.valueOf(qry.toString());

		if (count > 0) {
			totalCottonArea = count;
		}
		sessions.flush();
		sessions.close();
		return (double) totalCottonArea;
	}

	@Override
	public Double findTotalCottonAreaCountByMonth(Date firstDateOfMonth, Date date) {
		double totalCottonArea = 0;
		String qry = "";
		Session sessions = getSessionFactory().openSession();
		Query query = sessions.createSQLQuery("SELECT  IFNULL(SUM(fc.CULTIVATION_AREA),0) FROM FARM_CROPS fc "
				+ "LEFT OUTER JOIN FARM f ON fc.FARM_ID = f.id " + "LEFT OUTER JOIN FARMER fm ON f.FARMER_ID = fm.id "
				+ "INNER JOIN VILLAGE v ON fm.VILLAGE_ID = v.id " + "INNER JOIN WAREHOUSE w ON fm.SAMITHI_ID = w.id "
				+ "LEFT OUTER JOIN PROCUREMENT_VARIETY pv ON fc.PROCUREMENT_CROPS_VARIETY_ID = pv.id "
				+ "INNER JOIN PROCUREMENT_PRODUCT pp ON pv.PROCUREMENT_PRODUCT_ID = pp.id "
				+ "INNER JOIN HARVEST_SEASON hs ON fc.CROP_SEASON = hs.id WHERE pp.NAME LIKE '%cotton%'  AND fm.STATUS_CODE = '"
				+ ESETxnStatus.SUCCESS.ordinal() + "' AND fm.CREATED_DATE BETWEEN :startDate AND :endDate");
		query.setParameter("startDate", firstDateOfMonth).setParameter("endDate", date);

		qry = String.valueOf(query.list().get(0));
		double count = Double.valueOf(qry.toString());

		if (count > 0) {
			totalCottonArea = count;
		}
		sessions.flush();
		sessions.close();
		return (double) totalCottonArea;
	}

	@Override
	public List<Object[]> populateFarmerCropCountChartByGroup(String selectedics, String selectedBranch) {
		String hqlString;
		Session session = getSessionFactory().getCurrentSession();
		Query query = null;

		if (StringUtil.isEmpty(selectedics)) {
			hqlString = "SELECT Count( f.id ),pp.name,pp.code from Farmer f inner join f.farms fa inner join f.samithi s inner join fa.farmCrops famcrps inner join famcrps.procurementVariety pv inner join pv.procurementProduct pp where f.statusCode = 0  group by pp.code ";

			query = session.createQuery(hqlString);
			List<Object[]> result = query.list();
			return result;
		} else {
			hqlString = "SELECT Count( f.id ),pp.name,pp.code FROM Farmer f inner join f.farms fa inner join f.samithi s inner join fa.farmCrops famcrps inner join famcrps.procurementVariety pv inner join pv.procurementProduct pp where f.statusCode = 0 AND s.code= :ics  group by pp.code ";

			query = session.createQuery(hqlString);
			query.setParameter("ics", selectedics);
			List<Object[]> result = query.list();
			return result;
		}

	}

	@Override
	public List<DynamicFieldConfig> listDynamicFields() {
		return list("FROM DynamicFieldConfig dsc order by dsc.id asc ");
	}

	@Override
	public void editFarmerStatus(long id, int statusCode, String statusMsg) {
		Session session = getSessionFactory().openSession();
		Query query = session
				.createSQLQuery("UPDATE farmer f set f.STATUS_CODE = :code,f.STATUS_MSG=:msg where f.id=:id");
		query.setParameter("code", statusCode);
		query.setParameter("msg", statusMsg);
		query.setParameter("id", id);
		int result = query.executeUpdate();
		session.flush();
		session.close();

	}

	@Override
	public void processCustomisedFormula(FarmerDynamicData farmerDynamicData,
			Map<String, DynamicFieldConfig> fieldConfigMap) {

		List<FarmerDynamicFieldsValue> resultSet = new ArrayList<>();
		List<DynamicFieldConfig> fmap = fieldConfigMap.values().stream()
				.filter(p -> (Arrays.asList("4").contains(p.getIsMobileAvail()) && p.getFormula() != null
						&& !StringUtil.isEmpty(p.getFormula())))
				.collect(Collectors.toList());
		if (fmap != null && !ObjectUtil.isListEmpty(fmap)) {
			Session session = this.getSessionFactory().openSession();
			fmap.sort((p1, p2) -> p1.getCode().compareTo(p2.getCode()));
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			fmap.stream().forEach(u -> {
				if (u.getReferenceId() != null && u.getReferenceId() > 0) {
					u.setFormula(u.getFormula().replace("##REFID##", String.valueOf(farmerDynamicData.getId())));
					Query query = session.createSQLQuery(u.getFormula());
					List<Object[]> result = (List<Object[]>) query.list();
					if (result != null) {
						result.stream().forEach(ff -> {
							FarmerDynamicFieldsValue farmerDynamicFieldsValue = new FarmerDynamicFieldsValue();
							farmerDynamicFieldsValue.setFieldName(u.getCode());
							farmerDynamicFieldsValue
									.setFieldValue(!ObjectUtil.isEmpty(ff[0].toString()) ? ff[0].toString() : "");
							farmerDynamicFieldsValue.setTypez(
									!ObjectUtil.isEmpty(ff[1].toString()) ? Integer.parseInt(ff[1].toString()) : 0);
							farmerDynamicFieldsValue.setComponentType(u.getComponentType());
							farmerDynamicFieldsValue.setTxnType(farmerDynamicData.getTxnType());
							farmerDynamicFieldsValue.setReferenceId(farmerDynamicData.getReferenceId());
							farmerDynamicFieldsValue.setCreatedDate(farmerDynamicData.getCreatedDate());
							farmerDynamicFieldsValue.setCreatedUser(farmerDynamicData.getCreatedUser());
							farmerDynamicFieldsValue.setTxnUniqueId(farmerDynamicData.getTxnUniqueId());
							farmerDynamicFieldsValue.setIsMobileAvail(u.getIsMobileAvail());
							farmerDynamicFieldsValue.setValidationType(u.getValidation());
							farmerDynamicFieldsValue.setIsMobileAvail(
									fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null ? fieldConfigMap
											.get(farmerDynamicFieldsValue.getFieldName()).getIsMobileAvail() : "0");

							farmerDynamicFieldsValue.setAccessType(
									fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null ? fieldConfigMap
											.get(farmerDynamicFieldsValue.getFieldName()).getAccessType() : 0);

							farmerDynamicFieldsValue
									.setListMethod(fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null
											&& fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName())
													.getCatalogueType() != null
															? fieldConfigMap
																	.get(farmerDynamicFieldsValue.getFieldName())
																	.getCatalogueType()
															: "");
							farmerDynamicFieldsValue
									.setParentId(fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null
											&& fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName())
													.getReferenceId() != null
															? fieldConfigMap
																	.get(farmerDynamicFieldsValue.getFieldName())
																	.getReferenceId()
															: 0);
							farmerDynamicFieldsValue.setFarmerDynamicData(farmerDynamicData);
							session.saveOrUpdate(farmerDynamicFieldsValue);
						});
					}

				} else {
					FarmerDynamicFieldsValue farmerDynamicFieldsValue = new FarmerDynamicFieldsValue();
					u.setFormula(u.getFormula().replace("##REFID##", String.valueOf(farmerDynamicData.getId())));
					Query query = session.createSQLQuery(u.getFormula());
					String result = (String) query.uniqueResult();
					farmerDynamicFieldsValue.setFieldName(u.getCode());
					farmerDynamicFieldsValue.setFieldValue(result);
					farmerDynamicFieldsValue.setComponentType(u.getComponentType());
					farmerDynamicFieldsValue.setTxnType(farmerDynamicData.getTxnType());
					farmerDynamicFieldsValue.setReferenceId(farmerDynamicData.getReferenceId());
					farmerDynamicFieldsValue.setCreatedDate(farmerDynamicData.getCreatedDate());
					farmerDynamicFieldsValue.setCreatedUser(farmerDynamicData.getCreatedUser());
					farmerDynamicFieldsValue.setTxnUniqueId(farmerDynamicData.getTxnUniqueId());
					farmerDynamicFieldsValue.setIsMobileAvail(u.getIsMobileAvail());
					farmerDynamicFieldsValue.setValidationType(u.getValidation());
					farmerDynamicFieldsValue
							.setIsMobileAvail(fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null
									? fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getIsMobileAvail()
									: "0");

					farmerDynamicFieldsValue
							.setAccessType(fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()) != null
									? fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getAccessType() : 0);

					farmerDynamicFieldsValue.setListMethod(fieldConfigMap
							.get(farmerDynamicFieldsValue.getFieldName()) != null
							&& fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getCatalogueType() != null
									? fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getCatalogueType()
									: "");
					farmerDynamicFieldsValue.setParentId(fieldConfigMap
							.get(farmerDynamicFieldsValue.getFieldName()) != null
							&& fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getReferenceId() != null
									? fieldConfigMap.get(farmerDynamicFieldsValue.getFieldName()).getReferenceId() : 0);
					farmerDynamicFieldsValue.setFarmerDynamicData(farmerDynamicData);
					session.saveOrUpdate(farmerDynamicFieldsValue);
				}

			});

			session.flush();
			session.clear();
			session.close();
		}

	}

	@Override
	public Farmer findFarmerByMsgNo(String msgNo) {
		// TODO Auto-generated method stub
		return (Farmer) find("FROM Farmer f WHERE f.msgNo=?", msgNo);
	}

	@Override
	public void updateFarmerImageInfo(long id, long imageInfoId) {

		Session session = getSessionFactory().openSession();
		SQLQuery query = session
				.createSQLQuery("update farmer set IMAGE_INFO_ID = " + imageInfoId + " where id=" + id + "");
		int list = query.executeUpdate();

		session.flush();
		session.close();

	}

	public void removeUnmappedFarmCropObject() {

		Session session = getSessionFactory().openSession();
		Query query = session.createQuery("DELETE FarmCrops f WHERE f.farm is null");
		int result = query.executeUpdate();
		session.flush();
		session.close();

	}

	@Override
	public List<FarmerField> listFarmerFields() {
		return list("FROM FarmerField ff");
	}

	public List<Farmer> listFarmer() {

		return list("FROM Farmer f WHERE f.statusCode = ? ORDER BY f.firstName ASC", ESETxnStatus.SUCCESS.ordinal());
	}

	@Override
	public List<Object[]> listFarmFieldsByFarmerId(long id) {

		return (List<Object[]>) list(
				"select DISTINCT fm.id,fm.farmCode,fm.farmName FROM FarmCrops fc join fc.farm fm Where fm.farmer.id = ? and fm.status=1", id);
	}

	@Override
	public List<Farmer> listFarmersByExporter(Long expId) {
		return list(
				"FROM Farmer f left join fetch f.exporter e WHERE f.statusCode = ? and e.id=?ORDER BY f.firstName ASC",
				new Object[] { ESETxnStatus.SUCCESS.ordinal(), expId });
	}

	@Override
	public Farm findFarmById(Long id) {
		// TODO Auto-generated method stub
		return (Farm) find("FROM Farm fm WHERE fm.id  = ?", id);
	}

	@Override
	public Farm findFarmbyFarmCode(String farmCode) {
		return (Farm) find("FROM Farm fm WHERE fm.farmCode  = ? and status=1", farmCode);
	}

	@Override
	public Object findObjectById(String qryString, Object[] value) {
		return find(qryString, value);

	}

	@Override
	public Object listObjectById(String qryString, Object[] value) {
		return list(qryString, value);

	}

	@Override
	public void runSQLQuery(String methodNameQ, Object[] parameter) {
		Session session = getSessionFactory().openSession();
		Query query = session.createSQLQuery(methodNameQ);
		if (parameter != null && parameter.length > 0) {
			i = 1;
			Arrays.asList(parameter).stream().forEach(u -> {
				if (methodNameQ.contains("param" + i)) {
					if (u != null && u.toString().contains(",")) {
						query.setParameterList("param" + i, Arrays.asList(u.toString().split(",")));
					} else {
						query.setParameter("param" + i, u);
					}
					i++;
				}
			});
		}
		query.executeUpdate();
		session.flush();
		session.close();

	}

	public List<Object[]> getharvestdateandquantity(int i, List<Long> fcids) {
		Session session = getSessionFactory().openSession();
		String queryString = "SELECT fc.id,max(ct.LAST_HARVEST_DATE),ct.HARVESTED_WEIGHT,ct.SORTED_WEIGHT,ct.LOSS_WEIGHT FROM city_warehouse ct join farm_crops fc on fc.id = ct.FARM_CROP_ID and ct.STOCK_TYPE=:txnType where fc.id in (:ids)";
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameterList("ids", fcids);
		query.setParameter("txnType", i);
		List<Object[]> result = query.list();
		session.flush();
		session.close();
		return result;
	}

	public List<Object[]> getlistofkenyacode() {

		Session session = getSessionFactory().openSession();
		String queryString = "SELECT distinct PRODUCE_CONSIGNMENT_NO as pp,PRODUCE_CONSIGNMENT_NO FROM `shipment` s where s.PRODUCE_CONSIGNMENT_NO not in (select KENYA_TRACE_CODE from recalling)";
		SQLQuery query = session.createSQLQuery(queryString);
		List<Object[]> result = query.list();
		session.flush();
		session.close();
		return result;

	}

	public void updateshipmentdetail(String receiptNumber) {
		Session sessions = getSessionFactory().openSession();
		String queryStrings = "UPDATE shipment_details pl SET pl.recalling_status='1' WHERE pl.id = '" + receiptNumber
				+ "'";
		Query querys = sessions.createSQLQuery(queryStrings);

		int results = querys.executeUpdate();
		sessions.flush();
		sessions.close();
	}

	public List<Object[]> getlistofkenyacodeFromRecalling() {
		return (List<Object[]>) list(
				"select rc.id,rc.kenyaTraceCode FROM Recalling rc WHERE kenyaTraceCode is not NULL and rc.kenyaTraceCode not in (select recall.kenyaTraceCode from PostRecallInspection) AND rc.status=1");
	}

	public void removerecallingdetail(Long id, Long id2) {
		Session sessions = getSessionFactory().openSession();
		String queryStrings = "UPDATE shipment_details pl SET pl.recalling_status= NULL WHERE pl.id = '" + id2 + "'";
		Query querys = sessions.createSQLQuery(queryStrings);

		int results = querys.executeUpdate();
		sessions.flush();

		/*
		 * String queryStrings1 = "UPDATE recall_details WHERE status =0"; Query
		 * querys1 = sessions.createSQLQuery(queryStrings1);
		 * 
		 * int results1 = querys1.executeUpdate(); sessions.flush();
		 */
		sessions.close();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Object[]> executeChartQuery(String chartQuery, int dateFilter, String dateField, String sDate,
			String eDate, Long id, String exporterField, String groupField, String orderField) {
		Session session = getSessionFactory().openSession();
		String QueryString = chartQuery;
		if (id > 0) {
			QueryString += " where " + exporterField + "=" + id;

		}

		if (dateFilter == 1) {
			String datestr = " AND " + dateField + " BETWEEN cast(:startDate as Date) AND cast(:endDate  as Date) ";
			QueryString = QueryString.replace("##dateFilter##", datestr);
		}

		if (!StringUtil.isEmpty(groupField)) {
			QueryString += " group by " + groupField;
		}

		if (!StringUtil.isEmpty(orderField)) {
			QueryString += " order by " + orderField;
		}

		SQLQuery query = session.createSQLQuery(QueryString);
		if (dateFilter == 1) {
			query.setParameter("startDate", sDate).setParameter("endDate", eDate);
		}
		List list = query.list();
		session.flush();
		session.close();
		return list;
	}

	@Override
	public List<Dashboard> listDashboardDataByChartDivId() {

		return list("FROM Dashboard d WHERE d.status='1' order by d.chartOrder ");
	}

	@Override
	public List<ProcurementVariety> findProcurementVarietyByIds(List<Long> convertStringList) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery("FROM ProcurementVariety f WHERE f.id in (:status1) ");
		query.setParameterList("status1", convertStringList);
		List list = query.list();

		return list;
	}

}
