package wusc.edu.pay.timer.report.achieve;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.facade.bank.entity.BankAccount;
import wusc.edu.pay.facade.bank.service.BankAccountFacade;
import wusc.edu.pay.facade.report.entity.AccountingReservesBankReport;
import wusc.edu.pay.facade.report.service.AccountingReservesBankReportFacade;
import wusc.edu.pay.timer.report.util.ReportDataQuery;


/**
 * 
 * @描述: 会计备付金报表.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-4-29, 下午2:08:59 .
 * @版本: V1.0 .
 */
@Component("addAccountingReservesBankReport")
public class AddAccountingReservesBankReport {	
	@Autowired
	private ReportDataQuery reportDataQuery;
	@Autowired
	private AccountingReservesBankReportFacade accountingReservesBankReportFacade;
	@Autowired
	private BankAccountFacade bankAccountFacade;
	private static Log log = LogFactory.getLog(AddAccountingReservesBankReport.class);

	@SuppressWarnings("rawtypes")
	public void execute() {
//		
//		// 会计备付金明细记录
//		Map<String, Object> bankjnRegistDetailMap = new HashMap<String, Object>();
//        String accountTitleNo = "1002";//备付金银行的科目编号
//        Integer dailyType = 2;//账户
//        Integer accountTitleLevel = 4;
//         List adjustList = accountingAccountTitleNotesFacade.listAdjust(accountTitleNo);
//			List<AccountingReservesBankReport> AccountingReservesBankReportList = new ArrayList<AccountingReservesBankReport>();
//			if (adjustList.size() > 0) {
//				for (int i = 0; i < adjustList.size(); i++) {//遍历这个科目下的所有核算项目编号,每个核算科目做一次查询
//						bankjnRegistDetailMap.put("beginDate", reportDataQuery.beginTime);
//				        bankjnRegistDetailMap.put("endDate", reportDataQuery.endTime);
//				        bankjnRegistDetailMap.put("accountTitleNo", accountTitleNo);
//				        bankjnRegistDetailMap.put("dailyType", dailyType);
//				        bankjnRegistDetailMap.put("accountTitleLevel", accountTitleLevel);
//						List<Object> bankjnRegistDetailList = reportDataQuery.getReservesBankList(bankjnRegistDetailMap);//查出这个核算科目
//						/**
//						 * 循环遍历获得备付金银行详细信息，放入备付金银行报表实体类中
//						 */
//						if (bankjnRegistDetailList.size() > 0) {
//							for (Object bankjnReDetailObject:bankjnRegistDetailList) {
//								try {
//									AccountingAccountTitleDailyAccount account = new AccountingAccountTitleDailyAccount();
//									AccountingReservesBankReport AccountingReservesBankReportParam = new AccountingReservesBankReport();
//									account = (AccountingAccountTitleDailyAccount) bankjnReDetailObject;
//									Date journalDate = account.getAccountingDate();// 记账时间
//									String bankNoString = account.getAdjustAccountNo();
//									BankAccount bankAccount = bankAccountFacade.getByBankAccount(bankNoString);
//									String openBankName = bankAccount.getOpenBank();
//									String accountNature = bankAccount.getAccountNature().toString();
////									switch (bankAccount.getAccountNature()) {
////									case 1:accountNature = "备付金存管账户";
////									case 2:accountNature = "自有资金账户";
////									case 3:accountNature = "备付金收付账户 ";
////									case 4:accountNature = "备付金汇缴账户";
////									}
//									double amount = Double.valueOf(account.getDebitAmount().toString()); // 发生额
//									double balance = Double.valueOf(account.getDebitBalanceAfter().toString()); //余额 
////									double balance = bankAccount.getBalance();
//									AccountingReservesBankReportParam.setOpenBankName(openBankName);
//									AccountingReservesBankReportParam.setAccountNature(accountNature);
//									AccountingReservesBankReportParam.setBalance(balance);
//									AccountingReservesBankReportParam.setLastBalance(0d);
//
//									AccountingReservesBankReportParam.setJournalDate(journalDate);
//									AccountingReservesBankReportParam.setChangeAmount(amount);
//									AccountingReservesBankReportParam.setAcountDate(new Date());
//									
//									accountingReservesBankReportFacade.create(AccountingReservesBankReportParam);				
//									AccountingReservesBankReportList.add(AccountingReservesBankReportParam);
//								} catch (Exception e) {
//									log.error("统计备付金银行出错：", e);
//									e.printStackTrace();
//									continue;
//								}
//							}	
//						}
//						
//				}	
//			}
			
	}

}
