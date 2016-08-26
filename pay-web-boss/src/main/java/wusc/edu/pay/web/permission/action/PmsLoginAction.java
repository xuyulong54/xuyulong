package wusc.edu.pay.web.permission.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.enums.OperatorStatusEnum;
import wusc.edu.pay.common.web.constant.PermissionConstant;
import wusc.edu.pay.web.permission.base.PermissionBaseAction;
import wusc.edu.pay.web.permission.biz.PmsActionBiz;
import wusc.edu.pay.web.permission.biz.PmsMenuBiz;
import wusc.edu.pay.web.permission.biz.PmsOperatorBiz;
import wusc.edu.pay.web.permission.biz.PmsRoleBiz;
import wusc.edu.pay.web.permission.entity.PmsAction;
import wusc.edu.pay.web.permission.entity.PmsOperator;
import wusc.edu.pay.web.permission.exception.PermissionException;

import com.google.code.kaptcha.Constants;

/**
 * 
 * <ul>
 * <li>Title:用户登录</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2013-11-17
 */
@Scope("prototype")
public class PmsLoginAction extends PermissionBaseAction {
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(PmsLoginAction.class);

	@Autowired
	private PmsActionBiz pmsActionBiz;
	@Autowired
	private PmsRoleBiz pmsRoleBiz;
	@Autowired
	private PmsOperatorBiz pmsOperatorBiz;
	@Autowired
	private PmsMenuBiz pmsMenuBiz;

	/**
	 * 进入登录页面.
	 * 
	 * @return
	 */
	public String loginPage() {
		return "login";
	}

	/**
	 * 登录验证Action
	 * 
	 * @return
	 * @throws Exception
	 */
	public String operatorLogin() {
		try {
			// TODO 测试使用，暂时注释验证码校验
			// 如果会话还存在的情况下则不检验验证码，否则会遇到刷新时跳到登录页面的问题
			// 明文用户名
			String loginName = getString("loginName");

			if (StringUtils.isBlank(loginName)) {
				super.putData("loginNameMsg", "用户名不能为空");
				return "input";
			}
			
			super.putData("loginName", loginName);
			
			Object operatorSession = super.getSessionMap().get(PermissionConstant.OPERATOR_SESSION_KEY);
			if (operatorSession == null) {
				// 校验验证码是否正确
				String code = getString("code");
				String kaptchaCode = (String) super.getSessionMap().get(Constants.KAPTCHA_SESSION_KEY);
				if (StringUtils.isBlank(code)) {
					super.putData("codeMsg", "请输入验证码");
					return "input";
				} else if (!code.equalsIgnoreCase(kaptchaCode)) {
					// 忽略大小写的对比
					super.putData("codeMsg", "验证码不正确");
					return "input";
				}
			}

			// 定义一个result来记录Action的定向
			// String result = "input";

			PmsOperator operator = pmsOperatorBiz.findOperatorByLoginName(loginName);
			if (operator == null) {
				log.warn("==> 登录名[" + loginName + "]不存在");
				super.putData("loginNameMsg", "用户名或密码不正确");
				return "input";
			}

			if (operator.getStatus().intValue() == OperatorStatusEnum.INACTIVE.getValue()) {
				log.warn("== 帐号【" + loginName + "】已被冻结");
				super.putData("loginNameMsg", "该帐号已被冻结");
				return "input";
			}

			String pwd = getString("loginPwd");
			if (StringUtils.isBlank(pwd)) {
				super.putData("loginPwdMsg", "密码不能为空");
				return "input";
			}

			// 加密明文密码，验证密码
			if (operator.getLoginPwd().equals(DigestUtils.sha1Hex(pwd))) {
				// 密码正确
				
				// 当前在线人数
				int currentLoginCount = 0;
				// TODO
				if (currentLoginCount > PermissionConstant.WEB_ONLINE_LIMIT) {
					log.info("==>系统繁忙，已超最大在线用户数限制【" + PermissionConstant.WEB_ONLINE_LIMIT + "】");
					super.putData("errorMsg", "系统繁忙，已超最在线用户数限制");
					return "input";
				}

				// 用户信息，包括登录信息和权限
				super.getSessionMap().put(PermissionConstant.OPERATOR_SESSION_KEY, operator);
				super.getSessionMap().put(PermissionConstant.ACTIONS_SESSION_KEY, getActions(operator));
				
				super.putData("loginName", loginName);
				// 设置操作员的真实姓名
				super.putData("realName", operator.getRealName());
				super.putData("lastLoginTime", operator.getLastLoginTime());
				
				try {
					// 获取用户的菜单权限
					super.putData("tree", buildOperatorPermissionMenu(operator));

					// 更新登录数据
					operator.setLastLoginTime(new Date());
					operator.setPwdErrorCount(0); // 错误次数设为0
					pmsOperatorBiz.update(operator);

				} catch (Exception e) {
					log.error("==>登录异常:", e);
					super.logLoginError("登录出错", operator);
					super.putData("errorMsg", "登录出错");
					return "input";
				}
				// 记录系统操作日志
				super.logLogin("登录系统");
				// 判断用户是否重置了密码，如果重置，弹出强制修改密码页面； TODO
				super.putData("isChangePwd", operator.getIsChangedPwd());

				return "main";

			} else {
				// 密码错误
				log.warn("==>密码错误");
				// 错误次数加1
				Integer pwdErrorCount = operator.getPwdErrorCount();
				if (pwdErrorCount == null){
					pwdErrorCount = 0;
				}
				operator.setPwdErrorCount(pwdErrorCount + 1);
				operator.setPwdErrorTime(new Date()); // 设为当前时间
				String msg = "";
				if (operator.getPwdErrorCount().intValue() >= PermissionConstant.WEB_PWD_INPUT_ERROR_LIMIT) {
					// 超5次就冻结帐号
					operator.setStatus(OperatorStatusEnum.INACTIVE.getValue());
					msg += "<br/>密码已连续输错【" + PermissionConstant.WEB_PWD_INPUT_ERROR_LIMIT + "】次，帐号已被冻结";
					super.logLoginError("登录出错,密码已连续输错【" + PermissionConstant.WEB_PWD_INPUT_ERROR_LIMIT + "】次，帐号已被冻结", operator);
				} else {
					msg += "<br/>密码错误，再输错【" + (PermissionConstant.WEB_PWD_INPUT_ERROR_LIMIT - operator.getPwdErrorCount().intValue()) + "】次将冻结帐号";
					super.logLoginError("登录出错,密码输入错误.剩余[" + (PermissionConstant.WEB_PWD_INPUT_ERROR_LIMIT - operator.getPwdErrorCount()) + "]次机会。", operator);
				}

				pmsOperatorBiz.update(operator);
				super.putData("loginPwdMsg", msg);
				return "input";
			}

		} catch (RuntimeException e) {
			log.error("login exception:", e);
			super.putData("errorMsg", "登录出错");
			return "input";
		} catch (Exception e) {
			log.error("login exception:", e);
			super.putData("errorMsg", "登录出错");
			return "input";
		}
	}

	/**
	 * 跳转到退出确认页面.
	 * 
	 * @return LogOutConfirm.
	 */
	public String logoutConfirm() {
		log.info("== logoutConfirm");
		return "logoutConfirm";
	}

	/**
	 * 退出登录 .
	 * @return logout.
	 */
	public String logout() {
		super.getSessionMap().clear();
		log.info("==>logout,clear session");
		return "logout";
	}

	/**
	 * 跳转到登录超时确认页面.
	 * 
	 * @return timeoutConfirm.
	 */
	public String timeoutConfirm() {
		log.info("==>timeoutConfirm");
		return "timeoutConfirm";
	}

	/**
	 * 得到以权限（Action）为key，以PmsAction为Value的指定用户的权限Map
	 * 
	 * @param pmsOperator
	 * @return
	 */
	private List<String> getActions(PmsOperator pmsOperator) {
		// 根据用户ID得到该用户的所有角色拼成的字符串
		String roleIds = pmsRoleBiz.getRoleIdsByOperatorId(pmsOperator.getId());
		// 根据角色ID字符串得到该用户的所有权限拼成的字符串
		String actionIds = "";
		if (StringUtils.isNotBlank(roleIds)) {
			actionIds = pmsActionBiz.getActionIdsByRoleIds(roleIds);
		}
		// 根据权限ID字符串得到权限列表
		List<PmsAction> pmsActionList = new ArrayList<PmsAction>();
		if (!"".equals(actionIds)) {
			pmsActionList = pmsActionBiz.findActionsByIdStr(actionIds);
		}
		// 将权限放入HashMap中，其中key为权限（action），值为权限对象
		List<String> actionList = new ArrayList<String>();
		for (PmsAction pmsAction : pmsActionList) {
			actionList.add(pmsAction.getAction());
		}
		return actionList;
	}

	/**
	 * 获取用户的菜单权限
	 * 
	 * @param pmsOperator
	 * @return
	 * @throws Exception
	 */
	private String buildOperatorPermissionMenu(PmsOperator pmsOperator) throws PermissionException {
		// 根据用户ID得到该用户的所有角色拼成的字符串
		String roleIds = pmsRoleBiz.getRoleIdsByOperatorId(pmsOperator.getId());
		if (StringUtils.isBlank(roleIds)) {
			log.error("==>用户[" + pmsOperator.getLoginName() + "]没有配置对应的权限角色");
			throw new RuntimeException("该帐号已被取消所有系统权限");
		}
		// 根据操作员拥有的角色ID,构建管理后台的树形权限功能菜单
		return pmsMenuBiz.buildPermissionTree(roleIds);
	}

}
