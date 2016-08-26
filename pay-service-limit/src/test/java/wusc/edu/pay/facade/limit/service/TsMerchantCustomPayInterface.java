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
import wusc.edu.pay.facade.limit.entity.MerchantCustomPayInterface;
import wusc.edu.pay.facade.limit.enums.MerchantCustomPayInterfaceStatusEnum;
import wusc.edu.pay.facade.limit.service.MerchantCustomPayInterfaceFacade;

import com.alibaba.fastjson.JSONObject;

public class TsMerchantCustomPayInterface extends TestCase {

	ClassPathXmlApplicationContext context;

	MerchantCustomPayInterfaceFacade merchantCustomPayInterfaceFacade;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		super.setUp();
		merchantCustomPayInterfaceFacade = (MerchantCustomPayInterfaceFacade) context.getBean("merchantCustomPayInterfaceFacade");
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		context = null;
	}

	@Test
	public void testSaveMerchantCustomPayInterface() {

		// 增加支付接口分流
		MerchantCustomPayInterface merchantCustomPayInterface = new MerchantCustomPayInterface();
		merchantCustomPayInterface.setCreateTime(new Date());
		merchantCustomPayInterface.setLastModifyTime(new Date());
		merchantCustomPayInterface.setMerchantNo("1111111111");
		merchantCustomPayInterface.setPayInterface("ICBC_NET_GZ");
		//merchantCustomPayInterface.setPayWay(TrxTypeEnum.ACCOUNT_ADJUST.getDesc());
		merchantCustomPayInterface.setStatus(MerchantCustomPayInterfaceStatusEnum.ACTIVITY);

		merchantCustomPayInterfaceFacade.saveMerchantCustomPayInterface(merchantCustomPayInterface);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		PageParam pageParam = new PageParam(1, 50000);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		PageBean pageBean = merchantCustomPayInterfaceFacade.listPage(pageParam, paramMap);
		for (int i = 0; i < pageBean.getRecordList().size(); i++) {
			System.out.println(JSONObject.toJSONString(pageBean.getRecordList().get(i)));
		}

	}
}
