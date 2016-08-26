package wusc.edu.pay.facade.remit.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.remit.entity.RemitChannel;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * @Title: 打款通道接口服务
 * @Description:
 * @author zzh
 * @date 2014-7-22 下午3:26:39
 */
public interface RemitChannelFacade {
	/**
	 * @Title: 创建打款通道
	 * @Description:
	 * @param @param remitChannel
	 * @param @return
	 * @param @throws RemitBizException
	 * @return long
	 * @throws
	 */
	public long create(RemitChannel remitChannel) throws RemitBizException;

	/**
	 * @Title: 修改打款通道
	 * @Description:
	 * @param @param remitChannel
	 * @param @return
	 * @param @throws RemitBizException
	 * @return int
	 * @throws
	 */
	public long update(RemitChannel remitChannel) throws RemitBizException;

	/**
	 * @Title: 分页查询打款通道
	 * @Description:
	 * @param @param pageParam
	 * @param @param paramMap
	 * @param @return
	 * @param @throws RemitBizException
	 * @return PageBean
	 * @throws
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws RemitBizException;

	/**
	 * @Title: 根据ID获取打款通道信息
	 * @Description:
	 * @param @param id
	 * @param @return
	 * @param @throws RemitBizException
	 * @return RemitChannel
	 * @throws
	 */
	public RemitChannel getById(long id) throws RemitBizException;

	/**
	 * @Title: 根据ID删除打款通道
	 * @Description:
	 * @param @param id
	 * @param @throws RemitBizException
	 * @return void
	 * @throws
	 */
	public void deleteById(long id) throws RemitBizException;
	
	/**
	 * @Title: 根据打款通道编号获取打款通道信息 
	 * @Description: 
	 * @param @param channelCode
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return RemitChannel  
	 * @throws
	 */
	public RemitChannel getByChannelCode(String channelCode) throws RemitBizException;
	
	/**
	 * @Title: 获取所有开启的打款通道 
	 * @Description: 
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return List<RemitChannel>  
	 * @throws
	 */
	public List<RemitChannel> getAllOpenChannel() throws RemitBizException;

	/**
	 * 根据条件查询打款通道
	 * 
	 * @param paramMap
	 * @return
	 * @throws RemitBizException
	 */
	public List<RemitChannel> listBy(Map<String, Object> paramMap) throws RemitBizException;
	
}
