package com.cost.facade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.cost.entity.CalFeeFlow;
import wusc.edu.pay.facade.cost.service.CalFeeFlowFacade;

public class TsCalFlow extends TsBase {

	@Test
	public void create() throws Exception {
		CalFeeFlowFacade calFeeFlowFacade = (CalFeeFlowFacade) context.getBean("calFeeFlowFacade");
		CalFeeFlow calFeeFlow = new CalFeeFlow();
		calFeeFlow.setBeginDate(new Date());
		calFeeFlow.setEndDate(new Date());
		calFeeFlow.setFeeWayId(1L);
		calFeeFlow.setModifyTime(new Date());
		calFeeFlow.setThisAmount(BigDecimal.TEN);
		calFeeFlow.setTotalAmount(BigDecimal.TEN);
		Long result = calFeeFlowFacade.create(calFeeFlow);

		// 列表
		PageParam pageParam = new PageParam(1, 2);
		PageBean pageBean = calFeeFlowFacade.listPage(pageParam, new HashMap<String, Object>());
		List<Object> list = pageBean.getRecordList();
		// 更新

		for (Object object : list) {
			CalFeeFlow feeFlow = (CalFeeFlow) object;
			feeFlow.setTotalAmount(BigDecimal.ZERO);
			calFeeFlowFacade.update(feeFlow);
		}
	}
}
