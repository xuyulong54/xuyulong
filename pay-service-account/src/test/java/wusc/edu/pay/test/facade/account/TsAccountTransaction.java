package wusc.edu.pay.test.facade.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import wusc.edu.pay.facade.account.enums.AccountFundDirectionEnum;
import wusc.edu.pay.facade.account.enums.AccountTradeTypeEnum;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.account.service.AccountManagementFacade;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.account.service.AccountTransactionFacade;
import wusc.edu.pay.facade.account.vo.AccountTransactionVo;


public class TsAccountTransaction extends TestCase {

	ClassPathXmlApplicationContext context;

	AccountTransactionFacade accountTransactionFacade;
	AccountManagementFacade accountManagementFacade;

	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		super.setUp();
		accountTransactionFacade = (AccountTransactionFacade) context.getBean("accountTransactionFacade");
		accountManagementFacade = (AccountManagementFacade) context.getBean("accountManagementFacade");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		context = null;
	}
}
