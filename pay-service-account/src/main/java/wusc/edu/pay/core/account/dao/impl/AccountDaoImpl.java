package wusc.edu.pay.core.account.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.core.account.dao.AccountDao;
import wusc.edu.pay.facade.account.entity.Account;


@Repository("accountDao")
public class AccountDaoImpl extends BaseDaoImpl<Account> implements AccountDao {
	
	/**
	 * 生成账户编号20位
	 */
	public String buildAccountNo(String accountType) {
		// 获取账户编号序列值，用于生成20位的账户编号
		String accountNoSeq = super.getSeqNextValue("ACCOUNT_NO_SEQ");
		// 构造账户编号
		String accountNo = "8008" + accountType + accountNoSeq + "0101";
		
		return accountNo;
	}

	/**
	 * 根據帳戶編號獲取帳戶信息
	 * 
	 * @param accountNo
	 * @return
	 */
	public Account getByAccountNo(String accountNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", accountNo);
		return super.getBy(params);
	}

	/**
	 * 获取账户实体
	 * 
	 * @param userNo
	 * @param isPessimist
	 *            是否乐观锁
	 * @return
	 */
	public Account getByUserNo_IsPessimist(String userNo, boolean isPessimist) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userNo", userNo);
		params.put("isPessimist", isPessimist);
		return super.getBy(params);
	}
}
