package wusc.edu.pay.core.payrule.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.payrule.dao.PayWayDao;
import wusc.edu.pay.facade.payrule.entity.PayWay;
import wusc.edu.pay.facade.payrule.entity.vo.FrpSelectVo;
import wusc.edu.pay.facade.payrule.entity.vo.PayWayVo;


/**
 * ClassName: PayWayDao <br/>
 * Function: <br/>
 * date: 2014-6-27 上午9:23:19 <br/>
 * 
 * @author laich
 */
@Repository(value = "payWayDao")
public class PayWayDaoImpl extends BaseDaoImpl<PayWay> implements PayWayDao {

	/**
	 * 根据支付产品编号查找支付方式
	 * 
	 * @param payProductCode
	 * @return
	 */
	public List<PayWay> findPayWayByPayProductCode(String payProductCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("payProductCode", payProductCode);
		return super.listBy(paramMap);
		// return super.getSessionTemplate().selectList("findPayWayByPayProductCode", paramMap);
	}

	/**
	 * 根据支付方式编号查找
	 */
	public PayWay getPayWayByPayWayCode(String payWayCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("payWayCode", payWayCode);
		return super.getBy(paramMap);
	}

	public PayWay getPayWayBypayWayCodeAndpayProductCode(String payWayCode, String payProductCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("payWayCode", payWayCode);
		paramMap.put("payProductCode", payProductCode);
		return super.getBy(paramMap);
	}
	
	public List<FrpSelectVo> findBindPayWayByPayProductCode(String payProductCode) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("payProductCode", payProductCode);
		return super.getSessionTemplate().selectList(getStatement("findBindPayWayByPayProductCode"), paramMap);
	}

	public List<PayWay> findByPayWayCode(String payWayCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("payWayCode", payWayCode);
		return super.listBy(paramMap);
	}

	@Override
	public List<PayWay> listAllPayWay() {
		return super.listBy(new HashMap<String, Object>());
	}

}
