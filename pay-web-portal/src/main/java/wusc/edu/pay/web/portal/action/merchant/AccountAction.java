package wusc.edu.pay.web.portal.action.merchant;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.export.DataField;
import wusc.edu.pay.common.utils.export.ExportDataSource;
import wusc.edu.pay.common.utils.export.excel.ExcelDataExportor;
import wusc.edu.pay.common.utils.export.excel.MODE;
import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.entity.AccountHistory;
import wusc.edu.pay.facade.account.enums.AccountStatusEnum;
import wusc.edu.pay.facade.account.enums.AccountTradeTypeEnum;
import wusc.edu.pay.facade.limit.entity.BizFunctionSwitch;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.service.BizFunctionSwitchFacade;
import wusc.edu.pay.web.portal.base.MerchantLogonAction;


/**
 * 账户
 * 
 * @author liliqiong
 * @date 2013-11-4
 * @version 1.0
 */
@Scope("prototype")
public class AccountAction extends MerchantLogonAction {
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(AccountAction.class);

	@Autowired
	private BizFunctionSwitchFacade bizFunctionSwitchFacade;

	/**
	 * 我的帐户
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String viewAccount() {
		// 1.判断该商户支持的业务
		Integer transferStatus = 0;// 转账业务状态
		Integer rechargeStatus = 0;// 充值业务状态
		List<BizFunctionSwitch> bizs = bizFunctionSwitchFacade.getBizFunctionSwitchByMerchantNo(super.getCurrentUserInfo().getUserNo());
		for (Iterator iterator = bizs.iterator(); iterator.hasNext();) {
			BizFunctionSwitch bizFunctionSwitch = (BizFunctionSwitch) iterator.next();
			if (bizFunctionSwitch.getBizFunction().equals(LimitTrxTypeEnum.ACCOUNT_DEPOSIT.name())) {//
				rechargeStatus = 100;
			}
			if (bizFunctionSwitch.getBizFunction().equals(LimitTrxTypeEnum.ACCOUNT_TRANSFER.name())) {//
				transferStatus = 100;
			}
		}

		// 2.得到账户信息
		Account account = getAccount();

		// 3.获取积分
		putData("loginScore", this.getScore().getLoginScore()); // 积分

		// 4.查看公告（POS商户）
		/*
		 * if (getCurrentUser().getUserType().intValue() ==
		 * AccountTypeEnum.POS.getValue()) { Map<String, Object> paramMap = new
		 * HashMap<String, Object>(); paramMap.put("type",
		 * ArticleTypeEnum.NOTICE.getValue()); paramMap.put("status",
		 * PublicStatusEnum.ACTIVE.getValue()); paramMap.put("articleType",
		 * AccountTypeEnum.POS.getValue()); pageBean =
		 * articleFacade.listPage(getPageParam(), paramMap); pushData(pageBean);
		 * }
		 */

		// 5.把页面中需要的参数放入request域中
		putData("AccountStatusEnumList", AccountStatusEnum.values());// 账户状态

		/*
		 * 在可用账户余额显示时，要对余额精度进行处理，大于两位小数的不能四舍五入，而是直接舍弃两位以后的小数（如100.0098,显示时为100.0，
		 * 不能显示为100.01，否则提现时会出问题） 注意，此用法也不能直接加要account实体的get方法中，否则有可能造成账户余额错误.
		 */
		putData("availableBalance", AmountUtil.roundDown(account.getAvailableBalance())); // 账户可用余额
		putData("account", account);// 账户信息
		putData("transferStatus", transferStatus);// 转账业务状态
		putData("rechargeStatus", rechargeStatus);// 充值业务状态
		putData("userInfo", getCurrentUserInfo());
		putData("PublicStatusEnum", PublicStatusEnum.toMap());
		return "viewAccount";
	}

	/**
	 * 财务明细查询
	 * 
	 * @return
	 */
	@Permission("Account:History:List")
	public String listAccountHistory() {
		// 1.获取页面查询参数
		String beginDate = getString("beginDate");
		String endDate = getString("endDate");
		String trxType = getString("trxType");// 业务类型
		String fundDirection = getString("fundDirection");// 资金变动方向
		String requestNo = getString("requestNo");

		// 回显示查询条件
		super.pushData(this.getParamMap());

		super.putData("trxTypeList", AccountTradeTypeEnum.toList());

		super.pageBean = this.queryAccountHistoryListPage(getPageParam(), beginDate, endDate, fundDirection, requestNo, trxType);
		super.pushData(pageBean);

		return "listAccountHistory";
	}

	/**
	 * 导出EXCEL
	 */
	@Permission("Account:History:List")
	public void exportExcel() {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String filename = "Account_" + sdf.format(new Date());
			String fileName = filename + ".xls";
			// 解决导出文件的文件名乱码问题
			String name = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
			this.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + name);
			OutputStream os = getHttpResponse().getOutputStream();
			DataField[] dataFields = new DataField[6];
			dataFields[0] = new DataField("序号", "no");
			dataFields[1] = new DataField("交易日期", "createTime");
			dataFields[2] = new DataField("交易流水号", "requestNo");
			dataFields[3] = new DataField("交易金额", "amount");
			dataFields[4] = new DataField("账户余额", "balance");
			dataFields[5] = new DataField("交易类型", "trxType");
			final String beginDate = getString("beginDate");
			final String endDate = getString("endDate");
			final String trxType = getString("trxType");// 业务类型
			final String fundDirection = getString("fundDirection");// 资金变动方向
			final String requestNo = getString("requestNo");

			PageParam pageParam = new PageParam(1, 5000);
			pageBean = this.queryAccountHistoryListPage(pageParam, beginDate, endDate, fundDirection, requestNo, trxType);

			new ExcelDataExportor<Object>(dataFields, new ExportDataSource<Object>() {
				// DecimalFormat df = new DecimalFormat("#0.00");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				@SuppressWarnings({ "unchecked", "rawtypes" })
				public List getData() {
					List<Object> list = pageBean.getRecordList();
					List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
					if (list != null && !list.isEmpty()) {
						int temp = 0;
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> mapParam = new HashMap<String, Object>();
							AccountHistory accountHistory = (AccountHistory) list.get(i);
							mapParam.put("no", ++temp);
							mapParam.put("createTime",
									ValidateUtils.isEmpty(accountHistory.getCreateTime()) ? "" : sdf.format(accountHistory.getCreateTime()));
							mapParam.put("requestNo", accountHistory.getRequestNo());
							if (123 == accountHistory.getFundDirection()) {
								mapParam.put("amount", "+" + accountHistory.getAmount());
							} else {
								mapParam.put("amount", "-" + accountHistory.getAmount());
							}
							mapParam.put("balance", accountHistory.getBalance());
							mapParam.put(
									"trxType",
									ValidateUtils.isEmpty(accountHistory.getTrxType()) ? "" : AccountTradeTypeEnum.getEnum(
											accountHistory.getTrxType()).getDesc());
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
