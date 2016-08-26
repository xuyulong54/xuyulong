package wusc.edu.pay.core.trade.biz.sub;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.facade.notify.entity.NotifyRecord;
import wusc.edu.pay.facade.notify.enums.NotifyTypeEnum;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.exception.TradeBizException;
import wusc.edu.pay.facade.trade.util.OrderFacadeUtil;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;

import com.alibaba.fastjson.JSONObject;

@Component("merchantNotifyBiz")
public class MerchantNotifyBiz {

	@Autowired
	private JmsTemplate notifyJmsTemplate; // 商户通知队列模板

	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;

	private static final Log log = LogFactory.getLog(MerchantNotifyBiz.class);

	public void notifyMerchant(PaymentRecord paymentRecord, PaymentOrder paymentOrder) {

		log.info("==>notifyMerchant");

		if (StringUtil.isBlank(paymentOrder.getNotifyUrl())) {
			return;
		}

		String notifyUrl;
		try {
			if (OrderFacadeUtil.toCmdCodeEnum(paymentRecord.getBizType(), paymentRecord.getPaymentType()) == null) {
				throw new TradeBizException(TradeBizException.ORDER_BUILD_NOTIFYURL_ERROR, "生成商户通知url中枚举转换错误");
			}
			int cmdCode = OrderFacadeUtil.toCmdCodeEnum(paymentRecord.getBizType(), paymentRecord.getPaymentType()).getValue();

			MerchantOnline merchantOnline = merchantOnlineFacade.getMerchantOnlineByMerchantNo(paymentRecord.getMerchantNo());
			notifyUrl = OrderFacadeUtil.buildMerchantNotifyUrl(paymentRecord.getNotifyUrl(), cmdCode, paymentRecord.getMerchantNo(),
					paymentRecord.getMerchantOrderNo(), paymentRecord.getOrderAmount().doubleValue(), paymentRecord.getCur(),
					paymentOrder.getCallbackPara(), paymentRecord.getStatus(), paymentRecord.getTrxNo(), paymentRecord.getBankOrderNo(),
					paymentRecord.getBankTrxNo(), paymentRecord.getPaySuccessTime(), paymentRecord.getCompleteTime(),
					paymentRecord.getPayWayCode(), merchantOnline.getMerchantKey());

			log.info("==>notifyUrl:" + notifyUrl);

			final NotifyRecord notifyRecord = new NotifyRecord();
			notifyRecord.setUrl(notifyUrl);
			notifyRecord.setNotifyType(NotifyTypeEnum.MERCHANT.getValue());
			notifyRecord.setMerchantNo(paymentRecord.getMerchantNo());
			notifyRecord.setMerchantOrderNo(paymentRecord.getMerchantOrderNo());
			notifyJmsTemplate.send(new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(JSONObject.toJSONString(notifyRecord));
				}
			});
		} catch (Exception e) {
			log.error("==>生成商户通知url错误 ", e);
			throw new TradeBizException(TradeBizException.ORDER_BUILD_NOTIFYURL_ERROR, "生成商户通知url错误");
		}

		log.info("==>notifyMerchant<==");
	}
}
