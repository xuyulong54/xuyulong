package wusc.edu.pay.web.portal.action.merchant;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.export.DataField;
import wusc.edu.pay.common.utils.export.ExportDataSource;
import wusc.edu.pay.common.utils.export.excel.ExcelDataExportor;
import wusc.edu.pay.common.utils.export.excel.MODE;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;
import wusc.edu.pay.facade.trade.enums.OrderStatusEnum;
import wusc.edu.pay.facade.trade.enums.TradePaymentTypeEnum;
import wusc.edu.pay.web.portal.base.MerchantLogonAction;
import wusc.edu.pay.web.portal.exceptions.PortalMerchantException;


/**
 * <ul>
 * <li>Title:我的收款交易</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Liliqiong
 * @version 2014-5-21
 */
public class PayPaymentOrderAction extends MerchantLogonAction {
	private static final long serialVersionUID = 1L;

	/**
	 * 交易记录查询
	 * 
	 * @return
	 */
	@Permission("Pay:PaymentOrder:List")
	public String listPaymentOrder() {
		// 查询参数封装
		String beginDate = getString("beginDate");
		String endDate = getString("endDate");
		String merchantOrderNo = getString("merchantOrderNo");
		String status = getString("status");
		String paymentType = getString("paymentType");

		// 回显查询条件
		super.pushData(this.getParamMap());

		pageBean = this.payPaymentOrderListForPage(getPageParam(), beginDate, endDate, merchantOrderNo, status, paymentType);
		pushData(pageBean);
		putData("tradePaymentTypeList", TradePaymentTypeEnum.values());// 支付方式类型
		putData("orderStatusList", OrderStatusEnum.values());// 交易状态集合

		return "listPaymentOrder";
	}

	/**
	 * 交易记录详情
	 * 
	 * @return
	 */
	@Permission("Pay:PaymentOrder:List")
	public String viewPaymentOrder() {
		String merchantOrderNo = getString("merchantOrderNo"); // 商户订单号
		PaymentOrder paymentOrder = this.payGetPaymentOrder(merchantOrderNo);

		if (ValidateUtils.isEmpty(paymentOrder)) {
			throw new PortalMerchantException(PortalMerchantException.ORDER_IS_NOT_EXIT, "商户订单不存在");
		}
		putData("paymentOrder", paymentOrder);
		//putData("refundRecordList", null);
		//putData("refundStatusList", null);
		return "viewPaymentOrder";
	}

	/**
	 * 在线导出EXCEL
	 * 
	 * @throws IOException
	 */
	@Permission("Pay:PaymentOrder:List")
	public void exportPaymentOrderToExcel() throws IOException {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String filename = "Pay_paymentRecord_" +  sdf.format(new Date());
		this.getHttpResponse().setHeader("Content-disposition",
				"attachment; filename=" + new String((filename + ".xls").getBytes("ISO-8859-1"), "UTF-8"));
		OutputStream os = getHttpResponse().getOutputStream();
		DataField[] dataFields = new DataField[8];
		dataFields[0] = new DataField("序号", "no");
		dataFields[1] = new DataField("创建日期", "createTime");
		dataFields[2] = new DataField("支付时间", "paymentTime");
		dataFields[3] = new DataField("支付方式类型", "paymentType");
		dataFields[4] = new DataField("商户订单号", "merchantOrderNo");
		dataFields[5] = new DataField("订单金额", "orderAmount");
		dataFields[6] = new DataField("实付金额", "paymentAmount");
		dataFields[7] = new DataField("交易状态", "status");//
		// dataFields[9] = new DataField("是否退款", "isRefund");//

		String merchantOrderNo = getString("merchantOrderNo"); // 商户订单号
		String beginDate = getString("beginDate");
		String endDate = getString("endDate");
		String status = getString("status");// 支付状态
		String paymentType = getString("paymentType");//

		PageParam pageParam = new PageParam(1, 5000);
		pageBean = this.payPaymentOrderListForPage(pageParam, beginDate, endDate, merchantOrderNo, status, paymentType);

		new ExcelDataExportor<Object>(dataFields, new ExportDataSource<Object>() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public List getData() {
				List<Object> list = pageBean.getRecordList();
				List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
				if (list != null && !list.isEmpty()) {
					int temp = 0;
					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> mapParam = new HashMap<String, Object>();
						PaymentOrder paymentRecord = (PaymentOrder) list.get(i);
						mapParam.put("no", ++temp);
						mapParam.put("createTime",
								ValidateUtils.isEmpty(paymentRecord.getCreateTime()) ? "" : sdf.format(paymentRecord.getCreateTime()));
						mapParam.put(
								"paymentTime",
								ValidateUtils.isEmpty(paymentRecord.getPaySuccessTime()) ? "" : sdf.format(paymentRecord
										.getPaySuccessTime()));
						mapParam.put("paymentType", ValidateUtils.isEmpty(paymentRecord.getPaymentType()) ? "" : TradePaymentTypeEnum
								.getEnum(paymentRecord.getPaymentType()).getDesc());
						mapParam.put("merchantOrderNo", paymentRecord.getMerchantOrderNo());
						mapParam.put("status",
								ValidateUtils.isEmpty(paymentRecord.getStatus()) ? "" : OrderStatusEnum.getEnum(paymentRecord.getStatus())
										.getDesc());
						mapParam.put("orderAmount", paymentRecord.getOrderAmount());
						mapParam.put("paymentAmount", paymentRecord.getPayerPayAmount());
						// mapParam.put("isRefund", new
						// Integer(100).equals(paymentRecord.getIsRefund()) ?
						// "已退" : "未退");
						lists.add(mapParam);
					}
				}
				return lists;
			}
		}, os, MODE.EXCEL).export();
	}
}
