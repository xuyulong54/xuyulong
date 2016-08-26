package wusc.edu.pay.core.payrule.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.payrule.dao.PayProductDao;
import wusc.edu.pay.facade.payrule.entity.PayProduct;
import wusc.edu.pay.facade.payrule.enums.PayProductStatusEnum;

/**
 * ClassName: PayProductDaoImpl <br/>
 * Function:  <br/>
 * date: 2014-6-27 上午9:22:20 <br/>
 * 
 * @author laich
 */
@Repository(value="payProductDao")
public class PayProductDaoImpl extends BaseDaoImpl<PayProduct> implements PayProductDao {

	public PayProduct findByProductCode(String payProductCode) {
		Map<String ,Object> paramMap  = new HashMap<String ,Object> ();
		paramMap.put("payProductCode", payProductCode);
		return super.getBy(paramMap);
	}

	/***
	 * 查询所有有效的支付产品列表
	 * @return
	 */
	public List<PayProduct> listAllProduct() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", PayProductStatusEnum.ACTIVITY.getValue());
		return super.listBy(map);
	}

}
