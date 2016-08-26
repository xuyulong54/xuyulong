package wusc.edu.pay.core.boss.dao;

import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.boss.entity.EmailVerify;


/**
 * 
 * @描述: 邮箱验证记录表数据访问层接口.
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-29,下午3:32:14 .
 * @版本: 1.0 .
 */
public interface EmailVerifyDao extends BaseDao<EmailVerify> {

	/**
	 * 根据令牌(token)获取邮箱验证记录.
	 * 
	 * @param id
	 *            .
	 * @return EmailVerify.
	 */
	EmailVerify getByToken(String token);

	/**
	 * 批量修改状态
	 * 
	 * @param paramMap
	 * @return
	 */
	int updateStatus(Map<String, Object> paramMap);

}
