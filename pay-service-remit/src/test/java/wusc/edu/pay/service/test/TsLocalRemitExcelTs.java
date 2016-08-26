/**
 * com
 * className：Test.java
 * @version：1.0
 * date: 2014-9-19-下午2:07:50
 *   2014中益智正公司-版权所有
 */
package wusc.edu.pay.service.test;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wusc.edu.pay.core.remit.biz.sub.RemitRouterBiz;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * className：TsLocalRemitTs Function：
 * 
 * @author laich
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
public class TsLocalRemitExcelTs {

	@Autowired
	private RemitRouterBiz remitRouterBiz;

	@Test
	public void testA() {
		remitRouterBiz.lanunchBatch();
	}

}
