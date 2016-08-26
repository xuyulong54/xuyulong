package wusc.edu.pay.common.web.constant;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @描述: 会话键常量类.
 * @作者: WuShuicheng.
 * @创建: 2014-8-19,上午9:26:46
 * @版本: V1.0
 *
 */
public class PermissionConstant {
	
	/**
	 * logger.
	 */
	private static final Log LOG = LogFactory.getLog(PermissionConstant.class);

	/**
	 * 登录操作员的session键名.
	 */
	public static final String OPERATOR_SESSION_KEY = "pmsOperator";

	/**
	 * 登录操作员拥有的权限集合的session键名.
	 */
	public static final String ACTIONS_SESSION_KEY = "actions";

//
//	/**
//	 * 运营操作员会话键
//	 */
//	public static String WEB_OPERATOR_KEY = "WebOperators";
	
	/**
	 * 操作员在线用户数限制(默认100).
	 */
	public static int WEB_ONLINE_LIMIT = 100;
	
	/**
	 * 操作员密码连续输错次数限制(默认5).
	 */
	public static int WEB_PWD_INPUT_ERROR_LIMIT = 5;
	
	/**
	 * 只加载一次.
	 */
	static {
		try {
			LOG.info("=== load session.properties and init");
			InputStream proFile = Thread.currentThread().getContextClassLoader().getResourceAsStream("permission.properties");
			Properties props = new Properties();
			props.load(proFile);
			init(props);
		} catch (Exception e) {
			LOG.error("=== load and init session.properties exception:" + e);
		}
	}
	
	/**
	 * 根据配置文件初始化静态变量值.
	 * 
	 * @param props
	 */
	private static void init(Properties props) {
//		String web_operator_key_prefix = props.getProperty("web.operator.key.prefix");
//		if (StringUtils.isNotBlank(web_operator_key_prefix)) {
//			WEB_OPERATOR_KEY = web_operator_key_prefix + WEB_OPERATOR_KEY;
//			LOG.info("===>WEB_OPERATOR_KEY:" + WEB_OPERATOR_KEY);
//		}
		
		String web_online_limit = props.getProperty("web.online.limit");
		if (StringUtils.isNotBlank(web_online_limit)) {
			WEB_ONLINE_LIMIT = Integer.valueOf(web_online_limit);
			LOG.info("===>WEB_ONLINE_LIMIT:" + WEB_ONLINE_LIMIT);
		}
		
		String web_pwd_input_error_limit = props.getProperty("web.pwd.input.error.limit");
		if (StringUtils.isNotBlank(web_pwd_input_error_limit)) {
			WEB_PWD_INPUT_ERROR_LIMIT = Integer.valueOf(web_pwd_input_error_limit);
			LOG.info("===>WEB_PWD_INPUT_ERROR_LIMIT:" + WEB_PWD_INPUT_ERROR_LIMIT);
		}
	}

}
