package wusc.edu.pay.facade.user.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @描述:审核描述.
 * @作者: LiLiQiong.
 * @创建时间: 2014-12-9 上午9:19:34
 * @版本: V1.0.
 * 
 */
public enum AuditDescEnum {

	AuditDesc1("营业执照号有误", 101), 
	AuditDesc2("法人身份证号有误", 102), 
	AuditDesc3("开户名有误", 103), 
	AuditDesc4("开户账号有误", 104), 
	AuditDesc5("商户全称有误", 105), 
	AuditDesc6("营业执照不实", 106),

	AuditDesc7("营业执照模糊", 107), 
	AuditDesc8("开户行证明模糊", 108), 
	AuditDesc9("门头照模糊", 109), 
	AuditDesc10("经营场所照模糊", 110), 
	AuditDesc11("收银台照模糊", 111), 
	AuditDesc12("税务登记证模糊", 112), 
	AuditDesc13("门头照不含商户名", 113), 
	AuditDesc14("五证模糊", 114), 
	AuditDesc15("法人信息与营业执照不符", 115), 
	AuditDesc16("身份证模糊", 116),

	AuditDesc17("申请费率与经营类型费率不符", 117),
	AuditDesc18("手持身份证模糊", 118);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private AuditDescEnum(String desc, int value) {
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

	public static AuditDescEnum getEnum(int value) {
		AuditDescEnum resultEnum = null;
		AuditDescEnum[] enumAry = AuditDescEnum.values();
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
		AuditDescEnum[] ary = AuditDescEnum.values();
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
		AuditDescEnum[] ary = AuditDescEnum.values();
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

	/**
	 * 代理商审核描述
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListAgentAuditDesc() {
		AuditDescEnum[] ary = AuditDescEnum.values();
		List list = new ArrayList();
		int value[] = { 101, 102, 103, 104, 105, 106 }; // 代理商审核描述
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for (int v : value) {
				if (val == v) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("value", String.valueOf(val));
					map.put("desc", ary[i].getDesc());
					list.add(map);
				}
			}
		}
		return list;
	}

	/**
	 * 商户审核描述(一审)
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListMerchantOneAuditDesc() {
		AuditDescEnum[] ary = AuditDescEnum.values();
		List list = new ArrayList();
		int value[] = { 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116,118 }; // 商户审核描述
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for (int v : value) {
				if (val == v) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("value", String.valueOf(val));
					map.put("desc", ary[i].getDesc());
					list.add(map);
				}
			}
		}
		return list;
	}

	/**
	 * 商户审核描述(二审)
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListMerchantTwoAuditDesc() {
		AuditDescEnum[] ary = AuditDescEnum.values();
		List list = new ArrayList();
		int value[] = { 117 }; // 商户审核描述
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for (int v : value) {
				if (val == v) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("value", String.valueOf(val));
					map.put("desc", ary[i].getDesc());
					list.add(map);
				}
			}
		}
		return list;
	}
}
