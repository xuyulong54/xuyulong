package com.cost.facade;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.cost.entity.CalDimension;
import wusc.edu.pay.facade.cost.service.CalDimensionFacade;

public class TsCalDimension extends TsBase {

	@Test
	public void create() throws Exception {
		CalDimensionFacade calDimensionFacade = (CalDimensionFacade) context.getBean("calDimensionFacade");
		CalDimension calDimension = new CalDimension();
		calDimension.setCalCostInterfaceCode("interface code2");
		calDimension.setCalProduct("pro name2");
		Long result = calDimensionFacade.create(calDimension);

		// 列表
		PageParam pageParam = new PageParam(1, 10);
		PageBean pageBean = calDimensionFacade.listPage(pageParam, new HashMap<String, Object>());
		List<Object> list = pageBean.getRecordList();
		// 更新

		for (Object object : list) {
			CalDimension dimension = (CalDimension) object;
			dimension.setCalProduct("pro1");
			calDimensionFacade.update(dimension);
		}
	}
}
