package wusc.edu.pay.facade.settlement.entity;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 每日待结算汇总实体
 * 
 * @author pc-Along
 * 
 */
public class SettDailyCollect extends BaseEntity {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 4096169383852235862L;

	/** 账户编号 **/
	private String accountNo;

	/** 汇总日期 **/
	private Date collectDate;

	/** 汇总类型(参考枚举:SettDailyCollectTypeEnum) **/
	private Integer collectType;

	/** 结算批次号(结算之后再回写过来) **/
	private String batchNo;

	/** 交易总金额 **/
	private BigDecimal totalAmount = BigDecimal.ZERO;

	/** 交易总笔数 **/
	private Integer totalCount = 0;

	/** 结算状态(参考枚举:SettDailyCollectStatusEnum) **/
	private Integer settStatus;

	/** 描述 **/
	private String remark;
	
	/** 风险预存期 **/
	private Integer riskDay;
	
	/** 用户编号 ,只用作显示 **/
	private String userNo; 
	
	/** 用户姓名 ,只用作显示 **/
	private String realName;
	
	/**
	 * 账户编号
	 * 
	 * @return
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * 账户编号
	 * 
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo == null ? null : accountNo.trim();
	}

	/**
	 * 汇总日期
	 * 
	 * @return
	 */
	public Date getCollectDate() {
		return collectDate;
	}

	/**
	 * 汇总日期
	 * 
	 * @param collectDate
	 */
	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

	/**
	 * 汇总类型(参考枚举:SettDailyCollectTypeEnum)
	 * 
	 * @return
	 */
	public Integer getCollectType() {
		return collectType;
	}

	/**
	 * 汇总类型(参考枚举:SettDailyCollectTypeEnum)
	 * 
	 * @param collectType
	 */
	public void setCollectType(Integer collectType) {
		this.collectType = collectType;
	}

	/**
	 * 结算批次号(结算之后再回写过来)
	 * 
	 * @return
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * 结算批次号(结算之后再回写过来)
	 * 
	 * @param batchNo
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo == null ? null : batchNo.trim();
	}

	/**
	 * 交易总金额
	 * 
	 * @return
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * 交易总金额
	 * 
	 * @param totalAmount
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * 交易总笔数
	 * 
	 * @return
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	/**
	 * 交易总笔数
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 结算状态(参考枚举:SettDailyCollectStatusEnum)
	 * 
	 * @return
	 */
	public Integer getSettStatus() {
		return settStatus;
	}

	/**
	 * 结算状态(参考枚举:SettDailyCollectStatusEnum)
	 * 
	 * @param settStatus
	 */
	public void setSettStatus(Integer settStatus) {
		this.settStatus = settStatus;
	}

	/**
	 * 备注
	 * 
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 备注
	 * 
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
	/**
	 * 风险预存期
	 */
	public Integer getRiskDay() {
		return riskDay;
	}

	/**
	 * 风险预存期
	 */
	public void setRiskDay(Integer riskDay) {
		this.riskDay = riskDay;
	}

	/*********************************************/
 
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
}