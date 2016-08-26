package wusc.edu.pay.core.agent.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.agent.dao.AgentRequestFileDao;
import wusc.edu.pay.facade.agent.entity.AgentRequestFile;


/***
 * 代理商申请文件 biz类
 * @author Administrator
 *
 */
@Component("agentRequestFileBiz")
public class AgentRequestFileBiz {
	@Autowired
	private AgentRequestFileDao agentRequestFileDao;
	
	/***
	 * 查询申请列表
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap){
		return agentRequestFileDao.listPage(pageParam, paramMap);
	}
	
	public AgentRequestFile getFileById(long id) {
		return agentRequestFileDao.getById(id);
	}

	public long createFile(AgentRequestFile file) {
		return agentRequestFileDao.insert(file);
	}
	
	public long updateFile(AgentRequestFile file) {
		return agentRequestFileDao.update(file);
	}

	/***
	 * 查询代理商的的申请是否有未审核
	 * @param agentNo
	 * @param reqType
	 * @return
	 */
	public AgentRequestFile getFile_AgentNo_ReqType(String agentNo, int reqType) {
		return agentRequestFileDao.getFile_AgentNo_ReqType(agentNo, reqType);
	}

	
	/***
	 * 代理商变更请求列表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean agentFileListPage(PageParam pageParam, Map<String, Object> paramMap) {
		return agentRequestFileDao.agentFileListPage(pageParam, paramMap);
	}

	/***
	 * @Title: loadChangeInfoStatus
	 * @Description: 统计代理商和商户变更申请未审核的数量
	 * @param @param paramMap
	 * @param @return    设定文件
	 * @return List    返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public List loadChangeInfoStatus(Map<String, Object> paramMap) {
		return agentRequestFileDao.loadChangeInfoStatus(paramMap);
	}
}
