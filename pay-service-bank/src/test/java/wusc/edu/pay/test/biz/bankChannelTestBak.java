package wusc.edu.pay.test.biz;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wusc.edu.pay.facade.bank.service.BankChannelFacade;


/**
 * ClassName: AccountHistoryFaceTest <br/>
 * Function: <br/>
 * date: 2013-11-7 下午8:02:34 <br/>
 * 
 * @author laich
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context-bank.xml" })
// @Transactional
public class bankChannelTestBak {

	@Autowired
	private BankChannelFacade bankChannelFacade;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void test() {
		List list = bankChannelFacade.listChannalByAgreementBusTypeAndAccountType(2, 1);
		System.out.println(list.size());
		}
}