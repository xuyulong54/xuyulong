package test.biz.settlement;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wusc.edu.pay.core.settlement.biz.SettQueryBiz;


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
public class SettleTestBak {
	@Autowired private SettQueryBiz settQueryBiz;
	@Test
	public void test() {
	}

}