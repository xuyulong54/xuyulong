package wusc.edu.pay.timer.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.facade.trade.enums.PaymentRecordStatusEnum;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.exception.TradeBizException;
import wusc.edu.pay.timer.report.achieve.AddAccountingReservesBankReport;
import wusc.edu.pay.timer.report.achieve.AddOnlineMemberReport;
import wusc.edu.pay.timer.report.util.ReportDataQuery;


/**
 * 
 * @描述: 报表业务类（在此统一引用和管理各报表类） .
 * @作者: WuShuicheng.
 * @创建: 2014-5-7,上午9:39:24
 * @版本: V1.0
 *
 */
@Component("reportBiz")
public class ReportBiz {
	@Autowired
	private ReportDataQuery reportDataQuery;
	@Autowired
	private AddOnlineMemberReport addOnlineMemberReport;
	@Autowired
	private AddAccountingReservesBankReport addAccountingReservesBankReport;
	
	
	/**
	 * 执行报表业务逻辑.
	 */
	public void executeReport() throws TradeBizException {
		// 交易数据
		List<String> bizTypes = new ArrayList<String>();
		bizTypes.add(String.valueOf(TradeBizTypeEnum.RECHARGE.getValue()));
		bizTypes.add(String.valueOf(TradeBizTypeEnum.ONLINE_ACQUIRING.getValue()));
		Map<String, Object> paymentMap = new HashMap<String, Object>();
		paymentMap.put("beginDate", reportDataQuery.beginTime);
		paymentMap.put("endDate", reportDataQuery.endTime);
		paymentMap.put("status", PaymentRecordStatusEnum.SUCCESS.getValue());
		paymentMap.put("bizTypes", bizTypes);
		List<Object> paymentRecordList = reportDataQuery.getPaymentRecordList(paymentMap);
		
		//=====>>会计备付金报表
		addAccountingReservesBankReport.execute();
		
		//=====>>在线会员数据》》》》》》》测试通过
		addOnlineMemberReport.execute(paymentRecordList);
	}

}
	
