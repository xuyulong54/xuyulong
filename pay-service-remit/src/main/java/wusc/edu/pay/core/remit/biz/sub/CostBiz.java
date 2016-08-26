package wusc.edu.pay.core.remit.biz.sub;

import java.util.Date;

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
import wusc.edu.pay.facade.remit.entity.RemitProcess;

import com.alibaba.fastjson.JSONObject;

@Component("costBiz")
public class CostBiz {

	@Autowired
	private CalCostOrderFacade calCostOrderFacade;

	@Autowired
	private JmsTemplate notifyJmsTemplate;

	private static final Log log = LogFactory.getLog(CostBiz.class);

	/**
	 * 成本试算
	 * 
	 * @param remitProcess
	 * @return
	 */
	public CalCostOrder preCaculate(RemitProcess remitProcess) {
		log.info("==>preCaculate");

		log.info(String.format("==>payInterfacaeCode:%s,  payerPayAmount:%s, costItem:%s", remitProcess.getChannelCode(),
				remitProcess.getAmount(), CostItemEnum.SETTLEMENT_ACQUIRING.name()));

		CalCostOrder calCostOrder = calCostOrderFacade.preCalulateCost(remitProcess.getChannelCode(), "", remitProcess.getAmount(),
				CostItemEnum.SETTLEMENT_ACQUIRING);

		log.info("==>preCaculate Result:" + JSONObject.toJSONString(calCostOrder));

		// 填充CalCostOrder
		calCostOrder.setCalInterface(remitProcess.getChannelCode());
		calCostOrder.setTrxNo(remitProcess.getRequestNo());
		calCostOrder.setMerchantNo(remitProcess.getUserNo());
		calCostOrder.setMerchantName(remitProcess.getAccountName());
		calCostOrder.setMerchantOrderNo(remitProcess.getRequestNo());
		calCostOrder.setBankOrderNo(remitProcess.getOrderId());
		calCostOrder.setTrxTime(new Date());
		calCostOrder.setFromSystem(String.valueOf(SystemResourceTypeEnum.ONLINE.getValue()));
		
		remitProcess.setCalCost(calCostOrder.getFee());	// 填充成本
		
		log.info("==>preCaculate<==" + JSONObject.toJSONString(calCostOrder));

		return calCostOrder;
	}

	/**
	 * 成本订单保存
	 * 
	 * @param remitProcess
	 * @return
	 */
	public void caculate(final CalCostOrder calCostOrder) {
		notifyJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(NotifyUtil.formatCost(calCostOrder));
			}
		});
	}
}
