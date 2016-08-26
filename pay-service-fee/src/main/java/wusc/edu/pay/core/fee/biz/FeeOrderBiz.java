package wusc.edu.pay.core.fee.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.core.fee.dao.FeeOrderDao;
import wusc.edu.pay.facade.fee.dto.FeeCalculateResultDTO;
import wusc.edu.pay.facade.fee.dto.FeeRuleDTO;
import wusc.edu.pay.facade.fee.entity.FeeOrder;
import wusc.edu.pay.facade.fee.enums.FeeOrderStatusEnum;
import wusc.edu.pay.facade.fee.enums.FeeOrderTypeEnum;
import wusc.edu.pay.facade.fee.utils.FeeUtils;
import wusc.edu.pay.facade.fee.vo.FeeDimensionVO;


/**
 * 计费订单biz定义
 * 
 */
@Component("feeOrderBiz")
public class FeeOrderBiz {

	@Autowired
	private FeeOrderDao feeOrderDao;

	/**
	 * 计费订单分页
	 * 
	 * @param pageParam
	 * @param map
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> map) {
		return feeOrderDao.listPage(pageParam, map);
	}

	/**
	 * 根据计费订单编号查询订单详情
	 * 
	 * @param id
	 * @return
	 */
	public FeeOrder getById(Long id) {
		return feeOrderDao.getById(id);
	}

	/**
	 * 创建计费订单
	 * @param userNo
	 * @param userType
	 * @param dimension
	 * @param feeRule
	 * @param result
	 * @param amount
	 * @param transferDate
	 * @param rateBearerFilter  分摊比例
	 * @param merchantName
	 * @param orderNo
	 * @param trxNo
	 * @return
	 */
	public FeeOrder createFeeOrder(String userNo, Integer userType, FeeDimensionVO dimension, FeeRuleDTO feeRule, FeeCalculateResultDTO result, Double amount, Date transferDate, String merchantName, String orderNo, String trxNo) {
		FeeOrder order = new FeeOrder();
		order.setAmount(amount);// 计费金额
		order.setBankChannelCode(dimension.getBankChannelCode());// 支付接口
		order.setCalculateDate(transferDate);// 计费时间
		order.setCalculateFeeItem(dimension.getCalculateFeeItem());// 计费项
		order.setCalculateType(feeRule.getCalculateType());// 计费类型
		order.setCalculateWayId(feeRule.getId());// 计费方式id
		order.setChargePeriodic(feeRule.getLadderCycleType());// 阶梯周期类型
		order.setChargeType(feeRule.getChargeType());// 收费方式
		order.setFeeBase(result.getFeeBase());// 费率
		order.setFeeRole(feeRule.getFeeRole());// 计费角色
		order.setFrpCode(dimension.getFrpCode());// 支付方式
		order.setMerchantName(merchantName);// 商户名称
		order.setMerchantNo(userNo);// 商户编号
		order.setMerchantOrderNo(orderNo);// 商户订单号
		order.setOlFrpCode("");// 原支付方式
		order.setOlPayProduct("");// 原支付产品
		order.setOrderType(FeeOrderTypeEnum.TRADE.getValue());// 订单类型 ==正向交易
		order.setParentFlowNo("");// 父交易流水号

		order.setPayProduct(dimension.getPayProduct());// 支付产品
		order.setRemark("");// 备注
		order.setTrxDate(transferDate);// 交易时间
		order.setTrxFlowNo(trxNo);// 交易流水号
		order.setUserType(userType);// 用户类型
		order.setPayAllFee(result.getPayFee());// 总的手续费

		// 预算
		FeeUtils.distributionFee(feeRule.getFeeRole(), result);
		order.setPayeeFee(result.getPayeeFee());// 收款方应承担手续费
		order.setPayeeUnBackFee(AmountUtil.sub(amount, result.getPayeeFee()));// 收款方应收金额
																				// (应该收取的钱)
		order.setPayerFee(result.getPayerFee());// 付款方应承担手续费
		// 付款方应付金额(手续费做了四舍五入)
		order.setPayerUnBackFee(AmountUtil.add(amount, result.getPayerFee()));

		order.setConfirmDate(null);// 实收时间
		order.setStatus(FeeOrderStatusEnum.TAX_CALCULATE.getValue());//  已计费
		
		return order;

	}

	/**
	 * 修改计费订单
	 * 
	 * @param feeOrder
	 */
	public void updateFeeOrder(FeeOrder feeOrder) {
		feeOrderDao.update(feeOrder);
	}

	/**
	 * 根据 trxNo查询计费订单
	 * 
	 * @param oldTrxNo
	 */
	public FeeOrder getFeeOrderByTrxNo(String oldTrxNo) {
		if (oldTrxNo == null || "".equals(oldTrxNo)) {
			return null;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("trxFlowNo", oldTrxNo);
		return feeOrderDao.getBy(paramMap);
	}

	/**
	 * 创建计费订单
	 * @param order
	 */
	public void createFeeOrder(FeeOrder order) {
		feeOrderDao.insert(order);		
	}

	/**
	 * 删除计费订单（结算特有，只有结算业务可以调用）
	 * @param trxFlowNo 流水号
	 */
	public void deleteFeeOrder(String trxFlowNo) {
		if (trxFlowNo == null || "".equals(trxFlowNo)) {
			return;
		}
			feeOrderDao.deleteByTrxFlowNoLike(trxFlowNo);
		
	}

}
