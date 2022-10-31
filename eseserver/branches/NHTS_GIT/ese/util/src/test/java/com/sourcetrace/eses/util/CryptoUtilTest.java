/*
 * CryptoUtilTest.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.util;

import java.math.BigInteger;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.service.IMailService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author PANNEER
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans-*.xml" })
public class CryptoUtilTest {

	@Autowired
	private ICryptoUtil cryptoUtil;
	@Autowired
	private IMailService mailService;


	private static String secret_key = "STRACE@12345SAKTHIATHISOURCETRACE";

	/**
	 * Test encrypt password.
	 */
	@Test
	public void testEncryptPassword() {
		/*
		 * username : sourcetrace
		 * password : 12345678
		 */
		String encryptPassword = cryptoUtil.encrypt(StringUtil.getMulipleOfEight(
				"MIICCDCCAa+gAwIBAgIJAL7/4dF3k5yiMAoGCCqGSM49BAMCMGExCzAJBgNVBAYTAkNIMQ8wDQYDVQQIDAZHZW5ldmExDzANBgNVBAcMBk1leXJpbjESMBAGA1UECgwJRmlybWVuaWNoMRwwGgYDVQQDDBNhcGltYW5hZ2VyX3Byb2RfZG16MB4XDTE5MDkxMDA4MDgxN1oXDTI5MDcxOTA4MDgxN1owYTELMAkGA1UEBhMCQ0gxDzANBgNVBAgMBkdlbmV2YTEPMA0GA1UEBwwGTWV5cmluMRIwEAYDVQQKDAlGaXJtZW5pY2gxHDAaBgNVBAMME2FwaW1hbmFnZXJfcHJvZF9kbXowWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAATFRaqq8dkXcmyKVH6QneLK3cMBaDjldWdpgeWKonDBgtyKgUwCZti1WUrwbqnj9O4Hn2vU14xhOqX02a8UKBeZo1AwTjAdBgNVHQ4EFgQUVuQyJFq/ENZXW2AbvFQ3SEJIoUAwHwYDVR0jBBgwFoAUVuQyJFq/ENZXW2AbvFQ3SEJIoUAwDAYDVR0TBAUwAwEB/zAKBggqhkjOPQQDAgNHADBEAiApHBfelkgKCz0SNvKCf7vG67xCZIE3hwX5TfxNN14OhQIgUFe2QIPH2NSCKsxSNZYhltzSi4KtA0HJ2k/Guls1i2c="));
		System.out.println("in :" + encryptPassword);
		Assert.notNull(encryptPassword);
	}

	/**
	 * Test decrypt password.
	 */
	@Test
	public void testDecryptPassword() {

		String decryptPassword = cryptoUtil.decrypt(
				"F013751DAD5B4886F9C2DD59A8CCC133BC63AA5EE2716FDC");
		System.out.println("out :" + decryptPassword);
		Assert.notNull(decryptPassword);

	}

	@Test
	public void getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		String str = "Tajmahal";
		byte[] b = str.getBytes();
		sr.nextBytes(b);
		System.out.println(toHex(b));
	}

	public String toHex(byte[] array) throws NoSuchAlgorithmException {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

	@Test
	public void encryptJWT() {
		String cipherText = "testName@1234";
		String cipherText1 = "MingS12345678";
		String cipherText2 = "XueC12345678";
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = secret_key.getBytes();
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		String jws = Jwts.builder().setSubject(cipherText).signWith(signatureAlgorithm, signingKey).compact();
		String jws1 = Jwts.builder().setSubject(cipherText1).signWith(signatureAlgorithm, signingKey).compact();
		String jws2 = Jwts.builder().setSubject(cipherText2).signWith(signatureAlgorithm, signingKey).compact();
		System.out.println("jwt Eddi12345678 " + jws);
		System.out.println("jwt Eddi12345678 " + jws1);
		System.out.println("jwt Eddi12345678 " + jws2);
	}

	@Test
	public void decryptJWT() {
		String cipherText = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbnVmc1Bhc3NAMTIzIn0.AsVw-lhgla0mjgwBbnxRbFM2Wm3FTYn2QvKafL-N3UA";
		try {
			SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
			byte[] apiKeySecretBytes = secret_key.getBytes();
			Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
			Jws<Claims> cc = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(cipherText);

			System.out.println("jwt Decryt " + cc.getBody().getSubject());
		} catch (Exception e) {
			System.out.println("jwt Decryt ");
		}
	}
	@Test
	public void sendExpireMail() {
		String subject = "Test";
		String title = "Test";
		String message ="hello";
		try {
			Map<String, String> mailConfigMap = new HashMap<String, String>();

			mailConfigMap.put(ESESystem.USER_NAME, "nhts@afa.go.ke");
			mailConfigMap.put(ESESystem.PASSWORD, "nhts@hcdhq");
			mailConfigMap.put(ESESystem.MAIL_HOST, "afa.go.ke");
			mailConfigMap.put(ESESystem.PORT, "587");

			mailService.sendSimpleMail("vijayalakshmi@sourcetrace.com", new String[] { "vijayalakshmi@sourcetrace.com" }, subject, message, mailConfigMap);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
