package wusc.edu.pay.facade.limit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.limit.biz.LimitSwitchBiz;
import wusc.edu.pay.facade.limit.entity.LimitSwitch;
import wusc.edu.pay.facade.limit.service.LimitSwitchFacade;


/**
 * 
 * <ul>
 * <li>Title: 限制开关实现</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author zh
 * @version 2014-7-9
 */
@Component("limitSwitchFacade")
public class LimitSwitchFacadeImpl implements LimitSwitchFacade {

	/**
	 * 限制开关
	 */   
	@Autowired
	private LimitSwitchBiz limitSwitchBiz;

	/***
	 * 查询支付包下的业务功能
	 * 
	 * @param switchPackId
	 * @return
	 */
	public List<LimitSwitch> findBizFunctionList(Long switchPackId){
		return limitSwitchBiz.findBizFunctionList(switchPackId);
	}
	
	/***
	 * 查询支付包下的支付产品和支付方式
	 * 
	 * @param switchPackId
	 * @return
	 */
	public List<LimitSwitch> findLimitSwitchList(Long switchPackId){
		return limitSwitchBiz.findLimitSwitchList(switchPackId);
	}

}
