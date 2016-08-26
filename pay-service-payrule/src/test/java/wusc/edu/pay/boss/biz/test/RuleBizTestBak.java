package wusc.edu.pay.boss.biz.test;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wusc.edu.pay.core.payrule.biz.PayRuleBiz;
import wusc.edu.pay.core.payrule.biz.PayWayBiz;
import wusc.edu.pay.core.payrule.dao.PayProductSwitchDao;
import wusc.edu.pay.facade.payrule.entity.vo.BindSwitchVo;
import wusc.edu.pay.facade.payrule.entity.vo.FrpSelectVo;
import wusc.edu.pay.facade.payrule.entity.vo.PayWayVo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
// @Transactional
public class RuleBizTestBak {

	@Autowired
	private PayRuleBiz payRuleBiz;

	@Autowired
	private PayProductSwitchDao payProductSwitchDao;
	@Autowired
	private PayWayBiz payWayBiz;

	@Test
	public void testPayRule() {
		List<PayWayVo> list = payWayBiz.findPayWayByUserNo("userno", "NET_B2B");
		for (Iterator<PayWayVo> iterator = list.iterator(); iterator.hasNext();) {
			PayWayVo vo = (PayWayVo) iterator.next();
			System.out.println(vo.getPayWayCode() + "|" + vo.getPayProductName() + "|" + vo.getPayProductCode() + "|" + vo.getSorts() + "|" + vo.getBusType() + "|" + vo.getBankCode());
		}

	}

	@Test
	public void testpayWayProductBiz() {
		List<FrpSelectVo> list = payWayBiz.queryFrpSelectVos("SINA_001");
		for (Iterator<FrpSelectVo> iterator = list.iterator(); iterator.hasNext();) {
			FrpSelectVo vo = (FrpSelectVo) iterator.next();
			System.out.println(vo.getFrpCode() + "|" + vo.getBankName() + "|" + vo.getPayProductCode() + "|" + vo.getIsUse() + "|" + vo.getBusType() + "|" + vo.getPayWayId());
		}
	}

	@Test
	public void testpayWayProductBiz2() {
		@SuppressWarnings("rawtypes")
		List list = payProductSwitchDao.findPayProductSwitchByPayProductCode("testtt1111");
		System.out.println(list);
		if (list != null && !list.isEmpty()) {
			System.out.println(33333333);
		}
	}

	@Test
	public void listProductByRuleId() {
		List<BindSwitchVo> list = payRuleBiz.listProductByRuleId(1l);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			BindSwitchVo bindSwitchVo = (BindSwitchVo) iterator.next();
			System.err.println(bindSwitchVo.getPayProductCode() + "--" + bindSwitchVo.getPayWayCode());
		}
	}

}
