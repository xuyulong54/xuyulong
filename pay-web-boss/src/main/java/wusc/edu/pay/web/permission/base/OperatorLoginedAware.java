package wusc.edu.pay.web.permission.base;

import wusc.edu.pay.web.permission.entity.PmsOperator;

public interface OperatorLoginedAware {

	/**
	 * 取得登录的操作员
	 * 
	 * @return
	 */
	public PmsOperator getLoginedOperator();
}
