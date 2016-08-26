package wusc.edu.pay.facade.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.core.account.biz.AccountTransactionBiz;
import wusc.edu.pay.facade.account.enums.AccountTradeTypeEnum;
import wusc.edu.pay.facade.account.service.AccountTransactionFacade;
import wusc.edu.pay.facade.account.vo.AccountTransactionVo;


/**
 * 账户交易
 * 
 * @author healy
 * 
 */
@Component("accountTransactionFacade")
public class AccountTransactionFacadeImpl implements AccountTransactionFacade {

	@Autowired
	private AccountTransactionBiz accountTransactionBiz;

	/**
	 * 账户收/付款(单笔).
	 * 
	 * @param vo
	 *            交易命令参数vo .
	 */
	public void execute(AccountTransactionVo vo) {
		accountTransactionBiz.execute(vo);
	}

	/**
	 * 账户收/付款(批量)
	 * 
	 * @param voList
	 *            交易命令参数vo集 .
	 * @throws BizException
	 */
	public void execute(List<AccountTransactionVo> voList) {
		accountTransactionBiz.execute(voList);
	}

	/**
	 * 同一账户批量加款.
	 * 
	 * @param voList
	 *            交易命令参数vo集.
	 */
	public void batchCreditForSameAccount(List<AccountTransactionVo> voList) {
		accountTransactionBiz.batchCreditForSameAccount(voList);
	}

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
	 */
	public void frozen(String userNo, double frozenAmount, String requestNo, AccountTradeTypeEnum tradeType) {
		accountTransactionBiz.frozen(userNo, frozenAmount, requestNo, tradeType);
	}

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
	 */
	public void unFrozen(String userNo, double unFrozenAmount, String requestNo, AccountTradeTypeEnum tradeType) {
		accountTransactionBiz.unFrozen(userNo, unFrozenAmount, requestNo, tradeType);
	}

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
	public void unfrozen_debit(String userNo, double amount, String requestNo, AccountTradeTypeEnum tradeType, double fee) {
		accountTransactionBiz.unfrozen_debit(userNo, amount, requestNo, tradeType, fee);
	}
}
