package wusc.edu.pay.core.remit.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.core.biz.BaseBizImpl;
import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.core.remit.dao.RemitProcessDao;
import wusc.edu.pay.facade.remit.entity.RemitProcess;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * 打款处理记录实体
 * 
 * @author： Peter
 * @ClassName: RemitProcessBiz.java
 * @Date： 2014-7-22 下午4:18:26
 * @version： V1.0
 */
@Component("remitProcessBiz")
@Transactional(rollbackFor = Exception.class)
public class RemitProcessBiz extends BaseBizImpl<RemitProcess> {

	private static final Log LOG = LogFactory.getLog(RemitProcessBiz.class);

	@Autowired
	private RemitProcessDao remitProcessDao;

	@Override
	protected BaseDao<RemitProcess> getDao() {
		return remitProcessDao;
	}
	/**
	 * @Title: 根据打款请求号获取打款处理记录实体
	 * @Description:
	 * @param @param requestNo
	 * @param @return
	 * @param @throws RemitBizException
	 * @return RemitProcess
	 * @throws
	 */
	public RemitProcess getByRequestNo(String requestNo) throws RemitBizException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("requestNo", requestNo);
		return remitProcessDao.getBy(paramMap);
	}

	public List<RemitProcess> listBy(Map<String, Object> paramMap) {
		return remitProcessDao.listBy(paramMap);
	}

	public List<Map<String, Object>> getChannelCodesByMap(Map<String, Object> paramMap) throws RemitBizException {
		return remitProcessDao.getChannelCodesByMap(paramMap);
	}

	/**
	 * 批量修改
	 * 
	 * @param remitProcesss
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void batchUpdate(List<RemitProcess> remitProcesss) {
		remitProcessDao.batchUpdate(remitProcesss);
	}

}
