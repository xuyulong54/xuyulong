package wusc.edu.pay.core.user.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.dao.UserOperatorDao;
import wusc.edu.pay.facade.user.entity.UserOperator;


/**
 * 
 * @描述: 用户操作员操作日志业务逻辑实现 .
 * @作者: WuShuicheng.
 * @创建: 2014-5-28,上午11:16:35
 * @版本: V1.0
 *
 */
@Component("userOperatorBiz")
public class UserOperatorBiz {
	
	@Autowired
	private UserOperatorDao userOperatorDao;
	
	/**
	 * 根据登录名获取用户操作员信息.<br/>
	 * @param loginName 登录名.
	 * @return
	 */
	public UserOperator getUserOperatorByLoginName(String loginName) {
		return userOperatorDao.getByLoginName(loginName);
	}
	
	/**
	 * 创建用户操作员信息.<br/>
	 * @param operator <br/>
	 * @return ID.<br/>
	 * @throws BizException
	 */
	public long createMerchantOperator(UserOperator operator) {
		return userOperatorDao.insert(operator);
	}
	
	/**
	 * 更新用户操作员信息.<br/>
	 * @param operator.<br/>
	 * @return
	 * @throws BizException
	 */
	public long updateUserOperator(UserOperator operator) {
		return userOperatorDao.update(operator);
	}
	
	/**
	 * 根据ID获取操作员信息.<br/>
	 * @param id 操作员ID.<br/>
	 * @return operator.<br/>
	 * @throws BizException
	 */
	public UserOperator getUserOperatorById(long merchantOperatorId) {
		return userOperatorDao.getById(merchantOperatorId);
	}
	
	/**
	 * 根据用户编号列出该用户的所有操作员.<br/>
	 * @param userNo 用户编号.
	 * @return UserOperatorList 操作员列表.
	 */
	public List<UserOperator> listUserOperatorByUserNo(String userNo) {
		return userOperatorDao.listByUserNo(userNo);
	}
	
	/**
	 * 分页查询
	 * @param pageParam
	 * @param params
	 * @return
	 */
	public PageBean listUserOperatorForPage(PageParam pageParam, Map<String,Object> params) {
		return userOperatorDao.listPage(pageParam, params);
	}
	
	/***
	 * 根据用户编号,修改该用户下所有操作员的状态.<br/>
	 * @param userNo 用户编号.<br/>
	 * @param status 要更新的状态.<br/>
	 */
	public void updateUserOperatorStatusByUserNo(String userNo, int status) {
		userOperatorDao.updateUserOperatorStatusByUserNo(userNo, status);
	}

	/***
	 * 重置操作员密码
	 * @param loginName 登录名
	 * @param newPwd 新密码-加密后的数据
	 */
	public void resetUserOperatorPassword(String loginName, String newPwd) {
		UserOperator uo = userOperatorDao.getByLoginName(loginName);
		uo.setIsChangedPwd(1);
		uo.setLoginPwd(newPwd);
		userOperatorDao.update(uo);
	}

}
