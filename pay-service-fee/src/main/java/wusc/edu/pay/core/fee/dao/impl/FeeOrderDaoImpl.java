package wusc.edu.pay.core.fee.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.fee.dao.FeeOrderDao;
import wusc.edu.pay.facade.fee.entity.FeeOrder;


@Repository("feeOrderDao")
public class FeeOrderDaoImpl extends BaseDaoImpl<FeeOrder> implements FeeOrderDao {
	/**
	 * 根据流水号查询计费订单
	 * @return
	 */
	public FeeOrder getFeeOrderByTrxFlowNo(String trxFlowNo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("trxFlowNo", trxFlowNo);
		return this.getBy(map);
	}
	
	/**
	 * 根据商户编号和商户订单好查询计费订单
	 * @param merchantNo
	 * @param orderNo
	 * @return
	 */
	public FeeOrder getFeeOrderByMerchantNoAndOrderNo(String merchantNo, String orderNo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantNo", merchantNo);
		map.put("merchantOrderNo", orderNo);
		return this.getBy(map);
	}

	@Override
	public FeeOrder getByTrxFlowNoAndFeeItem(String trxFlowNo, Integer feeItem) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("trxFlowNo", trxFlowNo);
		map.put("calculateFeeItem", feeItem);
		return this.getBy(map);
	}
	
	/**
	 * 
	 * @描述: 结算记录作废，计费订单删除.
	 * @作者: Along.shen .
	 * @创建时间:2015-2-4,下午5:17:04.
	 * @版本:
	 */
	public void deleteByTrxFlowNoLike(String trxFlowNo){
		super.getSessionTemplate().delete(getStatement("deleteByTrxFlowNoLike"), trxFlowNo);
	}
}
