/*
 * FileUtil.java
 * Copyright (c) 2008-2009, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Blob;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

/**
 * The Class FileUtil.
 * @author $Author: aravind $
 * @version $Rev: 74 $, $Date: 2009-10-08 08:19:33 +0530 (Thu, 08 Oct 2009) $
 */
public class FileUtil {

    private static Logger LOGGER = Logger.getLogger(FileUtil.class);

    /**
     * Gets the binary file content.
     * @param file the file
     * @return the binary file content
     */
    public static byte[] getBinaryFileContent(File file) {

        byte[] fileContent = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];

            while (fis.read(b) != -1) {
                baos.write(b);
                b = new byte[1024];
            }

            fileContent = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    /**
     * Gets the binary file content.
     * @param is the is
     * @return the binary file content
     */
    public static byte[] getBinaryFileContent(InputStream is) {

        byte[] fileContent = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];

            while (is.read(b) != -1) {
                baos.write(b);
                b = new byte[1024];
            }

            fileContent = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent;
    }
    
    /**
     * Gets the binary file content.
     * @param file the file
     * @return the binary file content
     */
    public static byte[] getBinaryFileContent(String file) {

        byte[] fileContent = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];

            while (fis.read(b) != -1) {
                baos.write(b);
                b = new byte[1024];
            }

            fileContent = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    /**
     * Gets the binary file content.
     * @param blob the blob
     * @return the binary file content
     */
    public static byte[] getBinaryFileContent(Blob blob) {

        byte[] contents = null;
        if (blob != null) {

            BufferedInputStream bis;
            try {
                bis = new BufferedInputStream(blob.getBinaryStream());
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int length = 0;

                while ((length = bis.read(buffer)) != -1) {
                    bao.write(buffer, 0, length);

                }

                bao.close();
                bis.close();

                contents = bao.toByteArray();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return contents;
    }

    /**
     * Un zip.
     * @param source the source
     * @return the hashtable
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static Hashtable<String, File> unZip(File source) throws IOException {

        Hashtable<String, File> FingerPrintFile = new Hashtable<String, File>();

        try {
            ZipFile zf = new ZipFile(source);

            Enumeration entries = zf.entries();

            while (entries.hasMoreElements()) {

                ZipEntry ze = (ZipEntry) entries.nextElement();
                File f1 = null;
                String key = null;
                if (ze.getName().contains("lh1")) {
                    key = "LHFP1";
                    f1 = new File("LHFP1");
                } else if (ze.getName().contains("lh2")) {
                    key = "LHFP2";
                    f1 = new File("LHFP2");
                } else if (ze.getName().contains("lh3")) {
                    key = "LHFP3";
                    f1 = new File("LHFP3");
                } else if (ze.getName().contains("rh1")) {
                    key = "RHFP1";
                    f1 = new File("RHFP1");
                } else if (ze.getName().contains("rh2")) {
                    key = "RHFP2";
                    f1 = new File("RHFP2");
                } else if (ze.getName().contains("rh3")) {
                    key = "RHFP3";
                    f1 = new File("RHFP3");
                }
                FingerPrintFile.put(key, f1);

                long size = ze.getSize();
                if (size > 0) {

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            zf.getInputStream(ze)));
                    String line;

                    while ((line = br.readLine()) != null) {

                        FileOutputStream out = new FileOutputStream(f1);
                        PrintStream p;

                        p = new PrintStream(out);

                        p.println(line);

                    }
                    br.close();

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return FingerPrintFile;
    }

    /**
     * Compress file.
     * @param byteArray the byte array
     * @return the byte[]
     */
    public static byte[] compressFile(byte[] byteArray) {

        LOGGER.info("Before:" + byteArray.length);
        byte[] commpressedData = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(baos);
            gzip.write(byteArray);
            gzip.close();
            gzip.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        commpressedData = baos.toByteArray();
        LOGGER.info("After:" + commpressedData.length);
        return commpressedData;
    }

    /**
     * Zip entry content.
     * @param zipIns the zip ins
     * @param zipEntry the zip entry
     * @return the byte[]
     */
    public static byte[] zipEntryContent(ZipInputStream zipIns, ZipEntry zipEntry) {

        ByteArrayOutputStream baisEntry = new ByteArrayOutputStream();
        int size;
        try {
            while ((size = zipIns.read()) != -1) {
                baisEntry.write(size);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        return baisEntry.toByteArray();
    }

    /**
     * Zip file.
     * @param filesMap the files map
     * @return the zip output stream
     */
    public static byte[] zipFile(Map<String, String> filesMap) {

        // take each item from file and add to zip input stream
        Iterator<String> iterator = filesMap.keySet().iterator();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        try {
            // iterate through the all map entries
            while (iterator.hasNext()) {
                String fileName = iterator.next();
                ZipEntry zipEntry = new ZipEntry(fileName);
                zos.putNextEntry(zipEntry);
                // write the file content in zip output stream
                zos.write(filesMap.get(fileName).getBytes());
            }
            zos.flush();
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    /**
     * Image to byte array.
     * @param bImage the b image
     * @return the byte[]
     */
    public static byte[] imageToByteArray(BufferedImage bImage) {

        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "png", bas);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = bas.toByteArray();
        return data;
    }

    /**
     * Create file input stream to zip file.
     * @param zipFileName the zip file name
     * @param fileMap the file map
     * @param fileExtension the file extension
     * @return the file input stream
     */
    public static FileInputStream createFileInputStreamToZipFile(String zipFileName,
            Map<String, InputStream> fileMap, String fileExtension) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis = null;
        String tempDirectory = getJavaTmpLocation();

        try {
            byte[] buffer = new byte[1024];
            zipFileName = tempDirectory+zipFileName;
            
            FileOutputStream fout = new FileOutputStream(zipFileName);

            ZipOutputStream zout = new ZipOutputStream(fout);
            zout.setMethod(ZipOutputStream.DEFLATED);
            // zout.setLevel();
            Set<String> keySet = fileMap.keySet();
            for (String fileKey : keySet) {

                InputStream is = fileMap.get(fileKey);
                zout.putNextEntry(new ZipEntry(fileKey + fileExtension));

                int length;
                while ((length = is.read(buffer)) > 0) {
                    zout.write(buffer, 0, length);
                }
                zout.closeEntry();
                is.close();
            }
            File file = new File(zipFileName);
            fis = new FileInputStream(file);
            zout.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fis;
    }

    /**
     * Store xls.
     * @param sessionId the session id
     * @return the string
     */
    
    
    
    
   public synchronized static String storeXls(String sessionId) {

        String properties = "java.io.tmpdir";
        String tempDirt = System.getProperty(properties);        
        String makeDir = tempDirt + "/" + sessionId;
        makeDir = makeDir.replace("\\", "/");
        makeDir = makeDir.replace("//", "/");
        removeDirectory(new File(makeDir));
        new File(makeDir).mkdirs();
        makeDir = makeDir + "/";
        return makeDir;
    }


    /**
     * Removes the directory.
     * @param directory the directory
     * @return true, if successful
     */
    public static boolean removeDirectory(File directory) {

        if (directory == null)
            return false;
        if (!directory.exists())
            return true;
        if (!directory.isDirectory())
            return false;

        String[] list = directory.list();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                File entry = new File(directory, list[i]);
                if (entry.isDirectory()) {
                    if (!removeDirectory(entry))
                        return false;
                } else {
                    if (!entry.delete())
                        return false;
                }
            }
        }
        return directory.delete();
    }
    
    public static File getConvertedPicture(byte[] imageByteArray,String QuestionCode) {

        File imageFile;

        try {

            BufferedImage imag = ImageIO.read(new ByteArrayInputStream(imageByteArray));
            String id = String.valueOf(DateUtil.getRevisionNumber())+"_"+QuestionCode;

            imageFile = new File(FileUtil.storeXls(id), "verificationImage" + id + ".jpg");
            ImageIO.write(imag, "jpg", imageFile);
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    
    public static String getJavaTmpLocation(){
    	String properties = "java.io.tmpdir";
        String tempDirt = System.getProperty(properties);        
        tempDirt = tempDirt.replace("\\", "/");
        tempDirt = tempDirt + "/";
        tempDirt = tempDirt.replace("//", "/");        
        return tempDirt;
    }

}