package wusc.edu.pay.web.permission.entity;

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
	private Long operatorId; // 操作员ID
	private String operatorName; // 操作员登录名
	private Integer operateType; // 操作类型（参与枚举:OperatorLogTypeEnum,1:增加,2:修改,3:删除,4:查询,5:登录）
	private Integer status; // 操作状态（参与枚举:OperatorLogStatusEnum,100:成功，101:失败）
	private String ip; // IP地址
	private String content; // 操作内容

	public PmsOperatorLog() {
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
