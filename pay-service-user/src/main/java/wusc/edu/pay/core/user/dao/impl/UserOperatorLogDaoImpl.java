package wusc.edu.pay.core.user.dao.impl;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.user.dao.UserOperatorLogDao;
import wusc.edu.pay.facade.user.entity.UserOperatorLog;


/**
 * 
 * @描述: 用户操作日志表数据访问层接口实现.
 * @作者: WuShuicheng.
 * @创建: 2014-5-28,下午1:59:49
 * @版本: V1.0
 *
 */
@Repository("userOperatorLogDao")
public class UserOperatorLogDaoImpl extends BaseDaoImpl<UserOperatorLog> implements UserOperatorLogDao {

}
