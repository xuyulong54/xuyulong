package wusc.edu.pay.common.utils.json;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import wusc.edu.pay.common.utils.BeanUtils;
import wusc.edu.pay.common.utils.ConvertUtils;
import wusc.edu.pay.common.utils.PropertyUtils;


/**    
 * @version:   
 */
public class JSONUtils {
	/**
	 * 把对象转换成JSON格式的字符串
	 * @param obj 需要转换的对象
	 * @return
	 */
	public static String toJsonString(Object value){
		try {
			if (value == null) {
				return "null";
			}
			if (value instanceof JSONString) {
				return ((JSONString) value).toJSONString();
			}
			if (value instanceof JSONArray) {
				return ((JSONArray)value).toString();
			}
			if (value instanceof JSONObject) {
				return ((JSONObject)value).toString();
			}
			if (value instanceof Map) {
				return new JSONObject((Map) value).toString();
			}
			if (value instanceof Collection) {
				return new JSONArray((Collection) value).toString();
			}
			if (value.getClass().isArray()) {
				return new JSONArray(value).toString();
			}
			return new JSONObject(value).toString();
		} catch (JSONException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 把JSON格式的字符串转换成JAVABEAN
	 * @param <T> 
	 * @param str JSON格式的字符串
	 * @param clz 目标类型
	 * @return
	 */
	public static<T> T jsonToBean(String str, Class<T> clz){
		return (T)fromJSONString(str, clz, null);
	}
	
	/**
	 * 把JSON格式的字符串转换成Map
	 * @param str JSON格式的字符串
	 * @param keyType Map键的对象类型
	 * @param objType Map值的对象类型
	 * @return
	 */
	public static Map jsonToMap(String str, Class keyType, Class objType){
		return (Map)fromJSONString(str, Map.class, new Class[]{keyType, objType});
	}
	
	/**
	 * 把JSON格式的字符串转换成List
	 * @param str JSON格式的字符串
	 * @param objType List里面的对象类型
	 * @return
	 */
	public static List jsonToList(String str, Class objType){
		return (List)fromJSONString(str, List.class, new Class[]{objType});
	}
	
	/**
	 * 把JSON格式的字符串转换成对象数组
	 * @param str JSON格式的字符串
	 * @param objType 数组里面的对象类型
	 * @return
	 */
	public static Object jsonToArray(String str, Class objType){
		try {
			return fromJSONString(str, Class.forName("[L"+objType.getName()+";"), null);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	private static Object fromJSONString(String str, Class clz, Class[] actualTypes){
		if(str == null){
			return null;
		}
		str = str.trim();
		if(str.startsWith("[")){
			try {
				return populateObject(new JSONArray(str), clz, actualTypes, null, null);
			} catch (JSONException e) {
				throw new RuntimeException(e.getMessage() , e);
			}
		}
		if(str.startsWith("{")){
			try {
				return populateObject(new JSONObject(str), clz, actualTypes, null, null);
			} catch (JSONException e) {
				throw new RuntimeException(e.getMessage() , e);
			}
		}
		return populateObject(str, clz, actualTypes, null, null);
	}
	
	private static Object populateObject(Object jsonObj, Class clz, Class[] actualTypes, Class parentType, String propertyName){
		if(jsonObj instanceof JSONArray){
			if(clz==null){
				clz = ArrayList.class;
			}
			if(clz.isArray()){
				return populateArray((JSONArray)jsonObj, clz);
			}
			else if(Collection.class.isAssignableFrom(clz)){
				return populateCollection((JSONArray)jsonObj, clz, getActualTypes(actualTypes, parentType, propertyName));
			}
			else{
				throw new RuntimeException("can't convert JSONArray to class : "+clz);
			}
		}
		else if(jsonObj instanceof JSONObject){
			if(clz==null){
				clz = HashMap.class;
			}
			if(Map.class.isAssignableFrom(clz)){
				return populateMap((JSONObject)jsonObj, clz, getActualTypes(actualTypes, parentType, propertyName));
			}else{
				return populateBean((JSONObject)jsonObj, clz);
			}
		}
		else{
			if(clz!=null && jsonObj!=null && !jsonObj.getClass().equals(clz)){
				if(ConvertUtils.Type.parseType(clz.getName())!=null){
					return ConvertUtils.convert(clz.getName(), jsonObj.toString());
				}
			}
			return jsonObj!=null?jsonObj.toString():null;
		}
	}
	
	private static Map populateMap(JSONObject jsonObj, Class clz, Class[] actualTypes){	
		if(clz.equals(Map.class)){
			clz = HashMap.class;
		}
		Map map;
		try {
			map = (Map)clz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("can't get instance of class : "+clz.getName(), e);
		}
		Class keyType = null;
		Class valueType = null;
		if(actualTypes!=null && actualTypes.length==2){
			keyType = actualTypes[0];
			valueType = actualTypes[1];
		}
		Iterator it = jsonObj.keys();
		while(it.hasNext()){
			String key = (String)it.next();
			Object value;
			try {
				value = jsonObj.get(key);
			} catch (JSONException e) {
				throw new RuntimeException(e.getMessage() , e);
			}
			Object newKey = key;
			if(keyType!=null && !keyType.equals(String.class)){
				newKey = ConvertUtils.convert(keyType, key);
			}
			Object newValue = populateObject(value, valueType, null, null, null);
			map.put(newKey, newValue);
		}
		return map;
	}
	
	private static Object populateBean(JSONObject jsonObj, Class clz){		
		Object object;
		try {
			object = clz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("can't get instance of class : "+clz.getName(), e);
		}
		Iterator it = jsonObj.keys();
		while(it.hasNext()){
			String key = (String)it.next();
			Object value;
			try {
				value = jsonObj.get(key);
			} catch (JSONException e) {
				throw new RuntimeException(e.getMessage() , e);
			}
			Class type = PropertyUtils.getPropertyType(clz, key);
			Object newValue = populateObject(value, type, null, clz, key);
			BeanUtils.copyProperty(object, key, newValue);
		}
		return object;
	}
	
	private static Class[] getActualTypes(Class[] actualTypes, Class parentType, String propertyName){
		if(actualTypes!=null){
			return actualTypes;
		}
		if(parentType==null || propertyName==null){
			return null;
		}
		return PropertyUtils.getPropertyGenericActualTypes(parentType, propertyName);
	}
	
	private static Object populateArray(JSONArray jsonObj, Class clz){
		Class objType = clz.getComponentType();
		Object array = Array.newInstance(objType, jsonObj.length());
		for(int i=0; i<jsonObj.length(); i++){
			Object obj;
			try {
				obj = jsonObj.get(i);
			} catch (JSONException e) {
				throw new RuntimeException(e.getMessage() , e);
			}
			Object newValue = populateObject(obj, objType, null, null, null);
			Array.set(array, i, newValue);
		}
		return array;
	}
	
	private static Object populateCollection(JSONArray jsonObj, Class clz, Class[] actualTypes){
		if(clz.equals(List.class)){
			clz = ArrayList.class;
		}
		if(clz.equals(Set.class)){
			clz = HashSet.class;
		}
		
		Collection collection = null;
		try {
			collection = (Collection)clz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("can't get instance of class : "+clz.getName(), e);
		}
		Class objType = null;
		if(actualTypes!=null && actualTypes.length==1){
			objType = actualTypes[0];
		}
		for(int i=0; i<jsonObj.length(); i++){
			Object obj;
			try {
				obj = jsonObj.get(i);
			} catch (JSONException e) {
				throw new RuntimeException(e.getMessage() , e);
			}
			Object newValue = populateObject(obj, objType, null, null, null);
			collection.add(newValue);
		}
		return collection;
	}
}	
