package wusc.edu.pay.test.facade;

import java.io.FileWriter;
import java.util.Date;
import java.util.Random;

import wusc.edu.pay.common.utils.DateUtils;


public class BankFacadeTestBak {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// 加载相应接口配置文件
		// ClassPathXmlApplicationContext context = new
		// ClassPathXmlApplicationContext(new
		// String[]{"dubbo-consumer-bank-test.xml"});
		// BankFacade bankFacade = (BankFacade)context.getBean("bankFacade");
		// String ss = bankFacade.pay(BankCode.PINGANBANK.toString(),
		// TrxTypeEnum.NET_B2C_PAY.toString(), System.currentTimeMillis()+"",
		// new Date(), 100.01);
		// System.out.println("返回加密结果：" + ss);

		// 60042014040910002116|100|2014-4-9 10:14:33|1|1

		System.out.println("-----make data begin-----");
		test();
		System.out.println("-----make data end-----");

	}

	public static void test() throws Exception {

		FileWriter fileWriter = new FileWriter("D:\\makeData.txt");

		for (int i = 0; i < 15000000; i++) {

			String number = String.valueOf(new Random().nextInt(10000) / 100);

			String str = "600420140409" + i + "|" + number + "|"
					+ DateUtils.getTimeStampStr(new Date()) + "|1" + "|1\r\n";
			System.out.println(i);
			fileWriter.write(str);
			fileWriter.flush();
		}
		fileWriter.close();
	}
}
