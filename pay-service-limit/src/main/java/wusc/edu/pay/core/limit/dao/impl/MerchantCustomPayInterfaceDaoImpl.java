package wusc.edu.pay.core.limit.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.limit.dao.MerchantCustomPayInterfaceDao;
import wusc.edu.pay.facade.limit.entity.MerchantCustomPayInterface;

/**
 * 
 * @描述: 支付接口分流Dao实现.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-9, 下午8:47:10 .
 * @版本: V1.0 .
 */
@Repository("merchantCustomPayInterfaceDao")
public class MerchantCustomPayInterfaceDaoImpl extends BaseDaoImpl<MerchantCustomPayInterface> implements MerchantCustomPayInterfaceDao{
	
	/***
	 * 根据merchantNo查
	 * @param merchantNo
	 * @return
	 */
	public List<MerchantCustomPayInterface>  getListByMerchantNo(String merchantNo){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantNo", merchantNo);
		return super.getSqlSession().selectList(this.getStatement("getByMerchantNo"),params);
	}
	
	/***
	 * 根据merchantNo、payWay、payInterface查
	 * @param merchantNo
	 * @param payWay
	 * @param payInterface
	 * @return
	 */
	public MerchantCustomPayInterface getByMerchantNoAndWayAndInterface(String merchantNo, String payWay, String payInterface){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantNo", merchantNo);
		params.put("payWay", payWay);
		params.put("payInterface", payInterface);
		return super.getSqlSession().selectOne(this.getStatement("getByMerchantNoAndWayAndInterface"),params);
	}
}
