package wusc.edu.pay.core.cost.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.cost.dao.CalCostInterfaceDao;
import wusc.edu.pay.facade.cost.entity.CalCostInterface;


/**
 * 
 * @描述: 成本计费接口表的数据访问层接口实现类.
 * @作者: WuShuicheng.
 * @创建: 2014-7-9,下午5:18:52
 * @版本: V1.0
 *
 */
@Repository("calCostInterfaceDao")
public class CalCostInterfaceDaoImpl extends BaseDaoImpl<CalCostInterface> implements CalCostInterfaceDao {

	/**
	 * 根据计费接口编码获取计费接口信息
	 * @param interfaceCode 计费接口编码
	 * @return
	 */
	public CalCostInterface getByInterfaceCode(String interfaceCode){
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("interfaceCode", interfaceCode);
		return super.getBy(paramMap);
	}

	@Override
	public void deleteByCalCostInterfaceCode(String calCostInterfaceCode) {
		super.getSessionTemplate().delete(getStatement("deleteByCalCostInterfaceCode"), calCostInterfaceCode);
	}

}
