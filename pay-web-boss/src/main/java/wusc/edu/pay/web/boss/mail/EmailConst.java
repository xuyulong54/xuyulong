package wusc.edu.pay.web.boss.mail;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.utils.ResourceUtils;


/**
 * 
 * @描述: 邮件功能常量配置 .
 * @作者: WuShuicheng.
 * @创建: 2014-9-16,下午10:57:23
 * @版本: V1.0
 * 
 */
public class EmailConst {
	/**
	 * logger.
	 */
	private static final Log log = LogFactory.getLog(EmailConst.class);

	public static Map<String, String> PUBLIC_SYSTEM = ResourceUtils.getResource("public_system").getMap();
	/** 会员实名验证 **/
	public static String MEMBER_REALNAME_AUDIT = "template/email/MemberRealNameAudit.vm";
	/** 会员销户 **/
	public static String MEMBER_CELL = "template/email/MemberCancel.vm";
	/** 会员状态审核通过 **/
	public static String MEMBER_AUDIT_STATUS_PASS = "template/email/MemberAuditStatusPass.vm";
	/** 商户添加成功 **/
	public static String MERCHANT_ADD_SUCCESS = "template/email/MerchantAddSuccess.vm";
	/** 商户审核通过 **/
	public static String MERCHANT_AUDIT_PASS = "template/email/MerchantAuditPass.vm";
	/** 商户审核不通过 **/
	public static String MERCHANT_AUDIT_NOTPASS = "template/email/MerchantAuditNotPass.vm";
	/** 商户状态变更审核通过 **/
	public static String MERCHANT_AUDIT_STATUS_PASS = "template/email/MerchantAuditStatusPass.vm";
	/** 重置会员登录密码 **/
	public static String RESET_MEMBER_LOGIN_PASSWORD = "template/email/ResetMemberLoginPassword.vm";
	/** 重置商户登录密码 **/
	public static String RESET_MERCHANT_LOGIN_PASSWORD = "template/email/ResetMerchantLoginPassword.vm";
	/** 商户销户审核(邮件) **/
	public static String MERCHANT_AUDIT_CELL = "template/email/MerchantCell.vm";
	/** 商户销户审核(短信) **/
	public static String MERCHANT_AUDIT_CELL_MESSAGE = "template/email/MerchantCellMessage.vm";

	/** 代理商审核通过 **/
	public static String AGENT_AUDIT_PASS = "template/email/AgentAuditPass.vm";
	/** 代理商审核不通过 **/
	public static String AGENT_AUDIT_NOTPASS = "template/email/AgentAuditNotPass.vm";
	/** 代理商审核通过，发送账户密码邮件 **/
	public static String AGENT_AUDIT_PASS_SENDPWD = "template/email/AgentAuditPassSendpwd.vm";
	/** 重置代理商登录密码 **/
	public static String RESET_AGENT_LOGIN_PASSWORD = "template/email/ResetAgentLoginPassword.vm";

	/** 代理商商户审核通过 ,通知代理商 **/
	public static String AGENT_MERCHANT_AUDIT_PASS = "template/email/AgentMerchantAuditPass.vm";
	/** 代理商商户审核不通过,通知代理商 **/
	public static String AGENT_MERCHANT_AUDIT_NOTPASS = "template/email/AgentMerchantAuditNotPass.vm";
	/** 代理商商户审核通过，发送账户密码邮件 **/
	public static String AGENT_MERCHANT_AUDIT_PASS_SENDPWD = "template/email/AgentMerchantAuditPassSendpwd.vm";
	/** 代理商商户审核不通过,通知商户 **/
	public static String AGENT_MERCHANT_AUDIT_NOTPASS_TO_MERCHANT = "template/email/AgentMerchantAuditNotPassToMerchant.vm";

	public static String PHONE = PUBLIC_SYSTEM.get("service.phone");
	public static String BOSS_SYSTEM_URL = PUBLIC_SYSTEM.get("system.web.boss.url");
	public static String PORTAL_SYSTEM_URL = PUBLIC_SYSTEM.get("system.web.portal.url");

	/**
	 * 只加载一次.
	 */
	static {
		try {
			log.info("=== load email.properties and init sys param");
			InputStream proFile = Thread.currentThread().getContextClassLoader().getResourceAsStream("email.properties");
			Properties props = new Properties();
			props.load(proFile);
			init(props);
		} catch (Exception e) {
			log.error("=== load and init email.properties exception:" + e);
		}
	}

	/**
	 * 根据配置文件初始化静态变量值.
	 * 
	 * @param props
	 */
	private static void init(Properties props) {

		String member_realname_audit = props.getProperty("member.realname.audit");
		if (StringUtils.isNotBlank(member_realname_audit)) {
			MEMBER_REALNAME_AUDIT = member_realname_audit;
		}

		String merchant_add_success = props.getProperty("merchant.add.success");
		if (StringUtils.isNotBlank(merchant_add_success)) {
			MERCHANT_ADD_SUCCESS = merchant_add_success;
		}

		String merchant_audit_notpass = props.getProperty("merchant.audit.notpass");
		if (StringUtils.isNotBlank(merchant_audit_notpass)) {
			MERCHANT_AUDIT_NOTPASS = merchant_audit_notpass;
		}

		String merchant_audit_pass = props.getProperty("merchant.audit.pass");
		if (StringUtils.isNotBlank(merchant_audit_pass)) {
			MERCHANT_AUDIT_PASS = merchant_audit_pass;
		}

		String reset_member_login_password = props.getProperty("reset.member.login.password");
		if (StringUtils.isNotBlank(reset_member_login_password)) {
			RESET_MEMBER_LOGIN_PASSWORD = reset_member_login_password;
		}

		String reset_merchant_login_password = props.getProperty("reset.merchant.login.password");
		if (StringUtils.isNotBlank(reset_merchant_login_password)) {
			RESET_MERCHANT_LOGIN_PASSWORD = reset_merchant_login_password;
		}
		String agent_audit_pass = props.getProperty("agent.audit.pass");
		if (StringUtils.isNotBlank(agent_audit_pass)) {
			AGENT_AUDIT_PASS = agent_audit_pass;
		}
		String agent_audit_notpass = props.getProperty("agent.audit.notpass");
		if (StringUtils.isNotBlank(agent_audit_notpass)) {
			AGENT_AUDIT_NOTPASS = agent_audit_notpass;
		}
		String agent_audit_pass_sendpwd = props.getProperty("agent.audit.pass.sendpwd");
		if (StringUtils.isNotBlank(agent_audit_pass_sendpwd)) {
			AGENT_AUDIT_PASS_SENDPWD = agent_audit_pass_sendpwd;
		}

		String reset_agent_login_password = props.getProperty("reset.agent.login.password");
		if (StringUtils.isNotBlank(reset_agent_login_password)) {
			RESET_AGENT_LOGIN_PASSWORD = reset_agent_login_password;
		}

		String agent_merchant_audit_pass = props.getProperty("agent.merchant.audit.pass");
		if (StringUtils.isNotBlank(agent_merchant_audit_pass)) {
			AGENT_MERCHANT_AUDIT_PASS = agent_merchant_audit_pass;
		}

		String agent_merchant_audit_notpass = props.getProperty("agent.merchant.audit.notpass");
		if (StringUtils.isNotBlank(agent_merchant_audit_notpass)) {
			AGENT_MERCHANT_AUDIT_NOTPASS = agent_merchant_audit_notpass;
		}

		String agent_merchant_audit_pass_sendpwd = props.getProperty("agent.merchant.audit.pass.sendpwd");
		if (StringUtils.isNotBlank(agent_merchant_audit_pass_sendpwd)) {
			AGENT_MERCHANT_AUDIT_PASS_SENDPWD = agent_merchant_audit_pass_sendpwd;
		}

		String agent_merchant_audit_notpass_to_merchant = props.getProperty("agent.merchant.audit.notpass.to.merchant");
		if (StringUtils.isNotBlank(agent_merchant_audit_notpass_to_merchant)) {
			AGENT_MERCHANT_AUDIT_NOTPASS_TO_MERCHANT = agent_merchant_audit_notpass_to_merchant;
		}
	}
}
