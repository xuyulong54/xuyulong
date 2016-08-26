package wusc.edu.pay.core.cost.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.cost.entity.CalCostOrder;


/**
 * 计费成本订单DAO
 */
public interface CalCostOrderDao extends BaseDao<CalCostOrder> {
	
	/**
	 * 根据银行订单号查询
	 * @param bankOrderNo
	 * @return
	 */
	public CalCostOrder getBybankOrderNo(String bankOrderNo);

	/**
	 * 根据系统来源和交易流水号获取成本订单信息 * 由于（系统来源 + 交易流水号）在数据库的成本订单表中是唯一的，故只能获取到一个值 </pre>
	 * 
	 * @param fromSystem
	 *            系统来源
	 * @param trxno
	 *            交易流水号
	 * @return
	 */
	public CalCostOrder getByTrxno(String fromSystem, String trxno);

	/**
	 * 根据系统来源和原交易流水号，获取订单信息
	 * 
	 * @param fromSystem
	 *            系统来源
	 * @param trxno
	 *            交易流水号
	 * @return
	 */
	public List<CalCostOrder> listByOrgTrxNo(String fromSystem,
			String orgTrxNo);

}
