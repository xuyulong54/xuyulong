package wusc.edu.pay.facade.pms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.pms.biz.PmsActionBiz;
import wusc.edu.pay.core.pms.biz.PmsMenuBiz;
import wusc.edu.pay.core.pms.biz.PmsOperatorBiz;
import wusc.edu.pay.core.pms.biz.PmsOperatorLogBiz;
import wusc.edu.pay.core.pms.biz.PmsRoleBiz;
import wusc.edu.pay.facade.pms.entity.PmsAction;
import wusc.edu.pay.facade.pms.entity.PmsMenu;
import wusc.edu.pay.facade.pms.entity.PmsOperator;
import wusc.edu.pay.facade.pms.entity.PmsOperatorLog;
import wusc.edu.pay.facade.pms.entity.PmsRole;
import wusc.edu.pay.facade.pms.enums.PmsOperatorLogStatusEnum;
import wusc.edu.pay.facade.pms.enums.PmsOperatorLogTypeEnum;
import wusc.edu.pay.facade.pms.exception.PermissionException;
import wusc.edu.pay.facade.pms.service.PmsFacade;


/**
 * 
 * @描述: 权限服务接口实现类.
 * @作者: WuShuicheng.
 * @创建: 2014-11-26,上午10:04:30
 * @版本: V1.0
 *
 */
@Component("pmsFacade")
public class PmsFacadeImpl implements PmsFacade {
	
	@Autowired
	private PmsActionBiz pmsActionBiz;
	@Autowired
	private PmsMenuBiz pmsMenuBiz;
	@Autowired
	private PmsRoleBiz pmsRoleBiz;
	@Autowired
	private PmsOperatorBiz pmsOperatorBiz;
	@Autowired
	private PmsOperatorLogBiz pmsOperatorLogBiz;
	

	@Override
	public PmsOperator getOperatorByLoginName(String loginName) {
		return pmsOperatorBiz.getOperatorByLoginName(loginName);
	}

	@Override
	public void updateOperator(PmsOperator operator) {
		pmsOperatorBiz.update(operator);
	}

	@Override
	public String getRoleIdsByOperatorId(Long operatorId) {
		return pmsRoleBiz.getRoleIdsByOperatorId(operatorId);
	}

	@Override
	public String getActionIdsByRoleIds(String roleIds) {
		return pmsActionBiz.getActionIdsByRoleIds(roleIds);
	}

	@Override
	public List<PmsAction> findActionsByIdStr(String actionIds) {
		return pmsActionBiz.findActionsByIdStr(actionIds);
	}

	@Override
	public String buildPermissionTree(String roleIds) throws PermissionException {
		return pmsMenuBiz.buildPermissionTree(roleIds);
	}

	@Override
	public String getPmsTreeMenuToManage(String actionUrl) {
		return pmsMenuBiz.getPmsTreeMenuToManage(actionUrl);
	}

	@Override
	public PmsMenu getMenuById(Long menuId) {
		return pmsMenuBiz.getById(menuId);
	}

	@Override
	public List<PmsMenu> getMenuByNameAndIsLeaf(Map<String, Object> map) {
		return pmsMenuBiz.getMenuByNameAndIsLeaf(map);
	}

	@Override
	public void createMenu(PmsMenu menu) throws PermissionException {
		pmsMenuBiz.createMenu(menu);
	}

	@Override
	public void updateMenu(PmsMenu menu) {
		pmsMenuBiz.update(menu);
	}

	@Override
	public List<PmsMenu> listMenuByParentId(Long menuId) {
		return pmsMenuBiz.listByParentId(menuId);
	}

	@Override
	public List<PmsAction> listActionByMenuId(Long menuId) {
		return pmsActionBiz.listByMenuId(menuId);
	}

	@Override
	public void deleteMenu(Long menuId) {
		pmsMenuBiz.delete(menuId);
	}

	@Override
	public PageBean listOperaotrLogForPage(PageParam pageParam, Map<String, Object> paramMap) {
		return pmsOperatorLogBiz.listPage(pageParam, paramMap);
	}

	@Override
	public PmsOperatorLog getOperatorLogById(Long id) {
		return pmsOperatorLogBiz.getById(id);
	}

	@Override
	public PmsOperator getOperatorById(Long operatorId) {
		return pmsOperatorBiz.getById(operatorId);
	}

	@Override
	public PageBean listActionForPage(PageParam pageParam, Map<String, Object> paramMap) {
		return pmsActionBiz.listPage(pageParam, paramMap);
	}

	@Override
	public PmsAction getActionByActionName(String actionName) {
		return pmsActionBiz.getByActionName(actionName);
	}

	@Override
	public PmsAction getActionByAction(String action) {
		return pmsActionBiz.getByAction(action);
	}

	@Override
	public void saveAction(PmsAction action) {
		pmsActionBiz.saveAction(action);
		
	}

	@Override
	public String buildLookUpMenu() {
		return pmsMenuBiz.buildLookUpMenu();
	}

	@Override
	public PmsAction getActionById(Long id) {
		return pmsActionBiz.getById(id);
	}

	@Override
	public PmsAction getActionByActionNameNotEqId(String actionName, Long id) {
		return pmsActionBiz.getByActionNameNotEqId(actionName, id);
	}

	@Override
	public void updateAction(PmsAction pmsAction) {
		pmsActionBiz.updateAction(pmsAction);
	}

	@Override
	public List<PmsRole> listRoleByActionId(Long actionId) {
		return pmsRoleBiz.listByActionId(actionId);
	}

	@Override
	public void deleteActionById(Long actionId) {
		pmsActionBiz.deleteById(actionId);
	}

	@Override
	public PageBean listRoleForPage(PageParam pageParam, Map<String, Object> paramMap) {
		return pmsRoleBiz.listPage(pageParam, paramMap);
	}

	@Override
	public PmsRole getRoleByRoleNameAndUserNo(String roleName, String userNo) {
		return pmsRoleBiz.getByRoleNameAndUserNo(roleName, userNo);
	}

	@Override
	public void saveRole(PmsRole pmsRole) {
		pmsRoleBiz.saveRole(pmsRole);
	}

	@Override
	public PmsRole getRoleById(Long roleId) {
		return pmsRoleBiz.getById(roleId);
	}

	@Override
	public PmsRole getRoleByRoleNameAndUserNoNotEqId(String roleName, String userNo, Long id) {
		return pmsRoleBiz.getByRoleNameAndUserNoNotEqId(roleName, userNo, id);
	}

	@Override
	public void updateRole(PmsRole pmsRole) {
		pmsRoleBiz.update(pmsRole);
	}

	@Override
	public int countOperatorByRoleId(Long roleId) {
		return pmsOperatorBiz.countOperatorByRoleId(roleId);
	}

	@Override
	public void deleteRoleById(Long roleId) {
		pmsRoleBiz.deleteRoleById(roleId);
		
	}

	@Override
	public String buildMenuActionTree(Long roleId) {
		return pmsMenuBiz.buildMenuActionTree(roleId);
	}

	@Override
	public List<PmsOperator> listOperatorByRoleId(Long roleId) {
		return pmsOperatorBiz.listOperatorByRoleId(roleId);
	}

	@Override
	public void assignPermission(Long roleId, String menuIds, String actionIds) throws PermissionException {
		pmsMenuBiz.assignPermission(roleId, menuIds, actionIds);
	}

	@Override
	public PageBean listOpertaorForPage(PageParam pageParam, Map<String, Object> paramMap) {
		return pmsOperatorBiz.listPage(pageParam, paramMap);
	}

	@Override
	public List<PmsRole> listRoleByRoleTypeAndUserNo(String roleType, String userNo) {
		return pmsRoleBiz.listByRoleTypeAndUserNo(roleType, userNo);
	}

	@Override
	public void saveOperator(PmsOperator pmsOperator, String roleOperatorStr) {
		pmsOperatorBiz.saveOperator(pmsOperator, roleOperatorStr);
	}

	@Override
	public void deleteOperatorById(long operatorId) {
		pmsOperatorBiz.deleteOperatorById(operatorId);
	}

	@Override
	public void updateOperator(PmsOperator pmsOperator, String roleOperatorStr) {
		pmsOperatorBiz.updateOperator(pmsOperator, roleOperatorStr);
	}

	@Override
	public void updateOperatorPwd(Long operatorId, String newPwd, boolean isChangedPwd) {
		pmsOperatorBiz.updateOperatorPwd(operatorId, newPwd, isChangedPwd);
		
	}

	/**
	 * 创建操作员操作记录.
	 * @param logTypeEnum 操作日志类型.
	 * @param logStatusEnum 操作日志状态.
	 * @param content 要记录的日志内容.
	 * @param loginedOperator 操作员信息.
	 * @param ipAddr IP地址.
	 */
	@Override
	public void createOperatorLog(PmsOperatorLogTypeEnum logTypeEnum, PmsOperatorLogStatusEnum logStatusEnum, String content, PmsOperator loginedOperator, String ipAddr) {
		pmsOperatorLogBiz.createOperatorLog(logTypeEnum, logStatusEnum, content, loginedOperator, ipAddr);
		
	}

}
