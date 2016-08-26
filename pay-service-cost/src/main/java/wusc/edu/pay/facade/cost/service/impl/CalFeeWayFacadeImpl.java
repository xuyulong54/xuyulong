package wusc.edu.pay.facade.cost.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.cost.biz.CalFeeWayBiz;
import wusc.edu.pay.facade.cost.entity.CalFeeWay;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;
import wusc.edu.pay.facade.cost.service.CalFeeWayFacade;


/**
 * 
 * @描述: 计费维度信息，Dubbo服务接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2014-4-15, 下午2:33:37
 */
@Component("calFeeWayFacade")
public class CalFeeWayFacadeImpl implements CalFeeWayFacade {

	@Autowired
	private CalFeeWayBiz calFeeWayBiz;

	@Override
	public long create(CalFeeWay entity) throws CostBizException {
		// TODO Auto-generated method stub
		return calFeeWayBiz.create(entity);
	}

	@Override
	public long update(CalFeeWay entity) throws CostBizException {
		return calFeeWayBiz.update(entity);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap)
			throws CostBizException {
		return calFeeWayBiz.listPage(pageParam, paramMap);
	}

	@Override
	public CalFeeWay getById(long id) throws CostBizException {
		return calFeeWayBiz.getById(id);
	}

	@Override
	public List<CalFeeWay> getByDimensionId(int dimensionId)
			throws CostBizException {
		return null;// calFeeWayBiz.getByDimensionId(dimensionId);
	}

	@Override
	public void deleteById(long id) throws CostBizException {
		calFeeWayBiz.deleteById(id);
	}

	@Override
	public List<CalFeeWay> listAll() throws CostBizException {
		return calFeeWayBiz.listAll();
	}
}