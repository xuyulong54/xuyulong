package wusc.edu.pay.facade.bank.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @描述: 银行账户信息表参数实体.
 * @作者: WuShuicheng .
 * @创建时间: 2014-4-15, 下午2:31:56
 */
public class BankAccount extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	
	/** 开户银行：具体的银行名称 */
	private String openBank;
	
	/** 开户行地址：具体到开户支行地址 */
	private String openBankAddress;
	
	/** 开户日期：账户开户的日期 */
	private Date opendate;
	
	/** 银行账号：具体的账户编号 */
	private String bankAccount;
	
	/** 银行行号：具体视银行支行行号，确定与银联系统数据相同 */
	private String bankNo;
	
	/** 银行账户名称：具体的银行账户名称 */
	private String userName;
	
	/** 开户经办人：具体经办人 */
	private String operator;
	
	/** 合作方式：1：存管银行、2：合作银行 */
	private Integer cooperationWay;
	
	/** 账户性质：1：备付金存管账户、2：自有资金账户、3：备付金收付账户 、4：备付金汇缴账户 */
	private Integer accountNature;
	
	/** 账户状态：1：正常、2：待销户、3：已销户 */
	private Integer accountStatus;
	
	/** 账户类型：1:活期 、2:定期 、3:通支 */
	private Integer accountType;
	
	/** 网银管理费：按照具体情况填写 */
	private Double onlineBankFee;
	
	/** 账户管理费：按照具体情况填写 */
	private Double accountMngFee;
	
	/** 是否有网银：1：是、2：否 */
	private Integer isOnlineBank;
	
	/** 回单形式：1:邮寄、2:自取、3:打印 */
	private Integer receiptForm;
	
	/** 预留印鉴记录 */
	private String reserveSeal;
	
	/** 申请人：具体申请人 */
	private String proposer;
	
	/** 银行联系方式：姓名、类型、电话、邮箱（长文本存放） */
	private String linkMan;
	
	/** 开户信息预留人 */
	private String openAccountObligate;
	
	/** 网银验证码预留人 */
	private String onlineBankObligate;
	
	/** 大额转款确定预留人 */
	private String bigAmounttransferObligate;
	
	/** 质押保证金 */
	private Double pledgefDeposits;
	
	/** 备注 */
	private String comments;

	/**
	 * 初始化金额
	 */
	private Double balance;
	
	/**
	 * 初始化金额
	 */
	public Double getBalance() {
		return balance;
	}
	
	/**
	 * 初始化金额
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	/**
	 * 开户银行：具体的银行名称
	 * @return
	 */
	public String getOpenBank() {
		return openBank;
	}

	/**
	 * 开户银行：具体的银行名称
	 * @param openBank
	 */
	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	/**
	 * 开户行地址：具体到开户支行地址
	 * @return
	 */
	public String getOpenBankAddress() {
		return openBankAddress;
	}

	/**
	 * 开户行地址：具体到开户支行地址
	 * @param openBankAddress
	 */
	public void setOpenBankAddress(String openBankAddress) {
		this.openBankAddress = openBankAddress;
	}

	/**
	 * 开户日期：账户开户的日期
	 * @return
	 */
	public Date getOpendate() {
		return opendate;
	}

	/**
	 * 开户日期：账户开户的日期
	 * @param opendate
	 */
	public void setOpendate(Date opendate) {
		this.opendate = opendate;
	}

	/**
	 * 银行账号：具体的账户编号
	 * @return
	 */
	public String getBankAccount() {
		return bankAccount;
	}

	/**
	 * 银行账号：具体的账户编号
	 * @param bankAccount
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * 银行行号：具体视银行支行行号，确定与银联系统数据相同
	 * @return
	 */
	public String getBankNo() {
		return bankNo;
	}

	/**
	 * 银行行号：具体视银行支行行号，确定与银联系统数据相同
	 * @param bankNo
	 */
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	/**
	 * 银行账户名称：具体的银行账户名称
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 银行账户名称：具体的银行账户名称
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 开户经办人：具体经办人
	 * @return
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * 开户经办人：具体经办人
	 * @param operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 合作方式：1：存管银行、2：合作银行
	 * @return
	 */
	public Integer getCooperationWay() {
		return cooperationWay;
	}

	/**
	 * 合作方式：1：存管银行、2：合作银行
	 * @param cooperationWay
	 */
	public void setCooperationWay(Integer cooperationWay) {
		this.cooperationWay = cooperationWay;
	}

	/**
	 * 账户性质：1：备付金存管账户、2：自有资金账户、3：备付金收付账户 、4：备付金汇缴账户
	 * @return
	 */
	public Integer getAccountNature() {
		return accountNature;
	}

	/**
	 * 账户性质：1：备付金存管账户、2：自有资金账户、3：备付金收付账户 、4：备付金汇缴账户
	 * @param accountNature
	 */
	public void setAccountNature(Integer accountNature) {
		this.accountNature = accountNature;
	}

	/**
	 * 账户状态：1：正常、2：待销户、3：已销户
	 * @return
	 */
	public Integer getAccountStatus() {
		return accountStatus;
	}

	/**
	 * 账户状态：1：正常、2：待销户、3：已销户
	 * @param accountStatus
	 */
	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * 账户类型：1:活期 、2:定期 、3:通支
	 * @return
	 */
	public Integer getAccountType() {
		return accountType;
	}

	/**
	 * 账户类型：1:活期 、2:定期 、3:通支
	 * @param accountType
	 */
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	/**
	 * 网银管理费：按照具体情况填写
	 * @return
	 */
	public Double getOnlineBankFee() {
		return onlineBankFee;
	}

	/**
	 * 网银管理费：按照具体情况填写
	 * @param onlineBankFee
	 */
	public void setOnlineBankFee(Double onlineBankFee) {
		this.onlineBankFee = onlineBankFee;
	}

	/**
	 * 账户管理费：按照具体情况填写
	 * @return
	 */
	public Double getAccountMngFee() {
		return accountMngFee;
	}

	/**
	 * 账户管理费：按照具体情况填写
	 * @param accountMngFee
	 */
	public void setAccountMngFee(Double accountMngFee) {
		this.accountMngFee = accountMngFee;
	}

	/**
	 * 是否有网银：1：是、2：否
	 * @return
	 */
	public Integer getIsOnlineBank() {
		return isOnlineBank;
	}

	/**
	 * 是否有网银：1：是、2：否
	 * @param isOnlineBank
	 */
	public void setIsOnlineBank(Integer isOnlineBank) {
		this.isOnlineBank = isOnlineBank;
	}

	/**
	 * 回单形式：1:邮寄、2:自取、3:打印
	 * @return
	 */
	public Integer getReceiptForm() {
		return receiptForm;
	}

	/**
	 * 回单形式：1:邮寄、2:自取、3:打印
	 * @param receiptForm
	 */
	public void setReceiptForm(Integer receiptForm) {
		this.receiptForm = receiptForm;
	}

	/**
	 * 预留印鉴记录
	 * @return
	 */
	public String getReserveSeal() {
		return reserveSeal;
	}

	/**
	 * 预留印鉴记录
	 * @param reserveSeal
	 */
	public void setReserveSeal(String reserveSeal) {
		this.reserveSeal = reserveSeal;
	}

	/**
	 * 申请人：具体申请人
	 * @return
	 */
	public String getProposer() {
		return proposer;
	}

	/**
	 * 申请人：具体申请人
	 * @param proposer
	 */
	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	/**
	 * 银行联系方式：姓名、类型、电话、邮箱（长文本存放）
	 * @return
	 */
	public String getLinkMan() {
		return linkMan;
	}

	/**
	 * 银行联系方式：姓名、类型、电话、邮箱（长文本存放）
	 * @param linkMan
	 */
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	/**
	 * 开户信息预留人
	 * @return
	 */
	public String getOpenAccountObligate() {
		return openAccountObligate;
	}

	/**
	 * 开户信息预留人
	 * @param openAccountObligate
	 */
	public void setOpenAccountObligate(String openAccountObligate) {
		this.openAccountObligate = openAccountObligate;
	}

	/**
	 * 网银验证码预留人
	 * @return
	 */
	public String getOnlineBankObligate() {
		return onlineBankObligate;
	}

	/**
	 * 网银验证码预留人
	 * @param onlineBankObligate
	 */
	public void setOnlineBankObligate(String onlineBankObligate) {
		this.onlineBankObligate = onlineBankObligate;
	}

	/**
	 * 大额转款确定预留人
	 * @return
	 */
	public String getBigAmounttransferObligate() {
		return bigAmounttransferObligate;
	}

	/**
	 * 大额转款确定预留人
	 * @param bigAmounttransferObligate
	 */
	public void setBigAmounttransferObligate(String bigAmounttransferObligate) {
		this.bigAmounttransferObligate = bigAmounttransferObligate;
	}

	/**
	 * 质押保证金
	 * @return
	 */
	public Double getPledgefDeposits() {
		return pledgefDeposits;
	}

	/**
	 * 质押保证金
	 * @param pledgefDeposits
	 */
	public void setPledgefDeposits(Double pledgefDeposits) {
		this.pledgefDeposits = pledgefDeposits;
	}

	/**
	 * 备注
	 * @return
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * 备注
	 * @param comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
