package wusc.edu.pay.facade.agent.entity;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/***
 * 代理商和商户关联表
 *  
 * @author huangbin
 * 
 */
public class AgentMerchantRelation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4793762972985842033L;

	private String merchantNo; // 商户编号
	private String firstAgentNo; // 一级代理商编号
	private String secondAgentNo; // 二级代理商编号
	private String thirdAgentNo; // 三级代理商编号
	private Integer applyRateType; // 商户申请费率类型
	private BigDecimal applyRate; // 商户申请费率
	private BigDecimal upperAmount; // 封顶金额
	private Integer mccType; // mcc类型
	private Integer merchantSafeLevel; // 商户安全级别

	private String merchantName;
	private String firstAgentName;
	private String secondAgentName;
	private String thirdAgentName;
	
	private String salesNo; // 销售人员编号
	private String salesName; // 销售人员名称
	
	private Date openDate;//开通时间
	
	private String remark; //  商户备注
	
	private String settMode; // 结算方式，支持多种 （1=T+0, 2=T+1, 3=T+5）
	
	private Integer terminalNum; // 绑定终端数量
	

	public Integer getTerminalNum() {
		return terminalNum;
	}

	public void setTerminalNum(Integer terminalNum) {
		this.terminalNum = terminalNum;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getFirstAgentNo() {
		return firstAgentNo;
	}

	public void setFirstAgentNo(String firstAgentNo) {
		this.firstAgentNo = firstAgentNo;
	}

	public String getSecondAgentNo() {
		return secondAgentNo;
	}

	public void setSecondAgentNo(String secondAgentNo) {
		this.secondAgentNo = secondAgentNo;
	}

	public String getThirdAgentNo() {
		return thirdAgentNo;
	}

	public void setThirdAgentNo(String thirdAgentNo) {
		this.thirdAgentNo = thirdAgentNo;
	}

	public Integer getApplyRateType() {
		return applyRateType;
	}

	public void setApplyRateType(Integer applyRateType) {
		this.applyRateType = applyRateType;
	}

	public BigDecimal getApplyRate() {
		return applyRate;
	}

	public void setApplyRate(BigDecimal applyRate) {
		this.applyRate = applyRate;
	}

	public BigDecimal getUpperAmount() {
		return upperAmount;
	}

	public void setUpperAmount(BigDecimal upperAmount) {
		this.upperAmount = upperAmount;
	}

	public Integer getMccType() {
		return mccType;
	}

	public void setMccType(Integer mccType) {
		this.mccType = mccType;
	}

	public Integer getMerchantSafeLevel() {
		return merchantSafeLevel;
	}

	public void setMerchantSafeLevel(Integer merchantSafeLevel) {
		this.merchantSafeLevel = merchantSafeLevel;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getFirstAgentName() {
		return firstAgentName;
	}

	public void setFirstAgentName(String firstAgentName) {
		this.firstAgentName = firstAgentName;
	}

	public String getSecondAgentName() {
		return secondAgentName;
	}

	public void setSecondAgentName(String secondAgentName) {
		this.secondAgentName = secondAgentName;
	}

	public String getThirdAgentName() {
		return thirdAgentName;
	}

	public void setThirdAgentName(String thirdAgentName) {
		this.thirdAgentName = thirdAgentName;
	}

	public String getSalesNo() {
		return salesNo;
	}

	public void setSalesNo(String salesNo) {
		this.salesNo = salesNo;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getSettMode() {
		return settMode;
	}

	public void setSettMode(String settMode) {
		this.settMode = settMode;
	}

	@Override
	public int hashCode() { 
		return this.getMerchantNo().hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof AgentMerchantRelation)) return false;
		AgentMerchantRelation cmi = (AgentMerchantRelation)obj;
		if(!cmi.getMerchantNo().equals(this.getMerchantNo())) return false;
		return true;
	}
}
