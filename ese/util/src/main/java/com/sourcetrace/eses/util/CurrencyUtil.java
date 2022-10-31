/*
 * CurrencyUtil.java
 * Copyright (c) 2008, Source Trace Systems
 * ALL RIGHTS RESERVED
 */
package com.sourcetrace.eses.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

// TODO: Auto-generated Javadoc
/**
 * The Class CurrencyUtil.
 * 
 * @author $Author: antronivan $
 * @version $Rev: 103 $, $Date: 2009-11-20 12:56:27 +0530 (Fri, 20 Nov 2009) $
 */
public class CurrencyUtil {


	// Format the amount by USD
	/**
	 * Format by usd.
	 * 
	 * @param amount
	 *            the amount
	 * 
	 * @return the string
	 */
	public static String formatByUSDcomma(String formatAmount) {

		formatAmount = removeChar(formatAmount, ',');

		return formatAmount;
	}

	public static String removeChar(String s, char c) {
		String r = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != c)
				r += s.charAt(i);
		}
		return r;
	}

	public static String formatByUSD(double amount) {
		Locale locale = Locale.US;
		String formatAmount = NumberFormat.getCurrencyInstance(locale).format(
				amount);
		if (formatAmount != null && formatAmount.length() > 1) {
			StringBuffer s = new StringBuffer(formatAmount);
			s.insert(1, "     ");
			formatAmount = s.toString();
		}
		return formatAmount;
	}

	// Format the amount by NIO
	/**
	 * Format by nio.
	 * 
	 * @param amount
	 *            the amount
	 * 
	 * @return the string
	 */
	public static String formatByNIO(double amount) {
		Locale locale = Locale.US;
		String formatAmount = "C"
				+ NumberFormat.getCurrencyInstance(locale).format(amount);
		if (formatAmount != null && formatAmount.length() > 1) {
			String format = formatAmount.substring(0, 2);
			String namount = formatAmount.substring(2, formatAmount.length());
			formatAmount = format + "     " + namount;
		}
		return formatAmount;
	}

	public static String thousandSeparator(Double value) {
		DecimalFormat decimal = new DecimalFormat("0.00");
		String pattern = "###,###.##";
		decimal.applyPattern(pattern);
		String output = decimal.format(value);

		if (!output.contains(".")) {
			output = output + ".00";
		} else if (output.contains(".")) {
			String val = output.substring(output.lastIndexOf("."), output
					.length());
			if (val.length() == 2) {
				output = output + "0";
			}
		}

		return output;
	}

	public static String currencyFormatter(Double value) {
		DecimalFormat decimal = new DecimalFormat("0.00");
		String pattern = "#.##";
		decimal.applyPattern(pattern);
		String output = decimal.format(value);

		if (!output.contains(".")) {
			output = output + ".00";
		} else if (output.contains(".")) {
			String val = output.substring(output.lastIndexOf("."), output
					.length());
			if (val.length() == 2) {
				output = output + "0";
			}
		}

		return output;
	}
	
	/**
	 * Gets the decimal format.
	 * 
	 * @param value the value
	 * @param pattern the pattern
	 * 
	 * @return the decimal format
	 */
	
	public static String getDecimalFormat(Double value, String pattern){
	//pattern format : ##.000 / ##.00	  
		  DecimalFormat f = new DecimalFormat(pattern);  
		  String output= f.format(value);
		  if(value < 1 && value > -1) {
		   return "0" + output;
		  }
		  return output;
		 }
}
