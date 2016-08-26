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
import wusc.edu.pay.facade.limit.entity.PayWaySwitch;
import wusc.edu.pay.facade.limit.enums.SwitchLimitStatusEnum;
import wusc.edu.pay.facade.limit.service.PayWaySwitchFacade;

import com.alibaba.fastjson.JSONObject;

public class TsPayWaySwitch extends TestCase {

	ClassPathXmlApplicationContext context;

	PayWaySwitchFacade payWaySwitchFacade;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		super.setUp();
		payWaySwitchFacade = (PayWaySwitchFacade) context.getBean("payWaySwitchFacade");
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		context = null;
	}

	@Test
	public void testSavePayWaySwitch() {

		// 增加支付方式开关
		PayWaySwitch payWaySwitch = new PayWaySwitch();
		//payWaySwitch.setBizFunction(TrxTypeEnum.ACCOUNT_BALANCE_PAY.getDesc());
		payWaySwitch.setCreateTime(new Date());
		payWaySwitch.setLastModifyTime(new Date());
		payWaySwitch.setPayProduct("支付产品");
		payWaySwitch.setPayWay("支付方式");
		payWaySwitch.setStatus(SwitchLimitStatusEnum.OFF);
		payWaySwitch.setSwitchLimitPackId(1L);

		payWaySwitchFacade.savePayWaySwitch(payWaySwitch);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		PageParam pageParam = new PageParam(1, 50000);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		PageBean pageBean = payWaySwitchFacade.listPage(pageParam, paramMap);
		for (int i = 0; i < pageBean.getRecordList().size(); i++) {
			System.out.println(JSONObject.toJSONString(pageBean.getRecordList().get(i)));
		}

	}
}
