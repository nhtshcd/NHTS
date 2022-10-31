package com.sourcetrace.eses.adapter.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.entity.FarmerDynamicData;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.txn.adapter.ITxnAdapter;
import com.sourcetrace.eses.util.ObjectUtil;


@Component
public class DynamicTxnAdapter implements ITxnAdapter {
	public static enum ENTITES {
		NA, FARMER, FARM, GROUP, CERTIFICATION, TRAINING, FARM_CROPS
	}

	@Autowired
	private IFarmerService farmerService;

	@Autowired
	private IUtilService utilService;

	@Autowired
	public IUniqueIDGenerator idServoce;
	
	private List<FarmCatalogue> farmCatalogueList;
	private String id;
	private String branch;
	private String tenantId;
	private boolean isVideo;
	private FarmerDynamicData farmerDynamicDatas;
	private String VIDEO_FILE_PATH = "/home/deployer/ftp/Pictures/";
	private FarmerDynamicData farmerDynamicData = new FarmerDynamicData();
	String phone;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public File createFileObject(String fileName) {
		File tmpFileObject = null;
		String filePath = VIDEO_FILE_PATH;
		tmpFileObject = new File(filePath + fileName);
		System.out.println("The File Path To Create is : " + filePath + fileName);
		return tmpFileObject;
	}

	public FarmCatalogue getCatlogueValueByCode(String code) {
		if (ObjectUtil.isEmpty(getFarmCatalogueList()) && ObjectUtil.isListEmpty(getFarmCatalogueList())) {
			setFarmCatalogueList(new ArrayList<>());
			setFarmCatalogueList(utilService.listCatalogues());
		}
		FarmCatalogue catValue = getFarmCatalogueList().stream().filter(fc -> fc.getCode().equalsIgnoreCase(code))
				.findAny().orElseGet(() -> {
					FarmCatalogue tmp = new FarmCatalogue();
					tmp.setName("");
					tmp.setDispName("");
					return tmp;
				});

		return catValue;
	}

	public List<FarmCatalogue> getFarmCatalogueList() {
		return farmCatalogueList;
	}

	public void setFarmCatalogueList(List<FarmCatalogue> farmCatalogueList) {
		this.farmCatalogueList = farmCatalogueList;
	}

	@Override
	public Map<?, ?> processJson(Map<?, ?> reqData) {
		// TODO Auto-generated method stub
		return null;
	}

}
