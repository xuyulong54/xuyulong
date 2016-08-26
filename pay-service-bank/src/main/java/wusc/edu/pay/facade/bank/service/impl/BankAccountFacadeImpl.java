package wusc.edu.pay.facade.bank.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.bank.biz.BankAccountBiz;
import wusc.edu.pay.facade.bank.entity.BankAccount;
import wusc.edu.pay.facade.bank.service.BankAccountFacade;


/**
 * 
 * @描述: 银行账户信息，Dubbo服务接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2014-4-15, 下午2:33:37
 */
@Component("bankAccountFacade")
public class BankAccountFacadeImpl implements BankAccountFacade {
	
	@Autowired
	private BankAccountBiz bankAccountBiz;

	@Override
	public long create(BankAccount entity) {
		return bankAccountBiz.create(entity);
	}

	@Override
	public long update(BankAccount entity) {
		return bankAccountBiz.update(entity);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return bankAccountBiz.listPage(pageParam, paramMap);
	}

	@Override
	public BankAccount getById(long id) {
		BankAccount bankAccount = bankAccountBiz.getById(id);
		return bankAccount;
	}

	@Override
	public void deleteById(long id) {
		bankAccountBiz.deleteById(id);
	}

	/***
	 * 根据银行账号查询银行账户信息
	 */
	@Override
	public BankAccount getByBankAccount(String bankAccount) {
		return bankAccountBiz.getByBankAccount(bankAccount);
	}

	/***
	 * 根据开户银行获取银行账户信息
	 */
	@Override
	public List<BankAccount> getByOpenBank(String openBank) {
		// TODO Auto-generated method stub
		return bankAccountBiz.getByOpenBank(openBank);
	}
	
	/**
	 * 根据银行账号模糊查找 
	 * @param bankAccountKey .
	 * @param status .
	 * @return List .
	 */
	public List<BankAccount> likeBy(String bankAccountKey, Integer status) {
		return bankAccountBiz.likeBy(bankAccountKey, status);
	}
}
