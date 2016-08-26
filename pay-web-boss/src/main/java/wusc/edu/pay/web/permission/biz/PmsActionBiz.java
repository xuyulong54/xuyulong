package wusc.edu.pay.web.permission.biz;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.web.permission.dao.PmsActionDao;
import wusc.edu.pay.web.permission.dao.PmsRoleActionDao;
import wusc.edu.pay.web.permission.entity.PmsAction;
import wusc.edu.pay.web.permission.entity.PmsRoleAction;


/**
 * 
 * @描述: 权限表--服务层接口.
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-25,下午10:40:40 .
 * @版本: 1.0 .
 */
@Service("pmsActionBiz")
public class PmsActionBiz {
	
	@Autowired
	private PmsActionDao pmsActionDao;
	@Autowired
	private PmsRoleActionDao pmsRoleActionDao;


	/**
	 * 根据Action的id字符串得到相应的权限列表
	 */
	public List<PmsAction> findActionsByIdStr(String ids) {
		return pmsActionDao.findByIds(ids);
	}

	/**
	 * 根据ID删除权限信息.
	 */
	public void deleteById(Long id) {
		pmsActionDao.deleteById(id);
	}

	/**
	 * 根据权限名称查找权限（用于判断权限名是否已存在）.
	 * 
	 * @param actionName
	 *            .
	 * @return PmsAction.
	 */
	public PmsAction getByActionName(String actionName) {
		return pmsActionDao.getByActionName(actionName);
	}

	/**
	 * 根据权限查找权限记录（用于判断权限是否已存在）.
	 * 
	 * @param action
	 *            .
	 * @return PmsAction.
	 */
	public PmsAction getByAction(String action) {
		return pmsActionDao.getByAction(action);
	}

	/**
	 * 检查修改后的权限名是否会与其他权限名冲突.
	 * 
	 * @param actionName
	 * @param id
	 * @return PmsAction.
	 */
	public PmsAction getByActionNameNotEqId(String actionName, Long id) {
		return pmsActionDao.getByActionNameNotEqId(actionName, id);
	}

	/**
	 * 检查修改后的权限是否会与其他权限冲突.
	 * 
	 * @param action
	 * @param id
	 * @return PmsAction.
	 */
	public PmsAction getByActionNotEqId(String action, Long id) {
		return pmsActionDao.getByActionNotEqId(action, id);
	}

	/**
	 * 根据菜单ID查找权限集.
	 * 
	 * @param menuId
	 *            菜单ID.
	 * @return .
	 */
	public List<PmsAction> listByMenuId(Long menuId) {
		return pmsActionDao.listByMenuId(menuId);
	}

	/**
	 * 查询并分页列出权限功能点.
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return pmsActionDao.listPage(pageParam, paramMap);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public PmsAction getById(Long id) {
		return pmsActionDao.getById(id);
	}

	/**
	 * 保存权限功能点
	 * @param act
	 */
	public void saveAction(PmsAction act) {
		pmsActionDao.insert(act);
		
	}

	/**
	 * 更新权限功能点.
	 * @param pmsAction
	 */
	public void updateAction(PmsAction pmsAction) {
		pmsActionDao.update(pmsAction);
	}
	
	/**
	 * 根据权限ID删除权限并解除权限与角色的关联关系.
	 * 
	 * @param actionId
	 *            .
	 */
	public void deleteActionById(Long actionId) {
		pmsActionDao.deleteById(actionId);
		// 删除权限和角色关联表中的关联关系
		pmsRoleActionDao.deleteByActionId(actionId);
	}
	
	/**
	 * 根据角色ID统计有多少权限关联到此角色.
	 * 
	 * @param roleId
	 *            角色ID.
	 * @return count.
	 */
	public int countActionByRoleId(Long roleId) {
		List<PmsRoleAction> actionList = pmsRoleActionDao.listByRoleId(roleId);
		if (actionList == null || actionList.isEmpty()) {
			return 0;
		} else {
			return actionList.size();
		}
	}
	
	/**
	 * 根据角色ID，获取所有的功能权限ID集
	 * 
	 * @param roleId
	 * @return actionIds
	 */
	public String getActionIdsByRoleId(Long roleId) {
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
	 * 根据角色ID集得到所有权限ID集
	 * @param roleIds
	 * @return actionIds
	 */
	public String getActionIdsByRoleIds(String roleIds) {
		// 得到角色－权限表中roleiId在ids中的所有关联对象
		List<PmsRoleAction> listPmsRoleActions = pmsRoleActionDao.listByRoleIds(roleIds);
		// 构建StringBuffer
		StringBuffer actionIdsBuf = new StringBuffer("");
		// 拼接字符串
		for (PmsRoleAction pmsRoleAction : listPmsRoleActions) {
			actionIdsBuf.append(pmsRoleAction.getActionId()).append(",");
		}
		String actionIds = actionIdsBuf.toString();
		// 截取字符串
		if (StringUtils.isNotBlank(actionIds) && actionIds.length() > 0) {
			actionIds = actionIds.substring(0, actionIds.length() - 1); // 去掉最后一个逗号
		}
		return actionIds;
	}

}
