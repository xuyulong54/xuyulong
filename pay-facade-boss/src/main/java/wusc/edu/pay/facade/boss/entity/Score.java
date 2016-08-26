package wusc.edu.pay.facade.boss.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 类描述：积分信息实体
 * 
 * @author: huangbin
 * @date： 日期：2013-12-3 时间：上午11:26:39
 * @version 1.0
 */
public class Score extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -713103081203235249L;

	private String accountNo; // 账户ID
	private Long loginScore; // 登录积分

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Long getLoginScore() {
		return loginScore;
	}

	public void setLoginScore(Long loginScore) {
		this.loginScore = loginScore;
	}

}
