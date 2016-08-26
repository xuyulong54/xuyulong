package wusc.edu.pay.core.trade.dao;

import java.math.BigDecimal;
import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.trade.entity.SplitRecord;


/**
 * 
 * @描述: 分账记录表数据访问层接口.
 * @作者: huqian .
 * @创建时间: 2014-9-12,上午9:55:31 .
 * @版本: 1.0 .
 */
public interface SplitRecordDao extends BaseDao<SplitRecord> {

	/**
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @param splitLoginName
	 * @param splitStatus
	 * @return
	 */
	List<SplitRecord> listBy_merchantNo_merchantOrderNo_splitLoginName_splitStatus(String merchantNo, String merchantOrderNo,
			String splitLoginName, Integer splitStatus);

	/**
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @param splitMerchantNo
	 * @param splitStatus
	 * @return
	 */
	List<SplitRecord> listBy_merchantNo_merchantOrderNo_splitMerchantNo_splitStatus(String merchantNo, String merchantOrderNo,
			String splitMerchantNo, Integer splitStatus);

	/**
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @param splitLoginName
	 * @param splitStatus
	 * @param splitAmount
	 * @return
	 */
	SplitRecord getBy_merchantNo_merchantOrderNo_splitLoginName_splitStatus_splitAmount(String merchantNo, String merchantOrderNo,
			String splitLoginName, Integer splitStatus, BigDecimal splitAmount);

	/**
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @param splitMerchantNo
	 * @param splitStatus
	 * @param splitAmount
	 * @return
	 */
	SplitRecord getBy_merchantNo_merchantOrderNo_splitMerchantNo_splitStatus_splitAmount(String merchantNo, String merchantOrderNo,
			String splitMerchantNo, Integer splitStatus, BigDecimal splitAmount);
}
