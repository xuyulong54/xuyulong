package wusc.edu.pay.facade.fee.service;

import java.sql.SQLException;
import java.util.Date;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.facade.fee.dto.FeeCalculateResultDTO;
import wusc.edu.pay.facade.fee.exceptions.FeeBizException;


/**
 * 
 * @desc 费率计算相关Facade
 * @author shenjialong
 * @date 2014-6-27,下午4:36:55
 */
public interface CalculateFeeFacade {

	/**
	 * 费率预算接口： 计算收款方（payee）以及付款方（payer）的费率，分别保存于计费结果DTO的不同属性中
	 * 
	 * @param userNo
	 *            费率调用者编号
	 * @param userType
	 *            用户类型
	 * @param calFeeItem
	 *            计费项 CalculateFeeItemEnum
	 * @param payProduct
	 *            支付产品编号
	 * @param frpCode
	 *            支付方式编号
	 * @param amount
	 *            待计费金额
	 * @param transferDate
	 *            交易发起时间
	 * @param merchantName
	 *            商户名称
	 * @param orderNo
	 *            商户订单号
	 * @param trxNo
	 *            交易流水号
	 * @return
	 */
	public FeeCalculateResultDTO preCaculateFee(String userNo, Integer userType, Integer calFeeItem, String payProduct,
			String frpCode, Double amount, Date transferDate, String merchantName, String orderNo, String trxNo)
			throws FeeBizException, BizException;

	/**
	 * 退款退手续费专用接口
	 * 
	 * @param refundOrderNo
	 * @param oldTrxNo
	 * @param refundAmount
	 * @return
	 * @throws FeeBizException
	 */
	public FeeCalculateResultDTO preCaculateFee(String refundOrderNo, String oldTrxNo, String newTrxNo, Double refundAmount)
			throws FeeBizException;

	/**
	 * 处理计费订单
	 * 
	 * @param order
	 */
	public void dealWithFeeOrder(FeeCalculateResultDTO calculateResult) throws FeeBizException, SQLException;
}
