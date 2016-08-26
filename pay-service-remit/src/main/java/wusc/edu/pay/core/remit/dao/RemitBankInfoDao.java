package wusc.edu.pay.core.remit.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.remit.entity.RemitBankInfo;


/**
 * @Title: 银行信息DAO接口
 * @Description:
 * @author zzh
 * @date 2014-7-22 下午3:42:11
 */
public interface RemitBankInfoDao extends BaseDao<RemitBankInfo> {
	/**
	 * @Title: 根据银行行别和区域查询银行信息
	 * @Description:
	 * @param @param bankTypeCode
	 * @param @param province
	 * @param @param city
	 * @param @return
	 * @return List<RemitBankInfo>
	 * @throws
	 */
	public List<RemitBankInfo> listByBankTypeCodeAndArea(String bankTypeCode, String province, String city);

	/**
	 * @Title: 根据银行名称模糊查询银行信息
	 * @Description:
	 * @param @param bankName
	 * @param @return
	 * @return List<RemitBankInfo>
	 * @throws
	 */
	public List<RemitBankInfo> listByBankName(String bankName);

	/**
	 * 根据银行名称查找
	 * 
	 * @param bankChannelNo
	 * @return
	 */
	public RemitBankInfo getByBankChannelNo(String bankChannelNo);
}
