package wusc.edu.pay.core.payrule.dao;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.payrule.entity.Frp;


public interface FrpDao extends BaseDao<Frp> {

	/**
	 * 按支付渠道编号查找.
	 * @param merchantNo .
	 * @return BankAgreement .
	 * 
	 */
	Frp getByFrpCode(String frpCode);

	@SuppressWarnings("rawtypes")
	List listAll();

	/***
	 * 根据map查询支付渠道列表
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List listByMap(Map<String, Object> map);
	
	List<Frp> listByPayType(Integer payType);

}
