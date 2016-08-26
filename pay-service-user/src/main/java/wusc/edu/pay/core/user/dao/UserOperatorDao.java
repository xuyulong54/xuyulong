package wusc.edu.pay.core.user.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.user.entity.UserOperator;


/**
 * 
 * @描述: 用户操作员表数据访问层接口.
 * @作者: WuShuicheng .
 * @创建时间: 2013-8-6,下午3:50:29 .
 * @版本: 1.0 .
 */
public interface UserOperatorDao extends BaseDao<UserOperator> {

	/**
	 * 根据登录名获取用户操作员信息.<br/>
	 * @param loginName 登录名.
	 * @return
	 */
	public UserOperator getByLoginName(String loginName);

	/**
	 * 根据用户编号列出该用户的所有操作员.<br/>
	 * @param userNo 用户编号.
	 * @return UserOperatorList 操作员列表.
	 */
	public List<UserOperator> listByUserNo(String userNo);

	/***
	 * 根据用户编号,修改该用户下所有操作员的状态.<br/>
	 * @param userNo 用户编号.<br/>
	 * @param status 要更新的状态.<br/>
	 */
	public void updateUserOperatorStatusByUserNo(String userNo, int status);

	/***
	 * 重置操作员密码
	 * @param loginName 登录名
	 * @param newPwd 新密码-加密后的数据
	 */
	public void resetUserOperatorPassword(String loginName, String newPwd);

	/***
	 * @Description: 根据商户编号和类型查询操作员表
	 * @return UserOperator  
	 * @throws
	 * @author Huang Bin
	 * @date 2015-4-7
	 */
	public UserOperator getOperator_userNo_type(String userNo, int type);

}
