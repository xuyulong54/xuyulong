package wusc.edu.pay.core.boss.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.boss.dao.EmailVerifyDao;
import wusc.edu.pay.facade.boss.entity.EmailVerify;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;


/**
 * 
 * @描述: 邮件功能（邮箱验证记录表、邮件发送记录表）业务层.
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-29,下午3:41:03 .
 * @版本: 1.0 .
 */
@Component("emailBiz")
public class EmailBiz {

	@Autowired
	private EmailVerifyDao emailVerifyDao;

	/**
	 * 创建邮箱验证记录.
	 * 
	 * @param entity
	 *            .
	 * @return ID .
	 */
	public long createEmailVerify(EmailVerify entity) {
		return emailVerifyDao.insert(entity);
	}

	/**
	 * 更新邮箱验证记录.
	 * 
	 * @param param
	 *            .
	 * @return 0或1 .
	 */
	public long updateEmailVerify(EmailVerify entity) {
		return emailVerifyDao.update(entity);
	}

	/**
	 * 根据ID获取邮箱验证记录.
	 * 
	 * @param id
	 *            .
	 * @return EmailVerify.
	 */
	public EmailVerify getEmailVerifyById(long id) {
		return emailVerifyDao.getById(id);
	}

	/**
	 * 根据令牌(token)获取邮箱验证记录.
	 * 
	 * @param id
	 *            .
	 * @return EmailVerify.
	 */
	public EmailVerify getEmailVerifyByToken(String token) {
		return emailVerifyDao.getByToken(token);
	}

	/**
	 * 分页查询邮箱验证记录.
	 * 
	 * @param pageParam
	 *            分页参数.
	 * @param paramMap
	 *            查询能数 .
	 * @return EmailVerifyList.
	 */
	public PageBean queryEmailVerifyListPage(PageParam pageParam, Map<String, Object> paramMap) {
		return emailVerifyDao.listPage(pageParam, paramMap);
	}

	/***
	 * 根据条件查询邮箱验证记录
	 * 
	 * @param paramMap
	 * @return
	 * @throws BossBizException
	 */
	public List<EmailVerify> listModelByCondition(Map<String, Object> paramMap) {
		return emailVerifyDao.listBy(paramMap);
	}

	/**
	 * 批量修改状态
	 * 
	 * @param paramMap
	 * @return
	 */
	public int updateStatus(Map<String, Object> paramMap) {
		return emailVerifyDao.updateStatus(paramMap);
	}

}
