package wusc.edu.pay.core.agent.dao;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.agent.entity.AgentRequestFile;



/***
 * 代理商,商户申请文件信息Dao接口
 * @author huangbin
 *
 */
public interface AgentRequestFileDao extends BaseDao<AgentRequestFile> {

	/***
	 * 查询代理商的的申请是否有未审核
	 * @param agentNo
	 * @param reqType
	 * @return
	 */
	AgentRequestFile getFile_AgentNo_ReqType(String agentNo, int reqType);

	/***
	 * 代理商变更请求列表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	PageBean agentFileListPage(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * @Title: loadChangeInfoStatus
	 * @Description: 统计代理商和商户变更申请未审核的数量
	 * @param @param paramMap
	 * @param @return    设定文件
	 * @return List    返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	List loadChangeInfoStatus(Map<String, Object> paramMap);
	
	
}
