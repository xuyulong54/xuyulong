package wusc.edu.pay.core.limit.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.limit.entity.MerchantCustomPayInterface;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;

/**
 * 
 * @描述: 支付接口分流DAO.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-9, 下午8:47:00 .
 * @版本: V1.0 .
 */
public interface MerchantCustomPayInterfaceDao extends BaseDao<MerchantCustomPayInterface>{

	/***
	 * 根据merchantNo查
	 * @param merchantNo
	 * @return
	 */
	List<MerchantCustomPayInterface>  getListByMerchantNo(String merchantNo);
	
	
	/***
	 * 根据merchantNo、payWay、payInterface查
	 * @param merchantNo
	 * @param payWay
	 * @param payInterface
	 * @return
	 */
	MerchantCustomPayInterface getByMerchantNoAndWayAndInterface(String merchantNo, String payWay, String payInterface);
}
