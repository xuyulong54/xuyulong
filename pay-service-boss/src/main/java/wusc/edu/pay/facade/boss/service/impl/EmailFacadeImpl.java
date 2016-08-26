package wusc.edu.pay.facade.boss.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.boss.biz.EmailBiz;
import wusc.edu.pay.facade.boss.entity.EmailVerify;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;
import wusc.edu.pay.facade.boss.service.EmailFacade;


/**
 * 
 * @描述: 邮件功能对外服务接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-29,下午3:47:33 .
 * @版本: 1.0 .
 */
@Component("emailFacade")
public class EmailFacadeImpl implements EmailFacade {

	@Autowired
	private EmailBiz emailBiz;

	/**
	 * 创建邮箱验证记录.
	 * 
	 * @param entity
	 *            .
	 * @return ID .
	 */

	public long createEmailVerify(EmailVerify entity) {
		return emailBiz.createEmailVerify(entity);
	}

	/**
	 * 更新邮箱验证记录.
	 * 
	 * @param entity
	 *            .
	 * @return 0或1 .
	 */

	public long updateEmailVerify(EmailVerify entity) {
		return emailBiz.updateEmailVerify(entity);
	}

	/**
	 * 根据ID获取邮箱验证记录.
	 * 
	 * @param id
	 *            .
	 * @return EmailVerify.
	 */

	public EmailVerify getEmailVerifyById(long id) {
		return emailBiz.getEmailVerifyById(id);
	}

	/**
	 * 根据令牌(token)获取邮箱验证记录.
	 * 
	 * @param id
	 *            .
	 * @return EmailVerify.
	 */

	public EmailVerify getEmailVerifyByToken(String token) {
		return emailBiz.getEmailVerifyByToken(token);
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
		return emailBiz.queryEmailVerifyListPage(pageParam, paramMap);
	}

	/***
	 * 根据条件查询邮箱验证记录
	 * 
	 * @param paramMap
	 * @return
	 * @throws BossBizException
	 */
	@SuppressWarnings("rawtypes")
	public List listModelByCondition(Map<String, Object> paramMap) throws BossBizException {
		return emailBiz.listModelByCondition(paramMap);
	}

	@Override
	public long updateStatus(Map<String, Object> paramMap) {
		return emailBiz.updateStatus(paramMap);
	}
}
