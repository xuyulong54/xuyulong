package wusc.edu.pay.web.portal.action.member;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.CurrencyTypeEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.exceptions.CerSignException;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.export.DataField;
import wusc.edu.pay.common.utils.export.ExportDataSource;
import wusc.edu.pay.common.utils.export.excel.ExcelDataExportor;
import wusc.edu.pay.common.utils.export.excel.MODE;
import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.enums.AccountStatusEnum;
import wusc.edu.pay.facade.account.exception.AccountBizException;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.remit.entity.RemitBankType;
import wusc.edu.pay.facade.remit.service.RemitBankTypeFacade;
import wusc.edu.pay.facade.remit.service.RemitRequestFacade;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.enums.SettModeTypeEnum;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;
import wusc.edu.pay.facade.settlement.enums.SettTypeEnum;
import wusc.edu.pay.facade.settlement.exception.SettBizException;
import wusc.edu.pay.facade.settlement.service.SettBusinessFacade;
import wusc.edu.pay.facade.settlement.service.SettQueryFacade;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.service.UserBankAccountFacade;
import wusc.edu.pay.web.portal.base.MerchantLogonAction;
import wusc.edu.pay.web.portal.exceptions.PortalMerchantException;

import com.alibaba.dubbo.rpc.RpcException;

/**
 * 提现
 * 
 * @author liliqiong
 * @date 2013-11-4
 * @version 1.0
 */
public class WithdrawAction extends MerchantLogonAction {
	private static final Log LOG = LogFactory.getLog(WithdrawAction.class);
	private static final long serialVersionUID = 1L;
	@Autowired
	private UserBankAccountFacade userBankAccountFacade;
	@Autowired
	private AccountQueryFacade accountQueryFacade;
	@Autowired
	private SettQueryFacade settQueryFacade;
	@Autowired
	private SettBusinessFacade settBusinessFacade;
	@Autowired
	private RemitRequestFacade remitRequestFacade;
	@Autowired
	private RemitBankTypeFacade remitBankTypeFacade;

	/**
	 * 打款记录查询(提现记录查询)
	 * 
	 * @return
	 */
	public String listRemittanceRecordUI() {
		return "listRemittanceRecord";
	}
	
	/**
	 * 打款记录查询(提现记录查询)
	 * 
	 * @return
	 */
	public String listRemittanceRecord() {
		String beginDate = getString("beginDate"); // 退款时间
		beginDate = ValidateUtils.isEmpty(beginDate) ? "" : beginDate;
		String endDate = getString("endDate");
		endDate = ValidateUtils.isEmpty(endDate) ? "" : endDate;
		String batchNo = getString("batchNo");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountNo", this.getCurrentUserInfo().getAccountNo());
		paramMap.put("beginTime", beginDate);
		paramMap.put("endTime", endDate);
		paramMap.put("batchNo", batchNo);
		
		// 回显查询条件
		super.pushData(this.getParamMap());
		// 页面参数回显
		putData("SettRecordStatusEnum", SettRecordStatusEnum.toList());
		putData("RemitBankTypeList", remitBankTypeFacade.listAll());

		pageBean = super.listSettRecord(beginDate, endDate, batchNo);
		pushData(pageBean);

		return "listRemittanceRecord";
	}

	/**
	 * 去提现页面
	 * 
	 * @return
	 */
	public String withdrawUI() {
		Account account = accountQueryFacade.getAccountByAccountNo(getCurrentUserInfo().getAccountNo());
		// 判断账户是否是激活状态
		if (account.getStatus().intValue() == AccountStatusEnum.INACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.ACCOUNT_STATUS_INACTIVE, "账户状态冻结");
		}

		List<UserBankAccount> bankCardList = userBankAccountFacade.listSettlementBankAccountByMemberUserNo(getCurrentUserInfo().getUserNo());
		putData("availableBalance", AmountUtil.roundDown(account.getAvailableBalance())); // 账户可用余额
		putData("bankCardList", bankCardList);
		return "withdrawUI";
	}

	/**
	 * 提现
	 * 
	 * @return
	 */
	public String withdraw() throws NumberFormatException, AccountBizException, BizException, CerSignException, RpcException, Exception {
		String withdrawAmount = StringTools.stringToTrim(getString("withdrawAmount"));// 提现金额
		String tradePwd = super.getPwd("tradePwd");// 交易密码
		String bankAccountNo = StringTools.stringToTrim(getString("bankAccountNo").split("\\$")[1]);// 银行卡号
		String batchNo = settQueryFacade.buildSettBatchNo(SettTypeEnum.SELF_HELP_SETTLEMENT); // 结算批次号//TODO 提现-----目前归为自助结算

		Account account = accountQueryFacade.getAccountByAccountNo(getCurrentUserInfo().getAccountNo());
		// 判断账户是否是激活状态
		if (account.getStatus().intValue() == AccountStatusEnum.INACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.ACCOUNT_STATUS_INACTIVE, "账户状态冻结");
		}
		// 判断用户所选提现银行正确
		UserBankAccount userBankAccount = userBankAccountFacade.getByBankAccountNoAndUserNo(bankAccountNo, getCurrentUserInfo().getUserNo());
		if (ValidateUtils.isEmpty(userBankAccount) || userBankAccount.getIsDelete().intValue() == PublicStatusEnum.ACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.WITHDRAW_BANK_IS_ERROR, "提现银行错误");
		}

		// 验证
		String msg = validateTradePwd(tradePwd);
		
		if(msg != null){
			putData("PAGE_ERROR_MSG", msg);
			putData("account", account);
			List<UserBankAccount> bankCardList = userBankAccountFacade.listSettlementBankAccountByMemberUserNo(getCurrentUserInfo().getUserNo());
			putData("availableBalance", AmountUtil.roundDown(account.getAvailableBalance())); // 账户可用余额
			putData("bankCardList", bankCardList);
			return "withdrawUI";
		}
		
		putData("withdrawAmount", withdrawAmount);
		putData("bankAccountNo", bankAccountNo);
		UserBankAccount bankCardList = userBankAccountFacade.getSettlementBankAccountByMerchantUserNo(getCurrentUserInfo().getUserNo());
		putData("account", account);
		putData("bankCardList", bankCardList);

		// 会员提现接口
		SettRecord settRecord = new SettRecord();
//		String batchNo = settQueryFacade.buildSettBatchNo(SettTypeEnum.SELF_HELP_SETTLEMENT); // 结算批次号//TODO 提现-----目前归为自助结算

		if (StringUtil.isBlank(batchNo)) {
			LOG.error("===>结算批次号为空");
			throw new SettBizException(SettBizException.SETTBATCHNO_NULL, "结算批次号为空");
		}
		settRecord.setBatchNo(batchNo);
		String remitRequestNo = remitRequestFacade.buildRemitRequestNo(); // 获取打款请求号
		if (StringUtil.isBlank(remitRequestNo)) {
			LOG.error("===>打款请求号为空");
			throw new SettBizException(SettBizException.REMITREQUESTNO_NULL, "打款请求号为空");
		}
		settRecord.setRemitNo(remitRequestNo);
		settRecord.setSettMode(SettModeTypeEnum.MEMBER_WITHDRAW.getValue());// 提现
		settRecord.setAccountNo(account.getAccountNo());
		settRecord.setUserType(UserTypeEnum.CUSTOMER.getValue());
		settRecord.setUserNo(account.getUserNo());
		settRecord.setSettDate(new Date());
		settRecord.setCurrencyType(CurrencyTypeEnum.RMB.getValue());

		// 结算银行信息
		settRecord.setBankCode(userBankAccount.getBankCode());
		settRecord.setBankChannelNo(userBankAccount.getBankChannelNo()); // 银行行号
		settRecord.setBankAccountName(userBankAccount.getBankAccountName());
		settRecord.setBankAccountNo(userBankAccount.getBankAccountNo());
		settRecord.setBankAccountType(userBankAccount.getBankAccountType());
		settRecord.setProvince(userBankAccount.getProvince());
		settRecord.setCity(userBankAccount.getCity());
		settRecord.setBankAccountAddress(userBankAccount.getBankAccountAddress());
		settRecord.setUserName(userBankAccount.getBankAccountName());
		settRecord.setSettAmount(new BigDecimal(withdrawAmount));

		settRecord.setSettStatus(SettRecordStatusEnum.WAIT_CONFIRM.getValue()); // 状态为：等待确认

		settBusinessFacade.memberWithdraw(settRecord);

		return "withdraw";
	}
	

	public void exportExcel() {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String filename = "WithDraw_" + sdf.format(new Date());
			this.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + new String((filename + ".xls").getBytes("ISO-8859-1"), "UTF-8"));
			OutputStream os = getHttpResponse().getOutputStream();
			DataField[] dataFields = new DataField[8];
			dataFields[0] = new DataField("序号", "no");
			dataFields[1] = new DataField("创建时间", "createTime");
			dataFields[2] = new DataField("打款批次号", "batchNo");
			dataFields[3] = new DataField("提现金额(元)", "remitAmount");
			dataFields[4] = new DataField("提现银行卡", "bankAccountNo");
			dataFields[5] = new DataField("提现卡所属银行", "bankName");
			dataFields[6] = new DataField("交易状态", "settStatus");
			dataFields[7] = new DataField("备注", "remark");
			final String beginDate = getString("beginDate");
			final String endDate = getString("endDate");
			// final Long accountId = getLoginedMemberAccountId();

			new ExcelDataExportor<Object>(dataFields, new ExportDataSource<Object>() {
				// DecimalFormat df = new DecimalFormat("#0.00");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				@SuppressWarnings({ "rawtypes", "unchecked" })
				public List getData() {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("accountNo", getCurrentUserInfo().getAccountNo());
					map.put("beginTime", ValidateUtils.isEmpty(beginDate) ? null : beginDate);
					map.put("endTime", ValidateUtils.isEmpty(endDate) ? null : endDate);
					PageParam pageParam = new PageParam(1, 5000);
					pageBean = settQueryFacade.listPageSettRecord(pageParam, map);

					List<Object> list = pageBean.getRecordList();
					List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
					if (list!=null&&!list.isEmpty()) {
						int temp = 0;
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> mapParam = new HashMap<String, Object>();
							SettRecord remittanceRecord = (SettRecord) list.get(i);
							mapParam.put("no", ++temp);
							mapParam.put("batchNo", remittanceRecord.getBatchNo());
							mapParam.put("createTime", sdf.format(remittanceRecord.getCreateTime()));
							mapParam.put("remitAmount", remittanceRecord.getRemitAmount());
							mapParam.put("bankAccountNo", StringTools.bankNoChange(remittanceRecord.getBankAccountNo()));
							List<RemitBankType> bankTypeList = remitBankTypeFacade.listAll();
							String bankName = "";
							for (RemitBankType bank : bankTypeList) {
								if (StringUtil.equals(remittanceRecord.getBankCode(), bank.getBankCode())) {
									bankName = bank.getTypeName();
								}
							}
							mapParam.put("bankName", bankName);

							mapParam.put("settStatus", SettRecordStatusEnum.getEnum(remittanceRecord.getSettStatus()).getDesc());
							mapParam.put("remark", remittanceRecord.getRemark());
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
