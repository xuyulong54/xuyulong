package wusc.edu.pay.facade.settlement.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.settlement.biz.SettManagementBiz;
import wusc.edu.pay.facade.settlement.entity.SettControl;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.exception.SettBizException;
import wusc.edu.pay.facade.settlement.service.SettManagementFacade;


/**
 * 结算规则管理服务接口实现
 * 
 * @author pc-Along
 * 
 */
@Component("settManagementFacade")
public class SettManagementFacadeImpl implements SettManagementFacade {
	@Autowired
	private SettManagementBiz settManagementBiz;

	/**
	 * 创建结算规则<br>
	 */
	public long createSettlementRule(SettRule param) throws SettBizException {
		return settManagementBiz.createSettlementRule(param);
	}

	/**
	 * 修改结算规则信息
	 * 
	 * @param param
	 */
	public void updateSettRule(SettRule param) throws SettBizException {
		settManagementBiz.updateSettRule(param);

	}

	/**
	 * 创建不可结算日（节假日），该日内不做结算
	 * 
	 * @param startDate
	 *            不可结算日开始日期
	 * @param endDate
	 *            不可结算日结束日期
	 * @param desc
	 *            对不可结算日的描述信息
	 */
	public void createSettHolidaySetting(Date startDate, Date endDate, String desc) {
		settManagementBiz.addSettHoliday(startDate,endDate,desc);
	}


	/**
	 * 删除不可结算日
	 * 
	 * @param String
	 *            [] ids
	 */
	public void delSettlementHoliday(String[] ids) {
		settManagementBiz.delSettlementHoliday(ids);
	}

	/**
	 *  增加结算日调整。如国庆假、春节假休息，需要调整工作日，导致周六或周日工作
	 * 
	 * @param settleDay
	 *            结算日，结算规则中设定
	 * @param toDay
	 *            调整后的结算日
	 * @param desc
	 *            描述
	 */
	public void addAdjustSettlementDay(Date settleDay, Date toDay, String desc) {
		settManagementBiz.addSettlementAdjustDay(settleDay,toDay,desc);
	}

	/**
	 * 删除结算日调整
	 * 
	 * @param String
	 *            [] ids
	 */
	public void delAdjustSettlementDay(String[] ids) {
		settManagementBiz.delAdjustSettlementDay(ids);
	}

	/**
	 * 修改结算系统控制数据，只修改每日汇总时间段的起止时间
	 * 
	 * @param param
	 */
	public void updateSettControl(SettControl param) {
		settManagementBiz.updateSettControl(param);
	}

	/**
	 * 创建结算控制
	 */
	@Override
	public void createSettControl(SettControl sc) {
		settManagementBiz.createSettControl(sc);
	}

	@Override
	public void updateSettRecordByBatchNoAccountNo(String batchNo, String accountNo, Integer settStatus, String remark) {
		settManagementBiz.updateSettRecordByBatchNoAccountNo(batchNo, accountNo, settStatus, remark);
	}

	/***
	 * 重新绑定商户编号
	 * @param accountNo 旧商户编号
	 * @param newMerchNo 新商户编号
	 * @return
	 */
	public long reBindUserNo(String accountNo, String newMerchNo) {
		return settManagementBiz.reBindUserNo(accountNo, newMerchNo);
	}
	
	
	
}
