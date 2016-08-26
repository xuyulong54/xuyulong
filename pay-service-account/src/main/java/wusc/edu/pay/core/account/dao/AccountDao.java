package wusc.edu.pay.core.account.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.account.entity.Account;


public interface AccountDao extends BaseDao<Account> {

	/**
	 * 生成账户编号20位
	 * 
	 * @param accountType
	 * @return
	 */
	public String buildAccountNo(String accountType);

	/**
	 * 根據帳戶編號獲取帳戶信息
	 * 
	 * @param accountNo
	 * @return
	 */
	public Account getByAccountNo(String accountNo);

	/**
	 * 获取账户实体
	 * 
	 * @param userNo
	 * @param accountType
	 * @param isPessimist
	 *            是否乐观锁
	 * @return
	 */
	public Account getByUserNo_IsPessimist(String userNo, boolean isPessimist);

}
