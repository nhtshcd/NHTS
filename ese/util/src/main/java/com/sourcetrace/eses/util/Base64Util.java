package com.sourcetrace.eses.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class Base64Util {

	private static final Logger logger = Logger.getLogger(Base64Util.class
			.getName());

	public static void main(String args[]) {
		String value = "Code for encode / decode byte array to base64";
		byte[] rawData = value.getBytes();

		logger.info("Given    : " + value);
		logger.info("Encoder  : " + encoder(rawData));
		logger.info("Decoder  : " + decoder(rawData));
		System.out.println("Given    : " + value);
		System.out.println("Encoder  : " + encoder(rawData));
		System.out.println("Decoder  : " + decoder(encoder(rawData).getBytes()));
	}

	public static String encoder(byte[] data) {
		byte[] encoded = Base64.encodeBase64(data);
		return new String(encoded);
	}

	public static String decoder(byte[] data) {
		byte[] decoded = Base64.decodeBase64(data);
		return new String(decoded);
	}

}
