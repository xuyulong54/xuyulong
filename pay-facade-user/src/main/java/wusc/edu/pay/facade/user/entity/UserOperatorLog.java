package wusc.edu.pay.facade.user.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 
 * @描述: 用户操作日志 .
 * @作者: HuQian, WuShuicheng .
 * @创建时间: 2013-9-23,下午10:35:46 .
 * @版本: 1.0 .
 */
public class UserOperatorLog extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/** 用户编号 **/
	private String userNo;
	/** 操作员登录名 **/
	private String loginName;
	/** 操作状态(1:成功,0:失败) **/
	private Integer operateStatus;
	/** IP地址 **/
	private String ip;
	/** 操作内容 **/
	private String content;
	
	private Integer operType; // 操作类型 1-增加，2-修改，3-删除，4-查询

	
	public Integer getOperType() {
		return operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}


	/**
	 * 用户编号
	 * 
	 * @return
	 */
	public String getUserNo() {
		return userNo;
	}

	/**
	 * 用户编号
	 * 
	 * @param userNo
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	/**
	 * 操作员登录名
	 * 
	 * @return
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * 操作员登录名
	 * 
	 * @param loginName
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * IP地址
	 * 
	 * @return
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * IP地址
	 * 
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 操作内容
	 * 
	 * @return
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 操作内容
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public Integer getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(Integer operateStatus) {
		this.operateStatus = operateStatus;
	}

}
