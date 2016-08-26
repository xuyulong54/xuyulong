package wusc.edu.pay.facade.remit.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.remit.biz.RemitChannelFlowRuleBiz;
import wusc.edu.pay.facade.remit.entity.RemitChannelFlowRule;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;
import wusc.edu.pay.facade.remit.service.RemitChannelFlowRuleFacade;


/**
 * 打款通道分流规则实体
 * @author： Peter
 * @ClassName: RemitChannelFlowRuleFacadeImpl.java
 * @Date： 2014-7-22 下午3:53:53 
 * @version：  V1.0
 */
@Component("remitChannelFlowRuleFacade")
public class RemitChannelFlowRuleFacadeImpl implements
		RemitChannelFlowRuleFacade {
	@Autowired
	private RemitChannelFlowRuleBiz remitChannelFlowRuleBiz; 
	
	@Override
	public long creat(RemitChannelFlowRule remitChannelFlowRule)
			throws RemitBizException {
		return remitChannelFlowRuleBiz.create(remitChannelFlowRule);
	}

	@Override
	public long update(RemitChannelFlowRule remitChannelFlowRule)
			throws RemitBizException {
		return remitChannelFlowRuleBiz.update(remitChannelFlowRule);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap)
			throws RemitBizException {
		return remitChannelFlowRuleBiz.listPage(pageParam, paramMap);
	}

	@Override
	public RemitChannelFlowRule getById(long id) {
		return remitChannelFlowRuleBiz.getById(id);
	}

	@Override
	public RemitChannelFlowRule getByChannelCode(String channelCode) throws RemitBizException {
		return remitChannelFlowRuleBiz.getByChannelCode(channelCode);
	}

}
