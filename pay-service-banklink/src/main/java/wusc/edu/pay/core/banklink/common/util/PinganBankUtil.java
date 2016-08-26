package wusc.edu.pay.core.banklink.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.common.enums.BankCode;
import wusc.edu.pay.common.enums.CardTypeEnum;
import wusc.edu.pay.common.utils.DateUtils;


/**
 * 平安银行工具类
 * @author： Peter
 * @ClassName: PinganBankUtil.java
 * @Date： 2015-2-5 上午9:58:56 
 * @version：  V1.0
 */
public class PinganBankUtil {

	public static String switchCertType(String sourceCertType){
		
		if(StringUtils.isNotBlank(sourceCertType)){
			if(sourceCertType.equals(String.valueOf(CardTypeEnum.ID_CARD.getValue()))){//身份证
				return "1";
			}else if(sourceCertType.equals(String.valueOf(CardTypeEnum.CERTIFICATE_OF_OFFICERS.toString()))){//军人军官证
				return "2";
			}else if(sourceCertType.equals(String.valueOf(CardTypeEnum.HKPermit.toString()))){//港澳台居民通行证
				return "3";
			}else if(sourceCertType.equals(String.valueOf(CardTypeEnum.PASSPORT.toString()))){//中国护照
				return "4";
			}else if(sourceCertType.equals(String.valueOf(CardTypeEnum.HOUSEHOLD.toString()))){//户口薄
				return "11";
			}else if(sourceCertType.equals(String.valueOf(CardTypeEnum.FOREIGN_PASSPORT.toString()))){//外国护照
				return "19";
			}else if(sourceCertType.equals(String.valueOf(CardTypeEnum.SOLDIERS.toString()))){//士兵证
				return "13";
			}else if(sourceCertType.equals(String.valueOf(CardTypeEnum.TEMPORARY_IDCARD.toString()))){//临时身份证
				return "9";
			}else if(sourceCertType.equals(String.valueOf(CardTypeEnum.OFFICERS_CARD.toString()))){//警官证
				return "8";
			}
		}
		
		return "0";//其他证件类型
	}
	
	
	/**
	 * 将平台的支付账户类型转换成平安银行的支付账户类型
	 * @param sourceCardType
	 * @return
	 */
	public static String switchCardType(int sourceCardType){
		
		if(sourceCardType == BankAccountTypeEnum.PRIVATE_DEBIT_CARD.getValue()){//借记卡
			return "0";
		}else if(sourceCardType == BankAccountTypeEnum.PRIVATE_BANK_BOOK.getValue()){//借记卡
			return "1";
		}else if(sourceCardType == BankAccountTypeEnum.PUBLIC_ACCOUNTS.getValue()){//对公账户
			return "2";
		}
		
		return null;
	}
	
	
	/**
	 * 将平台银行编码转换成平安银行的编码
	 * @param sourceBankcode
	 * @return
	 */
	public static String switchBankCode(String sourceBankcode){
		if(StringUtils.isNotBlank(sourceBankcode)){
			if(sourceBankcode.equals(BankCode.PINGANBANK.toString())){//平安银行
				return "12";
			}else if(sourceBankcode.equals(BankCode.ICBC.toString())){//工商银行
				return "10001";
			}else if(sourceBankcode.equals(BankCode.ABC.toString())){//农业银行
				return "10002";
			}else if(sourceBankcode.equals(BankCode.BOC.toString())){//中国银行
				return "10003";
			}else if(sourceBankcode.equals(BankCode.CCB.toString())){//建设银行
				return "10004";
			}else if(sourceBankcode.equals(BankCode.POST.toString())){//邮政存蓄银行
				return "10005";
			}else if(sourceBankcode.equals(BankCode.CMBCHINA.toString())){//招商银行
				return "10006";
			}else if(sourceBankcode.equals(BankCode.ECITIC.toString())){//中信银行
				return "10007";
			}else if(sourceBankcode.equals(BankCode.CIB.toString())){//兴业银行
				return "10008";
			}else if(sourceBankcode.equals(BankCode.CEB.toString())){//光大银行
				return "10009";
			}else if(sourceBankcode.equals(BankCode.PINGANBANK.toString())){//民生银行
				return "10010";
			}else if(sourceBankcode.equals(BankCode.PINGANBANK.toString())){//广发银行
				return "10011";
			}
		}
		return null;
	}
	
	/**
	 * 生成通用的标准请求报文头
	 * 遵循字符右补空格，数字左补0的规则
	 * @param codeType 报文编码
	 * @param protocolType 协议编码
	 * @param selcorpId 企业标准代码
	 * @param msgLength 报文长度
	 * @param tradeCode 交易码
	 * @param requestNo 请求编码
	 * @return
	 */
	public static String madeMessageHeader(String codeType , String protocolType ,String selcorpId , int msgLength ,String tradeCode , String requestNo){
		
		StringBuffer messageString = new StringBuffer();
		messageString.append("A00101");//报文类别编号 6位
		messageString.append(addChar(true,codeType,' ',2));//报文编码 2位
		messageString.append(addChar(true,protocolType,' ',2));//通讯协议 2位
		messageString.append(addChar(true,selcorpId,' ',20));//企业银企直连标准代码 20位
		messageString.append(addChar(false,msgLength+"",' ',10));//接收报文长度 10位
		messageString.append(addChar(true,tradeCode,' ',6));//交易码 6位
		messageString.append("00001");//操做员代码 5位
		messageString.append("01");//服务类型 2位 01:请求 02:应答
		messageString.append(DateUtils.getStrFormTime("yyyyMMdd", new Date()));//交易日期yyyyMMdd 8位
		messageString.append(DateUtils.getStrFormTime("HHmmss", new Date()));//交易时间 HHmmss 6位
		messageString.append(addChar(true,requestNo,' ',20));//请求方系统流水号 20位
		messageString.append(addChar(true,"",' ',6));//返回码 6位，请求非必输
		messageString.append(addChar(true,"",' ',100));//返回描述 100位
		messageString.append("0");//后续包标志 0 结束包 ， 1 还有后续包 1位
		messageString.append("000");//请求次数 000第一次 001第二次002第三次 一次类推 3位
		messageString.append("0");//签名标识 0不签名，1签名（填0，企业不管，由银行客户端完成）
		messageString.append("1");//签名数据包格式 0-	裸签（填1，企业不管，由银行客户端完成）1-	PKCS7
		messageString.append(addChar(true,"",' ',12));//签名算法 12位
		messageString.append(addChar(false,"",' ',10));//签名数据长度 企业不需要签名10位
		messageString.append("0");//附件数目 0-	没有；有的话填具体个数  目前最多只支持1个附件，即填写1
		
		return messageString.toString();
	}
	
	
	/**
	 * 解析结果报文
	 * @param message 原报文实体
	 * @return
	 */
	public static Map<String , Object> parseResultMessag(String sourceMessage){
		
		if(StringUtils.isBlank(sourceMessage)){
			return null;
		}else{
			byte[] sourceByte = sourceMessage.getBytes();
			if(sourceByte.length < 222){
				return null;
			}else{
				Map<String , Object> returnMap = new HashMap<String , Object>();
				
					returnMap.put("messageTypeCode", new String(sourceByte,0,6));//报文类别编号
					returnMap.put("messageCode", new String(sourceByte,6,2));//报文编码
					returnMap.put("protocolType", new String(sourceByte,8,2));//通讯协议
					returnMap.put("selcorpId", new String(sourceByte,10,20));//企业银企直连标准代码
					int messageLength = Integer.valueOf(new String(sourceByte,30,10));
					returnMap.put("messageLength", messageLength);//报文长度
					returnMap.put("tradeCode", new String(sourceByte,40,6));//交易码
					returnMap.put("operator", new String(sourceByte,46,5));//操作员号
					returnMap.put("serviceType", new String(sourceByte,51,2));//服务类型
					returnMap.put("tradeDate", new String(sourceByte,53,8));//交易日期
					returnMap.put("tradeTime", new String(sourceByte,61,6));//交易时间
					returnMap.put("requestNo", new String(sourceByte,67,20));//请求方流水号
					returnMap.put("resultCode", new String(sourceByte,87,6));//返回码
					returnMap.put("resultMsg", new String(sourceByte,93,100));//返回信息
					returnMap.put("subFlag", new String(sourceByte,193,1));//后续包标识
					returnMap.put("requestTimes", new String(sourceByte,194,3));//请求次数
					returnMap.put("signFlag", new String(sourceByte,197,1));//签名标识
					returnMap.put("signType", new String(sourceByte,198,1));//签名格式
					returnMap.put("signMath", new String(sourceByte,199,12));//签名算法
					returnMap.put("signMsgLength", new String(sourceByte,211,10));//签名数据长度
					returnMap.put("annexCount", new String(sourceByte,221,1));//附件数目
					returnMap.put("messageBody", new String(sourceByte,222,messageLength));//报文体
					
				return returnMap;
			}
		}
		
	}
	
	/**
	 * 拼接字符
	 * @param isRight 是否是在右侧拼接
	 * @param source 源字符串（如果源字符串为null，则将源字符串按照空字符串对待即""）
	 * @param addChar 要补的字符
	 * @param length 拼接为多长的字符串，如果源字符串超出此长度，则将源字符串直接返回
	 * @return
	 */
	public static String addChar(boolean isRight , String source , char addChar , int length){
		
		if(source == null){//先判断源字符串是否为null
			source = "";
		}
		
		int sourceLength = source.length();
		if(sourceLength >= length){//如果源字符串长度大于等于需要拼接的长度，则直接返回源字符串
			return source;
		}
		
		StringBuffer  addStr = new StringBuffer();
		int addLength = length - sourceLength;//需要添加多少位字符 
		
		for(int i = 0 ; i < addLength ; i++){
			addStr.append(addChar);
		}
		
		if(isRight){//补右侧
			return addStr + source;
		}else{//补左侧
			return source + addStr;
		}
	}
	
}
