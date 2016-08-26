package wusc.edu.pay.core.settlement.biz.sub;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.facade.notify.util.NotifyUtil;
import wusc.edu.pay.facade.remit.entity.SettlRequestParam;
import wusc.edu.pay.facade.remit.enums.RemitUrgentEnum;
import wusc.edu.pay.facade.remit.enums.TradeSourcesEnum;
import wusc.edu.pay.facade.remit.enums.TradeTypeEnum;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.enums.SettModeTypeEnum;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;


@Component("remitBiz")
public class RemitBiz {

	@Autowired
	private JmsTemplate notifyJmsTemplate;

	private static final Log log = LogFactory.getLog(RemitBiz.class);
	
	/** 发起打款请求**/
	public void launchRemit(SettRecord settRecord){
		if (settRecord.getSettMode() == SettModeTypeEnum.URGENT_SETTLE.getValue()) {
			this.requestRemit(settRecord, TradeSourcesEnum.MERCHANT_SETTLEMENT, TradeTypeEnum.MERCHANT_AUTO_SETTLEMENT_URGENT, RemitUrgentEnum.URGENT);
		} else {
			// 判断区分商户结算和代理商结算
			TradeTypeEnum tradeType = null;
			if (settRecord.getUserType() != null && settRecord.getUserType() == UserTypeEnum.POSAGENT.getValue()) {
				tradeType = TradeTypeEnum.AGENT_AUTO_SETTLEMENT;
			} else {
				tradeType = TradeTypeEnum.MERCHANT_AUTO_SETTLEMENT;
			}
			this.requestRemit(settRecord, TradeSourcesEnum.MERCHANT_SETTLEMENT, tradeType, RemitUrgentEnum.UN_URGENT);
		}
	}

	/**
	 * 请求打款
	 * 
	 * @param settRecord
	 * @param tradeSource
	 */
	public void requestRemit(SettRecord settRecord, TradeSourcesEnum tradeSource, TradeTypeEnum tradeType, RemitUrgentEnum remitUrgent) {
		log.info("==>requestRemit");

		final List<SettlRequestParam> list = new ArrayList<SettlRequestParam>();
		SettlRequestParam settRequest = new SettlRequestParam();
		settRequest.setRequestNo(settRecord.getRemitNo());
		settRequest.setIsUrgent(remitUrgent.getValue()); // 非加急
		settRequest.setBankAccountName(settRecord.getBankAccountName());
		settRequest.setBankAccountNo(settRecord.getBankAccountNo());
		settRequest.setBankName(settRecord.getBankAccountAddress()); // 收款行名称
		settRequest.setBankChannelNo(settRecord.getBankChannelNo()); // 银行行号
		settRequest.setAmount(settRecord.getRemitAmount().doubleValue()); // 实际打款金额
		settRequest.setUserNo(settRecord.getUserNo());
		settRequest.setAccountNo(settRecord.getAccountNo());
		settRequest.setProvince(settRecord.getProvince());
		settRequest.setCity(settRecord.getCity());
		settRequest.setBankAccountType(settRecord.getBankAccountType());
		settRequest.setTradeSource(tradeSource.getValue());
		settRequest.setTradeType(tradeType.getValue());
		list.add(settRequest);

		Double totalAmount = 0D;
		for (int i = 0; i < list.size(); i++) {
			totalAmount = AmountUtil.add(totalAmount, list.get(i).getAmount());
		}

		notifyJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(NotifyUtil.formatRemit(list));
			}
		});

		log.info("==>requestRemit<==");
	}
}
