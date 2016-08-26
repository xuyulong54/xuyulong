package wusc.edu.pay.core.user.dao;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.facade.user.entity.MemberInfo;


/**
 * 会员信息表数据访问层接口.
 * 
 * @author xiehui
 * @time 2013-8-11,下午5:26:41
 */

public interface MemberInfoDao extends BaseDao<MemberInfo> {

	/**
	 * 根据身份证号码查询
	 * 
	 * @param cardNo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List findByCardNo(String cardNo);

	
	/**
	 * 根据会员编号查询会员信息
	 * @param memberNo
	 * @return
	 * @throws BizException
	 */
	MemberInfo getMemberInfoByMemberNo(String memberNo);
	
	/**
	 * 统计会员拉黑和冻结的人数
	 * @param listBlackUser
	 * @param listFreezeUser
	 * @return
	 */
	Map<String, Object> countMember(List<Map<String, Object>> listBlackUser,
			List<Map<String, Object>> listFreezeUser);
	
}
