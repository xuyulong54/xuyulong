package wusc.edu.pay.test.facade.account;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.account.service.AccountManagementFacade;


public class TsAccountManagement extends TestCase {

	ClassPathXmlApplicationContext context;

	AccountManagementFacade accountManagementFacade;

	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		super.setUp();
		accountManagementFacade = (AccountManagementFacade) context.getBean("accountManagementFacade");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		context = null;
	}

	public void testCreateAccount() {
		String accountNo = accountManagementFacade.buildAccountNo(AccountTypeEnum.MERCHANT);
		long result = accountManagementFacade.createAccount(accountNo, accountNo, AccountTypeEnum.MERCHANT.getValue());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(result);
	}
}
