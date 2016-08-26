package wusc.edu.pay.facade.agent.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.agent.entity.AgentRequestFile;
import wusc.edu.pay.facade.agent.entity.AgentRequestLog;



/***
 * 代理商和商户资料变更申请 对外接口
 * @author Administrator
 *
 */
public interface AgentRequestFileManagerFacade {
	
	/**
	 * 分页查询
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean fileListPage(PageParam pageParam, Map<String, Object> paramMap) ;
	
	/**
	 * 分页查询
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean fileLogListPage(PageParam pageParam, Map<String, Object> paramMap) ;
	
	/***
	 * 
	 * @param id
	 * @return
	 */
	public AgentRequestFile getFileById(long id);
	
	/***
	 * 
	 * @param id
	 * @return
	 */
	public AgentRequestLog getFileLogById(long id);
	
	/***
	 * 
	 * @param file
	 * @return
	 */
	public long createFile(AgentRequestFile file);
	
	/***
	 * 
	 * @param log
	 * @return
	 */
	public long createFileLog(AgentRequestLog log);
	
	/***
	 * 
	 * @param file
	 * @return
	 */
	public long updateFile(AgentRequestFile file);

	
	/***
	 * 查询审核记录列表
	 * @param id
	 * @return
	 */
	public List<AgentRequestLog> listRequestLogByFileId(Long id);

	/***
	 * 查询代理商的的申请是否有未审核
	 * @param agentNo
	 * @param reqType
	 * @return
	 */
	public AgentRequestFile getFile_AgentNo_ReqType(String agentNo, int reqType);

	
	/***
	 * 代理商变更请求列表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean agentFileListPage(PageParam pageParam, Map<String, Object> paramMap);

	
	/***
	 * @Title: loadChangeInfoStatus
	 * @Description: 统计代理商和商户变更申请未审核的数量
	 * @param @param paramMap
	 * @param @return    设定文件
	 * @return List    返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public List loadChangeInfoStatus(Map<String, Object> paramMap);
	
}
