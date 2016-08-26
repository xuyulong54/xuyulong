package wusc.edu.pay.core.remit.biz;

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
import wusc.edu.pay.core.remit.dao.RemitRequestDao;
import wusc.edu.pay.facade.remit.entity.RemitRequest;
import wusc.edu.pay.facade.remit.entity.SettlRequestParam;
import wusc.edu.pay.facade.remit.enums.RemitRequestStatusEnum;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * 打款请求实体
 * 
 * @author： Peter
 * @ClassName: RemitRequestBiz.java
 * @Date： 2014-7-22 下午4:32:22
 * @version： V1.0
 */
@Component("remitRequestBiz")
public class RemitRequestBiz {

	private static final Log log = LogFactory.getLog(RemitRequestBiz.class);

	@Autowired
	private RemitRequestDao remitRequestDao;

	public long deleteById(long id) throws RemitBizException {
		return remitRequestDao.deleteById(id);
	}

	/**
	 * 批量修改
	 * 
	 * @param remitRequests
	 */
	public void batchUpdateToStatus(List<RemitRequest> remitRequests, int status) {
		remitRequestDao.batchUpdateToStatus(remitRequests, status);
	}

	/**
	 * 创建打款请求_接口生成_自动审核,复核
	 * 
	 * @param settlRequestParam
	 */
	@Transactional(rollbackFor = Exception.class)
	public void createAndAutoCheck(SettlRequestParam settlRequestParam) {

		// 1. 保存打款请求
		RemitRequest remitRequest = new RemitRequest();
		this.fill(remitRequest, settlRequestParam);
		remitRequestDao.insert(remitRequest);

	}

	/**
	 * 创建打款请求_接口生成_自动审核,复核
	 * 
	 * @param listSettlRequestParam
	 */
	@Transactional(rollbackFor = Exception.class)
	public void createAndAutoCheck(List<SettlRequestParam> listSettlRequestParam) {

		// 1. 保存打款请求
		List<RemitRequest> listRemitRequest = new ArrayList<RemitRequest>();
		for (int i = 0; i < listSettlRequestParam.size(); i++) {
			RemitRequest remitRequest = new RemitRequest();
			this.fill(remitRequest, listSettlRequestParam.get(i));

			listRemitRequest.add(remitRequest);
		}

		remitRequestDao.insert(listRemitRequest);
	}

	/**
	 * 填充 remitRequest
	 * 
	 * @param remitRequest
	 * @param settlRequestParam
	 */
	private void fill(RemitRequest remitRequest, SettlRequestParam settlRequestParam) {

		remitRequest.setAccountName(settlRequestParam.getBankAccountName());
		remitRequest.setAccountNo(settlRequestParam.getBankAccountNo());
		remitRequest.setAccountType(settlRequestParam.getBankAccountType());
		remitRequest.setAmount(BigDecimal.valueOf(settlRequestParam.getAmount()));
		remitRequest.setBankChannelNo(settlRequestParam.getBankChannelNo());
		remitRequest.setBankName(settlRequestParam.getBankName());
		// remitRequest.setBankRemark(bankRemark);
		remitRequest.setBusinessType(settlRequestParam.getTradeType());
		// remitRequest.setCancelReason(cancelReason);
		remitRequest.setCity(settlRequestParam.getCity());
		remitRequest.setConfirm("system");
		remitRequest.setConfirmDate(new Date());
		remitRequest.setCopied(PublicStatusEnum.INACTIVE.getValue());
		remitRequest.setCreateDate(new Date());
		remitRequest.setCreateTime(new Date());
		remitRequest.setCreator("system");
		// remitRequest.setCurrency(setCreator);
		// remitRequest.setFlowNo(flowNo);
		remitRequest.setIsAutoProcess(PublicStatusEnum.ACTIVE.getValue());
		remitRequest.setIsUrgent(settlRequestParam.getIsUrgent());
		// remitRequest.setNotifyAddress(notifyAddress);
		// remitRequest.setOrialId(orialId);
		remitRequest.setProvince(settlRequestParam.getProvince());
		remitRequest.setRequestNo(settlRequestParam.getRequestNo());
		remitRequest.setStatus(RemitRequestStatusEnum.AUDITED.getValue()); // 已审核
		remitRequest.setTradeInitiator(settlRequestParam.getTradeSource());
		remitRequest.setUserNo(settlRequestParam.getUserNo());

	}
}
