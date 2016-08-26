package wusc.edu.pay.core.remit.biz;

import java.util.Date;
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
import wusc.edu.pay.core.remit.biz.sub.AccountBiz;
import wusc.edu.pay.core.remit.dao.RemitBatchDao;
import wusc.edu.pay.core.remit.dao.RemitProcessDao;
import wusc.edu.pay.core.remit.dao.RemitRequestDao;
import wusc.edu.pay.facade.notify.util.NotifyUtil;
import wusc.edu.pay.facade.remit.entity.RemitBatch;
import wusc.edu.pay.facade.remit.entity.RemitProcess;
import wusc.edu.pay.facade.remit.entity.RemitRequest;
import wusc.edu.pay.facade.remit.enums.RemitBatchStatusEnum;
import wusc.edu.pay.facade.remit.enums.RemitProcessStatusEnum;
import wusc.edu.pay.facade.remit.enums.RemitRequestStatusEnum;
import wusc.edu.pay.facade.remit.enums.TradeSourcesEnum;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * 打款业务处理核心类
 * 
 * @author Administrator
 * 
 */
@Component("remitBiz")
public class RemitBiz {

	@Autowired
	private RemitProcessDao remitProcessDao;
	@Autowired
	private RemitRequestDao remitRequestDao;
	@Autowired
	private RemitBatchDao remitBatchDao;
	@Autowired
	private AccountBiz accountBiz;

	@Autowired
	private JmsTemplate notifyJmsTemplate;

	private static final Log log = LogFactory.getLog(RemitBiz.class);

	/**
	 * 打款成功
	 * 
	 * @param batchNo
	 * @param loginName
	 */
	public void remitSuccess(String batchNo, String loginName) {

		log.info("==>remitSuccess");

		// 1. 批次是否存在
		RemitBatch remitBatch = remitBatchDao.getByBatchNo(batchNo);
		if (remitBatch == null) {
			throw RemitBizException.REMIT_BATCH_RECORD_NOT_EXIST.print();
		}

		// 处理中,或处理完成
		if (remitBatch.getStatus() == RemitBatchStatusEnum.SOLVED.getValue()) {
			throw RemitBizException.REMIT_BATCH_IS_COMPLETED.print();
		}

		// 2. 打款请求是否存在
		List<RemitRequest> listRemitRequest = remitRequestDao.listByBatchNo(batchNo);
		if (listRemitRequest == null) {
			throw RemitBizException.REMIT_REQUEST_RECORD_NOT_EXIST.print();
		}

		// 3. 打款处理记录是否存在
		List<RemitProcess> listRemitProcess = remitProcessDao.listByBatchNo(batchNo);
		if (listRemitProcess == null) {
			throw RemitBizException.REMIT_PROCESS_RECORD_NOT_EXIST.print();
		}

		// 4. 填充, 保存
		remitBatch.setStatus(RemitBatchStatusEnum.SOLVED.getValue()); // 处理完成
		remitBatch.setProcessSucAmount(remitBatch.getTotalAmount());
		remitBatch.setProcessSucNum(remitBatch.getTotalNum());
		remitBatch.setProcessDate(new Date());
		remitBatch.setConfirm(loginName);
		remitBatch.setConfirmDate(new Date());
		remitBatchDao.update(remitBatch);

		for (int i = 0; i < listRemitRequest.size(); i++) {
			listRemitRequest.get(i).setStatus(RemitRequestStatusEnum.REMIT_SUCCESS.getValue()); // 打款成功
			remitRequestDao.update(listRemitRequest.get(i));
		}

		for (int i = 0; i < listRemitProcess.size(); i++) {
			listRemitProcess.get(i).setStatus(RemitProcessStatusEnum.REMIT_SUCCESS.getValue()); // 打款成功
			listRemitProcess.get(i).setProcessDate(new Date());
			remitProcessDao.update(listRemitProcess.get(i));
		}

		// 5. 备付金,虚拟账户处理
		accountBiz.remitSuccess(remitBatch);

		// 6. 异步通知结算系统
		for (int i = 0; i < listRemitRequest.size(); i++) {
			final RemitRequest remitRequest = listRemitRequest.get(i);
			// 通知结算
			if (remitRequest.getTradeInitiator() != TradeSourcesEnum.BOSS_SYSTEM.getValue()) {
				notifyJmsTemplate.send(new MessageCreator() {
					public Message createMessage(Session session) throws JMSException {
						return session.createTextMessage(NotifyUtil.formatRemitComplete(remitRequest.getRequestNo(), remitRequest.getConfirmDate(), PublicStatusEnum.ACTIVE.getValue(), ""));
					}
				});
			}
		}

		log.info("==>remitSuccess<==");
	}

	/**
	 * 打款失败
	 * 
	 * @param batchNo
	 * @param loginName
	 * @param remitRemark
	 */
	@Transactional(rollbackFor = Exception.class)
	public void remitFail(String batchNo, String loginName, String remitRemark) {
		log.info("==>remitFail");

		// 1. 批次是否存在
		RemitBatch remitBatch = remitBatchDao.getByBatchNo(batchNo);
		if (remitBatch == null) {
			throw RemitBizException.REMIT_BATCH_RECORD_NOT_EXIST.print();
		}

		// 处理中,或处理完成
		if (remitBatch.getStatus() == RemitBatchStatusEnum.SOLVED.getValue()) {
			throw RemitBizException.REMIT_BATCH_IS_COMPLETED.print();
		}

		// 2. 打款请求是否存在
		List<RemitRequest> listRemitRequest = remitRequestDao.listByBatchNo(batchNo);
		if (listRemitRequest == null) {
			throw RemitBizException.REMIT_REQUEST_RECORD_NOT_EXIST.print();
		}

		// 3. 打款处理记录是否存在
		List<RemitProcess> listRemitProcess = remitProcessDao.listByBatchNo(batchNo);
		if (listRemitProcess == null) {
			throw RemitBizException.REMIT_PROCESS_RECORD_NOT_EXIST.print();
		}

		// 4. 填充, 保存
		remitBatch.setStatus(RemitBatchStatusEnum.SOLVED.getValue()); // 处理完成
		remitBatch.setProcessFailAmount(remitBatch.getTotalAmount());
		remitBatch.setProcessFailNum(remitBatch.getTotalNum());
		remitBatch.setProcessDate(new Date());
		remitBatch.setConfirm(loginName);
		remitBatch.setConfirmDate(new Date());
		remitBatchDao.update(remitBatch);

		for (int i = 0; i < listRemitRequest.size(); i++) {
			listRemitRequest.get(i).setStatus(RemitRequestStatusEnum.REMIT_FAILURE.getValue()); // 打款失败
			listRemitRequest.get(i).setBankRemark(remitRemark);
			remitRequestDao.update(listRemitRequest.get(i));
		}

		for (int i = 0; i < listRemitProcess.size(); i++) {
			listRemitProcess.get(i).setStatus(RemitProcessStatusEnum.REMIT_FAILURE.getValue()); // 打款失败
			listRemitProcess.get(i).setProcessDate(new Date());
			listRemitProcess.get(i).setBankRemark(remitRemark);
			remitProcessDao.update(listRemitProcess.get(i));
		}

		// 5. 备付金,虚拟账户处理
		accountBiz.remitFail(remitBatch);

		// 6. 异步通知结算系统
		for (int i = 0; i < listRemitRequest.size(); i++) {
			final RemitRequest remitRequest = listRemitRequest.get(i);
			// 通知结算
			if (remitRequest.getTradeInitiator() != TradeSourcesEnum.BOSS_SYSTEM.getValue()) {
				notifyJmsTemplate.send(new MessageCreator() {
					public Message createMessage(Session session) throws JMSException {
						return session.createTextMessage(NotifyUtil.formatRemitComplete(remitRequest.getRequestNo(), remitRequest.getConfirmDate(), PublicStatusEnum.INACTIVE.getValue(), ""));
					}
				});
			}
		}

		log.info("==>remitFail<==");
	}

}
