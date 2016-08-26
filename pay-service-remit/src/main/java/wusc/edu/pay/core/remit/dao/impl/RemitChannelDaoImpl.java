package wusc.edu.pay.core.remit.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.core.remit.dao.RemitChannelDao;
import wusc.edu.pay.facade.remit.entity.RemitChannel;


/**
 * @Title: 打款通道DAO接口实现类
 * @Description:
 * @author zzh
 * @date 2014-7-22 下午3:58:43
 */
@Repository("remitChannelDao")
public class RemitChannelDaoImpl extends BaseDaoImpl<RemitChannel> implements RemitChannelDao {

	public RemitChannel getByChannelCode(String channelCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("channelCode", channelCode);
		return super.getBy(paramMap);
	}

	public List<RemitChannel> listByActive() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", PublicStatusEnum.ACTIVE.getValue());
		return super.listBy(paramMap);
	}
}
