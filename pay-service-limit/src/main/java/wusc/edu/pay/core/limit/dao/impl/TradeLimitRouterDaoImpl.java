/**
 * wusc.edu.pay.core.limit.dao.impl.TradeLimitRouterDaoImpl.java
 */
package wusc.edu.pay.core.limit.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.limit.dao.TradeLimitRouterDao;
import wusc.edu.pay.facade.limit.entity.TradeLimitRouter;


/**
 * 
 * <ul>
 * <li>Title:商户关联开关限制和额度限制的中间关联表dao实现</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-9
 */
@Repository("tradeLimitRouterDao")
public class TradeLimitRouterDaoImpl extends BaseDaoImpl<TradeLimitRouter> implements TradeLimitRouterDao {

	@Override
	public TradeLimitRouter getTradeLimitRouter(String merchantNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantNo", merchantNo);
		return super.getSqlSession().selectOne(this.getStatement("listBy"), params);
	}

}
