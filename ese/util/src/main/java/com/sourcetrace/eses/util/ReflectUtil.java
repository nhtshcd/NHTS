package com.sourcetrace.eses.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sourcetrace.eses.txn.schema.Head;
import com.sourcetrace.eses.txn.schema.JsonRequest;

public class ReflectUtil {

	public static List fetchFieldValues(List objects, String fieldName) {
		List list = new ArrayList();
		for (Object object : objects) {
			list.add(getFieldValue(object, fieldName));
		}
		return list;
	}

	public static List<Object[]> buildExcelData(List objects, String[] fieldNames) {
		List<Object[]> listObjects = new ArrayList<Object[]>();
		for (Object object : objects) {
			Object[] objects2 = new Object[fieldNames.length];
			for (int i = 0; i < fieldNames.length; i++) {
				objects2[i] = getFieldValue(object, fieldNames[i]);
			}
			listObjects.add(objects2);
		}
		return listObjects;
	}

	public static Map buildMap(List objects, String[] columnNames) {

		return buildMap(objects, columnNames, " ");
	}

	public static Map buildMap(List objects, String[] columnNames, String seperater) {

		Map<String, String> map = new LinkedHashMap<String, String>();
		for (Object obj : objects) {
			String key = "", value = "";
			Object fieldValue = ReflectUtil.getFieldValue(obj, columnNames[0]);
			key = ObjectUtil.isEmpty(fieldValue) ? "" : String.valueOf(fieldValue);
			for (int i = 1; i < columnNames.length; i++) {
				fieldValue = ReflectUtil.getFieldValue(obj, columnNames[i]);
				value += (ObjectUtil.isEmpty(fieldValue) ? "" : String.valueOf(fieldValue)) + seperater;
			}
			value = StringUtil.removeLastString(value, seperater);
			if (!StringUtil.isEmpty(key)) {
				map.put(key, value.trim());
			}
		}
		return map;
	}

	public static List getMemberValues(List objects, String memberName) {

		List list = new ArrayList();
		for (Object obj : objects) {
			Object value = null;
			value = String.valueOf(ReflectUtil.getFieldValue(obj, memberName));
			list.add(value);
		}
		return list;
	}

	public static Object setEmptyStringToNull(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			Class<?> objClass = obj.getClass();

			Field[] fields = objClass.getDeclaredFields();
			for (Field field : fields) {
				if (Modifier.isPrivate(field.getModifiers())) {
					field.setAccessible(true);
				}
				if (field.getType().equals(String.class)) {
					String name = field.getName();
					Object value = field.get(obj);
					if (!ObjectUtil.isEmpty(value)) {
						if (value.equals("")) {
							field.set(obj, null);
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static List setListObjEmptyStringToNull(List objects) {
		List<Object> results = new ArrayList<Object>();
		for (Object object : objects) {
			results.add(setEmptyStringToNull(object));
		}
		return results;
	}

	public static Object cloneObjectValues(Object sourceObj, Object distObj) {

		if (sourceObj == null) {
			return null;
		}
		try {
			Class<?> objClass = sourceObj.getClass();

			Field[] fields = objClass.getDeclaredFields();
			Field[] dfields = distObj.getClass().getDeclaredFields();
			List<Field> flist = fetchFieldValues(new ArrayList<Field>(Arrays.asList(dfields)), "name");
			for (Field field : fields) {
				if (Modifier.isPrivate(field.getModifiers())) {
					field.setAccessible(true);
				}
				String name = field.getName();
				Object value = field.get(sourceObj);
				if (flist.contains(name)) {
					Field dField = distObj.getClass().getDeclaredField(name);
					if (dField != null) {
						dField.setAccessible(true);
						dField.set(distObj, value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return distObj;
	}

	public static Field getField(String classAndFieldName) {

		String fieldName = StringUtil.substringLast(classAndFieldName, ".");
		String className = StringUtil.substringBeforeLast(classAndFieldName, ".");
		Field field = null;
		try {
			Class<?> objClass = Class.forName(className);
			field = objClass.getDeclaredField(fieldName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return field;
	}

	public static Object getFieldValue(Object object, String fieldName) {
		if (object instanceof Object[]) {
			if (ObjectUtil.isLong(fieldName)) {
				Object[] objects = (Object[]) object;
				return objects[Integer.parseInt(fieldName)];
			}
		} else {
			try {
				Field field = object.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				return field.get(object);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} // NoSuchFieldException
			catch (NoSuchFieldError e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void setFieldDesiredValue(Field field, Object object, Object value) {
		try {
			if (field.getType().equals(Integer.class) && ObjectUtil.isLong(value)) {
				field.set(object, Integer.parseInt(value.toString()));
			} else if (field.getType().equals(Boolean.class) && ObjectUtil.isBoolean(value)) {
				field.set(object, Boolean.parseBoolean(value.toString()));
			} else {
				field.set(object, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static HttpServletRequest getCurrentHttpRequest() {
		if (!ObjectUtil.isEmpty(RequestContextHolder.getRequestAttributes())) {
			return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		}
		return null;
	}

	public static HttpSession getCurrentHttpSession() {
		HttpServletRequest request = getCurrentHttpRequest();
		if (!ObjectUtil.isEmpty(request)) {
			return request.getSession();
		}
		return null;
	}

	public static List<Object[]> buildPDFData(List objects, String[] fieldNames) {
		List<Object[]> listObjects = new ArrayList<Object[]>();
		for (Object object : objects) {
			Object[] objects2 = new Object[fieldNames.length];
			for (int i = 0; i < fieldNames.length; i++) {
				objects2[i] = getObjectFieldValue(object, fieldNames[i]);
			}
			listObjects.add(objects2);
		}
		return listObjects;
	}

	public static Object getObjectFieldValue(Object object, String fieldName) { // to
																				// get
																				// object
																				// field
																				// name
																				// from
																				// string
																				// passed.

		Object objectValue = null;
		if (object instanceof Object[]) {
			if (ObjectUtil.isLong(fieldName)) {
				Object[] objects = (Object[]) object;

				if (objects[Integer.parseInt(fieldName)] instanceof byte[]) {
					return objects[Integer.parseInt(fieldName)];
				} else {
					return objects[Integer.parseInt(fieldName)] != null
							? String.valueOf(objects[Integer.parseInt(fieldName)]) : "";
				}

			} else if (fieldName.contains(",")) {
				String[] fields = fieldName.split(",", -1);
				Object[] objAry = new Object[fields.length];
				Object[] objects = (Object[]) object;
				int i = 0;
				for (String str : fields) {
					objAry[i] = objects[Integer.parseInt(str)] != null ? String.valueOf(objects[Integer.parseInt(str)])
							: "";
					i++;
				}
				return objAry;
			}
		} else {
			Object classObject = null;
			try {
				objectValue = getObjectField(object, fieldName); // Get object
																	// value
																	// returned
				// field.setAccessible(true);
				// classObject =
				// Class.forName(field.getClass().getName()).cast(object);
				return objectValue;

			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldError e) {
				e.printStackTrace();
			} /*
				 * catch (IllegalAccessException e) { e.printStackTrace();
				 * }catch (ClassNotFoundException e) { // TODO Auto-generated
				 * catch block e.printStackTrace(); }
				 */
		}
		return null;
	}

	public static List<String> getObjectFieldValueByParameters(Object arr, String[] parametersArray,
			Map<Integer, Long> index) {
		List<String> fValueList = new ArrayList<>();
		String final_fvalue = null;

		for (int i = 0; i < parametersArray.length; i++) {
			if (!ObjectUtil.isEmpty(parametersArray[i]) && !StringUtil.isEmpty(parametersArray[i])) {
				for (Entry<Integer, Long> entry : index.entrySet()) {
					if (Long.toString(entry.getValue()).equals(parametersArray[i])) {
						// System.out.println(entry.getKey());
						Object temp = ReflectUtil.getObjectFieldValue(arr, String.valueOf(entry.getKey()));
						fValueList.add(temp.toString());
					}
				}
			}
		}

		return fValueList;
	}

	public static String getObjectFieldValueByExpression(Object arr, String expression2, Map<Integer, Long> index)
			throws ScriptException {

		StringBuilder expression = new StringBuilder(expression2);
		String temp_expression = expression.toString();
		List<String> idList = new ArrayList<String>();
		List<String> idValueList = new ArrayList<String>();
		String final_expression = "";

		for (int i = 0; i <= expression.length(); i++) {
			if (expression.toString().contains("{")) {
				/*
				 * System.out.println(expression.indexOf("{"));
				 * System.out.println(expression.indexOf("}"));
				 * System.out.println(expression.substring(expression.indexOf(
				 * "{")+1, expression.indexOf("}")));
				 */
				idList.add(expression.substring(expression.indexOf("{") + 1, expression.indexOf("}")));
				expression = expression.deleteCharAt(expression.indexOf("{"));
				expression = expression.deleteCharAt(expression.indexOf("}"));
			}
		}

		for (String string : idList) {
			for (Entry<Integer, Long> entry : index.entrySet()) {
				if (Long.toString(entry.getValue()).equals(string)) {
					// System.out.println(entry.getKey());
					Object temp = ReflectUtil.getObjectFieldValue(arr, String.valueOf(entry.getKey()));
					idValueList.add(temp.toString());
				}
			}
		}

		for (int i = 0; i < idValueList.size(); i++) {
			final_expression = temp_expression.toString().replace(idList.get(i), idValueList.get(i));
			temp_expression = final_expression;
		}

		String expression_without_parenthesis = final_expression.replaceAll("[{}]", "");

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		Object result = engine.eval(expression_without_parenthesis);
		// System.out.println("result - "+result);

		/*
		 * List< String > fValueList = new ArrayList<>(); String
		 * final_expression = "";
		 * 
		 * String expression_without_parenthesis = expression.replaceAll("[()]",
		 * ""); String[] expressionArray =
		 * expression_without_parenthesis.split("[^\\d.]");
		 * 
		 * for (int i = 0; i < expressionArray.length; i++) {
		 * if(!ObjectUtil.isEmpty(expressionArray[i]) &&
		 * !StringUtil.isEmpty(expressionArray[i])){ for (Entry<Integer, Long>
		 * entry : index.entrySet()) { if
		 * (Long.toString(entry.getValue()).equals(expressionArray[i])) { //
		 * System.out.println(entry.getKey()); Object temp =
		 * ReflectUtil.getObjectFieldValue(arr,String.valueOf(entry.getKey()));
		 * fValueList.add(temp.toString()); } } } }
		 * 
		 * for (int i = 0; i < expressionArray.length; i++) { final_expression =
		 * expression.replace(expressionArray[i], fValueList.get(i)); expression
		 * = final_expression; }
		 * 
		 * ScriptEngineManager manager = new ScriptEngineManager(); ScriptEngine
		 * engine = manager.getEngineByName("js"); Object result =
		 * engine.eval(final_expression);
		 */
		return result.toString();

	}

	public static Object getObjectField(Object object, String classAndFieldName) {
		String[] fields = null;
		Field field = null;

		fields = classAndFieldName.split("\\.");

		try {
			for (String fieldName : fields) {
				if (!ObjectUtil.isEmpty(object)) {
					try {
						field = object.getClass().getDeclaredField(fieldName);
					} catch (NoSuchFieldException e) {
						try{
							field = object.getClass().getSuperclass().getDeclaredField(fieldName);
						}catch(NoSuchFieldException ex){
							field = object.getClass().getSuperclass().getSuperclass().getDeclaredField(fieldName);
						}
						
					}
					field.setAccessible(true);
					object = field.get(object);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;
	}

	public static String getCurrentUser() {
		HttpServletRequest request = getCurrentHttpRequest();
		if (request != null && request.getAttribute("reqObj") != null
				&& request.getAttribute("reqObj") instanceof JsonRequest) {
			JsonRequest js = (JsonRequest) request.getAttribute("reqObj");
			return js.getHead().getAgentId();
		} else if (!ObjectUtil.isEmpty(request) && request.getSession() != null
				&& request.getSession().getAttribute("user") != null) {
			return (String) request.getSession().getAttribute("user");
		}

		return null;
	}

	public static String getCurrentLatitude() {
		HttpServletRequest request = getCurrentHttpRequest();
		if (request != null && request.getAttribute("reqObj") != null
				&& request.getAttribute("reqObj") instanceof JsonRequest) {
			JsonRequest js = (JsonRequest) request.getAttribute("reqObj");
			return js.getHead().getLat();
		}
		return null;
	}
	public static String getCurrentLongitude() {
		HttpServletRequest request = getCurrentHttpRequest();
		if (request != null && request.getAttribute("reqObj") != null
				&& request.getAttribute("reqObj") instanceof JsonRequest) {
			JsonRequest js = (JsonRequest) request.getAttribute("reqObj");
			return js.getHead().getLon();
		}
		return null;
	}

	public static String getCurrentIPaddress() {
		HttpServletRequest request = getCurrentHttpRequest();
		if (request != null && request.getAttribute("reqObj") != null
				&& request.getAttribute("reqObj") instanceof JsonRequest) {
			JsonRequest js = (JsonRequest) request.getAttribute("reqObj");
			return js.getHead().getIpAddress();
		} else if (!ObjectUtil.isEmpty(request) && request.getSession() != null) {
			String remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}

		return null;
	}

	public static Head getCurrentHeadReq() {
		HttpServletRequest request = getCurrentHttpRequest();
		if (request != null && request.getAttribute("reqObj") != null
				&& request.getAttribute("reqObj") instanceof JsonRequest) {
			JsonRequest js = (JsonRequest) request.getAttribute("reqObj");
			return js.getHead();
		}

		return null;
	}

}
