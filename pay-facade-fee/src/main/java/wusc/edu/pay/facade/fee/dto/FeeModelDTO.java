package wusc.edu.pay.facade.fee.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import wusc.edu.pay.facade.fee.entity.FeeDimension;


/**
 * 费率模型DTO
 * 
 * @desc
 * @author shenjialong
 * @date 2014-7-1,上午10:09:31
 */
@SuppressWarnings("serial")
public class FeeModelDTO implements Serializable {
	/**
	 * 用户节点Id
	 */
	private Long userSettingId;
	/**
	 * 费率节点Id
	 */
	private Long nodeId;
	/**
	 * 费率用户编号 （必须）
	 */
	private String userNo;
	/**
	 * 费率客户名称
	 */
	private String userName;
	/**
	 * 计费项<参考 CalculateFeeItemEnum 枚举 > (必须)
	 */
	private Integer calculateFeeItem;
	/**
	 * 费率维度DTO List （必须）
	 */
	private List<FeeDimension> feeDimensionList;
	/**
	 * 用户类型 （必须）
	 */
	private Integer userType;
	/**
	 * 费率模型类型（公共、私有、模板） 《 参考 FeeModelTypeEnum 》 （必须）
	 */
	private Integer nodeType;
	/**
	 * 节点名称
	 */
	private String nodeName;
	/**
	 * 节点描述
	 */
	private String description;
	/**
	 * 用户节点状态是否有效
	 */
	private Integer status;
	
	/**
	 * 用户节点设置创建时间
	 */
	private Date createTime = new Date();
	
	/**
	 * 判断维度list是否不为空
	 * 
	 * @return
	 */
	
	
	public boolean isValid4Dimensions() {
		if (null != feeDimensionList && feeDimensionList.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
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


	public Integer getCalculateFeeItem() {
		return calculateFeeItem;
	}

	public void setCalculateFeeItem(Integer calculateFeeItem) {
		this.calculateFeeItem = calculateFeeItem;
	}

	public List<FeeDimension> getFeeDimensionList() {
		return feeDimensionList;
	}

	public void setFeeDimensionList(List<FeeDimension> feeDimensionList) {
		this.feeDimensionList = feeDimensionList;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getUserSettingId() {
		return userSettingId;
	}

	public void setUserSettingId(Long userSettingId) {
		this.userSettingId = userSettingId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
