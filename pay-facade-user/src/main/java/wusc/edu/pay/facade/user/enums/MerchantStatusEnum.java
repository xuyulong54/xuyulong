package wusc.edu.pay.facade.user.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 商户状态枚举
 * 
 * @author huangbin
 * 
 */
public enum MerchantStatusEnum {

	ACTIVE("激活", 100), INACTIVE("冻结", 101), CREATED("用户已创建", 102), 
	NOPASS("审核不通过", 103),
//	SIGNING("注册中", 104), 
	CLOSE("已注销", 105),FIRSTPASS("初审通过", 106);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private MerchantStatusEnum(String desc, int value) {
		this.value = value;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static MerchantStatusEnum getEnum(int value) {
		MerchantStatusEnum resultEnum = null;
		MerchantStatusEnum[] enumAry = MerchantStatusEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		MerchantStatusEnum[] ary = MerchantStatusEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

	public static Map<String, Map<String, Object>> toMap() {
		MerchantStatusEnum[] ary = MerchantStatusEnum.values();
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
