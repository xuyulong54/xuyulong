package wusc.edu.pay.facade.fee.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.fee.biz.FeeFormulaBiz;
import wusc.edu.pay.facade.fee.entity.FeeFormula;
import wusc.edu.pay.facade.fee.service.FeeFormulaeFacade;


@Component("feeFormulaeFacade")
public class FeeFormulaeFacadeImpl implements FeeFormulaeFacade {
	@Autowired
	private FeeFormulaBiz feeFormulaBiz;

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> map) {
		return feeFormulaBiz.listPage(pageParam, map);
	}

	@Override
	public void createFeeFormulae(FeeFormula feeFormula) {
		feeFormulaBiz.createFeeFormulae(feeFormula);
	}

	@Override
	public FeeFormula getById(Long id) {
		return feeFormulaBiz.getById(id);
	}

	@Override
	public void updateFeeFormulae(FeeFormula feeFormula) {
		feeFormulaBiz.updateFeeFormulae(feeFormula);
	}

	@Override
	public List<FeeFormula> listByCalWayId(Long calWayId) {
		return feeFormulaBiz.listByCalWayId(calWayId);
	}

	@Override
	public boolean checkUnique(FeeFormula feeFormula) {
		return feeFormulaBiz.checkUnique(feeFormula);
	}

}
