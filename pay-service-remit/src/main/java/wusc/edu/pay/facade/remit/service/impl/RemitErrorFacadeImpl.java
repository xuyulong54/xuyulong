/**
 * wusc.edu.pay.facade.remit.service.impl.RemitErrorFacadeImpl.java
 */
package wusc.edu.pay.facade.remit.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.remit.dao.RemitErrorDao;
import wusc.edu.pay.facade.remit.entity.RemitError;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;
import wusc.edu.pay.facade.remit.service.RemitErrorFacade;


/**
 * <ul>
 * <li>Title:</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Liliqiong
 * @version 2014-8-6
 */
@Component("remitErrorFacade")
public class RemitErrorFacadeImpl implements RemitErrorFacade {
	@Autowired
	private RemitErrorDao remitErrorDao;

	@Override
	public long create(RemitError remitError) throws RemitBizException {
		return remitErrorDao.insert(remitError);
	}

	@Override
	public long update(RemitError remitError) throws RemitBizException {
		return remitErrorDao.update(remitError);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws RemitBizException {
		return remitErrorDao.listPage(pageParam, paramMap);
	}

	@Override
	public RemitError getById(long id) throws RemitBizException {
		return remitErrorDao.getById(id);
	}

	@Override
	public RemitError getByRequestNo(String requestNo) {
		return remitErrorDao.getByRequestNo(requestNo);
	}
}
