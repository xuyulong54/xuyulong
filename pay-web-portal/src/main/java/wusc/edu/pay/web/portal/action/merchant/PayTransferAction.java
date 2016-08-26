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
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.trade.entity.TransferRecord;
import wusc.edu.pay.facade.trade.enums.OrderStatusEnum;
import wusc.edu.pay.web.portal.base.MerchantLogonAction;


/**
 * 我是付款：转账 商户
 * 
 * @desc
 * @author shenjialong
 * @date 2014-6-5,下午3:21:01
 */
public class PayTransferAction extends MerchantLogonAction {

	private static final long serialVersionUID = -1180297479867965902L;

	/**
	 * 转帐列表
	 * 
	 * @return
	 */
	@Permission("Pay:TransferRecord:List")
	public String listTransfer() {
		String trxNo = getString("trxNo");// 交易流水号
		String beginDate = getString("beginDate");
		String endDate = getString("endDate");

		// 回显查询条件
		super.pushData(this.getParamMap());

		pageBean = this.payTransferRecordListForPage(getPageParam(), trxNo, beginDate, endDate);
		pushData(pageBean);
		putData("orderStatusList", OrderStatusEnum.values());

		return "listTransfer";
	}

	/**
	 * 转账记录导出EXCEL
	 * 
	 * @throws IOException
	 */
	@Permission("Pay:TransferRecord:List")
	public void transferExportExcel() throws IOException {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String filename = "Pay_Transfer_" + sdf.format(new Date());
		this.getHttpResponse().setHeader("Content-disposition",
				"attachment; filename=" + new String((filename + ".xls").getBytes("ISO-8859-1"), "UTF-8"));
		OutputStream os = getHttpResponse().getOutputStream();
		DataField[] dataFields = new DataField[8];
		dataFields[0] = new DataField("序号", "no");
		dataFields[1] = new DataField("创建时间", "createTime");
		dataFields[2] = new DataField("交易流水号", "trxNo");
		dataFields[3] = new DataField("对方账号", "loginName");
		dataFields[4] = new DataField("转账金额", "amount");
		dataFields[5] = new DataField("手续费", "targetFee");
		dataFields[6] = new DataField("交易状态", "status");
		dataFields[7] = new DataField("转账说明", "desc");
		// 查询条件
		final String trxNo = getString("trxNo");// 交易流水号
		final String beginDate = getString("beginDate");
		final String endDate = getString("endDate");

		PageParam pageParam = new PageParam(1, 5000);
		pageBean = this.payTransferRecordListForPage(pageParam, trxNo, beginDate, endDate);

		new ExcelDataExportor<Object>(dataFields, new ExportDataSource<Object>() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public List getData() {
				List<Object> list = pageBean.getRecordList();
				List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
				if (list != null && !list.isEmpty()) {
					int temp = 0;
					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> map = new HashMap<String, Object>();
						TransferRecord trans = (TransferRecord) list.get(i);
						map.put("no", ++temp);
						map.put("trxNo", trans.getTrxNo());
						map.put("createTime", sdf.format(trans.getCreateTime()));
						map.put("loginName", trans.getSourceLoginName());
						map.put("amount", trans.getAmount());
						map.put("targetFee", trans.getTargetFee());
						map.put("status", OrderStatusEnum.getEnum(trans.getStatus()).getDesc());
						map.put("desc", trans.getRemark());
						lists.add(map);
					}
				}
				return lists;
			}
		}, os, MODE.EXCEL).export();
	}

}
