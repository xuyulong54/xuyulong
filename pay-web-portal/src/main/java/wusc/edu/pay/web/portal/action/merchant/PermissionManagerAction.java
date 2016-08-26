package wusc.edu.pay.web.portal.action.merchant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.enums.OperatorStatusEnum;
import wusc.edu.pay.common.utils.UUIDUitl;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.user.entity.MerchantAction;
import wusc.edu.pay.facade.user.entity.MerchantRole;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.entity.UserOperatorLog;
import wusc.edu.pay.facade.user.enums.UserOperatorTypeEnum;
import wusc.edu.pay.facade.user.enums.UserStatusEnum;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.MerchantActionFacade;
import wusc.edu.pay.facade.user.service.MerchantPermissionFacade;
import wusc.edu.pay.facade.user.service.MerchantRoleActionFacade;
import wusc.edu.pay.facade.user.service.MerchantRoleFacade;
import wusc.edu.pay.facade.user.service.MerchantRoleOperatorFacade;
import wusc.edu.pay.facade.user.service.UserOperatorFacade;
import wusc.edu.pay.facade.user.service.UserOperatorLogFacade;
import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.exceptions.PortalMerchantException;


/**
 * 权限管理模块的Action类，包括权限管理、角色管理、操作员管理,日志管理
 * 
 * @author liliqiong
 * @date 2013-8-26
 * @version 1.0
 */
@Scope("prototype")
public class PermissionManagerAction extends BaseAction {
	private static final long serialVersionUID = -5814770893743608172L;
	/**
	 * 注入权限管理模块业务层接口.
	 */
	@Autowired
	private MerchantPermissionFacade merchantPermissionFacade;
	@Autowired
	private MerchantActionFacade merchantActionFacade;
	@Autowired
	private MerchantRoleFacade merchantRoleFacade;
	@Autowired
	private UserOperatorFacade userOperatorFacade;
	@Autowired
	private MerchantRoleOperatorFacade merchantRoleOperatorFacade;
	@Autowired
	private MerchantRoleActionFacade merchantRoleActionFacade;
	@Autowired
	private UserOperatorLogFacade userOperatorLogFacade;

	// /////////////////////////////////////权限相关///////////////////////////////
	/**
	 * 转到添加权限页面 .
	 * 
	 * @return "actionInput" .
	 */
	@Permission("pms:action:add")
	public String addMerchantActionUI() {
		return "addMerchantActionUI";
	}

	/**
	 * 添加权限页面 .
	 * 
	 * @return
	 */
	@Permission("pms:action:add")
	public String addMerchantAction() {
		String actionName = getString("actionName");// 权限名
		String action = getString("action");// 权限
		String desc = getString("desc");// 描述
		MerchantAction merchantAction = new MerchantAction();
		merchantAction.setActionName(actionName);
		merchantAction.setAction(action);
		merchantAction.setRemark(desc);
		merchantPermissionFacade.create(merchantAction);
		putData("msg", "添加权限成功");
		return "addMerchantAction";
	}

	/**
	 * 转到修改权限页面 .
	 * 
	 * @return
	 */
	@Permission("pms:action:edit")
	public String editMerchantActionUI() {
		MerchantAction merchantAction = merchantActionFacade
				.getMerchantActionById(getLong("id"));
		if (ValidateUtils.isEmpty(merchantAction)) {
			throw new PortalMerchantException(
					PortalMerchantException.PARAM_IS_ERROR, "参数有误");
		}
		pushData(merchantAction);
		return "editMerchantActionUI";
	}

	/**
	 * 修改权限.
	 * 
	 * @return
	 */
	@Permission("pms:action:edit")
	public String editMerchantAction() {
		Long id = getLong("id");
		String actionName = getString("actionName");// 权限名
		String action = getString("action");// 权限
		String desc = getString("desc");// 描述
		MerchantAction merchantAction = merchantActionFacade
				.getMerchantActionById(id);
		if (ValidateUtils.isEmpty(merchantAction)) {
			throw new PortalMerchantException(
					PortalMerchantException.PARAM_IS_ERROR, "参数有误");
		}
		merchantAction.setActionName(actionName);
		merchantAction.setAction(action);
		merchantAction.setRemark(desc);
		merchantPermissionFacade.update(merchantAction);
		putData("msg", "修改权限成功");
		return "editMerchantAction";
	}

	/**
	 * 查询权限
	 * 
	 * @return
	 */
	@Permission("pms:action:view")
	public String listMerchantAction() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("actionName", getString("actionName"));
		pageBean = merchantActionFacade.listPageForMerchantAction(
				getPageParam(), map);
		pushData(pageBean);
		pushData(map);
		putData("msg", getString("msg"));
		return "listMerchantAction";

	}

	/**
	 * 删除权限
	 * 
	 * @return
	 */
	@Permission("pms:action:delete")
	public String deleteMerchantAction() {
		Long id = getLong("id");
		MerchantAction merchantAction = merchantActionFacade
				.getMerchantActionById(id);
		if (ValidateUtils.isEmpty(merchantAction)) {
			throw new PortalMerchantException(
					PortalMerchantException.PARAM_IS_ERROR, "参数有误");
		}
		merchantPermissionFacade.deleteMerchantActionById(id);
		return "deleteMerchantAction";
	}

	// /////////////////////////////////////角色相关///////////////////////////////
	/**
	 * 转到添加角色页面
	 * 
	 * @return
	 */
	@Permission("Pms:Role:Operation")
	public String addMerchantRoleUI() {
		// 获取权限列表并设置到范围对象中
		initActionList();
		return "addMerchantRoleUI";
	}

	/**
	 * 添加角色
	 * 
	 * @return
	 */
	@Permission("Pms:Role:Operation")
	public String addMerchantRole() {
		String roleName = getString("roleName");// 角色名
		String desc = getString("desc");// 描述
		String tradePwd = super.getPwd("tradePwd");// 交易密码
		String actionStr = getSelectActions();// 权限

		// 1、验证角色参数密码是否有效
		Map<String, String> msgMap = validateRoleParams(roleName, desc,
				tradePwd);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL,
					String.format("添加角色名为%s。：%s", roleName, msgMap.get("msg")));
			putData("roleName", roleName);
			putData("desc", desc);
			initActionList();
			putData("owenedActionIds", actionStr);
			pushData(msgMap);
			return "addMerchantRoleUI";
		}

		// 2、 设置角色基本信息
		MerchantRole merchantRole = new MerchantRole();
		merchantRole.setRoleName(roleName);
		merchantRole.setRemark(desc);
		merchantRole.setUserNo(getCurrentUserInfo().getUserNo());

		// 3、 添加角色
		try {
			merchantPermissionFacade.saveMerchantRoleAndMerchantRelateAction(
					merchantRole, actionStr);
			putData("msg", "角色添加成功");
		} catch (UserBizException e) {
			// 记录操作日志
			this.addUserLog(OpeStatusEnum.FAIL,
					String.format("添加角色名为%s。原因：%s", roleName, e.getMsg()));
		}

		// 4、记录操作日志
		this.addUserLog(OpeStatusEnum.SUCCESS,
				String.format("添加角色名为%s。", roleName));

		return "addMerchantRole";
	}

	/**
	 * 转到修改角色页面
	 * 
	 * @return
	 */
	@Permission("Pms:Role:Operation")
	public String editMerchantRoleUI() {
		Long roleId = getLong("id");
		MerchantRole merchantRole = merchantRoleFacade.getById(roleId);
		if (ValidateUtils.isEmpty(merchantRole)) {
			throw new PortalMerchantException(
					PortalMerchantException.PARAM_IS_ERROR, "参数有误");
		}
		if (!merchantRole.getUserNo().equals(getCurrentUserInfo().getUserNo())) {
			throw new PortalMerchantException(
					PortalMerchantException.PARAM_IS_ERROR, "访角色不属于当前商户");
		}
		// 获取全部权限列表并设置到范围对象中
		initActionList();

		// 获取角色对象
		pushData(merchantRole);

		// 获取该角色对应的权限字符串
		putData("owenedActionIds",
				merchantRoleActionFacade.getMRActionStrByRoleId(roleId));

		return "editMerchantRoleUI";
	}

	/**
	 * 修改角色
	 * 
	 * @return
	 */
	@Permission("Pms:Role:Operation")
	public String editMerchantRole() {
		Long id = getLong("id");
		String roleName = getString("roleName");// 角色名
		String desc = getString("desc");// 描述
		String tradePwd = super.getPwd("tradePwd");// 支付密码
		String actionStr = getSelectActions(); // 获取页面提交的功能权限

		// 1、判断参数是否有效
		MerchantRole merchantRole = merchantRoleFacade.getById(id);
		if (ValidateUtils.isEmpty(merchantRole)) {
			throw new PortalMerchantException(
					PortalMerchantException.PARAM_IS_ERROR, "参数有误");
		}
		if (!merchantRole.getUserNo().equals(getCurrentUserInfo().getUserNo())) {
			throw new PortalMerchantException(
					PortalMerchantException.PARAM_IS_ERROR, "访问角色不属于当前商户");
		}

		// 2、验证角色参数密码是否有效
		Map<String, String> msgMap = validateRoleParams(roleName, desc,
				tradePwd);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL,
					String.format("修改角色信息，错误：%s。", msgMap.get("msg")));
			initActionList();
			putData("id", id);
			putData("roleName", roleName);
			putData("desc", desc);
			putData("owenedActionIds", actionStr);
			putData("msg", msgMap.get("msg"));
			return "editMerchantRoleUI";
		}

		// 3、重新设置参数值
		merchantRole.setRoleName(roleName);
		merchantRole.setRemark(desc);

		// 4、执行修改操作
		try {
			merchantPermissionFacade.updateMerchantRoleAndMerchantRelateAction(
					merchantRole, actionStr);
			putData("msg", "修改角色成功");
		} catch (UserBizException e) {
			// 记录操作日志
			this.addUserLog(OpeStatusEnum.FAIL,
					String.format("修改角色信息，错误：%s。", e.getMsg()));
		}

		// 5、记录操作日志
		this.addUserLog(OpeStatusEnum.SUCCESS, "修改角色信息。");

		return "editMerchantRole";
	}

	/**
	 * 验证是否可以执行权限操作
	 * 
	 * @param roleName
	 * @param desc
	 * @param tradePwd
	 * @return
	 */
	private Map<String, String> validateRoleParams(String roleName,
			String desc, String tradePwd) {
		Map<String, String> msgMap = new HashMap<String, String>();
		if (ValidateUtils.isEmpty(roleName) || roleName.length() < 2
				|| roleName.length() > 10) {
			msgMap.put("msg", "角色名称请输入长度2-10的字");
			return msgMap;
		}
		if (ValidateUtils.isEmpty(desc) || desc.length() < 2
				|| desc.length() > 30) {
			msgMap.put("msg", "描述请输入长度为2-30的字");
			return msgMap;
		}

		// 验证交易密码是否正确
		String tradePwdMsg = validateTradePwd(tradePwd);
		if (!ValidateUtils.isEmpty(tradePwdMsg)) {
			msgMap.put("msg", tradePwdMsg);
		}
		return msgMap;
	}

	/**
	 * 角色列表
	 * 
	 * @return
	 */
	@Permission("Pms:Role:View")
	public String listMerchantRole() {
		Map<String, Object> params = new HashMap<String, Object>();
		if (ValidateUtils.isEmpty(getString("msg"))) {
			params.put("roleName", getString("roleName"));
		}
		params.put("userNo", getCurrentUserInfo().getUserNo());
		pageBean = merchantRoleFacade.listPage(getPageParam(), params);
		pushData(pageBean);
		pushData(params);
		putData("msg", getString("msg"));
		return "listMerchantRole";
	}

	/**
	 * 删除角色
	 * 
	 * @return
	 */
	@Permission("Pms:Role:Operation")
	public void deleteMerchantRole() {
		Long id = getLong("id");
		String tradePassWord = super.getPwd("tradePwd");// 支付密码

		// 1、根据主键获取角色ID
		MerchantRole merchantRole = merchantRoleFacade.getById(id);
		if (ValidateUtils.isEmpty(merchantRole)) {
			throw new PortalMerchantException(
					PortalMerchantException.PARAM_IS_ERROR, "参数有误");
		}

		// 2、验证交易密码
		String errMsg = super.validateTradePwd(tradePassWord);
		if (!ValidateUtils.isEmpty(errMsg)) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", errMsg);
		} else {

			// 验证参数是否有误
			Map<String, String> msgMap = validateDeleteMerchantRole(id);
			if (!msgMap.isEmpty()) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "删除角色失败，" + msgMap.get("msg"));

				// 记录操作日志
				this.addUserLog(OpeStatusEnum.FAIL,
						String.format("删除角色名为%s。", merchantRole.getRoleName()));
			} else {
				merchantRoleFacade.deleteById(Long.valueOf(id));
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "删除角色成功!");

				// 记录操作日志
				this.addUserLog(OpeStatusEnum.SUCCESS,
						String.format("删除角色名为%s。", merchantRole.getRoleName()));
			}
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 删除角色时,验证参数是否有误
	 * 
	 * @param id
	 * @return
	 */
	private Map<String, String> validateDeleteMerchantRole(Long id) {
		Map<String, String> msgMap = new HashMap<String, String>();
		List<?> list = merchantRoleOperatorFacade.listByRoleId(id);
		if (list != null && list.size() > 0) {
			msgMap.put("msg", "无法删除：您要删除的角色有" + list.size() + "个操作员");
		}
		return msgMap;
	}

	// /////////////////////////////////////操作员相关///////////////////////////////
	/**
	 * 转到添加操作员页面
	 * 
	 * @return
	 */
	@Permission("Pms:Operator:Operation")
	public String addMerchantOperatorUI() {
		initRoleList();// 准备角色列表
		return "addMerchantOperatorUI";

	}

	/**
	 * 添加操作员
	 * 
	 * @return
	 */
	@Permission("Pms:Operator:Operation")
	public String addMerchantOperator() {
		String loginName = getString("loginName");// 登录名
		String realName = getString("realName");// 真实姓名
		String mobileNo = getString("mobileNo");// 手机号码
		String loginPwd = getString("loginPwd");// 登陆密码
		String reLoginPwd = getString("reLoginPwd");// 确认登陆密码
		String tradePwd = super.getPwd("tradePwd"); // 交易密码
		String roleOperatorStr = getRoleOperatorStr(); // 获取角色操作员

		// 1、验证操作员的参数信息是否有效
		Map<String, String> msgMap = validateAddMerchantOperator(loginName,
				realName, mobileNo, tradePwd);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL,
					String.format("添加操作员，姓名为%s", loginName));			
			putData("loginName", loginName);
			putData("realName", realName);
			putData("mobileNo", mobileNo);
			putData("loginPwd", loginPwd);
			putData("reLoginPwd", reLoginPwd);
			putData("owenedRoleIds", roleOperatorStr);
			pushData(msgMap);
			initRoleList();
			return "addMerchantOperatorUI";
		}

		// 2、设置操作员信息
		UserOperator userOperator = new UserOperator();
		userOperator.setRealName(realName);// 姓名
		userOperator.setLoginName(loginName);// 登录名
//		userOperator.setLoginPwd(DigestUtils.sha1Hex(loginName));// 密码
		userOperator.setLoginPwd(DigestUtils.sha1Hex(loginPwd));
		userOperator.setMobileNo(mobileNo);// 手机号码
		userOperator.setStatus(UserStatusEnum.ACTIVE.getValue());// 状态
		userOperator.setType(UserOperatorTypeEnum.USER.getValue());// 类型
		userOperator.setUserNo(getCurrentUserInfo().getUserNo());// 用户ID
		userOperator.setIsChangedPwd(0);// 是否更改过密码
		userOperator.setPwdErrorTimes(0);
		userOperator.setPwdErrorLastTime(null);

		// 3、执行添加操作
		merchantPermissionFacade.saveMerchantOperator(userOperator,
				roleOperatorStr);

		putData("merchantOperatorTypeList", UserOperatorTypeEnum.values());
		putData("msg", "添加操作员成功");

		// 4、记录操作日志
		this.addUserLog(OpeStatusEnum.SUCCESS,
				String.format("添加操作员，姓名为%s", loginName));

		return "addMerchantOperator";
	}

	/**
	 * 验证操作员的参数信息是否有效
	 * 
	 * @param loginName
	 * @param realName
	 * @param loginPwd
	 * @param reLoginPwd
	 * @param mobileNo
	 * @param tradePwd
	 * @return
	 */
	private Map<String, String> validateAddMerchantOperator(String loginName,
			String realName, String mobileNo, String tradePwd) {
		Map<String, String> msgMap = new HashMap<String, String>();
		if (ValidateUtils.isEmpty(realName) || realName.length() < 2
				|| realName.length() > 10 || !ValidateUtils.isChinese(realName)) {
			msgMap.put("msg", "操作员姓名请输入2-10个中文'");
			return msgMap;
		}
		if (ValidateUtils.isEmpty(mobileNo)
				|| !ValidateUtils.isMobile(mobileNo)) {
			msgMap.put("msg", "操作员手机请输入正确的手机号码");
			return msgMap;
		}

		// 验证交易密码
		String tradePwdMsg = this.validateTradePwd(tradePwd);
		if (!ValidateUtils.isEmpty(tradePwdMsg)) {
			msgMap.put("msg", tradePwdMsg);
		}
		return msgMap;
	}

	/**
	 * 转到修改操作员页面
	 * 
	 * @return
	 */
	@Permission("Pms:Operator:Operation")
	public String editMerchantOperatorUI() {
		Long id = getLong("id");
		UserOperator merchantOperator = userOperatorFacade
				.getUserOperatorById(id);
		if (ValidateUtils.isEmpty(merchantOperator)) {
			throw new PortalMerchantException(
					PortalMerchantException.PARAM_IS_ERROR, "参数有误");
		}
		if (UserOperatorTypeEnum.USER.getValue() == getCurrentUserOperator()
				.getType()
				&& UserOperatorTypeEnum.ADMIN.getValue() == merchantOperator
						.getType()) {
			throw new PortalMerchantException(
					PortalMerchantException.PMS_IS_ERROR, "权限不足！操作员不可修改管理员信息");
		}
		if (!merchantOperator.getUserNo().equals(
				getCurrentUserInfo().getUserNo())) {
			throw new PortalMerchantException(
					PortalMerchantException.PARAM_IS_ERROR, "参数有误，此操作员不是商户操作员");
		}
		pushData(merchantOperator);

		putData("owenedRoleIds",
				merchantRoleOperatorFacade.getRoleStrByOperatorId(id));// 原角色
		initRoleList();// 准备角色列表
		putData("operatorStatuslist", OperatorStatusEnum.values());
		putData("operatorTypeList", UserOperatorTypeEnum.values());
		return "editMerchantOperatorUI";
	}

	/**
	 * 批量更新多个操作员角色
	 * 
	 * @return
	 */
	@Permission("Pms:Operator:Operation")
	public String editMerchantOperatorsRole() {
		String roleStr = getRoleOperatorStr();// 角色
		String operatorIdsStr = getOperatorIdsStr();// 操作员
		String tradePwd = super.getPwd("tradePwd");

		// 1、验证批量更新多个操作员角色的参数是否有效
		Map<String, String> msgMap = validateEditMerchantOperatorUI(roleStr,
				operatorIdsStr, tradePwd);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "批量分配操作员角色。");
			putData("msg", msgMap.get("msg"));
			return "editMerchantOperatorsRole";
		}
		try {
			merchantPermissionFacade.saveMerchantRoleOperator(roleStr,
					operatorIdsStr);
			putData("msg", "操作员分配角色成功");
		} catch (UserBizException e) {
			// 记录操作日志
			this.addUserLog(OpeStatusEnum.FAIL, "批量分配操作员角色。");
		}
		// 记录操作日志
		this.addUserLog(OpeStatusEnum.SUCCESS, "批量分配操作员角色。");

		return "editMerchantOperatorsRole";
	}

	/**
	 * 验证批量更新多个操作员角色的参数是否有效
	 * 
	 * @param roleStr
	 * @param operatorIdsStr
	 * @param tradePwd
	 * @return
	 */
	private Map<String, String> validateEditMerchantOperatorUI(String roleStr,
			String operatorIdsStr, String tradePwd) {
		Map<String, String> msgMap = new HashMap<String, String>();
		if (ValidateUtils.isEmpty(roleStr)) {
			msgMap.put("msg", "请选择角色");
			return msgMap;
		}
		if (ValidateUtils.isEmpty(operatorIdsStr)) {
			msgMap.put("msg", "请选择操作员");
			return msgMap;
		}
		// 验证交易密码
		String tradePwdMsg = this.validateTradePwd(tradePwd);
		if (!ValidateUtils.isEmpty(tradePwdMsg)) {
			msgMap.put("msg", tradePwdMsg);
		}
		return msgMap;
	}

	/**
	 * 修改操作员
	 * 
	 * @return
	 */
	@Permission("Pms:Operator:Operation")
	public String editMerchantOperator() {
		Long id = getLong("id");
		String mobileNo = getString("mobileNo");// 手机号码
		String tradePwd = super.getPwd("tradePwd");// 支付密码
		String roleOperatorStr = getRoleOperatorStr();

		// 1、根据主键查询用户操作员
		UserOperator userOperator = userOperatorFacade.getUserOperatorById(id);
		if (ValidateUtils.isEmpty(userOperator)) {
			throw new PortalMerchantException(
					PortalMerchantException.PARAM_IS_ERROR, "参数有误");
		}
		if (UserOperatorTypeEnum.USER.getValue() == getCurrentUserOperator()
				.getType()
				&& UserOperatorTypeEnum.ADMIN.getValue() == userOperator
						.getType()) {
			throw new PortalMerchantException(
					PortalMerchantException.PMS_IS_ERROR, "权限不足！操作员不可修改管理员信息");
		}
		if (!userOperator.getUserNo().equals(getCurrentUserInfo().getUserNo())) {
			throw new PortalMerchantException(
					PortalMerchantException.PARAM_IS_ERROR, "参数有误，此操作员不是商户操作员");
		}

		// 2、验证操作员参数是否有效
		Map<String, String> msgMap = validateEditMerchantOperator(mobileNo,
				tradePwd);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL,
					String.format("修改操作员，原因：%s。", msgMap.get("msg")));
			putData("id", id);
			putData("mobileNo", mobileNo);
			putData("loginName", userOperator.getLoginName());
			putData("type", userOperator.getType());
			putData("realName", userOperator.getRealName());
			putData("owenedRoleIds", roleOperatorStr);
			putData("status", userOperator.getStatus());
			putData("operatorStatuslist", OperatorStatusEnum.values());
			putData("operatorTypeList", UserOperatorTypeEnum.values());
			initRoleList();
			pushData(msgMap);
			return "editMerchantOperatorUI";
		}

		// 3、重新设置操作员的值
		userOperator.setMobileNo(mobileNo);

		// 4、执行修改操作 （普通操作员、管理员）
		try {
			if (userOperator.getType() == UserOperatorTypeEnum.ADMIN.getValue()) {
				merchantPermissionFacade.updateMerchantOperator(userOperator,
						null);
			} else {
				merchantPermissionFacade.updateMerchantOperator(userOperator,
						roleOperatorStr);
			}
			putData("msg", "修改操作员成功");
		} catch (UserBizException e) {
			// 记录操作日志
			this.addUserLog(OpeStatusEnum.FAIL,
					String.format("修改操作员，原因：%s。", e.getMsg()));
		}

		// 5、记录操作日志
		this.addUserLog(OpeStatusEnum.SUCCESS, "修改操作员。");

		return "editMerchantOperator";
	}

	/**
	 * 验证操作员参数是否有效
	 * 
	 * @param realName
	 * @param mobileNo
	 * @param tradePwd
	 * @return
	 */
	private Map<String, String> validateEditMerchantOperator(String mobileNo,
			String tradePwd) {
		Map<String, String> msgMap = new HashMap<String, String>();
		if (ValidateUtils.isEmpty(mobileNo)
				|| !ValidateUtils.isMobile(mobileNo)) {
			msgMap.put("msg", "操作员手机请输入正确的手机号码");
			return msgMap;
		}

		// 验证交易密码
		String tradePwdMsg = this.validateTradePwd(tradePwd);
		if (!ValidateUtils.isEmpty(tradePwdMsg)) {
			msgMap.put("msg", tradePwdMsg);
		}
		return msgMap;
	}

	/**
	 * 操作员列表
	 * 
	 * @return
	 */
	@Permission("Pms:Operator:View")
	public String listMerchantOperator() {
		Map<String, Object> params = new HashMap<String, Object>();
		if (ValidateUtils.isEmpty(getString("msg"))) {
			params.put("loginName", getString("loginName"));
			params.put("realName", getString("realName"));
		}
		params.put("userNo", getCurrentUserInfo().getUserNo());
		params.put("status", UserStatusEnum.ACTIVE.getValue());
		pageBean = userOperatorFacade.listUserOperatorForPage(
				this.getPageParam(), params);
		pushData(pageBean);
		pushData(params);
		initRoleList();
		putData("operatorStatuslist", OperatorStatusEnum.values());
		putData("operatorTypeList", UserOperatorTypeEnum.values());
		putData("UserOperatorTypeEnum", UserOperatorTypeEnum.toMap());
		putData("OperatorStatusEnum", OperatorStatusEnum.toMap());
		putData("msg", getString("msg"));
		return "listMerchantOperator";
	}

	/**
	 * 修改操作员状态（冻结，激活）
	 * 
	 * @return
	 */
	@Permission("Pms:Operator:Operation")
	public void updateMerchantOperatorStatus() {
		// 1、根据ID获取当前用户
		UserOperator userOperator = userOperatorFacade
				.getUserOperatorById(getLong("id"));
		if (ValidateUtils.isEmpty(userOperator)) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG",
					"错误代码：" + PortalMerchantException.PARAM_IS_ERROR);
			outPrint(this.getHttpResponse(),
					JSONObject.fromObject(getOutputMsg()));
			return;
		}
		if (!userOperator.getUserNo().equals(getCurrentUserInfo().getUserNo())) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG",
					"错误代码：" + PortalMerchantException.PARAM_IS_ERROR);
			outPrint(this.getHttpResponse(),
					JSONObject.fromObject(getOutputMsg()));
			return;
		}
		if (userOperator.getType() == UserOperatorTypeEnum.ADMIN.getValue()) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG",
					"错误代码：" + PortalMerchantException.PMS_IS_ERROR);
			outPrint(this.getHttpResponse(),
					JSONObject.fromObject(getOutputMsg()));
			return;
		}

		// 2、验证交易密码
		String tradePwd = super.getPwd("tradePwd");// 支付密码
		String errMsg = this.validateTradePwd(tradePwd);
		if (!ValidateUtils.isEmpty(errMsg)) {// 验证支付密码
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", errMsg);
		} else {

			if (userOperator.getStatus() == UserStatusEnum.ACTIVE.getValue()) {
				userOperator.setStatus(UserStatusEnum.INACTIVE.getValue());
				userOperatorFacade.updateUserOperator(userOperator);
				getOutputMsg().put("STATE", "SECCESS");
				getOutputMsg().put("MSG",
						"用户名为" + userOperator.getLoginName() + "的操作员冻结成功！");

				// 记录操作日志
				this.addUserLog(OpeStatusEnum.SUCCESS,
						String.format("冻结%s操作员。", userOperator.getLoginName()));

			} else if (userOperator.getStatus() == UserStatusEnum.INACTIVE
					.getValue()) {
				userOperator.setStatus(UserStatusEnum.ACTIVE.getValue());
				userOperatorFacade.updateUserOperator(userOperator);
				getOutputMsg().put("STATE", "SECCESS");
				getOutputMsg().put("MSG",
						"用户名为" + userOperator.getLoginName() + "的操作员激活成功！");

				// 记录操作日志
				this.addUserLog(OpeStatusEnum.SUCCESS,
						String.format("激活%s操作员。", userOperator.getLoginName()));
			}
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 重置密码
	 * 
	 * @return
	 */

	@Permission("Pms:Operator:Operation")
	public void resetMerchantOperatorLoginPwd() {
		// 1、根据主键获取操作员
		UserOperator userOperator = userOperatorFacade
				.getUserOperatorById(getLong("id"));
		if (ValidateUtils.isEmpty(userOperator)) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG",
					"错误代码：" + PortalMerchantException.PARAM_IS_ERROR);
			outPrint(this.getHttpResponse(),
					JSONObject.fromObject(getOutputMsg()));
			return;
		}
		if (!userOperator.getUserNo().equals(getCurrentUserInfo().getUserNo())) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG",
					"错误代码：" + PortalMerchantException.PARAM_IS_ERROR);
			outPrint(this.getHttpResponse(),
					JSONObject.fromObject(getOutputMsg()));
			return;
		}
		if (userOperator.getType() == UserOperatorTypeEnum.ADMIN.getValue()) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG",
					"错误代码：" + PortalMerchantException.PMS_IS_ERROR);
			outPrint(this.getHttpResponse(),
					JSONObject.fromObject(getOutputMsg()));
			return;
		}

		// 2、验证交易密码
		String tradePwd = super.getPwd("tradePwd");// 支付密码
		String errMsg = this.validateTradePwd(tradePwd);
		if (!ValidateUtils.isEmpty(errMsg)) {// 验证支付密码
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", errMsg);
		} else {

			// 设置参数值
			String newPassword = UUIDUitl.generateString(8);
			userOperator.setLoginPwd(DigestUtils.sha1Hex(newPassword));
			userOperator.setIsChangedPwd(0);

			// 执行修改操作
			try {
				merchantPermissionFacade.updateMerchantOperator(userOperator,
						null);
				putData("operator", userOperator);
				putData("newPwd", newPassword);
				getOutputMsg().put("STATE", "SECCESS");
				getOutputMsg().put(
						"MSG",
						"重置密码成功，" + userOperator.getLoginName() + "的新密码为："
								+ newPassword);

				// 记录操作日志
				this.addUserLog(OpeStatusEnum.SUCCESS,
						String.format("重置%s的密码。", userOperator.getLoginName()));
			} catch (UserBizException e) {

				// 记录操作日志
				this.addUserLog(OpeStatusEnum.FAIL,
						String.format("重置%s的密码。", userOperator.getLoginName()));
			}
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	// ////////////////////////////////////商户操作员日志//////////////////////////////////

	/**
	 * 进入操作员操作日志信息查询页面.
	 * 
	 * @return
	 */
	public String listPmsOperatorLogUI() {
		return "PmsOperatorLogList";
	}

	/**
	 * 分页查询列出操作员操作日志信息.
	 * 
	 * @return
	 */
	@Permission("Pms:OperatorLog:View")
	public String listMerchantOperateLog() {
		String beginDate = getString("beginDate");// 开始时间
		String endDate = getString("endDate");// 结束时间
		Integer operateStatus = getInteger("operateStatus");// 状态
		String ip = getString("ip");// IP
		String operatorName = getString("operatorName");

		if (!(ValidateUtils.isEmpty(beginDate) || ValidateUtils
				.isEmpty(endDate))) {
			pageBean = userOperatorLogFacade.listUserOperatorLogForPage(
					getPageParam(), beginDate, endDate, getCurrentUserInfo()
							.getUserNo(), operatorName, operateStatus, ip);
			pushData(pageBean);
		}
		putData("proMap", getParamMap_Utf8());
		putData("timeType", getString("timeType"));
		putData("beginDate", beginDate);
		putData("endDate", endDate);
		return "listMerchantOperateLog";
	}

	/**
	 * 根据操作员操作日志ID查看操作日志详情.
	 * 
	 * @return
	 */
	@Permission("Pms:OperatorLog:View")
	public String viewMerchantOperateLog() {
		UserOperatorLog entity = userOperatorLogFacade
				.getUserOperatorLogById(getLong("id"));
		this.putData("operatorLog", entity);
		return "viewMerchantOperateLog";
	}

	// /////////////////////////////////////辅助相关方法///////////////////////////////

	/**
	 * 准备权限列表
	 */
	private void initActionList() {
		putData("actionsList",
				merchantActionFacade
						.listMerActionByMerType(getCurrentUserInfo()
								.getUserType().toString()));
	}

	/**
	 * 权限点集合字符串.
	 * 
	 * @return actionStr .
	 */
	private String getSelectActions() {
		String actionStr = StringTools.stringToTrim(getString("selectVal"));
		if (actionStr.length() > 0) {
			actionStr = actionStr.substring(0, actionStr.length() - 1);
		}
		return actionStr;
	}

	/**
	 * 准备角色列表
	 */
	private void initRoleList() {
		String hasRole = "";
		if ( merchantRoleFacade.listRoleByUserNo(getCurrentUserInfo().getUserNo()).size() >= 1) {
			hasRole = "true";
		} 
		else {
			hasRole = "false";
		}
		this.putData("hasRole", hasRole);
		this.putData("rolesList", merchantRoleFacade
				.listRoleByUserNo(getCurrentUserInfo().getUserNo()));
	}

	/**
	 * 得到角色和操作员关联的ID字符串
	 * 
	 * @return
	 */
	private String getRoleOperatorStr() {
		String roleStr = StringTools.stringToTrim(getString("selectVal"));
		if (roleStr != null && roleStr.length() > 0) {
			roleStr = roleStr.substring(0, roleStr.length() - 1);
		}
		return roleStr;
	}

	/**
	 * 得到操作员（用于批量给多个操作员分配角色）
	 * 
	 * @return
	 */
	private String getOperatorIdsStr() {
		String operatorIdsStr = StringTools
				.stringToTrim(getString("selectOperatorVal"));
		if (operatorIdsStr != null && operatorIdsStr.length() > 0) {
			operatorIdsStr = operatorIdsStr.substring(0,
					operatorIdsStr.length() - 1);
		}
		return operatorIdsStr;
	}
}
