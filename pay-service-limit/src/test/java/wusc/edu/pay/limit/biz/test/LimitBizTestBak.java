package wusc.edu.pay.limit.biz.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wusc.edu.pay.core.limit.biz.AmountLimitBiz;
import wusc.edu.pay.core.limit.biz.LimitSwitchBiz;
import wusc.edu.pay.facade.limit.entity.LimitSwitch;


/**
 * ClassName: PaymentQueryBizTestBak <br/>
 * Function: <br/>
 * date: 2013-11-11 下午1:37:45 <br/>
 * 
 * @author laich
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
public class LimitBizTestBak {

	@Autowired
	private AmountLimitBiz amountLimitBiz;
	@Autowired
	private LimitSwitchBiz limitSwitchBiz;

	@Test
	public void testOpenSwitchList() {
		List<LimitSwitch> list = limitSwitchBiz.findBizFunctionList(12L);
		for (LimitSwitch limit : list) {
			System.out.println(limit.getBizFunctionName());
		}
	}

	/**
	 * 测试交易金额限制
	 */
	@Test
	public void testValidateTradeAmount() {
//		String bizFunction = TrxTypeEnum.PAY.name();
//		String payProduct = "0001";
//		String payWay = "PINGANBANK_NET_B2C";
//		String cardType = "1";
//		BigDecimal tradeAmount = new BigDecimal("400");
//		String merchantNo = "1210002390";
//		amountLimitBiz.validateTradeAmount(bizFunction, payProduct, payWay, cardType, tradeAmount, merchantNo);
	}

	/**
	 * 测试金额累计限制
	 */
	@Test
	public void testCumulateTradeAmount() {
		// String bizFunction = TrxTypeEnum.PAY.toString();
		// String payProduct = "0001";
		// String payWay = "PINGANBANK_NET_B2C";
		// String cardType = "1";
		// BigDecimal tradeAmount = new BigDecimal("500");
		// String merchantNo = "1210002390";
		// Date orderDate = new Date();
		// amountLimitBiz.cumulateTradeAmount(bizFunction, payProduct, payWay, cardType, tradeAmount, merchantNo, orderDate);
	}

}
