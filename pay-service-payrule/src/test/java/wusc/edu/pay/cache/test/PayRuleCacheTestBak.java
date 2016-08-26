package wusc.edu.pay.cache.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wusc.edu.pay.common.utils.cache.redis.RedisUtils;
import wusc.edu.pay.core.payrule.dao.PayWayDao;
import wusc.edu.pay.facade.payrule.entity.PayProduct;
import wusc.edu.pay.facade.payrule.entity.PayRule;
import wusc.edu.pay.facade.payrule.service.PayProductFacade;
import wusc.edu.pay.facade.payrule.service.PayRuleFacade;
import wusc.edu.pay.facade.payrule.service.PayWayFacade;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-context.xml"})
//@Transactional 
public class PayRuleCacheTestBak {

	@Autowired
	private PayWayFacade payWayFacade;
	@Autowired
	private PayProductFacade payProductFacade;
	@Autowired
	private PayRuleFacade payRuleFacade;
	
	@Autowired
	private PayWayDao payWayDao;
	
	@Test
	public void testCache(){
		RedisUtils ru = new RedisUtils();
		///ru.flushAll();/**清空所有缓存 */
		System.out.println("start-------");
		PayProduct pp=payProductFacade.getPayProductById(21l);
		payProductFacade.getPayProductById(21l);
		payProductFacade.updatePayWayProduct(pp.getId(), pp.getPayProductName(), pp.getStatus());
		payProductFacade.getPayProductById(21l);
		System.out.println(payWayFacade.getPayWayById(36l));
	}
	
	/**
	 * 测试 redis
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testCache2(){
/*		RedisUtils ru = new RedisUtils();
		ru.flushAll();*/
		payWayFacade.findPayWayByUserNo("1210002390", null);
		payWayFacade.findPayWayByUserNo("1210002390", null);
		RedisUtils ru = new RedisUtils();
		ru.save("gzzyzz", new Date());
		System.out.println("start-------");
		
		
		///PayRule payRule = payRuleFacade.getPayRuleById(3l);
		payRuleFacade.updatePayRule(3l, "支付规则1-test", 100, "tttt");
		
		System.err.println(ru.get("gzzyzz")+"---------------");
		payWayFacade.findPayWayByUserNo("1210002390", null);
		
	

	}
	/**
	 * 测试 redis  不同Facade 测试证明：不同 Facade 之间不影响 Cache 缓存组织
	 */
	@Test
	public void testCache3(){
	
		System.out.println("start-------");
		RedisUtils ru = new RedisUtils();
		ru.save("gzzyzz", new Date());
		List<?>  list = payWayFacade.findPayWayByUserNo("1210002390", null);
		System.out.println(list.size());
		payWayFacade.findPayWayByUserNo("1210002390", null);
//		payWayFacade.getPayWayById(36l);
		PayRule payRule = payRuleFacade.getPayRuleById(2l);
		payRuleFacade.updatePayRule(payRule.getId(), payRule.getRuleName(), payRule.getRuleType(), "tttt");
		
		payWayFacade.findPayWayByUserNo("1210002390", null);
		payWayFacade.findPayWayByUserNo("1210002390", null);
		payWayFacade.getPayWayById(36l);
		System.err.println(ru.get("gzzyzz")+"---------------");
	}
	
	@Test
	public void testUpCache(){
		payWayFacade.updatePayWaySwitch(208l, 101);
	}
}
