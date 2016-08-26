package wusc.edu.pay.core.remit.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.facade.remit.entity.RemitBankType;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * @Title: 银行行别信息Dao接口
 * @Description:
 * @author zzh
 * @date 2014-7-24 下午3:51:09
 */
public interface RemitBankTypeDao extends BaseDao<RemitBankType> {

	/**
	 * 根据in条件查询出有效的行别
	 * 
	 * @param bankCodeList
	 * @return
	 */
	public List<RemitBankType> listActiveBankByIn(List<String> bankCodeList);

	/**
	 * 根据not in条件查询出有效的行别
	 * 
	 * @param bankCodeList
	 * @return
	 */
	public List<RemitBankType> listActiveBankByNotIn(List<String> bankCodeList);

	/**
	 * @Title: 根据银行行别代码获取银行行别信息
	 * @Description:
	 * @param @param typeCode
	 * @param @return
	 * @return RemitBankType
	 * @throws
	 */
	public RemitBankType getByTypeCode(String typeCode);

	/**
	 * @Title: 根据银行编码代码获取银行行别信息
	 * @Description:
	 * @param @param typeCode
	 * @param @return
	 * @return RemitBankType
	 * @throws
	 */
	public RemitBankType getByBankCode(String bankCode);

	/**
	 * @Title: 查询所有的银行行别
	 * @Description:
	 * @param @return
	 * @param @throws RemitBizException
	 * @return List<RemitBankType>
	 * @throws
	 */
	public List<RemitBankType> listAll();

	/**
	 * 查询所有有效的行别
	 * 
	 * @return
	 * @throws RemitBizException
	 */
	public List<RemitBankType> listActiveBank();
}
