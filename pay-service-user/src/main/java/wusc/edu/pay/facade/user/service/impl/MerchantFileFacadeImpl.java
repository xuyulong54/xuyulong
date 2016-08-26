package wusc.edu.pay.facade.user.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.biz.MerchantFileBiz;
import wusc.edu.pay.facade.user.entity.MerchantFile;
import wusc.edu.pay.facade.user.service.MerchantFileFacade;



/***
 * 商户资质文件对外接口的实现类
 * @author huangbin
 *
 */
@Component("merchantFileFacade")
public class MerchantFileFacadeImpl implements MerchantFileFacade {
	
	@Autowired
	private MerchantFileBiz merchantFileBiz;
	
	/***
	 * 根据ID查询实体信息
	 * @param id
	 * @return
	 */
	public MerchantFile getById(long id){
		return merchantFileBiz.getById(id);
	}
	
	/**
	 * 分页查询
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return merchantFileBiz.listPage(pageParam, paramMap);
	}
	
	/**
	 * 更新代理商和商户关系
	 * @return result 
	 */
	public long update(MerchantFile entity) {
		return merchantFileBiz.update(entity);
	}
	
	/**
	 * 添加代理商和商户关系
	 * @return result 
	 */
	public long create(MerchantFile entity) {
		return merchantFileBiz.create(entity);
	}

	/***
	 * 根据商户编号查询资质文件信息
	 * @param merchantNo
	 * @return
	 */
	public MerchantFile getByMerchantNo(String merchantNo) {
		return merchantFileBiz.getByMerchantNo(merchantNo);
	}
}
