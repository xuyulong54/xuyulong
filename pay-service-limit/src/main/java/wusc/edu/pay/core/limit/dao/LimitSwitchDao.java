package wusc.edu.pay.core.limit.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.limit.entity.LimitSwitch;


public interface LimitSwitchDao extends BaseDao<LimitSwitch>{

	/***
	 * 查询所有有效的支付产品列表
	 * @return
	 */
	public List<LimitSwitch> listAllProduct();
	
	/**
	 * 根据支付产品编号查找支付方式
	 * @param payProductCode
	 * @return
	 */
	public List<LimitSwitch> findPayWayByPayProductCode(String payProductCode);
}
