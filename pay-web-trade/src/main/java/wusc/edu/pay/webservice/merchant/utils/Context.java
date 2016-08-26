 package wusc.edu.pay.webservice.merchant.utils;

import java.util.Map;

import wusc.edu.pay.common.utils.ResourceUtils;


/**
 *   balanceRate.properties
 * @desc  
 * @date  2013-10-31,下午4:28:01
 */
public class Context {
	
	final static String SYSTEM_FILE="service";//系统配置文件
	
	/**
	 * 系统文件配置 加载。
	 */
	public static Map<String,String> SYSTEM_CONFIG=ResourceUtils.getResource(SYSTEM_FILE).getMap();
	 
	public final static String SUCCESS="100";
	
	public static String notityReceiveUrl = SYSTEM_CONFIG.get("NOTIFY_RECEIVE_URL");
}
