package wusc.edu.pay.core.payrule.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.payrule.entity.PayWay;
import wusc.edu.pay.facade.payrule.entity.vo.FrpSelectVo;
import wusc.edu.pay.facade.payrule.entity.vo.PayWayVo;


public interface PayWayDao extends BaseDao<PayWay>{
	
	/**
	 * 根据支付产品编号查找支付方式
	 * @param payProductCode
	 * @return
	 */
	List<PayWay> findPayWayByPayProductCode(String payProductCode);

	PayWay getPayWayByPayWayCode(String payWayCode);

	PayWay getPayWayBypayWayCodeAndpayProductCode(String payWayCode, String payProductCode);
	
	List<FrpSelectVo> findBindPayWayByPayProductCode(String payProductCode);

	List<PayWay> findByPayWayCode(String payWayCode);

	List<PayWay> listAllPayWay();

}
