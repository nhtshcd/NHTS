/*
 * StringUtil.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.tools.ant.util.regexp.Regexp;
import org.apache.tools.ant.util.regexp.RegexpUtil;

public class StringUtil extends ObjectUtil {

	private static String CRON_EXPRESS = "0 0 00 * * ?";
	public static final String HYPEN = "-";
	public static final String COMMA = ",";
	public static final String SPACE = " ";
	public static final String NEW_LINE = "\n";
	public static final String SLASH = "/";
	public static final String EMPTY = "";
	public static final String PIPELINE = "|";
	private static final String DIGITS = "0123456789";
	private static final String LOCASE_CHARACTERS = "abcdefghjkmnpqrstuvwxyz";
	private static final String UPCASE_CHARACTERS = "ABCDEFGHJKMNPQRSTUVWXYZ";
	private static final String SYMBOLS = "@#$%=:?";
	private static final String ALL = DIGITS + LOCASE_CHARACTERS + UPCASE_CHARACTERS + SYMBOLS;
	private static final char[] upcaseArray = UPCASE_CHARACTERS.toCharArray();
	private static final char[] locaseArray = LOCASE_CHARACTERS.toCharArray();
	private static final char[] digitsArray = DIGITS.toCharArray();
	private static final char[] symbolsArray = SYMBOLS.toCharArray();
	private static final char[] allArray = ALL.toCharArray();
	private static java.util.Random r = new java.util.Random();
	/**
	 * Trim.
	 *
	 * @param value
	 *            the value
	 * @return the string
	 */
	public static String trim(String value) {

		return (!ObjectUtil.isEmpty(value)) ? value.trim() : "";
	}

	/**
	 * Checks if is empty.
	 *
	 * @param value
	 *            the value
	 * @return true, if is empty
	 */
	public static boolean isEmpty(String value) {

		return !(trim(value).equalsIgnoreCase("null")) && !(trim(value).length() > 0);
	}

	/**
	 * Checks if is equal.
	 *
	 * @param cmp
	 *            the cmp
	 * @param with
	 *            the with
	 * @return true, if is equal
	 */
	public static boolean isEqual(String cmp, String with) {

		return !isEmpty(cmp) && !isEmpty(with) && trim(cmp).equalsIgnoreCase(trim(with));
	}

	/**
	 * Gets the string.
	 *
	 * @param obj
	 *            the obj
	 * @param defValue
	 *            the def value
	 * @return the string
	 */
	public static String getString(Object obj, String defValue) {

		return ObjectUtil.isEmpty(obj) ? defValue : getString(String.valueOf(obj), defValue);
	}

	/**
	 * Gets the string.
	 *
	 * @param value
	 *            the value
	 * @param defValue
	 *            the def value
	 * @return the string
	 */
	public static String getString(String value, String defValue) {

		return StringUtil.isEmpty(value) ? defValue : value.trim();
	}

	/**
	 * Gets the exact.
	 *
	 * @param value
	 *            the value
	 * @param count
	 *            the count
	 * @return the exact
	 */
	public static String getExact(String value, int count) {

		return ((value.length() > count) ? (value.substring(0, count))
				: (getEmptyLength(count - value.length())) + value);
	}

	/**
	 * Gets the exact empty.
	 *
	 * @param value
	 *            the value
	 * @param count
	 *            the count
	 * @return the exact empty
	 */
	public static String getExactEmpty(String value, int count) {

		return ((value.length() > count) ? (value.substring(0, count))
				: value + (getEmptySpace(count - value.length())));
	}

	// Added this to get number field prefixed with zero's - Siva
	/**
	 * Gets the exact number.
	 *
	 * @param value
	 *            the value
	 * @param count
	 *            the count
	 * @return the exact number
	 */
	public static String getExactNumber(String value, int count) {

		return ((value.length() > count) ? (value.substring(0, count))
				: (StringUtil.getEmptyLengthFill(count - value.length()) + value));
	}

	/**
	 * Gets the empty length.
	 *
	 * @param count
	 *            the count
	 * @return the empty length
	 */
	public static String getEmptyLength(int count) {

		StringBuffer data = new StringBuffer();
		for (int i = 0; i < count; i++) {
			data.append("0");
		}
		return data.toString();
	}

	/**
	 * Gets the muliple of eight.
	 *
	 * @param data
	 *            the data
	 * @return the muliple of eight
	 */
	public static String getMulipleOfEight(String data) {

		int mod = data.length() % 8;
		if (mod != 0) {
			int diff = 8 - mod;
			data = data + getEmptyFill(diff);
		}

		return data;
	}

	/**
	 * Gets the empty fill.
	 *
	 * @param count
	 *            the count
	 * @return the empty fill
	 */
	public static String getEmptyFill(int count) {

		StringBuffer data = new StringBuffer();

		for (int i = 0; i < count; i++) {
			data.append("\0");
		}
		return data.toString();
	}

	/**
	 * Gets the empty length fill.
	 *
	 * @param count
	 *            the count
	 * @return the empty length fill
	 */
	public static String getEmptyLengthFill(int count) {

		StringBuffer data = new StringBuffer();

		for (int i = 0; i < count; i++) {
			data.append("0");
		}
		return data.toString();
	}

	/**
	 * Gets the formatted dob.
	 *
	 * @param dateOfBirth
	 *            the date of birth
	 * @return the formatted dob
	 */
	public static String getFormattedDOB(String dateOfBirth) {

		String[] dob = dateOfBirth.split("/");
		StringBuffer sBuf = new StringBuffer();

		if (dob.length == 3) {
			sBuf.append(getExactNumber(dob[1], 2));
			sBuf.append(getExactNumber(dob[0], 2));
			sBuf.append(getExactNumber(dob[2], 4));
			return sBuf.toString();
		}
		return "IncorrectDate";
	}

	/**
	 * Trim collection string.
	 *
	 * @param s
	 *            the s
	 * @return the string
	 */
	public static String trimCollectionString(String s) {

		return s.length() > 2 ? s.substring(1, s.length() - 1) : "";
	}

	/**
	 * Gets the exact length.
	 *
	 * @param value
	 *            the value
	 * @param count
	 *            the count
	 * @return the exact length
	 */
	public static String getExactLength(String value, int count) {

		return ((value.length() > count) ? (value.substring(0, count))
				: (value.trim() + StringUtil.getZeroAppend(count - value.length()).trim()));
	}

	/**
	 * Gets the zero append.
	 *
	 * @param count
	 *            the count
	 * @return the zero append
	 */
	public static String getZeroAppend(int count) {

		StringBuffer data = new StringBuffer();
		for (int i = 0; i < count; i++) {
			data.append("0");
		}
		return data.toString();
	}

	/**
	 * Remove white space.
	 *
	 * @param value
	 *            the value
	 * @return the string
	 */
	public static String removeWhiteSpace(String value) {

		value = (!ObjectUtil.isEmpty(value)) ? value.trim() : "";
		value = value.replaceAll("\\s+", "");
		return value;
	}

	/**
	 * Checks if is valid filter value.
	 *
	 * @param value
	 *            the value
	 * @return true, if is valid filter value
	 */
	public static boolean isValidFilterValue(String value) {

		boolean retrunValue = true;
		retrunValue = (StringUtil.isEmpty(value)) ? false
				: !(("0".equals(value) || "all".equalsIgnoreCase(value) || "todo".equalsIgnoreCase(value)));
		return retrunValue;
	}

	/**
	 * Gets the cron expression.
	 *
	 * @param string
	 *            the string
	 * @return the cron expression
	 */
	public static String getCronExpression(String string) {

		if (StringUtil.isEmpty(string)) {
			return CRON_EXPRESS;
		} else {
			String[] values = string.split(":");
			return values[2] + " " + values[1] + " " + values[0] + " ? * *";
		}
	}

	/**
	 * Remove duplicate.
	 *
	 * @param values
	 *            the values
	 */
	public static void removeDuplicate(Collection<?> values) {

		HashSet h = new HashSet(values);
		values.clear();
		values.addAll(h);
	}

	/**
	 * Gets the random number.
	 *
	 * @return the random number
	 */
	public static String getRandomNumber() {

		int aStart = 100000;
		int aEnd = 999999;
		Random aRandom = new Random();
		// get the range, casting to long to avoid overflow problems
		long range = (long) aEnd - (long) aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * aRandom.nextDouble());
		String randomNumber = String.valueOf(fraction + aStart);

		return new String(randomNumber);
	}

	/**
	 * Trim list.
	 *
	 * @param list
	 *            the list
	 * @return the string
	 */
	public static String trimList(List list) {

		if (!ObjectUtil.isListEmpty(list)) {
			String output = list.toString().replace("[", " ").replace("]", "").trim();
			if (output.equals("") || output.equals(" ") || (output == null) || output.equalsIgnoreCase("null")) {
				return "";
			} else {
				return output;
			}
		} else {
			return "";
		}
	}

	/**
	 * Gets the empty space.
	 *
	 * @param count
	 *            the count
	 * @return the empty space
	 */
	public static String getEmptySpace(int count) {

		StringBuffer data = new StringBuffer();
		for (int i = 0; i < count; i++) {
			data.append(" ");
		}
		return data.toString();
	}

	/**
	 * Format.
	 *
	 * @param object
	 *            the object
	 * @return the string
	 */
	public static String format(Object object) {

		if (object instanceof Double) {
			double data = Double.parseDouble(object.toString());
			double power = (double) Math.pow(10, 2);
			data = data * power;
			double tmp = Math.round(data);
			double finalValue = (double) tmp / power;
			NumberFormat formatter = new DecimalFormat("#0.00");
			return formatter.format(finalValue);
		} else if (object instanceof Date) {
			Date tempDate = (Date) object;
			return DateUtil.getTransactionDateTime(tempDate);
		} else if (object instanceof String) {
			double d;
			String data = object.toString();
			try {
				d = Double.parseDouble(data);
			} catch (Exception e) {
				return data;
			}

			return BigDecimal.valueOf(d).toPlainString();
		} else {
			return StringUtil.trim(ObjectUtil.isEmpty(object) ? EMPTY : String.valueOf(object));
		}
	}

	/**
	 * Gets the cron expression for monthly.
	 *
	 * @param string
	 *            the string
	 * @return the cron expression for monthly
	 */
	public static String getCronExpressionForMonthly(String string) {

		if (StringUtil.isEmpty(string)) {
			return CRON_EXPRESS;
		} else {
			String[] values = string.split(":");
			return values[2] + " " + values[1] + " " + values[0] + " L * ?";
		}
	}

	/**
	 * To upper first character.
	 *
	 * @param value
	 *            the value
	 * @return the string
	 */
	public static String toUpperFirstCharacter(String value) {

		String subString = value.substring(1);
		char firstChar = value.charAt(0);
		String upper = Character.toString(firstChar).toUpperCase();
		return upper + subString;
	}

	/**
	 * Generate password.
	 *
	 * @return the string
	 */
	public static String generatePassword() {

		return RandomStringUtils.random(8, true, true);

	}

	/**
	 * @param value
	 * @param count
	 * @return
	 */
	public static String appendZeroPrefix(String value, int count) {

		if (isEmpty(value)) {
			return getEmptySpace(count - 0);
		} else {
			return ((value.length() > count) ? (value.substring(0, count))
					: (getEmptyLengthFill(count - value.length())) + value);
		}
	}

	public static List<String> split(String string, String seperater) {

		if (StringUtil.isEmpty(string)) {
			return new ArrayList<String>();
		}
		List<String> list = new ArrayList<String>(Arrays.asList(string.trim().split(seperater)));
		return list;
	}

	public static String removeLastComma(String str) {

		str = str.replaceAll(", $", "");
		return str;
	}

	public static boolean isEmail(String emailAddress) {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return emailAddress.matches(EMAIL_REGEX);
	}

	public static String sortCommaString(String commaString) {
		List<String> strList = new ArrayList<String>(Arrays.asList(commaString.trim().split(",")));
		Collections.sort(strList);
		commaString = "";
		for (String string : strList) {
			commaString += string + ",";
		}
		return removeLastComma(commaString);
	}

	public static List<String> splitIntoList(String string, String seperater) {
		String[] arr = string.trim().split(seperater);
		List<String> list = new ArrayList<String>(Arrays.asList(arr));
		return list;
	}

	public static String removeLastChar(String str, char c) {
		str = str.replaceAll(c + "$", "");
		return str;
	}

	public static String removeLastString(String str, String string) {
		str = str.replaceAll(string + "$", "");
		return str;
	}

	public static String substringLast(String str, String separator) {
		if (isEmpty(str) || isEmpty(separator)) {
			return str;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(pos + 1);
	}

	public static String substringBeforeLast(String str, String separator) {
		if (isEmpty(str) || isEmpty(separator)) {
			return str;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String stringArrayToString(String[] arr, String seperater) {
		String result = "";
		for (String string : arr) {
			result += (string + seperater);
		}
		return removeLastString(result, seperater);
	}

	public static boolean isCommaStringContain(String srcValue, String value) {
		String[] arr = srcValue.trim().split(",");
		List<String> list = new ArrayList<String>(Arrays.asList(arr));
		return list.contains(value);
	}

	public static String toBase64String(byte[] byteArray) {

		if (byteArray == null) {
			byteArray = new byte[] {};
		}
		String base64String = DatatypeConverter.printBase64Binary(byteArray);
		return base64String;
		// return new sun.misc.BASE64Encoder().encode(byteArrzay);
	}

	public static byte[] base64StringToByteArray(String base64String) throws IOException {

		// return new sun.misc.BASE64Decoder().decodeBuffer(base64String);
		byte[] bytearray = DatatypeConverter.parseBase64Binary(base64String);
		return bytearray;
	}

	public static String listToString(List<String> list, String seperater) {
		String result = "";
		for (String string : list) {
			result += (string + seperater);
		}
		return removeLastString(result, seperater);
	}

	public static String trimIncludingNonbreakingSpace(String s) {
		if(!isEmpty(s)){
			return s.replaceFirst("^[\\x00-\\x200\\xA0]+", "").replaceFirst("[\\x00-\\x20\\xA0]+$", "");
		}
		return null;
	}
	
	public static String generateStrPassword(Integer MAX_LENGTH) {
	    StringBuilder sb = new StringBuilder();
	 
	    // get at least one lowercase letter
	    sb.append(locaseArray[r.nextInt(locaseArray.length)]);
	 
	    // get at least one uppercase letter
	    sb.append(upcaseArray[r.nextInt(upcaseArray.length)]);
	 
	    // get at least one digit
	    sb.append(digitsArray[r.nextInt(digitsArray.length)]);
	 
	    // get at least one symbol
	    sb.append(symbolsArray[r.nextInt(symbolsArray.length)]);
	 
	    // fill in remaining with random letters
	    for (int i = 0; i < MAX_LENGTH - 4; i++) {
	        sb.append(allArray[r.nextInt(allArray.length)]);
	    }
	 
	    return sb.toString();
	}

	public static String replaceCaseInsensitive(String input,String from, String to){
		return  input!=null ? input.replaceFirst("(?i)"+from, to) :"";
	}
}
