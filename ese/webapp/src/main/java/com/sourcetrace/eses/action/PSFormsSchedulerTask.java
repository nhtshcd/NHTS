package com.sourcetrace.eses.action;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.service.IUtilService;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;

public class PSFormsSchedulerTask {
	@Autowired
	private IUtilService utilService;

	@Autowired
	private DynamicViewReportDTAction exporter;
	private Map<String, Map<String, String>> localeMapMailExport = new LinkedHashMap<String, Map<String, String>>();

	public void process() throws Exception, IOException {
		ESESystem es = utilService.findPrefernceById("1");
		String psforms = es.getPreferences().get("psforms");
		Arrays.asList(psforms.split(",")).stream().forEach(uu -> {
			InputStream is;
			String fileName =uu.split(":")[1].toString();
			String reprotId =uu.split(":")[0].toString();
			Object[] data = (Object[]) getMethodValue("getExportDataStream",
					new Object[] { "1", reprotId, "en", "nhts", "" }, exporter);
			if (data != null) {
				is = (InputStream) data[1];
				if (!ObjectUtil.isEmpty(is) && data[0] != null) {
					if (!data[0].toString().equalsIgnoreCase("0")) {
						ftpFile(is, es.getPreferences(),fileName);
					}

				}
			}
		});

	}

	public String getLocalePropertyForMailExport(String prop, String lang) {
		String locProp = "";
		if (!ObjectUtil.isEmpty(utilService)) {
			if (!StringUtil.isEmpty(lang)) {
				if (localeMapMailExport.size() <= 0) {
					localeMapMailExport = utilService.listLocaleProp().stream()
							.collect(Collectors.groupingBy(f -> f.getLangCode(),
									Collectors.toMap(f -> f.getCode(), f -> f.getLangValue(), (f1, f2) -> f1)));
				}
			}
			if (localeMapMailExport.containsKey(lang)) {
				locProp = localeMapMailExport.get(lang).get(prop);
			}
		}
		try {

			Properties properties = new Properties();
			Class clazz = SwitchAction.class;
			properties.load(clazz.getResourceAsStream("SwitchAction_" + lang + ".properties"));
			// String dd =StringUtil.isEmpty(locProp) ? "" : locProp;
			String dd = StringUtil.isEmpty(locProp) ? properties.getProperty(prop) : locProp;
			return dd;
			/* } */
		} catch (Exception e) {
			return locProp;
		}

	}

	@SuppressWarnings({ "unused", "unchecked" })
	private Object getMethodValue(String methodName, Object[] param, Object exporter) {
		Object field = null;
		try {

			if (param != null) {
				Class<?> params[] = new Class[param.length];
				for (int i = 0; i < param.length; i++) {
					if (param[i] instanceof Integer) {
						params[i] = Integer.TYPE;
					} else if (param[i] instanceof String) {
						params[i] = String.class;
					}
					// you can do additional checks for other data types if you
					// want.
				}
				if (param instanceof Object[]) {
					@SuppressWarnings("unchecked")
					Method setNameMethod = exporter.getClass().getDeclaredMethod(methodName, params);
					field = setNameMethod.invoke(exporter, param);
				} else {
					Method setNameMethod = exporter.getClass().getDeclaredMethod(methodName, String.class);
					field = setNameMethod.invoke(exporter, param);
				}
			} else {
				Method setNameMethod = exporter.getClass().getMethod(methodName);
				field = setNameMethod.invoke(exporter);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return field;
	}

	public void ftpFile(InputStream inputStream, Map<String, String> pref, String firstRemoteFile) {
		FTPClient ftpClient = new FTPClient();
		String server = pref.get("FTP_SERVER");
		String user = pref.get("FTP_USER");
		String pass = pref.get("FTP_PW");
		String ftpPath = pref.get("FTP_PATH");
		try {

			ftpClient.connect(server);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory("/home/deployer/Olivado/94102/");
			String currdir = ftpClient.printWorkingDirectory();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			System.out.println("Start uploading first file");
			boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
			inputStream.close();
			if (done) {
				System.out.println("The first file is uploaded successfully.");
			}
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
