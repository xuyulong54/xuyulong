package wusc.edu.pay.core.remit.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.core.remit.dao.RemitBankTypeDao;
import wusc.edu.pay.facade.remit.entity.RemitBankType;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * @Title: 银行行别信息Dao实现类
 * @Description:
 * @author zzh
 * @date 2014-7-24 下午3:55:31
 */
@Repository("remitBankTypeDao")
public class RemitBankTypeDaoImpl extends BaseDaoImpl<RemitBankType> implements RemitBankTypeDao {

	@Override
	public List<RemitBankType> listActiveBankByIn(List<String> bankCodeList) {
		return super.getSessionTemplate().selectList("listActiveBankByIn", bankCodeList);
	}

	@Override
	public List<RemitBankType> listActiveBankByNotIn(List<String> bankCodeList) {
		return super.getSessionTemplate().selectList("listActiveBankByNotIn", bankCodeList);
	}

	@Override
	public RemitBankType getByTypeCode(String typeCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("typeCode", typeCode);
		paramMap.put("status", 100);
		return super.getSessionTemplate().selectOne(getStatement("getByTypeCode"), paramMap);
	}

	@Override
	public RemitBankType getByBankCode(String bankCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bankCode", bankCode);
		return super.getSessionTemplate().selectOne(getStatement("getByBankCode"), paramMap);
	}

	/**
	 * @Title: 查询所有的银行行别
	 * @Description:
	 * @param @return
	 * @param @throws RemitBizException
	 * @return List<RemitBankType>
	 * @throws
	 */
	public List<RemitBankType> listAll() throws RemitBizException {
		return super.listBy(new HashMap<String, Object>());
	}

	/**
	 * 查询所有有效的行别
	 * 
	 * @return
	 * @throws RemitBizException
	 */
	public List<RemitBankType> listActiveBank() throws RemitBizException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", PublicStatusEnum.ACTIVE.getValue());
		return super.listBy(paramMap);
	}

}
