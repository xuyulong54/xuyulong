package wusc.edu.pay.web.boss.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.enums.BankCode;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.notify.entity.NotifyRecord;
import wusc.edu.pay.facade.notify.enums.NotifyTypeEnum;
import wusc.edu.pay.facade.notify.service.NotifyFacade;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.enums.OrderFromTypeEnum;
import wusc.edu.pay.facade.trade.enums.PaymentRecordStatusEnum;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.enums.TradePaymentTypeEnum;
import wusc.edu.pay.facade.trade.service.PaymentQueryFacade;
import wusc.edu.pay.facade.trade.service.TransferFacade;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * 支付交易记录
 * 
 * @author liliqiong
 * @date 2013-8-21
 */
@Scope("prototype")
public class PaymentRecordAction extends BossBaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private PaymentQueryFacade paymentQueryFacade;
	@Autowired
	private TransferFacade transferFacade;
	@Autowired
	private NotifyFacade notifyFacade;
	@Autowired
	private UserQueryFacade userQueryFacade; // 商户接口

	/**
	 * 商户跟会员一起充值查询 merchantRechargeRecode: <br/>
	 * 
	 * @return
	 */
	@Permission("merchant:rechargerecode:view")
	public String listMerchantRechargeRecode() {
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
		putData("paymentRecordStatus", PaymentRecordStatusEnum.toList());
		Map<String, Object> map = this.getParameterFromJSPFrom();
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		map.put("merchantOrderNo", getString("merchantOrderNo"));
		map.put("trxNo", getString("trxNo"));
		map.put("status", getString("status"));
		map.put("bizType", TradeBizTypeEnum.RECHARGE.getValue());// 业务类型
		super.pageBean = paymentQueryFacade.queryPaymentRecordListPage(getPageParam(), map);
		pushData(pageBean);
		putData("proMap", map);
		return "listMerchantRechargeRecode";
	}

	/**
	 * 商户跟会员一起转账查询 listMerchantTransferRecord: <br/>
	 * 
	 * @return
	 */
	@Permission("merchant:transferrecord:view")
	public String listMerchantTransferRecord() {
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
		Map<String, Object> map = this.getParameterFromJSPFrom();
		map.put("sourceLoginName", getString("sourceLoginName"));// 收款方账户ID
		map.put("targetLoginName", getString("targetLoginName"));// 付款方账户ID
		map.put("trxNo", getString("trxNo")); // 交易流水号
		map.put("status", getString("status"));
		super.pageBean = transferFacade.queryTransferRecordListPage(getPageParam(), map);
		pushData(pageBean);
		putData("proMap", map);
		putData("paymentRecordStatus", PaymentRecordStatusEnum.toList());
		return "listMerchantTransferRecord";
	}

	private Map<String, Object> getParameterFromJSPFrom() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createTime", getString("createTime"));// 创建时间
		map.put("bankTrxNo", getString("bankTrxNo"));// 银行流水号
		map.put("orderNo", getString("orderNo"));// 商户订单号
		map.put("trxNo", getString("trxNo"));// 交易流水号
		map.put("beginDate", getString("beginDate"));
		map.put("endDate", getString("endDate"));
		map.put("status", getString("status"));// 支付状态
		map.put("trxType", getString("trxType"));// 业务类型
		return map;
	}

	/**
	 * 查询交易记录并分页
	 * 
	 * @return listPaymentRecord or operateError.
	 */
	@SuppressWarnings("rawtypes")
	@Permission("merchant:paymentrecord:view")
	public String listPaymentRecord() {
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

		if (beginDate != null && !"".equals(beginDate)) {
			Long s1 = Long.valueOf(beginDate.replace("-", ""));
			Long e1 = Long.valueOf(endDate.replace("-", ""));
			Date sDate = new Date(s1);
			Date eDate = new Date(e1);
			if (eDate.getTime() < sDate.getTime()) {
				return operateError("结束时间必须大于开始时间");
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantOrderNo", getString("merchantOrderNo"));//商户订单号
		map.put("merchantNo", getString("merchantNo"));//商户编号
		map.put("status", getString("status"));//交易状态
		map.put("trxNo", getString("trxNo"));//交易流水号
		map.put("bankOrderNo", getString("bankOrderNo")); //银行订单号
		map.put("bankTrxNo", getString("bankTrxNo")); //银行流水号
		map.put("bizType", getString("bizType"));//业务类型
		map.put("paymentType", getString("paymentType"));//支付方式
		map.put("payInterfaceCode", getString("payInterfaceCode"));
		
		// 银行订单号
		map.put("beginDate", beginDate); // 开始时间
		map.put("endDate", endDate); // 结束时间
		super.pageBean = paymentQueryFacade.queryPaymentRecordListPage(getPageParam(), map);
		Double order_Amount = 0D;
		Double income_Amount = 0D;
		Double merchantFee_Amount = 0D;
		Double refund_Amount = 0D;
		List listOrders = pageBean.getRecordList();
		for (int i = 0; i < listOrders.size(); i++) {
			PaymentRecord maps = (PaymentRecord) listOrders.get(i);
			Double Amount = Double.valueOf(maps.getOrderAmount().doubleValue()) - Double.valueOf(maps.getReceiverFee().doubleValue());
			income_Amount += Amount;
			order_Amount += Double.valueOf(maps.getOrderAmount().doubleValue());
			merchantFee_Amount += Double.valueOf(maps.getReceiverFee().doubleValue());
			refund_Amount += Double.valueOf(maps.getSuccessRefundAmount().doubleValue());
		}
		putData("order_Amount", order_Amount);
		putData("income_Amount", income_Amount);
		putData("merchantFee_Amount", merchantFee_Amount);
		putData("refund_Amount", refund_Amount);
		pushData(pageBean);
		putData("proMap", map);
		putData("tradeBizTypeList", TradeBizTypeEnum.toList());// 业务类型集合
		putData("tradePaymentTypeList", TradePaymentTypeEnum.toList());
		putData("paymentRecordStatus", PaymentRecordStatusEnum.toList());
		return "listPaymentRecord";
	}

	/**
	 * 查询支付订单并分页
	 * 
	 * @return listPaymentRecord or operateError.
	 */
	@Permission("merchant:paymentorder:view")
	public String listPaymentOrder() {
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

		if (beginDate != null && !"".equals(beginDate)) {
			Long s1 = Long.valueOf(beginDate.replace("-", ""));
			Long e1 = Long.valueOf(endDate.replace("-", ""));
			Date sDate = new Date(s1);
			Date eDate = new Date(e1);
			if (eDate.getTime() < sDate.getTime()) {
				return operateError("结束时间必须大于开始时间");
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantOrderNo", getString("merchantOrderNo"));	// 商户订单号
		map.put("merchantNo", getString("merchantNo"));// 商户编号
		map.put("status", getString("status"));// 支付状态
		
		map.put("merchantNo", getString("merchantNo")); // 收款方商编
		map.put("successTrxNo", getString("successTrxNo")); // 付款方商编
		//map.put("merchantName", getString("merchantName")); // 收款方姓名
		//map.put("payerName", getString("payerName")); // 付款方姓名
		map.put("bizType", getString("bizType"));// 业务类型
		map.put("beginDate", beginDate); // 开始时间
		map.put("endDate", endDate); // 结束时间
		map.put("paymentType", getString("paymentType")); // 支付方式
		
		super.pageBean = paymentQueryFacade.queryPaymentOrderListForPage(getPageParam(), map);
		pushData(pageBean);
		putData("proMap", map);
		putData("tradeBizTypeList", TradeBizTypeEnum.toList());// 业务类型集合
		putData("paymentRecordStatus", PaymentRecordStatusEnum.toList());
		putData("orderFromTypeEnum", OrderFromTypeEnum.toList()); // 订单来源枚举.
		putData("tradeBizTypeEnum", TradeBizTypeEnum.toList()); // 交易业务类型
		putData("tradePaymentTypeEnum", TradePaymentTypeEnum.toList()); // 支付方式类型
		
		return "listPaymentOrder";
	}

	/**
	 * 去新增页面
	 * 
	 * @return operateError or addPaymentRecordUI.
	 */
	public String addPaymentRecordUI() {
		Map<String, String> mapBankCode = BankCode.toStringMap();
		TradePaymentTypeEnum[] fields = TradePaymentTypeEnum.values();
		Map<String, Integer> selectMap = new HashMap<String, Integer>();
		for (TradePaymentTypeEnum field : fields) {
			String fieldName = field.getDesc();
			Integer fieldValue = field.getValue();
			selectMap.put(fieldName, fieldValue);
		}
		ActionContext.getContext().put("selectMap", selectMap);
		ActionContext.getContext().put("mapBankCode", mapBankCode);
		return "addPaymentRecordUI";

	}

	/**
	 * 去修改页面
	 * 
	 * @return operateError or editPaymentRecordUI.
	 */
	@Permission("merchant:paymentrecord:view")
	public String viewPaymentRecordUI() {
		String trxNo = getString("trxNo");
		if (StringUtil.isEmpty(trxNo)) {
			return operateError("交易流水号为空");
		}
		PaymentRecord paymentRecord = paymentQueryFacade.getPaymentRecordByMerchantNo_orderNo_trxNo_bankOrderNo_status(null, null, trxNo,
				null, null);
		if (paymentRecord == null) {
			return operateError("该交易记录不存在");
		}
		Map<String, String> mapBankCode = BankCode.toStringMap();
		TradePaymentTypeEnum[] fields = TradePaymentTypeEnum.values(); // 交易类型枚举
		Map<String, Integer> selectMap = new HashMap<String, Integer>();
		for (TradePaymentTypeEnum field : fields) {
			String fieldName = field.getDesc();
			Integer fieldValue = field.getValue();
			selectMap.put(fieldName, fieldValue);
		}
		UserInfo merchant = userQueryFacade.getUserInfoByUserNo(paymentRecord.getMerchantNo());

		// 查询通知状态
		NotifyRecord vo = notifyFacade.getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(paymentRecord.getMerchantNo(), paymentRecord.getMerchantOrderNo(), NotifyTypeEnum.MERCHANT.getValue());
		if (vo != null) {
			this.putData("notifyStatus", vo.getStatus());
		}

		this.putData("selectMap", selectMap);
		//this.putData("RefundStatusEnum", RefundStatusEnum.values());
		this.putData("mapBankCode", mapBankCode);
		this.pushData(paymentRecord);
		this.putData("merchantName", merchant.getRealName()); // 保存商户信息
		//this.putData("list", new ArrayList<RefundRecord>());
		this.putData("view", getString("view"));
		this.putData("trxTypeEnums", TradePaymentTypeEnum.values());
		this.putData("trxTypeDesc", TradePaymentTypeEnum.getEnum(paymentRecord.getPaymentType()).getDesc());
		return "viewPaymentRecordUI";
	}

	/**
	 * 去修改页面
	 * 
	 * @return operateError or editPaymentRecordUI.
	 */
	@Permission("merchant:paymentorder:view")
	public String viewPaymentOrderUI() {
		String merchantNo = getString("merchantNo");
		String merchantOrderNo = getString("merchantOrderNo");
		if (StringUtil.isEmpty(merchantOrderNo)) {
			return operateError("支付订单号为空");
		}
		PaymentOrder paymentOrder = paymentQueryFacade.getPaymentOrderBy_merchantNo_orderNo_status(merchantNo, merchantOrderNo, null);
		if (paymentOrder == null) {
			return operateError("该支付订单不存在");
		}
		Map<String, String> mapBankCode = BankCode.toStringMap();
		TradePaymentTypeEnum[] fields = TradePaymentTypeEnum.values(); // 交易类型枚举
		Map<String, Integer> selectMap = new HashMap<String, Integer>();
		for (TradePaymentTypeEnum field : fields) {
			String fieldName = field.getDesc();
			Integer fieldValue = field.getValue();
			selectMap.put(fieldName, fieldValue);
		}
		/*
		 * List<RefundRecord> list = refundQueryFacade.
		 * listRefundRecordByMerchantNo_orgTrxNo_orgMerchantOrderNo
		 * (paymentOrder.getMerchantNo(), null,
		 * paymentOrder.getMerchantOrderNo());
		 */
		UserInfo merchant = userQueryFacade.getUserInfoByUserNo(paymentOrder.getMerchantNo());

		// 查询通知状态
		NotifyRecord vo = notifyFacade.getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(paymentOrder.getMerchantNo(), paymentOrder.getMerchantOrderNo(),NotifyTypeEnum.MERCHANT.getValue());

		if (vo != null) {
			this.putData("notifyStatus", vo.getStatus());
		}

		this.putData("selectMap", selectMap);
		//this.putData("RefundStatusEnum", RefundStatusEnum.values());
		this.putData("mapBankCode", mapBankCode);
		this.pushData(paymentOrder);
		this.putData("merchantName", merchant.getRealName()); // 保存商户信息
		/* this.putData("list", list); */
		this.putData("view", getString("view"));
		this.putData("tradeBizTypeEnums", TradeBizTypeEnum.values());
		return "viewPaymentOrderUI";
	}


}
