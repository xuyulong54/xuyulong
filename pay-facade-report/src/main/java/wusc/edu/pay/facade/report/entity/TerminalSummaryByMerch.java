package wusc.edu.pay.facade.report.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/***
 * 根据商户统计终端报表
 * 
 * @author Administrator
 * 
 */
public class TerminalSummaryByMerch extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7229844185142719715L;
	private String statDate; //
	private Integer reportType; // 报表类型
	private String trxDate; // 交易时间
	private String merchantNo; // 商户编号
	private String merchantName; // 商户名称
	private Integer newMachineNum; // 采购机器
	private Integer ownedMachineNum; // 自备机器
	private Integer machineNum; // 新增机器

	public String getStatDate() {
		return statDate;
	}

	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public String getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Integer getNewMachineNum() {
		return newMachineNum;
	}

	public void setNewMachineNum(Integer newMachineNum) {
		this.newMachineNum = newMachineNum;
	}

	public Integer getOwnedMachineNum() {
		return ownedMachineNum;
	}

	public void setOwnedMachineNum(Integer ownedMachineNum) {
		this.ownedMachineNum = ownedMachineNum;
	}

	public Integer getMachineNum() {
		return machineNum;
	}

	public void setMachineNum(Integer machineNum) {
		this.machineNum = machineNum;
	}

}
