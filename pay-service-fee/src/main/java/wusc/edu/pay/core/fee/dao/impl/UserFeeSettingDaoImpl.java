package wusc.edu.pay.core.fee.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.fee.dao.UserFeeSettingDao;
import wusc.edu.pay.facade.fee.entity.UserFeeSetting;


@Repository("userFeeSettingDao")
public class UserFeeSettingDaoImpl extends BaseDaoImpl<UserFeeSetting> implements UserFeeSettingDao {
	/**
	 * 根据开始时间查询用户费率设置
	 * 
	 * @param hourStart
	 * @return
	 */
	@Override
	public List<UserFeeSetting> queryAllSettings(Date target) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("target", target);
		return this.getSqlSession().selectList(getStatement("queryAll"), paramMap);
	}

	/**
	 * 根据用户编号和用户类型查询 用户费率设置表
	 * 
	 * @param userNo
	 * @param userType
	 * @return
	 */
	public List<UserFeeSetting> queryFeeUserByConsumerNo(String userNo, Integer userType, Integer calFeeItem) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", userNo);
		paramMap.put("userType", null);
		paramMap.put("calculateFeeItem", calFeeItem);
		paramMap.put("status", 100);
		return listBy(paramMap);
	}

	@Override
	public UserFeeSetting getUserFeeByUserTypeNode(String userNo, Integer userType, Long nodeId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", userNo);
		paramMap.put("userType", userType);
		paramMap.put("feeNodeId", nodeId);
		return this.getSqlSession().selectOne("getUserFeeByUserTypeNode", paramMap);
	}

	@Override
	public PageBean queryUserFeeSettingAndNodeListPage(PageParam pageParam, Map<String, Object> map) {

		return super.listPage(pageParam, map, "queryUserFeeSettingAndNodeListPage");

	}
}
