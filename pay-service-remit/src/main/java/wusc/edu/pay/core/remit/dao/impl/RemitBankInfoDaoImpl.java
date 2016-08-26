package wusc.edu.pay.core.remit.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.remit.dao.RemitBankInfoDao;
import wusc.edu.pay.facade.remit.entity.RemitBankInfo;


/**
 * @Title: 银行信息DAO接口实现类
 * @Description:
 * @author zzh
 * @date 2014-7-22 下午3:43:51
 */
@Repository("remitBankInfoDao")
public class RemitBankInfoDaoImpl extends BaseDaoImpl<RemitBankInfo> implements RemitBankInfoDao {

	@Override
	public List<RemitBankInfo> listByBankTypeCodeAndArea(String bankTypeCode, String province, String city) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bankTypeCode", bankTypeCode);
		paramMap.put("province", province);
		paramMap.put("city", city);
		return getSessionTemplate().selectList(getStatement("listByBankTypeCodeAndArea"), paramMap);
	}

	@Override
	public List<RemitBankInfo> listByBankName(String bankName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bankName", bankName);
		return getSessionTemplate().selectList(getStatement("listByBankName"), paramMap);
	}

	public RemitBankInfo getByBankChannelNo(String bankChannelNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bankChannelNo", bankChannelNo);
		return super.getBy(paramMap);
	}
}
