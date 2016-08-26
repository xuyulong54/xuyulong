package wusc.edu.pay.web.portal.action.merchant;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.export.DataField;
import wusc.edu.pay.common.utils.export.ExportDataSource;
import wusc.edu.pay.common.utils.export.excel.ExcelDataExportor;
import wusc.edu.pay.common.utils.export.excel.MODE;
import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.service.TradeLimitFacade;
import wusc.edu.pay.facade.trade.entity.TransferRecord;
import wusc.edu.pay.facade.trade.enums.OrderStatusEnum;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.base.MerchantLogonAction;
import wusc.edu.pay.web.portal.biz.SmsBiz;


/**
 * 转账(收款)
 * 
 * @author liliqiong
 * @date 2013-10-9
 * @version 1.0
 */
public class ReceiveTransferAction extends MerchantLogonAction {

	private static final long serialVersionUID = -1180297479867965902L;
	private static final Log LOG = LogFactory.getLog(ReceiveTransferAction.class);

	@Autowired
	private TradeLimitFacade tradeLimitFacade;
	@Autowired
	private SmsBiz smsBiz;

	/**
	 * 转帐列表
	 * 
	 * @return
	 */
	@Permission("Receive:TransferRecord:List")
	public String listReceiveTransfer() {
		String trxNo = getString("trxNo");// 交易流水号
		String beginDate = getString("beginDate");
		beginDate = ValidateUtils.isEmpty(beginDate) ? null : beginDate;
		String endDate = getString("endDate");
		endDate = ValidateUtils.isEmpty(endDate) ? null : endDate;

		// 回显查询条件
		super.pushData(this.getParamMap());

		pageBean = receiveTransferRecordListForPage(getPageParam(), trxNo, beginDate, endDate);
		pushData(pageBean);

		putData("orderStatusList", OrderStatusEnum.values());

		return "listRecieveTransfer";
	}

	/**
	 * 转账记录导出EXCEL
	 * 
	 * @throws IOException
	 */
	@Permission("Receive:TransferRecord:List")
	public void exportReceiveTransferToExcel() throws IOException {

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String filename = "Transfer_" + sdf.format(new Date());
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
		final String merchantNo = this.getCurrentUserInfo().getUserNo();
		new ExcelDataExportor<Object>(dataFields, new ExportDataSource<Object>() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public List getData() {

				PageParam pageParam = new PageParam(1, 10000);
				pageBean = receiveTransferRecordListForPage(pageParam, trxNo, beginDate, endDate);

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
						map.put("loginName",
								merchantNo.equals(trans.getTargetUserNo()) ? trans.getSourceLoginName() : trans.getTargetLoginName());
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

	/**
	 * 进入转账页面.
	 * 
	 * @return
	 */
	@Permission("Account:Account:Transfer")
	public String ransferPage() {
		if (!getCurrentUserInfo().getUserType().equals(UserTypeEnum.CUSTOMER.getValue())) {
			/** 只针对商户是否开通转帐功能 */
			tradeLimitFacade.validateSwitchAvailable(getCurrentUserInfo().getUserNo(), LimitTrxTypeEnum.ACCOUNT_TRANSFER, null, null);
		}
		String loginName = this.getCurrentUserInfo().getLoginName();
		putData("balance", AmountUtil.roundDown(super.getAccount().getAvailableBalance())); // 账户可用余额
		putData("loName", loginName);
		putData("transferDesc", "付款");
		return "ransferPage";
	}

	/**
	 * 转账
	 * 
	 * @return
	 */
	@Permission("Account:Account:Transfer")
	public String transfer() {

		String checkMobile = getString("checkMobile");// checkbox是否选 中手机通知收款人。
		String recodeName = getString("recodeName");// 转账人真实姓名从Ajax中获得
		String balance = getString("balance");// 账户余额
		String tradePwd_rs = super.getPwd("tradePwd");// 转款人支付密码
		String tradePwd = (StringTools.stringToTrim(tradePwd_rs));// 解密
		String transferAmount = getString("transferAmount");// 转账金额
		String payeeAccountNo = getString("payeeAccountNo");// 收款人帐户
		String transferDesc = getString("transferDesc"); // 转帐说明
		String mobileNo = getString("mobileNo"); // 手机号码

		// 验证支付密码
		String msg = super.validateTradePwd(tradePwd);
		if (null != msg) { // 验证不通过，返回之前的页面重新验证
			LOG.info(msg);
			putData(BaseConsts.PAGE_ERROR_MSG, msg);
			putData("transferAmount", transferAmount);
			putData("transferDesc", transferDesc);
			putData("mobileNo", mobileNo);
			putData("balance", balance);
			putData("recodeName", recodeName);
			putData("payeeAccountNo", payeeAccountNo);
			putData("checkMobile", checkMobile);
			return "ransferPage";
		}

		try {


			String transferTrxNo = super.buildTransferTrxNo();

			// 转账
			super.completeTransfer(transferTrxNo, this.getCurrentUserInfo().getLoginName(), payeeAccountNo, Double.valueOf(transferAmount),
					transferDesc);

			this.addUserLog(OpeStatusEnum.SUCCESS, String.format("转账成功，金额%s，付款方%s，收款方%s", Double.valueOf(transferAmount), this
					.getCurrentUserInfo().getLoginName(), payeeAccountNo));
			LOG.info("转账成功");
		} catch (Exception e) {
			LOG.error("==transfer error", e);
			putData(BaseConsts.PAGE_ERROR_MSG, e.getMessage());
			putData("transferAmount", transferAmount);
			putData("transferDesc", transferDesc);
			putData("mobileNo", mobileNo);
			putData("balance", balance);
			putData("recodeName", recodeName);
			putData("payeeAccountNo", payeeAccountNo);
			putData("checkMobile", checkMobile);
			return "ransferPage";
		}

		// 转账成功发送提示短信
		if (!StringUtils.isEmpty(mobileNo)) {

			String smsTemplatePath = "template/sms/TransferSuccess.vm"; // 短信模板

			Map<String, Object> paramModel = new HashMap<String, Object>();
			paramModel.put("realName", getCurrentUserInfo().getRealName());
			paramModel.put("transferAmount", transferAmount);
			paramModel.put("compayFor", PublicConfig.COMPANY_FOR);

			String smsContent = "";
			try {
				smsContent = smsBiz.mergeSmsTemplate(smsTemplatePath, paramModel);
			} catch (Exception e) {
				LOG.error("短信模板解释异常", e);
			}

			smsBiz.sendSms(mobileNo, smsContent);
		}
		putData("amount", transferAmount);
		return "editTransferSuccess";

	}

}
