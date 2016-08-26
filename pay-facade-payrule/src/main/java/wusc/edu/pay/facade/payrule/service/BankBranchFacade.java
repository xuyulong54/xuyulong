package wusc.edu.pay.facade.payrule.service;

import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.payrule.entity.BankBranch;
import wusc.edu.pay.facade.payrule.exceptions.PayruleBizException;


public interface BankBranchFacade {

	/**
	 * 创建
	 * 
	 * @param entity
	 * @return
	 */
	public long create(BankBranch entity) throws PayruleBizException;

	/**
	 * 修改
	 * 
	 * @param entity
	 * @return
	 */
	public long update(BankBranch entity) throws PayruleBizException;

	/**
	 * 分页查询
	 * 
	 * @param pageParam
	 *            分页实体对象
	 * @param paramMap
	 *            查询条件
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws PayruleBizException;

	/**
	 * 根据ID查找
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public BankBranch getById(long id) throws PayruleBizException;

	/**
	 * 根据frpCode查找
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public BankBranch getByFrpCode(String frpCode) throws PayruleBizException;

	/**
	 * 根据frpCode从缓存中查找
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public BankBranch getCacheByFrpCode(String frpCode) throws PayruleBizException;

}
