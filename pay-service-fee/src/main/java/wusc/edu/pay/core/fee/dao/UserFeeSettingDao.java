package wusc.edu.pay.core.fee.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.fee.entity.UserFeeSetting;


public interface UserFeeSettingDao extends BaseDao<UserFeeSetting>{

	List<UserFeeSetting> queryAllSettings(Date hourStart);

	/**
	 * 根据用户编号和用户类型查询 用户费率设置表
	 * @param userNo
	 * @param userType
	 * @return
	 */
	List<UserFeeSetting> queryFeeUserByConsumerNo(String userNo, Integer userType ,Integer calFeeItem);

	/**
	 * 获取指定用户类型、用户编号、费率节点下的费率客户信息设置
	 * @param consumer
	 * @param source
	 * @param nodeId
	 * @return
	 */
	UserFeeSetting getUserFeeByUserTypeNode(String userNo, Integer userType,
			Long nodeId);

	PageBean queryUserFeeSettingAndNodeListPage(PageParam pageParam,
			Map<String, Object> map);

}
