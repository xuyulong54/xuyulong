package wusc.edu.pay.web.boss.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.user.entity.MemberInfo;
import wusc.edu.pay.facade.user.entity.UserAuditRecordClose;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.UserAuditStatusEnum;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.MemberInfoFacade;
import wusc.edu.pay.facade.user.service.UserAuditFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;
import wusc.edu.pay.web.boss.mail.EmailConst;
import wusc.edu.pay.web.boss.mail.MailBiz;
import wusc.edu.pay.web.boss.mail.SmsBiz;


/**
 * 类描述：会员销户审核操作
 * 
 * @author: huangbin
 * @date： 日期：2013-11-29 时间：上午10:01:33
 * @version 1.0
 */
public class MemberCancelAduitAction extends BossBaseAction {

	private static final long serialVersionUID = -8834046310236744028L;
	private static Log log = LogFactory.getLog(MemberCancelAduitAction.class);
	@Autowired
	private MemberInfoFacade memberInfoFacade; // 会员信息查询
	@Autowired
	private UserQueryFacade userQueryFacade;
	@Autowired
	private UserAuditFacade userAuditFacade;
	@Autowired
	private SmsBiz smsBiz;
	@Autowired
	private MailBiz mailBiz;

	/***
	 * 查询账户实名认证状态
	 * 
	 * @return
	 */
	@Permission("member:cancel:view")
	public String listMemberCancelAudit() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName", getString("loginName"));// 登录名
		paramMap.put("userNo", getString("userNo"));// 用户编号
		paramMap.put("auditStatus", getString("auditStatus"));
		paramMap.put("userType", UserTypeEnum.CUSTOMER.getValue());
		super.pageBean = userAuditFacade.queryUserAuditRecordCloseListPage(getPageParam(), paramMap);
		this.putData("userAuditStatusEnum", UserAuditStatusEnum.toMap());
		// 加载认证状态
		this.pushData(pageBean);
		this.pushData(paramMap);
		super.putEnums();
		return "auditMemberCancelList";
	}

	/***
	 * 会员销户审核
	 * 
	 * @return
	 */
	@Permission("member:cancel:view")
	public String auditMemberCancelUI() {
		Long id = getLong("id");
		UserAuditRecordClose userAuditRecordClose =userAuditFacade.getUserAuditRecordCloseById(id); // 查询会员销户信息
		if(userAuditRecordClose == null){
			return operateError("审核记录为空！");
		}
		this.putData("isView", getString("isView"));
		this.putData("cancel", userAuditRecordClose);
		super.putEnums();
		return "auditMemberCancel";
	}

	/***
	 * 审核会员实名认证
	 * 
	 * @return
	 */
	@Permission("member:cancel:audit")
	public String doAuditMemberCancel() {
		Long id = getLong("id");
		Integer status = Integer.parseInt(getString("auditStatus"));
		String auditDesc = getString("auditDesc"); // 描述信息
		UserAuditRecordClose cancel = userAuditFacade.getUserAuditRecordCloseById(id);
		if (cancel == null) {
			return operateError("实名认证记录为空");
		}
		MemberInfo memberInfo = memberInfoFacade.getMemberByUserNo(cancel.getUserNo());
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(cancel.getUserNo());
		if (memberInfo == null) {
			return operateError("会员信息为空");
		}
		
		Map<String, Object> paramModel = new HashMap<String, Object>();
		
		String smsContent = "";
		if (status == UserAuditStatusEnum.AGREE.getValue()) {
			// 审核通过
			try {
				paramModel.put("status", "成功");
				paramModel.put("auditStatus", "已通过");
				userAuditFacade.auditPass_Close(id, cancel.getApplyOperatorLoginName(), cancel.getApplyOperatorName(), auditDesc, 2);
				super.logEdit("会员销户(审核通过).编号[" + cancel.getUserNo() + "].会员登录名[" + userInfo.getLoginName() + "]");
			}catch(UserBizException e){
				return operateError(e.getMsg() + "异常编码：" + e.getCode());
			} catch (Exception e) {
				e.printStackTrace();
				return operateError("系统异常，请联系管理员");
			}
		} else {
			// 审核不通过
			try {
				paramModel.put("status", "失败");
				paramModel.put("auditStatus", "不通过");
				userAuditFacade.auditRefuse_Close(id, cancel.getApplyOperatorLoginName(), cancel.getApplyOperatorName(), auditDesc);
				super.logEdit("会员销户(审核不通过).编号[" + cancel.getUserNo() + "].会员登录名[" + userInfo.getLoginName() + "]");
			}catch(UserBizException e){
				return operateError(e.getMsg() + "异常编码：" + e.getCode());
			}
		}
		try {
			paramModel.put("phone", EmailConst.PHONE);
			smsContent = smsBiz.mergeSmsTemplate(EmailConst.MEMBER_CELL, paramModel);
		} catch (Exception e) {
			log.error("短信模板解释异常", e);
		}
		if (!StringUtils.isEmpty(userInfo.getBindMobileNo())) {
			smsBiz.sendSms(userInfo.getBindMobileNo(), smsContent);
		}
		if (!StringUtils.isEmpty(userInfo.getBindEmail())){
			mailBiz.sendMail(userInfo.getBindEmail(), "会员注销审核结果", smsContent);
		}
		return operateSuccess();
	}
}
