package wusc.edu.pay.core.pms.biz;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.pms.dao.PmsRoleActionDao;
import wusc.edu.pay.core.pms.dao.PmsRoleDao;
import wusc.edu.pay.core.pms.dao.PmsRoleMenuDao;
import wusc.edu.pay.core.pms.dao.PmsRoleOperatorDao;
import wusc.edu.pay.facade.pms.entity.PmsRole;
import wusc.edu.pay.facade.pms.entity.PmsRoleOperator;


/**
 * 
 * @描述: 角色表--服务层接口.
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-25,下午10:44:16 .
 * @版本: 1.0 .
 */
@Component("pmsRoleBiz")
public class PmsRoleBiz {

	@Autowired
	private PmsRoleDao pmsRoleDao;
	@Autowired
	private PmsRoleActionDao pmsRoleActionDao;
	@Autowired
	private PmsRoleOperatorDao pmsRoleOperatorDao;
	@Autowired
	private PmsRoleMenuDao pmsRoleMenuDao;


	/**
	 * 根据角色类型、用户编号来获取角色列表（可用于列出操作员可以关联的角色）.
	 * @param roleType 角色类型. 
	 * @param userNo 用户编号. 
	 * @return roleList.
	 */
	public List<PmsRole> listByRoleTypeAndUserNo(String roleType, String userNo) {
		return pmsRoleDao.listByRoleTypeAndUserNo(roleType, userNo);
	}

	/**
	 * 根据角色ID删除角色 .
	 */
	public void deleteById(Long id) {
		pmsRoleDao.deleteById(id);
	}

	/**
	 * 根据角色名称、用户编号获取角色记录（用于判断角色名是否已存在）.
	 * @param roleName 角色名.
	 * @param userNo 用户编号.
	 * @return PmsRole.
	 */
	public PmsRole getByRoleNameAndUserNo(String roleName, String userNo) {
		return pmsRoleDao.getByRoleNameAndUserNo(roleName, userNo);
	}

	/**
	 * 查找是否存在与ID值不相同与角色名、用户编号相同的角色记录（用于判断修改的角色名与其他的角色名冲突）。
	 * @param roleName 角色名称.
	 * @param userNo 用户编号.
	 * @param id 角色ID .
	 * @return PmsRole.
	 */
	public PmsRole getByRoleNameAndUserNoNotEqId(String roleName, String userNo, Long id) {
		return pmsRoleDao.getByRoleNameAndUserNoNotEqId(roleName, userNo, id);
	}

	/**
	 * 根据权限ID找出关联了此权限的角色.
	 * 
	 * @param actionId
	 *            .
	 * @return roleList.
	 */
	public List<PmsRole> listByActionId(Long actionId) {
		return pmsRoleDao.listByActionId(actionId);
	}

	/**
	 * 查询并分页列出角色信息.
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return pmsRoleDao.listPage(pageParam, paramMap);
	}

	/**
	 * 根据ID获取角色.
	 * @param id
	 * @return
	 */
	public PmsRole getById(Long id) {
		return pmsRoleDao.getById(id);
	}

	/**
	 * 保存角色
	 * @param pmsRole
	 */
	public void create(PmsRole pmsRole) {
		pmsRoleDao.insert(pmsRole);
		
	}

	/**
	 * 更新角色.
	 * @param pmsRole
	 */
	public void update(PmsRole pmsRole) {
		pmsRoleDao.update(pmsRole);
		
	}
	
	/**
	 * 根据角色ID删除角色信息（并删除与之关联的权限、菜单、操作员的关联关系）.
	 * @param roleId 角我ID.
	 */
	public void deleteRoleById(Long roleId) {
		// 删除角色权限关联表中的数据
		pmsRoleActionDao.deleteByRoleId(roleId);
		// 删除角色菜单关联表中的数据
		pmsRoleMenuDao.deleteByRoleId(roleId);
		// 删除角色操作员关联表中的数据
		pmsRoleOperatorDao.deleteByRoleId(roleId);
		// 最后删除角色信息
		pmsRoleDao.deleteById(roleId);
		
	}
	
	/**
	 * 保存角色并关联权限.
	 * 
	 * @param pmsRole
	 *            角色信息.
	 */
	public void saveRole(PmsRole pmsRole) {
		pmsRoleDao.insert(pmsRole);
	}
	
	/**
	 * 根据操作员ID获得该操作员的所有角色id所拼成的String，每个ID用“,”分隔
	 * @param operatorId 操作员ID
	 * @return roleIds
	 */
	public String getRoleIdsByOperatorId(long operatorId) {
		// 得到操作员和角色列表
		List<PmsRoleOperator> rpList = pmsRoleOperatorDao.listByOperatorId(operatorId);
		// 构建StringBuffer来拼字符串
		StringBuffer roleIdsBuf = new StringBuffer("");
		for (PmsRoleOperator rp : rpList) {
			roleIdsBuf.append(rp.getRoleId()).append(",");
		}
		String roleIds = roleIdsBuf.toString();
		// 截取字符串
		if (StringUtils.isNotBlank(roleIds) && roleIds.length() > 0) {
			roleIds = roleIds.substring(0, roleIds.length() - 1);
		}
		return roleIds;
	}


}
