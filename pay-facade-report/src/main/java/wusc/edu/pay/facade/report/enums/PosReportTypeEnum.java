package wusc.edu.pay.facade.report.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * POS报表类型 
 * 商户交易汇总报表、 
 * 商户清算报表、 
 * 一级代理分润报表、 
 * 代理商分润明细报表、 
 * 收益报表 
 * 渠道交易报表 
 * 时段报表 
 * 地区报表 
 * 异常卡BIN报表
 * 错误码统计报表 
 * 厂家类型统计终端报表 
 * 代理商统计终端报表 
 * 商户统计终端报表 
 * 商户审核状态类型报表
 * 
 * @author Administrator
 * 
 */
public enum PosReportTypeEnum {

	MERCH_TRADE_SUMMARY_REPORT("商户交易汇总报表", 100), 
	CHANNEL_TRADE_SUMMARY_REPORT("渠道交易报表", 101), 
	TIME_SCOPE_REPORT("时段报表", 102), 
	AREA_SCOPE_REPORT("地区报表", 103), 
	CARDBIN_ERROR_MSG_REPORT("异常卡BIN报表", 104), 
	ERROR_MSG_REPORT("错误码统计报表", 105), 
	TIMINAL_REPORT_BY_FACTORY("厂家类型统计终端报表", 106), 
	TIMINAL_REPORT_BY_AGENT("代理商统计终端报表", 107), 
	TIMINAL_REPORT_BY_MERCHANT("商户统计终端报表", 108), 
	MERCHANT_AUDIT_STATUS_REPORT("商户审核状态类型报表", 109);

	/** 枚举值 */
	private int value;

	/** 描述 */
	private String desc;

	private PosReportTypeEnum(String desc, int value) {
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

	public static PosReportTypeEnum getEnum(int value) {
		PosReportTypeEnum resultEnum = null;
		PosReportTypeEnum[] enumAry = PosReportTypeEnum.values();
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
		PosReportTypeEnum[] ary = PosReportTypeEnum.values();
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
		PosReportTypeEnum[] ary = PosReportTypeEnum.values();
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
