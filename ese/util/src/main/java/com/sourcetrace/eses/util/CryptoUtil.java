/*
 * CryptoUtil.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CryptoUtil {


	private static SecretKeySpec key;
	private static Cipher cipher;
	private static byte[] initVector = { 0x2A, 0x48, 0x6E, 0x7F, 0x46, 0x5F, 0x2A,0x70 };

	static {
	    byte[] seedKey = (new String("STRACEINDIAROCKTRIPLEDES")).getBytes();
        key = new SecretKeySpec(seedKey, "TripleDES");
        try {
            cipher = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
	}

	/**
	 * Gets the encrypted string.
	 *
	 * @param input the input
	 * @return the encrypted string
	 * @throws Exception the exception
	 */
	public static String getEncryptedString(String input) throws Exception {
		AlgorithmParameterSpec algParamSpec = new IvParameterSpec(initVector);

		cipher.init(Cipher.ENCRYPT_MODE, key, algParamSpec);
		byte[] encrypted = Base64
				.encodeBase64(cipher.doFinal(input.getBytes()));
		return new String(encrypted);

	}

	/**
	 * Gets the decrypted string.
	 *
	 * @param input the input
	 * @return the decrypted string
	 * @throws Exception the exception
	 */
	public static String getDecryptedString(String input) throws Exception {
		AlgorithmParameterSpec algParamSpec = new IvParameterSpec(initVector);
		cipher.init(Cipher.DECRYPT_MODE, key, algParamSpec);
		byte[] decrypted = cipher
				.doFinal(Base64.decodeBase64(input.getBytes()));
		return new String(decrypted);

	}

	/**
	 * Decrypt iv.
	 *
	 * @param input the input
	 * @throws Exception the exception
	 */
	public static void decryptIV(String input) throws Exception {
		try {
			AlgorithmParameterSpec algParamSpec = new IvParameterSpec(initVector);

			File f = new File(input);
			File files[] = f.listFiles();
			for (int i = 0; i < files.length; i++)
				if (files[i].isFile()) {

					FileInputStream fi = new FileInputStream(files[i]);
					byte[] data = new byte[fi.available()];
					fi.read(data);
					fi.close();
					cipher.init(Cipher.DECRYPT_MODE, key, algParamSpec);
					byte[] decrypted = cipher.doFinal(data);
                    System.out.println(new String(decrypted));
					FileOutputStream fo = new FileOutputStream(input+ files[i].getName());
					fo.write(decrypted);
                    System.out.println("Test"+ files[i].toString());
					fo.flush();
                    fo.close();

				}
		} catch (javax.crypto.BadPaddingException e) {

		}

	}
}
