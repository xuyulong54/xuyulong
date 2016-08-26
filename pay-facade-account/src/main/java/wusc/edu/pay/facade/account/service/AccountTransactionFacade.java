package wusc.edu.pay.facade.account.service;

import java.util.List;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.facade.account.enums.AccountTradeTypeEnum;
import wusc.edu.pay.facade.account.exception.AccountBizException;
import wusc.edu.pay.facade.account.vo.AccountTransactionVo;


/**
 * 账户交易
 * 
 * @author healy
 * 
 */
public interface AccountTransactionFacade {

	/**
	 * 账户收/付款(单笔).
	 * 
	 * @param vo
	 *            交易命令参数vo .
	 * @throws AccountBizException
	 */
	void execute(AccountTransactionVo vo) throws AccountBizException;

	/**
	 * 账户收/付款(批量)
	 * 
	 * @param voList
	 *            交易命令参数vo集 .
	 * @throws BizException
	 */
	void execute(List<AccountTransactionVo> voList) throws AccountBizException, BizException;

	/**
	 * 同一账户批量加款.
	 * 
	 * @param voList
	 *            交易命令参数vo集.
	 * @throws AccountBizException
	 */
	void batchCreditForSameAccount(List<AccountTransactionVo> voList) throws AccountBizException;

	/**
	 * 资金冻结.
	 * 
	 * @param userNo
	 *            用户编号.
	 * @param frozenAmount
	 *            冻结金额.
	 * @param requestNo
	 *            请求号.
	 * @param tradeType
	 *            账户交易类型.
	 * @throws AccountBizException
	 */
	void frozen(String userNo, double frozenAmount, String requestNo, AccountTradeTypeEnum tradeType) throws AccountBizException;

	/**
	 * 资金解冻.
	 * 
	 * @param userNo
	 *            用户编号.
	 * @param unFrozenAmount
	 *            解冻金额.
	 * @param requestNo
	 *            请求号.
	 * @param tradeType
	 *            账户交易类型.
	 * @throws AccountBizException
	 */
	void unFrozen(String userNo, double unFrozenAmount, String requestNo, AccountTradeTypeEnum tradeType) throws AccountBizException;

	/**
	 * 资金解冻并减款.
	 * 
	 * @param userNo
	 *            用户编号.
	 * @param unFrozenAmount
	 *            解冻金额.
	 * @param requestNo
	 *            请求号.
	 * @param tradeType
	 *            账户交易类型.
	 * @param fee
	 *            手续费
	 */
	void unfrozen_debit(String userNo, double amount, String requestNo, AccountTradeTypeEnum tradeType, double fee) throws AccountBizException;

}