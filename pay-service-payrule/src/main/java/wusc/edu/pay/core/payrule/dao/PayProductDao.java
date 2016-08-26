package wusc.edu.pay.core.payrule.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.payrule.entity.PayProduct;

/**
 * ClassName: PayProductDao <br/>
 * Function:  <br/>
 * date: 2014-6-27 上午9:13:01 <br/>
 * 
 * @author laich
 */
public interface PayProductDao extends BaseDao<PayProduct>{

	PayProduct findByProductCode(String payProductCode);

	/***
	 * 查询所有有效的支付产品列表
	 * @return
	 */
	List<PayProduct> listAllProduct();

}
