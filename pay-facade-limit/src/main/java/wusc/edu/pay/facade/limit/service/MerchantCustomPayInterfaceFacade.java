package wusc.edu.pay.facade.limit.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.limit.entity.MerchantCustomPayInterface;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;


/**
 * 
 * @描述: 支付接口分流接口.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-9, 下午8:36:29 .
 * @版本: V1.0 .
 */
public interface MerchantCustomPayInterfaceFacade {
	/****
	 * 查询列表方法
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws LimitBizException;	
	/***
	 * 增加支付接口分流
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long saveMerchantCustomPayInterface(MerchantCustomPayInterface entity) throws LimitBizException;
	/***
	 * 修改支付接口分流
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long updateMerchantCustomPayInterface(MerchantCustomPayInterface entity) throws LimitBizException;
	/***
	 * 删除支付接口分流
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public long deleteMerchantCustomPayInterface(Long id) throws LimitBizException;

	/***
	 * 根据ID查
	 * @param entity
	 * @return
	 * @throws LimitBizException
	 */
	public MerchantCustomPayInterface getById (Long id) throws LimitBizException;
	
	/***
	 * 根据merchantNo查
	 * @param merchantNo
	 * @return
	 * @throws LimitBizException
	 */
	public List<MerchantCustomPayInterface>  getListByMerchantNo (String merchantNo) throws LimitBizException;
	
	/**
	 * 根据merchantNo,payWay,payInterface查
	 * @param merchantNo
	 * @param payWay
	 * @param payInterface
	 * @return
	 */
	public MerchantCustomPayInterface getByMerchantNoPayWayAndPayInterface (String merchantNo ,String payWay,String payInterface);
}
