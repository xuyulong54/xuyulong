/**
 * wusc.edu.pay.bank.dao.impl.BankAccountDaoImpl.java
 */
package wusc.edu.pay.core.bank.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.bank.dao.BankAccountDao;
import wusc.edu.pay.facade.bank.entity.BankAccount;


/**
 * 
 * @描述: 银行账户信息表，数据访问层接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2014-4-15, 下午2:24:17
 */
@Repository("bankAccountDao")
public class BankAccountDaoImpl extends BaseDaoImpl<BankAccount> implements BankAccountDao {
	
	/**
	 * 根据银行账号模糊查找 
	 * @param bankAccountKey .
	 * @param status .
	 * @return List .
	 */
	public List<BankAccount> likeBy(String bankAccountKey, Integer status){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bankAccountKey", bankAccountKey);
		params.put("status", status);
		return super.getSessionTemplate().selectList(getStatement("likeBy"), params);
	}
}