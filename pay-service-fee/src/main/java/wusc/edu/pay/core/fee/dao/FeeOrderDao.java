package wusc.edu.pay.core.fee.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.fee.entity.FeeOrder;


public interface FeeOrderDao extends BaseDao<FeeOrder>{

	/**
	 * 根据流水号查询计费订单
	 * @return
	 */
	FeeOrder getFeeOrderByTrxFlowNo(String trxFlowNo);

	/**
	 * 根据商户编号和商户订单好查询计费订单
	 * @param merchantNo
	 * @param orderNo
	 * @return
	 */
	FeeOrder getFeeOrderByMerchantNoAndOrderNo(String merchantNo, String orderNo);

	/**
	 * 根据支付流水号和计费项查询计费订单
	 * @param trxFlowNo
	 * @param feeItem
	 * @return
	 */
	FeeOrder getByTrxFlowNoAndFeeItem(String trxFlowNo, Integer feeItem);

	/**
	 * 
	 * @描述: 结算记录作废，计费订单删除.
	 * @作者: Along.shen .
	 * @创建时间:2015-2-4,下午5:17:04.
	 * @版本:
	 */
	void deleteByTrxFlowNoLike(String trxFlowNo);

}
