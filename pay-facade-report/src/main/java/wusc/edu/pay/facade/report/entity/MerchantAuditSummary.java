package wusc.edu.pay.facade.report.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/***
 * 商户审核报表
 * 
 * @author Administrator
 * 
 */
public class MerchantAuditSummary extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4881125264459958812L;
	private String statDate ; // 
	private Integer reportType; // 报表类型
	private String trxDate; // 交易时间
	private Integer createdNum; // 以创建的商户数量
	private Integer trialPassNum;// 初审通过数量
	private Integer trialNoPassNum;// 初审不通过数量
	private Integer finalPassNum; // 终审通过数量
	private Integer finalNoPassNum; // 终审未通过数量

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public String getStatDate() {
		return statDate;
	}

	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}

	public String getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}

	public Integer getCreatedNum() {
		return createdNum;
	}

	public void setCreatedNum(Integer createdNum) {
		this.createdNum = createdNum;
	}

	public Integer getTrialPassNum() {
		return trialPassNum;
	}

	public void setTrialPassNum(Integer trialPassNum) {
		this.trialPassNum = trialPassNum;
	}

	public Integer getTrialNoPassNum() {
		return trialNoPassNum;
	}

	public void setTrialNoPassNum(Integer trialNoPassNum) {
		this.trialNoPassNum = trialNoPassNum;
	}

	public Integer getFinalPassNum() {
		return finalPassNum;
	}

	public void setFinalPassNum(Integer finalPassNum) {
		this.finalPassNum = finalPassNum;
	}

	public Integer getFinalNoPassNum() {
		return finalNoPassNum;
	}

	public void setFinalNoPassNum(Integer finalNoPassNum) {
		this.finalNoPassNum = finalNoPassNum;
	}

}
