/**
 * wusc.edu.pay.bank.dao.BankAgreementDao.java
 */
package wusc.edu.pay.core.bank.dao;


import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.bank.entity.BankAgreement;

/**
 * @author System
 *
 * @since 2013-11-07
 */
public interface BankAgreementDao extends BaseDao<BankAgreement> {
	/**
	 * 根据商户编号查找银行协议 .
	 * @param merchantNo .
	 * @return BankAgreement .
	 * 
	 */
	BankAgreement getByMerchantNo(String merchantNo);
}