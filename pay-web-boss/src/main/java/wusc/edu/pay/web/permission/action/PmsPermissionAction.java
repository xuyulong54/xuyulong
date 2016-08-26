package wusc.edu.pay.web.permission.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.web.permission.base.PermissionBaseAction;
import wusc.edu.pay.web.permission.biz.PmsActionBiz;
import wusc.edu.pay.web.permission.biz.PmsMenuBiz;
import wusc.edu.pay.web.permission.biz.PmsOperatorBiz;
import wusc.edu.pay.web.permission.biz.PmsRoleBiz;
import wusc.edu.pay.web.permission.entity.PmsAction;
import wusc.edu.pay.web.permission.entity.PmsMenu;
import wusc.edu.pay.web.permission.entity.PmsOperator;
import wusc.edu.pay.web.permission.entity.PmsRole;
import wusc.edu.pay.web.permission.entity.PmsRoleOperator;
import wusc.edu.pay.web.permission.enums.OperatorStatusEnum;
import wusc.edu.pay.web.permission.enums.OperatorTypeEnum;
import wusc.edu.pay.web.permission.enums.RoleTypeEnum;


/**
 * 权限管理模块的Action类，包括权限点管理、角色管理、操作员管理.<br/>
 * 
 * @author ZhangWensi.<br/>
 * 
 * @修改版本:1.1 .
 * @修改人：WuShuicheng .
 * @修改时间: 2013-07-11 .
 * @修改内容: 添加结合DWZ-UI框架的Action层可共用方法 .
 * 
 */
@Scope("prototype")
public class PmsPermissionAction extends PermissionBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5588682213578275029L;

	private static Log log = LogFactory.getLog(PmsPermissionAction.class);

	@Autowired
	private PmsActionBiz pmsActionBiz;
	@Autowired
	private PmsRoleBiz pmsRoleBiz;
	@Autowired
	private PmsOperatorBiz pmsOperatorBiz;
	@Autowired
	private PmsMenuBiz pmsMenuBiz;

	// //////////////////////////////////// 权限点管理
	// ///////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////

	/**
	 * 分页列出pms权限，也可根据权限获权限名称进行查询.
	 * 
	 * @return PmsActionList or operateError.
	 */
	@Permission("pms:action:view")
	public String listPmsAction() {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>(); // 业务条件查询参数
			paramMap.put("actionName", getString("actionName")); // 权限名称（模糊查询）
			paramMap.put("action", getString("act")); // 权限（精确查询）
			paramMap.put("act", getString("act"));
			super.pageBean = pmsActionBiz.listPage(getPageParam(), paramMap);
			super.pushData(pageBean);
			super.pushData(paramMap); // 回显查询条件值
			return "PmsActionList";
		} catch (Exception e) {
			log.error("== listPmsAction exception:", e);
			return operateError("获取数据失败");
		}
	}

	/**
	 * 进入添加Pms权限页面 .
	 * 
	 * @return addPmsActionUI .
	 */
	@Permission("pms:action:add")
	public String addPmsActionUI() {
		return "PmsActionAdd";
	}

	/**
	 * 将权限信息保存到数据库中
	 * 
	 * @return operateSuccess or operateError.
	 */
	@Permission("pms:action:add")
	public String addPmsAction() {
		try {
			String actionName = getString("actionName"); // 权限名称
			String action = getString("action"); // 权限标识
			String desc = getString("desc"); // 权限描述
			Long menuId = getLong("menu.id"); // 权限关联的菜单ID
			// 权限
			PmsAction act = new PmsAction();
			act.setActionName(actionName);
			act.setAction(action);
			act.setRemark(desc);
			// 菜单
			PmsMenu menu = new PmsMenu();
			menu.setId(menuId);
			act.setMenu(menu); // 设置菜单ID

			// 表单数据校验
			String validateMsg = validatePmsAction(act);
			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg); // 返回错误信息
			}
			// 检查权限名称是否已存在
			PmsAction checkName = pmsActionBiz.getByActionName(actionName.trim());
			if (checkName != null) {
				return operateError("权限名称【" + actionName + "】已存在");
			}
			// 检查权限是否已存在
			PmsAction checkAction = pmsActionBiz.getByAction(action.trim());
			if (checkAction != null) {
				return operateError("权限【" + action + "】已存在");
			}

			pmsActionBiz.saveAction(act);

			super.logSave("添加权限[" + actionName + "," + action + "]");

			return operateSuccess(); // 返回operateSuccess视图,并提示“操作成功”
		} catch (Exception e) {
			log.error("== addPmsAction exception:", e);
			return operateError("保存失败");
		}
	}

	/**
	 * 添加或修改权限时，查找带回权限要关联的菜单ID.
	 * 
	 * @return .
	 */
	public String pmsMenuLookUpUI() {
		putData("tree", pmsMenuBiz.buildLookUpMenu());
		return "PmsMenuLookUp";
	}

	/**
	 * 校验Pms权限信息.
	 * 
	 * @param pmsAction
	 *            .
	 * @return msg .
	 */
	private String validatePmsAction(PmsAction pmsAction) {
		String msg = ""; // 用于存放校验提示信息的变量
		String actionName = pmsAction.getActionName(); // 权限名称
		String action = pmsAction.getAction(); // 权限标识
		String desc = pmsAction.getRemark(); // 权限描述
		// 权限名称 actionName
		msg += lengthValidate("权限名称", actionName, true, 3, 90);
		// 权限标识 action
		msg += lengthValidate("权限标识", action, true, 3, 100);
		// 描述 desc
		msg += lengthValidate("描述", desc, true, 3, 60);
		// 校验菜单ID是否存在
		if (null != pmsAction.getMenu().getId()) {
			PmsMenu menu = pmsMenuBiz.getById(pmsAction.getMenu().getId());
			if (menu == null) {
				msg += "，请选择权限关联的菜单";
			}
		} else {
			msg += "，请选择权限关联的菜单";
		}
		return msg;
	}

	/**
	 * 转到权限修改页面 .
	 * 
	 * @return editPmsActionUI or operateError .
	 */
	@Permission("pms:action:edit")
	public String editPmsActionUI() {
		try {
			Long id = getLong("id");
			PmsAction pmsAction = pmsActionBiz.getById(id);
			super.putData("pmsAction", pmsAction);
			return "PmsActionEdit";
		} catch (Exception e) {
			log.error("== editPmsActionUI exception:", e);
			return operateError("获取数据失败");
		}
	}

	/**
	 * 保存修改后的权限信息
	 * 
	 * @return operateSuccess or operateError .
	 */
	@Permission("pms:action:edit")
	public String editPmsAction() {
		try {
			Long id = getLong("actionId");
			PmsAction pmsAction = pmsActionBiz.getById(id);
			if (pmsAction == null) {
				return operateError("无法获取要修改的数据");
			} else {

				String actionName = getString("actionName");
				// String action = getString("action");
				String desc = getString("desc");

				pmsAction.setActionName(actionName);
				// pmsAction.setAction(action);
				pmsAction.setRemark(desc);

				// 表单数据校验
				String validateMsg = validatePmsAction(pmsAction);
				if (StringUtils.isNotBlank(validateMsg)) {
					return operateError(validateMsg); // 返回错误信息
				}

				// 检查权限名称是否已存在
				PmsAction checkName = pmsActionBiz.getByActionNameNotEqId(actionName, id);
				if (checkName != null) {
					return operateError("权限名称【" + actionName + "】已存在");
				}
				// 检查权限是否已存在
				// PmsAction checkAction =
				// pmsActionBiz.getByActionNotEqId(action, id);
				// if (checkAction != null){
				// return operateError("权限【"+action+"】已存在");
				// }

				pmsActionBiz.updateAction(pmsAction);

				super.logEdit("修改权限[" + actionName + "],[" + pmsAction.getAction() + "]");

				return operateSuccess();
			}
		} catch (Exception e) {
			log.error("== editPmsAction exception:", e);
			return operateError("修改失败");
		}
	}

	/**
	 * 删除一条权限记录
	 * 
	 * @return operateSuccess or operateError .
	 */
	@Permission("pms:action:delete")
	public String deletePmsAction() {
		try {
			Long actionId = getLong("id");
			PmsAction act = pmsActionBiz.getById(actionId);
			if (act == null) {
				return operateError("无法获取要删除的数据");
			}
			// 判断此权限是否关联有角色，要先解除与角色的关联后才能删除该权限
			List<PmsRole> roleList = pmsRoleBiz.listByActionId(actionId);
			if (roleList != null && !roleList.isEmpty()) {
				return operateError("权限【" + act.getAction() + "】关联了【" + roleList.size() + "】个角色，要解除所有关联后才能删除。其中一个角色名为:" + roleList.get(0).getRoleName());
			}
			pmsActionBiz.deleteActionById(actionId);
			super.logDelete("删除权限[" + act.getActionName() + "],[" + act.getAction() + "]");
			return operateSuccess(); // 返回operateSuccess视图,并提示“操作成功”
		} catch (Exception e) {
			log.error("== deletePmsAction exception:", e);
			return operateError("删除限权异常");
		}
	}

	// /////////////////////////////////// 角色管理
	// ///////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 获取角色列表
	 * 
	 * @return listPmsRole or operateError .
	 */
	@Permission("pms:role:view")
	public String listPmsRole() {
		try {

			Map<String, Object> paramMap = new HashMap<String, Object>(); // 业务条件查询参数
			paramMap.put("roleName", getString("roleName")); // 角色名称（模糊查询）

			super.pageBean = pmsRoleBiz.listPage(getPageParam(), paramMap);

			PmsOperator operator = this.getLoginedOperator();
			super.pushData(operator);
			super.pushData(pageBean);
			// 回显查询条件值
			super.pushData(paramMap);		
			
			super.putData("RoleTypeEnumList", RoleTypeEnum.values());
			super.putData("RoleTypeEnum", RoleTypeEnum.toMap());
			super.putData("OperatorTypeEnum", OperatorTypeEnum.toMap());
			
			return "PmsRoleList";
		} catch (Exception e) {
			log.error("== listPmsRole exception:", e);
			return operateError("获取数据失败");
		}
	}

	/**
	 * 转到添加角色页面 .
	 * 
	 * @return addPmsRoleUI or operateError .
	 */
	@Permission("pms:role:add")
	public String addPmsRoleUI() {
		try {
			return "PmsRoleAdd";
		} catch (Exception e) {
			log.error("== addPmsRoleUI get data exception:", e);
			return operateError("获取数据失败");
		}
	}

	/**
	 * 保存新添加的一个角色 .
	 * 
	 * @return operateSuccess or operateError .
	 */
	@Permission("pms:role:add")
	public String addPmsRole() {
		try {
			String roleName = getString("roleName");
			PmsRole roleCheck = pmsRoleBiz.getByRoleName(roleName);
			if (roleCheck != null) {
				return operateError("角色名【" + roleName + "】已存在");
			}

			// 保存基本角色信息
			PmsRole pmsRole = new PmsRole();
			pmsRole.setRoleType(RoleTypeEnum.USER.getValue()); // 角色类型（1:超级管理员角色，0:普通操作员角色）
			pmsRole.setRoleName(roleName);
			pmsRole.setRemark(getString("desc"));
			pmsRole.setCreateTime(new Date());

			// 表单数据校验
			String validateMsg = validatePmsRole(pmsRole);
			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg); // 返回错误信息
			}

			pmsRoleBiz.saveRole(pmsRole);

			// 记录操作员操作日志
			super.logSave("添加角色信息，角色名称[" + pmsRole.getRoleName() + "]");
			return operateSuccess();
		} catch (Exception e) {
			log.error("== addPmsRole exception:", e);
			return operateError("保存数据失败");
		}
	}

	/**
	 * 校验角色表单数据.
	 * 
	 * @param pmsRole
	 *            角色信息.
	 * @return msg .
	 */
	private String validatePmsRole(PmsRole pmsRole) {
		String msg = ""; // 用于存放校验提示信息的变量
		String roleName = pmsRole.getRoleName(); // 角色名称
		String desc = pmsRole.getRemark(); // 描述
		// 角色名称 actionName
		msg += lengthValidate("角色名称", roleName, true, 3, 90);
		// 描述 desc
		msg += lengthValidate("描述", desc, true, 3, 300);
		return msg;
	}

	/**
	 * 转到角色修改页面 .
	 * 
	 * @return editPmsRoleUI or operateError .
	 */
	@Permission("pms:role:edit")
	public String editPmsRoleUI() {
		try {
			Long roleId = getLong("roleId");
			PmsRole pmsRole = pmsRoleBiz.getById(roleId);
			if (pmsRole == null) {
				return operateError("获取数据失败");
			}

			// 普通操作员没有修改超级管理员角色的权限
			if (OperatorTypeEnum.USER.getValue().equals(this.getLoginedOperator().getType()) 
			 && RoleTypeEnum.ADMIN.getValue().equals(pmsRole.getRoleType())) {
				return operateError("你没有修改超级管理员角色的权限");
			}

			super.pushData(pmsRole);
			
			return "PmsRoleEdit";
		} catch (Exception e) {
			log.error("== editPmsRoleUI exception:", e);
			return operateError("获取数据失败");
		}
	}

	/**
	 * 保存修改后的角色信息 .
	 * 
	 * @return operateSuccess or operateError .
	 */
	@Permission("pms:role:edit")
	public String editPmsRole() {
		try {
			Long id = getLong("id");

			PmsRole pmsRole = pmsRoleBiz.getById(id);
			if (pmsRole == null) {
				return operateError("无法获取要修改的数据");
			}

			// 普通操作员没有修改超级管理员角色的权限
			if (OperatorTypeEnum.USER.getValue().equals(this.getLoginedOperator().getType()) 
			 && RoleTypeEnum.ADMIN.getValue().equals(pmsRole.getRoleType())) {
				return operateError("你没有修改超级管理员角色的权限");
			}

			String roleName = getString("roleName");
			PmsRole roleCheck = pmsRoleBiz.findByRoleNameNotEqId(id, roleName);
			if (roleCheck != null) {
				return operateError("角色名【" + roleName + "】已存在");
			}

			pmsRole.setRoleName(roleName);
			pmsRole.setRemark(getString("remark"));

			// 表单数据校验
			String validateMsg = validatePmsRole(pmsRole);
			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg); // 返回错误信息
			}

			pmsRoleBiz.updateRole(pmsRole);

			super.logEdit("修改角色[" + pmsRole.getRoleName() + "]");

			return operateSuccess();
		} catch (Exception e) {
			log.error("== editPmsRole exception:", e);
			return operateError("保存失败");
		}
	}

	/**
	 * 删除一个角色
	 * 
	 * @return operateSuccess or operateError .
	 */
	@Permission("pms:role:delete")
	public String deletePmsRole() {
		try {
			Long roleId = getLong("roleId");

			PmsRole role = pmsRoleBiz.getById(roleId);
			if (role == null) {
				return operateError("无法获取要删除的角色");
			}
			if (RoleTypeEnum.ADMIN.getValue().equals(role.getRoleType())) {
				return operateError("超级管理员角色不可删除");
			}

			String msg = "";
			// 判断是否有操作员关联到此角色
			int operatorCount = pmsOperatorBiz.countOperatorByRoleId(roleId);
			if (operatorCount > 0) {
				msg += "【" + operatorCount + "】个操作员";
			}
			// 判断是否有权限关联到此角色 ----2014-02-18注释
			// int actionCount = pmsActionBiz.countActionByRoleId(roleId);
			// if (actionCount > 0){
			// msg += "【"+actionCount+"】个权限";
			// }
			// // 判断是否有菜单关联到此角色
			// int menuCount = pmsMenuBiz.countMenuByRoleId(roleId);
			// if (menuCount > 0){
			// msg += "【"+menuCount+"】个菜单";
			// }

			if (StringUtils.isNotBlank(msg)) {
				msg += "关联到此角色，要先解除所有关联后才能删除!";
				return operateError("有" + msg);
			}

			pmsRoleBiz.deleteRoleById(roleId);
			super.logDelete("删除角色，名称:" + role.getRoleName());
			return operateSuccess();
		} catch (Exception e) {
			log.error("== deletePmsRole exception:", e);
			super.logDeleteError("删除角色出错:" + e.getMessage());
			return operateError("删除失败");
		}
	}

	/**
	 * 分配权限UI
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Permission("pms:role:assignpermission")
	public String assignPermissionUI() {
		Long roleId = getLong("roleId");

		PmsRole role = pmsRoleBiz.getById(roleId);
		if (role == null) {
			return operateError("无法获取角色信息");
		}
		// 普通操作员没有修改超级管理员角色的权限
		if (OperatorTypeEnum.USER.getValue().equals(this.getLoginedOperator().getType()) 
		 && RoleTypeEnum.ADMIN.getValue().equals(role.getRoleType())) {
			return operateError("你没有修改超级管理员角色的权限");
		}

		String menuIds = "";
		String actionIds = "";
		try {
			menuIds = pmsMenuBiz.getMenuIdsByRoleId(roleId); // 根据角色查找角色对应的菜单ID集
			actionIds = pmsActionBiz.getActionIdsByRoleId(roleId); // 根据角色查找角色对应的功能权限ID集
		} catch (Exception e) {
			log.error("根据角色ID，找不到对应的菜单、权限", e);
		}

		// 前面加个逗号，方便接下来的处理
		menuIds = "," + menuIds;
		actionIds = "," + actionIds;

		super.putData("menuActionTree", pmsMenuBiz.buildMenuActionTree(menuIds, actionIds));

		// 查询角色对应的用户
		List<PmsOperator> userList = (List<PmsOperator>) pmsOperatorBiz.listOperatorByRoleId(roleId);
		super.putData("userList", userList);

		super.putData("roleId", roleId);
		return "assignPermissionUI";
	}

	/**
	 * 分配角色权限
	 */
	@Permission("pms:role:assignpermission")
	public void assignPermission() {
		try {

			Long roleId = getLong("roleId");

			PmsRole role = pmsRoleBiz.getById(roleId);
			if (role == null) {
				getOutputMsg().put("MSG", "无法获取角色信息");
				return;
			}
			// 普通操作员没有修改超级管理员角色的权限
			if (OperatorTypeEnum.USER.getValue().equals(this.getLoginedOperator().getType()) 
					 && RoleTypeEnum.ADMIN.getValue().equals(role.getRoleType())) {
				getOutputMsg().put("MSG", "你没有修改超级管理员角色的权限");
				return;
			}
			
			String menuIds = getString("menuIds");
			
			if (StringUtils.isNotBlank(menuIds)) {
				// 去除js错误选择导致的 undefined
				menuIds = menuIds.replaceAll("undefined,", "");
			}
			
			String actionIds = getString("actionIds");
			
			if (StringUtils.isNotBlank(actionIds)) {
				// 去除js错误选择导致的 undefined
				actionIds = actionIds.replaceAll("undefined,", "");
			}
			// 分配菜单权限，功能权限
			pmsMenuBiz.assignPermission(roleId, menuIds, actionIds);

			// String menuNameBuffer = theMenusIdsChangeNames(menuIds); // 查询菜单的

			// String actionNameBuffer = theActionIdsChangeNames(actionIds);
			super.logEdit("修改角色[" + role.getRoleName() + "]的权限，菜单ID[" + menuIds + "],权限ID[" + actionIds + "]");
			// super.logEdit("修改角色[" + role.getRoleName() +
			// "]的权限，菜单名称[" + menuNameBuffer + "],权限名称[" + actionNameBuffer +
			// "]"); // TODO 暂时注释
			getOutputMsg().put("STATE", "SUCCESS");
		} catch (Exception e) {
			log.error("分配权限出现错误!", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "分配权限出现错误。" + e.getMessage());
		}
		outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/***
	 * 把权限的ID转换成NAME
	 * 
	 * @param actionIds
	 * @return
	 */
	@SuppressWarnings("unused")
	private String theActionIdsChangeNames(String actionIds) {
		if (StringUtils.isEmpty(actionIds))
			return null;
		StringBuffer actionBuffer = new StringBuffer();
		int actionNum = actionIds.indexOf(",");
		if (actionNum <= 0) {
			PmsAction action = pmsActionBiz.getById(Long.valueOf(actionIds));
			actionBuffer.append(action.getActionName());
		} else {
			String[] actionArray = actionIds.split(",");
			for (int i = 0; i < actionArray.length; i++) {
				PmsAction action = pmsActionBiz.getById(Long.valueOf(actionArray[i]));
				if (i == actionArray.length - 1) {
					actionBuffer.append(action.getActionName());
				} else {
					actionBuffer.append(action.getActionName()).append(",");
				}
			}
		}
		return actionBuffer.toString();
	}

	/***
	 * 把菜单的ID转换成name
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String theMenusIdsChangeNames(String menuIds) {
		if (StringUtils.isEmpty(menuIds))
			return null;
		StringBuffer menuBuffer = new StringBuffer(); // 追加菜单的名称
		int menuNum = menuIds.indexOf(",");
		if (menuNum <= 0) {
			PmsMenu menu = pmsMenuBiz.getById(Long.valueOf(menuIds));
			menuBuffer.append(menu.getName());
		} else {
			String[] menuArray = menuIds.split(",");
			for (int i = 0; i < menuArray.length; i++) {
				PmsMenu menu = pmsMenuBiz.getById(Long.valueOf(menuArray[i]));
				if (i == menuArray.length - 1) {
					menuBuffer.append(menu.getName());
				} else {
					menuBuffer.append(menu.getName()).append(",");
				}
			}
		}
		return menuBuffer.toString();
	}

	// /////////////////////////////////// 操作员管理
	// /////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 分页列出操作员信息，并可按登录名获姓名进行查询.
	 * 
	 * @return listPmsOperator or operateError .
	 * 
	 */
	@Permission("pms:operator:view")
	public String listPmsOperator() {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>(); // 业务条件查询参数
			paramMap.put("loginName", getString("loginName")); // 操作员登录名（精确查询）
			paramMap.put("realName", getString("realName")); // 操作员姓名（模糊查询）
			paramMap.put("status", getInteger("status")); // 状态

			super.pageBean = pmsOperatorBiz.listPage(getPageParam(), paramMap);
			super.pushData(pageBean);
			PmsOperator pmsOperator = getLoginedOperator();// 获取当前登录操作员对象
			super.putData("currLoginName", pmsOperator.getLoginName());
			// 回显查询条件值
			super.pushData(paramMap);
			
			super.putData("OperatorStatusEnumList", OperatorStatusEnum.values());
			super.putData("OperatorStatusEnum", OperatorStatusEnum.toMap());
			super.putData("OperatorTypeEnumList", OperatorTypeEnum.values());
			super.putData("OperatorTypeEnum", OperatorTypeEnum.toMap());
			
			return "PmsOperatorList";
		} catch (Exception e) {
			log.error("== listPmsOperator exception:", e);
			return operateError("获取数据失败");
		}
	}

	/**
	 * 查看操作员详情.
	 * 
	 * @return .
	 */
	@Permission("pms:operator:view")
	public String viewPmsOperatorUI() {
		try {
			Long operatorId = getLong("id");
			PmsOperator pmsOperator = pmsOperatorBiz.getById(operatorId);
			if (pmsOperator == null) {
				return operateError("无法获取要查看的数据");
			}

			// 普通操作员没有查看超级管理员的权限
			// if ("0".equals(this.getLoginedOperator().getType()) &&
			// "1".equals(pmsOperator.getType())) {
			// return operateError("权限不足");
			// }

			super.pushData(pmsOperator);
			// 准备角色列表
			super.putData("rolesList", pmsRoleBiz.listAllRole());
			
			// 准备该用户拥有的角色ID字符串
			List<PmsRoleOperator> lisPmsRoleOperators = pmsOperatorBiz.listRoleOperatorByOperatorId(operatorId);
			StringBuffer owenedRoleIdBuffer = new StringBuffer("");
			for (PmsRoleOperator pmsRoleOperator : lisPmsRoleOperators) {
				owenedRoleIdBuffer.append(pmsRoleOperator.getRoleId());
				owenedRoleIdBuffer.append(",");
			}
			String owenedRoleIds = owenedRoleIdBuffer.toString();
			if (StringUtils.isNotBlank(owenedRoleIds) && owenedRoleIds.length() > 0) {
				owenedRoleIds = owenedRoleIds.substring(0, owenedRoleIds.length() - 1);
			}
			super.putData("owenedRoleIds", owenedRoleIds);
			return "PmsOperatorView";
		} catch (Exception e) {
			log.error("== viewPmsOperatorUI exception:", e);
			return operateError("获取数据失败");
		}
	}

	/**
	 * 转到添加操作员页面 .
	 * 
	 * @return addPmsOperatorUI or operateError .
	 */
	@Permission("pms:operator:add")
	public String addPmsOperatorUI() {
		try {
			
			super.putData("rolesList", pmsRoleBiz.listAllRole());
			super.putData("OperatorStatusEnumList", OperatorStatusEnum.values());
			super.putData("RoleTypeEnum", RoleTypeEnum.toMap());
			return "PmsOperatorAdd";
		} catch (Exception e) {
			log.error("== addPmsOperatorUI exception:", e);
			return operateError("获取角色列表数据失败");
		}
	}

	/**
	 * 保存一个操作员
	 * 
	 */
	@Permission("pms:operator:add")
	public String addPmsOperator() {
		try {
			String loginPwd = getString("loginPwdss"); // 初始登录密码

			String loginName = getString("loginNamess");

			PmsOperator pmsOperator = new PmsOperator();
			pmsOperator.setRealName(getString("realName")); // 姓名
			pmsOperator.setLoginName(loginName); // 登录名
			pmsOperator.setLoginPwd(loginPwd);
			pmsOperator.setRemark(getString("desc")); // 描述
			pmsOperator.setIsChangedPwd(false);
			pmsOperator.setLastLoginTime(null);
			pmsOperator.setMobileNo(getString("mobileNo")); // 手机号码
			pmsOperator.setStatus(getInteger("status")); // 状态（100:'激活',101:'冻结'1）
			pmsOperator.setType(String.valueOf(OperatorTypeEnum.USER.getValue())); // 类型（ "0":'普通操作员',"1":'超级管理员'），只能添加普通操作员

			String roleOperatorStr = getRoleOperatorStr();

			// 表单数据校验
			String validateMsg = validatePmsOperator(pmsOperator, roleOperatorStr);

			// if (!loginPwdFormat(loginPwd)) {
			// return operateError("登录密码必须由字母、数字、特殊符号组成");
			// }

			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg); // 返回错误信息
			}

			// 校验操作员登录名是否已存在
			PmsOperator loginNameCheck = pmsOperatorBiz.findOperatorByLoginName(loginName);
			if (loginNameCheck != null) {
				return operateError("登录名【" + loginName + "】已存在");
			}

			pmsOperator.setLoginPwd(DigestUtils.sha1Hex(loginPwd)); // 存存前对密码进行加密

			pmsOperatorBiz.saveOperator(pmsOperator, roleOperatorStr);

			String roleNames = theRolesChangeNames(roleOperatorStr);

			super.logSave("添加操作员[" + loginName + "]，关联角色[" + roleNames + "]");

			return operateSuccess();
		} catch (Exception e) {
			log.error("== addPmsOperator exception:", e);
			return operateError("保存操作员信息失败");
		}
	}

	/***
	 * 把角色的ID转成NAME
	 * 
	 * @param roleOperatorStr
	 * @return
	 */
	private String theRolesChangeNames(String roleOperatorStr) {
		if (StringUtils.isEmpty(roleOperatorStr))
			return null;
		StringBuffer menuBuffer = new StringBuffer(); // 追加菜单的名称
		int roleNum = roleOperatorStr.indexOf(",");
		if (roleNum <= 0) {
			PmsRole role = pmsRoleBiz.getById(Long.valueOf(roleOperatorStr));
			menuBuffer.append(role.getRoleName());
		} else {
			String[] roleArray = roleOperatorStr.split(",");
			for (int i = 0; i < roleArray.length; i++) {
				PmsRole role = pmsRoleBiz.getById(Long.valueOf(roleArray[i]));
				if (i == roleArray.length - 1) {
					menuBuffer.append(role.getRoleName());
				} else {
					menuBuffer.append(role.getRoleName()).append(",");
				}
			}
		}
		return menuBuffer.toString();
	}

	/**
	 * 验证输入的邮箱格式是否符合
	 * 
	 * @param email
	 * @return 是否合法
	 */
	public static boolean emailFormat(String email) {
		// boolean tag = true;
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		boolean result = Pattern.matches(check, email);
		return result;
	}

	/**
	 * 验证输入的密码格式是否符合
	 * 
	 * @param loginPwd
	 * @return 是否合法
	 */
	public static boolean loginPwdFormat(String loginPwd) {
		return loginPwd.matches(".*?[^a-zA-Z\\d]+.*?") && loginPwd.matches(".*?[a-zA-Z]+.*?") && loginPwd.matches(".*?[\\d]+.*?");
	}

	/**
	 * 验证输入的操作员姓名格式是否符合
	 * 
	 * @param loginPwd
	 * @return 是否合法
	 */
	public static boolean realNameFormat(String realName) {
		return realName.matches("[^\\x00-\\xff]+");
	}

	/**
	 * 校验Pms操作员表单数据.
	 * 
	 * @param PmsOperator
	 *            操作员信息.
	 * @param roleOperatorStr
	 *            关联的角色ID串.
	 * @return
	 */
	private String validatePmsOperator(PmsOperator operator, String roleOperatorStr) {
		String msg = ""; // 用于存放校验提示信息的变量
		msg += lengthValidate("真实姓名", operator.getRealName(), true, 2, 15);
		msg += lengthValidate("登录名", operator.getLoginName(), true, 3, 50);

		/*
		 * String specialChar = "`!@#$%^&*()_+\\/"; if
		 * (operator.getLoginName().contains(specialChar)) { msg +=
		 * "登录名不能包含特殊字符，"; }
		 */
		if (!realNameFormat(operator.getRealName())) {
			msg += "操作员姓名必须为中文！";
		}

//		if (!emailFormat(operator.getLoginName())) {
//			msg += "账户名格式必须为邮箱地址！";
//		}
		
		// 登录密码
		String loginPwd = operator.getLoginPwd();
		String loginPwdMsg = lengthValidate("登录密码", loginPwd, true, 6, 50);
		/*
		 * if (StringUtils.isBlank(loginPwdMsg) &&
		 * !ValidateUtils.isAlphanumeric(loginPwd)) { loginPwdMsg +=
		 * "登录密码应为字母或数字组成，"; }
		 */
		msg += loginPwdMsg;

		// 手机号码
		String mobileNo = operator.getMobileNo();
		String mobileNoMsg = lengthValidate("手机号", mobileNo, true, 0, 12);
		if (StringUtils.isBlank(mobileNoMsg) && !ValidateUtils.isMobile(mobileNo)) {
			mobileNoMsg += "手机号格式不正确，";
		}
		msg += mobileNoMsg;

		// 状态
		Integer status = operator.getStatus();
		if (status == null) {
			msg += "请选择状态，";
		} else if (status.intValue() < 100 || status.intValue() > 101) {
			msg += "状态值不正确，";
		}

		msg += lengthValidate("描述", operator.getRemark(), true, 3, 100);

		// 新增操作员的权限不能为空，为空没意义
		if (StringUtils.isBlank(roleOperatorStr) && operator.getId() == null) {
			msg += "操作员关联的角色不能为空";
		}
		return msg;
	}

	/**
	 * 删除操作员
	 * 
	 * @return
	 * */
	@Permission("pms:operator:delete")
	public String deleteOperatorStatus() {
		long id = getLong("id");
		PmsOperator pmsOperator = pmsOperatorBiz.getById(id); // 查询操作员信息
		pmsOperatorBiz.deleteOperatorById(id);
		super.logDelete("删除操作员.操作员登录名[" + pmsOperator.getLoginName() + "]");
		return this.operateSuccess("操作成功");
	}

	/**
	 * 转到修改操作员界面
	 * 
	 * @return PmsOperatorEdit or operateError .
	 */
	@Permission("pms:operator:edit")
	public String editPmsOperatorUI() {
		try {
			Long id = getLong("id");
			PmsOperator pmsOperator = pmsOperatorBiz.getById(id);
			if (pmsOperator == null) {
				return operateError("无法获取要修改的数据");
			}

			// 普通操作员没有修改超级管理员的权限
			if (OperatorTypeEnum.USER.getValue().equals(this.getLoginedOperator().getType()) 
			 && OperatorTypeEnum.ADMIN.getValue().equals(pmsOperator.getType())) {
				return operateError("权限不足");
			}

			super.pushData(pmsOperator);
			// 准备角色列表
			super.putData("rolesList", pmsRoleBiz.listAllRole());
			
			// 准备该用户拥有的角色ID字符串
			List<PmsRoleOperator> lisPmsRoleOperators = pmsOperatorBiz.listRoleOperatorByOperatorId(id);
			StringBuffer owenedRoleIdBuffer = new StringBuffer("");
			for (PmsRoleOperator pmsRoleOperator : lisPmsRoleOperators) {
				owenedRoleIdBuffer.append(pmsRoleOperator.getRoleId());
				owenedRoleIdBuffer.append(",");
			}
			String owenedRoleIds = owenedRoleIdBuffer.toString();
			if (StringUtils.isNotBlank(owenedRoleIds) && owenedRoleIds.length() > 0) {
				owenedRoleIds = owenedRoleIds.substring(0, owenedRoleIds.length() - 1);
			}
			super.putData("owenedRoleIds", owenedRoleIds);
			
			super.putData("OperatorStatusEnum", OperatorStatusEnum.toMap());
			super.putData("OperatorTypeEnum", OperatorTypeEnum.toMap());
			super.putData("RoleTypeEnum", RoleTypeEnum.toMap());
			
			return "PmsOperatorEdit";
		} catch (Exception e) {
			log.error("== editPmsOperatorUI exception:", e);
			return operateError("获取修改数据失败");
		}
	}

	/**
	 * 保存修改后的操作员信息
	 * 
	 * @return operateSuccess or operateError .
	 */
	@Permission("pms:operator:edit")
	public String editPmsOperator() {
		try {
			Long id = getLong("id");

			PmsOperator pmsOperator = pmsOperatorBiz.getById(id);
			if (pmsOperator == null) {
				return operateError("无法获取要修改的操作员信息");
			}

			// 普通操作员没有修改超级管理员的权限
			if ("0".equals(this.getLoginedOperator().getType()) && "1".equals(pmsOperator.getType())) {
				return operateError("权限不足");
			}

			pmsOperator.setRemark(getString("remark"));
			pmsOperator.setMobileNo(getString("mobileNo"));
			pmsOperator.setRealName(getString("realName"));
			// 修改时不能修状态
			// pmsOperator.setStatus(getInteger("status"));

			String roleOperatorStr = getRoleOperatorStr();
			String newStr = "";
			StringBuffer oldRoleNameBuffer = new StringBuffer();
			// 查询操作员原有的角色
			List<PmsRoleOperator> list = pmsOperatorBiz.listRoleOperatorByOperatorId(id);
			for (PmsRoleOperator ro : list) {
				if (newStr == null || "".equals(newStr) ) {
					newStr += ro.getRoleId();
				} else {
					newStr += "," + ro.getRoleId();
				}
				PmsRole role = pmsRoleBiz.getById(ro.getRoleId());
				oldRoleNameBuffer.append(role.getRoleName()).append(",");
			}

			// StringBuffer newRoleNameBuffer = new StringBuffer();
			// String[] newRoleIdList = roleOperatorStr.split(",");
			// for (String roleId : newRoleIdList) {
			// PmsRole role = pmsRoleBiz.getById(Long.valueOf(roleId));
			// newRoleNameBuffer.append(role.getRoleName()).append(",");
			// }
			String newRoleNames = theRolesChangeNames(roleOperatorStr);

			// 表单数据校验
			String validateMsg = validatePmsOperator(pmsOperator, roleOperatorStr);
			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg); // 返回错误信息
			}

			pmsOperatorBiz.updateOperator(pmsOperator, roleOperatorStr);
			super.logEdit("修改操作员[" + pmsOperator.getLoginName() + "]，更改前角色[" + oldRoleNameBuffer + "]，更改后角色[" + newRoleNames + "]");
			return operateSuccess();
		} catch (Exception e) {
			log.error("== editPmsOperator exception:", e);
			return operateError("更新操作员信息失败");
		}
	}

	/**
	 * 根据ID冻结或激活操作员.
	 * 
	 * @return operateSuccess or operateError .
	 */
	@Permission("pms:operator:edit")
	public String changeOperatorStatus() {
		try {
			Long operatorId = getLong("id");
			PmsOperator operator = pmsOperatorBiz.getById(operatorId);
			if (operator == null) {
				return operateError("无法获取要操作的数据");
			}

			if (this.getLoginedOperator().getId() == operatorId) {
				return operateError("不能修改自己账户的状态");
			}

			// 普通操作员没有修改超级管理员的权限
			if ("0".equals(this.getLoginedOperator().getType()) && "1".equals(operator.getType())) {
				return operateError("你没有修改超级管理员的权限");
			}

			// 2014-01-02,由删除改为修改状态
			// pmsPermissionBiz.deleteOperator(id);
			// 激活的变冻结，冻结的则变激活
			if (operator.getStatus().intValue() == OperatorStatusEnum.ACTIVE.getValue()) {
				if ("1".equals(operator.getType())) {
					return operateError("【" + operator.getLoginName() + "】为超级管理员，不能冻结");
				}
				operator.setStatus(OperatorStatusEnum.INACTIVE.getValue());
				pmsOperatorBiz.update(operator);
				super.logEdit("冻结操作员[" + operator.getLoginName() + "]");
			} else {
				operator.setStatus(OperatorStatusEnum.ACTIVE.getValue());
				operator.setPwdErrorCount(0);
				pmsOperatorBiz.update(operator);
				super.logEdit("激活操作员[" + operator.getLoginName() + "]");
			}
			return operateSuccess();
		} catch (Exception e) {
			log.error("== changeOperatorStatus exception:", e);
			return operateError("删除操作员失败:" + e.getMessage());
		}
	}

	/***
	 * 重置操作员的密码（注意：不是修改当前登录操作员自己的密码） .
	 * 
	 * @return
	 */
	@Permission("pms:operator:resetpwd")
	public String resetOperatorPwdUI() {
		PmsOperator operator = pmsOperatorBiz.getById(getLong("id"));
		if (operator == null) {
			return operateError("无法获取要重置的信息");
		}

		// 普通操作员没有修改超级管理员的权限
		if ("0".equals(this.getLoginedOperator().getType()) && "1".equals(operator.getType())) {
			return operateError("你没有修改超级管理员的权限");
		}

		super.putData("operatorId", operator.getId());
		super.pushData(operator);

		return "PmsOperatorResetPwd";
	}

	/**
	 * 重置操作员密码.
	 * 
	 * @return
	 */
	@Permission("pms:operator:resetpwd")
	public String resetOperatorPwd() {
		try {
			Long operatorId = getLong("operatorId");
			PmsOperator operator = pmsOperatorBiz.getById(operatorId);
			if (operator == null) {
				return operateError("无法获取要重置密码的操作员信息");
			}

			// 普通操作员没有修改超级管理员的权限
			if ("0".equals(this.getLoginedOperator().getType()) && "1".equals(operator.getType())) {
				return operateError("你没有修改超级管理员的权限");
			}

			String newPwd = getString("newPwd");
			String newPwd2 = getString("newPwd2");

			if (!loginPwdFormat(newPwd)) {
				return operateError("登录密码必须由字母、数字、特殊符号组成");
			}

			String validateMsg = validatePassword(newPwd, newPwd2);
			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg); // 返回错误信息
			}
			
			pmsOperatorBiz.updateOperatorPwd(operatorId, DigestUtils.sha1Hex(newPwd), false);

			super.logEdit("重置操作员[" + operator.getLoginName() + "]的密码");
			return operateSuccess();
		} catch (Exception e) {
			log.error("== resetOperatorPwd exception:", e);
			return operateError("密码重置出错:" + e.getMessage());
		}
	}

	/**
	 * 进入重置当前登录操作员自己的密码的页面.
	 * 
	 * @return
	 */
	public String operatorChangeOwnPwdUI() {
		return "PmsOperatorChangeOwnPwd";
	}

	/**
	 * 重置当前登录操作员自己的密码.
	 * 
	 * @return
	 */
	public String operatorChangeOwnPwd() {
		try {

			PmsOperator operator = this.getLoginedOperator();
			if (operator == null) {
				return operateError("无法从会话中获取操作员信息");
			}

			// 判断旧密码是否正确
			String oldPwd = getString("oldPwd");
			if (StringUtils.isBlank(oldPwd)) {
				return operateError("请输入旧密码");
			}
			// 旧密码要判空，否则sha1Hex会出错
			if (!operator.getLoginPwd().equals(DigestUtils.sha1Hex(oldPwd))) {
				return operateError("旧密码不正确");
			}

			// 校验新密码
			String newPwd = getString("newPwd");
			if (oldPwd.equals(newPwd)) {
				return operateError("新密码不能与旧密码相同");
			}

			if (!loginPwdFormat(newPwd)) {
				return operateError("登录密码必须由字母、数字、特殊符号组成");
			}

			String newPwd2 = getString("newPwd2");
			String validateMsg = validatePassword(newPwd, newPwd2);
			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg); // 返回错误信息
			}

			// 更新密码
			pmsOperatorBiz.updateOperatorPwd(operator.getId(), DigestUtils.sha1Hex(newPwd), true);

			// 修改密码成功后要清空session，以强制重新登录
			// getSessionMap().remove(ConstantSession.OPERATOR_SESSION_KEY);
			// getSessionMap().remove(ConstantSession.ACTIONS_SESSION_KEY);
			// getSessionMap().clear();
			
			super.logEdit("修改了自己的密码,登录名[" + operator.getLoginName() + "]");

			return operateSuccess("密码修改成功，请重新登录!");
		} catch (Exception e) {
			log.error("== operatorChangeOwnPwd exception:", e);
			super.logEditError("修改了自己的密码出错:" + e.getMessage());
			return operateError("修改密码出错:" + e.getMessage());
		}
	}

	/**
	 * 当前登录的操作员查看自己帐号的详细信息.
	 * 
	 * @return
	 */
	public String operatorViewOwnInfo() {
		try {

			PmsOperator pmsOperator = this.getLoginedOperator();
			if (pmsOperator == null) {
				return operateError("无法从会话中获取操作员信息");
			}

			PmsOperator operator = pmsOperatorBiz.getById(pmsOperator.getId());
			if (operator == null) {
				return operateError("无法获取操作员信息");
			}

			super.pushData(operator);

			return "PmsOperatorViewOwnInfo";
		} catch (Exception e) {
			log.error("== editPmsOperator exception:", e);
			return operateError("无法获取要修改的操作员信息失败");
		}
	}

	/**
	 * 得到角色和操作员关联的ID字符串
	 * 
	 * @return
	 */
	private String getRoleOperatorStr() throws Exception {
		String roleStr = getString("selectVal");
		if (StringUtils.isNotBlank(roleStr) && roleStr.length() > 0) {
			roleStr = roleStr.substring(0, roleStr.length() - 1);
		}
		return roleStr;
	}

	/***
	 * 验证重置密码
	 * 
	 * @param newPwd
	 * @param newPwd2
	 * @return
	 */
	private String validatePassword(String newPwd, String newPwd2) {
		String msg = ""; // 用于存放校验提示信息的变量
		if (StringUtils.isBlank(newPwd)) {
			msg += "新密码不能为空，";
		} else if (newPwd.length() < 6) {
			msg += "新密码不能少于6位长度，";
		}

		if (!newPwd.equals(newPwd2)) {
			msg += "两次输入的密码不一致";
		}
		return msg;
	}
}
