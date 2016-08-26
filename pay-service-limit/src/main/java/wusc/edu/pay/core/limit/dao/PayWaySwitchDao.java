/**
 * wusc.edu.pay.core.limit.dao.PayWaySwitchDao.java
 */
package wusc.edu.pay.core.limit.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.limit.entity.PayWaySwitch;


/**
 * 
 * <ul>
 * <li>Title: 支付方式dao</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-9
 */
public interface PayWaySwitchDao extends BaseDao<PayWaySwitch> {

	/**
	 * 获取支付方式开关列表
	 * 
	 * @param switchLimitPackId
	 *            开关限制包ID
	 * @param status
	 *            开关状态
	 * @return 支付方式开关列表
	 */
	public List<PayWaySwitch> queryPayWaySwitch(Long switchLimitPackId, String status);

	
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
	public PayWaySwitch getPayWaySwitchByPackIdAndProductAndWay(Long switchLimitPackId, String payProduct, String payWay);

	
	/***
	 * 删除支付方式开关
	 * @param switchLimitPackId
	 * @param payProduct
	 * @param payWay
	 * @return
	 */
	public long deletePayProductSwitchByPackIdAndProductAndWay(
			Long switchLimitPackId, String payProduct, String payWay);
}
