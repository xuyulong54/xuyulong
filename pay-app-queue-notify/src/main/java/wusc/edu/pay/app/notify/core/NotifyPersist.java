package wusc.edu.pay.app.notify.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.facade.notify.entity.NotifyRecord;
import wusc.edu.pay.facade.notify.entity.NotifyRecordLog;
import wusc.edu.pay.facade.notify.service.NotifyFacade;


/**
 * 该类提供了写通知表和通知日志表的两个方法
 * 
 * @author 张文斯
 * 
 */
@Component
public class NotifyPersist {

	public static NotifyFacade notifyFacade;

	/**
	 * 创建商户通知记录.<br/>
	 * 
	 * @param notifyRecord
	 * @return
	 */
	public static long saveNotifyRecord(NotifyRecord notifyRecord) {
		return notifyFacade.creatNotifyRecord(notifyRecord);
	}

	/**
	 * 更新商户通知记录.<br/>
	 * 
	 * @param id
	 * @param notifyTimes
	 *            通知次数.<br/>
	 * @param status
	 *            通知状态.<br/>
	 * @return 更新结果
	 */
	public static void updateNotifyRord(Long id, int notifyTimes, int status) {
		NotifyRecord notifyRecord = notifyFacade.getNotifyById(id);
		notifyRecord.setNotifyTimes(notifyTimes);
		notifyRecord.setStatus(status);
		notifyFacade.updateNotifyRecord(notifyRecord);
	}

	/**
	 * 创建商户通知日志记录.<br/>
	 * 
	 * @param notifyId
	 *            通知记录ID.<br/>
	 * @param merchantNo
	 *            商户编号.<br/>
	 * @param merchantOrderNo
	 *            商户订单号.<br/>
	 * @param request
	 *            请求信息.<br/>
	 * @param response
	 *            返回信息.<br/>
	 * @param httpStatus
	 *            通知状态(HTTP状态).<br/>
	 * @return 创建结果
	 */
	public static long saveNotifyRecordLogs(long notifyId, String merchantNo, String merchantOrderNo, String request, String response,
			int httpStatus) {
		NotifyRecordLog notifyRecordLog = new NotifyRecordLog();
		notifyRecordLog.setHttpStatus(httpStatus);
		notifyRecordLog.setMerchantNo(merchantNo);
		notifyRecordLog.setMerchantOrderNo(merchantOrderNo);
		notifyRecordLog.setNotifyId(notifyId);
		notifyRecordLog.setRequest(request);
		notifyRecordLog.setResponse(response);
		return notifyFacade.creatNotifyRecordLog(notifyRecordLog);
	}

	@Autowired
	public void setNotifyFacade(NotifyFacade notifyFacade) {
		NotifyPersist.notifyFacade = notifyFacade;
	}

}
