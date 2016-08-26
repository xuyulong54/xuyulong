/**
 * wusc.edu.pay.bank.dao.impl.BankAccountDaoImpl.java
 */
package wusc.edu.pay.core.payrule.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.payrule.dao.BankBranchDao;
import wusc.edu.pay.facade.payrule.entity.BankBranch;


/**
 * @author System
 * 
 * @since 2013-11-07
 */
@Repository(value="bankBranchDao")
public class BankBranchDaoImpl extends BaseDaoImpl<BankBranch> implements BankBranchDao {
	
	public BankBranch getByFrpCode(String frpCode){

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("frpCode", frpCode);

		return super.getBy(params);
	}

}