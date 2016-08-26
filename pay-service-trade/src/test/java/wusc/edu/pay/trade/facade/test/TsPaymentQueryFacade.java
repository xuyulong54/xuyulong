package wusc.edu.pay.trade.facade.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.trade.service.PaymentQueryFacade;

import com.alibaba.fastjson.JSONObject;

public class TsPaymentQueryFacade extends TestCase {
	ClassPathXmlApplicationContext context;
	PaymentQueryFacade paymentQueryFacade;

	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		super.setUp();
		paymentQueryFacade = (PaymentQueryFacade) context.getBean("paymentQueryFacade");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		context = null;
	}

	public void testQueryPaymentOrder() {
		PageParam pageParam = new PageParam(1, 5000);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("accountNo", account.getAccountNo());
		PageBean pageBean = paymentQueryFacade.queryPaymentOrderListForPage(pageParam, paramMap);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		for (int i = 0; i < pageBean.getRecordList().size(); i++) {
			System.out.println(JSONObject.toJSONString(pageBean.getRecordList().get(i)));
		}
	}

	public void testQueryPaymentRecord() {
		PageParam pageParam = new PageParam(1, 5000);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("accountNo", account.getAccountNo());
		PageBean pageBean = paymentQueryFacade.queryPaymentOrderListForPage(pageParam, paramMap);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		for (int i = 0; i < pageBean.getRecordList().size(); i++) {
			System.out.println(JSONObject.toJSONString(pageBean.getRecordList().get(i)));
		}
	}
}
