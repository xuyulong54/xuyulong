package wusc.edu.pay.facade.cost.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.cost.biz.CalCostOrderBiz;
import wusc.edu.pay.facade.cost.entity.CalCostOrder;
import wusc.edu.pay.facade.cost.entity.CalFeeFlow;
import wusc.edu.pay.facade.cost.enums.CostItemEnum;
import wusc.edu.pay.facade.cost.enums.SystemResourceTypeEnum;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;
import wusc.edu.pay.facade.cost.service.CalCostOrderFacade;


/**
 * 
 * @描述: 成本订单信息，Dubbo服务接口实现类.
 * @作者: huqian .
 * @创建时间: 2014-7-1, 上午11:35:37
 */
@Component("calCostOrderFacade")
public class CalCostOrderFacadeImpl implements CalCostOrderFacade {
	

	@Autowired
	private CalCostOrderBiz calCostOrderBiz;
	
	/**
	 * 根据银行订单号查询
	 * @param bankOrderNo
	 * @return
	 */
	public CalCostOrder getBybankOrderNo(String bankOrderNo){
		return calCostOrderBiz.getBybankOrderNo(bankOrderNo);
	}

	/**
	 * 创建成本订单信息
	 * 
	 * @param entity
	 * @return
	 */
	public long create(CalCostOrder entity) {
		return calCostOrderBiz.create(entity);
	}

	/**
	 * 修改成本订单信息
	 * 
	 * @param entity
	 * @return
	 */
	public long update(CalCostOrder entity) {
		return calCostOrderBiz.update(entity);
	}

	/**
	 * 分页查询成本订单信息
	 * 
	 * @param entity
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return calCostOrderBiz.listPage(pageParam, paramMap);
	}

	/**
	 * 根据ID查询成本订单信息
	 * 
	 * @param entity
	 * @return
	 */
	public CalCostOrder getById(long id) {
		CalCostOrder bankAccount = calCostOrderBiz.getById(id);
		return bankAccount;
	}

	/**
	 * 将计费订单信息和计费流量信息更新到数据库中
	 * 
	 * @param costOrder
	 *            计费订单信息
	 * @param feeFlow
	 *            计费流量信息
	 * @throws CostBizException
	 */
	@Override
	public void create(CalCostOrder costOrder, CalFeeFlow feeFlow)
			throws CostBizException {
		calCostOrderBiz.create(costOrder, feeFlow);
	}

	/**
	 * <pre>
	 * 	根据计费接口和交易金额对成本进行预算
	 * 	【注意事项】
	 * 		1、计费接口不能为空，且必须在TBL_CAL_COST_INTERFACE表中存在
	 * 		2、交易金额必须大于0
	 * 		3、返回的计费订单实体需要通过消息队列传给计费系统
	 * </pre>
	 * 
	 * @param calInterface
	 *            计费接口
	 * @param amount
	 *            交易金额
	 * @param costItemEnum
	 *            计费项
	 * @return 计费订单实体
	 */
	@Override
	public CalCostOrder preCalulateCost(String calInterface, String mcc, BigDecimal amount, CostItemEnum costItemEnum)
			throws CostBizException {
		CalCostOrder order = new CalCostOrder();
		order.setCalInterface(calInterface);
		order.setAmount(amount);
		order.setMcc(mcc);
		return calCostOrderBiz.calulateBankCost(order, costItemEnum, null);
	}

	/**
	 * <pre>
	 * 	根据原交易流水号查询原交易的手续费信息
	 * 	【注意事项】
	 * 		1、系统来源、原交易流水号和计费项不能为空
	 * 		2、系统来源枚举取SystemResourceTypeEnum.ONLINE或SystemResourceTypeEnum.POS
	 * 		3、对于部分退款(退货)，则计费接口和交易金额不能为空
	 * 		4、返回的计费订单实体需要通过消息队列传给计费系统
	 * </pre>
	 * 
	 * @param systemResourceTypeEnum
	 *            系统来源枚举
	 * @param calInterface
	 *            计费接口
	 * @param orialTraceNo
	 *            原交易流水号
	 * @param amount
	 *            交易金额
	 * @param costItemEnum
	 *            计费项
	 * @return
	 */
	@Override
	public CalCostOrder preCalulateCost(
			SystemResourceTypeEnum systemResourceTypeEnum, String calInterface,
			String orialTraceNo, BigDecimal amount, CostItemEnum costItemEnum) throws CostBizException {
		CalCostOrder order = new CalCostOrder();
		order.setCalInterface(calInterface);
		order.setAmount(amount);
		order.setOrgTrxNo(orialTraceNo);
		return calCostOrderBiz.calulateBankCost(order, costItemEnum, systemResourceTypeEnum);
	}

	@Override
	public CalCostOrder getByPayTrxNoAndCostItem(String trxNo, Integer costItem) throws CostBizException {
		if(trxNo==null || costItem==null){
			return null;
		}
		return calCostOrderBiz.getByPayTrxNoAndCostItem(trxNo, costItem);
	}

}
