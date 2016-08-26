package wusc.edu.pay.facade.cost.exceptions;

import wusc.edu.pay.common.exceptions.BizException;

public class CostBizException extends BizException{

	private static final long serialVersionUID = -2814707659216158933L;
	
	public static final CostBizException DIMENSION_IS_EXIST = new CostBizException(60010001,"维度已存在");

	public static final CostBizException DIMENSION_NOEXIST = new CostBizException(60010002,"找不到计费维度信息");
	

	public static final CostBizException COST_ORDER_INVALID = new CostBizException(60010003,"成本订单信息验证失败");
	

	public static final CostBizException CAL_INTERFACE_NOEXIST = new CostBizException(60010004,"找不到银行计费接口");
	
	public static final CostBizException CAL_RULE_NO_FOUND = new CostBizException(60010005,"找不到有效的计费规则");

	public static final CostBizException CAL_CYCLE_DATE_ERROR = new CostBizException(60010006,"计费周期设置有误");
	
	public static final CostBizException CAL_FLOW_SAVE_ERROR = new CostBizException(60010007,"计费流量保存出现异常");

	public static final CostBizException CAL_FEE_ERROR = new CostBizException(60010008,"计算费率成本出现异常");
	
	public static final CostBizException COST_INTERFACE_IS_EXIST = new CostBizException(60010009,"计费接口已经存在");
	
	public static final CostBizException COST_ORDER_NOT_EXIST = new CostBizException(60010010,"成本订单不存在");
	
	
	public CostBizException() {
	}

	public CostBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
	}

	public CostBizException(int code, String msg) {
		super(code, msg);
	}
	
	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public CostBizException newInstance(String msgFormat, Object... args) {
		return new CostBizException(this.code, msgFormat, args);
	}
}
