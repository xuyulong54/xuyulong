package wusc.edu.pay.facade.cost.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.cost.biz.CalFeeFlowBiz;
import wusc.edu.pay.facade.cost.entity.CalFeeFlow;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;
import wusc.edu.pay.facade.cost.service.CalFeeFlowFacade;


/**
 * 
 * @描述: 计费维度信息，Dubbo服务接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2014-4-15, 下午2:33:37
 */
@Component("calFeeFlowFacade")
public class CalFeeFlowFacadeImpl implements CalFeeFlowFacade {

	@Autowired
	private CalFeeFlowBiz calFeeFlowBiz;

	@Override
	public long create(CalFeeFlow entity) throws CostBizException {
		// TODO Auto-generated method stub
		return calFeeFlowBiz.create(entity);
	}

	@Override
	public long update(CalFeeFlow entity) throws CostBizException {
		return calFeeFlowBiz.update(entity);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap)
			throws CostBizException {
		return calFeeFlowBiz.listPage(pageParam, paramMap);
	}

	@Override
	public CalFeeFlow getById(long id) throws CostBizException {
		return calFeeFlowBiz.getById(id);
	}

	@Override
	public List<CalFeeFlow> getByFormulaId(int formulaId)
			throws CostBizException {
		return null;// calFeeFlowBiz.getByFormulaId(formulaId);
	}

}