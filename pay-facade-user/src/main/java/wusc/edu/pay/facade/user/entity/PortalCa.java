package wusc.edu.pay.facade.user.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * ClassName: ProtalCAParam <br/>
 * Function: 门户CA证书信息表 <br/>
 * date: 2014-1-7 下午1:59:08 <br/>
 * 
 * @author laich
 */
public class PortalCa extends BaseEntity {

	private static final long serialVersionUID = -5268657215656309188L;

	private Long userId;// 会员/操作员ID

	private Integer userType;// 商户类型
	private Integer addrId;// 使用地点ID

	private String dn;// DN信息
	private String sn;// CA SN信息
	private Integer status;// 状态
	private Date updateTime;// 修改时间
	private String signCert;
	private String userNo;// 用户编号


	/**
	 * userId.
	 * 
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * userType.
	 * 
	 * @return the userType
	 */
	public Integer getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	/**
	 * addrId.
	 * 
	 * @return the addrId
	 */
	public Integer getAddrId() {
		return addrId;
	}

	/**
	 * @param addrId
	 *            the addrId to set
	 */
	public void setAddrId(Integer addrId) {
		this.addrId = addrId;
	}

	/**
	 * dn.
	 * 
	 * @return the dn
	 */
	public String getDn() {
		return dn;
	}

	/**
	 * @param dn
	 *            the dn to set
	 */
	public void setDn(String dn) {
		this.dn = dn;
	}

	/**
	 * sn.
	 * 
	 * @return the sn
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * @param sn
	 *            the sn to set
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * status.
	 * 
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * updateTime.
	 * 
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * signCert.
	 * 
	 * @return the signCert
	 */
	public String getSignCert() {
		return signCert;
	}

	/**
	 * @param signCert
	 *            the signCert to set
	 */
	public void setSignCert(String signCert) {
		this.signCert = signCert;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	
}
