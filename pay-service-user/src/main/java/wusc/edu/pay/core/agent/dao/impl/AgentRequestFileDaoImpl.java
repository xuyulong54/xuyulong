package wusc.edu.pay.core.agent.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.agent.dao.AgentRequestFileDao;
import wusc.edu.pay.facade.agent.entity.AgentRequestFile;
import wusc.edu.pay.facade.agent.enums.RequestVerifyStatusEnum;


/***
 * 代理商,商户申请文件信息Dao接口实现类
 * 
 * @author huangbin
 * 
 */
@Repository("agentRequestFileDao")
public class AgentRequestFileDaoImpl extends BaseDaoImpl<AgentRequestFile> implements AgentRequestFileDao {

	/***
	 * 查询代理商的的申请是否有未审核
	 * 
	 * @param agentNo
	 * @param reqType
	 * @return
	 */
	public AgentRequestFile getFile_AgentNo_ReqType(String agentNo, int reqType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("agentNo", agentNo);
		paramMap.put("requestType", reqType);
		paramMap.put("verifyStatus", RequestVerifyStatusEnum.AUDIT_ING.getValue());
		return super.getBy(paramMap);
	}

	/***
	 * 代理商变更请求列表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean agentFileListPage(PageParam pageParam, Map<String, Object> paramMap) {

		return super.listPage(pageParam, paramMap, "listPageByAgentFile");

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
		return this.getSqlSession().selectList(getStatement("loadChangeInfoStatus"), paramMap);
	}

}
