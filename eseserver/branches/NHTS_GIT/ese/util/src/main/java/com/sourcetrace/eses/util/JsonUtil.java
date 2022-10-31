package com.sourcetrace.eses.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonUtil {

    public static JSONObject mapToJsonObject(Map<String, String> map) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(map);
        return jsonObject;
    }

    public static JSONObject maptoJSONArrayMap(Map<String, String> map) {

        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put(entry.getKey(), entry.getValue());
            array.add(jsonObject2);
        }
        jsonObject.put("maps", array);
        return jsonObject;
    }

    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    public static boolean isWrapperType(Class<?> clazz) {

        return WRAPPER_TYPES.contains(clazz);
    }

    private static Set<Class<?>> getWrapperTypes() {

        Set<Class<?>> ret = new HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        ret.add(String.class);
        ret.add(Date.class);
        return ret;
    }

    public static JSONObject toJsonObject(Object obj) {

        JSONObject jsonObject = null;
        if (obj == null) {
            return jsonObject;
        }
        try {
            jsonObject = new JSONObject();
            Class<?> objClass = obj.getClass();

            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isPrivate(field.getModifiers())) {
                    field.setAccessible(true);
                }
                if (!field.getType().isPrimitive() && !isWrapperType(field.getType())) {
                    // JSONObject jsonObject2 = toJsonObject(field.get(obj));
                    // System.out.println("jsonObject2 " +
                    // jsonObject2.toString());
                    // jsonObject.put(field.getName(), jsonObject2);
                    // if (field.getType().isAssignableFrom(Date.class)) {
                    //
                    // } else {
                    jsonObject.put(field.getName(), "null");
                    // }
                } else if (field.getType().isAssignableFrom(Date.class)) {
                    jsonObject.put(field.getName(),
                            DateUtil.convertDateToString((Date) field.get(obj), DateUtil.DATE_FORMAT));
                } else if (field.getType().equals(Character.class)) {
                    String name = field.getName();
                    Object value = field.get(obj);
                    if (value == null) {
                        jsonObject.put(name, "");
                    } else {
                        jsonObject.put(name, value + "");
                    }
                } else {
                    String name = field.getName();
                    Object value = field.get(obj);
                    if (value instanceof Character) {
                        if (value == null) {
                            jsonObject.put(name, "");
                        } else {
                            jsonObject.put(name, value + "");
                        }
                    } else if (value == null) {
                        jsonObject.put(name, "");
                    } else {
                        jsonObject.put(name, value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray toJsonMaps(List objects, String[] columnNames) {

        return toJsonMaps(objects, columnNames, " ");
    }

    public JSONArray toJsonMaps(List objects, String[] columnNames, String seperater) {

        JSONArray jsonArray = new JSONArray();
        for (Object obj : objects) {
            String key = "", value = "";
            key = String.valueOf(ReflectUtil.getFieldValue(obj, columnNames[0]));
            for (int i = 1; i < columnNames.length; i++) {
                value += String.valueOf(ReflectUtil.getFieldValue(obj, columnNames[i])) + seperater;
            }
            value = StringUtil.removeLastString(value, seperater);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(key, value.trim());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    public JSONArray toJsonObjects(List<Object> objects, String[] columnNames) {

        JSONArray jsonArray = new JSONArray();
        for (Object obj : objects) {
            JSONObject jsonObject = new JSONObject();
            for (String fieldName : columnNames) {
                jsonObject.put(fieldName, String.valueOf(ReflectUtil.getFieldValue(obj, fieldName)));
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    
   
}
