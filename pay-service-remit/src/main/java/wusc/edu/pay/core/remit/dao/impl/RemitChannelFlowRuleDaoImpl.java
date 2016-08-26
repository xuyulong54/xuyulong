package wusc.edu.pay.core.remit.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.remit.dao.RemitChannelFlowRuleDao;
import wusc.edu.pay.facade.remit.entity.RemitChannelFlowRule;


/**
 * 打款通道分流规则实体
 * 
 * @author： Peter
 * @ClassName: RemitChannelFlowRuleDaoImpl.java
 * @Date： 2014-7-22 下午4:13:47
 * @version： V1.0
 */
@Repository("remitChannelFlowRuleDao")
public class RemitChannelFlowRuleDaoImpl extends BaseDaoImpl<RemitChannelFlowRule> implements RemitChannelFlowRuleDao {

	@Override
	public List<RemitChannelFlowRule> getByRemitRequest(Map<String, Object> paramMap) {
		return this.getSessionTemplate().selectList(getStatement("getByRemitRequest"), paramMap);
	}

	@Override
	public RemitChannelFlowRule getByChannelCode(String channelCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("channelCode", channelCode);
		return super.getBy(paramMap);
	}

	public List<RemitChannelFlowRule> listRoute(String accountType, String bankCode, BigDecimal amount, String tradeType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountType", accountType);
		paramMap.put("bankCode", bankCode);
		paramMap.put("amount", amount);
		paramMap.put("tradeType", tradeType);
		return this.getSessionTemplate().selectList(getStatement("listRoute"), paramMap);
	}
}
