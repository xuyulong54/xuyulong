package wusc.edu.pay.facade.cost.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.cost.biz.CalDimensionBiz;
import wusc.edu.pay.facade.cost.entity.CalDimension;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;
import wusc.edu.pay.facade.cost.service.CalDimensionFacade;


/**
 * 
 * @描述: 计费维度信息，Dubbo服务接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2014-4-15, 下午2:33:37
 */
@Component("calDimensionFacade")
public class CalDimensionFacadeImpl implements CalDimensionFacade {
	
	@Autowired
	private CalDimensionBiz calDimensionBiz;

	@Override
	public long create(CalDimension entity) {
		return calDimensionBiz.createCalDimension(entity);
	}

	@Override
	public long update(CalDimension entity) {
		return calDimensionBiz.updateCalDimension(entity);
		
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap){
		return calDimensionBiz.listPage(pageParam, paramMap);
	}

	@Override
	public CalDimension getById(long id) {
		return calDimensionBiz.getById(id);
	}

	@Override
	public List<CalDimension> getByProductName(String productName){
		return calDimensionBiz.getByProductName(productName);
	}

	@Override
	public void deleteById(long id) {
		calDimensionBiz.deleteById(id);
	}

	@Override
	public List<CalDimension> listAll() {
		return calDimensionBiz.listAll();
	}

	@Override
	public List<CalDimension> getByCostInterfaceCode(String calCostInterfaceCode) throws CostBizException {
		return calDimensionBiz.getByCalCostInterfaceCode(calCostInterfaceCode);
	}


}