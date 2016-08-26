package wusc.edu.pay.facade.fee.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 费率节点
 * 
 * @desc
 * @author shenjialong
 * @date 2014-6-26,下午3:50:57
 */
public class FeeNode extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 计费项
	 */
	private Integer calculateFeeItem;
	/**
	 * 节点类型
	 */
	private Integer nodeType;
	/**
	 * 节点名称
	 */
	private String nodeName;
	/**
	 * 描述
	 */
	private String remark;
	/**
	 * 状态
	 */
	private Integer status;
	

	public Integer getCalculateFeeItem() {
		return calculateFeeItem;
	}

	public void setCalculateFeeItem(Integer calculateFeeItem) {
		this.calculateFeeItem = calculateFeeItem;
	}

	public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
