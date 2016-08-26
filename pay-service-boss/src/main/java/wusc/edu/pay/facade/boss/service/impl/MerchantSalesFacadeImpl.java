package wusc.edu.pay.facade.boss.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.boss.biz.MerchantSalesBiz;
import wusc.edu.pay.facade.boss.entity.MerchantSales;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;
import wusc.edu.pay.facade.boss.service.MerchantSalesFacade;



@Component("merchantSalesFacade")
public class MerchantSalesFacadeImpl implements MerchantSalesFacade {
	@Autowired
	private MerchantSalesBiz merchantSalesBiz;
	
	public long create(MerchantSales sales){
		return merchantSalesBiz.createMerchantSales(sales);
	}

	
	public long update(MerchantSales sales){
		return merchantSalesBiz.updateMerchantSales(sales);
	}

	public MerchantSales getById(Long id) throws BossBizException {
		return merchantSalesBiz.getById(id);
	}

	public MerchantSales getByMerchantNo(String merchantNo) {
		return merchantSalesBiz.getByMerchantNo(merchantNo);
	}

	public void deteleById(Long sId) {
		merchantSalesBiz.deteleById(sId);
	}


	public List<MerchantSales> listByCondition(Map<String, Object> paramMap)
			throws BossBizException {
		return merchantSalesBiz.listByCondition(paramMap);
	}
	
}
