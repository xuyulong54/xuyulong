package wusc.edu.pay.core.pms.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.core.pms.dao.PmsActionDao;
import wusc.edu.pay.core.pms.dao.PmsMenuDao;
import wusc.edu.pay.core.pms.dao.PmsRoleActionDao;
import wusc.edu.pay.core.pms.dao.PmsRoleMenuDao;
import wusc.edu.pay.facade.pms.entity.PmsAction;
import wusc.edu.pay.facade.pms.entity.PmsMenu;
import wusc.edu.pay.facade.pms.entity.PmsRoleAction;
import wusc.edu.pay.facade.pms.entity.PmsRoleMenu;
import wusc.edu.pay.facade.pms.exception.PermissionException;


/**
 * @author System
 * 
 * @since 2013-11-12
 */
@Service("pmsMenuBiz")
public class PmsMenuBiz {
	
	private static final Log log = LogFactory.getLog(PmsMenuBiz.class);
	
	@Autowired
	private PmsMenuDao pmsMenuDao;
	@Autowired
	private PmsActionDao pmsActionDao;
	@Autowired
	private PmsRoleMenuDao pmsRoleMenuDao;
	@Autowired
	private PmsRoleActionDao pmsRoleActionDao;


	/**
	 * 获取用于权限菜单管理时的树.
	 * @param editMenuAction 编辑菜单的ActionURL.
	 */
	@SuppressWarnings("rawtypes")
	public String getPmsTreeMenuToManage(String actionUrl) {
		List treeData = getTreeData(null);
		StringBuffer strJson = new StringBuffer();
		recursionTreeMenu("0", strJson, treeData, actionUrl);
		return strJson.toString();
	}

	/**
	 * 根据父菜单ID获取该菜单下的所有子孙菜单.<br/>
	 * 
	 * @param parentId
	 *            (如果为空，则为获取所有的菜单).<br/>
	 * @return menuList.
	 */
	@SuppressWarnings("rawtypes")
	private List getTreeData(String parentId) {
		return pmsMenuDao.listByParent(parentId);
	}

	/**
	 * 递归输出树形菜单
	 * 
	 * @param pId
	 * @param buffer
	 */
	@SuppressWarnings("rawtypes")
	private void recursionTreeMenu(String pId, StringBuffer buffer, List list, String url) {
		if (pId.equals("0")) {
			buffer.append("<ul class=\"tree treeFolder collapse \" >");
		} else {
			buffer.append("<ul>");
		}
		List<Map> listMap = getSonMenuListByPid(pId, list);
		for (Map map : listMap) {
			String id = map.get("id").toString();// id
			String name = map.get("name").toString();// 名称
			String isLeaf = map.get("isLeaf").toString();// 是否叶子科目
			buffer.append("<li><a onclick=\"onClickMenuNode(" + id + ")\"  href=\"" + url + "?id=" + id + "\" target=\"ajax\" rel=\"jbsxBox\"  value=" + id + ">" + name + "</a>");
			if (!isLeaf.equals("1")) {
				recursionTreeMenu(id, buffer, list, url);
			}
			buffer.append("</li>");
		}
		buffer.append("</ul>");
	}

	/**
	 * 根据(pId)获取(menuList)中的所有子菜单集合.
	 * 
	 * @param pId
	 *            父菜单ID.
	 * @param menuList
	 *            菜单集合.
	 * @return sonMenuList.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> getSonMenuListByPid(String pId, List menuList) {
		List sonMenuList = new ArrayList<Object>();
		for (Object menu : menuList) {
			Map map = (Map) menu;
			if (map != null) {
				String parentId = map.get("pId").toString();// 父id
				if (parentId.equals(pId)) {
					sonMenuList.add(map);
				}
			}
		}
		return sonMenuList;
	}

	/**
	 * 根据ID删除菜单.
	 * @param menuId .
	 */
	public void delete(Long id) {
		this.pmsMenuDao.deleteById(id);
	}

	@SuppressWarnings("rawtypes")
	public List listByRoleIds(String roleIdsStr) {
		return this.pmsMenuDao.listByRoleIds(roleIdsStr);
	}

	/**
	 * 根据操作员拥有的角色ID,构建管理后台的树形权限功能菜单
	 * @param roleIds
	 * @return
	 * @throws PermissionException
	 */
	@SuppressWarnings("rawtypes")
	public String buildPermissionTree(String roleIds) throws PermissionException {
		List treeData = null;
		try {
			treeData = this.pmsMenuDao.listByRoleIds(roleIds);
			if (!StringUtil.isNotNull(treeData)) {
				log.error("用户没有分配菜单权限");
				throw new PermissionException(PermissionException.PERMISSION_USER_NOT_MENU); // 该用户没有分配菜单权限
			}
		} catch (Exception e) {
			log.error("根据角色查询菜单出现错误", e);
			throw new PermissionException(PermissionException.PERMISSION_QUERY_MENU_BY_ROLE_ERROR); // 查询当前角色的
		}
		StringBuffer strJson = new StringBuffer();
		buildAdminPermissionTree("0", strJson, treeData);
		return strJson.toString();
	}

	/**
	 * 构建管理后台的树形权限功能菜单
	 * 
	 * @param pId
	 * @param treeBuf
	 * @param menuList
	 */
	@SuppressWarnings("rawtypes")
	private void buildAdminPermissionTree(String pId, StringBuffer treeBuf, List menuList) {
		
		List<Map> listMap = getSonMenuListByPid(pId.toString(), menuList);
		for (Map map : listMap) {
			String id = map.get("id").toString();// id
			String name = map.get("name").toString();// 名称
			String isLeaf = map.get("isLeaf").toString();// 是否叶子
			String level = map.get("level").toString();// 菜单层级（1、2、3、4）
			String url = map.get("url").toString(); // ACTION访问地址
			String navTabId = "";
			if (StringUtil.isNotNull(map.get("targetName"))) {
				navTabId = map.get("targetName").toString(); // 用于刷新查询页面
			}
			
			if ("1".equals(level)){
				treeBuf.append("<div class='accordionHeader'>");
				treeBuf.append("<h2>" + name + "</h2>");
				treeBuf.append("</div>");
				treeBuf.append("<div class='accordionContent'>");
			}
			
			if ("1".equals(isLeaf)) {
				treeBuf.append("<li><a href='" + url + "' target='navTab' rel='" + navTabId + "'>" + name + "</a></li>");
			} else {
				
				if ("1".equals(level)){
					treeBuf.append("<ul class='tree treeFolder'>");
				}else{
					treeBuf.append("<li><a>" + name + "</a>");
					treeBuf.append("<ul>");
				}
				
				buildAdminPermissionTree(id, treeBuf, menuList);
				
				if ("1".equals(level)){
					treeBuf.append("</ul>");
				}else{
					treeBuf.append("</ul></li>");
				}
				
			}
			
			if ("1".equals(level)){
				treeBuf.append("</div>");
			}
		}

	}

	/**
	 * 新建菜单.
	 * @param model
	 * @throws PermissionException
	 */
	public void createMenu(PmsMenu model) throws PermissionException {
		try {
			PmsMenu newPmsMenu = model;
			PmsMenu parentPmsMenu = newPmsMenu.getParent();
			if (null == parentPmsMenu.getId()) {
				parentPmsMenu = new PmsMenu();
				parentPmsMenu.setId(0L);
				newPmsMenu.setIsLeaf(true);
				newPmsMenu.setLevel(1L);
				newPmsMenu.setParent(parentPmsMenu);
			} else {
				parentPmsMenu = this.pmsMenuDao.getById(parentPmsMenu.getId());
				newPmsMenu.setIsLeaf(true);
				newPmsMenu.setLevel(parentPmsMenu.getLevel() + 1);
				parentPmsMenu.setIsLeaf(false);
				pmsMenuDao.update(parentPmsMenu);
			}
			pmsMenuDao.insert(newPmsMenu);
		} catch (Exception e) {
			log.error("添加菜单报错", e);
			throw new PermissionException("添加菜单SQL报错");
		}
	}

	/**
	 * 根据角色ID，获取菜单ID集合(逗号分隔的菜单ID字符串)
	 * 
	 * @param roleId
	 * @return menuIdStr
	 * @throws Exception
	 */
	private String getMenuIdsByRoleId(Long roleId) throws PermissionException {
		List<PmsRoleMenu> menuList = pmsRoleMenuDao.listByRoleId(roleId);
		StringBuffer menuIds = new StringBuffer("");
		if (menuList != null && !menuList.isEmpty()) {
			for (PmsRoleMenu rm : menuList) {
				menuIds.append(rm.getMenuId()).append(",");
			}
		}
		return menuIds.toString();
	}
	
	/**
	 * 根据角色ID，获取所有的功能权限ID集
	 * 
	 * @param roleId
	 * @return actionIds
	 */
	private String getActionIdsByRoleId(Long roleId) {
		List<PmsRoleAction> rmList = pmsRoleActionDao.listByRoleId(roleId);
		StringBuffer actionIds = new StringBuffer();
		if (rmList != null && !rmList.isEmpty()) {
			for (PmsRoleAction rm : rmList) {
				actionIds.append(rm.getActionId()).append(",");
			}
		}
		return actionIds.toString();
	}

	/**
	 * 根据角色ID，生成菜单权限树
	 * @param roleId 角色ID.
	 * @return treeStr .
	 */
	@SuppressWarnings("rawtypes")
	public String buildMenuActionTree(Long roleId) {
		
		String menuIds = "";
		String actionIds = "";
		try {
			menuIds = getMenuIdsByRoleId(roleId); // 根据角色查找角色对应的菜单ID集
			actionIds = getActionIdsByRoleId(roleId); // 根据角色查找角色对应的功能权限ID集
		} catch (Exception e) {
			log.error("根据角色ID，找不到对应的菜单、权限", e);
		}

		// 前面加个逗号，方便接下来的处理
		menuIds = "," + menuIds;
		actionIds = "," + actionIds;

		List allMenuList = getTreeData(null); // 获取所有的菜单
		StringBuffer treeBuf = new StringBuffer();
		
		buildPermissionTree("0", treeBuf, allMenuList, menuIds, actionIds);
		
		return treeBuf.toString();

	}

	/**
	 * 创建分配权限的菜单树
	 * @param pId
	 * @param treeBuf
	 * @param allMenuList
	 * @param menuIds
	 * @param actionIds
	 */
	@SuppressWarnings("rawtypes")
	private void buildPermissionTree(String pId, StringBuffer treeBuf, List allMenuList, String menuIds, String actionIds) {
		if (pId.equals("0")) {
			treeBuf.append("<ul class=\"tree treeFolder treeCheck expand\" >");
		} else {
			treeBuf.append("<ul>");
		}

		List<Map> sonMenuList = getSonMenuListByPid(pId.toString(), allMenuList);
		for (Map sonMenu : sonMenuList) {
			String menuId = sonMenu.get("id").toString();// id
			String parentId = sonMenu.get("pId").toString(); // PID
			String name = sonMenu.get("name").toString();// 名称
			String isLeaf = sonMenu.get("isLeaf").toString();// 是否叶子
			if (menuIds.indexOf("," + menuId + ",") > -1) {
				treeBuf.append("<li><a menuid='" + menuId + "' checked='true' pid='" + parentId + "' isleaf='" + isLeaf + "'>" + name + " (M)</a>");
			} else {
				treeBuf.append("<li><a menuid='" + menuId + "' pid='" + parentId + "' isleaf='" + isLeaf + "'>" + name + " (M)</a>");
			}
			if (isLeaf.equals("1")) {
				// 如果叶子菜单，则处理挂在此菜单下的权限功能点

				// 获取叶子菜单下所有的功能权限
				List<PmsAction> actionList = pmsActionDao.listAllByMenuId(Long.valueOf(menuId));
				if (null != actionList && !actionList.isEmpty()) {
					treeBuf.append("<ul>");
					for (int j = 0; j < actionList.size(); j++) {
						PmsAction action = actionList.get(j);
						if (actionIds.indexOf("," + action.getId().toString() + ",") > -1) {
							treeBuf.append("<li><a checked='true' actionid='" + action.getId() + "'>" + action.getActionName() + " (A)</a>");
						} else {
							treeBuf.append("<li><a actionid='" + action.getId() + "'>" + action.getActionName() + " (A)</a>");
						}
					}
					treeBuf.append("</ul>");
				}

			} else {
				// 不是叶子菜单，递归
				buildPermissionTree(menuId, treeBuf, allMenuList, menuIds, actionIds);
			}
			treeBuf.append("</li>");
		}

		treeBuf.append("</ul>");
	}

	/**
	 * 为角色分配权限 .
	 * @param roleId 角色ID.
	 * @param menuIds 菜单ID集.
	 * @param actionIds 权限ID集.
	 */
	public void assignPermission(Long roleId, String menuIds, String actionIds) throws PermissionException {
		
		if (!StringUtil.isNotNull(roleId)) {
			throw new PermissionException(PermissionException.PERMISSION_ASSIGN_MENU_ROLE_NULL); // 角色ID为空
		}
		
		// 删除该角色与菜单的所有关联数据
		pmsRoleMenuDao.deleteByRoleId(roleId);
		// 删除该角色与权限的所有关联数据
		pmsRoleActionDao.deleteByRoleId(roleId);
		
		// 重新关联菜单
		List<String> oldMenuIdList = new ArrayList<String>();
		if (StringUtil.isNotNull(menuIds)) {
			String[] menuArray = menuIds.split(",");
			for (String menuId : menuArray) {
				if(!oldMenuIdList.contains(menuId)){
					// 防止重复添加菜单权限
					PmsRoleMenu entity = new PmsRoleMenu();
					entity.setRoleId(roleId);
					entity.setMenuId(Long.valueOf(menuId));
					// 新增菜单权限
					pmsRoleMenuDao.insert(entity);
				}
				oldMenuIdList.add(menuId);
			}
		}		
		
		// 重新关联权限
		if (StringUtil.isNotNull(actionIds)) {
			String[] actionArray = actionIds.split(",");
			for (String actionId : actionArray) {
				PmsRoleAction entity = new PmsRoleAction();
				entity.setRoleId(roleId);
				entity.setActionId(Long.valueOf(actionId));
				// 新增功能权限
				pmsRoleActionDao.insert(entity);
			}
		}
	}

	/**
	 * 添加或修改权限时，查找带回权限要关联的菜单ID.
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String buildLookUpMenu() {
		List treeData = getTreeData(null);
		StringBuffer strJson = new StringBuffer();
		recursionTreeMenuLookUp("0", strJson, treeData);
		return strJson.toString();
	}

	@SuppressWarnings("rawtypes")
	private void recursionTreeMenuLookUp(String pId, StringBuffer buffer, List list) {
		if ("0".equals(pId)) {
			buffer.append("<ul class=\"tree treeFolder\" >");
		} else {
			buffer.append("<ul>");
		}
		List<Map> listMap = getSonMenuListByPid(pId, list);
		for (Map map : listMap) {
			String id = map.get("id").toString();// id
			String parentId = map.get("pId").toString();// 父id
			String name = map.get("name").toString();// 名称
			String isLeaf = map.get("isLeaf").toString();// 是否叶子科目

			if (isLeaf.equals("1")) {
				buffer.append("<li><a onclick=\"$.bringBack({id:'" + id + "', name:'" + name + "'})\"  href=\"javascript:\"  >" + name + "</a>");
			} else {
				buffer.append("<li><a id='" + id + "' pid='" + parentId + "' isleaf='" + isLeaf + "'>" + name + "</a>");
			}

			if (!isLeaf.equals("1")) {
				recursionTreeMenuLookUp(id, buffer, list);
			}
			buffer.append("</li>");
		}
		buffer.append("</ul>");
	}

	/**
	 * 根据菜单ID查找菜单（可用于判断菜单下是否还有子菜单）.
	 * 
	 * @param parentId
	 *            .
	 * @return menuList.
	 */
	public List<PmsMenu> listByParentId(Long parentId) {
		return pmsMenuDao.listByParentId(parentId);
	}

	/***
	 * 根据名称和是否叶子节点查询数据
	 * 
	 * @param isLeaf
	 *            是否是叶子节点
	 * @param name
	 *            节点名称
	 * @return
	 */
	public List<PmsMenu> getMenuByNameAndIsLeaf(Map<String, Object> map) {
		return pmsMenuDao.getMenuByNameAndIsLeaf(map);
	}

	/**
	 * 根据菜单ID获取菜单.
	 * @param menuId 菜单ID .
	 * @return PmsMenu .
	 */
	public PmsMenu getById(Long menuId) {
		return pmsMenuDao.getById(menuId);
	}

	/**
	 * 更新菜单.
	 * @param menu
	 */
	public void update(PmsMenu menu) {
		pmsMenuDao.update(menu);
		
	}
	
	/**
	 * 根据角色ID统计关联到此角色的菜单数.
	 * 
	 * @param roleId
	 *            角色ID.
	 * @return count.
	 */
	public int countMenuByRoleId(Long roleId) {
		List<PmsRoleMenu> meunList = pmsRoleMenuDao.listByRoleId(roleId);
		if (meunList == null || meunList.isEmpty()) {
			return 0;
		} else {
			return meunList.size();
		}
	}

}