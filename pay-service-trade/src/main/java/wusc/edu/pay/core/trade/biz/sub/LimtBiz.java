package wusc.edu.pay.core.trade.biz.sub;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.service.TradeLimitFacade;


@Component("limtBiz")
public class LimtBiz {

	@Autowired
	private TradeLimitFacade tradeLimitFacade;

	private static final Log log = LogFactory.getLog(LimtBiz.class);

	/** 交易金额的累加 **/
	public void tradeAmountAddUp(LimitTrxTypeEnum limitTrxTypeEnum, String payProduct, String payWay, String cardType,
			BigDecimal tradeAmount, String merchantNo, Date orderDate) {

		log.info(String.format(
				"==>limitTrxTypeEnum:%s, payProduct:%s, payWay:%s, cardType:%s, tradeAmount:%s, merchantNo:%s, orderDate:%s",
				limitTrxTypeEnum.name(), payProduct, payWay, cardType, tradeAmount, merchantNo, orderDate));

		tradeLimitFacade.cumulateTradeAmount(limitTrxTypeEnum, payProduct, payWay, cardType, tradeAmount, merchantNo, orderDate);

	}

}
