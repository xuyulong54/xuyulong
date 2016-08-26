package wusc.edu.pay.boss.biz.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.core.payrule.biz.PayWayBiz;
import wusc.edu.pay.facade.payrule.entity.PayWay;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-context.xml"})
@Transactional 
public class BossBizTestBak {

	@Autowired
	private PayWayBiz  payWayBiz;
	
	@Test 
	public void testGetPayRule(){
		PayWay payWay = payWayBiz.getPayWayBypayWayCode_merchantNo_payWayCode("888000000000000", "PINGANBANK_NET_B2C");
		System.err.println("------"+payWay.getPayProductCode()+"--"+payWay.getDefaultPayInterface());
	}
}
