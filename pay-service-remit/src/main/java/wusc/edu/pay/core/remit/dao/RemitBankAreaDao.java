package wusc.edu.pay.core.remit.dao;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.remit.entity.RemitBankArea;


/**
 * @Title: 银行地区信息Dao接口 
 * @Description: 
 * @author zzh
 * @date 2014-7-24 下午3:47:38
 */
public interface RemitBankAreaDao extends BaseDao<RemitBankArea>{
	/**
	 * @Title: 获取省份信息 
	 * @Description: 
	 * @param @return    
	 * @return List<Map<String, Object>>  listMap: {[province:xxx],[province:xxx]} 
	 * @throws
	 */
	public List<Map<String, Object>> getProvince();
	
	/**
	 * @Title: 根据省份获取下面的城市信息 
	 * @Description: 
	 * @param @param province
	 * @param @return    
	 * @return List<Map<String,Object>>  listMap:{[city:xxx],[city:xxx]}
	 * @throws
	 */
	public List<Map<String, Object>> getCityByProvince(String province);
	
	/**
	 * @Title: 根据区域代码查询  
	 * @Description: 
	 * @param @param areaCode
	 * @param @return    
	 * @return List<RemitBankArea>  
	 * @throws
	 */
	public List<RemitBankArea> listByAreaCode(String areaCode);
}
