package wusc.edu.pay.core.banklink.netpay.util;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.utils.CheckUtils;


/**
 * 读取银行接口参数配置文件
 * @author Administrator
 *
 */
public class BankConfUtil {

	private static final Log logger = LogFactory.getLog(BankConfUtil.class);

	/** 所需要读取的配置文件，以‘，’相隔 */
	private static final String CONFIG_FILE_PATH = "service";

	/** 配置文件中的属性，读取后缓存到此MAP中 */
	private static Map<String, String> configs;

	static {
		configs = new HashMap<String, String>();
		// 读取配置文件
		ResourceBundle bundle = ResourceBundle.getBundle(CONFIG_FILE_PATH);
		logger.info("加载运行时配置文件[CONFIG_FILE_PATH = {}]" + CONFIG_FILE_PATH);
		// 将文件中属性以key-value形式缓存到MAP中
		for (String key : bundle.keySet()) {
			configs.put(key, bundle.getString(key));
			logger.debug("读取: {" + key + "} = {" + bundle.getString(key) + "}");
		}
	}

	/**
	 * 获得配置文件中的某个值
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		if (CheckUtils.isEmpty(key)) {
			return null;
		}
		String value = configs.get(key);
		return value;
	}

	/**
	 * 获得证书根路径
	 * 
	 * @return
	 */
	public static String getCertRoot() {
		String root = get("bankCertPath");
		if (root == null) {
			throw new RuntimeException("属性值为空，请在配置文件中配置，属性名称为bankCertPath");
		}
		return root;
	}

	/**
	 * 获得银行订单号标志位
	 * 
	 * @return
	 */
	public static String getMarkPosition() {
		String mark = get("bankOrderNoFlag");
		if (mark == null) {
			mark = "3";
		}
		return mark;
	}

}
