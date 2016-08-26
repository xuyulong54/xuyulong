package wusc.edu.pay.facade.agent.entity;

import wusc.edu.pay.common.entity.BaseEntity;


/***
 * 代理商申请日志
 * @author huangbin
 *
 */
public class AgentRequestLog extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6456246970343925459L;
	
	private long requestId;
	private String agentNo;
	private String agentName;
	private String operator; //操作员
	private String content;// 操作备注
	public long getRequestId() {
		return requestId;
	}
	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
