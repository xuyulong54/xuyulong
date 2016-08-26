package wusc.edu.pay.core.cost.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.cost.entity.CalCostInterface;


/**
 * 
 * @描述: 成本计费接口表的数据访问层接口.
 * @作者: WuShuicheng.
 * @创建: 2014-7-9,下午5:17:04
 * @版本: V1.0
 *
 */
public interface CalCostInterfaceDao extends BaseDao<CalCostInterface>{

	/**
	 * 根据计费接口编码获取计费接口信息
	 * @param interfaceCode 计费接口编码
	 * @return
	 */
	public CalCostInterface getByInterfaceCode(String interfaceCode);

	/**
	 * 根据计费接口编码删除计费接口信息
	 * @param calCostInterfaceCode
	 */
	public void deleteByCalCostInterfaceCode(String calCostInterfaceCode);

}
