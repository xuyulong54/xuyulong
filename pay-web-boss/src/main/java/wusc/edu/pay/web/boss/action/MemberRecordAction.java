package wusc.edu.pay.web.boss.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.remit.service.RemitBankTypeFacade;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.enums.SettModeTypeEnum;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;
import wusc.edu.pay.facade.settlement.service.SettQueryFacade;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.enums.PaymentRecordStatusEnum;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.service.PaymentQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * 会员交易记录
 * 
 * @author huangbin
 * @date 2013-10-11
 */
@Scope("prototype")
public class MemberRecordAction extends BossBaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private PaymentQueryFacade paymentQueryFacade;
	@Autowired
	private SettQueryFacade settQueryFacade;
	@Autowired
	private RemitBankTypeFacade remitBankTyoeFacade;

	/**
	 * 会员提现记录查询 listMemberWithdrawRecord: <br/>
	 * 
	 * @return
	 */
	@Permission("member:withdrawrecord:view")
	public String listMemberWithdrawRecord() {
		String startDate = getString("startDate");
		String endDate = getString("endDate");
		// 对时间进行校验
		if (startDate != null && endDate != null) {
			// 取得两个日期之间的日数
			long a = DateUtils.daysBetween(DateUtils.toSqlTimestamp(startDate), DateUtils.toSqlTimestamp(endDate));

			if (a < 0) {
				return this.operateError("开始时间不能大于结束时间");
			}
		} else if ((startDate != null && endDate == null) || (startDate == null && endDate != null)) {
			return this.operateError("不能只输入一个时间查询");
		}
		// 查询参数集合
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("batchNo", getString("batchNo")); // 打款批次号
		paramMap.put("bankAccountName", getString("bankAccountName")); // 收款人
		paramMap.put("beginTime", startDate); // 提交开始时间
		paramMap.put("endTime", endDate); // 提交结束时间
		paramMap.put("settStatus", getString("settStatus"));
		paramMap.put("settMode", SettModeTypeEnum.MEMBER_WITHDRAW.getValue()); // 会员提现

		super.pageBean = settQueryFacade.listPageSettRecord(getPageParam(), paramMap);

		// 获取银行列表
		this.putData("bankList", remitBankTyoeFacade.listAll());
		this.pushData(paramMap); // 回显查询条件
		this.putData("SettRecordStatusEnum", SettRecordStatusEnum.toList());
		this.putData("remitFail", SettRecordStatusEnum.FAIL_RETURN.getValue());
		this.pushData(pageBean);
		return "listWithdrawRecord";
	}
	
	/**
	 * 查看
	 */
	@Permission("member:paymentrecord:view")
	public String viewMemberRecordUI(){
		String trxNo = getString("trxNo");
		PaymentRecord paymentRecord = paymentQueryFacade.getPaymentRecordByMerchantNo_orderNo_trxNo_bankOrderNo_status(null, null, trxNo, null, null);
		pushData(paymentRecord);
		return "viewMemberRecordUI";
	}

	/***
	 * 提现详情页面
	 * 
	 * @return
	 */
	@Permission("member:withdrawrecord:view")
	public String reviewWithDrawRecord() {
		String batchNo = getString("batchNo");
		String accountNo = getString("accountNo");
		SettRecord settRecord = settQueryFacade.getSettRecordByBatchNoAndAccountNo(batchNo, accountNo);
		this.pushData(settRecord);
		this.putData("bankList", remitBankTyoeFacade.listAll());
		this.putData("SettRecordStatusEnum", SettRecordStatusEnum.toList());
		return "reviewWithDrawRecord";
	}

	/****
	 * 查询订单交易记录并分页
	 * 
	 * @return listPaymentRecord or operateError.
	 */
	@Permission("member:paymentrecord:view")
	public String listMemberRecord() {
		String beginDate = getString("beginDate");
		String endDate = getString("endDate");
		// 对时间进行校验
		if (beginDate != null && endDate != null) {
			// 取得两个日期之间的日数
			long a = DateUtils.daysBetween(DateUtils.toSqlTimestamp(beginDate), DateUtils.toSqlTimestamp(endDate));

			if (a < 0) {
				return this.operateError("开始时间不能大于结束时间");
			}
		} else if ((beginDate != null && endDate == null) || (beginDate == null && endDate != null)) {
			return this.operateError("不能只输入一个时间查询");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("payerName", getString("payerName"));// 付款方用户名
		map.put("merchantName", getString("merchantName")); // 收款方用户名
		map.put("trxNo", getString("trxNo"));// 交易流水号
		map.put("status", getString("status"));// 支付状态
		map.put("beginDate", beginDate); // 开始时间
		map.put("endDate", endDate); // 结束时间
		map.put("bizType", TradeBizTypeEnum.ONLINE_ACQUIRING.getValue());// 业务类型
		map.put("payerAccountType", AccountTypeEnum.CUSTOMER.getValue());// 用户类型，
																			// 只是会员

		if (beginDate != null && !"".equals(beginDate)) {
			Long s1 = Long.valueOf(beginDate.replace("-", ""));
			Long e1 = Long.valueOf(endDate.replace("-", ""));
			Date sDate = new Date(s1);
			Date eDate = new Date(e1);
			if (eDate.getTime() < sDate.getTime()) {
				return operateError("结束时间必须大于开始时间");
			}
		}

		super.pageBean = paymentQueryFacade.queryPaymentRecordListPage(getPageParam(), map);
		pushData(pageBean);
		putData("proMap", map);

		// 交易状态
		ActionContext.getContext().put("paymentRecordStatus", PaymentRecordStatusEnum.toList());
		return "listMemberRecord";
	}

	@SuppressWarnings("unused")
	private Map<String, Object> getParameterFromJSPFrom() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createTime", getString("createTime"));// 创建时间
		map.put("userName", getString("userName"));// 商户名称
		map.put("bankTrxNo", getString("bankTrxNo"));// 银行流水号
		map.put("orderNo", getString("orderNo"));// 商户订单号
		map.put("trxNo", getString("trxNo"));// 交易流水号
		map.put("beginDate", getString("beginDate"));
		map.put("endDate", getString("endDate"));
		map.put("status", getString("status"));// 支付状态
		map.put("trxType", getString("trxType"));// 业务类型
		return map;
	}

}
