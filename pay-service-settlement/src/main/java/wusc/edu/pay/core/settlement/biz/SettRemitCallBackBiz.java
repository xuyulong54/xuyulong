package wusc.edu.pay.core.settlement.biz;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.core.settlement.biz.sub.AccountBiz;
import wusc.edu.pay.core.settlement.dao.SettErrorRecordDao;
import wusc.edu.pay.core.settlement.dao.SettRecordDao;
import wusc.edu.pay.facade.settlement.entity.SettErrorRecord;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.enums.SettErrorRecordStatusEnum;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;
import wusc.edu.pay.facade.settlement.exception.SettBizException;


@Component("settRemitCallBackBiz")
public class SettRemitCallBackBiz {

	@Autowired
	private SettRecordDao settRecordDao;
	@Autowired
	private AccountBiz accountBiz;
	@Autowired
	private SettErrorRecordDao settErrorRecordDao;
	
	private static final Log log = LogFactory.getLog(SettRemitCallBackBiz.class);

	/**
	 * 打款结果回调接口.<br/>
	 * 
	 * @param remitNo
	 *            打款请求号.<br/>
	 * @param remitConfirmTime
	 *            打款确认时间.<br/>
	 * @param remitStatus
	 *            打款状态(100:成功,101:失败).<br/>
	 * @param remitRemark
	 *            打款描述.<br/>
	 */
	@Transactional(rollbackFor = { Exception.class })
	public void settRemitCallBack(String remitRequestNo, Date remitConfirmTime, Integer remitStatus, String remitRemark) {

		SettRecord settRecord = settRecordDao.getByRemitNo(remitRequestNo);
		if (settRecord == null) {
			throw SettBizException.SETT_RECORD_NOT_EXIST;
		}

		if(settRecord.getSettStatus() == SettRecordStatusEnum.REMIT_SUCCESS.getValue()){
			throw SettBizException.SETT_RECORD_ALREADY_SUCCESS;
		}
		
		if (remitStatus == PublicStatusEnum.ACTIVE.getValue()) {

			// 更新结算记录
			settRecord.setSettStatus(SettRecordStatusEnum.REMIT_SUCCESS.getValue()); // 打款成功
			settRecord.setRemitConfirmTime(remitConfirmTime);
			settRecordDao.update(settRecord);

			// 账户处理
			accountBiz.settSuccess(settRecord);

		} else if (remitStatus == PublicStatusEnum.INACTIVE.getValue()) {
			// 更新结算记录
			settRecord.setSettStatus(SettRecordStatusEnum.FAIL_RETURN.getValue()); // 失败退回
			settRecord.setRemitConfirmTime(remitConfirmTime);
			settRecordDao.update(settRecord);

			// 创建结算失败记录
			SettErrorRecord errorRecord = new SettErrorRecord();
			errorRecord.setUserNo(settRecord.getUserNo());
			errorRecord.setUserName(settRecord.getUserName());
			errorRecord.setAccountNo(settRecord.getAccountNo());
			errorRecord.setSettDate(settRecord.getSettDate());
			errorRecord.setSettMode(settRecord.getSettMode());
			errorRecord.setErrDesc(remitRemark);
			errorRecord.setStatus(SettErrorRecordStatusEnum.UNPROCESSED.getValue()); // 未处理
			errorRecord.setBeginDate(settRecord.getBeginDate());
			errorRecord.setEndDate(settRecord.getEndDate());
			settErrorRecordDao.insert(errorRecord);
		} else {
			log.info("remitStatus value error");
		}

	}
}
