/**
 * className：PublicConifg.java <br>
 * @version：1.0  <br>
 * date: 2014-11-5-上午10:15:20     <br>
 * Copyright (c)  2014中益智正公司-版权所有   <br>
 */
package wusc.edu.pay.common.config;

import java.util.Map;

import wusc.edu.pay.common.utils.ResourceUtils;


/**
 * className：PublicConifg <br>
 * Function： 环境配置基础类 <br>
 * date: 2014-11-5-上午10:15:20<br>
 * 
 * @author laich
 */
public class PublicConfig {

	/**
	 * 系统文件配置 加载。
	 */
	public static Map<String, String> PUBLIC_USER = ResourceUtils.getResource("public_user").getMap();

	/**
	 * 系统文件配置 加载。
	 */
	public static Map<String, String> PUBLIC_SYSTEM = ResourceUtils.getResource("public_system").getMap();

	/**
	 * 通知URL
	 */
	public final static String NOTIFY_RECEIVE_URL = PUBLIC_SYSTEM.get("NOTIFY_RECEIVE_URL");

	/**
	 * 门户地址
	 */
	public final static String PROTAL_URL = PUBLIC_SYSTEM.get("PROTAL_URL");

	/**
	 * b2b 支付地址
	 */
	public final static String B2BPAY_URL = PUBLIC_SYSTEM.get("B2BPayUrl");

	/**
	 * b2c 支付地址
	 */
	public final static String B2CPAY_URL = PUBLIC_SYSTEM.get("B2CPayUrl");

	/**
	 * 退款接口地址
	 */
	public final static String REFUND_REQUEST_URL = PUBLIC_SYSTEM.get("refundUrl");

	/**
	 * 充值 地址
	 */
	public final static String RECHARGE_URL = PUBLIC_SYSTEM.get("rechargeUrl");

	/**
	 * 商户充值 支付成功通知地址
	 */
	public final static String RECHARGE_MERCHANT_RETURNURL = PUBLIC_SYSTEM.get("RECHARGE_MERCHANT_RETURNURL");

	/**
	 * 会员充值 支付成功通知地址
	 */
	public final static String RECHARGE_MEMBER_RETURNURL = PUBLIC_SYSTEM.get("RECHARGE_MEMBER_RETURNURL");

	/**
	 * 订单查询地址
	 */
	public final static String ORDER_QUERY_URL = PUBLIC_SYSTEM.get("ORDER_QUERY_URL");

	/**
	 * 银行 支付成功回调地址
	 */
	public final static String BANK_SUCCESS_RETURNURL = PUBLIC_SYSTEM.get("BANK_SUCCESS_RETURNURL");

	/**
	 * 银行 支付成功回调地址
	 */
	public final static String BANK_FAIL_RETURNURL = PUBLIC_SYSTEM.get("BANK_FAIL_RETURNURL");

	/**
	 * 网关成功支付回调地址
	 */
	public final static String GATEWAY_PAY_RETURNURL = PUBLIC_SYSTEM.get("GATEWAY_PAY_RETURNURL");

	/**
	 * FastDFS分布式文件系统，文件访问URL.
	 */
	public final static String FILE_SYS_URL = PUBLIC_SYSTEM.get("FILE_SYS_URL");

	/**
	 * 网关访问URL.
	 */
	public final static String GATEWAY_URL = PUBLIC_SYSTEM.get("GATEWAY_URL");

	/**
	 * 分账URL
	 */
	public final static String WEB_TRADE_URL = PUBLIC_SYSTEM.get("WEB_TRADE_URL");
	/**
	 * ================================================================ URL
	 * 配置写在上面， 分割线
	 * ================================================================
	 */

	/**
	 * 密码错误限制次数
	 */
	public final static Integer PWD_ERROR_LIMIT_TIMES = Integer.parseInt(PUBLIC_USER.get("PWD_ERROR_LIMIT_TIMES"));

	/**
	 * 密码错误限制时间（分钟）
	 */
	public final static Integer PWD_ERROR_LIMIT_TIME = Integer.parseInt(PUBLIC_USER.get("PWD_ERROR_LIMIT_TIME"));

	/**
	 * 门户是否使用验证码 配合密码错误次数值使用
	 */
	public final static boolean IS_USE_KAPTCHA = Boolean.parseBoolean(PUBLIC_USER.get("IS_USE_KAPTCHA"));

	/**
	 * 密码错误次数值 将 出现验证码，如果值为0 则永远不会出现验证码
	 */
	public final static Integer PWD_TIMES_USE_KAPTCHA = Integer.parseInt(PUBLIC_USER.get("PWD_TIMES_USE_KAPTCHA"));

	/**
	 * 是否使用CFCA密码控件
	 */
	public final static boolean USE_KEYBOARD = Boolean.parseBoolean(PUBLIC_USER.get("USE_KEYBOARD"));

	/**
	 * 是否启用CFCA数字证书
	 */
	public final static boolean USE_SECURITYCENTER = Boolean.parseBoolean(PUBLIC_USER.get("USE_SECURITYCENTER"));

	/** CA service ip */
	public static String CA_SERVER_IP;
	/** CA服务端口 */
	public static String CA_SERVERPORT;
	/** CA证书路径 */
	public static String CA_KEYSTORE_PATH;
	/** CA证书口令 */
	public static String CA_KEYSTORE_PWD;

	static {
		if (USE_SECURITYCENTER) {
			CA_SERVER_IP = PUBLIC_USER.get("CA_SERVER_IP");
			CA_SERVERPORT = PUBLIC_USER.get("CA_SERVERPORT");
			CA_KEYSTORE_PATH = PUBLIC_USER.get("CA_KEYSTORE_PATH");
			CA_KEYSTORE_PWD = PUBLIC_USER.get("CA_KEYSTORE_PWD");
		}
	}
	
	/**
	 * 是否开发状态
	 */
	public final static boolean IS_DEV_STATUS = Boolean.parseBoolean(PUBLIC_USER.get("IS_DEV_STATUS"));

	/**
	 * 是否 是 域名 + 应用名
	 */
	public final static boolean IS_USE_DOMAIN_NAME = Boolean.parseBoolean(PUBLIC_USER.get("IS_USE_DOMAIN_NAME"));

	/**
	 * 是否采用SSL协议
	 */
	public final static boolean IS_SSL = Boolean.parseBoolean(PUBLIC_USER.get("IS_SSL"));

	/**
	 * 是否需要注册功能
	 */
	public final static boolean PORTAL_IS_REGISTER = Boolean.parseBoolean(PUBLIC_USER.get("PORTAL_IS_REGISTER"));

	/**
	 * SFTP IP
	 */
	public final static String SFTP_IP = PUBLIC_USER.get("SFTP_IP");

	/**
	 * SFTP PORT
	 */
	public final static Integer SFTP_PORT = Integer.parseInt(PUBLIC_USER.get("SFTP_PORT"));

	/**
	 * SFTP DIR
	 */
	public final static String SFTP_DIR = PUBLIC_USER.get("SFTP_DIR");

	/**
	 * SFTP SFTP_USER
	 */
	public final static String SFTP_USER = PUBLIC_USER.get("SFTP_USER");

	/**
	 * SFTP SFTP_PWD
	 */
	public final static String SFTP_PWD = PUBLIC_USER.get("SFTP_PWD");

	/**
	 * 余额支付产品编号
	 */
	public final static String BALANCE_PAY = PUBLIC_USER.get("BALANCE_PAY");

	/**
	 * 会员同时登录最大限制
	 */
	public final static Integer MEMBER_BIGGEST_LOGINS = Integer.parseInt(PUBLIC_USER.get("MEMBER_BIGGEST_LOGINS"));

	/**
	 * 商户同时登录最大限制
	 */
	public final static Integer MERCHANT_BIGGEST_LOGINS = Integer.parseInt(PUBLIC_USER.get("MERCHANT_BIGGEST_LOGINS"));

	/**
	 * 充值限制金额
	 */
	public final static Integer RECHARGE_LIMIT_AMOUNT = Integer.parseInt(PUBLIC_USER.get("RECHARGE_LIMIT_AMOUNT"));

	/**
	 * 充值商户号
	 */
	public final static String RECHARGE_MERCHANTNO = PUBLIC_USER.get("RECHARGE_MERCHANTNO");

	/**
	 * ================================================================ 平台信息，
	 * 分割线 ================================================================
	 */

	/**
	 * 公司名称
	 */
	public final static String COMPANY_NAME = PUBLIC_USER.get("COMPANY_NAME");

	/**
	 * 公司简称
	 */
	public final static String COMPANY_FOR = PUBLIC_USER.get("COMPANY_FOR");

	/**
	 * 公司LOGO
	 */
	public final static String COMPANY_LOGO = PUBLIC_USER.get("COMPANY_LOGO");

	/**
	 * 公司 ICP
	 */
	public final static String COMPANY_NET_ICP = PUBLIC_USER.get("COMPANY_NET_ICP");

	/**
	 * 公司 Address
	 */
	public final static String COMPANY_ADDRESS = PUBLIC_USER.get("COMPANY_ADDRESS");

	/**
	 * 公司 TEL
	 */
	public final static String COMPANY_TEL = PUBLIC_USER.get("COMPANY_TEL");

	/**
	 * 公司 email
	 */
	public final static String COMPANY_EMAIL = PUBLIC_USER.get("COMPANY_EMAIL");

	/**
	 * 公司 HR email
	 */
	public final static String COMPANY_HR_EMAIL = PUBLIC_USER.get("COMPANY_HR_EMAIL");

	/**
	 * 公司 About us
	 */
	public final static String COMPANY_ABOUT_US = PUBLIC_USER.get("COMPANY_ABOUT_US");

	/**
	 * POS 商户结算周期
	 */
	public final static Integer POS_SETTL_CYCLEDATA = Integer.parseInt(PUBLIC_USER.get("POS_SETTL_CYCLEDATA"));

	/**
	 * POS商户 风险预存期天数
	 */
	public final static Integer POS_RISKDAY = Integer.parseInt(PUBLIC_USER.get("POS_RISKDAY"));

}
