package wusc.edu.pay.facade.remit.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.remit.entity.RemitRequest;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * 
 * @author： Peter
 * @ClassName: RemitRequestFacade.java
 * @Date： 2014-7-22 下午3:44:52
 * @version： V1.0
 */
public interface RemitRequestFacade {

	/**
	 * 生成打款请求号.<br/>
	 * 
	 * @return remitRequestNo 打款请求号.<br/>
	 */
	public String buildRemitRequestNo();


	/**
	 * 创建打款请求实体
	 * 
	 * @param RemitRequest
	 * @return
	 */
	public long create(RemitRequest remitRequest) throws RemitBizException;

	/**
	 * 修改打款请求实体
	 * 
	 * @param RemitRequest
	 * @return
	 */
	public long update(RemitRequest remitRequest) throws RemitBizException;

	/**
	 * 分页查询打款请求实体
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws RemitBizException;

	/**
	 * 根据id获取打款请求实体
	 * 
	 * @param id
	 * @return
	 */
	public RemitRequest getById(long id) throws RemitBizException;

	/**
	 * @Title: 根据打款请求号获取请求实体
	 * @Description:
	 * @param @param requestNo
	 * @param @return
	 * @param @throws RemitBizException
	 * @return RemitRequest
	 * @throws
	 */
	public RemitRequest getByRequestNo(String requestNo) throws RemitBizException;

	/**
	 * @Title: 根据ID删除 打款请求
	 * @Description:
	 * @param @param id
	 * @param @throws RemitBizException
	 * @return void
	 * @throws
	 */
	public void deleteById(long id) throws RemitBizException;

	/**
	 * 批量导入打款记录
	 * 
	 * @param remitRequest
	 * @return
	 * @throws RemitBizException
	 */
	public long batchInsert(List<RemitRequest> remitRequest) throws RemitBizException;
	
	
}
