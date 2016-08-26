package wusc.edu.pay.facade.report.entity;

import java.math.BigDecimal;

import wusc.edu.pay.common.entity.BaseEntity;


/***
 * 错误代码统计报表
 * 
 * @author Administrator
 * 
 */
public class ErrorCodeSummary extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2505640137325758429L;

	private String statDate ; // 
	private Integer reportType; // 报表类型
	private String trxDate; // 交易时间
	private String errorCode; // 区域
	private Integer totalCount; // 总笔数
	private BigDecimal totalAmount; // 总金额
	private String errorMsg; // 错误代码说明

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

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
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

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
