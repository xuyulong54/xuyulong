package wusc.edu.pay.facade.user.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @描述: 用户验证码类型枚举.
 * @作者: huqian.
 * @创建时间: 2015-3-2, 上午10:20:04 .
 * @版本: V1.0.
 * 
 */
public enum UserVerificationCodeTypeEnum {
	CHANGE_LOGIN_PASSWORD("修改登录密码", 1), 
	MERCHANT_REG("商户注册", 2), 
	OTHER("其他", 99);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private UserVerificationCodeTypeEnum(String desc, int value) {
		this.desc = desc;
		this.value = value;
	}

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

	public static UserVerificationCodeTypeEnum getEnum(int value) {
		UserVerificationCodeTypeEnum resultEnum = null;
		UserVerificationCodeTypeEnum[] enumAry = UserVerificationCodeTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		UserVerificationCodeTypeEnum[] ary = UserVerificationCodeTypeEnum.values();
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
