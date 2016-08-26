package wusc.edu.pay.core.cost.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.core.biz.BaseBizImpl;
import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.core.cost.dao.CalCostInterfaceDao;
import wusc.edu.pay.facade.cost.entity.CalCostInterface;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;


@Component("calCostInterfaceBiz")
public class CalCostInterfaceBiz extends BaseBizImpl<CalCostInterface> {
	
	@Autowired
	private CalCostInterfaceDao calCostInterfaceDao;
	
	@Override
	protected BaseDao<CalCostInterface> getDao() {
		return calCostInterfaceDao;
	}
	
	/**
	 * 根据计费接口编码获取计费接口信息
	 * @param interfaceCode 计费接口编码
	 * @return
	 */
	public CalCostInterface getByInterfaceCode(String interfaceCode){
		return calCostInterfaceDao.getByInterfaceCode(interfaceCode);
	}
	
	
	public long createCostInterface(CalCostInterface calCostInterface){
		CalCostInterface calCostInterfaceTemp = getByCalCostInterfaceCode(calCostInterface.getInterfaceCode());
		if(calCostInterfaceTemp!=null){
			throw CostBizException.COST_INTERFACE_IS_EXIST;
		}
		return calCostInterfaceDao.insert(calCostInterface);
	}

	public long updateCostInterface(CalCostInterface calCostInterface){
/*		List<CalCostInterface> calCostInterfaceList = getByCalCostInterfaceCode(calCostInterface.getInterfaceCode());
		if(calCostInterfaceList.size()>0){
			throw CalCostBizException.COST_INTERFACE_IS_EXIST;
		}*/
		return calCostInterfaceDao.update(calCostInterface);
	}
	
	public CalCostInterface getByCalCostInterfaceCode(String calCostInterfaceCode){
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("interfaceCode", calCostInterfaceCode);
		return calCostInterfaceDao.getBy(paramMap);
	}

}
