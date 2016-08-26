package wusc.edu.pay.facade.remit.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.remit.entity.RemitBankArea;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * @Title: 银行地区信息服务接口
 * @Description: 
 * @author zzh
 * @date 2014-7-24 下午3:30:46
 */
public interface RemitBankAreaFacade {
	/**
	 * @Title: 创建银行地区信息 
	 * @Description: 
	 * @param @param remitBankArea
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return long  
	 * @throws
	 */
	public long create(RemitBankArea remitBankArea) throws RemitBizException;
	
	/**
	 * @Title: 更新银行地区信息 
	 * @Description: 
	 * @param @param remitBankArea
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return int  
	 * @throws
	 */
	public long update(RemitBankArea remitBankArea) throws RemitBizException;
	
	/**
	 * @Title: 分页查询银行地区信息 
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
	 * @Title: 根据ID查询银行地区信息 
	 * @Description: 
	 * @param @param id
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return RemitBankInfo  
	 * @throws
	 */
	public RemitBankArea getById(long id) throws RemitBizException;
	
	/**
	 * @Title: 获取省份信息 
	 * @Description:
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return List<Map<String, Object>>  listMap: {[province:xxx],[province:xxx]}
	 * @throws
	 */
	public List<Map<String, Object>> getProvince() throws RemitBizException;
	
	/**
	 * @Title: 根据省份获取下面的城市信息   
	 * @Description: 
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return List<Map<String,Object>>  listMap:{[city:xxx],[city:xxx]}  
	 * @throws
	 */
	public List<Map<String, Object>> getCityByProvince(String province) throws RemitBizException;
	
	/**
	 * @Title: 根据区域代码查询 
	 * @Description: 
	 * @param @param areaCode
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return List<RemitBankArea>  
	 * @throws
	 */
	public List<RemitBankArea> listByAreaCode(String areaCode) throws RemitBizException;
}
