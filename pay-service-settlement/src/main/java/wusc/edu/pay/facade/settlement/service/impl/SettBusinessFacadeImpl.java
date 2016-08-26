package wusc.edu.pay.facade.settlement.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.settlement.biz.SettBusinessBiz;
import wusc.edu.pay.core.settlement.biz.SettCheckBiz;
import wusc.edu.pay.core.settlement.biz.SettHandleBiz;
import wusc.edu.pay.core.settlement.biz.SettLaunchBiz;
import wusc.edu.pay.core.settlement.biz.SettRemitCallBackBiz;
import wusc.edu.pay.core.settlement.biz.SettUrgentLaunchBiz;
import wusc.edu.pay.facade.account.exception.AccountBizException;
import wusc.edu.pay.facade.settlement.DTO.LaunchSettleVo;
import wusc.edu.pay.facade.settlement.DTO.UserBankAccountVo;
import wusc.edu.pay.facade.settlement.entity.SettDailyCollect;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.enums.SettModeTypeEnum;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;
import wusc.edu.pay.facade.settlement.exception.SettBizException;
import wusc.edu.pay.facade.settlement.service.SettBusinessFacade;


/**
 * 结算规则管理服务接口实现.
 * 
 * @author pc-Along
 * 
 */
@Component("settBusinessFacade")
public class SettBusinessFacadeImpl implements SettBusinessFacade {

	@Autowired
	private SettBusinessBiz settBusinessBiz;
	@Autowired
	private SettLaunchBiz settLaunchBiz;
	@Autowired
	private SettUrgentLaunchBiz settUrgentLaunchBiz;
	@Autowired
	private SettRemitCallBackBiz settRemitCallBackBiz;
	@Autowired
	private SettCheckBiz settCheckBiz;
	@Autowired
	private SettHandleBiz settHandleBiz;

	/**
	 * 判断是否可以发起结算、汇总(查询结算控制表).<br/>
	 * 
	 * @param settModeTypeEnum
	 * @param nowDateTime
	 * @return
	 */
	@Override
	public boolean isSettModeLaunchable(SettModeTypeEnum settModeTypeEnum, Date nowDateTime) {
		return settBusinessBiz.isSettModeLaunchable(settModeTypeEnum, nowDateTime);
	}

	/**
	 * 判断该日期是否进行过结算日期调整（如:因节假日调整）.<br/>
	 * 
	 * @param date
	 *            日期.<br/>
	 * @return true or false.
	 */
	@Override
	public boolean isSettDayAdjust(Date date) {
		return settBusinessBiz.isSettDayAdjust(date);
	}

	/**
	 * 创建结算记录.<br/>
	 * 
	 * @param settRecord
	 *            结算记录对象.<br/>
	 */
	@Override
	public void createSettRecord(SettRecord settRecord) {
		settBusinessBiz.createSettRecord(settRecord);
	}

	/**
	 * 批量创建结算记录.<br/>
	 * 
	 * @param settRecordList
	 *            结算记录对象列表.<br/>
	 */
	@Override
	public void createSettRecord(List<SettRecord> settRecordList) {
		settBusinessBiz.createSettRecord(settRecordList);
	}

	/**
	 * 更新日汇总记录为已结算，并回写结算批次号（创建结算记录成功后调用）.<br/>
	 * 
	 * @param collectList
	 *            要更新的汇总记录（必填）.<br/>
	 * @param batchNo
	 *            结算批次号（必填）.<br/>
	 */
	@Override
	public void updateSettDailyCollectToSetted(List<SettDailyCollect> collectList, String batchNo) {
		settBusinessBiz.updateSettDailyCollectToSetted(collectList, batchNo);
	}

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
	public void remitCallBack(String remitRequestNo, Date remitConfirmTime, Integer remitStatus, String remitRemark) {
		settRemitCallBackBiz.settRemitCallBack(remitRequestNo, remitConfirmTime, remitStatus, remitRemark);
	}

	/**
	 * 自助结算接口
	 * 
	 * @param settRecord
	 */
	public SettRecord launchSelfHelpSettle(LaunchSettleVo launchSettleVo, BigDecimal balance, boolean isSelfHelpSettle) {
		return settLaunchBiz.launchSettle(launchSettleVo, balance, isSelfHelpSettle);
	}

	@Override
	public void memberWithdraw(SettRecord settRecord) {
		settBusinessBiz.memberWithdraw(settRecord);
	}

	/**
	 * 判断是否为节假日.<br/>
	 * 
	 * @param date
	 *            要验证的日期.<br/>
	 * @return true or false.
	 */
	@Override
	public boolean isHoliday(Date date) {
		return settBusinessBiz.isHoliday(date);
	}

	/**
	 * 根据结算批次号，更新结算记录状态.<br/>
	 * 
	 * @param batchNo
	 * @param statusEnum
	 */
	@Override
	public void updateSettRecordSettStatus(String batchNo, SettRecordStatusEnum statusEnum) {
		settBusinessBiz.updateSettRecordSettStatus(batchNo, statusEnum);
	}

	/**
	 * 失败退回的结算记录重新结算
	 * 
	 * @param settRecord
	 */
	public void launchAnewSettle(SettRecord settRecord) {
		settHandleBiz.resendSettleRemit(settRecord);
	}

	/**
	 * 确认结算
	 * 
	 * @param settRecord
	 */
	public void confirmSettleProcess(SettRecord settRecord) {
		settCheckBiz.confirmSettleProcess(settRecord);
	}

	/**
	 * 按单个商户的结算规则发起每日待结算数据统计汇总.<br/>
	 * 
	 * @param settRule
	 *            结算规则.
	 * @param beginDate
	 *            汇总开始日期.
	 * @param endDate
	 *            汇总结束日期.
	 */
	@Override
	public void dailySettlementCollect(SettRule settRule, Date endDate) throws SettBizException {
		settBusinessBiz.dailySettlementCollect(settRule, endDate);
	}

	/**
	 * 查询结算金额 getSettAmount()
	 * 
	 * @param launchSettleVo
	 * @param balance
	 * @return
	 * @since 1.0
	 */
	public SettRecord getSettAmount(LaunchSettleVo launchSettleVo, BigDecimal balance) {
		return settLaunchBiz.getSettAmount(launchSettleVo, balance);
	}

	/**
	 * 调接口把结算记录的状态设置为 REMIT_FAIL (打款确定失败)，并解冻资金 confirmFail()
	 * 
	 * @param settRecord
	 * @since 1.0
	 */
	public void confirmFail(SettRecord settRecord) {
		settBusinessBiz.confirmFail(settRecord);
	}

	/**
	 * 
	 * @描述: 发起紧急结算（T+0）,需先运营审核才能发起.
	 * @作者: Along.shen .
	 * @创建时间:2015-3-3,下午1:53:09.
	 * @param merchantNo
	 *            商户编号
	 * @param trxNo
	 *            支付记录的流水号
	 * @param settAmount
	 *            结算金额
	 * @param bankAccount
	 *            结算账户信息
	 */
	public void launchUrgentSettle(String merchantNo, String trxNo, double settAmount, UserBankAccountVo bankAccount) throws SettBizException, AccountBizException {
		settUrgentLaunchBiz.launchUrgentSettle(merchantNo, trxNo, settAmount, bankAccount);
	}
}
