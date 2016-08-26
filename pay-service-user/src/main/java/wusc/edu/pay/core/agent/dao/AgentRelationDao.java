package wusc.edu.pay.core.agent.dao;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.agent.entity.AgentRelation;



/***
 * 代理商层级关系的Dao接口类
 * @author huangbin
 *
 */
public interface AgentRelationDao extends BaseDao<AgentRelation> {

	/***
	 * 根据代理商编号，上级代理商编号，上上级代理商编号查询代理商
	 * @param agentNo
	 * @param parentAgentNo
	 * @param parentUpAgentNo
	 * @return
	 */
	AgentRelation getByAgentNo(String agentNo, String parentAgentNo, String parentUpAgentNo);

	/***
	 * 查询代理商下面的所有代理商
	 * @param agentNo		代理商编号
	 * @param agentLevel	代理商级别
	 * @return
	 */
	List<AgentRelation> getAgentByAgentNo(String agentNo, int agentLevel);

	
	/***
	 * 判断输入的代理商编号是否属于当前代理商
	 * @param agentNo		输入的代理商编号
	 * @param parentAgentNo 登录的代理商
	 * @return
	 */
	AgentRelation getByParentAgentNo(String agentNo, String parentAgentNo);

	/**
	 * 统计代理商状态
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List summaryAgentStatusByAgentNo(Map<String, Object> paramMap);
	
	
	
}
