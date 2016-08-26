package wusc.edu.pay.common.utils;

import java.beans.Beans;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import net.sf.cglib.beans.BeanCopier;

import org.apache.log4j.Logger;

/**
 * Bean属性拷贝工具
 * 
 * @version:
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BeanUtils {

	/**
	 * 存储BeanCoper对象
	 */
	public static Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();

	private static Logger logger = Logger.getLogger(BeanUtils.class);
	/**
	 * 转换时对map中的key里的字符串会做忽略处理的正则串
	 */
	private static final String OMIT_REG = "_";

	/**
	 * 将source的所有对象copy到desc中
	 * 
	 * @param source
	 *            原对象列表
	 * @param desc
	 *            目标对象列表
	 * @param descClazz
	 *            目标对象类型
	 */
	public static void copyListProperties(List source, List desc, Class descClazz) {
		for (Object o : source) {
			try {
				Object d = descClazz.newInstance();
				copyProperties(o, d);
				desc.add(d);
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

	}

	/**
	 * 对象赋值
	 * 
	 * @param source
	 * @param target
	 */
	public static void copyProperties(Object source, Object target) {
		if (source != null) {
			String beanKey = generateKey(source.getClass(), target.getClass());
			if (!beanCopierMap.containsKey(beanKey)) {
				BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
				beanCopierMap.put(beanKey, copier);
			}
			beanCopierMap.get(beanKey).copy(source, target, null);
		}
	}

	private static String generateKey(Class<?> cls1, Class<?> cls2) {
		return cls1.toString() + cls2.toString();
	}

	//
	// public static void copyProperties(Object source, Object target) {
	// String[] properties = getPropertyNames(source);
	// copyProperties(source, target, properties, true, true);
	// }

	public static void copyProperties(Object source, Object target, String[] properties) {
		copyProperties(source, target, properties, true, true);
	}

	public static void copyProperties(Object source, Object target, boolean convertType, boolean ignoreNullValue) {
		String[] properties = getPropertyNames(source);
		copyProperties(source, target, properties, convertType, ignoreNullValue);
	}

	public static void copyProperties(Object source, Object target, String[] properties, boolean convertType, boolean ignoreNullValue) {
		Map valueMap = getProperties(source, properties);

		Iterator keys = valueMap.keySet().iterator();
		while (keys.hasNext()) {
			String property = keys.next().toString();
			Object value = valueMap.get(property);
			copyProperty(target, property, value, convertType, ignoreNullValue);
		}
	}

	public static boolean copyProperty(Object obj, String property, Object value) {
		return copyProperty(obj, property, value, true, true);
	}

	public static boolean copyProperty(Object obj, String property, Object value, boolean convertType, boolean ignoreNullValue) {
		if (obj == null) {
			throw new IllegalArgumentException("no bean specified");
		}
		if (property == null) {
			throw new IllegalArgumentException("no property specified");
		}
		if (ignoreNullValue && value == null) {
			return true;
		}

		if (obj instanceof Map) {
			return invokeSetter(obj, property, value, convertType, ignoreNullValue);
		}

		StringTokenizer st = new StringTokenizer(property, ".");
		if (st.countTokens() == 0) {
			return false;
		}

		Object current = obj;
		try {
			while (st.hasMoreTokens()) {
				String currentPropertyName = st.nextToken();
				if (st.hasMoreTokens()) {
					current = invokeGetter(current, currentPropertyName);
				} else {
					try {
						invokeSetter(current, currentPropertyName, value, convertType, ignoreNullValue);
					} catch (Exception e) {
						return false;
					}
				}
			}
			return true;
		} catch (NullPointerException e) {
			return false;
		}
	}

	public static Object getIndexedProperty(Object obj, int index) {
		if (!obj.getClass().isArray()) {
			if (!(obj instanceof java.util.List)) {
				throw new IllegalArgumentException("bean is not indexed");
			} else {
				return ((java.util.List) obj).get(index);
			}
		} else {
			return (Array.get(obj, index));
		}
	}

	/**
	 * 
	 * @param obj
	 * @param property
	 * @return
	 */
	public static Object getProperty(Object obj, String property) {
		if (obj == null) {
			throw new IllegalArgumentException("no bean specified");
		}
		if (property == null) {
			throw new IllegalArgumentException("no property specified");
		}

		if (obj instanceof Map) {
			return ((Map) obj).get(property);
		}

		StringTokenizer st = new StringTokenizer(property, ".");
		if (st.countTokens() == 0) {
			return null;
		}

		Object result = obj;

		try {
			while (st.hasMoreTokens()) {
				String currentPropertyName = st.nextToken();
				if (result != null) {
					result = PropertyUtils.getProperty(result, currentPropertyName);
				}
			}
			return result;
		} catch (NullPointerException e) {
			logger.error(e);
			return null;
		}
	}

	/**
	 * 获取对象属性
	 * 
	 * @param obj
	 * @param properties
	 * @return
	 */
	public static Map getProperties(Object obj, String[] properties) {
		if (obj == null) {
			throw new IllegalArgumentException("no bean specified");
		}
		if (properties == null) {
			throw new IllegalArgumentException("no priperties specified");
		}
		Map result = new LinkedHashMap();
		for (int i = 0; i < properties.length; i++) {
			Object value = getProperty(obj, properties[i]);
			result.put(properties[i], value);
		}
		return result;
	}

	/**
	 * 获取对象的所有属性
	 * 
	 * @param obj
	 * @return
	 */
	public static Map getProperties(Object obj) {
		String[] propertiyNames = getPropertyNames(obj);
		return getProperties(obj, propertiyNames);
	}

	/**
	 * get property from object
	 * 
	 * @param obj
	 *            target object
	 * @param property
	 *            target property
	 * @return
	 */
	public static Object invokeGetter(Object obj, String property) {
		if (obj instanceof Map) {
			return ((Map) obj).get(property);
		} else {
			return PropertyUtils.getProperty(obj, property);
		}
	}

	/**
	 * inject value into object
	 * 
	 * @param obj
	 *            target object
	 * @param property
	 *            target property
	 * @param value
	 *            injected object
	 * @param autoConvert
	 *            是否需要自动转换类型
	 * @param ingoreNullValue
	 *            是否自动忽略NULL值
	 * @return
	 */
	public static boolean invokeSetter(Object target, String property, Object value, boolean autoConvert, boolean ingoreNullValue) {
		if (target instanceof Map) {
			((Map) target).put(property, value);
			return true;
		}
		Object newValue = null;
		if (autoConvert) {
			Class type = PropertyUtils.getPropertyType(target.getClass(), property);
			if (Beans.isInstanceOf(value, type)) {
				newValue = value;
			} else if (value instanceof String) {
				newValue = ConvertUtils.convert(type, (String) value);
			} else {
				newValue = value;
			}
		} else {
			newValue = value;
		}
		if (!ingoreNullValue || newValue != null) {
			PropertyUtils.setProperty(target, property, newValue);
		}
		return true;
	}

	/**
	 * 获取所有属性名称
	 * 
	 * @param obj
	 * @return
	 */
	private static String[] getPropertyNames(Object obj) {
		if (obj == null) {
			throw new IllegalArgumentException("no bean specified");
		}
		if (obj instanceof Map) {
			Object[] keys = ((Map) obj).keySet().toArray();
			String[] results = new String[keys.length];
			for (int i = 0; i < keys.length; i++) {
				results[i] = keys[i] + "";
			}
			return results;
		}
		String[] result = PropertyUtils.getPropertyNames(obj.getClass());
		int index = Arrays.binarySearch(result, "class");

		if (index > 0) {
			if (result.length == 1) {
				return new String[0];
			}
			String[] newResult = new String[result.length - 1];
			System.arraycopy(result, 0, newResult, 0, index);
			if (index < result.length) {
				System.arraycopy(result, index + 1, newResult, index, result.length - index - 1);
			}
			return newResult;
		} else {
			return result;
		}
	}

	/**
	 * 
	 * @param cla
	 * @param source
	 * @return
	 */
	public static <E> E toBean(Class<E> cla, Object source) {
		try {
			E obj = null;
			if (source == null) {
				return obj;
			}
			obj = cla.newInstance();
			copyProperties(source, obj);
			return obj;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	/**
	 * 将map转换成Bean，Bean的属性名与map的key值对应时不区分大小写，并对map中key做忽略OMIT_REG正则处理
	 * 
	 * @param <E>
	 * @param cla
	 * @param map
	 * @return
	 */
	public static <E> E toBean(Class<E> cla, Map<String, Object> map) {

		// 创建对象
		E obj = null;
		try {
			obj = cla.newInstance();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
		// 处理map的key
		Map<String, Object> newmap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> en : map.entrySet()) {
			newmap.put("set" + en.getKey().trim().replaceAll(OMIT_REG, "").toLowerCase(), en.getValue());
		}
		// 进行值装入
		Method[] ms = cla.getMethods();
		for (Method method : ms) {
			String mname = method.getName().toLowerCase();
			if (mname.startsWith("set")) {
				Class[] clas = method.getParameterTypes();
				Object v = newmap.get(mname);
				if (v != null && clas.length == 1) {
					try {
						method.invoke(obj, v);
					} catch (Exception e) {
						logger.error(e);
					}
				}
			}
		}
		return obj;
	}

	/**
	 * 将map集合转换成Bean集合，Bean的属性名与map的key值对应时不区分大小写，并对map中key做忽略OMIT_REG正则处理
	 * 
	 * @param <E>
	 * @param cla
	 * @param mapList
	 * @return
	 */
	public static <E> List<E> toBeanList(Class<E> cla, List<Map<String, Object>> mapList) {
		List<E> list = new ArrayList<E>(mapList.size());
		for (Map<String, Object> map : mapList) {
			E obj = toBean(cla, map);
			list.add(obj);
		}
		return list;

	}
}
