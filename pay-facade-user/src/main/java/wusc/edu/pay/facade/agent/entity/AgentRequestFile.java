package wusc.edu.pay.facade.agent.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/***
 * 代理商申请文件
 * @author huangbin
 *
 */
public class AgentRequestFile extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7818569828401686135L;
	
	private String agentNo; // 代理商编号
	private String agentName;//代理商名称
	private String filePath; //文件地址
	private Integer requestType;//申请类型
	private Integer requestStatus; //申请状态
	private Integer verifyStatus; //审核状态
	private Date lastTime; //最后修改时间
	private String requestOperator; //申请操作员
	private String requestName;//申请人姓名
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Integer getRequestType() {
		return requestType;
	}
	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}
	public Integer getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(Integer requestStatus) {
		this.requestStatus = requestStatus;
	}
	public Integer getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public String getRequestOperator() {
		return requestOperator;
	}
	public void setRequestOperator(String requestOperator) {
		this.requestOperator = requestOperator;
	}
	public String getRequestName() {
		return requestName;
	}
	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}
	
}
