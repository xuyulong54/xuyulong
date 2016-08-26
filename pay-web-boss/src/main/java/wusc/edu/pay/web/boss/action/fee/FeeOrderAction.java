package wusc.edu.pay.web.boss.action.fee;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.BeanUtils;
import wusc.edu.pay.common.utils.export.DataField;
import wusc.edu.pay.common.utils.export.ExportDataSource;
import wusc.edu.pay.common.utils.export.excel.ExcelDataExportor;
import wusc.edu.pay.common.utils.export.excel.MODE;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.fee.entity.FeeCalculateWay;
import wusc.edu.pay.facade.fee.entity.FeeFormula;
import wusc.edu.pay.facade.fee.entity.FeeOrder;
import wusc.edu.pay.facade.fee.enums.CalculateFeeItemEnum;
import wusc.edu.pay.facade.fee.enums.FeeCalculateTypeEnum;
import wusc.edu.pay.facade.fee.enums.FeeChargeTypeEnum;
import wusc.edu.pay.facade.fee.enums.FeeFormulaTypeEnum;
import wusc.edu.pay.facade.fee.enums.FeeOrderStatusEnum;
import wusc.edu.pay.facade.fee.enums.FeeOrderTypeEnum;
import wusc.edu.pay.facade.fee.enums.FeeRoleTypeEnum;
import wusc.edu.pay.facade.fee.enums.LadderCycleTypeEnum;
import wusc.edu.pay.facade.fee.service.FeeCalculateWayFacade;
import wusc.edu.pay.facade.fee.service.FeeFormulaeFacade;
import wusc.edu.pay.facade.fee.service.FeeQueryFacade;
import wusc.edu.pay.facade.payrule.entity.PayProduct;
import wusc.edu.pay.facade.payrule.service.PayProductFacade;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 计费订单管理Action
 * 
 * @author zws
 * 
 */
public class FeeOrderAction extends BossBaseAction {

	private static final long serialVersionUID = 1389912229806978954L;
	private static Log log = LogFactory.getLog(FeeOrderAction.class);
	@Autowired
	private FeeQueryFacade feeQueryFacade;
	@Autowired
	private FeeCalculateWayFacade feeCalculateWayFacade;
	@Autowired
	private FeeFormulaeFacade feeFormulaeFacade;
	@Autowired
	private PayProductFacade payProductFacade;

	/**
	 * 计费订单列表
	 * 
	 * @return
	 */
	@Permission("fee:order:view")
	public String listOrder() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantName", getString("merchantName"));
		map.put("userType", getInteger("userType"));
		map.put("calculateFeeItem", getInteger("calculateFeeItem"));
		map.put("status", getInteger("status"));
		map.put("orderType", getInteger("orderType"));
		map.put("trxFlowNo", getString("trxFlowNo"));
		map.put("beginDate", getString("beginDate"));
		map.put("endDate", getString("endDate"));
		map.put("merchantNo", getString("merchantNo"));

		super.pageBean = feeQueryFacade.queryFeeOrderListPage(this.getPageParam(), map);
		// 准备枚举
		putData("CalculateFeeItemEnum", CalculateFeeItemEnum.values());
		putData("FeeOrderTypeEnum", FeeOrderTypeEnum.values());
		putData("UserTypeEnum", UserTypeEnum.values());
		putData("FeeOrderStatusEnum", FeeOrderStatusEnum.values());
		pushData(pageBean);
		// 回显
		pushData(map);
		return "feeOrderList";
	}

	/**
	 * 计费订单详情
	 * 
	 * @return
	 */
	@Permission("fee:order:view")
	public String showOrderDetail() {
		Long id = getLong("id");
		FeeOrder feeOrder = null;
		if (id != null) {
			feeOrder = feeQueryFacade.queryFeeOrderById(id);
			if (feeOrder != null) {
				Long calWayId = feeOrder.getCalculateWayId();
				FeeCalculateWay feeCalculateWay = feeCalculateWayFacade.getById(calWayId);
				List<FeeFormula> listFeeFormulas = feeFormulaeFacade.listByCalWayId(calWayId);
				PayProduct payProduct = new PayProduct();
				if (feeOrder.getCalculateFeeItem() == CalculateFeeItemEnum.REFUND_ACQUIRING.getValue()) {
					payProduct = payProductFacade.getPayProductByPayProductCode(feeOrder.getOlPayProduct());
				} else {
					payProduct = payProductFacade.getPayProductByPayProductCode(feeOrder.getPayProduct());
				}

				putData("feeCalculateWay", feeCalculateWay);
				putData("listFeeFormulas", listFeeFormulas);
				putData("payProduct", payProduct);
			}
		}
		putData("feeOrder", feeOrder);
		// 准备枚举
		putData("CalculateFeeItemEnum", CalculateFeeItemEnum.values());
		putData("FeeOrderTypeEnum", FeeOrderTypeEnum.values());
		putData("UserTypeEnum", UserTypeEnum.values());
		putData("FeeOrderStatusEnum", FeeOrderStatusEnum.values());
		putData("FeeCalculateTypeEnum", FeeCalculateTypeEnum.values());
		putData("FeeChargeTypeEnum", FeeChargeTypeEnum.values());
		putData("LadderCycleTypeEnum", LadderCycleTypeEnum.values());
		putData("FeeRoleTypeEnum", FeeRoleTypeEnum.values());
		putData("FeeFormulaTypeEnum", FeeFormulaTypeEnum.values());
		return "showOrderDetail";
	}
	
	/**
	 * 导出商户计费订单报表
	 */
	public void feeOrderExportExecl() {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String filename = "商户计费订单_" + sdf.format(new Date());
			this.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(filename + ".xls", "utf-8"));
			OutputStream os = getHttpResponse().getOutputStream();
			DataField[] dataFields = new DataField[13];
			dataFields[0] = new DataField("序号", "no",5);
			dataFields[1] = new DataField("计费时间", "calculateDate",25);
			dataFields[2] = new DataField("交易流水号", "trxFlowNo", 25);
			dataFields[3] = new DataField("商户名称", "merchantName", 30);
			dataFields[4] = new DataField("商户编号", "merchantNo", 25);
			dataFields[5] = new DataField("订单编号", "merchantOrderNo", 20);
			dataFields[6] = new DataField("用户类型", "userType");
			dataFields[7] = new DataField("计费项", "calculateFeeItem");
			dataFields[8] = new DataField("费率基数", "feeBase",30);
			dataFields[9] = new DataField("交易金额", "amount");
			dataFields[10] = new DataField("收款方手续费", "payeeFee");
			dataFields[11] = new DataField("订单类型", "orderType");
			dataFields[12] = new DataField("订单状态", "status");

			final String beginDate = getString("beginDate");
			final String endDate = getString("endDate");
			final String status = getString("status");
			final String merchantName = getString("merchantName");
			final String userType = getString("userType");
			final String calculateFeeItem = getString("calculateFeeItem");
			final String trxFlowNo = getString("trxFlowNo");
			final String merchantNo = getString("merchantNo");
			final String orderType = getString("orderType");

			new ExcelDataExportor<Object>(dataFields, new ExportDataSource<Object>() {
				@Override
				public List getData() {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("beginDate", beginDate);
					map.put("endDate", endDate);
					map.put("status", status);
					map.put("merchantName", merchantName);
					map.put("userType", userType);
					map.put("calculateFeeItem", calculateFeeItem);
					map.put("trxFlowNo", trxFlowNo);
					map.put("merchantNo", merchantNo);
					map.put("orderType", orderType);
					PageParam pageParam = new PageParam(1, exportMaxNumber);
					PageBean pageBean = feeQueryFacade.queryFeeOrderListPage(pageParam, map);

					List<Object> list = pageBean.getRecordList();
					List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
					if (!list.isEmpty()) {
						int temp = 0;
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> mapParam = new HashMap<String, Object>();
							FeeOrder ap = (FeeOrder) list.get(i);
							FeeOrder feeOrder = BeanUtils.toBean(FeeOrder.class, ap);
							mapParam.put("no", ++temp);
							mapParam.put("calculateDate", sdf1.format(feeOrder.getCalculateDate()));
							mapParam.put("trxFlowNo", feeOrder.getTrxFlowNo());
							mapParam.put("merchantName", feeOrder.getMerchantName());
							mapParam.put("merchantNo", feeOrder.getMerchantNo());
							mapParam.put("merchantOrderNo", feeOrder.getMerchantOrderNo());
							mapParam.put("userType", feeOrder.getUserType()!=null && UserTypeEnum.getEnum(feeOrder.getUserType())!=null?UserTypeEnum.getEnum(feeOrder.getUserType()).getDesc():"") ;
							mapParam.put("calculateFeeItem", feeOrder.getCalculateFeeItem()!=null && CalculateFeeItemEnum.getEnum(feeOrder.getCalculateFeeItem())!=null?CalculateFeeItemEnum.getEnum(feeOrder.getCalculateFeeItem()).getDesc():"");
							mapParam.put("feeBase", feeOrder.getFeeBase());
							mapParam.put("amount", feeOrder.getAmount());
							mapParam.put("payeeFee", feeOrder.getPayeeFee());
							mapParam.put("orderType", feeOrder.getOrderType()!=null && FeeOrderTypeEnum.getEnum(feeOrder.getOrderType())!=null?FeeOrderTypeEnum.getEnum(feeOrder.getOrderType()).getDesc():"");
							mapParam.put("status", feeOrder.getStatus()!=null&&FeeOrderStatusEnum.getEnum(feeOrder.getStatus())!=null?FeeOrderStatusEnum.getEnum(feeOrder.getStatus()).getDesc():"");
							lists.add(mapParam);
						}
					}
					return lists;
				}
			}, os, MODE.EXCEL).export();
			super.logSave("导出商户计费订单数据");
		} catch (UnsupportedEncodingException e) {
			log.error("下载报表", e);
		} catch (IOException e) {
			log.error("IO", e);
		}
	}
}
