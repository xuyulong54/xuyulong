package wusc.edu.pay.facade.user.service;

import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.user.entity.MerchantFile;
import wusc.edu.pay.facade.user.exceptions.UserBizException;



/***
 * 商户资质文件的对外接口
 * @author huangbin
 *
 */
public interface MerchantFileFacade {
	
	/***
	 * 根据ID查询实体信息
	 * @param id
	 * @return
	 */
	public MerchantFile getById(long id);
	
	/**
	 * 分页查询
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) ;
	
	/**
	 * 更新商户资质文件
	 * @return result 
	 */
	public long update(MerchantFile relation) throws UserBizException;
	
	/**
	 * 添加商户资质文件
	 * @return result 
	 */
	public long create(MerchantFile relation) throws UserBizException;

	/***
	 * 根据商户编号查询资质文件信息
	 * @param merchantNo
	 * @return
	 */
	public MerchantFile getByMerchantNo(String merchantNo);
	
}
