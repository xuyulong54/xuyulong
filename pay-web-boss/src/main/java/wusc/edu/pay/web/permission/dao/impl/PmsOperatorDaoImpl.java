package wusc.edu.pay.web.permission.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.web.permission.dao.PmsOperatorDao;
import wusc.edu.pay.web.permission.entity.PmsOperator;


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

	public PmsOperator findByLoginName(String loginName) {
		return super.getSqlSession().selectOne(getStatement("findByLoginName"), loginName);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List listByRoleId(long roleId) {
		return super.getSqlSession().selectList(getStatement("listByRoleId"), roleId);
	}

}
