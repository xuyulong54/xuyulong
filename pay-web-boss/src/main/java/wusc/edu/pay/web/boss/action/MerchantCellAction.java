package wusc.edu.pay.web.boss.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.user.entity.UserAuditRecordClose;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.UserAuditStatusEnum;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.UserAuditFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;
import wusc.edu.pay.web.boss.mail.EmailConst;
import wusc.edu.pay.web.boss.mail.MailBiz;
import wusc.edu.pay.web.boss.mail.SmsBiz;
import wusc.edu.pay.web.permission.entity.PmsOperator;


/**
 * 类描述：商户销户审核Action
 * 
 * @author: huangbin
 * @date： 日期：2013-12-2 时间：下午5:04:59
 * @version 1.0
 */
public class MerchantCellAction extends BossBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2411915635587183611L;
	private static Log log = LogFactory.getLog(MerchantCellAction.class);
	@Autowired
	private UserAuditFacade userAuditFacade;
	@Autowired
	private MailBiz mailBiz;
	@Autowired
	private UserQueryFacade userQueryFacade;
	@Autowired
	private SmsBiz smsBiz;

	/***
	 * 列表方法
	 * 
	 * @return
	 */
	public String list() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("auditStatus", getString("auditStatus"));
		paramMap.put("loginName", getString("loginName"));
		paramMap.put("userNo", getString("userNo")); // 商户编号
		paramMap.put("userType", UserTypeEnum.MERCHANT.getValue());
		super.pageBean = userAuditFacade.queryUserAuditRecordCloseListPage(getPageParam(), paramMap);

		this.putData("userAuditStatusEnum", UserAuditStatusEnum.toList());
		this.putData("waitEnum", UserAuditStatusEnum.WAIT.getValue());
		// 加载认证状态
		this.pushData(pageBean);
		this.pushData(paramMap);
		return "merchantCellList";
	}

	/***
	 * 查看详情
	 * 
	 * @return
	 */
	@Permission("merchant:cell:view")
	public String view() {
		Long id = getLong("id");
		String isView = getString("isView");
		UserAuditRecordClose colse = userAuditFacade.getUserAuditRecordCloseById(id);
		this.putData("userAuditStatusEnum", UserAuditStatusEnum.toList());

		this.pushData(colse);
		this.putData("isView", isView);
		return "merchantCellView";
	}

	/***
	 * 审核操作
	 * 
	 * @return
	 */
	@Permission("merchant:cell:audit")
	public String audit() {
		Long id = getLong("id");
		Integer status = getInteger("status"); // 审核状态
		String auditDesc = getString("auditDesc");
		Integer isLogin = getInteger("isLogin"); // 区分商户是否可以登陆，1-可登陆，2-不可登陆
		String userNo = getString("userNo");
		if (StringUtil.isEmpty(userNo))
			return operateError("商户基本信息为空");
		PmsOperator operator = this.getLoginedOperator();
		try {
			// 封装邮件发送
			Map<String, Object> paramModel = new HashMap<String, Object>();
			UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(userNo);
			String bindEmail = userInfo.getBindEmail();
			String bindMobileNo = userInfo.getBindMobileNo();
			if (status == UserAuditStatusEnum.AGREE.getValue()) {
				String trxTypeLimitFilter = "";
				if (isLogin != null && isLogin == 1) { // 可以登陆，获取商户所保留的功能，更新商户业务设置表
					String[] trxTypeLimitFilters = getHttpRequest().getParameterValues("trxTypeLimitFilter");
					if (trxTypeLimitFilters != null) {
						for (int i = 0; i < trxTypeLimitFilters.length; i++) {
							if ("".equals(trxTypeLimitFilter) || trxTypeLimitFilter == null) {
								trxTypeLimitFilter = trxTypeLimitFilters[i];
							} else {
								trxTypeLimitFilter += "," + trxTypeLimitFilters[i];
							}
						}
					}
				}
				paramModel.put("status", "成功");
				paramModel.put("auditStatus", "已通过");
				// 通过模板生成邮件内容
				String content = "";
				try {
					paramModel.put("phone", EmailConst.PHONE);
					content = mailBiz.mergeEmailTemplate(EmailConst.MERCHANT_AUDIT_CELL_MESSAGE, paramModel);
				} catch (Exception e) {
					log.error("邮件模板解释异常", e);
				}
				if (!StringUtils.isEmpty(bindMobileNo)) {
					smsBiz.sendSms(bindMobileNo, content);
				}
				if (!StringUtils.isEmpty(bindEmail)) {
					mailBiz.sendMail(bindEmail, "商户注销审核结果", content);
				}

				userAuditFacade.auditPass_Close(id, operator.getLoginName(), operator.getRealName(), auditDesc, isLogin);
				this.logSave("销户审核通过，商户编号："+userNo);
			} else if (status == UserAuditStatusEnum.REFUSE.getValue()) {
				// 不通过
				paramModel.put("status", "失败");
				paramModel.put("auditStatus", "不通过");
				// 通过模板生成邮件内容
				String content = "";
				try {
					paramModel.put("phone", EmailConst.PHONE);
					content = mailBiz.mergeEmailTemplate(EmailConst.MERCHANT_AUDIT_CELL_MESSAGE, paramModel);
				} catch (Exception e) {
					log.error("邮件模板解释异常", e);
				}
				if (!StringUtils.isEmpty(bindMobileNo)) {
					smsBiz.sendSms(bindMobileNo, content);
				}
				if (!StringUtils.isEmpty(bindEmail)) {
					mailBiz.sendMail(bindEmail, "商户注销审核结果", content);
				}
				userAuditFacade.auditRefuse_Close(id, operator.getLoginName(), operator.getRealName(), auditDesc);
				this.logSave("销户审核不通过，商户编号："+userNo);
			}
		} catch (UserBizException e) {
			this.logSaveError("商户销户审核失败，商户编号："+userNo);
			return operateError("商户销户记录不存在！");
		} catch (Exception e) {
			this.logSaveError("商户销户审核失败，商户编号："+userNo);
			log.error("商户销户审核失败", e);
		}
		return operateSuccess();
	}
}
