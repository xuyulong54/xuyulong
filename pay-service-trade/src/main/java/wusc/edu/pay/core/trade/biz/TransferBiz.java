package wusc.edu.pay.core.trade.biz;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.core.trade.biz.sub.AccountBiz;
import wusc.edu.pay.core.trade.biz.sub.LimtBiz;
import wusc.edu.pay.core.trade.dao.TransferRecordDao;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.settlement.service.SettQueryFacade;
import wusc.edu.pay.facade.trade.entity.TransferRecord;
import wusc.edu.pay.facade.trade.enums.OrderStatusEnum;
import wusc.edu.pay.facade.trade.exception.TradeBizException;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.service.UserQueryFacade;


/**
 * 
 * @描述: 转账记录表，业务层接口 .
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-9,上午10:00:23 .
 * @版本: 1.0 .
 */
@Component("transferBiz")
public class TransferBiz {

	private static final Log log = LogFactory.getLog(TransferBiz.class);

	@Autowired
	private TransferRecordDao transferRecordDao;

	@Autowired
	private AccountBiz accountBiz;

	@Autowired
	private UserQueryFacade userQueryFacade;

	@Autowired
	private LimtBiz limtBiz;
	@Autowired
	private SettQueryFacade settQueryFacade;

	/**
	 * 创建转账
	 * 
	 * @param targetLoginName
	 * @param sourceLoginName
	 * @param transferAmount
	 * @param transferDesc
	 */
	@Transactional(rollbackFor = Exception.class)
	public void createTransfer(String transferTrxNo, String targetLoginName, String sourceLoginName, double transferAmount,
			String transferDesc) {

		log.info("==>createTransfer");
		log.info(String.format("==>transferTrxNo:%s, targetLoginName:%s, sourceLoginName:%s, transferAmount:%s, transferDesc:%s",
				transferTrxNo, targetLoginName, sourceLoginName, transferAmount, transferDesc));

		UserInfo targetUserInfo = userQueryFacade.getUserInfoByLoginName(targetLoginName);
		if (targetUserInfo == null) {
			throw TradeBizException.TRANSFER_USERINFO_NOT_EXIST.newInstance("转账,用户不存在,登录名:%s", targetLoginName);
		}

		UserInfo sourceUserInfo = userQueryFacade.getUserInfoByLoginName(sourceLoginName);
		if (sourceUserInfo == null) {
			throw TradeBizException.TRANSFER_USERINFO_NOT_EXIST.newInstance("转账,用户不存在,登录名:%s", sourceLoginName);
		}

		TransferRecord transferRecord = new TransferRecord();
		transferRecord.setAmount(transferAmount);
		transferRecord.setCreateTime(new Date());
		transferRecord.setTrxNo(transferTrxNo);
		transferRecord.setRemark(transferDesc);
		transferRecord.setSourceUserNo(sourceUserInfo.getUserNo());
		transferRecord.setSourceLoginName(sourceLoginName);
		transferRecord.setSourceName(sourceUserInfo.getRealName());
		transferRecord.setSourceMobileNo(sourceUserInfo.getBindMobileNo());
		transferRecord.setStatus(OrderStatusEnum.SUCCESS.getValue());
		transferRecord.setTargetUserNo(targetUserInfo.getUserNo());
		transferRecord.setTargetLoginName(targetLoginName);
		transferRecord.setTargetName(targetUserInfo.getRealName());
		transferRecord.setSourceUserType(sourceUserInfo.getUserType());
		transferRecord.setTargetUserType(targetUserInfo.getUserType());

		// 累加交易金额 TODO chenjianhua-wushuichen
		limtBiz.tradeAmountAddUp(LimitTrxTypeEnum.ACCOUNT_TRANSFER, null, null, null, new BigDecimal(transferAmount),
				transferRecord.getTargetUserNo(), new Date());

		// step.1 手续费预算
		// FeeCalculateResultDTO dto =
		// feeBiz.preCaculate_AccountTransfer(transferRecord);

		// step.2 保存转账记录
		transferRecordDao.insert(transferRecord);

		// step.3 更新账务
		accountBiz.transfer(transferRecord);

		// step.5 手续费计费（写队列）
		// feeBiz.caculate(dto);

		// step.6 通知风控分析

		log.info("==>createTransfer<==");
	}
}
