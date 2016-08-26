package wusc.edu.pay.web.boss.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.utils.UUIDUitl;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.account.enums.AccountStatusEnum;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.user.entity.MemberInfo;
import wusc.edu.pay.facade.user.entity.UserAuditRecordStatus;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.enums.MemberStatusEnum;
import wusc.edu.pay.facade.user.enums.MerchantStatusEnum;
import wusc.edu.pay.facade.user.enums.UserAuditStatusEnum;
import wusc.edu.pay.facade.user.enums.UserStatusEnum;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.MemberInfoFacade;
import wusc.edu.pay.facade.user.service.UserAuditFacade;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.facade.user.service.UserOperatorFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;
import wusc.edu.pay.web.boss.mail.EmailConst;
import wusc.edu.pay.web.boss.mail.MailBiz;
import wusc.edu.pay.web.boss.mail.SmsBiz;


/**
 * customer
 * 
 * @author xiehui
 * @time 2013-8-11,下午10:07:35
 */
@Scope("prototype")
public class MemberInfoAction extends BossBaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5293523523472389717L;
	
	private static Log LOG = LogFactory.getLog(MemberInfoAction.class);

	@Autowired
	private MemberInfoFacade memberInfoFacade;
	@Autowired
	private UserAuditFacade userAuditFacade;
	@Autowired
	private UserQueryFacade userQueryFacade;
	@Autowired
	private UserManagementFacade userManagementFacade;
	@Autowired
	private UserOperatorFacade userOperatorFacade;
	@Autowired
	private MailBiz mailBiz;
	@Autowired
	private SmsBiz smsBiz;


	/**
	 * 查询会员信息
	 * 
	 * @return
	 */
	@Permission("member:info:view")
	public String listMemberInfo() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String memberNo = getString("memberNo");
		paramMap.put("memberNo", memberNo);// 会员编号
		paramMap.put("realName", getString("realName"));// 真实姓名
		paramMap.put("cardNo", getString("cardNo"));// 身份证号

		String startDate = getString("startDate");
		String endDate = getString("endDate");
		// 对时间进行校验
		if (startDate != null && endDate != null) {
			// 取得两个日期之间的日数
			long a = DateUtils.daysBetween(DateUtils.toSqlTimestamp(startDate), DateUtils.toSqlTimestamp(endDate));

			if (a < 0) {
				return this.operateError("开始时间不能大于结束时间");
			}
		} else if ((startDate != null && endDate == null) || (startDate == null && endDate != null)) {
			return this.operateError("不能只输入一个时间查询");
		}

		paramMap.put("startDate", startDate);//
		paramMap.put("endDate", endDate);//
		paramMap.put("status", getString("status"));//

		if (startDate != null && !"".equals(startDate)) {
			Long s1 = Long.valueOf(startDate.replace("-", ""));
			Long e1 = Long.valueOf(endDate.replace("-", ""));
			Date sDate = new Date(s1);
			Date eDate = new Date(e1);
			if (eDate.getTime() < sDate.getTime()) {
				return operateError("结束时间必须大于开始时间");
			}
		}
		paramMap.put("userType", UserTypeEnum.CUSTOMER.getValue());
		super.pageBean = memberInfoFacade.listPage(getPageParam(), paramMap);
		// 加载认证状态
		this.pushData(pageBean);
		this.pushData(paramMap);
		this.putData("memberStatusList", MemberStatusEnum.toList()); // 会员状态查询
		return "listMemberInfo";
	}

	/***
	 * 跳转到修改会员状态变更申请页面
	 * 
	 * @return
	 */
	@Permission("member:info:changestatus")
	public String toChangeMemberStatusUI() {
		this.putData("memberId", getString("id"));
		this.putData("status", getString("status"));
		return "memberStatusUI";
	}

	/***
	 * 重置会员登录密码
	 * 
	 * @return
	 */
	@Permission("member:info:resetpassword")
	public String resetPassword() {
		Long id = getLong("id");
		if (id == null) {
			return operateError("会员ID不能为空");
		}
		
		MemberInfo memberInfo = memberInfoFacade.getById(id);
		if (memberInfo == null) {
			return operateError("该会员信息不存在");
		}

		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(memberInfo.getMemberNo());
		if (userInfo == null) {
			return operateError("会员对应的用户信息不存在");
		}
		
		UserOperator memberOperator = userOperatorFacade.getUserOperatorByLoginName(userInfo.getLoginName());
		if (memberOperator == null) {
			return operateError("会员对应的操作员信息不存在");
		}
		
		String newPwd = UUIDUitl.generateString(8);
		memberOperator.setLoginPwd(DigestUtils.sha1Hex(newPwd));
		memberOperator.setIsChangedPwd(1); // 密码已更改
		
		if (userOperatorFacade.updateUserOperator(memberOperator) > 0) {
			String email = userInfo.getBindEmail();
			
			if (StringUtil.isNotBlank(email)) {
				// 封装邮件发送
				Map<String, Object> paramModel = new HashMap<String, Object>();
				paramModel.put("loginName", userInfo.getLoginName());
				paramModel.put("newPwd", newPwd);
				paramModel.put("COMPANY_FOR", PublicConfig.COMPANY_FOR);
				
				// 通过模板生成邮件内容
				String content = "";
				try {
					paramModel.put("phone", EmailConst.PHONE);
					content = mailBiz.mergeEmailTemplate(EmailConst.RESET_MEMBER_LOGIN_PASSWORD, paramModel);
				} catch (Exception e) {
					LOG.error("邮件模板解释异常", e);
				}
				
				mailBiz.sendMail(email, "重置会员登录密码", content);
			}
			super.logEdit("重置会员登录密码.会员编号[" + memberInfo.getMemberNo() + "]");
			return operateSuccess();
		}
		
		super.logEditError("重置会员登录密码.会员编号[" + memberInfo.getMemberNo() + "]");
		return operateError("操作失败");
	}

	/***
	 * 提交会员状态变更请求 ==后台会员激活与冻结
	 * 
	 * @return
	 */
	@Permission("member:info:changestatus")
	public String changeMemberStatus() {
		Long id = getLong("id");
		int currentStatus = MemberStatusEnum.ACTIVE.getValue() == getInteger("status").intValue() ? MemberStatusEnum.INACTIVE
				.getValue() : MemberStatusEnum.ACTIVE.getValue(); // 当前状态
		int changeStatus = MemberStatusEnum.INACTIVE.getValue() == getInteger("status").intValue() ? MemberStatusEnum.INACTIVE
				.getValue() : MemberStatusEnum.ACTIVE.getValue(); // 变更状态
		String requestDesc = getString("requestDesc"); // 变更原因
		MemberInfo memberInfo = memberInfoFacade.getById(id);
		if (memberInfo == null)
			return operateError("会员基本信息为空");
		try {
			userAuditFacade.createUserAuditRecordStatus(memberInfo.getMemberNo(), getLoginedOperator().getLoginName(), getLoginedOperator()
					.getRealName(), requestDesc, changeStatus, currentStatus);
		} catch (UserBizException e) {
			return operateError(e.getMsg() + "， 异常代码：" + e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			return operateError("系统异常，请联系管理员");
		}
		super.logSave(
				"操作员：" + getLoginedOperator().getRealName() + " 添加会员变更状态 " + MemberStatusEnum.getEnum(changeStatus).getDesc() + " 会员编号[" + memberInfo.getMemberNo() + "]");
		return operateSuccess();

	}

	/***
	 * 查询商户状态变更审核申请表
	 * 
	 * @return
	 */
	@Permission("member:status:view")
	public String listMemberStatus() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("auditStatus", getString("auditStatus"));
		paramMap.put("fullName", getString("fullName"));
		paramMap.put("userNo", getString("userNo"));
		paramMap.put("userType", UserTypeEnum.CUSTOMER.getValue());
		super.pageBean = userAuditFacade.queryUserAuditRecordStatusListPage(getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		super.putEnums();
		return "listMemberStatus";
	}

	/***
	 * 审核会员状态变更申请表
	 * 
	 * @return
	 */
	@Permission("member:status:view")
	public String toAudit() {
		Long id = getLong("id");
		if (id != null) {
			UserAuditRecordStatus aduit = userAuditFacade.getUserAuditRecordStatusById(id);
			this.pushData(aduit);
		}
		this.putData("isView", getString("isView"));
		this.putData("UserAuditStatusEnum", UserAuditStatusEnum.toMap());
		return "auditMemberview";
	}

	/***
	 * 审核操作
	 * 
	 * @return
	 */
	@Permission("member:status:audit")
	public String doAduit() {
		Long id = getLong("auditId");
		if (id != null) {
			Integer auditStatus = getInteger("status"); // 审核状态, 控制审核记录通过不通过
			String auditDesc = getString("auditDesc");
			String userNo = getString("userNo");
			Integer changeStatus = getInteger("changeStatus"); // 如果审核通过，需要改变会员的状态
			Integer currentStatus = getInteger("currentStatus");
			UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(userNo);
			if (userInfo == null)
				return operateError("用户信息为空");
			if (auditStatus == UserStatusEnum.ACTIVE.getValue()) {// 通过
				
				String bindEmail = userInfo.getBindEmail();
				String bindMobileNo = userInfo.getBindMobileNo();
					
				// 封装邮件发送
				Map<String, Object> paramModel = new HashMap<String, Object>();
				paramModel.put("loginName", userInfo.getLoginName());
				paramModel.put("fullName", userInfo.getRealName());
				paramModel.put("reason", getString("applyDesc"));
				paramModel.put("changeStatus", changeStatus);
				paramModel.put("COMPANY_FOR", PublicConfig.COMPANY_FOR);
				
				// 通过模板生成邮件内容
				String content = "";
				try {
					content = mailBiz.mergeEmailTemplate(EmailConst.MERCHANT_AUDIT_NOTPASS, paramModel);
					if (!StringUtil.isEmpty(bindEmail)){
						mailBiz.sendMail(bindEmail, "商户审核结果邮件", content);
					}
					if(!StringUtil.isEmpty(bindMobileNo)){
						smsBiz.sendSms(bindMobileNo, "尊敬的会员，您的会员状态已被"+ MerchantStatusEnum.getEnum(changeStatus).getDesc());
					}
				} catch (Exception e) {
					LOG.error("邮件或者信息发送异常", e);
				}
				
				userAuditFacade.auditPass_Status(id, userInfo.getUserNo(), getLoginedOperator().getLoginName(), getLoginedOperator()
						.getRealName(), "", auditDesc, changeStatus, currentStatus);
				super.logEdit("会员状态审核：会员编号[" + userNo + "]，审核结果[通过]"+"备注： [" + auditDesc + "]");
			} else {
				userAuditFacade.auditRefuse_Status(id, userInfo.getUserNo(), getLoginedOperator().getLoginName(), getLoginedOperator()
						.getRealName(), "", auditDesc, changeStatus, currentStatus);
				super.logEdit("会员状态审核：会员编号[" + userNo + "]，审核结果[不通过]"+"备注： [" + auditDesc + "]");
			}
			return operateSuccess();
		}
		return this.operateError("数据有误,请稍后重试");
	}

	/***
	 * 审核会员信息 首先查询到会员对应的账户信息 根据账户信息查询账户安全表中有无数据 如果没有，则表示实名认证不通过
	 * 如果有，再更新实名认证表和账户安全表
	 * 
	 * @return
	 */
	public String auditMember() {
		Long memberId = getLong("memberId");
		// String type = getString("type");// 状态
		if (memberId != null) {
			// 修改实名认证表的状态
			// 修改会员表实名认证状态
			// 根据ID跟类型查询账户信息
			MemberInfo memberinfo = memberInfoFacade.getById(memberId);
			if (memberinfo == null)
				return operateError("会员信息为空");

			UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(memberinfo.getMemberNo());
			if (userInfo == null)
				return operateError("用户信息为空");

			userInfo.setIsRealNameAuth(PublicStatusEnum.ACTIVE.getValue());

			if (userManagementFacade.updateUserInfo(userInfo) > 0) {
				super.logEdit("修改用户表的实名认证状态，用户编号[" + memberinfo.getMemberNo() + "],会员编号[" + memberinfo.getMemberNo() + "]");
				return operateSuccess();
			} else {
				return operateError("修改账户信息失败");
			}
		} else {
			return operateError("审核会员信息异常，会员信息不存在！");
		}
	}

	/**
	 * 查看会员的详细信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@Permission("member:info:view")
	public String viewMemberInfo() {
		Long id = getLong("id");
		if (null == id) {
			return operateError("会员ID为空");
		}
		MemberInfo memberInfo = memberInfoFacade.getById(id);
		if(memberInfo == null) return operateError("会员信息为空");
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(memberInfo.getMemberNo());
		this.pushData(memberInfo); // 显示会员表基本信息
		this.putData("accountStatusEnum", AccountStatusEnum.toList());
		super.putEnums();
		if (userInfo != null) {
			this.pushData(userInfo); // 显示用户表信息
			return "viewMemberInfo";
		} else {
			return operateError("会员信息不存在");
		}
	}
}
