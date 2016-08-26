package wusc.edu.pay.core.settlement.biz.sub;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.enums.CurrencyTypeEnum;
import wusc.edu.pay.core.settlement.biz.BatchNoBuildBiz;
import wusc.edu.pay.core.settlement.dao.SettDailyCollectDao;
import wusc.edu.pay.facade.remit.service.RemitRequestFacade;
import wusc.edu.pay.facade.settlement.DTO.LaunchSettleVo;
import wusc.edu.pay.facade.settlement.entity.SettDailyCollect;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.enums.SettDailyCollectStatusEnum;
import wusc.edu.pay.facade.settlement.enums.SettDailyCollectTypeEnum;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;


/**
 * 
 * @描述: 结算公用方法biz.
 * @创建时间:2015-3-11,下午1:44:09.
 * @版本:
 */
@Component("settBiz")
public class SettBiz {

	@Autowired
	private SettDailyCollectDao settDailyCollectDao;
	
	@Autowired
	private BatchNoBuildBiz batchNoBuildBiz;
	
	@Autowired
	private RemitRequestFacade remitRequestFacade;
	/**
	 * 填充结算记录
	 * 
	 * @param settleVo
	 * @return
	 */
	public SettRecord populateSettRecord(LaunchSettleVo settleVo) {
		SettRecord settRecord = new SettRecord();
		// 获取结算批次号
		SettRule settRule = settleVo.getSettRule();
		String newBatchNo = batchNoBuildBiz.getSettlementBatchCode(settleVo.getAccountNo(), settleVo.getCurrentDate(), settRule.getLastBatchNo());
		//批次号
		settRule.setLastBatchNo(newBatchNo);

		// 生成打款编号
		String remitNo = remitRequestFacade.buildRemitRequestNo(); // 获取打款请求号;

		settRecord.setBatchNo(newBatchNo);
		settRecord.setRemitNo(remitNo);
		settRecord.setAccountNo(settRule.getAccountNo());

		// 银行相关
		settRecord.setBankAccountAddress(settleVo.getBankAccount().getBankAccountAddress());
		settRecord.setBankAccountName(settleVo.getBankAccount().getBankAccountName());
		settRecord.setBankAccountNo(settleVo.getBankAccount().getBankAccountNo());
		settRecord.setBankAccountType(settleVo.getBankAccount().getBankAccountType());
		settRecord.setBankChannelNo(settleVo.getBankAccount().getBankChannelNo());
		settRecord.setBankCode(settleVo.getBankAccount().getBankCode());
		settRecord.setCountry("");
		settRecord.setProvince(settleVo.getBankAccount().getProvince());
		settRecord.setCity(settleVo.getBankAccount().getCity());

		// 其他
		settRecord.setCurrencyType(CurrencyTypeEnum.RMB.getValue());
		settRecord.setRefundAmount(new BigDecimal(0));
		settRecord.setRefundFee(new BigDecimal(0));
		settRecord.setRefundNum(0);

		settRecord.setSettMode(settleVo.getSettType());// 结算发起方式
		settRecord.setSettDate(settleVo.getCurrentDate());// 结算时间
		settRecord.setSettStatus(SettRecordStatusEnum.WAIT_CONFIRM.getValue());// 结算状态

		settRecord.setBeginDate(settRule.getLastSettDate());// 结算开始时间
		settRecord.setEndDate(settleVo.getSettleEndDate());// 结算结束时间
		settRecord.setRemitRemark(settRule.getRemark());// 规则里面设置的备注作为打款时备注
		settRecord.setRemark(settleVo.getRemark());// 发起结算是备注
		settRecord.setOperatorLoginname(settleVo.getOperatorLoginname());
		settRecord.setOperatorRealname(settleVo.getOperatorRealname());
		settRecord.setUserName(settRule.getUserName());
		settRecord.setUserType(UserTypeEnum.MERCHANT.getValue());
		settRecord.setUserNo(settRule.getUserNo());
		return settRecord;
	}

	
	/**
	 * 更新待汇总记录
	 * 
	 * @param totalDailys
	 * @param nowBatchNo
	 * @param isSelfHelpCheck  是否是自动审核  true 自动  false 手动
	 */
	public void updateDailyCollectList(List<SettDailyCollect> totalDailys, String nowBatchNo,boolean isSelfHelpCheck) {
		for (SettDailyCollect daily : totalDailys) {
			daily.setBatchNo(nowBatchNo);
			//自动结算不需要审核结算记录，直接把汇总记录标记为已结算。
			if(isSelfHelpCheck){
				daily.setSettStatus(SettDailyCollectStatusEnum.SETTLLED.getValue());
			}
			// 这里不更新状态，如果审核失败不会对汇总有影响，审核是吧只要把结算规则里面的当前结算状态设置为可结算即可
			if (daily.getId() != null && daily.getId() > 0) {
				settDailyCollectDao.update(daily);
			} else {
				settDailyCollectDao.insert(daily);
			}
		}
	}
	
	/**
	 * 填充类型为 TEMP 的待结算汇总
	 * @param record
	 * @param collectVo
	 * @param newCollectNo
	 * @return
	 */
	public SettDailyCollect popluateTempDailyCollect(SettRecord record, SettDailyCollect temp) {
		temp.setAccountNo(record.getAccountNo());
		temp.setBatchNo(record.getBatchNo());
		temp.setCollectType(SettDailyCollectTypeEnum.TEMP.getValue());
		temp.setCollectDate(new Date());
		temp.setSettStatus(SettDailyCollectStatusEnum.UNSETTLE.getValue());
		temp.setRemark("结算临时反向金额归集");
		/** 创建临时汇总记录 **/
		// settDailyCollectDao.insert(temp);
		return temp;
	}
}
