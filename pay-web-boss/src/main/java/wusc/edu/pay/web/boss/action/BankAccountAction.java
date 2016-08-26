package wusc.edu.pay.web.boss.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.BankCode;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.common.web.struts.ModelDrivenUtil;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.account.service.AccountManagementFacade;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.bank.entity.BankAccount;
import wusc.edu.pay.facade.bank.service.BankAccountFacade;
import wusc.edu.pay.facade.remit.entity.RemitBankType;
import wusc.edu.pay.facade.remit.service.RemitBankTypeFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;

import com.opensymphony.xwork2.ModelDriven;

public class BankAccountAction extends BossBaseAction implements ModelDriven<BankAccount> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private BankAccountFacade bankAccountFacade;
	@Autowired
	private RemitBankTypeFacade remitBankTypeFacade;
	@Autowired
	private AccountManagementFacade accountManagementFacade;
	@Autowired
	private AccountQueryFacade accountQueryFacade;

	private BankAccount bankAccountParam = new BankAccount();
	private static final Log log = LogFactory.getLog(BankAccountAction.class);

	/**
	 * 获取银行账户列表
	 * 
	 * @return listBankAgreement or operateError.
	 */
	@Permission("bank:account:view")
	public String listBankAccount() {
		// 查询参数集合
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("openBank", getString("openBank")); // 银行渠道ID
		paramMap.put("bankAccount", getString("bankAccount")); // 商户编号
		paramMap.put("accountStatus", getString("accountStatus")); // 证书开关
		paramMap.put("userName", getString("userName")); // 银行账户名称
		paramMap.put("cooperationWay", getString("cooperationWay")); // 合作类型
		paramMap.put("accountNature", getString("accountNature")); // 账户性质
		super.pageBean = bankAccountFacade.listPage(getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap); // 回显查询条件
		return "listBankAccount";
	}

	/**
	 * 跳转添加银行账户信息页面
	 * */
	@Permission("bank:account:add")
	public String addBankAccountUI() {
		this.putData("bankCodeWithName", BankCode.toStringMap());
		List<RemitBankType> remitBankTypeList = remitBankTypeFacade.listActiveBank();
		this.putData("remitBankTypeList", remitBankTypeList);
		return "addBankAccountUI";
	}

	/**
	 * 设置账户信息
	 * 
	 * @return
	 */
	@Permission("bank:account:edit")
	public String setBankAccountUI() {
		return "addBankAccountUI";
	}

	/**
	 * 添加银行账户信息
	 * */
	@Permission("bank:account:add")
	public String addBankAccount() {
		BankAccount tempbankAccountParam = getModel();
		String errMsg = validateBankAccount(tempbankAccountParam);
		
		if (errMsg != null && !"".equals(errMsg)) {
			return operateError(errMsg);
		} else {
			BankAccount bankAccount = bankAccountFacade.getByBankAccount(tempbankAccountParam.getBankAccount());
			if (bankAccount != null) {
				return operateError("该银行账号已存在，请查证后再添加");
			}
			long num = bankAccountFacade.create(tempbankAccountParam);
			if (num <= 0) {
				log.error("创建银行账户失败");
				return operateError("创建添加银行账户信息失败");
			} else {
				String accountNo = accountManagementFacade.buildAccountNo(AccountTypeEnum.PRIVATE);
				if (StringUtils.isBlank(accountNo)) {
					log.error("创建账户编号失败");
					return operateError("创建添加银行账户信息失败");
				}
				try{
					long accountNum = accountManagementFacade.createPrivateAccount(tempbankAccountParam.getBankAccount(),
							accountNo , tempbankAccountParam.getBalance() , tempbankAccountParam.getPledgefDeposits());
					if (accountNum <= 0) {
						bankAccountFacade.deleteById(num);
						log.error("账户系统创建银行虚拟账户失败，删除银行账户信息");
						super.logSaveError("系统创建银行失败" + tempbankAccountParam.getBankAccount());
						return operateError("创建添加银行账户信息失败");
					}
				}catch(Exception e){
					bankAccountFacade.deleteById(num);
					log.error("账户系统创建银行虚拟账户失败，删除银行账户信息");
					super.logSaveError("系统创建银行失败" + tempbankAccountParam.getBankAccount());
					return operateError("创建添加银行账户信息失败");
				}
				
				super.logSave("系统创建银行" + tempbankAccountParam.getBankAccount());
			}
		}
		return operateSuccess();
	}

	/**
	 * 查看银行账户信息
	 * */
	@Permission("bank:account:view")
	public String viewBankAccount() {
		long id = getLong("id");
		bankAccountParam = bankAccountFacade.getById(id);
		if(!StringUtils.isBlank(bankAccountParam.getBankAccount())){
			Account account = accountQueryFacade.getAccountByUserNo(bankAccountParam.getBankAccount());
			this.pushData(account);
		}
		return "viewBankAccount";
	}

	/**
	 * 跳转到修改银行账户信息页面
	 * */
	@Permission("bank:account:edit")
	public String editBankAccountUI() {
		long id = getLong("id");
		bankAccountParam = bankAccountFacade.getById(id);
		if(!StringUtils.isBlank(bankAccountParam.getBankAccount())){
			Account account = accountQueryFacade.getAccountByUserNo(bankAccountParam.getBankAccount());
			this.pushData(account);
		}
		return "editBankAccountUI";
	}

	/**
	 * 修改银行账户信息
	 * */
	@Permission("bank:account:edit")
	public String editBankAccount() {
		Integer id = getInteger("id");
		if (id == null || id.intValue() == 0) {
			return operateError("请选择银行账户");
			// throw new BossBizException("请选择银行账户");
		}
		Integer accountStatus = getInteger("accountStatus");
		if (accountStatus == null || accountStatus.intValue() <= 0 || accountStatus.intValue() > 3) {
			return operateError("请选择正确的银行账户状态");
		}

		BankAccount tempbankAccountParam = bankAccountFacade.getById(id);
		if (tempbankAccountParam == null) {
			return operateError("错误的银行账户信息");
		}
		tempbankAccountParam.setAccountStatus(accountStatus);
		tempbankAccountParam.setComments(getString("comments"));
		long updateNum = bankAccountFacade.update(tempbankAccountParam);
		if (updateNum <= 0) {
			super.logSave("系统修改银行账户失败" + tempbankAccountParam.getBankAccount());
			return operateError("修改银行账户信息失败");
		} else {
			super.logSave("系统修改银行账户成功" + tempbankAccountParam.getBankAccount());
		}
		return operateSuccess();
	}

	public String bankAccountLookupList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountStatus", "1");
		paramMap.put("accountNature", getString("accountNature"));
//		paramMap.put("openBank", getString("bankName"));
		// 查询参数集合
		super.pageBean = bankAccountFacade.listPage(getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		this.putData("bankCodeWithName", BankCode.toStringMap());
		return "bankAccountLookupList";
	}

	public String validateBankAccount(BankAccount bankAccountParam) {
		StringBuffer errStr = new StringBuffer();
		String openBank = bankAccountParam.getOpenBank();// 开户银行
		if (ValidateUtils.isEmpty(openBank)) {
			errStr.append("请输入正确的银开户银行，<br/>");
		} else {
			if (!ValidateUtils.checkStrMinLengthByBytes(openBank, 6)) {
				errStr.append("开户银行长度不能小于6位，<br/>");
			} else if (!ValidateUtils.checkStrMaxLengthByBytes(openBank, 30)) {
				errStr.append("开户银行长度不能大于30，<br/>");
			}
		}
		Date opendate = bankAccountParam.getOpendate();// 开户日期
		if (ValidateUtils.isEmpty(opendate)) {
			errStr.append("请选择开户日期，<br/>");
		}
		String bankAmount = bankAccountParam.getBankAccount();// 银行账号
		if (ValidateUtils.isEmpty(bankAmount)) {
			errStr.append("请输入正确的银行账号，<br/>");
		} else {
			if (!ValidateUtils.checkStrMinLengthByBytes(bankAmount, 5)) {
				errStr.append("银行账号长度不能小于5位，<br/>");
			} else if (!ValidateUtils.checkStrMaxLengthByBytes(bankAmount, 20)) {
				errStr.append("银行账号长度不能大于20，<br/>");
			}
		}
		String bankNo = bankAccountParam.getBankNo();// 行号
		if (ValidateUtils.isEmpty(bankNo)) {
			errStr.append("请输入正确的行号，<br/>");
		} else {
			if (!ValidateUtils.checkStrMinLengthByBytes(bankNo, 5)) {
				errStr.append("行号长度不能小于5位，<br/>");
			} else if (!ValidateUtils.checkStrMaxLengthByBytes(bankNo, 15)) {
				errStr.append("行号长度不能大于15，<br/>");
			}
		}
		String userName = bankAccountParam.getUserName();// 户名
		if (ValidateUtils.isEmpty(userName)) {
			errStr.append("请输入正确的户名，<br/>");
		} else {
			if (!ValidateUtils.checkStrMinLengthByBytes(userName, 4)) {
				errStr.append("户名长度不能小于4位，<br/>");
			} else if (!ValidateUtils.checkStrMaxLengthByBytes(userName, 60)) {
				errStr.append("户名长度不能大于60，<br/>");
			}
		}
		String openBankAddress = bankAccountParam.getOpenBankAddress();// 开户行地址
		if (ValidateUtils.isEmpty(openBankAddress)) {
			errStr.append("请输入正确的开户行地址，<br/>");
		} else {
			if (!ValidateUtils.checkStrMinLengthByBytes(openBankAddress, 6)) {
				errStr.append("开户行地址长度不能小于6位，<br/>");
			} else if (!ValidateUtils.checkStrMaxLengthByBytes(openBankAddress, 90)) {
				errStr.append("开户行地址长度不能大于90，<br/>");
			}
		}
		String operator = bankAccountParam.getOperator();// 开户经办人
		if (ValidateUtils.isEmpty(operator)) {
			errStr.append("请输入正确的开户经办人，<br/>");
		} else {
			if (!ValidateUtils.checkStrMinLengthByBytes(operator, 4)) {
				errStr.append("开户经办人长度不能小于4位，<br/>");
			} else if (!ValidateUtils.checkStrMaxLengthByBytes(operator, 30)) {
				errStr.append("开户经办人长度不能大于30，<br/>");
			}
		}
		Integer amountNature = bankAccountParam.getAccountNature();// 账户性质
		if (ValidateUtils.isEmpty(amountNature)) {
			errStr.append("请选择账户性质，<br/>");
		} else {
			if (amountNature <= 0) {
				errStr.append("账户性质有误，<br/>");
			}
		}
		Integer amountStatus = bankAccountParam.getAccountStatus();// 账户状态
		if (ValidateUtils.isEmpty(amountStatus)) {
			errStr.append("请选择账户状态，<br/>");
		} else {
			if (amountStatus <= 0) {
				errStr.append("账户状态有误，<br/>");
			}
		}
		Integer amountType = bankAccountParam.getAccountType();// 账户类型
		if (ValidateUtils.isEmpty(amountType)) {
			errStr.append("请选择账户类型，<br/>");
		} else {
			if (amountType <= 0) {
				errStr.append("账户类型有误，<br/>");
			}
		}
		Integer isOnlineBank = bankAccountParam.getIsOnlineBank();// 是否有网银
		if (ValidateUtils.isEmpty(isOnlineBank)) {
			errStr.append("请选择是否有网银，<br/>");
		} else {
			if (isOnlineBank <= 0) {
				errStr.append("网银选择有误，<br/>");
			} else if (isOnlineBank == 1) {
				Double onlineBankFee = bankAccountParam.getOnlineBankFee();// 网银管理费
				if (ValidateUtils.isEmpty(onlineBankFee)) {
					errStr.append("请输入网银管理费，<br/>");
				} else {
					if (onlineBankFee < 0) {
						errStr.append("网银管理费有误，<br/>");
					}
				}
			} else if (isOnlineBank == 2) {
				bankAccountParam.setOnlineBankFee(0d);
			}
		}

		Double amountFee = bankAccountParam.getAccountMngFee();// 账户管理费
		if (ValidateUtils.isEmpty(amountFee)) {
			errStr.append("请输入正确的账户管理费，<br/>");
		} else {
			if (amountFee < 0) {
				errStr.append("账户管理费有误，<br/>");
			}
		}
		Integer cooperationWay = bankAccountParam.getCooperationWay();// 合作方式
		if (ValidateUtils.isEmpty(cooperationWay)) {
			errStr.append("请选择合作方式，<br/>");
		} else {
			if (cooperationWay <= 0) {
				errStr.append("合作方式有误，<br/>");
			}
		}
		Integer receiptForm = bankAccountParam.getReceiptForm();// 回单形式
		if (ValidateUtils.isEmpty(receiptForm)) {
			errStr.append("请选择回单形式，<br/>");
		} else {
			if (receiptForm <= 0) {
				errStr.append("回单形式有误，<br/>");
			}
		}
		String reserveSeal = bankAccountParam.getReserveSeal();// 预留印鉴记录
		/*if (ValidateUtils.isEmpty(reserveSeal)) {
			errStr.append("请输入正确的预留印鉴记录，<br/>");
		} else {*/
			if (!ValidateUtils.isEmpty(reserveSeal)&&!ValidateUtils.checkStrMaxLengthByBytes(reserveSeal, 150)) {
				errStr.append("预留印鉴记录长度不能大于150，<br/>");
			}
		/*}*/
		String proposer = bankAccountParam.getProposer();// 申请人
		if (ValidateUtils.isEmpty(proposer)) {
			errStr.append("请输入正确的申请人，<br/>");
		} else {
			if (!ValidateUtils.checkStrMinLengthByBytes(proposer, 4)) {
				errStr.append("申请人长度不能小于4位，<br/>");
			} else if (!ValidateUtils.checkStrMaxLengthByBytes(proposer, 60)) {
				errStr.append("申请人长度不能大于60，<br/>");
			}
		}
		/*
		 * Long linkmanID = bankAccountParam.getLinkmanID();//银行联系人ID
		 * if(ValidateUtils.isEmpty(linkmanID)){
		 * errStr.append("请输入正确的银行联系人ID，<br/>"); }else{ if(amountNature < 0){
		 * errStr.append("银行联系人ID有误，<br/>"); } }
		 */
		String openAccountObligate = bankAccountParam.getOpenAccountObligate();// 开户信息预留人
		if (ValidateUtils.isEmpty(openAccountObligate)) {
			errStr.append("请输入正确的开户信息预留人，<br/>");
		} else {
			if (!ValidateUtils.checkStrMinLengthByBytes(openAccountObligate, 4)) {
				errStr.append("开户信息预留人长度不能小于4位，<br/>");
			} else if (!ValidateUtils.checkStrMaxLengthByBytes(openAccountObligate, 60)) {
				errStr.append("开户信息预留人长度不能大于60，<br/>");
			}
		}
		String onlineBankObligate = bankAccountParam.getOnlineBankObligate();// 网银验证码预留人
		if (ValidateUtils.isEmpty(onlineBankObligate)) {
			errStr.append("请输入正确的网银验证码预留人，<br/>");
		} else {
			if (!ValidateUtils.checkStrMinLengthByBytes(onlineBankObligate, 4)) {
				errStr.append("网银验证码预留人长度不能小于4位，<br/>");
			} else if (!ValidateUtils.checkStrMaxLengthByBytes(onlineBankObligate, 60)) {
				errStr.append("网银验证码预留人长度不能大于60，<br/>");
			}
		}
		String bigAmounttransferObligate = bankAccountParam.getBigAmounttransferObligate();// 大额转款确定预留人
		if (ValidateUtils.isEmpty(bigAmounttransferObligate)) {
			errStr.append("请输入正确的大额转款确定预留人，<br/>");
		} else {
			if (!ValidateUtils.checkStrMinLengthByBytes(bigAmounttransferObligate, 4)) {
				errStr.append("大额转款确定预留人长度不能小于4位，<br/>");
			} else if (!ValidateUtils.checkStrMaxLengthByBytes(bigAmounttransferObligate, 60)) {
				errStr.append("大额转款确定预留人长度不能大于60，<br/>");
			}
		}
		Double pledgefDeposits = bankAccountParam.getPledgefDeposits(); // 质押保证金
		if (ValidateUtils.isEmpty(pledgefDeposits)) {
			errStr.append("请输入质押保证金，<br/>");
		} else {
			if (pledgefDeposits < 0) {
				errStr.append("质押保证金有误，<br/>");
			}
		}
		return errStr.toString();
	}

	@Override
	public BankAccount getModel() {
		return (BankAccount) ModelDrivenUtil.cleanModel(bankAccountParam);
	}
	
}
