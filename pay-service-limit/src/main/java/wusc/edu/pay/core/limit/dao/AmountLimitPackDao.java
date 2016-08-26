package wusc.edu.pay.core.limit.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.limit.entity.AmountLimitPack;


public interface AmountLimitPackDao extends BaseDao<AmountLimitPack>{

	/**
	 * 查询所有的金额限制包
	 * @return
	 */
	List<AmountLimitPack> queryAmountLimitPackAll();
	/**
	 * 根据金额包名称查询
	 */
	AmountLimitPack getAmountLimitPackByName(String name);

}
