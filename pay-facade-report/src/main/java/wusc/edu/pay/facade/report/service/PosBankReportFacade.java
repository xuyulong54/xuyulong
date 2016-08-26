package wusc.edu.pay.facade.report.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.report.entity.PosBankReport;


/***
 * 
 * @描述: POS银行报表数据表.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午9:03:27 .
 * @版本: V1.0.
 *
 */
public interface PosBankReportFacade {

	/**
	 * 创建POS银行报表数据表信息
	 * @param entity
	 * @return
	 */
	public long create(PosBankReport entity) throws BizException;
	/**
	 * 根据ID查找信息
	 * @param id  主键
	 * @return
	 */
	public PosBankReport getById(long id) throws BizException;
	/**
	 * 分页查询银行账户信息
	 * @param pageParam  分页实体对象
 	 * @param paramMap  查询条件
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws BizException;
	
	public List<PosBankReport> listByDate(Map<String, Object> posBankMap) throws BizException;
}
