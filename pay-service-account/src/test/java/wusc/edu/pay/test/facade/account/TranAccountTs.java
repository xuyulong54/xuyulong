/**
 * className：TranPaymentTs.java
 * @version：1.0
 * date: 2014-10-31-下午2:13:43
 * Copyright (c)  2014中益智正公司-版权所有
 */
package wusc.edu.pay.test.facade.account;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wusc.edu.pay.core.account.biz.AccountTransactionBiz;
import wusc.edu.pay.facade.account.enums.AccountTradeTypeEnum;


/**
 * className：TranAccountTs Function： 多线程测试Mysql for update 行级锁， date:
 * 2014-10-31-下午2:13:43
 * 
 * @author laich
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
// @Transactional 不添加事务处理，即默认用Mysql自动提交方式 auto commit();
public class TranAccountTs {

	@Autowired
	private AccountTransactionBiz acountTransactionBiz;
	int completeThread = 0;

	@Test
	public void testCommitTran() {
		TestRunnable runner = new TestRunnable() {
			@Override
			public void runTest() throws Throwable {
				acountTransactionBiz.credit("888000010001177", 2.00d, "", AccountTradeTypeEnum.NET_B2C_PAY, "test", 0, 0D);
				completeThread += 1;
			}
		};

		int runnerCount = 50; // 开启100线程
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
		System.err.println("completeThread:" + completeThread);

		// 实验结果:Mysql 支持行级锁
	}

}
