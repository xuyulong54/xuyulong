package wusc.edu.pay.facade.agent.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理商利润分配vo
 * 
 * @author Administrator
 * 
 */
public class AgentMerchantRelationVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1885653460472363340L;

	private Date createTime; // 创建时间
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
	private Integer status;
	private Integer mcc;
	private Integer merchantType;
	private String firstAgentName;
	private String secondAgentName;
	private String thirdAgentName;
	private Date openDate;
	
	private Integer terminalNum; //终端数量

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMcc() {
		return mcc;
	}

	public void setMcc(Integer mcc) {
		this.mcc = mcc;
	}

	public Integer getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(Integer merchantType) {
		this.merchantType = merchantType;
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

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Integer getTerminalNum() {
		return terminalNum;
	}

	public void setTerminalNum(Integer terminalNum) {
		this.terminalNum = terminalNum;
	}
}
