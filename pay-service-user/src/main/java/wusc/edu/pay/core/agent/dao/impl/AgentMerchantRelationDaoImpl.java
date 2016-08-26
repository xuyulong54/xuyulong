package wusc.edu.pay.core.agent.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.agent.dao.AgentMerchantRelationDao;
import wusc.edu.pay.facade.agent.entity.AgentMerchantRelation;
import wusc.edu.pay.facade.agent.vo.AgentSplidProfitVo;


/***
 * 代理商商户关系的Dao接口实现类
 * 
 * @author huangbin
 * 
 */
@Repository("agentMerchantRelationDao")
public class AgentMerchantRelationDaoImpl extends BaseDaoImpl<AgentMerchantRelation> implements AgentMerchantRelationDao {

	/***
	 * 根据商户编号查询代理商和商户的关联表
	 * 
	 * @param merchantNo
	 * @param firstAgentNo
	 * @param secondAgentNo
	 * @param thirdAgentNo
	 * @return
	 */
	public AgentMerchantRelation getByMerchantNo(String merchantNo, String firstAgentNo, String secondAgentNo, String thirdAgentNo, String isDirectly) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("merchantNo", merchantNo);
		paramMap.put("firstAgentNo", firstAgentNo);
		paramMap.put("secondAgentNo", secondAgentNo);
		paramMap.put("thirdAgentNo", thirdAgentNo);
		paramMap.put("isDirectly", isDirectly);

		return super.getBy(paramMap);
	}

	/**
	 * 根据商户编号找代理商层级分润
	 * 
	 * @param merchantNo
	 * @return
	 */
	public AgentSplidProfitVo getAgentSplidProfitVo(String merchantNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("merchantNo", merchantNo);
		return this.getSqlSession().selectOne("selectAgentSplitProfitVo", paramMap);
	}

	/***
	 * 更新商户编号
	 * @param oldMerchNo
	 * @param newMerchNo
	 * @return
	 */
	public int reBindMerchantNo(String oldMerchNo, String newMerchNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("oldMerchNo", oldMerchNo);
		paramMap.put("newMerchNo", newMerchNo);
		return super.getSqlSession().update("reBindMerchantNo", paramMap);
	}

	/***
	 * 统计当前代理商下的商户状态
	 * @param firstAgentNo
	 * @param secondAgentNo
	 * @param thirdAgentNo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List summaryMerchStatusByAgentNo(String firstAgentNo, String secondAgentNo, String thirdAgentNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("firstAgentNo", firstAgentNo);
		paramMap.put("secondAgentNo", secondAgentNo);
		paramMap.put("thirdAgentNo", thirdAgentNo);
		return super.getSqlSession().selectList("summaryMerchStatusByAgentNo", paramMap);
	}

	@SuppressWarnings("rawtypes")
	public List selectMerchantAllInfo(Map<String, Object> paramMap) {
		return super.getSqlSession().selectList("selectMerchantAllInfo", paramMap);
	}
	
	

}
