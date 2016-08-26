package wusc.edu.pay.facade.agent.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.agent.biz.AgentMerchantRelationBiz;
import wusc.edu.pay.facade.agent.entity.AgentMerchantRelation;
import wusc.edu.pay.facade.agent.service.AgentMerchantRelationFacade;
import wusc.edu.pay.facade.agent.vo.AgentSplidProfitVo;
import wusc.edu.pay.facade.fee.entity.UserFeeSetting;
import wusc.edu.pay.facade.limit.entity.TradeLimitRouter;


/***
 * 商户 代理商关系对外接口的实现类
 * 
 * @author huangbin
 * 
 */
@Component("agentMerchantRelationFacade")
public class AgentMerchantRelationFacadeImpl implements AgentMerchantRelationFacade {

	@Autowired
	private AgentMerchantRelationBiz agentMerchantRelationBiz;

	/***
	 * 根据ID查询实体信息
	 * 
	 * @param id
	 * @return
	 */
	public AgentMerchantRelation getById(long id) {
		return agentMerchantRelationBiz.getById(id);
	}

	/**
	 * 更新代理商和商户关系
	 * 
	 * @return result
	 */
	public long update(AgentMerchantRelation relation) {
		return agentMerchantRelationBiz.update(relation);
	}

	/**
	 * 添加代理商和商户关系
	 * 
	 * @return result
	 */
	public long create(AgentMerchantRelation relation) {
		return agentMerchantRelationBiz.create(relation);
	}

	/***
	 * 分页查询商户和代理商关系
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listMerchantListPage(PageParam pageParam, Map<String, Object> paramMap) {
		return agentMerchantRelationBiz.listPage(pageParam, paramMap);
	}

	/***
	 * 根据条件查询商户信息
	 * 
	 * @param merchantNo
	 * @param firstAgentNo
	 * @param secondAgentNo
	 * @param thirdAgentNo
	 * @param isDirectly 是否直属商户
	 * @return
	 */
	public AgentMerchantRelation getByMerchantNo(String merchantNo, String firstAgentNo, String secondAgentNo, String thirdAgentNo, String isDirectly) {
		return agentMerchantRelationBiz.getByMerchantNo(merchantNo, firstAgentNo, secondAgentNo, thirdAgentNo, isDirectly);
	}

	/**
	 * 根据商户编号找代理商层级分润
	 * 
	 * @param merchantNo
	 * @return
	 */
	public AgentSplidProfitVo getAgentSplidProfitVo(String merchantNo) {
		return agentMerchantRelationBiz.getAgentSplidProfitVo(merchantNo);
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
		return agentMerchantRelationBiz.summaryMerchStatusByAgentNo(firstAgentNo, secondAgentNo, thirdAgentNo);
	}
	/**
	 * 查询商户所有相关信息
	 * 
	 * @param paramMap
	 * @return
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List selectMerchantAllInfo(Map<String, Object> paramMap) {
		return agentMerchantRelationBiz.selectMerchantAllInfo(paramMap);
	}

	@Override
	public List<AgentMerchantRelation> listBy(Map<String, Object> paramMap) {
		return agentMerchantRelationBiz.listBy(paramMap);
	}

	/***
	 * @Title: auditMerch_updMerch
	 * @Description:  审核商户，并且重新生成商户编号
	 * @param @param id
	 * @param @param userNo
	 * @param @param loginName
	 * @param @param realName
	 * @param @param string
	 * @param @param string2
	 * @param @param value
	 * @param @param status
	 * @param @param mcc
	 * @param @return    设定文件
	 * @return long    返回类型
	 * @throws
	 */
	public long auditMerch_updMerch(long id, String userNo, String auditOperatorLoginName, String auditOperatorName,
			String applyDesc, String auditDesc, Integer changeStatus, Integer currentStatus, String mcc,
			List<UserFeeSetting> userFeeList, TradeLimitRouter tradeLimitRouterModel) {
		
		return agentMerchantRelationBiz.auditMerch_updMerch(id, userNo, auditOperatorLoginName, auditOperatorName, applyDesc,
				auditDesc, changeStatus, currentStatus, mcc, userFeeList, tradeLimitRouterModel);
	}

	/***
	 * @Description: 修改商户绑定的终端号
	 * @param merchantNo   
	 * @param operFlag 操作方向 （1-加，2-减） 
	 * @return void  
	 * @throws
	 * @author Huang Bin
	 * @date 2015-4-2
	 */
	public long updateTerminalNum(String merchantNo, int operFlag) {
		return agentMerchantRelationBiz.updateTerminalNum(merchantNo, operFlag);
	}
	
}
