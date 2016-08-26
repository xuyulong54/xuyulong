package wusc.edu.pay.facade.limit.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.limit.dao.MerchantCustomPayInterfaceDao;
import wusc.edu.pay.facade.limit.entity.MerchantCustomPayInterface;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;
import wusc.edu.pay.facade.limit.service.MerchantCustomPayInterfaceFacade;


/**
 * 
 * @描述: 支付接口分流接口实现类.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-9, 下午8:45:53 .
 * @版本: V1.0 .
 */
@Component("merchantCustomPayInterfaceFacade")
public class MerchantCustomPayInterfaceFacadeImpl implements MerchantCustomPayInterfaceFacade {

	@Autowired
	private MerchantCustomPayInterfaceDao merchantCustomPayInterfaceDao;

	/****
	 * 查询列表方法
	 */
	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws LimitBizException {
		return merchantCustomPayInterfaceDao.listPage(pageParam, paramMap);
	}

	/***
	 * 增加支付接口分流
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public long saveMerchantCustomPayInterface(MerchantCustomPayInterface entity) throws LimitBizException {
		return merchantCustomPayInterfaceDao.insert(entity);
	}

	/***
	 * 修改支付接口分流
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public long updateMerchantCustomPayInterface(MerchantCustomPayInterface entity) throws LimitBizException {
		return merchantCustomPayInterfaceDao.update(entity);
	}

	/***
	 * 删除支付接口分流
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public long deleteMerchantCustomPayInterface(Long id) throws LimitBizException {
		return merchantCustomPayInterfaceDao.deleteById(id);
	}

	/***
	 * 根据ID查
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public MerchantCustomPayInterface getById(Long id) {
		return merchantCustomPayInterfaceDao.getById(id);
	}

	/***
	 * 根据merchantNo查
	 * 
	 * @param merchantNo
	 * @return
	 */
	public List<MerchantCustomPayInterface> getListByMerchantNo(String merchantNo) {
		return merchantCustomPayInterfaceDao.getListByMerchantNo(merchantNo);
	}

	/**
	 * 根据merchantNo,payWay,payInterface查
	 * 
	 * @param merchantNo
	 * @param payWay
	 * @param payInterface
	 * @return
	 */
	public MerchantCustomPayInterface getByMerchantNoPayWayAndPayInterface(String merchantNo, String payWay, String payInterface) {
		return merchantCustomPayInterfaceDao.getByMerchantNoAndWayAndInterface(merchantNo, payWay, payInterface);
	}

}
