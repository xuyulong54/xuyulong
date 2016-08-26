/**
 * wusc.edu.pay.core.limit.dao.TradeLimitRouterDao.java
 */
package wusc.edu.pay.core.limit.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.limit.entity.TradeLimitRouter;


/**
 * 
 * <ul>
 * <li>Title:商户关联开关限制和额度限制的中间关联表dao</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-9
 */
public interface TradeLimitRouterDao extends BaseDao<TradeLimitRouter>{
	
	/**
	 * 根据商户号查询商户交易限制路由
	 * 
	 * @param merchantNo
	 *            商户号
	 * @return 交易限制路由实体
	 */
	public TradeLimitRouter getTradeLimitRouter(String merchantNo);

}
