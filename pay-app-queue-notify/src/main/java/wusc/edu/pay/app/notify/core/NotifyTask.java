package wusc.edu.pay.app.notify.core;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.app.notify.entity.NotifyParam;
import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.utils.httpclient.SimpleHttpParam;
import wusc.edu.pay.common.utils.httpclient.SimpleHttpResult;
import wusc.edu.pay.common.utils.httpclient.SimpleHttpUtils;
import wusc.edu.pay.facade.notify.entity.NotifyRecord;
import wusc.edu.pay.facade.notify.enums.NotifyStatusEnum;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSONObject;

/**
 * 执行通知的类，以后有业务时添加业务代码即可
 * 
 * @author ZhangWensi
 * 
 */
public class NotifyTask implements Runnable, Delayed {

	private static final Log LOG = LogFactory.getLog(NotifyTask.class);

	private long executeTime;

	private NotifyRecord notifyRecord;

	private NotifyQueue notifyQueue;

	private NotifyParam notifyParam;

	public NotifyTask() {
	}

	public NotifyTask(NotifyRecord notifyRecord, NotifyQueue notifyQueue, NotifyParam notifyParam) {
		super();
		this.notifyRecord = notifyRecord;
		this.notifyQueue = notifyQueue;
		this.notifyParam = notifyParam;
		this.executeTime = getExecuteTime(notifyRecord);
	}

	private long getExecuteTime(NotifyRecord record) {
		long lastTime = record.getLastNotifyTime().getTime();
		Integer nextNotifyTime = notifyParam.getNotifyParams().get(record.getNotifyTimes());
		return (nextNotifyTime == null ? 0 : nextNotifyTime * 1000) + lastTime;
	}

	public int compareTo(Delayed o) {
		NotifyTask task = (NotifyTask) o;
		return executeTime > task.executeTime ? 1 : (executeTime < task.executeTime ? -1 : 0);
	}

	public long getDelay(TimeUnit unit) {
		return unit.convert(executeTime - System.currentTimeMillis(), unit.SECONDS);
	}

	public void run() {
		// 得到当前通知对象的通知次数
		Integer notifyTimes = notifyRecord.getNotifyTimes();
		// 去通知
		try {
			LOG.info("Notify Url " + notifyRecord.getUrl());

			/** 采用 httpClient */
			SimpleHttpParam param = new SimpleHttpParam(notifyRecord.getUrl());
			SimpleHttpResult result = SimpleHttpUtils.httpRequest(param);

			/*
			 * OkHttpClient client = new OkHttpClient(); Request request = new
			 * Request.Builder().url(notifyRecord.getUrl()).build(); Response
			 * response = client.newCall(request).execute();
			 */

			notifyRecord.setNotifyTimes(notifyTimes + 1);
			String successValue = notifyParam.getSuccessValue();

			String responseMsg = "";
			Integer responseStatus = result.getStatusCode();

			// 得到返回状态，如果是200，也就是通知成功
			if (result != null
					&& (responseStatus == 200 || responseStatus == 201 || responseStatus == 202 || responseStatus == 203
							|| responseStatus == 204 || responseStatus == 205 || responseStatus == 206)) {
				responseMsg = result.getContent().trim();
				responseMsg = responseMsg.length() >= 600 ? responseMsg.substring(0, 600) : responseMsg;
				LOG.info("订单号： " + notifyRecord.getMerchantOrderNo() + " HTTP_STATUS：" + responseStatus + "请求返回信息：" + responseMsg);
				// 通知成功
				if (responseMsg.trim().equals(successValue)) {
					NotifyPersist
							.updateNotifyRord(notifyRecord.getId(), notifyRecord.getNotifyTimes(), NotifyStatusEnum.SUCCESS.getValue());
				} else {
					notifyQueue.addElementToList(notifyRecord);
					NotifyPersist.updateNotifyRord(notifyRecord.getId(), notifyRecord.getNotifyTimes(),
							NotifyStatusEnum.HTTP_REQUEST_SUCCESS.getValue());

				}
				LOG.info("Update NotifyRecord:" + JSONObject.toJSONString(notifyRecord));
			} else {
				notifyQueue.addElementToList(notifyRecord);
				// 再次放到通知列表中，由添加程序判断是否已经通知完毕或者通知失败
				NotifyPersist.updateNotifyRord(notifyRecord.getId(), notifyRecord.getNotifyTimes(),
						NotifyStatusEnum.HTTP_REQUEST_FALIED.getValue());
			}

			// 写通知日志表
			NotifyPersist.saveNotifyRecordLogs(notifyRecord.getId(), notifyRecord.getMerchantNo(), notifyRecord.getMerchantOrderNo(),
					notifyRecord.getUrl(), responseMsg, responseStatus);
			LOG.info("Insert NotifyRecordLog, merchantNo:" + notifyRecord.getMerchantNo() + ",merchantOrderNo:"
					+ notifyRecord.getMerchantOrderNo());
		} catch (RpcException e) {
			LOG.error("NotifyTask", e);
			notifyQueue.addElementToList(notifyRecord);
		} catch (BizException e) {
			LOG.error("NotifyTask", e);
		} catch (Exception e) {
			LOG.error("NotifyTask", e);
			notifyQueue.addElementToList(notifyRecord);

			NotifyPersist.updateNotifyRord(notifyRecord.getId(), notifyRecord.getNotifyTimes(),
					NotifyStatusEnum.HTTP_REQUEST_FALIED.getValue());
			NotifyPersist.saveNotifyRecordLogs(notifyRecord.getId(), notifyRecord.getMerchantNo(), notifyRecord.getMerchantOrderNo(),
					notifyRecord.getUrl(), "", 0);
		}

	}
}
