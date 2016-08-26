package wusc.edu.pay.core.banklink.common.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import wusc.edu.pay.common.utils.DateUtils;


/**
 * 银行参数格式化工具类
 * @author Administrator
 *
 */
public class BankTransUtils {

	/**
	 * 将金额对象转换成以分为单位的字符串（无小数点）
	 * 
	 * @param amount
	 *            待转换金额
	 * @return 以分为单位的金额字符串
	 */
	public static String formatAmountToFen(BigDecimal amount) {
		return amount.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 将金额对象转换成以元为单位的字符串（有小数点）
	 * 
	 * @param amount
	 *            待转换金额
	 * @return 以分为单位的金额字符串
	 */
	public static String formatAmountToYuan(BigDecimal amount) {
		return amount.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
	/**
	 * 将金额对象转换成以元为单位的字符串（有小数点）
	 * 
	 * @param amount
	 *            待转换金额
	 * @return 以分为单位的金额字符串
	 */
	public static BigDecimal formatAmountToYuan(String amount) {
		return new BigDecimal(formatAmountToYuan(new BigDecimal(amount)));
	}

	/**
	 * 将以分为单位的字符串转换为金额对象（无小数点）
	 * 
	 * @param amount
	 *            待转换字符串
	 * @return 金额对象
	 */
	public static BigDecimal parseFromFen(String amount) {
		return new BigDecimal(amount).divide(new BigDecimal(100));
	}

	/**
	 * 将以元为单位的字符串转换为金额对象（有小数点）
	 * 
	 * @param amount
	 *            待转换字符串
	 * @return 金额对象
	 */
	public static BigDecimal parseFromYuan(String amount) {
		return new BigDecimal(amount);
	}

	/**
	 * 将日期字符串转换为日期对象
	 * 
	 * @param strDate
	 *            时间日期
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static Date parseDate(String strDate, String pattern) {
		try {
			Date date = DateUtils.parseDate(strDate, pattern);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
