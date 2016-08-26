/**
 * wusc.edu.pay.facade.user.service.impl.PortalCaFacadeImpl.java
 */
package wusc.edu.pay.facade.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.dao.PortalCADao;
import wusc.edu.pay.facade.user.entity.PortalCa;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.PortalCaFacade;


/**
 * <ul>
 * <li>Title:CA证书对外公布接口实现</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-5-30
 */
@Component("portalCaFacade")
public class PortalCaFacadeImpl implements PortalCaFacade {
	@Autowired
	private PortalCADao portalCADao;

	@Override
	public long saveOrUpdateProtalCA(PortalCa entity) throws UserBizException {
		if (null != entity && null != entity.getId()) {
			return portalCADao.update(entity);
		} else {
			return portalCADao.insert(entity);
		}
	}

	@Override
	public List<PortalCa> listByUserIdAndType(String userNo, Long userId, Integer userType) throws UserBizException {
		return portalCADao.listByUserIdAndType(userNo, userId, userType);
	}

	@Override
	public PortalCa getProtalCAById(long id) throws UserBizException {
		return portalCADao.getById(id);
	}

	@Override
	public PageBean queryProtalCAListPage(PageParam pageParam, Map<String, Object> paramMap) throws UserBizException {
		return portalCADao.listPage(pageParam, paramMap);
	}

	@Override
	public void deleteProtalCA(long id) throws UserBizException {
		portalCADao.deleteById(id);
	}
	@Override
	public List<PortalCa> listByUserNo(String userNo) throws UserBizException {
		return portalCADao.listByUserNo(userNo);
	}
	

}
