package wusc.edu.pay.core.remit.biz.sub;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.core.remit.dao.RemitBatchDao;
import wusc.edu.pay.core.remit.dao.RemitChannelDao;
import wusc.edu.pay.core.remit.dao.RemitChannelFlowRuleDao;
import wusc.edu.pay.core.remit.dao.RemitProcessDao;
import wusc.edu.pay.core.remit.dao.RemitRequestDao;
import wusc.edu.pay.facade.remit.entity.RemitBatch;
import wusc.edu.pay.facade.remit.entity.RemitChannel;
import wusc.edu.pay.facade.remit.entity.RemitChannelFlowRule;
import wusc.edu.pay.facade.remit.entity.RemitProcess;
import wusc.edu.pay.facade.remit.entity.RemitRequest;
import wusc.edu.pay.facade.remit.enums.RemitBatchStatusEnum;
import wusc.edu.pay.facade.remit.enums.RemitProcessStatusEnum;
import wusc.edu.pay.facade.remit.enums.RemitRequestStatusEnum;


@Component("remitRouterBiz")
public class RemitRouterBiz {

	@Autowired
	private RemitRequestDao remitRequestDao;
	@Autowired
	private RemitBatchDao remitBatchDao;
	@Autowired
	private RemitProcessDao remitProcessDao;
	@Autowired
	private RemitChannelDao remitChannelDao;
	@Autowired
	private RemitChannelFlowRuleDao remitChannelFlowRuleDao;
	@Autowired
	private AccountBiz accountBiz;

	private static final Log log = LogFactory.getLog(RemitRouterBiz.class);

	/**
	 * 发起批次
	 */
	@Transactional(rollbackFor = Exception.class)
	public void lanunchBatch() {

		// 1. 查找未批次的打款请求数据
		List<RemitRequest> listRemitRequest = remitRequestDao.listBatchNoIsNull();

		// 2. 通道智能路由
		List<RemitChannel> listRemitChannel = this.channelRouter(listRemitRequest);

		// 3. 创建批次
		List<RemitBatch> listRemitBatch = this.createBatch(listRemitChannel);

		// 4. 账户处理
		accountBiz.remitCreate(listRemitBatch);

		// 5. 调用自动接口
		for (int i = 0; i < listRemitBatch.size(); i++) {
			if (listRemitBatch.get(i).getIsAutoRemit() == PublicStatusEnum.ACTIVE.getValue()) {
				String remitChannelCode = listRemitBatch.get(i).getRemitChannelCode();
				// do something
			}
		}
	}

	/**
	 * 创建批次
	 * 
	 * @param listRemitChannel
	 */
	@Transactional(rollbackFor = Exception.class)
	private List<RemitBatch> createBatch(List<RemitChannel> listRemitChannel) {

		List<RemitBatch> listRemitBatch = new ArrayList<RemitBatch>();

		for (int i = 0; i < listRemitChannel.size(); i++) {

			if (listRemitChannel.get(i).getListRemitRequest() == null) {
				continue;
			}

			if (listRemitChannel.get(i).getListRemitRequest().size() == 0) {
				continue;
			}

			BigDecimal totalRemitAmount = BigDecimal.ZERO; // 打款总金额
			for (int j = 0; j < listRemitChannel.get(i).getListRemitRequest().size(); j++) {
				totalRemitAmount = totalRemitAmount.add(listRemitChannel.get(i).getListRemitRequest().get(j).getAmount());
			}

			// 2. 创建批次
			String batchNo = remitBatchDao.buildRemitBatchNo();

			RemitBatch remitBatch = new RemitBatch();
			remitBatch.setBatchNo(batchNo);
			remitBatch.setStatus(RemitBatchStatusEnum.HANDLING.getValue());
			remitBatch.setTotalNum(listRemitChannel.get(i).getListRemitRequest().size());
			remitBatch.setTotalAmount(totalRemitAmount);
			remitBatch.setAcceptSucAmount(totalRemitAmount);
			remitBatch.setAcceptSucNum(listRemitChannel.get(i).getListRemitRequest().size());
			remitBatch.setCreateDate(new Date());
			remitBatch.setAcceptDate(new Date());
			remitBatch.setRemitChannelCode(listRemitChannel.get(i).getChannelCode());
			remitBatch.setIsAutoRemit(listRemitChannel.get(i).getIsAutoRemit());
			remitBatch.setRemitBankAccountNo(listRemitChannel.get(i).getSrcAccountNum());

			listRemitBatch.add(remitBatch);

			remitBatchDao.insert(remitBatch);

			// 3. 更新打款记录
			for (int j = 0; j < listRemitChannel.get(i).getListRemitRequest().size(); j++) {
				listRemitChannel.get(i).getListRemitRequest().get(j).setStatus(RemitRequestStatusEnum.REMITING.getValue());
				listRemitChannel.get(i).getListRemitRequest().get(j).setBatchNo(batchNo);
				remitRequestDao.update(listRemitChannel.get(i).getListRemitRequest().get(j));
			}

			// 4. 批量创建打款处理记录
			List<RemitProcess> listRemitProcess = new ArrayList<RemitProcess>();
			for (int j = 0; j < listRemitChannel.get(i).getListRemitRequest().size(); j++) {
				RemitProcess remitProcess = new RemitProcess();
				this.fillRemitProcess(listRemitChannel.get(i).getListRemitRequest().get(j), remitProcess, listRemitChannel.get(i).getChannelCode());
				listRemitProcess.add(remitProcess);
			}
			remitProcessDao.insert(listRemitProcess);
		}

		return listRemitBatch;
	}

	/**
	 * 填充 remitProcess
	 * 
	 * @param remitRequest
	 * @param remitProcess
	 * @param remitChannelCode
	 */
	private void fillRemitProcess(RemitRequest remitRequest, RemitProcess remitProcess, String remitChannelCode) {

		log.info("==>fillRemitProcess");

		remitProcess.setRequestNo(remitRequest.getRequestNo());
		remitProcess.setBatchNo(remitRequest.getBatchNo());
		remitProcess.setTradeInitiator(remitRequest.getTradeInitiator());
		remitProcess.setAccountType(remitRequest.getAccountType());

		remitProcess.setAccountName(remitRequest.getAccountName());
		remitProcess.setAccountNo(remitRequest.getAccountNo());
		remitProcess.setBankChannelNo(remitRequest.getBankChannelNo());
		remitProcess.setBankName(remitRequest.getBankName());

		remitProcess.setProvince(remitRequest.getProvince());
		remitProcess.setCity(remitRequest.getCity());
		remitProcess.setCurrency(remitRequest.getCurrency());
		remitProcess.setIsAutoProcess(remitRequest.getIsAutoProcess());

		remitProcess.setIsUrgent(remitRequest.getIsUrgent());
		remitProcess.setAmount(remitRequest.getAmount());
		remitProcess.setIsReconciled(PublicStatusEnum.INACTIVE.getValue());
		remitProcess.setUserNo(remitRequest.getUserNo());

		remitProcess.setBusinessType(remitRequest.getBusinessType());
		remitProcess.setConfirm(remitRequest.getConfirm());
		remitProcess.setConfirmDate(new Date());
		remitProcess.setBankRemark("");

		remitProcess.setOrderId("");
		remitProcess.setStatus(RemitProcessStatusEnum.PROCESSING.getValue());
		remitProcess.setCreator(remitRequest.getCreator());
		remitProcess.setChannelCode(remitChannelCode);

		log.info("==>fillRemitProcess<==");
	}

	/**
	 * 智能路由
	 * 
	 * @param listRemitRequest
	 * @return
	 */
	private List<RemitChannel> channelRouter(List<RemitRequest> listRemitRequest) {

		// 1. 获取可用打款通道
		List<RemitChannel> listRemitChannel = remitChannelDao.listByActive();

		// 2. 打款请求记录,按路由规则分组到各个打款通道
		for (int i = 0; i < listRemitRequest.size(); i++) {
			String accountType = listRemitRequest.get(i).getAccountType().toString();
			String bankCode = listRemitRequest.get(i).getBankChannelNo().substring(0, 3);
			BigDecimal amount = listRemitRequest.get(i).getAmount();
			String tradeType = listRemitRequest.get(i).getBusinessType().toString();
			List<RemitChannelFlowRule> listRemitChannelFlowRule = remitChannelFlowRuleDao.listRoute(accountType, bankCode, amount, tradeType);

			if (listRemitChannelFlowRule == null)
				continue;

			if (listRemitChannelFlowRule.size() == 0)
				continue;

			for (int j = 0; j < listRemitChannel.size(); j++) {
				if (listRemitChannel.get(j).getChannelCode().equals(listRemitChannelFlowRule.get(0).getChannelCode())) {
					listRemitChannel.get(j).getListRemitRequest().add(listRemitRequest.get(i));
				}
			}
		}

		return listRemitChannel;
	}
}
