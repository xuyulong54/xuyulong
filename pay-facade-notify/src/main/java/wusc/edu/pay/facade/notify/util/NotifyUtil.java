package wusc.edu.pay.facade.notify.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.param.MailParam;
import wusc.edu.pay.common.param.SmsParam;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.facade.cost.entity.CalCostOrder;
import wusc.edu.pay.facade.fee.dto.FeeCalculateResultDTO;
import wusc.edu.pay.facade.notify.entity.NotifyRecord;
import wusc.edu.pay.facade.notify.enums.NotifyTypeEnum;
import wusc.edu.pay.facade.remit.entity.SettlRequestParam;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @描述: 队列发送工具类 .
 * @作者: LaiChunhua,WuShuicheng.
 * @创建: 2014-11-14,下午4:26:25
 * @版本: V1.0
 * 
 */
public class NotifyUtil {

	private static Log LOG = LogFactory.getLog(NotifyUtil.class);

	/**
	 * 编码类型 UTF-8.
	 */
	private static final String UTF_8 = "utf-8";

	private static final String NOTITY_RECEIVE_URL = PublicConfig.NOTIFY_RECEIVE_URL;

	/**
	 * 计费
	 * 
	 * @param dto
	 * @return
	 */
	public static String formatFee(FeeCalculateResultDTO dto) {

		// 转与Json
		String msg = JSONObject.toJSONString(dto);
		try {
			msg = URLEncoder.encode(Base64.encodeBase64String(msg.getBytes(UTF_8)), UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOG.error("==>UnsupportedEncodingException:", e);
		}

		final NotifyRecord notifyRecord = new NotifyRecord();
		notifyRecord.setNotifyType(NotifyTypeEnum.FEE.getValue());
		notifyRecord.setMerchantNo(dto.getFeeOrder().getMerchantNo());
		notifyRecord.setMerchantOrderNo(dto.getFeeOrder().getMerchantOrderNo());
		notifyRecord.setUrl(NOTITY_RECEIVE_URL + "fee_notifyReceive.action?msg=" + msg);

		return JSONObject.toJSONString(notifyRecord);
	}

	/**
	 * 成本
	 * 
	 * @param calCostOrder
	 * @return
	 */
	public static String formatCost(CalCostOrder calCostOrder) {

		// 转与Json
		String msg = JSONObject.toJSONString(calCostOrder);
		try {
			msg = URLEncoder.encode(Base64.encodeBase64String(msg.getBytes(UTF_8)), UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		final NotifyRecord notifyRecord = new NotifyRecord();
		notifyRecord.setNotifyType(NotifyTypeEnum.COST.getValue());
		notifyRecord.setMerchantNo(calCostOrder.getCalInterface());
		notifyRecord.setMerchantOrderNo(calCostOrder.getTrxNo());
		notifyRecord.setUrl(NOTITY_RECEIVE_URL + "cost_notifyReceive.action?msg=" + msg);

		return JSONObject.toJSONString(notifyRecord);
	}

	/**
	 * 打款
	 * 
	 * @param calCostOrder
	 * @return
	 */
	public static String formatRemit(List<SettlRequestParam> listSettlRequestParam) {

		// 转与Json
		String msg = JSONObject.toJSONString(listSettlRequestParam);
		try {
			msg = URLEncoder.encode(Base64.encodeBase64String(msg.getBytes(UTF_8)), UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOG.error("==>UnsupportedEncodingException:", e);
		}

		final NotifyRecord notifyRecord = new NotifyRecord();
		notifyRecord.setNotifyType(NotifyTypeEnum.REMIT_CREATE.getValue());
		notifyRecord.setMerchantNo(NotifyTypeEnum.REMIT_CREATE.name());
		notifyRecord.setMerchantOrderNo(StringUtil.getUUID());

		notifyRecord.setUrl(NOTITY_RECEIVE_URL + "remitCreate_notifyReceive.action?msg=" + msg);

		return JSONObject.toJSONString(notifyRecord);
	}

	/**
	 * 打款完成
	 * 
	 * @param remitRequestNo
	 *            打款请求号
	 * @param remitConfirmTime
	 *            打款确认时间
	 * @param remitStatus
	 *            打款状态 PublicStatusEnum 100 成功 101 失败
	 * @param remitRemark
	 *            打款描述
	 * @return
	 */
	public static String formatRemitComplete(String remitRequestNo, Date remitConfirmTime, Integer remitStatus, String remitRemark) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("remitRequestNo", remitRequestNo);
		map.put("remitConfirmTime", remitConfirmTime);
		map.put("remitStatus", remitStatus);
		map.put("remitRemark", remitRemark);

		// 转与Json
		String msg = JSONObject.toJSONString(map);
		try {
			msg = URLEncoder.encode(Base64.encodeBase64String(msg.getBytes(UTF_8)), UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOG.error("==>UnsupportedEncodingException:", e);
		}

		final NotifyRecord notifyRecord = new NotifyRecord();
		notifyRecord.setNotifyType(NotifyTypeEnum.REMIT_COMPLETE.getValue());
		notifyRecord.setMerchantNo(remitRequestNo);
		notifyRecord.setMerchantOrderNo(remitRequestNo);

		notifyRecord.setUrl(NOTITY_RECEIVE_URL + "remitComplete_notifyReceive.action?msg=" + msg);

		return JSONObject.toJSONString(notifyRecord);
	}

	/**
	 * 邮件
	 * 
	 * @param calCostOrder
	 * @return
	 */
	public static String formatMail(MailParam mail) {

		// 转与Json
		String msg = JSONObject.toJSONString(mail);
		try {
			msg = URLEncoder.encode(Base64.encodeBase64String(msg.getBytes(UTF_8)), UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOG.error("==>UnsupportedEncodingException:", e);
		}

		final NotifyRecord notifyRecord = new NotifyRecord();
		notifyRecord.setNotifyType(NotifyTypeEnum.MAIL.getValue());
		notifyRecord.setMerchantNo(mail.getTo());
		notifyRecord.setMerchantOrderNo(StringUtil.getUUID());

		notifyRecord.setUrl(NOTITY_RECEIVE_URL + "mail_notifyReceive.action?msg=" + msg);

		return JSONObject.toJSONString(notifyRecord);
	}

	/**
	 * 短信
	 * 
	 * @param calCostOrder
	 * @return
	 */
	public static String formatSms(SmsParam sms) {

		// 转与Json
		String msg = JSONObject.toJSONString(sms);
		try {
			msg = URLEncoder.encode(Base64.encodeBase64String(msg.getBytes(UTF_8)), UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOG.error("==>UnsupportedEncodingException:", e);
		}

		final NotifyRecord notifyRecord = new NotifyRecord();
		notifyRecord.setNotifyType(NotifyTypeEnum.SMS.getValue());
		notifyRecord.setMerchantNo(sms.getPhone());
		notifyRecord.setMerchantOrderNo(StringUtil.getUUID());

		notifyRecord.setUrl(NOTITY_RECEIVE_URL + "sms_notifyReceive.action?msg=" + msg);

		return JSONObject.toJSONString(notifyRecord);
	}

	/**
	 * pos支付
	 * 
	 * @param p1_merchantNo
	 *            商户编号
	 * @param p2_payInterfaceCode
	 *            接口编号
	 * @param p3_mcc
	 *            自有商户mcc
	 * @param p4_payAmount
	 *            支付金额
	 * @param p5_paymentOrderNo
	 *            支付订单号(传平台posp参考号)
	 * @param p6_bankOrderNo
	 *            银行订单号(如果没有，可传银行流水号)
	 * @param p7_payInterfaceMcc
	 *            大商户mcc
	 * @param p8_bankTrxNo
	 *            银行流水号(交易成功后，通道方返回的唯一标识)
	  * @param p9_settHandleType
	 *            结算方类型(1:平台，2:外部)
	 * @return
	 */
	public static String formatPospPay(String p1_merchantNo, String p2_payInterfaceCode, String p3_mcc, String p4_payAmount, String p5_paymentOrderNo, String p6_bankOrderNo,
			String p7_payInterfaceMcc, String p8_bankTrxNo, String p9_settHandleType) {

		// 转与Json
		Map<String, String> map = new HashMap<String, String>();
		map.put("p1_merchantNo", p1_merchantNo);
		map.put("p2_payInterfaceCode", p2_payInterfaceCode);
		map.put("p3_mcc", p3_mcc);
		map.put("p4_payAmount", p4_payAmount);
		map.put("p5_paymentOrderNo", p5_paymentOrderNo);
		map.put("p6_bankOrderNo", p6_bankOrderNo);
		map.put("p7_payInterfaceMcc", p7_payInterfaceMcc);
		map.put("p8_bankTrxNo", p8_bankTrxNo);
		map.put("p9_settHandleType", p9_settHandleType);

		String msg = JSONObject.toJSONString(map);
		try {
			msg = URLEncoder.encode(Base64.encodeBase64String(msg.getBytes(UTF_8)), UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOG.error("==>UnsupportedEncodingException:", e);
		}

		final NotifyRecord notifyRecord = new NotifyRecord();
		notifyRecord.setNotifyType(NotifyTypeEnum.POSP_PAY.getValue());
		notifyRecord.setMerchantNo(p1_merchantNo);
		notifyRecord.setMerchantOrderNo(p5_paymentOrderNo);

		notifyRecord.setUrl(NOTITY_RECEIVE_URL + "pos_payment_notifyReceive.action?msg=" + msg);

		return JSONObject.toJSONString(notifyRecord);
	}

	/**
	 * pos退款创建
	 * 
	 * @param p1_merchantNo
	 *            商户编号
	 * @param p2_refundOrderNo
	 *            退款订单号(系统参考号)
	 * @param p3_merchantOrderNo
	 *            原商户订单号(原系统参考号)
	 * @param p4_refundAmount
	 *            退款金额
	 * @param p5_refundReason
	 *            退款原因
	 * @param p6_refundSplitDetail
	 *            退款分账明细(可传空)
	 * @return
	 */
	public static String formatPospRefundCreate(String p1_merchantNo, String p2_refundOrderNo, String p3_merchantOrderNo, String p4_refundAmount, String p5_refundReason, String p6_refundSplitDetail) {
		// 转与Json
		Map<String, String> map = new HashMap<String, String>();
		map.put("p1_merchantNo", p1_merchantNo);
		map.put("p2_refundOrderNo", p2_refundOrderNo);
		map.put("p3_merchantOrderNo", p3_merchantOrderNo);
		map.put("p4_refundAmount", p4_refundAmount);
		map.put("p5_refundReason", p5_refundReason);
		map.put("p6_refundSplitDetail", p6_refundSplitDetail);

		String msg = JSONObject.toJSONString(map);
		try {
			msg = URLEncoder.encode(Base64.encodeBase64String(msg.getBytes(UTF_8)), UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOG.error("==>UnsupportedEncodingException:", e);
		}

		final NotifyRecord notifyRecord = new NotifyRecord();
		notifyRecord.setNotifyType(NotifyTypeEnum.POSP_REFUND_CREATE.getValue());
		notifyRecord.setMerchantNo(p1_merchantNo);
		notifyRecord.setMerchantOrderNo(p2_refundOrderNo);

		notifyRecord.setUrl(NOTITY_RECEIVE_URL + "pos_refund_create_notifyReceive.action?msg=" + msg);

		return JSONObject.toJSONString(notifyRecord);
	}

	/**
	 * pos退款成功
	 * 
	 * @param p1_merchantNo
	 *            商户编号
	 * @param p2_refundOrderNo
	 *            退款订单号(传平台posp参考号)
	 * @param p3_bankRefundTrxNo
	 *            银行流水号(交易成功后，通道方返回的唯一标识)
	 * @param p4_refundAmount
	 *            退款金额
	 * @param p5_bankRefundOrderNo
	 *            银行订单号(如果没有，可传银行流水号)
	 * @return
	 */
	public static String formatPospRefundSuccess(String p1_merchantNo, String p2_refundOrderNo, String p3_bankRefundTrxNo, String p4_refundAmount, String p5_bankRefundOrderNo) {
		// 转与Json
		Map<String, String> map = new HashMap<String, String>();
		map.put("p1_merchantNo", p1_merchantNo);
		map.put("p2_refundOrderNo", p2_refundOrderNo);
		map.put("p3_bankRefundTrxNo", p3_bankRefundTrxNo);
		map.put("p4_refundAmount", p4_refundAmount);
		map.put("p5_bankRefundOrderNo", p5_bankRefundOrderNo);

		String msg = JSONObject.toJSONString(map);
		try {
			msg = URLEncoder.encode(Base64.encodeBase64String(msg.getBytes(UTF_8)), UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOG.error("==>UnsupportedEncodingException:", e);
		}

		final NotifyRecord notifyRecord = new NotifyRecord();
		notifyRecord.setNotifyType(NotifyTypeEnum.POSP_REFUND_SUCCESS.getValue());
		notifyRecord.setMerchantNo(p1_merchantNo);
		notifyRecord.setMerchantOrderNo(p2_refundOrderNo);

		notifyRecord.setUrl(NOTITY_RECEIVE_URL + "pos_refund_success_notifyReceive.action?msg=" + msg);

		return JSONObject.toJSONString(notifyRecord);
	}

	/**
	 * pos退款取消
	 * 
	 * @param p1_merchantNo
	 * @param p2_refundOrderNo
	 * @return
	 */
	public static String formatPospRefundCancel(String p1_merchantNo, String p2_refundOrderNo) {
		// 转与Json
		Map<String, String> map = new HashMap<String, String>();
		map.put("p1_merchantNo", p1_merchantNo);
		map.put("p2_refundOrderNo", p2_refundOrderNo);

		String msg = JSONObject.toJSONString(map);
		try {
			msg = URLEncoder.encode(Base64.encodeBase64String(msg.getBytes(UTF_8)), UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOG.error("==>UnsupportedEncodingException:", e);
		}

		final NotifyRecord notifyRecord = new NotifyRecord();
		notifyRecord.setNotifyType(NotifyTypeEnum.POSP_REFUND_CANCEL.getValue());
		notifyRecord.setMerchantNo(p1_merchantNo);
		notifyRecord.setMerchantOrderNo(p2_refundOrderNo);

		notifyRecord.setUrl(NOTITY_RECEIVE_URL + "pos_refund_canel_notifyReceive.action?msg=" + msg);

		return JSONObject.toJSONString(notifyRecord);
	}

	/**
	 * 代理商分润
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @return
	 */
	public static String formatAgentSplitGrofit(String merchantNo, String merchantOrderNo) {

		// 转与Json
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantNo", merchantNo);
		map.put("merchantOrderNo", merchantOrderNo);

		String msg = JSONObject.toJSONString(map);
		try {
			msg = URLEncoder.encode(Base64.encodeBase64String(msg.getBytes(UTF_8)), UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOG.error("==>UnsupportedEncodingException:", e);
		}

		final NotifyRecord notifyRecord = new NotifyRecord();
		notifyRecord.setNotifyType(NotifyTypeEnum.AGENT_SPLIT_GROFIT.getValue());
		notifyRecord.setMerchantNo(merchantNo);
		notifyRecord.setMerchantOrderNo(merchantOrderNo);

		notifyRecord.setUrl(NOTITY_RECEIVE_URL + "agentSplitGrofit_notifyReceive.action?msg=" + msg);

		return JSONObject.toJSONString(notifyRecord);
	}

	/**
	 * 代理商分润退回
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @return
	 */
	public static String formatAgentSplitGrofitBack(String merchantNo, String orgMerchantOrderNo, String refundAmount, String refundOrderNo) {

		// 转与Json
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantNo", merchantNo);
		map.put("orgMerchantOrderNo", orgMerchantOrderNo);
		map.put("refundAmount", refundAmount);
		map.put("refundOrderNo", refundOrderNo);

		String msg = JSONObject.toJSONString(map);
		try {
			msg = URLEncoder.encode(Base64.encodeBase64String(msg.getBytes(UTF_8)), UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOG.error("==>UnsupportedEncodingException:", e);
		}

		final NotifyRecord notifyRecord = new NotifyRecord();
		notifyRecord.setNotifyType(NotifyTypeEnum.AGENT_SPLIT_GROFIT_BACK.getValue());
		notifyRecord.setMerchantNo(merchantNo);
		notifyRecord.setMerchantOrderNo(refundOrderNo);

		notifyRecord.setUrl(NOTITY_RECEIVE_URL + "agentSplitGrofitBack_notifyReceive.action?msg=" + msg);

		return JSONObject.toJSONString(notifyRecord);
	}

}
