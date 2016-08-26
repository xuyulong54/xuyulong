package wusc.edu.pay.core.remit.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.remit.entity.RemitChannelFlowRule;


/**
 * 打款通道分流规则实体
 * 
 * @author： Peter
 * @ClassName: RemitChannelFlowRuleDao.java
 * @Date： 2014-7-22 下午4:12:23
 * @version： V1.0
 */
public interface RemitChannelFlowRuleDao extends BaseDao<RemitChannelFlowRule> {
	public List<RemitChannelFlowRule> getByRemitRequest(Map<String, Object> paramMap);

	/**
	 * 根据打款通道编码获取打款分流规则
	 * 
	 * @param channelCode
	 * @return
	 */
	public RemitChannelFlowRule getByChannelCode(String channelCode);

	public List<RemitChannelFlowRule> listRoute(String accountType, String bankCode, BigDecimal amount, String tradeType);
}
