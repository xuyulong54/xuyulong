package wusc.edu.pay.web.portal.action.merchant;

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

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.export.DataField;
import wusc.edu.pay.common.utils.export.ExportDataSource;
import wusc.edu.pay.common.utils.export.excel.ExcelDataExportor;
import wusc.edu.pay.common.utils.export.excel.MODE;
import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.service.TradeLimitFacade;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;
import wusc.edu.pay.facade.trade.enums.OrderStatusEnum;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.base.MerchantLogonAction;


/**
 * (商户收款)商户充值
 * 
 * @author liliqiong
 * @date 2013-11-4
 * @version 1.0
 */
public class ReceiveRechargeAction extends MerchantLogonAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private TradeLimitFacade tradeLimitFacade;

	/**
	 * 充值记录列表
	 * 
	 * @return
	 */
	@Permission("Receive:RechargeRecord:List")
	public String listReceiveRechargeRecord() {
		String beginDate = getString("beginDate");
		beginDate = ValidateUtils.isEmpty(beginDate) ? null : beginDate;
		String endDate = getString("endDate");
		endDate = ValidateUtils.isEmpty(endDate) ? null : endDate;
		String merchantOrderNo = getString("merchantOrderNo"); // 商户订单号
		Integer status = getInteger("status"); // 支付状态

		// 回显查询条件
		super.pushData(this.getParamMap());

		pageBean = queryRechargeOrderListPage(getPageParam(), merchantOrderNo, status, beginDate, endDate, null, null);
		pushData(pageBean);

		putData("orderStatusList", OrderStatusEnum.values());

		return "listReceiveRechargeRecord";
	}

	/**
	 * 充值记录导出EXCEL
	 * 
	 * @return
	 */
	@Permission("Receive:RechargeRecord:List")
	public void exportReceiveRechargeToExcel() throws IOException {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String filename ="Recharge_" + sdf.format(new Date());
		this.getHttpResponse().setHeader("Content-disposition",
				"attachment; filename=" + new String((filename + ".xls").getBytes("ISO-8859-1"), "UTF-8"));
		OutputStream os = getHttpResponse().getOutputStream();
		DataField[] dataFields = new DataField[7];
		dataFields[0] = new DataField("序号", "no");
		dataFields[1] = new DataField("创建时间", "createTime");
		dataFields[2] = new DataField("商户订单号", "merchantOrderNo");
		dataFields[3] = new DataField("交易时间", "paymentTime");
		dataFields[4] = new DataField("交易金额", "payAmount");
		dataFields[5] = new DataField("交易渠道", "bankName");
		dataFields[6] = new DataField("交易状态", "status");
		// 查询条件
		final String beginDate = getString("beginDate");
		final String endDate = getString("endDate");
		final String merchantOrderNo = getString("merchantOrderNo");
		PageParam pageParam = new PageParam(1, 10000);
		pageBean = queryRechargeOrderListPage(pageParam, merchantOrderNo, null, beginDate, endDate, null, null);

		new ExcelDataExportor<Object>(dataFields, new ExportDataSource<Object>() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public List getData() {
				List<Object> list = pageBean.getRecordList();
				List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
				if (list != null && !list.isEmpty()) {
					int temp = 0;
					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> map = new HashMap<String, Object>();
						PaymentOrder trans = (PaymentOrder) list.get(i);
						map.put("no", ++temp);
						map.put("createTime", ValidateUtils.isEmpty(trans.getCreateTime()) ? "" : sdf.format(trans.getCreateTime()));
						map.put("merchantOrderNo", trans.getMerchantOrderNo());
						map.put("paymentTime",
								ValidateUtils.isEmpty(trans.getPaySuccessDate()) ? "" : sdf.format(trans.getPaySuccessDate()));
						map.put("payAmount", trans.getPayerPayAmount());
						map.put("bankName", trans.getPayWayName());
						map.put("status", ValidateUtils.isEmpty(trans.getStatus()) ? "" : OrderStatusEnum.getEnum(trans.getStatus())
								.getDesc());
						lists.add(map);
					}
				}
				return lists;
			}
		}, os, MODE.EXCEL).export();

	}

	/**
	 * 去充值页面
	 * 
	 * @return
	 */
	@Permission("Account:Account:Recharge")
	public String rechargePage() {UserInfo userInfo = this.getCurrentUserInfo();
		if (userInfo.getIsMobileAuth() != 100) {
		return "RechargeException";
	}
		Account account = super.getAccount();
		putData("availableBalance", AmountUtil.roundDown(account.getAvailableBalance())); // 账户可用余额
		putData("account", account);// 帐户信息
		return "rechargePage";
	}

	/**
	 * 验证金额
	 * 
	 * @return
	 */
	@Permission("Account:Account:Recharge")
	public String checkAddAmount() {
		String addAmount = getString("addAmount");
		Map<String, String> msgMap = validateAddAmount(addAmount);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}
		return "checkAddAmount";
	}

	/**
	 * 充值
	 * 
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@Permission("Account:Account:Recharge")
	public void recharge() throws UnsupportedEncodingException, IOException {
		String p1_Amount = getString("addAmount"); // 充值金额
		String p2_LoginName = getCurrentUserInfo().getLoginName();
		String p3_ReturnUrl = PublicConfig.PROTAL_URL + "merchantaccount_viewAccount.action";
		String p4_OrderNo = super.buildRechargeOrderNo();
		/** 充值功能是否受限 */
		tradeLimitFacade.validateSwitchAvailable(super.getCurrentUserInfo().getUserNo(), LimitTrxTypeEnum.ACCOUNT_DEPOSIT, null, null);

		StringBuffer param = new StringBuffer();
		param.append("p1_Amount=").append(p1_Amount);
		param.append("&p2_LoginName=").append(p2_LoginName);
		param.append("&p3_ReturnUrl=").append(p3_ReturnUrl);
		param.append("&p4_OrderNo=").append(p4_OrderNo);

		String vHmac = DigestUtils.md5Hex(param.toString() + p2_LoginName);

		String url = PublicConfig.RECHARGE_URL;
		url += "?p1_Amount=" + p1_Amount;
		url += "&p2_LoginName=" + URLEncoder.encode(p2_LoginName, "utf-8");
		url += "&p3_ReturnUrl=" + URLEncoder.encode(p3_ReturnUrl, "utf-8");
		url += "&p4_OrderNo=" + URLEncoder.encode(p4_OrderNo, "utf-8");
		url += "&hmac=" + URLEncoder.encode(vHmac, "utf-8");
		getHttpResponse().sendRedirect(url);

		this.addUserLog(OpeStatusEnum.SUCCESS, "充值,金额：" + p1_Amount);
	}

	// ////////////////////////验证////////////////////////////////
	public Map<String, String> validateAddAmount(String addAmount) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorPage = BaseConsts.PAGE_ERROR_MSG;
		// 充值金额验证
		if (ValidateUtils.isEmpty(addAmount) || !ValidateUtils.isDouble(addAmount)) {
			msgMap.put(errorPage, "输入金额有误");
			return msgMap;
		} else if (Double.valueOf(addAmount) <= 0) {
			msgMap.put(errorPage, "金额必须大于0");
			return msgMap;
		}
		return msgMap;
	}
}
