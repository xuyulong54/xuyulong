/**
 * wusc.edu.pay.core.limit.dao.impl.PayWaySwitchDaoImpl.java
 */
package wusc.edu.pay.core.limit.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.limit.dao.PayWaySwitchDao;
import wusc.edu.pay.facade.limit.entity.PayWaySwitch;


/**
 * 
 * <ul>
 * <li>Title: 支付方式dao实现</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-9
 */
@Repository("payWaySwitchDao")
public class PayWaySwitchDaoImpl extends BaseDaoImpl<PayWaySwitch> implements PayWaySwitchDao {

	/**
	 * 获取支付方式开关列表
	 * 
	 * @param switchLimitPackId
	 *            开关限制包ID
	 * @param status
	 *            开关状态
	 * @return 支付方式开关列表
	 */
	@Override
	public List<PayWaySwitch> queryPayWaySwitch(Long switchLimitPackId, String status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("switchLimitPackId", switchLimitPackId);
		params.put("status", status);
		return super.getSqlSession().selectList(this.getStatement("listBy"), params);
	}

	/**
	 * 获取支付方式开关根据包ID、支付产品、支付方式
	 * 
	 * @param switchLimitPackId
	 *            开关限制包ID
	 * @param payProduct
	 *            支付产品
	 * @param payWay
	 *            支付方式
	 * @return 支付方式开关
	 */
	public PayWaySwitch getPayWaySwitchByPackIdAndProductAndWay(Long switchLimitPackId, String payProduct, String payWay) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("switchLimitPackId", switchLimitPackId);
		params.put("payProduct", payProduct);
		params.put("payWay", payWay);
		return super.getBy(params);
	}
	
	/***
	 * 删除支付方式开关
	 * @param switchLimitPackId
	 * @param payProduct
	 * @param payWay
	 * @return
	 */
	public long deletePayProductSwitchByPackIdAndProductAndWay(
			Long switchLimitPackId, String payProduct, String payWay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("switchLimitPackId", switchLimitPackId);
		params.put("payProduct", payProduct);
		params.put("payWay", payWay);
		return this.getSqlSession().delete(this.getStatement("deleteByPackIdAndProductAndWay"), params);
	}

}
