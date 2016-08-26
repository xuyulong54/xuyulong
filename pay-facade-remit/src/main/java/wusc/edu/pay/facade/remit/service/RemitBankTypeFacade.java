package wusc.edu.pay.facade.remit.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.remit.entity.RemitBankType;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * @Title: 银行行别信息服务接口
 * @Description: 
 * @author zzh
 * @date 2014-7-24 下午3:33:52
 */
public interface RemitBankTypeFacade {

	/**
	 * @Title: 创建银行行别信息 
	 * @Description: 
	 * @param @param remitBankType
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return long  
	 * @throws
	 */
	public long create(RemitBankType remitBankType) throws RemitBizException; 
	
	/**
	 * @Title: 更新银行行别信息 
	 * @Description: 
	 * @param @param remitBankType
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return int  
	 * @throws
	 */
	public long update(RemitBankType remitBankType) throws RemitBizException;
	
	/**
	 * @Title: 分页查询银行行别信息 
	 * @Description: 
	 * @param @param pageParam
	 * @param @param paramMap
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return PageBean  
	 * @throws
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws RemitBizException;
	
	/**
	 * @Title: 根据ID查询 银行行别信息
	 * @Description: 
	 * @param @param id
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return RemitBankInfo  
	 * @throws
	 */
	public RemitBankType getById(long id) throws RemitBizException;
	
	/**
	 * @Title: 根据银行行别代码获取银行行别信息 
	 * @Description: 
	 * @param @param typeCode
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return RemitBankType  
	 * @throws
	 */
	public RemitBankType getByTypeCode(String typeCode) throws RemitBizException;
	
	/**
	 * 根据银行编号获取银行行行别信息
	 * @param bankCode
	 * @return
	 * @throws RemitBizException
	 */
	public RemitBankType getByBankCode(String bankCode) throws RemitBizException;
	/**
	 * @Title: 查询所有行别 
	 * @Description: 
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return RemitBankType  
	 * @throws
	 */
	public List<RemitBankType> listAll()  throws RemitBizException;
	
	/**
	 * 查询所有有效的行别
	 * @return
	 * @throws RemitBizException
	 */
	public List<RemitBankType> listActiveBank() throws RemitBizException;
	
	/**
	 * 通过in条件查询出所有有效的行别
	 * @param bankCodeList
	 * @return
	 * @throws RemitBizException
	 */
	public List<RemitBankType> listActiveBankByIn(List<String> bankCodeList) throws RemitBizException;
	
	/**
	 * 通过not in条件查询出所有有效的行别
	 * @param bankCodeList
	 * @return
	 * @throws RemitBizException
	 */
	public List<RemitBankType> listActiveBankByNotIn(List<String> bankCodeList) throws RemitBizException;
}
