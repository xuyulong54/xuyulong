package wusc.edu.pay.common.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * 资金调拨记录状态
 * @desc  
 * @author shenjialong
 * @date  2013-11-27,上午9:16:51
 */
public enum FundTransferStatusEnum {
	CREATED("已创建","0"),
	REVIEWED("已复核","1"),
	REFUNDED("已拒绝","2"),
	PAY("已打款","3");
	private String desc;
	private String value;
	
	FundTransferStatusEnum(String desc,String value){
		this.desc = desc;
		this.value = value;
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public static FundTransferStatusEnum getEnum(String value) {
		FundTransferStatusEnum resultEnum = null;
		FundTransferStatusEnum[] enumAry = FundTransferStatusEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		FundTransferStatusEnum[] ary = FundTransferStatusEnum.values();
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
