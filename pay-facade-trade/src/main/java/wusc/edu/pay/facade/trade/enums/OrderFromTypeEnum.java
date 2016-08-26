package wusc.edu.pay.facade.trade.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @描述: 订单来源枚举.
 * @作者: WuShuicheng.
 * @创建: 2014-7-11,下午1:53:58
 * @版本: V1.0
 * 
 */
public enum OrderFromTypeEnum {

	/**
	 * 支付网关B2C
	 */
	GATEWAY_B2C("支付网关B2C", 1),
	/**
	 * 支付网关B2B
	 */
	GATEWAY_B2B("支付网关B2B", 2),
	/**
	 * 充值
	 */
	RECHARGE("充值", 3),
	/**
	 * 门户
	 */
	PORTAL("门户", 4),
	/**
	 * 移动端
	 */
	MOBILE("移动端", 5),

	/**
	 * POS
	 */
	POS("POS", 6);

	/* 描述 */
	private String desc;
	/* 枚举值 */
	private int value;

	private OrderFromTypeEnum(String desc, int value) {
		this.desc = desc;
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		OrderFromTypeEnum[] ary = OrderFromTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

}
