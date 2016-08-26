package wusc.edu.pay.facade.remit.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.remit.biz.RemitRequestBiz;
import wusc.edu.pay.core.remit.dao.RemitRequestDao;
import wusc.edu.pay.facade.remit.entity.RemitRequest;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;
import wusc.edu.pay.facade.remit.service.RemitRequestFacade;


/**
 * 打款请求实体
 * 
 * @author： Peter
 * @ClassName: RemitRequestFacadeImpl.java
 * @Date： 2014-7-22 下午3:53:41
 * @version： V1.0
 */
@Component("remitRequestFacade")
public class RemitRequestFacadeImpl implements RemitRequestFacade {

	@Autowired
	private RemitRequestBiz remitRequestBiz;
	@Autowired
	private RemitRequestDao remitRequestDao;

	/**
	 * 生成打款请求号.<br/>
	 * 
	 * @return remitRequestNo 打款请求号.<br/>
	 */
	@Override
	public String buildRemitRequestNo() {
		return remitRequestDao.buildRemitRequestNo();
	}

	@Override
	public long create(RemitRequest remitRequest) throws RemitBizException {
		return remitRequestDao.insert(remitRequest);
	}

	@Override
	public long update(RemitRequest remitRequest) {
		return remitRequestDao.update(remitRequest);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws RemitBizException {
		return remitRequestDao.listPage(pageParam, paramMap);
	}

	@Override
	public RemitRequest getById(long id) throws RemitBizException {
		return remitRequestDao.getById(id);
	}

	@Override
	public RemitRequest getByRequestNo(String requestNo) {
		return remitRequestDao.getByRequestNo(requestNo);
	}

	@Override
	public void deleteById(long id) throws RemitBizException {
		remitRequestBiz.deleteById(id);
	}

	@Override
	public long batchInsert(List<RemitRequest> remitRequest) throws RemitBizException {
		return remitRequestDao.batchInsert(remitRequest);
	}
}
