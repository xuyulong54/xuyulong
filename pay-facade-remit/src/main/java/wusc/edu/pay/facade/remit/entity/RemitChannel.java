package wusc.edu.pay.facade.remit.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @Title: 打款通道实体
 * @Description:
 * @author zzh
 * @date 2014-7-22 上午11:43:30
 */
public class RemitChannel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String channelCode; // 打款通道编号

	private String channelName; // 打款通道名称

	private Integer accountType; // 账户类型:1-对公，2-对私

	private String bankTypeCode; // 所属银行行别

	private Long limitNum; // 总笔数限制

	private BigDecimal minAmount; // 最小额

	private BigDecimal maxAmount; // 最大额

	private BigDecimal batchMinAmount; // 单个批次最小额

	private BigDecimal batchMaxAmount; // 单个批次最大额

	private Integer isUrgent; // 是否支持加急:100-是，101-否

	private Integer isConfirm; // 是否支持打款确认:100-是，101-否

	private Integer isAutoRemit; // 是否支持自动打款:100-是，101-否

	private Integer status; // 状态

	private String srcBankChannelNo; // 付款账号开户行行号

	private String srcBankName; // 付款账号开户行名称

	private String srcAccountNum; // 付款帐号

	private String srcAccountName; // 付款帐号户名

	private List<RemitRequest> listRemitRequest = new ArrayList<RemitRequest>();

	public List<RemitRequest> getListRemitRequest() {
		return listRemitRequest;
	}

	public void setListRemitRequest(List<RemitRequest> listRemitRequest) {
		this.listRemitRequest = listRemitRequest;
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
	 * @return 打款通道名称
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * @param 打款通道名称
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @return 账户类型：1-对公，2-对私
	 */
	public Integer getAccountType() {
		return accountType;
	}

	/**
	 * @param 账户类型
	 *            ：1-对公，2-对私
	 */
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return 所属银行行别
	 */
	public String getBankTypeCode() {
		return bankTypeCode;
	}

	/**
	 * @param 所属银行行别
	 */
	public void setBankTypeCode(String bankTypeCode) {
		this.bankTypeCode = bankTypeCode;
	}

	/**
	 * @return 总笔数限制
	 */
	public Long getLimitNum() {
		return limitNum;
	}

	/**
	 * @param 总笔数限制
	 */
	public void setLimitNum(Long limitNum) {
		this.limitNum = limitNum;
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
	 * @return 单个批次最小额
	 */
	public BigDecimal getBatchMinAmount() {
		return batchMinAmount;
	}

	/**
	 * @param 单个批次最小额
	 */
	public void setBatchMinAmount(BigDecimal batchMinAmount) {
		this.batchMinAmount = batchMinAmount;
	}

	/**
	 * @return 单个批次最大额
	 */
	public BigDecimal getBatchMaxAmount() {
		return batchMaxAmount;
	}

	/**
	 * @param 单个批次最大额
	 */
	public void setBatchMaxAmount(BigDecimal batchMaxAmount) {
		this.batchMaxAmount = batchMaxAmount;
	}

	/**
	 * @return 是否支持加急:100-是，101-否
	 */
	public Integer getIsUrgent() {
		return isUrgent;
	}

	/**
	 * @param 是否支持加急
	 *            :100-是，101-否
	 */
	public void setIsUrgent(Integer isUrgent) {
		this.isUrgent = isUrgent;
	}

	/**
	 * @return 是否支持打款确认:100-是，101-否
	 */
	public Integer getIsConfirm() {
		return isConfirm;
	}

	/**
	 * @param 是否支持打款确认
	 *            :100-是，101-否
	 */
	public void setIsConfirm(Integer isConfirm) {
		this.isConfirm = isConfirm;
	}

	/**
	 * @return 是否支持自动打款:100-是，101-否
	 */
	public Integer getIsAutoRemit() {
		return isAutoRemit;
	}

	/**
	 * @param 是否支持自动打款
	 *            :100-是，101-否
	 */
	public void setIsAutoRemit(Integer isAutoRemit) {
		this.isAutoRemit = isAutoRemit;
	}

	/**
	 * @return 状态：100-开启，101-关闭
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param 状态
	 *            ：100-开启，101-关闭
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return 付款账号开户行行号
	 */
	public String getSrcBankChannelNo() {
		return srcBankChannelNo;
	}

	/**
	 * @param 付款账号开户行行号
	 */
	public void setSrcBankChannelNo(String srcBankChannelNo) {
		this.srcBankChannelNo = srcBankChannelNo;
	}

	/**
	 * @return 付款账号开户行名称
	 */
	public String getSrcBankName() {
		return srcBankName;
	}

	/**
	 * @param 付款账号开户行名称
	 */
	public void setSrcBankName(String srcBankName) {
		this.srcBankName = srcBankName;
	}

	/**
	 * @return 付款帐号
	 */
	public String getSrcAccountNum() {
		return srcAccountNum;
	}

	/**
	 * @param 付款帐号
	 */
	public void setSrcAccountNum(String srcAccountNum) {
		this.srcAccountNum = srcAccountNum;
	}

	/**
	 * @return 付款帐号户名
	 */
	public String getSrcAccountName() {
		return srcAccountName;
	}

	/**
	 * @param 付款帐号户名
	 */
	public void setSrcAccountName(String srcAccountName) {
		this.srcAccountName = srcAccountName;
	}

}