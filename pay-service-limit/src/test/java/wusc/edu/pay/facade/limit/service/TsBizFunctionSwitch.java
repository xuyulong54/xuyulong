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
import wusc.edu.pay.facade.limit.entity.BizFunctionSwitch;
import wusc.edu.pay.facade.limit.enums.SwitchLimitStatusEnum;
import wusc.edu.pay.facade.limit.service.BizFunctionSwitchFacade;

import com.alibaba.fastjson.JSONObject;

public class TsBizFunctionSwitch extends TestCase {

	ClassPathXmlApplicationContext context;

	BizFunctionSwitchFacade bizFunctionSwitchFacade;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		super.setUp();
		bizFunctionSwitchFacade = (BizFunctionSwitchFacade) context.getBean("bizFunctionSwitchFacade");
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		context = null;
	}

	@Test
	public void testSaveBizFunctionSwitch() {

		// 增加业务功能开关
		BizFunctionSwitch bizFunctionSwitch = new BizFunctionSwitch();
//		bizFunctionSwitch.setBizFunction(TrxTypeEnum.ACCOUNT_BALANCE_PAY.getDesc());
		bizFunctionSwitch.setCreateTime(new Date());
		bizFunctionSwitch.setLastModifyTime(new Date());
		bizFunctionSwitch.setStatus(SwitchLimitStatusEnum.OFF);
		bizFunctionSwitch.setSwitchLimitPackId(1L);

		bizFunctionSwitchFacade.saveBizFunctionSwitch(bizFunctionSwitch);

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		PageParam pageParam = new PageParam(1, 50000);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		PageBean pageBean = bizFunctionSwitchFacade.listPage(pageParam, paramMap);
		for (int i = 0; i < pageBean.getRecordList().size(); i++) {
			System.out.println(JSONObject.toJSONString(pageBean.getRecordList().get(i)));
		}

	}
}
