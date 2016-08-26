package wusc.edu.pay.core.remit.biz.sub;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.core.remit.biz.RemitBiz;
import wusc.edu.pay.core.remit.dao.RemitRequestDao;
import wusc.edu.pay.facade.notify.util.NotifyUtil;
import wusc.edu.pay.facade.remit.entity.RemitRequest;
import wusc.edu.pay.facade.remit.enums.RemitRequestStatusEnum;
import wusc.edu.pay.facade.remit.enums.TradeSourcesEnum;


@Component("remitReviewBiz")
public class RemitReviewBiz {

	@Autowired
	private RemitRequestDao remitRequestDao;
	@Autowired
	private RemitBiz remitBiz;
	@Autowired
	private JmsTemplate notifyJmsTemplate;

	private static final Log log = LogFactory.getLog(RemitReviewBiz.class);

	/**
	 * 复核通过
	 * 
	 * @param ids
	 * @param reviewLoginName
	 *            审核人登录名
	 */
	@Transactional(rollbackFor = Exception.class)
	public void reviewSuccess(String[] ids, String reviewLoginName) {

		log.info("==>batchReviewSuccess");

		List<RemitRequest> list = new ArrayList<RemitRequest>();
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];

			if (StringUtil.isBlank(id)) {
				continue;
			}

			RemitRequest remitRequest = remitRequestDao.getById(Long.parseLong(id));
			if (remitRequest == null) {
				continue;
			}

			list.add(remitRequest);
		}

		for (int i = 0; i < list.size(); i++) {
			this.reviewSuccess(list.get(i), reviewLoginName);
		}

		log.info("==>batchReviewSuccess<==");
	}

	@Transactional(rollbackFor = Exception.class)
	public void reviewSuccess(RemitRequest remitRequest, String reviewLoginName) {

		log.info("==>checkSuccess");

		remitRequest.setStatus(RemitRequestStatusEnum.REMITING.getValue()); // 处理中
		remitRequestDao.update(remitRequest);

		log.info("==>checkSuccess<==");

	}

	/**
	 * 复核失败
	 * 
	 * @param ids
	 * @param reviewLoginName
	 *            审核人登录名
	 */
	@Transactional(rollbackFor = Exception.class)
	public void reviewFail(String[] ids, String reviewLoginName) {

		log.info("==>reviewFail");

		List<RemitRequest> list = new ArrayList<RemitRequest>();
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];

			if (StringUtil.isBlank(id)) {
				continue;
			}

			RemitRequest remitRequest = remitRequestDao.getById(Long.parseLong(id));
			if (remitRequest == null) {
				continue;
			}

			list.add(remitRequest);
		}

		for (int i = 0; i < list.size(); i++) {
			this.reviewFail(list.get(i), reviewLoginName);
		}

		log.info("==>reviewFail<==");
	}

	@Transactional(rollbackFor = Exception.class)
	private void reviewFail(final RemitRequest remitRequest, String reviewLoginName) {

		log.info("==>reviewFail");

		if(remitRequest.getTradeInitiator() == TradeSourcesEnum.BOSS_SYSTEM.getValue()){
			remitRequest.setStatus(RemitRequestStatusEnum.UNAUDIT.getValue()); // 待审核
			remitRequestDao.update(remitRequest);
		}else{
			remitRequest.setStatus(RemitRequestStatusEnum.REMIT_FAILURE.getValue()); // 打款失败
			remitRequest.setBankRemark("打款复核失败");
			remitRequestDao.update(remitRequest);

			// 通知结算系统
			notifyJmsTemplate.send(new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(NotifyUtil.formatRemitComplete(remitRequest.getRequestNo(), remitRequest.getConfirmDate(), PublicStatusEnum.INACTIVE.getValue(), ""));
				}
			});
		}
		
		log.info("==>reviewFail<==");

	}
}
