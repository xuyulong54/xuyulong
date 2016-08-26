package wusc.edu.pay.core.remit.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.core.biz.BaseBizImpl;
import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.core.remit.dao.RemitChannelDao;
import wusc.edu.pay.core.remit.dao.RemitChannelFlowRuleDao;
import wusc.edu.pay.facade.remit.entity.RemitChannelFlowRule;

/**
 * 打款通道分流规则实体
 * @author： Peter
 * @ClassName: RemitChannelFlowRuleBiz.java
 * @Date： 2014-7-22 下午4:14:19 
 * @version：  V1.0
 */
@Component("remitChannelFlowRuleBiz")
public class RemitChannelFlowRuleBiz extends BaseBizImpl<RemitChannelFlowRule> {
	@Autowired
	RemitChannelFlowRuleDao remitChannelFlowRuleDao;
	@Autowired
	RemitChannelDao remitChannelDao; 
	
	@Override
	protected BaseDao<RemitChannelFlowRule> getDao() {
		return remitChannelFlowRuleDao;
	}
	
	/**
	 * @Title: getByChannelCode 
	 * @Description: 根据打款通道编号获取分流规则
	 * @param @param channelCode
	 * @param @return    
	 * @return RemitChannel  
	 * @throws
	 */
	public RemitChannelFlowRule getByChannelCode(String channelCode) {
		return remitChannelFlowRuleDao.getByChannelCode(channelCode);
	}

}
