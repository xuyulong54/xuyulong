package wusc.edu.pay.core.remit.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.core.biz.BaseBizImpl;
import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.core.remit.dao.RemitChannelDao;
import wusc.edu.pay.facade.remit.entity.RemitChannel;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * @Title: 打款通道业务实现类 ,打款接口
 * @Description:
 * @author zzh
 * @date 2014-7-22 下午4:02:15
 */
@Component("remitChannelBiz")
@Transactional(rollbackFor = Exception.class)
public class RemitChannelBiz extends BaseBizImpl<RemitChannel> {

	private static Logger logger = LoggerFactory.getLogger(RemitChannelBiz.class);
	@Autowired
	private RemitChannelDao remitChannelDao;

	@Override
	protected BaseDao<RemitChannel> getDao() {
		return remitChannelDao;
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public long deleteById(long id) throws RemitBizException {
		return remitChannelDao.deleteById(id);
	}

	public List<RemitChannel> getAllOpenChannel() throws RemitBizException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", PublicStatusEnum.ACTIVE.getValue());
		return remitChannelDao.listBy(paramMap);
	}

	public List<RemitChannel> listBy(Map<String, Object> paramMap) {
		return remitChannelDao.listBy(paramMap);
	}

	public RemitChannel getByChannelCode(String channelCode) {
		return remitChannelDao.getByChannelCode(channelCode);
	}


}
