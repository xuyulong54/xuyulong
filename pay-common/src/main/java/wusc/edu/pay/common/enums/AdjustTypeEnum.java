package wusc.edu.pay.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 核算类型枚举.
 * 适用于TBL_ACCOUNTING_TEMPLATE_TITLE表.
 * @author Hill
 */
public enum AdjustTypeEnum {
	 SOURCETITLENO("收款方",1),
	 TARGETTITLENO("付款方",2),
	 AGENTTITLENO("代理商",3),
	 BANKTITLENO("银行账户",4),
	 BANKCHANNELCODE("银行渠道编号",5),
	 SPLITTITLENO("分账方",0);

	 /** 描述  */
	 private String desc;
	 /** 枚举值 */
	 private int value;
	 
	 private AdjustTypeEnum(String desc , int value){
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
	 
	public static AdjustTypeEnum getEnum(int value){
		AdjustTypeEnum resultEnum = null;
		AdjustTypeEnum[] enumAry = AdjustTypeEnum.values();
		for(int i = 0;i<enumAry.length;i++){
			if(enumAry[i].getValue() == value){
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList(){
		AdjustTypeEnum[] ary = AdjustTypeEnum.values();
		List list = new ArrayList();
		for(int i=0;i<ary.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value",String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		AdjustTypeEnum[] ary = AdjustTypeEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key,map);
		}
		return enumMap;
	}

}
