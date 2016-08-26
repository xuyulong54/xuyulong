package wusc.edu.pay.web.portal.action.merchant;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.UserOperatorTypeEnum;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.portal.base.BaseAction;


/**
 * 商户：安全中心
 * 
 * @author liliqiong
 * @date 2013-11-4
 * @version 1.0
 */
public class SecurityCenterAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	UserQueryFacade userQueryFacade;

	/**
	 * 商户：安全中心首页
	 * 
	 * @return
	 */
	public String securityCenter() {

		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(getCurrentUserInfo().getUserNo());

		// 商户实体
		putData("bindEmail", ValidateUtils.isEmpty(userInfo.getBindEmail()) ? "" : StringTools.phoneChange(userInfo.getBindEmail()));
		putData("bindMobileNo", ValidateUtils.isEmpty(userInfo.getBindMobileNo()) ? "" : StringTools.phoneChange(userInfo.getBindMobileNo()));

		putData("userInfo",userInfo);
		putData("PublicStatusEnum", PublicStatusEnum.toMap());
		putData("UserOperatorTypeEnum", UserOperatorTypeEnum.toMap());
		
		return "securityCenter";
	}
}
