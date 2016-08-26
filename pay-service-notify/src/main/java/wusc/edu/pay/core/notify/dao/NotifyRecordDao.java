package wusc.edu.pay.core.notify.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.notify.entity.NotifyRecord;


/**
 * 
 * @描述: 商户通知记录表数据访问层接口.
 * @作者: WuShuicheng.
 * @创建: 2014-7-18,下午5:46:09
 * @版本: V1.0
 *
 */
public interface NotifyRecordDao extends BaseDao<NotifyRecord> {

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
	NotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo, String merchantOrderNo, Integer notifyType);
}
