package wusc.edu.pay.core.settlement.biz.sub;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.service.TradeLimitFacade;
import wusc.edu.pay.facade.settlement.exception.SettBizException;

/**
 * 
 * @描述: 限制包业务biz.
 * @创建时间:2015-3-11,下午3:14:24.
 * @版本:
 */
@Component("tradeLimitBiz")
public class TradeLimitBiz {

	@Autowired
	private TradeLimitFacade tradeLimitFacade;

	/**
	 * 
	 * @描述: 调用限制包对结算金额限制.
	 * @作者: Along.shen .
	 * @创建时间:2015-3-11,下午3:14:04.
	 * @版本:
	 */
	public void validateTradeAmount(LimitTrxTypeEnum limitTrxTypeEnum, String payProduct, String payWay, String cardType, BigDecimal tradeAmount,  String merchantNo){
		try {
			tradeLimitFacade.validateTradeAmount(limitTrxTypeEnum, payProduct, payWay, cardType,tradeAmount, merchantNo);
		} catch (BizException e) {
			throw new SettBizException(SettBizException.SETT_IS_IN_LIMIT, "金额被限制:" + e.getMsg());
		}
	}
}
