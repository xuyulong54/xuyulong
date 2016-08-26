package wusc.edu.pay.web.portal.consts;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.utils.string.StringUtil;


public class SmsProperties {
	private static final Log log = LogFactory.getLog(SmsProperties.class);

	// 短信模板
	public static String SMS_PHONE_REGISTER = "您正在申请手机号码注册，验证码是{0}。如非本人操作，请致电{1}";
	public static String SMS_IDENTIFICATION = "您正在申请手机号码验证身份，验证码是{0}。如非本人操作，请致电{1}";
	public static String SMS_BING_PHONE = "您正在绑定手机，验证码是{0}，请于{1}分钟内输入，工作人员不会向您索取，请勿泄露";
	public static String SMS_LOOKFOR_LOGINPWD = "您正在找回登录密码，验证码是{0}，请于{1}分钟内输入，工作人员不会向您索取，请勿泄露";
	public static String SMS_INCOME_MONEY = "您的账户{0}于{1}月{2}日收入人民币{3}元，付款方：{4}";
	public static String SMS_UNBING_PHONE = "您正在解绑手机，验证码是{0}，请于{1}分钟内输入，工作人员不会向您索取，请勿泄露";
	public static String SMS_LOOKFOR_TRADEPWD = "您正在找回支付密码，验证码是{0}，请于{1}分钟内输入，工作人员不会向您索取，请勿泄露";
	public static String SMS_ADD_CA = "您正在申请数字证书，验证码是{0}，请于{1}分钟内输入，工作人员不会向您索取，请勿泄露";
	public static String SMS_UPDATE_MERCHANTINFO = "您正在修改商户基本信息，验证码是{0}，请于{1}分钟内输入，工作人员不会向您索取，请勿泄露";
	public static String SMS_CANCELACCOUNT = "您正在申请销户，验证码是{0}，请于{1}分钟内输入，工作人员不会向您索取，请勿泄露";
	public static String SMS_KEY_VIEW = "您正在查看商户密钥，验证码是{0}，请于{1}分钟内输入，工作人员不会向您索取，请勿泄露";
	public static String SMS_RESETLOGPWD = "您于{0}月{1}日 {2}：{3}成功为操作员{4}重置密码";
	public static String SMS_RECHARGE = "您的账户{0}于{1}月{2}日 {3}：{4}成功充值{5}元，可用余额{6}元";
	public static String SMS_CONSUMPTION_MONEY = "您的账户{0}于{1}月{2}日 {3}：{4}成功支出{5}元。如有问题，请致电{6}";
	public static String SMS_WITHDRAW = "您的账户{0}于{1}月{2}日 {3}：{4}提现人民币{5}元到{6}银行账户(尾号{7}),请以实际入账信息为准。如有问题，请致电{8}";
	public static String SMS_SETT = "您的账户{0}于{1}月{2}日 {3}：{4}结算人民币{5}元到{6}银行账户(尾号{7}),请以实际入账信息为准。如有问题，请致电{8}";
	public static String SMS_REFUND = "您的退款申请已提交，退款金额{0}元，请等待银行处理。如有问题，请致电{1}";
	public static String SMS_UPDATE_MERCHANT_SUCC = "您的账户{0}商户信息已修改。如有问题，请致电{1}";
	public static String SMS_MNT = "您的交易操作已经触发风控规则，请谨慎交易。严重影响支付平台交易的，平台将采取相关措施。";
	public static String SMS_OPEN_FASTPAY = "您正在开通快捷支付，验证码是{0}，请于{1}分钟内输入，工作人员不会向您索取，请勿泄露";
	public static String SMS_RECHARGE_SUCC = "您的账户{0}于{1}月{2}日 {3}：{4}充值失败，充值金额{5}元已退回，请留意中益智正账户及银行卡余额。";
	public static String SMS_II ="您的卡片（尾号{0}）于{1}成功{2}{3}元，可用余额{4}元。如有问题，请致电020-29198784";
	

	/**
	 * 只加载一次.
	 */
	static {
		try {
			log.info("=== load sms.properties and init sys param");
			InputStream proFile = Thread.currentThread().getContextClassLoader().getResourceAsStream("sms.properties");
			Properties props = new Properties();
			props.load(proFile);
			init(props);
		} catch (Exception e) {
			log.error("=== load and init sms.properties exception:" + e);
		}
	}

	private static void init(Properties props) {
		String sms_ii = props.getProperty(SMS_II);
		if (StringUtil.isNotBlank(sms_ii)) {
		SMS_II=sms_ii;
		}
		String sms_phone_register = props.getProperty(SMS_PHONE_REGISTER);
		if (StringUtil.isNotBlank(sms_phone_register)) {
			SMS_PHONE_REGISTER = sms_phone_register;
		}
		String sms_identification = props.getProperty(SMS_IDENTIFICATION);
		if (StringUtil.isNotBlank(sms_identification)) {
			SMS_IDENTIFICATION = sms_identification;
		}
		String sms_bing_phone = props.getProperty(SMS_BING_PHONE);
		if (StringUtil.isNotBlank(sms_bing_phone)) {
			SMS_BING_PHONE = sms_bing_phone;
		}
		String sms_lookfor_loginpwd = props.getProperty(SMS_LOOKFOR_LOGINPWD);
		if (StringUtil.isNotBlank(sms_lookfor_loginpwd)) {
			SMS_LOOKFOR_LOGINPWD = sms_lookfor_loginpwd;
		}
		String sms_income_money = props.getProperty(SMS_INCOME_MONEY);
		if (StringUtil.isNotBlank(sms_income_money)) {
			SMS_INCOME_MONEY = sms_income_money;
		}
		String sms_unbing_phone = props.getProperty(SMS_UNBING_PHONE);
		if (StringUtil.isNotBlank(sms_unbing_phone)) {
			SMS_UNBING_PHONE = sms_unbing_phone;
		}
		String sms_lookfor_tradepwd = props.getProperty(SMS_LOOKFOR_TRADEPWD);
		if (StringUtil.isNotBlank(sms_lookfor_tradepwd)) {
			SMS_LOOKFOR_TRADEPWD = sms_lookfor_tradepwd;
		}
		String sms_add_ca = props.getProperty(SMS_ADD_CA);
		if (StringUtil.isNotBlank(sms_add_ca)) {
			SMS_ADD_CA = sms_add_ca;
		}
		String sms_update_merchantinfo = props.getProperty(SMS_UPDATE_MERCHANTINFO);
		if (StringUtil.isNotBlank(sms_update_merchantinfo)) {
			SMS_UPDATE_MERCHANTINFO = sms_update_merchantinfo;
		}
		String sms_cancelaccount = props.getProperty(SMS_CANCELACCOUNT);
		if (StringUtil.isNotBlank(sms_cancelaccount)) {
			SMS_CANCELACCOUNT = sms_cancelaccount;
		}
		String sms_key_view = props.getProperty(SMS_KEY_VIEW);
		if (StringUtil.isNotBlank(sms_key_view)) {
			SMS_KEY_VIEW = sms_key_view;
		}
		String sms_resetlogpwd = props.getProperty(SMS_RESETLOGPWD);
		if (StringUtil.isNotBlank(sms_resetlogpwd)) {
			SMS_RESETLOGPWD = sms_resetlogpwd;
		}
		String sms_recharge = props.getProperty(SMS_RECHARGE);
		if (StringUtil.isNotBlank(sms_recharge)) {
			SMS_RECHARGE = sms_recharge;
		}
		String sms_consumption_money = props.getProperty(SMS_CONSUMPTION_MONEY);
		if (StringUtil.isNotBlank(sms_consumption_money)) {
			SMS_CONSUMPTION_MONEY = sms_consumption_money;
		}
		String sms_withdraw = props.getProperty(SMS_WITHDRAW);
		if (StringUtil.isNotBlank(sms_withdraw)) {
			SMS_WITHDRAW = sms_withdraw;
		}
		String sms_sett = props.getProperty(SMS_SETT);
		if (StringUtil.isNotBlank(sms_sett)) {
			SMS_SETT = sms_sett;
		}
		String sms_refund = props.getProperty(SMS_REFUND);
		if (StringUtil.isNotBlank(sms_refund)) {
			SMS_REFUND = sms_refund;
		}
		String sms_update_merchant_succ = props.getProperty(SMS_UPDATE_MERCHANT_SUCC);
		if (StringUtil.isNotBlank(sms_update_merchant_succ)) {
			SMS_UPDATE_MERCHANT_SUCC = sms_update_merchant_succ;
		}
		String sms_mnt = props.getProperty(SMS_MNT);
		if (StringUtil.isNotBlank(sms_mnt)) {
			SMS_MNT = sms_mnt;
		}
		String sms_open_fastpay = props.getProperty(SMS_OPEN_FASTPAY);
		if (StringUtil.isNotBlank(sms_open_fastpay)) {
			SMS_OPEN_FASTPAY = sms_open_fastpay;
		}
		String sms_recharge_succ = props.getProperty(SMS_RECHARGE_SUCC);
		if (StringUtil.isNotBlank(sms_recharge_succ)) {
			SMS_RECHARGE_SUCC = sms_recharge_succ;
		}
	}

	/*public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("SMS_II");
		list.add("MDF_PWD");
		list.add("SMS_URL");
		list.add("SEND_ACTION");
		list.add("SP_CODE");
		list.add("ACCOUNT");
		list.add("PASSWORD");
		for (String i : list) {
			String ii = i;
			String jj = ii.toLowerCase();
			System.out.println("String " + jj + " = props.getProperty(" + ii + ");");
			System.out.println("if (StringUtils.isNotBlank(" + jj + ")) {");
			System.out.println(ii + "=" + jj + ";");
			System.out.println("}");
		}
	}*/

	public String getValue(String name) {
		String proname = name;
		try {
			Field field = this.getClass().getField(proname);
			return field.get(this).toString();
		} catch (Exception ex) {
			return "";
		}
	}

}
