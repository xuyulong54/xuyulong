package wusc.edu.pay.web.portal.util;

import java.util.Random;

public class PhoneCode {
	/**
	 * 六位随机数字
	 * 
	 * @return
	 */
	public static String getPhoneCode() {
		Random rand = new Random();
		int result = rand.nextInt(999999);
		// 对0开头的数字进行处理
		return "000000".substring((result + "").length()) + result; 
	}
}
