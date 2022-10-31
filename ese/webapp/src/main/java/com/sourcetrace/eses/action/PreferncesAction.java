/*
 * PreferncesAction.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.action;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.sourcetrace.eses.entity.Asset;
import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.Distribution;
import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.HarvestSeason;
import com.sourcetrace.eses.entity.License;
import com.sourcetrace.eses.entity.LicenseType;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.Base64Util;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.FileUtil;
import com.sourcetrace.eses.util.ILicenseKeyGenerator;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class PreferncesAction.
 * 
 * @author $Author: $
 * @version $Rev: 1048 $, $Date: 2008-11-12 15:46:45 +0530 (Wed, 12 Nov 2008) $
 */
public class PreferncesAction extends SwitchValidatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2919520539865200466L;

	public enum AreaCaptureTypes {
		MANUAL, GPS, BOTH
	}

	public enum EnableBranchTypes {
		NO, YES
	}

	public enum EnableMultiProductTypes {
		NO, YES
	}

	public enum EnableGrampanjayatTypes {
		NO, YES
	}

	public enum ICSName {
		NO, YES
	}

	public enum IdProof {
		NO, YES
	}

	public enum InsuranceInformation {
		NO, YES
	}

	public enum CropInformation {
		NO, YES
	}

	public enum HarvestSeasonInformation {
		NO, YES
	}

	public enum IsCertifiedFarmer {
		NO, YES
	}
	
	public enum IsKpfBased {
		NO, YES
	}
	public enum DistributionImage {
		NO,YES
	}
	
	public enum ProductReturnImage {
		NO,YES
	}
	
	public enum DigitalSignature {
		NO,YES
	}

	private static final Logger logger = Logger.getLogger(PreferncesAction.class);
	
	
	@Autowired
	private IFarmerService farmerService;
	@Autowired
	private IUtilService utilService;
	private ESESystem preferences, prefernecesId;
	private String id;
	private String noOfInvalidAttempts;
	private String timeToAutoRelease;
	private String dailyClosingTime;
	private String userName;
	private String password;
	private String mailHost;
	private String port;
	private Date timeStamp;
	private String temp;
	// public PreferncesAction preferncesAction;
	public String theme;
	private String agentTransactionSession;
	private String loginUserName;
	private String period;
	private String devTypeValue;
	private String version;
	private String startPeriod;
	private String endPeriod;
	private String licenseOwner;
	private String licenseClient;
	private String aeraCaptureMode;
	private String geoFincingFlg;
	private String geoFincingRadius;
	private String currentSeasonId;
	private String currencyTypez;
	private String enableBranch;
	private Long branchTenantId;
	private String mainBranchName;
	private String enableMultiProduct;
	private String loginLogoImageByteString;
	private String favIconImageByteString;
	private File loginLogoImage;
	private File favIconImage;
	private String enableGrampanjayat;
	private String enableFPOFG;
	private String enableFarmerCode;
	private String codeType;
	private String supplierType;
	private String icsName;
	private String soil;
	private String idProof;
	private String insuranceInformation;
	private String cropInformation;
	private String enableBankInfo;
	private String isSingleTenant;
	private String singleTenantName;
	private String harvestSeasonInformation;
	private String isCertifiedFarmer;
	private String farmersByGroupPie;
	private String warehouseStockShart;
	private String farmersByGroupBar;
	private String deviceChart;
	private String farmersByOrg;
	private String totalLandAcreChart;
	private String totalAreaAroductionChart;
	private String seedTypeChart;
	private String seedSourceChart;
	private String farmerDetailsChart;
	private String farmerCropDetailChart;
	private String approveOption;
	private String batchNo;
	private String cowMilkByMonthChart;
	private String cowDiseaseChart;
	private String cowByResearchStationChart;
	private String cowByVillageChart;
	private String cowMilkNonMilkChart;
	private String txnChart;
	private String cowCostBarChart;
	private String totalLandAcreVillageChart;
	private String farmerCocChart;
	private String farmerDataStatChart;
	private String farmerIcsChart;
	private String cropHarvestSaleChart;
	private String farmerCocExpensesChart;
	private String farmerCocSegregatedChart;
	private String traceableBatchNo;
	private String farmerSowingHarvest;
	private String areaUnderProdByOrg;
	private String ginnerQuantitySold;
	private String gmoPercentage;
    private String isKpfBased;
    private String distImage;
    private String prodReturnImage;
    private String digitalSignature;
    private String cropCalendar;

	/** QR CODE ADDRESS */
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String addressLine4;
	private String addressLine5;
	private String addressLine6;
	private String addressLine7;
	private String addressLine8;
	private String addressLine9;
	private String addressLine10;
	private String addressLine11;
	private String addressLine12;
	private String addressLine13;
	private String addressLine14;
	private String addressLine15;
	private String addressLine16;
	private String addressLine17;
	private String barcode;
	private String storage;
	private String noOfCrates;

	/** The preferencesmap. */
	HashMap<String, String> preferencesmap = new HashMap<String, String>();
	Map<Integer, String> devType = new LinkedHashMap<Integer, String>();
	Map<String, String> aeraCaptureModeType = new LinkedHashMap<String, String>();
	Map<String, String> enableBranchTypes = new LinkedHashMap<String, String>();
	Map<String, String> enableMultiProductTypes = new LinkedHashMap<String, String>();
	Map<String, String> enableGrampanjayatTypes = new LinkedHashMap<String, String>();
	SimpleDateFormat inFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT);
	SimpleDateFormat outFormat = new SimpleDateFormat("MMddyyyy");
	private static final String TEXT_RETURN_TYPE = "text";
	private InputStream fileInputStream;
	private String fileName;
	List<Integer> periodType = new ArrayList<Integer>();
	private ILicenseKeyGenerator licenseKeyGenerator;
	Map<String, String> icsNameList = new LinkedHashMap<String, String>();
	Map<String, String> soilList = new LinkedHashMap<String, String>();
	Map<String, String> idProofList = new LinkedHashMap<String, String>();
	Map<String, String> insuranceInfoList = new LinkedHashMap<String, String>();
	Map<String, String> cropInfoList = new LinkedHashMap<String, String>();
	Map<String, String> harvestSeasonInfoList = new LinkedHashMap<String, String>();
	Map<String, String> isCertifiedFarmerInfoList = new LinkedHashMap<String, String>();
	Map<String, String> approveOptionList = new LinkedHashMap<String, String>();
	Map<String, String> batchNoList = new LinkedHashMap<String, String>();
	Map<String, String> farmerCocChartList = new LinkedHashMap<String, String>();
	Map<String, String> farmerDataStatChartList = new LinkedHashMap<String, String>();
	Map<String, String> farmerIcsChartList = new LinkedHashMap<String, String>();
	Map<String, String> cropHarvestSaleChartList = new LinkedHashMap<String, String>();
	Map<String, String> farmerCocExpensesChartList = new LinkedHashMap<String, String>();
	Map<String, String> farmerCocSegregatedChartList = new LinkedHashMap<String, String>();
	Map<String, String> traceableBatchNoList = new LinkedHashMap<String, String>();
	Map<String, String> farmerSowingHavstList = new LinkedHashMap<String, String>();
	Map<String, String> areaUnderProdByOrgList = new LinkedHashMap<String, String>();
	Map<String, String> ginnerQuantityList = new LinkedHashMap<String, String>();
	Map<String, String> gmoList = new LinkedHashMap<String, String>();
	Map<String, String> prefList = new LinkedHashMap<String, String>();
	Map<String, String> isKpfBasedList = new LinkedHashMap<String, String>();
	Map<String, String> isDistImageList = new LinkedHashMap<String, String>();
	Map<String, String> isProdReturnImageList = new LinkedHashMap<String, String>();
	Map<String, String> isDigitalSignatureList = new LinkedHashMap<String, String>();
	

	// Roi Info
	private String rateOfInterest, rateOfInterestDecimal, roiEffectiveFrom, roiApplyExist;
	private String isInterestModule = "0";

	private static final String EXEC_USER = "exec";
	private String user;

	@Resource(name = "datasources")
	private Map<String, DataSource> datasources;

	
	private String parentMenuId; // For menu delete UI
	private String subMenuId;
	
	private String parentId;
	private String menuName;
	private String menuDescription;
	private String menuUrl;
	private String menuOrder;
	private String ese_ent_name;
	private String ese_action_actionId;
	private String role_id;
	private String subMenusOrder;
	private String viewName;
	private String valStr;
	private String reportName;
	
	private String loanDetailJsonString="";
	private int minRange;
	private int maxRange;
	private String templateId;
	private String htmlContent;
	private String enableLoanModule;
	
	@Getter
	@Setter
	private String dealerExpireDate;
	@Getter
	@Setter
	private String dealerExpireDateStock;
	@Getter
	@Setter
	private String fumigatorExpireDate;
	@Getter
	@Setter
	private String premisesExpireDate;
	@Getter
	@Setter
	private String importAppExpire;
	@Getter
	@Setter
	private String chemicalExpireDate;
	
	@Getter
	@Setter
	private String chemicalRenewalExpireDate;
	
	@Getter
	@Setter
	private String permitExpireDate;
	
	@Getter
	@Setter
	private String seedMerchantExpire;
	
	@Getter
	@Setter
	private String seedDealerExpire;
	
	@Getter
	@Setter
	private String breederSeedExpire;
	
	@Getter
	@Setter
	private String growerExpire;
	
	@Getter
	@Setter
	private String passwordMinLength;
	@Getter
	@Setter
	private String passwordMaxLength;
	@Getter
	@Setter
	private String age;
	@Getter
	@Setter
	private String reminderDays;
	
	@Getter
	@Setter
	private String seedCertExpire;
	@Getter
	@Setter
	private String dealerExpireJson;
	@Getter
	@Setter
	private String populateDealerJson;
	/**
	 * @see com.sourcetrace.esesw.view.SwitchAction#list()
	 */
	public String list() {

		request.setAttribute("heading", getText("title"));
		preferences = utilService.findPrefernceById("1");
		if (getBranchId() != null && !getBranchId().isEmpty()) {
			preferences = utilService.findPrefernceByOrganisationId(getBranchId());
			if (preferences == null) {
				preferences = new ESESystem();
				Map<String, String> prefs = new HashMap<String, String>();
				ESESystem mainese = utilService.findPrefernceById("1");
				prefs.put(ESESystem.INVALID_ATTEMPTS_COUNT,
						mainese.getPreferences().get(ESESystem.INVALID_ATTEMPTS_COUNT));
				prefs.put(ESESystem.MANU_REPACK_DEALER_EXPIRE_DAYS, mainese.getPreferences().get(ESESystem.MANU_REPACK_DEALER_EXPIRE_DAYS));
				prefs.put(ESESystem.CHEMICAL_EXPIRE_DAYS, mainese.getPreferences().get(ESESystem.CHEMICAL_EXPIRE_DAYS));
				prefs.put(ESESystem.TIME_TO_AUTO_RELEASE, mainese.getPreferences().get(ESESystem.TIME_TO_AUTO_RELEASE));
				prefs.put(ESESystem.AREA_CAPTURE_MODE, mainese.getPreferences().get(ESESystem.AREA_CAPTURE_MODE));
				prefs.put(ESESystem.GEO_FENCING_FLAG, mainese.getPreferences().get(ESESystem.GEO_FENCING_FLAG));
				prefs.put(ESESystem.GEO_FENCING_RADIUS_MT,
						mainese.getPreferences().get(ESESystem.GEO_FENCING_RADIUS_MT));
				prefs.put(ESESystem.CURRENT_SEASON_CODE, mainese.getPreferences().get(ESESystem.CURRENT_SEASON_CODE));

				prefs.put(ESESystem.INTEREST_MODULE, mainese.getPreferences().get(ESESystem.INTEREST_MODULE));
				prefs.put(ESESystem.RATE_OF_INTEREST, mainese.getPreferences().get(ESESystem.RATE_OF_INTEREST));
				prefs.put(ESESystem.ROI_EFFECTIVE_FROM, mainese.getPreferences().get(ESESystem.ROI_EFFECTIVE_FROM));
				prefs.put(ESESystem.ROI_EFFECT_EXISTING_FARMER,
						mainese.getPreferences().get(ESESystem.ROI_EFFECT_EXISTING_FARMER));
				prefs.put(ESESystem.EXPORT_RECORD_LIMIT, mainese.getPreferences().get(ESESystem.EXPORT_RECORD_LIMIT));
				prefs.put(ESESystem.GENERAL_DATE_FORMAT, mainese.getPreferences().get(ESESystem.GENERAL_DATE_FORMAT));

				prefs.put(ESESystem.ADDRESS_LINE1, mainese.getPreferences().get(ESESystem.ADDRESS_LINE1));
				prefs.put(ESESystem.ADDRESS_LINE2, mainese.getPreferences().get(ESESystem.ADDRESS_LINE2));
				prefs.put(ESESystem.ADDRESS_LINE3, mainese.getPreferences().get(ESESystem.ADDRESS_LINE3));
				prefs.put(ESESystem.ADDRESS_LINE4, mainese.getPreferences().get(ESESystem.ADDRESS_LINE4));
				prefs.put(ESESystem.ADDRESS_LINE5, mainese.getPreferences().get(ESESystem.ADDRESS_LINE5));
				prefs.put(ESESystem.ADDRESS_LINE6, mainese.getPreferences().get(ESESystem.ADDRESS_LINE6));
				prefs.put(ESESystem.ADDRESS_LINE7, mainese.getPreferences().get(ESESystem.ADDRESS_LINE7));
				prefs.put(ESESystem.ADDRESS_LINE8, mainese.getPreferences().get(ESESystem.ADDRESS_LINE8));
				prefs.put(ESESystem.ADDRESS_LINE9, mainese.getPreferences().get(ESESystem.ADDRESS_LINE9));
				prefs.put(ESESystem.ADDRESS_LINE10, mainese.getPreferences().get(ESESystem.ADDRESS_LINE10));
				prefs.put(ESESystem.ADDRESS_LINE11, mainese.getPreferences().get(ESESystem.ADDRESS_LINE11));
				prefs.put(ESESystem.ADDRESS_LINE12, mainese.getPreferences().get(ESESystem.ADDRESS_LINE12));
				prefs.put(ESESystem.ADDRESS_LINE13, mainese.getPreferences().get(ESESystem.ADDRESS_LINE13));
				prefs.put(ESESystem.ADDRESS_LINE14, mainese.getPreferences().get(ESESystem.ADDRESS_LINE14));
				prefs.put(ESESystem.ADDRESS_LINE15, mainese.getPreferences().get(ESESystem.ADDRESS_LINE15));
				prefs.put(ESESystem.ADDRESS_LINE16, mainese.getPreferences().get(ESESystem.ADDRESS_LINE16));
				prefs.put(ESESystem.ADDRESS_LINE17, mainese.getPreferences().get(ESESystem.ADDRESS_LINE17));
				prefs.put(ESESystem.BARCODE, mainese.getPreferences().get(ESESystem.BARCODE));
com.google.gson.JsonObject js= new com.google.gson.JsonObject();
for (Entry<String, String> e : mainese.getPreferences().entrySet()) {
    if (e.getKey().startsWith("DEALER_EXPIRE_")) {
        //add to my result list
    	js.addProperty(e.getKey(), e.getValue());
    }
}	
setPopulateDealerJson(js.toString());
				preferences.setName(getBranchId());
				preferences.setPreferences(prefs);
				utilService.addOrganisationESE(preferences);

			} else if (preferences.getPreferences() == null || preferences.getPreferences().isEmpty()) {
				Map<String, String> prefs = new HashMap<String, String>();
				ESESystem mainese = utilService.findPrefernceById("1");
				prefs.put(ESESystem.INVALID_ATTEMPTS_COUNT,
						mainese.getPreferences().get(ESESystem.INVALID_ATTEMPTS_COUNT));
				prefs.put(ESESystem.MANU_REPACK_DEALER_EXPIRE_DAYS, mainese.getPreferences().get(ESESystem.MANU_REPACK_DEALER_EXPIRE_DAYS));
				prefs.put(ESESystem.CHEMICAL_EXPIRE_DAYS, mainese.getPreferences().get(ESESystem.CHEMICAL_EXPIRE_DAYS));
				prefs.put(ESESystem.TIME_TO_AUTO_RELEASE, mainese.getPreferences().get(ESESystem.TIME_TO_AUTO_RELEASE));
				prefs.put(ESESystem.AREA_CAPTURE_MODE, mainese.getPreferences().get(ESESystem.AREA_CAPTURE_MODE));
				prefs.put(ESESystem.GEO_FENCING_FLAG, mainese.getPreferences().get(ESESystem.GEO_FENCING_FLAG));
				prefs.put(ESESystem.GEO_FENCING_RADIUS_MT,
						mainese.getPreferences().get(ESESystem.GEO_FENCING_RADIUS_MT));
				prefs.put(ESESystem.CURRENT_SEASON_CODE, mainese.getPreferences().get(ESESystem.CURRENT_SEASON_CODE));

				prefs.put(ESESystem.INTEREST_MODULE, mainese.getPreferences().get(ESESystem.INTEREST_MODULE));
				prefs.put(ESESystem.RATE_OF_INTEREST, mainese.getPreferences().get(ESESystem.RATE_OF_INTEREST));
				prefs.put(ESESystem.ROI_EFFECTIVE_FROM, mainese.getPreferences().get(ESESystem.ROI_EFFECTIVE_FROM));
				prefs.put(ESESystem.ROI_EFFECT_EXISTING_FARMER,
						mainese.getPreferences().get(ESESystem.ROI_EFFECT_EXISTING_FARMER));
				prefs.put(ESESystem.EXPORT_RECORD_LIMIT, mainese.getPreferences().get(ESESystem.EXPORT_RECORD_LIMIT));
				prefs.put(ESESystem.GENERAL_DATE_FORMAT, mainese.getPreferences().get(ESESystem.GENERAL_DATE_FORMAT));

				prefs.put(ESESystem.ADDRESS_LINE1, mainese.getPreferences().get(ESESystem.ADDRESS_LINE1));
				prefs.put(ESESystem.ADDRESS_LINE2, mainese.getPreferences().get(ESESystem.ADDRESS_LINE2));
				prefs.put(ESESystem.ADDRESS_LINE3, mainese.getPreferences().get(ESESystem.ADDRESS_LINE3));
				prefs.put(ESESystem.ADDRESS_LINE4, mainese.getPreferences().get(ESESystem.ADDRESS_LINE4));
				prefs.put(ESESystem.ADDRESS_LINE5, mainese.getPreferences().get(ESESystem.ADDRESS_LINE5));
				prefs.put(ESESystem.ADDRESS_LINE6, mainese.getPreferences().get(ESESystem.ADDRESS_LINE6));
				prefs.put(ESESystem.ADDRESS_LINE7, mainese.getPreferences().get(ESESystem.ADDRESS_LINE7));
				prefs.put(ESESystem.ADDRESS_LINE8, mainese.getPreferences().get(ESESystem.ADDRESS_LINE8));
				prefs.put(ESESystem.ADDRESS_LINE9, mainese.getPreferences().get(ESESystem.ADDRESS_LINE9));
				prefs.put(ESESystem.ADDRESS_LINE10, mainese.getPreferences().get(ESESystem.ADDRESS_LINE10));
				prefs.put(ESESystem.ADDRESS_LINE11, mainese.getPreferences().get(ESESystem.ADDRESS_LINE11));
				prefs.put(ESESystem.ADDRESS_LINE12, mainese.getPreferences().get(ESESystem.ADDRESS_LINE12));
				prefs.put(ESESystem.ADDRESS_LINE13, mainese.getPreferences().get(ESESystem.ADDRESS_LINE13));
				prefs.put(ESESystem.ADDRESS_LINE14, mainese.getPreferences().get(ESESystem.ADDRESS_LINE14));
				prefs.put(ESESystem.ADDRESS_LINE15, mainese.getPreferences().get(ESESystem.ADDRESS_LINE15));
				prefs.put(ESESystem.ADDRESS_LINE16, mainese.getPreferences().get(ESESystem.ADDRESS_LINE16));
				prefs.put(ESESystem.ADDRESS_LINE17, mainese.getPreferences().get(ESESystem.ADDRESS_LINE17));
				prefs.put(ESESystem.BARCODE, mainese.getPreferences().get(ESESystem.BARCODE));
				utilService.editOrganisationPreference(prefs, preferences);
			}
		}

		setNoOfInvalidAttempts(preferences.getPreferences().get(ESESystem.INVALID_ATTEMPTS_COUNT));
		setTimeToAutoRelease(preferences.getPreferences().get(ESESystem.TIME_TO_AUTO_RELEASE));
		setDealerExpireDate(preferences.getPreferences().get(ESESystem.MANU_REPACK_DEALER_EXPIRE_DAYS));
		setDealerExpireDateStock(preferences.getPreferences().get(ESESystem.STOCK_DEALER_EXPIRE_DAYS));
		setChemicalExpireDate(preferences.getPreferences().get(ESESystem.CHEMICAL_EXPIRE_DAYS));
		setChemicalRenewalExpireDate(preferences.getPreferences().get(ESESystem.CHEMICAL_RENEWAL_EXPIRE_DAYS));
		setAeraCaptureMode(preferences.getPreferences().get(ESESystem.AREA_CAPTURE_MODE));
		setPremisesExpireDate(preferences.getPreferences().get(ESESystem.PREMISES_EXPIRE_DAYS));
		
		setImportAppExpire(preferences.getPreferences().get(ESESystem.IMPORT_DAYS));
		setSeedMerchantExpire(preferences.getPreferences().get(ESESystem.MERCHANT_EXPIRE_DAYS));
		setPermitExpireDate(preferences.getPreferences().get(ESESystem.PERMITAPPLN_EXPIRE_DAYS));
		setFumigatorExpireDate(preferences.getPreferences().get(ESESystem.FUM_EXPIRY_DAYS));
		setSeedDealerExpire(preferences.getPreferences().get(ESESystem.SEED_DEALER_EXPIRE_DAYS));
		setBreederSeedExpire(preferences.getPreferences().get(ESESystem.BREEDER_SEED_EXPIRE_DAYS));
		setGrowerExpire(preferences.getPreferences().get(ESESystem.GROWER_EXPIRY_DAYS));
		setSeedCertExpire(preferences.getPreferences().get(ESESystem.SEED_CERT_EXPIRE_DAYS));
		setPasswordMinLength(preferences.getPreferences().get(ESESystem.PASSWORD_MIN_LENGTH));
		setAge(preferences.getPreferences().get(ESESystem.AGE));
		setReminderDays(preferences.getPreferences().get(ESESystem.REMINDER_DAYS));

		
		com.google.gson.JsonObject js= new com.google.gson.JsonObject();
		for (Entry<String, String> e : preferences.getPreferences().entrySet()) {
		    if (e.getKey().startsWith("DEALER_EXPIRE_CG")) {
		        //add to my result list
		    	js.addProperty(e.getKey(), e.getValue());
		    }
		}
		setPopulateDealerJson(js.toString());
		if (!StringUtil.isEmpty(preferences.getPreferences().get(ESESystem.GEO_FENCING_FLAG))
				|| preferences.getPreferences().get(ESESystem.GEO_FENCING_FLAG) != null) {
			setGeoFincingFlg(
					preferences.getPreferences().get(ESESystem.GEO_FENCING_FLAG).equals("1") ? "true" : "false");
		}

		setGeoFincingRadius(preferences.getPreferences().get(ESESystem.GEO_FENCING_RADIUS_MT));
		
		setCurrencyTypez(preferences.getPreferences().get(ESESystem.CURRENCY_TYPE));
		
		setAddressLine1(preferences.getPreferences().get(ESESystem.ADDRESS_LINE1));
		setAddressLine2(preferences.getPreferences().get(ESESystem.ADDRESS_LINE2));
		setAddressLine3(preferences.getPreferences().get(ESESystem.ADDRESS_LINE3));
		setAddressLine4(preferences.getPreferences().get(ESESystem.ADDRESS_LINE4));
		setAddressLine5(preferences.getPreferences().get(ESESystem.ADDRESS_LINE5));
		setAddressLine6(preferences.getPreferences().get(ESESystem.ADDRESS_LINE6));
		setAddressLine7(preferences.getPreferences().get(ESESystem.ADDRESS_LINE7));
		setAddressLine8(preferences.getPreferences().get(ESESystem.ADDRESS_LINE8));
		setAddressLine9(preferences.getPreferences().get(ESESystem.ADDRESS_LINE9));
		setAddressLine10(preferences.getPreferences().get(ESESystem.ADDRESS_LINE10));
		setAddressLine11(preferences.getPreferences().get(ESESystem.ADDRESS_LINE11));
		setAddressLine12(preferences.getPreferences().get(ESESystem.ADDRESS_LINE12));
		setAddressLine13(preferences.getPreferences().get(ESESystem.ADDRESS_LINE13));
		setAddressLine14(preferences.getPreferences().get(ESESystem.ADDRESS_LINE14));
		setAddressLine15(preferences.getPreferences().get(ESESystem.ADDRESS_LINE15));
		setAddressLine16(preferences.getPreferences().get(ESESystem.ADDRESS_LINE16));
		setAddressLine17(preferences.getPreferences().get(ESESystem.ADDRESS_LINE17));
		setBarcode(preferences.getPreferences().get(ESESystem.ADDRESS_LINE17));

		HarvestSeason harvestSeason = farmerService
				.findHarvestSeasonByCode(preferences.getPreferences().get(ESESystem.CURRENT_SEASON_CODE));

		if (!ObjectUtil.isEmpty(harvestSeason))
			setCurrentSeasonId(harvestSeason.getCode());

		setUser(getExecUser());
		Asset assetLogin = utilService.findAssetsByAssetCode(Asset.APP_LOGO);
		if (assetLogin.getFile() != null) {
			setLoginLogoImageByteString(Base64Util.encoder(assetLogin.getFile())); // farmer.getImageInfo().getPhoto().getImage()
		}

		Asset assetFavIcon = utilService.findAssetsByAssetCode(Asset.FAVICON);
		if (assetFavIcon.getFile() != null) {
			setFavIconImageByteString(Base64Util.encoder(assetFavIcon.getFile())); // farmer.getImageInfo().getPhoto().getImage()
		}

		isInterestModule = preferences.getPreferences().get(ESESystem.INTEREST_MODULE);

		String rateOfInterstStr = preferences.getPreferences().get(ESESystem.RATE_OF_INTEREST);
		if (!StringUtil.isEmpty(rateOfInterstStr)) {
			String[] rateOfInterstStrArr = rateOfInterstStr.trim().split("\\.");
			rateOfInterest = rateOfInterstStrArr[0];
			rateOfInterestDecimal = rateOfInterstStrArr[1];
		} else {
			rateOfInterest = "0";
			rateOfInterestDecimal = "0";
		}
		roiEffectiveFrom = preferences.getPreferences().get(ESESystem.ROI_EFFECTIVE_FROM);
		roiApplyExist = preferences.getPreferences().get(ESESystem.ROI_EFFECT_EXISTING_FARMER);

		/*
		 * setDailyClosingTime(preferences.getPreferences().get(ESESystem.
		 * DAILY_CLOSIGN_TIME));
		 * setUserName(preferences.getPreferences().get(ESESystem.USER_NAME));
		 * setPassword(preferences.getPreferences().get(ESESystem.PASSWORD));
		 * setMailHost(preferences.getPreferences().get(ESESystem.MAIL_HOST));
		 * setPort(preferences.getPreferences().get(ESESystem.PORT));
		 */
		// setTheme(preferences.getPreferences().get(ESESystem.THEME));
		setLoginUserName(request.getSession().getAttribute("user").toString());
		setEnableLoanModule(utilService.findPrefernceByName(ESESystem.ENABLE_LOAN_MODULE));
		return "list";
	}

	/**
	 * Update.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String update() throws Exception {

		if (!StringUtil.isEmpty(temp)) {
			HashMap<String, String> preferences = new HashMap();
			preferences.put(ESESystem.INVALID_ATTEMPTS_COUNT, getNoOfInvalidAttempts());
			preferences.put(ESESystem.TIME_TO_AUTO_RELEASE, getTimeToAutoRelease());
					preferences.put(ESESystem.PASSWORD_MIN_LENGTH, getPasswordMinLength());
			preferences.put(ESESystem.AGE, getAge());
			preferences.put(ESESystem.REMINDER_DAYS, getReminderDays());
			
			System.out.println(getDealerExpireJson());
			
			//seedDealerExpire
			if(!StringUtil.isEmpty( getCurrencyTypez())&&  getCurrencyTypez()!=null ){
			preferences.put(ESESystem.CURRENCY_TYPE, getCurrencyTypez());
			}
			preferences.put(ESESystem.AREA_CAPTURE_MODE,
					StringUtil.isEmpty(getAeraCaptureMode()) ? "" : getAeraCaptureMode());
			if (!StringUtil.isEmpty(getGeoFincingFlg())) {
				boolean flag = Boolean.parseBoolean(getGeoFincingFlg());
				if (flag) {
					preferences.put(ESESystem.GEO_FENCING_FLAG, "1");
					preferences.put(ESESystem.GEO_FENCING_RADIUS_MT, getGeoFincingRadius());
				} else {
					preferences.put(ESESystem.GEO_FENCING_FLAG, "0");
					preferences.put(ESESystem.GEO_FENCING_RADIUS_MT, "");
				}
			}

			// HarvestSeason harvestSeason =
			// farmerService.findHarvestSeasonByCode(getCurrentSeasonId());
			preferences.put(ESESystem.CURRENT_SEASON_CODE, getCurrentSeasonId());

			setUser(getExecUser());

			preferences.put(ESESystem.ADDRESS_LINE1, getAddressLine1());
			preferences.put(ESESystem.ADDRESS_LINE2, getAddressLine2());
			preferences.put(ESESystem.ADDRESS_LINE3, getAddressLine3());
			preferences.put(ESESystem.ADDRESS_LINE4, getAddressLine4());
			preferences.put(ESESystem.ADDRESS_LINE5, getAddressLine5());
			preferences.put(ESESystem.ADDRESS_LINE6, getAddressLine6());
			preferences.put(ESESystem.ADDRESS_LINE7, getAddressLine7());
			preferences.put(ESESystem.ADDRESS_LINE8, getAddressLine8());
			preferences.put(ESESystem.ADDRESS_LINE9, getAddressLine9());
			preferences.put(ESESystem.ADDRESS_LINE10, getAddressLine10());
			preferences.put(ESESystem.ADDRESS_LINE11, getAddressLine11());
			preferences.put(ESESystem.ADDRESS_LINE12, getAddressLine12());
			preferences.put(ESESystem.ADDRESS_LINE13, getAddressLine13());
			preferences.put(ESESystem.ADDRESS_LINE14, getAddressLine14());
			preferences.put(ESESystem.ADDRESS_LINE15, getAddressLine15());
			preferences.put(ESESystem.ADDRESS_LINE16, getAddressLine16());
			preferences.put(ESESystem.ADDRESS_LINE17, getAddressLine17());
			preferences.put(ESESystem.BARCODE, getBarcode());

			 if (getBranchId() != null && !getBranchId().isEmpty()) {
					if (this.getLoginLogoImage() != null) {
						Asset existingAssetLogin = utilService.findAssetsByAssetCode(Asset.APP_LOGO);
						existingAssetLogin.setFile(FileUtil.getBinaryFileContent(this.getLoginLogoImage()));
						utilService.updateAsset(existingAssetLogin);
					}

					if (this.getFavIconImage() != null) {
						Asset existingAssetFavIcon = utilService.findAssetsByAssetCode(Asset.FAVICON);
						existingAssetFavIcon.setFile(FileUtil.getBinaryFileContent(this.getFavIconImage()));
						utilService.updateAsset(existingAssetFavIcon);
					}
				ESESystem ese = utilService.findPrefernceByOrganisationId(getBranchId());
				utilService.editOrganisationPreference(preferences, ese);
				
				addActionMessage(getText("sucess"));
			}
			
			
			/*
			 * ESESystem eseSystem = preferncesService.findPrefernceById("1");
			 * Map<String, String> exPreferences = eseSystem.getPreferences();
			 * isInterestModule = exPreferences.get(ESESystem.INTEREST_MODULE);
			 * if (isInterestModule.equals("1")) { if
			 * (!exPreferences.get(ESESystem.RATE_OF_INTEREST).equals(
			 * rateOfInterest + "." + rateOfInterestDecimal) ||
			 * !exPreferences.get(ESESystem.ROI_EFFECTIVE_FROM)
			 * .equals(roiEffectiveFrom) ||
			 * !exPreferences.get(ESESystem.ROI_EFFECT_EXISTING_FARMER).equals(
			 * roiApplyExist)) { preferences.put(ESESystem.RATE_OF_INTEREST,
			 * rateOfInterest + "." + rateOfInterestDecimal);
			 * preferences.put(ESESystem.ROI_EFFECTIVE_FROM, roiEffectiveFrom);
			 * preferences.put(ESESystem.ROI_EFFECT_EXISTING_FARMER,
			 * roiApplyExist); if (!ObjectUtil.isLong(rateOfInterest) ||
			 * !ObjectUtil.isLong(rateOfInterestDecimal)) {
			 * addActionError(getText("invalidRoi")); return execute(); } double
			 * fullRoi = Double.parseDouble(rateOfInterest + "." +
			 * rateOfInterestDecimal); InterestRateHistory interestRateHistory =
			 * new InterestRateHistory();
			 * interestRateHistory.setRateOfInterest(fullRoi);
			 * if(!StringUtil.isEmpty(roiEffectiveFrom)){
			 * interestRateHistory.setRoiEffFrom(DateUtil.convertStringToDate(
			 * roiEffectiveFrom, DateUtil.DATE_FORMAT)); }
			 * interestRateHistory.setAffectExistingFarmerBal(roiApplyExist
			 * .equalsIgnoreCase("true") ? 1 : 0);
			 * interestRateHistory.setIsActive(1);
			 * interestRateHistory.setCreateUserName(request.getSession().
			 * getAttribute("user") .toString());
			 * preferncesService.addInterestRateHistory(interestRateHistory); }
			 * }
			 */

			/*
			 * Pattern time = Pattern.compile(EXPRESSION_TIME); if
			 * (getDailyClosingTime() != null) { Matcher match =
			 * time.matcher(getDailyClosingTime()); if (!match.matches()) {
			 * addActionError(getText("invalid.dailyClosingTimeFormat"));
			 * request.setAttribute(HEADING, getText("create.page")); return
			 * INPUT; } else { preferences.put(ESESystem.DAILY_CLOSIGN_TIME,
			 * getDailyClosingTime()); } } preferences.put(ESESystem.USER_NAME,
			 * getUserName()); preferences.put(ESESystem.PASSWORD,
			 * getPassword()); preferences.put(ESESystem.MAIL_HOST,
			 * getMailHost()); preferences.put(ESESystem.PORT, getPort());
			 */

			// preferences.put(ESESystem.THEME,
			// getDefaultThemeIfThemeNotFound());

		}
		return list();
	}

	public String history() {

		return "list";
	}


	/*@SuppressWarnings("unchecked")
	public JSONObject toJSON(Object obj) {

		InterestRateHistory rateHistory = (InterestRateHistory) obj;
		JSONObject jsonObject = new JSONObject();
		JSONArray rows = new JSONArray();
		rows.add(String.valueOf(rateHistory.getRateOfInterest()));
		rows.add(DateUtil.convertDateToString(rateHistory.getRoiEffFrom(), DateUtil.DATE_FORMAT));
		rows.add(rateHistory.getAffectExistingFarmerBal() == 0 ? getText("no") : getText("yes"));
		rows.add(rateHistory.getIsActive() == 0 ? getText("inActive") : getText("active"));
		jsonObject.put("id", rateHistory.getId());
		jsonObject.put("cell", rows);
		return jsonObject;
	}*/

	/**
	 * @see com.sourcetrace.esesw.view.SwitchValidatorAction#getData()
	 */
	@Override
	public HashMap<String, String> getData() {

		command = "update";
		request.setAttribute("heading", getText("update"));
		if (ActionContext.getContext().getName().equalsIgnoreCase("roi_history")) {
			request.setAttribute("heading", getText("interestHistory"));
		}
		preferencesmap.put(ESESystem.INVALID_ATTEMPTS_COUNT, getNoOfInvalidAttempts());
		preferencesmap.put(ESESystem.TIME_TO_AUTO_RELEASE, getTimeToAutoRelease());
		preferencesmap.put(ESESystem.MANU_REPACK_DEALER_EXPIRE_DAYS, getDealerExpireDate());
		preferencesmap.put(ESESystem.STOCK_DEALER_EXPIRE_DAYS, getDealerExpireDateStock());
		preferencesmap.put(ESESystem.CHEMICAL_EXPIRE_DAYS, getChemicalExpireDate());
		preferencesmap.put(ESESystem.CHEMICAL_RENEWAL_EXPIRE_DAYS, getChemicalRenewalExpireDate());
		preferencesmap.put(ESESystem.GEO_FENCING_FLAG, getGeoFincingFlg());
		preferencesmap.put(ESESystem.GEO_FENCING_RADIUS_MT, getGeoFincingRadius());
		preferencesmap.put(ESESystem.PREMISES_EXPIRE_DAYS, getPremisesExpireDate());
		preferencesmap.put(ESESystem.FUM_EXPIRY_DAYS, getFumigatorExpireDate());
		preferencesmap.put(ESESystem.INTEREST_MODULE, isInterestModule);
		preferencesmap.put(ESESystem.IMPORT_DAYS, getImportAppExpire());
		preferencesmap.put(ESESystem.MERCHANT_EXPIRE_DAYS, getSeedMerchantExpire());
		preferencesmap.put(ESESystem.SEED_DEALER_EXPIRE_DAYS, getSeedDealerExpire());
		preferencesmap.put(ESESystem.BREEDER_SEED_EXPIRE_DAYS, getBreederSeedExpire());
		preferencesmap.put(ESESystem.GROWER_EXPIRY_DAYS, getGrowerExpire());
		preferencesmap.put(ESESystem.SEED_CERT_EXPIRE_DAYS, getSeedCertExpire());
		preferencesmap.put(ESESystem.RATE_OF_INTEREST, rateOfInterest + "." + rateOfInterestDecimal);
		preferencesmap.put(ESESystem.ROI_EFFECTIVE_FROM, roiEffectiveFrom);
		preferencesmap.put(ESESystem.ROI_EFFECT_EXISTING_FARMER, roiApplyExist);
		preferencesmap.put(ESESystem.PASSWORD_MIN_LENGTH, getPasswordMinLength());
		preferencesmap.put(ESESystem.AGE, getAge());
		preferencesmap.put(ESESystem.REMINDER_DAYS, getReminderDays());

		/*
		 * preferencesmap.put(ESESystem.DAILY_CLOSIGN_TIME,
		 * getDailyClosingTime()); preferencesmap.put(ESESystem.USER_NAME,
		 * getUserName()); preferencesmap.put(ESESystem.PASSWORD,
		 * getPassword()); preferencesmap.put(ESESystem.MAIL_HOST,
		 * getMailHost()); preferencesmap.put(ESESystem.PORT, getPort());
		 */
		preferencesmap.put(ESESystem.THEME, getTheme());
		if (!StringUtil.isEmpty(temp))
			return preferencesmap;
		else
			return null;

	}

	/**
	 * Sets the prefernces service.
	 * 
	 * @param preferncesService
	 *            the new prefernces service
	 */
	/**
	 * Gets the preferences.
	 * 
	 * @return the preferences
	 */
	public ESESystem getPreferences() {

		return preferences;
	}

	/**
	 * Sets the preferences.
	 * 
	 * @param preferences
	 *            the new preferences
	 */
	public void setPreferences(ESESystem preferences) {

		this.preferences = preferences;
	}

	/**
	 * Gets the no of invalid attempts.
	 * 
	 * @return the no of invalid attempts
	 */
	public String getNoOfInvalidAttempts() {

		return noOfInvalidAttempts;
	}

	/**
	 * Sets the no of invalid attempts.
	 * 
	 * @param noOfInvalidAttempts
	 *            the new no of invalid attempts
	 */
	public void setNoOfInvalidAttempts(String noOfInvalidAttempts) {

		this.noOfInvalidAttempts = noOfInvalidAttempts;
	}

	/**
	 * Gets the time to auto release.
	 * 
	 * @return the time to auto release
	 */
	public String getTimeToAutoRelease() {

		return timeToAutoRelease;
	}

	/**
	 * Sets the time to auto release.
	 * 
	 * @param timeToAutoRelease
	 *            the new time to auto release
	 */
	public void setTimeToAutoRelease(String timeToAutoRelease) {

		this.timeToAutoRelease = timeToAutoRelease;
	}

	/**
	 * Gets the daily closing time.
	 * 
	 * @return the daily closing time
	 */
	public String getDailyClosingTime() {

		return dailyClosingTime;
	}

	/**
	 * Sets the daily closing time.
	 * 
	 * @param dailyClosingTime
	 *            the new daily closing time
	 */
	public void setDailyClosingTime(String dailyClosingTime) {

		this.dailyClosingTime = dailyClosingTime;
	}

	/**
	 * Gets the time stamp.
	 * 
	 * @return the time stamp
	 */
	public Date getTimeStamp() {

		return timeStamp;
	}

	/**
	 * Sets the time stamp.
	 * 
	 * @param timeStamp
	 *            the new time stamp
	 */
	public void setTimeStamp(Date timeStamp) {

		this.timeStamp = timeStamp;
	}

	/**
	 * Gets the temp.
	 * 
	 * @return the temp
	 */
	public String getTemp() {

		return temp;
	}

	/**
	 * Sets the temp.
	 * 
	 * @param temp
	 *            the new temp
	 */
	public void setTemp(String temp) {

		this.temp = temp;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {

		return id;
	}

	/**
	 * Gets the start period.
	 * 
	 * @return the start period
	 */
	public String getStartPeriod() {

		return startPeriod;
	}

	/**
	 * Sets the start period.
	 * 
	 * @param startPeriod
	 *            the new start period
	 */
	public void setStartPeriod(String startPeriod) {

		this.startPeriod = startPeriod;
	}

	/**
	 * Gets the end period.
	 * 
	 * @return the end period
	 */
	public String getEndPeriod() {

		return endPeriod;
	}

	/**
	 * Sets the end period.
	 * 
	 * @param endPeriod
	 *            the new end period
	 */
	public void setEndPeriod(String endPeriod) {

		this.endPeriod = endPeriod;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {

		this.id = id;
	}

	/**
	 * Gets the preferencesmap.
	 * 
	 * @return the preferencesmap
	 */
	public HashMap<String, String> getPreferencesmap() {

		return preferencesmap;
	}

	/**
	 * Gets the dev type.
	 * 
	 * @return the dev type
	 */
	public Map<Integer, String> getDevType() {

		return devType;
	}

	/**
	 * Gets the period type.
	 * 
	 * @return the period type
	 */
	public List<Integer> getPeriodType() {

		return periodType;
	}

	/**
	 * Sets the period type.
	 * 
	 * @param periodType
	 *            the new period type
	 */
	public void setPeriodType(List<Integer> periodType) {

		this.periodType = periodType;
	}

	/**
	 * Set dev type.
	 * 
	 * @param devType
	 */
	public void setDevType(Map<Integer, String> devType) {

		this.devType = devType;
	}

	/**
	 * Set preferencesmap.
	 * 
	 * @param preferencesmap
	 */
	public void setPreferencesmap(HashMap<String, String> preferencesmap) {

		this.preferencesmap = preferencesmap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	/**
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {

		return super.execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.view.ESEValidatorAction#getData()
	 */

	/**
	 * Sets the user name.
	 * 
	 * @param userName
	 *            the new user name
	 */
	public void setUserName(String userName) {

		this.userName = userName;
	}

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName() {

		return userName;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {

		this.password = password;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {

		return password;
	}

	/**
	 * Sets the mail host.
	 * 
	 * @param mailHost
	 *            the new mail host
	 */
	public void setMailHost(String mailHost) {

		this.mailHost = mailHost;
	}

	/**
	 * Gets the mail host.
	 * 
	 * @return the mail host
	 */
	public String getMailHost() {

		return mailHost;
	}

	/**
	 * Sets the port.
	 * 
	 * @param port
	 *            the new port
	 */
	public void setPort(String port) {

		this.port = port;
	}

	/**
	 * Gets the port.
	 * 
	 * @return the port
	 */
	public String getPort() {

		return port;
	}

	/**
	 * Gets the theme.
	 * 
	 * @return the theme
	 */
	public String getTheme() {

		return theme;
	}

	/**
	 * Gets the default theme if theme not found.
	 * 
	 * @return the default theme if theme not found
	 */
	public String getDefaultThemeIfThemeNotFound() {

		String cssName = request.getRealPath("/") + "css/" + getTheme() + ".css";
		File cssFile = new File(cssName);
		if (!cssFile.exists())
			setTheme(getText("default.theme"));
		return getTheme();
	}

	/**
	 * Sets the theme.
	 * 
	 * @param theme
	 *            the new theme
	 */
	public void setTheme(String theme) {

		this.theme = theme;
	}

	/**
	 * Gets the prefernces service.
	 * 
	 * @return the prefernces service
	 */
	/**
	 * Sets the agent transaction session.
	 * 
	 * @param agentTransactionSession
	 *            the new agent transaction session
	 */
	public void setAgentTransactionSession(String agentTransactionSession) {

		this.agentTransactionSession = agentTransactionSession;
	}

	/**
	 * Gets the agent transaction session.
	 * 
	 * @return the agent transaction session
	 */
	public String getAgentTransactionSession() {

		return agentTransactionSession;
	}

	/**
	 * Sets the login user name.
	 * 
	 * @param loginUserName
	 *            the new login user name
	 */
	public void setLoginUserName(String loginUserName) {

		this.loginUserName = loginUserName;
	}

	/**
	 * Gets the login user name.
	 * 
	 * @return the login user name
	 */
	public String getLoginUserName() {

		return loginUserName;
	}

	/**
	 * Gets the dev type list.
	 * 
	 * @return the dev type list
	 */
	public Map<Integer, String> getDevTypeList() {

		devType.put(LicenseType.DEMO.ordinal(), getText("devType0"));
		devType.put(LicenseType.PRE_PRODUCTION.ordinal(), getText("devType1"));
		devType.put(LicenseType.PRODUCTION.ordinal(), getText("devType2"));
		return devType;
	}

	/**
	 * Sets the period.
	 * 
	 * @param period
	 *            the new period
	 */
	public void setPeriod(String period) {

		this.period = period;
	}

	/**
	 * Gets the period.
	 * 
	 * @return the period
	 */
	public String getPeriod() {

		return period;
	}

	/**
	 * Generate end date.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void generateEndDate() throws Exception {

		String result = "";
		Date startDate = new Date();
		result = DateUtil.plusMonth(inFormat.format(startDate), Integer.parseInt(period), DateUtil.DATE_FORMAT);
		response.getWriter()
				.print(getText(outFormat.format(DateUtil.convertStringToDate(result, DateUtil.DATE_FORMAT))));
	}

	/**
	 * Sets the dev type value.
	 * 
	 * @param devTypeValue
	 *            the new dev type value
	 */
	public void setDevTypeValue(String devTypeValue) {

		this.devTypeValue = devTypeValue;
	}

	/**
	 * Gets the dev type value.
	 * 
	 * @return the dev type value
	 */
	public String getDevTypeValue() {

		return devTypeValue;
	}

	/**
	 * Sets the file name.
	 * 
	 * @param fileName
	 *            the new file name
	 */
	public void setFileName(String fileName) {

		this.fileName = fileName;
	}

	/**
	 * Gets the file name.
	 * 
	 * @return the file name
	 */
	public String getFileName() {

		return fileName;
	}

	/**
	 * Sets the version.
	 * 
	 * @param version
	 *            the new version
	 */
	public void setVersion(String version) {

		this.version = version;
	}

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public String getVersion() {

		return version;
	}

	/**
	 * Sets the license key generator.
	 * 
	 * @param licenseKeyGenerator
	 *            the new license key generator
	 */
	public void setLicenseKeyGenerator(ILicenseKeyGenerator licenseKeyGenerator) {

		this.licenseKeyGenerator = licenseKeyGenerator;
	}

	/**
	 * Gets the license key generator.
	 * 
	 * @return the license key generator
	 */
	public ILicenseKeyGenerator getLicenseKeyGenerator() {

		return licenseKeyGenerator;
	}

	/**
	 * Sets the license owner.
	 * 
	 * @param licenseOwner
	 *            the new license owner
	 */
	public void setLicenseOwner(String licenseOwner) {

		this.licenseOwner = licenseOwner;
	}

	/**
	 * Gets the license owner.
	 * 
	 * @return the license owner
	 */
	public String getLicenseOwner() {

		return licenseOwner;
	}

	/**
	 * Sets the license client.
	 * 
	 * @param licenseClient
	 *            the new license client
	 */
	public void setLicenseClient(String licenseClient) {

		this.licenseClient = licenseClient;
	}

	/**
	 * Gets the license client.
	 * 
	 * @return the license client
	 */
	public String getLicenseClient() {

		return licenseClient;
	}

	/**
	 * Generate license.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String generateLicense() throws Exception {

		setFileName(getText("licenseKey"));

		License license = new License();
		license.setVer(version);
		license.setStart(DateUtil.convertStringToDate(startPeriod, "MMddyyyy"));
		license.setEnd(DateUtil.convertStringToDate(endPeriod, "MMddyyyy"));
		license.setOwner("SourceTrace");
		license.setClient(licenseClient);
		if (LicenseType.DEMO.ordinal() == Integer.parseInt(devTypeValue))
			license.setLicType(LicenseType.DEMO);
		else if (LicenseType.PRE_PRODUCTION.ordinal() == Integer.parseInt(devTypeValue))
			license.setLicType(LicenseType.PRE_PRODUCTION);
		else if (LicenseType.PRODUCTION.ordinal() == Integer.parseInt(devTypeValue))
			license.setLicType(LicenseType.PRODUCTION);

	    //license.setKey(licenseKeyGenerator.generate(license.getText()));
		fileInputStream = new ByteArrayInputStream(license.getLicKey().getBytes());
		return TEXT_RETURN_TYPE;
	}

	/**
	 * Sets the file input stream.
	 * 
	 * @param fileInputStream
	 *            the new file input stream
	 */
	public void setFileInputStream(InputStream fileInputStream) {

		this.fileInputStream = fileInputStream;
	}

	/**
	 * Gets the file input stream.
	 * 
	 * @return the file input stream
	 */
	public InputStream getFileInputStream() {

		return fileInputStream;
	}

	/**
	 * Gets the period list.
	 * 
	 * @return the period list
	 */
	public List<Integer> getPeriodList() {

		for (int i = 1; i <= 24; i++)
			periodType.add(i);
		return periodType;

	}

	/**
	 * Gets the aera capture mode type.
	 * 
	 * @return the aera capture mode type
	 */
	public Map<String, String> getAeraCaptureModeType() {

		aeraCaptureModeType.put(String.valueOf(AreaCaptureTypes.MANUAL.ordinal()),
				this.getLocaleProperty("preference.manual"));
		aeraCaptureModeType.put(String.valueOf(AreaCaptureTypes.GPS.ordinal()),
				this.getLocaleProperty("preference.gps"));
		aeraCaptureModeType.put(String.valueOf(AreaCaptureTypes.BOTH.ordinal()),
				this.getLocaleProperty("preference.both"));

		return aeraCaptureModeType;
	}

	/**
	 * Set aera capture mode type.
	 * 
	 * @param aeraCaptureModeType
	 */
	public void setAeraCaptureModeType(Map<String, String> aeraCaptureModeType) {

		this.aeraCaptureModeType = aeraCaptureModeType;
	}

	public Map<String, String> getEnableBranchTypes() {

		enableBranchTypes.put(String.valueOf(EnableBranchTypes.YES.ordinal()), EnableBranchTypes.YES.toString());
		enableBranchTypes.put(String.valueOf(EnableBranchTypes.NO.ordinal()), EnableBranchTypes.NO.toString());
		return enableBranchTypes;
	}

	public void setEnableBranchTypes(Map<String, String> enableBranchTypes) {

		this.enableBranchTypes = enableBranchTypes;
	}

	public Map<String, String> getCodeTypeValues() {

		Map<String, String> values = new LinkedHashMap<>();
		values.put(String.valueOf(EnableBranchTypes.NO.ordinal()), getText("normalCode"));
		values.put(String.valueOf(EnableBranchTypes.YES.ordinal()), getText("hhCode"));
		return values;
	}

	public Map<Integer, String> getBranchList() {

		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		List<BranchMaster> branchList = utilService.listBranchMastersAndDisableFilter();
		map = ReflectUtil.buildMap(branchList, new String[] { "Id", "name" });
		return map;
	}

	public Map<String, String> getEnableMultiProductTypes() {

		enableMultiProductTypes.put(String.valueOf(EnableMultiProductTypes.YES.ordinal()),
				EnableMultiProductTypes.YES.toString());
		enableMultiProductTypes.put(String.valueOf(EnableMultiProductTypes.NO.ordinal()),
				EnableMultiProductTypes.NO.toString());
		return enableMultiProductTypes;
	}

	public void setEnableMultiProductTypes(Map<String, String> enableMultiProductTypes) {

		this.enableMultiProductTypes = enableMultiProductTypes;
	}

	/**
	 * Gets the rate of interest.
	 * 
	 * @return the rate of interest
	 */
	public String getRateOfInterest() {

		return rateOfInterest;
	}

	/**
	 * Sets the rate of interest.
	 * 
	 * @param rateOfInterest
	 *            the new rate of interest
	 */
	public void setRateOfInterest(String rateOfInterest) {

		this.rateOfInterest = rateOfInterest;
	}

	/**
	 * Gets the roi effective from.
	 * 
	 * @return the roi effective from
	 */
	public String getRoiEffectiveFrom() {

		return roiEffectiveFrom;
	}

	/**
	 * Sets the roi effective from.
	 * 
	 * @param roiEffectiveFrom
	 *            the new roi effective from
	 */
	public void setRoiEffectiveFrom(String roiEffectiveFrom) {

		this.roiEffectiveFrom = roiEffectiveFrom;
	}

	/**
	 * Gets the roi apply exist.
	 * 
	 * @return the roi apply exist
	 */
	public String getRoiApplyExist() {

		return roiApplyExist;
	}

	/**
	 * Sets the roi apply exist.
	 * 
	 * @param roiApplyExist
	 *            the new roi apply exist
	 */
	public void setRoiApplyExist(String roiApplyExist) {

		this.roiApplyExist = roiApplyExist;
	}

	/**
	 * Gets the is interest module.
	 * 
	 * @return the is interest module
	 */
	public String getIsInterestModule() {

		return isInterestModule;
	}

	/**
	 * Sets the is interest module.
	 * 
	 * @param isInterestModule
	 *            the new is interest module
	 */
	public void setIsInterestModule(String isInterestModule) {

		this.isInterestModule = isInterestModule;
	}

	/**
	 * Gets the rate of interest decimal.
	 * 
	 * @return the rate of interest decimal
	 */
	public String getRateOfInterestDecimal() {

		return rateOfInterestDecimal;
	}

	/**
	 * Sets the rate of interest decimal.
	 * 
	 * @param rateOfInterestDecimal
	 *            the new rate of interest decimal
	 */
	public void setRateOfInterestDecimal(String rateOfInterestDecimal) {

		this.rateOfInterestDecimal = rateOfInterestDecimal;
	}

	/**
	 * Gets the aera capture mode.
	 * 
	 * @return the aera capture mode
	 */
	public String getAeraCaptureMode() {

		return aeraCaptureMode;
	}

	/**
	 * Sets the aera capture mode.
	 * 
	 * @param aeraCaptureMode
	 *            the new aera capture mode
	 */
	public void setAeraCaptureMode(String aeraCaptureMode) {

		this.aeraCaptureMode = aeraCaptureMode;
	}

	/**
	 * Gets the geo fincing flg.
	 * 
	 * @return the geo fincing flg
	 */
	public String getGeoFincingFlg() {

		return geoFincingFlg;
	}

	/**
	 * Sets the geo fincing flg.
	 * 
	 * @param geoFincingFlg
	 *            the new geo fincing flg
	 */
	public void setGeoFincingFlg(String geoFincingFlg) {

		this.geoFincingFlg = geoFincingFlg;
	}

	/**
	 * Gets the geo fincing radius.
	 * 
	 * @return the geo fincing radius
	 */
	public String getGeoFincingRadius() {

		return geoFincingRadius;
	}

	/**
	 * Sets the geo fincing radius.
	 * 
	 * @param geoFincingRadius
	 *            the new geo fincing radius
	 */
	public void setGeoFincingRadius(String geoFincingRadius) {

		this.geoFincingRadius = geoFincingRadius;
	}

	public String getEnableBranch() {

		return enableBranch;
	}

	public void setEnableBranch(String enableBranch) {

		this.enableBranch = enableBranch;
	}

	public Long getBranchTenantId() {

		return branchTenantId;
	}

	public void setBranchTenantId(Long branchTenantId) {

		this.branchTenantId = branchTenantId;
	}

	public String getMainBranchName() {

		return mainBranchName;
	}

	public void setMainBranchName(String mainBranchName) {

		this.mainBranchName = mainBranchName;
	}

	public String getEnableMultiProduct() {

		return enableMultiProduct;
	}

	public void setEnableMultiProduct(String enableMultiProduct) {

		this.enableMultiProduct = enableMultiProduct;
	}

	public String getLoginLogoImageByteString() {

		return loginLogoImageByteString;
	}

	public void setLoginLogoImageByteString(String loginLogoImageByteString) {

		this.loginLogoImageByteString = loginLogoImageByteString;
	}

	public String getFavIconImageByteString() {

		return favIconImageByteString;
	}

	public void setFavIconImageByteString(String favIconImageByteString) {

		this.favIconImageByteString = favIconImageByteString;
	}

	public File getLoginLogoImage() {

		return loginLogoImage;
	}

	public void setLoginLogoImage(File loginLogoImage) {

		this.loginLogoImage = loginLogoImage;
	}

	public File getFavIconImage() {

		return favIconImage;
	}

	public void setFavIconImage(File favIconImage) {

		this.favIconImage = favIconImage;
	}

	public static String getExecUser() {

		return EXEC_USER;
	}

	public String getUser() {

		return user;
	}

	public void setUser(String user) {

		this.user = user;
	}

	public String getEnableGrampanjayat() {

		return enableGrampanjayat;
	}

	public void setEnableGrampanjayat(String enableGrampanjayat) {

		this.enableGrampanjayat = enableGrampanjayat;
	}

	public Map<String, String> getEnableGrampanjayatTypes() {

		enableGrampanjayatTypes.put(String.valueOf(EnableGrampanjayatTypes.YES.ordinal()),
				EnableGrampanjayatTypes.YES.toString());
		enableGrampanjayatTypes.put(String.valueOf(EnableGrampanjayatTypes.NO.ordinal()),
				EnableGrampanjayatTypes.NO.toString());
		return enableGrampanjayatTypes;
	}

	public void setEnableGrampanjayatTypes(Map<String, String> enableGrampanjayatTypes) {

		this.enableGrampanjayatTypes = enableGrampanjayatTypes;
	}

	public String getEnableFPOFG() {

		return enableFPOFG;
	}

	public void setEnableFPOFG(String enableFPOFG) {

		this.enableFPOFG = enableFPOFG;
	}

	public String getEnableFarmerCode() {

		return enableFarmerCode;
	}

	public void setEnableFarmerCode(String enableFarmerCode) {

		this.enableFarmerCode = enableFarmerCode;
	}

	public Map<String, String> getCurrentSeasonList() {

		Map<String, String> map = new LinkedHashMap<String, String>();
		List<Object[]> seasonList = farmerService.listSeasonCodeAndName();
		// map = ReflectUtil.buildMap(seasonList, new String[] { "code", "name"
		// });

		for (Object[] objects : seasonList) {
			map.put(String.valueOf(objects[0]), String.valueOf(objects[1]));
		}
		return map;
	}

	public String getCurrentSeasonId() {

		return currentSeasonId;
	}

	public void setCurrentSeasonId(String currentSeasonId) {

		this.currentSeasonId = currentSeasonId;
	}

	public String getCodeType() {

		return codeType;
	}

	public void setCodeType(String codeType) {

		this.codeType = codeType;
	}

	public String getSupplierType() {

		return supplierType;
	}

	public void setSupplierType(String supplierType) {

		this.supplierType = supplierType;
	}

	public Map<String, String> getIcsNameList() {

		icsNameList.put(String.valueOf(ICSName.YES.ordinal()), ICSName.YES.toString());
		icsNameList.put(String.valueOf(ICSName.NO.ordinal()), ICSName.NO.toString());

		return icsNameList;
	}

	public void setIcsNameList(Map<String, String> icsNameList) {

		this.icsNameList = icsNameList;
	}

	public String getIcsName() {

		return icsName;
	}

	public void setIcsName(String icsName) {

		this.icsName = icsName;
	}

	public String getSoil() {

		return soil;
	}

	public void setSoil(String soil) {

		this.soil = soil;
	}

	public Map<String, String> getSoilList() {

		soilList.put(String.valueOf(ICSName.YES.ordinal()), ICSName.YES.toString());
		soilList.put(String.valueOf(ICSName.NO.ordinal()), ICSName.NO.toString());

		return soilList;
	}

	public String getIdProof() {

		return idProof;
	}

	public void setIdProof(String idProof) {

		this.idProof = idProof;
	}

	public Map<String, String> getIdProofList() {

		idProofList.put(String.valueOf(IdProof.YES.ordinal()), IdProof.YES.toString());
		idProofList.put(String.valueOf(IdProof.NO.ordinal()), IdProof.NO.toString());

		return idProofList;
	}

	public String getEnableBankInfo() {

		return enableBankInfo;
	}

	public void setEnableBankInfo(String enableBankInfo) {

		this.enableBankInfo = enableBankInfo;
	}

	public String getIsSingleTenant() {

		return isSingleTenant;
	}

	public void setIsSingleTenant(String isSingleTenant) {

		this.isSingleTenant = isSingleTenant;
	}

	public String getSingleTenantName() {

		return singleTenantName;
	}

	public void setSingleTenantName(String singleTenantName) {

		this.singleTenantName = singleTenantName;
	}

	public Map<String, String> getTenantList() {
		List<String> tenantIds = new ArrayList(datasources.keySet());
		Map<String, String> tenants = new LinkedHashMap<>();
		tenants = tenantIds.stream().collect(Collectors.toMap(String::toString, String::toString));
		return tenants;
	}

	public String getInsuranceInformation() {
		return insuranceInformation;
	}

	public void setInsuranceInformation(String insuranceInformation) {
		this.insuranceInformation = insuranceInformation;
	}

	public Map<String, String> getInsuranceInfoList() {

		insuranceInfoList.put(String.valueOf(InsuranceInformation.YES.ordinal()), InsuranceInformation.YES.toString());
		insuranceInfoList.put(String.valueOf(InsuranceInformation.NO.ordinal()), InsuranceInformation.NO.toString());

		return insuranceInfoList;
	}

	public String getCropInformation() {
		return cropInformation;
	}

	public void setCropInformation(String cropInformation) {
		this.cropInformation = cropInformation;
	}

	public Map<String, String> getCropInfoList() {

		cropInfoList.put(String.valueOf(CropInformation.YES.ordinal()), CropInformation.YES.toString());
		cropInfoList.put(String.valueOf(CropInformation.NO.ordinal()), CropInformation.NO.toString());

		return cropInfoList;
	}

	public String getHarvestSeasonInformation() {

		return harvestSeasonInformation;
	}

	public void setHarvestSeasonInformation(String harvestSeasonInformation) {

		this.harvestSeasonInformation = harvestSeasonInformation;
	}

	public Map<String, String> getHarvestSeasonInfoList() {

		harvestSeasonInfoList.put(String.valueOf(HarvestSeasonInformation.YES.ordinal()),
				HarvestSeasonInformation.YES.toString());
		harvestSeasonInfoList.put(String.valueOf(HarvestSeasonInformation.NO.ordinal()),
				HarvestSeasonInformation.NO.toString());

		return harvestSeasonInfoList;
	}

	public Map<String, String> getIsCertifiedFarmerInfoList() {

		isCertifiedFarmerInfoList.put(String.valueOf(IsCertifiedFarmer.YES.ordinal()),
				IsCertifiedFarmer.YES.toString());
		isCertifiedFarmerInfoList.put(String.valueOf(IsCertifiedFarmer.NO.ordinal()), IsCertifiedFarmer.NO.toString());

		return isCertifiedFarmerInfoList;
	}

	public Map<String, String> getApproveOptionList() {

		approveOptionList.put(String.valueOf(Distribution.APPROVED), HarvestSeasonInformation.YES.toString());
		approveOptionList.put(String.valueOf(Distribution.NOT_APPROVED), HarvestSeasonInformation.NO.toString());

		return approveOptionList;
	}

	public void setHarvestSeasonInfoList(Map<String, String> harvestSeasonInfoList) {

		this.harvestSeasonInfoList = harvestSeasonInfoList;
	}

	public String getFarmersByGroupPie() {

		return farmersByGroupPie;
	}

	public void setFarmersByGroupPie(String farmersByGroupPie) {

		this.farmersByGroupPie = farmersByGroupPie;
	}

	public String getWarehouseStockShart() {

		return warehouseStockShart;
	}

	public void setWarehouseStockShart(String warehouseStockShart) {

		this.warehouseStockShart = warehouseStockShart;
	}

	public String getFarmersByGroupBar() {

		return farmersByGroupBar;
	}

	public void setFarmersByGroupBar(String farmersByGroupBar) {

		this.farmersByGroupBar = farmersByGroupBar;
	}

	public String getDeviceChart() {

		return deviceChart;
	}

	public void setDeviceChart(String deviceChart) {

		this.deviceChart = deviceChart;
	}

	public String getFarmersByOrg() {

		return farmersByOrg;
	}

	public void setFarmersByOrg(String farmersByOrg) {

		this.farmersByOrg = farmersByOrg;
	}

	public String getTotalLandAcreChart() {

		return totalLandAcreChart;
	}

	public void setTotalLandAcreChart(String totalLandAcreChart) {

		this.totalLandAcreChart = totalLandAcreChart;
	}

	public String getTotalAreaAroductionChart() {

		return totalAreaAroductionChart;
	}

	public void setTotalAreaAroductionChart(String totalAreaAroductionChart) {

		this.totalAreaAroductionChart = totalAreaAroductionChart;
	}

	public String getSeedTypeChart() {

		return seedTypeChart;
	}

	public void setSeedTypeChart(String seedTypeChart) {

		this.seedTypeChart = seedTypeChart;
	}

	public String getSeedSourceChart() {

		return seedSourceChart;
	}

	public void setSeedSourceChart(String seedSourceChart) {

		this.seedSourceChart = seedSourceChart;
	}

	public String getFarmerDetailsChart() {

		return farmerDetailsChart;
	}

	public void setFarmerDetailsChart(String farmerDetailsChart) {

		this.farmerDetailsChart = farmerDetailsChart;
	}

	public String getFarmerCropDetailChart() {

		return farmerCropDetailChart;
	}

	public void setFarmerCropDetailChart(String farmerCropDetailChart) {

		this.farmerCropDetailChart = farmerCropDetailChart;
	}

	public void setApproveOptionList(Map<String, String> approveOptionList) {
		this.approveOptionList = approveOptionList;
	}

	public String getApproveOption() {
		return approveOption;
	}

	public void setApproveOption(String approveOption) {
		this.approveOption = approveOption;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Map<String, String> getBatchNoList() {
		batchNoList.put(String.valueOf(EnableBranchTypes.YES.ordinal()), EnableBranchTypes.YES.toString());
		batchNoList.put(String.valueOf(EnableBranchTypes.NO.ordinal()), HarvestSeasonInformation.NO.toString());
		return batchNoList;
	}

	public void setBatchNoList(Map<String, String> batchNoList) {
		this.batchNoList = batchNoList;
	}

	public String getCowMilkByMonthChart() {
		return cowMilkByMonthChart;
	}

	public void setCowMilkByMonthChart(String cowMilkByMonthChart) {
		this.cowMilkByMonthChart = cowMilkByMonthChart;
	}

	public String getCowByResearchStationChart() {
		return cowByResearchStationChart;
	}

	public void setCowByResearchStationChart(String cowByResearchStationChart) {
		this.cowByResearchStationChart = cowByResearchStationChart;
	}

	public String getCowByVillageChart() {
		return cowByVillageChart;
	}

	public void setCowByVillageChart(String cowByVillageChart) {
		this.cowByVillageChart = cowByVillageChart;
	}

	public String getCowMilkNonMilkChart() {
		return cowMilkNonMilkChart;
	}

	public void setCowMilkNonMilkChart(String cowMilkNonMilkChart) {
		this.cowMilkNonMilkChart = cowMilkNonMilkChart;
	}

	public String getCowDiseaseChart() {
		return cowDiseaseChart;
	}

	public void setCowDiseaseChart(String cowDiseaseChart) {
		this.cowDiseaseChart = cowDiseaseChart;
	}

	public String getTxnChart() {
		return txnChart;
	}

	public void setTxnChart(String txnChart) {
		this.txnChart = txnChart;
	}

	public String getCowCostBarChart() {
		return cowCostBarChart;
	}

	public void setCowCostBarChart(String cowCostBarChart) {
		this.cowCostBarChart = cowCostBarChart;
	}

	public String getTotalLandAcreVillageChart() {
		return totalLandAcreVillageChart;
	}

	public void setTotalLandAcreVillageChart(String totalLandAcreVillageChart) {
		this.totalLandAcreVillageChart = totalLandAcreVillageChart;
	}

	public String getIsCertifiedFarmer() {
		return isCertifiedFarmer;
	}

	public void setIsCertifiedFarmer(String isCertifiedFarmer) {
		this.isCertifiedFarmer = isCertifiedFarmer;
	}

	public String getFarmerCocChart() {

		return farmerCocChart;
	}

	public void setFarmerCocChart(String farmerCocChart) {

		this.farmerCocChart = farmerCocChart;
	}

	public String getFarmerDataStatChart() {

		return farmerDataStatChart;
	}

	public void setFarmerDataStatChart(String farmerDataStatChart) {

		this.farmerDataStatChart = farmerDataStatChart;
	}

	public Map<String, String> getFarmerCocChartList() {

		farmerCocChartList.put(String.valueOf(EnableBranchTypes.YES.ordinal()), EnableBranchTypes.YES.toString());
		farmerCocChartList.put(String.valueOf(EnableBranchTypes.NO.ordinal()), HarvestSeasonInformation.NO.toString());
		return farmerCocChartList;
	}

	public void setFarmerCocChartList(Map<String, String> farmerCocChartList) {

		this.farmerCocChartList = farmerCocChartList;
	}

	public Map<String, String> getFarmerDataStatChartList() {

		farmerDataStatChartList.put(String.valueOf(EnableBranchTypes.YES.ordinal()), EnableBranchTypes.YES.toString());
		farmerDataStatChartList.put(String.valueOf(EnableBranchTypes.NO.ordinal()),
				HarvestSeasonInformation.NO.toString());
		return farmerDataStatChartList;
	}

	public void setFarmerDataStatChartList(Map<String, String> farmerDataStatChartList) {

		this.farmerDataStatChartList = farmerDataStatChartList;
	}

	public String getFarmerIcsChart() {

		return farmerIcsChart;
	}

	public void setFarmerIcsChart(String farmerIcsChart) {

		this.farmerIcsChart = farmerIcsChart;
	}

	public String getCropHarvestSaleChart() {

		return cropHarvestSaleChart;
	}

	public void setCropHarvestSaleChart(String cropHarvestSaleChart) {

		this.cropHarvestSaleChart = cropHarvestSaleChart;
	}

	public Map<String, String> getFarmerIcsChartList() {

		farmerIcsChartList.put(String.valueOf(EnableBranchTypes.YES.ordinal()), EnableBranchTypes.YES.toString());
		farmerIcsChartList.put(String.valueOf(EnableBranchTypes.NO.ordinal()), HarvestSeasonInformation.NO.toString());
		return farmerIcsChartList;
	}

	public void setFarmerIcsChartList(Map<String, String> farmerIcsChartList) {

		this.farmerIcsChartList = farmerIcsChartList;
	}

	public Map<String, String> getCropHarvestSaleChartList() {

		cropHarvestSaleChartList.put(String.valueOf(EnableBranchTypes.YES.ordinal()), EnableBranchTypes.YES.toString());
		cropHarvestSaleChartList.put(String.valueOf(EnableBranchTypes.NO.ordinal()),
				HarvestSeasonInformation.NO.toString());
		return cropHarvestSaleChartList;
	}

	public void setCropHarvestSaleChartList(Map<String, String> cropHarvestSaleChartList) {

		this.cropHarvestSaleChartList = cropHarvestSaleChartList;
	}

	public Map<String, String> getFarmerCocExpensesChartList() {

		farmerCocExpensesChartList.put(String.valueOf(EnableBranchTypes.YES.ordinal()),
				EnableBranchTypes.YES.toString());
		farmerCocExpensesChartList.put(String.valueOf(EnableBranchTypes.NO.ordinal()),
				HarvestSeasonInformation.NO.toString());
		return farmerCocExpensesChartList;
	}

	public void setFarmerCocExpensesChartList(Map<String, String> farmerCocExpensesChartList) {

		this.farmerCocExpensesChartList = farmerCocExpensesChartList;
	}

	public String getFarmerCocExpensesChart() {

		return farmerCocExpensesChart;
	}

	public void setFarmerCocExpensesChart(String farmerCocExpensesChart) {

		this.farmerCocExpensesChart = farmerCocExpensesChart;
	}

	public Map<String, String> getFarmerCocSegregatedChartList() {

		farmerCocSegregatedChartList.put(String.valueOf(EnableBranchTypes.YES.ordinal()),
				EnableBranchTypes.YES.toString());
		farmerCocSegregatedChartList.put(String.valueOf(EnableBranchTypes.NO.ordinal()),
				HarvestSeasonInformation.NO.toString());
		return farmerCocSegregatedChartList;
	}

	public void setFarmerCocSegregatedChartList(Map<String, String> farmerCocSegregatedChartList) {

		this.farmerCocSegregatedChartList = farmerCocSegregatedChartList;
	}

	public String getFarmerCocSegregatedChart() {

		return farmerCocSegregatedChart;
	}

	public void setFarmerCocSegregatedChart(String farmerCocSegregatedChart) {

		this.farmerCocSegregatedChart = farmerCocSegregatedChart;
	}

	public String getTraceableBatchNo() {

		return traceableBatchNo;
	}

	public void setTraceableBatchNo(String traceableBatchNo) {

		this.traceableBatchNo = traceableBatchNo;
	}

	public Map<String, String> getTraceableBatchNoList() {
		traceableBatchNoList.put(String.valueOf(EnableBranchTypes.YES.ordinal()), EnableBranchTypes.YES.toString());
		traceableBatchNoList.put(String.valueOf(EnableBranchTypes.NO.ordinal()),
				HarvestSeasonInformation.NO.toString());

		return traceableBatchNoList;
	}

	public void setTraceableBatchNoList(Map<String, String> traceableBatchNoList) {

		this.traceableBatchNoList = traceableBatchNoList;
	}

	public Map<String, String> getFarmerSowingHarvestList() {
		farmerSowingHavstList.put(String.valueOf(EnableBranchTypes.YES.ordinal()), EnableBranchTypes.YES.toString());
		farmerSowingHavstList.put(String.valueOf(EnableBranchTypes.NO.ordinal()),
				HarvestSeasonInformation.NO.toString());

		return farmerSowingHavstList;
	}

	public Map<String, String> getGinnerQuantityList() {
		ginnerQuantityList.put(String.valueOf(EnableBranchTypes.YES.ordinal()), EnableBranchTypes.YES.toString());
		ginnerQuantityList.put(String.valueOf(EnableBranchTypes.NO.ordinal()), HarvestSeasonInformation.NO.toString());

		return ginnerQuantityList;
	}

	public Map<String, String> getGmoList() {
		gmoList.put(String.valueOf(EnableBranchTypes.YES.ordinal()), EnableBranchTypes.YES.toString());
		gmoList.put(String.valueOf(EnableBranchTypes.NO.ordinal()), HarvestSeasonInformation.NO.toString());

		return gmoList;
	}

	public Map<String, String> getAreaUnderProdByOrgList() {
		areaUnderProdByOrgList.put(String.valueOf(EnableBranchTypes.YES.ordinal()), EnableBranchTypes.YES.toString());
		areaUnderProdByOrgList.put(String.valueOf(EnableBranchTypes.NO.ordinal()),
				HarvestSeasonInformation.NO.toString());

		return areaUnderProdByOrgList;
	}
	
	
	
	protected JSONObject getJSONObject(Object id, Object name) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("name", name);
		return jsonObject;
	}
	
	public String getFarmerSowingHarvest() {
		return farmerSowingHarvest;
	}

	public void setFarmerSowingHarvest(String farmerSowingHarvest) {
		this.farmerSowingHarvest = farmerSowingHarvest;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getAddressLine4() {
		return addressLine4;
	}

	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	public String getAddressLine5() {
		return addressLine5;
	}

	public void setAddressLine5(String addressLine5) {
		this.addressLine5 = addressLine5;
	}

	public String getAddressLine6() {
		return addressLine6;
	}

	public void setAddressLine6(String addressLine6) {
		this.addressLine6 = addressLine6;
	}

	public String getAddressLine7() {
		return addressLine7;
	}

	public void setAddressLine7(String addressLine7) {
		this.addressLine7 = addressLine7;
	}

	public String getAddressLine8() {
		return addressLine8;
	}

	public void setAddressLine8(String addressLine8) {
		this.addressLine8 = addressLine8;
	}

	public String getAddressLine9() {
		return addressLine9;
	}

	public void setAddressLine9(String addressLine9) {
		this.addressLine9 = addressLine9;
	}

	public String getAddressLine10() {
		return addressLine10;
	}

	public void setAddressLine10(String addressLine10) {
		this.addressLine10 = addressLine10;
	}

	public String getAddressLine11() {
		return addressLine11;
	}

	public void setAddressLine11(String addressLine11) {
		this.addressLine11 = addressLine11;
	}

	public String getAddressLine12() {
		return addressLine12;
	}

	public void setAddressLine12(String addressLine12) {
		this.addressLine12 = addressLine12;
	}

	public String getAddressLine13() {
		return addressLine13;
	}

	public void setAddressLine13(String addressLine13) {
		this.addressLine13 = addressLine13;
	}

	public String getAreaUnderProdByOrg() {
		return areaUnderProdByOrg;
	}

	public void setAreaUnderProdByOrg(String areaUnderProdByOrg) {
		this.areaUnderProdByOrg = areaUnderProdByOrg;
	}

	public String getGinnerQuantitySold() {
		return ginnerQuantitySold;
	}

	public void setGinnerQuantitySold(String ginnerQuantitySold) {
		this.ginnerQuantitySold = ginnerQuantitySold;
	}

	public String getGmoPercentage() {
		return gmoPercentage;
	}

	public void setGmoPercentage(String gmoPercentage) {
		this.gmoPercentage = gmoPercentage;
	}

	public String getAddressLine14() {
		return addressLine14;
	}

	public void setAddressLine14(String addressLine14) {
		this.addressLine14 = addressLine14;
	}

	public String getAddressLine15() {
		return addressLine15;
	}

	public void setAddressLine15(String addressLine15) {
		this.addressLine15 = addressLine15;
	}

	public String getAddressLine16() {
		return addressLine16;
	}

	public void setAddressLine16(String addressLine16) {
		this.addressLine16 = addressLine16;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(String subMenuId) {
		this.subMenuId = subMenuId;
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuDescription() {
		return menuDescription;
	}

	public void setMenuDescription(String menuDescription) {
		this.menuDescription = menuDescription;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(String menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getEse_ent_name() {
		return ese_ent_name;
	}

	public void setEse_ent_name(String ese_ent_name) {
		this.ese_ent_name = ese_ent_name;
	}

	public String getEse_action_actionId() {
		return ese_action_actionId;
	}

	public void setEse_action_actionId(String ese_action_actionId) {
		this.ese_action_actionId = ese_action_actionId;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	
	public Map<String, String> getPrefList() {
		prefList.put(String.valueOf(EnableBranchTypes.YES.ordinal()), EnableBranchTypes.YES.toString());
		prefList.put(String.valueOf(EnableBranchTypes.NO.ordinal()), HarvestSeasonInformation.NO.toString());
		return prefList;
	}


	public String getAddressLine17() {
		return addressLine17;
	}

	public void setAddressLine17(String addressLine17) {
		this.addressLine17 = addressLine17;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getSubMenusOrder() {
		return subMenusOrder;
	}

	public void setSubMenusOrder(String subMenusOrder) {
		this.subMenusOrder = subMenusOrder;
	}

	public Map<String, String> getIsKpfBasedList() {
	
		isKpfBasedList.put(String.valueOf(IsKpfBased.YES.ordinal()),
				IsKpfBased.YES.toString());
		isKpfBasedList.put(String.valueOf(IsKpfBased.NO.ordinal()), IsKpfBased.NO.toString());

		return isKpfBasedList;
		
		
	}
	
	public void populateViews() throws Exception{
		JSONArray viewArr = new JSONArray();
		DataSource ds=datasources.get(getCurrentTenantId());
		List<Object[]> viewsList= utilService.listOfViewByDBName(ds.getConnection().getCatalog());
		viewsList.stream().forEach(f->{
			viewArr.add(getJSONObject(f[0].toString(), f[0].toString()));
		});
		sendAjaxResponse(viewArr);
	}
	public void populateViewFields() throws Exception{
		JSONArray fieldsArr = new JSONArray();
		if(!StringUtil.isEmpty(viewName)){
			DataSource ds=datasources.get(getCurrentTenantId());
			List<Object> fieldList=utilService.listFieldsByViewNameAndDBName(ds.getConnection().getCatalog(),viewName);
			fieldList.stream().forEach(a->{
				fieldsArr.add(getJSONObject(a.toString(), a.toString()));
			});
		}
		sendAjaxResponse(fieldsArr);
	}
	/**
	 * 
	 */
	/*public void populateDynamicReportConfig(){
		DynamicReportConfig dynamicReportConfig=new DynamicReportConfig();
		Set<DynamicReportConfigDetail> dynmaicReportConfigDetails = new HashSet<DynamicReportConfigDetail>();
		Set<DynamicReportConfigFilter> dynmaicReportConfigFilters = new HashSet<DynamicReportConfigFilter>();
		
		dynamicReportConfig.setEntityName(viewName);
		dynamicReportConfig.setFetchType(3l);
		dynamicReportConfig.setGridType(1l);
		dynamicReportConfig.setReport(reportName);
		dynamicReportConfig.setStatus(1l);
		String xls= reportName.replace(" ", "");
		dynamicReportConfig.setXlsName(xls);
		
		if(valStr!=null && !StringUtil.isEmpty(valStr)){
			DynamicReportConfigDetail dr=new DynamicReportConfigDetail();
			DynamicReportConfigFilter df=new DynamicReportConfigFilter();
			String mainStr[]=valStr.split("#");
			for(String str:mainStr){
				String[] inStr=str.split("~");
				dr.setLabelName(inStr[1]);
				dr.setField(inStr[0]);
				dr.setAccessType(1l);
				dr.setGroupProp(0l);
				dr.setSumProp(0l);
				dr.setWidth(100l);
				dr.setOrder(Long.parseLong(inStr[2]));
				dr.setIsGridAvailabiltiy("1".equals(inStr[3])?true:false);
				dr.setIsExportAvailabiltiy("1".equals(inStr[4])?true:false);
				dr.setStatus(Long.parseLong(inStr[5]));
				dr.setAlignment(inStr[6]!=null && !StringUtil.isEmpty(inStr[6])?inStr[6]:null);
				dr.setIsFooterSum(inStr[7]);
				dr.setDynamicReportConfig(dynamicReportConfig);
				dynmaicReportConfigDetails.add(dr);
				if(inStr[8].equals("1")){
					df.setField(inStr[0]);
					df.setLabel(inStr[1]);
					df.setMethod(inStr[9].equals("3")?inStr[10]:null);
					df.setStatus(Long.parseLong(inStr[12]));
					df.setOrder(Long.parseLong(inStr[11]));
					df.setType(Long.parseLong(inStr[9]));
					df.setType(inStr[13]!=null && !StringUtil.isEmpty(inStr[13])?Long.parseLong(inStr[13]):null);
					df.setDynamicReportConfig(dynamicReportConfig);
					dynmaicReportConfigFilters.add(df);
				}
			}
			utilService.addDynamicReportConfig(dynamicReportConfig);
		}
	}*/
	public void setIsKpfBasedList(Map<String, String> isKpfBasedList) {
		this.isKpfBasedList = isKpfBasedList;
	}

	public String getIsKpfBased() {
		return isKpfBased;
	}

	public void setIsKpfBased(String isKpfBased) {
		this.isKpfBased = isKpfBased;
	}
	public Map<String, String> getIsDistImageList() {
		isDistImageList.put(String.valueOf(DistributionImage.YES.ordinal()),
				DistributionImage.YES.toString());
		isDistImageList.put(String.valueOf(DistributionImage.NO.ordinal()), DistributionImage.NO.toString());

		return isDistImageList;
	}

	public void setIsDistImageList(Map<String, String> isDistImageList) {
		this.isDistImageList = isDistImageList;
	}

	public String getDistImage() {
		return distImage;
	}

	public void setDistImage(String distImage) {
		this.distImage = distImage;
	}

    public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getValStr() {
		return valStr;
	}

	public void setValStr(String valStr) {
		this.valStr = valStr;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Map<String, String> getIsProdReturnImageList() {

		isProdReturnImageList.put(String.valueOf(ProductReturnImage.YES.ordinal()),ProductReturnImage.YES.toString());
		isProdReturnImageList.put(String.valueOf(ProductReturnImage.NO.ordinal()), ProductReturnImage.NO.toString());

		return isProdReturnImageList;
	
	}

	public void setIsProdReturnImageList(Map<String, String> isProdReturnImageList) {
		this.isProdReturnImageList = isProdReturnImageList;
	}

	public String getProdReturnImage() {
		return prodReturnImage;
	}

	public void setProdReturnImage(String prodReturnImage) {
		this.prodReturnImage = prodReturnImage;
	}


	public Map<String, String> getIsDigitalSignatureList() {
		
		isDigitalSignatureList.put(String.valueOf(DigitalSignature.YES.ordinal()),DigitalSignature.YES.toString());
		isDigitalSignatureList.put(String.valueOf(DigitalSignature.NO.ordinal()), DigitalSignature.NO.toString());
		
		return isDigitalSignatureList;
	}

	public void setIsDigitalSignatureList(Map<String, String> isDigitalSignatureList) {
		this.isDigitalSignatureList = isDigitalSignatureList;
	}

	public String getDigitalSignature() {
		return digitalSignature;
	}

	public void setDigitalSignature(String digitalSignature) {
		this.digitalSignature = digitalSignature;
	}
	
	


	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getNoOfCrates() {
		return noOfCrates;
	}

	public void setNoOfCrates(String noOfCrates) {
		this.noOfCrates = noOfCrates;
	}
	
	public String getHtmlContent() {
		return htmlContent;
	}

	public String getCropCalendar() {
		return cropCalendar;
	}

	public void setCropCalendar(String cropCalendar) {
		this.cropCalendar = cropCalendar;
	}


	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
			

	public String getCurrencyTypez() {
		return currencyTypez;
	}

	public void setCurrencyTypez(String currencyTypez) {
		this.currencyTypez = currencyTypez;
	}

	public void saveContracteTemplate(){
		if(!StringUtil.isEmpty(getHtmlContent())){
			ESESystem system = utilService.findPrefernceById("1");
			if (!ObjectUtil.isEmpty(system) && !ObjectUtil.isEmpty(system.getPreferences())) {
				
				HashMap<String, String> preferences = new HashMap();
				preferences.put("permit_template", getHtmlContent());
				utilService.editPreference(preferences);
				
				getJsonObject().put("msg", "Template Updated");
				getJsonObject().put("title", "Success");
				sendAjaxResponse(getJsonObject());
				
			}
		}else{
			getJsonObject().put("msg", "Failed Please try again");
			getJsonObject().put("title", "Failed");
			sendAjaxResponse(getJsonObject());
		}
	}
	
	
	
public String getLoanDetailJsonString() {
	return loanDetailJsonString;
}

public void setLoanDetailJsonString(String loanDetailJsonString) {
	this.loanDetailJsonString = loanDetailJsonString;
}

public int getMinRange() {
	return minRange;
}

public void setMinRange(int minRange) {
	this.minRange = minRange;
}

public int getMaxRange() {
	return maxRange;
}

public void setMaxRange(int maxRange) {
	this.maxRange = maxRange;
}

public String getTemplateId() {
	return templateId;
}

public void setTemplateId(String templateId) {
	this.templateId = templateId;
}

public String getEnableLoanModule() {
	return enableLoanModule;
}

public void setEnableLoanModule(String enableLoanModule) {
	this.enableLoanModule = enableLoanModule;
}

	
}
