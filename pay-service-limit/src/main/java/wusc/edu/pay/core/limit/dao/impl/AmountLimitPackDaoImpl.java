package wusc.edu.pay.core.limit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.limit.dao.AmountLimitPackDao;
import wusc.edu.pay.facade.limit.entity.AmountLimitPack;


@Repository(value="amountLimitPackDao")
public class AmountLimitPackDaoImpl extends BaseDaoImpl<AmountLimitPack> implements AmountLimitPackDao{

	/**
	 * 查询所有的金额限制包
	 */
	@Override
	public List<AmountLimitPack> queryAmountLimitPackAll() {
		return super.getSqlSession().selectList(this.getStatement("listBy"));
	}
	/**
	 * 根据金额包名称查询
	 */
	@Override
	public AmountLimitPack getAmountLimitPackByName(String name) {
		return super.getSqlSession().selectOne(this.getStatement("getAmountLimitPackByName"),name);
	}

}
