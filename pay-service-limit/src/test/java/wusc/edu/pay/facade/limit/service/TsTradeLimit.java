package wusc.edu.pay.facade.limit.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wusc.edu.pay.common.enums.CardTypeEnum;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.exptions.AmountOverLimitException;
import wusc.edu.pay.facade.limit.service.TradeLimitFacade;

import com.alibaba.fastjson.JSONObject;

public class TsTradeLimit extends TestCase {
	
	ClassPathXmlApplicationContext context;
	
	TradeLimitFacade tradeLimitFacade;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		super.setUp();
		tradeLimitFacade = (TradeLimitFacade) context.getBean("tradeLimitFacade");
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		context = null;
	}

	public void testCumulateTradeAmount(){
		
		//累计金额
		LimitTrxTypeEnum limitTrxTypeEnum = LimitTrxTypeEnum.ACCOUNT_ATM;
		String payProduct = "支付产品";
		String payWay = "支付方式";
		String cardType = CardTypeEnum.ID_CARD.getDesc();
		BigDecimal tradeAmount = BigDecimal.valueOf(100);
		String merchantNo = "1111111111";
		Date orderDate = new Date();
		
		tradeLimitFacade.cumulateTradeAmount(limitTrxTypeEnum, payProduct, payWay, cardType, tradeAmount, merchantNo, orderDate);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	@Test
	public void testValidateSwitchAvailable(){
		String merchantNo = "1111111111";
		LimitTrxTypeEnum limitTrxTypeEnum = LimitTrxTypeEnum.ACCOUNT_ATM;
		String payProduct = "支付产品";
		String payWay = "支付方式";
		
		tradeLimitFacade.validateSwitchAvailable(merchantNo, limitTrxTypeEnum, payProduct, payWay);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
}
