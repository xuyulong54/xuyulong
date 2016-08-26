/**
 * wusc.edu.pay.core.limit.dao.BizFunctionSwitchDao.java
 */
package wusc.edu.pay.core.limit.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.limit.entity.BizFunctionSwitch;


/**
 * 
 * <ul>
 * <li>Title: 业务功能开关限制dao</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-9
 */
public interface BizFunctionSwitchDao extends BaseDao<BizFunctionSwitch> {

	/**
	 * 获取业务功能开关列表
	 * 
	 * @param switchLimitPackId
	 *            开关限制包ID
	 * @param status
	 *            开关状态
	 * @return 业务功能开关列表
	 */
	public List<BizFunctionSwitch> queryBizFunctionSwitch(Long switchLimitPackId, String status);
	
	/**
	 * 获取业务功能开关根据包ID、业务功能
	 * 
	 * @param switchLimitPackId
	 *            开关限制包ID
	 * @param bizFunction
	 *            业务功能
	 * @return 业务功能开关
	 */
	public BizFunctionSwitch getBizFunctionSwitchByPackIdAndBizFunction(Long switchLimitPackId, String bizFunction);

	/***
	 * 删除业务功能开关
	 * @param switchLimitPackId
	 * @param bizFunction
	 * @return
	 */
	public long deleteBizFunctionSwitchByPackIdAndFunction(
			Long switchLimitPackId, String bizFunction);
	
	
	/**
	 * 根据商户编号查询业务功能
	 * @param merchantNo
	 * @return
	 */
	public List<BizFunctionSwitch> getBizFunctionSwitchByMerchantNo(String merchantNo);
}
