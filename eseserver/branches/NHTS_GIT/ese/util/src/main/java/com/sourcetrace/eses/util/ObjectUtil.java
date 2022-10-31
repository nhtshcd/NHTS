/*
 * ObjectUtil.java
 * Copyright (c) 2013-2014, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ObjectUtil {

	public static final List<String> arithematicSymbols = new ArrayList<String>() {
		{
			add("=");
			add("+");
			add("-");
			add("*");
			add("/");
			add("%");
			add("(");
			add(")");
			add("<");
			add(">");
			add("^");
		}
	};

	/**
	 * Checks if is empty.
	 *
	 * @param value
	 *            the value
	 * @return true, if is empty
	 */
	public static boolean isEmpty(Object value) {

		return value == null;
	}

	/**
	 * Checks if is list empty.
	 *
	 * @param value
	 *            the value
	 * @return true, if is list empty
	 */
	public static boolean isListEmpty(List value) {

		return !(value != null && value.size() > 0);
	}

	/**
	 * Checks if is list empty.
	 *
	 * @param value
	 *            the value
	 * @return true, if is list empty
	 */
	public static boolean isListEmpty(Set value) {

		return !(value != null && value.size() > 0);
	}

	/**
	 * Validate object.
	 *
	 * @param object
	 *            the object
	 * @return the string
	 */
	public static String validateObject(Object object) {

		if (isEmpty(object)) {
			return StringUtil.EMPTY;
		} else {
			return object.toString();
		}
	}

	public static boolean isLong(Object o) {

		if (o == null) {
			return false;
		}
		try {
			Long l = Long.parseLong(o.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static List<Long> convertStringList(List<String> strings) {
		return strings.stream().filter(u -> u != null).map(u -> Long.parseLong(u.trim())).collect(Collectors.toList());

	}
	public static List<Long> convertStringList(String strings) {
		return Arrays.asList(strings.split(",")).stream().filter(u -> u != null).map(u -> Long.parseLong(u.trim())).collect(Collectors.toList());
		

	}

	public static List<List<String>> splitStartEndYear(List<String> years) {

		List<List<String>> list = new ArrayList<List<String>>();
		List<String> startYears = new ArrayList<String>();
		List<String> endYears = new ArrayList<String>();
		for (String year : years) {
			String[] arr = year.trim().split(" - ");
			startYears.add(arr[0]);
			endYears.add(arr[1]);
		}
		list.add(startYears);
		list.add(endYears);
		return list;
	}

	public static String mergeStringArray(String[] arr, String seprater) {

		String result = "";
		for (String string : arr) {
			result += string + seprater;
		}
		return result;
	}

	public static String getAllUniqueValues(List<String> strings) {

		Set<String> uniqueStrings = new HashSet<String>(strings);
		String result = "";
		for (String string : uniqueStrings) {
			result += string + ",";
		}
		return StringUtil.removeLastComma(result);
	}

	public static List<String> mapToList(Map<String, String> map) {
		List<String> list = new ArrayList<String>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			list.add(entry.getKey());
		}
		return list;
	}

	public static List<Long> stringListToLongList(List<String> list) {
		List<Long> list2 = new ArrayList<Long>();
		for (String string : list) {
			list2.add(Long.parseLong(string));
		}
		return list2;
	}

	public static int safeLongToInt(long l) {
		if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
		}
		return (int) l;
	}

	public static boolean isInteger(Object obj) {
		if (ObjectUtil.isEmpty(obj)) {
			return false;
		}
		try {
			int l = Integer.parseInt(obj.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isDouble(Object obj) {
		if (ObjectUtil.isEmpty(obj)) {
			return false;
		}
		try {
			double l = Double.parseDouble(obj.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Long[] toLongArray(List<Object> objects) {
		Long[] longs = new Long[objects.size()];
		for (int i = 0; i < objects.size(); i++) {
			longs[i] = Long.parseLong(objects.get(i).toString());
		}
		return longs;
	}

	public static String[] listToArray(List<String> strings) {
		String[] stringArr = new String[strings.size()];
		for (int i = 0; i < strings.size(); i++) {
			stringArr[i] = strings.get(i);
		}
		return stringArr;
	}

	public static List<Object> toListOfObjects(List<Object[]> objects) {
		List<Object> objects2 = new ArrayList<Object>();
		for (Object[] objects3 : objects) {
			objects2.add((Object) objects3);
		}
		return objects2;
	}

	public static List<String> objListToStringList(List<Object> objects) {
		List<String> strings = new ArrayList<String>();
		for (Object object : objects) {
			strings.add(isEmpty(object) ? null : object.toString());
		}
		return strings;
	}

	public static List<Long> objListToLongList(List<Object> objects) {
		List<Long> longs = new ArrayList<Long>();
		for (Object object : objects) {
			if (!ObjectUtil.isEmpty(object)) {
				if (object instanceof BigInteger) {
					longs.add((Long) ((BigInteger) object).longValue());
				} else {
					longs.add((Long) object);
				}
			} else {
				longs.add(null);
			}
		}
		return longs;
	}

	public static Map<String, String> removeMapValues(Map<String, String> masterMap, Map<String, String> selectedMap) {

		for (Object key : selectedMap.keySet()) {
			if (masterMap.containsKey(key)) {
				masterMap.remove(key);
			}
		}
		return masterMap;
	}

	public static boolean isValidFormula(String formula) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		try {
			String result = String.valueOf(engine.eval(formula));
		} catch (ScriptException e) {
			return false;
		}
		return true;
	}

	public static Map collectionToMap(Object collectionObj, String field) {
		Map map = new HashMap();
		if (collectionObj instanceof Set) {
			Set set = (Set) collectionObj;
			for (Object object : set) {
				Object key = ReflectUtil.getFieldValue(object, field);
				map.put(key, object);
			}
		} else if (collectionObj instanceof List) {
			List list = (List) collectionObj;
			for (Object object : list) {
				Object key = ReflectUtil.getFieldValue(object, field);
				map.put(key, object);
			}
		}
		return map;
	}

	public static boolean isBoolean(Object obj) {
		if (ObjectUtil.isEmpty(obj)) {
			return false;
		}
		try {
			boolean b = Boolean.parseBoolean(obj.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isLong(Object[] obj) {
		if (ObjectUtil.isEmpty(obj)) {
			return false;
		}
		try {
			for (Object object : obj) {
				long l = Long.parseLong(object.toString());
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isDouble(Object[] obj) {
		if (ObjectUtil.isEmpty(obj)) {
			return false;
		}
		try {
			for (Object object : obj) {
				double l = Double.parseDouble(object.toString());
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Integer[] toIntArray(String[] sArr) {
		Integer[] intArr = new Integer[sArr.length];
		for (int i = 0; i < sArr.length; i++) {
			intArr[i] = Integer.parseInt(sArr[i]);
		}
		return intArr;
	}

	public static Long[] toLongArray(String[] sArr) {
		Long[] longArr = new Long[sArr.length];
		for (int i = 0; i < sArr.length; i++) {
			longArr[i] = Long.parseLong(sArr[i]);
		}
		return longArr;
	}

	public static Double[] toDoubleArray(String[] sArr) {
		Double[] doubleArr = new Double[sArr.length];
		for (int i = 0; i < sArr.length; i++) {
			doubleArr[i] = Double.parseDouble(sArr[i]);
		}
		return doubleArr;
	}

	public static Float[] toFloatArray(String[] sArr) {
		Float[] floatArr = new Float[sArr.length];
		for (int i = 0; i < sArr.length; i++) {
			floatArr[i] = Float.parseFloat(sArr[i]);
		}
		return floatArr;
	}
}
