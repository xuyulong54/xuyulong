package wusc.edu.pay.facade.remit.service;

import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.remit.entity.RemitBatch;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;


/**
 * @Title: 打款批次服务接口
 * @Description: 
 * @author zzh
 * @date 2014-7-22 下午3:21:08
 */
public interface RemitBatchFacade {
	/**
	 * @Title: 增加打款批次 
	 * @Description:  
	 * @param @param remitBatch
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return long  
	 * @throws
	 */
	public long create(RemitBatch remitBatch) throws RemitBizException;
	
	/**
	 * @Title: 更新打款批次 
	 * @Description: 
	 * @param @param remitBatch
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return int  
	 * @throws
	 */
	public long update(RemitBatch remitBatch) throws RemitBizException;
	
	/**
	 * @Title: 分页查询打款批次 
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
	 * @Title: 根据ID获取打款批次信息 
	 * @Description: 
	 * @param @param id
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return RemitBatch  
	 * @throws
	 */
	public RemitBatch getById(long id) throws RemitBizException;
	
	/**
	 * @Title: 根据批次号获取打款批次信息 
	 * @Description: 
	 * @param @param batchNo
	 * @param @return
	 * @param @throws RemitBizException    
	 * @return RemitBatch  
	 * @throws
	 */
	public RemitBatch getByBatchNo(String batchNo)throws RemitBizException;
}
