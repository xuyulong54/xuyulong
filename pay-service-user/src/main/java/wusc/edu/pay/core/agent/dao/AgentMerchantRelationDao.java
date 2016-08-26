package wusc.edu.pay.core.agent.dao;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.agent.entity.AgentMerchantRelation;
import wusc.edu.pay.facade.agent.vo.AgentSplidProfitVo;


/***
 * 代理商和商户关系的Dao接口类
 * 
 * @author huangbin
 * 
 */
public interface AgentMerchantRelationDao extends BaseDao<AgentMerchantRelation> {

	/***
	 * 根据商户编号查询代理商和商户的关联表
	 * 
	 * @param merchantNo
	 * @param firstAgentNo
	 * @param secondAgentNo
	 * @param thirdAgentNo
	 * @return
	 */
	AgentMerchantRelation getByMerchantNo(String merchantNo, String firstAgentNo, String secondAgentNo, String thirdAgentNo, String isDirectly);

	/**
	 * 根据商户编号找代理商层级分润
	 * 
	 * @param merchantNo
	 * @return
	 */
	AgentSplidProfitVo getAgentSplidProfitVo(String merchantNo);

	/***
	 * 更新商户编号
	 * 
	 * @param oldMerchNo
	 * @param newMerchNo
	 * @return
	 */
	int reBindMerchantNo(String oldMerchNo, String newMerchNo);

	/***
	 * 统计当前代理商下的商户状态
	 * 
	 * @param firstAgentNo
	 * @param secondAgentNo
	 * @param thirdAgentNo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List summaryMerchStatusByAgentNo(String firstAgentNo, String secondAgentNo, String thirdAgentNo);

	/**
	 * 查询商户所有信息
	 * 
	 * @param paramObj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List selectMerchantAllInfo(Map<String, Object> paramMap);

}
