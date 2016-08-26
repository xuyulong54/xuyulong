package wusc.edu.pay.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 行政区域工具类
 * 
 * @author healy
 * 
 */
public class ReginUtils {

	// 省份 代码=名称
	private static Map<String, String> provinceMap;
	// 市 代码=名称
	private static Map<String, String> cityMap;

	// 省份资源文件名称
	private static final String PROVINCE_RSC_NAME = "province";
	// 市资源文件名称
	private static final String CITY_RSC_NAME = "city";

	/**
	 * 获取省份的代码与名称映射
	 * 
	 * @return
	 */
	public static Map<String, String> getProvinceMap() {
		if (provinceMap == null) {
			ResourceUtils util = ResourceUtils.getResource(PROVINCE_RSC_NAME);
			provinceMap = util.getMap();
		}
		return trimKeyAndValue(provinceMap);
	}

	/**
	 * 获取市级代码与名称映射
	 * 
	 * @return
	 */
	public static Map<String, String> getCityMap() {
		if (cityMap == null) {
			ResourceUtils util = ResourceUtils.getResource(CITY_RSC_NAME);
			cityMap = util.getMap();
		}
		return trimKeyAndValue(cityMap);
	}

	/**
	 * 根据省份代码获取省份名称
	 * 
	 * @param provinceCode
	 *            省份代码
	 * @return
	 */
	public static String getProvinceNameByCode(String provinceCode) {
		if (provinceCode == null || "".equals(provinceCode.trim())) {
			return "";
		}
		Map<String, String> map = getProvinceMap();
		return map.get(provinceCode.trim()) != null ? map.get(provinceCode.trim()) : "";
	}

	/**
	 * 根据省份名称获取代码
	 * 
	 * @param provinceName
	 * @return
	 */
	public static String getProvinceCodeByName(String provinceName) {
		if (provinceName == null || "".equals(provinceName.trim())) {
			return "";
		}

		Map<String, String> map = getProvinceMap();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String code = it.next();
			String name = map.get(code);
			if (name != null && name.trim().equalsIgnoreCase(provinceName.trim())) {
				return code;
			}
		}
		return "";
	}

	/**
	 * 根据市级代码获取市级名称
	 * 
	 * @param cityCode
	 *            市级代码
	 * @return
	 */
	public static String getCityNameByCode(String cityCode) {
		if (cityCode == null || "".equals(cityCode.trim())) {
			return "";
		}
		Map<String, String> map = getCityMap();
		return map.get(cityCode.trim()) != null ? map.get(cityCode.trim()) : "";
	}

	/**
	 * 根据市级名称获取代码
	 * 
	 * @param provinceName
	 * @return
	 */
	public static String getCityCodeByName(String cityName) {
		if (cityName == null || "".equals(cityName.trim())) {
			return "";
		}

		Map<String, String> map = getCityMap();

		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String code = it.next();
			String name = map.get(code);
			if (name != null && name.trim().equalsIgnoreCase(cityName.trim())) {
				return code;
			}
		}
		return "";
	}

	/**
	 * 键值去首尾空格
	 * 
	 * @param map
	 * @return
	 */
	private static Map<String, String> trimKeyAndValue(Map<String, String> map) {
		Map<String, String> finalMap = new HashMap<String, String>();
		if (map == null || map.size() == 0) {
			return finalMap;
		}
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = map.get(key);
			if (key != null) {
				key = key.trim();
			}
			if (value != null) {
				value = value.trim();
			}
			finalMap.put(key, value);
		}
		return finalMap;
	}

}
