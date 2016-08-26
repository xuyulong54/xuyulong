/**
 * wusc.edu.pay.facade.remit.service.RemitErrorFacade.java
 */
package wusc.edu.pay.facade.remit.service;

import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.remit.entity.RemitError;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * <ul>
 * <li>Title: 差错对外接口</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Liliqiong
 * @version 2014-8-6
 */
public interface RemitErrorFacade {
	
	/**
	 * 创建差错实体
	 * 
	 * @param remitError
	 * @return
	 * @throws RemitBizException
	 */
	public long create(RemitError remitError) throws RemitBizException;

	/**
	 * 更新差错实体
	 * 
	 * @param remitError
	 * @return
	 * @throws RemitBizException
	 */
	public long update(RemitError remitError) throws RemitBizException;

	/**
	 * 分页查询差错实体
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 * @throws RemitBizException
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws RemitBizException;

	
	/**
	 * 根据ID差错实体
	 * @param id
	 * @return
	 * @throws RemitBizException
	 */
	public RemitError getById(long id) throws RemitBizException;
	
	/**
	 * @Title: 根据打款请求号获取差错实体 
	 * @Description: 
	 * @param @param requestNo
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return RemitError  
	 * @throws
	 */
	public RemitError getByRequestNo(String requestNo) throws RemitBizException;
}
