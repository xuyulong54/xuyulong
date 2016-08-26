package wusc.edu.pay.facade.agent.entity;

import java.math.BigDecimal;

import wusc.edu.pay.common.entity.BaseEntity;


/***
 * 代理商层级关系表
 * 
 * @author huangbin
 * 
 */
public class AgentRelation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6261544974000371348L;

	private String agentNo; // 代理商编号
	private String parentAgentNo; // 上级代理商编号
	private String parentUpAgentNo; // 上上级代理商编号
	private Integer level; // 代理商级别
	private BigDecimal splitProfit = BigDecimal.ZERO; // 分润
	private Integer agentStatus; // 代理商状态
	private BigDecimal affiliateAmount; // 加盟费
	private String agentName;
	private String parentAgentName;
	private String parentUpAgentName;

	// 附加字段，便于页面显示
	private BigDecimal parentSplitProfit = BigDecimal.ZERO;
	private BigDecimal parentUpSplitProfit = BigDecimal.ZERO;
	
	
	private String salesNo; // 销售人员编号
	private String salesName; // 销售人员名称
	

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public String getParentAgentNo() {
		return parentAgentNo;
	}

	public void setParentAgentNo(String parentAgentNo) {
		this.parentAgentNo = parentAgentNo;
	}

	public String getParentUpAgentNo() {
		return parentUpAgentNo;
	}

	public void setParentUpAgentNo(String parentUpAgentNo) {
		this.parentUpAgentNo = parentUpAgentNo;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public BigDecimal getSplitProfit() {
		return splitProfit;
	}

	public void setSplitProfit(BigDecimal splitProfit) {
		this.splitProfit = splitProfit;
	}

	public Integer getAgentStatus() {
		return agentStatus;
	}

	public void setAgentStatus(Integer agentStatus) {
		this.agentStatus = agentStatus;
	}

	public BigDecimal getAffiliateAmount() {
		return affiliateAmount;
	}

	public void setAffiliateAmount(BigDecimal affiliateAmount) {
		this.affiliateAmount = affiliateAmount;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getParentAgentName() {
		return parentAgentName;
	}

	public void setParentAgentName(String parentAgentName) {
		this.parentAgentName = parentAgentName;
	}

	public String getParentUpAgentName() {
		return parentUpAgentName;
	}

	public void setParentUpAgentName(String parentUpAgentName) {
		this.parentUpAgentName = parentUpAgentName;
	}

	public BigDecimal getParentSplitProfit() {
		return parentSplitProfit;
	}

	public void setParentSplitProfit(BigDecimal parentSplitProfit) {
		this.parentSplitProfit = parentSplitProfit;
	}

	public BigDecimal getParentUpSplitProfit() {
		return parentUpSplitProfit;
	}

	public void setParentUpSplitProfit(BigDecimal parentUpSplitProfit) {
		this.parentUpSplitProfit = parentUpSplitProfit;
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

}
