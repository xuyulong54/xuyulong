package wusc.edu.pay.web.boss.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import wusc.edu.pay.common.utils.httpclient.SimpleHttpParam;
import wusc.edu.pay.common.utils.httpclient.SimpleHttpResult;
import wusc.edu.pay.common.utils.httpclient.SimpleHttpUtils;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.notify.entity.NotifyRecord;
import wusc.edu.pay.facade.notify.entity.NotifyRecordLog;
import wusc.edu.pay.facade.notify.enums.NotifyStatusEnum;
import wusc.edu.pay.facade.notify.enums.NotifyTypeEnum;
import wusc.edu.pay.facade.notify.service.NotifyFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;

import com.alibaba.fastjson.JSONObject;

/**
 * 通知记录
 * 
 * @time 2013-8-14,上午9:44:10
 */
@SuppressWarnings("serial")
@Scope("prototype")
public class NotifyRecordAction extends BossBaseAction {

	private static Log log = LogFactory.getLog(NotifyRecordAction.class);

	@Autowired
	private NotifyFacade notifyFacade;
	@Autowired
	private JmsTemplate notifyJmsTemplate; // 商户通知队列模板

	/**
	 * 可按条件查询并分页列出通知记录信息.
	 * 
	 * @return.
	 */
	@Permission("notify:record:view")
	public String listNotifyRecord() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("merchantNo", getString("merchantNo"));
		paramMap.put("merchantOrderNo", getString("merchantOrderNo"));
		String status = getString("status");
		if (StringUtil.isEmpty(status)) {
			status = String.valueOf(NotifyStatusEnum.HTTP_REQUEST_SUCCESS.getValue());
		} else if ("all".equals(status)) {
			status = "";
		}
		paramMap.put("status", status);
		paramMap.put("notifyType", getString("notifyType"));
		// 如果为补单查询
		if (!StringUtil.isEmpty(getString("flag"))) {
			paramMap.put("flag", getString("flag"));
		}
		this.pushData(paramMap);

		super.pageBean = notifyFacade.queryNotifyRecordListPage(this.getPageParam(), paramMap);
		this.pushData(pageBean);

		this.putData("notifyStatusEnumList", NotifyStatusEnum.toList());
		this.putData("notifyStatusEnum", NotifyStatusEnum.toMap());
		this.putData("notifyTypeEnumList", NotifyTypeEnum.toList());

		return "listNotifyRecord";
	}

	/***
	 * @Title: sendNotify
	 * @Description: 补发交易通知
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public String sendNotify() {
		String id = getString("id");
		if (StringUtil.isEmpty(id)) {
			return operateError("通知失败，通知地址为空！");
		}

		try {
			NotifyRecord notifyRecord = notifyFacade.getNotifyById(Long.valueOf(id));
			if (notifyRecord == null) {
				return operateError("通知信息为空，通知失败！");
			}

			// 当前通知的状态
			int currentStatus = notifyRecord.getStatus();

			SimpleHttpParam param = new SimpleHttpParam(notifyRecord.getUrl());
			SimpleHttpResult result = SimpleHttpUtils.httpRequest(param);

			// if(notifyRecord.getNotifyTimes() + 1 >
			// notifyRecord.getLimitNotifyTimes()){
			// return operateError("通知超限，通知失败！");
			// }

			// 响应内容
			// String successValue = result.getContent();

			String responseMsg = "";
			// 响应吗
			Integer responseStatus = result.getStatusCode();

			if (result != null
					&& (responseStatus == 200 || responseStatus == 201 || responseStatus == 202 || responseStatus == 203 || responseStatus == 204 || responseStatus == 205 || responseStatus == 206)) {

				responseMsg = result.getContent().trim();
				responseMsg = responseMsg.length() >= 600 ? responseMsg.substring(0, 600) : responseMsg;
				if (responseMsg.trim().equals("success")) {
					// 通知成功
					notifyRecord.setStatus(NotifyStatusEnum.SUCCESS.getValue()); // 更改通知状态
					notifyRecord.setNotifyTimes(notifyRecord.getNotifyTimes() + 1); // 通知次数加1
					notifyFacade.updateNotifyRecord(notifyRecord);
					super.logSave("从商户交易查询补发通知成功！ 商户编号[" + notifyRecord.getMerchantNo() + "]");
				} else { // 通知失败
					notifyRecord.setNotifyTimes(notifyRecord.getNotifyTimes() + 1);
					if (currentStatus != NotifyStatusEnum.SUCCESS.getValue()) { // 如果当前的通知为成功，则补发即使失败也不改变通知状态；
						notifyRecord.setStatus(NotifyStatusEnum.HTTP_REQUEST_SUCCESS.getValue());
					}
					super.logSave("从商户交易查询补发通知失败 ！ 商户编号[" + notifyRecord.getMerchantNo() + "]");
					notifyFacade.updateNotifyRecord(notifyRecord);
				}
			}
			// 保存通知日志
			NotifyRecordLog notifyRecordLog = new NotifyRecordLog();
			notifyRecordLog.setNotifyId(Long.valueOf(id));
			notifyRecordLog.setMerchantNo(notifyRecord.getMerchantNo());
			notifyRecordLog.setMerchantOrderNo(notifyRecord.getMerchantOrderNo());
			notifyRecordLog.setRequest(notifyRecord.getUrl());
			notifyRecordLog.setResponse(responseMsg);
			notifyRecordLog.setHttpStatus(responseStatus);
			notifyFacade.creatNotifyRecordLog(notifyRecordLog);
		} catch (Exception e) {
			log.error("通知失败！", e);
			return operateError("通知失败！");
		}
		return operateSuccess();
	}

	@Permission("notify:record:view")
	public String listNotifyRecordLog() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("notifyId", getLong("notifyId"));
		super.pageBean = notifyFacade.queryNotifyRecordLogListPage(this.getPageParam(), paramMap);
		this.pushData(pageBean);
		this.putData("notifyStatusEnumList", NotifyStatusEnum.toList());
		return "listNotifyRecordLog";
	}

	/**
	 * 从商户交易查询补发通知 merchantSenderMQ: <br/>
	 * 
	 * @return
	 */
	@Permission("merchant:paymentrecord:sendmq")
	public String merchantSenderMQ() {
		String merchantNo = getString("merchantNo");
		String merchantOrderNo = getString("merchantOrderNo");

		NotifyRecord vo = notifyFacade.getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(merchantNo, merchantOrderNo, NotifyTypeEnum.MERCHANT.getValue());
		if (vo != null) {
			final NotifyRecord notifyRecord = new NotifyRecord();
			notifyRecord.setLimitNotifyTimes(5);
			notifyRecord.setNotifyTimes(0);
			notifyRecord.setUrl(vo.getUrl());
			notifyRecord.setMerchantNo(vo.getMerchantNo());
			notifyRecord.setMerchantOrderNo(vo.getMerchantOrderNo());

			notifyJmsTemplate.send(new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(JSONObject.toJSONString(notifyRecord));
				}
			});

			super.logSave("从商户交易查询补发通知.商户编号[" + vo.getMerchantNo() + "]");
			return operateSuccess("发送消息请求成功");
		} else {
			super.logSaveError("从商户交易查询补发通知.商户ID[null]");
			return operateError("发送消息请求失败!");
		}
	}

	/**
	 * 批量发送消息到消息服务器
	 */
	public String messageSenderMQ() {
		String entityJson = getString("entityJson");
		if (!StringUtil.isEmpty(entityJson)) {
			@SuppressWarnings("unchecked")
			Collection<NotifyRecord> tmp = JSONArray.toCollection(JSONArray.fromObject(entityJson), NotifyRecord.class);
			List<NotifyRecord> notifyRecordList = new ArrayList<NotifyRecord>(tmp);
			for (int i = 0; i < notifyRecordList.size(); i++) {
				try {
					final NotifyRecord notifyRecord = new NotifyRecord();

					NotifyRecord vo = notifyFacade.getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(notifyRecordList.get(i).getMerchantNo(), notifyRecordList.get(i).getMerchantOrderNo(),
							NotifyTypeEnum.MERCHANT.getValue());

					notifyRecord.setLimitNotifyTimes(vo.getLimitNotifyTimes());
					notifyRecord.setNotifyTimes(vo.getNotifyTimes());
					notifyRecord.setUrl(vo.getUrl());
					notifyRecord.setMerchantOrderNo(vo.getMerchantOrderNo());
					notifyRecord.setMerchantNo(vo.getMerchantNo());

					notifyJmsTemplate.send(new MessageCreator() {
						public Message createMessage(Session session) throws JMSException {
							return session.createTextMessage(JSONObject.toJSONString(notifyRecord));
						}
					});

				} catch (Exception e) {
					return operateError("访问消息服务出错");
				}
			}
		}
		return operateSuccess("发送消息请求成功");
	}

}
