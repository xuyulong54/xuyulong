package wusc.edu.pay.facade.pms.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 
 * @描述: 权限管理-操作员操作日志..
 * @作者: WuShuicheng.
 * @创建时间: 2013-12-31,下午4:13:19.
 * @版本号: V1.0 .
 * 
 */
public class PmsOperatorLog extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 操作员类型（1:超级管理员,2:普通管理员,3:默认操作员,4:普通操作员，参考枚举PmsOperatorTypeEnum） **/
	private String operatorType;
	
	/** 操作员登录名 **/
	private String loginName;
	
	/** 操作员用户编号（可以是商户编号、所属代理商编号） **/
	private String userNo;
	
	/** 操作类型（1:增加,2:修改,3:删除,4:查询,5:登录，参与枚举PmsOperatorLogTypeEnum） **/
	private Integer type; // 
	
	/** 操作状态（100:成功，101:失败, 参与枚举PmsOperatorLogStatusEnum） **/
	private Integer status; // 
	
	/** IP地址 **/
	private String ip;
	
	/** 操作内容 **/
	private String content;

	public PmsOperatorLog() {
	}

	
	/** 操作员类型（1:超级管理员,2:普通管理员,3:默认操作员,4:普通操作员，参考枚举PmsOperatorTypeEnum） **/
	public String getOperatorType() {
		return operatorType;
	}

	/** 操作员类型（1:超级管理员,2:普通管理员,3:默认操作员,4:普通操作员，参考枚举PmsOperatorTypeEnum） **/
	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	/** 操作员登录名 **/
	public String getLoginName() {
		return loginName;
	}

	/** 操作员登录名 **/
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/** 操作员用户编号（可以是商户编号、所属代理商编号） **/
	public String getUserNo() {
		return userNo;
	}

	/** 操作员用户编号（可以是商户编号、所属代理商编号） **/
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	/** 操作类型（1:增加,2:修改,3:删除,4:查询,5:登录，参与枚举PmsOperatorLogTypeEnum） **/
	public Integer getType() {
		return type;
	}

	/** 操作类型（1:增加,2:修改,3:删除,4:查询,5:登录，参与枚举PmsOperatorLogTypeEnum） **/
	public void setType(Integer type) {
		this.type = type;
	}

	/** 操作状态（100:成功，101:失败, 参与枚举PmsOperatorLogStatusEnum） **/
	public Integer getStatus() {
		return status;
	}

	/** 操作状态（100:成功，101:失败, 参与枚举PmsOperatorLogStatusEnum） **/
	public void setStatus(Integer status) {
		this.status = status;
	}

	/** IP地址 **/
	public String getIp() {
		return ip;
	}

	/** IP地址 **/
	public void setIp(String ip) {
		this.ip = ip;
	}

	/** 操作内容 **/
	public String getContent() {
		return content;
	}

	/** 操作内容 **/
	public void setContent(String content) {
		this.content = content;
	}


}
