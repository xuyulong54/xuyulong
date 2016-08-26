package wusc.edu.pay.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交易码枚举. 适用于所有表.
 * 
 * @author Hill
 */
public enum TrxTypeEnum {
	
	/** 分账:1001 **/
	SPLIT("分账", 1001),
	
	/** 会员转账:2001 **/
	ACCOUNT_TRANSFER("会员转账", 2001),
	
	/** 会员充值:3001 **/
	ACCOUNT_DEPOSIT("会员充值", 3001),
	
	/** 退款:4000 **/
	REFUND("退款", 4000), 
	
	/** B2C网银退款:4001 **/
	NET_B2C_REFUND("B2C网银退款", 4001), 
	
	/** B2B网银退款:4002 **/
	NET_B2B_REFUND("B2B网银退款", 4002), 
	
	/** 充值失败退款:4003 **/
	DEPOSIT_FAILD_REFUND("充值失败退款", 4003), 
	
	/** 快捷支付退款:4004 **/
	FAST_REFUND("快捷支付退款", 4004), 
	
	/** 会员余额支付退款:4005 **/
	ACCOUNT_BALANCE_REFUND("会员余额支付退款", 4005),

	/** 消费撤销:4006 **/
	POS_REVOKE("消费撤销", 4006), 
	
	/** 消费冲正:4007 **/
	POS_PAY_RUSH("消费冲正", 4007), 
	
	/** 退货:4008 **/
	POS_REFUND("退货", 4008), 
	
	/** 预授权完成撤销:4009 **/
	POS_PREAUTHED_REVOKE("预授权完成撤销", 4009), 
	
	/** 预授权完成冲正:4010 **/
	POS_PREAUTHED_RUSH("预授权完成冲正", 4010), 
	
	/** POS充值:4013 **/
	POS_RECHARGE("POS充值", 4013),
	
	/** 结算:5001 **/
	SETTLEMENT("结算", 5001), 
	
	/** 提现:5002 **/
	ATM("提现", 5002),
	
	/** 结算到账:5003 **/
	SETTLEMENT_INTO("结算到账",5003),
	
	/** 提现到账:5004 **/
	ATM_INTO("提现到账",5004),

	/** 收款:6000 **/
	PAY("收款", 6000), // 交易查询时，不需要列出
	
	/** B2C银行卡支付:6001 **/
	NET_B2C_PAY("B2C银行卡支付", 6001), 
	
	/** B2B银行卡支付:6002 **/
	NET_B2B_PAY("B2B银行卡支付", 6002), 
	
	/** 快捷支付:6004 **/
	FAST_PAY("快捷支付", 6004), 
	
	/** 余额支付:6005 **/
	ACCOUNT_BALANCE_PAY("余额支付",	6005),

	/** 消费:6006 **/
	POS_PAY("消费", 6006), 
	
	/** 预授权:6010 **/
	POS_PREAUTH("预授权", 6010), 
	
	/** 预授权完成:6007 **/
	POS_PREAUTHED("预授权完成", 6007),
	
	/** 消费撤销冲正:6008 **/
	POS_PAY_REVOKE_RUSH("消费撤销冲正", 6008), 
	
	/** 预授权完成撤销冲正:6009 **/
	POS_PREAUTHED_REVOKE_RUSH("预授权完成撤销冲正", 6009),

	/** 账户调账:9001 **/
	ACCOUNT_ADJUST("账户调账", 9001),
	
	ACCOUNT_QUERY("余额查询", 9002),
	
	/*会计中的11：调账*/
	/** 商户认账:1101 **/
	MERCHANT_RECON("商户认账",1101),
	
	/** 银行长款平台认账:1102 **/
	BANK_MORE_PLAT_RECON("银行长款平台认账",1102),
	
	/** 银行短款平台认账:1103 **/
	BANK_LESS_PLAT_RECON("银行短款平台认账",1103),
	
	/** 银行长款金额不符银行认账:1104 **/
	BANK_MORE_NOT_MATCH_BANK_RECON("银行长款金额不符银行认账",1104),
	
	CASH_PAY_RECON("现金支付入账",1105);

	/** 枚举值 */
	private int value;
	/** 描述 */
	private String desc;

	private TrxTypeEnum(String desc, int value) {
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

	public static TrxTypeEnum getEnum(int value) {
		TrxTypeEnum resultEnum = null;
		TrxTypeEnum[] enumAry = TrxTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		TrxTypeEnum[] ary = TrxTypeEnum.values();
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
	public static List toList() {
		TrxTypeEnum[] ary = TrxTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 支付方式类型
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListForPayWay() {
		TrxTypeEnum[] ary = TrxTypeEnum.values();
		List list = new ArrayList();
		int value[] = {6001,6002,6004 } ; //在线交易类型
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for(int v:value){
				if(val==v){
					Map<String, String> map = new HashMap<String, String>();
					map.put("value",TrxTypeEnum.getEnum(val).name());
					map.put("desc", ary[i].getDesc());
					list.add(map);
				}
			}
		}
		return list;
	}
	
	/**
	 * 计费系统---支付方式类型----枚举
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListForPayWayEnum() {
		TrxTypeEnum[] ary = TrxTypeEnum.values();
		List list = new ArrayList();
		int value[] = {6001,6002,6004,6005, 3001 } ; //支付类型
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for(int v:value){
				if(val==v){
					Map<String, String> map = new HashMap<String, String>();
					map.put("value",String.valueOf(val));
					map.put("desc", ary[i].getDesc());
					list.add(map);
				}
			}
		}
		return list;
	}
	
	/**
	 * 计费系统---转账、结算等方式类型----枚举
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListForCalOther() {
		TrxTypeEnum[] ary = TrxTypeEnum.values();
		List list = new ArrayList();
		int value[] = {2001, 4001, 4002, 4003, 4004, 4005, 5001, 5002 } ; //计费其他类型
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for(int v:value){
				if(val==v){
					Map<String, String> map = new HashMap<String, String>();
					map.put("value",String.valueOf(val));
					map.put("desc", ary[i].getDesc());
					list.add(map);
				}
			}
		}
		return list;
	}
	
	/**
	 * 商户可选的业务类型
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListForMerchantBusType() {
		TrxTypeEnum[] ary = TrxTypeEnum.values();
		List list = new ArrayList();
		int value[] = {2001, 3001, 4000, 5001, 6000};
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for(int v:value){
				if(val==v){
					Map<String, String> map = new HashMap<String, String>();
					map.put("value",TrxTypeEnum.getEnum(val).name());
					map.put("desc", ary[i].getDesc());
					list.add(map);
				}
			}
		}
		return list;
	}

	/**
	 * 取枚举的json字符串
	 * 
	 * @return
	 */
	public static String getJsonStr() {
		TrxTypeEnum[] enums = TrxTypeEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (TrxTypeEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("',value:'")
					.append(senum.getValue()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}
	
	/**
	 * 在线交易类型枚举
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListForOnline() {
		TrxTypeEnum[] ary = TrxTypeEnum.values();
		List list = new ArrayList();
		int value[] = { 2001, 3001, 4001, 4002, 4003, 4004, 4005, 5002, 5004, 6001, 6002, 6005, 7004, 8002, 8003, 9001,5001,5003 } ; //在线交易类型
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for(int v:value){
				if(val==v){
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
	 * POS交易类型枚举
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toListForPos() {
		TrxTypeEnum[] ary = TrxTypeEnum.values();
		List list = new ArrayList();
		int value[] = { 4006, 4007, 4008, 4009, 4010, 4013, 6006, 6007, 6008,6009, 6010, 7004, 8002, 8003,5001,5003 } ; //POS交易类型
		for (int i = 0; i < ary.length; i++) {
			int val = ary[i].getValue();
			for(int v:value){
				if(val==v){
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
