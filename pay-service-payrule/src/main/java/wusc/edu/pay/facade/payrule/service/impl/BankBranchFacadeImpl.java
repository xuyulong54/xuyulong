package wusc.edu.pay.facade.payrule.service.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.constant.CacheConstant;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.cache.redis.RedisUtils;
import wusc.edu.pay.core.payrule.dao.BankBranchDao;
import wusc.edu.pay.facade.payrule.entity.BankBranch;
import wusc.edu.pay.facade.payrule.service.BankBranchFacade;


@Component("bankBranchFacade")
public class BankBranchFacadeImpl implements BankBranchFacade {
	
	private static final Log log = LogFactory.getLog(BankBranchFacadeImpl.class);

	@Autowired
	BankBranchDao bankBranchDao;

	@Override
	public long create(BankBranch entity) {
		saveCacheBankBranch(entity);
		return bankBranchDao.insert(entity);
	}

	@Override
	public long update(BankBranch entity) {
		saveCacheBankBranch(entity);
		return bankBranchDao.update(entity);
	}

	/**
	 * 缓存银行渠道
	 * 
	 * @param entity
	 */
	private void saveCacheBankBranch(BankBranch entity) {
		StringBuffer buffer = new StringBuffer(CacheConstant.BANK_BRANCH).append(entity.getFrpCode());
		RedisUtils.save(buffer.toString(), entity);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return bankBranchDao.listPage(pageParam, paramMap);
	}

	@Override
	public BankBranch getById(long id) {
		return bankBranchDao.getById(id);
	}

	/**
	 * 根据frpCode查找
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public BankBranch getByFrpCode(String frpCode) {
		return bankBranchDao.getByFrpCode(frpCode);
	}

	/**
	 * 根据frpCode从缓存中查找
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public BankBranch getCacheByFrpCode(String frpCode) {
		log.info("==>getCacheByFrpCode:" + frpCode);
		StringBuffer buffer = new StringBuffer(CacheConstant.BANK_BRANCH);
		buffer.append(frpCode);
		//Object object = RedisUtils.get(buffer.toString());
		BankBranch bankBranch = (BankBranch) RedisUtils.get(buffer.toString());
		if (bankBranch == null) {
			log.info("==>getCacheByFrpCode from db");
			bankBranch = bankBranchDao.getByFrpCode(frpCode);
			if (bankBranch == null) {
				return null;
			}
			RedisUtils.save(buffer.toString(), bankBranch);
		}
		log.info("==>getCacheByFrpCode from cache");
		return bankBranch;
	}
}
