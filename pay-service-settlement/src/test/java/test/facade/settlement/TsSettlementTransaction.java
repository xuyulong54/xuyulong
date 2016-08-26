package test.facade.settlement;

import java.rmi.server.UID;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.account.service.AccountTransactionFacade;


public class TsSettlementTransaction extends TestCase {

	ClassPathXmlApplicationContext context;

	AccountTransactionFacade accountTransactionFacade;
	AccountQueryFacade accountQueryFacade;

	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		super.setUp();
		accountTransactionFacade = (AccountTransactionFacade) context.getBean("accountTransactionFacade");
		accountQueryFacade = (AccountQueryFacade) context.getBean("accountQueryFacade");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		context = null;
	}

	public void testA() {
//		Account account = accountQueryFacade.getAccountByUser(148l, 1);
//		System.out.println("============" + account.getAccountNo());
		// accountTransactionFacade.transfer(1, 1, 1, 1, 1, 1, "", "");
	}

	public void testAccountDeposit() {
		long userId = 11l;
		int accountType = 1;
		double amount = 100d;
		double fee = 0d;
		String requestNo = "20011108201310000101";
		String desc = "充值压力测试";
		System.out.println("========开始========");
//		accountTransactionFacade.accountDeposit(userId, accountType, amount, fee, requestNo, desc);
		System.out.println("========结束========");
	}

	public void testBalancePay() {
		System.out.println("========开始========");
		long targetUserId = 11L;
		int targetAccountType = 1;
		long sourceUserId = 16L;
		long agentUserId = 0;
		double targetAmount = 100D;
		double sourceAmount = 100D;
		double agentAmount = 0D;
		double targetFee = 0D;
		double sourceFee = 0D;
		double agentFee = 0D;
		String requestNo = new UID().toString();
		String desc = "账户余额支付压力测试";
//		accountTransactionFacade.balancePay(targetUserId, targetAccountType, sourceUserId, agentUserId, targetAmount, sourceAmount,
//				agentAmount, targetFee, sourceFee, agentFee, requestNo, desc);
		System.out.println("========结束========");
	}

	public void testNetPay() {
		System.out.println("========开始========");
		long sourceUserId = 11l;
		long agentUserId = 0l;
		double sourceAmount = 100;
		double agentAmount = 0;
		double sourceFee = 0;
		double agentFee = 0;
		String requestNo = new UID().toString();
		String desc = "网银支付测试";
//		accountTransactionFacade.netPay(sourceUserId, agentUserId, sourceAmount, agentAmount, sourceFee, agentFee, requestNo, desc);
		System.out.println("========结束========");
	}

	public void testTransfer() {
		System.out.println("========开始========");
		long targetUserId = 11l;
		int targetAccountType = 1;
		long sourceUserId = 16l;
		int sourceAccountType = 1;
		double amount = 100d;
		double fee = 0;
		String requestNo = new UID().toString();
		String desc = "转账测试";
//		accountTransactionFacade.transfer(targetUserId, targetAccountType, sourceUserId, sourceAccountType, amount, fee, 0D, requestNo,
//				desc);
		System.out.println("========结束========");

	}

	public void testBalancePay_Refund() {
		System.out.println("========开始========");
		long targetUserId = 11l;
		int targetAccountType = 1;
		long sourceUserId = 16l;
		long agentUserId = 0;
		double targetAmount = 100;
		double sourceAmount = 100;
		double agentAmount = 0;
		double targetFee = 0;
		double sourceFee = 0;
		double agentFee = 0;
		String requestNo = new UID().toString();
		String desc = "账户余额支付退款测试";
//		accountTransactionFacade.balancePay_Refund(targetUserId, targetAccountType, sourceUserId, agentUserId, targetAmount, sourceAmount,
//				agentAmount, requestNo, desc);
		System.out.println("========结束========");
	}

	public void testNetPay_Refund() {

		System.out.println("========开始========");
		long sourceUserId = 11l;
		long agentUserId = 0;
		double sourceAmount = 100;
		double agentAmount = 0;
		double sourceFee = 0;
		double agentFee = 0;
		String requestNo = new UID().toString();
		String desc = "网银支付退款测试";
//		accountTransactionFacade.netPay_Refund(sourceUserId, agentUserId, sourceAmount, agentAmount, requestNo, desc);
		System.out.println("========结束========");
	}

}
