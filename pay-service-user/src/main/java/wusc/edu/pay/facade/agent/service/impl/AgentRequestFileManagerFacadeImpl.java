package wusc.edu.pay.facade.agent.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.agent.biz.AgentRequestFileBiz;
import wusc.edu.pay.core.agent.biz.AgentRequestLogBiz;
import wusc.edu.pay.facade.agent.entity.AgentRequestFile;
import wusc.edu.pay.facade.agent.entity.AgentRequestLog;
import wusc.edu.pay.facade.agent.service.AgentRequestFileManagerFacade;


/***
 * 代理商层级关系对外接口的实现类
 * @author huangbin
 *
 */
@Component("agentRequestFileManagerFacade")
public class AgentRequestFileManagerFacadeImpl implements AgentRequestFileManagerFacade{
	@Autowired
	private AgentRequestFileBiz agentRequestFileBiz;
	@Autowired
	private AgentRequestLogBiz agentRequestLogBiz;
	
	/***
	 * 查询申请列表
	 */
	public PageBean fileListPage(PageParam pageParam,
			Map<String, Object> paramMap) {
		return agentRequestFileBiz.listPage(pageParam, paramMap);
	}

	/***
	 * 查询日志列表
	 */
	public PageBean fileLogListPage(PageParam pageParam,
			Map<String, Object> paramMap) {
		return agentRequestLogBiz.listPage(pageParam, paramMap);
	}

	/***
	 * 根据id查询
	 */
	public AgentRequestFile getFileById(long id) {
		return agentRequestFileBiz.getFileById(id);
	}

	/***
	 * 根据id查询日志
	 */
	public AgentRequestLog getFileLogById(long id) {
		return agentRequestLogBiz.getFileById(id);
	}

	/***
	 * 创建申请文件
	 */
	public long createFile(AgentRequestFile file) {
		return agentRequestFileBiz.createFile(file);
	}

	/***
	 * 创建日志
	 */
	public long createFileLog(AgentRequestLog log) {
		return agentRequestLogBiz.createFile(log);
	}

	/***
	 * 更新申请文件
	 */
	public long updateFile(AgentRequestFile file) {
		return agentRequestFileBiz.updateFile(file);
	}

	/***
	 * 查询审核记录列表
	 * @param id
	 * @return
	 */
	public List<AgentRequestLog> listRequestLogByFileId(Long id) {
		return agentRequestLogBiz.listRequestLogByFileId(id);
	}

	/***
	 * 查询代理商的的申请是否有未审核
	 * @param agentNo
	 * @param reqType
	 * @return
	 */
	public AgentRequestFile getFile_AgentNo_ReqType(String agentNo, int reqType) {
		return agentRequestFileBiz.getFile_AgentNo_ReqType(agentNo, reqType);
	}

	/***
	 * 代理商变更请求列表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean agentFileListPage(PageParam pageParam, Map<String, Object> paramMap) {
		return agentRequestFileBiz.agentFileListPage(pageParam, paramMap);
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
		return agentRequestFileBiz.loadChangeInfoStatus(paramMap);
	}
	
	
	
}
