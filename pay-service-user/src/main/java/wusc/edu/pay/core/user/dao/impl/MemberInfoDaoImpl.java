package wusc.edu.pay.core.user.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.core.user.dao.MemberInfoDao;
import wusc.edu.pay.facade.user.entity.MemberInfo;

/**
 * 会员信息表数据访问层接口实现类.
 * @author  xiehui
 * @time 2013-8-8,下午1:40:54
 */
@Repository("memberInfoDao")
public class MemberInfoDaoImpl extends BaseDaoImpl<MemberInfo> implements MemberInfoDao {


	@SuppressWarnings("rawtypes")
	public List findByCardNo(String cardNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardNo", cardNo);
		return super.listBy(params);
	}
	
	/**
	 * 根据会员编号查询会员信息
	 * @param memberNo
	 * @return
	 * @throws BizException
	 */
	public MemberInfo getMemberInfoByMemberNo(String memberNo){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberNo", memberNo);
		return super.getBy(map);
	}

	@Override
	public Map<String, Object> countMember(
			List<Map<String, Object>> listBlackUser,
			List<Map<String, Object>> listFreezeUser) {
		List<String> blackList = new ArrayList<String>();
		for(Map<String, Object> map : listBlackUser){
			blackList.add(map.get("USERNO").toString());
		}
		List<String> freezeList = new ArrayList<String>();
		for(Map<String, Object> map : listFreezeUser){
			freezeList.add(map.get("USERNO").toString());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if(freezeList.size()==0){
			map.put("sumFreeze", "0");
		}else{
			map.put("sumFreeze",
					super.getSqlSession().selectOne(getStatement("sumFreeze"),
							freezeList));
		}
		if(blackList.size()==0){
			map.put("sumBlack", "0");
		}else{
			map.put("sumBlack",
					super.getSqlSession().selectOne(getStatement("sumBlack"),
							blackList));
		}
		
		return map;
	}
	
}
