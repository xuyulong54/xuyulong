package wusc.edu.pay.web.portal.action.member;

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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.export.DataField;
import wusc.edu.pay.common.utils.export.ExportDataSource;
import wusc.edu.pay.common.utils.export.excel.ExcelDataExportor;
import wusc.edu.pay.common.utils.export.excel.MODE;
import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.boss.entity.GlobalSet;
import wusc.edu.pay.facade.boss.service.GlobalSetFacade;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;
import wusc.edu.pay.facade.trade.enums.OrderStatusEnum;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.base.MemberLogonAction;


/**
 * 充值
 * 
 * @author liliqiong
 * @date 2013-10-9
 * @version 1.0
 */
public class ReceiveRechargeAction extends MemberLogonAction {
	private static final Log LOG = LogFactory.getLog(ReceiveRechargeAction.class);
	private static final long serialVersionUID = 1L;
	@Autowired
	private GlobalSetFacade globalSetFacade;

	/**
	 * 充值
	 */
	public String listReceiveRechargeRecord() {
		String beginDate = getString("beginDate");
		beginDate = ValidateUtils.isEmpty(beginDate) ? null : beginDate;
		String endDate = getString("endDate");
		endDate = ValidateUtils.isEmpty(endDate) ? null : endDate;
		String merchantOrderNo = getString("merchantOrderNo"); // 商户订单号
		Integer status = getInteger("status"); // 支付状态
		
		// 回显查询条件
		super.pushData(this.getParamMap());
		
		putData("orderStatusList", OrderStatusEnum.values());
		putData("timeType", getString("timeType"));

		pageBean = queryRechargeOrderListPage(getPageParam(), merchantOrderNo, status, beginDate, endDate, null, null);
		pushData(pageBean);

		return "listReceiveRechargeRecord";
	}

	/**
	 * 导出充值交易记录
	 */
	public void exportReceiveRechargeToExcel() {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String filename = "Recharge_" + sdf.format(new Date());
			this.getHttpResponse().setHeader("Content-disposition",
					"attachment; filename=" + new String((filename + ".xls").getBytes("ISO-8859-1"), "UTF-8"));
			OutputStream os = getHttpResponse().getOutputStream();
			DataField[] dataFields = new DataField[8];
			dataFields[0] = new DataField("序号", "no");
			dataFields[1] = new DataField("创建时间", "merchantOrderNo");
			dataFields[2] = new DataField("商户订单号", "createTime");
			dataFields[3] = new DataField("交易类型", "type");
			dataFields[4] = new DataField("交易时间", "paymentTime");
			dataFields[5] = new DataField("交易金额", "payAmount");
			dataFields[6] = new DataField("交易渠道", "bankName");
			dataFields[7] = new DataField("交易状态", "status");

			final String beginDate = getString("beginDate");
			final String endDate = getString("endDate");
			final String merchantOrderNo = getString("merchantOrderNo");
			final Integer status = getInteger("status");
			final String beginAmount = getString("beginAmount");
			final String endAmount = getString("endAmount");

			new ExcelDataExportor<Object>(dataFields, new ExportDataSource<Object>() {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				@SuppressWarnings({ "rawtypes", "unchecked" })
				public List getData() {
					PageParam pageParam = new PageParam(1, 10000);

					pageBean = queryRechargeOrderListPage(pageParam, merchantOrderNo, status, beginDate, endDate, beginAmount, endAmount);

					List<Object> list = pageBean.getRecordList();
					List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
					if (list != null && !list.isEmpty()) {
						int temp = 0;
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> mapParam = new HashMap<String, Object>();
							PaymentOrder paymentRecord = (PaymentOrder) list.get(i);
							mapParam.put("no", ++temp);
							mapParam.put("merchantOrderNo", paymentRecord.getMerchantOrderNo());
							mapParam.put("createTime",
									ValidateUtils.isEmpty(paymentRecord.getCreateTime()) ? "" : sdf.format(paymentRecord.getCreateTime()));
							mapParam.put("type", "充值");
							mapParam.put(
									"paymentTime",
									ValidateUtils.isEmpty(paymentRecord.getPaySuccessDate()) ? "" : sdf.format(paymentRecord
											.getPaySuccessDate()));
							mapParam.put("payAmount", paymentRecord.getPayerPayAmount());
							mapParam.put("payProductName", paymentRecord.getPayProductName());
							mapParam.put(
									"status",
									ValidateUtils.isEmpty(paymentRecord.getStatus()) ? "" : OrderStatusEnum.getEnum(
											paymentRecord.getStatus()).getDesc());
							lists.add(mapParam);
						}
					}
					return lists;
				}
			}, os, MODE.EXCEL).export();
		} catch (UnsupportedEncodingException e) {
			LOG.error("exportExcel fail:", e);
		} catch (IOException e) {
			LOG.error("exportExcel fail:", e);
		}
	}

	/**
	 * 去充值页面
	 * 
	 * @return
	 */
	public String rechargePage() {
		UserInfo userInfo = this.getCurrentUserInfo();
		if (userInfo.getIsMobileAuth() != 100) {
			return "RechargeException";
		}
		Account account = super.getAccount();
		putData("availableBalance", AmountUtil.roundDown(account.getAvailableBalance())); // 账户可用余额
		putData("account", account);// 帐户信息
		return "rechargePage";
	}

	public String checkAddAmount() {
		String addAmount = getString("addAmount");
		Map<String, String> msgMap = validateAddAmount(addAmount);
		if (!msgMap.isEmpty()) {
			putData("account", super.getAccountByAccountNo(getCurrentUserInfo().getAccountNo()));// 帐户信息
			putData("addAmount", addAmount);
			pushData(msgMap);
			return "rechargePage";
		}
		return "checkAddAmount";
	}

	/**
	 * 会员充值
	 * 
	 * @return
	 */
	public void recharge() throws Exception {

		String p1_Amount = getString("addAmount"); // 充值金额
		String p2_LoginName = getCurrentUserInfo().getLoginName();
		String p3_ReturnUrl = PublicConfig.PROTAL_URL + "memberaccount_viewAccount.action";
		String p4_OrderNo = super.buildRechargeOrderNo();
	
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
		
	}

	// ////////////////////////验证////////////////////////////////
	public Map<String, String> validateAddAmount(String addAmount) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		// 充值金额验证
		if (ValidateUtils.isEmpty(addAmount) || !ValidateUtils.isDouble(addAmount)) {
			msgMap.put(errorMsg, "输入金额有误");
			return msgMap;
		} else if (Double.valueOf(addAmount) <= 0) {
			msgMap.put(errorMsg, "金额必须大于0");
			return msgMap;
		}
		if (Double.valueOf(addAmount).toString().split("\\.")[1].length() > 2) {
			msgMap.put(errorMsg, "金额精确到分");
			return msgMap;
		}

		GlobalSet globalSetMax = globalSetFacade.getBySetKey("MEMBER_SINGLE_RECHARGE_MAX_LIMIT");
		GlobalSet globalSetMin = globalSetFacade.getBySetKey("MEMBER_SINGLE_RECHARGE_MIN_LIMIT");
		Double addAmount1 = Double.valueOf(addAmount);
		if (addAmount1 > Double.valueOf(globalSetMax.getSetContent())) {
			msgMap.put(errorMsg, "单笔充值金额不可大于" + globalSetMax.getSetContent() + "元");
		}
		if (addAmount1 < Double.valueOf(globalSetMin.getSetContent())) {
			msgMap.put(errorMsg, "单笔充值金额不可小于" + globalSetMin.getSetContent() + "元");
		}
		return msgMap;
	}

}
