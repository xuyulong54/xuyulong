/**
 * wusc.edu.pay.core.limit.dao.impl.BizFunctionSwitchDaoImpl.java
 */
package wusc.edu.pay.core.limit.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.limit.dao.BizFunctionSwitchDao;
import wusc.edu.pay.facade.limit.entity.BizFunctionSwitch;


/**
 * 
 * <ul>
 * <li>Title: 业务功能限制dao实现</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-9
 */
@Repository("bizFunctionSwitchDao")
public class BizFunctionSwitchDaoImpl extends BaseDaoImpl<BizFunctionSwitch> implements BizFunctionSwitchDao {

	
	
	@Override
	public List<BizFunctionSwitch> queryBizFunctionSwitch(Long switchLimitPackId, String status) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("switchLimitPackId", switchLimitPackId);
		params.put("status", status);
		
		return this.getSqlSession().selectList(this.getStatement("listBy"), params);
	}
	
	/**
	 * 获取业务功能开关根据包ID、业务功能
	 * 
	 * @param switchLimitPackId
	 *            开关限制包ID
	 * @param bizFunction
	 *            业务功能
	 * @return 业务功能开关
	 */
	public BizFunctionSwitch getBizFunctionSwitchByPackIdAndBizFunction(Long switchLimitPackId, String bizFunction){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("switchLimitPackId", switchLimitPackId);
		params.put("bizFunction", bizFunction);
		
		return super.getBy(params);
	}
	
	/***
	 * 删除业务功能开关
	 * @param switchLimitPackId
	 * @param bizFunction
	 * @return
	 */
	public long deleteBizFunctionSwitchByPackIdAndFunction(
			Long switchLimitPackId, String bizFunction){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("switchLimitPackId", switchLimitPackId);
		params.put("bizFunction", bizFunction);
		return this.getSqlSession().delete(this.getStatement("deleteByPackIdAndFunction"), params); 
	}
	
	
	/**
	 * 根据商户编号查询业务功能
	 * @param merchantNo
	 * @return
	 */
	public List<BizFunctionSwitch> getBizFunctionSwitchByMerchantNo(String merchantNo){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantNo", merchantNo);
		return super.getSqlSession().selectList("wusc.edu.pay.core.limit.dao.impl.BizFunctionSwitchDaoImpl.getBizFunctionByMerchantNo",params);
	}

}
