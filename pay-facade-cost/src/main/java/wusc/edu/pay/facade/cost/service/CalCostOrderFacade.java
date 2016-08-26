package wusc.edu.pay.facade.cost.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.cost.entity.CalCostOrder;
import wusc.edu.pay.facade.cost.entity.CalFeeFlow;
import wusc.edu.pay.facade.cost.enums.CostItemEnum;
import wusc.edu.pay.facade.cost.enums.SystemResourceTypeEnum;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;


/**
 * 
 * @描述: 成本订单，Dubbo服务接口.
 * @作者: huqian .
 * @创建时间: 2014-7-1, 上午10:56:29
 */
public interface CalCostOrderFacade {
	
	/**
	 * 根据银行订单号查询
	 * @param bankOrderNo
	 * @return
	 */
	public CalCostOrder getBybankOrderNo(String bankOrderNo);

	/**
	 * 创建成本订单信息
	 * 
	 * @param entity
	 * @return
	 */
	public long create(CalCostOrder entity) throws CostBizException;

	/**
	 * 修改成本订单信息
	 * 
	 * @param entity
	 * @return
	 */
	public long update(CalCostOrder entity) throws CostBizException;

	/**
	 * 分页查询成本订单信息
	 * 
	 * @param pageParam
	 *            分页实体对象
	 * @param paramMap
	 *            查询条件
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap)
			throws CostBizException;

	/**
	 * 根据ID查找成本订单信息
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public CalCostOrder getById(long id) throws CostBizException;

	/**
	 * <pre>
	 * 	根据计费接口和交易金额对成本进行预算
	 * 	【注意事项】
	 * 		1、计费接口不能为空，且必须在TBL_CAL_COST_INTERFACE表中存在
	 * 		2、交易金额必须大于0
	 * 		3、计费项不能为空
	 * 		4、返回的计费订单实体需要通过消息队列传给计费系统
	 * </pre>
	 * 
	 * @param calInterface
	 *            计费接口
	 * @param mcc
	 *            POS交易使用MCC
	 * @param amount
	 *            交易金额
	 * @param costItemEnum
	 *            计费项
	 * @return 计费订单实体
	 */
	public CalCostOrder preCalulateCost(String calInterface, String mcc, BigDecimal amount, CostItemEnum costItemEnum)
			throws CostBizException;

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
	public CalCostOrder preCalulateCost(
			SystemResourceTypeEnum systemResourceTypeEnum, String calInterface,
			String orialTraceNo, BigDecimal amount, CostItemEnum costItemEnum) throws CostBizException;

	/**
	 * 	<pre>
	 * 	将计费订单信息和计费流量信息更新到数据库中
	 * 	【注意事项】
	 * 	在保存信息之前，必须要保证，订单信息中存在以下信息：
	 * 		【必填信息】
	 * 			银行计费接口、交易金额、系统来源(取枚举SystemResourceTypeEnum)、交易流水号、交易类型(取枚举CostItemEnum)
	 * 		【选填信息】
	 * 			商户编号、商户名称、商户订单号、银行订单号、交易时间、MCC、原支付流水号
	 * 	对于撤销、退款等带有原交易的订单，必须上送原支付流水号
	 * 	</pre>
	 * @param costOrder
	 *            计费订单信息
	 * @param feeFlow
	 *            计费流量信息
	 * @throws CostBizException
	 */
	public void create(CalCostOrder costOrder, CalFeeFlow feeFlow)
			throws CostBizException, SQLException;

	/**
	 * 查询成本订单
	 * @param trxNo 支付流水号
	 * @param costItem 计费项
	 * @return
	 * @throws CostBizException
	 */
	public CalCostOrder getByPayTrxNoAndCostItem(String trxNo, Integer costItem) throws CostBizException;
	
}
