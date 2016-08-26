package wusc.edu.pay.web.portal.action.merchant;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.export.DataField;
import wusc.edu.pay.common.utils.export.ExportDataSource;
import wusc.edu.pay.common.utils.export.excel.ExcelDataExportor;
import wusc.edu.pay.common.utils.export.excel.MODE;
import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.notify.entity.NotifyRecord;
import wusc.edu.pay.facade.notify.enums.NotifyStatusEnum;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;
import wusc.edu.pay.facade.trade.enums.OrderStatusEnum;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.enums.TradePaymentTypeEnum;
import wusc.edu.pay.web.portal.base.MerchantLogonAction;
import wusc.edu.pay.web.portal.exceptions.PortalMerchantException;


/**
 * 
 * @描述: 商户收款交易.
 * @作者: LiLiqiong,WuShuicheng.
 * @创建: 2014-6-5,上午11:53:02
 * @版本: V1.0
 * 
 */
public class ReceivePaymentOrderAction extends MerchantLogonAction {
	private static final long serialVersionUID = 1L;

	// private static final Log LOG =
	// LogFactory.getLog(SourcePaymentRecordAction.class);

	// 支付记录 Begin===================================================
	/**
	 * 商户收款交易记录查询
	 * 
	 * @return
	 */
	@Permission("Receive:PaymentOrder:List")
	public String listReceivePaymentOrder() {

		String merchantOrderNo = getString("merchantOrderNo"); // 商户订单号
		String beginDate = getString("beginDate"); // 开始时间
		beginDate = ValidateUtils.isEmpty(beginDate) ? null : beginDate;
		String endDate = getString("endDate"); // 结束时间
		endDate = ValidateUtils.isEmpty(endDate) ? null : endDate;
		String status = getString("status"); // 支付状态
		String paymentType = getString("paymentType"); // 交易业务类型

		// 回显查询条件
		super.pushData(this.getParamMap());

		pageBean = super.receivePaymentOrderListForPage(getPageParam(), merchantOrderNo, beginDate, endDate, status, paymentType);
		pushData(pageBean);

		// 枚举
		putData("tradePaymentTypeList", TradePaymentTypeEnum.values());// 支付方式类型
		putData("TradeBizTypeEnum", TradeBizTypeEnum.toMap());// 支付方式类型
		putData("orderStatusList", OrderStatusEnum.values());// 交易状态集合

		return "listReceivePaymentOrder";

	}

	/**
	 * 商户补单
	 */
	@Permission("Receive:PaymentOrder:List")
	public void replacementOrder() {
		String params = getString("params"); // 参数

		String noNotifyTrxNoStr = super.replacementOrder(params);

		getOutputMsg().put("status", noNotifyTrxNoStr.toString().trim().length() == 0 ? 0 : 1);
		getOutputMsg().put("noNotifyTrxNo", noNotifyTrxNoStr.substring(0, noNotifyTrxNoStr.length() - 1));
		this.addUserLog(OpeStatusEnum.SUCCESS, "商户补单.参数：" + params);
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));

	}

	/**
	 * 撤消交易
	 * 
	 * @return
	 */
	@Permission("Receive:PaymentOrder:List")
	public void cancelPaymentRecord() {
		String merchantOrderNo = getString("merchantOrderNo");
		PaymentOrder paymentOrder = super.getPaymentOrderBy_merchantNo_orderNo_status(getCurrentUserInfo().getUserNo(), merchantOrderNo,
				OrderStatusEnum.CREATED.getValue());
		if (ValidateUtils.isEmpty(paymentOrder)) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误");
		}
		String message = "商户订单号为：" + merchantOrderNo + "的记录撤消";
		try {
			super.cancelOrder(merchantOrderNo, getCurrentUserInfo().getUserNo(), "");
			getOutputMsg().put("status", "SUCC");
			getOutputMsg().put("paystatus", OrderStatusEnum.CANCELED.getDesc());
			getOutputMsg().put("message", message + "成功");
			this.addUserLog(OpeStatusEnum.SUCCESS, String.format("撤消交易成功，订单号为%s", message));
		} catch (Exception e) {
			getOutputMsg().put("status", "FAIL");
			getOutputMsg().put("message", message + "失败," + e.getMessage());
			this.addUserLog(OpeStatusEnum.FAIL, String.format("撤消交易失败，订单号为%s", message));
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 收款交易记录详情
	 * 
	 * @return
	 */
	@Permission("Receive:PaymentOrder:List")
	public String viewReceivePaymentOrder() {

		String merchantOrderNo = getString("merchantOrderNo"); // 商户订单号

		PaymentOrder paymentOrder = this.receiveGetPaymentOrder(merchantOrderNo);

		if (ValidateUtils.isEmpty(paymentOrder)) {
			throw new PortalMerchantException(PortalMerchantException.ORDER_IS_NOT_EXIT, "商户订单不存在");
		}
		putData("notifyRecord",
				super.getNotifyByMerchantNoAndMerchantOrderNo(paymentOrder.getMerchantNo(), paymentOrder.getMerchantOrderNo()));
		putData("notifyStatusList", NotifyStatusEnum.values());

		// 查询通知状态
		NotifyRecord vo = super.getNotifyByMerchantNoAndMerchantOrderNo(paymentOrder.getMerchantNo(), paymentOrder.getMerchantOrderNo());
		if (vo != null) {
			this.putData("notifyStatus", vo.getStatus());
		} else {
			this.putData("notifyStatus", 0);
		}

		putData("paymentOrder", paymentOrder);

		String orderNo = paymentOrder.getMerchantOrderNo();
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
		String beginDate = sDate.format(paymentOrder.getCreateTime());
		String endDate = sDate.format(new Date());

//		// 退款记录
//		List<Object> refundList = receiveRefundRecordListForPage(getPageParam(), orderNo, null, null, beginDate, endDate, null, null, null)
//				.getRecordList();
//
//		putData("list", refundList);
//		putData("refundStatusList", RefundStatusEnum.values());

		return "viewReceivePaymentOrder";
	}

	/**
	 * 在线导出EXCEL
	 * 
	 * @throws IOException
	 */
	@Permission("Receive:PaymentOrder:List")
	public void exportReceivePaymentOrderToExcel() throws IOException {

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String filename = "PaymentOrder_" + sdf.format(new Date());
		this.getHttpResponse().setHeader("Content-disposition",
				"attachment; filename=" + new String((filename + ".xls").getBytes("ISO-8859-1"), "UTF-8"));
		OutputStream os = getHttpResponse().getOutputStream();
		// final String stType = getString("stType");// 支出/收入
		DataField[] dataFields = new DataField[10];
		dataFields[0] = new DataField("序号", "no");
		dataFields[1] = new DataField("创建日期", "createTime");
		dataFields[2] = new DataField("支付时间", "paymentTime");
		dataFields[3] = new DataField("支付方式类型", "paymentType");
		dataFields[4] = new DataField("商户订单号", "merchantOrderNo");
		dataFields[5] = new DataField("交易状态", "status");
		dataFields[6] = new DataField("订单金额", "orderAmount");
		dataFields[7] = new DataField("手续费", "sourceFee");
		dataFields[8] = new DataField("实收金额", "paymentAmount");//
		dataFields[9] = new DataField("是否退款", "isRefund");//

		final String merchantOrderNo = getString("merchantOrderNo"); // 商户订单号
		final String beginDate = getString("beginDate");
		final String endDate = getString("endDate");
		// final String userNo = this.getCurrentUser().getUserNo();
		final String status = getString("status");
		final String paymentType = getString("paymentType");

		PageParam pageParam = new PageParam(1, 10000);
		// pageBean = paymentQueryFacade.queryPaymentRecordListPage(pageParam,
		// map);
		pageBean = super.receivePaymentOrderListForPage(pageParam, merchantOrderNo, beginDate, endDate, status, paymentType);

		new ExcelDataExportor<Object>(dataFields, new ExportDataSource<Object>() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public List getData() {
				List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
				List<Object> recordList = pageBean.getRecordList();
				if (recordList != null && !recordList.isEmpty()) {
					int temp = 0;
					for (int i = 0; i < recordList.size(); i++) {
						Map<String, Object> mapParam = new HashMap<String, Object>();
						PaymentOrder paymentRecord = (PaymentOrder) recordList.get(i);
						mapParam.put("no", ++temp);
						mapParam.put("createTime", sdf.format(paymentRecord.getCreateTime()));
						mapParam.put(
								"paymentTime",
								ValidateUtils.isEmpty(paymentRecord.getPaySuccessTime()) ? "" : sdf.format(paymentRecord
										.getPaySuccessTime()));
						mapParam.put("paymentType", ValidateUtils.isEmpty(paymentRecord.getPaymentType()) ? "" : TradePaymentTypeEnum
								.getEnum(paymentRecord.getPaymentType()).getDesc());
						mapParam.put("merchantOrderNo", paymentRecord.getMerchantOrderNo());
						mapParam.put("status", OrderStatusEnum.getEnum(paymentRecord.getStatus()).getDesc());
						mapParam.put("orderAmount", paymentRecord.getOrderAmount());
						String paymentAmount = "";
						paymentAmount = "+"
								+ AmountUtil
										.sub(paymentRecord.getOrderAmount().doubleValue(), paymentRecord.getReceiverFee().doubleValue());
						mapParam.put("sourceFee", paymentRecord.getReceiverFee());
						mapParam.put("paymentAmount", paymentAmount);
						mapParam.put("isRefund", new Integer(100).equals(paymentRecord.getIsRefund()) ? "已退" : "未退");
						lists.add(mapParam);
					}
				}
				return lists;
			}
		}, os, MODE.EXCEL).export();

	}

	// 支付记录 End===================================================
}
