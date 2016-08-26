/**
 * wusc.edu.pay.facade.user.service.impl.MemberInfoFacadeImpl.java
 */
package wusc.edu.pay.facade.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.dao.MemberInfoDao;
import wusc.edu.pay.facade.user.entity.MemberInfo;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.MemberInfoFacade;


/**
 * <ul>
 * <li>Title: 用户对外提供接口实现</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-6-3
 */
@Component("memberInfoFacade")
public class MemberInfoFacadeImpl implements MemberInfoFacade {

	@Autowired
	private MemberInfoDao memberInfoDao;

	public MemberInfo getMemberByUserNo(String userNo) throws UserBizException {
		return memberInfoDao.getMemberInfoByMemberNo(userNo);
	}

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws UserBizException {
		return memberInfoDao.listPage(pageParam, paramMap);
	}

	public long update(MemberInfo memberInfo) throws UserBizException {
		return memberInfoDao.update(memberInfo);
	}

	/***
	 * 根据ID查询会员信息
	 * @param memberId
	 * @return
	 */
	public MemberInfo getById(Long memberId) {
		return memberInfoDao.getById(memberId);
	}
	
	@Override
	public Map<String, Object> countMember(
			List<Map<String, Object>> listBlackUser,
			List<Map<String, Object>> listFreezeUser) {
		return memberInfoDao.countMember(listBlackUser, listFreezeUser);
	}

}
