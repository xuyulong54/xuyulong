package wusc.edu.pay.core.notify.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.notify.dao.NotifyRecordDao;
import wusc.edu.pay.facade.notify.entity.NotifyRecord;


/**
 * 
 * @描述: 商户通知记录表数据访问层接口实现类 .
 * @作者: WuShuicheng.
 * @创建: 2014-7-18,下午5:47:37
 * @版本: V1.0
 *
 */
@Repository("notifyRecordDao")
public class NotifyRecordDaoIpml extends BaseDaoImpl<NotifyRecord> implements NotifyRecordDao {

	/**
	 * 查询商户通知记录.<br/>
	 * 
	 * @param merchantNo
	 *            商户编号.
	 * @param merchantOrderNo
	 *            商户订单号.
	 * @return notifyType.
	 *            通知类型
	 */
	public NotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo, String merchantOrderNo, Integer notifyType) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantNo", merchantNo);
		params.put("merchantOrderNo", merchantOrderNo);
		params.put("notifyType", notifyType);
		
		return super.getBy(params);
	}
}
