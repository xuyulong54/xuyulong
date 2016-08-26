package wusc.edu.pay.facade.limit.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.limit.entity.PayProductSwitch;
import wusc.edu.pay.facade.limit.enums.SwitchLimitStatusEnum;
import wusc.edu.pay.facade.limit.service.PayProductSwitchFacade;

import com.alibaba.fastjson.JSONObject;

public class TsPayProductSwitch extends TestCase {

	ClassPathXmlApplicationContext context;

	PayProductSwitchFacade payProductSwitchFacade;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		super.setUp();
		payProductSwitchFacade = (PayProductSwitchFacade) context.getBean("payProductSwitchFacade");
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		context = null;
	}

	@Test
	public void testSavePayProductSwitch() {

		// 增加支付产品开关
		PayProductSwitch payProductSwitch = new PayProductSwitch();
		// payProductSwitch.setBizFunction(TrxTypeEnum.ACCOUNT_BALANCE_PAY.getDesc());
		payProductSwitch.setCreateTime(new Date());
		payProductSwitch.setLastModifyTime(new Date());
		payProductSwitch.setPayProduct("支付产品");
		payProductSwitch.setStatus(SwitchLimitStatusEnum.OFF);
		payProductSwitch.setSwitchLimitPackId(1L);

		payProductSwitchFacade.savePayProductSwitch(payProductSwitch);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		PageParam pageParam = new PageParam(1, 50000);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		PageBean pageBean = payProductSwitchFacade.listPage(pageParam, paramMap);
		for (int i = 0; i < pageBean.getRecordList().size(); i++) {
			System.out.println(JSONObject.toJSONString(pageBean.getRecordList().get(i)));
		}

	}
}
