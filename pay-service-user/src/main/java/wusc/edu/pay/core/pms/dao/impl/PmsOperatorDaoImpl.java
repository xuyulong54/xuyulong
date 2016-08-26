package wusc.edu.pay.core.pms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.pms.dao.PmsOperatorDao;
import wusc.edu.pay.facade.pms.entity.PmsOperator;


/**
 * 
 * @描述: 操作员表数据访问层接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2013-7-22,下午5:51:47 .
 * @版本: 1.0 .
 */
@Repository("pmsOperatorDao")
public class PmsOperatorDaoImpl extends BaseDaoImpl<PmsOperator> implements PmsOperatorDao {

	/**
	 * 根据操作员登录名获取操作员信息.
	 * 
	 * @param loginName
	 *            .
	 * @return operator .
	 */

	public PmsOperator getByLoginName(String loginName) {
		return super.getSqlSession().selectOne(getStatement("getByLoginName"), loginName);
	}

	@Override
	public List<PmsOperator> listByRoleId(long roleId) {
		return super.getSqlSession().selectList(getStatement("listByRoleId"), roleId);
	}
	/***
	 * 根据用户编号,修改该用户下所有操作员的状态.<br/>
	 * @param userNo 用户编号.<br/>
	 * @param status 要更新的状态.<br/>
	 */
	public void updateUserOperatorStatusByUserNo(String userNo, int status) {
		Map<String ,Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		map.put("status", status);
		super.getSessionTemplate().update("updateAgentOperatorStatusByUserNo", map);
	}

}
