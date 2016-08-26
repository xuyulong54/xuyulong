package wusc.edu.pay.facade.fee.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 费率维度实体
 * 
 * @desc
 * @author shenjialong
 * @date 2014-6-26,下午4:00:18
 */
public class FeeDimension extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 费率节点Id
	 */
	private Long feeNodeId;

	/**
	 * 支付产品编码
	 */
	private String payProduct;
	/**
	 * 支付产品名称
	 */
	private String payProductName;

	/**
	 * 支付方式
	 */
	private String frpCode;
	/**
	 * 支付接口编码
	 */
	private String bankChannelCode;
	/**
	 * 是否有效(PublicStatusEnum)
	 */
	private Integer status;

	public Long getFeeNodeId() {
		return feeNodeId;
	}

	public void setFeeNodeId(Long feeNodeId) {
		this.feeNodeId = feeNodeId;
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

	public String getBankChannelCode() {
		return bankChannelCode;
	}

	public void setBankChannelCode(String bankChannelCode) {
		this.bankChannelCode = bankChannelCode;
	}

	public String getPayProductName() {
		return payProductName;
	}

	public void setPayProductName(String payProductName) {
		this.payProductName = payProductName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
