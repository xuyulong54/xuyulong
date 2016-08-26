package wusc.edu.pay.gateway.utils;

import java.util.Map;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.utils.ResourceUtils;


/**
 * ClassName: Context <br/>
 * Function:  <br/>
 * date: 2014-8-5 下午3:41:36 <br/>
 * 
 * @author laich
 */
public class Context {
	
	
	/**
	 * 当前用户的SN列表
	 */
	public static final String CURRENT_SN_LIST = "CURRENT_SN_LIST";
	/**
	 * 当前用户的SN
	 */
	public static final String CURRENT_SN = "CURRENT_SN";
	/**
	 * 是否是数字证书用户
	 */
	public static final String IS_SN_USER = "IS_SN_USER";
	/**
	 * 银行渠道
	 */
	public static final String FRP_CODE = "BALANCE";
	
	/**
	 * 当前登录用户
	 */
	public static final String CURRENT_USER = "currentUser";
	/**
	 * 当前登录用户
	 */
	public static final String CURRENT_ORDER = "PaymentOrderVo";
	

	/**
	 * 是否使用CFCA密码控件
	 */
	public static boolean USE_KEYBOARD = PublicConfig.USE_KEYBOARD ;
	
	/**
	 * 是否启用CFCA数字证书
	 */
	public static boolean USE_SECURITYCENTER = PublicConfig.USE_KEYBOARD;
	
}
