package wusc.edu.pay.facade.account.service;

import wusc.edu.pay.facade.account.enums.AccountInitiatorEnum;
import wusc.edu.pay.facade.account.enums.AccountOperationTypeEnum;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.account.exception.AccountBizException;

/**
 * 账户管理
 * 
 * @author healy
 * 
 */
public interface AccountManagementFacade {

	/**
	 * 重新绑定商户编号
	 * 
	 * @param accountNo
	 * @param userNo
	 * @return
	 * @throws AccountBizException
	 */
	public long reBindUserNo(String accountNo, String userNo) throws AccountBizException;

	/**
	 * 生成账户编号
	 * 
	 * @param accountType
	 * @return
	 * @throws AccountBizException
	 */
	String buildAccountNo(AccountTypeEnum accountType) throws AccountBizException;

	/**
	 * 创建账户
	 * 
	 * @param userNo
	 *            用户编号.
	 * @param accountNo
	 *            账户编号.
	 * @param accountType
	 *            账户类型.
	 * @return ID 账户ID.
	 * @throws AccountBizException
	 */
	long createAccount(String userNo, String accountNo, int accountType) throws AccountBizException;

	/**
	 * 创建内部银行虚拟账户
	 * 
	 * @param userNo
	 *            用户编号.
	 * @param accountNo
	 *            账户编号.
	 * @param balance
	 *            账户余额
	 * @param securityMoney
	 *            保证金
	 * @throws AccountBizException
	 */
	long createPrivateAccount(String userNo, String accountNo, Double balance, Double securityMoney) throws AccountBizException;

	/**
	 * 账户状态变更操作.
	 * 
	 * @param userNo
	 *            用户编号.
	 * @param operationType
	 *            账户操作类型.
	 * @param initiator
	 *            账户操作,发起方.
	 * @param desc
	 *            变更操作说明.
	 */
	void changeAccountStatus(String userNo, AccountOperationTypeEnum operationType, AccountInitiatorEnum initiator, String desc)
			throws AccountBizException;
}
