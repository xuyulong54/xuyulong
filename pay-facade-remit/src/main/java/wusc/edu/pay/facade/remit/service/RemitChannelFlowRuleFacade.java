package wusc.edu.pay.facade.remit.service;

import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.remit.entity.RemitChannelFlowRule;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * 
 * @author： Peter
 * @ClassName: RemitChannelFlowRuleFacade.java
 * @Date： 2014-7-22 下午3:42:23 
 * @version: V1.0
 */
public interface RemitChannelFlowRuleFacade {
	/**
	 * 创建打款通道分流规则
	 * @param remitChannelFlowRule
	 * @return
	 */
	public long creat(RemitChannelFlowRule remitChannelFlowRule) throws RemitBizException;
	
	/**
	 * 修改打款通道分流规则
	 * @param remitChannelFlowRule
	 * @return
	 */
	public long update(RemitChannelFlowRule remitChannelFlowRule) throws RemitBizException;
	
	/**
	 * 分页查询打款通道分流规则
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws RemitBizException;
	
	/**
	 * 根据id获取打款通道分流规则
	 * @param id
	 * @return
	 */
	public RemitChannelFlowRule getById(long id) throws RemitBizException;
	
	/**
	 * @Title: getByChannelCode 
	 * @Description: 根据打款通道编号获取分流规则
	 * @param @param channelCode
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return RemitChannelFlowRule  
	 * @throws
	 */
	public RemitChannelFlowRule getByChannelCode(String channelCode) throws RemitBizException;
}
