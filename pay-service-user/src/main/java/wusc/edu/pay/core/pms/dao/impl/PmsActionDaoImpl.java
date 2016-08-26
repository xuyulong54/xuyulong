package wusc.edu.pay.core.pms.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.pms.dao.PmsActionDao;
import wusc.edu.pay.facade.pms.entity.PmsAction;


/**
 * 
 * @描述: 权限点表数据访问层接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-22,下午5:51:47 .
 * @版本: 1.0 .
 */
@Repository("pmsActionDao")
public class PmsActionDaoImpl extends BaseDaoImpl<PmsAction> implements PmsActionDao {

	/**
	 * 根据实体ID集字符串获取获取对象列表.
	 * 
	 * @param idStr
	 * @return
	 */
	public List<PmsAction> findByIds(String idStr) {
		List<String> ids = Arrays.asList(idStr.split(","));
		return this.getSqlSession().selectList(getStatement("findByIds"), ids);
	}

	/**
	 * 根据权限名称查找权限（用于判断权限名是否已存在）.
	 * 
	 * @param actionName
	 *            .
	 * @return PmsAction.
	 */
	@Override
	public PmsAction getByActionName(String actionName) {
		return this.getSqlSession().selectOne(getStatement("getByActionName"), actionName);
	}

	/**
	 * 根据权限查找权限记录（用于判断权限是否已存在）.
	 * 
	 * @param action
	 *            .
	 * @return PmsAction.
	 */
	@Override
	public PmsAction getByAction(String action) {
		return this.getSqlSession().selectOne(getStatement("getByAction"), action);
	}

	/**
	 * 检查修改后的权限名是否会与其他权限名冲突.
	 * 
	 * @param actionName
	 * @param id
	 * @return PmsAction.
	 */
	@Override
	public PmsAction getByActionNameNotEqId(String actionName, Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("actionName", actionName);
		param.put("id", id);
		return this.getSqlSession().selectOne(getStatement("getByActionNameNotEqId"), param);
	}

	/**
	 * 检查修改后的权限是否会与其他权限冲突.
	 * 
	 * @param action
	 * @param id
	 * @return PmsAction.
	 */
	@Override
	public PmsAction getByActionNotEqId(String action, Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("action", action);
		param.put("id", id);
		return this.getSqlSession().selectOne(getStatement("getByActionNotEqId"), param);
	}

	/**
	 * 根据菜单ID找出该菜单下的所有权限.
	 * 
	 * @param menuId
	 *            .
	 * @return
	 */
	@Override
	public List<PmsAction> listAllByMenuId(Long menuId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("menuId", menuId);
		return this.getSqlSession().selectList(getStatement("listAllByMenuId"), param);
	}

	/**
	 * 根据菜单ID查找权限集.
	 * 
	 * @param menuId
	 *            菜单ID.
	 * @return .
	 */
	@Override
	public List<PmsAction> listByMenuId(Long menuId) {
		return this.getSqlSession().selectList(getStatement("listByMenuId"), menuId);
	}

}
