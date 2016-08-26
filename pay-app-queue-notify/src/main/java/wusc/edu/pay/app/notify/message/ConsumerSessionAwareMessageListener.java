package wusc.edu.pay.app.notify.message;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import wusc.edu.pay.app.notify.core.NotifyPersist;
import wusc.edu.pay.app.notify.core.NotifyQueue;
import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.facade.notify.entity.NotifyRecord;
import wusc.edu.pay.facade.notify.enums.NotifyStatusEnum;
import wusc.edu.pay.facade.notify.service.NotifyFacade;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @描述: 通知队列监听器.
 * @作者: HuPitao,WuShuicheng.
 * @创建: 2014-5-8,下午3:58:28
 * @版本: V1.0
 * 
 */
@Component
public class ConsumerSessionAwareMessageListener implements SessionAwareMessageListener<Message> {

	private static final Log log = LogFactory.getLog(ConsumerSessionAwareMessageListener.class);

	@Autowired
	private JmsTemplate notifyJmsTemplate;
	@Autowired
	private Destination sessionAwareQueue;

	@Autowired
	private NotifyQueue notifyQueue;
	@Autowired
	private NotifyFacade notifyFacade;

	@SuppressWarnings("static-access")
	public synchronized void onMessage(Message message, Session session) {
		try {
			ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
			final String ms = msg.getText();
			log.info("== receive message:" + ms);
			NotifyRecord notifyRecord = JSONObject.parseObject(ms, NotifyRecord.class);// 这里转换成相应的对象还有问题
			if (notifyRecord == null) {
				return;
			}
			// log.info("notifyParam:" + notifyParam);
			notifyRecord.setStatus(NotifyStatusEnum.CREATED.getValue());
			if (notifyRecord.getId() == null) {// 判断数据库中是否已有通知记录
				// log.info("ActiveMQUtil.notifyFacade:" +
				// ActiveMQUtil.notifyFacade);
				while (notifyFacade == null) {
					Thread.currentThread().sleep(1000); // 主动休眠，防止类notifyRecordFacade未加载完成，监听服务就开启监听出现空指针异常
				}

				try {
					// 将获取到的通知先保存到数据库中
					long notifyId = NotifyPersist.saveNotifyRecord(notifyRecord);
					notifyRecord.setId(notifyId); // 插入后，立即返回ID by chenjianhua

					// 添加到通知队列
					notifyQueue.addElementToList(notifyRecord);
				} catch (RpcException e) {

					notifyJmsTemplate.send(sessionAwareQueue, new MessageCreator() {
						public Message createMessage(Session session) throws JMSException {
							return session.createTextMessage(ms);
						}
					});

					log.error("RpcException :", e);
				} catch (BizException e) {
					log.error("BizException :", e);
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}
}
