package wusc.edu.pay.core.remit.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.remit.entity.RemitChannel;


/**
 * @Title: 打款通道DAO接口 
 * @Description: 
 * @author zzh
 * @date 2014-7-22 下午3:55:58
 */
public interface RemitChannelDao extends BaseDao<RemitChannel> {
	/**
	 * 根据打款通道编码获取打款通道
	 * @param channelCode
	 * @return
	 */
	public RemitChannel getByChannelCode(String channelCode);
	
	public List<RemitChannel> listByActive();
}
