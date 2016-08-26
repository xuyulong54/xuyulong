package wusc.edu.pay.fee.facade.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wusc.edu.pay.facade.fee.service.CalculateFeeFacade;


/**
 * ClassName: AccountHistoryFaceTest <br/>
 * Function: <br/>
 * date: 2013-11-7 下午8:02:34 <br/>
 * 
 * @author laich
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
// @Transactional
public class FeeFacadeTestBak {

	@Autowired
	private CalculateFeeFacade calculateFeeFacade;
	@Test
	public void calculateFee() {
//		calculateFeeFacade.caculateFee("1100000097", 1, 1, "0001", "PINGANBANK_NET_B2C", 1000D, new Date(),"大神","NO123456","201407141106");
	}
	
}