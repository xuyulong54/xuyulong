package wusc.edu.pay.facade.notify.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.notify.biz.NotifyBiz;
import wusc.edu.pay.facade.notify.entity.NotifyRecord;
import wusc.edu.pay.facade.notify.entity.NotifyRecordLog;
import wusc.edu.pay.facade.notify.service.NotifyFacade;


@Component("notifyFacade")
public class NotifyFacadeImpl implements NotifyFacade {

	@Autowired
	private NotifyBiz notifyBiz;

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
		return notifyBiz.getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(merchantNo, merchantOrderNo, notifyType);
	}

	/**
	 * 查询商户通知记录.<br/>
	 * 
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public NotifyRecord getNotifyById(Long id) {
		return notifyBiz.getNotifyById(id);
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
		return notifyBiz.queryNotifyRecordListPage(pageParam, paramMap);
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
		return notifyBiz.queryNotifyRecordLogListPage(pageParam, paramMap);
	}

	/**
	 * 创建商户通知记录.<br/>
	 */
	public long creatNotifyRecord(NotifyRecord notifyRecord) {
		return notifyBiz.creatNotifyRecord(notifyRecord);
	}

	/**
	 * 更新商户通知记录.<br/>
	 */
	public long updateNotifyRecord(NotifyRecord notifyRecord) {
		return notifyBiz.updateNotifyRecord(notifyRecord);
	}

	/**
	 * 创建商户通知日志记录.<br/>
	 */
	public long creatNotifyRecordLog(NotifyRecordLog notifyRecordLog) {
		return notifyBiz.creatNotifyRecordLog(notifyRecordLog);
	}

}
