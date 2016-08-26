 package wusc.edu.pay.timer.report.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.utils.string.StringUtil;


/**
 * 
 * @描述: 银行对账文件管理属性配置文件上下文.
 * @作者: WuShuicheng.
 * @创建: 2014-4-21,下午3:26:48
 * @版本: V1.0
 *
 */
public class ReportProperties {
	
	private static final Log LOG = LogFactory.getLog(ReportProperties.class);
	
	/**
	 * 属性配置文件名.
	 */
	private static final String PROPERTIES_FILE = "report.properties";

	
	/** 每次批量获取记录数(默认1000次) */
	public static int BATCH_GET_NUMBER = 1000;
	/** 每次批量插入记录数(默认300) */
	public static int BATCH_INSERT_NUMBER = 300;
	
	// 第一次调用时加载属性文件，只加载一次
	static {
		try {
			LOG.info("==>load report.properties and init param");
			InputStream proFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE);
			Properties props = new Properties();
			props.load(proFile);
			init(props);
		} catch (Exception e) {
			LOG.error("==>load and init report.properties exception:", e);
		}
	}
	
	/**
	 * 获取属性文件中的属性值.<br/>
	 * @param props
	 */
	private static void init(Properties props) {
		
		String batch_get_number = props.getProperty("batch.get.number");
		if (StringUtil.isNotBlank(batch_get_number)) {
			BATCH_GET_NUMBER = Integer.valueOf(batch_get_number);;
		}
		
		String batch_insert_number = props.getProperty("batch.insert.number");
		if (StringUtil.isNotBlank(batch_insert_number)) {
			BATCH_INSERT_NUMBER = Integer.valueOf(batch_insert_number);;
		}

	}
}
