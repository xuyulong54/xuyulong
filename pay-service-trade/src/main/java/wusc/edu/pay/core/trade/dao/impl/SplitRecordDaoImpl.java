package wusc.edu.pay.core.trade.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.trade.dao.SplitRecordDao;
import wusc.edu.pay.facade.trade.entity.SplitRecord;


/**
 * 
 * @描述: 分账记录表数据访问层接口实现类 .
 * @作者: huqian .
 * @创建时间: 2014-9-23,上午9:54:53 .
 * @版本: 1.0 .
 */
@Repository("splitRecordDao")
public class SplitRecordDaoImpl extends BaseDaoImpl<SplitRecord> implements SplitRecordDao {

	/**
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @param splitLoginName
	 * @param splitStatus
	 * @return
	 */
	public List<SplitRecord> listBy_merchantNo_merchantOrderNo_splitLoginName_splitStatus(String merchantNo, String merchantOrderNo,
			String splitLoginName, Integer splitStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantNo", merchantNo);
		params.put("merchantOrderNo", merchantOrderNo);
		params.put("splitLoginName", splitLoginName);
		params.put("splitStatus", splitStatus);
		return super.listBy(params);
	}

	/**
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @param splitLoginName
	 * @param splitStatus
	 * @param splitAmount
	 * @return
	 */
	public SplitRecord getBy_merchantNo_merchantOrderNo_splitLoginName_splitStatus_splitAmount(String merchantNo, String merchantOrderNo,
			String splitLoginName, Integer splitStatus, BigDecimal splitAmount) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantNo", merchantNo);
		params.put("merchantOrderNo", merchantOrderNo);
		params.put("splitLoginName", splitLoginName);
		params.put("splitStatus", splitStatus);
		params.put("splitAmount", splitAmount);
		return super.getBy(params);
	}

	/**
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @param splitMerchantNo
	 * @param splitStatus
	 * @return
	 */
	public List<SplitRecord> listBy_merchantNo_merchantOrderNo_splitMerchantNo_splitStatus(String merchantNo, String merchantOrderNo,
			String splitMerchantNo, Integer splitStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantNo", merchantNo);
		params.put("merchantOrderNo", merchantOrderNo);
		params.put("splitMerchantNo", splitMerchantNo);
		params.put("splitStatus", splitStatus);
		return super.listBy(params);
	}

	/**
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @param splitMerchantNo
	 * @param splitStatus
	 * @param splitAmount
	 * @return
	 */
	public SplitRecord getBy_merchantNo_merchantOrderNo_splitMerchantNo_splitStatus_splitAmount(String merchantNo, String merchantOrderNo,
			String splitMerchantNo, Integer splitStatus, BigDecimal splitAmount) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantNo", merchantNo);
		params.put("merchantOrderNo", merchantOrderNo);
		params.put("splitMerchantNo", splitMerchantNo);
		params.put("splitStatus", splitStatus);
		params.put("splitAmount", splitAmount);
		return super.getBy(params);
	}
}