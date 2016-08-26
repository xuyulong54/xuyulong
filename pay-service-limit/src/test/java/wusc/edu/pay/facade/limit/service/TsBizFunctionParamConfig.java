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
import wusc.edu.pay.facade.limit.entity.BizFunctionParamConfig;
import wusc.edu.pay.facade.limit.service.BizFunctionParamConfigFacade;

import com.alibaba.fastjson.JSONObject;

public class TsBizFunctionParamConfig extends TestCase {

	ClassPathXmlApplicationContext context;

	BizFunctionParamConfigFacade bizFunctionParamConfigFacade;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		super.setUp();
		bizFunctionParamConfigFacade = (BizFunctionParamConfigFacade) context.getBean("bizFunctionParamConfigFacade");
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		context = null;
	}

	@Test
	public void testSaveBizFunctionParamConfig() {

		// 增加业务功能参数配置
		BizFunctionParamConfig bizFunctionParamConfig = new BizFunctionParamConfig();
//		bizFunctionParamConfig.setBizFunction(TrxTypeEnum.ACCOUNT_BALANCE_PAY.getDesc());
		bizFunctionParamConfig.setCreateTime(new Date());
		bizFunctionParamConfig.setLastModifyTime(new Date());
		bizFunctionParamConfig.setMerchantNo("1111111111");
		bizFunctionParamConfig.setParamName("参数名称");
		bizFunctionParamConfig.setParamValue("参数值");

		bizFunctionParamConfigFacade.saveBizFunctionParamConfig(bizFunctionParamConfig);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		PageParam pageParam = new PageParam(1, 50000);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		PageBean pageBean = bizFunctionParamConfigFacade.listPage(pageParam, paramMap);
		for (int i = 0; i < pageBean.getRecordList().size(); i++) {
			System.out.println(JSONObject.toJSONString(pageBean.getRecordList().get(i)));
		}

	}
}
