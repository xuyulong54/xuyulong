package com.cost.facade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.cost.entity.CalFeeFlow;
import wusc.edu.pay.facade.cost.entity.CalFeeRateFormula;
import wusc.edu.pay.facade.cost.service.CalFeeFlowFacade;
import wusc.edu.pay.facade.cost.service.CalFeeRateFormulaFacade;

public class TsCalFeeFomular extends TsBase {

	@Test
	public void create() throws Exception {
		CalFeeRateFormulaFacade calFeeRateFormulaFacade = (CalFeeRateFormulaFacade) context.getBean("calFeeRateFormulaFacade");

		CalFeeRateFormula calFeeRateFormula = new CalFeeRateFormula();
		calFeeRateFormula.setFeeWayId(1L);
		calFeeRateFormula.setFixedFee(BigDecimal.ZERO);
		calFeeRateFormula.setMaxAmount(BigDecimal.TEN);
		calFeeRateFormula.setMaxLadderAmount(BigDecimal.TEN);
		calFeeRateFormula.setModel(1);
		calFeeRateFormula.setPercentFee(BigDecimal.ONE);
		calFeeRateFormula.setSingleMaxFee(BigDecimal.TEN);
		calFeeRateFormula.setSingleMinFee(BigDecimal.ZERO);

		Long result = calFeeRateFormulaFacade.create(calFeeRateFormula);

		// 列表
		PageParam pageParam = new PageParam(1, 2);
		PageBean pageBean = calFeeRateFormulaFacade.listPage(pageParam, new HashMap<String, Object>());
		List<Object> list = pageBean.getRecordList();
		// 更新

		for (Object object : list) {
			CalFeeRateFormula fomular = (CalFeeRateFormula) object;
			fomular.setFixedFee(BigDecimal.ONE);
			calFeeRateFormulaFacade.update(fomular);
		}
	}
}
