package wusc.edu.pay.test.facade.account;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;

import com.alibaba.fastjson.JSONObject;

public class TsAccountQuery extends TestCase {

	ClassPathXmlApplicationContext context;

	AccountQueryFacade accountQueryFacade;

	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		super.setUp();
		accountQueryFacade = (AccountQueryFacade) context.getBean("accountQueryFacade");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		context = null;
	}

	public void testA() {
		Account account = accountQueryFacade.getAccountByUserNo("80080041000002620101");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(JSONObject.toJSONString(account));

		PageParam pageParam = new PageParam(1, 50000);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//paramMap.put("accountNo", account.getAccountNo());
		PageBean pageBean = accountQueryFacade.queryAccountHistoryListPage(pageParam, paramMap);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		for (int i = 0; i < pageBean.getRecordList().size(); i++) {
			System.out.println(JSONObject.toJSONString(pageBean.getRecordList().get(i)));
		}

	}
	
	public static void main(String[] args){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("r1_MerchantNo", true);
		map.put("r2_RefundOrderNo", 123L);
		map.put("r3_RefundAmount", "abcd");
		
		System.out.println(JSONObject.toJSONString(map));
	}
}
