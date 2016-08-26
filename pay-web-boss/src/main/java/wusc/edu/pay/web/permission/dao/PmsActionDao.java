package wusc.edu.pay.web.permission.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.web.permission.entity.PmsAction;


/**
 * 
 * @描述: 权限点表数据访问层接口.
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-22,下午5:51:47 .
 * @版本: 1.0 .
 */
public interface PmsActionDao extends BaseDao<PmsAction> {

	/**
	 * 根据实体ID集字符串获取获取对象列表.
	 * 
	 * @param ids
	 * @return
	 */
	List<PmsAction> findByIds(String ids);

	/**
	 * 根据权限名称查找权限（用于判断权限名是否已存在）.
	 * 
	 * @param actionName
	 *            .
	 * @return PmsAction.
	 */
	PmsAction getByActionName(String actionName);

	/**
	 * 根据权限查找权限记录（用于判断权限是否已存在）.
	 * 
	 * @param action
	 *            .
	 * @return PmsAction.
	 */
	PmsAction getByAction(String action);

	/**
	 * 检查修改后的权限名是否会与其他权限名冲突.
	 * 
	 * @param actionName
	 * @param id
	 * @return PmsAction.
	 */
	PmsAction getByActionNameNotEqId(String actionName, Long id);

	/**
	 * 检查修改后的权限是否会与其他权限冲突.
	 * 
	 * @param action
	 * @param id
	 * @return PmsAction.
	 */
	PmsAction getByActionNotEqId(String action, Long id);

	/**
	 * 根据菜单ID找出该菜单下的所有权限.
	 * 
	 * @param menuId
	 *            .
	 * @return
	 */
	List<PmsAction> listAllByMenuId(Long menuId);

	/**
	 * 根据菜单ID查找权限集.
	 * 
	 * @param menuId
	 *            菜单ID.
	 * @return .
	 */
	List<PmsAction> listByMenuId(Long menuId);

}
