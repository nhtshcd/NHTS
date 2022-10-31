package com.sourcetrace.eses.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcetrace.eses.entity.ExporterRegistration;
import com.sourcetrace.eses.entity.Farm;
import com.sourcetrace.eses.entity.FarmCrops;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.FileUtil;
import com.sourcetrace.eses.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
public class ImisReportAction extends BaseReportAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TraceabilityViewReportAction.class);
	protected static final String CREATE = "create";
	protected static final String DETAIL = "detail";
	protected static final String UPDATE = "update";
	protected static final String MAPPING = "mapping";
	protected static final String DELETE = "delete";
	protected static final String LIST = "list";
	protected static final String TITLE_PREFIX = "title.";
	protected static final String HEADING = "heading";
	
	protected List<String> fields = new ArrayList<String>();
	int serialNo = 1;
	@Autowired
	private IFarmerService farmerService;  
	public String exporterId;
	
	@Getter
	@Setter
	private Farmer farmer;
	
	@Getter
	@Setter
	private Farm farm;
	
	@Getter
	@Setter
	private FarmCrops farmcrops;
	
	@Getter
	@Setter
	private ExporterRegistration exporter;
	
	private String xlsFileName;
	private InputStream fileInputStream;
	private static final SimpleDateFormat fileNameDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public String list() {
		Calendar currentDate = Calendar.getInstance();
		Calendar cal = (Calendar) currentDate.clone();
		cal.set(Calendar.MONTH, currentDate.get(Calendar.MONTH) - 1);
		DateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT);
		super.startDate = df.format(cal.getTime());
		super.endDate = df.format(currentDate.getTime());
		
		request.setAttribute(HEADING, getText(LIST));
		return LIST;

	}
	StringBuffer headerCols = new StringBuffer();	 
	String builder = new String();
	HSSFRow row, titleRow;
	HSSFCell cell;

	int rowNum = 3;
	int colNum = 0;
	private InputStream getExporterInputStream() throws IOException, ParseException {
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet sheet = wb.createSheet(getLocaleProperty("exporterImisReport1"));
	HSSFPatriarch drawing = sheet.createDrawingPatriarch();

	HSSFCellStyle style1 = wb.createCellStyle();
	HSSFCellStyle style2 = wb.createCellStyle();

	HSSFFont font1 = wb.createFont();
	font1.setFontHeightInPoints((short) 22);

	HSSFFont font2 = wb.createFont();
	font2.setFontHeightInPoints((short) 12);

	HSSFFont font3 = wb.createFont();
	font3.setFontHeightInPoints((short) 10);

	branchIdValue = getBranchId(); // set value for branch id.
	buildBranchMap();

	titleRow = sheet.createRow(rowNum++);
	cell = titleRow.createCell(3);
	cell.setCellValue(new HSSFRichTextString(getLocaleProperty("exportReportTitle")));
	cell.setCellStyle(style1);
	font1.setBoldweight((short) 22);
	font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	style1.setFont(font1);

	sheet.addMergedRegion(new CellRangeAddress(3, 3, 3, 5));
	int imgRow1 = 0;
	int imgRow2 = 2;
	int imgCol1 = 0;
	int imgCol2 = 1;

	sheet.addMergedRegion(new CellRangeAddress(imgRow1, imgRow2, imgCol1, imgCol2));

	++rowNum;

	rowNum += 2;
	HSSFRow rowHead1 = sheet.createRow(rowNum++);
	rowHead1.setHeight((short) 600);

	String columnHeaders1 = null;

 
		columnHeaders1 = getLocaleProperty("exportHeading");
	 
	int iterator1 = 1;

	cell = rowHead1.createCell(0);
	cell.setCellValue(new HSSFRichTextString("SNO"));
	style2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	cell.setCellStyle(style2);
	font2.setBoldweight((short) 12);
	font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	style2.setFont(font2);
	style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	style2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
	style2.setRightBorderColor(IndexedColors.BLACK.getIndex());
	style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
	style2.setTopBorderColor(IndexedColors.BLACK.getIndex());

	for (String cellHeader : columnHeaders1.split("\\,")) {

		if (StringUtil.isEmpty(branchIdValue)) {
			cell = rowHead1.createCell(iterator1);
			cell.setCellValue(new HSSFRichTextString(cellHeader));
			style2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cell.setCellStyle(style2);
			font2.setBoldweight((short) 12);
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style2.setFont(font2);
			if (iterator1 != 7) {
				sheet.setColumnWidth(iterator1, (15 * 450));
			}
			iterator1++;

		} else {
			if (!cellHeader.equalsIgnoreCase(getText("app.branch"))) {
				cell = rowHead1.createCell(iterator1);
				cell.setCellValue(new HSSFRichTextString(cellHeader));
				// style2.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
				// style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cell.setCellStyle(style2);
				font2.setBoldweight((short) 12);
				font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				style2.setFont(font2);
				sheet.setColumnWidth(iterator1, (15 * 550));
				iterator1++;
			}
		}
	}
 
   
	ExporterRegistration filter = new ExporterRegistration();
 
             filter=(ExporterRegistration)farmerService.findObjectById("from ExporterRegistration fc where fc.isActive =?", new Object[] {1l});
 
	Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
			getPage());
	List<Farmer> farmerList = (List) data.get(ROWS);
 
	// 0=Id,1=Farmer Id,2=Farmer Code,3=First Name,4=Last
	// name,5=surName,6=village name,7=Group name,8=Is certified
	// Farmer,9=Status,10=BranchId
	AtomicInteger snoCount = new AtomicInteger(0);
	farmerList.stream().forEach(farmer -> {
		
		farmer.getFarms().stream().forEach(farm-> {
		 
		row = sheet.createRow(rowNum++);
		row.setHeight((short) 400);
		colNum = 0;
		cell = row.createCell(colNum++);
		cell.setCellValue(snoCount.incrementAndGet());
		/*
		 * if (StringUtil.isEmpty(branchIdValue)) { cell =
		 * row.createCell(colNum++); cell.setCellValue(new
		 * HSSFRichTextString(!StringUtil.isEmpty(branchesMap.get(device.
		 * getBranch())) ? (branchesMap.get(device.getBranch())) : "")); }
		 */
       
		cell = row.createCell(colNum++);
		cell.setCellValue(
				new HSSFRichTextString(farm.getFarmName()) != null ? getText("NA") : farm.getFarmName());
    
	});
	});

	for (int i = 0; i <= colNum; i++) {
		sheet.autoSizeColumn(i);
	}

 

	String makeDir = FileUtil.storeXls(request.getSession().getId());
	String fileName = getLocaleProperty("farmerList") + fileNameDateFormat.format(new Date()) + ".xls";
	FileOutputStream fileOut = new FileOutputStream(makeDir + fileName);
	wb.write(fileOut);
	InputStream stream = new FileInputStream(new File(makeDir + fileName));
	fileOut.close();
	return stream;

}
	
	public String populateXLS() throws Exception {
		response.setContentType("application/vnd.ms-excel");
		// response.setHeader("Content-Disposition",
		// "attachment;filename=" + getText("farmerList") +
		// fileNameDateFormat.format(new Date()) + ".xls");
		InputStream is = null;

		is = getExporterInputStream();

		setXlsFileName(getText("farmerList") + fileNameDateFormat.format(new Date()));
		Map<String, InputStream> fileMap = new HashMap<String, InputStream>();
		fileMap.put(xlsFileName, is);
		setFileInputStream(FileUtil.createFileInputStreamToZipFile(getText("farmerList"), fileMap, ".xls"));

		return "xls";
	}
	
	public String getXlsFileName() {
		return xlsFileName;
	}

	public void setXlsFileName(String xlsFileName) {
		this.xlsFileName = xlsFileName;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public static SimpleDateFormat getFilenamedateformat() {
		return fileNameDateFormat;
	}
       
}
