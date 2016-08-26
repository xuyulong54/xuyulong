package wusc.edu.pay.facade.remit.entity;

import java.math.BigDecimal;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @Title: 打款通道分流规则实体
 * @Description: 
 * @author zzh
 * @date 2014-7-22 上午11:49:58
 */
public class RemitChannelFlowRule extends BaseEntity{

	private static final long serialVersionUID = 1L;
	/**分流规则名称*/
	private String channelFlowName;
	/**打款通道编号*/
	private String channelCode;
	/**账户类型：1-对公，2-对私*/
    private String accountType;
    /**业务类型：1-会员提现，2-商户结算*/
    private String tradeType;
    /**包含的收款银行编号*/
    private String includeBankCode;
    /**不包含的收款银行编号*/
    private String excludeBankCode;
    /**最小额*/
    private BigDecimal minAmount;
    /**最大额*/
    private BigDecimal maxAmount;
    /**是否加急*/
    private Integer isUrgent;
    /**状态*/
    private Integer status;
    
    
    /**分流规则名称*/
	public String getChannelFlowName() {
		return channelFlowName;
	}
	/**分流规则名称*/
	public void setChannelFlowName(String channelFlowName) {
		this.channelFlowName = channelFlowName;
	}

	/**
	 * @return 打款通道编号
	 */
	public String getChannelCode() {
		return channelCode;
	}

	/**
	 * @param 打款通道编号
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	/**
	 * @return 账户类型：1-对公，2-对私
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param 账户类型：1-对公，2-对私
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return 业务类型：1-会员提现，2-商户结算
	 */
	public String getTradeType() {
		return tradeType;
	}

	/**
	 * @param 业务类型：1-会员提现，2-商户结算
	 */
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	/**
	 * @return 包含的收款银行编号
	 */
	public String getIncludeBankCode() {
		return includeBankCode;
	}

	/**
	 * @param 包含的收款银行编号
	 */
	public void setIncludeBankCode(String includeBankCode) {
		this.includeBankCode = includeBankCode;
	}

	/**
	 * @return 排除的收款银行编号
	 */
	public String getExcludeBankCode() {
		return excludeBankCode;
	}

	/**
	 * @param 排除的收款银行编号
	 */
	public void setExcludeBankCode(String excludeBankCode) {
		this.excludeBankCode = excludeBankCode;
	}

	/**
	 * @return 最小额
	 */
	public BigDecimal getMinAmount() {
		return minAmount;
	}

	/**
	 * @param 最小额
	 */
	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	/**
	 * @return 最大额
	 */
	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	/**
	 * @param 最大额
	 */
	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	/**
	 * @return 是否加急：100-是，101-否
	 */
	public Integer getIsUrgent() {
		return isUrgent;
	}

	/**
	 * @param 是否加急：100-是，101-否
	 */
	public void setIsUrgent(Integer isUrgent) {
		this.isUrgent = isUrgent;
	}

	/**
	 * @return 状态：100-有效，101-失效
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param 状态：100-有效，101-失效
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
    
    

}