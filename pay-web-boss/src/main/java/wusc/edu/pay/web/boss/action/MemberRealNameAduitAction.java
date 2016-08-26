package wusc.edu.pay.web.boss.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.user.entity.MemberInfo;
import wusc.edu.pay.facade.user.entity.UserAuditRecordRealName;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.UserAuditStatusEnum;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.service.MemberInfoFacade;
import wusc.edu.pay.facade.user.service.UserAuditFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;
import wusc.edu.pay.web.boss.mail.EmailConst;
import wusc.edu.pay.web.boss.mail.MailBiz;


/**
 * 类描述：会员实名认证审核操作
 * 
 * @author: huangbin
 * @date： 日期：2013-11-29 时间：上午10:01:33
 * @version 1.0
 */
public class MemberRealNameAduitAction extends BossBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8834046310236744028L;
	
	private static Log LOG = LogFactory.getLog(MemberRealNameAduitAction.class);
			
	@Autowired
	private UserAuditFacade userAuditFacade;// 用户审核
	@Autowired
	private UserQueryFacade userQueryFacade;
	@Autowired
	private MemberInfoFacade memberInfoFacade;
	@Autowired
	private MailBiz mailBiz;

	/***
	 * 查询账户实名认证状态
	 * 
	 * @return
	 */
	@Permission("member:audit:view")
	public String listMemberAudit() {

		String beginDate = getString("beginDate");
		String endDate = getString("endDate");
		String auditStatus = getString("auditStatus"); // 实名认证
		// 对时间进行校验
		if (beginDate != null && endDate != null) {
			// 取得两个日期之间的日数
			long a = DateUtils.daysBetween(DateUtils.toSqlTimestamp(beginDate), DateUtils.toSqlTimestamp(endDate));

			if (a < 0) {
				return this.operateError("开始时间不能大于结束时间");
			}
		} else if ((beginDate != null && endDate == null) || (beginDate == null && endDate != null)) {
			return this.operateError("不能只输入一个时间查询");
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName", getString("loginName"));//
		paramMap.put("userNo", getString("userNo"));//
		paramMap.put("realName", getString("realName"));// 真实姓名
		paramMap.put("cardNo", getString("cardNo"));// 身份证号
		paramMap.put("beginDate", beginDate);// 注册开始时间
		paramMap.put("endDate", endDate);// 注册结束时间
		paramMap.put("auditStatus", auditStatus);// 状态

		paramMap.put("userType", UserTypeEnum.CUSTOMER.getValue());// 只查在线会员的记录
		super.pageBean = userAuditFacade.queryUserAuditRecordRealNameListPage(getPageParam(), paramMap);
		// 加载认证状态
		this.pushData(pageBean);
		this.pushData(paramMap);
		putData("UserAuditStatusEnum", UserAuditStatusEnum.toMap());
		return "auditMemberList";
	}

	/***
	 * 审核会员实名认证
	 * 
	 * @return
	 */
	@Permission("member:audit:view")
	public String auditMemberUI() {
		Long id = getLong("id");
		UserAuditRecordRealName auth = userAuditFacade.getUserAuditRecordRealNameById(id);
		if (auth == null) {
			return operateError("申请记录信息为空");
		}
		MemberInfo memberInfo = memberInfoFacade.getMemberByUserNo(auth.getUserNo());
		if (memberInfo == null)
			return operateError("会员信息为空");

		this.putData("memberInfo", memberInfo);
		this.putData("isView", getString("isView"));
		this.pushData(auth);
		putData("UserAuditStatusEnum", UserAuditStatusEnum.toMap());
		return "auditMember";
	}

	/***
	 * 审核会员实名认证
	 * 
	 * @return
	 */
	@Permission("member:audit:audit")
	public String doAuditMember() {
		Long id = getLong("id");
		String status = getString("status"); // 状态
		String auditDesc = getString("auditDesc"); // 审核意见
		String userNo = getString("userNo");
		if ("".equals(auditDesc) || auditDesc == null) {
			return operateError("请填写审核意见.");
		}
		Integer intStatus = Integer.parseInt(status); // 操作状态

		String auditResult = "";

		if (intStatus == 100) { // 实名认证审核通过
			// 1.修改账户表状态为激活 100
			// 2.替换会员表的真实姓名和身份证号
			userAuditFacade.auditPass_RealName(id, getLoginedOperator().getLoginName(), getLoginedOperator().getRealName(), auditDesc);

			auditResult = "审核通过";
		} else {
			// 不通过的时候发邮件和短信通知会员，TODO
			userAuditFacade.auditRefuse_RealName(id, getLoginedOperator().getLoginName(), getLoginedOperator().getRealName(), auditDesc);
			auditResult = "审核未通过";
		}
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(userNo);

		String bindEmail = userInfo.getBindEmail();
		if (StringUtil.isNotBlank(bindEmail)) { 
			// 邮箱不为空，发邮件
			Map<String, Object> paramModel = new HashMap<String, Object>();
			paramModel.put("loginName", userInfo.getLoginName());
			paramModel.put("auditResult", auditResult);
			paramModel.put("COMPANY_FOR", PublicConfig.COMPANY_FOR);
			
			// 通过模板生成邮件内容
			String content = "";
			try {
				paramModel.put("phone", EmailConst.PHONE);
				content = mailBiz.mergeEmailTemplate(EmailConst.MEMBER_REALNAME_AUDIT, paramModel);
			} catch (Exception e) {
				LOG.error("邮件模板解释异常", e);
			}
			mailBiz.sendMail(bindEmail, "会员实名认证审核结果", content);
		}
		super.logEdit("操作员" + getLoginedOperator().getRealName() + "审核会员实名认证.真实姓名[" + userInfo.getRealName() + "].会员编号[" + userNo + "].审核结果[" + auditResult + "]");

		return operateSuccess();
	}
}
