package wusc.edu.pay.facade.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.biz.MerchantActionBiz;
import wusc.edu.pay.facade.user.entity.MerchantAction;
import wusc.edu.pay.facade.user.service.MerchantActionFacade;


/**
 * 商户权限管理-权限功能点
 * 
 * @author jialong
 * 
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Component("merchantActionFacade")
public class MerchantActionFacadeImpl implements MerchantActionFacade {
	
	@Autowired
	private MerchantActionBiz merchantActionBiz;

	/**
	 * 创建保存商户权限管理-权限功能点
	 */
	public long createMerchantAction(MerchantAction mAction) {
		return merchantActionBiz.createMerchantAction(mAction);
	}

	/**
	 * 修改
	 */
	public long updateMerchantAction(MerchantAction mAction) {
		return merchantActionBiz.updateMerchantAction(mAction);
	}

	/**
	 * 根据id查找
	 */
	public MerchantAction getMerchantActionById(long id) {
		return merchantActionBiz.getMerchantActionById(id);
	}

	/***
	 * 查询商户权限管理-权限功能点 列表
	 */
	public PageBean listPageForMerchantAction(PageParam pageParam, Map<String, Object> paramMap) {
		return merchantActionBiz.listPageForMerchantAction(pageParam, paramMap);
	}

	
	public List<MerchantAction> listMerchantActionByIdStr(String ids) {
		List list = merchantActionBiz.listMerchantActionByIdStr(ids);
		return list;
	}

	@Override
	public List<MerchantAction> listMerActionByMerType(String merType) throws BizException {
		return merchantActionBiz.listMerActionByMerType(merType);
	}

}
