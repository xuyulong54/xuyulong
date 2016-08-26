package wusc.edu.pay.facade.settlement.service;

import java.util.Date;

import wusc.edu.pay.facade.account.exception.AccountBizException;
import wusc.edu.pay.facade.settlement.entity.SettControl;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.exception.SettBizException;


/**
 * 结算规则管理服务接口
 * 
 */
public interface SettManagementFacade {
	
	/*********************************结算规则****************************************************/
	/**
	 * 创建结算规则<br>
	 * @return 
	 */
	public long createSettlementRule(SettRule param) throws SettBizException;

	/**
	 * 修改结算规则信息
	 * 
	 * @param param
	 */
	public void updateSettRule(SettRule param) throws SettBizException;
	
	
	/*********************************结算节假日设置****************************************************/

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
	public void createSettHolidaySetting(Date startDate, Date endDate,
			String desc)  throws SettBizException;

	
	/**
	 * 删除不可结算日
	 * 
	 * @param String
	 *            [] id
	 */
	public void delSettlementHoliday(String[] id) throws SettBizException;
	
	
	/*********************************结算日调整设置****************************************************/
	/**
	 * 增加结算日调整。如国庆假、春节假休息，需要调整工作日，导致周六或周日工作
	 * 
	 * @param settleDay
	 *            结算日，结算规则中设定
	 * @param toDay
	 *            调整后的结算日
	 * @param desc
	 *            描述
	 */
	public void addAdjustSettlementDay(Date settleDay, Date toDay, String desc) throws SettBizException;

	

	/**
	 * 删除结算日调整
	 * 
	 * @param String
	 *            [] ids
	 */
	public void delAdjustSettlementDay(String[] ids) throws SettBizException;
	
	
	/*********************************结算控制****************************************************/
	/**
	 * 修改结算系统控制数据，只修改每日汇总时间段的起止时间
	 * 
	 * @param param
	 */
	public void updateSettControl(SettControl param) throws SettBizException;

	/**
	 * 创建结算控制
	 * @param sc
	 */
	public void createSettControl(SettControl sc) throws SettBizException;
	

	/*********************************结算管理****************************************************/
	/**
	 * 根据结算批次号和账户编号
	 * 更新结算状态和备注
	 * @param remark 
	 * @param settStatus 
	 * @param accountNo 
	 * @param batchNo 
	 * 
	 */
	public void updateSettRecordByBatchNoAccountNo(String batchNo, String accountNo, Integer settStatus, String remark)throws SettBizException,AccountBizException;

	/***
	 * 重新绑定商户编号
	 * @param accountNo 账户编号
	 * @param newMerchNo 新商户编号
	 * @return
	 */
	public long reBindUserNo(String accountNo, String newMerchNo) throws SettBizException;


	
}
