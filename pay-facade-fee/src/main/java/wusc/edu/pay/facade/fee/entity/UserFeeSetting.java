package wusc.edu.pay.facade.fee.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 用户费率设置表
 * @desc  
 * @author shenjialong
 * @date  2014-6-26,下午3:43:41
 */
public class UserFeeSetting extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 费率节点ID
	 */
	private Long feeNodeId;
	/**
	 * 费率用户编号（商户编号、结算账号等）
	 */
	private String userNo;
	/**
	 * 费率用户名称
	 */
	private String userName;
	/**
	 * 用户类型
	 */
	private Integer userType;
	/**
	 * 计费项
	 */
	private Integer calculateFeeItem;
	/**
	 * 状态
	 */
	private Integer status;
	
	public Long getFeeNodeId() {
		return feeNodeId;
	}
	public void setFeeNodeId(Long feeNodeId) {
		this.feeNodeId = feeNodeId;
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
	public Integer getCalculateFeeItem() {
		return calculateFeeItem;
	}
	public void setCalculateFeeItem(Integer calculateFeeItem) {
		this.calculateFeeItem = calculateFeeItem;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
