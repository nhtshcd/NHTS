package com.sourcetrace.eses.adapter.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.DynamicFeildMenuConfig;
import com.sourcetrace.eses.entity.DynamicFieldConfig;
import com.sourcetrace.eses.entity.DynamicMenuFieldMap;
import com.sourcetrace.eses.entity.DynamicMenuSectionMap;
import com.sourcetrace.eses.entity.DynamicSectionConfig;
import com.sourcetrace.eses.entity.LanguagePreferences;
import com.sourcetrace.eses.property.TransactionProperties;
import com.sourcetrace.eses.property.TxnEnrollmentProperties;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

@Component
public class DynamicComponentDownload implements ITxnAdapter {
	private static final Logger LOGGER = Logger.getLogger(DynamicComponentDownload.class.getName());
	@Autowired
	private IFarmerService farmerService;
	Map<String, Map<String, LinkedList<Object>>> folloFields = new HashMap<>();
	private Map<Long, String> fieldsList = new HashMap<>();
	private Map<String, List<Long>> rtefList = new HashMap();



public Map<?, ?> processJson(Map<?, ?> reqData) {
		LOGGER.info("--------------------Dynamic Component Download statrts--------------------------");
		String revisionNo = (String) reqData.get(TxnEnrollmentProperties.DYNAMIC_COMPONENT_DOWNLOAD_REVISION_NO);
		String folloRev = (String) reqData.get("followUpRevNo");

		if (StringUtil.isEmpty(revisionNo))
			revisionNo = "0";

		if (StringUtil.isEmpty(folloRev))
			folloRev = "0";
		fieldsList = new HashMap<>();
		rtefList = new HashMap<>();
		Map resp = new LinkedHashMap<>();
		Head head = (Head) reqData.get(TransactionProperties.HEAD);
		String agentId = head.getAgentId();
		List<DynamicFeildMenuConfig> dynamicMenuList = farmerService.listDynamicMenusByRevNo(revisionNo,
				head.getBranchId(), head.getTenantId());
		folloFields = new HashMap<>();
		if (dynamicMenuList == null || dynamicMenuList.isEmpty()) {
			resp.put(TxnEnrollmentProperties.MENU_LIST, new ArrayList());
			resp.put(TxnEnrollmentProperties.DYNAMIC_COMPONENT_DOWNLOAD_REVISION_NO, "0");

		} else {

			List menuCollection = new ArrayList();

			dynamicMenuList.stream().forEach(menu -> {
				Map fieldObj = new HashMap();
				List<DynamicFieldConfig> menuFieldList = menu.getDynamicFieldConfigs().stream()
						.map(DynamicMenuFieldMap::getField).collect(Collectors.toList());
				rtefList = menuFieldList.stream().filter(u -> u.getReferenceId() != null)
						.collect(Collectors.groupingBy(u -> u.getDynamicSectionConfig().getSectionCode(),
								Collectors.mapping(DynamicFieldConfig::getReferenceId, Collectors.toList())));
				fieldsList = menuFieldList.stream().collect(Collectors.toMap(DynamicFieldConfig::getId,
						DynamicFieldConfig::getComponentName, (address1, address2) -> {

							return address1;
						}));
				try {
					fieldObj.put(TxnEnrollmentProperties.MENU_ID,
							!StringUtil.isEmpty(menu.getCode()) ? menu.getCode() : "");
					fieldObj.put(TxnEnrollmentProperties.MENU_ID,
							!StringUtil.isEmpty(menu.getCode()) ? menu.getCode() : "");
					fieldObj.put(TxnEnrollmentProperties.MENU_NAME,
							!StringUtil.isEmpty(menu.getName()) ? menu.getName() : "");
					fieldObj.put(TxnEnrollmentProperties.MENU_ICON_CLASS,
							!StringUtil.isEmpty(menu.getIconClass()) ? menu.getIconClass() : "");
					fieldObj.put(TxnEnrollmentProperties.ENTITY,
							!StringUtil.isEmpty(menu.getEntity()) ? menu.getEntity() : "");
					fieldObj.put(TxnEnrollmentProperties.MENU_ORDER,
							!StringUtil.isEmpty(String.valueOf(menu.getOrder())) ? String.valueOf(menu.getOrder())
									: "");
					fieldObj.put(TxnEnrollmentProperties.IS_SEASON,
							!StringUtil.isEmpty(menu.getIsSeason()) ? String.valueOf(menu.getIsSeason()) : "");

					fieldObj.put(TxnEnrollmentProperties.TXN_TYPE_ID,
							!StringUtil.isEmpty(menu.getmTxnType()) ? menu.getmTxnType() : "");
					fieldObj.put(TransactionProperties.LANG_LIST,
							getCollectionJson(new ArrayList<LanguagePreferences>(menu.getLanguagePreferences())));
					fieldObj.put(TxnEnrollmentProperties.AGENT_TYPE,
							!StringUtil.isEmpty(menu.getAgentType()) ? menu.getAgentType() : "02");

					Map sectMap = getSectionCollectionJson(menu.getDynamicSectionConfigs(), folloFields);
					fieldObj.put(TxnEnrollmentProperties.SECTIONS, (List) sectMap.get("newAp"));
					if (sectMap.containsKey("fw") && !((Map) sectMap.get("fw")).isEmpty()) {
						folloFields.putAll((Map) sectMap.get("fw"));
					}
					menuCollection.add(fieldObj);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

			resp.put(TxnEnrollmentProperties.MENU_LIST, menuCollection);
			resp.put(TxnEnrollmentProperties.DYNAMIC_COMPONENT_DOWNLOAD_REVISION_NO,
					dynamicMenuList.get(0).getRevisionNo());

		}
		return resp;
	}


public Map getFieldCollectionJson(Set<DynamicMenuFieldMap> dynamicFieldMap,
			Map<String, Map<String, LinkedList<Object>>> folloFields) {
		List fieldCollection = new ArrayList();
		SortedSet<DynamicMenuFieldMap> sortedMap = new TreeSet<>(
				Comparator.comparing((DynamicMenuFieldMap pp) -> pp.getMenu().getmTxnType())
						.thenComparing(DynamicMenuFieldMap::getOrder));
		sortedMap.addAll(dynamicFieldMap);
		sortedMap.forEach(u -> {
			DynamicFieldConfig dfields = u.getField();
			if (!ObjectUtil.isEmpty(dfields)
					&& (dfields.getIsMobileAvail() != null && (dfields.getFollowUp() != 1 && dfields.getFollowUp() != 2)
							&& (!dfields.getIsMobileAvail().equals("2") && !dfields.getIsMobileAvail().equals("6")
									&& !dfields.getIsMobileAvail().equals("4")))) {

				Map fieldData = new HashMap();
				try {
					fieldData.put(TxnEnrollmentProperties.COMPONENT_TYPE,
							!StringUtil.isEmpty(dfields.getComponentType()) ? dfields.getComponentType() : "");
					fieldData.put(TxnEnrollmentProperties.COMPONENT_LABEL,
							!StringUtil.isEmpty(dfields.getComponentName()) ? dfields.getComponentName() : "");

					fieldData.put(TxnEnrollmentProperties.COMPONENT_ID,
							!StringUtil.isEmpty(dfields.getCode()) ? dfields.getCode() : "");

				
					fieldData.put(TxnEnrollmentProperties.FIELD_BEFORE_INSERT,
							!StringUtil.isEmpty(u.getMBeforeInsert()) ? u.getMBeforeInsert() : "");
					fieldData.put(TxnEnrollmentProperties.FIELD_AFTER_INSERT,
							!StringUtil.isEmpty(u.getMAfterInsert()) ? u.getMAfterInsert() : "");
					fieldData.put(TxnEnrollmentProperties.DATA_FORMAT,
							!StringUtil.isEmpty(dfields.getDataFormat()) ? dfields.getDataFormat() : "");
					fieldData.put(TxnEnrollmentProperties.IS_MANDATORY,
							!StringUtil.isEmpty(dfields.getIsRequired()) ? dfields.getIsRequired() : "");
					fieldData.put(TxnEnrollmentProperties.VALID_TYPE,
							!StringUtil.isEmpty(dfields.getValidation()) ? dfields.getValidation() : "");
					fieldData.put(TxnEnrollmentProperties.MAXLENGTH,
							!StringUtil.isEmpty(dfields.getComponentMaxLength()) ? dfields.getComponentMaxLength()
									: "");
					fieldData.put(TxnEnrollmentProperties.MIN_LENGTH,
							!StringUtil.isEmpty(dfields.getMinLen()) ? dfields.getMinLen() : "");
						fieldData.put(TxnEnrollmentProperties.DEPENDENT_FIELD,
							!StringUtil.isEmpty(dfields.getDependencyKey()) ? dfields.getDependencyKey() : "");
					fieldData.put(TxnEnrollmentProperties.CATLOGUE_TYPE,
							!StringUtil.isEmpty(dfields.getCatalogueType())
									&& !ObjectUtil.isEmpty(dfields.getAccessType())
											? dfields.getCatalogueType() + "|" + dfields.getAccessType() : "");

					if (dfields.getParentActKey() != null && !StringUtil.isEmpty(dfields.getParentActKey())
							&& (dfields.getFollowUp() == 1 || dfields.getFollowUp() == 2)) {
						dfields.setParentDependencyKey(dfields.getParentDependencyKey() != null
								&& !StringUtil.isEmpty(dfields.getParentDependencyKey())
										? dfields.getParentDependencyKey() + "," + dfields.getParentActKey()
										: dfields.getParentActKey());
						dfields.setParentDependencyKey(Arrays.asList(dfields.getParentDependencyKey().split(","))
								.stream().distinct().collect(Collectors.joining(",")));
					}
					fieldData.put(TxnEnrollmentProperties.PARENT_DEPEND,
							!StringUtil.isEmpty(dfields.getParentDependencyKey()) ? dfields.getParentDependencyKey()
									: "");

					if (dfields.getParentActField() != null && !StringUtil.isEmpty(dfields.getParentActField())
							&& (dfields.getFollowUp() == 1 || dfields.getFollowUp() == 2)) {
						fieldData.put(TxnEnrollmentProperties.PARENT_FIELD, dfields.getParentActField());
					} else {

						fieldData.put(TxnEnrollmentProperties.PARENT_FIELD,
								!ObjectUtil.isEmpty(dfields.getParentDepen()) ? dfields.getParentDepen().getCode()
										: "");
					}
					fieldData.put(TxnEnrollmentProperties.IS_OTHER, String.valueOf(dfields.getIsOther()));

					if (dfields.getParentActKey() != null && !StringUtil.isEmpty(dfields.getParentActKey())
							&& (dfields.getFollowUp() == 3)) {
						dfields.setCatDependencyKey(dfields.getCatDependencyKey() != null
								&& !StringUtil.isEmpty(dfields.getCatDependencyKey())
										? dfields.getCatDependencyKey() + "," + dfields.getParentActKey()
										: dfields.getParentActKey());
					}
					fieldData.put(TxnEnrollmentProperties.CAT_DEP_KEY,
							!StringUtil.isEmpty(dfields.getCatDependencyKey()) ? dfields.getCatDependencyKey() : "");
					fieldData.put(TxnEnrollmentProperties.SECTION,
							!StringUtil.isEmpty(dfields.getDynamicSectionConfig())
									? dfields.getDynamicSectionConfig().getSectionCode() : "");
					fieldData.put(TxnEnrollmentProperties.TXN_TYPE_ID, u.getMenu().getmTxnType());

					if (StringUtil.isInteger(dfields.getComponentType()) && Integer
							.valueOf(dfields.getComponentType()) == DynamicFieldConfig.COMPONENT_TYPES.LIST.ordinal()) {
						fieldData.put(TxnEnrollmentProperties.LIST_ID, String.valueOf(dfields.getId()));
					} else {
						fieldData.put(TxnEnrollmentProperties.LIST_ID, !StringUtil.isEmpty(dfields.getReferenceId())
								? String.valueOf(dfields.getReferenceId()) : "");
					}

					fieldData.put(TxnEnrollmentProperties.ORDER, String.valueOf(u.getOrder()));
					fieldData.put(TxnEnrollmentProperties.BLOCK_ID, "0");
					fieldData.put(TxnEnrollmentProperties.LIST_METHOD_NAME, "");
					fieldData.put(TxnEnrollmentProperties.FORMULA_DEPENDENCY,
							!StringUtil.isEmpty(dfields.getFormula()) ? dfields.getFormula() : "");
					fieldData.put(TransactionProperties.LANG_LIST,
							getCollectionJson(new ArrayList<LanguagePreferences>(dfields.getLanguagePreferences())));

					fieldCollection.add(fieldData);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		Map newAp = new HashMap<>();
		newAp.put("fieldsColl", fieldCollection);
		return newAp;
	}

	public Map getSectionCollectionJson(Set<DynamicMenuSectionMap> dynamicSectionMap,
			Map<String, Map<String, LinkedList<Object>>> folloFields) {
		List sectionCollection = new ArrayList();
		dynamicSectionMap.forEach(u -> {
			DynamicSectionConfig section = u.getSection();
			if (!ObjectUtil.isEmpty(section) && section.getMobileFieldsSize() > 0) {
				Map sectionId = new HashMap();
				try {

					sectionId.put(TxnEnrollmentProperties.SECTION, section.getSectionCode());
					sectionId.put(TxnEnrollmentProperties.SECTION_NAME, section.getSectionName());
					sectionId.put(TxnEnrollmentProperties.TXN_TYPE_ID, u.getMenu().getmTxnType());
					sectionId.put(TxnEnrollmentProperties.BEFORE_INSERT,
							!StringUtil.isEmpty(u.getMBeforeInsert()) ? u.getMBeforeInsert() : "");
					sectionId.put(TxnEnrollmentProperties.AFTER_INSERT,
							!StringUtil.isEmpty(u.getMAfterInsert()) ? u.getMAfterInsert() : "");
					if (!ObjectUtil.isEmpty(section.getDynamicFieldConfigs())) {
						sectionId.put(TxnEnrollmentProperties.BLOCK_ID, String.valueOf(u.getMenu().getId()));
					} else {
						sectionId.put(TxnEnrollmentProperties.BLOCK_ID, 0);
					}

					List listCollection = new ArrayList();
					List<Object> listObject = new ArrayList<Object>();
					if (rtefList.containsKey(u.getSection().getSectionCode())) {
						List<Long> lsu = rtefList.get(u.getSection().getSectionCode());
						lsu.stream().distinct()

								.forEach(fieldsConfig -> {

									try {
										Map listId = new HashMap();
										listId.put(TxnEnrollmentProperties.LIST_ID, (fieldsConfig).toString());
										listId.put(TxnEnrollmentProperties.LIST_NAME,
												fieldsList.containsKey(Long.valueOf(fieldsConfig))
														? fieldsList.get(Long.valueOf(fieldsConfig)) : "");
										listCollection.add(listId);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								});
					}

					sectionId.put(TxnEnrollmentProperties.LISTS, listCollection);
					Map fieldss = getFieldCollectionJson(
							u.getMenu().getDynamicFieldConfigs().stream()
									.filter(mm -> mm.getField().getDynamicSectionConfig().getSectionCode()
											.equals(section.getSectionCode()))
									.collect(Collectors.toSet()),
							folloFields);

					sectionId.put(TxnEnrollmentProperties.FIELDS_LIST, (List) fieldss.get("fieldsColl"));
					sectionCollection.add(sectionId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		Map newAp = new HashMap<>();
		newAp.put("newAp", sectionCollection);
		return newAp;

	}

	public List getCollectionJson(List<LanguagePreferences> lpListObj){
		List langColl = new ArrayList();
		if (lpListObj != null && !lpListObj.isEmpty()) {
			for (LanguagePreferences lp : lpListObj) {
				Map langCode = new HashMap();
				langCode.put(TransactionProperties.LANGUAGE_CODE, lp.getLang());
				langCode.put(TransactionProperties.LANGUAGE_NAME, lp.getName());
				langColl.add(langCode);

			}
		}

		return langColl;
	}
}
