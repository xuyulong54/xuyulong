package wusc.edu.pay.core.boss.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.boss.dao.EmailVerifyDao;
import wusc.edu.pay.facade.boss.entity.EmailVerify;


/**
 * 
 * @描述: 邮箱验证记录表数据访问层接口现实类.
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-29,下午3:33:49 .
 * @版本: 1.0 .
 */
@Repository("emailVerifyDao")
public class EmailVerifyDaoImpl extends BaseDaoImpl<EmailVerify> implements EmailVerifyDao {

	/**
	 * 根据令牌(token)获取邮箱验证记录.
	 * 
	 * @param id
	 *            .
	 * @return EmailVerify.
	 */
	public EmailVerify getByToken(String token) {
		return super.getSessionTemplate().selectOne(getStatement("getByToken"), token);
	}

	/**
	 * 批量修改状态
	 * 
	 * @param paramMap
	 * @return
	 */
	public int updateStatus(Map<String, Object> paramMap) {
		return super.getSessionTemplate().update(getStatement("updateStatus"), paramMap);
	}
}
