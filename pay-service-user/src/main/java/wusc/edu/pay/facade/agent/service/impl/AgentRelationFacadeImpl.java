package wusc.edu.pay.facade.agent.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.agent.biz.AgentRelationBiz;
import wusc.edu.pay.facade.agent.entity.AgentRelation;
import wusc.edu.pay.facade.agent.service.AgentRelationFacade;



/***
 * 代理商层级关系对外接口的实现类
 * @author huangbin
 *
 */
@Component("agentRelationFacade")
public class AgentRelationFacadeImpl implements AgentRelationFacade {
	
	@Autowired
	private AgentRelationBiz agentRelationBiz;
	
	/***
	 * 根据ID查询实体信息
	 * @param id
	 * @return
	 */
	public AgentRelation getById(long id){
		return agentRelationBiz.getById(id);
	}
	
	/**
	 * 分页查询
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return agentRelationBiz.listPage(pageParam, paramMap);
	}
	
	/**
	 * 更新代理商和商户关系
	 * @return result 
	 */
	public long update(AgentRelation relation) {
		return agentRelationBiz.update(relation);
	}
	
	/**
	 * 添加代理商和商户关系
	 * @return result 
	 */
	public long create(AgentRelation relation) {
		return agentRelationBiz.create(relation);
	}

	/***
	 * 根据代理商编号，上级代理商编号，上上级代理商编号查询代理商
	 * @param agentNo
	 * @param parentAgentNo
	 * @param parentUpAgentNo
	 * @return
	 */
	public AgentRelation getByAgentNo(String agentNo, String parentAgentNo, String parentUpAgentNo) {
		return agentRelationBiz.getByAgentNo(agentNo, parentAgentNo, parentUpAgentNo);
	}

	/***
	 * 查询代理商下面的所有代理商
	 * @param agentNo		代理商编号
	 * @param agentLevel	代理商级别
	 * @return
	 */
	public List<AgentRelation> getAgentByAgentNo(String agentNo, int agentLevel) {
		return agentRelationBiz.getAgentByAgentNo(agentNo, agentLevel);
	}

	/***
	 * 判断输入的代理商编号是否属于当前代理商
	 * @param agentNo		输入的代理商编号
	 * @param parentAgentNo 登录的代理商
	 * @return
	 */
	public AgentRelation getByParentAgentNo(String agentNo, String parentAgentNo) {
		return agentRelationBiz.getByParentAgentNo(agentNo, parentAgentNo);
	}
	/**
	 * 统计代理商当前状态
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List summaryAgentStatusByAgentNo(Map<String, Object> paramMap) {
		return agentRelationBiz.summaryAgentStatusByAgentNo(paramMap);
	}

	/**
	 * 条件查询
	 * @param paramMap
	 * @return
	 */
	public List<AgentRelation> listBy(Map<String, Object> paramMap) {
		return agentRelationBiz.listBy(paramMap);
	}
	
}
