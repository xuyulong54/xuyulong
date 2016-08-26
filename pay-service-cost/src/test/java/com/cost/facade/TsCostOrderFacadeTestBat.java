package com.cost.facade;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import wusc.edu.pay.facade.cost.entity.CalCostOrder;
import wusc.edu.pay.facade.cost.service.CalCostOrderFacade;

public class TsCostOrderFacadeTestBat  extends TestCase {
	ClassPathXmlApplicationContext context;
	CalCostOrderFacade calCostOrderFacade;
	
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		super.setUp();

		calCostOrderFacade = (CalCostOrderFacade) context.getBean("calCostOrderFacade");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		context = null;
	}
	public void testA() {
		CalCostOrder order = new CalCostOrder();
		/**
		 * 维度
		 *	PINGANBANK-GZ-SHOUKUAN 	-	维度A
		 *	ABC-GZ-SHOUKUAN 		-	维度B
		 **/
		order.setCalInterface("PINGANBANK-GZ-SHOUKUAN");
		/**
		 * 交易金额
		 */
		order.setAmount(BigDecimal.valueOf(10));
		
		order.setAmount(BigDecimal.valueOf(100));
//		order.setBankorderNo(new SimpleDateFormat("HHMMSS").format(new Date()));
		order.setFromSystem("1");
		order.setMerchantNo("888000000000000");
//		order.setMerchantOrderno("m"+new SimpleDateFormat("HHMMSS").format(new Date()));
		//BigDecimal fee = calCostOrderFacade.calulateBankCost(order);
		System.out.println("==============================");
		//System.out.println("银行手续费: " + fee);
		System.out.println("==============================");
	}
}
