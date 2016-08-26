package wusc.edu.pay.core.limit.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.limit.entity.PayProductSwitch;


/**
 * 
 * 
*<ul>
*<li>Title: 支付产品开关dao</li>
*<li>Description: </li>
*<li>Copyright: www.gzzyzz.com</li>
*<li>Company:</li>
*</ul>
*
 * @author Hill
 * @version 2014-7-9
 */
public interface PayProductSwitchDao extends BaseDao<PayProductSwitch>{

	/**
	 * 获取支付产品开关列表
	 * 
	 * @param switchLimitPackId
	 *            开关限制包ID
	 * @param status
	 *            开关状态
	 * @return 支付产品开关列表
	 */
	public List<PayProductSwitch> queryPayProductSwitch(Long switchLimitPackId, String status);
	
	/**
	 * 获取支付产品开关根据包ID、支付产品
	 * 
	 * @param switchLimitPackId
	 *            开关限制包ID
	 * @param payProduct
	 *            支付产品
	 * @return 支付产品开关
	 */
	public PayProductSwitch getPayProductByPackIdAndProduct(Long switchLimitPackId, String payProduct);

	
	/***
	 * 删除支付产品开关
	 * @param switchLimitPackId
	 * @param payProduct
	 * @return
	 */
	public long deletePayProductSwitchByPackIdAndProduct(
			Long switchLimitPackId, String payProduct);

}
