package wusc.edu.pay.facade.notify.service;

import java.util.Map;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.notify.entity.NotifyRecord;
import wusc.edu.pay.facade.notify.entity.NotifyRecordLog;


/**
 * 
 * @描述: 通知服务接口.
 * @作者: WuShuicheng.
 * @创建: 2014-6-5,上午11:54:29
 * @版本: V1.0
 * 
 */
public interface NotifyFacade {

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
	public NotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo, String merchantOrderNo, Integer notifyType) throws BizException;

	/**
	 * 查询商户通知记录.<br/>
	 * 
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public NotifyRecord getNotifyById(Long id) throws BizException;

	/**
	 * 根据传入参数分页查询
	 * 
	 * @param pageParam
	 *            分页参数
	 * @param paramMap
	 *            查询参数
	 * @return 查询结果
	 */
	public PageBean queryNotifyRecordListPage(PageParam pageParam, Map<String, Object> paramMap) throws BizException;

	/**
	 * 根据传入参数分页查询
	 * 
	 * @param pageParam
	 *            分页参数
	 * @param paramMap
	 *            查询参数
	 * @return 查询结果
	 */
	public PageBean queryNotifyRecordLogListPage(PageParam pageParam, Map<String, Object> paramMap) throws BizException;

	/**
	 * 创建商户通知记录.<br/>
	 * 
	 * @param notifyRecord
	 * @return
	 * @throws BizException
	 */
	public long creatNotifyRecord(NotifyRecord notifyRecord) throws BizException;

	/**
	 * 更新商户通知记录.<br/>
	 * 
	 * @param notifyRecord
	 * @return
	 * @throws BizException
	 */
	public long updateNotifyRecord(NotifyRecord notifyRecord) throws BizException;

	/**
	 * 创建商户通知日志记录.<br/>
	 * 
	 * @param notifyRecordLog
	 * @return
	 * @throws BizException
	 */
	public long creatNotifyRecordLog(NotifyRecordLog notifyRecordLog) throws BizException;
}
