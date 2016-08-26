package wusc.edu.pay.core.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.user.dao.UserVerificationCodeDao;
import wusc.edu.pay.facade.user.entity.UserVerificationCode;
import wusc.edu.pay.facade.user.enums.UserVerificationCodeStatusEnum;


/**
 * 
 * @描述: 用户验证码数据访问层接口实现类.
 * @作者: huqian .
 * @创建时间: 2015-3-2,下午3:50:58 .
 * @版本: 1.0 .
 */
@Repository("userVerificationCodeDao")
public class UserVerificationCodeDaoImpl extends BaseDaoImpl<UserVerificationCode> implements UserVerificationCodeDao {

	public List<UserVerificationCode> listUserVerificationCodeByLoginName(String loginName) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("loginName", loginName);
		parameter.put("status", UserVerificationCodeStatusEnum.NOT.getValue());
		return super.getSqlSession().selectList(getStatement("getByLoginName"), parameter);
	}
}
