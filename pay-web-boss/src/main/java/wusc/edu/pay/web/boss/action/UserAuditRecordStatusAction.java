package wusc.edu.pay.web.boss.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.user.entity.UserAuditRecordStatus;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.MerchantStatusEnum;
import wusc.edu.pay.facade.user.enums.UserAuditStatusEnum;
import wusc.edu.pay.facade.user.enums.UserStatusEnum;
import wusc.edu.pay.facade.user.service.UserAuditFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;
import wusc.edu.pay.web.boss.mail.EmailConst;
import wusc.edu.pay.web.boss.mail.MailBiz;
import wusc.edu.pay.web.permission.entity.PmsOperator;


/**
 * 
 * @描述: 在线商户状态变更审核.
 * @作者: Lanzy.
 * @创建时间: 2014-6-19, 上午11:28:01 .
 * @版本: V1.0.
 * 
 */
public class UserAuditRecordStatusAction extends BossBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6845487696029140011L;

	@Autowired
	private UserAuditFacade userAuditFacade;
	@Autowired
	private UserQueryFacade userQueryFacade;
	@Autowired
	private MailBiz mailBiz;

	private static Log LOG = LogFactory.getLog(UserAuditRecordStatusAction.class);

	/***
	 * 跳转到修改账户状态页面
	 * 
	 * @return
	 */
	@Permission("merchant:info:changestatus")
	public String toChangeMerchantStatusUI() {
		this.putData("merchantNo", getString("merchantNo"));
		this.putData("status", getString("status"));
		return "changeMerchantStatusUI";

	}

	/**
	 * 对商户账户的冻结或者激活
	 * 
	 * @return operateSuccess or operateError .
	 */
	@Permission("merchant:info:changestatus")
	public String changeMerchantStatus() {
		String merchantNo = getString("merchantNo");
		int currentStatus = MerchantStatusEnum.ACTIVE.getValue() == getInteger("status").intValue() ? MerchantStatusEnum.INACTIVE.getValue() : MerchantStatusEnum.ACTIVE.getValue(); // 变更状态
		int changeStatus = MerchantStatusEnum.INACTIVE.getValue() == getInteger("status").intValue() ? MerchantStatusEnum.INACTIVE.getValue() : MerchantStatusEnum.ACTIVE.getValue(); // 当前状态
		String requestDesc = getString("requestDesc");
		String statusDesc = changeStatus == UserStatusEnum.ACTIVE.getValue() ? "激活" : "冻结";

		userAuditFacade.createUserAuditRecordStatus(merchantNo, getLoginedOperator().getLoginName(), getLoginedOperator().getRealName(), requestDesc, changeStatus, currentStatus);

		super.logSave("操作员" + getLoginedOperator().getRealName() + "添加商户变更状态审核.商户编号：[" + merchantNo + "]，申请[" + statusDesc + "]的记录");
		return operateSuccess();
	}

	/***
	 * 列表
	 * 
	 * @return
	 */
	@Permission("merchant:status:view")
	public String list() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String auditStatus = getString("auditStatus");
		if (auditStatus == null || "".equals(auditStatus)) {
			paramMap.put("auditStatus", UserAuditStatusEnum.WAIT.getValue());
		} else if ("1".equals(auditStatus)) {

		} else {
			paramMap.put("auditStatus", getString("auditStatus"));
		}
		paramMap.put("fullName", getString("fullName"));
		paramMap.put("userNo", getString("userNo"));
		paramMap.put("currentStatus", getString("currentStatus"));
		// paramMap.put("userType", UserTypeEnum.MERCHANT.getValue());//
		// 只查在线商户的记录
		super.pageBean = userAuditFacade.queryUserAuditRecordStatusListPage(getPageParam(), paramMap);
		// 加载认证状态
		this.pushData(pageBean);
		this.pushData(paramMap);
		putData("UserAuditStatusEnum", UserAuditStatusEnum.toMap());
		putData("UserStatusEnum", MerchantStatusEnum.toMap());
		putData("UserAuditStatusEnumList", UserAuditStatusEnum.values());
		putData("UserStatusEnumList", MerchantStatusEnum.values());

		return "merchantStatusAduitList";
	}

	/***
	 * 查看详情
	 * 
	 * @return
	 */
	@Permission("merchant:status:view")
	public String view() {
		Long id = getLong("id");
		UserAuditRecordStatus aduit = userAuditFacade.getUserAuditRecordStatusById(id);
		this.pushData(aduit);
		this.putData("isView", getString("isView"));
		this.putData("MerchantStatusEnums", MerchantStatusEnum.toMap());
		return "merchantStatusAduitView";
	}

	/***
	 * 商户状态审核通过操作
	 * 
	 * @return auditPass_Status
	 */
	@Permission("merchant:status:audit")
	public String auditPassStatus() {
		Long id = getLong("id");
		UserAuditRecordStatus userAuditRecordStatus = userAuditFacade.getUserAuditRecordStatusById(id);
		Integer changeStatus = getInteger("changeStatus"); // 审核(变更)状态
		String auditDesc = getString("auditDesc");
		String applyDesc = getString("applyDesc");
		PmsOperator operator = this.getLoginedOperator();
		String userNo = getString("userNo");

		userAuditFacade.auditPass_Status(id, userNo, operator.getLoginName(), operator.getRealName(), "", auditDesc, changeStatus, userAuditRecordStatus.getCurrentStatus());

		super.logSave("操作员：" + operator.getRealName() + "审核商户状态.商户登录名：[" + getString("loginName") + "]，商户编号[" + getString("userNo") + "]，审核结果[通过]");
		if (sendMail(userNo, applyDesc, MerchantStatusEnum.getEnum(changeStatus).getDesc(), 100)) {
			return operateSuccess();
		} else {
			return operateError("商户状态审核成功，但发邮件失败！");
		}
	}

	/***
	 * 商户状态审核不通过操作
	 * 
	 * @return auditPass_Status
	 */
	@Permission("merchant:status:audit")
	public String auditRefuseStatus() {
		Long id = getLong("id");
		UserAuditRecordStatus userAuditRecordStatus = userAuditFacade.getUserAuditRecordStatusById(id);
		Integer changeStatus = getInteger("changeStatus"); // 审核状态
		String auditDesc = getString("auditDesc");
		PmsOperator operator = this.getLoginedOperator();
		String userNo = getString("userNo");

		userAuditFacade.auditRefuse_Status(id, userNo, operator.getLoginName(), operator.getRealName(), "", auditDesc, changeStatus, userAuditRecordStatus.getCurrentStatus());

		super.logSave("操作员：" + operator.getRealName() + "审核商户状态.商户登录名：[" + getString("loginName") + "]，商户编号[" + getString("userNo") + "]，审核结果[不通过]");

		if (sendMail(userNo, auditDesc, MerchantStatusEnum.getEnum(changeStatus).getDesc(), 101)) {
			return operateSuccess();
		} else {
			return operateError("商户状态审核成功，但发邮件失败！");
		}
	}

	public boolean sendMail(String userNo, String reason, String changeStatus, Integer passOrRefuse) {

		// 邮件发送
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(userNo);
		String bindEmail = userInfo.getBindEmail();
		if (StringUtil.isEmpty(bindEmail)) {
			bindEmail = userInfo.getLoginName();
		}

		// 封装邮件发送
		Map<String, Object> paramModel = new HashMap<String, Object>();
		paramModel.put("loginName", userInfo.getLoginName());
		paramModel.put("fullName", userInfo.getRealName());
		paramModel.put("reason", reason);
		paramModel.put("changeStatus", changeStatus);
		paramModel.put("COMPANY_FOR", PublicConfig.COMPANY_FOR);

		// 通过模板生成邮件内容
		String content = "";
		try {
			paramModel.put("phone", EmailConst.PHONE);
			if (passOrRefuse == 100) {
				content = mailBiz.mergeEmailTemplate(EmailConst.MERCHANT_AUDIT_STATUS_PASS, paramModel);
			} else if (passOrRefuse == 101) {
				content = mailBiz.mergeEmailTemplate(EmailConst.MERCHANT_AUDIT_NOTPASS, paramModel);
			}
			mailBiz.sendMail(bindEmail, "商户审核结果邮件", content);
			return true;
		} catch (Exception e) {
			LOG.error("邮件发送异常", e);
			return false;
		}
	}
}
