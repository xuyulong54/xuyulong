package wusc.edu.pay.trade.facade.test;

import java.util.Date;

import junit.framework.TestCase;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import wusc.edu.pay.common.enums.CurrencyTypeEnum;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.trade.enums.OrderFromTypeEnum;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.enums.TradePaymentTypeEnum;
import wusc.edu.pay.facade.trade.enums.TrxModelEnum;
import wusc.edu.pay.facade.trade.service.PaymentFacade;
import wusc.edu.pay.facade.trade.vo.PaymentOrderVo;
import wusc.edu.pay.facade.trade.vo.PaymentRecordVo;


public class TsPaymentFacadeM extends TestCase {

	ClassPathXmlApplicationContext context;

	PaymentFacade paymentFacade;

	int completeThread = 0;

	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		super.setUp();
		paymentFacade = (PaymentFacade) context.getBean("paymentFacade");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		context = null;
	}

	public void testA() {
		TestRunnable runner = new TestRunnable() {
			@Override
			public void runTest() throws Throwable {
				createPaymentOrder();
			}
		};

		int runnerCount = 10; // 开启100线程
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

	private void createPaymentOrder() {
		for (int i = 0; i < 100; i++) {
			PaymentOrderVo vo = new PaymentOrderVo();
			vo.setCur(CurrencyTypeEnum.RMB.getValue());
			vo.setMerchantName("收款测试商户");
			vo.setMerchantNo("888000000000000");
			vo.setMerchantOrderNo(paymentFacade.buildPaymentOrderNo());
			vo.setNotifyUrl("http://127.0.0.1");
			vo.setOrderAmount("200.00");
			vo.setOrderDate(new Date());
			vo.setOrderFrom(OrderFromTypeEnum.GATEWAY_B2C.getValue());
			vo.setOrderIp("127.0.0.1");
			vo.setOrderRefererUrl("bank");
			vo.setOrderTime(new Date());
			vo.setProductDesc("测试商品");
			vo.setProductName("测试商品");
			vo.setReturnUrl("http:/back.url");
			vo.setPayerLoginName("999000000000000@gzzyzz.com");

			paymentFacade.createPaymentOrder(vo);

			PaymentRecordVo voo = new PaymentRecordVo();
			voo.setBankOrderNo(paymentFacade.buildBankOrderNo());
			voo.setBizType(TradeBizTypeEnum.ONLINE_ACQUIRING.getValue());
			voo.setCur(CurrencyTypeEnum.RMB.getValue());
			voo.setMerchantName(vo.getMerchantName());
			voo.setMerchantNo(vo.getMerchantNo());
			voo.setMerchantOrderNo(vo.getMerchantOrderNo());
			voo.setNotifyUrl(vo.getNotifyUrl());
			voo.setOrderAmount(new java.math.BigDecimal(vo.getOrderAmount()));
			voo.setOrderFrom(vo.getOrderFrom());
			voo.setOrderIp(vo.getOrderIp());
			voo.setOrderRefererUrl(vo.getOrderRefererUrl());
			voo.setPayerAccountType(TradePaymentTypeEnum.FAST_PAY.getValue());
			voo.setPayerName("测试付款人");
			voo.setPayerUserNo("0000000000");
			voo.setPayInterfaceCode("FAST-ICBC-GZ");
			voo.setPayInterfaceName("工行快捷支付-gz");
			voo.setPayProductCode("001");
			voo.setPayProductName("支付产品001");
			voo.setPayWayCode("FAST-ICBC");
			voo.setPayWayName("工行快捷");
			voo.setProductDesc(vo.getProductDesc());
			voo.setProductName(vo.getProductName());
			voo.setReceiverAccountType(AccountTypeEnum.MERCHANT.getValue());
			voo.setReturnUrl(vo.getReturnUrl());
			voo.setTrxModel(TrxModelEnum.GUARANTEE.getValue());
			voo.setTrxNo(paymentFacade.buildPaymentTrxNo());

			paymentFacade.createPaymentRecord(voo);
		}

		completeThread += 1;
	}
}
