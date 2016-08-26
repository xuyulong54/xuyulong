package wusc.edu.pay.facade.cost.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.cost.biz.CalFeeRateFormulaBiz;
import wusc.edu.pay.facade.cost.entity.CalFeeRateFormula;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;
import wusc.edu.pay.facade.cost.service.CalFeeRateFormulaFacade;


/**
 * 
 * @描述: 计费公式信息，Dubbo服务接口实现类.
 * @作者: huqian .
 * @创建时间: 2014-7-1, 上午11:35:37
 */
@Component("calFeeRateFormulaFacade")
public class CalFeeRateFormulaFacadeImpl implements CalFeeRateFormulaFacade {
	
	@Autowired
	private CalFeeRateFormulaBiz calFeeRateFormulaBiz;

	/**
	 * 创建计费公式信息
	 * 
	 * @param entity
	 * @return
	 */
	public long create(CalFeeRateFormula entity) {
		return calFeeRateFormulaBiz.create(entity);
	}

	/**
	 * 修改计费公式信息
	 * 
	 * @param entity
	 * @return
	 */
	public long update(CalFeeRateFormula entity) {
		return calFeeRateFormulaBiz.update(entity);
	}

	/**
	 * 分页查询计费公式信息
	 * 
	 * @param entity
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return calFeeRateFormulaBiz.listPage(pageParam, paramMap);
	}

	/**
	 * 根据ID查询计费公式信息
	 * 
	 * @param entity
	 * @return
	 */
	public CalFeeRateFormula getById(long id) {
		CalFeeRateFormula calFeeRateFormula = calFeeRateFormulaBiz.getById(id);
		return calFeeRateFormula;
	}

	/**
	 * 根据计费约束ID查找计费公式信息
	 */
	public List<CalFeeRateFormula> getByFeeWayId(long feeWayId) throws CostBizException {
		return calFeeRateFormulaBiz.getRateFormulaByFeeWayId(feeWayId);
	}

}
