package wusc.edu.pay.facade.fee.vo;

import java.util.Date;
import java.util.List;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.facade.fee.dto.FeeRuleDTO;


/**
 * 费率维度VO
 * 
 * @desc
 * @author shenjialong
 * @date 2014-6-30,上午10:27:10
 */
@SuppressWarnings("serial")
public class FeeDimensionVO extends BaseEntity {

	/**
	 * 节点Id
	 */
	private Long nodeId;
	/**
	 * 业务类型
	 */
	private String trxType;
	/**
	 * 计费项 《参考 CalculateFeeItemEnum 枚举》
	 */
	private Integer calculateFeeItem;

	/**
	 * 费率模型类型 《参考 FeeModelTypeEnum 枚举》
	 */
	private Integer nodeType;
	
	/**
	 * 节点名称
	 */
	private String nodeName;

	// //////////////////////////////////

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getPayProductName() {
		return payProductName;
	}

	public void setPayProductName(String payProductName) {
		this.payProductName = payProductName;
	}

	/**
	 * 费率维度Id
	 */
	private Long dimensionId;
	/**
	 * 支付产品编码
	 */
	private String payProduct;
	/**
	 * 支付方式编码
	 */
	private String frpCode;
	/**
	 * 支付接口
	 */
	private String bankChannelCode;
	
	/**
	 * 维度状态
	 */
	private Integer status;
	
	/**
	 * 产品名称
	 */
	private String payProductName;
	
	// ///////////////////下面是必须添加的 上面的还要优化////////////////////////

	/**
	 * 费率规则组 （必须）
	 */
	private List<FeeRuleDTO> feeRuleList;

	public List<FeeRuleDTO> getFeeRuleList() {
		return feeRuleList;
	}

	public void setFeeRuleList(List<FeeRuleDTO> feeRuleList) {
		this.feeRuleList = feeRuleList;
	}

	// /////////////////////////////////////////////////////////////

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getTrxType() {
		return trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

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

	public Long getDimensionId() {
		return dimensionId;
	}

	public void setDimensionId(Long dimensionId) {
		this.dimensionId = dimensionId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


}
