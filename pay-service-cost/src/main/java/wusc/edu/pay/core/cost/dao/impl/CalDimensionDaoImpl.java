package wusc.edu.pay.core.cost.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.cost.dao.CalDimensionDao;
import wusc.edu.pay.facade.cost.entity.CalDimension;


@Repository("calDimensionDao")
public class CalDimensionDaoImpl extends BaseDaoImpl<CalDimension> implements
		CalDimensionDao {
	
	/**
	 * 根据银行渠道编号获取计费维度列表
	 * @param calInterface 银行渠道编码
	 * @return
	 */
	@Override
	public List<CalDimension> getCalDimensionByCalInterface(
			String calInterface) {
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("calCostInterfaceCode", calInterface);
		return super.listBy(paramMap);
	}


}
