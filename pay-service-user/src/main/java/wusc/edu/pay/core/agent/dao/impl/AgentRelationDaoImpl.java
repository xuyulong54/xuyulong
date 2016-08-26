package wusc.edu.pay.core.agent.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.agent.dao.AgentRelationDao;
import wusc.edu.pay.facade.agent.entity.AgentRelation;


/***
 * 代理商层级关系的Dao接口实现类
 * 
 * @author huangbin
 * 
 */
@Repository("agentRelationDao")
public class AgentRelationDaoImpl extends BaseDaoImpl<AgentRelation> implements AgentRelationDao {

	/***
	 * 根据代理商编号，上级代理商编号，上上级代理商编号查询代理商
	 * 
	 * @param agentNo
	 * @param firstAgentNo
	 * @param secondAgentNo
	 * @return
	 */
	public AgentRelation getByAgentNo(String agentNo, String parentAgentNo, String parentUpAgentNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("agentNo", agentNo);
		paramMap.put("parentAgentNo", parentAgentNo);
		paramMap.put("parentUpAgentNo", parentUpAgentNo);
		return super.getBy(paramMap);
	}

	/***
	 * 查询代理商下面的所有代理商
	 * 
	 * @param agentNo
	 *            代理商编号
	 * @param agentLevel
	 *            代理商级别
	 * @return
	 */
	public List<AgentRelation> getAgentByAgentNo(String agentNo, int agentLevel) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(agentLevel == 0){ // 在运营查询所有的代理商
			return super.listBy(paramMap);
		} else if (agentLevel == 1) {
			paramMap.put("parentUpAgentNo", agentNo);
		} else if (agentLevel == 2) {
			paramMap.put("parentAgentNo", agentNo);
		} else {
			return new ArrayList<AgentRelation>();
		}
		
		return super.listBy(paramMap);
	}

	/***
	 * 判断输入的代理商编号是否属于当前代理商
	 * 
	 * @param agentNo
	 *            输入的代理商编号
	 * @param parentAgentNo
	 *            登录的代理商
	 * @return
	 */
	public AgentRelation getByParentAgentNo(String agentNo, String parentAgentNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("agentNo", agentNo);
		paramMap.put("parentAgentNo", parentAgentNo);
		return this.getSqlSession().selectOne("getByParentAgentNo", paramMap);
	}
	/***
	 * 统计代理商状态
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List summaryAgentStatusByAgentNo(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList("summaryAgentStatusByAgentNo", paramMap);
	}
}
