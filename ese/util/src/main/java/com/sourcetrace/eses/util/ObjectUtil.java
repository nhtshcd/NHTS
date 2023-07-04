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

import com.sourcetrace.eses.entity.*;
import com.sourcetrace.eses.multitenancy.*;

import java.beans.*;
import java.lang.reflect.*;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.*;

import javax.persistence.*;
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
		return Arrays.asList(strings.split(",")).stream().filter(u -> u != null).map(u -> Long.parseLong(u.trim()))
				.collect(Collectors.toList());

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

	public static boolean isEquals(Object obj1, Object obj2) {
		// If both objects are null, consider them equal
		if (obj1 == obj2) {
			return true;
		}

		if (obj1 instanceof String || obj1 instanceof Long || obj1 instanceof Double || obj1 instanceof Integer) {

			return obj1.equals(obj2);

		}

		if (obj1 == null || obj2 == null || obj1.getClass() != obj2.getClass()) {
			return false;
		}

		if (obj1.getClass().isArray()) {

			return deepEqualsWithExclusion((Object[]) obj1, (Object[]) obj2,
					new HashSet<>(Arrays.asList(0, 1, 2, 3, 4)));

		}
		/*
		 * if (obj1 instanceof Farmer) { // Skip the field comparison for Farmer
		 * objects ((Farmer) obj1).getFarms().clear(); return true; }
		 */
		// If the objects have different classes, consider them not equal
		if (!obj1.getClass().equals(obj2.getClass())) {
			return false;
		}
		// Create a stack to store the fields of the objects
		Deque<Field> fieldStack = new ArrayDeque<>();
		fieldStack.addAll(Arrays.asList(obj1.getClass().getDeclaredFields()));
		// fieldStack.addAll(Arrays.asList(obj1.getClass().getSuperclass().getDeclaredFields()));

		// Compare the fields of the objects
		while (!fieldStack.isEmpty()) {
			Field field = fieldStack.pop();
			// Enable access to private fields
			field.setAccessible(true);

			if (java.lang.reflect.Modifier.isFinal(field.getModifiers())
					|| java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
				continue;
			}

			if (field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(ManyToOne.class)
					|| field.isAnnotationPresent(OneToMany.class) ||

					isGetterMethodAnnotatedWithColumn(obj1.getClass(), field.getName(), Column.class)
					|| isGetterMethodAnnotatedWithColumn(obj1.getClass(), field.getName(), ManyToOne.class)

					|| isGetterMethodAnnotatedWithColumn(obj1.getClass(), field.getName(), OneToMany.class)) {
				if (field.getName().equalsIgnoreCase("updatedUser") || field.getName().equalsIgnoreCase("updatedDate")
						|| field.getName().equalsIgnoreCase("createdUser")
						|| field.getName().equalsIgnoreCase("createdDate")) {
					System.out.println(field.getName());
					return true;
				} else {
					try {
						// Get the field values of the objects
						Object fieldValue1 = ReflectUtil.getFieldValue(obj1, field.getName());
						Object fieldValue2 = ReflectUtil.getFieldValue(obj2, field.getName());

						// If the field values are not equal, consider the
						// objects not equal
						if (!isEquals(fieldValue1, fieldValue2)) {
							return false;
						}

						// If the field values are objects, push their fields
						// onto the stack for comparison
						if (fieldValue1 != null && fieldValue2 != null && !field.getType().isPrimitive()) {
							fieldStack.addAll(Arrays.asList(field.getType().getDeclaredFields()));
						}
					} catch (Exception e) {
						// Handle exception if unable to access field value
						e.printStackTrace();
					}
				}

			}
		}

		// All fields are equal
		return true;
	}

	public static boolean deepEqualsWithExclusion(Object[] array1, Object[] array2, Set<Integer> excludedIndexes) {
		if (array1.length != array2.length) {
			return false;
		}

		for (int i = 0; i < array1.length; i++) {
			if (excludedIndexes.contains(i)) {
				continue; // Skip the excluded index
			}

			if (!Objects.deepEquals(array1[i], array2[i])) {
				return false;
			}
		}

		return true;
	}

	public static boolean deepCompare(Object[] array1, Object[] array2) {
		if (array1 == null && array2 == null) {
			return true;
		}
		if (array1 == null || array2 == null) {
			return false;
		}
		return Arrays.deepEquals(array1, array2);
	}

	private static boolean isGetterMethodAnnotatedWithColumn(Class<?> clazz, String fieldName, Class ccd) {
		try {
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, clazz);
			Method getterMethod = propertyDescriptor.getReadMethod();
			return getterMethod != null && getterMethod.isAnnotationPresent(ccd);
		} catch (Exception e) {

			return false;
		}
	}

	/*
	 * public static boolean deepEquals(Object obj1, Object obj2, Set<Object>
	 * visited) { Deque<Object> stack = new ArrayDeque<>();
	 * 
	 * stack.push(obj1); stack.push(obj2);
	 * 
	 * while (!stack.isEmpty()) { Object o2 = stack.pop(); Object o1 =
	 * stack.pop();
	 * 
	 * if (o1 == o2) { continue; } if (o1 == null || o2 == null) { return false;
	 * }
	 * 
	 * Class<?> clazz = o1.getClass(); if (o1.getClass() == Farmer.class) {
	 * 
	 * System.out.println("ddd"); } if (clazz.isArray()) { if
	 * (!deepEqualsArray(o1, o2, visited)) { return false; } } else if (o1
	 * instanceof Collection && o2 instanceof Collection) { if
	 * (!deepEqualsCollection((Collection<?>) o1, (Collection<?>) o2, visited))
	 * { return false; } } else if (o1 instanceof Map && o2 instanceof Map) { if
	 * (!deepEqualsMap((Map<?, ?>) o1, (Map<?, ?>) o2, visited)) { return false;
	 * } } else if (clazz == Farmer.class) { // Skip the "farms" field for the
	 * Farmer object continue; } else if (!isEquals(o1, o2)) { return false; }
	 * 
	 * visited.add(o1); visited.add(o2);
	 * 
	 * if (clazz.isArray()) { int length = Array.getLength(o1); for (int i = 0;
	 * i < length; i++) { stack.push(Array.get(o1, i)); stack.push(Array.get(o2,
	 * i)); } } else { Field[] fields = clazz.getDeclaredFields(); for (Field
	 * field : fields) { if (field != null) { field.setAccessible(true); try {
	 * stack.push(field.get(o1) != null ? field.get(o1) : "");
	 * stack.push(field.get(o2) != null ? field.get(o2) : ""); } catch
	 * (IllegalAccessException e) { e.printStackTrace(); } } } } }
	 * 
	 * return true; }
	 */

	private static boolean deepEqualsArray(Object array1, Object array2, Set<Object> visited) {
		int length1 = Array.getLength(array1);
		int length2 = Array.getLength(array2);

		if (length1 != length2) {
			return false;
		}

		for (int i = 0; i < length1; i++) {
			Object element1 = Array.get(array1, i);
			Object element2 = Array.get(array2, i);

			if (!visited.contains(element1) || !visited.contains(element2)) {
				visited.add(element1);
				visited.add(element2);

				if (!deepEquals(element1, element2, visited)) {
					return false;
				}
			}
		}

		return true;
	}

	private static boolean deepEqualsCollection(Collection<?> coll1, Collection<?> coll2, Set<Object> visited) {
		if (coll1.size() != coll2.size()) {
			return false;
		}

		Iterator<?> iter1 = coll1.iterator();
		Iterator<?> iter2 = coll2.iterator();

		while (iter1.hasNext() && iter2.hasNext()) {
			Object element1 = iter1.next();
			Object element2 = iter2.next();

			if (!visited.contains(element1) || !visited.contains(element2)) {
				visited.add(element1);
				visited.add(element2);

				if (!deepEquals(element1, element2, visited)) {
					return false;
				}
			}
		}

		return true;
	}

	private static boolean deepEqualsMap(Map<?, ?> map1, Map<?, ?> map2, Set<Object> visited) {
		if (map1.size() != map2.size()) {
			return false;
		}

		for (Map.Entry<?, ?> entry : map1.entrySet()) {
			Object key1 = entry.getKey();
			Object value1 = entry.getValue();
			Object value2 = map2.get(key1);

			if (!visited.contains(value1) || !visited.contains(value2)) {
				visited.add(value1);
				visited.add(value2);

				if (!deepEquals(value1, value2, visited)) {
					return false;
				}
			}
		}

		return true;
	}

	public static boolean deepEquals(Object obj1, Object obj2, Set<Object> visited) {
		Deque<Object> stack = new ArrayDeque<>();
		if (obj1 == null || obj2 == null) {
			return false;
		}
		stack.push(obj1);
		stack.push(obj2);

		while (!stack.isEmpty()) {
			Object o2 = stack.pop();
			Object o1 = stack.pop();

			if (o1 == o2) {
				continue;
			}
			if (o1 == null || o2 == null) {
				return false;
			}

			Class<?> clazz = o1.getClass();

			// Check if objects have been visited before
			if (visited.contains(o1) && visited.contains(o2)) {
				continue;
			}

			if (clazz.isArray()) {
				if (!deepEqualsArray(o1, o2, visited)) {
					return false;
				}
			} else if (o1 instanceof Collection && o2 instanceof Collection) {
				if (!deepEqualsCollection((Collection<?>) o1, (Collection<?>) o2, visited)) {
					return false;
				}
			} else if (o1 instanceof Map && o2 instanceof Map) {
				if (!deepEqualsMap((Map<?, ?>) o1, (Map<?, ?>) o2, visited)) {
					return false;
				}
			} else if (!isEquals(o1, o2)) {
				return false;
			}

			visited.add(o1);
			visited.add(o2);

			if (clazz.isArray()) {
				int length = Array.getLength(o1);
				for (int i = 0; i < length; i++) {
					stack.push(Array.get(o1, i));
					stack.push(Array.get(o2, i));
				}
			} else {
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					if (field != null) {
						field.setAccessible(true);
						try {
							if (clazz != Farmer.class || !field.getName().equals("farms")) {
								if (clazz != Farm.class || !field.getName().equals("farmCrops")) {
									if (clazz != FarmCrops.class || !field.getName().equals("planting")) {
										stack.push(field.get(o1) != null ? field.get(o1) : "");
										stack.push(field.get(o2) != null ? field.get(o2) : "");
									}
								}
							}
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		return true;
	}

	private static boolean compareFieldsExceptFarms(Farmer farmer1, Farmer farmer2, Set<Object> visited) {
		Field[] fields = farmer1.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field != null && !field.getName().equals("farms")) {
				field.setAccessible(true);
				try {
					Object fieldValue1 = field.get(farmer1);
					Object fieldValue2 = field.get(farmer2);
					if (!deepEquals(fieldValue1, fieldValue2, visited)) {
						return false;
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
}
