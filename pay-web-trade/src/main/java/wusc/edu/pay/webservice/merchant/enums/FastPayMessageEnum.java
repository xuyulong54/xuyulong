package wusc.edu.pay.webservice.merchant.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 快捷支付相关状态代码及返回信息
 * */
public enum FastPayMessageEnum {
	
	FASTPAY_SUCCESS(5000,"成功"),
	FASTPAY_ERRPHONENUM(5001,"该手机号码已绑定"),
	FASTPAY_PSWERR(5002,"会员密码错误"),
	FASTPAY_PSWFORMATERR(5003,"密码格式不正确"),
	FASTPAY_SYSTEMERR(5004,"系统异常"),
	FASTPAY_DATASOURCEERR(5005,"解密验签失败，非法数据"),
	FASTPAY_ERRPOHNECODE(5006,"手机验证码错误"),
	FASTPAY_NULLPSW(5007,"登录密码为空"),
	FASTPAY_NULLLOGINNAME(5008,"登录名为空"),
	FASTPAY_NULLMEMBER(5009,"不存在该会员"),
	FASTPAY_ERRSTATUS(5010,"会员状态不正常"),
	FASTPAY_NULLMERCHANT(5011,"不存在该商户"),
	FASTPAY_MERCHANTBUSERR(5012,"商户业务设置异常"),
	FASTPAY_BINDINGCARDFAIL(5013,"绑定银行卡失败"),
	FASTPAY_ERRPSW(5014,"登录密码两次输入不相同"),
	FASTPAY_PAYFAIL(5015,"支付失败"),
	FASTPAY_LOGINNAMEDATAFORMATERR(5017,"登录名格式有误"),
	FASTPAY_ERRBANKACCOUNTNO(5018,"银行卡账号有误"),
	FASTPAY_NULLBINDCARD(5019,"没有已绑定的银行卡"),
	FASTPAY_CREATORDERERR(5020,"创建订单记录失败"),
	FASTPAY_SENDMSGCODEERR(5021,"发送短信验证码失败"),
	FASTPAY_NULLPAYPSW(5022,"支付密码为空"),
	FASTPAY_ERRPAYPSW(5023,"支付密码两次输入不一样"),
	FASTPAY_EQUALPAYPSWANDPSW(5024,"登录密码与支付密码相同"),
	FASTPAY_NULLORDER(5025,"订单不存在"),
	FASTPAY_AMOUNTERR(5026,"订单金额有误"),
	FASTPAY_DATAERR(5027,"非法数据"),
	FASTPAY_PSYPSWERR(5028,"支付密码有误"),
	FASTPAY_MERCHANT_DANGERTRADE(5029,"该商户存在风险"),
	FASTPAY_BANKNOCF(5030,"该银行卡已经被绑定"),
	FASTPAY_PAYPSWFORMATERR(5031,"支付密码格式不正确"),
	FASTPAY_ERRORDER(5032,"异常订单"),
	FASTPAY_IDCARDERR(5033,"身份证号错误"),
	FASTPAY_PHONENUMERR(5034,"手机号码有误"),
	FASTPAY_BANKACCOUNTTYPEERR(5035,"银行卡类型有误"),
	FASTPAY_CURERR(5036,"币种有误"),
	FASTPAY_GOODSNAMEERR(5037,"商品名称有误"),
	FASTPAY_CHANNELERR(5038,"支付渠道有误"),
	FASTPAY_BINDTYPEERR(5039,"绑定类型有误"),
	FASTPAY_CARDTYPEERR(5040,"证件类型有误"),
	FASTPAY_REALNAMEERR(5041,"真实姓名有误"),
	FASTPAY_EFFECTIVEDATEERR(5042,"有效日期有误"),
	FASTPAY_CVV2ERR(5043,"CVV2号码有误"),
	FASTPAY_OPERATETYPEERR(5044,"操作系统有误"),
	FASTPAY_OTAADDRESSERR(5045,"手机浏览器地址为空"),
	FASTPAY_UDIDERR(5046,"手机唯一标识为空"),
	FASTPAY_MEMBER_DANGERTRADE(5047,"会员交易存在风险"),
	FASTPAY_DANGERTRADE(5048,"交易存在风险");
	
	
	private int value;
	private String desc;
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private FastPayMessageEnum(int value , String desc){
		this.value = value;
		this.desc = desc;
	}
	
	public static FastPayMessageEnum getEnum(int value) {
		FastPayMessageEnum resultEnum = null;
		FastPayMessageEnum[] enumAry = FastPayMessageEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		FastPayMessageEnum[] ary = FastPayMessageEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}
}
