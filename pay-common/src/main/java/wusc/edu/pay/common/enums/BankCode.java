package wusc.edu.pay.common.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum BankCode {

	ICBC {
		public String getDesc() {
			return "工商银行";
		}
	},
	CMBCHINA {
		public String getDesc() { 
			return "招商银行";
		}
		
	},
	ABC{
		public String getDesc() {
			return "中国农业银行";
		}
	},
	CCB{
		public String getDesc() {
			return "建设银行";
		}
	},
	BCCB{
		public String getDesc() {
			return "北京银行";
		}
	},
	BOCO{
		public String getDesc() {
			return "交通银行";
		}
	},
	CIB{
		public String getDesc() {
			return "兴业银行";
		}
	},
	NJCB{
		public String getDesc() {
			return "南京银行";
		}
	},
	CMBC{
		public String getDesc() {
			return "中国民生银行";
		}
	},
	CEB{
		public String getDesc() {
			return "光大银行";
		}
	},
	BOC{
		public String getDesc() {
			return "中国银行";
		}
	},
	PINGANBANK{
		public String getDesc() {
			return "平安银行";
		}
	},
	CBHB{
		public String getDesc() {
			return "渤海银行";
		}
	},
	HKBEA{
		public String getDesc() {
			return "东亚银行";
		}
	},
	NBCB{
		public String getDesc() {
			return "宁波银行";
		}
	},
	ECITIC{
		public String getDesc() {
			return "中信银行";
		}
	},
	CGB{
		public String getDesc() {
			return "广发银行";
		}
	},
	SHB{
		public String getDesc() {
			return "上海银行";
		}
	},
	SPDB{
		public String getDesc() {
			return "上海浦东发展银行";
		}
	},
	POST{
		public String getDesc() {
			return "中国邮政";
		}
	},
	BJRCB{
		public String getDesc() {
			return "北京农村商业银行";
		}
	},
	HXB{
		public String getDesc() {
			return "华夏银行";
		}
	},
	CZ{
		public String getDesc() {
			return "浙商银行";
		}
	},
	HZBANK{
		public String getDesc() {
			return "杭州银行";
		}
	},
	SRCB{
		public String getDesc() {
			return "上海农村商业银行";
		}
	},
	NCBBANK{
		public String getDesc() {
			return "南洋商业银行";
		}
	},
	SCCB{
		public String getDesc() {
			return "河北银行";
		}
	},
	ZJTLCB{
		public String getDesc() {
			return "泰隆银行";
		}
	},
	SINA{
		public String getDesc() {
			return "新浪微博支付";
		}
	},
	YEEPAY{
		public String getDesc() {
			return "易宝支付";
		}
	},
	TFTPAY{
		public String getDesc() {
			return "腾付通支付";
		}
	},
	MOBAOPAY{
		public String getDesc() {
			return "摩宝支付";
		}
	},
	OTHER{
		public String getDesc() {
			return "其他";
		}
	};

	public static Map<String, String> toStringMap() {
		BankCode[] type = values();
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (type != null) {
			for (BankCode e : type) {
				map.put(e.toString(), e.getDesc());
			}
		}
		return map;
	}

	public abstract String getDesc();
	
	public static String getCode(String desc){
		String code=null;
		BankCode[] enumAry=BankCode.values();
		for(int i=0;i<enumAry.length;i++){
			if(desc.equals(enumAry[i].getDesc())){
				code=enumAry[i].toString();
				break;
			}
		}
		return code;
	}
	
	public static Map<String, String> getTopFourMap() {
		// 建、工、农、中国
		BankCode[] type = { BankCode.CCB, BankCode.ICBC, BankCode.ABC, BankCode.BOC };
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (type != null) {
			for (BankCode e : type) {
				map.put(e.toString(), e.getDesc());
			}
		}
		return map;
	}

	public static Map<String, String> getCommonMap() {
		// 招商、广发、光大、平安、交通、邮政、民生、中信、兴业
		BankCode[] type = { BankCode.CMBCHINA, BankCode.CGB, BankCode.CEB, BankCode.PINGANBANK, BankCode.BOCO, BankCode.POST, BankCode.CMBC, BankCode.ECITIC, BankCode.CIB };
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (type != null) {
			for (BankCode e : type) {
				map.put(e.toString(), e.getDesc());
			}
		}
		return map;
	}
	
}
