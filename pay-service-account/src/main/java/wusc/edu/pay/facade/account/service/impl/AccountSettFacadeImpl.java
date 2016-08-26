package wusc.edu.pay.facade.account.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.account.biz.AccountSettBiz;
import wusc.edu.pay.facade.account.service.AccountSettFacade;


@Component("accountSettFacade")
public class AccountSettFacadeImpl implements AccountSettFacade {

	@Autowired
	private AccountSettBiz accountSettBiz;

	/**
	 * 结算创建_T+0
	 * 
	 * @param userNo
	 * @param settAmount
	 * @param requestNo 结算请求
	 * @param trxNo 账户历史交易请求
	 */
	public void settCreateT0(String userNo, Double settAmount, String requestNo ,String trxNo) {
		accountSettBiz.settCreateT0(userNo, settAmount, requestNo,trxNo);
	}

	/**
	 * 结算成功
	 * 
	 * @param userNo
	 * @param settAmount
	 * @param requestNo
	 */
	public void settSuccess(String userNo, Double settAmount, String requestNo) {
		accountSettBiz.settSuccess(userNo, settAmount, requestNo);
	}

	/**
	 * 结算汇总成功
	 * 
	 * @param userNo
	 * @param statDate
	 * @param riskDay
	 */
	public void settCollectSuccess(String userNo, String statDate, Integer riskDay) {
		accountSettBiz.settCollectSuccess(userNo, statDate, riskDay);
	}

	/**
	 * 结算创建
	 * 
	 * @param userNo
	 * @param settAmount
	 * @param requestNo
	 * @param lastId
	 */
	public void settCreate(String userNo, Double settAmount, String requestNo, Long lastId) {
		accountSettBiz.settCreate(userNo, settAmount, requestNo, lastId);
	}

	/**
	 * 结算失败
	 * 
	 * @param userNo
	 * @param settAmount
	 * @param requestNo
	 */
	public void settFail(String userNo, Double settAmount, String requestNo) {
		accountSettBiz.settFail(userNo, settAmount, requestNo);
	}

}
