/**
 * wusc.edu.pay.bank.dao.BankAccountDao.java
 */
package wusc.edu.pay.core.bank.dao;


import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.bank.entity.CardBin;

/**
 * @author System
 *
 * @since 2013-11-07
 */
public interface CardBinDao extends BaseDao<CardBin> {
	
	CardBin getByCardBin(String cardBin, Integer status);
}