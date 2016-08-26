package wusc.edu.pay.facade.payrule.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.payrule.entity.Frp;
import wusc.edu.pay.facade.payrule.exceptions.PayruleBizException;


/**
 * 
 * @描述: 支付渠道业务层接口 .
 * @作者: huqian .
 * @创建时间: 2013-8-1 .
 * @版本: 1.0 .
 */

public interface FrpFacade {

	/**
	 * 按支付渠道编号查找
	 * 
	 * @param frpCode
	 * @return
	 */
	public Frp findByFrpCode(String frpCode) throws PayruleBizException;

	PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws PayruleBizException;

	Frp getById(long id) throws PayruleBizException;

	long update(Frp entity) throws PayruleBizException;

	long create(Frp entity) throws PayruleBizException;

	@SuppressWarnings("rawtypes")
	public List listAll() throws PayruleBizException;

	/***
	 * 根据map查询支付渠道列表
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List listByMap(Map<String, Object> map);

	public List<Frp> listByPayType(Integer payType)throws PayruleBizException;
}
