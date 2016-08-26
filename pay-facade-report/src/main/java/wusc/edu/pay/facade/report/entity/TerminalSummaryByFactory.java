package wusc.edu.pay.facade.report.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/***
 * 根据厂家类型统计终端报表
 * 
 * @author Administrator
 * 
 */
public class TerminalSummaryByFactory extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8835146446683845212L;

	private String statDate ; // 
	private Integer reportType; // 报表类型
	private String trxDate; // 交易时间
	private String factoryName; // 厂家名称
	private String terminalType;// 终端类型
	private Integer newMachineNum; // 采购机器
	private Integer ownedMachineNum; // 自备机器
	private Integer machineNum; //入库总数
	private Integer assignNum; // 分配数量
	private Integer bindingNum; // 绑定数量
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
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
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
	public Integer getAssignNum() {
		return assignNum;
	}
	public void setAssignNum(Integer assignNum) {
		this.assignNum = assignNum;
	}
	public Integer getBindingNum() {
		return bindingNum;
	}
	public void setBindingNum(Integer bindingNum) {
		this.bindingNum = bindingNum;
	}
}
