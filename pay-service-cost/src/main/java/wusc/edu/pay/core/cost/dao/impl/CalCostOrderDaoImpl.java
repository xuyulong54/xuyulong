package wusc.edu.pay.core.cost.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.cost.dao.CalCostOrderDao;
import wusc.edu.pay.facade.cost.entity.CalCostOrder;


@Repository("calCostOrderDao")
public class CalCostOrderDaoImpl extends BaseDaoImpl<CalCostOrder> implements
		CalCostOrderDao {
	
	/**
	 * 根据银行订单号查询
	 * @param bankOrderNo
	 * @return
	 */
	@Override
	public CalCostOrder getBybankOrderNo(String bankOrderNo){
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("bankOrderNo", bankOrderNo);
		return super.getBy(paramMap);
	}

	/**
	 * <pre>
	 * 	根据交易流水号获取成本订单信息
	 * 	* 由于交易流水号在数据库的成本订单表中是唯一的，故只能获取到一个值
	 * </pre>
	 * @param trxno 交易流水号
	 * @return
	 */
	@Override
	public CalCostOrder getByTrxno(String fromSystem, String trxno){
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("fromSystem", fromSystem);
		paramMap.put("trxNo", trxno);
		return super.getBy(paramMap);
	}
	
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
			String orgTrxNo){
		Map<String , Object> paramMap = new HashMap<String , Object>();
		paramMap.put("fromSystem", fromSystem);
		paramMap.put("orgTrxNo", orgTrxNo);
		return super.listBy(paramMap);
	}

}
