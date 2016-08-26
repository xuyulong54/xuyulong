package wusc.edu.pay.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 投诉与建议 帮助类型枚举
 * @author xiehui
 * @time 2013-9-17,下午3:33:18
 */
public enum HelpTypeEnum {

	COMPLAINT("投诉",41),
	LOGIN("登录注册",42),
	TRANSFER_ACCOUNTS("转账",43),
	RECHARGE("充值",44),
	WITHDRAW_DEPOSIT("提现",45),
	SETTLE_ACCOUNTS("结算",46),
	AUTONYM("实名认证",47),
	DC("数字证书",49),
	PASSWORD_MANAGEMENT("密码管理",50),
	MOBILE_PHONE("手机绑定",51),
	EMAIL("邮箱绑定",52),
	OTHER("其他",53);
	
	/** 枚举值 */
	private int value;  
	/** 描述 */
	private String desc;
	
	private HelpTypeEnum(String desc,int value) {
		this.value = value;
		this.desc = desc;
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

	public static HelpTypeEnum getEnum(int value){
		HelpTypeEnum resultEnum=null;
		HelpTypeEnum[] enumAry=HelpTypeEnum.values();
		for(int i=0;i<enumAry.length;i++){
			if(enumAry[i].getValue()==value){
				resultEnum=enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		HelpTypeEnum[] ary = HelpTypeEnum.values();
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList(){
		HelpTypeEnum[] ary = HelpTypeEnum.values();
		List list = new ArrayList();
		for(int i=0;i<ary.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value",String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 取枚举的json字符串
	 * @return
	 */
	public static String getJsonStr(){
		HelpTypeEnum[] enums = HelpTypeEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (HelpTypeEnum senum : enums) {
			if(!"[".equals(jsonStr.toString())){
				jsonStr.append(",");
			}
			jsonStr.append("{id:'")
					.append(senum.toString())
					.append("',desc:'")
					.append(senum.getDesc())
					.append("',value:'")
					.append(senum.getValue())
					.append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}

}
