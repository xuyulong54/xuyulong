package wusc.edu.pay.facade.bank.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.bank.entity.BankAccount;
import wusc.edu.pay.facade.bank.exceptions.BankBizException;


/**
 * 
 * @描述: 银行账户信息，Dubbo服务接口.
 * @作者: WuShuicheng .
 * @创建时间: 2014-4-15, 下午2:30:29
 */
public interface BankAccountFacade {
	/**
	 * 创建银行账户信息
	 * 
	 * @param entity
	 * @return
	 */
	public long create(BankAccount entity) throws BankBizException;

	/**
	 * 修改银行账户信息
	 * 
	 * @param entity
	 * @return
	 */
	public long update(BankAccount entity) throws BankBizException;

	/**
	 * 分页查询银行账户信息
	 * 
	 * @param pageParam
	 *            分页实体对象
	 * @param paramMap
	 *            查询条件
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws BankBizException;

	/**
	 * 根据ID查找银行账户信息
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public BankAccount getById(long id) throws BankBizException;
	
	/***
	 * 根据银行账号查询银行账户信息
	 * @param bankAccount 银行账号
	 * @return
	 */
	public BankAccount getByBankAccount(String bankAccount) throws BankBizException;
	
	/***
	 * 根据开户银行查询银行账户信息
	 * @param openBank 开户银行
	 * @return
	 */
	public List<BankAccount> getByOpenBank(String openBank) throws BankBizException;

	/**
	 * 根据Id删除银行账户信息
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public void deleteById(long id) throws BankBizException;
	
	/**
	 * 根据银行账号模糊查找 
	 * @param bankAccountKey .
	 * @param status .
	 * @return List .
	 */
	public List<BankAccount> likeBy(String bankAccountKey, Integer status)  throws BankBizException;
}
