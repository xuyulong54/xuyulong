package wusc.edu.pay.facade.remit.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.remit.entity.RemitProcess;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * 
 * @author： Peter
 * @ClassName: RemitProcessFacade.java
 * @Date： 2014-7-22 下午3:42:23
 * @version: V1.0
 */
public interface RemitProcessFacade {
	/**
	 * 创建打款处理记录实体
	 * 
	 * @param RemitProcess
	 * @return
	 */
	public long creat(RemitProcess remitProcess) throws RemitBizException;

	/**
	 * 修改打款处理记录实体
	 * 
	 * @param RemitProcess
	 * @return
	 */
	public long update(RemitProcess remitProcess) throws RemitBizException;

	/**
	 * 分页查询打款处理记录实体
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws RemitBizException;

	/**
	 * 根据id获取打款处理记录实体
	 * 
	 * @param id
	 * @return
	 */
	public RemitProcess getById(long id) throws RemitBizException;

	/**
	 * @Title: 根据打款请求号获取打款处理记录实体
	 * @Description:
	 * @param @param requestNo
	 * @param @return
	 * @param @throws RemitBizException
	 * @return RemitProcess
	 * @throws
	 */
	public RemitProcess getByRequestNo(String requestNo) throws RemitBizException;

	/**
	 * 条件查询打款记录
	 * 
	 * @param paramMap
	 * @return
	 * @throws RemitBizException
	 */
	public List<RemitProcess> listBy(Map<String, Object> paramMap) throws RemitBizException;

}
