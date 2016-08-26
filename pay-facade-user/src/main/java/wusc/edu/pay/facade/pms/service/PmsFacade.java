package wusc.edu.pay.facade.pms.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.pms.entity.PmsAction;
import wusc.edu.pay.facade.pms.entity.PmsMenu;
import wusc.edu.pay.facade.pms.entity.PmsOperator;
import wusc.edu.pay.facade.pms.entity.PmsOperatorLog;
import wusc.edu.pay.facade.pms.entity.PmsRole;
import wusc.edu.pay.facade.pms.enums.PmsOperatorLogStatusEnum;
import wusc.edu.pay.facade.pms.enums.PmsOperatorLogTypeEnum;
import wusc.edu.pay.facade.pms.exception.PermissionException;


/**
 * 
 * @描述: 权限服务接口.
 * @作者: WuShuicheng.
 * @创建: 2014-11-26,上午10:03:09
 * @版本: V1.0
 *
 */
public interface PmsFacade {

	/**
	 * 根据登录名取得操作员对象.
	 * @param loginName 操作员登录名.
	 * @return PmsOperator.
	 */
	PmsOperator getOperatorByLoginName(String loginName);

	/**
	 * 更新操作员信息.
	 * @param operator
	 */
	void updateOperator(PmsOperator operator);

	/**
	 * 根据操作员ID获得该操作员的所有角色id所拼成的String，每个ID用“,”分隔
	 * @param operatorId 操作员ID.
	 * @return roleIds 多个角色的ID串.
	 */
	String getRoleIdsByOperatorId(Long operatorId);

	/**
	 * 根据角色ID集得到所有权限ID集
	 * @param roleIds
	 * @return actionIds
	 */
	String getActionIdsByRoleIds(String roleIds);

	/**
	 * 根据Action的id字符串得到相应的权限列表
	 */
	List<PmsAction> findActionsByIdStr(String actionIds);

	/**
	 * 根据操作员拥有的角色ID,构建管理后台的树形权限功能菜单
	 * @param roleIds
	 * @return
	 * @throws PermissionException
	 */
	String buildPermissionTree(String roleIds) throws PermissionException;

	/**
	 * 获取用于权限菜单管理时的树.
	 * @param editMenuAction 编辑菜单的ActionURL.
	 */
	String getPmsTreeMenuToManage(String editMenuAction);

	/**
	 * 根据菜单ID获取菜单.
	 * @param menuId 菜单ID .
	 * @return PmsMenu .
	 */
	PmsMenu getMenuById(Long menuId);

	/***
	 * 根据名称和是否叶子节点查询数据
	 * 
	 * @param isLeaf
	 *            是否是叶子节点
	 * @param name
	 *            节点名称
	 * @return
	 */
	List<PmsMenu> getMenuByNameAndIsLeaf(Map<String, Object> map);

	/**
	 * 新建菜单.
	 * @param model
	 * @throws PermissionException
	 */
	void createMenu(PmsMenu menu) throws PermissionException;

	/**
	 * 更新菜单.
	 * @param menu
	 */
	void updateMenu(PmsMenu menu);

	/**
	 * 根据菜单ID查找菜单（可用于判断菜单下是否还有子菜单）.
	 * 
	 * @param parentId
	 *            .
	 * @return menuList.
	 */
	List<PmsMenu> listMenuByParentId(Long menuId);

	/**
	 * 根据菜单ID查找权限集.
	 * 
	 * @param menuId
	 *            菜单ID.
	 * @return .
	 */
	List<PmsAction> listActionByMenuId(Long menuId);

	/**
	 * 根据ID删除菜单.
	 * @param menuId .
	 */
	void deleteMenu(Long menuId);

	/**
	 * 分页查询操作员操作日志信息.
	 * 
	 * @param pageParam
	 *            分页参数.
	 * @param paramMap
	 *            查询参数.
	 * @return
	 */
	PageBean listOperaotrLogForPage(PageParam pageParam, Map<String, Object> paramMap);

	/**
	 * 根据ID获取操作员操作日志信息.
	 * 
	 * @param id
	 *            .
	 * @return PmsOperatorLog.
	 */
	PmsOperatorLog getOperatorLogById(Long id);

	/**
	 * 根据ID获取操作员信息.
	 * @param operatorId
	 * @return
	 */
	PmsOperator getOperatorById(Long operatorId);

	/**
	 * 查询并分页列出权限功能点.
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	PageBean listActionForPage(PageParam pageParam, Map<String, Object> paramMap);

	/**
	 * 根据权限名称查找权限（用于判断权限名是否已存在）.
	 * 
	 * @param actionName
	 *            .
	 * @return PmsAction.
	 */
	PmsAction getActionByActionName(String actionName);

	/**
	 * 根据权限查找权限记录（用于判断权限是否已存在）.
	 * 
	 * @param action
	 *            .
	 * @return PmsAction.
	 */
	PmsAction getActionByAction(String action);

	/**
	 * 保存权限功能点
	 * @param act
	 */
	void saveAction(PmsAction action);

	/**
	 * 添加或修改权限时，查找带回权限要关联的菜单ID.
	 * @return
	 */
	String buildLookUpMenu();

	/**
	 * 根据权限ID获取权限功能点信息.
	 * @param id
	 * @return
	 */
	PmsAction getActionById(Long id);

	/**
	 * 检查修改后的权限名是否会与其他权限名冲突.
	 * 
	 * @param actionName
	 * @param id
	 * @return PmsAction.
	 */
	PmsAction getActionByActionNameNotEqId(String actionName, Long id);

	/**
	 * 更新权限功能点.
	 * @param pmsAction
	 */
	void updateAction(PmsAction pmsAction);

	/**
	 * 根据权限ID找出关联了此权限的角色.
	 * 
	 * @param actionId
	 *            .
	 * @return roleList.
	 */
	List<PmsRole> listRoleByActionId(Long actionId);

	/**
	 * 根据ID删除权限信息.
	 */
	void deleteActionById(Long actionId);

	/**
	 * 查询并分页列出角色信息.
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	PageBean listRoleForPage(PageParam pageParam, Map<String, Object> paramMap);

	/**
	 * 根据角色名称、用户编号获取角色记录（用于判断角色名是否已存在）.
	 * @param roleName 角色名.
	 * @param userNo 用户编号.
	 * @return PmsRole.
	 */
	PmsRole getRoleByRoleNameAndUserNo(String roleName, String userNo);

	/**
	 * 保存角色并关联权限.
	 * 
	 * @param pmsRole
	 *            角色信息.
	 */
	void saveRole(PmsRole pmsRole);

	/**
	 * 根据ID获取角色.
	 * @param id
	 * @return
	 */
	PmsRole getRoleById(Long roleId);

	/**
	 * 查找是否存在与ID值不相同与角色名、用户编号相同的角色记录（用于判断修改的角色名与其他的角色名冲突）。
	 * @param roleName 角色名称.
	 * @param userNo 用户编号.
	 * @param id 角色ID .
	 * @return PmsRole.
	 */
	PmsRole getRoleByRoleNameAndUserNoNotEqId(String roleName, String userNo, Long id);

	/**
	 * 更新角色.
	 * @param pmsRole
	 */
	void updateRole(PmsRole pmsRole);

	/**
	 * 根据角色ID统计有多少个操作员关联到此角色.
	 * 
	 * @param roleId
	 *            .
	 * @return count.
	 */
	int countOperatorByRoleId(Long roleId);

	/**
	 * 根据角色ID删除角色信息（并删除与之关联的权限、菜单、操作员的关联关系）.
	 * @param roleId 角我ID.
	 */
	void deleteRoleById(Long roleId);

	/**
	 * 根据角色ID，生成菜单权限树.
	 * @param roleId 角色ID.
	 * @return treeStr .
	 */
	String buildMenuActionTree(Long roleId);

	/**
	 * 根据角色ID查询关联到此角色的操作员信息.
	 * 
	 * @param roleId 角色ID.
	 * @return operatorList.
	 */
	List<PmsOperator> listOperatorByRoleId(Long roleId);

	/**
	 * 为角色分配权限 .
	 * @param roleId 角色ID.
	 * @param menuIds 菜单ID集.
	 * @param actionIds 权限ID集.
	 */
	void assignPermission(Long roleId, String menuIds, String actionIds) throws PermissionException;

	/**
	 * 查询并分页列出操作员信息.
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	PageBean listOpertaorForPage(PageParam pageParam, Map<String, Object> paramMap);

	/**
	 * 根据角色类型、用户编号来获取角色列表（可用于列出操作员可以关联的角色）.
	 * @param roleType 角色类型. 
	 * @param userNo 用户编号. 
	 * @return roleList.
	 */
	List<PmsRole> listRoleByRoleTypeAndUserNo(String roleType, String userNo);

	/**
	 * 保存操作員信息及其关联的角色.
	 * 
	 * @param pmsOperator
	 *            .
	 * @param roleOperatorStr
	 *            .
	 */
	void saveOperator(PmsOperator pmsOperator, String roleOperatorStr);

	/**
	 * 根据ID删除一个操作员，同时删除与该操作员关联的角色关联信息（注：超级管理员和默认操作员不能删除）.
	 * @param operatorId 操作员ID.
	 */
	void deleteOperatorById(long operatorId);

	/**
	 * 修改操作員信息及其关联的角色.
	 * 
	 * @param pmsOperator
	 *            .
	 * @param roleOperatorStr
	 *            .
	 */
	void updateOperator(PmsOperator pmsOperator, String roleOperatorStr);

	/**
	 * 根据操作员ID更新操作员密码.
	 * @param operatorId 操作员ID.
	 * @param newPwd 新密码(已进行SHA1加密).
	 * @param isChangedPwd 密码是否已修改.
	 */
	void updateOperatorPwd(Long operatorId, String newPwd, boolean isChangedPwd);

	/**
	 * 创建操作员操作记录.
	 * @param logTypeEnum 操作日志类型.
	 * @param logStatusEnum 操作日志状态.
	 * @param content 要记录的日志内容.
	 * @param loginedOperator 操作员信息.
	 * @param ipAddr IP地址.
	 */
	void createOperatorLog(PmsOperatorLogTypeEnum logTypeEnum, PmsOperatorLogStatusEnum logStatusEnum, String content, PmsOperator loginedOperator, String ipAddr);
	

}
