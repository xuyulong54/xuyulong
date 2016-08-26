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
import wusc.edu.pay.facade.limit.entity.SwitchLimitPack;
import wusc.edu.pay.facade.limit.service.SwitchLimitPackFacade;

import com.alibaba.fastjson.JSONObject;

public class TsSwitchLimitPack extends TestCase {
	
	ClassPathXmlApplicationContext context;
	
	SwitchLimitPackFacade switchLimitPackFacade;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		super.setUp();
		switchLimitPackFacade = (SwitchLimitPackFacade) context.getBean("switchLimitPackFacade");
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		context = null;
	}

	@Test
	public void testSavePayWaySwitch() {
		
		//增加开关限制
		SwitchLimitPack SwitchLimitPack = new SwitchLimitPack();
		
		SwitchLimitPack.setCreateTime(new Date());
		SwitchLimitPack.setDescription("描述");
		SwitchLimitPack.setLastModifyTime(new Date());
		SwitchLimitPack.setName("姓名");
		
		switchLimitPackFacade.saveSwitchLimitPack(SwitchLimitPack);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		PageParam pageParam = new PageParam(1, 50000);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		PageBean pageBean = switchLimitPackFacade.listPage(pageParam, paramMap);
		for (int i = 0; i < pageBean.getRecordList().size(); i++) {
			System.out.println(JSONObject.toJSONString(pageBean.getRecordList().get(i)));
		}
		
	}
}
