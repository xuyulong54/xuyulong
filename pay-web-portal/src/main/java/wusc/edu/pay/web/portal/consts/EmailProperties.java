package wusc.edu.pay.web.portal.consts;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Email系统设置参数.
 * 
 * @author liliqiong
 * @date 2013-11-28
 * @version 1.0
 */
public class EmailProperties {
	/**
	 * logger.
	 */
	private static final Log log = LogFactory.getLog(EmailProperties.class);

	public static String MAIL_MEMBER_RES_MA_VM = "template/email/MemberRegisterEmail.vm";
	public static String MAIL_MEMBER_RES_MA_URL = "http://192.168.88.248:8083/pay-web-portal/memberRegister_setIdentityInfoUI.action?token=";
	public static String MAIL_MEMBER_RES_MA_TIME = "1440";
	public static String MAIL_MEMBER_RES_MA_SUB = "MemberRegister";

	public static String MAIL_MERCHANT_RES_MA_VM = "template/email/MerchantRegisterEmail.vm";
	public static String MAIL_MERCHANT_RES_MA_URL = "http://192.168.88.248:8083/pay-web-portal/merchantRegister_setIdentityInfoUI.action?token=";
	public static String MAIL_MERCHANT_RES_MA_TIME = "1440";
	public static String MAIL_MERCHANT_RES_MA_SUB = "MerchantRegister";

	public static String MAIL_MEMBER_FIND_CODE_LOGIN_MA_VM = "template/email/MemberLookforLoginPwdEmail.vm";
	public static String MAIL_MEMBER_FIND_CODE_LOGIN_MA_URL = "http://192.168.88.248:8083/pay-web-portal/memberLookForLoginPwd_editLoginPwdUI.action?token=";
	public static String MAIL_MEMBER_FIND_CODE_LOGIN_MA_TIME = "1440";
	public static String MAIL_MEMBER_FIND_CODE_LOGIN_MA_SUB = "MemberLookforLoginPwd";

	public static String MAIL_MERCHANT_FIND_CODE_LOGIN_MA_VM = "template/email/MerchantLookforLoginPwdEmail.vm";
	public static String MAIL_MERCHANT_FIND_CODE_LOGIN_MA_URL = "http://192.168.88.248:8083/pay-web-portal/merchantLookForLoginPwd_editLoginPwdUI.action?token=";
	public static String MAIL_MERCHANT_FIND_CODE_LOGIN_MA_TIME = "1440";
	public static String MAIL_MERCHANT_FIND_CODE_LOGIN_MA_SUB = "MerchantLookforLoginPwd";

	public static String MAIL_MEMBER_BANG_MA_VM = "template/email/MemberBangEmail.vm";
	public static String MAIL_MEMBER_BANG_MA_URL = "http://192.168.88.248:8083/pay-web-portal/memberemailbind_bindingActivationEmail.action?token=";
	public static String MAIL_MEMBER_BANG_MA_TIME = "1440";
	public static String MAIL_MEMBER_BANG_MA_SUB = "MemberBang";

	public static String MAIL_MERCHANT_BANG_MA_VM = "template/email/MerchantBangEmail.vm";
	public static String MAIL_MERCHANT_BANG_MA_URL = "http://192.168.88.248:8083/pay-web-portal/merchantemailbind_bindingActivationEmail.action?token=";
	public static String MAIL_MERCHANT_BANG_MA_TIME = "1440";
	public static String MAIL_MERCHANT_BANG_MA_SUB = "MerchantBang";

	public static String MAIL_MEMBER_UNBANG_MA_VM = "template/email/MemberUnBangEmail.vm";
	public static String MAIL_MEMBER_UNBANG_MA_URL = "http://192.168.88.248:8083/pay-web-portal/memberemailbind_unbundlingActivationEmail.action?token=";
	public static String MAIL_MEMBER_UNBANG_MA_TIME = "1440";
	public static String MAIL_MEMBER_UNBANG_MA_SUB = "MemberUnBang";

	public static String MAIL_MERCHANT_UNBANG_MA_VM = "template/email/MerchantUnBangEmail.vm";
	public static String MAIL_MERCHANT_UNBANG_MA_URL = "http://192.168.88.248:8083/pay-web-portal/merchantemailbind_unbundlingActivationEmail.action?token=";
	public static String MAIL_MERCHANT_UNBANG_MA_TIME = "1440";
	public static String MAIL_MERCHANT_UNBANG_MA_SUB = "MerchantUnBang";

	public static String MAIL_MEMBER_LOOKFOR_TRADEPWD_MA_VM = "template/email/MemberLookforTradePwd.vm";
	public static String MAIL_MEMBER_LOOKFOR_TRADEPWD_MA_URL = "http://192.168.88.248:8083/pay-web-portal/memberlookfortradepwd_editTradePwdUI.action?token=";
	public static String MAIL_MEMBER_LOOKFOR_TRADEPWD_MA_TIME = "1440";
	public static String MAIL_MEMBER_LOOKFOR_TRADEPWD_MA_SUB = "MemberLookforTradePwd";

	public static String MAIL_MERCHANT_LOOKFOR_TRADEPWD_MA_VM = "template/email/MerchantLookforTradePwd.vm";
	public static String MAIL_MERCHANT_LOOKFOR_TRADEPWD_MA_URL = "http://192.168.88.248:8083/pay-web-portal/merchantlookfortradepwd_editTradePwdUI.action?token=";
	public static String MAIL_MERCHANT_LOOKFOR_TRADEPWD_MA_TIME = "1440";
	public static String MAIL_MERCHANT_LOOKFOR_TRADEPWD_MA_SUB = "MerchantLookforTradePwd";

	/**
	 * 只加载一次.
	 */
	static {
		try {
			log.info("=== load email.properties and init sys param");
			InputStream proFile = Thread.currentThread().getContextClassLoader().getResourceAsStream("email.properties");
			Properties props = new Properties();
			props.load(proFile);
			init(props);
		} catch (Exception e) {
			log.error("=== load and init email.properties exception:" + e);
		}
	}

	/**
	 * 根据配置文件初始化静态变量值.
	 * 
	 * @param props
	 */
	private static void init(Properties props) {


		String mail_member_res_ma_vm = props.getProperty(MAIL_MEMBER_RES_MA_VM);
		if (StringUtils.isNotBlank(mail_member_res_ma_vm)) {
			MAIL_MEMBER_RES_MA_VM = mail_member_res_ma_vm;
		}
		String mail_member_res_ma_url = props.getProperty(MAIL_MEMBER_RES_MA_URL);
		if (StringUtils.isNotBlank(mail_member_res_ma_url)) {
			MAIL_MEMBER_RES_MA_URL = mail_member_res_ma_url;
		}
		String mail_member_res_ma_time = props.getProperty(MAIL_MEMBER_RES_MA_TIME);
		if (StringUtils.isNotBlank(mail_member_res_ma_time)) {
			MAIL_MEMBER_RES_MA_TIME = mail_member_res_ma_time;
		}
		String mail_member_res_ma_sub = props.getProperty(MAIL_MEMBER_RES_MA_SUB);
		if (StringUtils.isNotBlank(mail_member_res_ma_sub)) {
			MAIL_MEMBER_RES_MA_SUB = mail_member_res_ma_sub;
		}
		String mail_merchant_res_ma_vm = props.getProperty(MAIL_MERCHANT_RES_MA_VM);
		if (StringUtils.isNotBlank(mail_merchant_res_ma_vm)) {
			MAIL_MERCHANT_RES_MA_VM = mail_merchant_res_ma_vm;
		}
		String mail_merchant_res_ma_url = props.getProperty(MAIL_MERCHANT_RES_MA_URL);
		if (StringUtils.isNotBlank(mail_merchant_res_ma_url)) {
			MAIL_MERCHANT_RES_MA_URL = mail_merchant_res_ma_url;
		}
		String mail_merchant_res_ma_time = props.getProperty(MAIL_MERCHANT_RES_MA_TIME);
		if (StringUtils.isNotBlank(mail_merchant_res_ma_time)) {
			MAIL_MERCHANT_RES_MA_TIME = mail_merchant_res_ma_time;
		}
		String mail_merchant_res_ma_sub = props.getProperty(MAIL_MERCHANT_RES_MA_SUB);
		if (StringUtils.isNotBlank(mail_merchant_res_ma_sub)) {
			MAIL_MERCHANT_RES_MA_SUB = mail_merchant_res_ma_sub;
		}
		String mail_member_find_code_login_ma_vm = props.getProperty(MAIL_MEMBER_FIND_CODE_LOGIN_MA_VM);
		if (StringUtils.isNotBlank(mail_member_find_code_login_ma_vm)) {
			MAIL_MEMBER_FIND_CODE_LOGIN_MA_VM = mail_member_find_code_login_ma_vm;
		}
		String mail_member_find_code_login_ma_url = props.getProperty(MAIL_MEMBER_FIND_CODE_LOGIN_MA_URL);
		if (StringUtils.isNotBlank(mail_member_find_code_login_ma_url)) {
			MAIL_MEMBER_FIND_CODE_LOGIN_MA_URL = mail_member_find_code_login_ma_url;
		}
		String mail_member_find_code_login_ma_time = props.getProperty(MAIL_MEMBER_FIND_CODE_LOGIN_MA_TIME);
		if (StringUtils.isNotBlank(mail_member_find_code_login_ma_time)) {
			MAIL_MEMBER_FIND_CODE_LOGIN_MA_TIME = mail_member_find_code_login_ma_time;
		}
		String mail_member_find_code_login_ma_sub = props.getProperty(MAIL_MEMBER_FIND_CODE_LOGIN_MA_SUB);
		if (StringUtils.isNotBlank(mail_member_find_code_login_ma_sub)) {
			MAIL_MEMBER_FIND_CODE_LOGIN_MA_SUB = mail_member_find_code_login_ma_sub;
		}
		String mail_merchant_find_code_login_ma_vm = props.getProperty(MAIL_MERCHANT_FIND_CODE_LOGIN_MA_VM);
		if (StringUtils.isNotBlank(mail_merchant_find_code_login_ma_vm)) {
			MAIL_MERCHANT_FIND_CODE_LOGIN_MA_VM = mail_merchant_find_code_login_ma_vm;
		}
		String mail_merchant_find_code_login_ma_url = props.getProperty(MAIL_MERCHANT_FIND_CODE_LOGIN_MA_URL);
		if (StringUtils.isNotBlank(mail_merchant_find_code_login_ma_url)) {
			MAIL_MERCHANT_FIND_CODE_LOGIN_MA_URL = mail_merchant_find_code_login_ma_url;
		}
		String mail_merchant_find_code_login_ma_time = props.getProperty(MAIL_MERCHANT_FIND_CODE_LOGIN_MA_TIME);
		if (StringUtils.isNotBlank(mail_merchant_find_code_login_ma_time)) {
			MAIL_MERCHANT_FIND_CODE_LOGIN_MA_TIME = mail_merchant_find_code_login_ma_time;
		}
		String mail_merchant_find_code_login_ma_sub = props.getProperty(MAIL_MERCHANT_FIND_CODE_LOGIN_MA_SUB);
		if (StringUtils.isNotBlank(mail_merchant_find_code_login_ma_sub)) {
			MAIL_MERCHANT_FIND_CODE_LOGIN_MA_SUB = mail_merchant_find_code_login_ma_sub;
		}
		String mail_member_bang_ma_vm = props.getProperty(MAIL_MEMBER_BANG_MA_VM);
		if (StringUtils.isNotBlank(mail_member_bang_ma_vm)) {
			MAIL_MEMBER_BANG_MA_VM = mail_member_bang_ma_vm;
		}
		String mail_member_bang_ma_url = props.getProperty(MAIL_MEMBER_BANG_MA_URL);
		if (StringUtils.isNotBlank(mail_member_bang_ma_url)) {
			MAIL_MEMBER_BANG_MA_URL = mail_member_bang_ma_url;
		}
		String mail_member_bang_ma_time = props.getProperty(MAIL_MEMBER_BANG_MA_TIME);
		if (StringUtils.isNotBlank(mail_member_bang_ma_time)) {
			MAIL_MEMBER_BANG_MA_TIME = mail_member_bang_ma_time;
		}
		String mail_member_bang_ma_sub = props.getProperty(MAIL_MEMBER_BANG_MA_SUB);
		if (StringUtils.isNotBlank(mail_member_bang_ma_sub)) {
			MAIL_MEMBER_BANG_MA_SUB = mail_member_bang_ma_sub;
		}
		String mail_merchant_bang_ma_vm = props.getProperty(MAIL_MERCHANT_BANG_MA_VM);
		if (StringUtils.isNotBlank(mail_merchant_bang_ma_vm)) {
			MAIL_MERCHANT_BANG_MA_VM = mail_merchant_bang_ma_vm;
		}
		String mail_merchant_bang_ma_url = props.getProperty(MAIL_MERCHANT_BANG_MA_URL);
		if (StringUtils.isNotBlank(mail_merchant_bang_ma_url)) {
			MAIL_MERCHANT_BANG_MA_URL = mail_merchant_bang_ma_url;
		}
		String mail_merchant_bang_ma_time = props.getProperty(MAIL_MERCHANT_BANG_MA_TIME);
		if (StringUtils.isNotBlank(mail_merchant_bang_ma_time)) {
			MAIL_MERCHANT_BANG_MA_TIME = mail_merchant_bang_ma_time;
		}
		String mail_merchant_bang_ma_sub = props.getProperty(MAIL_MERCHANT_BANG_MA_SUB);
		if (StringUtils.isNotBlank(mail_merchant_bang_ma_sub)) {
			MAIL_MERCHANT_BANG_MA_SUB = mail_merchant_bang_ma_sub;
		}

	}

	/*public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("MAIL_HOST");
		list.add("MAIL_PORT");
		list.add("MAIL_USERNAME");
		list.add("MAIL_PASSWORD");
		list.add("MAIL_SMTP_AUTH");
		list.add("MAIL_SMTP_TIMEOUT");
		list.add("MAIL_DEFAULT_FROM");
		
		 * list.add("MAIL_MEMBER_RES_MA_VM");
		 * list.add("MAIL_MEMBER_RES_MA_URL");
		 * list.add("MAIL_MEMBER_RES_MA_TIME");
		 * list.add("MAIL_MEMBER_RES_MA_SUB");
		 * list.add("MAIL_MERCHANT_RES_MA_VM");
		 * list.add("MAIL_MERCHANT_RES_MA_URL");
		 * list.add("MAIL_MERCHANT_RES_MA_TIME");
		 * list.add("MAIL_MERCHANT_RES_MA_SUB");
		 * list.add("MAIL_MEMBER_FIND_CODE_LOGIN_MA_VM");
		 * list.add("MAIL_MEMBER_FIND_CODE_LOGIN_MA_URL");
		 * list.add("MAIL_MEMBER_FIND_CODE_LOGIN_MA_TIME");
		 * list.add("MAIL_MEMBER_FIND_CODE_LOGIN_MA_SUB");
		 * list.add("MAIL_MERCHANT_FIND_CODE_LOGIN_MA_VM");
		 * list.add("MAIL_MERCHANT_FIND_CODE_LOGIN_MA_URL");
		 * list.add("MAIL_MERCHANT_FIND_CODE_LOGIN_MA_TIME");
		 * list.add("MAIL_MERCHANT_FIND_CODE_LOGIN_MA_SUB");
		 * list.add("MAIL_MEMBER_BANG_MA_VM");
		 * list.add("MAIL_MEMBER_BANG_MA_URL");
		 * list.add("MAIL_MEMBER_BANG_MA_TIME");
		 * list.add("MAIL_MEMBER_BANG_MA_SUB");
		 * list.add("MAIL_MERCHANT_BANG_MA_VM");
		 * list.add("MAIL_MERCHANT_BANG_MA_URL");
		 * list.add("MAIL_MERCHANT_BANG_MA_TIME");
		 * list.add("MAIL_MERCHANT_BANG_MA_SUB");
		 
		for (String i : list) {
			String ii = i;
			String jj = ii.toLowerCase();
			System.out.println("String " + jj + " = props.getProperty(" + ii + ");");
			System.out.println("if (StringUtils.isNotBlank(" + jj + ")) {");
			System.out.println(ii + "=" + jj + ";");
			System.out.println("}");
		}
	}*/
}
