package com.sourcetrace.eses.adapter.core;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.ExporterRegistration;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.ObjectUtil;

@Component
@Transactional
public class validateKRApinSchedulerTask {
	
	@Autowired
	private IFarmerService farmerService;
	@Autowired
    private IUtilService utilService;
	
	public void process() throws Exception, IOException {
		List<ExporterRegistration> expReg = (List<ExporterRegistration>) farmerService
				.listObjectById("from ExporterRegistration ex", new Object[] { });
		if (!ObjectUtil.isListEmpty(expReg)) {
			ESESystem es = utilService.findPrefernceById("1");
			for (ExporterRegistration exp : expReg) {
				utilService.processExporterLic(exp,es.getPreferences());
			}
		}
	}

}
