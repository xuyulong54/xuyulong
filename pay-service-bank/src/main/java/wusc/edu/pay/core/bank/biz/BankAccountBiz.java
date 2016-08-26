package wusc.edu.pay.core.bank.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.core.biz.BaseBizImpl;
import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.core.bank.dao.BankAccountDao;
import wusc.edu.pay.facade.bank.entity.BankAccount;
import wusc.edu.pay.facade.bank.exceptions.BankBizException;


/**
 * 
 * @描述: 银行账户信息表业务实现类 .
 * @作者: WuShuicheng .
 * @创建时间: 2014-4-15, 下午2:28:52
 */
@Component("bankAccountBiz")
public class BankAccountBiz extends BaseBizImpl<BankAccount> {

	@Autowired
	private BankAccountDao bankAccountDao;

	protected BaseDao<BankAccount> getDao() {
		return bankAccountDao;
	}
	
	public long deleteById(long id){
		return bankAccountDao.deleteById(id);
	}
	
	/**
	 * 根据银行账号查询银行账户信息
	 * @param bankAccount
	 * @return
	 */
	public BankAccount getByBankAccount(String bankAccount){
		if (StringUtils.isBlank(bankAccount)) {
			throw new BankBizException(BankBizException.BANK_SERVICE_PARAMS_ERROR, "银行账户编号不能为空:%s", bankAccount);
		}
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("bankAccount", bankAccount);
		return bankAccountDao.getBy(paramMap);
	}
	
	/**
	 * 根据开户银行查询银行账户信息
	 * @param openBank
	 * @return
	 */
	public List<BankAccount> getByOpenBank(String openBank){
		if (StringUtils.isBlank(openBank)) {
			throw new BankBizException(BankBizException.BANK_SERVICE_PARAMS_ERROR, "开户银行不能为空:%s", openBank);
		}
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("openBank", openBank);
		return bankAccountDao.listBy(paramMap);
	}
	
	/**
	 * 根据银行账号模糊查找 
	 * @param bankAccountKey .
	 * @param status .
	 * @return List .
	 */
	public List<BankAccount> likeBy(String bankAccountKey, Integer status){
		return bankAccountDao.likeBy(bankAccountKey, status);
	}
}