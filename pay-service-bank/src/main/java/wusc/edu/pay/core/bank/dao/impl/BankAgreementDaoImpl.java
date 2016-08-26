/**
 * wusc.edu.pay.bank.dao.impl.BankAgreementDaoImpl.java
 */
package wusc.edu.pay.core.bank.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.bank.dao.BankAgreementDao;
import wusc.edu.pay.facade.bank.entity.BankAgreement;


/**
 * @author Peter
 * 
 * @since 2013-11-07
 */
@Repository(value = "bankAgreementDao")
public class BankAgreementDaoImpl extends BaseDaoImpl<BankAgreement> implements BankAgreementDao {
	/**
	 * 根据商户编号查找银行协议 .
	 * 
	 * @param merchantNo
	 *            .
	 * @return BankAgreement .
	 * 
	 */
	public BankAgreement getByMerchantNo(String merchantNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantNo", merchantNo);

		return super.getBy(params);
	}
}