package wusc.edu.pay.test.facade.account;

import junit.framework.TestCase;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.account.service.AccountManagementFacade;


/**
 * 多线程压力测试
 * 
 * @author healy
 * 
 */
public class TsAccountManagementM extends TestCase {

	ClassPathXmlApplicationContext context;

	AccountManagementFacade accountManagementFacade;

	int completeThread = 0;

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

		TestRunnable runner = new TestRunnable() {
			@Override
			public void runTest() throws Throwable {
				String accountNo = accountManagementFacade.buildAccountNo(AccountTypeEnum.MERCHANT);
				long result = accountManagementFacade.createAccount(accountNo, accountNo, AccountTypeEnum.MERCHANT.getValue());
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println(result);
				completeThread += 1;
			}
		};

		int runnerCount = 101; // 开启100线程
		TestRunnable[] trs = new TestRunnable[runnerCount];
		for (int i = 0; i < runnerCount; i++) {
			trs[i] = runner;
		}

		MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
		try {
			mttr.runTestRunnables();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("completeThread:" + completeThread);
	}
}
