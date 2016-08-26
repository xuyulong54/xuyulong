package wusc.edu.pay.timer.report.achieve;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.report.entity.AccountingUserReport;
import wusc.edu.pay.facade.report.service.AccountingUserReportFacade;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.UserStatusEnum;
import wusc.edu.pay.timer.report.util.ReportDataQuery;


/**
 * @描述: 用户财务报表（平台应付账款统计）.
 * @作者: Liliqiong.
 * @创建时间: 2014-4-29, 上午10:13:24 .
 * @版本: V1.0.
 * 
 */
@Component("addAccountingUserReport")
public class AddAccountingUserReport {
	
	@Autowired
	private ReportDataQuery reportDataQuery;
	@Autowired
	private AccountingUserReportFacade accountingUserReportFacade;

	public void execute() {

		// 激活，冻结的商户
		Map<String, Object> merchantStatusParamMap = new HashMap<String, Object>();
		List<String> statusMerchantlist = new ArrayList<String>();
		statusMerchantlist.add(String.valueOf(UserStatusEnum.ACTIVE.getValue()));
		statusMerchantlist.add(String.valueOf(UserStatusEnum.INACTIVE.getValue()));
		merchantStatusParamMap.put("inStatus", statusMerchantlist);
		merchantStatusParamMap.put("userType", AccountTypeEnum.MERCHANT.getValue());
		List<Object> statusMerchantList = reportDataQuery.getUserInfoList(merchantStatusParamMap);
		// 激活，冻结的会员
		Map<String, Object> memberStatusParamMap = new HashMap<String, Object>();
		List<String> statusMemberlist = new ArrayList<String>();
		statusMemberlist.add(String.valueOf(UserStatusEnum.ACTIVE.getValue()));
		statusMemberlist.add(String.valueOf(UserStatusEnum.INACTIVE.getValue()));
		memberStatusParamMap.put("inStatus", statusMemberlist);
		memberStatusParamMap.put("userType", AccountTypeEnum.CUSTOMER.getValue());
		List<Object> statusMemberList = reportDataQuery.getUserInfoList(memberStatusParamMap);
		// 账户数据
		List<Object> accountList = ReportDataQuery.getAccountList(null);
		// 打款记录
		Map<String, Object> remittanceRecordMap = new HashMap<String, Object>();
		List<String> ransferStatuslist = new ArrayList<String>();
		ransferStatuslist.add(String.valueOf(SettRecordStatusEnum.CONFIRMED.getValue()));
		ransferStatuslist.add(String.valueOf(SettRecordStatusEnum.REMIT_SUCCESS.getValue()));
		remittanceRecordMap.put("intransferStatus", ransferStatuslist);
		List<Object> remittanceRecordlist = reportDataQuery.getRemittanceRecordList(remittanceRecordMap);

		List<AccountingUserReport> accountingUserReportList = new ArrayList<AccountingUserReport>();// 保存数据
		int settingRansferStatus = SettRecordStatusEnum.CONFIRMED.getValue();// 打款状态（打款中）
		int sucessRansferStatus = SettRecordStatusEnum.REMIT_SUCCESS.getValue();// 打款状态（打款成功）
		if (statusMerchantList != null && !statusMerchantList.isEmpty()) {
			int merchantOnlineAccountType = AccountTypeEnum.MERCHANT.getValue();// 账户类型（在线商户）
			int merchantPosAccountType = AccountTypeEnum.POS.getValue();// 账户类型（POS商户）
			for (Object merchantObj : statusMerchantList) {
				UserInfo merchant = (UserInfo) merchantObj;
				AccountingUserReport accountingUserReportParam = new AccountingUserReport();
				for (Object accountObj : accountList) {
					Account account = (Account) accountObj;
					int accounttype = account.getAccountType().intValue();
					if (merchant.getUserNo().equals(account.getUserNo()) && (accounttype == merchantOnlineAccountType || accounttype == merchantPosAccountType)) {
						accountingUserReportParam.setCreateTime(new Date());// 创建时间
						accountingUserReportParam.setUserType(0);// 用户类型（0：商户1：会员）
						accountingUserReportParam.setLoginName(merchant.getLoginName());// 登陆名
						accountingUserReportParam.setShowName(merchant.getRealName()==null?"":merchant.getRealName());// 商户签约名
						accountingUserReportParam.setAccountBalance(account.getBalance());// 账户余额
						accountingUserReportParam.setAvailableAmount(AmountUtil.sub(account.getBalance(), account.getUnBalance()));// 可用金额
						accountingUserReportParam.setNotAvailableAmount(account.getUnBalance());// 不可用金额
						accountingUserReportParam.setCanTransferAmount(account.getAvailableSettAmount());// 平台可转出金额
						Double outToPlayAmount = 0D;
						Double transferredOutAmount = 0D;
						if (remittanceRecordlist != null && !remittanceRecordlist.isEmpty()) {
							for (Object remittanceRecordObj : remittanceRecordlist) {
								SettRecord settRecord = (SettRecord) remittanceRecordObj;
								if (account.getAccountNo().equals(settRecord.getAccountNo())) {
									if (settRecord.getSettStatus().intValue() == settingRansferStatus) {
										outToPlayAmount = AmountUtil.add(outToPlayAmount, settRecord.getTradeAmount().doubleValue());
									} else if (settRecord.getSettStatus().intValue() == sucessRansferStatus) {
										transferredOutAmount = AmountUtil.add(outToPlayAmount, settRecord.getTradeAmount().doubleValue());
									}
								}
							}
						}
						accountingUserReportParam.setOutToPlayAmount(outToPlayAmount);// 平台转出待打款金额
						accountingUserReportParam.setTransferredOutAmount(transferredOutAmount);// 累计已转出金额
						accountingUserReportParam.setAcountDate(new Date());// 统计日期
						accountingUserReportList.add(accountingUserReportParam);
						accountingUserReportFacade.create(accountingUserReportParam);
						//break;
					}

				}

			}
		}
		if (statusMemberList != null && !statusMemberList.isEmpty()) {
			int memberOnlineAccountType = AccountTypeEnum.CUSTOMER.getValue();
			for (Object memberInfoObj : statusMemberList) {
				UserInfo memberInfo = (UserInfo) memberInfoObj;
				AccountingUserReport accountingUserReportParam = new AccountingUserReport();
				for (Object accountObj : accountList) {
					Account account = (Account) accountObj;
					int accounttype = account.getAccountType().intValue();
					if (account.getUserNo().equals(memberInfo.getUserNo())&& accounttype == memberOnlineAccountType) {
						accountingUserReportParam.setCreateTime(new Date());// 创建时间
						accountingUserReportParam.setUserType(1);// 用户类型（0：商户1：会员）
						accountingUserReportParam.setLoginName(memberInfo.getLoginName());// 登录名
						accountingUserReportParam.setShowName((memberInfo.getRealName()==null)?"":memberInfo.getRealName());// 会员真实姓名
						accountingUserReportParam.setAccountBalance(account.getBalance());// 账户余额
						accountingUserReportParam.setAvailableAmount(AmountUtil.sub(account.getBalance(), account.getUnBalance()));// 可用金额
						accountingUserReportParam.setNotAvailableAmount(account.getUnBalance());// 不可用金额
						accountingUserReportParam.setCanTransferAmount(account.getAvailableSettAmount());// 平台可转出金额
						Double outToPlayAmount = 0D;
						Double transferredOutAmount = 0D;
						if (remittanceRecordlist != null && !remittanceRecordlist.isEmpty()) {
							for (Object remittanceRecordObj : remittanceRecordlist) {
								SettRecord settRecord = (SettRecord) remittanceRecordObj;
								if (account.getAccountNo().equals(settRecord.getAccountNo())) {
									if (settRecord.getSettStatus().intValue() == settingRansferStatus) {
										outToPlayAmount = AmountUtil.add(outToPlayAmount, settRecord.getTradeAmount().doubleValue());
									} else if (settRecord.getSettStatus().intValue() == sucessRansferStatus) {
										transferredOutAmount = AmountUtil.add(outToPlayAmount, settRecord.getTradeAmount().doubleValue());
									}
								}
							}
						}
						accountingUserReportParam.setOutToPlayAmount(outToPlayAmount);// 平台转出待打款金额
						accountingUserReportParam.setTransferredOutAmount(transferredOutAmount);// 累计已转出金额
						accountingUserReportParam.setAcountDate(new Date());// 统计日期
						accountingUserReportList.add(accountingUserReportParam);
						accountingUserReportFacade.create(accountingUserReportParam);
						//break;
					}
				}
			}
		}
	}
}
