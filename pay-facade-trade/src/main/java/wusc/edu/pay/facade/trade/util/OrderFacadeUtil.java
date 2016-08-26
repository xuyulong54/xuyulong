package wusc.edu.pay.facade.trade.util;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import wusc.edu.pay.common.enums.CmdCodeEnum;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.enums.TradePaymentTypeEnum;


/**
 * 
 * @描述: 订单服务工具类.
 * @作者: WuShuicheng.
 * @创建: 2014-6-10,上午10:40:48
 * @版本: V1.0
 * 
 */
public class OrderFacadeUtil {

	/**
	 * 枚举转换，转换成接口CmdCodeEnum
	 * 
	 * @param bizType
	 * @param paymentType
	 * @return
	 */
	public static CmdCodeEnum toCmdCodeEnum(int bizType, int paymentType) {
		if (bizType == TradeBizTypeEnum.RECHARGE.getValue()) {
			return CmdCodeEnum.RECHARGE;
		} else if (bizType == TradeBizTypeEnum.ONLINE_ACQUIRING.getValue()) {
			if (paymentType == TradePaymentTypeEnum.NET_B2B_PAY.getValue()) {
				return CmdCodeEnum.ONLINE_B2B_ACQUIRING;
			} else if (paymentType == TradePaymentTypeEnum.NET_B2C_PAY.getValue()
					|| paymentType == TradePaymentTypeEnum.FAST_PAY.getValue()
					|| paymentType == TradePaymentTypeEnum.ACCOUNT_BALANCE_PAY.getValue()) {// TODO
																							// 余额支付暂时放置在线B2C收款中
				return CmdCodeEnum.ONLINE_B2C_ACQUIRING;
			}
		} else if (bizType == TradeBizTypeEnum.MOBILE_ACQUIRING.getValue()) {
			if (paymentType == TradePaymentTypeEnum.FAST_PAY.getValue()) {
				return CmdCodeEnum.MOBILE_ACQUIRING;
			}
		} else if (bizType == TradeBizTypeEnum.POS_ACQUIRING.getValue()) {
			return CmdCodeEnum.POS_ACQUIRING;
		} else if (bizType == TradeBizTypeEnum.ICC_ACQUIRING.getValue()) {
			return CmdCodeEnum.ICC_ACQUIRING;
		} else if (bizType == TradeBizTypeEnum.ACCOUNT_AGENCYDEBIT.getValue()) {
			return CmdCodeEnum.ACCOUNT_AGENCYDEBIT;
		}

		return null;
	}

	/**
	 * 构建商户通知URL串.<br/>
	 * 
	 * @param notifyUrl
	 *            商户通知地址.<br/>
	 * @param r0_Cmd
	 *            业务类型.<br/>
	 * @param r1_MerchantNo
	 *            商户编号.<br/>
	 * @param r2_OrderNo
	 *            商户订单号.<br/>
	 * @param r3_Amount
	 *            订单金额.<br/>
	 * @param r4_Cur
	 *            货币类型.<br/>
	 * @param r5_Mp
	 *            回调附加信息.<br/>
	 * @param r6_Status
	 *            订单状态.<br/>
	 * @param r7_TrxNo
	 *            支付平台交易流水号.<br/>
	 * @param r8_BankOrderNo
	 *            银行订单号.<br/>
	 * @param r9_BankTrxNo
	 *            银行流水号.<br/>
	 * @param ra_PayTime
	 *            支付时间.<br/>
	 * @param rb_DealTime
	 *            处理时间.<br/>
	 * @param rc_BankCode
	 *            银行编号.<br/>
	 * @param merchantKey
	 *            商户密钥.<br/>
	 * @return merchantNotifyUrl .<br/>
	 * @throws Exception
	 */
	public static String buildMerchantNotifyUrl( String notifyUrl,  Integer r0_Cmd,  String r1_MerchantNo,
			 String r2_OrderNo,  Double r3_Amount,  Integer r4_Cur, String r5_Mp,  Integer r6_Status,
			 String r7_TrxNo,  String r8_BankOrderNo,  String r9_BankTrxNo,  Date ra_PayTime,  Date rb_DealTime,
			 String rc_BankCode, String merchantKey) throws Exception {

		String r3_Amount_Str = new DecimalFormat("#0.00").format(r3_Amount);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ra_PayTime_Str = "";
		if(ra_PayTime != null){
			ra_PayTime_Str = sdf.format(ra_PayTime);
		}
		String rb_DealTime_Str = sdf.format(rb_DealTime);
		if(r5_Mp == null ){
			r5_Mp = "";
		}

		// 拼装签名字符串
		StringBuffer signStr = new StringBuffer();
		signStr.append("r1_MerchantNo=").append(r1_MerchantNo);
		signStr.append("&r2_OrderNo=").append(r2_OrderNo);
		signStr.append("&r3_Amount=").append(r3_Amount_Str);
		signStr.append("&r4_Cur=").append(r4_Cur);
		signStr.append("&r5_Mp=").append(r5_Mp);
		signStr.append("&r6_Status=").append(r6_Status);
		signStr.append("&r7_TrxNo=").append(r7_TrxNo);
		signStr.append("&r8_BankOrderNo=").append(r8_BankOrderNo);
		signStr.append("&r9_BankTrxNo=").append(r9_BankTrxNo);
		signStr.append("&ra_PayTime=").append(ra_PayTime_Str);
		signStr.append("&rb_DealTime=").append(rb_DealTime_Str);
		signStr.append("&rc_BankCode=").append(rc_BankCode);

		String hmac = DigestUtils.md5Hex(signStr.toString() + merchantKey);

		StringBuffer paramBuf = new StringBuffer();
		paramBuf.append("?r1_MerchantNo=" + r1_MerchantNo);
		paramBuf.append("&r2_OrderNo=" + r2_OrderNo);
		paramBuf.append("&r3_Amount=" + r3_Amount_Str);
		paramBuf.append("&r4_Cur=" + r4_Cur);

		paramBuf.append("&r5_Mp=" + URLEncoder.encode(r5_Mp, "UTF-8"));
		paramBuf.append("&r6_Status=" + r6_Status);
		paramBuf.append("&r7_TrxNo=" + r7_TrxNo);
		paramBuf.append("&r8_BankOrderNo=" + r8_BankOrderNo);
		paramBuf.append("&r9_BankTrxNo=" + r9_BankTrxNo);

		paramBuf.append("&ra_PayTime=" + URLEncoder.encode(ra_PayTime_Str, "UTF-8"));
		paramBuf.append("&rb_DealTime=" + URLEncoder.encode(rb_DealTime_Str, "UTF-8"));
		paramBuf.append("&rc_BankCode=" + rc_BankCode);

		paramBuf.append("&hmac=" + hmac);

		return notifyUrl + paramBuf.toString();
	}
}
