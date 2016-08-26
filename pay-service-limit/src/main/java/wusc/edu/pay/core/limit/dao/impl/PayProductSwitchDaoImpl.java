package wusc.edu.pay.core.limit.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.limit.dao.PayProductSwitchDao;
import wusc.edu.pay.facade.limit.entity.PayProductSwitch;


/**
 * 
 * 
 * <ul>
 * <li>Title: 支付产品开关dao实现</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-9
 */
@Repository(value = "payProductSwitchDao")
public class PayProductSwitchDaoImpl extends BaseDaoImpl<PayProductSwitch> implements PayProductSwitchDao {

	/**
	 * 获取支付产品开关列表
	 * 
	 * @param switchLimitPackId
	 *            开关限制包ID
	 * @param status
	 *            开关状态
	 * @return 支付产品开关列表
	 */
	@Override
	public List<PayProductSwitch> queryPayProductSwitch(Long switchLimitPackId, String status) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("switchLimitPackId", switchLimitPackId);
		params.put("status", status);

		return super.getSqlSession().selectList(this.getStatement("listBy"), params);
	}
	
	/**
	 * 获取支付产品开关根据包ID、支付产品
	 * 
	 * @param switchLimitPackId
	 *            开关限制包ID
	 * @param payProduct
	 *            支付产品
	 * @return 支付产品开关
	 */
	public PayProductSwitch getPayProductByPackIdAndProduct(Long switchLimitPackId, String payProduct){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("switchLimitPackId", switchLimitPackId);
		params.put("payProduct", payProduct);
		
		return super.getBy(params);
	}
	
	/***
	 * 删除支付产品开关
	 * @param switchLimitPackId
	 * @param payProduct
	 * @return
	 */
	public long deletePayProductSwitchByPackIdAndProduct(
			Long switchLimitPackId, String payProduct){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("switchLimitPackId", switchLimitPackId);
		params.put("payProduct", payProduct);
		return this.getSqlSession().delete(this.getStatement("deleteByPackIdAndProduct"), params); 
	}

}
