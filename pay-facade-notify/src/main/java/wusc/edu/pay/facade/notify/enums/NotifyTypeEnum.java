package wusc.edu.pay.facade.notify.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知类型
 * 
 * @author Administrator
 * 
 */
public enum NotifyTypeEnum {

	/**
	 * 商户通知
	 */
	MERCHANT("商户通知", 1),

	/**
	 * POS消费
	 */
	POSP_PAY("POS消费", 2),

	/**
	 * POS退款成功
	 */
	POSP_REFUND_SUCCESS("POS退款成功", 21),

	/**
	 * 成本计费
	 */
	COST("成本计费", 3),

	/**
	 * 用户计费
	 */
	FEE("用户计费", 4),

	/**
	 * 打款创建
	 */
	REMIT_CREATE("打款创建", 6),

	/**
	 * 短信
	 */
	SMS("短信", 7),

	/**
	 * 邮件
	 */
	MAIL("邮件", 8),

	/**
	 * 代理商分润
	 */
	AGENT_SPLIT_GROFIT("代理商分润", 10),

	/**
	 * 代理商分润退回
	 */
	AGENT_SPLIT_GROFIT_BACK("代理商分润退回", 11),

	/**
	 * POS退款取消
	 */
	POSP_REFUND_CANCEL("POS退款取消", 12),

	/**
	 * 打款完成
	 */
	REMIT_COMPLETE("打款完成", 13),

	/**
	 * POS退款创建
	 */
	POSP_REFUND_CREATE("POS退款创建", 14), ;

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	/** 构造函数 */
	private NotifyTypeEnum(String desc, int value) {
		this.desc = desc;
		this.value = value;
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

	public static NotifyTypeEnum getEnum(int value) {
		NotifyTypeEnum resultEnum = null;
		NotifyTypeEnum[] enumAry = NotifyTypeEnum.values();
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
		NotifyTypeEnum[] ary = NotifyTypeEnum.values();
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
