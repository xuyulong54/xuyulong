package wusc.edu.pay.core.agent.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.agent.entity.AgentRequestLog;


/***
 * 代理商,商户申请文件 记录表dao接口
 * @author huangbin
 *
 */
public interface AgentRequestLogDao extends BaseDao<AgentRequestLog> {

	
	/***
	 * 查询审核记录列表
	 * @param id
	 * @return
	 */
	List<AgentRequestLog> listRequestLogByFileId(Long fileId);
	
}
