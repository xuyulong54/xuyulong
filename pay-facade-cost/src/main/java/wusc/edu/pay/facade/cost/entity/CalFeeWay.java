package wusc.edu.pay.facade.cost.entity;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * @Title: CalFeeWay.java 
 * @Description: 计费约束实体
 * @author zzh
 */
public class CalFeeWay extends BaseEntity {
	private static final long serialVersionUID = 6207546066163881123L;

	/**	维度ID	*/
	private Long dimensionId; 

	/**	约束名称	*/
	private String wayName; 

	/**	 免计费金额 	*/
	private BigDecimal feeFreeAmount = BigDecimal.ZERO;

	/**	状态	*/
	private Integer status;

	/**	计费周期	*/
	private Integer cycleType; 

	/**	自定义计费周期	*/
	private Integer cusCycleType;  

	/**	自定义计费日	*/
	private Date customizeDay; 

	/**	计费类型(单笔:SIMPLE,区间 :INTERVAL,阶梯:LADDER)	*/
	private Integer calType;  

	/**	计费方式(实收,后收)	*/
	private Integer calPeriod; 

	/**	计费角色(付款人,收款人,支付平台)	*/
	private Integer calRole; 
	
	/**	是否四舍五入（100-是，101-否）	*/
	private Integer isRound;

	/**	生效日期	*/
	private Date beginDate;  

	/**	失效日期	*/
	private Date endDate;  

	/**	MCC码	*/
	private String mcc;

	/**
	 * 维度ID
	 * 
	 * @return
	 */
	public Long getDimensionId() {
		return dimensionId;
	}

	/**
	 * 维度ID
	 * 
	 * @param dimensionId
	 */
	public void setDimensionId(Long dimensionId) {
		this.dimensionId = dimensionId;
	}

	/**
	 * 约束名称
	 * 
	 * @return
	 */
	public String getWayName() {
		return wayName;
	}

	/**
	 * 约束名称
	 * 
	 * @param wayName
	 */
	public void setWayName(String wayName) {
		this.wayName = wayName == null ? null : wayName.trim();
	}

	/**
	 * 免计费金额
	 * 
	 * @return
	 */
	public BigDecimal getFeeFreeAmount() {
		return feeFreeAmount;
	}

	/**
	 * 免计费金额
	 * 
	 * @param feeFreeAmount
	 */
	public void setFeeFreeAmount(BigDecimal feeFreeAmount) {
		this.feeFreeAmount = feeFreeAmount;
	}

	/**
	 * 状态
	 * 
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 状态
	 * 
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 自定义计费周期
	 * 
	 * @return
	 */
	public Integer getCusCycleType() {
		return cusCycleType;
	}

	/**
	 * 自定义计费周期
	 * 
	 * @param cusCycleType
	 */
	public void setCusCycleType(Integer cusCycleType) {
		this.cusCycleType = cusCycleType;
	}

	/**
	 * 自定义计费日
	 * 
	 * @return
	 */
	public Date getCustomizeDay() {
		return customizeDay;
	}

	/**
	 * 自定义计费日
	 * 
	 * @param customizeDay
	 */
	public void setCustomizeDay(Date customizeDay) {
		this.customizeDay = customizeDay;
	}

	/**
	 * 生效日期
	 * 
	 * @return
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * 生效日期
	 * 
	 * @param beginDate
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 失效日期
	 * 
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 失效日期
	 * 
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * MCC码
	 * 
	 * @return
	 */
	public String getMcc() {
		return mcc;
	}

	/**
	 * MCC码
	 * 
	 * @param mcc
	 */
	public void setMcc(String mcc) {
		this.mcc = mcc == null ? null : mcc.trim();
	}

	public Integer getCycleType() {
		return cycleType;
	}

	public void setCycleType(Integer cycleType) {
		this.cycleType = cycleType;
	}

	public Integer getCalType() {
		return calType;
	}

	public void setCalType(Integer calType) {
		this.calType = calType;
	}

	public Integer getCalPeriod() {
		return calPeriod;
	}

	public void setCalPeriod(Integer calPeriod) {
		this.calPeriod = calPeriod;
	}

	public Integer getCalRole() {
		return calRole;
	}

	public void setCalRole(Integer calRole) {
		this.calRole = calRole;
	}

	/**
	 * @return 是否四舍五入（100-是，101-否）
	 */
	public Integer getIsRound() {
		return isRound;
	}

	/**
	 * @param 是否四舍五入（100-是，101-否）
	 */
	public void setIsRound(Integer isRound) {
		this.isRound = isRound;
	}
	
	

}