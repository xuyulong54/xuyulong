package wusc.edu.pay.facade.report.entity;

import java.math.BigDecimal;

import wusc.edu.pay.common.entity.BaseEntity;


/***
 * 区域汇总
 * 
 * @author Administrator
 * 
 */
public class AreaSummary extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5760349093193037703L;
	private String statDate ; // 
	private Integer reportType; // 报表类型
	private String trxDate; // 交易时间
	private String areaCode; // 区域
	private Integer totalCount; // 总笔数
	private BigDecimal totalAmount; // 总金额
	
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
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
