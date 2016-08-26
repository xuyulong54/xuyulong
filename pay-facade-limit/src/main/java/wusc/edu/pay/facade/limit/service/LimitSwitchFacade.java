package wusc.edu.pay.facade.limit.service;

import java.util.List;

import wusc.edu.pay.facade.limit.entity.LimitSwitch;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;


/**
 * 
 * <ul>
 * <li>Title:限制开关接口</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author zh
 * @version 2014-7-9
 */
public interface LimitSwitchFacade {

	/***
	 * 查询支付包下的业务功能
	 * 
	 * @param switchPackId
	 * @return
	 */
	public List<LimitSwitch> findBizFunctionList(Long switchPackId) throws LimitBizException;
	
	/***
	 * 查询支付包下的支付产品和支付方式
	 * 
	 * @param switchPackId
	 * @return
	 */
	public List<LimitSwitch> findLimitSwitchList(Long switchPackId) throws LimitBizException;


}
