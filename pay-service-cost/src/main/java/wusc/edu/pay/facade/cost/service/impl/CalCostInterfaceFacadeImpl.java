package wusc.edu.pay.facade.cost.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.cost.biz.CalCostInterfaceBiz;
import wusc.edu.pay.core.cost.dao.CalCostInterfaceDao;
import wusc.edu.pay.facade.cost.entity.CalCostInterface;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;
import wusc.edu.pay.facade.cost.service.CalCostInterfaceFacade;


/**
 * 
 * @描述: 成本计费接口的Dubbo服务接口的实现类.
 * @作者: WuShuicheng.
 * @创建: 2014-7-9,下午5:14:23
 * @版本: V1.0
 *
 */
@Component("calCostInterfaceFacade")
public class CalCostInterfaceFacadeImpl implements CalCostInterfaceFacade {
	@Autowired
	private CalCostInterfaceBiz calCostInterfaceBiz;
	@Autowired
	private CalCostInterfaceDao calCostInterfaceDao;

	@Override
	public long create(CalCostInterface entity) throws CostBizException {
//		return calCostInterfaceDao.insert(entity);
		return calCostInterfaceBiz.createCostInterface(entity);
	}

	@Override
	public long update(CalCostInterface entity) throws CostBizException {
		return calCostInterfaceBiz.updateCostInterface(entity);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws CostBizException {
		return calCostInterfaceDao.listPage(pageParam, paramMap);
	}

	@Override
	public CalCostInterface getById(long id) throws CostBizException {
		return calCostInterfaceDao.getById(id);
	}

	@Override
	public void deleteById(long id) throws CostBizException {
		calCostInterfaceDao.deleteById(id);
		
	}

	@Override
	public void deleteByCalCostInterfaceCode(String calCostInterfaceCode) throws CostBizException {
		calCostInterfaceDao.deleteByCalCostInterfaceCode(calCostInterfaceCode);
	}

	@Override
	public CalCostInterface getByCalCostInterfaceCode(String calCostInterfaceCode) throws CostBizException {
		return calCostInterfaceBiz.getByCalCostInterfaceCode(calCostInterfaceCode);
	}

	@Override
	public List<CalCostInterface> listAll() throws CostBizException {
		return calCostInterfaceDao.listBy(new HashMap<String,Object>());
	}
	

}
