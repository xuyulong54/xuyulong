package wusc.edu.pay.facade.fee.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * Desc: 费率维度DTO
 * @author lichao
 * Date: 2014-7-2
 *
 */
public class FeeDimensionDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 费率维度id
	 */
	private Long feeDimensionId;
	/**
	 * 创建时间
	 */
	private Date createTime = new Date();

	/**
	 * 费率节点Id
	 */
	private Long feeNodeId;

	/**
	 * 支付产品编码
	 */
	private String payProduct;

	/**
	 * 支付方式
	 */
	private String frpCode;
	/**
	 * 支付接口编码
	 */
	private String bankChannelCode;
	/**
	 * 费率规则组
	 * （必须）
	 */
	private List<FeeRuleDTO> feeRuleList;
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
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
	public List<FeeRuleDTO> getFeeRuleList() {
		return feeRuleList;
	}
	public void setFeeRuleList(List<FeeRuleDTO> feeRuleList) {
		this.feeRuleList = feeRuleList;
	}
	public Long getFeeDimensionId() {
		return feeDimensionId;
	}
	public void setFeeDimensionId(Long feeDimensionId) {
		this.feeDimensionId = feeDimensionId;
	}

}
