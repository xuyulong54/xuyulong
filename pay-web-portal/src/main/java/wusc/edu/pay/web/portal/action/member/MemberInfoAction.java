package wusc.edu.pay.web.portal.action.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.enums.SecurityQuestionEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.user.entity.MemberInfo;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.UserAuditStatusEnum;
import wusc.edu.pay.facade.user.enums.UserCheckRealNameStatusEnum;
import wusc.edu.pay.facade.user.service.MemberInfoFacade;
import wusc.edu.pay.facade.user.service.UserAuditFacade;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.base.MemberLogonAction;

/**
 * <ul>
 * <li>Title:会员基本信息</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Liliqiong
 * @version 2014-6-6
 */
// @Scope("prototype")
public class MemberInfoAction extends MemberLogonAction {
	private static final long serialVersionUID = 1L;
	private static Log LOG = LogFactory.getLog(MemberInfoAction.class);
	@Autowired
	private MemberInfoFacade memberInfoFacade;
	@Autowired
	private UserManagementFacade userManagementFacade;
	@Autowired
	private UserAuditFacade userAuditFacade;
	@Autowired
	private UserQueryFacade userQueryFacade;

	/**
	 * 会员信息详情
	 * 
	 * @return
	 */
	public String viewMember() {

		// 获取商户基本信息
		MemberInfo member = this.getMemberInfo();

		super.putEnums();
		putData("showRealName",
				StringTools.realNameChange(member.getRealName()));
		putData("showCardNo",
				ValidateUtils.isEmpty(getCurrentUserInfo().getCardNo()) ? ""
						: StringTools.idCardChange(getCurrentUserInfo()
								.getCardNo()));

		putData("member", member);
		putData("questions", SecurityQuestionEnum.values());
		putData("PublicStatusEnum", PublicStatusEnum.toMap());
		putData("isNotWaitRealName", isNotWaitRealName());
		putData("userBean", userQueryFacade.getUserInfoByUserNo(getCurrentUserInfo().getUserNo()));
		return "viewMember";
	}

	/**
	 * 去修改会员信息页面
	 * 
	 * @return
	 */
	public String editMemberUI() {

		return "editMemberUI";
	}

	/**
	 * 修改会员信息
	 * 
	 * @return
	 */
	public String editMember() {
		String realName = getString("realName");// 真实姓名
		String cardNo = getString("cardNo");// 身份证号

		// 1.验证页面参数
		Map<String, String> msgMap = validateEditMember(realName, cardNo);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		// 2.更新基本信息表
		MemberInfo memberInfo = memberInfoFacade
				.getMemberByUserNo(getCurrentUserInfo().getUserNo());
		memberInfo.setRealName(realName);
		memberInfoFacade.update(memberInfo);

		// 3.更新用户表
		UserInfo userInfo = super.getUserInfoByUserNo(getCurrentUserInfo()
				.getUserNo());
		userInfo.setCardNo(cardNo);
		userInfo.setRealName(realName);
		userManagementFacade.updateUserInfo(userInfo);

		// 4.更新SESSION
		setCurrentUserInfo(userInfo);
		return "editMember";
	}

	/**
	 * 跳转至指定的URL
	 */
	public void redirectUrl() {
		try {
			getHttpResponse().sendRedirect(
					(String) getSessionMap().remove(
							BaseConsts.MEMBER_REDIRECT_URL));
		} catch (IOException e) {
			LOG.error("redirectUrl fail:", e);
		}
	}

	private Map<String, String> validateEditMember(String realName,
			String cardNo) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		if (getCurrentUserInfo().getIsRealNameAuth().intValue() == UserCheckRealNameStatusEnum.SECCUSS
				.getValue()) {
			msgMap.put(errorMsg, "已实名认证，不可修改基本信息");
			return msgMap;
		}
		if (getCurrentUserInfo().getIsRealNameAuth().intValue() == UserCheckRealNameStatusEnum.WAIT
				.getValue()) {
			msgMap.put(errorMsg, "正在实名认证，不可修改基本信息");
			return msgMap;
		}
		// 真实姓名验证
		if (ValidateUtils.isEmpty(realName) || realName.length() < 2
				|| realName.length() > 10 || !ValidateUtils.isChinese(realName)) {
			msgMap.put(errorMsg, "真实姓名请输入2-10位的中文");
			return msgMap;
		}
		// 身份证验证
		if (ValidateUtils.isEmpty(cardNo) || !ValidateUtils.isIdCard(cardNo)) {
			msgMap.put(errorMsg, "请输入18位正确格式的身份证号");
		}
		return msgMap;
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
		pageBean = userAuditFacade.queryUserAuditRecordRealNameListPage(
				getPageParam(), paramMap);
		if (ValidateUtils.isEmpty(pageBean) || pageBean.getTotalCount() <= 0) {
			return false;
		}
		return true;
	}
}
