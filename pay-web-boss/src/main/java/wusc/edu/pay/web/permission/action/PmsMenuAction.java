/**
 * 
 */
package wusc.edu.pay.web.permission.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.common.web.struts.ModelDrivenUtil;
import wusc.edu.pay.web.permission.base.PermissionBaseAction;
import wusc.edu.pay.web.permission.biz.PmsActionBiz;
import wusc.edu.pay.web.permission.biz.PmsMenuBiz;
import wusc.edu.pay.web.permission.entity.PmsAction;
import wusc.edu.pay.web.permission.entity.PmsMenu;

import com.opensymphony.xwork2.ModelDriven;

/**
 * <ul>
 * <li>Title:菜单权限控制器</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2013-11-12
 */
public class PmsMenuAction extends PermissionBaseAction implements ModelDriven<PmsMenu> {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(PmsMenuAction.class);

	private static final String EDIT_MENU_ACTION = "pmsMenu_editPmsMenuUI.action";

	private PmsMenu pmsMenu = new PmsMenu();

	@Override
	public PmsMenu getModel() {
		return (PmsMenu) ModelDrivenUtil.cleanModel(pmsMenu);
	}

	@Autowired
	private PmsMenuBiz pmsMenuBiz;

	@Autowired
	private PmsActionBiz pmsActionBiz;

	/**
	 * 列出要管理的菜单.
	 * 
	 * @return PmsMenuList .
	 */
	@Permission("pms:menu:view")
	public String listPmsMenu() {
		String str = pmsMenuBiz.getTreeMenu(EDIT_MENU_ACTION);
		super.putData("tree", str);
		return "PmsMenuList";
	}

	/**
	 * 进入新菜单添加页面.
	 * 
	 * @return PmsMenuAdd .
	 */
	@Permission("pms:menu:add")
	public String addPmsMenuUI() {
		Long pid = getLong("pid");
		if (null != pid) {
			PmsMenu parentMenu = pmsMenuBiz.getById(pid);
			pmsMenu.setParent(parentMenu);
		}
		return "PmsMenuAdd";
	}

	/**
	 * 保存新增菜单.
	 * 
	 * @return operateSuccess or operateError .
	 */
	@Permission("pms:menu:add")
	public String addPmsMenu() {
		try {
			String name = getModel().getName();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isLeaf", "1");
			map.put("name", name);
			List<PmsMenu> list = pmsMenuBiz.getMenuByNameAndIsLeaf(map);
			if (list.size() > 0) {
				return operateError("同级菜单名称不能重复");
			}
			pmsMenuBiz.createMenu(getModel());
			super.logSave("添加菜单[" + getModel().getName() + "]");
		} catch (Exception e) {
			// 记录系统操作日志
			log.error("== addPmsMenu exception:", e);
			super.logSaveError("增加菜单");
			return operateError("添加菜单出错");
		}
		return operateSuccess();
	}

	/**
	 * 进入菜单修改页面.
	 * 
	 * @return
	 */
	@Permission("pms:menu:view")
	public String editPmsMenuUI() {
		Long id = getLong("id");
		if (null != id) {
			pmsMenu = pmsMenuBiz.getById(id);
			super.pushData(pmsMenu);
		}
		return "PmsMenuEdit";
	}

	/**
	 * 保存要修改的菜单.
	 * 
	 * @return
	 */
	@Permission("pms:menu:edit")
	public String editPmsMenu() {
		try {

			PmsMenu menu = getModel();
			PmsMenu parentMenu = menu.getParent();
			if (null == parentMenu) {
				parentMenu = new PmsMenu();
				parentMenu.setId(0L);
			}
			menu.setParent(parentMenu);
			pmsMenuBiz.update(menu);
			// 记录系统操作日志
			super.logEdit("修改菜单,菜单名称[" + menu.getName() + "]");
			return operateSuccess();
		} catch (Exception e) {
			// 记录系统操作日志
			log.error("== editPmsMenu exception:", e);
			super.logEditError("修改菜单,菜单名称[" + getModel().getName() + "]");
			return operateError("保存菜单出错");
		}

	}

	/**
	 * 删除菜单.
	 * 
	 * @return
	 */
	@Permission("pms:menu:delete")
	public String delPmsMenu() {
		String menuName = null;
		try {
			Long menuId = getLong("id");
			if (menuId == null || menuId.longValue() == 0) {
				return operateError("无法获取要删除的数据");
			}
			PmsMenu menu = pmsMenuBiz.getById(menuId);
			if (menu == null) {
				return operateError("无法获取要删除的数据");
			}
			menuName = menu.getName();
			Long parentId = menu.getParent().getId(); // 获取父菜单ID

			// 先判断此菜单下是否有子菜单
			List<PmsMenu> childMenuList = pmsMenuBiz.listByParentId(menuId);
			if (childMenuList != null && !childMenuList.isEmpty()) {
				return operateError("此菜单下关联有【" + childMenuList.size() + "】个子菜单，不能支接删除!");
			}

			// 判断是否有权限关联到此菜单上，如有则不能删除
			List<PmsAction> actionList = pmsActionBiz.listByMenuId(menuId);
			if (actionList != null && !actionList.isEmpty()) {
				return operateError("此菜单下关联有【" + actionList.size() + "】个权限，要先解除关联后才能删除此菜单!");
			}

			// 删除掉菜单
			pmsMenuBiz.delete(menuId);

			// 删除菜单后，要判断其父菜单是否还有子菜单，如果没有子菜单了就要装其父菜单设为叶子节点
			List<PmsMenu> childList = pmsMenuBiz.listByParentId(parentId);
			if (childList == null || childList.isEmpty()) {
				// 此时要将父菜单设为叶子
				PmsMenu parent = pmsMenuBiz.getById(parentId);
				parent.setIsLeaf(true);
				pmsMenuBiz.update(parent);
			}
			// 记录系统操作日志
			super.logDelete("删除菜单,菜单名称[" + menu.getName() + "]");
			return operateSuccess();
		} catch (Exception e) {
			// 记录系统操作日志
			log.error("== delPmsMenu exception:", e);
			super.logDeleteError("删除菜单,菜单名称[" + menuName + "]");
			return operateError("删除菜单出错");
		}
	}

}
