package wusc.edu.pay.core.agent.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.agent.dao.AgentRequestLogDao;
import wusc.edu.pay.facade.agent.entity.AgentRequestLog;



/***
 * 代理商申请文件日志biz类
 * @author Administrator
 *
 */

@Component("agentRequestLogBiz")
public class AgentRequestLogBiz {
	@Autowired
	private AgentRequestLogDao agentRequestLogDao;
	
	/***
	 * 查询申请列表
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap){
		return agentRequestLogDao.listPage(pageParam, paramMap);
	}
	
	public AgentRequestLog getFileById(long id) {
		return agentRequestLogDao.getById(id);
	}

	public long createFile(AgentRequestLog log) {
		return agentRequestLogDao.insert(log);
	}
	
	public long updateFile(AgentRequestLog log) {
		return agentRequestLogDao.update(log);
	}

	/***
	 * 查询审核记录列表
	 * @param id
	 * @return
	 */
	public List<AgentRequestLog> listRequestLogByFileId(Long id) {
		return agentRequestLogDao.listRequestLogByFileId(id);
	}
	
}
