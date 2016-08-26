/**
 * wusc.edu.pay.bank.dao.BankAccountDao.java
 */
package wusc.edu.pay.core.payrule.dao;


import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.payrule.entity.BankBranch;

/**
 * @author System
 *
 * @since 2013-11-07
 */
public interface BankBranchDao extends BaseDao<BankBranch> {
	
	BankBranch getByFrpCode(String frpCode);
}