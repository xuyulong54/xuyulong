package wusc.edu.pay.test.facade.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import wusc.edu.pay.facade.account.enums.AccountFundDirectionEnum;
import wusc.edu.pay.facade.account.enums.AccountTradeTypeEnum;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.account.service.AccountManagementFacade;
import wusc.edu.pay.facade.account.service.AccountTransactionFacade;
import wusc.edu.pay.facade.account.vo.AccountTransactionVo;


/**
 * 压力测试
 * 
 * @author healy
 * 
 */
public class TsAccountTransactionM extends TestCase {

	ClassPathXmlApplicationContext context;

	AccountTransactionFacade accountTransactionFacade;
	AccountManagementFacade accountManagementFacade;

	int completeThread = 0;

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

	public void testA() {
		TestRunnable runner = new TestRunnable() {
			@Override
			public void runTest() throws Throwable {
				testExecute();
			}
		};
		
		TestRunnable runner1 = new TestRunnable() {
			@Override
			public void runTest() throws Throwable {
				testExecute1();
			}
		};

		int runnerCount = 100; // 开启100线程
		TestRunnable[] trs = new TestRunnable[runnerCount];
		for (int i = 0; i < runnerCount; i++) {
			trs[i] = runner;
		}
		
		trs[50] = runner1;

		MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
		try {
			mttr.runTestRunnables();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("completeThread:" + completeThread);
	}

	public void testExecute() {
		// 创建新账户
		String userNo = "888100000001206";

		List<AccountTransactionVo> list = new ArrayList<AccountTransactionVo>();

		String requestNo = String.valueOf(new Date().getTime()) + String.valueOf(new Random().nextInt(1000000) + 1);
		System.out.println(requestNo);
		AccountTransactionVo vo = new AccountTransactionVo();
		vo.setUserNo(userNo);
		vo.setAmount(1000);
		vo.setAccountFundDirection(AccountFundDirectionEnum.ADD);
		vo.setDesc(AccountTradeTypeEnum.NET_B2C_PAY.getDesc());
		vo.setTradeType(AccountTradeTypeEnum.NET_B2C_PAY);
		vo.setRequestNo(requestNo);
		list.add(vo);

		requestNo = String.valueOf(new Date().getTime()) + String.valueOf(new Random().nextInt(1000000) + 1);
		System.out.println(requestNo);
		vo = new AccountTransactionVo();
		vo.setUserNo(userNo);
		vo.setAmount(1000);
		vo.setAccountFundDirection(AccountFundDirectionEnum.SUB);
		vo.setDesc(AccountTradeTypeEnum.NET_B2C_PAY.getDesc());
		vo.setTradeType(AccountTradeTypeEnum.NET_B2C_PAY);
		vo.setRequestNo(requestNo);
		list.add(vo);

		accountTransactionFacade.execute(list);
		
		completeThread += 1;
	}

	public void testExecute1() {
		// 创建新账户
		String userNo = "888100058121205";

		List<AccountTransactionVo> list = new ArrayList<AccountTransactionVo>();

		String requestNo = String.valueOf(new Date().getTime()) + String.valueOf(new Random().nextInt(1000000) + 1);
		System.out.println(requestNo);
		AccountTransactionVo vo = new AccountTransactionVo();
		vo.setUserNo(userNo);
		vo.setAmount(1000);
		vo.setAccountFundDirection(AccountFundDirectionEnum.ADD);
		vo.setDesc(AccountTradeTypeEnum.NET_B2C_PAY.getDesc());
		vo.setTradeType(AccountTradeTypeEnum.NET_B2C_PAY);
		vo.setRequestNo(requestNo);
		list.add(vo);

		accountTransactionFacade.execute(list);
		
		completeThread += 1;
	}
	
	private void batchCredit() {
		// 创建新账户
		String accountNo = accountManagementFacade.buildAccountNo(AccountTypeEnum.PRIVATE);
		//accountManagementFacade.createPrivateAccount(accountNo, accountNo);

		List<AccountTransactionVo> list = new ArrayList<AccountTransactionVo>();

		// 同一账户批量加款(1w笔)
		for (int i = 0; i < 1000; i++) {
			String requestNo = String.valueOf(new Date().getTime()) + String.valueOf(new Random().nextInt(1000000) + 1);
			System.out.println(requestNo);

			AccountTransactionVo vo = new AccountTransactionVo();
			vo.setUserNo(accountNo);
			vo.setAmount(Double.valueOf(Math.random() * 1000));
			vo.setAccountFundDirection(AccountFundDirectionEnum.ADD);
			vo.setDesc(AccountTradeTypeEnum.NET_B2C_PAY.getDesc());
			vo.setTradeType(AccountTradeTypeEnum.NET_B2C_PAY);
			vo.setRequestNo(requestNo);
			list.add(vo);
		}

		accountTransactionFacade.batchCreditForSameAccount(list);

		completeThread += 1;
	}
}
