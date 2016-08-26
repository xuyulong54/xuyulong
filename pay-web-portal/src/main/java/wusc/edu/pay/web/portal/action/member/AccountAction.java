package wusc.edu.pay.web.portal.action.member;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.export.DataField;
import wusc.edu.pay.common.utils.export.ExportDataSource;
import wusc.edu.pay.common.utils.export.excel.ExcelDataExportor;
import wusc.edu.pay.common.utils.export.excel.MODE;
import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.entity.AccountHistory;
import wusc.edu.pay.facade.account.enums.AccountStatusEnum;
import wusc.edu.pay.facade.account.enums.AccountTradeTypeEnum;
import wusc.edu.pay.web.portal.base.MemberLogonAction;


/**
 * 账户
 * 
 * @author liliqiong
 * @date 2013-11-4
 * @version 1.0
 */
@Scope("prototype")
public class AccountAction extends MemberLogonAction {
	private static final long serialVersionUID = 1L;
	private static Log LOG = LogFactory.getLog(AccountAction.class);

	/**
	 * 我的帐户
	 * 
	 * @return
	 */
	public String viewAccount() {
		// 账户信息
		Account account = this.getAccount();
		/*
		 * 在账户余额显示时，要对余额精度进行处理，大于两位小数的不能四舍五入，而是直接舍弃两位以后的小数（如100.0098,显示时为100.0，
		 * 不能显示为100.01，否则提现时会出问题） 注意，此用法也不能直接加要account实体的get方法中，否则有可能造成账户余额错误.
		 */
		putData("availableBalance", AmountUtil.roundDown(account.getAvailableBalance())); // 账户可用余额
		putData("account", account);
		// 账户状态
		putData("accountStatusEnum", AccountStatusEnum.values());
		// 积分
		putData("loginScore", getScore().getLoginScore());

		putData("accountStatusList", AccountStatusEnum.values());
		putData("lastLoginDate", this.getCurrentUserOperator().getLastLoginTime());
		putData("member", this.getMemberInfo());
		putData("userInfo", super.getUserInfoByUserNo(getCurrentUserInfo().getUserNo()));
		putData("PublicStatusEnum", PublicStatusEnum.toMap());
		return "viewAccount";
	}

	/**
	 * 财务明细查询
	 * 
	 * @return
	 */
	public String listAccountHistory() {
		// 查询账户历史的查询参数
		String beginDate = getString("beginDate");
		String endDate = getString("endDate");
		String fundDirection = getString("fundDirection");// 资金变动方向
		String trxType = getString("trxType");// 业务类型
		String requestNo = getString("requestNo");

		// 回显查询条件
		super.pushData(this.getParamMap());

		List<AccountTradeTypeEnum> memberTrxTypeList = new ArrayList<AccountTradeTypeEnum>();
		memberTrxTypeList.add(AccountTradeTypeEnum.ACCOUNT_TRANSFER);// 内部转账
		memberTrxTypeList.add(AccountTradeTypeEnum.ACCOUNT_DEPOSIT);// 账户充值
		memberTrxTypeList.add(AccountTradeTypeEnum.NET_B2C_REFUND);// B2C网银退款
		memberTrxTypeList.add(AccountTradeTypeEnum.DEPOSIT_FAILD_REFUND);// 充值失败退款
		memberTrxTypeList.add(AccountTradeTypeEnum.FAST_REFUND);// 快捷支付退款
		memberTrxTypeList.add(AccountTradeTypeEnum.ACCOUNT_BALANCE_REFUND);// 余额支付退款
		memberTrxTypeList.add(AccountTradeTypeEnum.ATM);// 提现
		memberTrxTypeList.add(AccountTradeTypeEnum.NET_B2C_PAY);// B2C网银支付
		memberTrxTypeList.add(AccountTradeTypeEnum.FAST_PAY);// 快捷支付
		memberTrxTypeList.add(AccountTradeTypeEnum.ACCOUNT_BALANCE_PAY);// 余额支付

		putData("trxTypeList", memberTrxTypeList);

		pageBean = this.queryAccountHistoryListPage(getPageParam(), beginDate, endDate, fundDirection, requestNo, trxType);
		pushData(pageBean);

		return "listAccountHistory";
	}

	public void exportExcel() {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String filename = "Account_" + sdf.format(new Date());
			this.getHttpResponse().setHeader("Content-disposition",
					"attachment; filename=" + new String((filename + ".xls").getBytes("ISO-8859-1"), "UTF-8"));
			OutputStream os = getHttpResponse().getOutputStream();
			DataField[] dataFields = new DataField[6];
			dataFields[0] = new DataField("序号", "no");
			dataFields[1] = new DataField("交易时间", "createTime");
			dataFields[2] = new DataField("交易流水号", "requestNo");
			dataFields[3] = new DataField("交易金额", "amount");
			dataFields[4] = new DataField("账户余额", "balance");
			dataFields[5] = new DataField("交易类型", "trxType");

			final String beginDate = getString("beginDate");
			final String endDate = getString("endDate");
			final String fundDirection = getString("fundDirection");// 资金变动方向
			final String trxType = getString("trxType");// 业务类型
			final String requestNo = getString("requestNo");

			PageParam pageParam = new PageParam(1, 5000); // TODO
			pageBean = this.queryAccountHistoryListPage(pageParam, beginDate, endDate, fundDirection, requestNo, trxType);

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
							AccountHistory accountHistory = (AccountHistory) list.get(i);
							mapParam.put("no", ++temp);
							mapParam.put("createTime", sdf.format(accountHistory.getCreateTime()));
							mapParam.put("requestNo", accountHistory.getRequestNo());
							if (123 == accountHistory.getFundDirection().intValue()) {
								mapParam.put("amount", "+" + accountHistory.getAmount());
							} else {
								mapParam.put("amount", "-" + accountHistory.getAmount());
							}
							mapParam.put("balance", accountHistory.getBalance());
							mapParam.put("trxType", AccountTradeTypeEnum.getEnum(accountHistory.getTrxType()).getDesc());
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
}
