package wusc.edu.pay.timer.report.achieve;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.report.entity.OnlineMemberReport;
import wusc.edu.pay.facade.report.service.OnlineMemberReportFacade;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.user.entity.MemberInfo;
import wusc.edu.pay.timer.report.util.ReportDataQuery;


/**
 * 添加在线会员数据
 * 
 * @author lichao
 * 
 *         测试：13570235165---1---10---2 select COUNT(*) FROM
 *         TBL_PAY_PAYMENTRECORD WHERE USERTYPE = '3' AND TRXTYPE ='6001' AND
 *         USERID IN ( SELECT id FROM TBL_MEMBER_INFO WHERE
 *         LOGINNAME='13570235165' )
 */
@Component("addOnlineMemberReport")
public class AddOnlineMemberReport {
	private static Log log = LogFactory.getLog(AddOnlineMemberReport.class);
	@Autowired
	private OnlineMemberReportFacade onlineMemberReportFacade;
	@Autowired
	private ReportDataQuery reportDataQuery;

	public void execute(List<Object> successPaymentRecordList) {
		try {
			Map<String, Object> memberMap = new HashMap<String, Object>();
			List<Object> memberList = reportDataQuery.getMemberList(memberMap);// 会员数据

			Set<String> bizTypeSet = new HashSet<String>();// 交易类型
			Set<String> memberNoSet = new HashSet<String>();// 会员登录名
			if (successPaymentRecordList == null || successPaymentRecordList.isEmpty() || memberList == null || memberList.isEmpty()) {
				return;
			}
			List<OnlineMemberReport> onlineMemberReportParamList = new ArrayList<OnlineMemberReport>();
			for (Object memberObj : memberList) {
				MemberInfo memberInfo = (MemberInfo) memberObj;
				for (Object paymentObj : successPaymentRecordList) {
					PaymentRecord paymentRecord = (PaymentRecord) paymentObj;
					if (memberInfo.getMemberNo().equals(paymentRecord.getPayerUserNo())
							&& paymentRecord.getPayerAccountType().intValue() == AccountTypeEnum.CUSTOMER.getValue()) {
						
						memberNoSet.add(memberInfo.getMemberNo());
						bizTypeSet.add(String.valueOf(paymentRecord.getBizType()));
						OnlineMemberReport onlineMemberReportParam = new OnlineMemberReport();
						onlineMemberReportParam.setRealName(memberInfo.getRealName());
						onlineMemberReportParam.setLonginName(memberInfo.getMemberNo());
						onlineMemberReportParam.setTrxType(paymentRecord.getBizType());
						onlineMemberReportParam.setTransactionAmount(paymentRecord.getOrderAmount().doubleValue());
						onlineMemberReportParam.setTransactionNumber(1);
						onlineMemberReportParam.setTotalFee(paymentRecord.getPlatIncome().doubleValue());
						onlineMemberReportParam.setAcountDate(new Date());
						onlineMemberReportParamList.add(onlineMemberReportParam);
					}
				}
			}
			if (onlineMemberReportParamList == null || onlineMemberReportParamList.isEmpty()) {
				return;
			}

			List<OnlineMemberReport> list = new ArrayList<OnlineMemberReport>();
			for (String bizType : bizTypeSet) {
				for (String memNo : memberNoSet) {
					
					OnlineMemberReport onlineMemberReportParam = new OnlineMemberReport();
					String realName = "";
					String longinName = "";
					Double transactionAmount = 0d;// 交易金额
					Integer transactionNumber = 0;// 交易笔数
					Double totalFee = 0d;// 总手续费
					int stemp = 0;
					for (int i = 0; i < onlineMemberReportParamList.size(); i++) {
						if (Integer.parseInt(bizType) == onlineMemberReportParamList.get(i).getTrxType().intValue()
								&& memNo.equals(onlineMemberReportParamList.get(i).getLonginName())) {
							
							transactionAmount = AmountUtil.add(transactionAmount, onlineMemberReportParamList.get(i).getTransactionAmount());
							transactionNumber += onlineMemberReportParamList.get(i).getTransactionNumber();
							totalFee = AmountUtil.add(totalFee, onlineMemberReportParamList.get(i).getTotalFee());
							
							if (stemp == 0) {
								realName = onlineMemberReportParamList.get(i).getRealName();
								longinName = onlineMemberReportParamList.get(i).getLonginName();
							}
							stemp++;
						}
					}
					if (stemp != 0) {
						onlineMemberReportParam.setRealName(realName);
						onlineMemberReportParam.setLonginName(longinName);
						onlineMemberReportParam.setTransactionAmount(transactionAmount);
						onlineMemberReportParam.setTransactionNumber(transactionNumber);
						onlineMemberReportParam.setTrxType(Integer.valueOf(bizType));
						onlineMemberReportParam.setTotalFee(totalFee);
						onlineMemberReportParam.setAcountDate(new Date());
						list.add(onlineMemberReportParam);
						onlineMemberReportFacade.create(onlineMemberReportParam);
					}

				}
			}
		} catch (Exception e) {
			log.error("添加在线会员数据"+e);
		}
	}

}
