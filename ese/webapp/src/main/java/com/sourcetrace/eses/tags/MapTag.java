package com.sourcetrace.eses.tags;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.sourcetrace.eses.util.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class MapTag.
 */
public class MapTag extends TagSupport {

	private static final Logger logger = Logger.getLogger(MapTag.class);
	private String startDate;
	private String endDate;

	/**
	 * Gets the start date.
	 * 
	 * @return the start date
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date.
	 * 
	 * @param startDate
	 *            the new start date
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the end date.
	 * 
	 * @return the end date
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * Gets the headers.
	 * 
	 * @return the headers
	 */
	@SuppressWarnings("unchecked")
	private Map<Object, Object> getHeaders() {
		return (Map) pageContext.getRequest().getAttribute("headers");
	}

	/**
	 * Gets the txnTypes.
	 * 
	 * @return the txnTypes
	 */
	@SuppressWarnings("unchecked")
	private Map<Object, Object> getTxnTypes() {
		return (Map) pageContext.getRequest().getAttribute("txnTypes");
	}

	/**
	 * Gets the key.
	 * 
	 * @return the key
	 */
	private String getKey() {
		return (String) pageContext.getRequest().getAttribute("geKey");
	}

	@SuppressWarnings("unchecked")
	private Map<Object, Object> getLocations() {
		return (Map) pageContext.getRequest().getAttribute("locations");
	}
	// @SuppressWarnings("unchecked")
	// private List<Location> getLocation() {
	// return (List<Location>)
	// pageContext.getRequest().getAttribute("locationList");
	// }
	// @SuppressWarnings("unchecked")
	// private List<Branch> getBranches() {
	// return (List<Branch>) pageContext.getRequest().getAttribute("branches");
	// }

	/**
	 * Sets the end date.
	 * 
	 * @param endDate
	 *            the new end date
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		try {
			pageContext.getOut().write("");
		} catch (Exception e) {
			logger.error("doStartTag()", e);
		}
		return SKIP_BODY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return super.doEndTag();
	}

	/**
	 * Creates the map.
	 * 
	 * @return the string
	 */
//	private String createMap() {
//
//		StringBuffer returnString = new StringBuffer();
//		StringBuffer placeString = new StringBuffer();
//		StringBuffer jsArrayString = new StringBuffer();
//		int i = 0;
//
//		// This is used to specify size of java script array, which is used for
//		// searching the
//		// information
//		int arraySize = 0;
//
//		// This is index of java script array.
//		int arrayIndex = 0;
//		List<POS> posDevices = (List) pageContext.getRequest().getAttribute("posDevices");
//		// Calculating the array size
//		if (posDevices != null)
//			arraySize += posDevices.size();
//		List<EnrollmentStation> enrollmentDevices = (List) pageContext.getRequest().getAttribute("enrollmentDevices");
//		// Calculating the array size
//		if (enrollmentDevices != null)
//			arraySize += enrollmentDevices.size();
//
//		List<Location> locationList = (List) pageContext.getRequest().getAttribute("locationList");
//		// Calculating the array size
//
//		if (locationList != null)
//			arraySize += locationList.size();
//
//		Map<Object, Object> txns = (Map) pageContext.getRequest().getAttribute("posTxns");
//		// Getting the kiosk detail from GlobalViewReportAction.java through
//		// request object
//		List<Kiosk> kiosks = (List) pageContext.getRequest().getAttribute("kiosks");
//		// Calculating the array size
//		if (kiosks != null)
//			arraySize += kiosks.size();
//		Map<Object, Object> brnTxns = (Map) pageContext.getRequest().getAttribute("brnTxns");
//		Map<Object, Object> kioskTxns = (Map) pageContext.getRequest().getAttribute("kioskTxns");
//		Map<Object, Object> enrollTxns = (Map) pageContext.getRequest().getAttribute("enrollTxns");
//
//		// The search is applicable for both id and name search, so we are
//		// increasing the size of an
//		// array
//		arraySize *= 2;
//		// The search is applicable for agent id and agent name, so we are
//		// calculating the size of
//		// an array
//		for (POS pos : posDevices) {
//
//			Set<Agent> agents = pos.getAgents();
//			List<ClientTransaction> dataList = (List) txns.get(pos.getDeviceId());
//			if (agents != null)
//				arraySize += (agents.size() * 2);
//			if (dataList != null)
//				arraySize += (dataList.size() * 2);
//		}
//		arraySize += 2;
//
//		// Creating the java script two dimension array
//		// Each row contains 4 columns
//		// First column contains id of the device/branch which is used to search
//		// Second column contains latitude
//		// Third column contains longitude
//		// Four column contains information about the device.
//		jsArrayString.append("\n var jsArray = new Array(" + arraySize + "); \n");
//		for (int j = 0; j < arraySize; j++) {
//			jsArrayString.append("\n jsArray[" + j + "] = new Array(4); \n");
//		}
//
//		boolean posExists = false;
//		for (POS pos : posDevices) {
//			String latitude = pos.getLocation().getLatitude();
//			String longitude = pos.getLocation().getLongitude();
//			String placemark = "placemark";
//			String point = "point";
//
//			// Initializing the java script array
//			// This is for id search
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][0]='" + pos.getDeviceId() + "'; \n");
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][1]=" + latitude + "; \n");
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][2]=" + longitude + "; \n");
//			jsArrayString
//					.append("\n jsArray[" + arrayIndex + "][3]='" + pos.getDeviceId() + ":" + pos.getName() + "'; \n");
//			arrayIndex++;
//			// This is for name search
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][0]='" + pos.getName() + "'; \n");
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][1]=" + latitude + "; \n");
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][2]=" + longitude + "; \n");
//			jsArrayString
//					.append("\n jsArray[" + arrayIndex + "][3]='" + pos.getName() + ":" + pos.getDeviceId() + "'; \n");
//			arrayIndex++;
//
//			List<ClientTransaction> dataList = (List) txns.get(pos.getDeviceId());
//			Set<Agent> agents = pos.getAgents();
//
//			for (Agent agent : agents) {
//
//				// Initializing the java script array
//				// This is for id search
//				jsArrayString.append("\n jsArray[" + arrayIndex + "][0]='" + agent.getProfileId() + "'; \n");
//				jsArrayString.append("\n jsArray[" + arrayIndex + "][1]=" + latitude + "; \n");
//				jsArrayString.append("\n jsArray[" + arrayIndex + "][2]=" + longitude + "; \n");
//				arrayIndex++;
//				// This is for name search
//				jsArrayString.append("\n jsArray[" + arrayIndex + "][1]=" + latitude + "; \n");
//				jsArrayString.append("\n jsArray[" + arrayIndex + "][2]=" + longitude + "; \n");
//				arrayIndex++;
//			}
//
//			if (!ObjectUtil.isListEmpty(dataList)) {
//
//				int recTotal = dataList.size();
//
//				for (int j = 0; j < recTotal; j++) {
//
//					ClientTransaction data = (ClientTransaction) dataList.get(j);
//
//					// Initializing the java script array
//					// This is for id search
//					jsArrayString.append("\n jsArray[" + arrayIndex + "][0]='" + data.getClientId() + "'; \n");
//					jsArrayString.append("\n jsArray[" + arrayIndex + "][1]=" + latitude + "; \n");
//					jsArrayString.append("\n jsArray[" + arrayIndex + "][2]=" + longitude + "; \n");
//					jsArrayString.append("\n jsArray[" + arrayIndex + "][3]='" + data.getClientId() + ":"
//							+ data.getClientAccountNumber() + "'; \n");
//					arrayIndex++;
//					// This is for name search
//					jsArrayString
//							.append("\n jsArray[" + arrayIndex + "][0]='" + data.getClientAccountNumber() + "'; \n");
//					jsArrayString.append("\n jsArray[" + arrayIndex + "][1]=" + latitude + "; \n");
//					jsArrayString.append("\n jsArray[" + arrayIndex + "][2]=" + longitude + "; \n");
//					jsArrayString.append("\n jsArray[" + arrayIndex + "][3]='" + data.getClientAccountNumber() + ":"
//							+ data.getClientId() + "'; \n");
//					arrayIndex++;
//
//				}
//			}
//			placemark += i;
//			point += i;
//
//			// This following statement is used to place the device/branch in
//			// the google map
//			placeString.append("\n var " + placemark + " = ge.createPlacemark(''); \n");
//			// Giving the caption for the place mark
//			placeString.append("\n " + placemark + ".setName('" + pos.getName() + "-" + pos.getDeviceId() + "'); \n");
//			placeString.append("\n ge.getFeatures().appendChild(" + placemark + "); \n");
//			// Selecting the image for the place mark
//			placeString.append("\n " + placemark + ".setStyleSelector(styleMap); \n");
//			placeString.append("\n var " + point + " = ge.createPoint(''); \n");
//			// Finding the exact location of the device by giving latitude and
//			// longitude.
//			placeString.append("\n " + point + ".setLatitude(" + latitude + "); \n");
//			placeString.append("\n " + point + ".setLongitude(" + longitude + "); \n");
//			// Giving the unique name for the place mark
//			placeString.append("\n " + placemark + ".setGeometry(" + point + "); \n");
//			// Giving the description for the device/branch.
//			placeString.append("\n " + placemark + ".setDescription('" + getDescString(pos, dataList) + "'); \n");
//
//			i++;
//			posExists = true;
//		}
//		// ////////////////
//
//		for (EnrollmentStation enrollmentStation : enrollmentDevices) {
//			String latitude = enrollmentStation.getLocation().getLatitude();
//			String longitude = enrollmentStation.getLocation().getLongitude();
//			String placemark = "placemark";
//			String point = "point";
//			placemark += i;
//			point += i;
//
//			// Initializing the java script array
//			// This is for id search
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][0]='" + enrollmentStation.getDeviceId() + "';\n");
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][1]=" + latitude + "; \n");
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][2]=" + longitude + "; \n");
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][3]='" + enrollmentStation.getDeviceId() + ":"
//					+ enrollmentStation.getName() + "'; \n");
//			arrayIndex++;
//
//			// This is for name search
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][0]='" + enrollmentStation.getName() + "';\n");
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][1]=" + latitude + "; \n");
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][2]=" + longitude + "; \n");
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][3]='" + enrollmentStation.getName() + ":"
//					+ enrollmentStation.getDeviceId() + "'; \n");
//			arrayIndex++;
//
//			placeString.append("\n var " + placemark + " = ge.createPlacemark(''); \n");
//			placeString.append("\n " + placemark + ".setName('" + enrollmentStation.getName() + "-"
//					+ enrollmentStation.getDeviceId() + "'); \n");
//			placeString.append("\n ge.getFeatures().appendChild(" + placemark + "); \n");
//			placeString.append("\n " + placemark + ".setStyleSelector(styleMapEs); \n");
//			placeString.append("\n var " + point + " = ge.createPoint(''); \n");
//			placeString.append("\n " + point + ".setLatitude(" + latitude + "); \n");
//			placeString.append("\n " + point + ".setLongitude(" + longitude + "); \n");
//			placeString.append("\n " + placemark + ".setGeometry(" + point + "); \n");
//
//			placeString.append("\n " + placemark + ".setDescription("
//					+ getEnrollInfoTab((Map<String, Integer>) enrollTxns.get(enrollmentStation.getDeviceId()))
//					+ "); \n");
//
//			i++;
//
//		}
//		for (Location location : locationList) {
//			String latitude = location.getLatitude();
//			String longitude = location.getLongitude();
//			String placemark = "placemark";
//			String point = "point";
//			placemark += i;
//			point += i;
//
//			// Initializing the java script array
//			// This is for id search
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][0]='" + location.getId() + "';\n");
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][1]=" + latitude + "; \n");
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][2]=" + longitude + "; \n");
//			jsArrayString.append(
//					"\n jsArray[" + arrayIndex + "][3]='" + location.getId() + ":" + location.getName() + "'; \n");
//			arrayIndex++;
//			// This is for name search
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][0]='" + location.getName() + "';\n");
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][1]=" + latitude + "; \n");
//			jsArrayString.append("\n jsArray[" + arrayIndex + "][2]=" + longitude + "; \n");
//			jsArrayString.append(
//					"\n jsArray[" + arrayIndex + "][3]='" + location.getName() + ":" + location.getId() + "'; \n");
//			arrayIndex++;
//
//			placeString.append("\n var " + placemark + " = ge.createPlacemark(''); \n");
//			placeString
//					.append("\n " + placemark + ".setName('" + location.getName() + "-" + location.getId() + "'); \n");
//			placeString.append("\n ge.getFeatures().appendChild(" + placemark + "); \n");
//			placeString.append("\n " + placemark + ".setStyleSelector(styleMapB); \n");
//			placeString.append("\n var " + point + " = ge.createPoint(''); \n");
//			placeString.append("\n " + point + ".setLatitude(" + latitude + "); \n");
//			placeString.append("\n " + point + ".setLongitude(" + longitude + "); \n");
//			placeString.append("\n " + placemark + ".setGeometry(" + point + "); \n");
//			placeString.append("\n " + placemark + ".setDescription('"
//					+ getBranchDescString((List<ClientTransaction>) brnTxns.get(location.getId())) + "'); \n");
//
//			i++;
//
//		}
//		// Adding kiosk detail to the google map
//		for (Kiosk kiosk : kiosks) {
//			// caption for placemark
//			String placemark = "placemark";
//			// Unique point to placemark
//			String point = "point";
//			String latitude = null;
//			String longitude = null;
//			// Getting latitude and longitude information of kiosk
//
//			latitude = kiosk.getLocation().getLatitude();
//			longitude = kiosk.getLocation().getLongitude();
//
//			if (latitude != null && longitude != null) {
//				// Initializing the java script array
//				// This is for id search
//				jsArrayString.append("\n jsArray[" + arrayIndex + "][0]='" + kiosk.getDeviceId() + "';\n");
//				jsArrayString.append("\n jsArray[" + arrayIndex + "][1]=" + latitude + "; \n");
//				jsArrayString.append("\n jsArray[" + arrayIndex + "][2]=" + longitude + "; \n");
//				jsArrayString.append(
//						"\n jsArray[" + arrayIndex + "][3]='" + kiosk.getDeviceId() + ":" + kiosk.getName() + "'; \n");
//				arrayIndex++;
//				// This is for name search
//				jsArrayString.append("\n jsArray[" + arrayIndex + "][0]='" + kiosk.getName() + "';\n");
//				jsArrayString.append("\n jsArray[" + arrayIndex + "][1]=" + latitude + "; \n");
//				jsArrayString.append("\n jsArray[" + arrayIndex + "][2]=" + longitude + "; \n");
//				jsArrayString.append(
//						"\n jsArray[" + arrayIndex + "][3]='" + kiosk.getName() + ":" + kiosk.getDeviceId() + "'; \n");
//				arrayIndex++;
//				placemark += i;
//				point += i;
//				// Adding the placemark to the google map
//				// Fixing the latitude and longitude to the placemark
//				// Adding the description to the placemark
//				// Adding the name to the placemark
//				placeString.append("\n var " + placemark + " = ge.createPlacemark(''); \n");
//				placeString.append(
//						"\n " + placemark + ".setName('" + kiosk.getName() + "-" + kiosk.getDeviceId() + "'); \n");
//				placeString.append("\n ge.getFeatures().appendChild(" + placemark + "); \n");
//				placeString.append("\n " + placemark + ".setStyleSelector(styleMapKiosk); \n");
//				placeString.append("\n var " + point + " = ge.createPoint(''); \n");
//				placeString.append("\n " + point + ".setLatitude(" + latitude + "); \n");
//				placeString.append("\n " + point + ".setLongitude(" + longitude + "); \n");
//				placeString.append("\n " + placemark + ".setGeometry(" + point + "); \n");
//				placeString.append("\n " + placemark + ".setDescription('"
//						+ getKioskDescString(kiosk, (List<ClientTransaction>) kioskTxns.get(kiosk.getDeviceId()))
//						+ "'); \n");
//			}
//			i++;
//		}
//
//		// ///////////////
//
//		returnString.append("\n <style> \n");
//		returnString.append("\n div.tabcontainer { \n");
//		returnString.append("\n width: 500px; \n");
//		returnString.append("\n background: #eee; \n");
//		returnString.append("\n border: 1px solid #000000; \n");
//		returnString.append("\n } \n");
//		returnString.append("\n ul.tabnav { \n");
//		returnString.append("\n list-style-type: none; \n");
//		returnString.append("\n margin: 0; \n");
//		returnString.append("\n padding: 0; \n");
//		returnString.append("\n width: 100%; \n");
//		returnString.append("\n overflow: hidden; \n");
//		returnString.append("\n } \n");
//
//		returnString.append("\n ul.tabnav a { \n");
//		returnString.append("\n display: block; \n");
//		returnString.append("\n width: 100%; \n");
//		returnString.append("\n } \n");
//
//		returnString.append("\n ul.tabnav a:hover { \n");
//		returnString.append("\n background: #ccc; \n");
//		returnString.append("\n } \n");
//
//		returnString.append("\n ul.tabnav li { \n");
//		returnString.append("\n float: left; \n");
//		returnString.append("\n width: 125px; \n");
//		returnString.append("\n margin: 0; \n");
//		returnString.append("\n padding: 0; \n");
//		returnString.append("\n text-align: center; \n");
//		returnString.append("\n } \n");
//
//		returnString.append("\n div.tabcontents { \n");
//		returnString.append("\n height: 290px; \n");
//		returnString.append("\n background: #ffffff; \n");
//		returnString.append("\n overflow: hidden; \n");
//		returnString.append("\n border-top: 1px solid #011; \n");
//		returnString.append("\n padding: 3px; \n");
//
//		returnString.append("\n } \n");
//
//		returnString.append("\n .view {display: table-cell;}\n");
//		returnString.append("\n .hide { display: none;}\n");
//
//		returnString.append("\n div.tabcontents div.content { \n");
//		returnString.append("\n float: left; \n");
//		returnString.append("\n width: 100%; \n");
//		returnString.append("\n height: 102%; \n");
//		returnString.append("\n overflow-y: auto; \n");
//		returnString.append("\n } \n");
//
//		returnString.append("\n div.tabcontents div.content h2 { \n");
//		returnString.append("\n margin-top: 3px; \n");
//		returnString.append("\n } \n");
//
//		returnString.append("\n </style> \n");
//		returnString.append("\n <input type='hidden' id='isNotFound'/> \n");
//		returnString.append("\n <script type=\"text/javascript\" src=\"http://www.google.com/jsapi?key=" + getKey()
//				+ "\"></script> \n");
//		returnString.append("\n <script type=\"text/javascript\"> \n");
//		returnString.append(jsArrayString);
//		returnString.append(getLinkString());
//		// The below function is used to hide and show message
//		returnString.append("\nfunction disable()  \n");
//		returnString.append("\n { \n");
//		// nodata is div which is used show/hide the "No data found" caption
//		returnString.append("\n document.getElementById(\"nodata\").className = \"hide\"; \n");
//		// mutipleData is div which is used show/hide the "Multiple data were
//		// found" caption
//		returnString.append("\n document.getElementById(\"mutipleData\").className = \"hide\"; \n");
//		returnString.append("\n } \n");
//
//		// if multiple devices are having same name then we are adding the
//		// device detail to list to
//		// select uniquely
//		// The function addItem is used add the information about the
//		// device/branch to the list
//		returnString.append("\n function AddItem(Text,Value) \n");
//		returnString.append("\n { \n");
//		returnString.append("\n var select = document.getElementById(\"lstBox\"); \n");
//		returnString.append("\n select.options[select.options.length] = new Option(Text,Value); \n");
//		returnString.append("\n } \n");
//
//		// if user selected the particular device among several devices
//		// then following function is used locate the location of the device in
//		// the google map
//		returnString.append("\n function onClickLst(){ \n");
//		returnString.append("\n var list = document.getElementById(\"lstBox\"); \n");
//		returnString.append("\n var index = lstBox.options[list.selectedIndex].value; \n");
//		returnString.append("\n submitLocation(jsArray[index][1],jsArray[index][2]); \n");
//		returnString.append("\n } \n");
//
//		// This is function is used to remove all information from list
//		returnString.append("\n function removeAll(){ \n");
//		returnString.append("\n var list = document.getElementById(\"lstBox\"); \n");
//		returnString.append("\n list.options.length = 0; \n");
//		returnString.append("\n } \n");
//
//		// override equal to so that ignore the case
//		returnString.append("\n String.prototype.equalTo = function( str ) \n");
//		returnString.append("\n { \n");
//		returnString.append("\n return this.toLowerCase() == str.toLowerCase(); \n");
//		returnString.append("\n } \n");
//
//		// The following function is used to search the device/branch details in
//		// the java script
//		// array
//		returnString.append("\nfunction searchPlace()  \n");
//		returnString.append("\n { \n");
//		returnString.append("\n  var address = document.getElementById('address').value; \n");
//		returnString.append("\n  var count=0; \n");
//		returnString.append("\n  removeAll(); \n");
//		returnString.append("\n for(i=0;i<jsArray.length;i++){ \n");
//		returnString.append("\n if(jsArray[i][0]){ \n");
//
//		returnString.append("\n if(address.equalTo(jsArray[i][0])){ \n");
//		returnString.append("\n count=count+1; \n");
//		returnString.append("\n if(count==1){searchedIndex=i;}\n");
//		returnString.append("\n else if(count==2){\n");
//		returnString.append("\n AddItem(jsArray[searchedIndex][3],searchedIndex); \n");
//		returnString.append("\n AddItem(jsArray[i][3],i);} \n");
//		returnString.append("\n else if(count>2){AddItem(jsArray[i][3],i);}\n");
//		returnString.append("\n } \n");
//		returnString.append("\n } \n");
//		returnString.append("\n } \n");
//		returnString.append("\n if(count==0){\n");
//		returnString.append("\n  document.getElementById(\"nodata\").className = \"view\";\n");
//		returnString.append("\n  document.getElementById(\"mutipleData\").className = \"hide\";\n");
//		returnString.append("\n }else if(count==1){\n");
//		returnString.append("\n  submitLocation(jsArray[searchedIndex][1],jsArray[searchedIndex][2]);\n");
//		returnString.append("\n  document.getElementById(\"nodata\").className = \"hide\";\n");
//		returnString.append("\n  document.getElementById(\"mutipleData\").className = \"hide\";\n");
//		returnString.append("\n }else{ \n");
//		returnString.append("\n document.getElementById(\"nodata\").className = \"hide\";\n");
//		returnString.append("\n document.getElementById(\"mutipleData\").className = \"hide\";\n");
//		returnString.append("\n }} \n");
//
//		returnString.append("\n google.load(\"earth\", \"1\"); \n");
//		returnString.append("\n google.load(\"maps\", \"2.99\"); \n");
//		returnString.append("\n document.getElementById(\"nodata\").className = \"hide\"; \n");
//		returnString.append("\n var ge = null; \n");
//		returnString.append("\n var geocoder; \n");
//		returnString.append("\n function init() { \n");
//		returnString.append("\n geocoder = new GClientGeocoder(); \n");
//		returnString.append("\n google.earth.createInstance(\"map3d\", initCB, failureCB); \n");
//		returnString.append("\n } \n");
//		returnString.append("\n function initCB(object) { \n");
//		returnString.append("\n ge = object; \n");
//		returnString.append("\n ge.getWindow().setVisibility(true); \n");
//		returnString.append("\n ge.getNavigationControl().setVisibility(ge.VISIBILITY_AUTO); \n");
//		returnString.append("\n ge.getLayerRoot().enableLayerById(ge.LAYER_BORDERS, true); \n");
//		returnString.append("\n ge.getLayerRoot().enableLayerById(ge.LAYER_ROADS, true); \n");
//		returnString.append("\n var normal = ge.createIcon(''); \n");
//
//		returnString.append("\n var href = window.location.href; \n");
//
//		returnString
//				.append("\n var pagePath = href.substring(0, href.lastIndexOf('/')) + '/assets/common/pos.jpg'; \n");
//		returnString.append("\n var normal = ge.createIcon(''); \n");
//		returnString.append("\n  normal.setHref(pagePath); \n");
//		returnString.append("\n var iconNormal = ge.createStyle(''); \n");
//		returnString.append("\n iconNormal.getIconStyle().setIcon(normal); \n");
//		returnString.append("\n var highlight = ge.createIcon(''); \n");
//		returnString.append("\n highlight.setHref(pagePath); \n");
//		returnString.append("\n var iconHighlight = ge.createStyle(''); \n");
//		returnString.append("\n iconHighlight.getIconStyle().setIcon(highlight); \n");
//		returnString.append("\n var styleMap = ge.createStyleMap(''); \n");
//		returnString.append("\n styleMap.setNormalStyle(iconNormal); \n");
//		returnString.append("\n styleMap.setHighlightStyle(iconHighlight); \n");
//
//		returnString.append(
//				"\n var pagePathEs = href.substring(0, href.lastIndexOf('/')) + '/assets/common/eStation.jpg'; \n");
//		returnString.append("\n var normalEs = ge.createIcon(''); \n");
//		returnString.append("\n  normalEs.setHref(pagePathEs); \n");
//		returnString.append("\n var iconNormalEs = ge.createStyle(''); \n");
//		returnString.append("\n iconNormalEs.getIconStyle().setIcon(normalEs); \n");
//		returnString.append("\n var highlightEs = ge.createIcon(''); \n");
//		returnString.append("\n highlightEs.setHref(pagePathEs); \n");
//		returnString.append("\n var iconHighlightEs = ge.createStyle(''); \n");
//		returnString.append("\n iconHighlightEs.getIconStyle().setIcon(highlightEs); \n");
//		returnString.append("\n var styleMapEs = ge.createStyleMap(''); \n");
//		returnString.append("\n styleMapEs.setNormalStyle(iconNormalEs); \n");
//		returnString.append("\n styleMapEs.setHighlightStyle(iconHighlightEs); \n");
//
//		// Adding the kiosk image in the google map
//		returnString.append(
//				"\n var pagePathKiosk = href.substring(0, href.lastIndexOf('/')) + '/assets/common/kiosk.jpg'; \n");
//		returnString.append("\n var normalKiosk = ge.createIcon(''); \n");
//		returnString.append("\n  normalKiosk.setHref(pagePathKiosk); \n");
//		returnString.append("\n var iconNormalKiosk = ge.createStyle(''); \n");
//		returnString.append("\n iconNormalKiosk.getIconStyle().setIcon(normalKiosk); \n");
//		returnString.append("\n var highlightKiosk = ge.createIcon(''); \n");
//		returnString.append("\n highlightKiosk.setHref(pagePathKiosk); \n");
//		returnString.append("\n var iconHighlightKiosk = ge.createStyle(''); \n");
//		returnString.append("\n iconHighlightKiosk.getIconStyle().setIcon(highlightKiosk); \n");
//		returnString.append("\n var styleMapKiosk = ge.createStyleMap(''); \n");
//		returnString.append("\n styleMapKiosk.setNormalStyle(iconNormalKiosk); \n");
//		returnString.append("\n styleMapKiosk.setHighlightStyle(iconHighlightKiosk); \n");
//
//		returnString.append(
//				"\n var pagePathB = href.substring(0, href.lastIndexOf('/')) + '/assets/common/branch.jpg'; \n");
//		returnString.append("\n var normalB = ge.createIcon(''); \n");
//		returnString.append("\n  normalB.setHref(pagePathB); \n");
//		returnString.append("\n var iconNormalB = ge.createStyle(''); \n");
//		returnString.append("\n iconNormalB.getIconStyle().setIcon(normalB); \n");
//		returnString.append("\n var highlightB = ge.createIcon(''); \n");
//		returnString.append("\n highlightB.setHref(pagePathB); \n");
//		returnString.append("\n var iconHighlightB = ge.createStyle(''); \n");
//		returnString.append("\n iconHighlightB.getIconStyle().setIcon(highlightB); \n");
//		returnString.append("\n var styleMapB = ge.createStyleMap(''); \n");
//		returnString.append("\n styleMapB.setNormalStyle(iconNormalB); \n");
//		returnString.append("\n styleMapB.setHighlightStyle(iconHighlightB); \n");
//		// Create point1
//		returnString.append(placeString);
//		// Create point2
//
//		returnString.append("\n } \n");
//		returnString.append("\n function failureCB(object) { \n");
//		returnString.append("\n alert('Load Failed'); \n");
//		returnString.append("\n } \n");
//		returnString.append("\n function submitLocation(lat,lon) { \n");
//		returnString.append("\n if (ge == null) { \n");
//		returnString.append("\n return 0; \n");
//		returnString.append("\n } \n");
//		returnString.append("\n var la = ge.createLookAt(''); \n");
//		returnString.append("\n la.set(lat,lon,0,ge.ALTITUDE_RELATIVE_TO_GROUND,0,0,75000); \n");
//		returnString.append("\n ge.getView().setAbstractView(la); \n");
//		returnString.append("\n } \n");
//
//		returnString.append("\n </script> \n");
//
//		returnString.append("\n <table style='align:center;width:100%'> \n");
//		returnString.append("\n <tr> \n");
//		returnString.append("\n <td colspan='3'  style='width:80%'>  \n");
//		returnString.append("\n <div id='map3d_container' style='border: 1px solid silver; height: 600px;'> \n");
//		returnString.append("\n <div id='map3d' style='height: 100%;'></div> \n");
//		returnString.append("\n </div> \n");
//		returnString.append("\n </td> \n");
//		returnString.append("\n </tr> \n");
//		returnString.append("\n <tr> \n");
//		returnString.append("\n <td style='width:25%'> \n");
//		returnString.append("\n <span>\n");
//		returnString.append("\n <fieldset style=\"margin-bottom: 77px;\"> \n");
//		returnString.append(
//				"\n <legend>" + (String) pageContext.getRequest().getAttribute("branches.caption") + "</legend> \n");
//		returnString.append("\n <select size=\"5\" id=\"brnLstBox\" onClick=\"onClickBrnLst()\"></select> \n");
//		returnString.append("\n </fieldset>  \n");
//		returnString.append("\n </span>\n");
//		returnString.append("\n </td> \n");
//		returnString.append("\n <td style='width:50%;'> \n");
//		returnString.append("\n <span id=\"mutipleData\"> \n");
//		returnString.append("\n <fieldset style=\"margin-bottom: 77px;\"> \n");
//		returnString.append(
//				"\n <legend>" + pageContext.getRequest().getAttribute("mulitple.data.caption") + "</legend> \n");
//		returnString.append("\n <select size=\"5\" id=\"lstBox\" onClick=\"onClickLst()\"></select> \n");
//		returnString.append("\n </fieldset>  \n");
//		returnString.append("\n </span></td>\n");
//		returnString.append("\n <td style='width:25%;align:right;'> \n");
//		returnString.append("\n <div style='align:right;'> \n");
//		returnString.append("\n <form name='searchform' id='searchform' action='javascript:searchPlace()'> \n");
//		returnString.append("\n <fieldset> \n");
//		returnString.append("\n <legend>" + getHeaders().get("search") + "</legend> \n");
//		returnString.append("\n <div>\n");
//		returnString.append("\n <label>" + getSearchDetail() + "</label> \n");
//		returnString.append("\n </div>\n");
//		returnString.append("\n <div>\n");
//		returnString.append("\n <span>\n");
//		returnString.append("\n <input type=text size=20 id='address' title='" + getSearchDetail() + "'></input> \n");
//		returnString.append("\n </span>\n");
//		returnString.append("\n <span>\n");
//		returnString.append("\n <input type='image' id='search' src='assets/search.jpg' value='Submit' alt='"
//				+ getHeaders().get("search") + "'> \n");
//		returnString.append("\n </span>\n");
//		returnString.append("\n </div>\n");
//		returnString.append("\n <div>\n");
//
//		returnString.append("\n <label id='nodata'>" + ((String) pageContext.getRequest().getAttribute("noDataCaption"))
//				+ "</label> \n");
//		returnString.append("\n </div>\n");
//		returnString.append("\n </fieldset> \n");
//		returnString.append("\n </form> \n");
//		returnString.append("\n </div> \n");
//		returnString.append("\n </td> \n");
//		returnString.append("\n </tr> \n");
//		returnString.append("\n </table> \n");
//
//		return returnString.toString();
//	}
//
	private String getSearchDetail() {
		return (String) pageContext.getRequest().getAttribute("searchDetail");
	}

	private String check(StringBuffer searchString) {
		String s = searchString.toString();
		if (StringUtil.isEmpty(s))
			s = "";
		return s;
	}

	/**
	 * Gets the desc string.
	 * 
	 * @param pos
	 *            the pos
	 * @param txns
	 *            the txns
	 * @return the desc string
	 */
//	private String getDescString(POS pos, List<ClientTransaction> txns) {
//		Map<Object, Object> headers = getHeaders();
//		StringBuffer template = new StringBuffer();
//
//		template.append("<div class=\"tabcontainer\">'+ \n");
//		template.append("\n '<ul class=\"tabnav\">'+ \n");
//		template.append("\n '<li><a href=\"#tab1\">" + headers.get("txntab") + "</a></li>'+ \n");
//		template.append("\n '<li><a href=\"#tab2\">" + headers.get("agenttab") + "</a></li>'+ \n");
//		template.append("\n '<li><a href=\"#tab3\">" + headers.get("postab") + "</a></li>'+ \n");
//		template.append("\n '</ul>'+ \n");
//		template.append("\n '<div class=\"tabcontents\">'+ \n");
//		template.append(getTxnTab(txns));
//		template.append(getDeviceTab(pos));
//		template.append(getAgentTap(pos));
//		template.append("\n '</div>'+ \n");
//		template.append("\n '</div>");
//		return template.toString();
//	}
//
	/**
	 * Gets the desc string.
	 * 
	 * @param kiosk
	 *            the kiosk
	 * @param txns
	 *            the txns
	 * @return the desc string
	 */
//	private String getKioskDescString(Kiosk kiosk, List<ClientTransaction> txns) {
//		Map<Object, Object> headers = getHeaders();
//		StringBuffer template = new StringBuffer();
//
//		template.append("<div class=\"tabcontainer\">'+ \n");
//		template.append("\n '<ul class=\"tabnav\">'+ \n");
//		template.append("\n '<li><a href=\"#tab1\">" + headers.get("txntab") + "</a></li>'+ \n");
//
//		template.append("\n '<li><a href=\"#tab3\">" + "Kiosk" + "</a></li>'+ \n");
//		template.append("\n '</ul>'+ \n");
//		template.append("\n '<div class=\"tabcontents\">'+ \n");
//		template.append(getTxnTab(txns));
//		template.append(getKioskDeviceTab(kiosk));
//		template.append("\n '</div>'+ \n");
//		template.append("\n '</div>");
//		return template.toString();
//	}

	/**
	 * Gets the desc string.
	 * 
	 * @param Branch
	 *            the Branch
	 * @param txns
	 *            the txns
	 * @return the desc string
	 */
//	private String getBranchDescString(List<ClientTransaction> txns) {
//		Map<Object, Object> headers = getHeaders();
//		StringBuffer template = new StringBuffer();
//
//		template.append("<div class=\"tabcontainer\">'+ \n");
//		template.append("\n '<ul class=\"tabnav\">'+ \n");
//		template.append("\n '<li>" + headers.get("txntab") + "</li>'+ \n");
//
//		template.append("\n '</ul>'+ \n");
//		template.append("\n '<div class=\"tabcontents\">'+ \n");
//		template.append(getTxnTab(txns));
//		template.append("\n '</div>'+ \n");
//		template.append("\n '</div>");
//		return template.toString();
//	}

	/**
	 * Gets the txn tab.
	 * 
	 * @param pos
	 *            the pos
	 * @param txns
	 *            the txns
	 * @return the txn tab
	 */
//	private String getTxnTab(List<ClientTransaction> txns) {
//		Map<Object, Object> headers = getHeaders();
//		String dateTimeFormat = (String) pageContext.getRequest().getAttribute("dateTimeFormat");
//		DateFormat df = new SimpleDateFormat(dateTimeFormat);
//		StringBuffer buf = new StringBuffer();
//
//		buf.append("\n '<div style=\"align:right;\" class=\"content\" id=\"tab1\">'+ \n");
//		buf.append("\n '<table cellpadding=\"2\" cellspacing=\"0\">'+");
//		if (!ObjectUtil.isListEmpty(txns)) {
//			buf.append("\n '<th bgcolor=\"#F6F9F9\">'+ ");
//			buf.append("\n '" + headers.get("agent.profileId") + "'+ ");
//			buf.append("\n '</th>'+ ");
//			buf.append("\n '<th bgcolor=\"#F6F9F9\">'+ ");
//			buf.append("\n '" + headers.get("datetime") + "'+ ");
//			buf.append("\n '</th>'+ ");
//			buf.append("\n '<th bgcolor=\"#F6F9F9\">'+ ");
//			buf.append("\n '" + headers.get("txntype") + "'+ ");
//			buf.append("\n '</th>'+ ");
//			buf.append("\n '<th bgcolor=\"#F6F9F9\">'+ ");
//			buf.append("\n '" + headers.get("clientid") + "'+ ");
//			buf.append("\n '</th>'+ ");
//			buf.append("\n '<th bgcolor=\"#F6F9F9\">'+ ");
//			buf.append("\n '" + headers.get("clientaccno") + "'+");
//			buf.append("\n '</th>'+ ");
//			buf.append("\n '<th bgcolor=\"#F6F9F9\">'+ ");
//			buf.append("\n '" + headers.get("amount") + "'+");
//			buf.append("\n '</th>'+ ");
//
//			int i = 0;
//
//			for (ClientTransaction data : txns) {
//
//				if (i % 2 != 0) {
//					buf.append("\n '<tr bgcolor=\"#F0F8FF\">'+ ");
//				} else {
//					buf.append("\n '<tr>'+ ");
//				}
//				buf.append("\n '<td>'+ ");
//				buf.append("\n '" + data.getAgentId() + "'+ ");
//				buf.append("\n '</td>'+ ");
//				buf.append("\n '<td>'+ ");
//				buf.append("\n '" + df.format(data.getDeviceTime()) + "'+ ");
//				buf.append("\n '</td>'+ ");
//				buf.append("\n '<td>'+ ");
//				buf.append("\n '" + StringUtil.getString(getTxnTypes().get(Integer.valueOf(data.getTransType())), "")
//						+ "'+ ");
//				buf.append("\n '</td>'+ ");
//				buf.append("\n '<td>'+ ");
//				buf.append("\n '" + (String) data.getClientId() + "'+ ");
//				buf.append("\n '</td>'+ ");
//				buf.append("\n '<td>'+ ");
//				buf.append("\n '" + (String) data.getClientAccountNumber() + "'+ \n");
//				buf.append("\n '</td>'+ ");
//				buf.append("\n '<td>'+ ");
//				buf.append("\n '" + getCurrency(data.getCurrency()) + (double) data.getAmount() + "'+ \n");
//				buf.append("\n '</td>'+ ");
//				buf.append("\n '</tr>'+ ");
//				i++;
//			}
//
//		} else {
//			buf.append("\n '<tr>'+ ");
//			buf.append("\n '<td>'+ ");
//			buf.append("\n '" + headers.get("notxn") + "'+ \n");
//			buf.append("\n '</td>'+ ");
//			buf.append("\n '</tr>'+ ");
//		}
//
//		buf.append("\n '</table>'+ \n");
//
//		buf.append("\n '</div>'+ \n");
//		return buf.toString();
//	}
//
	private String getCurrency(String currency) {
		if ("cor".equalsIgnoreCase(currency)) {
			return "C$";
		} else if ("dol".equalsIgnoreCase(currency)) {
			return "$ ";
		}
		return currency;
	}

	/**
	 * Gets the agent tap.
	 * 
	 * @param pos
	 *            the pos
	 * @return the agent tap
	 */
//	private String getAgentTap(POS pos) {
//		Map<Object, Object> headers = getHeaders();
//		StringBuffer buf = new StringBuffer();
//
//		buf.append("\n '<div style=\"align:right;\" class=\"content\" id=\"tab2\">'+ \n");
//		buf.append("\n '<table cellpadding=\"2\" cellspacing=\"0\">'+");
//		buf.append("\n '<th bgcolor=\"#F6F9F9\">'+ ");
//		buf.append("\n '" + headers.get("agent.profileId") + "'+ ");
//		buf.append("\n '</th>'+ ");
//		buf.append("\n '<th bgcolor=\"#F6F9F9\">'+ ");
//		buf.append("\n '" + headers.get("agent.enrolledStationId") + "'+ ");
//		buf.append("\n '</th>'+ ");
//		buf.append("\n '<th bgcolor=\"#F6F9F9\">'+ ");
//		buf.append("\n '" + headers.get("agent.enrolledAgentId") + "'+ ");
//		buf.append("\n '</th>'+ ");
//		buf.append("\n '<th bgcolor=\"#F6F9F9\">'+ ");
//		buf.append("\n '" + headers.get("personalInfo.firstName") + "'+ ");
//		buf.append("\n '</th>'+ ");
//		buf.append("\n '<th bgcolor=\"#F6F9F9\">'+ ");
//		buf.append("\n '" + headers.get("contactInfo.phone") + "'+ ");
//		buf.append("\n '</th>'+ ");
//
//		Set<Agent> agents = pos.getAgents();
//		int i = 0;
//
//		for (Agent agent : agents) {
//			if (i % 2 != 0) {
//				buf.append("\n '<tr bgcolor=\"#F0F8FF\">'+ ");
//			} else {
//				buf.append("\n '<tr>'+ ");
//			}
//			buf.append("\n '<td>'+ ");
//			buf.append("\n '" + agent.getProfileId() + "'+ ");
//			buf.append("\n '</td>'+ ");
//			buf.append("\n '<td>'+ ");
//			buf.append("\n '" + agent.getEnrolledStationId() + "'+ ");
//			buf.append("\n '</td>'+ ");
//			buf.append("\n '<td>'+ ");
//			buf.append("\n '" + agent.getEnrolledAgentId() + "'+ ");
//			buf.append("\n '</td>'+ ");
//			buf.append("\n '<td>'+ ");
//			if (agent.getPersonalInfo() != null)
//				buf.append("\n '" + agent.getPersonalInfo().getName() + "'+ ");
//			buf.append("\n '</td>'+ ");
//			buf.append("\n '<td>'+ ");
//			buf.append("\n '</td>'+ ");
//			buf.append("\n '</tr>'+ ");
//			i++;
//		}
//		buf.append("\n '</table>'+");
//		buf.append("\n '</div>'+ \n");
//		return buf.toString();
//	}
//
	/**
	 * Gets the device tab.
	 * 
	 * @param pos
	 *            the pos
	 * @return the device tab
	 */
//	private String getDeviceTab(POS pos) {
//		Map<Object, Object> headers = getHeaders();
//		StringBuffer buf = new StringBuffer();
//
//		buf.append("\n '<div style=\"align:right;\" class=\"content\" id=\"tab3\">'+ \n");
//		buf.append("\n '<table>'+");
//		buf.append("\n '<tr>'+ ");
//		buf.append("\n '<td ><strong>'+ ");
//		buf.append("\n '" + headers.get("device.deviceId") + "'+ ");
//		buf.append("\n '</strong></td>'+ ");
//		buf.append("\n '<td ><strong>:</strong></td>'+ ");
//		buf.append("\n '<td>'+ ");
//		buf.append("\n '" + pos.getDeviceId() + "'+ ");
//		buf.append("\n '</td>'+ ");
//		buf.append("\n '</tr>'+ ");
//		buf.append("\n '<tr>'+ ");
//		buf.append("\n '<td ><strong>'+ ");
//		buf.append("\n '" + headers.get("device.serialNo") + "'+ ");
//		buf.append("\n '</strong></td>'+ ");
//		buf.append("\n '<td ><strong>:</strong></td>'+ ");
//		buf.append("\n '<td>'+ ");
//		buf.append("\n '" + pos.getSerialNumber() + "'+ ");
//		buf.append("\n '</td>'+ ");
//		buf.append("\n '</tr>'+ ");
//		buf.append("\n '<tr>'+ ");
//		buf.append("\n '<td ><strong>'+ ");
//		buf.append("\n '" + headers.get("device.name") + "'+");
//		buf.append("\n '</strong></td>'+ ");
//		buf.append("\n '<td ><strong>:</strong></td>'+ ");
//		buf.append("\n '<td>'+ ");
//		buf.append("\n '" + pos.getName() + "'+");
//		buf.append("\n '</td>'+ ");
//		buf.append("\n '</tr>'+ ");
//		buf.append("\n '<tr>'+ ");
//		buf.append("\n '<td ><strong>'+ ");
//		buf.append("\n '" + headers.get("device.branches") + "'+");
//		buf.append("\n '</strong></td>'+ ");
//		buf.append("\n '<td ><strong>:</strong></td>'+ ");
//		buf.append("\n '<td>'+ ");
//		if (pos.getBranch() != null)
//			buf.append("\n '" + pos.getBranch().getName() + "'+");
//		buf.append("\n '</td>'+ ");
//		buf.append("\n '</tr>'+ ");
//		buf.append("\n '</table>'+");
//		buf.append("\n '</div>'+ \n");
//		return buf.toString();
//	}
//
	/**
	 * Gets the device tab.
	 * 
	 * @param pos
	 *            the pos
	 * @return the device tab
	 */
	private String getEnrollInfoTab(Map<String, Integer> clientInfo) {
		Map<Object, Object> headers = getHeaders();
		StringBuffer buf = new StringBuffer();

		buf.append("\n '<div style=\"align:right;\" class=\"content\" id=\"tab3\">'+ \n");
		buf.append("\n '<table>'+");
		buf.append("\n '<tr>'+ ");
		buf.append("\n '<td ><strong>'+ ");
		buf.append("\n '" + pageContext.getRequest().getAttribute("noOfPersonalFormCaption") + "'+ ");
		buf.append("\n '</strong></td>'+ ");
		buf.append("\n '<td ><strong>:</strong></td>'+ ");
		buf.append("\n '<td>'+ ");
		if (clientInfo != null && clientInfo.get("noOfPersonalForm") != null)
			buf.append("\n '" + clientInfo.get("noOfPersonalForm") + "'+ ");
		buf.append("\n '</td>'+ ");
		buf.append("\n '</tr>'+ ");
		buf.append("\n '<tr>'+ ");
		buf.append("\n '<td ><strong>'+ ");
		buf.append("\n '" + pageContext.getRequest().getAttribute("noOfCorporateFormCaption") + "'+");
		buf.append("\n '</strong></td>'+ ");
		buf.append("\n '<td ><strong>:</strong></td>'+ ");
		buf.append("\n '<td>'+ ");
		if (clientInfo != null && clientInfo.get("noOfCorporateForm") != null)
			buf.append("\n '" + ((Integer) clientInfo.get("noOfCorporateForm")).intValue() + "'+");
		buf.append("\n '</td>'+ ");
		buf.append("\n '</tr>'+ ");
		buf.append("\n '</table>'+");
		buf.append("\n '</div>'");

		return buf.toString();
	}

	/**
	 * Gets the device tab.
	 * 
	 * @param kiosk
	 *            the kiosk
	 * @return the device tab
	 */
//	private String getKioskDeviceTab(Kiosk kiosk) {
//		Map<Object, Object> headers = getHeaders();
//		StringBuffer buf = new StringBuffer();
//
//		buf.append("\n '<div style=\"align:right;\" class=\"content\" id=\"tab3\">'+ \n");
//		buf.append("\n '<table>'+");
//		buf.append("\n '<tr>'+ ");
//		buf.append("\n '<td ><strong>'+ ");
//		buf.append("\n '" + headers.get("device.deviceId") + "'+ ");
//		buf.append("\n '</strong></td>'+ ");
//		buf.append("\n '<td ><strong>:</strong></td>'+ ");
//		buf.append("\n '<td>'+ ");
//		buf.append("\n '" + kiosk.getDeviceId() + "'+ ");
//		buf.append("\n '</td>'+ ");
//		buf.append("\n '</tr>'+ ");
//		buf.append("\n '<tr>'+ ");
//		buf.append("\n '<td ><strong>'+ ");
//		buf.append("\n '" + headers.get("device.serialNo") + "'+ ");
//		buf.append("\n '</strong></td>'+ ");
//		buf.append("\n '<td ><strong>:</strong></td>'+ ");
//		buf.append("\n '<td>'+ ");
//		buf.append("\n '" + kiosk.getSerialNumber() + "'+ ");
//		buf.append("\n '</td>'+ ");
//		buf.append("\n '</tr>'+ ");
//		buf.append("\n '<tr>'+ ");
//		buf.append("\n '<td ><strong>'+ ");
//		buf.append("\n '" + headers.get("device.name") + "'+");
//		buf.append("\n '</strong></td>'+ ");
//		buf.append("\n '<td ><strong>:</strong></td>'+ ");
//		buf.append("\n '<td>'+ ");
//		buf.append("\n '" + kiosk.getName() + "'+");
//		buf.append("\n '</td>'+ ");
//		buf.append("\n '</tr>'+ ");
//		buf.append("\n '<tr>'+ ");
//		buf.append("\n '<td ><strong>'+ ");
//		buf.append("\n '" + headers.get("device.branches") + "'+");
//		buf.append("\n '</strong></td>'+ ");
//		buf.append("\n '<td ><strong>:</strong></td>'+ ");
//		buf.append("\n '<td>'+ ");
//		if (kiosk.getBranch() != null)
//			buf.append("\n '" + kiosk.getBranch().getName() + "'+");
//		buf.append("\n '</td>'+ ");
//		buf.append("\n '</tr>'+ ");
//		buf.append("\n '</table>'+");
//		buf.append("\n '</div>'+ \n");
//		return buf.toString();
//	}
//
//	private String getLinkString() {
//		StringBuffer linkString = new StringBuffer();
//		StringBuffer listString = new StringBuffer();
//		List<Location> locations = getLocation();
//		int arraySize = locations.size() + 1;
//		int arrayIndex = 0;
//
//		linkString.append("\n var brnArray = new Array(" + arraySize + "); \n");
//		for (int j = 0; j < arraySize; j++) {
//			linkString.append("\n brnArray[" + j + "] = new Array(3); \n");
//		}
//		listString.append("\n function addBrnLst(){ \n");
//		listString.append("\n var select = document.getElementById(\"brnLstBox\"); \n");
//		for (Location location : locations) {
//			if (location != null && location != null && location.getLongitude() != null) {
//				linkString.append("\n brnArray[" + arrayIndex + "][0] = '" + location.getName() + "'; \n");
//				linkString.append("\n brnArray[" + arrayIndex + "][1] = " + location.getLatitude() + "; \n");
//				linkString.append("\n brnArray[" + arrayIndex + "][2] = " + location.getLongitude() + "; \n");
//				listString.append("\n select.options[select.options.length] = new Option('" + location.getName() + "',"
//						+ arrayIndex + "); \n");
//				arrayIndex++;
//			}
//		}
//		listString.append("\n } \n");
//		listString.append("\n function onClickBrnLst(){ \n");
//		listString.append("\n var list = document.getElementById(\"brnLstBox\"); \n");
//		listString.append("\n var index = brnLstBox.options[list.selectedIndex].value; \n");
//		listString.append("\n submitLocation(brnArray[index][1],brnArray[index][2]); \n");
//		listString.append("\n } \n");
//		linkString.append(listString);
//		return check(new StringBuffer(linkString.toString()));
//	}

}
