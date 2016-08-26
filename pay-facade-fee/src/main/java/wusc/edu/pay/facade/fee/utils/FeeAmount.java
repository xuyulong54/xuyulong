package wusc.edu.pay.facade.fee.utils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 定义单次计费的最大金额上、下限
 */
@SuppressWarnings("serial")
public class FeeAmount implements Serializable {
	
	/**
	 * 计费系统中可使用的的最小金额
	 */
	public final static BigDecimal MIN_VALUE = BigDecimal.ZERO;
	/**
	 * 计费系统中可使用的最大金额
	 */
	public final static BigDecimal MAX_VALUE = BigDecimal.valueOf(1000000000000L);
	
	public static void main(String[] args){
		System.out.println(FeeAmount.MAX_VALUE);
	}

}
