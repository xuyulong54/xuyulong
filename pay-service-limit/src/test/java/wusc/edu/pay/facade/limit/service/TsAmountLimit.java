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
import wusc.edu.pay.facade.limit.entity.AmountLimit;
import wusc.edu.pay.facade.limit.entity.AmountLimitPack;
import wusc.edu.pay.facade.limit.enums.LimitTypeEnum;
import wusc.edu.pay.facade.limit.service.AmountLimitManagementFacade;

import com.alibaba.fastjson.JSONObject;

public class TsAmountLimit extends TestCase {

	ClassPathXmlApplicationContext context;

	AmountLimitManagementFacade amountLimitManagementFacade;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		super.setUp();
		amountLimitManagementFacade = (AmountLimitManagementFacade) context.getBean("amountLimitManagementFacade");
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		context = null;
	}

	@Test
	public void testAddAmountLimit() {
		// 新增金额限制
		AmountLimit amountLimit = new AmountLimit();
		amountLimit.setAmountLimitPackId(1L);
		// amountLimit.setBizFunction(TrxTypeEnum.ACCOUNT_TRANSFER.getDesc());
		amountLimit.setCardType(CardTypeEnum.ID_CARD.getDesc());
		amountLimit.setCreateTime(new Date());
		amountLimit.setExpression("自定义限制表达式");
		amountLimit.setLastModifyTime(new Date());
		amountLimit.setLimitType(LimitTypeEnum.SINGLE);
		amountLimit.setMaxAmount(BigDecimal.valueOf(1000));
		amountLimit.setMinAmount(BigDecimal.valueOf(10));
		amountLimit.setPayProduct("支付产品");
		amountLimit.setPayWay("支付方式");

		amountLimitManagementFacade.addAmountLimit(amountLimit);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		PageParam pageParam = new PageParam(1, 50000);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		PageBean pageBean = amountLimitManagementFacade.queryAmountLimitPage(pageParam, paramMap);
		for (int i = 0; i < pageBean.getRecordList().size(); i++) {
			System.out.println(JSONObject.toJSONString(pageBean.getRecordList().get(i)));
		}

	}

	@Test
	public void testAddAmountLimitPack() {

		// 新增金额限制包
		AmountLimitPack amountLimitPack = new AmountLimitPack();
		amountLimitPack.setCreateTime(new Date());
		amountLimitPack.setDescription("描述");
		amountLimitPack.setLastModifyTime(new Date());
		amountLimitPack.setName("金额包名称");

		amountLimitManagementFacade.addAmountLimitPack(amountLimitPack);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		PageParam pageParam = new PageParam(1, 50000);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		PageBean pageBean = amountLimitManagementFacade.queryAmountLimitPackPage(pageParam, paramMap);
		for (int i = 0; i < pageBean.getRecordList().size(); i++) {
			System.out.println(JSONObject.toJSONString(pageBean.getRecordList().get(i)));
		}

	}

}
