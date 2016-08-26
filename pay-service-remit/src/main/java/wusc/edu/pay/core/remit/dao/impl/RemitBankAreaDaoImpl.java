package wusc.edu.pay.core.remit.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.remit.dao.RemitBankAreaDao;
import wusc.edu.pay.facade.remit.entity.RemitBankArea;


/**
 * @Title: 银行地区信息Dao实现类
 * @Description: 
 * @author zzh
 * @date 2014-7-24 下午3:48:51
 */
@Repository("remitBankAreaDao")
public class RemitBankAreaDaoImpl extends BaseDaoImpl<RemitBankArea> implements RemitBankAreaDao {

	@Override
	public List<Map<String, Object>> getProvince() {
		return getSessionTemplate().selectList(getStatement("getProvince"));
	}

	@Override
	public List<Map<String, Object>> getCityByProvince(String province) {
		return getSessionTemplate().selectList(getStatement("getCityByProvince"),province);
	}

	@Override
	public List<RemitBankArea> listByAreaCode(String areaCode) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("areaCode", areaCode);
		return getSessionTemplate().selectList(getStatement("listByAreaCode"),paramMap);
	}

}
