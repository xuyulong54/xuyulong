package wusc.edu.pay.core.agent.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.agent.dao.AgentRequestLogDao;
import wusc.edu.pay.facade.agent.entity.AgentRequestLog;



/***
 * 代理商,商户申请文件 记录表dao接口实现类
 * @author huangbin
 *
 */
@Repository("agentRequestLogDao")
public class AgentRequestLogDaoImpl extends BaseDaoImpl<AgentRequestLog> implements AgentRequestLogDao {

	/***
	 * 查询审核记录列表
	 * @param id
	 * @return
	 */
	public List<AgentRequestLog> listRequestLogByFileId(Long fileId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("requestId", fileId);
		return super.listBy(paramMap);
	}

}
