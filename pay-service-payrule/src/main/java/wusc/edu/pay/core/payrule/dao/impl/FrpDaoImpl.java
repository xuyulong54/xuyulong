package wusc.edu.pay.core.payrule.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.payrule.dao.FrpDao;
import wusc.edu.pay.facade.payrule.entity.Frp;


@Repository("frpDao")
public class FrpDaoImpl extends BaseDaoImpl<Frp> implements FrpDao {
	/**
	 * 按支付渠道编号查找
	 */
	public Frp getByFrpCode(String frpCode) {
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("frpCode", frpCode);
		return super.getBy(paramMap);
		//return super.getSessionTemplate().selectOne(getStatement("findByFrpCode"), frpCode);
	}

	@SuppressWarnings("rawtypes")
	public List listAll() {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("status", 100);
		return super.listBy(map);
	}


	/***
	 * 根据map查询支付渠道列表
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List listByMap(Map<String, Object> map) {
		return super.listBy(map);
	}

	public List<Frp> listByPayType(Integer payType) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("payType", payType);
		return super.listBy(map);
	}

	
}
