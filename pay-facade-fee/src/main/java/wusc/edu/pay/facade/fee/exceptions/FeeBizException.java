package wusc.edu.pay.facade.fee.exceptions;

import wusc.edu.pay.common.exceptions.BizException;

/**
 * 计费biz异常类
 * 
 * @desc
 * @author shenjialong
 * @date 2014-6-30,下午3:39:49
 */
public class FeeBizException extends BizException {

	private static final long serialVersionUID = 1L;

	/**
	 * 费率维度不存在
	 */
	public static final int FEE_DIMENSION_NOT_EXIST = 50010001;
	/**
	 * 费率用户设置不存在
	 */
	public static final int FEE_USERSETTING_NOT_EXIST = 50010002;
	/**
	 * 待计费金额没有满足的公式
	 */
	public static final int NO_SUITABLE_FORMULA_FOR_AMOUNT = 50010003;
	/**
	 * 指定计费项下费率维度已经存在
	 */
	public static final int FEE_DIMENSION_EXIST = 50010004;
	/**
	 * 免手续费金额大于零
	 */
	public static final int FEE_FREE_AMOUNT_THAN_ZERO = 50010005;
	/**
	 * 计费公式有效期非法，有效期起必须早于有效期止
	 */
	public static final int FEE_FORMULA_EFFECTIVE_DATE_ILLEGAL = 50010006;
	/**
	 * 计费方式没有阶梯周期信息
	 */
	public static final int FEECALCULATEWAY_NOT_EXIST_LADDERCYCLEINFO = 50010007;

	/**
	 * 自定义周期不符合要求
	 */
	public static final int CAL_CYCLE_DATE_ERROR = 50010008;

	/**
	 * 计费订单不存在
	 */
	public static final int FEEORDER_IS_NOT_EXIT = 50010009;
	/**
	 * 计费方式不存在
	 */
	public static final int CALCULATEWAY_IS_NOT_EXIT = 50010010;

	/**
	 * MCC码对应的MccFee不存在
	 */
	public static final  int MCC_IS_ERROR = 50010011;

	public FeeBizException() {
		super();
	}

	public FeeBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}
}
