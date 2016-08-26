package wusc.edu.pay.core.notify.biz;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.notify.dao.NotifyRecordDao;
import wusc.edu.pay.core.notify.dao.NotifyRecordLogDao;
import wusc.edu.pay.facade.notify.entity.NotifyRecord;
import wusc.edu.pay.facade.notify.entity.NotifyRecordLog;


/**
 * 
 * @描述: 商户通知服务业务逻辑实现.
 * @作者: WuShuicheng.
 * @创建: 2014-7-18,下午6:09:11
 * @版本: V1.0
 * 
 */
@Component("notifyBiz")
public class NotifyBiz {

	@Autowired
	private NotifyRecordDao notifyRecordDao;

	@Autowired
	private NotifyRecordLogDao notifyRecordLogDao;

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
	public NotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String trxNo, String merchantNo, Integer notifyType) {
		return notifyRecordDao.getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(trxNo, merchantNo, notifyType);
	}

	/**
	 * 查询商户通知记录.<br/>
	 * 
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public NotifyRecord getNotifyById(Long id) {
		return notifyRecordDao.getById(id);
	}

	/**
	 * 根据传入参数分页查询
	 * 
	 * @param pageParam
	 *            分页参数
	 * @param paramMap
	 *            查询参数
	 * @return 查询结果
	 */
	public PageBean queryNotifyRecordListPage(PageParam pageParam, Map<String, Object> paramMap) {
		return notifyRecordDao.listPage(pageParam, paramMap);
	}

	/**
	 * 根据传入参数分页查询
	 * 
	 * @param pageParam
	 *            分页参数
	 * @param paramMap
	 *            查询参数
	 * @return 查询结果
	 */
	public PageBean queryNotifyRecordLogListPage(PageParam pageParam, Map<String, Object> paramMap) {
		return notifyRecordLogDao.listPage(pageParam, paramMap);
	}

	/**
	 * 创建商户通知记录.<br/>
	 * 
	 * @param notifyRecord
	 * @return
	 */
	public long creatNotifyRecord(NotifyRecord notifyRecord) {
		return notifyRecordDao.insert(notifyRecord);
	}

	/**
	 * 更新商户通知记录.<br/>
	 * 
	 * @param notifyRecord
	 * @return
	 */
	public long updateNotifyRecord(NotifyRecord notifyRecord) {
		return notifyRecordDao.update(notifyRecord);
	}

	/**
	 * 创建商户通知日志记录.<br/>
	 * 
	 * @param notifyRecordLog
	 * @return
	 */
	public long creatNotifyRecordLog(NotifyRecordLog notifyRecordLog) {
		return notifyRecordLogDao.insert(notifyRecordLog);
	}

}
