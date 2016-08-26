package wusc.edu.pay.facade.remit.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.remit.dao.RemitBatchDao;
import wusc.edu.pay.facade.remit.entity.RemitBatch;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;
import wusc.edu.pay.facade.remit.service.RemitBatchFacade;


/**
 * @Title: 打款批次服务接口实现类
 * @Description:
 * @author zzh
 * @date 2014-7-22 下午4:04:28
 */
@Component("remitBatchFacade")
public class RemitBatchFacadeImpl implements RemitBatchFacade {

	@Autowired
	private RemitBatchDao remitBatchDao;

	@Override
	public long create(RemitBatch remitBatch) throws RemitBizException {
		return remitBatchDao.insert(remitBatch);
	}

	@Override
	public long update(RemitBatch remitBatch) throws RemitBizException {
		return remitBatchDao.update(remitBatch);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws RemitBizException {
		return remitBatchDao.listPage(pageParam, paramMap);
	}

	@Override
	public RemitBatch getById(long id) throws RemitBizException {
		return remitBatchDao.getById(id);
	}

	@Override
	public RemitBatch getByBatchNo(String batchNo) {
		return remitBatchDao.getByBatchNo(batchNo);
	}

}
