package wusc.edu.pay.facade.fee.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * Desc: 费率规则历史表
 * 
 * @author lichao Date: 2014-7-1
 * 
 */
public class FeeRuleHistory extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 费率客户编号
	 */
	private String userNo;
	/**
	 * 客户名称
	 */
	private String userName;
	/**
	 * 用户类型-----枚举：UserTypeEnum
	 */
	private Integer userType;
	/**
	 * 计费项----枚举：CalculateFeeItemEnum
	 */
	private Integer calFeeItem;
	/**
	 * 支付产品
	 */
	private String payProduct;
	/**
	 * 支付方式---对应支付渠道
	 */
	private String frpCode;
	/**
	 * 生效时间
	 */
	private Date effectiveStart;
	/**
	 * 失效时间
	 */
	private Date effectiveEnd;
	/**
	 * 创建规则时间
	 */
	private Date createRule;
	/**
	 * 费率规则
	 */
	private String feeRule;
	

	public FeeRuleHistory() {
		super();
	}

	public FeeRuleHistory(String userName, Integer userType, Integer calFeeItem, String payProduct, String frpCode, Date effectiveStart, Date effectiveEnd, Date createRule, String feeRule,
			Date createTime) {
		super();
		this.userName = userName;
		this.userType = userType;
		this.calFeeItem = calFeeItem;
		this.payProduct = payProduct;
		this.frpCode = frpCode;
		this.effectiveStart = effectiveStart;
		this.effectiveEnd = effectiveEnd;
		this.createRule = createRule;
		this.feeRule = feeRule;
		this.createTime = createTime;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getCalFeeItem() {
		return calFeeItem;
	}

	public void setCalFeeItem(Integer calFeeItem) {
		this.calFeeItem = calFeeItem;
	}

	public String getPayProduct() {
		return payProduct;
	}

	public void setPayProduct(String payProduct) {
		this.payProduct = payProduct;
	}

	public String getFrpCode() {
		return frpCode;
	}

	public void setFrpCode(String frpCode) {
		this.frpCode = frpCode;
	}

	public Date getEffectiveStart() {
		return effectiveStart;
	}

	public void setEffectiveStart(Date effectiveStart) {
		this.effectiveStart = effectiveStart;
	}

	public Date getEffectiveEnd() {
		return effectiveEnd;
	}

	public void setEffectiveEnd(Date effectiveEnd) {
		this.effectiveEnd = effectiveEnd;
	}

	public Date getCreateRule() {
		return createRule;
	}

	public void setCreateRule(Date createRule) {
		this.createRule = createRule;
	}

	public String getFeeRule() {
		return feeRule;
	}

	public void setFeeRule(String feeRule) {
		this.feeRule = feeRule;
	}

}
