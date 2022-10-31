/*
 * TripleDES.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The Class CryptoUtil.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class TripleDES implements ICryptoUtil {

	private static final Logger LOGGER = Logger.getLogger("TripleDES.class");
	private String algorithm;
	private String transformation;
	private String key;
	private SecretKey secretKey;
	private Cipher encrypter;
	private Cipher decrypter;
	private static String secret_key = "STRACE@12345SAKTHIATHISOURCETRACE";
	/**
	 * Sets the algorithm.
	 * 
	 * @param algorithm
	 *            the new algorithm
	 */
	public void setAlgorithm(String algorithm) {

		this.algorithm = algorithm;
	}

	/**
	 * Sets the transformation.
	 * 
	 * @param transformation
	 *            the new transformation
	 */
	public void setTransformation(String transformation) {

		this.transformation = transformation;
	}

	/**
	 * Sets the key.
	 * 
	 * @param key
	 *            the new key
	 */
	public void setKey(String key) {

		this.key = key;
	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			DESedeKeySpec keySpec = new DESedeKeySpec(key.getBytes("UTF-8"));
			secretKey = SecretKeyFactory.getInstance(algorithm).generateSecret(
					keySpec);
			encrypter = Cipher.getInstance(transformation);
			encrypter.init(Cipher.ENCRYPT_MODE, secretKey);
			decrypter = Cipher.getInstance(transformation);
			decrypter.init(Cipher.DECRYPT_MODE, secretKey);
		} catch (InvalidKeyException e) {
			LOGGER.error(e);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e);
		} catch (InvalidKeySpecException e) {
			LOGGER.error(e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e);
		} catch (NoSuchPaddingException e) {
			LOGGER.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sourcetrace.eses.util.crypto.ICryptoUtil#decrypt(java.lang.String)
	 */
	public String decrypt(String cipherText) {

		String clearText = null;

		try {
			byte[] decrypted = decrypter.doFinal(Hex.decodeHex(cipherText
					.toCharArray()));
			clearText = new String(decrypted, "UTF-8");
		} catch (IllegalBlockSizeException e) {
			LOGGER.error(e);
		} catch (BadPaddingException e) {
			LOGGER.error(e);
		} catch (DecoderException e) {
			LOGGER.error(e);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e);
		}

		return clearText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sourcetrace.eses.util.crypto.ICryptoUtil#encrypt(java.lang.String)
	 */
	public String encrypt(String clearText) {

		String cipherText = null;

		try {
			byte[] input = clearText.getBytes("UTF-8");
			byte[] encrypted = encrypter.doFinal(input);
			cipherText = new String(Hex.encodeHex(encrypted)).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e);
		} catch (BadPaddingException e) {
			LOGGER.error(e);
		} catch (IllegalBlockSizeException e) {
			LOGGER.error(e);
		}

		return cipherText;
	}
	
	@Override
	public String encryptJWT(String cipherText) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = secret_key.getBytes();
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		String jws = Jwts.builder().setSubject(cipherText).signWith(signatureAlgorithm, signingKey).compact();
		return jws;
	}

	@Override
	public String decryptJWT(String cipherText) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = secret_key.getBytes();
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		Jws<Claims> cc = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(cipherText);
		return cc.getBody().getSubject();
	}
}
