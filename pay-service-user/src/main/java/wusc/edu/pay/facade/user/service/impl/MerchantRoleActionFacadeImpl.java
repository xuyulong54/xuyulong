package wusc.edu.pay.facade.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.biz.MerchantActionBiz;
import wusc.edu.pay.facade.user.entity.MerchantRoleAction;
import wusc.edu.pay.facade.user.service.MerchantRoleActionFacade;


/**
 * 
 * @author jialong
 * 
 */
@Component("merchantRoleActionFacade")
public class MerchantRoleActionFacadeImpl implements MerchantRoleActionFacade {
	@Autowired
	private MerchantActionBiz merchantActionBiz; // 商户角色Biz

	
	public long createMerchantRoleAction(MerchantRoleAction merchantRoleAction) {
		return merchantActionBiz.createMerchantRoleAction(merchantRoleAction);
	}

	
	public long updateMerchantRoleAction(MerchantRoleAction merchantRoleAction) {
		return merchantActionBiz.updateMerchantRoleAction(merchantRoleAction);
	}

	public MerchantRoleAction getMerchantRoleActionById(long id) {
		return merchantActionBiz.getMerchantRoleActionById(id);
	}

	public PageBean listPageForMerchantRoleAction(PageParam pageParam, Map<String, Object> paramMap) {
		return merchantActionBiz.listPageForMerchantAction(pageParam, paramMap);
	}

	public String getMRActionStrByRoleIds(String roleIds) {
		return merchantActionBiz.getMerchantRoleActionStrByRoleIds(roleIds);
	}

	public void deleteMerchantRoleActionByActionId(Long actionId) {
		//merchantRoleActionService.deleteByActionId(actionId);
		merchantActionBiz.deleteMerchantRoleActionById(actionId);
	}

	public void saveMerchantRoleAction(long roleId, String actionStr) {
		//merchantRoleActionService.saveRoleAction(roleId, actionStr);
		merchantActionBiz.saveMerchantRoleAction(roleId, actionStr);
	}

	public void deleteMerchantRoleActionByRoleId(Long roleId) {
		//merchantRoleActionService.deleteRoleActionByRoleId(roleId);
		merchantActionBiz.deleteMerchantRoleActionByRoleId(roleId);
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List listMerchantRoleActionByRoleId(long roleId) {
		//return merchantRoleActionService.findByRoleId(roleId);
		return merchantActionBiz.listMerchantRoleActionByRoleId(roleId);
	}

	
	public String getMRActionStrByRoleId(Long id) {
		//return merchantRoleActionService.getActionStrByRoleId(id);
		return merchantActionBiz.getMerchantActionStrByRoleId(id);
	}

}
