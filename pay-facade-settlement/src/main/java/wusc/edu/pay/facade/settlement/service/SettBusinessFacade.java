package wusc.edu.pay.facade.settlement.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import wusc.edu.pay.facade.account.exception.AccountBizException;
import wusc.edu.pay.facade.fee.exceptions.FeeBizException;
import wusc.edu.pay.facade.settlement.DTO.LaunchSettleVo;
import wusc.edu.pay.facade.settlement.DTO.UserBankAccountVo;
import wusc.edu.pay.facade.settlement.entity.SettDailyCollect;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.enums.SettModeTypeEnum;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;
import wusc.edu.pay.facade.settlement.exception.SettBizException;

import com.alibaba.dubbo.rpc.RpcException;

/**
 * 
 * @描述: 结算业务服务接口 .
 * @作者: pc-Along,WuShuicheng.
 * @创建: 2014-7-31,上午9:50:08
 * @版本: V1.0
 * 
 */
public interface SettBusinessFacade {

	/**
	 * 判断是否可以发起结算、汇总(查询结算控制表).<br/>
	 * 
	 * @param settModeTypeEnum
	 * @param nowDateTime
	 * @return true or false.
	 */
	boolean isSettModeLaunchable(SettModeTypeEnum settModeTypeEnum, Date nowDateTime) throws SettBizException;

	/**
	 * 判断该日期是否进行过结算日期调整（如:因节假日调整）.<br/>
	 * 
	 * @param date
	 *            日期.<br/>
	 * @return true or false.
	 */
	boolean isSettDayAdjust(Date date) throws SettBizException;

	/**
	 * 创建结算记录.<br/>
	 * 
	 * @param settRecord
	 *            结算记录对象.<br/>
	 */
	void createSettRecord(SettRecord settRecord) throws SettBizException;

	/**
	 * 批量创建结算记录.<br/>
	 * 
	 * @param settRecordList
	 *            结算记录对象列表.<br/>
	 */
	void createSettRecord(List<SettRecord> settRecordList) throws SettBizException;

	/**
	 * 更新日汇总记录为已结算，并回写结算批次号（创建结算记录成功后调用）.<br/>
	 * 
	 * @param collectList
	 *            要更新的汇总记录（必填）.<br/>
	 * @param batchNo
	 *            结算批次号（必填）.<br/>
	 */
	void updateSettDailyCollectToSetted(List<SettDailyCollect> collectList, String batchNo) throws SettBizException;

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
	void remitCallBack(String remitRequestNo, Date remitConfirmTime, Integer remitStatus, String remitRemark) throws SettBizException, RpcException;

	/**
	 * 会员提现
	 * 
	 * @param settRecord
	 */
	void memberWithdraw(SettRecord settRecord) throws SettBizException;

	/**
	 * 自助结算接口
	 * 
	 * @param isSelfHelpSettle
	 *            判断是否是自助结算
	 * @return settRecord
	 */
	SettRecord launchSelfHelpSettle(LaunchSettleVo launchSettleVo, BigDecimal balance, boolean isSelfHelpSettle) throws SettBizException, FeeBizException;

	/**
	 * 判断是否为节假日.<br/>
	 * 
	 * @param date
	 *            要验证的日期.<br/>
	 * @return true or false.
	 */
	boolean isHoliday(Date date) throws SettBizException;

	/**
	 * 根据结算批次号，更新结算记录状态.<br/>
	 * 
	 * @param batchNo
	 * @param statusEnum
	 */
	void updateSettRecordSettStatus(String batchNo, SettRecordStatusEnum statusEnum) throws SettBizException;

	/**
	 * 失败退回的结算记录重新结算
	 * 
	 * @param settRecord
	 */
	void launchAnewSettle(SettRecord settRecord);

	/**
	 * 确认结算
	 * 
	 * @param settRecord
	 */
	public void confirmSettleProcess(SettRecord settRecord);

	/**
	 * 按单个商户的结算规则发起每日待结算数据统计汇总.<br/>
	 * 
	 * @param settRule
	 *            结算规则.
	 * @param endDate
	 *            汇总结束日期.
	 */
	public void dailySettlementCollect(SettRule settRule, Date endDate) throws AccountBizException, SettBizException;

	/**
	 * 查询结算金额 getSettAmount()
	 * 
	 * @param launchSettleVo
	 * @param balance
	 * @return
	 * @since 1.0
	 */
	public SettRecord getSettAmount(LaunchSettleVo launchSettleVo, BigDecimal balance) throws SettBizException, FeeBizException;

	/**
	 * 调接口把结算记录的状态设置为 REMIT_FAIL (打款确定失败)，并解冻资金 confirmFail()
	 * 
	 * @param settRecord
	 * @since 1.0
	 */
	public void confirmFail(SettRecord settRecord) throws SettBizException, AccountBizException;

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
	public void launchUrgentSettle(String merchantNo, String trxNo, double settAmount, UserBankAccountVo bankAccount) throws SettBizException, AccountBizException, FeeBizException, RpcException;

}
