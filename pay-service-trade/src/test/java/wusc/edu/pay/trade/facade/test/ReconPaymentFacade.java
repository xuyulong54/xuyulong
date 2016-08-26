package wusc.edu.pay.trade.facade.test;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import junit.framework.TestCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wusc.edu.pay.core.trade.dao.PaymentRecordDao;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.service.PaymentFacade;


public class ReconPaymentFacade extends TestCase {
	ClassPathXmlApplicationContext context;
	@Autowired
	PaymentFacade paymentFacade;
	@Autowired
	PaymentRecordDao paymentRecordDao;

	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "dubbo-consumer-order-test.xml" });
		super.setUp();
		paymentFacade = (PaymentFacade) context.getBean("paymentFacade");
	}

	/**
	 * 业务对数据（同时添加交易记录表记录）
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public void testCreatePaymentRecord_BankOrder() throws IOException, ParseException {
		System.out.println("=======开始=======");
		// 7|GDWZ 1381816762263 10.02220131012 20131015 14:02:03
		FileWriter fileWriter = new FileWriter("D:\\对账文件\\平安银行\\bus.txt");
		fileWriter.write("     9");

		// -----------清算---------------------------------------------
		FileWriter fileWriter1 = new FileWriter("D:\\对账文件\\平安银行\\cle.txt");
		String orderTotalCount = "    16"; // 订单总笔数
		String orderTotalAmt = "                 0.0"; // 定单总金额
		String reckoningAmt = "                 0.0";// 清算金额
		String chargeTotalAmt = "                 0.0";// 手续费总金额
		String creditTotalOrderCount = "     0";// 信用卡总笔数
		String creditTotalOrderAmt = "                 0.0";// 信用卡总金额
		String creditfree = "                 0.0"; // 信用卡手续费
		String debitTotalOrderCount = "     0"; // 借记卡总笔数
		String debitTotalOrderAmt = "                 0.0";
		String debitCharge = "                 0.0" + "|";
		String returnNmber = "     0";// 返回信息笔数

		fileWriter1.write(orderTotalCount + orderTotalAmt + reckoningAmt + chargeTotalAmt + creditTotalOrderCount + creditTotalOrderAmt
				+ creditfree + debitTotalOrderCount + debitTotalOrderAmt + debitCharge + returnNmber);
		// ---------------------清算--------------------------------------------------------------------

		for (int i = 0; i < 16; i++) {
			String bankOrder = paymentFacade.buildBankOrderNo();// 银行订单号
			// String order =
			// paymentFacade.buildMerchantOrderNo(TrxTypeEnum.NET_B2C_PAY.getValue());//商户订单号
			// 金额
			String random1 = String.valueOf(new Random().nextInt(100) + 100);
			int status = 1;
			int cardType = Math.random() > 0.5 ? 1 : 2; // 卡类型
			SimpleDateFormat LONG_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// String expireTime =DateUtils.getTimeStampStr(new Date());
			String expireTime = "2014-05-27 11:11:11";

			Date dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expireTime);
			// String Dat = DateUtils.getReqDate();
			// String Dat1 = LONG_DATE_FORMAT.format(new Date());
			String Dat = "20140527";
			String Dat1 = "20140527 11:11:11";
			String str = "|GDWZ                " + bankOrder + "          " + "                 " + random1 + status + Dat + "  " + Dat1
					+ cardType;
			fileWriter.write(str);
			fileWriter.flush();

			// ----------------清算------------------------
			String merNamee = "|GWZH                                              ";
			String payCardType = "1";//
			String str1 = merNamee + bankOrder + "          " + "                 " + random1 + payCardType + Dat1 + Dat1;
			fileWriter1.write(str1);
			fileWriter1.flush();
			// ----------------清算------------------------

			/*
			 * B2cOrder b2cOrder =new B2cOrder();
			 * b2cOrder.setAmount(Double.valueOf(random1));
			 * b2cOrder.setCallbackPara(""); b2cOrder.setCallbackUrl("");
			 * b2cOrder.setCur(1); b2cOrder.setDealTime(dateTime);
			 * b2cOrder.setExpireTime(dateTime);
			 * b2cOrder.setMerchantNo("888000000000000");
			 * b2cOrder.setMerchantName("林氏集团");
			 * b2cOrder.setMobileNo("13484043279"); b2cOrder.setNotifyUrl("");
			 * b2cOrder.setOrderNo(order); b2cOrder.setOrderTime(Dat);
			 * b2cOrder.setProductName(""); b2cOrder.setStatus(100);
			 * paymentFacade.createB2cOrder(b2cOrder);
			 * 
			 * PaymentRecord paymentRecord = new PaymentRecord();
			 * paymentRecord.setOrderNo(order);
			 * paymentRecord.setOrderAmount(Double.valueOf(random1));
			 * paymentRecord.setFrpCode("PINGANBANK-B2C");
			 * //paymentRecord.setMerchantId(merchant.getId());
			 * 
			 * 
			 * paymentRecord.setMerchantNo("888000000000000");
			 * paymentRecord.setTrxType(TrxTypeEnum.NET_B2C_PAY.getValue());
			 * 
			 * // paymentRecord.setTargetUserNo(""); // 付款方编号 TODO
			 * paymentRecord.setTargetUserName(""); // TODO 付款方名称
			 * paymentRecord.setTargetAccountType
			 * (AccountTypeEnum.MERCHANT.getValue()); //
			 * 
			 * // step.1 设置商户信息 //paymentRecord.setAgentId(0L);
			 * paymentRecord.setAgentNo(""); //
			 * paymentRecord.setMerchantName("林氏集团");
			 * 
			 * // step.2 创建代理商 paymentRecord.setAgentName("");
			 * 
			 * // step.3 设置商户费率 paymentRecord.setMerchantFee(0.4);
			 * paymentRecord.setMerchantRate(0.1);
			 * paymentRecord.setAgentRate(0.1);
			 * paymentRecord.setTargetRate(0.1);
			 * paymentRecord.setSourceRate(0.1);
			 * 
			 * //step.4 设置商户手续费 paymentRecord.setAgentFee(0.2);
			 * paymentRecord.setTargetFee(0.2); paymentRecord.setSourceFee(0.2);
			 * paymentRecord.setPayAmount(0.2);
			 * 
			 * String bankchannelcode = "PINGANBANK-NET-B2C-GZ";
			 * paymentRecord.setBankChannelCode(bankchannelcode); // step.5
			 * 设置银行渠道
			 * 
			 * // step.6 设置银行费率,手续费
			 * 
			 * paymentRecord.setBankChannelCode(bankchannelcode);
			 * paymentRecord.setBankRate(0.1);
			 * paymentRecord.setBankAgreementId(1L);
			 * paymentRecord.setBankCode(BankCode.PINGANBANK.name());
			 * paymentRecord.setBankName("平安银行"); paymentRecord.setBankFee(0.1);
			 * 
			 * // step.7 设置其它 paymentRecord.setPlatFee(0.3);
			 * paymentRecord.setIsRefund(false);
			 * paymentRecord.setRefundTimes(0L); //
			 * paymentRecord.setSuccessRefundAmount(0D);
			 * paymentRecord.setStatus(OrderStatusEnum.SUCCESS.getValue());
			 * paymentRecord.setBankTrxNo(""); paymentRecord.setPaymentTime(new
			 * Date());
			 * 
			 * paymentRecord.setTargetUserNo("");
			 * paymentRecord.setSourceUserNo("115000009"); // 收款方编号
			 * paymentRecord.setSourceUserName("林氏集团"); // 收款方签约名
			 * 
			 * // step.8 设置银行订单号 paymentRecord.setBankOrderNo(bankOrder);
			 * 
			 * // step.9 设置支付流水号
			 * paymentRecord.setTrxNo(paymentFacade.buildPaymentTrxNo
			 * (paymentRecord.getTrxType()));
			 * paymentFacade.createPaymentRecord_BankOrder(paymentRecord);
			 */

			System.out.println(i);
		}
		fileWriter.close();
		System.out.println("=======结束=======");

	}

	// 清算对账文件
	public void testCreatePaymentRecord_clearRecon() throws IOException, ParseException {
		FileWriter fileWriter1 = new FileWriter("D:\\对账文件\\平安银行\\ClearRecon.txt");
		String orderTotalCount = "     9"; // 订单总笔数
		// 金额
		String orderTotalAmt = "                 0.0"; // 定单总金额
		String reckoningAmt = "                 0.0";// 清算金额
		String chargeTotalAmt = "                 0.0";// 手续费总金额
		String creditTotalOrderCount = "     0";// 信用卡总笔数
		String creditTotalOrderAmt = "                 0.0";// 信用卡总金额
		String creditfree = "                 0.0"; // 信用卡手续费
		String debitTotalOrderCount = "     0"; // 借记卡总笔数
		String debitTotalOrderAmt = "                 0.0";
		String debitCharge = "                 0.0" + "|";
		String returnNmber = "     0";// 返回信息笔数

		fileWriter1.write(orderTotalCount + orderTotalAmt + reckoningAmt + chargeTotalAmt + creditTotalOrderCount + creditTotalOrderAmt
				+ creditfree + debitTotalOrderCount + debitTotalOrderAmt + debitCharge + returnNmber);
		for (int i = 0; i < 100; i++) {
			String merNamee = "|GWZH                                              ";
			String order = paymentFacade.buildBankOrderNo();
			// 金额
			String orderAmt = String.valueOf(new Random().nextInt(100) + 100);
			String payCardType = "1";//
			SimpleDateFormat LONG_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			String Dat1 = LONG_DATE_FORMAT.format(new Date());
			String str = merNamee + order + "          " + "                 " + orderAmt + payCardType + Dat1 + Dat1;
			fileWriter1.write(str);
			fileWriter1.flush();

			System.out.println(i);
		}
		fileWriter1.close();
	}

}
