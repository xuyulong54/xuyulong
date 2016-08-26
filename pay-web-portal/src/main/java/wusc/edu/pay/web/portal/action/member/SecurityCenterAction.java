package wusc.edu.pay.web.portal.action.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.UserAuditStatusEnum;
import wusc.edu.pay.facade.user.service.UserAuditFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.portal.base.MemberLogonAction;


/**
 * 商户：安全中心
 * 
 * @author liliqiong
 * @date 2013-11-4
 * @version 1.0
 */
public class SecurityCenterAction extends MemberLogonAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	UserAuditFacade userAuditFacade;
	@Autowired
	UserQueryFacade userQueryFacade;

	/**
	 * 会员：安全中心首页
	 * 
	 * @return
	 */
	public String securityCenter() {
		// 会员实体
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(getCurrentUserInfo().getUserNo());
		putData("bindEmail", ValidateUtils.isEmpty(userInfo.getBindEmail()) ? "" : StringTools.phoneChange(userInfo.getBindEmail()));
		putData("bindMobileNo", ValidateUtils.isEmpty(userInfo.getBindMobileNo()) ? "" : StringTools.phoneChange(userInfo.getBindMobileNo()));
		
		

		// 帐户验证标识
		putData("isNotWaitRealName", isNotWaitRealName());
		putData("userInfo", userInfo);
		putData("PublicStatusEnum", PublicStatusEnum.toMap());
		super.putEnums();
		return "securityCenter";
	}

	/**
	 * 是在在做实名认证申请
	 * 
	 * @return
	 */
	public Boolean isNotWaitRealName() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", getCurrentUserInfo().getUserNo());
		paramMap.put("auditStatus", UserAuditStatusEnum.WAIT.getValue());
		pageBean = userAuditFacade.queryUserAuditRecordRealNameListPage(getPageParam(), paramMap);
		if (ValidateUtils.isEmpty(pageBean) || pageBean.getTotalCount() <= 0) {
			return false;
		}
		return true;
	}
}
