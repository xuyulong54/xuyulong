package wusc.edu.pay.core.cost.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.cost.entity.CalDimension;


/**
 * 计费维度DAO
 */
public interface CalDimensionDao extends BaseDao<CalDimension> {
	
	/**
	 * 根据银行渠道编号获取计费维度列表
	 * @param calInterface 银行渠道编码
	 * @return
	 */
	public List<CalDimension> getCalDimensionByCalInterface(String calInterface);
}
