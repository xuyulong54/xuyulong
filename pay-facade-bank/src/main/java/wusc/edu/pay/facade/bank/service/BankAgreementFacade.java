package wusc.edu.pay.facade.bank.service;

import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.bank.entity.BankAgreement;
import wusc.edu.pay.facade.bank.exceptions.BankBizException;


public interface BankAgreementFacade {
	/**
	 * 创建会员
	 * 
	 * @param entity
	 * @return
	 */
	public long create(BankAgreement entity) throws BankBizException;

	/**
	 * 修改会员
	 * 
	 * @param entity
	 * @return
	 */
	public long update(BankAgreement entity) throws BankBizException;

	/**
	 * 分页查询会员
	 * 
	 * @param pageParam
	 *            分页实体对象
	 * @param paramMap
	 *            查询条件
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws BankBizException;

	/**
	 * 根据ID查找
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public BankAgreement getById(long id) throws BankBizException;
}
