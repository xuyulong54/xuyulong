package wusc.edu.pay.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：文章类型枚举
 * 
 * @author: huangbin
 * @date： 日期：2013-12-3 时间：下午3:02:03
 * @version 1.0
 */
public enum ArticleTypeEnum {

	ARTICLE("企业动态", 1), 
	RECRUITMENT("招聘信息", 2), 
	ASKEDQUESTION("常见问题", 3), 
	PRODUCT("产品发布", 4),
	NOTICE("公告/通知", 5);

	/** 枚举值 */
	private int value;
	/** 描述 */
	private String desc;

	private ArticleTypeEnum(String desc, int value) {
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

	public static ArticleTypeEnum getEnum(int value) {
		ArticleTypeEnum resultEnum = null;
		ArticleTypeEnum[] enumAry = ArticleTypeEnum.values();
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
		ArticleTypeEnum[] ary = ArticleTypeEnum.values();
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
		ArticleTypeEnum[] ary = ArticleTypeEnum.values();
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
	 * 取枚举的json字符串
	 * 
	 * @return
	 */
	public static String getJsonStr() {
		ArticleTypeEnum[] enums = ArticleTypeEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (ArticleTypeEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum.toString()).append("',desc:'").append(senum.getDesc()).append("',value:'").append(senum.getValue()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}

}
