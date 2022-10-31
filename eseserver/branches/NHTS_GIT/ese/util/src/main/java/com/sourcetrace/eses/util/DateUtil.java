/*
 * DateUtil.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.Period;

// TODO: Auto-generated Javadoc
public class DateUtil {

	private static Calendar lastCal = Calendar.getInstance();
	public static final String DATE_FORMAT = "MM/dd/yyyy";
	public static final String DATE_FORMAT_1 = "dd/MM/yyyy";
	public static final String DATE_FORMAT_2 = "dd-MM-yyyy";
	public static final String DATE_FORMAT_4 = "dd-MM-yyyy HH:mm:ss";
	public static final String CARD_DATE_FORMAT = "MMddyyyy";
	public static final String DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";
	public static final String DATE_TIME = "dd-MM-yyyy hh:mm:ss";
	public static final String DEVICE_DATE_TIME = "dd-MM-yyyy kk:mm:ss";
	public static final String SYNC_DATE_TIME = "yyyyMMddHHmmss";
	public static final String DATE = "yyyy-MM-dd";
	public static final String DATE_TIME_REPORT_FORMAT = "dd-MM-yyyy hh:mm:ss a";
	public static final String TXN_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String TRANSACTION_FORMAT = "dd-MM-yyyy kk:mm:ss";
	public static final String REVISION_NO_DATE_TIME_FORMAT = "yyyyMMddHHmmss";
	public static final String REVISION_NO_DATE_TIME_MILLISEC_FORMAT = "yyyyMMddHHmmssS";
	public static final String REVISION_NO_DATE_TIME_MILLISEC_FORMATS = "yyyyMMddHHmmssSSSS";
	public static final String DATE_FORMAT_3 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	public static final String FILE_DOWNLOAD_DATE_FORMAT = "yyyyMMddHHmmss";
	public static final String TXN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static String WEB_DATE_FORMAT;
	public static String WEB_DATE_TIME_FORMAT;
	public static String DATABASE_DATE_FORMAT = "yyyy-MM-dd";
	public static String DATABASE_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	private static Calendar calendar = new GregorianCalendar();
	public static final String FARMER_DOB = "yyyyMMdd";
	public static final String PROFILE_DATE_FORMAT = "dd/MM/yyyy";
	public static final String HARVEST_SEASON_DATE_FORMAT = "MM/yyyy";
	public static final String TXN_DATE_DISPLAY_FORMAT = "DD/MM/YYYY";
	public static final String DATE_FORMAT_DDMMYYYY = "ddMMyyyy";
	public static final String DATE_FORMAT_DDMMYY = "ddMMyy";
	public static final String WEATHER_DATE_FORMAT = "MMddyy";
	public static final String WEATHER_DATE_FORMAT_TIME ="EEE, d MMM yyyy HH:mm:ss Z";
	public static final String BALNACEFORMAT ="yyyy/MM/dd";
	public static final String STD_DATE_TIME_FORMAT="dd/MM/yyyy hh:mm:ss a";

	public static String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public static String getDate() {

		Date date = new Date();
		DateFormat dFormat = new SimpleDateFormat("MMddyyyyHHmmss");
		return dFormat.format(date);
	}

	/**
	 * Gets the audit date time.
	 *
	 * @return the audit date time
	 */
	public static String getAuditDateTime() {

		Date date = new Date();
		DateFormat dFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		return dFormat.format(date);
	}

	/**
	 * Gets the date time.
	 *
	 * @return the date time
	 */
	public static String getDateTime() {

		Date date = new Date();
		DateFormat dFormat = new SimpleDateFormat(DATE_TIME);
		return dFormat.format(date);
	}

	/**
	 * Gets the format date.
	 *
	 * @param date
	 *            the date
	 * @return the format date
	 */
	public static String getFormatDate(Date date) {

		DateFormat dFormat = new SimpleDateFormat(CARD_DATE_FORMAT);
		return dFormat.format(date);
	}

	/**
	 * Gets the audit date time.
	 *
	 * @param date
	 *            the date
	 * @return the audit date time
	 */
	public static String getAuditDateTime(Date date) {

		DateFormat dFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		return dFormat.format(date);
	}

	/**
	 * Gets the date in format.
	 *
	 * @param pattern
	 *            the pattern
	 * @return the date in format
	 */
	public static String getDateInFormat(String pattern) {

		Date date = new Date();
		DateFormat dFormat = new SimpleDateFormat(pattern);
		return dFormat.format(date);
	}

	/**
	 * Gets the date in format.
	 *
	 * @param pattern
	 *            the pattern
	 * @param date
	 *            the date
	 * @return the date in format
	 */
	public static String getDateInFormat(String pattern, Date date) {

		DateFormat dFormat = new SimpleDateFormat(pattern);
		String dateStr = dFormat.format(date);
		return dateStr;
	}

	/**
	 * Gets the banex cbs date format.
	 *
	 * @param pattern
	 *            the pattern
	 * @return the banex cbs date format
	 */
	public static String getBanexCBSDateFormat(String pattern) {

		Date date = new Date();
		DateFormat dFormat = new SimpleDateFormat(pattern);
		String dateStr = dFormat.format(date);
		dateStr = dateStr.substring(0, dateStr.length() - 2) + ":" + dateStr.substring(dateStr.length() - 2);
		return dateStr;
	}

	/**
	 * Gets the unique date.
	 *
	 * @return the unique date
	 */
	public static String getUniqueDate() {

		int sec = lastCal.get(Calendar.SECOND);
		lastCal.set(Calendar.SECOND, ++sec);
		DateFormat dFormat = new SimpleDateFormat("MMddyyyyHHmmss");
		return dFormat.format(lastCal.getTime());
	}

	/**
	 * Parses the date.
	 *
	 * @param dateStr
	 *            the date str
	 * @return the date
	 */
	public static Date parseDate(String dateStr) {

		DateFormat dFormat = new SimpleDateFormat("MMddyyyyHHmmss");
		Date date = null;
		try {
			date = dFormat.parse(dateStr);
		} catch (ParseException e) {
			// throw new ESEException(ESEException.INVALID_DEV_TIME);
		}

		return date;
	}

	/**
	 * Checks if is today.
	 *
	 * @param date
	 *            the date
	 * @return true, if is today
	 */
	public static boolean isToday(Date date) {

		boolean today = false;
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(date);
		Calendar todayDate = Calendar.getInstance();
		todayDate.setTime(new Date());

		today = startDate.get(Calendar.DATE) == todayDate.get(Calendar.DATE)
				&& startDate.get(Calendar.MONTH) == todayDate.get(Calendar.MONTH)
				&& startDate.get(Calendar.YEAR) == todayDate.get(Calendar.YEAR);

		return today;
	}

	/**
	 * Gets the last month start date.
	 *
	 * @return the last month start date
	 */
	public static Date getLastMonthStartDate() {

		Calendar date = Calendar.getInstance();

		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH);

		if (month == 0) {
			month = 11;
			year = year - 1;
		} else {
			month = month - 1;
		}

		Calendar cal = new GregorianCalendar(year, month, 1);
		Date startDate = cal.getTime();

		return startDate;
	}

	/**
	 * Gets the last month end date.
	 *
	 * @return the last month end date
	 */
	public static Date getLastMonthEndDate() {

		Calendar date = Calendar.getInstance();

		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH);
		System.out.println(year + ", " + month);

		if (month == 0) {
			month = 11;
			year = year - 1;
		} else {
			month = month - 1;
		}

		Calendar cal = new GregorianCalendar(year, month, 1);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DATE, days);
		Date endDate = cal.getTime();

		return endDate;
	}

	/**
	 * Convert string to date.
	 *
	 * @param inputDate
	 *            the input date
	 * @param dateFormat
	 *            the date format
	 * @return the date
	 */
	public static Date convertStringToNewDate(String inputDate, final String newFormat) {

		final DateFormat newFormat1 = new SimpleDateFormat(HARVEST_SEASON_DATE_FORMAT);
		Date outputDate = null;
		String newString = null;
		if (inputDate != null) {
			outputDate = newFormat1.parse(inputDate, new ParsePosition(0));
			newString = convertDateToString(outputDate, newFormat);

			if (!newString.equals(inputDate)) {
				IllegalArgumentException exception = new IllegalArgumentException(
						"Data in string " + "is not a valid date.");

				throw exception;
			}

		}

		return outputDate;

	}

	public static Date convertStringToDate(String inputDate, final String dateFormat) {

		final SimpleDateFormat realDateFormat = new SimpleDateFormat(dateFormat);
		Date outputDate = null;
		String newString = null;

		if (inputDate != null) {
			outputDate = realDateFormat.parse(inputDate, new ParsePosition(0));
			newString = convertDateToString(outputDate, dateFormat);

			if (!newString.equals(inputDate)) {
				IllegalArgumentException exception = new IllegalArgumentException(
						"Data in string " + "is not a valid date.");

				throw exception;
			}
		}

		return outputDate;
	}

	/**
	 * Convert date to string.
	 *
	 * @param inputDate
	 *            the input date
	 * @param dateFormat
	 *            the date format
	 * @return the string
	 */
	public static String convertDateToString(final Date inputDate, final String dateFormat) {

		final SimpleDateFormat realDateFormat = new SimpleDateFormat(dateFormat);
		String outputDate = null;

		if (inputDate != null) {
			outputDate = realDateFormat.format(inputDate);
		}

		return outputDate;
	}

	/**
	 * Plus day.
	 *
	 * @param inputDate
	 *            the input date
	 * @param count
	 *            the count
	 * @return the string
	 */
	public static String plusDay(String inputDate, int count) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String outputDate = null;
		DateTime dt = new DateTime(df.format(DateUtil.convertStringToDate(inputDate, WEB_DATE_FORMAT)));
		DateTime plusPeriod = dt.plus(Period.days(count));
		outputDate = plusPeriod.toString(WEB_DATE_FORMAT);
		return outputDate;

	}

	/**
	 * Plus week.
	 *
	 * @param inputDate
	 *            the input date
	 * @param count
	 *            the count
	 * @return the string
	 */
	public static String plusWeek(String inputDate, int count) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String outputDate = null;
		DateTime dt = new DateTime(df.format(DateUtil.convertStringToDate(inputDate, WEB_DATE_FORMAT)));
		DateTime plusPeriod = dt.plus(Period.weeks(count));
		outputDate = plusPeriod.toString(WEB_DATE_FORMAT);
		return outputDate;

	}

	/**
	 * Plus month.
	 *
	 * @param inputDate
	 *            the input date
	 * @param count
	 *            the count
	 * @return the string
	 */
	public static String plusMonth(String inputDate, int count) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String outputDate = null;
		DateTime dt = new DateTime(df.format(DateUtil.convertStringToDate(inputDate, DATE_FORMAT)));
		DateTime plusPeriod = dt.plus(Period.months(count));
		outputDate = plusPeriod.toString(WEB_DATE_FORMAT);
		return outputDate;

	}

	/**
	 * Plus month.
	 *
	 * @param inputDate
	 *            the input date
	 * @param count
	 *            the count
	 * @param format
	 *            the format
	 * @return the date
	 */
	public static Date plusMonth(Date inputDate, int count, String format) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateTime dt = new DateTime(df.format(inputDate));
		DateTime plusPeriod = dt.plus(Period.months(count));
		return convertStringToDate(plusPeriod.toString(format), format);
	}

	/**
	 * Plus day.
	 *
	 * @param inputDate
	 *            the input date
	 * @param count
	 *            the count
	 * @param format
	 *            the format
	 * @return the string
	 */
	public static String plusDay(String inputDate, int count, String format) {

		DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);
		String outputDate = null;
		DateTime dt = new DateTime(df.format(DateUtil.convertStringToDate(inputDate, format)));
		DateTime plusPeriod = dt.plus(Period.days(count));
		outputDate = plusPeriod.toString(format);
		return outputDate;

	}

	/**
	 * Plus week.
	 *
	 * @param inputDate
	 *            the input date
	 * @param count
	 *            the count
	 * @param format
	 *            the format
	 * @return the string
	 */
	public static String plusWeek(String inputDate, int count, String format) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String outputDate = null;
		DateTime dt = new DateTime(df.format(DateUtil.convertStringToDate(inputDate, format)));
		DateTime plusPeriod = dt.plus(Period.weeks(count));
		outputDate = plusPeriod.toString(format);
		return outputDate;

	}

	/**
	 * Plus month.
	 *
	 * @param inputDate
	 *            the input date
	 * @param count
	 *            the count
	 * @param format
	 *            the format
	 * @return the string
	 */
	public static String plusMonth(String inputDate, int count, String format) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String outputDate = null;
		DateTime dt = new DateTime(df.format(DateUtil.convertStringToDate(inputDate, format)));
		DateTime plusPeriod = dt.plus(Period.months(count));
		outputDate = plusPeriod.toString(format);
		return outputDate;

	}

	/**
	 * Minus day.
	 *
	 * @param inputDate
	 *            the input date
	 * @param count
	 *            the count
	 * @param format
	 *            the format
	 * @return the string
	 */
	public static String minusDay(String inputDate, int count, String format) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String outputDate = null;
		DateTime dt = new DateTime(df.format(DateUtil.convertStringToDate(inputDate, format)));
		DateTime plusPeriod = dt.minus(Period.days(count));
		outputDate = plusPeriod.toString(format);
		return outputDate;

	}

	/**
	 * Minus week.
	 *
	 * @param inputDate
	 *            the input date
	 * @param count
	 *            the count
	 * @param format
	 *            the format
	 * @return the string
	 */
	public static String minusWeek(String inputDate, int count, String format) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String outputDate = null;
		DateTime dt = new DateTime(df.format(DateUtil.convertStringToDate(inputDate, format)));
		DateTime plusPeriod = dt.minus(Period.weeks(count));
		outputDate = plusPeriod.toString(format);
		return outputDate;

	}

	/**
	 * Minus month.
	 *
	 * @param inputDate
	 *            the input date
	 * @param count
	 *            the count
	 * @param format
	 *            the format
	 * @return the string
	 */
	public static String minusMonth(String inputDate, int count, String format) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String outputDate = null;
		DateTime dt = new DateTime(df.format(DateUtil.convertStringToDate(inputDate, format)));
		DateTime plusPeriod = dt.minus(Period.months(count));
		outputDate = plusPeriod.toString(format);
		return outputDate;

	}

	/**
	 * Minus day.
	 *
	 * @param inputDate
	 *            the input date
	 * @param count
	 *            the count
	 * @return the date
	 */
	public static Date minusDay(String inputDate, int count) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String outputDate = null;
		DateTime dt = new DateTime(df.format(DateUtil.convertStringToDate(inputDate, DATE_FORMAT)));
		DateTime plusPeriod = dt.minus(count);
		outputDate = plusPeriod.toString(DATE_FORMAT);
		return convertStringToDate(outputDate, DATE_FORMAT);

	}

	/**
	 * Checks if is valid range.
	 *
	 * @param beginDate
	 *            the begin date
	 * @param lastDate
	 *            the last date
	 * @return true, if is valid range
	 * @throws ParseException
	 *             the parse exception
	 */
	public static boolean isValidRange(Date beginDate, Date lastDate) throws ParseException {

		boolean returnValue = true;
		Date currentDate = new Date();
		if (beginDate.after(currentDate)) {
			returnValue = false;
		}

		if (lastDate.after(currentDate)) {
			returnValue = false;
		}

		if (beginDate.compareTo(lastDate) > 0) {
			returnValue = false;
		}

		return returnValue;
	}

	/**
	 * Checks if is valid range.
	 *
	 * @param beginDate
	 *            the begin date
	 * @return true, if is valid range
	 * @throws ParseException
	 *             the parse exception
	 */
	public static boolean isValidRange(Date beginDate) throws ParseException {

		boolean returnValue = true;
		Date currentDate = new Date();
		if (beginDate.after(currentDate)) {
			returnValue = false;
		}

		if (beginDate.compareTo(currentDate) > 0) {
			returnValue = false;
		}

		return returnValue;
	}

	/**
	 * Checks if is valid birth date.
	 *
	 * @param inputDate
	 *            the input date
	 * @param count
	 *            the count
	 * @return true, if is valid birth date
	 */
	public static boolean isValidBirthDate(Date inputDate, int count) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateTime dt = new DateTime(df.format(inputDate));

		DateTime plusPeriod = dt.plusYears(count);
		DateTime today = new DateTime();
		if (today.getYear() >= plusPeriod.getYear()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Gets the calendar.
	 *
	 * @return the calendar
	 */
	public static Calendar getCalendar() {

		Calendar cal = (Calendar) calendar.clone();
		cal.setTimeInMillis(System.currentTimeMillis());
		return cal;
	}

	/**
	 * Gets the calendar no time.
	 *
	 * @return the calendar no time
	 */
	public static Calendar getCalendarNoTime() {

		Calendar cal = getCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	/**
	 * Gets the next date.
	 *
	 * @param date
	 *            the date
	 * @return the next date
	 */
	public static Date getNextDate(Date date) {

		if (!ObjectUtil.isEmpty(date)) {
			Calendar cal = getCalendar();
			cal.setTimeInMillis(getDateWithoutTime(date).getTime());
			cal.add(Calendar.DATE, 1);
			return cal.getTime();
		} else {
			return date;
		}
	}

	/**
	 * Gets the date without time.
	 *
	 * @param date
	 *            the date
	 * @return the date without time
	 */
	public static Date getDateWithoutTime(Date date) {

		if (!ObjectUtil.isEmpty(date)) {
			Calendar cal = getCalendar();
			cal.setTimeInMillis(date.getTime());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTime();
		} else {
			return date;
		}
	}

	/**
	 * Gets the date with last minuteof day.
	 *
	 * @param date
	 *            the date
	 * @return the date with last minuteof day
	 */
	public static Date getDateWithLastMinuteofDay(Date date) {

		if (!ObjectUtil.isEmpty(date)) {
			Calendar cal = getCalendar();
			cal.setTimeInMillis(date.getTime());
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 59);
			return cal.getTime();
		} else {
			return date;
		}
	}

	/**
	 * Gets the months.
	 *
	 * @return the months
	 */
	public static Map<Integer, String> getMonths() {

		Map<Integer, String> months = new LinkedHashMap<Integer, String>();
		months.put(-1, "Select");
		months.put(0, "jan");
		months.put(1, "feb");
		months.put(2, "mar");
		months.put(3, "apr");
		months.put(4, "may");
		months.put(5, "jun");
		months.put(6, "jul");
		months.put(7, "aug");
		months.put(8, "sep");
		months.put(9, "oct");
		months.put(10, "nov");
		months.put(11, "dec");
		return months;
	}

	/**
	 * Gets the first date of month.
	 *
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @return the first date of month
	 */
	public static Date getFirstDateOfMonth(int year, int month) {

		Calendar cal = getCalendar();
		cal.set(year, month, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}
	
	
	public static Date getdateBeforeOneMonth(int year, int month,int date) {
        if (month==1){
        	year=year-1;
        	month=13;
        }
		Calendar cal = getCalendar();
		cal.set(year, month-1, date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	/**
	 * Gets the first date of month with time.
	 *
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @param hours
	 *            the hours
	 * @return the first date of month with time
	 */
	public static Date getFirstDateOfMonthWithTime(int year, int month, int hours) {

		Calendar cal = getCalendar();
		cal.set(year, month, 1);
		cal.set(Calendar.HOUR_OF_DAY, hours);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	/**
	 * Gets the last date of month.
	 *
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @return the last date of month
	 */
	public static Date getLastDateOfMonth(int year, int month) {

		Calendar cal = getCalendar();
		cal.set(year, month, 1);
		int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(year, month, day);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return cal.getTime();
	}

	/**
	 * Gets the last date of month with time.
	 *
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @param hours
	 *            the hours
	 * @return the last date of month with time
	 */
	public static Date getLastDateOfMonthWithTime(int year, int month, int hours) {

		Calendar cal = getCalendar();
		int calMonth = month + 1;
		cal.set(calMonth == 12 ? year + 1 : year, calMonth == 12 ? 0 : month, 1);
		if (calMonth != 12) {
			int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set(year, month, day);
		}
		cal.set(Calendar.HOUR_OF_DAY, hours - 1);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return cal.getTime();
	}

	/**
	 * Gets the next sconds of date.
	 *
	 * @param date
	 *            the date
	 * @return the next sconds of date
	 */
	public static Date getNextScondsOfDate(Date date) {

		Calendar cal = getCalendar();
		cal.setTime(date);
		int seconds = cal.get(Calendar.SECOND);
		int minutes = cal.get(Calendar.MINUTE);
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		if (seconds < 59) {
			cal.set(Calendar.SECOND, (seconds + 1));
		} else if (minutes < 59) {
			cal.set(Calendar.MINUTE, (minutes + 1));
		} else if (hours < 23) {
			cal.set(Calendar.HOUR_OF_DAY, (hours + 1));
		} else {
			cal.set(Calendar.SECOND, (seconds + 1));
			cal.set(Calendar.MINUTE, (minutes + 1));
			cal.set(Calendar.HOUR_OF_DAY, (hours + 1));
		}

		return cal.getTime();
	}

	/**
	 * Gets the transaction date time.
	 *
	 * @param tempDate
	 *            the temp date
	 * @return the transaction date time
	 */
	public static String getTransactionDateTime(Date tempDate) {

		DateFormat dFormat = new SimpleDateFormat(TRANSACTION_FORMAT);
		return dFormat.format(tempDate);
	}

	/**
	 * Gets the transaction date.
	 *
	 * @param date
	 *            the date
	 * @return the transaction date
	 */
	public static Date getTransactionDate(String date) {

		DateFormat df = new SimpleDateFormat(TXN_TIME_FORMAT);
		// If we set lenient to true, then parse will not throw exception for
		// "2013-10-55 09:12:20"
		// format.
		// So to do the strict validation set lenient as false.
		df.setLenient(false);
		Date txnDate = null;
		try {
			txnDate = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return txnDate;
	}

	/**
	 * Gets the current month.
	 *
	 * @return the current month
	 */
	public static int getCurrentMonth() {

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.MONTH);
	}

	/**
	 * Gets the current year.
	 *
	 * @return the current year
	 */
	public static int getCurrentYear() {

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.YEAR);
	}

	/**
	 * Gets the revision number.
	 *
	 * @return the revision number
	 */
	public static long getRevisionNumber() {

		DateFormat dateFormat = new SimpleDateFormat(REVISION_NO_DATE_TIME_FORMAT);
		String date = dateFormat.format(new Date());
		return Long.valueOf(date);
	}
	
	public static String getDateTimWithMin() {

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String date = dateFormat.format(new Date());
		return date;
	}
	
	public static String getDateTimWithMinsec() {

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = dateFormat.format(new Date());
		return date;
	}

	/**
	 * Gets the revision no date time milli sec.
	 *
	 * @return the revision no date time milli sec
	 */
	public static long getRevisionNoDateTimeMilliSec() {

		DateFormat dateFormat = new SimpleDateFormat(REVISION_NO_DATE_TIME_MILLISEC_FORMAT);
		String date = dateFormat.format(new Date());
		return Long.valueOf(date);
	}

	public static boolean isValidDate(String inDate, String format) {

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	public static String convertDateFormat(String dateString, String fromFarmat, String toFarmat) {
		String result = "";
		try {
			DateFormat fDateFormat = new SimpleDateFormat(fromFarmat);
			DateFormat tDateFormat = new SimpleDateFormat(toFarmat);
			Date date = fDateFormat.parse(dateString);
			result = tDateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean isValidDateFormat(String format, String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
		}
		return date != null;
	}

	public static int noOfDaysInYear(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		Calendar cal = new GregorianCalendar(Integer.parseInt(dateFormat.format(date)), 0, 31);
		return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
	}

	public static List<Date> listDatesBwTwoDates(Date startdate, Date enddate) {
		List<Date> dates = new ArrayList<Date>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startdate);

		while (calendar.getTime().before(enddate)) {
			Date result = calendar.getTime();
			dates.add(result);
			calendar.add(Calendar.DATE, 1);
		}
		return dates;
	}

	public static int getCurrent(int param) {

		Calendar now = Calendar.getInstance();
		int value = now.get(param);
		return value;
	}

	public static Date getFirstDateOfYear() {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		System.out.println("cal new: " + cal.getTime());
		return cal.getTime();
	}

	public static Map<String, String> getYearsList(int startingYear) {

		Map<String, String> yearMap = new LinkedHashMap<String, String>();
		Calendar cal = getCalendar();
		int currentYear = cal.get(Calendar.YEAR);
		for (int i = currentYear; i >= startingYear; i--) {
			yearMap.put(String.valueOf(i), String.valueOf(i));
		}
		return yearMap;

	}

	public static Date getFirstDateOfMonth() {
		Calendar c = Calendar.getInstance(); // this takes current date
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime(); // this returns java.util.Date
	}
	
	public static Date parseTxnDate(String dateStr) {

		DateFormat dFormat = new SimpleDateFormat(TXN_TIME_FORMAT);
		Date date = null;
		try {
			date = dFormat.parse(dateStr);
		} catch (ParseException e) {
			// throw new ESEException(ESEException.INVALID_DEV_TIME);
		}

		return date;
	}
	
	public static int daysBetween2Date(Date d1, Date d2)
	{
	      return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
	
	public static Date removeDateDotZero(Date inputDate)
	{
		String replaceDot=inputDate.toString().replace(".0", "");
		return DateUtil.convertStringToDate(replaceDot, DateUtil.TXN_DATE_TIME);
		
	}

	public static Date setTimeToDate(Date date) {

		if (!ObjectUtil.isEmpty(date)) {
			Calendar cal = getCalendar();
			cal.setTime(date);
			cal.setTimeInMillis(date.getTime());
		
			return cal.getTime();
		} else {
			return date;
		}
	}
	public static Map<String, String> getYearsListByLimit(int limit) {

		Map<String, String> yearMap = new LinkedHashMap<String, String>();
		Calendar cal = getCalendar();
		int currentYear = cal.get(Calendar.YEAR);
		for (int j = 0; j < limit; j++) {
			yearMap.put(String.valueOf(currentYear), String.valueOf(currentYear));
			currentYear--;
		}
		return yearMap;

	}
	

	public static Map<String, String> getCombinedYearsListByLimit(int limit) {

		Map<String, String> yearMap = new LinkedHashMap<String, String>();
		Calendar cal = getCalendar();
		int currentYear = cal.get(Calendar.YEAR);
		for (int j = 0; j < limit; j++) {
			yearMap.put(String.valueOf(currentYear + "-" + (currentYear + 1)), String.valueOf(currentYear + "-" + (currentYear + 1)));
			currentYear--;
		}
		return yearMap;

	}
	
	public static String getRevisionNoWithMillSec() {

		DateFormat dateFormat = new SimpleDateFormat(REVISION_NO_DATE_TIME_MILLISEC_FORMAT);
		String date = dateFormat.format(new Date());
		return date;
	}

	public static String getDateByDateTime(Date date) {
		if (!ObjectUtil.isEmpty(date)) {
			Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
			calendar.setTime(date);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			return String.valueOf(day);
		}
		return null;

	}
	
	public static String getMonthByDateTime(Date date) {
		if (!ObjectUtil.isEmpty(date)) {
			Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
			calendar.setTime(date);
			String month = new SimpleDateFormat("MMM").format(calendar.getTime());
			return String.valueOf(month);
		}
		return null;

	}
	
	
	public static String getYearByDateTime(Date date) {
		if (!ObjectUtil.isEmpty(date)) {
			Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			return String.valueOf(year);
		}
		return null;

	}
	
	public static String convertWeatherDateTime(String inputDate) throws ParseException
	{
			SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.WEATHER_DATE_FORMAT_TIME);
		    SimpleDateFormat formatter1 = new SimpleDateFormat(DateUtil.TXN_DATE_TIME);
		    Date date = formatter.parse(inputDate);
		    String strDate= formatter1.format(date);
			return strDate;  
	}
	
	public static Date getDateWithStartMinuteofDay(Date date) {

		if (!ObjectUtil.isEmpty(date)) {
			Calendar cal = getCalendar();
			cal.setTimeInMillis(date.getTime());
			cal.set(Calendar.HOUR_OF_DAY, 00);
			cal.set(Calendar.MINUTE, 00);
			cal.set(Calendar.SECOND, 00);
			cal.set(Calendar.MILLISECOND, 00);
			return cal.getTime();
		} else {
			return date;
		}
	}
	
	public static Date getStartDateWithTime() {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);

		return cal.getTime();
	}
	
	public static Date getCurrentDateWithLastTime() {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		
		return cal.getTime();
	}
	
	public static String removeDateDotZeroString(String inputDate)
	{
		SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");
		String replaceDot=inputDate.replace(".0", "");
		return replaceDot;
		
	}
	public static String getDateTimWithMinNew() {

		DateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmm");
		String date = dateFormat.format(new Date());
		return date;
	}
	
	public static long getRevisionNumberMills() {

		DateFormat dateFormat = new SimpleDateFormat(REVISION_NO_DATE_TIME_MILLISEC_FORMATS);
		String date = dateFormat.format(new Date());
		return Long.valueOf(date);
	}
}
