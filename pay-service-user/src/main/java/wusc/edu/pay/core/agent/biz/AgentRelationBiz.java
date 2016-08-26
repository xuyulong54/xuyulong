package wusc.edu.pay.core.agent.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.agent.dao.AgentRelationDao;
import wusc.edu.pay.facade.agent.entity.AgentRelation;


/***
 * 代理商关系 biz 类
 * @author huangbin
 *
 */
@Component("agentRelationBiz")
public class AgentRelationBiz {
	
	@Autowired
	private AgentRelationDao agentRelationDao;
	
	/***
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public AgentRelation getById(long id) {
		return agentRelationDao.getById(id);
	}

	/***
	 * 分页列表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return agentRelationDao.listPage(pageParam, paramMap);
	}

	/***
	 * 更新方法
	 * @param relation
	 * @return
	 */
	public long update(AgentRelation relation) {
		return agentRelationDao.update(relation);
	}

	/***
	 * 新增方法
	 * @param relation
	 * @return
	 */
	public long create(AgentRelation relation) {
		return agentRelationDao.insert(relation);
	}

	/***
	 * 根据代理商编号，上级代理商编号，上上级代理商编号查询代理商
	 * @param agentNo
	 * @param parentAgentNo
	 * @param parentUpAgentNo
	 * @return
	 */
	public AgentRelation getByAgentNo(String agentNo, String parentAgentNo, String parentUpAgentNo) {
		return agentRelationDao.getByAgentNo(agentNo, parentAgentNo, parentUpAgentNo);
	}

	
	/***
	 * 查询代理商下面的所有代理商
	 * @param agentNo		代理商编号
	 * @param agentLevel	代理商级别
	 * @return
	 */
	public List<AgentRelation> getAgentByAgentNo(String agentNo, int agentLevel) {
		return agentRelationDao.getAgentByAgentNo(agentNo, agentLevel);
	}

	/***
	 * 判断输入的代理商编号是否属于当前代理商
	 * @param agentNo		输入的代理商编号
	 * @param parentAgentNo 登录的代理商
	 * @return
	 */
	public AgentRelation getByParentAgentNo(String agentNo, String parentAgentNo) {
		return agentRelationDao.getByParentAgentNo(agentNo, parentAgentNo);
	}
	/**
	 * 统计代理商当前状态
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List summaryAgentStatusByAgentNo(Map<String, Object> paramMap) {
		return agentRelationDao.summaryAgentStatusByAgentNo(paramMap);
	}
	/**
	 * 条件查询
	 * @param paramMap
	 * @return
	 */
	public List<AgentRelation> listBy(Map<String, Object> paramMap) {
		return agentRelationDao.listBy(paramMap);
	}

}
