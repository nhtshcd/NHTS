/*
 * KMLUtil.java
 * Copyright (c) 2015-2016 SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.util;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
public class KMLUtil {

    private static final Logger LOGGER = Logger.getLogger(KMLUtil.class);

    /**
     * Parses the co ordinates.
     * @param kmlFile
     * @return the list< object[]>
     */
    public static List<Object[]> parseCoOrdinates(File kmlFile) {

        List<Object[]> coordinateses = new ArrayList<Object[]>();
        try {
            NodeList coordinates = process(kmlFile, "//coordinates/text()");
            if (coordinates != null) {
                for (int i = 0; i < coordinates.getLength(); i++) {
                    Node n = coordinates.item(i);
                    String[] points = n.getNodeValue().split("\\s+");
                    for (String point : points) {
                        Scanner in = new Scanner(point);
                        in.useDelimiter(",");
                        if (in.hasNextDouble()) {
                            Object[] coordinates1 = new Object[2];
                            coordinates1[1] = in.nextDouble(); // longitude
                            coordinates1[0] = in.nextDouble(); // latitude
                            coordinateses.add(coordinates1);
                        }
                    }
                }
            }

        } catch (Exception t) {
            t.printStackTrace();
        }
        return coordinateses;
    }

    /**
     * Process.
     * @param kmlFile
     * @param pattern
     * @return the node list
     * @throws Exception
     */
    private static NodeList process(File kmlFile, String pattern) throws Exception {

        NodeList result = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setIgnoringElementContentWhitespace(true);

        DocumentBuilder builder = factory.newDocumentBuilder();

        // Create document from URL - if it's not a url, try a file
        InputStream in = null;

        Document document = builder.parse(kmlFile);
        document.getDocumentElement().normalize();

        XPath xpath = XPathFactory.newInstance().newXPath();

        /*
         * If namespaces being used NamespaceContextImpl nsctx = new NamespaceContextImpl();
         * nsctx.setNamespaceURI("xhtml", "http://www.w3.org/1999/xhtml");
         * xpath.setNamespaceContext(nsctx);
         */
        // Evaluate the document against the pattern
        NodeList nodeList = (NodeList) xpath.evaluate(pattern, document, XPathConstants.NODESET);

        if (nodeList.getLength() > 0) {
            result = nodeList;
        }

        return result;
    }

    /**
     * Calculate area.
     * @param coOrdinates
     * @return the double
     */
    public static double calculateArea(List<Object[]> coOrdinates) {

        double radius = 6378137;
        double diameter = radius * 2;
        double circumference = diameter * Math.PI;
        List<Double> listY = new ArrayList<Double>();
        List<Double> listX = new ArrayList<Double>();
        List<Double> listArea = new ArrayList<Double>();
        double latitudeRef = (Double) coOrdinates.get(0)[0]; // Latitude
        double longitudeRef = (Double) coOrdinates.get(0)[1]; // Longitude

        for (int i = 1; i < coOrdinates.size(); i++) {
            double latitude = (Double) coOrdinates.get(i)[0];
            double longitude = (Double) coOrdinates.get(i)[1];
            double vY = calculateYSegment(latitudeRef, latitude, circumference);

            listY.add(vY);

            double vX = calculateXSegment(longitudeRef, longitude, latitude, circumference);

            listX.add(vX);
        }

        for (int j = 1; j < listX.size(); j++) {
            double x1 = listX.get(j - 1);

            double y1 = listY.get(j - 1);

            double x2 = listX.get(j);
            double y2 = listY.get(j);

            double area = calculateAreaInSquareMeters(x1, x2, y1, y2);
            listArea.add(area);

        }

        double areasSum = 0;
        for (int i = 0; i < listArea.size(); i++) {
            double areaCal = listArea.get(i);
            areasSum = areasSum + areaCal;
        }
        double msqr = areasSum;

        areasSum = (msqr * 0.000247104393);
        areasSum = Math.abs(areasSum);
        double sqftArea = areasSum / 0.00024711;
        double hectAcre = areasSum / 2.4711;
        return hectAcre;
    }

    /**
     * Calculate y segment.
     * @param latitudeRef
     * @param latitude
     * @param circumference
     * @return the double
     */
    private static double calculateYSegment(double latitudeRef, double latitude,
            double circumference) {

        return (latitude - latitudeRef) * circumference / 360.0;
    }

    /**
     * Calculate x segment.
     * @param longitudeRef
     * @param longitude
     * @param latitude
     * @param circumference
     * @return the double
     */
    private static double calculateXSegment(double longitudeRef, double longitude, double latitude,
            double circumference) {

        return (longitude - longitudeRef) * circumference
                * Math.cos(0.017453292519943295769236907684886 * latitude) / 360.0;
    }

    /**
     * Calculate area in square meters.
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @return the double
     */
    private static double calculateAreaInSquareMeters(double x1, double x2, double y1, double y2) {

        return ((y1 * x2 - x1 * y2) / 2);
    }

    /**
     * Gets the middle point.
     * @param coordinates
     * @return the middle point
     */
    public static String getMiddlePoint(List<Object[]> coordinates) {

        if (!ObjectUtil.isListEmpty(coordinates)) {

            double x = 0;
            double y = 0;
            double z = 0;
            double w1 = 0;

            for (Object[] coordinate : coordinates) {
                double latitude = (Double.parseDouble(String.valueOf(coordinate[0])) * Math.PI) / 180;
                double longitude = (Double.parseDouble(String.valueOf(coordinate[1])) * Math.PI) / 180;

                x = x + Math.cos(latitude) * Math.cos(longitude);
                y = y + Math.cos(latitude) * Math.sin(longitude);
                z = z + Math.sin(latitude);
            }
            int total = coordinates.size();

            x = x / total;
            y = y / total;
            z = z / total;

            double centralLongitude = Math.atan2(y, x);
            double centralSquareRoot = Math.sqrt(x * x + y * y);
            double centralLatitude = Math.atan2(z, centralSquareRoot);

            double cLong = (centralLongitude * 180) / Math.PI;
            double cLat = (centralLatitude * 180) / Math.PI;

            LOGGER.info("Longitude->" + cLong + "<----->Latitude->" + cLat);

            return String.valueOf(DoubleUtil.getSixDecimalPointString(cLat)) + "," + String.valueOf(DoubleUtil.getSixDecimalPointString(cLong));
        } else {
            return null;
        }
    }
    
    /**
     * Gets the middle point.
     * @param coordinates
     * @return the middle point
     */
    public static String getMiddlePointData(List<Object> coordinates) {

        if (!ObjectUtil.isListEmpty(coordinates)) {

            double x = 0;
            double y = 0;
            double z = 0;
            double w1 = 0;

            for (Object coordinate : coordinates) {
                double latitude = (Double.parseDouble("0") * Math.PI) / 180;
                double longitude = (Double.parseDouble("0") * Math.PI) / 180;

                x = x + Math.cos(latitude) * Math.cos(longitude);
                y = y + Math.cos(latitude) * Math.sin(longitude);
                z = z + Math.sin(latitude);
            }
            int total = coordinates.size();

            x = x / total;
            y = y / total;
            z = z / total;

            double centralLongitude = Math.atan2(y, x);
            double centralSquareRoot = Math.sqrt(x * x + y * y);
            double centralLatitude = Math.atan2(z, centralSquareRoot);

            double cLong = (centralLongitude * 180) / Math.PI;
            double cLat = (centralLatitude * 180) / Math.PI;

            LOGGER.info("Longitude->" + cLong + "<----->Latitude->" + cLat);

            return String.valueOf(DoubleUtil.getSixDecimalPointString(cLat)) + "," + String.valueOf(DoubleUtil.getSixDecimalPointString(cLong));
        } else {
            return null;
        }
    }
    @SuppressWarnings("unchecked")
	public static Document downloadKML(
			Map<String,  Map<String,List<Object[]>>> farmerMap)
			throws ParserConfigurationException, TransformerException {
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Element root = doc.createElement("kml");
		root.setAttribute("xmlns", "http://earth.google.com/kml/2.1");
		doc.appendChild(root);
		Element folder=doc.createElement("Folder");
		root.appendChild(folder);
		farmerMap.entrySet().stream().filter(f-> f.getValue()!=null && !ObjectUtil.isEmpty(f.getValue()) && f.getValue().size()>0).forEach(frmrData ->{
			String headerDetail = (String) frmrData.getKey();
			String[] dets = headerDetail.split(",");
			String textNode = "<b>Farmer Code:</b> "+dets[1]+"<br/><b>Farmer Name:</b> "+dets[0]+"<br /><b>Village:</b> "+dets[2];
			
			Element frmrFolder=doc.createElement("Folder");
			folder.appendChild(frmrFolder);
			
			Element frmFoldName=doc.createElement("name");
			frmFoldName.appendChild(doc.createTextNode(dets[0]+"_"+dets[1]));
			frmrFolder.appendChild(frmFoldName);
			frmrData.getValue().entrySet().stream().forEach(frmDet-> {
				
				String farmText=(String)frmDet.getKey();
				String[] farmEach=farmText.split(",");
				Element placemark = doc.createElement("Placemark");
				frmrFolder.appendChild(placemark);
				Element style= doc.createElement("Style");
				style.setAttribute("id", "style_"+farmEach[0]);
				placemark.appendChild(style);
				Element poly=doc.createElement("PolyStyle");
				style.appendChild(poly);
				Element describe=doc.createElement("description");
				describe.appendChild(doc.createTextNode(textNode+"<br /><b>Farm Name:</b> "+farmEach[1]+"<br/><b>Farm Area(HA):</b> "+farmEach[2]+"<br/><b>Farm Crops:</b> "+farmEach[4]));
				placemark.appendChild(describe);
				Element name=doc.createElement("name");
				placemark.appendChild(name);
				name.appendChild(doc.createTextNode(farmEach[1]));
				Element color=doc.createElement("color");
				color.appendChild(doc.createTextNode("3214F0FA"));
				poly.appendChild(color);
				Element lineStyle=doc.createElement("LineStyle");
				style.appendChild(lineStyle);
				Element lineColor =doc.createElement("color");
				lineColor.appendChild(doc.createTextNode("ff0000ff"));
				lineStyle.appendChild(lineColor);
				Element lineWidth=doc.createElement("width");
				lineWidth.appendChild(doc.createTextNode("2"));
				lineStyle.appendChild(lineWidth);
				Element styleUrl2 = doc.createElement("styleUrl");
				styleUrl2.appendChild(doc.createTextNode("#m_ylw-pushpin220"));
				placemark.appendChild(styleUrl2);
				Element multiGeo=doc.createElement("MultiGeometry");
				placemark.appendChild(multiGeo);
				Element polygon=doc.createElement("Polygon");
				multiGeo.appendChild(polygon);
				Element outBoundry=doc.createElement("outerBoundaryIs");
				polygon.appendChild(outBoundry);
				Element linerRing=doc.createElement("LinearRing");
				outBoundry.appendChild(linerRing);
				Element coor=doc.createElement("coordinates");
				linerRing.appendChild(coor);
				frmDet.getValue().stream().forEach(co ->{
					coor.appendChild(doc.createTextNode(co[1] + ","
							+ co[0] + " "));
				});
				
			});
			
			
		});
		return doc;
    }

   
}
