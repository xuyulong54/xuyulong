package wusc.edu.pay.facade.fee.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.fee.biz.FeeCalculateWayBiz;
import wusc.edu.pay.facade.fee.entity.FeeCalculateWay;
import wusc.edu.pay.facade.fee.service.FeeCalculateWayFacade;


@Component("feeCalculateWayFacade")
public class FeeCalculateWayFacadeImpl implements FeeCalculateWayFacade {
	@Autowired
	private FeeCalculateWayBiz feeCalculateWayBiz;
	
	@Override
	public PageBean queryFeeCalculateWayList(PageParam pageParam,
			Map<String, Object> param) {
		return feeCalculateWayBiz.queryFeeCalculateWayList(pageParam, param);
	}

	@Override
	public void createFeeCalculateWay(FeeCalculateWay feeCalculateWay) {
		feeCalculateWayBiz.createFeeCalculateWay(feeCalculateWay);
	}

	@Override
	public FeeCalculateWay getById(Long feeCalculateWayId) {
		return feeCalculateWayBiz.getById(feeCalculateWayId);
	}

	@Override
	public void updateFeeCalculateWay(FeeCalculateWay feeCalculateWay) {
		feeCalculateWayBiz.updateFeeCalculateWay(feeCalculateWay);
	}

	@Override
	public boolean checkUnique(FeeCalculateWay feeCalculateWay) {
		return feeCalculateWayBiz.checkUnique(feeCalculateWay);
	}

	@Override
	public PageBean listDimensionAndCalWay(PageParam pageParam,
			Map<String, Object> map) {
		return feeCalculateWayBiz.listDimensionAndCalWay(pageParam,map);
	}

}
