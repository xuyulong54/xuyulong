package wusc.edu.pay.facade.remit.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.remit.biz.RemitProcessBiz;
import wusc.edu.pay.facade.remit.entity.RemitProcess;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;
import wusc.edu.pay.facade.remit.service.RemitProcessFacade;


/**
 * 打款处理记录实体
 * 
 * @author： Peter
 * @ClassName: RemitProcessFacadeImpl.java
 * @Date： 2014-7-22 下午3:53:48
 * @version： V1.0
 */
@Component("remitProcessFacade")
public class RemitProcessFacadeImpl implements RemitProcessFacade {

	@Autowired
	private RemitProcessBiz remitProcessBiz;

	@Override
	public long creat(RemitProcess remitProcess) throws RemitBizException {
		return remitProcessBiz.create(remitProcess);
	}

	@Override
	public long update(RemitProcess remitProcess) throws RemitBizException {
		return remitProcessBiz.update(remitProcess);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws RemitBizException {
		return remitProcessBiz.listPage(pageParam, paramMap);
	}

	@Override
	public RemitProcess getById(long id) throws RemitBizException {
		return remitProcessBiz.getById(id);
	}

	@Override
	public RemitProcess getByRequestNo(String requestNo) {
		return remitProcessBiz.getByRequestNo(requestNo);
	}

	@Override
	public List<RemitProcess> listBy(Map<String, Object> paramMap) throws RemitBizException {
		return remitProcessBiz.listBy(paramMap);
	}

	public List<Map<String, Object>> getChannelCodesByMap(Map<String, Object> paramMap) throws RemitBizException {
		return remitProcessBiz.getChannelCodesByMap(paramMap);
	}

}
