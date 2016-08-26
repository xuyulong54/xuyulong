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

import wusc.edu.pay.facade.cost.entity.CalCostOrder;
import wusc.edu.pay.facade.cost.enums.CostItemEnum;
import wusc.edu.pay.facade.cost.enums.SystemResourceTypeEnum;
import wusc.edu.pay.facade.cost.service.CalCostOrderFacade;
import wusc.edu.pay.facade.notify.util.NotifyUtil;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.enums.TradePaymentTypeEnum;

import com.alibaba.fastjson.JSONObject;

@Component("costBiz")
public class CostBiz {

	@Autowired
	private CalCostOrderFacade calCostOrderFacade;

	@Autowired
	private JmsTemplate notifyJmsTemplate;

	private static final Log log = LogFactory.getLog(CostBiz.class);

	/**
	 * 支付_成本试算
	 * 
	 * @param paymentRecord
	 */
	public CalCostOrder preCaculate_Pay(PaymentRecord paymentRecord) {

		log.info("==>preCaculate_Pay");

		CostItemEnum costItem = null;
		if (paymentRecord.getBizType() == TradeBizTypeEnum.ONLINE_ACQUIRING.getValue()) {
			if (paymentRecord.getPaymentType() == TradePaymentTypeEnum.NET_B2C_PAY.getValue()
					|| paymentRecord.getPaymentType() == TradePaymentTypeEnum.NET_B2B_PAY.getValue()) {
				costItem = CostItemEnum.ONLINE_ACQUIRING;
			} else if (paymentRecord.getPaymentType() == TradePaymentTypeEnum.FAST_PAY.getValue()) {
				costItem = CostItemEnum.FP_ACQUIRING;
			}
		} else if (paymentRecord.getBizType() == TradeBizTypeEnum.POS_ACQUIRING.getValue()) {
			costItem = CostItemEnum.POS_ACQUIRING;
		} else if (paymentRecord.getBizType() == TradeBizTypeEnum.RECHARGE.getValue()) {
			costItem = CostItemEnum.RECHARGE_ACQUIRING;
		} else if (paymentRecord.getBizType() == TradeBizTypeEnum.MOBILE_ACQUIRING.getValue()) { // 移动端
			if (paymentRecord.getPaymentType() == TradePaymentTypeEnum.FAST_PAY.getValue()) {
				costItem = CostItemEnum.FP_ACQUIRING;
			}
		}

		log.info(String.format("==>payInterfacaeCode:%s, payInterrfacaeMcc:%s, payerPayAmount:%s, costItem:%s",
				paymentRecord.getPayInterfaceCode(), paymentRecord.getPayInterfaceMcc(), paymentRecord.getPayerPayAmount(), costItem));

		CalCostOrder calCostOrder = calCostOrderFacade.preCalulateCost(paymentRecord.getPayInterfaceCode(),
				paymentRecord.getPayInterfaceMcc(), paymentRecord.getPayerPayAmount(), costItem);

		log.info("==>preCaculate_Pay Result:" + JSONObject.toJSONString(calCostOrder));

		// 填充CalCostOrder
		calCostOrder.setCalInterface(paymentRecord.getPayInterfaceCode());
		calCostOrder.setTrxNo(paymentRecord.getTrxNo());
		calCostOrder.setMerchantNo(paymentRecord.getMerchantNo());
		calCostOrder.setMerchantName(paymentRecord.getMerchantName());
		calCostOrder.setMerchantOrderNo(paymentRecord.getMerchantOrderNo());
		calCostOrder.setBankOrderNo(paymentRecord.getBankOrderNo());
		calCostOrder.setTrxTime(paymentRecord.getPaySuccessTime());

		if (paymentRecord.getBizType() == TradeBizTypeEnum.POS_ACQUIRING.getValue()) {
			calCostOrder.setFromSystem(String.valueOf(SystemResourceTypeEnum.POS.getValue())); // 数据来源
		} else {
			calCostOrder.setFromSystem(String.valueOf(SystemResourceTypeEnum.ONLINE.getValue())); // 数据来源
		}

		return calCostOrder;
	}

	/**
	 * 支付_成本计费
	 * 
	 * @param paymentRecord
	 */
	public void caculate_Pay(final CalCostOrder calCostOrder) {

		notifyJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(NotifyUtil.formatCost(calCostOrder));
			}
		});
	}

}
