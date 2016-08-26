package wusc.edu.pay.facade.report.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.report.entity.AccountingReservesBankReport;


public interface AccountingReservesBankReportFacade {
	/**
	 * 备付金银行帐户统计
	 * @param entity
	 * @return
	 */
	public long create(AccountingReservesBankReport entity) throws BizException;
	/**
	 * 根据ID查找信息
	 * @param id  主键
	 * @return
	 */
	public AccountingReservesBankReport getById(long id) throws BizException;
	/**
	 * 分页查询银行账户信息
	 * @param pageParam  分页实体对象
 	 * @param paramMap  查询条件
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws BizException;
	
	/**
	 * 报表展现数据
	 * @param paramMap
	 * @return
	 */
	public List<AccountingReservesBankReport> listByDate(Map<String, Object> paramMap);
	/**
	 * 详情报表展现数据
	 * @param paramMap
	 * @return
	 */
	public List<AccountingReservesBankReport> listDetail(Map<String, Object> paramMap);

}
